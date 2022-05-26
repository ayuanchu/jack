package com.kancy.mybatisplus.generator.core;

import com.kancy.mybatisplus.generator.config.*;
import com.kancy.mybatisplus.generator.core.model.EntityModel;
import com.kancy.mybatisplus.generator.core.model.MavenModel;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.exception.CodeGeneratorException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.FileUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.view.MavenProjectDialog;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * CodeGenerator
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 19:24
 **/
public class CodeGenerator {

    private GlobalConfig globalConfig;

    private String entityName;

    private String basePackageName;

    private String baseModulePath;

    private long tableCount;

    private long tableIndex;

    private Map<String, Object> models = Collections.emptyMap();

    protected CodeGenerator() {

    }

    public Optional<String> getEntity(GlobalEntityConfig config){
        if (Objects.isNull(config)){
            return Optional.empty();
        }
        return getTemplateResult(config.getTemplate(), config.getTemplatePath());
    }

    public Optional<String> getMapperXml(GlobalMapperXmlConfig config){
        if (Objects.isNull(config)){
            return Optional.empty();
        }
        return getTemplateResult(config.getTemplate(), config.getTemplatePath());
    }

    public void generator(){
        try {
            generatorEntity();
            generatorMapper();
            generatorMapperXml();
            generatorDao();
            generatorService();
            generatorServiceImpl();
            generatorController();
            generatorMavenPom();
            generatorSpringBoot();
        } catch (AlertException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CodeGeneratorException("生成文件失败！", e);
        }
    }

    private void generatorEntity() {
        GlobalEntityConfig config = globalConfig.getEntity();
        Optional<String> render = getEntity(config);
        if (render.isPresent()){
            writeOutRenderContent(render.get(), getMavenSrcPathIfEnabled(), config.getPackageName(),
                    String.format(config.getFileNameFormat(), entityName));
        }
    }

    private void generatorMapper() {
        GlobalMapperConfig config = globalConfig.getMapper();
        if (!config.isEnabled()){
            return;
        }

        Optional<String> render = getTemplateResult(config.getTemplate(), config.getTemplatePath());
        if (render.isPresent()){
            writeOutRenderContent(render.get(), getMavenSrcPathIfEnabled(),
                    config.getPackageName(),
                    String.format(config.getFileNameFormat(), entityName));
        }
    }


    private void generatorMapperXml() {
        GlobalMapperXmlConfig config = globalConfig.getMapperXml();
        if (!config.isEnabled()){
            return;
        }

        Optional<String> render = getMapperXml(config);
        if (render.isPresent()){
            if (globalConfig.getMaven().isEnabled()){
                writeOutRenderContent(render.get(), globalConfig.getMaven().getResourcePath(),
                        config.getPackageName(),
                        String.format(config.getFileNameFormat(), entityName),
                        true);
            }else {
                writeOutRenderContent(render.get(),"",
                        config.getPackageName() ,
                        String.format(config.getFileNameFormat(), entityName));
            }
        }
    }

    private void generatorDao() {
        GlobalDaoConfig config = globalConfig.getDao();
        if (!config.isEnabled()){
            return;
        }

        Optional<String> render = getTemplateResult(config.getTemplate(), config.getTemplatePath());
        if (render.isPresent()){
            writeOutRenderContent(render.get(), getMavenSrcPathIfEnabled(),
                    config.getPackageName() ,
                    String.format(config.getFileNameFormat(), entityName));
        }
    }
    private void generatorService() {
        GlobalServiceConfig config = globalConfig.getService();
        if (!globalConfig.getService().isEnabled()){
            return;
        }

        Optional<String> render = getTemplateResult(config.getTemplate(), config.getTemplatePath());
        if (render.isPresent()){
            writeOutRenderContent(render.get(), getMavenSrcPathIfEnabled(),
                    config.getPackageName() ,
                    String.format(config.getFileNameFormat(), entityName));
        }
    }

    private void generatorServiceImpl() {
        GlobalServiceImplConfig config = globalConfig.getServiceImpl();
        if (!config.isEnabled()){
            return;
        }
        Optional<String> render = getTemplateResult(config.getTemplate(), config.getTemplatePath());
        if (render.isPresent()){
            writeOutRenderContent(render.get(), getMavenSrcPathIfEnabled(),
                    config.getPackageName() ,
                    String.format(config.getFileNameFormat(), entityName));
        }
    }

