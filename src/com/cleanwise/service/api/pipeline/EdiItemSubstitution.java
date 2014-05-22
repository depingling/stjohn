/*
 * EdiItemSubstitution.java
 *
 * Created on November 14, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.dao.PreOrderAddressDataAccess;
import com.cleanwise.service.api.dao.PreOrderDataAccess;
import com.cleanwise.service.api.dao.PreOrderItemDataAccess;
import com.cleanwise.service.api.dao.PreOrderPropertyDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Pipeline;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemSubstitutionDefData;
import com.cleanwise.service.api.value.ItemSubstitutionDefDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.PreOrderAddressData;
import com.cleanwise.service.api.value.PreOrderAddressDataVector;
import com.cleanwise.service.api.value.PreOrderData;
import com.cleanwise.service.api.value.PreOrderItemData;
import com.cleanwise.service.api.value.PreOrderItemDataVector;
import com.cleanwise.service.api.value.PreOrderPropertyData;
import com.cleanwise.service.api.value.PreOrderPropertyDataVector;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.view.utils.Constants;

import org.apache.log4j.Logger;
/**
 * Determines whether order has noncontract items, which could be substituted.Cancels an order and places another one with substitution items.
 * @author  YKupershmidt (originaly CancelReplaceItemSubstitution)
 */
