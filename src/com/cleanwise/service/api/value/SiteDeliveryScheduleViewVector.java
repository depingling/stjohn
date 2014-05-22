
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        SiteDeliveryScheduleViewVector
 * Description:  Container object for SiteDeliveryScheduleView objects
 * Purpose:      Provides container storage for SiteDeliveryScheduleView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>SiteDeliveryScheduleViewVector</code>
 */
public class SiteDeliveryScheduleViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6684934913434015354L;
    /**
     * Constructor.
     */
    public SiteDeliveryScheduleViewVector () {}

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
      SiteDeliveryScheduleView obj1 = (SiteDeliveryScheduleView)o1;
      SiteDeliveryScheduleView obj2 = (SiteDeliveryScheduleView)o2;
      
      if("SiteId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSiteId();
        int i2 = obj2.getSiteId();
        retcode = i1-i2;
      }
      
      if("SiteName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSiteName();
        String i2 = obj2.getSiteName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SiteStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSiteStatusCd();
        String i2 = obj2.getSiteStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SiteScheduleType".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSiteScheduleType();
        String i2 = obj2.getSiteScheduleType();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("City".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCity();
        String i2 = obj2.getCity();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("State".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getState();
        String i2 = obj2.getState();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("PostalCode".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getPostalCode();
        String i2 = obj2.getPostalCode();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("County".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCounty();
        String i2 = obj2.getCounty();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("IntervWeek".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getIntervWeek();
        String i2 = obj2.getIntervWeek();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
