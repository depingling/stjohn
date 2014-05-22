/*
 * AccountBudgetReport.java
 *
 * Created on April 27, 2005, 10:21 AM
 */

package com.cleanwise.service.api.reporting;

/**
 * Shell class that configures the account budget report.  All logic is in the super class implementation
 * @author bstevens
 */
public class AccountBudgetNoBSCReport extends BudgetReport{
   protected int getType(){
        return ACCOUNT_TYPE;
    }
   protected int getRenderType(){
        return ACCOUNT_BUDGET_REPORT;
    }
	protected boolean getRenderBSCTab(){
	   return false;
	}
}
