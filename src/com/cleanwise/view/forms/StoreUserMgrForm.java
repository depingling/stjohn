/*
 * StoreUserMgrForm.java
 *
 * Created on May 1, 2006, 1:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.validators.EmailValidator;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 *
 * @author Ykupershmidt
 */
public class StoreUserMgrForm  extends StorePortalBaseForm {
	
  public static Logger log = Logger.getLogger(StoreUserMgrForm.class);
	
  public StoreUserMgrForm() {
    super();
  }
  private UserRightsForm mBaseUserForm = new UserRightsForm();

  //////////////////////////////////////////////
  private String _searchField = "";
  private String _searchRefNum = "";
  private String _searchType = "nameBegins";
  private String _searchRefNumType = "nameBegins";
  private boolean _searchShowInactiveFl = false;
  private int _searchAccountId = 0;

  private String _mFirstName = new String("");
  private String _mLastName = new String("");
  private String _mUserType = new String("");

  private int _selectedId = 0;
  /**
   * Holds value of property searchStoreId.
   */
  private String searchStoreId;

  private String userIDCode;

  private boolean isCorporateUser = false;
  private boolean receiveInvMissingEmail = false;
  private String defaultSite;
  private boolean restrictAcctInvoices =false;

  private AccountDataVector mAccountFilter;

  public int getSearchAccountId() {
    return this._searchAccountId;
  }

  public void setSearchAccountId(int v) {
    this._searchAccountId = v;
  }

  public int getSelectedId() {
    return this._selectedId;
  }

  public void setSelectedId(int v) {
    this._selectedId = v;
  }
  /**
   * <code>getSearchField</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getSearchField() {
    return (this._searchField);
  }

  /**
   * <code>setSearchField</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setSearchField(String pVal) {
    this._searchField = pVal;
  }

  public void setSearchRefNum(String pVal) {this._searchRefNum = pVal;}
  public String getSearchRefNum() {return (this._searchRefNum);}

  public void setSearchRefNumType(String pVal) {this._searchRefNumType = pVal;}
  public String getSearchRefNumType() {return (this._searchRefNumType);}

  /**
   * <code>getSearchType</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getSearchType() {return (this._searchType);}
  public void setSearchType(String pVal) {this._searchType = pVal; }

  public boolean getSearchShowInactiveFl() {return _searchShowInactiveFl; }
  public void setSearchShowInactiveFl(boolean pVal) { _searchShowInactiveFl = pVal; }



  /**
   * <code>getFirstName</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getFirstName() {
    return (this._mFirstName);
  }

  /**
   * <code>setFirstName</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setFirstName(String pVal) {
    this._mFirstName = pVal;
  }


  /**
   * <code>getLastName</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getLastName() {
    return (this._mLastName);
  }

  /**
   * <code>setLastName</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setLastName(String pVal) {
    this._mLastName = pVal;
  }


  /**
   * <code>getUserType</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getUserType() {
    return (this._mUserType);
  }

  /**
   * <code>setUserType</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setUserType(String pVal) {
    this._mUserType = pVal;
  }




  /**
   * Getter for property searchStoreId.
   * @return Value of property searchStoreId.
   */
  public String getSearchStoreId() {

    return this.searchStoreId;
  }

  /**
   * Setter for property searchStoreId.
   * @param searchStoreId New value of property searchStoreId.
   */
  public void setSearchStoreId(String searchStoreId) {

    this.searchStoreId = searchStoreId;
  }







/////////////////////////////////////


  private UserInfoData    _detail = UserInfoData.createValue();
  private String storeId, storeName;
  //private String          _accountId = "";
  //private String          _accountName = "";
  private AddressData     _accountAddress = AddressData.createValue();
  private String          _effDate = "",
          _expDate = "";

  private String mId = "0"; // Default to an invalid value.
  private String mUserTypeCd = "";
  private String mUserName = "";
  private String mFirstName = "";
  private String mLastName = "";
  private String mUserStatusCd = "";
  private String mPassword = "";
  private String mConfirmPassword = "";
  private String mPreferredLocale = "";
  private String mCustomerServiceRoleCd = "";
  private SelectableObjects stores;

