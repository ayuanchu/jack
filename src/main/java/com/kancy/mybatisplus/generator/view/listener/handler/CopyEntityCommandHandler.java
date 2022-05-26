package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.GlobalEntityConfig;
import com.kancy.mybatisplus.generator.core.CodeGenerator;
import com.kancy.mybatisplus.generator.enums.TemplateEngineTypeEnum;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.SystemUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.util.Optional;

/**
 * CopyEntityCommandHandler
 *
 * @author kancy
 * @date 2020/6/14 14:52
 */
@CommandAction(Command.COPY_ENTITY)
public class CopyEntityCommandHandler extends CustomCodeGeneratorCommandHandler {
    public CopyEntityCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handleCodeGenerator(CodeGenerator codeGenerator) {
        GlobalEntityConfig clone = GlobalConfigManager.get().getEntity().clone();
        clone.setTemplateEngineType(TemplateEngineTypeEnum.Freemark.name());
        clone.setTemplatePath("/copy.entity.ftl");
        Optional<String> entity = codeGenerator.getEntity(clone);
        if (!entity.isPresent()){
            throw new AlertException("处理失败");
        }
        processEntity(codeGenerator.getEntityName(), entity.get());
    }

    protected void processEntity(String entityName, String entity) {
        SystemUtils.setClipboardText(entity);
    }

    @Override
    protected void doSuccess() {
        AlertUtils.msg(component, "复制成功！");
    }
}
