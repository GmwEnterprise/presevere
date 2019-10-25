package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * PreArticleImageStore 业务对象
 */
@Data
public class PreArticleImageStoreVo {
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
