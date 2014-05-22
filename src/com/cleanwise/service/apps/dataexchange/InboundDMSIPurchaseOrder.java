/**
 *
 */
package com.cleanwise.service.apps.dataexchange;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.math.BigDecimal;
import java.io.Serializable;
import java.lang.Exception;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;


/**
 * @author Ssharma
 *
 */
public class InboundDMSIPurchaseOrder extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
	//"com.cleanwise.service.apps.dataexchange.InboundDMSIPurchaseOrder$InboundFileData"
	HashMap theMap = new HashMap();
	ArrayList parsedObjects = new ArrayList();
	int siteId = 0;
	int accId = 0;
	private static SimpleDateFormat poDateFormat = new SimpleDateFormat(
	"MM/dd/yyyy");


	protected void addIntegrationRequest(Object pRequest){
		parsedObjects.add(pRequest);
	}


	protected void doPostProcessing(){

		//loop through

		OrderRequestData order = OrderRequestData.createValue();
		Iterator it = parsedObjects.iterator();
		while(it.hasNext()){
			InboundDMSIPurchaseOrderData flat =(InboundDMSIPurchaseOrderData) it.next();
			log.debug("DDDDDDDDDDDDDDDDDDDDDDDDDD "+flat.toString());
			addOrderItem(order,flat);
		}


		Iterator it1 = theMap.values().iterator();
        while(it1.hasNext()){
        	Object o = it1.next();
        	log.debug("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+o.getClass().getName());

            super.addIntegrationRequest(o);

        }
        getTransactionObject().setKeyString("PO Number: "+order.getCustomerPoNumber());
    }

	/**
     * Adds an item to the orderRequest
     */
    private void addOrderItem(OrderRequestData order,InboundDMSIPurchaseOrderData flatOrder){


    	String siteRefNum = flatOrder.getSiteRefNum();
    	log.info("siteRefNum= "+siteRefNum);

    	String vendorNum = flatOrder.getVendorNum(); // distCustRefCode
		String vendorName = flatOrder.getVendorName(); //distItemShortdesc
		String[] words = vendorName.split("\\s"); // get only first word
		String firstWord = words[0];
		log.info("firstWord= "+firstWord);
		String distId = "";
		try{

		DBCriteria crit = new DBCriteria();
		crit.addContainsIgnoreCase(BusEntityDataAccess.SHORT_DESC, firstWord);
		crit.addContainsIgnoreCase(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

		distId = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, crit);

		}catch(Exception ex){
			log.error(ex.toString(),ex);
		}

    	String key = flatOrder.getErpPoNum()+"::"+flatOrder.getVendorNum()+"::"+siteRefNum;

    	OrderRequestData aReq = (OrderRequestData) theMap.get(key);

    	if(aReq ==null){
    		//create new one and pop with header values
    		log.info("CREATING NEW");
    		aReq = OrderRequestData.createValue();
    		aReq.setSkuTypeCd(RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR);

    		aReq.setSiteName(siteRefNum);
    		aReq.setTradingPartnerId(this.getTranslator().getTradingPartnerDescView().getTradingPartnerData().getTradingPartnerId());
    		aReq.setIncomingProfileId(this.getTranslator().getTradingPartnerDescView().getTradingProfileData().getTradingProfileId());

    		OrderAddressData shipAdd = OrderAddressData.createValue();
    		shipAdd.setShortDesc(formatData(flatOrder.getShipToName()));
    		shipAdd.setAddress1(formatData(flatOrder.getAdd1()));
    		shipAdd.setAddress2(formatData(flatOrder.getAdd2()));
    		shipAdd.setAddress3(formatData(flatOrder.getAdd3()));
    		shipAdd.setCity(formatData(flatOrder.getCity()));
    		shipAdd.setStateProvinceCd(formatData(flatOrder.getState()));
    		shipAdd.setPostalCode(formatData(flatOrder.getZip()));

    		order.setOrderShipAddress(shipAdd);
    		aReq.setOrderShipAddress(shipAdd);

    		aReq.setCustomerPoNumber(flatOrder.getErpPoNum());
    		aReq.setCustomerOrderDate(flatOrder.getPoDate()); // revised order date

    		aReq.setCustomerComments(flatOrder.getDeliveryInst1()+" "+flatOrder.getDeliveryInst2());

    		aReq.setOrderContactName(flatOrder.getContactName());
    		aReq.setOrderTelephoneNumber(flatOrder.getContactNum());
    		aReq.setOrderEmail(flatOrder.getContactEmail());

    		/* Distributor validation (vendor num,vendor name) */
    		if(distId.trim().length() == 0){ // no distributor found
    			log.info("Distributor '"+vendorName+"' not found!");
    			aReq.setOrderStatusCdOveride(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
    		}

    		aReq.addItemEntry(flatOrder.getPoLineNum(), flatOrder.getVendorItemNum(),
    				flatOrder.getQty(), flatOrder.getCustPrice().doubleValue(),
    				flatOrder.getUom(), flatOrder.getItemName(), "");

    	}else{
    		aReq.addItemEntry(flatOrder.getPoLineNum(),flatOrder.getVendorItemNum() ,
    				flatOrder.getQty(), flatOrder.getCustPrice().doubleValue(),
    				flatOrder.getUom(), flatOrder.getItemName(), "");
    	}

    	log.info("######################## "+aReq);
    	theMap.put(key,aReq);
    }

    public String formatData(String val){

    	if(val==null || val.length()==0){
    		val = "";
    	}

    	return val;
    }

	public static class InboundDMSIPurchaseOrderData implements Serializable{
		String siteRefNum;
		String shipToName;
		String add1;
		String add2;
		String add3;
		String city;
		String state;
		String zip;
		String vendorNum;
		String vendorName;
		String erpPoNum;
		String poDate;
		int poLineNum;
		String vendorItemNum;
		String itemName;
		int qty;
		String uom;
		BigDecimal custPrice;
		BigDecimal totPrice;
		String deliveryInst1;
		String deliveryInst2;
		String userid;
		String contactName;
		String contactNum;
		String contactEmail;

		public String getSiteRefNum() {
			return siteRefNum;
		}
		public void setSiteRefNum(String siteRefNum) {
			this.siteRefNum = siteRefNum;
		}

		public String getShipToName() {
			return shipToName;
		}
		public void setShipToName(String shipToName) {
			this.shipToName = shipToName;
		}

		public String getAdd1() {
			return add1;
		}
		public void setAdd1(String add1) {
			this.add1 = add1;
		}

		public String getAdd2() {
			return add2;
		}
		public void setAdd2(String add2) {
			this.add2 = add2;
		}

		public String getAdd3() {
			return add3;
		}
		public void setAdd3(String add3) {
			this.add3 = add3;
		}

		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}

		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getVendorNum() {
			return vendorNum;
		}
		public void setVendorNum(String vendorNum) {
			this.vendorNum = vendorNum;
		}

		public String getVendorName() {
			return vendorName;
		}
		public void setVendorName(String vendorName) {
			this.vendorName = vendorName;
		}

		public String getVendorItemNum() {
			return vendorItemNum;
		}
		public void setVendorItemNum(String vendorItemNum) {
			this.vendorItemNum = vendorItemNum;
		}

		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public String getErpPoNum() {
			return erpPoNum;
		}
		public void setErpPoNum(String erpPoNum) {
			this.erpPoNum = erpPoNum;
		}

		public int getPoLineNum() {
			return poLineNum;
		}
		public void setPoLineNum(int poLineNum) {
			this.poLineNum = poLineNum;
		}

		public String getPoDate() {
			return poDate;
		}
		public void setPoDate(String poDate) {
			this.poDate = poDate;
		}

		public int getQty() {
			return qty;
		}
		public void setQty(int qty) {
			this.qty = qty;
		}

		public String getUom() {
			return uom;
		}
		public void setUom(String uom) {
			this.uom = uom;
		}

		public BigDecimal getCustPrice() {
			return custPrice;
		}
		public void setCustPrice(BigDecimal custPrice) {
			this.custPrice = custPrice;
		}

		public BigDecimal getTotPrice() {
			return totPrice;
		}
		public void setTotPrice(BigDecimal totPrice) {
			this.totPrice = totPrice;
		}

		public String getDeliveryInst1() {
			return deliveryInst1;
		}
		public void setDeliveryInst1(String deliveryInst1) {
			this.deliveryInst1 = deliveryInst1;
		}

		public String getDeliveryInst2() {
			return deliveryInst2;
		}
		public void setDeliveryInst2(String deliveryInst2) {
			this.deliveryInst2 = deliveryInst2;
		}

		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getContactName() {
			return contactName;
		}
		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getContactNum() {
			return contactNum;
		}
		public void setContactNum(String contactNum) {
			this.contactNum = contactNum;
		}

		public String getContactEmail() {
			return contactEmail;
		}
		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}

		public String toString(){
			return "InboundDMSIPurchaseOrderData: "+
			siteRefNum +
			',' + shipToName +
			',' + add1 +
			',' + add2 +
			',' + add3 +
			',' + city +
			',' + state +
			',' + zip +
			','+ vendorNum+
			',' + vendorName +
			',' +erpPoNum+
			',' +poDate+
			',' +poLineNum+
			','+ vendorItemNum+
			',' + itemName +
			','+ qty+
			',' +uom+
			',' +custPrice+
			',' +totPrice +
			',' + deliveryInst1 +
			',' + deliveryInst2 +
			',' + userid +
			',' + contactName +
			',' + contactNum +
			',' + contactEmail;
		}

	}

}
