package com.kancy.mybatisplus.generator.utils;

import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.view.UserInputDialog;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * <p>
 * AlertUtils
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 19:58
 **/

public class AlertUtils {

    private static Component defaultComponent;
    /**
     * 提示信息
     * @param msgFormat
     * @param args
     */
    public static void msg(String msgFormat, Object ... args){
        msg(JOptionPane.getRootFrame(), msgFormat, args);
    }

    /**
     * 提示信息
     * @param component
     * @param msgFormat
     * @param args
     */
    public static void msg(Component component, String msgFormat, Object ... args){
        if (Objects.isNull(component)){
            component = defaultComponent;
        }
        if (Objects.nonNull(msgFormat)){
            String msg = String.format(msgFormat.replace("{}", "%s"), args);
            Log.warn(msg);
            JOptionPane.showMessageDialog(component, msg);
        }
    }

    public static boolean yesOrNo(String msgFormat, Object ... args){
        return yesOrNo(null, msgFormat, args);
    }

    public static boolean yesOrNo(Component component,String msgFormat, Object ... args){
        assert Objects.nonNull(msgFormat);

        if (Objects.isNull(component)){
            component = defaultComponent;
        }
        String msg = String.format(msgFormat.replace("{}", "%s"), args);
        Log.warn(msg);

        int result = JOptionPane.showConfirmDialog(component, msg, "系统消息", JOptionPane.YES_NO_OPTION);
        boolean bResult = result == 0;
        Log.debug("用户选择：%s , %s", result, bResult);
        return bResult;
    }

    public static boolean verifyUserInput(Component component,String inputValue, String msgFormat, Object ... args){
        return Objects.equals(inputValue, getUserInput(component, msgFormat, args));
    }

    public static String getUserInput(Component component,String msgFormat, Object ... args){
        if (Objects.isNull(component)){
            component = defaultComponent;
        }
        String msg = String.format(msgFormat.replace("{}", "%s"), args);
        Log.warn(msg);
        String userInputValue = JOptionPane.showInputDialog(component, msg);
        return userInputValue;
    }


    public static <T> T getUserChooseInput(Component component, T[] options, String msgFormat){
        return getUserChooseInput(component, options, null, msgFormat, null);
    }

    public static <T> T getUserChooseInput(Component component, T[] options, T initValue, String msgFormat, Object ... args){
        if (Objects.isNull(component)){
            component = defaultComponent;
        }

        String msg = String.format(msgFormat.replace("{}", "%s"), args);
        Log.warn(msg);

        UserInputDialog inputDialog = new UserInputDialog(component instanceof Window ? (Window) component : null, options, initValue);
        inputDialog.setMessage(msg);
        inputDialog.setVisible(true);
        T userInputValue = (T) inputDialog.getUserInputValue();

        Log.debug("用户输入：%s", userInputValue);
        return userInputValue;
    }

    public static void setDefaultComponent(Component defaultComponent) {
        AlertUtils.defaultComponent = defaultComponent;
    }
}
