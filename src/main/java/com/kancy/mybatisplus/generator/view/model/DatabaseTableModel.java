package com.kancy.mybatisplus.generator.view.model;

import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.TableColumnCacheManager;
import com.kancy.mybatisplus.generator.service.TableInfo;
import com.kancy.mybatisplus.generator.utils.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.List;

/**
 * <p>
 * DatabaseTableModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 22:29
 **/

public class DatabaseTableModel extends DefaultTableModel {

    private static boolean[] columnEditable = new boolean[] {
            false, false, false,false
    };

    private static String[] tableHeader = new String[] {
            "\u5e8f\u53f7", "\u8868\u540d\u79f0", "\u6ce8\u91ca","创建时间"
    };

    public DatabaseTableModel() {
        init();
    }

    private void init() {
        List<TableInfo> activeDatabaseTables = DatabaseManager.getActiveDatabaseTables();
        Object[][] data = new Object[activeDatabaseTables.size()][4];
        for (int i = 0; i < activeDatabaseTables.size(); i++) {
            TableInfo tableInfo = activeDatabaseTables.get(i);
            data[i][0] = String.format("     %s", i+1);
            data[i][1] = tableInfo.getTableName();
            data[i][2] = TableColumnCacheManager.getTableRemark(tableInfo.getTableName(), tableInfo.getTableComment());
            data[i][3] = String.format("   %s", StringUtils.left(tableInfo.getCreateTime(), 19));
        }
        this.setDataVector(data, tableHeader);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnEditable[columnIndex];
    }

    public void setColumnModel(TableColumnModel cm) {
        // 水平居中
//        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
//        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
//        cm.getColumn(0).setCellRenderer(cellRenderer);
//        cm.getColumn(3).setCellRenderer(cellRenderer);

        cm.getColumn(0).setMaxWidth(60);
        cm.getColumn(0).setMinWidth(60);
        cm.getColumn(3).setMaxWidth(150);
        cm.getColumn(3).setMinWidth(150);

    }
}
