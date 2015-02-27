/**
 * Title: DashboardLogic 
 * Description: This is the business logic class handling the ESW dash board functionality.
 */

package com.espendwise.view.logic.esw;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.dto.OrderSearchDto;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.ContractInformation;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.ProductInformation;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderJoinData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.OrderStatusCriteriaData;
import com.cleanwise.service.api.value.OrderStatusDescData;
import com.cleanwise.service.api.value.OrderStatusDescDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UIConfigData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.forms.OrderSearchForm;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.HandleOrderLogic;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.logic.OrderDetailLogic;
import com.cleanwise.view.logic.OrderSearchLogic;
import com.cleanwise.view.logic.UserShopLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.DashboardForm;

/**
 * Implementation of logic that handles dash board functionality.
 */
public class DashboardLogic {

    private static final Logger log = Logger.getLogger(DashboardLogic.class);

    /**
     * Find the locations matching a set of criteria.
     * @param   locationSearchDto - a <code>LocationSearchDto</code> containing the search criteria.
     * @return  An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors performLocationSearch(HttpServletRequest request, LocationSearchDto locationSearchDto) {
    	//trim all user entered values before use
    	if (locationSearchDto != null) {
    		if (locationSearchDto.getCity() != null) {
    			locationSearchDto.setCity(locationSearchDto.getCity().trim());
    		}
    		if (locationSearchDto.getKeyword() != null) {
    			locationSearchDto.setKeyword(locationSearchDto.getKeyword().trim());
    		}
    		if (locationSearchDto.getPostalCode() != null) {
    			locationSearchDto.setPostalCode(locationSearchDto.getPostalCode().trim());
    		}
    		if (locationSearchDto.getState() != null) {
    			locationSearchDto.setState(locationSearchDto.getState().trim());
    		}
    	}
    	ActionErrors errors = validateLocationCriteria(request, locationSearchDto);
    	//if the location search/sort criteria is invalid return the errors
    	if (errors != null && !errors.isEmpty()) {
    		return errors;
    	}
    	try {
    		Site siteBean = APIAccess.getAPIAccess().getSiteAPI();
    		siteBean.getLocationsByCriteria(locationSearchDto, Constants.LOCATION_SEARCH_RESULTS_MAX_DISPLAY);
        }
        catch (Exception exc) {
        	log.error("Unable to retrieve sites for the logged in user.");
        	String errorMess = ClwI18nUtil.getMessage(request, "location.search.problemRetrievingLocations", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
        }
        return errors;
    }
    
    /*
     * Private method to validate any location search/sort criteria is valid
     */
    private static ActionErrors validateLocationCriteria(HttpServletRequest request, LocationSearchDto locationSearchDto) {
        CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
    	ActionErrors errors = new ActionErrors();
    	if (locationSearchDto == null) {
        	log.error("Missing location search criteria encountered.");
            String errorMess = ClwI18nUtil.getMessage(request, "location.search.problemRetrievingLocations", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
    	else {
    		//make sure the user id is specified and valid
    		if (!Utility.isSet(locationSearchDto.getUserId()) ||
    			Integer.valueOf(locationSearchDto.getUserId().trim()) != user.getUserId()) {
            	log.error("Invalid user id search criteria encountered.");
                String errorMess = ClwI18nUtil.getMessage(request, "location.search.problemRetrievingLocations", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    		}
        	boolean validSortField = true;
        	boolean validSortOrder = true;
        	//make sure the sort field is specified and valid
        	if (!Utility.isSet(locationSearchDto.getSortField())) {
        		validSortField = false;
        	}
        	else {
            	String sortField = locationSearchDto.getSortField().trim();
            	if (!Constants.LOCATION_SORT_FIELD_NAME.equalsIgnoreCase(sortField) &&
            		!Constants.LOCATION_SORT_FIELD_ADDRESS.equalsIgnoreCase(sortField) &&
            		!Constants.LOCATION_SORT_FIELD_NUMBER.equalsIgnoreCase(sortField) &&
            		!Constants.LOCATION_SORT_FIELD_LAST_VISIT.equalsIgnoreCase(sortField)) {
            		validSortField = false;
            	}
        	}
        	if (!validSortField) {
                String errorMess = ClwI18nUtil.getMessage(request, "location.sort.invalidSortField", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	}
        	//make sure the sort order is specified and valid
        	if (!Utility.isSet(locationSearchDto.getSortOrder())) {
        		validSortOrder = false;
        	}
        	else {
            	String sortOrder = locationSearchDto.getSortOrder().trim();
            	if (!Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder) &&
            		!Constants.LOCATION_SORT_ORDER_DESCENDING.equalsIgnoreCase(sortOrder)) {
            		validSortOrder = false;
            	}
        	}
        	if (!validSortOrder) {
                String errorMess = ClwI18nUtil.getMessage(request, "location.sort.invalidSortOrder", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
        	}
    	}
    	return errors;
    }

    /**
     * Retrieve information for a specific user's location and update the user with that information.
     * @param  request  The HTTP request we are processing.
     * @param  user  The currently logged in user.
     * @param  location  The location that is being selected for the user.
     * @return An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors setUserLocation(HttpServletRequest request, CleanwiseUser user,	BusEntityData location) {
    	
        ActionErrors errors = new ActionErrors();
        
		APIAccess factory = (APIAccess) request.getSession(false).getAttribute(Constants.APIACCESS);
		AccountData prevAccount = user.getUserAccount();

            try {
                User userEjb = factory.getUserAPI();
                CatalogInformation catInfo = factory.getCatalogInformationAPI();
                ContractInformation contractInfo = factory.getContractInformationAPI();
                BusEntityDataVector locationCollection = new BusEntityDataVector();       	
                locationCollection.add(location);	            	
                long startTime = System.currentTimeMillis();
	        SiteData locationData = LogOnLogic.getValidSite(request, userEjb, catInfo, contractInfo, locationCollection, user.getUser(), new Date(), errors);
            if (locationData == null) {
                if (!errors.isEmpty()) {
                    return errors;
                }
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.invalidLocationDependencies", null);
	        errors.add("error", new ActionError("error.simpleGenericError", errorMess));
	        return errors;
            }
            AccountData account = SessionTool.getAccountData(request, locationData.getAccountBusEntity().getBusEntityId());
            StoreData store = SessionTool.getStoreData(request, account.getStoreAssoc().getBusEntity2Id());
            long endTime = System.currentTimeMillis();
            log.info("Location validation time: " + (endTime - startTime)/1000);
            user.setUserAccount(account);
            user.setUserStore(store);
			user.setSite(locationData);
            if (!LogOnLogic.logOnSiteShop(request, user)) {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.siteNotReadyForPurchases", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
                return errors;
            }
            

            //update the user/site association with the current date/time
            Date currentDate = new Date();
            userEjb.updateLastUserVisit(locationData.getSiteId(), user.getUserId(), user.getUserName(), currentDate);
            
            //update any previous search results to have the correct "most recently visited location"
            //information and to be in the correct order.
            SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
            LocationSearchDto previousLocationSearch = sessionDataUtil.getLocationSearchDtoMap().get(Constants.CHANGE_LOCATION);
            if (previousLocationSearch != null) {
            	//blank out any previous most recently visited site id.
            	previousLocationSearch.setMostRecentlyVisitedSiteId(StringUtils.EMPTY);
            	//iterate over the previous matching locations, looking for the location that was just selected
            	SiteDataVector locations =  previousLocationSearch.getMatchingLocations();
            	if (locations != null && locations.size() > 0) {
            		Iterator locationIterator = locations.iterator();
            		boolean foundLocation = false;
            		while (locationIterator.hasNext() && !foundLocation) {
            			SiteData candidateLocation = (SiteData)locationIterator.next();
            			foundLocation = (locationData.getSiteId() == candidateLocation.getSiteId());
                    	if (foundLocation) {
                        	// update the most recently visited location id on the results to be the id of the 
                        	// new location
                    		previousLocationSearch.setMostRecentlyVisitedSiteId(Integer.toString(candidateLocation.getSiteId()));
                        	// update the last user visit value on that location
                    		candidateLocation.setLastUserVisitDate(currentDate);
                        	// if the results are sorted by last visit time, move the selected location to the 
                        	// correct order in the list (last if sorted in ascending order, first if descending)
                    		if (Constants.LOCATION_SORT_FIELD_LAST_VISIT.equalsIgnoreCase(previousLocationSearch.getSortField())) {
                    			locationIterator.remove();
                    			if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(previousLocationSearch.getSortOrder())) {
                    				locations.add(locations.size(), candidateLocation);
                    			}
                    			else {
                    				locations.add(0,candidateLocation);
                    			}
                    		}
                    	}
            		}
            	}
            }
            
            //take care of any session values specific to the previous location

            sessionDataUtil.setLastViewedOrderScheduleId(null);
    		sessionDataUtil.setRememberShoppingList(0);
    		sessionDataUtil.setHeaderLogo(null);
    		sessionDataUtil.setCorporateOrderOpenDate(null);
    		sessionDataUtil.setCorporateOrderReleaseDate(null);
    		//update the value of the header logo
    		setHeaderLogo(request, user.getUserId(), locationData.getSiteId(), store.getStoreId());

            // check if other account
            if (prevAccount == null || prevAccount.getAccountId() != user.getUserAccount().getAccountId() ) {
                LogOnLogic.getUIConfigAccount(account.getAccountId(), user, request);
                UIConfigData uioptions = user.getUIConfigData();
                LogOnLogic.addUIConfigDataToSession(uioptions,request.getSession(false));
            }

	        //STJ-4583 - determine if the selected location supports Corporate Orders
	        PropertyService propEjb = factory.getPropertyServiceAPI();
	        //STJ-5934
	        String invShopping = null;
	        try {
	        	invShopping = Utility.strNN(propEjb.getBusEntityProperty(locationData.getAccountId(), RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY));
	        }
	        catch (DataNotFoundException dnfe) {
	        	invShopping = Constants.FALSE;
	        }
	        
	        boolean useCorporateOrders = (Constants.TRUE.equals(invShopping) || locationData.isAllowCorpSchedOrder()) &&
	        						locationData.hasInventoryShoppingOn();  	
	        sessionDataUtil.setConfiguredForCorporateOrders(useCorporateOrders);
	        
	        boolean isCorporateOrderOpen = ShopTool.hasInventoryCartAccessOpen(request);
	        Date nextCutOffDate = locationData.getNextOrdercutoffDate();
	        Date nextCutOffTime =  locationData.getNextOrdercutoffTime();
	        
	        if(useCorporateOrders){
	        	SimpleDateFormat sdf = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
	        	SimpleDateFormat stf = new SimpleDateFormat(ClwI18nUtil.getTimePattern(request));	    		
	    		
	        	if(isCorporateOrderOpen){
	        	    if(nextCutOffDate!=null){
    	        		//get release date and time
    		    		String releaseDateS = sdf.format(nextCutOffDate) + " " +stf.format(nextCutOffTime);
    		    		sessionDataUtil.setCorporateOrderReleaseDate(releaseDateS.toString());
	        	    }
	        	}else{	        		
	        		if(nextCutOffDate!=null){
	        			Date openDate = locationData.getNextScheduleAccessTime();
	        			if (openDate != null){
	        				String openDateS = sdf.format(openDate);
	        				if (locationData.isAllowCorpSchedOrder())
	        					openDateS += " " + stf.format(openDate);;
	        				sessionDataUtil.setCorporateOrderOpenDate(openDateS);
	        			}
	        		}
	        	}
	        }	        
        } catch (Exception e) {
            e.printStackTrace();
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.exceptionGettingLocationInfo", new String[] {e.getMessage()});
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }
    	errors = LogOnLogic.initNextDeliveryDateForSite(request.getSession(false), request);
    	if (errors != null && !errors.isEmpty()) {
    		return errors;
    	}
    	
    	try {
            UserShopForm userShopForm = new UserShopForm();
            SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
            userShopForm.setShopMethod(Constants.SHOP_BY_CATEGORY);
            long startTime = System.currentTimeMillis();
            errors = UserShopLogic.init(request, userShopForm);
            long endTime = System.currentTimeMillis();
            log.info("UserShopLogic.init time: " + (endTime - startTime)/1000);
            //populate the form with a map of categories to items, so we can display the count of
            //items per category in the menu
            startTime = System.currentTimeMillis();
            errors.add(UserShopLogic.populateCategoryToItemMap(request, userShopForm));
            endTime = System.currentTimeMillis();
            log.info("category to item map population time: " + (endTime - startTime)/1000);
            sessionData.setUserShopForm(userShopForm);
            //request.getSession().setAttribute(Constants.USER_SHOP_FORM, userShopForm);
            
            //STJ-5774: 
    		ShoppingLogic.refreshShoppingLists(request);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.exceptionGettingLocationInfo", new String[] {e.getMessage()});
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }
    	
    	return errors;
    }

    /**
     * Determine the logo to display in the header.
     * @param  request  The HTTP request we are processing.
     * @param  userId  The id of the currently logged in user.
     * @param  locationId  The id of the target location (either most recently visited when first logging in
     * 		or most recently selected when choosing a location)
     * @param  storeId  The id of the parent store of the target location.
     * @return An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors setHeaderLogo(HttpServletRequest request, int userId,
    		int locationId, int storeId) {
        ActionErrors errors = new ActionErrors();
        String headerLogo = null;
		Locale userLocale = ClwI18nUtil.getUserLocale(request);
		String userLocaleStr = (userLocale != null ? userLocale.toString() : Locale.US.toString());
    	try {
    		APIAccess factory = (APIAccess) request.getSession(false).getAttribute(Constants.APIACCESS);
    		PropertyService propertyService = factory.getPropertyServiceAPI();
	    	//first try to use the location id specified
	    	if (locationId > 0) {
	    		Account accountService = factory.getAccountAPI();
	    		int accountId = accountService.getAccountIdForSite(locationId);
	    		if (accountId > 0) {
		        	PropertyDataVector properties = propertyService.getProperties(null, accountId, RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO1);
		        	headerLogo = determineHeaderLogo(properties, userId, userLocaleStr);
	    		}
	    	}
	    	//if we've failed to find a value for the logo via the account id, use the store id
	    	if (!Utility.isSet(headerLogo)) {
	        	PropertyDataVector properties = propertyService.getProperties(null, storeId, RefCodeNames.PROPERTY_TYPE_CD.UI_LOGO1);
	        	headerLogo = determineHeaderLogo(properties, userId, userLocaleStr);
	    	}
	    	//store the logo in the session data util object
	        Utility.getSessionDataUtil(request).setHeaderLogo(headerLogo);
    	}
    	catch (Exception e) {
        	log.error("Unable to determine header logo.");
			String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    	}
		return errors;
    }
    
    private static String determineHeaderLogo(PropertyDataVector properties, int userId, String localeCd) {
    	String returnValue = null;
    	if (Utility.isSet(properties)) {
    		String userAndLocaleMatch = null;
    		String userMatch = null;
    		String localeMatch = null;
    		String defaultMatch = null;
    		Iterator<PropertyData> propertyIterator = properties.iterator();    	
    		while (propertyIterator.hasNext()) {
    			PropertyData property = propertyIterator.next();
    			if (property.getUserId() > 0 && userId == property.getUserId() && 
    					Utility.isSet(property.getLocaleCd()) && property.getLocaleCd().equals(localeCd)) {
    				userAndLocaleMatch = property.getValue();
    			}
    			else if (property.getUserId() > 0 && userId == property.getUserId() && 
    					!Utility.isSet(property.getLocaleCd())) {
    				userMatch = property.getValue();
    			}
    			else if (property.getUserId() == 0 && 
    					Utility.isSet(property.getLocaleCd()) && property.getLocaleCd().equals(localeCd)) {
    				localeMatch = property.getValue();
    			}
    			else if (property.getUserId() == 0 && 
    					!Utility.isSet(property.getLocaleCd())) {
    				defaultMatch = property.getValue();
    			}
    		}
    		if (Utility.isSet(userAndLocaleMatch)) {
    			returnValue = userAndLocaleMatch;
    		}
    		else if (Utility.isSet(userMatch)) {
    			returnValue = userMatch;
    		}
    		else if (Utility.isSet(localeMatch)) {
    			returnValue = localeMatch;
    		}
    		else if (Utility.isSet(defaultMatch)) {
    			returnValue = defaultMatch;
    		}
    	}
    	return returnValue;
    }
    
    /**
     * Find the previous orders matching a set of criteria.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   orderSearchDto - an <code>OrderSearchDto</code> containing the search criteria.
     * @return  An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors performPreviousOrdersSearch(HttpServletRequest request, OrderSearchDto orderSearchDto) {
    	ActionErrors errors = validatePreviousOrderCriteria(request, orderSearchDto);
    	//if the order search criteria is invalid return the errors
    	if (errors != null && !errors.isEmpty()) {
    		return errors;
    	}
    	//STJ-5775
    	OrderSearchForm form = new OrderSearchForm();
    	//set the location id on the form
    	form.setSiteSiteId(orderSearchDto.getLocationId());
    	//STJ-4759 - use revised order date when filtering orders
    	form.setFilterByRevisedOrderDate(true);
    	try {
    		errors = OrderSearchLogic.search(request, form,Constants.PREVIOUS_ORDERS);//STJ-5775
    	}
    	catch (Exception e) {
        	log.error("Exception occurred when searching for previous orders...");
            String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.problemRetrievingOrders", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
    	}
    	if (errors == null || errors.isEmpty()) {
    		orderSearchDto.setMatchingOrders(form.getResultList());
    	}
		return errors;
    }
    
    /*
     * Private method to validate any previous order search/sort criteria is valid
     */
    private static ActionErrors validatePreviousOrderCriteria(HttpServletRequest request, OrderSearchDto orderSearchDto) {
    	ActionErrors errors = new ActionErrors();
        CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
    	if (orderSearchDto == null) {
        	log.error("Missing order search criteria encountered.");
            String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.problemRetrievingOrders", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
    	else {
    		//make sure the user id is specified and valid
    		if (!Utility.isSet(orderSearchDto.getUserId()) ||
    			Integer.valueOf(orderSearchDto.getUserId().trim()) != user.getUserId()) {
            	log.error("Invalid user id search criteria encountered.");
                String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.problemRetrievingOrders", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    		}
        	//make sure the location id is specified and valid
        	SiteData currentLocation = ShopTool.getCurrentSite(request);
        	if (!Utility.isSet(orderSearchDto.getLocationId()) ||
        			currentLocation == null || 
        			!orderSearchDto.getLocationId().equalsIgnoreCase(Integer.toString(currentLocation.getSiteId()))) {
                String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.problemRetrievingOrders", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));  		
        	}
    	}
    	return errors;
    }

    /**
     * Find the pending orders matching a set of criteria.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   orderSearchDto - an <code>OrderSearchDto</code> containing the search criteria.
     * @return  An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors performPendingOrdersSearch(HttpServletRequest request, OrderSearchDto orderSearchDto) {

    	ActionErrors errors = validatePendingOrderCriteria(request, orderSearchDto);
    	//if the order search criteria is invalid return the errors
    	if (errors != null && !errors.isEmpty()) {
    		return errors;
    	}
    	
    	boolean pOrdersAccountsSupportBudget = false;
    	
    	//specify the order status values we should retrieve (just pending approval).
    	List<String> statusValues = new ArrayList<String>();
    	statusValues.add(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL);
    	orderSearchDto.setOrderStatuses(statusValues);
    	
    	//specify that customer notes and workflow notes are to be retrieved during the search
	    List<String> orderProperties = new ArrayList<String>();
	    orderProperties.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
	    orderProperties.add(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
	    orderSearchDto.setOrderProperties(orderProperties);
    	
    	//retrieve the orders.
        long startTime = System.currentTimeMillis();
		OrderStatusDescDataVector pendingOrders = null;
    	try {
    		pendingOrders = OrderSearchLogic.listPendingOrders(request, orderSearchDto);
    	}
    	catch (Exception e) {
        	log.error("Exception occurred when searching for pending orders...");
            String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.search.error.problemRetrievingOrders", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
    	}
    	long endTime = System.currentTimeMillis();
    	log.info("Order retrieval: " + (endTime - startTime)/1000);
    	
    	if (errors == null || errors.isEmpty()) {
			//NOTE - return all pending orders, even if the user is not allowed to approve any of the
			//reasons a given order is in a pending state.  Downstream code will ensure that a user
    		//cannot approve/reject an order for which they are not authorized.
    		if (Utility.isSet(pendingOrders) && !orderSearchDto.isRetrieveBasicInfoOnly()) {
        		//iterate over the pending orders, taking the following actions:
    			//	1. populate each order with a list of reason codes the user is allowed to approve.
    			//	2. populate each order with information about the remaining budget for its location. 
    			//	3. populate any approved workflow note(s) on each order with the user name of its 
    			//		approver.
    			CleanwiseUser user = ShopTool.getCurrentUser(request);
        		Map<Integer,UserData> userIdToApprovingUserMap = new HashMap<Integer,UserData>();
        		try {
    	    		Order orderBean = APIAccess.getAPIAccess().getOrderAPI();
    	    		Site siteBean = APIAccess.getAPIAccess().getSiteAPI();
    	    		Account acctBean = APIAccess.getAPIAccess().getAccountAPI();
    	    		
	    			//	1. populate each order with a list of reason codes the user is allowed to approve.
    	    		Iterator<OrderStatusDescData> pendingOrderIterator = pendingOrders.iterator();
    	    		startTime = System.currentTimeMillis();
    	    		while (pendingOrderIterator.hasNext()) {
    	    			OrderStatusDescData pendingOrder = pendingOrderIterator.next();
    	    			IdVector userApprovableReasonCodeIds = orderBean.getPropertiesUserCanApprove(user.getUser(), 
        	            		pendingOrder.getOrderDetail().getOrderId());
            	        pendingOrder.setUserApprovableReasonCodeIds(userApprovableReasonCodeIds);
    	    		}
    	    		endTime = System.currentTimeMillis();
    	    		log.info("Reason code retrieval: " + (endTime - startTime)/1000);
    	    		
        			//	2. populate each order with information about the remaining budget for its location.
    	    		pendingOrderIterator = pendingOrders.iterator();
    	    		startTime = System.currentTimeMillis();
	    			Set<Integer> locationIdSet = new HashSet<Integer>();
    	    		while (pendingOrderIterator.hasNext()) {
    	    			OrderStatusDescData pendingOrder = pendingOrderIterator.next();
    	    			locationIdSet.add(pendingOrder.getOrderDetail().getSiteId());
    	    		}
    	    		IdVector locationIds = new IdVector();
    	    		locationIds.addAll(locationIdSet);
	    			SiteDataVector locations = siteBean.getLocations(locationIds);
	    			Iterator<SiteData> locationIterator = locations.iterator();
	    			Map<Integer,SiteData> idToLocationMap = new HashMap<Integer, SiteData>();
	    			while (locationIterator.hasNext()) {
	    				SiteData location = locationIterator.next();
	    				idToLocationMap.put(location.getSiteId(), location);
	    			}
    	    		pendingOrderIterator = pendingOrders.iterator();
    	    		while (pendingOrderIterator.hasNext()) {
    	    			OrderStatusDescData pendingOrder = pendingOrderIterator.next();
    	    			SiteData site = idToLocationMap.get(pendingOrder.getOrderDetail().getSiteId());
	    				pendingOrder.setOrderSiteData(site);
    	    		}
    	    		endTime = System.currentTimeMillis();
    	    		log.info("Site/budget retrieval: " + (endTime - startTime)/1000);
    	    		
	    			//	3. populate any approved workflow note(s) on each order with the user name of its 
	    			//		approver.
    	    		pendingOrderIterator = pendingOrders.iterator();
    	    		startTime = System.currentTimeMillis();
    	    		while (pendingOrderIterator.hasNext()) {
    	    			OrderStatusDescData pendingOrder = pendingOrderIterator.next();
    					if (Utility.isSet(pendingOrder.getOrderPropertyList())) {
    						Iterator<OrderPropertyData> propertyIterator = pendingOrder.getOrderPropertyList().iterator();
    						while (propertyIterator.hasNext()) {
    							OrderPropertyData property = propertyIterator.next();
    							if (property.getApproveDate() != null) {
    								String approvingUserName = StringUtils.EMPTY;
    								int userId = property.getApproveUserId();
    								if (userId > 0) {
    									UserData approvingUser = userIdToApprovingUserMap.get(userId);
    									if (approvingUser == null) {
    										try {
    											approvingUser = APIAccess.getAPIAccess().getUserAPI().getUser(property.getApproveUserId());
    											if (approvingUser != null) {
    												userIdToApprovingUserMap.put(userId, approvingUser);
    											}
    										}
    										catch (Exception e) {
    											//nothing to do here - a blank value will be used.
    										}
    									}
    									if (approvingUser != null) {
    										approvingUserName = approvingUser.getUserName();
    									}
    								}
    								property.setModBy(approvingUserName);
    							}
    						}
    						sortProperties(pendingOrder.getOrderPropertyList());		
    					}
    	    		}
    	    		endTime = System.currentTimeMillis();
    	    		log.info("Approver name retrieval: " + (endTime - startTime)/1000);
    	    		
    	    		//get supports budgets

    	    		IdVector acctIds = new IdVector();
    	    		pendingOrderIterator = pendingOrders.iterator();
    	    		while (pendingOrderIterator.hasNext()) {
    	    			OrderStatusDescData pendingOrder = pendingOrderIterator.next();
    	    			acctIds.add(pendingOrder.getAccountId());
    	    		}
    	    		
    	    		List shortDesc = new ArrayList();
    	    		shortDesc.add(RefCodeNames.PROPERTY_TYPE_CD.SUPPORTS_BUDGET);
    	    		Map acctPropMap = acctBean.getPropertiesForAccounts(acctIds, shortDesc, null, false);
    	    		if(acctPropMap!=null && acctPropMap.size()>0){
    	    			Iterator it = acctPropMap.keySet().iterator();
    	    			while(it.hasNext()){
    	    				Integer acctKey = (Integer)it.next();
    	    				List props = (List)acctPropMap.get(acctKey);
    	    				Iterator it2 = props.iterator();
    	    				while(it2.hasNext()){
    	    					PropertyData propD = (PropertyData)it2.next();
    	    					if(propD.getValue().equalsIgnoreCase("true")){
    	        	    			pOrdersAccountsSupportBudget = true;
    	    					}
    	    				}
    	    			}
    	    			
    	    		}
        		}
    	    	catch (Exception e) {
    	        	log.error("Exception occurred when searching for pending orders...");
    	            String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.search.error.problemRetrievingOrders", null);
    	            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
    	            return errors;
    	    	}
    		}
    		orderSearchDto.setMatchingOrders(pendingOrders);
    		
    		orderSearchDto.setOrdersAccountsSupportBudget(pOrdersAccountsSupportBudget);
    	}

		return errors;
    }
    
    /*
     * Private method to validate any pending order search/sort criteria is valid
     */
    private static ActionErrors validatePendingOrderCriteria(HttpServletRequest request, OrderSearchDto orderSearchDto) {
    	ActionErrors errors = new ActionErrors();
    	if (orderSearchDto == null) {
        	log.error("Missing order search criteria encountered.");
            String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.problemRetrievingOrders", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
    	else {
    		//make sure the user id is specified and valid
            CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
    		if (!Utility.isSet(orderSearchDto.getUserId()) ||
    			Integer.valueOf(orderSearchDto.getUserId().trim()) != user.getUserId()) {
            	log.error("Invalid user id search criteria encountered.");
                String errorMess = ClwI18nUtil.getMessage(request, "previousOrder.search.error.problemRetrievingOrders", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    		}
    	}
    	return errors;
    }

    /*
     * Private method to validate any most recent order search/sort criteria is valid
     */
    private static ActionErrors validateMostRecentOrderCriteria(
            HttpServletRequest request, OrderSearchDto orderSearchDto) {
        ActionErrors errors = new ActionErrors();
        if (orderSearchDto == null) {
            log.error("Missing order search criteria encountered.");
            String errorMess = ClwI18nUtil.getMessage(request,
                    "mostRecentOrder.search.error.problemRetrievingOrder",
                    null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        } else {
            // make sure the user id is specified and valid
            CleanwiseUser user = (CleanwiseUser) request.getSession()
                    .getAttribute(Constants.APP_USER);
            if (!Utility.isSet(orderSearchDto.getUserId())
                    || Integer.valueOf(orderSearchDto.getUserId().trim()) != user
                            .getUserId()) {
                log.error("Invalid user id search criteria encountered.");
                String errorMess = ClwI18nUtil.getMessage(request,
                        "mostRecentOrder.search.error.problemRetrievingOrder",
                        null);
                errors.add("error", new ActionError("error.simpleGenericError",
                        errorMess));
            }
        }
        return errors;
    }
    
    /**
     * Add a comment to an order.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   comment - an <code>OrderPropertyData</code> containing the comment information.
     */
    public static String performAddOrderCommentResponseJson(HttpServletRequest request, HttpServletResponse response, OrderPropertyData newComment) {
    	//trim any leading/trailing whitespace from the comment
    	if (newComment != null && newComment.getValue() != null) {
    		newComment.setValue(newComment.getValue().trim());
    	}
    	//validate the comment data
    	List<String> errors = validateOrderComment(request, newComment);
    	String returnValue = null;
    	if (Utility.isSet(errors)) {
            StringBuffer responseJson = new StringBuffer();
            responseJson.append("{\"errors\": [");
            boolean includeComma = false;
            Iterator<String> errorIterator = errors.iterator();
            while (errorIterator.hasNext()) {
            	if (includeComma) {
            		responseJson.append(", ");
            	}
            	includeComma = true;
            	String error = errorIterator.next();
            	responseJson.append("{\"error\": \"");
            	String errorText = Utility.escapeStringForJSON(error);
            	responseJson.append(errorText);
            	responseJson.append("\"}");
            }
            responseJson.append("]}");
            returnValue = responseJson.toString();
    	}
    	else {
    		HttpSession session = request.getSession();
    		//save any existing form currently in the session
    		OrderOpDetailForm existingForm = (OrderOpDetailForm) session.getAttribute(Constants.ORDER_OP_DETAIL_FORM);
    		try {
    			//call out to the order detail logic class to create the note
    			OrderOpDetailForm commentForm = new OrderOpDetailForm();
    			OrderData orderDetail = new OrderData();
    			OrderStatusDescData order = new OrderStatusDescData();
    			order.setOrderDetail(orderDetail);
    			commentForm.setOrderStatusDetail(order);
    			orderDetail.setOrderId(newComment.getOrderId());
    			commentForm.setCustomerComment(newComment.getValue());
    			OrderDetailLogic.updateCustomerNote(request, commentForm);
        		//retrieve the list of comments for the order (including the comment just added)
                APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
                if (null == factory) {
                    throw new Exception("Without APIAccess.");
                }
        		OrderPropertyDataVector comments = factory.getOrderAPI().getOrderPropertyCollection(newComment.getOrderId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS);
        		//return the comments as a JSON string
                StringBuffer responseJson = new StringBuffer();
                responseJson.append("{\"comments\": [");
                boolean includeComma = false;
                Iterator commentIterator = comments.iterator();
                while (commentIterator.hasNext()) {
                	if (includeComma) {
                		responseJson.append(", ");
                	}
                	includeComma = true;
                	OrderPropertyData comment = (OrderPropertyData) commentIterator.next();
                	responseJson.append("{\"date\": \"");
                	responseJson.append(ClwI18nUtil.formatDateInp(request, comment.getAddDate()));
                	responseJson.append("\",\"comment\": \"");
                	String commentText = Utility.escapeStringForJSON(comment.getValue());
                	responseJson.append(commentText);
                	responseJson.append("\",\"author\": \"");
                	responseJson.append(comment.getAddBy());
                	responseJson.append("\"}");
                }
                responseJson.append("]}");
                returnValue = responseJson.toString();
    		}
    		catch (Exception e) {
    			String error = ClwI18nUtil.getMessage(request, "error.unExpectedError", null);
                StringBuffer responseJson = new StringBuffer();
                responseJson.append("{\"errors\": [");
            	responseJson.append("{\"error\": \"");
            	String errorText = Utility.escapeStringForJSON(error);
            	responseJson.append(errorText);
            	responseJson.append("\"}");
                responseJson.append("]}");
                returnValue = responseJson.toString();
    		}
    		finally {
    			//restore the original form in the session
    			session.setAttribute(Constants.ORDER_OP_DETAIL_FORM, existingForm);
    		}
    		
    	}
    	return returnValue;
    }

    /*
     * Private method to validate that an order comment can be added and is valid
     */
    private static List<String> validateOrderComment(HttpServletRequest request, OrderPropertyData comment) {
    	List<String>errors = new ArrayList<String>();
    	//if the user has the browse only privilege set, they are not allowed to add comments
    	if (ShopTool.getCurrentUser(request).isBrowseOnly()) {
    		errors.add(ClwI18nUtil.getMessage(request, "pendingOrders.error.notAuthorizedToAddComment", null));
    	}
    	//otherwise validate the comment
    	else {
        	if (comment == null || !Utility.isSet(comment.getValue())) {
        		errors.add(ClwI18nUtil.getMessage(request, "pendingOrders.error.noCommentTextProvided", null));
        	}
        	else {
        		boolean maxLengthExceeded = comment.getValue().length() > Constants.MAX_LENGTH_ORDER_PROPERTY_VALUE;
        		if (maxLengthExceeded) {
                	Object[] insertionStrings = new Object[1];
                	insertionStrings[0] = Integer.toString(Constants.MAX_LENGTH_ORDER_PROPERTY_VALUE);
                	errors.add(ClwI18nUtil.getMessage(request,"pendingOrders.error.maximumCommentLengthExceeded", insertionStrings)); 
        		}
        		if (comment.getOrderId() <= 0) {
        			errors.add(ClwI18nUtil.getMessage(request, "pendingOrders.error.noOrderIdSpecifiedForComment"));
        		}
        		else {
        			//TODO - validate the the user has access to the specified order.
        		}
        	}
    	}
    	return errors;
    }

    /**
     * Approve one or more pending orders.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   orderIds - a <code>String</code> array containing the ids of the pending orders to approve.
     * @param	approvalDate - a <code>String</code> representing the date upon which the orders should be approved.
     * @param	poNumber - a <code>String</code> representing the a purchase order number.
     * @return  A <code>Map</code> object containing any errors and/or messages.
     */
    public static synchronized Map<String,Object> performApprovePendingOrders(HttpServletRequest request, String[] orderIds, 
    		String approvalDate, String poNumber, boolean checkPassedPoNum) {
    	Map<String,Object> returnValue = new HashMap<String,Object>();
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	returnValue.put(ActionErrors.GLOBAL_ERROR, errors);
    	returnValue.put(ActionErrors.GLOBAL_MESSAGE, messages);
    	//trim any user entered values before use
    	if (approvalDate != null) {
    		approvalDate = approvalDate.trim();
    	}
    	ActionErrors validationErrors = validateApprovePendingOrdersCriteria(request, orderIds, approvalDate);
    	//if any errors occurred return them
    	if (validationErrors != null && !validationErrors.isEmpty()) {
    		errors.add(validationErrors);
    		return returnValue;
    	}
    	
		//get the list of pending orders for this user
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setUserId(Integer.toString(appUser.getUserId()));
	    //specify the order information that should be retrieved
		orderSearchDto.setRetrieveOrderHistory(false);
		orderSearchDto.setRetrieveOrderItems(false);
		orderSearchDto.setRetrieveOrderAddresses(true);
		orderSearchDto.setRetrieveOrderAccount(false);
		orderSearchDto.setRetrieveOrderMetaData(false);
		orderSearchDto.setRetrieveOrderReceptionData(false);
		orderSearchDto.setRetrieveOrderAutoOrderData(false);
		orderSearchDto.setRetrieveOrderProperties(true);
		performPendingOrdersSearch(request, orderSearchDto);
		OrderStatusDescDataVector pendingOrders = (OrderStatusDescDataVector) orderSearchDto.getMatchingOrders();
		
		//iterate over the order ids, taking the following actions
		//	- verify the order is a pending order for the user - if not return an error.
		//	- verify the user can approve at least one of the reasons for the order being in a pending
		//		approval state - if not return an error.
		//	- approve the order.  Note that this doesn't necessarily mean the order will have its status changed,
		//		because there may be one or more reasons the user is not authorized to approve still waiting to
		//		be handled.
		List approvedOrderIds = new ArrayList();
		for (int i = 0; i < orderIds.length; i++) {
			int orderId = Integer.valueOf(orderIds[i]);
			OrderStatusDescData pendingOrder = getPendingOrder(pendingOrders, orderId);
			if (pendingOrder == null) {
            	Object[] insertionStrings = new Object[1];
            	try {
	            	Order orderBean = APIAccess.getAPIAccess().getOrderAPI();
			        OrderJoinData orderJD = orderBean.fetchOrder(orderId);
	            	insertionStrings[0] = orderJD.getOrderNum();
	                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.orderCannotBeApproved", insertionStrings);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            	} catch (Exception e) {
            		insertionStrings[0] = Integer.toString(orderId);
            		String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.unexpectedApprovalError", insertionStrings);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            	}
			}
			else {
				IdVector reasonsToApprove = new IdVector();
				IdVector userApprovableReasons = pendingOrder.getUserApprovableReasonCodeIds();
				if (Utility.isSet(pendingOrder.getOrderPropertyList())) {
					Iterator<OrderPropertyData> propertyIterator = pendingOrder.getOrderPropertyList().iterator();
					while (propertyIterator.hasNext()) {
						OrderPropertyData property = propertyIterator.next();
						if (Constants.WORKFLOW_NOTE.equalsIgnoreCase(property.getShortDesc()) &&
								property.getApproveDate() == null &&
								userApprovableReasons.contains(property.getOrderPropertyId())) {
							reasonsToApprove.add(property.getOrderPropertyId());
						}
					}
					sortProperties(pendingOrder.getOrderPropertyList());
				}
				if (!Utility.isSet(reasonsToApprove)) {
	            	Object[] insertionStrings = new Object[1];
	            	insertionStrings[0] = pendingOrder.getOrderDetail().getOrderNum();
	                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.userCannotApproveOrder", insertionStrings);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
				}
				else {
					//note - this logic was taken from the HandleOrderLogic.approveOrder method.  That
					//method was not used directly because it sets the users site to be that of the
					//order being processed, makes use of a number of session attributes, pulls values
					//from the request, etc.
					try {
				        Order orderBean = APIAccess.getAPIAccess().getOrderAPI();
				        OrderJoinData orderJD = orderBean.fetchOrder(orderId);
	
				        //validate purchase order information.  
				        boolean purchaseOrderIssueOccurred = false;
				        boolean allowPoEntry = true;
				        if (RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(appUser.getUserAccount().getCustomerSystemApprovalCd())){
				        	allowPoEntry = false;
				        }
				        boolean f_showPO = true;
				        if (orderJD.getOrderCC() != null) {
				        	f_showPO = false;
				        }
			            SiteData site = SessionTool.getSiteDataNoCache(request,  orderJD.getOrder().getSiteId());

				        if (site.getBlanketPoNum() != null && 
				        		site.getBlanketPoNum().getBlanketPoNumId() != 0){
				        	allowPoEntry = false;
				        }
				        String poNum = orderJD.getOrder().getRequestPoNum();
				        if (allowPoEntry && f_showPO){
				           if (checkPassedPoNum) {
				        	  if (!Utility.isSet(poNumber) && appUser.getPoNumRequired()) {
				        		Object[] insertionStringsI = new Object[1];
				        		insertionStringsI[0] = pendingOrder.getOrderDetail().getOrderNum();
				        		String errorMess = ClwI18nUtil.getMessage(request,"pendingOrders.errors.orderRequiresPoNumber",insertionStringsI);
				        		errors.add("error",	new ActionError("error.simpleGenericError", errorMess));
				        		purchaseOrderIssueOccurred = true;
				        	  }
				        	  else {
				        		poNum = poNumber;
				        	  }
				           }
				        }
				        HandleOrderLogic handleOrderLogic =
				                (HandleOrderLogic) ClwCustomizer.getJavaObject(request,"com.cleanwise.view.logic.HandleOrderLogic");
				        ActionErrors poErrors = handleOrderLogic.validatePoNum(request, poNum);
				        if (poErrors != null && !poErrors.isEmpty()) {
				            errors.add(poErrors);
				            purchaseOrderIssueOccurred = true;
				        }
	
				        //if no purchase order error occurred, approve the order
				        if (!purchaseOrderIssueOccurred) {
				        	Date processDate = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request)).parse(approvalDate);
				        	//for audit trail correctness, if the user is logged in as a proxy user then pass the original logged in
				        	//user id, not the id of the proxy user.
				        	int userId = appUser.getUserId();
				        	if (appUser.getOriginalUser() != null) {
				        		userId = appUser.getOriginalUser().getUserId();
				        	}
					        orderJD = orderBean.approveOrder(orderId, reasonsToApprove, processDate, poNum,
					        		userId, appUser.getUserName());
					        APIAccess.getAPIAccess().getIntegrationServicesAPI().updateJanitorsCloset(orderJD, true);
					        APIAccess.getAPIAccess().getSiteAPI().updateBudgetSpendingInfo(site);
				            Object[] insertionStrings = new Object[1];
				            if (Utility.isSet(orderJD.getOrderNum())) {
				            	insertionStrings[0] = orderJD.getOrderNum(); 
				            }
				            else {
				            	insertionStrings[0] = Integer.toString(orderId);
				            }
				            String successMessage = ClwI18nUtil.getMessage(request, "pendingOrders.message.orderWasApproved", insertionStrings);
				            messages.add("message", new ActionMessage("message.simpleMessage", successMessage));
				            approvedOrderIds.add(orderId);
				        }
					}
					catch (Exception e) {
		            	Object[] insertionStrings = new Object[1];
			            if (pendingOrder != null && Utility.isSet(pendingOrder.getOrderDetail().getOrderNum())) {
			            	insertionStrings[0] = pendingOrder.getOrderDetail().getOrderNum(); 
			            }
			            else {
			            	insertionStrings[0] = Integer.toString(orderId);
			            }
		                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.unexpectedApprovalError", insertionStrings);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
				}
			}
		}
    	
		//remove any approved order ids from the list of order ids passed in, so that when the
		//user is returned to the pending orders page those orders do not have their "Select"
		//checkbox checked.  This happens when the user can only handle some (but not all) of the
		//reasons an order is in a pending approval state.  In that situation the order will
		//remain in a pending approval state and will thus be shown on the pending orders screen,
		//but the user can take no further action on that order so we want to uncheck the "Select"
		//check box for it.
		for (int i=0; i<orderIds.length; i++) {
			if (approvedOrderIds.contains(new Integer(orderIds[i]))) {
				orderIds[i] = StringUtils.EMPTY;
			}
		}

		//return the errors and messages
		return returnValue;
    }

    /*
     * Private method to validate that an approve pending orders request can be handled
     */
    private static ActionErrors validateApprovePendingOrdersCriteria(HttpServletRequest request, String[] orderIds, String approvalDate) {
    	ActionErrors errors = new ActionErrors();
    	//if the user has the browse only privilege set, return an error
    	CleanwiseUser currentUser = ShopTool.getCurrentUser(request);
    	if (currentUser.isBrowseOnly()) {
            String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.notAuthorizedToApproveOrders", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
    	//otherwise validate the approve orders information
    	else {
    		if (!Utility.isSet(approvalDate)) {
                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.noApprovalDate", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    		}
    		else {
    			try {
    				Date appDate = ClwI18nUtil.parseDateInp(request, approvalDate);
    	            Date now = new Date();
    	       		Calendar gCalendar = Calendar.getInstance();
            		gCalendar.setTime(appDate);
            		gCalendar.set(Calendar.HOUR_OF_DAY, 23);
            		gCalendar.set(Calendar.MINUTE, 59);
            		gCalendar.set(Calendar.SECOND, 59);
            		appDate = gCalendar.getTime();
            		if (appDate.before(now)) {
                        String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.pastApprovalDate", null);
                        errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
            		}
    			}
    			catch (ParseException pe) {
                    String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.invalidApprovalDate", null);
                    errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    			}
    		}
    		if (!Utility.isSet(orderIds)) {
                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.noOrdersSelectedForApproval", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    		}
    	}
    	return errors;
    }

    /**
     * Reject one or more pending orders.
     * @param   request - the <code>HttpServletRequest</code> currently being handled.
     * @param   orderIds - a <code>String</code> array containing the ids of the pending orders to reject.
     * @return  A <code>Map</code> object containing any errors and/or messages.
     */
    public static synchronized Map<String,Object> performRejectPendingOrders(HttpServletRequest request, String[] orderIds) {
    	Map<String,Object> returnValue = new HashMap<String,Object>();
    	ActionErrors errors = new ActionErrors();
    	ActionMessages messages = new ActionMessages();
    	returnValue.put(ActionErrors.GLOBAL_ERROR, errors);
    	returnValue.put(ActionErrors.GLOBAL_MESSAGE, messages);
    	ActionErrors validationErrors = validateRejectPendingOrdersCriteria(request, orderIds);
    	//if any errors occurred return them
    	if (validationErrors != null && !validationErrors.isEmpty()) {
    		errors.add(validationErrors);
    		return returnValue;
    	}
    	
		//get the list of pending orders for this user
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		orderSearchDto.setUserId(Integer.toString(ShopTool.getCurrentUser(request).getUserId()));
	    //specify the order information that should be retrieved
		orderSearchDto.setRetrieveOrderHistory(false);
		orderSearchDto.setRetrieveOrderItems(false);
		orderSearchDto.setRetrieveOrderAddresses(true);
		orderSearchDto.setRetrieveOrderAccount(false);
		orderSearchDto.setRetrieveOrderMetaData(false);
		orderSearchDto.setRetrieveOrderReceptionData(false);
		orderSearchDto.setRetrieveOrderAutoOrderData(false);
		orderSearchDto.setRetrieveOrderProperties(true);
		performPendingOrdersSearch(request, orderSearchDto);
		OrderStatusDescDataVector pendingOrders = (OrderStatusDescDataVector) orderSearchDto.getMatchingOrders();
		
		//iterate over the order ids, taking the following actions
		//	- verify the order is a pending order for the user - if not return an error.
		//	- verify the user can approve at least one of the reasons for the order being in a pending
		//		approval state - if not return an error.
		//	- reject the order
		for (int i = 0; i < orderIds.length; i++) {
			int orderId = Integer.valueOf(orderIds[i]);
			OrderStatusDescData pendingOrder = getPendingOrder(pendingOrders, orderId);
			if (pendingOrder == null) {
            	Object[] insertionStrings = new Object[1];
            	insertionStrings[0] = Integer.toString(orderId);
                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.orderCannotBeRejected", insertionStrings);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
			}
			else {
				IdVector reasonsToApprove = new IdVector();
				IdVector userApprovableReasons = pendingOrder.getUserApprovableReasonCodeIds();
				if (Utility.isSet(pendingOrder.getOrderPropertyList())) {
					Iterator<OrderPropertyData> propertyIterator = pendingOrder.getOrderPropertyList().iterator();
					while (propertyIterator.hasNext()) {
						OrderPropertyData property = propertyIterator.next();
						if (Constants.WORKFLOW_NOTE.equalsIgnoreCase(property.getShortDesc()) &&
								property.getApproveDate() == null &&
								userApprovableReasons.contains(property.getOrderPropertyId())) {
							reasonsToApprove.add(property.getOrderPropertyId());
						}
					}
					sortProperties(pendingOrder.getOrderPropertyList());
				}
				if (!Utility.isSet(reasonsToApprove)) {
	            	Object[] insertionStrings = new Object[1];
	            	insertionStrings[0] = pendingOrder.getOrderDetail().getOrderNum();
	                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.userCannotRejectOrder", insertionStrings);
	                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
				}
				else {
					try {
						//note - this logic was taken from the HandleOrderLogic.rejectOrder method.  That
						//method was not used directly because it sets the users site to be that of the
						//order being processed, makes use of a number of session attributes, etc.
	                    OrderJoinData order = APIAccess.getAPIAccess().getOrderAPI().updateOrderInfo(orderId,
	                    		RefCodeNames.ORDER_STATUS_CD.REJECTED,
	                    		ShopTool.getCurrentUser(request).getUserName());
			            Object[] insertionStrings = new Object[1];
			            if (Utility.isSet(order.getOrderNum())) {
			            	insertionStrings[0] = order.getOrderNum(); 
			            }
			            else {
			            	insertionStrings[0] = Integer.toString(orderId);
			            }
			            String successMessage = ClwI18nUtil.getMessage(request, "pendingOrders.message.orderWasRejected", insertionStrings);
			            messages.add("message", new ActionMessage("message.simpleMessage", successMessage));
					}
					catch (Exception e) {
		            	Object[] insertionStrings = new Object[1];
			            if (pendingOrder != null && Utility.isSet(pendingOrder.getOrderDetail().getOrderNum())) {
			            	insertionStrings[0] = pendingOrder.getOrderDetail().getOrderNum(); 
			            }
			            else {
			            	insertionStrings[0] = Integer.toString(orderId);
			            }
		                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.unexpectedRejectionError", insertionStrings);
		                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
					}
				}
			}
		}
    	
		return returnValue;
    }

    /*
     * Private method to validate that a reject pending orders request can be handled
     */
    private static ActionErrors validateRejectPendingOrdersCriteria(HttpServletRequest request, String[] orderIds) {
    	ActionErrors errors = new ActionErrors();
    	//if the user has the browse only privilege set, return an error
    	CleanwiseUser currentUser = ShopTool.getCurrentUser(request);
    	if (currentUser.isBrowseOnly()) {
            String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.notAuthorizedToRejectOrders", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
    	//otherwise validate the reject orders information
    	else {
    		if (!Utility.isSet(orderIds)) {
                String errorMess = ClwI18nUtil.getMessage(request, "pendingOrders.error.noOrdersSelectedForRejection", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    		}
    	}
    	return errors;
    }
    
    //method to determine if a given order id corresponds to a pending order accessible to a user
    private static OrderStatusDescData getPendingOrder(OrderStatusDescDataVector pendingOrders, int orderId) {
    	OrderStatusDescData returnValue = null;
		Iterator<OrderStatusDescData> orderIterator = pendingOrders.iterator();
		while (orderIterator.hasNext() && returnValue == null) {
			OrderStatusDescData order = orderIterator.next();
			if (order.getOrderDetail().getOrderId() == orderId) {
				returnValue = order;
			}
		}
    	return returnValue;
    }

    public static ActionErrors performMostRecentOrderSearch(
            HttpServletRequest request, OrderSearchDto orderSearchDto,
            DashboardForm dashboardForm) {
        ActionErrors errors = validateMostRecentOrderCriteria(request,
                orderSearchDto);
        // if the order search criteria is invalid return the errors
        if (errors != null && !errors.isEmpty()) {
            return errors;
        }
        try {
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess) session
                    .getAttribute(Constants.APIACCESS);
            OrderStatusCriteriaData searchCriteria = OrderStatusCriteriaData
                    .createValue();
            searchCriteria.setUserId(orderSearchDto.getUserId());
            // searchCriteria.setUserTypeCd();
            searchCriteria.setSiteId(orderSearchDto.getLocationId());
            searchCriteria.setMaxRows(1);
            //STJ-4759 order by revised order date
            searchCriteria.setOrderBy(OrderDataAccess.REVISED_ORDER_DATE);
            searchCriteria.setOrderDirection(Constants.DB_SORT_ORDER_DESCENDING);

            List<String> statusList = new ArrayList<String>();
            statusList.add(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
            searchCriteria.getOrderStatusList().addAll(statusList);

            Order orderEjb = factory.getOrderAPI();
            ProductInformation productInfoEjb = factory
                    .getProductInformationAPI();
            OrderStatusDescDataVector orderStatusList = orderEjb
                    .getOrderStatusDescCollection(searchCriteria);
            if (orderStatusList != null && orderStatusList.isEmpty() == false) {
                OrderStatusDescData orderStatusDesc = (OrderStatusDescData) orderStatusList
                        .get(0);
                Map<Integer, ProductData> productDataByItemIdMap = getProductDataByItemIdMap(
                        productInfoEjb, orderStatusDesc.getOrderItemList());
                dashboardForm.setProductDataByItemIdMap(productDataByItemIdMap);
            }
            orderSearchDto.setMatchingOrders(orderStatusList);
        } catch (Exception e) {
            log.error("Unable to retrieve Most Recent Orders for the logged in user.");
            String errorMess = ClwI18nUtil
                    .getMessage(
                            request,
                            "mostRecentOrder.search.error.problemRetrievingOrder",
                            null);
            errors.add("error", new ActionError("error.simpleGenericError",
                    errorMess));
        }
        return errors;
    }

    public static Map<Integer, ProductData> getProductDataByItemIdMap(
            ProductInformation pProductInfoEjb,
            OrderItemDataVector pOorderItemDV) throws RemoteException {
        IdVector itemIds = new IdVector();
        for (int k = 0; pOorderItemDV != null && k < pOorderItemDV.size(); k++) {
            OrderItemData orderItemD = (OrderItemData) pOorderItemDV.get(k);
            itemIds.add(orderItemD.getItemId());
        }
        return getProductDataByItemIdMap(pProductInfoEjb, itemIds);
    }

    public static Map<Integer, ProductData> getProductDataByItemIdMap(
            ProductInformation productInfoEjb, IdVector itemIds)
            throws RemoteException {
        ProductDataVector productDV = productInfoEjb
                .getProductsCollectionByItemIds(itemIds);
        Map<Integer, ProductData> productDataByItemIdMap = new TreeMap<Integer, ProductData>();
        for (int i = 0; productDV != null && i < productDV.size(); i++) {
            ProductData productData = (ProductData) productDV.get(i);
            productDataByItemIdMap.put(productData.getItemData().getItemId(),
                    productData);
        }
        return productDataByItemIdMap;
    }

    public static void initEswDefaultValues(HttpServletRequest request) {
    	
    	CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
		
		//STJ-4560: Get 'Exclude Order From Budget' value and set it into the session
		boolean excludeOrderFromBudget = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET);
		sessionDataUtil.setExcludeOrderFromBudget(false);
		
		if(excludeOrderFromBudget) {
			OrderGuideData ogData = ShoppingLogic.getOrderGuideOfCart(request);
    		if(ogData!=null) {
    			String orderBudgetTypeCode = ogData.getOrderBudgetTypeCd();
    			if(Utility.isSet(orderBudgetTypeCode) &&
    					RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equalsIgnoreCase(orderBudgetTypeCode.trim())) {
    				if(excludeOrderFromBudget) {
    					sessionDataUtil.setExcludeOrderFromBudget(true);
    				}
    			} 
    		} 
		}
		
		//Reset Flag to initialize Report Controls.
		sessionDataUtil.setInitReportControls(true);
    }
    
    public static boolean isUserPasswordExpired(HttpServletRequest request) throws Exception {
    	ActionErrors errors = new ActionErrors();
    	CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
    	try {
        	APIAccess factory = new APIAccess();
        	User userBean = factory.getUserAPI();
        	return userBean.isPasswordExpired(appUser.getUser());
        } catch (Exception exc) {
			exc.printStackTrace();
			throw exc;
		}    	
    }
    
    public static boolean isUserPasswordNeedReset(HttpServletRequest request) throws Exception {
    	CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
    	try {
        	APIAccess factory = new APIAccess();
        	User userBean = factory.getUserAPI();
        	return userBean.isPasswordNeedReset(appUser.getUser());
        } catch (Exception exc) {
			exc.printStackTrace();
			throw exc;
		}    	
    }
    
    public static void sortProperties(OrderPropertyDataVector properties) {
    	Collections.sort(properties, new Comparator() {
              public int compare(Object o1, Object o2) {
                if (o1 != null && o2 != null) {
              	OrderPropertyData op1 = (OrderPropertyData)o1;
              	OrderPropertyData op2 = (OrderPropertyData)o2;
    	
              	if (Constants.WORKFLOW_NOTE.equals(op1.getShortDesc()) && "pipeline.message.nonShoppingListItem".equals(op1.getArg0()) &&
              	    Constants.WORKFLOW_NOTE.equals(op2.getShortDesc()) && "pipeline.message.nonShoppingListItem".equals(op2.getArg0())  ){
	              		int val1 = (new Integer (((OrderPropertyData)o1).getArg1())).intValue();
	    				int val2 = (new Integer (((OrderPropertyData)o2).getArg1())).intValue();
	    				return val1 - val2;
                	}
                }
                return 0;
            }
         	});
    }
    
    
}
