package com.topsec.tsm.generic.pojo;

/**
 * @author yue_tf
 */
public class Property {

    /**
     * 属性名
     */
    private String engName;

    /**
     * 属性中文释义
     */
    private String cnName;

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public Property() {

    }

    /**
     * @param engName 属性名
     * @param cnName 属性释义
     */
    public Property(String engName, String cnName) {
        this.engName = engName;
        this.cnName = cnName;
    }

}
