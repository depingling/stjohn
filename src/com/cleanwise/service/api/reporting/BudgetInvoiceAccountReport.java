package com.cleanwise.service.api.reporting;

public class BudgetInvoiceAccountReport extends BudgetInvoiceReport{
    /**
     * Method to be overidden to detemine how this report runs
     */
    protected int getMode(){
        return ACCOUNT_MODE;
    }
}
