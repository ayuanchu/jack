package com.kancy.mybatisplus.generator.view.listener;

import java.lang.annotation.*;

/**
 * @author kancy
 * @date 2019/12/21 17:43
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandAction {
    /**
     * value
     * @return
     */
    Command value();

    /**
     * 是否启用
     * @return
     */
    boolean enable() default true;
}
