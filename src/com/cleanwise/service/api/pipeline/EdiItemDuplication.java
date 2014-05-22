/*
 * EdiItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.CustomerOrderRequestData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.view.utils.Constants;
/**
 * Cancels an order with duplication items and places another consolidated one
 * @author  YKupershmidt (originaly CancelReplaceItemDuplication)
 */
public class EdiItemDuplication  implements OrderPipeline
{
    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton, 
                OrderPipelineActor pActor, 
                Connection pCon, 
                APIAccess pFactory) 
    throws PipelineException
    {
    try{
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      OrderData orderD = pBaton.getOrderData();
      //should only process EDI/External orders
      OrderRequestData orderRequest = pBaton.getOrderRequestData();
      if (orderRequest instanceof CustomerOrderRequestData) {
        return pBaton;
      }
      if(orderD.getOrderId()<=0) {
        return pBaton; //Order was not parsed correctly
      }

      //do not process unrecognized sku types
      if(!(orderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CLW)||
           orderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER)||
           orderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.MANUFACTURER))){
                return pBaton;
      }
      //get the site id
      int siteId = orderD.getSiteId();
      if(siteId<=0) {
        return pBaton; //Something wrong with the order
      }
      //initialize some id values
      Map itemHM = new HashMap();
      //find and combine duplications
      ArrayList dupNotes = new ArrayList();
      OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
      boolean foundDuplFl = false;      
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        int itemId = oiD.getItemId();
        if(itemId<=0) {
          return pBaton;
        }
        ArrayList lineNums = (ArrayList) itemHM.get(new Integer(itemId));
        if(lineNums==null) {
          lineNums = new ArrayList();
        } else {
          foundDuplFl = true;
        }
        lineNums.add(new Integer(oiD.getCustLineNum()));
      }
      if(!foundDuplFl) {
        return pBaton;
      }
      
      OrderRequestData newReq = copyRequestData(orderRequest);
      OrderRequestData.ItemEntryVector entryItems = newReq.getEntriesCollection();
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        int itemId = oiD.getItemId();
        ArrayList lineNums = (ArrayList) itemHM.get(new Integer(itemId));
        if(lineNums.size()<=1){
          continue;
        }
        //find first line
        int firstLineNum = ((Integer) lineNums.get(0)).intValue();
        OrderRequestData.ItemEntry itemEntry = null;
        for(int jj=0; jj<entryItems.size(); jj++) {
          OrderRequestData.ItemEntry ie = 
                               (OrderRequestData.ItemEntry) entryItems.get(jj);
          if(ie.getLineNumber()==firstLineNum) {
            itemEntry = ie;
            break;
          }
        }
        if(itemEntry==null) {
          return pBaton; //No consistancy
        }
        for(int ll=1; ll<lineNums.size(); ll++) {
          int ln = ((Integer) lineNums.get(ll)).intValue();
          for(int jj=0; jj<entryItems.size(); jj++) {
            OrderRequestData.ItemEntry ie = 
                               (OrderRequestData.ItemEntry) entryItems.get(jj);
            if(ie.getLineNumber()==ln) {
              //compare prices
              double price = itemEntry.getPrice();
              double duplPrice = ie.getPrice();
              double diff = price-duplPrice;
              if(diff>0.005 || diff<-0.005) {
                return pBaton; //Different request prices
              }
              //consolidate 
              int qty = itemEntry.getQuantity();
              int duplQty = ie.getQuantity();
              qty += duplQty;
              String dupNote = " Item "+ie.getCustomerSku()+" appeared multiple times, "+
               "this was combined into one line item (customer line = "+ln+
               ") with total qunatiy "+qty+", and customer line "+ie.getLineNumber()+" was removed";
               dupNotes.add(dupNote);
               itemEntry.setQuantity(qty);
               entryItems.remove(jj);
              break;
            }
          }
          
        }
      }

      //Place new order        
      newReq.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.OTHER);
      newReq.setOrderStatusCdOveride(null);
      String  newCustPoNum = orderRequest.getOrderRefNumber();
      newReq.setCustomerPoNumber(newCustPoNum);
      newReq.setOrderRefNumber(null);
      newReq.setOrderNote(null);
      ArrayList  substVector = new ArrayList();
      String orderNote ="Replacement for order: "+orderD.getOrderNum()+
                             " (Had the same item in different lines).";
      substVector.add(orderNote);
      newReq.setOrderNote(orderNote);        
      substVector.add("New Customer Po Number = "+newCustPoNum);
      ProcessOrderResultData orderResult = pFactory.getIntegrationServicesAPI().processOrderRequest(newReq);
      String newOrderNum = orderResult.getOrderNum();
      substVector.add("New Order Number = "+newOrderNum);
            
      //Cancell and create new order  
      orderRequest.setBypassPreCapturePipeline(true);
      String oldOrderNote = "This order is being cancelled as it has duplicated items."+
           " New order Customer Po Number = "+newCustPoNum +" New Order Number = "+newOrderNum ;
        
      //Save comments
      for(int ii=0; ii<dupNotes.size(); ii++) {
        String note = (String) dupNotes.get(ii);
        OrderPropertyData opD = OrderPropertyData.createValue();
        opD.setOrderId(orderD.getOrderId());
        opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        opD.setValue(note);
        opD.setShortDesc("Edi order note");
        opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        opD.setAddBy(pBaton.getUserName());
        opD.setModBy(pBaton.getUserName());
        OrderPropertyDataAccess.insert(pCon, opD);
      }
      OrderPropertyData opD = OrderPropertyData.createValue();
      opD.setOrderId(orderD.getOrderId());
      opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
      opD.setValue(oldOrderNote);
      opD.setShortDesc("Edi order note");
      opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
      opD.setAddBy(pBaton.getUserName());
      opD.setModBy(pBaton.getUserName());
      OrderPropertyDataAccess.insert(pCon, opD);

      //Change order status
      orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
      OrderDataAccess.update(pCon,orderD);

      //Change item status
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
        OrderItemDataAccess.update(pCon,oiD);
      }
      
      //Send eMail
      //Get eMail address
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,orderD.getAccountId());
      dbc.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD, 
                               RefCodeNames.EMAIL_TYPE_CD.ORDER_MANAGER);
      dbc.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,
                                RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
      EmailDataVector emailDV = EmailDataAccess.select(pCon,dbc);
      if(emailDV.size()==0) { //Get default Address
        dbc = new DBCriteria();
        dbc.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,orderD.getAccountId());
        dbc.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD, 
                                         RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
        dbc.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,
                                   RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
        emailDV = EmailDataAccess.select(pCon,dbc);
       }
            
       EmailClient emailEjb = pFactory.getEmailClientAPI();
          String emailMessage = null;
          for(int ii=0; ii<dupNotes.size(); ii++) {
            String str = (String)dupNotes.get(ii);
            if(ii==0) {
              emailMessage = str;
            } else {
              emailMessage += System.getProperty("line.separator")+str;
            }
          }
          String emailAddress = null;
          for(int ii=0; ii<emailDV.size(); ii++) {
            EmailData eD = (EmailData) emailDV.get(ii);
            emailAddress = eD.getEmailAddress();
            try {
              emailEjb.send(emailAddress,
			    emailEjb.getDefaultEmailAddress(),
			    (String) substVector.get(0) ,
			    emailMessage,Constants.EMAIL_FORMAT_PLAIN_TEXT,
			    eD.getBusEntityId(),0);            
            } catch(Exception exc) {
		exc.printStackTrace();
            }
          }
          if(emailAddress==null) {
            String errorMess = "No address to send order item substitution eMail."+
              " Account id = "+orderD.getAccountId()+". Also no default "+
              "eMail address found.";
              //throw new Exception(errorMess);
          }
    //Return
     pBaton.setWhatNext(OrderPipelineBaton.STOP);
     return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    }

  //-----------------------------------------------------------------------------------
  private OrderRequestData copyRequestData(OrderRequestData pOrderRequest)
      throws java.io.IOException, ClassNotFoundException
  {
    java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
    java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
    os.writeObject(pOrderRequest);
    os.flush();
    os.close();
    byte[] byteImage = oStream.toByteArray();
    oStream.close();
    java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(byteImage);
    java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
    OrderRequestData orderRequestCopy = (OrderRequestData) is.readObject();    
    is.close();
    iStream.close();
    return orderRequestCopy;
  }

 }


