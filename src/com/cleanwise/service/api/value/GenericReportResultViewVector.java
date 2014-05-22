
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;import com.cleanwise.service.api.value.GenericReportColumnViewVector;

/**
 * Title:        GenericReportResultViewVector
 * Description:  Container object for GenericReportResultView objects
 * Purpose:      Provides container storage for GenericReportResultView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>GenericReportResultViewVector</code>
 */
public class GenericReportResultViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2846541702626774161L;
    /**
     * Constructor.
     */
    public GenericReportResultViewVector () {}

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
      GenericReportResultView obj1 = (GenericReportResultView)o1;
      GenericReportResultView obj2 = (GenericReportResultView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
