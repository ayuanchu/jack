package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.utils.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * TableColumnCacheManager
 *
 * @author kancy
 * @date 2020/6/10 23:36
 */
public class TableColumnCacheManager {

    public static void putTableRemark(String tableName, String tableRemark){
        if (!StringUtils.isEmpty(tableRemark)){
            String tableRemarkCacheKey = getColumnPropertyKey(tableName, "*", "tableRemark");
            put(tableRemarkCacheKey, tableRemark);
        }
    }

    public static boolean hasTableRemark(String tableName){
        String tableRemarkCacheKey = getColumnPropertyKey(tableName, "*", "tableRemark");
        return DataStoreManager.getTableColumnCache().containsKey(tableRemarkCacheKey);
    }

    public static void removeTableRemark(String tableName){
        String tableRemarkCacheKey = getColumnPropertyKey(tableName, "*", "tableRemark");
        DataStoreManager.getTableColumnCache().remove(tableRemarkCacheKey);
        Log.debug("TableColumnCacheManager remove tableRemark key : %s", tableRemarkCacheKey);
    }

    public static String getTableRemark(String tableName, String tableRemark){
        String tableRemarkCacheKey = getColumnPropertyKey(tableName, "*", "tableRemark");
        Object o = get(tableRemarkCacheKey);
        return Objects.isNull(o) ? tableRemark : String.valueOf(o);
    }

    public static String getColumnPropertyKey(String tableName, String columnName, String propertyKey){
        DataSourceConfig activeDataSourceConfig = DataSourceManager.getActive();
        String databaseName = DatabaseManager.getActive();
        if (Objects.isNull(activeDataSourceConfig) || Objects.isNull(databaseName)) {
            return null;
        }
        String dataSourceId = activeDataSourceConfig.getId();
        return String.format("%s:%s:%s:%s@%s", dataSourceId, databaseName, tableName, columnName, propertyKey);
    }

    public static Object get(String tableName, String columnName, String propertyKey){
        String columnPropertyKey = getColumnPropertyKey(tableName, columnName, propertyKey);
        return DataStoreManager.getTableColumnCache().get(columnPropertyKey);
    }

    public static String getString(String tableName, String columnName, String propertyKey){
        Object o = get(tableName, columnName, propertyKey);
        return Objects.isNull(o) ? null : String.valueOf(o);
    }

    public static Object get(String key){
        return DataStoreManager.getTableColumnCache().get(key);
    }

    public static void put(String key, Object value){
        Log.debug("TableColumnCacheManager put key : %s , value : %s", key, value);
        DataStoreManager.getTableColumnCache().put(key, value);
    }

    public static void remove(String key){
        Log.debug("TableColumnCacheManager remove key : %s", key);
        DataStoreManager.getTableColumnCache().remove(key);
    }

    /**
     * 清除数据源
     * @param dataSourceConfig
     */
    public static void clear(DataSourceConfig dataSourceConfig){
        String dataSourceId = dataSourceConfig.getId();
        Map<String, Object> cache = DataStoreManager.getTableColumnCache();
        Set<String> set = cache.keySet();
        for (String key : set) {
            if (key.startsWith(dataSourceId)){
                cache.remove(key);
                Log.debug("TableColumnCacheManager clear [%s] datasource table column key : %s", dataSourceId, key);
            }
        }
    }

    public static void clear(){
        DataStoreManager.getTableColumnCache().clear();
    }
}
