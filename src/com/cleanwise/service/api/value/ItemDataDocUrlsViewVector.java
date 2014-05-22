
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ItemDataDocUrlsViewVector
 * Description:  Container object for ItemDataDocUrlsView objects
 * Purpose:      Provides container storage for ItemDataDocUrlsView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ItemDataDocUrlsViewVector</code>
 */
public class ItemDataDocUrlsViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -8923077835807688705L;
    /**
     * Constructor.
     */
    public ItemDataDocUrlsViewVector () {}

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
      ItemDataDocUrlsView obj1 = (ItemDataDocUrlsView)o1;
      ItemDataDocUrlsView obj2 = (ItemDataDocUrlsView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
