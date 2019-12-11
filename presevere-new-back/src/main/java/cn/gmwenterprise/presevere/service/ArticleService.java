package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.config.security.Authorization;
import cn.gmwenterprise.presevere.domain.ArticleDraftWithBLOBs;
import cn.gmwenterprise.presevere.domain.ArticleMetadata;
import cn.gmwenterprise.presevere.dto.ArticleDraftDto;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.vo.Archive;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;
import cn.gmwenterprise.presevere.vo.ArticleVo;
import cn.gmwenterprise.presevere.vo.TagCountVo;

import java.util.List;

public interface ArticleService {
    Long save(ArticleDraftDto draft, Authorization authorization);

    List<ArticleDraftMetaData> search(ArticleSearchDto condition, Authorization authorization);

    ArticleDraftWithBLOBs get(Integer draftId);

    void deleteDraft(Integer draftId);

    void publish(Long urlNumber);

    List<ArticleDraftMetaData> getArticleMetaDataList(ArticleSearchDto condition, Authorization authorization);

    ArticleVo getArticleByUrl(Long url);

    List<TagCountVo> getAllTabs();

    List<ArticleMetadata> getListOrderBy(String orderBy, boolean desc, String tag);

    Archive getArchiveDataByYear(Integer year);
}
