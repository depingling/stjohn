
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ReturnRequestDescDataViewVector
 * Description:  Container object for ReturnRequestDescDataView objects
 * Purpose:      Provides container storage for ReturnRequestDescDataView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ReturnRequestDescDataViewVector</code>
 */
public class ReturnRequestDescDataViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 8643000861863890453L;
    /**
     * Constructor.
     */
    public ReturnRequestDescDataViewVector () {}

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
      ReturnRequestDescDataView obj1 = (ReturnRequestDescDataView)o1;
      ReturnRequestDescDataView obj2 = (ReturnRequestDescDataView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
