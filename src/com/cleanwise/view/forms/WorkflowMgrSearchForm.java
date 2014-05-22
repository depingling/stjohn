/**
 *  Title: WorkflowMgrSearchForm Description: This is the Struts ActionForm
 *  class for cost center management page. Purpose: Strut support to search 
 *  for cost centers.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     tbesser
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *  Form bean for the user manager page.
 *
 *@author     tbesser
 *@created    August 27, 2001
 */
public final class WorkflowMgrSearchForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";

    private String mBusEntityIdS = new String("");
    private String mSearchBusEntityType = new String("store");
    private WorkflowDescDataVector mWorkflowDescList = new WorkflowDescDataVector();
    
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
     *  <code>getSearchBusEntityType</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getSearchBusEntityType() {
        return (this.mSearchBusEntityType);
    }
    

    /**
     *  <code>setSearchBusEntityType</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchBusEntityType(String pVal) {
        this.mSearchBusEntityType = pVal;
    }


    /**
     *  <code>getBusEntityIdS</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getBusEntityIdS() {
        return (this.mBusEntityIdS);
    }
    

    /**
     *  <code>setBusEntityIdS</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setBusEntityIdS(String pVal) {
        this.mBusEntityIdS = pVal;
    }


    /**
     * <code>getWorkflowDescList</code> method.
     *
     * @return a <code>WorkflowDescDataVector</code> value
     */
    public WorkflowDescDataVector getWorkflowDescList() {
        if (null == mWorkflowDescList) {
            mWorkflowDescList = new WorkflowDescDataVector();
        }
        return (this.mWorkflowDescList);
    }

    /**
     * <code>setWorkflowDescList</code> method.
     *
     * @param pVal a <code>WorkflowDescDataVector</code> value
     */
    public void setWorkflowDescList(WorkflowDescDataVector pVal) {
        this.mWorkflowDescList = pVal;
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

}

