
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        GenericReportControlViewVector
 * Description:  Container object for GenericReportControlView objects
 * Purpose:      Provides container storage for GenericReportControlView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>GenericReportControlViewVector</code>
 */
public class GenericReportControlViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 6100245409346161227L;
    /**
     * Constructor.
     */
    public GenericReportControlViewVector () {}

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
      GenericReportControlView obj1 = (GenericReportControlView)o1;
      GenericReportControlView obj2 = (GenericReportControlView)o2;
      
      if("Default".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getDefault();
        String i2 = obj2.getDefault();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Value".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getValue();
        String i2 = obj2.getValue();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
