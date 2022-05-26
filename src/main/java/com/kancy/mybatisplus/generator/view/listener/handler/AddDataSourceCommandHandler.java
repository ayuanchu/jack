package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.view.DataSourceDialog;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;
import java.util.Objects;

/**
 * AddDataSourceCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 15:42
 */
@CommandAction(Command.ADD_DATASOURCE)
public class AddDataSourceCommandHandler extends DataSourceCommandHandler {

    public AddDataSourceCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a Action
     *
     * @param e
     */
    @Override
    protected void handle(ActionEvent e) {
        DataSourceDialog dataSourceDialog = new DataSourceDialog(component);
        DataSourceConfig dataSourceConfig = dataSourceDialog.getDataSourceConfig();
        if (Objects.isNull(dataSourceConfig) || Objects.isNull(dataSourceConfig.getName())){
            return;
        }

        // 添加数据源
        DataSourceManager.addDataSourceConfig(dataSourceConfig);
        // 刷新数据源显示组件
        component.refreshComboBox_datasource();

        // 优化用户体验
        // 当前没有激活的数据源时，激活新添加的数据源
        // 连接的数据库不是系统的，则自动选择该数据库
        if (Objects.isNull(DataSourceManager.getActive())){
            // 激活当前数据源
            DataSourceManager.setActive(dataSourceConfig);
            if (!StringUtils.isEmpty(dataSourceConfig.getDatabase())
                    && !ServiceManager.getDatabaseService().isBuiltInDatabase(dataSourceConfig)){
                // 激活当前数据源的数据库
                if (Objects.equals(dataSourceConfig.getDriver(), DatabaseDriverEnum.Oracle.name())){
                    DatabaseManager.setActive(dataSourceConfig.getUsername());
                }else {
                    DatabaseManager.setActive(dataSourceConfig.getDatabase());
                }
                // 选择这个数据库
                component.refreshComboBox_database();
                component.getComboBox_database().setSelectedItem(dataSourceConfig.getDatabase());
                // 刷新数据库表列表
                component.refreshTable_db_tables();
            }else {
                // 刷新数数据库选择框
                component.refreshComboBox_database();
            }
        }
    }
}
