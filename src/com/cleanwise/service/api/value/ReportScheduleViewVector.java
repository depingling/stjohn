
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

/**
 * Title:        ReportScheduleViewVector
 * Description:  Container object for ReportScheduleView objects
 * Purpose:      Provides container storage for ReportScheduleView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ReportScheduleViewVector</code>
 */
public class ReportScheduleViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2340128420699795026L;
    /**
     * Constructor.
     */
    public ReportScheduleViewVector () {}

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
      ReportScheduleView obj1 = (ReportScheduleView)o1;
      ReportScheduleView obj2 = (ReportScheduleView)o2;
      
      if("ReportScheduleId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getReportScheduleId();
        int i2 = obj2.getReportScheduleId();
        retcode = i1-i2;
      }
      
      if("GenericReportId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getGenericReportId();
        int i2 = obj2.getGenericReportId();
        retcode = i1-i2;
      }
      
      if("ReportCategory".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getReportCategory();
        String i2 = obj2.getReportCategory();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ReportName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getReportName();
        String i2 = obj2.getReportName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("LastRunDate".equalsIgnoreCase(_sortField)) {
        Date i1 = obj1.getLastRunDate();
        Date i2 = obj2.getLastRunDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ScheduleName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getScheduleName();
        String i2 = obj2.getScheduleName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
