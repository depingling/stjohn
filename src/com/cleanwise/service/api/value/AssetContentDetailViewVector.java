
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AssetContentDetailViewVector
 * Description:  Container object for AssetContentDetailView objects
 * Purpose:      Provides container storage for AssetContentDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AssetContentDetailViewVector</code>
 */
public class AssetContentDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 6453908694896172090L;
    /**
     * Constructor.
     */
    public AssetContentDetailViewVector () {}

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
      AssetContentDetailView obj1 = (AssetContentDetailView)o1;
      AssetContentDetailView obj2 = (AssetContentDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
