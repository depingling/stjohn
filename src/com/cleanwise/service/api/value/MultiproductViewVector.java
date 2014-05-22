
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        MultiproductViewVector
 * Description:  Container object for MultiproductView objects
 * Purpose:      Provides container storage for MultiproductView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>MultiproductViewVector</code>
 */
public class MultiproductViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7959270292586859295L;
    /**
     * Constructor.
     */
    public MultiproductViewVector () {}

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
      MultiproductView obj1 = (MultiproductView)o1;
      MultiproductView obj2 = (MultiproductView)o2;
      
      if("MultiproductId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getMultiproductId();
        int i2 = obj2.getMultiproductId();
        retcode = i1-i2;
      }
      
      if("MultiproductName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getMultiproductName();
        String i2 = obj2.getMultiproductName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CatalogId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCatalogId();
        int i2 = obj2.getCatalogId();
        retcode = i1-i2;
      }
      
      if("CatalogName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCatalogName();
        String i2 = obj2.getCatalogName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
