package com.kancy.mybatisplus.generator.core;

import com.kancy.mybatisplus.generator.config.*;
import com.kancy.mybatisplus.generator.core.model.*;
import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.enums.KeyStrategyEnum;
import com.kancy.mybatisplus.generator.exception.ParamException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.manager.TableColumnCacheManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.utils.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * <p>
 * CodeGeneratorFactory
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/11 12:38
 **/

public class CodeGeneratorFactory {

    private static final List<String> fillUpdateFieldNames = Arrays.asList(new String[]{"updateTime","updatedTime","updateDate"});

    public static Builder builder(){
        return new Builder();
    }

    @Data
    @Accessors(chain = true)
    public static class Builder {

        private Builder() {

        }

        private long tableCount;

        private long tableIndex;

        private GlobalConfig globalConfig;

        private String entityName;

        private String tableName;

        private String tableRemark;

        private String databaseName;

        private String basePackageName;

        private String baseModulePath;

        private List<ColumnInfo> columnInfos;

        public CodeGenerator build() {
            CodeGenerator codeGenerator = new CodeGenerator();
            codeGenerator.setTableCount(tableCount);
            codeGenerator.setTableIndex(tableIndex);
            codeGenerator.setGlobalConfig(globalConfig);
            codeGenerator.setBaseModulePath(baseModulePath);
            codeGenerator.setBasePackageName(basePackageName);
            codeGenerator.setEntityName(getEntityNameByTableName());
            codeGenerator.setModels(buildDataModel());
            return codeGenerator;
        }


        private Map<String, Object> buildDataModel() {
            Map<String, Object> map = new HashMap<>();
            initDataModel(map);
            buildEntityDataModel(map);
            buildMapperDataModel(map);
            buildDaoDataModel(map);
            buildServiceDataModel(map);
            buildServiceImplDataModel(map);
            buildControllerDataModel(map);
            buildMavenDataModel(map);
            buildSpringBootDataModel(map);
            return map;
        }

        private void buildBaseModelByGlobalConfig(BaseModel model, BaseClassConfig config) {
            model.setPackageName(String.format("%s.%s", basePackageName, config.getPackageName())
                    .replaceAll("\\.+","."));
            model.setClassName(String.format("%s.%s", model.getPackageName(), model.getName())
                    .replaceAll("\\.+","."));
            model.setSupperClassPackageName(config.getSupperClassName()
                    .substring(0, config.getSupperClassName().lastIndexOf(".")));
            model.setSupperClassSimpleName(config.getSupperClassName()
                    .replace(String.format("%s.", model.getSupperClassPackageName()), ""));
            model.setExtendsStatement(ClassUtils.isObjectClass(config.getSupperClassName()) ? "" :
                    String.format("extends %s ", model.getSupperClassSimpleName()));
        }


        private void buildSpringBootDataModel(Map<String, Object> map) {
            DataSourceConfig dataSourceConfig = DataSourceManager.getActive();
            map.put("driverClassName", dataSourceConfig.getDriverClassName());
            if (Objects.equals(dataSourceConfig.getDriver(), DatabaseDriverEnum.MySQL.name())){
                map.put("driverClassName", "com.mysql.cj.jdbc.Driver");
            }
            map.put("jdbcUrl", dataSourceConfig.getJdbcUrl());
            if (!Objects.equals(dataSourceConfig.getDatabase(), databaseName)){
                if (Objects.equals(dataSourceConfig.getDriver(), DatabaseDriverEnum.MySQL.name())){
                    map.put("jdbcUrl", String.format(DatabaseDriverEnum.MySQL.getJdbcUrlFormat(), dataSourceConfig.getHost() ,dataSourceConfig.getPort(), databaseName));
                }
                if (Objects.equals(dataSourceConfig.getDriver(), DatabaseDriverEnum.Oracle.name())){
                    map.put("jdbcUrl", String.format(DatabaseDriverEnum.Oracle.getJdbcUrlFormat(), dataSourceConfig.getHost() ,dataSourceConfig.getPort(), databaseName));
                }
            }
        }


