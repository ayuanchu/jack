package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.utils.FileUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.components.SimpleDirectoryChooser;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;
import com.kancy.mybatisplus.generator.view.model.ModuleComboBoxModel;

import java.awt.event.MouseEvent;

/**
 * OpenTableCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 16:19
 */
@CommandAction(Command.SELECT_MODULE)
public class SelectModuleCommandHandler extends MouseEventCommandHandler<Main>{

    public SelectModuleCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(MouseEvent event) {
        if (event.getClickCount() == 2){
            SimpleDirectoryChooser chooser = new SimpleDirectoryChooser(component, "请选择生成文件存放的目录");
            chooser.setMultiSelectionEnabled(false).setHideFileShowEnabled(false).setOnlyDirectorySelection();
            chooser.setCurrentDirectory(FileUtils.getFile(Settings.getDefaultWorkPath()));
            chooser.showSaveDialog();

            if(chooser.hasSelectedFile()){
                String selectedFilePath = chooser.getSelectedFilePath();
                ModuleComboBoxModel model = (ModuleComboBoxModel) component.getComboBox_module().getModel();
                model.refresh(selectedFilePath);
            }
        }
    }
}
