
package com.cleanwise.service.api.session;


/**
 * Title:        DWDim
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       T Besser
 */

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.rmi.RemoteException;
import java.util.List;



public interface  DWOperation  extends javax.ejb.EJBObject
{

    public int getStoreDimId (String storeId)  throws RemoteException;
    public  DistributorDataVector getDistributorByCriteria(BusEntitySearchCriteria pCrit) throws RemoteException;

    public  ManufacturerDataVector getManufacturerByCriteria (BusEntitySearchCriteria pCrit) throws RemoteException;

    public  SiteViewVector getUserSites(
        int pStoreDimId, int pUserDimId, Integer pSiteDimId, String pNameTempl, int nameBeginsFl,
        String pCity, String pState, IdVector pAccountIdv,
        boolean showInactiveFl, int pResultLimit)
        throws  RemoteException;

    public  AccountUIViewVector getAccountsUIByCriteria( BusEntitySearchCriteria pCrit) throws  RemoteException;
    public  ItemViewVector searchStoreItems(List pCriteria, boolean pDistInfoFl) throws RemoteException ;

    public  String getUserFilterForAccounts(int pUserId) throws  RemoteException ;

    public List<String> getRepNameStartWith(int pStoreId, String pName, int pMaxRows) throws RemoteException;

    public List<String> getRegionNameStartWith(int pStoreId, String pName, int pMaxRows) throws RemoteException;

    public List<String> getCategoryNameStartWith(int pStoreId, String pName, int pMaxRows) throws RemoteException;
}
