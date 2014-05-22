
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WorkOrderItemWarrantyViewVector
 * Description:  Container object for WorkOrderItemWarrantyView objects
 * Purpose:      Provides container storage for WorkOrderItemWarrantyView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WorkOrderItemWarrantyViewVector</code>
 */
public class WorkOrderItemWarrantyViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 3225120202900307686L;
    /**
     * Constructor.
     */
    public WorkOrderItemWarrantyViewVector () {}

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
      WorkOrderItemWarrantyView obj1 = (WorkOrderItemWarrantyView)o1;
      WorkOrderItemWarrantyView obj2 = (WorkOrderItemWarrantyView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
