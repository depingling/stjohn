
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        CatalogContractViewVector
 * Description:  Container object for CatalogContractView objects
 * Purpose:      Provides container storage for CatalogContractView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>CatalogContractViewVector</code>
 */
public class CatalogContractViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 1539143994675232438L;
    /**
     * Constructor.
     */
    public CatalogContractViewVector () {}

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
      CatalogContractView obj1 = (CatalogContractView)o1;
      CatalogContractView obj2 = (CatalogContractView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
