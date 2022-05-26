/*
 * Created by JFormDesigner on Thu Jul 02 00:52:09 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.view.model.DataTypeMappingTableModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * @author kancy
 */
public class DataTypeMappingConfigDialog extends JDialog {

    private DataTypeMappingPanel mysql = new DataTypeMappingPanel();
    private DataTypeMappingPanel oracle = new DataTypeMappingPanel();

    public DataTypeMappingConfigDialog(Window owner) {
        super(owner);
        initComponents();
        setModal(true);
        setBounds(0,0,500,400);
        setLocationRelativeTo(owner);

        DataTypeMappingTableModel tableModel = new DataTypeMappingTableModel(DatabaseDriverEnum.MySQL);
        mysql.getTable().setModel(tableModel);
        tableModel.setColumnModel(mysql.getTable().getColumnModel());

        tableModel = new DataTypeMappingTableModel(DatabaseDriverEnum.Oracle);
        oracle.getTable().setModel(tableModel);
        tableModel.setColumnModel(oracle.getTable().getColumnModel());

        tabbedPane.addTab("MySQL", mysql);

        // 隐藏oracle
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            tabbedPane.addTab("Oracle", oracle);
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                tabbedPane.addTab("Oracle", oracle);
            } catch (ClassNotFoundException ex) {
            }
        }

    }

    public void saveConfig() {
        GlobalConfigManager.get().getDataTypeMappings().put(DatabaseDriverEnum.MySQL, mysql.getDataTypeMapping());
        GlobalConfigManager.get().getDataTypeMappings().put(DatabaseDriverEnum.Oracle, oracle.getDataTypeMapping());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel_top = new JPanel();
        tabbedPane = new JTabbedPane();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel_top ========
        {
            panel_top.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[15,fill]" +
                "[fill]",
                // rows
                "[]"));
        }
        contentPane.add(panel_top, BorderLayout.NORTH);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        initComponentsI18n();

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        setTitle(bundle.getString("DataTypeMappingConfigDialog.this.title"));
        // JFormDesigner - End of component i18n initialization  //GEN-END:initI18n
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel_top;
    private JTabbedPane tabbedPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
