package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.core.excel.Excel;
import com.kancy.mybatisplus.generator.core.excel.TableSheet;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.manager.TableColumnCacheManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.service.TableInfo;
import com.kancy.mybatisplus.generator.utils.DateUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.view.Main;

import java.util.*;

/**
 * <p>
 * ExportXMindCommandHandler
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/16 15:55
 **/

public abstract class ExportExcelCommandHandler extends ActionEventCommandHandler<Main> {

    public ExportExcelCommandHandler(Main component) {
        super(component);
    }

    protected void exportExcelCheck(){
        DataSourceConfig activeDataSource = DatabaseManager.getActiveDataSource();
        if (Objects.isNull(activeDataSource)){
            throw new AlertException("请先连接数据源！");
        }

        String databaseName = DatabaseManager.getActive();
        if (Objects.isNull(databaseName)){
            throw new AlertException("请先选择数据库！");
        }

    }

    protected Excel getExcel(DataSourceConfig dataSourceConfig, String databaseName){
        List<TableInfo> tables = ServiceManager.getDatabaseService().getTables(dataSourceConfig, databaseName);
        return getExcel(databaseName, tables);
    }


    protected Excel getExcel(String databaseName, List<TableInfo> tables){
        Excel excel = new Excel();
        excel.setDocName(String.format("数据库（%s）设计文档", databaseName));
        excel.setSystemName("业务系统");

        Set<String> tableNames = new HashSet<>();
        for (TableInfo tableInfo : tables) {
            tableNames.add(tableInfo.getTableName());
        }

        Map<String, List<ColumnInfo>> tableColumnsMap = ServiceManager.getDatabaseService().getColumns(DataSourceManager.getActive(), databaseName, tableNames);

        List<TableSheet> tableSheets = new ArrayList<>();
        for (TableInfo tableInfo : tables) {
            TableSheet tableSheet = new TableSheet();
            tableSheet.setCreateDate(StringUtils.isEmpty(tableInfo.getCreateTime())? DateUtils.dateString() : tableInfo.getCreateTime().substring(0,10));
            tableSheet.setTableName(tableInfo.getTableName());
            tableSheet.setTableRemark(TableColumnCacheManager.getTableRemark(tableInfo.getTableName(), tableInfo.getTableComment()));

            List<ColumnInfo> columnInfos = tableColumnsMap.get(tableInfo.getTableName());
            List<TableSheet.TableColumn> tableColumns = new ArrayList<>();
            for (ColumnInfo columnInfo : columnInfos) {
                TableSheet.TableColumn tableColumn = new TableSheet.TableColumn();
                tableColumn.setKey(ServiceManager.getDatabaseService().isPrimaryKey(columnInfo));
                tableColumn.setNullable(columnInfo.getIsNullable());
                tableColumn.setColumnName(columnInfo.getColumnName());
                // 从缓存中获取字段注释
                String fieldComment = TableColumnCacheManager.getString(tableInfo.getTableName(), columnInfo.getColumnName(), "fieldComment");
                if (StringUtils.isEmpty(fieldComment)){
                    tableColumn.setColumnComment(columnInfo.getColumnComment());
                }else {
                    tableColumn.setColumnComment(fieldComment);
                }
                tableColumn.setLength(columnInfo.getColumnType());
                tableColumn.setDefaultValue(columnInfo.getColumnDefault());
                tableColumn.setDataType(columnInfo.getDataType());
                tableColumns.add(tableColumn);
            }
            tableSheet.setTableColumns(tableColumns);
            tableSheets.add(tableSheet);
        }
        excel.setTableSheets(tableSheets);
        return excel;
    }

}
