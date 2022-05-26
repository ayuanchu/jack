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
public class GlobalServiceConfig extends BaseClassConfig {
    private static final long serialVersionUID = 1L;

    public GlobalServiceConfig() {
        setEnabled(false);
        setTemplatePath("/service.ftl");
        setClassNameFormat("%sService");
        setFileNameFormat(String.format("%s.java",getClassNameFormat()));
        setPackageName("service");
        setSupperClassName("java.lang.Object");
    }
}
