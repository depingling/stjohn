
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.util.Vector;

/**
 * Title:        ContractItemSubstViewVector
 * Description:  Container object for ContractItemSubstView objects
 * Purpose:      Provides container storage for ContractItemSubstView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ContractItemSubstViewVector</code>
 */
public class ContractItemSubstViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7380163968629557066L;
    /**
     * Constructor.
     */
    public ContractItemSubstViewVector () {}

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
      ContractItemSubstView obj1 = (ContractItemSubstView)o1;
      ContractItemSubstView obj2 = (ContractItemSubstView)o2;
      
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
      
      if("SubstItemId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSubstItemId();
        int i2 = obj2.getSubstItemId();
        retcode = i1-i2;
      }
      
      if("SubstItemSku".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSubstItemSku();
        int i2 = obj2.getSubstItemSku();
        retcode = i1-i2;
      }
      
      if("SubstItemDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSubstItemDesc();
        String i2 = obj2.getSubstItemDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SubstItemUom".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSubstItemUom();
        String i2 = obj2.getSubstItemUom();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SubstItemPack".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSubstItemPack();
        String i2 = obj2.getSubstItemPack();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SubstItemSize".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSubstItemSize();
        String i2 = obj2.getSubstItemSize();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SubstItemMfgId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSubstItemMfgId();
        int i2 = obj2.getSubstItemMfgId();
        retcode = i1-i2;
      }
      
      if("SubstItemMfgName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSubstItemMfgName();
        String i2 = obj2.getSubstItemMfgName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SubstItemMfgSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSubstItemMfgSku();
        String i2 = obj2.getSubstItemMfgSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SustStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSustStatusCd();
        String i2 = obj2.getSustStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
