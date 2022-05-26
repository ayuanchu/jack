package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.config.BaseConfig;
import com.kancy.mybatisplus.generator.config.GlobalConfig;
import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.log.Log;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * <p>
 * GlobalConfigManager
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 18:43
 **/

public class GlobalConfigManager {

    private static final String GLOBAL_CONFIG_CACHE_KEY = "GlobalConfig";

    private static GlobalConfig globalConfig = loadGlobalConfig();

    private static GlobalConfig loadGlobalConfig() {
        GlobalConfig globalConfig =
                (GlobalConfig) DataStoreManager.getGlobalCache().get(GLOBAL_CONFIG_CACHE_KEY);
        if (Objects.isNull(globalConfig)){
            globalConfig = initGlobalConfig();
        }
        return globalConfig;
    }

    private static GlobalConfig initGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        try {
            loadTemplateData(globalConfig.getEntity());
            loadTemplateData(globalConfig.getMapper());
            loadTemplateData(globalConfig.getMapperXml());
            loadTemplateData(globalConfig.getDao());
            loadTemplateData(globalConfig.getService());
            loadTemplateData(globalConfig.getServiceImpl());
            loadTemplateData(globalConfig.getController());
            loadTemplateData(globalConfig.getMaven());
        } catch (IOException e) {
            Log.error(e.getMessage(), e);
        }
        return globalConfig;
    }

    private static void loadTemplateData(BaseConfig config) throws IOException {
        config.setTemplate(getTemplateData(String.format("%s%s", config.getTemplateEngineType().toLowerCase(),
                config.getTemplatePath())));
    }

    private static String getTemplateData(String path) throws IOException {
        InputStream resourceAsStream = GlobalConfigManager.class.getClassLoader()
                .getResourceAsStream(path);
        String data = IOUtils.toString(resourceAsStream, Settings.getDefaultCharset());
        IOUtils.closeQuietly(resourceAsStream);
        return data;
    }

    public static GlobalConfig get() {
        return globalConfig;
    }

    public static void store(){
        DataStoreManager.getGlobalCache().put(GLOBAL_CONFIG_CACHE_KEY, globalConfig);
        if (Log.isDebug()){
            Log.debug("Store globalConfig : %s", getGlobalConfigString(globalConfig));
        }
    }

    private static Object getGlobalConfigString(GlobalConfig globalConfig) {
        StringBuilder sb = new StringBuilder();

        try {
            Field[] fields = globalConfig.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                sb.append(field.getName()).append(" -> ").append(field.get(globalConfig)).append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static GlobalConfig reset() {
        globalConfig = initGlobalConfig();
        store();
        return get();
    }
}
