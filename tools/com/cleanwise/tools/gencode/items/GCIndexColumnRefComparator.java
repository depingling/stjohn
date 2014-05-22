/**
 * Title:        GCIndexColumnRefComparator
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.Comparator;
import com.cleanwise.tools.gencode.utils.GCUtils;


public class GCIndexColumnRefComparator implements Comparator<GCIndexColumnRef> {

    final static public int COMPARE_BY_ALL_FIELDS = 0;
    final static public int COMPARE_BY_NAME = 1;
    final static public int COMPARE_BY_KEYSEQ = 2;

    public GCIndexColumnRefComparator(int compareType) {
        _compareType = compareType;
    }

    public GCIndexColumnRefComparator() {
        this(COMPARE_BY_ALL_FIELDS);
    }

    public int getCompareType() {
        return _compareType;
    }

    public void setCompareType(int compareType) {
        _compareType = compareType;
    }

    public int compare(GCIndexColumnRef o1, GCIndexColumnRef o2) {
        if (o1 == null && o2 == null)
            return 0;
        if (o1 == null && o2 != null)
            return -1;
        if (o1 != null && o2 == null)
            return 1;
        int cmpRes = 0;
        if (_compareType == COMPARE_BY_ALL_FIELDS || _compareType == COMPARE_BY_NAME) {
            cmpRes = GCUtils.CompareIgnoreCaseStrings(o1.getColumnName(), o2.getColumnName());
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
        if (obj instanceof GCIndexColumnRef) {
            return true;
        }
        return false;
    }

    private int _compareType;
}
