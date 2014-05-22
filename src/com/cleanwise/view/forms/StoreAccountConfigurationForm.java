package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *@author Alexander Chikin
 * Date: 31.08.2006
 * Time: 11:14:01
 */
public class StoreAccountConfigurationForm extends ActionForm {

    private String mSearchForType = "";
    private String mSearchForName = "";
    private String _searchType = "nameBegins";
    private String _viewAll = "";

    private String _city = "";
    private String _state = "";
    private String _firstName = "";
    private String _lastName = "";
    private String _userType = "";
    private boolean _showInactiveFl = false;

    /**
     *  Sets the SearchForType attribute of the StoreAccountConfigurationForm object
     *
     *@param  pTypeDesc  The new SearchForType value
     */
    public void setSearchForType(String pTypeDesc) {
        this.mSearchForType = pTypeDesc;
    }


    /**
     *  Sets the SearchForName attribute of the StoreAccountConfigurationForm object
     *
     *@param  pSearchForName  The new SearchForName value
     */
    public void setSearchForName(String pSearchForName) {
        this.mSearchForName = pSearchForName;
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
     *  <code>setViewAll</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setViewAll(String pVal) {
        this._viewAll = pVal;
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
     *  <code>setState</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setState(String pVal) {
        this._state = pVal;
    }

    /**
     *  <code>setFirstName</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setFirstName(String pVal) {
        this._firstName = pVal;
    }

    /**
     *  <code>setLastName</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setLastName(String pVal) {
        this._lastName = pVal;
    }

    /**
     *  <code>setUserType</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setUserType(String pVal) {
        this._userType = pVal;
    }


    /**
     *  Gets the SearchForName attribute of the StoreAccountConfigurationForm object
     *
     *@return    The SearchForName value
     */
    public String getSearchForName() {
        return this.mSearchForName;
    }

    /**
     *  Gets the SearchForType attribute of the StoreAccountConfigurationForm object
     *
     *@return    The SearchForType value
     */
    public String getSearchForType() {
        return this.mSearchForType;
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
     *  <code>getViewAll</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getViewAll() {
        return (this._viewAll);
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
     *  <code>getState</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getState() {
        return (this._state);
    }

    /**
     *  <code>getFirstName</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getFirstName() {
        return (this._firstName);
    }

    /**
     *  <code>getLastName</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getLastName() {
        return (this._lastName);
    }

    /**
     *  <code>getUserType</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getUserType() {
        return (this._userType);
    }


    public boolean isShowInactiveFl() {
        return _showInactiveFl;
    }

    public void setShowInactiveFl(boolean showInactiveFl) {
        this._showInactiveFl = showInactiveFl;
    }

    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
     this._showInactiveFl=false;
    }
}

