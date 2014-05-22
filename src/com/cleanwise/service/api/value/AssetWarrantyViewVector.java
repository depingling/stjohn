
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AssetWarrantyViewVector
 * Description:  Container object for AssetWarrantyView objects
 * Purpose:      Provides container storage for AssetWarrantyView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AssetWarrantyViewVector</code>
 */
public class AssetWarrantyViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 3806036825388450308L;
    /**
     * Constructor.
     */
    public AssetWarrantyViewVector () {}

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
      AssetWarrantyView obj1 = (AssetWarrantyView)o1;
      AssetWarrantyView obj2 = (AssetWarrantyView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
