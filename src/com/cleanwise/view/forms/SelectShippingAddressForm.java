/**
 * Title:        SelectShippingAddressForm
 * Description:  This is the Struts ActionForm class for the select shippingaddress page.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.view.utils.CleanwiseUser;

/**
 * Form bean for the signon page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>shippingAddressKey</b> - Entered shipping address key value
 * <li><b>properAction</b> - Proper action value
 * </ul>
 *
 * @author Liang Li
 */

public final class SelectShippingAddressForm extends ActionForm {

  public SelectShippingAddressForm() {
    super();
  }

  // -------------------------------------------------------- Instance Variables


  private String shippingAddressKey = "";
  private String properAction = "";
  private String _test ="";
  private SiteDataVector _sites = null;
  private CleanwiseUser _appUser = null;
  private boolean _multiAcctFlag = true;
  // ---------------------------------------------------------------- Properties


  /**
   * Return the shippingAddressKey.
   */
  public String getShippingAddressKey() {
    return (this.shippingAddressKey);
  }


  /**
   * Set the shippingAddressKey.
   *
   * @param value The new shippingAddressKey
   */
  public void setShippingAddressKey(String value) {
    this.shippingAddressKey = value;
  }


  /**
   * Return the proper action.
   */
  public String getProperAction() {
    return (this.properAction);
  }


  /**
   * Set the proper action.
   *
   * @param action The new proper action
   */
  public void setProperAction(String action) {
    this.properAction = action;
  }


  public SiteDataVector getSites() {return _sites;}
  public void setSites(SiteDataVector pValue) {_sites = pValue;}


  public CleanwiseUser getAppUser() {return _appUser;}
  public void setAppUser(CleanwiseUser pValue) {_appUser = pValue;}

  public boolean getMultiAcctFlag() {return _multiAcctFlag;}
  public void setMultiAcctFlag(boolean pValue) {_multiAcctFlag = pValue;}

  public boolean getSiteFlag() {return (_appUser.getSite()==null)?false:true;}

  // ------------------------------------------------------------ Public Methods


  /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {

    this.shippingAddressKey = "";
    this.properAction = "";
  }

  public void setTest(String pValue){
  _test=pValue;
  }
  public String getTest(){return _test;}


  /**
   * Validate the properties that have been set from this HTTP request,
   * and return an <code>ActionErrors</code> object that encapsulates any
   * validation errors that have been found.  If no errors are found, return
   * <code>null</code> or an <code>ActionErrors</code> object with no
   * recorded error messages.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

    ActionErrors errors = new ActionErrors();
    return errors;

  }


}
