package com.topsec.tsm.generic.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import com.topsec.tsm.generic.util.IOUtil;

/**
 * Created by yue_tf on 2017/3/27
 */
public class Generator {

    /**
     * json配置文件绝对路径
     */
    private static String JSON_DIR = "";

    /**
     * 模板文件绝对路径
     */
    private static String FTL_DIR = "";

    /**
     * 生成文件绝对路径
     */
    private static String GEN_DIR = "";

    /**
     * 展示模板文件内容
     */
    private static String LIST_TEMPLATE = "";

    /**
     * 编辑模板文件内容
     */
    private static String EDIT_TEMPLATE = "";

    /**
     * 展示模板文件名
     */
    private static String LIST_TEM_NAME = "";

    /**
     * 编辑模板文件名
     */
    private static String EDIT_TEM_NAME = "";

    /**
     * 生成展示页面文件后缀
     */
    private static String LIST_SUFFIX_NAME = "";

    /**
     * 生成编辑页面文件后缀
     */
    private static String EDIT_SUFFIX_NAME = "";

    /**
     * 展示页面替换字符串
     */
    private static String REPLACE_LIST_TEM_STR = "";

    /**
     * 编辑页面替换字符串
     */
    private static String REPLACE_EDIT_TEM_STR = "";

    static {
        File file = new File("");
        /*
      项目绝对路径
     */
        String path = file.getAbsolutePath();
        PropertiesParam param = ApplicationConfig.getPropertiesParam();
        FTL_DIR = path + param.getTem_dir_path();
        GEN_DIR = path + param.getGen_dir_path();
        JSON_DIR = path +param.getResources_path() + param.getJson_cfg_home();
        LIST_TEM_NAME = param.getList_tem_name();
        EDIT_TEM_NAME = param.getEdit_tem_name();
        LIST_SUFFIX_NAME = param.getList_suffix_name();
        EDIT_SUFFIX_NAME = param.getEdit_suffix_name();
        REPLACE_LIST_TEM_STR = param.getReplace_list_tem_str();
        REPLACE_EDIT_TEM_STR = param.getReplace_edit_tem_str();
    }

    /**
     * @param replaceStr 替换字符串
     * @param templateName 标识名
     */
    public static void genListTemplate(String replaceStr, String templateName) {
        if (StringUtils.isEmpty(LIST_TEMPLATE)) {
            LIST_TEMPLATE = IOUtil.read(FTL_DIR + LIST_TEM_NAME);
        }
        String ftlPath = GEN_DIR + templateName + LIST_SUFFIX_NAME;
        String content = LIST_TEMPLATE.replace(REPLACE_LIST_TEM_STR, replaceStr);
        IOUtil.write(ftlPath, content);
    }

    /**
     * @param replaceStr 替换字符串
     * @param templateName 标识名
     * @author yue_tf
     */
    public static void genEditTemplate(String replaceStr, String templateName) {
        if (StringUtils.isEmpty(EDIT_TEMPLATE)) {
            EDIT_TEMPLATE = IOUtil.read(FTL_DIR + EDIT_TEM_NAME);
        }
        String ftlPath = GEN_DIR + templateName + EDIT_SUFFIX_NAME;
        String content = EDIT_TEMPLATE.replace(REPLACE_EDIT_TEM_STR, replaceStr);
        IOUtil.write(ftlPath, content);
    }

    /**
     * @return json配置文件信息集合
     */
    public static List<Configure> getConfigureInfo(){
        List<Configure> configureList = new ArrayList<>();
        File dir = new File(JSON_DIR);
        File[] files = dir.listFiles();
        for (File fileX : files) {
            String filePath = JSON_DIR + fileX.getName();
            String context = IOUtil.read(filePath);
            Configure config = JSON.parseObject(context, Configure.class);
            configureList.add(config);
        }
        return configureList;
    }

}
