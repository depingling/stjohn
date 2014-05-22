
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.*;

/**
 * Title:        FreightHandlerViewVector
 * Description:  Container object for FreightHandlerView objects
 * Purpose:      Provides container storage for FreightHandlerView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>FreightHandlerViewVector</code>
 */
public class FreightHandlerViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -3321374540952498350L;
    /**
     * Constructor.
     */
    public FreightHandlerViewVector () {}

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
      FreightHandlerView obj1 = (FreightHandlerView)o1;
      FreightHandlerView obj2 = (FreightHandlerView)o2;
      
      if("EdiRoutingCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getEdiRoutingCd();
        String i2 = obj2.getEdiRoutingCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AcceptFreightOnInvoice".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAcceptFreightOnInvoice();
        String i2 = obj2.getAcceptFreightOnInvoice();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
