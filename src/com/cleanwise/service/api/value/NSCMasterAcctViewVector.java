
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        NSCMasterAcctViewVector
 * Description:  Container object for NSCMasterAcctView objects
 * Purpose:      Provides container storage for NSCMasterAcctView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>NSCMasterAcctViewVector</code>
 */
public class NSCMasterAcctViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public NSCMasterAcctViewVector () {}

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
      NSCMasterAcctView obj1 = (NSCMasterAcctView)o1;
      NSCMasterAcctView obj2 = (NSCMasterAcctView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
