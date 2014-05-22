
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        FlatOrderGuideRequestViewVector
 * Description:  Container object for FlatOrderGuideRequestView objects
 * Purpose:      Provides container storage for FlatOrderGuideRequestView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>FlatOrderGuideRequestViewVector</code>
 */
public class FlatOrderGuideRequestViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -9182154641537680046L;
    /**
     * Constructor.
     */
    public FlatOrderGuideRequestViewVector () {}

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
      FlatOrderGuideRequestView obj1 = (FlatOrderGuideRequestView)o1;
      FlatOrderGuideRequestView obj2 = (FlatOrderGuideRequestView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
