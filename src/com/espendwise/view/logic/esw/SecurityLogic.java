/**
 * Title: LogOnLogic 
 * Description: This is the business logic class handling the ESW log on functionality.
 */

package com.espendwise.view.logic.esw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.LogOnLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;
import com.espendwise.view.forms.esw.SecurityForm;

/**
 * Implementation of logic that handles log on functionality.
 */
public class SecurityLogic extends LogOnLogic {

    private static final Logger log = Logger.getLogger(SecurityLogic.class);
    
    /**
     * Process a log on request.
     * @param  response The HTTP response we are creating.
     * @param  request  The HTTP request we are processing.
     * @param  form     The <code>ActionForm</code> containing the data.
     * @return          An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors performLogOn (HttpServletResponse response, HttpServletRequest request,
    		ActionForm form) {
    	if (request.getParameter(Constants.OCI_VERSION) != null ) {
    		return processOCILogOn(response, request, form);
    	}
    	else {
    		return processLogOn(response, request, form);
    	}
    }
	
    /**
     * Process a log on request.
     * @param  response The HTTP response we are creating.
     * @param  request  The HTTP request we are processing.
     * @param  form     The <code>ActionForm</code> containing the data.
     * @return          An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors processLogOn (HttpServletResponse response, HttpServletRequest request,
    											ActionForm form) {
        SecurityForm theForm = (SecurityForm) form;
        ActionErrors errors = new ActionErrors();

        //trim all form values
        String userName = theForm.getUsername();
        if (Utility.isSet(userName)) {
        	userName = userName.trim();
        	theForm.setUsername(userName);
        }
        String password = theForm.getPassword();
        if (Utility.isSet(password)) {
        	password = password.trim();
        	theForm.setPassword(password);
        }
        String accessToken = theForm.getAccessToken();
        if (Utility.isSet(accessToken)) {
        	accessToken = accessToken.trim();
        	theForm.setAccessToken(accessToken);
        }
        
        //if an access token has not been specified, return an error if a username or password 
        //haven't been specified
        if (!Utility.isSet(accessToken)) {
            if (!Utility.isSet(userName)) {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingUsername", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
            }
            if (!Utility.isSet(password)) {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.missingPassword", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
            }
        }
        if (!errors.isEmpty()) {
        	return errors;
        }

        LdapItemData lUserInfo = new LdapItemData();
        lUserInfo.setUserName(userName);
        lUserInfo.setPassword(password);
        lUserInfo.setAccessToken(theForm.getAccessToken());
        lUserInfo.setPasswordHashed(theForm.isPasswordHashed());
        return logOnUser(response,request, lUserInfo);
    }
	
    /**
     * Process a simulated log on request.
     * @param  response The HTTP response we are creating.
     * @param  request  The HTTP request we are processing.
     * @param  form     The <code>ActionForm</code> containing the data.
     * @return          An <code>ActionErrors</code> object containing any errors.
     */
    public static ActionErrors performProxyLogin (HttpServletResponse response, 
    		HttpServletRequest request,	ActionForm form) {
        SecurityForm theForm = (SecurityForm) form;
        ActionErrors errors = new ActionErrors();
        
        //verify the requesting user can simulate logging in as another user
        CleanwiseUser currentUser = ShopTool.getCurrentUser(request);
        if (!currentUser.isaAdmin()) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginNotAuthorized", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
        }

        //verify a proxy user id has been provided
        String proxyUserId = theForm.getUserId();
        if (Utility.isSet(proxyUserId)) {
        	proxyUserId = proxyUserId.trim();
        	theForm.setUserId(proxyUserId);
        }
        else {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUserNotSpecified");
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
        }
        
        //if any errors have been encountered don't proceed, since the logic below depends upon
        //the checks above succeeding.
        if (!errors.isEmpty()) {
        	return errors;
        }
        
