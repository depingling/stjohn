package com.cleanwise.service.api.value;

/**
 * Title:        LdapItemData
 * Description:  Value object extension for marshalling LDAP data.
 * Purpose:      Obtains and passes LDAP information to the session beans.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.ValueObject;

/**
 * <code>LdapItemData</code>
 */
public class LdapItemData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4988598618239446756L;

  // -------------------------------------------------------- Instance Variables
  private String userName;
  private String password;
  private String accessToken;
  private boolean passwordHashed = false;

  public LdapItemData() {

  }

  // ---------------------------------------------------------------- Properties

  /**
   * Gets the UserName property
   * return String
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * Sets the UserName property
   * @param pValue  the value we want to set
   * return none
   */
  public void setUserName(String pValue) {
    this.userName = pValue;
  }

  /**
   * Gets the Password property
   * return String
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Sets the Password property
   * @param pValue  the value we want to set
   * return none
   */
  public void setPassword(String pValue) {
    this.password = pValue;
  }
  
  
    /**
   * Gets the AccessToken property
   * return String
   */
  public String getAccessToken() {
    return this.accessToken;
  }

  /**
   * Sets the AccessToken property
   * @param pValue  the value we want to set
   * return none
   */
  public void setAccessToken(String pAccessToken) {
    this.accessToken = pAccessToken;
  }

/**
 * @return the passwordHashed
 */
public boolean isPasswordHashed() {
	return passwordHashed;
}

/**
 * @param passwordHashed the passwordHashed to set
 */
public void setPasswordHashed(boolean passwordHashed) {
	this.passwordHashed = passwordHashed;
}

}
