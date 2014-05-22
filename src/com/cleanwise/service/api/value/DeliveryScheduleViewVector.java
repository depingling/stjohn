
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        DeliveryScheduleViewVector
 * Description:  Container object for DeliveryScheduleView objects
 * Purpose:      Provides container storage for DeliveryScheduleView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>DeliveryScheduleViewVector</code>
 */
public class DeliveryScheduleViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -4945404517947215069L;
    /**
     * Constructor.
     */
    public DeliveryScheduleViewVector () {}

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
      DeliveryScheduleView obj1 = (DeliveryScheduleView)o1;
      DeliveryScheduleView obj2 = (DeliveryScheduleView)o2;
      
      if("BusEntityId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBusEntityId();
        int i2 = obj2.getBusEntityId();
        retcode = i1-i2;
      }
      
      if("BusEntityShortDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBusEntityShortDesc();
        String i2 = obj2.getBusEntityShortDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BusEntityErpNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBusEntityErpNum();
        String i2 = obj2.getBusEntityErpNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ScheduleId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getScheduleId();
        int i2 = obj2.getScheduleId();
        retcode = i1-i2;
      }
      
      if("ScheduleName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getScheduleName();
        String i2 = obj2.getScheduleName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ScheduleStatus".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getScheduleStatus();
        String i2 = obj2.getScheduleStatus();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ScheduleInfo".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getScheduleInfo();
        String i2 = obj2.getScheduleInfo();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CutoffInfo".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCutoffInfo();
        String i2 = obj2.getCutoffInfo();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Exceptions".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getExceptions();
        String i2 = obj2.getExceptions();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("NextDelivery".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getNextDelivery();
        java.util.Date i2 = obj2.getNextDelivery();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("LastProcessedDt".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getLastProcessedDt();
        java.util.Date i2 = obj2.getLastProcessedDt();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
