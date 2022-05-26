package com.kancy.mybatisplus.generator.service;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * ColumnInfo
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 20:05
 **/
@Data
@Accessors(chain = true)
public class ColumnInfo {
    private String databaseName;
    private String tableName;
    private String columnName;
    private String dataType;
    private String columnType;
    private String columnDefault;
    private String columnComment;
    private Integer numericPrecision;
    private Integer numericScale;

    /**
     * PRI
     */
    private String columnKey;

    /**
     * Y/N
     */
    private String isNullable;
}
