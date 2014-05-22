package com.cleanwise.service.apps.dataexchange.pollock;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderGuideData;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.util.*;


public class IPollockOrderGuideLoaderImplVer3 extends IPollockOrderGuideLoaderImplVer2 implements ImplVersion {

    private static final Logger log = Logger.getLogger(IPollockOrderGuideLoaderImplVer3.class);

    private static final String VERSIOM = "3";
    private static final String POLLOCK_ORDER_GUIDE_LOADER = "IPollockOrderGuideLoaderVer" + VERSIOM;

    protected void dbsync(Connection pCon, int pStoreId, List<IPollockCustomOrderGuide> pInboundGuides, DBDataImpl pDBDataImpl) throws Exception {

        log.info("dbsync()=> BEGIN, Impl: "+getImpl());

        for (IPollockCustomOrderGuide iOrderGuide : pInboundGuides) {

            printOrderGuideInfo("dbsync()", iOrderGuide);

            OrderGuideData storeOrderGuide = PollockLoaderAssist.getStoreOrderGuide(pCon, pStoreId, iOrderGuide.getOrderGuideName());
            Set<Integer> orderGuideIdSet = new HashSet<Integer>(PollockLoaderAssist.getOrderGuideIds(pCon, pStoreId, iOrderGuide.getOrderGuideName()));

            if (OP_TYPE.DELETE.equals(iOrderGuide.getOp())) {
                if (storeOrderGuide != null) {
                    deleteOrderGuides(Utility.toIdVector(storeOrderGuide.getOrderGuideId()));
                }
                deleteOrderGuides(new ArrayList<Integer>(orderGuideIdSet));
                orderGuideIdSet.clear();
                Map<Integer, Map<String, Map<Integer, OrderGuideData>>> orderGuidesMap = getOrderGuideDBInfo(pDBDataImpl);
                for (Map<String, Map<Integer, OrderGuideData>> orderGuides : orderGuidesMap.values()) {
                    orderGuides.remove(iOrderGuide.getOrderGuideName());
                }

            } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuide.getOp())) {

                if (storeOrderGuide == null) {
                    storeOrderGuide = createOrderGuide(pCon, 0, iOrderGuide.getOrderGuideName(), pStoreId, getImpl());
                }

                UpdateOgiRequest updateOgiRequest = new UpdateOgiRequest();

                for (IPollockCustomOrderGuideDetail iOrderGuideDetail : iOrderGuide.getOrderGuideDetailList()) {
                    Set<Integer> itemIds = getItemId(pDBDataImpl, iOrderGuideDetail.getDistributorSku());
                    for (Integer itemId : itemIds) {
                        if (OP_TYPE.DELETE.equals(iOrderGuideDetail.getOp())) {
                            updateOgiRequest.getToAddOrChange().remove(itemId);
                            updateOgiRequest.getToDelete().put(itemId, iOrderGuideDetail);
                        } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuideDetail.getOp())) {
                            updateOgiRequest.getToAddOrChange().put(itemId, iOrderGuideDetail);
                            updateOgiRequest.getToDelete().remove(itemId);
                        }
                    }

                }

                dbSynchStoreOgi(pCon, storeOrderGuide, pStoreId, iOrderGuide.getOrderGuideName(), updateOgiRequest);

                for (IPollockCustomOrderGuideAssoc assoc : iOrderGuide.getOrderGuideAssocList()) {

                    Integer busEntityId = getBusEntityId(pDBDataImpl , assoc);


                    if (OP_TYPE.DELETE.equals(assoc.getOp())) {

                        Integer orderGuideId = getOrderGuideId(pDBDataImpl, busEntityId, iOrderGuide.getOrderGuideName());
                        if (orderGuideId != null) {
                            deleteOrderGuides(Utility.toIdVector(orderGuideId));
                             getOrderGuideDBInfo(pDBDataImpl).get(busEntityId).remove(iOrderGuide.getOrderGuideName());
                            orderGuideIdSet.remove(orderGuideId);
                        }

                    } else if (OP_TYPE.ADD_OR_CHANGE.equals(iOrderGuide.getOp())) {

                        Map<String, Map<Integer, OrderGuideData>> orderGuides =  getOrderGuideDBInfo(pDBDataImpl).get(busEntityId);
                        if (orderGuides == null) {
                            orderGuides = new HashMap<String, Map<Integer, OrderGuideData>>();
                            getOrderGuideDBInfo(pDBDataImpl).put(busEntityId, orderGuides);
                        }

                        Map<Integer, OrderGuideData> orderGuideMap = orderGuides.get(iOrderGuide.getOrderGuideName());
                        if (orderGuideMap == null) {
                            OrderGuideData orderGuide = createOrderGuide(
                                    pCon,
                                    storeOrderGuide.getOrderGuideId(),
                                    iOrderGuide.getOrderGuideName(),
                                    busEntityId,
                                    getImpl());
                            orderGuideMap = new HashMap<Integer, OrderGuideData>();
                            orderGuideMap.put(orderGuide.getOrderGuideId(), orderGuide);
                            orderGuides.put(iOrderGuide.getOrderGuideName(), orderGuideMap);
                            orderGuideIdSet.add(orderGuide.getOrderGuideId());
                        }
                    }

                }

                dbSynchCustOgi(
                        pCon,
                        storeOrderGuide.getOrderGuideId(),
                        pStoreId,
                        iOrderGuide.getOrderGuideName(),
                        orderGuideIdSet,
                        getImpl()
                );
            }
            log.info("dbsync()=> GO NEXT...");
        }

        log.info("dbsync()=> END, Impl: "+getImpl());

    }

    protected void dbSynchStoreOgi(Connection pCon, OrderGuideData pStoreOrderGuide, int pStoreId, String pOrderGuideName, UpdateOgiRequest pUpdateRequest) throws Exception {

        log.info("dbSynchStoreOgi()=> BEGIN");

        log.info("dbSynchStoreOgi()=> pStoreId: " + pStoreId + ", pOrderGuideName:  " + pOrderGuideName + ". ID: " + pStoreOrderGuide.getOrderGuideId());
        ///1
        int n1 = PollockLoaderAssist.deleteOrderGuideItems(pCon, Utility.toIdVector(pStoreOrderGuide.getOrderGuideId()), new ArrayList<Integer>(pUpdateRequest.getToDelete().keySet()));
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.DELETE, n1);

        //2
        int[] n2 = PollockLoaderAssist.createAndUpdateOrderGuideItems(
                pCon,
                Utility.toIdVector(pStoreOrderGuide.getOrderGuideId()),
                pUpdateRequest.getToAddOrChange(),
                getImpl()
        );

        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.CREATE, n2[1]);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.UPDATE, n2[0]);

        if (n1 > 0 || n2[1] > 0 || n2[0] > 0) {
            PollockLoaderAssist.updateOrderGuide(pStoreOrderGuide, getImpl());
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE, DBAction.UPDATE, pStoreOrderGuide.getOrderGuideId() > 0 ? 1 : 0);
        }

        log.info("dbSynchStoreOgi()=> END.");
    }

    protected void dbSynchCustOgi(Connection pCon, Integer pStoreOrderGuide, Integer pStoreId, String pOrderGuideName, Set<Integer> pCustOrderGuideIds, String pUser) throws Exception {

        log.info("dbSynchCustOgi()=> BEGIN");

        log.info("dbSynchCustOgi()=> pStoreId: " + pStoreId + ", pOrderGuideName:  " + pOrderGuideName + ". Store OG.ID: " + pStoreOrderGuide + ", CustOG.Size: " + pCustOrderGuideIds.size());
        ///1
        int i = 0;
        List<List> packs = Utility.createPackages(new ArrayList<Integer>(pCustOrderGuideIds), 1000);
        int packsSize = packs.size();
        for (List pack : packs) {

            log.info("dbSynchCustOgi()=> pack " + (++i) + " of " + packsSize + ", pack.size " + pack.size());
            int n1 = PollockLoaderAssist.deleteOrderGuideItems(pCon, pStoreOrderGuide, new ArrayList<Integer>(pCustOrderGuideIds));
            int un = PollockLoaderAssist.updateOrderGuideItems(pCon, pStoreOrderGuide, new ArrayList<Integer>(pCustOrderGuideIds), pUser);
            int in = PollockLoaderAssist.createOrderGuideItems(pCon, pStoreOrderGuide, new ArrayList<Integer>(pCustOrderGuideIds), pUser);
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.UPDATE, un);
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.CREATE, in);
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.DELETE, n1);

        }
        log.info("dbSynchStoreOgi()=> END.");
    }

    protected Integer getBusEntityId(DBDataImpl pDBDataImpl, IPollockCustomOrderGuideAssoc pAssoc) {
        return Utility.isSet(pAssoc.getSiteRefNum())
                ? ((CustomOrderGuideDBImpl) pDBDataImpl).getSiteId(pAssoc.getAccountRefNum(), pAssoc.getSiteRefNum())
                : ((CustomOrderGuideDBImpl) pDBDataImpl).getAccountId(pAssoc.getAccountRefNum());
    }

    protected Map<Integer, Map<String, Map<Integer, OrderGuideData>>> getOrderGuideDBInfo(DBDataImpl pDBDataImpl) {
        return ((CustomOrderGuideDBImpl) pDBDataImpl).getAppPollockCustomOrderGuideDBInfo();
    }

    protected Set<Integer> getItemId(DBDataImpl pDBDataImpl, String pDistributorSku) {
        return ((CustomOrderGuideDBImpl) pDBDataImpl).getItemId(pDistributorSku);
    }

    protected Integer getOrderGuideId(DBDataImpl pDBDataImpl, Integer pBusEntityId, String pOrderGuideName) {
        return ((CustomOrderGuideDBImpl) pDBDataImpl).getOrderGuideId(pBusEntityId, pOrderGuideName);
    }

    protected OrderGuideData createOrderGuide(Connection pCon, int pStoreOrderGuideId, String pOrderGuideName, Integer pBusEntityId, String pUser) throws Exception {
        log.info("createOrderGuide()=> BEGIN");
        log.info("createOrderGuide()=> pStoreOrderGuideId: " + pStoreOrderGuideId + ", pOrderGuideName: " + pOrderGuideName + ", pBusEntityId: " + pBusEntityId);
        OrderGuideData orderGuide = PollockLoaderAssist.createOrderGuideVer2(pOrderGuideName, pBusEntityId, pUser);
        getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE, DBAction.CREATE, orderGuide.getOrderGuideId() > 0 ? 1 : 0);
        if (pStoreOrderGuideId > 0) {
            int n = PollockLoaderAssist.cloneOrderGuideItems(pCon, pStoreOrderGuideId, orderGuide.getOrderGuideId(), pUser);
            getDBActivity().addActivity(DBTable.CLW_ORDER_GUIDE_STRUCTURE, DBAction.CREATE, n);
        }
        log.info("createOrderGuide()=> pStoreOrderGuideId: " + pStoreOrderGuideId + ", pOrderGuideName: " + pOrderGuideName + ", pBusEntityId: " + pBusEntityId);
        log.info("createOrderGuide()=> END.");
        return orderGuide;
    }

    public String getVersion() {
        return VERSIOM;
    }

    public String getImpl() {
        return POLLOCK_ORDER_GUIDE_LOADER;
    }
}
