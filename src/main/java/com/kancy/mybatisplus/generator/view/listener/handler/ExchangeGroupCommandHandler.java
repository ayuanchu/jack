package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.view.ExchangeGroupDialog;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;

/**
 * OpenConsoleCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 16:13
 */
@CommandAction(Command.EXCHANGE_GROUP)
public class ExchangeGroupCommandHandler extends ActionEventCommandHandler<Main> {

    public ExchangeGroupCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(ActionEvent event) {
        ExchangeGroupDialog dialog = new ExchangeGroupDialog(component);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}
