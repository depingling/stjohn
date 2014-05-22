
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WorkOrderAssetViewVector
 * Description:  Container object for WorkOrderAssetView objects
 * Purpose:      Provides container storage for WorkOrderAssetView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WorkOrderAssetViewVector</code>
 */
public class WorkOrderAssetViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1142984695354338671L;
    /**
     * Constructor.
     */
    public WorkOrderAssetViewVector () {}

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
      WorkOrderAssetView obj1 = (WorkOrderAssetView)o1;
      WorkOrderAssetView obj2 = (WorkOrderAssetView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
