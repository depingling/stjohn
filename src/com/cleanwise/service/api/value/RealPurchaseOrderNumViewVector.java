
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.PurchaseOrderData;

/**
 * Title:        RealPurchaseOrderNumViewVector
 * Description:  Container object for RealPurchaseOrderNumView objects
 * Purpose:      Provides container storage for RealPurchaseOrderNumView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>RealPurchaseOrderNumViewVector</code>
 */
public class RealPurchaseOrderNumViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -8846585360057178645L;
    /**
     * Constructor.
     */
    public RealPurchaseOrderNumViewVector () {}

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
      RealPurchaseOrderNumView obj1 = (RealPurchaseOrderNumView)o1;
      RealPurchaseOrderNumView obj2 = (RealPurchaseOrderNumView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
