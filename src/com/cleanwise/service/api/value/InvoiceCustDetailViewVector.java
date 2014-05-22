
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        InvoiceCustDetailViewVector
 * Description:  Container object for InvoiceCustDetailView objects
 * Purpose:      Provides container storage for InvoiceCustDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>InvoiceCustDetailViewVector</code>
 */
public class InvoiceCustDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5547018444332627790L;
    /**
     * Constructor.
     */
    public InvoiceCustDetailViewVector () {}

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
      InvoiceCustDetailView obj1 = (InvoiceCustDetailView)o1;
      InvoiceCustDetailView obj2 = (InvoiceCustDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
