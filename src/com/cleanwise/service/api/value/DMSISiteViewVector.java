
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        DMSISiteViewVector
 * Description:  Container object for DMSISiteView objects
 * Purpose:      Provides container storage for DMSISiteView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>DMSISiteViewVector</code>
 */
public class DMSISiteViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 234563456L;
    /**
     * Constructor.
     */
    public DMSISiteViewVector () {}

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
      DMSISiteView obj1 = (DMSISiteView)o1;
      DMSISiteView obj2 = (DMSISiteView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
