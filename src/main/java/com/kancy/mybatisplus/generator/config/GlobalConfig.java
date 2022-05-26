package com.kancy.mybatisplus.generator.config;

import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.enums.ProjectEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * GlobalConfig
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 16:29
 **/
@Data
public class GlobalConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String author = "kancy";
    private String description = "由 Mybatisplus Code Generator 创建";

    private boolean lombokEnabled = true;

    private boolean baseColumnEnabled = true;

    private boolean overwriteEnabled = true;

    private boolean fillEnabled = true;

    private boolean tablePrefixEnabled = true;

    private String tablePrefix = "t_;f_";

    private String projectType = ProjectEnum.NONE.name();

    private GlobalEntityConfig entity = new GlobalEntityConfig();
    private GlobalMapperConfig mapper = new GlobalMapperConfig();
    private GlobalMapperXmlConfig mapperXml = new GlobalMapperXmlConfig();
    private GlobalDaoConfig dao = new GlobalDaoConfig();
    private GlobalServiceConfig service = new GlobalServiceConfig();
    private GlobalServiceImplConfig serviceImpl = new GlobalServiceImplConfig();
    private GlobalControllerConfig controller = new GlobalControllerConfig();
    private GlobalMavenConfig maven = new GlobalMavenConfig();
    private GlobalSpringBootConfig springBoot = new GlobalSpringBootConfig();

    private Map<DatabaseDriverEnum,Map<String, String>> dataTypeMappings;

    public Map<DatabaseDriverEnum, Map<String, String>> getDataTypeMappings() {
        if (Objects.isNull(dataTypeMappings)){
            dataTypeMappings = new HashMap<>();
        }
        return dataTypeMappings;
    }
}
