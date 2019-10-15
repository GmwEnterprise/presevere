package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.*;

/**
 * PreArticleBody 业务对象
 */
@Data
public class PreArticleBodyVo {
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

    /**
     * 当前页（入参）
     */
    private Integer currentPage;
    /**
     * 每页条数（入参）
     */
    private Integer pageSize;
}
