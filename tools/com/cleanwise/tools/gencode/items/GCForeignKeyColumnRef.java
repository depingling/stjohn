/**
 * Title:        GCForeignKeyColumnRef
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;


public class GCForeignKeyColumnRef {

    public GCForeignKeyColumnRef(String fkColumnName, String pkColumnName, int keySeq) {
        _fkColumnName = fkColumnName;
        _pkColumnName = pkColumnName;
        _keySeq = keySeq;
    }

    public GCForeignKeyColumnRef(String fkColumnName, String pkColumnName) {
        this(fkColumnName, pkColumnName, 1);
    }

    public GCForeignKeyColumnRef() {
        this(null, null, 1);
    }

    public String getFkColumnName() {
        return _fkColumnName;
    }

    public void setFkColumnName(String fkColumnName) {
        _fkColumnName = fkColumnName == null ? "" : fkColumnName;
    }

    public String getPkColumnName() {
        return _pkColumnName;
    }

    public void setPkColumnName(String pkColumnName) {
        _pkColumnName = pkColumnName == null ? "" : pkColumnName;
    }

    public int getKeySeq() {
        return _keySeq;
    }

    public void setKeySeq(int keySeq) {
        this._keySeq = keySeq;
    }

    private String _fkColumnName;
    private String _pkColumnName;
    private int _keySeq;
}
