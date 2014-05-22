/*
 * PunchOutOrderProcessor.java
 *
 * Created on June 9, 2004, 2:28 PM
 */

package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.*;
import java.sql.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;
import java.io.*;
import java.net.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;




/**
 * Figures out wheather to proceed forther into the pipeline based off the configuration of
 * the account.  If an order is for an account that is setup for punch out orders then the
 *  pipeline will not continue.  This does not process EDI orders as they complete the loop.
 * @author  bstevens
 */
public class PunchOutInlineOrderProcessor implements OrderPipeline{


    public OrderPipelineBaton process(OrderPipelineBaton pBaton, OrderPipelineActor pActor, Connection pCon, APIAccess pFactory) throws PipelineException {
        OrderData orderD = pBaton.getOrderData();
        String whatNext = null;
        try{
            int accountId = orderD.getAccountId();
            if(accountId > 0){
                PropertyUtil pu = new PropertyUtil(pCon);
                String custSysAp = pu.fetchValueIgnoreMissing(0,accountId,RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_APPROVAL_CD);
                if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(custSysAp)){
                    if (!RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderD.getOrderSourceCd())){
                        //preform the translation
                        TradingPartner tpEjb = pFactory.getTradingPartnerAPI();
                        IntegrationServices isEjb = pFactory.getIntegrationServicesAPI();
                        try{
                            OutputStream out;
                            //establish the connection with the notification server.
                            out=null;
                            //Utility.getOrderProperty(pBaton.getOrderPropertyDataVector(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_ID);
                            OrderPropertyData opd = Utility.getOrderProperty(pBaton.getOrderPropertyDataVector(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_URL);
                            String url = opd.getValue();

                            ByteArrayOutputStream bout = new ByteArrayOutputStream();

                            OutboundTranslate translator = new OutboundTranslate(null, orderD.getAccountId(), RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT,isEjb,tpEjb,bout);
                            OutboundEDIRequestDataVector orders = new OutboundEDIRequestDataVector();
                            OutboundEDIRequestData toTranslate = OutboundEDIRequestData.createValue();
                            toTranslate.setOrderPropertyDV(pBaton.getOrderPropertyDataVector());
                            toTranslate.setOrderD(orderD);
                            toTranslate.setOrderItemDV(pBaton.getOrderItemDataVector());
                            orders.add(toTranslate);
                            translator.translate(orders, null, orderD.getAccountId(), RefCodeNames.EDI_TYPE_CD.TPUNCH_OUT_ORDER_OUT);
                            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());


                            //**************************
                            // Prepare HTTP post
                            PostMethod post = new PostMethod(url);
                            post.setRequestContentLength(bout.size());
                            post.setRequestBody(bin);
                            // Specify content type and encoding
                            post.setRequestHeader("Content-type", "text/xml");
                            // Get HTTP client
                            HttpClient httpclient = new HttpClient();
                            // Execute request
                            try {
                                int result = httpclient.executeMethod(post);
                                if(result != 200){
                                    pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM,
                                                    RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,0, 0,
                                      "pipeline.message.communicatingProblem");
                                    orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
                                }
                            } finally {
                                // Release current connection to the connection pool once you are done
                                post.releaseConnection();
                            }

                            //**************************

                        }catch(Exception e){
                            e.printStackTrace();
                            //reject the order
                            pBaton.addError(pCon, OrderPipelineBaton.WORKFLOW_RULE_ALARM,
                                            RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,0, 0,
                                            "pipeline.message.communicatingProblem");
                            orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
                        }






                        if(orderD.getOrderId() > 0){
                            OrderDataAccess.update(pCon,orderD);
                        }
                    }
                }
            }
        }catch(Exception e){
            pBaton.setWhatNext(OrderPipelineBaton.STOP);
            e.printStackTrace();
            orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
        }
        if(whatNext == null){
            whatNext = OrderPipelineBaton.GO_NEXT;
        }
        pBaton.setWhatNext(whatNext);
        return pBaton;
    }
}
