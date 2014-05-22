
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ProcessOutboundResultViewVector
 * Description:  Container object for ProcessOutboundResultView objects
 * Purpose:      Provides container storage for ProcessOutboundResultView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ProcessOutboundResultViewVector</code>
 */
public class ProcessOutboundResultViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -3505102189432225592L;
    /**
     * Constructor.
     */
    public ProcessOutboundResultViewVector () {}

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
      ProcessOutboundResultView obj1 = (ProcessOutboundResultView)o1;
      ProcessOutboundResultView obj2 = (ProcessOutboundResultView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
