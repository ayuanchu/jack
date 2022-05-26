package com.kancy.mybatisplus.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * DatabaseDriverEnum
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 17:46
 **/
@Getter
@AllArgsConstructor
public enum DatabaseDriverEnum {

    /**
     * com.mysql.cj.jdbc.Driver
     * com.mysql.jdbc.Driver
     */
    MySQL("com.mysql.jdbc.Driver","jdbc:mysql://%s:%s/%s?serverTimezone=GMT%%2B8&useSSL=false&characterEncoding=utf8&connectTimeout=3000"),
    /**
     * oracle.jdbc.driver.OracleDriver
     * oracle.jdbc.OracleDriver
     */
    Oracle("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@%s:%s:%s");

    /**
     * 类名
     */
    private String driverClassName;
    /**
     * 类名
     */
    private String jdbcUrlFormat;
}
