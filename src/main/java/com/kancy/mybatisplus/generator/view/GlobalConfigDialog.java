/*
 * Created by JFormDesigner on Wed Jun 10 13:24:55 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.config.GlobalConfig;
import com.kancy.mybatisplus.generator.enums.KeyStrategyEnum;
import com.kancy.mybatisplus.generator.enums.ProjectEnum;
import com.kancy.mybatisplus.generator.enums.TemplateEngineTypeEnum;
import com.kancy.mybatisplus.generator.exception.ParamException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.ClassUtils;
import com.kancy.mybatisplus.generator.utils.ExceptionUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @author kancy
 */
public class GlobalConfigDialog extends JDialog {

    private DataTypeMappingConfigDialog dataTypeMappingConfigDialog = new DataTypeMappingConfigDialog(this);

    private Main main;

    public GlobalConfigDialog(Main main) {
        super(main);
        this.main = main;

        // 安全的进行初始化
        ExceptionUtils.exceptionListener(() -> {
            initComponents();
            setModal(true);
            setBounds(new Rectangle(750, 550));
            setLocationRelativeTo(getOwner());
            setResizable(false);
            initDefaultValue();
            refreshTabbedPanel();
            tabbedPane.setSelectedIndex(0);
            setVisible(true);
        },this);
    }

    private void refreshTabbedPanel() {
        refreshTabbedPanel(checkBox_mapper, "Mapper", panel_mapper);
        refreshTabbedPanel(checkBox_mapper_xml, "Mapper Xml", panel_mapper_xml);
        refreshTabbedPanel(checkBox_dao, "Dao", panel_dao);
        refreshTabbedPanel(checkBox_service, "Service", panel_service);
        refreshTabbedPanel(checkBox_service, "Service Impl", panel_service_impl);
        refreshTabbedPanel(checkBox_controller, "Controller", panel_controller);

        ProjectEnum projectEnum = (ProjectEnum) comboBox_project.getSelectedItem();
        tabbedPane.remove(panel_maven);
        tabbedPane.remove(panel_spring_boot);
        if (Objects.equals(projectEnum, ProjectEnum.MAVEN_SPRING_BOOT)){
            tabbedPane.addTab("Maven", panel_maven);
            tabbedPane.addTab("Spring Boot", panel_spring_boot);
        }
        if (Objects.equals(projectEnum, ProjectEnum.MAVEN)){
            tabbedPane.addTab("Maven", panel_maven);
        }
    }

    private void initDefaultValue() {
        // 初始化模板类型
        TemplateEngineTypeEnum[] values = TemplateEngineTypeEnum.values();
        Vector vector = new Vector();
        for (TemplateEngineTypeEnum value : values) {
            vector.add(value);
        }
        comboBox_entity_template_type.setModel(new DefaultComboBoxModel(vector));
        comboBox_mapper_template_type.setModel(new DefaultComboBoxModel(vector));
        comboBox_mapper_xml_template_type.setModel(new DefaultComboBoxModel(vector));
        comboBox_service_template_type.setModel(new DefaultComboBoxModel(vector));
        comboBox_controller_template_type.setModel(new DefaultComboBoxModel(vector));
        comboBox_service_template_type_impl.setModel(new DefaultComboBoxModel(vector));
        comboBox_dao_template_type.setModel(new DefaultComboBoxModel(vector));
        comboBox_maven_template_type.setModel(new DefaultComboBoxModel(vector));

        GlobalConfig globalConfig = GlobalConfigManager.get();
        textField_author.setText(globalConfig.getAuthor());
        textField_description.setText(globalConfig.getDescription());

        checkBox_lombok.setSelected(globalConfig.isLombokEnabled());
        checkBox_base_column.setSelected(globalConfig.isBaseColumnEnabled());
        checkBox_is_fill.setSelected(globalConfig.isFillEnabled());
        checkBox_overwrite.setSelected(globalConfig.isOverwriteEnabled());
        checkBox_table_prefix.setSelected(globalConfig.isTablePrefixEnabled());
        textField_table_prefix.setText(globalConfig.getTablePrefix());
        textField_table_prefix.setEditable(globalConfig.isTablePrefixEnabled());
        checkBox_mapper.setSelected(globalConfig.getMapper().isEnabled());
        checkBox_mapper_xml.setSelected(globalConfig.getMapperXml().isEnabled());
        checkBox_dao.setSelected(globalConfig.getDao().isEnabled());
        checkBox_service.setSelected(globalConfig.getService().isEnabled());
        checkBox_controller.setSelected(globalConfig.getController().isEnabled());
        checkBox_maven_interceptor.setSelected(globalConfig.getMaven().isInterceptorEnabled());
        checkBox_maven_repo.setSelected(globalConfig.getMaven().isRepoEnabled());
        textField_maven_repo.setEditable(checkBox_maven_repo.isSelected());

        // Entity
        textField_entity_package.setText(globalConfig.getEntity().getPackageName());
        textField_entity_super_class.setText(globalConfig.getEntity().getSupperClassName());
        comboBox_entity_template_type.setSelectedItem(globalConfig.getEntity().getTemplateEngineType());
        textArea_entity.setText(globalConfig.getEntity().getTemplate());
        checkBox_entity_edit_swith.setSelected(globalConfig.getEntity().isUseCustom());
        comboBox_entity_key_strategy.setSelectedItem(globalConfig.getEntity().getKeyStrategy());
        // 主键策略
        KeyStrategyEnum[] keyStrategyEnums = KeyStrategyEnum.values();
        comboBox_entity_key_strategy.removeAllItems();
        for (KeyStrategyEnum keyStrategyEnum : keyStrategyEnums) {
            comboBox_entity_key_strategy.addItem(keyStrategyEnum);
        }
        comboBox_entity_key_strategy.setSelectedItem(KeyStrategyEnum.valueOf(GlobalConfigManager.get().getEntity().getKeyStrategy()));

        // Mapper
        textField_mapper_package.setText(globalConfig.getMapper().getPackageName());
        textField_mapper_super_class.setText(globalConfig.getMapper().getSupperClassName());
        comboBox_mapper_template_type.setSelectedItem(globalConfig.getMapper().getTemplateEngineType());
        textArea_mapper.setText(globalConfig.getMapper().getTemplate());
        checkBox_mapper_edit_swith.setSelected(globalConfig.getMapper().isUseCustom());

        // Mapper Xml
        textField_mapper_xml_package.setText(globalConfig.getMapperXml().getPackageName().replace("/","."));
        comboBox_mapper_xml_template_type.setSelectedItem(globalConfig.getMapperXml().getTemplateEngineType());
        textArea_mapper_xml.setText(globalConfig.getMapperXml().getTemplate());
        checkBox_mapper_xml_edit_swith.setSelected(globalConfig.getMapperXml().isUseCustom());

        // Dao
        textField_dao_package.setText(globalConfig.getDao().getPackageName());
        textField_dao_super_class.setText(globalConfig.getDao().getSupperClassName());
        comboBox_dao_template_type.setSelectedItem(globalConfig.getDao().getTemplateEngineType());
        textArea_dao.setText(globalConfig.getDao().getTemplate());
        checkBox_dao_edit_swith.setSelected(globalConfig.getDao().isUseCustom());

        // Service
        textField_service_package.setText(globalConfig.getService().getPackageName());
        textField_service_super_class.setText(globalConfig.getService().getSupperClassName());
        comboBox_service_template_type.setSelectedItem(globalConfig.getService().getTemplateEngineType());
        textArea_service.setText(globalConfig.getService().getTemplate());
        checkBox_service_edit_swith.setSelected(globalConfig.getService().isUseCustom());

        // Service Impl
        textField_service_package_impl.setText(globalConfig.getServiceImpl().getPackageName());
        textField_service_super_class_impl.setText(globalConfig.getServiceImpl().getSupperClassName());
        comboBox_service_template_type_impl.setSelectedItem(globalConfig.getServiceImpl().getTemplateEngineType());
        textArea_service_impl.setText(globalConfig.getServiceImpl().getTemplate());
        checkBox_service_edit_swith_impl.setSelected(globalConfig.getServiceImpl().isUseCustom());

        // Controller
        textField_controller_package.setText(globalConfig.getController().getPackageName());
        textField_controller_super_class.setText(globalConfig.getController().getSupperClassName());
        comboBox_controller_template_type.setSelectedItem(globalConfig.getController().getTemplateEngineType());
        textArea_controller.setText(globalConfig.getController().getTemplate());
        checkBox_controller_edit_swith.setSelected(globalConfig.getController().isUseCustom());

        // Maven
        comboBox_maven_template_type.setSelectedItem(globalConfig.getMaven().getTemplateEngineType());
        textArea_maven.setText(globalConfig.getMaven().getTemplate());
        checkBox_maven_overwrite_pom.setSelected(globalConfig.getMaven().isOverwritePomEnabled());
        textField_maven_repo.setText(globalConfig.getMaven().getRepoUrl());
        checkBox_maven_edit_swith.setSelected(globalConfig.getMaven().isUseCustom());

        // Spring Boot
        comboBox_spring_boot_config_file_type.setSelectedItem(globalConfig.getSpringBoot().getConfigFileType());

        checkBox_sb_mica.setSelected(globalConfig.getSpringBoot().isMicaEnabled());
        checkBox_sb_actuator.setSelected(globalConfig.getSpringBoot().isActuatorEnabled());
        checkBox_sb_prometheus.setSelected(globalConfig.getSpringBoot().isPrometheusEnabled());
        checkBox_sb_redis.setSelected(globalConfig.getSpringBoot().isRedisEnabled());
        checkBox_sb_redis_lock.setSelected(globalConfig.getSpringBoot().isRedisLockEnabled());
        checkBox_sb_mongo.setSelected(globalConfig.getSpringBoot().isMongoEnabled());
        checkBox_sb_hakiri.setSelected(globalConfig.getSpringBoot().isHakiriEnabled());
        checkBox_sb_druid.setSelected(globalConfig.getSpringBoot().isDruidEnabled());
        checkBox_sb_docker.setSelected(globalConfig.getSpringBoot().isDockerEnabled());
        checkBox_sb_jasypt.setSelected(globalConfig.getSpringBoot().isJasyptEnabled());

        checkBox_sb_rabbitmq.setSelected(globalConfig.getSpringBoot().isRabbitmqEnabled());
        checkBox_sb_stream_rabbitmq.setSelected(globalConfig.getSpringBoot().isStreamRabbitEnabled());
        checkBox_sb_kafka.setSelected(globalConfig.getSpringBoot().isKafkaEnabled());
        checkBox_sb_stream_kafak.setSelected(globalConfig.getSpringBoot().isStreamKafkaEnabled());
        checkBox_sb_freemarker.setSelected(globalConfig.getSpringBoot().isFreemarkerEnabled());

        // 关闭脚本编辑
        textArea_entity.setEditable(checkBox_entity_edit_swith.isSelected());
        textArea_mapper.setEditable(checkBox_mapper_edit_swith.isSelected());
        textArea_mapper_xml.setEditable(checkBox_mapper_xml_edit_swith.isSelected());
        textArea_dao.setEditable(checkBox_dao_edit_swith.isSelected());
        textArea_service.setEditable(checkBox_service_edit_swith.isSelected());
        textArea_service_impl.setEditable(checkBox_service_edit_swith_impl.isSelected());
        textArea_controller.setEditable(checkBox_controller_edit_swith.isSelected());
        textArea_maven.setEditable(checkBox_maven_edit_swith.isSelected());

        // 初始化工程数据
        ProjectEnum[] projectEnums = ProjectEnum.values();
        comboBox_project.removeAllItems();
        for (ProjectEnum projectEnum : projectEnums) {
            comboBox_project.addItem(projectEnum);
        }
        comboBox_project.setSelectedItem(ProjectEnum.valueOf(GlobalConfigManager.get().getProjectType()));
    }

