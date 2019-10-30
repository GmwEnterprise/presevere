package cn.gmwenterprise.website.vo;

import cn.gmwenterprise.website.domain.PreArticleBody;
import cn.gmwenterprise.website.domain.PreArticleMsg;
import lombok.Data;

@Data
public class PreArticle {

    private PreArticleMsg head;

    private PreArticleBody body;
}
