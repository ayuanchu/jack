/*
 * Created by JFormDesigner on Tue Jun 09 14:48:58 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.exception.ParamException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.*;
import com.kancy.mybatisplus.generator.service.TableInfo;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.ExceptionUtils;
import com.kancy.mybatisplus.generator.utils.HumpUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.view.model.TableColumnTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author kancy
 */
public class TableColumnDialog extends JDialog {

    private final Main main;

    private String tableName;

    private String originalTableRemark;

    public TableColumnDialog(Main main) {
        super(main);
        this.main = main;

        // 安全的进行初始化
        ExceptionUtils.exceptionListener(() -> {
            initComponents();
            setModal(true);
            setBounds(new Rectangle(700, 400));
            setLocationRelativeTo(getOwner());
            initDefaultValue();
            setVisible(true);
        });
    }

    private void initDefaultValue() {
        int selectedRowCount = main.getTable_db_tables().getSelectedRowCount();
        if (selectedRowCount == 1){
            int selectedRow = main.getTable_db_tables().getSelectedRow();
            String tableName = main.getTable_db_tables().getModel().getValueAt(selectedRow, 1).toString();
            setTableName(tableName);

            // 设置标题
            setTitle(String.format("%s %s",tableName, getTitle()));

            // 设置实体名
            String className = GlobalConfigManager.get().isTablePrefixEnabled() ?
                    HumpUtils.lineToHump(tableName, GlobalConfigManager.get().getTablePrefix()):
                    HumpUtils.lineToHump(tableName);
            String entityName = StringUtils.capitalize(className);
            textField_entity_name.setText(entityName);

            // 设置表注释
            refreshTableRemark(tableName);
            // 设置字段
            refreshTableColumnTable(tableName);
            // 显示
            showClearCacheButton();
        }
    }

    private void button_okActionPerformed(ActionEvent e) {
        try {
            // 查询数据库,获取表注释
            refreshOriginalTableRemark();
            doSave();
            AlertUtils.msg(getOwner(), "保存成功！");
            this.setVisible(false);
            this.dispose();
        } catch (Exception ex) {
            if (ex instanceof ParamException){
                AlertUtils.msg(getOwner(), ex.getMessage());
                return;
            }
            Log.error(ex.getMessage(), ex);
            AlertUtils.msg(this, "保存失败！");
        }
    }

    private void refreshOriginalTableRemark() {
        TableInfo table = ServiceManager.getDatabaseService()
                .getTable(DataSourceManager.getActive(), DatabaseManager.getActive(), tableName);
        if (Objects.nonNull(table)){
            setOriginalTableRemark(table.getTableComment());
        }else {
            setOriginalTableRemark(textField_table_remark.getText());
        }
    }

    private void doSave() {
        // 看表注释是否发生变化
        String tableRemark = textField_table_remark.getText();
        if (!StringUtils.isEmpty(tableRemark) && !Objects.equals(tableRemark, originalTableRemark)){
            // 缓存自定义的表注释
            TableColumnCacheManager.putTableRemark(tableName, tableRemark);
            int selectedRow = main.getTable_db_tables().getSelectedRow();
            main.getTable_db_tables().getModel().setValueAt(tableRemark, selectedRow, 2);
        }

        // 缓存自定义的字段信息
        TableColumnTableModel model = (TableColumnTableModel) table.getModel();
        model.updateAllColumnChangeValue();
    }

    private void button_clear_cacheActionPerformed(ActionEvent e) {
        // 查询数据库,获取表注释
        refreshOriginalTableRemark();

        // 清除字段缓存
        TableColumnTableModel model = (TableColumnTableModel) table.getModel();
        model.clearCache();

        // 清除表缓存
        TableColumnCacheManager.removeTableRemark(model.getTableName());
        int selectedRow = main.getTable_db_tables().getSelectedRow();
        textField_table_remark.setText(originalTableRemark);
        main.getTable_db_tables().getModel().setValueAt(textField_table_remark.getText(), selectedRow, 2);

        // 刷新模型
        refreshTableColumnTable(model.getTableName());
        // 显示
        showClearCacheButton();
    }

