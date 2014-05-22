package com.cleanwise.service.api.session;

import java.rmi.*;
import java.util.*;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.sql.Connection;
import java.sql.SQLException;


public interface MainDb extends javax.ejb.EJBObject {

    public String getUserStoreDatasource(LoginInfoView pLoginInfo, boolean pCheckPwd) throws RemoteException, InvalidLoginException;
 
    public List<String> getStoreDatasourceByDomain(String pDomain) throws RemoteException, InvalidLoginException;

    public boolean isAliveUnit(String pUnit) throws RemoteException, InvalidLoginException;
    
    public boolean isAliveUnitMainDb(String pUnit) throws RemoteException, InvalidLoginException;

    public void updateAllUserAndStore(AllUserData pAllUserData, List<AllStoreData> pAllStoreList, String pUser) throws RemoteException, InvalidLoginException;

    public Integer saveAllUserData(Connection conn, AllUserData pAllUserData, String pUser) throws RemoteException, InvalidLoginException, SQLException;

    public Integer saveAllStoreData(Connection conn, AllStoreData pAllStoreData, String pUser) throws RemoteException, InvalidLoginException, SQLException;

    public AllStoreDataVector getAllStoreListByUserName(String pUserName) throws RemoteException, InvalidLoginException;

    public AllStoreData getStoreByStoreId(Integer pStoreId) throws RemoteException, InvalidLoginException;
    
    public Integer updateAllStore(AllStoreData pAllStoreData, String pUser) throws RemoteException, InvalidLoginException;
    
    public AllUserData getAllUserDataByUserId(int pUserId) throws RemoteException, InvalidLoginException;
    
    public void saveUserStoreData(UserStoreData pUserStoreData, String pUser) throws RemoteException, InvalidLoginException;

    public void deleteUserStoreByAllStoreId(int pAllStoreId) throws RemoteException, InvalidLoginException;
    
    public void deleteAllStoreByAllStoreId(int pAllStoreId) throws RemoteException, InvalidLoginException;
    
    public AllStoreData getAllStoreDataByStoreId(int storeId) throws RemoteException, InvalidLoginException, SQLException;

    public AllStoreDataVector getAllStoreListByUserId(int pUserId) throws RemoteException, InvalidLoginException;

    public AllStoreDataVector getAllStores() throws RemoteException, InvalidLoginException, SQLException;
    
    public IdVector getStoreIdVectorByAllStoreIds(IdVector allStoreIds) throws RemoteException, InvalidLoginException, SQLException;
    
    public void removeUserStoreAssociationsByUserIdAndStoreIds(int pUserId, IdVector storeIds)
    throws RemoteException, InvalidLoginException, SQLException;
    
    public void removeUserStoreAssociationsByUserIdAndAllStoreIds(int pUserId, IdVector allStoreIds)
    throws RemoteException, InvalidLoginException, SQLException;
    
    public void addUserStoreAssociations(int pUserId, IdVector allStoreIds, String userName, String userLocaleCd)
    throws RemoteException, InvalidLoginException, SQLException;
    
    public AllStoreDataVector getAllStoreDataVectorByAllStoreIds(IdVector pAllStoreIds) throws RemoteException;
    
    public String getCurrentUnit();
    
    public Integer updateAllUser(AllUserData pAllUserData, String pUser) throws RemoteException, InvalidLoginException;

    public void updateUserStores(Integer pAllUserId, List<Integer> pAllStoreIds, String pUser) throws RemoteException, InvalidLoginException;

    public void addUserStoreAssociationSingleStore(int pUserId, int pAllStoreId, String pUserName, String pUserLocaleCd)
    throws RemoteException, InvalidLoginException, SQLException;
    
    public void createUserClone(UserData pUserData, String pAdmin,		       
		       int pUserStoreId)
    throws DataNotFoundException, RemoteException, SQLException;
    
    public AllStoreData getStoreByAllStoreId(Integer pAllStoreId) throws RemoteException, InvalidLoginException;
    
    public AllStoreData getAllStoreDataByAllStoreId(int allStoreId) throws RemoteException, InvalidLoginException, SQLException;
    
    public AllStoreDataVector getAllStoreListByUserNameAndPassword(String pUserName, String pPassword) throws RemoteException, InvalidLoginException;

    public AllUserData getAllUserDataByUserNamePasswordAndStoreId(String pUserName, String pPassword, int pStoreId)  throws RemoteException, InvalidLoginException;

    public void removeAllUserData(int pAllUserId)
    throws RemoteException, InvalidLoginException, SQLException;
}