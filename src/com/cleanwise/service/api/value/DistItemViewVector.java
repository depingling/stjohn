
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        DistItemViewVector
 * Description:  Container object for DistItemView objects
 * Purpose:      Provides container storage for DistItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>DistItemViewVector</code>
 */
public class DistItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 3261097954616628578L;
    /**
     * Constructor.
     */
    public DistItemViewVector () {}

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
      DistItemView obj1 = (DistItemView)o1;
      DistItemView obj2 = (DistItemView)o2;
      
      if("ItemId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemId();
        int i2 = obj2.getItemId();
        retcode = i1-i2;
      }
      
      if("ItemMfgMappingId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemMfgMappingId();
        int i2 = obj2.getItemMfgMappingId();
        retcode = i1-i2;
      }
      
      if("ItemDistMappingId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemDistMappingId();
        int i2 = obj2.getItemDistMappingId();
        retcode = i1-i2;
      }
      
      if("ItemMfg1MappingId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemMfg1MappingId();
        int i2 = obj2.getItemMfg1MappingId();
        retcode = i1-i2;
      }
      
      if("ItemMappingAssocId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemMappingAssocId();
        int i2 = obj2.getItemMappingAssocId();
        retcode = i1-i2;
      }
      
      if("Sku".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSku();
        int i2 = obj2.getSku();
        retcode = i1-i2;
      }
      
      if("Name".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getName();
        String i2 = obj2.getName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Size".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSize();
        String i2 = obj2.getSize();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Pack".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getPack();
        String i2 = obj2.getPack();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Uom".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUom();
        String i2 = obj2.getUom();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("MfgId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getMfgId();
        int i2 = obj2.getMfgId();
        retcode = i1-i2;
      }
      
      if("MfgName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMfgName();
        String i2 = obj2.getMfgName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("MfgItemSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMfgItemSku();
        String i2 = obj2.getMfgItemSku();
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
      
      if("DistItemSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistItemSku();
        String i2 = obj2.getDistItemSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistItemPack".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistItemPack();
        String i2 = obj2.getDistItemPack();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistItemUom".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistItemUom();
        String i2 = obj2.getDistItemUom();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Mfg1Id".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getMfg1Id();
        int i2 = obj2.getMfg1Id();
        retcode = i1-i2;
      }
      
      if("Mfg1Name".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMfg1Name();
        String i2 = obj2.getMfg1Name();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Mfg1ItemSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMfg1ItemSku();
        String i2 = obj2.getMfg1ItemSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistUomConvMultiplier".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getDistUomConvMultiplier();
        BigDecimal i2 = obj2.getDistUomConvMultiplier();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
