/**
 * Title: LogOnForm 
 * Description: This is the Struts ActionForm class handling the ESW UI log on functionality.
 */

package com.espendwise.view.forms.esw;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * Implementation of <code>ActionForm</code> that handles log on functionality.
 */
public final class SecurityForm extends EswForm {

	private static final long serialVersionUID = -2580197292852744110L;
	private String _password = "";
    private String _username = "";
    private String _userId = "";
    private String _accessToken = "";
    private String _originURL = "";
    private boolean _rememberUserName = false;
    private boolean _passwordHashed = false;
    private String _destination = "";

    /**
     * @return the password value.
     */
    public String getPassword() {
		return _password;
	}

	/**
	 * @param password - the password value to set.
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * @return the user name value.
	 */
	public String getUsername() {
		return _username;
	}

	/**
	 * @param username - the user name value to set.
	 */
	public void setUsername(String username) {
		_username = username;
	}

	/**
	 * @return the access token value.
	 */
	public String getAccessToken() {
		return _accessToken;
	}

	/**
	 * @param accessToken - the access token value to set.
	 */
	public void setAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	/**
	 * @return the originURL value.
	 */
	public String getOriginURL() {
		return _originURL;
	}

	/**
	 * @param originURL the originURL value to set
	 */
	public void setOriginURL(String originURL) {
		_originURL = originURL;
	}

	/**
	 * @return the rememberUserName
	 */
	public boolean isRememberUserName() {
		return _rememberUserName;
	}

	/**
	 * @param rememberUserName the rememberUserName to set
	 */
	public void setRememberUserName(boolean rememberUserName) {
		_rememberUserName = rememberUserName;
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
	 * @return the userId
	 */
	public String getUserId() {
		return _userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		_userId = userId;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return _destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		_destination = destination;
	}

	/**
     * Reset all properties to their default values.
     *@param  mapping  The mapping used to select this instance
     *@param  request  The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	super.reset(mapping, request);
        _password = "";
        _username = "";
        _accessToken = "";
        _originURL = null;
        _rememberUserName = false;
        _passwordHashed = false;
        _userId = "";
        _destination = "";
    }

}

