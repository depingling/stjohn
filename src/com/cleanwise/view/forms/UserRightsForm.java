package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.utils.Constants;
import javax.servlet.http.HttpSession;


/**
 *
 * @author durval
 */
public class UserRightsForm extends BaseUserDetailForm {

    public String toString () 
    {
        return "UserRightsForm {\n _user = " + _user
            + "\n _accountData = " + _accountData
            + "\n _rights = " + _rights + " }";
    }
    
    public UserRightsForm() 
    {
        _user = null;
        _accountData = null;
        _rights = "";
    }

    public UserRightsForm(UserData pUserData,
                          BusEntityData pAccountData,
                          String pRights) {
        _user = pUserData;
        _accountData = pAccountData;
        _rights = pRights;

        com.cleanwise.service.api.util.UserRightsTool userRights = new
                com.cleanwise.service.api.util.UserRightsTool(pUserData, pRights);

        // set al lthe flags based on user rights.

        _showPrice = userRights.getShowPrice();
        _browseOnly = userRights.isBrowseOnly();
        _contractItemsOnly = userRights.isUserOnContract();
        _onAccount = userRights.getOnAccount();
        _otherPmt = userRights.getOtherPaymentFlag();
        _creditCard = userRights.getCreditCardFlag();
        _poNumRequired = userRights.getPoNumRequired();
        _canApproveOrders = userRights.canApprovePurchases();
        _orderNotificationNeedsApprovalEmail = userRights.getsEmailForApproval();
        _orderNotificationApprovedEmail = userRights.getsEmailOrderApproved();
        _orderNotificationRejectedEmail = userRights.getsEmailOrderRejection();
        _orderNotificationModifiedEmail = userRights.getsEmailOrderModifications();
        _noReporting = userRights.isNoReporting();
        _salesPresentationOnly = userRights.isPresentationOnly();

        _canEditShipTo = userRights.canEditShipTo();
        _canEditBillTo = userRights.canEditBillTo();

        _orderDetailNotificationEmail = userRights.getEmailOrderDetailNotification();
        _orderNotificationShippedEmail = userRights.getOrderNotificationShipped();
        _reportingManager = userRights.isReportingManager();					//aj
        _reportingAssignAllAccts = userRights.isReportingAssignAllAccts();		//aj
        _workOrderCompletedNotification = userRights.getWorkOrderCompletedNotification();
        _workOrderAcceptedByProviderNotification = userRights.getWorkOrderAcceptedByProviderNotification();
        _workOrderRejectedByProviderNotification = userRights.getWorkOrderRejectedByProviderNotification();
        _cutoffTimeReminderEmail = userRights.getCutoffTimeReminderEmail();
    	_physicalInvNonComplSiteListingEmail = userRights.getPhysicalInvNonComplSiteListingEmail();
    	_physicalInvCountsPastDueEmail = userRights.getPhysicalInvCountsPastDueEmail();

    }
    
    private UserData _user;
    private String _rights;
    private BusEntityData          _accountData;
    
    /**
     * Return the user detail object
     */
    public UserData getUserData() {
        return (this._user);
    }

    public String getRightsFromOptions() {
        
        return makePermissionsToken(); 

    }
    
    /**
     * Set the user detail object
     */
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
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
            return null;
	}
}