    private void checkBox_table_prefixActionPerformed(ActionEvent e) {
        textField_table_prefix.setEditable(checkBox_table_prefix.isSelected());
    }

    private void button_saveActionPerformed(ActionEvent e) {
        try {
            check();
            doSave();
            store();
            AlertUtils.msg(getOwner(), "保存成功！");
            dataTypeMappingConfigDialog.dispose();
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

    private void check() {
        checkSupperClass(textField_controller_super_class, "Entity");
        if (checkBox_mapper.isSelected()){
            checkSupperClass(textField_mapper_super_class, "Mapper");
        }
        if (checkBox_dao.isSelected()){
            checkSupperClass(textField_dao_super_class, "Dao");
        }
        if (checkBox_service.isSelected()){
            checkSupperClass(textField_service_super_class, "Service");
        }
        if (checkBox_controller.isSelected()){
            checkSupperClass(textField_controller_super_class, "Controller");
        }
    }

    private void checkSupperClass(JTextField jTextField, String type) {
        String superClassName = jTextField.getText();
        try {
            if (StringUtils.isEmpty(superClassName)){
                jTextField.setText(Object.class.getName());
                superClassName = jTextField.getText();
            }
            if (!ClassUtils.isObjectClass(superClassName)){
                Class.forName(superClassName);
                throw new ParamException(String.format("请输入一个自定义的 %s Supper Class！", type));
            }
        } catch (ClassNotFoundException e) {
            if (!superClassName.contains(".")){
                throw new ParamException(String.format("请输入一个自定义带有包名的 %s Supper Class！", type));
            }
            if (superClassName.endsWith(".") || superClassName.startsWith(".")){
                throw new ParamException(String.format("请输入一个正确的 %s Supper Class！", type));
            }
        }
    }

    private void store() {
        GlobalConfigManager.store();
    }

    private void doSave() {
        GlobalConfig globalConfig = GlobalConfigManager.get();
        globalConfig.setAuthor(textField_author.getText());
        globalConfig.setDescription(textField_description.getText());

        globalConfig.setLombokEnabled(checkBox_lombok.isSelected());
        globalConfig.setFillEnabled(checkBox_is_fill.isSelected());
        globalConfig.setTablePrefixEnabled(checkBox_table_prefix.isSelected());
        globalConfig.getDao().setEnabled(checkBox_dao.isSelected());
        globalConfig.getService().setEnabled(checkBox_service.isSelected());
        globalConfig.getServiceImpl().setEnabled(globalConfig.getService().isEnabled());
        globalConfig.getController().setEnabled(checkBox_controller.isSelected());
        globalConfig.setOverwriteEnabled(checkBox_overwrite.isSelected());
        globalConfig.setTablePrefix(textField_table_prefix.getText());

        // Entity
        globalConfig.getEntity().setPackageName(textField_entity_package.getText());
        globalConfig.getEntity().setSupperClassName(textField_entity_super_class.getText());
        globalConfig.getEntity().setTemplateEngineType(comboBox_entity_template_type.getSelectedItem().toString());
        globalConfig.getEntity().setTemplate(textArea_entity.getText());
        globalConfig.getEntity().setKeyStrategy(((KeyStrategyEnum)comboBox_entity_key_strategy.getSelectedItem()).name());
        globalConfig.getEntity().setUseCustom(checkBox_entity_edit_swith.isSelected());
        // 类型映射
        dataTypeMappingConfigDialog.saveConfig();

        // Mapper
        globalConfig.getMapper().setPackageName(textField_mapper_package.getText());
        globalConfig.getMapper().setSupperClassName(textField_mapper_super_class.getText());
        globalConfig.getMapper().setTemplateEngineType(comboBox_mapper_template_type.getSelectedItem().toString());
        globalConfig.getMapper().setTemplate(textArea_mapper.getText());
        globalConfig.getMapper().setUseCustom(checkBox_mapper_edit_swith.isSelected());

        // Mapper Xml
        globalConfig.getMapperXml().setPackageName(textField_mapper_xml_package.getText().replace(".","/"));
        globalConfig.getMapperXml().setTemplateEngineType(comboBox_mapper_xml_template_type.getSelectedItem().toString());
        globalConfig.getMapperXml().setTemplate(textArea_mapper_xml.getText());
        globalConfig.getMapperXml().setUseCustom(checkBox_mapper_xml_edit_swith.isSelected());

        // Dao
        globalConfig.getDao().setPackageName(textField_dao_package.getText());
        globalConfig.getDao().setSupperClassName(textField_dao_super_class.getText());
        globalConfig.getDao().setTemplateEngineType(comboBox_dao_template_type.getSelectedItem().toString());
        globalConfig.getDao().setTemplate(textArea_dao.getText());
        globalConfig.getDao().setUseCustom(checkBox_dao_edit_swith.isSelected());

        // Service
        globalConfig.getService().setPackageName(textField_service_package.getText());
        globalConfig.getService().setSupperClassName(textField_service_super_class.getText());
        globalConfig.getService().setTemplateEngineType(comboBox_service_template_type.getSelectedItem().toString());
        globalConfig.getService().setTemplate(textArea_service.getText());
        globalConfig.getService().setUseCustom(checkBox_service_edit_swith.isSelected());

        // Service Impl
        globalConfig.getServiceImpl().setPackageName(textField_service_package_impl.getText());
        globalConfig.getServiceImpl().setSupperClassName(textField_service_super_class_impl.getText());
        globalConfig.getServiceImpl().setTemplateEngineType(comboBox_service_template_type_impl.getSelectedItem().toString());
        globalConfig.getServiceImpl().setTemplate(textArea_service_impl.getText());
        globalConfig.getServiceImpl().setUseCustom(checkBox_service_edit_swith_impl.isSelected());

        // Controller
        globalConfig.getController().setPackageName(textField_controller_package.getText());
        globalConfig.getController().setSupperClassName(textField_controller_super_class.getText());
        globalConfig.getController().setTemplateEngineType(comboBox_controller_template_type.getSelectedItem().toString());
        globalConfig.getController().setTemplate(textArea_controller.getText());
        globalConfig.getController().setUseCustom(checkBox_controller_edit_swith.isSelected());


        Object projectType = comboBox_project.getSelectedItem();
        globalConfig.setProjectType(((ProjectEnum)projectType).name());

        // Maven
        globalConfig.getMaven().setTemplateEngineType(comboBox_maven_template_type.getSelectedItem().toString());
        globalConfig.getMaven().setTemplate(textArea_maven.getText());
        globalConfig.getMaven().setOverwritePomEnabled(checkBox_maven_overwrite_pom.isSelected());
        globalConfig.getMaven().setInterceptorEnabled(checkBox_maven_interceptor.isSelected());
        globalConfig.getMaven().setUseCustom(checkBox_maven_edit_swith.isSelected());
        globalConfig.getMaven().setRepoEnabled(checkBox_maven_repo.isSelected());
        globalConfig.getMaven().setRepoUrl(textField_maven_repo.getText());
        globalConfig.getMaven().setEnabled(
                Objects.equals(ProjectEnum.MAVEN, projectType)
                || Objects.equals(ProjectEnum.MAVEN_SPRING_BOOT, projectType)
        );

        // Spring Boot
        globalConfig.getSpringBoot().setEnabled(Objects.equals(ProjectEnum.MAVEN_SPRING_BOOT, projectType));
        globalConfig.getSpringBoot().setConfigFileType(comboBox_spring_boot_config_file_type.getSelectedItem().toString());

        globalConfig.getSpringBoot().setActuatorEnabled(checkBox_sb_actuator.isSelected());
        globalConfig.getSpringBoot().setPrometheusEnabled(checkBox_sb_prometheus.isSelected());
        globalConfig.getSpringBoot().setMicaEnabled(checkBox_sb_mica.isSelected());
        globalConfig.getSpringBoot().setRedisEnabled(checkBox_sb_redis.isSelected());
        globalConfig.getSpringBoot().setRedisLockEnabled(checkBox_sb_redis_lock.isSelected());
        globalConfig.getSpringBoot().setHakiriEnabled(checkBox_sb_hakiri.isSelected());
        globalConfig.getSpringBoot().setDruidEnabled(checkBox_sb_druid.isSelected());
        globalConfig.getSpringBoot().setMongoEnabled(checkBox_sb_mongo.isSelected());
        globalConfig.getSpringBoot().setDockerEnabled(checkBox_sb_docker.isSelected());
        globalConfig.getSpringBoot().setJasyptEnabled(checkBox_sb_jasypt.isSelected());

        globalConfig.getSpringBoot().setRabbitmqEnabled(checkBox_sb_rabbitmq.isSelected());
        globalConfig.getSpringBoot().setKafkaEnabled(checkBox_sb_kafka.isSelected());
        globalConfig.getSpringBoot().setStreamKafkaEnabled(checkBox_sb_stream_kafak.isSelected());
        globalConfig.getSpringBoot().setStreamRabbitEnabled(checkBox_sb_stream_rabbitmq.isSelected());
        globalConfig.getSpringBoot().setFreemarkerEnabled(checkBox_sb_freemarker.isSelected());

    }

    private void button_resetActionPerformed(ActionEvent e) {
        GlobalConfigManager.reset();
        initDefaultValue();
        AlertUtils.msg(this, "重置成功！");
    }

    private void checkBox_edit_swithActionPerformed(ActionEvent e) {
        textArea_entity.setEditable(checkBox_entity_edit_swith.isSelected());
    }

    private void checkBox_mapper_edit_swithActionPerformed(ActionEvent e) {
        textArea_mapper.setEditable(checkBox_mapper_edit_swith.isSelected());
    }

    private void checkBox_dao_edit_swithActionPerformed(ActionEvent e) {
        textArea_dao.setEditable(checkBox_dao_edit_swith.isSelected());
    }

    private void checkBox_service_edit_swithActionPerformed(ActionEvent e) {
        textArea_service.setEditable(checkBox_service_edit_swith.isSelected());
    }

    private void checkBox_service_edit_swith_implActionPerformed(ActionEvent e) {
        textArea_service_impl.setEditable(checkBox_service_edit_swith_impl.isSelected());
    }

    private void checkBox_controller_edit_swithActionPerformed(ActionEvent e) {
        textArea_controller.setEditable(checkBox_controller_edit_swith.isSelected());
    }

    private void checkBox_mapper_xml_edit_swithActionPerformed(ActionEvent e) {
        textArea_mapper_xml.setEditable(checkBox_mapper_xml_edit_swith.isSelected());
    }

    private void checkBox_maven_edit_swithActionPerformed(ActionEvent e) {
        textArea_maven.setEditable(checkBox_maven_edit_swith.isSelected());
    }

    private void checkBox_mapperActionPerformed(ActionEvent e) {
        refreshTabbedPanel();
        if (checkBox_mapper.isSelected()){
            tabbedPane.setSelectedComponent(panel_mapper);
        }
    }

    private void checkBox_mapper_xmlActionPerformed(ActionEvent e) {
        refreshTabbedPanel();
        if (checkBox_mapper_xml.isSelected()){
            tabbedPane.setSelectedComponent(panel_mapper_xml);
        }
    }

    private void checkBox_daoActionPerformed(ActionEvent e) {
        refreshTabbedPanel();
        if (checkBox_dao.isSelected()){
            tabbedPane.setSelectedComponent(panel_dao);
        }
    }

    private void checkBox_serviceActionPerformed(ActionEvent e) {
        refreshTabbedPanel();
        if (checkBox_service.isSelected()){
            tabbedPane.setSelectedComponent(panel_service);
        }
    }

    private void checkBox_controllerActionPerformed(ActionEvent e) {
        refreshTabbedPanel();
        if (checkBox_controller.isSelected()){
            tabbedPane.setSelectedComponent(panel_controller);
        }
    }

    private void refreshTabbedPanel(JCheckBox checkBox, String panelName, JPanel panel) {
        if (!checkBox.isSelected()){
            tabbedPane.remove(panel);
        }else {
            tabbedPane.addTab(panelName, panel);
        }
    }

    private void comboBox_projectActionPerformed(ActionEvent e) {
        refreshTabbedPanel();
        if (Objects.equals(ProjectEnum.MAVEN, comboBox_project.getSelectedItem())){
            tabbedPane.setSelectedComponent(panel_maven);
            return;
        }
        if (Objects.equals(ProjectEnum.MAVEN_SPRING_BOOT, comboBox_project.getSelectedItem())){
            tabbedPane.setSelectedComponent(panel_spring_boot);
            return;
        }
    }

    private void checkBox_maven_sfActionPerformed(ActionEvent e) {
        textField_maven_repo.setEditable(checkBox_maven_repo.isSelected());
    }

    private void label_logoMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){
            GlobalConfigAboutDialog dialog = new GlobalConfigAboutDialog(this);
            dialog.setVisible(true);
        }
    }

