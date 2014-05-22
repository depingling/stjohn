/**
 *  Title: ManufMgrDetailForm Description: This is the Struts ActionForm class
 *  for user management page. Purpose: Strut support to search for
 *  manufacturers. Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     durval
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 *  Form bean for the user manager page.
 *
 *@author     Veronika Denega
 *@created    July 17, 2006
 */
public final class StoreManufMgrDetailForm extends Base2DetailForm {

  private boolean mSpec1;
  private boolean mSpec2;
  private boolean mSpec3;
  private boolean mSpec4;
  private boolean showManufEquvalentName;
  private String mBusinessClass;
  private String mWomanOwnedBusiness;
  private String mMinorityOwnedBusiness;
  private String mJWOD;
  private String mOtherBusiness;
  private String storeId;
  private String otherEquivalentManufNames;
  private String msdsPlugin;


  /**
   *  Sets the Specialization4 attribute of the StoreManufMgrDetailForm object
   *
   *@param  mSpec4  The new Specialization4 value
   */
  public void setSpecialization4(boolean mSpec4) {
    this.mSpec4 = mSpec4;
  }


  /**
   *  Sets the Specialization3 attribute of the StoreManufMgrDetailForm object
   *
   *@param  mSpec3  The new Specialization3 value
   */
  public void setSpecialization3(boolean mSpec3) {
    this.mSpec3 = mSpec3;
  }


  /**
   *  Sets the Specialization2 attribute of the StoreManufMgrDetailForm object
   *
   *@param  mSpec2  The new Specialization2 value
   */
  public void setSpecialization2(boolean mSpec2) {
    this.mSpec2 = mSpec2;
  }


  /**
   *  Sets the Specialization1 attribute of the StoreManufMgrDetailForm object
   *
   *@param  mSpec1  The new Specialization1 value
   */
  public void setSpecialization1(boolean mSpec1) {
    this.mSpec1 = mSpec1;
  }


  /**
   * Sets the BusinessClass attribute of the StoreManufMgrDetailForm object
   *
   *@param pBusinessClass The new BusinessClass value
   */
  public void setBusinessClass(String pBusinessClass) {
    this.mBusinessClass = pBusinessClass;
  }


  /**
   * Sets the WomanOwnedBusiness attribute of the StoreManufMgrDetailForm object
   *
   *@param pWomanOwnedBusiness The new WomanOwnedBusiness value
   */
  public void setWomanOwnedBusiness(String pWomanOwnedBusiness) {
    this.mWomanOwnedBusiness = pWomanOwnedBusiness;
  }


  /**
   * Sets the MinorityOwnedBusiness attribute of the StoreManufMgrDetailForm object
   *
   *@param pMinorityOwnedBusiness The new MinorityOwnedBusiness value
   */
  public void setMinorityOwnedBusiness(String pMinorityOwnedBusiness) {
    this.mMinorityOwnedBusiness = pMinorityOwnedBusiness;
  }


  /**
   * Sets the JWOD attribute of the StoreManufMgrDetailForm object
   *
   *@param pJWOD The new JWOD value
   */
  public void setJWOD(String pJWOD) {
    this.mJWOD = pJWOD;
  }


  /**
   * Sets the OtherBusiness attribute of the StoreManufMgrDetailForm object
   *
   *@param pOtherBusiness The new OtherBusiness value
   */
  public void setOtherBusiness(String OtherBusiness) {
    this.mOtherBusiness = OtherBusiness;
  }


  /**
   *  Gets the Specialization4 attribute of the StoreManufMgrDetailForm object
   *
   *@return    The Specialization4 value
   */
  public boolean getSpecialization4() {
    return mSpec4;
  }


  /**
   *  Gets the Specialization3 attribute of the StoreManufMgrDetailForm object
   *
   *@return    The Specialization3 value
   */
  public boolean getSpecialization3() {
    return mSpec3;
  }


  /**
   *  Gets the Specialization2 attribute of the StoreManufMgrDetailForm object
   *
   *@return    The Specialization2 value
   */
  public boolean getSpecialization2() {
    return mSpec2;
  }


  /**
   *  Gets the Specialization1 attribute of the StoreManufMgrDetailForm object
   *
   *@return    The Specialization1 value
   */
  public boolean getSpecialization1() {
    return mSpec1;
  }


  /**
   *  Gets the BusinessClass attribute of the StoreManufMgrDetailForm object
   *
   *@return The BusinessClass value
   */
  public String getBusinessClass() {
    return mBusinessClass;
  }


  /**
   *  Gets the WomanOwnedBusiness attribute of the StoreManufMgrDetailForm object
   *
   *@return The WomanOwnedBusiness value
   */
  public String getWomanOwnedBusiness() {
    return mWomanOwnedBusiness;
  }


  /**
   *  Gets the MinorityOwnedBusiness attribute of the StoreManufMgrDetailForm object
   *
   *@return The MinorityOwnedBusiness value
   */
  public String getMinorityOwnedBusiness() {
    return mMinorityOwnedBusiness;
  }


  /**
   *  Gets the JWOD attribute of the StoreManufMgrDetailForm object
   *
   *@return The JWOD value
   */
  public String getJWOD() {
    return mJWOD;
  }


  /**
   *  Gets the OtherBusiness attribute of the StoreManufMgrDetailForm object
   *
   *@return The OtherBusiness value
   */
  public String getOtherBusiness() {
    return mOtherBusiness;
  }


  /**
   *  <code>reset</code> method, set the search fiels to null.
   *
   *@param  mapping  an <code>ActionMapping</code> value
   *@param  request  a <code>HttpServletRequest</code> value
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    mSpec1 = false;
    mSpec2 = false;
    mSpec3 = false;
    mSpec4 = false;
    //showManufEquvalentName = false;
    return;
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
    // Validation happens in the logic bean.
    return null;
  }

  /**
   * Getter for property storeId.
   * @return Value of property storeId.
   */
  public String getStoreId() {
    return this.storeId;
  }

  /**
   * Setter for property storeId.
   * @param storeId New value of property storeId.
   */
  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public void setOtherEquivalentManufNames(String otherEquivalentManufNames) {
          this.otherEquivalentManufNames = otherEquivalentManufNames;
  }
  
  public String getOtherEquivalentManufNames() {
          return otherEquivalentManufNames;
  }

  public boolean getShowManufEquvalentName() {
      return showManufEquvalentName;
  }

  public void setShowManufEquvalentName(boolean showManufEquvalentName) {
      this.showManufEquvalentName = showManufEquvalentName;
  }

    public String getMsdsPlugin() {
        return msdsPlugin;
    }

    public void setMsdsPlugin(String msdsPlugin) {
        this.msdsPlugin = msdsPlugin;
    }
}