    private void generatorController() {
        GlobalControllerConfig config = globalConfig.getController();
        if (!config.isEnabled()){
            return;
        }

        Optional<String> render = getTemplateResult(config.getTemplate(), config.getTemplatePath());
        if (render.isPresent()){
            writeOutRenderContent(render.get(), getMavenSrcPathIfEnabled(),
                    config.getPackageName() ,
                    String.format(config.getFileNameFormat(), entityName));
        }
    }

    private void generatorMavenPom() {
        GlobalMavenConfig config = globalConfig.getMaven();
        if (!config.isEnabled()){
            return;
        }

        // 不是最后一只表，不开始生成pom.xml
        if (tableCount != tableIndex){
            return;
        }

        File file = FileUtils.getFile(baseModulePath, config.getFileNameFormat());
        if (file.exists() && file.isFile() &&
                (!globalConfig.isOverwriteEnabled() || !config.isOverwritePomEnabled())){

            if (globalConfig.isOverwriteEnabled() && !config.isOverwritePomEnabled()){
                // 询问是否需要覆盖
                if (AlertUtils.yesOrNo("POM文件已存在，是否需要覆盖文件？")){
                    try {
                        globalConfig.getMaven().setOverwritePomEnabled(true);
                        GlobalConfigManager.store();
                    } catch (Exception e) {
                        Log.error(e.getMessage());
                    }
                }
            }

            if (!globalConfig.isOverwriteEnabled() || !config.isOverwritePomEnabled()){
                Log.debug("不允许覆盖已存在的POM文件：%s", file.getAbsolutePath());
                return;
            }
        }

        // 用户交互
        // 允许用户确认或者修改maven工程构建信息
        if (globalConfig.getMaven().isInterceptorEnabled()){
            MavenModel mavenModel = (MavenModel) models.get("pom");
            MavenProjectDialog dialog = new MavenProjectDialog(mavenModel);
            dialog.setVisible(true);
        }

        Optional<String> render = getTemplateResult(config.getTemplate(), config.getTemplatePath(), config.isUseCustom());
        if (render.isPresent()){
            writeOutRenderContent(render.get(),"",
                    "",
                    config.getFileNameFormat(), true);

            // 创建测试文件夹
            FileUtils.getFile(baseModulePath, config.getTestPath()).mkdirs();
        }

    }

