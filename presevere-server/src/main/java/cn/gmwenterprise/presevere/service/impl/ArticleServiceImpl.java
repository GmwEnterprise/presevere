package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.BusinessException;
import cn.gmwenterprise.presevere.common.LinkedHashMapArrayList;
import cn.gmwenterprise.presevere.common.MarkdownUtils;
import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.Authorization;
import cn.gmwenterprise.presevere.dao.ArticleBodyMapper;
import cn.gmwenterprise.presevere.dao.ArticleDraftMapper;
import cn.gmwenterprise.presevere.dao.ArticleMetadataMapper;
import cn.gmwenterprise.presevere.dao.ArticleTagStoreMapper;
import cn.gmwenterprise.presevere.domain.*;
import cn.gmwenterprise.presevere.dto.ArticleDraftDto;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.service.AsyncTaskService;
import cn.gmwenterprise.presevere.vo.Archive;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;
import cn.gmwenterprise.presevere.vo.ArticleVo;
import cn.gmwenterprise.presevere.vo.TagCountVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleDraftMapper articleDraftMapper;
    @Resource
    ArticleMetadataMapper articleMetadataMapper;
    @Resource
    ArticleBodyMapper articleBodyMapper;
    @Resource
    ArticleTagStoreMapper articleTagStoreMapper;
    @Resource
    AsyncTaskService asyncTaskService;

    private DecimalFormat decimalFormat = new DecimalFormat("00");

    @Override
    public Long save(ArticleDraftDto draft, Authorization authorization) {
        ArticleDraftWithBLOBs articleDraft = new ArticleDraftWithBLOBs();// 要保存的数据
        articleDraft.setTitleIfNotNull(draft.getTitle());
        articleDraft.setIntroductionIfNotNull(draft.getIntroduction());
        articleDraft.setContentIfNotNull(draft.getMarkdown());
        articleDraft.setRenderHtmlIfNotNull(draft.getRender());
        articleDraft.setTagsList(draft.getTags());
        if (StringUtils.isEmpty(draft.getKey())) {
            // 新建
            SysUser currentUser = authorization.currentUser();
            articleDraft.setVersion(1);
            articleDraft.setPublished(ArticleDraft.UNPUBLISHED);
            articleDraft.setWriter(currentUser.getId());
            articleDraft.setUrlNumber(Long.parseLong(generateUrlNumber(currentUser.getId())));
            articleDraftMapper.insertSelective(articleDraft);
            return articleDraft.getUrlNumber(); // key
        } else {
            // 更新
            articleDraft.setUrlNumber(draft.getKey());
            boolean tagsIsNull = articleDraft.getTags() == null;
            boolean titleIsNull = articleDraft.getTitle() == null;
            boolean contentIsNull = articleDraft.getContent() == null;
            if (tagsIsNull && titleIsNull && contentIsNull) {
                articleDraft.setTags("");
            }
            articleDraftMapper.updateByUrlNumberSelectiveWithBLOBs(articleDraft);
            return null;
        }
    }

    private Boolean isAdmin(Authorization authorization) {
        return authorization.getPermissions()
            .stream().map(SysPermission::getPermission)
            .anyMatch(Permission.ADMIN::equals);
    }

    @Override
    public List<ArticleDraftMetaData> search(ArticleSearchDto condition, Authorization authorization) {
        if (!isAdmin(authorization)) {
            // 非管理员只能查看自己的
            condition.setSelf(true);
            condition.setCurrentUserId(authorization.getTokenPayload().getUserId());
        } else {
            // 管理员可以选择
            if (condition.getSelf() != null && condition.getSelf()) {
                // 只查询当前用户
                condition.setCurrentUserId(authorization.getTokenPayload().getUserId());
            }
        }
        condition.setPublished(ArticleDraft.UNPUBLISHED);
        return articleDraftMapper.selectBySearchCondition(condition);
    }

    @Override
    public ArticleDraftWithBLOBs get(Integer draftId) {
        return articleDraftMapper.selectByPrimaryKey(draftId);
    }

    @Override
    public void deleteDraft(Integer draftId) {
        articleDraftMapper.deleteByPrimaryKey(draftId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(Long urlNumber) {
        ArticleDraftWithBLOBs draft = articleDraftMapper.selectByUrlNumber(urlNumber);
        ArticleMetadata metadata = articleMetadataMapper.selectByUrlNumber(urlNumber);
        ArticleBodyWithBLOBs body = new ArticleBodyWithBLOBs() {{
            setUrlNumber(draft.getUrlNumber());
            setContent(MarkdownUtils.render(draft.getContent()));
            setContentMd(draft.getContent());
            setContentPlain(MarkdownUtils.plain(draft.getRenderHtml()));
        }};
        String introduction = StringUtils.isEmpty(draft.getIntroduction()) || draft.getIntroduction().trim().length() == 0 ?
            body.getContentPlain().substring(0, Math.min(body.getContentPlain().length(), 250)) : draft.getIntroduction();
        if (metadata == null) {
            // 发布文章
            metadata = new ArticleMetadata() {{
                setWriter(draft.getWriter());
                setTitle(draft.getTitle());
                setIntroduction(introduction);
                setTags(draft.getTags());
                setUrlNumber(draft.getUrlNumber());
            }};
            articleMetadataMapper.insertSelective(metadata);
            articleBodyMapper.insertSelective(body);
            // 更新当前草稿状态为已发布
            articleDraftMapper.updateByPrimaryKeySelective(new ArticleDraftWithBLOBs() {{
                setId(draft.getId());
                setPublished(ArticleDraft.PUBLISHED);
            }});
        } else {
            // 修改更新文章
            metadata.setTitle(draft.getTitle());
            metadata.setIntroduction(introduction);
            metadata.setTags(draft.getTags());
            articleMetadataMapper.updateByUrlNumber(metadata);
            articleBodyMapper.updateByUrlNumberWithBLOBs(body);
            // 版本 +1
            articleDraftMapper.updateByPrimaryKeySelective(new ArticleDraftWithBLOBs() {{
                setId(draft.getId());
                setVersion(draft.getVersion() + 1);
            }});
        }
        // 插入分类信息
        List<ArticleTagStore> tagStores = generateTagStoreList(metadata);
        articleTagStoreMapper.deleteByUrlNumber(metadata.getUrlNumber());
        if (tagStores != null && tagStores.size() > 0) {
            articleTagStoreMapper.insertBatch(tagStores);
        }

        // 发布成功后，调用邮件推送的异步程序
        asyncTaskService.pushNewPostEmails(draft.getUrlNumber());
    }

    private List<ArticleTagStore> generateTagStoreList(ArticleMetadata metadata) {
        String tags = metadata.getTags();
        if (StringUtils.isEmpty(tags) || tags.trim().length() == 0) {
            return null;
        }
        return Arrays.stream(tags.split(","))
            .map(tag -> new ArticleTagStore() {{
                setTag(tag);
                setUrlNumber(metadata.getUrlNumber());
            }}).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDraftMetaData> getArticleMetaDataList(ArticleSearchDto condition, Authorization authorization) {
        if (!isAdmin(authorization)) {
            // 非管理员只能查看自己的
            condition.setSelf(true);
            condition.setCurrentUserId(authorization.getTokenPayload().getUserId());
        } else {
            // 管理员可以选择
            if (condition.getSelf() != null && condition.getSelf()) {
                // 只查询当前用户
                condition.setCurrentUserId(authorization.getTokenPayload().getUserId());
            }
        }
        condition.setPublished(ArticleDraft.PUBLISHED);
        return articleDraftMapper.selectBySearchCondition(condition);
    }

    @Override
    public ArticleVo getArticleByUrl(Long url) {
        ArticleMetadata metadata = articleMetadataMapper.selectByUrlNumber(url);
        ArticleBodyWithBLOBs body = articleBodyMapper.selectByUrlNumber(url);
        try {
            return new ArticleVo(metadata, body);
        } catch (NullPointerException e) {
            throw new BusinessException("未找到相关数据。");
        }
    }

    @Override
    public List<TagCountVo> getAllTabs() {
        return articleTagStoreMapper.groupByTag();
    }

    @Override
    public List<ArticleMetadata> getListOrderBy(String orderBy, boolean desc, String tag) {
        try {
            return articleMetadataMapper.selectOrderBy(orderBy.replaceAll("[A-Z]", "_$0").toLowerCase(), desc, tag);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("请求参数错误!");
        }
    }

    @Override
    public Archive getArchiveDataByYear(Integer year) {
        if (year == null || year < 1000 || year > 9999) {
            throw new BusinessException("年份格式错误.");
        }
        LocalDateTime from = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        List<ArticleMetadata> resultList = articleMetadataMapper.selectByYear(from, to);
        List<Integer> years = articleMetadataMapper.selectAllYears();
        LinkedHashMapArrayList<Integer, ArticleMetadata> byMonth = new LinkedHashMapArrayList<>();
        resultList.forEach(metadata -> byMonth.set(metadata.getPublishedTime().getMonthValue(), metadata));
        return new Archive() {{
            setCurrentYear(year);
            setByMonth(byMonth);
            setHistoryYears(years);
        }};
    }

    private String generateUrlNumber(Integer userId) {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() % 100 +
            decimalFormat.format(now.getMonthValue()) +
            decimalFormat.format(now.getDayOfMonth()) +
            decimalFormat.format(now.getHour()) +
            decimalFormat.format(now.getMinute()) +
            decimalFormat.format(now.getSecond()) +
            userId;
    }
}
