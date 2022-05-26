package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.SystemUtils;
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
@CommandAction(Command.SHARE)
public class ShareCommandHandler extends ActionEventCommandHandler<Main> {

    public ShareCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(ActionEvent event) {
        SystemUtils.setClipboardText(getShareInfo());
        AlertUtils.msg(component, "分享链接复制成功！");
    }

    private String getShareInfo() {
        return "Mybatisplus Code Generator分享\r\n" +
                "\r\n" +
                "插件地址：\r\n" +
                "https://plugins.jetbrains.com/plugin/14533-mybatisplus-code-generator\r\n" +
                "\r\n" +
                "下载地址：\r\n" +
                "https://gitee.com/kancy666/mybatisplus-plugin/releases\r\n" +
                "\r\n" +
                "使用说明：\r\n" +
                "https://www.cnblogs.com/kancy/p/13204950.html\r\n" +
                "https://gitee.com/kancy666/mybatisplus-plugin/blob/master/README.md\r\n";
    }
}
