
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        BusEntityTerrViewVector
 * Description:  Container object for BusEntityTerrView objects
 * Purpose:      Provides container storage for BusEntityTerrView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>BusEntityTerrViewVector</code>
 */
public class BusEntityTerrViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -5046248154715614869L;
    /**
     * Constructor.
     */
    public BusEntityTerrViewVector () {}

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
      BusEntityTerrView obj1 = (BusEntityTerrView)o1;
      BusEntityTerrView obj2 = (BusEntityTerrView)o2;
      
      if("BusEntityId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBusEntityId();
        int i2 = obj2.getBusEntityId();
        retcode = i1-i2;
      }
      
      if("PostalCodeId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getPostalCodeId();
        int i2 = obj2.getPostalCodeId();
        retcode = i1-i2;
      }
      
      if("PostalCode".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getPostalCode();
        String i2 = obj2.getPostalCode();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CountyCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCountyCd();
        String i2 = obj2.getCountyCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("StateProvinceCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getStateProvinceCd();
        String i2 = obj2.getStateProvinceCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("StateProvinceName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getStateProvinceName();
        String i2 = obj2.getStateProvinceName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CountryCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCountryCd();
        String i2 = obj2.getCountryCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("City".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCity();
        String i2 = obj2.getCity();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
