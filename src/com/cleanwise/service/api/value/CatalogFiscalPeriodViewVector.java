
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.*;

/**
 * Title:        CatalogFiscalPeriodViewVector
 * Description:  Container object for CatalogFiscalPeriodView objects
 * Purpose:      Provides container storage for CatalogFiscalPeriodView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CatalogFiscalPeriodViewVector</code>
 */
public class CatalogFiscalPeriodViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 4776389824839354919L;
    /**
     * Constructor.
     */
    public CatalogFiscalPeriodViewVector () {}

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
      CatalogFiscalPeriodView obj1 = (CatalogFiscalPeriodView)o1;
      CatalogFiscalPeriodView obj2 = (CatalogFiscalPeriodView)o2;
      
      if("CurrentFiscalPeriod".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCurrentFiscalPeriod();
        int i2 = obj2.getCurrentFiscalPeriod();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
