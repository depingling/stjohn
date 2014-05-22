package com.cleanwise.service.api.util;

/**
 * Title:        TableColumnInfo
 * Description:  
 * Purpose:      
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendwise, Inc.
 */


public class TableColumnInfo {

    private String _name = null;
    private String _type = null;
    private long _size = -1;
    private int _precision = 0;
    private int _scale = 0;
    private int _charLength = 0;
    public String _charSet = null;
    public String _charUsed = null;
    private String _defaultValue = null;
    private boolean _isNullable = false;
    private boolean _isPrimaryKey = false;

    public TableColumnInfo() {
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getType() {
        return _type;
    }

    public void setType(String name) {
        _type = name;
    }

    public void setSize(long size) {
        _size = size;
    }

    public long getSize() {
        return _size;
    }

    public int getPrecision() {
        return _precision;
    }

    public void setPrecision(int precision) {
        _precision = precision;
    }

    public int getScale() {
        return _scale;
    }

    public void setScale(int scale) {
        _scale = scale;
    }

    public int getCharLength() {
        return _charLength;
    }

    public void setCharLength(int charLength) {
        _charLength = charLength;
    }

    public boolean getIsNullable() {
        return _isNullable;
    }

    public void setIsNullable(boolean isNullable) {
        _isNullable = isNullable;
    }

    public String getCharSet() {
        return _charSet;
    }

    public void setCharSet(String charSet) {
        _charSet = charSet;
    }

    public String getCharUsed() {
        return _charUsed;
    }

    public void setCharUsed(String charUsed) {
        _charSet = charUsed;
    }

    public String getDefaultValue() {
        return _defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        _defaultValue = defaultValue;
    }

    public boolean getIsPrimaryKey() {
        return _isPrimaryKey;
    }

    public void setIsPrimaryKey(boolean isPrimaryKey) {
        _isPrimaryKey = isPrimaryKey;
    }

    public String toString() {
        String desc = "";
        desc +=
            "[" +
            "name: " + _name +
            ",type: " + _type +
            ",size: " + _size +
            ",precision: " + _precision +
            ",scale: " + _scale +
            ",charLength: " + _charLength +
            ",charSet: " + _charSet +
            ",charUsed: " + _charUsed +
            ",isNullable: " + _isNullable +
            ",defaultValue: " + _defaultValue +
            ",isPrimaryKey: " + _isPrimaryKey +
            "]";
        return desc;
    }

}
