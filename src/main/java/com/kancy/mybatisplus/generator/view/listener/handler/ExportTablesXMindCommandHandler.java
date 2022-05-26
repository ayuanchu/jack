package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.core.xmind.XMind;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.service.TableInfo;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.components.SimpleFileDirectoryChooser;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * ExportTablesXMindCommandHandler
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/16 15:52
 **/
@CommandAction(Command.EXPORT_TABLES_XMIND)
public class ExportTablesXMindCommandHandler extends ExportXMindCommandHandler {

    public ExportTablesXMindCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a command
     *
     * @param event
     */
    @Override
    protected void handle(ActionEvent event) {
        exportXMindCheck();
        // 检查有没有选择表
        if (component.getTable_db_tables().getSelectedRowCount() < 1){
            AlertUtils.msg(component, "请至少选择一张需要导出的表！");
            return;
        }

        int[] selectedRows = component.getTable_db_tables().getSelectedRows();
        List<TableInfo> tableNames = new ArrayList<>();
        for (int selectedRow : selectedRows) {
            String tableName = component.getTable_db_tables().getModel().getValueAt(selectedRow, 1).toString();
            String tableRemark = component.getTable_db_tables().getModel().getValueAt(selectedRow, 2).toString();
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableName);
            tableInfo.setTableComment(tableRemark);
            tableNames.add(tableInfo);
        }

        // 选择保存的文件
        SimpleFileDirectoryChooser chooser = new SimpleFileDirectoryChooser(component);
        chooser.setOnlyFileSelection();
        chooser.setCurrentDirectory(Settings.getDefaultWorkPath());
        chooser.setDefaultSelectedFile(new File(String.format( "%s.xmind", DatabaseManager.getActive())));
        chooser.setTitle("请选择思维导图保存的位置");
        chooser.setFileNameExtensionFilter("保存文件格式为xmind", new String[]{"xmind"});
        chooser.showSaveDialog();
        if (chooser.hasSelectedFile()){
            File selectedFile = chooser.getSelectedFile();
            XMind xMind = getXMind(DatabaseManager.getActive(), tableNames);
            xMind.setFile(selectedFile);
            if (Objects.nonNull(xMind)){
                xMind.save();
                AlertUtils.msg(component, "导出成功！");
            }else {
                AlertUtils.msg(component, "导出失败！");
            }
        }
    }
}
