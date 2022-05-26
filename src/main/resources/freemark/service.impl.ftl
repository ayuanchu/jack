package ${serviceImpl.packageName};

<#list serviceImpl.importClasses as importClass>
import ${importClass};
</#list>
import ${service.packageName}.${service.name};
import org.springframework.stereotype.Service;
import ${entity.className};
import ${mapper.className};
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * ${tableRemark}服务接口实现
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
 */
<#list serviceImpl.classAnnotationList as classAnnotation>
${classAnnotation}
</#list>
@Service
public class ${serviceImpl.name} ${serviceImpl.extendsStatement}   extends ServiceImpl${r"<"}${mapper.name}, ${entity.name}> implements ${service.name}  {
<#list serviceImpl.autowires as autowire>
    ${autowire}
</#list>

}