/**
 * Title:        GCForeignKey
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.ArrayList;
import java.util.Collections;
import java.sql.DatabaseMetaData;


public class GCForeignKey {

    public GCForeignKey() {
        _name = null;
        _pkName = null;
        _pkTableName = null;
        _fkTableName = null;
        _updateRule = DatabaseMetaData.importedKeyNoAction;
        _deleteRule = DatabaseMetaData.importedKeyNoAction;
        _columnsRef = new ArrayList<GCForeignKeyColumnRef>();
    }

    public int getDeleteRule() {
        return _deleteRule;
    }

    public void setDeleteRule(int deleteRule) {
        this._deleteRule = deleteRule;
    }

    public String getFkTable() {
        return _fkTableName;
    }

    public void setFkTable(String fkTableName) {
        this._fkTableName = fkTableName == null ? "" : fkTableName;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name == null ? "" : name;
    }

    public String getPkName() {
        return _pkName;
    }

    public void setPkName(String pkName) {
        this._pkName = pkName == null ? "" : pkName;
    }

    public String getPkTable() {
        return _pkTableName;
    }

    public void setPkTable(String pkTableName) {
        this._pkTableName = pkTableName == null ? "" : pkTableName;
    }

    public int getUpdateRule() {
        return _updateRule;
    }

    public void setUpdateRule(int updateRule) {
        this._updateRule = updateRule;
    }

    public int getColumnRefCount() {
        if (_columnsRef == null) {
            return 0;
        }
        return _columnsRef.size();
    }

    public GCForeignKeyColumnRef getColumnRef(int index) {
        if (_columnsRef == null) {
            return null;
        }
        if (index < 0 && index >= _columnsRef.size()) {
            return null;
        }
        return _columnsRef.get(index);
    }

    public void addColumnRef(GCForeignKeyColumnRef columnRef) {
        if (columnRef == null) {
            return;
        }
        if (_columnsRef == null) {
            _columnsRef = new ArrayList<GCForeignKeyColumnRef>();
        }
        _columnsRef.add(columnRef);
    }

    public void sortColumns(GCForeignKeyColumnRefComparator comparator) {
        Collections.sort(_columnsRef, comparator);
    }

    private String _name;
    private String _pkName;
    private String _pkTableName;
    private String _fkTableName;
    private int _updateRule;
    private int _deleteRule;
    private ArrayList<GCForeignKeyColumnRef> _columnsRef;
}
