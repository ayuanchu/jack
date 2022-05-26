package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.service.TemplateService;
import com.kancy.mybatisplus.generator.service.impl.DatabaseServiceImpl;
import com.kancy.mybatisplus.generator.service.impl.FreemarkerTemplateServiceImpl;

/**
 * ServiceManager
 *
 * @author kancy
 * @date 2020/6/13 13:22
 */
public class ServiceManager {

    private static final DatabaseServiceImpl databaseService = new DatabaseServiceImpl();

    private static final TemplateService templateService = new FreemarkerTemplateServiceImpl();

    public static DatabaseServiceImpl getDatabaseService() {
        return databaseService;
    }

    public static TemplateService getTemplateService() {
        return templateService;
    }
}
