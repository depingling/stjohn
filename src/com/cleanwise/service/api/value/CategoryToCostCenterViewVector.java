
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        CategoryToCostCenterViewVector
 * Description:  Container object for CategoryToCostCenterView objects
 * Purpose:      Provides container storage for CategoryToCostCenterView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CategoryToCostCenterViewVector</code>
 */
public class CategoryToCostCenterViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2793737091284249311L;
    /**
     * Constructor.
     */
    public CategoryToCostCenterViewVector () {}

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
      CategoryToCostCenterView obj1 = (CategoryToCostCenterView)o1;
      CategoryToCostCenterView obj2 = (CategoryToCostCenterView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
