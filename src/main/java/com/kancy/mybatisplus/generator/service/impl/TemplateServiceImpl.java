package com.kancy.mybatisplus.generator.service.impl;

import com.kancy.mybatisplus.generator.service.TemplateService;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * TemplateServiceImpl
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/12 15:47
 **/

public class TemplateServiceImpl implements TemplateService {

    private static final TemplateService templateService = new FreemarkerTemplateServiceImpl();

    /**
     * 渲染
     *
     * @param templatePath
     * @param templateData
     * @return
     */
    @Override
    public Optional<String> render(String templatePath, Map<String, Object> templateData) {
        return templateService.render(templatePath, templateData);
    }

    /**
     * 渲染
     *
     * @param template
     * @param templateData
     * @return
     */
    @Override
    public Optional<String> execute(String template, Map<String, Object> templateData) {
        return templateService.render(template, templateData);
    }
}
