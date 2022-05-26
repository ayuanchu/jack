/*
 * Created by JFormDesigner on Tue Jun 09 14:32:58 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.exception.ParamException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.ExceptionUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * @author kancy
 */
public class DataSourceDialog extends JDialog {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private DataSourceConfig dataSourceConfig;

    public DataSourceDialog(Window owner) {
        this(owner, new DataSourceConfig());
    }

    public DataSourceDialog(Window owner, DataSourceConfig dataSourceConfig) {
        super(owner);
        this.dataSourceConfig = dataSourceConfig;

        // 安全的进行初始化
        ExceptionUtils.exceptionListener(() -> {
            initComponents();
            setModal(true);
            setBounds(new Rectangle(500, 380));
            setLocationRelativeTo(getOwner());
            initDefaultValue(dataSourceConfig);
            addTitle();
            setVisible(true);
        }, this);
    }

    private void addTitle() {
        setTitle(Objects.isNull(dataSourceConfig.getName()) ? "添加数据源" : "修改数据源");
    }

    private void initDefaultValue(DataSourceConfig dataSourceConfig) {
        if (Objects.isNull(dataSourceConfig.getName())){
            // 数据库类型
            DatabaseDriverEnum[] driverEnums = DatabaseDriverEnum.values();
            for (DatabaseDriverEnum value : driverEnums) {
                try {
                    Class.forName(value.getDriverClassName());
                    comboBox_driver.addItem(value);
                    comboBox_driver2.addItem(value);
                } catch (ClassNotFoundException e) {
                }
            }
        }else {
            comboBox_driver.addItem(DatabaseDriverEnum.valueOf(dataSourceConfig.getDriver()));
            comboBox_driver2.addItem(DatabaseDriverEnum.valueOf(dataSourceConfig.getDriver()));
        }

        textField_ds_name.setText(dataSourceConfig.getName());
        textField_ds_remark.setText(dataSourceConfig.getRemark());
        textArea_jdbc_url.setText(dataSourceConfig.getJdbcUrl());
        textField_user_name.setText(dataSourceConfig.getUsername());
        passwordField_password.setText(dataSourceConfig.getPassword());

        textField_host.setText(dataSourceConfig.getHost());
        textField_port.setText(dataSourceConfig.getPort());
        textField_user_name2.setText(dataSourceConfig.getUsername());
        passwordField_password2.setText(dataSourceConfig.getPassword());

        // 如果是修改，把所有库都查出来
        try {
            refreshComboBoxDatabaseNames(dataSourceConfig);
        } catch (Exception e) {
            Log.debug("refreshComboBoxDatabaseNames 失败 ： %s！", e.getMessage());
        }
        comboBox_database_name.setSelectedItem(dataSourceConfig.getDatabase());
    }

