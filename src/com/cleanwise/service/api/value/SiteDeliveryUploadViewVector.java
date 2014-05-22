
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        SiteDeliveryUploadViewVector
 * Description:  Container object for SiteDeliveryUploadView objects
 * Purpose:      Provides container storage for SiteDeliveryUploadView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>SiteDeliveryUploadViewVector</code>
 */
public class SiteDeliveryUploadViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 1L;
    /**
     * Constructor.
     */
    public SiteDeliveryUploadViewVector () {}

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
      SiteDeliveryUploadView obj1 = (SiteDeliveryUploadView)o1;
      SiteDeliveryUploadView obj2 = (SiteDeliveryUploadView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