    private void button_type_mappingActionPerformed(ActionEvent e) {
        dataTypeMappingConfigDialog.setVisible(true);
    }

    private void checkBox_sb_rabbitmqActionPerformed(ActionEvent e) {
        if (checkBox_sb_rabbitmq.isSelected()){
            checkBox_sb_stream_rabbitmq.setSelected(false);
        }
    }

    private void checkBox_sb_stream_rabbitmqActionPerformed(ActionEvent e) {
        if (checkBox_sb_stream_rabbitmq.isSelected()){
            checkBox_sb_rabbitmq.setSelected(false);
        }
    }

    private void checkBox_sb_kafkaActionPerformed(ActionEvent e) {
        if (checkBox_sb_kafka.isSelected()){
            checkBox_sb_stream_kafak.setSelected(false);
        }
    }

    private void checkBox_sb_stream_kafakActionPerformed(ActionEvent e) {
        if (checkBox_sb_stream_kafak.isSelected()){
            checkBox_sb_kafka.setSelected(false);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        panel1 = new JPanel();
        label_author = new JLabel();
        textField_author = new JTextField();
        button_save = new JButton();
        label_description = new JLabel();
        textField_description = new JTextField();
        button_reset = new JButton();
        label1 = new JLabel();
        comboBox_project = new JComboBox();
        checkBox_overwrite = new JCheckBox();
        label_fun = new JLabel();
        checkBox_mapper = new JCheckBox();
        checkBox_mapper_xml = new JCheckBox();
        checkBox_dao = new JCheckBox();
        checkBox_service = new JCheckBox();
        checkBox_controller = new JCheckBox();
        tabbedPane = new JTabbedPane();
        JPanel panel_guide = new JPanel();
        label_logo = new JLabel();
        panel_entity = new JPanel();
        label_entity_package = new JLabel();
        textField_entity_package = new JTextField();
        label_entity_super_class = new JLabel();
        textField_entity_super_class = new JTextField();
        checkBox_table_prefix = new JCheckBox();
        textField_table_prefix = new JTextField();
        button_type_mapping = new JButton();
        label_entity_template_type = new JLabel();
        comboBox_entity_template_type = new JComboBox<>();
        checkBox_entity_edit_swith = new JCheckBox();
        checkBox_lombok = new JCheckBox();
        checkBox_is_fill = new JCheckBox();
        label_entity_key_strategy = new JLabel();
        comboBox_entity_key_strategy = new JComboBox();
        scrollPane_entity = new JScrollPane();
        textArea_entity = new JTextArea();
        panel_mapper = new JPanel();
        label_mapper_package = new JLabel();
        textField_mapper_package = new JTextField();
        label_mapper_super_class = new JLabel();
        textField_mapper_super_class = new JTextField();
        label_mapper_template_type = new JLabel();
        comboBox_mapper_template_type = new JComboBox<>();
        checkBox_mapper_edit_swith = new JCheckBox();
        scrollPane_mapper = new JScrollPane();
        textArea_mapper = new JTextArea();
        panel_dao = new JPanel();
        label_dao_package = new JLabel();
        textField_dao_package = new JTextField();
        label_dao_super_class = new JLabel();
        textField_dao_super_class = new JTextField();
        label_dao_template_type = new JLabel();
        comboBox_dao_template_type = new JComboBox<>();
        checkBox_dao_edit_swith = new JCheckBox();
        scrollPane_dao = new JScrollPane();
        textArea_dao = new JTextArea();
        panel_service = new JPanel();
        label_service_package = new JLabel();
        textField_service_package = new JTextField();
        label_service_super_class = new JLabel();
        textField_service_super_class = new JTextField();
        label_service_template_type = new JLabel();
        comboBox_service_template_type = new JComboBox<>();
        checkBox_service_edit_swith = new JCheckBox();
        scrollPane_service = new JScrollPane();
        textArea_service = new JTextArea();
        panel_service_impl = new JPanel();
        label_service_package_impl = new JLabel();
        textField_service_package_impl = new JTextField();
        label_service_super_class_impl = new JLabel();
        textField_service_super_class_impl = new JTextField();
        label_service_template_type_impl = new JLabel();
        comboBox_service_template_type_impl = new JComboBox<>();
        checkBox_service_edit_swith_impl = new JCheckBox();
        scrollPane_service_impl = new JScrollPane();
        textArea_service_impl = new JTextArea();
        panel_controller = new JPanel();
        label_controller_package = new JLabel();
        textField_controller_package = new JTextField();
        label_controller_super_class = new JLabel();
        textField_controller_super_class = new JTextField();
        label_controller_template_type = new JLabel();
        comboBox_controller_template_type = new JComboBox<>();
        checkBox_controller_edit_swith = new JCheckBox();
        scrollPane_controller = new JScrollPane();
        textArea_controller = new JTextArea();
        panel_mapper_xml = new JPanel();
        label_mapper_xml_package = new JLabel();
        textField_mapper_xml_package = new JTextField();
        checkBox_base_column = new JCheckBox();
        label_mapper_xml_template_type = new JLabel();
        comboBox_mapper_xml_template_type = new JComboBox<>();
        checkBox_mapper_xml_edit_swith = new JCheckBox();
        JScrollPane scrollPane_mapper_xml = new JScrollPane();
        textArea_mapper_xml = new JTextArea();
        panel_maven = new JPanel();
        checkBox_maven_repo = new JCheckBox();
        textField_maven_repo = new JTextField();
        checkBox_maven_overwrite_pom = new JCheckBox();
        label_maven_template_type = new JLabel();
        comboBox_maven_template_type = new JComboBox<>();
        checkBox_maven_edit_swith = new JCheckBox();
        checkBox_maven_interceptor = new JCheckBox();
        JScrollPane scrollPane_maven = new JScrollPane();
        textArea_maven = new JTextArea();
        panel_spring_boot = new JPanel();
        label_config_type_name = new JLabel();
        comboBox_spring_boot_config_file_type = new JComboBox<>();
        label_sb_jicheng = new JLabel();
        checkBox_sb_mica = new JCheckBox();
        checkBox_sb_actuator = new JCheckBox();
        checkBox_sb_prometheus = new JCheckBox();
        checkBox_sb_jasypt = new JCheckBox();
        checkBox_sb_hakiri = new JCheckBox();
        checkBox_sb_druid = new JCheckBox();
        checkBox_sb_mongo = new JCheckBox();
        checkBox_sb_redis = new JCheckBox();
        checkBox_sb_redis_lock = new JCheckBox();
        checkBox_sb_rabbitmq = new JCheckBox();
        checkBox_sb_stream_rabbitmq = new JCheckBox();
        checkBox_sb_kafka = new JCheckBox();
        checkBox_sb_stream_kafak = new JCheckBox();
        checkBox_sb_okhttp = new JCheckBox();
        checkBox_sb_freemarker = new JCheckBox();
        checkBox_sb_docker = new JCheckBox();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[26,fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[95,fill]" +
                "[99,fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]" +
                "[]"));
            panel1.add(label_author, "cell 0 1");
            panel1.add(textField_author, "cell 1 1 6 1");

            //---- button_save ----
            button_save.addActionListener(e -> button_saveActionPerformed(e));
            panel1.add(button_save, "cell 7 1");
            panel1.add(label_description, "cell 0 2");
            panel1.add(textField_description, "cell 1 2 6 1");

            //---- button_reset ----
            button_reset.setToolTipText("\u6062\u590d\u5230\u7cfb\u7edf\u9ed8\u8ba4\u7684\u914d\u7f6e");
            button_reset.addActionListener(e -> button_resetActionPerformed(e));
            panel1.add(button_reset, "cell 7 2");
            panel1.add(label1, "cell 0 3");

            //---- comboBox_project ----
            comboBox_project.addActionListener(e -> comboBox_projectActionPerformed(e));
            panel1.add(comboBox_project, "cell 1 3 2 1");
            panel1.add(checkBox_overwrite, "cell 3 3");
            panel1.add(label_fun, "cell 0 4");

            //---- checkBox_mapper ----
            checkBox_mapper.addActionListener(e -> checkBox_mapperActionPerformed(e));
            panel1.add(checkBox_mapper, "cell 1 4");

            //---- checkBox_mapper_xml ----
            checkBox_mapper_xml.addActionListener(e -> checkBox_mapper_xmlActionPerformed(e));
            panel1.add(checkBox_mapper_xml, "cell 2 4");

            //---- checkBox_dao ----
            checkBox_dao.addActionListener(e -> checkBox_daoActionPerformed(e));
            panel1.add(checkBox_dao, "cell 3 4");

            //---- checkBox_service ----
            checkBox_service.addActionListener(e -> checkBox_serviceActionPerformed(e));
            panel1.add(checkBox_service, "cell 4 4");

            //---- checkBox_controller ----
            checkBox_controller.addActionListener(e -> checkBox_controllerActionPerformed(e));
            panel1.add(checkBox_controller, "cell 5 4");
        }
        contentPane.add(panel1, BorderLayout.NORTH);

