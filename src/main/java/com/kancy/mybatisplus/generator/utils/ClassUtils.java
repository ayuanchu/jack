package com.kancy.mybatisplus.generator.utils;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * ClassUtils
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/11 12:55
 **/

public class ClassUtils {

    private static List<String> baseClass = Arrays.asList(new String[]{"byte","char","short","int","long","float","double","boolean"});

    public static boolean isAutoImportClass(String className){
        assert Objects.nonNull(className);
        if (className.startsWith("java.lang.") || baseClass.contains(className)){
            return true;
        }
        return false;
    }

    public static boolean isObjectClass(Object object){
        if (Objects.isNull(object)){
            return true;
        }
        return Objects.equals(object, Object.class.getName())
                || Objects.equals(object, Object.class);
    }

    public static boolean isBoolClass(Class typeClass) {
        return Objects.equals(typeClass, Boolean.class) || Objects.equals(typeClass, boolean.class);
    }

    public static boolean isDateClass(Class typeClass){
        return Objects.equals(typeClass, Date.class)
                || Objects.equals(typeClass, LocalDate.class)
                || Objects.equals(typeClass, LocalDateTime.class);
    }

    public static <T> T newInstance(Class<T> klass, Object ... constructorArgs){
        try {
            Class[] constructorArgClasses = new Class[constructorArgs.length];
            for (int i = 0; i <constructorArgs.length; i++) {
                constructorArgClasses[i] = constructorArgs[i].getClass();
            }
            Constructor constructor = null;
            try {
                constructor = klass.getConstructor(constructorArgClasses);
            } catch (NoSuchMethodException e) {
                return null;
            }
            return (T) constructor.newInstance(constructorArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
