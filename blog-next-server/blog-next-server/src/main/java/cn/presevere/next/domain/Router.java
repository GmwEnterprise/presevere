package cn.presevere.next.domain;

import cn.presevere.next.common.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 文章跳转到指定标题的项
 */
@Getter
@Setter
@Entity
public class Router extends BaseDomain<Router> {
    /**
     * 关联post
     */
    @Column(name = "post_id")
    private Long postId;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 顺序【从1开始】
     */
    private Integer orderNum;
    /**
     * 标题
     */
    private String routerName;
    /**
     * 指向【html中a标签的id】
     */
    private String routerTo;
}
