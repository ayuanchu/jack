package com.kancy.mybatisplus.generator.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Log
 *
 * @author kancy
 * @date 2020/2/16 1:58
 */
public class Log {
    private static LogLevel LOG_LEVEL;
    private static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");

    static {
        loadLogLevel();
    }

    /**
     * debug
     * @param msgFormat
     * @param args
     */
    public static void debug(String msgFormat, Object ... args){
        log(msgFormat, args, LogLevel.DEBUG);
    }

    /**
     * info
     * @param msgFormat
     * @param args
     */
    public static void info(String msgFormat, Object ... args){
        log(msgFormat, args, LogLevel.INFO);
    }

    /**
     * warn
     * 不提供外部使用
     * @param msgFormat
     * @param args
     */
    public static void warn(String msgFormat, Object ... args){
        log(msgFormat, args, LogLevel.WARN);
    }


    /**
     * error
     * @param msgFormat
     * @param args
     */
    public static void error(String msgFormat, Object ... args){
        log(msgFormat, args, LogLevel.ERROR);
    }

    /**
     * error
     * @param msg
     * @param e
     */
    public static void error(String msg, Throwable e){
        String msgFormat = String.format("%s : {}", msg);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        log(msgFormat, new Object[]{sw.toString()}, LogLevel.ERROR);
    }

    /**
     * 打印日志
     * @param msgFormat
     * @param args
     * @param logLevel
     */
    private static void log(String msgFormat, Object[] args, LogLevel logLevel) {
        if (Objects.nonNull(msgFormat) && canLog(logLevel)) {
            msgFormat = msgFormat.replace("{}", "%s");
            String msg = String.format(msgFormat, args);
            if (Objects.equals(LogLevel.ERROR , logLevel)){
                System.err.println(logFormat(msg, logLevel));
            }else {
                System.out.println(logFormat(msg, logLevel));
            }
        }
    }

    /**
     * 日志格式化
     * @param msg
     * @return
     */
    private static StringBuffer logFormat(String msg, LogLevel logLevel) {
        StringBuffer log = new StringBuffer();
        log.append(LocalDateTime.now().format(DATETIME_FORMATTER));
        log.append(" ").append(String.format("%-5s", logLevel.name())).append(" ");
        log.append("[").append(getThreadName()).append("]");
        log.append(" - ");
        log.append(msg);
        return log;
    }

    /**
     * ThreadName
     * @return
     */
    private static String getThreadName() {
        String tmp = Thread.currentThread().getName();
        if (tmp.length() > 16){
            tmp = tmp.substring(tmp.length() -16);
        }
        return String.format("%-16s", tmp);
    }

    /**
     * 是否可以打印日志
     * @param logLevel
     * @return
     */
    private static boolean canLog(LogLevel logLevel) {
        if (Objects.nonNull(LOG_LEVEL)){
            return logLevel.getLevel() >= LOG_LEVEL.getLevel();
        }
        return false;
    }

    /**
     * 加载日志级别
     */
    private static void loadLogLevel() {
        String propertyName = "log.level";
        String logLevelName = System.getProperty(propertyName, System.getenv(propertyName));
        if (Objects.isNull(logLevelName) || logLevelName.isEmpty()){
            logLevelName = "INFO";
        }
        setLogLevel(LogLevel.valueOf(logLevelName.toUpperCase()));
    }

    /**
     * 设置日志级别
     * @param logLevel
     */
    public static void setLogLevel(LogLevel logLevel) {
        LOG_LEVEL = logLevel;
    }

    public static LogLevel getLogLevel() {
        return LOG_LEVEL;
    }

    public static boolean isDebug() {
        return Objects.equals(LogLevel.DEBUG, getLogLevel());
    }
}
