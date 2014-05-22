package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.util.Utility;

public class Admin2BaseUserDetailForm extends ActionForm {

    protected String _showPrice;
    protected String _browseOnly;
    protected String _contractItemsOnly;
    protected String _onAccount;
    protected String _otherPmt;
    protected String _creditCard;
    protected String _poNumRequired;
    protected String _canApproveOrders;
    protected String _orderNotificationNeedsApprovalEmail;
    protected String _orderNotificationApprovedEmail;
    protected String _orderNotificationRejectedEmail;
    protected String _orderNotificationModifiedEmail;
    protected String _canEditShipTo;
    protected String _canEditBillTo;
    protected String _noReporting;
    protected String _salesPresentationOnly;
    protected String _custServiceApprover;
    protected String _custServicePublisher;
    protected String _custServiceViewer;
    protected String _orderDetailNotificationEmail;
    protected String _orderNotificationShippedEmail;
    protected String _reportingManager;
    protected String _reportingAssignAllAccts;
    protected String _workOrderCompletedNotification;
    protected String _workOrderAcceptedByProviderNotification;
    protected String _workOrderRejectedByProviderNotification;

    /**
     * Gets the canEditShipTo attribute of the Admin2BaseUserDetailForm object
     *
     * @return The canEditShipTo value
     */
    public String getCanEditShipTo() {
        return (this._canEditShipTo);
    }

    /**
     * Sets the canEditShipTo attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new canEditShipTo value
     */
    public void setCanEditShipTo(String pVal) {
        this._canEditShipTo = pVal;
    }


    /**
     * Gets the canEditBillTo attribute of the Admin2BaseUserDetailForm object
     *
     * @return The canEditBillTo value
     */
    public String getCanEditBillTo() {
        return (this._canEditBillTo);
    }


    /**
     * Sets the canEditBillTo attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new canEditBillTo value
     */
    public void setCanEditBillTo(String pVal) {
        this._canEditBillTo = pVal;
    }


    /**
     * Description of the Method
     */
    public void resetPermissions() {
        _showPrice = String.valueOf(false);
        _browseOnly = String.valueOf(false);
        _contractItemsOnly = String.valueOf(false);
        _onAccount = String.valueOf(false);
        _otherPmt = String.valueOf(false);
        _creditCard = String.valueOf(false);
        _poNumRequired = String.valueOf(false);
        _canApproveOrders = String.valueOf(false);
        _noReporting = String.valueOf(false);
        _salesPresentationOnly = String.valueOf(false);
        _orderNotificationNeedsApprovalEmail = String.valueOf(false);
        _orderNotificationApprovedEmail = String.valueOf(false);
        _orderNotificationRejectedEmail = String.valueOf(false);
        _orderNotificationModifiedEmail = String.valueOf(false);
        _orderDetailNotificationEmail = String.valueOf(false);
        _orderNotificationShippedEmail = String.valueOf(false);
        _canEditShipTo = String.valueOf(false);
        _canEditBillTo = String.valueOf(false);
        _reportingManager = String.valueOf(false);
        _reportingAssignAllAccts = String.valueOf(false);
        _workOrderCompletedNotification = String.valueOf(false);
        _workOrderAcceptedByProviderNotification = String.valueOf(false);
        _workOrderRejectedByProviderNotification = String.valueOf(false);
    }

