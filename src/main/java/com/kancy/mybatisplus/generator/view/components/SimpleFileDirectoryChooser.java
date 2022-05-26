package com.kancy.mybatisplus.generator.view.components;

import java.awt.*;

/**
 * SimpleFileDirectoryChooser
 *
 * @author kancy
 * @date 2020/2/16 16:51
 */
public class SimpleFileDirectoryChooser extends AbstractFileChooser {

    public SimpleFileDirectoryChooser() {
        super();
        setFileSelection();
    }

    public SimpleFileDirectoryChooser(String title) {
        this();
        setTitle(title);
    }

    public SimpleFileDirectoryChooser(Component parentComponent) {
        super(parentComponent);
    }

    public SimpleFileDirectoryChooser(Component parentComponent, String title) {
        super(parentComponent);
        setTitle(title);
    }
}
