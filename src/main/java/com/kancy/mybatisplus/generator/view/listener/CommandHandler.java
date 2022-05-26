package com.kancy.mybatisplus.generator.view.listener;

/**
 * CommandHandler
 *
 * @author kancy
 * @date 2020/6/13 15:26
 */
public interface CommandHandler<E> {
    /**
     * 处理事件
     * @param event
     */
    void handleEvent(E event);
}
