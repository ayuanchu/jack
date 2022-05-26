package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;

/**
 * TestConnectionDataSourceCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 15:42
 */
@CommandAction(Command.DEFAULT)
public class SaveDataSourceCommandHandler extends DataSourceCommandHandler {

    public SaveDataSourceCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a Action
     *
     * @param e
     */
    @Override
    protected void handle(ActionEvent e) {

    }

}
