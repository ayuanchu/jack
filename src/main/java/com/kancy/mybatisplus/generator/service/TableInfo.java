package com.kancy.mybatisplus.generator.service;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * TableInfo
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 20:04
 **/
@Data
@Accessors(chain = true)
public class TableInfo {
    private String databaseName;
    private String tableName;
    private String createTime;
    private String engine;
    private String tableComment;
}
