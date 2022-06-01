package com.kancy.mybatisplus.generator.view.model;

import com.kancy.mybatisplus.generator.manager.DatabaseManager;

import javax.swing.*;
import java.util.List;

/**
 * <p>
 * DatabaseComboBoxModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 22:52
 **/

public class DatabaseComboBoxModel extends DefaultComboBoxModel<String> {
    /**
     * Constructs an empty DefaultComboBoxModel object.
     */
    public DatabaseComboBoxModel() {
        init();
    }

    private void init() {
        List<String> databases = DatabaseManager.getActiveDataSourceDatabases();
        if (!databases.isEmpty()){
            this.removeAllElements();
            for (String database : databases) {
                this.addElement(database);
            }
            setSelectedItem(databases.get(0));
        }
    }
}