        private void buildMavenDataModel(Map<String, Object> map) {
            MavenModel model = new MavenModel();
            model.setGroupId(basePackageName);
            model.setArtifactId(FileUtils.getFile(baseModulePath).getName());
            model.setProjectName(model.getArtifactId());
            map.put("pom", model);
            map.put("maven", model);
        }

        private void buildControllerDataModel(Map<String, Object> map) {
            GlobalControllerConfig config = globalConfig.getController();
            ControllerModel model = new ControllerModel();
            model.setName(String.format(config.getClassNameFormat(), getEntityNameByTableName()));
            buildBaseModelByGlobalConfig(model, config);

            if (globalConfig.getSpringBoot().isMicaEnabled()){
                model.setExtendsStatement(model.getExtendsStatement() + "implements IController ");
                model.getImportClasses().add("net.dreamlu.mica.common.support.IController");
            }

            if (globalConfig.isLombokEnabled()){
                model.getClassAnnotationList().add("@Slf4j");
                model.getClassAnnotationList().add("@RequiredArgsConstructor");
                model.getImportClasses().add("lombok.extern.slf4j.Slf4j");
                model.getImportClasses().add("lombok.RequiredArgsConstructor");
            }

            // 继承类
            if (!StringUtils.isEmpty(globalConfig.getController().getSupperClassName())
                && !Objects.equals(Object.class.getName(), globalConfig.getController().getSupperClassName())){
                model.getImportClasses().add(config.getSupperClassName());
            }

            ArrayList<String> autowires = new ArrayList<>();
            String key = globalConfig.getService().isEnabled() ? "service" :
                    (globalConfig.getDao().isEnabled() ? "dao" : (
                            globalConfig.getMapper().isEnabled() ? "mapper" : ""));

            if (!StringUtils.isEmpty(key)) {
                BaseModel baseModel = (BaseModel) map.get(key);
                if (globalConfig.isLombokEnabled()) {
                    autowires.add(String.format("private final %s %s;", baseModel.getName(), StringUtils.firstLower(baseModel.getName())));
                    model.getImportClasses().add(baseModel.getClassName());
                }else {
                    autowires.add("@Autowired");
                    autowires.add(String.format("private %s %s;", baseModel.getName(), StringUtils.firstLower(baseModel.getName())));
                    model.getImportClasses().add(baseModel.getClassName());
                    model.getImportClasses().add("org.springframework.beans.factory.annotation.Autowired");
                }
            }

            model.setAutowires(autowires);
            map.put("controller", model);
        }


        private void buildServiceImplDataModel(Map<String, Object> map) {
            GlobalServiceImplConfig config = globalConfig.getServiceImpl();
            ServiceImplModel model = new ServiceImplModel();
            model.setName(String.format(config.getClassNameFormat(), getEntityNameByTableName()));
            buildBaseModelByGlobalConfig(model, config);

            if (globalConfig.isLombokEnabled()){
                model.getClassAnnotationList().add("@Slf4j");
                model.getClassAnnotationList().add("@RequiredArgsConstructor");
                model.getImportClasses().add("lombok.extern.slf4j.Slf4j");
                model.getImportClasses().add("lombok.RequiredArgsConstructor");
            }

            // 继承类
            if (!StringUtils.isEmpty(globalConfig.getController().getSupperClassName())
                    && !Objects.equals(Object.class.getName(), globalConfig.getController().getSupperClassName())){
                model.getImportClasses().add(config.getSupperClassName());
            }

            ArrayList<String> autowires = new ArrayList<>();
            String key = globalConfig.getDao().isEnabled() ? "dao" : (globalConfig.getMapper().isEnabled() ? "mapper" : "");

            if (!StringUtils.isEmpty(key)) {
                BaseModel baseModel = (BaseModel) map.get(key);
                if (globalConfig.isLombokEnabled()) {
                    autowires.add(String.format("private final %s %s;", baseModel.getName(), StringUtils.firstLower(baseModel.getName())));
                    model.getImportClasses().add(baseModel.getClassName());
                }else {
                    autowires.add("@Autowired");
                    autowires.add(String.format("private %s %s;", baseModel.getName(), StringUtils.firstLower(baseModel.getName())));
                    model.getImportClasses().add(baseModel.getClassName());
                    model.getImportClasses().add("org.springframework.beans.factory.annotation.Autowired");
                }
            }

            model.setAutowires(autowires);
            map.put("serviceImpl", model);
        }

