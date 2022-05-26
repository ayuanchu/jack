package com.kancy.mybatisplus.generator.view.model;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.manager.GlobalCacheManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ModuleComboBoxModel
 *
 * @author kancy
 * @date 2020/6/12 0:33
 */
public class PackageComboBoxModel extends DefaultComboBoxModel {

    /**
     * Constructs an empty DefaultComboBoxModel object.
     */
    public PackageComboBoxModel() {
        init();
    }

    private void init() {
        List items = GlobalCacheManager.getPackageComboBoxModelItems();
        if (Objects.nonNull(items)){
            for (Object item : items) {
                addElement(item);
            }
        }else {
            addElement(Settings.getDefaultPackageName());
        }
    }

    public void refresh(String packageName){
        if (!Settings.isIdea()
                && !Objects.equals(getElementAt(0), packageName)){
            removeElement(packageName);
            insertElementAt(packageName, 0);
            setSelectedItem(packageName);
            int maxHistoryPackageSize = Settings.getMaxHistorySize();
            if (getSize() > maxHistoryPackageSize){
                removeElementAt(maxHistoryPackageSize);
            }
            GlobalCacheManager.updatePackageComboBoxModelItems(getAllData(maxHistoryPackageSize));
        }
    }

    private ArrayList getAllData(int size) {
        ArrayList objects = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            objects.add(getElementAt(i));
        }
        return objects;
    }
}
