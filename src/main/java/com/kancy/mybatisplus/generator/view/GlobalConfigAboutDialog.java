/*
 * Created by JFormDesigner on Tue Jun 16 20:54:07 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.log.Log;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author kancy
 */
public class GlobalConfigAboutDialog extends JDialog {
    public GlobalConfigAboutDialog(Window owner) {
        super(owner);
        initComponents();
        initValue();
    }

    private void initValue() {
        editorPane_about.setEditable(false);
        try {
            URL url = this.getClass().getClassLoader().getResource("html/global_config_about.html");
            editorPane_about.setPage(url);
        } catch (IOException e) {
            Log.error("加载global_config_about.html失败！");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        scrollPane_about = new JScrollPane();
        editorPane_about = new JEditorPane();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(bundle.getString("global.config.about.dialog.this.title"));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== scrollPane_about ========
        {

            //---- editorPane_about ----
            editorPane_about.setContentType("text/html");
            editorPane_about.setText("<html>\r   <head>\r \r   </head>\r   <body>\r     <p style=\"margin-top: 0\">\r       \r     </p>\r   </body>\r </html>\r ");
            editorPane_about.setPreferredSize(new Dimension(680, 450));
            scrollPane_about.setViewportView(editorPane_about);
        }
        contentPane.add(scrollPane_about, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane_about;
    private JEditorPane editorPane_about;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