        private void buildServiceDataModel(Map<String, Object> map) {
            GlobalServiceConfig config = globalConfig.getService();
            ServiceModel model = new ServiceModel();
            model.setName(String.format(config.getClassNameFormat(), getEntityNameByTableName()));
            buildBaseModelByGlobalConfig(model, config);

            // 继承类
            if (!StringUtils.isEmpty(model.getExtendsStatement())){
                model.getImportClasses().add(config.getSupperClassName());
            }
            map.put("service", model);

        }

        private void buildDaoDataModel(Map<String, Object> map) {
            GlobalDaoConfig config = globalConfig.getDao();
            DaoModel model = new DaoModel();
            model.setName(String.format(config.getClassNameFormat(), getEntityNameByTableName()));
            buildBaseModelByGlobalConfig(model, config);

            if (globalConfig.isLombokEnabled()){
                model.getClassAnnotationList().add("@Slf4j");
                model.getImportClasses().add("lombok.extern.slf4j.Slf4j");
            }
            map.put("dao", model);
        }

        private void buildMapperDataModel(Map<String, Object> map) {
            GlobalMapperConfig config = globalConfig.getMapper();
            MapperModel model = new MapperModel();
            model.setName(String.format(config.getClassNameFormat(), getEntityNameByTableName()));
            buildBaseModelByGlobalConfig(model, config);
            map.put("mapper", model);
        }

        private void buildEntityDataModel(Map<String, Object> map) {
            GlobalEntityConfig config = globalConfig.getEntity();

            EntityModel model = new EntityModel();
            model.setName(getEntityNameByTableName());
            model.setColumnInfos(columnInfos);
            model.setFields(getFieldsByColumns(columnInfos));
            buildBaseModelByGlobalConfig(model, config);

            // 计算需要加的类注解
            if (globalConfig.isLombokEnabled()){
                model.getClassAnnotationList().add("@Data");
                model.getClassAnnotationList().add("@NoArgsConstructor");
                model.getClassAnnotationList().add("@Accessors(chain = true)");
            }
            model.getClassAnnotationList().add(String.format("@TableName(\"%s\")", tableName));

            // 计算需要导入类
            if (globalConfig.isLombokEnabled()){
                model.getImportClasses().add("lombok.Data");
                model.getImportClasses().add("lombok.NoArgsConstructor");
                model.getImportClasses().add("lombok.experimental.Accessors");
            }
            model.getImportClasses().add("com.baomidou.mybatisplus.annotation.TableName");

            // 继承类
            if (!StringUtils.isEmpty(model.getExtendsStatement())){
                model.getImportClasses().add(config.getSupperClassName());
                // 如果是 com.baomidou.mybatisplus.extension.activerecord.Model 加上泛型
                String specialClassName = "com.baomidou.mybatisplus.extension.activerecord.Model";
                if (Objects.equals(config.getSupperClassName(), specialClassName)) {
                    model.setExtendsStatement(String.format("extends Model<%s> ", model.getName()));
                }
            }
            // 字段类型，主键
            HashMap<String, FieldModel> primaryMap = new HashMap<>();
            List<FieldModel> fields = model.getFields();
            for (FieldModel field : fields) {
                if (field.isPrimary()){
                    primaryMap.put(field.getName(), field);
                    model.getImportClasses().add("com.baomidou.mybatisplus.annotation.TableId");
                    String keyStrategy = globalConfig.getEntity().getKeyStrategy();
                    if (!Objects.equals(keyStrategy, KeyStrategyEnum.NONE.name())){
                        model.getImportClasses().add("com.baomidou.mybatisplus.annotation.IdType");
                    }
                }
                if (field.isFillUpdate() || field.isHasTableField()){
                    model.getImportClasses().add("com.baomidou.mybatisplus.annotation.TableField");
                }
                String typeClassName = field.getTypeClass().getName();
                if (!ClassUtils.isAutoImportClass(typeClassName)){
                    model.getImportClasses().add(typeClassName);
                }
            }

            // 多个主键时，优先选择id
            if (primaryMap.size() > 1){
                String keyStrategy = globalConfig.getEntity().getKeyStrategy();
                Object id = primaryMap.get("id");
                if (Objects.nonNull(id)){
                    primaryMap.remove("id");
                    primaryMap.forEach((key, fieldModel) -> {
                        fieldModel.setPrimary(false);
                        fieldModel.getAnnotationSet().remove(String.format("@TableId(type = IdType.%s)", keyStrategy));
                        fieldModel.getAnnotationSet().remove("@TableId");
                        fieldModel.fillAnnotations();
                    });
                }else {
                    Log.warn("表【%s】存在多个主键，请注意！", tableName);
                }
            }
            map.put("entity", model);
        }

