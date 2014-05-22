
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        CatalogItemDescViewVector
 * Description:  Container object for CatalogItemDescView objects
 * Purpose:      Provides container storage for CatalogItemDescView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CatalogItemDescViewVector</code>
 */
public class CatalogItemDescViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6748790183878054096L;
    /**
     * Constructor.
     */
    public CatalogItemDescViewVector () {}

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
      CatalogItemDescView obj1 = (CatalogItemDescView)o1;
      CatalogItemDescView obj2 = (CatalogItemDescView)o2;
      
      if("StoreId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getStoreId();
        int i2 = obj2.getStoreId();
        retcode = i1-i2;
      }
      
      if("CatalogId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCatalogId();
        int i2 = obj2.getCatalogId();
        retcode = i1-i2;
      }
      
      if("ItemId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemId();
        int i2 = obj2.getItemId();
        retcode = i1-i2;
      }
      
      if("CategoryId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCategoryId();
        int i2 = obj2.getCategoryId();
        retcode = i1-i2;
      }
      
      if("ManufId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getManufId();
        int i2 = obj2.getManufId();
        retcode = i1-i2;
      }
      
      if("Name".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getName();
        String i2 = obj2.getName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Sku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSku();
        String i2 = obj2.getSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Category".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCategory();
        String i2 = obj2.getCategory();
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
      
      if("Color".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getColor();
        String i2 = obj2.getColor();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ManufName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getManufName();
        String i2 = obj2.getManufName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ManufSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getManufSku();
        String i2 = obj2.getManufSku();
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
      
      if("DistSku".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistSku();
        String i2 = obj2.getDistSku();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogPrice".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getCatalogPrice();
        BigDecimal i2 = obj2.getCatalogPrice();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistCost".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getDistCost();
        BigDecimal i2 = obj2.getDistCost();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BaseCost".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getBaseCost();
        BigDecimal i2 = obj2.getBaseCost();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Selected".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getSelected();
        boolean i2 = obj2.getSelected();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
