package com.kancy.mybatisplus.generator.config;

import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.log.LogLevel;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Settings
 *
 * @author kancy
 * @date 2020/6/13 19:38
 */
@Data
public class Settings {

    private static String defaultDbFileName = "mybatisplus";

    private static boolean admin;

    private static boolean consoleStartup;

    private static boolean idea = false;

    private static String theme = "seaGlass";

    private static Charset defaultCharset = StandardCharsets.UTF_8;

    private static String defaultWorkPath = ".";

    private static String defaultModuleName = "data";

    private static String defaultPackageName = "com.kancy.data";

    private static int maxHistorySize = 3;
    
    static {
        init();
    }

    private static void init() {
        setAdmin(StringUtils.equalsIgnoreCase(System.getProperty("admin", String.valueOf(admin)), "true"));
        setConsoleStartup(StringUtils.equalsIgnoreCase(System.getProperty("console.startup", String.valueOf(consoleStartup)), "true"));
        setDefaultDbFileName(System.getProperty("db.fileName", defaultDbFileName));
        setTheme(System.getProperty("theme", theme));
        Log.info("Settings 初始化成功！");
    }


    public static String getDefaultModuleName() {
        return defaultModuleName;
    }

    public static void setDefaultModuleName(String defaultModuleName) {
        Settings.defaultModuleName = defaultModuleName;
    }

    public static String getDefaultPackageName() {
        return defaultPackageName;
    }

    public static void setDefaultPackageName(String defaultPackageName) {
        Settings.defaultPackageName = defaultPackageName;
    }

    public static int getMaxHistorySize() {
        return maxHistorySize;
    }

    public static void setMaxHistorySize(int maxHistorySize) {
        Settings.maxHistorySize = maxHistorySize;
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAdmin(boolean admin) {
        Settings.admin = admin;
    }

    public static boolean isConsoleStartup() {
        return consoleStartup;
    }

    private static void setConsoleStartup(boolean consoleStartup) {
        Settings.consoleStartup = consoleStartup;
    }

    public static String getDefaultWorkPath() {
        return defaultWorkPath;
    }

    public static void setDefaultWorkPath(String defaultWorkPath) {
        Settings.defaultWorkPath = defaultWorkPath;
    }

    public static String getTheme() {
        return theme;
    }

    public static void setTheme(String theme) {
        Settings.theme = theme;
    }

    public static String getDefaultCharsetName() {
        return defaultCharset.displayName();
    }
    public static Charset getDefaultCharset() {
        return defaultCharset;
    }

    public static boolean isIdea() {
        return idea;
    }

    public static void setIdea(boolean idea) {
        Settings.idea = idea;
    }

    public static void setLogLevel(LogLevel logLevel) {
        Log.setLogLevel(logLevel);
    }

    public static String getDefaultDbFileName() {
        return defaultDbFileName;
    }

    public static void setDefaultDbFileName(String defaultDbFileName) {
        Settings.defaultDbFileName = defaultDbFileName;
    }
}
