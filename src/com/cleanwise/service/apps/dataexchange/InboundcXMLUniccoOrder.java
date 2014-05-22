/*
 * InboundcXMLPunchOutLogon.java
 *
 * Created on July 14, 2004, 5:07 PM
 */

package com.cleanwise.service.apps.dataexchange;
import com.cleanwise.service.api.value.*;

import org.apache.log4j.Logger;

import org.dom4j.*;
import java.io.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.APIAccess;

import java.math.BigDecimal;
import java.util.Iterator;
/**
 *
 * @author  bstevens
 */
public class InboundcXMLUniccoOrder extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private OrderRequestData mCurrOrd;
    private static final int TYPE_EDIT = 1;
    private static final int TYPE_NEW = 2;
    private static final int ADDRESS_TYPE_BILL = 1;
    private static final int ADDRESS_TYPE_SHIP = 2;
            
    public void translate(Node nodeToOperateOn) throws Exception {
        try{
        	
        	Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/OrderRequest");
            parseOrder(requestNode,"InboundcXMLUniccoOrder");            
            
            //create the response code
            Document cxmlResp = xmlHandler.createPositiveResponse();
            //log our response
            OutputStream out = getTranslator().getOutputResponseStream(null);
            InboundXMLHandler.writeCXMLToResponse(out,cxmlResp);
            log.info("Responding with: "+cxmlResp.asXML());
        }catch(Exception e){
            appendErrorMsgs(e, true);
        }

    }
    
    /**
     *Parses out the order into an order request
     */
    private void parseOrder(Node pOrderRequestNode, String pUserName) throws Exception{
        mCurrOrd = OrderRequestData.createValue();
        mCurrOrd.setUserNameKey(pUserName);
        Node orderRequestHeader = pOrderRequestNode.selectSingleNode("OrderRequestHeader");
        String custPoNum = orderRequestHeader.valueOf("@orderID");
        String origOrdDate = orderRequestHeader.valueOf("@orderDate");
        String type = orderRequestHeader.valueOf("@type");  //new = new order, edit = new order and send a cancellation of the old
        
        int operationType = -1;
        if ("new".equalsIgnoreCase(type)) {
            operationType = TYPE_NEW;
        } else if("edit".equalsIgnoreCase(type)) {
            operationType = TYPE_EDIT;
        } 
        Node moneyNode = orderRequestHeader.selectSingleNode("Total/Money");
        String cur = moneyNode.valueOf("@currency");
        if(Utility.isSet(cur)){
            if(!"usd".equalsIgnoreCase(cur)){
                throw new Exception("Only supports USD as currency");
            }
        }
        String orderTotStr = moneyNode.getText();
        BigDecimal orderTotal = null;
        if(Utility.isSet(orderTotStr)){
            orderTotal = new BigDecimal(orderTotStr);
        }        
        OrderAddressData shipTo = parseAddress(orderRequestHeader.selectSingleNode("ShipTo"),ADDRESS_TYPE_SHIP);
        OrderAddressData billTo = parseAddress(orderRequestHeader.selectSingleNode("BillTo"),ADDRESS_TYPE_BILL);
        shipTo.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        billTo.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.BILLING);


        mCurrOrd.setOrderBillAddress(billTo);
        mCurrOrd.setOrderShipAddress(shipTo);
        mCurrOrd.setCustomerPoNumber(custPoNum);
        checkForUniqPoNum();

        mCurrOrd.setCustomerOrderDate(origOrdDate);

        // account
        int[] accountIds = getTranslator().getTradingPartnerBusEntityIds(getTranslator().getProfile().getTradingPartnerId(),
				RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
        if(accountIds.length>0) {
            mCurrOrd.setAccountId(accountIds[0]);
        }
        
        //site
        //String addrName = orderRequestHeader.selectSingleNode("ShipTo/Address/Name").getText();
        //String refCode = getBudgetRefCode(addrName);
        Node addrNode = orderRequestHeader.selectSingleNode("ShipTo/Address");
        String refCode = addrNode.valueOf("@addressID");
        SiteData site = getSiteByRefCode(refCode, mCurrOrd.getAccountId());
        if(site!=null){
        	mCurrOrd.setSiteId(site.getSiteId());
        	mCurrOrd.setSiteName(site.getBusEntity().getShortDesc());
        }else{
        	String note ="ShipTo "+refCode+" not found";
        	mCurrOrd.addArbitraryOrderProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE, note);
        }
        mCurrOrd.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
        mCurrOrd.setTradingPartnerId(getTranslator().getPartner().getTradingPartnerId());
        mCurrOrd.setIncomingProfileId(getTranslator().getProfile().getTradingProfileId());

        // items
        ItemInformation itemEjb = APIAccess.getAPIAccess().getItemInformationAPI();
        
        Iterator itemsIt = pOrderRequestNode.selectNodes("ItemOut").iterator();
        BigDecimal orderTotalByItems = new BigDecimal(0);
        while(itemsIt.hasNext()){
            Node itemNode = (Node) itemsIt.next();
            BigDecimal price = parseItem(itemNode,itemEjb);
            orderTotalByItems = orderTotalByItems.add(price);
        }
        checkOrderTotal(orderTotal, orderTotalByItems);



        log.info("adding order to integration requests vector!");
        xmlHandler.appendIntegrationRequest(mCurrOrd);
        getTransactionD().setKeyString("CustomerPoNumber: " + mCurrOrd.getCustomerPoNumber());
    }
    
    
    private BigDecimal parseItem(Node pItemNode, ItemInformation pItemEjb) throws Exception{
        BigDecimal result;
        String qtyS = pItemNode.valueOf("@quantity");
        String custLineS = pItemNode.valueOf("@lineNumber");
        String distSkuNum = pItemNode.selectSingleNode("ItemID/SupplierPartID").getText();

        Node itemDet = pItemNode.selectSingleNode("ItemDetail");
        String priceS = itemDet.selectSingleNode("UnitPrice/Money").getText();
        String desc = itemDet.selectSingleNode("Description").getText();
        String uom = itemDet.selectSingleNode("UnitOfMeasure").getText();


        try{
            int qty = Integer.parseInt(qtyS);
            int custLine = Integer.parseInt(custLineS);
            double price = Double.parseDouble(priceS);
            mCurrOrd.addItemEntry(custLine,distSkuNum, qty, price, uom, desc, null);
            result = new BigDecimal(price*qty);

        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Something was unexpectedly non-numeric (qty, line, or price) for supplier part: " + distSkuNum);
        }
        return result;
    }
    
    
    private OrderAddressData parseAddress(Node pAddrNode, int addressType){
        OrderAddressData addr = OrderAddressData.createValue();
        addr.setShortDesc(pAddrNode.selectSingleNode("Address/Name").getText());
        Node postalAddr = pAddrNode.selectSingleNode("Address/PostalAddress");
        Node deliverTo = pAddrNode.selectSingleNode("Address/PostalAddress/DeliverTo");
        if(deliverTo == null){
            deliverTo = pAddrNode.selectSingleNode("Address/DeliverTo");
        }
        String deliverToStr;
        if(deliverTo == null){
            deliverToStr = "";
        } else {
            deliverToStr = deliverTo.getText();
        }
        if(ADDRESS_TYPE_SHIP == addressType){
            mCurrOrd.setCustomerComments("Attn: "+deliverToStr);
        } else {
            mCurrOrd.setOrderContactName("Bill Contact: " + deliverToStr);
        }
        Iterator it = postalAddr.selectNodes("Street").iterator();
        int ct = 0;
        while(it.hasNext() && ct < 4){
            ct++;
            Node streetNode = (Node) it.next();
            switch (ct){
                case 1:{
                    addr.setAddress1(streetNode.getText());
                    break;
                }case 2:{
                    addr.setAddress2(streetNode.getText());
                    break;
                }case 3:{
                    addr.setAddress3(streetNode.getText());
                    break;
                }case 4:{
                    addr.setAddress4(streetNode.getText());
                    break;
                }
            }
        }
        addr.setCity(postalAddr.selectSingleNode("City").getText());
        addr.setStateProvinceCd(postalAddr.selectSingleNode("State").getText());
        addr.setPostalCode(postalAddr.selectSingleNode("PostalCode").getText());
        Node countryNode = postalAddr.selectSingleNode("CountryCode");
        if(countryNode != null){
            addr.setCountryCd(countryNode.valueOf("@isoCountryCode"));
        }

        // phone number
        Node phoneNode = pAddrNode.selectSingleNode("Address/Phone/TelephoneNumber");
        if (phoneNode != null) {
            StringBuffer phoneNum = new StringBuffer();
            Node scratch = phoneNode.selectSingleNode("AreaOrCityCode");
            if(scratch != null){
                phoneNum.append(scratch.getText());
                phoneNum.append("-");
            }
            scratch = phoneNode.selectSingleNode("Number");
            if(scratch != null){
                phoneNum.append(scratch.getText());
            }
            addr.setPhoneNum(phoneNum.toString());
            mCurrOrd.setOrderTelephoneNumber(phoneNum.toString());
        }
        // email
        Node emailNode = pAddrNode.selectSingleNode("Address/Email");
        if (emailNode != null) {
            String email = emailNode.getText();
            addr.setEmailAddress(email);
            mCurrOrd.setOrderEmail(email);
        }

        return addr;
        
    }

    private void checkForUniqPoNum() throws Exception {
        IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        boolean uniq = isEjb.isUniqCustomerPoNumber(mCurrOrd.getCustomerPoNumber());
        if (!uniq) {
            mCurrOrd.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.DUPLICATED);
        }
    }

    private void checkOrderTotal(BigDecimal pOrderTotal, BigDecimal pOrderTotalByItems) {
        if (pOrderTotal != pOrderTotalByItems) {
          mCurrOrd.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
          // add notes
        }
    }

    private String getBudgetRefCode(String pSiteName) {
        String result = "";
        int n = pSiteName.indexOf("-");
        if ( n != 0 ) {
            result = pSiteName.substring(0, n);
        }
        return result;
    }

    private SiteData getSiteByRefCode(String pBudgetRefCode, int pAccountId) throws Exception {
        SiteData result = null;
        if (pBudgetRefCode == null) {
            return SiteData.createValue();
        }
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        SiteDataVector sites = siteEjb.getSiteByProperty(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER, pBudgetRefCode, pAccountId );
        if (sites.size() == 1) {
            result = (SiteData)sites.get(0);
        }
        return result;
    }

    
}
