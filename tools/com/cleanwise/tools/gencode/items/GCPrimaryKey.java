/**
 * Title:        GCPrimaryKey
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.ArrayList;
import java.util.Collections;

public class GCPrimaryKey {

    public GCPrimaryKey() {
        _columns = null;
        _name = null;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name == null ? "" : name;
    }

    public int getColumnRefCount() {
        if (_columns == null) {
            return 0;
        }
        return _columns.size();
    }

    public void addColumnRef(GCPrimaryKeyColumnRef columnRef) {
        if (columnRef == null) {
            return;
        }
        if (columnRef.getColumnName() == null) {
            return;
        }
        if (_columns == null) {
            _columns = new ArrayList<GCPrimaryKeyColumnRef>();
        }
        _columns.add(columnRef);
    }

    public GCPrimaryKeyColumnRef getColumnRef(int indexColumn) {
        if (_columns == null) {
            return null;
        }
        if (indexColumn < 0 && indexColumn >= _columns.size()) {
            return null;
        }
        return (GCPrimaryKeyColumnRef)_columns.get(indexColumn);
    }

    public void sortColumns(GCPrimaryKeyColumnRefComparator comparator) {
        Collections.sort(_columns, comparator);
    }

    private String _name;
    private ArrayList<GCPrimaryKeyColumnRef> _columns;
}
