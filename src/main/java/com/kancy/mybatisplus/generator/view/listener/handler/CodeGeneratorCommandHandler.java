package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.core.CodeGenerator;
import com.kancy.mybatisplus.generator.core.CodeGeneratorFactory;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;
import com.kancy.mybatisplus.generator.view.model.ModuleComboBoxModel;
import com.kancy.mybatisplus.generator.view.model.PackageComboBoxModel;
import org.apache.commons.io.FileUtils;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * OpenConsoleCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 16:13
 */
@CommandAction(Command.CODE_GENERATOR)
public class CodeGeneratorCommandHandler extends ActionEventCommandHandler<Main> {

    public CodeGeneratorCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a Action
     *
     * @param e
     */
    @Override
    protected void handle(ActionEvent e) {

        // 选择的表
        int[] selectedRows = component.getTable_db_tables().getSelectedRows();
        if (selectedRows.length < 1){
            AlertUtils.msg(component, "至少需要选择一张表！");
            return;
        }

        // 基础路径
        Object moduleOrPath = component.getComboBox_module().getSelectedItem();
        if (moduleOrPath instanceof ModuleComboBoxModel.ItemData){
            moduleOrPath = ((ModuleComboBoxModel.ItemData) moduleOrPath).getPath();
        }
        if (Objects.isNull(moduleOrPath) || StringUtils.isEmpty(moduleOrPath.toString())){
            AlertUtils.msg(component, "没有选择模块或者没有指定生成文件基础路径！");
            return;
        }
        if (!FileUtils.getFile(moduleOrPath.toString()).isAbsolute()){
            File file = FileUtils.getFile(Settings.getDefaultWorkPath(), moduleOrPath.toString());
            try {
                moduleOrPath = file.getCanonicalPath();
            } catch (IOException ex) {
                moduleOrPath = file.getAbsolutePath();
            }
        }

        // 基础包名
        Object packageName = component.getComboBox_package().getSelectedItem();
        if (Objects.isNull(packageName) || StringUtils.isEmpty(packageName.toString())){
            AlertUtils.msg(component, "请输入基础包名！");
            return;
        }

        Set<String> tableNameSet = new HashSet<>();
        for (int selectedRow : selectedRows) {
            String tableName = component.getTable_db_tables().getModel().getValueAt(selectedRow, 1).toString();
            tableNameSet.add(tableName);
        }

        Map<String, List<ColumnInfo>> tableColumnMaps = ServiceManager.getDatabaseService().getColumns(DataSourceManager.getActive(), DatabaseManager.getActive(), tableNameSet);

        long tableIndex = 1;
        for (int selectedRow : selectedRows) {
            String tableName = component.getTable_db_tables().getModel().getValueAt(selectedRow, 1).toString();
            String tableRemark = component.getTable_db_tables().getModel().getValueAt(selectedRow, 2).toString();
            tableNameSet.add(tableName);
            CodeGenerator codeGenerator = CodeGeneratorFactory.builder()
                    .setTableCount(selectedRows.length)
                    .setTableIndex(tableIndex++)
                    .setBaseModulePath(moduleOrPath.toString())
                    .setBasePackageName(packageName.toString())
                    .setGlobalConfig(GlobalConfigManager.get())
                    .setDatabaseName(DatabaseManager.getActive())
                    .setTableName(tableName)
                    .setTableRemark(tableRemark)
                    .setColumnInfos(tableColumnMaps.getOrDefault(tableName, Collections.emptyList()))
                    .build();
            codeGenerator.generator();
        }
        AlertUtils.msg(component, "生成成功！");

        // 附加操作
        additionalOperations(moduleOrPath.toString(), packageName.toString());
    }

    private void additionalOperations(String moduleOrPath, String packageName) {
        try {
            // 生成成功时，也触发一次持久化操作，作为常用目录
            ModuleComboBoxModel moduleComboBoxModel = (ModuleComboBoxModel) component.getComboBox_module().getModel();
            moduleComboBoxModel.refresh(moduleOrPath);
            PackageComboBoxModel packageComboBoxModel = (PackageComboBoxModel) component.getComboBox_package().getModel();
            packageComboBoxModel.refresh(packageName);
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
    }
}
