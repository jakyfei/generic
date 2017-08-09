package com.topsec.tsm.generic.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.topsec.tsm.generic.core.PageInfo;
import com.topsec.tsm.generic.dao.TableDao;
import com.topsec.tsm.generic.pojo.Property;
import com.topsec.tsm.generic.util.PageUtil;

/**
 * Created by yue_tf on 2017/3/26
 */
public class TableDaoImpl extends HibernateDaoSupport implements TableDao {

    protected Class<?> entityClazz;

    public TableDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            this.entityClazz = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            this.entityClazz = null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getAll(String className) {
        String hql = "from " + className;
        Session session = this.getSession();
        Query query = session.createQuery(hql);
        List<T> list = query.list();
        session.close();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getAll(String className, PageInfo pageInfo) {
        String hql = "from " + className;
        Session session = this.getSession();
        Query query = session.createQuery(hql);
        Query queryCount = session.createQuery("select count(*) " + hql);
        List<T> list = PageUtil.getPagedDatas(query, queryCount, pageInfo);
        session.close();
        return list;
    }

    @Override
    public List<?> searchDatasByInput(String condition, final String className, List<Property> propertys,
                                      PageInfo pageInfo) {
        String hql = "from " + className + " where ";
        for (Property p : propertys) {
            hql += p.getEngName() + " like '%" + condition + "%' or ";
        }
        hql = hql.substring(0, hql.lastIndexOf(" or "));
        Session session = this.getSession();
        Query query = session.createQuery(hql);
        Query queryCount = session.createQuery("select count(*) " + hql);
        List<?> list = PageUtil.getPagedDatas(query, queryCount, pageInfo);
        session.close();
        return list;
    }

    @Override
    public void delete(String className, int id) throws ClassNotFoundException {
        String hql = " delete from " + className + " where id = '" + id + "'";
        Session session = this.getSession();
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.close();
    }

    @Override
    public <T> T get(Class<T> entityClass, Serializable id) {

        return (T) getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public void deleteAll(String className) {
        String hql = "delete from " + className;
        Session session = this.getSession();
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.close();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void saveAll(List<?> list) {
        for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) {
            Object entity = localIterator.next();
            this.getHibernateTemplate().save(entity);
        }
    }

    @Override
    public Object getDataById(String classFullName, int id) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(classFullName);
        return this.get(clazz, id);
    }

    @Override
    public void save(String className, Object entity) {

        this.getHibernateTemplate().save(className, entity);
    }

    @Override
    public void update(String className, Object entity) {

        this.getHibernateTemplate().update(className, entity);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void importExcel(String className, String classFullName, List<?> list) {
        if (list != null && list.get(0) != null) {
            Transaction transaction = null;
            Session session = this.getSession();
            try {
                transaction = session.beginTransaction();
                transaction.begin();
                Class<?> clazz = Class.forName(classFullName);
                Object obj = list.get(0);
                Field[] fields = clazz.getDeclaredFields();
                int count = 0;
                int size = fields.length;
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType() == int.class) {
                        count++;
                    } else if (field.get(obj) == null) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == size) {
                    return;
                }
                String hql = "delete from " + className;
                Query query = session.createQuery(hql);
                query.executeUpdate();
                for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) {
                    Object entity = localIterator.next();
                    this.getHibernateTemplate().save(entity);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                session.close();
            }
        }
    }

}
