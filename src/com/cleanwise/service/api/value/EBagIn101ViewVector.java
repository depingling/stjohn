
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        EBagIn101ViewVector
 * Description:  Container object for EBagIn101View objects
 * Purpose:      Provides container storage for EBagIn101View objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EBagIn101ViewVector</code>
 */
public class EBagIn101ViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 510515462621081768L;

    /**
     * Constructor.
     */
    public EBagIn101ViewVector () {}

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
      EBagIn101View obj1 = (EBagIn101View)o1;
      EBagIn101View obj2 = (EBagIn101View)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
