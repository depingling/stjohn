
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        GroupSearchCriteriaViewVector
 * Description:  Container object for GroupSearchCriteriaView objects
 * Purpose:      Provides container storage for GroupSearchCriteriaView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>GroupSearchCriteriaViewVector</code>
 */
public class GroupSearchCriteriaViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -3262734297810683364L;
    /**
     * Constructor.
     */
    public GroupSearchCriteriaViewVector () {}

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
      GroupSearchCriteriaView obj1 = (GroupSearchCriteriaView)o1;
      GroupSearchCriteriaView obj2 = (GroupSearchCriteriaView)o2;
      
      if("GroupName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGroupName();
        String i2 = obj2.getGroupName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GroupType".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGroupType();
        String i2 = obj2.getGroupType();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GroupStatus".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getGroupStatus();
        String i2 = obj2.getGroupStatus();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("ReportName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getReportName();
        String i2 = obj2.getReportName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("UserName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getUserName();
        String i2 = obj2.getUserName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("GroupId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getGroupId();
        int i2 = obj2.getGroupId();
        retcode = i1-i2;
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
