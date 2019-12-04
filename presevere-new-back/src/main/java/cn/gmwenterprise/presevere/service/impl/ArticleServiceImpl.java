package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.Authorization;
import cn.gmwenterprise.presevere.dao.ArticleBodyMapper;
import cn.gmwenterprise.presevere.dao.ArticleDraftMapper;
import cn.gmwenterprise.presevere.dao.ArticleMetadataMapper;
import cn.gmwenterprise.presevere.domain.*;
import cn.gmwenterprise.presevere.dto.ArticleDraftDto;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.service.ArticleService;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    ArticleDraftMapper articleDraftMapper;
    @Resource
    ArticleMetadataMapper articleMetadataMapper;
    @Resource
    ArticleBodyMapper articleBodyMapper;

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
            articleDraft.setUrlNumber(Long.parseLong(generateURLNumber(currentUser.getId())));
            articleDraftMapper.insertSelective(articleDraft);
            return articleDraft.getUrlNumber(); // key
        } else {
            // 更新
            articleDraft.setUrlNumber(draft.getKey());
            articleDraftMapper.updateByUrlNumberSelectiveWithBLOBs(articleDraft);
            return null;
        }
    }

    @Override
    public List<ArticleDraftMetaData> search(ArticleSearchDto condition, Authorization authorization) {
        boolean isAdmin = authorization.getPermissions()
            .stream().map(SysPermission::getPermission)
            .anyMatch(Permission.ADMIN::equals);
        if (!isAdmin) {
            // 非管理员只能查看自己的
            condition.setSelf(false);
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

    @Override
    public void publish(Long urlNumber) {
        ArticleDraftWithBLOBs draft = articleDraftMapper.selectByUrlNumber(urlNumber);
        ArticleMetadata metadata = articleMetadataMapper.selectByUrlNumber(urlNumber);
        ArticleBody body = new ArticleBody() {{
            setUrlNumber(draft.getUrlNumber());
            setContent(draft.getRenderHtml());
        }};
        if (metadata == null) {
            // 发布文章
            metadata = new ArticleMetadata() {{
                setWriter(draft.getWriter());
                setTitle(draft.getTitle());
                setIntroduction(draft.getIntroduction());
                setTags(draft.getTags());
                setUrlNumber(draft.getUrlNumber());
            }};
            articleMetadataMapper.insertSelective(metadata);
            articleBodyMapper.insertSelective(body);
        } else {
            // 修改更新文章
            metadata.setTitle(draft.getTitle());
            metadata.setIntroduction(draft.getIntroduction());
            metadata.setTags(draft.getTags());
            articleMetadataMapper.updateByUrlNumber(metadata);
            articleBodyMapper.updateByUrlNumberWithBLOBs(body);
        }
        // 更新当前草稿状态为已发布
        articleDraftMapper.updateByPrimaryKeySelective(new ArticleDraftWithBLOBs() {{
            setId(draft.getId());
            setPublished(ArticleDraft.PUBLISHED);
        }});
    }

    private String generateURLNumber(Integer userId) {
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
