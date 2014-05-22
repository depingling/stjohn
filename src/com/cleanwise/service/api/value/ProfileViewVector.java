
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ProfileViewVector
 * Description:  Container object for ProfileView objects
 * Purpose:      Provides container storage for ProfileView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ProfileViewVector</code>
 */
public class ProfileViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 7732322573191683354L;
    /**
     * Constructor.
     */
    public ProfileViewVector () {}

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
      ProfileView obj1 = (ProfileView)o1;
      ProfileView obj2 = (ProfileView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
