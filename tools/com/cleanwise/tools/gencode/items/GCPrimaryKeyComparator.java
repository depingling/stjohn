/**
 * Title:        GCPrimaryKeyComparator
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.Comparator;
import com.cleanwise.tools.gencode.utils.GCUtils;


public class GCPrimaryKeyComparator implements Comparator<GCPrimaryKey> {

    final static public int COMPARE_BY_ALL_FIELDS = 0;
    final static public int COMPARE_BY_NAME = 1;
    final static public int COMPARE_BY_COLUMNS = 2;

    public GCPrimaryKeyComparator(int compareType) {
        _compareType = compareType;
    }

    public GCPrimaryKeyComparator() {
        this(COMPARE_BY_ALL_FIELDS);
    }

    public int getCompareType() {
        return _compareType;
    }

    public void setCompareType(int compareType) {
        _compareType = compareType;
    }

    public int compare(GCPrimaryKey o1, GCPrimaryKey o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null && o2 != null) {
            return -1;
        }
        if (o1 != null && o2 == null) {
            return 1;
        }
        int cmpRes = 0;
        if (_compareType == COMPARE_BY_ALL_FIELDS || _compareType == COMPARE_BY_NAME) {
            cmpRes = GCUtils.CompareIgnoreCaseStrings(o1.getName(), o2.getName());
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        if (_compareType == COMPARE_BY_ALL_FIELDS || _compareType == COMPARE_BY_COLUMNS) {
            if (o1.getColumnRefCount() != o2.getColumnRefCount()) {
                return (o1.getColumnRefCount() - o2.getColumnRefCount());
            }
        }
        if (_compareType == COMPARE_BY_ALL_FIELDS || _compareType == COMPARE_BY_COLUMNS) {
            GCPrimaryKeyColumnRefComparator comparator = new GCPrimaryKeyColumnRefComparator();
            o1.sortColumns(new GCPrimaryKeyColumnRefComparator(GCPrimaryKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
            o2.sortColumns(new GCPrimaryKeyColumnRefComparator(GCPrimaryKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
            for (int i = 0; i < o1.getColumnRefCount(); ++i) {
                cmpRes = comparator.compare(o1.getColumnRef(i), o2.getColumnRef(i));
                if (cmpRes != 0) {
                    return cmpRes;
                }
            }
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GCPrimaryKey) {
            return true;
        }
        return false;
    }

    private int _compareType;
}
