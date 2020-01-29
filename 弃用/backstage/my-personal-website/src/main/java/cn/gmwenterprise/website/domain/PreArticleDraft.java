package cn.gmwenterprise.website.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.*;

/**
 * pre_article_draft 
 */
@Data
@Alias("preArticleDraft")
public class PreArticleDraft {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 主键
     */
    private Integer id;
    /**
     * [title] 草稿标题
     */
    private String title;
    /**
     * [creator] 草稿创建者
     */
    private Integer creator;
    /**
     * [update_time] 草稿更新时间
     */
    private LocalDateTime updateTime;
    /**
     * [introduction] 草稿介绍
     */
    private String introduction;
    /**
     * [tag] 草稿分类
     */
    private String tag;
    /**
     * [content] 草稿内容
     */
    private String content;
    /**
     * [content_type] 草稿文章类型
     */
    private String contentType;
    /**
     * [msg_id] 成功发布后关联的msgId
     */
    private Integer msgId;
    /**
     * [version] 版本号
     */
    private Integer version;
}
