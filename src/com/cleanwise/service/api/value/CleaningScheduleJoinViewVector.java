
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        CleaningScheduleJoinViewVector
 * Description:  Container object for CleaningScheduleJoinView objects
 * Purpose:      Provides container storage for CleaningScheduleJoinView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CleaningScheduleJoinViewVector</code>
 */
public class CleaningScheduleJoinViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -7729077863815357483L;
    /**
     * Constructor.
     */
    public CleaningScheduleJoinViewVector () {}

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
      CleaningScheduleJoinView obj1 = (CleaningScheduleJoinView)o1;
      CleaningScheduleJoinView obj2 = (CleaningScheduleJoinView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
