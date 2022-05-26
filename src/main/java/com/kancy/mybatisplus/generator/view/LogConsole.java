/*
 * Created by JFormDesigner on Sat Jun 13 00:55:07 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.log.LogLevel;
import com.kancy.mybatisplus.generator.utils.*;
import com.kancy.mybatisplus.generator.view.components.ConsolePrintStream;
import com.kancy.mybatisplus.generator.view.components.SimpleFileDirectoryChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author kancy
 */
public class LogConsole extends JFrame implements ActionListener {

    private static LogConsole logConsole;

    private static Window window;

    private static LogConsole initLogConsole() {
        LogConsole logConsole = new LogConsole();
        logConsole.setVisible(false);
        logConsole.setLocationRelativeTo(null);
        return logConsole;
    }

    public static synchronized void install(Window window) {
        if (Objects.isNull(logConsole)){
            logConsole = initLogConsole();
            setWindow(window);
        }
    }

    public static void display() {
        display(getWindow());
    }

    public static void display(Window component) {
        SwingUtilities.invokeLater(() -> {
            if (Objects.isNull(logConsole)){
                install(component);
            }
            logConsole.setLocationRelativeTo(component);
            logConsole.setVisible(true);
        });
    }

    public static void close() {
        logConsole.setVisible(false);
    }

    private LogConsole() {

        // 初始化
        initComponents();

        // 添加扩展菜单
        JPopupMenu jPopupMenu = new JPopupMenu();
        PopupMenuUtils.addMenuItem(jPopupMenu, "清空",this, "console_clear");
        PopupMenuUtils.addMenuItem(jPopupMenu, "另存为",this, "console_log_save");
        PopupMenuUtils.addPopupMenu(textArea_console, jPopupMenu);

        // 控制台重定向
        ConsolePrintStream consoleStream = new ConsolePrintStream(System.out, textArea_console);
        System.setOut(consoleStream);
        System.setErr(consoleStream);

        // 设置当前日志级别
        radioButtonMenuItem_log_level_debug.setSelected(Objects.equals(Log.getLogLevel(), LogLevel.DEBUG));
        radioButtonMenuItem_log_level_info.setSelected(Objects.equals(Log.getLogLevel(), LogLevel.INFO));
        radioButtonMenuItem_log_level_warn.setSelected(Objects.equals(Log.getLogLevel(), LogLevel.WARN));
        radioButtonMenuItem_log_level_error.setSelected(Objects.equals(Log.getLogLevel(), LogLevel.ERROR));

    }

    private void radioButtonMenuItem_log_level_debugActionPerformed(ActionEvent e) {
        Log.setLogLevel(LogLevel.DEBUG);
    }

    private void radioButtonMenuItem_log_level_infoActionPerformed(ActionEvent e) {
        Log.setLogLevel(LogLevel.INFO);
    }

    private void radioButtonMenuItem_log_level_warnActionPerformed(ActionEvent e) {
        Log.setLogLevel(LogLevel.WARN);
    }

