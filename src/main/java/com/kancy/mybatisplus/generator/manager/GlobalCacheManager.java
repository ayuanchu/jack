package com.kancy.mybatisplus.generator.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * GlobalCacheManager
 *
 * @author kancy
 * @date 2020/6/13 19:23
 */
public class GlobalCacheManager {

    private static final String CACHE_KEY_PACKAGE_COMBOBOX_MODEL_ITEMS = "MAIN_PACKAGE_COMBOBOX_MODEL_ITEMS";
    private static final String CACHE_KEY_MODULE_COMBOBOX_MODEL_ITEMS = "MAIN_MODULE_COMBOBOX_MODEL_ITEMS";

    private static final String CACHE_KEY_MAVEN_GROUP_ID_COMBOBOX_MODEL_ITEMS = "MAVEN_GROUP_ID_COMBOBOX_MODEL_ITEMS";
    private static final String CACHE_KEY_MAVEN_ARTIFACT_ID_COMBOBOX_MODEL_ITEMS = "MAVEN_ARTIFACT_ID_COMBOBOX_MODEL_ITEMS";
    private static final String CACHE_KEY_MAVEN_VERSION_COMBOBOX_MODEL_ITEMS = "MAVEN_VERSION_COMBOBOX_MODEL_ITEMS";

    public static void init(){

    }

    public static List getPackageComboBoxModelItems(){
        return (ArrayList) DataStoreManager.getGlobalCache().get(CACHE_KEY_PACKAGE_COMBOBOX_MODEL_ITEMS);
    }
    public static void updatePackageComboBoxModelItems(List list){
        DataStoreManager.getGlobalCache().put(CACHE_KEY_PACKAGE_COMBOBOX_MODEL_ITEMS, list);
    }

    public static List getModuleComboBoxModelItems(){
        return (ArrayList) DataStoreManager.getGlobalCache().get(CACHE_KEY_MODULE_COMBOBOX_MODEL_ITEMS);
    }
    public static void updateModuleComboBoxModelItems(List list){
        DataStoreManager.getGlobalCache().put(CACHE_KEY_MODULE_COMBOBOX_MODEL_ITEMS, list);
    }


    public static List getMavenGroupIdComboboxModelItems(){
        return (ArrayList) DataStoreManager.getGlobalCache().get(CACHE_KEY_MAVEN_GROUP_ID_COMBOBOX_MODEL_ITEMS);
    }
    public static void updateMavenGroupIdComboboxModelItems(List list){
        DataStoreManager.getGlobalCache().put(CACHE_KEY_MAVEN_GROUP_ID_COMBOBOX_MODEL_ITEMS, list);
    }

    public static List getMavenArtifactIdComboboxModelItems(){
        return (ArrayList) DataStoreManager.getGlobalCache().get(CACHE_KEY_MAVEN_ARTIFACT_ID_COMBOBOX_MODEL_ITEMS);
    }
    public static void updateMavenArtifactIdComboboxModelItems(List list){
        DataStoreManager.getGlobalCache().put(CACHE_KEY_MAVEN_ARTIFACT_ID_COMBOBOX_MODEL_ITEMS, list);
    }

    public static List getMavenVersionComboboxModelItems(){
        return (ArrayList) DataStoreManager.getGlobalCache().get(CACHE_KEY_MAVEN_VERSION_COMBOBOX_MODEL_ITEMS);
    }
    public static void updateMavenVersionComboboxModelItems(List list){
        DataStoreManager.getGlobalCache().put(CACHE_KEY_MAVEN_VERSION_COMBOBOX_MODEL_ITEMS, list);
    }

    private static <T> T getSettingFromGlobalCache(String key, T def) {
        String cacheKey = String.format("settings.%s", key);
        Object o = DataStoreManager.getGlobalCache().get(cacheKey);
        if (Objects.isNull(o)){
            return def;
        }else {
            return (T) o;
        }
    }

    public static void clear(){
        DataStoreManager.getGlobalCache().remove(CACHE_KEY_PACKAGE_COMBOBOX_MODEL_ITEMS);
        DataStoreManager.getGlobalCache().remove(CACHE_KEY_MODULE_COMBOBOX_MODEL_ITEMS);

        DataStoreManager.getGlobalCache().remove(CACHE_KEY_MAVEN_GROUP_ID_COMBOBOX_MODEL_ITEMS);
        DataStoreManager.getGlobalCache().remove(CACHE_KEY_MAVEN_ARTIFACT_ID_COMBOBOX_MODEL_ITEMS);
        DataStoreManager.getGlobalCache().remove(CACHE_KEY_MAVEN_VERSION_COMBOBOX_MODEL_ITEMS);
    }
}
