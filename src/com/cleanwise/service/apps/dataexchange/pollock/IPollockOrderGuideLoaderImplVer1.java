package com.cleanwise.service.apps.dataexchange.pollock;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.InboundFlatFile;
import org.apache.log4j.Logger;


import java.io.Serializable;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.*;

public class IPollockOrderGuideLoaderImplVer1 extends InboundFlatFile implements Serializable {

    private static final Logger log = Logger.getLogger(IPollockOrderGuideLoaderImplVer1.class);

    private static final String POLLOCK_ORDER_GUIDE_LOADER = "IPollockOrderGuideLoaderVer1";

    private List<IPollockCustomOrderGuide> mParsedDataList            = new ArrayList<IPollockCustomOrderGuide>();
    private List<InboundPollockOrderGuideLoaderView> mInboundDataList = new ArrayList<InboundPollockOrderGuideLoaderView>();
    private List<Line<InboundPollockOrderGuideLoaderView>> mBadLines  = new ArrayList<Line<InboundPollockOrderGuideLoaderView>>();

    private DBActivity mDBActivity = new DBActivity();

    private static final String ERROR_FMT = "Line {0} : {1}"; // error message format

    //errors
    protected static final String UNKNOWN_LINE           = "Unknown Record Type";
    protected static final String FIELD_REQUIRED         = "Field {0} requires information";
    protected static final String FIELD_IS_TO_LONG       = "Field {0} is too long";
    protected static final String SEG_REQUIRED           = "Field {0} requires information";
    protected static final String SEG_WRONG_FORMAT       = "Incorrect {0} field: {1}";
    protected static final String OP_NOT_SUPPORTED       = "Unknown Edit Type Code - {0}. Segment: {1}";
    protected static final String ACCOUNT_NOT_FOUND      = "Could not find account with account reference number {0}";
    protected static final String MULTIPLE_ACCOUNT_FOUND = "Multiple accounts found for account reference number {0}. AccountID(s): {1}";
    protected static final String SITE_NOT_FOUND         = "Could not find site with site reference number {0}";
    protected static final String MULTIPLE_SITE_FOUND    = "Multiple sites found for site reference number {0}, SiteID(s): {1}";
    protected static final String ITEM_NOT_FOUND         = "Could not find item with distributor sku {0}";
    protected static final String MULTIPLE_ITEM_FOUND    = "Multiple items found for distributor sku {0}, ItemID(s): {1}";
    protected static final String DIST_NOT_FOUND         = "Distributor not found.";
    protected static final String MULTIPLE_DIST_FOUND    = "Multiple distributor found.";

    //file fields
    protected static final String EDIT_TYPE                = "Edit Type";
    protected static final String ORDER_GIDE_NAME          = "Order Guide Name";
    protected static final String ACCOUNT_REFERENCE_NUMBER = "Account Reference Number";
    protected static final String SITE_REFERENCE_NUMBER    = "Site Reference Number";
    protected static final String SEQ_NUMBER               = "Sequence Number";
    protected static final String CATEGORY_NAME            = "Category Name";
    protected static final String DISTR_SKU                = "Distributor Sku";
    protected static final String RECORD_TYPE              = "Record Type";

    //record type
    protected static interface I_RECORD_TYPE {
        public static final String HEADER = "H"; //Categorization Header
        public static final String SITE   = "S"; //Custom Order Guide Account/Site Association
        public static final String DETAIL = "D"; //Item Detail
    }

    // operation type
    protected static interface OP_TYPE {
        public static final String ADD_OR_CHANGE = "C";
        public static final String DELETE = "D";
    }

    protected enum DBTable  { CLW_ORDER_GUIDE, CLW_ORDER_GUIDE_STRUCTURE }
    protected enum DBAction { DELETE, CREATE, UPDATE }

    public IPollockOrderGuideLoaderImplVer1() {
        super.setSepertorChar('|');
        super.setQuoteChar('"');
    }

    public void init() {
        super.init();
        mParsedDataList.clear();
    }

    protected void processParsedObject(Object pParsedObject) throws Exception {
        if (pParsedObject instanceof InboundPollockOrderGuideLoaderView) {
            processParsedLine((InboundPollockOrderGuideLoaderView) pParsedObject);
        } else {
            throw new IllegalArgumentException("No parsed object present!");
        }
    }

    protected void doPostProcessing() throws Exception {

        log.info("doPostProcessing()=> BEGIN");

        Connection con = null;
        try {

            con = getConnection();

            int storeId = super.getTranslator().getStoreId();
            TradingPartnerData partner = this.getTranslator().getPartner();

            log.info("doPostProcessing()=> StoreID: "+storeId);
            log.info("doPostProcessing()=> Partner: "+partner.getShortDesc()+", PartnerID: "+partner.getTradingPartnerId());

            BusEntityDataVector distributorDBInfo = PollockLoaderAssist.defineDistributor(con, storeId);
            List<String> errors = verifyDBDistributorInfo(distributorDBInfo);
            if(!errors.isEmpty()){
                throw processErrors(errors);
            }

            BusEntityData distributor = ((BusEntityData) distributorDBInfo.get(0));
            log.info("doPostProcessing()=> Distributor: "+distributor.getShortDesc()+", DistributorID: "+distributor.getBusEntityId());

            List<IPollockCustomOrderGuide> inboundGuideList = getIPollockCustomOrderGuides();

            log.info("doPostProcessing()=> OG.Size: "+inboundGuideList.size());

            log.info("doPostProcessing()=> Getting data from database..." );

            DBDataImpl dataBaseInfo = getCustomOrderGuideDBImpl(con, storeId, distributor.getBusEntityId(), inboundGuideList);

            List<Line<InboundPollockOrderGuideLoaderView>> badLines = getBadLines();
            if (!badLines.isEmpty()) {
                log.info("doPostProcessing()=> " + badLines.size() + " bad line(s) detected!!! Process will be rejected after db.synch.");
            }

            log.info("doPostProcessing()=> Starting database synchronization..." );

            dbsync(con, storeId, inboundGuideList, dataBaseInfo);

            log.info("doPostProcessing()=> DB synchronization is finished." );

            printDbActivity();

            if (!badLines.isEmpty()) {
                throw processLineErrors(badLines);
            }

        } finally {
            closeConnection(con);
        }

        log.info("doPostProcessing()=> END.");
    }

