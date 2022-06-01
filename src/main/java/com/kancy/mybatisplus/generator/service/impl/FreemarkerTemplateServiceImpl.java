package com.kancy.mybatisplus.generator.service.impl;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.exception.TemplateException;
import com.kancy.mybatisplus.generator.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * FreemarkerEmailTemplateServiceImpl
 *
 * @author kancy
 * @date 2020/2/21 0:11
 */
public class FreemarkerTemplateServiceImpl implements TemplateService {
    /**
     * 去除空白字符正则
     */
    private static final Pattern REMOVE_SPACE_PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    private String baseTemplatePath = "/freemark";

    private Configuration configuration;

    public FreemarkerTemplateServiceImpl() {
    }

    public FreemarkerTemplateServiceImpl(String baseTemplatePath) {
        this.baseTemplatePath = baseTemplatePath;
    }

    /**
     * 渲染
     * @param templatePath
     * @param templateData
     * @return
     * @return
     */
    @Override
    public Optional<String> render(String templatePath, Map<String, Object> templateData) {
        StringWriter result = null;
        try {
            Template template = getConfiguration().getTemplate(templatePath);
            result = new StringWriter();
            template.process(templateData, result);
        } catch (IOException e){
            throw new TemplateException("模板引擎执行错误！", e);
        } catch (freemarker.template.TemplateException e) {
            throw new TemplateException("Freemarker模板可能存在错误，请检查！", e);
        }
        return Optional.ofNullable(result.getBuffer().toString());
    }

    /**
     * 渲染
     *
     * @param templateData
     * @param modelData
     * @return
     */
    @Override
    public Optional<String> execute(String templateData, Map<String, Object> modelData) {
        StringWriter result = null;
        try {
            Template template = new Template(null, templateData, setConfiguration(new Configuration()));
            result = new StringWriter();
            template.process(modelData, result);
        } catch (IOException e){
            throw new TemplateException("模板引擎执行错误！", e);
        } catch (freemarker.template.TemplateException e) {
            throw new TemplateException("Freemarker模板可能存在错误，请检查！", e);
        }
        return Optional.ofNullable(result.getBuffer().toString());
    }

    /**
     * 初始化并且线程安全
     * @return
     */
    protected final Configuration getConfiguration(){
        if (Objects.isNull(configuration)){
            synchronized (this){
                if (Objects.isNull(configuration)){
                    configuration = initConfiguration();
                }
            }
        }
        return configuration;
    }

    /**
     * 初始化配置
     * @return
     */
    protected Configuration initConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(FreemarkerTemplateServiceImpl.class, baseTemplatePath);
        setConfiguration(configuration);
        return configuration;
    }

    /**
     * 设置属性
     * @param configuration
     */
    protected Configuration setConfiguration(Configuration configuration) {
        configuration.setLocale(Locale.CHINA);
        configuration.setDefaultEncoding(Settings.getDefaultCharsetName());
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setNumberFormat("#.######");
        configuration.setBooleanFormat("true,false");
        return configuration;
    }

}
