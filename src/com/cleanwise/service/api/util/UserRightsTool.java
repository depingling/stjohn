package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.*;

import java.io.Serializable;

/**
 * Encapsulates the logic around user rights based off both the user type code and the user 
 * role code.  Provides methods to manipulate the user role code encoded String. For example:
 * userRightsTool.setBrowseOnly(true);
 * userRightsTool.setPoNumRequiered(false);
 * Sting newRoleCode = userRightsTool.makePermissionsToken();
 * will modify the permissions so that browse only is true and the po number is not requiered
 * 
 * @author Dvieira
 * @created September 18, 2003
 */

public final class UserRightsTool implements Serializable {
	private UserData _user = null;

	private String _userRights = "";

	private boolean _showPrice = false, _browseOnly = false,
			_contractItemsOnly = false, _onAccount = false, _otherPmt = false,
			_creditCard = false, _poNumRequired = false,
			_canApproveOrders = false,
			_orderNotificationNeedsApprovalEmail = false,
            _emailOrderDetailNotification=false,
            _orderNotificationShipped=false,
            _orderNotificationApprovedEmail = false,
			_orderNotificationRejectedEmail = false,
			_orderNotificationModifiedEmail = false, _canEditShipTo = false,
			_canEditBillTo = false, _noReporting = false,
			_salesPresentationOnly = false, _custServiceApprover = false,
			_custServicePublisher = false, _custServiceViewer = false,
			_reportingManager = false,			//aj 
			_reportingAssignAllAccts = false,			//aj
			_workOrderCompletedNotification = false,
			_workOrderAcceptedByProviderNotification = false,
			_workOrderRejectedByProviderNotification = false,
			_cutoffTimeReminderEmail = false,
			_physicalInvNonComplSiteListingEmail = false,
			_physicalInvCountsPastDueEmail = false;

	/**
	 * Called by the constructor
	 * 
	 * @param pUserData
	 *            a user data object to use as the source of permissions
	 * @param pRightsOverlay
	 *            the user rights to be used if not present uses the rights
	 *            String present in the user object (the user role code
	 *            property).
	 */
	private void init(UserData pUserData, String pRightsOverlay) {
		_user = pUserData;
		if (null != pRightsOverlay && pRightsOverlay.length() > 0) {
			_userRights = pRightsOverlay;
		} else {
			_userRights = _user.getUserRoleCd();
		}

		if (null == _userRights)
			_userRights = "";

		_showPrice = parseShowPrice();
		_browseOnly = parseBrowseOnly();
		_contractItemsOnly = parseUserOnContract();
		_onAccount = parseOnAccount();
		_otherPmt = parseOtherPaymentFlag();
		_creditCard = parseCreditCardFlag();
		_poNumRequired = parsePoNumRequired();
		_canApproveOrders = parseCanApprovePurchases();
		_orderNotificationNeedsApprovalEmail = parseGetsEmailForApproval();
		_orderNotificationApprovedEmail = parseGetsEmailOrderApproved();
        _emailOrderDetailNotification = parseGetsEmailOrderDetailApproved();
        _orderNotificationShipped = parseGetsEmailOrderShipped();
        _orderNotificationRejectedEmail = parseGetsEmailOrderRejection();
		_orderNotificationModifiedEmail = parseGetsEmailOrderModifications();
		_noReporting = parseNoReporting();
		_salesPresentationOnly = parsePresentationOnly();
		_canEditShipTo = parseCanEditShipTo();
		_canEditBillTo = parseCanEditBillTo();
		_reportingManager = parseReportingManager();			//aj 
		_reportingAssignAllAccts = parseReportingAssignAllAccts();			//aj
		_workOrderCompletedNotification = parseGetsEmailWorkOrderCompleted();
		_workOrderAcceptedByProviderNotification = parseGetsEmailWorkOrderAccepted();
		_workOrderRejectedByProviderNotification = parseGetsEmailWorkOrderRejected();
		_cutoffTimeReminderEmail = parseGetsEmailCutoffTimeReminder();
		_physicalInvNonComplSiteListingEmail = parseGetsEmailPhysicalInvNonComplSiteListing();
		_physicalInvCountsPastDueEmail = parseGetsEmailPhysicalInvCountsPastDue();

	}

	/**
	 * Constructor for the UserRightsTool object
	 * 
	 * @param pUserData
	 *            a user data object to use as the source of permissions
	 */
	public UserRightsTool(UserData pUserData) {
		init(pUserData, pUserData.getUserRoleCd());
	}

