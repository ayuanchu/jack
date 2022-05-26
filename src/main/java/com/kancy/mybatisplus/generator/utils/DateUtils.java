package com.kancy.mybatisplus.generator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * DateUtils
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/11 11:28
 **/

public class DateUtils {
    public static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_PATTERN_HH_MM_SS = "HH:mm:ss";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD);
    private static SimpleDateFormat timeFormat = new SimpleDateFormat(DATE_PATTERN_HH_MM_SS);
    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);

    public static String dateString(){
        return dateFormat.format(new Date());
    }

    public static String timeString(){
        return timeFormat.format(new Date());
    }

    public static String datetimeString(){
        return datetimeFormat.format(new Date());
    }
}