        //try to retrieve the information for the proxy user.
        UserData proxyUser = null;
    	BusEntityDataVector proxyUserStores = null;
        try {
        	User userBean = APIAccess.getAPIAccess().getUserAPI();
        	proxyUser = userBean.getUser(Integer.valueOf(proxyUserId));
        	if (proxyUser != null) {
        		proxyUserStores = userBean.getBusEntityCollection(proxyUser.getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        	}
        }
        catch (DataNotFoundException dnfe) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUserNotFound");
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
        }
        catch (Exception exc) {
            String errorMess = ClwI18nUtil.getMessage(request, "error.unExpectedError");
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
        }
        
        //verify that the proxy user, if found, is a multi-site buyer
        if (proxyUser != null) {
	    	if (!RefCodeNames.USER_TYPE_CD.MSB.equals(proxyUser.getUserTypeCd())) {
	            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUserNotAMSB");
	        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
	    	}
        }
        
    	//verify that the proxy user belongs to the same store as the current user
    	boolean matchingStoreFound = false;
    	int currentUserStoreId = currentUser.getUserStore().getStoreId();
    	if (Utility.isSet(proxyUserStores)) {
	    	Iterator<BusEntityData> storeIterator = proxyUserStores.iterator();
	    	while (storeIterator.hasNext() && !matchingStoreFound) {
	    		matchingStoreFound = (currentUserStoreId == storeIterator.next().getBusEntityId());
	    	}
    	}
    	if (!matchingStoreFound) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUserNotFound");
        	errors.add("error", new ActionError("error.simpleGenericError", errorMess));    		
    	}
        
        //if any errors have been encountered don't proceed, since the logic below depends upon
        //the checks above succeeding.
        if (!errors.isEmpty()) {
        	return errors;
        }
        
        //if we successfully retrieved the proxy user, try to log them in
        if (proxyUser != null) {
        	theForm.setUsername(proxyUser.getUserName());
        	theForm.setPassword(proxyUser.getPassword());
        	theForm.setPasswordHashed(true);
        	errors.add(processLogOn(response, request, theForm));
        }
        
        //if any errors have been encountered don't proceed, since the logic below depends upon
        //the checks above succeeding.
        if (!errors.isEmpty()) {
        	return errors;
        }
        //save the original logged in user info
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        appUser.setOriginalUser(currentUser);
                
        return errors;
    }

    /**
     * Initialize user/location information.
     * @param  request  The HTTP request we are processing.
     */
    public static ActionErrors initializeUserLocationInformation(HttpServletRequest request) {
    	
        ActionErrors errors = new ActionErrors();
        
        //first, clear any existing location related information for the user.
        CleanwiseUser user = (CleanwiseUser)request.getSession().getAttribute(Constants.APP_USER);
        user.setSite(null);
        user.setSiteNumber(0);
        user.setConfiguredLocationCount(0);        
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        sessionDataUtil.setLocationSearchDtoMap(null);
        
    	//if the user has a default location, automatically select that location.  This is done
        //here instead of later so that the default location is updated to have a last visit date
        //of now.  This handles the obscure case where the user has multiple sites configured
        //and a site that hasn't been visited is made the default.  In that case it is possible that
        //when we retrieve the sites below the default site would not be included.  By selecting the
        //site here, it will be first in the list of returned sites.
		String defaultLocation = (String) request.getSession().getAttribute(Constants.DEFAULT_SITE);
		if (Utility.isSet(defaultLocation)) {
            BusEntityData defaultLocationD = null;
            try {
            	User userEjb = APIAccess.getAPIAccess().getUserAPI();
            	if (userEjb.isSiteOfUser(Integer.parseInt(defaultLocation), user.getUserId())) {
            		defaultLocationD = APIAccess.getAPIAccess().getSiteAPI().getSite(Integer.parseInt(defaultLocation)).getBusEntity();
            	}
            }
            catch (Exception exc) {
            	log.error("Unable to retrieve default location for the logged in user.");
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.noUserSiteData", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
            if (defaultLocationD != null) {
        		errors = DashboardLogic.setUserLocation(request, user, defaultLocationD);
            }
            else {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.noUserSiteData", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
		}
        
    	//retrieve all of the locations to which the user has been configured sorted in descending
        //order of most recently visited (the sort order is necessary for the code below to identify 
        //the most recently visited location).
        //if we couldn't retrieve the location information, return an error.
    	LocationSearchDto locationSearchDto = new LocationSearchDto();
    	locationSearchDto.setUserId(Integer.toString(user.getUserId()));
    	locationSearchDto.setSortField(Constants.LOCATION_SORT_FIELD_LAST_VISIT);
    	locationSearchDto.setSortOrder(Constants.LOCATION_SORT_ORDER_DESCENDING);
    	errors = DashboardLogic.performLocationSearch(request, locationSearchDto);
    	if (errors != null && !errors.isEmpty()) {
            return errors;
        }
        
        //if the user hasn't been configured for any sites, return an error.
        int numberOfLocations = locationSearchDto.getMatchingLocations().size();
        if (numberOfLocations == 0) {
        	log.error("User has not been configured for any locations.");
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.noActiveLocation", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            return errors;
        }
        
        //initialize a variable to hold the id of the "last visited" location, to determine the logo to display 
        //in the header.
        //From STJ-4727:
        //	If the user has NOT selected a location use the Account logo of the "last visited" (this is the first 
        //  location that is displayed in the 'select a location' screen before any filtering is done) location's 
        //  parent Account. If this Account does not have a Logo then use the Store's logo.
        int mostRecentLocationId = 0;
        
        //since the locations have been returned sorted in descending order of most recently visited, 
        //if the first location in the list has a last visit date then set that location id on the search 
        //dto as the most recently visited so the UI can render that location appropriately.  Regardless of
        //whether or not it has a last visit date, get the id of the location as the first step in
        //determining the logo to display in the header.
    	if (locationSearchDto.getMatchingLocations().size() > 0) {
    		SiteData location = (SiteData)locationSearchDto.getMatchingLocations().get(0);
    		mostRecentLocationId = location.getSiteId();
    		if (location.getLastUserVisitDate() != null) {
    			locationSearchDto.setMostRecentlyVisitedSiteId(Integer.toString(location.getSiteId()));
    		}
    	}
        
        //store the results of the search in the session
    	//sessionDataUtil.setLocationSearchDto(locationSearchDto);
    	
    	//STJ-5677: Preserve 'Specify Location' search criteria separately, per each instance in the application.
    	Map<String,LocationSearchDto> locationSearchDtoMap = new HashMap<String, LocationSearchDto>();
    	for (String functionalArea:Constants.SPECIFY_LOCATIONS_FUNCTIONAL_AREAS) {
    		locationSearchDtoMap.put(functionalArea, locationSearchDto);
    	}
    	sessionDataUtil.setLocationSearchDtoMap(locationSearchDtoMap);
    	
    	//determine the logo to display in the header
        errors = DashboardLogic.setHeaderLogo(request, user.getUserId(), mostRecentLocationId, user.getUserStore().getStoreId());
        
        //update the user with the number of locations for which they are configured.  Note that they
        //may actually be configured for more than this number, since we retrieve at most 500 locations.
        //However for what this information is used for it doesn't currently need to be exact.
        user.setConfiguredLocationCount(numberOfLocations);
        
    	//if the user is configured for a single location, automatically select that location.
    	if (numberOfLocations == 1) {
    		BusEntityData location = null;
    		try {
            	User userEjb = APIAccess.getAPIAccess().getUserAPI();
            	if (userEjb.isSiteOfUser(((SiteData)locationSearchDto.getMatchingLocations().get(0)).getSiteId(), user.getUserId())) {
            		SiteData siteData = APIAccess.getAPIAccess().getSiteAPI().getSite(((SiteData)locationSearchDto.getMatchingLocations().get(0)).getSiteId());
            		if (siteData != null) {
            			location = siteData.getBusEntity();
            		}
            	}
            }
            catch (Exception exc) {
            	log.error("Unable to retrieve single configured location for the logged in user.");
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.noUserSiteData", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
            if (location != null) {
	    		errors = DashboardLogic.setUserLocation(request, user, location);
            }
            else {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.noUserSiteData", null);
                errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
    	}
    	
        return errors;
    }

}
