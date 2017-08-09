package com.topsec.tsm.generic.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.topsec.tsm.generic.core.PageInfo;

/**
 * Created by yue_tf on 2017/3/26
 */
public interface TableService {

    <T> void importExcelDates2DB(String tableName, String fileName, InputStream inputStream) throws ClassNotFoundException;

    void exportExcelDatasFromDB(String tableName, HttpServletResponse response);

    List<?> getDatas(String tableName, PageInfo pageInfo);

    List<?> searchDatasByInput(String condition, String tableName, PageInfo pageInfo);

    void deleteTableData(String tableName, String id) throws ClassNotFoundException;

    Object getTableDataByDataID(String tableName, int id) throws ClassNotFoundException;

    void addTableData(String className, Object entity);

    void updateTableData(String className, Object entity);


}
