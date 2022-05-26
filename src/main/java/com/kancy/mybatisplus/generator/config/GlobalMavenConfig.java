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
public class GlobalMavenConfig extends BaseConfig {
    private static final long serialVersionUID = 1L;

    private String srcPath = "src/main/java";
    private String resourcePath = "src/main/resources";
    private String testPath = "src/test/java";

    private boolean overwritePomEnabled = false;
    private boolean interceptorEnabled = false;
    private boolean repoEnabled = true;
    private String repoUrl = "http://maven.aliyun.com/nexus/content/groups/public";


    public GlobalMavenConfig() {
        setTemplatePath("/maven/pom.xml.ftl");
        setFileNameFormat("pom.xml");
    }
}
