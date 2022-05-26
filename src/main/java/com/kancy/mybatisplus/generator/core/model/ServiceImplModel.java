package com.kancy.mybatisplus.generator.core.model;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * ServiceModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 19:28
 **/
@Data
public class ServiceImplModel extends ServiceModel {
    private List<String> autowires;
}
