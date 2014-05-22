package com.cleanwise.view.forms;
import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.view.utils.Constants;

/**
 *  Description of the Class
 *
 *@author     Dvieira
 *@created    October 15, 2004
 */
public class BaseUserDetailForm extends ActionForm {

	protected boolean
	_showPrice = false,
	_browseOnly = false,
	_contractItemsOnly = false,
	_onAccount = false,
	_otherPmt = false,
	_creditCard = false,
	_poNumRequired = false,
	_canApproveOrders = false,
	_orderNotificationNeedsApprovalEmail = false,
	_orderNotificationApprovedEmail = false,
	_orderNotificationRejectedEmail = false,
	_orderNotificationModifiedEmail = false,
	_canEditShipTo = false,
	_canEditBillTo = false,
	_noReporting = false,
	_salesPresentationOnly = false,
	_custServiceApprover = false,
	_custServicePublisher = false,
	_custServiceViewer = false,
    _orderDetailNotificationEmail=false,
    _orderNotificationShippedEmail=false,
	_reportingManager = false,			//aj 
	_reportingAssignAllAccts = false,			//aj
	_workOrderCompletedNotification = false,
	_workOrderAcceptedByProviderNotification = false,
	_workOrderRejectedByProviderNotification = false,
	_cutoffTimeReminderEmail = false,
	_physicalInvNonComplSiteListingEmail = false,
	_physicalInvCountsPastDueEmail = false;

	private String _cutoffTimeReminderEmailCount = "1";
        
    /**
     *  Gets the canEditShipTo attribute of the BaseUserDetailForm object
     *
     *@return    The canEditShipTo value
     */
    public boolean isCanEditShipTo() {
        return (this._canEditShipTo);
    }


    /**
     *  Sets the canEditShipTo attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new canEditShipTo value
     */
    public void setCanEditShipTo(boolean pVal) {
        this._canEditShipTo = pVal;
    }


    /**
     *  Gets the canEditBillTo attribute of the BaseUserDetailForm object
     *
     *@return    The canEditBillTo value
     */
    public boolean isCanEditBillTo() {
        return (this._canEditBillTo);
    }


    /**
     *  Sets the canEditBillTo attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new canEditBillTo value
     */
    public void setCanEditBillTo(boolean pVal) {
        this._canEditBillTo = pVal;
    }


    /**
     *  Description of the Method
     */
    public void resetPermissions() {
        _showPrice = false;
        _browseOnly = false;
        _contractItemsOnly = false;
        _onAccount = false;
        _otherPmt = false;
        _creditCard = false;
        _poNumRequired = false;
        _canApproveOrders = false;
        _noReporting = false;
        _salesPresentationOnly = false;
        _orderNotificationNeedsApprovalEmail = false;
        _orderNotificationApprovedEmail = false;
        _orderNotificationRejectedEmail = false;
        _orderNotificationModifiedEmail = false;
        _orderDetailNotificationEmail=false;
        _orderNotificationShippedEmail=false;
        _canEditShipTo = false;
        _canEditBillTo = false;
    	_reportingManager = false;			//aj 
	_reportingAssignAllAccts = false;		//aj 
        _workOrderCompletedNotification = false;
        _workOrderAcceptedByProviderNotification = false;
        _workOrderRejectedByProviderNotification = false;
        _cutoffTimeReminderEmail = false;
    	_physicalInvNonComplSiteListingEmail = false;
    	_physicalInvCountsPastDueEmail = false;

    	_cutoffTimeReminderEmailCount = "1";
    }

