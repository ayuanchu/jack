package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;
import com.kancy.mybatisplus.generator.view.listener.CommandHandler;


/**
 * DefaultCommandHandler
 *
 * @author kancy
 * @date 2020/6/14 13:34
 */
@CommandAction(Command.DEFAULT)
public class DefaultCommandHandler implements CommandHandler {

    /**
     * 处理事件
     *
     * @param event
     */
    @Override
    public void handleEvent(Object event) {
        AlertUtils.msg("该功能暂不支持！");
    }
}
