
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        VenInvoiceItemViewVector
 * Description:  Container object for VenInvoiceItemView objects
 * Purpose:      Provides container storage for VenInvoiceItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>VenInvoiceItemViewVector</code>
 */
public class VenInvoiceItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2922970193237498740L;
    /**
     * Constructor.
     */
    public VenInvoiceItemViewVector () {}

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
      VenInvoiceItemView obj1 = (VenInvoiceItemView)o1;
      VenInvoiceItemView obj2 = (VenInvoiceItemView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
