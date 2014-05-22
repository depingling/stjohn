
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        TaskDetailViewVector
 * Description:  Container object for TaskDetailView objects
 * Purpose:      Provides container storage for TaskDetailView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>TaskDetailViewVector</code>
 */
public class TaskDetailViewVector extends java.util.ArrayList implements Comparator
{
    /**
     * Constructor.
     */
    public TaskDetailViewVector () {}

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
      TaskDetailView obj1 = (TaskDetailView)o1;
      TaskDetailView obj2 = (TaskDetailView)o2;
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
