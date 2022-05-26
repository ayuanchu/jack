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
public class GlobalServiceImplConfig extends GlobalServiceConfig {
    private static final long serialVersionUID = 1L;

    public GlobalServiceImplConfig() {
        setEnabled(false);
        setTemplatePath("/service.impl.ftl");
        setClassNameFormat("%sServiceImpl");
        setFileNameFormat(String.format("%s.java",getClassNameFormat()));
        setPackageName("service.impl");
    }
}
