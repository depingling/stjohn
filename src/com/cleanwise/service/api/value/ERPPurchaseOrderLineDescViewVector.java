
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        ERPPurchaseOrderLineDescViewVector
 * Description:  Container object for ERPPurchaseOrderLineDescView objects
 * Purpose:      Provides container storage for ERPPurchaseOrderLineDescView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ERPPurchaseOrderLineDescViewVector</code>
 */
public class ERPPurchaseOrderLineDescViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1770615597738589479L;
    /**
     * Constructor.
     */
    public ERPPurchaseOrderLineDescViewVector () {}

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
      ERPPurchaseOrderLineDescView obj1 = (ERPPurchaseOrderLineDescView)o1;
      ERPPurchaseOrderLineDescView obj2 = (ERPPurchaseOrderLineDescView)o2;
      
      if("ErpPoNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getErpPoNum();
        String i2 = obj2.getErpPoNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ErpOrderNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getErpOrderNum();
        String i2 = obj2.getErpOrderNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ErpSkuNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getErpSkuNum();
        String i2 = obj2.getErpSkuNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ErpOpenQty".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getErpOpenQty();
        int i2 = obj2.getErpOpenQty();
        retcode = i1-i2;
      }
      
      if("ErpOpenAmount".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getErpOpenAmount();
        BigDecimal i2 = obj2.getErpOpenAmount();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistributorName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistributorName();
        String i2 = obj2.getDistributorName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AccountName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAccountName();
        String i2 = obj2.getAccountName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ErpPoDate".equalsIgnoreCase(_sortField)) {
        java.util.Date i1 = obj1.getErpPoDate();
        java.util.Date i2 = obj2.getErpPoDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("OpenLineStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getOpenLineStatusCd();
        String i2 = obj2.getOpenLineStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipCity".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipCity();
        String i2 = obj2.getShipCity();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipState".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipState();
        String i2 = obj2.getShipState();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipPostalCode".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipPostalCode();
        String i2 = obj2.getShipPostalCode();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShipCountry".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShipCountry();
        String i2 = obj2.getShipCountry();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
