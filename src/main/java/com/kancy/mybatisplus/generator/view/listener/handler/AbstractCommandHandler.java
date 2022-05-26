package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.utils.ExceptionUtils;
import com.kancy.mybatisplus.generator.view.listener.CommandHandler;

import java.awt.*;

/**
 * AbstractCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 15:43
 */
public abstract class AbstractCommandHandler<E, C extends Component> implements CommandHandler<E> {

    protected final C component;

    public AbstractCommandHandler(C component) {
        this.component = component;
    }

    @Override
    public void handleEvent(E event) {
        ExceptionUtils.exceptionListener(() -> handle(event), component);
    }

    /**
     * handle a command
     * @param event
     */
    protected abstract void handle(E event);
}
