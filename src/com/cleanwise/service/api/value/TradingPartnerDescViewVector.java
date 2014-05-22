
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        TradingPartnerDescViewVector
 * Description:  Container object for TradingPartnerDescView objects
 * Purpose:      Provides container storage for TradingPartnerDescView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>TradingPartnerDescViewVector</code>
 */
public class TradingPartnerDescViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2404786874068001470L;
    /**
     * Constructor.
     */
    public TradingPartnerDescViewVector () {}

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
      TradingPartnerDescView obj1 = (TradingPartnerDescView)o1;
      TradingPartnerDescView obj2 = (TradingPartnerDescView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
