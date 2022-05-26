/*
 * Created by JFormDesigner on Sat Jun 27 00:58:28 CST 2020
 */

package com.kancy.mybatisplus.generator.view;

import com.kancy.mybatisplus.generator.manager.NetResourceManager;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * @author kancy
 */
public class ExchangeGroupDialog extends JDialog {
    public ExchangeGroupDialog(Window owner) {
        super(owner);
        initComponents();
        label_wx_me.setIcon(new ImageIcon(NetResourceManager.getImage_wx_me()));
        label_pay_support_image.setIcon(new ImageIcon(NetResourceManager.getImage_pay()));
        label_wechat.setIcon(new ImageIcon(NetResourceManager.getImage_qq_me()));
        label_qq.setIcon(new ImageIcon(NetResourceManager.getImage_qq()));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        tabbedPane2 = new JTabbedPane();
        panel_wx_me = new JPanel();
        label_wx_me = new JLabel();
        panel_wechat = new JPanel();
        label_wechat = new JLabel();
        panel_qq = new JPanel();
        label_qq = new JLabel();
        panel_pay_support = new JPanel();
        label_pay_support_author = new JLabel();
        label_pay_support_author_value = new JLabel();
        label_pay_support_email = new JLabel();
        textField_pay_support_email = new JTextField();
        label_pay_support = new JLabel();
        label_pay_support_image = new JLabel();

        //======== this ========
        setAlwaysOnTop(true);
        setModal(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabbedPane2 ========
        {

            //======== panel_wx_me ========
            {
                panel_wx_me.setLayout(new BorderLayout());
                panel_wx_me.add(label_wx_me, BorderLayout.CENTER);
            }
            tabbedPane2.addTab(bundle.getString("ExchangeGroupDialog.panel_wx_me.tab.title"), panel_wx_me);

            //======== panel_wechat ========
            {
                panel_wechat.setLayout(new BorderLayout());
                panel_wechat.add(label_wechat, BorderLayout.CENTER);
            }
            tabbedPane2.addTab(bundle.getString("ExchangeGroupDialog.panel_wechat.tab.title"), panel_wechat);

            //======== panel_qq ========
            {
                panel_qq.setLayout(new BorderLayout());
                panel_qq.add(label_qq, BorderLayout.CENTER);
            }
            tabbedPane2.addTab(bundle.getString("ExchangeGroupDialog.panel_qq.tab.title"), panel_qq);

            //======== panel_pay_support ========
            {
                panel_pay_support.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[240,fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[225]"));
                panel_pay_support.add(label_pay_support_author, "cell 0 0");
                panel_pay_support.add(label_pay_support_author_value, "cell 1 0");
                panel_pay_support.add(label_pay_support_email, "cell 0 1");

                //---- textField_pay_support_email ----
                textField_pay_support_email.setEditable(false);
                panel_pay_support.add(textField_pay_support_email, "cell 1 1,growx");
                panel_pay_support.add(label_pay_support, "cell 0 2,aligny top,growy 0");
                panel_pay_support.add(label_pay_support_image, "cell 1 2,align center center,grow 0 0");
            }
            tabbedPane2.addTab(bundle.getString("ExchangeGroupDialog.panel_pay_support.tab.title"), panel_pay_support);
        }
        contentPane.add(tabbedPane2, BorderLayout.NORTH);

        initComponentsI18n();

        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    private void initComponentsI18n() {
        // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
        ResourceBundle bundle = ResourceBundle.getBundle("i18n");
        setTitle(bundle.getString("ExchangeGroupDialog.this.title"));
        tabbedPane2.setTitleAt(0, bundle.getString("ExchangeGroupDialog.panel_wx_me.tab.title"));
        tabbedPane2.setTitleAt(1, bundle.getString("ExchangeGroupDialog.panel_wechat.tab.title"));
        tabbedPane2.setTitleAt(2, bundle.getString("ExchangeGroupDialog.panel_qq.tab.title"));
        label_pay_support_author.setText(bundle.getString("ExchangeGroupDialog.label_pay_support_author.text"));
        label_pay_support_author_value.setText(bundle.getString("ExchangeGroupDialog.label_pay_support_author_value.text"));
        label_pay_support_email.setText(bundle.getString("ExchangeGroupDialog.label_pay_support_email.text"));
        textField_pay_support_email.setText(bundle.getString("ExchangeGroupDialog.textField_pay_support_email.text"));
        label_pay_support.setText(bundle.getString("ExchangeGroupDialog.label_pay_support.text"));
        tabbedPane2.setTitleAt(3, bundle.getString("ExchangeGroupDialog.panel_pay_support.tab.title"));
        // JFormDesigner - End of component i18n initialization  //GEN-END:initI18n
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane2;
    private JPanel panel_wx_me;
    private JLabel label_wx_me;
    private JPanel panel_wechat;
    private JLabel label_wechat;
    private JPanel panel_qq;
    private JLabel label_qq;
    private JPanel panel_pay_support;
    private JLabel label_pay_support_author;
    private JLabel label_pay_support_author_value;
    private JLabel label_pay_support_email;
    private JTextField textField_pay_support_email;
    private JLabel label_pay_support;
    private JLabel label_pay_support_image;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
