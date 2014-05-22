
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        AggregateItemViewVector
 * Description:  Container object for AggregateItemView objects
 * Purpose:      Provides container storage for AggregateItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AggregateItemViewVector</code>
 */
public class AggregateItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 4002843712732717940L;
    /**
     * Constructor.
     */
    public AggregateItemViewVector () {}

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
      AggregateItemView obj1 = (AggregateItemView)o1;
      AggregateItemView obj2 = (AggregateItemView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
