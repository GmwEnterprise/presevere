package cn.gmwenterprise.website.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * pre_article_image_store
 */
@Data
@Alias("preArticleImageStore")
public class PreArticleImageStore {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id]
     */
    private Integer id;
    /**
     * [image_title] 图片标题
     */
    private String imageTitle;
    /**
     * [image_content] 图片字节
     */
    private byte[] imageContent;
    private String imageType;
    private String contentType;
    /**
     * [add_datetime] 图片添加时间
     */
    private LocalDateTime addDatetime;
}
