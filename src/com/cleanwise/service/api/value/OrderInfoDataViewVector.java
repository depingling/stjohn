
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        OrderInfoDataViewVector
 * Description:  Container object for OrderInfoDataView objects
 * Purpose:      Provides container storage for OrderInfoDataView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>OrderInfoDataViewVector</code>
 */
public class OrderInfoDataViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 1052499003134112387L;
    /**
     * Constructor.
     */
    public OrderInfoDataViewVector () {}

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
      OrderInfoDataView obj1 = (OrderInfoDataView)o1;
      OrderInfoDataView obj2 = (OrderInfoDataView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
