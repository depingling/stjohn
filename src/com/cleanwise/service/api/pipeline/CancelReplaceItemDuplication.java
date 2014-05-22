/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.CustomerOrderRequestData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.view.utils.Constants;
/**
 * Pipeline class that will take an order that has a few lines for the same item, cancel this order,
 * and place a new one with merged lines for the same items.
 * @author  YKupershmidt
 */
public class CancelReplaceItemDuplication implements PreOrderCapturePipeline{
    int mCatalogId;
    int mSiteId;
    OrderRequestData mOrderRequest;
    APIAccess mFactory;
    Map mContractItemMap;
    IdVector mContractItemIds;
    
    
    private void initCatalogId()throws Exception {
        Contract contractEjb = mFactory.getContractAPI();
        CatalogData catData = mFactory.getCatalogInformationAPI().getSiteCatalog(mSiteId);
        if (null != catData) {
          mCatalogId = catData.getCatalogId();
        } else {
          mCatalogId = 0;
        }
    }
    
    
    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public void process(OrderRequestData pOrderRequest, Connection pCon, APIAccess pFactory) 
    throws PipelineException
    {
        Date currDate = new Date();
        mOrderRequest = pOrderRequest;
        //should only process EDI/External orders
        if (mOrderRequest instanceof CustomerOrderRequestData) {
           return;
       }
        mFactory = pFactory;
        try{
          //do not process unrecognized sku types
          if(!(mOrderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CLW)||
            mOrderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER)||
            mOrderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.MANUFACTURER))){
                return;
            }
          //get the site id
          if (mOrderRequest.getSiteId() <= 0){
            SiteDataVector sites = mFactory.getSiteAPI().getSiteByName(mOrderRequest.getSiteName(),
            mOrderRequest.getAccountId(),Site.BEGINS_WITH, Site.ORDER_BY_ID);
            if (sites.size() > 1) {
              return;
            } else if (sites.size()==0){
              return;
            } else {
                mSiteId = ((SiteData)sites.get(0)).getBusEntity().getBusEntityId();
                mOrderRequest.setSiteId(mSiteId);
            }
          }else{
             mSiteId = mOrderRequest.getSiteId();
          }            
          //initialize some id values
            initCatalogId();
          OrderRequestData.ItemEntryVector reqItems = mOrderRequest.getEntriesCollection();
          Map itemHM = new HashMap();
          Map newQty = new HashMap();
          //find and combine duplications
          ArrayList dupNotes = new ArrayList();
          for(int ii=0; ii<reqItems.size(); ii++) {
            OrderRequestData.ItemEntry reqItem = (OrderRequestData.ItemEntry) reqItems.get(ii);
            int itemId = ItemSkuMapping.mapToItemId(pCon, reqItem.getCustomerSku(), mOrderRequest.getSkuTypeCd(), mCatalogId);
            Integer itemIdI = new Integer(itemId);
            if(itemHM.containsKey(itemIdI)) {
              OrderRequestData.ItemEntry reqItem1 = (OrderRequestData.ItemEntry) itemHM.get(itemIdI);
              double price = reqItem.getPrice();
              double price1 = reqItem1.getPrice();
              double priceDif = price - price1;
              if(priceDif>0.01 || priceDif<-0.01) {
                return;
              } else {
                int duplQty = reqItem.getQuantity();
                Integer qtyI = (Integer) newQty.get(itemIdI);
                qtyI = new Integer(qtyI.intValue()+ duplQty);
                newQty.put(itemIdI, qtyI);
                String dupNote = " Item "+reqItem1.getCustomerSku()+" appeared multiple times, "+
                  "this was combined into one line item (customer line = "+reqItem1.getLineNumber()+
                  ") with total qunatiy "+qtyI+", and customer line "+reqItem.getLineNumber()+" was removed";
                dupNotes.add(dupNote);
              }
            } else {
              itemHM.put(itemIdI, reqItem);  
              newQty.put(itemIdI, new Integer(reqItem.getQuantity()));
            }
          }
          if(dupNotes.size()==0){
                return;
          }
            
          //Cancell and create new order  
          mOrderRequest.setBypassPreCapturePipeline(true);
          String  newCustPoNum = mOrderRequest.getOrderRefNumber();
          String oldOrderNote = "This order is being cancelled as it has duplicated items."+
              " New order Customer Po Number equals "+newCustPoNum;
          if(mOrderRequest.getOrderNote() == null){
             mOrderRequest.setOrderNote(oldOrderNote);
          }else{
             mOrderRequest.setOrderNote(mOrderRequest.getOrderNote() + "::" +oldOrderNote);
          }
          mOrderRequest.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
          //log the old order in a cancelled state
          ProcessOrderResultData orderResult = mFactory.getIntegrationServicesAPI().processOrderRequest(mOrderRequest);
          mOrderRequest.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.OTHER);
          mOrderRequest.setOrderStatusCdOveride(null);
          mOrderRequest.setCustomerPoNumber(newCustPoNum);
          mOrderRequest.setOrderRefNumber(null);
          mOrderRequest.setOrderNote(null);
          ArrayList  substVector = new ArrayList();
          StringBuffer orderNote = new StringBuffer("Replacement for order: ");
          orderNote.append(orderResult.getOrderNum());
          orderNote.append(" (Had the same item in different lines).");
          substVector.add(orderNote.toString());
          substVector.add("New Customer Po Number = "+newCustPoNum);
          //loop through the items again and remove the bad items and replce them with the
          //proper subs
          Iterator it = itemHM.keySet().iterator();
          reqItems.clear();
          while(it.hasNext()) {
            Integer key = (Integer) it.next();
            OrderRequestData.ItemEntry reqItem = (OrderRequestData.ItemEntry) itemHM.get(key);
            Integer qtyI = (Integer) newQty.get(key);
            reqItem.setQuantity(qtyI.intValue());
            reqItems.add(reqItem);
          }
          if(mOrderRequest.getOrderNote() == null){
            mOrderRequest.setOrderNote(orderNote.toString());
          }else{
            mOrderRequest.setOrderNote(mOrderRequest.getOrderNote() + "::" +orderNote.toString());
          }
          //Get eMail address
          DBCriteria dbc = new DBCriteria();
          dbc.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,mOrderRequest.getAccountId());
          dbc.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD, 
                               RefCodeNames.EMAIL_TYPE_CD.ORDER_MANAGER);
          dbc.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,
                                RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
          EmailDataVector emailDV = EmailDataAccess.select(pCon,dbc);
          if(emailDV.size()==0) { //Get default Address
            dbc = new DBCriteria();
            dbc.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,mOrderRequest.getAccountId());
            dbc.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD, 
                                         RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
            dbc.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,
                                   RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
            emailDV = EmailDataAccess.select(pCon,dbc);
          }
            
          EmailClient emailEjb = mFactory.getEmailClientAPI();
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
            emailEjb.send(emailAddress,
			  emailEjb.getDefaultEmailAddress(),
			  (String) substVector.get(0) ,
			  emailMessage,Constants.EMAIL_FORMAT_PLAIN_TEXT,
			  eD.getBusEntityId(),0);            
          }
          if(emailAddress==null) {
            String errorMess = "No address to send order item substitution eMail."+
              " Account id = "+mOrderRequest.getAccountId()+". Also no default "+
              "eMail address found.";
              throw new Exception(errorMess);
          }
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }
    
}
