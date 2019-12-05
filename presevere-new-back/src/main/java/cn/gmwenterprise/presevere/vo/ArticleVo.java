package cn.gmwenterprise.presevere.vo;

import cn.gmwenterprise.presevere.domain.ArticleBody;
import cn.gmwenterprise.presevere.domain.ArticleMetadata;

public class ArticleVo extends ArticleMetadata {
    private ArticleBody body;

    {
        body = new ArticleBody();
    }

    public ArticleVo() {
    }

    public ArticleVo(ArticleMetadata metadata, ArticleBody body) {
        setTitle(metadata.getTitle());
        setIntroduction(metadata.getIntroduction());
        setTags(metadata.getTags());
        setWriter(metadata.getWriter());
        setPublishedTime(metadata.getPublishedTime());
        setUrlNumber(metadata.getUrlNumber());
        setContent(body.getContent());
    }

    public void setContent(String content) {
        body.setContent(content);
    }

    public String getContent() {
        return body.getContent();
    }
}
