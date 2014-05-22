
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        PaperPlusApplViewVector
 * Description:  Container object for PaperPlusApplView objects
 * Purpose:      Provides container storage for PaperPlusApplView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>PaperPlusApplViewVector</code>
 */
public class PaperPlusApplViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -424075551198901463L;
    /**
     * Constructor.
     */
    public PaperPlusApplViewVector () {}

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
      PaperPlusApplView obj1 = (PaperPlusApplView)o1;
      PaperPlusApplView obj2 = (PaperPlusApplView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
