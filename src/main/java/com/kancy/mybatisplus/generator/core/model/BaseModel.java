package com.kancy.mybatisplus.generator.core.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * BaseModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 19:30
 **/
@Data
public abstract class BaseModel {
    /**
     * 服务名称
     */
    private String name;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 超类包名
     */
    private String supperClassPackageName;

    /**
     * 超类简单类名
     */
    private String supperClassSimpleName;

    /**
     * 继承内容
     */
    private String extendsStatement;

    /**
     * 导入的类
     */
    private Set<String> importClasses = new TreeSet<>();

    /**
     * 导入的注解
     */
    private List<String> classAnnotationList = new ArrayList<>();
}
