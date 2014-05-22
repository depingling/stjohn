/**
 * Title:        UserMgrDetailForm
 * Description:  This is the Struts ActionForm class for 
 * the user management page.
 * Purpose:      Strut support for managing users.      
 * Copyright:    Copyright (c) 2001
 * Company:      Cleanwise, Inc.
 * @author       durval
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


/**
 * Form bean for the user manager page. 
 *
 * @author durval
 */
public final class UserMgrDetailForm  extends BaseUserDetailForm {

    private UserInfoData    _detail = UserInfoData.createValue();
    private String storeId, storeName;
    private String          _accountId = "";
    private String          _accountName = "";
    private AddressData     _accountAddress = AddressData.createValue();
    private String          _effDate = "",
    _expDate = "";    
    
    private String mId = "0"; // Default to an invalid value.
    private String mUserTypeCd = "";
    private String mUserName = "";
    private String mFirstName = "";
    private String mLastName = "";
    private String mUserStatusCd = "";
    private String mOldPassword = "";
    private String mPassword = "";
    private String mConfirmPassword = "";
    private String mPreferredLocale = "";
    private String mCustomerServiceRoleCd = "";
    private SelectableObjects stores;
    private ArrayList<ShoppingRestrictionsView> mShoppingRestrictionsViews;
    private boolean mExistRestrictionDays = false;

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
    public ArrayList getRightsForms() {
        return _rightsForms;
    }
    
    ArrayList _rightsForms = null;
    public UserRightsForm getUserRightsForm(int pIdx)
    {
        if (_rightsForms == null)
            return null;

        while (pIdx >= _rightsForms.size()) {
            _rightsForms.add(new UserRightsForm());
        }

        return (UserRightsForm)_rightsForms.get(pIdx);
    }
    
    public void setDetail(UserInfoData detail) {
	this.resetPermissions();
	if ( _detail.getUserData() != null ) {
	    setId(
		  String.valueOf
		  (_detail.getUserData().getUserId())
		  );
	}
        this._detail = detail;
        
        UserAccountRightsViewVector rightsv =
            detail.getUserAccountRights();

        _rightsForms = new ArrayList();
        for ( int i = 0; rightsv != null &&
                  i < rightsv.size(); i++)
        {
            UserAccountRightsView acctrights =
                (UserAccountRightsView)rightsv.get(i);
            UserRightsForm urf = new UserRightsForm	
                (detail.getUserData(),
                 acctrights.getAccountData(),
                 acctrights.getUserSettings().getValue()
                 );
		 _rightsForms.add(urf);

		 _userAccountIds.add
		     (new Integer(acctrights.getAccountData().getBusEntityId()));
        }

	// Set the form values.
	UserData ud = detail.getUserData();
	UserRightsTool urt = new UserRightsTool(ud);
	this.setShowPrice(urt.getShowPrice());
	this.setBrowseOnly(urt.isBrowseOnly());
	this.setContractItemsOnly(urt.isUserOnContract());
	this.setOnAccount(urt.getOnAccount());
	this.setCreditCard(urt.getCreditCardFlag());
    this.setOtherPayment(urt.getOtherPaymentFlag());
	this.setPoNumRequired(urt.getPoNumRequired());
	this.setSalesPresentationOnly(urt.isPresentationOnly());
	this.setNoReporting(urt.isNoReporting());
	this.setOrderNotificationApprovedEmail(urt.getsEmailOrderApproved());
	this.setOrderNotificationModifiedEmail(urt.getsEmailOrderModifications());
	this.setOrderNotificationRejectedEmail(urt.getsEmailOrderRejection());
	this.setOrderNotificationNeedsApprovalEmail(urt.getsEmailForApproval());
    this.setOrderDetailNotificationEmail(urt.getEmailOrderDetailNotification());
    this.setOrderNotificationShippedEmail(urt.getOrderNotificationShipped());
    this.setCanEditShipTo(urt.canEditShipTo());
	this.setCanEditBillTo(urt.canEditBillTo());
	this.setReportingManager(urt.isReportingManager());							//aj
	this.setReportingAssignAllAccts(urt.isReportingAssignAllAccts());			//aj

	this.setCustomerServiceRoleCd("");
	if (urt.isCustServiceApprover()) {
	    this.setCustomerServiceRoleCd
		(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER);
	}
	if (urt.isCustServicePublisher()){
	    this.setCustomerServiceRoleCd
		(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.PUBLISHER);
	}
	if (urt.isCustServiceViewer()){
	    this.setCustomerServiceRoleCd
		(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.VIEWER);
	}
	
	if (RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER.equals
	    (ud.getWorkflowRoleCd())) {
	    this.setCanApproveOrders(true);
	}
	
    }

