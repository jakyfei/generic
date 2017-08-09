package com.topsec.tsm.generic.dao;

import java.io.Serializable;
import java.util.List;

import com.topsec.tsm.generic.core.PageInfo;
import com.topsec.tsm.generic.pojo.Property;

/**
 * Created by yue_tf on 2017/3/26
 */
public interface TableDao {

    <T> List<T> getAll(String className);

    <T> List<T> getAll(String className, PageInfo pageInfo);

    List<?> searchDatasByInput(String condition, String className, List<Property> propertys, PageInfo pageInfo);

    void delete(String className, int id) throws ClassNotFoundException;

    void deleteAll(String className);

    <T> T get(Class<T> entityClass, Serializable id);

    void saveAll(List<?> list);

    Object getDataById(String classFullName, int id) throws ClassNotFoundException;

    void save(String className, Object entity);

    void update(String className, Object entity);

    void importExcel(String className, String classFullName, List<?> list);

}
