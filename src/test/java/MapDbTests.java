import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.io.File;

/**
 * MapDbTests
 *
 * @author kancy
 * @date 2020/6/10 22:25
 */
public class MapDbTests {
    public static void main(String[] args) {
        File file = new File("G:\\develop\\idea\\workspace\\mybatisplus-plugin\\data\\test.dat");
        DBMaker dbMaker = DBMaker.newFileDB(file);
        DB db = dbMaker.closeOnJvmShutdown()
                .transactionDisable()
                .asyncWriteEnable()
                .make();
        HTreeMap<Object, Object> map = db.createHashMap("a").makeOrGet();
        System.out.println(map.get("time"));
        map.put("time", System.currentTimeMillis());

        HTreeMap<Object, Object> mapB = db.createHashMap("b").makeOrGet();
        System.out.println(mapB.get("time2"));
        mapB.put("time2", System.currentTimeMillis());
    }
}
