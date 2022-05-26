package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.FileUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.components.SimpleDirectoryChooser;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * CopyEntityCommandHandler
 *
 * @author kancy
 * @date 2020/6/14 14:52
 */
@CommandAction(Command.SAVE_AS_ENTITY)
public class SaveAsEntityCommandHandler extends CopyEntityCommandHandler {

    protected static final ThreadLocal<String> saveAsPath = new ThreadLocal<>();

    public SaveAsEntityCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a command
     *
     * @param event
     */
    @Override
    protected void handle(ActionEvent event) {
        try {
            String initSaveAsPath = initSaveAsPath();
            if (StringUtils.isEmpty(initSaveAsPath)){
                return;
            }
            saveAsPath.set(initSaveAsPath);
            super.handle(event);
        } finally {
            saveAsPath.remove();
        }
    }


    @Override
    protected void processEntity(String entityName, String entity) {
        try {
            FileUtils.writeStringToFile(FileUtils.getFile(saveAsPath.get(),
                    String.format(GlobalConfigManager.get().getEntity().getFileNameFormat(), entityName)),
                    entity, Settings.getDefaultCharset());
        } catch (IOException e) {
            Log.debug(e.getMessage(), e);
            throw new AlertException("文件写入失败！");
        }
    }

    @Override
    protected void doSuccess() {
        AlertUtils.msg(component, "另存为成功！");
    }

    private String initSaveAsPath() {
        SimpleDirectoryChooser chooser = new SimpleDirectoryChooser(component, "另存为");
        chooser.setMultiSelectionEnabled(false)
                .setHideFileShowEnabled(false)
                .setOnlyDirectorySelection()
                .setCurrentDirectory(FileUtils.getFile(Settings.getDefaultWorkPath()));
        chooser.showSaveDialog();
        if(chooser.hasSelectedFile()){
            return chooser.getSelectedFilePath();
        }
        return null;
    }
}
