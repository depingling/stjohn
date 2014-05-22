
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ReplacedOrderViewVector
 * Description:  Container object for ReplacedOrderView objects
 * Purpose:      Provides container storage for ReplacedOrderView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ReplacedOrderViewVector</code>
 */
public class ReplacedOrderViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -5491863269418620813L;
    /**
     * Constructor.
     */
    public ReplacedOrderViewVector () {}

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
      ReplacedOrderView obj1 = (ReplacedOrderView)o1;
      ReplacedOrderView obj2 = (ReplacedOrderView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