    public int getPermisionsAsInt () {
      int key = 
        1+//just not to be 0      
        (_showPrice?1:0)*2+
        (_browseOnly?1:0)*4+
        (_contractItemsOnly?1:0)*8+
        (_onAccount?1:0)*16+
        (_otherPmt?1:0)*32+
        (_creditCard?1:0)*64+
        (_poNumRequired?1:0)*128+
        (_canApproveOrders?1:0)*256+
        (_noReporting?1:0)*512+
        (_salesPresentationOnly?1:0)*1024+
        (_orderNotificationNeedsApprovalEmail?1:0)*2048+
        (_orderNotificationApprovedEmail?1:0)*4096+
        (_orderNotificationRejectedEmail?1:0)*8192+
        (_orderNotificationModifiedEmail?1:0)*16384+
        (_canEditShipTo?1:0)*32768+
        (_canEditBillTo?1:0)*65536+
        (_orderDetailNotificationEmail?1:0)*131072+
        (_orderNotificationShippedEmail?1:0)*262144+
        (_reportingManager?1:0)*524288+			//aj
        (_reportingAssignAllAccts?1:0)*1048576+	//aj
        (_workOrderCompletedNotification?1:0)*2097152+
        (_workOrderAcceptedByProviderNotification?1:0)*4194304+
        (_workOrderRejectedByProviderNotification?1:0)*8388608+
        (_cutoffTimeReminderEmail?1:0)*16777216+
        (_physicalInvNonComplSiteListingEmail?1:0)*33554432+
        (_physicalInvCountsPastDueEmail?1:0)*67108864        
        ;
      return key;
    }
    /**
     *  Gets the orderNotificationNeedsApprovalEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@return    The orderNotificationNeedsApprovalEmail value
     */
    public boolean isOrderNotificationNeedsApprovalEmail() {
        return (this._orderNotificationNeedsApprovalEmail);
    }


    /**
     *  Sets the orderNotificationNeedsApprovalEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@param  pVal  The new orderNotificationNeedsApprovalEmail value
     */
    public void setOrderNotificationNeedsApprovalEmail(boolean pVal) {
        this._orderNotificationNeedsApprovalEmail = pVal;
    }


    /**
     *  Gets the orderNotificationApprovedEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@return    The orderNotificationApprovedEmail value
     */
    public boolean isOrderNotificationApprovedEmail() {
        return (this._orderNotificationApprovedEmail);
    }

    /**
     * Creates a permission token that is defined by the forms current settings.
     */
    public String makePermissionsToken() {
    	UserData u = UserData.createValue();
    	UserRightsTool tool = new UserRightsTool(u);
    	tool.setShowPrice(_showPrice);
    	tool.setBrowseOnly(_browseOnly);
    	tool.setUserOnContract(_contractItemsOnly);
    	tool.setOnAccount(_onAccount);
    	tool.setOtherPaymentFlag(_otherPmt);
    	tool.setCreditCardFlag(_creditCard);
    	tool.setPoNumRequired(_poNumRequired);
    	tool.setCanApprovePurchases(_canApproveOrders);
    	tool.setEmailForApproval(_orderNotificationNeedsApprovalEmail);
    	tool.setEmailOrderApproved(_orderNotificationApprovedEmail);
    	tool.setEmailOrderRejection(_orderNotificationRejectedEmail);
    	tool.setEmailOrderModifications(_orderNotificationModifiedEmail);
        tool.setEmailOrderDetailNotification(_orderDetailNotificationEmail);
        tool.setOrderNotificationShipped(_orderNotificationShippedEmail);
        tool.setCanEditShipTo(_canEditShipTo);
    	tool.setCanEditBillTo(_canEditBillTo);
    	tool.setNoReporting(_noReporting);
    	tool.setPresentationOnly(_salesPresentationOnly);
    	tool.setCustServiceApprover(_custServiceApprover);
    	tool.setCustServicePublisher(_custServicePublisher);
    	tool.setCustServiceViewer(_custServiceViewer);
    	tool.setReportingManager(_reportingManager);				//aj
    	tool.setReportingAssignAllAccts(_reportingAssignAllAccts);		//aj
        tool.setWorkOrderCompletedNotification(_workOrderCompletedNotification);
        tool.setWorkOrderAcceptedByProviderNotification(_workOrderAcceptedByProviderNotification);
        tool.setWorkOrderRejectedByProviderNotification(_workOrderRejectedByProviderNotification);
        tool.setCutoffTimeReminderEmail(_cutoffTimeReminderEmail);
        tool.setPhysicalInvNonComplSiteListingEmail(_physicalInvNonComplSiteListingEmail);
        tool.setPhysicalInvCountsPastDueEmail(_physicalInvCountsPastDueEmail);
    	return tool.makePermissionsToken();
    }

