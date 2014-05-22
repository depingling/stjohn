package com.cleanwise.service.api.cachecos;

import java.util.ArrayList;

public abstract class TableObjectMeta extends ObjectMeta {

    String table;
    ArrayList<TableObjectFieldMeta> fields;

    public TableObjectMeta(String table) {
        this.table = table;
        this.fields = new ArrayList<TableObjectFieldMeta>();
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public ArrayList<com.cleanwise.service.api.cachecos.TableObjectFieldMeta> getFields() {
        return fields;
    }

    public void setFields(ArrayList<TableObjectFieldMeta> fields) {
        this.fields = fields;
    }

    public void addField(com.cleanwise.service.api.cachecos.TableObjectFieldMeta field) {
        fields.add(field);
    }

}
