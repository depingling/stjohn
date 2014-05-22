package com.cleanwise.service.api.session;

import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.APIAccess;

import javax.ejb.CreateException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Title:        WarrantyBean
 * Description:  Bean implementation for Warranty Session Bean
 * Purpose:      Ejb for scheduled warranty management
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         16.09.2007
 * Time:         15:20:45
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class WarrantyBean extends ApplicationServicesAPI {

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws RemoteException           if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

    public WarrantyViewVector getWarrantyViewCollection(WarrantySimpleSearchCriteria criteria) throws RemoteException{
        WarrantyViewVector result = new WarrantyViewVector();
        Connection conn=null;
        try {
            if(criteria!=null){
                conn=getConnection();
                return getWarrantyViewCollection(conn,criteria);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return result;
    }

    private WarrantyViewVector getWarrantyViewCollection(Connection conn, WarrantySimpleSearchCriteria criteria) throws Exception {

        DBCriteria dbCriteria     = new DBCriteria();
        DBCriteria assocCrit      = new DBCriteria();
        WarrantyViewVector result = new WarrantyViewVector();
        HashMap warrantyAssetMap  = new HashMap();
        AssetDataVector assets    = new AssetDataVector();
        IdVector assetIds         = null;
        IdVector warrantyIds      = new IdVector();

        if (criteria != null) {
            if (Utility.isSet(criteria.getAssetName()) || Utility.isSet(criteria.getAssetNumber())) {
                assetIds = new IdVector();
                AssetSearchCriteria assetCrit = new AssetSearchCriteria();
                assetCrit.setAssetName(criteria.getAssetName());
                assetCrit.setShowInactive(true);
                assetCrit.setAssetNumber(criteria.getAssetNumber());
                assetCrit.setSearchNameTypeCd(criteria.getSearchType());
                assetCrit.setStoreIds(criteria.getStoreIds());
                Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();

                assets = assetEjb.getAssetDataByCriteria(assetCrit);
                if (!assets.isEmpty()) {
                    Iterator it = assets.iterator();
                    while (it.hasNext()) {
                        AssetData ad = (AssetData) it.next();
                        assetIds.add(new Integer(ad.getAssetId()));
                    }
                    assocCrit.addOneOf(AssetWarrantyDataAccess.ASSET_ID, assetIds);
                }
            }

            if (Utility.isSet(criteria.getWarrantyName())) {
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(criteria.getSearchType())) {
                    dbCriteria.addBeginsWith(WarrantyDataAccess.SHORT_DESC, criteria.getWarrantyName());
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(criteria.getSearchType())) {
                    dbCriteria.addContains(WarrantyDataAccess.SHORT_DESC, criteria.getWarrantyName());
                } else {
                    dbCriteria.addEqualTo(WarrantyDataAccess.SHORT_DESC, criteria.getWarrantyName());
                }
            }

            if (criteria.getWarrantyId()>0) {
                dbCriteria.addEqualTo(WarrantyDataAccess.WARRANTY_ID, criteria.getWarrantyId());
            }

            if(criteria.getStoreIds()!=null&&!criteria.getStoreIds().isEmpty()){
                dbCriteria.addJoinTableOneOf(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC,WarrantyAssocDataAccess.BUS_ENTITY_ID, criteria.getStoreIds());
                dbCriteria.addJoinTableEqualTo(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC,WarrantyAssocDataAccess.WARRANTY_ASSOC_CD,RefCodeNames.WARRANTY_ASSOC_CD.WARRANTY_STORE);
                dbCriteria.addJoinCondition(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC,WarrantyAssocDataAccess.WARRANTY_ID,WarrantyDataAccess.CLW_WARRANTY,WarrantyDataAccess.WARRANTY_ID);
            }

            if(Utility.isSet(criteria.getWarrantyProvider())){
                DBCriteria isolCriteria = new DBCriteria();
                dbCriteria.addJoinTable(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC+" WARR_PROV_ASSOC");
                dbCriteria.addJoinTable(BusEntityDataAccess.CLW_BUS_ENTITY+" WARR_PROV");

                isolCriteria.addCondition("WARR_PROV_ASSOC."+WarrantyAssocDataAccess.WARRANTY_ASSOC_CD+"='"+RefCodeNames.WARRANTY_ASSOC_CD.WARRANTY_PROVIDER+"'");
                isolCriteria.addCondition("WARR_PROV_ASSOC."+WarrantyAssocDataAccess.BUS_ENTITY_ID+"="+"WARR_PROV"+"."+BusEntityDataAccess.BUS_ENTITY_ID);
                isolCriteria.addCondition("WARR_PROV."+BusEntityDataAccess.BUS_ENTITY_TYPE_CD+"='"+RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER+"'");
                isolCriteria.addCondition("WARR_PROV_ASSOC."+WarrantyAssocDataAccess.WARRANTY_ID+"="+WarrantyDataAccess.CLW_WARRANTY+"."+WarrantyDataAccess.WARRANTY_ID);
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(criteria.getSearchType())) {
                    isolCriteria.addCondition("WARR_PROV"+"."+BusEntityDataAccess.SHORT_DESC+" LIKE '"+criteria.getWarrantyProvider()+"%'");
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(criteria.getSearchType())) {
                    isolCriteria.addCondition("WARR_PROV"+"."+BusEntityDataAccess.SHORT_DESC+" LIKE '%"+criteria.getWarrantyProvider()+"%'");
                } else {
                    isolCriteria.addCondition("WARR_PROV"+"."+BusEntityDataAccess.SHORT_DESC+"='"+criteria.getWarrantyProvider()+"'");
                }
                dbCriteria.addIsolatedCriterita(isolCriteria);
            }

            if(Utility.isSet(criteria.getServiceProvider())){
                DBCriteria isolCriteria = new DBCriteria();
                dbCriteria.addJoinTable(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC+" SERV_PROV_ASSOC");
                dbCriteria.addJoinTable(BusEntityDataAccess.CLW_BUS_ENTITY+" SERV_PROV");

                isolCriteria.addCondition("SERV_PROV_ASSOC."+WarrantyAssocDataAccess.WARRANTY_ASSOC_CD+"='"+RefCodeNames.WARRANTY_ASSOC_CD.SERVICE_PROVIDER+"'");
                isolCriteria.addCondition("SERV_PROV_ASSOC."+WarrantyAssocDataAccess.BUS_ENTITY_ID+"="+"SERV_PROV"+"."+BusEntityDataAccess.BUS_ENTITY_ID);
                isolCriteria.addCondition("SERV_PROV."+BusEntityDataAccess.BUS_ENTITY_TYPE_CD+"='"+RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER+"'");
                isolCriteria.addCondition("SERV_PROV_ASSOC."+WarrantyAssocDataAccess.WARRANTY_ID+"="+WarrantyDataAccess.CLW_WARRANTY+"."+WarrantyDataAccess.WARRANTY_ID);
                if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(criteria.getSearchType())) {
                    isolCriteria.addCondition("SERV_PROV"+"."+BusEntityDataAccess.SHORT_DESC+" LIKE '"+criteria.getServiceProvider()+"%'");
                } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(criteria.getSearchType())) {
                    isolCriteria.addCondition("SERV_PROV"+"."+BusEntityDataAccess.SHORT_DESC+" LIKE '%"+criteria.getServiceProvider()+"%'");
                } else {
                    isolCriteria.addCondition("SERV_PROV"+"."+BusEntityDataAccess.SHORT_DESC+"='"+criteria.getServiceProvider()+"'");
                }
                dbCriteria.addIsolatedCriterita(isolCriteria);
            }

            if (assetIds != null && !assetIds.isEmpty()) {
                AssetWarrantyDataVector assetWarranty = AssetWarrantyDataAccess.select(conn, assocCrit);
                Iterator it = assetWarranty.iterator();

                while (it.hasNext()) {
                    AssetWarrantyData aw = (AssetWarrantyData) it.next();
                    Integer key = new Integer(aw.getWarrantyId());
                    warrantyIds.add(key);
                    if (warrantyAssetMap.containsKey(key)) {
                        AssetData asset = getAssetData(assets, aw.getAssetId());
                        if (asset != null) {
                            AssetDataVector assetDV = (AssetDataVector) warrantyAssetMap.get(key);
                            assetDV.add(asset);
                        }

                    } else {
                        AssetData asset = getAssetData(assets, aw.getAssetId());
                        if (asset != null) {
                            AssetDataVector assetDV = new AssetDataVector();
                            assetDV.add(asset);
                            warrantyAssetMap.put(key, assetDV);
                        }
                    }
                }
            }
            //if search by asset and asset  not have asset_waranty association
            if (assetIds != null && warrantyIds.isEmpty()) {
                return result;
            }

            if(!warrantyIds.isEmpty()){
                dbCriteria.addOneOf(WarrantyDataAccess.WARRANTY_ID,warrantyIds);
            }

            WarrantyDataVector warrantyCollection = WarrantyDataAccess.select(conn, dbCriteria);

            if (!warrantyCollection.isEmpty()) {
                if (assetIds == null) {
                    Iterator it = warrantyCollection.iterator();
                    while (it.hasNext()) {
                        WarrantyData wd = (WarrantyData) it.next();
                        Integer key = new Integer(wd.getWarrantyId());
                        warrantyIds.add(key);
                    }

                    assocCrit.addOneOf(AssetWarrantyDataAccess.WARRANTY_ID, warrantyIds);
                    AssetWarrantyDataVector assetWarranty = AssetWarrantyDataAccess.select(conn, assocCrit);
                    assetIds = new IdVector();
                    it = assetWarranty.iterator();
                    while (it.hasNext()) {
                        AssetWarrantyData aw = (AssetWarrantyData) it.next();
                        Integer key = new Integer(aw.getAssetId());
                        assetIds.add(key);
                    }

                    AssetSearchCriteria assetCrit = new AssetSearchCriteria();
                    assetCrit.setStoreIds(criteria.getStoreIds());
                    assetCrit.setAssetIds(assetIds);
                    assetCrit.setShowInactive(true);
                    Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();
                    assets = assetEjb.getAssetDataByCriteria(assetCrit);

                    it = assetWarranty.iterator();

                    while (it.hasNext()) {
                        AssetWarrantyData aw = (AssetWarrantyData) it.next();
                        Integer key = new Integer(aw.getWarrantyId());
                        if (warrantyAssetMap.containsKey(key)) {
                            AssetData asset = getAssetData(assets, aw.getAssetId());
                            if (asset != null) {
                                AssetDataVector assetDV = (AssetDataVector) warrantyAssetMap.get(key);
                                assetDV.add(asset);
                            }

                        } else {
                            AssetData asset = getAssetData(assets, aw.getAssetId());
                            if (asset != null) {
                                AssetDataVector assetDV = new AssetDataVector();
                                assetDV.add(asset);
                                warrantyAssetMap.put(key, assetDV);
                            }
                        }
                    }
                }
            }

            Iterator it = warrantyCollection.iterator();
            while (it.hasNext()) {
                try {
                    WarrantyData warrData = (WarrantyData) it.next();
                    WarrantyView warrantyView = WarrantyView.createValue();
                    warrantyView.setWarrantyId(warrData.getWarrantyId());
                    warrantyView.setWarrantyNumber(warrData.getWarrantyNumber());
                    warrantyView.setType(warrData.getTypeCd());
                    warrantyView.setStatus(warrData.getStatusCd());
                    warrantyView.setWarrantyProvider(getWarrantyProvider(conn,warrData.getWarrantyId()));
                    warrantyView.setAssets(getAssetCategoriesForAssets((AssetDataVector) warrantyAssetMap.get(new Integer(warrData.getWarrantyId()))));
                    result.add(warrantyView);
                } catch (Exception e) {
                    logInfo("WARNING:"+e.getMessage());
                }
            }
        }
        return result;
    }

    private AssetData getAssetData(AssetDataVector assetDV, int assetId) {

        if (assetDV != null) {
            Iterator assocIter = assetDV.iterator();
            while(assocIter.hasNext()) {
                AssetData assetData = ((AssetData) assocIter.next());
                if(assetId==assetData.getAssetId()) {
                    return assetData;
                }
            }
        }
        return null;
    }

    public  WarrantyDetailView getWarrantyDetailView(int warrantyId) throws RemoteException{

        Connection conn=null;
        try {
            conn=getConnection();
            return getWarrantyDetailView(conn,warrantyId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    public BusEntityData getWarrantyProvider(int warrantyId) throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            return getWarrantyProvider(conn, warrantyId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }


    public WarrantyData getWarrantyData(int warrantyId) throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            return getWarranty(conn, warrantyId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private WarrantyDetailView getWarrantyDetailView(Connection conn, int warrantyId) throws Exception  {

        WarrantyDetailView wDetailView= WarrantyDetailView.createValue();

        WarrantyData  warranty = getWarranty(conn,warrantyId);
        WarrantyAssocViewVector wAssocVV = getWarrantyAssocViewCollection(conn,warrantyId);
        WarrantyStatusHistDataVector statusHistory = getStatusHistory(conn,warrantyId);
        WarrantyContentViewVector contents = getWarrantyContents(conn,warrantyId);
        AssetWarrantyViewVector assetWarrantyVV = getAssetWarrantyViewCollection(conn,warrantyId);
        WorkOrderItemWarrantyViewVector  woiWarrantyVV = getWOIWarrantyViewCollection(conn,warrantyId);
        WarrantyNoteDataVector notes = getWarrantyNotes(conn,warrantyId);

        wDetailView.setWarrantyData(warranty);
        wDetailView.setWarrantyAssocViewVector(wAssocVV);
        wDetailView.setStatusHistory(statusHistory);
        wDetailView.setContents(contents);
        wDetailView.setAssetWarrantyViewVector(assetWarrantyVV);
        wDetailView.setWorkOrderItemWarrantyViewVector(woiWarrantyVV);
        wDetailView.setWarrantyNotes(notes);

        return wDetailView;
    }

    private WarrantyAssocViewVector getWarrantyAssocViewCollection(Connection conn, int warrantyId) throws SQLException {

        DBCriteria crit;
        HashMap busEntityMap = new HashMap();
        WarrantyAssocViewVector result = new WarrantyAssocViewVector();

        WarrantyAssocDataVector wAssoc=getWarrantyAssoc(conn,warrantyId);
        IdVector busEntityIds = getOnlyBusEntityIds(wAssoc);
        if (!busEntityIds.isEmpty()) {
            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, busEntityIds);
            BusEntityDataVector busEntityCollection = BusEntityDataAccess.select(conn, crit);
            busEntityMap = toMap(busEntityCollection);
        }

        if (!wAssoc.isEmpty()) {
            Iterator it = wAssoc.iterator();
            while (it.hasNext()) {
                WarrantyAssocView assocView = WarrantyAssocView.createValue();
                WarrantyAssocData wad = (WarrantyAssocData) it.next();
                assocView.setWarrantyAssoc(wad);
                Integer beKey = new Integer(wad.getBusEntityId());
                if (busEntityMap.containsKey(beKey)) {
                    assocView.setBusEntity((BusEntityData) busEntityMap.get(beKey));
                }
                result.add(assocView);
            }
        }
        return result;
    }

    private WarrantyAssocDataVector getWarrantyAssoc(Connection conn, int warrantyId) throws SQLException {
        DBCriteria crit= new DBCriteria();
        crit.addEqualTo(WarrantyAssocDataAccess.WARRANTY_ID, warrantyId);
        return  WarrantyAssocDataAccess.select(conn, crit);
    }

    private IdVector getOnlyBusEntityIds(WarrantyAssocDataVector wAssoc) {
        IdVector ids=new IdVector();
        if ( wAssoc != null) {
            Iterator it =  wAssoc.iterator();
            while (it.hasNext()) {
                WarrantyAssocData wAD = (WarrantyAssocData) it.next();
                ids.add(new Integer(wAD.getBusEntityId()));
            }
        }
        return ids;
    }

    private WarrantyNoteDataVector getWarrantyNotes(Connection conn, int warrantyId) throws SQLException {
        DBCriteria crit   = new DBCriteria();
        crit.addEqualTo(WarrantyNoteDataAccess.WARRANTY_ID,warrantyId);
        crit.addIsNull(WarrantyNoteDataAccess.ASSET_WARRANTY_ID);
        return WarrantyNoteDataAccess.select(conn,crit);
    }

    private WorkOrderItemWarrantyViewVector getWOIWarrantyViewCollection(Connection conn, int warrantyId) throws SQLException {

        DBCriteria crit = new DBCriteria();
        WorkOrderItemWarrantyViewVector result = new WorkOrderItemWarrantyViewVector();

        crit.addEqualTo(WorkOrderItemDataAccess.WARRANTY_ID, warrantyId);
        WorkOrderItemDataVector wois = WorkOrderItemDataAccess.select(conn, crit);

        HashMap woiMap = toMapByWorkOrderId(wois);
        IdVector woIds = new IdVector();
        woIds.addAll(woiMap.keySet());
        if (!woIds.isEmpty()) {
            WorkOrderDataVector workOrders = getWorkOrders(conn, woIds);
            HashMap workOrderMap = toMap(workOrders);
            Iterator wosIdsIt = woIds.iterator();
            while (wosIdsIt.hasNext()) {
                WorkOrderItemWarrantyView woiWarranty = WorkOrderItemWarrantyView.createValue();

                Integer woiKey = (Integer) wosIdsIt.next();

                WorkOrderItemDataVector woiDataVector = (WorkOrderItemDataVector) woiMap.get(woiKey);
                WorkOrderData workOrder = (WorkOrderData) workOrderMap.get(woiKey);

                woiWarranty.setWorkOrderItems(woiDataVector);
                woiWarranty.setWorkOrder(workOrder);

                result.add(woiWarranty);
            }
        }
        return result;

    }

    private HashMap toMap(WorkOrderDataVector workOrders) {
        HashMap woInfoMap = new HashMap();
        if ( workOrders != null) {
            Iterator it =  workOrders.iterator();
            while (it.hasNext()) {

                WorkOrderData wo = (WorkOrderData) it.next();
                woInfoMap.put(new Integer(wo.getWorkOrderId()),wo);
            }
        }
        return woInfoMap;
    }

    private HashMap toMap(AssetDataVector assets) {
        HashMap assetInfoMap = new HashMap();
        if ( assets != null) {
            Iterator it =  assets.iterator();
            while (it.hasNext()) {

                AssetData asset = (AssetData) it.next();
                assetInfoMap.put(new Integer(asset.getAssetId()),asset);
            }
        }
        return assetInfoMap;
    }

    private HashMap toMap(AssetViewVector assets) {
        HashMap assetInfoMap = new HashMap();
        if ( assets != null) {
            Iterator it =  assets.iterator();
            while (it.hasNext()) {

                AssetView asset = (AssetView) it.next();
                assetInfoMap.put(new Integer(asset.getAssetId()),asset);
            }
        }
        return assetInfoMap;
    }

    private HashMap toMap(BusEntityDataVector busEntityCollection) {
        HashMap map = new HashMap();
        if (busEntityCollection != null) {
            Iterator it = busEntityCollection.iterator();
            while (it.hasNext()) {

                BusEntityData bed = (BusEntityData) it.next();
                map.put(new Integer(bed.getBusEntityId()), bed);
            }
        }
        return map;
    }

    private HashSet toSet( WarrantyAssocDataVector warrantyAssoc) {
        HashSet set = new HashSet();
        if (warrantyAssoc != null) {
            Iterator it = warrantyAssoc.iterator();
            while (it.hasNext()) {

                WarrantyAssocData wad = (WarrantyAssocData) it.next();
                set.add(new Integer(wad.getWarrantyAssocId()));
            }
        }
        return set;
    }

    private HashMap groupByAssetWarranty(WarrantyNoteDataVector assetWarrantyNotes) {
        HashMap resultMap = new HashMap();
        if(assetWarrantyNotes!=null){
            Iterator it = assetWarrantyNotes.iterator();
            while (it.hasNext()) {
                WarrantyNoteData note = (WarrantyNoteData) it.next();
                Integer key=new Integer(note.getAssetWarrantyId());
                if(resultMap.containsKey(key)){
                    WarrantyNoteDataVector groupNotes = (WarrantyNoteDataVector) resultMap.get(key);
                    if(!groupNotes.contains(note)){
                        groupNotes.add(note);
                    }
                }else{
                    WarrantyNoteDataVector groupNotes =new WarrantyNoteDataVector();
                    groupNotes.add(note);
                    resultMap.put(key,groupNotes);
                }
            }
        }
        return resultMap;
    }

    private WorkOrderDataVector getWorkOrders(Connection conn, IdVector wosIds) throws SQLException {
        DBCriteria crit   = new DBCriteria();
        crit.addOneOf(WorkOrderDataAccess.WORK_ORDER_ID,wosIds);
        return WorkOrderDataAccess.select(conn,crit);
    }

    private HashMap toMapByWorkOrderId(WorkOrderItemDataVector wois) {
        HashMap woiMap = new HashMap();
        if(wois!=null){
            Iterator it = wois.iterator();
            while (it.hasNext()) {
                WorkOrderItemData woiData = (WorkOrderItemData) it.next();
                Integer key=new Integer(woiData.getWorkOrderId());
                if(woiMap.containsKey(key)){
                    WorkOrderItemDataVector groupWOIs = (WorkOrderItemDataVector) woiMap.get(key);
                    if(!groupWOIs.contains(woiData)){
                        groupWOIs.add(woiData);
                    }
                }else{
                    WorkOrderItemDataVector groupWOIs =new WorkOrderItemDataVector();
                    groupWOIs.add(woiData);
                    woiMap.put(key,groupWOIs);
                }
            }
        }
        return woiMap;
    }

    private HashMap toMap(ContentViewVector contents) {
        HashMap contentMap = new HashMap();

        Iterator it = contents.iterator();
        while (it.hasNext()) {
            ContentView c = (ContentView) it.next();
            Integer key = new Integer(c.getContentId());
            contentMap.put(key, c);
        }
        return contentMap;
    }

    private AssetWarrantyViewVector getAssetWarrantyViewCollection(Connection conn, int warrantyId) throws Exception {

        DBCriteria crit   = new DBCriteria();
        IdVector assetIds = new IdVector();
        IdVector assetWarrantyIds = new IdVector();
        AssetWarrantyViewVector result = new AssetWarrantyViewVector();
        HashMap assetMap  = new HashMap();
        HashMap assetWarrantyNotesMap = new HashMap();

        crit.addEqualTo(AssetWarrantyDataAccess.WARRANTY_ID,warrantyId);
        AssetWarrantyDataVector assetWarrantyDV = AssetWarrantyDataAccess.select(conn, crit);

        //gets asset ids and Asset Warranty Ids
        if (!assetWarrantyDV.isEmpty()) {
            Iterator it = assetWarrantyDV.iterator();
            while (it.hasNext()) {
                AssetWarrantyData ad = (AssetWarrantyData) it.next();
                assetIds.add(new Integer(ad.getAssetId()));
                assetWarrantyIds.add(new Integer(ad.getAssetWarrantyId()));
            }
        }

        if(!assetWarrantyIds.isEmpty()){
            crit   = new DBCriteria();
            crit.addEqualTo(WarrantyNoteDataAccess.WARRANTY_ID,warrantyId);
            crit.addOneOf(WarrantyNoteDataAccess.ASSET_WARRANTY_ID,assetWarrantyIds);
            WarrantyNoteDataVector assetWarrantyNotes = WarrantyNoteDataAccess.select(conn,crit);
            assetWarrantyNotesMap=groupByAssetWarranty(assetWarrantyNotes);
        }

        //gets asset map
        if(!assetIds.isEmpty()){
            Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();
            AssetSearchCriteria assetCrit = new AssetSearchCriteria();
            assetCrit.setAssetIds(assetIds);
            assetCrit.setShowInactive(true);
            AssetViewVector assetVV = assetEjb.getAssetViewVector(assetCrit);
            assetMap=toMap(assetVV);
        }
        if (!assetWarrantyDV.isEmpty()) {
            Iterator it = assetWarrantyDV.iterator();
            while (it.hasNext()) {
                AssetWarrantyData assetWarrantyData = (AssetWarrantyData) it.next();
                AssetWarrantyView assetWarrantyView = AssetWarrantyView.createValue();
                assetWarrantyView.setAssetWarrantyData(assetWarrantyData);
                Integer assetKey=new Integer(assetWarrantyData.getAssetId());
                Integer notesKey=new Integer(assetWarrantyData.getAssetWarrantyId());
                if(assetMap.containsKey(assetKey)){
                    assetWarrantyView.setAssetView((AssetView) assetMap.get(assetKey));
                }
                if(assetWarrantyNotesMap.containsKey(notesKey)){
                    assetWarrantyView.setAssetWarrantyNotes((WarrantyNoteDataVector) assetWarrantyNotesMap.get(notesKey));
                }

                result.add(assetWarrantyView);
            }
        }
        return result;
    }

    private WarrantyContentViewVector getWarrantyContents(Connection conn, int warrantyId) throws Exception {

        String sql ="SELECT C.BUS_ENTITY_ID," +
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
                "WC.WARRANTY_CONTENT_ID," +
                "WC.WARRANTY_ID,"+
                "WC.URL,"+
                "WC.MOD_BY," +
                "WC.MOD_DATE," +
                "WC.ADD_BY," +
                "WC.ADD_DATE " +
                " FROM CLW_CONTENT C,CLW_WARRANTY_CONTENT WC " +
                "   WHERE WC.WARRANTY_ID = ?"+
                "   AND WC.CONTENT_ID = C.CONTENT_ID";

        logInfo("getWarrantyContents => sql:"+sql);
        logInfo("getWarrantyContents => param[warrantyId]:"+warrantyId);

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,warrantyId);

        ResultSet rs = pstmt.executeQuery();

        WarrantyContentViewVector v = new WarrantyContentViewVector();
        while (rs.next()) {
            ContentView c = ContentView.createValue();
            WarrantyContentData wc = WarrantyContentData.createValue();

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

            wc.setWarrantyContentId(rs.getInt(20));
            wc.setWarrantyId(rs.getInt(21));
            wc.setUrl(rs.getString(22));
            wc.setModBy(rs.getString(23));
            wc.setModDate(rs.getTimestamp(24));
            wc.setAddBy(rs.getString(25));
            wc.setAddDate(rs.getTimestamp(26));

            v.add(new WarrantyContentView(c,wc));
        }

        rs.close();
        pstmt.close();

        return v;
    }

    public WarrantyContentDetailView getWarrantyContentDetails(int warrantyContentId) throws RemoteException{
        Connection conn=null;
        try {
            conn=getConnection();
            return getWarrantyContentDetails(conn,warrantyContentId);

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    private WarrantyContentDetailView getWarrantyContentDetails(Connection conn, int warrantyContentId) throws Exception{
        WarrantyContentData warrantyContent = WarrantyContentDataAccess.select(conn, warrantyContentId);
        Content contentEjb = APIAccess.getAPIAccess().getContentAPI();
        ContentDetailView contentDetails = contentEjb.getContentDetailView(warrantyContent.getContentId());
        return new WarrantyContentDetailView(contentDetails,warrantyContent);
    }


    private WarrantyStatusHistDataVector getStatusHistory(Connection conn, int warrantyId) throws Exception {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(WarrantyStatusHistDataAccess.WARRANTY_ID,warrantyId);
        crit.addOrderBy(WarrantyStatusHistDataAccess.STATUS_DATE);
        return WarrantyStatusHistDataAccess.select(conn,crit);
    }

    private BusEntityData getWarrantyProvider(Connection conn, int warrantyId) throws Exception {

        DBCriteria crit = new DBCriteria();
        crit.addJoinTableEqualTo(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC,WarrantyAssocDataAccess.WARRANTY_ASSOC_CD,RefCodeNames.WARRANTY_ASSOC_CD.WARRANTY_PROVIDER);
        crit.addJoinTableEqualTo(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC,WarrantyAssocDataAccess.WARRANTY_ID,warrantyId);
        crit.addJoinCondition(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC,WarrantyAssocDataAccess.BUS_ENTITY_ID, BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID);
        crit.addJoinTableEqualTo(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);

        BusEntityDataVector res = BusEntityDataAccess.select(conn, crit);

        if(res.size()>1){
            throw new Exception("Multiple Warranty Provider.Warranty Id => "+warrantyId);
        }

        if(res.size()>0){
            return (BusEntityData) res.get(0);
        }

        return null;
    }

    public BusEntityDataVector getWarrantyProvidersForStore(int storeId) throws RemoteException {

        try {
            Manufacturer manufEjb = APIAccess.getAPIAccess().getManufacturerAPI();

            BusEntitySearchCriteria besc = new BusEntitySearchCriteria();

            IdVector storeIdAsV = new IdVector();
            storeIdAsV.add(new Integer(storeId));
            besc.setStoreBusEntityIds(storeIdAsV);

            return manufEjb.getManufacturerBusEntitiesByCriteria(besc);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }

    public BusEntityDataVector getServiceProvidersForStore(int storeId) throws RemoteException{

        Connection conn=null;
        try {
            conn=getConnection();
            return getServiceProvidersForStore(conn,storeId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }
    
    public WarrantyDataVector getWarrantiesForStore(int storeId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getWarrantiesForStore(conn, storeId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    private WarrantyDataVector getWarrantiesForStore(Connection conn, int storeId) throws SQLException {
        WarrantyDataVector result = new WarrantyDataVector();
        
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(WarrantyAssocDataAccess.BUS_ENTITY_ID, storeId);
        crit.addEqualTo(WarrantyAssocDataAccess.WARRANTY_ASSOC_CD, RefCodeNames.WARRANTY_ASSOC_CD.WARRANTY_STORE);
        
        IdVector storeWarrantyIds = WarrantyAssocDataAccess.selectIdOnly(conn, WarrantyAssocDataAccess.WARRANTY_ID, crit);
        
        if (!storeWarrantyIds.isEmpty()) {
            result = WarrantyDataAccess.select(conn, storeWarrantyIds);
        }
        
        return result;
    }
    
    private BusEntityDataVector getServiceProvidersForStore(Connection conn, int storeId) throws SQLException {

        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
        crit.addJoinCondition(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY1_ID, BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID);
        crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_STORE);
        crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeId);

        return BusEntityDataAccess.select(conn, crit);
    }
    
    public BusEntityDataVector getServiceProvidersForAccount(int accountId) throws RemoteException{

        Connection conn=null;
        try {
            conn=getConnection();
            return getServiceProvidersForAccount(conn,accountId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }
    
    private BusEntityDataVector getServiceProvidersForAccount(Connection conn, int accountId) throws SQLException {

        DBCriteria crit = new DBCriteria();

        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER);
        crit.addJoinCondition(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY1_ID, BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID);
        crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SERVICE_PROVIDER_ACCOUNT);
        crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY2_ID,accountId);

        return BusEntityDataAccess.select(conn, crit);
    }

    private WarrantyData getWarranty(Connection conn, int warrantyId) throws SQLException, DataNotFoundException {
        return WarrantyDataAccess.select(conn,warrantyId);
    }

    public WarrantyData updateWarranty(WarrantyData warranty, WarrantyAssocDataVector warrantyAssoc, UserData user) throws RemoteException {
        Connection conn = null;
        try {
            if (warranty != null) {
                conn = getConnection();
                String currentStatusCd = "";
                // get current status cd if warranty exist
                if (warranty.getWarrantyId() > 0) {
                    WarrantyData currentWarranty = getWarranty(conn, warranty.getWarrantyId());
                    currentStatusCd = currentWarranty.getStatusCd();
                }
                warranty = updateWarranty(conn, warranty, user);
                updateWarrantyAssoc(conn, warranty.getWarrantyId(), warrantyAssoc, user);
                if (!currentStatusCd.equals(warranty.getStatusCd())) {
                    addStatusToHistory(conn, warranty.getWarrantyId(), warranty.getStatusCd(), user);
                }
            }
            return warranty;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private WarrantyStatusHistData addStatusToHistory(Connection conn, int warrantyId, String statusCd, UserData user) throws SQLException {
        WarrantyStatusHistData statusHistory = WarrantyStatusHistData.createValue();
        statusHistory.setStatusCd(statusCd);
        statusHistory.setTypeCd(RefCodeNames.WARRANTY_STATUS_HIST_TYPE_CD.UNKNOWN);
        statusHistory.setStatusDate(new Date());
        statusHistory.setWarrantyId(warrantyId);
        return updateWarrantyStatusHistory(conn, statusHistory, user);
    }

    private WarrantyStatusHistData updateWarrantyStatusHistory(Connection conn, WarrantyStatusHistData statusHistory, UserData user) throws SQLException {
        if (statusHistory != null) {
            if (statusHistory.getWarrantyStatusHistId() > 0) {
                statusHistory.setModBy(user.getUserName());
                WarrantyStatusHistDataAccess.update(conn, statusHistory);
            } else {
                statusHistory.setAddBy(user.getUserName());
                statusHistory.setModBy(user.getUserName());
                statusHistory = WarrantyStatusHistDataAccess.insert(conn, statusHistory);
            }
        }
        return statusHistory;
    }

    private WarrantyData updateWarranty(Connection conn, WarrantyData warranty, UserData user) throws SQLException {
        if(warranty!=null){
            if(warranty.getWarrantyId()>0){
                warranty.setModBy(user.getUserName());
                WarrantyDataAccess.update(conn,warranty);
            }
            else{
                warranty.setAddBy(user.getUserName());
                warranty.setModBy(user.getUserName());
                warranty=WarrantyDataAccess.insert(conn,warranty);
            }
        }
        return   warranty;
    }

    private WarrantyAssocDataVector updateWarrantyAssoc(Connection conn, int warrantyId, WarrantyAssocDataVector warrantyAssoc, UserData user) throws SQLException {

        WarrantyAssocDataVector currentAssoc = getWarrantyAssoc(conn, warrantyId);
        HashSet currentAssocIdsSet = toSet(currentAssoc);

        if (warrantyAssoc != null && !warrantyAssoc.isEmpty()) {
            Iterator it = warrantyAssoc.iterator();
            while (it.hasNext()) {
                WarrantyAssocData assoc = (WarrantyAssocData) it.next();
                assoc.setWarrantyId(warrantyId);
                if (assoc.getWarrantyAssocId() > 0) {
                    assoc.setModBy(user.getUserName());
                    WarrantyAssocDataAccess.update(conn, assoc);
                    if (currentAssocIdsSet.contains(new Integer(assoc.getWarrantyAssocId()))) {
                        currentAssocIdsSet.remove(new Integer(assoc.getWarrantyAssocId()));
                    }
                } else {
                    assoc.setAddBy(user.getUserName());
                    assoc.setModBy(user.getUserName());
                    assoc = WarrantyAssocDataAccess.insert(conn, assoc);
                }
            }
        }

        if (!currentAssocIdsSet.isEmpty()) {
            IdVector ids = new IdVector();
            ids.addAll(currentAssocIdsSet);
            DBCriteria crit = new DBCriteria();
            crit.addOneOf(WarrantyAssocDataAccess.WARRANTY_ASSOC_ID, ids);
            WarrantyAssocDataAccess.remove(conn, crit);
        }

        return warrantyAssoc;
    }

    private void removeWarrantyAssoc(Connection conn, int warrantyId, String assocCd) throws SQLException {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(WarrantyAssocDataAccess.WARRANTY_ID,warrantyId);
        crit.addEqualTo(WarrantyAssocDataAccess.WARRANTY_ASSOC_CD,assocCd);
        WarrantyAssocDataAccess.remove(conn, crit);
    }

    private void addWarrantyAssoc(Connection conn, int warrantyId, int busEntityId, String assocCd,UserData user) throws SQLException {
        WarrantyAssocData warrantyAssoc = WarrantyAssocData.createValue();
        warrantyAssoc.setWarrantyAssocCd(assocCd);
        warrantyAssoc.setBusEntityId(busEntityId);
        warrantyAssoc.setWarrantyAssocStatusCd(RefCodeNames.WARRANTY_ASSOC_STATUS_CD.ACTIVE);
        warrantyAssoc.setWarrantyId(warrantyId);
        warrantyAssoc.setModBy(user.getUserName());
        warrantyAssoc.setAddBy(user.getUserName());
        WarrantyAssocDataAccess.insert(conn,warrantyAssoc);
    }

    private boolean existWarrantyAssoc(Connection conn,int warrantyId, int busEntityId, String assocCd) throws SQLException {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(WarrantyAssocDataAccess.WARRANTY_ID,warrantyId);
        crit.addEqualTo(WarrantyAssocDataAccess.BUS_ENTITY_ID,busEntityId);
        crit.addEqualTo(WarrantyAssocDataAccess.WARRANTY_ASSOC_CD,assocCd);
        WarrantyAssocDataVector result = WarrantyAssocDataAccess.select(conn, crit);
        return result.size()>0;
    }

    public AssetWarrantyData updateAssetWarranty(AssetWarrantyData awData, UserData user) throws RemoteException{
        Connection conn=null;
        try {
            if(awData!=null){
                conn=getConnection();
                awData = updateAssetWarranty(conn,awData,user);
            }
            return awData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    private AssetWarrantyDataVector getAssetWarrantyLinks(Connection conn, int assetId, int warrantyId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(AssetWarrantyDataAccess.ASSET_ID, assetId);
        dbCrit.addEqualTo(AssetWarrantyDataAccess.WARRANTY_ID, warrantyId);
        return  AssetWarrantyDataAccess.select(conn, dbCrit);
    }
    
    public IdVector getAssetWarrantyIdOnly(int assetId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAssetWarrantyIdOnly(conn, assetId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    private IdVector getAssetWarrantyIdOnly(Connection conn, int assetId) throws SQLException {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(AssetWarrantyDataAccess.ASSET_ID, assetId);
        return  AssetWarrantyDataAccess.selectIdOnly(conn, dbCrit);
    }
    
    private AssetWarrantyData updateAssetWarranty(Connection conn, AssetWarrantyData awData, UserData user) throws Exception {
        if (awData != null) {

            if (awData.getAssetWarrantyId() > 0) {

                AssetWarrantyDataVector currLinks = getAssetWarrantyLinks(conn, awData.getAssetId(), awData.getWarrantyId());
                logInfo("updateAssetWarranty => currLinks size:"+currLinks.size());
                if (currLinks.size() == 1) {
                    if (awData.getAssetWarrantyId() == ((AssetWarrantyData) currLinks.get(0)).getAssetWarrantyId()) {
                        awData.setModBy(user.getUserName());
                        AssetWarrantyDataAccess.update(conn, awData);
                    } else {
                        throw new Exception("Link exist");
                    }
                } else if (currLinks.size() > 1) {
                    throw new Exception("Multiple link found");
                } else {
                    awData.setModBy(user.getUserName());
                    AssetWarrantyDataAccess.update(conn, awData);
                }
            } else {
                if (getAssetWarrantyLinks(conn, awData.getAssetId(), awData.getWarrantyId()).size() == 0) {
                    awData.setAddBy(user.getUserName());
                    awData.setModBy(user.getUserName());
                    awData = AssetWarrantyDataAccess.insert(conn, awData);
                } else {
                    throw new Exception("Link exist");
                }
            }
        }
        return awData;
    }
    
    public void insertAssetWarranty(AssetWarrantyData awData) throws RemoteException {
        Connection conn=null;
        try {
            if(awData != null){
                conn = getConnection();
                AssetWarrantyDataAccess.insert(conn, awData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    
    public void removeAssetWarranty(int assetWarrantyId) throws RemoteException{
        Connection conn=null;
        try {
            if(assetWarrantyId>0){
                conn=getConnection();
                removeAssetWarranty(conn,assetWarrantyId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    public void removeAssetWarranty(IdVector assetWarrantyIds) throws RemoteException{
        Connection conn=null;
        try {
            if(assetWarrantyIds!=null){
                conn=getConnection();
                removeAssetWarranty(conn,assetWarrantyIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    private void removeAssetWarranty(Connection conn, IdVector assetWarrantyIds) throws SQLException {
        DBCriteria dbCrit;

        //remove note
        dbCrit = new  DBCriteria();
        dbCrit.addOneOf(WarrantyNoteDataAccess.ASSET_WARRANTY_ID,assetWarrantyIds);
        WarrantyNoteDataAccess.remove(conn,dbCrit);
        //remove asset warranty
        dbCrit = new  DBCriteria();
        dbCrit.addOneOf(AssetWarrantyDataAccess.ASSET_WARRANTY_ID,assetWarrantyIds);
        AssetWarrantyDataAccess.remove(conn,dbCrit);
    }

    private void removeAssetWarranty(Connection conn, int assetWarrantyId) throws SQLException {
        DBCriteria dbCrit = new  DBCriteria();
        //remove note
        dbCrit.addEqualTo(WarrantyNoteDataAccess.ASSET_WARRANTY_ID,assetWarrantyId);
        WarrantyNoteDataAccess.remove(conn,dbCrit);
        //remove asset warranty
        AssetWarrantyDataAccess.remove(conn,assetWarrantyId);
    }

    public WarrantyNoteData getWarrantyNote(int warrantyNoteId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return WarrantyNoteDataAccess.select(conn, warrantyNoteId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public WarrantyNoteData updateWarrantyNote(WarrantyNoteData note, UserData user) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return updateWarrantyNote(conn,note,user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    private WarrantyNoteData updateWarrantyNote(Connection conn, WarrantyNoteData note, UserData user) throws SQLException {
        if (note != null) {
            if (note.getWarrantyNoteId() > 0) {
                note.setModBy(user.getUserName());
                WarrantyNoteDataAccess.update(conn, note);
            } else {
                note.setAddBy(user.getUserName());
                note.setModBy(user.getUserName());

                WarrantyNoteDataAccess.insert(conn, note);
            }
        }
        return note;
    }

    public void removeWarrantyNote(int warrantyNoteId) throws RemoteException{
        Connection conn=null;
        try {
            if(warrantyNoteId>0){
                conn=getConnection();
                removeWarrantyNote(conn,warrantyNoteId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    private void removeWarrantyNote(Connection conn, int warrantyNoteId) throws SQLException {
        WarrantyNoteDataAccess.remove(conn,warrantyNoteId);
    }

    public WarrantyContentDetailView updateWarrantyContent(WarrantyContentDetailView warrantyContent, UserData user) throws RemoteException{
        Connection conn=null;
        try {
            if(warrantyContent!=null){
                conn=getConnection();
                return updateWarrantyContent(conn,warrantyContent,user);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return warrantyContent;
    }

    private WarrantyContentDetailView updateWarrantyContent(Connection conn, WarrantyContentDetailView warrantyContent,UserData user) throws Exception {

        if(warrantyContent!=null && warrantyContent.getContent()!=null &&warrantyContent.getWarrantyContentData()!=null){

            Content contentEjb=APIAccess.getAPIAccess().getContentAPI();
            ContentDetailView content = contentEjb.updateContent(warrantyContent.getContent(),user);
            warrantyContent.getWarrantyContentData().setContentId(content.getContentId());

            if(warrantyContent.getWarrantyContentData().getWarrantyContentId()<=0){
                warrantyContent.getWarrantyContentData().setAddBy(user.getUserName());
                warrantyContent.getWarrantyContentData().setModBy(user.getUserName());
                WarrantyContentData wC = WarrantyContentDataAccess.insert(conn, warrantyContent.getWarrantyContentData());
                warrantyContent.setWarrantyContentData(wC);
            }  else{
                warrantyContent.getWarrantyContentData().setModBy(user.getUserName());
                WarrantyContentDataAccess.update(conn, warrantyContent.getWarrantyContentData());
            }
            warrantyContent= new WarrantyContentDetailView(content,warrantyContent.getWarrantyContentData());
        }
        return  warrantyContent;
    }

    private static AssetWarrantyDataVector getAssetWarrantyDataVector(AssetWarrantyViewVector assetWarrantyViewVector) {
        AssetWarrantyDataVector assetDV = new AssetWarrantyDataVector();
        if(assetWarrantyViewVector!=null && !assetWarrantyViewVector.isEmpty()) {
            Iterator it =  assetWarrantyViewVector.iterator();
            while(it.hasNext()){
                assetDV.add (((AssetWarrantyView) it.next()).getAssetWarrantyData());
            }
        }
        return assetDV;
    }

    public void updateAssetWarrantyView(int warrantyId,AssetWarrantyViewVector newLinks , UserData user) throws RemoteException{
        Connection conn=null;
        try {
            conn=getConnection();

            AssetWarrantyDataVector currentlyLinks = getAssetWarrantyCollection(conn, warrantyId);

            HashMap changeStatus = getChangesStatusMap(currentlyLinks,getAssetWarrantyDataVector(newLinks));

            //remove
            AssetWarrantyDataVector deleteVector= (AssetWarrantyDataVector) changeStatus.get(RefCodeNames.CHANGE_STATUS.DELETE);
            if(deleteVector!=null && !deleteVector.isEmpty()){
                Iterator it =  deleteVector.iterator();
                IdVector assetWarrantyIds = new IdVector();
                while(it.hasNext()){
                    AssetWarrantyData aw = (AssetWarrantyData) it.next();
                    assetWarrantyIds.add(new Integer(aw.getAssetWarrantyId()));
                }
                if(!assetWarrantyIds.isEmpty()){
                    removeAssetWarranty(assetWarrantyIds);
                }
            }

            //insert & update
            AssetWarrantyDataVector updateVector= (AssetWarrantyDataVector) changeStatus.get(RefCodeNames.CHANGE_STATUS.UPDATE);
            AssetWarrantyDataVector insertVector= (AssetWarrantyDataVector) changeStatus.get(RefCodeNames.CHANGE_STATUS.INSERT);
            if(updateVector!=null){
                if(insertVector!=null){
                    updateVector.addAll(insertVector);
                }
                Iterator it =  updateVector.iterator();
                while(it.hasNext()){
                    AssetWarrantyData aw = (AssetWarrantyData) it.next();
                    aw.setWarrantyId(warrantyId);
                    updateAssetWarrantyLink(conn,aw,user);
                }
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
    }

    private AssetWarrantyDataVector getAssetWarrantyCollection(Connection conn, int warrantyId) throws Exception {
        DBCriteria dbCrit = new DBCriteria();
        dbCrit.addEqualTo(AssetWarrantyDataAccess.WARRANTY_ID, warrantyId);
        return  AssetWarrantyDataAccess.select(conn, dbCrit);
    }

    private HashMap getChangesStatusMap(AssetWarrantyDataVector currentlyLinks, AssetWarrantyDataVector newLinks) {

        HashMap changesMap = new HashMap();

        AssetWarrantyDataVector insert = new AssetWarrantyDataVector();
        AssetWarrantyDataVector update = new AssetWarrantyDataVector();
        AssetWarrantyDataVector delete = new AssetWarrantyDataVector();

        changesMap.put(RefCodeNames.CHANGE_STATUS.DELETE, delete);
        changesMap.put(RefCodeNames.CHANGE_STATUS.INSERT, insert);
        changesMap.put(RefCodeNames.CHANGE_STATUS.UPDATE, update);


        if (newLinks.size() == 0 && currentlyLinks.size() > 0) {

            delete.addAll(currentlyLinks);

        } else if (newLinks.size() > 0 && currentlyLinks.size() == 0) {

            insert.addAll( newLinks);

        } else {

            Iterator it = currentlyLinks.iterator();
            while (it.hasNext()) {
                AssetWarrantyData currAssetWarranty = (AssetWarrantyData) it.next();
                Iterator it1 =  newLinks.iterator();
                while (it1.hasNext()) {
                    AssetWarrantyData newAssetWarranty = (AssetWarrantyData) it1.next();
                    if (newAssetWarranty.getAssetWarrantyId() == currAssetWarranty.getAssetWarrantyId()) {
                        update.add(newAssetWarranty);
                        it1.remove();
                        it.remove();
                        break;
                    }
                }
            }

            insert.addAll(newLinks);
            delete.addAll(currentlyLinks);
        }
        return changesMap;
    }

    private AssetWarrantyData updateAssetWarrantyLink(Connection conn, AssetWarrantyData assetWarranty, UserData user) throws SQLException {

        if(assetWarranty!=null){
            if(assetWarranty.getAssetWarrantyId()==0){
                assetWarranty.setAddBy(user.getUserName());
                assetWarranty.setModBy(user.getUserName());
                assetWarranty= AssetWarrantyDataAccess.insert(conn,assetWarranty);
            } else {
                assetWarranty.setModBy(user.getUserName());
                AssetWarrantyDataAccess.update(conn,assetWarranty);
            }
        }
        return assetWarranty;
    }

    public boolean removeWarrantyContent(int wcId,int contentId) throws RemoteException{
        Connection conn=null;
        try {
            if(wcId>0){
                conn=getConnection();
                return removeWarrantyContent(conn,wcId,contentId);
            }

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(conn);
        }
        return false;
    }

    private boolean removeWarrantyContent(Connection conn, int wcId, int contentId) throws SQLException {
        try {
            logInfo("removeWarrantyContent => wcId:"+wcId+" contentId:"+contentId);
            if (contentId <= 0) {
                WarrantyContentData acData = WarrantyContentDataAccess.select(conn, wcId);
                wcId = acData.getWarrantyContentId();
                contentId = acData.getContentId();
                logInfo("removeWarrantyContent => now wcId:"+wcId+" contentId:"+contentId);
            }
            WarrantyContentDataAccess.remove(conn, wcId);
            if (contentId > 0) {
                ContentDataAccess.remove(conn, contentId);
            }
            return true;
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static AssetDataVector getAssetCategoriesForAssets(AssetDataVector assets) throws Exception {

        IdVector assetIds = new IdVector();
        AssetDataVector assetCat = new AssetDataVector();
        APIAccess factory = APIAccess.getAPIAccess();
        Asset assetEjb = factory.getAssetAPI();

        if (assets != null && !assets.isEmpty()) {

            Iterator it = assets.iterator();
            while (it.hasNext()) {
                AssetData assetData = (AssetData) it.next();
                if (assetData.getParentId() > 0 ) {
                    assetIds.add(new Integer(assetData.getParentId()));
                }
            }
            if (!assetIds.isEmpty()) {
                AssetSearchCriteria criteria = new AssetSearchCriteria();
                criteria.setAssetIds(assetIds);
                criteria.setShowInactive(true);
                criteria.setAssetTypeCd(RefCodeNames.ASSET_TYPE_CD.CATEGORY);
                assetCat = assetEjb.getAssetDataByCriteria(criteria);
            }
        }
        return assetCat;
    }

}
