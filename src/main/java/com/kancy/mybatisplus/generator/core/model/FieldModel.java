package com.kancy.mybatisplus.generator.core.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * FieldModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/11 11:09
 **/
@Data
public class FieldModel {
    private String name;
    private String getMethodName;
    private String setMethodName;
    private String type;
    private Class typeClass;
    private String comment;
    private boolean enable;
    private boolean primary;
    private boolean fillUpdate;
    private boolean hasTableField;
    private String annotations;
    private Set<String> annotationSet = new HashSet<>();

    public void fillAnnotations(){
        if (annotationSet.isEmpty()){
            annotations = "";
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String annotation : annotationSet) {
            sb.append("\t").append(annotation).append("\r\n");
        }
        sb.append("\t");
        sb.deleteCharAt(0);
        annotations = sb.toString();
    }
}
