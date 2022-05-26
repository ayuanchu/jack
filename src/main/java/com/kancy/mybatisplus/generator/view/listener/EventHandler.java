package com.kancy.mybatisplus.generator.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

/**
 * EventHandler
 *
 * @author kancy
 * @date 2020/6/13 15:24
 */
public interface EventHandler extends ActionListener {
    /**
     * 处理
     * @param command
     */
    void handle(String command);

    /**
     * 处理事件
     * @param command
     * @param eventObject
     */
    void handle(String command, EventObject eventObject);

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    default void actionPerformed(ActionEvent e) {
        handle(e.getActionCommand(), e);
    }
}
