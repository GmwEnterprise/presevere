package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.*;

/**
 * PreArticleMsg 业务对象
 */
@Data
public class PreArticleMsgVo {
    /**
     * PRIMARY KEY<br>
     * AUTO INCREMENT<br>
     * [id] 主键
     */
    private Integer id;
    /**
     * [title] 文章标题
     */
    private String title;
    /**
     * [writer] 文章作者
     */
    private Integer writer;
    /**
     * [create_time] 创建时间
     */
    private LocalDateTime createTime;
    /**
     * [update_time] 上次更新时间
     */
    private LocalDateTime updateTime;
    /**
     * [introduction] 文章介绍
     */
    private String introduction;
    /**
     * [tag] 分类标签（逗号分隔）
     */
    private String tag;
    /**
     * [status] 状态
     */
    private Integer status;

    /**
     * 当前页（入参）
     */
    private Integer currentPage;
    /**
     * 每页条数（入参）
     */
    private Integer pageSize;
}
