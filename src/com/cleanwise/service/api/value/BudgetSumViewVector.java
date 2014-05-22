
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        BudgetSumViewVector
 * Description:  Container object for BudgetSumView objects
 * Purpose:      Provides container storage for BudgetSumView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>BudgetSumViewVector</code>
 */
public class BudgetSumViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 3327006092789462869L;
    /**
     * Constructor.
     */
    public BudgetSumViewVector () {}

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
      BudgetSumView obj1 = (BudgetSumView)o1;
      BudgetSumView obj2 = (BudgetSumView)o2;
      
      if("YtdAmountSpent".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getYtdAmountSpent();
        BigDecimal i2 = obj2.getYtdAmountSpent();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("YtdAmountAllocated".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getYtdAmountAllocated();
        BigDecimal i2 = obj2.getYtdAmountAllocated();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("YtdDifference".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getYtdDifference();
        BigDecimal i2 = obj2.getYtdDifference();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AnnualAmountSpent".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAnnualAmountSpent();
        BigDecimal i2 = obj2.getAnnualAmountSpent();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AnnualAmountAllocated".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAnnualAmountAllocated();
        BigDecimal i2 = obj2.getAnnualAmountAllocated();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AnnualDifference".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAnnualDifference();
        BigDecimal i2 = obj2.getAnnualDifference();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
