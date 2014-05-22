
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WorkOrderContentViewVector
 * Description:  Container object for WorkOrderContentView objects
 * Purpose:      Provides container storage for WorkOrderContentView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WorkOrderContentViewVector</code>
 */
public class WorkOrderContentViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 4098323075851204645L;
    /**
     * Constructor.
     */
    public WorkOrderContentViewVector () {}

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
      WorkOrderContentView obj1 = (WorkOrderContentView)o1;
      WorkOrderContentView obj2 = (WorkOrderContentView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
