/**
 * Title: CheckOutEswLogic 
 * Description: This is the business logic class handling the ESW Checkout functionality.
 */
package com.espendwise.view.logic.esw;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.ShoppingCartDistData;
import com.cleanwise.service.api.value.ShoppingCartDistDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.CheckoutLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.CheckOutForm;

public class CheckOutLogic {

	private static final Logger log = Logger.getLogger(CheckOutLogic.class);
	
	public static ActionErrors showCheckOut(HttpServletRequest request,CheckOutForm form) {
		// Get the existing CheckoutForm and pass it on to the existing
		// Logic method.
		CheckoutForm checkOutForm = form.getCheckOutForm();
		
	    ActionErrors errors = CheckoutLogic.init(request, checkOutForm);
	    
	    //STJ-4384 :Initialize ReBill Info, if it is applicable.
		initReBillInfo(request, checkOutForm);
		
	    //Default User Date Format
	    String defaultDateFormat = ClwI18nUtil.getUIDateFormat(request);
	    defaultDateFormat = defaultDateFormat.toLowerCase();
	    //String defaultDateFormat = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.text.inputDateFormat",null);
	    checkOutForm.setProcessOrderDate(defaultDateFormat);
	    checkOutForm.setRequestedShipDate(defaultDateFormat);
	    SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
	    form.getCheckOutForm().setBypassBudget(sessionData.isExcludeOrderFromBudget());
	    
		return errors;
	}
	
	public static ActionErrors doPlaceOrder(HttpServletRequest request,CheckOutForm form) throws Exception {
		// Get the existing CheckoutForm and pass it on to the existing
		// Logic method.
		CheckoutForm checkOutForm = form.getCheckOutForm();
		//STJ-4384
		initReBillInfo(request, checkOutForm);
		
		ActionErrors errors = validateCheckOutFields(request, checkOutForm);
		if(errors==null || errors.isEmpty()) {
			errors = CheckoutLogic.placeOrder(request, checkOutForm);
		}
		return errors;
	} 
	
	public static ActionErrors doPlaceOrderViaCreditCard(HttpServletRequest request,CheckOutForm form) throws Exception {
		// Get the existing CheckoutForm and pass it on to the existing
		// Logic method.
		CheckoutForm checkOutForm = form.getCheckOutForm();
		
		ActionErrors errors = validateCheckOutFields(request, checkOutForm);
		if(errors==null || errors.isEmpty()) {
			errors = CheckoutLogic.paymetricAuthorization(request,checkOutForm);
		}
		
		if(errors ==null || errors.isEmpty()){
			errors = CheckoutLogic.placeOrderSave(request, checkOutForm);
		}
		
		return errors;
	} 
	
	private static ActionErrors validateCheckOutFields(HttpServletRequest request, CheckoutForm checkOutForm){
		ActionErrors errors = new ActionErrors();
    	if (checkOutForm == null) {
        	log.error("Missing CheckoutForm Object");
            String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.error.problemRetrievingData", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	} else {
    		if(Utility.isSet(checkOutForm.getCustomerComment())){
    			if(Utility.strNN(checkOutForm.getCustomerComment()).length()>Constants.MAX_SIZE_OF_CUSTOMER_COMMENTS){
    				log.error("Customer Comments should not exceed 1999 characters.");
    	            String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.error.customerComments", null);
    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			}
    		}
    		if(Utility.isSet(checkOutForm.getComments())){
    			if(Utility.strNN(checkOutForm.getComments()).length()>Constants.MAX_SIZE_OF_SHIPPING_COMMNETS){
    				log.error("Shipping Comments should not exceed 1000 characters.");
    	            String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.error.shippingComments", null);
    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			}
    		}
    		if(checkOutForm.getItemsSize()==0) {
		    	String message = ClwI18nUtil.getMessage(request,"shop.checkout.text.shoppiningCartIsEmpty", null);
		    	errors.add("message", new ActionMessage("message.simpleMessage", message));
    		}
    		
    		//Validate Freight.
    		//If Select Option is selected then return an error message.
    		CleanwiseUser user = ShopTool.getCurrentUser(request);
    		boolean isaMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(user.getUserStore().getStoreType().getValue());
    		 if (!isaMLAStore) { //NOT an MLA store
    			 ShoppingCartDistDataVector cartDistV = checkOutForm.getCartDistributors();
    			 if (Utility.isSet(cartDistV)) {
    				 String[] freightVendors = checkOutForm.getDistFreightVendor();
    				 
    				 int minNum = 0;
    		         if (freightVendors != null) {
    		            minNum = freightVendors.length < cartDistV.size() ? freightVendors.length : cartDistV.size();
    		         }
    		         String selectOption = ClwI18nUtil.getMessage(request, "shop.checkout.text.selectOption", null);
    		         for (int i = 0; i < minNum; i++) {
			            ShoppingCartDistData distD = (ShoppingCartDistData) cartDistV.get(i);
			            if ((null == freightVendors[i] || selectOption.equals(freightVendors[i].trim()))
			                && (null != distD.getDistFreightOptions() && 0 < distD.getDistFreightOptions().size())) {
			              Object[] param = new Object[1];
			              param[0] = distD.getDistributorName();
			              String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.errors.selectFreightForDistributor", param);
			              errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.simpleGenericError", errorMess));
			            }
    			     }
    			 }
    		 }
    		
    		//Reset Date fields to empty if they have default User Locale format.
    		String defaultDateFormat = ClwI18nUtil.getUIDateFormat(request);
    		//Process Order Date.
    		if(defaultDateFormat.equalsIgnoreCase(checkOutForm.getProcessOrderDate())){
    			checkOutForm.setProcessOrderDate("");
    		} else {
    		    String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.text.processOrderOn");
                if(!Utility.isSet(fieldName)) {
                	fieldName = Constants.FIELD_PROCESS_ORDER_ON_DATE;
                }
    			if(Utility.isSet(checkOutForm.getProcessOrderDate())) {
    				try {
            			Date processOrderDate = ClwI18nUtil.parseDateInp(request, checkOutForm.getProcessOrderDate());
            			Date now = new Date();
            			if (processOrderDate.compareTo(now) <= 0) {
        		           String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.errors.dateBeforeCurrentDate", new Object[]{fieldName});
        		           errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        		         }
    				} catch (ParseException e) {
    					log.error("Invalid Process Order On Date is entered.");
    	                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, checkOutForm.getProcessOrderDate()});
    	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    				}
                }
            }
    		
