/*
 * CancelReplaceOrderSubstitution.java
 *
 * Created on May 6, 2003, 3:35 PM
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
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.session.EmailClient;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.ContractDescData;
import com.cleanwise.service.api.value.ContractDescDataVector;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.ContractItemDataVector;
import com.cleanwise.service.api.value.CustomerOrderRequestData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.EmailDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemSubstitutionDefData;
import com.cleanwise.service.api.value.ItemSubstitutionDefDataVector;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.ProcessOrderResultData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ProductDataVector;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.SiteDataVector;
import com.cleanwise.view.utils.Constants;
import org.apache.log4j.*;
/**
 * Pipeline class that will take an order that has an item that is not on the contract, cancel this order,
 * and place a new one with an item that is set up as being equivalent.
 * @author  bstevens
 */
public class CancelReplaceOrderSubstitution implements PreOrderCapturePipeline{
    private static final Logger log = Logger.getLogger(CancelReplaceOrderSubstitution.class);
    int mCatalogId;
    int mContractId;
    int mSiteId;
    OrderRequestData mOrderRequest;
    APIAccess mFactory;
    Map mContractItemMap;
    IdVector mContractItemIds;
    
    
    private void initContractCatalogIds()throws Exception {
        Contract contractEjb = mFactory.getContractAPI();
        CatalogData catData = mFactory.getCatalogInformationAPI().getSiteCatalog(mSiteId);
        mCatalogId = catData.getCatalogId();
        if (mOrderRequest instanceof CustomerOrderRequestData) {
            CustomerOrderRequestData custOrderRequest = (CustomerOrderRequestData) mOrderRequest;
            mContractId = custOrderRequest.getContractId();
            return;
        }
        if (null != catData) {
            ContractDescDataVector contractDescDV = contractEjb.getContractDescsByCatalog(mCatalogId);
            Iterator it = contractDescDV.iterator();
            while(it.hasNext()){
                ContractDescData contractDescD = (ContractDescData) it.next();
                if (contractDescD.getStatus().equalsIgnoreCase(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE)) {
                    mContractId = contractDescD.getContractId();
                    return;
                }
            }
        }
        mContractId = 0;
    }
    
    private void initStateForContractItems(ContractItemDataVector pContractItemDataVector){
        Iterator it = pContractItemDataVector.iterator();
        mContractItemIds = new IdVector();
        mContractItemMap = new HashMap();
        while(it.hasNext()){
            ContractItemData data = (ContractItemData) it.next();
            mContractItemMap.put(new Integer(data.getItemId()), data);
            mContractItemIds.add(new Integer(data.getItemId()));
        }
    }
    
