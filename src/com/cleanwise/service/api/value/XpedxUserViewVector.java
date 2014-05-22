
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        XpedxUserViewVector
 * Description:  Container object for XpedxUserView objects
 * Purpose:      Provides container storage for XpedxUserView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>XpedxUserViewVector</code>
 */
public class XpedxUserViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 
            -2L
        ;
    /**
     * Constructor.
     */
    public XpedxUserViewVector () {}

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
      XpedxUserView obj1 = (XpedxUserView)o1;
      XpedxUserView obj2 = (XpedxUserView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
