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
public class GlobalControllerConfig extends BaseClassConfig {
    private static final long serialVersionUID = 1L;

    public GlobalControllerConfig() {
        setEnabled(false);
        setTemplatePath("/controller.ftl");
        setClassNameFormat("%sController");
        setFileNameFormat(String.format("%s.java",getClassNameFormat()));
        setPackageName("controller");
        setSupperClassName("java.lang.Object");
    }
}
