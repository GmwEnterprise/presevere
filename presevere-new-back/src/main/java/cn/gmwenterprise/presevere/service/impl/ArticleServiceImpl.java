package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.Permission;
import cn.gmwenterprise.presevere.config.security.Authorization;
import cn.gmwenterprise.presevere.config.security.AuthorizationHolder;
import cn.gmwenterprise.presevere.dao.ArticleDraftMapper;
import cn.gmwenterprise.presevere.domain.ArticleDraft;
import cn.gmwenterprise.presevere.domain.ArticleDraftWithBLOBs;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.domain.SysUser;
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

    private DecimalFormat decimalFormat = new DecimalFormat("00");

    @Override
    public Long save(ArticleDraftDto draft, Authorization authorization) {
        ArticleDraftWithBLOBs articleDraft = new ArticleDraftWithBLOBs();// 要保存的数据
        articleDraft.setTitleIfNotNull(draft.getTitle());
        articleDraft.setIntroductionIfNotNull(draft.getIntroduction());
        articleDraft.setContentIfNotNull(draft.getMarkdown());
        articleDraft.setRenderHtmlIfNotNull(draft.getRender());
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
        return articleDraftMapper.selectByCondition(condition);
    }

    @Override
    public ArticleDraftWithBLOBs get(Integer draftId) {
        return articleDraftMapper.selectByPrimaryKey(draftId);
    }

    @Override
    public void deleteDraft(Integer draftId) {
        articleDraftMapper.deleteByPrimaryKey(draftId);
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
