package com.kancy.mybatisplus.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ProjectEnum
 *
 * @author kancy
 * @date 2020/6/14 17:36
 */
@Getter
@AllArgsConstructor
public enum ProjectEnum {
    NONE(0, "无","无工程"),
    MAVEN(1, "普通Maven工程","普通Maven工程"),
    MAVEN_SPRING_BOOT(2, "Spring Boot工程","Spring Boot工程"),
    ;

    private int index;

    private String name;

    private String description;

    @Override
    public String toString() {
        return getName();
    }
}