    public int getPermisionsAsInt() {
        return 1 +//just not to be 0
                (Utility.isSelected(_showPrice) ? 1 : 0) * 2 +
                (Utility.isSelected(_browseOnly) ? 1 : 0) * 4 +
                (Utility.isSelected(_contractItemsOnly) ? 1 : 0) * 8 +
                (Utility.isSelected(_onAccount) ? 1 : 0) * 16 +
                (Utility.isSelected(_otherPmt) ? 1 : 0) * 32 +
                (Utility.isSelected(_creditCard) ? 1 : 0) * 64 +
                (Utility.isSelected(_poNumRequired) ? 1 : 0) * 128 +
                (Utility.isSelected(_canApproveOrders) ? 1 : 0) * 256 +
                (Utility.isSelected(_noReporting) ? 1 : 0) * 512 +
                (Utility.isSelected(_salesPresentationOnly) ? 1 : 0) * 1024 +
                (Utility.isSelected(_orderNotificationNeedsApprovalEmail) ? 1 : 0) * 2048 +
                (Utility.isSelected(_orderNotificationApprovedEmail) ? 1 : 0) * 4096 +
                (Utility.isSelected(_orderNotificationRejectedEmail) ? 1 : 0) * 8192 +
                (Utility.isSelected(_orderNotificationModifiedEmail) ? 1 : 0) * 16384 +
                (Utility.isSelected(_canEditShipTo) ? 1 : 0) * 32768 +
                (Utility.isSelected(_canEditBillTo) ? 1 : 0) * 65536 +
                (Utility.isSelected(_orderDetailNotificationEmail) ? 1 : 0) * 131072 +
                (Utility.isSelected(_orderNotificationShippedEmail) ? 1 : 0) * 262144 +
                (Utility.isSelected(_reportingManager) ? 1 : 0) * 524288 +            //aj
                (Utility.isSelected(_reportingAssignAllAccts) ? 1 : 0) * 1048576 +    //aj
                (Utility.isSelected(_workOrderCompletedNotification) ? 1 : 0) * 2097152 +
                (Utility.isSelected(_workOrderAcceptedByProviderNotification) ? 1 : 0) * 4194304 +
                (Utility.isSelected(_workOrderRejectedByProviderNotification) ? 1 : 0) * 8388608;
    }

    /**
     * Gets the orderNotificationNeedsApprovalEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @return The orderNotificationNeedsApprovalEmail value
     */
    public String getOrderNotificationNeedsApprovalEmail() {
        return (this._orderNotificationNeedsApprovalEmail);
    }


    /**
     * Sets the orderNotificationNeedsApprovalEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @param pVal The new orderNotificationNeedsApprovalEmail value
     */
    public void setOrderNotificationNeedsApprovalEmail(String pVal) {
        this._orderNotificationNeedsApprovalEmail = pVal;
    }


    /**
     * Gets the orderNotificationApprovedEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @return The orderNotificationApprovedEmail value
     */
    public String getOrderNotificationApprovedEmail() {
        return (this._orderNotificationApprovedEmail);
    }

    /**
     * Creates a permission token that is defined by the forms current settings.
     * @return  permissions token
     */
    public String makePermissionsToken() {

        UserData u = UserData.createValue();
        UserRightsTool tool = new UserRightsTool(u);

        tool.setShowPrice(Utility.isSelected(_showPrice));
        tool.setBrowseOnly(Utility.isSelected(_browseOnly));
        tool.setUserOnContract(Utility.isSelected(_contractItemsOnly));
        tool.setOnAccount(Utility.isSelected(_onAccount));
        tool.setOtherPaymentFlag(Utility.isSelected(_otherPmt));
        tool.setCreditCardFlag(Utility.isSelected(_creditCard));
        tool.setPoNumRequired(Utility.isSelected(_poNumRequired));
        tool.setCanApprovePurchases(Utility.isSelected(_canApproveOrders));
        tool.setEmailForApproval(Utility.isSelected(_orderNotificationNeedsApprovalEmail));
        tool.setEmailOrderApproved(Utility.isSelected(_orderNotificationApprovedEmail));
        tool.setEmailOrderRejection(Utility.isSelected(_orderNotificationRejectedEmail));
        tool.setEmailOrderModifications(Utility.isSelected(_orderNotificationModifiedEmail));
        tool.setEmailOrderDetailNotification(Utility.isSelected(_orderDetailNotificationEmail));
        tool.setOrderNotificationShipped(Utility.isSelected(_orderNotificationShippedEmail));
        tool.setCanEditShipTo(Utility.isSelected(_canEditShipTo));
        tool.setCanEditBillTo(Utility.isSelected(_canEditBillTo));
        tool.setNoReporting(Utility.isSelected(_noReporting));
        tool.setPresentationOnly(Utility.isSelected(_salesPresentationOnly));
        tool.setCustServiceApprover(Utility.isSelected(_custServiceApprover));
        tool.setCustServicePublisher(Utility.isSelected(_custServicePublisher));
        tool.setCustServiceViewer(Utility.isSelected(_custServiceViewer));
        tool.setReportingManager(Utility.isSelected(_reportingManager));
        tool.setReportingAssignAllAccts(Utility.isSelected(_reportingAssignAllAccts));
        tool.setWorkOrderCompletedNotification(Utility.isSelected(_workOrderCompletedNotification));
        tool.setWorkOrderAcceptedByProviderNotification(Utility.isSelected(_workOrderAcceptedByProviderNotification));
        tool.setWorkOrderRejectedByProviderNotification(Utility.isSelected(_workOrderRejectedByProviderNotification));

        return tool.makePermissionsToken();
    }


