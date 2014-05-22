
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        ItemContractCostViewVector
 * Description:  Container object for ItemContractCostView objects
 * Purpose:      Provides container storage for ItemContractCostView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ItemContractCostViewVector</code>
 */
public class ItemContractCostViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -5374484575951604750L;
    /**
     * Constructor.
     */
    public ItemContractCostViewVector () {}

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
      ItemContractCostView obj1 = (ItemContractCostView)o1;
      ItemContractCostView obj2 = (ItemContractCostView)o2;
      
      if("ItemId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemId();
        int i2 = obj2.getItemId();
        retcode = i1-i2;
      }
      
      if("DistId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getDistId();
        int i2 = obj2.getDistId();
        retcode = i1-i2;
      }
      
      if("ContractId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getContractId();
        int i2 = obj2.getContractId();
        retcode = i1-i2;
      }
      
      if("CatalogId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCatalogId();
        int i2 = obj2.getCatalogId();
        retcode = i1-i2;
      }
      
      if("ItemCost".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getItemCost();
        BigDecimal i2 = obj2.getItemCost();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("DistDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistDesc();
        String i2 = obj2.getDistDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ContractDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getContractDesc();
        String i2 = obj2.getContractDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
