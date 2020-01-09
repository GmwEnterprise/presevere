package cn.presevere.next.domain;

import cn.presevere.next.common.BaseDomain;

public class CatalogItem extends BaseDomain<CatalogItem> {
    private Integer order; // 目录中的序号 / 行号
    private Integer level; // 层级
    private String title; // 目录项标题

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
