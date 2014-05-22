package com.cleanwise.service.api.session;

import java.rmi.*;
import java.util.*;
import java.util.Date;

import javax.ejb.*;
import javax.ejb.Remote;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import com.cleanwise.service.api.framework.*;

import java.sql.*;
//import java.sql.Date;

public class MainDbBean extends ApplicationServicesAPI {

    private static final Logger log = Logger.getLogger(MainDbBean.class);

    public MainDbBean() {
    }

    /**
    *
    */
    public void ejbCreate() throws CreateException, RemoteException {}
    
    public String getUserStoreDatasource(LoginInfoView pLoginInfo, boolean pCheckPwd) throws RemoteException, InvalidLoginException {
        AllUserData userD = login(pLoginInfo, pCheckPwd);
        log.info("getUserStoreDatasource(): userD = " + userD);
        int defaultStoreId = userD.getDefaultStoreId();
        log.info("getUserStoreDatasource(): defaultStoreId = " + defaultStoreId);
        Integer intObj = new Integer(defaultStoreId);
        log.info("getUserStoreDatasource(): intObj = " + intObj);
        //if (userD.getDefaultStoreId() != null) { //original code
        if (intObj != null) {
        	log.info("getUserStoreDatasource(): intObj != null");
            AllStoreData storeD = getStoreByStoreId(userD.getDefaultStoreId());
            log.info("getUserStoreDatasource(): storeD1 = " + storeD);
            if (storeD != null && storeD.getDatasource() != null && !"".equals(storeD.getDatasource())) {
                return storeD.getDatasource();
            }
        } else {
            List<UserStoreData> userStoreList = getStoreList(userD.getAllUserId());
            if (userStoreList.size() > 0) {
                UserStoreData userStoreD = userStoreList.get(0);
                AllStoreData storeD = getStoreByAllStoreId(userStoreD.getAllStoreId());
                log.info("getUserStoreDatasource(): storeD2 = " + storeD);
                if (storeD != null && storeD.getDatasource() != null && !"".equals(storeD.getDatasource())) {
                	Date currentDate = new Date();
                    //userStoreD.setLastLoginDate(new Date());
                    userStoreD.setLastLoginDate(currentDate);
                    updateLastLoginDate(userStoreD);
                    return storeD.getDatasource();
                }
            }
        }
        throw new InvalidLoginException("Datasource for the store not found");
    }

    public List<String> getStoreDatasourceByDomain(String pDomain) throws RemoteException, InvalidLoginException {
        List<String> storeDatasourceList = new ArrayList<String>();
        List<AllStoreData> storeList = getStoreList(pDomain);
        if (storeList != null) {
            for (AllStoreData store : storeList) {
                storeDatasourceList.add(store.getDatasource());
            }
        }
        return storeDatasourceList;
    }

    public boolean isAliveUnit(String pUnit) throws RemoteException, InvalidLoginException {
    	
        log.info("isAliveUnit(): pUnit = " + pUnit);
    	Connection con = null;
        try {            
        	
          con = getConnection(pUnit);
          Statement stmn = con.createStatement();
          String sql = "select sysdate as system_datetime from dual";
          ResultSet rs = stmn.executeQuery(sql);
          rs.next();
          Date ret_sysdate = rs.getDate(1);
          log.info("==== sysDateTime: " + ret_sysdate);
          rs.close();
          stmn.close();
        } catch (Exception ex) {
            log.info("==== isAliveUnit. Exception");
            return false;
        } finally {
		    closeConnection(con);
        }
        return true;
    }

    public boolean isAliveUnitMainDb(String pUnit) throws RemoteException, InvalidLoginException {
    	
        Connection con = null;
        try {
        	
          con = getMainDbConnection();
          Statement stmn = con.createStatement();
          String sql = "select sysdate as system_datetime from dual";
          ResultSet rs = stmn.executeQuery(sql);
          rs.next();
          Date ret_sysdate = rs.getDate(1);
          log.info("==== sysDateTime: " + ret_sysdate);
          rs.close();
          stmn.close();
        } catch (Exception ex) {
            log.info("==== isAliveUnitMainDb. Exception");
            return false;
        } finally {
		    closeConnection(con);
        }
        return true;
    }
    
