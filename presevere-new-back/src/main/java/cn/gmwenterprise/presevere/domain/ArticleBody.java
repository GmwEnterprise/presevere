package cn.gmwenterprise.presevere.domain;

public class ArticleBody {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_body.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_body.url_number
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private Long urlNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article_body.content
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_body.id
     *
     * @return the value of article_body.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_body.id
     *
     * @param id the value for article_body.id
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_body.url_number
     *
     * @return the value of article_body.url_number
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public Long getUrlNumber() {
        return urlNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_body.url_number
     *
     * @param urlNumber the value for article_body.url_number
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setUrlNumber(Long urlNumber) {
        this.urlNumber = urlNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article_body.content
     *
     * @return the value of article_body.content
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article_body.content
     *
     * @param content the value for article_body.content
     *
     * @mbg.generated Mon Nov 18 11:38:29 CST 2019
     */
    public void setContent(String content) {
        this.content = content;
    }
}