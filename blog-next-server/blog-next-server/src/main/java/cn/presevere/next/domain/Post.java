package cn.presevere.next.domain;

import cn.presevere.next.common.BaseDomain;

import java.time.LocalDateTime;
import java.util.List;

public class Post extends BaseDomain<Post> {
    private String title; // 标题
    private String outline; // 概要
    private List<CatalogItem> catalog; // 目录
    private LocalDateTime releaseTime; // 发布时间
    private LocalDateTime lastUpdateTime; // 最新修改时间
    private List<String> tags; // 标签列表
    private String classify; // 分类
    private Integer viewTimes; // 阅读次数
    private String markdownBody; // markdown正文
    private String htmlBody; // html正文

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public List<CatalogItem> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<CatalogItem> catalog) {
        this.catalog = catalog;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Integer getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Integer viewTimes) {
        this.viewTimes = viewTimes;
    }

    public String getMarkdownBody() {
        return markdownBody;
    }

    public void setMarkdownBody(String markdownBody) {
        this.markdownBody = markdownBody;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }
}
