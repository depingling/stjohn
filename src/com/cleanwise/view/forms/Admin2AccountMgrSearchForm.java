package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.service.api.value.GroupDataVector;

import javax.servlet.http.HttpServletRequest;

public class Admin2AccountMgrSearchForm  extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";
    private GroupDataVector _accountGroups = null;

    /** Holds value of property searchGroupId. */
    private String searchGroupId;
    private int _currentStore;

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

    public GroupDataVector getAccountGroups() {return _accountGroups;}
    public void setAccountGroups(GroupDataVector pVal) {_accountGroups = pVal;}

    public int getCurrentStore() {
        return _currentStore;
    }

    public void setCurrentStore(int currentStore) {
        this._currentStore = currentStore;
    }
}

