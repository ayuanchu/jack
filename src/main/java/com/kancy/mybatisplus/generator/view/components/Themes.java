package com.kancy.mybatisplus.generator.view.components;

/**
 * Themes
 *
 * @author kancy
 * @date 2020/6/14 12:34
 */
public class Themes {

    public static void setTheme(String theme){
        switch (theme){
            case "random" : LookAndFeels.useRandom();break;
            case "nimbus" : LookAndFeels.useNimbus();break;
            case "metal" : LookAndFeels.useMetal();break;
            case "seaGlass" : LookAndFeels.useSeaGlass();break;
            default:
                LookAndFeels.useDefault();
        }
    }
}
