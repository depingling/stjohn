
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        GenericReportCellViewVector
 * Description:  Container object for GenericReportCellView objects
 * Purpose:      Provides container storage for GenericReportCellView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>GenericReportCellViewVector</code>
 */
public class GenericReportCellViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5838248078801288027L;
    /**
     * Constructor.
     */
    public GenericReportCellViewVector () {}

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
      GenericReportCellView obj1 = (GenericReportCellView)o1;
      GenericReportCellView obj2 = (GenericReportCellView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
