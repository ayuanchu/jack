package com.kancy.mybatisplus.generator.view.model;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.manager.GlobalCacheManager;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ModuleComboBoxModel
 *
 * @author kancy
 * @date 2020/6/12 0:33
 */
public class ModuleComboBoxModel extends DefaultComboBoxModel {

    /**
     * Constructs an empty DefaultComboBoxModel object.
     */
    public ModuleComboBoxModel() {
        init();
    }

    private void init() {
        List items = GlobalCacheManager.getModuleComboBoxModelItems();
        if (Objects.nonNull(items)){
            for (Object item : items) {
                addElement(item);
            }
        } else {
            addElement(Settings.getDefaultModuleName());
        }
    }

    public void refresh(String addModulePath){
        if (!Objects.equals(getElementAt(0), addModulePath)){
            removeElement(addModulePath);
            insertElementAt(addModulePath, 0);
            setSelectedItem(addModulePath);
            int maxHistoryModulePathSize = Settings.getMaxHistorySize();
            if (getSize() > maxHistoryModulePathSize ){
                removeElementAt(maxHistoryModulePathSize);
            }
            GlobalCacheManager.updateModuleComboBoxModelItems(getAllData(maxHistoryModulePathSize));
        }
    }

    private ArrayList getAllData(int size) {
        ArrayList objects = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            objects.add(getElementAt(i));
        }
        return objects;
    }

    @Data
    @NoArgsConstructor
    public static class ItemData implements Serializable {
        private static final long serialVersionUID = 1L;

        private boolean selected;
        private String name;
        private String path;

        public ItemData(String name, String path) {
            this.name = name;
            this.path = path;
        }

        @Override
        public String toString() {
            if (selected){
                return getPath();
            }
            if (StringUtils.isEmpty(name)){
                return String.format(getPath());
            }
            return String.format("%s（%s）", name, getPath());
        }

        public String getPath() {
            if (StringUtils.isEmpty(path)){
                return path;
            }
            return path.replace("\\","/")
                    .replaceAll("/+","/");
        }

    }

}
