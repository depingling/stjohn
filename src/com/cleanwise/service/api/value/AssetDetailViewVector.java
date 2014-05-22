
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AssetDetailViewVector
 * Description:  Container object for AssetDetailView objects
 * Purpose:      Provides container storage for AssetDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AssetDetailViewVector</code>
 */
public class AssetDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 6082937581638258929L;
    /**
     * Constructor.
     */
    public AssetDetailViewVector () {}

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
      AssetDetailView obj1 = (AssetDetailView)o1;
      AssetDetailView obj2 = (AssetDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
