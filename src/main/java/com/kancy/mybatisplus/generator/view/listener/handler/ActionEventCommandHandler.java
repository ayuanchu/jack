package com.kancy.mybatisplus.generator.view.listener.handler;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * ActionEventCommandHandler
 *
 * @author kancy
 * @date 2020/6/13 15:37
 */
public abstract class ActionEventCommandHandler<C extends Component> extends AbstractCommandHandler<ActionEvent, C> {

    public ActionEventCommandHandler(C component) {
        super(component);
    }
}
