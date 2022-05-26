package ${controller.packageName};

<#list controller.importClasses as importClass>
import ${importClass};
</#list>
import cn.renhe.va.common.common.SuccessResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ${entity.className};
import java.util.List;

/**
 * ${tableRemark}服务控制器
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
 */
<#list controller.classAnnotationList as classAnnotation>
${classAnnotation}
</#list>
@RestController
@RequestMapping("/${entity.name?uncap_first}")
public class ${controller.name} ${controller.extendsStatement}{
<#list controller.autowires as autowire>
    ${autowire}
</#list>
    /**
    * 列表
    */
    @PostMapping(path = {"/list"})
    @ApiOperation(value = "全部列表")
    public SuccessResult getList() {

        List<${entity.name}> res = ${controller.serviceName}.list();
        return   SuccessResult.ok().put("data", res);
    }


    /**
    * 保存
    */
    @PostMapping(path = {"/save"})
    public SuccessResult save(@Validated @RequestBody ${entity.name} entity) {
        ${controller.serviceName}.save(entity);
        return   SuccessResult.ok();
    }

    /**
    * 更新
    */
    @PostMapping(path = {"/update"})
    public SuccessResult update(@Validated @RequestBody ${entity.name} entity) {
        ${controller.serviceName}.updateById(entity);
        return   SuccessResult.ok();
    }



    /**
    * 删除
    */
    @PostMapping(path = {"/del"})
    public SuccessResult del(@Validated @RequestBody  ${entity.name} entity) {
        ${controller.serviceName}.removeById(entity);
        return   SuccessResult.ok();
    }



}