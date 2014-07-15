/**
 * Title: ModuleIntegrationAction 
 * Description: This is the Struts Action class handling integration between the various
 * eSpendwise modules (St. John, Orca, etc).
 */

package com.espendwise.view.actions.esw;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.StoreMessageData;
import com.cleanwise.service.api.value.StoreMessageDataVector;
import com.cleanwise.service.api.value.StoreMessageView;
import com.cleanwise.service.api.value.StoreMessageViewVector;
import com.cleanwise.service.apps.MessageService;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.ModuleIntegrationForm;
import com.espendwise.view.logic.esw.DashboardLogic;

/**
 * Implementation of <code>Action</code> that handles module integration
 * functionality.
 */
public final class ModuleIntegrationAction extends EswAction {
	private static final Logger log = Logger
			.getLogger(ModuleIntegrationAction.class);

	// constants to hold the various action mappings that can be returned by this
	// action class.
	private static final String MAPPING_MODULE_ASSETS = "assets";
	private static final String MAPPING_MODULE_SERVICES = "services";
	private static final String MAPPING_MODULE_SHOPPING_DASHBOARD = "shoppingDashboard";
	private static final String MAPPING_MODULE_SHOPPING_PRODUCTS = "shoppingProducts";
	private static final String MAPPING_MODULE_SHOPPING_ORDERS = "shoppingOrders";
	private static final String MAPPING_MODULE_REPORTING = "reporting";
	private static final String MAPPING_MODULE_DOCUMENTATION = "documentation";
	private static final String MAPPING_MODULE_UNDETERMINED = "undetermined";
	private static final String MAPPING_TRADITIONAL_UI = "traditionalUI";
	private static final String MAPPING_SHOW_MESSAGE = "showMessage";

	/**
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it). Return
	 * an <code>ActionForward</code> instance describing where and how control
	 * should be forwarded, or <code>null</code> if the response has already been
	 * completed.
	 * 
	 * @param mapping
	 *          the ActionMapping used to select this instance.
	 * @param form
	 *          the ActionForm containing the data.
	 * @param request
	 *          the HTTP request we are processing.
	 * @param response
	 *          the HTTP response we are creating.
	 * @return an ActionForward describing the component that should receive
	 *         control.
	 */
	public ActionForward performAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// If there isn't a currently logged on user then go to the login page
		if (!new SessionTool(request).checkSession()) {
			return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
		}

		ActionForward returnValue = null;
		ModuleIntegrationForm theForm = (ModuleIntegrationForm) form;
		String operation = theForm.getOperation();
		// trim whitespace from the operation
		if (Utility.isSet(operation)) {
			operation = operation.trim();
		}

