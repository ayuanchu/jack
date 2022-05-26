package com.kancy.mybatisplus.generator.config.springboot;

import com.kancy.mybatisplus.generator.config.BaseConfig;

/**
 * LogbackFileConfig
 *
 * @author kancy
 * @date 2020/6/16 21:59
 */
public class LogbackFileConfig  extends BaseConfig {

    public LogbackFileConfig() {
        setEnabled(true);
        setFileNameFormat("logback-spring.xml");
        setTemplatePath("/springboot/logback-spring.xml.ftl");
    }
}
