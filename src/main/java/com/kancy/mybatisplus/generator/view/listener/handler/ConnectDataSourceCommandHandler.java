package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
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
@CommandAction(Command.CONNECT_DATASOURCE)
public class ConnectDataSourceCommandHandler extends DataSourceCommandHandler {

    public ConnectDataSourceCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a Action
     *
     * @param e
     */
    @Override
    protected void handle(ActionEvent e) {
        refresh();
    }

    private void refresh() {

        Object selectedItem = component.getComboBox_datasource().getSelectedItem();
        if (Objects.isNull(selectedItem)){
            if (component.getComboBox_datasource().getItemCount() == 0){
                AlertUtils.msg(component, "请先添加数据源！");
            }else {
                AlertUtils.msg(component, "请先选择数据源！");
            }
            return;
        }

        // 激活当前数据源
        DataSourceConfig dataSourceConfig = (DataSourceConfig) selectedItem;
        DataSourceManager.setActive((DataSourceConfig) selectedItem);
        // 重新刷新数据源列表，显示颜色
        component.refreshComboBox_datasource();

        // 优化用户体验
        // 连接的数据库不是系统的，则自动选择该数据库
        if (!StringUtils.isEmpty(dataSourceConfig.getDatabase())
                && !ServiceManager.getDatabaseService().isBuiltInDatabase(dataSourceConfig)){
            // 激活当前数据源的数据库
            if (Objects.equals(dataSourceConfig.getDriver(), DatabaseDriverEnum.Oracle.name())){
                DatabaseManager.setActive(dataSourceConfig.getUsername());
            }else {
                DatabaseManager.setActive(dataSourceConfig.getDatabase());
            }
            // 刷新并且选择这个数据库
            component.refreshComboBox_database();
            component.getComboBox_database().setSelectedItem(dataSourceConfig.getDatabase());
            // 刷新数据库表列表
            component.refreshTable_db_tables();
        } else {
            // 刷新数据库列表
            component.refreshComboBox_database();
            // 重连后，重置默认数据库
            DatabaseManager.cleanActive();
            // 刷新数据库表列表
            component.refreshTable_db_tables();
        }
    }
}
