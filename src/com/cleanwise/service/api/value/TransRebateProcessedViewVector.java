
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        TransRebateProcessedViewVector
 * Description:  Container object for TransRebateProcessedView objects
 * Purpose:      Provides container storage for TransRebateProcessedView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>TransRebateProcessedViewVector</code>
 */
public class TransRebateProcessedViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public TransRebateProcessedViewVector () {}

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
      TransRebateProcessedView obj1 = (TransRebateProcessedView)o1;
      TransRebateProcessedView obj2 = (TransRebateProcessedView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
