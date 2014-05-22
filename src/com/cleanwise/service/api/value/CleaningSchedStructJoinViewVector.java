
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        CleaningSchedStructJoinViewVector
 * Description:  Container object for CleaningSchedStructJoinView objects
 * Purpose:      Provides container storage for CleaningSchedStructJoinView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CleaningSchedStructJoinViewVector</code>
 */
public class CleaningSchedStructJoinViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 4315827549383193714L;
    /**
     * Constructor.
     */
    public CleaningSchedStructJoinViewVector () {}

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
      CleaningSchedStructJoinView obj1 = (CleaningSchedStructJoinView)o1;
      CleaningSchedStructJoinView obj2 = (CleaningSchedStructJoinView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
