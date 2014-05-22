
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WarrantyViewVector
 * Description:  Container object for WarrantyView objects
 * Purpose:      Provides container storage for WarrantyView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WarrantyViewVector</code>
 */
public class WarrantyViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -825185247658170383L;
    /**
     * Constructor.
     */
    public WarrantyViewVector () {}

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
      WarrantyView obj1 = (WarrantyView)o1;
      WarrantyView obj2 = (WarrantyView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
