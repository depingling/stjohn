package com.cleanwise.service.apps.dataexchange.pollock;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderGuideData;
import org.apache.log4j.Logger;


import java.io.Serializable;
import java.sql.Connection;
import java.util.*;


public class IPollockOrderGuideLoaderImplVer2 extends IPollockOrderGuideLoaderImplVer1 implements Serializable {

    private static final Logger log = Logger.getLogger(IPollockOrderGuideLoaderImplVer2.class);

    private static final String POLLOCK_ORDER_GUIDE_LOADER = "IPollockOrderGuideLoaderVer2";

    protected void dbsync(Connection pCon, int pStoreId, List<IPollockCustomOrderGuide> pInboundGuides, DBDataImpl pDBDataImpl) throws Exception {

        log.info("dbsync()=> BEGIN");

        CustomOrderGuideDBImpl guideDB = (CustomOrderGuideDBImpl) pDBDataImpl;

        for (IPollockCustomOrderGuide iOrderGuide : pInboundGuides) {

            printOrderGuideInfo("dbsync()", iOrderGuide);

           Set<Integer> orderGuideIdSet = new HashSet<Integer>(PollockLoaderAssist.getOrderGuideIds(pCon, pStoreId, iOrderGuide.getOrderGuideName()));

            if (OP_TYPE.DELETE.equals(iOrderGuide.getOp())) {

                deleteOrderGuides(new ArrayList<Integer>(orderGuideIdSet));
                orderGuideIdSet.clear();
                Map<Integer, Map<String, Map<Integer, OrderGuideData>>> orderGuidesMap = guideDB.getAppPollockCustomOrderGuideDBInfo();
                for (Map<String, Map<Integer, OrderGuideData>> orderGuides : orderGuidesMap.values()) {
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
                            orderGuideIdSet.remove(orderGuideId);
                        }

                    } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuide.getOp())) {

                        Map<String, Map<Integer, OrderGuideData>> orderGuides = guideDB.getAppPollockCustomOrderGuideDBInfo().get(busEntityId);
                        if (orderGuides == null) {
                            orderGuides = new HashMap<String, Map<Integer, OrderGuideData>>();
                            guideDB.getAppPollockCustomOrderGuideDBInfo().put(busEntityId, orderGuides);
                        }

                        Map<Integer, OrderGuideData> orderGuideMap = orderGuides.get(iOrderGuide.getOrderGuideName());
                        if (orderGuideMap == null) {
                            OrderGuideData orderGuide = createOrderGuide(
                                    pCon,
                                    getTemplateId(orderGuideIdSet),
                                    iOrderGuide.getOrderGuideName(),
                                    busEntityId,
                                    POLLOCK_ORDER_GUIDE_LOADER);
                            orderGuideMap = new HashMap<Integer, OrderGuideData>();
                            orderGuideMap.put(orderGuide.getOrderGuideId(), orderGuide);
                            orderGuides.put(iOrderGuide.getOrderGuideName(), orderGuideMap);
                            orderGuideIdSet.add(orderGuide.getOrderGuideId());
                        }
                    }

                }


                UpdateOgiRequest updateOgiRequest = new UpdateOgiRequest();
                for (IPollockCustomOrderGuideDetail iOrderGuideDetail : iOrderGuide.getOrderGuideDetailList()) {

                	Set<Integer> itemIds = guideDB.getItemId(iOrderGuideDetail.getDistributorSku());
                    //int itemId = guideDB.getItemId(iOrderGuideDetail.getDistributorSku());
                	Iterator it = itemIds.iterator();
                	while(it.hasNext()){
                		
                		int itemId = ((Integer)it.next()).intValue();
                		if (OP_TYPE.DELETE.equals(iOrderGuideDetail.getOp())) {

                            updateOgiRequest.getToAddOrChange().remove(itemId);
                            updateOgiRequest.getToDelete().put(itemId, iOrderGuideDetail);


                        } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuideDetail.getOp())) {

                            updateOgiRequest.getToAddOrChange().put(itemId, iOrderGuideDetail);
                            updateOgiRequest.getToDelete().remove(itemId);

                        }
                	}
                	
                    
                }

                dbSynchOgi(pCon, new ArrayList<Integer>(orderGuideIdSet),  pStoreId, iOrderGuide.getOrderGuideName(), updateOgiRequest);

            }
        }

        log.info("dbsync()=> END.");

    }

    private int getTemplateId(Set<Integer> pOrderGuideIdSet) {
        ArrayList<Integer> list = new ArrayList<Integer>(pOrderGuideIdSet);
        Collections.sort(list);
        if(list.size()>0) {
            return Utility.intNN(list.get(0));
        }
        return 0;
    }

    protected OrderGuideData createOrderGuide(Connection pCon, int pTemplateId, String pOrderGuideName, Integer pBusEntityId, String pUser) throws Exception {
        OrderGuideData orderGuide = PollockLoaderAssist.createOrderGuideVer2(pOrderGuideName, pBusEntityId, pUser);
        int n = PollockLoaderAssist.cloneOrderGuideItems(pCon, pTemplateId, orderGuide.getOrderGuideId(), pUser);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE, DBAction.CREATE, orderGuide.getOrderGuideId() > 0 ? 1 : 0);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.CREATE, n);
        return orderGuide;
    }

    protected void dbSynchOgi(Connection pCon, List pOrderGuideIds, Integer pStoreId, String pOrderGuideName, UpdateOgiRequest pUpdateRequest) throws Exception {

        log.info("dbSynchOgi()=> BEGIN");

        for (List pack : Utility.createPackages(pOrderGuideIds, 1000)) {

            log.info("dbSynchOgi()=> pStoreId: " + pStoreId + ", pOrderGuideName:  " + pOrderGuideName + ". ID(s): " + pack);

            ///1
            int n1 = PollockLoaderAssist.deleteOrderGuideItems(pCon, pack, new ArrayList<Integer>(pUpdateRequest.getToDelete().keySet()));
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.DELETE, n1);

            //2
            int[] n2 = PollockLoaderAssist.createAndUpdateOrderGuideItems(
                    pCon,
                    pOrderGuideIds,
                    pUpdateRequest.getToAddOrChange(),
                    POLLOCK_ORDER_GUIDE_LOADER
            );
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.CREATE, n2[1]);
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.UPDATE, n2[0]);

        }

        log.info("dbSynchOgi()=> END.");

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

            Map<Integer, Map<String, Map<Integer, OrderGuideData>>> orderGuideDbInfo =
                    PollockLoaderAssist.defineOrderGuidesVer2(
                            pCon,
                            busEntityIds,
                            orderGuideNames
                    );

            ogDBImpl.add(orderGuideDbInfo);

        }

        log.info("getCustomOrderGuideDBImpl()=> END.");

        return ogDBImpl;

    }

    protected class CustomOrderGuideDBImpl implements DBDataImpl {

        Map<Integer, Map<String, Map<Integer, OrderGuideData>>> mAppPollockCustomOrderGuideDBInfo;
        Map<String, Map<Integer, Map<String, Integer>>> mAppAccountData;
        Map<String, Set<Integer>> mAppItemData;

        public CustomOrderGuideDBImpl() {
            this.mAppPollockCustomOrderGuideDBInfo = new HashMap<Integer, Map<String, Map<Integer, OrderGuideData>>>();
            this.mAppAccountData = new HashMap<String, Map<Integer, Map<String, Integer>>>();
            this.mAppItemData = new HashMap<String, Set<Integer>>();
        }

        public void add(Map<Integer, Map<String, Map<Integer, OrderGuideData>>> pOrderGuideDbInfo) {

            for (Map.Entry<Integer, Map<String, Map<Integer, OrderGuideData>>> e : pOrderGuideDbInfo.entrySet()) {

                Map<String, Map<Integer, OrderGuideData>> thisOrderGuideMap = mAppPollockCustomOrderGuideDBInfo.get(e.getKey());
                if (thisOrderGuideMap == null) {
                    thisOrderGuideMap = new HashMap<String, Map<Integer, OrderGuideData>>();
                    mAppPollockCustomOrderGuideDBInfo.put(e.getKey(), thisOrderGuideMap);
                }
                thisOrderGuideMap.putAll(e.getValue());

            }

        }

        public List<Integer> getOrderGuideIds(String pOrderGuideName) {
            Set<Integer> orderGuideIdSet = new HashSet<Integer>();
            for (Map<String, Map<Integer, OrderGuideData>> v : mAppPollockCustomOrderGuideDBInfo.values()) {
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


        public Map<Integer, Map<String, Map<Integer, OrderGuideData>>> getAppPollockCustomOrderGuideDBInfo() {
            return mAppPollockCustomOrderGuideDBInfo;
        }

        public Map<String, Map<Integer, Map<String, Integer>>> getAppAccountData() {
            return mAppAccountData;
        }

        public Integer getOrderGuideId(Integer pBusEntityId, String pOrderGuideName) {
            Map<String, Map<Integer, OrderGuideData>> m = mAppPollockCustomOrderGuideDBInfo.get(pBusEntityId);
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

    protected class UpdateOgiRequest implements Serializable {

        Map<Integer, IPollockCustomOrderGuideDetail> mToAddOrChange = new HashMap<Integer, IPollockCustomOrderGuideDetail>();
        Map<Integer, IPollockCustomOrderGuideDetail> mToDelete = new HashMap<Integer, IPollockCustomOrderGuideDetail>();

        public Map<Integer, IPollockCustomOrderGuideDetail> getToAddOrChange() {
            return mToAddOrChange;
        }

        public void setToAddOrChange(Map<Integer, IPollockCustomOrderGuideDetail> pToAddOrChange) {
            this.mToAddOrChange = pToAddOrChange;
        }

        public Map<Integer, IPollockCustomOrderGuideDetail> getToDelete() {
            return mToDelete;
        }

        public void setToDelete(Map<Integer, IPollockCustomOrderGuideDetail> pToDelete) {
            this.mToDelete = pToDelete;
        }
    }
}



