package com.kancy.mybatisplus.generator.view.model;

import com.kancy.mybatisplus.generator.manager.DataSourceManager;

import javax.swing.*;
import java.util.Collection;
import java.util.Objects;

/**
 * <p>
 * DatasourceComboBoxModel
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 0:23
 **/

public class DatasourceComboBoxModel extends DefaultComboBoxModel {
    /**
     * Constructs an empty DefaultComboBoxModel object.
     */
    public DatasourceComboBoxModel() {
        init();
    }

    private void init() {
        Collection dataSourceConfigs = DataSourceManager.getDataSourceConfigs();
        if (!dataSourceConfigs.isEmpty()){
            this.removeAllElements();
            for (Object database : dataSourceConfigs) {
                this.addElement(database);
            }
            if (Objects.nonNull(DataSourceManager.getActive())){
                this.setSelectedItem(DataSourceManager.getActive());
            }
        }
    }
}