    private void radioButtonMenuItem_log_level_errorActionPerformed(ActionEvent e) {
        Log.setLogLevel(LogLevel.ERROR);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menuBar2 = new JMenuBar();
        menu = new JMenu();
        menu_set_log_level = new JMenu();
        radioButtonMenuItem_log_level_debug = new JRadioButtonMenuItem();
        radioButtonMenuItem_log_level_info = new JRadioButtonMenuItem();
        radioButtonMenuItem_log_level_warn = new JRadioButtonMenuItem();
        radioButtonMenuItem_log_level_error = new JRadioButtonMenuItem();
        panel_log = new JPanel();
        scrollPane = new JScrollPane();
        textArea_console = new JTextArea();

        //======== this ========
        setMinimumSize(new Dimension(750, 450));
        setTitle("\u63a7\u5236\u53f0");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menuBar2 ========
            {

                //======== menu ========
                {
                    menu.setText("\u83dc\u5355");

                    //======== menu_set_log_level ========
                    {
                        menu_set_log_level.setText("\u8bbe\u7f6e\u65e5\u5fd7\u7ea7\u522b");

                        //---- radioButtonMenuItem_log_level_debug ----
                        radioButtonMenuItem_log_level_debug.setText("debug");
                        radioButtonMenuItem_log_level_debug.addActionListener(e -> radioButtonMenuItem_log_level_debugActionPerformed(e));
                        menu_set_log_level.add(radioButtonMenuItem_log_level_debug);

                        //---- radioButtonMenuItem_log_level_info ----
                        radioButtonMenuItem_log_level_info.setText("info");
                        radioButtonMenuItem_log_level_info.addActionListener(e -> radioButtonMenuItem_log_level_infoActionPerformed(e));
                        menu_set_log_level.add(radioButtonMenuItem_log_level_info);

                        //---- radioButtonMenuItem_log_level_warn ----
                        radioButtonMenuItem_log_level_warn.setText("warn");
                        radioButtonMenuItem_log_level_warn.addActionListener(e -> radioButtonMenuItem_log_level_warnActionPerformed(e));
                        menu_set_log_level.add(radioButtonMenuItem_log_level_warn);

                        //---- radioButtonMenuItem_log_level_error ----
                        radioButtonMenuItem_log_level_error.setText("error");
                        radioButtonMenuItem_log_level_error.addActionListener(e -> radioButtonMenuItem_log_level_errorActionPerformed(e));
                        menu_set_log_level.add(radioButtonMenuItem_log_level_error);
                    }
                    menu.add(menu_set_log_level);
                }
                menuBar2.add(menu);
            }
            menuBar1.add(menuBar2);
        }
        setJMenuBar(menuBar1);

        //======== panel_log ========
        {
            panel_log.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]"));
        }
        contentPane.add(panel_log, BorderLayout.SOUTH);

        //======== scrollPane ========
        {

            //---- textArea_console ----
            textArea_console.setEditable(false);
            scrollPane.setViewportView(textArea_console);
        }
        contentPane.add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup_set_log_level ----
        ButtonGroup buttonGroup_set_log_level = new ButtonGroup();
        buttonGroup_set_log_level.add(radioButtonMenuItem_log_level_debug);
        buttonGroup_set_log_level.add(radioButtonMenuItem_log_level_info);
        buttonGroup_set_log_level.add(radioButtonMenuItem_log_level_warn);
        buttonGroup_set_log_level.add(radioButtonMenuItem_log_level_error);
        // JFormDesigner - End of data initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenuBar menuBar2;
    private JMenu menu;
    private JMenu menu_set_log_level;
    private JRadioButtonMenuItem radioButtonMenuItem_log_level_debug;
    private JRadioButtonMenuItem radioButtonMenuItem_log_level_info;
    private JRadioButtonMenuItem radioButtonMenuItem_log_level_warn;
    private JRadioButtonMenuItem radioButtonMenuItem_log_level_error;
    private JPanel panel_log;
    private JScrollPane scrollPane;
    private JTextArea textArea_console;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private static void setWindow(Window window){
        LogConsole.window = window;
    }

    private static Window getWindow() {
        return window;
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (Objects.equals("console_clear", actionCommand)){
            textArea_console.setText(null);
            return;
        }

        if (Objects.equals("console_log_save", actionCommand)){

            String log = textArea_console.getText();
            if (StringUtils.isEmpty(log)){
                AlertUtils.msg(this, "日志控制台没有内容！");
                return;
            }

            SimpleFileDirectoryChooser chooser = new SimpleFileDirectoryChooser(window);
            chooser.setOnlyFileSelection();
            chooser.setCurrentDirectory(Settings.getDefaultWorkPath());
            chooser.setDefaultSelectedFile(new File("console.log"));
            chooser.setTitle("请选择日志保存的位置");
            chooser.showSaveDialog();
            if (chooser.hasSelectedFile()){
                File selectedFile = chooser.getSelectedFile();
                try {
                    FileUtils.writeStringToFile(selectedFile, log, StandardCharsets.UTF_8);
                    AlertUtils.msg(this, "保存成功！");
                } catch (IOException ex) {
                    Log.error(ex.getMessage(), ex);
                    AlertUtils.msg(this, "保存失败！");
                }
            }
            return;
        }
    }
}
