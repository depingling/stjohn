
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        CatalogItemViewVector
 * Description:  Container object for CatalogItemView objects
 * Purpose:      Provides container storage for CatalogItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CatalogItemViewVector</code>
 */
public class CatalogItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -7476335724188615452L;
    /**
     * Constructor.
     */
    public CatalogItemViewVector () {}

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
      CatalogItemView obj1 = (CatalogItemView)o1;
      CatalogItemView obj2 = (CatalogItemView)o2;
      
      if("Name".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getName();
        String i2 = obj2.getName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
