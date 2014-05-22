
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        PurchaseOrderStatusDescDataViewVector
 * Description:  Container object for PurchaseOrderStatusDescDataView objects
 * Purpose:      Provides container storage for PurchaseOrderStatusDescDataView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>PurchaseOrderStatusDescDataViewVector</code>
 */
public class PurchaseOrderStatusDescDataViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -8676936492570166770L;
    /**
     * Constructor.
     */
    public PurchaseOrderStatusDescDataViewVector () {}

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
      PurchaseOrderStatusDescDataView obj1 = (PurchaseOrderStatusDescDataView)o1;
      PurchaseOrderStatusDescDataView obj2 = (PurchaseOrderStatusDescDataView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
