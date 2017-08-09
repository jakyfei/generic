package com.topsec.tsm.generic.core;

import java.util.List;

import com.topsec.tsm.generic.pojo.Property;
import com.topsec.tsm.generic.pojo.Table;

/**
 * Created by yue_tf on 2017/3/26
 */
public class Configure {

    private Table tableInfo;

    private List<Property> propertys;

    public Table getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(Table tableInfo) {
        this.tableInfo = tableInfo;
    }

    public List<Property> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<Property> propertys) {
        this.propertys = propertys;
    }

}
