package com.kancy.mybatisplus.generator.config;

import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * GlobalEntityConfig
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 16:32
 **/
@ToString(callSuper = true)
@Data
public class GlobalMapperXmlConfig extends BaseConfig {
    private static final long serialVersionUID = 1L;

    private String packageName;

    public GlobalMapperXmlConfig() {
        setEnabled(true);
        setTemplatePath("/mapper.xml.ftl");
        setFileNameFormat("%sMapper.xml");
        setPackageName("mapper");
    }
}
