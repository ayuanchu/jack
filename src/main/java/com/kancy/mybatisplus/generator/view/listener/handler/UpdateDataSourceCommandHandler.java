package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
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
@CommandAction(Command.UPDATE_DATASOURCE)
public class UpdateDataSourceCommandHandler extends DataSourceCommandHandler {

    public UpdateDataSourceCommandHandler(Main component) {
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

        // 显示DataSourceDialog
        DataSourceDialog dataSourceDialog = new DataSourceDialog(component, (DataSourceConfig) selectedItem);
        // 更新数据
        DataSourceManager.updateDataSourceConfig(dataSourceDialog.getDataSourceConfig());
        // 刷新组件
        component.refreshComboBox_datasource();
    }
}