    private IdVector _userAccountIds = new IdVector();
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
            }
            catch (Exception e) {
                effDate = null;
            }    
            this._detail.getUserData().setEffDate(effDate);
        }
        else {
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
            }
            catch (Exception e) {
                expDate = null;
            }
            this._detail.getUserData().setExpDate(expDate);
        }
        else {
            this._detail.getUserData().setExpDate(null);
        }
    }


    
        

    /**
     *  Gets the AccountId attribute of the UserMgrDetailForm object
     *
     *@return    The AccountId value
     */
    public String getAccountId() {
        return this._accountId;
    }


    /**
     *  Sets the AccountId attribute of the UserMgrDetailForm object
     *
     *@param  pAccountId  The new AccountId value
     */
    public void setAccountId(String pAccountId) {
        this._accountId = pAccountId;
    }


    /**
     *  Gets the AccountName attribute of the UserMgrDetailForm object
     *
     *@return    The AccountName value
     */
    public String getAccountName() {
        return this._accountName;
    }


    /**
     *  Sets the AccountName attribute of the UserMgrDetailForm object
     *
     *@param  pAccountName  The new AccountName value
     */
    public void setAccountName(String pAccountName) {
        this._accountName = pAccountName;
    }
    
        
    
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
    
    String UserStatusCd;
    
    /** Holds value of property memberOfGroups. */
    private String[] memberOfGroups;
    
    /** Holds value of property distributionCenterId. */
    private String distributionCenterId;
    
    /** Holds value of property manifestLabelHeight. */
    private String manifestLabelHeight;
    
    /** Holds value of property manifestLabelWidth. */
    private String manifestLabelWidth;
    
    /** Holds value of property manifestLabelType. */
    private String manifestLabelType;

    /**
     * Holds value of property storeIds.
     */
    private IdVector storeIds;
    
    /** Holds value of property manifestLabelPrintMode. */
    private String manifestLabelPrintMode;
    
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
        if(stores != null){
            stores.handleStutsFormResetRequest();
        }
    }
    
    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

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
        this.mOldPassword = "";
        this.mCustomerServiceRoleCd = "";
        resetPermissions();
        for ( int i = 0; null != _rightsForms &&
          i < _rightsForms.size(); i++ )
        {
            UserRightsForm urf = (UserRightsForm)_rightsForms.get(i);
            urf.reset(mapping,request);
        }
        memberOfGroups = new String[0];

        if(stores != null){
            stores.handleStutsFormResetRequest();
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

	// Don't check all the detail values when configuring
	// other user parameters.
	// this should be doing a 'getMessage("global.action.label.save")' - but
	// how?  we'll hardcode the string value here for now
        if (
	    !action.equals("Save User Detail") && 
	    !action.equals("Register") &&
	    !"updateProfile".equals(action)
	    ) {
            return null;
        }

	boolean validateField = true;
	if (action.equals("Register")) {
	    // When registering some fields will be set by
	    // the system and not the form.
	    validateField = false;
	}

        if(null != change && "type".equals(change)) {
            return null;
        }
        
        // Perform the save validation.
        ActionErrors errors = new ActionErrors();

	// If creating new user...
	if (_detail.getUserData().getUserId() == 0) {
	    // password and confirm password must both be set and equal
	    if ((mPassword == null) || (mPassword.trim().length() < 1)) {
		errors.add("password", new ActionError("variable.empty.error", "Password"));            
	    }
	    else if(!mPassword.equals(mConfirmPassword)) {
		errors.add("password", new ActionError("password.confirm.error"));            
	    }
	} else { // updating user
	    // if password set, then password must equal confirm password
	    if ((mPassword != null) && (mPassword.trim().length() > 0)) {
		if(!mPassword.equals(mConfirmPassword)) {
		    errors.add("password", new ActionError("password.confirm.error"));            
		}
	    }
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
        }        
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date effDate = new Date();
            try {
                effDate  = simpleDateFormat.parse(_effDate);  
            }
            catch (Exception e) {
                errors.add("username", new ActionError("variable.date.format.error", "User Active Date"));
                effDate = null;
            }            
        }

        if ((_expDate == null) || (_expDate.trim().length() < 1)) {
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date expDate = new Date();
            try {
                expDate  = simpleDateFormat.parse(_expDate);  
            }
            catch (Exception e) {
                errors.add("username", new ActionError("variable.date.format.error", "User Inactive Date"));
                expDate = null;
            }            
        }
        

        if (validateField == true && 
	    (_detail.getUserData().getUserStatusCd() == null || 
	     _detail.getUserData().getUserStatusCd().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "User Status"));            
        }        
        
        if (validateField == true && 
	    (_detail.getUserData().getPrefLocaleCd() == null || 
	     _detail.getUserData().getPrefLocaleCd().trim().length() < 1) 
	    ){
            errors.add("username", new ActionError("variable.empty.error", "User Preferred Language"));            
        }        
        
        if ((_detail.getUserData().getFirstName() == null) || (_detail.getUserData().getFirstName().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact First Name"));            
        }        
        
        if ((_detail.getUserData().getLastName() == null) || (_detail.getUserData().getLastName().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact Last Name"));            
        }        
       /* phone requirement was removed
        veronika 06/20/2011 STJ-4189
        if ((_detail.getPhone().getPhoneNum() == null) || (_detail.getPhone().getPhoneNum().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact Phone Number"));            
        }
        */

        /*
        Email requirement was removed.
        durval, 10/19/2004
        if ((_detail.getEmailData().getEmailAddress() == null) || (_detail.getEmailData().getEmailAddress().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact Email Address"));            
        } 
        */
        String emailAddress = (_detail.getEmailData() == null) ? "" : _detail.getEmailData().getEmailAddress().trim();
        EmailValidator.validateEmail(request, errors,
                "shop.userProfile.text.email", null, emailAddress);

        /* all requirements was removed
            veronika 06/20/2011 STJ-4189

        if (((_detail.getAddressData().getAddress1() == null) || (_detail.getAddressData().getAddress1().trim().length() < 1)) 
            && ((_detail.getAddressData().getAddress2() == null) || (_detail.getAddressData().getAddress2().trim().length() < 1)))  {
            errors.add("username", new ActionError("variable.empty.error", "Contact Address"));            
        }        
        
        if ((_detail.getAddressData().getCity() == null) || (_detail.getAddressData().getCity().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact Address City"));            
        }        
        
        if ((_detail.getAddressData().getCountryCd() == null) || (_detail.getAddressData().getCountryCd().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact Address Country"));            
        }        
        
        if ((_detail.getAddressData().getStateProvinceCd() == null) || (_detail.getAddressData().getStateProvinceCd().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact Address State"));            
        }        
        
        if ((_detail.getAddressData().getPostalCode() == null) || (_detail.getAddressData().getPostalCode().trim().length() < 1)) {
            errors.add("username", new ActionError("variable.empty.error", "Contact Zip Code"));            
        }        
        */        
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
        if ( this.distributionCenterId == null ) {         
            this.distributionCenterId = "" ; 
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

    private java.util.ArrayList mUserGroupsReport = null;
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
     * Holds value of property profileUpdate.
     */
    private String profileUpdate;

    /**
     * Getter for property passwordOnlyUpdate.
     * @return Value of property passwordOnlyUpdate.
     */
    public String getProfileUpdate()  {

        return this.profileUpdate;
    }

    /**
     * Setter for property passwordOnlyUpdate.
     * @param profileUpdate New value of property passwordOnlyUpdate.
     */
    public void setProfileUpdate(String profileUpdate)  {

        this.profileUpdate = profileUpdate;
    }

    public String getOldPassword() {
        return mOldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.mOldPassword = oldPassword;
    }

    public ArrayList<ShoppingRestrictionsView> getShoppingRestrictionsViews() {
        return mShoppingRestrictionsViews;
    }

    public void setShoppingRestrictionsViews(ArrayList<ShoppingRestrictionsView> items) {
        mShoppingRestrictionsViews = items;
        mExistRestrictionDays = 
            ShoppingRestrictionsUtil.existRestrictionDays(mShoppingRestrictionsViews);
    }

    private ShoppingRestrictionsView getShoppingRestrictionsView(int id) {
        if (mShoppingRestrictionsViews == null) {
            return null;
        }
        if (id < 0 || id >= mShoppingRestrictionsViews.size()) {
            return null;
        }
        return (ShoppingRestrictionsView) mShoppingRestrictionsViews.get(id);
    }

    public boolean getExistRestrictionDays() {
        return mExistRestrictionDays;
    }

    public void setExistRestrictionDays(boolean existRestrictionDays) {
        return;
    }

}




