
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ShipToViewVector
 * Description:  Container object for ShipToView objects
 * Purpose:      Provides container storage for ShipToView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ShipToViewVector</code>
 */
public class ShipToViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2349918897400684691L;
    /**
     * Constructor.
     */
    public ShipToViewVector () {}

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
      ShipToView obj1 = (ShipToView)o1;
      ShipToView obj2 = (ShipToView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
