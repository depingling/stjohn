
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        SiteInventoryConfigViewVector
 * Description:  Container object for SiteInventoryConfigView objects
 * Purpose:      Provides container storage for SiteInventoryConfigView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>SiteInventoryConfigViewVector</code>
 */
public class SiteInventoryConfigViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -3547605434525848084L;
    /**
     * Constructor.
     */
    public SiteInventoryConfigViewVector () {}

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
      SiteInventoryConfigView obj1 = (SiteInventoryConfigView)o1;
      SiteInventoryConfigView obj2 = (SiteInventoryConfigView)o2;
      
      if("SiteId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSiteId();
        int i2 = obj2.getSiteId();
        retcode = i1-i2;
      }
      
      if("ItemId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemId();
        int i2 = obj2.getItemId();
        retcode = i1-i2;
      }
      
      if("ItemSku".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemSku();
        int i2 = obj2.getItemSku();
        retcode = i1-i2;
      }
      
      if("ActualSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getActualSku();
        String i2 = obj2.getActualSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemDesc();
        String i2 = obj2.getItemDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemUom".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemUom();
        String i2 = obj2.getItemUom();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemPack".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemPack();
        String i2 = obj2.getItemPack();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }

      if("QtyOnHand".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getQtyOnHand();
        String i2 = obj2.getQtyOnHand();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("OrderQty".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getOrderQty();
        String i2 = obj2.getOrderQty();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SumOfAllParValues".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSumOfAllParValues();
        int i2 = obj2.getSumOfAllParValues();
        retcode = i1-i2;
      }
      
      if("AutoOrderItem".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAutoOrderItem();
        String i2 = obj2.getAutoOrderItem();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("InitialQtyOnHand".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getInitialQtyOnHand();
        String i2 = obj2.getInitialQtyOnHand();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
