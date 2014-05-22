package com.cleanwise.service.api.pipeline;

import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.dao.EventDataAccess;
import com.cleanwise.service.api.dao.EventEmailDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.blob.storage.util.BlobStorageAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

/**
 * evolution of PendingOrderNotification class 
 */

public class PendingOrderNotificationResistant implements OrderPipeline {

    private static final Logger log = Logger.getLogger(PendingOrderNotificationResistant.class);

    /**
     * @see com.cleanwise.service.api.pipeline.OrderPipeline#process(com.cleanwise.service.api.pipeline.OrderPipelineBaton,
     * com.cleanwise.service.api.pipeline.OrderPipelineActor,
     * java.sql.Connection,com.cleanwise.service.api.APIAccess)
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {
        try {
            log.info("process()=> BEGIN");
            checkOrderStatusAndCreateNotificationEmail(pCon, pBaton, pFactory);
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            log.info("process()=> END.");
            return pBaton;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

    private void checkOrderStatusAndCreateNotificationEmail(Connection pCon, OrderPipelineBaton pBaton, APIAccess pFactory) throws Exception {

        if (pBaton.getOrderData() == null) {
            log.info("no order, not sending email, return");
            return;
        }


        if (couldBeSent(pBaton.getOrderData().getOrderNum(), pBaton.getOrderStatus(), pBaton.isPendingOrderEmailSent())) {

            EventEmailView eventEmail = createNotification(
                    pCon,
                    pFactory,
                    pBaton.getOrderStatus(),
                    pBaton.getOrderData(),
                    pBaton.getShipToData(),
                    pBaton.getErrorsAsProperties(),
                    false,
                    pBaton.getUserName()
            );

            boolean b = eventEmail != null
                    && eventEmail.getEventData() != null
                    && eventEmail.getEventData().getEventId() > 0;

            pBaton.setPendingOrderEmailSent(b);

        }

    }

    public boolean couldBeSent(String pOrderNum, String pStatus, boolean pIsPendingOrderEmailSent) throws Exception {

        log.info("couldBeSent()=> orderNum: " + pOrderNum + ", OrderStatusCD: " + pStatus + ", pBaton.isPendingOrderEmailSent: " + pIsPendingOrderEmailSent);
        boolean b = (RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(pStatus)
                || RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(pStatus))
                && !pIsPendingOrderEmailSent
                && pOrderNum != null;
        log.info("couldBeSent()=> Ret.Flag:" + b);
        return b;
    }

    public EventEmailView createNotification(Connection pCon,
                                             APIAccess pFactory,
                                             String pStatus,
                                             OrderData pOrder,
                                             OrderAddressData pOrderAddress,
                                             OrderPropertyDataVector pNote,
                                             boolean pUseEjb,
                                             String pUserName) throws Exception {


        log.info("createNotification()=> BEGIN");
        String toAddress = getToAddress(pCon, pFactory, pOrder);

        String subject = createEmailSubject(pCon, pOrder);

        String text = createEmailText(
                pCon,
                pStatus,
                pOrder,
                pOrderAddress,
                pNote
        );

        if (Utility.isSet(toAddress)) {

            log.info("createNotification()=> Email will be sent to " + toAddress);

            EmailView ev = EmailView.createValue();
            ev.setToAddress(toAddress);
            ev.setFromAddress("pendingrevieworders@espendwise.com");
            ev.setSubject(subject);
            ev.setText(text);

            EmailNotificationView enotif = EmailNotificationView.createValue();
            enotif.setEmail(ev);
            enotif.setType(EmailNotificationView.TYPE_CD.PENDING_ORDER_NOTIFICATION);

            EventEmailView eventEmail = createEventEmail(enotif);

            log.info("createNotification()=> pUseEjb: " + pUseEjb);

            if (pUseEjb) {
                eventEmail = pFactory.getEventAPI().addEventEmail(eventEmail, pUserName);
            } else {

                eventEmail.getEventData().setAddBy(pUserName);
                eventEmail.getEventData().setModBy(pUserName);
                eventEmail.getEventData().setEventPriority(50);

                EventData eventData = EventDataAccess.insert(pCon, eventEmail.getEventData());

                eventEmail.setEventData(eventData);
               
                eventEmail.getEmailProperty().setEventId(eventEmail.getEventData().getEventId());
                eventEmail.getEmailProperty().setAddBy(eventEmail.getEventData().getAddBy());
                eventEmail.getEmailProperty().setModBy(eventEmail.getEventData().getModBy());
                
                //STJ-6037
                byte[] attachments = eventEmail.getEmailProperty().getAttachments();
                byte[] longText = eventEmail.getEmailProperty().getLongText();
                if (attachments != null || longText != null) {
                    BlobStorageAccess bsa = new BlobStorageAccess();
                    if (attachments != null) {
                        String blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                                BlobStorageAccess.FIELD_ATTACHMENTS,
                                                                                eventData.getEventId());
                        bsa.storeBlob(attachments, blobFileName);
                        if (bsa.getStoredToStorageType() != null &&
                            bsa.getStoredToHostName() != null) { // blob has been stored
                            eventEmail.getEmailProperty().setAttachmentsSystemRef(blobFileName);
                            eventEmail.getEmailProperty().setStorageTypeCd(bsa.getStoredToStorageType());
                            eventEmail.getEmailProperty().setBinaryDataServer(bsa.getStoredToHostName());

                            eventEmail.getEmailProperty().setAttachments(null);
                        } else {
                            log.error("ERROR. Blob will be written into DB. EventID: " + eventData.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_ATTACHMENTS + ")");
                            eventEmail.getEmailProperty().setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                            eventEmail.getEmailProperty().setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                            eventEmail.getEmailProperty().setAttachments(attachments);
                        }
                    }
                    if (longText != null) {
                        String blobFileName = BlobStorageAccess.composeFileName(BlobStorageAccess.TABLE_EVENT_EMAIL,
                                                                                BlobStorageAccess.FIELD_LONG_TEXT,
                                                                                eventData.getEventId());
                        bsa.storeBlob(longText, blobFileName);
                        if (bsa.getStoredToStorageType() != null &&
                            bsa.getStoredToHostName() != null	) { // blob has been stored
                            eventEmail.getEmailProperty().setLongTextSystemRef(blobFileName);
                            eventEmail.getEmailProperty().setStorageTypeCd(bsa.getStoredToStorageType());
                            eventEmail.getEmailProperty().setBinaryDataServer(bsa.getStoredToHostName());

                            eventEmail.getEmailProperty().setLongText(null);
                        } else {
                            log.error("ERROR. Blob will be written into DB. EventID: " + eventData.getEventId() +
                                  " (" + BlobStorageAccess.FIELD_LONG_TEXT + ")");
                            eventEmail.getEmailProperty().setBinaryDataServer(BlobStorageAccess.getCurrentHost());
                            eventEmail.getEmailProperty().setStorageTypeCd(BlobStorageAccess.STORAGE_DB);
                            eventEmail.getEmailProperty().setLongText(longText);
                        }
                    }
                }
                EventEmailData eep = EventEmailDataAccess.insert(pCon, eventEmail.getEmailProperty());

                eventEmail.setEmailProperty(eep);
            }

            log.info("createNotification()=> The email object is created successfully" +
                    ", EventID: " + eventEmail.getEventData().getEventId() +
                    ", EventEmailID: " + eventEmail.getEmailProperty().getEventEmailId());

            log.info("createNotification()=> END.");
            return eventEmail;

        }

        log.info("createNotification()=> END.");
        return null;
    }

    private String createEmailSubject(Connection pCon, OrderData pOrderData) throws SQLException, DataNotFoundException {
        BusEntityData storeD = BusEntityDataAccess.select(pCon, pOrderData.getStoreId());
        String storeName = storeD.getShortDesc();
        storeName = storeName + " Order " + pOrderData.getOrderNum() + " in Pending Review Status";
        return storeName;
    }

    private static String createEmailText(Connection pCon,
                                          String pStatus,
                                          OrderData pOrder,
                                          OrderAddressData pOrderAddress,
                                          OrderPropertyDataVector pNotes) throws Exception {

        String outboundPO = pOrder.getRequestPoNum();
        String siteAddress = pOrderAddress.getAddress1();
        String siteCity = pOrderAddress.getCity();
        String siteState = pOrderAddress.getStateProvinceCd();
        String siteZip = pOrderAddress.getPostalCode();

        String accountName = "";
        String siteName = "";

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, Utility.toIdVector(pOrder.getAccountId(), pOrder.getSiteId()));

        BusEntityDataVector busEntities = BusEntityDataAccess.select(pCon, dbc);
        for (int i = 0; busEntities != null && i < busEntities.size(); i++) {
            BusEntityData busEntity = (BusEntityData) busEntities.get(i);
            if (RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(busEntity.getBusEntityTypeCd())) {
                accountName = busEntity.getShortDesc();
            } else if (RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(busEntity.getBusEntityTypeCd())) {
                siteName = busEntity.getShortDesc();
            }
        }

        StringBuilder text = new StringBuilder();
        text.append("Account Name:").append(accountName).append("\n");
        text.append("Outbound PO #:").append(outboundPO).append("\n");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        text.append("Order Date:").append(sdf.format(pOrder.getOriginalOrderDate())).append("\n");
        text.append("Web Order #:").append(pOrder.getOrderNum()).append("\n");
        text.append("Site Name:").append(siteName).append("\n");
        text.append("Site Address 1:").append(siteAddress).append("\n");
        text.append("Site City:").append(siteCity).append("\n");
        text.append("Site State:").append(siteState).append("\n");
        text.append("Site Zip:").append(siteZip).append("\n");
        text.append("Placed By:").append(pOrder.getAddBy()).append("\n");
        text.append("Order Status:").append(pStatus).append("\n");

        if (pNotes != null && pNotes.size() > 0) {
            text.append("Order's Notes:\n");
            for (Object note1 : pNotes) {
                OrderPropertyData note = (OrderPropertyData) note1;
                if (note.getOrderPropertyTypeCd().equalsIgnoreCase(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE)) {
                    if ((note.getMessageKey()).equalsIgnoreCase("pipeline.message.stopOrder")) {
                        String msg = "Item held for review";
                        if (note.getArg0() != null && note.getArg0().trim().length() > 0) {
                            msg = msg + "\n" + note.getArg0();
                        }
                        text.append(msg).append("\n");
                    } else {
                        text.append(note.getValue()).append("\n");
                    }
                }
            }
        }

        return text.toString();
    }

    private static EventEmailView createEventEmail(EmailNotificationView pEmailNotification) {
        EventData eventData = Utility.createEventDataForEmail();
        EventEmailData eventEmailData = EventEmailData.createValue();
        eventEmailData.setToAddress(pEmailNotification.getEmail().getToAddress());
        eventEmailData.setFromAddress(pEmailNotification.getEmail().getFromAddress());
        eventEmailData.setCcAddress(pEmailNotification.getEmail().getCcAddress());
        eventEmailData.setSubject(pEmailNotification.getEmail().getSubject());
        eventEmailData.setText(pEmailNotification.getEmail().getText());
        eventEmailData.setLongText(pEmailNotification.getEmail().getLongText());
        eventEmailData.setAttachments(pEmailNotification.getEmail().getAttachments());
        eventEmailData.setImportance(pEmailNotification.getEmail().getImportance());
        eventEmailData.setEmailStatusCd(Event.STATUS_READY);
        return new EventEmailView(eventData, eventEmailData);
    }

    public String getToAddress(Connection pCon, APIAccess pFactory, OrderData pOrder) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pOrder.getStoreId());
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.PENDING_ORDER_NOTIFICATION);

        PropertyDataVector list = PropertyDataAccess.select(pCon, dbc);
        if (list.size() == 0) {
            log.info("PENDING_ORDER_NOTIFICATION property does not exist, StoreID: " + pOrder.getStoreId());
            return null;
        }

        PropertyData toAddressProp = (PropertyData) list.get(0);
        String toAddress = toAddressProp.getValue();
        if (!Utility.isSet(toAddress)) {
            log.info("Store specific email could not be found for " + RefCodeNames.PROPERTY_TYPE_CD.PENDING_ORDER_NOTIFICATION + " looking for default email");
            toAddress = pFactory.getEmailClientAPI().getDefaultEmailAddress();
        }

        if (!Utility.isSet(toAddress)) {
            log.info("No to address could be found, not sending email, return");
            return null;
        }

        return toAddress;
    }
}
