package cn.gmwenterprise.website.service;

import cn.gmwenterprise.website.vo.${entityName}Vo;

import java.util.List;

/**
 * @author gmw
 */
public interface ${entityName}Service {

    /**
     * 删除记录
     *
     * @param ${keyProperty} 主键
     * @return 受影响行数
     */
    int deleteByPrimaryKey(${keyPropertyType} ${keyProperty});

    /**
     * 插入记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int insert(${entityName}Vo vo);
<#--
    /**
     * 插入满足条件的字段
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int insertSelective(${entityName}Vo vo);-->

    /**
     * 查询记录
     *
     * @param ${keyProperty} 主键
     * @return 结果
     */
    ${entityName}Vo selectByPrimaryKey(${keyPropertyType} ${keyProperty});

    /**
     * 查询所有记录
     *
     * @param vo 条件
     * @return 结果
     */
    List<${entityName}Vo> selectPage(${entityName}Vo vo);
<#--
    /**
     * 更新满足条件的字段
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKeySelective(${entityName}Vo vo);-->

    /**
     * 更新记录
     *
     * @param vo 记录
     * @return 受影响行数
     */
    int updateByPrimaryKey(${entityName}Vo vo);
}