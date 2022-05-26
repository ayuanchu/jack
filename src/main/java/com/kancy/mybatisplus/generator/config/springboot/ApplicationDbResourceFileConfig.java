package com.kancy.mybatisplus.generator.config.springboot;

import com.kancy.mybatisplus.generator.config.BaseConfig;
import lombok.Data;

/**
 * ApplicationResourceFileConfig
 *
 * @author kancy
 * @date 2020/6/16 21:58
 */
@Data
public class ApplicationDbResourceFileConfig extends BaseConfig {
    public ApplicationDbResourceFileConfig() {
        setEnabled(true);
        setTemplatePath("/springboot/application-db.yml.ftl");
        setFileNameFormat("application-db.yml");
    }
}
