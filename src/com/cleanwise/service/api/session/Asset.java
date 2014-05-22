package com.cleanwise.service.api.session;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;

import javax.ejb.*;
import java.rmi.*;
import com.cleanwise.service.api.util.DBCriteria;
import java.util.ArrayList;


/**
 * Title:        Asset
 * Description:  Remote Interface for Asset Stateless Session Bean
 * Purpose:      Provides access to the methods
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         19.12.2006
 * Time:         8:54:52
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public interface Asset extends javax.ejb.EJBObject {

    /**
     * Updates the Asset information values to be used by the request.
     *
     * @param pAssetData the AssetData .
     * @param userName   user name
     * @return a <code>AssetData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetData updateAssetData(AssetData pAssetData, String userName) throws RemoteException;

    /**
     * Updates the Asset Property information values to be used by the request.
     *
     * @param pAssetPropertyData the AssetPropertyData .
     * @param userName           user name
     * @return a <code>AssetPropertData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetPropertyData updateAssetPropertyData(AssetPropertyData pAssetPropertyData, String userName) throws RemoteException;

    /**
     * Updates the Asset Detail information values to be used by the request.
     *
     * @param pAssetDetailData the AssetDetailData .
     * @param userName         user name
     * @return a <code>AssetDetailData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetDetailData updateAssetDetailData(AssetDetailData pAssetDetailData, String userName) throws RemoteException;

    /**
     * Gets asset data view collection
     *
     * @param criteria AssetSearchCriteria
     * @return AssetViewVector
     * @throws com.cleanwise.service.api.util.DataNotFoundException
     *                         Data Not Found Exception
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetViewVector getAssetViewVector(AssetSearchCriteria criteria) throws DataNotFoundException, RemoteException;

    /**
     * Gets asset detail data
     *
     * @param assetId  asset id
     * @param storeIds ids collection of  store
     * @param siteIds  ids collection of  site
     * @return AssetDetailData
     * @throws com.cleanwise.service.api.util.DataNotFoundException
     *                         Data Not Found Exception
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetDetailData getAssetDetailData(int assetId, IdVector storeIds, IdVector siteIds) throws DataNotFoundException, RemoteException;

    /**
     * Gets asset detail  view collection
     *
     * @param criteria AssetSearchCriteria
     * @return asset detail  view collection
     * @throws DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    public AssetDetailViewVector getAssetDetailViewVector(AssetSearchCriteria criteria) throws RemoteException, DataNotFoundException;

    /**
     * Updates the Asset Assoc Collection .
     *
     * @param assetId
     *@param pAssetAssocDataVector the  AssetAssocDataVector .
     * @param userName         user name @return a <code>AssetAssocDataVector</code> values
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetAssocDataVector updateAssetAssocDataVector(int assetId, AssetAssocDataVector pAssetAssocDataVector, String userName) throws RemoteException;

    /**
     * Gets a list of BusEntityData objects based off the supplied search criteria object
     *
     * @param pCrit  Description of the Parameter
     * @param typeCd type
     * @return a set of SiteData objects
     * @throws RemoteException if an error occurs
     */
    public BusEntityDataVector getBusEntityByCriteria(BusEntitySearchCriteria pCrit, String typeCd) throws RemoteException;

    /**
     * removes data from clw_asset_assoc
     *
     * @param ids asset_assoc_ids collection
     * @return int Number of rows deleted.
     * @throws RemoteException Required by EJB 1.0
     */
    public int removeAssetAssoc(IdVector ids) throws RemoteException;

    /**
     * Gets asset data  collection
     *
     * @param criteria AssetSearchCriteria
     * @return AssetDataVector
     *
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetDataVector getAssetDataByCriteria(AssetSearchCriteria criteria) throws RemoteException;

    /**
     * gets  associations of an asset on AssetSearchCriteria
     *
     * @param assetId asset id
     * @param assocCd asset assoc cd
     * @return AssetAssocDataVector
     * @throws DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    public AssetAssocDataVector getAssetAssociations(int assetId, String assocCd) throws RemoteException, DataNotFoundException;

    /**
     * removes data from clw_asset_assoc
     *
     * @param pAssetIds     asset ids
     * @param pAssetAssocCd asset assoc cd
     * @param pAssocId      bus_entity_id or item_id
     * @return int Number of rows deleted.
     * @throws RemoteException Required by EJB 1.0
     */
    public int removeAssetAssoc(IdVector pAssetIds, int pAssocId,String pAssetAssocCd) throws RemoteException;

    /**
     * gets site id with which  link is active
     * @param assetId asset id
     * @return site id
     * @throws DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    public int getAssetLocationSiteId(int assetId) throws RemoteException, DataNotFoundException;

    /**
     * locates asset
     *
     * @param assetId assetid
     * @return site address data
     * @throws RemoteException Required by EJB 1.0
     */
    public AddressData getAssetLocation(int assetId) throws RemoteException;
    /**
     * to move asset location
     *
     * @param assetId     asset id
     * @param oldLocation old site id
     * @param newLocation new site id
     * @param userName user name
     * @return success flag
     * @throws RemoteException Required by EJB 1.0
     */

    public boolean moveAssetToSite(int assetId, int oldLocation, int newLocation, String userName) throws RemoteException ;

    /**
     * Gets asset data  collection
     *
     * @param assetIds the asset ids
     * @return AssetDataVector
     * @throws com.cleanwise.service.api.util.DataNotFoundException   Data Not Found Exception
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetDataVector getAssetCollectionByIds(IdVector assetIds) throws RemoteException, DataNotFoundException;

    /**
     * Gets all warranties which has assign to asset
     *
     * @param assetId the asset id
     * @return WarrantyDataVector  warranties
     * @throws RemoteException Required by EJB 1.0
     */
    public WarrantyDataVector getAssetWarrantyAssoc(int assetId) throws RemoteException;

    /**
     * Gets all work orders for each work order item which has assign with asset.
     *
     * @param assetId the asset id
     * @return WarrantyDataVector  work orders
     * @throws RemoteException Required by EJB 1.0
     */
    public WorkOrderDataVector getAssetWorkOrderAssoc(int assetId) throws RemoteException;

    /**
     * Gets contents.
     *
     * @param assetId the asset id
     * @return AssetContentViewVector content data collection without binary data
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetContentViewVector getAssetContents(int assetId) throws RemoteException;

    /**
     * Gets content.
     *
     * @param assetContentId the Asset Content Id
     * @return AssetContentDetailView content data with binary data
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetContentDetailView getAssetContentDetails(int assetContentId) throws RemoteException;

    /**
     * Updates content.
     * @param assetContent data
     * @param user user
     * @return updated data
     * @throws RemoteException RemoteException Required by EJB 1.0
     */
    public AssetContentDetailView updateAssetContent(AssetContentDetailView assetContent, UserData user) throws RemoteException;

    /**
     * Removes asset content
     * @param acId asset content Id
     * @param contentId content Id(optional)
     * @return success flag
     * @throws RemoteException RemoteException Required by EJB 1.0
     */
    public boolean removeAssetContent(int acId,int contentId) throws RemoteException;
    public BusEntityDataVector getMasterAssetLinkedStores(int assetId) throws RemoteException;
    public AssetViewVector getStagedAssetVector(AssetSearchCriteria criteria) throws DataNotFoundException, RemoteException;
    public StagedAssetData updateStagedAssetData(StagedAssetData pAssetData, String userName) throws RemoteException;
    public StagedAssetAssocData updateStagedAssetAssocData(StagedAssetAssocData pAssetAssocData, String userName) throws RemoteException;
    public StagedAssetAssocDataVector getStagedAssetAssocDataVector(AssetSearchCriteria criteria) throws RemoteException;
    public StagedAssetData getStagedAssetData(int stagedAssetId) throws RemoteException;
    public boolean canEditMasterAsset(int assetId, int storeId) throws RemoteException;
    public IdVector checkAssetStoreUnique(AssetSearchCriteria criteria) throws RemoteException;

    public BusEntityDataVector getMasterItemLinkedStores(int itemId) throws RemoteException;
    public AssetContentDetailView getAssetContentDetails(DBCriteria dbc) throws RemoteException;
    public IdVector getParentMasterAssetIds (AssetSearchCriteria criteria) throws RemoteException;
    public IdVector getParentOrChildMasterAssetIds(int assetId, String resultIdName) throws RemoteException ;
    public ArrayList checkAssetsInUse(IdVector categoryIdV) throws RemoteException ;
    public int removeAssetMasterAssoc(DBCriteria dbc) throws RemoteException ;
    public int removeAssetDetailDataForCategory(IdVector toDeleteAssetIds) throws RemoteException;
    public int removeAssetAssoc(DBCriteria dbCriteria) throws RemoteException ;
}
