/*
 * Created by JFormDesigner on Thu Jul 02 01:00:14 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.utils.PopupMenuUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kancy
 */
public class DataTypeMappingPanel extends JPanel implements ActionListener {
    public DataTypeMappingPanel() {
        initComponents();
        JPopupMenu jPopupMenu = new JPopupMenu();
        PopupMenuUtils.addMenuItem(jPopupMenu, "添加一行", this, "addRow");
        PopupMenuUtils.addMenuItem(jPopupMenu, "删除选中行", this, "delRow");
        PopupMenuUtils.addMenuItem(jPopupMenu, "删除所有行", this, "delAllRow");

        PopupMenuUtils.addPopupMenu(table, jPopupMenu);
        PopupMenuUtils.addPopupMenu(scrollPane, jPopupMenu);
    }

    public Map<String,String> getDataTypeMapping(){
        Map<String,String> map = new LinkedHashMap<>();
        TableModel model = table.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String jdbcType = (String) model.getValueAt(i, 1);
            String javaType = (String) model.getValueAt(i, 2);
            if (!StringUtils.isEmpty(jdbcType) && !StringUtils.isEmpty(javaType)){
                map.put(jdbcType, javaType);
            }
        }
        return map;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        String actionCommand = e.getActionCommand();
        if (Objects.equals("addRow", actionCommand)){
            model.addRow(new Object[]{model.getRowCount() + 1,"","java.lang.String"});
            table.requestFocus();
            int count = table.getRowCount();
            table.setRowSelectionInterval(count-1, count-1);
            table.editCellAt(count-1, 1);
            return;
        }

        if (Objects.equals("delRow", actionCommand)){
            int[] selectedRows = table.getSelectedRows();
            if (Objects.isNull(selectedRows) || selectedRows.length < 1){
                return;
            }

            for (int i = 0; i < selectedRows.length; i++) {
                model.removeRow(selectedRows[i]-i);
            }

            return;
        }

        if (Objects.equals("delAllRow", actionCommand)){
            int rowCount = table.getRowCount();
            if (rowCount < 1){
                return;
            }

            for (int i = 0; i < rowCount; i++) {
                model.removeRow(rowCount - 1 -i);
            }

            return;
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane = new JScrollPane();
        table = new JTable();

        //======== this ========
        setLayout(new BorderLayout());

        //======== scrollPane ========
        {

            //---- table ----
            table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "\u5e8f\u53f7", "\u6570\u636e\u5e93\u7c7b\u578b", "JAVA\u7c7b\u578b"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, true, true
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            {
                TableColumnModel cm = table.getColumnModel();
                cm.getColumn(0).setResizable(false);
                cm.getColumn(0).setMinWidth(40);
                cm.getColumn(0).setMaxWidth(40);
            }
            scrollPane.setViewportView(table);
        }
        add(scrollPane, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane;
    private JTable table;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public JTable getTable() {
        return table;
    }
}