    		//Requested Delivery Date.
    		//STJ-4696
    		if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_MANDATORY_REQUEST_SHIP_DATE) ||
    		   user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_REQUEST_SHIP_DATE) ){
    			String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.text.requestedDeliveryDate");
                if(!Utility.isSet(fieldName)) {
                	fieldName = Constants.FIELD_REQUSTED_DELIVERY_DATE;
                }
    			if(defaultDateFormat.equalsIgnoreCase(checkOutForm.getRequestedShipDate())) {
    				checkOutForm.setRequestedShipDate("");
    			}
    			if(Utility.isSet(checkOutForm.getRequestedShipDate())) {
    				try {
            			Date reqDeliveryDate = ClwI18nUtil.parseDateInp(request, checkOutForm.getRequestedShipDate());
            			Date now = new Date();
            			if (reqDeliveryDate.compareTo(now) <= 0) {
        		           String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.errors.dateBeforeCurrentDate", new Object[]{fieldName});
        		           errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        		         }
    				} catch (ParseException e) {
    					log.error("Invalid Requested Delivery Date is entered.");
    	                String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.invalidDate", new Object[]{fieldName, checkOutForm.getRequestedShipDate()});
    	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    				}
    			} else {
    			    if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_MANDATORY_REQUEST_SHIP_DATE)) {
                        log.error("Requested Delivery Date is required field.");
                        String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.requiredField", new Object[]{fieldName});
                        errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                    }
    			}
    		} else {
    			if(defaultDateFormat.equalsIgnoreCase(checkOutForm.getRequestedShipDate())) {
    				checkOutForm.setRequestedShipDate("");
    			}
    		}

    		// PO Number
	        boolean allowPoEntry = true;
        	if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
                equals(user.getUserAccount().getCustomerSystemApprovalCd())){
		        allowPoEntry = false;
        	}
	        if(checkOutForm.getSite().getBlanketPoNum()!= null &&
	            checkOutForm.getSite().getBlanketPoNum().getBlanketPoNumId()!=0) {
        	    allowPoEntry=false;
            }
            if(!user.getUserAccount().isCustomerRequestPoAllowed()){
                  allowPoEntry=false;
            }
    		if (allowPoEntry) {
                if (user.getPoNumRequired() && !Utility.isSet(checkOutForm.getPoNumber())) {
                    String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.text.poNum");
                    if(!Utility.isSet(fieldName)) {
                        fieldName = Constants.FIELD_PO_NUMBER;
                    }
   					log.error("PO number is required field.");
                    String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.requiredField", new Object[]{fieldName});
                    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                }
            }
    	    
    		//TODO: Temporary error messages - These should be removed once the functionalities of 
    		// Record of call and Credit Card are implemented.
    		
    		if(!Utility.isSet(checkOutForm.getOtherPaymentInfo())) {
    			if(checkOutForm.getReqPmt().equals("Other") ) {
    				String errorMess = "The functionality for 'Record Of Call' is not implemented yet. Please try another Payment Type.";
    				errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			}
    		}
    		if(checkOutForm.isaCcReq()){
    			String errorMess = "The functionality for 'Credit Card' is not implemented yet. Please try another Payment Type.";
				errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    		}
    	       
    	}
		return errors;
	}
	
	private static void initReBillInfo(HttpServletRequest request, CheckoutForm checkOutForm) {
		CleanwiseUser user = ShopTool.getCurrentUser(request);
		AccountData account = user.getUserAccount();
		if(account.isShowReBillOrder()) {
			SiteData siteData = user.getSite();
			if(siteData.getReBill()!=null && Utility.isTrue(siteData.getReBill().getValue())){
				checkOutForm.setRebillOrder("true");
			}
		}
	}
}
