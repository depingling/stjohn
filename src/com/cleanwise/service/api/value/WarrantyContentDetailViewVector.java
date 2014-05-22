
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WarrantyContentDetailViewVector
 * Description:  Container object for WarrantyContentDetailView objects
 * Purpose:      Provides container storage for WarrantyContentDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WarrantyContentDetailViewVector</code>
 */
public class WarrantyContentDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2216829118335270554L;
    /**
     * Constructor.
     */
    public WarrantyContentDetailViewVector () {}

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
      WarrantyContentDetailView obj1 = (WarrantyContentDetailView)o1;
      WarrantyContentDetailView obj2 = (WarrantyContentDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
