
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        AddressInfoViewVector
 * Description:  Container object for AddressInfoView objects
 * Purpose:      Provides container storage for AddressInfoView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>AddressInfoViewVector</code>
 */
public class AddressInfoViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -5822776367163232901L;
    /**
     * Constructor.
     */
    public AddressInfoViewVector () {}

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
      AddressInfoView obj1 = (AddressInfoView)o1;
      AddressInfoView obj2 = (AddressInfoView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
