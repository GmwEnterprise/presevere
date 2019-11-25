package cn.gmwenterprise.presevere.domain;

import java.time.LocalDateTime;

public class ArticleDraft {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.title
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.introduction
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String introduction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.tags
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String tags;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.writer
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer writer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.version
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.published
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Byte published;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.create_time
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.last_update_time
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private LocalDateTime lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_draft.content
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.id
     *
     * @return the value of article_draft.id
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.id
     *
     * @param id the value for article_draft.id
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.title
     *
     * @return the value of article_draft.title
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.title
     *
     * @param title the value for article_draft.title
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.introduction
     *
     * @return the value of article_draft.introduction
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.introduction
     *
     * @param introduction the value for article_draft.introduction
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.tags
     *
     * @return the value of article_draft.tags
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getTags() {
        return tags;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.tags
     *
     * @param tags the value for article_draft.tags
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.writer
     *
     * @return the value of article_draft.writer
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getWriter() {
        return writer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.writer
     *
     * @param writer the value for article_draft.writer
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setWriter(Integer writer) {
        this.writer = writer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.version
     *
     * @return the value of article_draft.version
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.version
     *
     * @param version the value for article_draft.version
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.published
     *
     * @return the value of article_draft.published
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Byte getPublished() {
        return published;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.published
     *
     * @param published the value for article_draft.published
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setPublished(Byte published) {
        this.published = published;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.create_time
     *
     * @return the value of article_draft.create_time
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.create_time
     *
     * @param createTime the value for article_draft.create_time
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.last_update_time
     *
     * @return the value of article_draft.last_update_time
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.last_update_time
     *
     * @param lastUpdateTime the value for article_draft.last_update_time
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_draft.content
     *
     * @return the value of article_draft.content
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_draft.content
     *
     * @param content the value for article_draft.content
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setContent(String content) {
        this.content = content;
    }
}