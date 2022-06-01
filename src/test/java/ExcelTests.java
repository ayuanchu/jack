import com.kancy.mybatisplus.generator.core.excel.Excel;
import com.kancy.mybatisplus.generator.core.excel.TableSheet;
import com.kancy.mybatisplus.generator.utils.DateUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * <p>
 * ExcelTests
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/17 10:10
 **/

public class ExcelTests {
    public static void main(String[] args) {

        Excel excel = new Excel();
        excel.setDocName("DB设计书");
        excel.setSystemName("业务系统");

        ArrayList<TableSheet> objects = new ArrayList<>();
        for (int i = 0; i <4 ; i++) {
            TableSheet tableSheet = new TableSheet();
            tableSheet.setTableName("t_user_"+i);
            tableSheet.setTableRemark("用户表");
            tableSheet.setCreateDate(DateUtils.dateString());
            ArrayList<TableSheet.TableColumn > columns = new ArrayList<>();

            TableSheet.TableColumn tableColumn = new TableSheet.TableColumn();
            tableColumn.setColumnName("id");
            tableColumn.setColumnComment("主键");
            tableColumn.setKey(true);
            tableColumn.setDataType("int");
            tableColumn.setLength("20");
            tableColumn.setNullable("N");
            columns.add(tableColumn);

            for (int j = 0; j < 4; j++) {
                TableSheet.TableColumn tableColumn2 = new TableSheet.TableColumn();
                tableColumn2.setColumnName("demo_column_"+j);
                tableColumn2.setColumnComment("测试字段（1.大萨达，2.第三发送）");
                tableColumn2.setDataType("VARCHAR");
                tableColumn2.setLength("9");
                tableColumn2.setNullable("N");
                columns.add(tableColumn2);
            }

            tableSheet.setTableColumns(columns);
            objects.add(tableSheet);
        }
        excel.setTableSheets(objects);
        long s = System.currentTimeMillis();
        excel.save(new File("1.xls"));
        System.out.println(System.currentTimeMillis() - s);

    }

}
