package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.BusEntityData;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;

import javax.servlet.http.HttpServletRequest;


public class Admin2UserRightsForm extends Admin2BaseUserDetailForm {

    private UserData _user;
    private String _rights;
    private BusEntityData _accountData;

    public Admin2UserRightsForm() {
        _user = null;
        _accountData = null;
        _rights = "";
    }

    public Admin2UserRightsForm(UserData pUserData,
                                BusEntityData pAccountData,
                                String pRights) {
        _user = pUserData;
        _accountData = pAccountData;
        _rights = pRights;

        com.cleanwise.service.api.util.UserRightsTool userRights = new
                com.cleanwise.service.api.util.UserRightsTool(pUserData, pRights);

        // set al lthe flags based on user rights.

        _showPrice = String.valueOf(userRights.getShowPrice());
        _browseOnly = String.valueOf(userRights.isBrowseOnly());
        _contractItemsOnly = String.valueOf(userRights.isUserOnContract());
        _onAccount = String.valueOf(userRights.getOnAccount());
        _otherPmt = String.valueOf(userRights.getOtherPaymentFlag());
        _creditCard = String.valueOf(userRights.getCreditCardFlag());
        _poNumRequired = String.valueOf(userRights.getPoNumRequired());
        _canApproveOrders = String.valueOf(userRights.canApprovePurchases());
        _orderNotificationNeedsApprovalEmail = String.valueOf(userRights.getsEmailForApproval());
        _orderNotificationApprovedEmail = String.valueOf(userRights.getsEmailOrderApproved());
        _orderNotificationRejectedEmail = String.valueOf(userRights.getsEmailOrderRejection());
        _orderNotificationModifiedEmail = String.valueOf(userRights.getsEmailOrderModifications());
        _noReporting = String.valueOf(userRights.isNoReporting());
        _salesPresentationOnly = String.valueOf(userRights.isPresentationOnly());

        _canEditShipTo = String.valueOf(userRights.canEditShipTo());
        _canEditBillTo = String.valueOf(userRights.canEditBillTo());

        _orderDetailNotificationEmail = String.valueOf(userRights.getEmailOrderDetailNotification());
        _orderNotificationShippedEmail = String.valueOf(userRights.getOrderNotificationShipped());
        _reportingManager = String.valueOf(userRights.isReportingManager());
        _reportingAssignAllAccts = String.valueOf(userRights.isReportingAssignAllAccts());
        _workOrderCompletedNotification = String.valueOf(userRights.getWorkOrderCompletedNotification());
        _workOrderAcceptedByProviderNotification = String.valueOf(userRights.getWorkOrderAcceptedByProviderNotification());
        _workOrderRejectedByProviderNotification = String.valueOf(userRights.getWorkOrderRejectedByProviderNotification());

    }

    public UserData getUserData() {
        return (this._user);
    }

    public String getRightsFromOptions() {

        return makePermissionsToken();

    }

    public void setUserData(UserData v) {
        this._user = v;
    }


    public BusEntityData getAccountData() {
        return this._accountData;
    }

    public void setAccountData(BusEntityData v) {
        this._accountData = v;
    }

    /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetPermissions();
    }

    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return null;
    }

    public String toString() {
        return "Admin2UserRightsFor {\n _user = " + _user
                + "\n _accountData = " + _accountData
                + "\n _rights = " + _rights + " }";
    }

}