    /**
     *  Sets the orderNotificationApprovedEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@param  pVal  The new orderNotificationApprovedEmail value
     */
    public void setOrderNotificationApprovedEmail(boolean pVal) {
        this._orderNotificationApprovedEmail = pVal;
    }


    /**
     *  Gets the orderNotificationRejectedEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@return    The orderNotificationRejectedEmail value
     */
    public boolean isOrderNotificationRejectedEmail() {
        return (this._orderNotificationRejectedEmail);
    }


    /**
     *  Sets the orderNotificationRejectedEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@param  pVal  The new orderNotificationRejectedEmail value
     */
    public void setOrderNotificationRejectedEmail(boolean pVal) {
        this._orderNotificationRejectedEmail = pVal;
    }


    /**
     *  Gets the orderNotificationModifiedEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@return    The orderNotificationModifiedEmail value
     */
    public boolean isOrderNotificationModifiedEmail() {
        return (this._orderNotificationModifiedEmail);
    }


    /**
     *  Sets the orderNotificationModifiedEmail attribute of the
     *  BaseUserDetailForm object
     *
     *@param  pVal  The new orderNotificationModifiedEmail value
     */
    public void setOrderNotificationModifiedEmail(boolean pVal) {
        this._orderNotificationModifiedEmail = pVal;
    }


    /**
     *  Gets the showPrice attribute of the BaseUserDetailForm object
     *
     *@return    The showPrice value
     */
    public boolean isShowPrice() {
        return (this._showPrice);
    }


    /**
     *  Sets the showPrice attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new showPrice value
     */
    public void setShowPrice(boolean pVal) {
        this._showPrice = pVal;
    }


    /**
     *  Gets the browseOnly attribute of the BaseUserDetailForm object
     *
     *@return    The browseOnly value
     */
    public boolean isBrowseOnly() {
        return (this._browseOnly);
    }


    /**
     *  Sets the browseOnly attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new browseOnly value
     */
    public void setBrowseOnly(boolean pVal) {
        this._browseOnly = pVal;
    }


    /**
     *  Gets the contractItemsOnly attribute of the BaseUserDetailForm object
     *
     *@return    The contractItemsOnly value
     */
    public boolean isContractItemsOnly() {
        return (this._contractItemsOnly);
    }


    /**
     *  Sets the contractItemsOnly attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new contractItemsOnly value
     */
    public void setContractItemsOnly(boolean pVal) {
        this._contractItemsOnly = pVal;
    }


    /**
     *  Gets the onAccount attribute of the BaseUserDetailForm object
     *
     *@return    The onAccount value
     */
    public boolean isOnAccount() {
        return (this._onAccount);
    }

    public boolean isOtherPayment() {
        return (this._otherPmt);
    }

    /**
     *  Sets the onAccount attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new onAccount value
     */
    public void setOnAccount(boolean pVal) {
        this._onAccount = pVal;
    }

    public void setOtherPayment(boolean pVal) {
        this._otherPmt = pVal;
    }

    /**
     *  Gets the creditCard attribute of the BaseUserDetailForm object
     *
     *@return    The creditCard value
     */
    public boolean isCreditCard() {
        return (this._creditCard);
    }


    /**
     *  Sets the creditCard attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new creditCard value
     */
    public void setCreditCard(boolean pVal) {
        this._creditCard = pVal;
    }


    /**
     *  Gets the poNumRequired attribute of the BaseUserDetailForm object
     *
     *@return    The poNumRequired value
     */
    public boolean isPoNumRequired() {
        return (this._poNumRequired);
    }


    /**
     *  Sets the poNumRequired attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new poNumRequired value
     */
    public void setPoNumRequired(boolean pVal) {
        this._poNumRequired = pVal;
    }


    /**
     *  Gets the canApproveOrders attribute of the BaseUserDetailForm object
     *
     *@return    The canApproveOrders value
     */
    public boolean isCanApproveOrders() {
        return (this._canApproveOrders);
    }


    /**
     *  Sets the canApproveOrders attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new canApproveOrders value
     */
    public void setCanApproveOrders(boolean pVal) {
        this._canApproveOrders = pVal;
    }


