
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        EdiInp856ViewVector
 * Description:  Container object for EdiInp856View objects
 * Purpose:      Provides container storage for EdiInp856View objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EdiInp856ViewVector</code>
 */
public class EdiInp856ViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1122865400554567643L;
    /**
     * Constructor.
     */
    public EdiInp856ViewVector () {}

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
      EdiInp856View obj1 = (EdiInp856View)o1;
      EdiInp856View obj2 = (EdiInp856View)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
