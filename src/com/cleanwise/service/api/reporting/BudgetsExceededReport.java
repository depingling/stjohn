/*
 * BudgetsExceededReport.java
 *
 * Created on April 27, 2005, 11:02 AM
 */

package com.cleanwise.service.api.reporting;

/**
 * Shell class that configures the account budget report.  All logic is in the super class implementation
 * @author bstevens
 */
public class BudgetsExceededReport extends BudgetReport{
   protected int getType(){
        return ACCOUNT_TYPE;
    }
   protected int getRenderType(){
        return BUDGETS_EXCEEDED_REPORT;
    }
}
