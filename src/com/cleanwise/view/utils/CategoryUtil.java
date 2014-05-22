package com.cleanwise.view.utils;

import java.util.ArrayList;
import com.cleanwise.service.api.util.CategoryKey;
import com.cleanwise.service.api.util.CategoryComparatorByKey;

public class CategoryUtil {

    public final static String NAME_DIVIDER = "-=I=-";
    
    /**
     * Builds a full name of the category with use of a short name 
     * and a of long (admin) name of category.
     */
    public final static String buildFullCategoryName(String categoryName, 
        String adminCategoryName) {        
        String categoryNameEx = (categoryName == null) ? "" : categoryName;
        if (adminCategoryName != null) {
            if (adminCategoryName.length() > 0) {
                categoryNameEx = categoryNameEx + " (" + 
                    adminCategoryName + ")";
            }
        }
        return categoryNameEx;
    }
    
    /**
     * Builds a string which contains a short and long name of a category. 
     * Such string is used in controls on JSP-pages. 
     */
    public final static String encodeCategoryNames(String categoryName, 
        String adminCategoryName) {        
        if (categoryName == null) {
            return categoryName;
        }
        String categoryValue = categoryName;
        if (adminCategoryName != null && adminCategoryName.length() > 0) {
            categoryValue = categoryValue + NAME_DIVIDER + adminCategoryName;
        }
        return categoryValue;
    }
    
    /**
     * Finds in string (which should be constructed with use 'encodeCategoryNames(...)') 
     * a short and long name of a category.
     * The first element of a returned array contains a short name of a category.
     * The second element of a returned array contains a long name of a category.
     */
    public final static String[] decodeCategoryNames(String categoryValue) {
        String[] arrayNames = new String[]{"", ""};
        if (categoryValue == null)
            return arrayNames;
        if (categoryValue.length() == 0)
            return arrayNames;        
        int pos = categoryValue.indexOf(NAME_DIVIDER);
        if (pos >= 0) {
            arrayNames[0] = categoryValue.substring(0, pos);
            arrayNames[1] = categoryValue.substring(pos + NAME_DIVIDER.length(), categoryValue.length());
        }
        else {
            arrayNames[0] = categoryValue;
        }
        return arrayNames;
    }
    
    /**
     * Compare two categories by short and long names
     */
    public final static int compareCategoriesByNames(String shortName1, String longName1, 
        String shortName2, String longName2) {
        CategoryKey key1 = new CategoryKey(shortName1, longName1);
        CategoryKey key2 = new CategoryKey(shortName2, longName2);
        CategoryComparatorByKey categoryComparator = new CategoryComparatorByKey();
        return categoryComparator.compare(key1, key2);
    }
 
    /**
     * Builds a full name of the category by 'Category-value'. 
     * The 'Category-value' are used at building of full category name.
     * The 'Category-value' should be constructed by 'encodeCategoryNames' function.
     */
    public final static String buildFullCategoryNameByValue(String categoryValue) {        
        if (categoryValue == null || categoryValue.length() == 0) {
            return categoryValue;
        }
        String[] arrayNames = decodeCategoryNames(categoryValue);
        if (arrayNames == null || arrayNames.length == 0) {
            return categoryValue;
        }
        String categoryShortName = "";
        String categoryLongName = "";
        if (arrayNames.length > 0) {
            categoryShortName = arrayNames[0];
        }
        if (arrayNames.length > 1) {
            categoryLongName = arrayNames[1];
        }
        return buildFullCategoryName(categoryShortName, categoryLongName);
    }
}
