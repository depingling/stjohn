/**
 *  Title: LogOnForm Description: This is the Struts ActionForm class for the
 *  logon page. Purpose: Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     Liang Li
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

/**
 *  Form bean for the logon page. This form has the following fields, with
 *  default values in square brackets:
 *  <ul>
 *    <li> <b>password</b> - Entered password value
 *    <li> <b>username</b> - Entered username value
 *  </ul>
 *
 *
 *@author     dvieira
 *@created    October 16, 2001
 */

public final class LogOnForm extends ActionForm {

    // -------------------------------------------------------- Instance Variables


    private String j_password = "";
    private String j_username = "";
    private CleanwiseUser _appUser = null;
    private SiteDataVector _sites = null;
    private boolean _forceRedirect = false;
    private boolean _passwordHashed = false;

    /**
     *  Set the password.
     *
     *@param  password  The new password
     */
    public void setJ_password(String password) {
        this.j_password = password;
    }


    /**
     *  Set the username.
     *
     *@param  username  The new username
     */
    public void setJ_username(String username) {
        this.j_username = username;
    }


    /**
     *  Sets the AppUser attribute of the LogOnForm object
     *
     *@param  pValue  The new AppUser value
     */
    public void setAppUser(CleanwiseUser pValue) {
        _appUser = pValue;
    }


    /**
     *  Sets the Sites attribute of the LogOnForm object
     *
     *@param  pValue  The new Sites value
     */
    public void setSites(SiteDataVector pValue) {
        _sites = pValue;
    }


    /**
     *  Return the password.
     *
     *@return    The J_password value
     */
    public String getJ_password() {
        return (this.j_password);
    }


    /**
     *  Return the username.
     *
     *@return    The J_username value
     */
    public String getJ_username() {
        return (this.j_username);
    }


    /**
     *  Gets the AppUser attribute of the LogOnForm object
     *
     *@return    The AppUser value
     */
    public CleanwiseUser getAppUser() {
        return _appUser;
    }


    /**
     *  Gets the Sites attribute of the LogOnForm object
     *
     *@return    The Sites value
     */
    public SiteDataVector getSites() {
        return _sites;
    }


    /**
	 * @return the forceRedirect
	 */
	public boolean isForceRedirect() {
		return _forceRedirect;
	}


	/**
	 * @param forceRedirect the forceRedirect to set
	 */
	public void setForceRedirect(boolean forceRedirect) {
		_forceRedirect = forceRedirect;
	}


	/**
	 * @return the passwordHashed
	 */
	public boolean isPasswordHashed() {
		return _passwordHashed;
	}


	/**
	 * @param passwordHashed the _passwordHashed to set
	 */
	public void setPasswordHashed(boolean passwordHashed) {
		_passwordHashed = passwordHashed;
	}


	/**
     *  Reset all properties to their default values.
     *
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

        this.j_password = "";
        this.j_username = "";
        this._forceRedirect = false;
        this._passwordHashed = false;
    }


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
        String accessToken = request.getParameter(Constants.ACCESS_TOKEN);
        if(accessToken!=null){
            return errors;
        }
        if ((j_username == null) || (j_username.length() < 1)) {
            errors.add("username", new ActionError("error.username.required"));
        }

        if ((j_password == null) || (j_password.length() < 1)) {
            errors.add("password", new ActionError("error.password.required"));
        }

        return errors;
    }

}

