package com.kancy.mybatisplus.generator.config;

import com.kancy.mybatisplus.generator.config.springboot.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

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
public class GlobalSpringBootConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String configFileType;

    private boolean enabled = false;
    private boolean micaEnabled = true;
    private boolean actuatorEnabled = true;
    private boolean prometheusEnabled = false;
    private boolean druidEnabled = false;
    private boolean hakiriEnabled = true;
    private boolean mongoEnabled = false;
    private boolean redisEnabled = false;
    private boolean redisLockEnabled = false;
    private boolean okhttpEnabled = false;
    private boolean jasyptEnabled = false;
    private boolean swaggerEnabled = false;
    private boolean dockerEnabled = false;

    private boolean rabbitmqEnabled = false;
    private boolean kafkaEnabled = false;
    private boolean streamRabbitEnabled = false;
    private boolean streamKafkaEnabled = false;
    private boolean freemarkerEnabled = false;

    private ApplicationDbResourceFileConfig applicationDbResourceFile = new ApplicationDbResourceFileConfig();
    private ApplicationResourceFileConfig applicationResourceFile = new ApplicationResourceFileConfig();
    private ApplicationClassFileConfig applicationClassFile = new ApplicationClassFileConfig();
    private MybatisPlusConfigClassFileConfig mybatisPlusConfigClassFile = new MybatisPlusConfigClassFileConfig();
    private LogbackFileConfig logbackFile = new LogbackFileConfig();
    private BannerFileConfig bannerFile = new BannerFileConfig();

    public GlobalSpringBootConfig() {
        setConfigFileType("yml");
    }

}
