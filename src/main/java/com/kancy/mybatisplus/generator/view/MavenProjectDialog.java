/*
 * Created by JFormDesigner on Sat Jun 13 23:30:35 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.core.model.MavenModel;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.GlobalCacheManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author kancy
 */
public class MavenProjectDialog extends JDialog {

    private MavenModel mavenModel;

    public MavenProjectDialog(MavenModel mavenModel) {
        this.mavenModel = mavenModel;
        initComponents();
        setModal(true);
        setResizable(false);
        initDefaultValue();
    }

    private void initDefaultValue() {
        textField_project_name.setText(mavenModel.getProjectName());
        textField_project_desc.setText(mavenModel.getProjectDescription());

        List groupIds = GlobalCacheManager.getMavenGroupIdComboboxModelItems();
        if (Objects.nonNull(groupIds)){
            for (Object modelItem : groupIds) {
                comboBox_groupid.addItem(modelItem);
            }
        }else{
            comboBox_groupid.addItem(mavenModel.getGroupId());
        }

        List artifactIds = GlobalCacheManager.getMavenArtifactIdComboboxModelItems();
        if (Objects.nonNull(artifactIds)){
            for (Object modelItem : artifactIds) {
                comboBox_artifactid.addItem(modelItem);
            }
        }else {
            comboBox_artifactid.addItem(mavenModel.getArtifactId());
        }

        List versions = GlobalCacheManager.getMavenVersionComboboxModelItems();
        if (Objects.nonNull(versions)){
            for (Object modelItem : versions) {
                comboBox_version.addItem(modelItem);
            }
        }else {
            comboBox_version.addItem(mavenModel.getVersion());
        }

    }

    private void button_editActionPerformed(ActionEvent e) {
        textField_project_name.setEnabled(!textField_project_name.isEnabled());
        textField_project_name.setEditable(!textField_project_name.isEditable());
        textField_project_desc.setEnabled(!textField_project_desc.isEnabled());
        textField_project_desc.setEditable(!textField_project_desc.isEditable());
        comboBox_groupid.setEnabled(!comboBox_groupid.isEnabled());
        comboBox_groupid.setEditable(!comboBox_groupid.isEditable());
        comboBox_artifactid.setEnabled(!comboBox_artifactid.isEnabled());
        comboBox_artifactid.setEditable(!comboBox_artifactid.isEditable());
        comboBox_version.setEnabled(!comboBox_version.isEnabled());
        comboBox_version.setEditable(!comboBox_version.isEditable());
        button_edit.setText(String.format("%s编辑", textField_project_name.isEditable() ? "关闭" : "开启"));
    }

    private void button_okActionPerformed(ActionEvent e) {
        mavenModel.setProjectName(textField_project_name.getText());
        mavenModel.setProjectDescription(textField_project_desc.getText());
        mavenModel.setGroupId(comboBox_groupid.getSelectedItem().toString());
        mavenModel.setArtifactId(comboBox_artifactid.getSelectedItem().toString());
        mavenModel.setVersion(comboBox_version.getSelectedItem().toString());

        // 刷新緩存
        try {
            List comboBox_groupIds = getComboBoxAllData(comboBox_groupid, Settings.getMaxHistorySize());
            List comboBox_artifactIds = getComboBoxAllData(comboBox_artifactid, Settings.getMaxHistorySize());
            List comboBox_versions = getComboBoxAllData(comboBox_version, Settings.getMaxHistorySize());
            GlobalCacheManager.updateMavenGroupIdComboboxModelItems(comboBox_groupIds);
            GlobalCacheManager.updateMavenArtifactIdComboboxModelItems(comboBox_artifactIds);
            GlobalCacheManager.updateMavenVersionComboboxModelItems(comboBox_versions);
        } catch (Exception ex) {
            // 忽略异常，不要影响主流程
            Log.warn(ex.getMessage());
        }

        setVisible(false);
        dispose();
    }

    private List getComboBoxAllData(JComboBox comboBox, int size) {
        Object selectedItem = comboBox.getSelectedItem();
        comboBox.removeItem(selectedItem);
        comboBox.insertItemAt(selectedItem, 0);
        int itemCount = comboBox.getItemCount();
        ArrayList list = new ArrayList(itemCount);
        for (int i = 0; i < itemCount; i++) {
            if (i < size){
                list.add(comboBox.getItemAt(i));
            }
        }
        return list;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        label_project_name = new JLabel();
        textField_project_name = new JTextField();
        label_project_desc = new JLabel();
        textField_project_desc = new JTextField();
        label_groupid = new JLabel();
        comboBox_groupid = new JComboBox();
        label_artifactid = new JLabel();
        comboBox_artifactid = new JComboBox();
        label_version = new JLabel();
        comboBox_version = new JComboBox();
        button_edit = new JButton();
        button_ok = new JButton();

        //======== this ========
        setTitle("Maven\u5de5\u7a0b\u6784\u5efa");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[119,fill]" +
            "[122,fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label_project_name ----
        label_project_name.setText(bundle.getString("maven.project.dialog.label_project_name.text"));
        contentPane.add(label_project_name, "cell 1 1,alignx right,growx 0");

        //---- textField_project_name ----
        textField_project_name.setEditable(false);
        textField_project_name.setEnabled(false);
        contentPane.add(textField_project_name, "cell 2 1 2 1");

        //---- label_project_desc ----
        label_project_desc.setText(bundle.getString("maven.project.dialog.label_project_desc.text"));
        contentPane.add(label_project_desc, "cell 1 2,alignx right,growx 0");

        //---- textField_project_desc ----
        textField_project_desc.setEditable(false);
        textField_project_desc.setEnabled(false);
        contentPane.add(textField_project_desc, "cell 2 2 2 1");

        //---- label_groupid ----
        label_groupid.setText(bundle.getString("maven.project.dialog.label_groupid.text"));
        contentPane.add(label_groupid, "cell 1 3,alignx right,growx 0");

        //---- comboBox_groupid ----
        comboBox_groupid.setEnabled(false);
        contentPane.add(comboBox_groupid, "cell 2 3 2 1");

        //---- label_artifactid ----
        label_artifactid.setText(bundle.getString("maven.project.dialog.label_artifactid.text"));
        contentPane.add(label_artifactid, "cell 1 4,alignx right,growx 0");

        //---- comboBox_artifactid ----
        comboBox_artifactid.setEnabled(false);
        contentPane.add(comboBox_artifactid, "cell 2 4 2 1");

        //---- label_version ----
        label_version.setText(bundle.getString("maven.project.dialog.label_version.text"));
        contentPane.add(label_version, "cell 1 5,alignx right,growx 0");

        //---- comboBox_version ----
        comboBox_version.setEnabled(false);
        contentPane.add(comboBox_version, "cell 2 5 2 1");

        //---- button_edit ----
        button_edit.setText(bundle.getString("maven.project.dialog.button_edit.text"));
        button_edit.addActionListener(e -> button_editActionPerformed(e));
        contentPane.add(button_edit, "cell 2 7");

        //---- button_ok ----
        button_ok.setText(bundle.getString("maven.project.dialog.button_ok.text"));
        button_ok.addActionListener(e -> button_okActionPerformed(e));
        contentPane.add(button_ok, "cell 3 7");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label_project_name;
    private JTextField textField_project_name;
    private JLabel label_project_desc;
    private JTextField textField_project_desc;
    private JLabel label_groupid;
    private JComboBox comboBox_groupid;
    private JLabel label_artifactid;
    private JComboBox comboBox_artifactid;
    private JLabel label_version;
    private JComboBox comboBox_version;
    private JButton button_edit;
    private JButton button_ok;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
