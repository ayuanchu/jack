/*
 * Created by JFormDesigner on Tue Jun 09 13:20:24 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.ExceptionUtils;
import com.kancy.mybatisplus.generator.utils.PopupMenuUtils;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.DynamicEventHandler;
import com.kancy.mybatisplus.generator.view.listener.EventHandler;
import com.kancy.mybatisplus.generator.view.listener.handler.*;
import com.kancy.mybatisplus.generator.view.model.*;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * @author kancy
 */
@Getter
public class Main extends JFrame {

    private EventHandler eventHandler = new DynamicEventHandler();

    public Main() {
        // 设置系统提示默认的中心位置
        AlertUtils.setDefaultComponent(this);
        // 安全的初始化
        ExceptionUtils.exceptionListener(() -> {
            if (Settings.isConsoleStartup()){
                LogConsole.install(this);
            }
            initComponents();
            setBounds(new Rectangle(800,600));
            setLocationRelativeTo(getOwner());
            addPopupMenu();
            initDefaultValue();
            registerCommandAction();
        }, this);
    }

    private void registerCommandAction() {
        DynamicEventHandler.registerCommandHandler(new AddDataSourceCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new DeleteDataSourceCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new UpdateDataSourceCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ConnectDataSourceCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new CodeGeneratorCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new OpenConsoleCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new OpenTableCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new SwitchDatabaseCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new GlobalConfigCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new SelectModuleCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ClearGlobalCacheCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new SystemResetCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new GetConsolePasswordCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new CopyEntityCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new CopyMapperXmlCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new SaveAsEntityCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new SaveAsMapperXmlCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ExportDatabaseXMindCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ExportTablesXMindCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ExportTablesExcelCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ExportDatabaseExcelCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ShareCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new ExchangeGroupCommandHandler(this));
        DynamicEventHandler.registerCommandHandler(new CopyEntityJsonCommandHandler(this));
    }


    /**
     * 添加扩展菜单
     */
    private void addPopupMenu() {
        addMorePopupMenu();
        addDbTablePopupMenu();
    }

    private void addDbTablePopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();

        PopupMenuUtils.addMenuItem(jPopupMenu, "导出思维导图", eventHandler, Command.EXPORT_TABLES_XMIND.name());
        PopupMenuUtils.addMenuItem(jPopupMenu, "导出数据库文档", eventHandler, Command.EXPORT_TABLES_DB_DOC.name());
        PopupMenuUtils.addSeparator(jPopupMenu);
        PopupMenuUtils.addMenuItem(jPopupMenu, "复制实体类", eventHandler, Command.COPY_ENTITY.name());
        PopupMenuUtils.addMenuItem(jPopupMenu, "另存为实体类", eventHandler, Command.SAVE_AS_ENTITY.name());
        PopupMenuUtils.addSeparator(jPopupMenu);
        PopupMenuUtils.addMenuItem(jPopupMenu, "复制Mapper.xml", eventHandler, Command.COPY_MAPPER_XML.name());
        PopupMenuUtils.addMenuItem(jPopupMenu, "另存为Mapper.xml", eventHandler, Command.SAVE_AS_MAPPER_XML.name());
        PopupMenuUtils.addSeparator(jPopupMenu);
        PopupMenuUtils.addMenuItem(jPopupMenu, "复制JSON", eventHandler, Command.COPY_ENTITY_JSON.name());

