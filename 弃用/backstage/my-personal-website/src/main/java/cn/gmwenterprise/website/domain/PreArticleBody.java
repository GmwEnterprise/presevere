package cn.gmwenterprise.website.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.*;

/**
 * pre_article_body 
 */
@Data
@Alias("preArticleBody")
public class PreArticleBody {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 
     */
    private Integer id;
    /**
     * [article_msg_id] 文章外键
     */
    private Integer articleMsgId;
    /**
     * [content] 内容
     */
    private String content;
    /**
     * [content_type] 内容类型（默认为markdown）
     */
    private String contentType;
}