    private void refreshTableRemark(String tableName) {
        int selectedRow = main.getTable_db_tables().getSelectedRow();
        String tableRemark = main.getTable_db_tables().getModel().getValueAt(selectedRow, 2).toString();

        textField_table_remark.setText(TableColumnCacheManager.getTableRemark(tableName, tableRemark));
        main.getTable_db_tables().getModel().setValueAt(textField_table_remark.getText(), selectedRow, 2);
    }

    private void refreshTableColumnTable(String tableName) {
        TableColumnTableModel newModel = new TableColumnTableModel(tableName);
        table.setModel(newModel);
        newModel.setColumnModel(table.getColumnModel());
    }


    private void showClearCacheButton() {
        TableColumnTableModel model = (TableColumnTableModel) table.getModel();

        boolean hasTableRemarkCache = TableColumnCacheManager.hasTableRemark(tableName);
        boolean hasColumnCache = !model.getHasColumnCacheKeySet().isEmpty();

        if (hasTableRemarkCache){
            textField_table_remark.setToolTipText(String.format("我来自缓存，我以前的名字可能是 “%s” !",
                    Objects.isNull(originalTableRemark) ? textField_table_remark.getText() : originalTableRemark));
        }else {
            textField_table_remark.setToolTipText(null);
        }

        // 清除缓存按钮
        button_clear_cache.setVisible(hasTableRemarkCache || hasColumnCache);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        JPanel panel = new JPanel();
        JLabel label_entity_name = new JLabel();
        textField_entity_name = new JTextField();
        JLabel label_table_remark = new JLabel();
        textField_table_remark = new JTextField();
        button_clear_cache = new JButton();
        JButton button_ok = new JButton();
        JScrollPane scrollPane = new JScrollPane();
        table = new JTable();

        //======== this ========
        setTitle(bundle.getString("table.dialog.this.title"));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel ========
        {
            panel.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[265,fill]" +
                "[36,fill]" +
                "[265,fill]" +
                "[fill]" +
                "[48,fill]",
                // rows
                "[]"));

            //---- label_entity_name ----
            label_entity_name.setText(bundle.getString("table.dialog.label_entity_name.text"));
            panel.add(label_entity_name, "cell 0 0,alignx right,growx 0");

            //---- textField_entity_name ----
            textField_entity_name.setEditable(false);
            panel.add(textField_entity_name, "cell 1 0");

            //---- label_table_remark ----
            label_table_remark.setText(bundle.getString("table.dialog.label_table_remark.text"));
            panel.add(label_table_remark, "cell 2 0");
            panel.add(textField_table_remark, "cell 3 0,dock center");

            //---- button_clear_cache ----
            button_clear_cache.setText(bundle.getString("table.dialog.button_clear_cache.text"));
            button_clear_cache.addActionListener(e -> button_clear_cacheActionPerformed(e));
            panel.add(button_clear_cache, "cell 4 0");

            //---- button_ok ----
            button_ok.setText(bundle.getString("table.dialog.button_ok.text"));
            button_ok.addActionListener(e -> button_okActionPerformed(e));
            panel.add(button_ok, "cell 5 0,aligny center,grow 100 0");
        }
        contentPane.add(panel, BorderLayout.NORTH);

        //======== scrollPane ========
        {

            //---- table ----
            table.setModel(new DefaultTableModel());
            table.setPreferredScrollableViewportSize(new Dimension(450, 300));
            table.setToolTipText(bundle.getString("table.dialog.table.toolTipText"));
            scrollPane.setViewportView(table);
        }
        contentPane.add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of data initialization  //GEN-END:initComponents
    }

    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
        // JFormDesigner - End of data i18n initialization  //GEN-END:initI18n
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTextField textField_entity_name;
    private JTextField textField_table_remark;
    private JButton button_clear_cache;
    private JTable table;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setOriginalTableRemark(String originalTableRemark) {
        this.originalTableRemark = originalTableRemark;
    }
}