  private java.util.ArrayList mUserGroupsReport = null;
  private IdVector _userAccountIds = new IdVector();
  private String UserStatusCd;
  private String distributionCenterId;
  private String[] memberOfGroups;
  private String manifestLabelHeight;
  private String manifestLabelWidth;
  private String manifestLabelType;
  private IdVector storeIds;
  private String manifestLabelPrintMode;
  private String profileUpdate;

  private String totalReadOnly;

  public String getTotalReadOnly() { return totalReadOnly; }
  public void setTotalReadOnly(String val) { totalReadOnly = val; }


  public UserRightsForm getBaseUserForm() {return mBaseUserForm;}
  public void setBaseUserForm(UserRightsForm pVal) {mBaseUserForm = pVal;};
  
  private SelectableObjects allStoresMainDb;

  /**
   * Return the user detail object
   */
  public UserInfoData getDetail() {
    if ( null != _detail ) {
      if ( null == _detail.getEmailData() ) {
        _detail.setEmailData(EmailData.createValue());
      }
      if ( null == _detail.getEmailData().getEmailAddress() ) {
        _detail.getEmailData().setEmailAddress("");
      }

    }
    return (this._detail);
  }

  /**
   * Set the user detail object
   */
  /*
  public ArrayList getRightsForms() {
    return _rightsForms;
  }
  */
  /*
  ArrayList _rightsForms = null;
  public UserRightsForm getUserRightsForm(int pIdx) {
    if (_rightsForms == null)
      return null;

    while (pIdx >= _rightsForms.size()) {
      _rightsForms.add(new UserRightsForm());
    }

    return (UserRightsForm)_rightsForms.get(pIdx);
  }
  */
  public void setDetail(UserInfoData detail) {
    mBaseUserForm.resetPermissions();
    if ( _detail.getUserData() != null ) {
      setId(String.valueOf(_detail.getUserData().getUserId()));
    }
    this._detail = detail;


  }

  public IdVector getUserAccountIds(){ return _userAccountIds; }

  /**
   * Get the account address.  Note that this is meaningful only for
   * those user types which belong to one account.  eg. application
   * administrators and customers.
   * @return  AddressData */
  public AddressData getAccountAddress() {
    return this._accountAddress;
  }

  /**
   * Set the account address.  Note that this is meaningful only for
   * those user types which belong to one account.  eg. application
   * administrators and customers.
   * @param v  */
  public void setAccountAddress(AddressData v) {
    this._accountAddress = v;
  }


  /**
   * Get the effective date field value.
   * @return  String representing the date value. */
  public String getEffDate() {
    Date effDate =  this._detail.getUserData().getEffDate();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    if(null != effDate) {
      this._effDate = simpleDateFormat.format(effDate);
    }
    return this._effDate;
  }


