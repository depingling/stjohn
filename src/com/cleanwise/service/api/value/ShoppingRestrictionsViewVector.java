
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ShoppingRestrictionsViewVector
 * Description:  Container object for ShoppingRestrictionsView objects
 * Purpose:      Provides container storage for ShoppingRestrictionsView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ShoppingRestrictionsViewVector</code>
 */
public class ShoppingRestrictionsViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5812347234230038487L;
    /**
     * Constructor.
     */
    public ShoppingRestrictionsViewVector () {}

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
      ShoppingRestrictionsView obj1 = (ShoppingRestrictionsView)o1;
      ShoppingRestrictionsView obj2 = (ShoppingRestrictionsView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
