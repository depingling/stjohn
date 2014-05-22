package com.cleanwise.service.api.util;

import java.util.Comparator;

/**
 * This class compares the categories with using the short and long name
 */
public class CategoryComparatorByKey implements Comparator<CategoryKey> {
    
    public CategoryComparatorByKey() {
    }
    
    public int compare(CategoryKey o1, CategoryKey o2) {
        if (o1 == null && o2 == null)
            return 0;
        if (o1 == null && o2 != null)
            return -1;
        if (o1 != null && o2 == null)
            return 1;
        if (!isStrEmpty(o1.GetShortName()) && !isStrEmpty(o2.GetShortName())) {
            int cmpShort = o1.GetShortName().compareToIgnoreCase(o2.GetShortName());
            if (cmpShort == 0) {
                if (isStrEmpty(o1.GetAdminName()) && isStrEmpty(o2.GetAdminName())) {
                    return 0;
                }
                if (!isStrEmpty(o1.GetAdminName()) && isStrEmpty(o2.GetAdminName())) {
                    return 1;
                }
                if (isStrEmpty(o1.GetAdminName()) && !isStrEmpty(o2.GetAdminName())) {
                    return -1;
                }
                if (!isStrEmpty(o1.GetAdminName()) && !isStrEmpty(o2.GetAdminName())) {
                    return o1.GetAdminName().compareToIgnoreCase(o2.GetAdminName());
                }
            }
            else {
                return cmpShort;
            }
        }
        if (!isStrEmpty(o1.GetShortName()) && isStrEmpty(o2.GetShortName())) {
            return 1;
        }
        if (isStrEmpty(o1.GetShortName()) && !isStrEmpty(o2.GetShortName())) {
            return -1;
        }
        if (isStrEmpty(o1.GetShortName()) && isStrEmpty(o2.GetShortName())) {
            if (isStrEmpty(o1.GetAdminName()) && isStrEmpty(o2.GetAdminName())) {
                return 0;
            }
            if (!isStrEmpty(o1.GetAdminName()) && isStrEmpty(o2.GetAdminName())) {
                return 1;
            }
            if (isStrEmpty(o1.GetAdminName()) && !isStrEmpty(o2.GetAdminName())) {
                return -1;
            }
            if (!isStrEmpty(o1.GetAdminName()) && !isStrEmpty(o2.GetAdminName())) {
                return o1.GetAdminName().compareToIgnoreCase(o2.GetAdminName());
            }
        }
        return 0;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof CategoryComparatorByKey) {
            return true;
        }
        return false;
    }
    
    private boolean isStrEmpty(String strValue) {
        if (strValue == null) {
            return true;
        }
        if (strValue.length() == 0) {
            return true;
        }
        return false;
    }
}