        //======== tabbedPane ========
        {

            //======== panel_guide ========
            {
                panel_guide.setLayout(new BorderLayout());

                //---- label_logo ----
                label_logo.setIcon(new ImageIcon(getClass().getResource("/images/icon.png")));
                label_logo.setHorizontalAlignment(SwingConstants.CENTER);
                label_logo.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        label_logoMouseClicked(e);
                    }
                });
                panel_guide.add(label_logo, BorderLayout.CENTER);
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_guide.tab.title"), panel_guide);

            //======== panel_entity ========
            {
                panel_entity.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[116,fill]" +
                    "[32,fill]" +
                    "[182,fill]" +
                    "[fill]" +
                    "[70,fill]" +
                    "[43,fill]" +
                    "[133,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- label_entity_package ----
                label_entity_package.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_entity.add(label_entity_package, "cell 0 0");

                //---- textField_entity_package ----
                textField_entity_package.setText("entity");
                panel_entity.add(textField_entity_package, "cell 1 0");
                panel_entity.add(label_entity_super_class, "cell 2 0");

                //---- textField_entity_super_class ----
                textField_entity_super_class.setText("com.baomidou.mybatisplus.extension.activerecord.Model");
                panel_entity.add(textField_entity_super_class, "cell 3 0");

                //---- checkBox_table_prefix ----
                checkBox_table_prefix.addActionListener(e -> checkBox_table_prefixActionPerformed(e));
                panel_entity.add(checkBox_table_prefix, "cell 4 0");
                panel_entity.add(textField_table_prefix, "cell 5 0");

                //---- button_type_mapping ----
                button_type_mapping.addActionListener(e -> button_type_mappingActionPerformed(e));
                panel_entity.add(button_type_mapping, "cell 7 0");
                panel_entity.add(label_entity_template_type, "cell 0 1");

                //---- comboBox_entity_template_type ----
                comboBox_entity_template_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Beetl",
                    "Freemark"
                }));
                panel_entity.add(comboBox_entity_template_type, "cell 1 1");

                //---- checkBox_entity_edit_swith ----
                checkBox_entity_edit_swith.addActionListener(e -> checkBox_edit_swithActionPerformed(e));
                panel_entity.add(checkBox_entity_edit_swith, "cell 3 1");
                panel_entity.add(checkBox_lombok, "cell 4 1");
                panel_entity.add(checkBox_is_fill, "cell 5 1");
                panel_entity.add(label_entity_key_strategy, "cell 6 1,alignx right,growx 0");
                panel_entity.add(comboBox_entity_key_strategy, "cell 7 1");

                //======== scrollPane_entity ========
                {

                    //---- textArea_entity ----
                    textArea_entity.setRows(15);
                    scrollPane_entity.setViewportView(textArea_entity);
                }
                panel_entity.add(scrollPane_entity, "cell 0 2 8 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_entity.tab.title"), panel_entity);

            //======== panel_mapper ========
            {
                panel_mapper.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[116,fill]" +
                    "[32,fill]" +
                    "[189,fill]" +
                    "[60,fill]" +
                    "[93,fill]" +
                    "[167,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- label_mapper_package ----
                label_mapper_package.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_mapper.add(label_mapper_package, "cell 0 0");

                //---- textField_mapper_package ----
                textField_mapper_package.setText("mapper");
                panel_mapper.add(textField_mapper_package, "cell 1 0");
                panel_mapper.add(label_mapper_super_class, "cell 2 0");

                //---- textField_mapper_super_class ----
                textField_mapper_super_class.setText("com.baomidou.mybatisplus.core.mapper.BaseMapper");
                panel_mapper.add(textField_mapper_super_class, "cell 3 0 3 1");
                panel_mapper.add(label_mapper_template_type, "cell 0 1");

                //---- comboBox_mapper_template_type ----
                comboBox_mapper_template_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Beetl",
                    "Freemark"
                }));
                panel_mapper.add(comboBox_mapper_template_type, "cell 1 1");

                //---- checkBox_mapper_edit_swith ----
                checkBox_mapper_edit_swith.setText("\u4f7f\u7528\u81ea\u5b9a\u4e49\u6a21\u677f");
                checkBox_mapper_edit_swith.addActionListener(e -> checkBox_mapper_edit_swithActionPerformed(e));
                panel_mapper.add(checkBox_mapper_edit_swith, "cell 3 1");

                //======== scrollPane_mapper ========
                {

                    //---- textArea_mapper ----
                    textArea_mapper.setRows(15);
                    scrollPane_mapper.setViewportView(textArea_mapper);
                }
                panel_mapper.add(scrollPane_mapper, "cell 0 2 7 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_mapper.tab.title"), panel_mapper);

            //======== panel_dao ========
            {
                panel_dao.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[116,fill]" +
                    "[32,fill]" +
                    "[261,fill]" +
                    "[fill]" +
                    "[81,fill]" +
                    "[166,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- label_dao_package ----
                label_dao_package.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_dao.add(label_dao_package, "cell 0 0");

                //---- textField_dao_package ----
                textField_dao_package.setText("dao");
                panel_dao.add(textField_dao_package, "cell 1 0");
                panel_dao.add(label_dao_super_class, "cell 2 0");

                //---- textField_dao_super_class ----
                textField_dao_super_class.setText("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
                panel_dao.add(textField_dao_super_class, "cell 3 0 3 1");
                panel_dao.add(label_dao_template_type, "cell 0 1");

                //---- comboBox_dao_template_type ----
                comboBox_dao_template_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Beetl",
                    "Freemark"
                }));
                panel_dao.add(comboBox_dao_template_type, "cell 1 1");

                //---- checkBox_dao_edit_swith ----
                checkBox_dao_edit_swith.addActionListener(e -> checkBox_dao_edit_swithActionPerformed(e));
                panel_dao.add(checkBox_dao_edit_swith, "cell 3 1");

                //======== scrollPane_dao ========
                {

                    //---- textArea_dao ----
                    textArea_dao.setRows(15);
                    scrollPane_dao.setViewportView(textArea_dao);
                }
                panel_dao.add(scrollPane_dao, "cell 0 2 7 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_dao.title"), panel_dao);

            //======== panel_service ========
            {
                panel_service.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[131,fill]" +
                    "[32,fill]" +
                    "[261,fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[307,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- label_service_package ----
                label_service_package.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_service.add(label_service_package, "cell 0 0");

                //---- textField_service_package ----
                textField_service_package.setText("service");
                panel_service.add(textField_service_package, "cell 1 0");
                panel_service.add(label_service_super_class, "cell 2 0");

                //---- textField_service_super_class ----
                textField_service_super_class.setText("java.lang.Object");
                panel_service.add(textField_service_super_class, "cell 3 0 3 1");
                panel_service.add(label_service_template_type, "cell 0 1");

                //---- comboBox_service_template_type ----
                comboBox_service_template_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Beetl",
                    "Freemark"
                }));
                panel_service.add(comboBox_service_template_type, "cell 1 1");

                //---- checkBox_service_edit_swith ----
                checkBox_service_edit_swith.addActionListener(e -> checkBox_service_edit_swithActionPerformed(e));
                panel_service.add(checkBox_service_edit_swith, "cell 3 1");

                //======== scrollPane_service ========
                {

                    //---- textArea_service ----
                    textArea_service.setRows(15);
                    scrollPane_service.setViewportView(textArea_service);
                }
                panel_service.add(scrollPane_service, "cell 0 2 7 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_service.title"), panel_service);

            //======== panel_service_impl ========
            {
                panel_service_impl.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[118,fill]" +
                    "[32,fill]" +
                    "[261,fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[279,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- label_service_package_impl ----
                label_service_package_impl.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_service_impl.add(label_service_package_impl, "cell 0 0");

                //---- textField_service_package_impl ----
                textField_service_package_impl.setText("service.impl");
                panel_service_impl.add(textField_service_package_impl, "cell 1 0");
                panel_service_impl.add(label_service_super_class_impl, "cell 2 0");

                //---- textField_service_super_class_impl ----
                textField_service_super_class_impl.setText("java.lang.Object");
                panel_service_impl.add(textField_service_super_class_impl, "cell 3 0 3 1");
                panel_service_impl.add(label_service_template_type_impl, "cell 0 1");

                //---- comboBox_service_template_type_impl ----
                comboBox_service_template_type_impl.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Beetl",
                    "Freemark"
                }));
                panel_service_impl.add(comboBox_service_template_type_impl, "cell 1 1");

                //---- checkBox_service_edit_swith_impl ----
                checkBox_service_edit_swith_impl.addActionListener(e -> checkBox_service_edit_swith_implActionPerformed(e));
                panel_service_impl.add(checkBox_service_edit_swith_impl, "cell 3 1");

                //======== scrollPane_service_impl ========
                {

                    //---- textArea_service_impl ----
                    textArea_service_impl.setRows(15);
                    scrollPane_service_impl.setViewportView(textArea_service_impl);
                }
                panel_service_impl.add(scrollPane_service_impl, "cell 0 2 7 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_service_impl.title"), panel_service_impl);

            //======== panel_controller ========
            {
                panel_controller.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[122,fill]" +
                    "[32,fill]" +
                    "[261,fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[298,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- label_controller_package ----
                label_controller_package.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_controller.add(label_controller_package, "cell 0 0");

                //---- textField_controller_package ----
                textField_controller_package.setText("controller");
                panel_controller.add(textField_controller_package, "cell 1 0");
                panel_controller.add(label_controller_super_class, "cell 2 0");

                //---- textField_controller_super_class ----
                textField_controller_super_class.setText("java.lang.Object");
                panel_controller.add(textField_controller_super_class, "cell 3 0 3 1");
                panel_controller.add(label_controller_template_type, "cell 0 1");

                //---- comboBox_controller_template_type ----
                comboBox_controller_template_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Freemark"
                }));
                panel_controller.add(comboBox_controller_template_type, "cell 1 1");

                //---- checkBox_controller_edit_swith ----
                checkBox_controller_edit_swith.addActionListener(e -> checkBox_controller_edit_swithActionPerformed(e));
                panel_controller.add(checkBox_controller_edit_swith, "cell 3 1");

                //======== scrollPane_controller ========
                {

                    //---- textArea_controller ----
                    textArea_controller.setRows(15);
                    scrollPane_controller.setViewportView(textArea_controller);
                }
                panel_controller.add(scrollPane_controller, "cell 0 2 7 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_controller.title"), panel_controller);

            //======== panel_mapper_xml ========
            {
                panel_mapper_xml.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[118,fill]" +
                    "[32,fill]" +
                    "[261,fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[317,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- label_mapper_xml_package ----
                label_mapper_xml_package.setHorizontalAlignment(SwingConstants.RIGHT);
                panel_mapper_xml.add(label_mapper_xml_package, "cell 0 0");

                //---- textField_mapper_xml_package ----
                textField_mapper_xml_package.setText("mapper");
                panel_mapper_xml.add(textField_mapper_xml_package, "cell 1 0");
                panel_mapper_xml.add(checkBox_base_column, "cell 3 0");
                panel_mapper_xml.add(label_mapper_xml_template_type, "cell 0 1");

                //---- comboBox_mapper_xml_template_type ----
                comboBox_mapper_xml_template_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Beetl",
                    "Freemark"
                }));
                panel_mapper_xml.add(comboBox_mapper_xml_template_type, "cell 1 1");

                //---- checkBox_mapper_xml_edit_swith ----
                checkBox_mapper_xml_edit_swith.addActionListener(e -> checkBox_mapper_xml_edit_swithActionPerformed(e));
                panel_mapper_xml.add(checkBox_mapper_xml_edit_swith, "cell 3 1");

                //======== scrollPane_mapper_xml ========
                {

                    //---- textArea_mapper_xml ----
                    textArea_mapper_xml.setRows(15);
                    scrollPane_mapper_xml.setViewportView(textArea_mapper_xml);
                }
                panel_mapper_xml.add(scrollPane_mapper_xml, "cell 0 2 7 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_mapper_xml.title"), panel_mapper_xml);

            //======== panel_maven ========
            {
                panel_maven.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[108,fill]" +
                    "[44,fill]" +
                    "[99,fill]" +
                    "[80,fill]" +
                    "[65,fill]" +
                    "[38,fill]" +
                    "[46,fill]" +
                    "[163,fill]",
                    // rows
                    "[]" +
                    "[5]" +
                    "[310,top]"));

                //---- checkBox_maven_repo ----
                checkBox_maven_repo.addActionListener(e -> checkBox_maven_sfActionPerformed(e));
                panel_maven.add(checkBox_maven_repo, "cell 0 0");

                //---- textField_maven_repo ----
                textField_maven_repo.setEditable(false);
                panel_maven.add(textField_maven_repo, "cell 1 0 4 1");
                panel_maven.add(checkBox_maven_overwrite_pom, "cell 6 0");
                panel_maven.add(label_maven_template_type, "cell 0 1,alignx right,growx 0");

                //---- comboBox_maven_template_type ----
                comboBox_maven_template_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "Beetl",
                    "Freemark"
                }));
                panel_maven.add(comboBox_maven_template_type, "cell 1 1");

                //---- checkBox_maven_edit_swith ----
                checkBox_maven_edit_swith.addActionListener(e -> checkBox_maven_edit_swithActionPerformed(e));
                panel_maven.add(checkBox_maven_edit_swith, "cell 3 1");
                panel_maven.add(checkBox_maven_interceptor, "cell 6 1");

                //======== scrollPane_maven ========
                {

                    //---- textArea_maven ----
                    textArea_maven.setRows(15);
                    scrollPane_maven.setViewportView(textArea_maven);
                }
                panel_maven.add(scrollPane_maven, "cell 0 2 9 1");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_maven.title"), panel_maven);

            //======== panel_spring_boot ========
            {
                panel_spring_boot.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[62,fill]" +
                    "[fill]" +
                    "[fill]" +
                    "[76,fill]" +
                    "[80,fill]" +
                    "[64,fill]" +
                    "[264,fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[20]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]" +
                    "[250,top]"));
                panel_spring_boot.add(label_config_type_name, "cell 0 0");

                //---- comboBox_spring_boot_config_file_type ----
                comboBox_spring_boot_config_file_type.setModel(new DefaultComboBoxModel<>(new String[] {
                    "yml"
                }));
                panel_spring_boot.add(comboBox_spring_boot_config_file_type, "cell 1 0,growx");
                panel_spring_boot.add(label_sb_jicheng, "cell 0 2,alignx right,growx 0");
                panel_spring_boot.add(checkBox_sb_mica, "cell 1 2");
                panel_spring_boot.add(checkBox_sb_actuator, "cell 2 2");
                panel_spring_boot.add(checkBox_sb_prometheus, "cell 3 2");
                panel_spring_boot.add(checkBox_sb_jasypt, "cell 4 2");
                panel_spring_boot.add(checkBox_sb_hakiri, "cell 1 3");
                panel_spring_boot.add(checkBox_sb_druid, "cell 2 3");
                panel_spring_boot.add(checkBox_sb_mongo, "cell 3 3");
                panel_spring_boot.add(checkBox_sb_redis, "cell 4 3");
                panel_spring_boot.add(checkBox_sb_redis_lock, "cell 5 3");

                //---- checkBox_sb_rabbitmq ----
                checkBox_sb_rabbitmq.addActionListener(e -> checkBox_sb_rabbitmqActionPerformed(e));
                panel_spring_boot.add(checkBox_sb_rabbitmq, "cell 1 4");

                //---- checkBox_sb_stream_rabbitmq ----
                checkBox_sb_stream_rabbitmq.addActionListener(e -> checkBox_sb_stream_rabbitmqActionPerformed(e));
                panel_spring_boot.add(checkBox_sb_stream_rabbitmq, "cell 2 4");

                //---- checkBox_sb_kafka ----
                checkBox_sb_kafka.addActionListener(e -> checkBox_sb_kafkaActionPerformed(e));
                panel_spring_boot.add(checkBox_sb_kafka, "cell 3 4");

                //---- checkBox_sb_stream_kafak ----
                checkBox_sb_stream_kafak.addActionListener(e -> checkBox_sb_stream_kafakActionPerformed(e));
                panel_spring_boot.add(checkBox_sb_stream_kafak, "cell 4 4");
                panel_spring_boot.add(checkBox_sb_okhttp, "cell 1 5");
                panel_spring_boot.add(checkBox_sb_freemarker, "cell 2 5");
                panel_spring_boot.add(checkBox_sb_docker, "cell 1 6");
            }
            tabbedPane.addTab(bundle.getString("global.config.dialog.panel_spring_boot.title"), panel_spring_boot);
        }
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        //---- buttonGroup_sb_db_pool ----
        ButtonGroup buttonGroup_sb_db_pool = new ButtonGroup();
        buttonGroup_sb_db_pool.add(checkBox_sb_hakiri);
        buttonGroup_sb_db_pool.add(checkBox_sb_druid);

        initComponentsI18n();

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of data initialization  //GEN-END:initComponents
    }

    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        setTitle(bundle.getString("global.config.dialog.this.title"));
        label_author.setText(bundle.getString("global.config.dialog.label_author.text"));
        button_save.setText(bundle.getString("global.config.dialog.button_save.text"));
        label_description.setText(bundle.getString("global.config.dialog.label_description.text"));
        button_reset.setText(bundle.getString("global.config.dialog.button_reset.text"));
        label1.setText(bundle.getString("global.config.dialog.label1.text"));
        checkBox_overwrite.setText(bundle.getString("global.config.dialog.checkBox_overwrite.text"));
        checkBox_overwrite.setToolTipText(bundle.getString("global.config.dialog.checkBox_overwrite.toolTipText"));
        label_fun.setText(bundle.getString("global.config.dialog.label_fun.text"));
        checkBox_mapper.setText(bundle.getString("global.config.dialog.checkBox_mapper.text"));
        checkBox_mapper_xml.setText(bundle.getString("global.config.dialog.checkBox_mapper_xml.text"));
        checkBox_dao.setText(bundle.getString("global.config.dialog.checkBox_dao.text"));
        checkBox_service.setText(bundle.getString("global.config.dialog.checkBox_service.text"));
        checkBox_controller.setText(bundle.getString("global.config.dialog.checkBox_controller.text"));
        label_logo.setToolTipText(bundle.getString("global.config.dialog.label_logo.toolTipText"));
        tabbedPane.setTitleAt(0, bundle.getString("global.config.dialog.panel_guide.tab.title"));
        label_entity_package.setText(bundle.getString("global.config.dialog.label_entity_package.text"));
        label_entity_super_class.setText(bundle.getString("global.config.dialog.label_entity_super_class.text"));
        checkBox_table_prefix.setText(bundle.getString("global.config.dialog.checkBox_table_prefix.text"));
        textField_table_prefix.setToolTipText(bundle.getString("global.config.dialog.textField_table_prefix.toolTipText"));
        button_type_mapping.setText(bundle.getString("global.config.dialog.button_type_mapping.text"));
        label_entity_template_type.setText(bundle.getString("global.config.dialog.label_entity_template_type.text"));
        checkBox_entity_edit_swith.setText(bundle.getString("global.config.dialog.checkBox_entity_edit_swith.text"));
        checkBox_lombok.setText(bundle.getString("global.config.dialog.checkBox_lombok.text"));
        checkBox_is_fill.setText(bundle.getString("global.config.dialog.checkBox_is_fill.text"));
        label_entity_key_strategy.setText(bundle.getString("global.config.dialog.label_entity_key_strategy.text"));
        tabbedPane.setTitleAt(1, bundle.getString("global.config.dialog.panel_entity.tab.title"));
        label_mapper_package.setText(bundle.getString("global.config.dialog.label_mapper_package.text"));
        label_mapper_super_class.setText(bundle.getString("global.config.dialog.label_mapper_super_class.text"));
        label_mapper_template_type.setText(bundle.getString("global.config.dialog.label_mapper_template_type.text"));
        tabbedPane.setTitleAt(2, bundle.getString("global.config.dialog.panel_mapper.tab.title"));
        label_dao_package.setText(bundle.getString("global.config.dialog.label_dao_package.text"));
        label_dao_super_class.setText(bundle.getString("global.config.dialog.label_dao_super_class.text"));
        label_dao_template_type.setText(bundle.getString("global.config.dialog.label_dao_template_type.text"));
        checkBox_dao_edit_swith.setText(bundle.getString("global.config.dialog.checkBox_dao_edit_swith.text"));
        tabbedPane.setTitleAt(3, bundle.getString("global.config.dialog.panel_dao.title"));
        label_service_package.setText(bundle.getString("global.config.dialog.label_service_package.text"));
        label_service_super_class.setText(bundle.getString("global.config.dialog.label_service_super_class.text"));
        label_service_template_type.setText(bundle.getString("global.config.dialog.label_service_template_type.text"));
        checkBox_service_edit_swith.setText(bundle.getString("global.config.dialog.checkBox_service_edit_swith.text"));
        tabbedPane.setTitleAt(4, bundle.getString("global.config.dialog.panel_service.title"));
        label_service_package_impl.setText(bundle.getString("global.config.dialog.label_service_package_impl.text"));
        label_service_super_class_impl.setText(bundle.getString("global.config.dialog.label_service_super_class_impl.text"));
        label_service_template_type_impl.setText(bundle.getString("global.config.dialog.label_service_template_type_impl.text"));
        checkBox_service_edit_swith_impl.setText(bundle.getString("global.config.dialog.checkBox_service_edit_swith_impl.text"));
        tabbedPane.setTitleAt(5, bundle.getString("global.config.dialog.panel_service_impl.title"));
        label_controller_package.setText(bundle.getString("global.config.dialog.label_controller_package.text"));
        label_controller_super_class.setText(bundle.getString("global.config.dialog.label_controller_super_class.text"));
        label_controller_template_type.setText(bundle.getString("global.config.dialog.label_controller_template_type.text"));
        checkBox_controller_edit_swith.setText(bundle.getString("global.config.dialog.checkBox_controller_edit_swith.text"));
        tabbedPane.setTitleAt(6, bundle.getString("global.config.dialog.panel_controller.title"));
        label_mapper_xml_package.setText(bundle.getString("global.config.dialog.label_mapper_xml_package.text"));
        checkBox_base_column.setText(bundle.getString("global.config.dialog.checkBox_base_column.text"));
        label_mapper_xml_template_type.setText(bundle.getString("global.config.dialog.label_mapper_xml_template_type.text"));
        checkBox_mapper_xml_edit_swith.setText(bundle.getString("global.config.dialog.checkBox_mapper_xml_edit_swith.text"));
        tabbedPane.setTitleAt(7, bundle.getString("global.config.dialog.panel_mapper_xml.title"));
        checkBox_maven_repo.setText(bundle.getString("global.config.dialog.checkBox_maven_repo.text"));
        textField_maven_repo.setText(bundle.getString("global.config.dialog.textField_maven_repo.text"));
        checkBox_maven_overwrite_pom.setText(bundle.getString("global.config.dialog.checkBox_maven_overwrite_pom.text"));
        checkBox_maven_overwrite_pom.setToolTipText(bundle.getString("global.config.dialog.checkBox_maven_overwrite_pom.toolTipText"));
        label_maven_template_type.setText(bundle.getString("global.config.dialog.label_maven_template_type.text"));
        checkBox_maven_edit_swith.setText(bundle.getString("global.config.dialog.checkBox_maven_edit_swith.text"));
        checkBox_maven_interceptor.setText(bundle.getString("global.config.dialog.checkBox_maven_interceptor.text"));
        checkBox_maven_interceptor.setToolTipText(bundle.getString("global.config.dialog.checkBox_maven_interceptor.toolTipText"));
        tabbedPane.setTitleAt(8, bundle.getString("global.config.dialog.panel_maven.title"));
        label_config_type_name.setText(bundle.getString("global.config.dialog.label_config_type_name.text"));
        label_sb_jicheng.setText(bundle.getString("global.config.dialog.label_sb_jicheng.text"));
        checkBox_sb_mica.setText(bundle.getString("global.config.dialog.checkBox_sb_mica.text"));
        checkBox_sb_actuator.setText(bundle.getString("global.config.dialog.checkBox_sb_actuator.text"));
        checkBox_sb_prometheus.setText(bundle.getString("global.config.dialog.checkBox_sb_prometheus.text"));
        checkBox_sb_jasypt.setText(bundle.getString("global.config.dialog.checkBox_sb_jasypt.text"));
        checkBox_sb_hakiri.setText(bundle.getString("global.config.dialog.checkBox_sb_hakiri.text"));
        checkBox_sb_druid.setText(bundle.getString("global.config.dialog.checkBox_sb_druid.text"));
        checkBox_sb_mongo.setText(bundle.getString("global.config.dialog.checkBox_sb_mongo.text"));
        checkBox_sb_redis.setText(bundle.getString("global.config.dialog.checkBox_sb_redis.text"));
        checkBox_sb_redis_lock.setText(bundle.getString("global.config.dialog.checkBox_sb_redis_lock.text"));
        checkBox_sb_rabbitmq.setText(bundle.getString("global.config.dialog.checkBox_sb_rabbitmq.text"));
        checkBox_sb_stream_rabbitmq.setText(bundle.getString("global.config.dialog.checkBox_sb_stream_rabbitmq.text"));
        checkBox_sb_kafka.setText(bundle.getString("global.config.dialog.checkBox_sb_kafka.text"));
        checkBox_sb_stream_kafak.setText(bundle.getString("global.config.dialog.checkBox_sb_stream_kafak.text"));
        checkBox_sb_okhttp.setText(bundle.getString("global.config.dialog.checkBox_sb_okhttp.text"));
        checkBox_sb_freemarker.setText(bundle.getString("global.config.dialog.checkBox_sb_freemarker.text"));
        checkBox_sb_docker.setText(bundle.getString("global.config.dialog.checkBox_sb_docker.text"));
        tabbedPane.setTitleAt(9, bundle.getString("global.config.dialog.panel_spring_boot.title"));
        // JFormDesigner - End of data i18n initialization  //GEN-END:initI18n
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label_author;
    private JTextField textField_author;
    private JButton button_save;
    private JLabel label_description;
    private JTextField textField_description;
    private JButton button_reset;
    private JLabel label1;
    private JComboBox comboBox_project;
    private JCheckBox checkBox_overwrite;
    private JLabel label_fun;
    private JCheckBox checkBox_mapper;
    private JCheckBox checkBox_mapper_xml;
    private JCheckBox checkBox_dao;
    private JCheckBox checkBox_service;
    private JCheckBox checkBox_controller;
    private JTabbedPane tabbedPane;
    private JLabel label_logo;
    private JPanel panel_entity;
    private JLabel label_entity_package;
    private JTextField textField_entity_package;
    private JLabel label_entity_super_class;
    private JTextField textField_entity_super_class;
    private JCheckBox checkBox_table_prefix;
    private JTextField textField_table_prefix;
    private JButton button_type_mapping;
    private JLabel label_entity_template_type;
    private JComboBox<String> comboBox_entity_template_type;
    private JCheckBox checkBox_entity_edit_swith;
    private JCheckBox checkBox_lombok;
    private JCheckBox checkBox_is_fill;
    private JLabel label_entity_key_strategy;
    private JComboBox comboBox_entity_key_strategy;
    private JScrollPane scrollPane_entity;
    private JTextArea textArea_entity;
    private JPanel panel_mapper;
    private JLabel label_mapper_package;
    private JTextField textField_mapper_package;
    private JLabel label_mapper_super_class;
    private JTextField textField_mapper_super_class;
    private JLabel label_mapper_template_type;
    private JComboBox<String> comboBox_mapper_template_type;
    private JCheckBox checkBox_mapper_edit_swith;
    private JScrollPane scrollPane_mapper;
    private JTextArea textArea_mapper;
    private JPanel panel_dao;
    private JLabel label_dao_package;
    private JTextField textField_dao_package;
    private JLabel label_dao_super_class;
    private JTextField textField_dao_super_class;
    private JLabel label_dao_template_type;
    private JComboBox<String> comboBox_dao_template_type;
    private JCheckBox checkBox_dao_edit_swith;
    private JScrollPane scrollPane_dao;
    private JTextArea textArea_dao;
    private JPanel panel_service;
    private JLabel label_service_package;
    private JTextField textField_service_package;
    private JLabel label_service_super_class;
    private JTextField textField_service_super_class;
    private JLabel label_service_template_type;
    private JComboBox<String> comboBox_service_template_type;
    private JCheckBox checkBox_service_edit_swith;
    private JScrollPane scrollPane_service;
    private JTextArea textArea_service;
    private JPanel panel_service_impl;
    private JLabel label_service_package_impl;
    private JTextField textField_service_package_impl;
    private JLabel label_service_super_class_impl;
    private JTextField textField_service_super_class_impl;
    private JLabel label_service_template_type_impl;
    private JComboBox<String> comboBox_service_template_type_impl;
    private JCheckBox checkBox_service_edit_swith_impl;
    private JScrollPane scrollPane_service_impl;
    private JTextArea textArea_service_impl;
    private JPanel panel_controller;
    private JLabel label_controller_package;
    private JTextField textField_controller_package;
    private JLabel label_controller_super_class;
    private JTextField textField_controller_super_class;
    private JLabel label_controller_template_type;
    private JComboBox<String> comboBox_controller_template_type;
    private JCheckBox checkBox_controller_edit_swith;
    private JScrollPane scrollPane_controller;
    private JTextArea textArea_controller;
    private JPanel panel_mapper_xml;
    private JLabel label_mapper_xml_package;
    private JTextField textField_mapper_xml_package;
    private JCheckBox checkBox_base_column;
    private JLabel label_mapper_xml_template_type;
    private JComboBox<String> comboBox_mapper_xml_template_type;
    private JCheckBox checkBox_mapper_xml_edit_swith;
    private JTextArea textArea_mapper_xml;
    private JPanel panel_maven;
    private JCheckBox checkBox_maven_repo;
    private JTextField textField_maven_repo;
    private JCheckBox checkBox_maven_overwrite_pom;
    private JLabel label_maven_template_type;
    private JComboBox<String> comboBox_maven_template_type;
    private JCheckBox checkBox_maven_edit_swith;
    private JCheckBox checkBox_maven_interceptor;
    private JTextArea textArea_maven;
    private JPanel panel_spring_boot;
    private JLabel label_config_type_name;
    private JComboBox<String> comboBox_spring_boot_config_file_type;
    private JLabel label_sb_jicheng;
    private JCheckBox checkBox_sb_mica;
    private JCheckBox checkBox_sb_actuator;
    private JCheckBox checkBox_sb_prometheus;
    private JCheckBox checkBox_sb_jasypt;
    private JCheckBox checkBox_sb_hakiri;
    private JCheckBox checkBox_sb_druid;
    private JCheckBox checkBox_sb_mongo;
    private JCheckBox checkBox_sb_redis;
    private JCheckBox checkBox_sb_redis_lock;
    private JCheckBox checkBox_sb_rabbitmq;
    private JCheckBox checkBox_sb_stream_rabbitmq;
    private JCheckBox checkBox_sb_kafka;
    private JCheckBox checkBox_sb_stream_kafak;
    private JCheckBox checkBox_sb_okhttp;
    private JCheckBox checkBox_sb_freemarker;
    private JCheckBox checkBox_sb_docker;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
