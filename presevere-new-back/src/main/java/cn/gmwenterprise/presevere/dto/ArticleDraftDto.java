package cn.gmwenterprise.presevere.dto;

import java.util.List;

public class ArticleDraftDto {
    private Long key;
    // ###########################
    //       面向接收参数的字段
    // ###########################
    private String title; // 标题
    private String introduction; // 介绍
    private List<String> tags; // 标签
    private String markdown; // markdown内容
    private String render; // 渲染后的html

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }
}
