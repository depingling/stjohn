package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.dao.InventoryLevelDAO;
import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.InventoryLevelViewWrapper;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.*;


public class InboundJCPenneyParLoader extends InboundFlatFile {

    private Logger log = Logger.getLogger(InboundJCPenneyParLoader.class);

    private static final String LOADER_FMT = "Line {0} : {1}";
    private static final int JCP_STORE = 1;

    private final List<InboundJCPParLoaderView> mParsedDataList = new ArrayList<InboundJCPParLoaderView>();
    private boolean mHeaderWasLoad = false;
    private Reporter mReporter;

    private static final String SKU_USAGE = "SkuUsage";
    private static final String ADD_BY = "loader";
    private static final String SKU_USAGE_DELIM = ",";
    private static final int ACTIVITY_PERIOD = 30;//days


    public InboundJCPenneyParLoader() {
        super.setSepertorChar(',');
    }

    protected void init() {

        log.info("init()=> BEGIN");

        super.init();
        mParsedDataList.clear();
        mHeaderWasLoad = true;

        mReporter = new Reporter();


        log.info("init()=> mHeaderWasLoad: " + mHeaderWasLoad);

        log.info("init()=> END.");
    }

    /**
     * Called when the object has successfully been parsed
     */
    protected void processParsedObject(Object pParsedObject) throws Exception {
        if (pParsedObject instanceof InboundJCPParLoaderView) {
            if (mHeaderWasLoad) {
                mParsedDataList.add((InboundJCPParLoaderView) pParsedObject);
            } else {
                mHeaderWasLoad = true;
            }
        } else {
            throw new IllegalArgumentException("No parsed object present!");
        }
    }


    @Override
    protected void doPostProcessing() throws Exception {

        log.info("doPostProcessing()=> BEGIN");

        List<String> errors = checkInputValues();
        if (!errors.isEmpty()) {
            throw processErrors(errors);
        }

        log.info("doPostProcessing()=> errors not found, go next");

        process();

        mReporter.print();

        log.info("doPostProcessing()=> END.");

    }

    protected void process() throws Exception {

        log.info("process()=> BEGIN");

        Connection con = null;

        try {

            con = getConnection();

            InboundData inboundData = getInboundData();

            Set<String> inboundSiteNames = new HashSet<String>();
            Set<Integer> inboundSkus = new HashSet<Integer>();
            for (Map.Entry<String/*SiteName*/, List<InboundJCPItemPar>> e : inboundData.getInboundSitePars().entrySet()) {
                inboundSiteNames.add(e.getKey());
                for (InboundJCPItemPar itemPar : e.getValue()) {
                    inboundSkus.add(itemPar.getItemSku());
                }
            }

            log.info("process()=> Inbound Sites(Names): " + inboundSiteNames.size());
            log.info("process()=> Inbound Item(SKUs): " + inboundSkus.size());

            DBData dbData = readDBData(con, inboundSiteNames, inboundSkus);

            List<String> errors;
            errors = verification(inboundData, dbData);
            if (!errors.isEmpty()) {
                throw processErrors(errors);
            }

            log.info("process()=> errors not found.");

            updateProcess(con, dbData, inboundData);

        } finally {
            closeConnection(con);
        }

    }

