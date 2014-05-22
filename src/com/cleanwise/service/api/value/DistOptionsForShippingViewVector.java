
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        DistOptionsForShippingViewVector
 * Description:  Container object for DistOptionsForShippingView objects
 * Purpose:      Provides container storage for DistOptionsForShippingView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>DistOptionsForShippingViewVector</code>
 */
public class DistOptionsForShippingViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1159936605277871629L;
    /**
     * Constructor.
     */
    public DistOptionsForShippingViewVector () {}

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
      DistOptionsForShippingView obj1 = (DistOptionsForShippingView)o1;
      DistOptionsForShippingView obj2 = (DistOptionsForShippingView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
