package com.espendwise.webservice.restful;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.value.LoginInfoView;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.util.ICleanwiseUser;

import com.espendwise.webservice.restful.security.RestSecurity;
import com.espendwise.webservice.restful.value.*;

import javax.ws.rs.*;
import java.util.*;

import java.text.SimpleDateFormat;
import java.text.DateFormat;


@Path("/instance")
public class AndroidRestService {

    private static final Logger log = Logger.getLogger(AndroidRestService.class);
    public static final String OK = "OK";
    public static final String NOT_OK = "NOT_OK";
    public static final String N_A = "N/A";
    private DateFormat dateFormat = (new SimpleDateFormat("MM/dd/yyyy"));
    private DateFormat timeFormat = (new SimpleDateFormat("HH:mm"));
    private DateFormat dateTimeFormat = (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS"));

    @PUT
    @Path("/login")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public LoginData login(LoginData pLoginData){

    	String show = "";
        show += "\n****************************** login ***************************************\n";
        show += "userName: " + pLoginData.getUserName() +
                ", password: " + pLoginData.getPassword() + "\n";
        show += "****************************************************************************\n";
        log.info(show);
    	
            LoginInfoView userInfo = new LoginInfoView();
            userInfo.setUserName(pLoginData.getUserName());
            userInfo.setPassword(pLoginData.getPassword());
            Date curDate = new Date();
            UserData user;
            LoginData summary = new LoginData();
            try {

                APIAccess factory = APIAccess.getAPIAccess();
                User userEjb = factory.getUserAPI();
        		Group groupEjb = factory.getGroupAPI();


                String storeUnit = CallerUtilsStJohn.initCallerParameters(userInfo, RefCodeNames.APP_SOURCE_CD.AUDITOR);
                log.info("===storeUnit: " + storeUnit);
                if("yes".equals(System.getProperty("multi.store.db")) && "unknown".equals(storeUnit)) {
                    summary.setResult("loginErrorsWrongNamePassword");
                    return summary;
                }
                CallerUtilsStJohn.replaceUnitAndUserWithUser(userInfo);

                log.info("===login.start");
                user = userEjb.login(userInfo);
                log.info("===user: " + user);
                if (!Utility.isSet(user.getPassword())) {
                    summary.setResult("loginErrorsWrongNamePassword");
                    return summary;
                }
                
                String res = checkUser(user, curDate);
                if (res != OK) {
                    summary.setResult(res);
                    return summary;
                }
                res = fetchUserAccount(user.getUserId(), pLoginData.getTracking());
                if (res != OK) {
                    summary.setResult(res);
                    return summary;
                }

                CleanwiseUser appUser = new CleanwiseUser();
                appUser.setUser(user);
                Map groupAssociations = groupEjb.getAllValidUserGroupAssociations(user.getUserId());
                Set<String> authorizedForFunctions = (Set) groupAssociations.get(RefCodeNames.GROUP_ASSOC_CD.FUNCTION_OF_GROUP);

                String appFuncs = "\n";
                for (String f : authorizedForFunctions){
                	appFuncs += "appFunc: " + f + "\n";;
                }
                log.info(appFuncs);
                appUser.setAuthorizedFor(groupAssociations);

                
                
                String securityKey = RestSecurity.getInstance().getSecurityKey(user.getUserName(), user.getPassword());
                RestSecurity.getInstance().register(securityKey, appUser, storeUnit);
            		   
                summary.setToken(securityKey);
                summary.setResult(OK);
            } catch (Exception e) {
                e.printStackTrace();
                summary.setResult(e.getMessage());
            }
            show = "";
            show += "\n****************************************************************************\n";
            show += "userName: " + pLoginData.getUserName() +
                    ", password: " + pLoginData.getPassword() + "\n";
            show += "token: " + summary.getToken() + "\n";
            show += "******************************* end login **********************************\n";
            log.info(show);
            return summary;
    }

    
    @PUT
    @Path("/setInventory")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public InventoryLocationData setInventory(InventoryLocationData pLocationData){
    	InventoryLocationData response = pLocationData;
    	String show = "";
        show += "\n***************************** setInventory *********************************\n";
        show += "userName: " + pLocationData.getUserName() +
                ", password: " + pLocationData.getPassword() + "\n";
        show += "location: " + pLocationData.getLocationId() + "\n";
        for (InventoryItemData item : pLocationData.getItems()) {
            show += "itemId: " + item.getItemId() +
                    ", quantity: " + item.getQuantity() + "\n";
        }
        show += "****************************************************************************\n";
        log.info(show);

        try {
        	ICleanwiseUser sessionUser = authorization((LoginData)pLocationData, response); 
            APIAccess factory = APIAccess.getAPIAccess();
            IntegrationServices isEjb = factory.getIntegrationServicesAPI();
            HashMap<Integer, Integer> itemMap = new HashMap<Integer, Integer>();
            for(InventoryItemData item : pLocationData.getItems()) {
            	itemMap.put(item.getItemId(), item.getQuantity());
            }
            log.info("ICleanwiseUser: " + sessionUser.getUser().getUserName());
            HashMap result = isEjb.processInventoryUpdate(pLocationData.getLocationId(), itemMap, sessionUser.getUser().getUserName());
            for(InventoryItemData item : pLocationData.getItems()) {
            	item.setSubmitted(result.containsKey(item.getItemId()));
//            	item.setSubmitted(true);
            }
            if (result.size() == response.getItems().size()) {
            	response.setSubmitted(true);
            }
            response.setResult(OK);
        } catch (AndroidRestServiceLoginException le) {
			response.setResult(le.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult(e.getMessage());
        }

        show = "";
        show += "\n****************************************************************************\n";
        show += "result: " + response.getResult() + "\n";
        show += "**************************** end setInventory ******************************\n";
        log.info(show);
        return response;
}
    	
    
    
    private String  checkUser(UserData user, Date curDate) {

        Date pExpDate = user.getExpDate();
        Date pEffDate = user.getEffDate();

        //exp date can be null - don't compare dates then - but ...
        if (pExpDate != null && !curDate.before(pExpDate)) {
            return "login.errors.accountExpired";
        }

        // ... eff date can't be null
        if (pEffDate == null) {
            return "login.errors.accountNoEffDate";
        }

        if (!curDate.after(pEffDate)) {
            return "login.errors.accountNotActiveYet";
        }

        if (null == user.getUserTypeCd()) {
            return "login.errors.userTypeUnknown";
        }

        //Check user status
        if ((user.getUserStatusCd() == null) || (!user.getUserStatusCd().equals(RefCodeNames.USER_STATUS_CD.ACTIVE))) {
            return "login.errors.userNotActive";
        }

        String roleCd = user.getUserRoleCd();
        if (roleCd == null) {
            return "login.errors.userNoRole";
        }
        return OK;

    }

    private String fetchUserAccount(int pUserId, boolean tracking) throws Exception {
        APIAccess factory = APIAccess.getAPIAccess();
        User userEjb = factory.getUserAPI();

        List<BusEntityData> stores = userEjb.getBusEntityCollection(pUserId, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
        if (stores.size() == 0) {
            return "login.errors.noStoreFoundForUser";
        }
        List<BusEntityData> accounts = userEjb.getBusEntityCollection(pUserId, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
        if (accounts.size() == 0) {
            return "login.errors.noAccountFoundForUser";
        }
        return OK;
    }

    private ICleanwiseUser authorization(LoginData loginData, LoginData newLoginData) 
            throws Exception, AndroidRestServiceLoginException {  

    	if (RestSecurity.getInstance().verify(loginData.getToken())) {
    		String storeUnit = RestSecurity.getInstance().getStoreUnit(loginData.getToken());
    		log.info("===storeUnit: " + storeUnit);
    		CallerUtilsStJohn.JaasLogin(storeUnit, RefCodeNames.APP_SOURCE_CD.AUDITOR);
    		return RestSecurity.getInstance().getUser(loginData.getToken());
    	} else {
    		log.info("\n***************************** reLogin **************************************");
    		LoginData reLoginData = login(loginData);
    		if(!"OK".equals(reLoginData.getResult())) {
    			throw new AndroidRestServiceLoginException(reLoginData.getResult());
    		}
    		newLoginData.setToken(reLoginData.getToken());
    		return RestSecurity.getInstance().getUser(reLoginData.getToken());
    	}
    }
    
}
