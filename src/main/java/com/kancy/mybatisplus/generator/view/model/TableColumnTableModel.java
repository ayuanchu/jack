package com.kancy.mybatisplus.generator.view.model;

import com.kancy.mybatisplus.generator.exception.ParamException;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.manager.TableColumnCacheManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.utils.HumpUtils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * TableColumnTableModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 10:07
 **/

public class TableColumnTableModel extends DefaultTableModel implements TableModelListener{

    private static boolean[] columnEditable = new boolean[] {
            false, false, false, true, true, true, true
    };

    private static String[] tableHeader = new String[] {
            "\u5e8f\u53f7", "\u6570\u636e\u5e93\u5b57\u6bb5", "\u6570\u636e\u5e93\u7c7b\u578b", "\u5b57\u6bb5", "\u7c7b\u578b", "\u6ce8\u91ca", "\u662f\u5426\u542f\u7528"
    };

    private String tableName;

    private Object[][] backupData;

    private List<ColumnInfo> columnInfos;

    private Set<String> mayChangeColumnPositionSet = new HashSet<>();

    private Set<String> hasColumnCacheKeySet = new HashSet<>();

    public TableColumnTableModel() {
        this(null);
    }

    public TableColumnTableModel(String tableName) {
        this.tableName = tableName;
        init();
    }

    private void init() {
        columnInfos = DatabaseManager.getActiveDatabaseTableColumns(tableName);
        Object[][] data = new Object[columnInfos.size()][7];
        for (int i = 0; i < columnInfos.size(); i++) {
            ColumnInfo columnInfo = columnInfos.get(i);
            data[i][0] = String.format("  %s", i+1);
            data[i][1] = columnInfo.getColumnName();
            data[i][2] = columnInfo.getColumnType();
            data[i][3] = getFromCache(i, 3, HumpUtils.lineToHump(columnInfo.getColumnName()));
            data[i][4] = getFromCache(i, 4, ServiceManager.getDatabaseService().toJavaDataType(columnInfo));
            data[i][5] = getFromCache(i, 5, columnInfo.getColumnComment());
            data[i][6] = Objects.equals(getFromCache(i, 6, "Y"), "Y");
        }

        // 备份数据
        backupData = new Object[data.length][7];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 7; j++) {
                backupData[i][j] = data[i][j];
            }
        }

        this.setDataVector(data, tableHeader);
        this.addTableModelListener(this);
    }

    private Object getFromCache(int row,int col, Object defaultValue) {
        String columnPropertyKey = getColumnPropertyKey(row, col);
        Object o = TableColumnCacheManager.get(columnPropertyKey);
        if (Objects.isNull(o)){
            return defaultValue;
        }
        // 这个字段值是从缓存来的
        hasColumnCacheKeySet.add(columnPropertyKey);
        return o;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnEditable[columnIndex];
    }

    /**
     * This fine grain notification tells listeners the exact range
     * of cells, rows, or columns that changed.
     *
     * @param e a {@code TableModelEvent} to notify listener that a table model
     *          has changed
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int col = e.getColumn();
        int row = e.getFirstRow();
        mayChangeColumnPositionSet.add(String.format("%s@%s", row, col));
    }

    public void updateAllColumnChangeValue(){
        for (String rowAndCol : mayChangeColumnPositionSet) {
            String[] rowAndColArray = rowAndCol.split("@", 2);
            int row = Integer.parseInt(rowAndColArray[0]);
            int col = Integer.parseInt(rowAndColArray[1]);

            // 比较现在的值和以前的值
            Object originalValue = backupData[row][col];
            Object updateValue = getValueAt(row, col);

            if (Objects.isNull(updateValue) || updateValue.toString().isEmpty()
                    || Objects.equals(originalValue, updateValue)){
                continue;
            }

            // 更新
            String key = getColumnPropertyKey(row, col);
            if (col == 6){
                 TableColumnCacheManager.put(key, (boolean)updateValue ? "Y" : "N");
            }else {
                TableColumnCacheManager.put(key, updateValue);
            }
        }
    }

    private String getColumnPropertyKey(int row, int col) {
        ColumnInfo columnInfo = columnInfos.get(row);
        String columnName = columnInfo.getColumnName();
        String columnPropertyKey = TableColumnCacheManager.getColumnPropertyKey(tableName, columnName, getPropertyName(col).toString());
        return columnPropertyKey;
    }

    private Object getPropertyName(int col) {
        switch (col){
            case 3:return "fieldName";
            case 4:return "javaDataType";
            case 5:return "fieldComment";
            case 6:return "enable";
        }
        throw new ParamException("内部参数错误");
    }

    public Set<String> getHasColumnCacheKeySet() {
        return hasColumnCacheKeySet;
    }

    public void clearCache() {
        for (String key : hasColumnCacheKeySet) {
            TableColumnCacheManager.remove(key);
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setColumnModel(TableColumnModel cm) {
        // 水平居中
//        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
//        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
//        cm.getColumn(0).setCellRenderer(cellRenderer);
//        cm.getColumn(6).setCellRenderer(cellRenderer);
        cm.getColumn(0).setMaxWidth(40);
        cm.getColumn(6).setMaxWidth(60);
        cm.getColumn(4).setCellEditor(new DefaultCellEditor(
                new JComboBox(new DefaultComboBoxModel(new String[] {
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
                }))));
        cm.getColumn(6).setCellEditor(new DefaultCellEditor(new JCheckBox()));
    }

}
