package com.kancy.mybatisplus.generator.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * HumpUtils
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 13:54
 **/

public class HumpUtils {

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param str
     * @param removePrefixs
     * @param addSuffix
     * @return
     */
    public static String lineToHump(String str, String removePrefixs, String addSuffix) {
        if (!StringUtils.isEmpty(removePrefixs)){
            String[] strings = removePrefixs.split(";");
            for (String prefix : strings) {
                if (!StringUtils.isEmpty(prefix) && str.startsWith(prefix.trim())){
                    str = str.substring(prefix.trim().length());
                }
            }



        }
        if (Objects.nonNull(addSuffix)){
            str += addSuffix;
        }
        return lineToHump(str);
    }

    /**
     * 下划线转驼峰
     * @param str
     * @param removePrefix 分号分割
     * @return
     */
    public static String lineToHump(String str, String removePrefix) {
        return lineToHump(str, removePrefix, null);
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
