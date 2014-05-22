
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        PairViewVector
 * Description:  Container object for PairView objects
 * Purpose:      Provides container storage for PairView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>PairViewVector</code>
 */
public class PairViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2351599870343067519L;
    /**
     * Constructor.
     */
    public PairViewVector () {}

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
      PairView obj1 = (PairView)o1;
      PairView obj2 = (PairView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
