
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        EdiInp856ItemViewVector
 * Description:  Container object for EdiInp856ItemView objects
 * Purpose:      Provides container storage for EdiInp856ItemView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EdiInp856ItemViewVector</code>
 */
public class EdiInp856ItemViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -391012127853493990L;
    /**
     * Constructor.
     */
    public EdiInp856ItemViewVector () {}

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
      EdiInp856ItemView obj1 = (EdiInp856ItemView)o1;
      EdiInp856ItemView obj2 = (EdiInp856ItemView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