        // 添加到相应的控件上
        PopupMenuUtils.addPopupMenu(table_db_tables, jPopupMenu, new PopupMenuUtils.Interceptor<JTable>() {
            @Override
            public void showBefore(JTable component, JPopupMenu popup, MouseEvent e) {
                int selectedRowCount = component.getSelectedRowCount();

                popup.getComponent(0).setEnabled(true);
                popup.getComponent(1).setEnabled(true);
                popup.getComponent(3).setEnabled(true);
                popup.getComponent(6).setEnabled(true);
                popup.getComponent(9).setEnabled(true);

                if (selectedRowCount > 1){
                    popup.getComponent(3).setEnabled(false);
                    popup.getComponent(6).setEnabled(false);
                    popup.getComponent(9).setEnabled(false);
                }
                if (selectedRowCount < 1){
                    popup.getComponent(0).setEnabled(false);
                    popup.getComponent(1).setEnabled(false);
                }
            }
            @Override
            public void show(JTable component, JPopupMenu popup, MouseEvent e) {
                if (component.getSelectedRowCount() > 0){
                    PopupMenuUtils.Interceptor.super.show(component, popup, e);
                }
            }
        });
    }

    private void addMorePopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();

        // 管理员独有菜单
        if (Settings.isAdmin()){
            addAdminMenu(jPopupMenu);
        }

        PopupMenuUtils.addMenuItem(jPopupMenu, "修改数据源", eventHandler, Command.UPDATE_DATASOURCE.name());
        PopupMenuUtils.addMenuItem(jPopupMenu, "删除数据源", eventHandler, Command.DELETE_DATASOURCE.name());
        PopupMenuUtils.addSeparator(jPopupMenu);
        PopupMenuUtils.addMenuItem(jPopupMenu, "导出思维导图", eventHandler, Command.EXPORT_DATABASE_XMIND.name());
        PopupMenuUtils.addMenuItem(jPopupMenu, "导出数据库文档", eventHandler, Command.EXPORT_DATABASE_DB_DOC.name());
        PopupMenuUtils.addSeparator(jPopupMenu);
        PopupMenuUtils.addMenuItem(jPopupMenu, "重置系统", eventHandler, Command.SYSTEM_RESET.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.ALT_MASK));
        PopupMenuUtils.addMenuItem(jPopupMenu, "清除全局缓存", eventHandler, Command.CLEAR_GLOBAL_CACHE.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.ALT_MASK));
        PopupMenuUtils.addMenuItem(jPopupMenu, "打开控制台", eventHandler, Command.OPEN_CONSOLE.name(),
                KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.ALT_MASK));
        PopupMenuUtils.addSeparator(jPopupMenu);
        PopupMenuUtils.addMenuItem(jPopupMenu, "分享给其他人", eventHandler, Command.SHARE.name());
        PopupMenuUtils.addMenuItem(jPopupMenu, "反馈&交流", eventHandler, Command.EXCHANGE_GROUP.name());

        // 添加到相应的控件上
        PopupMenuUtils.addPopupMenu(button_popup_menu, jPopupMenu);
        PopupMenuUtils.addPopupMenu(scrollPane_db_tables, jPopupMenu);
    }

    private void addAdminMenu(JPopupMenu jPopupMenu) {
        JMenu adminMenu = new JMenu("管理员菜单");
        jPopupMenu.add(adminMenu);
        PopupMenuUtils.addSeparator(jPopupMenu);
        // 具体菜单
        PopupMenuUtils.addMenuItem(adminMenu, "获取密码", eventHandler, Command.GET_CONSOLE_PASSWORD.name());
    }

    private void initDefaultValue() {
        refreshComboBox_datasource();
        refreshComboBox_database();
        refreshTable_db_tables();

        comboBox_module.setModel(new ModuleComboBoxModel());
        comboBox_package.setModel(new PackageComboBoxModel());
    }

    public void refreshComboBox_database() {
        comboBox_database.setModel(new DatabaseComboBoxModel());
    }

    public void refreshTable_db_tables() {
        table_db_tables.setModel(new DatabaseTableModel());
        TableColumnModel cm = table_db_tables.getColumnModel();
        DatabaseTableModel model = (DatabaseTableModel) table_db_tables.getModel();
        model.setColumnModel(cm);

        //设置表头居中显示
        TableCellRenderer tableCellRenderer = table_db_tables.getTableHeader().getDefaultRenderer();
        if (tableCellRenderer instanceof DefaultTableCellRenderer){
            ((DefaultTableCellRenderer)tableCellRenderer).setHorizontalAlignment(JLabel.CENTER);
        }

    }

    public void refreshComboBox_datasource() {
        getComboBox_datasource().setModel(new DatasourceComboBoxModel());
    }

    private void button_add_dsActionPerformed(ActionEvent e) {
        eventHandler.actionPerformed(e);
    }

    private void button_connectActionPerformed(ActionEvent e) {
        eventHandler.actionPerformed(e);
    }

    private void button_configActionPerformed(ActionEvent e) {
        eventHandler.actionPerformed(e);
    }

    private void button_generatorActionPerformed(ActionEvent e) {
        eventHandler.actionPerformed(e);
    }

    private void comboBox_databaseActionPerformed(ActionEvent e) {
        eventHandler.actionPerformed(e);
    }

    private void table_db_tablesMouseClicked(MouseEvent e) {
        eventHandler.handle(Command.OPEN_TABLE.name(), e);
    }

    private void label_moduleMouseClicked(MouseEvent e) {
        eventHandler.handle(Command.SELECT_MODULE.name(), e);
    }

    private void comboBox_moduleItemStateChanged(ItemEvent e) {
        Object item = e.getItem();
        if (item instanceof ModuleComboBoxModel.ItemData){
            ((ModuleComboBoxModel.ItemData) item).setSelected((ItemEvent.SELECTED == e.getStateChange()));
        }
    }

    private void label_packageKeyPressed(KeyEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JPanel panel_ds = new JPanel();
        label_datasource = new JLabel();
        comboBox_datasource = new JComboBox();
        button_connect = new JButton();
        button_add_ds = new JButton();
        button_popup_menu = new JButton();
        JPanel panel_main = new JPanel();
        panel_db_select = new JPanel();
        label_database = new JLabel();
        comboBox_database = new JComboBox<>();
        scrollPane_db_tables = new JScrollPane();
        table_db_tables = new JTable();
        JPanel panel_config = new JPanel();
        label_module = new JLabel();
        comboBox_module = new JComboBox();
        button_global_config = new JButton();
        label_package = new JLabel();
        comboBox_package = new JComboBox<>();
        button_generator = new JButton();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel_ds ========
        {
            panel_ds.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[571,fill]" +
                "[fill]" +
                "[fill]" +
                "[30,fill]",
                // rows
                "[]"));
            panel_ds.add(label_datasource, "cell 0 0");
            panel_ds.add(comboBox_datasource, "cell 1 0,dock center");

            //---- button_connect ----
            button_connect.setActionCommand("connect_datasource");
            button_connect.addActionListener(e -> button_connectActionPerformed(e));
            panel_ds.add(button_connect, "cell 2 0");

            //---- button_add_ds ----
            button_add_ds.setActionCommand("add_datasource");
            button_add_ds.addActionListener(e -> button_add_dsActionPerformed(e));
            panel_ds.add(button_add_ds, "cell 3 0");
            panel_ds.add(button_popup_menu, "cell 4 0");
        }
        contentPane.add(panel_ds, BorderLayout.NORTH);

        //======== panel_main ========
        {
            panel_main.setLayout(new BorderLayout());

            //======== panel_db_select ========
            {
                panel_db_select.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[725,fill]",
                    // rows
                    "[]"));
                panel_db_select.add(label_database, "cell 0 0");

                //---- comboBox_database ----
                comboBox_database.setModel(new DefaultComboBoxModel<>(new String[] {
                    "vmc",
                    "vccx"
                }));
                comboBox_database.setActionCommand("switch_database");
                comboBox_database.setMinimumSize(new Dimension(55, 27));
                comboBox_database.addActionListener(e -> comboBox_databaseActionPerformed(e));
                panel_db_select.add(comboBox_database, "cell 1 0,dock center");
            }
            panel_main.add(panel_db_select, BorderLayout.NORTH);

            //======== scrollPane_db_tables ========
            {

                //---- table_db_tables ----
                table_db_tables.setModel(new DefaultTableModel());
                table_db_tables.setPreferredScrollableViewportSize(new Dimension(450, 350));
                table_db_tables.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        table_db_tablesMouseClicked(e);
                    }
                });
                scrollPane_db_tables.setViewportView(table_db_tables);
            }
            panel_main.add(scrollPane_db_tables, BorderLayout.CENTER);

            //======== panel_config ========
            {
                panel_config.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[680,fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]"));

                //---- label_module ----
                label_module.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        label_moduleMouseClicked(e);
                    }
                });
                panel_config.add(label_module, "cell 0 0");

                //---- comboBox_module ----
                comboBox_module.setEditable(true);
                comboBox_module.addItemListener(e -> comboBox_moduleItemStateChanged(e));
                panel_config.add(comboBox_module, "cell 1 0,dock center");

                //---- button_global_config ----
                button_global_config.setActionCommand("global_config");
                button_global_config.addActionListener(e -> button_configActionPerformed(e));
                panel_config.add(button_global_config, "cell 2 0");
                panel_config.add(label_package, "cell 0 1");

                //---- comboBox_package ----
                comboBox_package.setEditable(true);
                comboBox_package.setModel(new DefaultComboBoxModel<>(new String[] {
                    "com.kancy.data"
                }));
                panel_config.add(comboBox_package, "cell 1 1,dock center");

                //---- button_generator ----
                button_generator.setActionCommand("code_generator");
                button_generator.addActionListener(e -> button_generatorActionPerformed(e));
                panel_config.add(button_generator, "cell 2 1");
            }
            panel_main.add(panel_config, BorderLayout.SOUTH);
        }
        contentPane.add(panel_main, BorderLayout.CENTER);

        initComponentsI18n();

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of data initialization  //GEN-END:initComponents
    }

    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        setTitle(bundle.getString("main.this.title"));
        label_datasource.setText(bundle.getString("main.label_datasource.text"));
        button_connect.setText(bundle.getString("main.button_connect.text"));
        button_add_ds.setText(bundle.getString("main.button_add_ds.text"));
        button_popup_menu.setText(bundle.getString("main.button_popup_menu.text_2"));
        label_database.setText(bundle.getString("main.label_database.text"));
        table_db_tables.setToolTipText(bundle.getString("main.table_db_tables.toolTipText"));
        label_module.setText(bundle.getString("main.label_module.text"));
        comboBox_module.setToolTipText(bundle.getString("main.comboBox_module.toolTipText"));
        button_global_config.setText(bundle.getString("main.button_global_config.text"));
        label_package.setText(bundle.getString("main.label_package.text"));
        button_generator.setText(bundle.getString("main.button_generator.text"));
        // JFormDesigner - End of data i18n initialization  //GEN-END:initI18n
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label_datasource;
    private JComboBox comboBox_datasource;
    private JButton button_connect;
    private JButton button_add_ds;
    private JButton button_popup_menu;
    private JPanel panel_db_select;
    private JLabel label_database;
    private JComboBox<String> comboBox_database;
    private JScrollPane scrollPane_db_tables;
    private JTable table_db_tables;
    private JLabel label_module;
    private JComboBox comboBox_module;
    private JButton button_global_config;
    private JLabel label_package;
    private JComboBox<String> comboBox_package;
    private JButton button_generator;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
