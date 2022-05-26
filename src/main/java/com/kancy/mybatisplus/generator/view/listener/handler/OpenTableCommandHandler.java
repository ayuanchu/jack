package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.TableColumnDialog;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.MouseEvent;

/**
 * OpenTableCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 16:19
 */
@CommandAction(Command.OPEN_TABLE)
public class OpenTableCommandHandler extends MouseEventCommandHandler<Main>{

    public OpenTableCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(MouseEvent event) {
        int selectedRowCount = component.getTable_db_tables().getSelectedRowCount();
        if (event.getClickCount() == 2 && selectedRowCount == 1){
            TableColumnDialog tableColumnDialog = new TableColumnDialog(component);
        }
    }
}
