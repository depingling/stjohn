
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        BudgetSpentShortViewVector
 * Description:  Container object for BudgetSpentShortView objects
 * Purpose:      Provides container storage for BudgetSpentShortView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>BudgetSpentShortViewVector</code>
 */
public class BudgetSpentShortViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = 
            -1L
        ;
    /**
     * Constructor.
     */
    public BudgetSpentShortViewVector () {}

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
      BudgetSpentShortView obj1 = (BudgetSpentShortView)o1;
      BudgetSpentShortView obj2 = (BudgetSpentShortView)o2;
      
      if("BusEntityId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBusEntityId();
        int i2 = obj2.getBusEntityId();
        retcode = i1-i2;
      }
      
      if("CostCenterId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCostCenterId();
        int i2 = obj2.getCostCenterId();
        retcode = i1-i2;
      }
      
      if("BudgetTypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBudgetTypeCd();
        String i2 = obj2.getBudgetTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BudgetYear".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBudgetYear();
        int i2 = obj2.getBudgetYear();
        retcode = i1-i2;
      }
      
      if("BudgetPeriod".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBudgetPeriod();
        int i2 = obj2.getBudgetPeriod();
        retcode = i1-i2;
      }
      
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
      
      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
}
