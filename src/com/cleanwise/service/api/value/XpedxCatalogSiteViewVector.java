
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        XpedxCatalogSiteViewVector
 * Description:  Container object for XpedxCatalogSiteView objects
 * Purpose:      Provides container storage for XpedxCatalogSiteView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>XpedxCatalogSiteViewVector</code>
 */
public class XpedxCatalogSiteViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public XpedxCatalogSiteViewVector () {}

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
      XpedxCatalogSiteView obj1 = (XpedxCatalogSiteView)o1;
      XpedxCatalogSiteView obj2 = (XpedxCatalogSiteView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
