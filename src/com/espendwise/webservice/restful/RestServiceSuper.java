package com.espendwise.webservice.restful;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.value.LoginInfoView;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.InvoiceCustDetailDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.ItemSubstitutionDataVector;
import com.cleanwise.service.api.value.OrderItemActionDescDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.operations.ProcessBatchOrders;
import com.cleanwise.service.api.session.Group;
import com.cleanwise.service.api.session.StoreOrder;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.CallerUtilsStJohn;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.util.ICleanwiseUser;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.espendwise.ocean.common.webaccess.BasicResponseValue;
import com.espendwise.ocean.common.webaccess.LoginCredential;
import com.espendwise.ocean.common.webaccess.LoginData;
import com.espendwise.ocean.common.webaccess.ResponseError;
import com.espendwise.webservice.restful.security.RestSecurity;

import java.net.URI;
import javax.ws.rs.*;
import java.util.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/service")
public class RestServiceSuper {

    private static final Logger log = Logger.getLogger(RestServiceSuper.class);
    public static final String OK = "OK";
    public static final String NOT_OK = "NOT_OK";
    public static final String N_A = "N/A";
    
    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    @PUT
    @Path("/login")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public BasicResponseValue login(LoginData pLoginData) {

    	StringBuilder infoBuffer = new StringBuilder();
        infoBuffer.append("\n");
        infoBuffer.append("****************************** user login ****************************\n");
        infoBuffer.append("*************** userName: " + pLoginData.getUserName() + "************\n");
        infoBuffer.append("*************** password: " + pLoginData.getPassword() + "************\n");
        infoBuffer.append("**********************************************************************\n");
        log.info(infoBuffer.toString());
    	
        LoginInfoView userInfo = new LoginInfoView();
        userInfo.setUserName(pLoginData.getUserName());
        userInfo.setPassword(pLoginData.getPassword());

        Date curDate = new Date();
        UserData user;
        LoginCredential loginSummary = new LoginCredential();
        try {
            APIAccess factory = APIAccess.getAPIAccess();
            User userEjb = factory.getUserAPI();
            Group groupEjb = factory.getGroupAPI();

            String storeUnit = CallerUtilsStJohn.initCallerParameters(userInfo, RefCodeNames.APP_SOURCE_CD.MANTA);
            
            log.info("====== storeUnit: " + storeUnit);
            if ("yes".equals(System.getProperty("multi.store.db")) && "unknown".equals(storeUnit)) {
                log.info("login.errors.datasource.unknown");
                return new BasicResponseValue<LoginCredential>(loginSummary,
                                                               BasicResponseValue.STATUS.ERROR,
                                                               Utility.toList(new ResponseError(null,null,null,"login.errors.datasource.unknown",null)));
            }
            CallerUtilsStJohn.replaceUnitAndUserWithUser(userInfo);

            log.info("===== login.start");
                user = userEjb.login(userInfo, true, true);
            if (!Utility.isSet(user.getPassword())) {
                log.info("login.errors.no.password");
                return new BasicResponseValue<LoginCredential>(loginSummary,
                                                               BasicResponseValue.STATUS.ERROR,
                                                               Utility.toList(new ResponseError(null,null,null,"login.errors.no.password",null)));
            }

            String userCheckResult = checkUser(user, curDate);
            if (!OK.equals(userCheckResult)) {
                return new BasicResponseValue<LoginCredential>(loginSummary,
                                                               BasicResponseValue.STATUS.ERROR,
                                                               Utility.toList(new ResponseError(null,null,null,userCheckResult,null)));
            }             
                
            CleanwiseUser appUser = new CleanwiseUser();
            appUser.setUser(user);
            
            String securityKey = RestSecurity.getInstance().getSecurityKey(user.getUserName(), user.getPassword());
            RestSecurity.getInstance().register(securityKey, appUser, storeUnit);

            loginSummary.setUserName(pLoginData.getUserName());
            URI uri = uriInfo.getBaseUri();
            uri.getHost();
            loginSummary.setHostAddress(uri.getHost() + ":" + uri.getPort());
            loginSummary.setAccessToken(securityKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponseValue<LoginCredential>(loginSummary,
                                                           BasicResponseValue.STATUS.ERROR,
                                                           Utility.toList(new ResponseError(e.getMessage())));
        }
        
        infoBuffer = new StringBuilder();
        infoBuffer.append("\n");
        infoBuffer.append("**************************** user login finish ***********************\n");
        infoBuffer.append("*************** userName: " + loginSummary.getUserName() + "***************\n");
        infoBuffer.append("*************** host: " + loginSummary.getHostAddress() + "***************\n");
        infoBuffer.append("*************** token: " + loginSummary.getAccessToken() + "***************\n");
        infoBuffer.append("**********************************************************************\n");
        log.info(infoBuffer.toString());

        return new BasicResponseValue<LoginCredential>(loginSummary, BasicResponseValue.STATUS.OK, null);
    }

    @PUT
    @Path("/check_put")
    public void checkPut() {
        log.error("I'm alive PUT");
    }
    
    @GET
    @Path("/check_get")
    public void checkGet() {
        log.error("I'm alive GET");
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

    public ICleanwiseUser authorize(String accessToken, LoginData loginData) throws Exception {
        ICleanwiseUser authorizedUser = null;

    	if (RestSecurity.getInstance().verify(accessToken)) {
            String storeUnit = RestSecurity.getInstance().getStoreUnit(accessToken);
            log.info("===== storeUnit: " + storeUnit);
            CallerUtilsStJohn.JaasLogin(storeUnit, RefCodeNames.APP_SOURCE_CD.MANTA);

            authorizedUser = RestSecurity.getInstance().getUser(accessToken);
    	} else {
            log.info("\n***************************** Re-Login **************************************");
            BasicResponseValue reLoginResponse = login(loginData);
            if (BasicResponseValue.STATUS.OK == reLoginResponse.getStatus()) {
                accessToken = ((LoginCredential)reLoginResponse.getObject()).getAccessToken();
                authorizedUser = RestSecurity.getInstance().getUser(accessToken);
            }
    	}
        
        return authorizedUser;
    }
}
