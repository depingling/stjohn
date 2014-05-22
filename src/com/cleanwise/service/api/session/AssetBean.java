package com.cleanwise.service.api.session;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.*;

/**
 * Title:        AssetBean
 * Description:  Bean implementation for Asset Session Bean
 * Purpose:      Ejb for scheduled asset management
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         19.12.2006
 * Time:         9:03:52
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class AssetBean extends ApplicationServicesAPI {

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws RemoteException           if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

    /**
     * Updates the Asset information values to be used by the request.
     *
     * @param pAssetData the AssetData .
     * @param userName   user name
     * @return a <code>AssetData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetData updateAssetData(AssetData pAssetData, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            updateAssetData(conn, pAssetData, userName);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pAssetData;
    }

    /**
     * Updates the Asset Property information values to be used by the request.
     *
     * @param pAssetPropertyData the AssetPropertyData .
     * @param userName           user name
     * @return a <code>AssetPropertData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetPropertyData updateAssetPropertyData(AssetPropertyData pAssetPropertyData, String userName) throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            updateAssetPropertyData(conn, pAssetPropertyData, userName);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pAssetPropertyData;
    }

    /**
     * Updates the Asset information values to be used by the request.
     *
     * @param conn       Connection
     * @param pAssetData the AssetData .
     * @param userName   user name
     * @return a <code>AssetData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    private AssetData updateAssetData(Connection conn, AssetData pAssetData, String userName) throws RemoteException {

        try {
            AssetData data;
            data = pAssetData;
            if (data.isDirty()) {
                if (data.getAssetId() == 0) {
                    data.setAddBy(userName);
                    data = AssetDataAccess.insert(conn, data);
                    if (!Utility.isSet(data.getAssetNum())
                            && !RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(pAssetData.getAssetTypeCd())) {
                        data.setAssetNum(String.valueOf(data.getAssetId()));
                        AssetDataAccess.update(conn, data);
                    }
                } else {
                    if (!Utility.isSet(data.getAssetNum())
                            && !RefCodeNames.ASSET_TYPE_CD.CATEGORY.equals(pAssetData.getAssetTypeCd())) {
                        data.setAssetNum(String.valueOf(data.getAssetId()));
                    }
                    data.setModBy(userName);
                    AssetDataAccess.update(conn, data);
                }
            }
            return data;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the Asset Detail information values to be used by the request.
     *
     * @param pAssetDetailData the AssetDetailData .
     * @param userName         user name
     * @return a <code>AssetDetailData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetDetailData updateAssetDetailData(AssetDetailData pAssetDetailData, String userName) throws RemoteException {

        Connection conn = null;
        try {
            if (pAssetDetailData != null) {
                conn = getConnection();
                AssetDetailData detailData;
                detailData = pAssetDetailData;
                if (detailData.isDirty()) {
                    AssetData assetData = detailData.getAssetData();
                    AssetPropertyData longDesc = detailData.getLongDesc();
                    AssetPropertyData customDesc = detailData.getCustomDesc();
                    AssetPropertyData inactiveReason = detailData.getInactiveReason();
                    AssetPropertyData acquisitionCost = detailData.getAcquisitionCost();
                    AssetPropertyData acquisitionDate = detailData.getAcquisitionDate();
                    AssetPropertyData dateInService = detailData.getDateInService();
                    AssetPropertyData lastHMR = detailData.getLastHMR();
                    AssetPropertyData dateLastHMR = detailData.getDateLastHMR();

                    if (assetData != null && assetData.getAssetId() > 0) {
                        AssetPropertyDataVector currentProperties = null;
                        boolean foundPropFlag;
                        boolean propertyInUseFlag = false;
                        try {
                            currentProperties = getAllAssetProperties(conn, assetData.getAssetId());
                            foundPropFlag = true;
                        } catch (DataNotFoundException e) {
                            foundPropFlag = false;
                        }
                        if (foundPropFlag) {
                            Iterator it = currentProperties.iterator();
                            int countProperties = 0;
                            while (it.hasNext()) {
                                AssetPropertyData assetPropData = (AssetPropertyData) it.next();
                                if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC.equals(assetPropData.getAssetPropertyCd()) && longDesc != null) {
                                    propertyInUseFlag = longDesc.getAssetPropertyId() == 0 ? true : false;
                                    countProperties++;
                                } else
                                if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE.equals(assetPropData.getAssetPropertyCd()) && dateInService != null) {
                                    propertyInUseFlag = dateInService.getAssetPropertyId() == 0 ? true : false;
                                    countProperties++;
                                } else
                                if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.LAST_HOUR_METER_READING.equals(assetPropData.getAssetPropertyCd()) && lastHMR != null) {
                                    propertyInUseFlag = dateInService.getAssetPropertyId() == 0 ? true : false;
                                    countProperties++;
                                } else
                                if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_LAST_HOUR_METER_READING.equals(assetPropData.getAssetPropertyCd()) && dateLastHMR != null) {
                                    propertyInUseFlag = dateInService.getAssetPropertyId() == 0 ? true : false;
                                    countProperties++;
                                } else
                                if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.INACTIVE_REASON.equals(assetPropData.getAssetPropertyCd()) && inactiveReason != null) {
                                    propertyInUseFlag = inactiveReason.getAssetPropertyId() == 0 ? true : false;
                                    countProperties++;
                                } else
                                if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_DATE.equals(assetPropData.getAssetPropertyCd()) && acquisitionDate != null) {
                                    propertyInUseFlag = acquisitionDate.getAssetPropertyId() == 0 ? true : false;
                                    countProperties++;
                                } else
                                if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_COST.equals(assetPropData.getAssetPropertyCd()) && acquisitionCost != null) {
                                    propertyInUseFlag = acquisitionCost.getAssetPropertyId() == 0 ? true : false;
                                    countProperties++;
                                }
                            }
                            if (propertyInUseFlag) {
                                throw new Exception("some properties can not be multiple");
                            }
                        }
                    }

                    assetData = updateAssetData(conn, assetData, userName);

                    longDesc = updateAssetPropertyData(conn, longDesc,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC, assetData.getAssetId(), userName);

                    customDesc = updateAssetPropertyData(conn, customDesc,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.CUSTOM_DESC, assetData.getAssetId(), userName);

                    dateInService = updateAssetPropertyData(conn, dateInService,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE, assetData.getAssetId(), userName);

                    dateLastHMR = updateAssetPropertyData(conn, dateLastHMR,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_LAST_HOUR_METER_READING, assetData.getAssetId(), userName);

                    lastHMR = updateAssetPropertyData(conn, lastHMR,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.LAST_HOUR_METER_READING, assetData.getAssetId(), userName);

                    acquisitionDate = updateAssetPropertyData(conn, acquisitionDate,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_DATE, assetData.getAssetId(), userName);

                    acquisitionCost = updateAssetPropertyData(conn, acquisitionCost,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_COST, assetData.getAssetId(), userName);

                    inactiveReason = updateAssetPropertyData(conn, inactiveReason,
                            RefCodeNames.ASSET_PROPERTY_TYPE_CD.INACTIVE_REASON, assetData.getAssetId(), userName);

                    pAssetDetailData.setAssetData(assetData);
                    pAssetDetailData.setLongDesc(longDesc);
                    pAssetDetailData.setLongDesc(customDesc);
                    pAssetDetailData.setInactiveReason(inactiveReason);
                    pAssetDetailData.setAcquisitionCost(acquisitionCost);
                    pAssetDetailData.setAcquisitionDate(acquisitionDate);
                    pAssetDetailData.setDateInService(dateInService);
                    pAssetDetailData.setDateLastHMR(dateLastHMR);
                    pAssetDetailData.setLastHMR(lastHMR);
                }

            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pAssetDetailData;
    }

    /**
     * Updates the Asset Property information values to be used by the request.
     *
     * @param conn               Connectin
     * @param pAssetPropertyData the AssetPropertyData .
     * @param userName           user name
     * @return a <code>AssetPropertData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    private AssetPropertyData updateAssetPropertyData(Connection conn, AssetPropertyData pAssetPropertyData, String userName) throws RemoteException {
        try {
            AssetPropertyData propertyData;
            propertyData = pAssetPropertyData;
            String propertyTypeCd = propertyData.getAssetPropertyCd();
            if (propertyData.getAssetId() > 0) {
                if (propertyData.isDirty()) {

                    if (propertyData.getAssetPropertyId() == 0 && propertyData.getValue().trim().length() > 0) {

                        propertyData.setAddBy(userName);
                        AssetPropertyDataAccess.insert(conn, propertyData);

                    } else if (propertyData.getAssetPropertyId() > 0 && propertyData.getValue().trim().length() > 0) {

                        propertyData.setModBy(userName);
                        AssetPropertyDataAccess.update(conn, propertyData);

                    } else if (propertyData.getAssetPropertyId() > 0 && propertyData.getValue().trim().length() == 0) {

                        AssetPropertyDataAccess.remove(conn, propertyData.getAssetPropertyId());
                        propertyData = AssetPropertyData.createValue();
                        propertyData.setAssetPropertyCd(propertyTypeCd);
                    }
                }
                return propertyData;
            } else {
                throw new Exception("DATA IS NULL");
            }
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    private String getAssetPropertyValue(Connection conn, int assetId, String propertyCd) throws RemoteException {
        try {
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(AssetPropertyDataAccess.ASSET_ID, assetId);
            crit.addEqualTo(AssetPropertyDataAccess.ASSET_PROPERTY_CD, propertyCd);
            AssetPropertyDataVector result = AssetPropertyDataAccess.select(conn, crit);
            if (result != null && result.size() > 1) {
                throw new Exception("Multiple asset property");
            } else if (result != null && result.size() > 0) {
                return ((AssetPropertyData) result.get(0)).getValue();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    private AssetPropertyDataVector getAllAssetProperties(Connection conn, int assetId) throws DataNotFoundException, RemoteException {
        try {
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(AssetPropertyDataAccess.ASSET_ID, assetId);
            return AssetPropertyDataAccess.select(conn, crit);
        }
        catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }


    /**
     * Gets asset data view collection
     *
     * @param criteria AssetSearchCriteria
     * @return AssetViewVector
     * @throws com.cleanwise.service.api.util.DataNotFoundException
     *                         Data Not Found Exception
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetViewVector getAssetViewVector(AssetSearchCriteria criteria) throws DataNotFoundException, RemoteException {
        AssetViewVector assetVV = new AssetViewVector();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = convertToDBCriteria(criteria);
            AssetDataVector assetDV = groupById(AssetDataAccess.select(conn, crit));
            PropertyUtil propertyUtil = new PropertyUtil(conn);
            Iterator it = assetDV.iterator();
            while (it.hasNext()) {
                AssetData asset = (AssetData) it.next();
                AssetView assetView = AssetView.createValue();
                HashMap siteInfo = new HashMap();
                crit = new DBCriteria();

                crit.addEqualTo(AssetAssocDataAccess.ASSET_ID, asset.getAssetId());
                crit.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
                if (criteria.getAssetAssocCds().containsKey(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE)) {
                    crit.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, criteria.getAssetAssocCds().get(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE));
                }
                AssetAssocDataVector assetAssocDV = AssetAssocDataAccess.select(conn, crit);
                IdVector siteIds = new IdVector();
                Iterator it1 = assetAssocDV.iterator();
                while (it1.hasNext()) {
                    siteIds.add(new Integer(((AssetAssocData) it1.next()).getBusEntityId()));
                }
                crit = new DBCriteria();

                if (siteIds != null && siteIds.size() > 0) {

                    crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, siteIds);
                    crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
                    if (criteria.getUserId() > 0) {
                        if (RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(criteria.getUserTypeCd()) || RefCodeNames.USER_TYPE_CD.MSB.equals(criteria.getUserTypeCd())) {

                            crit.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID, UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.BUS_ENTITY_ID);
                            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ID, criteria.getUserId());
                            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);
                        } else if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(criteria.getUserTypeCd())) {
                            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ID, criteria.getUserId());
                            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                            crit.addJoinCondition(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.BUS_ENTITY_ID, BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID);
                            crit.addJoinCondition(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID, BusEntityDataAccess.CLW_BUS_ENTITY, BusEntityDataAccess.BUS_ENTITY_ID);
                            crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                        } else if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(criteria.getUserTypeCd())
                                || RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(criteria.getUserTypeCd())
                                || RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(criteria.getUserTypeCd())
                                || RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(criteria.getUserTypeCd())) {

                            DBCriteria isolCrit = new DBCriteria();
                            crit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " ACCOUNT_STORE");
                            crit.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " SITE_ACCOUNT");

                            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ID, criteria.getUserId());
                            crit.addJoinTableEqualTo(UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.STORE);

                            isolCrit.addCondition(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.BUS_ENTITY_ID + "=" + "ACCOUNT_STORE." + BusEntityAssocDataAccess.BUS_ENTITY2_ID);
                            isolCrit.addEqualTo("ACCOUNT_STORE." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                            isolCrit.addCondition("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY2_ID + "=" + "ACCOUNT_STORE." + BusEntityAssocDataAccess.BUS_ENTITY1_ID);
                            isolCrit.addCondition("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY1_ID + "=" + BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID);
                            isolCrit.addEqualTo("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                            crit.addIsolatedCriterita(isolCrit);
                        } else if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(criteria.getUserTypeCd())) {
                            DBCriteria isolCrit = new DBCriteria();
                            isolCrit.addCondition("1=2");
                            crit.addIsolatedCriterita(isolCrit);
                        }

                    }
                    BusEntityDataVector busEntityDV = BusEntityDataAccess.select(conn, crit);
                    if (busEntityDV != null && busEntityDV.size() > 0) {
                        Iterator it2 = busEntityDV.iterator();
                        ArrayList siteInfoIds = new ArrayList();
                        ArrayList siteInfoNames = new ArrayList();
                        int index = 0;
                        while (it2.hasNext()) {
                            BusEntityData busEntityData = (BusEntityData) it2.next();
                            siteInfo = new HashMap();
                            siteInfoIds.add(index, new Integer(busEntityData.getBusEntityId()));
                            siteInfoNames.add(index, busEntityData.getShortDesc());
                            index++;
                        }
                        siteInfo.put(AssetView.SITE_INFO.SITE_IDS, siteInfoIds);
                        siteInfo.put(AssetView.SITE_INFO.SITE_NAMES, siteInfoNames);
                    }
                }

                assetView.setAssetId(asset.getAssetId());
                assetView.setParentId(asset.getParentId());
                assetView.setManufId(asset.getManufId());
                assetView.setManufName(asset.getManufName());
                assetView.setManufSku(asset.getManufSku());
                assetView.setAssetTypeCd(asset.getAssetTypeCd());
                assetView.setAssetName(asset.getShortDesc());
                assetView.setAssetNumber(asset.getAssetNum());
                assetView.setSerialNumber(asset.getSerialNum());
                assetView.setModelNumber(asset.getModelNumber());
                assetView.setDateInService(getAssetPropertyValue(conn, asset.getAssetId(), RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE));
                if(asset.getParentId()>0){
                    try {
                        AssetData category= AssetDataAccess.select(conn,asset.getParentId());
                        assetView.setAssetCategory(category.getShortDesc());
                    } catch (DataNotFoundException e) {
                       assetView.setAssetCategory(null);
                    }
                }
                assetView.setStatus(asset.getStatusCd());
                assetView.setSiteInfo(siteInfo);
                assetVV.add(assetView);
            }

        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
        return assetVV;
    }

    private AssetDataVector groupById(AssetDataVector assetDV) {

        AssetDataVector result = new AssetDataVector();
        HashMap ids = new HashMap();
        Iterator it = assetDV.iterator();
        while (it.hasNext()) {
            AssetData assetData = (AssetData) it.next();
            if (!ids.containsValue(String.valueOf(assetData.getAssetId()))) {
                ids.put(String.valueOf(assetData.getAssetId()), String.valueOf(assetData.getAssetId()));
                result.add(assetData);
            }
        }
        return result;
    }

    /**
     * convert to DBCriteria
     *
     * @param criteria AssetSearchCriteria
     */
    private DBCriteria convertToDBCriteria(AssetSearchCriteria criteria) {
        DBCriteria dbCriteria = new DBCriteria();

        if (criteria.getAssetId() > 0) {
            dbCriteria.addEqualTo(AssetDataAccess.ASSET_ID, criteria.getAssetId());
        } else if (criteria.getAssetIds() != null && criteria.getAssetIds().size() > 0) {
            dbCriteria.addOneOf(AssetDataAccess.ASSET_ID, criteria.getAssetIds());
        }

        if (criteria.getParentId() > 0) {
            dbCriteria.addEqualTo(AssetDataAccess.PARENT_ID, criteria.getParentId());
        } else if (criteria.getParentIds() != null && criteria.getParentIds().size() > 0) {
            dbCriteria.addOneOf(AssetDataAccess.PARENT_ID, criteria.getParentIds());
        } else if (criteria.getParentIds() != null){
            DBCriteria tCrit = new DBCriteria();
            tCrit.addCondition("1=2");
            dbCriteria.addIsolatedCriterita(tCrit);
        }

        if (criteria.getMasterAssetId() > 0) {
            dbCriteria.addEqualTo(AssetDataAccess.MASTER_ASSET_ID, criteria.getMasterAssetId());
        }

        if (criteria.getManufId() > 0) {
            dbCriteria.addEqualTo(AssetDataAccess.MANUF_ID, criteria.getManufId());
        }

        if (criteria.getAssetTypeCd() != null) {
            dbCriteria.addEqualTo(AssetDataAccess.ASSET_TYPE_CD, criteria.getAssetTypeCd());
        }

        if (criteria.getOrderBy() != null) {
            dbCriteria.addOrderBy((String)criteria.getOrderBy().getObject1(),
                    ((Boolean)criteria.getOrderBy().getObject2()).booleanValue());
        }

        if (criteria.getAssetName() != null) {
            String assetName = criteria.getAssetName();
            if (criteria.getSearchNameTypeCd() != null) {
                String searchNameType = criteria.getSearchNameTypeCd();
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchNameType)) {
                    if(criteria.getIgnoreCase()){
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.SHORT_DESC, assetName);
                    }else{
                        dbCriteria.addBeginsWith(AssetDataAccess.SHORT_DESC, assetName);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchNameType)) {
                    if(criteria.getIgnoreCase()){
                        dbCriteria.addContainsIgnoreCase(AssetDataAccess.SHORT_DESC, assetName);
                    } else{
                        dbCriteria.addContains(AssetDataAccess.SHORT_DESC, assetName);
                    }
                } else {
                    if(criteria.getIgnoreCase()){
                        dbCriteria.addEqualToIgnoreCase(AssetDataAccess.SHORT_DESC, assetName);
                    } else {
                        dbCriteria.addEqualTo(AssetDataAccess.SHORT_DESC, assetName);
                    }
                }
            } else {
                if(criteria.getIgnoreCase()){
                    dbCriteria.addEqualToIgnoreCase(AssetDataAccess.SHORT_DESC, assetName);
                } else {
                    dbCriteria.addEqualTo(AssetDataAccess.SHORT_DESC, assetName);
                }
            }
        }

        if (criteria.getAssetNumber() != null) {
            String assetNumber = criteria.getAssetNumber();
            if (criteria.getSearchNumberTypeCd() != null) {
                String searchNumberType = criteria.getSearchNumberTypeCd();
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchNumberType)) {
                    if(criteria.getIgnoreCase()){
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.ASSET_NUM, assetNumber);
                    }else{
                        dbCriteria.addBeginsWith(AssetDataAccess.ASSET_NUM, assetNumber);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchNumberType)) {
                    if(criteria.getIgnoreCase()){
                        dbCriteria.addContainsIgnoreCase(AssetDataAccess.ASSET_NUM, assetNumber);
                    } else{
                        dbCriteria.addContains(AssetDataAccess.ASSET_NUM, assetNumber);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.EQUALS.equals(searchNumberType)){
                    if(criteria.getIgnoreCase()){
                        dbCriteria.addEqualToIgnoreCase(AssetDataAccess.ASSET_NUM, assetNumber);
                    } else {
                        dbCriteria.addEqualTo(AssetDataAccess.ASSET_NUM, assetNumber);
                    }
                } else {
                    if(criteria.getIgnoreCase()){
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.ASSET_NUM, assetNumber);
                    } else {
                        dbCriteria.addBeginsWith(AssetDataAccess.ASSET_NUM, assetNumber);
                    }
                }
            } else {
                if (criteria.getIgnoreCase()) {
                    dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.ASSET_NUM, assetNumber);
                } else {
                    dbCriteria.addBeginsWith(AssetDataAccess.ASSET_NUM, assetNumber);
                }
            }
        }

        if (criteria.getSerialNumber() != null) {
            String serialNumber = criteria.getSerialNumber();
            if (criteria.getSearchNumberTypeCd() != null) {
                String searchNumberType = criteria.getSearchNumberTypeCd();
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchNumberType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.SERIAL_NUM, serialNumber);
                    } else {
                        dbCriteria.addBeginsWith(AssetDataAccess.SERIAL_NUM, serialNumber);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchNumberType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addContainsIgnoreCase(AssetDataAccess.SERIAL_NUM, serialNumber);
                    } else {
                        dbCriteria.addContains(AssetDataAccess.SERIAL_NUM, serialNumber);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.EQUALS.equals(searchNumberType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addEqualToIgnoreCase(AssetDataAccess.SERIAL_NUM, serialNumber);
                    } else {
                        dbCriteria.addEqualTo(AssetDataAccess.SERIAL_NUM, serialNumber);
                    }
                } else {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.SERIAL_NUM, serialNumber);
                    } else {
                        dbCriteria.addBeginsWith(AssetDataAccess.SERIAL_NUM, serialNumber);
                    }
                }
            } else {
                if (criteria.getIgnoreCase()) {
                    dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.SERIAL_NUM, serialNumber);
                } else {
                    dbCriteria.addBeginsWith(AssetDataAccess.SERIAL_NUM, serialNumber);
                }
            }
        }

        if (criteria.getManufSku() != null) {
            String manufSku = criteria.getManufSku();
            if (criteria.getSearchManufSkuTypeCd() != null) {
                String searchManufSkuType = criteria.getSearchManufSkuTypeCd();
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchManufSkuType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.MANUF_SKU, manufSku);
                    } else {
                        dbCriteria.addBeginsWith(AssetDataAccess.MANUF_SKU, manufSku);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchManufSkuType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addContainsIgnoreCase(AssetDataAccess.MANUF_SKU, manufSku);
                    } else {
                        dbCriteria.addContains(AssetDataAccess.MANUF_SKU, manufSku);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.EQUALS.equals(searchManufSkuType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addEqualToIgnoreCase(AssetDataAccess.MANUF_SKU, manufSku);
                    } else {
                        dbCriteria.addEqualTo(AssetDataAccess.MANUF_SKU, manufSku);
                    }
                } else {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.MANUF_SKU, manufSku);
                    } else {
                        dbCriteria.addBeginsWith(AssetDataAccess.MANUF_SKU, manufSku);
                    }
                }
            } else {
                if (criteria.getIgnoreCase()) {
                    dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.MANUF_SKU, manufSku);
                } else {
                    dbCriteria.addBeginsWith(AssetDataAccess.MANUF_SKU, manufSku);
                }
            }
        }

        if (criteria.getModelNumber() != null) {
            String modelNumber = criteria.getModelNumber();
            if (criteria.getSearchModelTypeCd() != null) {
                String searchModelType = criteria.getSearchModelTypeCd();
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchModelType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    } else {
                        dbCriteria.addBeginsWith(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchModelType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addContainsIgnoreCase(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    } else {
                        dbCriteria.addContains(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    }
                } else if (RefCodeNames.SEARCH_TYPE_CD.EQUALS.equals(searchModelType)) {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addEqualToIgnoreCase(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    } else {
                        dbCriteria.addEqualTo(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    }
                } else {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    } else {
                        dbCriteria.addBeginsWith(AssetDataAccess.MODEL_NUMBER, modelNumber);
                    }
                }
            } else {
                if (criteria.getIgnoreCase()) {
                    dbCriteria.addBeginsWithIgnoreCase(AssetDataAccess.MODEL_NUMBER, modelNumber);
                } else {
                    dbCriteria.addBeginsWith(AssetDataAccess.MODEL_NUMBER, modelNumber);
                }
            }
        }

        if (criteria.getStatusCd() != null) {
            dbCriteria.addEqualTo(AssetDataAccess.STATUS_CD, criteria.getStatusCd());
        } else if (!criteria.getShowInactive()) {
            dbCriteria.addNotEqualTo(AssetDataAccess.STATUS_CD, RefCodeNames.ASSET_STATUS_CD.INACTIVE);
        }

        if (criteria.getManufName() != null) {
            dbCriteria.addEqualTo(AssetDataAccess.MANUF_NAME, criteria.getManufName());
        }

        if (criteria.getUserId() > 0 && criteria.getUserTypeCd() != null) {
        	if(criteria.isUserAuthorizedForAssetWOViewAllForStore()) {

        	} else if (RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(criteria.getUserTypeCd())
                    || RefCodeNames.USER_TYPE_CD.MSB.equals(criteria.getUserTypeCd())) {
                DBCriteria isolCrit1 = new DBCriteria();

                dbCriteria.addJoinTable(UserDataAccess.CLW_USER);
                dbCriteria.addJoinTable(UserAssocDataAccess.CLW_USER_ASSOC);
                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_SITE");
                isolCrit1.addEqualTo(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, criteria.getUserId());
                isolCrit1.addCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID + "=" + UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ID);
                isolCrit1.addCondition(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.BUS_ENTITY_ID + "=ASSET_SITE." + AssetAssocDataAccess.BUS_ENTITY_ID);
                isolCrit1.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);
                isolCrit1.addCondition("ASSET_SITE." + AssetAssocDataAccess.ASSET_ID + "=" + AssetDataAccess.CLW_ASSET + "." + AssetDataAccess.ASSET_ID);

                dbCriteria.addIsolatedCriterita(isolCrit1);

            } else if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(criteria.getUserTypeCd())) {
                DBCriteria isolCrit1 = new DBCriteria();

                dbCriteria.addJoinTable(UserDataAccess.CLW_USER);
                dbCriteria.addJoinTable(UserAssocDataAccess.CLW_USER_ASSOC);
                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_SITE");
                dbCriteria.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " SITE_ACCOUNT");

                isolCrit1.addEqualTo(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, criteria.getUserId());
                isolCrit1.addCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID + "=" + UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ID);
                isolCrit1.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                isolCrit1.addCondition(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.BUS_ENTITY_ID + "=SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY2_ID);
                isolCrit1.addEqualTo("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                isolCrit1.addCondition("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY1_ID + "=" + "ASSET_SITE" + "." + AssetAssocDataAccess.BUS_ENTITY_ID);
                isolCrit1.addCondition("ASSET_SITE." + AssetAssocDataAccess.ASSET_ID + "=" + AssetDataAccess.CLW_ASSET + "." + AssetDataAccess.ASSET_ID);

                dbCriteria.addIsolatedCriterita(isolCrit1);

            } else if (RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(criteria.getUserTypeCd())
                    || RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(criteria.getUserTypeCd())
                    || RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(criteria.getUserTypeCd())
                    || RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(criteria.getUserTypeCd())) {

                DBCriteria isolCrit1 = new DBCriteria();
                dbCriteria.addJoinTable(UserDataAccess.CLW_USER);
                dbCriteria.addJoinTable(UserAssocDataAccess.CLW_USER_ASSOC);
                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_STORE");

                isolCrit1.addEqualTo(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, criteria.getUserId());
                isolCrit1.addCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID + "=" + UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ID);
                isolCrit1.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.STORE);
                isolCrit1.addCondition(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.BUS_ENTITY_ID + "=ASSET_STORE." + AssetAssocDataAccess.BUS_ENTITY_ID);
                isolCrit1.addCondition("ASSET_STORE." + AssetAssocDataAccess.ASSET_ID + "=" + AssetDataAccess.CLW_ASSET + "." + AssetDataAccess.ASSET_ID);

                dbCriteria.addIsolatedCriterita(isolCrit1);

            } else if (!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(criteria.getUserTypeCd())) {

                DBCriteria isolCrit1 = new DBCriteria();
                isolCrit1.addCondition(("1=2"));
                dbCriteria.addIsolatedCriterita(isolCrit1);
            }
        }

        if (criteria.getBusEntityIds() != null) {
            dbCriteria.addJoinCondition(AssetDataAccess.CLW_ASSET, AssetDataAccess.ASSET_ID, AssetAssocDataAccess.CLW_ASSET_ASSOC, AssetAssocDataAccess.ASSET_ID);
            dbCriteria.addJoinTableOneOf(AssetAssocDataAccess.CLW_ASSET_ASSOC, AssetAssocDataAccess.BUS_ENTITY_ID, criteria.getBusEntityIds());

        } else if (criteria.getStoreIds() != null || criteria.getSiteIds() != null || criteria.getAccountIds() != null || criteria.getServiceIds() != null) {

            DBCriteria isolCrit1 = new DBCriteria();
            DBCriteria isolCrit2 = new DBCriteria();
            DBCriteria isolCrit3 = new DBCriteria();
            DBCriteria isolCrit4 = new DBCriteria();
            DBCriteria isolCrit = new DBCriteria();

            String siteAssocStatusCd = (String) criteria.getAssetAssocCds().get(RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
            String storeAssocStatusCd = (String) criteria.getAssetAssocCds().get(RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
            String serviceAssocStatusCd = (String) criteria.getAssetAssocCds().get(RefCodeNames.ASSET_ASSOC_CD.ASSET_SERVICE);


            if (criteria.getSiteIds() != null) {

                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_SITE");
                isolCrit1.addOneOf("ASSET_SITE." + AssetAssocDataAccess.BUS_ENTITY_ID, criteria.getSiteIds());
                isolCrit1.addEqualTo("ASSET_SITE." + AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
                isolCrit1.addCondition("ASSET_SITE." + AssetAssocDataAccess.ASSET_ID + "=" + AssetDataAccess.CLW_ASSET + "." + AssetAssocDataAccess.ASSET_ID);
                if (siteAssocStatusCd != null) {
                    isolCrit1.addEqualTo("ASSET_SITE." + AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, siteAssocStatusCd);
                }
                isolCrit.addIsolatedCriterita(isolCrit1);
            }

            if (criteria.getAccountIds() != null) {

                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_SITE");
                dbCriteria.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " SITE_ACCOUNT");

                isolCrit2.addOneOf("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY2_ID, criteria.getAccountIds());
                isolCrit2.addCondition(AssetDataAccess.CLW_ASSET + "." + AssetDataAccess.ASSET_ID + " = " + "ASSET_SITE." + AssetAssocDataAccess.ASSET_ID);
                isolCrit2.addEqualTo("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                isolCrit2.addCondition("SITE_ACCOUNT." + BusEntityAssocDataAccess.BUS_ENTITY1_ID + "=" + "ASSET_SITE" + "." + AssetAssocDataAccess.BUS_ENTITY_ID);
                if (storeAssocStatusCd != null) {
                    isolCrit2.addEqualTo("ASSET_SITE." + AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, storeAssocStatusCd);
                }
                isolCrit.addIsolatedCriterita(isolCrit2);
            }

            if (criteria.getStoreIds() != null) {

                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_STORE");
                isolCrit3.addOneOf("ASSET_STORE." + AssetAssocDataAccess.BUS_ENTITY_ID, criteria.getStoreIds());
                isolCrit3.addEqualTo("ASSET_STORE." + AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
                isolCrit3.addCondition("ASSET_STORE." + AssetAssocDataAccess.ASSET_ID + "=" + AssetDataAccess.CLW_ASSET + "." + AssetAssocDataAccess.ASSET_ID);
                if (storeAssocStatusCd != null) {
                    isolCrit3.addEqualTo("ASSET_STORE." + AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, storeAssocStatusCd);
                }
                isolCrit.addIsolatedCriterita(isolCrit3);
                if (criteria.getSiteIds() != null) {
                    isolCrit.addCondition("ASSET_SITE." + AssetAssocDataAccess.ASSET_ID + "=" + "ASSET_STORE." + AssetAssocDataAccess.ASSET_ID);
                }

            }

            if (criteria.getServiceIds() != null) {

                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_SERVICE");
                isolCrit4.addOneOf("ASSET_SERVICE." + AssetAssocDataAccess.ITEM_ID, criteria.getServiceIds());
                isolCrit4.addEqualTo("ASSET_SERVICE." + AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_SERVICE);
                isolCrit4.addCondition("ASSET_SERVICE." + AssetAssocDataAccess.ASSET_ID + "=" + AssetDataAccess.CLW_ASSET + "." + AssetAssocDataAccess.ASSET_ID);
                isolCrit.addIsolatedCriterita(isolCrit3);

                if (serviceAssocStatusCd != null) {
                    isolCrit4.addEqualTo("ASSET_SERVICE." + AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, serviceAssocStatusCd);
                }

                if (criteria.getSiteIds() != null) {
                    isolCrit.addCondition("ASSET_SITE." + AssetAssocDataAccess.ASSET_ID + "=" + "ASSET_SERVICE." + AssetAssocDataAccess.ASSET_ID);
                }

                if (criteria.getStoreIds() != null) {
                    isolCrit.addCondition("ASSET_STORE." + AssetAssocDataAccess.ASSET_ID + "=" + "ASSET_SERVICE." + AssetAssocDataAccess.ASSET_ID);
                }
            }

            if (criteria.getWarrantyIds() != null) {
                dbCriteria.addJoinTableOneOf(AssetWarrantyDataAccess.CLW_ASSET_WARRANTY, AssetWarrantyDataAccess.WARRANTY_ID, criteria.getWarrantyIds());
                dbCriteria.addJoinCondition(AssetWarrantyDataAccess.CLW_ASSET_WARRANTY, AssetWarrantyDataAccess.ASSET_ID, AssetDataAccess.CLW_ASSET, AssetDataAccess.ASSET_ID);
            }

            dbCriteria.addIsolatedCriterita(isolCrit);
        }

        if (criteria.getAssocTypeCd() != null && criteria.getBusEntityIds() != null) {
            dbCriteria.addJoinTableEqualTo(AssetAssocDataAccess.CLW_ASSET_ASSOC, AssetAssocDataAccess.ASSET_ASSOC_CD, criteria.getAssocTypeCd());
        }

        return dbCriteria;
    }

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
    public AssetDetailData getAssetDetailData(int assetId, IdVector storeIds, IdVector siteIds) throws DataNotFoundException, RemoteException {

        Connection conn = null;
        AssetData assetData = null;
        try {
            conn = getConnection();
            AssetSearchCriteria criteria = new AssetSearchCriteria();
            AssetDetailData detailData = AssetDetailData.createValue();
            criteria.setAssetId(assetId);
            criteria.setStoreIds(storeIds);
            criteria.setSiteIds(siteIds);
            criteria.setShowInactive(true);
            DBCriteria crit = convertToDBCriteria(criteria);
            AssetDataVector assetDataVector = AssetDataAccess.select(conn, crit);
            if (assetDataVector != null && assetDataVector.size() > 1) {
                throw new Exception("Multiple asset.Asset id : " + assetId);
            }
            if (assetDataVector != null && assetDataVector.size() != 0){
              assetData = (AssetData) assetDataVector.get(0);
              boolean foundPropertiesFl = false;
              AssetPropertyDataVector properties = null;
              try {
                properties = getAllAssetProperties(conn, assetData.getAssetId());
                foundPropertiesFl = true;
              }
              catch (DataNotFoundException e) {
                foundPropertiesFl = false;
              }
              if (foundPropertiesFl) {
                detailData = createAssetDetailData(assetData, properties);
              }
            }
            return detailData;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private AssetDetailData createAssetDetailData(AssetData assetData, AssetPropertyDataVector properties) {

        AssetDetailData detailData = AssetDetailData.createValue();
        AssetPropertyData longDesc = null;
        AssetPropertyData customDesc = null;
        AssetPropertyData inactiveReason = null;
        AssetPropertyData acquisitionCost = null;
        AssetPropertyData acquisitionDate = null;
        AssetPropertyData dateInService = null;
        AssetPropertyData lastHMR = null;
        AssetPropertyData dateLastHMR = null;

        int countProperties = 0;
        Iterator it = properties.iterator();
        while (it.hasNext()) {
            AssetPropertyData assetPropData = (AssetPropertyData) it.next();
            if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.LONG_DESC.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                longDesc = assetPropData;
            } else if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.CUSTOM_DESC.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                customDesc = assetPropData;
            } else if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.INACTIVE_REASON.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                inactiveReason = assetPropData;
            } else
            if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_DATE.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                acquisitionDate = assetPropData;
            } else
            if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.ACQUISITION_COST.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                acquisitionCost = assetPropData;
            } else if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_IN_SERVICE.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                dateInService = assetPropData;
            } else
            if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.DATE_LAST_HOUR_METER_READING.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                dateLastHMR = assetPropData;
            } else
            if (RefCodeNames.ASSET_PROPERTY_TYPE_CD.LAST_HOUR_METER_READING.equals(assetPropData.getAssetPropertyCd())) {
                countProperties++;
                lastHMR = assetPropData;
            }
        }
        if (assetData != null) detailData.setAssetData(assetData);
        if (longDesc != null) detailData.setLongDesc(longDesc);
        if (customDesc != null) detailData.setCustomDesc(customDesc);
        if (dateInService != null) detailData.setDateInService(dateInService);
        if (inactiveReason != null) detailData.setInactiveReason(inactiveReason);
        if (acquisitionDate != null) detailData.setAcquisitionDate(acquisitionDate);
        if (acquisitionCost != null) detailData.setAcquisitionCost(acquisitionCost);
        if (lastHMR != null) detailData.setLastHMR(lastHMR);
        if (dateLastHMR != null) detailData.setDateLastHMR(dateLastHMR);

        return detailData;
    }

    /**
     * Gets asset data  collection
     *
     * @param criteria AssetSearchCriteria
     * @return AssetDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetDataVector getAssetDataByCriteria(AssetSearchCriteria criteria) throws RemoteException {
        Connection conn = null;
        AssetDataVector result = new AssetDataVector();
        try {

            conn = getConnection();
            DBCriteria crit = convertToDBCriteria(criteria);
            result = AssetDataAccess.select(conn, crit);

        } catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
        return result;
    }

    /**
     * Updates the Asset Assoc Collection .
     *
     * @param assetId               Asset Indentifier
     * @param pAssetAssocDataVector the  AssetAssocDataVector .
     * @param userName              user name
     * @return a <code>AssetAssocDataVector</code> values
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetAssocDataVector updateAssetAssocDataVector(int assetId, AssetAssocDataVector pAssetAssocDataVector, String userName) throws RemoteException {
        Connection conn = null;
        try {
            if (!Utility.isFail(verifyVector(assetId, pAssetAssocDataVector))) {
                conn = getConnection();
                return updateAssetAssocDataVector(conn, assetId, pAssetAssocDataVector, userName);
            }

        } catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
        return null;
    }

    private boolean verifyVector(int assetId, AssetAssocDataVector pAssetAssocDataVector) {
        if (pAssetAssocDataVector != null && !pAssetAssocDataVector.isEmpty() && assetId > 0) {
            Iterator it = pAssetAssocDataVector.iterator();
            while (it.hasNext()) {
                AssetAssocData assetAssocData = (AssetAssocData) it.next();
                if (assetAssocData.getAssetId() != assetId) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    private AssetAssocDataVector updateAssetAssocDataVector(Connection conn, int assetId, AssetAssocDataVector pNewAssetAssoc, String userName) throws Exception {

        AssetAssocDataVector updatedAssoc = new AssetAssocDataVector();
        AssetAssocDataVector currentAssetAssoc = getAllAssetAssociations(conn, assetId);
        HashMap statusMap = getChangesStatusMap(currentAssetAssoc, pNewAssetAssoc);

        AssetAssocDataVector delete = (AssetAssocDataVector) statusMap.get(RefCodeNames.CHANGE_STATUS.DELETE);
        AssetAssocDataVector insert = (AssetAssocDataVector) statusMap.get(RefCodeNames.CHANGE_STATUS.INSERT);
        AssetAssocDataVector update = (AssetAssocDataVector) statusMap.get(RefCodeNames.CHANGE_STATUS.UPDATE);

        Iterator it;

        //delete
        if (!delete.isEmpty()) {
            it = delete.iterator();
            IdVector ids = new IdVector();
            while (it.hasNext()) {
                AssetAssocData assetAssoc = (AssetAssocData) it.next();
                ids.add(new Integer(assetAssoc.getAssetAssocId()));
            }
            DBCriteria dbCiteria = new DBCriteria();
            dbCiteria.addOneOf(AssetAssocDataAccess.ASSET_ASSOC_ID, ids);
            AssetAssocDataAccess.remove(conn, dbCiteria);
        }

        //update
        it = update.iterator();
        while (it.hasNext()) {
            AssetAssocData assetAssoc = (AssetAssocData) it.next();
            if (assetAssoc.getAssetAssocId() > 0
                    && assetAssoc.getAssetId() > 0 &&
                    (assetAssoc.getBusEntityId() > 0 || assetAssoc.getItemId() > 0)) {
                assetAssoc.setModBy(userName);
                AssetAssocDataAccess.update(conn, assetAssoc);
            }
        }

        //insert
        it = insert.iterator();
        while (it.hasNext()) {
            AssetAssocData assetAssoc = (AssetAssocData) it.next();
            if (assetAssoc.getAssetAssocId() == 0
                    && assetAssoc.getAssetId() > 0
                    && (assetAssoc.getBusEntityId() > 0 || assetAssoc.getItemId() > 0)) {
                assetAssoc.setModBy(userName);
                assetAssoc.setAddBy(userName);
                assetAssoc = AssetAssocDataAccess.insert(conn, assetAssoc);
            }
        }

        updatedAssoc.addAll(insert);
        updatedAssoc.addAll(update);

        return updatedAssoc;
    }

    private HashMap getChangesStatusMap(AssetAssocDataVector pCurrentAssetAssoc, AssetAssocDataVector pNewAssetAssoc) {

        HashMap changesMap = new HashMap();

        AssetAssocDataVector insert = new AssetAssocDataVector();
        AssetAssocDataVector update = new AssetAssocDataVector();
        AssetAssocDataVector delete = new AssetAssocDataVector();

        changesMap.put(RefCodeNames.CHANGE_STATUS.DELETE, delete);
        changesMap.put(RefCodeNames.CHANGE_STATUS.INSERT, insert);
        changesMap.put(RefCodeNames.CHANGE_STATUS.UPDATE, update);


        if (pNewAssetAssoc.size() == 0 && pCurrentAssetAssoc.size() > 0) {

            delete.addAll(pCurrentAssetAssoc);

        } else if (pNewAssetAssoc.size() > 0 && pCurrentAssetAssoc.size() == 0) {

            insert.addAll(pNewAssetAssoc);

        } else {

            Iterator it = pCurrentAssetAssoc.iterator();
            while (it.hasNext()) {
                AssetAssocData currAssetAssoc = (AssetAssocData) it.next();
                Iterator it1 = pNewAssetAssoc.iterator();
                while (it1.hasNext()) {
                    AssetAssocData newAssetAssoc = (AssetAssocData) it1.next();
                    if ((newAssetAssoc.getAssetAssocId() == currAssetAssoc.getAssetAssocId())
                            || (currAssetAssoc.getAssetId() == newAssetAssoc.getAssetId()
                            && currAssetAssoc.getBusEntityId() == newAssetAssoc.getBusEntityId()
                            && currAssetAssoc.getItemId() == newAssetAssoc.getItemId())) {
                        if (Utility.strNN(currAssetAssoc.getAssetAssocCd()).equals(Utility.strNN(newAssetAssoc.getAssetAssocCd()))) {
                            update.add(newAssetAssoc);
                            it1.remove();
                            it.remove();
                            break;
                        }
                    }
                }
            }

            insert.addAll(pNewAssetAssoc);
            delete.addAll(pCurrentAssetAssoc);
        }
        return changesMap;
    }

    /**
     * Updates the Asset Assoc information values to be used by the request.
     *
     * @param conn            Connectin
     * @param pAssetAssocData the pAssetAssocData .
     * @param userName        user name
     * @return a <code>pAssetAssocData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    private AssetAssocData updateAssetAssocData(Connection conn, AssetAssocData pAssetAssocData, String userName) throws Exception {
        if (pAssetAssocData != null) {
            if (pAssetAssocData.getAssetAssocId() == 0
                    && pAssetAssocData.getAssetId() > 0 && (pAssetAssocData.getBusEntityId() > 0 || pAssetAssocData.getItemId() > 0)) {
                pAssetAssocData.setAddBy(userName);
                pAssetAssocData = AssetAssocDataAccess.insert(conn, pAssetAssocData);
            } else if (pAssetAssocData.getAssetAssocId() > 0
                    && pAssetAssocData.getAssetId() > 0 && (pAssetAssocData.getBusEntityId() > 0 || pAssetAssocData.getItemId() > 0)) {
                pAssetAssocData.setModBy(userName);
                AssetAssocDataAccess.update(conn, pAssetAssocData);
            }
        }
        return pAssetAssocData;

    }


    /**
     * Updates the Asset Property information values to be used by the request.
     *
     * @param conn               Connectin
     * @param pAssetPropertyData the AssetPropertyData .
     * @param propertyTypeCd     property type cd
     * @param assetId            asset id
     * @param userName           user name
     * @return a <code>AssetPropertData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetPropertyData updateAssetPropertyData(Connection conn,
                                                     AssetPropertyData pAssetPropertyData,
                                                     String propertyTypeCd,
                                                     int assetId,
                                                     String userName) throws Exception {
        if (pAssetPropertyData != null && pAssetPropertyData.getAssetPropertyCd() != null) {
            if (propertyTypeCd.equals(pAssetPropertyData.getAssetPropertyCd())) {
                if (assetId > 0) {
                    pAssetPropertyData.setAssetId(assetId);
                } else {
                    throw new Exception("Asset id must be greater 0");
                }
                pAssetPropertyData = updateAssetPropertyData(conn, pAssetPropertyData, userName);
                return pAssetPropertyData;

            } else {
                throw new Exception("Invalid type cd  " + propertyTypeCd);
            }
        }

        return pAssetPropertyData;
    }

    /**
     * Gets asset detail  view collection
     *
     * @param criteria AssetSearchCriteria
     * @return asset detail  view collection
     * @throws DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    public AssetDetailViewVector getAssetDetailViewVector(AssetSearchCriteria criteria) throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = convertToDBCriteria(criteria);
            AssetDataVector assetDV = AssetDataAccess.select(conn, crit);
            AssetDetailViewVector result = getAssetDetailViewVector(conn, assetDV);
            return result;

        } catch (Exception e) {
            throw processException(e);

        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Gets asset detail  view collection
     *
     * @param conn            Conection
     * @param assetDataVector assets collection
     * @return asset detail  view collection
     * @throws RemoteException Required by EJB 1.0
     */
    private AssetDetailViewVector getAssetDetailViewVector(Connection conn, AssetDataVector assetDataVector) throws Exception {
        AssetDetailViewVector assetDetailVV = new AssetDetailViewVector();
        if (assetDataVector != null) {
            Iterator it = assetDataVector.iterator();
            while (it.hasNext()) {
                AssetDetailView assetDetailView;
                AssetData assetData = (AssetData) it.next();
                AssetPropertyDataVector assetPropertyDV = null;

                assetPropertyDV = getAllAssetProperties(conn, assetData.getAssetId());

                AssetAssocDataVector assetAssocDV = null;
                AssetAssocDataVector parentAssetAssocDV = new AssetAssocDataVector();
                assetAssocDV = getAllAssetAssociations(conn, assetData.getAssetId());

                if (assetData.getParentId() > 0) {
                    parentAssetAssocDV = getAllAssetAssociations(conn, assetData.getParentId());
                }

                addParentAssocToChild(parentAssetAssocDV, assetAssocDV);

                assetDetailView = buildAssetDetailView(conn, assetData, assetPropertyDV, assetAssocDV);
                assetDetailVV.add(assetDetailView);
            }

        }

        return assetDetailVV;
    }

    private void addParentAssocToChild(AssetAssocDataVector parentAssetAssocDV, AssetAssocDataVector assetAssocDV) {


        if (assetAssocDV.size() == 0) {
            assetAssocDV.clear();
            assetAssocDV.addAll(parentAssetAssocDV);
            return;
        }
        AssetAssocDataVector inheritance = new AssetAssocDataVector();
        Iterator it = parentAssetAssocDV.iterator();
        while (it.hasNext()) {
            AssetAssocData parentAssocData = (AssetAssocData) it.next();
            Iterator it2 = assetAssocDV.iterator();
            while (it2.hasNext()) {
                AssetAssocData childAssocData = (AssetAssocData) it2.next();
                if (RefCodeNames.ASSET_ASSOC_CD.ASSET_SERVICE.equals(parentAssocData.getAssetAssocCd())) {
                    if (childAssocData.getItemId() != parentAssocData.getItemId()) {
                        inheritance.add(parentAssocData);
                    }
                } else if (RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE.equals(parentAssocData.getAssetAssocCd())) {
                    ;
                } else if (RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE.equals(parentAssocData.getAssetAssocCd())) {
                    ;
                }
            }
        }
        assetAssocDV.addAll(inheritance);
    }

    /**
     * builds AssetDetailView object
     *
     * @param conn            connection
     * @param assetData       asset data
     * @param assetPropertyDV asset property collection
     * @param assetAssocDV    asset assoc collection
     * @return AssetDetailView
     * @throws RemoteException Required by EJB 1.0
     */
    private AssetDetailView buildAssetDetailView(Connection conn,
                                                 AssetData assetData,
                                                 AssetPropertyDataVector assetPropertyDV,
                                                 AssetAssocDataVector assetAssocDV) throws Exception {
        AssetDetailView assetDetailView = AssetDetailView.createValue();
        IdVector siteIds = new IdVector();
        IdVector storeIds = new IdVector();

        Iterator iter = assetAssocDV.iterator();
        while (iter.hasNext()) {
            AssetAssocData assetAssocData = (AssetAssocData) iter.next();
            if (RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE.equals(assetAssocData.getAssetAssocCd())) {
                siteIds.add(new Integer(assetAssocData.getBusEntityId()));
            } else if (RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE.equals(assetAssocData.getAssetAssocCd())) {
                storeIds.add(new Integer(assetAssocData.getBusEntityId()));
            }
        }

        BusEntityDataVector siteAssoc = new BusEntityDataVector();
        if (siteIds.size() > 0) {
            siteAssoc = getBusEntityDataVector(conn, siteIds);
        }

        BusEntityDataVector storeAssoc = new BusEntityDataVector();
        if (storeIds.size() > 0) {
            storeAssoc = getBusEntityDataVector(conn, storeIds);
        }

        WarrantyDataVector warrantyAssoc   = getAssetWarrantyAssoc(conn, assetData.getAssetId());
        WorkOrderDataVector workOrderAssoc = getAssetWorkOrderAssoc(conn, assetData.getAssetId());
        AssetContentViewVector contents    = getAssetContents(conn, assetData.getAssetId());

        AddressData assetLocation = null;
        if (siteIds.size() > 0) {
            assetLocation = getAssetLocation(conn, assetData.getAssetId());
        }

        assetDetailView.setAssetDetailData(createAssetDetailData(assetData,assetPropertyDV));
        assetDetailView.setAssocDataVector(assetAssocDV);
        assetDetailView.setAssetSiteAssoc(siteAssoc);
        assetDetailView.setAssetStoreAssoc(storeAssoc);
        assetDetailView.setAssetWarrantyAssoc(warrantyAssoc);
        assetDetailView.setAssetWorkOrderAssoc(workOrderAssoc);
        assetDetailView.setContents(contents);
        assetDetailView.setLocation(assetLocation);

        return assetDetailView;
    }

    /**
     * Gets data collection from clw_bus_entity table
     *
     * @param conn         Connection
     * @param busEntityIds ids collection
     * @return BusEntityData   collection
     * @throws DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    private BusEntityDataVector getBusEntityDataVector(Connection conn, IdVector busEntityIds) throws Exception {
        return BusEntityDataAccess.select(conn, busEntityIds);
    }

    /**
     * Gets bus entity id from AssetAssocData collection
     *
     * @param assetAssocDataVector collection
     * @return IdVector
     */
    private IdVector getBusEntityIds(AssetAssocDataVector assetAssocDataVector) {
        IdVector ids = new IdVector();
        Iterator it = assetAssocDataVector.iterator();
        while (it.hasNext()) {
            AssetAssocData assetAssocData = (AssetAssocData) it.next();
            ids.add(new Integer(assetAssocData.getBusEntityId()));
        }
        return ids;
    }


    private AssetAssocDataVector getAllAssetAssociations(Connection conn, int assetId) throws Exception {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(AssetPropertyDataAccess.ASSET_ID, assetId);
        return AssetAssocDataAccess.select(conn, crit);
    }

    /**
     * gets  associations of an asset on AssetSearchCriteria
     *
     * @param assetId asset id
     * @param assocCd asset assoc cd
     * @return AssetAssocDataVector
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetAssocDataVector getAssetAssociations(int assetId, String assocCd) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ID, assetId);
            if (assocCd != null) {
                dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_CD, assocCd);
            }
            return AssetAssocDataAccess.select(conn, dbCriteria);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }


    /**
     * Gets a list of BusEntityData objects based off the supplied search criteria
     * object
     *
     * @param pCrit  Description of the Parameter
     * @param typeCd type cd
     * @return a set of SiteData objects
     * @throws RemoteException if an error occurs
     */
    public BusEntityDataVector getBusEntityByCriteria(BusEntitySearchCriteria pCrit, String typeCd) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return BusEntityDAO.getBusEntityByCriteria(conn, pCrit, typeCd);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

    }

    /**
     * removes  from clw_asset_assoc
     *
     * @param ids asset_assoc_ids collection
     * @return int Number of rows deleted.
     * @throws RemoteException Required by EJB 1.0
     */
    public int removeAssetAssoc(IdVector ids) throws RemoteException {
        Connection conn = null;
        try {
            if (ids != null && ids.size() > 0) {
                conn = getConnection();
                DBCriteria dbCriteria = new DBCriteria();
                dbCriteria.addOneOf(AssetAssocDataAccess.ASSET_ASSOC_ID, ids);
                int n = AssetAssocDataAccess.remove(conn, dbCriteria);
                return n;
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return 0;
    }

    /**
     * removes data from clw_asset_assoc
     *
     * @param pAssetIds     asset ids
     * @param pAssetAssocCd asset assoc cd
     * @param pAssocId      bus_entity_id or item_id
     * @return int Number of rows deleted.
     * @throws RemoteException Required by EJB 1.0
     */
    public int removeAssetAssoc(IdVector pAssetIds, int pAssocId, String pAssetAssocCd) throws RemoteException {

        Connection conn = null;
        try {
            if (pAssetIds != null && pAssetIds.size() > 0) {
                conn = getConnection();
                DBCriteria dbCriteria = new DBCriteria();

                if (RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE.equals(pAssetAssocCd)) {
                    dbCriteria.addEqualTo(AssetAssocDataAccess.BUS_ENTITY_ID, pAssocId);
                } else if (RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE.equals(pAssetAssocCd)) {
                    dbCriteria.addEqualTo(AssetAssocDataAccess.BUS_ENTITY_ID, pAssocId);
                } else if (RefCodeNames.ASSET_ASSOC_CD.ASSET_SERVICE.equals(pAssetAssocCd)) {
                    dbCriteria.addEqualTo(AssetAssocDataAccess.ITEM_ID, pAssocId);
                } else {
                    throw new Exception("Unknown asset assoc type code : " + pAssetAssocCd);
                }

                dbCriteria.addOneOf(AssetAssocDataAccess.ASSET_ID, pAssetIds);
                dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_CD, pAssetAssocCd);
                int n = AssetAssocDataAccess.remove(conn, dbCriteria);
                return n;
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return 0;
    }

    /**
     * gets site id with which  link is active
     *
     * @param assetId asset id
     * @param conn    connection
     * @return site id
     * @throws DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    private int getSiteIdAssetLocation(Connection conn, int assetId) throws Exception {

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ID, assetId);
        dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
        dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
        IdVector siteIds = AssetAssocDataAccess.selectIdOnly(conn, AssetAssocDataAccess.BUS_ENTITY_ID, dbCriteria);

        if (siteIds.size() > 1) {
            throw new RemoteException("multiple asset location");
        } else if (siteIds.isEmpty()) {
            throw new DataNotFoundException("asset location not found");
        } else {
            return ((Integer) siteIds.get(0)).intValue();
        }
    }

    /**
     * gets site id with which link is active
     *
     * @param assetId asset id
     * @return site id
     * @throws DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    public int getAssetLocationSiteId(int assetId) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getSiteIdAssetLocation(conn, assetId);
        }
        catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        finally {
            closeConnection(conn);
        }
    }

    /**
     * locates asset
     *
     * @param assetId assetid
     * @return site address data
     * @throws RemoteException Required by EJB 1.0
     */
    public AddressData getAssetLocation(int assetId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAssetLocation(conn, assetId);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * locates asset
     *
     * @param assetId assetid
     * @param conn    Connection
     * @return site address data
     * @throws RemoteException Required by EJB 1.0
     */
    private AddressData getAssetLocation(Connection conn, int assetId) throws RemoteException {
        try {
            int siteId = getSiteIdAssetLocation(conn, assetId);
            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, siteId);
            dbCriteria.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
            AddressDataVector address = AddressDataAccess.select(conn, dbCriteria);
            if (address != null && address.size() > 1) {
                throw new RemoteException("multiple address data");
            }

            if (address != null && address.size() != 0) {
                return (AddressData) address.get(0);
            }
        } catch (DataNotFoundException e) {
            logInfo(e.getMessage());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        return null;
    }

    /**
     * to move asset location
     *
     * @param assetId     asset id
     * @param oldLocation old site id
     * @param newLocation new site id
     * @param userName    user name
     * @return success flag
     * @throws RemoteException Required by EJB 1.0
     */
    public boolean moveAssetToSite(int assetId, int oldLocation, int newLocation, String userName) throws RemoteException {
        boolean successFlag = false;
        Connection conn = null;
        try {
            conn = getConnection();
            if (assetId > 0) {
                DBCriteria dbCriteria = new DBCriteria();
                if (oldLocation > 0) {
                    dbCriteria.addEqualTo(AssetAssocDataAccess.BUS_ENTITY_ID, oldLocation);
                }
                dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
                dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
                dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ID, assetId);

                AssetAssocDataVector oldAssetAssoc = AssetAssocDataAccess.select(conn, dbCriteria);
                if (oldAssetAssoc == null) {
                    throw new RemoteException("Data is null");
                }
                if (oldAssetAssoc.size() > 1) {
                    throw new RemoteException("multiple asset location");
                } else if (oldAssetAssoc.size() == 1) {
                    AssetAssocData assetAssoc = (AssetAssocData) oldAssetAssoc.get(0);
                    assetAssoc.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.INACTIVE);
                    updateAssetAssocData(conn, assetAssoc, userName);
                }

                dbCriteria = new DBCriteria();
                if (newLocation > 0) {
                    dbCriteria.addEqualTo(AssetAssocDataAccess.BUS_ENTITY_ID, newLocation);
                    dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_SITE);
                    dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.INACTIVE);
                    dbCriteria.addEqualTo(AssetAssocDataAccess.ASSET_ID, assetId);
                    AssetAssocDataVector newAssetAssoc = AssetAssocDataAccess.select(conn, dbCriteria);
                    if (newAssetAssoc == null) {
                        throw new RemoteException("Data is null");
                    }
                    if (newAssetAssoc.size() > 1) {
                        throw new RemoteException("multiple asset associations");
                    } else if (newAssetAssoc.size() == 1) {
                        AssetAssocData assetAssoc = (AssetAssocData) newAssetAssoc.get(0);
                        assetAssoc.setAssetAssocStatusCd(RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
                        updateAssetAssocData(conn, assetAssoc, userName);
                    } else if (oldAssetAssoc.size() == 0 && newLocation > 0) {
                        throw new RemoteException("it impossible location.site is not assign.siteId : " + newLocation);
                    }
                }

                successFlag = true;
            }

        }
        catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return successFlag;
    }

    /**
     * Gets asset data  collection
     *
     * @param assetIds the asset ids
     * @return AssetDataVector
     * @throws com.cleanwise.service.api.util.DataNotFoundException
     *                         Data Not Found Exception
     * @throws RemoteException Required by EJB 1.0
     */
    public AssetDataVector getAssetCollectionByIds(IdVector assetIds) throws RemoteException, DataNotFoundException {
        Connection conn = null;
        AssetDataVector assetDV = new AssetDataVector();
        try {
            conn = getConnection();
            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addEqualTo(AssetDataAccess.ASSET_ID, assetIds);
            assetDV = AssetDataAccess.select(conn, dbCriteria);


        }
        catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return assetDV;
    }

    public WarrantyDataVector getAssetWarrantyAssoc(int assetId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAssetWarrantyAssoc(conn, assetId);
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
    }

    private WarrantyDataVector getAssetWarrantyAssoc(Connection conn, int assetId) throws SQLException {

        DBCriteria dbCriteria = new DBCriteria();

        dbCriteria.addEqualTo(AssetWarrantyDataAccess.ASSET_ID, assetId);
        IdVector warrantyIds = AssetWarrantyDataAccess.selectIdOnly(conn, AssetWarrantyDataAccess.WARRANTY_ID, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(WarrantyDataAccess.WARRANTY_ID, warrantyIds);
        WarrantyDataVector warranties = WarrantyDataAccess.select(conn, dbCriteria);

        return warranties;

    }

    public WorkOrderDataVector getAssetWorkOrderAssoc(int assetId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAssetWorkOrderAssoc(conn, assetId);
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
    }

    private WorkOrderDataVector getAssetWorkOrderAssoc(Connection conn, int assetId) throws SQLException {

        DBCriteria dbCriteria = new DBCriteria();

        dbCriteria.addEqualTo(WorkOrderAssetDataAccess.ASSET_ID, assetId);
        IdVector woiIds = WorkOrderAssetDataAccess.selectIdOnly(conn, WorkOrderAssetDataAccess.WORK_ORDER_ITEM_ID, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(WorkOrderItemDataAccess.WORK_ORDER_ITEM_ID, woiIds);
        IdVector workOrderIds = WorkOrderItemDataAccess.selectIdOnly(conn, WorkOrderItemDataAccess.WORK_ORDER_ID, dbCriteria);

        dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(WorkOrderDataAccess.WORK_ORDER_ID, workOrderIds);
        WorkOrderDataVector workOrders = WorkOrderDataAccess.select(conn, dbCriteria);

        return workOrders;

    }

    private AssetContentViewVector getAssetContents(Connection conn, int assetId) throws SQLException {

        String sql = "SELECT C.BUS_ENTITY_ID," +
                "C.CONTENT_ID," +
                "C.ITEM_ID," +
                "C.ADD_BY," +
                "C.ADD_DATE," +
                "C.CONTENT_STATUS_CD," +
                "C.CONTENT_TYPE_CD," +
                "C.CONTENT_USAGE_CD," +
                "C.EFF_DATE," +
                "C.EXP_DATE," +
                "C.LANGUAGE_CD," +
                "C.LOCALE_CD," +
                "C.LONG_DESC," +
                "C.MOD_BY," +
                "C.MOD_DATE," +
                "C.PATH," +
                "C.SHORT_DESC," +
                "C.SOURCE_CD," +
                "C.VERSION," +
                "AC.ASSET_CONTENT_ID," +
                "AC.ASSET_ID," +
                "AC.URL," +
                "AC.TYPE_CD," +
                "AC.MOD_BY," +
                "AC.MOD_DATE," +
                "AC.ADD_BY," +
                "AC.ADD_DATE " +
                " FROM CLW_CONTENT C,CLW_ASSET_CONTENT AC " +
                "   WHERE AC.ASSET_ID = ?" +
                "   AND AC.CONTENT_ID = C.CONTENT_ID";

        logInfo("getAssetContents => sql:" + sql);
        logInfo("getAssetContents => param[assetId]:" + assetId);

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,assetId);

        ResultSet rs = pstmt.executeQuery();

        AssetContentViewVector v = new AssetContentViewVector();
        while (rs.next()) {
            ContentView c = ContentView.createValue();
            AssetContentData ac = AssetContentData.createValue();

            c.setBusEntityId(rs.getInt(1));
            c.setContentId(rs.getInt(2));
            c.setItemId(rs.getInt(3));
            c.setAddBy(rs.getString(4));
            c.setAddDate(rs.getTimestamp(5));
            c.setContentStatusCd(rs.getString(6));
            c.setContentTypeCd(rs.getString(7));
            c.setContentUsageCd(rs.getString(8));
            c.setEffDate(rs.getDate(9));
            c.setExpDate(rs.getDate(10));
            c.setLanguageCd(rs.getString(11));
            c.setLocaleCd(rs.getString(12));
            c.setLongDesc(rs.getString(13));
            c.setModBy(rs.getString(14));
            c.setModDate(rs.getTimestamp(15));
            c.setPath(rs.getString(16));
            c.setShortDesc(rs.getString(17));
            c.setSourceCd(rs.getString(18));
            c.setVersion(rs.getInt(19));

            ac.setAssetContentId(rs.getInt(20));
            ac.setAssetId(rs.getInt(21));
            ac.setUrl(rs.getString(22));
            ac.setTypeCd(rs.getString(23));

            ac.setModBy(rs.getString(24));
            ac.setModDate(rs.getTimestamp(25));
            ac.setAddBy(rs.getString(26));
            ac.setAddDate(rs.getTimestamp(27));

            v.add(new AssetContentView(c, ac));
        }

        rs.close();
        pstmt.close();

        return v;
    }

    public AssetContentViewVector getAssetContents(int assetId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAssetContents(conn, assetId);
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
    }

    public AssetContentDetailView getAssetContentDetails(int assetContentId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAssetContentDetails(conn, assetContentId);
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
    }

    private AssetContentDetailView getAssetContentDetails(Connection conn, int assetContentId) throws Exception {
        AssetContentData assetContent = AssetContentDataAccess.select(conn, assetContentId);
        Content contentEjb = APIAccess.getAPIAccess().getContentAPI();
        ContentDetailView contentDetails = contentEjb.getContentDetailView(assetContent.getContentId());
        return new AssetContentDetailView(contentDetails, assetContent);
    }

    public AssetContentDetailView getAssetContentDetails(DBCriteria dbc) throws RemoteException {
      Connection conn = null;
      try {
         conn = getConnection();
         IdVector acIV = AssetContentDataAccess.selectIdOnly(conn, dbc);
         if (acIV == null || acIV.size() == 0){
           return null;
         } else if (acIV.size() > 1) {
           throw new Exception("Multiple asset content found");
         }
         int assetContentId = ((Integer)acIV.get(0)).intValue();
         return getAssetContentDetails(conn, assetContentId);
      }
      catch (Exception e) {
         throw processException(e);
      }
      finally {
         closeConnection(conn);
      }
   }

    public AssetContentDetailView updateAssetContent(AssetContentDetailView assetContent, UserData user) throws RemoteException {
        Connection conn = null;
        try {
            if (assetContent != null) {
                conn = getConnection();
                return updateAssetContent(conn, assetContent, user);
            }

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return assetContent;
    }

    private AssetContentDetailView updateAssetContent(Connection conn, AssetContentDetailView assetContent, UserData user) throws Exception {

        if (assetContent != null && assetContent.getContent() != null && assetContent.getAssetContentData() != null) {

            Content contentEjb = APIAccess.getAPIAccess().getContentAPI();
            ContentDetailView content = contentEjb.updateContent(assetContent.getContent(), user);
            assetContent.getAssetContentData().setContentId(content.getContentId());

            if (assetContent.getAssetContentData().getAssetContentId() <= 0) {
                assetContent.getAssetContentData().setAddBy(user.getUserName());
                assetContent.getAssetContentData().setModBy(user.getUserName());
                AssetContentData aC = AssetContentDataAccess.insert(conn, assetContent.getAssetContentData());
                assetContent.setAssetContentData(aC);
            } else {
                assetContent.getAssetContentData().setModBy(user.getUserName());
                AssetContentDataAccess.update(conn, assetContent.getAssetContentData());
            }
            assetContent = new AssetContentDetailView(content, assetContent.getAssetContentData());
        }
        return assetContent;
    }

    public boolean removeAssetContent(int acId, int contentId) throws RemoteException {
        Connection conn = null;
        try {
            if (acId > 0) {
                conn = getConnection();
                return removeAssetContent(conn, acId, contentId);
            }

        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return false;
    }

    private boolean removeAssetContent(Connection conn, int acId, int contentId) throws SQLException {
        try {
            logInfo("removeAssetContent => acId:" + acId + " contentId:" + contentId);
            if (contentId <= 0) {
                AssetContentData acData = AssetContentDataAccess.select(conn, acId);
                acId = acData.getAssetContentId();
                contentId = acData.getContentId();
                logInfo("removeAssetContent => now acId:" + acId + " contentId:" + contentId);
            }
            AssetContentDataAccess.remove(conn, acId);
            if (contentId > 0) {
                ContentDataAccess.remove(conn, contentId);
            }
            return true;
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BusEntityDataVector getMasterAssetLinkedStores(int assetId) throws RemoteException {
        BusEntityDataVector maLinkedStores = new BusEntityDataVector();
        Connection conn = null;
        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            crit.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC);
            crit.addJoinTable(AssetMasterAssocDataAccess.CLW_ASSET_MASTER_ASSOC);

            DBCriteria isolCrit = new DBCriteria();
            isolCrit.addCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID + "=" +
                                  AssetAssocDataAccess.CLW_ASSET_ASSOC + "." + AssetAssocDataAccess.BUS_ENTITY_ID);
            isolCrit.addEqualTo(AssetAssocDataAccess.CLW_ASSET_ASSOC + "." + AssetAssocDataAccess.ASSET_ASSOC_CD,
                                RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
            isolCrit.addCondition(AssetAssocDataAccess.CLW_ASSET_ASSOC + "." + AssetAssocDataAccess.ASSET_ID + "=" +
                                AssetMasterAssocDataAccess.CLW_ASSET_MASTER_ASSOC + "." + AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID);
            isolCrit.addEqualTo(AssetAssocDataAccess.CLW_ASSET_ASSOC + "." + AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD,
                                RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
            isolCrit.addEqualTo(AssetMasterAssocDataAccess.CLW_ASSET_MASTER_ASSOC + "." + AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID,
                                assetId);
            isolCrit.addEqualTo(AssetMasterAssocDataAccess.CLW_ASSET_MASTER_ASSOC + "." + AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_STATUS_CD,
                                RefCodeNames.ASSET_STATUS_CD.ACTIVE);
            crit.addIsolatedCriterita(isolCrit);

            BusEntityDataVector linkedStores = BusEntityDataAccess.select(conn, crit);

            HashMap linkedStoresMap = new HashMap();
            Iterator it = linkedStores.iterator();
            BusEntityData beD;
            while (it.hasNext()) {
                beD = (BusEntityData) it.next();
                linkedStoresMap.put(Integer.valueOf(beD.getBusEntityId()), beD);
            }
            maLinkedStores.addAll(linkedStoresMap.values());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.toString());
        } finally {
            closeConnection(conn);
        }
        return maLinkedStores;
    }

    public BusEntityDataVector getMasterItemLinkedStores(int itemId) throws RemoteException {
        BusEntityDataVector maLinkedStores = new BusEntityDataVector();
        Connection conn = null;
        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            crit.addJoinTable(ItemAssocDataAccess.CLW_ITEM_ASSOC);
            crit.addJoinTable(ItemMasterAssocDataAccess.CLW_ITEM_MASTER_ASSOC);
            crit.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC);


            DBCriteria isolCrit = new DBCriteria();
            isolCrit.addCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID + "=" +
                                  CatalogAssocDataAccess.CLW_CATALOG_ASSOC + "." + CatalogAssocDataAccess.BUS_ENTITY_ID);
            isolCrit.addEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC + "." + CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

            isolCrit.addCondition(CatalogAssocDataAccess.CLW_CATALOG_ASSOC + "." + CatalogAssocDataAccess.CATALOG_ID + "=" +
                                  ItemAssocDataAccess.CLW_ITEM_ASSOC + "." + ItemAssocDataAccess.CATALOG_ID);
            isolCrit.addCondition(ItemAssocDataAccess.CLW_ITEM_ASSOC + "." + ItemAssocDataAccess.ITEM1_ID + "=" +
                                  ItemMasterAssocDataAccess.CLW_ITEM_MASTER_ASSOC + "." + ItemMasterAssocDataAccess.CHILD_MASTER_ITEM_ID);

            isolCrit.addEqualTo(ItemMasterAssocDataAccess.CLW_ITEM_MASTER_ASSOC + "." + ItemMasterAssocDataAccess.PARENT_MASTER_ITEM_ID,
                                itemId);
            isolCrit.addEqualTo(ItemMasterAssocDataAccess.CLW_ITEM_MASTER_ASSOC + "." + ItemMasterAssocDataAccess.ITEM_MASTER_ASSOC_STATUS_CD,
                                RefCodeNames.ITEM_STATUS_CD.ACTIVE);
            crit.addIsolatedCriterita(isolCrit);

            BusEntityDataVector linkedStores = BusEntityDataAccess.select(conn, crit);

            HashMap linkedStoresMap = new HashMap();
            Iterator it = linkedStores.iterator();
            BusEntityData beD;
            while (it.hasNext()) {
                beD = (BusEntityData) it.next();
                linkedStoresMap.put(Integer.valueOf(beD.getBusEntityId()), beD);
            }
            maLinkedStores.addAll(linkedStoresMap.values());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.toString());
        } finally {
            closeConnection(conn);
        }
        return maLinkedStores;
    }

    public AssetViewVector getStagedAssetVector (AssetSearchCriteria criteria) throws DataNotFoundException, RemoteException {
        StagedAssetDataVector stagedDV = new StagedAssetDataVector();
        AssetViewVector stagedVV = new AssetViewVector();
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCriteria = new DBCriteria();
            //convertToDBCriteria(criteria);
            if (Utility.isSet(criteria.getAssetName())) {
                String assetName = criteria.getAssetName();
                if (criteria.getSearchNameTypeCd() != null) {
                    String searchNameType = criteria.getSearchNameTypeCd();
                    if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchNameType)) {
                        if(criteria.getIgnoreCase()){
                            dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.ASSET_NAME, assetName);
                        } else {
                            dbCriteria.addBeginsWith(StagedAssetDataAccess.ASSET_NAME, assetName);
                        }
                    } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchNameType)) {
                        if(criteria.getIgnoreCase()) {
                            dbCriteria.addContainsIgnoreCase(StagedAssetDataAccess.ASSET_NAME, assetName);
                        } else{
                            dbCriteria.addContains(StagedAssetDataAccess.ASSET_NAME, assetName);
                        }
                    } else {
                        if(criteria.getIgnoreCase()) {
                            dbCriteria.addEqualToIgnoreCase(StagedAssetDataAccess.ASSET_NAME, assetName);
                        } else {
                            dbCriteria.addEqualTo(StagedAssetDataAccess.ASSET_NAME, assetName);
                        }
                    }
                } else {
                    if(criteria.getIgnoreCase()) {
                        dbCriteria.addEqualToIgnoreCase(StagedAssetDataAccess.ASSET_NAME, assetName);
                    } else {
                        dbCriteria.addEqualTo(StagedAssetDataAccess.ASSET_NAME, assetName);
                    }
                }
            }

            if (Utility.isSet(criteria.getCategoryName())) {
                dbCriteria.addContainsIgnoreCase(StagedAssetDataAccess.CATEGORY_NAME, criteria.getCategoryName());
            }

            if (criteria.getManufName() != null) {
                dbCriteria.addContainsIgnoreCase(StagedAssetDataAccess.MANUFACTURER, criteria.getManufName());
            }

            if (criteria.getManufSku() != null) {
                String manufSku = criteria.getManufSku();
                if (criteria.getSearchManufSkuTypeCd() != null) {
                    String searchManufSkuType = criteria.getSearchManufSkuTypeCd();
                    if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchManufSkuType)) {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.MFG_SKU, manufSku);
                        } else {
                            dbCriteria.addBeginsWith(StagedAssetDataAccess.MFG_SKU, manufSku);
                        }
                    } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchManufSkuType)) {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addContainsIgnoreCase(StagedAssetDataAccess.MFG_SKU, manufSku);
                        } else {
                            dbCriteria.addContains(StagedAssetDataAccess.MFG_SKU, manufSku);
                        }
                    } else if (RefCodeNames.SEARCH_TYPE_CD.EQUALS.equals(searchManufSkuType)) {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addEqualToIgnoreCase(StagedAssetDataAccess.MFG_SKU, manufSku);
                        } else {
                            dbCriteria.addEqualTo(StagedAssetDataAccess.MFG_SKU, manufSku);
                        }
                    } else {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.MFG_SKU, manufSku);
                        } else {
                            dbCriteria.addBeginsWith(StagedAssetDataAccess.MFG_SKU, manufSku);
                        }
                    }
                } else {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.MFG_SKU, manufSku);
                    } else {
                        dbCriteria.addBeginsWith(StagedAssetDataAccess.MFG_SKU, manufSku);
                    }
                }
            }

            if (criteria.getModelNumber() != null) {
                String modelNumber = criteria.getModelNumber();
                if (criteria.getSearchModelTypeCd() != null) {
                    String searchModelType = criteria.getSearchModelTypeCd();
                    if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchModelType)) {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        } else {
                            dbCriteria.addBeginsWith(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        }
                    } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchModelType)) {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addContainsIgnoreCase(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        } else {
                            dbCriteria.addContains(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        }
                    } else if (RefCodeNames.SEARCH_TYPE_CD.EQUALS.equals(searchModelType)) {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addEqualToIgnoreCase(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        } else {
                            dbCriteria.addEqualTo(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        }
                    } else {
                        if (criteria.getIgnoreCase()) {
                            dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        } else {
                            dbCriteria.addBeginsWith(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                        }
                    }
                } else {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                    } else {
                        dbCriteria.addBeginsWith(StagedAssetDataAccess.MODEL_NUMBER, modelNumber);
                    }
                }
            }

            if (criteria.getAssetNumber() != null) {
                String stagedAssetId = criteria.getAssetNumber();
                if (criteria.getSearchNumberTypeCd() != null) {
                    String searchNumberType = criteria.getSearchNumberTypeCd();
                    if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(searchNumberType)) {
                        if(criteria.getIgnoreCase()){
                            dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        }else{
                            dbCriteria.addBeginsWith(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        }
                    } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(searchNumberType)) {
                        if(criteria.getIgnoreCase()){
                            dbCriteria.addContainsIgnoreCase(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        } else{
                            dbCriteria.addContains(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        }
                    } else if (RefCodeNames.SEARCH_TYPE_CD.EQUALS.equals(searchNumberType)){
                        if(criteria.getIgnoreCase()){
                            dbCriteria.addEqualToIgnoreCase(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        } else {
                            dbCriteria.addEqualTo(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        }
                    } else {
                        if(criteria.getIgnoreCase()){
                            dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        } else {
                            dbCriteria.addBeginsWith(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                        }
                    }
                } else {
                    if (criteria.getIgnoreCase()) {
                        dbCriteria.addBeginsWithIgnoreCase(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                    } else {
                        dbCriteria.addBeginsWith(StagedAssetDataAccess.STAGED_ASSET_ID, stagedAssetId);
                    }
                }
            }

            /*
            if (criteria.getStagedSearchType() != null) {
                String stagedSearchType = criteria.getStagedSearchType();
                if (RefCodeNames.STAGED_ASSETS_SEARCH_TYPE_CD.MATCHED.equals(stagedSearchType)) {
                    dbCriteria.addGreaterThan(StagedAssetDataAccess.MATCHED_ASSET_ID, 0);
                } else if (RefCodeNames.STAGED_ASSETS_SEARCH_TYPE_CD.NOT_MATCHED.equals(stagedSearchType)) {
                    dbCriteria.addEqualTo("NVL(" +StagedAssetDataAccess.MATCHED_ASSET_ID+",0)", 0);
                }
            }
            */

            dbCriteria.addEqualToIgnoreCase(StagedAssetDataAccess.ASSET, "TRUE");

            if (criteria.getStoreIds() != null) {
                dbCriteria.addEqualTo(StagedAssetDataAccess.STORE_ID, criteria.getStoreIds().get(0));
            }
            stagedDV = StagedAssetDataAccess.select(conn, dbCriteria);

            Iterator it = stagedDV.iterator();
            StagedAssetData assetD;
            AssetView assetV;
            String actionStr;
            while (it.hasNext()) {
                assetD = (StagedAssetData)it.next();
                assetV = AssetView.createValue();

                assetV.setAssetId(assetD.getStagedAssetId());
                //assetV.setParentId(assetD.getgetParentId());
                //assetV.setManufId(assetD.getManufId());
                assetV.setManufName(assetD.getManufacturer());
                assetV.setManufSku(assetD.getMfgSku());
                //assetV.setManufType(assetD.getManufTypeCd());
                //assetV.setAssetTypeCd(assetD.getStagedAssetTypeCd());
                assetV.setAssetName(assetD.getAssetName());
                //assetV.setAssetNumber(assetD.getAssetNum());
                //assetV.setSerialNumber(assetD.getSerialNum());
                assetV.setModelNumber(assetD.getModelNumber());
                assetV.setMatchedAssetId(assetD.getMatchedAssetId());
                assetV.setAssetCategory(assetD.getCategoryName());
                actionStr = assetD.getAction();
                if (Utility.isSet(actionStr)) {
                    if (actionStr.equals("A") || actionStr.equals("C")) {
                        assetV.setStatus(RefCodeNames.ASSET_STATUS_CD.ACTIVE);
                    } else {
                        assetV.setStatus(RefCodeNames.ASSET_STATUS_CD.INACTIVE);
                    }
                } else {
                    assetV.setStatus(RefCodeNames.ASSET_STATUS_CD.INACTIVE);
                }

                stagedVV.add(assetV);
            }
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(conn);
        }
        return stagedVV;
    }
    /**
     * Updates the Staged Asset information values to be used by the request.
     *
     * @param pAssetData the StagedAssetData.
     * @param userName   user name
     * @return a <code>StagedAssetData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public StagedAssetData updateStagedAssetData(StagedAssetData pAssetData, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            updateStagedAssetData(conn, pAssetData, userName);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pAssetData;
    }

    /**
     * Updates the Staged Asset information values to be used by the request.
     *
     * @param conn       Connection
     * @param pAssetData the StagedAssetData.
     * @param userName   user name
     * @return a <code>StagedAssetData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    private StagedAssetData updateStagedAssetData(Connection conn, StagedAssetData pAssetData, String userName) throws RemoteException {

        try {
            StagedAssetData data;
            data = pAssetData;
            if (data.isDirty()) {
                if (data.getStagedAssetId() == 0) {
                    data = StagedAssetDataAccess.insert(conn, data);
                } else {
                    StagedAssetDataAccess.update(conn, data);
                }
            }
            return data;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the Staged Asset Assoc information values to be used by the request.
     *
     * @param pAssetAssocData the StagedAssocAssetData.
     * @param userName   user name
     * @return a <code>StagedAssetData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public StagedAssetAssocData updateStagedAssetAssocData(StagedAssetAssocData pAssetAssocData, String userName) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            updateStagedAssetAssocData(conn, pAssetAssocData, userName);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pAssetAssocData;
    }

    /**
     * Updates the Staged Asset Assoc information values to be used by the request.
     *
     * @param conn            Connection
     * @param pAssetAssocData the pAssetAssocData.
     * @param userName        user name
     * @return a <code>pAssetAssocData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    private StagedAssetAssocData updateStagedAssetAssocData(Connection conn, StagedAssetAssocData pAssetAssocData, String userName) throws Exception {
        if (pAssetAssocData != null) {
            if (pAssetAssocData.getStagedAssetAssocId() == 0
                    && pAssetAssocData.getStagedAssetId() > 0 && pAssetAssocData.getBusEntityId() > 0 ) {
                pAssetAssocData.setAddBy(userName);
                pAssetAssocData = StagedAssetAssocDataAccess.insert(conn, pAssetAssocData);
            } else if (pAssetAssocData.getStagedAssetAssocId() > 0
                    && pAssetAssocData.getStagedAssetId() > 0 && pAssetAssocData.getBusEntityId() > 0) {
                pAssetAssocData.setModBy(userName);
                StagedAssetAssocDataAccess.update(conn, pAssetAssocData);
            }
        }
        return pAssetAssocData;
    }

    public StagedAssetAssocDataVector getStagedAssetAssocDataVector(AssetSearchCriteria criteria) throws RemoteException {
        Connection conn = null;
        StagedAssetAssocDataVector pAssetAssocDV = new StagedAssetAssocDataVector();
        try {
            conn = getConnection();
            pAssetAssocDV = getStagedAssetAssocDataVector(conn, criteria);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return pAssetAssocDV;
    }

    private StagedAssetAssocDataVector getStagedAssetAssocDataVector(Connection conn, AssetSearchCriteria criteria) throws Exception {
        DBCriteria crit = new DBCriteria();

        if (criteria.getAssetId() > 0) {
            crit.addEqualTo(StagedAssetAssocDataAccess.STAGED_ASSET_ID, criteria.getAssetId());
        }
        if (criteria.getBusEntityIds() != null) {
            crit.addOneOf(StagedAssetAssocDataAccess.BUS_ENTITY_ID, criteria.getBusEntityIds());
        }
        if (criteria.getAssocTypeCd() != null) {
            crit.addEqualTo(StagedAssetAssocDataAccess.ASSOC_CD, criteria.getAssocTypeCd());
        }
        if (criteria.getStatusCd() != null) {
            crit.addEqualTo(StagedAssetAssocDataAccess.ASSOC_STATUS_CD, criteria.getStatusCd());
        }
        return StagedAssetAssocDataAccess.select(conn, crit);
    }

    public StagedAssetData getStagedAssetData(int stagedAssetId) throws RemoteException {
        Connection conn = null;
        StagedAssetData stagedAsset = null;
        try {
            conn = getConnection();
            stagedAsset = getStagedAssetData(conn, stagedAssetId);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return stagedAsset;
    }

    private StagedAssetData getStagedAssetData(Connection conn, int stagedAssetId) throws Exception {
        if (stagedAssetId > 0) {
            return StagedAssetDataAccess.select(conn, stagedAssetId);
        } else {
            return null;
        }
    }

    public boolean canEditMasterAsset(int assetId, int storeId) throws RemoteException {
        Connection conn = null;
        boolean canEditFlag = true;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID, assetId);
            crit.addJoinTable(AssetMasterAssocDataAccess.CLW_ASSET_MASTER_ASSOC);
            crit.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " CHILD_STORE");
            crit.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " PARENT_STORE");
            crit.addJoinTable(PropertyDataAccess.CLW_PROPERTY);

            DBCriteria isolCrit = new DBCriteria();
            isolCrit.addEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.BUS_ENTITY_ID, storeId);
            isolCrit.addEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.PARENT_STORE_ID);
            isolCrit.addNotEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.CLW_VALUE, 0);
            isolCrit.addEqualTo(PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            isolCrit.addEqualTo("CHILD_STORE." + AssetAssocDataAccess.ASSET_ID, assetId);
            isolCrit.addEqualTo("CHILD_STORE." + AssetAssocDataAccess.BUS_ENTITY_ID, storeId);
            isolCrit.addEqualTo("CHILD_STORE." + AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
            isolCrit.addEqualTo("CHILD_STORE." + AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);

            DBCriteria parentAssetsCrit = new DBCriteria();
            parentAssetsCrit.addEqualTo(AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID, assetId);
            parentAssetsCrit.addEqualTo(AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);

            String parentAssetsSQL = AssetMasterAssocDataAccess.getSqlSelectIdOnly(AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID, parentAssetsCrit);

            isolCrit.addOneOf("PARENT_STORE." + AssetAssocDataAccess.ASSET_ID, parentAssetsSQL);
            isolCrit.addCondition("PARENT_STORE." + AssetAssocDataAccess.BUS_ENTITY_ID + "=" + PropertyDataAccess.CLW_PROPERTY + "." + PropertyDataAccess.CLW_VALUE);
            isolCrit.addEqualTo("PARENT_STORE." + AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
            isolCrit.addEqualTo("PARENT_STORE." + AssetAssocDataAccess.ASSET_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);

            crit.addIsolatedCriterita(isolCrit);

            AssetMasterAssocDataVector parentMasterAssets = AssetMasterAssocDataAccess.select(conn, crit);
            if (!parentMasterAssets.isEmpty()) {
                canEditFlag = false;
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return canEditFlag;
    }

    public IdVector checkAssetStoreUnique(AssetSearchCriteria criteria) throws RemoteException {
        IdVector assetIds = new IdVector();
        DBCriteria dbCriteria = new DBCriteria();
        ArrayList manufNames = new ArrayList();
        Connection conn = null;
        try {
            conn = getConnection();
            if (Utility.isSet(criteria.getManufName())) {
                manufNames.add(criteria.getManufName());
            }

            if (criteria.getManufOtherNames() != null) {
                String[] manufOtherNames = criteria.getManufOtherNames();
                for (int i = 0; i < manufOtherNames.length; i++) {
                    manufNames.add(manufOtherNames[i]);
                }
            }

            if (!manufNames.isEmpty()) {
                dbCriteria.addOneOf(AssetDataAccess.MANUF_NAME, manufNames);
            }

            if (Utility.isSet(criteria.getManufSku())) {
                dbCriteria.addEqualTo(AssetDataAccess.MANUF_SKU, criteria.getManufSku());
            }

            if (Utility.isSet(criteria.getAssetTypeCd())) {
                dbCriteria.addEqualTo(AssetDataAccess.ASSET_TYPE_CD, criteria.getAssetTypeCd());
            }

            if (Utility.isSet(criteria.getStatusCd())) {
                dbCriteria.addEqualTo(AssetDataAccess.STATUS_CD, criteria.getStatusCd());
            } else {
                dbCriteria.addEqualTo(AssetDataAccess.STATUS_CD, RefCodeNames.ASSET_STATUS_CD.ACTIVE);
            }

            if (criteria.getStoreIds() != null) {
                DBCriteria isolCriteria = new DBCriteria();

                dbCriteria.addJoinTable(AssetAssocDataAccess.CLW_ASSET_ASSOC + " ASSET_STORE");
                isolCriteria.addOneOf("ASSET_STORE." + AssetAssocDataAccess.BUS_ENTITY_ID, criteria.getStoreIds());
                isolCriteria.addEqualTo("ASSET_STORE." + AssetAssocDataAccess.ASSET_ASSOC_CD, RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE);
                isolCriteria.addCondition("ASSET_STORE." + AssetAssocDataAccess.ASSET_ID + "=" +
                                          AssetDataAccess.CLW_ASSET + "." + AssetDataAccess.ASSET_ID);

                dbCriteria.addIsolatedCriterita(isolCriteria);
            }
            AssetDataVector assetDV = AssetDataAccess.select(conn, dbCriteria);
            AssetData assetD;
            for (int i = 0; i < assetDV.size(); i++) {
                assetD = (AssetData)assetDV.get(i);
                assetIds.add(Integer.valueOf(assetD.getAssetId()));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return assetIds;
    }

    public IdVector getParentMasterAssetIds (AssetSearchCriteria criteria) throws RemoteException {
        IdVector parentAssetIds = new IdVector();
        DBCriteria crit = new DBCriteria();
        Connection conn = null;
        try {
            conn = getConnection();
            if (criteria.getAssetId() > 0) {
                crit.addEqualTo(AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID, criteria.getAssetId());
                crit.addEqualTo(AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);

                String parentAssetsSQL = AssetMasterAssocDataAccess.getSqlSelectIdOnly(AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID, crit);

                crit = new DBCriteria();
                crit.addOneOf(AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID, parentAssetsSQL);
                parentAssetIds = AssetMasterAssocDataAccess.selectIdOnly(conn, crit);
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return parentAssetIds;
    }


    public IdVector getParentOrChildMasterAssetIds(int assetId, String resultIdName) throws RemoteException {
      IdVector linkedAssetIds = new IdVector();
      DBCriteria dbc = new DBCriteria();
      Connection conn = null;
      try {
          conn = getConnection();
          if (assetId > 0 && Utility.isSet(resultIdName)) {
             if (AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID.equals(resultIdName) ){
               dbc.addEqualTo(AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID, assetId);
             } else if (AssetMasterAssocDataAccess.PARENT_MASTER_ASSET_ID.equals(resultIdName)){
               dbc.addEqualTo(AssetMasterAssocDataAccess.CHILD_MASTER_ASSET_ID, assetId);
             }
             dbc.addEqualTo(AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_STATUS_CD, RefCodeNames.ASSET_ASSOC_STATUS_CD.ACTIVE);
             linkedAssetIds = AssetMasterAssocDataAccess.selectIdOnly(conn, resultIdName, dbc);
          }
      } catch (Exception e) {
          throw processException(e);
      } finally {
          closeConnection(conn);
      }
      return linkedAssetIds;
    }

    public ArrayList checkAssetsInUse(IdVector categoryIdV) throws RemoteException {
     Connection conn = null;
     try {
        conn = getConnection();
//        IdVector aIV = AssetDataAccess.selectIdOnly(conn, dbc);
//        AssetDataVector adV = AssetDataAccess.select(conn, dbc);
        String sql =
            "SELECT aa.BUS_ENTITY_ID, a.ASSET_TYPE_CD, a.ASSET_ID,  a.PARENT_ID," +
            " (select short_desc from clw_bus_entity where bus_entity_id = AA.BUS_ENTITY_ID) store_name" +
            " FROM CLW_ASSET a, CLW_ASSET_ASSOC aa " +
            " WHERE a.ASSET_ID = aa.ASSET_ID " +
            "   AND a.PARENT_ID in (" + IdVector.toCommaString(categoryIdV) +")" +
            "   AND aa.ASSET_ASSOC_CD= '"+RefCodeNames.ASSET_ASSOC_CD.ASSET_STORE+"'" +
            " ORDER BY aa.BUS_ENTITY_ID, a.ASSET_TYPE_CD ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        ArrayList err = new ArrayList();

        HashMap storeMap =  new HashMap();
        HashMap typeMap =  null;
        HashSet assets = null;
        int n = 0;

        while(rs.next()){
          n++;
//          int storeId = rs.getInt(1);
          String storeName = rs.getString(5);
          String assetType = rs.getString(2);
          int assetId = rs.getInt(3);

          if (storeMap != null && storeMap.containsKey(storeName)) {
            typeMap = (HashMap) storeMap.get(storeName);
            if (typeMap != null && typeMap.containsKey(assetType)){
              assets = (HashSet)typeMap.get(assetType);
            } else {
              assets = new HashSet();
              typeMap.put(assetType, assets);
            }
          } else {
            typeMap = new HashMap();
            assets = new HashSet();
            storeMap.put(storeName, typeMap);
            typeMap.put(assetType, assets);
          }
          assets.add(new Integer(assetId));
        }
        pstmt.close();

        if (n > 0 && storeMap != null){
          err.add("Category cannot be deleted. " + n +" asset(s) has been found for this category.");
          Set keys = storeMap.keySet();
          for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            typeMap = (HashMap) storeMap.get(key);
            if (typeMap != null && typeMap.size() > 0) {
              Set _mAssets = (Set) typeMap.get(RefCodeNames.ASSET_TYPE_CD.MASTER_ASSET);
              Set _assets = (Set) typeMap.get(RefCodeNames.ASSET_TYPE_CD.ASSET);
              if (_mAssets != null && _mAssets.size() > 0) {
                err.add("Store Name: '" + key + "' => Master Assets:" +_mAssets.toString() + "; ");
              }
              if (_assets != null && _assets.size() > 0) {
                err.add("Store Name: '" + key + "' => Assets:" +_assets.toString() + "; ");
              }
            }
          }
        }
        return err;
     }
     catch (Exception e) {
        throw processException(e);
     }
     finally {
        closeConnection(conn);
     }
  }
  /**
   * removes  from clw_asset_master_assoc
   *
   * @param dbc  DBCriteria object
   * @return int Number of rows deleted.
   * @throws RemoteException Required by EJB 1.0
   */
  public int removeAssetMasterAssoc(DBCriteria dbc) throws RemoteException {
      Connection conn = null;
      try {
          if (dbc != null ) {
              conn = getConnection();
              String sql = AssetMasterAssocDataAccess.getSqlSelectIdOnly(AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_ID, dbc);
              DBCriteria dbCriteria = new DBCriteria();
              dbCriteria.addOneOf(AssetMasterAssocDataAccess.ASSET_MASTER_ASSOC_ID, sql);
              int n = AssetMasterAssocDataAccess.remove(conn, dbCriteria);
              return n;
          }
      } catch (Exception e) {
          throw processException(e);
      } finally {
          closeConnection(conn);
      }
      return 0;
  }
  /**
    * removes  from clw_asset_assoc
    *
    * @param ids asset_assoc_ids collection
    * @return int Number of rows deleted.
    * @throws RemoteException Required by EJB 1.0
    */
   public int removeAssetAssoc(DBCriteria dbCriteria) throws RemoteException {
       Connection conn = null;
       try {
           if (dbCriteria != null ) {
               conn = getConnection();
               int n = AssetAssocDataAccess.remove(conn, dbCriteria);
               return n;
           }
       } catch (Exception e) {
           throw processException(e);
       } finally {
           closeConnection(conn);
       }
       return 0;
    }

  public int removeAssetDetailDataForCategory(IdVector toDeleteAssetIds) throws RemoteException{
    Connection conn = null;
    try {
        if (toDeleteAssetIds != null && toDeleteAssetIds.size() > 0 ) {
            conn = getConnection();
            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addOneOf(AssetDataAccess.ASSET_ID, toDeleteAssetIds);
            dbCriteria.addEqualTo(AssetDataAccess.ASSET_TYPE_CD, RefCodeNames.ASSET_TYPE_CD.CATEGORY);
            int n = AssetDataAccess.remove(conn, dbCriteria);
            return n;
        }
    } catch (Exception e) {
        throw processException(e);
    } finally {
        closeConnection(conn);
    }
    return 0;

  }

}
