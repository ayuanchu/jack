package com.kancy.mybatisplus.generator.core.excel;

import com.kancy.mybatisplus.generator.utils.StringUtils;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Number;
import jxl.write.*;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * <p>
 * Excel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/17 12:51
 **/
@Data
public class Excel {

    private String docName;

    private String systemName;

    private List<TableSheet> tableSheets;

    public void save(File file){
        try {
            Workbook wb = Workbook.getWorkbook(Excel.class.getClassLoader().getResourceAsStream("xls/database_doc_template.xls"));
            WritableWorkbook wwb = Workbook.createWorkbook(file, wb);
            WritableCellFormat cellFormat = getCellFormat01();
            WritableCellFormat cellFormat01 = getCellFormat01();
            cellFormat01.setAlignment(Alignment.CENTRE);

            // 填写履历
            WritableSheet sheet0 = wwb.getSheet(0);
            addCell(sheet0, 1, 1,getDocName(), cellFormat);
            addCell(sheet0, 1, 5, getSystemName(),cellFormat);
            addCell(sheet0, 2, 5, "数据表项目定义", cellFormat);

            // 填充表数据
            int tableSheetSize = tableSheets.size();
            for (int i = 1; i < tableSheetSize; i++) {
                wwb.importSheet(tableSheets.get(i).getTableName(), 2 + i, wb.getSheet(2));
            }

            if (tableSheetSize > 0){
                wwb.getSheet(2).setName(tableSheets.get(0).getTableName());
            }

            int sheetIndex = 2;
            WritableSheet indexSheet = wwb.getSheet(1);
            for (int i = 0; i < tableSheetSize; i++) {
                WritableSheet writableSheet = wwb.getSheet(sheetIndex++);
                TableSheet tableSheet = tableSheets.get(i);
                addCell(indexSheet, i+1, 0, i+1, cellFormat01);
                addCell(indexSheet, i+1, 1, tableSheet.getTableName(), cellFormat);
                addCell(indexSheet, i+1, 2, tableSheet.getTableRemark(), cellFormat);
                addCell(indexSheet, i+1, 3, tableSheet.getCreateDate(), cellFormat01);
                addCell(indexSheet, i+1, 4, "", cellFormat);
                fillSheet(tableSheet, writableSheet);
                // 建立超链接
                indexSheet.addHyperlink(new WritableHyperlink(1,i+1,tableSheet.getTableName(),writableSheet,0,0));
                writableSheet.addHyperlink(new WritableHyperlink(0,0, "回到目录", indexSheet,0,0));
            }

            wwb.write();
            wwb.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillSheet(TableSheet tableSheet, WritableSheet ws) throws WriteException {
        setTableHeader(tableSheet, ws);

        WritableCellFormat cellFormat = getDefaultCellFormat();
        WritableCellFormat cellFormat01 = getCellFormat01();
        WritableCellFormat cellFormat02 = getCellFormat01();
        cellFormat02.setWrap(false);

        List<TableSheet.TableColumn> tableColumns = tableSheet.getTableColumns();
        for (int i = 0; i < tableColumns.size(); i++) {
            int row = i + 6;
            TableSheet.TableColumn column = tableColumns.get(i);
            addCell(ws, row, 0, i+1, cellFormat);
            addCell(ws, row, 1, column.isKey() ? "●": "", cellFormat);
            addCell(ws, row, 2, column.getColumnName(), cellFormat01);
            addCell(ws, row, 3, getColumnComment(column), cellFormat02);
            addCell(ws, row, 4, column.getDataType(),cellFormat);
            addCell(ws, row, 5, getbracketsValue(column.getLength(), "0"), cellFormat);
            addCell(ws, row, 6, getDefaultValue(column), cellFormat);
            addCell(ws, row, 7, column.getNullable(), cellFormat);
            addCell(ws, row, 8, "", cellFormat);
            addCell(ws, row, 9, getColumnRemark(column), cellFormat01);
        }

    }

    private String getDefaultValue(TableSheet.TableColumn column) {
        if (StringUtils.isEmpty(column.getDefaultValue()) ||
            StringUtils.equalsIgnoreCase(column.getDefaultValue(), "null")){
            return "";
        }
        return column.getDefaultValue();
    }

    private void setTableHeader(TableSheet tableSheet, WritableSheet ws) throws WriteException {
        WritableCellFormat cellFormat02 = getCellFormat01();
        WritableCellFormat cellFormat03 = getCellFormat01();
        WritableCellFormat cellFormat04 = getCellFormat01();
        addCell(ws, 1, 2,getDocName(), cellFormat02);
        addCell(ws, 1, 5, getSystemName(),cellFormat03);
        addCell(ws, 2, 5, "数据表项目定义", cellFormat03);
        addCell(ws, 1, 8, tableSheet.getTableName(),cellFormat03);
        addCell(ws, 2, 8, tableSheet.getTableRemark(),cellFormat03);
        addCell(ws, 4, 0, String.format("■%s结构", tableSheet.getTableRemark()),cellFormat04);
    }

    private Object getColumnRemark(TableSheet.TableColumn column) {
        return getbracketsValue(column.getColumnComment(),"");
    }

    private Object getColumnComment(TableSheet.TableColumn column) {
        String columnComment = column.getColumnComment();
        if (StringUtils.isEmpty(columnComment)){
            return "";
        }

        int a = columnComment.indexOf("（");
        if (a < 0){
            a = columnComment.indexOf("(");
        }
        int b = columnComment.lastIndexOf("）");
        if (b < 0){
            b = columnComment.indexOf(")");
        }

        if (a > -1 && b > -1){
            return columnComment.substring(0, a);
        }
        return columnComment;
    }

    private String getbracketsValue(String value, String def) {
        if (StringUtils.isEmpty(value)){
            return def;
        }

        int a = value.indexOf("（");
        if (a < 0){
            a = value.indexOf("(");
        }
        int b = value.lastIndexOf("）");
        if (b < 0){
            b = value.indexOf(")");
        }

        if (a > -1 && b > -1){
            return value.substring(a+1, b);
        }
        return def;
    }

    private void addCell(WritableSheet sheet, int rowId, int colId, Object value, WritableCellFormat format) {
        try {
            String str = String.valueOf(value);
            try {
                double aDouble = Double.parseDouble(str);
                jxl.write.Number number = new Number(colId, rowId, aDouble , format);
                sheet.addCell(number);
            } catch (NumberFormatException e) {
                Label label = new Label(colId, rowId, String.valueOf(value), format);
                sheet.addCell(label);
            }
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    private WritableCellFormat getCellFormat01() throws WriteException {
        WritableCellFormat cellFormat = getCellFormat();
        //设置边框;
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        cellFormat.setAlignment(Alignment.LEFT);
        return cellFormat;
    }
    private WritableCellFormat getDefaultCellFormat() throws WriteException {
        WritableCellFormat cellFormat = getCellFormat();
        //设置边框;
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        return cellFormat;
    }


    private WritableCellFormat getCellFormat() throws WriteException {
        //设置字体;
        WritableFont font1 = new WritableFont(WritableFont.ARIAL,9,WritableFont.NO_BOLD,false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
        WritableCellFormat cellFormat1 = new WritableCellFormat(font1);
        //设置背景颜色;
        cellFormat1.setBackground(Colour.WHITE);
        //设置自动换行;
        cellFormat1.setWrap(true);
        //设置文字居中对齐方式;
        cellFormat1.setAlignment(Alignment.CENTRE);
        //设置垂直居中;
        cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);
        return cellFormat1;
    }

}
