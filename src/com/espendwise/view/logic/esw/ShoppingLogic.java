package com.espendwise.view.logic.esw;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.AutoOrder;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.Manufacturer;
import com.cleanwise.service.api.session.OrderGuide;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccCategoryToCostCenterView;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.CategoryInfoView;
import com.cleanwise.service.api.value.ContentData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ManufacturerData;
import com.cleanwise.service.api.value.ManufacturerDataVector;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.service.api.value.MenuItemViewVector;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderScheduleView;
import com.cleanwise.service.api.value.OrderScheduleViewVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.ShoppingCartItemDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.EditOrderGuideForm;
import com.cleanwise.view.forms.QuickOrderForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.EditOrderGuideLogic;
import com.cleanwise.view.logic.QuickOrderLogic;
import com.cleanwise.view.logic.ShoppingCartLogic;
import com.cleanwise.view.logic.UserShopLogic;
import com.cleanwise.view.logic.esw.SiteLocationBudgetLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.ShoppingForm;
/**
 * Implementation of logic that handles Shopping functionality.
 */
public class ShoppingLogic {
	 private static final Logger log = Logger.getLogger(ShoppingLogic.class);

	public static ActionErrors viewShoppingCart(HttpServletRequest request,
			ShoppingForm form) {
		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();

		return ShoppingCartLogic.init(request, shoppingCartForm);
	}

	public static ActionErrors obtainParOrder(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
		ActionErrors errors = ShoppingCartLogic.inventoryInit(request, shoppingCartForm);
		
		//Set Par Order release date, time and order close interval in days.
		shoppingCartForm = (ShoppingCartForm)request.getSession().getAttribute(Constants.INVENTORY_SHOPPING_CART_FORM);
		if (shoppingCartForm != null && shoppingCartForm.getShoppingCart() != null) {
			setParOrderDateTimeInfo(request,form);
			if (errors.isEmpty()){
				errors = ShoppingCartLogic.recalInvCartQty(request, shoppingCartForm);
			}
		}
   		return errors;
	}
	
	public static ActionErrors saveParOrder(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
		
		//Recalculate Order Quantity 
		//ActionErrors errors = ShoppingCartLogic.calcInvOrderQty(request, shoppingCartForm);
		//Save Inventory items.
		ActionErrors errors = ShoppingCartLogic.updateInvShoppingCart(request, shoppingCartForm);
		
		//Set Par Order release date, time and order close interval in days.
		setParOrderDateTimeInfo(request,form);
		
		return errors;
	}
	

	/**
	 * Prepares the data for the existing removeSelected method of old UI.
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	public static ActionErrors removeItemFromCart(HttpServletRequest request,
			ShoppingForm form) {

		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
		shoppingCartForm.setSelectBox(prepareSelectedItmes(form));

		return ShoppingCartLogic.removeSelected(request, shoppingCartForm);
	}
	

	/**
	 * Prepares the data for the existing removeSelected method of old UI.
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	public static ActionErrors removeInvItemFromCart(HttpServletRequest request,
			ShoppingForm form) {
		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
		shoppingCartForm.setSelectBox(prepareSelectedItmes(form));
		
		//Set Par Order release date, time and order close interval in days.
		setParOrderDateTimeInfo(request,form);
		return ShoppingCartLogic.removeInvSelected(request, shoppingCartForm);
	}
	
	private static long[] prepareSelectedItmes(ShoppingForm form){
		// As existing method to remove the items from the cart uses the array
		// of long - ids
		// Need to build selectedItem long array
		long[] selectedItem = new long[1];
		selectedItem[0] = Long.parseLong(form.getItemIdToRemove());
		return selectedItem;
	}

	/**
	 * Removes selected items from user shopping list.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws RemoteException
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static ActionErrors removeSelectedUserShoppingListItem(
			HttpServletRequest request, ShoppingForm form)
			throws RemoteException, Exception {
		ActionErrors errors = null;
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		editOrderGuideForm.setInputOrderGuideId(editOrderGuideForm.getOrderGuideId());
		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		SiteData siteData = appUser.getSite();
		int accountId = siteData.getAccountId();
		int siteId = siteData.getSiteId();
		try{
			AutoOrder autoOrderBean = APIAccess.getAPIAccess().getAutoOrderAPI();
			OrderScheduleViewVector orderScheduleViewVector = 
			autoOrderBean.getOrderSchedules(accountId, siteId, null, null, null, null, null,0);
			//STJ-5774: New way of saving objects into session.
			SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
			Map<Integer,Integer> userShoppingList;
			
			for(int i = 0;orderScheduleViewVector != null && i < orderScheduleViewVector.size();i++){
				OrderScheduleView orderScheduleView = (OrderScheduleView)orderScheduleViewVector.get(i);
				if(orderScheduleView.getOrderGuideId() == editOrderGuideForm.getInputOrderGuideId()){
					userShoppingList = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
					sessionData.setUserShoppingListIds(userShoppingList);
					
					errors = new ActionErrors();
					String errorMess = ClwI18nUtil.getMessage(request,"shoppingList.error.shoppingListIsScheduled.itemCantBeRemoved", null);
					errors.add("error", new ActionMessage("message.simpleMessage",errorMess));
					return errors;
			    }
		   }
		}
		catch (Exception e) {
        	log.error("Exception occurred while removing shopping list item");
        	errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "shoppingList.error.problemRemovingShoppingListItem", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
		}
		// As we are not using select check boxes in new UI, we need to set it to of length 0;
		//editOrderGuideForm.setSelectBox(new long[0]);
		//STJ-5802:
		long[] selectedItems = editOrderGuideForm.getSelectBox();
		//int itemId = Integer.parseInt(form.getItemIdToRemove());
		List shoppingListItems = editOrderGuideForm.getItems();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		if(sessionDataUtil.isActionProductSearch()){
			List productSearchItems = editOrderGuideForm.getItems();
			int[] itemIds = editOrderGuideForm.getItemIds();
			String[] q = editOrderGuideForm.getQuantity();
			int[] orderNumbers = editOrderGuideForm.getOrderNumbers();
			String[] quantity = sessionDataUtil.getQuantity();
			int[] orderNums = sessionDataUtil.getOrderNumbers();
			int[] itemsIds = sessionDataUtil.getOrderNumbers();
				List items = editOrderGuideForm.getItemsAvailable();
				if(items!=null && selectedItems!=null && items.size()-selectedItems.length>=0) {
					List remainingItems = getRemainingItems(items,selectedItems);
					
					quantity = new String[remainingItems.size()];
					itemsIds = new int[remainingItems.size()];
					orderNums = new int[remainingItems.size()];
					for(int i = 0; remainingItems != null && i < remainingItems.size();i++) {
						ShoppingCartItemData item = (ShoppingCartItemData)remainingItems.get(i);
						quantity[i] = item.getQuantityString();
						itemsIds[i] = item.getItemId();
						orderNums[i] = item.getOrderNumber();
					}
					editOrderGuideForm.setItems(remainingItems);
				}
				
				editOrderGuideForm.setItemIds(itemsIds);
				editOrderGuideForm.setQuantity(quantity);
				editOrderGuideForm.setOrderNumbers(orderNums);
				sessionDataUtil.setQuantity(quantity);
				sessionDataUtil.setItemIds(itemsIds);
				sessionDataUtil.setOrderNumbers(orderNums);
				errors = EditOrderGuideLogic.removeSelected(request, editOrderGuideForm);
				editOrderGuideForm.setItems(productSearchItems);
				editOrderGuideForm.setItemIds(itemIds);
				editOrderGuideForm.setQuantity(q);
				editOrderGuideForm.setOrderNumbers(orderNumbers);
		} else {
			/*for(int i = 0;i < shoppingListItems.size();i++){
				ShoppingCartItemData scid = (ShoppingCartItemData)shoppingListItems.get(i);
				if(scid.getItemId() == itemId){
					shoppingListItems.remove(i);
					break;
				}
			}*/
			List remainingItems = getRemainingItems(shoppingListItems,selectedItems);
			editOrderGuideForm.setItems(remainingItems);
			
