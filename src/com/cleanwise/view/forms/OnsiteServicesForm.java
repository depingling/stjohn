/**
 *  Title: OnsiteServicesForm Description: This is the Struts ActionForm class
 *  for the shopping page. Purpose: Copyright: Copyright (c) 2001 Company:
 *  CleanWise, Inc.
 *
 *@author     Yuriy Kupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.value.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import com.cleanwise.view.utils.Constants;

/**
 *  Form bean for the onsite services page. This form has the following fields,
 *
 *@author     dvieira
 *@created    October 18, 2001
 */

public final class OnsiteServicesForm extends ActionForm {

    String mComments;
    String mOnSiteServiceType;
    String mLocationSameAsShipping;
    String mAction;
    String mResultMsg;


    /**
     *  Sets the ResultMsg attribute of the OnsiteServicesForm object
     *
     *@param  v  The new ResultMsg value
     */
	public void setResultMsg(String v) {
		this.mResultMsg = v;
	}

    /**
     *  Gets the ResultMsg attribute of the OnsiteServicesForm object
     *
     *@return    The ResultMsg value
     */
	public String getResultMsg() {
		return this.mResultMsg;
	}

    /**
     *  Sets the Action attribute of the OnsiteServicesForm object
     *
     *@param  v  The new Action value
     */
    public void setAction(String v) {
        this.mAction = v;
    }


    /**
     *  Sets the LocationSameAsShipping attribute of the OnsiteServicesForm
     *  object
     *
     *@param  v  The new LocationSameAsShipping value
     */
    public void setLocationSameAsShipping(String v) {
        this.mLocationSameAsShipping = v;
    }


    /**
     *  Sets the OnSiteServiceType attribute of the OnsiteServicesForm object
     *
     *@param  v  The new OnSiteServiceType value
     */
    public void setOnSiteServiceType(String v) {
        this.mOnSiteServiceType = v;
    }


    /**
     *  Sets the Comments attribute of the OnsiteServicesForm object
     *
     *@param  v  The new Comments value
     */
    public void setComments(String v) {
        this.mComments = v;
    }


    /**
     *  Gets the Action attribute of the OnsiteServicesForm object
     *
     *@return    The Action value
     */
    public String getAction() {
        return mAction;
    }


    /**
     *  Gets the LocationSameAsShipping attribute of the OnsiteServicesForm
     *  object
     *
     *@return    The LocationSameAsShipping value
     */
    public String getLocationSameAsShipping() {
        return mLocationSameAsShipping;
    }


    /**
     *  Gets the OnSiteServiceType attribute of the OnsiteServicesForm object
     *
     *@return    The OnSiteServiceType value
     */
    public String getOnSiteServiceType() {
        return mOnSiteServiceType;
    }


    /**
     *  Gets the Comments attribute of the OnsiteServicesForm object
     *
     *@return    The Comments value
     */
    public String getComments() {
        return mComments;
    }


    /**
     *  Reset all properties to their default values.
     *
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) { }


    /**
     *  Validate the properties that have been set from this HTTP request, and
     *  return an <code>ActionErrors</code> object that encapsulates any
     *  validation errors that have been found. If no errors are found, return
     *  <code>null</code> or an <code>ActionErrors</code> object with no
     *  recorded error messages.
     *
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     *@return          Description of the Returned Value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        return errors;
    }
}

