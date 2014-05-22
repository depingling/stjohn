
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        EBagIn101ComponentViewVector
 * Description:  Container object for EBagIn101ComponentView objects
 * Purpose:      Provides container storage for EBagIn101ComponentView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EBagIn101ComponentViewVector</code>
 */
public class EBagIn101ComponentViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2409899076407133310L;

    /**
     * Constructor.
     */
    public EBagIn101ComponentViewVector () {}

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
      EBagIn101ComponentView obj1 = (EBagIn101ComponentView)o1;
      EBagIn101ComponentView obj2 = (EBagIn101ComponentView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
