
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        JciCustInvoiceViewVector
 * Description:  Container object for JciCustInvoiceView objects
 * Purpose:      Provides container storage for JciCustInvoiceView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>JciCustInvoiceViewVector</code>
 */
public class JciCustInvoiceViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 656452029325100122L;
    /**
     * Constructor.
     */
    public JciCustInvoiceViewVector () {}

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
      JciCustInvoiceView obj1 = (JciCustInvoiceView)o1;
      JciCustInvoiceView obj2 = (JciCustInvoiceView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
