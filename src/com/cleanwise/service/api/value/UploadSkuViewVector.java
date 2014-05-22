
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        UploadSkuViewVector
 * Description:  Container object for UploadSkuView objects
 * Purpose:      Provides container storage for UploadSkuView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>UploadSkuViewVector</code>
 */
public class UploadSkuViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1845473136751585997L;
    /**
     * Constructor.
     */
    public UploadSkuViewVector () {}

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
      UploadSkuView obj1 = (UploadSkuView)o1;
      UploadSkuView obj2 = (UploadSkuView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
