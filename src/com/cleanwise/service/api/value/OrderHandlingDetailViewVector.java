
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        OrderHandlingDetailViewVector
 * Description:  Container object for OrderHandlingDetailView objects
 * Purpose:      Provides container storage for OrderHandlingDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>OrderHandlingDetailViewVector</code>
 */
public class OrderHandlingDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -607603985505376665L;
    /**
     * Constructor.
     */
    public OrderHandlingDetailViewVector () {}

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
      OrderHandlingDetailView obj1 = (OrderHandlingDetailView)o1;
      OrderHandlingDetailView obj2 = (OrderHandlingDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
