
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        OrderHandlingViewVector
 * Description:  Container object for OrderHandlingView objects
 * Purpose:      Provides container storage for OrderHandlingView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>OrderHandlingViewVector</code>
 */
public class OrderHandlingViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 3318663044982333871L;
    /**
     * Constructor.
     */
    public OrderHandlingViewVector () {}

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
      OrderHandlingView obj1 = (OrderHandlingView)o1;
      OrderHandlingView obj2 = (OrderHandlingView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
