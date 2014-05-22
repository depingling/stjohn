
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        XpedxCatalogItemViewVector
 * Description:  Container object for XpedxCatalogItemView objects
 * Purpose:      Provides container storage for XpedxCatalogItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>XpedxCatalogItemViewVector</code>
 */
public class XpedxCatalogItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public XpedxCatalogItemViewVector () {}

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
      XpedxCatalogItemView obj1 = (XpedxCatalogItemView)o1;
      XpedxCatalogItemView obj2 = (XpedxCatalogItemView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
