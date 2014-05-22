/**
 * Title: CheckOutEswLogic 
 * Description: This is the business logic class handling the ESW Checkout functionality.
 */
package com.espendwise.view.logic.esw;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ShoppingCartDistData;
import com.cleanwise.service.api.value.ShoppingCartDistDataVector;
import com.cleanwise.view.forms.CheckoutForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.CheckoutLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.CheckOutEswForm;

public class CheckOutEswLogic {

	private static final Logger log = Logger.getLogger(CheckOutEswLogic.class);
	
	public static ActionErrors showCheckOut(HttpServletRequest request,CheckOutEswForm form) {
		// Get the existing CheckoutForm and pass it on to the existing
		// Logic method.
		CheckoutForm checkOutForm = form.getCheckOutForm();
		
	    ActionErrors errors = CheckoutLogic.init(request, checkOutForm);
	    //Default User Date Format
	    //String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
	    String defaultDateFormat = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.text.inputDateFormat",null);
	    checkOutForm.setProcessOrderDate(defaultDateFormat);
	    checkOutForm.setRequestedShipDate(defaultDateFormat);
	    
		return errors;
	}
	
	public static ActionErrors doPlaceOrder(HttpServletRequest request,CheckOutEswForm form) throws Exception {
		// Get the existing CheckoutForm and pass it on to the existing
		// Logic method.
		CheckoutForm checkOutForm = form.getCheckOutForm();
		
		ActionErrors errors = validateCheckOutFields(request, checkOutForm);
		if(errors==null || errors.isEmpty()) {
			errors = CheckoutLogic.placeOrder(request, checkOutForm);
		}
		return errors;
	} 
	
	public static ActionErrors doPlaceOrderViaCreditCard(HttpServletRequest request,CheckOutEswForm form) throws Exception {
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
    			if(Utility.strNN(checkOutForm.getCustomerComment()).length()>Constants.MAX_SIZE_OF_TEXT_AREA_VALUE){
    				log.error("Customer Comments should not exceed 4000 characters.");
    	            String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.checkout.error.customerComments", null);
    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			}
    		}
    		if(Utility.isSet(checkOutForm.getComments())){
    			if(Utility.strNN(checkOutForm.getComments()).length()>Constants.MAX_SIZE_OF_TEXT_AREA_VALUE){
    				log.error("Shipping Comments should not exceed 4000 characters.");
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
    		if(defaultDateFormat.equals(checkOutForm.getProcessOrderDate())){
    			checkOutForm.setProcessOrderDate("");
    		}
    		if(defaultDateFormat.equals(checkOutForm.getRequestedShipDate())) {
    			checkOutForm.setRequestedShipDate("");
    		}
    	    
    		//TODO: Temporary error messages these should be removed once the functionalities of 
    		// Record of call and Credit Card are implemented.
    		
    		if(!Utility.isSet(checkOutForm.getOtherPaymentInfo())) {
    			if(checkOutForm.getReqPmt().equals("Other") ) {
    				String errorMess = "The functionality for 'Record Of Call' is not implemented yet. Please try another Payment Type.";
    				errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    			}
    		}
    		if(checkOutForm.isaCcReq()){
    			String errorMess = "The functionality for 'Credit Cart' is not implemented yet. Please try another Payment Type.";
				errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    		}
    	       
    	}
		return errors;
	}
}
