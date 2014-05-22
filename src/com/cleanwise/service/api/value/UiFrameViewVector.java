
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        UiFrameViewVector
 * Description:  Container object for UiFrameView objects
 * Purpose:      Provides container storage for UiFrameView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>UiFrameViewVector</code>
 */
public class UiFrameViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 
            -3L
        ;
    /**
     * Constructor.
     */
    public UiFrameViewVector () {}

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
      UiFrameView obj1 = (UiFrameView)o1;
      UiFrameView obj2 = (UiFrameView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