public class EdiItemSubstitution  implements OrderPipeline
{
    private static final Logger log = Logger.getLogger(EdiItemSubstitution.class);
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
      //should only process EDI/External orders
      //OrderRequestData orderRequest = pBaton.getOrderRequestData();
      pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      PreOrderData preOrderD = pBaton.getPreOrderData();
      String orderSourceCd = preOrderD.getOrderSourceCd();
      if(RefCodeNames.ORDER_SOURCE_CD.EDI_850.equals(orderSourceCd)) {
         return pBaton;
      }
      OrderData orderD = pBaton.getOrderData();
      if(orderD.getOrderId()<=0) {
        return pBaton; //Order was not parsed correctly
      }
      if(orderD.getAccountId()<=0) {
        return pBaton; //Order was not parsed correctly
      }
      //do not process unrecognized sku types
      String skuTypeCd = preOrderD.getSkuTypeCd();
      if(!(skuTypeCd.equals(RefCodeNames.SKU_TYPE_CD.CLW)||
           skuTypeCd.equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER)||
           skuTypeCd.equals(RefCodeNames.SKU_TYPE_CD.MANUFACTURER))){
                return pBaton;
      }
      //get the site id
      int siteId = orderD.getSiteId();
      if(siteId<=0) {
        return pBaton; //Something wrong with the order
      }
      
      //initialize some id values
      int contractId = orderD.getContractId();
      if(contractId<=0) {
        return pBaton; //Something wrong with the order
      }

      //Check if we have cost and price for all items
      boolean checkContractFl = false;
      OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        int itemId = oiD.getItemId();
        if(itemId<=0) {
          return pBaton; //Order error
        }
        BigDecimal priceBD = oiD.getCustContractPrice();
        double price = 0;
        if(priceBD!=null) price = priceBD.doubleValue();
        BigDecimal costBD = oiD.getDistItemCost();
        double cost = 0;
        if(costBD!=null) cost = costBD.doubleValue();
        if(cost<0.005 || price<0.005) {
          checkContractFl = true;
          break;
        }
      }
      if(!checkContractFl) {
        return pBaton;
      }

      //Get catalog id
      CatalogData catData = pFactory.getCatalogInformationAPI().getSiteCatalog(siteId);
      int catalogId = catData.getCatalogId();
      
      //Get contract items
      Contract contractEjb = pFactory.getContractAPI();
      ContractItemDataVector contItmDataVec = contractEjb.getContractItems(contractId);
      Iterator it = contItmDataVec.iterator();
      IdVector contractItemIds = new IdVector();
      HashMap  contractItemMap = new HashMap();
      while(it.hasNext()){
        ContractItemData data = (ContractItemData) it.next();
        contractItemMap.put(new Integer(data.getItemId()), data);
        contractItemIds.add(new Integer(data.getItemId()));
      }
      
      PreOrderItemDataVector itms = pBaton.getPreOrderItemDataVector();
      Map badItems = new HashMap();
      boolean allOnContract = true;
      boolean allSuccessfulSubs = true;
      it = itms.iterator();
      while(it.hasNext()){
        PreOrderItemData itm = (PreOrderItemData) it.next();
        int reqItemId = 
          ItemSkuMapping.mapToItemId(pCon, itm.getCustomerSku(), skuTypeCd, catalogId);
        Integer reqItemIdI = new Integer(reqItemId);
        if(!contractItemIds.contains(reqItemIdI)){
          allOnContract = false;
          DBCriteria crit = new DBCriteria();
          crit.addEqualTo(ItemSubstitutionDefDataAccess.BUS_ENTITY_ID,orderD.getAccountId());
          crit.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_STATUS_CD,RefCodeNames.SUBST_STATUS_CD.ACTIVE);
          crit.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_TYPE_CD,RefCodeNames.SUBST_TYPE_CD.ITEM_ACCOUNT);
          crit.addEqualTo(ItemSubstitutionDefDataAccess.ITEM_ID,reqItemId);
          ItemSubstitutionDefDataVector isdv = ItemSubstitutionDefDataAccess.select(pCon,crit);
          Iterator it2 = isdv.iterator();
          boolean foundASub = false;
          while(it2.hasNext()){
            ItemSubstitutionDefData sub = (ItemSubstitutionDefData) it2.next();
            if(contractItemIds.contains(new Integer(sub.getSubstItemId()))){
            BigDecimal qty = new BigDecimal(itm.getQuantity());
            qty = qty.setScale(0);
            try{
              ProductContainer prod = createProductContainer(pCon,skuTypeCd,sub,qty,contractItemMap);
              if (prod.isValid()){
                foundASub = true;
                badItems.put(new Integer(itm.getLineNumber()),prod);
              }
            }catch(ArithmeticException e){
              return pBaton;
            }catch(Exception e){
              e.printStackTrace();
              return pBaton;
            }
          }
        }
        if(!foundASub){
          allSuccessfulSubs = false;
          break;
        }
      }
    }
    if(allOnContract){
       return pBaton;
    }
    if(!allSuccessfulSubs){
      return pBaton;
    }
            
      String  newCustPoNum = preOrderD.getOrderRefNumber();
      //OrderRequestData newReq = copyRequestData(orderRequest);
      PreOrderData newPreOrderD = (PreOrderData) preOrderD.clone();
      PreOrderAddressDataVector newPreOrderAddressDV = new PreOrderAddressDataVector();
      for(Iterator iter=pBaton.getPreOrderAddressDataVector().iterator();
                                                              iter.hasNext();){
        PreOrderAddressData poaD = (PreOrderAddressData) iter.next();
        PreOrderAddressData newPoaD = (PreOrderAddressData) poaD.clone();
        newPreOrderAddressDV.add(newPoaD);
      }

      PreOrderPropertyDataVector newPreOrderPropertyDV = new PreOrderPropertyDataVector();
      for(Iterator iter=pBaton.getPreOrderPropertyDataVector().iterator();
                                                              iter.hasNext();){
        PreOrderPropertyData popD = (PreOrderPropertyData) iter.next();
        PreOrderPropertyData newPopD = (PreOrderPropertyData) popD.clone();
        newPreOrderPropertyDV.add(newPopD);
      }
      
      PreOrderItemDataVector newPreOrderItemDV = new PreOrderItemDataVector();
      for(Iterator iter=pBaton.getPreOrderItemDataVector().iterator();
                                                              iter.hasNext();){
        PreOrderItemData poiD = (PreOrderItemData) iter.next();
        PreOrderItemData newPoiD = (PreOrderItemData) poiD.clone();
        newPreOrderItemDV.add(newPoiD);
      }

      newPreOrderD.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.OTHER);
      newPreOrderD.setOrderStatusCdOveride(null);
      newPreOrderD.setCustomerPoNumber(newCustPoNum);
      newPreOrderD.setOrderRefNumber(null);

      //newReq.setOrderNote(null);
      ArrayList  substVector = new ArrayList();
      String orderNote = "Replacement for order: "+orderD.getOrderNum()+
            " (Had valid subs for non-contract items).";


      it = newPreOrderItemDV.iterator();
      HashMap custSkuMap = new HashMap();
      while(it.hasNext()){
        PreOrderItemData itm = (PreOrderItemData) it.next();
        if(badItems.containsKey(new Integer(itm.getLineNumber()))){
          ProductContainer lProductContainer = (ProductContainer) badItems.get(new Integer(itm.getLineNumber()));
          String substMess = "Sku "+itm.getCustomerSku()+
                         " Pack="+itm.getCustomerPack()+
                         " Uom="+itm.getCustomerUom()+
                         " Qty="+itm.getQuantity()+
                         " Price="+itm.getPrice()+ 
                         " substituted with Sku "+lProductContainer.mSkuNum+
                         " Pack="+lProductContainer.mPack+
                         " Uom="+lProductContainer.mUom+
                         " Qty="+lProductContainer.mConvertedQuantity+
                         " Price="+lProductContainer.mAmount;
          substVector.add(substMess);
          itm.setCustomerSku(lProductContainer.mSkuNum);
          itm.setCustomerPack(lProductContainer.mPack);
          itm.setCustomerProductDesc(lProductContainer.mShortDesc);
          itm.setCustomerUom(lProductContainer.mUom);
          itm.setPrice(lProductContainer.mAmount);
          itm.setQuantity(lProductContainer.mConvertedQuantity);
        }
                
        //remove any dups
        if(custSkuMap.containsKey(itm.getCustomerSku())){
           OrderRequestData.ItemEntry prevItm = (OrderRequestData.ItemEntry) custSkuMap.get(itm.getCustomerSku());
           prevItm.setQuantity(prevItm.getQuantity() + itm.getQuantity());
           it.remove();
           String dups = " Item "+itm.getCustomerSku()+" appeared multiple times, "+
                  "this was combined into one line item, and "+itm.getLineNumber()+
                  " was removed";
           orderNote += dups;
           substVector.add(dups);
        }else{
           custSkuMap.put(itm.getCustomerSku(),itm);
        }
       }
       if(newPreOrderD.getOrderNote() == null){
         newPreOrderD.setOrderNote(orderNote.toString());
       }else{
         newPreOrderD.setOrderNote(newPreOrderD.getOrderNote() + 
                                                   "::" +orderNote.toString());
       }

       OrderData newOrderD = 
           saveOrderRequest(preOrderD,newPreOrderItemDV, 
                       newPreOrderAddressDV, newPreOrderPropertyDV,
                       pCon,pBaton.getUserName());
       Pipeline pipelineEjb = pFactory.getPipelineAPI();
       pipelineEjb.processPipeline(newOrderD,RefCodeNames.PIPELINE_CD.SYNCH_ASYNCH);

       int orderId = newOrderD.getOrderId();
       newOrderD = OrderDataAccess.select(pCon,orderId);
       
       String newOrderNum = newOrderD.getOrderNum();
       newCustPoNum = newOrderD.getRequestPoNum();
       
       //Cancel old order
       preOrderD.setBypassPreCapturePipeline("T");
       String oldOrderNote = "This order is being cancelled as it has a sku not on contract."+
             " New order number equals: "+newOrderNum+
             " New order Customer Po Number equals "+newCustPoNum+" ";
       if(preOrderD.getOrderNote() == null){
          preOrderD.setOrderNote(oldOrderNote);
       }else{
          preOrderD.setOrderNote(preOrderD.getOrderNote() + "::" +oldOrderNote);
       }
       preOrderD.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
       PreOrderDataAccess.update(pCon,preOrderD);

       {
       OrderPropertyData opD = OrderPropertyData.createValue();
       opD.setOrderId(orderD.getOrderId());
       opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
       opD.setValue(oldOrderNote);
       opD.setShortDesc("Edi order note");
       opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
       opD.setAddBy(pBaton.getUserName());
       opD.setModBy(pBaton.getUserName());
       OrderPropertyDataAccess.insert(pCon, opD);
       }

      //Save comments
      for(int ii=0; ii<substVector.size(); ii++) {
        String note = (String) substVector.get(ii);
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

      //Change order status
      orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
      OrderDataAccess.update(pCon,orderD);

      //Change item status
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
        OrderItemDataAccess.update(pCon,oiD);
      }
    //send eMail
