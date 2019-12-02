package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.dto.ArticleDraftDto;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;

import java.util.List;

public interface ArticleService {
    Long save(ArticleDraftDto draft);

    List<ArticleDraftMetaData> search(ArticleSearchDto condition);
}
