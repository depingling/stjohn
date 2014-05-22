package com.cleanwise.service.api.util;

import javax.security.auth.login.*;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import org.apache.log4j.Logger;
import org.jboss.security.*;
import org.jboss.security.auth.callback.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class CallerUtilsStJohn {
    private static final Logger log = Logger.getLogger(CallerUtilsStJohn.class);
//    public static String[] HIBERNATE_UNITS = new String[]{"Store01","Store02"};

    public static void JaasLogin(String pDatasourceUnit, String source) throws Exception{
        log.info("JaasLogin.start");

        if ("yes".equals(System.getProperty("multi.store.db"))) {
        	MainDb mainDbEjb = null;
        	APIAccess factory = APIAccess.getAPIAccess();
        	mainDbEjb = factory.getMainDbAPI();
        	if (!mainDbEjb.isAliveUnit(pDatasourceUnit)) {
        		throw new Exception("Store is not alive");
        	}
        }
        LoginContext loginContext = null;
        try {
            SecurityAssociationHandler handler = new SecurityAssociationHandler(new SimplePrincipal(pDatasourceUnit+","+source), null);
            loginContext = new LoginContext("client-login", handler);
            loginContext.login();
        } catch (LoginException exc) {
            exc.printStackTrace();
        }
    }

    private static LoginContext JaasLogin(String pCallerPars) {
        log.info("JaasLogin.start");
        LoginContext loginContext = null;
        try {
            SecurityAssociationHandler handler = new SecurityAssociationHandler(new SimplePrincipal(pCallerPars), null);
            loginContext = new LoginContext("client-login", handler);
            loginContext.login();
        } catch (LoginException exc) {
            exc.printStackTrace();
        }
        return loginContext;
    }

    private static boolean isAliveStoreUnit(MainDb pMainDbEjb, String pStoreDatasource) {
        log.info("StoreUnit: " + pStoreDatasource);
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


    public static String initCallerParameters(LoginInfoView pUserInfo, String pAppSource) {
        log.info("initCallerParameters.start");
        if (pUserInfo == null ||
            pUserInfo.getUserName() == null ||
            "".equals(pUserInfo.getUserName())){
            return null;
        }
        String datasource = "unknown";
        String callerPars = "";
        String appSource = pAppSource != null?pAppSource:"";
        try {
            if ("yes".equals(System.getProperty("multi.store.db"))) {
                String datasourceUnit = null;
                if (pUserInfo.getUserName().indexOf("/") > 0) {
                    datasourceUnit = pUserInfo.getUserName().substring(0, pUserInfo.getUserName().indexOf("/"));
                    log.info("==== datasourceUnit: " + datasourceUnit);
                    boolean found = false;
                    if (getStoreUnits() != null) {
                        for( String unit : getStoreUnits()) {
//                    for( String unit : HIBERNATE_UNITS) {
                        	if (unit.equals(datasourceUnit)) {
                        		found = true;
                        		break;
                        	}
                    }
                        }
                    if (!found) {
                        log.info("Store is wrong");
                    }
                    APIAccess factory = APIAccess.getAPIAccess();
                    MainDb mainDbEjb = factory.getMainDbAPI();
                    if (!isAliveStoreUnit(mainDbEjb, datasourceUnit)) {
                        log.info("Store is not alive");
                    }
                } else {
                    datasourceUnit = initMultiDbAccess(pUserInfo);
                }
                log.info("datasourceUnit: " + datasourceUnit);
                if (datasourceUnit != null) {
                    datasource = datasourceUnit;
                }
            }
            log.info("datasource: " + datasource);
            callerPars = datasource + "," + appSource;
            log.info("callerPars: " + callerPars);
            JaasLogin(callerPars);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return datasource;
    }


    public static String initCallerParameters(LoginInfoView pUserInfo, String pDsUnit, String pAppSource) {

        log.info("initCallerParameters.start");

        if (pUserInfo == null ||pUserInfo.getUserName() == null ||"".equals(pUserInfo.getUserName())){
            return null;
        }

        String datasource = "unknown";
        String callerPars = "";
        String appSource = pAppSource != null?pAppSource:"";
        try {
            if ("yes".equals(System.getProperty("multi.store.db"))) {
                String datasourceUnit = null;
                if (pUserInfo.getUserName().indexOf("/") > 0) {
                    datasourceUnit = pDsUnit;
                    log.info("==== datasourceUnit: " + datasourceUnit);
                    boolean found = false;
                    if (getStoreUnits() != null) {
                        for( String unit : getStoreUnits()) {
                            if (unit.equals(datasourceUnit)) {
                                found = true;
                                break;
                            }
                        }
                     }
                    if (!found) {
                        log.info("Store is wrong");
                    }
                    APIAccess factory = APIAccess.getAPIAccess();
                    MainDb mainDbEjb = factory.getMainDbAPI();
                    if (!isAliveStoreUnit(mainDbEjb, datasourceUnit)) {
                        log.info("Store is not alive");
                    }
                } else {
                    datasourceUnit = initMultiDbAccess(pUserInfo);
                }
                log.info("datasourceUnit: " + datasourceUnit);
                if (datasourceUnit != null) {
                    datasource = datasourceUnit;
                }
            }
            log.info("datasource: " + datasource);
            callerPars = datasource + "," + appSource;
            log.info("callerPars: " + callerPars);
            JaasLogin(callerPars);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return datasource;
    }

    private static String initMultiDbAccess(LoginInfoView pUserInfo) throws Exception, APIServiceAccessException {
        log.info("initMultiDbAccess.start");
        if (pUserInfo.getUserName() != null && !"".equals(pUserInfo.getUserName())){
            APIAccess factory = APIAccess.getAPIAccess();
            MainDb mainDbEjb = factory.getMainDbAPI();
            String storeDatasource = null;
            try {
                log.info("Read Main DB");
                storeDatasource = mainDbEjb.getUserStoreDatasource(pUserInfo,true);
                log.info("storeDatasource: " + storeDatasource);
                return storeDatasource;
            } catch (Exception exc) {
                log.info("Error Reading Main DB");
                if (getStoreUnits() != null) {
                    for( String unit : getStoreUnits()) {
//                for (String storeDatasource : HIBERNATE_UNITS) {
                    	if (isAliveStoreUnit(mainDbEjb, storeDatasource)) {
                    		log.info("===probe");
                    		LoginContext loginContext = null;
                    		try {
                    			loginContext = JaasLogin(storeDatasource);
                    			User userEjb = factory.getUserAPI();
                    			UserData user = userEjb.login(pUserInfo);
                    			log.info("==== logOnUser");
                    			if (Utility.isSet(user.getPassword())) {
                    				log.info("==== Store is OK");
                    				return storeDatasource;
                    			}
                    		} catch (Exception e) {
                    			log.info("Store Error");
                    		} finally {
                    			log.info("===logout probe");
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
        return null;
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
    
    
}
