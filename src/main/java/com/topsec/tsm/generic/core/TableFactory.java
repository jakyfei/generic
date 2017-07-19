package com.topsec.tsm.generic.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.topsec.tsm.generic.pojo.Property;
import com.topsec.tsm.generic.pojo.Table;

/**
 * @author yue_tf
 */
public class TableFactory {

    /**
     * 表名与table实体
     */
    public static Map<String, Table> tables = new LinkedHashMap<String, Table>();

    /**
     * 表名与表内属性实体
     */
    public static Map<String, List<Property>> propertys = new LinkedHashMap<String, List<Property>>();

    /**
     * 表名与页面表头实体
     */
    public static Map<String, List<String>> columnNames = new LinkedHashMap<String, List<String>>();

    /**
     * 表名与读取文件表头
     */
    public static Map<String, Map<String, String>> readHeaders = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 表名与导出文件表头
     */
    public static Map<String, Map<String, String>> writeHeaders = new LinkedHashMap<String, Map<String, String>>();


    public static Map<String, String> getReaderHeader(String tableName) {
        if (readHeaders.containsKey(tableName))
            return readHeaders.get(tableName);
        return null;
    }

    public static Map<String, String> getWriteHeader(String tableName) {
        if (writeHeaders.containsKey(tableName))
            return writeHeaders.get(tableName);
        return null;
    }

    public static List<String> getColumns(String tableName) {
        if (columnNames.containsKey(tableName))
            return columnNames.get(tableName);
        return null;
    }

    public static List<Property> getPropertys(String tableName) {
        if (propertys.containsKey(tableName))
            return propertys.get(tableName);
        return null;
    }

    public static Table getTableInfo(String tableName) {
        if (tables.containsKey(tableName))
            return tables.get(tableName);
        return null;
    }

    public static List<Table> getAllTableInfo() {
        List<Table> tableList = new ArrayList<Table>();
        for (Table tableInfo : tables.values()) {
            tableList.add(tableInfo);
        }
        return tableList;
    }

}
