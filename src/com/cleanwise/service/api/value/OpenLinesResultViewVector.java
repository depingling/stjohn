
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;import java.util.Date;

/**
 * Title:        OpenLinesResultViewVector
 * Description:  Container object for OpenLinesResultView objects
 * Purpose:      Provides container storage for OpenLinesResultView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>OpenLinesResultViewVector</code>
 */
public class OpenLinesResultViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -411601385537853260L;
    /**
     * Constructor.
     */
    public OpenLinesResultViewVector () {}

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
      OpenLinesResultView obj1 = (OpenLinesResultView)o1;
      OpenLinesResultView obj2 = (OpenLinesResultView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
