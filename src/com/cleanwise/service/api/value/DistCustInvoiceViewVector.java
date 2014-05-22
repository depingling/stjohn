
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        DistCustInvoiceViewVector
 * Description:  Container object for DistCustInvoiceView objects
 * Purpose:      Provides container storage for DistCustInvoiceView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>DistCustInvoiceViewVector</code>
 */
public class DistCustInvoiceViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5165476736614384336L;
    /**
     * Constructor.
     */
    public DistCustInvoiceViewVector () {}

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
      DistCustInvoiceView obj1 = (DistCustInvoiceView)o1;
      DistCustInvoiceView obj2 = (DistCustInvoiceView)o2;
      
      if("OrderDate".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getOrderDate();
        java.util.Date i2 = obj2.getOrderDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CustInvoiceDate".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getCustInvoiceDate();
        java.util.Date i2 = obj2.getCustInvoiceDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("VenInvoiceDate".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getVenInvoiceDate();
        java.util.Date i2 = obj2.getVenInvoiceDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