    protected void dbsync(Connection pCon, int pStoreId, List<IPollockCustomOrderGuide> pInboundGuides, DBDataImpl pDBDataImpl) throws Exception {

        log.info("dbsync()=> BEGIN");

        CustomOrderGuideDBImpl guideDB = (CustomOrderGuideDBImpl) pDBDataImpl;

        for (IPollockCustomOrderGuide iOrderGuide : pInboundGuides) {

            printOrderGuideInfo("dbsync()", iOrderGuide);

            if (OP_TYPE.DELETE.equals(iOrderGuide.getOp())) {

                List<Integer> orderGuidesToDelete = PollockLoaderAssist.getOrderGuideIds(pCon, pStoreId, iOrderGuide.getOrderGuideName());

                deleteOrderGuides(orderGuidesToDelete);

                Map<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> orderGuidesMap = guideDB.getAppPollockCustomOrderGuideDBInfo();
                for (Map<String, Map<Integer, List<OrderGuideStructureData>>> orderGuides : orderGuidesMap.values()) {
                    orderGuides.remove(iOrderGuide.getOrderGuideName());
                }

            } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuide.getOp())) {

                List<IPollockCustomOrderGuideAssoc> assocList = iOrderGuide.getOrderGuideAssocList();

                for (IPollockCustomOrderGuideAssoc assoc : assocList) {

                    Integer busEntityId = Utility.isSet(assoc.getSiteRefNum())
                            ? guideDB.getSiteId(assoc.getAccountRefNum(), assoc.getSiteRefNum())
                            : guideDB.getAccountId(assoc.getAccountRefNum());


                    if (OP_TYPE.DELETE.equals(assoc.getOp())) {

                        Integer orderGuideId = guideDB.getOrderGuideId(busEntityId, iOrderGuide.getOrderGuideName());
                        if (orderGuideId != null) {
                            deleteOrderGuides(Utility.toIdVector(orderGuideId));
                            guideDB.getAppPollockCustomOrderGuideDBInfo().get(busEntityId).remove(iOrderGuide.getOrderGuideName());
                        }

                    } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuide.getOp())) {

                        Map<String, Map<Integer, List<OrderGuideStructureData>>> orderGuides = guideDB.getAppPollockCustomOrderGuideDBInfo().get(busEntityId);
                        if (orderGuides == null) {
                            orderGuides = new HashMap<String, Map<Integer, List<OrderGuideStructureData>>>();
                            guideDB.getAppPollockCustomOrderGuideDBInfo().put(busEntityId, orderGuides);
                        }

                        Map<Integer, List<OrderGuideStructureData>> orderGuideDetailMap = orderGuides.get(iOrderGuide.getOrderGuideName());
                        if (orderGuideDetailMap == null) {
                            int orderGuideId = createOrderGuide(iOrderGuide.getOrderGuideName(), busEntityId, POLLOCK_ORDER_GUIDE_LOADER);
                            orderGuideDetailMap = new HashMap<Integer, List<OrderGuideStructureData>>();
                            orderGuideDetailMap.put(orderGuideId, new ArrayList<OrderGuideStructureData>());
                        }

                        Map<Integer,OrderGuideStructureData> orderGuideItemsToDelete  = new HashMap<Integer,OrderGuideStructureData> ();
                        Map<Integer,OrderGuideStructureData> orderGuideItemsToCreate  = new HashMap<Integer,OrderGuideStructureData> ();
                        Map<Integer,OrderGuideStructureData> orderGuideItemsToUpdate  = new HashMap<Integer,OrderGuideStructureData> ();

                        List<OrderGuideStructureData> orderGuideDetails = orderGuideDetailMap.values().iterator().next();
                        Map<Integer, OrderGuideStructureData> orderGuideItemByItemId = toMapByItemId(orderGuideDetails);

                        Integer orderGuideId = orderGuideDetailMap.keySet().iterator().next();

                        for (IPollockCustomOrderGuideDetail iOrderGuideDetail : iOrderGuide.getOrderGuideDetailList()) {

                        	Set<Integer> itemIds = guideDB.getItemId(iOrderGuideDetail.getDistributorSku());
                            //int itemId = guideDB.getItemId(iOrderGuideDetail.getDistributorSku());
                        	Iterator it = itemIds.iterator();
                        	while(it.hasNext()){
                        		int itemId = ((Integer)it.next()).intValue();
                        		
                        		OrderGuideStructureData orderGuideStructure = orderGuideItemByItemId.get(itemId);

                                if (OP_TYPE.DELETE.equals(iOrderGuideDetail.getOp())) {
                                    if (orderGuideStructure != null) {
                                        orderGuideItemsToCreate.remove(orderGuideStructure.getItemId());
                                        orderGuideItemsToDelete.put(orderGuideStructure.getItemId(), orderGuideStructure);
                                    }
                                } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuideDetail.getOp())) {

                                    if (orderGuideStructure == null) {

                                        orderGuideStructure = PollockLoaderAssist.createOrderGuideItem(
                                                itemId,
                                                iOrderGuideDetail.getCategoryName(),
                                                iOrderGuideDetail.getSequenceNumber());

                                        orderGuideItemsToCreate.put(orderGuideStructure.getItemId(), orderGuideStructure);
                                        orderGuideItemsToDelete.remove(orderGuideStructure.getItemId());

                                    } else {

                                        if (!Utility.strNN(orderGuideStructure.getCustCategory()).equals(iOrderGuideDetail.getCategoryName())
                                                || orderGuideStructure.getSortOrder() != (iOrderGuideDetail.getSequenceNumber())) {

                                            orderGuideStructure.setCustCategory(iOrderGuideDetail.getCategoryName());
                                            orderGuideStructure.setSortOrder(iOrderGuideDetail.getSequenceNumber());

                                            orderGuideItemsToUpdate.put(orderGuideStructure.getItemId(), orderGuideStructure);
                                            orderGuideItemsToDelete.remove(orderGuideStructure.getItemId());

                                        }
                                    }
                                }
                        	}
                        	
                            
                        }

                        IdVector idsToDelete = Utility.toIdVector(new ArrayList<OrderGuideStructureData>(orderGuideItemsToDelete.values()));
                        deleteOrderGuideItems(idsToDelete);
                        for (Integer itemId : orderGuideItemsToDelete.keySet()) {
                            orderGuideItemByItemId.remove(itemId);
                        }

                        List<OrderGuideStructureData> itemsToCreate = new ArrayList<OrderGuideStructureData>(orderGuideItemsToCreate.values());
                        itemsToCreate =  addNewOrderGuideItems(orderGuideId, itemsToCreate, POLLOCK_ORDER_GUIDE_LOADER);
                        for (OrderGuideStructureData item : itemsToCreate) {
                            orderGuideItemByItemId.put(item.getItemId(), item);
                        }

                        List<OrderGuideStructureData> itemsToUpdate = new ArrayList<OrderGuideStructureData>(orderGuideItemsToUpdate.values());
                        itemsToUpdate =  updateOrderGuideItems(orderGuideId, itemsToUpdate, POLLOCK_ORDER_GUIDE_LOADER);
                        for (OrderGuideStructureData item : itemsToUpdate) {
                            orderGuideItemByItemId.put(item.getItemId(), item);
                        }
                    }

                }
            }
        }

        log.info("dbsync()=> END.");

    }

    protected DBDataImpl getCustomOrderGuideDBImpl(Connection pCon,
                                                   int pStoreId,
                                                   int pDistrId,
                                                   List<IPollockCustomOrderGuide> pInboundGuideList) throws Exception {

        log.info("getCustomOrderGuideDBImpl()=> BEGIN");

        CustomOrderGuideDBImpl ogDBImpl = new CustomOrderGuideDBImpl();

        IPollockOrderGuideServiceObject serviceObj = createIPollockOrderGuideServiceObject(pInboundGuideList);

        Map<String, Set<Integer>> itemDbInfo = PollockLoaderAssist.defineItemId(pCon, pDistrId, serviceObj.getDistSkus());
        Map<String/*DistSku*/, String/*ErrorMessage*/> itemsWithErrors = verifyDBItemInfo(serviceObj.getDistSkus(), itemDbInfo);
        if (!itemsWithErrors.isEmpty()) {
            processDataErrors(itemDbInfo, itemsWithErrors, serviceObj.getItemLines());
        }

        for (Map.Entry<String, Set<Integer>> e : itemDbInfo.entrySet()) {
            ogDBImpl.getAppItemData().put(e.getKey(), e.getValue());
        }

        Map<String, Set<Integer>> accountDbInfo = PollockLoaderAssist.defineAccountId(pCon, pStoreId, serviceObj.getAccountRefNumbers());
        Map<String/*AccountNumber*/, String/*ErrorMessage*/> accountsWithErrors = verifyDBAccountInfo(serviceObj.getAccountRefNumbers(), accountDbInfo);
        if (!accountsWithErrors.isEmpty()) {
            processDataErrors(accountDbInfo, accountsWithErrors, serviceObj.getAccountLines());
        }

        for (Map.Entry<String, Set<Integer>> e : accountDbInfo.entrySet()) {

            int accountId = e.getValue().iterator().next();

            Map<Integer, Map<String, Integer>> appAccountMap = ogDBImpl.getAppAccountData().get(e.getKey());
            if (appAccountMap == null) {
                appAccountMap = new HashMap<Integer, Map<String, Integer>>();
                ogDBImpl.getAppAccountData().put(e.getKey(), appAccountMap);
            }

            Map<String, Integer> siteMap = appAccountMap.get(accountId);
            if (siteMap == null) {
                siteMap = new HashMap<String, Integer>();
                appAccountMap.put(accountId, siteMap);
            }

            Set<String> siteRefs = serviceObj.getSiteRefNumbers().get(e.getKey());
            Map<String, Set<Integer>> siteDBInfo = PollockLoaderAssist.defineSiteId(pCon, accountId, siteRefs);
            Map<String/*SiteNumber*/, String/*ErrorMessage*/> sitesWithErrors = verifyDBSiteInfo(siteRefs, siteDBInfo);
            if (!sitesWithErrors.isEmpty()) {
                processDataErrors(siteDBInfo, sitesWithErrors, serviceObj.getSiteLines().get(e.getKey()));
            }

            for (Map.Entry<String, Set<Integer>> siteRefNumberEntry : siteDBInfo.entrySet()) {
                siteMap.put(siteRefNumberEntry.getKey(), siteRefNumberEntry.getValue().iterator().next());
            }

            Set<String> orderGuideNames = serviceObj.getOrderGuideNames().get(e.getKey());

            Set<Integer> busEntityIds = new HashSet<Integer>();
            busEntityIds.add(e.getValue().iterator().next());
            for (Set<Integer> val : siteDBInfo.values()) {
                busEntityIds.addAll(val);
            }

            Map<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> orderGuideDbInfo =
                    PollockLoaderAssist.defineOrderGuides(
                            pCon,
                            busEntityIds,
                            orderGuideNames
                    );

            ogDBImpl.add(orderGuideDbInfo);

        }

        log.info("getCustomOrderGuideDBImpl()=> END.");

        return ogDBImpl;

    }

    protected IPollockOrderGuideServiceObject createIPollockOrderGuideServiceObject(List<IPollockCustomOrderGuide> pInboundGuideList) {

        IPollockOrderGuideServiceObject serviceobj = new IPollockOrderGuideServiceObject();

        Set<String>                                      distSkus  = new HashSet<String>();
        Map<String, Set<String>>            siteRefSetByAccountRef = new HashMap<String, Set<String>>();
        Map<String, Set<String>> orderGuideNamesByAccountRefNumber = new HashMap<String, Set<String>>();
        Map<String, Set<Integer>>                     accountLines = new HashMap<String, Set<Integer>>();
        Map<String, Map<String, Set<Integer>>>           siteLines = new HashMap<String, Map<String, Set<Integer>>>();
        Map<String, Set<Integer>>                        itemLines = new HashMap<String, Set<Integer>>();

        for (IPollockCustomOrderGuide oGuide : pInboundGuideList) {

            for (IPollockCustomOrderGuideAssoc assoc : oGuide.getOrderGuideAssocList()) {

                if (Utility.isSet(assoc.getSiteRefNum())) {

                    Set<String> siteRefSet = siteRefSetByAccountRef.get(assoc.getAccountRefNum());
                    if (siteRefSet == null) {
                        siteRefSet = new HashSet<String>();
                        siteRefSetByAccountRef.put(assoc.getAccountRefNum(), siteRefSet);
                    }
                    siteRefSet.add(assoc.getSiteRefNum());

                    Map<String, Set<Integer>> linesBySiteRefNumber = siteLines.get(assoc.getAccountRefNum());
                    if (linesBySiteRefNumber == null) {
                        linesBySiteRefNumber = new HashMap<String, Set<Integer>>();
                        siteLines.put(assoc.getAccountRefNum(), linesBySiteRefNumber);
                    }

                    Set<Integer> slines = linesBySiteRefNumber.get(assoc.getSiteRefNum());
                    if (slines == null) {
                        slines = new HashSet<Integer>();
                        linesBySiteRefNumber.put(assoc.getSiteRefNum(), slines);
                    }
                    slines.add(assoc.getDataLineNumber());

                }

                Set<Integer> lines = accountLines.get(assoc.getAccountRefNum());
                if (lines == null) {
                    lines = new HashSet<Integer>();
                    accountLines.put(assoc.getAccountRefNum(), lines);
                }
                lines.add(assoc.getDataLineNumber());

                Set<String> names = orderGuideNamesByAccountRefNumber.get(assoc.getAccountRefNum());
                if (names == null) {
                    names = new HashSet<String>();
                    orderGuideNamesByAccountRefNumber.put(assoc.getAccountRefNum(), names);
                }
                names.add(oGuide.getOrderGuideName());
            }

            for (IPollockCustomOrderGuideDetail item : oGuide.getOrderGuideDetailList()) {

                distSkus.add(item.getDistributorSku());

                Set<Integer> ilines = itemLines.get(item.getDistributorSku());
                if (ilines == null) {
                    ilines = new HashSet<Integer>();
                    itemLines.put(item.getDistributorSku(), ilines);
                }
                ilines.add(item.getDataLineNumber());
            }

        }

        serviceobj.setDistSkus(distSkus);
        serviceobj.setSiteRefNumbers(siteRefSetByAccountRef);
        serviceobj.setAccountRefNumbers(siteRefSetByAccountRef.keySet());
        serviceobj.setOrderGuideNames(orderGuideNamesByAccountRefNumber);
        serviceobj.setAccountLines(accountLines);
        serviceobj.setSiteLines(siteLines);
        serviceobj.setItemLines(itemLines);

        return serviceobj;

    }

    private List<String> verifyDBDistributorInfo(BusEntityDataVector pDistrDbInfo) {

        List<String> result = new ArrayList<String>();
        if (pDistrDbInfo == null || pDistrDbInfo.isEmpty()) {
            result.add(DIST_NOT_FOUND);
        } else if (pDistrDbInfo.size() > 1) {
            result.add(MULTIPLE_DIST_FOUND);
        }

        return result;

    }

    protected Map<String, String> verifyDBItemInfo(Set<String> pInboundDistSkus, Map<String, Set<Integer>> pItemDbInfo) {

        Map<String, String> result = new HashMap<String, String>();
        for (String inboundSku: pInboundDistSkus) {
            Set<Integer> val = pItemDbInfo.get(inboundSku);
            if (val == null || val.isEmpty()) {
                result.put(inboundSku, MessageFormat.format(ITEM_NOT_FOUND, inboundSku));
            } /*else if (val.size() > 1) {
                result.put(inboundSku, MessageFormat.format(MULTIPLE_ITEM_FOUND, inboundSku, val));
            }*/
        }

        return result;

    }

    protected Map<String, String> verifyDBAccountInfo(Set<String> pInboundAccounts, Map<String, Set<Integer>> pAccountDbInfo) {

        Map<String, String> result = new HashMap<String, String>();
        for(String inboundAccount:pInboundAccounts) {
            Set<Integer> val = pAccountDbInfo.get(inboundAccount);
            if (val  == null || val .isEmpty()) {
                result.put(inboundAccount , MessageFormat.format(ACCOUNT_NOT_FOUND, inboundAccount));
            } else if (val.size() > 1) {
                result.put(inboundAccount, MessageFormat.format(MULTIPLE_ACCOUNT_FOUND, inboundAccount, val));
            }
        }

        return result;

    }

    protected Map<String, String> verifyDBSiteInfo(Set<String> pInboundSiteRefs, Map<String, Set<Integer>> pSiteDbInfo) {

        Map<String, String> result = new HashMap<String, String>();
        for (String inboundSiteRef: pInboundSiteRefs) {
            Set<Integer> val = pSiteDbInfo.get(inboundSiteRef);
            if (val == null || val.isEmpty()) {
                result.put(inboundSiteRef, MessageFormat.format(SITE_NOT_FOUND, inboundSiteRef));
            } else if (val.size() > 1) {
                result.put(inboundSiteRef, MessageFormat.format(MULTIPLE_SITE_FOUND, inboundSiteRef, val));
            }
        }

        return result;

    }

    protected void processParsedLine(InboundPollockOrderGuideLoaderView pParsedObject) throws Exception {

        int lineNum = addNewInboundObject(pParsedObject);

        List<String> errors = verifyRecord(pParsedObject);

        if (errors.isEmpty()) {

            if (isNextOrderGuide(pParsedObject)) {

                String orderGuideName = pParsedObject.getRecordValue1();

                IPollockCustomOrderGuide orderGuide = new IPollockCustomOrderGuide();
                orderGuide.setOrderGuideName(orderGuideName);
                orderGuide.setOp(pParsedObject.getEditType());
                orderGuide.setDataLineNumber(lineNum);

                addNewGuide(orderGuide);

            } else {

                IPollockCustomOrderGuide orderGuide = getLastOrderGuide();

                if (isNextBusEntityAssoc(pParsedObject)) {

                    IPollockCustomOrderGuideAssoc assoc = new IPollockCustomOrderGuideAssoc();
                    assoc.setAccountRefNum(pParsedObject.getRecordValue1());
                    assoc.setOp(pParsedObject.getEditType());
                    assoc.setSiteRefNum(pParsedObject.getRecordValue2());
                    assoc.setDataLineNumber(lineNum);

                    orderGuide.getOrderGuideAssocList().add(assoc);

                } else if (isNextOrderGuideItem(pParsedObject)) {

                    IPollockCustomOrderGuideDetail orderGuideItem = new IPollockCustomOrderGuideDetail();

                    orderGuideItem.setSequenceNumber(Utility.parseInt(pParsedObject.getRecordValue1()));
                    orderGuideItem.setCategoryName(pParsedObject.getRecordValue2());
                    orderGuideItem.setDistributorSku(pParsedObject.getRecordValue3());
                    orderGuideItem.setOp(pParsedObject.getEditType());
                    orderGuideItem.setDataLineNumber(lineNum);

                    orderGuide.getOrderGuideDetailList().add(orderGuideItem);

                }

            }

        } else {
            log.info("processParsedLine()=> WARN: Found errors in line " + lineNum + ", Errors- " + errors);
            addBadLine(new Line<InboundPollockOrderGuideLoaderView>(lineNum, pParsedObject, errors));
        }

    }

    protected List<String> verifyRecord(InboundPollockOrderGuideLoaderView pParsedObject) {

        List<String> errors = new ArrayList<String>();

        if (I_RECORD_TYPE.HEADER.equalsIgnoreCase(pParsedObject.getRecordType())) {

            if (!Utility.isSet(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, EDIT_TYPE));
            } else if (!OP_TYPE.ADD_OR_CHANGE.equalsIgnoreCase(pParsedObject.getEditType()) && !OP_TYPE.DELETE.equalsIgnoreCase(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(OP_NOT_SUPPORTED, pParsedObject.getEditType(), I_RECORD_TYPE.HEADER));
            }

            if (!Utility.isSet(pParsedObject.getRecordValue1())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, ORDER_GIDE_NAME));
            }

        } else if (I_RECORD_TYPE.SITE.equalsIgnoreCase(pParsedObject.getRecordType())) {

            if (!Utility.isSet(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, EDIT_TYPE));
            } else if (!OP_TYPE.ADD_OR_CHANGE.equalsIgnoreCase(pParsedObject.getEditType()) && !OP_TYPE.DELETE.equalsIgnoreCase(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(OP_NOT_SUPPORTED, pParsedObject.getEditType(), I_RECORD_TYPE.SITE));
            }

            if (!Utility.isSet(pParsedObject.getRecordValue1())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, ACCOUNT_REFERENCE_NUMBER));
            }

        } else if (I_RECORD_TYPE.DETAIL.equalsIgnoreCase(pParsedObject.getRecordType())) {

            if (!Utility.isSet(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, EDIT_TYPE));
            } else if (!OP_TYPE.ADD_OR_CHANGE.equalsIgnoreCase(pParsedObject.getEditType()) && !OP_TYPE.DELETE.equalsIgnoreCase(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(OP_NOT_SUPPORTED, pParsedObject.getEditType(), I_RECORD_TYPE.DETAIL));
            }

            if (!Utility.isSet(pParsedObject.getRecordValue1())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, SEQ_NUMBER));
            } else {
                try {
                    Integer.parseInt(pParsedObject.getRecordValue1());
                } catch (NumberFormatException e) {
                    errors.add(MessageFormat.format(SEG_WRONG_FORMAT, SEQ_NUMBER, pParsedObject.getRecordValue1()));
                }
            }

            if (!Utility.isSet(pParsedObject.getRecordValue2())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, CATEGORY_NAME));
            } else if (pParsedObject.getRecordValue2().length() > 255) {
                errors.add(MessageFormat.format(FIELD_IS_TO_LONG, CATEGORY_NAME));
            }

            if (!Utility.isSet(pParsedObject.getRecordValue3())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, DISTR_SKU));
            }

        } else if (!Utility.isSet(pParsedObject.getRecordType())) {
            errors.add(MessageFormat.format(FIELD_REQUIRED, RECORD_TYPE));
        } else {
            errors.add(UNKNOWN_LINE);
        }

        return errors;

    }

    private Exception processErrors(List<String> pErrors) {
        for (String error : pErrors) {
            this.appendErrorMsgs(error);
        }
        return new Exception(super.getFormatedErrorMsgs());
    }

    private Exception processLineErrors(List<Line<InboundPollockOrderGuideLoaderView>> pLines) {

        Collections.sort(pLines, new Comparator<Line<InboundPollockOrderGuideLoaderView>>() {
            public int compare(Line<InboundPollockOrderGuideLoaderView> pLine1, Line<InboundPollockOrderGuideLoaderView> pLine2) {
                return pLine1.getLine() != pLine2.getLine() ? pLine1.getLine() > pLine2.getLine() ? 1 : -1 : 0;
            }
        });

        for (Line<InboundPollockOrderGuideLoaderView> line : pLines) {
            for (String error : line.getErrors()) {
                this.appendErrorMsgs(MessageFormat.format(ERROR_FMT, String.valueOf(line.getLine()), error));
            }
        }

        return new Exception(super.getFormatedErrorMsgs());
    }

    protected <T> void processDataErrors(Map<T, ?> pDBInfo, Map<T, String> pObjectErrors, Map<T, Set<Integer>> pLines) {
        for (T o : pObjectErrors.keySet()) {
            pDBInfo.remove(o);
            Set<Integer> badLines = pLines.get(o);
            for (int badline : badLines) {
                markBadLine(badline, pObjectErrors.get(o));
            }
        }
    }

    private void markBadLine(int pLine, String pError) {

        log.info("markBadLine()=> pLine: " + pLine + ", pError: " + pError);

        List<Line<InboundPollockOrderGuideLoaderView>> lines = getBadLines();
        for (Line<InboundPollockOrderGuideLoaderView> line : lines) {
            if (line.getLine() == pLine) {
                line.getErrors().add(pError);
                return;
            }
        }

        Iterator<IPollockCustomOrderGuide> ogIt = getIPollockCustomOrderGuides().iterator();
        while (ogIt.hasNext()) {

            IPollockCustomOrderGuide guide = ogIt.next();
            if (guide.getDataLineNumber() == pLine && pLine > 0) {
                log.info("markBadLine()=> removing line from og, line data: " + getInboundDataList().get(pLine - 1));
                ogIt.remove();
                break;
            }

            boolean removed = false;
            Iterator<IPollockCustomOrderGuideAssoc> assocIt = guide.getOrderGuideAssocList().iterator();
            while (assocIt.hasNext()) {
                IPollockCustomOrderGuideAssoc assoc = assocIt.next();
                if (assoc.getDataLineNumber() == pLine && pLine > 0) {
                    log.info("markBadLine()=> removing line from assoc, line data: " + getInboundDataList().get(pLine - 1));
                    assocIt.remove();
                    removed = true;
                    break;
                }
            }

            if (!removed) {
                Iterator<IPollockCustomOrderGuideDetail> detIt = guide.getOrderGuideDetailList().iterator();
                while (detIt.hasNext()) {
                    IPollockCustomOrderGuideDetail det = detIt.next();
                    if (det.getDataLineNumber() == pLine && pLine > 0) {
                        log.info("markBadLine()=> removing line from detail, line data: " + getInboundDataList().get(pLine - 1));
                        detIt.remove();
                        break;
                    }
                }
            }
        }

        addBadLine(new Line<InboundPollockOrderGuideLoaderView>(pLine, getInboundDataList().get(pLine - 1), Utility.getAsList(pError)));

    }

    protected void printOrderGuideInfo(String pSource, IPollockCustomOrderGuide pOrderGuide) {

        int assocSize = pOrderGuide.getOrderGuideAssocList().size();
        int assocToDeleteSize = pOrderGuide.getOrderGuideAssocList(OP_TYPE.DELETE).size();
        int assocToAddOrUpdateSize = pOrderGuide.getOrderGuideAssocList(OP_TYPE.ADD_OR_CHANGE).size();

        int detailSize = pOrderGuide.getOrderGuideDetailList().size();
        int detailToDeleteSize = pOrderGuide.getOrderGuideDetailList(OP_TYPE.DELETE).size();
        int detailToAddOrUpdateSize = pOrderGuide.getOrderGuideDetailList(OP_TYPE.ADD_OR_CHANGE).size();

        log.info(pSource + "=>" + " ****************************************************");
        log.info(pSource + "=>" + "       Order Guide Name : " + pOrderGuide.getOrderGuideName());
        log.info(pSource + "=>" + "              Data Line : " + pOrderGuide.getDataLineNumber());
        log.info(pSource + "=>" + "            OperationCD : " + pOrderGuide.getOp());
        log.info(pSource + "=>" + "             Assoc.Size : " + assocSize + ", C-" + assocToAddOrUpdateSize + ", D-" + assocToDeleteSize);
        log.info(pSource + "=>" + "            Detail.Size : " + detailSize + ", C-" + detailToAddOrUpdateSize + ", D-" + detailToDeleteSize);
        log.info(pSource + "=>" + " ****************************************************");

    }

    private void printDbActivity() {
        log.info("printDbActivity()=> BEGIN");
        for (Map.Entry<DBTable, Map<DBAction, Integer>> e : getDBActivity().entrySet()) {
            String tstr = e.getKey().toString() + " : ";
            String astr = "";
            for (Map.Entry<DBAction, Integer> e1 : e.getValue().entrySet()) {
                if (Utility.isSet(astr)) {
                    astr += " ,";
                }
                astr += e1.getKey() + "-" + e1.getValue();
            }
            tstr += astr;
            log.info("printDbActivity()=> " + tstr);
        }
        log.info("printDbActivity()=> END.");
    }

    private List<OrderGuideStructureData> updateOrderGuideItems(Integer pOrderGuideId, List<OrderGuideStructureData> pItems, String pUser) throws Exception {
        List<OrderGuideStructureData> items = PollockLoaderAssist.updateOrderGuideItems(pOrderGuideId, pItems, pUser);
        mDBActivity.addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.UPDATE, items.size());
        return items;
    }

    public List<OrderGuideStructureData> addNewOrderGuideItems(Integer pOrderGuideId, List<OrderGuideStructureData> pItems, String pUser) throws Exception {
        List<OrderGuideStructureData> items = PollockLoaderAssist.updateOrderGuideItems(pOrderGuideId, pItems, pUser);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.CREATE, items.size());
        return items;
    }

    private void deleteOrderGuideItems(IdVector pOrderGuideStructureIds) throws Exception {
        int n = PollockLoaderAssist.deleteOrderGuideItems(pOrderGuideStructureIds);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.DELETE, n);
    }

    private int createOrderGuide(String pOrderGuideName, Integer pBusEntityId, String pUser) throws Exception {
        int orderGuideId = PollockLoaderAssist.createOrderGuide(pOrderGuideName, pBusEntityId, pUser);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE, DBAction.CREATE, orderGuideId > 0 ? 1 : 0);
        return orderGuideId;
    }

    protected void deleteOrderGuides(List pOrderGuidesToDelete) throws Exception {
        int[] n = PollockLoaderAssist.deleteOrderGuides(pOrderGuidesToDelete);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE, DBAction.DELETE, n[0]);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.DELETE, n[1]);
    }

    private Map<Integer, OrderGuideStructureData> toMapByItemId(List<OrderGuideStructureData> pOrderGuideDetails) {
        Map<Integer, OrderGuideStructureData> result = new HashMap<Integer, OrderGuideStructureData>();
        if (pOrderGuideDetails != null && !pOrderGuideDetails.isEmpty()) {
            for (OrderGuideStructureData item : pOrderGuideDetails) {
                result.put(item.getItemId(), item);
            }
        }
        return result;
    }

    public List<Line<InboundPollockOrderGuideLoaderView>> getBadLines() {
        return mBadLines;
    }

    public void addBadLine(Line<InboundPollockOrderGuideLoaderView> pLine) {
        mBadLines.add(pLine);
    }

    private int addNewInboundObject(InboundPollockOrderGuideLoaderView pParsedObject) {
        mInboundDataList.add(pParsedObject);
        return mInboundDataList.size();
    }

    private void addNewGuide(IPollockCustomOrderGuide pOrderGuide) {
        mParsedDataList.add(pOrderGuide);
    }

    public IPollockCustomOrderGuide getLastOrderGuide() {
        return mParsedDataList.get(mParsedDataList.size() - 1);
    }

    private List<IPollockCustomOrderGuide> getIPollockCustomOrderGuides() {
        return mParsedDataList;
    }

    public List<InboundPollockOrderGuideLoaderView> getInboundDataList() {
        return mInboundDataList;
    }

    public DBActivity getDBActivity() {
        return mDBActivity;
    }

    private boolean isNextOrderGuideItem(InboundPollockOrderGuideLoaderView pView) {
        return I_RECORD_TYPE.DETAIL.equalsIgnoreCase(pView.getRecordType());
    }

    private boolean isNextBusEntityAssoc(InboundPollockOrderGuideLoaderView pView) {
        return I_RECORD_TYPE.SITE.equalsIgnoreCase(pView.getRecordType());
    }

    private boolean isNextOrderGuide(InboundPollockOrderGuideLoaderView pView) {
        return I_RECORD_TYPE.HEADER.equalsIgnoreCase(pView.getRecordType());
    }

    public class IPollockCustomOrderGuide {

        private int mDataLineNumber;
        private String mOrderGuideName;
        private String mOp;
        private List<IPollockCustomOrderGuideAssoc> mOrderGuideAssocList;
        private List<IPollockCustomOrderGuideDetail> mOrderGuideDetailList;

        public IPollockCustomOrderGuide() {
            mOrderGuideAssocList = new  ArrayList<IPollockCustomOrderGuideAssoc>();
            mOrderGuideDetailList = new  ArrayList<IPollockCustomOrderGuideDetail>();
        }

        public String getOrderGuideName() {
            return mOrderGuideName;
        }

        public void setOrderGuideName(String pOrderGuideName) {
            this.mOrderGuideName = pOrderGuideName;
        }

        public String getOp() {
            return mOp;
        }

        public void setOp(String pOp) {
            this.mOp = pOp;
        }

        public List<IPollockCustomOrderGuideAssoc> getOrderGuideAssocList() {
            return mOrderGuideAssocList;
        }

        public void setOrderGuideAssocList(List<IPollockCustomOrderGuideAssoc> pOrderGuideAssocList) {
            this.mOrderGuideAssocList = pOrderGuideAssocList;
        }

        public List<IPollockCustomOrderGuideDetail> getOrderGuideDetailList() {
            return mOrderGuideDetailList;
        }

        public void setOrderGuideDetailList(List<IPollockCustomOrderGuideDetail> pOrderGuideDetailList) {
            this.mOrderGuideDetailList = pOrderGuideDetailList;
        }

        public void setDataLineNumber(int pDataLineNumber) {
            this.mDataLineNumber = pDataLineNumber;
        }

        public int getDataLineNumber() {
            return mDataLineNumber;
        }

        public List<IPollockCustomOrderGuideAssoc> getOrderGuideAssocList(String pOp) {
            List<IPollockCustomOrderGuideAssoc> res = new ArrayList<IPollockCustomOrderGuideAssoc>();
            for (IPollockCustomOrderGuideAssoc assoc : mOrderGuideAssocList) {
                if (assoc.getOp().equals(pOp)) {
                    res.add(assoc);
                }
            }
            return res;
        }

        public List<IPollockCustomOrderGuideDetail> getOrderGuideDetailList(String pOp) {
            List<IPollockCustomOrderGuideDetail> res = new ArrayList<IPollockCustomOrderGuideDetail>();
            for (IPollockCustomOrderGuideDetail detail : mOrderGuideDetailList) {
                if (detail.getOp().equals(pOp)) {
                    res.add(detail);
                }
            }
            return res;
        }
    }

    public class IPollockCustomOrderGuideAssoc {

        private String mAccountRefNum;
        private String mSiteRefNum;
        private String mOp;
        private int mDataLineNumber;

        public String getAccountRefNum() {
            return mAccountRefNum;
        }

        public void setAccountRefNum(String pAccountRefNum) {
            this.mAccountRefNum = pAccountRefNum;
        }

        public void setOp(String pOp) {
            this.mOp = pOp;
        }

        public String getOp() {
            return mOp;
        }

        public String getSiteRefNum() {
            return mSiteRefNum;
        }

        public void setSiteRefNum(String pSiteRefNum) {
            this.mSiteRefNum = pSiteRefNum;
        }

        public void setDataLineNumber(int pDataLineNumber) {
            this.mDataLineNumber = pDataLineNumber;
        }

        public int getDataLineNumber() {
            return mDataLineNumber;
        }
    }

    public class IPollockCustomOrderGuideDetail {

        private Integer mSequenceNumber;
        private String mCategoryName;
        private String mDistributorSku;
        private String mOp;
        private int mDataLineNumber;

        public Integer getSequenceNumber() {
            return mSequenceNumber;
        }

        public String getCategoryName() {
            return mCategoryName;
        }

        public String getDistributorSku() {
            return mDistributorSku;
        }

        public void setSequenceNumber(Integer pSequenceNumber) {
            this.mSequenceNumber = pSequenceNumber;
        }

        public void setCategoryName(String pCategoryName) {
            this.mCategoryName = pCategoryName;
        }

        public void setDistributorSku(String pDistributorSku) {
            this.mDistributorSku = pDistributorSku;
        }

        public String getOp() {
            return mOp;
        }

        public void setOp(String pOp) {
            this.mOp = pOp;
        }

        public void setDataLineNumber(int pDataLineNumber) {
            this.mDataLineNumber = pDataLineNumber;
        }

        public int getDataLineNumber() {
            return mDataLineNumber;
        }
    }

    public class IPollockOrderGuideServiceObject {

        private Set<String> mDistSkus;
        private Map<String, Set<String>> mSiteRefNumbers;
        private Set<String> mAccountRefNumbers;
        private Map<String, Set<String>> mOrderGuideNames;
        private Map<String, Set<Integer>> mAccountLines;
        private Map<String, Map<String, Set<Integer>>> mSiteLines;
        private Map<String, Set<Integer>> mItemLines;

        public void setDistSkus(Set<String> pDistSkus) {
            this.mDistSkus = pDistSkus;
        }

        public Set<String> getDistSkus() {
            return mDistSkus;
        }

        public void setSiteRefNumbers(Map<String, Set<String>> pSiteRefNumbers) {
            this.mSiteRefNumbers = pSiteRefNumbers;
        }

        public Map<String, Set<String>> getSiteRefNumbers() {
            return mSiteRefNumbers;
        }

        public void setAccountRefNumbers(Set<String> pAccountRefNumbers) {
            this.mAccountRefNumbers = pAccountRefNumbers;
        }

        public Set<String> getAccountRefNumbers() {
            return mAccountRefNumbers;
        }

        public void setOrderGuideNames(Map<String, Set<String>> pOrderGuideNames) {
            this.mOrderGuideNames = pOrderGuideNames;
        }

        public Map<String, Set<String>> getOrderGuideNames() {
            return mOrderGuideNames;
        }

        public void setAccountLines(Map<String, Set<Integer>> pAccountLines) {
            this.mAccountLines = pAccountLines;
        }

        public Map<String, Set<Integer>> getAccountLines() {
            return mAccountLines;
        }

        public void setSiteLines(Map<String, Map<String, Set<Integer>>> pSiteLines) {
            this.mSiteLines = pSiteLines;
        }

        public Map<String, Map<String, Set<Integer>>> getSiteLines() {
            return mSiteLines;
        }

        public void setItemLines(Map<String, Set<Integer>> pItemLines) {
            this.mItemLines = pItemLines;
        }

        public Map<String, Set<Integer>> getItemLines() {
            return mItemLines;
        }
    }

    private class CustomOrderGuideDBImpl implements DBDataImpl {

        Map<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> mAppPollockCustomOrderGuideDBInfo;
        Map<String, Map<Integer, Map<String, Integer>>> mAppAccountData;
        Map<String, Set<Integer>> mAppItemData;

        public CustomOrderGuideDBImpl() {
            this.mAppPollockCustomOrderGuideDBInfo = new HashMap<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>>();
            this.mAppAccountData = new HashMap<String, Map<Integer, Map<String, Integer>>>();
            this.mAppItemData = new HashMap<String, Set<Integer>>();
        }

        public void add(Map<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> pOrderGuideDbInfo) {

            for (Map.Entry<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> e : pOrderGuideDbInfo.entrySet()) {

                Map<String, Map<Integer, List<OrderGuideStructureData>>> thisOrderGuideMap = mAppPollockCustomOrderGuideDBInfo.get(e.getKey());
                if (thisOrderGuideMap == null) {
                    thisOrderGuideMap = new HashMap<String, Map<Integer, List<OrderGuideStructureData>>>();
                    mAppPollockCustomOrderGuideDBInfo.put(e.getKey(), thisOrderGuideMap);
                }
                thisOrderGuideMap.putAll(e.getValue());

            }

        }

        public List<Integer> getOrderGuideIds(String pOrderGuideName) {
            Set<Integer> orderGuideIdSet = new HashSet<Integer>();
            for (Map<String, Map<Integer, List<OrderGuideStructureData>>> v : mAppPollockCustomOrderGuideDBInfo.values()) {
                orderGuideIdSet.addAll(v.get(pOrderGuideName).keySet());
            }
            return new ArrayList<Integer>(orderGuideIdSet);
        }

        public Integer getAccountId(String pAccountRefNumber) {
            return mAppAccountData
                    .get(pAccountRefNumber)
                    .keySet()
                    .iterator()
                    .next();
        }

        public Integer getSiteId(String pAccountRefNumber, String pSiteRefNumber) {
            return mAppAccountData
                    .get(pAccountRefNumber)
                    .values()
                    .iterator()
                    .next()
                    .get(pSiteRefNumber);
        }


        public Map<Integer, Map<String, Map<Integer, List<OrderGuideStructureData>>>> getAppPollockCustomOrderGuideDBInfo() {
            return mAppPollockCustomOrderGuideDBInfo;
        }

        public Map<String, Map<Integer, Map<String, Integer>>> getAppAccountData() {
            return mAppAccountData;
        }

        public Integer getOrderGuideId(Integer pBusEntityId, String pOrderGuideName) {
            Map<String, Map<Integer, List<OrderGuideStructureData>>> m = mAppPollockCustomOrderGuideDBInfo.get(pBusEntityId);
            return m != null && m.get(pOrderGuideName) != null && !m.get(pOrderGuideName).isEmpty()
                    ? m.get(pOrderGuideName).keySet().iterator().next()
                    : null;
        }

        public Set<Integer> getItemId(String pDistributorSku) {
            return mAppItemData.get(pDistributorSku);
        }


        public Map<String, Set<Integer>> getAppItemData() {
            return mAppItemData;
        }

        public void setAppItemData(Map<String, Set<Integer>> pAppItemData) {
            this.mAppItemData = pAppItemData;
        }

        public void setAppAccountData(Map<String, Map<Integer, Map<String, Integer>>> pAppAccountData) {
            this.mAppAccountData = pAppAccountData;
        }
    }

    public class Line<T> implements Serializable {

        private int mLine;
        private T mItem;
        private List<String> mErrors;

        public Line(int pLine, T pItem, List<String> pErrors) {
            this.mLine = pLine;
            this.mItem = pItem;
            this.mErrors = pErrors;
        }

        public T getItem() {
            return mItem;
        }

        public void setItem(T pItem) {
            this.mItem = pItem;
        }

        public int getLine() {
            return mLine;
        }

        public void setLine(int pLine) {
            this.mLine = pLine;
        }

        public List<String> getErrors() {
            return mErrors;
        }

        public void setErrors(List<String> pErrors) {
            this.mErrors = pErrors;
        }
    }

    public class BadLine extends Line<InboundPollockOrderGuideLoaderView> {
        public BadLine(int pLine, InboundPollockOrderGuideLoaderView pItem, List<String> pErrors) {
            super(pLine, pItem, pErrors);
        }
    }

    public class DBActivity extends HashMap<DBTable, Map<DBAction, Integer>> {

        public DBActivity() {

            super(DBTable.values().length);

            for (DBTable t : DBTable.values()) {
                Map<DBAction, Integer> m = new HashMap<DBAction, Integer>(DBAction.values().length);
                for (DBAction a : DBAction.values()) {
                    m.put(a, 0);
                }
                put(t, m);
            }
        }

        public void addActivity(DBTable pTable, DBAction pAction, int pCount) {
            this.get(pTable).put(pAction, (this.get(pTable).get(pAction) + pCount));
        }
    }

}
