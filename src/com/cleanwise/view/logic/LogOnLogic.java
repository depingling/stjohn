/**
 *  Title: LogOnLogic Description: This is the business logic class for the
 *  LogOnAction and LogOnForm. Purpose: Copyright: Copyright (c) 2001 Company:
 *  CleanWise, Inc.
 *
 *@author     Liang Li
 */

package com.cleanwise.view.logic;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.ContractInformation;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.MainDb;
import com.cleanwise.service.api.session.Note;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.Ui;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.util.PhysicalInventoryPeriod;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.RefCodeNames.BUS_ENTITY_TYPE_CD;
import com.cleanwise.service.api.util.RefCodeNames.CONTRACT_STATUS_CD;
import com.cleanwise.service.api.util.RefCodeNames.PROPERTY_TYPE_CD;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.AllStoreData;
import com.cleanwise.service.api.value.AllStoreDataVector;
import com.cleanwise.service.api.value.AllUserData;
import com.cleanwise.service.api.value.ApplicationDomainNameData;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.DistributorDataVector;
import com.cleanwise.service.api.value.GroupData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.api.value.LoginInfoView;
import com.cleanwise.service.api.value.NoteJoinViewVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.ShoppingCartData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.service.api.value.SiteDeliveryDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UIConfigData;
import com.cleanwise.service.api.value.UiView;
import com.cleanwise.service.api.value.UserAccountRightsViewVector;
import com.cleanwise.service.api.value.UserAcessTokenViewData;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.view.forms.LogOnForm;
import com.cleanwise.view.forms.SelectShippingAddressForm;
import com.cleanwise.view.forms.ShoppingCartForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Admin2UiUtillity;
import com.cleanwise.view.utils.CallerParametersStJohn;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ContactUsInfo;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ShopTool;
import com.cleanwise.view.utils.UpdaterAfterStoreChange;

/**
 *  Class description.
 *
 *@author     dvieira
 *@created    November 7, 2001
 */
public class LogOnLogic {

    private static final Logger log = Logger.getLogger(LogOnLogic.class);

    private static final int MAX_SITES = 3;
    private static final String SELECT_SHIPPING_ADDRESS = "SelectShippingAddress";

    /**
     *  Description of the Method
     *
     *@param  session  Description of Parameter
     */
    public static void initUserAttributes(HttpSession session) {
        //Set configuration
        String storeDir = "";

        storeDir = ClwCustomizer.getStoreDir();
        log.info("LogOnLogic initUserAttributes call ClwCustomizer.getStoreDir="+storeDir); 
        session.setAttribute("store.dir", storeDir);

        String root = (String) session.getAttribute
        ("pages.root");
        if (null == root) {
            root = "/"+storeDir;
            session.setAttribute("pages.root", root);
        }

        String imgpath = "store/";
        session.setAttribute(Constants.IMAGES_PATH, imgpath);

        String lpath = "/store";
        session.setAttribute("pages.store.templates", lpath);

        lpath = root + "/store/images";
        session.setAttribute("pages.store.images", lpath);

    }

    public static ActionErrors mainLogOn
    (HttpServletResponse response,HttpServletRequest request,
    ActionForm form) {

  if ( request.getParameter(Constants.OCI_VERSION) != null ) {
      return processOCILogOn(response, request, form);
  }
  else {
      return processLogOn(response, request, form);
  }
    }

    public static ActionErrors processOCILogOn
    (HttpServletResponse response, HttpServletRequest request, ActionForm form) {


        String userName = request.getParameter("USERNAME"),
      password = request.getParameter("PASSWORD"),
      hookUrl = request.getParameter("HOOK_URL"),
      formTarget = request.getParameter("~TARGET"),
      buyerUsername = request.getParameter("BUYER_USERNAME");
        //some users (swiss post) use a different param for this.
        if(formTarget == null){
        	formTarget = request.getParameter("RETURNTARGET");
        }


        log.info(" OCI1, userName=" + userName +
         " password=XXXXX" + 
         " hookUrl=" + hookUrl +
         " formTarget=" + formTarget +
         " buyerUsername=" + buyerUsername
         );
  String lang = request.getParameter(Constants.REQUESTED_LANG_CD);
  if(lang != null && userName != null){
	  userName = userName.replace("{LANGUAGE}", lang);
  }

  request.getSession().setAttribute(Constants.OCI_TARGET, formTarget);
  request.getSession().setAttribute(Constants.OCI_HOOK_URL, hookUrl);
  request.getSession().setAttribute(Constants.OCI_BUYER_USERNAME,
            buyerUsername);

  ActionErrors ae = processLogOn(response, request, userName, password);


  log.info(
         " OCI2," +
         " hookUrl=" + hookUrl +
         " buyerUsername=" + buyerUsername
         );

  Iterator it = ae.get();
  int i = 0;
  while ( it.hasNext() ) {
      log.info("  i=" + i++ + (ActionMessage)it.next());
  }

  return ae;

    }


    /**
     *  Function description.
     *
     *@param  request  The HTTP request we are processing.
     *@param  form     Description of Parameter
     *@return          Description of the Returned Value
     */
    public static ActionErrors processLogOn
    (HttpServletResponse response,HttpServletRequest request,
    ActionForm form) {

        LogOnForm theForm = (LogOnForm) form;
        String userName = theForm.getJ_username();
        String password = theForm.getJ_password();
        String accessToken = request.getParameter(Constants.ACCESS_TOKEN);

        LdapItemData lUserInfo = new LdapItemData();
        lUserInfo.setUserName(userName);
        lUserInfo.setPassword(password);
        lUserInfo.setAccessToken(accessToken);
        lUserInfo.setPasswordHashed(theForm.isPasswordHashed());
  return logOnUser(response,request, lUserInfo);
    }

    public static ActionErrors processLogOn
  (HttpServletResponse response,HttpServletRequest request,String userName, String password) {

        LdapItemData lUserInfo = new LdapItemData();
        lUserInfo.setUserName(userName);
        lUserInfo.setPassword(password);
        lUserInfo.setAccessToken(null);
  return logOnUser(response, request, lUserInfo);
    }

    public static ActionErrors logOnUser(HttpServletResponse response,
                                         HttpServletRequest request,
                                         LdapItemData lUserInfo) {
    	return logOnUser(response, request, lUserInfo, null);
    }

