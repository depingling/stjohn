/**
 *  Title: CatalogMgrConfigForm Description: This is the Struts ActionForm 
 *  class for site config management page.
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

/**
 *  Form bean for the catalog manager configuration page.
 *
 *@author     tbesser
 *@created    October 14, 2001
 */
public final class CatalogMgrConfigForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";
    private String _configType = "";
    private String _city = "";
    private String _county = "";
    private String _state = "";
    private String _zipcode = "";
    private int _overwriteDistId = 0;

    private String _distributorId;



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
     *  <code>setSearchField</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchField(String pVal) {
        this._searchField = pVal;
    }

    
   /**
     *  <code>setZipcode</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setZipcode(String pVal) {
        this._zipcode = pVal;
    }

   /**
     *  <code>setState</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setState(String pVal) {
        this._state = pVal;
    }

    /**
     *  <code>setCity</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCity(String pVal) {
        this._city = pVal;
    }

    /**
     *  <code>setCounty</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCounty(String pVal) {
        this._county = pVal;
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
     *  <code>setConfigType</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setConfigType(String pVal) {
        this._configType = pVal;
    }

    /**
     *  <code>setDistributorId</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setDistributorId(String pVal) {
        this._distributorId = pVal;
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
     *  <code>getZipcode</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getZipcode() {
        return (this._zipcode);
    }

    /**
     *  <code>getState</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getState() {
        return (this._state);
    }

    /**
     *  <code>getCity</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getCity() {
        return (this._city);
    }

    /**
     *  <code>getCounty</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getCounty() {
        return (this._county);
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
     *  <code>getConfigType</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getConfigType() {
        return (this._configType);
    }

    /**
     *  <code>getDistributorId</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getDistributorId() {
        return (this._distributorId);
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
     * Describe <code>setOverwriteDistId</code> method here.
     *
     * @param pValue a <code>int</code> value
     */
    public void setOverwriteDistId (int pValue) {
	_overwriteDistId = pValue;
    }

    /**
     * Describe <code>getOverwriteDistId</code> method here.
     *
     * @return a <code>int</code> value
     */
    public int getOverwriteDistId() {
	return _overwriteDistId;
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
        this._configType = "";
	this._selectIds = new String[0];
	this._displayIds = new String[0];
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

