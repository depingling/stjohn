
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ManifestItemViewVector
 * Description:  Container object for ManifestItemView objects
 * Purpose:      Provides container storage for ManifestItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ManifestItemViewVector</code>
 */
public class ManifestItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2382143112459631854L;
    /**
     * Constructor.
     */
    public ManifestItemViewVector () {}

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
      ManifestItemView obj1 = (ManifestItemView)o1;
      ManifestItemView obj2 = (ManifestItemView)o2;
      
      if("DistributionCenterId".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDistributionCenterId();
        String i2 = obj2.getDistributionCenterId();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
