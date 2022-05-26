package com.kancy.mybatisplus.generator.core.model;

import com.kancy.mybatisplus.generator.service.ColumnInfo;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * EntityModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 19:26
 **/
@Data
public class EntityModel extends BaseModel {
    /**
     * 数据库字段列表
     */
    private List<ColumnInfo> columnInfos;

    /**
     * 字段列表
     */
    private List<FieldModel> fields;

}