	/**
	 * Constructor for the UserRightsTool object
	 * 
	 * @param pUserData
	 *            a user data object to use as the source of permissions
	 * @param pRightsOverlay
	 *            the user rights to be used if not present uses the rights
	 *            String present in the user object.
	 */
	public UserRightsTool(UserData pUserData, String pRightsOverlay) {
		init(pUserData, pRightsOverlay);
	}

	/**
	 * -------------------- Methods based off the user type -------------------
	 */
	public boolean isaCustomer() {
		String utype = _user.getUserTypeCd();
		return (utype.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER));
	}

	public boolean isaMSB() {
		String utype = _user.getUserTypeCd();
		return (utype.equals(RefCodeNames.USER_TYPE_CD.MSB));
	}

	public boolean isaReportingUser() {
		String utype = _user.getUserTypeCd();
		return (utype.equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER));
	}

	public boolean isaRegistrationUser() {
		String utype = _user.getUserTypeCd();
		return (utype.equals(RefCodeNames.USER_TYPE_CD.REGISTRATION_USER));
	}

	public boolean isaDistributor() {
		String utype = _user.getUserTypeCd();
		return (utype.equals(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR));
	}

	public boolean hasAccounts() {
		return isaMSB() || 
		    isaRegistrationUser() || 
		    isaReportingUser() || 
		    isaDistributor() ||
		    isaCustomer();
	}

	/**
	 * Creates a permissions token suitable for storage in the userRoleCode
	 * property of the user object.
	 */
	public String makePermissionsToken() {

		String permissionToken = "";
		if (getShowPrice()) {
			permissionToken += "SP" + "^";
		}
		if (isBrowseOnly()) {
			permissionToken += "BO" + "^";
		}
		if (this.isUserOnContract()) {
			permissionToken += "CI" + "^";
		}
		if (getOnAccount()) {
			permissionToken += "OA" + "^";
		}
		if (getOtherPaymentFlag()) {
			permissionToken += "OPmt" + "^";
		}
		if (getCreditCardFlag()) {
			permissionToken += "CC" + "^";
		}
		if (getPoNumRequired()) {
			permissionToken += "PR" + "^";
		}

		if (this.isPresentationOnly()) {
			permissionToken += "SaP" + "^";
		}
		if (isNoReporting()) {
			permissionToken += "NR" + "^";
		}
		//start aj
		if (isReportingManager()) {
			permissionToken += "RepM" + "^";
		}
		if (isReportingAssignAllAccts()) {
			permissionToken += "RepOA" + "^";
		}
		//end aj
		// Order notification emails.
		if (getsEmailForApproval()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_ORDER_NEEDS_APPROVAL
					+ "^";
		}
		if (getsEmailOrderApproved()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_ORDER_WAS_APPROVED
					+ "^";
		}
		if (getsEmailOrderRejection()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_ORDER_WAS_REJECTED
					+ "^";
		}
		if (getsEmailOrderModifications()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_ORDER_WAS_MODIFIED
					+ "^";
		}
        if (getEmailOrderDetailNotification()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_ORDER_DETAIL_APPROVED
					+ "^";
		}
        if (getOrderNotificationShipped()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_ORDER_SHIPPED
					+ "^";
		}
                if (getWorkOrderCompletedNotification()) {
                        permissionToken += UserInfoData.USER_GETS_EMAIL_WORK_ORDER_COMPLETED
                                        + "^";
                }
                if (getWorkOrderAcceptedByProviderNotification()) {
                        permissionToken += UserInfoData.USER_GETS_EMAIL_WORK_ORDER_ACCEPTED
                                        + "^";
                }
                if (getWorkOrderRejectedByProviderNotification()) {
                        permissionToken += UserInfoData.USER_GETS_EMAIL_WORK_ORDER_REJECTED
                                        + "^";
                }
        if (true == canEditShipTo()) {
			permissionToken += Constants.UserRole.CAN_EDIT_SHIPTO;
		}
		if (true == canEditBillTo()) {
			permissionToken += Constants.UserRole.CAN_EDIT_BILLTO;
		}

		if (canApprovePurchases()) {
			permissionToken += RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER
					+ "^";
		}
		if (getCutoffTimeReminderEmail()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_CUTOFF_TIME_REMINDER
					+ "^";
		}
        if (getPhysicalInvNonComplSiteListingEmail()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_PHYSICAL_INV_NON_COMPL_SITE_LISTING
					+ "^";
		}
        if (getPhysicalInvCountsPastDueEmail()) {
			permissionToken += UserInfoData.USER_GETS_EMAIL_PHYSICAL_INV_COUNTS_PAST_DUE
					+ "^";
		}
		if (null == permissionToken) {
			permissionToken = "";
		}
		return permissionToken;
	}

	/**
	 * Based off all the various user permissions returns true if this user
	 * should be allowed to make a purchase.
	 */
	public boolean canMakePurchases() {

		if (null == _user)
			return false;
		boolean f_onaccount = getOnAccount(), f_oncc = getCreditCardFlag(), f_otherpmt = getOtherPaymentFlag(), f_allowp = getAllowPurchase(), f_presonly = isPresentationOnly(), f_returnf = ((f_presonly == false)
				&& (f_allowp == true) && (f_onaccount == true || f_oncc == true || f_otherpmt));

		return f_returnf;
	}

	/**
	 * Gets the allowPurchase attribute of the UserRightsTool object
	 * 
	 * @return The allowPurchase value
	 */
	public boolean getAllowPurchase() {

		if (isBrowseOnly() == true) {
			// Purchases are not allowed for this user.
			return false;
		}
		return true;
	}

	
	/**------------------------Methods based on user role code----------------*/
	
	public boolean getOnAccount() {
		return _onAccount;
	}

	public void setOnAccount(boolean pValue) {
		_onAccount = pValue;
	}

	private boolean parseOnAccount() {

		if (_user == null) {
			return false;
		}

		if (_userRights.indexOf(Constants.UserRole.ON_ACCOUNT) >= 0) {
			return true;
		}
		return false;
	}

	public boolean getOtherPaymentFlag() {
		return _otherPmt;
	}

	public void setOtherPaymentFlag(boolean pValue) {
		_otherPmt = pValue;
	}

	private boolean parseOtherPaymentFlag() {

		if (_user == null) {
			return false;
		}

		if (_userRights.indexOf(Constants.UserRole.OTHER_PAYMENT) >= 0) {
			return true;
		}
		return false;
	}

	public boolean isPresentationOnly() {
		return _salesPresentationOnly;
	}

	public void setPresentationOnly(boolean pValue) {
		_salesPresentationOnly = pValue;
	}

	private boolean parsePresentationOnly() {
		if (_user == null) {
			return false;
		}
		boolean pres = (_userRights
				.indexOf(Constants.UserRole.SALES_PRESENTATION_ONLY) >= 0) ? true
				: false;
		return pres;
	}

	public boolean canApprovePurchases() {
		return _canApproveOrders;
	}

	public void setCanApprovePurchases(boolean pValue) {
		_canApproveOrders = pValue;
	}

	public boolean parseCanApprovePurchases() {
		if (null == _user)
			return false;
		String clwWorkflowRole = _user.getWorkflowRoleCd();
		if (clwWorkflowRole
				.indexOf(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER) >= 0) {
			return true;
		}
		return false;
	}

	public boolean isBrowseOnly() {
		return _browseOnly;
	}

	public void setBrowseOnly(boolean pValue) {
		_browseOnly = pValue;
	}

	private boolean parseBrowseOnly() {

		if (_user == null) {
			return true;
		}
		if (_userRights == null) {
			return true;
		}
		if (_userRights.indexOf(Constants.UserRole.BROWSE_ONLY) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the showWholeCatalog attribute of the UserRightsTool object
	 * 
	 * @return The showWholeCatalog value
	 */
	public boolean getShowWholeCatalog() {
		if (true == isUserOnContract()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean getPoNumRequired() {
		return _poNumRequired;
	}

	public void setPoNumRequired(boolean pValue) {
		_poNumRequired = pValue;
	}

	private boolean parsePoNumRequired() {
		if (_user == null) {
			return false;
		}
		boolean poNumRequired = (_userRights
				.indexOf(Constants.UserRole.PO_NUM_REQUIRED) >= 0) ? true
				: false;
		return poNumRequired;
	}

	public boolean isNoReporting() {
		return _noReporting;
	}

	public void setNoReporting(boolean pValue) {
		_noReporting = pValue;
	}

	private boolean parseNoReporting() {
		if (_user == null) {
			return false;
		}
		return isNoReporting(_userRights);
	}

	public boolean isUserOnContract() {
		return _contractItemsOnly;
	}

	public void setUserOnContract(boolean pValue) {
		_contractItemsOnly = pValue;
	}

	private boolean parseUserOnContract() {
		if (_user == null) {
			return false;
		}
		return isUserOnContract(_userRights);
	}

	public boolean getShowPrice() {
		return _showPrice;
	}

	public void setShowPrice(boolean pValue) {
		_showPrice = pValue;
	}

	private boolean parseShowPrice() {
		if (_user == null) {
			return false;
		}
		if (_userRights.indexOf(Constants.UserRole.SHOW_PRICE) < 0) {
			return false;
		}
		return true;
	}

	public boolean getCreditCardFlag() {
		return _creditCard;
	}

	public void setCreditCardFlag(boolean pValue) {
		_creditCard = pValue;
	}

	private boolean parseCreditCardFlag() {
		if (_user == null) {
			return false;
		}
		boolean creditCardFlag = (_userRights
				.indexOf(Constants.UserRole.CREDIT_CARD) >= 0) ? true : false;
		return creditCardFlag;
	}

	public boolean getsEmailForApproval() {
		return _orderNotificationNeedsApprovalEmail;
	}

	public void setEmailForApproval(boolean pValue) {
		_orderNotificationNeedsApprovalEmail = pValue;
	}

	private boolean parseGetsEmailForApproval() {
		if (_user == null) {
			return false;
		}
		boolean flag = (_userRights
				.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_NEEDS_APPROVAL) >= 0) ? true
				: false;
		return flag;
	}

	public boolean getsEmailOrderApproved() {
		return _orderNotificationApprovedEmail;
	}

	public void setEmailOrderApproved(boolean pValue) {
		_orderNotificationApprovedEmail = pValue;
	}

	private boolean parseGetsEmailOrderApproved() {
		if (_user == null) {
			return false;
		}
		boolean flag = (_userRights
				.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_WAS_APPROVED) >= 0) ? true
				: false;
		return flag;
	}

	public boolean getsEmailOrderRejection() {
		return _orderNotificationRejectedEmail;
	}

	public void setEmailOrderRejection(boolean pValue) {
		_orderNotificationRejectedEmail = pValue;
	}

	private boolean parseGetsEmailOrderRejection() {
		if (_user == null) {
			return false;
		}
		boolean flag = (_userRights
				.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_WAS_REJECTED) >= 0) ? true
				: false;
		return flag;
	}

	public boolean getsEmailOrderModifications() {
		return _orderNotificationModifiedEmail;
	}

	public void setEmailOrderModifications(boolean pValue) {
		_orderNotificationModifiedEmail = pValue;
	}

	private boolean parseGetsEmailOrderModifications() {
		if (_user == null) {
			return false;
		}
		boolean flag = (_userRights
				.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_WAS_MODIFIED) >= 0) ? true
				: false;
		return flag;
	}

	public boolean canEditShipTo() {
		return _canEditShipTo;
	}

	public void setCanEditShipTo(boolean pValue) {
		_canEditShipTo = pValue;
	}

	private boolean parseCanEditShipTo() {
		boolean flag = (_userRights.indexOf(Constants.UserRole.CAN_EDIT_SHIPTO) >= 0) ? true
				: false;
		return flag;
	}

	public boolean canEditBillTo() {
		return _canEditBillTo;
	}

	public void setCanEditBillTo(boolean pValue) {
		_canEditBillTo = pValue;
	}

	private boolean parseCanEditBillTo() {
		boolean flag = (_userRights.indexOf(Constants.UserRole.CAN_EDIT_BILLTO) >= 0) ? true
				: false;
		return flag;
	}

	public boolean isCustServiceApprover() {
		return _custServiceApprover;
	}

	public void setCustServiceApprover(boolean pValue) {
		_custServiceApprover = pValue;
	}

	private boolean parseCustServiceApprover() {
		boolean flag = (_userRights
				.indexOf(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.APPROVER) >= 0) ? true
				: false;
		return flag;
	}

	public boolean isCustServicePublisher() {
		return _custServicePublisher;
	}

	public void setCustServicePublisher(boolean pValue) {
		_custServicePublisher = pValue;
	}

	private boolean parseCustServicePublisher() {
		boolean flag = (_userRights
				.indexOf(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.PUBLISHER) >= 0) ? true
				: false;
		return flag;
	}

	public boolean isCustServiceViewer() {
		return _custServiceViewer;
	}

	public void setCustServiceViewer(boolean pValue) {
		_custServiceViewer = pValue;
	}

	private boolean parseCustServiceViewer() {
		boolean flag = (_userRights
				.indexOf(RefCodeNames.CUSTOMER_SERVICE_ROLE_CD.VIEWER) >= 0) ? true
				: false;
		return flag;
	}
	
	//start aj
	public boolean isReportingManager() {
		return _reportingManager;
	}

	public void setReportingManager(boolean pValue) {
		_reportingManager = pValue;
	}

	private boolean parseReportingManager() {
		boolean flag = (_userRights.indexOf(Constants.UserRole.REPORTING_MANAGER) >= 0) ? true
				: false;
		return flag;
	}
	public boolean isReportingAssignAllAccts() {
		return _reportingAssignAllAccts;
	}

	public void setReportingAssignAllAccts(boolean pValue) {
		_reportingAssignAllAccts = pValue;
	}

	private boolean parseReportingAssignAllAccts() {
		boolean flag = (_userRights.indexOf(Constants.UserRole.REPORPTING_ASSIGN_ALL_ACCTS) >= 0) ? true
				: false;
		return flag;
	}
	//end aj	

	/**
	 * ---------------- Static utility methods ------------------------
	 */
	public static boolean isNoReporting(String pUserRights) {
		boolean pres = (pUserRights.indexOf(Constants.UserRole.NO_REPORTING) >= 0) ? true
				: false;
		return pres;
	}

	public static boolean isUserOnContract(String pUserRights) {
		boolean contractItemOnly = (pUserRights
				.indexOf(Constants.UserRole.CONTRACT_ITEMS_ONLY) >= 0) ? true
				: false;
		return contractItemOnly;
	}


    public boolean getOrderNotificationShipped() {
        return _orderNotificationShipped;
    }

    public void setOrderNotificationShipped(boolean pVal) {
        this._orderNotificationShipped = pVal;
    }

    public boolean getEmailOrderDetailNotification() {
        return _emailOrderDetailNotification;
    }

    public void setEmailOrderDetailNotification(boolean pVal) {
        this._emailOrderDetailNotification = pVal;
    }

    public boolean getWorkOrderAcceptedByProviderNotification() {
        return _workOrderAcceptedByProviderNotification;
    }

    public void setWorkOrderAcceptedByProviderNotification(boolean workOrderAcceptedByProviderNotification) {
        this._workOrderAcceptedByProviderNotification = workOrderAcceptedByProviderNotification;
    }

    public boolean getWorkOrderCompletedNotification() {
        return _workOrderCompletedNotification;
    }

    public void setWorkOrderCompletedNotification(boolean workOrderCompletedNotification) {
        this._workOrderCompletedNotification = workOrderCompletedNotification;
    }

    public boolean getWorkOrderRejectedByProviderNotification() {
        return _workOrderRejectedByProviderNotification;
    }

    public void setWorkOrderRejectedByProviderNotification(boolean workOrderRejectedByProviderNotification) {
        this._workOrderRejectedByProviderNotification = workOrderRejectedByProviderNotification;
    }

    public boolean getCutoffTimeReminderEmail() {
        return _cutoffTimeReminderEmail;
    }

    public void setCutoffTimeReminderEmail(boolean cutoffTimeReminderEmail) {
        this._cutoffTimeReminderEmail = cutoffTimeReminderEmail;
    }
    
    public boolean getPhysicalInvNonComplSiteListingEmail() {
        return _physicalInvNonComplSiteListingEmail;
    }

    public void setPhysicalInvNonComplSiteListingEmail(boolean physicalInvNonComplSiteListingEmail) {
        this._physicalInvNonComplSiteListingEmail = physicalInvNonComplSiteListingEmail;
    }
    
    public boolean getPhysicalInvCountsPastDueEmail() {
        return _physicalInvCountsPastDueEmail;
    }

    public void setPhysicalInvCountsPastDueEmail(boolean physicalInvCountsPastDueEmail) {
        this._physicalInvCountsPastDueEmail = physicalInvCountsPastDueEmail;
    }

    private boolean parseGetsEmailOrderShipped() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_SHIPPED) >= 0);
        return flag;
    }

    private boolean parseGetsEmailOrderDetailApproved() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_ORDER_DETAIL_APPROVED) >= 0);
        return flag;
    }
    
    private boolean parseGetsEmailWorkOrderCompleted() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_WORK_ORDER_COMPLETED) >= 0);
        return flag;
    }
    
    private boolean parseGetsEmailWorkOrderAccepted() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_WORK_ORDER_ACCEPTED) >= 0);
        return flag;
    }
    
    private boolean parseGetsEmailWorkOrderRejected() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_WORK_ORDER_REJECTED) >= 0);
        return flag;
    }
    
    private boolean parseGetsEmailCutoffTimeReminder() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_CUTOFF_TIME_REMINDER) >= 0);
        return flag;
    }
    
    private boolean parseGetsEmailPhysicalInvNonComplSiteListing() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_PHYSICAL_INV_NON_COMPL_SITE_LISTING) >= 0);
        return flag;
    }
    
    private boolean parseGetsEmailPhysicalInvCountsPastDue() {
        if (_user == null) {
            return false;
        }
        boolean flag;
        flag = (_userRights.indexOf(UserInfoData.USER_GETS_EMAIL_PHYSICAL_INV_COUNTS_PAST_DUE) >= 0);
        return flag;
    }
    
}

