/*
 * StoreVendorInvoiceSearchForm.java
 *
 * Created on July 1, 2005, 1:52 PM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.AccountDataVector;
import com.cleanwise.service.api.value.DistributorDataVector;

/**
 *
 * @author bstevens
 */
public class StoreVendorInvoiceSearchForm extends PurchaseOrderOpSearchForm{
    /**
     * Holds value of property accountFilter.
     */
    private AccountDataVector accountFilter;

    /**
     * Holds value of property distributorFilter.
     */
    private DistributorDataVector distributorFilter;

    private int resultCount;
    /**
     * Getter for property accountFilter.
     * @return Value of property accountFilter.
     */
    public AccountDataVector getAccountFilter() {

        return this.accountFilter;
    }

    /**
     * Setter for property accountFilter.
     * @param accountFilter New value of property accountFilter.
     */
    public void setAccountFilter(AccountDataVector accountFilter) {

        this.accountFilter = accountFilter;
    }

    /**
     * Getter for property distributorFilter.
     * @return Value of property distributorFilter.
     */
    public DistributorDataVector getDistributorFilter() {

        return this.distributorFilter;
    }

    /**
     * Setter for property distributorFilter.
     * @param distributorFilter New value of property distributorFilter.
     */
    public void setDistributorFilter(DistributorDataVector distributorFilter) {

        this.distributorFilter = distributorFilter;
    }

    public int getResultCount(){
    	return this.resultCount;
    }
    
    public void setResultCount(int pVal){
    	this.resultCount = pVal;
    }
}
