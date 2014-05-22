
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        EmailViewVector
 * Description:  Container object for EmailView objects
 * Purpose:      Provides container storage for EmailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EmailViewVector</code>
 */
public class EmailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public EmailViewVector () {}

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
      EmailView obj1 = (EmailView)o1;
      EmailView obj2 = (EmailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
