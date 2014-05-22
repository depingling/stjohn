
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WorkOrderSearchResultViewVector
 * Description:  Container object for WorkOrderSearchResultView objects
 * Purpose:      Provides container storage for WorkOrderSearchResultView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WorkOrderSearchResultViewVector</code>
 */
public class WorkOrderSearchResultViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7309869677344546070L;
    /**
     * Constructor.
     */
    public WorkOrderSearchResultViewVector () {}

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
      WorkOrderSearchResultView obj1 = (WorkOrderSearchResultView)o1;
      WorkOrderSearchResultView obj2 = (WorkOrderSearchResultView)o2;
      
      if("SiteName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSiteName();
        String i2 = obj2.getSiteName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistributorShipToNumber".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistributorShipToNumber();
        String i2 = obj2.getDistributorShipToNumber();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("WorkOrderId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getWorkOrderId();
        int i2 = obj2.getWorkOrderId();
        retcode = i1-i2;
      }
      
      if("WorkOrderNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getWorkOrderNum();
        String i2 = obj2.getWorkOrderNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("PoNumber".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getPoNumber();
        String i2 = obj2.getPoNumber();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShortDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShortDesc();
        String i2 = obj2.getShortDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("TypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getTypeCd();
        String i2 = obj2.getTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Priority".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getPriority();
        String i2 = obj2.getPriority();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("StatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getStatusCd();
        String i2 = obj2.getStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ActualStartDate".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getActualStartDate();
        java.util.Date i2 = obj2.getActualStartDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ActualFinishDate".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getActualFinishDate();
        java.util.Date i2 = obj2.getActualFinishDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
