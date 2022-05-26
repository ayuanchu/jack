package com.kancy.mybatisplus.generator.view.components;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * ImagePanel
 * <p>
 *
 * @author: kancy
 * @date: 2020/7/1 18:37
 **/

public class ImagePanel extends JPanel {
    private Dimension d;
    private Image image;

    public ImagePanel(Dimension d, Image image) {
        super();
        this.d = d;
        this.image = image;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, d.width, d.height, this);
        repaint();
    }
}