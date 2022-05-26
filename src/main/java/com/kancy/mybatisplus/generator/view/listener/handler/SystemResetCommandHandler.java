package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.manager.DataStoreManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
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
@CommandAction(Command.SYSTEM_RESET)
public class SystemResetCommandHandler extends ActionEventCommandHandler<Main> {

    public SystemResetCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(ActionEvent event) {
        if (AlertUtils.yesOrNo(component, "确认需要重置系统数据吗？")){
            DataStoreManager.resetOnExit();
            AlertUtils.msg(component, "系统重置成功，重启即生效！");
        }
    }
}
