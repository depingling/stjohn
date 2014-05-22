
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        CustInvoiceItemViewVector
 * Description:  Container object for CustInvoiceItemView objects
 * Purpose:      Provides container storage for CustInvoiceItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CustInvoiceItemViewVector</code>
 */
public class CustInvoiceItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -7502821486704958019L;
    /**
     * Constructor.
     */
    public CustInvoiceItemViewVector () {}

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
      CustInvoiceItemView obj1 = (CustInvoiceItemView)o1;
      CustInvoiceItemView obj2 = (CustInvoiceItemView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
