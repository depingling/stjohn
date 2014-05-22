package com.cleanwise.service.api.pipeline;

/**
 * Title:        PendingOrderNotification
 * Description:  Generates notification emails for orders with pending status.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 *
 * @author Alexey Lukovnikov
 */

/*import java.sql.Connection;
import java.text.SimpleDateFormat;*/

import org.apache.log4j.Logger;


/*import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.EventDataAccess;
import com.cleanwise.service.api.dao.EventEmailDataAccess;
import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailData;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAddressDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.view.i18n.ClwI18nUtil;*/

public class PendingOrderNotification extends PendingOrderNotificationResistant implements OrderPipeline {

  /*   private static final Logger log = Logger.getLogger(PendingOrderNotification.class);

  *//**
     * @see com.cleanwise.service.api.pipeline.OrderPipeline#process(com.cleanwise.service.api.pipeline.OrderPipelineBaton,
     *      com.cleanwise.service.api.pipeline.OrderPipelineActor,
     *      java.sql.Connection, com.cleanwise.service.api.APIAccess)
     *//*
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor, Connection pCon, APIAccess pFactory)
            throws PipelineException {
        try {
        	checkOrderStatusAndCreateNotificationEmail(pCon, pBaton, pFactory);
        	pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            return pBaton;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }
    
    *//**
     * Creating notification email for pending orders.
     *
     * @param pCon
     *            Active database connection.
     * @param pOrderData
     *            Processed order.
     * @param pUser
     *            System user for logging.
     *//*
    private void checkOrderStatusAndCreateNotificationEmail(Connection pCon,
    		OrderPipelineBaton pBaton, APIAccess pFactory ) throws Exception {
    	
    	if (pBaton.getOrderData() == null
                || (RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(pBaton.getOrderStatus()) == false && RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(pBaton.getOrderStatus()) == false)) {
              return;
          }
    	
    	EventData eventData = EventData.createValue();
        EventEmailData eventEmailData = EventEmailData.createValue();
        int storeId = pBaton.getOrderData().getStoreId();
    	
        DBCriteria cr = new DBCriteria();
        cr.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
        cr.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.PENDING_ORDER_NOTIFICATION);
        PropertyDataVector list = PropertyDataAccess.select(pCon, cr);
        if (list.size() == 0) {
            return;
        }
        PropertyData toAddressProp = (PropertyData) list.get(0);
        String toAddress = toAddressProp.getValue();
        if(!Utility.isSet(toAddress)){
        	log.info("Store specific email could not be found for "+RefCodeNames.PROPERTY_TYPE_CD.PENDING_ORDER_NOTIFICATION +
        			" looking for default email");
        	toAddress = pFactory.getEmailClientAPI().getDefaultEmailAddress();
        }
        if(!Utility.isSet(toAddress)){
        	log.info("No to address could be found, not sending email.");
        	return;
        }
        eventEmailData.setEventId(eventData.getEventId());
        eventEmailData.setEmailStatusCd(RefCodeNames.PROCESS_STATUS_CD.READY);
        eventEmailData.setToAddress(toAddress);
        eventEmailData.setFromAddress("pendingrevieworders@espendwise.com");
        
        BusEntityData storeD = BusEntityDataAccess.select(pCon, storeId);
        String storeName = storeD.getShortDesc();       
        storeName = storeName+" Order "+pBaton.getOrderData().getOrderNum()+" in Pending Review Status";
        eventEmailData.setSubject(storeName);
        //eventEmailData.setSubject("Subject:  NetSupply Order in Pending Review Status ");
        
        String outboundPO = pBaton.getOrderData().getRequestPoNum();
        String siteAddress = pBaton.getShipToData().getAddress1();
        String siteCity = pBaton.getShipToData().getCity();
        String siteState = pBaton.getShipToData().getStateProvinceCd();
        String siteZip = pBaton.getShipToData().getPostalCode();
        String accountName ="";
        String siteName = "";
        
        cr = new DBCriteria();
        cr.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, pBaton.getOrderData().getAccountId()
                + "," +  pBaton.getOrderData().getSiteId());
        BusEntityDataVector busEntities = BusEntityDataAccess.select(pCon, cr);
        for (int i = 0; busEntities != null && i < busEntities.size(); i++) {
            BusEntityData busEntity = (BusEntityData) busEntities.get(i);
            if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(busEntity.getBusEntityTypeCd()) == true) {
                accountName = busEntity.getShortDesc();
            } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(busEntity.getBusEntityTypeCd()) == true) {
                siteName = busEntity.getShortDesc();
            }
        }
        
        StringBuilder text = new StringBuilder();
        text.append("Account Name:" + accountName + "\n");
        text.append("Outbound PO #:" + outboundPO + "\n");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        text.append("Order Date:"
                + sdf.format(pBaton.getOrderData().getOriginalOrderDate()) + "\n");
        text.append("Web Order #:" + pBaton.getOrderData().getOrderNum() + "\n");
        text.append("Site Name:" + siteName + "\n");
        text.append("Site Address 1:" + siteAddress + "\n");
        text.append("Site City:" + siteCity + "\n");
        text.append("Site State:" + siteState + "\n");
        text.append("Site Zip:" + siteZip + "\n");
        text.append("Placed By:" + pBaton.getOrderData().getAddBy() + "\n");
        text.append("Order Status:" + pBaton.getOrderStatus() + "\n");
        
        OrderPropertyDataVector notes = pBaton.getErrorsAsProperties();
        if (notes != null && notes.size() > 0) {
            text.append("Order's Notes:\n");
            for (int i = 0; i < notes.size(); i++) {
                OrderPropertyData note = (OrderPropertyData) notes.get(i);
                if(note.getOrderPropertyTypeCd().equalsIgnoreCase(
                		RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE)){
                	
                	if((note.getMessageKey()).equalsIgnoreCase("pipeline.message.stopOrder")){
                		String msg = "Item held for review";
                		if(note.getArg0()!=null && note.getArg0().trim().length()>0){
                			msg = msg + "\n" + note.getArg0();
                		}
                		text.append(msg + "\n");
                	}else{
                		text.append(note.getValue() + "\n");
                	}
                }
            }
        }
        
        eventEmailData.setText(text.toString());
        eventEmailData.setModBy(pBaton.getUserName());
        eventEmailData.setAddBy(pBaton.getUserName());
        eventData.setAddBy(pBaton.getUserName());
        eventData.setModBy(pBaton.getUserName());
        eventData.setAttempt(1);
        eventData.setType(Event.TYPE_EMAIL);
        eventData.setStatus(Event.STATUS_READY);
       
        if(!pBaton.isPendingOrderEmailSent() && pBaton.getOrderData().getOrderNum()!=null){        	
        	EventDataAccess.insert(pCon, eventData);
        	
        	eventEmailData.setEventId(eventData.getEventId());
        	EventEmailDataAccess.insert(pCon, eventEmailData);
        	
        	pBaton.setPendingOrderEmailSent(true);
        }
        
    }*/

}
