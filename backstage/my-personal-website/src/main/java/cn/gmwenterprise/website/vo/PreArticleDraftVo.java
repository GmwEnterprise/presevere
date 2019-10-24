package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.*;

/**
 * PreArticleDraft 业务对象
 */
@Data
public class PreArticleDraftVo {
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

    private String htmlRender;

    /**
     * 当前页（入参）
     */
    private Integer currentPage;
    /**
     * 每页条数（入参）
     */
    private Integer pageSize;
}
