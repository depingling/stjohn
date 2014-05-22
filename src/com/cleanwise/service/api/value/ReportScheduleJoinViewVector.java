
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.*;

/**
 * Title:        ReportScheduleJoinViewVector
 * Description:  Container object for ReportScheduleJoinView objects
 * Purpose:      Provides container storage for ReportScheduleJoinView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ReportScheduleJoinViewVector</code>
 */
public class ReportScheduleJoinViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -322524118510031736L;
    /**
     * Constructor.
     */
    public ReportScheduleJoinViewVector () {}

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
      ReportScheduleJoinView obj1 = (ReportScheduleJoinView)o1;
      ReportScheduleJoinView obj2 = (ReportScheduleJoinView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
