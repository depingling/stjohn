
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.*;

/**
 * Title:        InvoiceCustViewVector
 * Description:  Container object for InvoiceCustView objects
 * Purpose:      Provides container storage for InvoiceCustView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>InvoiceCustViewVector</code>
 */
public class InvoiceCustViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6606680776405357305L;
    /**
     * Constructor.
     */
    public InvoiceCustViewVector () {}

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
      InvoiceCustView obj1 = (InvoiceCustView)o1;
      InvoiceCustView obj2 = (InvoiceCustView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
