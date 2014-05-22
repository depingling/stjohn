package com.cleanwise.service.api.session;

import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;

/**
 * Title:        Warranty
 * Description:  Remote Interface for Warranty Stateless Session Bean
 * Purpose:      Provides access to the methods
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         16.09.2007
 * Time:         15:18:22
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public interface Warranty extends javax.ejb.EJBObject {

    public  WarrantyViewVector getWarrantyViewCollection(WarrantySimpleSearchCriteria criteria) throws RemoteException;

    public  WarrantyDetailView getWarrantyDetailView(int warrantyId) throws RemoteException;

    public  BusEntityDataVector getWarrantyProvidersForStore(int storeId) throws RemoteException;

    public WarrantyData updateWarranty(WarrantyData warrantyData, WarrantyAssocDataVector warrantyAssoc, UserData user) throws RemoteException;

    public AssetWarrantyData updateAssetWarranty(AssetWarrantyData awData, UserData user) throws RemoteException;

    public void removeAssetWarranty(int assetWarrantyId) throws RemoteException;

    public WarrantyNoteData getWarrantyNote(int warrantyNotId) throws RemoteException;

    public WarrantyNoteData updateWarrantyNote(WarrantyNoteData note, UserData user) throws RemoteException;

    public void removeWarrantyNote(int warrantyNoteId) throws RemoteException;

    public WarrantyContentDetailView updateWarrantyContent(WarrantyContentDetailView warrantyContent, UserData user) throws RemoteException;

    public WarrantyContentDetailView getWarrantyContentDetails(int warrantyContentId) throws RemoteException;

    public BusEntityDataVector getServiceProvidersForStore(int storeId) throws RemoteException;

    public void removeAssetWarranty(IdVector assetWarrantyIds) throws RemoteException;

    public void updateAssetWarrantyView(int warrantyId,AssetWarrantyViewVector newLinks , UserData user) throws RemoteException;

    public boolean removeWarrantyContent(int wcId, int cId) throws RemoteException;

    public WarrantyData getWarrantyData(int warrantyId) throws RemoteException;

    public BusEntityData getWarrantyProvider(int warrantyId) throws RemoteException;
    
    public BusEntityDataVector getServiceProvidersForAccount(int accountId) throws RemoteException;

    public WarrantyDataVector getWarrantiesForStore(int storeId) throws RemoteException;
    
    public IdVector getAssetWarrantyIdOnly(int assetId) throws RemoteException;
    
    public void insertAssetWarranty(AssetWarrantyData awData) throws RemoteException;
}
