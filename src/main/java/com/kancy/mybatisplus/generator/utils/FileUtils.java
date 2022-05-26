package com.kancy.mybatisplus.generator.utils;

import java.util.Objects;

/**
 * <p>
 * FileUtils
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/11 15:33
 **/

public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 是否相同文件
     * @param path1
     * @param path2
     * @return
     */
    public static boolean isSameFile(String path1, String path2) {
        return Objects.equals(getFile(path1).getAbsolutePath(), getFile(path2).getAbsolutePath());
    }
}