    private void refreshComboBoxDatabaseNames(DataSourceConfig dataSourceConfig) {
        if (Objects.equals(comboBox_driver2.getSelectedItem(), DatabaseDriverEnum.Oracle)){
            return;
        }

        if (Objects.nonNull(dataSourceConfig.getName())){
            try {
                Future<List<String>> future = executorService.submit(new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return ServiceManager.getDatabaseService().getDatabaseNames(dataSourceConfig);
                    }
                });
                // 最多耗费500毫秒
                List<String> databaseNames = future.get(500, TimeUnit.MILLISECONDS);
                Object selectedItem = comboBox_database_name.getSelectedItem();
                comboBox_database_name.removeAllItems();
                for (String databaseName : databaseNames) {
                    comboBox_database_name.addItem(databaseName);
                }
                if (Objects.nonNull(selectedItem)){
                    comboBox_database_name.insertItemAt(selectedItem, 0);
                    comboBox_database_name.setSelectedIndex(0);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private DataSourceConfig testConnection(boolean refresh) {
        DataSourceConfig config = new DataSourceConfig();
        int selectedIndex = tabbedPane.getSelectedIndex();

        config.setName(textField_ds_name.getText());
        config.setRemark(textField_ds_remark.getText());
        config.setDriver(comboBox_driver.getSelectedItem().toString());
        DatabaseDriverEnum databaseDriverEnum = null;
        if (selectedIndex == 0){
            databaseDriverEnum = (DatabaseDriverEnum) comboBox_driver2.getSelectedItem();
            config.setDriver(databaseDriverEnum.name());
            config.setDriverClassName(databaseDriverEnum.getDriverClassName());
            config.setUsername(textField_user_name2.getText());
            config.setPassword(passwordField_password2.getText());
            config.setHost(textField_host.getText());
            config.setPort(textField_port.getText());
            config.setDatabase(comboBox_database_name.getSelectedItem().toString());
            // 拼接jdbcUrl
            if (Objects.equals(databaseDriverEnum, DatabaseDriverEnum.MySQL)){
                config.setJdbcUrl(String.format(databaseDriverEnum.getJdbcUrlFormat(),
                        config.getHost(), config.getPort(), config.getDatabase()));
            }else if(Objects.equals(databaseDriverEnum, DatabaseDriverEnum.Oracle)){
                config.setJdbcUrl(String.format(databaseDriverEnum.getJdbcUrlFormat(),
                        config.getHost(), config.getPort(), config.getDatabase()));
            }
        }

        if (selectedIndex == 1){
            databaseDriverEnum = (DatabaseDriverEnum) comboBox_driver.getSelectedItem();
            config.setDriver(databaseDriverEnum.name());
            config.setDriverClassName(databaseDriverEnum.getDriverClassName());
            config.setUsername(textField_user_name.getText());
            config.setPassword(passwordField_password.getText());
            config.setJdbcUrl(textArea_jdbc_url.getText());

            // 解析jdbcUrl
            config.update(config);
        }

        Future<Boolean> future = executorService.submit(() -> DatabaseManager.testConnect(config));

        // 最多耗费3000毫秒
        boolean testConnection;
        try {
            testConnection = future.get(3000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new AlertException("测试连接失败！", e);
        }

        if (testConnection && refresh){
            dataSourceConfig.update(config);
        }
        return config;
    }

    private void button_ds_test_connectActionPerformed(ActionEvent e) {
        ExceptionUtils.exceptionListener(() -> {
            DataSourceConfig dataSourceConfig = testConnection(false);
            if(ServiceManager.getDatabaseService().isBuiltInDatabase(dataSourceConfig)){
                try {
                    if (tabbedPane.getSelectedIndex() == 0){
                        // 简易配置面板时，刷新数据库下拉框
                        refreshComboBoxDatabaseNames(dataSourceConfig);
                    }
                    AlertUtils.msg(DataSourceDialog.this, "<html>连接成功！<br><br><font color='blue'>" +
                            "你使用的是内置数据库："+ dataSourceConfig.getDatabase()+"<br>选择非系统数据库会更好哦!</font></html>");
                    return;
                } catch (Exception ex) {
                    Log.debug("refreshComboBoxDatabaseNames 失败 ： %s！", ex.getMessage());
                }
            }
            AlertUtils.msg(DataSourceDialog.this, "连接成功！");
        },this);
    }

    private void button_ds_saveActionPerformed(ActionEvent e) {
        ExceptionUtils.exceptionListener(() -> {
            // 数据源名称为空
            String dsName = textField_ds_name.getText();
            if (StringUtils.isEmpty(dsName)){
                throw new ParamException("数据源名称不能为空！");
            }

            // 名称重复,弹药排除更新
            if (DataSourceManager.getDataSourceConfigs()
                    .stream().anyMatch(dsc -> (Objects.equals(dsc.getName(), dsName)
                            && !Objects.equals(dsc.getId(), getDataSourceConfig().getId())))){
                throw new ParamException("数据源名称已添加，请更换名称！");
            }
            testConnection(true);
            AlertUtils.msg(DataSourceDialog.this, "保存成功！");
            DataSourceDialog.this.setVisible(false);
            DataSourceDialog.this.dispose();
        },this);
    }

    private void comboBox_driver2ActionPerformed(ActionEvent e) {
        if (Objects.isNull(dataSourceConfig.getName())){
            textArea_jdbc_url.setText(DataSourceConfig.getDefaultJdbcUrl(comboBox_driver2.getSelectedItem().toString()));

            if (Objects.equals(comboBox_driver2.getSelectedItem(), DatabaseDriverEnum.Oracle)){
                label_database_name.setText("实例名：");
                textField_port.setText("1521");
                comboBox_database_name.removeAllItems();
                comboBox_database_name.addItem("xe");
                comboBox_database_name.addItem("orcl");
            }
            if (Objects.equals(comboBox_driver2.getSelectedItem(), DatabaseDriverEnum.MySQL)){
                label_database_name.setText("数据库：");
                comboBox_database_name.removeAllItems();
                comboBox_database_name.setSelectedItem(dataSourceConfig.getDatabase());
            }
        }
    }

    private void comboBox_driverActionPerformed(ActionEvent e) {
        if (Objects.isNull(dataSourceConfig.getName())){
            textArea_jdbc_url.setText(DataSourceConfig.getDefaultJdbcUrl(comboBox_driver.getSelectedItem().toString()));
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        JPanel panel_ds_head = new JPanel();
        label_ds_name = new JLabel();
        textField_ds_name = new JTextField();
        button_ds_test_connect = new JButton();
        label_ds_remark = new JLabel();
        textField_ds_remark = new JTextField();
        button_ds_save = new JButton();
        tabbedPane = new JTabbedPane();
        JPanel panel_ds_config2 = new JPanel();
        label_driver2 = new JLabel();
        comboBox_driver2 = new JComboBox();
        label_jdbc_url2 = new JLabel();
        textField_host = new JTextField();
        label_port = new JLabel();
        textField_port = new JTextField();
        label_user_name2 = new JLabel();
        textField_user_name2 = new JTextField();
        label_password2 = new JLabel();
        passwordField_password2 = new JPasswordField();
        label_database_name = new JLabel();
        comboBox_database_name = new JComboBox();
        JPanel panel_ds_config = new JPanel();
        label_driver = new JLabel();
        comboBox_driver = new JComboBox();
        label_jdbc_url = new JLabel();
        textArea_jdbc_url = new JTextArea();
        label_user_name = new JLabel();
        textField_user_name = new JTextField();
        label_password = new JLabel();
        passwordField_password = new JPasswordField();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel_ds_head ========
        {
            panel_ds_head.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[374,fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]"));
            panel_ds_head.add(label_ds_name, "cell 0 0");
            panel_ds_head.add(textField_ds_name, "cell 1 0");

            //---- button_ds_test_connect ----
            button_ds_test_connect.addActionListener(e -> button_ds_test_connectActionPerformed(e));
            panel_ds_head.add(button_ds_test_connect, "cell 2 0");
            panel_ds_head.add(label_ds_remark, "cell 0 1");
            panel_ds_head.add(textField_ds_remark, "cell 1 1");

            //---- button_ds_save ----
            button_ds_save.addActionListener(e -> button_ds_saveActionPerformed(e));
            panel_ds_head.add(button_ds_save, "cell 2 1");
        }
        contentPane.add(panel_ds_head, BorderLayout.NORTH);

        //======== tabbedPane ========
        {

            //======== panel_ds_config2 ========
            {
                panel_ds_config2.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[185,fill]" +
                    "[27,fill]" +
                    "[52,fill]" +
                    "[181,fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[32]" +
                    "[]" +
                    "[]" +
                    "[]"));
                panel_ds_config2.add(label_driver2, "cell 0 1,alignx right,growx 0");

                //---- comboBox_driver2 ----
                comboBox_driver2.addActionListener(e -> comboBox_driver2ActionPerformed(e));
                panel_ds_config2.add(comboBox_driver2, "cell 1 1 4 1");
                panel_ds_config2.add(label_jdbc_url2, "cell 0 2,alignx right,growx 0");
                panel_ds_config2.add(textField_host, "cell 1 2,growx");
                panel_ds_config2.add(label_port, "cell 3 2,alignx trailing,growx 0");
                panel_ds_config2.add(textField_port, "cell 4 2,growx");
                panel_ds_config2.add(label_user_name2, "cell 0 3,alignx right,growx 0");
                panel_ds_config2.add(textField_user_name2, "cell 1 3");
                panel_ds_config2.add(label_password2, "cell 3 3,alignx trailing,growx 0");
                panel_ds_config2.add(passwordField_password2, "cell 4 3");
                panel_ds_config2.add(label_database_name, "cell 0 4");

                //---- comboBox_database_name ----
                comboBox_database_name.setEditable(true);
                panel_ds_config2.add(comboBox_database_name, "cell 1 4");
            }
            tabbedPane.addTab(bundle.getString("datasource.dialog.panel_ds_config2.title"), panel_ds_config2);

            //======== panel_ds_config ========
            {
                panel_ds_config.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[448,fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[32]" +
                    "[]" +
                    "[]" +
                    "[]"));
                panel_ds_config.add(label_driver, "cell 0 1,alignx right,growx 0");

                //---- comboBox_driver ----
                comboBox_driver.addActionListener(e -> comboBox_driverActionPerformed(e));
                panel_ds_config.add(comboBox_driver, "cell 1 1,growx");
                panel_ds_config.add(label_jdbc_url, "cell 0 2,align right top,grow 0 0");

                //---- textArea_jdbc_url ----
                textArea_jdbc_url.setLineWrap(true);
                textArea_jdbc_url.setBorder(new TitledBorder("jdbcurl"));
                textArea_jdbc_url.setMinimumSize(new Dimension(385, 70));
                textArea_jdbc_url.setRows(2);
                panel_ds_config.add(textArea_jdbc_url, "cell 1 2,growx");
                panel_ds_config.add(label_user_name, "cell 0 3,alignx right,growx 0");
                panel_ds_config.add(textField_user_name, "cell 1 3,growx");
                panel_ds_config.add(label_password, "cell 0 4,alignx right,growx 0");
                panel_ds_config.add(passwordField_password, "cell 1 4,growx");
            }
            tabbedPane.addTab(bundle.getString("datasource.dialog.panel_ds_config.tab.title"), panel_ds_config);
        }
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        initComponentsI18n();

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of data initialization  //GEN-END:initComponents
    }

    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        setTitle(bundle.getString("datasource.dialog.this.title"));
        label_ds_name.setText(bundle.getString("datasource.dialog.label_ds_name.text"));
        button_ds_test_connect.setText(bundle.getString("datasource.dialog.button_ds_test_connect.text"));
        label_ds_remark.setText(bundle.getString("datasource.dialog.label_ds_remark.text"));
        button_ds_save.setText(bundle.getString("datasource.dialog.button_ds_save.text"));
        label_driver2.setText(bundle.getString("datasource.dialog.label_driver2.text"));
        label_jdbc_url2.setText(bundle.getString("datasource.dialog.label_jdbc_url2.text"));
        label_port.setText(bundle.getString("datasource.dialog.label_port.text"));
        label_user_name2.setText(bundle.getString("datasource.dialog.label_user_name2.text"));
        label_password2.setText(bundle.getString("datasource.dialog.label_password2.text"));
        label_database_name.setText(bundle.getString("datasource.dialog.label_database_name.text"));
        tabbedPane.setTitleAt(0, bundle.getString("datasource.dialog.panel_ds_config2.title"));
        label_driver.setText(bundle.getString("datasource.dialog.label_driver.text"));
        label_jdbc_url.setText(bundle.getString("datasource.dialog.label_jdbc_url.text"));
        textArea_jdbc_url.setToolTipText(bundle.getString("datasource.dialog.textArea_jdbc_url.toolTipText"));
        label_user_name.setText(bundle.getString("datasource.dialog.label_user_name.text"));
        label_password.setText(bundle.getString("datasource.dialog.label_password.text"));
        tabbedPane.setTitleAt(1, bundle.getString("datasource.dialog.panel_ds_config.tab.title"));
        // JFormDesigner - End of data i18n initialization  //GEN-END:initI18n
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label_ds_name;
    private JTextField textField_ds_name;
    private JButton button_ds_test_connect;
    private JLabel label_ds_remark;
    private JTextField textField_ds_remark;
    private JButton button_ds_save;
    private JTabbedPane tabbedPane;
    private JLabel label_driver2;
    private JComboBox comboBox_driver2;
    private JLabel label_jdbc_url2;
    private JTextField textField_host;
    private JLabel label_port;
    private JTextField textField_port;
    private JLabel label_user_name2;
    private JTextField textField_user_name2;
    private JLabel label_password2;
    private JPasswordField passwordField_password2;
    private JLabel label_database_name;
    private JComboBox comboBox_database_name;
    private JLabel label_driver;
    private JComboBox comboBox_driver;
    private JLabel label_jdbc_url;
    private JTextArea textArea_jdbc_url;
    private JLabel label_user_name;
    private JTextField textField_user_name;
    private JLabel label_password;
    private JPasswordField passwordField_password;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }
}
