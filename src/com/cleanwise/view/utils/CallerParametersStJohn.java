package com.cleanwise.view.utils;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.callback.SecurityAssociationHandler;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.MainDb;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.LoginInfoView;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.i18n.ClwI18nUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class CallerParametersStJohn {

    private static final Logger log = Logger.getLogger(CallerParametersStJohn.class);
    
    //public static String[] STORE_UNITS = new String[]{"java:/Store01DS","java:/Store02DS","java:/Store03DS","java:/Store04DS"};
    
    public CallerParametersStJohn() {     	
    }
    
    public static String[] getStoreUnits() {
      String multiStoreDatasources = System.getProperty("multi.store.datasources");  
      String[] STORE_UNITS = null;
      if (Utility.isSet(multiStoreDatasources)) {
    	int intMultiStoreDatasources = Integer.parseInt(multiStoreDatasources);
        STORE_UNITS = new String[intMultiStoreDatasources];    
 	    for (int i=0; i<intMultiStoreDatasources; i++) {
 		    //construct datasource name
 	    	int im = i + 1;
 	    	if (i<10) {
 	    	    STORE_UNITS[i] = Constants.DATASOURCE_NAME_PARTIAL + "0" + im + "DS";
 	    	} else {
 	    		STORE_UNITS[i] = Constants.DATASOURCE_NAME_PARTIAL + im + "DS";
 	    	}
 		    //log.info("STORE_UNITS = " + STORE_UNITS[i]);
 	    }
      }
      return STORE_UNITS;
    }    

    public static void initDefaultMultiDbAccess(HttpServletRequest request) {
        log.info("initDefaultMultiDbAccess.start");
        if ("yes".equals(System.getProperty("multi.store.db"))) {
            APIAccess factory = null;
            MainDb mainDbEjb = null;
            try {
                factory = APIAccess.getAPIAccess();
                mainDbEjb = factory.getMainDbAPI();
            } catch (Exception exc) {
                exc.printStackTrace();
                return;
            }
            boolean found = false;
            try {
                log.info("Read Main DB");
                String domain = SessionTool.getRequestedDomain(request);
                log.info("initDefaultMultiDbAccess(): domain1 = " + domain);
                if (domain == null || domain.equals("") || domain.trim().equals(Constants.LOCALHOST)){
                    domain = Constants.DEFAULT_DOMAIN;
                }
                log.info("initDefaultMultiDbAccess(): domain2 = " + domain);
                List<String> storeDatasourceList = mainDbEjb.getStoreDatasourceByDomain(domain);
                log.info("initDefaultMultiDbAccess(): storeDatasourceList.size() = " + storeDatasourceList.size());
                log.info("initDefaultMultiDbAccess(): storeDatasourceList = " + storeDatasourceList);
                for (String storeDatasource : storeDatasourceList) {
                    if (isAliveStoreUnit(mainDbEjb, storeDatasource)) {
                        JaasLogin(request, storeDatasource, false);
                        found = true;
                        return;
                    }
                }
            } catch (Exception exc) {
                log.info("Error Reading Main DB");
            } finally {
                if (!found) {
                	log.info("storeDatasource is NOT found for domain");
                    //for (String storeDatasource : STORE_UNITS) {
                	if (getStoreUnits() != null) {
                	   for (String storeDatasource : getStoreUnits()) {
                           if (isAliveStoreUnit(mainDbEjb, storeDatasource)) {
                               JaasLogin(request, storeDatasource, false);
                               return;
                           }
                       }
                	}
                }
            }

        }
    }

    public static LoginInfoView replaceUnitAndUserWithUser(LoginInfoView pUserInfo) {
        LoginInfoView userInfo = pUserInfo;
        if ("yes".equals(System.getProperty("multi.store.db"))) {
            if (pUserInfo != null &&
                pUserInfo.getUserName() != null &&
                !"".equals(pUserInfo.getUserName()) &&
                pUserInfo.getUserName().indexOf("/") > 0 &&
                pUserInfo.getUserName().indexOf("/") < pUserInfo.getUserName().length()) {
                userInfo.setUserName(pUserInfo.getUserName().substring(pUserInfo.getUserName().indexOf("/") + 1));
                log.info("====user : " + userInfo.getUserName());
            }
        }
        return userInfo;
    }

    public static ActionErrors initCallerParameters(HttpServletRequest request, LoginInfoView pUserInfo, String pAppSource) {
        log.info("initCallerParameters.start");
        ActionErrors ae = new ActionErrors();
        if (pUserInfo == null ||
            pUserInfo.getUserName() == null ||
            "".equals(pUserInfo.getUserName())){
            return ae;
        }
        String datasource = "unknown";
        String callerPars = "";
        String appSource = pAppSource != null?pAppSource:"";
        try {
            if ("yes".equals(System.getProperty("multi.store.db"))) {
                String datasourceUnit = null;
                if (pUserInfo.getUserName().indexOf("/") > 0) {
                    datasourceUnit = pUserInfo.getUserName().substring(0, pUserInfo.getUserName().indexOf("/"));
                    log.info("==== initCallerParameters(): datasourceUnit = " + datasourceUnit);
                    boolean found = false;
                    //for( String unit : STORE_UNITS) {
                    if (getStoreUnits() != null) {
                       for( String unit : getStoreUnits()) {
                           if (unit.equals(datasourceUnit)) {
                               found = true;
                               break;
                           }
                       }
                    }
                    if (!found) {
                        ae.add("error", new ActionMessage("error.systemError", "Store is wrong"));
                    }
                    HttpSession session = request.getSession();
                    MainDb mainDbEjb = null;
                    APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
                    mainDbEjb = factory.getMainDbAPI();
                    if (!isAliveStoreUnit(mainDbEjb, datasourceUnit)) {
                        ae.add("error", new ActionMessage("error.systemError", "Store is not alive"));
                    }
                } else {
                    datasourceUnit = initMultiDbAccess(request, pUserInfo);
                }
                log.info("initCallerParameters(): datasourceUnit = " + datasourceUnit);
                if (datasourceUnit != null) {
                    datasource = datasourceUnit;
                }
            }
            log.info("initCallerParameters(): datasource = " + datasource);
            callerPars = datasource + "," + appSource;
            log.info("initCallerParameters(): callerPars: " + callerPars);
            JaasLogin(request, callerPars, true);
        } catch (APIServiceAccessException ex) {
            ae.add("error", new ActionMessage("error.systemError", "No Ejb access"));
        } catch (Exception ex) {
            if("wrongNamePassword".equals(ex.getMessage())) {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.wrongNamePassword", null);
                ae.add("error", new ActionMessage("error.systemError", errorMess));
            } else {
                String errorMess = ClwI18nUtil.getMessage(request, "login.errors.userLogonError", null);
                ae.add("error", new ActionMessage("error.systemError", errorMess));
            }
        }
        return ae;
    }

    private static String initMultiDbAccess(HttpServletRequest request, LoginInfoView pUserInfo) throws Exception, APIServiceAccessException {
        log.info("initMultiDbAccess().start");
        if (pUserInfo.getUserName() != null && !"".equals(pUserInfo.getUserName())){
            HttpSession session = request.getSession();
            MainDb mainDbEjb = null;
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            mainDbEjb = factory.getMainDbAPI();
            try {
                log.info("initMultiDbAccess(): Read Main DB");
                String storeDatasource = mainDbEjb.getUserStoreDatasource(pUserInfo, true);
                log.info("initMultiDbAccess(): storeDatasource: " + storeDatasource);                
                return storeDatasource;
            } catch (Exception exc) {
                log.info("initMultiDbAccess(): Error Reading Main DB");
                //for (String storeDatasource : STORE_UNITS) {
                if (getStoreUnits() != null) {
                   for (String storeDatasource : getStoreUnits()) {
                     if (isAliveStoreUnit(mainDbEjb, storeDatasource)) {
                        log.info("initMultiDbAccess(): ===probe");
                        LoginContext loginContext = null;
                        try {
                            loginContext = JaasLogin(request, storeDatasource, false);
                            User userEjb = factory.getUserAPI();
                            UserData user;                           
                            user = userEjb.login(pUserInfo);
                            log.info("initMultiDbAccess(): ==== logOnUser");
                            if (Utility.isSet(user.getPassword())) {
                                log.info("initMultiDbAccess(): ==== Store is OK");
                                return storeDatasource;
                            }
                        } catch (Exception e) {
                            log.info("Store Error");
                        } finally {
                            log.info("initMultiDbAccess(): ===logout probe");
                            if (loginContext != null) {
                                loginContext.logout();
                            }
                        }
                     }
                   } 
                }
                throw new Exception("wrongNamePassword");
            }
        }
        log.info("initMultiDbAccess().finish");
        return null;
    }

    private static boolean isAliveStoreUnit(MainDb pMainDbEjb, String pStoreDatasource) {
        log.info("isAliveStoreUnit(): StoreUnit = " + pStoreDatasource);
        try {
            if (pMainDbEjb.isAliveUnit(pStoreDatasource)) {
                log.info("StoreUnit: " + pStoreDatasource + " is alive");
                return true;
            } else {
                log.info("StoreUnit: " + pStoreDatasource + " is not alive");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static LoginContext JaasLogin(HttpServletRequest request, String pCallerPars, boolean persistence) {
        log.info("JaasLogin.start");
        log.info("JaasLogin(): pCallerPars = " + pCallerPars);
        log.info("JaasLogin(): persistence = " + persistence);
        HttpSession session = request.getSession();
        LoginContext loginContext = null;
        try {
            SecurityAssociationHandler handler = new SecurityAssociationHandler(new SimplePrincipal(pCallerPars), null);
            loginContext = new LoginContext("client-login", handler);
            loginContext.login();
            if (persistence) {
                log.info("CallerParameters - persistence(true)");
                session.setAttribute(Constants.CALLER_PARAMETERS_CONTEXT, loginContext);
            }
        } catch (LoginException exc) {
            exc.printStackTrace();
        }
        //log.info("JaasLogin(): loginContext = " + loginContext);
        log.info("JaasLogin.end");
        return loginContext;
    }

    public static final int CURRENT_UNIT = 0;
    public static final int CURRENT_SOURCE = 1;

    public static String getCallerParameter(HttpServletRequest request, int par) {
        LoginContext loginContext = (LoginContext)request.getSession().getAttribute(Constants.CALLER_PARAMETERS_CONTEXT);
        if (loginContext != null) {
            String[] callerPars = null;
            Set<Principal> l = loginContext.getSubject().getPrincipals();
            for (Principal o : l) {
                SimplePrincipal sp = (SimplePrincipal) o;
                callerPars = Utility.parseStringToArray(sp.getName(), ",");
                if (callerPars != null && callerPars.length >= par) {
                    return callerPars[par];
                }
            }
        }
        return null;
    }

}
