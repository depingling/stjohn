
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        TradingPartnerFullDescViewVector
 * Description:  Container object for TradingPartnerFullDescView objects
 * Purpose:      Provides container storage for TradingPartnerFullDescView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>TradingPartnerFullDescViewVector</code>
 */
public class TradingPartnerFullDescViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -2404786874068001471L;
    /**
     * Constructor.
     */
    public TradingPartnerFullDescViewVector () {}

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
      TradingPartnerFullDescView obj1 = (TradingPartnerFullDescView)o1;
      TradingPartnerFullDescView obj2 = (TradingPartnerFullDescView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
