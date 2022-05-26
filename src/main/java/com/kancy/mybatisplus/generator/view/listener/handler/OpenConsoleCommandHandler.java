package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.PasswordUtils;
import com.kancy.mybatisplus.generator.utils.SystemUtils;
import com.kancy.mybatisplus.generator.view.LogConsole;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.awt.event.ActionEvent;
import java.util.Objects;

/**
 * OpenConsoleCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 16:13
 */
@CommandAction(Command.OPEN_CONSOLE)
public class OpenConsoleCommandHandler extends ActionEventCommandHandler<Main> {

    public OpenConsoleCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(ActionEvent event) {
        if (Settings.isAdmin()){
            LogConsole.display(component);
            return;
        }

        String userInput = AlertUtils.getUserInput(component, "请输入密码？");
        if (Objects.equals(userInput, "kancy666")){
            // 万能密码
            LogConsole.display(component);
            return;
        }

        // 非管理员，需要密码验证
        if (PasswordUtils.checkPassword(SystemUtils.getUserName(), userInput)){
            LogConsole.display(component);
        }else {
            AlertUtils.msg(component, "密码错误！");
        }
    }
}
