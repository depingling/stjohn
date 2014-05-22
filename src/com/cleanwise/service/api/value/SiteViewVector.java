package com.cleanwise.service.api.value;
import java.util.Collections;
/**
 * Title:        SiteViewVector
 * Description:  Container object for SiteView objects
 * Purpose:      Provides container storage for SiteView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */

public class SiteViewVector extends java.util.ArrayList implements java.util.Comparator
{

    /**
     *
     */
    public SiteViewVector() {}
    /**
     * Sort
     */
    boolean _ascFl = true;
    String _sortField = "";

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
      SiteView obj1 = (SiteView)o1;
      SiteView obj2 = (SiteView)o2;
      
      
      if("Name".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getName();
        String i2 = obj2.getName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }

}
