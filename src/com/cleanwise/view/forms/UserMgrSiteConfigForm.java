/**
 * Title:        UserMgrSiteConfigForm
 * Description:  This is the Struts ActionForm class for 
 * user management page.
 * Purpose:      Strut support to search for users.      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       durval
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * Form bean for the user manager page. 
 *
 * @author tbesser
 */
public final class UserMgrSiteConfigForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";
    private String _city = "";
    private String _state = "";
    private int _accountId;
    private String _accountName;

    /**
     * the ids of sites that are currently associated with the user
     *
     */
    private String[] _assocSiteIds = null;
    /**
     * the ids of selected/checked sites that are to be associated with user
     *
     */
    private String[] _selectIds = null;
    /**
     * the ids of sites being displayed (needed because of paging/filtering)
     *
     */
    private String[] _displayIds = null;

    /**
     * <code>getSearchField</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchField() {
	return (this._searchField);
    }

    /**
     * <code>setSearchField</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchField(String pVal) {
	this._searchField = pVal;
    }

    /**
     * <code>getSearchType</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getSearchType() {
	return (this._searchType);
    }

    /**
     * <code>setSearchType</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setSearchType(String pVal) {
	this._searchType = pVal;
    }

    /**
     * <code>getCity</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getCity() {
	return (this._city);
    }

    /**
     * <code>setCity</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setCity(String pVal) {
	this._city = pVal;
    }

    /**
     * <code>getState</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getState() {
	return (this._state);
    }

    /**
     * <code>setState</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setState(String pVal) {
	this._state = pVal;
    }

    /**
     * Describe <code>setSelectIds</code> method here.
     *
     * @param pValue a <code>String[]</code> value
     */
    public void setSelectIds (String[] pValue) {
	_selectIds = pValue;
    }
    
    /**
     * Describe <code>getSelectIds</code> method here.
     *
     * @return a <code>String[]</code> value
     */
    public String[] getSelectIds() {
	return _selectIds;
    }

    /**
     * Describe <code>setDisplayIds</code> method here.
     *
     * @param pValue a <code>String[]</code> value
     */
    public void setDisplayIds (String[] pValue) {
	_displayIds = pValue;
    }

    /**
     * Describe <code>getDisplayIds</code> method here.
     *
     * @return a <code>String[]</code> value
     */
    public String[] getDisplayIds() {
	return _displayIds;
    }

    /**
     * <code>getAccountName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getAccountName() {
	return (this._accountName);
    }

    /**
     * <code>setAccountName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setAccountName(String pVal) {
	this._accountName = pVal;
    }
    /**
     * <code>getAccountId</code> method.
     *
     * @return a <code>int</code> value
     */
    public int getAccountId() {
	return (this._accountId);
    }

    /**
     * <code>setAccountId</code> method.
     *
     * @param pVal a <code>int</code> value
     */
    public void setAccountId(int pVal) {
	this._accountId = pVal;
    }

    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	this._searchField = "";
	this._searchType = "";
	this._selectIds = new String[0];
	this._displayIds = new String[0];
        _accountId = 0;
        _accountName = "";
    }


    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
				 HttpServletRequest request) {
	// No validation necessary.
	return null;
    }

}





