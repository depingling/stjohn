
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        NoteJoinViewVector
 * Description:  Container object for NoteJoinView objects
 * Purpose:      Provides container storage for NoteJoinView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>NoteJoinViewVector</code>
 */
public class NoteJoinViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -7892906659294476431L;
    /**
     * Constructor.
     */
    public NoteJoinViewVector () {}

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
      NoteJoinView obj1 = (NoteJoinView)o1;
      NoteJoinView obj2 = (NoteJoinView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
