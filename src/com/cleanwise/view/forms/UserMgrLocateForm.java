/**
 * Title:        UserMgrLocateForm
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
 * @author durval
 */
public final class UserMgrLocateForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "nameBegins";
    private int _searchAccountId = 0;

    private String _mFirstName = new String("");
    private String _mLastName = new String("");
    private String _mUserType = new String("");
    private String _mShowStoreMSBOnly = "no";

    public int getSearchAccountId() {
	return this._searchAccountId;
    }

    public void setSearchAccountId(int v) {
	this._searchAccountId = v;
    }

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
     * <code>getFirstName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getFirstName() {
	return (this._mFirstName);
    }

    /**
     * <code>setFirstName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setFirstName(String pVal) {
	this._mFirstName = pVal;
    }

    
    /**
     * <code>getLastName</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getLastName() {
	return (this._mLastName);
    }

    /**
     * <code>setLastName</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setLastName(String pVal) {
	this._mLastName = pVal;
    }

    
    /**
     * <code>getUserType</code> method.
     *
     * @return a <code>String</code> value
     */
    public String getUserType() {
	return (this._mUserType);
    }

    /**
     * <code>setUserType</code> method.
     *
     * @param pVal a <code>String</code> value
     */
    public void setUserType(String pVal) {
	this._mUserType = pVal;
    }
    
    public String getShowStoreMSBOnly() {
		return _mShowStoreMSBOnly;
	}

	public void setShowStoreMSBOnly(String showStoreMSBOnly) {
		_mShowStoreMSBOnly = showStoreMSBOnly;
	}

	/**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
	this._searchField = "";
//	this._searchType = "";
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





