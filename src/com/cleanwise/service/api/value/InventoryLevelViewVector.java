
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        InventoryLevelViewVector
 * Description:  Container object for InventoryLevelView objects
 * Purpose:      Provides container storage for InventoryLevelView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>InventoryLevelViewVector</code>
 */
public class InventoryLevelViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public InventoryLevelViewVector () {}

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
      InventoryLevelView obj1 = (InventoryLevelView)o1;
      InventoryLevelView obj2 = (InventoryLevelView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
