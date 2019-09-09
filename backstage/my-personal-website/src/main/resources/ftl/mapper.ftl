${'<?xml version="1.0" encoding="UTF-8"?>'}
${'<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">'}
${'<mapper namespace="cn.gmwenterprise.website.dao.' + entityName + 'Dao">'}

    ${'<resultMap id="BaseResultMap" type="${entityAlias}">'}
        <#list columnList as item>
        <#if item.isPrimaryKey>
        ${'<id column="' + item.columnName + '" jdbcType="' + item.columnType + '" property="' + item.fieldName + '"/>'}
        <#else>
        ${'<result column="' + item.columnName + '" jdbcType="' + item.columnType + '" property="' + item.fieldName + '"/>'}
        </#if>
        </#list>
    ${'</resultMap>'}

    ${'<sql id="Base_Column_List">'}
        <#list columnList as item>
        a.`${item.columnName}`<#if item_has_next>,</#if>
        </#list>
    ${'</sql>'}

    ${'<select id="selectByPrimaryKey" resultMap="BaseResultMap">'}
        select
        ${'<include refid="Base_Column_List"/>'}
        from ${tableName} a
        ${'where a.`${keyColumn}` = #' + '{${keyProperty}}'}
    ${'</select>'}

    ${'<select id="selectPage" resultMap="BaseResultMap">'}
        select
        ${'<include refid="Base_Column_List"/>'}
        from ${tableName} a
        ${'<where>'}
            <#list columnList as item>
            <#if !item.isPrimaryKey>
            ${'<if test="${item.fieldName} != null">'}
                <#if item.columnType == "VARCHAR">
                ${'and a.`${item.columnName}` like concat(\'%\', #' + '{${item.fieldName},jdbcType=${item.columnType}}, \'%\')'}
                <#else>
                ${'and a.`${item.columnName}` = #' + '{${item.fieldName},jdbcType=${item.columnType}}'}
                </#if>
            ${'</if>'}
            </#if>
            </#list>
        ${'</where>'}
    ${'</select>'}

    ${'<delete id="deleteByPrimaryKey">'}
        delete
        from ${tableName} a
        ${'where a.`${keyColumn}` = #' + '{${keyProperty}}'}
    ${'</delete>'}
<#--
    ${'<insert id="insert" useGeneratedKeys="true" keyColumn="${keyColumn}" keyProperty="${keyProperty}">'}
        insert into ${tableName} (
        <#list columnList as item>
        <#if !item.isPrimaryKey>
            `${item.columnName}`<#if item_has_next>,</#if>
        </#if>
        </#list>
        ) values (
        <#list columnList as item>
        <#if !item.isPrimaryKey>
            ${'#' + '{${item.fieldName},jdbcType=${item.columnType}}'}<#if item_has_next>,</#if>
        </#if>
        </#list>
        )
    ${'</insert>'}-->

    ${'<insert id="insert" useGeneratedKeys="true" keyColumn="${keyColumn}" keyProperty="${keyProperty}">'}
        insert into ${tableName}
        ${'<trim prefix="(" suffix=")" suffixOverrides=",">'}
            <#list columnList as item>
            <#if !item.isPrimaryKey>
            ${'<if test="${item.fieldName} != null">'}
                `${item.columnName}`,
            ${'</if>'}
            </#if>
            </#list>
        ${'</trim>'}
        ${'<trim prefix="values (" suffix=")" suffixOverrides=",">'}
            <#list columnList as item>
            <#if !item.isPrimaryKey>
            ${'<if test="${item.fieldName} != null">'}
                ${'#' + '{${item.fieldName},jdbcType=${item.columnType}},'}
            ${'</if>'}
            </#if>
            </#list>
        ${'</trim>'}
    ${'</insert>'}

    ${'<update id="updateByPrimaryKey">'}
        update ${tableName}
        ${'<set>'}
            <#list columnList as item>
            <#if !item.isPrimaryKey>
            ${'<if test="${item.fieldName} != null">'}
                ${'`${item.columnName}` = #' + '{${item.fieldName},jdbcType=${item.columnType}},'}
            ${'</if>'}
            </#if>
            </#list>
        ${'</set>'}
        ${'where ${keyColumn} = #' + '{${keyProperty}}'}
    ${'</update>'}
<#--
    ${'<update id="updateByPrimaryKey">'}
        update ${tableName}
        set
        <#list columnList as item>
        <#if !item.isPrimaryKey>
            ${'`${item.columnName}` = #' + '{${item.fieldName},jdbcType=${item.columnType}}'}<#if item_has_next>,</#if>
        </#if>
        </#list>
        ${'where ${keyColumn} = #' + '{${keyProperty}}'}
    ${'</update>'}-->
${'</mapper>'}