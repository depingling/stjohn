
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AssetViewVector
 * Description:  Container object for AssetView objects
 * Purpose:      Provides container storage for AssetView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AssetViewVector</code>
 */
public class AssetViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 5367593785333432572L;
    /**
     * Constructor.
     */
    public AssetViewVector () {}

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
      AssetView obj1 = (AssetView)o1;
      AssetView obj2 = (AssetView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
