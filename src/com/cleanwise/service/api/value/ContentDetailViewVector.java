
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        ContentDetailViewVector
 * Description:  Container object for ContentDetailView objects
 * Purpose:      Provides container storage for ContentDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>ContentDetailViewVector</code>
 */
public class ContentDetailViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -8640447389483797185L;
    /**
     * Constructor.
     */
    public ContentDetailViewVector () {}

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
      ContentDetailView obj1 = (ContentDetailView)o1;
      ContentDetailView obj2 = (ContentDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
