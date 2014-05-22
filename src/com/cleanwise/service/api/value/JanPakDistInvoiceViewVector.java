
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        JanPakDistInvoiceViewVector
 * Description:  Container object for JanPakDistInvoiceView objects
 * Purpose:      Provides container storage for JanPakDistInvoiceView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>JanPakDistInvoiceViewVector</code>
 */
public class JanPakDistInvoiceViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public JanPakDistInvoiceViewVector () {}

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
      JanPakDistInvoiceView obj1 = (JanPakDistInvoiceView)o1;
      JanPakDistInvoiceView obj2 = (JanPakDistInvoiceView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
