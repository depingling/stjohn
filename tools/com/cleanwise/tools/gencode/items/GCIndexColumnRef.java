/**
 * Title:        GCIndexColumnRef
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;


public class GCIndexColumnRef {

    public GCIndexColumnRef(String columnName, int keySeq) {
        _columnName = columnName;
        _keySeq = keySeq;
        _sortType = "";
    }

    public GCIndexColumnRef(String columnName) {
        this(columnName, 1);
    }

    public GCIndexColumnRef() {
        this(null, 1);
    }

    public String getColumnName() {
        return _columnName;
    }

    public void setColumnName(String columnName) {
        _columnName = columnName == null ? "" : columnName;
    }

    public int getKeySeq() {
        return _keySeq;
    }

    public void setKeySeq(int keySeq) {
        this._keySeq = keySeq;
    }

    public String getSortType() {
        return _sortType;
    }

    public void setSortType(String sortType) {
        _sortType = sortType;
    }

    private String _columnName;
    private String _sortType;
    private int _keySeq;
}
