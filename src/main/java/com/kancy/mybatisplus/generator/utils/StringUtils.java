package com.kancy.mybatisplus.generator.utils;

import java.util.Objects;

/**
 * <p>
 * StringUtils
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 13:53
 **/

public class StringUtils {
    /**
     * 首字母大写
     * @param word
     * @return
     */
    public static boolean isEmpty(String word){
        return Objects.isNull(word) || word.isEmpty();
    }
    /**
     * 首字母大写
     * @param word
     * @return
     */
    public static String capitalize(String word){
        if (isEmpty(word)){
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
    /**
     * 首字母小写
     * @param word
     * @return
     */
    public static String firstLower(String word){
        if (isEmpty(word)){
            return word;
        }
        return word.substring(0, 1).toLowerCase() + word.substring(1);
    }

    /**
     * 截取最前面的字符串
     * @param str
     * @param maxLen
     * @return
     */
    public static String left(String str, int maxLen){
        if (Objects.isNull(str)){
            return null;
        }
        if (str.length() < maxLen){
            return str;
        }
        return str.substring(0, maxLen);
    }

    /**
     * 字符串比较
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (a != null && a.equalsIgnoreCase(b));
    }

}
