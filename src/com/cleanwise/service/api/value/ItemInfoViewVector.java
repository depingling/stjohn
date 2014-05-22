
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        ItemInfoViewVector
 * Description:  Container object for ItemInfoView objects
 * Purpose:      Provides container storage for ItemInfoView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ItemInfoViewVector</code>
 */
public class ItemInfoViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -5580462839351628321L;
    /**
     * Constructor.
     */
    public ItemInfoViewVector () {}

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
      ItemInfoView obj1 = (ItemInfoView)o1;
      ItemInfoView obj2 = (ItemInfoView)o2;
      
      if("ItemId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getItemId();
        int i2 = obj2.getItemId();
        retcode = i1-i2;
      }
      
      if("PoLineNum".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getPoLineNum();
        int i2 = obj2.getPoLineNum();
        retcode = i1-i2;
      }
      
      if("ItemName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getItemName();
        String i2 = obj2.getItemName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
