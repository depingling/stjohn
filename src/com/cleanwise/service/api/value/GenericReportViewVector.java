
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        GenericReportViewVector
 * Description:  Container object for GenericReportView objects
 * Purpose:      Provides container storage for GenericReportView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>GenericReportViewVector</code>
 */
public class GenericReportViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7798739259635880712L;
    /**
     * Constructor.
     */
    public GenericReportViewVector () {}

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
      GenericReportView obj1 = (GenericReportView)o1;
      GenericReportView obj2 = (GenericReportView)o2;
      
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
      
      if("LongDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getLongDesc();
        String i2 = obj2.getLongDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DBName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDBName();
        String i2 = obj2.getDBName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ReportClass".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getReportClass();
        String i2 = obj2.getReportClass();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
