
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

/**
 * Title:        PreparedReportViewVector
 * Description:  Container object for PreparedReportView objects
 * Purpose:      Provides container storage for PreparedReportView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>PreparedReportViewVector</code>
 */
public class PreparedReportViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -5890284595481864884L;
    /**
     * Constructor.
     */
    public PreparedReportViewVector () {}

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
      PreparedReportView obj1 = (PreparedReportView)o1;
      PreparedReportView obj2 = (PreparedReportView)o2;
      
      if("ReportResultId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getReportResultId();
        int i2 = obj2.getReportResultId();
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
      
      if("ReportDate".equalsIgnoreCase(_sortField)) {
        Date i1 = obj1.getReportDate();
        Date i2 = obj2.getReportDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("RequesterId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getRequesterId();
        int i2 = obj2.getRequesterId();
        retcode = i1-i2;
      }
      
      if("RequesterName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getRequesterName();
        String i2 = obj2.getRequesterName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ReportResultStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getReportResultStatusCd();
        String i2 = obj2.getReportResultStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ProtectedFl".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getProtectedFl();
        String i2 = obj2.getProtectedFl();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
