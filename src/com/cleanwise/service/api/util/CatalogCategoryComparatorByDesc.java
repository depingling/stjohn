package com.cleanwise.service.api.util;

import java.util.Comparator;
import com.cleanwise.service.api.value.CatalogCategoryData;

/**
 * This class compares the categories with using the short and long name
 */
public class CatalogCategoryComparatorByDesc implements Comparator<CatalogCategoryData> {

    public CatalogCategoryComparatorByDesc() {
    }
    
    public int compare(CatalogCategoryData o1, CatalogCategoryData o2) {
        if (o1 == null && o2 == null)
            return 0;
        if (o1 == null && o2 != null)
            return -1;
        if (o1 != null && o2 == null)
            return 1;
        CategoryKey key1 = new CategoryKey(o1.getCatalogCategoryShortDesc(), o1.getCatalogCategoryLongDesc());
        CategoryKey key2 = new CategoryKey(o2.getCatalogCategoryShortDesc(), o2.getCatalogCategoryLongDesc());
        CategoryComparatorByKey categoryComparator = new CategoryComparatorByKey();
        return categoryComparator.compare(key1, key2);
    }

    public boolean equals(Object obj) {
        if (obj instanceof CatalogCategoryComparatorByDesc) {
            return true;
        }
        return false;
    }
}