    /**
     * Sets the orderNotificationApprovedEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @param pVal The new orderNotificationApprovedEmail value
     */
    public void setOrderNotificationApprovedEmail(String pVal) {
        this._orderNotificationApprovedEmail = pVal;
    }


    /**
     * Gets the orderNotificationRejectedEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @return The orderNotificationRejectedEmail value
     */
    public String getOrderNotificationRejectedEmail() {
        return (this._orderNotificationRejectedEmail);
    }


    /**
     * Sets the orderNotificationRejectedEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @param pVal The new orderNotificationRejectedEmail value
     */
    public void setOrderNotificationRejectedEmail(String pVal) {
        this._orderNotificationRejectedEmail = pVal;
    }


    /**
     * Gets the orderNotificationModifiedEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @return The orderNotificationModifiedEmail value
     */
    public String getOrderNotificationModifiedEmail() {
        return (this._orderNotificationModifiedEmail);
    }


    /**
     * Sets the orderNotificationModifiedEmail attribute of the
     * Admin2BaseUserDetailForm object
     *
     * @param pVal The new orderNotificationModifiedEmail value
     */
    public void setOrderNotificationModifiedEmail(String pVal) {
        this._orderNotificationModifiedEmail = pVal;
    }


    /**
     * Gets the showPrice attribute of the Admin2BaseUserDetailForm object
     *
     * @return The showPrice value
     */
    public String getShowPrice() {
        return (this._showPrice);
    }


    /**
     * Sets the showPrice attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new showPrice value
     */
    public void setShowPrice(String pVal) {
        this._showPrice = pVal;
    }


    /**
     * Gets the browseOnly attribute of the Admin2BaseUserDetailForm object
     *
     * @return The browseOnly value
     */
    public String getBrowseOnly() {
        return (this._browseOnly);
    }


    /**
     * Sets the browseOnly attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new browseOnly value
     */
    public void setBrowseOnly(String pVal) {
        this._browseOnly = pVal;
    }


    /**
     * Gets the contractItemsOnly attribute of the Admin2BaseUserDetailForm object
     *
     * @return The contractItemsOnly value
     */
    public String getContractItemsOnly() {
        return (this._contractItemsOnly);
    }


    /**
     * Sets the contractItemsOnly attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new contractItemsOnly value
     */
    public void setContractItemsOnly(String pVal) {
        this._contractItemsOnly = pVal;
    }


    /**
     * Gets the onAccount attribute of the Admin2BaseUserDetailForm object
     *
     * @return The onAccount value
     */
    public String getOnAccount() {
        return (this._onAccount);
    }

    public String getOtherPayment() {
        return (this._otherPmt);
    }

    /**
     * Sets the onAccount attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new onAccount value
     */
    public void setOnAccount(String pVal) {
        this._onAccount = pVal;
    }