    public static ActionErrors logOnUser(HttpServletResponse response,
                                         HttpServletRequest request,
                                         LdapItemData lUserInfo, Integer pSiteId) {

        log.info("logOnUser =>  Begin");

        Date curDate = new Date();
        long startTime = System.currentTimeMillis();   
		PropertyData accessTokenProperty = null;

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;

        try {
            factory = APIAccess.getAPIAccess();
            session.setAttribute(Constants.APIACCESS, factory);
        } catch (Exception exc) {
            exc.printStackTrace();
            session.setAttribute(Constants.APIACCESS, factory);
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }
        PropertyService propEjb = null;
        try {
        	propEjb = factory.getPropertyServiceAPI();
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No Property Ejb access"));
        }

        //this variable should be set to true if the user is logging in from any kind of external 
        //system.  It drives the checkbox at the store level indicating that users may only log in 
        //from an external system.  
        //Note that if the access token has an originalUserId value then that indicates this is an 
        //admin user logging in as another user, and this is NOT considered an external system login
        boolean loggingInFromExternalSystem = false;
        String accessToken = lUserInfo.getAccessToken();
        if (Utility.isSet(accessToken)) {
        	boolean originalUserIdSpecified = false;
        	try {
        		accessTokenProperty = propEjb.getAccessToken(accessToken);
        		originalUserIdSpecified = accessTokenProperty.getOriginalUserId() > 0;
        	}
        	catch (Exception e) {
            	e.printStackTrace();
            	ae.add("error", new ActionError("error.systemError"));
            	return ae;
        	}
        	if (!originalUserIdSpecified) {
        		loggingInFromExternalSystem = true;
        		log.info("loggingInFromExternalSystem is true!!!!");
        	}
        }

        LoginInfoView loginUserInfo = new LoginInfoView();
        loginUserInfo.setUserName(lUserInfo.getUserName());
        loginUserInfo.setPassword(lUserInfo.getPassword());
        loginUserInfo.setAccessToken(lUserInfo.getAccessToken());
    
        ae = CallerParametersStJohn.initCallerParameters(request, loginUserInfo, RefCodeNames.APP_SOURCE_CD.WEB);
        if (ae.size() > 0) {
        	return ae;            
        }
        
        User userEjb = null;
        try {
            userEjb = factory.getUserAPI();
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No User Ejb access"));
        }

        CatalogInformation catInfo = null;
        try {
            catInfo = factory.getCatalogInformationAPI();
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No Catalog Information Ejb access"));
        }

        ContractInformation contractInfo = null;
        try {
            contractInfo = factory.getContractInformationAPI();
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No Contract Information Ejb access"));
        }
        
        Site siteBean = null;
        try {
            siteBean = factory.getSiteAPI();
        } catch (Exception exc) {
            exc.printStackTrace();
            ae.add("error", new ActionError("error.systemError", "No Site Ejb access"));
        }
        
        if (ae.size() > 0) {
            return ae;
        }

        UserData user;
        try {
            user = userEjb.login(lUserInfo, "siteid=0");
            if (!Utility.isSet(user.getPassword())) {
            	String errorMess = ClwI18nUtil.getMessage(request, "login.errors.userLogonError", null);
            	ae.add("error", new ActionError("error.systemError", errorMess));
            	return ae;
            }
        }
        catch (InvalidLoginException exc) {
            processInvalidLoginException(request, ae);
            return ae;
        }
        catch (Exception exc) {
            exc.printStackTrace();
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.userLogonError", null);
            ae.add("error", new ActionError("error.systemError", errorMess));
            return ae;
        }
        
        String changeLocation = request.getParameter(Constants.CHANGE_LOCATION);
        if (changeLocation == null) {
        	changeLocation = request.getParameter("amp;"+Constants.CHANGE_LOCATION);
        }
        
        String defaultSite = request.getParameter(Constants.DEFAULT_SITE);
        if (defaultSite == null) {
        	defaultSite = request.getParameter("amp;"+Constants.DEFAULT_SITE);
        }
        
        String cwLogoutEnabled = request.getParameter(Constants.CW_LOGOUT_ENABLED);
        if (cwLogoutEnabled == null) {
        	cwLogoutEnabled = request.getParameter("amp;"+Constants.CW_LOGOUT_ENABLED);
        }

        String custSysId = request.getParameter(Constants.CUSTOMER_SYSTEM_ID);
        if (custSysId == null) {
        	custSysId = request.getParameter("amp;"+Constants.CUSTOMER_SYSTEM_ID);
        }

        String custSysUrl = request.getParameter(Constants.CUSTOMER_SYSTEM_URL);
        if (custSysUrl == null) {
        	custSysUrl = request.getParameter("amp;"+Constants.CUSTOMER_SYSTEM_URL);
        }
        if (Utility.isSet(custSysUrl)) {
            try {
                custSysUrl = java.net.URLDecoder.decode(custSysUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.info("Caught malformed url exception trying to parse " + Constants.CUSTOMER_SYSTEM_URL + " paramater");
            }
        }
        
        String uniqueName = request.getParameter(Constants.UNIQUE_NAME);
        if (uniqueName == null) {
        	uniqueName = request.getParameter("amp;"+Constants.UNIQUE_NAME);
        }

        Boolean cwLogoutEnabledB = Boolean.TRUE;
        if ((Utility.isSet(cwLogoutEnabled) && !Utility.isTrue(cwLogoutEnabled))) {
            cwLogoutEnabledB = Boolean.FALSE;
        }

        session.setAttribute(Constants.CUSTOMER_SYSTEM_ID, custSysId);
        session.setAttribute(Constants.CUSTOMER_SYSTEM_URL, custSysUrl);
        session.setAttribute(Constants.CW_LOGOUT_ENABLED, cwLogoutEnabledB);
        if (changeLocation != null) {
        	session.setAttribute(Constants.CHANGE_LOCATION, changeLocation);
        }
        if (uniqueName != null) {
        	session.setAttribute(Constants.UNIQUE_NAME, uniqueName);
        }
        
        try {
        	String val = propEjb.getUserProperty(user.getUserId(), RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_SITE);
        	if (val != null) {
        		log.info("Existing default location = " + val);
        		if (defaultSite != null) {
        			if (val.equalsIgnoreCase(defaultSite)){
        				log.info("Specified default location matches existing default location of "+ defaultSite);
        			}
        			else {
        				log.info("Changing default location from " + val + " to " + defaultSite);
        				/*userEjb.updateUserProperty(user.getUserId(), Integer.parseInt(defaultSite), 
        						RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_SITE);*/
        			}
        		}
        		else{
        			log.info("No default location was specified so using existing default of " + val);
        			defaultSite = val;
        		}
        	}
        }
        catch(RemoteException exc){
        	exc.printStackTrace();
        	ae.add("error", new ActionError("error.systemError"));
        	return ae;
        }
        catch(DataNotFoundException exc){
        	//exc.printStackTrace();
        	log.info("User has no default location specified.");
        }
        	
        if (defaultSite != null) {
        	log.info("logOnUser defaultSite != null : defaultSite = "+defaultSite);
        	session.setAttribute(Constants.DEFAULT_SITE, defaultSite);
        }

        SiteData defsite = null;
        CleanwiseUser appUser = new CleanwiseUser();
        
        // Multi Store Db code: Begin
        if ("yes".equals(System.getProperty("multi.store.db"))) {
           MainDb mainDbEjb = null;
           try {
        	   mainDbEjb = factory.getMainDbAPI();
           }
           catch (Exception exc) {
               exc.printStackTrace();
               ae.add("error", new ActionError("error.systemError", "No MainDb Ejb access"));
           }
           if (ae.size() > 0) {
               return ae;
           }

           //populate AllStoreDataVector in order to show a list of user's stores on the Main page of the Admin Portal
           String pUserName = lUserInfo.getUserName();
           String pPassword = lUserInfo.getPassword();
           log.info("logOnUser(): pUserName = " + pUserName + " pPassword = " + pPassword);
           AllStoreDataVector allStoreDataVector = null;
           try {
               allStoreDataVector = mainDbEjb.getAllStoreListByUserName(pUserName);
           }
           catch (Exception exc) {
               log.info("initMultiDbAccess(): Error Reading Main DB");
           }
           appUser.setMultiDbStores(allStoreDataVector);
        }
        // Multi Store Db code: End
        if (!RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(user.getUserTypeCd()) &&
        		!RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(user.getUserTypeCd())) {
            BusEntityDataVector siteCollection = null;
            try {
                if (RefCodeNames.USER_TYPE_CD.MSB.equals(user.getUserTypeCd()) ||
                    RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(user.getUserTypeCd())) {
                   siteCollection = userEjb.getSiteCollection(user.getUserId(),null,MAX_SITES, true);
                }
                if (siteCollection == null || siteCollection.size() == 0) {
                  siteCollection = userEjb.getSiteCollection(user.getUserId(),RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, MAX_SITES, false);
                }
            } 
            catch (RemoteException exc) {
                exc.printStackTrace();
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.gettingUserSites", null);
                ae.add("error", new ActionError("error.systemError", errorMess));
                incrementLoginFailure(session);
                return ae;
            }

            appUser.setSiteNumber(siteCollection.size());
            
            BusEntityData defaultSiteD = null;
            //if a site id was passed in, use it
            if (pSiteId != null) {
            	defaultSite = pSiteId.toString();
            }

            if (defaultSite != null) {
            	try {
            		if (userEjb.isSiteOfUser(Integer.parseInt(defaultSite), user.getUserId())) {
            			SiteData site = siteBean.getSite(Integer.parseInt(defaultSite));
            			if (site != null) {
            				defaultSiteD = site.getBusEntity();
            			}
            			if (defaultSiteD != null) {
            				siteCollection = new BusEntityDataVector();       	
            				siteCollection.add(defaultSiteD);	            	
            			}
            		}
            		else{
            			log.info("Not a valid location: " + defaultSite + " for user: " + user.getUserId());
            		}
            	}
            	catch(RemoteException exc){
                	exc.printStackTrace();
                	ae.add("error", new ActionError("error.systemError"));
                	return ae;
                }
            	catch(DataNotFoundException exc){
                	exc.printStackTrace();
                	log.info("No data found for location: " + defaultSite);
                }
            }
            
            defsite = getValidSite(request, userEjb, catInfo, contractInfo, siteCollection, user, curDate, ae);
            if (ae.size() > 0) {
                return ae;
            }
        }

        checkUser(request, user, curDate, ae);
        if (ae.size() > 0) {
            return ae;
        }

        resetLoginInfo(session);
        fetchClwUser(request, appUser, user, loggingInFromExternalSystem, defsite, ae);
        if (ae.size() > 0) {
            return ae;
        }

		//if the user logged into the application via an access token, and there is an "original user id" value
		//associated with that access token, retrieve and validate information about that user as well.  This is 
	    //currently only used for the Neptune "logged in as" functionality.
		if (accessTokenProperty != null) {
			int originalUserId = accessTokenProperty.getOriginalUserId();
	    	if (originalUserId > 0) {
    			CleanwiseUser originalUser = new CleanwiseUser();
	    		UserData originalUserData = null;
	    		try {
	    			originalUserData = userEjb.getUser(originalUserId);
	    		}
	    		catch (Exception e) {
                	e.printStackTrace();
                	ae.add("error", new ActionError("error.systemError"));	
                	return ae;
	    		}
	    		if (originalUserData != null) {
	    			originalUser.setUserName(originalUserData.getUserName());
	    			originalUser.setUser(originalUserData);
		    	    //verify the original user can simulate logging in as another user
		    		String originalUserRole = Utility.safeTrim(originalUserData.getUserTypeCd()).toUpperCase();
		    		boolean originalUserIsAdministrator = (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(originalUserRole) ||
		    			RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(originalUserRole) ||
		    			RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(originalUserRole) ||
		    			RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(originalUserRole));
		    	    if (!originalUserIsAdministrator) {
	    	            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginNotAuthorized");
	    	        	ae.add("error", new ActionError("error.simpleGenericError", errorMess));    		
		    	    }
		    	    //verify the logged in as user is a multi-site buyer
		    	    if (appUser != null && !RefCodeNames.USER_TYPE_CD.MSB.equals(appUser.getUser().getUserTypeCd())) {
	    	            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUserNotAMSB");
	    	        	ae.add("error", new ActionError("error.simpleGenericError", errorMess));    		
		    	    }
		    	    //verify store information for the original user and logged in as user
		    	    int originalUserStoreId = accessTokenProperty.getBusEntityId();
		    	    if (originalUserStoreId > 0) {
		    	        //verify that a valid, active store was specified for the original user
		    	    	BusEntityDataVector originalUserStores = null;
		    	        try {
		    	        	originalUserStores = userEjb.getBusEntityCollection(originalUserData.getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
			    	    	Iterator<BusEntityData> originalUserStoreIterator = originalUserStores.iterator();
		    	    		while (originalUserStoreIterator.hasNext() && originalUser.getUserStore() == null) {
		    	    			BusEntityData originalUserStore = originalUserStoreIterator.next();
		    	    			if (originalUserStore.getBusEntityId() == originalUserStoreId) {
			    	    			if (RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE.equals(originalUserStore.getBusEntityStatusCd())) {
			    	    	            StoreData sd = SessionTool.getStoreData(request, originalUserStoreId);
			    	    				originalUser.setUserStore(sd);
			    	    			}
		    	    			}
		    	    		}
		    	        }
		    	        catch (Exception e) {
		                	e.printStackTrace();
		                	ae.add("error", new ActionError("error.systemError"));	
		                	return ae;
		    	        }
		    	    	if (originalUser.getUserStore() == null) {
			    	        String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginOriginalUserInvalidStoreSpecified");
			    	        ae.add("error", new ActionError("error.simpleGenericError", errorMess));    		
		    	    	}
		    	    	else {
		    	    		//verify the original user and logged in user belong to the same store
				    	    boolean matchingStoreFound = false;
		    	        	List<BusEntityData> currentUserStores = null;
		    	        	try {
		    	        		currentUserStores = userEjb.getBusEntityCollection(appUser.getUserId(), RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
		    	        	}
			    	        catch (Exception e) {
			                	e.printStackTrace();
			                	ae.add("error", new ActionError("error.systemError"));	
			                	return ae;
			    	        }
				    	    if (currentUserStores != null) {
				    		    Iterator<BusEntityData> currentUserStoreIterator = currentUserStores.iterator();
				    		    while (currentUserStoreIterator.hasNext() && !matchingStoreFound) {
				    		    	matchingStoreFound = (originalUserStoreId == currentUserStoreIterator.next().getBusEntityId());
				    		    }
				    	    }
				    	    if (!matchingStoreFound) {
				    	        String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginUserNotFound");
				    	        ae.add("error", new ActionError("error.simpleGenericError", errorMess));    		
				    	    }			    	        	
		    	    	}			    	        	
		    	    }
		    	    else {
		    	        String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginOriginalUserNoStoreSpecified");
		    	        ae.add("error", new ActionError("error.simpleGenericError", errorMess));    		
		    	    }
	    		}
	    		else {
    	            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.proxyLoginOriginalUserNotFound");
    	        	ae.add("error", new ActionError("error.simpleGenericError", errorMess));    		
	    		}
	    		//if there were any errors retrieving/validating the original user return them
		        if (ae.size() > 0) {
		            return ae;
		        }
		        //otherwise update the user with the information about the original user
		        else {
		    		appUser.setOriginalUser(originalUser);
		        }
	    	}
		}

        session.setAttribute(Constants.USER_NAME, user.getUserName());
        session.setAttribute(Constants.USER_ID, String.valueOf(user.getUserId()));
        session.setAttribute(Constants.APP_USER, appUser);
        session.setAttribute(Constants.REQUEST_NUM, "0");

        // Now set the store and account specific session attributesfor the user.
        fetchCustomProperties(session, appUser);
        initUserAttributes(session);
        if (appUser.getSite() != null) {
            try {
                if (!logOnSiteShop(request, appUser)) {
                    String errorMess = ClwI18nUtil.getMessage(request, "login.errors.siteNotReadyForPurchases", null);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return ae;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            // set the billtos available for this location.
            try {
                appUser.setUserBillTos(userEjb.getBilltoCollection(appUser.getUser().getUserId()));
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }

            // define header attributes : cutoffDate and nextDeliveryDate for site
            ae = initNextDeliveryDateForSite(session, request);
        }

        if (loggingInFromExternalSystem) {
            SessionTool.setCookie(response, request, SessionTool.COOKIE_EXTERNAL_LOGON, Boolean.TRUE.toString(), null);
        }
        
        //STJ-5358
        String storeOrAccountPrefix = "";
        if (appUser.getUserStore() != null && appUser.getUserStore().getPrefix() != null) {
        	storeOrAccountPrefix = appUser.getUserStore().getPrefix().getValue();
        }

        //if store prefix is not set look for account prefix.
        if (!Utility.isSet(storeOrAccountPrefix)) {
        	AccountData accountD = appUser.getUserAccount();
        	if (accountD != null) {
        		storeOrAccountPrefix = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
        	}
        }

        String lang = "", country = "";
        //STJ-5693
        // Get the user's language and country 
        String prefLocaleCd = appUser.getUser().getPrefLocaleCd();
        StringTokenizer st = new StringTokenizer(prefLocaleCd, "_");
        for (int i = 0; st.hasMoreTokens() && i < 2; i++) {
        	if (i == 0) {
        		lang = st.nextToken();
        	}
        	else if (i == 1) {
        		country = st.nextToken();
        	}
        }
        // If there is a store prefix, remove any "/" and construct locale with storePrefix as variant 
        // Otherwise, construct user's Locale. 
        Locale locale;
        if (Utility.isSet(storeOrAccountPrefix)) {
        	storeOrAccountPrefix = storeOrAccountPrefix.replaceAll(Constants.FORWARD_SLASH,StringUtils.EMPTY);
	        locale = new Locale(lang, country, storeOrAccountPrefix);
	        appUser.setStorePrefixLocale(locale);
        } 
        else {
        	locale = new Locale(lang,country);
        	appUser.setStorePrefixLocale(locale);
        }
        
        session.setAttribute(Constants.APP_USER, appUser);
        log.info("logOnUser => logon success at:" + (System.currentTimeMillis() - startTime));
        
        return ae;
    }

    private static void checkUser(HttpServletRequest request, UserData user, Date curDate, ActionErrors ae) {

        HttpSession session = request.getSession();

        Date pExpDate = user.getExpDate();
        Date pEffDate = user.getEffDate();

        //exp date can be null - don't compare dates then - but ...
        if (pExpDate != null && !curDate.before(pExpDate)) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.accountExpired", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            incrementLoginFailure(session);
            return;
        }

        // ... eff date can't be null
        if (pEffDate == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.accountNoEffDate", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            incrementLoginFailure(session);
            return;
        }

        if (!curDate.after(pEffDate)) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.accountNotActiveYet", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            incrementLoginFailure(session);
            return;
        }
        if (null == user.getUserTypeCd()) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.userTypeUnknown", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return;
        }

        //Check user status
        if ((user.getUserStatusCd() == null) || (!user.getUserStatusCd().equals(RefCodeNames.USER_STATUS_CD.ACTIVE))) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.userNotActive", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return;
        }

        String roleCd = user.getUserRoleCd();
        if (roleCd == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.userNoRole", null);
            ae.add("error", new ActionError("error.systemError", errorMess));
        }
    }

    private static void processInvalidLoginException(HttpServletRequest request, ActionErrors errors) {

        HttpSession session = request.getSession();

        if (request.getParameter(Constants.PAGE_VISIT_TIME) != null) {
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.wrongNamePassword", null);
            errors.add("error", new ActionError("error.simpleGenericError", errorMess));
            incrementLoginFailure(session);
        } else {
            //first time visitor
            Cookie[] cc = request.getCookies();
            if (cc != null) {
                for (Cookie c : cc) {
                    if (SessionTool.COOKIE_EXTERNAL_LOGON.equalsIgnoreCase(c.getName())) {
                        if (Utility.isTrue(c.getValue())) {
                            //redirect to informational page
                            session.setAttribute(Constants.EXTERNAL_LOGON, Boolean.TRUE.toString());
                        }
                    }
                }
            }
        }
    }

    public static SiteData getValidSite(HttpServletRequest request,
                                         User userEjb,
                                         CatalogInformation catInfo,
                                         ContractInformation contractInfo,
                                         List/*<BusEntityData>*/ siteCollection,
                                         UserData pUser,
                                         Date curDate,
                                         ActionErrors ae) {

        SiteData validSite = null;
        HttpSession session = request.getSession();

        boolean allSitesInvalid = true;

        CleanwiseUser appUser = new CleanwiseUser();
        appUser.setSiteNumber(siteCollection.size());

        for (Object aSiteCollection : siteCollection) {
            // get site data
            BusEntityData siteBed = (BusEntityData) aSiteCollection;
            log.info("logOnUser => site id: " + siteBed.getBusEntityId());
            String siteStatus = siteBed.getBusEntityStatusCd();

            Date effDate = siteBed.getEffDate();
            Date expDate = siteBed.getExpDate();

            if (siteStatus.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE) &&
                    (effDate == null || !effDate.after(curDate)) &&
                    (expDate == null || !expDate.before(curDate))) {


                allSitesInvalid = false;
                int siteId = siteBed.getBusEntityId();
                SiteData siteData;
                try {
                    log.info("logOnUser => get site data.Site Id  " + siteId);
                    siteData = SessionTool.getSiteDataNoCache(request, siteId);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    String errorMess = ClwI18nUtil.getMessage(request, "login.errors.findLocationForId", new Integer[]{Integer.valueOf(siteId)});
                    ae.add("error", new ActionError("error.systemError", errorMess));
                    incrementLoginFailure(session);
                    return validSite;
                }

                BusEntityData acctBed = siteData.getAccountBusEntity();
                if (acctBed == null) {
                    continue;
                }

                String accountStatus = acctBed.getBusEntityStatusCd();
                if (accountStatus.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE)) {
                    continue;
                }

                int accountId = acctBed.getBusEntityId();
                log.info("logOnUser => accountId: " + accountId);
                AccountData accountData;
                try {
                    log.info("logOnUser => get account data.Account Id  " + accountId);
                    accountData = SessionTool.getAccountDataNoCache(request, accountId);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    String errorMess = ClwI18nUtil.getMessage(request, "login.errors.findAccountForId", new Integer[]{Integer.valueOf(accountId)});
                    ae.add("error", new ActionError("error.systemError", errorMess));
                    incrementLoginFailure(session);
                    return validSite;
                }

                BusEntityAssocData bead = accountData.getStoreAssoc();
                int storeId = bead.getBusEntity2Id();
                //StoreBean stb = new StoreBean();
                StoreData storeData;
                try {
                    log.info("logOnUser => get store data.Store Id  " + storeId);
                    storeData = SessionTool.getStoreDataNoCache(request, storeId);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    String errorMess = ClwI18nUtil.getMessage(request, "login.errors.findStoreForId", new Integer[]{Integer.valueOf(storeId)});
                    ae.add("error", new ActionError("error.systemError", errorMess));
                    incrementLoginFailure(session);
                    return validSite;
                }

                BusEntityData bed = storeData.getBusEntity();
                String storeStatus = bed.getBusEntityStatusCd();
                if (storeStatus.equals(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE)) {
                    continue;
                }

                Object catConD = SelectShippingAddressLogic.getCatalogAndContractForSite(request,
                                                                                         ae,
                                                                                         catInfo,
                                                                                         contractInfo,
                                                                                         siteData,
                                                                                         pUser.getUserRoleCd());
                if (catConD != null) {
                    log.info("logOnUser => Setting default site as it is VALID");
                    validSite = siteData;
                    break;
                } else {
                    log.info("logOnUser => Setting default site as it is NULL");
                    validSite = null;
                    allSitesInvalid = true;
                  }
            }
        }

