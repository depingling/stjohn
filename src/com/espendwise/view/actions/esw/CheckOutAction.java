/**
 * Title: CheckOutAction 
 * Description: This is the Struts Action class handling the ESW CheckOut functionality.
 */

package com.espendwise.view.actions.esw;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Country;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.esw.SiteLocationBudgetLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.CheckOutForm;
import com.espendwise.view.logic.esw.CheckOutLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class CheckOutAction extends EswAction {
    private static final Logger log = Logger.getLogger(CheckOutAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_CHECK_OUT = "showCheckOutPage";
    private static final String MAPPING_CUST_SYS_REDIRECT = "customerSystemPostRedirect";
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * @param  mapping      the ActionMapping used to select this instance.
     * @param  form         the ActionForm containing the data.
     * @param  request      the HTTP request we are processing.
     * @param  response     the HTTP response we are creating.
     * @return              an ActionForward describing the component that should receive control.
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            						HttpServletRequest request, HttpServletResponse response) {

        //If there isn't a currently logged on user then go to the login page
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        
    	ActionForward returnValue = null;
    	
    	CheckOutForm theForm = (CheckOutForm)form;
        
        //No need to set any sub tabs.
    	Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
        
    	//determine what action to perform
        //If an operation has been specified use it.
    	String operation = theForm.getOperation();
    	clearWarningMessages(theForm);
    	//If no operation was specified and there is no previous operation, default to show all orders
    	if (!Utility.isSet(operation)) {
    		operation = Constants.PARAMETER_OPERATION_VALUE_CHECK_OUT;
    	}
    	
    	//trim whitespace if an operation has been specified
    	if (Utility.isSet(operation)) {
    		operation = operation.trim();
    	}
    	
    	//now that we've determined what action to take, take it
    	//Begin: CheckOut 
    	if (Constants.PARAMETER_OPERATION_VALUE_CHECK_OUT.equalsIgnoreCase(operation)) {
        	returnValue = handleCheckOutRequest(request, response, theForm, mapping);
        } 
    	else if(Constants.PARAMETER_OPERATION_VALUE_PLACE_ORDER.equalsIgnoreCase(operation)) {
    		returnValue = handlePlaceOrderRequest(request, response, theForm, mapping);
    	}
        //End: CheckOut
        else {
        	returnValue = handleUnknownOperation(request, response, theForm, mapping);
        }
    	checkWarningMessages(request, theForm);
    	return returnValue;   
    }

    /*
     * Private method to determine what action forward should be returned after a show Checkout request.
     */
	private ActionForward handleCheckOutRequest(HttpServletRequest request,
			HttpServletResponse response, CheckOutForm theForm,
			ActionMapping mapping) {
		
		ActionErrors errors = null;
		ActionForward actionForward = mapping.findForward(MAPPING_CHECK_OUT);
		//Populate Common Data
		populateCommonData(request,theForm);
		CleanwiseUser user = ShopTool.getCurrentUser(request);
		try {
            if (user.isBrowseOnly()) {
                errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,
                        "orders.error.browseOnlyCannotCheckout", null);
                errors.add("error", new ActionMessage(
                        "error.simpleGenericError", message));
                saveErrors(request, errors);
                return actionForward;
            }
			errors = CheckOutLogic.showCheckOut(request,theForm);
			
			if(errors!=null && errors.size()>0) {
				 saveErrors(request, errors);
			} 
			else if(theForm.getCheckOutForm().getItemsSize()==0) {
				ActionMessages messages = new ActionMessages();
		    	String message = ClwI18nUtil.getMessage(request,"shop.checkout.text.shoppiningCartIsEmpty", null);
		    	messages.add("message", new ActionMessage("message.simpleMessage", message));
		    	saveMessages(request, messages);
			}
			
			//STJ-5669
			//update location budget chart.
			//errors = ShoppingLogic.updateLocationBudgetChart(request);
			
			//STJ-5637 In checkout page, we need to include order fee(freight, handling, discount, and tax) in cart amount.
			//Here we pass a true value as the 4th argument so that ReportingLogic will include this order fee
			//based on this true value.
			errors = SiteLocationBudgetLogic.updateLocationBudgetChart(request,
					user.getSite(), user, theForm.getCheckOutForm(), theForm.getOrderFee());
			if(errors != null && errors.size() > 0) {
				saveErrors(request, errors);
			}	
			
			//STJ-5637
			if(theForm.getOrderFee() != null) {
				theForm.getCheckOutForm().setDistFreightVendor(new String[]{theForm.getOrderFee()});
			}
			
			HttpSession session = request.getSession();
	        if (session.getAttribute("country.vector") == null)
	        {
	        	APIAccess factory = new APIAccess();		        
		        Country countryBean = factory.getCountryAPI();
		        CountryDataVector countriesv = countryBean.getAllCountries();
		        session.setAttribute("country.vector", countriesv);
	        }
			
		} catch(Exception e) {
			log.error("Unexpected exception in CheckOutAction.handleCheckOutRequest: " +e);
			saveErrors(request,mapping,errors);
		}
        // set user info

        String userContactName = user.getUser().getFirstName() + " " + user.getUser().getLastName();
        if (Utility.isSet(userContactName)) {
           theForm.getCheckOutForm().setOrderContactName(userContactName);
        }
        if (user.getContact() == null) {
            try {
                APIAccess factory = new APIAccess();
                User userBean = factory.getUserAPI();
                UserInfoData userInfoData = userBean.getUserContactForNotification(user.getUserId());
                if (userInfoData != null) {
                    user.setContact(userInfoData);
                }
            } catch (Exception e) {
            }
        }
		if (user.getContact() != null && user.getContact().getPhone() != null ) {
            String contactPhone = user.getContact().getPhone().getPhoneNum();
            if (Utility.isSet(contactPhone)) {
                theForm.getCheckOutForm().setOrderContactPhoneNum(contactPhone);
            }
        }
        if (user.getContact() != null && user.getContact().getEmailData() != null) {
            String contactEmail = user.getContact().getEmailData().getEmailAddress();
            if (Utility.isSet(contactEmail)) {
                theForm.getCheckOutForm().setOrderContactEmail(contactEmail);
            }
        }

        if (errors.size() == 0 && theForm.getPaymentTypeChoices().size() == 0){
        	String message = ClwI18nUtil.getMessage(request,
                    "shop.checkout.text.paymentTypeNotSetup", null);
            errors.add("error", new ActionMessage(
                    "error.simpleGenericError", message));
            saveErrors(request, errors);
        }
		return actionForward;
	}
	
	/*
     * Private method to determine what action forward should be returned after a show place Order request.
     */
	private ActionForward handlePlaceOrderRequest(HttpServletRequest request, HttpServletResponse response, 
			CheckOutForm theForm, ActionMapping mapping) {
		ActionErrors errors = null;
		ActionForward actionForward = mapping.findForward(MAPPING_CHECK_OUT);
        if (ShopTool.getCurrentUser(request).isBrowseOnly()) {
            errors = new ActionErrors();
            String message = ClwI18nUtil.getMessage(request,
                    "orders.error.browseOnlyCannotPlaceOrder", null);
            errors.add("error", new ActionMessage("error.simpleGenericError",
                    message));
            saveErrors(request, errors);
            return actionForward;
        }
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		//STJ-5637
		theForm.setOrderFee(null);
		
		//STJ-4905 - if the user is logged in as somebody else, update the Placed By value on the form
		CleanwiseUser user = ShopTool.getCurrentUser(request);
		if (user.getOriginalUser() != null) {
		    StringBuilder placedBy = new StringBuilder(50);
			placedBy.append(user.getOriginalUser().getUser().getFirstName());
			placedBy.append(" ");
			placedBy.append(user.getOriginalUser().getUser().getLastName());
			placedBy.append(" (");
			placedBy.append(user.getUser().getFirstName());
			placedBy.append(" ");
			placedBy.append(user.getUser().getLastName());
			placedBy.append(")");
			theForm.getCheckOutForm().setOrderContactName(placedBy.toString());
		}
		//Lock if the purchase is in progress or not.
		if(!sessionDataUtil.isPurchaseInProgress()) {
			try {
				//Put a lock on purchase process
				sessionDataUtil.setPurchaseInProgress(true);
				 //If pay metrics is set as Credit Card.
				 if(Boolean.parseBoolean(String.valueOf(request.getSession(false).getAttribute(Constants.PAYMETRICS_CC)))) {
					 errors = CheckOutLogic.doPlaceOrderViaCreditCard(request,theForm);
				 } else {
					 errors = CheckOutLogic.doPlaceOrder(request,theForm);
				 }
				 if(errors!=null && errors.size()>0) {
					 saveErrors(request, errors);
				 }else{
					 CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
					 if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(appUser.getUserAccount().getCustomerSystemApprovalCd())){
						 actionForward = mapping.findForward(MAPPING_CUST_SYS_REDIRECT);
					 }
				 }
				
			} catch(Exception e) {
				log.error("Unexpected exception in CheckOutAction.handleCheckOutRequest: " +e);
				saveErrors(request,mapping,errors);
			} finally {
				//Unlock the purchasing process
				sessionDataUtil.setPurchaseInProgress(false);
			}
		} else {
			String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.purchaseIsInProgress", null);
			if(errors==null) {
				errors = new ActionErrors();
			}
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.simpleGenericError", errorMess));
			saveErrors(request, errors);
		}
		log.info("Confirmation Flag Value=============="+theForm.getCheckOutForm().getConfirmationFlag());
		
		return actionForward;
		
	}
	
	/* 
	 * Private method to gather data commonly needed on the Checkout page.
     */
    private void populateCommonData(HttpServletRequest request, CheckOutForm form) {
    	form.setPaymentTypeChoices(ClwI18nUtil.getPaymentTypeChoices(request));

    }
    
    @SuppressWarnings("deprecation")
	/**
	 * Saves the generic error and returns action forward for error page.
	 */
    private ActionForward saveErrors(HttpServletRequest request,ActionMapping mapping,ActionErrors errors) {
    	
     if(errors==null) {
    	errors = new ActionErrors();
      }
      String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
      errors.add("error", new ActionError("error.simpleGenericError", errorMess));
      saveErrors(request, errors);
      
      return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
    }

    private void clearWarningMessages(CheckOutForm theForm) {
        if (theForm != null && theForm.getCheckOutForm() != null) {
            theForm.getCheckOutForm().setWarningMessages(null);
        }
    }

    private void checkWarningMessages(HttpServletRequest request,
            CheckOutForm theForm) {
        if (theForm != null
                && theForm.getCheckOutForm() != null
                && Utility
                        .isSet(theForm.getCheckOutForm().getWarningMessages())) {
            ActionMessages messages = getMessages(request);
            if (messages == null) {
                messages = new ActionMessages();
            }
            for (Object message : theForm.getCheckOutForm()
                    .getWarningMessages()) {
                messages.add("message", new ActionMessage(
                        "message.simpleMessage", message));
            }
            saveMessages(request, messages);
        }
    }
}