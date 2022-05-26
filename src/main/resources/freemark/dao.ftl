package ${dao.packageName};

<#if (dao.importClasses?size > 0)>
<#list dao.importClasses as importClass>
import ${importClass};
</#list>
</#if>

import org.springframework.stereotype.Repository;

/**
 * ${tableRemark}(${tableName})数据DAO
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
 */
<#if (dao.classAnnotationList?size > 0)>
<#list dao.classAnnotationList as classAnnotation>
${classAnnotation}
</#list>
</#if>
@Repository
public class ${dao.name}  {

}