/**
 * Title: CheckOutEswAction 
 * Description: This is the Struts Action class handling the ESW CheckOut functionality.
 */

package com.espendwise.view.actions.esw;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.espendwise.view.forms.esw.CheckOutEswForm;
import com.espendwise.view.logic.esw.CheckOutEswLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class CheckOutEswAction extends EswAction {
    private static final Logger log = Logger.getLogger(CheckOutEswAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_CHECK_OUT = "showCheckOutPage";
    
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
    	
    	CheckOutEswForm theForm = (CheckOutEswForm)form;
        
        //No need to set any sub tabs.
    	Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
        
    	//determine what action to perform
        //If an operation has been specified use it.
    	String operation = theForm.getOperation();
    	
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
        
    	return returnValue;   
    }

    /*
     * Private method to determine what action forward should be returned after a show Checkout request.
     */
	private ActionForward handleCheckOutRequest(HttpServletRequest request,
			HttpServletResponse response, CheckOutEswForm theForm,
			ActionMapping mapping) {
		
		ActionErrors errors = null;
		ActionForward actionForward = mapping.findForward(MAPPING_CHECK_OUT);
		//Populate Common Data
		populateCommonData(request,theForm);
		try {
			errors = CheckOutEswLogic.showCheckOut(request,theForm);
			
			if(errors!=null && errors.size()>0) {
				 saveErrors(request, errors);
			} 
			else if(theForm.getCheckOutForm().getItemsSize()==0) {
				ActionMessages messages = new ActionMessages();
		    	String message = ClwI18nUtil.getMessage(request,"shop.checkout.text.shoppiningCartIsEmpty", null);
		    	messages.add("message", new ActionMessage("message.simpleMessage", message));
		    	saveMessages(request, messages);
			}
		} catch(Exception e) {
			log.error("Unexpected exception in CheckOutEswAction.handleCheckOutRequest: " +e);
			saveErrors(request,mapping,errors);
		}
		
		
		return actionForward;
	}
	
	/*
     * Private method to determine what action forward should be returned after a show place Order request.
     */
	private ActionForward handlePlaceOrderRequest(HttpServletRequest request, HttpServletResponse response, 
			CheckOutEswForm theForm, ActionMapping mapping) {
		ActionErrors errors = null;
		ActionForward actionForward = mapping.findForward(MAPPING_CHECK_OUT);
		
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		//Lock if the purchase is in progress or not.
		if(!sessionDataUtil.isPurchaseInProgress()) {
			try {
				//Put a lock on purchase process
				sessionDataUtil.setPurchaseInProgress(true);
				 //If pay metrics is set as Credit Card.
				 if(Boolean.parseBoolean(String.valueOf(request.getSession(false).getAttribute(Constants.PAYMETRICS_CC)))) {
					 errors = CheckOutEswLogic.doPlaceOrderViaCreditCard(request,theForm);
				 } else {
					 errors = CheckOutEswLogic.doPlaceOrder(request,theForm);
				 }
				 if(errors!=null && errors.size()>0) {
					 saveErrors(request, errors);
				 }
				
			} catch(Exception e) {
				log.error("Unexpected exception in CheckOutEswAction.handleCheckOutRequest: " +e);
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
    private void populateCommonData(HttpServletRequest request, CheckOutEswForm form) {
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
}

