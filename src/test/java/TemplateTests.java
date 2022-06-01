import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.config.GlobalConfig;
import com.kancy.mybatisplus.generator.core.model.EntityModel;
import com.kancy.mybatisplus.generator.manager.ServiceManager;

import java.util.HashMap;
import java.util.Optional;

/**
 * TemplateTests
 *
 * @author kancy
 * @date 2020/6/16 21:26
 */
public class TemplateTests {

    public static void main(String[] args) {
        HashMap dataModel = new HashMap();
        dataModel.put("driverClassName","");
        dataModel.put("jdbcUrl","");

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataModel.put("datasource", dataSourceConfig);

        GlobalConfig globalConfig = new GlobalConfig();
        dataModel.put("global",globalConfig);

        EntityModel entityModel = new EntityModel();
        entityModel.setPackageName("");
        dataModel.put("entity",entityModel);

        Optional render = ServiceManager.getTemplateService().render("application-db.yml.ftl", dataModel);
        System.out.println(render.get());

    }
}