        private List<FieldModel> getFieldsByColumns(List<ColumnInfo> columnInfos) {
            List<FieldModel> objects = new ArrayList<>();
            for (ColumnInfo columnInfo : columnInfos) {
                FieldModel fieldModel = getFieldModelByColumn(columnInfo);
                if (fieldModel.isEnable()){
                    objects.add(fieldModel);
                }
            }
            return objects;
        }

        private FieldModel getFieldModelByColumn(ColumnInfo columnInfo) {
            FieldModel fieldModel = new FieldModel();
            // enable
            String enable = TableColumnCacheManager.getString(tableName, columnInfo.getColumnName(), "enable");
            fieldModel.setEnable(StringUtils.isEmpty(enable) || Objects.equals(enable, "Y"));

            // 主键
            fieldModel.setPrimary(ServiceManager.getDatabaseService().isPrimaryKey(columnInfo));

            // fieldName
            String fieldName = HumpUtils.lineToHump(columnInfo.getColumnName());
            String cacheFieldName = TableColumnCacheManager.getString(tableName, columnInfo.getColumnName(), "fieldName");
            boolean hasNewFieldName = !StringUtils.isEmpty(cacheFieldName) && !Objects.equals(fieldName, cacheFieldName);
            if (hasNewFieldName){
                fieldName = cacheFieldName;
                // 如果字段是从缓存中来的，且与驼峰名字不一致，需要加TableName注解
                if (!fieldModel.isPrimary()){
                    fieldModel.setHasTableField(true);
                    fieldModel.getAnnotationSet().add(String.format("@TableField(\"%s\")", columnInfo.getColumnName()));
                }
            }
            fieldModel.setName(fieldName);

            // fieldComment
            String fieldComment = TableColumnCacheManager.getString(tableName, columnInfo.getColumnName(), "fieldComment");
            if (StringUtils.isEmpty(fieldComment)){
                fieldComment = columnInfo.getColumnComment();
                if (StringUtils.isEmpty(fieldComment)){
                    fieldComment = fieldModel.getName();
                }
            }
            fieldModel.setComment(fieldComment);

            // javaDataType
            String javaDataType = TableColumnCacheManager.getString(tableName, columnInfo.getColumnName(), "javaDataType");
            if (StringUtils.isEmpty(javaDataType)){
                javaDataType = ServiceManager.getDatabaseService().toJavaDataType(columnInfo);
            }
            try {
                Class<?> forName = Class.forName(javaDataType);
                fieldModel.setTypeClass(forName);
                fieldModel.setType(forName.getSimpleName());
            } catch (ClassNotFoundException e) {
                throw new ParamException(String.format("javaDataType class [%s] not found！", javaDataType), e);
            }

            // 方法名称
            String capitalizeFieldName = StringUtils.capitalize(fieldName);
            fieldModel.setSetMethodName(String.format("set%s", capitalizeFieldName));
            fieldModel.setGetMethodName(String.format("%s%s",
                    ClassUtils.isBoolClass(fieldModel.getTypeClass()) ? "is" : "get", capitalizeFieldName));

            // 注解
            if (fieldModel.isPrimary()){
                String keyStrategy = globalConfig.getEntity().getKeyStrategy();
                if (!Objects.equals(globalConfig.getEntity().getKeyStrategy(), KeyStrategyEnum.NONE.name())){
                    fieldModel.getAnnotationSet().add(String.format(hasNewFieldName ? "@TableId(value = \""+columnInfo.getColumnName()+"\",type = IdType.%s)"
                            :"@TableId(type = IdType.%s)", keyStrategy));
                }else{
                    fieldModel.getAnnotationSet().add(hasNewFieldName ? "@TableId(\""+columnInfo.getColumnName()+"\")":"@TableId");
                }
            }
            if (globalConfig.isFillEnabled()){
                // 判断字段是否需要fill
                if (fillUpdateFieldNames.contains(fieldName) && ClassUtils.isDateClass(fieldModel.getTypeClass())){
                    DataSourceConfig dataSourceConfig = DataSourceManager.getActive();
                    if (DatabaseDriverEnum.MySQL.name().equals(dataSourceConfig.getDriver())){
                        fieldModel.setFillUpdate(true);
                        fieldModel.getAnnotationSet().add("@TableField(update = \"now()\")");
                    }
                }
            }
            fieldModel.fillAnnotations();
            return fieldModel;
        }

