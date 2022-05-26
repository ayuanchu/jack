package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.ExceptionUtils;
import com.kancy.mybatisplus.generator.utils.FileUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * DataStoreManager
 *
 * @author kancy
 * @date 2020/6/10 22:33
 */
public class DataStoreManager {

    private static String dbFilePath;
    private static String dbpFilePath;

    private static DB db;

    private static HTreeMap<String, Object> globalCache;

    private static HTreeMap<String, Object> tableColumnCache;

    private static AtomicBoolean Initialized = new AtomicBoolean(false);

    public static void init() {
        try {
            if (!Initialized.getAndSet(true)){
                doInit();
            }
        } catch (Throwable e) {
            Initialized.set(false);
            Log.error("Startup occur throwable : %s", e.getMessage());
            // 自动恢复
            if (AlertUtils.yesOrNo("发生系统无法处理的异常，是否重置系统进行恢复？")){
                ExceptionUtils.exceptionListener(()->{
                    resetOnExit();
                    AlertUtils.msg("恢复成功，请重新启动！");
                });
            }
            throw new RuntimeException("系统异常，需要重新启动！");
        }
    }

    private static void doInit() {
        String fileName = System.getProperty("db.fileName", Settings.getDefaultDbFileName());
        dbFilePath = getMapDbFilePath(fileName + ".db");
        dbpFilePath = getMapDbFilePath(fileName +".db.p");
        Log.debug("MapDB FilePath : %s", dbFilePath);

        db = DBMaker.newFileDB(new File(dbFilePath))
                .transactionDisable()
                .closeOnJvmShutdown()
                .make();
        globalCache = db.createHashMap("globalCache").makeOrGet();
        tableColumnCache = db.createHashMap("tableColumnCache").makeOrGet();
        Log.info("MapDB 初始化成功！");
    }

    public static DB getDb() {
        return db;
    }

    public static HTreeMap<String, Object> getGlobalCache() {
        return globalCache;
    }

    public static HTreeMap<String, Object> getTableColumnCache() {
        return tableColumnCache;
    }

    public static void resetOnExit(){
        try {
            File dbFile = new File(dbFilePath);
            File dbpFile = new File(dbpFilePath);

            if (!FileUtils.deleteQuietly(dbFile)){
                FileUtils.forceDeleteOnExit(dbFile);
            }

            if (!FileUtils.deleteQuietly(dbpFile)){
                FileUtils.forceDeleteOnExit(dbpFile);
            }

        } catch (Exception ex) {
            throw new AlertException("处理失败", ex);
        }
    }

    private static String getMapDbFilePath(String fileName) {
        String userHome = System.getProperty("user.home");
        return FileUtils.getFile(userHome, fileName).getAbsolutePath();
    }
}
