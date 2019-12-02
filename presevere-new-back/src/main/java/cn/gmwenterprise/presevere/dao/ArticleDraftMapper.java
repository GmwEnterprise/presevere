package cn.gmwenterprise.presevere.dao;

import cn.gmwenterprise.presevere.domain.ArticleDraft;
import cn.gmwenterprise.presevere.domain.ArticleDraftWithBLOBs;

public interface ArticleDraftMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_draft
     *
     * @mbg.generated Mon Dec 02 11:24:36 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_draft
     *
     * @mbg.generated Mon Dec 02 11:24:36 CST 2019
     */
    int insert(ArticleDraftWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_draft
     *
     * @mbg.generated Mon Dec 02 11:24:36 CST 2019
     */
    int insertSelective(ArticleDraftWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_draft
     *
     * @mbg.generated Mon Dec 02 11:24:36 CST 2019
     */
    ArticleDraftWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_draft
     *
     * @mbg.generated Mon Dec 02 11:24:36 CST 2019
     */
    int updateByPrimaryKeySelective(ArticleDraftWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_draft
     *
     * @mbg.generated Mon Dec 02 11:24:36 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(ArticleDraftWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_draft
     *
     * @mbg.generated Mon Dec 02 11:24:36 CST 2019
     */
    int updateByPrimaryKey(ArticleDraft record);

    int updateByUrlNumberSelectiveWithBLOBs(ArticleDraftWithBLOBs articleDraft);
}