        if (!siteCollection.isEmpty() && allSitesInvalid) {
            if (ae.isEmpty()) {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.noValidSite", null);
                ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            }
            incrementLoginFailure(session);
            return validSite;
        }

        return validSite;
    }

    private static void fetchClwUser(HttpServletRequest request,
                                     CleanwiseUser pAppUser,
                                     UserData pUser,
                                     boolean pLoggingInFromExternalSystem,
                                     SiteData pDefSite,
                                     ActionErrors ae) {
        try {


            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

            // Set the user name and id in the session for other session
            // based user access.
            pAppUser.setUserName(pUser.getUserName());
            pAppUser.setUser(pUser);

            addLocaleToClwUser(request, pAppUser);

            String userType = pUser.getUserTypeCd();

            if (userType.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR)) {
                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR);
                fetchUserStore(request, pUser.getUserId(), pAppUser);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR)) {
                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.ADMINISTRATOR);
                fetchUserStore(request, pUser.getUserId(), pAppUser);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR)) {
                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR);
                fetchUserStore(request, pUser.getUserId(), pAppUser);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR)) {
                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR);
                fetchUserAccount(request, pUser.getUserId(), pAppUser);
            } else if (pAppUser.isaCustomer()) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.CUSTOMER);

                if (pDefSite != null) {
                    // Set the starting site for this customer.
                    log.info("fetchClwUser => Setting Default Site id for customer to: " + pDefSite.getBusEntity().getBusEntityId());
                    fetchSiteAccount(pDefSite, pAppUser, request);
                    pAppUser.setSite(pDefSite);
                } else {
                    fetchUserAccount(request, pUser.getUserId(), pAppUser);
                }

                if (!pLoggingInFromExternalSystem && Utility.isTrue(Utility.getPropertyValue(pAppUser.getUserStore().getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON))) {
                    session.setAttribute(Constants.APP_USER, null);
                    ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.require.external.sys.logon"));
                    return;
                }

                pAppUser.setRights(getCustomerRights(pUser.getUserId()));

            } else if (pAppUser.isaMSB()) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.MSB);

                try {
	                if (pDefSite != null) {
	                    // Set the starting site for this MSB user.
	                    log.info("fetchClwUser => Setting Default Site id for MSB to: " + pDefSite.getBusEntity().getBusEntityId());
	                    AccountData account = SessionTool.getAccountData(request,pDefSite.getAccountBusEntity().getBusEntityId());
	                    StoreData store = SessionTool.getStoreData(request,account.getStoreAssoc().getBusEntity2Id());
	                    fetchSiteAccount(store,account,pAppUser, request);
	                    pAppUser.setSite(pDefSite);
	                } else {
	                    fetchUserAccount(request, pUser.getUserId(), pAppUser);
	                }
                }
                catch (Exception e) {
                    String errorMess = ClwI18nUtil.getMessage(request, "login.errors.noActiveAccount", null);
                    ae.add("error", new ActionError("error.simpleGenericError", errorMess));
                    return;
                }

                if (!pLoggingInFromExternalSystem && Utility.isTrue(Utility.getPropertyValue(pAppUser.getUserStore().getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON))) {
                    session.setAttribute(Constants.APP_USER, null);
                    ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.require.external.sys.logon"));
                    return;
                }

                pAppUser.setRights(getCustomerRights(pUser.getUserId()));

                Note noteEjb;
                try {
                    noteEjb = factory.getNoteAPI();
                    boolean hasUserMessages = noteEjb.hasUserNotes(pAppUser.getUserId(), pAppUser.getUserStore().getStoreId());
                    log.info("fetchClwUser => Setting has user messages or not to " + hasUserMessages);
                    pAppUser.setHasNotes(hasUserMessages);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    ae.add("error", new ActionError("error.systemError", "No Note Ejb access"));
                }

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.ESTORE_CLIENT)) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.ESTORE_CLIENT);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.DISTRIBUTOR)) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.DISTRIBUTOR);
                fetchUserStore(request, pUser.getUserId(), pAppUser);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE)) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE);
                fetchUserStore(request, pUser.getUserId(), pAppUser);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER)) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.SERVICE_PROVIDER);
                fetchUserStore(request, pUser.getUserId(), pAppUser);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.CRC_MANAGER);
                fetchUserStore(request, pUser.getUserId(), pAppUser);

            } else if (userType.equals(RefCodeNames.USER_TYPE_CD.REPORTING_USER)) {

                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.REPORTING_USER);
                fetchUserStore(request, pUser.getUserId(), pAppUser);
                if (pDefSite != null) {
                    log.info("fetchClwUser =>  Setting default  site for reporting user: " + pDefSite.getBusEntity().getBusEntityId());
                    fetchSiteAccount(pDefSite, pAppUser, request);
                    pAppUser.setSite(pDefSite);
                } else {
                    log.info("fetchClwUser => no default for user=" + pUser);
                }

            } else {
                session.setAttribute(Constants.USER_TYPE, RefCodeNames.USER_TYPE_CD.CUSTOMER);
                if (pDefSite != null) {
                    // Set the starting site for this customer.
                    log.info("fetchClwUser => Setting Default Site id for customer to: " + pDefSite.getBusEntity().getBusEntityId());
                    fetchSiteAccount(pDefSite, pAppUser, request);
                    pAppUser.setSite(pDefSite);
                } else {
                    fetchUserAccount(request, pUser.getUserId(), pAppUser);
                }

                if (!pLoggingInFromExternalSystem && Utility.isTrue(Utility.getPropertyValue(pAppUser.getUserStore().getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.REQUIRE_EXTERNAL_SYS_LOGON))) {
                    session.setAttribute(Constants.APP_USER, null);
                    ae.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.require.external.sys.logon"));
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMess = ClwI18nUtil.getMessage(request, "login.errors.userOrStoreOrAccount", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        }

    }

    private static void addLocaleToClwUser(HttpServletRequest request, CleanwiseUser appUser) {

        String country = request.getParameter(Constants.REQUESTED_COUNTRY_CD),
                lang = request.getParameter(Constants.REQUESTED_LANG_CD);

        if (country != null || lang != null) {

            String defLocaleStr = appUser.getUser().getPrefLocaleCd();
            Locale defLocale;

            if (defLocaleStr == null) {
                defLocale = Locale.US;
            } else {
                defLocale = Utility.parseLocaleCode(defLocaleStr);
            }

            if (!Utility.isSet(country)) {
                country = defLocale.getCountry();
            } else {
                country = country.toUpperCase();
            }

            if (!Utility.isSet(lang)) {
                lang = defLocale.getLanguage();
            } else {
                lang = lang.toLowerCase();
            }

            appUser.getUser().setPrefLocaleCd(lang + "_" + country);
            appUser.getPrefLocale();//cache locale now!
        }
    }

    public static boolean logOnSiteShop(HttpServletRequest request, CleanwiseUser pAppUser) throws Exception {

        HttpSession session = request.getSession();
        try {

            APIAccess factory = new APIAccess();

            CatalogInformation catInfo = factory.getCatalogInformationAPI();
            Order orderEjb = factory.getOrderAPI();
            ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

            SiteData site = pAppUser.getSite();
            StoreData store = pAppUser.getUserStore();

            SiteDataVector sdv = new SiteDataVector();
            sdv.add(site);
            SelectShippingAddressForm form = new SelectShippingAddressForm();
            form.setMultiAcctFlag(false);
            form.setSites(sdv);
            form.setAppUser(pAppUser);

            session.setAttribute(SELECT_SHIPPING_ADDRESS, form);

            pAppUser.getContactUsList().clear();
            //initialize the contact us
            initializeContactUs(pAppUser);

            if (store != null && Utility.isSet(store.getEvenRowColor())) {
                session.setAttribute(Constants.EVEN_ROW_COLOR, store.getEvenRowColor());
            }

            if (store != null && Utility.isSet(store.getOddRowColor())) {
                session.setAttribute(Constants.ODD_ROW_COLOR, store.getOddRowColor());
            }

            // Set the user into the shopping cart.
            resetTheCarts(request, pAppUser);

            // get the catalog for the site.
            CatalogData catalog = catInfo.getSiteCatalog(site.getBusEntity().getBusEntityId());
            if (null == catalog) {
                log.info("logOnSiteShop => Catalog Data was null");
                return false;
            }

            int catalogId = catalog.getCatalogId();
            session.setAttribute(Constants.CATALOG_ID, catalogId);
            session.setAttribute(Constants.CATALOG_NAME, catalog.getShortDesc());

            int contractId;
            ContractData contract = site.getContractData();
            if (contract != null) {
                contractId = site.getContractData().getContractId();
                session.setAttribute(Constants.CONTRACT_NAME, contract.getShortDesc());
                session.setAttribute(Constants.CATALOG_LOCALE, contract.getLocaleCd());
                log.info("Using Contract ID: "+contractId);
                log.info("Using catalog locale: "+contract.getLocaleCd());
                int decimalPlaces = -1;
                try{
                	decimalPlaces = factory.getCurrencyAPI().getDecimalPlacesForLocale(contract.getLocaleCd());
                	log.info("decimalPlaces="+decimalPlaces);
                }catch(Exception e){
                	log.error("Could not get decimal places for currency", e);
                }
                session.setAttribute(Constants.CATALOG_DECIMALS, decimalPlaces);
            } else {
                session.setAttribute(Constants.CONTRACT_NAME, "");
                contractId = 0;
            }

            session.setAttribute(Constants.CONTRACT_ID, contractId);
            log.info("logOnSiteShop => pSiteId=" + site.getSiteId() + " Site contract Id=" + contract.getContractId());


            orderEjb.initOrderNumber(store.getBusEntity().getBusEntityId(), pAppUser.getUser().getUserName());

            //Shopping cart

            PropertyData storeTypeProperty = store.getStoreType();
            if (storeTypeProperty == null) {
                throw new Exception("No store type information was loaded");
            }

            ///
            ArrayList<PhysicalInventoryPeriod> physicalInventoryPeriods = null;
            boolean isUsedPhysicalInventoryAlgorithm = ShopTool.isUsedPhysicalInventoryAlgorithm(request);
            boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
            boolean usePhysicalInventory = isUsedPhysicalInventoryAlgorithm && isPhysicalCartAvailable;

            ShoppingCartData shoppingCartD = null;
            ShoppingCartData inventoryShoppingCartD = null;
            if (ShopTool.canSaveShoppingCart(session)) {
                try {
                	String userName2 = (String) session.getAttribute(Constants.UNIQUE_NAME);
                    shoppingCartD = shoppingServEjb.getShoppingCart(storeTypeProperty.getValue(),
                            pAppUser.getUser(),
                            userName2,
                            pAppUser.getSite(),
                            site.getContractData().getCatalogId(),
                            site.getContractData().getContractId(),
                            SessionTool.getCategoryToCostCenterView(request.getSession(), pAppUser.getSite().getSiteId()));
                } catch (RemoteException exc) {
                    throw new Exception("Failed to get the shopping cart data");
                }

log.info("LogOnLogic  LLLLLLLLLLLLLLLLLLLLLLL usePhysicalInventory: "+usePhysicalInventory);
                if (usePhysicalInventory) {
                    physicalInventoryPeriods = pAppUser.getSite().getPhysicalInventoryPeriods();
                    try {
                      inventoryShoppingCartD = shoppingServEjb.getInvShoppingCart(storeTypeProperty.getValue(), pAppUser.getUser(), site, true, SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
                    } catch (RemoteException exc) {
                        throw new Exception("Failed to get the physical shopping cart data");
                    }
                } else {
                    if (ShopTool.isModernInventoryCartAvailable(session)) {
                        try {
                          inventoryShoppingCartD = shoppingServEjb.getInvShoppingCart(storeTypeProperty.getValue(), pAppUser.getUser(), site, true,SessionTool.getCategoryToCostCenterView(session, site.getSiteId()));
                        } catch (RemoteException exc) {
                            throw new Exception("Failed to get the inventory shopping cart data");
                        }
                    }
                }
            }
            session.setAttribute(Constants.SHOPPING_CART, shoppingCartD);
            session.setAttribute(Constants.INVENTORY_SHOPPING_CART, inventoryShoppingCartD);

            ShoppingCartForm scf = new ShoppingCartForm();
            scf.setShoppingCart(shoppingCartD);
            scf.setSiteInventory(pAppUser.getSite().getSiteInventory());
            scf.setUsePhysicalInventory(usePhysicalInventory);
            scf.setPhysicalInventoryPeriods(physicalInventoryPeriods);
            session.setAttribute(Constants.SHOPPING_CART_FORM, scf);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("logOnSiteShop => error: " + e);
        }
        log.info("logOnSiteShop => didn't return true somewhere");
        return false;
    }

    private static void initializeContactUs(CleanwiseUser pAppUser) throws Exception {

        APIAccess factory = new APIAccess();
        Site siteEjb = factory.getSiteAPI();

        StoreData store = pAppUser.getUserStore();
        SiteData site = pAppUser.getSite();
        AccountData account = pAppUser.getUserAccount();

        if (RefCodeNames.CONTACT_US_TYPE_CD.DISTRIBUTOR.equals(Utility.getPropertyValue(store.getMiscProperties(), RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TYPE_CD))) {

            DistributorDataVector distDV = siteEjb.getAllDistributorsForSite(site.getSiteId());

            for (Object aDistDV : distDV) {

                DistributorData dist = (DistributorData) aDistDV;
                ContactUsInfo cont = new ContactUsInfo();
                cont.setAddress(dist.getPrimaryAddress());

                if (Utility.isSet(dist.getRuntimeDisplayName())) {
                    cont.setNickName(dist.getRuntimeDisplayName());
                } else {
                    cont.setNickName(dist.getBusEntity().getShortDesc());
                }

                if (Utility.isSet(dist.getPrimaryAddress().getName1()) || Utility.isSet(dist.getPrimaryAddress().getName2())) {
                    if (!Utility.isSet(dist.getPrimaryAddress().getName1())) {
                        cont.setContactName(dist.getPrimaryAddress().getName2());
                    } else if (!Utility.isSet(dist.getPrimaryAddress().getName2())) {
                        cont.setContactName(dist.getPrimaryAddress().getName1());
                    } else {
                        cont.setContactName(dist.getPrimaryAddress().getName1() + " " + dist.getPrimaryAddress().getName2());
                    }
                } else {
                    cont.setContactName(pAppUser.getUIConfigData()
                            .getCustomerServiceAlias());
                }
                cont.setEmail(dist.getPrimaryEmail().getEmailAddress());
                cont.setFax(dist.getPrimaryFax().getPhoneNum());
                cont.setPhone(dist.getPrimaryPhone().getPhoneNum());
                cont.setCallHours(dist.getCallHours());

                pAppUser.addContactUs(cont);
            }
        } else {
            ContactUsInfo cont = new ContactUsInfo();
            if (Utility.isSet(store.getPrimaryAddress().getName1())
                    || Utility.isSet(store.getPrimaryAddress().getName2())) {
                String contactName = store.getPrimaryAddress().getName1();
                if (Utility.isSet(contactName)) {
                    contactName += " ";
                }
                if (Utility.isSet(store.getPrimaryAddress().getName2())) {
                    contactName += store.getPrimaryAddress().getName2();
                }
                cont.setContactName(contactName);
            } else {
                cont.setContactName(pAppUser.getUIConfigData()
                        .getCustomerServiceAlias());
            }
            cont.setAddress(store.getPrimaryAddress());
            cont.setNickName(store.getStoreBusinessName().getValue());
            if (Utility.isSet(account.getOrderPhone().getPhoneNum())) {
                cont.setPhone(account.getOrderPhone().getPhoneNum());
            } else {
                cont.setPhone(store.getPrimaryPhone().getPhoneNum());
            }
            if (Utility.isSet(account.getOrderFax().getPhoneNum())) {
                cont.setFax(account.getOrderFax().getPhoneNum());
            } else {
                cont.setFax(store.getPrimaryFax().getPhoneNum());
            }
            cont.setEmail(store.getContactUsEmail().getEmailAddress());
            cont.setCallHours(store.getCallHours().getValue());
            pAppUser.addContactUs(cont);
        }
    }

    private static void resetTheCarts(HttpServletRequest request, CleanwiseUser pCwUser) {

        ShoppingCartData sc = new ShoppingCartData();
        sc.setUser(pCwUser.getUser());
        sc.setSite(pCwUser.getSite());

        request.getSession().setAttribute(Constants.SHOPPING_CART, sc);
        request.getSession().setAttribute(Constants.INVENTORY_SHOPPING_CART, null);
    }



    /**
     *  Generate a new password for the user trying to log on.
     *
     *@param  request
     *@param  pUserName  , user trying to access the system
     *@param  pMsgRes    , message catalog used to compose the email to send.
     *@return            possible errors to report to the user.
     */
    public static ActionErrors generateNewPassword
    (
     HttpServletRequest request,
     MessageResources pMsgRes,
     String pUserName) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        APIAccess factory = null;
        try {
            factory = APIAccess.getAPIAccess();
            session.setAttribute(Constants.APIACCESS, factory);
        }
        catch (Exception exc) {
            exc.printStackTrace();
            String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.systemError1",null);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        User userEjb = null;
        try {
            userEjb = factory.getUserAPI();
        }
        catch (Exception exc) {
            exc.printStackTrace();
            String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.systemError2",null);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        UserDataVector udv = null;
        try {
            udv = userEjb.getUsersCollectionByName
            (pUserName, User.NAME_EXACT, 0);

            if (udv == null || udv.size() == 0) {
            String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.unknownUserName",null);
                ae.add("error", new ActionError("error.simpleGenericError",errorMess));
                return ae;
            }

            if (udv.size() > 1) {
                // User names must be unique.  This is an error.
                String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.userNameError",null);
                ae.add("error", new ActionError("error.simpleGenericError",errorMess));
                return ae;
            }

            // Now generate a new password for the user found.
            UserData ud = (UserData) udv.get(0);

            userEjb.sendPasswordEmail
            (true,
            ud.getUserId(),
            ClwI18nUtil.getMessage(request,"password.email.subject",null, true),
            ClwI18nUtil.getMessage(request,"password.email.start",null, true),""
            //ClwI18nUtil.getMessage(request,"password.email.end",null, true)
            );
        }
        catch (DataNotFoundException exc) {
            String errorMess =
              ClwI18nUtil.getMessage(request,"login.errors.userNameNotFound",null);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }
        catch (Exception exc) {
            String errorMess =
              ClwI18nUtil.getMessage(request,"login.errors.emailError",null);
            ae.add("error", new ActionError("error.simpleGenericError",errorMess));
            return ae;
        }

        return ae;
    }
    private static UserAccountRightsViewVector getCustomerRights(int pUserId)
    throws Exception {
        UserAccountRightsViewVector rights = new UserAccountRightsViewVector();

        APIAccess factory = APIAccess.getAPIAccess();
        User uBean = factory.getUserAPI();
        rights = uBean.getUserAccountRights(pUserId);
        return rights;

    }

    /**
     *  Description of the Method
     *
     *@param  pSession  Description of Parameter
     */
    private static void incrementLoginFailure(HttpSession pSession) {
        String cCount = (String) pSession.getAttribute
        (Constants.USER_LOGIN_FAIL_COUNT);
        int iCount = 1;
        if (cCount != null) {
            iCount = Integer.parseInt(cCount);
            iCount++;
        }
        pSession.setAttribute(Constants.USER_LOGIN_FAIL_COUNT,
        String.valueOf(iCount));
    }


    /**
     *  Description of the Method
     *
     *@param  pSession  Description of Parameter
     */
    private static void resetLoginInfo(HttpSession pSession) {
        pSession.setAttribute(Constants.USER_LOGIN_DATE,new Date());
        pSession.setAttribute(Constants.USER_LOGIN_FAIL_COUNT, "0");
    }


    /**
     *  Description of the Method
     *
     *@param  pSession      Description of Parameter
     *@param  pCurrentUser  Description of Parameter
     */
    public static void fetchCustomProperties (HttpSession pSession, CleanwiseUser pCurrentUser)
    {
    	log.info("fetchCustomProperties pCurrentUserId = "+String.valueOf(pCurrentUser.getUserId()));
        if ( pCurrentUser.getUser().getUserId() <= 0 ) {
            // No user specified.  Set the defaults.
            setCustomerStoreProperties(pSession, pCurrentUser);
            return;
        }

        try{
            APIAccess factory = APIAccess.getAPIAccess();
            Group groupEJB = factory.getGroupAPI();
            Note noteBean = factory.getNoteAPI();

            try{
            	log.info("fetchCustomProperties(): pCurrentUser.getUser().getUserId() = " + pCurrentUser.getUser().getUserId());
            	pCurrentUser.setAuthorizedFor(groupEJB.getAllValidUserGroupAssociations(pCurrentUser.getUser().getUserId()));
            	PropertyService propEJB = factory.getPropertyServiceAPI();
                pCurrentUser.setUserProperties(propEJB.getUserPropertyCollection(pCurrentUser.getUser().getUserId()));
            }catch(Exception e){
                log.info("No User Properties Found");
            }
            //don't retrieve notes if the user is using the new UI
            String userType = pCurrentUser.getUser().getUserTypeCd();
            boolean isNewUiUser = RefCodeNames.USER_TYPE_CD.MSB.equals(userType) &&
            		 (Constants.PORTAL_ESW.equals(Utility.getAlternatePortal(pCurrentUser.getUserStore())) ||
            	      Constants.PORTAL_ESW.equals(Utility.getAlternatePortal(pCurrentUser.getUserAccount())) ) ;
        	if (!isNewUiUser) {
        		int storeId = pCurrentUser.getUserStore().getStoreId();
        		NoteJoinViewVector noteJoinViewVector = noteBean.getActualNotesForUser(pCurrentUser.getUser().getUserId());
        		pSession.setAttribute("user.notes", noteJoinViewVector);
        		pSession.setAttribute("user.notesAll",
                    noteBean.getNotesForUserExcludeDuplicateTitles(pCurrentUser.getUser().getUserId(), new DBCriteria(),storeId));
        	}

        }catch(Exception e){
            log.info("Could not get map of authorized functions, user will be authorized for nothing");
            pCurrentUser.setAuthorizedFor(new HashMap());
            e.printStackTrace();
        }

        setCustomerStoreProperties(pSession, pCurrentUser);
    }



    /**
     *  Description of the Method
     *
     *@param  pSession      Description of Parameter
     *@param  pCurrentUser  Description of Parameter
     */
    private static void setCustomerStoreProperties
    (HttpSession pSession,
    CleanwiseUser pCurrentUser) {
    	
    	String Logo1Image = null;
    	
    	log.info("setCustomerStoreProperties");

        StoreData store = pCurrentUser.getUserStore();
        if (store != null) {
        	log.info("setCustomerStoreProperties store != null");
            pSession.setAttribute("pages.store.prefix",
            store.getPrefix().getValue());
            pSession.setAttribute("pages.store.locale",
            store.getBusEntity().getLocaleCd());
        }

        AccountData account = pCurrentUser.getUserAccount();
        // Store Administrators belong to a store, but not
        // to an account.
        if (account != null) {
        	log.info("setCustomerStoreProperties account != null");
            pSession.setAttribute
            ("pages.account.id",
            String.valueOf
            (account.getBusEntity().getBusEntityId()));

        }

        try {
            // load the default properties file.
        	log.info("load the default properties file = "+Constants.DEFAULT_PROPERTIES_FILE);
            Properties props = Utility.loadProperties(Constants.DEFAULT_PROPERTIES_FILE);
            Enumeration en = props.propertyNames();
            while (en.hasMoreElements()) {
                String s = (String) en.nextElement();
                pSession.setAttribute(s, props.getProperty(s));
                if (s.equals("UI_LOGO1"))
                  Logo1Image = props.getProperty(s);	
            }
        }
        catch (Exception e) {
            System.err.println("LogOnLogic.fetchCustomProperties: could not load properties from file: " +
            Constants.DEFAULT_PROPERTIES_FILE + "! Error: " + e.getMessage());
        }

        // Set the configurable ui items.        
        UIConfigData uioptions = pCurrentUser.getUIConfigData();
        if (Logo1Image != null)
          uioptions.setLogo1(Logo1Image); 	 
        addUIConfigDataToSession(uioptions,pSession);
        log.info("setCustomerStoreProperties uioptions.getLogo1() = "+uioptions.getLogo1());

    }

    public static void addUIConfigDataToSession(UIConfigData uioptions,
        HttpSession pSession){
    	log.info("addUIConfigDataToSession");
    	boolean removeExistingValueWhenNoSpecifiedValue = false;
    	CleanwiseUser currentUser = ShopTool.getCurrentUser(pSession);
    	if (currentUser != null) {
    		removeExistingValueWhenNoSpecifiedValue = Constants.PORTAL_ESW.equals(Utility.getAlternatePortal(currentUser.getUserStore())) ||
    												  Constants.PORTAL_ESW.equals(Utility.getAlternatePortal(currentUser.getUserAccount()));
    	}
        if (null != uioptions.getLogo1() &&
                uioptions.getLogo1().length() > 0) {
            pSession.setAttribute("pages.logo1.image",
            uioptions.getLogo1());
            log.info("uioptions.getLogo1() = "+uioptions.getLogo1());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.logo1.image");
        	}
        }
        if (null != uioptions.getStoreLogo1() &&
                uioptions.getStoreLogo1().length() > 0) {
            pSession.setAttribute("pages.store.logo1.image",
            uioptions.getStoreLogo1());
            log.info("uioptions.getStoreLogo1() = "+uioptions.getStoreLogo1());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.store.logo1.image");
        	}
        }

        if (uioptions.getLogo2().length() > 0) {
            pSession.setAttribute("pages.logo2.image",
            uioptions.getLogo2());
            log.info("uioptions.getLogo2() = "+uioptions.getLogo2());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.logo2.image");
        	}
        }

        if (uioptions.getStyleSheet().length() > 0) {
            pSession.setAttribute("pages.css",
            uioptions.getStyleSheet());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.css");
        	}
        }

        if (uioptions.getMainMsg().length() > 0) {
            pSession.setAttribute("pages.main.text",
            uioptions.getMainMsg());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.main.text");
        	}
        }

        if (uioptions.getTipsMsg().length() > 0) {
            pSession.setAttribute("pages.tips.text",
            uioptions.getTipsMsg());
        } else {
          pSession.removeAttribute("pages.tips.text");
        }
        if (uioptions.getContactUsMsg().length() > 0) {
            pSession.setAttribute("pages.contactus.text",
            uioptions.getContactUsMsg());
        } else {
          pSession.removeAttribute("pages.contactus.text");
        }

        if (uioptions.getToolbarStyle().length() > 0) {
            pSession.setAttribute("pages.toolbar.style",
            uioptions.getToolbarStyle());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.toolbar.style");
        	}
        }

        if (uioptions.getFooterMsg().length() > 0) {
            pSession.setAttribute("pages.footer.msg",
            uioptions.getFooterMsg());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.footer.msg");
        	}
        }

        if (uioptions.getPageTitle().length() > 0) {
            pSession.setAttribute("pages.title",
            uioptions.getPageTitle());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.title");
        	}
        }

        if (uioptions.getHomePageButtonLabel().length() > 0) {
            pSession.setAttribute("pages.home.button",
            uioptions.getHomePageButtonLabel());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.home.button");
        	}
        }


        if (uioptions.getCustomerServiceAliasName().length() > 0) {
            pSession.setAttribute("pages.customer.service.alias",
            uioptions.getCustomerServiceAlias());
        }
        else {
        	if (removeExistingValueWhenNoSpecifiedValue) {
                pSession.removeAttribute("pages.customer.service.alias");
        	}
        }
    }




    // Get the store the user belongs to.
    /**
     *  Description of the Method
     *
     *@param  pUserId        Description of Parameter
     *@param  pAppuser       Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void fetchUserStore(HttpServletRequest request, int pUserId, CleanwiseUser pAppuser) throws Exception {

    	//a SYSTEM_ADMINISTRATOR user
    	
        APIAccess factory = APIAccess.getAPIAccess();

        if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(pAppuser.getUser().getUserTypeCd())) { 

        	// Multi Store Db Schemas: Begin
        	
            if ("yes".equals(System.getProperty("multi.store.db"))) {
               MainDb mainDbEjb = null;
               try {
            	   mainDbEjb  = factory.getMainDbAPI();
               } catch (Exception exc) {
                   exc.printStackTrace();
                   throw new Exception("No MainDb Ejb access");
               }

               AllStoreDataVector stores = mainDbEjb.getAllStores(); //sorted by DATASOURCE
               
               AllStoreData asd = (AllStoreData) stores.get(0);
               StoreData sd = SessionTool.getStoreData(request, asd.getStoreId());

               pAppuser.setUserStore(sd);
               //pAppuser.setAssociatedStores(stores); //do I need something like this ???
               pAppuser.setMultiDbStores(stores); //is it enough ? 
                                                  //Or I also have to create a new method in CleanwiseUser.java 
                                                  //to set AssociatedStores for MultiDB Schemas ?

               // Get the UI settings for the store.
               getUIConfigStore(asd.getStoreId(), pAppuser, request);
               
            //Multi Store DB Schemas: End  
               
            } else {
            	
               BusEntityDataVector stores = factory.getStoreAPI().getAllStoresBusEntityData(Store.ORDER_BY_ID);

               BusEntityData bed = (BusEntityData) stores.get(0);
               StoreData sd = SessionTool.getStoreData(request, bed.getBusEntityId());

               pAppuser.setUserStore(sd);
               pAppuser.setAssociatedStores(stores);

               // Get the UI settings for the store.
               getUIConfigStore(bed.getBusEntityId(), pAppuser, request);
            
            }
            
        }else{ //NOT A SYSTEM_ADMINISTRATOR user
         
        	// Multi Store Db Schemas: Begin
        	
            if ("yes".equals(System.getProperty("multi.store.db"))) {
            	
               MainDb mainDbEjb = null;
               try {
            	   mainDbEjb  = factory.getMainDbAPI();
               } catch (Exception exc) {
                   exc.printStackTrace();
                   throw new Exception("No MainDb Ejb access");
               }
               
               User uBean = factory.getUserAPI();
               
               //AllStoreDataVector stores = mainDbEjb.getAllStoreListByUserId(pUserId); //new staff: worked correct, but ALWAYS showed only ONE Store 
               
               //find ALL Stores by username and password from multiple Database Schemas
               String pUserName = pAppuser.getUser().getUserName();
               String pPassword = pAppuser.getUser().getPassword();
               AllStoreDataVector stores = mainDbEjb.getAllStoreListByUserNameAndPassword(pUserName, pPassword);

               if (stores.size() == 0) {
                   //throw new Exception("No store found for user id: " + pUserId);
                   throw new Exception("No store found for user name: " + pUserName);
               }

               // we don't need to check for ACTIVE STORES, because if the Stores' info. IS FOUND in the Main DB, 
               // these stores ARE ACTIVE (otherwise user-store association(s) are deleted from the Main DB)
            
               AllStoreData asd = (AllStoreData) stores.get(0);
               StoreData sd = SessionTool.getStoreData(request, asd.getStoreId());
               
               pAppuser.setUserStore(sd);               
               //pAppuser.setAssociatedStores(stores); //do I need something like this ???
               pAppuser.setMultiDbStores(stores); //is it enough ? 
                                                  //Or I also have to create a new method in CleanwiseUser.java 
                                                  //to set AssociatedStores for MultiDB Schemas ?
               
               if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(pAppuser.getUser().getUserTypeCd())) {
                   PropertyService pBean = factory.getPropertyServiceAPI();
                   BusEntityDataVector distributors = uBean.getBusEntityCollection(pUserId, BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                   if (distributors == null) {
                       distributors = new BusEntityDataVector();
                   }
                   pAppuser.setDistributors(distributors);
                   pAppuser.setContact(uBean.getUserContact(pUserId));
                   pAppuser.setDistributionCenterId(pBean.getUserProperty(pUserId, PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID));
               }


               // Get the UI settings for the store.
               getUIConfigStore(asd.getStoreId(), pAppuser, request);
               
               // Multi Store Db Schemas: End   
               
            } else { 
            	
               User uBean = factory.getUserAPI();

               BusEntityDataVector stores = uBean.getBusEntityCollection(pUserId, BUS_ENTITY_TYPE_CD.STORE, null, 0, true);
               if (stores.size() == 0) {
                   throw new Exception("No store found for user id: " + pUserId);
               }

               BusEntityDataVector activeStores = pAppuser.getActiveStores(stores);
               if (activeStores.size() == 0) {
                   throw new Exception("No active store found for user id: " + pUserId);
               }

               BusEntityData bed = (BusEntityData) activeStores.get(0);
               StoreData sd = SessionTool.getStoreData(request, bed.getBusEntityId());

               pAppuser.setUserStore(sd);
               pAppuser.setAssociatedStores(stores);


               if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(pAppuser.getUser().getUserTypeCd())) {
                   PropertyService pBean = factory.getPropertyServiceAPI();
                   BusEntityDataVector distributors = uBean.getBusEntityCollection(pUserId, BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
                   if (distributors == null) {
                       distributors = new BusEntityDataVector();
                   }
                   pAppuser.setDistributors(distributors);
                   pAppuser.setContact(uBean.getUserContact(pUserId));
                   pAppuser.setDistributionCenterId(pBean.getUserProperty(pUserId, PROPERTY_TYPE_CD.DISTRIBUTION_CENTER_ID));
               }


               // Get the UI settings for the store.
               getUIConfigStore(bed.getBusEntityId(), pAppuser, request);
               
            }  
            
            // Multi Store Db Schemas: End
            
        }  //NOT A SYSTEM_ADMINISTRATOR user
            
        request.getSession().setAttribute("Store.id", String.valueOf(pAppuser.getUserStore().getStoreId()));
        

    }

    /**
     *Switches the currently logged in user to the store that is passed in. There is a validation done against the
     *user stores property to make sure that the user is authorized for this store.
     */
    public static ActionMessages switchUserStore(int storeId, HttpServletRequest request) throws Exception{

        log.info("switchUserStore => BEGIN. StoreId: "+storeId);

        HttpSession session = request.getSession();
        ActionMessages lMessages = new ActionMessages();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        
        //Multi Store DB Schemas: Begin
        if ("yes".equals(System.getProperty("multi.store.db"))) {
        	
        	APIAccess factory = APIAccess.getAPIAccess();
        	MainDb mainDbEjb = factory.getMainDbAPI();
        	
        	//while switching to another store User Id can become different,
        	//although the User Name and Password for this user are adentical across ALL Stores
        	//find User Id for the store that is passed in 
        	//find ALL Stores by username and password from multiple Database Schemas
            String userName = appUser.getUser().getUserName();
            String password = appUser.getUser().getPassword();
            AllUserData allUserD = mainDbEjb.getAllUserDataByUserNamePasswordAndStoreId(userName, password, storeId);
            log.info("switchUserStore(): allUserD = " + allUserD);            
        	
            //find Datasource for the new storeId in the Main DB        	
        	Integer integerStoreId = new Integer(storeId);
        	AllStoreData allStoreData = mainDbEjb.getStoreByStoreId(integerStoreId); 
        	String datasource = allStoreData.getDatasource();
        	log.info("switchUserStore(): datasource = " + datasource);
        	CallerParametersStJohn.JaasLogin(request, datasource, true);
        	
        	User userBean = factory.getUserAPI();
        	if (allUserD != null) {
        	    UserData userData = userBean.getUser(allUserD.getUserId());
        	    appUser.setUser(userData);
        	} else {
        		lMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionError("Logged in User " + userName + " is not assigned to the store " + allStoreData.getStoreName()));
                return lMessages;
        	}
        	
        }
        //Multi Store DB Schemas: End

        int oldStoreId = 0;
        if (appUser != null) {
            StoreData storeData = appUser.getUserStore();
            if (storeData != null) {
                if (storeData.getBusEntity() != null) {
                    oldStoreId = storeData.getBusEntity().getBusEntityId();
                }
            }
        }

        //Multi Store DB Schemas: Begin
        if ("yes".equals(System.getProperty("multi.store.db"))) {        	
           if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
              log.info("switchUserStore => authorizedStoreIds: "+Utility.toIdVector(appUser.getMultiDbStores()));
              Iterator it = appUser.getMultiDbStores().iterator();
              boolean authorized = false;
              while(it.hasNext()){
            	AllStoreData aStore = (AllStoreData) it.next();
                if(aStore.getStoreId() == storeId){
                    authorized = true;
                    break;
                }
              }

              if(!authorized){
                lMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionError("user.bad.store"));
                return lMessages;
              }
           } //endif
         //Multi Store DB Schemas: End   
        } else {
           if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
                log.info("switchUserStore => authorizedStoreIds: "+Utility.toIdVector(appUser.getStores()));
                Iterator it = appUser.getStores().iterator();
                 boolean authorized = false;
                 while(it.hasNext()){
                     BusEntityData aStore = (BusEntityData) it.next();
                     if(aStore.getBusEntityId() == storeId){
                         authorized = true;
                         break;
                     }
                 }

                 if(!authorized){
                     lMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionError("user.bad.store"));
                     return lMessages;
                 }
            } //endif
        } //endif

        StoreData sd = SessionTool.getStoreData(request, storeId);
        appUser.setUserStore(sd);
        session.setAttribute("Store.id", String.valueOf(storeId));
        initMainUserUI(appUser, session, request);
        HashMap categoriesForStoreFlag = new HashMap();
        categoriesForStoreFlag.put(String.valueOf(storeId), Boolean.FALSE);
        session.setAttribute(Constants.CATEGORIES_FOR_STORE_FLAG, categoriesForStoreFlag);

        log.info("[LogOnLogic.switchUserStore] old storeId: " + oldStoreId + ", new storeId: " + storeId);
        if (oldStoreId != storeId) {
            updateSessionDataAfterStoreChange(request);
        }

        log.info("switchUserStore => END.");

        return lMessages;
    }

    public static void updateSessionDataAfterStoreChange(HttpServletRequest request) throws Exception {
        log.info("[LogOnLogic.updateSessionDataAfterStoreChange] Updating the data from session after store change");
        if (request == null) {
            return;
        }
        HttpSession session = request.getSession();
        Enumeration attributesEnum = session.getAttributeNames();
        if (attributesEnum != null) {
            while (attributesEnum.hasMoreElements()) {
                String attributeName = (String)attributesEnum.nextElement();
                if (attributeName != null) {
                    Object attributeObj = (Object)session.getAttribute(attributeName);
                    String className = attributeObj.getClass().getName();
                    if (className.startsWith("com.cleanwise")) {
                        if (attributeObj instanceof UpdaterAfterStoreChange) {
                            UpdaterAfterStoreChange updater = (UpdaterAfterStoreChange)attributeObj;
                            try {
                                updater.updateDataAfterStoreChange();
                            } catch (Exception ex) {
                                log.info("[LogOnLogic.updateSessionDataAfterStoreChange] An error occurred. " + ex.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

    public static ActionMessages switchUserAccount(int pAccountId, HttpServletRequest request) throws Exception {

        log.info("switchUserAccount => BEGIN. pAccountId: "+pAccountId);
        HttpSession session = request.getSession();
        ActionMessages aMessages = new ActionMessages();

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
            IdVector authorizedIds = Utility.toIdVector(appUser.getAccounts());
            log.info("switchUserAccount => authorizedIds: "+authorizedIds);
            if (!authorizedIds.contains(pAccountId)) {
                log.info("switchUserAccount => ERROR: No such account found. AccounId: "+pAccountId);
                aMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("user.bad.account"));
                return aMessages;
            }
        }

        AccountData ad = SessionTool.getAccountData(request, pAccountId);
        appUser.setUserAccount(ad);
        aMessages = switchUserStore(ad.getStoreAssoc().getBusEntity2Id(),request);

        log.info("switchUserAccount => END. Error Size: "+aMessages.size());

        return aMessages;
    }

    /*
     * Does the main work of initializing the user interface for the account and store
     */
    private static void initMainUserUI(CleanwiseUser appUser, HttpSession session, HttpServletRequest request){

        getUIConfigStore(appUser.getUserStore().getStoreId(), appUser, request);
        if(appUser.getUserAccount() != null && (appUser.isaCustomer() || appUser.isaAccountAdmin())){
            getUIConfigAccount(appUser.getUserAccount().getBusEntity().getBusEntityId(), appUser, request);
        }

        fetchCustomProperties(session, appUser);
        initUserAttributes(session);
        resetLoginInfo(session);

    }

    public static void initAdmin2UI(CleanwiseUser appUser, HttpServletRequest request) {

        log.info("initAdmin2UI => BEGIN");

        try {

            APIAccess factory = APIAccess.getAPIAccess();
            Ui uiEjb = factory.getUiAPI();
            Group groupEjb = factory.getGroupAPI();

            GroupData group = groupEjb.getUserUiGroup(appUser.getUserId());
            log.info("initAdmin2UI => USER UI GROUP  " + group);
            if (group == null && appUser.getUserAccount() != null) {
                group = groupEjb.getAccountUiGroup(appUser.getUserAccount().getAccountId());
                log.info("initAdmin2UI => ACCOUNT UI GROUP  " + group);
            }

            if (group == null && appUser.getUserStore() != null) {
                group = groupEjb.getStoreUiGroup(appUser.getUserStore().getStoreId());
                log.info("initAdmin2UI => STORE UI GROUP  " + group);
            }

            if (group == null) {
                appUser.setUi(null);
            } else {

                UiView ui = uiEjb.getUiForGroup(group.getGroupId());
                if (RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())
                        || RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())) {
                    Admin2UiUtillity.rebuild4SysAdmin(ui);

                }
                appUser.setUi(ui);
            }

        } catch (Exception e) {
            appUser.setUi(null);
            e.printStackTrace();
        }

        log.info("initAdmin2UI => END.");
    }

    public static void getUIConfigStore(int pBusEntityId, CleanwiseUser pAppuser, HttpServletRequest request) {
    getUIConfigStore(pBusEntityId, pAppuser, pAppuser.getUser().getUserId(), request);
  }

  public static void getUIConfigStore(int pBusEntityId,
      CleanwiseUser pAppuser, int userId, HttpServletRequest request) {
    fetchUIConfigOptions(true, pBusEntityId, pAppuser, userId, request);
  }


    public static void getUIConfigAccount(int pBusEntityId,
        CleanwiseUser pAppuser, HttpServletRequest request) {
      getUIConfigAccount(pBusEntityId, pAppuser, pAppuser.getUser()
        .getUserId(), request);
    }
    public static void getUIConfigAccount(int pBusEntityId,
            CleanwiseUser pAppuser, int userId, HttpServletRequest request) {
  fetchUIConfigOptions( false, pBusEntityId, pAppuser, userId, request);
    }

    /**
     *  Description of the Method
     *
     *@param  pBusEntityId  Description of Parameter
     *@param  pAppuser      Description of Parameter
     */
    public static void  fetchUIConfigOptions(boolean pIsStore, int pBusEntityId,
           CleanwiseUser pAppuser, int userId, HttpServletRequest request) {
        try {
        	log.info("fetchUIConfigOptions(): pIsStore = " + pIsStore + " pBusEntityId = " + pBusEntityId + " userId = " + userId);
            APIAccess factory = APIAccess.getAPIAccess();
            PropertyService psBean = factory.getPropertyServiceAPI();
            Locale userLocale = pAppuser.getUserLocaleCode(request.getLocale());
            log.info("fetchUIConfigOptions(): userLocale = " + userLocale);
            UIConfigData uioptions= null;
            ApplicationDomainNameData domain = SessionTool.getApplicationDomainData(request);
            if (pAppuser.isaReportingUser()){                        
              int defaultStoreId = domain.getDefaultStore().getBusEntityId();
               uioptions = psBean.getStoreUIConfigData(defaultStoreId, "" + userLocale);  // default UI by domain
             } else {
               uioptions = psBean.fetchUIConfigData(pBusEntityId, ""  + userLocale, userId);
            }
            
            if (null == uioptions) {
                return;
            }
            if (Utility.isSet(uioptions.getToolbarStyle())) {
                pAppuser.getUIConfigData().setToolbarStyle(
                uioptions.getToolbarStyle());
            }
            //if (Utility.isSet(uioptions.getStoreLogo1())) {
            //    pAppuser.getUIConfigData().setStoreLogo1(uioptions.getStoreLogo1());
            //} else {
            //    pAppuser.getUIConfigData().setStoreLogo1(null);
            //}
        	if (Utility.isSet(uioptions.getLogo1())) {
                pAppuser.getUIConfigData().setLogo1(
                		uioptions.getLogo1());

                if (pIsStore) {
                    pAppuser.getUIConfigData().setStoreLogo1
                        (uioptions.getLogo1());
                    }
            } 
            if (domain.isDefaultDomain()) {            	
            	log.info("domain.isDefaultDomain() = true");    
           	
            } else {
            	log.info("domain.isDefaultDomain() = false");           	
            }
            
            if (Utility.isSet(uioptions.getLogo2())) {
                pAppuser.getUIConfigData().setLogo2(
                uioptions.getLogo2());
            //} else {
            //    pAppuser.getUIConfigData().setLogo2(null);
            }
            if (Utility.isSet(uioptions.getStyleSheet())) {
                pAppuser.getUIConfigData().setStyleSheet(
                uioptions.getStyleSheet());
            }
            if (Utility.isSet(uioptions.getMainMsg())) {
                pAppuser.getUIConfigData().setMainMsg(
                uioptions.getMainMsg());
            }
            if (Utility.isSet(uioptions.getTipsMsg())) {
                pAppuser.getUIConfigData().setTipsMsg(
                uioptions.getTipsMsg());
            }
            if (Utility.isSet(uioptions.getContactUsMsg())) {
                pAppuser.getUIConfigData().setContactUsMsg(
                uioptions.getContactUsMsg());
            }
            if (Utility.isSet(uioptions.getPageTitle())) {
                pAppuser.getUIConfigData().setPageTitle(
                uioptions.getPageTitle());
            }
            if (Utility.isSet(uioptions.getFooterMsg())) {
                pAppuser.getUIConfigData().setFooterMsg(
                uioptions.getFooterMsg());
            }
            if (Utility.isSet(uioptions.getHomePageButtonLabel())) {
                pAppuser.getUIConfigData().setHomePageButtonLabel(
                uioptions.getHomePageButtonLabel());
            }
            if (Utility.isSet(uioptions.getCustomerServiceAlias())) {
                pAppuser.getUIConfigData().setCustomerServiceAlias(
                uioptions.getCustomerServiceAlias());
            }
        }
        catch (Exception e) {
            System.err.println("LogOnLogic.fetchUIConfigOptions: " + e);
            e.printStackTrace();
        }
        return;
    }

    /**
     *  Get the account for the supplied user.
     *
     *@param  pUserId        the user id loging in
     *@param  pAppuser       the retrieved application user
     *@exception  Exception  if an error occurs
     */
    private static void fetchUserAccount(HttpServletRequest request,
                                         int pUserId,
                                         CleanwiseUser pAppuser) throws Exception {

        APIAccess factory = APIAccess.getAPIAccess();
        User userEjb = factory.getUserAPI();

        BusEntityDataVector stores = userEjb.getBusEntityCollection(pUserId, BUS_ENTITY_TYPE_CD.STORE, null, 0, true);
        if (stores.size() == 0) {
            throw new Exception("No store found for user id: " + pUserId);
        }

        BusEntityDataVector activeStores = pAppuser.getActiveStores(stores);
        if (activeStores.size() == 0) {
            throw new Exception("No active store found for user id: " + pUserId);
        }

        BusEntityDataVector accounts = userEjb.getBusEntityCollection(pUserId, BUS_ENTITY_TYPE_CD.ACCOUNT);
        if (accounts.size() == 0) {
            throw new Exception("No account found for user id: " + pUserId);
        }

        BusEntityData bed = (BusEntityData) accounts.get(0);
        // Get the account.
        int accountid = bed.getBusEntityId();
        AccountData ad = SessionTool.getAccountData(request, accountid);
        pAppuser.setUserAccount(ad);
        pAppuser.setAccounts(accounts);

        int storeid = ad.getStoreAssoc().getBusEntity2Id();
        StoreData sd = SessionTool.getStoreData(request, storeid);
        pAppuser.setUserStore(sd);
        pAppuser.setAssociatedStores(stores);
        // Get the UI settings for the store.
        getUIConfigStore(storeid, pAppuser, request);
        // Get the UI settings for the account.
        getUIConfigAccount(accountid, pAppuser, request);

        return;
    }

  private static void fetchSiteAccount(StoreData pStore,
                                       AccountData pAccount,
                                       CleanwiseUser pAppuser,
                                       HttpServletRequest request) throws Exception {

        pAppuser.setUserAccount(pAccount);
        pAppuser.setUserStore(pStore);

        // Get the UI settings for the store.
        getUIConfigStore(pStore.getStoreId(), pAppuser, request);
        // Get the UI settings for the account.
        getUIConfigAccount(pAccount.getAccountId(), pAppuser, request);

        return;
    }

    /**
     * Get the account for the supplied user.
     *
     * @param pUserId  the user id loging in
     * @param pAppuser the retrieved application user
     * @throws Exception if an error occurs
     */
    private static void fetchSiteAccount(SiteData pSite,
                                         CleanwiseUser pAppuser,
                                         HttpServletRequest request) throws Exception {
        APIAccess factory = APIAccess.getAPIAccess();

        BusEntityData bed = pSite.getAccountBusEntity();

        // Get the account.
        int accountid = bed.getBusEntityId();
        Account aBean = factory.getAccountAPI();
        AccountData ad = aBean.getAccount(accountid, 0);

        // Get the store.
        Store sBean = factory.getStoreAPI();
        int storeid = ad.getStoreAssoc().getBusEntity2Id();
        StoreData sd = sBean.getStore(storeid);

        fetchSiteAccount(sd, ad, pAppuser, request);

        return;
    }

    public static int fetchContractId( int pCatalogId )
    throws Exception  {

        APIAccess factory = new APIAccess();
        Contract conBean = factory.getContractAPI();
        ContractDataVector v = conBean.getContractsByCatalog(pCatalogId);
        //get the first active contract
        if(v.size() > 0){
            for(int i=0; i<v.size(); i++){
                ContractData cd = (ContractData)v.get(i);
                String status = cd.getContractStatusCd();
                if(status.equals(CONTRACT_STATUS_CD.ACTIVE)){
                    return cd.getContractId();
                }
            }
        }
        return 0;
    }

    private static void resetTheCart(HttpServletRequest request,
    CleanwiseUser pCwUser) {

        ShoppingCartData sc = new ShoppingCartData();
        sc.setUser(pCwUser.getUser());
        sc.setSite(pCwUser.getSite());

        request.getSession().setAttribute
        (Constants.SHOPPING_CART, sc);
    }

    private static void resetTheInventoryCart(HttpServletRequest request,CleanwiseUser pCwUser) {
        request.getSession().setAttribute(Constants.INVENTORY_SHOPPING_CART,null);
    }
    public static boolean siteShop( HttpServletRequest request, int pSiteId)
    	throws Exception {
    	return siteShop(request, pSiteId, false);
    }
    public static boolean siteShop( HttpServletRequest request, int pSiteId, boolean allowSiteIsNotAssignedToUser)
    throws Exception {

        HttpSession session = request.getSession();

        try {
            APIAccess factory = new APIAccess();
            Site siteBean = factory.getSiteAPI();
            User uBean = factory.getUserAPI();
            Contract contractBean = factory.getContractAPI();

            CleanwiseUser appUser = (CleanwiseUser)
    session.getAttribute(Constants.APP_USER);
            if (appUser == null) {
                log.info("App user was null");
                return false;
            }
            if(appUser.isaCustomer() || appUser.isaMSB()){
                if(!factory.getUserAPI().isSiteOfUser
       (pSiteId,appUser.getUser().getUserId())){
                    log.info("Site does not belong to user");
                    if(!allowSiteIsNotAssignedToUser)
                    {
                    	return false;
                    }
                }
            }

            SiteData sd = SessionTool.getSiteDataNoCache(request, pSiteId);
            SiteDataVector sdv = new SiteDataVector();
            sdv.add(sd);
            SelectShippingAddressForm form =
            new SelectShippingAddressForm();
            form.setMultiAcctFlag(false);
            form.setSites(sdv);

      appUser.setSite(sd);
            form.setAppUser(appUser);
            session.setAttribute
            ("SelectShippingAddress", form);

            // set the user account.
            int accountid = sd.getAccountBusEntity().getBusEntityId();
            AccountData ad = SessionTool.getAccountData(request, accountid);
            appUser.setUserAccount(ad);

            // set the user store.
            int storeid = ad.getStoreAssoc().getBusEntity2Id();
            StoreData store_d = SessionTool.getStoreData(request, storeid);
            appUser.setUserStore(store_d);

            appUser.getContactUsList().clear();
            //initialize the contact us
            if(RefCodeNames.CONTACT_US_TYPE_CD.DISTRIBUTOR.equals(Utility.getPropertyValue(store_d.getMiscProperties(),RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TYPE_CD))){
                DistributorDataVector distDV = siteBean.getAllDistributorsForSite(pSiteId);

                Iterator it = distDV.iterator();
                while(it.hasNext()){
                    DistributorData dist = (DistributorData) it.next();
                    ContactUsInfo cont = new ContactUsInfo();
                    cont.setAddress(dist.getPrimaryAddress());
                    if(Utility.isSet(dist.getRuntimeDisplayName())){
                        cont.setNickName(dist.getRuntimeDisplayName());
                    }else{
                        cont.setNickName(dist.getBusEntity().getShortDesc());
                    }
                    if(Utility.isSet(dist.getPrimaryAddress().getName1()) || Utility.isSet(dist.getPrimaryAddress().getName2())){
                        if(!Utility.isSet(dist.getPrimaryAddress().getName1())){
                            cont.setContactName(dist.getPrimaryAddress().getName2());
                        }else if(!Utility.isSet(dist.getPrimaryAddress().getName2())){
                            cont.setContactName(dist.getPrimaryAddress().getName1());
                        }else{
                            cont.setContactName(dist.getPrimaryAddress().getName1()+" "+dist.getPrimaryAddress().getName2());
                        }
                    }
                    cont.setEmail(dist.getPrimaryEmail().getEmailAddress());
                    cont.setFax(dist.getPrimaryFax().getPhoneNum());
                    cont.setPhone(dist.getPrimaryPhone().getPhoneNum());
                    cont.setCallHours(dist.getCallHours());
                    appUser.addContactUs(cont);
                }
            }else{
                ContactUsInfo cont = new ContactUsInfo();
                cont.setAddress(store_d.getPrimaryAddress());
                cont.setNickName(store_d.getStoreBusinessName().getValue());
                cont.setEmail(store_d.getContactUsEmail().getEmailAddress());
                cont.setFax(ad.getOrderFax().getPhoneNum());
                cont.setPhone(ad.getOrderPhone().getPhoneNum());
                cont.setCallHours(store_d.getCallHours().getValue());
                appUser.addContactUs(cont);
            }

            if(store_d != null && Utility.isSet(store_d.getEvenRowColor())){
                session.setAttribute(Constants.EVEN_ROW_COLOR,store_d.getEvenRowColor());
            }
            if(store_d != null && Utility.isSet(store_d.getOddRowColor())){
                session.setAttribute(Constants.ODD_ROW_COLOR,store_d.getOddRowColor());
            }


            // Get the UI settings for the store.
            getUIConfigStore(storeid, appUser, request);
            // Get the UI settings for the account.
            getUIConfigAccount(accountid, appUser, request);
            // Set session parameters
            setCustomerStoreProperties(session,appUser);

            // Set the user into the shopping cart.
            resetTheCart( request, appUser);
            resetTheInventoryCart(request, appUser);
            SelectShippingAddressLogic.setShoppingSessionObjects(request.getSession(), appUser);

            // get the catalog for the site.
            int siteCat = 0;
            CatalogInformation catInfo =
            factory.getCatalogInformationAPI();
            CatalogData cd = catInfo.getSiteCatalog
            (sd.getBusEntity().getBusEntityId());
            if ( null == cd ) {
                log.info("Catalog Data was null");
                return false;
            }

            siteCat = cd.getCatalogId();
            session.setAttribute
            (Constants.CATALOG_ID, new Integer(siteCat));
            session.setAttribute
            (Constants.CATALOG_NAME, new String(cd.getShortDesc()));

            ContractData conD = sd.getContractData();
            int conId = 0;
            if(conD!=null) {
			    conId = sd.getContractData().getContractId();
                session.setAttribute(Constants.CONTRACT_NAME,
                		conD.getShortDesc());
                session.setAttribute(Constants.CATALOG_LOCALE,conD.getLocaleCd());
                log.info("Using Contract ID: "+conId);
                log.info("Using catalog locale: "+conD.getLocaleCd());
                int decimalPlaces = -1;
                try{
                	decimalPlaces = factory.getCurrencyAPI().getDecimalPlacesForLocale(conD.getLocaleCd());
                	log.info("decimalPlaces="+decimalPlaces);
                }catch(Exception e){
                	log.error("Could not get decimal places for currency", e);
                }
                session.setAttribute(Constants.CATALOG_DECIMALS, decimalPlaces);
            }
            else{
                session.setAttribute(Constants.CONTRACT_NAME,"");
            }

      session.setAttribute
    (Constants.CONTRACT_ID,new Integer(conId));
            log.info(" pSiteId=" + pSiteId +
             " Site contract Id="+conId);


            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("LogOnLogic error: " + e);
        }
        log.info("didn't return true somewhere");
        return false;
    }

    public static ActionErrors processFakeLogOn
            (HttpServletRequest request, int pOptAccountId) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if ( session.getAttribute(Constants.APP_USER) != null ) {
      // This user is already logged in.
      // Nothing more to do.
      return ae;
  }

        Account ab = null;
        try {
      APIAccess factory = new APIAccess();
      ab = factory.getAccountAPI();
        }
        catch (Exception e) {
            e.printStackTrace();
         String errorMess =
            ClwI18nUtil.getMessage(request,"login.errors.gettingAccountData1",null);
      ae.add("error", new ActionError("error.systemError",errorMess));
      return ae;
        }
        boolean accountInactive = false, storeInactive = false;

        String userName = "ItemAccess";
        session.setAttribute(Constants.USER_NAME, userName);
        session.setAttribute(Constants.USER_ID, "0" );

        CleanwiseUser appUser = new CleanwiseUser();
        appUser.setUserName(userName);
        appUser.setUser(UserData.createValue());
        session.setAttribute(Constants.USER_TYPE,
        RefCodeNames.USER_TYPE_CD.CUSTOMER);
        session.setAttribute(Constants.USER_LOGIN_DATE,
           new java.util.Date());

        if ( pOptAccountId > 0 ) {
            // get account data
            AccountData accountData;
            try{
                accountData =
        SessionTool.getAccountData(request, pOptAccountId);
            }
            catch (java.rmi.RemoteException exc) {
                exc.printStackTrace();
                String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.gettingAccountData",null);
                ae.add("error", new ActionError("error.systemError",errorMess));
                return ae;
            }
            catch (Exception exc) {
                String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.gettingAccountData2",null);
                ae.add("error", new ActionError("error.systemError",errorMess));
                return ae;
            }
            if( accountData.getBusEntity().getBusEntityStatusCd().equals("INACTIVE") ){
                accountInactive = true;
            }
            appUser.setUserAccount(accountData);

            // get store data
            BusEntityAssocData bead = accountData.getStoreAssoc();
            int storeId = bead.getBusEntity2Id();
            StoreData storeData;
            try {
                storeData = SessionTool.getStoreData
        (request,storeId);
            }
            catch (java.rmi.RemoteException exc) {
                exc.printStackTrace();
                String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.gettingSiteData",null);
                ae.add("error", new ActionError("error.systemError",errorMess));
                return ae;
            }
            catch (Exception exc) {
                exc.printStackTrace();
                String errorMess =
                  ClwI18nUtil.getMessage(request,"login.errors.gettingSiteData",null);
                ae.add("error", new ActionError("error.systemError",errorMess));
                return ae;
            }

            appUser.setUserStore(storeData);

            BusEntityData bed =  storeData.getBusEntity();
            String storeStatus = bed.getBusEntityStatusCd();
            if( storeStatus.equals("INACTIVE") ){
                storeInactive = true;
            }
        }

        if ( accountInactive ) {
          String errorMess =
            ClwI18nUtil.getMessage(request,"login.errors.inactiveAccount",null);
      ae.add("error", new ActionError("error.simpleGenericError",errorMess));
      return ae;
        }

        if ( storeInactive ) {
          String errorMess =
            ClwI18nUtil.getMessage(request,"login.errors.inactiveStore",null);
      ae.add("error", new ActionError("error.simpleGenericError",errorMess));
      return ae;
        }


        // Set the user name and id in the session for other session
        // based user access.

        session.setAttribute(Constants.APP_USER, appUser);
        session.setAttribute(Constants.REQUEST_NUM, "0");

        // Now set the store and account specific session attributes
        // for the user.
        fetchCustomProperties(session, appUser);
        initUserAttributes(session);
        return ae;
    }




    private static String getCookie(HttpServletRequest request,String name){
        Cookie[] cc = request.getCookies();
        for(int i=0;i<cc.length;i++){
            Cookie c = cc[i];
            if(name.equalsIgnoreCase(c.getName())){
                return c.getValue();
            }
        }
        return null;
    }



    /**
     * Decodes all of the known cookies from the request (somepag.jsp?cookie=value) for those cookies in the
     * @see KNOWN_COOKIES list and places them back as cookies in the response.  On the next redirect they will
     * become cookies again.  They ARE NOT added to the session as it is assumed that a call to send redirect is
     * immenent and so the session does not need to be updated.
     */
    private static void decodeCookies(HttpServletRequest request,HttpServletResponse response){
        for(int i=0,len=SessionTool.KNOWN_COOKIES.size();i<len;i++){
            String name = (String) SessionTool.KNOWN_COOKIES.get(i);
            String value = (String) request.getAttribute(name);
            if(value != null){
                SessionTool.setCookie(response,request,name,value,null);
            }
        }
    }

    /**
     * Initializes some default variables for users that have not yet logged in.
     * Uses the domain of the request to populate store data etc.
     * @param request
     */
    public static void initAnonymousUser(HttpServletRequest request,HttpServletResponse response){

        String reqCt = "reqCt";
        //Read in jsessionid param and write it out as a cookie and redirect user.
        //This is not spec compliant, but it gets around cross url issues
      String sesId = request.getParameter("jsessionid");
      if(sesId!= null && (request.getSession(false) == null || !sesId.equals(request.getSession(false).getId()))){
            String reqCtVal = getCookie(request,reqCt);
            int reqCtValInt=0;
            if(reqCtVal!=null){
                try{
                    reqCtValInt = Integer.parseInt(reqCtVal);
                    reqCtValInt++;
                }catch(Exception e){}
            }
            if(reqCtValInt < 3){
                //avoid potential endless redirects
                request.setAttribute(reqCt,Integer.toString(reqCtValInt));
                SessionTool.setCookie(response, request,"jsessionid", sesId,null);
                decodeCookies(request,response);
                try{
                  String url = response.encodeRedirectURL(request.getRequestURI());
                  response.sendRedirect(url);
                  return;
                }catch(Exception e){e.printStackTrace();}
            }
      }




      CleanwiseUser appUser = ShopTool.getCurrentUser(request);
      if(appUser == null || appUser.getUserStore() == null){
        //get the store for this domain
        try{
          HttpSession session = request.getSession(true);
            if(appUser == null){
              appUser = new CleanwiseUser();
              session.setAttribute(Constants.APP_USER,appUser);
            }

          APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
          if(factory == null){
            factory = APIAccess.getAPIAccess();
                session.setAttribute(Constants.APIACCESS, factory);
          }


            ApplicationDomainNameData domainData = SessionTool.getApplicationDomainData(request);
            StoreData store = null;
            if(domainData != null && domainData.getDefaultStore() != null){
              store =SessionTool.getStoreData(request, domainData.getDefaultStore().getBusEntityId());
            }
            appUser.setUserStore(store);


            initMainUserUI(appUser, session, request);
          }catch(Exception e){
            e.printStackTrace();
          }



      }
      return;
    }

    public static ActionErrors initNextDeliveryDateForSite(HttpSession session, HttpServletRequest request ) {
      ActionErrors ae = new ActionErrors();

      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      CleanwiseUser appUser = ShopTool.getCurrentUser(request);
      if(appUser != null && appUser.getSite() != null){
        log.info("-- LogOnLogic :: siteId = " + appUser.getSite().getSiteId());
        try {
          Site siteBean = factory.getSiteAPI();
          if (siteBean != null){
            SiteDeliveryDataVector nextDeliveryDataVector = siteBean.getNextSiteDeliveryData(appUser.getSite().getSiteId());
            session.setAttribute(Constants.NEXT_DELIVERY_DATA, nextDeliveryDataVector);
          }
        }
        catch (RemoteException ex) {
          ex.printStackTrace();
          String errorMess = ClwI18nUtil.getMessage(request,
              "login.errors.gettingNextDeliveryDate", null);
          ae.add("error", new ActionError("error.systemError", errorMess));
          incrementLoginFailure(session);
          return ae;

        }
        catch (APIServiceAccessException ex) {
          ex.printStackTrace();
          String errorMess = ClwI18nUtil.getMessage(request,
              "login.errors.gettingNextDeliveryDate", null);
          ae.add("error", new ActionError("error.systemError", errorMess));
          incrementLoginFailure(session);
          return ae;
        }
      }
      return ae;
      //
    }
    
    /**
     * Creates an access token to allow a user to access the system without entering a 
     * username and a password.  This access token can be appened to a url to send back
     * to a client machine.  The access token is valide only for a short period of time.
     */
    public static UserAcessTokenViewData createAccessToken(APIAccess factory,CleanwiseUser user){
    	assert user.getUserStore() != null : user;
    	try{
	    	LdapItemData ldap = new LdapItemData();
	    	ldap.setUserName(user.getUserName());
	    	User userEjb = factory.getUserAPI();
	    	return userEjb.createAccessToken(ldap, false, user.getUserStore().getStoreId());
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

}
