
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        ContractItemPriceViewVector
 * Description:  Container object for ContractItemPriceView objects
 * Purpose:      Provides container storage for ContractItemPriceView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ContractItemPriceViewVector</code>
 */
public class ContractItemPriceViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5760159770037125600L;
    /**
     * Constructor.
     */
    public ContractItemPriceViewVector () {}

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
      ContractItemPriceView obj1 = (ContractItemPriceView)o1;
      ContractItemPriceView obj2 = (ContractItemPriceView)o2;
      
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
      
      if("ItemSize".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemSize();
        String i2 = obj2.getItemSize();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemMfgId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemMfgId();
        int i2 = obj2.getItemMfgId();
        retcode = i1-i2;
      }
      
      if("ItemMfgName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemMfgName();
        String i2 = obj2.getItemMfgName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemMfgSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemMfgSku();
        String i2 = obj2.getItemMfgSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ContractId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getContractId();
        int i2 = obj2.getContractId();
        retcode = i1-i2;
      }
      
      if("ContractName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getContractName();
        String i2 = obj2.getContractName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getDistId();
        int i2 = obj2.getDistId();
        retcode = i1-i2;
      }
      
      if("DistName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistName();
        String i2 = obj2.getDistName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistCost".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getDistCost();
        BigDecimal i2 = obj2.getDistCost();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Price".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getPrice();
        BigDecimal i2 = obj2.getPrice();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemCustDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemCustDesc();
        String i2 = obj2.getItemCustDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemCustSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemCustSku();
        String i2 = obj2.getItemCustSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
