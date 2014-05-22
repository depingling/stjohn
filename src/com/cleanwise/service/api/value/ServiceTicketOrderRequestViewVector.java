
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ServiceTicketOrderRequestViewVector
 * Description:  Container object for ServiceTicketOrderRequestView objects
 * Purpose:      Provides container storage for ServiceTicketOrderRequestView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 * @version      1.0
 */

/**
 * <code>ServiceTicketOrderRequestViewVector</code>
 */
public class ServiceTicketOrderRequestViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public ServiceTicketOrderRequestViewVector () {}

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
      ServiceTicketOrderRequestView obj1 = (ServiceTicketOrderRequestView)o1;
      ServiceTicketOrderRequestView obj2 = (ServiceTicketOrderRequestView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
