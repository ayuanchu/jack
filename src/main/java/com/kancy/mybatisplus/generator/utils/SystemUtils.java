package com.kancy.mybatisplus.generator.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * SystemUtils
 *
 * @author kancy
 * @date 2020/6/14 10:22
 */
public class SystemUtils {

    public static String getUserName(){
        return System.getenv("USERNAME");
    }

    public static String getAdminUserName(){
        return "Administrator";
    }

    /**
     * 将字符串复制到剪切板。
     */
    public static void setClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }
}
