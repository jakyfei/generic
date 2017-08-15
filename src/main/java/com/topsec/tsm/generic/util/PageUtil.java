package com.topsec.tsm.generic.util;

import java.util.List;

import org.hibernate.Query;

import com.topsec.tsm.generic.core.PageInfo;

/**
 * 分页辅助类
 * Created by yue_tf on 2017/3/28
 */
public class PageUtil {

    @SuppressWarnings("rawtypes")
    public static List getPagedDatas(Query query, Query queryCount, PageInfo pageInfo) {
        if (pageInfo == null)
            return query.list();
        int total = getTotalCount(queryCount);
        pageInfo.setTotal(total);
        pageInfo.setPageSize(pageInfo.getPageSize());
        pageInfo.setPageNo(pageInfo.getPageNo());
        return pagedQuery(query, pageInfo).list();
    }

    private static Query pagedQuery(Query query, PageInfo pageInfo) {
        query.setMaxResults(pageInfo.getPageSize());
        query.setFirstResult((pageInfo.getPageNo() - 1) * pageInfo.getPageSize());
        return query;
    }

    public static int getTotalCount(Query queryCount) {
        return ((Long) queryCount.list().get(0)).intValue();
    }

}
