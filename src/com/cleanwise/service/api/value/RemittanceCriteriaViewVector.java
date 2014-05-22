
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        RemittanceCriteriaViewVector
 * Description:  Container object for RemittanceCriteriaView objects
 * Purpose:      Provides container storage for RemittanceCriteriaView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>RemittanceCriteriaViewVector</code>
 */
public class RemittanceCriteriaViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2153931358145574688L;
    /**
     * Constructor.
     */
    public RemittanceCriteriaViewVector () {}

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
      RemittanceCriteriaView obj1 = (RemittanceCriteriaView)o1;
      RemittanceCriteriaView obj2 = (RemittanceCriteriaView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
