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
import java.math.BigDecimal;
import java.util.Iterator;
/**
 *
 * @author  bstevens
 */
public class InboundcXMLPunchOutOrder extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private OrderRequestData mCurrOrd;
    private static final int TYPE_EDIT = 1;
    private static final int TYPE_NEW = 2;
    private static final int ADDRESS_TYPE_BILL = 1;
    private static final int ADDRESS_TYPE_SHIP = 2;
            
    public void translate(Node nodeToOperateOn) throws Exception {
        
        try{
            LdapItemData lUserInfo = xmlHandler.parseLdapItem(nodeToOperateOn);
            if(lUserInfo == null){
                return;
            }
            
            Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/OrderRequest");
            if(requestNode == null){
            	appendErrorMsgs("Could not parse request node", true);
                return;
            }
            Node headerFromIdNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Header/From/Credential/Identity");
            if(headerFromIdNode == null){
            	appendErrorMsgs("Could not parse header from identiy node", true);
                return;
            }
            String userName = headerFromIdNode.getText();
            parseOrder(requestNode,userName);            
            
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
        /*
         *First parse out the header which will look like this:
         <OrderRequestHeader orderID="000000000000031" orderDate="2003-08-22T11:50:13" type="new">
         <Total>
                        <Money currency="USD">10.42</Money>
                </Total>
        <ShipTo>
                <Address>
                        <Name xml:lang="en"> Hotels </Name>
                        <PostalAddress>
                                <DeliverTo>User Name At Birch Street</DeliverTo>
                                <Street>1234 Test Street</Street>
                                <Street>Suite 650</Street>
                                <City>Washington</City>
                                <State>DC</State>
                                <PostalCode>20007</PostalCode>
                                <Country isoCountryCode="US">US</Country>
                        </PostalAddress>
                        <Email>User Email@domain.com</Email>
                        <Phone name="work">
                                <TelephoneNumber>
                                        <CountryCode isoCountryCode="US"></CountryCode>
                                        <AreaOrCityCode>222</AreaOrCityCode>
                                        <Number>2952262</Number>
                                </TelephoneNumber>
                        </Phone>
                </Address>
         </ShipTo>
         <!--There may or may not be a billing address as well-->
         <Shipping>
                <Money currency="USD">0</Money>
                <Description xml:lang="en"></Description>
        </Shipping>
        <Tax>
                <Money currency="USD">0</Money>
                <Description xml:lang="en"></Description>
        </Tax>
         
         */
        Node orderRequestHeader = pOrderRequestNode.selectSingleNode("OrderRequestHeader");
        String custPoNum = orderRequestHeader.valueOf("@orderID");
        String origOrdDate = orderRequestHeader.valueOf("@orderDate");
        String type = orderRequestHeader.valueOf("@type");  //new = new order, edit = new order and send a cancellation of the old
        
        int operationType = -1;
        if("new".equalsIgnoreCase(type)){
            operationType = TYPE_NEW;
        }else{
        	throw new Exception("OrderRequest type is not 'New' ");
        }
        //}else if("edit".equalsIgnoreCase(type)){
        //    operationType = TYPE_NEW;
        //} 
        
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
        
        //we ignore the following 2 elements of the order header:
        /*
         <Shipping>
                <Money currency="USD">0</Money>
                <Description xml:lang="en"></Description>
        </Shipping>
        <Tax>
                <Money currency="USD">0</Money>
                <Description xml:lang="en"></Description>
        </Tax>
         *It is not clear what we would do with these, so just ignore them
         */
        
        
        
        Iterator itemsIt = pOrderRequestNode.selectNodes("ItemOut").iterator();
        while(itemsIt.hasNext()){
            Node itemNode = (Node) itemsIt.next();
            parseItem(itemNode);
        }
        
        
        //setup the order request
        mCurrOrd.setOrderBillAddress(billTo);
        mCurrOrd.setOrderShipAddress(shipTo);
        mCurrOrd.setCustomerPoNumber(custPoNum);
        mCurrOrd.setCustomerOrderDate(origOrdDate);
        
        int[] accountIds = getTranslator().getTradingPartnerBusEntityIds(getTranslator().getProfile().getTradingPartnerId(),
				RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
        if(accountIds.length==1) {
            mCurrOrd.setAccountId(accountIds[0]);
        }
        mCurrOrd.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
        mCurrOrd.setTradingPartnerId(getTranslator().getPartner().getTradingPartnerId());
        mCurrOrd.setIncomingProfileId(getTranslator().getProfile().getTradingProfileId());
                
        log.info("adding order to integration requests vector!");
        xmlHandler.appendIntegrationRequest(mCurrOrd);
        getTransactionD().setKeyString("CustomerPoNumber: " + mCurrOrd.getCustomerPoNumber());
    }
    
    
    /**
     *Parse out and adds to the current order request an item from soemthing that looks like:
     * <ItemOut quantity="1" lineNumber="1" requestedDeliveryDate="2003-08-22T12:00:00">
     * <ItemID>
     * <SupplierPartID>111110175523  S</SupplierPartID>
     * <SupplierPartAuxiliaryID>22221220614</SupplierPartAuxiliaryID>
     * </ItemID>
     * <ItemDetail>
     * <UnitPrice>
     * <Money currency="USD">10.42</Money>
     * </UnitPrice>
     * <Description xml:lang="en">Oxford Cloth Buttondown Shirt  - Light Blue</Description>
     * <UnitOfMeasure>EA</UnitOfMeasure>
     * <Classification domain=""></Classification>
     * </ItemDetail>
     * <Shipping>
     * <Money currency="USD">0</Money>
     * <Description xml:lang="en"></Description>
     * </Shipping>
     * </ItemOut>
     */
    private void parseItem(Node pItemNode) throws Exception{
        String qtyS = pItemNode.valueOf("@quantity");
        String custLineS = pItemNode.valueOf("@lineNumber");
        String requestedDeliveryDateS = pItemNode.valueOf("@requestedDeliveryDate");
        String skuNum = pItemNode.selectSingleNode("ItemID/SupplierPartID").getText();
        
        Node itemDet = pItemNode.selectSingleNode("ItemDetail");
        String priceS = itemDet.selectSingleNode("UnitPrice/Money").getText();
        String desc = itemDet.selectSingleNode("Description").getText();
        String uom = itemDet.selectSingleNode("UnitOfMeasure").getText();
        //ignore the line item shipping. This is not supported so we would not send it
        String lineItemMon = pItemNode.selectSingleNode("Shipping/Money").getText();
        if(Utility.isSet(lineItemMon) && !lineItemMon.equals("0")){
            throw new Exception("Line item shipping amount was set and is not supported!!!");
        }
        
        try{
            int qty = Integer.parseInt(qtyS);
            int custLine = Integer.parseInt(custLineS);
            double price = Double.parseDouble(priceS);
            mCurrOrd.addItemEntry(custLine,skuNum, qty, price, uom, desc, null);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Something was unexpectedly non-numeric (qty,line, or price) for supplier part: "+skuNum);
        }
        
    }
    
    
    /**
     *Parse out an Order Address from soemthing that looks like:
     * <Address>
     * <Name xml:lang="en"> Hotels </Name>
     * <PostalAddress>
     * <DeliverTo>User Name At Birch Street</DeliverTo>
     * <Street>1234 Test Street</Street>
     * <Street>Suite 650</Street>
     * <City>Washington</City>
     * <State>DC</State>
     * <PostalCode>20007</PostalCode>
     * <Country isoCountryCode="US">US</Country>
     * </PostalAddress>
     * <Email>User Email@domain.com</Email>
     * <Phone name="work">
     * <TelephoneNumber>
     * <CountryCode isoCountryCode="US"></CountryCode>
     * <AreaOrCityCode>222</AreaOrCityCode>
     * <Number>2952262</Number>
     * </TelephoneNumber>
     * </Phone>
     * </Address>
     */
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
        }else{
            deliverToStr = deliverTo.getText();
        }
        if(ADDRESS_TYPE_SHIP == addressType){
            mCurrOrd.setCustomerComments("Attn: "+deliverToStr);
        }else{
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
        
        log.info(pAddrNode.asXML());
        
        //now get the phone number
        Node phoneNode = pAddrNode.selectSingleNode("Address/Phone");
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
        
        return addr;
        
    }
    
}
