package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.CatalogBean;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.CurrencyData;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.PreparedStatement;

public class InboundXpedxLoaderHelper {

    protected static Logger log = Logger.getLogger(InboundXpedxLoaderHelper.class);

    public static final String IMAGE_BASE_PATH = "/en/products/images/";
    private static final String X = "x";
    private static final String IMAGE = "Image";
    private static final String ITEM_IMAGE ="ItemImage";
    private static final String XSUITE_APP = "xsuite-app";
    private static final String YES = "YES";
    private static final String NO = "NO";

    public static Map<String, Integer> getAccountIdsByAccountRef(
            Connection pCon, int pTradingPartnerId, Set<String> pAccounts,
            List<String> pErrors) throws Exception {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,
                pTradingPartnerId);
        cr.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD,
                RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
        if (pAccounts != null && pAccounts.size() > 0) {
            cr.addOneOf(TradingPartnerAssocDataAccess.GROUP_SENDER_OVERRIDE,
                    new ArrayList<String>(pAccounts));
        }
        TradingPartnerAssocDataVector list = TradingPartnerAssocDataAccess
                .select(pCon, cr);
        for (int i = 0; i < list.size(); i++) {
            TradingPartnerAssocData item = (TradingPartnerAssocData) list
                    .get(i);
            String key = item.getGroupSenderOverride()+"_"+ item.getBusEntityId();
            Integer oldVal = result.put(key, item
                    .getBusEntityId());
            if (oldVal != null) {
                pErrors.add("Account for reference number:"
                        + item.getGroupSenderOverride() + " already exist!");
            }
        }
        return result;
    }

    public static Map<ComparableList, Integer> getCatalogIdsByAccountCatalogRef(
            Connection pCon, int pTradingPartnerId,
            Set<ComparableList> pAccountCatalogRefs, List<String> pErrors)
            throws Exception {
        Map<ComparableList, Integer> result = new TreeMap<ComparableList, Integer>();
        if (pAccountCatalogRefs.isEmpty() == true) {
            return result;
        }
        Set<String> accountRefs = new TreeSet<String>();
        Set<String> catalogRefs = new TreeSet<String>();
        for (ComparableList i : pAccountCatalogRefs) {
            accountRefs.add(((String) i.get(0)).trim());
            catalogRefs.add(((String) i.get(1)).trim());
        }

        StringBuilder sql = new StringBuilder();
        DBCriteria inAccounts = new DBCriteria();
        DBCriteria inCatalogs = new DBCriteria();
        sql.append("SELECT DISTINCT trim(t1.loader_field) AS catalog_ref,\n"
                + "    trim(t3.group_sender_override) AS account_ref,\n"
                + "    t1.catalog_id AS catalog_id\n");
        sql.append("FROM clw_catalog t1\n");
        sql.append("INNER JOIN clw_catalog_assoc t2  "
                + "ON t1.catalog_id  = t2.catalog_id\n");
        sql.append("    AND t2.catalog_assoc_cd = '"
                + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT + "'\n");
        sql.append("INNER JOIN clw_trading_partner_assoc t3 "
                + " ON t2.bus_entity_id = t3.bus_entity_id\n");
        sql.append("    AND t3.trading_partner_id = " + pTradingPartnerId
                + "\n");
        sql.append("    AND t3.trading_partner_assoc_cd = '"
                + RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT + "'\n");
        inAccounts.addOneOf("trim(t3.group_sender_override)",
                new ArrayList<String>(accountRefs));
        sql.append("    AND " + inAccounts.getWhereClause() + "\n");
        inCatalogs.addOneOf("trim(t1.loader_field)", new ArrayList<String>(
                catalogRefs));
        sql.append("WHERE " + inCatalogs.getWhereClause() + "\n");
        Statement statement = null;
        ResultSet resultSet = null;
        log.debug("getCatalogIdsByAccountCatalogRef SQL:\n" + sql);
        try {
            statement = pCon.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {
                String accountRef = resultSet.getString("account_ref");
                String catalogRef = resultSet.getString("catalog_ref");
                int catalogId = resultSet.getInt("catalog_id");
                result.put(ComparableList.createValue(accountRef.trim(),
                        catalogRef.trim()), catalogId);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return result;
    }

    public static Map<ComparableList, Integer> getSiteIdsByAccountSiteRef(
            Connection pCon, int pTradingPartnerId,
            Set<ComparableList> pAccountsSitesRefs, List<String> pErrors)
            throws Exception {
        Map<ComparableList, Integer> result = new TreeMap<ComparableList, Integer>();
        if (pAccountsSitesRefs == null || pAccountsSitesRefs.isEmpty() == true) {
            return result;
        }
        Set<String> sites = new TreeSet<String>();
        Set<String> accounts = new TreeSet<String>();
        for (ComparableList i : pAccountsSitesRefs) {
            accounts.add((String) i.get(0));
            sites.add((String) i.get(1));
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT t1.clw_value AS site_ref,\n"
                + "    t3.group_sender_override AS account_ref,\n"
                + "    t1.bus_entity_id AS site_id\n");
        sql.append("FROM clw_property t1\n");
        sql.append("INNER JOIN clw_bus_entity_assoc t2 "
                + " ON t1.bus_entity_id = t2.bus_entity1_id\n");
        sql.append("    AND t2.bus_entity_assoc_cd = '"
                + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'\n");
        sql.append("INNER JOIN clw_trading_partner_assoc t3 "
                + " ON t2.bus_entity2_id = t3.bus_entity_id\n");
        sql.append("    AND t3.trading_partner_id = '" + pTradingPartnerId
                + "'\n");
        sql.append("    AND t3.trading_partner_assoc_cd = '"
                + RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT + "'\n");
        DBCriteria inAccounts = new DBCriteria();
        inAccounts.addOneOf("trim(t3.group_sender_override)",
                new ArrayList<String>(accounts));
        sql.append("    AND " + inAccounts.getWhereClause() + "\n");
        sql.append("WHERE t1.short_desc = '"
                + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "'\n");
        DBCriteria inSites = new DBCriteria();
        inSites.addOneOf("trim(t1.clw_value)", new ArrayList<String>(sites));
        sql.append("    AND " + inSites.getWhereClause() + "\n");
        Statement statement = null;
        ResultSet resultSet = null;
        log.debug("getSiteIdsByAccountSiteRef SQL:\n" + sql);
        try {
            statement = pCon.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {
                String accountRef = resultSet.getString("account_ref");
                String siteRef = resultSet.getString("site_ref");
                int siteId = resultSet.getInt("site_id");
                result.put(ComparableList.createValue(accountRef.trim(),
                        siteRef.trim()), siteId);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return result;
    }

    public static Map<Integer, Integer> getStoreIdsByAccountId(Connection pCon,
            Collection<Integer> accountIds, List<String> errors)
            throws Exception {
        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
        if (accountIds != null && accountIds.isEmpty() == false) {
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            cr.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                    new ArrayList<Integer>(accountIds));
            BusEntityAssocDataVector list = BusEntityAssocDataAccess.select(
                    pCon, cr);
            for (int i = 0; i < list.size(); i++) {
                BusEntityAssocData item = (BusEntityAssocData) list.get(i);
                int key = item.getBusEntity1Id();
                int val = item.getBusEntity2Id();
                Integer oldVal = result.put(key, val);
                if (oldVal != null) {
                    errors.add("Account :" + key
                            + " has assigned more than 1 stores:" + oldVal
                            + "," + val + "!");
                }
            }
        }
        return result;
    }

    public static Map<Integer, Integer> getAccountIdsBySiteId(Connection pCon,
            Collection<Integer> siteIds, List<String> errors) throws Exception {
        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
        if (siteIds != null && siteIds.isEmpty() == false) {
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            cr.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                    new ArrayList<Integer>(siteIds));
            BusEntityAssocDataVector list = BusEntityAssocDataAccess.select(
                    pCon, cr);
            for (int i = 0; i < list.size(); i++) {
                BusEntityAssocData item = (BusEntityAssocData) list.get(i);
                int key = item.getBusEntity1Id();
                int val = item.getBusEntity2Id();
                Integer oldVal = result.put(key, val);
                if (oldVal != null) {
                    errors.add("Site :" + key
                            + " has assigned more than 1 account:" + oldVal
                            + "," + val + "!");
                }
            }
        }
        return result;
    }

    public static Map<Integer, Set<Integer>> getBusEntIdsByCatalogId(
            Connection pCon, Collection<Integer> catalogIds,
            List<String> pErrors) throws Exception {
        Map<Integer, Set<Integer>> result = new TreeMap<Integer, Set<Integer>>();
        if (catalogIds != null && catalogIds.isEmpty() == false) {
            DBCriteria cr = new DBCriteria();
            cr.addOneOf(CatalogAssocDataAccess.CATALOG_ID,
                    new ArrayList<Integer>(catalogIds));
            CatalogAssocDataVector list = CatalogAssocDataAccess.select(pCon,
                    cr);
            for (int i = 0; i < list.size(); i++) {
                CatalogAssocData item = (CatalogAssocData) list.get(i);
                int key = item.getCatalogId();
                Set<Integer> val = result.get(key);
                if (val == null) {
                    val = new TreeSet<Integer>();
                    result.put(key, val);
                }
                if (val.contains(item.getBusEntityId()) == true) {
                    pErrors.add("Catalog: " + key
                            + " already has assigned bus entity: "
                            + item.getBusEntityId());
                } else {
                    val.add(item.getBusEntityId());
                }
            }
        }
        return result;
    }

    public static BusEntityDataVector getTradingPartnerEntities(
            Connection pCon, int pTradingPartnerId) throws Exception {
        DBCriteria cr = new DBCriteria();
        cr.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY,
                BusEntityDataAccess.BUS_ENTITY_ID,
                TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC,
                TradingPartnerAssocDataAccess.BUS_ENTITY_ID);
        cr.addJoinTableEqualTo(
                TradingPartnerAssocDataAccess.CLW_TRADING_PARTNER_ASSOC,
                TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,
                pTradingPartnerId);
        BusEntityDataVector result = BusEntityDataAccess.select(pCon, cr);
        return result;
    }

    public static Map<String, Integer> getDistIdsByName(Connection pCon,
            int pTradingPartnerId, List<String> pErrors) throws Exception {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        BusEntityDataVector list = InboundXpedxLoaderHelper
                .getTradingPartnerEntities(pCon, pTradingPartnerId);
        for (int i = 0; i < list.size(); i++) {
            BusEntityData item = (BusEntityData) list.get(i);
            if (RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(item
                    .getBusEntityTypeCd()) == true) {
                String key = item.getShortDesc().trim();
                Integer oldValue = result.get(key);
                int value = item.getBusEntityId();
                if (oldValue != null) {
                    pErrors.add("Was found duplicates for distributor name '"
                            + key + "':" + oldValue + " " + value);
                } else {
                    result.put(key, value);
                }
            }
        }
        return result;
    }

    public static Map<String, Integer> getManufIdsByName(Connection pCon, int pStoreId, List<String> pErrors) throws Exception {

        Map<String, Integer> result = new TreeMap<String, Integer>();

        BusEntitySearchCriteria beSearchCriteria = new BusEntitySearchCriteria();
        beSearchCriteria.setStoreBusEntityIds(Utility.toIdVector(pStoreId));
        BusEntityDataVector list = BusEntityDAO.getBusEntityByCriteria(pCon, beSearchCriteria, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER);

        for (Object aList : list) {

            BusEntityData item = (BusEntityData) aList;
            String key = item.getShortDesc().trim();
            Integer oldValue = result.get(key);

            int value = item.getBusEntityId();
            if (oldValue != null) {
                pErrors.add("Was found duplicates for manufacturer name '" + key + "':" + oldValue + " " + value);
            } else {
                result.put(key, value);
            }

        }

        return result;
    }

    /**
     * Create non-exist catalogs.
     */
    public static Map<ComparableList, Integer> createCatalogs(Connection pCon,
            Set<ComparableList> pAccountCatalogRefs, String pAddModBy)
            throws Exception {
        Map<ComparableList, Integer> result = new TreeMap<ComparableList, Integer>();
        for (ComparableList complexRef : pAccountCatalogRefs) {
            String catalogRef = (String) complexRef.get(1);
            CatalogData catalogData = CatalogData.createValue();
            catalogData.setShortDesc(catalogRef);
            catalogData.setLoaderField(catalogRef);
            catalogData.setAddBy(pAddModBy);
            catalogData.setModBy(pAddModBy);
            catalogData.setCatalogTypeCd(RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
            catalogData.setCatalogStatusCd(RefCodeNames.STATUS_CD.ACTIVE);
            catalogData = CatalogDataAccess.insert(pCon, catalogData);
            result.put(complexRef, catalogData.getCatalogId());
        }
        return result;
    }

    public static void synchronizeCatalogAssoc(Connection pCon,
                                               String pAddModBy,
                                               Map<Integer, Set<Integer>> pBusEntityIdsByCatalog,
                                               List<ComparableList> pComplexKeys,
                                               Map<ComparableList, Integer> pCatalogIdsByComplexKey,
                                               Map<Integer, Integer> pStoreIdsByAccountId,
                                               Map<String, Integer> pAccountIdsByName) throws Exception {
        synchronizeCatalogAssoc(pCon,
                pAddModBy,
                pBusEntityIdsByCatalog,
                pComplexKeys,
                pCatalogIdsByComplexKey,
                pStoreIdsByAccountId,
                pAccountIdsByName,
                null,
                null,
                null);

    }

    public static void synchronizeCatalogAssoc(Connection pCon,
                                               String pAddModBy,
                                               Map<Integer, Set<Integer>> pBusEntIdsByCatalogId,
                                               List<ComparableList>  pComplexKeys,
                                               Map<ComparableList, Integer> pCatalogIdsByComplexKey,
                                               Map<Integer, Integer> pStoreIdsByAccountId,
                                               Map<String, Integer> pAccountIdsByAccountRef,
                                               Map<Integer, Integer> pAccountIdsBySiteId,
                                               Map<ComparableList, Integer> pSiteIdsByAccountSiteRef,
                                               Map<ComparableList, Integer> pNewCatalogs) throws Exception {

    	List inactiveCatalogChecked = new ArrayList();
        for (ComparableList key : pComplexKeys) {

            String accountRef = (String)  key.get(0);

            if (pAccountIdsByAccountRef.containsKey(accountRef)) {

                String siteRef = (String) (( key.size() > 2) ?  key.get(2) : null);

                int catalogId = pCatalogIdsByComplexKey.get(key);

                boolean isNewCatalog = (pNewCatalogs!= null) ? (new ArrayList<Integer>(pNewCatalogs.values())).contains(catalogId) : false;
                if (!isNewCatalog && !inactiveCatalogChecked.contains(catalogId)){
                	setCatalogAndContractActiveIfInactive(pCon, catalogId, pAddModBy);
                	inactiveCatalogChecked.add(catalogId);
                }

                Set<Integer> busEntityIds = pBusEntIdsByCatalogId.get(catalogId);
                if (busEntityIds == null) {
                    busEntityIds = new TreeSet<Integer>();
                    pBusEntIdsByCatalogId.put(catalogId, busEntityIds);
                }

                log.debug("synchronizeCatalogAssoc()=> AccountRef.: " + accountRef
                        + ", SiteRef.: " + siteRef
                        + ", CatalogID: " + catalogId
                        + ", isNewCatalog: " + isNewCatalog);

                if (Utility.isSet(siteRef)
                        && pAccountIdsBySiteId != null
                        && pSiteIdsByAccountSiteRef != null) {

                    int siteId = pSiteIdsByAccountSiteRef.get(ComparableList.createValue(accountRef, siteRef));
                    if (!busEntityIds.contains(siteId)) {
                        createCatalogSiteAssoc(pCon, catalogId, siteId, pBusEntIdsByCatalogId, pAddModBy, isNewCatalog);
                    }

                }

                int accountId = pAccountIdsByAccountRef.get(accountRef);
                if (!(busEntityIds.contains(accountId))) {
                    createCatalogAssoc(pCon,
                            catalogId,
                            accountId,
                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT,
                            pAddModBy);
                }

                int storeId = pStoreIdsByAccountId.get(accountId);
                if (!busEntityIds.contains(storeId)) {
                    createCatalogAssoc(pCon,
                            catalogId,
                            storeId,
                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE,
                            pAddModBy);
                }
            }

        }
    }

    private static void setCatalogAndContractActiveIfInactive(Connection pCon, int catalogId, String pModBy) throws Exception {
    	CatalogData catalogData = CatalogDataAccess.select(pCon, catalogId);
    	if (!catalogData.getCatalogStatusCd().equals(RefCodeNames.STATUS_CD.ACTIVE)){
    		catalogData.setCatalogStatusCd(RefCodeNames.STATUS_CD.ACTIVE);
    		catalogData.setModBy(pModBy);
    		CatalogDataAccess.update(pCon, catalogData);    		
    	}
    	DBCriteria cr = new DBCriteria();
        cr.addEqualTo(ContractDataAccess.CATALOG_ID, catalogId);
        cr.addNotEqualTo(ContractDataAccess.CONTRACT_STATUS_CD, RefCodeNames.STATUS_CD.ACTIVE);
        ContractDataVector cdv = ContractDataAccess.select(pCon, cr);
        for (Object o:cdv){
        	ContractData cd = (ContractData) o;
        	cd.setContractStatusCd(RefCodeNames.STATUS_CD.ACTIVE);
        	cd.setModBy(pModBy);
        	ContractDataAccess.update(pCon, cd);
        }
		
	}

	public static void synchAllCatalogAssocs(Connection pCon,
            String pAddModBy,
            Map<Integer, Set<Integer>> pBusEntityIdsByCatalog,
            Map<ComparableList, Integer> pCatalogIdsByComplexKey,
            Map<Integer, Integer> pStoreIdsByAccountId,
            Map<String, Integer> pAccountIdsByName,
            Map<Integer,Integer> pAccountMainDists,
            Map<String,List<Integer>> pCatalogDists) throws Exception {
    	synchAllCatalogAssocs(pCon, pAddModBy, pBusEntityIdsByCatalog,
    			pCatalogIdsByComplexKey, pStoreIdsByAccountId,
    			pAccountIdsByName, null, null,pAccountMainDists,pCatalogDists);
    }

    public static void synchAllCatalogAssocs(Connection pCon,
                                             String pAddModBy,
                                             Map<Integer, Set<Integer>> pBusEntIdsByCatalogId,
                                             Map<ComparableList, Integer> pCatalogIdsByComplexKey,
                                             Map<Integer, Integer> pStoreIdsByAccountId,
                                             Map<String, Integer> pAccountIdsByAccountRef,
                                             Map<Integer, Integer> pAccountIdsBySiteId,
                                             Map<ComparableList, Integer> pSiteIdsByAccountSiteRef,
                                             Map<Integer, Integer> pAccountMainDists,
                                             Map<String, List<Integer>> pCatalogDists) throws Exception {

        for (Map.Entry<ComparableList, Integer> e : pCatalogIdsByComplexKey.entrySet()) {

            String accountRef = (String) e.getKey().get(0);

            if (pAccountIdsByAccountRef.containsKey(accountRef)) {

                String catalogRef = (String) e.getKey().get(1);
                String siteRef = (String) ((e.getKey().size() > 2) ? e.getKey().get(2) : null);

                int catalogId = e.getValue();
                boolean isNewCatalog = false;

                Set<Integer> busEntityIds = pBusEntIdsByCatalogId.get(catalogId);
                if (busEntityIds == null) {
                    busEntityIds = new TreeSet<Integer>();
                    pBusEntIdsByCatalogId.put(catalogId, busEntityIds);
                }

                if (Utility.isSet(siteRef) && pAccountIdsBySiteId != null && pSiteIdsByAccountSiteRef != null) {
                    int siteId = pSiteIdsByAccountSiteRef.get(ComparableList.createValue(accountRef, siteRef));
                    if (!busEntityIds.contains(siteId)) {
                        createCatalogSiteAssoc(pCon, catalogId, siteId, pBusEntIdsByCatalogId, pAddModBy,isNewCatalog);
                    }
                }

                int accountId = pAccountIdsByAccountRef.get(accountRef);

                if (!(busEntityIds.contains(accountId))) {
                    createCatalogAssoc(pCon, catalogId, accountId,
                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT,
                            pAddModBy);
                }

                int storeId = pStoreIdsByAccountId.get(accountId);
                if (storeId > 0) {
                    //if (busEntityIds.contains(storeId) == false) {
                    createCatalogAssoc(pCon, catalogId, storeId,
                            RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE, pAddModBy);
                    //}
                }

                if (pAccountMainDists != null && pAccountMainDists.size() > 0) {
                    if (pAccountMainDists.get(accountId) != null) {
                        int mainDistId = pAccountMainDists.get(accountId);
                        if (mainDistId > 0) {
                            //if (busEntityIds.contains(mainDistId) == false) {
                            createCatalogAssoc(pCon, catalogId, mainDistId,
                                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR, pAddModBy);
                            //}
                        }
                    }
                }

                if (pCatalogDists != null) {
                    List allDists = pCatalogDists.get(catalogRef);
                    for (Object dist : allDists) {
                        int distId = (Integer) dist;
                        if (distId > 0) {
                            //if (busEntityIds.contains(distId) == false) {
                            createCatalogAssoc(pCon, catalogId, distId,
                                    RefCodeNames.CATALOG_ASSOC_CD.CATALOG_DISTRIBUTOR, pAddModBy);
                            // }
                        }
                    }

                }
            }
        }
    }

    public static CatalogAssocData createCatalogSiteAssoc(Connection pCon,
                                                          int pShoppingCatalogId,
                                                          int pSiteId,
                                                          Map<Integer, Set<Integer>> pExistCatalogLinks,
                                                          String pAddModBy,
                                                          boolean pIsNewCatalog) throws Exception {

        log.debug("createCatalogSiteAssoc()=> BEGIN. SiteID: " + pSiteId);


        CatalogAssocData catalogAssocData;

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pSiteId);
        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
        crit.addJoinTableEqualTo(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        crit.addJoinCondition(CatalogAssocDataAccess.CATALOG_ID, CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_ID);

        CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(pCon, crit);

        if (!caDV.isEmpty()) {

            catalogAssocData = (CatalogAssocData) caDV.remove(0);
            int catalogId = catalogAssocData.getCatalogId();

            if (pShoppingCatalogId != catalogId) {

                log.debug("createCatalogSiteAssoc()=> Updating association, Old CatalogID:" + catalogAssocData.getCatalogId() + ", New CatalogID: " + pShoppingCatalogId);

                if (pIsNewCatalog) {
                  catalogAssocData.setNewCatalogId(pShoppingCatalogId);
                } else if (catalogAssocData.getNewCatalogId()!=pShoppingCatalogId){
                  if (isShoppingCatalogNotEmpty(pCon, pShoppingCatalogId)) {
                    catalogAssocData.setCatalogId(pShoppingCatalogId);
                    catalogAssocData.setNewCatalogId(0);
                  } else {
                    catalogAssocData.setNewCatalogId(pShoppingCatalogId);
                  }
                }

                catalogAssocData.setModBy(pAddModBy);
                CatalogAssocDataAccess.update(pCon, catalogAssocData);

                Set<Integer> busEntityIds = pExistCatalogLinks.get(catalogId);
                if (busEntityIds != null) {
                    busEntityIds.remove(pSiteId);
                }

            } else {
                log.debug("createCatalogSiteAssoc()=> CatalogID: " + catalogAssocData.getCatalogId() + " already assigned to SiteID: " + pSiteId);
            }

        } else {

            log.debug("createCatalogSiteAssoc()=> Creating new association with CatalogID: " + pShoppingCatalogId);
            catalogAssocData = CatalogAssocData.createValue();

            catalogAssocData.setCatalogId(pShoppingCatalogId);
            if (pIsNewCatalog) {
              catalogAssocData.setNewCatalogId(pShoppingCatalogId);
            }

            catalogAssocData.setCatalogAssocCd(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
            catalogAssocData.setBusEntityId(pSiteId);
            catalogAssocData.setAddBy(pAddModBy);
            catalogAssocData.setModBy(pAddModBy);

            catalogAssocData = CatalogAssocDataAccess.insert(pCon, catalogAssocData);

            Set<Integer> busEntityIds = pExistCatalogLinks.get(catalogAssocData.getCatalogId());
            if (busEntityIds != null) {
                busEntityIds.add(catalogAssocData.getBusEntityId());
            }
        }

        if (!caDV.isEmpty()) {

            log.debug("createCatalogSiteAssoc()=> Removing Duplicate Records");

            for (Object oCaDV : caDV) {

                log.debug("createCatalogSiteAssoc()=> Removing duplicate, SiteID: " + pSiteId + ", CatalogID: " + pShoppingCatalogId);
                CatalogAssocData duplAxssoc = (CatalogAssocData) oCaDV;

                CatalogAssocDataAccess.remove(pCon, duplAxssoc.getCatalogAssocId());

                Set<Integer> busEntityIds = pExistCatalogLinks.get(duplAxssoc.getCatalogId());
                if (busEntityIds != null && duplAxssoc.getBusEntityId() != pSiteId) {
                    busEntityIds.remove(duplAxssoc.getBusEntityId());
                }
            }
        }

        log.debug("createCatalogSiteAssoc()=> END.");

        return catalogAssocData;
    }

    public static CatalogAssocData createCatalogAssoc(Connection pCon,
                                                      int pCatalogId,
                                                      int pBusEntityId,
                                                      String pCatalogAssocCd,
                                                      String pAddModBy) throws Exception {



        DBCriteria crit = new DBCriteria();
        CatalogAssocData catalogAssocData = CatalogAssocData.createValue();
        crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, pCatalogAssocCd);
        crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pCatalogId);

        CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(pCon, crit);

        if (caDV != null && caDV.size() > 0) {

            catalogAssocData = (CatalogAssocData) caDV.get(0);
            catalogAssocData.setCatalogId(pCatalogId);
            catalogAssocData.setModBy(pAddModBy);
            CatalogAssocDataAccess.update(pCon, catalogAssocData);

        } else {

            catalogAssocData.setCatalogId(pCatalogId);
            catalogAssocData.setCatalogAssocCd(pCatalogAssocCd);
            catalogAssocData.setBusEntityId(pBusEntityId);
            catalogAssocData.setAddBy(pAddModBy);
            catalogAssocData.setModBy(pAddModBy);
            catalogAssocData = CatalogAssocDataAccess.insert(pCon, catalogAssocData);
        }

        return catalogAssocData;
    }

    public static Map<Integer, FreightTableData> getFreightTables(
            Connection pCon, Set<Integer> pFreightTableIds) throws Exception {
        Map<Integer, FreightTableData> result = new TreeMap<Integer, FreightTableData>();
        DBCriteria cr = new DBCriteria();
        cr.addOneOf(FreightTableDataAccess.FREIGHT_TABLE_ID,
                new ArrayList<Integer>(pFreightTableIds));
        FreightTableDataVector list = FreightTableDataAccess.select(pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            FreightTableData item = (FreightTableData) list.get(i);
            result.put(item.getFreightTableId(), item);
        }
        return result;
    }

    public static Map<Integer, Map<Integer, Set<Integer>>> getItemIdsByCategoryIdByCatalogId(
            Connection pCon, Collection<Integer> pCatalogIds) throws Exception {
        Map<Integer, Map<Integer, Set<Integer>>> result = new TreeMap<Integer, Map<Integer, Set<Integer>>>();
        DBCriteria cr = new DBCriteria();

        log.info("getItemIdsByCategoryIdByCatalogId()=> pCatalogIds:"+pCatalogIds);
        cr.addOneOf(ItemAssocDataAccess.CATALOG_ID, new ArrayList<Integer>(
                pCatalogIds));
        cr.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
        ItemAssocDataVector list = ItemAssocDataAccess.select(pCon, cr);
        for (int i = 0; i < list.size(); i++) {
            ItemAssocData item = (ItemAssocData) list.get(i);
            Map<Integer, Set<Integer>> categoryMap = result.get(item
                    .getCatalogId());
            if (categoryMap == null) {
                categoryMap = new TreeMap<Integer, Set<Integer>>();
                result.put(item.getCatalogId(), categoryMap);
            }
            Set<Integer> itemIds = categoryMap.get(item.getItem2Id());
            if (itemIds == null) {
                itemIds = new TreeSet<Integer>();
                categoryMap.put(item.getItem2Id(), itemIds);
            }
            itemIds.add(item.getItem1Id());
        }
        return result;
    }

    public static Map<Integer, Integer> getCategoryParentIdByChildId(Connection pCon, int pStoreCatalogId) throws Exception {

        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(ItemAssocDataAccess.CATALOG_ID, pStoreCatalogId);
        cr.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD, RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY);

        ItemAssocDataVector list = ItemAssocDataAccess.select(pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            ItemAssocData item = (ItemAssocData) list.get(i);
            result.put(item.getItem1Id(), item.getItem2Id());
        }

        return result;

    }


    public static Map<ComparableList, Integer> getItemIdsByDistItemRef(
            Connection pCon, Set<ComparableList> pDistItemRefs,
            Map<Integer, String> pDistNameById, List<String> pErrors)
            throws Exception {
        Set<Integer> distIds = new HashSet<Integer>();
        Set<String> itemNums = new HashSet<String>();
        for (ComparableList item : pDistItemRefs) {
            distIds.add((Integer) item.get(0));
            itemNums.add((String) item.get(1));
        }
        DBCriteria cr = new DBCriteria();
        cr.addOneOf(ItemMappingDataAccess.BUS_ENTITY_ID,
                new ArrayList<Integer>(distIds));
        cr.addOneOf(ItemMappingDataAccess.ITEM_NUM, new ArrayList<String>(
                itemNums));
        ItemMappingDataVector list = ItemMappingDataAccess.select(pCon, cr);
        Map<ComparableList, Integer> result = new TreeMap<ComparableList, Integer>();
        Map<Integer, ComparableList> alreadyMapped = new TreeMap<Integer, ComparableList>();
        for (int i = 0; list != null && i < list.size(); i++) {
            ItemMappingData item = (ItemMappingData) list.get(i);
            int distId = item.getBusEntityId();
            String distName = pDistNameById.get(distId);
            if (distName == null) {
                pErrors.add("Not found distributor name by ID:"
                        + item.getBusEntityId());
                continue;
            }
            ComparableList distIdItemNumKey = ComparableList.createValue(
                    distId, item.getItemNum().trim());
            ComparableList distNameItemNumKey = ComparableList.createValue(
                    distName, item.getItemNum().trim());
            int value = item.getItemId();
            if (pDistItemRefs.contains(distIdItemNumKey)) {
                Integer oldValue = result.put(distNameItemNumKey, value);
                if (oldValue != null && oldValue != value) {
                    pErrors.add("Was found duplicate for '"
                            + distNameItemNumKey + "':" + oldValue + " "
                            + value);
                }
                ComparableList existKey = alreadyMapped.get(value);
                if (existKey != null) {
                    pErrors.add("Item " + value + " has duplicate mapping:"
                            + existKey + "," + distNameItemNumKey);
                }
                alreadyMapped.put(value, distNameItemNumKey);
            }
        }
        return result;
    }


    public static Map<ComparableList, ItemData> getItemDataByDistItemRef(Connection pCon, Map<ComparableList, Integer> pItemIdsByDistItemRef) throws SQLException {

        Map<ComparableList, ItemData> result = new HashMap<ComparableList, ItemData>();
        DBCriteria crit = new DBCriteria();
        crit.addOneOf(ItemDataAccess.ITEM_ID, new ArrayList<Integer>(pItemIdsByDistItemRef.values()));

        ItemDataVector items = ItemDataAccess.select(pCon, crit);

        Map<Integer, ItemData> itemMap = new HashMap<Integer, ItemData>();
        for (int i = 0; items != null && i < items.size(); i++) {
            ItemData item = (ItemData) items.get(i);
            itemMap.put(item.getItemId(), item);
        }

        for (Map.Entry<ComparableList, Integer> entry : pItemIdsByDistItemRef.entrySet()) {
            result.put(entry.getKey(), itemMap.get(entry.getValue()));
        }

        return result;
    }

    public static Map<Integer, Set<Integer>> getCategoryIdsByCatalogId(
            Connection pCon, Collection<Integer> pCatalogIds) throws Exception {
        Map<Integer, Set<Integer>> result = new TreeMap<Integer, Set<Integer>>();
        DBCriteria cr = new DBCriteria();
        cr.addOneOf(CatalogStructureDataAccess.CATALOG_ID,
                new ArrayList<Integer>(pCatalogIds));
        cr.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
        log.info("getCategoryIdsByCatalogId:" + cr.getWhereClause());
        CatalogStructureDataVector list = CatalogStructureDataAccess.select(
                pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            CatalogStructureData item = (CatalogStructureData) list.get(i);
            Set<Integer> val = result.get(item.getCatalogId());
            if (val == null) {
                val = new TreeSet<Integer>();
                result.put(item.getCatalogId(), val);
            }
            val.add(item.getItemId());
        }
        return result;
    }

    public static Map<Integer, Set<Integer>> getCategoryIdsByStoreCatalogId(
            Connection pCon, int pCatalogId) throws Exception {
        Map<Integer, Set<Integer>> result = new TreeMap<Integer, Set<Integer>>();
        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,pCatalogId);
        cr.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
        log.info("getCategoryIdsByStoreCatalogId:" + cr.getWhereClause());
        CatalogStructureDataVector list = CatalogStructureDataAccess.select(pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            CatalogStructureData item = (CatalogStructureData) list.get(i);
            Set<Integer> val = result.get(item.getCatalogId());
            if (val == null) {
                val = new TreeSet<Integer>();
                result.put(item.getCatalogId(), val);
            }
            val.add(item.getItemId());
        }
        return result;
    }

    public static Map<Integer, ContractData> getContracts(Connection pCon,
            Collection<Integer> pCatalogIds) throws Exception {
        Map<Integer, ContractData> result = new TreeMap<Integer, ContractData>();
        DBCriteria cr = new DBCriteria();
        cr.addOneOf(ContractDataAccess.CATALOG_ID, new ArrayList<Integer>(
                pCatalogIds));
        ContractDataVector cdv = ContractDataAccess.select(pCon, cr);
        for (int i = 0; cdv != null && i < cdv.size(); i++) {
            ContractData contractData = (ContractData) cdv.get(i);
            result.put(contractData.getCatalogId(), contractData);
        }
        return result;
    }

    public static Map<Integer, Map<Integer, ContractItemData>> getContractItemsByContractId(
            Connection pCon, Collection<Integer> pContractIds,
            List<String> pErrors) throws Exception {
        Map<Integer, Map<Integer, ContractItemData>> result = new TreeMap<Integer, Map<Integer, ContractItemData>>();
        DBCriteria cr = new DBCriteria();
        cr.addOneOf(ContractItemDataAccess.CONTRACT_ID, new ArrayList<Integer>(
                pContractIds));
        ContractItemDataVector list = ContractItemDataAccess.select(pCon, cr);
        for (int i = 0; list != null && i < list.size(); i++) {
            ContractItemData item = (ContractItemData) list.get(i);
            Map<Integer, ContractItemData> val = result.get(item
                    .getContractId());
            if (val == null) {
                val = new TreeMap<Integer, ContractItemData>();
                result.put(item.getContractId(), val);
            }
            ContractItemData oldItem = val.put(item.getItemId(), item);
            if (oldItem != null) {
                pErrors.add("Was found duplicate contract item for contract:"
                        + item.getContractId() + " items:"
                        + oldItem.getItemId() + "," + item.getItemId());
            }
        }
        return result;
    }


    public static Map<String, Integer> getPriceDecimalsMap(Connection pCon, Set<String> pLocales) throws Exception {

        log.info("getPriceDecimalsMap()=> BEGIN. pLocales: " + pLocales);

        Map<String, Integer> result = new HashMap<String, Integer>();

        DBCriteria crit = new DBCriteria();
        crit.addOneOf(CurrencyDataAccess.LOCALE, new ArrayList<String>(pLocales));

        CurrencyDataVector currDV = CurrencyDataAccess.select(pCon, crit);
        for (Object oCurr : currDV) {
            CurrencyData curr = (CurrencyData) oCurr;
            result.put(curr.getLocale(), curr.getDecimals());
        }

        log.info("getPriceDecimalsMap()=> END. result: " + result);

        return result;
    }

    public static Map<String, Integer> getExistCategories(Connection pCon,
            int pCatalogId, Set<String> pCategoryNames) throws Exception {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT trim(t1.short_desc) AS category_name,\n"
                + " t1.item_id AS category_id\n");
        sql.append("FROM clw_item t1\n");
        sql.append("INNER JOIN clw_catalog_structure t2 "
                + "ON t1.item_id = t2.item_id\n");
        sql.append("    AND t1.item_type_cd = '"
                + RefCodeNames.ITEM_TYPE_CD.CATEGORY + "'\n");
        sql.append("    AND t2.catalog_id = " + pCatalogId + "\n");
        if (pCategoryNames!=null && pCategoryNames.isEmpty() == false) {
            DBCriteria inCategories = new DBCriteria();
            inCategories.addOneOf("trim(t1.short_desc)", new ArrayList(
                    pCategoryNames));
            sql.append("    AND " + inCategories.getWhereClause() + "\n");
        }
        Statement statement = null;
        ResultSet resultSet = null;
        log.debug("getExistCategories SQL:\n" + sql);
        try {
            statement = pCon.createStatement();
            resultSet = statement.executeQuery(sql.toString());
            while (resultSet.next()) {
                String categoryName = resultSet.getString("category_name")
                        .trim();
                int categoryId = resultSet.getInt("category_id");
                result.put(categoryName, categoryId);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return result;
    }

    public static Map<Integer, Map<Integer, CatalogStructureData>> getCatalogStructuresByCatalog(Connection pCon,
                                                                                                 Collection<Integer> pCatalogIds,
                                                                                                 Collection<String> pStructureCds,
                                                                                                 List<String> pErrors) throws Exception {

        Map<Integer, Map<Integer, CatalogStructureData>> result = new TreeMap<Integer, Map<Integer, CatalogStructureData>>();

        DBCriteria cr = new DBCriteria();
        cr.addOneOf(CatalogStructureDataAccess.CATALOG_ID, new ArrayList<Integer>(pCatalogIds));
        cr.addOneOf(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, new ArrayList<String>(pStructureCds));

        CatalogStructureDataVector list = CatalogStructureDataAccess.select(pCon, cr);

        for (int i = 0; list != null && i < list.size(); i++) {
            CatalogStructureData item = (CatalogStructureData) list.get(i);

            int key = item.getCatalogId();
            Map<Integer, CatalogStructureData> val = result.get(key);
            if (val == null) {
                val = new TreeMap<Integer, CatalogStructureData>();
                result.put(key, val);
            }

            CatalogStructureData oldItem = val.put(item.getItemId(), item);
            if (oldItem != null) {
                pErrors.add("Was found duplicate catalog structure for catalog:"
                        + item.getCatalogId()
                        + " items:"
                        + oldItem.getItemId() + "," + item.getItemId());
            }
        }

        return result;
    }

    public static Map<Integer, Map<Integer, CatalogStructureData>> getCatalogStructuresByCatalog(Connection pCon,
                                                                                                 Collection<Integer> pCatalogIds,
                                                                                                 List<String> pErrors) throws Exception {

        return getCatalogStructuresByCatalog(pCon,
                pCatalogIds,
                Utility.getAsList(RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT),
                pErrors);
    }


    public static Map<Integer, Map<Integer, InventoryItemsData>> getInventoryItemsByAccountId(Connection pCon,
                                                                                              Collection<Integer> pAccounIds,
                                                                                              List<String> pErrors) throws Exception {

        Map<Integer, Map<Integer, InventoryItemsData>> result = new HashMap<Integer, Map<Integer, InventoryItemsData>>();

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(InventoryItemsDataAccess.BUS_ENTITY_ID, new ArrayList<Integer>(pAccounIds));

        InventoryItemsDataVector items = InventoryItemsDataAccess.select(pCon, dbCriteria);
        for (Object oItem : items) {
            InventoryItemsData invItem = (InventoryItemsData) oItem;
            int accountId = invItem.getBusEntityId();
            if (!result.containsKey(accountId)) {
                HashMap<Integer, InventoryItemsData> map = new HashMap<Integer, InventoryItemsData>();
                map.put(invItem.getItemId(), invItem);
                result.put(accountId, map);
            } else {
                Map<Integer, InventoryItemsData> map = result.get(accountId);
                map.put(invItem.getItemId(), invItem);
            }
        }
        return result;
    }

    public static Map<Integer, Map<Integer, ShoppingControlData>> getShoppingControlsByAccountId(Connection pCon,
                                                                                                 Collection<Integer> pAccounIds,
                                                                                                 List<String> pErrors) throws Exception {

        Map<Integer, Map<Integer, ShoppingControlData>> result = new HashMap<Integer, Map<Integer, ShoppingControlData>>();

        DBCriteria dbCriteria = new DBCriteria();
        dbCriteria.addOneOf(ShoppingControlDataAccess.ACCOUNT_ID, new ArrayList<Integer>(pAccounIds));
        dbCriteria.addIsNullOr0(ShoppingControlDataAccess.SITE_ID);

        ShoppingControlDataVector items = ShoppingControlDataAccess.select(pCon, dbCriteria);
        for (Object oItem : items) {
            ShoppingControlData item = (ShoppingControlData) oItem;
            int accountId = item.getAccountId();
            if (!result.containsKey(accountId)) {
                HashMap<Integer, ShoppingControlData> map = new HashMap<Integer, ShoppingControlData>();
                map.put(item.getItemId(), item);
                result.put(accountId, map);
            } else {
                Map<Integer, ShoppingControlData> map = result.get(item.getAccountId());
                map.put(item.getItemId(), item);
            }
        }
        return result;
    }

    public static void tryToRemoveCategoryFromAccountCatalogs(Connection pCon,
                                                              int pStoreCatalogId,
                                                              Set<Integer> pCatalogIds,
                                                              Set<Integer> pCategoryIds,
                                                              Set<Integer> pAllCatalogIds) throws Exception {

        Map<Integer, Set<Integer>> toDelete = new HashMap<Integer, Set<Integer>>();

        StringBuilder buffer = new StringBuilder();

        Set<Integer> emptyCategoryIds = getEmptyCategoryIds(pCon, pCatalogIds, pCategoryIds);

        if (!emptyCategoryIds.isEmpty()) {

            Set<Integer> categIdsToDelete = CatalogBean.getCategoryIdsForDelete(pCon,
                    pStoreCatalogId,
                    pCatalogIds,
                    emptyCategoryIds);

            if (!categIdsToDelete.isEmpty()) {

                HashSet<Integer> otherCatalogs = new HashSet<Integer>(pAllCatalogIds);
                otherCatalogs.removeAll(pCatalogIds);

                if (!otherCatalogs.isEmpty()) {

                    buffer.append("SELECT DEL.CATALOG_ID,DEL.ITEM_ID FROM \n" +
                            "(SELECT CATALOG_ID, ITEM_ID FROM CLW_CATALOG_STRUCTURE WHERE ITEM_ID IN (");
                    buffer.append(Utility.toCommaSting(categIdsToDelete));
                    buffer.append(")" + " AND CATALOG_ID IN (");
                    buffer.append(Utility.toCommaSting(pCatalogIds));
                    buffer.append(")) DEL\n" + " LEFT JOIN (SELECT CATALOG_ID, ITEM_ID\n" + "FROM CLW_CATALOG_STRUCTURE  WHERE  \n" + "ITEM_ID IN (");
                    buffer.append(Utility.toCommaSting(categIdsToDelete)).append(")");
                    buffer.append("  AND CATALOG_ID IN (");
                    buffer.append(Utility.toCommaSting(otherCatalogs));
                    buffer.append(") GROUP BY CATALOG_ID, ITEM_ID) OTHER\n" + "ON DEL.ITEM_ID=OTHER.ITEM_ID  AND OTHER.CATALOG_ID IS NULL");

                    String sql = buffer.toString();

                    log.info("tryToRemoveCategoryFromAccountCatalogs()=> SQL:\n" + sql);

                    Statement statement = null;
                    ResultSet resultSet = null;

                    try {

                        statement = pCon.createStatement();
                        resultSet = statement.executeQuery(sql);

                        while (resultSet.next()) {

                            int catalogId = resultSet.getInt("CATALOG_ID");
                            int categoryId = resultSet.getInt("ITEM_ID");

                            Set<Integer> categoryIds = toDelete.get(catalogId);
                            if (categoryIds == null) {
                                categoryIds = new HashSet<Integer>();
                                toDelete.put(catalogId, categoryIds);
                            }
                            categoryIds.add(categoryId);
                        }

                    } catch (Exception e) {
                        throw e;
                    } finally {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (statement != null) {
                            statement.close();

                        }
                    }
                } else {
                    for (Integer catalogId : pCatalogIds) {
                        toDelete.put(catalogId, new HashSet<Integer>(categIdsToDelete));
                    }
                }

                for (Map.Entry<Integer, Set<Integer>> entry : toDelete.entrySet()) {
                    DBCriteria dbCriteria = new DBCriteria();
                    dbCriteria.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, entry.getKey());
                    dbCriteria.addOneOf(CatalogStructureDataAccess.ITEM_ID, new ArrayList<Integer>(entry.getValue()));
                    log.info("tryToRemoveCategoryFromAccountCatalogs()=> removing " + entry.getValue() + " categories from catalog: " + entry.getKey());
                    CatalogStructureDataAccess.remove(pCon, dbCriteria);
                }
            }
        }

    }

    public static void tryToRemoveCategoryFromShoppingCatalogs(Connection pCon,
                                                               int pStoreCatalogId,
                                                               Set<Integer> pCatalogIds,
                                                               Set<Integer> pCategoryIds) throws Exception {

        Map<Integer, Set<Integer>> toDelete = new HashMap<Integer, Set<Integer>>();

        Set<Integer> emptyCategoryIds = getEmptyCategoryIds(pCon, pCatalogIds, pCategoryIds);

        if (!emptyCategoryIds.isEmpty()) {

            Set<Integer> categIdsToDelete = CatalogBean.getCategoryIdsForDelete(pCon,
                    pStoreCatalogId,
                    pCatalogIds,
                    emptyCategoryIds);

            if (!categIdsToDelete.isEmpty()) {

                for (Integer catalogId : pCatalogIds) {
                    toDelete.put(catalogId, new HashSet<Integer>(categIdsToDelete));
                }

                for (Map.Entry<Integer, Set<Integer>> entry : toDelete.entrySet()) {
                    DBCriteria dbCriteria = new DBCriteria();
                    dbCriteria.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, entry.getKey());
                    dbCriteria.addOneOf(CatalogStructureDataAccess.ITEM_ID, new ArrayList<Integer>(entry.getValue()));
                    log.info("tryToRemoveCategoryFromShoppingCatalogs()=> removing " + entry.getValue() + " categories from catalog: " + entry.getKey());
                    CatalogStructureDataAccess.remove(pCon, dbCriteria);
                }
            }

        }

    }

    public static Set<Integer> getEmptyCategoryIds(Connection pCon, Set<Integer> pCatalogIds, Set<Integer> pCategoryIds) throws Exception {

        StringBuilder buffer = new StringBuilder();

        buffer.append("SELECT DISTINCT item_id AS category FROM (\n");
        buffer.append("    SELECT cs.catalog_id, cs.item_id, ia.item1_id\n");
        buffer.append("    FROM clw_catalog_structure cs\n");
        buffer.append("    LEFT JOIN clw_item_assoc ia ON cs.item_id = ia.item2_id" +
                " AND cs.catalog_id = ia.catalog_id " +
                " AND ia.item_assoc_cd='"+RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+"'\n");
        buffer.append("    WHERE cs.CATALOG_STRUCTURE_CD = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "'\n");
        buffer.append("        AND cs.catalog_id IN  (").append(Utility.toCommaSting(pCatalogIds)).append(")\n");
        buffer.append("        AND cs.item_id IN (").append(Utility.toCommaSting(pCategoryIds)).append(")\n");
        buffer.append(") WHERE item1_id IS null\n");

        String sql = buffer.toString();

        log.info("getEmptyCategoryIds()=> SQL:\n" + sql);

        Set<Integer> result = new TreeSet<Integer>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {

            statement = pCon.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category");
                result.add(categoryId);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();

            }
        }
        log.info("getEmptyCategoryIds()=> emptyCategoryIds:" + result);

        return result;
    }

    public static Set<Integer> getShoppingCatalogIdsFor(Connection pCon, int pAccountCatalogId) throws Exception {

        DBCriteria accCrit = new DBCriteria();
        accCrit.addEqualTo(CatalogAssocDataAccess.CATALOG_ID, pAccountCatalogId);
        accCrit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

        IdVector busEntityIds = CatalogAssocDataAccess.selectIdOnly(pCon, CatalogAssocDataAccess.BUS_ENTITY_ID, accCrit);

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
        crit.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
        crit.addJoinTableOneOf(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, busEntityIds);
        crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);



        CatalogDataVector catalogs = CatalogDataAccess.select(pCon, crit);

        return new HashSet<Integer>(Utility.toIdVector(catalogs));

    }

    public static HashMap<String, Set<Integer>> getCatalogIdsByNameForStore(Connection pCon, int pStoreId) throws Exception {

        HashMap<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();

        DBCriteria crit = new DBCriteria();

        crit.addJoinCondition(CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
        crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
        crit.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

        CatalogDataVector catalogs = CatalogDataAccess.select(pCon, crit);
        for (Object oCatalog : catalogs) {
            CatalogData catalog = (CatalogData) oCatalog;
            Set<Integer> catalogIds = map.get(catalog.getShortDesc());
            if (catalogIds == null) {
                catalogIds = new HashSet<Integer>();
                map.put(catalog.getShortDesc(), catalogIds);
            }
            catalogIds.add(catalog.getCatalogId());
        }

        return map;

    }

    public static List<CatalogStructureData> getCatalogStructuresForCreate(Connection pCon,
                                                                           Integer pCatalogId,
                                                                           Set<Integer> pProcessCatalogIds) throws SQLException {
        String sql = "SELECT\n" +
                CatalogStructureDataAccess.CATALOG_ID + "," +
                CatalogStructureDataAccess.BUS_ENTITY_ID + "," +
                CatalogStructureDataAccess.CATALOG_STRUCTURE_CD + "," +
                CatalogStructureDataAccess.ITEM_ID + "," +
                CatalogStructureDataAccess.CUSTOMER_SKU_NUM + "," +
                CatalogStructureDataAccess.SHORT_DESC + "," +
                CatalogStructureDataAccess.EFF_DATE + "," +
                CatalogStructureDataAccess.EXP_DATE + "," +
                CatalogStructureDataAccess.STATUS_CD + "," +
                CatalogStructureDataAccess.COST_CENTER_ID + "," +
                CatalogStructureDataAccess.TAX_EXEMPT + "," +
                CatalogStructureDataAccess.ITEM_GROUP_ID + "," +
                CatalogStructureDataAccess.SPECIAL_PERMISSION + "," +
                CatalogStructureDataAccess.SORT_ORDER +
                " FROM ( " +
                " SELECT * FROM " + CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE + " WHERE " + CatalogStructureDataAccess.CATALOG_ID + " IN (" + Utility.toCommaSting(pProcessCatalogIds) + ")) A" +
                " LEFT JOIN (SELECT " + CatalogStructureDataAccess.ITEM_ID + "  AS ITEM2_ID FROM " + CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE +
                " WHERE " + CatalogStructureDataAccess.CATALOG_ID + " = " + pCatalogId + ") B \n" +
                " ON A.ITEM_ID = B.ITEM2_ID WHERE B.ITEM2_ID IS NULL";


        ResultSet rs = null;
        Statement stmt = null;

        log.info("getCatalogStructuresForCreate()=> SQL: " + sql);

        HashSet<String> duplControlSet = new HashSet<String>();
        List<CatalogStructureData> v = new ArrayList<CatalogStructureData>();

        try {

            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                CatalogStructureData x = CatalogStructureData.createValue();

                x.setCatalogId(pCatalogId);
                x.setBusEntityId(rs.getInt(CatalogStructureDataAccess.BUS_ENTITY_ID));
                x.setCatalogStructureCd(rs.getString(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD));
                x.setItemId(rs.getInt(CatalogStructureDataAccess.ITEM_ID));
                x.setCustomerSkuNum(rs.getString(CatalogStructureDataAccess.CUSTOMER_SKU_NUM));
                x.setShortDesc(rs.getString(CatalogStructureDataAccess.SHORT_DESC));
                x.setEffDate(rs.getDate(CatalogStructureDataAccess.EFF_DATE));
                x.setExpDate(rs.getDate(CatalogStructureDataAccess.EFF_DATE));
                x.setStatusCd(rs.getString(CatalogStructureDataAccess.STATUS_CD));
                x.setCostCenterId(rs.getInt(CatalogStructureDataAccess.COST_CENTER_ID));
                x.setTaxExempt(rs.getString(CatalogStructureDataAccess.TAX_EXEMPT));
                x.setItemGroupId(rs.getInt(CatalogStructureDataAccess.ITEM_GROUP_ID));
                x.setSpecialPermission(rs.getString(CatalogStructureDataAccess.SPECIAL_PERMISSION));
                x.setSortOrder(rs.getInt(CatalogStructureDataAccess.SORT_ORDER));

                if (!duplControlSet.contains(x.getItemId()+"_"+x.getCatalogStructureCd())) {
                    duplControlSet.add(x.getItemId()+"_"+x.getCatalogStructureCd());
                    v.add(x);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();

            }
        }

        return v;

    }

    public static List<ItemAssocData> getItemAssocForCreate(Connection pCon,
                                                            Integer pCatalogId,
                                                            Set<Integer> pProcessCatalogIds) throws SQLException {

        String sql = "SELECT " + ItemAssocDataAccess.ITEM1_ID + ", " + ItemAssocDataAccess.ITEM2_ID + ", " + ItemAssocDataAccess.ITEM_ASSOC_CD + " FROM (SELECT " + ItemAssocDataAccess.ITEM1_ID + "," + ItemAssocDataAccess.ITEM2_ID + "," + ItemAssocDataAccess.ITEM_ASSOC_CD +
                " FROM " + ItemAssocDataAccess.CLW_ITEM_ASSOC + " WHERE " + ItemAssocDataAccess.CATALOG_ID + " IN (" + Utility.toCommaSting(pProcessCatalogIds) + ")) A LEFT JOIN \n" +
                " (SELECT " + ItemAssocDataAccess.ITEM1_ID + " AS ACC_ITEM1_ID, " + ItemAssocDataAccess.ITEM2_ID + " AS ACC_ITEM2_ID, " + ItemAssocDataAccess.ITEM_ASSOC_CD + " AS ACC_ITEM_ASSOC_CD" +
                " FROM " + ItemAssocDataAccess.CLW_ITEM_ASSOC + " WHERE " + ItemAssocDataAccess.CATALOG_ID + "=" + pCatalogId + ") B" +
                " ON A.ITEM1_ID = B.ACC_ITEM1_ID \n" +
                " AND A.ITEM2_ID = B.ACC_ITEM2_ID\n" +
                " AND A.ITEM_ASSOC_CD = B.ACC_ITEM_ASSOC_CD WHERE ACC_ITEM1_ID IS NULL";

        ResultSet rs = null;
        Statement stmt = null;

        log.info("getItemAssocForCreate()=> SQL: " + sql);

        HashSet<String> duplControlSet = new HashSet<String>();
        List<ItemAssocData> v = new ArrayList<ItemAssocData>();

        try {

            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                ItemAssocData x = ItemAssocData.createValue();

                x.setCatalogId(pCatalogId);
                x.setItem1Id(rs.getInt(ItemAssocDataAccess.ITEM1_ID));
                x.setItem2Id(rs.getInt(ItemAssocDataAccess.ITEM2_ID));
                x.setItemAssocCd(rs.getString(ItemAssocDataAccess.ITEM_ASSOC_CD));

                if (!duplControlSet.contains(x.getItem1Id() + "_" + x.getItem2Id() + "_" + x.getItemAssocCd())) {
                    duplControlSet.add(x.getItem1Id() + "_" + x.getItem2Id() + "_" + x.getItemAssocCd());
                    v.add(x);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();

            }
        }

        return v;

    }

    public static List<InboundXpedxCatalogItemLoader.ItemAssocObj> getAssocItemIdsForDelete(Connection pCon, Integer pCatalogId, Set<Integer> pExistsChildsCatalogIds) throws Exception {

        String sql = "SELECT ACC_ITEM1_ID,ACC_ITEM2_ID FROM " +
                "(SELECT " + ItemAssocDataAccess.ITEM1_ID + " AS ACC_ITEM1_ID," + ItemAssocDataAccess.ITEM2_ID + " AS ACC_ITEM2_ID, " + ItemAssocDataAccess.ITEM_ASSOC_CD + " AS ACC_ITEM_ASSOC_CD" +
                " FROM " + ItemAssocDataAccess.CLW_ITEM_ASSOC +
                "  WHERE " + ItemAssocDataAccess.CATALOG_ID + " = " + pCatalogId + ") A " +
                " LEFT JOIN (SELECT " + ItemAssocDataAccess.ITEM1_ID + "," + ItemAssocDataAccess.ITEM2_ID + "," + ItemAssocDataAccess.ITEM_ASSOC_CD +
                " FROM " + ItemAssocDataAccess.CLW_ITEM_ASSOC +
                " WHERE " + ItemAssocDataAccess.CATALOG_ID + " IN (" + Utility.toCommaSting(pExistsChildsCatalogIds) + ")) B\n" +
                " ON A.ACC_ITEM1_ID = B.ITEM1_ID \n" +
                " AND A.ACC_ITEM2_ID = B.ITEM2_ID\n" +
                " AND A.ACC_ITEM_ASSOC_CD=B.ITEM_ASSOC_CD  WHERE  ITEM1_ID IS NULL";


        ResultSet rs = null;
        Statement stmt = null;

        log.info("getAccountAssocItemIdsForDelete()=> SQL: " + sql);

        HashSet<String> duplControlSet = new HashSet<String>();
        List<InboundXpedxCatalogItemLoader.ItemAssocObj> itemAssocObjList = new ArrayList<InboundXpedxCatalogItemLoader.ItemAssocObj>();

        try {

            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                InboundXpedxCatalogItemLoader.ItemAssocObj obj = new InboundXpedxCatalogItemLoader.ItemAssocObj();

                obj.setItem1Id(rs.getInt(1));
                obj.setItem2Id(rs.getInt(2));
                obj.setCatalogId(pCatalogId);
                if (!duplControlSet.contains(obj.getItem1Id() + "_" + obj.getItem2Id())) {
                    duplControlSet.add(obj.getItem1Id() + "_" + obj.getItem2Id());
                    itemAssocObjList.add(obj);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();

            }
        }

        return itemAssocObjList;
    }


    public static HashSet<Integer> getAccountCatalogItemIdsForDelete(Connection pCon, Integer pAccountCatalogId, Set<Integer> pExistsAccountShoppingCatalogIds) throws Exception {

        String sql = "SELECT A.ITEM1_ID FROM " +
                "(SELECT DISTINCT ITEM_ID AS ITEM1_ID FROM " + CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE +
                " WHERE " + CatalogStructureDataAccess.CATALOG_ID + " = " + pAccountCatalogId + ") A " +
                "LEFT JOIN (SELECT DISTINCT ITEM_ID AS ITEM2_ID FROM " + CatalogStructureDataAccess.CLW_CATALOG_STRUCTURE + " " +
                " WHERE " + CatalogStructureDataAccess.CATALOG_ID + " IN (" + Utility.toCommaSting(pExistsAccountShoppingCatalogIds) + ")) B\n" +
                " ON A.ITEM1_ID = B.ITEM2_ID WHERE B.ITEM2_ID IS NULL";


        ResultSet rs = null;
        Statement stmt = null;

        log.info("getCatalogStructuresForCreate()=> SQL: " + sql);

        HashSet<Integer> itemIds = new HashSet<Integer>();

        try {

            stmt = pCon.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                itemIds.add(rs.getInt(1));
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();

            }
        }

        return itemIds;
    }

    public static HashMap<Integer, Set<Integer>> getShoppingCatalogIdsByItem(Connection pCon, Set<Integer> pExistsAccountShoppingCatalogIds) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, new ArrayList<Integer>(pExistsAccountShoppingCatalogIds));
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        dbc.addGreaterThan(CatalogStructureDataAccess.ITEM_ID, 0);

        HashMap<Integer, Set<Integer>> shoppingCatalogIdsByItem = new HashMap<Integer, Set<Integer>>();

        CatalogStructureDataVector csList = CatalogStructureDataAccess.select(pCon, dbc);
        for (Object oCatalogStructure : csList) {
            CatalogStructureData catalogStructure = (CatalogStructureData) oCatalogStructure;
            Set<Integer> catalogIdSet = shoppingCatalogIdsByItem.get(catalogStructure.getItemId());
            if (catalogIdSet == null) {
                catalogIdSet = new HashSet<Integer>();
                shoppingCatalogIdsByItem.put(catalogStructure.getItemId(), catalogIdSet);
            }
            catalogIdSet.add(catalogStructure.getCatalogId());
        }

        return shoppingCatalogIdsByItem;
    }

    public static List<URLResponseDataView> readImage(Set<String> pUrls) {
        ImageGrabber imgGrabber = new ImageGrabber(pUrls);
        imgGrabber.run();
        return imgGrabber.getDataList();
    }


    public static Map<Integer, ContentData> getItemContent(Connection pCon,
                                                           Map<ComparableList, ItemData> pItemDataByDistItemRef,
                                                           List<XpedxCatalogItemView> pParsedDataList) throws Exception {

        HashMap<Integer, ContentData> result = new HashMap<Integer, ContentData>();

        DBCriteria dbc;
        for (XpedxCatalogItemView parsedData : pParsedDataList) {

            if (Utility.isSet(parsedData.getImage())) {

                ItemData itemData = pItemDataByDistItemRef.get(ComparableList.createValue(
                        parsedData.getDistributor(),
                        parsedData.getDistSKU()));

                if (itemData != null) {

                    dbc = new DBCriteria();

                    dbc.addEqualTo(ContentDataAccess.SHORT_DESC, ITEM_IMAGE);
                    dbc.addEqualTo(ContentDataAccess.CONTENT_TYPE_CD, IMAGE);
                    dbc.addEqualTo(ContentDataAccess.CONTENT_USAGE_CD, ITEM_IMAGE);
                    dbc.addEqualTo(ContentDataAccess.SOURCE_CD, XSUITE_APP);
                    dbc.addEqualTo(ContentDataAccess.PATH, createImagePathForContent(itemData.getItemId(), parsedData.getImage()));

                    ContentDataVector images = ContentDataAccess.select(pCon, dbc);
                    if (!images.isEmpty()) {
                        result.put(itemData.getItemId(), (ContentData) images.get(0));
                    }
                }
            }

        }

        return result;
    }


    /**
     * Comparable list, for working in tree-collections.
     */
    public static class ComparableList extends ArrayList<Comparable> implements
            Comparable {
        public static ComparableList createValue(Comparable... pKeys) {
            ComparableList comparableList = new ComparableList();
            for (int i = 0; pKeys != null && i < pKeys.length; i++) {
                comparableList.add(pKeys[i]);
            }
            return comparableList;
        }

        private ComparableList() {
        }

        @Override
        public boolean equals(Object obj) {
            if (super.equals(obj) == true) {
                return true;
            }
            if (obj instanceof ComparableList) {
                ComparableList other = (ComparableList) obj;
                if (other.size() != this.size()) {
                    return false;
                }
                for (int i = 0; i < this.size(); i++) {
                    Object item = this.get(i);
                    if (item == null || item.equals(other.get(i)) == false) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int h = 0;
            for (Object i : this) {
                h = 31 * h + i.hashCode();
            }
            return h;
        }

        public int compareTo(Object o) {
            if (o instanceof ComparableList) {
                ComparableList other = (ComparableList) o;
                int count = Math.min(other.size(), this.size());
                for (int i = 0; i < count; i++) {
                    Comparable thisItem = this.get(i);
                    Comparable otherItem = other.get(i);
                    if (thisItem == null && otherItem == null) {
                    } else if (thisItem == null) {
                        return -1;
                    } else if (otherItem == null) {
                        return 1;
                    } else {
                        int result = thisItem.compareTo(otherItem);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
                return new Integer(this.size()).compareTo(other.size());
            }
            return -1;
        }
    }

    public static ItemMetaData getItemMeta(ItemMetaDataVector pItemMeta, String pName) {
        for(Object oItemMeta:pItemMeta){
            ItemMetaData meta = (ItemMetaData) oItemMeta;
            if(pName.equals(meta.getNameValue())){
              return meta;
        }
        }
        return null;
    }

    public static ItemMetaData createItemMeta(int pItemId, String pName, String pValue, String pForUser) {

        ItemMetaData itemMeta = ItemMetaData.createValue();

        itemMeta.setItemId(pItemId);
        itemMeta.setNameValue(pName);
        itemMeta.setValue(pValue);
        itemMeta.setAddBy(pForUser);
        itemMeta.setModBy(pForUser);

        return itemMeta;
    }

    public static ItemMetaDataVector createItemMetas(ItemData pItem, XpedxCatalogItemView pInboundItem, String pForUser) {

        ItemMetaDataVector itemMetas = new ItemMetaDataVector();

        ItemMetaData itemMeta;
        if (Utility.isSet(pInboundItem.getHazmat())) {
            String value = YES.equalsIgnoreCase(pInboundItem.getHazmat()) ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.HAZMAT,
                    value,
                    pForUser);
            itemMetas.add(itemMeta);
        } else {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.HAZMAT,
                    Boolean.FALSE.toString(),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getImage())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.IMAGE,
                    createImagePathForMapping(pItem.getItemId(), pInboundItem.getImage()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getColor())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.COLOR,
                    String.valueOf(pInboundItem.getColor()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getPack())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.PACK,
                    String.valueOf(pInboundItem.getPack()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getShippingCubicSize())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.CUBE_SIZE,
                    String.valueOf(pInboundItem.getShippingCubicSize()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getShippingWeight())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.SHIP_WEIGHT,
                    String.valueOf(pInboundItem.getShippingWeight()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getWeightUnit())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.WEIGHT_UNIT,
                    String.valueOf(pInboundItem.getWeightUnit()),
                    pForUser);
            itemMetas.add(itemMeta);
        } else {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.WEIGHT_UNIT,
                    RefCodeNames.WEIGHT_UNIT.OUNCE,
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getUOM())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.UOM,
                    String.valueOf(pInboundItem.getUOM()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getListPrice())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.LIST_PRICE,
                    String.valueOf(pInboundItem.getListPrice()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getProductUPC())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.UPC_NUM,
                    String.valueOf(pInboundItem.getProductUPC()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getPackUPC())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.PKG_UPC_NUM,
                    String.valueOf(pInboundItem.getPackUPC()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        if (Utility.isSet(pInboundItem.getSize())) {
            itemMeta = createItemMeta(pItem.getItemId(),
                    ProductData.SIZE,
                    String.valueOf(pInboundItem.getSize()),
                    pForUser);
            itemMetas.add(itemMeta);
        }

        return itemMetas;
    }

    public static String createImagePathForMapping(int pItemId, String pImageUtl) {
        return new StringBuilder()
                .append(IMAGE_BASE_PATH)
                .append(pItemId)
                .append(IOUtilities.getFileExt(pImageUtl).toLowerCase()).toString();
    }

    private static String createImagePathForContent(int pItemId, String pImageUtl) {
        return new StringBuilder()
                .append(".")
                .append(IMAGE_BASE_PATH)
                .append(pItemId)
                .append(IOUtilities.getFileExt(pImageUtl).toLowerCase()).toString();
    }

    public static ItemMappingData createItemMapping(int itemId,
                                                    int pBusEntityId,
                                                    String pItemSMappingCd,
                                                    String pItemNum,
                                                    String pPack,
                                                    String pUom,
                                                    String pForUser) {

        ItemMappingData itemMapping = ItemMappingData.createValue();
        itemMapping.setItemId(itemId);
        itemMapping.setBusEntityId(pBusEntityId);
        itemMapping.setItemNum(pItemNum);
        itemMapping.setItemPack(pPack);
        itemMapping.setItemUom(pUom);
        itemMapping.setItemMappingCd(pItemSMappingCd);
        itemMapping.setAddBy(pForUser);
        itemMapping.setModBy(pForUser);
        itemMapping.setStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);

        return itemMapping;
    }

    public static ContentData createImageContent(int pItemId, String pPath, byte[] pImageData, String pForUser) {

        ContentData data = ContentData.createValue();

        data.setShortDesc(ITEM_IMAGE);
        data.setContentTypeCd(IMAGE);
        data.setContentStatusCd(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
        data.setLocaleCd(X);
        data.setLanguageCd(X);
        data.setContentUsageCd(ITEM_IMAGE);
        data.setSourceCd(XSUITE_APP);
        data.setAddBy(pForUser);
        data.setModBy(pForUser);
        data.setPath(createImagePathForContent(pItemId, pPath));
        data.setBinaryData(pImageData);

        return data;
    }

    public static ItemData createItemData(XpedxCatalogItemView pInboundItem, String pForUser, Date pEffDate) {

        ItemData item = ItemData.createValue();

        item.setItemStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
        item.setItemTypeCd(RefCodeNames.ITEM_TYPE_CD.PRODUCT);
        item.setLongDesc(pInboundItem.getLongDescription());
        item.setShortDesc(pInboundItem.getShortDescription());
        item.setEffDate(pEffDate);
        item.setAddBy(pForUser);
        item.setModBy(pForUser);

        return item;
    }
    private static boolean isShoppingCatalogNotEmpty(Connection pCon, int pShoppingCatalogId) throws Exception {
      boolean b= false;
      String sql = " select 'T' from dual where exists (select ITEM_ID from CLW_CATALOG_STRUCTURE where CATALOG_ID =" + pShoppingCatalogId + ")";
      PreparedStatement pstmt =pCon.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();
      while(rs.next()){
        b= true;
      }
      pstmt.close();
      return b;
    }


}
