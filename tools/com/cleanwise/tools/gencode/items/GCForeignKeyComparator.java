/**
 * Title:        GCForeignKeyComparator
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.Comparator;
import com.cleanwise.tools.gencode.utils.GCUtils;


public class GCForeignKeyComparator implements Comparator<GCForeignKey> {

    final static public int COMPARE_BY_ALL_FIELDS = 0;
    final static public int COMPARE_BY_NAME = 1;
    final static public int COMPARE_BY_NAMES_AND_COLUMNS = 2;

    public int compare(GCForeignKey o1, GCForeignKey o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null && o2 != null) {
            return -1;
        }
        if (o1 != null && o2 == null) {
            return 1;
        }
        int cmpRes = GCUtils.CompareIgnoreCaseStrings(o1.getName(), o2.getName());
        if (cmpRes != 0) {
            return cmpRes;
        }
        cmpRes = GCUtils.CompareIgnoreCaseStrings(o1.getFkTable(), o2.getFkTable());
        if (cmpRes != 0) {
            return cmpRes;
        }
        cmpRes = GCUtils.CompareIgnoreCaseStrings(o1.getPkTable(), o2.getPkTable());
        if (cmpRes != 0) {
            return cmpRes;
        }
        cmpRes = o1.getColumnRefCount() - o2.getColumnRefCount();
        if (cmpRes != 0) {
            return cmpRes;
        }
        GCForeignKeyColumnRefComparator columnRefComparator = new GCForeignKeyColumnRefComparator();
        o1.sortColumns(new GCForeignKeyColumnRefComparator(GCForeignKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
        o2.sortColumns(new GCForeignKeyColumnRefComparator(GCForeignKeyColumnRefComparator.COMPARE_BY_KEYSEQ));
        for (int i = 0; i < o1.getColumnRefCount(); ++i) {
            cmpRes = columnRefComparator.compare(o1.getColumnRef(i), o2.getColumnRef(i));
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        //cmpRes = o1.getUpdateRule() - o1.getUpdateRule();
        //if (cmpRes != 0) {
        //    return cmpRes;
        //}
        //cmpRes = o1.getDeleteRule() - o1.getDeleteRule();
        //if (cmpRes != 0) {
        //    return cmpRes;
        //}
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GCForeignKey) {
            return true;
        }
        return false;
    }

    private int _compareType;
}
