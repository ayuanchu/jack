package com.kancy.mybatisplus.generator.config;

import com.kancy.mybatisplus.generator.enums.KeyStrategyEnum;
import lombok.Data;
import lombok.SneakyThrows;
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
public class GlobalEntityConfig extends BaseClassConfig {
    private static final long serialVersionUID = 1L;

    private String keyStrategy = KeyStrategyEnum.NONE.name();

    public GlobalEntityConfig() {
        setEnabled(true);
        setTemplatePath("/entity.ftl");
        setClassNameFormat("%s");
        setFileNameFormat(String.format("%s.java",getClassNameFormat()));
        setPackageName("entity");
        setSupperClassName("com.baomidou.mybatisplus.extension.activerecord.Model");
    }

    @SneakyThrows
    @Override
    public GlobalEntityConfig clone()  {
        return (GlobalEntityConfig)super.clone();
    }
}