    public void setOtherPayment(String pVal) {
        this._otherPmt = pVal;
    }

    /**
     * Gets the creditCard attribute of the Admin2BaseUserDetailForm object
     *
     * @return The creditCard value
     */
    public String getCreditCard() {
        return (this._creditCard);
    }


    /**
     * Sets the creditCard attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new creditCard value
     */
    public void setCreditCard(String pVal) {
        this._creditCard = pVal;
    }


    /**
     * Gets the poNumRequired attribute of the Admin2BaseUserDetailForm object
     *
     * @return The poNumRequired value
     */
    public String getPoNumRequired() {
        return (this._poNumRequired);
    }


    /**
     * Sets the poNumRequired attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new poNumRequired value
     */
    public void setPoNumRequired(String pVal) {
        this._poNumRequired = pVal;
    }


    /**
     * Gets the canApproveOrders attribute of the Admin2BaseUserDetailForm object
     *
     * @return The canApproveOrders value
     */
    public String getCanApproveOrders() {
        return (this._canApproveOrders);
    }


    /**
     * Sets the canApproveOrders attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new canApproveOrders value
     */
    public void setCanApproveOrders(String pVal) {
        this._canApproveOrders = pVal;
    }


    /**
     * Gets the noReporting attribute of the Admin2BaseUserDetailForm object
     *
     * @return The noReporting value
     */
    public String getNoReporting() {
        return (this._noReporting);
    }


    /**
     * Sets the noReporting attribute of the Admin2BaseUserDetailForm object
     *
     * @param pVal The new noReporting value
     */
    public void setNoReporting(String pVal) {
        this._noReporting = pVal;
    }


    /**
     * Gets the salesPresentationOnly attribute of the Admin2BaseUserDetailForm
     * object
     *
     * @return The salesPresentationOnly value
     */
    public String getSalesPresentationOnly() {
        return (this._salesPresentationOnly);
    }


    /**
     * Sets the salesPresentationOnly attribute of the Admin2BaseUserDetailForm
     * object
     *
     * @param pVal The new salesPresentationOnly value
     */
    public void setSalesPresentationOnly(String pVal) {
        this._salesPresentationOnly = pVal;
    }

    public String getOrderNotificationShippedEmail() {
        return _orderNotificationShippedEmail;
    }

    public void setOrderNotificationShippedEmail(String pVal) {
        this._orderNotificationShippedEmail = pVal;
    }

    public String getOrderDetailNotificationEmail() {
        return _orderDetailNotificationEmail;
    }

    public void setOrderDetailNotificationEmail(String pVal) {
        this._orderDetailNotificationEmail = pVal;
    }

    //start aj
    public String getReportingManager() {
        return _reportingManager;
    }

    public void setReportingManager(String pVal) {
        this._reportingManager = pVal;
    }

    public String getReportingAssignAllAccts() {
        return _reportingAssignAllAccts;
    }

    public void setReportingAssignAllAccts(String pVal) {
        this._reportingAssignAllAccts = pVal;
    }

    public String getWorkOrderAcceptedByProviderNotification() {
        return _workOrderAcceptedByProviderNotification;
    }

    public void setWorkOrderAcceptedByProviderNotification(String workOrderAcceptedByProviderNotification) {
        this._workOrderAcceptedByProviderNotification = workOrderAcceptedByProviderNotification;
    }

    public String getWorkOrderCompletedNotification() {
        return _workOrderCompletedNotification;
    }

    public void setWorkOrderCompletedNotification(String workOrderCompletedNotification) {
        this._workOrderCompletedNotification = workOrderCompletedNotification;
    }

    public String getWorkOrderRejectedByProviderNotification() {
        return _workOrderRejectedByProviderNotification;
    }

    public void setWorkOrderRejectedByProviderNotification(String workOrderRejectedByProviderNotification) {
        this._workOrderRejectedByProviderNotification = workOrderRejectedByProviderNotification;
    }
   
}
