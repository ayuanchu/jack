package com.kancy.mybatisplus.generator.utils;

import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.log.Log;

import java.awt.*;
import java.util.Objects;

/**
 * ExceptionUtils
 *
 * @author kancy
 * @date 2020/6/13 18:10
 */
public class ExceptionUtils {

    /**
     * 异常监听
     * @param handler
     */
    public static void exceptionListener(BusinessHandler handler){
       exceptionListener(handler, null);
    }

    /**
     * 异常监听
     * @param handler
     * @param component
     */
    public static void exceptionListener(BusinessHandler handler, Component component){
        try {
            handler.handle();
        } catch (AlertException e){
            if (Objects.nonNull(e.getCause())){
                Log.error("Alert exception cause : %s", e.getCause().getMessage());
            }
            AlertUtils.msg(component, e.getAlertMessage());
        } catch (Throwable e) {
            Log.error(e.getMessage(), e);
            AlertUtils.msg(component,"未知异常: %s", e.getClass().getName());
        }
    }

    public interface BusinessHandler {
        /**
         * 处理业务代码
         */
        void handle();
    }
}
