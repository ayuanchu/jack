package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.log.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * DataSourceManager
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 18:32
 **/

public class DataSourceManager {

    private static final String DATA_SOURCE_SET_CACHE_KEY = "DataSourceConfigSet";

    private static final Set<DataSourceConfig> dataSourceConfigSet = new HashSet<>();

    private static final ThreadLocal<DataSourceConfig> activeDataSourceConfig = new ThreadLocal<>();

    public static boolean isEmpty(){
        return dataSourceConfigSet.isEmpty();
    }

    public static DataSourceConfig getActive(){
        return activeDataSourceConfig.get();
    }

    public static void setActive(DataSourceConfig dataSourceConfig){
        // 设置激活标识
        getDataSourceConfigs().forEach(config -> config.setActive(false));
        dataSourceConfig.setActive(true);
        // 缓存起来
        activeDataSourceConfig.set(dataSourceConfig);
        if (Log.isDebug()){
            Log.debug("Active DataSource : %s", dataSourceConfig.showInfo());
        }
    }

    public static void addDataSourceConfig(DataSourceConfig dataSourceConfig){
        dataSourceConfigSet.add(dataSourceConfig);
        if (Log.isDebug()){
            Log.debug("Add DataSource : %s", dataSourceConfig.showInfo());
        }
        store();
    }

    public static void updateDataSourceConfig(DataSourceConfig dataSourceConfig){
        store();
    }

    public static void removeDataSourceConfig(DataSourceConfig dataSourceConfig){
        dataSourceConfigSet.remove(dataSourceConfig);
        if (Log.isDebug()){
            Log.debug("Delete DataSource : %s", dataSourceConfig.showInfo());
        }
        if (dataSourceConfig.isActive()){
            activeDataSourceConfig.remove();
            Log.debug("Active dataSource [%s] is down", dataSourceConfig.getName());
        }
        store();
    }

    public static Collection<DataSourceConfig> getDataSourceConfigs(){
        return dataSourceConfigSet;
    }

    public static void store() {
        DataStoreManager.getGlobalCache().put(DATA_SOURCE_SET_CACHE_KEY, dataSourceConfigSet);
        Log.debug("Store dataSource sets : %s", dataSourceConfigSet.size());
    }

    static {
        initDataSourceConfigSet();
    }

    private static void initDataSourceConfigSet() {
        Object object = DataStoreManager.getGlobalCache().get(DATA_SOURCE_SET_CACHE_KEY);
        if (Objects.nonNull(object)){
            dataSourceConfigSet.addAll((Collection<? extends DataSourceConfig>) object);
        }
    }
}
