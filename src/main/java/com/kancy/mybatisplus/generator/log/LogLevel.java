package com.kancy.mybatisplus.generator.log;

/**
 * LogLevel
 *
 * @author kancy
 * @date 2020/2/16 2:19
 */
public enum LogLevel {
    DEBUG,INFO,WARN,ERROR;

    /**
     * get level value
     * @return
     */
    public int getLevel(){
        return this.ordinal();
    }
}
