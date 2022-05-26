package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.PasswordUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.utils.SystemUtils;
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
@CommandAction(Command.GET_CONSOLE_PASSWORD)
public class GetConsolePasswordCommandHandler extends DataSourceCommandHandler {

    public GetConsolePasswordCommandHandler(Main component) {
        super(component);
    }

    /**
     * handle a Action
     *
     * @param e
     */
    @Override
    protected void handle(ActionEvent e) {
        String userName = AlertUtils.getUserChooseInput(component, new String[]{
                SystemUtils.getAdminUserName(),SystemUtils.getUserName()
        }, "请输入用户名");
        if (!StringUtils.isEmpty(userName)){
            String password = PasswordUtils.password(userName);
            AlertUtils.msg(component, password);
            SystemUtils.setClipboardText(password);
        }
    }

}
