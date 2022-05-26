package com.kancy.mybatisplus.generator.config;

import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * BaseConfig
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 16:36
 **/
@ToString(callSuper = true)
@Data
public class BaseClassConfig extends BaseConfig {

    private String packageName;

    private String supperClassName;

    private String classNameFormat;

}
