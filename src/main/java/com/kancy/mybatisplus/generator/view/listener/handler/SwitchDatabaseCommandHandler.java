package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;
import java.util.Objects;

/**
 * SwitchDatabaseCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 16:12
 */
@CommandAction(Command.SWITCH_DATABASE)
public class SwitchDatabaseCommandHandler extends ActionEventCommandHandler<Main> {

    public SwitchDatabaseCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(ActionEvent event) {
        Object selectedItem = component.getComboBox_database().getSelectedItem();
        if (Objects.nonNull(selectedItem)){
            DatabaseManager.setActive(String.valueOf(selectedItem));
        }
        // 刷新表格数据
        component.refreshTable_db_tables();
    }
}