    /**
     *  Gets the noReporting attribute of the BaseUserDetailForm object
     *
     *@return    The noReporting value
     */
    public boolean isNoReporting() {
        return (this._noReporting);
    }


    /**
     *  Sets the noReporting attribute of the BaseUserDetailForm object
     *
     *@param  pVal  The new noReporting value
     */
    public void setNoReporting(boolean pVal) {
        this._noReporting = pVal;
    }


    /**
     *  Gets the salesPresentationOnly attribute of the BaseUserDetailForm
     *  object
     *
     *@return    The salesPresentationOnly value
     */
    public boolean isSalesPresentationOnly() {
        return (this._salesPresentationOnly);
    }


    /**
     *  Sets the salesPresentationOnly attribute of the BaseUserDetailForm
     *  object
     *
     *@param  pVal  The new salesPresentationOnly value
     */
    public void setSalesPresentationOnly(boolean pVal) {
        this._salesPresentationOnly = pVal;
    }

    public boolean isOrderNotificationShippedEmail() {
        return _orderNotificationShippedEmail;
    }

    public void setOrderNotificationShippedEmail(boolean pVal) {
        this._orderNotificationShippedEmail =  pVal;
    }

    public boolean isOrderDetailNotificationEmail() {
        return _orderDetailNotificationEmail;
    }

    public void setOrderDetailNotificationEmail(boolean  pVal) {
        this._orderDetailNotificationEmail =  pVal;
    }
    
    //start aj
    public boolean isReportingManager() {
        return _reportingManager;
    }

    public void setReportingManager(boolean  pVal) {
        this._reportingManager =  pVal;
    }
    public boolean isReportingAssignAllAccts() {
        return _reportingAssignAllAccts;
    }

    public void setReportingAssignAllAccts(boolean  pVal) {
        this._reportingAssignAllAccts =  pVal;
    }

    public boolean isWorkOrderAcceptedByProviderNotification() {
        return _workOrderAcceptedByProviderNotification;
    }

    public void setWorkOrderAcceptedByProviderNotification(boolean workOrderAcceptedByProviderNotification) {
        this._workOrderAcceptedByProviderNotification = workOrderAcceptedByProviderNotification;
    }

    public boolean isWorkOrderCompletedNotification() {
        return _workOrderCompletedNotification;
    }

    public void setWorkOrderCompletedNotification(boolean workOrderCompletedNotification) {
        this._workOrderCompletedNotification = workOrderCompletedNotification;
    }

    public boolean isWorkOrderRejectedByProviderNotification() {
        return _workOrderRejectedByProviderNotification;
    }

    public void setWorkOrderRejectedByProviderNotification(boolean workOrderRejectedByProviderNotification) {
        this._workOrderRejectedByProviderNotification = workOrderRejectedByProviderNotification;
    }
    
    public boolean isCutoffTimeReminderEmail() {
        return _cutoffTimeReminderEmail;
    }

    public void setCutoffTimeReminderEmail(boolean cutoffTimeReminderEmail) {
        this._cutoffTimeReminderEmail = cutoffTimeReminderEmail;
    }
    
    public boolean isPhysicalInvNonComplSiteListingEmail() {
        return _physicalInvNonComplSiteListingEmail;
    }

    public void setPhysicalInvNonComplSiteListingEmail(boolean physicalInvNonComplSiteListingEmail) {
        this._physicalInvNonComplSiteListingEmail = physicalInvNonComplSiteListingEmail;
    }
    
    public boolean isPhysicalInvCountsPastDueEmail() {
        return _physicalInvCountsPastDueEmail;
    }
    
    public void setPhysicalInvCountsPastDueEmail(boolean physicalInvCountsPastDueEmail) {
        this._physicalInvCountsPastDueEmail = physicalInvCountsPastDueEmail;
    }

	public void setCutoffTimeReminderEmailCount(
			String cutoffTimeReminderEmailCount) {
		this._cutoffTimeReminderEmailCount = cutoffTimeReminderEmailCount;
	}

	public String getCutoffTimeReminderEmailCount() {
		return _cutoffTimeReminderEmailCount;
	}
    
}

