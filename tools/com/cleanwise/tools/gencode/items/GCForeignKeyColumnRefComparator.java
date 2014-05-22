/**
 * Title:        GCForeignKeyColumnRefComparator
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.Comparator;
import com.cleanwise.tools.gencode.utils.GCUtils;


public class GCForeignKeyColumnRefComparator implements Comparator<GCForeignKeyColumnRef> {

    final static public int COMPARE_BY_ALL_FIELDS = 0;
    final static public int COMPARE_BY_KEYSEQ = 1;

    public GCForeignKeyColumnRefComparator(int compareType) {
        _compareType = compareType;
    }

    public GCForeignKeyColumnRefComparator() {
        this(COMPARE_BY_ALL_FIELDS);
    }

    public int getCompareType() {
        return _compareType;
    }

    public void setCompareType(int compareType) {
        _compareType = compareType;
    }

    public int compare(GCForeignKeyColumnRef o1, GCForeignKeyColumnRef o2) {
        if (o1 == null && o2 == null)
            return 0;
        if (o1 == null && o2 != null)
            return -1;
        if (o1 != null && o2 == null)
            return 1;
        int cmpRes = 0;
        if (_compareType == COMPARE_BY_ALL_FIELDS) {
            cmpRes = GCUtils.CompareIgnoreCaseStrings(o1.getFkColumnName(), o2.getFkColumnName());
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        if (_compareType == COMPARE_BY_ALL_FIELDS) {
        cmpRes = GCUtils.CompareIgnoreCaseStrings(o1.getPkColumnName(), o2.getPkColumnName());
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        if (_compareType == COMPARE_BY_ALL_FIELDS || _compareType == COMPARE_BY_KEYSEQ) {
            cmpRes = o1.getKeySeq() - o2.getKeySeq();
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GCForeignKeyColumnRef) {
            return true;
        }
        return false;
    }

    private int _compareType;
}
