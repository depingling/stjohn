
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        JciRebateViewVector
 * Description:  Container object for JciRebateView objects
 * Purpose:      Provides container storage for JciRebateView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>JciRebateViewVector</code>
 */
public class JciRebateViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -1784628995977312533L;
    /**
     * Constructor.
     */
    public JciRebateViewVector () {}

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
      JciRebateView obj1 = (JciRebateView)o1;
      JciRebateView obj2 = (JciRebateView)o2;
      
      if("GroupId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getGroupId();
        int i2 = obj2.getGroupId();
        retcode = i1-i2;
      }
      
      if("AccountErpNum".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAccountErpNum();
        String i2 = obj2.getAccountErpNum();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AccountId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getAccountId();
        int i2 = obj2.getAccountId();
        retcode = i1-i2;
      }
      
      if("AccountName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getAccountName();
        String i2 = obj2.getAccountName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
