
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        AllocatedCategoryViewVector
 * Description:  Container object for AllocatedCategoryView objects
 * Purpose:      Provides container storage for AllocatedCategoryView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AllocatedCategoryViewVector</code>
 */
public class AllocatedCategoryViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -490598583201649522L;
    /**
     * Constructor.
     */
    public AllocatedCategoryViewVector () {}

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
      AllocatedCategoryView obj1 = (AllocatedCategoryView)o1;
      AllocatedCategoryView obj2 = (AllocatedCategoryView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
