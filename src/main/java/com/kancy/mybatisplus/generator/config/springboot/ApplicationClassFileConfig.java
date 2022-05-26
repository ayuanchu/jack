package com.kancy.mybatisplus.generator.config.springboot;

import com.kancy.mybatisplus.generator.config.BaseClassConfig;

/**
 * ApplicationResourceFileConfig
 *
 * @author kancy
 * @date 2020/6/16 21:58
 */
public class ApplicationClassFileConfig extends BaseClassConfig {
    public ApplicationClassFileConfig() {
        setEnabled(true);
        setPackageName("");
        setFileNameFormat("Application.java");
        setTemplatePath("/springboot/Application.java.ftl");
    }
}
