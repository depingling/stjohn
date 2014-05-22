/**
 *
 */
package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.view.utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.lang.Exception;

import org.apache.log4j.Logger;


/**
 * @author Deping
 *
 */
public class InboundBatchOrder extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
	private static final SimpleDateFormat custOrderDataFormat = new SimpleDateFormat("yyyyMMdd");
	List parsedObjects = new ArrayList();
	List errorEmailLogs = new ArrayList();  //error message list that going to be sent as email event
	
	public interface COLUMN {
        public static final int
                ACCOUNT_ID = 0,
                SITE_REF_NUM = 1,
                DIST_SKU = 2,
                QUANTITY = 3,
                PO_NUM = 4;
        
    }
	
	protected void processParsedObject(Object pParsedObject) throws Exception{
		InboundBatchOrderData parsedObj = (InboundBatchOrderData) pParsedObject;
		if (parsedObj.getAccountId()==0){
			appendErrorMsgs("Account Id expected on column " + (COLUMN.ACCOUNT_ID+1) + " of line#:" + getCurrentLineNumber());
		}
		if (!Utility.isSet(parsedObj.siteRefNum)){
			appendErrorMsgs("Site Ref Number expected on column "  + (COLUMN.SITE_REF_NUM +1) + " of line#: " + getCurrentLineNumber());
		}
		
		if (!Utility.isSet(parsedObj.distSku)){
			appendErrorMsgs("Dist Sku expected on column "  + (COLUMN.DIST_SKU +1) + " of line#: " + getCurrentLineNumber());
		}
		
		if (parsedObj.qty == 0){
			appendErrorMsgs("Quantity expected on column "  + (COLUMN.QUANTITY +1) + " of line#: " + getCurrentLineNumber());
		}

		
		parsedObjects.add(parsedObj);
	}
	protected void processUnparsableLine(Object pPartiallyParsedObject,List pParsedLine, String errorMessage){
		appendErrorMsgs("Line# " + (currentLineNumber+1) + ", " + errorMessage);
	}

	protected void doPostProcessing() throws Exception{
		/** Data are sorted by account id, site ref num. Multiple orders will be 
		 * placed for each site.  The loader will process the file as received, 
		 * so if all items for one site are not together in the file, multiple 
		 * orders will be processed
		 */ 
		if (getErrorMsgs().size()>0){
        	throw new RuntimeException(getFormatedErrorMsgs());
        }
		Connection conn = null;
		String selAccountId = "SELECT BUS_ENTITY_ID FROM CLW_BUS_ENTITY WHERE BUS_ENTITY_ID = ? AND BUS_ENTITY_STATUS_CD = 'ACTIVE'";
		String sql = "SELECT be.bus_entity_id, ca.catalog_id \n" +
				"FROM clw_bus_entity be, clw_bus_entity_assoc bea,  clw_property pr, clw_catalog_assoc ca \n" +
				"WHERE be.bus_entity_id = bea.bus_entity1_id \n" +
				"AND be.bus_entity_id = pr.bus_entity_id \n" +
				"AND pr.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "' \n" +
				"AND pr.clw_value = ? \n" +
				"AND be.bus_entity_status_cd = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' \n" +
				"AND bea.bus_entity2_id = ? \n" +
				"AND be.bus_entity_id = ca.bus_entity_id \n" +
				"ORDER BY bea.bus_entity1_id ";
		
		String date = custOrderDataFormat.format(new Date());

        try {
            conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(selAccountId);
            PreparedStatement pstmt1 = conn.prepareStatement(sql);
            List accountIdList = new ArrayList();
            List accountNotExistList = new ArrayList();
            Map siteMap = new HashMap();
        	Map shoppingCatalogMap = new HashMap();        	
            int orderCount = 0;
            String preSiteKey = null;
            
            // get list of account ids that associated with the trading partner and use it to validate input account id
            List acctIdsAssocWithTradingPartner = new ArrayList();
            int[] accountIds = getTranslator().getTradingPartnerBusEntityIds(getTranslator().getProfile().getTradingPartnerId(),
    				RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
            for (int accountId : accountIds){
            	acctIdsAssocWithTradingPartner.add(accountId);
            }
            
            Map<String, InboundBatchOrderData> orderItems = null;			
            List<Map<String, InboundBatchOrderData>> orders = new ArrayList<Map<String, InboundBatchOrderData>>();
            
            Iterator it = parsedObjects.iterator();            
    		while(it.hasNext()){
    			InboundBatchOrderData flat =(InboundBatchOrderData) it.next();
    			if (accountNotExistList.contains(flat.getAccountId())){
	    			continue;
	    		}
    			if (!accountIdList.contains(flat.getAccountId())){
    				if (!acctIdsAssocWithTradingPartner.contains(flat.getAccountId())){
    					appendErrorMsgs("Account Id (" + flat.getAccountId() + ") is not configured for trading partner: " + getTranslator().getPartner().getShortDesc());
    				}
	    			pstmt.setInt(1,flat.getAccountId());
	    			ResultSet rs = pstmt.executeQuery();
	    			if (!rs.next()){
	    				accountNotExistList.add(flat.getAccountId());
	    				log.info("None active account found for account id: " + flat.getAccountId());
	    				continue;
	    			}
	    			accountIdList.add(flat.getAccountId());
	    		}
    			String siteKey = getSiteKey(flat.getAccountId(), flat.getSiteRefNum(), flat.getPoNum());
    			if (!siteMap.containsKey(siteKey)){
	    			pstmt1.setString(1,flat.getSiteRefNum());
	    			pstmt1.setInt(2,flat.getAccountId());
	    			ResultSet rs = pstmt1.executeQuery();
	    			if (rs.next()){
	    				int siteId = rs.getInt(1);
	    				int shoppingCatalogId = rs.getInt(2);
	    				if (rs.next()){
	    					appendErrorMsgsLogs(flat, "Multiple sites matched");
	    				}else{
	    					siteMap.put(siteKey, siteId);
	    					shoppingCatalogMap.put(siteKey, shoppingCatalogId);
	    				}
	    			}else{
	    				appendErrorMsgsLogs(flat, "Failed to find active site");
	    			}   
    			}
    			if (siteMap.containsKey(siteKey)){
    				if (setItemInfo(conn, ((Integer)shoppingCatalogMap.get(siteKey)).intValue(), flat)){
	    				if (!siteKey.equals(preSiteKey)){
							preSiteKey = siteKey;
							orderItems = new HashMap<String, InboundBatchOrderData>();
							orders.add(orderItems);
	    				}
	    				InboundBatchOrderData existItem = orderItems.get(flat.getDistSku());
						if (existItem != null){ // consolidate the same items in a order
							existItem.qty += flat.qty;
						}else{
							orderItems.put(flat.distSku, flat);
						}
    				}
    			}
    		}            		
    		pstmt.close();
    		pstmt1.close();
    		if (getErrorMsgs().size()>0){
            	throw new RuntimeException(getFormatedErrorMsgs());
            }
	        
			OrderRequestData order = null;
			it = orders.iterator();
			getReqInterchange().getTransactionDataVector().clear(); // remove previous inserted Transaction data
			while(it.hasNext()){
				Object[] items = ((Map<String, InboundBatchOrderData>) it.next()).values().toArray();
									
				for(int lineNum = 1; lineNum <= items.length; lineNum++){				
					InboundBatchOrderData flat = (InboundBatchOrderData) items[lineNum-1];
					String siteKey = getSiteKey(flat.getAccountId(), flat.getSiteRefNum(), flat.getPoNum());
					
					if (lineNum == 1){
						Integer siteId = (Integer) siteMap.get(siteKey);
						createTransactionObject();
						order = OrderRequestData.createValue();
						order.setAccountId(flat.getAccountId());
						order.setSiteId(siteId.intValue());
						order.setCustomerPoNumber(flat.getPoNum());
						order.setCustomerOrderDate(date);
						order.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.EDI_850);
						order.setSkuTypeCd(getTranslator().getPartner().getSkuTypeCd());
						order.setTradingPartnerId(this.getTranslator().getTradingPartnerDescView().getTradingPartnerData().getTradingPartnerId());
						order.setIncomingProfileId(this.getTranslator().getTradingPartnerDescView().getTradingProfileData().getTradingProfileId());
						order.setOrderType(RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER);
						order.setBypassCustomerWorkflow(true);
			    		
						super.addIntegrationRequest(order);
						getTransactionObject().setKeyString("AccountId:SiteRefNum= "+siteKey);
					}
					order.addItemEntry(lineNum, flat.getDistSku(), flat.getQty(), flat.price, null, null, "");
				}
			}
			
			if (errorEmailLogs.size() > 0){
				String exceptionEmailTo = ((InboundBatchOrderData)parsedObjects.get(0)).sendExceptionEmailTo;
				if (!Utility.isSet(exceptionEmailTo)){
					throw new RuntimeException("Exception Email Address need to be configured through Trading Partner Config Mapping");
				}
				generateEmailEvent(exceptionEmailTo);
			}
        } finally {
            closeConnection(conn);
        }
    }
	
	// generate Email event if site not found or item is not in shopping catalog
	private void generateEmailEvent(String exceptionEmailTo) throws Exception {
		if (errorEmailLogs.size() == 0)
			return;
		String message = "";
		String user = "InboundBatchOrder";
		for (Object error : errorEmailLogs){
			message += error;
		}
			
		Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
		EventData eventData = EventData.createValue();
        eventData.setStatus(Event.STATUS_READY);
        eventData.setType(Event.TYPE_EMAIL);
        eventData.setAttempt(1);
        
        EventEmailDataView eventEmailData = new EventEmailDataView();
        eventEmailData.setEventEmailId(0);
        eventEmailData.setToAddress(exceptionEmailTo);//distribution.webmaster@ipaper.com
        eventEmailData.setFromAddress(Constants.EMAIL_ADDR_NO_REPLY);
        eventEmailData.setSubject("Batch Order Errors");
        eventEmailData.setText(message);
        eventEmailData.setEventId(eventData.getEventId());
        eventEmailData.setEmailStatusCd(Event.STATUS_READY);
        eventEmailData.setModBy(user);
        eventEmailData.setAddBy(user);
        
        EventEmailView eev = new EventEmailView(eventData, eventEmailData); 
        eventEjb.addEventEmail(eev, user);	
	}
	
	// set item_id and price to object InboundBatchOrderData if item is found in shopping catalog
	private boolean setItemInfo(Connection conn, int shoppingCatalogId, InboundBatchOrderData flat) throws Exception {
		boolean itemInCatalog = true;
		String sql = "SELECT im.ITEM_ID, ci.AMOUNT \n" +
				"FROM CLW_ITEM i, CLW_ITEM_MAPPING im, CLW_CATALOG_STRUCTURE cs, CLW_CONTRACT c, CLW_CONTRACT_ITEM ci \n" +
				"WHERE im.ITEM_ID = i.ITEM_ID \n" +
				"AND ITEM_NUM = ? \n" +
				"AND ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "' \n" +
				"AND im.ITEM_ID = cs.ITEM_ID \n" +
				"AND cs.CATALOG_ID = ? \n" +
				"AND c.CATALOG_ID = CS.CATALOG_ID \n" +
				"AND C.CONTRACT_ID = CI.CONTRACT_ID \n" +
				"AND CI.ITEM_ID = im.ITEM_ID \n" +
				"AND im.BUS_ENTITY_ID = cs.BUS_ENTITY_ID";
		
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, flat.getDistSku());
        pstmt.setInt(2, shoppingCatalogId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
        	flat.itemId = rs.getInt(1);
        	flat.price = rs.getDouble(2);
        }else{
        	appendErrorMsgsLogs(flat, "Item not in shopping catalog");
        	itemInCatalog = false;
        }
        pstmt.close();
        return itemInCatalog;
	}

	// add error message that going to be sent as email event
	private void appendErrorMsgsLogs(InboundBatchOrderData flat, String message) {
		String errMsg = "";
		if (errorEmailLogs.size() == 0){// create header
			errMsg = Utility.padRight("Account Id", ' ', 12);
			errMsg += Utility.padRight("Site Budget Ref Number", ' ', 25);
			errMsg += Utility.padRight("Dist Sku", ' ', 10);
			errMsg += Utility.padRight("Qty", ' ', 6);
			errMsg += "Error Message";			
			errorEmailLogs.add(errMsg+"\r\n");
		}
		errMsg = Utility.padRight(""+flat.getAccountId(), ' ', 12);
		errMsg += Utility.padRight(flat.getSiteRefNum(), ' ', 25);
		errMsg += Utility.padRight(flat.getDistSku(), ' ', 10);
		errMsg += Utility.padRight(""+flat.getQty(), ' ', 6);
		errMsg += message;
		log.info(errMsg);	
		errorEmailLogs.add(errMsg+"\r\n");
		getErrorMsgs().add(errMsg);		
	}

	private String getSiteKey(int accountId, String siteRefNum, String poNum){
		if (Utility.isSet(poNum))
			return accountId+":"+ siteRefNum + ":" + poNum;
		else
			return accountId+":"+ siteRefNum;
	}

	public static class InboundBatchOrderData implements Serializable{
		private int accountId;
		private String siteRefNum;
		private String poNum;		
		private String distSku;
		private int qty;
		private String sendExceptionEmailTo;
		int itemId=0;
		double price;
		public String toString(){
			return "InboundBatchOrderData: accountId="+	accountId + ", siteRefNum=" + siteRefNum + 
				", poNum=" + poNum + ", distSku=" + distSku + ", qty="+ qty + ", itemId=" + itemId + 
				", price=" + price;
		}
		public void setAccountId(int accountId) {
			this.accountId = accountId;
		}
		public int getAccountId() {
			return accountId;
		}
		public void setSiteRefNum(String siteRefNum) {
			this.siteRefNum = siteRefNum;
		}
		public String getSiteRefNum() {
			return siteRefNum;
		}
		public void setPoNum(String poNum) {
			this.poNum = poNum;
		}
		public String getPoNum() {
			return poNum;
		}
		public void setDistSku(String distSku) {
			this.distSku = distSku;
		}
		public String getDistSku() {
			return distSku;
		}
		public void setQty(int qty) {
			this.qty = qty;
		}
		public int getQty() {
			return qty;
		}
		public void setSendExceptionEmailTo(String sendExceptionEmailTo) {
			this.sendExceptionEmailTo = sendExceptionEmailTo;
		}
		public String getSendExceptionEmailTo() {
			return sendExceptionEmailTo;
		}
	}

}
