
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        ItemCatalogAggrViewVector
 * Description:  Container object for ItemCatalogAggrView objects
 * Purpose:      Provides container storage for ItemCatalogAggrView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ItemCatalogAggrViewVector</code>
 */
public class ItemCatalogAggrViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 1673797685278927254L;
    /**
     * Constructor.
     */
    public ItemCatalogAggrViewVector () {}

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
      ItemCatalogAggrView obj1 = (ItemCatalogAggrView)o1;
      ItemCatalogAggrView obj2 = (ItemCatalogAggrView)o2;
      
      if("SelectFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getSelectFl();
        boolean i2 = obj2.getSelectFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("AccountId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getAccountId();
        int i2 = obj2.getAccountId();
        retcode = i1-i2;
      }
      
      if("CatalogId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCatalogId();
        int i2 = obj2.getCatalogId();
        retcode = i1-i2;
      }
      
      if("ContractId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getContractId();
        int i2 = obj2.getContractId();
        retcode = i1-i2;
      }
      
      if("OrderGuideId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getOrderGuideId();
        int i2 = obj2.getOrderGuideId();
        retcode = i1-i2;
      }
      
      if("OrderGuideName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getOrderGuideName();
        String i2 = obj2.getOrderGuideName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogName();
        String i2 = obj2.getCatalogName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogType".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogType();
        String i2 = obj2.getCatalogType();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogStatus".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogStatus();
        String i2 = obj2.getCatalogStatus();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemId();
        int i2 = obj2.getItemId();
        retcode = i1-i2;
      }
      
      if("ItemName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemName();
        String i2 = obj2.getItemName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SkuNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSkuNum();
        String i2 = obj2.getSkuNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SkuSize".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSkuSize();
        String i2 = obj2.getSkuSize();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SkuUom".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSkuUom();
        String i2 = obj2.getSkuUom();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SkuPack".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSkuPack();
        String i2 = obj2.getSkuPack();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ManufId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getManufId();
        int i2 = obj2.getManufId();
        retcode = i1-i2;
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
      
      if("CategoryId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCategoryId();
        int i2 = obj2.getCategoryId();
        retcode = i1-i2;
      }
      
      if("CategoryIdInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCategoryIdInp();
        String i2 = obj2.getCategoryIdInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CategoryName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCategoryName();
        String i2 = obj2.getCategoryName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CostCenterId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCostCenterId();
        int i2 = obj2.getCostCenterId();
        retcode = i1-i2;
      }
      
      if("CostCenterIdInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCostCenterIdInp();
        String i2 = obj2.getCostCenterIdInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CostCenterName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCostCenterName();
        String i2 = obj2.getCostCenterName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getDistId();
        int i2 = obj2.getDistId();
        retcode = i1-i2;
      }
      
      if("DistIdInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistIdInp();
        String i2 = obj2.getDistIdInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistName();
        String i2 = obj2.getDistName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Cost".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getCost();
        BigDecimal i2 = obj2.getCost();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CostInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCostInp();
        String i2 = obj2.getCostInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Price".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getPrice();
        BigDecimal i2 = obj2.getPrice();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("PriceInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getPriceInp();
        String i2 = obj2.getPriceInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BaseCost".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getBaseCost();
        BigDecimal i2 = obj2.getBaseCost();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BaseCostInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBaseCostInp();
        String i2 = obj2.getBaseCostInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogSkuNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogSkuNum();
        String i2 = obj2.getCatalogSkuNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogSkuNumInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogSkuNumInp();
        String i2 = obj2.getCatalogSkuNumInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistSkuNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistSkuNum();
        String i2 = obj2.getDistSkuNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistSkuNumInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistSkuNumInp();
        String i2 = obj2.getDistSkuNumInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistSkuPack".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistSkuPack();
        String i2 = obj2.getDistSkuPack();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistSkuPackInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistSkuPackInp();
        String i2 = obj2.getDistSkuPackInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistSkuUom".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistSkuUom();
        String i2 = obj2.getDistSkuUom();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistSkuUomInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistSkuUomInp();
        String i2 = obj2.getDistSkuUomInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistConvers".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getDistConvers();
        BigDecimal i2 = obj2.getDistConvers();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistConversInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistConversInp();
        String i2 = obj2.getDistConversInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GenManufId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getGenManufId();
        int i2 = obj2.getGenManufId();
        retcode = i1-i2;
      }
      
      if("GenManufIdInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGenManufIdInp();
        String i2 = obj2.getGenManufIdInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GenManufName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGenManufName();
        String i2 = obj2.getGenManufName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GenManufSkuNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGenManufSkuNum();
        String i2 = obj2.getGenManufSkuNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GenManufSkuNumInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGenManufSkuNumInp();
        String i2 = obj2.getGenManufSkuNumInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getCatalogFl();
        boolean i2 = obj2.getCatalogFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("CatalogFlInp".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getCatalogFlInp();
        boolean i2 = obj2.getCatalogFlInp();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("ContractFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getContractFl();
        boolean i2 = obj2.getContractFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("ContractFlInp".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getContractFlInp();
        boolean i2 = obj2.getContractFlInp();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("OrderGuideFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getOrderGuideFl();
        boolean i2 = obj2.getOrderGuideFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("OrderGuideFlInp".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getOrderGuideFlInp();
        boolean i2 = obj2.getOrderGuideFlInp();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("DistSPLFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getDistSPLFl();
        boolean i2 = obj2.getDistSPLFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("DistSPLFlInp".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getDistSPLFlInp();
        boolean i2 = obj2.getDistSPLFlInp();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("TaxExemptFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getTaxExemptFl();
        boolean i2 = obj2.getTaxExemptFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("TaxExemptFlInp".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getTaxExemptFlInp();
        boolean i2 = obj2.getTaxExemptFlInp();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("SpecialPermissionFl".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getSpecialPermissionFl();
        boolean i2 = obj2.getSpecialPermissionFl();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("SpecialPermissionFlInp".equalsIgnoreCase(_sortField)) {
        boolean i1 = obj1.getSpecialPermissionFlInp();
        boolean i2 = obj2.getSpecialPermissionFlInp();
        if(i1==false) if(i2==false) retcode = 0; else retcode = -1;
        else if(i2==false) retcode = 1; else retcode = 0;
      }
      
      if("DistErpSkuNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistErpSkuNum();
        String i2 = obj2.getDistErpSkuNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistErpUom".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistErpUom();
        String i2 = obj2.getDistErpUom();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistErpNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistErpNum();
        String i2 = obj2.getDistErpNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("MultiproductId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getMultiproductId();
        int i2 = obj2.getMultiproductId();
        retcode = i1-i2;
      }
      
      if("MultiproductName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMultiproductName();
        String i2 = obj2.getMultiproductName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("MultiproductIdInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMultiproductIdInp();
        String i2 = obj2.getMultiproductIdInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ServiceFeeCode".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getServiceFeeCode();
        String i2 = obj2.getServiceFeeCode();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ServiceFeeCodeInp".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getServiceFeeCodeInp();
        String i2 = obj2.getServiceFeeCodeInp();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ItemStatusCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemStatusCd();
        String i2 = obj2.getItemStatusCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistStatus".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistStatus();
        String i2 = obj2.getDistStatus();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
