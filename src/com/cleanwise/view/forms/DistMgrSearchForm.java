/**
 *  Title: DistMgrSearchForm Description: This is the Struts ActionForm class
 *  for user management page. Purpose: Strut support to search for users.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *  Form bean for the user manager page.
 *
 *@author     durval
 *@created    August 8, 2001
 */
public final class DistMgrSearchForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "nameBegins";
    private String _searchCity = "";
    private String _searchState = "";
    private String _searchCounty = "";
    private String _searchPostalCode = "";

    /** Holds value of property searchGroupId. */
    private String searchGroupId;    

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
     *  <code>setSearchState</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchState(String pVal) {
        this._searchState = pVal;
    }
    public void setSearchCity(String pVal) {
        this._searchCity = pVal;
    }
 
    /**
     *  <code>setSearchCounty</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchCounty(String pVal) {
        this._searchCounty = pVal;
    }

    /**
     *  <code>setSearchPostalCode</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchPostalCode(String pVal) {
        this._searchPostalCode = pVal;
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
     *  <code>getSearchState</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getSearchState() {
        return (this._searchState);
    }
    public String getSearchCity() {
        return (this._searchCity);
    }

    /**
     *  <code>getSearchCounty</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getSearchCounty() {
        return (this._searchCounty);
    }

    /**
     *  <code>getSearchPostalCode</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getSearchPostalCode() {
        return (this._searchPostalCode);
    }


    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this._searchField = "";
      //  this._searchType = "";
        this._searchState = "";
        this._searchCounty = "";
        this._searchPostalCode = "";
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

    /** Getter for property searchGroupId.
     * @return Value of property searchGroupId.
     *
     */
    public String getSearchGroupId() {
        return this.searchGroupId;
    }
    
    /** Setter for property searchGroupId.
     * @param searchGroupId New value of property searchGroupId.
     *
     */
    public void setSearchGroupId(String searchGroupId) {
        this.searchGroupId = searchGroupId;
    }
    
}