		// if no operation has been specified, then determine the landing page for
		// the user and send them
		// there
		if (!Utility.isSet(operation)) {
			try {
	    		// check if user password expired
	    		if (DashboardLogic.isUserPasswordExpired(request)){
	    			ActionErrors errors = new ActionErrors();
	    	        String errorMess = ClwI18nUtil.getMessage(request, "shop.userProfile.error.passwordExpired", null);
	    	        errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	    	        
	    	        //because we need to issue a redirect to return the user to the reset password page, 
	    	        //save the errors in the session (not the request) so they will be available after the redirect	            	
	            	Utility.getSessionDataUtil(request).setErrors(errors);
	    	    	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_RESET_PASSWORD);
	    		}
	    		// check if user password need reset
	    		if (DashboardLogic.isUserPasswordNeedReset(request)){
	    			ActionErrors errors = new ActionErrors();
	    	        String errorMess = ClwI18nUtil.getMessage(request, "shop.userProfile.error.needResetPassword", null);
	    	        errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	    	        
	    	        //because we need to issue a redirect to return the user to the reset password page, 
	    	        //save the errors in the session (not the request) so they will be available after the redirect
	            	Utility.getSessionDataUtil(request).setErrors(errors);
	    	    	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_RESET_PASSWORD);
	    		}  
    		}catch (Exception e){
    			ActionErrors errors = new ActionErrors();
    	        String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
    	        errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    	        saveErrors(request, errors);
    	    	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
    		}
			returnValue = handleDetermineLandingPage(request, response, theForm,
					mapping);
		}
		// if the user has requested the assets module, handle that
		else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_ASSETS
				.equalsIgnoreCase(operation)) {
			returnValue = handleShowModuleAssets(request, response, theForm, mapping);
		}
		// if the user has requested the services module, handle that
		else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_SERVICES
				.equalsIgnoreCase(operation)) {
			returnValue = handleShowModuleServices(request, response, theForm,
					mapping);
		}
		// if the user has requested the shopping module, handle that
		else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_SHOPPING
				.equalsIgnoreCase(operation)) {
			returnValue = handleShowModuleShopping(request, response, theForm,
					mapping);
		}
		// if the user has requested the reporting module, handle that
		else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_REPORTING
				.equalsIgnoreCase(operation)) {
			returnValue = handleShowModuleReporting(request, response, theForm,
					mapping);
		}
		// if the user has requested the documentation module, handle that. Until
		// documentation
		// is implemented, return an error
		else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_DOCUMENTATION
				.equalsIgnoreCase(operation)) {
			returnValue = handleShowModuleDocumentation(request, response, theForm,
					mapping);
		} else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGE
				.equalsIgnoreCase(operation)) {
			returnValue = handleShowMessageRequest(request, response, theForm,
					mapping);
		} else {
			returnValue = handleUnknownOperation(request, response, theForm, mapping);
		}

		// if we're not in the process of showing a message, determine if we need to
		// do anything
		// around interstitial messages.
		if (!Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGE
				.equalsIgnoreCase(operation)) {
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			// get the interstitial message information from the session.
			StoreMessageViewVector interstitialMessages = sessionDataUtil
					.getInterstitialMessages();
			Integer interstitialMessageIndex = sessionDataUtil
					.getInterstitialMessageIndex();

			// if there are any interstitial messages that haven't been displayed,
			// return the next
			// one and increment the index. The landing page will recognize that an
			// interstitial
			// message needs to be displayed and handle requesting it so that we can
			// reuse the
			// Bridgeline code used to show a "regular" message.
			if (interstitialMessages != null && interstitialMessageIndex != null) {
				int lastIndex = interstitialMessageIndex.intValue() - 1;
				// if the last displayed message is 'ACKNOWLEDGEMENT_REQUIRED' type,
				// then mark as read.
				if (lastIndex > -1) {
					StoreMessageView lastMessage = (StoreMessageView) interstitialMessages
							.get(lastIndex);
					markMessageAsReadByUser(lastMessage, request);
				}
				// next message to display.
				if (interstitialMessageIndex.intValue() < interstitialMessages.size()) {
					theForm.setCurrentMessage((StoreMessageView) interstitialMessages
							.get(interstitialMessageIndex.intValue()));
					sessionDataUtil.setInterstitialMessageIndex(new Integer(
							interstitialMessageIndex.intValue() + 1));
				}
			}
		}
		return returnValue;
	}

	/*
	 * Private method to determine the landing page for a user.
	 */
	private ActionForward handleDetermineLandingPage(HttpServletRequest request,
			HttpServletResponse response, ModuleIntegrationForm form,
			ActionMapping mapping) {

		ActionForward returnValue = null;

		CleanwiseUser user = ShopTool.getCurrentUser(request);

		/*
		 * boolean authorizedForAssets =
		 * user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS
		 * .ACCESS_ASSETS); boolean authorizedForServices =
		 * user.isAuthorizedForFunction
		 * (RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SERVICES);
		 */
		boolean authorizedForShopping = user
				.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SHOPPING);
		boolean authorizedForReporting = !user.isNoReporting();
		// STJ-5261 For mobile clients,to check the user is configured to use the
		// Shopping module is not in the requirement.
		String mobileClient = (String) request.getSession(false).getAttribute(
				Constants.MOBILE_CLIENT);
		if (mobileClient != null && mobileClient.equals("true")) {
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			sessionDataUtil.setMobileLocationSelected("currentLocation");
			return mapping.findForward(MAPPING_MODULE_SHOPPING_ORDERS);
		}
		// if the user is configured to use the assets module, that is their landing
		// page
		/*
		 * if (authorizedForAssets ) { returnValue = handleShowModuleAssets(request,
		 * response, form, mapping); }
		 */
		// if the user is not configured to use the assets module but is configured
		// to use
		// the services tab, that is their landing page
		/*
		 * else if (authorizedForServices) { returnValue =
		 * handleShowModuleServices(request, response, form, mapping); }
		 */

		// if the user is not configured to use the assets or services module but is
		// configured
		// to use the shopping module, that is their landing page
		if (authorizedForShopping) {
			returnValue = handleShowModuleShopping(request, response, form, mapping);
		}
		// if the user is not configured to use the assets, services, or shopping
		// modules but
		// is configured to run reports then reporting is their landing page
		else if (authorizedForReporting) {
			returnValue = handleShowModuleReporting(request, response, form, mapping);
		}
		// if the user is configured to use no functionality, log them out and
		// return an error
		else {
			ActionErrors errors = new ActionErrors();
			String errorMess = ClwI18nUtil.getMessage(request,
					"login.errors.userNotConfiguredForFunctionality");
			errors.add("error",
					new ActionError("error.simpleGenericError", errorMess));
			saveErrors(request, errors);
			returnValue = mapping.findForward(MAPPING_MODULE_UNDETERMINED);
		}
		return returnValue;
	}

	/*
	 * Private method to show the assets module.
	 */
	private ActionForward handleShowModuleAssets(HttpServletRequest request,
			HttpServletResponse response, ModuleIntegrationForm form,
			ActionMapping mapping) {

		ActionForward returnValue = null;

		boolean authorizedForAssets = ShopTool.getCurrentUser(request)
				.isAuthorizedForFunction(
						RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_ASSETS);
		Utility.getSessionDataUtil(request)
				.setSelectedMainTab(Constants.TAB_ASSETS);
		if (authorizedForAssets) {
			// JEE TODO - determine how to integrate with Orca. For now return an
			// error
			ActionErrors errors = new ActionErrors();
			String errorMess = ClwI18nUtil.getMessage(request,
					"error.functionalityNotImplemented");
			errors.add("error",
					new ActionError("error.simpleGenericError", errorMess));
			saveErrors(request, errors);
			returnValue = mapping.findForward(MAPPING_MODULE_ASSETS);
		} else {
			returnValue = notAuthorizedForFunctionality(mapping, request);
		}
		return returnValue;
	}

	/*
	 * Private method to show the services module.
	 */
	private ActionForward handleShowModuleServices(HttpServletRequest request,
			HttpServletResponse response, ModuleIntegrationForm form,
			ActionMapping mapping) {

		ActionForward returnValue = null;

		boolean authorizedForServices = ShopTool.getCurrentUser(request)
				.isAuthorizedForFunction(
						RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SERVICES);
		Utility.getSessionDataUtil(request).setSelectedMainTab(
				Constants.TAB_SERVICES);
		if (authorizedForServices) {
			// JEE TODO - determine how to integrate with Orca. For now return an
			// error
			ActionErrors errors = new ActionErrors();
			String errorMess = ClwI18nUtil.getMessage(request,
					"error.functionalityNotImplemented");
			errors.add("error",
					new ActionError("error.simpleGenericError", errorMess));
			saveErrors(request, errors);
			returnValue = mapping.findForward(MAPPING_MODULE_SERVICES);
		} else {
			returnValue = notAuthorizedForFunctionality(mapping, request);
		}
		return returnValue;
	}

	/*
	 * Private method to show the shopping module.
	 */
	private ActionForward handleShowModuleShopping(HttpServletRequest request,
			HttpServletResponse response, ModuleIntegrationForm form,
			ActionMapping mapping) {

		ActionForward returnValue = null;

		boolean authorizedForShopping = ShopTool.getCurrentUser(request)
				.isAuthorizedForFunction(
						RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SHOPPING);
		Utility.getSessionDataUtil(request).setSelectedMainTab(
				Constants.TAB_SHOPPING);
		if (authorizedForShopping) {

			// STJ-4642 - if the user is authorized to specify a UI preference and
			// they have made such
			// a specification, respect that
			CleanwiseUser user = ShopTool.getCurrentUser(request);
			boolean isAuthorizedForFunction = user
					.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.NEW_UI_ACCESS);
			if (isAuthorizedForFunction) {
				String userUIPreference = null;
				try {
					APIAccess factory = (APIAccess) request.getSession().getAttribute(
							Constants.APIACCESS);
					userUIPreference = factory.getPropertyServiceAPI().getUserProperty(
							user.getUserId(),
							RefCodeNames.PROPERTY_TYPE_CD.USER_UI_PREFERENCE);
				} catch (DataNotFoundException dnfe) {
					// nothing to do here - no user ui preference has been set
				} catch (Exception e) {
					log.error("Caught exception trying to determine user UI preference.");
				}
				if (Constants.PORTAL_CLASSIC.equals(userUIPreference)) {
					return mapping.findForward(MAPPING_TRADITIONAL_UI);
				}
			}

			// JEE TODO - to make this more generic we should forward to the
			// DashboardAction to determine
			// which of the 3 shopping related tabs (dashboard, products, or orders)
			// should be shown.
			// For now though we'll make that determination here.
			String previousTab = Utility.getSessionDataUtil(request)
					.getPreviousShoppingModuleTab();
			if (!Utility.isSet(previousTab)) {
				boolean authorizedForDashboard = user
						.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_DASHBOARD);
				if (authorizedForDashboard)
					previousTab = Constants.TAB_DASHBOARD;
				else
					previousTab = Constants.TAB_PRODUCTS;
			}
			if (Constants.TAB_ORDERS.equals(previousTab)) {
				returnValue = mapping.findForward(MAPPING_MODULE_SHOPPING_ORDERS);
			} else if (Constants.TAB_PRODUCTS.equals(previousTab)) {
				returnValue = mapping.findForward(MAPPING_MODULE_SHOPPING_PRODUCTS);
			} else {
				returnValue = mapping.findForward(MAPPING_MODULE_SHOPPING_DASHBOARD);
			}
		} else {
			returnValue = notAuthorizedForFunctionality(mapping, request);
		}
		return returnValue;
	}

	/*
	 * Private method to show the reporting module.
	 */
	private ActionForward handleShowModuleReporting(HttpServletRequest request,
			HttpServletResponse response, ModuleIntegrationForm form,
			ActionMapping mapping) {

		ActionForward returnValue = null;

		boolean authorizedForReporting = !ShopTool.getCurrentUser(request)
				.isNoReporting();
		Utility.getSessionDataUtil(request).setSelectedMainTab(
				Constants.TAB_REPORTING);
		if (authorizedForReporting) {
			returnValue = mapping.findForward(MAPPING_MODULE_REPORTING);
		} else {
			returnValue = notAuthorizedForFunctionality(mapping, request);
		}
		return returnValue;
	}

	/*
	 * Private method to show the documentation module.
	 */
	private ActionForward handleShowModuleDocumentation(
			HttpServletRequest request, HttpServletResponse response,
			ModuleIntegrationForm form, ActionMapping mapping) {

		ActionForward returnValue = null;

		Utility.getSessionDataUtil(request).setSelectedMainTab(
				Constants.TAB_DOCUMENTATION);
		ActionErrors errors = new ActionErrors();
		String errorMess = ClwI18nUtil.getMessage(request,
				"error.functionalityNotImplemented");
		errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		saveErrors(request, errors);
		returnValue = mapping.findForward(MAPPING_MODULE_DOCUMENTATION);
		return returnValue;
	}

	private ActionForward notAuthorizedForFunctionality(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		String errorMess = ClwI18nUtil.getMessage(request,
				"global.error.notAuthorized");
		errors.add("error", new ActionError("error.simpleGenericError", errorMess));
		saveErrors(request, errors);
		return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
	}

	/*
	 * Private method to determine what action forward should be returned after a
	 * show message request.
	 */
	private ActionForward handleShowMessageRequest(HttpServletRequest request,
			HttpServletResponse response, ModuleIntegrationForm form,
			ActionMapping mapping) {

		// if we cannot determine the message to show, return an error.
		StoreMessageView message = form.getCurrentMessage();
		if (message == null || message.getStoreMessageId() == 0) {
			String errorMess = ClwI18nUtil.getMessage(request,
					"message.error.noMessageSpecified", null);
			ActionErrors errors = new ActionErrors();
			errors.add("error",
					new ActionError("error.simpleGenericError", errorMess));
			saveErrors(request, errors);
			message = null;
		} else {
			CleanwiseUser appUser = ShopTool.getCurrentUser(request);
			int userId = appUser.getUserId();

			try {
				if (message.getStoreMessageId() > 0){
					message = MessageService.getMessageViewForUser(new Integer(userId),
							new Integer(message.getStoreMessageId()), true);
					// if the message was not valid, return an error.
					if (message == null || message.getStoreMessageId() == 0) {
						String errorMess = ClwI18nUtil.getMessage(request,
								"message.error.invalidMessageSpecified", null);
						ActionErrors errors = new ActionErrors();
						errors.add("error", new ActionError("error.simpleGenericError",
								errorMess));
						saveErrors(request, errors);
						message = null;
					} else {
						// if the message is expired return an error
						Calendar gCalendar = Calendar.getInstance();
						Date postedDate = null;
						if (message.getPostedDate() != null) {
							gCalendar.setTime(message.getPostedDate());
							gCalendar.set(Calendar.HOUR_OF_DAY, 0);
							gCalendar.set(Calendar.MINUTE, 0);
							gCalendar.set(Calendar.SECOND, 0);
							postedDate = gCalendar.getTime();
						}
						Date endDate = null;
						if (message.getEndDate() != null) {
							gCalendar.setTime(message.getEndDate());
							gCalendar.set(Calendar.HOUR_OF_DAY, 23);
							gCalendar.set(Calendar.MINUTE, 59);
							gCalendar.set(Calendar.SECOND, 59);
							endDate = gCalendar.getTime();
						}
						if ((postedDate != null && postedDate.after(new Date()))
								|| (endDate != null && endDate.before(new Date()))) {
							String errorMess = ClwI18nUtil.getMessage(request,
									"message.error.messageHasExpired", null);
							ActionErrors errors = new ActionErrors();
							errors.add("error", new ActionError("error.simpleGenericError",
									errorMess));
							saveErrors(request, errors);
							message = null;
						}
					}
				}
			} catch (Exception e) {
				log.error("Unable to retrieve user message: ", e);
				String errorMess = ClwI18nUtil.getMessage(request,
						"message.error.unableToRetrieveMessage", null);
				ActionErrors errors = new ActionErrors();
				errors.add("error", new ActionError("error.simpleGenericError",
						errorMess));
				saveErrors(request, errors);
				message = null;
			}
			if(message!=null && message.getStoreMessageId() > 0){
				boolean markMessageAsRead = (message.getMessageType().equals(
						RefCodeNames.MESSAGE_TYPE_CD.FORCE_READ) && appUser.getOriginalUser() == null);
				// if the message was valid and not expired, and should be marked as read,
				// mark it as read
				if (message != null && markMessageAsRead) {
					try {
						MessageService.markMessageAsReadByUser(new Integer(userId),
								new Integer(message.getStoreMessageId()));
					} catch (Exception e) {
						log.error("Unable to mark message as read: ", e);
						String errorMess = ClwI18nUtil.getMessage(request,
								"message.error.unableToMarkMessageRead", null);
						ActionErrors errors = new ActionErrors();
						errors.add("error", new ActionError("error.simpleGenericError",
								errorMess));
						saveErrors(request, errors);
					}
				}
			}
		}
		if(message!=null){
			form.setCurrentMessage(message);
		}
		return mapping.findForward(MAPPING_SHOW_MESSAGE);
	}

	private void markMessageAsReadByUser(StoreMessageView message,
			HttpServletRequest request) {
		// StoreMessageView message = form.getCurrentMessage();
		if (message != null && message.getStoreMessageId() > 0) {
			CleanwiseUser appUser = ShopTool.getCurrentUser(request);
			int userId = appUser.getUserId();
			boolean markMessageAsRead = (message.getMessageType().equals(
					RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED) && appUser
					.getOriginalUser() == null);
			if (message != null && markMessageAsRead) {
				try {
					MessageService.markMessageAsReadByUser(new Integer(userId),
							new Integer(message.getStoreMessageId()));
				} catch (Exception e) {
					log.error("Unable to mark message as read: ", e);
					String errorMess = ClwI18nUtil.getMessage(request,
							"message.error.unableToMarkMessageRead", null);
					ActionErrors errors = new ActionErrors();
					errors.add("error", new ActionError("error.simpleGenericError",
							errorMess));
					saveErrors(request, errors);
				}
			}

		}

	}

}
