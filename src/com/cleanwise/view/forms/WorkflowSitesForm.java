package com.cleanwise.view.forms;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 *  Form bean for the workflow detail page.
 *
 *@author     durval
 *@created    December 11, 2001
 */
public final class WorkflowSitesForm extends Base2DetailForm {

    private int mBusEntityId = 0;
    private String mState = "";
    private String mSearchField = "";
    private String mConfigType = "";
    private String mSearchType = "";


    /**
     * Get the value of BusEntityId.
     * @return value of BusEntityId.
     */
    public int getBusEntityId() {
        return mBusEntityId;
    }
    
    /**
     * Set the value of BusEntityId.
     * @param v  Value to assign to BusEntityId.
     */
    public void setBusEntityId(int  v) {
        this.mBusEntityId = v;
    }    


    /**
     * Get the value of State.
     * @return value of State.
     */
    public String getState() {
        return mState;
    }
    
    /**
     * Set the value of State.
     * @param v  Value to assign to State.
     */
    public void setState(String  v) {
        this.mState = v;
    }    
    

    /**
     * the ids of sites that are currently associated.
     *
     */
    private String[] mAssocSiteIds = null;
    /**
     * the ids of selected/checked sites that are to be.
     *
     */
    private String[] mSelectIds = null;
    /**
     * the ids of sites being displayed (needed because of paging/filtering)
     *
     */
    private String[] mDisplayIds = null;

    /**
     *  <code>setSearchField</code> method.
     *
     *@param  pVal  a <code>String</code> value
     */
    public void setSearchField(String pVal) {
        this.mSearchField = pVal;
    }
    public void setSearchType(String pVal) {
        this.mSearchType = pVal;
    }


    /**
     *  <code>getSearchField</code> method.
     *
     *@return    a <code>String</code> value
     */
    public String getSearchField() {
        return (this.mSearchField);
    }
    public String getSearchType() {
        return (this.mSearchType);
    }

    /**
     * Describe <code>setSelectIds</code> method here.
     *
     * @param pValue a <code>String[]</code> value
     */
    public void setSelectIds (String[] pValue) {
	mSelectIds = pValue;
    }
    
    /**
     * Describe <code>getSelectIds</code> method here.
     *
     * @return a <code>String[]</code> value
     */
    public String[] getSelectIds() {
	return mSelectIds;
    }

    /**
     * Describe <code>setDisplayIds</code> method here.
     *
     * @param pValue a <code>String[]</code> value
     */
    public void setDisplayIds (String[] pValue) {
	mDisplayIds = pValue;
    }

    /**
     * Describe <code>getDisplayIds</code> method here.
     *
     * @return a <code>String[]</code> value
     */
    public String[] getDisplayIds() {
	return mDisplayIds;
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.mSearchField = "";
        this.mSearchType = "";
        this.mConfigType = "";
	this.mSelectIds = new String[0];
	this.mDisplayIds = new String[0];
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


