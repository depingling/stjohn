
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        OrderRoutingDescViewVector
 * Description:  Container object for OrderRoutingDescView objects
 * Purpose:      Provides container storage for OrderRoutingDescView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>OrderRoutingDescViewVector</code>
 */
public class OrderRoutingDescViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -8688743984286184182L;
    /**
     * Constructor.
     */
    public OrderRoutingDescViewVector () {}

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
      OrderRoutingDescView obj1 = (OrderRoutingDescView)o1;
      OrderRoutingDescView obj2 = (OrderRoutingDescView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
