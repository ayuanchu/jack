package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.TableColumnCacheManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

/**
 * AddDataSourceCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 15:42
 */
@CommandAction(Command.DELETE_DATASOURCE)
public class DeleteDataSourceCommandHandler extends DataSourceCommandHandler {

    public DeleteDataSourceCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a Action
     *
     * @param e
     */
    @Override
    protected void handle(ActionEvent e) {
        if (DataSourceManager.isEmpty()){
            AlertUtils.msg(component, "请先添加数据源！");
            return;
        }

        Object selectedItem = component.getComboBox_datasource().getSelectedItem();
        if (Objects.isNull(selectedItem)){
            if (component.getComboBox_datasource().getItemCount() == 0){
                AlertUtils.msg(component, "请先添加数据源！");
            }else {
                AlertUtils.msg(component, "请先选择数据源！");
            }
            return;
        }

        JComboBox comboBox_datasource = component.getComboBox_datasource();
        DataSourceConfig dataSourceConfig = (DataSourceConfig) comboBox_datasource.getSelectedItem();
        if(AlertUtils.yesOrNo(component, "确定要删除[%s]数据源", dataSourceConfig.getNameAndHostAndPort())){
            DataSourceManager.removeDataSourceConfig(dataSourceConfig);
            TableColumnCacheManager.clear(dataSourceConfig);

            // 刷新组件
            component.refreshComboBox_datasource();
            if (dataSourceConfig.isActive()){
                component.refreshComboBox_database();
                component.refreshTable_db_tables();
            }
        }
    }
}