    private void generatorSpringBoot() {
        GlobalSpringBootConfig config = globalConfig.getSpringBoot();
        if (!config.isEnabled()){
            return;
        }

        // 不是最后一只表，不开始生成pom.xml
        if (tableCount != tableIndex){
            return;
        }

        // application.yml.ftl
        if (config.getApplicationResourceFile().isEnabled()) {
            Optional<String> applicationResourceFile = getTemplateResult(config.getApplicationResourceFile().getTemplate(),
                    config.getApplicationResourceFile().getTemplatePath());
            if (applicationResourceFile.isPresent()){
                writeOutRenderContent(applicationResourceFile.get(), getMavenResourcePathIfEnabled(),
                        "",
                        config.getApplicationResourceFile().getFileNameFormat(), true);
            }
        }

        // application-db.yml
        if (config.getApplicationDbResourceFile().isEnabled()) {
            Optional<String> applicationDbResourceFile = getTemplateResult(config.getApplicationDbResourceFile().getTemplate(),
                    config.getApplicationDbResourceFile().getTemplatePath());
            if (applicationDbResourceFile.isPresent()){
                writeOutRenderContent(applicationDbResourceFile.get(),getMavenResourcePathIfEnabled(),
                        "",
                        config.getApplicationDbResourceFile().getFileNameFormat(), true);
            }
        }

        // mybatisplus config
        if (config.getMybatisPlusConfigClassFile().isEnabled()) {
            Optional<String> mybatisPlusConfigClassFile = getTemplateResult(config.getMybatisPlusConfigClassFile().getTemplate(),
                    config.getMybatisPlusConfigClassFile().getTemplatePath());
            if (mybatisPlusConfigClassFile.isPresent()) {
                writeOutRenderContent(mybatisPlusConfigClassFile.get(), getMavenSrcPathIfEnabled(),
                        config.getMybatisPlusConfigClassFile().getPackageName(),
                        config.getMybatisPlusConfigClassFile().getFileNameFormat());
            }
        }

        // Application
        if (config.getApplicationClassFile().isEnabled()){
            Optional<String> applicationClassFile = getTemplateResult(config.getApplicationClassFile().getTemplate(),
                    config.getApplicationClassFile().getTemplatePath());
            if (applicationClassFile.isPresent()){
                writeOutRenderContent(applicationClassFile.get(), getMavenSrcPathIfEnabled(),
                        "",
                        config.getApplicationClassFile().getFileNameFormat());
            }
        }

        // banner
        if (config.getBannerFile().isEnabled()){
            Optional<String> bannerFile = getTemplateResult(config.getBannerFile().getTemplate(),
                    config.getBannerFile().getTemplatePath());
            if (bannerFile.isPresent()){
                writeOutRenderContent(bannerFile.get(), getMavenResourcePathIfEnabled(),
                        "",
                        config.getBannerFile().getFileNameFormat(), true);
            }
        }

        // logback
        if (config.getLogbackFile().isEnabled()){
            Optional<String> logbackFile = getTemplateResult(config.getLogbackFile().getTemplate(),
                    config.getLogbackFile().getTemplatePath());
            if (logbackFile.isPresent()){
                writeOutRenderContent(logbackFile.get(), getMavenResourcePathIfEnabled(),
                        "",
                        config.getLogbackFile().getFileNameFormat(), true);
            }
        }

    }

    private Optional<String> getTemplateResult(String templateData, String backupTemplatePath) {
        return getTemplateResult(templateData, backupTemplatePath, false);
    }

    private Optional<String> getTemplateResult(String templateData, String backupTemplatePath, boolean useCustom) {
        Optional<String> render = null;
        if (!useCustom || StringUtils.isEmpty(templateData)){
            render = ServiceManager.getTemplateService().render(backupTemplatePath, models);
        } else {
            render = ServiceManager.getTemplateService().execute(templateData, models);
        }
        return render;
    }

    private void writeOutRenderContent(String data, String mvnPath, String simplePackageName, String fileName, boolean noPackage) {
        File file = FileUtils.getFile(baseModulePath, mvnPath,
                noPackage ? "" : basePackageName.replace(".", "/"),
                simplePackageName.replace(".", "/"), fileName);
        try {
            if (file.exists() && file.isFile() && !globalConfig.isOverwriteEnabled()){
                // 不允许覆盖文件
                Log.debug("不允许覆盖已存在的文件：%s", file.getAbsolutePath());
                return;
            }
            FileUtils.writeStringToFile(file, data, Settings.getDefaultCharset());
            Log.debug("Write out file success : %s", file.getAbsolutePath());
        } catch (IOException e) {
            throw new CodeGeneratorException("写文件失败！", e);
        }
    }

    private void writeOutRenderContent(String data, String mvnPath, String simplePackageName, String fileName) {
        writeOutRenderContent(data, mvnPath, simplePackageName, fileName, false);
    }

    private String getMavenSrcPathIfEnabled() {
        return globalConfig.getMaven().isEnabled() ? globalConfig.getMaven().getSrcPath() : "";
    }
    private String getMavenResourcePathIfEnabled() {
        return globalConfig.getMaven().isEnabled() ? globalConfig.getMaven().getResourcePath() : "";
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public void setModels(Map<String, Object> models) {
        this.models = models;
    }

    public EntityModel getEntityModel() {
        return (EntityModel) models.get("entity");
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public void setBaseModulePath(String baseModulePath) {
        this.baseModulePath = baseModulePath;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setTableCount(long tableCount) {
        this.tableCount = tableCount;
    }

    public void setTableIndex(long tableIndex) {
        this.tableIndex = tableIndex;
    }

    public long getTableCount() {
        return tableCount;
    }

    public long getTableIndex() {
        return tableIndex;
    }

    public String getEntityName() {
        return entityName;
    }
}