    private ProductContainer createProductContainer(Connection pCon,ItemSubstitutionDefData pSubData,BigDecimal pCurrentQty)
    throws PipelineException,ArithmeticException{
        BigDecimal factor = pSubData.getUomConversionFactor();
        int newQty = pCurrentQty.divide(factor,0,BigDecimal.ROUND_UNNECESSARY).intValue();
        ProductDAO dao = new ProductDAO(pCon,pSubData.getSubstItemId());
        ProductDataVector pdv = dao.getResultVector();
        ProductData pd = (ProductData) pdv.get(0);
        String newSku;
        String newDesc;
        if(mOrderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CLW)){
            newSku = Integer.toString(pd.getSkuNum());
            newDesc = pd.getShortDesc();
        }else if(mOrderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.CUSTOMER)){
            newSku = pd.getCustomerSkuNum();
            newDesc = pd.getCustomerProductShortDesc();
            //default to cw if customer sku does not exist
            if(!Utility.isSet(newSku)){
                newSku = Integer.toString(pd.getSkuNum());
            }
            if(!Utility.isSet(newDesc)){
                newDesc = pd.getShortDesc();
            }
        }else if(mOrderRequest.getSkuTypeCd().equals(RefCodeNames.SKU_TYPE_CD.MANUFACTURER)){
            newSku = pd.getManufacturerSku();
            newDesc = pd.getShortDesc();
        }else{
            throw new PipelineException("UNKNOWN SKU TYPE: " + mOrderRequest.getSkuTypeCd());
        }
        ContractItemData cid = (ContractItemData) mContractItemMap.get(new Integer(pSubData.getSubstItemId()));

        ProductContainer retVal = new ProductContainer();
        retVal.mAmount = cid.getAmount().doubleValue();
        retVal.mSubst = pSubData;
        retVal.mPack = pd.getPack();
        retVal.mShortDesc = newDesc;
        retVal.mSkuNum = newSku;
        retVal.mUom = pd.getUom();
        retVal.mConvertedQuantity = newQty;
        return retVal;
    }
    
    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public void process(OrderRequestData pOrderRequest, Connection pCon, APIAccess pFactory) throws PipelineException{
        mOrderRequest = pOrderRequest;
        mFactory = pFactory;
        try{
            //should only process EDI/External orders
            if (mOrderRequest instanceof CustomerOrderRequestData) {
                return;
            }
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
                if (sites.size() != 1) {
                    return;
                }
                mSiteId = ((SiteData)sites.get(0)).getBusEntity().getBusEntityId();
                mOrderRequest.setSiteId(mSiteId);
            }else{
                mSiteId = mOrderRequest.getSiteId();
            }            
            //initialize some id values
            initContractCatalogIds();
            if(mContractId == 0){
                return;
            }

            ContractItemDataVector contItmDataVec = mFactory.getContractAPI().getContractItems(mContractId);
            initStateForContractItems(contItmDataVec);
            
            
            OrderRequestData.ItemEntryVector itms = mOrderRequest.getEntriesCollection();
            
            Map badItems = new HashMap();
            
            boolean allOnContract = true;
            boolean allSuccessfulSubs = true;
            Iterator it = itms.iterator();
            while(it.hasNext()){
                OrderRequestData.ItemEntry itm = (OrderRequestData.ItemEntry) it.next();
                Integer reqItemId = new Integer(ItemSkuMapping.mapToItemId(pCon, itm.getCustomerSku(), mOrderRequest.getSkuTypeCd(), mCatalogId));
                if(!mContractItemIds.contains(reqItemId)){
                    allOnContract = false;
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(ItemSubstitutionDefDataAccess.BUS_ENTITY_ID,mOrderRequest.getAccountId());
                    crit.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_STATUS_CD,RefCodeNames.SUBST_STATUS_CD.ACTIVE);
                    crit.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_TYPE_CD,RefCodeNames.SUBST_TYPE_CD.ITEM_ACCOUNT);
                    crit.addEqualTo(ItemSubstitutionDefDataAccess.ITEM_ID,reqItemId.intValue());
                    ItemSubstitutionDefDataVector isdv = ItemSubstitutionDefDataAccess.select(pCon,crit);
                    Iterator it2 = isdv.iterator();
                    boolean foundASub = false;
                    while(it2.hasNext()){
                        ItemSubstitutionDefData sub = (ItemSubstitutionDefData) it2.next();
                        if(mContractItemIds.contains(new Integer(sub.getSubstItemId()))){
                            BigDecimal qty = new BigDecimal(itm.getQuantity());
                            qty = qty.setScale(0);
                            try{
                                ProductContainer prod = createProductContainer(pCon,sub,qty);
                                if (prod.isValid()){
                                    foundASub = true;
                                    badItems.put(new Integer(itm.getLineNumber()),prod);
                                }
                            }catch(ArithmeticException e){
                                log.info("Skipping sub due to not dividable: ");
                                log.info("ITEM::"+itm);
                                log.info("SUB::"+sub);
                            }catch(RuntimeException e){
                                e.printStackTrace();
                                log.info("Skipping as something was not set: ");
                                log.info("ITEM::"+itm);
                                log.info("SUB::"+sub);
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
                return;
            }
            if(!allSuccessfulSubs){
                return;
            }
            

            mOrderRequest.setBypassPreCapturePipeline(true);
            String  newCustPoNum = mOrderRequest.getOrderRefNumber();
            String oldOrderNote = "This order is being cancelled as it has a sku not on contract."+
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
            orderNote.append(" (Had valid subs for non-contract items).");
            substVector.add(orderNote.toString());
            substVector.add("New Customer Po Number = "+newCustPoNum);
            //loop through the items again and remove the bad items and replce them with the
            //proper subs
            it = itms.iterator();
            HashMap custSkuMap = new HashMap();
            while(it.hasNext()){
                OrderRequestData.ItemEntry itm = (OrderRequestData.ItemEntry) it.next();
                if(badItems.containsKey(new Integer(itm.getLineNumber()))){
                    //ItemSubstitutionDefData subData = (ItemSubstitutionDefData) badItems.get(new Integer(itm.getLineNumber()));
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
                    StringBuffer dups = new StringBuffer();
                    dups.append(" Item ").append(itm.getCustomerSku()).append(" appeared multiple times, ");
                    dups.append("this was combined into one line item, and ").append(itm.getLineNumber());
                    dups.append(" was removed");
                    orderNote.append(dups);
                    substVector.add(dups.toString());
                }else{
                    custSkuMap.put(itm.getCustomerSku(),itm);
                }
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
            for(int ii=0; ii<substVector.size(); ii++) {
              String str = (String) substVector.get(ii);
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
    
    
    private class ProductContainer{
        String mSkuNum;
        String mUom;
        String mPack;
        String mShortDesc;
        double mAmount;
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
            if(mAmount < 0){
                return false;
            }
            return true;
        }
    }
}
