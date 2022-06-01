package com.kancy.mybatisplus.generator.view.model;

import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * DataTypeMappingTableModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/7/2 13:29
 **/

public class DataTypeMappingTableModel extends DefaultTableModel {

    private DatabaseDriverEnum databaseDriverEnum;

    private static boolean[] columnEditable = new boolean[] {
            false, true, true
    };

    private static String[] tableHeader = new String[] {
            "\u5e8f\u53f7", "\u6570\u636e\u5e93\u7c7b\u578b", "JAVA\u7c7b\u578b"
    };

    public DataTypeMappingTableModel(DatabaseDriverEnum databaseDriverEnum) {
        this.databaseDriverEnum = databaseDriverEnum;
        init();
    }

    private void init() {
        Map<String, String> dataTypeMapping = GlobalConfigManager.get().getDataTypeMappings().getOrDefault(databaseDriverEnum, new HashMap<>());
        Object[][] data = new Object[dataTypeMapping.size()][3];
        Set<Map.Entry<String, String>> entries = dataTypeMapping.entrySet();
        int row = 0;
        for (Map.Entry<String, String> entry : entries) {
            data[row][0] = String.format("%s", row+1);
            data[row][1] = entry.getKey();
            data[row][2] = entry.getValue();
            row++;
        }
        this.setDataVector(data, tableHeader);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnEditable[columnIndex];
    }

    public void setColumnModel(TableColumnModel cm) {
        cm.getColumn(0).setResizable(false);
        cm.getColumn(0).setMinWidth(40);
        cm.getColumn(0).setMaxWidth(40);

        JComboBox jComboBox = new JComboBox(new DefaultComboBoxModel(new String[]{
                "java.lang.String",
                "java.lang.Integer",
                "java.lang.Long",
                "java.lang.Double",
                "java.lang.Float",
                "java.lang.Boolean",
                "java.math.BigDecimal",
                "java.util.Date",
                "java.time.LocalDateTime",
                "java.time.LocalDate"
        }));
        jComboBox.setEditable(true);
        cm.getColumn(2).setCellEditor(new DefaultCellEditor(jComboBox));
    }
}
