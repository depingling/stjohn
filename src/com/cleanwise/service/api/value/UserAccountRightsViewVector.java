
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import com.cleanwise.service.api.value.*;

/**
 * Title:        UserAccountRightsViewVector
 * Description:  Container object for UserAccountRightsView objects
 * Purpose:      Provides container storage for UserAccountRightsView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>UserAccountRightsViewVector</code>
 */
public class UserAccountRightsViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 2075112097564652986L;
    /**
     * Constructor.
     */
    public UserAccountRightsViewVector () {}

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
      UserAccountRightsView obj1 = (UserAccountRightsView)o1;
      UserAccountRightsView obj2 = (UserAccountRightsView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