    private List<String> verification(InboundData pInboundData, DBData pDbData) {

        log.info("verification()=> BEGIN");

        ArrayList<String> errors = new ArrayList<String>();

        for (Map.Entry<String/*SiteName*/, List<DBJCPSite>> e : pDbData.getJcpSiteDbMap().entrySet()) {
            if (e.getValue().size() > 1) {
                errors.add("Duplicated site with name '" + e.getKey()+"' was found");
            }
        }

        for (Map.Entry<Integer/*Sku*/, List<ItemData>> e : pDbData.getJcpItemDbMap().entrySet()) {
            if (e.getValue().size() > 1) {
                errors.add("Duplicated item with SKU '" + e.getKey()+"' was found");
            }
        }

        for (Map.Entry<Integer/*SiteId*/, HashMap<Integer/*ItemId*/, List<InventoryLevelView>>> e : pDbData.getJcpInventoryItemDbMap().entrySet()) {
            for (Map.Entry<Integer/*ItemId*/, List<InventoryLevelView>> e1 : e.getValue().entrySet()) {
                if (e1.getValue().size() > 1)
                    errors.add("Duplicated inventory item was found. SiteID: " + e.getKey() + ", ItemID: " + e1.getKey());
            }
        }

        for (Map.Entry<Integer/*AccountID*/, List<DBJCPSkuUsage>> e : pDbData.getJcpSkuUsagesDbMap().entrySet()) {
            if (e.getValue().size() > 1) {
                errors.add("Duplicated SkuUsage was found. AccountID: " + e.getKey());
            }
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        HashSet<Integer> validSiteAccountIds = new HashSet<Integer>();

        for (Map.Entry<String/*SiteName*/, List<InboundJCPItemPar>> e : pInboundData.getInboundSitePars().entrySet()) {

            List<DBJCPSite> sites = pDbData.getJcpSiteDbMap().get(e.getKey());

            if (sites != null && !sites.isEmpty()) {

                DBJCPSite site = sites.get(0);
                for (InboundJCPItemPar sitePar : e.getValue()) {
                    Integer itemSku = sitePar.getItemSku();
                    List<ItemData> items = pDbData.getJcpItemDbMap().get(itemSku);
                    if (items == null || items.isEmpty()) {
                        errors.add("Not found item with SKU '" + itemSku + "', SiteName '" + e.getKey() + "'");
                    }
                }

                validSiteAccountIds.add(site.getAccountId());

            } else {
                errors.add("Not found site with name '" + e.getKey() + "'");
            }


        }

        for (Integer accountId : validSiteAccountIds) {
            List<DBJCPSkuUsage> skuUssages = pDbData.getJcpSkuUsagesDbMap().get(accountId);
            if (skuUssages == null || skuUssages.isEmpty()) {
                errors.add("Not found SkuUsage, AccountID: " + accountId);
            } else {
                int period = 1;
                try {
                    String[] list = Utility.parseStringToArray(skuUssages.get(0).getSkuUsages(), SKU_USAGE_DELIM);
                    for (String o : list) {
                        new Double(o.trim());
                        period++;
                    }
                } catch (Exception e) {
                    errors.add("Incorect SkuUsage: " + skuUssages.get(0).getSkuUsages() + ", period: " + period);
                }
            }
        }

        log.info("verification()=> END.");

        return errors;
    }

    private InboundData getInboundData() {

        InboundData inboundData = new InboundData();

        for (InboundJCPParLoaderView parsedDataItem : mParsedDataList) {

            String siteName = parsedDataItem.getSiteName();
            String itemSku = parsedDataItem.getItemSku();
            Integer qty = new Integer(parsedDataItem.getQuantity());

            List<InboundJCPItemPar> sitePars = inboundData.getInboundSitePars().get(siteName);
            if (sitePars == null) {
                sitePars = new ArrayList<InboundJCPItemPar>();
                inboundData.getInboundSitePars().put(siteName, sitePars);
            }

            sitePars.add(new InboundJCPItemPar(new Integer(itemSku), qty));

        }

        return inboundData;

    }

    private void updateProcess(Connection pCon, DBData pDbData, InboundData pInboundData) throws Exception {

        log.info("updateProcess()=> BEGIN");

        log.info("updateProcess()=> Updating DBData...");

        for (Map.Entry<String, List<InboundJCPItemPar>> siteEntry : pInboundData.getInboundSitePars().entrySet()) {

            String siteName = siteEntry.getKey();

            List<DBJCPSite> dbJCPSiteList = pDbData.getJcpSiteDbMap().get(siteName);
            DBJCPSite dbJCPSite = dbJCPSiteList.get(0);

            List<DBJCPSkuUsage> itemsSkuUsageList = pDbData.getJcpSkuUsagesDbMap().get(dbJCPSite.getAccountId());
            DBJCPSkuUsage itemsSkuUsage = itemsSkuUsageList.get(0);

            HashMap<Integer, List<InventoryLevelView>> invSiteMap = pDbData.getJcpInventoryItemDbMap().get(dbJCPSite.getSiteId());

            List<InboundJCPItemPar> itemsParValueList = siteEntry.getValue();
            for (InboundJCPItemPar itemParValue : itemsParValueList) {

                SkuUsagesMap skuUsagesMap = new SkuUsagesMap(itemsSkuUsage.getSkuUsages(), itemParValue.getQuantity());

                List<ItemData> itemsList = pDbData.getJcpItemDbMap().get(itemParValue.getItemSku());
                ItemData item = itemsList.get(0);

                List<InventoryLevelView> invLevelList = invSiteMap.get(item.getItemId());
                InventoryLevelView invLevel = invLevelList != null ? invLevelList.get(0) : null;

                update(pCon, dbJCPSite, item, invLevel, skuUsagesMap);
            }

            mReporter.incrementSiteProcessed();

        }

        log.info("updateProcess()=> END.");

    }

    private void update(Connection pCon,
                        DBJCPSite pDbJCPSite,
                        ItemData pItem,
                        InventoryLevelView pInvLevel,
                        SkuUsagesMap pItemSkuUsage) throws Exception {

        Date date = new Date();

        if (pInvLevel == null) {
            pInvLevel = new InventoryLevelView(new InventoryLevelData(), new InventoryLevelDetailDataVector());
            pInvLevel.getInventoryLevelData().setBusEntityId(pDbJCPSite.getSiteId());
            pInvLevel.getInventoryLevelData().setItemId(pItem.getItemId());
        }

        InventoryLevelViewWrapper ilWrapper = new InventoryLevelViewWrapper(pInvLevel);

        if (lastModNotUserActivity(ilWrapper.getInventoryLevelData(), date, ACTIVITY_PERIOD)) {

            ilWrapper.setParsModBy(ADD_BY);
            ilWrapper.setParsModDate(date);
            ilWrapper.setModBy(ADD_BY);
            ilWrapper.setModDate(date);

            boolean parValueWasChanged = false;
            for (Map.Entry<Integer/*Period*/, Double /*Value*/> e : pItemSkuUsage.entrySet()) {

                Integer period = e.getKey();

                Integer inboundParValue = Utility.intNN(e.getValue().intValue());
                Integer dbParValue = ilWrapper.getParValue(period);

                if (dbParValue == null) {
                    ilWrapper.setParValue(period, inboundParValue);
                    parValueWasChanged = true;
                } else if (dbParValue.intValue() != inboundParValue.intValue()) {
                    ilWrapper.setParValue(period, inboundParValue);
                    parValueWasChanged = true;
                }

            }

            InventoryLevelDAO.updateInventoryLevelView(pCon, ilWrapper.getInventoryLevelView(), !parValueWasChanged, ADD_BY);

            if (parValueWasChanged) {
                mReporter.incrementInventoryItemsChanged();
            }
        }

        mReporter.incrementInventoryItemsProcessed();

    }

    private boolean lastModNotUserActivity(InventoryLevelData pInventoryLevelData, Date pDate, int pActivityPeriod) {

        GregorianCalendar activityThr = new GregorianCalendar();
        activityThr.setTime(pDate);
        activityThr.add(Calendar.DAY_OF_YEAR, -1 * pActivityPeriod);

        return (pInventoryLevelData.getParsModDate() != null && activityThr.getTime().compareTo(pInventoryLevelData.getParsModDate()) > 0)
                || pInventoryLevelData.getParsModBy() == null
                || pInventoryLevelData.getParsModBy().startsWith(ADD_BY);
    }


    private DBData readDBData(Connection pCon, Set<String> pSiteNames, Set<Integer> pSkus) throws Exception {

        log.info("readDBData()=> BEGIN");

        DBData dbData = new DBData();

        DBJCPSiteMap jcpSiteDbMap = new DBJCPSiteMap();
        DBJCPSkuUsageMap jcpSkuUsagesDbMap = new DBJCPSkuUsageMap();
        DBJCPInventoryItemMap jcpInventoryItemDbMap = new DBJCPInventoryItemMap();
        DBJCPItemMap jcpItemDbMap = new DBJCPItemMap();

        Set<String> reqSites = new HashSet<String>();
        for (String siteName : pSiteNames) {
            reqSites.add(DBAccess.toQuoted(siteName));
        }

        List<List> siteNamesPkgs = Utility.createPackages(new ArrayList<String>(reqSites), 50);

        Set<Integer> accountIdSet = new HashSet<Integer>();
        Set<Integer> siteIdSet = new HashSet<Integer>();

        int i = 0;

        for (List pkg : siteNamesPkgs) {

            i++;

            String sql = "SELECT SITE.BUS_ENTITY_ID AS SITE_ID, " +
                    "SITE.SHORT_DESC AS SITE_NAME, " +
                    "ACCOUNT_ASSOC.BUS_ENTITY2_ID AS ACCOUNT_ID," +
                    "STORE_ASSOC.BUS_ENTITY2_ID AS STORE_ID FROM CLW_BUS_ENTITY SITE," +
                    "CLW_BUS_ENTITY_ASSOC ACCOUNT_ASSOC," +
                    "CLW_BUS_ENTITY_ASSOC STORE_ASSOC WHERE " +
                    " SITE.BUS_ENTITY_ID = ACCOUNT_ASSOC.BUS_ENTITY1_ID " +
                    " AND ACCOUNT_ASSOC.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "' " +
                    " AND ACCOUNT_ASSOC.BUS_ENTITY2_ID = STORE_ASSOC.BUS_ENTITY1_ID " +
                    " AND STORE_ASSOC.BUS_ENTITY_ASSOC_CD = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE + "' " +
                    " AND STORE_ASSOC.BUS_ENTITY2_ID = " + JCP_STORE +
                    " AND SITE.BUS_ENTITY_TYPE_CD = '" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "' " +
                    " AND SITE.SHORT_DESC IN (" + Utility.getAsString(pkg) + ")";

            Statement stmt = null;
            ResultSet rs = null;

            log.debug("readDBData()=> read site info. SQL: " + sql);
            log.debug("readDBData()=> site, package[" + i + "] pkg.size: " + pkg.size());

            try {

                stmt = pCon.createStatement();
                rs = stmt.executeQuery(sql);

                while (rs.next()) {

                    DBJCPSite site = new DBJCPSite();

                    site.setSiteId(rs.getInt(1));
                    site.setSiteName(rs.getString(2));
                    site.setAccountId(rs.getInt(3));
                    site.setStoreId(rs.getInt(4));

                    List<DBJCPSite> siteList = jcpSiteDbMap.get(site.getSiteName());
                    if (siteList == null) {
                        siteList = new ArrayList<DBJCPSite>();
                        jcpSiteDbMap.put(site.getSiteName(), siteList);
                    }

                    siteList.add(site);
                    accountIdSet.add(site.getAccountId());
                    siteIdSet.add(site.getSiteId());

                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }

        log.info("readDBData()=> found accounts, size: " + accountIdSet.size());
        log.info("readDBData()=> found sites, size: " + siteIdSet.size());

        List<List> accountIdsPkgs = Utility.createPackages(new ArrayList<Integer>(accountIdSet), 50);

        i = 0;

        for (List pkg : accountIdsPkgs) {

            String sql = "SELECT BUS_ENTITY_ID, CLW_VALUE" +
                    " FROM CLW_PROPERTY WHERE BUS_ENTITY_ID IN (" + Utility.toCommaSting(pkg) + ")" +
                    " AND PROPERTY_TYPE_CD = '" + RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD + "'" +
                    " AND SHORT_DESC = '" + SKU_USAGE + "'" +
                    " AND CLW_VALUE IS NOT NULL";

            Statement stmt = null;
            ResultSet rs = null;

            log.debug("readDBData()=> read SkuUsages for accounts. SQL: " + sql);
            log.debug("readDBData()=> SkuUsages, package[" + i + "] pkg.size: " + pkg.size());

            try {

                stmt = pCon.createStatement();
                rs = stmt.executeQuery(sql);

                while (rs.next()) {

                    DBJCPSkuUsage skuUsage = new DBJCPSkuUsage();
                    skuUsage.setAccountId(rs.getInt(1));
                    skuUsage.setSkuUsages(rs.getString(2));

                    List<DBJCPSkuUsage> skuUsageList = jcpSkuUsagesDbMap.get(skuUsage.getAccountId());
                    if (skuUsageList == null) {
                        skuUsageList = new ArrayList<DBJCPSkuUsage>();
                        jcpSkuUsagesDbMap.put(skuUsage.getAccountId(), skuUsageList);
                    }

                    skuUsageList.add(skuUsage);

                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }

        }

        log.info("readDBData()=> found " + jcpSkuUsagesDbMap.size() + " SkuUsages for " + accountIdSet.size() + " accounts");

        InventoryLevelSearchCriteria searchCriteria = new InventoryLevelSearchCriteria();
        searchCriteria.setSiteIds(new ArrayList<Integer>(siteIdSet));

        InventoryLevelViewVector invLevelViewList = InventoryLevelDAO.getInvLevelViewCollections(pCon, searchCriteria);

        for (Object oInvLeveView : invLevelViewList) {

            InventoryLevelView invLeveView = (InventoryLevelView) oInvLeveView;

            int siteId = invLeveView.getInventoryLevelData().getBusEntityId();
            int itemId = invLeveView.getInventoryLevelData().getItemId();

            HashMap<Integer, List<InventoryLevelView>> siteMap = jcpInventoryItemDbMap.get(siteId);
            if (siteMap == null) {
                siteMap = new HashMap<Integer, List<InventoryLevelView>>();
                jcpInventoryItemDbMap.put(siteId, siteMap);
            }

            List<InventoryLevelView> invLevelList = siteMap.get(itemId);
            if (invLevelList == null) {
                invLevelList = new ArrayList<InventoryLevelView>();
                siteMap.put(itemId, invLevelList);
            }

            invLevelList.add(invLeveView);

        }

        log.info("readDBData()=> found inventory items data for " + jcpInventoryItemDbMap.size() + " sites");

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(ItemDataAccess.SKU_NUM, Utility.getAsString(new ArrayList<Integer>(pSkus)));

        ItemDataVector itemList = ItemDataAccess.select(pCon, dbc);
        for (Object oItem : itemList) {

            ItemData item = (ItemData) oItem;

            List<ItemData> skuItems = jcpItemDbMap.get(item.getSkuNum());
            if (skuItems == null) {
                skuItems = new ArrayList<ItemData>();
                jcpItemDbMap.put(item.getSkuNum(), skuItems);
            }

            skuItems.add(item);

        }

        log.info("readDBData()=> found " + jcpItemDbMap.size() + " items for " + jcpItemDbMap.size() + " SKUs");

        dbData.setJcpSiteDbMap(jcpSiteDbMap);
        dbData.setJcpSkuUsagesDbMap(jcpSkuUsagesDbMap);
        dbData.setJcpInventoryItemDbMap(jcpInventoryItemDbMap);
        dbData.setJcpItemDbMap(jcpItemDbMap);

        log.info("readDBData()=> END.");

        return dbData;

    }

    private Exception processErrors(List<String> errors) {

        for (String error : errors) {
            appendErrorMsgs(error);
        }

        log.info("processErrors()=>  found " + errors.size() + " errors.");

        return new Exception(super.getFormatedErrorMsgs());
    }

    private List<String> checkInputValues() {

        log.info("checkInputValues()=> BEGIN");

        log.info("checkInputValues()=> parsed object size: " + mParsedDataList.size());

        List<String> errors = new ArrayList<String>();

        for (int i = 0; i < mParsedDataList.size(); i++) {

            InboundJCPParLoaderView parsedDataItem = mParsedDataList.get(i);

            String lineNum = String.valueOf(i + 1);

            if (!Utility.isSet(parsedDataItem.getSiteName())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Site Name' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getItemSku())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Item Sku' requires information"));
            }

            if (!Utility.isSet(parsedDataItem.getQuantity())) {
                errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Field 'Quantity' requires information"));
            }

            if (Utility.isSet(parsedDataItem.getQuantity())) {
                try {
                    Integer.parseInt(parsedDataItem.getQuantity());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'Quantity' field: " + parsedDataItem.getQuantity()));
                }

            }

            if (Utility.isSet(parsedDataItem.getItemSku())) {
                try {
                    Integer.parseInt(parsedDataItem.getItemSku());
                } catch (Exception e) {
                    errors.add(MessageFormat.format(LOADER_FMT, lineNum, "Incorrect 'ItemSku' field: " + parsedDataItem.getQuantity()));
                }

            }
        }

        log.info("checkInputValues()=> END, Errors size: " + errors.size() + ".");

        return errors;

    }

    public class DBData {

        private DBJCPSiteMap jcpSiteDbMap;
        private DBJCPSkuUsageMap jcpSkuUsagesDbMap;
        private DBJCPInventoryItemMap jcpInventoryItemDbMap;
        private DBJCPItemMap jcpItemDbMap;

        public void setJcpSiteDbMap(DBJCPSiteMap jcpSiteDbMap) {
            this.jcpSiteDbMap = jcpSiteDbMap;
        }

        public void setJcpSkuUsagesDbMap(DBJCPSkuUsageMap jcpSkuUsagesDbMap) {
            this.jcpSkuUsagesDbMap = jcpSkuUsagesDbMap;
        }

        public DBJCPSiteMap getJcpSiteDbMap() {
            return jcpSiteDbMap;
        }

        public DBJCPSkuUsageMap getJcpSkuUsagesDbMap() {
            return jcpSkuUsagesDbMap;
        }

        public void setJcpInventoryItemDbMap(DBJCPInventoryItemMap jcpInventoryItemDbMap) {
            this.jcpInventoryItemDbMap = jcpInventoryItemDbMap;
        }

        public void setJcpItemDbMap(DBJCPItemMap jcpItemDbMap) {
            this.jcpItemDbMap = jcpItemDbMap;
        }


        public DBJCPInventoryItemMap getJcpInventoryItemDbMap() {
            return jcpInventoryItemDbMap;
        }

        public DBJCPItemMap getJcpItemDbMap() {
            return jcpItemDbMap;
        }
    }

    public class InboundData {

        InboundJCPItemParsMap mInboundSitePars;

        public InboundData() {
            this.mInboundSitePars = new InboundJCPItemParsMap();
        }


        public InboundJCPItemParsMap getInboundSitePars() {
            return mInboundSitePars;
        }

        public void setInboundSitePars(InboundJCPItemParsMap pInboundSitePars) {
            this.mInboundSitePars = pInboundSitePars;
        }
    }


    public class SkuUsagesMap extends HashMap<Integer/*Period*/, Double /*Value*/> {

        public SkuUsagesMap(String pSkuUsages, int pItemParQty) {

            super();

            int period = 1;
            String[] list = Utility.parseStringToArray(pSkuUsages, SKU_USAGE_DELIM);
            for (String o : list) {
                put(period++, new Double(o.trim()));
            }

            if (pItemParQty < 6) {
                for (period = 1; period <= 12; period++) {
                    if (period % 2 == 0) {
                        put(period, 0.0);
                    } else {
                        put(period, 1.0);
                    }
                }
            } else if (pItemParQty <= 12 && pItemParQty >= 6) {
                for (period = 1; period <= 12; period++) {
                    put(period, 1.0);
                }
            } else {
                for (period = 1; period <= 12; period++) {
                    put(period, get(period) * pItemParQty);
                }
            }
        }

    }

    public class InboundJCPItemParsMap extends HashMap<String/*SiteName*/, List<InboundJCPItemPar>> {
    }

    public class DBJCPSiteMap extends HashMap<String/*SiteName*/, List<DBJCPSite>> {
    }

    public class DBJCPInventoryItemMap extends HashMap<Integer/*SiteId*/, HashMap<Integer/*ItemId*/, List<InventoryLevelView>>> {
    }

    public class DBJCPSkuUsageMap extends HashMap<Integer/*AccountID*/, List<DBJCPSkuUsage>> {
    }

    public class DBJCPItemMap extends HashMap<Integer/*Sku*/, List<ItemData>> {
    }

    public class DBJCPSkuUsage {

        private String mSkuUsages;
        private int mAccountId;


        public String getSkuUsages() {
            return mSkuUsages;
        }

        public void setSkuUsages(String mSkuUsages) {
            this.mSkuUsages = mSkuUsages;
        }

        public int getAccountId() {
            return mAccountId;
        }

        public void setAccountId(int mAccountId) {
            this.mAccountId = mAccountId;
        }
    }

    public class DBJCPSite {

        private int mStoreId;
        private int mAccountId;
        private int mSiteId;
        private String mSiteName;

        public int getStoreId() {
            return mStoreId;
        }

        public void setStoreId(int pStoreId) {
            this.mStoreId = pStoreId;
        }

        public int getAccountId() {
            return mAccountId;
        }

        public void setAccountId(int pAccountId) {
            this.mAccountId = pAccountId;
        }

        public int getSiteId() {
            return mSiteId;
        }

        public void setSiteId(int pSiteId) {
            this.mSiteId = pSiteId;
        }

        public String getSiteName() {
            return mSiteName;
        }

        public void setSiteName(String pSiteName) {
            this.mSiteName = pSiteName;
        }
    }

    public class InboundJCPItemPar {

        private Integer mItemSku;
        private Integer mQuantity;

        public InboundJCPItemPar(Integer pItemSku, Integer pQuantity) {
            this.mItemSku = pItemSku;
            this.mQuantity = pQuantity;
        }

        public Integer getItemSku() {
            return mItemSku;
        }

        public void setItemSku(Integer mItemSku) {
            this.mItemSku = mItemSku;
        }

        public Integer getQuantity() {
            return mQuantity;
        }

        public void setQuantity(Integer mQuantity) {
            this.mQuantity = mQuantity;
        }

    }

    public class Reporter {

        private int mSiteProcessed = 0;
        private int mInventoryItemsProcessed = 0;
        private int mInventoryItemsChanged = 0;

        public void incrementSiteProcessed() {
            mSiteProcessed++;
        }

        public void incrementInventoryItemsProcessed() {
            mInventoryItemsProcessed++;
        }

        public void incrementInventoryItemsChanged() {
            mInventoryItemsChanged++;
        }

        public void print() {
            log.info("print()=> site processed            : " + mSiteProcessed);
            log.info("print()=> inventory Items processed : " + mInventoryItemsProcessed);
            log.info("print()=> inventory Items changed   : " + mInventoryItemsChanged);
        }

    }
}


