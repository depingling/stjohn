
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        InvoiceCustInfoViewVector
 * Description:  Container object for InvoiceCustInfoView objects
 * Purpose:      Provides container storage for InvoiceCustInfoView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>InvoiceCustInfoViewVector</code>
 */
public class InvoiceCustInfoViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6087612819231433734L;
    /**
     * Constructor.
     */
    public InvoiceCustInfoViewVector () {}

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
      InvoiceCustInfoView obj1 = (InvoiceCustInfoView)o1;
      InvoiceCustInfoView obj2 = (InvoiceCustInfoView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
