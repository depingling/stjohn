
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;


/**
 * Title:        WorkflowRuleJoinViewVector
 * Description:  Container object for WorkflowRuleJoinView objects
 * Purpose:      Provides container storage for WorkflowRuleJoinView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>WorkflowRuleJoinViewVector</code>
 */
public class WorkflowRuleJoinViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -3231026523404086065L;
    /**
     * Constructor.
     */
    public WorkflowRuleJoinViewVector () {}

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
      WorkflowRuleJoinView obj1 = (WorkflowRuleJoinView)o1;
      WorkflowRuleJoinView obj2 = (WorkflowRuleJoinView)o2;
      
      if("WorkflowId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getWorkflowId();
        int i2 = obj2.getWorkflowId();
        retcode = i1-i2;
      }
      
      if("RuleSeq".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getRuleSeq();
        int i2 = obj2.getRuleSeq();
        retcode = i1-i2;
      }
      
      if("RuleTypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getRuleTypeCd();
        String i2 = obj2.getRuleTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
