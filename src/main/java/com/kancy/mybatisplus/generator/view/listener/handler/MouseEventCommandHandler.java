package com.kancy.mybatisplus.generator.view.listener.handler;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * MouseEventCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 15:38
 */
public abstract class MouseEventCommandHandler<C extends Component> extends AbstractCommandHandler<MouseEvent,C> {

    public MouseEventCommandHandler(C component) {
        super(component);
    }
}
