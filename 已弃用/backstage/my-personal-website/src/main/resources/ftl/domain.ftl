package cn.gmwenterprise.website.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.time.*;

/**
 * ${tableName} ${tableComment}
 */
@Data
@Alias("${entityAlias}")
public class ${entityName} {
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
}
