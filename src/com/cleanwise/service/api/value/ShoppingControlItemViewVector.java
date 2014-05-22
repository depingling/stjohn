
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ShoppingControlItemViewVector
 * Description:  Container object for ShoppingControlItemView objects
 * Purpose:      Provides container storage for ShoppingControlItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ShoppingControlItemViewVector</code>
 */
public class ShoppingControlItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 439363425096002091L;
    /**
     * Constructor.
     */
    public ShoppingControlItemViewVector () {}

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
      ShoppingControlItemView obj1 = (ShoppingControlItemView)o1;
      ShoppingControlItemView obj2 = (ShoppingControlItemView)o2;
      
      if("SkuNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getSkuNum();
        String i2 = obj2.getSkuNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ShortDesc".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getShortDesc();
        String i2 = obj2.getShortDesc();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
