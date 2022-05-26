package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.view.GlobalConfigDialog;
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
@CommandAction(Command.GLOBAL_CONFIG)
public class GlobalConfigCommandHandler extends ActionEventCommandHandler<Main> {

    public GlobalConfigCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(ActionEvent event) {
        GlobalConfigDialog globalConfigDialog = new GlobalConfigDialog(component);
    }
}
