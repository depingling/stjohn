
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        BuyListViewVector
 * Description:  Container object for BuyListView objects
 * Purpose:      Provides container storage for BuyListView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>BuyListViewVector</code>
 */
public class BuyListViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public BuyListViewVector () {}

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
      BuyListView obj1 = (BuyListView)o1;
      BuyListView obj2 = (BuyListView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
