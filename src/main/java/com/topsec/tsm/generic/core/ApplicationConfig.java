package com.topsec.tsm.generic.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.topsec.tsm.generic.pojo.Property;
import com.topsec.tsm.generic.pojo.Table;
import com.topsec.tsm.generic.util.IOUtil;

public class ApplicationConfig {

    /**
     * 全局配置文件resources地址
     */
    private static final String PROPERTIES_PATH = "/generic/table.properties";

    /**
     * resources文件根目录路径
     */
    private static final String RESOURCES_HOME = "/WEB-INF/classes/";

    /**
     * 项目resources目录路径
     */
    private static final String RESOURCES_PATH = "resources_path";
    /**
     * 实体及表信息json配置文件目录
     */
    private static final String JSON_CFG_HOME = "json_cfg_home";

    /**
     * 配置文件相对路径
     */
    private static final String TEM_DIR_PATH = "tem_dir_path";

    /**
     * 生成文件相对路径
     */
    private static final String GEN_DIR_PATH = "gen_dir_path";

    /**
     * 展示模板文件名
     */
    private static final String LIST_TEM_NAME = "list_tem_name";

    /**
     * 编辑模板文件名
     */
    private static final String EDIT_TEM_NAME = "edit_tem_name";

    /**
     * 生成展示页面文件后缀
     */
    private static final String LIST_SUFFIX_NAME = "list_suffix_name";

    /**
     * 生成编辑页面文件后缀
     */
    private static final String EDIT_SUFFIX_NAME = "edit_suffix_name";

    /**
     * 展示页面替换字符串
     */
    private static final String REPLACE_LIST_TEM_STR = "replace_list_tem_str";

    /**
     * 编辑页面替换字符串
     */
    private static final String REPLACE_EDIT_TEM_STR = "replace_edit_tem_str";

    public void init() {

        try {
            initTableFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initTableFactory() throws IOException {
        Properties properties = new Properties();
        InputStream in = ApplicationConfig.class.getResourceAsStream(PROPERTIES_PATH);
        properties.load(in);
        in.close();
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        String pathHome = wac.getServletContext().getRealPath("/") + RESOURCES_HOME;
        String json_cfg_home = properties.getProperty(JSON_CFG_HOME);
        File dir = new File(pathHome + json_cfg_home);
        File[] files = dir.listFiles();
        for (File fileX : files) {
            String filePath = json_cfg_home + fileX.getName();
            String context = IOUtil.readConfFile(filePath);
            Configure config = JSON.parseObject(context, Configure.class);
            genTableFactory(config);
        }
    }

    private void genTableFactory(Configure config) {
        Table table = config.getTableInfo();
        String tableName = table.getTableName();
        List<Property> propertys = config.getPropertys();
        List<String> columnNames = new ArrayList<String>();
        for (Property p : propertys) {
            columnNames.add(p.getCnName());
        }
        Map<String, String> readHeaders = new LinkedHashMap<String, String>();
        for (Property p : propertys) {
            readHeaders.put(p.getCnName(), p.getEngName());
        }
        Map<String, String> writeHeaders = new LinkedHashMap<String, String>();
        for (Property p : propertys) {
            writeHeaders.put(p.getEngName().toLowerCase(), p.getCnName());
        }
        TableFactory.tables.put(tableName, table);
        TableFactory.propertys.put(tableName, propertys);
        TableFactory.columnNames.put(tableName, columnNames);
        TableFactory.readHeaders.put(tableName, readHeaders);
        TableFactory.writeHeaders.put(tableName, writeHeaders);
    }


    public static PropertiesParam getPropertiesParam() {
        Properties properties = new Properties();
        InputStream in = ApplicationConfig.class.getResourceAsStream(PROPERTIES_PATH);
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        PropertiesParam param = new PropertiesParam();
        param.setResources_path(properties.getProperty(RESOURCES_PATH));
        param.setJson_cfg_home(properties.getProperty(JSON_CFG_HOME));
        param.setTem_dir_path(properties.getProperty(TEM_DIR_PATH));
        param.setGen_dir_path(properties.getProperty(GEN_DIR_PATH));
        param.setList_tem_name(properties.getProperty(LIST_TEM_NAME));
        param.setEdit_tem_name(properties.getProperty(EDIT_TEM_NAME));
        param.setList_suffix_name(properties.getProperty(LIST_SUFFIX_NAME));
        param.setEdit_suffix_name(properties.getProperty(EDIT_SUFFIX_NAME));
        param.setReplace_list_tem_str(properties.getProperty(REPLACE_LIST_TEM_STR));
        param.setReplace_edit_tem_str(properties.getProperty(REPLACE_EDIT_TEM_STR));

        return param;
    }

}
