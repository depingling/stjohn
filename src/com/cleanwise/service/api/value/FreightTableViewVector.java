
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        FreightTableViewVector
 * Description:  Container object for FreightTableView objects
 * Purpose:      Provides container storage for FreightTableView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>FreightTableViewVector</code>
 */
public class FreightTableViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public FreightTableViewVector () {}

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
      FreightTableView obj1 = (FreightTableView)o1;
      FreightTableView obj2 = (FreightTableView)o2;
      
      if("FreightTableId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getFreightTableId();
        int i2 = obj2.getFreightTableId();
        retcode = i1-i2;
      }
      
      if("ShortDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShortDesc();
        String i2 = obj2.getShortDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("FreightTableStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getFreightTableStatusCd();
        String i2 = obj2.getFreightTableStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("FreightTableTypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getFreightTableTypeCd();
        String i2 = obj2.getFreightTableTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistributorName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistributorName();
        String i2 = obj2.getDistributorName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
