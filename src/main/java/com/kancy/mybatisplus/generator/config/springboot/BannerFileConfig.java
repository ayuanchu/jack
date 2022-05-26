package com.kancy.mybatisplus.generator.config.springboot;

import com.kancy.mybatisplus.generator.config.BaseConfig;

/**
 * BannerFileConfig
 *
 * @author kancy
 * @date 2020/6/16 21:59
 */
public class BannerFileConfig extends BaseConfig {
    public BannerFileConfig() {
        setEnabled(true);
        setFileNameFormat("banner.txt");
        setTemplatePath("/springboot/banner.txt.ftl");
    }
}
