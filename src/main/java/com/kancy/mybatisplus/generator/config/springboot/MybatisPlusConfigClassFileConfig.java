package com.kancy.mybatisplus.generator.config.springboot;

import com.kancy.mybatisplus.generator.config.BaseClassConfig;

/**
 * MybatisPlusConfigClassFileConfig
 *
 * @author kancy
 * @date 2020/6/16 21:59
 */
public class MybatisPlusConfigClassFileConfig extends BaseClassConfig {

    public MybatisPlusConfigClassFileConfig() {
        setEnabled(true);
        setPackageName("config");
        setFileNameFormat("MybatisPlusConfig.java");
        setTemplatePath("/springboot/MybatisPlusConfig.java.ftl");
    }
}
