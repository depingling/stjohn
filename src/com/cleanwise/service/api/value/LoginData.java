package com.cleanwise.service.api.value;

/**
 * Title:        LoginData
 * Description:  Value object extension for marshalling user login data.
 * Purpose:      Obtains and passes login information to the login method.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;

/**
 * <code>LoginData</code>
 */
public class LoginData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 2825141284274693565L;

  // -------------------------------------------------------- Instance Variables
  public String userName;
  public String password;
  public Date   effDate;
  public Date   expDate;
  public String userTypeCd;
  public String userProfileCd;
  public String userStatusCd;

  /**
   *
   */
  public LoginData() {
    userName           = new String("");
    password           = new String("");
    effDate            = new Date();
    expDate            = new Date();
    userTypeCd         = new String("");
    userProfileCd      = new String("");
    userStatusCd       = new String("");
  }

  /**
   *
   */
  public LoginData(int pPromoId, String pText254) {

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
   * Gets the EffDate (effective date) property
   * return Date
   */
  public Date getEffDate() {
    return this.effDate;
  }

  /**
   * Sets the EffDate (effective date) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setEffDate(Date pValue) {
    this.effDate = pValue;
  }

  /**
   * Gets the ExpDate (expiration date) property
   * return Date
   */
  public Date getExpDate() {
    return this.expDate;
  }

  /**
   * Sets the ExpDate (expiration date) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setExpDate(Date pValue) {
    this.expDate = pValue;
  }

  /**
   * Gets the UserTypeCd (user type code) property
   * return String
   */
  public String getUserTypeCd() {
    return this.userTypeCd;
  }

  /**
   * Sets the UserTypeCd (user type code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setUserTypeCd(String pValue) {
    this.userTypeCd = pValue;
  }

  /**
   * Gets the UserProfileCd (user profile code) property
   * return String
   */
  public String getUserProfileCd() {
    return this.userProfileCd;
  }

  /**
   * Sets the UserProfileCd (user profile code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setUserProfileCd(String pValue) {
    this.userProfileCd = pValue;
  }

  /**
   * Gets the UserStatusCd (user status code) property
   * return String
   */
  public String getUserStatusCd() {
    return this.userStatusCd;
  }

  /**
   * Sets the UserStatusCd (user status code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setUserStatusCd(String pValue) {
    this.userStatusCd = pValue;
  }



}
