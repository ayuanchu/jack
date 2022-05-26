/*
 * Created by JFormDesigner on Wed Jul 01 19:26:22 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author kancy
 */
public class UserInputDialog extends JDialog {

    private Object userInputValue = null;

    public UserInputDialog(Window owner) {
        this(owner, null, null);
    }

    public UserInputDialog(Window owner, Object defaultValue) {
        this(owner, null, defaultValue);
    }

    public UserInputDialog(Window owner, Object[] options) {
        this(owner, options, null);
    }

    public UserInputDialog(Window owner, Object[] options, Object defaultValue) {
        super(owner);
        initComponents();

        if (Objects.nonNull(options)){
            HashSet<Object> objects = new HashSet<>();
            objects.addAll(Arrays.asList(options));
            if (Objects.nonNull(defaultValue)){
                objects.add(defaultValue);
            }

            for (Object object : objects) {
                comboBox_input.addItem(object);
            }

            if (Objects.nonNull(defaultValue)){
                comboBox_input.setSelectedItem(defaultValue);
            }
        }
    }

    public Object getUserInputValue() {
        return userInputValue;
    }

    private void okButtonActionPerformed(ActionEvent e) {
        userInputValue = comboBox_input.getSelectedItem();
        this.dispose();
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void setMessage(String msg){
        label_msg.setText(msg);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label_msg = new JLabel();
        comboBox_input = new JComboBox();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setModal(true);
        setTitle("\u7528\u6237\u8f93\u5165");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new MigLayout(
                    "insets dialog,hidemode 3",
                    // columns
                    "[fill]" +
                    "[255,fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]"));
                contentPanel.add(label_msg, "cell 1 0");

                //---- comboBox_input ----
                comboBox_input.setEditable(true);
                contentPanel.add(comboBox_input, "cell 1 1");
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setLayout(new MigLayout(
                    "insets dialog,alignx right",
                    // columns
                    "[button,fill]" +
                    "[button,fill]",
                    // rows
                    null));

                //---- okButton ----
                okButton.addActionListener(e -> okButtonActionPerformed(e));
                buttonBar.add(okButton, "cell 0 0");

                //---- cancelButton ----
                cancelButton.addActionListener(e -> cancelButtonActionPerformed(e));
                buttonBar.add(cancelButton, "cell 1 0");
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);

        initComponentsI18n();

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        okButton.setText(bundle.getString("UserInputDialog.okButton.text"));
        cancelButton.setText(bundle.getString("UserInputDialog.cancelButton.text"));
        // JFormDesigner - End of component i18n initialization  //GEN-END:initI18n
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label_msg;
    private JComboBox comboBox_input;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
