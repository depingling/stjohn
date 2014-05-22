
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        EmailNotificationViewVector
 * Description:  Container object for EmailNotificationView objects
 * Purpose:      Provides container storage for EmailNotificationView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>EmailNotificationViewVector</code>
 */
public class EmailNotificationViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1L;
    /**
     * Constructor.
     */
    public EmailNotificationViewVector () {}

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
      EmailNotificationView obj1 = (EmailNotificationView)o1;
      EmailNotificationView obj2 = (EmailNotificationView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
