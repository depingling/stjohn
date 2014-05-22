
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WorkOrderItemDetailViewVector
 * Description:  Container object for WorkOrderItemDetailView objects
 * Purpose:      Provides container storage for WorkOrderItemDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WorkOrderItemDetailViewVector</code>
 */
public class WorkOrderItemDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7958166776074059767L;
    /**
     * Constructor.
     */
    public WorkOrderItemDetailViewVector () {}

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
      WorkOrderItemDetailView obj1 = (WorkOrderItemDetailView)o1;
      WorkOrderItemDetailView obj2 = (WorkOrderItemDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
