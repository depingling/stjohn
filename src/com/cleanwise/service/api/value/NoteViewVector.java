
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;import java.util.Date;import java.util.ArrayList;

/**
 * Title:        NoteViewVector
 * Description:  Container object for NoteView objects
 * Purpose:      Provides container storage for NoteView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>NoteViewVector</code>
 */
public class NoteViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -3002478249861646237L;
    /**
     * Constructor.
     */
    public NoteViewVector () {}

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
      NoteView obj1 = (NoteView)o1;
      NoteView obj2 = (NoteView)o2;
      
      if("NoteId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getNoteId();
        int i2 = obj2.getNoteId();
        retcode = i1-i2;
      }
      
      if("PropertyId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getPropertyId();
        int i2 = obj2.getPropertyId();
        retcode = i1-i2;
      }
      
      if("BusEntityId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBusEntityId();
        int i2 = obj2.getBusEntityId();
        retcode = i1-i2;
      }
      
      if("Topic".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getTopic();
        String i2 = obj2.getTopic();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Title".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getTitle();
        String i2 = obj2.getTitle();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ModDate".equalsIgnoreCase(_sortField)) {
        Date i1 = obj1.getModDate();
        Date i2 = obj2.getModDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("SearchRate".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getSearchRate();
        int i2 = obj2.getSearchRate();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
