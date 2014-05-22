
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        BudgetSpentAmountViewVector
 * Description:  Container object for BudgetSpentAmountView objects
 * Purpose:      Provides container storage for BudgetSpentAmountView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>BudgetSpentAmountViewVector</code>
 */
public class BudgetSpentAmountViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 
            -1L
        ;
    /**
     * Constructor.
     */
    public BudgetSpentAmountViewVector () {}

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
      BudgetSpentAmountView obj1 = (BudgetSpentAmountView)o1;
      BudgetSpentAmountView obj2 = (BudgetSpentAmountView)o2;
      
      if("AmountSpent".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAmountSpent();
        BigDecimal i2 = obj2.getAmountSpent();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AmountAllocated".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAmountAllocated();
        BigDecimal i2 = obj2.getAmountAllocated();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AmountDifference".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAmountDifference();
        BigDecimal i2 = obj2.getAmountDifference();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("Amount".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAmount();
        BigDecimal i2 = obj2.getAmount();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("AmountTotal".equalsIgnoreCase(_sortField)) {
        BigDecimal i1 = obj1.getAmountTotal();
        BigDecimal i2 = obj2.getAmountTotal();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
