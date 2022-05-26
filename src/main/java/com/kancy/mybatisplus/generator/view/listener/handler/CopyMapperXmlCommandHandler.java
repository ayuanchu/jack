package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.core.CodeGenerator;
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
@CommandAction(Command.COPY_MAPPER_XML)
public class CopyMapperXmlCommandHandler extends CustomCodeGeneratorCommandHandler {
    public CopyMapperXmlCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handleCodeGenerator(CodeGenerator codeGenerator) {

        Optional<String> entity = codeGenerator.getMapperXml(GlobalConfigManager.get().getMapperXml());
        if (!entity.isPresent()){
            throw new AlertException("处理失败");
        }

        SystemUtils.setClipboardText(entity.get());
    }

    @Override
    protected void doSuccess() {
        AlertUtils.msg(component, "复制成功！");
    }
}
