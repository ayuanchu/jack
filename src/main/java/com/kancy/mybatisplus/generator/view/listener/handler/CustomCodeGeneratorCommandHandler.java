package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.core.CodeGenerator;
import com.kancy.mybatisplus.generator.core.CodeGeneratorFactory;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.view.Main;
import org.apache.commons.io.FileUtils;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * CustomCodeGeneratorCommandHandler
 *
 * @author kancy
 * @date 2020/6/14 14:20
 */
public abstract class CustomCodeGeneratorCommandHandler extends ActionEventCommandHandler<Main> {


    public CustomCodeGeneratorCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a command
     *
     * @param event
     */
    @Override
    protected void handle(ActionEvent event) {

        // 基础路径
        Object moduleOrPath = component.getComboBox_module().getSelectedItem();
        if (Objects.isNull(moduleOrPath) || StringUtils.isEmpty(moduleOrPath.toString())){
            moduleOrPath = Settings.getDefaultModuleName();
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
            packageName = Settings.getDefaultPackageName();
        }


        int[] selectedRows = component.getTable_db_tables().getSelectedRows();
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
            handleCodeGenerator(codeGenerator);
        }
        doSuccess();
    }

    protected abstract void handleCodeGenerator(CodeGenerator codeGenerator);

    protected void doSuccess() {
        AlertUtils.msg(component, "处理成功！");
    }

}
