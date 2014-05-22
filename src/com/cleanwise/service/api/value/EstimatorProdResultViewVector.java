
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        EstimatorProdResultViewVector
 * Description:  Container object for EstimatorProdResultView objects
 * Purpose:      Provides container storage for EstimatorProdResultView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EstimatorProdResultViewVector</code>
 */
public class EstimatorProdResultViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 8106110437954376818L;
    /**
     * Constructor.
     */
    public EstimatorProdResultViewVector () {}

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
      EstimatorProdResultView obj1 = (EstimatorProdResultView)o1;
      EstimatorProdResultView obj2 = (EstimatorProdResultView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
