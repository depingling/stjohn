package com.cleanwise.service.apps.dataexchange;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Asset;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.ProcessOutboundRequestView;
import com.cleanwise.service.api.value.ProcessOutboundResultView;
import com.cleanwise.service.api.value.ProcessOutboundResultViewVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.TradingPartnerAssocData;
import com.cleanwise.service.api.value.TradingPartnerAssocDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPartnerDescView;
import com.cleanwise.service.api.value.TradingProfileConfigData;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;

import org.apache.log4j.Logger;
/**
 * Title:        OutboundSimpleProvider
 * Description:  Contains methods for process of outbound transaction.
 * Purpose:      Operation provider of event-process-system.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         31.08.2007
 * Time:         12:54:15
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class OutboundSimpleProvider implements OutboundProvider {
	static final long serialVersionUID = 6985344516366410602L;
    private static final Logger log = Logger.getLogger(OutboundSimpleProvider.class);
    SimpleDateFormat df_US= new SimpleDateFormat("MM/dd/yyyy HH:mm");

    public OutboundEDIRequestDataVector getOrdersToProcess(String erpNum,String setType) throws Exception {

        IntegrationServices intSvc = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        return intSvc.getEDIOrdersByErpNumAndSetType(erpNum, setType,0);

    }

    public HashMap buildOutboundRequest(OutboundEDIRequestDataVector orders, String erpNum, String setType) throws Exception {

        HashMap processRequestsMap = new HashMap();
        HashMap partnerInfo = new HashMap();
        ProcessOutboundRequestView currProccReq;

        if (orders.size() == 0) {
            return null;
        }

        // sort the data...only implented for 850s currently.
        // this would be different depending on the mappings and what we are
        // trying to do. Right now this is implemented with the idea of it
        // being a distributor store with multiple Accounts that need different
        // group senders. There are other options here, but they have not yet
        // been implemented.
        Collections.sort(orders, OutboundTranslate.OUTBOUND_PROCESSING_COMPARE);


        HashMap overides = new HashMap();
        String storeType = getStoreType(orders);

        TradingPartnerData partner;
        TradingPartnerDescView partnerDescView;
        for (int i = 0; i < orders.size(); i++) {
            // Now looping through trasnsactions...build temp list from this
            // instead of just calling builder
            OutboundEDIRequestData outEdi = (OutboundEDIRequestData) orders.get(i);
            int id = outEdi.getIncomingTradingProfileId();

            if (!partnerInfo.containsKey((new Integer(id)))) {
                partnerDescView = getOutboundTradingPartnerDescView(erpNum, id, setType);
                buildOverideHashMap(overides, partnerDescView.getTradingProfileData().getTradingPartnerId());
                partnerInfo.put(new Integer(id), partnerDescView);
            } else {
                partnerDescView = (TradingPartnerDescView) partnerInfo.get(new Integer(id));
            }

            if (partnerDescView == null
                    || partnerDescView.getTradingPartnerData() == null
                    || partnerDescView.getTradingProfileData() == null
                    || partnerDescView.getTradingProfileConfigData() == null) {
                continue;
            }

            partner = partnerDescView.getTradingPartnerData();

            //Check TRADING_TYPE for partner if it is not EDI than continue
            if (partner != null) {
                if (RefCodeNames.TRADING_TYPE_CD.FAX.equals(partner.getTradingTypeCd())) {
                    continue;
                }
            }

            // Next check for overides for this bus entity. If there is an
            // overide for the header (aka group sender) then
            // we will need to recreate the envelope
            TradingPartnerAssocData profileOveride = null;
            if (partner != null
                    && partner.getTradingPartnerTypeCd().equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR)
                    && RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)) {
                // using arbitrary hierarchy for overides, if multiple bus
                // entities have overides the behavior is
                // distributor then account then store
                profileOveride = getProfileOveride(overides, outEdi);
            }

            currProccReq=constructProcessRequest(partnerDescView, profileOveride, erpNum);

            ProcessOutboundRequestView existingProcReq = (ProcessOutboundRequestView) processRequestsMap.get(currProccReq.getKey());

            if (existingProcReq == null) {
                existingProcReq = currProccReq;
                processRequestsMap.put(currProccReq.getKey(),existingProcReq);
            }

            if (existingProcReq.getTransactionsToProcess() == null) {
                existingProcReq.setTransactionsToProcess(new OutboundEDIRequestDataVector());
                //init the vector if necessary
            }

            existingProcReq.getTransactionsToProcess().add(outEdi);
        }
        return processRequestsMap;
    }

    private ProcessOutboundRequestView constructProcessRequest(TradingPartnerDescView partnerDescView,
                                                               TradingPartnerAssocData profileOveride,
                                                               String erpNum) {
        TradingProfileConfigData profileConfigD = partnerDescView.getTradingProfileConfigData();
        TradingProfileData profileD = partnerDescView.getTradingProfileData();
        TradingPartnerData partnerD = partnerDescView.getTradingPartnerData();
        int incommingProfileId = partnerDescView.getTradingProfileConfigData().getIncomingTradingProfileId();
        //+"@"+Integer.toString(incommingProfileId) intentionally left off as this may be 0 and 1 in ceratin cases.
        //the pProfileConfigD.getTradingProfileConfigId() should account for this case already as they are tied to
        //a given trading profile, and the EJB logic should handle getting this when there is not incomming trading
        //profile.  If included USPS will generate 2 files instead of one, one with the web orders (incomming trading profile id 0)
        //and 1 for the EDI (incomming trading profile id 1)
        String key = Integer.toString(profileConfigD.getTradingProfileConfigId()) +
                "@" + Integer.toString(profileD.getTradingProfileId()) + "@"
                + (profileOveride != null ? profileOveride.getGroupSenderOverride() : "");
        return  new ProcessOutboundRequestView(profileConfigD,profileD,partnerD,profileOveride,incommingProfileId,null,erpNum,partnerDescView,key);
    }

    private String getStoreType(OutboundEDIRequestDataVector orders) throws Exception {

        String storeType=null;
        for (int i = 0; i < orders.size(); i++) {
            OutboundEDIRequestData outEdi = (OutboundEDIRequestData) orders.get(i);
            if (storeType == null) {
                storeType = outEdi.getStoreType();
            } else {
                if (!storeType.equals(outEdi.getStoreType())) {
                    throw new Exception("Error multiple store types Outbound Translate");
                }
            }
        }
        return storeType;
    }

    private TradingPartnerAssocData getProfileOveride(HashMap overides,OutboundEDIRequestData outEdi) {

        TradingPartnerAssocData profileOveride = (TradingPartnerAssocData) overides.get(new Integer(outEdi.getDistributorIdIfPresent()));

        if (profileOveride == null) {
            profileOveride = (TradingPartnerAssocData) overides.get(new Integer(outEdi.getAccountIdIfPresent()));
            if (profileOveride == null) {
                profileOveride = (TradingPartnerAssocData) overides.get(new Integer(outEdi.getStoreIdIfPresent()));
            }
        }
        return profileOveride;
    }
    /**
     * Builds the overides hashmap which contrains group sender and potentially other overidden trading profile data.
     * @param overides the hashmap to add data to
     * @param pTradingPartner the trading partner id that is currently being translated
     * @throws Exception if an errors
     */
    private void buildOverideHashMap(HashMap overides, int pTradingPartner) throws Exception {
        TradingPartner partnerSvc = APIAccess.getAPIAccess().getTradingPartnerAPI();
        TradingPartnerAssocDataVector assocList = partnerSvc.getTradingPartnerAssocDataVectorForPartnerAll(pTradingPartner); // get em for this tpid
        Iterator it = assocList.iterator();
        while (it.hasNext()) {
            TradingPartnerAssocData tpa = (TradingPartnerAssocData) it.next();
            if (Utility.isSet(tpa.getGroupSenderOverride())) {
                overides.put(new Integer(tpa.getBusEntityId()), tpa);
            }
        }
    }

    /*
     * Get a trading profile config data from Trading_Profile_Config table
     * by matching trading profile id
     */
    public TradingPartnerDescView getOutboundTradingPartnerDescView(
            String erpNum,int incommingProfileId, String setType) throws
            Exception {
        IntegrationServices intSvc = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        return intSvc.getOutboundTradingProfileConfig(erpNum,incommingProfileId,setType,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR,"OUT");
    }

    /*
     * Get a trading profile record from Trading_Profile table by match group sender id
     * with incoming document group receiver id and group receiver id with incomming
     * group sender id.
     */
    public TradingProfileData getTradingProfile(int profileId) throws   Exception {

        TradingPartner partnerSvc = APIAccess.getAPIAccess().getTradingPartnerAPI();
        TradingProfileData lProfile = partnerSvc.getTradingProfile(profileId);
        return (lProfile);

    }

    /*
     * Get a trading profile record from Trading_Profile table by match group sender id
     * with incoming document group receiver id and group receiver id with incomming
     * group sender id.
     */
    public TradingPartnerData getTradingPartner(int tradingPartnerId)throws   Exception {
        TradingPartner partnerSvc = APIAccess.getAPIAccess().getTradingPartnerAPI();
        return partnerSvc.getTradingPartner(tradingPartnerId);
    }

    public ProcessOutboundResultView outboundRequestProcess(ProcessOutboundRequestView procReq) throws Exception {

        OutboundEDIRequestDataVector transactionsToProcess = procReq.getTransactionsToProcess();
        TradingPartnerAssocData profileOveride = procReq.getProfileOveride();
        TradingProfileData profileD = procReq.getProfileD();
        TradingProfileConfigData profileConfigD = procReq.getProfileConfigD();

        if (profileOveride != null) {
            // deal with overide information
            profileD.setGroupSender(profileOveride.getGroupSenderOverride());
        }

        Class c = Class.forName(profileConfigD.getClassname());
        OutboundEventTransaction builder = (OutboundEventTransaction) c.newInstance();

        builder.setOutboundTransactionsToProcess(transactionsToProcess);
        builder.setTradingPartnerDescView(procReq.getTradingPartnerDescView());
        builder.setDistErpNum(procReq.getDistErpNum());
        builder.build();
        return builder.getProcessOutboundResultData();
    }

    public ProcessOutboundResultViewVector outboundRequestProcess(HashMap outboundRequests) throws Exception {

        //now loop through the builder requests and process them
        ProcessOutboundResultViewVector result =new ProcessOutboundResultViewVector();
        Iterator it = outboundRequests.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            ProcessOutboundRequestView processReq = (ProcessOutboundRequestView) outboundRequests.get(key);
            result.add(outboundRequestProcess(processReq));
        }
        return result;
    }

    public void processIntegrationRequests(IntegrationRequestsVector reqs, TradingPartnerData partner, String erpNum) throws Exception {

        IntegrationServices intSvc = APIAccess.getAPIAccess().getIntegrationServicesAPI();

        try {
            int tradingPartnerId = 0;
            if (partner != null) {
                tradingPartnerId = partner.getTradingPartnerId();
            }
            //XXX should be removed when we know that the partner really cannot in fact be null
            if (tradingPartnerId == 0) {
                Thread.dumpStack();
                intSvc.processIntegrationRequests(reqs, erpNum, 0);
            } else {
                intSvc.processIntegrationRequests(reqs, erpNum, tradingPartnerId);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
            String to = null;
            try {
                to = intSvc.getIntegrationEmailAddress();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            StringWriter err = new StringWriter();
            PrintWriter err2 = new PrintWriter(err);
            e.printStackTrace(err2);

            String msg;

            msg = "StackTrace:" + err.toString();
            try {

                ApplicationsEmailTool.sendEmail(to, "Translation Failed", msg);

            } catch (Exception emailExc) {
                emailExc.printStackTrace();
            }

        }
    }

    public void outboundResultProcess(ProcessOutboundResultViewVector results, Integer currentEventId) throws Exception {
        Iterator it = results.iterator();
        while(it.hasNext()){
            ProcessOutboundResultView resultView = (ProcessOutboundResultView) it.next();
            putTransferEvent(resultView, currentEventId);
            processIntegrationRequests(resultView);
        }
        log.info(getTranslationReport(results));
    }
    
    public String getTranslationReport(ProcessOutboundResultViewVector results) {
    	String str = "Following outbound event processed\r\n\r\n";
    	str += "Set     PONumber    ErpNum    Result\r\n";
    	
    	Iterator it = results.iterator();
        while(it.hasNext()){
            ProcessOutboundResultView resultView = (ProcessOutboundResultView) it.next();
            String setType = resultView.getTradingPartnerDescView().getTradingProfileConfigData().getSetType();
            if( resultView.getOutboundEDIRequests() == null){
                continue;
            }
            Iterator iter = resultView.getOutboundEDIRequests().iterator();
            while (iter.hasNext()) {
            	OutboundEDIRequestData requestD = (OutboundEDIRequestData)iter.next();       
	            str += Utility.padRight(setType, ' ', 8);
	            str += Utility.padRight(requestD.getPurchaseOrderD().getOutboundPoNum(), ' ', 12);
	            str += Utility.padRight(resultView.getDistErpNum(), ' ', 10);
	            str += resultView.getProcessResult().toString();
	            str += "\r\n";
            }
        }
        return str;
    }

    public void putTransferEvent(ProcessOutboundResultView resultView, Integer currentEventId) throws Exception {
    	int eventId = currentEventId == null ? 0 : currentEventId.intValue();
        TradingPropertyMapDataVector mapDataVector = resultView.getTradingPartnerDescView().getTradingPropertyMapDataVector();
        if(existProperty(mapDataVector, Event.TYPE_EMAIL)){

            String eventStatus = Event.STATUS_READY;

            String address = getPropertyValue(mapDataVector,Event.TYPE_EMAIL);
            if(!Utility.isSet(address)){
                eventStatus = Event.STATUS_PENDING_REVIEW;
            }

            String text    = getBodyTextforEmail(resultView.getTradingPartnerDescView(),
                    resultView.getOutboundEDIRequests());
            String subject = getSubject(resultView.getTradingPartnerDescView());

//            com.cleanwise.service.api.eventsys.EventData eventData
//                    = new com.cleanwise.service.api.eventsys.EventData(0, eventStatus, Event.TYPE_EMAIL, null, null, 1);

            Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
//            eventData = eventEjb.addEventToDB(eventData);
            EventEmailDataView eventEmailData = new EventEmailDataView();
            if (resultView.getOutboundEDIRequests() != null
					&& resultView.getOutboundEDIRequests().size() > 0) {
				OutboundEDIRequestData or = (OutboundEDIRequestData) resultView
						.getOutboundEDIRequests().get(0);
				Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
				/*String from = accountEjb.getContactUsEmail(or
						.getAccountIdIfPresent(), or.getStoreIdIfPresent());*/
				String from = accountEjb.getDefaultEmail(or.getAccountIdIfPresent(), or.getStoreIdIfPresent());
				if (Utility.isSet(from)) {
					eventEmailData.setFromAddress(from);
				}
			}
            eventEmailData.setToAddress(address);
            eventEmailData.setSubject(subject);
            eventEmailData.setText(text);
            File[]   attachments = new File[]{(File) resultView.getProcessResult()};
            FileAttach[] fileAttach;

            fileAttach = (new ApplicationsEmailTool()).fromFilesToAttachs(attachments);

            eventEmailData.setAttachments(fileAttach);
//            eventEmailData.setEventId(eventData.getEventId());
            eventEmailData.setEmailStatusCd(Event.STATUS_READY);
            eventEmailData.setModBy("OutboundSimpleProvider");
            eventEmailData.setAddBy("OutboundSimpleProvider");
//            eventEjb.addEventEmail(eventEmailData);
            EventData eventData = Utility.createEventDataForEmail();
            eventData.setStatus(eventStatus);
            EventEmailView eev = new EventEmailView(eventData, eventEmailData); 
            eventEjb.addEventEmail(eev, "OutboundSimpleProvider", eventId);
        }
    }

    private String getSubject(TradingPartnerDescView tradingPartnerDescView) {
        String setType = tradingPartnerDescView.getTradingProfileConfigData().getSetType();
        String direction = tradingPartnerDescView.getTradingProfileConfigData().getDirection();
        if("OUT".equals(direction)&&"850".equals(setType)){
            return "Purchase Orders("+df_US.format(new Date())+")";
        } else{
            return direction+setType+"("+df_US.format(new Date())+")";
        }
    }

    private String getBodyTextforEmail(TradingPartnerDescView tradingPartnerDescView, OutboundEDIRequestDataVector outboundEDIRequests) {
        try {
            String setType = tradingPartnerDescView.getTradingProfileConfigData().getSetType();
            String direction = tradingPartnerDescView.getTradingProfileConfigData().getDirection();

            if("OUT".equals(direction)&&"850".equals(setType)){
                return getBodyTextforEmailOUT850(outboundEDIRequests);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getBodyTextforEmailOUT850(OutboundEDIRequestDataVector outboundEDIRequests) throws Exception {

        try {

            if(outboundEDIRequests == null){
                 return "";
            }
            Iterator itar = outboundEDIRequests.iterator();
            HashMap posSiteInfo = new HashMap();
            HashMap siteMap     = new HashMap();

            while (itar.hasNext()) {
            	OutboundEDIRequestData requestD = (OutboundEDIRequestData)itar.next();            	
                OrderData order = requestD.getOrderD();
                PurchaseOrderData poData = requestD.getPurchaseOrderD();
                if(poData==null) {
                    return "";
                }

                Integer siteIdInt=new Integer(order.getSiteId());
                if(!siteMap.containsKey(siteIdInt)){
                    String siteName = getSiteName(order.getSiteId());
                    siteMap.put(siteIdInt,siteName);
                }

                if (!posSiteInfo.containsKey(poData.getOutboundPoNum())) {
                    posSiteInfo.put(poData.getOutboundPoNum(), siteMap.get(siteIdInt));
                }
            }
            StringBuffer txt = new StringBuffer();
            if (!posSiteInfo.isEmpty()) {
                itar = posSiteInfo.keySet().iterator();
                while (itar.hasNext()) {
                    Object poKey = itar.next();
                    String siteName = (String) posSiteInfo.get(poKey);
                    txt.append(poKey);
                    txt.append("\t");
                    txt.append(siteName);
                    txt.append("\n");
                }
            }
            return txt.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getSiteName(int siteId) throws Exception {
        Asset assetEjb = APIAccess.getAPIAccess().getAssetAPI();
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        crit.setSearchId(siteId);
        BusEntityDataVector busEntityDV = assetEjb.getBusEntityByCriteria(crit, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
        if(busEntityDV.size()!=1){
            throw new Exception("Can't gets site name.SiteId => "+siteId);
        }
        return  ((BusEntityData)busEntityDV.get(0)).getShortDesc();
    }
    
    public void processIntegrationRequests(ProcessOutboundResultView resultView) throws Exception {
        processIntegrationRequests(resultView.getIntegrationRequests(),
                resultView.getTradingPartnerDescView().getTradingPartnerData(),
                resultView.getDistErpNum());
    }

    private String getPropertyValue(TradingPropertyMapDataVector properties, String key) {
        for (int i = 0; properties != null && i < properties.size(); i++) {
            TradingPropertyMapData item = (TradingPropertyMapData) properties.get(i);
            if (RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(item.getEntityProperty())
                    && RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(item.getTradingPropertyMapCode())
                    && key.equals(item.getPropertyTypeCd())) {
                return item.getHardValue();
            }
        }
        return null;
    }


    private boolean existProperty(TradingPropertyMapDataVector properties, String key) {
        for (int i = 0; properties != null && i < properties.size(); i++) {
            TradingPropertyMapData item = (TradingPropertyMapData) properties.get(i);
            if (RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(item.getEntityProperty())
                    && RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(item.getTradingPropertyMapCode())
                    && key.equals(item.getPropertyTypeCd())) {
                return true;
            }
        }
        return false;
    }
}
