
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.*;

/**
 * Title:        FiscalPeriodViewVector
 * Description:  Container object for FiscalPeriodView objects
 * Purpose:      Provides container storage for FiscalPeriodView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>FiscalPeriodViewVector</code>
 */
public class FiscalPeriodViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -6371797713120576534L;
    /**
     * Constructor.
     */
    public FiscalPeriodViewVector () {}

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
      FiscalPeriodView obj1 = (FiscalPeriodView)o1;
      FiscalPeriodView obj2 = (FiscalPeriodView)o2;
      
      if("BusEntityId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBusEntityId();
        int i2 = obj2.getBusEntityId();
        retcode = i1-i2;
      }
      
      if("CurrentFiscalPeriod".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCurrentFiscalPeriod();
        int i2 = obj2.getCurrentFiscalPeriod();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
