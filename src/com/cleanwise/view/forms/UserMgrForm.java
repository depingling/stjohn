/**
 * Title:        UserMgrForm
 * Description:  This is the Struts ActionForm class for the user management page.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 *  Form bean for the user manager page.  
 *
 */
public final class UserMgrForm extends ActionForm {

  /**
   * The user name search criteria.
   */
  private String _userName = "";


  /**
   * The user type search criteria.
   */
  private String _contactName = "";


  /**
   * The user location search criteria.
   */
  private String _userAddress = "";


  /**
   * The user location search criteria.
   */
  private String _accountAddress = "";


  /**
   * The action to take on submit.
   */
  private String _action = "";


  /**
   * The user key.
   */
  private String _key = "";


  // ---------------------------------------------------- Properties

  /**
   * Return the user name search value.
   */
  public String getUserName() {

    return (this._userName);

  }


  /**
   * Set the user name search value.
   *
   * @param name
   *  a partial or complete user name
   */
  public void setUserName(String name) {

    this._userName = name;

  }


  /**
   * Return the contact name search value.
   */
  public String getContactName() {

    return (this._contactName);

  }


  /**
   * Set the contact name search value.
   *
   * @param name
   *  a partial or complete contact name
   */
  public void setContactName(String name) {

    this._contactName = name;

  }


  /**
   * Return the user address search value.
   */
  public String getUserAddress() {

    return (this._userAddress);

  }


  /**
   * Set the user type search value.
   *
   * @param addr
   *  the user address
   */
  public void setUserAddress(String addr) {

    this._userAddress = addr;

  }


  /**
   * Return the account address search value.
   */
  public String getAccountAddress() {

    return (this._accountAddress);

  }


  /**
   * Set the account address search value.
   *
   * @param addr
   *  the account address
   */
  public void setAccountAddress(String addr) {

    this._accountAddress = addr;

  }



  /**
   * Return the action that will occur on page submit.
   */
  public String getAction() {

    return (this._action);

  }


  /**
   * Set the action to take on page submit.
   *
   * @param action
   *  the action
   */
  public void setAction(String action) {

    this._action = action;

  }


  /**
   * Return the user key.
   */
  public String getKey() {

    return (this._key);

  }


  /**
   * Set the user key.
   *
   * @param key
   *  the user key
   */
  public void setKey(String key) {

    this._key = key;

  }


  // ------------------------------------------------------------ Public Methods


  /**
   * Reset all properties to their default values.
   *
   * @param mapping
   *  the mapping used to select this instance
   * @param request
   *  the servlet request we are processing
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {

    this._userName = "";
    this._contactName = "";
    this._userAddress    = "";
    this._accountAddress    = "";
    this._action   = "";
    this._key      = "";
    // don't reset page size

  }


  /**
   * Validate the properties that have been set from this HTTP request,
   * and return an <code>ActionErrors</code> object that encapsulates any
   * validation errors that have been found.  If no errors are found, return
   * <code>null</code> or an <code>ActionErrors</code> object with no
   * recorded error messages.
   *
   * @param mapping
   *  the mapping used to select this instance
   * @param request
   *  the servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

    // No validation necessary.
    return null;

  }


}
