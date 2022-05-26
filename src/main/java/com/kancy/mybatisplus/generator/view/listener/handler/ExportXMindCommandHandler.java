package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.core.xmind.XMind;
import com.kancy.mybatisplus.generator.core.xmind.XMindNode;
import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.manager.DatabaseManager;
import com.kancy.mybatisplus.generator.manager.ServiceManager;
import com.kancy.mybatisplus.generator.manager.TableColumnCacheManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.service.TableInfo;
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

public abstract class ExportXMindCommandHandler extends ActionEventCommandHandler<Main> {

    public ExportXMindCommandHandler(Main component) {
        super(component);
    }

    protected void exportXMindCheck(){
        DataSourceConfig activeDataSource = DatabaseManager.getActiveDataSource();
        if (Objects.isNull(activeDataSource)){
            throw new AlertException("请先连接数据源！");
        }

        String databaseName = DatabaseManager.getActive();
        if (Objects.isNull(databaseName)){
            throw new AlertException("请先选择数据库！");
        }

    }

    protected XMind getXMind(DataSourceConfig dataSourceConfig, String databaseName){
        List<TableInfo> tables = ServiceManager.getDatabaseService().getTables(dataSourceConfig, databaseName);
        return getXMind(databaseName, tables);
    }


    protected XMind getXMind(String databaseName, List<TableInfo> tables){
        XMind xMind = new XMind(String.format("数据库（%s）表设计", databaseName));
        Set<String> tableNames = new HashSet<>();
        for (TableInfo tableInfo : tables) {
            tableNames.add(tableInfo.getTableName());
        }

        Map<String, List<ColumnInfo>> tableColumnsMap = ServiceManager.getDatabaseService().getColumns(DataSourceManager.getActive(), databaseName, tableNames);
        List<XMindNode> topics = new ArrayList<>();
        for (TableInfo tableInfo : tables) {
            XMindNode tableNode = new XMindNode();
            tableNode.setName(tableInfo.getTableName());
            tableNode.setDescription(tableInfo.getTableComment());
            String tableRemark = TableColumnCacheManager.getTableRemark(tableInfo.getTableName(), tableInfo.getTableComment());
            if (!StringUtils.isEmpty(tableRemark)){
                tableNode.setName(String.format("%s\r\n（%s）", tableInfo.getTableName(), tableRemark));
            }
            List<ColumnInfo> columnInfos = tableColumnsMap.get(tableInfo.getTableName());
            List<XMindNode> childNodes = new ArrayList<>();
            for (ColumnInfo columnInfo : columnInfos) {
                XMindNode columnNode = new XMindNode();
                columnNode.setName(columnInfo.getColumnName());
                columnNode.setDescription(columnInfo.getColumnComment());

                boolean primaryKey = ServiceManager.getDatabaseService().isPrimaryKey(columnInfo);
                if (primaryKey){
                    columnNode.addLabel("主键");
                }

                // 优先从缓存中获取字段注释
                String nodeName = null;
                String fieldComment = TableColumnCacheManager.getString(tableInfo.getTableName(), columnInfo.getColumnName(), "fieldComment");
                if (StringUtils.isEmpty(fieldComment)){
                    nodeName = columnInfo.getColumnComment();
                }else {
                    nodeName = fieldComment;
                }

                if (!StringUtils.isEmpty(nodeName)
                        && !StringUtils.equalsIgnoreCase(nodeName, columnInfo.getColumnName())){
                    List<XMindNode> columnChildNodes = new ArrayList<>();
                    XMindNode xMindNode = new XMindNode(nodeName);
                    xMindNode.setFolded(true);
                    columnChildNodes.add(xMindNode);
                    columnNode.setChildNodes(columnChildNodes);
                }
                childNodes.add(columnNode);
            }
            tableNode.setChildNodes(childNodes);
            topics.add(tableNode);
        }
        xMind.setTopics(topics);
        return xMind;
    }

}
