package com.cleanwise.service.api.util;

import java.util.Comparator;
import com.cleanwise.service.api.value.ShoppingRestrictionsView;

/**
 * This class compares the categories with using the short and long name
 */
public class ShoppingRestrictionsViewComparatorBySku implements Comparator<ShoppingRestrictionsView> {

    public ShoppingRestrictionsViewComparatorBySku() {
    }
    
    public int compare(ShoppingRestrictionsView o1, ShoppingRestrictionsView o2) {
        if (o1 == null && o2 == null)
            return 0;
        if (o1 == null && o2 != null)
            return -1;
        if (o1 != null && o2 == null)
            return 1;
        return o1.getItemSkuNum().compareTo(o2.getItemSkuNum());
    }

    public boolean equals(Object obj) {
        if (obj instanceof ShoppingRestrictionsView) {
            return true;
        }
        return false;
    }
}