        private void initDataModel(Map<String, Object> map) {
            DataSourceConfig datasource = DataSourceManager.getActive();
            map.put("date", DateUtils.dateString());
            map.put("time", DateUtils.timeString());
            map.put("datetime", DateUtils.datetimeString());
            map.put("tableName", tableName);
            map.put("basePackageName", basePackageName);
            map.put("tableRemark", tableRemark);
            map.put("databaseName", databaseName);
            map.put("global", globalConfig);
            map.put("datasource", datasource);
            map.put("author", globalConfig.getAuthor());
            map.put("description", globalConfig.getDescription());
            map.put("baseColumn", getBaseColumnString(columnInfos));
        }

        private String getEntityNameByTableName() {
            if (Objects.isNull(entityName)){
                entityName = StringUtils.capitalize(globalConfig.isTablePrefixEnabled() ?
                        HumpUtils.lineToHump(tableName, globalConfig.getTablePrefix()) :
                        HumpUtils.lineToHump(tableName));
            }
            return entityName;
        }

        private Object getBaseColumnString(List<ColumnInfo> columnInfos) {
            if (!globalConfig.isBaseColumnEnabled()
                    || Objects.isNull(columnInfos) || columnInfos.isEmpty()){
                return "";
            }
            StringBuilder sb = new StringBuilder();
            for (ColumnInfo columnInfo : columnInfos) {

                String fieldName = HumpUtils.lineToHump(columnInfo.getColumnName());
                String cacheFieldName = TableColumnCacheManager.getString(tableName, columnInfo.getColumnName(), "fieldName");
                if (!StringUtils.isEmpty(cacheFieldName) && !Objects.equals(fieldName, cacheFieldName)){
                    sb.append(columnInfo.getColumnName()).append(" as ").append(cacheFieldName).append(",");
                }else {
                    sb.append(columnInfo.getColumnName()).append(",");
                }
            }
            sb.deleteCharAt(sb.length() -1);
            return sb.toString();
        }
    }
}
