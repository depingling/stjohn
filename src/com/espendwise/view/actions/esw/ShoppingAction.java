/**
 * Title: ShoppingAction
 * Description: This is the Struts Action class handling the ESW shopping functionality.
 */

package com.espendwise.view.actions.esw;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.util.PhysicalInventoryPeriod;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.EditOrderGuideLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.ShoppingForm;
import com.espendwise.view.logic.esw.ShoppingLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class ShoppingAction extends EswAction  {
    private static final Logger log = Logger.getLogger(ShoppingAction.class);
    
    //constants to hold the various action mappings that can be returned by this action class.
    private static final String MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG = "showCatalogProduct";
    private static final String MAPPING_VIEW_SHOPPING_CART = "viewShoppingCart";
    private static final String MAPPING_SHOPPING_SHOW_PRODUCT_DETAIL = "showProductDetail";
    private static final String MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL = "showMultiProductGroupDetail";
    private static final String MAPPING_SHOPPING_SHOW_SHOPPING_LISTS = "showShoppingLists";
    private static final String MAPPING_ORDERS_SHOW_CORPORATE_ORDER = "showCorporateOrder";
    private static final String MAPPING_ORDERS_SHOW_PHYSICAL_CART = "showPhysicalCart";
    private static final String MAPPING_VIEW_DASHBOARD = "showDashboard";
    private static final String MAPPING_SHOW_CHECK_OUT = "showCheckOut";

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
    	
		log.info("performAction()----new SessionTool(request).checkSession():" + new SessionTool(request).checkSession() );

    	// If there isn't a currently logged on user then go to the login page
		if (!new SessionTool(request).checkSession()) {
			return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
		}
		
		ActionForward returnValue = null;
		ShoppingForm theForm = (ShoppingForm) form;
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        
    	//determine what action to perform
        //If an operation has been specified use it.
		String operation = theForm.getOperation();
        //If no operation was specified but there is a previously executed operation use it.
    	if (!Utility.isSet(operation)) {
    		operation = sessionDataUtil.getPreviousShoppingAction();
    	}
    	
    	//If no operation was specified and there is no previous operation, default to 
    	//shopping lists
    	if (!Utility.isSet(operation)) {
    		SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
			UserShopForm userShopForm = sessionData.getUserShopForm();
			//STJ-5799 STJ-5333
			if (userShopForm != null && userShopForm.getCatalogMenu() != null && userShopForm.getCatalogMenu().getSubItems() != null
					&& userShopForm.getCatalogMenu().getSubItems().size() > 0) {
				operation = Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG;
			} else {
				operation = Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_LISTS;
			}
    	} 
		
		//trim whitespace if an operation has been specified
    	if (Utility.isSet(operation)) {
    		operation = operation.trim(); 
    	}
    	
    	if(!Utility.isSet(theForm.getPanelProductCatalog())){
    		theForm.setPanelProductCatalog(sessionDataUtil.getPanelProductCatalog());
    	}
    	if(!Utility.isSet(theForm.getPanelShoppingList())){
    		theForm.setPanelShoppingList(sessionDataUtil.getPanelShoppingList());
    	}
    	//now that we've determined what action to take, take it
    	boolean rememberOperation = true;
    	//Begin: Product Catalog
    	if (Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG.equalsIgnoreCase(operation)) {
            //set the active sub tab to be the products tab.
            sessionDataUtil.setSelectedSubTab(Constants.TAB_PRODUCTS);
            //set the last visited shopping tab to be the products tab.
            sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_PRODUCTS);
			returnValue = handleNavigateItems(request, response,theForm, mapping);
		}else if(Constants.PARAMETER_OPERATION_VALUE_MOVE_PANEL.equalsIgnoreCase(operation)){
			rememberOperation = false;
			returnValue = handleMovePanel(request, response, theForm, mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_EXCEL_PRINT.equalsIgnoreCase(operation)||
				Constants.PARAMETER_OPERATION_VALUE_PDF_PRINT.equalsIgnoreCase(operation)) {
			rememberOperation = false;
			returnValue = handleExportItemsToPdfOrExcel(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ADD_TO_CART.equalsIgnoreCase(operation) ) {
			rememberOperation = false;
			returnValue = handleAddToCart(request, response, theForm,mapping, MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ADD_TO_EXISTING_LIST.equalsIgnoreCase(operation)) {
			rememberOperation = false;
    		returnValue = handleAddItemsToExistingShoppingList(request, response, theForm, mapping, MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG);
    	}
		else if (Constants.PARAMETER_OPERATION_VALUE_CREATE_LIST.equalsIgnoreCase(operation)) {
			rememberOperation = false;
			if(theForm.isAddCatalogItems()){
				returnValue = handleNewShoppingListFromProductCatalog(request, response, theForm, mapping, MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG);
			} else {
				returnValue = handleNewShoppingListFromCart(request, response, theForm, mapping);
			}
    	}
		else if (Constants.PARAMETER_OPERATION_VALUE_SEARCH_PRODUCTS.equalsIgnoreCase(operation)) {
			rememberOperation = false;
			returnValue = handleSearchProductCatalogItem(request, response,theForm, mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ADD_CATALOG_ITEMS_TO_CORPORATE_ORDER.equalsIgnoreCase(operation)) {
			rememberOperation = false;
			returnValue = handleAddCatalogItemsToPAROrder(request, response,theForm, mapping, MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_GROUP_ITEMS.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleShowGroupItemRequest(request, response, theForm, mapping);
        }
		else if (Constants.PARAMETER_OPERATION_VALUE_CLEAR_QTY.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleClearQtyRequest(request, response, theForm, mapping, MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG);
        }
    	// Multi-Product Detail Actions
		else if (Constants.PARAMETER_OPERATION_VALUE_GROUP_ITEMS_CLEAR_QTY.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleClearQtyRequest(request, response, theForm, mapping, MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL);
        }
		else if (Constants.PARAMETER_OPERATION_VALUE_GROUP_ITEMS_ADD_TO_CART.equalsIgnoreCase(operation) ) {
			rememberOperation = false;
			returnValue = handleAddToCart(request, response, theForm,mapping, MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_GROUP_ITEMS_ADD_TO_EXISTING_LIST.equalsIgnoreCase(operation)) {
			rememberOperation = false;
    		returnValue = handleAddItemsToExistingShoppingList(request, response, theForm, mapping, MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL);
    	}
		else if (Constants.PARAMETER_OPERATION_VALUE_GROUP_ITEMS_CREATE_LIST.equalsIgnoreCase(operation)) {
			rememberOperation = false;
			returnValue = handleNewShoppingListFromProductCatalog(request, response, theForm, mapping, MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_GROUP_ITEMS_ADD_CATALOG_ITEMS_TO_CORPORATE_ORDER.equalsIgnoreCase(operation)) {
			rememberOperation = false;
			returnValue = handleAddCatalogItemsToPAROrder(request, response,theForm, mapping, MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL);
		}

    	//End: Product Catalog
		
		//Begin: Shopping Cart
		else if(Constants.PARAMETER_OPERATION_VALUE_VIEW_CART.equalsIgnoreCase(operation)) {
			//set the Shopping tab to be the active main tab (this must be done here
			//because this action is available from the header and therefore won't
			//pass through any of the actions that typically take care of setting the
			//selection of the main tab
			sessionDataUtil.setSelectedMainTab(Constants.TAB_SHOPPING);
            //set the active sub tab to be empty.
            sessionDataUtil.setSelectedSubTab(Constants.EMPTY);
        	rememberOperation = false;
    		returnValue = handleViewCartRequest(request, response, theForm, mapping);
    	} 
    	else if(Constants.PARAMETER_OPERATION_VALUE_REMOVE_ITEM_FROM_CART.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
    		returnValue = handleRemoveCartItemRequest(request, response, theForm, mapping);
    	}
    	else if(Constants.PARAMETER_OPERATION_VALUE_REMOVE_ALL_FROM_CART.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
    		returnValue = handleRemoveAllCartRequest(request, response, theForm, mapping);
    	} 
    	else if (Constants.PARAMETER_OPERATION_VALUE_SAVE_CART.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
    		returnValue = handleSaveCartRequest(request, response, theForm, mapping);
    	} 
    	else if (Constants.PARAMETER_OPERATION_VALUE_ADD_ALL_TO_SHOPPING_LIST.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
    		returnValue = handleAddAllToShoppingList(request, response, theForm, mapping);
    	}
    	else if (Constants.PARAMETER_OPERATION_VALUE_CHECK_OUT.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
    		returnValue = handleCheckOutRequest(request, response, theForm, mapping);
    	}
		//End: Shopping Cart
		
		//Begin: PAR (Corporate) Orders
    	else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER.equalsIgnoreCase(operation)) {
        	rememberOperation = false;    
    		returnValue = handleShowParOrderRequest(request, response, theForm, mapping);
    	}
    	else if (Constants.PARAMETER_OPERATION_VALUE_SAVE_CORPORATE_ORDER.equalsIgnoreCase(operation)){
    		returnValue = handleSaveParOrderRequest(request, response, theForm, mapping);
    	}
		//End: PAR (Corporate) Orders
    	
    	//Begin: Physical Cart
        else if (Constants.PARAMETER_OPERATION_VALUE_SHOW_PHYSICAL_CART.equalsIgnoreCase(operation)) {
            rememberOperation = false;    
            returnValue = handleShowPhysicalCartRequest(request, response, theForm, mapping);
        }
        else if (Constants.PARAMETER_OPERATION_VALUE_SAVE_PHYSICAL_CART.equalsIgnoreCase(operation)){
            returnValue = handleSavePhysicalCartRequest(request, response, theForm, mapping);
        }
        //End: PAR (Corporate) Orders
		
		//Begin: Product Detail
    	else if (Constants.PARAMETER_OPERATION_VALUE_ITEM_TO_CART.equalsIgnoreCase(operation)) {
        	rememberOperation = false;
            returnValue = handleItemToCartRequest(request, response, theForm,mapping);
        }
		else if (Constants.PARAMETER_OPERATION_VALUE_ITEMS.equalsIgnoreCase(operation)) {
			//set the Shopping tab to be the active main tab (this must be done here
			//because this action is available from the header and therefore won't
			//pass through any of the actions that typically take care of setting the
			//selection of the main tab
			sessionDataUtil.setSelectedMainTab(Constants.TAB_SHOPPING);
            //set the active sub tab to be products.
            sessionDataUtil.setSelectedSubTab(Constants.TAB_PRODUCTS);
        	rememberOperation = false;
            returnValue = handleShowItemRequest(request, response, theForm,mapping);
        }
		else if (Constants.PARAMETER_OPERATION_VALUE_ITEM_TO_CORPORATE_ORDER.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleItemToParOrderRequest(request, response, theForm, mapping);
        }
		else if (Constants.PARAMETER_OPERATION_VALUE_ITEM_DOCUMENT_FROM_E3_STORAGE.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleShowItemDocumentFromE3Storage(request, response, theForm, mapping);
        }
		else if (Constants.PARAMETER_OPERATION_VALUE_ITEM_DOCUMENT_FROM_DB.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handleShowItemDocumentFromDb(request, response, theForm, mapping);
        }
		//End: Product Detail
		//Start: Shopping List
		else if (Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_LISTS.equalsIgnoreCase(operation)) {
	        //set the active sub tab to be the products tab.
	        sessionDataUtil.setSelectedSubTab(Constants.TAB_PRODUCTS);
	        //set the last visited shopping tab to be the products tab.
	        sessionDataUtil.setPreviousShoppingModuleTab(Constants.TAB_PRODUCTS);
			returnValue = handleShopByListsRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ORDER_GUIDE_SELECT.equalsIgnoreCase(operation)) {
			rememberOperation = false;
			returnValue = handleSelectedShoppingListRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_DELETE_GUIDE.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleDeleteShoppingListRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_RENAME_ORDER_GUIDE.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleRenameShoppingListRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_REMOVE_SELECTED_ITEMS.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleRemoveSelectedShoppingListItemsRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ADD_TO_CART_FROM_USER_ORDER_GUIDE.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleAddShoppingListItemsToCartRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_SEARCH_SHOPPING_LSIT_PRODUCTS.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
		    returnValue = handleSearchForShoppingListItem(request, response, theForm,mapping);
			
		    /*if ("".equals(Utility.safeTrim(Utility.strNN(theForm.getProductSearchValue())))) {
		        returnValue = handleShopByListsRequest(request, response, theForm,mapping);
		    } else {
		        returnValue = handleSearchForShoppingListItem(request, response, theForm,mapping);
		    }*/
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_UPDATE_QUANTITY.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleUpdateQuantityRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_SAVE_USER_ORDER_GUIDE.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleCreateShoppingListRequest(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ADD_SHOPPING_LIST_ITEMS_TO_CORPORATE_ORDER.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleAddShoppingListItemsToParOrder(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_EXCEL_PRINT_PERS.equalsIgnoreCase(operation)) {
            rememberOperation = false;
            returnValue = handlePrintOrExportShoppingListItems(request, response,theForm,mapping);
     }

		//End: Shopping List
    	//Start: Quick Shop Form
		else if (Constants.PARAMETER_OPERATION_VALUE_QUICK_SHOP.equalsIgnoreCase(operation)) {
			//STJ-5333
			 theForm.setShowQuickShop(true);
	    	 returnValue = handleViewCartRequest(request, response, theForm, mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ADD_QUICK_SHOP_ITEMS_TO_CART.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			 theForm.setShowQuickShop(true);
			returnValue = handleAddQuickShopItemsToCart(request, response, theForm,mapping);
		}
		else if (Constants.PARAMETER_OPERATION_VALUE_ADD_QUICK_SHOP_RESOLVE_SKU.equalsIgnoreCase(operation)) {
			 rememberOperation = false;
			returnValue = handleResolveQuickShopSkuCollisions(request, response, theForm,mapping);
		}
    	//End: Quick Shop Form
    	else{
        	rememberOperation = false;
			returnValue = handleUnknownOperation(request, response, theForm,mapping);
		}
        
    	if(rememberOperation) {
    		sessionDataUtil.setPreviousShoppingAction(operation);
    	}
    	
		return returnValue;
	}
    
    private ActionForward handleMovePanel(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	
    	ActionForward returnValue = null;
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		String panelProductCatalog = shoppingForm.getPanelProductCatalog();
    	String panelShoppingList = shoppingForm.getPanelShoppingList();
    	
    	if(!Utility.isSet(panelProductCatalog)){
    		panelProductCatalog = Constants.EXPAND_PANEL;
    	}
    	if(!Utility.isSet(panelShoppingList)){
    		panelShoppingList = Constants.EXPAND_PANEL;
    	}
    	
		StringBuffer responseJson = new StringBuffer();
		responseJson.append("{");
		responseJson.append("\"movePanel\":");
		responseJson.append("[");
		responseJson.append("{\""+Constants.PANEL_PRODUCT_CATALOG+"\":");
		responseJson.append("\""+panelProductCatalog+"\"},");
		responseJson.append("{\""+Constants.PANEL_SHOPPING_LIST+"\":");
		responseJson.append("\""+panelShoppingList+"\"}");
		responseJson.append("]}");
		
		sessionDataUtil.setPanelProductCatalog(panelProductCatalog);
		sessionDataUtil.setPanelShoppingList(panelShoppingList);
		shoppingForm.setPanelProductCatalog(panelProductCatalog);
		shoppingForm.setPanelShoppingList(panelShoppingList);
		
    	try {
    		response.setContentType(Constants.CONTENT_TYPE_JSON);
    		response.setHeader("Cache-Control", "no-cache");
    		response.setCharacterEncoding(Constants.UTF_8);
    		response.getWriter().write(responseJson.toString());
    	}
    	catch (Exception e) {
            ActionErrors errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            saveErrors(request, errors);
        	return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);    
    	}
    	
		return returnValue;
    }
    
    /*
     * Private method to determine what action forward should be returned after navigating to a specific category.
     */
    private ActionForward handleNavigateItems(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null;
    	ActionForward returnValue = null;
    	//Populate the common data
    	populateCommonData(request, shoppingForm);
		try {
			//STJ-5666: Reset product search field value.
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			sessionDataUtil.setProductSearchField(null);
			sessionDataUtil.setProductSearchValue(null);
			shoppingForm.setProductSearchField(null);
			shoppingForm.setProductSearchValue(null);
			
			errors = EditOrderGuideLogic.init(request, shoppingForm.getEditOrderGuideForm());
			if (errors!=null && errors.size() > 0) {
                saveErrors(request, errors);
            }
			
			Integer allProductsCount = sessionDataUtil.getAllProductsCount();
			if (allProductsCount != null && allProductsCount == 0){
				ActionMessages messages = new ActionMessages();
	        	String message = ClwI18nUtil.getMessage(request,"shoppingCatalog.noItems", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
			}else{			
				errors = ShoppingLogic.navigateCatalogProducts(request, shoppingForm);
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	            }
			}
			returnValue = mapping.findForward(MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG);
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleNavigateItems: "+e);
			returnValue = saveErrors(request,mapping,errors); 
		}
    	return returnValue;    
    }
    
    
    /*
     * Private method to determine what action forward should be returned after exporting catalog items.
     */
    private ActionForward handleExportItemsToPdfOrExcel(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null;
    	ActionForward returnValue = null;
    	//Populate the common data
		try {
			String action =  request.getParameter(Constants.PARAMETER_OPERATION);
			MessageResources mResources = getResources(request);
			errors = ShoppingLogic.printDetail(response, request,shoppingForm, action, mResources);
			if (errors!=null && errors.size() > 0) {
                saveErrors(request, errors);
            }
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleExportItemsToPdfOrExcel: "+e);
			returnValue = saveErrors(request,mapping,errors);  
		}
		
    	return returnValue;    
    }
    /*
     * Private method to determine what action forward should be returned after adding catalog items to cart.
     */
    private ActionForward handleAddToCart(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    		return handleAddToCart(request, response, shoppingForm, mapping,MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG );
	}
	private ActionForward handleAddToCart(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping, String retAction) {
    	ActionErrors errors = null;
    	
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			//boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
			CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			if(appUser.isBrowseOnly()){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
	        	return mapping.findForward(retAction);    
			}

	    	errors = ShoppingLogic.addCatalogItemsToCart(request,shoppingForm); 
			if (errors != null && errors.size() > 0) {
                saveErrors(request, errors);
            } else {
            	//Save the shopping cart messages and confirmation messages, if any
    			String confMsg = shoppingForm.getUserShopForm().getConfirmMessage();
    			saveMessages(request,confMsg,true);
            }
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleAddToCart: ", e);
            return saveErrors(request, mapping, errors);
		}
		return mapping.findForward(retAction);    
    }
    
    /*
     * Private method to determine what action forward should be returned after a view shopping cart request.
     */
    private ActionForward handleViewCartRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
    	ActionErrors errors;
    	//STJ-5815.
    	//if no there is no location set, return appropriate error message.
    	CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		SiteData location = user.getSite();
		if(location == null){
			errors = new ActionErrors();
			String errorMess = ClwI18nUtil.getMessage(request,"error.noLocationSelected",null);
            errors.add("error",new ActionError("error.simpleGenericError",errorMess));
            saveErrors(request, errors);
            return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
		}
		errors = ShoppingLogic.viewShoppingCart(request, form);
		
		if(errors!=null && errors.size()>0) {
			saveErrors(request, errors);
		} else {
			ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
			
			SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
			form.setExcludeOrderFromBudget(sessionData.isExcludeOrderFromBudget());
			
	    	//if no items were found, return an informational message to the user.
			if (errors.isEmpty() && !Utility.isSet(shoppingCartForm.getCartItems())) {
	        	ActionMessages messages = new ActionMessages();
	        	String message = ClwI18nUtil.getMessage(request,"shoppingCart.message.noItems", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
			}else{
				errors = EditOrderGuideLogic.init(request, form.getEditOrderGuideForm());
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	            }
				populateShoppingListOptions(request,form);
			}
			
		}
		
		//Update the Location budget chart
		errors = ShoppingLogic.updateLocationBudgetChart(request);
		if(errors!=null && errors.size()>0) {
			saveErrors(request, errors);
		}
		//Some times(Ex: When quantity is entered as '0'), The cart is not getting updated.
		//Update the Shopping Cart with the latest data from the session. 
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
		shoppingCartForm.setShoppingCart(shoppingCartForm.getShoppingCart(request));
		
    	//No need to select any tab for shopping cart view.
		Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
		
		return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
	}
	
    /*
     * Private method to determine what action forward should be returned after remove item from shopping cart request.
     */
    private ActionForward handleRemoveCartItemRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
		
		ActionErrors errors;
		ActionForward returnValue;
		
		boolean inventoryShopping = ShopTool.hasInventoryCartAccessOpen(request);
		// Checking browse-only status
        CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
        if (clwUser.isBrowseOnly()) {
            errors = new ActionErrors();
            String message = ClwI18nUtil.getMessage(request,
                    "orders.error.browseOnlyCannotModifyOrder", null);
            errors.add("error", new ActionMessage("error.simpleGenericError",
                    message));
            saveErrors(request, errors);
            if (inventoryShopping) {
                setShoppingCartForm(request,form);
                return mapping.findForward(MAPPING_ORDERS_SHOW_CORPORATE_ORDER);
            } else {
                handleViewCartRequest(request, response, form, mapping);
                return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
            }
        }

		if(inventoryShopping) {
			
			returnValue = mapping.findForward(MAPPING_ORDERS_SHOW_CORPORATE_ORDER);
			errors = ShoppingLogic.removeInvItemFromCart(request, form);
			setShoppingCartForm(request,form);
		} else {
			
			returnValue = mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
			
			errors = ShoppingLogic.removeItemFromCart(request, form);
			
			if(errors==null || errors.size()==0 ) {
				//Need to call the view schedule cart method to update
				//the PAR Activity history related stuff and 
				//Location budget related stuff.
				handleViewCartRequest(request, response, form, mapping);
			}
		}
				
		if(errors!=null && errors.size()>0) {
			saveErrors(request, errors);
		} else {
			ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
	    	
	    	//if no items were found, return an informational message to the user.
			if(errors.isEmpty()) {
				if ( !Utility.isSet(shoppingCartForm.getCartItems())) {
					ActionMessages messages = new ActionMessages();
		        	String message = ClwI18nUtil.getMessage(request,"shoppingCart.message.noItems", null);
		        	messages.add("message", new ActionMessage("message.simpleMessage", message));
		        	saveMessages(request, messages);
				} else {
					ActionMessages messages = new ActionMessages();
		        	String message = ClwI18nUtil.getMessage(request,"shoppingCart.text.itemDeleted", null);
		        	messages.add("message", new ActionMessage("message.simpleMessage", message));
		        	saveMessages(request, messages);
				}
			}
		}
		
		return returnValue;
	} 
	
    /*
     * Private method to determine what action forward should be returned after remove all items from shopping cart request.
     */
	private ActionForward handleRemoveAllCartRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
        ActionErrors errors = null;
        // Checking browse-only status
        CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
        if (clwUser.isBrowseOnly()) {
            errors = new ActionErrors();
            String message = ClwI18nUtil.getMessage(request,
                    "orders.error.browseOnlyCannotModifyOrder", null);
            errors.add("error", new ActionMessage("error.simpleGenericError",
                    message));
        } else {
            errors = ShoppingLogic.removeAllItemsFromCart(request, form);
        }
		
		//Update the Location budget chart
		if(errors==null || errors.isEmpty()) {
			errors = ShoppingLogic.updateLocationBudgetChart(request);
		}
		
		if(errors!=null && errors.size()>0) {
			saveErrors(request, errors);
		} else {
			ActionMessages messages = new ActionMessages();
	    	String message = ClwI18nUtil.getMessage(request,"shoppingCart.message.noItems", null);
	    	messages.add("message", new ActionMessage("message.simpleMessage", message));
	    	saveMessages(request, messages);
		}
		
		//Need to call the view schedule cart method to update
		//the PAR Activity history related stuff, if inventory shopping is set
		boolean inventoryShopping  = ShopTool.isInventoryShoppingOn(request);
		if(inventoryShopping) {
			handleViewCartRequest(request, response, form, mapping);
		}  else {
			populateShoppingListOptions(request,form);
			//No need to select any tab for shopping cart view.
			Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
		}
		return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
	} 
	
	/*
     * Private method to determine what action forward should be returned after save shopping cart request.
     */
	private ActionForward handleSaveCartRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
		populateShoppingListOptions(request,form);
		
		//Determine whether save is really needed or not.
		//Items should be saved when quantities are changed, Otherwise do nothing.
		boolean isQtyChanged = ShoppingLogic.areItemQtysUpdated(form.getShoppingCartForm());
		
		ActionErrors errors = null;			
		boolean isExcludeOrderUpdated = false;
		CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
        if (clwUser.isBrowseOnly()) {
            errors = new ActionErrors();
            String message = ClwI18nUtil.getMessage(request,
                    "orders.error.browseOnlyCannotModifyOrder", null);
            errors.add("error", new ActionMessage("error.simpleGenericError",
                    message));
            saveErrors(request, errors);
            return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
        }
        if(clwUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET)) {
            isExcludeOrderUpdated = ShoppingLogic.isExcludeOrderFromBudgetUpdated(request, form);
        }
		
		if(isQtyChanged || isExcludeOrderUpdated) {
			
			if(isExcludeOrderUpdated) {
				errors = ShoppingLogic.updateOrderGuide(request, form);
			}
			
			if(isQtyChanged) {
				errors = ShoppingLogic.reCalculateAndSaveShoppingCart(request, form);	
				//Save the shopping cart messages and confirmation messages, if any
				String confMsg = form.getShoppingCartForm().getConfirmMessage();
				saveMessages(request,confMsg,true);
			}
			
			//Update the Location budget chart
			if(errors==null || errors.isEmpty()) {
				errors = ShoppingLogic.updateLocationBudgetChart(request);
			}
			
			if(errors!=null && errors.size()>0) {
				saveErrors(request, errors);
				
			} else {
				//the PAR Activity history related stuff, if inventory shopping is set
				boolean inventoryShopping  = ShopTool.isInventoryShoppingOn(request);
				if(inventoryShopping) {
					handleViewCartRequest(request, response, form, mapping);
				} 
			}
		}
		//No need to select any tab for shopping cart view.
		Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
		
		return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
	}
	
	private ActionForward handleShowGroupItemRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
	    ActionErrors errors = new ActionErrors();
        //Populate the common data
		populateCommonData(request, form);

		try {
	        errors = ShoppingLogic.showGroupItemDetails(request, form);		
			if(errors!=null && errors.size()>0) {
				saveErrors(request, errors);
			}
		 } catch (Exception e) {
	            log.error("Unexpected exception in ShoppingAction.handleShowGroupItem: ",e);
	            
	            String errorMess = ClwI18nUtil.getMessage(request,"error.unExpectedError", null);
	            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
	            saveErrors(request, errors);
	            
	            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
	        }
        return mapping.findForward(MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL);
 
	}
	
	private ActionForward handleClearQtyRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping, String retAction) {
		
	    ActionErrors errors = new ActionErrors();
        //Populate the common data
		populateCommonData(request, form);

		try {
	        errors = ShoppingLogic.clearQty(request, form);		
			if(errors!=null && errors.size()>0) {
				saveErrors(request, errors);
			}
			request.setAttribute("gotoAnchor","true");
		 } catch (Exception e) {
	            log.error("Unexpected exception in ShoppingAction.handleClearQty: ",e);
	            
	            String errorMess = ClwI18nUtil.getMessage(request,"error.unExpectedError", null);
	            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
	            saveErrors(request, errors);
	            
	            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
	     }
		return mapping.findForward(retAction);
        //return mapping.findForward(MAPPING_SHOPPING_SHOW_MULTI_PRODUCT_DETAIL);
	}

	private ActionForward handleShowItemRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
	    ActionErrors errors = new ActionErrors();
        //Populate the common data
        populateCommonData(request, form);
		try {
	        errors = ShoppingLogic.showItemDetails(request, form);		
			if(errors!=null && errors.size()>0) {
				saveErrors(request, errors);
			}
		 } catch (Exception e) {
	            log.error("Unexpected exception in ShoppingAction.handleShowItem: ",e);
	            
	            String errorMess = ClwI18nUtil.getMessage(request,"error.unExpectedError", null);
	            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
	            saveErrors(request, errors);
	            
	            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
	        }
        return mapping.findForward(MAPPING_SHOPPING_SHOW_PRODUCT_DETAIL);
    }
	
	private ActionForward handleShowItemDocumentFromE3Storage(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
	    ActionErrors errors = new ActionErrors();
        //Populate the common data
        populateCommonData(request, form);
        UserShopForm userShopForm = form.getUserShopForm();
		try {
	        errors = ShoppingLogic.showItemDocumentFromE3Storage(request, userShopForm, response);		
			if(errors!=null && errors.size()>0) {
				saveErrors(request, errors);
			}
		 } catch (Exception e) {
	            log.error("Unexpected exception in ShoppingAction.handleShowItemDocumentFromE3Storage: ",e);
	            
	            String errorMess = ClwI18nUtil.getMessage(request,"error.unExpectedError", null);
	            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
	            saveErrors(request, errors);
	            
	            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
	        }
		return null;
        
    }
	
	private ActionForward handleShowItemDocumentFromDb(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
	    ActionErrors errors = new ActionErrors();
        //Populate the common data
        populateCommonData(request, form);
        UserShopForm userShopForm = form.getUserShopForm();
		try {
	        errors = ShoppingLogic.showItemDocumentFromDb(request, userShopForm, response);		
			if(errors!=null && errors.size()>0) {
				saveErrors(request, errors);
	            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR_NO_HEADER_NO_FOOTER);
			}
		 } catch (Exception e) {
	            log.error("Unexpected exception in ShoppingAction.handleShowItemDocumentFromDb: ",e);
	            
	            String errorMess = ClwI18nUtil.getMessage(request,"error.unExpectedError", null);
	            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
	            saveErrors(request, errors);
	            
	            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR_NO_HEADER_NO_FOOTER);
	        }
		return null;
        
    }
	
	private ActionForward handleItemToCartRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
        ActionErrors errors = new ActionErrors();
        //Populate the common data
        populateCommonData(request, form);
        try {
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            if(appUser.isBrowseOnly()){
                ActionMessages messages = new ActionMessages();
                String message = ClwI18nUtil.getMessage(request,"orders.error.browseOnlyCannotModifyOrder", null);
                messages.add("message", new ActionMessage("message.simpleMessage", message));
                saveMessages(request, messages);
                return mapping.findForward(MAPPING_SHOPPING_SHOW_PRODUCT_DETAIL);    
            }
            errors = ShoppingLogic.itemToCart(request, form);
            if (errors.size() > 0) {
                saveErrors(request, errors);
            }else{
            	String confMsg = form.getUserShopForm().getConfirmMessage();
    			saveMessages(request,confMsg,true);
            }
        } catch (Exception e) {
            log.error("Unexpected exception in ShoppingAction.handleItemToCart: ",e);
            
            String errorMess = ClwI18nUtil.getMessage(request,"error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
            saveErrors(request, errors);
            
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_ERROR);
        }
        return mapping.findForward(MAPPING_SHOPPING_SHOW_PRODUCT_DETAIL);
    }

    private ActionForward handleItemToParOrderRequest(
            HttpServletRequest request, HttpServletResponse response,
            ShoppingForm form, ActionMapping mapping) {
        ActionErrors errors = null;
        try {
            populateCommonData(request, form);
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
			if(appUser.isBrowseOnly()){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
                return mapping
                        .findForward(MAPPING_SHOPPING_SHOW_PRODUCT_DETAIL);
            }
            errors = ShoppingLogic.itemToParOrder(request, form);
            if (errors.size() > 0) {
                saveErrors(request, errors);
            }else {
	            	//Save the success message if items are added to PAR order.
            	ActionMessages messages = new ActionMessages();
            	String message = ClwI18nUtil.getMessage(request,"shoppingList.itemsAddedToCorporateOrder", null);
            	messages.add("message", new ActionMessage("message.simpleMessage", message));
            	saveMessages(request, messages);
            }
        } catch (Exception e) {
            log.error("Unexpected exception in ShoppingAction.handleItemToParOrderRequest: ",
                            e);
            return saveErrors(request, mapping, errors);
        }
        return mapping.findForward(MAPPING_SHOPPING_SHOW_PRODUCT_DETAIL);
    }

	/*
     * Private method to determine what action forward should be returned after a view scheduled shopping cart request.
     */
    private ActionForward handleShowParOrderRequest(HttpServletRequest request, HttpServletResponse response,
			ShoppingForm form, ActionMapping mapping) {
    	
    	ActionErrors errors = new ActionErrors();
    	ActionForward returnValue;
    	try {
    		
    		SiteData currentLocation = ShopTool.getCurrentSite(request);
        	//if the user has not yet selected a location, return an error
            if (currentLocation == null) {
                String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected");
                errors.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
                saveErrors(request, errors);
            } else {

    			errors = ShoppingLogic.obtainParOrder(request, form);
    			
    			if(errors!=null && errors.size()>0) {
    				saveErrors(request, errors);
    			} else {
    				//Set ShoppingCartForm from session to ShoppingForm.
    				setShoppingCartForm(request,form);
        			
        	    	//if no items were found, return an informational message to the user.
        			if (errors.isEmpty() && !Utility.isSet(form.getShoppingCartForm().getCartItems())) {
        				ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
        				if(messages==null) {
        					messages = new ActionMessages();
        				}
        	        	String message = ClwI18nUtil.getMessage(request,"shoppingCart.message.noItems", null);
        	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
        	        	saveMessages(request, messages);
        			}
    			}
    			
            }          
            returnValue = mapping.findForward(MAPPING_ORDERS_SHOW_CORPORATE_ORDER);
    	} catch (Exception e) {
    		log.error("Unexpected exception in ShoppingAction.handleShowParOrderRequest: "+e);
    		returnValue = saveErrors(request,mapping,errors);  
		} finally {
			Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
		}
		
		return returnValue;
	}
    
    /*
     * Private method to determine what action forward should be returned after getting order guide items.
     */
    private ActionForward handleSaveParOrderRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null ;
    	ActionMessages messages = null;
    	ActionForward returnValue;
		
		try {
            CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
            if (clwUser.isBrowseOnly()) {
                errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,
                        "orders.error.browseOnlyCannotUpdateCorporateOrder",
                        null);
                errors.add("error", new ActionMessage(
                        "error.simpleGenericError", message));
                saveErrors(request, errors);
                return mapping.findForward(MAPPING_ORDERS_SHOW_CORPORATE_ORDER);
            }
			//Validate Quantities entered.
			errors = ShoppingLogic.validateQuantities(request, shoppingForm.getShoppingCartForm(),true);
			if(errors==null || errors.isEmpty()) {
				messages = ShoppingLogic.getPAROrderInfoMessages(request, shoppingForm.getShoppingCartForm());
				if(messages!=null && !messages.isEmpty()) {
					saveMessages(request, messages);
				}
				
				errors = ShoppingLogic.saveParOrder(request,shoppingForm);
				
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	            } else {
	            	//Need to call show PAR Orders request to get latest data for PAR Activity
	            	handleShowParOrderRequest(request,response,shoppingForm,mapping);
	            	
	            	errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY); 

	            	//If there are no errors, print success message.
	            	if(errors==null || errors.isEmpty()) {
	            		if(messages==null) {
	    					messages = new ActionMessages();
	    				}
	    	        	String message = ClwI18nUtil.getMessage(request,"shoppingCart.info.message.corporateOrderSave", null);
	    	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	    	        	saveMessages(request, messages);
	            	} 
	            	
	            	//Save Info messages, if there are any.
	            	messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
		        	if(messages!=null && !messages.isEmpty()) {
						saveMessages(request, messages);
					}
	            }
			} else {
				saveErrors(request, errors);
			}
				
			returnValue = mapping.findForward(MAPPING_ORDERS_SHOW_CORPORATE_ORDER);
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleSaveParOrderRequest: "+e);
			returnValue = saveErrors(request,mapping,errors); 
		} finally {
			Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
		}
		return returnValue;   
    }
    
    /**
     * Sets the Inventory ShoppingCartForm to the ShoppingForm after getting it from the session.
     * @param request
     * @param form
     */
    private void setShoppingCartForm(HttpServletRequest request,ShoppingForm form) {
    	
    	//Shopping Cart form should be fetched from the session.
		//It needs to be set in  SessionDataUtil class.
		ShoppingCartForm shoppingCartForm = (ShoppingCartForm)request.getSession().
													getAttribute(Constants.INVENTORY_SHOPPING_CART_FORM);
		
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		if(shoppingCartForm==null) {
			shoppingCartForm = form.getShoppingCartForm();
		} else {
			//Set the same shoppingCartForm in the ShoppingForm
			form.setShoppingCartForm(shoppingCartForm);
		}
		
		//Shopping cart items.
		sessionDataUtil.setShoppingCartForm(shoppingCartForm);
    }
    
    /*
     * Private method to gather data commonly needed on the Orders page.
     */
    private void populateCommonData(HttpServletRequest request, ShoppingForm form) {
    	form.setProductSearchFieldChoices(ClwI18nUtil.getProductSearchFieldChoices(request));
    }
    
    /*
     * Private method to determine what action forward should be returned after getting order guide items.
     */
    private ActionForward handleSelectedShoppingListRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null;
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			if(sessionDataUtil.isActionProductSearch()){
				sessionDataUtil.setActionProductSearch(false);
			}
			//STJ-5666: Reset product search field value.
			sessionDataUtil.setProductSearchField(null);
			sessionDataUtil.setProductSearchValue(null);
			shoppingForm.setProductSearchField(null);
			shoppingForm.setProductSearchValue(null);
			
			errors = ShoppingLogic.getSelectedShoppingListItems(request,shoppingForm);
			if (errors != null && errors.size() > 0) {
                saveErrors(request, errors);
            }

			if (errors.isEmpty() &&
			     (shoppingForm.getEditOrderGuideForm().getItems()==null ||
			      shoppingForm.getEditOrderGuideForm().getItems().size() == 0) ) {
	        	ActionMessages messages = new ActionMessages();
	        	String message = ClwI18nUtil.getMessage(request,"shoppingList.noItems", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
			}
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleSelectedShoppingListRequest: ", e);
			return saveErrors(request, mapping, errors);
		}
		return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
    }
    /*
     * Private method to determine what action forward should be returned after initializing shopping lists.
     */
    private ActionForward handleShopByListsRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null;
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			if(sessionDataUtil.isActionProductSearch()){
				sessionDataUtil.setActionProductSearch(false);
			}
			errors = ShoppingLogic.shopByLists(request,shoppingForm);
			if (errors!=null && errors.size() > 0) {
                saveErrors(request, errors);
            } else {
            	//STJ-5774: Set Order guides into session for Shopping List sub-menu
				UserShopForm userShopForm = shoppingForm.getUserShopForm();
				if(userShopForm==null) {
					userShopForm = new UserShopForm();
				}
				EditOrderGuideForm editOrderGuideForm = shoppingForm.getEditOrderGuideForm();
				if(editOrderGuideForm==null) {
					editOrderGuideForm = new EditOrderGuideForm();
				}
				sessionDataUtil.setUserShopForm(userShopForm);
				sessionDataUtil.setEditOrderGuideForm(editOrderGuideForm);
				sessionDataUtil.setSelectedSubTab(Constants.TAB_PRODUCTS);
            }

			if (errors.isEmpty() &&
			    (shoppingForm.getEditOrderGuideForm().getUserOrderGuides() == null ||
				  shoppingForm.getEditOrderGuideForm().getUserOrderGuides().size() == 0) &&
				 (shoppingForm.getUserShopForm().getTemplateOrderGuides() == null ||
				  shoppingForm.getUserShopForm().getTemplateOrderGuides().size() == 0)) {
	        	ActionMessages messages = new ActionMessages();
	        	String message = ClwI18nUtil.getMessage(request,"shoppingList.noShoppingLists", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
			} else {
			    if (shoppingForm.getEditOrderGuideForm().getItems() == null ||
					 shoppingForm.getEditOrderGuideForm().getItems().size() == 0) {
                    ActionMessages messages = new ActionMessages();
                    String message = ClwI18nUtil.getMessage(request,"shoppingList.noItems", null);
                    messages.add("message", new ActionMessage("message.simpleMessage", message));
                    saveMessages(request, messages);
                }
            }
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleShopByListsRequest: ", e);
			return saveErrors(request, mapping, errors);
		}
		return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
    }
    
    /*
     * Private method to determine what action forward should be returned after deleting an order guide.
     */
    private ActionForward handleDeleteShoppingListRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null;
    	ActionMessages messages = null;
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
			if(isUserCanOnlyBrowse){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS); 
			}
			int deleteId = 0;
			if (shoppingForm.getEditOrderGuideForm() != null) {
				deleteId = shoppingForm.getEditOrderGuideForm().getInputOrderGuideId();
			}
			errors = ShoppingLogic.deleteShoppingList(request,shoppingForm);
			if (errors!=null && errors.size() > 0) {
                saveErrors(request, errors);
            }
			else {
				//STJ-5774: Refresh Shopping List sub-menu after deleting shopping list.
  				//ShoppingLogic.refreshShoppingLists(request);
  				
            	//Save the success message if shopping list is deleted.
				messages = new ActionMessages();
				String message = ClwI18nUtil.getMessage(request,"shoppingList.deleted", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
	        	//STJ-4632 - start with a new order guide form and clear out the
	        	//session data remembered shopping list if appropriate.
	        	EditOrderGuideForm newForm = new EditOrderGuideForm();
	        	shoppingForm.setEditOrderGuideForm(newForm);
	        	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
	        	sessionData.setEditOrderGuideForm(newForm);
	    		if(deleteId > 0 && deleteId == sessionData.getRememberShoppingList()) {
	    			sessionData.setRememberShoppingList(0);
	    		}
            }
			// Need to refresh the user shopping lists after deleting  shopping list.
			handleShopByListsRequest(request, response, shoppingForm, mapping);
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleDeleteShoppingListRequest: ", e);
            return saveErrors(request, mapping, errors);
		}
		return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
    }
    /*
     * Private method to determine what action forward should be returned after renaming a order guide.
     */
    private ActionForward handleRenameShoppingListRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null;
    	ActionMessages messages = null;
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
			if(isUserCanOnlyBrowse){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS); 
			}
			errors = ShoppingLogic.renameShoppingList(request,shoppingForm);
			if (errors!=null && errors.size() > 0) {
                saveErrors(request, errors);
            }
			else {
				//STJ-5774: Refresh Shopping List sub-menu after renaming shopping list.
  				//ShoppingLogic.refreshShoppingLists(request);
  				
            	//Save the success message if shopping list is renamed.
				messages = new ActionMessages();
				String message = ClwI18nUtil.getMessage(request,"shoppingList.renamed", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
            }
			// Need to update shopping lists after renaming shopping lists.
			handleShopByListsRequest(request, response, shoppingForm, mapping);
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleRenameShoppingListRequest: ", e);
            return saveErrors(request, mapping, errors);
		}
		return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
    }
    /*
     * Private method to determine what action forward after creating new Order Guide
     */
    private ActionForward handleCreateShoppingListRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	ActionErrors errors = null;
    	ActionMessages messages = null;
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
			if(isUserCanOnlyBrowse){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS); 
			}
			errors = ShoppingLogic.createShoppingList(request,shoppingForm);
			if (errors!=null && errors.size() > 0) {
                saveErrors(request, errors);
            }
			else {
            	//Save the success message if shopping list is created.
				messages = new ActionMessages();
				String message = ClwI18nUtil.getMessage(request,"shoppingList.created", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
            }
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleCreateShoppingListRequest: ", e);
            return saveErrors(request, mapping, errors);
		}
		//Need to refresh the current shopping list (which will be the new shopping list
		//if it was created successfully or the previously selected shopping list if there
		//were errors on the creation
		return handleSelectedShoppingListRequest(request, response, shoppingForm, mapping);   
    }
   
    /*
     * Private method to determine what action forward should be returned after deleting 
     * selected items from order guide.
     */
    private ActionForward handleRemoveSelectedShoppingListItemsRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	  ActionErrors errors = null;
    	  ActionMessages messages = null;
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
			if(isUserCanOnlyBrowse){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS); 
			}
			boolean canEdit = ShoppingLogic.canEditShoppingList(request, shoppingForm);
			if(!canEdit){
				messages = new ActionMessages();
	        	String message = ClwI18nUtil.getMessage(request,"shoppingList.errors.can'tDeleteTemplateShoppingListItem", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
			}
			//STJ-5802: Success Info message.
			String infoMessage = "shoppingList.itemDeleted";
			EditOrderGuideForm editOrderGuideForm = shoppingForm.getEditOrderGuideForm();
			long[] selectedItems = editOrderGuideForm.getSelectBox();
			if(selectedItems.length==0) {
				messages = new ActionMessages();
				String message = ClwI18nUtil.getMessage(request,"shoppingList.itemToRemove", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
			} else {
				if(selectedItems.length>1) {
					infoMessage = "shoppingList.itemsDeleted";
				}
				errors = ShoppingLogic.removeSelectedUserShoppingListItem(request,shoppingForm);
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	            }
				else {
					//STJ-5774: Refresh Shopping List sub-menu after removing items to existing shopping list.
	  				ShoppingLogic.refreshShoppingLists(request);
	  				
	            	//Save the success message if the item is deleted successfully
					messages = new ActionMessages();
					String message = ClwI18nUtil.getMessage(request,infoMessage, null);
		        	messages.add("message", new ActionMessage("message.simpleMessage", message));
		        	saveMessages(request, messages);
	            }
				// Need to refresh the current shopping list after deleting item from it.
				handleSelectedShoppingListRequest(request, response, shoppingForm, mapping);
			} 
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleRemoveSelectedShoppingListItemsRequest: ", e);
        	return saveErrors(request, mapping, errors); 
		}
		return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
    }
      /*
       * Private method to determine what action forward should be returned 
       * after adding items from order guide to cart.
       */
    private ActionForward handleAddShoppingListItemsToCartRequest(HttpServletRequest request, HttpServletResponse response, 
      		ShoppingForm shoppingForm, ActionMapping mapping) {
        	ActionErrors errors = null;
  		//Populate the common data
  		populateCommonData(request, shoppingForm);
  		ActionMessages messages = new ActionMessages();
		try {
			boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
			if(isUserCanOnlyBrowse){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS); 
			}
			errors = ShoppingLogic.addToCart(request, shoppingForm);
			if (errors != null && errors.size() > 0) {
				saveErrors(request, errors);
				return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);
			}
			else {
            	//Save the shopping cart messages and confirmation messages, if any
				String confMsg = shoppingForm.getUserShopForm().getConfirmMessage();
    			saveMessages(request,confMsg,true);
    			/*  STJ-5196 Unnecessary information message
    			boolean canEditShoppingList = ShoppingLogic.canEditShoppingList(request, shoppingForm);
    			if(canEditShoppingList){//Following message should be shown only for user shopping lists.
					String message = ClwI18nUtil.getMessage(request,"shoppingList.clickOn.save.to.updateItems", null);
			        messages.add("message", new ActionMessage("message.simpleMessage", message));
    			}
		        messages.add(getMessages(request));
		       	saveMessages(request, messages);  */
            }
			// Need to refresh the shopping list after adding items to cart.
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			if(!sessionDataUtil.isActionProductSearch()){
				errors = ShoppingLogic.getSelectedShoppingListItems(request,shoppingForm);
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	            }
			}

		} catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleAddShoppingListItemsToCartRequest: ",e);
			return saveErrors(request, mapping, errors);
		}
  		return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
      }
        /*
         * Private method to determine what action forward should be returned 
         * after adding items from order guide to cart.
         */
    private ActionForward handleUpdateQuantityRequest(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
    	  ActionErrors errors = null;
    	  ActionMessages messages = null;
		//Populate the common data
		populateCommonData(request, shoppingForm);
		try {
			boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
			if(isUserCanOnlyBrowse){
			    errors = new ActionErrors();
                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                errors.add("error", new ActionMessage("error.simpleGenericError", message));
                saveErrors(request, errors);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS); 
			}
			boolean canEdit = ShoppingLogic.canEditShoppingList(request, shoppingForm);
			if(!canEdit){
				messages = new ActionMessages();
	        	String message = ClwI18nUtil.getMessage(request,"shoppingList.errors.can'tUpdateTemplateShoppingListItem", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
	        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
			}
			errors = ShoppingLogic.updateQuantity(request,shoppingForm);
			if (errors!=null && errors.size() > 0) {
                saveErrors(request, errors);
                return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);
            }
			else {
            	//Save the success message if the items are updated.
				messages = new ActionMessages();
				String message = ClwI18nUtil.getMessage(request,"shoppingList.updated", null);
	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	        	saveMessages(request, messages);
            }
			// Need to refresh the current shopping list after updating item quantities.
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			if(!sessionDataUtil.isActionProductSearch()){
				errors = ShoppingLogic.getSelectedShoppingListItems(request,shoppingForm);
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	            }
			}
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleUpdateQuantityRequest: ", e);
        	return saveErrors(request, mapping, errors); 
		}
		return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
    }
          /*
           * Private method to determine what action forward should be returned after searching for a product item.
           */
    private ActionForward handleSearchForShoppingListItem(HttpServletRequest request, HttpServletResponse response, 
    		ShoppingForm shoppingForm, ActionMapping mapping) {
		try {
			populateCommonData(request, shoppingForm);
			Map<String,Object> results = ShoppingLogic.searchForShoppingListItem(request,shoppingForm);
	    	ActionErrors errors = (ActionErrors) results.get(ActionErrors.GLOBAL_ERROR);
	    	ActionMessages messages = (ActionMessages) results.get(ActionErrors.GLOBAL_MESSAGE);
	        
	        //save any errors that occurred
	    	if (errors.size() > 0) {
	    		saveErrors(request, errors);
	    	}
	    	//save any messages that we need to return
	    	if (messages.size() > 0) {
	    		saveMessages(request, messages);
	    	}
		}
		catch (Exception e) {
			log.error("Unexpected exception in ShoppingAction.handleSearchForShoppingListItem: ", e);
        	return saveErrors(request, mapping, null); 
		}
    	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);    
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
	    /*
         * Private method to determine what action forward should be returned 
         * after adding items from order guide to cart.
         */
	    private ActionForward handleAddItemsToExistingShoppingList(HttpServletRequest request, HttpServletResponse response, 
          		ShoppingForm shoppingForm, ActionMapping mapping, String retAction) {
          	  ActionErrors errors = null;
          	ActionMessages messages = null;
      		//Populate the common data
      		populateCommonData(request, shoppingForm);
      		try {
      			boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
    			if(isUserCanOnlyBrowse){
    			    errors = new ActionErrors();
                    String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                    errors.add("error", new ActionMessage("error.simpleGenericError", message));
                    saveErrors(request, errors);
    	        	return mapping.findForward(retAction);  
    			}
      			errors = ShoppingLogic.addItemsToExistingShoppingList(request,shoppingForm);
      			if (errors!=null && errors.size() > 0) {
                      saveErrors(request, errors);
                }
      			else {
	            	//STJSCR-20: Set Order guides into session for Shopping List sub-menu
      				
      				UserShopForm userShopFormPrev = shoppingForm.getUserShopForm();
      				List origCartItems=null;
					if(userShopFormPrev==null) {
						userShopFormPrev = new UserShopForm();
					}else{
						origCartItems=userShopFormPrev.getCartItems();
					}
					
					ShoppingLogic.shopByLists(request, shoppingForm);
					UserShopForm userShopForm = shoppingForm.getUserShopForm();
					if(userShopForm==null) {
						userShopForm = new UserShopForm();
					}
					EditOrderGuideForm editOrderGuideForm = shoppingForm.getEditOrderGuideForm();
					if(editOrderGuideForm==null) {
						editOrderGuideForm = new EditOrderGuideForm();
					}
					SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
					sessionDataUtil.setUserShopForm(userShopForm);
					sessionDataUtil.setEditOrderGuideForm(editOrderGuideForm);
					sessionDataUtil.setSelectedSubTab(Constants.TAB_PRODUCTS);
					//
      				//STJ-5774: Refresh Shopping List sub-menu after adding items to existing shopping list.
      				ShoppingLogic.recalcShoppingLists(request, shoppingForm);
      				
      				shoppingForm.getUserShopForm().setCartItems(origCartItems);
      				      				
                	//Save the success message if items are added to selected shopping list.
    				messages = new ActionMessages();
    				String message = ClwI18nUtil.getMessage(request,"productCatalog.itemsAddedToShoppingList", null);
    	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
    	        	saveMessages(request, messages);

                }
      			
      		}
      		catch (Exception e) {
      			log.error("Unexpected exception in ShoppingAction.handleAddItemsToExistingShoppingList: ", e);
              	return saveErrors(request, mapping, errors);
      		}
      		return mapping.findForward(retAction);  
          }
          /*
           * Private method to determine what action forward after creating new Order Guide
           */
	    private ActionForward handleNewShoppingListFromProductCatalog(HttpServletRequest request, HttpServletResponse response, 
	    		ShoppingForm shoppingForm, ActionMapping mapping, String retAction) {
	    	ActionErrors errors = null;
	    	ActionMessages messages = null;
			//Populate the common data
			populateCommonData(request, shoppingForm);
			try {
				CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
				if(appUser.isBrowseOnly()){
				    errors = new ActionErrors();
	                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
	                errors.add("error", new ActionMessage("error.simpleGenericError", message));
	                saveErrors(request, errors);
		        	return mapping.findForward(retAction);  
				}
				errors = ShoppingLogic.newUserShoppingList(request,shoppingForm);
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	            }
				else {
	            	//STJSCR-20: Set Order guides into session for Shopping List sub-menu
					UserShopForm userShopFormPrev = shoppingForm.getUserShopForm();
      				List origCartItems=null;
      				if(userShopFormPrev==null) {
						userShopFormPrev = new UserShopForm();
					}else{
						origCartItems=userShopFormPrev.getCartItems();
					}
      				
					ShoppingLogic.shopByLists(request, shoppingForm);
					UserShopForm userShopForm = shoppingForm.getUserShopForm();
					if(userShopForm==null) {
						userShopForm = new UserShopForm();
					}
					EditOrderGuideForm editOrderGuideForm = shoppingForm.getEditOrderGuideForm();
					if(editOrderGuideForm==null) {
						editOrderGuideForm = new EditOrderGuideForm();
					}
					SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
					sessionDataUtil.setUserShopForm(userShopForm);
					sessionDataUtil.setEditOrderGuideForm(editOrderGuideForm);
					sessionDataUtil.setSelectedSubTab(Constants.TAB_PRODUCTS);
					//
				    ShoppingLogic.recalcShoppingLists(request, shoppingForm);
				    
				    shoppingForm.getUserShopForm().setCartItems(origCartItems);
				 
                	//Save the success message if shopping list is created.
    				messages = new ActionMessages();
    				String message = ClwI18nUtil.getMessage(request,"productCatalog.createdShoppingList", null);
    	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
    	        	saveMessages(request, messages);
                }
			}
			catch (Exception e) {
				log.error("Unexpected exception in ShoppingAction.handleNewShoppingListFromProductCatalogt: ", e);
	        	return saveErrors(request, mapping, errors); 
			}
			return mapping.findForward(retAction);  
	    }
          /*
           * Private method to determine what action forward should be returned after searching for a product item.
           */
	    private ActionForward handleSearchProductCatalogItem(HttpServletRequest request, HttpServletResponse response, 
	    		ShoppingForm shoppingForm, ActionMapping mapping) {
	    	ActionErrors errors = null;
			populateCommonData(request, shoppingForm);
	    	//if the user has not yet selected a location, return an error
	    	SiteData currentLocation = ShopTool.getCurrentSite(request);
	        if (currentLocation == null) {
	        	errors = new ActionErrors();
	            String errorMess = ClwI18nUtil.getMessage(request,
	                    "error.noLocationSelected", null);
	            errors.add("error", new ActionError("error.simpleGenericError",
	                    errorMess));
                saveErrors(request, errors);
                return mapping.findForward(MAPPING_VIEW_DASHBOARD);
	        }
	        else {
				try {
					Map<String,Object> results = ShoppingLogic.searchForProductCatalogItem(request,shoppingForm);
			    	errors = (ActionErrors) results.get(ActionErrors.GLOBAL_ERROR);
			    	ActionMessages messages = (ActionMessages) results.get(ActionErrors.GLOBAL_MESSAGE);
			        
			        //save any errors that occurred
			    	if (errors.size() > 0) {
			    		saveErrors(request, errors);
			    	}
			    	//save any messages that we need to return
			    	if (messages.size() > 0) {
			    		saveMessages(request, messages);
			    	}
				}
				catch (Exception e) {
					log.error("Unexpected exception in ShoppingAction.handleNavigateItems: ", e);
		        	return saveErrors(request, mapping, errors); 
				}
	        }
	        return mapping.findForward(MAPPING_SHOPPING_SHOW_PRODUCT_CATALOG); 
	    }
          /*
           * Private method to determine what action forward after creating new Order Guide
           */
	    private ActionForward handleAddShoppingListItemsToParOrder(HttpServletRequest request, HttpServletResponse response, 
	    		ShoppingForm shoppingForm, ActionMapping mapping) {
	    	ActionErrors errors = null;
			ActionMessages messages = null;
			//Populate the common data
			populateCommonData(request, shoppingForm);
			try {
				boolean isUserCanOnlyBrowse = ShoppingLogic.isUserBrowseOnly(request, shoppingForm);
				if(isUserCanOnlyBrowse){
				    errors = new ActionErrors();
	                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
	                errors.add("error", new ActionMessage("error.simpleGenericError", message));
	                saveErrors(request, errors);
		        	return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS); 
				}
				errors = ShoppingLogic.addShoppingListItemsToParOrder(request, shoppingForm);
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	                return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);
	            }
				else {
  	            	//Save the success message if items are added to PAR order.
					messages = new ActionMessages();
  					String message = ClwI18nUtil.getMessage(request,"shoppingList.itemsAddedToCorporateOrder", null);
  		        	messages.add("message", new ActionMessage("message.simpleMessage", message));
  		        	saveMessages(request, messages);
  		        	/* STJ-5196 Unnecessary information message
  					message = ClwI18nUtil.getMessage(request,"shoppingList.clickOn.save.to.updateItems", null);
  			        messages.add("message", new ActionMessage("message.simpleMessage", message));
  			       	saveMessages(request, messages);           */
  	            }
				
			}
			catch (Exception e) {
				log.error("Unexpected exception in ShoppingAction.handleAddShoppingListItemsToCorporateOrder: ", e);
	        	return saveErrors(request, mapping, errors);
			}
			return mapping.findForward(MAPPING_SHOPPING_SHOW_SHOPPING_LISTS);   
	    }
         /*
  	     * Private method to determine what action forward should be returned after adding catalog items to PAR order.
  	     */
	    private ActionForward handleAddCatalogItemsToPAROrder(HttpServletRequest request, HttpServletResponse response, 
  	    		ShoppingForm shoppingForm, ActionMapping mapping, String retAction) {
  	    	ActionErrors errors = null;
  	    	ActionMessages messages = null;
  			try {
  				populateCommonData(request, shoppingForm);
  				CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
  				if(appUser.isBrowseOnly()){
  				    errors = new ActionErrors();
                    String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
                    errors.add("error", new ActionMessage("error.simpleGenericError", message));
                    saveErrors(request, errors);
		        	return mapping.findForward(retAction);    
				}
  				//STJ-5627: Refresh the cart before adding items to the corporate order.
  				errors = ShoppingLogic.obtainParOrder(request, shoppingForm);
    			
    			if(errors!=null && errors.size()>0) {
    				saveErrors(request, errors);
    			} else {
    				errors = ShoppingLogic.addCatalogItemsToParOrder(request,shoppingForm);
      				if (errors.size() > 0) {
      	                saveErrors(request, errors);
      	            }
      				else {
      	            	//Save the success message if items are added to PAR order.
      					messages = new ActionMessages();
      					String message = ClwI18nUtil.getMessage(request,"shoppingList.itemsAddedToCorporateOrder", null);
      		        	messages.add("message", new ActionMessage("message.simpleMessage", message));
      		        	saveMessages(request, messages);
      	            }
    			}
  			}
  			catch (Exception e) {
  				log.error("Unexpected exception in ShoppingAction.handleAddCatalogItemsToPAROrder: ", e);
  	        	return saveErrors(request, mapping, errors); 
  			}
  	    	return mapping.findForward(retAction);    
  	    }
  	    
  	    /*
  	     * Private method to determine what action forward should be returned after save shopping cart request.
  	     */
	    private ActionForward handleAddAllToShoppingList(HttpServletRequest request, HttpServletResponse response,
  				ShoppingForm shoppingForm, ActionMapping mapping) {
  			
  			ActionErrors errors = null;
  			try {
                // Checking browse-only status
                CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
                if (clwUser.isBrowseOnly()) {
                    populateShoppingListOptions(request, shoppingForm);
                    errors = new ActionErrors();
                    String message = ClwI18nUtil.getMessage(request,
                            "orders.error.browseOnlyCannotAddOrModifyShoppingList", null);
                    errors.add("error", new ActionMessage("error.simpleGenericError",
                            message));
                    saveErrors(request, errors);
                    return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
                }
  				//Shopping Cart should be saved before creating new Shopping list.
  				handleSaveCartRequest(request,response,shoppingForm,mapping);
  				//get errors if there are any in the request scope.
				errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY); 
				
				if (errors==null || errors.isEmpty()) {
					errors = ShoppingLogic.addAllCartItemsToShoppingList(request,shoppingForm);
	  				if (errors!=null && errors.size() > 0) {
	  	                saveErrors(request, errors);
	  	            }
	  				else {
	  					//STJ-5774: Refresh Shopping List sub-menu after add all to shopping list from view cart page.
	  	  				ShoppingLogic.refreshShoppingLists(request);
	  	  				
	                	//Save the success message if items are added to selected shopping list.
	  					ActionMessages messages = new ActionMessages();
	    				String message = ClwI18nUtil.getMessage(request,"productCatalog.itemsAddedToShoppingList", null);
	    	        	messages.add("message", new ActionMessage("message.simpleMessage", message));
	    	        	saveMessages(request, messages);
	                }
				}
				
  			}catch (Exception e) {
  				log.error("Unexpected exception in ShoppingAction.handleAddAllToShoppingList: ", e);
  	        	return saveErrors(request, mapping, errors); 
  			}
  			return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
  		}
  		
  		/*
         * Private method to determine what action forward after creating new Order Guide
         */
	    private ActionForward handleNewShoppingListFromCart(HttpServletRequest request, HttpServletResponse response, 
	    		ShoppingForm shoppingForm, ActionMapping mapping) {
	    	ActionErrors errors = null;
			try {
                // Checking browse-only status
                CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
                if (clwUser.isBrowseOnly()) {
                    populateShoppingListOptions(request, shoppingForm);
                    errors = new ActionErrors();
                    String message = ClwI18nUtil.getMessage(request,
                            "orders.error.browseOnlyCannotAddOrModifyShoppingList", null);
                    errors.add("error", new ActionMessage("error.simpleGenericError",
                            message));
                    saveErrors(request, errors);
                    return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
                }
				//Shopping Cart should be saved before creating new Shopping list.
				handleSaveCartRequest(request,response,shoppingForm,mapping);
				//get errors if there are any in the request scope.
				errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY); 
				
				if (errors==null || errors.isEmpty()) {
					errors = ShoppingLogic.newUserShoppingList(request,shoppingForm);
					if (errors!=null && errors.size() > 0) {
		                saveErrors(request, errors);
		            }
					else {
					    ShoppingLogic.recalcShoppingLists(request, shoppingForm);
		            	//Save the success message if shopping list is created.
						ActionMessages messages = new ActionMessages();
						String message = ClwI18nUtil.getMessage(request,"shoppingList.created", null);
			        	messages.add("message", new ActionMessage("message.simpleMessage", message));
			        	saveMessages(request, messages);
		            }
				}
				populateShoppingListOptions(request,shoppingForm);
			}catch (Exception e) {
				log.error("Unexpected exception in ShoppingAction.handleNewShoppingListFromCart: ", e);
	        	return saveErrors(request, mapping, errors); 
			}
			return mapping.findForward(MAPPING_VIEW_SHOPPING_CART); 
	    }
  	    
	    /**
	     * gets the data to populate Shopping List options.
	     * @param request
	     * @param form
	     */
	    private void populateShoppingListOptions(HttpServletRequest request,ShoppingForm form){
	    	List<LabelValueBean> returnValue = new ArrayList<LabelValueBean>();
	    	String select = ClwI18nUtil.getMessage(request, "userportal.esw.shoppingCart.addShoppingList.selectOption");
	    	returnValue.add(new LabelValueBean(select,""));
	    	try {
	    		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
	    		if(editOrderGuideForm == null) {
	    			SessionDataUtil sdu = Utility.getSessionDataUtil(request);
	    			editOrderGuideForm = sdu.getEditOrderGuideForm();
	    		}
		    	OrderGuideDataVector ogdList = editOrderGuideForm.getUserOrderGuides();
		    	OrderGuideData ogData;
		    	for(int ind=0; ind<ogdList.size();ind++) {
		    		ogData = ((OrderGuideData)ogdList.get(ind));
		    		returnValue.add(new LabelValueBean(ogData.getShortDesc(),String.valueOf(ogData.getOrderGuideId())));
		    	}
	    	}catch(Exception e) {
	    		log.info("There was a problem while setting the Shopping List Options from UserOrderGuides");
	    	}
	    	String createList = ClwI18nUtil.getMessage(request, "userportal.esw.shoppingCart.addShoppingList.createListOption");
	    	returnValue.add(new LabelValueBean(createList,"newList"));
	    	form.setShoppingListOptions(returnValue);
	    }
	    
	    /*
	    * Private method to determine what action forward should be returned after Check Out request.
	    */
	    private ActionForward handleCheckOutRequest(HttpServletRequest request, HttpServletResponse response,
	            ShoppingForm form, ActionMapping mapping) {
            ActionErrors errors = new ActionErrors();
            CleanwiseUser appUser = ShopTool.getCurrentUser(request);
            String forwardStr = MAPPING_VIEW_SHOPPING_CART;
            if (appUser.isBrowseOnly()) {
                String message = ClwI18nUtil.getMessage(request,
                    "orders.error.browseOnlyCannotCheckout", null);
                errors.add("error", new ActionMessage("error.simpleGenericError",
                    message));
                saveErrors(request, errors);
            } else {
                // Shopping Cart should be saved, if there are any changes made
                // before going to Checkout page.
               handleSaveCartRequest(request, response, (ShoppingForm) form,
                    mapping);
                // get errors if there are any from the request scope.
                errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);
            }
            if (errors != null && !errors.isEmpty()) {
                populateShoppingListOptions(request, form);
            } else {
               forwardStr = MAPPING_SHOW_CHECK_OUT;
            }
            return mapping.findForward(forwardStr);
	    }
  	   
  	    /**
  	     * Saves the informational and warning messages.
  	     * @param request
  	     * @param message - Message to save.
  	     * @param needShoppingCartMsgs - If Shopping Cart Messages are needed, then this should be set to true.
  	     */
  	    private void saveMessages(HttpServletRequest request,String message, boolean needShoppingCartMsgs){
  	    
				ActionMessages messages = null;
				if(needShoppingCartMsgs) {
					ShoppingCartData cartData = ShopTool.getCurrentShoppingCart(request);
					messages = reSetCartMessages(request,cartData);
				}
				//If there is any status related message.
				if (Utility.isSet(message)) {
					if(messages==null) {
						messages = new ActionMessages();
					}
		        	messages.add("message", new ActionMessage("message.simpleMessage", message));
		        	message = null;
				}
				if(messages!=null && !messages.isEmpty()){
					saveMessages(request, messages);
				}
  	    }
  	    
  	    /**
  	     * Resets the shopping cart messages once they are added in ActionMessages.
  	     * @param request - HTTPServeletRequest object
  	     * @param cartData - <ShoppingCartData> shopping cart object
  	     * @return - <ActionMessages> returns action messages if any
  	     */
  	    @SuppressWarnings("rawtypes")
		private ActionMessages reSetCartMessages(HttpServletRequest request, ShoppingCartData cartData) {
  	    	ActionMessages messages = null;
  	    	if(cartData!=null){
  	    		
				List warningMsgs = cartData.getWarningMessages();
				List itemMsgs = cartData.getItemMessages();
				if(Utility.isSet(warningMsgs) || Utility.isSet(itemMsgs)) {
					messages = new ActionMessages();
					cartData.setWarningMessages(null);
					cartData.setItemMessages(null);
				}
				
				 String warningMsg;
				 for (int ind = 0; ind < warningMsgs.size(); ind++) {
					 warningMsg = (String) warningMsgs.get(ind);
					 messages.add("message", new ActionMessage("message.simpleMessage", warningMsg));
				 }
				 
				 String message,itemSkuMsg;
				 for (int ind = 0; ind < itemMsgs.size(); ind++) {
					ShoppingCartData.CartItemInfo cii = (ShoppingCartData.CartItemInfo) itemMsgs.get(ind);
			        List al = cii.getI18nItemMessageAL();
			        String key = (String) al.get(0);
			        Object[]  params = null,
			        		  skuParms = null;
			        if(al.size()>1) {
			          params = new Object[al.size()-1];
			          for(int ii=0; ii<al.size()-1; ii++) {
			            params[ii] = (Object)al.get(ii+1);
			          }
			        }
			        skuParms = new Object[1];
			        skuParms[0] = cii.mCartItemData.getActualSkuNum();
			        itemSkuMsg = ClwI18nUtil.getMessage(request,"shoppingMessages.text.itemSku",skuParms);
			        message = ClwI18nUtil.getMessage(request,key,params);
			        messages.add("message", new ActionMessage("message.simpleMessage", itemSkuMsg+" "+message));
				 }
  	    	}
  	    	return messages;
  	    }
  	  /*
         * Private method to determine what action forward after adding quick shop items to cart.
         */
  	  private ActionForward handleAddQuickShopItemsToCart(HttpServletRequest request, HttpServletResponse response, 
	    		ShoppingForm shoppingForm, ActionMapping mapping) {
	    	ActionErrors errors = null;
			//Populate the common data
			populateCommonData(request, shoppingForm);
			populateShoppingListOptions(request,shoppingForm);
			try {
				CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
				if(appUser.isBrowseOnly()){
				    errors = new ActionErrors();
	                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
	                errors.add("error", new ActionMessage("error.simpleGenericError", message));
	                saveErrors(request, errors);
		        	return mapping.findForward(MAPPING_VIEW_SHOPPING_CART); 
				}
				errors = ShoppingLogic.addQuickShopItemsToCart(request, shoppingForm);
				if (errors != null && errors.size() > 0) {
					shoppingForm.setShowQuickShop(true);
	                saveErrors(request, errors);
	                return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
	            }
				else {
	            	//Save the shopping cart messages and confirmation messages, if any
	    			String confMsg = shoppingForm.getQuickOrderForm().getConfirmMessage();
	    			saveMessages(request,confMsg,true);
	    			handleViewCartRequest(request, response, shoppingForm, mapping);
	            }
				
			}
			catch (Exception e) {
				log.error("Unexpected exception in ShoppingAction.handleAddQuickShopItemsToCart: ", e);
	        	return saveErrors(request, mapping, errors);
			}
			return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);   
	    }
  	 /*
       * Private method to determine what action forward after resolving skus and adding quick shop items to cart.
       */
	  private ActionForward handleResolveQuickShopSkuCollisions(HttpServletRequest request, HttpServletResponse response, 
	    		ShoppingForm shoppingForm, ActionMapping mapping) {
	    	ActionErrors errors = null;
			//Populate the common data
			populateCommonData(request, shoppingForm);
			populateShoppingListOptions(request,shoppingForm);
			try {
				CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
				if(appUser.isBrowseOnly()){
				    errors = new ActionErrors();
	                String message = ClwI18nUtil.getMessage(request,"product.detail.errors.userCanOnlyBrowse", null);
	                errors.add("error", new ActionMessage("error.simpleGenericError", message));
	                saveErrors(request, errors);
		        	return mapping.findForward(MAPPING_VIEW_SHOPPING_CART); 
				}
				errors = ShoppingLogic.resolveQuickShopSkuCollisions(request, shoppingForm);
				if (errors!=null && errors.size() > 0) {
	                saveErrors(request, errors);
	                return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);
	            }
				else {
	            	//Save the shopping cart messages and confirmation messages, if any
	    			String confMsg = shoppingForm.getQuickOrderForm().getConfirmMessage();
	    			saveMessages(request,confMsg,true);
	    			handleViewCartRequest(request, response, shoppingForm, mapping);
	            }
				
			}
			catch (Exception e) {
				log.error("Unexpected exception in ShoppingAction.handleQuickShopResolveSkus: ", e);
	        	return saveErrors(request, mapping, errors);
			}
			return mapping.findForward(MAPPING_VIEW_SHOPPING_CART);   
	    }
      private ActionForward handlePrintOrExportShoppingListItems(HttpServletRequest request, HttpServletResponse response, 
              ShoppingForm shoppingForm, ActionMapping mapping) {
        ActionErrors errors = null;
        ActionForward returnValue = null;
                         try {
                    String action =  request.getParameter(Constants.PARAMETER_OPERATION);
                    MessageResources mResources = getResources(request);
                    errors = ShoppingLogic.printOrExportShoppingListItems(response, request,shoppingForm, action, mResources);
                    if (errors!=null && errors.size() > 0) {
                  saveErrors(request, errors);
              }
              }
              catch (Exception e) {
                    log.error("Unexpected exception in ShoppingAction. handlePrintOrExportShoppingListItems: "+e);
                    returnValue = saveErrors(request,mapping,errors);  
              }
              
        return returnValue;    
      }      
      
      private ActionForward handleShowPhysicalCartRequest(HttpServletRequest request, HttpServletResponse response,
              ShoppingForm form, ActionMapping mapping) {
          
          ActionErrors errors = new ActionErrors();
          ActionForward returnValue;
          try {
              
              SiteData currentLocation = ShopTool.getCurrentSite(request);
              //if the user has not yet selected a location, return an error
              if (currentLocation == null) {
                  String errorMess = ClwI18nUtil.getMessage(request, "error.noLocationSelected");
                  errors.add("error", new ActionError("error.simpleGenericError",
                          errorMess));
                  saveErrors(request, errors);
              } else {

                  errors = ShoppingLogic.obtainParOrder(request, form);
                  
                  if(errors!=null && errors.size()>0) {
                      saveErrors(request, errors);
                  } else {
                      //Set ShoppingCartForm from session to ShoppingForm.
                      setShoppingCartForm(request,form);
                      
                      //if no items were found, return an informational message to the user.
                      if (errors.isEmpty() && !Utility.isSet(form.getShoppingCartForm().getCartItems())) {
                          ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
                          if(messages==null) {
                              messages = new ActionMessages();
                          }
                          String message = ClwI18nUtil.getMessage(request,"shoppingCart.message.noItems", null);
                          messages.add("message", new ActionMessage("message.simpleMessage", message));
                          saveMessages(request, messages);
                      }else{
                          ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
                          if(messages==null) {
                              messages = new ActionMessages();
                          }
                          String[] param = {"error", "error", "error"};
                          PhysicalInventoryPeriod invPeriod = ShopTool.getCurrentPhysicalPeriod(request);
                          if(invPeriod != null && invPeriod.getEndDate() != null){
                              param[0] = ClwI18nUtil.formatDate(request,invPeriod.getEndDate(),DateFormat.MEDIUM);
                              Calendar gCalendar = Calendar.getInstance();
                              gCalendar.setTime(invPeriod.getAbsoluteFinishDate());
                              gCalendar.add(Calendar.HOUR, 24);// add one day                              
                              param[1] = ClwI18nUtil.formatDate(request,gCalendar.getTime(),DateFormat.MEDIUM);
                              if (currentLocation.getNextOrdercutoffDate() != null)
                                  param[2] = ClwI18nUtil.formatDate(request,currentLocation.getNextOrdercutoffDate(),DateFormat.MEDIUM);
                          }
                          // Physical Inventory inputs due (Feb 23, 2015) at 6pm.<br>Enter non-forecast items between (Feb 25, 2015) and (Feb 26, 2015) before 6pm.
                          String message = ClwI18nUtil.getMessage(request,"shoppingCart.text.physicalInventoryInputDue", param);
                          messages.add("message", new ActionMessage("message.simpleMessage", message));
                          saveMessages(request, messages);
                      }
                  }
                  
              }          
              returnValue = mapping.findForward(MAPPING_ORDERS_SHOW_PHYSICAL_CART);
          } catch (Exception e) {
              log.error("Unexpected exception in ShoppingAction.handleShowPhysicalCartRequest: "+e);
              returnValue = saveErrors(request,mapping,errors);  
          } finally {
              Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
          }
          
          return returnValue;
      }

      private ActionForward handleSavePhysicalCartRequest(HttpServletRequest request, HttpServletResponse response, 
              ShoppingForm shoppingForm, ActionMapping mapping) {
          ActionErrors errors = null ;
          ActionMessages messages = null;
          ActionForward returnValue;
          
          try {
              CleanwiseUser clwUser = ShopTool.getCurrentUser(request);
              if (clwUser.isBrowseOnly()) {
                  errors = new ActionErrors();
                  String message = ClwI18nUtil.getMessage(request,
                          "orders.error.browseOnlyCannotUpdatePhysicalCart",
                          null);
                  errors.add("error", new ActionMessage(
                          "error.simpleGenericError", message));
                  saveErrors(request, errors);
                  return mapping.findForward(MAPPING_ORDERS_SHOW_PHYSICAL_CART);
              }
              //Validate Quantities entered.
              errors = ShoppingLogic.validateQuantities(request, shoppingForm.getShoppingCartForm(),true);
              if(errors==null || errors.isEmpty()) {
                  errors = ShoppingLogic.saveParOrder(request,shoppingForm);
                  
                  if (errors!=null && errors.size() > 0) {
                      saveErrors(request, errors);
                  } else {
                      //Need to call show PAR Orders request to get latest data for PAR Activity
                      handleShowParOrderRequest(request,response,shoppingForm,mapping);
                      
                      errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY); 

                      //If there are no errors, print success message.
                      if(errors==null || errors.isEmpty()) {
                          if(messages==null) {
                              messages = new ActionMessages();
                          }
                          String message = ClwI18nUtil.getMessage(request,"shoppingCart.info.message.physicalCartSave", null);
                          messages.add("message", new ActionMessage("message.simpleMessage", message));
                          String confMessage;
                          if(ShopTool.isPhysicalCartCompliant(request)){
                              confMessage = ClwI18nUtil.getMessage(request, "shop.checkout.text.actionMessage.physicalInventoryCompliant", null );
                              messages.add("message", new ActionMessage("message.simpleMessage", confMessage));
                          }else{
                              Object[] param = new Object[2];
                              param[0] = ClwI18nUtil.getMessage(request, "shoppingItems.text.onHand");
                              
                              PhysicalInventoryPeriod invPeriod = ShopTool.getCurrentPhysicalPeriod(request);                              
                              if(invPeriod != null && invPeriod.getEndDate() != null){
                                  param[1] = ClwI18nUtil.formatDate(request,invPeriod.getEndDate(),DateFormat.MEDIUM);
                              }else{
                                  param[1] = "error";
                              }
                              confMessage = ClwI18nUtil.getMessage(request, "shoppingCart.text.physicalInventoryNonCompliant", param);
                              messages.add("message", new ActionMessage("message.simpleMessage", confMessage));
                          }
                          saveMessages(request, messages);
                      } 
                      
                      //Save Info messages, if there are any.
                      messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
                      if(messages!=null && !messages.isEmpty()) {
                          saveMessages(request, messages);
                      }
                  }
              } else {
                  saveErrors(request, errors);
              }
                  
              returnValue = mapping.findForward(MAPPING_ORDERS_SHOW_PHYSICAL_CART);
          }
          catch (Exception e) {
              log.error("Unexpected exception in ShoppingAction.handleSavePhysicalCartRequest: "+e);
              returnValue = saveErrors(request,mapping,errors); 
          } finally {
              Utility.getSessionDataUtil(request).setSelectedSubTab(Constants.EMPTY);
          }
          return returnValue;   
      }
      

}

