package com.cleanwise.service.api.cachecos;

public class TableField {

    String name;
    Object value;

    public TableField(String pName, Object pValue) {
        this.name = pName;
        this.value = pValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
