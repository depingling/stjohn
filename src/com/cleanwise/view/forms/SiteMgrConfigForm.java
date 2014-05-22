/**
 *  Title: SiteMgrConfigForm Description: This is the Struts ActionForm class
 *  for site config management page.
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
import java.util.*;

/**
 *  Form bean for the user manager page.
 *
 *@author     tbesser
 *@created    October 14, 2001
 */
public final class SiteMgrConfigForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";
    private String _configType = "";
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

    private String _catalogId = null;
    private String _oldCatalogId = null;

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
     *  <code>setConfigType</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setConfigType(String pVal) {
        this._configType = pVal;
    }

    /**
     *  <code>setCatalogId</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCatalogId(String pVal) {
        this._catalogId = pVal;
    }

    /**
     *  <code>setOldCatalogId</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setOldCatalogId(String pVal) {
        this._oldCatalogId = pVal;
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
     *  <code>getConfigType</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getConfigType() {
        return (this._configType);
    }

    /**
     *  <code>getCatalogId</code> method.
     *
     *@return  a <code>String</code> value
     */
    public String getCatalogId() {
        return (this._catalogId);
    }

    /**
     *  <code>getOldCatalogId</code> method.
     *
     *@return  a <code>String</code> value
     */
    public String getOldCatalogId() {
        return (this._oldCatalogId);
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

    // General purpose hash map for config items.
    private HashMap map = new HashMap();
    public Object getGeneralPropertyVal(String key) { 
        return map.get(key);
    }
    
    public void setGeneralPropertyVal(String key, Object value) { 
        map.put(key, value); 
    }    

    public HashMap getGeneralPropertyMap() { 
        return map; 
    }
}

