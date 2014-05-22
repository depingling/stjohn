
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        CatalogCategoryViewVector
 * Description:  Container object for CatalogCategoryView objects
 * Purpose:      Provides container storage for CatalogCategoryView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CatalogCategoryViewVector</code>
 */
public class CatalogCategoryViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7959240293446859295L;
    /**
     * Constructor.
     */
    public CatalogCategoryViewVector () {}

    String _sortField = "";
    boolean _ascFl = true;
    /**
     * Sort
     */
    public void sort(String pFieldName) {
       sort(pFieldName,true);     
    }

    public void sort(String pFieldName, boolean pAscFl) {
       _sortField = pFieldName;
       _ascFl = pAscFl;       
       Collections.sort(this,this);
    }

    /*
    *
    */
    public int compare(Object o1, Object o2)
    {
      int retcode = -1;
      CatalogCategoryView obj1 = (CatalogCategoryView)o1;
      CatalogCategoryView obj2 = (CatalogCategoryView)o2;
      
      if("CategoryId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCategoryId();
        int i2 = obj2.getCategoryId();
        retcode = i1-i2;
      }
      
      if("CategoryName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCategoryName();
        String i2 = obj2.getCategoryName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCatalogId();
        int i2 = obj2.getCatalogId();
        retcode = i1-i2;
      }
      
      if("CatalogName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogName();
        String i2 = obj2.getCatalogName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("MajorCategoryId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getMajorCategoryId();
        int i2 = obj2.getMajorCategoryId();
        retcode = i1-i2;
      }
      
      if("MajorCategoryName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMajorCategoryName();
        String i2 = obj2.getMajorCategoryName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
