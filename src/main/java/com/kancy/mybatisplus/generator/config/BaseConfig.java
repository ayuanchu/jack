package com.kancy.mybatisplus.generator.config;

import com.kancy.mybatisplus.generator.enums.TemplateEngineTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * BaseConfig
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 16:36
 **/
@ToString(exclude = {"template", "templatePath", "fileNameFormat"})
@Data
public class BaseConfig implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * 使用自定义配置
     */
    private boolean useCustom;

    /**
     * 使用默认配置
     * @Deprecated 过期属性
     */
    private boolean useDefault;

    private boolean enabled;
    private String templateEngineType = TemplateEngineTypeEnum.Freemark.name();
    private String template;
    private String templatePath;
    private String fileNameFormat;

}
