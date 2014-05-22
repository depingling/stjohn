
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

/**
 * Title:        ReportSchedUserShareViewVector
 * Description:  Container object for ReportSchedUserShareView objects
 * Purpose:      Provides container storage for ReportSchedUserShareView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ReportSchedUserShareViewVector</code>
 */
public class ReportSchedUserShareViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1947346202792287331L;
    /**
     * Constructor.
     */
    public ReportSchedUserShareViewVector () {}

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
      ReportSchedUserShareView obj1 = (ReportSchedUserShareView)o1;
      ReportSchedUserShareView obj2 = (ReportSchedUserShareView)o2;
      
      if("ReportScheduleId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getReportScheduleId();
        int i2 = obj2.getReportScheduleId();
        retcode = i1-i2;
      }
      
      if("UserId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getUserId();
        int i2 = obj2.getUserId();
        retcode = i1-i2;
      }
      
      if("UserFirstName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserFirstName();
        String i2 = obj2.getUserFirstName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("UserLastName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserLastName();
        String i2 = obj2.getUserLastName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("UserLoginName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserLoginName();
        String i2 = obj2.getUserLoginName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("UserTypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserTypeCd();
        String i2 = obj2.getUserTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("UserStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserStatusCd();
        String i2 = obj2.getUserStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("NotifyFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getNotifyFl();
        boolean i2 = obj2.getNotifyFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("ReportOwnerFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getReportOwnerFl();
        boolean i2 = obj2.getReportOwnerFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
