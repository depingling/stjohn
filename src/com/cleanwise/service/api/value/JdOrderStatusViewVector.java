
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;import java.util.Date;

/**
 * Title:        JdOrderStatusViewVector
 * Description:  Container object for JdOrderStatusView objects
 * Purpose:      Provides container storage for JdOrderStatusView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>JdOrderStatusViewVector</code>
 */
public class JdOrderStatusViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5190553350838996240L;
    /**
     * Constructor.
     */
    public JdOrderStatusViewVector () {}

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
      JdOrderStatusView obj1 = (JdOrderStatusView)o1;
      JdOrderStatusView obj2 = (JdOrderStatusView)o2;
      
      if("OrderId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getOrderId();
        int i2 = obj2.getOrderId();
        retcode = i1-i2;
      }
      
      if("OrderDate".equalsIgnoreCase(_sortField)) {
        Date i1 = obj1.getOrderDate();
        Date i2 = obj2.getOrderDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CustomerPoNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCustomerPoNum();
        String i2 = obj2.getCustomerPoNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("OrderNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getOrderNum();
        String i2 = obj2.getOrderNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AccountNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAccountNum();
        String i2 = obj2.getAccountNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Company".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCompany();
        String i2 = obj2.getCompany();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("TotalPrice".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getTotalPrice();
        BigDecimal i2 = obj2.getTotalPrice();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("TotalWeight".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getTotalWeight();
        BigDecimal i2 = obj2.getTotalWeight();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
