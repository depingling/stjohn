
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WarrantyContentViewVector
 * Description:  Container object for WarrantyContentView objects
 * Purpose:      Provides container storage for WarrantyContentView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WarrantyContentViewVector</code>
 */
public class WarrantyContentViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -4102541715419345488L;
    /**
     * Constructor.
     */
    public WarrantyContentViewVector () {}

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
      WarrantyContentView obj1 = (WarrantyContentView)o1;
      WarrantyContentView obj2 = (WarrantyContentView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
