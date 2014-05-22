
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        XpedxSiteViewVector
 * Description:  Container object for XpedxSiteView objects
 * Purpose:      Provides container storage for XpedxSiteView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>XpedxSiteViewVector</code>
 */
public class XpedxSiteViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 234563456123L;
    /**
     * Constructor.
     */
    public XpedxSiteViewVector () {}

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
      XpedxSiteView obj1 = (XpedxSiteView)o1;
      XpedxSiteView obj2 = (XpedxSiteView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
