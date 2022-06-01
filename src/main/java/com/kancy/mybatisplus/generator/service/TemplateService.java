package com.kancy.mybatisplus.generator.service;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * TemplateService
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 19:24
 **/

public interface TemplateService {
    /**
     * 渲染
     * @param templatePath
     * @param templateData
     * @return
     */
    Optional<String> render(String templatePath, Map<String, Object> templateData);
    /**
     * 渲染
     * @param template
     * @param templateData
     * @return
     */
    Optional<String> execute(String template, Map<String, Object> templateData);
}
