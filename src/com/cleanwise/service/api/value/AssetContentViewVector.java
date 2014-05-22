
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AssetContentViewVector
 * Description:  Container object for AssetContentView objects
 * Purpose:      Provides container storage for AssetContentView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AssetContentViewVector</code>
 */
public class AssetContentViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 1906843915857321144L;
    /**
     * Constructor.
     */
    public AssetContentViewVector () {}

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
      AssetContentView obj1 = (AssetContentView)o1;
      AssetContentView obj2 = (AssetContentView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
