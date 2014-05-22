
/* DO NOT EDIT - Generated code from XSL file ValueObjectVector.xsl */

package com.cleanwise.service.api.value;
import java.util.Comparator;
import java.util.Collections;
import java.math.BigDecimal;

/**
 * Title:        BudgetSpendViewVector
 * Description:  Container object for BudgetSpendView objects
 * Purpose:      Provides container storage for BudgetSpendView objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObjectVector.xsl
 */

/**
 * <code>BudgetSpendViewVector</code>
 */
public class BudgetSpendViewVector extends java.util.ArrayList implements Comparator
{
    private static final long serialVersionUID = -7957662992729852735L;
    /**
     * Constructor.
     */
    public BudgetSpendViewVector () {}

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
      BudgetSpendView obj1 = (BudgetSpendView)o1;
      BudgetSpendView obj2 = (BudgetSpendView)o2;
      
      if("BusEntityId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getBusEntityId();
        int i2 = obj2.getBusEntityId();
        retcode = i1-i2;
      }
      
      if("BusEntityName".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBusEntityName();
        String i2 = obj2.getBusEntityName();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("City".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCity();
        String i2 = obj2.getCity();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("State".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getState();
        String i2 = obj2.getState();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("PostalCode".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getPostalCode();
        String i2 = obj2.getPostalCode();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BusEntityTypeCd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBusEntityTypeCd();
        String i2 = obj2.getBusEntityTypeCd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CostCenterId".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCostCenterId();
        int i2 = obj2.getCostCenterId();
        retcode = i1-i2;
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
      
      if("CurrentBudgetPeriod".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCurrentBudgetPeriod();
        int i2 = obj2.getCurrentBudgetPeriod();
        retcode = i1-i2;
      }
      
      if("CurrentBudgetYear".equalsIgnoreCase(_sortField)) {
        int i1 = obj1.getCurrentBudgetYear();
        int i2 = obj2.getCurrentBudgetYear();
        retcode = i1-i2;
      }
      
      if("CurrentBudgetPeriodStart".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCurrentBudgetPeriodStart();
        String i2 = obj2.getCurrentBudgetPeriodStart();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("CurrentBudgetPeriodEnd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getCurrentBudgetPeriodEnd();
        String i2 = obj2.getCurrentBudgetPeriodEnd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BudgetPeriodStartDate".equalsIgnoreCase(_sortField)) {
        java.sql.Date i1 = obj1.getBudgetPeriodStartDate();
        java.sql.Date i2 = obj2.getBudgetPeriodStartDate();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BudgetPeriodStart".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBudgetPeriodStart();
        String i2 = obj2.getBudgetPeriodStart();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
      }
      
      if("BudgetPeriodEnd".equalsIgnoreCase(_sortField)) {
        String i1 = obj1.getBudgetPeriodEnd();
        String i2 = obj2.getBudgetPeriodEnd();
        if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
        else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
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
