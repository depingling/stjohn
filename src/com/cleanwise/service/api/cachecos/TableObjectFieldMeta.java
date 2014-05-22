package com.cleanwise.service.api.cachecos;

public class TableObjectFieldMeta {

    String field;
    Object value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

      public String toString(){
        return ""+field+"|"+value;
    }
}
