
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        DistInvoiceViewVector
 * Description:  Container object for DistInvoiceView objects
 * Purpose:      Provides container storage for DistInvoiceView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>DistInvoiceViewVector</code>
 */
public class DistInvoiceViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -534552217613333949L;
    /**
     * Constructor.
     */
    public DistInvoiceViewVector () {}

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
      DistInvoiceView obj1 = (DistInvoiceView)o1;
      DistInvoiceView obj2 = (DistInvoiceView)o2;
      
      if("OrderDate".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getOrderDate();
        java.util.Date i2 = obj2.getOrderDate();
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
