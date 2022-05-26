package com.kancy.mybatisplus.generator.view.listener.handler;

import com.kancy.mybatisplus.generator.core.CodeGenerator;
import com.kancy.mybatisplus.generator.core.model.EntityModel;
import com.kancy.mybatisplus.generator.core.model.FieldModel;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import com.kancy.mybatisplus.generator.utils.DateUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import com.kancy.mybatisplus.generator.utils.SystemUtils;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.listener.Command;
import com.kancy.mybatisplus.generator.view.listener.CommandAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * CopyEntityCommandHandler
 *
 * @author kancy
 * @date 2020/6/14 14:52
 */
@CommandAction(Command.COPY_ENTITY_JSON)
public class CopyEntityJsonCommandHandler extends CustomCodeGeneratorCommandHandler {

    private static final String SPACE = "    ";
    private static final String LINE_SPLIT_CHAR = "\r\n";

    public CopyEntityJsonCommandHandler(Main component) {
        super(component);
    }

    @Override
    protected void handleCodeGenerator(CodeGenerator codeGenerator) {

        EntityModel entityModel = codeGenerator.getEntityModel();
        List<ColumnInfo> columnInfos = entityModel.getColumnInfos();
        List<FieldModel> fields = entityModel.getFields();
        StringBuffer sb = new StringBuffer();
        sb.append("{").append(LINE_SPLIT_CHAR);
        for (int i = 0; i < fields.size(); i++) {
            FieldModel fieldModel = fields.get(i);
            if (!fieldModel.isEnable()){
                continue;
            }
            String columnDefault = columnInfos.get(i).getColumnDefault();
            sb.append(SPACE);
            sb.append("\"").append(fieldModel.getName()).append("\"").append(":");
            if (Objects.equals(fieldModel.getTypeClass(), Long.class)
                    || Objects.equals(fieldModel.getTypeClass(), Integer.class)){
                sb.append(StringUtils.isEmpty(columnDefault)?"0":columnDefault);
            }else if(Objects.equals(fieldModel.getTypeClass(), Double.class)
                    || Objects.equals(fieldModel.getTypeClass(), Float.class)
                    || Objects.equals(fieldModel.getTypeClass(), BigDecimal.class)){
                sb.append(StringUtils.isEmpty(columnDefault)?"0.0":columnDefault);
            }else if(Objects.equals(fieldModel.getTypeClass(), Date.class)
                    || Objects.equals(fieldModel.getTypeClass(), LocalDateTime.class)){
                sb.append("\"").append(DateUtils.datetimeString()).append("\"");
            }else if(Objects.equals(fieldModel.getTypeClass(), LocalDate.class)){
                sb.append("\"").append(DateUtils.datetimeString()).append("\"");
            }else if(Objects.equals(fieldModel.getTypeClass(), LocalTime.class)){
                sb.append("\"").append(DateUtils.datetimeString()).append("\"");
            }else if(Objects.equals(fieldModel.getTypeClass(), Boolean.class)){
                sb.append(true);
            }else {
                sb.append("\"").append(StringUtils.isEmpty(columnDefault)?"demoData":columnDefault).append("\"");
            }
            if (i < fields.size()-1){
                sb.append(",");
            }
            sb.append(LINE_SPLIT_CHAR);
        }
        if (Objects.equals(sb.charAt(sb.length()-2), ",")){
            sb.deleteCharAt(sb.length()-2);
        }
        sb.append("}");
        SystemUtils.setClipboardText(sb.toString());
    }

    @Override
    protected void doSuccess() {
        AlertUtils.msg(component, "复制成功！");
    }
}
