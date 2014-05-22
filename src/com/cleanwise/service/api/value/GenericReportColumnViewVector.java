
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        GenericReportColumnViewVector
 * Description:  Container object for GenericReportColumnView objects
 * Purpose:      Provides container storage for GenericReportColumnView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>GenericReportColumnViewVector</code>
 */
public class GenericReportColumnViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5838248078801288027L;
    /**
     * Constructor.
     */
    public GenericReportColumnViewVector () {}

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
      GenericReportColumnView obj1 = (GenericReportColumnView)o1;
      GenericReportColumnView obj2 = (GenericReportColumnView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
