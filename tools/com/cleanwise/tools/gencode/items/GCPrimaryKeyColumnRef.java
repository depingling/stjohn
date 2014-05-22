/**
 * Title:        GCPrimaryKeyColumnRef
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;


public class GCPrimaryKeyColumnRef {

    public GCPrimaryKeyColumnRef(String columnName, int keySeq) {
        _columnName = columnName;
        _keySeq = keySeq;
    }

    public GCPrimaryKeyColumnRef(String columnName) {
        this(columnName, 1);
    }

    public GCPrimaryKeyColumnRef() {
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

    private String _columnName;
    private int _keySeq;
}
