/**
 * Title:        GCField
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;


public class GCField {

    public GCField() {
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

    public String getSqlType() {
        return _sqlType;
    }

    public void setSqlType(String sqlType) {
        _sqlType = sqlType;
    }

    public void setSize(long size) {
        _size = size;
    }

    public long getSize() {
        return _size;
    }

    public int getDigits() {
        return _digits;
    }

    public void setDigits(int digits) {
        _digits = digits;
    }

    public boolean getIsNullable() {
        return _isNullable;
    }

    public void setIsNullable(boolean isNullable) {
        _isNullable = isNullable;
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

    public String getComments() {
        return _comments;
    }

    public void setComments(String comments) {
        _comments = comments;
    }

    public void setCharSize(long charSize) {
        _charSize = charSize;
    }

    public long getCharSize() {
        return _charSize;
    }

    public String getCharUsed() {
        return _charUsed;
    }

    public void setCharUsed(String charUsed) {
        _charUsed = charUsed;
    }

    private String _name = null;
    private String _type = null;
    private String _sqlType = null;
    private long _size = -1;
    private int _digits = 0;
    private boolean _isNullable = false;
    private String _defaultValue = null;
    private boolean _isPrimaryKey = false;
    private String _comments = null;
	private long _charSize = -1;
	private String _charUsed = null;
}
