/*
 * InboundcXMLOrder.java
 *
 * Created on July 14, 2004, 5:07 PM
 */

package com.cleanwise.service.apps.dataexchange;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CustomerOrderChangeRequestData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemChangeRequestData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.SiteData;
/**
 *
 * @author  bstevens
 */
public class InboundcXMLOrder extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private OrderRequestData mCurrOrd;
    private CustomerOrderChangeRequestData mChangeOrd;
    private Collection mChangeOrderItems;
    private OrderItemChangeRequestData mChangeItem;
    
	private BigDecimal totalItemsTax = new BigDecimal(0.0);
    private static final int TYPE_UPDATE = 1;
    private static final int TYPE_NEW = 2;
    private static final int ADDRESS_TYPE_BILL = 1;
    private static final int ADDRESS_TYPE_SHIP = 2;
            
    protected void checkSecurity(Node credentialNode) throws Exception {
        if (credentialNode == null) {
        	throw new Exception("Could not parse cXML (Credential)");
        }
        Node passNameNode = credentialNode.selectSingleNode("SharedSecret");
        if (passNameNode == null) {
        	throw new Exception("Could not parse cXML (SharedSecret)");
        }
        String pass = passNameNode.getText();
        String passval = mPassword;
        if (!pass.equalsIgnoreCase(passval)) {
        	throw new Exception("Authorization failed.  Check username and trading profile authorization setup.");
        }
    }
    
    public void translate(Node nodeToOperateOn) throws Exception {
        try{
        	
        	//check security info
        	Node credentialNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Header/Sender/Credential");
        	checkSecurity(credentialNode);
        	
        	Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/OrderRequest");
            parseOrder(requestNode,"InboundcXMLOrder");            
            
            //create the response code
            Document cxmlResp = xmlHandler.createPositiveResponse();
            //log our response
            OutputStream out = getTranslator().getOutputResponseStream(null);
            InboundXMLHandler.writeCXMLToResponse(out,cxmlResp);
            log.info("Responding with: "+cxmlResp.asXML());
        }catch(Exception e){
        	e.printStackTrace();
            appendErrorMsgs(e, true);
        }

    }
    

	/**
    * Sub classes can override this method in order to provide post-processing of site reference logic that is
	* specific to their implementation.  For example finding a site directly, or any string manipulation
	* @Returns the post processed site reference number
	*/
    protected String processAddressId(String refSiteId){
       return refSiteId;
	}


    /**
     *Parses out the order into an order request
     */
    private void parseOrder(Node pOrderRequestNode, String pUserName) throws Exception{
        
        Node orderRequestHeader = pOrderRequestNode.selectSingleNode("OrderRequestHeader");
        String custPoNum = orderRequestHeader.valueOf("@orderID");
        String origOrdDate = orderRequestHeader.valueOf("@orderDate");
        String type = orderRequestHeader.valueOf("@type");  //new = new order, edit = new order and send a cancellation of the old
        
        int orderType = -1;
        if("new".equalsIgnoreCase(type)){
        	orderType = TYPE_NEW;
        }else if("update".equalsIgnoreCase(type)){
        	orderType = TYPE_UPDATE;
        }else{
        	throw new Exception("OrderRequest type is not set. It should be of type 'new' or 'update'.");
        }
        
        //Tax
        BigDecimal totalTaxAmt = new BigDecimal(0.0);
        boolean isTaxable = false;
        Iterator extrinsicIt = orderRequestHeader.selectNodes("Extrinsic").iterator();
        while(extrinsicIt.hasNext()){
        	Node exNode = (Node) extrinsicIt.next();
        	String isTaxS = exNode.valueOf("@name");
        	if(isTaxS.equalsIgnoreCase("Taxable")){
        		if("Y".equals(exNode.getText())){
        			isTaxable = true;
        		}
        	}
        }
        if(isTaxable){
        	Node taxMoneyNode = orderRequestHeader.selectSingleNode("Tax/Money");
        	String totalTaxAmtS = taxMoneyNode.getText();
        	if(totalTaxAmtS!=null){
        		totalTaxAmt = new BigDecimal(totalTaxAmtS);
        	}else{
        		throw new Exception("Tax amount not found.");
        	}
        }
        
        Node moneyNode = orderRequestHeader.selectSingleNode("Total/Money");
        String orderTotStr = moneyNode.getText();
        BigDecimal orderTotal = new BigDecimal(0);
        if(Utility.isSet(orderTotStr)){
            orderTotal = new BigDecimal(orderTotStr);
        }     

        BigDecimal taxRate = new BigDecimal(0);
        if(isTaxable && orderTotal.compareTo(new BigDecimal(0))>0){
        	taxRate = totalTaxAmt.divide(orderTotal,5,BigDecimal.ROUND_HALF_UP);
        }
        
        //site
        Node addrNode = orderRequestHeader.selectSingleNode("ShipTo/Address");
        String refCode = addrNode.valueOf("@addressID");
		log.info("addrNode===>"+addrNode);
		if(!Utility.isSet(refCode)){refCode = addrNode.valueOf("@addressid");}
        refCode = processAddressId(refCode);
        // account
        int[] accountIds = getTranslator().getTradingPartnerBusEntityIds(getTranslator().getProfile().getTradingPartnerId(),
				RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
        
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        SiteData site = null;
        IdVector siteIds = new IdVector();
        for (int i = 0; i < accountIds.length; i++){
	        siteIds.addAll(siteEjb.getSiteIdsByProperty(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER, refCode, accountIds[i] ));
        }
        
        if(siteIds.size()==0){
        	//String note ="ShipTo "+refCode+" not found";
        	//mCurrOrd.addArbitraryOrderProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE, note);
        }else if (siteIds.size()>1){
        	throw new Exception("Multiple ShipTo "+refCode+" found within associated accounts for trading partner (" + getTranslator().getPartner().getShortDesc() + ")");
        }else{        	
        	site = siteEjb.getSite((Integer)siteIds.get(0));
        }
        IntegrationServices intServicesEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        
        // items
        Iterator itemsIt = pOrderRequestNode.selectNodes("ItemOut").iterator();
        
        //user
        Node userNode = addrNode.selectSingleNode("Name");
        if(userNode!=null){    	
        	pUserName = userNode.getText();
        }
        	
        if(orderType == TYPE_NEW){
        	
        	mCurrOrd = OrderRequestData.createValue();
        	if(siteIds.size()==0){
            	String note ="ShipTo "+refCode+" not found";
            	mCurrOrd.addArbitraryOrderProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE, note);
            }
        	if(site!=null){
	        	mCurrOrd.setAccountId(site.getAccountId());
	        	mCurrOrd.setSiteId(site.getSiteId());
	        	mCurrOrd.setSiteName(site.getBusEntity().getShortDesc());
	        }
            mCurrOrd.setUserNameKey(pUserName);
            
            OrderAddressData shipTo = parseAddress(orderRequestHeader.selectSingleNode("ShipTo"),ADDRESS_TYPE_SHIP);
            OrderAddressData billTo = parseAddress(orderRequestHeader.selectSingleNode("BillTo"),ADDRESS_TYPE_BILL);
            shipTo.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
            billTo.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.BILLING);

            mCurrOrd.setOrderBillAddress(billTo);
            mCurrOrd.setOrderShipAddress(shipTo);
            mCurrOrd.setCustomerPoNumber(custPoNum);
            mCurrOrd.setCustomerOrderDate(origOrdDate);
            Node commentsNode = orderRequestHeader.selectSingleNode("Comments");
            if (commentsNode != null) {
                mCurrOrd.setCustomerComments(commentsNode.getText());
            }
            checkForUniqPoNum();
                 
            mCurrOrd.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
            mCurrOrd.setTradingPartnerId(getTranslator().getPartner().getTradingPartnerId());
            mCurrOrd.setIncomingProfileId(getTranslator().getProfile().getTradingProfileId());
            
            BigDecimal orderTotalByItems = new BigDecimal(0.0);
            while(itemsIt.hasNext()){
                Node itemNode = (Node) itemsIt.next();
                BigDecimal price = parseItem(itemNode,orderType,isTaxable,taxRate);
                orderTotalByItems = orderTotalByItems.add(price);
            }
           
            if(orderTotal.compareTo(new BigDecimal(0))>0){
            	if(totalItemsTax.compareTo(new BigDecimal(0.0))>0){
            		
                	orderTotalByItems = orderTotalByItems.add(totalItemsTax);
                	checkTaxTotal(totalTaxAmt);
                	checkOrderTotal(orderTotal, orderTotalByItems);
                	
            	}
            	
            }
            
            log.info("adding order to integration requests vector!");
            xmlHandler.appendIntegrationRequest(mCurrOrd);
            getTransactionD().setKeyString("CustomerPoNumber: " + mCurrOrd.getCustomerPoNumber());
        	
        }else if(orderType == TYPE_UPDATE){
        	
        	mChangeOrd = CustomerOrderChangeRequestData.createValue();
        	mChangeOrd.setOrderSource("InboundCXML");
        	//validate if order exists else throw error
        	
        	OrderData oData = intServicesEjb.getOrderByPONum(custPoNum);
        	int orderId = oData.getOrderId();
        	if(orderId > 0){
        		OrderData orderD = OrderData.createValue();
        		orderD.setOrderId(orderId);
        		if(accountIds.length==1) {
        			orderD.setAccountId(accountIds[0]);
                }
        		orderD.setRequestPoNum(custPoNum);
        		mChangeOrd.setOrderData(orderD);
        		
        		int orderSiteId = oData.getSiteId();
        		if(site!=null){
                	if(site.getSiteId()!=orderSiteId){
                		throw new Exception("ShipTo "+refCode+" not valid for order:"+custPoNum);
                	}               
                }else{
                	throw new Exception("ShipTo "+refCode+" not found");
                }
                
        		//items
        		BigDecimal orderTotalByItems = new BigDecimal(0.0);
        		mChangeOrderItems = new ArrayList<OrderItemChangeRequestData>();
        		while(itemsIt.hasNext()){
                    Node itemNode = (Node) itemsIt.next();
                    BigDecimal price = parseItem(itemNode, orderType,isTaxable,taxRate);   
                    orderTotalByItems = orderTotalByItems.add(price);
                    mChangeOrderItems.add(mChangeItem);
                }
        		mChangeOrd.setOrderItemChangeRequests(mChangeOrderItems);
        		mChangeOrd.setUserName("InboundcXMLOrder");
        		
        		if(orderTotal.compareTo(new BigDecimal(0))>0){
                	if(totalItemsTax.compareTo(new BigDecimal(0.0))>0){
                		
                    	orderTotalByItems = orderTotalByItems.add(totalItemsTax);
                    	checkTaxTotal(totalTaxAmt);
                    	checkOrderTotal(orderTotal, orderTotalByItems);
                    	
                	}
                	
                }
        		log.info("adding change order to integration requests vector!");
                xmlHandler.appendIntegrationRequest(mChangeOrd);
                getTransactionD().setKeyString("CustomerPoNumber: " + custPoNum);
        		
        	}else{
        		throw new Exception("Order not found for cust po num: "+custPoNum);
        	}
        }
    }
    
    protected String parseItemLineNumber(Node pItemNode) {
        String custLineS = pItemNode.valueOf("@lineNumber");
        return custLineS;
    }
    
    private BigDecimal parseItem(Node pItemNode, int pOrderType, boolean pIsTaxable, BigDecimal pTaxRate) 
    throws Exception{
        BigDecimal result = new BigDecimal(0.0);
        result = result.setScale(2,BigDecimal.ROUND_HALF_UP);
        String qtyS = pItemNode.valueOf("@quantity");
        String custLineS = parseItemLineNumber(pItemNode);
        String distSkuNum = pItemNode.selectSingleNode("ItemID/SupplierPartID").getText();

        Node itemDet = pItemNode.selectSingleNode("ItemDetail");
        String priceS = itemDet.selectSingleNode("UnitPrice/Money").getText();
        String desc = itemDet.selectSingleNode("Description").getText();
        String uom = itemDet.selectSingleNode("UnitOfMeasure").getText();

        BigDecimal taxAmt = new BigDecimal(0.0);
        
        String pTaxExemptS = "T";
        if(pIsTaxable){
        	pTaxExemptS = "F";
        	Node itemTax = null;
        	itemTax = pItemNode.selectSingleNode("Tax");
        	if(itemTax!=null){
        		taxAmt = new BigDecimal(itemTax.selectSingleNode("Money").getText());
        		totalItemsTax = totalItemsTax.add(taxAmt);
        	}
        }

        try{
            int qty = Integer.parseInt(qtyS);
			if(custLineS == null){custLineS="0";}
            int custLine = Integer.parseInt(custLineS);
            double price = Double.parseDouble(priceS);
            BigDecimal priceBD = new BigDecimal(priceS);
            BigDecimal lineTotal = priceBD.multiply(new BigDecimal(qty));
            
            if(pIsTaxable && taxAmt.compareTo(new BigDecimal(0.0))==0){
            	taxAmt = lineTotal.multiply(pTaxRate);
            	taxAmt = taxAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
            	    	
            }
            
            if(pOrderType==TYPE_NEW){
            	mCurrOrd.addItemEntry(custLine,distSkuNum, qty, price, uom, desc, null,taxAmt,pTaxExemptS);
                result = priceBD.multiply(new BigDecimal(qty));
 
            }else if(pOrderType==TYPE_UPDATE){
            	
            	OrderItemData oid = OrderItemData.createValue();
            	oid.setItemShortDesc(desc);
            	oid.setCustLineNum(custLine);
            	oid.setDistItemQuantity(qty);
            	oid.setTotalQuantityOrdered(qty);
            	oid.setCustContractPrice(new BigDecimal(priceS));
            	oid.setDistItemCost(new BigDecimal(priceS));
            	oid.setDistItemSkuNum(distSkuNum);
            	oid.setDistItemUom(uom);
            	if(pIsTaxable){
            		oid.setTaxExempt("false");
            		oid.setTaxAmount(taxAmt);
            		//BigDecimal taxRate = taxAmt.divide(new BigDecimal(priceS),2,BigDecimal.ROUND_HALF_UP);
            		oid.setTaxRate(pTaxRate);
            	}else{
            		oid.setTaxExempt("true");
            		oid.setTaxAmount(new BigDecimal(0));
            		oid.setTaxRate(new BigDecimal(0));
            	}
            	result = priceBD.multiply(new BigDecimal(qty));
            	mChangeItem = new OrderItemChangeRequestData(oid);
            }
            

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
            mCurrOrd.setOrderContactName(deliverToStr);
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
            //mCurrOrd.setOrderTelephoneNumber(phoneNum.toString());
            if(ADDRESS_TYPE_SHIP == addressType){
            	mCurrOrd.setOrderTelephoneNumber(phoneNum.toString());
            }
        }
        // email
        Node emailNode = pAddrNode.selectSingleNode("Address/Email");
        if (emailNode != null) {
            String email = emailNode.getText();
            addr.setEmailAddress(email);
            //mCurrOrd.setOrderEmail(email);
            if(ADDRESS_TYPE_SHIP == addressType){
            	mCurrOrd.setOrderEmail(email);
            }
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

    private void checkOrderTotal(BigDecimal pOrderTotal, BigDecimal pOrderTotalByItems) throws Exception{
        if (pOrderTotal.compareTo(pOrderTotalByItems)!=0) {
          //mCurrOrd.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
        	throw new Exception("Order total amount did not match line items total");
        }
    }
    
    private void checkTaxTotal(BigDecimal pOrderTaxTotal) throws Exception {
        if (pOrderTaxTotal.compareTo(totalItemsTax)!=0) {
        	throw new Exception("Order tax amount did not match items total tax");
        }
    }
}
