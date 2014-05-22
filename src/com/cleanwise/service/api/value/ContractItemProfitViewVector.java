
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        ContractItemProfitViewVector
 * Description:  Container object for ContractItemProfitView objects
 * Purpose:      Provides container storage for ContractItemProfitView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ContractItemProfitViewVector</code>
 */
public class ContractItemProfitViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2661082099838940230L;
    /**
     * Constructor.
     */
    public ContractItemProfitViewVector () {}

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
      ContractItemProfitView obj1 = (ContractItemProfitView)o1;
      ContractItemProfitView obj2 = (ContractItemProfitView)o2;
      
      if("ItemSku".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemSku();
        int i2 = obj2.getItemSku();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
