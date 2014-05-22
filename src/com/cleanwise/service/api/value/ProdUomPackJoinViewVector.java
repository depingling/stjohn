
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ProdUomPackJoinViewVector
 * Description:  Container object for ProdUomPackJoinView objects
 * Purpose:      Provides container storage for ProdUomPackJoinView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ProdUomPackJoinViewVector</code>
 */
public class ProdUomPackJoinViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2039977006955908430L;
    /**
     * Constructor.
     */
    public ProdUomPackJoinViewVector () {}

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
      ProdUomPackJoinView obj1 = (ProdUomPackJoinView)o1;
      ProdUomPackJoinView obj2 = (ProdUomPackJoinView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
