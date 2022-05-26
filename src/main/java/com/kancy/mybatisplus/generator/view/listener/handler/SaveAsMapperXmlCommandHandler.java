package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.core.CodeGenerator;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.utils.FileUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.io.IOException;
import java.util.Optional;

/**
 * CopyEntityCommandHandler
 *
 * @author kancy
 * @date 2020/6/14 14:52
 */
@CommandAction(Command.SAVE_AS_MAPPER_XML)
public class SaveAsMapperXmlCommandHandler extends SaveAsEntityCommandHandler {

    public SaveAsMapperXmlCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handleCodeGenerator(CodeGenerator codeGenerator) {
        Optional<String> mapperXml = codeGenerator.getMapperXml(GlobalConfigManager.get().getMapperXml());
        if (!mapperXml.isPresent()){
            throw new AlertException("处理失败");
        }

        try {
            FileUtils.writeStringToFile(FileUtils.getFile(saveAsPath.get(),
                    String.format(GlobalConfigManager.get().getMapperXml().getFileNameFormat(), codeGenerator.getEntityName())),
                    mapperXml.get() , Settings.getDefaultCharset());
        } catch (IOException e) {
            Log.debug(e.getMessage(), e);
            throw new AlertException("文件写入失败！");
        }
    }

}
