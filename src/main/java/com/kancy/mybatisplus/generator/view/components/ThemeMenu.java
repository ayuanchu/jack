package com.kancy.mybatisplus.generator.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ThemeMenu
 *
 * @author kancy
 * @date 2020/6/29 21:54
 */
public class ThemeMenu extends JMenu implements ActionListener {

    private static Map<String, String> lookAndFeels = new LinkedHashMap<>();

    private Component updateComponent;

    /**
     * Constructs a new <code>JMenu</code> with no text.
     */
    public ThemeMenu(Component updateComponent) {
        this(updateComponent, UIManager.getLookAndFeel().getName());
    }

    /**
     * Constructs a new <code>JMenu</code> with no text.
     */
    public ThemeMenu(Component updateComponent, String defaultTheme) {
        this.updateComponent = updateComponent;
        setText("设置主题");
        ButtonGroup buttonGroup = new ButtonGroup();
        lookAndFeels.entrySet().forEach(entry -> {
            try {
                Class.forName(entry.getValue());
                JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(entry.getKey());
                menuItem.addActionListener(this);
                menuItem.setActionCommand(entry.getKey());
                menuItem.setSelected(Objects.equals(defaultTheme, entry.getKey()));
                buttonGroup.add(menuItem);
                this.add(menuItem);
            } catch (ClassNotFoundException e) {
            }
        });
    }

    /**
     * 添加设置主题菜单
     * @param updateComponent
     * @param jPopupMenu
     */
    public static void addThemeMenu(Component updateComponent, JPopupMenu jPopupMenu) {
        jPopupMenu.add(new ThemeMenu(updateComponent){
            @Override
            protected void refreshUI() {
                super.refreshUI();
                SwingUtilities.updateComponentTreeUI(jPopupMenu);
            }
        });
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(lookAndFeels.get(e.getActionCommand()));
            refreshUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 刷新
     */
    protected void refreshUI() {
        SwingUtilities.updateComponentTreeUI(updateComponent);
    }

    static {

        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo installedLookAndFeel : installedLookAndFeels) {
            String name = installedLookAndFeel.getName();
            lookAndFeels.put(name, installedLookAndFeel.getClassName());
        }

        // BeautyEye
        lookAndFeels.put("BeautyEye", "org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelWin");
        lookAndFeels.put("BeautyEye", "org.jb2011.lnf.beautyeye.BeautyEyeLookAndFeelCross");

        // Pgs
        lookAndFeels.put("Pgs", "com.pagosoft.plaf.PgsLookAndFeel");

        // SeaGlass
        lookAndFeels.put("SeaGlass", "com.seaglasslookandfeel.SeaGlassLookAndFeel");

        // JTattoo
        lookAndFeels.put("Texture", "com.jtattoo.plaf.texture.TextureLookAndFeel");
        lookAndFeels.put("Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel");
        lookAndFeels.put("Mint", "com.jtattoo.plaf.mint.MintLookAndFeel");
        lookAndFeels.put("Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel");
        lookAndFeels.put("Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        lookAndFeels.put("Fast", "com.jtattoo.plaf.fast.FastLookAndFeel");
        lookAndFeels.put("Aluminium", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        lookAndFeels.put("Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel");
        lookAndFeels.put("Acryl", "com.jtattoo.plaf.acryl.AcrylLookAndFeel");

        // Substance
        lookAndFeels.put("Autumn","org.jvnet.substance.skin.SubstanceAutumnLookAndFeel");
        lookAndFeels.put("BusinessBlackSteel","org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
        lookAndFeels.put("BusinessBlueSteel","org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel");
        lookAndFeels.put("Business","org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
        lookAndFeels.put("ChallengerDeep","org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel");
        lookAndFeels.put("CremeCoffee","org.jvnet.substance.skin.SubstanceCremeCoffeeLookAndFeel");
        lookAndFeels.put("Creme","org.jvnet.substance.skin.SubstanceCremeLookAndFeel");
        lookAndFeels.put("EmeraldDusk","org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel");
        lookAndFeels.put("FieldOfWheat","org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel");
        lookAndFeels.put("GreenMagic","org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel");
        lookAndFeels.put("Magma","org.jvnet.substance.skin.SubstanceMagmaLookAndFeel");
        lookAndFeels.put("Mango","org.jvnet.substance.skin.SubstanceMangoLookAndFeel");
        lookAndFeels.put("MistAqua","org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel");
        lookAndFeels.put("MistSilver","org.jvnet.substance.skin.SubstanceMistSilverLookAndFeel");
        lookAndFeels.put("Moderate","org.jvnet.substance.skin.SubstanceModerateLookAndFeel");
        lookAndFeels.put("NebulaBrickWall","org.jvnet.substance.skin.SubstanceNebulaBrickWallLookAndFeel");
        lookAndFeels.put("Nebula","org.jvnet.substance.skin.SubstanceNebulaLookAndFeel");
        lookAndFeels.put("OfficeBlue2007","org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel");
        lookAndFeels.put("OfficeSilver2007","org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel");
        lookAndFeels.put("RavenGraphiteGlass","org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel");
        lookAndFeels.put("RavenGraphite","org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
        lookAndFeels.put("Raven","org.jvnet.substance.skin.SubstanceRavenLookAndFeel");
        lookAndFeels.put("Sahara","org.jvnet.substance.skin.SubstanceSaharaLookAndFeel");
    }
}
