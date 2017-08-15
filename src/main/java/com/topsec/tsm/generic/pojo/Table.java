package com.topsec.tsm.generic.pojo;

/**
 * 表基本信息封装类
 * Created by yue_tf on 2017/3/26
 */
public class Table {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表中文名
     */
    private String tableCNName;

    /**
     * po类名
     */
    private String className;

    /**
     * po类包路径
     */
    private String packagePath;

    /**
     * po类全限定名
     */
    private String classFullName;

    /**
     * po类简称
     */
    private String classShortName;

    /**
     * 表导航标识
     */
    private String tableMenu;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCNName() {
        return tableCNName;
    }

    public void setTableCNName(String tableCNName) {
        this.tableCNName = tableCNName;
    }

    public String getTableMenu() {
        return tableMenu;
    }

    public void setTableMenu(String tableMenu) {
        this.tableMenu = tableMenu;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public String getClassShortName() {
        return classShortName;
    }

    public void setClassShortName(String classShortName) {
        this.classShortName = classShortName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

}
