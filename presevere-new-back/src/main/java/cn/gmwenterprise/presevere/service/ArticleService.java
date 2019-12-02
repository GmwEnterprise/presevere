package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.dto.ArticleDraftDto;

public interface ArticleService {
    Long save(ArticleDraftDto draft);
}
