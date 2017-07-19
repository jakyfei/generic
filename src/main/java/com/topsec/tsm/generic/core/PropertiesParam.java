package com.topsec.tsm.generic.core;

public class PropertiesParam {

    /**
     * 项目resources目录路径
     */
    private String resources_path = "";

    /**
     * 实体及表信息json配置文件目录
     */
    private String json_cfg_home = "";

    /**
     * 模板文件相对路径
     */
    private String tem_dir_path = "";

    /**
     * 生成文件相对路径
     */
    private String gen_dir_path = "";

    /**
     * 展示模板文件名
     */
    private String list_tem_name = "";

    /**
     * 编辑模板文件名
     */
    private String edit_tem_name = "";

    /**
     * 查看模板文件名
     */
    private String watch_tem_name = "";

    /**
     * 生成展示页面文件后缀
     */
    private String list_suffix_name = "";

    /**
     * 生成编辑页面文件后缀
     */
    private String edit_suffix_name = "";

    /**
     * 展示页面替换字符串
     */
    private String replace_list_tem_str = "";

    /**
     * 编辑页面替换字符串
     */
    private String replace_edit_tem_str = "";

    public String getResources_path() {
        return resources_path;
    }

    public void setResources_path(String resources_path) {
        this.resources_path = resources_path;
    }

    public String getJson_cfg_home() {
        return json_cfg_home;
    }

    public void setJson_cfg_home(String json_cfg_home) {
        this.json_cfg_home = json_cfg_home;
    }

    public String getTem_dir_path() {
        return tem_dir_path;
    }

    public void setTem_dir_path(String tem_dir_path) {
        this.tem_dir_path = tem_dir_path;
    }

    public String getGen_dir_path() {
        return gen_dir_path;
    }

    public void setGen_dir_path(String gen_dir_path) {
        this.gen_dir_path = gen_dir_path;
    }

    public String getList_tem_name() {
        return list_tem_name;
    }

    public void setList_tem_name(String list_tem_name) {
        this.list_tem_name = list_tem_name;
    }

    public String getEdit_tem_name() {
        return edit_tem_name;
    }

    public void setEdit_tem_name(String edit_tem_name) {
        this.edit_tem_name = edit_tem_name;
    }

    public String getWatch_tem_name() {
        return watch_tem_name;
    }

    public void setWatch_tem_name(String watch_tem_name) {
        this.watch_tem_name = watch_tem_name;
    }

    public String getList_suffix_name() {
        return list_suffix_name;
    }

    public void setList_suffix_name(String list_suffix_name) {
        this.list_suffix_name = list_suffix_name;
    }

    public String getEdit_suffix_name() {
        return edit_suffix_name;
    }

    public void setEdit_suffix_name(String edit_suffix_name) {
        this.edit_suffix_name = edit_suffix_name;
    }

    public String getReplace_list_tem_str() {
        return replace_list_tem_str;
    }

    public void setReplace_list_tem_str(String replace_list_tem_str) {
        this.replace_list_tem_str = replace_list_tem_str;
    }

    public String getReplace_edit_tem_str() {
        return replace_edit_tem_str;
    }

    public void setReplace_edit_tem_str(String replace_edit_tem_str) {
        this.replace_edit_tem_str = replace_edit_tem_str;
    }
}
