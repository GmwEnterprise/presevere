package cn.gmwenterprise.website.vo;

import lombok.Data;

import java.time.*;

/**
 * ${entityName} 业务对象
 */
@Data
public class ${entityName}Vo {
<#list columnList as field>
    /**
    <#if field.isPrimaryKey>
     * PRIMARY KEY${'<br>'}
    </#if>
    <#if field.isAutoIncrement>
     * AUTO INCREMENT${'<br>'}
    </#if>
     * [${field.columnName}] ${field.columnComment}
     */
    private ${field.fieldType} ${field.fieldName};
</#list>

    /**
     * 当前页（入参）
     */
    private Integer currentPage;
    /**
     * 每页条数（入参）
     */
    private Integer pageSize;
}
