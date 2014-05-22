
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ConsolidatedCartViewVector
 * Description:  Container object for ConsolidatedCartView objects
 * Purpose:      Provides container storage for ConsolidatedCartView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ConsolidatedCartViewVector</code>
 */
public class ConsolidatedCartViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6520437898129001158L;
    /**
     * Constructor.
     */
    public ConsolidatedCartViewVector () {}

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
      ConsolidatedCartView obj1 = (ConsolidatedCartView)o1;
      ConsolidatedCartView obj2 = (ConsolidatedCartView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