/////////////////////////////////////////////////////////////////////////////////
    //Get eMail address
    try {
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
      String emailMessage = "Replacement for order: "+orderD.getOrderNum()+
            " (Had valid subs for non-contract items).";
      emailMessage += System.getProperty("line.separator");
      emailMessage += "New order number: "+newOrderNum;
      emailMessage += System.getProperty("line.separator");
      emailMessage += "New Customer Po Number = "+newCustPoNum;
      for(int ii=0; ii<substVector.size(); ii++) {
        String str = (String) substVector.get(ii);
        emailMessage += System.getProperty("line.separator")+str;
      }
      String emailAddress = null;
      for(int ii=0; ii<emailDV.size(); ii++) {
        EmailData eD = (EmailData) emailDV.get(ii);
        emailAddress = eD.getEmailAddress();
        emailEjb.send(emailAddress,
		      emailEjb.getDefaultEmailAddress(),
		      (String) substVector.get(0) ,emailMessage,
		      	Constants.EMAIL_FORMAT_PLAIN_TEXT,
                      eD.getBusEntityId(),0);            
      }
      if(emailAddress==null) {
            String errorMess = "ERROR *********** No address to send order item substitution eMail."+
            " Account id = "+orderD.getAccountId()+". Also no default "+
                "eMail address found.";
         log.info(errorMess);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    //Return
     pBaton.setWhatNext(OrderPipelineBaton.STOP);
     return pBaton;
    }
    catch(Exception exc) {
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }

  //-----------------------------------------------------------------------------------
/*
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
 */
  //---------------------------------------------------------------------------
  private ProductContainer createProductContainer(Connection pCon,
                                                  String pSkuType,
                                                    ItemSubstitutionDefData pSubData,
                                                    BigDecimal pCurrentQty,
                                                    HashMap pContractItemMap)
  throws PipelineException,ArithmeticException
  {
     BigDecimal factor = pSubData.getUomConversionFactor();
     int newQty = pCurrentQty.divide(factor,0,BigDecimal.ROUND_UNNECESSARY).intValue();
     ProductDAO dao = new ProductDAO(pCon,pSubData.getSubstItemId());
     ProductDataVector pdv = dao.getResultVector();
     ProductData pd = (ProductData) pdv.get(0);
     String newSku;
     String newDesc;
     if(RefCodeNames.SKU_TYPE_CD.CLW.equals(pSkuType)){
       newSku = Integer.toString(pd.getSkuNum());
       newDesc = pd.getShortDesc();
     }else if(RefCodeNames.SKU_TYPE_CD.CUSTOMER.equals(pSkuType)){
       newSku = pd.getCustomerSkuNum();
       newDesc = pd.getCustomerProductShortDesc();
       if(!Utility.isSet(newSku)){
          newSku = Integer.toString(pd.getSkuNum());
       }
       if(!Utility.isSet(newDesc)){
          newDesc = pd.getShortDesc();
       }
     }else if(RefCodeNames.SKU_TYPE_CD.MANUFACTURER.equals(pSkuType)){
       newSku = pd.getManufacturerSku();
       newDesc = pd.getShortDesc();
     }else{
       String mess =   "EdiItemSubstituiton UNKNOWN SKU TYPE: " + pSkuType;
       log.info(mess);
       throw new PipelineException(mess);
     }
     ContractItemData cid = (ContractItemData) pContractItemMap.get(new Integer(pSubData.getSubstItemId()));

      ProductContainer retVal = new ProductContainer();
      retVal.mAmount = cid.getAmount();
      retVal.mSubst = pSubData;
      retVal.mPack = pd.getPack();
      retVal.mShortDesc = newDesc;
      retVal.mSkuNum = newSku;
      retVal.mUom = pd.getUom();
      retVal.mConvertedQuantity = newQty;
      return retVal;
    }
  
  //----------------------------------------------------------------------------
  private OrderData saveOrderRequest(PreOrderData pPreOrderD,
    PreOrderItemDataVector pPreOrderItemDV,
    PreOrderAddressDataVector pPreOrderAddressDV,
    PreOrderPropertyDataVector pPreOrderPropertyDV,
    Connection pCon,
    String pUserName
    )
  throws Exception
  {
      //Save  order request
      //PreOrder
      pPreOrderD = PreOrderDataAccess.insert(pCon,pPreOrderD);
      int preOrderId = pPreOrderD.getPreOrderId();

      //PreOrderItem
      for(Iterator iter = pPreOrderItemDV.iterator(); iter.hasNext();) {
        PreOrderItemData poiD = (PreOrderItemData) iter.next();
        poiD.setPreOrderId(preOrderId);
        poiD = PreOrderItemDataAccess.insert(pCon,poiD);
      }
      
      //PreOrderProperty
      for(Iterator iter = pPreOrderPropertyDV.iterator(); iter.hasNext();) {
        PreOrderPropertyData popD = (PreOrderPropertyData) iter.next();
        popD.setPreOrderId(preOrderId);
        popD = PreOrderPropertyDataAccess.insert(pCon,popD);
      }
      

      //PreOrderAddress
      for(Iterator iter = pPreOrderAddressDV.iterator(); iter.hasNext();) {
        PreOrderAddressData poaD = (PreOrderAddressData) iter.next();
        poaD.setPreOrderId(preOrderId);
        poaD = PreOrderAddressDataAccess.insert(pCon,poaD);
      }

      //Create order object
       OrderData orderD = OrderData.createValue();
       orderD.setPreOrderId(preOrderId);
       orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.RECEIVED);
       orderD.setAddBy(pUserName);
       orderD.setModBy(pUserName);
       orderD = OrderDataAccess.insert(pCon, orderD);       
       int orderId = orderD.getOrderId();

       //Create order item objects
       OrderItemDataVector orderItemDV = new OrderItemDataVector();

       //PreOrderItem
       for(Iterator iter = pPreOrderItemDV.iterator(); iter.hasNext();) {
         PreOrderItemData poiD = (PreOrderItemData) iter.next();
         OrderItemData oiD = OrderItemData.createValue();
         orderItemDV.add(oiD);
         oiD.setOrderId(orderId);
         oiD.setCustLineNum(poiD.getLineNumber());
         oiD.setCustItemSkuNum(poiD.getCustomerSku());
         oiD.setItemId(poiD.getItemId());
         oiD.setSaleTypeCd(poiD.getSaleTypeCd());
         oiD.setAddBy(pUserName);
         oiD.setModBy(pUserName);
         oiD = OrderItemDataAccess.insert(pCon, oiD);
       }
       return orderD;
  } 
   //---------------------------------------------------------------------------
    private class ProductContainer{
        String mSkuNum;
        String mUom;
        String mPack;
        String mShortDesc;
        BigDecimal mAmount;
        int mConvertedQuantity;
        //ProductData mProd;
        ItemSubstitutionDefData mSubst;
        boolean isValid(){
            if(!Utility.isSet(mSkuNum)){
                return false;
            }
            if(!Utility.isSet(mUom)){
                return false;
            }
            if(!Utility.isSet(mPack)){
                return false;
            }
            if(!Utility.isSet(mShortDesc)){
                return false;
            }
            if(mSubst == null){
                return false;
            }
            if(mAmount==null || mAmount.doubleValue()<0){
                return false;
            }
            return true;
        }
    }
  

 }


