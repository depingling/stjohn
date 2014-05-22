
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        SelfServiceOrderDescViewVector
 * Description:  Container object for SelfServiceOrderDescView objects
 * Purpose:      Provides container storage for SelfServiceOrderDescView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>SelfServiceOrderDescViewVector</code>
 */
public class SelfServiceOrderDescViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public SelfServiceOrderDescViewVector () {}

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
      SelfServiceOrderDescView obj1 = (SelfServiceOrderDescView)o1;
      SelfServiceOrderDescView obj2 = (SelfServiceOrderDescView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
