package com.kancy.mybatisplus.generator.view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * CustomTableCellRenderer
 *
 * @author kancy
 * @date 2020/6/12 22:25
 */
public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);

        if(row >0&&column>0){
            cell.setBackground(Color.GREEN);
        }

        return cell;
    }
}
