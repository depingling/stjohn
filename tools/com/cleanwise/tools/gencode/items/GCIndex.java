/**
 * Title:        GCIndex
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.ArrayList;
import java.util.Collections;

public class GCIndex {

    public GCIndex() {
        _name = null;
        _table = null;
        _columns = null;
        _isUnique = false;
        _type = 0;
    }

    public int getColumnRefCount() {
        if (_columns == null) {
            return 0;
        }
        return _columns.size();
    }

    public void addColumnRef(GCIndexColumnRef columnRef) {
        if (columnRef == null) {
            return;
        }
        if (columnRef.getColumnName() == null) {
            return;
        }
        if (_columns == null) {
            _columns = new ArrayList<GCIndexColumnRef>();
        }
        _columns.add(columnRef);
    }

    public GCIndexColumnRef getColumnRef(int indexColumn) {
        if (_columns == null) {
            return null;
        }
        if (indexColumn < 0 && indexColumn >= _columns.size()) {
            return null;
        }
        return (GCIndexColumnRef)_columns.get(indexColumn);
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name == null ? "" : name;
    }

    public String getTable() {
        return _table;
    }

    public void setTable(String table) {
        _table = table;
    }

    public boolean getIsUnique() {
        return _isUnique;
    }

    public void setIsUnique(boolean isUnique) {
        _isUnique = isUnique;
    }

    public int getType() {
        return _type;
    }

    public void setType(int type) {
        _type = type;
    }

    public boolean searchColumnRef(String columnName) {
        if (columnName == null) {
            return false;
        }
        if (_columns == null) {
            return false;
        }
        for (int i = 0; i < _columns.size(); ++i) {
            GCIndexColumnRef columnRef = _columns.get(i);
            if (columnRef.getColumnName().equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchColumnRef(String columnName, int keySeq) {
        if (columnName == null) {
            return false;
        }
        if (_columns == null) {
            return false;
        }
        for (int i = 0; i < _columns.size(); ++i) {
            GCIndexColumnRef columnRef = _columns.get(i);
            if (columnRef.getKeySeq() == keySeq) {
                if (columnRef.getColumnName().equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void sortColumns(GCIndexColumnRefComparator comparator) {
        Collections.sort(_columns, comparator);
    }

    private String _name;
    private String _table;
    private ArrayList<GCIndexColumnRef> _columns;
    private boolean _isUnique;
    private int _type;
}
