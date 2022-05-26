package ${entity.packageName};

import java.io.Serializable;
<#if global.lombokEnabled>
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
</#if>

/**
 * ${tableRemark}(${tableName})实体类
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
 */
<#if global.lombokEnabled>
@Data
@NoArgsConstructor
@Accessors(chain = true)
</#if>
public class ${entity.name} implements Serializable {
    private static final long serialVersionUID = 1L;

<#list entity.fields as field>
    /**
     * ${field.comment}
     */
    private ${field.type} ${field.name};
</#list>

<#if !global.lombokEnabled>
<#list entity.fields as field>
    public ${field.type} ${field.getMethodName}() {
        return ${field.name};
    }

    public void ${field.setMethodName}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }

</#list>
</#if>
}