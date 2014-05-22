
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

/**
 * Title:        ReportSchedGroupShareViewVector
 * Description:  Container object for ReportSchedGroupShareView objects
 * Purpose:      Provides container storage for ReportSchedGroupShareView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ReportSchedGroupShareViewVector</code>
 */
public class ReportSchedGroupShareViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -9049424613416599382L;
    /**
     * Constructor.
     */
    public ReportSchedGroupShareViewVector () {}

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
      ReportSchedGroupShareView obj1 = (ReportSchedGroupShareView)o1;
      ReportSchedGroupShareView obj2 = (ReportSchedGroupShareView)o2;
      
      if("ReportScheduleId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getReportScheduleId();
        int i2 = obj2.getReportScheduleId();
        retcode = i1-i2;
      }
      
      if("GroupId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getGroupId();
        int i2 = obj2.getGroupId();
        retcode = i1-i2;
      }
      
      if("GroupShortDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGroupShortDesc();
        String i2 = obj2.getGroupShortDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GroupTypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGroupTypeCd();
        String i2 = obj2.getGroupTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GroupStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGroupStatusCd();
        String i2 = obj2.getGroupStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("NotifyFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getNotifyFl();
        boolean i2 = obj2.getNotifyFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
