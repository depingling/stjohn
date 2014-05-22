package com.cleanwise.service.api.dao;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Hashtable;
import org.apache.log4j.*;

/**
 * Title:        ServiceDAO
 * Description:  Data Access Object helper class.
 * Purpose:      Provide access to service information.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         13.01.2007
 * Time:         16:46:48
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class ServiceDAO {
    private static final Logger log = Logger.getLogger(ServiceDAO.class);

    public ServiceDAO() {
    }

    private ItemData getItemDVByItemId(Connection pCon, int pItemId) throws Exception {

        return ItemDataAccess.select(pCon, pItemId);
    }

    private ItemMetaDataVector getItemMetaDVByItemId(Connection pCon, int pItemId) throws SQLException {
        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(ItemMetaDataAccess.ITEM_ID,pItemId);
        dbCriteria.addOrderBy(ItemMetaDataAccess.ITEM_ID);
        dbCriteria.addOrderBy(ItemMetaDataAccess.ITEM_META_ID);
        return ItemMetaDataAccess.select(pCon, dbCriteria);
    }

    private ItemMappingDataVector getItemMappingDVByItemId(Connection pCon, int pItemId) throws SQLException {
        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(ItemMappingDataAccess.ITEM_ID, pItemId);
        dbCriteria.addOrderBy(ItemMappingDataAccess.ITEM_ID);
        return ItemMappingDataAccess.select(pCon, dbCriteria);

    }
    public ServiceDetailView getServiceDetail(Connection pCon, int pItemId) throws Exception {
           return null;
    }
    private ItemDataVector getCategoriesByItemId(Connection pCon,int pItemId,int pCatalogId) throws Exception {
        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC,ItemAssocDataAccess.ITEM1_ID,pItemId);
        if(pCatalogId>0) {
        dbCriteria.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC,ItemAssocDataAccess.CATALOG_ID,pCatalogId);
        }
        dbCriteria.addJoinTableEqualTo(ItemAssocDataAccess.CLW_ITEM_ASSOC,ItemAssocDataAccess.ITEM_ASSOC_CD,RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY);
        dbCriteria.addJoinCondition(ItemDataAccess.ITEM_ID,ItemAssocDataAccess.CLW_ITEM_ASSOC,ItemAssocDataAccess.ITEM2_ID);
        return ItemDataAccess.select(pCon, dbCriteria);
    }

    private CatalogStructureDataVector getCatalogStructureDVByItemId(Connection pCon, int pItemId) throws Exception {
        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pItemId);
        dbCriteria.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
        return CatalogStructureDataAccess.select(pCon, dbCriteria);
    }

    private void loadItemMappingToHM(ItemMappingDataVector itemMapDV, HashMap hmItemMapping) throws Exception {
        Iterator it = itemMapDV.iterator();
        while (it.hasNext()) {
            ItemMappingData itemMappingData = (ItemMappingData) it.next();
            if(!hmItemMapping.containsKey(itemMappingData.getItemMappingCd()))
            {
                ItemMappingDataVector imDV=new ItemMappingDataVector();
                imDV.add(itemMappingData);
                hmItemMapping.put(itemMappingData.getItemMappingCd(), imDV);
            }
            else
            {
              ItemMappingDataVector imDV= (ItemMappingDataVector) hmItemMapping.get(itemMappingData.getItemMappingCd());
              imDV.add(itemMappingData);
              hmItemMapping.put(itemMappingData.getItemMappingCd(),imDV);
            }
        }
    }

    private void loadItemMetaToHT(ItemMetaDataVector itemMetaDV, Hashtable htItemMeta) throws Exception {
        if (itemMetaDV != null && itemMetaDV.size() > 0&& htItemMeta!=null) {
            Iterator it = itemMetaDV.iterator();
            while (it.hasNext()) {
                ItemMetaData itemMetaData = (ItemMetaData) it.next();
                if (!htItemMeta.containsKey(itemMetaData.getNameValue())) {
                    ItemMetaDataVector imDV = new ItemMetaDataVector();
                    imDV.add(itemMetaData);
                    htItemMeta.put(itemMetaData.getNameValue(), imDV);
                } else {
                    ItemMetaDataVector imDV = (ItemMetaDataVector) htItemMeta.get(itemMetaData.getNameValue());
                    imDV.add(itemMetaData);
                    htItemMeta.put(itemMetaData.getNameValue(), imDV);
                }
            }
        }
    }

     /**
     * Gets data collection from clw_bus_entity table
     *
     * @param conn         Connection
     * @param busEntityIds ids collection
     * @return BusEntityData   collection
     * @throws com.cleanwise.service.api.util.DataNotFoundException Data not found
     * @throws RemoteException       Required by EJB 1.0
     */
    private BusEntityDataVector getBusEntityDataVector(Connection conn, IdVector busEntityIds) throws Exception {
        try {
            BusEntityDataVector result = BusEntityDataAccess.select(conn, busEntityIds);
            if (result != null && result.size() > 0) {
                return result;
            } else {
                throw new DataNotFoundException("BusEntity not found");
            }
        }
        catch (DataNotFoundException e) {
            String mess = "Can't getting bus entity.";
            throw new DataNotFoundException(mess + e.getMessage());
        } catch (SQLException e) {
            String mess = "Can't getting bus entity.";
            throw new RemoteException(mess + e.getMessage());
        }
    }


    /**
     * Gets all service by criteria
     *
     * @param criteria ServiceSearchCriteria
     * @return ServiceViewVector
     * @throws RemoteException if an error occurs
     */
    public ServiceViewVector getServicesViewVector(Connection con, ServiceSearchCriteria criteria) throws Exception {
        ServiceViewVector result = null;
        DBCriteria dbc = convertToDBCriteria(criteria);
        ItemDataVector serviceDV = ItemDataAccess.select(con, dbc);

        if (serviceDV != null) {
            result = new ServiceViewVector();
            Iterator it = groupById(serviceDV).iterator();
            while (it.hasNext()) {
                ServiceView serviceView = ServiceView.createValue();
                ItemData service = (ItemData) it.next();
                serviceView.setServiceId(service.getItemId());
                serviceView.setServiceName(service.getShortDesc());
                serviceView.setStatusCd(service.getItemStatusCd());
                result.add(serviceView);
            }

        }
        return result;
    }

    private DBCriteria convertToDBCriteria(ServiceSearchCriteria criteria) {
        DBCriteria dbCriteria = new DBCriteria();
        if (criteria != null) {
            dbCriteria.addEqualTo(ItemDataAccess.ITEM_TYPE_CD, RefCodeNames.ITEM_TYPE_CD.SERVICE);
            if (criteria.getServiceId() > 0) {
                dbCriteria.addEqualTo(ItemDataAccess.ITEM_ID, criteria.getServiceId());
            }
            if (criteria.getServiceName() != null && criteria.getServiceName().trim().length() > 0) {
                if (criteria.getSearchNameTypeCd() != null) {
                    if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(criteria.getSearchNameTypeCd())) {
                        dbCriteria.addBeginsWith(ItemDataAccess.SHORT_DESC, criteria.getServiceName());
                    } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(criteria.getSearchNameTypeCd())) {
                        dbCriteria.addContains(ItemDataAccess.SHORT_DESC, criteria.getServiceName());
                    } else if (RefCodeNames.SEARCH_TYPE_CD.ID.equals(criteria.getSearchNameTypeCd())) {
                        dbCriteria.addEqualTo(ItemDataAccess.ITEM_ID, Integer.parseInt(criteria.getServiceName()));
                    } else {
                        dbCriteria.addEqualTo(ItemDataAccess.SHORT_DESC, criteria.getServiceName());
                    }
                } else {
                    dbCriteria.addEqualTo(ItemDataAccess.SHORT_DESC, criteria.getServiceName());
                }
            }
            if (criteria.getServiceStatusCd() != null) {
                dbCriteria.addEqualTo(AssetDataAccess.STATUS_CD, criteria.getServiceStatusCd());
            } else if (!criteria.getServiceShowInactive()) {
                dbCriteria.addNotEqualTo(ItemDataAccess.ITEM_STATUS_CD, RefCodeNames.ITEM_STATUS_CD.INACTIVE);
            }
            if (criteria.getStoreId() > 0) {
                DBCriteria isolCriteria = new DBCriteria();
                DBCriteria isolCriteria2 = new DBCriteria();
                isolCriteria.addJoinTable(CatalogDataAccess.CLW_CATALOG + " CATALOG");
                isolCriteria.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC + " CATALOG_ASSOC");
                dbCriteria.addJoinTable(CatalogAssocDataAccess.CLW_CATALOG_ASSOC + " CAT_ASSOC_ST");
                dbCriteria.addJoinTable(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE + " CAT_ST");

                isolCriteria.addEqualTo("CATALOG." + CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.STORE);
                isolCriteria.addEqualTo("CATALOG." + CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
                isolCriteria.addCondition("CATALOG." + CatalogDataAccess.CATALOG_ID + "=" + "CATALOG_ASSOC." + CatalogAssocDataAccess.CATALOG_ID);
                isolCriteria.addEqualTo("CATALOG_ASSOC." + CatalogAssocDataAccess.BUS_ENTITY_ID, criteria.getStoreId());
                isolCriteria.addEqualTo("CATALOG_ASSOC." + CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

                isolCriteria2.addEqualTo("CAT_ASSOC_ST." + CatalogAssocDataAccess.BUS_ENTITY_ID, criteria.getStoreId());
                isolCriteria2.addEqualTo("CAT_ASSOC_ST." + CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
                if (criteria.getCatalogIds() != null) {
                    isolCriteria2.addOneOf("CAT_ASSOC_ST." + CatalogAssocDataAccess.CATALOG_ID, criteria.getCatalogIds());

                }
                isolCriteria2.addCondition("CAT_ST." + CatalogStructureDataAccess.CATALOG_ID + "=" + "CAT_ASSOC_ST." + CatalogAssocDataAccess.CATALOG_ID);
                isolCriteria2.addEqualTo("CAT_ST." + CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);
                isolCriteria2.addCondition(ItemDataAccess.CLW_ITEM + "." + ItemDataAccess.ITEM_ID + "=" + "CAT_ST." + CatalogStructureDataAccess.ITEM_ID);

                isolCriteria.addDataAccessForJoin(new CatalogDataAccess());
                isolCriteria.addDataAccessForJoin(new CatalogAssocDataAccess());

                dbCriteria.addJoinTable("(" + JoinDataAccess.getSqlSelectIdOnly(null, "count(*) as RECORDS_COUNT", isolCriteria) + ") CATALOGS_COUNT");
                isolCriteria2.addCondition("CATALOGS_COUNT.RECORDS_COUNT=1");
                dbCriteria.addIsolatedCriterita(isolCriteria2);

            } else if (criteria.getCatalogIds() != null) {
                DBCriteria isolCriteria = new DBCriteria();
                dbCriteria.addJoinTable(CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE + " CAT_ST");
                isolCriteria.addCondition(ItemDataAccess.CLW_ITEM + "." + ItemDataAccess.ITEM_ID + "=" + "CAT_ST." + CatalogStructureDataAccess.ITEM_ID);
                isolCriteria.addOneOf("CAT_ST." + CatalogStructureDataAccess.CATALOG_ID, criteria.getCatalogIds());
                dbCriteria.addIsolatedCriterita(isolCriteria);
            }

            if (criteria.getCategory() != null&&criteria.getCategory().trim().length()>0) {

                DBCriteria isolCriteria = new DBCriteria();
                dbCriteria.addJoinTable(ItemDataAccess.CLW_ITEM + " CATEGORY");
                dbCriteria.addJoinTable(ItemAssocDataAccess.CLW_ITEM_ASSOC + " ITEM_CATEG");
                if (criteria.getSearchCategoryTypeCd() != null) {
                    if (RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(criteria.getSearchCategoryTypeCd())) {
                        isolCriteria.addBeginsWith("CATEGORY." + ItemDataAccess.SHORT_DESC, criteria.getCategory());
                    } else if (RefCodeNames.SEARCH_TYPE_CD.CONTAINS.equals(criteria.getSearchCategoryTypeCd())) {
                        isolCriteria.addContains("CATEGORY." + ItemDataAccess.SHORT_DESC, criteria.getCategory());
                    } else {
                        isolCriteria.addEqualTo("CATEGORY." + ItemDataAccess.SHORT_DESC, criteria.getCategory());
                    }
                } else {
                    isolCriteria.addEqualTo("CATEGORY." + ItemDataAccess.SHORT_DESC, criteria.getCategory());
                }

                isolCriteria.addCondition("CATEGORY." + ItemDataAccess.ITEM_ID + "=" + "ITEM_CATEG." + ItemAssocDataAccess.ITEM2_ID);
                isolCriteria.addCondition("ITEM_CATEG." + ItemAssocDataAccess.ITEM1_ID + "=" + ItemDataAccess.CLW_ITEM + "." + ItemDataAccess.ITEM_ID);
                isolCriteria.addEqualTo("ITEM_CATEG." + ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.SERVICE_PARENT_CATEGORY);
                dbCriteria.addIsolatedCriterita(isolCriteria);

            }

        }
        return dbCriteria;
    }

     public ServiceData getServiceData(Connection pCon, int pServiceId) throws Exception
        {
         return    getServiceData(pCon,pServiceId,0);
        }


    public ServiceData getServiceData(Connection pCon, int pServiceId, int pForCatalogId) throws Exception {

        ServiceData serviceData = null;
        try {
            serviceData = ServiceData.createValue();
            ItemData itemData = getItemDVByItemId(pCon, pServiceId);
            ItemMetaDataVector itemMetaDV = getItemMetaDVByItemId(pCon, pServiceId);
            ItemMappingDataVector itemMapDV = getItemMappingDVByItemId(pCon, pServiceId);

            HashMap hmItemMapping = new HashMap();
            loadItemMappingToHM(itemMapDV, hmItemMapping);
            Hashtable  htItemMeta=new Hashtable();
            loadItemMetaToHT(itemMetaDV,htItemMeta);
            ItemMappingDataVector distrItemMappingDV = (ItemMappingDataVector) hmItemMapping.get(RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR);
            IdVector distIds = getDistIds(distrItemMappingDV);

            BusEntityDataVector distrEntity = null;
            if (distIds != null && distIds.size() > 0) {
                try {
                    distrEntity = getBusEntityDataVector(pCon, distIds);
                } catch (DataNotFoundException e) {
                    log.info(e.getMessage());
                }
            }
            CatalogCategoryDataVector catDV = new CatalogCategoryDataVector();
            if (pForCatalogId > 0) {
                ItemDataVector iDV = getCategoriesByItemId(pCon, itemData.getItemId(),pForCatalogId);
                for (int ii = 0; iDV != null && ii < iDV.size(); ii++) {
                    CatalogCategoryData catalogCategoryD = new CatalogCategoryData();
                    ItemData catData = (ItemData) iDV.get(ii);
                    catalogCategoryD.setItemData(catData);
                    catDV.add(catalogCategoryD);
                    }
                }


            serviceData.setItemData(itemData);
            serviceData.setDistrBusEntityDV(distrEntity);
            serviceData.setDistrMappingDV((ItemMappingDataVector) hmItemMapping.get(RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR));
            serviceData.setCatalogCategoryDV(catDV);
            serviceData.setItemMeta(htItemMeta);
            if (pForCatalogId > 0) {
                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, pForCatalogId);
                dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, pServiceId);

                CatalogStructureDataVector catalogStructureDV = new CatalogStructureDataVector();

                try {
                    catalogStructureDV = CatalogStructureDataAccess.select(pCon, dbc);
                    if (catalogStructureDV != null && catalogStructureDV.size() > 1)
                        throw new Exception("Multiple record");
                    if (catalogStructureDV.size() == 0) throw new Exception("Catalog structure data not found");
                    serviceData.setCatalogStructureData((CatalogStructureData) catalogStructureDV.get(0));
                    int distrForCatId = 0;
                    if (serviceData.getCatalogStructureData() != null) {
                        distrForCatId = serviceData.getCatalogStructureData().getBusEntityId();
                    }
                    if (distrForCatId > 0) {
                        IdVector ids = new IdVector();
                        ids.add(new Integer(distrForCatId));
                        BusEntityDataVector catalogDistr = getBusEntityDataVector(pCon, ids);
                        if (catalogDistr != null && catalogDistr.size() > 1)
                            throw new Exception("Multiple catalog distributor");
                        if (catalogDistr.size() == 0) throw new Exception("Catalog distributor not found");
                        serviceData.setCatalogDistributor((BusEntityData) catalogDistr.get(0));
                    } else {
                        throw new Exception("Missing catalog distributor id : " + distrForCatId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceData;
    }

    private IdVector getDistIds(ItemMappingDataVector distrItemMappingDV) {

        IdVector ids = new IdVector();
        if(distrItemMappingDV!=null){
        Iterator it = distrItemMappingDV.iterator();
        while (it.hasNext()) {
            ids.add(new Integer(((ItemMappingData) it.next()).getBusEntityId()));
        }
        }
        return ids;
    }

    private ItemDataVector groupById(ItemDataVector itemDV) {

        ItemDataVector result =new ItemDataVector();
        HashMap ids=new HashMap();
        Iterator it = itemDV.iterator();
        while (it.hasNext()) {
            ItemData itemData = (ItemData) it.next();
            if(!ids.containsValue(String.valueOf(itemData.getItemId())))
            {
              ids.put(String.valueOf(itemData.getItemId()),String.valueOf(itemData.getItemId()));
              result.add(itemData);
            }
        }
        return result;
    }

    public IdVector getServicesIdsBySiteCatalog(Connection conn,int pSiteId,int pCatalogId) throws Exception {
       try {

            DBCriteria dbCriteria=new DBCriteria();
            dbCriteria.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);
            dbCriteria.addJoinCondition(CatalogStructureDataAccess.CATALOG_ID,CatalogAssocDataAccess.CLW_CATALOG_ASSOC,CatalogAssocDataAccess.CATALOG_ID);
            dbCriteria.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC,CatalogAssocDataAccess.CATALOG_ASSOC_CD,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
            dbCriteria.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC,CatalogAssocDataAccess.BUS_ENTITY_ID,pSiteId);
            dbCriteria.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);

            CatalogStructureDataVector catDV = CatalogStructureDataAccess.select(conn,dbCriteria);

        if (catDV != null && catDV.size() > 0) {
                return getItemIdsFromCSDataVector(catDV);
            } else {
                throw new DataNotFoundException("Services not found");
            }
        }
        catch (DataNotFoundException e) {
            String mess = "Can't getting Services.";
            throw new DataNotFoundException(mess + e.getMessage());
        } catch (Exception e) {
            String mess = "Can't getting Services.";
            throw new RemoteException(mess + e.getMessage());
        }
    }

    /**
     *Associates specified bus entity ids to the supplied service provider.
     *@param conn			 a valid @see Connection object
     *@param pSPId         the service provider id
     *@param busEntityIds    the bus entity ids to associate to the service provider
     *@param busEntityTypeCd used to determine the association type, no actual checking is done
     */
    public static void addBusEntityAssociations(Connection conn, int pSPId, IdVector busEntityIds, String busEntityTypeCd)
        throws SQLException, RemoteException{
        
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pSPId);
        crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, busEntityIds);
        IdVector existingBusEntityIds = BusEntityAssocDataAccess.selectIdOnly(conn, BusEntityAssocDataAccess.BUS_ENTITY2_ID, crit);
        
        Iterator it = busEntityIds.iterator();
        while(it.hasNext()){
            Integer bid = (Integer) it.next();
            if(bid != null && bid.intValue() != 0 && !existingBusEntityIds.contains(bid)){
                BusEntityServicesAPI.saveBusEntAssociation(
                    false,
                    pSPId,
                    bid.intValue(),
                    busEntityTypeCd,
                    conn
                );
            }
        }

    }    
    
    private IdVector getItemIdsFromCSDataVector(CatalogStructureDataVector catDV) {
        IdVector ids=new IdVector();
        Iterator it=catDV.iterator();
        while(it.hasNext())
        {
          ids.add(new Integer(((CatalogStructureData)it.next()).getItemId()));
        }
       return ids;
    }


}
