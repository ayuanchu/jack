package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.service.TableInfo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * DatabaseManager
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 19:22
 **/

public class DatabaseManager {

    private static final ThreadLocal<String> activeDatabase = new ThreadLocal<>();

    public static void setActive(String databaseName){
        activeDatabase.set(databaseName);
        if (Log.isDebug()){
            Log.debug("Active datasource : [%s] , database : %s", DataSourceManager.getActive().getName(), databaseName);
        }
    }

    public static void cleanActive(){
        Log.debug("Active database [%s] is down", getActive());
        activeDatabase.set(null);
    }

    public static String getActive(){
        return activeDatabase.get();
    }

    public static DataSourceConfig getActiveDataSource(){
        return DataSourceManager.getActive();
    }

    /**
     * 获取激活数据库表列表
     * @return
     */
    public static List<String> getActiveDataSourceDatabases(){
        DataSourceConfig dataSourceConfig = getActiveDataSource();
        if (Objects.isNull(dataSourceConfig)){
            return Collections.emptyList();
        }
        return ServiceManager.getDatabaseService().getDatabaseNames(DataSourceManager.getActive());
    }

    /**
     * 获取激活数据库表列表
     * @return
     */
    public static List<TableInfo> getActiveDatabaseTables(){
        DataSourceConfig dataSourceConfig = getActiveDataSource();
        if (Objects.isNull(dataSourceConfig) || Objects.isNull(getActive())){
            return Collections.emptyList();
        }
        return ServiceManager.getDatabaseService().getTables(DataSourceManager.getActive(), getActive());
    }

    /**
     * 获取激活数据库表字段列表
     * @return
     */
    public static List<ColumnInfo> getActiveDatabaseTableColumns(String tableName){
        DataSourceConfig dataSourceConfig = getActiveDataSource();
        if (Objects.isNull(dataSourceConfig) || Objects.isNull(getActive()) || Objects.isNull(tableName)){
            return Collections.emptyList();
        }
        return ServiceManager.getDatabaseService().getColumns(DataSourceManager.getActive(), getActive(), tableName);
    }

    public static boolean testConnect(DataSourceConfig dataSourceConfig){
        return ServiceManager.getDatabaseService().testConnect(dataSourceConfig);
    }

}
