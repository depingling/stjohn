
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ServiceViewVector
 * Description:  Container object for ServiceView objects
 * Purpose:      Provides container storage for ServiceView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ServiceViewVector</code>
 */
public class ServiceViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 3962688975343201545L;
    /**
     * Constructor.
     */
    public ServiceViewVector () {}

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
      ServiceView obj1 = (ServiceView)o1;
      ServiceView obj2 = (ServiceView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
