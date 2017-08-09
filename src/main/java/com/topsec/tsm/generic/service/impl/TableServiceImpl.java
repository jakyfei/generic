package com.topsec.tsm.generic.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.topsec.tsm.generic.core.PageInfo;
import com.topsec.tsm.generic.core.TableFactory;
import com.topsec.tsm.generic.dao.TableDao;
import com.topsec.tsm.generic.pojo.Property;
import com.topsec.tsm.generic.pojo.Table;
import com.topsec.tsm.generic.service.TableService;
import com.topsec.tsm.generic.util.ExcelExport;
import com.topsec.tsm.generic.util.ExcelImport;

/**
 * Created by yue_tf on 2017/3/26
 */
public class TableServiceImpl implements TableService {

    private TableDao tableDao;

    public void setTableDao(TableDao tableDao) {
        this.tableDao = tableDao;
    }

    @Override
    public <T> void importExcelDates2DB(String tableName, String fileName, InputStream inputStream)
            throws ClassNotFoundException {
        Map<String, String> header = TableFactory.getReaderHeader(tableName);
        Table tableInfo = TableFactory.getTableInfo(tableName);
        Class<?> clazz = Class.forName(tableInfo.getClassFullName());
        ExcelImport excelImport = new ExcelImport(fileName);
        List<?> list = excelImport.readExcel(header, clazz, inputStream);
        tableDao.importExcel(tableInfo.getClassName(), tableInfo.getClassFullName(), list);
    }

    @Override
    public void exportExcelDatasFromDB(String tableName, HttpServletResponse response) {
        Map<String, String> writeHeader = TableFactory.getWriteHeader(tableName);
        String className = TableFactory.getTableInfo(tableName).getClassName();
        List<?> list = tableDao.getAll(className);
        ExcelExport export = new ExcelExport(tableName);
        try {
            export.writeExcel(list, writeHeader, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<?> getDatas(String tableName, PageInfo pageInfo) {
        String className = TableFactory.getTableInfo(tableName).getClassName();
        return tableDao.getAll(className, pageInfo);
    }

    @Override
    public List<?> searchDatasByInput(String condition, String tableName, PageInfo pageInfo) {
        String className = TableFactory.getTableInfo(tableName).getClassName();
        List<Property> propertys = TableFactory.getPropertys(tableName);
        return tableDao.searchDatasByInput(condition, className, propertys, pageInfo);
    }

    @Override
    public void deleteTableData(String tableName, String id) throws ClassNotFoundException {
        int idRet = Integer.valueOf(id);
        String className = TableFactory.getTableInfo(tableName).getClassName();
        tableDao.delete(className, idRet);
    }

    @Override
    public Object getTableDataByDataID(String tableName, int id) throws ClassNotFoundException {
        String classFullName = TableFactory.getTableInfo(tableName).getClassFullName();
        return tableDao.getDataById(classFullName, id);
    }

    @Override
    public void addTableData(String className, Object entity) {

        tableDao.save(className, entity);
    }

    @Override
    public void updateTableData(String className, Object entity) {

        tableDao.update(className, entity);
    }

}
