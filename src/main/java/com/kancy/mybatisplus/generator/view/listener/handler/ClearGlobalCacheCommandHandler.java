package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.TableColumnCacheManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
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
@CommandAction(Command.CLEAR_GLOBAL_CACHE)
public class ClearGlobalCacheCommandHandler extends ActionEventCommandHandler<Main> {

    public ClearGlobalCacheCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handle(ActionEvent event) {
        if (AlertUtils.yesOrNo(component, "确认需要清除所有缓存数据吗？")){
            TableColumnCacheManager.clear();
            AlertUtils.msg(component, "清除成功！");
        }

        // 刷新UI数据
        if (!StringUtils.isEmpty(DatabaseManager.getActive())){
            SwitchDatabaseCommandHandler handler = new SwitchDatabaseCommandHandler(component);
            handler.handleEvent(event);
        }
    }
}
