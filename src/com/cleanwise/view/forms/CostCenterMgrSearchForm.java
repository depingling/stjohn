/**
 *  Title: CostCenterMgrSearchForm Description: This is the Struts ActionForm
 *  class for cost center management page. Purpose: Strut support to search 
 *  for cost centers.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     tbesser
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.text.SimpleDateFormat;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 *  Form bean for the user manager page.
 *
 *@author     tbesser
 *@created    August 27, 2001
 */
public final class CostCenterMgrSearchForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";


    /**
     *  <code>setSearchField</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchField(String pVal) {
        this._searchField = pVal;
    }


    /**
     *  <code>setSearchType</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchType(String pVal) {
        this._searchType = pVal;
    }


    /**
     *  <code>getSearchField</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getSearchField() {
        return (this._searchField);
    }


    /**
     *  <code>getSearchType</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getSearchType() {
        return (this._searchType);
    }


    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this._searchField = "";
        this._searchType = "";
    }


    /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // No validation necessary.
        return null;
    }

          /**
     *  Sets the BudgetPeriod attribute of the CostCenterMgrDetailForm object
     *
     *@param  mBudgetPeriod  The new BudgetPeriod value
     */
    public void setBudgetPeriod(String pBudgetPeriod) {
        this.mBudgetPeriod = pBudgetPeriod;
    }

    /**
     *  Gets the BudgetPeriod attribute of the CostCenterMgrDetailForm object
     *
     *@return    The BudgetPeriod value
     */
    public String getBudgetPeriod() {

        if (null == mBudgetPeriod)

            return "";

        return mBudgetPeriod;
    }
    
    private String mBudgetPeriod;

        private String mFiscalCalenderEffDate;
    public void setFiscalCalenderEffDate(String v) {
        this.mFiscalCalenderEffDate = v;
    }
    public void setFiscalCalenderEffDate(java.util.Date v) {
        if ( v == null ) v = new java.util.Date();
        SimpleDateFormat df = new  SimpleDateFormat("M/dd/yyyy");
        this.mFiscalCalenderEffDate = df.format(v);
    }

    public String getFiscalCalenderEffDate() {
        return this.mFiscalCalenderEffDate;
    }
    
    
}