    private AllUserData login(LoginInfoView pLoginInfo, boolean pCheckPwd) throws RemoteException, InvalidLoginException {
    	log.info("login()=> " + pLoginInfo.getUserName() + ", " + pLoginInfo.getPassword());
    	
        Connection conn = null;
        DBCriteria dbCrit;
        AllUserData lu = null;
        try {
                conn = getMainDbConnection();
                dbCrit = new DBCriteria();
                dbCrit.addEqualTo(AllUserDataAccess.USER_NAME, pLoginInfo.getUserName());
                AllUserDataVector sqlRes = AllUserDataAccess.select(conn, dbCrit);
                if (sqlRes.size() > 1) {
                    log.error("login()=> Multiple users found: '" + pLoginInfo.getUserName() + "'");
                    //throw new Exception("Multiple users found: '" + pLoginInfo.getUserName() + "'");
                }
                if (sqlRes.size() == 0) {
                    throw new Exception("login error: Username not found.");
                }
                if (sqlRes.size() == 1) {
                    lu = (AllUserData) sqlRes.get(0);
                }
                
                if (pCheckPwd) {

                    if (lu.getPassword() == null || lu.getPassword().length() == 0) {
                        return lu;
                    }

                    String passwordHash = PasswordUtil.getHash(pLoginInfo.getUserName(), pLoginInfo.getPassword());
                    if (!passwordHash.equals(lu.getPassword())) {
                        log.info("login()=> password " + passwordHash);
                        throw new InvalidLoginException("login error: Password does not match.");
                    }
                }
        } catch (InvalidLoginException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return lu;
    }

    public AllStoreData getStoreByStoreId(Integer pStoreId) throws RemoteException, InvalidLoginException {
    	    
    	    log.info("getStore(): pStoreId = " + pStoreId);
    	    int iStoreId = pStoreId.intValue();
    	    
    	    Connection conn = null;
            DBCriteria dbCrit;
            AllStoreDataVector asdv = null;    
            AllStoreData asdbdsi = null;    
        try {
                    conn = getMainDbConnection();
                    dbCrit = new DBCriteria();
                    dbCrit.addEqualTo(AllStoreDataAccess.STORE_ID, pStoreId);
                    asdv = AllStoreDataAccess.select(conn, dbCrit);
                    asdbdsi = (AllStoreData) asdv.get(0);                  
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        log.info("getStore(): asdbdsi  = " + asdbdsi);
        return asdbdsi;
    }

    public AllStoreData getStoreByAllStoreId(Integer pAllStoreId) throws RemoteException, InvalidLoginException {
	    
	    log.info("getStore(): pAllStoreId = " + pAllStoreId);
	    int iAllStoreId = pAllStoreId.intValue();
	    
	    Connection conn = null;
        DBCriteria dbCrit;
        AllStoreData asdbasi = null;            
    try {
                conn = getMainDbConnection();
                asdbasi = AllStoreDataAccess.select(conn, iAllStoreId);                    
    } catch (Exception e) {
        throw new RemoteException(e.getMessage());
    } finally {
	    closeConnection(conn);
    }
    log.info("getStore(): asdbasi = " + asdbasi);
    return asdbasi;
    }
    
    private List<AllStoreData> getStoreList(String pDomain) throws RemoteException, InvalidLoginException {
    	//produce the results in Ascending Order based off DATASOURCE; 
    	//Datasources are sorted also in Ascending Order,
    	//when the Default Store is selected on the Main page of the Admin Portal
        Connection conn = null;
        DBCriteria dbCrit;
        AllStoreDataVector asdv = null;
        try {
                    conn = getMainDbConnection();
                    dbCrit = new DBCriteria();
                    dbCrit.addEqualTo(AllStoreDataAccess.DOMAIN, pDomain);
                    //dbCrit.addOrderBy(AllStoreDataAccess.STORE_NAME);
                    dbCrit.addOrderBy(AllStoreDataAccess.DATASOURCE);
                    asdv = AllStoreDataAccess.select(conn, dbCrit);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return asdv;
    }


    private List<UserStoreData> getStoreList(Integer pAllUserId) throws RemoteException, InvalidLoginException {
    	int iAllUserId = pAllUserId.intValue();
        Connection conn = null;
        DBCriteria dbCrit;
        UserStoreDataVector usdv = null;
        try {
                    // Additional clause should be added here; it somehow includes LAST_LOGIN_DATE
        	        conn = getMainDbConnection();
                    dbCrit = new DBCriteria();
                    dbCrit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, iAllUserId);
                    usdv = UserStoreDataAccess.select(conn, dbCrit);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return usdv;
    }	

    public AllStoreDataVector getAllStoreListByUserName(String pUserName) throws RemoteException, InvalidLoginException {
    	log.info("getAllStoreListByUserName.start");
    	log.info("getAllStoreListByUserName(): pUserName = " + pUserName);
        Connection conn = null;
        DBCriteria dbCrit;
        AllStoreDataVector asdv = null;
        String pIdName = "ALL_STORE_ID";
        IdVector allUserIdVector = null;
        IdVector allStoreIdVector = null;
        try {
                    // Additional clause can be added here; it somehow may include LAST_LOGIN_DATE ???
        	        conn = getMainDbConnection();
                    dbCrit = new DBCriteria();
                    dbCrit.addEqualTo(AllUserDataAccess.USER_NAME, pUserName);                                        
                    allUserIdVector = AllUserDataAccess.selectIdOnly(conn, dbCrit);                    
                    if (allUserIdVector != null && allUserIdVector.size() != 0) {
                    	Integer allUserId = (Integer) allUserIdVector.get(0);
                    	int intAllUserId = allUserId.intValue();
                    	log.info("getAllStoreListByUserName(): intAllUserId = " + intAllUserId);
                    	//find vector of ALL_STORE_IDs in ESW_USER_STORE table of Main DB
                        dbCrit = new DBCriteria();
                        dbCrit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, intAllUserId);
                    	allStoreIdVector = UserStoreDataAccess.selectIdOnly(conn, pIdName, dbCrit);
                    	if (allStoreIdVector != null && allStoreIdVector.size() != 0) {
                    	   log.info("getAllStoreListByUserName(): allStoreIdVector = " + allStoreIdVector);
                    	   //find all Stores, configured for the User
                    	   asdv = AllStoreDataAccess.select(conn, allStoreIdVector);
                    	}
                    }
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        log.info("getAllStoreListByUserName.end");
        return asdv;
    }	

    //finds Stores in Multiple DB Schemas on the basis of User Name and Password
    public AllStoreDataVector getAllStoreListByUserNameAndPassword(String pUserName, String pPassword) throws RemoteException, InvalidLoginException {
    	log.info("getAllStoreListByUserNameAndPassword.start");
    	log.info("pUserName = " + pUserName);
    	log.info("pPassword = " + pPassword);
        Connection conn = null;
        DBCriteria dbCrit;
        AllStoreDataVector asDV = null;
        
        try {
        	        conn = getMainDbConnection();
        	        dbCrit = new DBCriteria();
        	        /***
        	        String sql = 
        	        	" select * from esw_all_store " +
        	        	" where all_store_id in " +
        	        	" (select eus.all_store_id " +
        	        	" from esw_all_user eau, esw_user_store eus " +
        	        	" where eau.user_name = " + pUserName  + 
        	        	" and eau.password = " + pPassword +  
        	        	" and eau.all_user_id = eus.all_user_id) " +
        	        	" order by datasource";
        	        ***/
        	        String sqlCondition = " all_store_id in " +
        	        	" (select eus.all_store_id " +
        	        	" from esw_all_user eau, esw_user_store eus " +
        	        	" where eau.user_name = '"+pUserName+"' "  + 
        	        	" and eau.password = '"+pPassword+"' " +  
        	        	" and eau.all_user_id = eus.all_user_id) " +
        	        	" order by datasource";
        	        dbCrit.addCondition(sqlCondition);
        	        log.info("sqlCondition : " + sqlCondition);
        	        
        	        asDV = AllStoreDataAccess.select(conn, dbCrit);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        log.info("getAllStoreListByUserNameAndPassword.end");
        
        return asDV;        

    }	
    
    //finds User Id in Main DB on the basis of User Name, Password
    public AllUserData getAllUserDataByUserNamePasswordAndStoreId(String pUserName, String pPassword, int pStoreId)  throws RemoteException, InvalidLoginException {

    	log.info("getAllUserDataByUserNamePasswordAndStoreId.Begin");
    	
    	log.info("pUserName = " + pUserName);
    	log.info("pPassword = " + pPassword);
    	log.info("pStoreId = " + pStoreId);
    	
        Connection conn = null;
        DBCriteria dbCrit;
        AllUserDataVector auDV = null;
        AllUserData auD = null;
        
        try {
                    // Additional clause can be added here; it somehow may include LAST_LOGIN_DATE ???
        	        conn = getMainDbConnection();
        	        dbCrit = new DBCriteria();
        	        /***
        	        String sql = 
        	        	select * from esw_all_user
                          where user_name = pUserName 
                            and password = pPassword 
                            and all_user_id in
                            (select eus.all_user_id from esw_user_store eus, esw_all_store eas
                               where eus.all_store_id = eas.all_store_id
                                 and eas.store_id = pStoreId
                            )
        	        ***/
        	        dbCrit.addEqualTo(AllUserDataAccess.USER_NAME, pUserName);
        	        dbCrit.addEqualTo(AllUserDataAccess.PASSWORD, pPassword);
        	        String sqlCondition = " all_user_id in " +
        	                " (select eus.all_user_id from esw_user_store eus, esw_all_store eas " +
                            " where eus.all_store_id = eas.all_store_id " +
                            " and eas.store_id = '"+pStoreId+"' ) ";
        	        dbCrit.addCondition(sqlCondition);
        	        log.info("sqlCondition : " + sqlCondition);
        	        
        	        auDV = AllUserDataAccess.select(conn, dbCrit);
        	        
        	        if (auDV != null && auDV.size() != 0) {
        	        	auD = (AllUserData) auDV.get(0);
        	        }
        	        	
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        log.info("getAllUserDataByUserNamePasswordAndStoreId.End");
        
        return auD;        

    }	
    
    
    public AllStoreDataVector getAllStoreListByUserId(int pUserId) throws RemoteException, InvalidLoginException {
    	log.info("getAllStoreListByUserId.start");
    	log.info("getAllStoreListByUserId(): pUserId = " + pUserId);
        Connection conn = null;
        DBCriteria dbCrit;
        AllStoreDataVector asdv = null;
        String pIdName = "ALL_STORE_ID";
        IdVector allUserIdVector = null;
        IdVector allStoreIdVector = null;
        try {
                    // Additional clause can be added here; it can somehow include LAST_LOGIN_DATE ???
        	        conn = getMainDbConnection();
                    dbCrit = new DBCriteria();
                    dbCrit.addEqualTo(AllUserDataAccess.USER_ID, pUserId);                                        
                    allUserIdVector = AllUserDataAccess.selectIdOnly(conn, dbCrit);                    
                    if (allUserIdVector != null && allUserIdVector.size() != 0) {
                    	Integer allUserId = (Integer) allUserIdVector.get(0);
                    	int intAllUserId = allUserId.intValue();
                    	log.info("getAllStoreListByUserId(): intAllUserId = " + intAllUserId);
                    	//find vector of ALL_STORE_IDs in the ESW_USER_STORE table of Main DB
                        dbCrit = new DBCriteria();
                        dbCrit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, intAllUserId);                        
                    	allStoreIdVector = UserStoreDataAccess.selectIdOnly(conn, pIdName, dbCrit);
                    	if (allStoreIdVector != null && allStoreIdVector.size() != 0) {
                    	   log.info("getAllStoreListByUserId(): allStoreIdVector = " + allStoreIdVector);
                    	   dbCrit = new DBCriteria();
                    	   dbCrit.addOneOf(AllStoreDataAccess.ALL_STORE_ID, allStoreIdVector);
                    	   dbCrit.addOrderBy(AllStoreDataAccess.DATASOURCE);
                    	   //find all Stores, configured for the User
                    	   asdv = AllStoreDataAccess.select(conn, dbCrit);
                    	}
                    }
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        log.info("getAllStoreListByUserId.end");
        return asdv;
    }	

    
    private void updateLastLoginDate(UserStoreData pUserStoreData) throws RemoteException, InvalidLoginException {
        Connection conn = null;
        DBCriteria dbCrit;
        try {
        	        conn = getMainDbConnection();	
        	        UserStoreDataAccess.update(conn, pUserStoreData);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
    }

    public void updateAllUserAndStore(AllUserData pAllUserData, List<AllStoreData> pAllStoreList, String pUser) throws RemoteException, InvalidLoginException{
        Integer allUserId = updateAllUser(pAllUserData, pUser);
        log.info("updateAllUserAndStore(): allUserId = " + allUserId);
        List<Integer> allStoreIdList = updateAllStores(pAllStoreList, pUser);
        updateUserStores(allUserId, allStoreIdList, pUser);
    }

    public Integer updateAllUser(AllUserData pAllUserData, String pUser) throws RemoteException, InvalidLoginException {
        Connection conn = null;
        DBCriteria dbCrit;
        AllUserDataVector oldUserDataVector = null;
        AllUserData oldUserData = null;
    	try {
            conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(AllUserDataAccess.USER_ID, pAllUserData.getUserId());
            
            oldUserDataVector = AllUserDataAccess.select(conn, dbCrit);                    
            if (!oldUserDataVector.isEmpty()) {
            	oldUserData = (AllUserData) oldUserDataVector.get(0);
            }
    		boolean change = false;
            if (oldUserData != null) {
                oldUserData.setUserId(pAllUserData.getUserId());
                if (pAllUserData.getUserName() != null && !pAllUserData.getUserName().equals(oldUserData.getUserName())) {
                    oldUserData.setUserName(pAllUserData.getUserName());
                    change = true;
                }
                if (pAllUserData.getFirstName() != null && !pAllUserData.getFirstName().equals(oldUserData.getFirstName())) {
                    oldUserData.setFirstName(pAllUserData.getFirstName());
                    change = true;
                }
                if (pAllUserData.getLastName() != null && !pAllUserData.getLastName().equals(oldUserData.getLastName())) {
                    oldUserData.setLastName(pAllUserData.getLastName());
                    change = true;
                }
                if (pAllUserData.getPassword() != null && !pAllUserData.getPassword().equals(oldUserData.getPassword())) {
                    oldUserData.setPassword(pAllUserData.getPassword());
                    change = true;
                }
                if (pAllUserData.getUserStatusCd() != null && !pAllUserData.getUserStatusCd().equals(oldUserData.getUserStatusCd())) {
                    oldUserData.setUserStatusCd(pAllUserData.getUserStatusCd());
                    change = true;
                }
                if (pAllUserData.getUserTypeCd() != null && !pAllUserData.getUserTypeCd().equals(oldUserData.getUserTypeCd())) {
                    oldUserData.setUserTypeCd(pAllUserData.getUserTypeCd());
                    change = true;
                }
                if (pAllUserData.getEffDate() != null && !pAllUserData.getEffDate().equals(oldUserData.getEffDate())) {
                    oldUserData.setEffDate(pAllUserData.getEffDate());
                    change = true;
                }
                if (pAllUserData.getExpDate() != null && !pAllUserData.getExpDate().equals(oldUserData.getExpDate())) {
                    oldUserData.setExpDate(pAllUserData.getExpDate());
                    change = true;
                }
                Integer intDefaultStoreId = new Integer(pAllUserData.getDefaultStoreId());
                Integer intOldDefaultStoreId = new Integer(oldUserData.getDefaultStoreId());
                if (intDefaultStoreId != null && !intDefaultStoreId.equals(intOldDefaultStoreId)) {
                    oldUserData.setDefaultStoreId(pAllUserData.getDefaultStoreId());
                    change = true;
                }
                if (change) {
                    log.info("======updateAllUser(): change");
                    return saveAllUserData(conn, oldUserData, pUser); 
                    
                }
            } else {
                log.info("======updateAllUser(): new");
                return saveAllUserData(conn, pAllUserData, pUser);
            }
            Integer intObj = new Integer(oldUserData.getAllUserId());
            return intObj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
    }

    private List<Integer> updateAllStores(List<AllStoreData> pAllStoreList, String pUser) throws RemoteException, InvalidLoginException {
        List<Integer> allStoreIds = new ArrayList<Integer>();
        for (AllStoreData allStoreData :  pAllStoreList) {
            allStoreIds.add(updateAllStore(allStoreData, pUser));
        }
        return allStoreIds;
    }

    public Integer updateAllStore(AllStoreData pAllStoreData, String pUser) throws RemoteException, InvalidLoginException {
    	Connection conn = null;
        DBCriteria dbCrit;
        AllStoreDataVector oldStoreDataVector = null;
        AllStoreData oldStoreData = null;
    	try {
            conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(AllStoreDataAccess.STORE_ID, pAllStoreData.getStoreId());
            oldStoreDataVector = AllStoreDataAccess.select(conn, dbCrit);                    
            if (!oldStoreDataVector.isEmpty()) {
            	oldStoreData = (AllStoreData) oldStoreDataVector.get(0);
            }
            
            
        	boolean change = false;
            if (oldStoreData != null) {
                oldStoreData.setStoreId(pAllStoreData.getStoreId());
                if (pAllStoreData.getStoreName() != null && !pAllStoreData.getStoreName().equals(oldStoreData.getStoreName())) {
                    oldStoreData.setStoreName(pAllStoreData.getStoreName());
                    change = true;
                }
                if (pAllStoreData.getDomain() != null && !pAllStoreData.getDomain().equals(oldStoreData.getDomain())) {
                    oldStoreData.setDomain(pAllStoreData.getDomain());
                    change = true;
                }
                String datasource = getCurrentUnit();
                if (datasource != null && !datasource.equals(oldStoreData.getDomain())) {
                    oldStoreData.setDatasource(datasource);
                    change = true;
                }
                if (change) {
                	log.info("updateAllStore(): updating oldStoreData...");
                	return saveAllStoreData(conn, oldStoreData, pUser);
                } else {
                	Integer intObj = new Integer(oldStoreData.getAllStoreId());
                    return intObj;
                }
            } else {
                String datasource = getCurrentUnit();
                pAllStoreData.setDatasource(datasource);
                
                log.info("updateAllStore(): inserting pAllStoreData...");
                return saveAllStoreData(conn, pAllStoreData, pUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
    }

    public void updateUserStores(Integer pAllUserId, List<Integer> pAllStoreIds, String pUser) throws RemoteException, InvalidLoginException {
    	log.info("updateUserStores(): pAllUserId =  " + pAllUserId);
    	log.info("updateUserStores(): pAllStoreIds =  " + pAllStoreIds);
    	List<UserStoreData> oldUserStoreList = getStoreList(pAllUserId);
    	log.info("updateUserStores(): oldUserStoreList =  " + oldUserStoreList);
        for (UserStoreData userStoreData : oldUserStoreList) {
            boolean found = false;
            for (Integer allStoreId : pAllStoreIds) {
                if(userStoreData.getAllStoreId() == allStoreId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
            	log.info("updateUserStores(): delete from esw_user_store table");
                deleteUserStore(userStoreData);
            }
        }
        for (Integer allStoreId : pAllStoreIds) {
            UserStoreData userStoreData = getUserStore(pAllUserId, allStoreId);
            if (userStoreData != null) {
                saveUserStoreData(userStoreData, pUser);
            } else {
                userStoreData = new UserStoreData();
                userStoreData.setAllUserId(pAllUserId);
                userStoreData.setAllStoreId(allStoreId);
                saveUserStoreData(userStoreData, pUser);
            }
        }

    }

    private void deleteUserStore(UserStoreData pUserStoreData) throws RemoteException, InvalidLoginException {
    	Connection conn = null;
    	try {
    		conn = getMainDbConnection();
    		UserStoreDataAccess.remove(conn, pUserStoreData.getUserStoreId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }

    }
    
    public void deleteUserStoreByAllStoreId(int pAllStoreId) throws RemoteException, InvalidLoginException {
    	Connection conn = null;
    	DBCriteria dbCrit;
    	try {
    		conn = getMainDbConnection();
    		dbCrit = new DBCriteria();
    		dbCrit.addEqualTo(UserStoreDataAccess.ALL_STORE_ID, pAllStoreId);
    		UserStoreDataAccess.remove(conn, dbCrit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }

    }

    public void deleteAllStoreByAllStoreId(int pAllStoreId) throws RemoteException, InvalidLoginException {
    	Connection conn = null;
    	try {
    		conn = getMainDbConnection();
    		AllStoreDataAccess.remove(conn, pAllStoreId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }

    }
    
    private UserStoreData getUserStore(Integer pAllUserId, Integer pAllStoreId) throws RemoteException, InvalidLoginException {
    	Connection conn = null;
        DBCriteria dbCrit;
        UserStoreDataVector userStoreDataVector = null;
        UserStoreData userStoreData = null;
    	try {
    		conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            int iAllUserId = pAllUserId.intValue();
            int iAllStoreId = pAllStoreId.intValue();
            dbCrit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, iAllUserId);
            dbCrit.addEqualTo(UserStoreDataAccess.ALL_STORE_ID, iAllStoreId);
            userStoreDataVector = UserStoreDataAccess.select(conn, dbCrit);                    
            if (!userStoreDataVector.isEmpty()) {
            	userStoreData = (UserStoreData) userStoreDataVector.get(0);
            	return userStoreData;
            } else {
            	return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
    }

    public void saveUserStoreData(UserStoreData pUserStoreData, String pUser) throws RemoteException, InvalidLoginException {
    	Connection conn = null;
    	try {
    		conn = getMainDbConnection();
    		Integer pk = new Integer(pUserStoreData.getUserStoreId());
    		log.info("saveUserStoreData(): pk = " + pk);
            if (pk == null || (pk.equals(new Integer(0)))) {
                pUserStoreData.setAddBy(pUser);
                pUserStoreData.setModBy(pUser);
                log.info("saveUserStoreData(): pUserStoreData = " + pUserStoreData);
                UserStoreDataAccess.insert(conn, pUserStoreData);
            } else {
                pUserStoreData.setModBy(pUser);
                UserStoreDataAccess.update(conn, pUserStoreData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

    }
    
    public Integer saveAllUserData(Connection conn, AllUserData pAllUserData, String pUser) throws RemoteException, InvalidLoginException, SQLException {
        log.info("pUser = " + pUser);
    	log.info("saveAllUserData(): pAllUserData = " + pAllUserData);
    	Integer pk = new Integer(pAllUserData.getAllUserId());
    	log.info("saveAllUserData(): pk before saving in the DB = " + pk);
    	AllUserData allUserData = null;
    	if (pk == null || (pk.equals(new Integer(0)))) {
            pAllUserData.setAddBy(pUser);
            pAllUserData.setModBy(pUser);
                        
            //insert record in the DB table
            log.info("AllUserDataAccess.insert()");
            allUserData = AllUserDataAccess.insert(conn, pAllUserData);
            pk = new Integer(allUserData.getAllUserId());
        } else {
            pAllUserData.setModBy(pUser);
            
            //update record in the DB table
            log.info("AllUserDataAccess.update()");
            AllUserDataAccess.update(conn, pAllUserData);
        }
    	log.info("saveAllUserData(): pk after saving in the DB = " + pk);
        return pk;
    }
    
    public Integer saveAllStoreData(Connection conn, AllStoreData pAllStoreData, String pUser) throws RemoteException, InvalidLoginException, SQLException {
    	Integer pk = new Integer(pAllStoreData.getAllStoreId());
    	log.info("saveAllStoreData(): pk before saving in the DB = " + pk);
    	AllStoreData allStoreData = null;
    	if (pk == null || (pk.equals(new Integer(0)))) {
            pAllStoreData.setAddBy(pUser);
            pAllStoreData.setModBy(pUser);            
            
            //insert record in the DB table
            allStoreData = AllStoreDataAccess.insert(conn, pAllStoreData);
            pk = new Integer(allStoreData.getAllStoreId());
        } else {
            pAllStoreData.setModBy(pUser);
            
            //update record in the DB table
            AllStoreDataAccess.update(conn, pAllStoreData);
        }
    	log.info("saveAllStoreData(): pk after saving in the DB = " + pk);
        return pk;
    }
    
    public AllUserData getAllUserDataByUserId(int userId) throws RemoteException, InvalidLoginException, SQLException {
    	Connection conn = null;
    	DBCriteria dbCrit;
    	AllUserDataVector allUserDataVector = null;
    	AllUserData allUserData = null;
    	try {
            conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(AllUserDataAccess.USER_ID, userId);
            
            allUserDataVector = AllUserDataAccess.select(conn, dbCrit);                    
            if (!allUserDataVector.isEmpty()) {
            	allUserData = (AllUserData) allUserDataVector.get(0);
            }
    	} catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return allUserData;
        
    }
    
    public AllStoreData getAllStoreDataByStoreId(int storeId) throws RemoteException, InvalidLoginException, SQLException {
    	Connection conn = null;
    	DBCriteria dbCrit;
    	AllStoreDataVector allStoreDataVector = null;
    	AllStoreData allStoreData = null;
    	try {
            conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(AllStoreDataAccess.STORE_ID, storeId);
            
            allStoreDataVector = AllStoreDataAccess.select(conn, dbCrit);                    
            if (!allStoreDataVector.isEmpty()) {
            	allStoreData = (AllStoreData) allStoreDataVector.get(0);
            }
    	} catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return allStoreData;
    }

    public AllStoreData getAllStoreDataByAllStoreId(int allStoreId) throws RemoteException, InvalidLoginException, SQLException {
    	Connection conn = null;
    	DBCriteria dbCrit;
    	AllStoreDataVector allStoreDataVector = null;
    	AllStoreData allStoreData = null;
    	try {
            conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            dbCrit.addEqualTo(AllStoreDataAccess.ALL_STORE_ID, allStoreId);
            
            allStoreDataVector = AllStoreDataAccess.select(conn, dbCrit);                    
            if (!allStoreDataVector.isEmpty()) {
            	allStoreData = (AllStoreData) allStoreDataVector.get(0);
            }
    	} catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return allStoreData;
    }
    
    public AllStoreDataVector getAllStoreDataVectorByAllStoreIds(IdVector allStoreIds) throws RemoteException, InvalidLoginException, SQLException {
    	Connection conn = null;
    	DBCriteria dbCrit;
    	AllStoreDataVector allStoreDataVector = null;
    	AllStoreData allStoreData = null;
    	try {
            conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            allStoreDataVector = AllStoreDataAccess.select(conn, allStoreIds);            
    	} catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return allStoreDataVector;
    }
    
    public AllStoreDataVector getAllStores() throws RemoteException, InvalidLoginException, SQLException {
    	Connection conn = null;
    	AllStoreDataVector v =  new AllStoreDataVector();
    	try {
            conn = getMainDbConnection();

            String sql = 
            "SELECT ALL_STORE_ID,STORE_ID,STORE_NAME,DOMAIN,DATASOURCE,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY FROM ESW_ALL_STORE ORDER BY DATASOURCE";
            
            Statement stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            AllStoreData x = null;
            while (rs.next()) {
                // build the object
                x = AllStoreData.createValue();
                
                x.setAllStoreId(rs.getInt(1));
                x.setStoreId(rs.getInt(2));
                x.setStoreName(rs.getString(3));
                x.setDomain(rs.getString(4));
                x.setDatasource(rs.getString(5));
                x.setAddDate(rs.getTimestamp(6));
                x.setAddBy(rs.getString(7));
                x.setModDate(rs.getTimestamp(8));
                x.setModBy(rs.getString(9));

                v.add(x);
            }

            rs.close();
            stmt.close();
                   
    	} catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        
        return v;
    }  
    
    public IdVector getStoreIdVectorByAllStoreIds(IdVector allStoreIds) throws RemoteException, InvalidLoginException, SQLException {
    	log.info("getStoreIdVectorByAllStoreIds.Begin");
    	Connection conn = null;
    	DBCriteria dbCrit;
    	AllStoreDataVector allStoreDataVector = null;
    	AllStoreData allStoreData = null;
    	IdVector storeIds = new IdVector();
    	try {
            conn = getMainDbConnection();
            dbCrit = new DBCriteria();
            allStoreDataVector = AllStoreDataAccess.select(conn, allStoreIds); 
            log.info("allStoreDataVector.size() = " + allStoreDataVector.size());
            log.info("allStoreDataVector = " + allStoreDataVector);
            for (int i = 0; i < allStoreDataVector.size(); i++) {
            	int storeId = ((AllStoreData) allStoreDataVector.get(i)).getStoreId();
            	log.info("i = " + i + " storeId = " + storeId);
            	storeIds.add(((AllStoreData) allStoreDataVector.get(i)).getStoreId());
            }
    	} catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
		    closeConnection(conn);
        }
        log.info("storeIds final = " + storeIds);
        log.info("getStoreIdVectorByAllStoreIds.End");
        
        return storeIds;
    }

    /**
     *Removes associations between the specifed User and the supplied list of Store Ids
     *@param pUserId     the User Id
     *@param storeIds    the Store Ids to "de"associate with the User
     */
    public void removeUserStoreAssociationsByUserIdAndStoreIds(int pUserId, IdVector storeIds)
    throws RemoteException, InvalidLoginException, SQLException {
        Connection conn = null;
        String pIdName = "STORE_ID";
        try{
            conn = getMainDbConnection();
            DBCriteria crit = new DBCriteria();
            
            // find allUserId based off pUserId
            AllUserData allUserData = getAllUserDataByUserId(pUserId);
            int allUserId = allUserData.getAllUserId();
            
            //find allStoreIds based off a vector of storeIds
            crit.addOneOf(AllStoreDataAccess.STORE_ID, storeIds);
            IdVector allStoreIdVector = AllStoreDataAccess.selectIdOnly(conn, pIdName, crit);
            
            //remove record(s) from ESW_USER_STORE DB table
            crit = new DBCriteria();
            crit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, allUserId);
            crit.addOneOf(UserStoreDataAccess.ALL_STORE_ID, allStoreIdVector);            
            UserStoreDataAccess.remove(conn,crit);
	    }catch (Exception e) {
            throw processException(e);
	    }finally {
	        closeConnection(conn);
	    }
    }
    
    /**
     *Removes associations between the specifed user and the supplied list of store ids
     *@param pUserId     the user id
     *@param storeIds    the store ids to "de"associate with the user
     */
    public void removeUserStoreAssociationsByUserIdAndAllStoreIds(int pUserId, IdVector allStoreIds)
    throws RemoteException, InvalidLoginException, SQLException {
        Connection conn = null;
        String pIdName = "STORE_ID";
        try{
            conn = getMainDbConnection();
            DBCriteria crit = new DBCriteria();
                        
            // find allUserId based off pUserId
            AllUserData allUserData = getAllUserDataByUserId(pUserId);
            int allUserId = allUserData.getAllUserId();
            
            //remove record(s) from ESW_USER_STORE DB table
            crit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, allUserId);
            crit.addOneOf(UserStoreDataAccess.ALL_STORE_ID, allStoreIds);            
            UserStoreDataAccess.remove(conn,crit);
            
	    }catch (Exception e) {
            throw processException(e);
	    }finally {
	        closeConnection(conn);
	    }
    } 
    
    public void removeAllUserData(int pAllUserId)
    throws RemoteException, InvalidLoginException, SQLException {
        Connection conn = null;
        try{
            conn = getMainDbConnection();                        
            AllUserDataAccess.remove(conn, pAllUserId);
	    }catch (Exception e) {
            throw processException(e);
	    }finally {
	        closeConnection(conn);
	    }
    } 
    
    /**
     *Add associations between the specifed user and the supplied list of store ids
     *@param pUserId         the user id
     *@param pAllStoreIds    the all store ids to associate with the user
     *
     */
    public void addUserStoreAssociations(int pUserId, IdVector allStoreIds, String userName, String userLocaleCd)
    throws RemoteException, InvalidLoginException, SQLException {
    	log.info("addUserStoreAssociations().Begin");
    	log.info("pUserId = " + pUserId + " allStoreIds = " + allStoreIds);
    	Connection conn = null;  
        try {
    	     conn = getMainDbConnection();
    	     DBCriteria crit = new DBCriteria();
    	     
    	     // find allUserId based off pUserId
             AllUserData allUserData = getAllUserDataByUserId(pUserId);
             int allUserId = allUserData.getAllUserId();
             
             crit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, allUserId);
             crit.addOneOf(UserStoreDataAccess.ALL_STORE_ID, allStoreIds);
             IdVector existingAllStoreIds = UserStoreDataAccess.selectIdOnly(conn, UserStoreDataAccess.ALL_STORE_ID, crit);
             IdVector newList = new IdVector();
             newList.addAll(allStoreIds);
             if (!existingAllStoreIds.isEmpty()){
    	         newList.removeAll(existingAllStoreIds);
             }
    
             Date now = new Date();
             Iterator it = newList.iterator();
             while(it.hasNext()){
                 Integer bid = (Integer) it.next();
                 if(bid != null && bid.intValue() != 0){
                     UserStoreData usd = UserStoreData.createValue();
                     usd.setAllUserId(allUserId);
                     usd.setAddBy(userName);
                     usd.setModBy(userName);
                     usd.setAllStoreId(bid.intValue());
                     usd.setLastLoginDate(now);
                     usd.setLocaleCd(userLocaleCd);
                     UserStoreDataAccess.insert(conn, usd);
                 }
             }
        } catch(Exception e) {
             throw processException(e);
        }finally {
             closeConnection(conn);
        }
        log.info("addUserStoreAssociations().End");
    }
    
    public void addUserStoreAssociationSingleStore(int pUserId, int pAllStoreId, String pUserName, String pUserLocaleCd)
    throws RemoteException, InvalidLoginException, SQLException {
    	log.info("addUserStoreAssociationSingleStore().Begin");
    	log.info("pUserId = " + pUserId + " pAllStoreId = " + pAllStoreId);
    	Connection conn = null;  
        try {
    	     conn = getMainDbConnection();
    	     DBCriteria crit = new DBCriteria();
    	     
    	     // find allUserId based off pUserId
             AllUserData allUserData = getAllUserDataByUserId(pUserId);
             int allUserId = allUserData.getAllUserId();
             
             crit.addEqualTo(UserStoreDataAccess.ALL_USER_ID, allUserId);
             crit.addEqualTo(UserStoreDataAccess.ALL_STORE_ID, pAllStoreId);
             UserStoreDataVector userStoreDataVector = UserStoreDataAccess.select(conn, crit);
             
             Date now = new Date();
             if (userStoreDataVector.isEmpty()){
            	 // add new user-store association to the ESW_USER_STORE table
            	 UserStoreData usd = UserStoreData.createValue();
                 usd.setAllUserId(allUserId);
                 usd.setAddBy(pUserName);
                 usd.setModBy(pUserName);
                 usd.setAllStoreId(pAllStoreId);
                 usd.setLastLoginDate(now);
                 usd.setLocaleCd(pUserLocaleCd);
                 UserStoreDataAccess.insert(conn, usd);            	 
             }
        } catch(Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        
       log.info("addUserStoreAssociationSingleStore().End");
    }
    
    public String getCurrentUnit() {
    	return super.getCurrentUnit();
    }

    public void createUserClone(UserData pUserData, String pAdmin,		       
		       int pUserStoreId)
    throws DataNotFoundException, RemoteException, SQLException {
    	Connection conn = null;
        try {
            conn = getMainDbConnection();
            
            //Prepare data for insertion in the ESW_ALL_USER table
            Date now = new Date();
            AllUserData newAllUserD = AllUserData.createValue();
            
            newAllUserD.setUserId(pUserData.getUserId());
            newAllUserD.setUserName(pUserData.getUserName());
            newAllUserD.setFirstName(pUserData.getFirstName());
            newAllUserD.setLastName(pUserData.getLastName());
            newAllUserD.setPassword(pUserData.getPassword());
            newAllUserD.setEffDate(now);
            newAllUserD.setExpDate(null);
		    newAllUserD.setUserStatusCd(pUserData.getUserStatusCd());
            newAllUserD.setUserTypeCd(pUserData.getUserTypeCd());                      
            newAllUserD.setAddBy(pAdmin);
            newAllUserD.setModBy(pAdmin);

            //INSERT data in the ESW_ALL_USER table
            newAllUserD = AllUserDataAccess.insert(conn, newAllUserD);
            
            //Prepare data for insertion in the ESW_USER_STORE table 
            //1. find all_user_id 
            AllUserData allUserData = getAllUserDataByUserId(pUserData.getUserId());
            //2. find all_store_id
            AllStoreData allStoreData = getAllStoreDataByStoreId(pUserStoreId);
            
            UserStoreData newUserStoreD = UserStoreData.createValue();
            
            newUserStoreD.setAllUserId(allUserData.getAllUserId());
            newUserStoreD.setAllStoreId(allStoreData.getAllStoreId());
            newUserStoreD.setLastLoginDate(now);
            newUserStoreD.setAddBy(pAdmin);
            newUserStoreD.setModBy(pAdmin);
            newUserStoreD.setLocaleCd(pUserData.getPrefLocaleCd());
            
            //INSERT data in the ESW_USER_STORE table
            newUserStoreD = UserStoreDataAccess.insert(conn, newUserStoreD);
            
        } catch(Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }        
        
    }
}
