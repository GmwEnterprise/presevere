package cn.presevere.next.domain;

import cn.presevere.next.common.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章
 */
@Getter
@Setter
@Entity
public class Post extends BaseDomain<Post> {
    /**
     * 标题
     */
    private String title;
    /**
     * 概要【自动生成】
     */
    private String outline;
    /**
     * 内容【Markdown】
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
    /**
     * 统计字数
     */
    private Integer wordCount;
    /**
     * 访问次数
     */
    private Integer visits;
    /**
     * 发表时间
     */
    private LocalDateTime issuingTime;
    /**
     * 最近更新时间
     */
    private LocalDateTime lastUpdated;
    /**
     * 目录【默认懒加载】
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "post_id",
        foreignKey = @ForeignKey(
            name = "none",
            value = ConstraintMode.NO_CONSTRAINT))
    private List<Router> catalog;
}
