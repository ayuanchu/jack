package com.kancy.mybatisplus.generator.core.excel;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * TableSheet
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/17 12:53
 **/
@Data
public class TableSheet {

    private String tableName;

    private String tableRemark;

    private String createDate;

    private List<TableColumn> tableColumns;

    @Data
    public static class TableColumn{

        private boolean isKey;

        private String columnName;

        private String columnComment;

        private String dataType;

        private String length;

        private String defaultValue;

        private String nullable;

    }
}
