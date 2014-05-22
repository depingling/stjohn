package com.cleanwise.service.apps.dataexchange.pollock;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InboundPollockOrderGuideLoaderView;
import com.cleanwise.service.api.value.OrderGuideData;
import org.apache.log4j.Logger;


import java.io.Serializable;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.*;


public class IPollockOrderGuideLoaderImplVer4 extends IPollockOrderGuideLoaderImplVer3 implements Serializable {

    private static final Logger log = Logger.getLogger(IPollockOrderGuideLoaderImplVer3.class);

    private static final String VERSIOM = "4";
    private static final String POLLOCK_ORDER_GUIDE_LOADER = "IPollockOrderGuideLoaderVer" + VERSIOM;

    protected List<String> verifyRecord(InboundPollockOrderGuideLoaderView pParsedObject) {

        List<String> errors = new ArrayList<String>();

        if (I_RECORD_TYPE.HEADER.equalsIgnoreCase(pParsedObject.getRecordType())) {

            if (!Utility.isSet(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, EDIT_TYPE));
            } else
            if (!OP_TYPE.ADD_OR_CHANGE.equalsIgnoreCase(pParsedObject.getEditType()) && !OP_TYPE.DELETE.equalsIgnoreCase(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(OP_NOT_SUPPORTED, pParsedObject.getEditType(), I_RECORD_TYPE.HEADER));
            }

            if (!Utility.isSet(pParsedObject.getRecordValue1())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, ORDER_GIDE_NAME));
            }

        } else if (I_RECORD_TYPE.SITE.equalsIgnoreCase(pParsedObject.getRecordType())) {

            if (!Utility.isSet(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, EDIT_TYPE));
            } else
            if (!OP_TYPE.ADD_OR_CHANGE.equalsIgnoreCase(pParsedObject.getEditType()) && !OP_TYPE.DELETE.equalsIgnoreCase(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(OP_NOT_SUPPORTED, pParsedObject.getEditType(), I_RECORD_TYPE.SITE));
            }

            if (!Utility.isSet(pParsedObject.getRecordValue2())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, SITE_REFERENCE_NUMBER));
            }

        } else if (I_RECORD_TYPE.DETAIL.equalsIgnoreCase(pParsedObject.getRecordType())) {

            if (!Utility.isSet(pParsedObject.getEditType())) {
                errors.add(MessageFormat.format(SEG_REQUIRED, EDIT_TYPE));
            } else
            if (!OP_TYPE.ADD_OR_CHANGE.equalsIgnoreCase(pParsedObject.getEditType()) && !OP_TYPE.DELETE.equalsIgnoreCase(pParsedObject.getEditType())) {
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

    protected DBDataImpl getCustomOrderGuideDBImpl(Connection pCon,
                                                   int pStoreId,
                                                   int pDistrId,
                                                   List<IPollockCustomOrderGuide> pInboundGuideList) throws Exception {

        log.info("getCustomOrderGuideDBImpl()=> BEGIN");

        CustomOrderGuideDBImplVer4 ogDBImpl = new CustomOrderGuideDBImplVer4();

        IPollockOrderGuideServiceObject serviceObj = createIPollockOrderGuideServiceObjectVer4(pInboundGuideList);

        Map<String, Set<Integer>> itemDbInfo = PollockLoaderAssist.defineItemId(pCon, pDistrId, serviceObj.getDistSkus());
        Map<String/*DistSku*/, String/*ErrorMessage*/> itemsWithErrors = verifyDBItemInfo(serviceObj.getDistSkus(), itemDbInfo);
        if (!itemsWithErrors.isEmpty()) {
            processDataErrors(itemDbInfo, itemsWithErrors, serviceObj.getItemLines());
        }

        for (Map.Entry<String, Set<Integer>> e : itemDbInfo.entrySet()) {
            ogDBImpl.getAppItemData().put(e.getKey(), e.getValue());

        }

        Set<String> siteRefs = serviceObj.getSiteRefs();
        Map<String, Set<Integer>> siteDBInfo = PollockLoaderAssist.defineSiteId(pCon, pStoreId, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE, siteRefs);
        Map<String/*SiteNumber*/, String/*ErrorMessage*/> sitesWithErrors = verifyDBSiteInfo(siteRefs, siteDBInfo);
        if (!sitesWithErrors.isEmpty()) {
            processDataErrors(siteDBInfo, sitesWithErrors, serviceObj.getSiteLines());
        }

        for (Map.Entry<String, Set<Integer>> siteRefNumberEntry : siteDBInfo.entrySet()) {
            ogDBImpl.getAppSiteData().put(siteRefNumberEntry.getKey(), siteRefNumberEntry.getValue().iterator().next());
        }

        Set<String> orderGuideNames = serviceObj.getOrderGuideNames();

        Set<Integer> busEntityIds = new HashSet<Integer>();
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


        log.info("getCustomOrderGuideDBImpl()=> END.");

        return ogDBImpl;

    }

    protected IPollockOrderGuideServiceObject createIPollockOrderGuideServiceObjectVer4(List<IPollockCustomOrderGuide> pInboundGuideList) {

        IPollockOrderGuideServiceObject serviceobj = new IPollockOrderGuideServiceObject();

        Set<String> distSkus = new HashSet<String>();
        Set<String> siteRefs = new HashSet<String>();
        Set<String> orderGuideNames = new HashSet<String>();
        Map<String, Set<Integer>> siteLines = new HashMap<String, Set<Integer>>();
        Map<String, Set<Integer>> itemLines = new HashMap<String, Set<Integer>>();

        for (IPollockCustomOrderGuide oGuide : pInboundGuideList) {

            for (IPollockCustomOrderGuideAssoc assoc : oGuide.getOrderGuideAssocList()) {

                siteRefs.add(assoc.getSiteRefNum());

                Set<Integer> lines = siteLines.get(assoc.getSiteRefNum());
                if (lines == null) {
                    lines = new HashSet<Integer>();
                    siteLines.put(assoc.getSiteRefNum(), lines);
                }

                lines.add(assoc.getDataLineNumber());

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

            orderGuideNames.add(oGuide.getOrderGuideName());
        }

        serviceobj.setDistSkus(distSkus);
        serviceobj.setSiteRefs(siteRefs);
        serviceobj.setOrderGuideNames(orderGuideNames);
        serviceobj.setSiteLines(siteLines);
        serviceobj.setSiteLines(siteLines);
        serviceobj.setItemLines(itemLines);

        return serviceobj;

    }

    protected Integer getBusEntityId(DBDataImpl pDBDataImpl, IPollockCustomOrderGuideAssoc pAssoc) {
        return ((CustomOrderGuideDBImplVer4) pDBDataImpl).getSiteId(pAssoc.getSiteRefNum());
    }

    protected Map<Integer, Map<String, Map<Integer, OrderGuideData>>> getOrderGuideDBInfo(DBDataImpl pDBDataImpl) {
        return ((CustomOrderGuideDBImplVer4) pDBDataImpl).getAppPollockCustomOrderGuideDBInfo();
    }

    protected Set<Integer> getItemId(DBDataImpl pDBDataImpl, String pDistributorSku) {
        return ((CustomOrderGuideDBImplVer4) pDBDataImpl).getItemId(pDistributorSku);
    }

    protected Integer getOrderGuideId(DBDataImpl pDBDataImpl, Integer pBusEntityId, String pOrderGuideName) {
        return ((CustomOrderGuideDBImplVer4) pDBDataImpl).getOrderGuideId(pBusEntityId, pOrderGuideName);
    }

    protected class CustomOrderGuideDBImplVer4 implements DBDataImpl {

        Map<Integer, Map<String, Map<Integer, OrderGuideData>>> mAppPollockCustomOrderGuideDBInfo;
        Map<String, Integer> mAppSiteData;
        Map<String, Set<Integer>> mAppItemData;

        public CustomOrderGuideDBImplVer4() {
            this.mAppPollockCustomOrderGuideDBInfo = new HashMap<Integer, Map<String, Map<Integer, OrderGuideData>>>();
            this.mAppSiteData = new HashMap<String, Integer>();
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

        public Integer getSiteId(String pSiteRefNumber) {
            return mAppSiteData.get(pSiteRefNumber);
        }

        public Map<Integer, Map<String, Map<Integer, OrderGuideData>>> getAppPollockCustomOrderGuideDBInfo() {
            return mAppPollockCustomOrderGuideDBInfo;
        }

        public Map<String, Integer> getAppSiteData() {
            return mAppSiteData;
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

        public void setAppSiteData(Map<String, Integer> pAppSiteData) {
            this.mAppSiteData = pAppSiteData;
        }
    }

    public class IPollockOrderGuideServiceObject {

        private Set<String> mDistSkus;
        private Set<String> mSiteRefs;
        private Set<String> mOrderGuideNames;
        private Map<String, Set<Integer>> mSiteLines;
        private Map<String, Set<Integer>> mItemLines;

        public void setDistSkus(Set<String> pDistSkus) {
            this.mDistSkus = pDistSkus;
        }

        public Set<String> getDistSkus() {
            return mDistSkus;
        }


        public void setOrderGuideNames(Set<String> pOrderGuideNames) {
            this.mOrderGuideNames = pOrderGuideNames;
        }

        public Set<String> getOrderGuideNames() {
            return mOrderGuideNames;
        }

        public void setSiteLines(Map<String, Set<Integer>> pSiteLines) {
            this.mSiteLines = pSiteLines;
        }

        public Map<String, Set<Integer>> getSiteLines() {
            return mSiteLines;
        }

        public void setItemLines(Map<String, Set<Integer>> pItemLines) {
            this.mItemLines = pItemLines;
        }

        public Map<String, Set<Integer>> getItemLines() {
            return mItemLines;
        }

        public Set<String> getSiteRefs() {
            return mSiteRefs;
        }

        public void setSiteRefs(Set<String> mSiteRefs) {
            this.mSiteRefs = mSiteRefs;
        }
    }

    public String getVersion() {
        return VERSIOM;
    }

    public String getImpl() {
        return POLLOCK_ORDER_GUIDE_LOADER;
    }
}
