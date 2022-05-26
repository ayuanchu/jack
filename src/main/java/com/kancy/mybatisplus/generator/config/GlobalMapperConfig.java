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
public class GlobalMapperConfig extends BaseClassConfig {
    private static final long serialVersionUID = 1L;

    public GlobalMapperConfig() {
        setEnabled(true);
        setTemplatePath("/mapper.ftl");
        setClassNameFormat("%sMapper");
        setFileNameFormat(String.format("%s.java",getClassNameFormat()));
        setPackageName("mapper");
        setSupperClassName("com.baomidou.mybatisplus.core.mapper.BaseMapper");
    }
}
