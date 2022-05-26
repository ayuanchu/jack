package com.kancy.mybatisplus.generator.core.model;

import lombok.Data;

/**
 * <p>
 * MavenModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/12 10:15
 **/
@Data
public class MavenModel {
    private String groupId;
    private String artifactId;
    private String projectName;
    private String projectDescription = "写一句话描述你的项目";
    private String version = "1.0.0-SNAPSHOT";
}