			errors = EditOrderGuideLogic.removeSelected(request, editOrderGuideForm);
			editOrderGuideForm.setItems(shoppingListItems);
		}
		return errors;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List getRemainingItems(List items, long[] selectedItems) {
		List remainingItems = null;
		if(items!=null && selectedItems!=null && items.size()-selectedItems.length>=0) {
			remainingItems = new ArrayList(items.size()-selectedItems.length);
			
			boolean itemToRemoveFound = false;
			for(int i = 0; items != null && i < items.size();i++) {
					ShoppingCartItemData item = (ShoppingCartItemData)items.get(i);
					itemToRemoveFound = false;
					for(int itemCnt=0; itemCnt<selectedItems.length; itemCnt++) {
						if(item.getItemId() == selectedItems[itemCnt]){
							itemToRemoveFound = true;
							break;
						}
					}
					if(!itemToRemoveFound) {
						remainingItems.add(item);
					}
			}
		} else {
			remainingItems = new ArrayList(0);
		}
		return remainingItems;
	}

	public static ActionErrors removeAllItemsFromCart(
			HttpServletRequest request, ShoppingForm form) {
		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();

		return ShoppingCartLogic.removeAll(request, shoppingCartForm);
	}

	public static ActionErrors reCalculateAndSaveShoppingCart(
			HttpServletRequest request, ShoppingForm form) {
		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
		
		//Validate Quantities entered.
		ActionErrors errors = validateQuantities(request, shoppingCartForm, false);
		
		if(errors==null || errors.isEmpty()) {
			errors = ShoppingCartLogic.recalc(request, shoppingCartForm);
		}
		return errors;
	}

	public static ActionErrors initCatalogShopping(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();

		return UserShopLogic.initCatalogShopping(request, userShopForm, true);
	}

	/**
	 * This method is used to get catalog products.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
	
	public static ActionErrors navigateCatalogProducts(
			HttpServletRequest request, ShoppingForm form) throws Exception {
		ActionErrors errors = new ActionErrors();
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();
		userShopForm.setSelectBox(new long[0]);
		userShopForm.setOriginalCartItems(null);
		userShopForm.setOriginalProducts(null);
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		String catalogItemKey = form.getCatalogItemKey();
		Integer allProductsCount = sessionDataUtil.getAllProductsCount();
		//Check if user has clicked any specific category
		if(catalogItemKey == null || catalogItemKey.trim().equals("")){
			String previousCategory = sessionDataUtil.getPreviousCategory();
			if(previousCategory != null && !previousCategory.trim().equals("")){
				request.setAttribute("catalogItemKey", previousCategory);
				form.setCatalogItemKey(previousCategory);
			}
			else{
				if(allProductsCount != null && allProductsCount <= 500){
					ShoppingCartItemDataVector catalogItemsList =new ShoppingCartItemDataVector();
					MenuItemView menuItemView = form.getUserShopForm().getCatalogMenu();
					MenuItemViewVector menuItemViewVector = (menuItemView != null ? menuItemView.getSubItems() : new MenuItemViewVector());
					for(int i = 0 ;menuItemViewVector != null && i < menuItemViewVector.size() ;i++){
						MenuItemView menuItem = (MenuItemView)menuItemViewVector.get(i);
						String categoryKey = menuItem.getKey();
						request.setAttribute("catalogItemKey", categoryKey);
						errors = UserShopLogic.navigateCatalogProducts(request, userShopForm);
						if(errors.size() > 0){
							String errorMess = ClwI18nUtil.getMessage(request,"productCatlog.errors.unableToGetCatalogItems", null);
							 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
							return errors;
						}
						List items = userShopForm.getCartItems();
						for( int j = 0; items != null && j < items.size(); j++ ){
							catalogItemsList.add(items.get(j));
						}
					}
					userShopForm.setCartItems(catalogItemsList);
					request.setAttribute("catalogItemKey", "0");
			         return errors;
				}
				else { //if clicked on Product Catalog sub menu and All Products has more than 500 items,
						//display next category items so on  until last.
					MenuItemViewVector menuItemViewVector = userShopForm.getCatalogMenu().getSubItems();
					MenuItemView menuItem = null;
					Map<Integer,ProductDataVector> categoryToItemMap = userShopForm.getCatalogMenuCategoryToItemMap();
					int i = 0;
					for(;menuItemViewVector != null && i < menuItemViewVector.size(); i++){
						menuItem = (MenuItemView)menuItemViewVector.get(i);
						int catalogProductsCount = calculateItemCount(menuItem,categoryToItemMap);
						if(catalogProductsCount <= 500){
							break;
						}
					}
					if(i == menuItemViewVector.size()){
						String errorMess = ClwI18nUtil.getMessage(request,"product.catalog.allCatalogsHave.maximumItems", null);
						 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						 return errors;
					}
					
					request.setAttribute("catalogItemKey", menuItem.getKey());
					sessionDataUtil.setPreviousCategory(menuItem.getKey());
					errors = UserShopLogic.navigateCatalogProducts(request, userShopForm);
					if(errors.size() > 0){
						String errorMess = ClwI18nUtil.getMessage(request,"productCatlog.errors.unableToGetCatalogItems", null);
						 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						return errors;
					}
				}
			}
		}
		else{
			if(catalogItemKey.equals("0")){
				ShoppingCartItemDataVector catalogItemsList = new ShoppingCartItemDataVector();
				MenuItemView menuItemView = form.getUserShopForm().getCatalogMenu();
				MenuItemViewVector menuItemViewVector = (menuItemView != null ? menuItemView.getSubItems() : new MenuItemViewVector());
				for(int i = 0 ;i < menuItemViewVector.size() ;i++){
					MenuItemView menuItem = (MenuItemView)menuItemViewVector.get(i);
					String categoryKey = menuItem.getKey();
					request.setAttribute("catalogItemKey", categoryKey);
					errors = UserShopLogic.navigateCatalogProducts(request, userShopForm);
					if(errors.size() > 0){
						String errorMess = ClwI18nUtil.getMessage(request,"productCatlog.errors.unableToGetCatalogItems", null);
						 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
						return errors;
					}
					List items = userShopForm.getCartItems();
					for( int j = 0; items != null && j < items.size(); j++ ){
						catalogItemsList.add(items.get(j));
					}
				}
				userShopForm.setCartItems(catalogItemsList);
				request.setAttribute("catalogItemKey", "0");
		         return errors;
			}
			else{
				sessionDataUtil.setPreviousCategory(catalogItemKey);
				request.setAttribute("catalogItemKey", catalogItemKey);
			}
		}
		 errors = UserShopLogic.navigateCatalogProducts(request, userShopForm);
		 if(errors.size() > 0){
				String errorMess = ClwI18nUtil.getMessage(request,"productCatlog.errors.unableToGetCatalogItems", null);
				 errors.add("error", new ActionError("error.simpleGenericError", errorMess));
				return errors;
			}
		 ArrayList keys = new ArrayList();
		 List path = userShopForm.getCategoryPath();
         for(int i = 1;path!=null && i < path.size();i++){
         	CategoryInfoView categoryInfo = (CategoryInfoView)path.get(i);
         	keys.add(categoryInfo.getCategoryKey());
         }
         request.setAttribute("categorykeys", keys);
         return errors;
         }
	/**
	 * This method is used for printing or exporting items to PDF or EXCEL
	 * 
	 * @param response
	 *            - HttpServletResponse
	 * @param request
	 *            - HttpServletRequest
	 * @param form
	 *            - ShoppingForm
	 * @param action
	 *            - String
	 * @param mResources
	 *            - MessageResources
	 * @return ActionErrors
	 * @throws Exception
	 */
	public static ActionErrors printDetail(HttpServletResponse response,
			HttpServletRequest request, ShoppingForm form, String action,
			MessageResources mResources) throws Exception {

		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();

		return UserShopLogic.printDetail(response, request, userShopForm,
				action, mResources);
	}

	/**
	 * This method is used to add shopping list items to cart
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
	public static ActionErrors addToCart(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors errors = new ActionErrors();
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		UserShopForm userShopForm = form.getUserShopForm();
		form.setSelectedShoppingList(editOrderGuideForm.getOrderGuideId());
		String[] quantityS = editOrderGuideForm.getQuantity();
		String[] quantity = new String[quantityS.length];
		System.arraycopy(quantityS, 0, quantity, 0, quantityS.length);
		for(int i = 0;i < quantityS.length;i++){
			if(quantityS[i].equals("0")){
				quantityS[i]="";
			}
		}
		userShopForm.setCartItems(editOrderGuideForm.getItems());
		userShopForm.setItemIds(editOrderGuideForm.getItemIds());
		userShopForm.setQuantity(quantityS);
		//count items in user shopping lists.
		SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
		Map<Integer,Integer> userShoppingList = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
		sessionData.setUserShoppingListIds(userShoppingList);
		
		// Following call is used to add items to cart
		errors = UserShopLogic.addToCart(request, userShopForm);
		editOrderGuideForm.setQuantity(quantity);
		return errors;
	}

	public static ActionErrors showItemDetails(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors ae = new ActionErrors();
        UserShopForm userShopForm = form.getUserShopForm();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        userShopForm.setAppUser(appUser);
        String source = request.getParameter("source");        
        if (Utility.isSet(source) && source.equals("order")) {
             ae = UserShopLogic.itemOrder(request, userShopForm);
        }
        else {
        	ae = UserShopLogic.item(request, userShopForm);
        }
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content contentBean = factory.getContentAPI();
        
        //Find Storage type of the product item  (E3 or Database) and store it in the Action Form: Begin
        String msdsUrl = StringUtils.EMPTY;
        String dedUrl = StringUtils.EMPTY;        
        String specUrl = StringUtils.EMPTY;
        
        if (userShopForm.getItemDetail() != null && userShopForm.getItemDetail().getProduct() != null) {
        	msdsUrl = userShopForm.getItemDetail().getProduct().getMsds();
        	dedUrl = userShopForm.getItemDetail().getProduct().getDed();        
        	specUrl = userShopForm.getItemDetail().getProduct().getSpec();
        }
        log.debug("msdsUrl = " + msdsUrl); //should I add "." to msdsUrl, etc. or not ?
        log.debug("dedUrl = " + dedUrl);
        log.debug("specUrl = " + specUrl);
        if (Utility.isSet(msdsUrl)) {
        	msdsUrl = "." + msdsUrl;
        	ContentData contentMsdsData = contentBean.getContent(msdsUrl);
			if(contentMsdsData != null){
        		form.setMsdsStorageTypeCd(contentMsdsData.getStorageTypeCd());
			}
        }
        if (Utility.isSet(dedUrl)) {
        	dedUrl = "." + dedUrl;
        	ContentData contentDedData = contentBean.getContent(dedUrl);
			if (contentDedData != null){
        		form.setDedStorageTypeCd(contentDedData.getStorageTypeCd());
			}
        }
        if (Utility.isSet(specUrl)) {
        	specUrl = "." + specUrl;
        	ContentData contentSpecData = contentBean.getContent(specUrl);
			if (contentSpecData != null ){
        		form.setSpecStorageTypeCd(contentSpecData.getStorageTypeCd());
			}
        }
        //Find Storage type of the product item  (E3 or Database) and store it in the Action Form: End      
	
        return ae;
	}

	public static ActionErrors showItemDocumentFromE3Storage(HttpServletRequest request,
			UserShopForm userShopForm, HttpServletResponse response) throws Exception {
		
		ActionErrors errors = new ActionErrors();
		
        CleanwiseUser appUser = (CleanwiseUser) request.getSession()
                .getAttribute(Constants.APP_USER);
        userShopForm.setAppUser(appUser);
        String path = request.getParameter("path");
        log.info("pathInit = " + path);
        
        //find contentName in the clw_content table based on the path; 
        //clw_content table: path is unique for a combination of "type of the item + item id"
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content cont = factory.getContentAPI();
        
    	//add starting "." to parameter "path"
        path = "." + path;
        log.info("pathFinal = " + path);        
        
        ContentData contentD = cont.getContent(path);
        String contentName = contentD.getContentSystemRef();
        log.info("contentName = " + contentName);       
        //Check for E3 Storage Data: 
        //if we have JPG, GIF, PDF, GIF, DOC, or DOCX type document for a product item,
        //read it
        log.info("Get E3 Storage Data or document (image) from the server => BEGIN");              
        if (null == contentName || contentName.trim().equals("")) {
        	log.info("Document " + path + " was not found in E3 Storage");
        	String errorMess = "Document " + path + " was not found in E3 Storage";
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	return errors; 
        }
        InputStream is = cont.getContentDataFromE3StorageSystem(contentName);
        log.info("is = " + is); //it should NOT be NULL
        
        //convert InputStream to byte[] Array
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = is.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); 
                //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset 
                off to this byte array output stream.
                */
                //log.info("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	log.info("Error reading Document as ByteArray from the E3 Storage");
        	String errorMess = "Error reading Document from the E3 Storage";
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	return errors;
        }
        byte[] bytes = bos.toByteArray();
        //"bytes" is the ByteArray we need
        log.info("response = " + response);
        
        if(response != null) {
        	if (contentD.getPath() != null && contentD.getPath().endsWith(".pdf")) { //.pdf file
        	    response.setContentType( "application/pdf" );
        	    response.setHeader("cache-control", "no-cache");
        	    response.setHeader("Pragma", "public");        	
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".gif")){ //.gif file: sometimes it doesn't work      	        	
                response.setContentType("image/gif");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".jpg")){ //.jpg file  
                response.setContentType("image/jpeg");                
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".doc")){ //.doc file (MSWord)     	        	
                response.setContentType("application/msword");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".docx")){ //.docx file (MSWord 2007)     	        	
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else {
        	    String errorMess = "Cannot load the Document " + contentD.getPath() + " : unknown type";
        	    log.info(errorMess);
        	    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return errors;
            }
	        response.getOutputStream().write(bytes);
 
        }
        log.info("Get E3 Storage Data or document (image) from the server => END"); 
        return errors; 
	}

	public static ActionErrors showItemDocumentFromDb(HttpServletRequest request,
			UserShopForm userShopForm, HttpServletResponse response) throws Exception {
		
		ActionErrors errors = new ActionErrors();
		
        CleanwiseUser appUser = (CleanwiseUser) request.getSession()
                .getAttribute(Constants.APP_USER);
        userShopForm.setAppUser(appUser);
        String path = request.getParameter("path");
        log.info("pathInit = " + path);
        
        //find contentName in the clw_content table based on the path; 
        //clw_content table: path is unique for a combination of "type of the item + item id"
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content cont = factory.getContentAPI();
        
    	//add starting "." to parameter "path"
        path = "." + path;
        log.info("pathFinal = " + path);        

        ContentData contentD = cont.getContent(path);
        byte[] documentBinaryData = contentD.getBinaryData();
        
        log.info("Get Document (image, etc.) from the Database => BEGIN");  
        //Check for E3 Storage Data: 
        //if we have JPG, GIF, PDF, GIF, DOC, or DOCX type document for a product item,
        //read it
        log.info("Get E3 Storage Data or document (image) from the server => BEGIN");              
        if (null == documentBinaryData) {
        	log.info("Binary data for the document " + path + " was not found in the Database");
        	String errorMess = "Binary data for the document " + path + " was not found in the Database";
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	return errors; 
        }
        
        //Convert byte[] array to InputStream
        InputStream is = new ByteArrayInputStream(documentBinaryData);
        log.info("is = " + is); //it should NOT be NULL        
        
        //convert InputStream to byte[] Array        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = is.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); 
                //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset 
                off to this byte array output stream.
                */
                //log.info("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        	log.info("Error reading Document as ByteArray from the Database");
        	String errorMess = "Error reading Document from the Database";
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	return errors;
        }
        byte[] bytes = bos.toByteArray();
        //"bytes" is the ByteArray we need
        log.info("response = " + response);
        
        if(response != null) {
        	if (contentD.getPath() != null && contentD.getPath().endsWith(".pdf")) { //.pdf file
        	    response.setContentType( "application/pdf" );
        	    response.setHeader("cache-control", "no-cache");
        	    response.setHeader("Pragma", "public");        	
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".gif")){ //.gif file: sometimes it doesn't work      	        	
                response.setContentType("image/gif");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".jpg")){ //.jpg file  
                response.setContentType("image/jpeg");                
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".doc")){ //.doc file (MSWord)     	        	
                response.setContentType("application/msword");
            } else if (contentD.getPath() != null && contentD.getPath().endsWith(".docx")){ //.docx file (MSWord 2007)     	        	
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else {
        	    String errorMess = "Cannot load the Document " + contentD.getPath() + " : unknown type";
        	    log.info(errorMess);
        	    errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return errors;
            }
	        response.getOutputStream().write(bytes);
 
        }
        log.info("Get Document (image, etc.) from the Database => END");
        return errors; 
	}
	
	public static ActionErrors itemToCart(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();

		// Begin - TODO:
		userShopForm.setItemId(form.getItemId());
		userShopForm.setQuantityDetail(form.getQuantityDetail());
		// End - TODO:

		ActionErrors errors = UserShopLogic.itemToCart(request, userShopForm);
		if(errors==null || errors.isEmpty()){
            List itemMessages = ShopTool.getCurrentShoppingCart(request).getItemMessages();
            if (!Utility.isSet(itemMessages)) {
            	userShopForm.setConfirmMessage(ClwI18nUtil.getMessage(request, "shoppingCart.text.actionMessage.itemAdded", null));
            }
            else {
            	userShopForm.setConfirmMessage(null);
            }
            // set 0 to qty
            userShopForm.setQuantityDetail("");
            form.setQuantityDetail("");
            userShopForm.setQuantity(new String[0]);
		}
		return errors;
	}

    public static ActionErrors itemToParOrder(HttpServletRequest request,
            ShoppingForm form) throws Exception {
        ActionErrors errors = new ActionErrors();
        UserShopForm userShopForm = form.getUserShopForm();
        ShoppingCartItemData itemData = userShopForm.getItemDetail();
        ShoppingCartItemDataVector cartItems = new ShoppingCartItemDataVector();
        cartItems.add(itemData);
        userShopForm.setCartItems(cartItems);
        userShopForm.setItemIds(new int[] { form.getItemId() });
        userShopForm.setQuantity(new String[] { form.getQuantityDetail() });

		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		AccountData account = appUser.getUserAccount();
		boolean allowOrdrInvItems = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS),false);
		
		List<String> parItemSkuList = new ArrayList<String>();
		for(int i = 0 ;cartItems != null && i < cartItems.size();i++){
			ShoppingCartItemData scid = (ShoppingCartItemData)cartItems.get(i);
			
			if(scid.getIsaInventoryItem() && !allowOrdrInvItems) {
				if(!parItemSkuList.contains(scid.getActualSkuNum())) {
					parItemSkuList.add(scid.getActualSkuNum());
				}
			} 
		
		}
		
		if(Utility.isSet(parItemSkuList)) {
			Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(parItemSkuList);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.parItemExistsInCorporateOrder", param);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
		}
        errors = UserShopLogic.addToInventoryCart(request, userShopForm);
        return errors;
    }
	
	/**
	 * Retrieves items pertaining to the selected shopping list.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
    public static ActionErrors getSelectedShoppingListItems(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		sessionDataUtil.setRememberShoppingList(editOrderGuideForm.getOrderGuideId());
		form.setSelectedShoppingList(editOrderGuideForm.getOrderGuideId());
		//following call is made to count items in user shopping lists.
		SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
	
		Map<Integer,Integer> userShoppingList = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
		sessionData.setUserShoppingListIds(userShoppingList);

		// Following call is made to retrieve user order guide items.
		return selectShoppingList(request,form);
	}
	
    public static ActionErrors selectShoppingList(HttpServletRequest request, ShoppingForm form)
    throws Exception
    {
      ActionErrors ae = new ActionErrors();
      EditOrderGuideForm pForm = form.getEditOrderGuideForm();
      int orderGuideId = pForm.getOrderGuideId();
      if(orderGuideId<=0) {
        String errorMess=ClwI18nUtil.getMessage(request,"shop.errors.noOrderGuideSelected",null);
        ae.add("error",new ActionError("error.simpleGenericError",errorMess));
        return ae;
      }

      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if(factory==null) {
        ae.add("error",new ActionError("error.systemError","No Ejb access"));
        return ae;
      }
      ShoppingServices shoppingServEjb = null;
      try {
        shoppingServEjb = factory.getShoppingServicesAPI();
      } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
        ae.add("error",new ActionError("error.systemError","No shopping services Ejb pointer"));
        return ae;
      }
      //
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
      if(catalogIdI==null) {
        ae.add("error",new ActionError("error.systemError","No "+Constants.CATALOG_ID+"session object found"));
        return ae;
      }
      Integer contractIdI = (Integer) session.getAttribute(Constants.CONTRACT_ID);
      if(contractIdI==null) {
         contractIdI = new Integer(0);
      }
      StoreData store = appUser.getUserStore();
      if(store==null) {
        ae.add("error",new ActionError("error.systemError","No store information was loaded"));
        return ae;
      }
      PropertyData storeTypeProperty = store.getStoreType();
      if(storeTypeProperty==null) {
        ae.add("error",new ActionError("error.systemError","No store type information was loaded"));
        return ae;
      }

      OrderGuide orderGuideEjb = null;
      try {
        orderGuideEjb = factory.getOrderGuideAPI();
      } catch(com.cleanwise.service.api.APIServiceAccessException exc) {
        ae.add("error",new ActionError("error.systemError","No order guide Ejb pointer"));
        return ae;
      }
      OrderGuideData orderGuideD = null;
      try {
        orderGuideD = orderGuideEjb.getOrderGuide(orderGuideId);
      } catch(Exception exc) {
        ae.add("error",new ActionError("error.systemError","Can't get order guide object"));
        return ae;
      }

      int pOrder = Constants.ORDER_BY_CATEGORY;
      if(orderGuideD.getOrderGuideTypeCd().equals(RefCodeNames.ORDER_GUIDE_TYPE_CD.CUSTOM_ORDER_GUIDE)){
    	  pOrder = Constants.ORDER_BY_CUSTOM_CATEGORY;
      }
      
      Properties configProps = ClwCustomizer.getUiProperties(request);
      String cfgSortBy = configProps.getProperty("orderGuideSecondarySorting");

      if (cfgSortBy != null && cfgSortBy.equalsIgnoreCase("ORDER_BY_CATEGORY_AND_SKU")) {
    	  pOrder = Constants.ORDER_BY_CATEGORY_AND_SKU;
      }
      
      ShoppingCartItemDataVector shoppingCartDV = null;
      try {
        SiteData site = appUser.getSite();
        shoppingCartDV = shoppingServEjb.getOrderGuideItems(storeTypeProperty.getValue(),
      		  site,
      		  orderGuideId,
      		  ShopTool.createShoppingItemRequest(request),
      		  pOrder,
      		  SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));

        /* TODO  add group items 
        if (shoppingCartDV!= null && isTemplateOrderGuide( request, form) ){
        	shoppingCartDV =prepareMultiProductGroupItems( request, shoppingCartDV, catalogIdI.intValue(), site.getSiteId() );
        }
        */
        shoppingCartDV = shoppingServEjb.sortShoppingCardItems(pOrder, shoppingCartDV);
      } catch(Exception exc) {
        ae.add("error",new ActionError("error.systemError","Can't load order guide from database 2"));
        return ae;
      }
      
      List items = new ArrayList();
      String[] quantityS = new String[shoppingCartDV.size()];
      int[] categoryIds = new int[shoppingCartDV.size()];
      for(int ii=0; ii<shoppingCartDV.size(); ii++) {
        ShoppingCartItemData sciD = ((ShoppingCartItemData) shoppingCartDV.get(ii)).copy();
        sciD.setOrderNumber(ii+1);
        items.add(sciD);
        quantityS[ii] = ""+sciD.getQuantity();
        CatalogCategoryData ccD = sciD.getCategory();
        if(ccD==null) {
          CatalogCategoryDataVector ccDV = (CatalogCategoryDataVector) sciD.getProduct().getCatalogCategories();
          if(ccDV!=null && ccDV.size()>0) {
            ccD = (CatalogCategoryData)ccDV.get(0);
            sciD.setCategory(ccD);
          }
        }
        categoryIds[ii] = (ccD!=null)?ccD.getCatalogCategoryId():-1;
      }
      

     
      pForm.setItems(items);
      pForm.setQuantity(quantityS);
      pForm.setItemIds(new int[quantityS.length]);
      pForm.setOrderNumbers(new int[quantityS.length]);
      pForm.setCategoryIds(categoryIds);
     
      pForm.setOrderGuideId(orderGuideD.getOrderGuideId());
      pForm.setOrderGuide(orderGuideD);
      pForm.setShortDesc(orderGuideD.getShortDesc());
      pForm.setSelectBox(new long[0]);
      pForm.setChangesFlag(false);
      
      return ae;
    }
	/**
	 * This method deletes a a specific order guide selected from the left panel
	 * of shopping list UI
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
	public static ActionErrors deleteShoppingList(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors errors = null;
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		//check whether schedules created for shopping list.
		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		SiteData siteData = appUser.getSite();
		int accountId = siteData.getAccountId();
		int siteId = siteData.getSiteId();
		try{
			AutoOrder autoOrderBean = APIAccess.getAPIAccess().getAutoOrderAPI();
			OrderScheduleViewVector orderScheduleViewVector = 
			autoOrderBean.getOrderSchedules(accountId, siteId, null, null, null, null, null,0);
			SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
			Map<Integer,Integer> userShoppingList;
			
			for(int i = 0;orderScheduleViewVector!=null && i < orderScheduleViewVector.size();i++){
				OrderScheduleView orderScheduleView = (OrderScheduleView)orderScheduleViewVector.get(i);
				if(orderScheduleView.getOrderGuideId() == editOrderGuideForm.getInputOrderGuideId()){
					userShoppingList = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
					sessionData.setUserShoppingListIds(userShoppingList);
					
					errors = new ActionErrors();
					String errorMess = ClwI18nUtil.getMessage(request,"shoppingList.error.shoppingListIsScheduled", null);
					errors.add("error", new ActionMessage("message.simpleMessage",errorMess));
					return errors;
			    }
		   }
		}
		catch (Exception e) {
        	log.error("Exception occurred while deleting shopping list");
        	errors = new ActionErrors();
            String errorMess = ClwI18nUtil.getMessage(request, "shoppingList.error.problemDeletingShoppingList", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
		}
		// following call is made to delete a user shopping list. 
		return EditOrderGuideLogic.deleteOrderGuide(request, editOrderGuideForm);
	}

	/**
	 * This method renames user order guides
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
	public static ActionErrors renameShoppingList(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors errors = null;
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		// As we are not using select check boxes in new UI, we need to set it to of length 0;
		int[] userOgIds = form.getUserOrdGuideIds();
		String[] orderGuideNames = form.getOrderGuideNames();
		// As the user is allowed to rename 'n' number of user shopping lists
		// we will be iterating and renaming user shopping lists.
		for (int i = 0; i < userOgIds.length; i++) {
			editOrderGuideForm.setOrderGuideId(userOgIds[i]);
			EditOrderGuideLogic.select(request, editOrderGuideForm);
			editOrderGuideForm.setInputOrderGuideId(userOgIds[i]);
			editOrderGuideForm.setShortDesc(orderGuideNames[i]);
			errors = EditOrderGuideLogic.save(request,editOrderGuideForm);
			if(errors!=null && errors.size() > 0){
				return errors;
			}
		}
		return errors;
	}
	/**
	 * creates user shopping list.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 */
	public static ActionErrors createShoppingList(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors errors = new ActionErrors();
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		//following call is made to count items in user shopping lists.
		SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
		Map<Integer,Integer> userShoppingList = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
		sessionData.setUserShoppingListIds(userShoppingList);
		
		//Check for uniqueness of the name(case sensitive)
		OrderGuideDataVector shoppingLists = editOrderGuideForm.getUserOrderGuides();
        if (shoppingLists != null) {
        	int i = 0;
            String shoppingListName = editOrderGuideForm.getShortDesc();
            for (; i < shoppingLists.size(); i++) {
                OrderGuideData ogD = (OrderGuideData) shoppingLists.get(i);
                String shoppingListDesc = ogD.getShortDesc();
                if (shoppingListDesc != null && shoppingListDesc.trim().equalsIgnoreCase(shoppingListName)) {
                    break;
                }
            }
            if (i < shoppingLists.size()) {
                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.nameExists", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return errors;
            }
        }
		// the following call is made to create a new shopping list.
		return ShoppingLogic.createUserShoppingList(request, editOrderGuideForm);
	}
	
	/**
	 * Prepares the data for the Shopping lists.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 */
	public static ActionErrors shopByLists(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors errors = null;
		CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		SiteData location = user.getSite();
		if(location == null){
			errors = new ActionErrors();
			String errorMess = ClwI18nUtil.getMessage(request,"error.noLocationSelected",null);
            errors.add("error",new ActionError("error.simpleGenericError",errorMess));
            return errors;
		}
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		// Following call is made to initialize the order guides.
		errors =  UserShopLogic.initOrderGuide(request, userShopForm);
		errors = EditOrderGuideLogic.init(request, editOrderGuideForm);
		
		//Template order guides + Custom order guides
		OrderGuideDataVector templateOrderGuides = new OrderGuideDataVector();
		if(Utility.isSet(userShopForm.getTemplateOrderGuides())){
			templateOrderGuides.addAll(userShopForm.getTemplateOrderGuides());
		}
		if(Utility.isSet(userShopForm.getCustomOrderGuides())){
			templateOrderGuides.addAll(userShopForm.getCustomOrderGuides());
		}
		DisplayListSort.sort(templateOrderGuides,"short_desc");
		userShopForm.setTemplateOrderGuides(templateOrderGuides);
			
		//User order guides
		OrderGuideDataVector shoppingListsDV = editOrderGuideForm.getUserOrderGuides();
		DisplayListSort.sort(shoppingListsDV,"short_desc");
		editOrderGuideForm.setUserOrderGuides(shoppingListsDV);

		//check if any shopping list has been previously selected by the user in his current session.
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		int shoppingListId = sessionDataUtil.getRememberShoppingList();
		if(shoppingListId > 0){
			editOrderGuideForm.setOrderGuideId(shoppingListId);
			userShopForm.setOrderGuideId(shoppingListId);
			return getSelectedShoppingListItems(request, form);
		}
		else
		if (templateOrderGuides.size()>0) {
			Map<Integer,Integer> templateShoppingListIds = countItemsInShoppingLists(request, templateOrderGuides,userShopForm.getOrderBy());
			sessionDataUtil.setTemplateShoppingListIds(templateShoppingListIds);
			shoppingListId = ((OrderGuideData) userShopForm.getTemplateOrderGuides().get(0)).getOrderGuideId();
			editOrderGuideForm.setOrderGuideId(shoppingListId);
			return getSelectedShoppingListItems(request, form);
		}
		else 
			if (shoppingListsDV != null && shoppingListsDV.size() > 0) {
			 shoppingListId = ((OrderGuideData) shoppingListsDV.get(0)).getOrderGuideId();
			 editOrderGuideForm.setOrderGuideId(shoppingListId);
			 return getSelectedShoppingListItems(request, form);
			} 
			else 
			{
			 editOrderGuideForm.setItems(new ArrayList());
			 sessionDataUtil.setTemplateShoppingListIds(null);
			 sessionDataUtil.setUserShoppingListIds(null);
			}
		return errors;
	}
	/*
	 * Private method to count number of items in user order guides.
	 */
	private static Map<Integer,Integer> countItemsInShoppingLists(HttpServletRequest request,
			OrderGuideDataVector shoppingListDV,int orderBy) {
		HttpSession session = request.getSession();
		Map<Integer,Integer> numberOfItemsInShoppingLists = new HashMap<Integer,Integer>();
		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		StoreData store = appUser.getUserStore();
		PropertyData storeTypeProperty = store.getStoreType();
		try {
			int orderGuideId = 0;
			for (int i = 0; shoppingListDV!=null && i < shoppingListDV.size(); i++) {
				orderGuideId = ((OrderGuideData) shoppingListDV.get(i)).getOrderGuideId();
				int count = APIAccess.getAPIAccess().getShoppingServicesAPI().getOrderGuideItemCount(orderGuideId, 
						ShopTool.createShoppingItemRequest(request));
				// put count of items and it's associated order guide id in
				// the numberOfItemsInOGMap map.
				numberOfItemsInShoppingLists.put(orderGuideId, count);
			}
			// set the map in request scope,so that it can be used to display
			// user order guide items count.
		} catch (Exception e) {
			log.error("Exception occured while trying to get count of shopping list items.");
		}
		return numberOfItemsInShoppingLists;
	}
	
	/**
	 * This method is used to update item quantities
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
	public static ActionErrors updateQuantity(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors errors = new ActionErrors();
		// Get the existing EditOrderGuideForm and pass it on to the existing Logic
		// method.
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		UserShopForm userShopForm = form.getUserShopForm();
		form.setSelectedShoppingList(editOrderGuideForm.getOrderGuideId());
		if (userShopForm.getTemplateOrderGuideIds() != null && userShopForm.getTemplateOrderGuideIds().size() > 0) {
			ArrayList templateShoppingListIds = userShopForm.getTemplateOrderGuideIds();
			for (int i = 0; templateShoppingListIds!=null && i < templateShoppingListIds.size(); i++) {
				if (templateShoppingListIds.get(i).equals(String.valueOf(editOrderGuideForm.getOrderGuideId()))) {
					return new ActionErrors();
				}
			}
		}


		editOrderGuideForm.setInputOrderGuideId(editOrderGuideForm.getOrderGuideId());
		SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
		Map<Integer,Integer> userShoppingListIds = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
		sessionData.setUserShoppingListIds(userShoppingListIds);
		
		// STJ-6022 if all qty == 0 we need check shopping list is scheduled
		checkZeroQtyForScheduleList(editOrderGuideForm, request, errors);
		if (errors.size() > 0) {
            return errors;
        }

		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		if(sessionDataUtil.isActionProductSearch()){
            List productSearchItems = editOrderGuideForm.getItems();
            int[] itemIds = editOrderGuideForm.getItemIds();
            String[] quantity = sessionDataUtil.getQuantity();
            int[] itemsIds = sessionDataUtil.getItemIds();
            String[] q = editOrderGuideForm.getQuantity();
            int[] orderNums = sessionDataUtil.getOrderNumbers();
            int[] orderNumbers = editOrderGuideForm.getOrderNumbers();
			List items = editOrderGuideForm.getItemsAvailable();
			List searchItems = editOrderGuideForm.getItems();
			for(int i = 0; items != null && i < items.size();i++) {
				ShoppingCartItemData itemAvailable = (ShoppingCartItemData)items.get(i);
				for(int j = 0 ;searchItems != null && j < searchItems.size();j++){
					ShoppingCartItemData itemFound = (ShoppingCartItemData)searchItems.get(j);
					if(itemAvailable.getItemId() == itemFound.getItemId()) {
						quantity[i] = ((String[])editOrderGuideForm.getQuantity())[j];
						itemsIds[i] = ((int[])editOrderGuideForm.getItemIds())[j];
						orderNums[i] = ((int[])editOrderGuideForm.getOrderNumbers())[j];
					}
				}
			}



			editOrderGuideForm.setItems(editOrderGuideForm.getItemsAvailable());
			editOrderGuideForm.setItemIds(itemsIds);
			editOrderGuideForm.setQuantity(quantity);
			editOrderGuideForm.setOrderNumbers(orderNums);
			errors = EditOrderGuideLogic.save(request, editOrderGuideForm);
			editOrderGuideForm.setItems(productSearchItems);
			editOrderGuideForm.setItemIds(itemIds);
			editOrderGuideForm.setQuantity(q);
			editOrderGuideForm.setOrderNumbers(orderNumbers);
		}
		else{
			return EditOrderGuideLogic.save(request, editOrderGuideForm);
		}
		return errors;
	}

	
	/**
	 * Searches for items in current Shopping List.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 */
	 public static Map<String,Object> searchForShoppingListItem(HttpServletRequest request,
				ShoppingForm form) throws Exception {
	    	Map<String,Object> returnValue = new HashMap<String,Object>();
	    	ActionErrors errors = new ActionErrors();
	    	ActionMessages messages = new ActionMessages();
	    	returnValue.put(ActionErrors.GLOBAL_ERROR, errors);
	    	returnValue.put(ActionErrors.GLOBAL_MESSAGE, messages);
			// Get the existing UserShopForm and pass it on to the existing Logic
			// method.
			EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
			form.setSelectedShoppingList(editOrderGuideForm.getOrderGuideId());
			EditOrderGuideLogic.select(request, editOrderGuideForm);
			editOrderGuideForm.setItemsAvailable(editOrderGuideForm.getItems());
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			sessionDataUtil.setActionProductSearch(true);
			sessionDataUtil.setItemIds(editOrderGuideForm.getItemIds());
			sessionDataUtil.setQuantity(editOrderGuideForm.getQuantity());
			sessionDataUtil.setOrderNumbers(editOrderGuideForm.getOrderNumbers());
			
			Map<Integer,Integer> userShoppingListIds = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
			sessionDataUtil.setUserShoppingListIds(userShoppingListIds);
			
			String productSearchField = Utility.safeTrim(Utility.strNN(form.getProductSearchField()));
			String productSearchValue = Utility.safeTrim(Utility.strNN(form.getProductSearchValue()));
			//STJ-5666
			sessionDataUtil.setProductSearchField(productSearchField);
			sessionDataUtil.setProductSearchValue(productSearchValue);
			
			List shoppingListItems = editOrderGuideForm.getItems();
			ShoppingCartItemDataVector foundItems = new ShoppingCartItemDataVector();

        	boolean isGreenCertified = (form.getGreenCertified() != null && form.getGreenCertified().equalsIgnoreCase("on"));

			//if no search criteria was specified, return an error
			if (!Utility.isSet(productSearchField) || !Utility.isSet(productSearchValue)) {
			    if (isGreenCertified) {
                    // get all green certified products
					Iterator shoppingItemsIterator = shoppingListItems.iterator();
					while(shoppingItemsIterator.hasNext()){
						ShoppingCartItemData orderGuideItem = (ShoppingCartItemData)shoppingItemsIterator.next();
  						if(orderGuideItem.getProduct().getCertifiedCompanies() != null && orderGuideItem.getProduct().getCertifiedCompanies().size() > 0){
						    foundItems.add(orderGuideItem);
						}
                  }
                } else {
	        	    String errorMess = ClwI18nUtil.getMessage(request,"productSearch.error.noSearchCriteriaSpecified",null);
	        	    errors.add("error",new ActionError("error.simpleGenericError",errorMess));
	        	    return returnValue;
                }
			} else {
			    boolean searchBySku = false;
			    boolean searchByProductName = false;
			    boolean searchByManufacturer = false;
			    boolean searchByManufSku = false;
			    Pattern pattern = Pattern.compile(Pattern.quote(productSearchValue), Pattern.CASE_INSENSITIVE);
				if (productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_SKU) ||
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL)) {
				    searchBySku = true;
				}
				if (productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_PRODUCT_NAME)|| 
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL)) {
				    searchByProductName = true;
				}
				if (productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_MANUFACTURER)|| 
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL)) {
				    searchByManufacturer = true;
				}
				if (productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_MANUF_SKU) ||
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL)) {
				    searchByManufSku = true;
				}
				if(searchBySku || searchByProductName || searchByManufacturer || searchByManufSku) {
					Iterator shoppingItemsIterator = shoppingListItems.iterator();
					List<String> searchCandidates = new ArrayList<String>();
					while(shoppingItemsIterator.hasNext()){
						ShoppingCartItemData orderGuideItem = (ShoppingCartItemData)shoppingItemsIterator.next();
						if (isGreenCertified && (orderGuideItem.getProduct()
                                    .getCertifiedCompanies() == null
                                    || orderGuideItem.getProduct()
                                            .getCertifiedCompanies().size() == 0)) {
						    continue;
                        }
						searchCandidates.clear();
						if (searchBySku) {
						    searchCandidates.add(orderGuideItem.getActualSkuNum());
						}
						if (searchByProductName) {
                            searchCandidates.add(orderGuideItem.getProduct().getCatalogProductShortDesc());
                        }
						if (searchByManufacturer) {
                            searchCandidates.add(orderGuideItem.getProduct().getManufacturerName());
                        }
                        if (searchByManufSku) {
						    searchCandidates.add(orderGuideItem.getProduct().getManufacturerSku());
                        }
						for (String searchCandidate : searchCandidates) {
						    Matcher m = pattern.matcher(searchCandidate);
						    if (m.find()) {
						        foundItems.add(orderGuideItem);
						        break;
						    }
						}
					}
				}
            }
			editOrderGuideForm.setItems(foundItems);
			if(foundItems.size() == 0){
                String errorMess = ClwI18nUtil.getMessage(request,"shoppingList.errors.noProductsFound",null);
		        messages.add("message", new ActionMessage("message.simpleMessage", errorMess));
			}
			else {
			    String[] quantityS = new String[foundItems.size()];
				int[] itemIds = new int[foundItems.size()];
				int[] orderNums = new int[foundItems.size()];
				for(int i  = 0;i < foundItems.size();i++) {
				    ShoppingCartItemData scid = (ShoppingCartItemData)foundItems.get(i);
					quantityS[i] = scid.getQuantityString();
					itemIds[i] = scid.getItemId();
					orderNums[i] = scid.getOrderNumber();
				}
				editOrderGuideForm.setQuantity(quantityS);
				editOrderGuideForm.setItemIds(itemIds);
				editOrderGuideForm.setOrderNumbers(orderNums);
			}

			return returnValue;
		}

    /**
	 * Adds items from product catalog to existing shopping list.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 */
	public static ActionErrors addItemsToExistingShoppingList(
			HttpServletRequest request, ShoppingForm form) throws Exception {
		ActionErrors errors = new ActionErrors();
		CleanwiseUser appUser = (CleanwiseUser) request.getSession()
				.getAttribute(Constants.APP_USER);
		int userId = appUser.getUser().getUserId();
		if (appUser.getSite().isSetupForSharedOrderGuides()) {
			appUser.getUser().setUserId(0);
		}
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		form.setCatalogItemKey(sessionDataUtil.getPreviousCategory());
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		List catalogItems = userShopForm.getCartItems();
		String[] quantityS = userShopForm.getQuantity();
		int selectedShoppingListId = 0;
		try {
			selectedShoppingListId = Integer.parseInt(userShopForm
					.getSelectedShoppingListId());
			request.setAttribute("catalogItemKey",
					sessionDataUtil.getPreviousCategory());
			sessionDataUtil.setCurrentShoppingListId(selectedShoppingListId);
		} catch (NumberFormatException e) {
			appUser.getUser().setUserId(userId);
			String errorMess = ClwI18nUtil.getMessage(request,
					"shoppingList.errors.noShoppingListToAddItems", null);
			errors.add("error", new ActionError("error.simpleGenericError",
					errorMess));
			for (int i = 0; i < catalogItems.size(); i++) {
				ShoppingCartItemData item = (ShoppingCartItemData) catalogItems
						.get(i);
				item.setQuantityString(quantityS[i]);
			}
			return errors;
		}
		
//		long[] addSelected = userShopForm.getSelectBox();
		int[] itemsIds = userShopForm.getItemIds();
		long[] addSelected = getAddSelected(quantityS, itemsIds);
		log.info("addItemsToExistingShoppingList()===== OLD method: addSelected.length ="+ addSelected.length);
		
		String[] newQuantityS = new String[addSelected.length];
		int[] newItemsIds = new int[addSelected.length];
		ShoppingCartItemDataVector itemsToAdd = new ShoppingCartItemDataVector();

		for (int i = 0; addSelected != null && i < addSelected.length; i++) {
			for (int j = 0; catalogItems != null && j < catalogItems.size(); j++) {
				ShoppingCartItemData scid = (ShoppingCartItemData) catalogItems
						.get(j);
//				log.info("============= OLD method: j=" + j + ", addSelected[j] ="+ addSelected[j]);
				if(addSelected[i] == scid.getItemId()){
//					log.info("=============== OLD method: VALIDATION >> " + addSelected[j] +" == " +scid.getItemId());
					itemsToAdd.add(scid);
					newQuantityS[i] = quantityS[j];
					newItemsIds[i] = itemsIds[j];
					break;
				}
			}
		}
		if (itemsToAdd.size() == 0) { 
			String errorMess = ClwI18nUtil.getMessage(request,
					"productCatalog.errors.noItemSelected", null);
			errors.add("error", new ActionError("error.simpleGenericError",
					errorMess));
			appUser.getUser().setUserId(userId);
			return errors;
		}
		userShopForm.setCartItems(itemsToAdd);
		userShopForm.setQuantity(newQuantityS);
		userShopForm.setItemIds(newItemsIds);

		// the following call is made to add items to shopping list
		errors = UserShopLogic.updateUserOrderGuide(request, userShopForm);
		appUser.getUser().setUserId(userId);
		userShopForm.setCartItems(catalogItems);
		int k = 0;
		for (int i = 0; i < catalogItems.size(); i++) {
			ShoppingCartItemData item = (ShoppingCartItemData) catalogItems.get(i);
			if (!item.getProduct().isItemGroup()){
				log.info("addItemsToExistingShoppingList()=======> k >> " + k );
				item.setQuantityString(quantityS[k]);
				k++;
			}
		}
		OrderGuideDataVector userShoppingLists = editOrderGuideForm.getUserOrderGuides();
		DisplayListSort.sort(userShoppingLists, "short_desc");
		userShopForm.setUserOrderGuides(userShoppingLists);
		userShopForm.setSelectedShoppingListId(String.valueOf(selectedShoppingListId));
		return errors;
	}

    
    /**
	 * Creates a new shopping list from product catalog items.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 */
	 public static ActionErrors newUserShoppingList(HttpServletRequest request,
				ShoppingForm form) throws Exception {
	    	ActionErrors errors = new ActionErrors();
			// Get the existing UserShopForm and EditOrderGuideForm and pass it on to the existing Logic
			// method.
			UserShopForm userShopForm = form.getUserShopForm();
			//STJ-5090
			String[] quantity = userShopForm.getQuantity();
			boolean empty = true;
			for (int i=0; i<quantity.length; i++) {
				String obj = (String) quantity[i];
				if (Utility.isSet(obj)) {
					empty = false;
					break;
				}
			}
			if(quantity == null || quantity.length==0 || empty){
				quantity = form.getShoppingCartForm().getQuantity();
			}
			/*List<ShoppingCartItemData> cartItems = form.getShoppingCartForm().getCartItems();
			if(cartItems!=null){
				for(int i = 0 ;i < cartItems.size() ;i++){
					cartItems.get(i).setQuantityString(quantity[i]);
				}
				
			}*/
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
			String name = userShopForm.getOrderGuideName();
			userShopForm.setSelectedShoppingListId(String.valueOf(sessionDataUtil.getCurrentShoppingListId()));
	        if (name == null || name.trim().length() == 0) {
	            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.provideShoppingListName", null);
	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	            return errors;
	        }
	        //Check for uniqueness of the name(case sensitive)STJ - 5045.
			OrderGuideDataVector shoppingLists = editOrderGuideForm.getUserOrderGuides();
	        if (shoppingLists != null) {
	        	int i = 0;
	            //String shoppingListName = editOrderGuideForm.getShortDesc();
	            for (; i < shoppingLists.size(); i++) {
	                OrderGuideData ogD = (OrderGuideData) shoppingLists.get(i);
	                String shoppingListDesc = ogD.getShortDesc();
	                if (shoppingListDesc != null && shoppingListDesc.trim().equalsIgnoreCase(name)) {
	                    break;
	                }
	            }
	            if (i < shoppingLists.size()) {
	                String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.nameExists", null);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	                return errors;
	            }
	        }
	        editOrderGuideForm.setShortDesc(name);
			//the following call is made to create a new shopping list.
			errors = createUserShoppingList(request, editOrderGuideForm);
			if(errors.size() > 0){
				OrderGuideDataVector userShoppingLists = editOrderGuideForm.getUserOrderGuides();
				DisplayListSort.sort(userShoppingLists, "short_desc");
				return errors;
			}
			userShopForm.setSelectedShoppingListId(String.valueOf(editOrderGuideForm.getOrderGuideId()));
			EditOrderGuideLogic.init(request, editOrderGuideForm);
			sessionDataUtil.setCurrentShoppingListId(userShopForm.getOrderGuideId());
			OrderGuideDataVector userShoppingLists = editOrderGuideForm.getUserOrderGuides();
			DisplayListSort.sort(userShoppingLists, "short_desc");
			//List catalogItems = userShopForm.getCartItems();
			/*for (int i = 0; i < catalogItems.size(); i++) {
				ShoppingCartItemData item = (ShoppingCartItemData) catalogItems.get(i);
				item.setQuantityString(quantity[i]);
			}*/
			return errors;
		}
    
	 /**
	 * Searches for items in product catalog.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 */
	 public static Map<String,Object> searchForProductCatalogItem(HttpServletRequest request,
				ShoppingForm form) throws Exception {
	    	Map<String,Object> returnValue = new HashMap<String,Object>();
	    	ActionErrors errors = new ActionErrors();
	    	ActionMessages messages = new ActionMessages();
	    	returnValue.put(ActionErrors.GLOBAL_ERROR, errors);
	    	returnValue.put(ActionErrors.GLOBAL_MESSAGE, messages);
	    	
	        Integer catalogIdI =(Integer) request.getSession().getAttribute(Constants.CATALOG_ID);
	        if (catalogIdI == null) {
	  	        errors.add("error",
	  	                new ActionError("error.systemError",
	  	                "No " + Constants.CATALOG_ID +
	  	                " session attrbute found"));
	  	
	  	      return returnValue;
	        }
	        int catalogId = catalogIdI.intValue();

	    	// Get the existing UserShopForm and pass it on to the existing Logic
			// method.
			UserShopForm userShopForm = form.getUserShopForm();

			boolean isGreenCertified = (form.getGreenCertified() != null && form.getGreenCertified().equalsIgnoreCase("on"));
			userShopForm.setSearchGreenCertifiedFl(isGreenCertified);

			userShopForm.setQuantity(new String[0]);
			userShopForm.setSelectBox(new long[0]);
			String productSearchField = Utility.safeTrim(Utility.strNN(form.getProductSearchField()));
			String productSearchValue = Utility.safeTrim(Utility.strNN(form.getProductSearchValue()));
			//STJ-5666
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			sessionDataUtil.setProductSearchField(productSearchField);
			sessionDataUtil.setProductSearchValue(productSearchValue);
			
            productSearchValue = Utility.safeWildCards(productSearchValue);
			userShopForm.setSearchName("");
			userShopForm.setSearchSku("");
			userShopForm.setSearchManufacturer("");
			userShopForm.setCartItems(new ArrayList());
			
			
			//if no search criteria was specified, return an error
			if (!Utility.isSet(productSearchField) || !Utility.isSet(productSearchValue)) {
			    IdVector itemsFoundIds = new IdVector();
			    if (isGreenCertified) {
					errors = UserShopLogic.search(request,userShopForm);
					if (userShopForm.getProductList() != null) {
                        itemsFoundIds.addAll(userShopForm.getProductList());
                     	ProductDataVector products =prepareMultiProductGroupItems( request,  itemsFoundIds, catalogId );
                        userShopForm.setProductList(products);
                        errors = prepareCartItems(request, userShopForm);
                        if (errors.size() > 0) {
                            return returnValue;
                        }
                    }
					
               } else {
    	        	String errorMess = ClwI18nUtil.getMessage(request,"productSearch.error.noSearchCriteriaSpecified",null);
	            	errors.add("error",new ActionError("error.simpleGenericError",errorMess));
                }
			}
			else {
			    IdVector itemsFoundIds = new IdVector();
				if (productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_MANUF_SKU)||
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL)) {
					userShopForm.setSearchSkuType("Mfg.Sku");// if the search is based on mfg sku#
					userShopForm.setSearchSku(productSearchValue);
					errors = UserShopLogic.search(request,userShopForm);
					if (userShopForm.getProductList() != null) {
					    itemsFoundIds.addAll(userShopForm.getProductList());
					}
				}
				if ((productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_PRODUCT_NAME)|| 
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL))) {
					userShopForm.setSearchSku("");
					userShopForm.setSearchName(productSearchValue); // if the search is based on product name
					errors = UserShopLogic.search(request,userShopForm);
					if (userShopForm.getProductList() != null) {
                        itemsFoundIds.addAll(userShopForm.getProductList());
                    }
					
				}
				if ((productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_SKU)|| 
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL))) {
					userShopForm.setSearchName(""); 
					userShopForm.setSearchSkuType("Cust.Sku");// if the search is based on Cust.Sku
					userShopForm.setSearchSku(productSearchValue);
					errors = UserShopLogic.search(request,userShopForm);
					if (userShopForm.getProductList() != null) {
                        itemsFoundIds.addAll(userShopForm.getProductList());
                    }
				}
				if ((productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_MANUFACTURER)|| 
						productSearchField.equalsIgnoreCase(Constants.PRODUCT_SEARCH_VALUE_ALL))) {
					APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);
					Manufacturer manufacturerBean = null;
					if (factory == null) {
						errors.add("error", new ActionError("error.systemError","No Ejb access"));
						return returnValue;
					}
					try {
						manufacturerBean = factory.getManufacturerAPI();
					} catch (com.cleanwise.service.api.APIServiceAccessException exc) {
						errors.add("error", new ActionError("error.systemError","No shopping services Ejb pointer"));
						return returnValue;
					}
					CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
					IdVector storeIds = appUser.getUserStoreAsIdVector();
					int manufacturerId = 0;
					  BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
					  crit.setStoreBusEntityIds(storeIds);
					  crit.setSearchName(productSearchValue);
		              crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
		              ManufacturerDataVector manufacturerVector = manufacturerBean.getManufacturerByCriteria(crit);
					for(int i = 0;manufacturerVector != null && i < manufacturerVector.size();i++){
						manufacturerId = ((ManufacturerData)manufacturerVector.get(i)).getBusEntity().getBusEntityId();
						userShopForm.setSearchSku("");
						userShopForm.setSearchName("");
						userShopForm.setSearchManufacturer(String.valueOf(manufacturerId));
						UserShopLogic.search(request,userShopForm);
						if (userShopForm.getProductList() != null) {
	                        itemsFoundIds.addAll(userShopForm.getProductList());
	                    }
					}
				}
                if(itemsFoundIds.size()>0){
                	
                 	ProductDataVector products =prepareMultiProductGroupItems( request,  itemsFoundIds, catalogId );
                    userShopForm.setProductList(products);
                    errors = prepareCartItems(request, userShopForm);
                    if (errors.size() > 0) {
                        return returnValue;
                    }
            
                }
				//if no results were found, return a message to that effect
				if (userShopForm.getCartItemsSize() == 0) { 
					String errorMess = ClwI18nUtil.getMessage(request,"shoppingList.errors.noProductsFound",null);
		            messages.add("message", new ActionMessage("message.simpleMessage", errorMess));
				}
			}
			return returnValue;
		}
    
    /**
	 * This method is used to add product catalog items to cart
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
	 public static ActionErrors addCatalogItemsToCart(HttpServletRequest request,
				ShoppingForm form) throws Exception {
			ActionErrors errors = new ActionErrors();
			// Get the existing UserShopForm and pass it on to the existing Logic
			// method.
			UserShopForm userShopForm = form.getUserShopForm();
			//long[] addSelected = userShopForm.getSelectBox();

			List<Long> invalidQtySelected = new ArrayList<Long>();
			//userShopForm.setSelectBox(new long[0]);
			List catalogItems = userShopForm.getCartItems();
			String[] quantityS = userShopForm.getQuantity();
			ArrayList newQuantityS = new ArrayList();
			int[] itemsIds = userShopForm.getItemIds();

			long[] addSelected = getAddSelected(quantityS, itemsIds);

			log.info("addCatalogItemsToCart()===== OLD method:  addSelected.length ="+ addSelected.length);

			ArrayList<Integer> newItemsIds = new ArrayList<Integer>();
			List<String> validationList = new ArrayList<String>();
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			request.setAttribute("catalogItemKey",sessionDataUtil.getPreviousCategory());
			ShoppingCartItemDataVector itemsToAdd = new ShoppingCartItemDataVector();
			
//			log.info("============= OLD method: catalogItems.size()=" + catalogItems.size());
			for(int i = 0 ;catalogItems != null && i < catalogItems.size();i++)
			{
				ShoppingCartItemData scid = (ShoppingCartItemData)catalogItems.get(i);
//				log.info("============= OLD method: addSelected.length=" + addSelected.length);
				for(int j = 0;addSelected != null && j < addSelected.length;j++)
				{
//					log.info("============= OLD method: addSelected[j="+j+"] ="+ addSelected[j]);
					if(addSelected[j] == scid.getItemId())
//						log.info("=============== OLD method: VALIDATION >> " + addSelected[j] +" == " +scid.getItemId()+ ", quantityS[i="+i+"]"+ quantityS[i]);

					{
						try{
							if(Utility.isSet(quantityS[i]) && quantityS[i].trim().length()> 0)
							{
								int qty = Integer.parseInt(quantityS[i]);
								if(qty <= 0){
									invalidQtySelected.add((long) scid.getItemId());
									validationList.add(scid.getActualSkuNum());
								}
								else {
				                	//STJ-6069 - make sure that if this item is already in the cart that the new quantity added to the
				                	//old quantity doesn't exceed the max value for an Integer
							        HttpSession session = request.getSession();
							        ShoppingCartData cartD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
							        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
							        if (appUser == null) {
							            errors.add("error", new ActionError("error.systemError", "No session user found"));
							            return errors;
							        }
							        if (cartD == null) {
							            cartD = new ShoppingCartData();
							            cartD.setUser(appUser.getUser());
							        }
				                	Iterator<ShoppingCartItemData> shoppingCartItemIterator = cartD.getItems().iterator();
				                	boolean foundItem = false;
				                	while (!foundItem && shoppingCartItemIterator.hasNext()) {
				                		ShoppingCartItemData shoppingCartItem = shoppingCartItemIterator.next();
				                		foundItem = (scid.getItemId() == shoppingCartItem.getProduct().getItemData().getItemId());
				                		if (foundItem) {
				                			Long newQuantity = new Long(new Long(qty) + new Long(shoppingCartItem.getQuantity()));
				                			if (newQuantity > Integer.MAX_VALUE) {
				                	        	String[] args = new String[2];
				                	        	args[0] = Integer.MAX_VALUE + "";
				                	        	args[1] = scid.getActualSkuNum();
				                	            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.exceededMaxQtyMessage", args);
				                	            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
				                	            return errors;
				                			}
				                		}
				                	}
									//if we got to here it is safe to add the new quantity
									itemsToAdd.add(scid);
									newQuantityS.add(quantityS[i]);
									newItemsIds.add(itemsIds[i]);
									break;
								}
							}
						}
						catch (NumberFormatException exc) {
							invalidQtySelected.add((long) scid.getItemId());
							validationList.add(scid.getActualSkuNum());
			            }
					}
				}
			}
			//log.info("=============== OLD method: itemsToAdd()= " + itemsToAdd.toString());

			form.setCatalogItemKey(sessionDataUtil.getPreviousCategory());
			if(itemsToAdd.size() == 0 && validationList.size()== 0){
	        	String errorMess = ClwI18nUtil.getMessage(request,"productCatalog.errors.selectItemWithQuantity",null);
	        	errors.add("error",new ActionError("error.simpleGenericError",errorMess));
	            return errors;
	        }
			String []qtyS = new String[newQuantityS.size()];
			newQuantityS.toArray(qtyS);
			userShopForm.setCartItems(itemsToAdd);
			int[] itemIds = new int[newItemsIds.size()];
			 for(int i = 0;i < newItemsIds.size();i++){
				 itemIds[i] = (Integer)newItemsIds.get(i);
			 }
			userShopForm.setQuantity(qtyS);
			userShopForm.setItemIds(itemIds);
			log.info("=============== NEW method: itemIds()= " + itemIds.toString());
			// Following call is made to add items to cart.
			if(itemsToAdd.size() > 0)
			    errors = UserShopLogic.addToCart(request, userShopForm);
			userShopForm.setSelectBox(new long[0]);
			userShopForm.setCartItems(catalogItems);
			if (errors == null || errors.size()==0) {
                // STJ-6304: Previous invalid quantity values are displayed for 'Qty' field
                // need to clear quantities
                UserShopLogic.clearQuantities(userShopForm);
            }
			if(validationList.size() > 0 && !validationList.isEmpty()){
				// retain values entered in qty field, if validation fails 
				for(int i = 0;i < catalogItems.size();i++){
					for(int j = 0;j < invalidQtySelected.size();j++){
						ShoppingCartItemData item = (ShoppingCartItemData) catalogItems.get(i);
						if(item.getItemId() == invalidQtySelected.get(j)){
							item.setQuantityString(quantityS[i]);
							break;
						}
					}
				}
				 long[] select = new long[invalidQtySelected.size()];
				 for(int i = 0;i < invalidQtySelected.size();i++){
					 select[i] = (long)invalidQtySelected.get(i);
				 }
				 userShopForm.setSelectBox(select);
		        	Object[] param = new Object[1];
		        	param[0] = Utility.toCommaSting(validationList);
		            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
		            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
		        	return errors;
		    }
			return errors;
		}
	
	/**
	 * This method is used to find whether the current shopping list is a template shopping list.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
    public static boolean canEditShoppingList(HttpServletRequest request,
			ShoppingForm form) {
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
		Map<Integer,Integer> userShoppingListIds = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
		sessionData.setUserShoppingListIds(userShoppingListIds);
		
		if (userShopForm.getTemplateOrderGuideIds() != null && userShopForm.getTemplateOrderGuideIds().size() > 0) {
			ArrayList templateShoppingListIds = userShopForm.getTemplateOrderGuideIds();
			for (int i = 0; templateShoppingListIds!=null && i < templateShoppingListIds.size(); i++) {
				if (templateShoppingListIds.get(i).equals(String.valueOf(editOrderGuideForm.getOrderGuideId()))) {
					return false;
				}
			}
		}
		return true;
	}
	 /**
	 * This method is used to add Shopping List Items To Par Order 
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
	public static ActionErrors addShoppingListItemsToParOrder(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		ActionErrors errors = new ActionErrors();
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.

		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		form.setSelectedShoppingList(editOrderGuideForm.getOrderGuideId());
		UserShopForm userShopForm = new UserShopForm();
		userShopForm.setCartItems(editOrderGuideForm.getItems());
		
		List cartItems= editOrderGuideForm.getItems();
		String[] qtys= editOrderGuideForm.getQuantity();
		
		userShopForm.setItemIds(editOrderGuideForm.getItemIds());
		userShopForm.setQuantity(editOrderGuideForm.getQuantity());
        
		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		AccountData account = appUser.getUserAccount();
		boolean allowOrdrInvItems = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS),false);
		List<String> parItemSkuList = new ArrayList<String>();
        
        for(int i=0; i<cartItems.size(); i++){
        	ShoppingCartItemData scid = (ShoppingCartItemData)cartItems.get(i);
        	
        	if(qtys[i]!=null && qtys[i].trim().length()>0){
        		if(scid.getIsaInventoryItem() && !allowOrdrInvItems) {
        			if(!parItemSkuList.contains(scid.getActualSkuNum())) {
        				parItemSkuList.add(scid.getActualSkuNum());
        			}
        		} 
        	}
	        	
		}
		
		if(Utility.isSet(parItemSkuList)) {
			Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(parItemSkuList);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.parItemExistsInCorporateOrder", param);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
		}
		
		// Following call is used to add items to cart
		return UserShopLogic.addToInventoryCart(request, userShopForm);
	}


    /**
     * Calculates the release date and order close time in days and sets it in the Shopping form.
     * @param request
     * @param form
     */
    private static void setParOrderDateTimeInfo(HttpServletRequest request, ShoppingForm form){
    	
    	//Calculate the release date for a PAR Order.
		SiteData siteData = ShopTool.getCurrentSite(request);
		PropertyData cutOffDateProp = siteData.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_DATE);
		PropertyData cutOffTimeProp = siteData.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.NEXT_ORDER_CUTOFF_TIME);
		DateFormat df = DateFormat.getDateInstance();
		
		//next order cut-off date
		Date nextCutOffDate = null;
		
		if(cutOffDateProp!=null){   		
			try {
				nextCutOffDate = df.parse(cutOffDateProp.getValue());
			} catch (ParseException e) {
				log.error("An Exception has occured while parsing Next order cut off date, Exception : "+e.getMessage());
			}      	
		}
		
		//next order cut-off time
		String nextCutOffTime = null;
		
		if(cutOffTimeProp!=null){   		
			nextCutOffTime = cutOffTimeProp.getValue();  	
		}
		
		StringBuilder releaseDateTime = new StringBuilder();
		releaseDateTime.append(ClwI18nUtil.formatDateInp(request,nextCutOffDate));
		releaseDateTime.append(" ");
		releaseDateTime.append(nextCutOffTime);
		
		form.setOrderReleaseDate(releaseDateTime.toString());
		
		//set order Interval
		long daysBetween = ClwI18nUtil.calculateDaysBetween(request,new Date(),nextCutOffDate);
		String orderInterval = "";
		if(daysBetween==0) {
			orderInterval = ClwI18nUtil.getMessage(request, "shoppingCart.text.closesToday", null);
		} else if(daysBetween==1){
			Object params[] = new Object[1];
			params[0] = String.valueOf(daysBetween);
			orderInterval = ClwI18nUtil.getMessage(request, "shoppingCart.text.closesInDay", params);
		} else {
			Object params[] = new Object[1];
			params[0] = String.valueOf(daysBetween);
			orderInterval = ClwI18nUtil.getMessage(request, "shoppingCart.text.closesInDays", params);
		}
		
		form.setOrderClosesInDays(orderInterval);
    }
    
    /**
     * Validates Quantities of ShoppingCartItems.
     * @param request - HttpServletRequest
	 * @param cartForm - ShoppingForm
     * @return
     */
    public static ActionErrors validateQuantities(HttpServletRequest request, ShoppingCartForm cartForm, boolean isParOrder) {
    	ActionErrors errors = new ActionErrors();
        List cartItems = cartForm.getCartItems();
        for (int i = 0; cartItems != null && i < cartItems.size(); i++) {
            ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(i);
            
            //Validate Order Quantities
            String quantity = Utility.strNN(cartItem.getQuantityString());
            if (quantity.trim().length() > 0) {
            	if( ! Utility.isValidNumber(quantity)){
                	if(errors==null) {
                		errors = new ActionErrors();
                	}
                	Object[] param = new Object[2];
                	param[0] = quantity;
                    param[1] = cartItem.getActualSkuNum();
                    String errorMess = ClwI18nUtil.getMessage(request, "shoppingCart.error.message.wrongOrderQuantity", param);
                    errors.add("error" + i, new ActionError("error.simpleGenericError", errorMess));
                }
            }
            
            //Validate On Hand Quantities.
            if (isParOrder && cartItem.getIsaInventoryItem()) {
            	 String qtyOnHand = Utility.strNN(cartItem.getInventoryQtyOnHandString());
        		 if( qtyOnHand.trim().length() > 0 ){
        			if(!Utility.isValidNumber(qtyOnHand) || !Utility.isInteger(qtyOnHand)) {
        				Object[] param = new Object[2];
                     	param[0] = qtyOnHand;
                        param[1] = cartItem.getActualSkuNum();
                        String errorMess = ClwI18nUtil.getMessage(request, "shoppingCart.error.message.wrongOnHandQuantity", param);
                        errors.add("error" + i, new ActionError("error.simpleGenericError", errorMess));
        			} 
            	 } 
            }
        }
        
    	return errors;
    }
    
    public static ActionMessages getPAROrderInfoMessages(HttpServletRequest request,ShoppingCartForm cartForm) {
    	ActionMessages messages = new ActionMessages();
    	List cartItems = cartForm.getCartItems();
        for (int i = 0; cartItems != null && i < cartItems.size(); i++) {
            ShoppingCartItemData cartItem = (ShoppingCartItemData) cartItems.get(i);
            if (cartItem.getIsaInventoryItem()) {
           	 	String qtyOnHandStr = Utility.strNN(cartItem.getInventoryQtyOnHandString());
           	 	//STJ-4771: If On Hand Quantity is not entered. Set Qty = 0.
           	 	int qty = 0;
           	 	String infoMsg = "";
           	 	if( qtyOnHandStr.trim().length() <= 0 ){
           	 		
	           	 	if(cartItem.getAutoOrderEnable()) {
	           	 		//If on hand value is blank, Order qty should be set to Auto order Qty = factor * par value
	           	 		BigDecimal autoOrderFactor = cartItem.getAutoOrderFactor();
	           	 		qty = (int)(Math.round(autoOrderFactor.doubleValue() * cartItem.getInventoryParValue()));
	           	 	} 
	           	 	infoMsg = "shoppingCart.error.message.nothingForOnHand";
	           	 	cartItem.setInventoryQtyOnHand(0);
	           	 	cartItem.setInventoryQtyOnHandString("");
	           	 	
           	 	} else {
           	 		if(Utility.isValidNumber(qtyOnHandStr)) {
           	 			//STJ-4771: IF OnHand > PAR value
	    				if(Integer.parseInt(qtyOnHandStr) > cartItem.getInventoryParValue()) {
	    					//Set Qty = 0
	    					infoMsg = "shoppingCart.error.message.onHandIsGraterThanPar";
	    				} else {
	    					// Calculate Quantities
	    					 int qtyOnHand = Integer.parseInt(qtyOnHandStr);
	    					 cartItem.setInventoryQtyOnHand(qtyOnHand);
	    					 qty = cartItem.getInventoryParValue() - qtyOnHand;
	                         if (qty < 0) {
	                        	 qty = 0;
	                         }
	    				}
           	 		}
           	 	}
           	 	
				cartItem.setQuantity(qty);
				cartItem.setQuantityString(String.valueOf(qty));
				
           	 	if(Utility.isSet(infoMsg)) {
	           	 	Object[] param = new Object[2];
					param[0] = cartItem.getActualSkuNum();
					param[1] = String.valueOf(qty);
					String warningMsg = ClwI18nUtil.getMessage(request, infoMsg, param);
					messages.add("message", new ActionMessage("message.simpleMessage", warningMsg));
           	 	}
           	 		
            } //end: if Inv Item
        }
        return messages;
    }

    /**
	 * This method is used to add product catalog items to PAR Order
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 * @throws Exception
	 */
    public static ActionErrors addCatalogItemsToParOrder(HttpServletRequest request,
			ShoppingForm form) throws Exception {
    	ActionErrors errors = new ActionErrors();
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		UserShopForm userShopForm = form.getUserShopForm();
		//long[] addSelected = userShopForm.getSelectBox();
		List<Long> invalidQtySelected = new ArrayList<Long>();
		userShopForm.setSelectBox(new long[0]);
		List catalogItems = userShopForm.getCartItems();
		String[] quantityS = userShopForm.getQuantity();
		ArrayList newQuantityS = new ArrayList();
		int[] itemsIds = userShopForm.getItemIds();
		long[] addSelected = getAddSelected(quantityS, itemsIds);
		log.info("addCatalogItemsToParOrder()======== OLD method:  addSelected.length ="+ addSelected.length);
		
		ArrayList<Integer> newItemsIds = new ArrayList<Integer>();
		List<String> validationList = new ArrayList<String>();
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		request.setAttribute("catalogItemKey",sessionDataUtil.getPreviousCategory());
    	form.setCatalogItemKey(sessionDataUtil.getPreviousCategory());
		ShoppingCartItemDataVector itemsToAdd = new ShoppingCartItemDataVector();
		
		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		AccountData account = appUser.getUserAccount();
		boolean allowOrdrInvItems = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS),false);
		
		List<String> parItemSkuList = new ArrayList<String>();

		for(int i = 0 ;catalogItems != null && i < catalogItems.size();i++)
		{
			ShoppingCartItemData scid = (ShoppingCartItemData)catalogItems.get(i);
			for(int j = 0;addSelected != null && j < addSelected.length;j++)
			{
//				log.info("============= OLD method: j=" + j + ", addSelected[j] ="+ addSelected[j]);
				if(addSelected[j] == scid.getItemId())
//					log.info("=============== OLD method: VALIDATION >> " + addSelected[j] +" == " +scid.getItemId());
				{
					try{
						if(quantityS[i].trim().length()> 0) {
							int qty = Integer.parseInt(quantityS[i]);
							if(qty <= 0) {
								invalidQtySelected.add((long) scid.getItemId());
								validationList.add(scid.getActualSkuNum());
							}
							else{
								if(scid.getIsaInventoryItem() && !allowOrdrInvItems) {
									if(!parItemSkuList.contains(scid.getActualSkuNum())) {
										parItemSkuList.add(scid.getActualSkuNum());
									}
								} else {
									itemsToAdd.add(scid);
									newQuantityS.add(quantityS[i]);
									newItemsIds.add(itemsIds[i]);
								}
								break;
							}
						}
					}
					catch (NumberFormatException exc) {
						invalidQtySelected.add((long) scid.getItemId());
						validationList.add(scid.getActualSkuNum());
		            }
				}
			}
		}
		if(Utility.isSet(parItemSkuList)) {
			Object[] param = new Object[1];
        	param[0] = Utility.toCommaSting(parItemSkuList);
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.parItemExistsInCorporateOrder", param);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
		}
		
		if(itemsToAdd.size() == 0 && validationList.size()== 0){  
        	String errorMess = ClwI18nUtil.getMessage(request,"productCatalog.errors.noItemSelected",null);
        	errors.add("error",new ActionError("error.simpleGenericError",errorMess));
        	return errors;
        }
		
		String []qtyS = new String[newQuantityS.size()];
		newQuantityS.toArray(qtyS);
		userShopForm.setCartItems(itemsToAdd);
		int[] itemIds = new int[newItemsIds.size()];
		 for(int i = 0;i < newItemsIds.size();i++){
			 itemIds[i] = (Integer)newItemsIds.get(i);
		 }
		userShopForm.setQuantity(qtyS);
		userShopForm.setItemIds(itemIds);
		// Following call is made to add items to cart.
		if(itemsToAdd.size() > 0)
		errors = UserShopLogic.addToInventoryCart(request, userShopForm);
		userShopForm.setCartItems(catalogItems);
		if(validationList.size() > 0 && !validationList.isEmpty()){
			// retain values entered in qty field, if validation fails 
			for(int i = 0;i < catalogItems.size();i++){
				for(int j = 0;j < invalidQtySelected.size();j++){
					ShoppingCartItemData item = (ShoppingCartItemData) catalogItems.get(i);
					if(item.getItemId() == invalidQtySelected.get(j)){
						item.setQuantityString(quantityS[i]);
						break;
					}
				}
			}
			 long[] select = new long[invalidQtySelected.size()];
			 for(int i = 0;i < invalidQtySelected.size();i++){
				 select[i] = (long)invalidQtySelected.get(i);
			 }
			 userShopForm.setSelectBox(select);
	        	Object[] param = new Object[1];
	        	param[0] = Utility.toCommaSting(validationList);
	            String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.invalidQtyMessage",param);
	            errors.add("error", new ActionError("error.simpleGenericError",errorMess));
	        	return errors;
	    }
		return errors;
	}
    
    /**
     *  This method checks if the user can only browse or not.
     * @param request - HttpServletRequest
     * @param form - ShoppingForm
     * @return boolean
     */
    public static boolean isUserBrowseOnly(HttpServletRequest request,
			ShoppingForm form) {
		// Get the existing UserShopForm and pass it on to the existing Logic
		// method.
		EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
		SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
		Map<Integer,Integer> userShoppingListIds = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
		sessionData.setUserShoppingListIds(userShoppingListIds);
		
		CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
		return appUser.isBrowseOnly();
	}
	
	/**
	 * Adds Cart items to Shopping List.
	 * @param request - HttpServletRequest
	 * @param form - ShoppingForm
	 * @return ActionErrors
	 */
	public static ActionErrors addAllCartItemsToShoppingList(HttpServletRequest request,ShoppingForm form) throws Exception{
		ActionErrors errors = null;
		// Get the existing ShoppingCartForm and pass it on to the existing
		// Logic method.
		ShoppingCartForm shoppingCartForm = form.getShoppingCartForm();
		UserShopForm userShopForm = form.getUserShopForm();
		int selectedShoppingListId = 0;
		CleanwiseUser appUser = (CleanwiseUser) request.getSession()
		.getAttribute(Constants.APP_USER);
			int userId = appUser.getUser().getUserId();
			if (appUser.getSite().isSetupForSharedOrderGuides()) {
				appUser.getUser().setUserId(0);
			}
		try{
			selectedShoppingListId = Integer.parseInt(userShopForm.getSelectedShoppingListId());
			SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
			sessionDataUtil.setCurrentShoppingListId(selectedShoppingListId);
		}
		catch (NumberFormatException e) {
			errors = new ActionErrors();
			String errorMess = ClwI18nUtil.getMessage(request,"shoppingList.errors.noShoppingListToAddItems",null);
            errors.add("error",new ActionError("error.simpleGenericError",errorMess));
            appUser.getUser().setUserId(userId);
            return errors;
        }
		userShopForm.setCartItems(shoppingCartForm.getCartItems());
		userShopForm.setQuantity(shoppingCartForm.getQuantity());
		userShopForm.setItemIds(shoppingCartForm.getItemIds());
		//the following call is made to add items to shopping list
		errors = UserShopLogic.updateUserOrderGuide(request, userShopForm);
		appUser.getUser().setUserId(userId);
		// following code is used to sort user shopping list names in alphabetical order.
		OrderGuideDataVector userShoppingLists = userShopForm.getUserOrderGuides(); 
		OrderGuideData shoppingList = null; 
		for(int i = 0;i < userShoppingLists.size();i++){
			OrderGuideData shoppingListData = (OrderGuideData)userShoppingLists.get(i);
			if(shoppingListData.getOrderGuideId() == selectedShoppingListId){
				shoppingList = (OrderGuideData)userShoppingLists.get(i);
				userShoppingLists.remove(i);
				break;
			}
		}
		DisplayListSort.sort(userShoppingLists, "short_desc");
		userShoppingLists.add(0, shoppingList);
		userShopForm.setSelectedShoppingListId(String.valueOf(selectedShoppingListId));
		userShopForm.setUserOrderGuides(userShoppingLists);
		return errors;
	}
    
    /**
	 * Creates a empty user shopping list.
	 * @param request - HttpServletRequest
	 * @return ActionErrors
	 */
    public static ActionErrors createUserShoppingList(HttpServletRequest request, EditOrderGuideForm editOrderGuideForm) throws Exception {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        if (factory == null) {
        	errors.add("error", new ActionError("error.systemError", "No Ejb access"));
            return errors;
        }
        ShoppingServices shoppingServEjb;
        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
        	errors.add("error", new ActionError("error.systemError", "No shopping services Ejb pointer"));
            return errors;
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if (appUser == null) {
        	errors.add("error", new ActionError("error.systemError", "No " + Constants.APP_USER + "session object found"));
            return errors;
        }
        UserData user = appUser.getUser();
        int siteId = appUser.getSite().getBusEntity().getBusEntityId();
        Integer catalogIdI = (Integer) session.getAttribute(Constants.CATALOG_ID);
        if (catalogIdI == null) {
        	errors.add("error", new ActionError("error.systemError", "No " + Constants.CATALOG_ID + " session object found"));
            return errors;
        }
        int catalogId = catalogIdI.intValue();
        OrderGuideDataVector userOrderGuideDV;
        try {
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides(user.getUserId(), catalogId, siteId);
        } catch (RemoteException exc) {
            exc.printStackTrace();
            errors.add("error", new ActionError("error.systemError", "Can't pick up order guides"));
            return errors;
        }
        //Check new order guide name
        String name = editOrderGuideForm.getShortDesc();
        if (name == null || name.trim().length() == 0) {
        	String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.provideShoppingListName", null);
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }
        name = name.trim();
        name = I18nUtil.getUtf8Str(name);
        editOrderGuideForm.setShortDesc(name);
        //Check uniqueness of the name
        if (userOrderGuideDV != null) {
            int ii = 0;
            for (; ii < userOrderGuideDV.size(); ii++) {
                OrderGuideData ogD = (OrderGuideData) userOrderGuideDV.get(ii);
                String ss = ogD.getShortDesc();
                if (ss != null && ss.trim().equals(name)) {
                    break;
                }
            }
            if (ii < userOrderGuideDV.size()) {
            	 Object[] params = new Object[1];
                 params[0] = name;
                 String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.enterNewName", params);
                 errors.add("error",new ActionError("error.simpleGenericError",errorMess));
                return errors;
            }
        }
        //Create new shopping list
        OrderGuideData orderGuideD = OrderGuideData.createValue();
        orderGuideD.setShortDesc(name);
        orderGuideD.setCatalogId(catalogIdI.intValue());
        orderGuideD.setBusEntityId(appUser.getSite().getBusEntity().getBusEntityId());
        orderGuideD.setUserId(appUser.getUser().getUserId());
        orderGuideD.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
        try {
            shoppingServEjb.saveUserOrderGuide(orderGuideD, null, appUser.getUser().getUserName());
            //STJ-4613
            editOrderGuideForm.setOrderGuide(orderGuideD);
            editOrderGuideForm.setOrderGuideId(orderGuideD.getOrderGuideId());
        } catch (Exception exc) {
        	errors.add("error", new ActionError("error.systemError", "Can't save order guide to database"));
            return errors;
        }
        try {
        	// STJ - 4756
        	int userId = appUser.getUser().getUserId(); 
        	if (appUser.getSite().isSetupForSharedOrderGuides()) {
        		userId = 0;
        	}
        	// STJ - 4756
            userOrderGuideDV = shoppingServEjb.getUserOrderGuides(userId, catalogId, siteId);
            editOrderGuideForm.setUserOrderGuides(userOrderGuideDV);
        } catch (RemoteException exc) {
            exc.printStackTrace();
            errors.add("error", new ActionError("error.systemError", "Can't pick up order guides"));
            return errors;
        }
        return errors;
    }
    
    public static ActionErrors updateLocationBudgetChart(HttpServletRequest request) {
    	ActionErrors errors = null;
    	CleanwiseUser user = ShopTool.getCurrentUser(request);
    	SiteData site = ShopTool.getCurrentSite(request);
    	errors = SiteLocationBudgetLogic.updateLocationBudgetChart(request, site, user);
    	return errors;
    }
    
    /**
     * Checks for quantities of items are changed in the shopping cart or not.
     * @param shoppingCartForm - the <code>ShoppingCartForm</code> from the ShoppingForm.
     * @return isSaveNeeded - is <code>true</code> if quantities of items are changed, 
     * 						Otherwise it returns <code>false</code>
     */
    public static boolean areItemQtysUpdated(ShoppingCartForm shoppingCartForm){
    	
    	boolean isSaveOperationNeeded = false;
    	ShoppingCartData cartData = shoppingCartForm.getShoppingCart();
    	List sCartItems = shoppingCartForm.getCartItems();
    	
    	if(Utility.isSet(sCartItems)){
    		for(int idx=0; idx<sCartItems.size();idx++) {
        		ShoppingCartItemData scItem = (ShoppingCartItemData)sCartItems.get(idx);
        		
        		if(!scItem.getQuantityString().equals(String.valueOf(scItem.getQuantity()))){
        			isSaveOperationNeeded = true;
        			break;
        		}
        	}
    	}
    	
    	return isSaveOperationNeeded;
    }
    private static int calculateItemCount(MenuItemView menuItem,Map<Integer,ProductDataVector> itemMap) {
    	int returnValue = 0;
    	List directItems = itemMap.get(Integer.valueOf(menuItem.getKey()));
    	if (Utility.isSet(directItems)) {
    		returnValue = directItems.size();
    	}
    	MenuItemViewVector subItems = menuItem.getSubItems();
    	if (subItems != null && !subItems.isEmpty()) {
    		Iterator subItemIterator = subItems.iterator();
    		while (subItemIterator.hasNext()) {
    			MenuItemView subItem = (MenuItemView)subItemIterator.next();
    	    	returnValue = returnValue + calculateItemCount(subItem,itemMap);
    		}
    	}
    	return returnValue;
    }
    
    public static boolean isExcludeOrderFromBudgetUpdated(HttpServletRequest request, ShoppingForm shoppingForm) {
    	boolean isSaveRequired = false;
    	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
    	if(sessionData.isExcludeOrderFromBudget()!= shoppingForm.isExcludeOrderFromBudget()){
    		isSaveRequired = true;
    		sessionData.setExcludeOrderFromBudget(shoppingForm.isExcludeOrderFromBudget());
    	}
    	return isSaveRequired;
    }
    
    public static ActionErrors updateOrderGuide(HttpServletRequest request,ShoppingForm shoppingForm) {
    	ActionErrors errors = null;
    	
    	OrderGuideData ogData = getOrderGuideOfCart(request);
    	boolean errorOccured = false;
    	if(ogData!=null) {
    		CleanwiseUser user = ShopTool.getCurrentUser(request);
    		Date now = new Date();
    		String orderBudgetTypeCd = null;
    		
    		if(shoppingForm.isExcludeOrderFromBudget() ) {
    			orderBudgetTypeCd = RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE;
    		}
    		
    		ogData.setOrderBudgetTypeCd(orderBudgetTypeCd);
    		ogData.setModBy(user.getUserName());
    		ogData.setModDate(now);
    		
			try {
				HttpSession session = request.getSession();
	            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
				OrderGuide orderGuideBean = factory.getOrderGuideAPI();
				ogData = orderGuideBean.updateOrderGuideData(ogData);
			} catch (Exception e) {
				log.error("Error occured while updating Order Guide Info");
				errorOccured = true;
			}
    		
    	} else {
    		log.error("No Order guide found for the current site.");
    		errorOccured = true;
    	}
    	if(errorOccured) {
    		errors = new ActionErrors();
    		String fieldName = ClwI18nUtil.getMessage(request, "userportal.esw.shoppingCart.excludeOrderFromBudget");
    		if(!Utility.isSet(fieldName)) {
    			fieldName = Constants.FIELD_EXCLUDE_ORDER_FROM_BUDGET;
    		}
    		Object[] obj = new Object[1];
    		obj[0] = fieldName;
            String errorMess = ClwI18nUtil.getMessage(request, "userportal.esw.generic.error.save", obj);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    	}
    	
    	return errors;
    }
    
    public static OrderGuideData getOrderGuideOfCart(HttpServletRequest request) {
    	OrderGuideData ogData = null;
    	
        try {
        	CleanwiseUser user = ShopTool.getCurrentUser(request);
        	int userId = user.getUser().getUserId();
        	int siteId = ShopTool.getCurrentSiteId(request);
        	String orderGuideTypeCd = RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART;
        	
        	HttpSession session = request.getSession();
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
			OrderGuide orderGuideBean = factory.getOrderGuideAPI();
			ogData = orderGuideBean.getOrderGuide(userId,siteId,orderGuideTypeCd);
			
		} catch (Exception e) {
			log.error("Unable to get Order Guide API Access");
		} 
        
    	return ogData;
    }
    
    private static ActionErrors prepareCartItems(HttpServletRequest request,
            UserShopForm pForm)
            throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(
                Constants.APIACCESS);

        if (factory == null) {
            ae.add("error",
                    new ActionError("error.systemError", "No Ejb access"));

            return ae;
        }

        ShoppingServices shoppingServEjb = null;

        try {
            shoppingServEjb = factory.getShoppingServicesAPI();
        } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError",
                    "No shopping services Ejb pointer"));

            return ae;
        }
        Integer catalogIdI = (Integer) session.getAttribute(
                Constants.CATALOG_ID);

        if (catalogIdI == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No " + Constants.CATALOG_ID +
                    " session attrbute found"));

            return ae;
        }

        int catalogId = catalogIdI.intValue();
        Integer contractIdI = (Integer) session.getAttribute(
                Constants.CONTRACT_ID);
        int contractId = 0;

        if (catalogIdI != null) {
            contractId = contractIdI.intValue();
        }
		//
		        
		//
        List productList = pForm.getProductList();
        pForm.setOffset(0);
        pForm.setPageSize(pForm.getProductListSize());

        int offset = pForm.getOffset();
        int pageSize = pForm.getPageSize();
        int listSize = pForm.getProductListSize();
        int size = (offset + pageSize > listSize) ? listSize : offset +
                pageSize;
        ShoppingCartItemDataVector cartItems = new ShoppingCartItemDataVector();
        List requestedItems = new LinkedList();

        for (int ii = offset; ii < size; ii++) {

            Object elem = productList.get(ii);
            requestedItems.add(elem);
        }

        StoreData store = pForm.getAppUser().getUserStore();

        if (store == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store information was loaded"));

            return ae;
        }

        PropertyData storeTypeProperty = store.getStoreType();

        if (storeTypeProperty == null) {
            ae.add("error",
                    new ActionError("error.systemError",
                    "No store type information was loaded"));

            return ae;
        }

        try {
           cartItems = shoppingServEjb.prepareShoppingItems(storeTypeProperty.getValue(), pForm.getAppUser().getSite(),
                        catalogId,
                        contractId,
                        requestedItems,
                        SessionTool.getCategoryToCostCenterView(request.getSession(),pForm.getAppUser().getSite().getSiteId()));
           cartItems = shoppingServEjb.sortShoppingCardItems(pForm.getOrderBy(), cartItems);
            //Jd end
        } catch (RemoteException exc) {
            exc.printStackTrace();
            ae.add("error",
                    new ActionError("error.systemError", exc.getMessage()));

            return ae;
        }
        pForm.setCartItems(cartItems);
        return ae;
    }   
    public static ActionErrors addQuickShopItemsToCart(HttpServletRequest request,ShoppingForm shoppingForm) throws Exception{
    	ActionErrors errors = new ActionErrors();
    	QuickOrderForm quickOrderForm = shoppingForm.getQuickOrderForm();
    	String[] skus = quickOrderForm.getItemSkus();
        String[] qtys = quickOrderForm.getItemQtys();
        int i = 0;
		for(; skus != null && i < skus.length; i++) {
		    if((skus[i] != null && skus[i].trim().length() > 0) || (qtys[i] != null && qtys[i].trim().length() > 0)) {
		    	break;
		    }
		}
		if(i >= quickOrderForm.getPageSize()){
			String errorMess = ClwI18nUtil.getMessage(request,"shop.errors.atleastOneSkuMustBeEnteredWithQuantity",null);
            errors.add("error",new ActionError("error.simpleGenericError",errorMess));
			return errors;
		}
    	quickOrderForm.setSkuType(QuickOrderForm.DEFAULT_SKU_TYPE);//Our-Sku
    	errors = QuickOrderLogic.addToCart(request, quickOrderForm);
    	if(quickOrderForm.getDuplicationFlag()){
    		String errorMess = ClwI18nUtil.getMessage(request,"shop.quick.text.someSkusHaveMoreThanOneMapping",null);
            errors.add("error",new ActionError("error.simpleGenericError",errorMess));
    	}
    	return errors;
    }
    public static ActionErrors resolveQuickShopSkuCollisions(HttpServletRequest request,ShoppingForm shoppingForm) throws Exception{
    	ActionErrors errors = null;
    	QuickOrderForm quickOrderForm = shoppingForm.getQuickOrderForm();
    	errors = QuickOrderLogic.resolveMfg(request,quickOrderForm,false);
    	if(errors != null && errors.size() == 0){
    		quickOrderForm.setDuplicationFlag(false);
    	}
    	return errors;
    }
    public static ActionErrors printOrExportShoppingListItems(HttpServletResponse response,
            HttpServletRequest request, ShoppingForm form, String action,
            MessageResources mResources) throws Exception {

      CleanwiseUser user = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
      EditOrderGuideForm editOrderGuideForm = form.getEditOrderGuideForm();
      UserShopForm userShopForm = form.getUserShopForm();
      userShopForm.setCartItems(editOrderGuideForm.getItems());
      userShopForm.setAppUser(user);
      return UserShopLogic.printDetail(response, request, userShopForm,action, mResources);
    }
    
    /**
     * Refreshes shopping list items.
     */

    public static void refreshShoppingLists(HttpServletRequest request) {
    	//STJ-5774: 
    	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
    	sessionData.setSelectedSubTab(Constants.TAB_PRODUCTS);
    	UserShopForm userShopForm = sessionData.getUserShopForm();
    	if(userShopForm==null) {
    		userShopForm = new UserShopForm();
    	}
    	
    	EditOrderGuideForm editOrderGuideForm = sessionData.getEditOrderGuideForm();
    	if(editOrderGuideForm==null) {
    		editOrderGuideForm = new EditOrderGuideForm();
    	}
    	
		ShoppingForm shoppingForm = new ShoppingForm();
		shoppingForm.setUserShopForm(userShopForm);
		shoppingForm.setEditOrderGuideForm(editOrderGuideForm);
		
		try {
			ActionErrors errors = shopByLists(request,shoppingForm);
			if(!(errors!=null && errors.size()>0)) {
				sessionData.setUserShopForm(shoppingForm.getUserShopForm());
				sessionData.setEditOrderGuideForm(shoppingForm.getEditOrderGuideForm());
			}
			
		} catch (Exception e) {
			log.debug("---- An Un Expected error has been occured while retriving shopping list data ---- "+e.getMessage());
			//e.printStackTrace();
		}
    }

    public static void recalcShoppingLists(HttpServletRequest request, ShoppingForm shoppingForm) {
    	SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
        UserShopForm userShopForm = shoppingForm.getUserShopForm();
        EditOrderGuideForm editOrderGuideForm = shoppingForm.getEditOrderGuideForm();
		try {
		    OrderGuideDataVector shoppingListsDV = editOrderGuideForm.getUserOrderGuides();
		    editOrderGuideForm.setUserOrderGuides(shoppingListsDV);
     
		    if (userShopForm.getTemplateOrderGuides() != null &&
		            userShopForm.getTemplateOrderGuides().size() > 0) {
			    Map<Integer,Integer> templateShoppingListIds = countItemsInShoppingLists(request, userShopForm.getTemplateOrderGuides(),userShopForm.getOrderBy());
			    sessionData.setTemplateShoppingListIds(templateShoppingListIds);

		        Map<Integer,Integer> userShoppingList = countItemsInShoppingLists(request, editOrderGuideForm.getUserOrderGuides(),editOrderGuideForm.getOrderBy());
		        sessionData.setUserShoppingListIds(userShoppingList);
            }
    	} catch (Exception e) {
			log.debug("---- An Un Expected error has been occured while retriving shopping list data ---- "+e.getMessage());
		}
    }
	public static ActionErrors showGroupItemDetails(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		log.info("showGroupItemDetails =====> BEGIN.");
		ActionErrors ae = new ActionErrors();
        UserShopForm userShopForm = form.getUserShopForm();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        userShopForm.setAppUser(appUser);
        
        String source = request.getParameter("source");        
        if (Utility.isSet(source) && source.equals("order")) {
             //ae = UserShopLogic.itemOrder(request, userShopForm);
        }
        else {
            //--------------------------------//
            if (userShopForm.getOriginalCartItems() == null){
            	if ( Utility.isSet(userShopForm.getCartItems())) {
	            	ArrayList<ShoppingCartItemData> origCartItemList = new ArrayList<ShoppingCartItemData>();
	    	        ArrayList<ProductData> origProductList = new ArrayList<ProductData>();
	    	        for (Object oCartItem : userShopForm.getCartItems()) {
	    	        	origCartItemList.add(((ShoppingCartItemData) oCartItem));
	    	        	origProductList.add(((ShoppingCartItemData) oCartItem).getProduct());
	    	        	
	    	        }
	    	        userShopForm.setOriginalCartItems(origCartItemList);
	    	        userShopForm.setOriginalProducts(origProductList);
            	}
            } else {
            	userShopForm.setCartItems(userShopForm.getOriginalCartItems());
            	userShopForm.setProductList(userShopForm.getOriginalProducts());
            }
//	        log.info("showGroupItemDetail()--------->userShopForm.getOriginalCartItems() = "+ userShopForm.getOriginalCartItems().size());
//	        log.info("showGroupItemDetail()---------> userShopForm.getCartItems().size() = "+ userShopForm.getCartItems().size());
            //--------------------------------//

        	ae = UserShopLogic.itemGroup(request, userShopForm);
        }
        
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        Content contentBean = factory.getContentAPI();
        
        String catalogItemKey = "";
        if (userShopForm!=null && userShopForm.getItemGroupCachedView()!=null && userShopForm.getItemGroupCachedView().getCategory()!= null) {
        	catalogItemKey = ""+userShopForm.getItemGroupCachedView().getCategory().getCatalogCategoryId();
        	Utility.getSessionDataUtil(request).setPreviousCategory(catalogItemKey);
			request.setAttribute("catalogItemKey", catalogItemKey);
        	form.setCatalogItemKey(catalogItemKey);
        	
        	//---------------------------------------//
        	
	   		 ArrayList keys = new ArrayList();
			 List path = userShopForm.getCategoryPath();
			 log.info("showGroupItemDetails =====>userShopForm.getCategoryPath()="+userShopForm.getCategoryPath());
	         for(int i = 1;path!=null && i < path.size();i++){
	         	CategoryInfoView categoryInfo = (CategoryInfoView)path.get(i);
	         	keys.add(categoryInfo.getCategoryKey());
	         }
	         request.setAttribute("categorykeys", keys);

        	//--------------------------------------//
        }        
        // First Record settings
        if (userShopForm != null && Utility.isSet(userShopForm.getCartItems()) && userShopForm.getItemDetail()!=null ) {
        	 //ShoppingCartItemData firstRec = ((ShoppingCartItemData)userShopForm.getCartItems().get(0));
        	 //String recLongDesc = firstRec.getProduct().getLongDesc();
	         //String recImage = firstRec.getProduct().getImage();
	         //String recThumbnail = firstRec.getProduct().getThumbnail();
	         //-----//
        	 final String EMPTY_DESC = "."; 
        	 String recLongDesc = null;
	         String recImage = null;
	         String recThumbnail = null;

        	 for (Object item : userShopForm.getCartItems()){
        		 	ShoppingCartItemData rec = (ShoppingCartItemData)item; 
        		 	log.info("showGroupItemDetails()========> rec.getItemId() = " + rec.getItemId()+ ", rec.getProduct().getSize()=" + rec.getProduct().getSize());
			         // if  both image and long description  are picked
        		    boolean isSetImage = Utility.isSet(rec.getProduct().getImage());
        		    boolean isSetThumbnail = Utility.isSet(rec.getProduct().getThumbnail());
        		    boolean isSetLongDesc = Utility.isSet(rec.getProduct().getLongDesc()) && !EMPTY_DESC.equals(rec.getProduct().getLongDesc());
        		    
        		    if ((Utility.isSet(recImage) /*|| Utility.isSet(recThumbnail)*/) && Utility.isSet(recLongDesc) ) {
        		    	break;
   			         // if there is an item with both image and long description 
        		    } else if ( (isSetImage /*|| isSetThumbnail*/ ) && isSetLongDesc ) {
		        		recLongDesc = rec.getProduct().getLongDesc();
		        		recImage = rec.getProduct().getImage();
		        		recThumbnail = rec.getProduct().getThumbnail();
		        		break;
		        	//	If there is an item with an image	
		        	} else if (!Utility.isSet(recImage) && isSetImage ) {
		         		recImage = rec.getProduct().getImage();
		        	} else if (!Utility.isSet(recThumbnail) && isSetThumbnail) {
		        		recThumbnail = rec.getProduct().getThumbnail();
		        	//	If there is an item with long desc	
		        	} else if (!Utility.isSet(recLongDesc) && isSetLongDesc ) {
		        		recLongDesc = rec.getProduct().getLongDesc();
		        	}
		     }
	         userShopForm.getItemDetail().getProduct().setLongDesc(recLongDesc);
	         userShopForm.getItemDetail().getProduct().setImage(recImage);
	         userShopForm.getItemDetail().getProduct().setThumbnail(recThumbnail);
	         
        }
        log.info("showGroupItemDetails =====> END. catalogItemKey = "+ catalogItemKey);
        return ae;
		
	}
	
	public static ActionErrors clearQty(HttpServletRequest request,
			ShoppingForm form) throws Exception {
		log.info("clearQty() =====> BEGIN.");
		ActionErrors ae = new ActionErrors();
        UserShopForm userShopForm = form.getUserShopForm();
        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        userShopForm.setAppUser(appUser);
        String source = request.getParameter("source");        
       	ae = UserShopLogic.clear(request, userShopForm);

		log.info("clearQty() =====> END.");
        return ae;
		
	}
	
	private static long[] getAddSelected (String[] quantityS, int[] itemsIds ){
		int modified = 0;
		
		for (int i = 0; quantityS != null && i < quantityS.length; i++) {
			if (Utility.isSet(quantityS[i])){
				modified++;
			}
		}
		long[] addSelected = new long[modified];
		int k = 0;
		for (int i = 0; quantityS != null && i < quantityS.length; i++) {
			if (Utility.isSet(quantityS[i])){
				addSelected[k]=itemsIds[i];
				k++;
			}
		}
		return addSelected ;

	}
	private static ShoppingCartItemDataVector prepareMultiProductGroupItems(HttpServletRequest request,  ShoppingCartItemDataVector items , int catalogId, int siteId) throws Exception {
		ShoppingCartItemDataVector allItems = new ShoppingCartItemDataVector();
		ProductDataVector pdV = new ProductDataVector();
		for (Object item : items) {
			ProductData product = ((ShoppingCartItemData)item).getProduct();
			if (product.hasItemGroup()){ // to prepare only group items
				pdV.add(product);
			} else {
				allItems.add(item);
			}
		}

		ProductDataVector products = prepareMultiProductGroupItems( request,  pdV , catalogId, siteId) ;
		
		for (Object product : products) {
			ShoppingCartItemData groupItem = new ShoppingCartItemData();
			groupItem.setProduct((ProductData)product);
			allItems.add(groupItem);
		}
		return allItems;
	}
	
	private static ProductDataVector prepareMultiProductGroupItems(HttpServletRequest request,  IdVector searchResults , int catalogId) throws Exception {
		CleanwiseUser appUser  = ShopTool.getCurrentUser(request);
        UserData userD  = appUser.getUser();
        int siteId = appUser.getSite().getSiteId();
        StoreData store  = appUser.getUserStore();

        
        AccCategoryToCostCenterView catToCostCenterView = SessionTool.getCategoryToCostCenterView(request.getSession(), siteId);
		log.info("getItemGroupIds()=====> searchResults =" + ((Utility.isSet(searchResults) ) ? searchResults.toString() : "NULL" ));
		//get products by Ids
        ProductDataVector foundProducts = APIAccess.getAPIAccess().getShoppingServicesAPI().getCatalogClwProductCollection( catalogId, searchResults, catToCostCenterView );
        
		// prepare multyProduct group items
        ProductDataVector products = prepareMultiProductGroupItems(request,  foundProducts ,  catalogId,  siteId);
        
        // Sort Items
        PropertyData storeTypeProperty = store.getStoreType();
//        int orderBy = pForm.getOrderBy();
//        if (orderBy == 0) {
//            orderBy = Constants.ORDER_BY_NAME;
//        }
        products = UserShopLogic.sortProducts(storeTypeProperty.getValue(), products, Constants.ORDER_BY_NAME);

        //pForm.setProductList(products);		
        return products;
	}
	
	private static ProductDataVector prepareMultiProductGroupItems(HttpServletRequest request,  ProductDataVector foundProducts , int catalogId, int siteId) throws Exception {
       
        AccCategoryToCostCenterView catToCostCenterView = SessionTool.getCategoryToCostCenterView(request.getSession(), siteId);

		// prepare multyProduct group items
       
        CatalogInformation catalogEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
        ProductDataVector products = UserShopLogic.getProductInfo(catalogEjb, foundProducts, siteId, catToCostCenterView);

        return products;
	}
	
	private static boolean isTemplateOrderGuide(HttpServletRequest request, ShoppingForm pForm) {
		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		Map<Integer, Integer> templateShoppingListIds = sessionDataUtil.getTemplateShoppingListIds();
		//OrderGuideDataVector ogDV = (pForm.getUserShopForm()).getTemplateOrderGuides();
		boolean isTemplateOrderGuide = false;
		if (templateShoppingListIds != null) {
			log.info("isTemplateOrderGuide() ---------> templateShoppingListIds = " + templateShoppingListIds.toString());
			isTemplateOrderGuide = templateShoppingListIds.containsKey(pForm.getSelectedShoppingList());
		}
		//for (Object og : ogDV) {
		//	if (pForm.getSelectedShoppingList() ==((OrderGuideData)og).getOrderGuideId()) {
		//		isTemplateOrderGuide = true;
		//	}
		//}
		return isTemplateOrderGuide;
	}
	/*private static void recalculateShoppingCartQuantities (HttpServletRequest request, ShoppingCartForm shoppingCartForm)	throws Exception {
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
		ShoppingCartData invCartData = shoppingCartForm.getFormCartIL();
	    if (invCartData != null) {
	    	ShoppingCartLogic.recalInvCartQty(request, )
	    	invCartData = ShoppingCartLogic.reviseShoppingCartQuantities(invCartData, APIAccess.getAPIAccess().getOrderAPI(), appUser.getSite().getSiteId(), invCartData.getInventoryItemsOnly()); // new statement
	    	shoppingCartForm.setFormCartIL(invCartData);
	    }			
	}*/


	private static void checkZeroQtyForScheduleList(EditOrderGuideForm editOrderGuideForm,
	                                                HttpServletRequest request,
	                                                ActionErrors errors) throws Exception {
       String[] quantityS = editOrderGuideForm.getQuantity();
       boolean allZero = true;
       for (int ii=0; ii<quantityS.length; ii++) {
            String qtyS = quantityS[ii];
            if (Utility.isSet(qtyS)) {
                try {
                    int qty = Integer.parseInt(qtyS, 10);
                    if (qty != 0) {
                        allZero = false;
                        break;
                    }
                } catch (Exception e) {
                        allZero = false;
                        break;
                }
            }
       }

       if (allZero) {

			AutoOrder autoOrderBean = APIAccess.getAPIAccess().getAutoOrderAPI();
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            SiteData siteData = appUser.getSite();
            int accountId = siteData.getAccountId();
            int siteId = siteData.getSiteId();

			OrderScheduleViewVector orderScheduleViewVector =
			    autoOrderBean.getOrderSchedules(accountId, siteId, null, null, null, null, null,0);
			for(int i = 0;orderScheduleViewVector != null && i < orderScheduleViewVector.size();i++){
				OrderScheduleView orderScheduleView = (OrderScheduleView)orderScheduleViewVector.get(i);
				if(orderScheduleView.getOrderGuideId() == editOrderGuideForm.getInputOrderGuideId()){
					if (errors == null) errors = new ActionErrors();
					String errorMess = ClwI18nUtil.getMessage(request,"shoppingList.error.shoppingListIsScheduled.itemCantBeAllZero", null);
					errors.add("error", new ActionError("error.simpleGenericError",errorMess));
					return;
			    }
		   }

       }

    }
}
