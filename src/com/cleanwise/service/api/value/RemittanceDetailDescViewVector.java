
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        RemittanceDetailDescViewVector
 * Description:  Container object for RemittanceDetailDescView objects
 * Purpose:      Provides container storage for RemittanceDetailDescView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>RemittanceDetailDescViewVector</code>
 */
public class RemittanceDetailDescViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2711684838980767237L;
    /**
     * Constructor.
     */
    public RemittanceDetailDescViewVector () {}

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
      RemittanceDetailDescView obj1 = (RemittanceDetailDescView)o1;
      RemittanceDetailDescView obj2 = (RemittanceDetailDescView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
