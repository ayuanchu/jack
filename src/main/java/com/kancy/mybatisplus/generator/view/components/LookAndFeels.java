package com.kancy.mybatisplus.generator.view.components;

import com.kancy.mybatisplus.generator.log.Log;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LookAndFeels
 *
 * @author kancy
 * @date 2020/6/13 17:05
 */
public class LookAndFeels {
    private static final Set<UIManager.LookAndFeelInfo> installedLookAndFeelSet = new HashSet<>();

    private static final Map<String, UIManager.LookAndFeelInfo> installedLookAndFeelMap = new HashMap<>();

    static {
        // 安装主题
        installLookAndFeel("SeaGlass", "com.seaglasslookandfeel.SeaGlassLookAndFeel");

        // 不好看的主题
        List<String> excludeLookAndFeels = Arrays.asList(new String[]{"CDE/Motif","Windows Classic","Windows"});
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo installedLookAndFeel : installedLookAndFeels) {
            String name = installedLookAndFeel.getName();
            if (!excludeLookAndFeels.contains(name)){
                installedLookAndFeelSet.add(installedLookAndFeel);
                installedLookAndFeelMap.put(name, installedLookAndFeel);
                installedLookAndFeelMap.put(name.toLowerCase(), installedLookAndFeel);
                installedLookAndFeelMap.put(installedLookAndFeel.getClassName(), installedLookAndFeel);
                Log.debug("Install Theme : %s", installedLookAndFeel.getName());
            }
        }
    }

    public static void useRandom(){
        Iterator<Map.Entry<String, UIManager.LookAndFeelInfo>> iterator = installedLookAndFeelMap.entrySet().iterator();
        int index = ThreadLocalRandom.current().nextInt(0, installedLookAndFeelMap.size());
        UIManager.LookAndFeelInfo lookAndFeelInfo = null;
        for (int i = 0; i < index; i++) {
            if (iterator.hasNext()){
                lookAndFeelInfo = iterator.next().getValue();
            }
        }
        if (Objects.nonNull(lookAndFeelInfo)){
            setLookAndFeel(lookAndFeelInfo.getClassName());
        }
    }

    public static void useNimbus(){
        setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    public static void useMetal(){
        setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    }

    public static void useSeaGlass(){
        setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
    }

    public static void useDefault(){
    }


    private static void installLookAndFeel(String name, String className) {
        try {
            UIManager.installLookAndFeel(name, className);
        } catch (Exception e) {
        }
    }

    public static Set<UIManager.LookAndFeelInfo> getInstalledLookAndFeelSet(){
        return installedLookAndFeelSet;
}

    private static void setLookAndFeel(String lookAndFeelClassName) {
        try {
            UIManager.setLookAndFeel(lookAndFeelClassName);
            Log.info("LookAndFeel set : %s", lookAndFeelClassName);
        } catch (Exception e) {
            Log.error("LookAndFeel set fail : %s", e.getMessage());
        }
    }
}
