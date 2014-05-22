
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WarrantyDetailViewVector
 * Description:  Container object for WarrantyDetailView objects
 * Purpose:      Provides container storage for WarrantyDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WarrantyDetailViewVector</code>
 */
public class WarrantyDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7116986873858373066L;
    /**
     * Constructor.
     */
    public WarrantyDetailViewVector () {}

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
      WarrantyDetailView obj1 = (WarrantyDetailView)o1;
      WarrantyDetailView obj2 = (WarrantyDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
