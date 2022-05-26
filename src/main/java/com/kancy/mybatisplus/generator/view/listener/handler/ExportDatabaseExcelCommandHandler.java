package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.core.excel.Excel;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.components.SimpleFileDirectoryChooser;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Objects;

/**
 * <p>
 * ExportTablesXMindCommandHandler
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/16 15:52
 **/
@CommandAction(Command.EXPORT_DATABASE_DB_DOC)
public class ExportDatabaseExcelCommandHandler extends ExportExcelCommandHandler {

    public ExportDatabaseExcelCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a command
     *
     * @param event
     */
    @Override
    protected void handle(ActionEvent event) {

        exportExcelCheck();

        // 选择保存的文件
        SimpleFileDirectoryChooser chooser = new SimpleFileDirectoryChooser(component);
        chooser.setOnlyFileSelection();
        chooser.setCurrentDirectory(Settings.getDefaultWorkPath());
        chooser.setDefaultSelectedFile(new File(String.format( "%s.xls", DatabaseManager.getActive())));
        chooser.setTitle("请选择数据库文档保存的位置");
        chooser.setFileNameExtensionFilter("保存文件格式为xls", new String[]{"xls"});
        chooser.showSaveDialog();
        if (chooser.hasSelectedFile()){
            File selectedFile = chooser.getSelectedFile();
            Excel excel = getExcel(DataSourceManager.getActive(), DatabaseManager.getActive());
            if (Objects.nonNull(excel)){
                excel.save(selectedFile);
                AlertUtils.msg(component, "导出成功！");
            }else {
                AlertUtils.msg(component, "导出失败！");
            }
        }
    }
}
