
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WorkOrderViewVector
 * Description:  Container object for WorkOrderView objects
 * Purpose:      Provides container storage for WorkOrderView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WorkOrderViewVector</code>
 */
public class WorkOrderViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 6442041815696479612L;
    /**
     * Constructor.
     */
    public WorkOrderViewVector () {}

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
      WorkOrderView obj1 = (WorkOrderView)o1;
      WorkOrderView obj2 = (WorkOrderView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
