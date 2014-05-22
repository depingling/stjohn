

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        StoreProductViewVector
 * Description:  Container object for StoreProductView objects
 * Purpose:      Provides container storage for StoreProductView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>StoreProductViewVector</code>
 */
public class StoreProductViewVector extends java.util.ArrayList implements Comparator
{
    /**
     * Constructor.
     */
    public StoreProductViewVector () {}

    String _sortField = "";
    /**
     * Sort
     */
    public void sort(String pFieldName) {
       _sortField = pFieldName;
       Collections.sort(this,this);
    }

    /*
    *
    */
    public int compare(Object o1, Object o2)
    {
      int retcode = -1;
      StoreProductView obj1 = (StoreProductView)o1;
      StoreProductView obj2 = (StoreProductView)o2;

      
      if("ProductId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getProduct().getProductId();
        int i2 = obj2.getProduct().getProductId();
        retcode = i1-i2;
      }
      
      if("StoreId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getStore().getStoreId();
        int i2 = obj2.getStore().getStoreId();
        retcode = i1-i2;
      }
      
      return retcode;
    }
}
