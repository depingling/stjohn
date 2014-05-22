
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        OrderHandlingItemViewVector
 * Description:  Container object for OrderHandlingItemView objects
 * Purpose:      Provides container storage for OrderHandlingItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>OrderHandlingItemViewVector</code>
 */
public class OrderHandlingItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6986003082023468622L;
    /**
     * Constructor.
     */
    public OrderHandlingItemViewVector () {}

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
      OrderHandlingItemView obj1 = (OrderHandlingItemView)o1;
      OrderHandlingItemView obj2 = (OrderHandlingItemView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
