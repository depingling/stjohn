package com.cleanwise.service.api.pipeline;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.naming.NamingException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.process.operations.FileGenerator;
import com.cleanwise.service.api.process.operations.OrderNotificationGenerator;
import com.cleanwise.service.api.process.operations.OrderNotificationGeneratorBase;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderInfoDataView;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.UserInfoDataVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;

import org.apache.log4j.Logger;

/**
 * Title:        OrderNotification
 * Description:  Sending email to customer
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         12.04.2007
 * Time:         13:08:11
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */

public class OrderNotification implements OrderPipeline {

    private  static final Logger log = Logger.getLogger(OrderNotification.class);

    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {

        String message;
        File[] attachments;
        File tmp = null;
        Order orderEjb = null;

        try {
            orderEjb = APIAccess.getAPIAccess().getOrderAPI();
        } catch (APIServiceAccessException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
/*
        try {
            tmp = File.createTempFile("Attachment", ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
       try {

            OrderData orderData = pBaton.getOrderData();

            if(orderData!=null) // &&
                   // !(RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(orderData.getOrderSourceCd())) &&
                   // !(RefCodeNames.ORDER_SOURCE_CD.SCHEDULER.equals(orderData.getOrderSourceCd())))
            {
                APIAccess factory = new APIAccess();
                Event eventEjb = factory.getEventAPI();
                User userEjb = factory.getUserAPI();
                PropertyService propEjb = factory.getPropertyServiceAPI();
                int accountId = orderData.getAccountId();
                boolean isBatchOrder = RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER.equals(orderData.getOrderTypeCd());
                if (isBatchOrder){
                	boolean sendOrderConfirmation = false;
                	OrderMetaDataVector omDV = pBaton.getOrderMetaDataVector();
                    for (Iterator iter = omDV.iterator(); iter.hasNext();) {
                    	OrderMetaData omD = (OrderMetaData) iter.next();
                        if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.SEND_ORDER_CONFIRMATION.equals(omD.getName())) {
                        	sendOrderConfirmation = Utility.isTrue(omD.getValue());
                            break;
                        }
                    }
                    if (!sendOrderConfirmation)
                    	return pBaton;
                }

                //--------------------------------------------------
                Date startTime = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
                String startTimeS = sdf.format(startTime);

                UserInfoDataVector contactUsers= userEjb.getUserInfoDataCollection(orderData.getSiteId(),
                                                                               orderData.getAccountId(),
                                                                               UserInfoData.USER_GETS_EMAIL_ORDER_DETAIL_APPROVED);

                Date endTime = new Date();
                String endTimeS = sdf.format(endTime);


//                String fromEmail =
//                        BusEntityDAO.getOutboundFromEmailAddress(pCon,orderData.getAccountId(),orderData.getStoreId());
				String fromEmail = pFactory.getAccountAPI().getDefaultEmail(
						orderData.getAccountId(), orderData.getStoreId());

                if (!contactUsers.isEmpty()) {

                    String subject = "Order notification";
                    String emailContacts= ApplicationsEmailTool.getEmailContacts(contactUsers);

                    if(Utility.isSet(emailContacts)){

//                        message = getMessage(orderData.getOrderId());
                        OrderInfoDataView orderInfoData = orderEjb.getOrderInfoData(orderData.getOrderId());

                        String className = null;
                        try {
                          className = propEjb.getBusEntityProperty(accountId, RefCodeNames.PROPERTY_TYPE_CD.CONFIRM_ORDER_EMAIL_GENERATOR);
                        }
                        catch (DataNotFoundException ex) {
                          className = null;
                        }
                        catch (RemoteException ex) {
                          ex.getStackTrace();
                        }
                        OrderNotificationGeneratorBase fileGenerator = (OrderNotificationGeneratorBase)getFileGenerator(className);
                        tmp = Utility.createTempFileAttachment(fileGenerator.getFileExtension());
                        fileGenerator.generate(orderInfoData, tmp);
//                        message = getMessage(orderData.getOrderId(), fileGenerator);
                        message = fileGenerator.getTextMessage();
                        subject = fileGenerator.getSubject(orderInfoData);
                        if (!Utility.isSet(message)){
                          String msg = "OrderNotification :: process()---<probably Order ERROR>---No Email Message content was generated!";
                          log.info(msg);
                        }

                        attachments = new File[]{tmp};

                        FileAttach[] fileAttach;

                        if(attachments!=null){
                            fileAttach = (new ApplicationsEmailTool()).fromFilesToAttachs(attachments);
                        } else{
                            fileAttach = new FileAttach[0];
                        }

//                        EventData eventData = new EventData(0, Event.STATUS_READY, Event.TYPE_EMAIL, null, null, 1);
//                        eventData = eventEjb.addEventToDB(eventData);
                        EventData eventData = EventData.createValue();
                        eventData.setStatus(Event.STATUS_READY);
                        eventData.setType(Event.TYPE_EMAIL);
                        eventData.setAttempt(1);
                        
                        EventEmailDataView eventEmailData = new EventEmailDataView();
                        eventEmailData.setEventEmailId(0);
                        eventEmailData.setToAddress(emailContacts);
                        eventEmailData.setFromAddress(fromEmail);
                        eventEmailData.setSubject(subject);
                        eventEmailData.setText(message);

                        eventEmailData.setAttachments(fileAttach);
                        eventEmailData.setEventId(eventData.getEventId());
                        eventEmailData.setEmailStatusCd(Event.STATUS_READY);
                        eventEmailData.setModBy("OrderNotification");
                        eventEmailData.setAddBy("OrderNotification");
//                        eventEjb.addEventEmail(eventEmailData);
                        
                        EventEmailView eev = new EventEmailView(eventData, eventEmailData); 
                        eventEjb.addEventEmail(eev, "OrderNotification");
                    } else {
                        OrderPropertyData note = OrderPropertyData.createValue();
                        note.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                        note.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                        note.setShortDesc("Pipeline Warning");
                        note.setValue("User has no contact email");
                        note.setOrderId(orderData.getOrderId());
                        note.setModBy("OrderNotification");
                        note.setAddBy("OrderNotification");
                        orderEjb.addNote(note);

                    }
                }

                pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            }

            return pBaton;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }


    private String getMessage(int orderId, OrderNotificationGeneratorBase fileGenerator){

        APIAccess factory;
        Order orderEjb;
        String message = "Order has been placed.";

        try {

            factory = new APIAccess();
            orderEjb = factory.getOrderAPI();
//            message =  (new OrderNotificationGenerator().genTXT(orderEjb.getOrderInfoData(orderId)));
             message = fileGenerator.genTXT(orderEjb.getOrderInfoData(orderId));

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (APIServiceAccessException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public FileGenerator getFileGenerator(String className) throws Exception {
        FileGenerator generator = null;
        Class c = null;
        if (Utility.isSet(className)) {
          c = Class.forName(className);
          generator = (FileGenerator) c.newInstance();
        } else {
          generator = new OrderNotificationGenerator();
        }

        return generator;

     }

}
