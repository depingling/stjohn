
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WarrantyAssocViewVector
 * Description:  Container object for WarrantyAssocView objects
 * Purpose:      Provides container storage for WarrantyAssocView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WarrantyAssocViewVector</code>
 */
public class WarrantyAssocViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5021186338272883206L;
    /**
     * Constructor.
     */
    public WarrantyAssocViewVector () {}

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
      WarrantyAssocView obj1 = (WarrantyAssocView)o1;
      WarrantyAssocView obj2 = (WarrantyAssocView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
