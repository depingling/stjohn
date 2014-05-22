
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AccountUIViewVector
 * Description:  Container object for AccountUIView objects
 * Purpose:      Provides container storage for AccountUIView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AccountUIViewVector</code>
 */
public class AccountUIViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 8107546316445778014L;
    /**
     * Constructor.
     */
    public AccountUIViewVector () {}

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
      AccountUIView obj1 = (AccountUIView)o1;
      AccountUIView obj2 = (AccountUIView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
