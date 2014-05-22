
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ServiceDetailViewVector
 * Description:  Container object for ServiceDetailView objects
 * Purpose:      Provides container storage for ServiceDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ServiceDetailViewVector</code>
 */
public class ServiceDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -3531657281542707260L;
    /**
     * Constructor.
     */
    public ServiceDetailViewVector () {}

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
      ServiceDetailView obj1 = (ServiceDetailView)o1;
      ServiceDetailView obj2 = (ServiceDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