  /**
   * Set the effective date string value.
   * @param dateString  */
  public void setEffDate(String dateString) {
    this._effDate = dateString;
    if(null != dateString && ! "".equals(dateString)) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Date effDate =  new Date();
      try {
        effDate  = simpleDateFormat.parse(dateString);
      } catch (Exception e) {
        effDate = null;
      }
      this._detail.getUserData().setEffDate(effDate);
    } else {
      this._detail.getUserData().setEffDate(null);
    }
  }


  /**
   * Get the expiration date value.
   * @return  String, representing the expiration date. */
  public String getExpDate() {
    Date expDate =  this._detail.getUserData().getExpDate();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    if (null != expDate) {
      this._expDate = simpleDateFormat.format(expDate);
    }
    return this._expDate;
  }


  /**
   * Set the expiration date string value.
   * @param dateString  */
  public void setExpDate(String dateString) {
    this._expDate = dateString;
    if(null != dateString && ! "".equals(dateString)) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Date expDate = new Date();
      try {
        expDate  = simpleDateFormat.parse(dateString);
      } catch (Exception e) {
        expDate = null;
      }
      this._detail.getUserData().setExpDate(expDate);
    } else {
      this._detail.getUserData().setExpDate(null);
    }
  }





  /**
   *  Gets the AccountId attribute of the UserMgrDetailForm object
   *
   *@return    The AccountId value
   */
  /*
  public String getAccountId() {
    return this._accountId;
  }
  */

  /**
   *  Sets the AccountId attribute of the UserMgrDetailForm object
   *
   *@param  pAccountId  The new AccountId value
   */
  /*
  public void setAccountId(String pAccountId) {
    this._accountId = pAccountId;
  }
  */

  /**
   *  Gets the AccountName attribute of the UserMgrDetailForm object
   *
   *@return    The AccountName value
   */
  /*
  public String getAccountName() {
    return this._accountName;
  }
  */

  /**
   *  Sets the AccountName attribute of the UserMgrDetailForm object
   *
   *@param  pAccountName  The new AccountName value
   */
  /*
  public void setAccountName(String pAccountName) {
    this._accountName = pAccountName;
  }
  */


  /**
   * <code>getId</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getId() {
    return (this.mId);
  }

  /**
   * <code>setId</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setId(String pVal) {
    this.mId = pVal;
  }

  /**
   * <code>getUserTypeCd</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getUserTypeCd() {
    return (this.mUserTypeCd);
  }

  /**
   * <code>setUserTypeCd</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setUserTypeCd(String pVal) {
    this.mUserTypeCd = pVal;
  }

  /**
   * <code>getUserName</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getUserName() {
    return (this.mUserName);
  }

  /**
   * <code>setUserName</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setUserName(String pVal) {
    this.mUserName = pVal;
  }

  /**
   * <code>getFirstName</code> method.
   *
   * @return a <code>String</code> value
   */
  //  public String getFirstName() {
  //return (this.mFirstName);
  // }

  /**
   * <code>setFirstName</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  // public void setFirstName(String pVal) {
  //this.mFirstName = pVal;
  //  }

  /**
   * Get the value of mLastName.
   * @return value of mLastName.
   */
  //  public String getLastName() {
  //return mLastName;
  //  }

  /**
   * Set the value of mLastName.
   * @param v  Value to assign to mLastName.
   */
  //  public void setLastName(String  v) {
  //this.mLastName = v;
  // }


  /**
   * Get the value of UserStatusCd.
   * @return value of UserStatusCd.
   */
  public String getUserStatusCd() {
    return mUserStatusCd;
  }

  /**
   * Set the value of UserStatusCd.
   * @param v  Value to assign to UserStatusCd.
   */
  public void setUserStatusCd(String  v) {
    this.mUserStatusCd = v;
  }

  /**
   * <code>getPassword</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getPassword() {
    return (this.mPassword);
  }

  /**
   * <code>setPassword</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setPassword(String pVal) {
    this.mPassword = pVal;
  }

  /**
   * <code>getConfirmPassword</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getConfirmPassword() {
    return (this.mConfirmPassword);
  }

  /**
   * <code>setConfirmPassword</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setConfirmPassword(String pVal) {
    this.mConfirmPassword = pVal;
  }

  /**
   * <code>getPreferredLocale</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getPreferredLocale() {
    return (this.mPreferredLocale);
  }

  /**
   * <code>setPreferredLocale</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setPreferredLocale(String pVal) {
    this.mPreferredLocale = pVal;
  }


  /**
   * <code>getCustomerServiceRoleCd</code> method.
   *
   * @return a <code>String</code> value
   */
  public String getCustomerServiceRoleCd() {
    return (this.mCustomerServiceRoleCd);
  }

  /**
   * <code>setCustomerServiceRoleCd</code> method.
   *
   * @param pVal a <code>String</code> value
   */
  public void setCustomerServiceRoleCd(String pVal) {
    this.mCustomerServiceRoleCd = pVal;
  }

  public void reset(ActionMapping mapping,ServletRequest request) {
	if ("yes".equals(System.getProperty("multi.store.db"))) { //Multiple DB schemas 
		allStoresMainDb.handleStutsFormResetRequest();
	} else {
        if(stores != null){
          stores.handleStutsFormResetRequest();
        }
	}
    setTotalReadOnly("off");
    isCorporateUser= false;
    receiveInvMissingEmail = false;
    restrictAcctInvoices = false;    
  }

  /**
   * <code>reset</code> method, set the search fiels to null.
   *
   * @param mapping an <code>ActionMapping</code> value
   * @param request a <code>HttpServletRequest</code> value
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    mShowAllAcctFl = false;
    mConifiguredOnlyFl = false;
    mConfShowInactiveFl = false;
    mRemoveSiteAssocFl = false;
    _searchShowInactiveFl = false;
    isCorporateUser= false;
    restrictAcctInvoices = false;
    receiveInvMissingEmail = false;
      mConfSelectIds = new String[0];
      mConfDisplayIds = new String[0];

    if ( null == _detail.getUserData().getUserTypeCd()) {
      _detail.getUserData().setUserTypeCd
              (RefCodeNames.USER_TYPE_CD.CUSTOMER);
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date effDate = new Date();
    _effDate = simpleDateFormat.format(effDate);
    if ( null == _detail.getUserData().getUserStatusCd() ) {
      _detail.getUserData().setUserStatusCd
              (RefCodeNames.USER_STATUS_CD.LIMITED);
    }

    if ( null == _detail.getUserData().getPrefLocaleCd() ) {
      _detail.getUserData().setPrefLocaleCd("en_US");
    }

    this.mPassword = "";
    this.mConfirmPassword = "";
    this.mCustomerServiceRoleCd = "";
    mBaseUserForm.resetPermissions();
    if(mConfPermissions!=null) {
      Collection permColl = mConfPermissions.values();
      for(Iterator iter = permColl.iterator(); iter.hasNext();) {
        UserRightsForm urf = (UserRightsForm) iter.next();
        urf.reset(mapping,request);
      }

    }

    memberOfGroups = new String[0];

    if ("yes".equals(System.getProperty("multi.store.db"))) { //Multiple DB schemas
    	if(allStoresMainDb != null){
    		allStoresMainDb.handleStutsFormResetRequest();
    	}
    } else { //One DB schema
        if(stores != null){
          stores.handleStutsFormResetRequest();
        }
    }
  }


  /**
   * <code>validate</code> method is a stub.
   *
   * @param mapping an <code>ActionMapping</code> value
   * @param request a <code>HttpServletRequest</code> value
   * @return an <code>ActionErrors</code> value
   */
  public ActionErrors validate(ActionMapping mapping,
          HttpServletRequest request) {

    // Is there a currently logged on user?
    HttpSession currentSession = request.getSession();
    if ((currentSession == null) ||
            (currentSession.getAttribute(Constants.APIACCESS) == null)
            || (currentSession.getAttribute(Constants.APP_USER) == null)) {
      return null;
    }

    String action = request.getParameter("action");
    String change = request.getParameter("change");
    String siteconfigfl = request.getParameter("siteconfig");


    if (action == null) action = "";

    log.info("validate(): action="
            + action );

    // Don't check all the detail values when configuring
    // other user parameters.
    // this should be doing a 'getMessage("global.action.label.save")' - but
    // how?  we'll hardcode the string value here for now
    if (
            !action.equals("Save User Detail") &&
            !action.equals("Register")
            ) {
      return null;
    }

    boolean validateField = true;
    if (action.equals("Register")) {
      // When registering some fields will be set by
      // the system and not the form.
      validateField = false;
    }

    log.info("validate(): action=" + action
            + " validateField=" + validateField );
    if(null != change && "type".equals(change)) {
      return null;
    }

    // Perform the save validation.
    ActionErrors errors = new ActionErrors();
    log.info("validate():--->isEditableForUserFl =" + isEditableForUserFl);
    if (!isEditableForUserFl){
      validateField = false;
      errors.add("username", new ActionError("user.unallowable.update.error"));
      return errors;
    }

    // If creating new user...
    if (_detail.getUserData().getUserId() == 0) {
      // password and confirm password must both be set and equal
      if ((mPassword == null) || (mPassword.trim().length() < 1)) {
        errors.add("password", new ActionError("variable.empty.error", "Password"));
      } else if(!mPassword.equals(mConfirmPassword)) {
        errors.add("password", new ActionError("password.confirm.error"));
      }
    } else { // updating user
      // if password set, then password must equal confirm password
      if ((mPassword != null) && (mPassword.trim().length() > 0)) {
        if(!mPassword.equals(mConfirmPassword)) {
          errors.add("password", new ActionError("password.confirm.error"));
        }
      }//else errors.add("password", new ActionError("variable.empty.error", "Password"));
    }

    if ((_detail.getUserData().getUserName() == null) || (_detail.getUserData().getUserName().trim().length() < 1)) {
      errors.add("username", new ActionError("variable.empty.error", "UserName"));
    }

    if (validateField == true &&
            (_detail.getUserData().getUserTypeCd() == null ||
            _detail.getUserData().getUserTypeCd().trim().length() < 1)) {
      errors.add("username", new ActionError("variable.empty.error", "User Type"));
    }

    if (validateField == true &&
            (_effDate == null || _effDate.trim().length() < 1)) {
      errors.add("username", new ActionError("variable.empty.error", "User Active Date"));
    } else {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Date effDate = new Date();
      try {
        effDate  = simpleDateFormat.parse(_effDate);
      } catch (Exception e) {
        errors.add("username", new ActionError("variable.date.format.error", "User Active Date"));
        effDate = null;
      }
    }

    if ((_expDate == null) || (_expDate.trim().length() < 1)) {
    } else {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      Date expDate = new Date();
      try {
        expDate  = simpleDateFormat.parse(_expDate);
      } catch (Exception e) {
        errors.add("username", new ActionError("variable.date.format.error", "User Inactive Date"));
        expDate = null;
      }
    }

    if (validateField == true &&
            (_detail.getUserData().getUserStatusCd() == null ||
            _detail.getUserData().getUserStatusCd().trim().length() < 1)) {
      errors.add("username", new ActionError("variable.empty.error", "User Status"));
    }

    /*if (validateField == true &&
            (_detail.getUserData().getPrefLocaleCd() == null ||
            _detail.getUserData().getPrefLocaleCd().trim().length() < 1) //STJ - 3115
            ){
      errors.add("username", new ActionError("variable.empty.error", "User Preferred Language"));
    }*/
    
    // STJ - 3115
    if (validateField == true &&
            (_detail.getLanguageData().getShortDesc() == null ||
            		_detail.getLanguageData().getShortDesc().trim().length() < 1) 
            ){
      errors.add("username", new ActionError("variable.empty.error", "User Language"));
    }

    if ((_detail.getUserData().getFirstName() == null) || (_detail.getUserData().getFirstName().trim().length() < 1)) {
      errors.add("username", new ActionError("variable.empty.error", "Contact First Name"));
    }

    if ((_detail.getUserData().getLastName() == null) || (_detail.getUserData().getLastName().trim().length() < 1)) {
      errors.add("username", new ActionError("variable.empty.error", "Contact Last Name"));
    }
    
    if (!Utility.isSet(_detail.getCountryData().getCountryCode())) {
    	//STJ-4097
    	errors.add("country",new ActionError("variable.empty.error","Country"));
    }
    
    int assignedStoreQty = 0;
    String userTypeCd = _detail.getUserData().getUserTypeCd();
    if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(userTypeCd)) {
      if ("yes".equals(System.getProperty("multi.store.db"))) { //Multiple DB schemas
          int storeQty = allStoresMainDb.getValues().size();
          //int assignedStoreQty = 0;
          for(int jj=0; jj<storeQty; jj++) {
            if(allStoresMainDb.getSelected(jj)) {
              assignedStoreQty++;
            }
          } 
      } else { //One DB schema
    	  int storeQty = stores.getValues().size();
          //int assignedStoreQty = 0;
          for(int ii=0; ii<storeQty; ii++) {
            if(stores.getSelected(ii)) {
              assignedStoreQty++;
            } 
          }
      }
      
      log.info("validate(): assignedStoreQty = " + assignedStoreQty);
      
      if(!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd)
         && !RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)
         && !RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userTypeCd)
         && !RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd)
         && !RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd)
         && !RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd)
              ) {
        if(assignedStoreQty>1) {
          String errorMess = "Mulitiple stores for "+userTypeCd;
          errors.add("multystores",new ActionError("error.simpleGenericError",errorMess));
        }
      }
      if(assignedStoreQty==0) {
        String errorMess = "No stores assigned ";
        errors.add("multystores",new ActionError("error.simpleGenericError",errorMess));
      }
    }
    
    UserRightsForm userRights  = getConfPermForm(0);
	
    if (!Utility.isSet(getDetail().getEmailData().getEmailAddress())){
    	if (userRights.isOrderDetailNotificationEmail()
    			|| userRights.isOrderNotificationShippedEmail()
    			|| userRights.isOrderNotificationNeedsApprovalEmail()
    			|| userRights.isOrderNotificationApprovedEmail()
    			|| userRights.isOrderNotificationRejectedEmail()
    			|| userRights.isOrderNotificationModifiedEmail()){
    		String errorMess = "Email Address required if Order Notification Selected";
    		errors.add("emailAddress",new ActionError("error.simpleGenericError",errorMess));

    	}
    	if (userRights.isWorkOrderAcceptedByProviderNotification()
    			|| userRights.isWorkOrderCompletedNotification()
    			|| userRights.isWorkOrderRejectedByProviderNotification()){
    		String errorMess = "Email Address required if Work Order Notification Selected";
    		errors.add("emailAddress",new ActionError("error.simpleGenericError",errorMess));

    	}
    	if (userRights.isCutoffTimeReminderEmail()
    			|| userRights.isPhysicalInvNonComplSiteListingEmail()
    			|| userRights.isPhysicalInvCountsPastDueEmail()){
    		String errorMess = "Email Address required if Corporate Order Notification Selected";
    		errors.add("emailAddress",new ActionError("error.simpleGenericError",errorMess));

    	}
    }
    
    try {
    	if (Utility.isSet(userRights.getCutoffTimeReminderEmailCount())){
    		int count = Integer.parseInt(userRights.getCutoffTimeReminderEmailCount());
    		if (count < 0){
    			errors.add("error",new ActionError("error.simpleGenericError","Cutoff Time Reminder Email Count can not be negative number: "+userRights.getCutoffTimeReminderEmailCount()));
    		}
    	}
	}catch(NumberFormatException e){
		errors.add("error",new ActionError("error.simpleGenericError","Wrong Cutoff Time Reminder Email Count format: "+userRights.getCutoffTimeReminderEmailCount()));
	}
    
    
  //STJ-3846
    if(_detail.getEmailData()!=null) {
          _detail.getEmailData().setEmailAddress(_detail.getEmailData().getEmailAddress().trim());
    }
        EmailValidator.validateEmail(request, errors,  getDetail().getEmailData()
                .getEmailAddress());
    return errors;

  }

  /** Getter for property _accountAddress.
   * @return Value of property _accountAddress.
   */
  public com.cleanwise.service.api.value.AddressData get_accountAddress() {
    return _accountAddress;
  }

  /** Setter for property _accountAddress.
   * @param _accountAddress New value of property _accountAddress.
   */
  public void set_accountAddress(com.cleanwise.service.api.value.AddressData _accountAddress) {
    this._accountAddress = _accountAddress;
  }

  /** Getter for property memberOfGroups.
   * @return Value of property memberOfGroups.
   *
   */
  public String[] getMemberOfGroups() {
    return this.memberOfGroups;
  }

  /** Setter for property memberOfGroups.
   * @param memberOfGroups New value of property memberOfGroups.
   *
   */
  public void setMemberOfGroups(String[] memberOfGroups) {
    this.memberOfGroups = memberOfGroups;
  }


  /** Getter for property distributionCenterId.
   * @return Value of property distributionCenterId.
   *
   */
  public String getDistributionCenterId() {
    if (!Utility.isSet(this.distributionCenterId)) {
      this.distributionCenterId = "NA" ;
    }
    return this.distributionCenterId;
  }

  /** Setter for property distributionCenterId.
   * @param distributionCenterId New value of property distributionCenterId.
   *
   */
  public void setDistributionCenterId(String distributionCenterId) {
    this.distributionCenterId = distributionCenterId;
  }

  /** Getter for property manifestLabelHeight.
   * @return Value of property manifestLabelHeight.
   *
   */
  public String getManifestLabelHeight() {
    return this.manifestLabelHeight;
  }

  /** Setter for property manifestLabelHeight.
   * @param manifestLabelHeight New value of property manifestLabelHeight.
   *
   */
  public void setManifestLabelHeight(String manifestLabelHeight) {
    this.manifestLabelHeight = manifestLabelHeight;
  }

  /** Getter for property manifestLabelWidth.
   * @return Value of property manifestLabelWidth.
   *
   */
  public String getManifestLabelWidth() {
    return this.manifestLabelWidth;
  }

  /** Setter for property manifestLabelWidth.
   * @param manifestLabelWidth New value of property manifestLabelWidth.
   *
   */
  public void setManifestLabelWidth(String manifestLabelWidth) {
    this.manifestLabelWidth = manifestLabelWidth;
  }

  /** Getter for property manifestLabelType.
   * @return Value of property manifestLabelType.
   *
   */
  public String getManifestLabelType() {
    return this.manifestLabelType;
  }

  /** Setter for property manifestLabelType.
   * @param manifestLabelType New value of property manifestLabelType.
   *
   */
  public void setManifestLabelType(String manifestLabelType) {
    this.manifestLabelType = manifestLabelType;
  }

  /** Getter for property manifestLabelPrintMode.
   * @return Value of property manifestLabelPrintMode.
   *
   */
  public String getManifestLabelPrintMode() {
    return this.manifestLabelPrintMode;
  }

  /**
   * Getter for property stores.
   * @return Value of property stores.
   */
  public SelectableObjects getStores() {

    return this.stores;
  }

  /**
   * Setter for property stores.
   * @param stores New value of property stores.
   */
  public void setStores(SelectableObjects stores) {

    this.stores = stores;
  }

  /** Setter for property manifestLabelPrintMode.
   * @param manifestLabelPrintMode New value of property manifestLabelPrintMode.
   *
   */
  public void setManifestLabelPrintMode(String manifestLabelPrintMode) {
    this.manifestLabelPrintMode = manifestLabelPrintMode;
  }

  public String getStoreId() {

    return this.storeId;
  }

  public void setStoreId(String storeId) {

    this.storeId = storeId;
  }

  public String getStoreName() {

    return this.storeName;
  }

  public void setStoreName(String storeName) {

    this.storeName = storeName;
  }

  public java.util.ArrayList getGroupsReport() {
    if ( null == mUserGroupsReport ) {
      mUserGroupsReport = new java.util.ArrayList();
    }
    return mUserGroupsReport;
  }
  public void setGroupsReport(java.util.ArrayList v) {
    mUserGroupsReport = v;
  }


  /**
   * Getter for property passwordOnlyUpdate.
   * @return Value of property passwordOnlyUpdate.
   */
  public String getProfileUpdate()  {

    return this.profileUpdate;
  }

  /**
   * Setter for property passwordOnlyUpdate.
   * @param passwordOnlyUpdate New value of property passwordOnlyUpdate.
   */
  public void setProfileUpdate(String profileUpdate)  {

    this.profileUpdate = profileUpdate;
  }

  //=================== Configuration ==============================
    private String mConfFunction = "";
    private boolean mConifiguredOnlyFl = false;
    private boolean mConfShowInactiveFl = false;
    private boolean mRemoveSiteAssocFl = false;
    private String mConfSearchField = "";
    private String mConfSearchType = "nameBegins";
    private String mConfCity = "";
    private String mConfState = "";
    private IdVector mConfPermAccountIds = null;
    private boolean mConfPermAcctFilterFl = false;
    private boolean mShowAllAcctFl = false;
    private boolean mMergeAccountPermFl = true;
    private LinkedList mConfPermKeys = null;
    private HashMap mConfPermissions = null;
    private HashMap mConfPermAccounts = null;
    //private String mConfAccountName;
    private String[] mConfAsocSiteIds = null;
    private String[] mConfSelectIds = null;
    private String[] mConfDisplayIds = null;
  private boolean isEditableForUserFl;

  public String getConfFunction() {return mConfFunction;}
    public void setConfFunction(String pVal) {mConfFunction = pVal; }

    public boolean getConifiguredOnlyFl() {return mConifiguredOnlyFl;}
    public void setConifiguredOnlyFl(boolean pVal) {mConifiguredOnlyFl = pVal; }

    public boolean getConfShowInactiveFl() {return mConfShowInactiveFl;}
    public void setConfShowInactiveFl(boolean pVal) {mConfShowInactiveFl = pVal; }

    public boolean getRemoveSiteAssocFl() {return mRemoveSiteAssocFl;}
    public void setRemoveSiteAssocFl(boolean pVal) {mRemoveSiteAssocFl = pVal; }

    public String getConfSearchField() {return mConfSearchField;}
    public void setConfSearchField(String pVal) {mConfSearchField = pVal; }

    public String getConfSearchType() {return mConfSearchType;}
    public void setConfSearchType(String pVal) {mConfSearchType = pVal; }

    public String getConfCity() {return mConfCity;}
    public void setConfCity(String pVal) {mConfCity = pVal; }

    public String getConfState() {return mConfState;}
    public void setConfState(String pVal) {mConfState = pVal; }

    //public int getConfAccountId() {return mConfAccountId;}
    //public void setConfAccountId(int pVal) {mConfAccountId = pVal; }

    //public String getConfAccountName() {return mConfAccountName;}
    //public void setConfAccountName(String pVal) {mConfAccountName = pVal; }

    public IdVector getConfPermAccountIds() {return mConfPermAccountIds;}
    public void setConfPermAccountIds(IdVector pVal) {mConfPermAccountIds = pVal; }

    public boolean getConfPermAcctFilterFl() {return mConfPermAcctFilterFl;}
    public void setConfPermAcctFilterFl(boolean pVal) {mConfPermAcctFilterFl = pVal; }

    public boolean getShowAllAcctFl() {return mShowAllAcctFl;}
    public void setShowAllAcctFl(boolean pVal) {mShowAllAcctFl = pVal; }

    public boolean getMergeAccountPermFl() {return mMergeAccountPermFl;}
    public void setMergeAccountPermFl(boolean pVal) {mMergeAccountPermFl = pVal; }

    public LinkedList getConfPermKeys() {return mConfPermKeys;}
    public void setConfPermKeys(LinkedList pVal) {mConfPermKeys = pVal; }

    public HashMap getConfPermissions() {return mConfPermissions;}
    public void setConfPermissions(HashMap pVal) {mConfPermissions = pVal; }
    public void setConfPermForm(UserRightsForm pUrf, int pInd) {
      if(pInd!=0) {
        mConfPermissions.put(new Integer(pInd),pUrf);
      }
    }
    public UserRightsForm getConfPermForm(int pInd) {
      if(pInd==0) {
        return mBaseUserForm;
      }
      UserRightsForm urf =
              (UserRightsForm)  mConfPermissions.get(new Integer(pInd));
      return urf;
    }

    public HashMap getConfPermAccounts() {return mConfPermAccounts;}
    public void setConfPermAccounts(HashMap pVal) {mConfPermAccounts = pVal; }

    public String[] getConfAsocSiteIds() {return mConfAsocSiteIds;}
    public void setConfAsocSiteIds(String[] pVal) {mConfAsocSiteIds = pVal; }

    public String[] getConfSelectIds() {return mConfSelectIds;}
    public void setConfSelectIds(String[] pVal) {mConfSelectIds = pVal; }

    public String[] getConfDisplayIds() {return mConfDisplayIds;}
    public void setConfDisplayIds(String[] pVal) {mConfDisplayIds = pVal; }
    
    public String getDefaultSite(){
		return defaultSite;
    }
    public void setDefaultSite(String pDefaultSite){
    	defaultSite = pDefaultSite;
    }

    public String getUserIDCode() {
        return userIDCode;
    }

    public void setUserIDCode(String userIDCode) {
        this.userIDCode = userIDCode;
    }

    public boolean getIsCorporateUser() {return isCorporateUser;}
    public void setIsCorporateUser(boolean pVal) {isCorporateUser = pVal; }
    
    public boolean isRestrictAcctInvoices() {return restrictAcctInvoices;}
    public void setRestrictAcctInvoices(boolean pVal) {restrictAcctInvoices = pVal; }

    public boolean getReceiveInvMissingEmail() {return receiveInvMissingEmail;}

    public boolean isIsEditableForUserFl() {
      return isEditableForUserFl;
    }

    public void setReceiveInvMissingEmail(boolean pVal) {receiveInvMissingEmail = pVal; }

    public void setIsEditableForUserFl(boolean isEditableForUserFl) {
      this.isEditableForUserFl = isEditableForUserFl;
    }

    public void setAccountFilter(AccountDataVector pVal) {mAccountFilter = pVal;}
    public AccountDataVector getAccountFilter() {return mAccountFilter;}
    
    /**
     * Getter for property stores.
     * @return Value of property stores.
     */
    public SelectableObjects getAllStoresMainDb() {

        return this.allStoresMainDb;
    }

    /**
     * Setter for property stores.
     * @param stores New value of property stores.
     */
    public void setAllStoresMainDb(SelectableObjects allStoresMainDb) {

        this.allStoresMainDb = allStoresMainDb;
    }

}
