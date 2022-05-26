package com.kancy.mybatisplus.generator;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.manager.SayDailyManager;
import com.kancy.mybatisplus.generator.view.Main;
import com.kancy.mybatisplus.generator.view.components.Themes;

import javax.swing.*;

/**
 * <p>
 * Application
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 13:18
 **/

public class Application {
    public static void main(String[] args) {
        Themes.setTheme(Settings.getTheme());
        SwingUtilities.invokeLater(() -> {
            Init.start();
            Main frame = new Main();
            frame.setTitle(String.format("%s %s by %s", frame.getTitle(), Version.VERSION, Version.AUTHOR));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            SayDailyManager.say();
        });
    }
}
