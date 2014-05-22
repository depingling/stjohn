
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        TransRebateApprovedViewVector
 * Description:  Container object for TransRebateApprovedView objects
 * Purpose:      Provides container storage for TransRebateApprovedView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>TransRebateApprovedViewVector</code>
 */
public class TransRebateApprovedViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public TransRebateApprovedViewVector () {}

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
      TransRebateApprovedView obj1 = (TransRebateApprovedView)o1;
      TransRebateApprovedView obj2 = (TransRebateApprovedView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
