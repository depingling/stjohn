package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.operations.FileGenerator;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.EventPropertyDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.TradingPartnerDescView;
import com.cleanwise.service.api.value.TradingProfileConfigData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Outbound850EventSender  implements OrderPipeline {
    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {
        try {
            OrderData orderData = pBaton.getOrderData();
            if(orderData!=null)
            {
                APIAccess factory = new APIAccess();
                IntegrationServices intServEjb = factory.getIntegrationServicesAPI();
                Event eventEjb = factory.getEventAPI();
                Process processEjb = factory.getProcessAPI();

                int accountId = orderData.getAccountId();

                TradingPartnerDescView profileConfig = intServEjb.getOutboundTradingProfileConfig(null, accountId, 0, "850");

                if(profileConfig!=null)
                {
                    TradingProfileConfigData config = profileConfig.getTradingProfileConfigData();
                    String genClass= config.getClassname();
                    int processId=processEjb.getActiveTemplateProcessId(RefCodeNames.PROCESS_NAMES.ORDER_NOTIFICATION);

                    String contactSql = "select order_contact_name contact_name," +
                    "order_contact_email contact_email," +
                    "user_first_name||' '||user_last_name as placed_by" +
                    " from clw_order o where order_id  = ?";

                    PreparedStatement pstmt = pCon.prepareStatement(contactSql);
                    pstmt.setInt(1,orderData.getOrderId());
                    ResultSet rs=pstmt.executeQuery();
                    rs.next();
                    String contactName   =  rs.getString("contact_name");
                    String contactEmail  =  rs.getString("contact_email");
                    String placedBy      =  rs.getString("placed_by");
                    rs.close();
                    pstmt.close();

                    String subject="";
                    String message="";
                    String path = System.getProperty("base.production.dir");
                    if(path==null)   {
                        throw new Exception("<base.production.dir> not found");
                    }
                    String tempAttachmentFileName=path+"/tmp/"+"Order_"+orderData.getOrderId();


//                    EventData eventData = new EventData(0,Event.STATUS_READY,Event.TYPE_PROCESS,null,null,1);
//
//                    eventData=eventEjb.addEventToDB(eventData);

                    EventData eventData = EventData.createValue();
                    eventData.setStatus(Event.STATUS_READY);
                    eventData.setType(Event.TYPE_PROCESS);
                    eventData.setAttempt(1);
                    EventProcessView epv = new EventProcessView(eventData,
                            new EventPropertyDataVector(), 0);
                    
//                    eventEjb.addProperty(eventData.getEventId(),"processId",Event.PROPERTY_PROCESS_TEMPLATE_ID,new Integer(processId),1);

//                    eventEjb.addProperty(eventData.getEventId(),"className",Event.PROCESS_VARIABLE,genClass,1);
//                    eventEjb.addProperty(eventData.getEventId(),"processName",Event.PROCESS_VARIABLE,RefCodeNames.PROCESS_NAMES.OUTBOUND_850,2);
//                    eventEjb.addProperty(eventData.getEventId(),"subject",Event.PROCESS_VARIABLE,subject,3);
//                    eventEjb.addProperty(eventData.getEventId(),"message",Event.PROCESS_VARIABLE,message,4);
//                    eventEjb.addProperty(eventData.getEventId(),"tempAttachmentFileName",Event.PROCESS_VARIABLE,tempAttachmentFileName,5);
//                    eventEjb.addProperty(eventData.getEventId(),"toAddress",Event.PROCESS_VARIABLE,contactEmail,6);
//                    eventEjb.addProperty(eventData.getEventId(),"orderId",Event.PROCESS_VARIABLE,new Integer(orderData.getOrderId()),7);
//                    eventEjb.addProperty(eventData.getEventId(),"addBy",Event.PROCESS_VARIABLE,"Outbound850EventSender",8);
//                    eventEjb.addProperty(eventData.getEventId(),"ccAddress",Event.PROCESS_VARIABLE,"",9);
//                    eventEjb.addProperty(eventData.getEventId(),"fileType",Event.PROCESS_VARIABLE, FileGenerator.XML_FILE,10);

                    epv.getProperties().add(Utility.createEventPropertyData("processId",Event.PROPERTY_PROCESS_TEMPLATE_ID,new Integer(processId),1));
                    epv.getProperties().add(Utility.createEventPropertyData("className",Event.PROCESS_VARIABLE,genClass,1));
                    epv.getProperties().add(Utility.createEventPropertyData("processName",Event.PROCESS_VARIABLE,RefCodeNames.PROCESS_NAMES.OUTBOUND_850,2));
                    epv.getProperties().add(Utility.createEventPropertyData("subject",Event.PROCESS_VARIABLE,subject,3));
                    epv.getProperties().add(Utility.createEventPropertyData("message",Event.PROCESS_VARIABLE,message,4));
                    epv.getProperties().add(Utility.createEventPropertyData("tempAttachmentFileName",Event.PROCESS_VARIABLE,tempAttachmentFileName,5));
                    epv.getProperties().add(Utility.createEventPropertyData("toAddress",Event.PROCESS_VARIABLE,contactEmail,6));
                    epv.getProperties().add(Utility.createEventPropertyData("orderId",Event.PROCESS_VARIABLE,new Integer(orderData.getOrderId()),7));
                    epv.getProperties().add(Utility.createEventPropertyData("addBy",Event.PROCESS_VARIABLE,"Outbound850EventSender",8));
                    epv.getProperties().add(Utility.createEventPropertyData("ccAddress",Event.PROCESS_VARIABLE,"",9));
                    epv.getProperties().add(Utility.createEventPropertyData("fileType",Event.PROCESS_VARIABLE, FileGenerator.XML_FILE,10));
                    eventEjb.addEventProcess(epv, "Outbound850EventSender");
                }
            }
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            return pBaton;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        } finally {
        }
    }
}
