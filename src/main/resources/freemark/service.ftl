package ${service.packageName};

<#list service.importClasses as importClass>
import ${importClass};
</#list>
import com.baomidou.mybatisplus.extension.service.IService;
import ${entity.className};
/**
 * ${tableRemark}服务接口
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
 */
public interface ${service.name} ${service.extendsStatement}  extends IService<${entity.name}>{

}
