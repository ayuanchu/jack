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
public class GlobalDaoConfig extends BaseClassConfig {
    private static final long serialVersionUID = 1L;

    public GlobalDaoConfig() {
        setEnabled(true);
        setTemplatePath("/dao.ftl");
        setClassNameFormat("%sDao");
        setFileNameFormat(String.format("%s.java",getClassNameFormat()));
        setPackageName("dao");
        setSupperClassName("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
    }
}
