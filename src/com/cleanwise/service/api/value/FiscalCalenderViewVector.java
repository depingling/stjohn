
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        FiscalCalenderViewVector
 * Description:  Container object for FiscalCalenderView objects
 * Purpose:      Provides container storage for FiscalCalenderView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>FiscalCalenderViewVector</code>
 */
public class FiscalCalenderViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public FiscalCalenderViewVector () {}

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
      FiscalCalenderView obj1 = (FiscalCalenderView)o1;
      FiscalCalenderView obj2 = (FiscalCalenderView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
