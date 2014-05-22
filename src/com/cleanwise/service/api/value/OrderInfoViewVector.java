
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        OrderInfoViewVector
 * Description:  Container object for OrderInfoView objects
 * Purpose:      Provides container storage for OrderInfoView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>OrderInfoViewVector</code>
 */
public class OrderInfoViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6197233566091643742L;
    /**
     * Constructor.
     */
    public OrderInfoViewVector () {}

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
      OrderInfoView obj1 = (OrderInfoView)o1;
      OrderInfoView obj2 = (OrderInfoView)o2;
      
      if("OrderId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getOrderId();
        int i2 = obj2.getOrderId();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
