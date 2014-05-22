
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        EasternBagItemViewVector
 * Description:  Container object for EasternBagItemView objects
 * Purpose:      Provides container storage for EasternBagItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EasternBagItemViewVector</code>
 */
public class EasternBagItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public EasternBagItemViewVector () {}

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
      EasternBagItemView obj1 = (EasternBagItemView)o1;
      EasternBagItemView obj2 = (EasternBagItemView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
