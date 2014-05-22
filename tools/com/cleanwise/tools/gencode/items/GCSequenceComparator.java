/**
 * Title:        GCSequenceComparator
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.items;

import java.util.Comparator;
import com.cleanwise.tools.gencode.utils.GCUtils;


public class GCSequenceComparator implements Comparator<GCSequence> {

    public int compare(GCSequence o1, GCSequence o2) {
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
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GCSequence) {
            return true;
        }
        return false;
    }
}
