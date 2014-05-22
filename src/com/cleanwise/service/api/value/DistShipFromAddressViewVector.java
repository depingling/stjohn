
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        DistShipFromAddressViewVector
 * Description:  Container object for DistShipFromAddressView objects
 * Purpose:      Provides container storage for DistShipFromAddressView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>DistShipFromAddressViewVector</code>
 */
public class DistShipFromAddressViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5518521801448696580L;
    /**
     * Constructor.
     */
    public DistShipFromAddressViewVector () {}

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
      DistShipFromAddressView obj1 = (DistShipFromAddressView)o1;
      DistShipFromAddressView obj2 = (DistShipFromAddressView)o2;
      
      if("DistributorId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getDistributorId();
        int i2 = obj2.getDistributorId();
        retcode = i1-i2;
      }
      
      if("DistName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistName();
        String i2 = obj2.getDistName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipFromAddressId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getShipFromAddressId();
        int i2 = obj2.getShipFromAddressId();
        retcode = i1-i2;
      }
      
      if("ShipFromName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipFromName();
        String i2 = obj2.getShipFromName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipFromAddress1".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipFromAddress1();
        String i2 = obj2.getShipFromAddress1();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipFromCity".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipFromCity();
        String i2 = obj2.getShipFromCity();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipFromState".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipFromState();
        String i2 = obj2.getShipFromState();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipFromPostalCode".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipFromPostalCode();
        String i2 = obj2.getShipFromPostalCode();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipFromStatus".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipFromStatus();
        String i2 = obj2.getShipFromStatus();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
