
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        MenuItemDescriptorViewVector
 * Description:  Container object for MenuItemDescriptorView objects
 * Purpose:      Provides container storage for MenuItemDescriptorView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>MenuItemDescriptorViewVector</code>
 */
public class MenuItemDescriptorViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 
            -1L
        ;
    /**
     * Constructor.
     */
    public MenuItemDescriptorViewVector () {}

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
      MenuItemDescriptorView obj1 = (MenuItemDescriptorView)o1;
      MenuItemDescriptorView obj2 = (MenuItemDescriptorView)o2;
      
      if("TreeLevel".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getTreeLevel();
        int i2 = obj2.getTreeLevel();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
