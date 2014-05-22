
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        InvoiceCustCritViewVector
 * Description:  Container object for InvoiceCustCritView objects
 * Purpose:      Provides container storage for InvoiceCustCritView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>InvoiceCustCritViewVector</code>
 */
public class InvoiceCustCritViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 3556039555054356680L;
    /**
     * Constructor.
     */
    public InvoiceCustCritViewVector () {}

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
      InvoiceCustCritView obj1 = (InvoiceCustCritView)o1;
      InvoiceCustCritView obj2 = (InvoiceCustCritView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
