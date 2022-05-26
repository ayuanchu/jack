package com.kancy.mybatisplus.generator.view.components;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * AbstractFileChooser
 *
 * @author kancy
 * @date 2020/2/16 16:44
 */
public abstract class AbstractFileChooser {
    private Component parentComponent;

    private boolean hasSelectedFile;

    private String title;

    private JFileChooser fileChooser = new JFileChooser(new File("."));

    public AbstractFileChooser() {
    }

    public AbstractFileChooser(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public boolean isMultiSelectionEnabled() {
        return fileChooser.isMultiSelectionEnabled();
    }

    /**
     * 多选功能
     * @param enabled
     * @return
     */
    public AbstractFileChooser setMultiSelectionEnabled(boolean enabled){
        fileChooser.setMultiSelectionEnabled(enabled);
        return this;
    }

    /**
     * 可以选择文件和文件夹
     * @return
     */
    public AbstractFileChooser setFileSelection(){
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        return this;
    }
    /**
     * 只能选择文件
     * @return
     */
    public AbstractFileChooser setOnlyFileSelection(){
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return this;
    }
    /**
     * 只能选择文件夹
     * @return
     */
    public AbstractFileChooser setOnlyDirectorySelection(){
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return this;
    }

    /**
     * 设置当前路径为浏览的文件夹
     * @return
     */
    public AbstractFileChooser setCurrentDirectory(){
        fileChooser.setCurrentDirectory(new File("."));
        return this;
    }
    public AbstractFileChooser setCurrentDirectory(File file){
        fileChooser.setCurrentDirectory(file);
        return this;
    }
    public AbstractFileChooser setCurrentDirectory(String filePath){
        fileChooser.setCurrentDirectory(new File(filePath));
        return this;
    }

    /**
     * 是否显示隐藏文件
     * @param enabled
     * @return
     */
    public AbstractFileChooser setHideFileShowEnabled(boolean enabled){
        fileChooser.setFileHidingEnabled(enabled);
        return this;
    }

    /**
     * 设置文件过滤器
     * @param fileFilter
     * @return
     */
    public AbstractFileChooser setFileFilter(FileFilter fileFilter){
        fileChooser.setFileFilter(fileFilter);
        return this;
    }

    /**
     * 设置文件过滤器
     * @param extensions
     * @return
     */
    public AbstractFileChooser setFileNameExtensionFilter(String... extensions){
        fileChooser.setFileFilter(new FileNameExtensionFilter(extensions));
        return this;
    }
    public AbstractFileChooser setFileNameExtensionFilter(String description, String... extensions){
        fileChooser.setFileFilter(new FileNameExtensionFilter(description,extensions));
        return this;
    }

    /**
     * 设置默认选择的文件
     * @param file
     * @return
     */
    public AbstractFileChooser setDefaultSelectedFile(File file){
        fileChooser.setSelectedFile(file);
        return this;
    }

    /**
     * 打开 Open Dialog
     */
    public void showOpenDialog(){
        processChooserResult(fileChooser.showOpenDialog(parentComponent));
    }

    /**
     * 打开 Save Dialog
     */
    public void showSaveDialog(){
        processChooserResult(fileChooser.showSaveDialog(parentComponent));
    }

    /**
     * 处理选择结果
     * @param result
     */
    private void processChooserResult(int result) {
        this.hasSelectedFile = Objects.equals(result, JFileChooser.APPROVE_OPTION);
    }

    public boolean hasSelectedFile() {
        return hasSelectedFile;
    }

    public File getSelectedFile() {
        if (hasSelectedFile()){
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    public String getSelectedFilePath() {
        if (hasSelectedFile()){
            File selectedFile = getSelectedFile();
            String filePath = null;
            try {
                filePath = selectedFile.getCanonicalPath();
            } catch (IOException e) {
                filePath = selectedFile.getAbsolutePath();
            }
            return filePath.replaceAll("\\+","/");
        }
        return null;
    }

    public File[] getSelectedFiles() {
        if (hasSelectedFile()){
            return fileChooser.getSelectedFiles();
        }
        return null;
    }

    public List<String> getSelectedFilePaths() {
        File[] selectedFiles = getSelectedFiles();
        List<String> list = new ArrayList<>();
        if (Objects.isNull(selectedFiles)){
            return list;
        }
        for (File selectedFile : selectedFiles) {
            list.add(selectedFile.getAbsolutePath().replaceAll("\\+","/"));
        }
        return list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        fileChooser.setDialogTitle(title);
    }
}