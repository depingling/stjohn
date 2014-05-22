/**
 *  Title: SiteMgrSearchForm Description: This is the Struts ActionForm class
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

import java.util.List;

import com.cleanwise.service.api.value.PairViewVector;

/**
 *  Form bean for the user manager page.
 *
 *@author     durval
 *@created    August 14, 2001
 */
public final class SiteMgrSearchForm extends ActionForm {

    private String _searchField = "";
    private String _searchType = "";

    private String _mId = "";
    private String _mName = "";
    private String _mCity = "";
    private String _mCounty = "";
    private String _mState = "";
    private String _mAccountIdList = "";
    private String _mCountry = "";
    private String _mSiteId="";

    String PostalCode = "";

    private List _mStateValueList;
    private PairViewVector _mSiteValuePairs;
    private List _mCountryValueList;
    private Object[] _mCountryAndStateLinks;

    /**
     * Get the value of PostalCode.
     * @return value of PostalCode.
     */
    public String getPostalCode() {
	return PostalCode;
    }

    /**
     * Set the value of PostalCode.
     * @param v  Value to assign to PostalCode.
     */
    public void setPostalCode(String  v) {
	this.PostalCode = v;
    }

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
     *  <code>setId</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setId(String pVal) {
        this._mId = pVal;
    }


    /**
     *  <code>getId</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getId() {
        return (this._mId);
    }


    /**
     *  <code>setName</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setName(String pVal) {
        this._mName = pVal;
    }


    /**
     *  <code>getName</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getName() {
        return (this._mName);
    }


    /**
     *  <code>setCity</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCity(String pVal) {
        this._mCity = pVal;
    }


    /**
     *  <code>getCity</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getCity() {
        return (this._mCity);
    }

    /**
     *  <code>setCounty</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCounty(String pVal) {
        this._mCounty = pVal;
    }


    /**
     *  <code>getCounty</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getCounty() {
        return (this._mCounty);
    }

    /**
     *  <code>setState</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setState(String pVal) {
        this._mState = pVal;
    }


    /**
     *  <code>getState</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getState() {
        return (this._mState);
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

    String RefValue;

    /**
     * Get the value of RefValue.
     * @return value of RefValue.
     */
    public String getRefValue() {
	return RefValue;
    }

    /**
     * Set the value of RefValue.
     * @param v  Value to assign to RefValue.
     */
    public void setRefValue(String  v) {
	this.RefValue = v;
    }

    public String getAccountIdList() {return _mAccountIdList;}
    public void setAccountIdList(String  v) {_mAccountIdList = v;}

    /**
     *  <code>setCountry</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setCountry(String pVal) {
        this._mCountry = pVal;
    }


    /**
     *  <code>getCountry</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getCountry() {
        return (this._mCountry);
    }

    public void setSiteId(String siteId) {
        this._mSiteId = siteId;
    }

    public String getSiteId() {
        return _mSiteId;
    }

    public void setStateValueList(List stateValueList) {
        this._mStateValueList = stateValueList;
    }

    public void setSiteValuePairs(PairViewVector siteValuePairs) {
        this._mSiteValuePairs = siteValuePairs;
    }

    public void setCountryValueList(List countryValueList) {
        this._mCountryValueList = countryValueList;
    }

    public List getStateValueList() {
        return _mStateValueList;
    }

    public PairViewVector getSiteValuePairs() {
        return _mSiteValuePairs;
    }

    public List getCountryValueList() {
        return _mCountryValueList;
    }

    public void setCountryAndStateLinks(Object[] countryAndStateLinks) {
        this._mCountryAndStateLinks = countryAndStateLinks;
    }

    public Object[] getCountryAndStateLinks() {
        return _mCountryAndStateLinks;
    }
}
