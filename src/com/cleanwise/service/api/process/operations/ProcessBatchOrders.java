/**
 * 
 */
package com.cleanwise.service.api.process.operations;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
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
import java.util.Set;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.OrderRequestDataVector;
import com.cleanwise.service.apps.dataexchange.ExcelReader;
import com.cleanwise.service.apps.dataexchange.InboundFlatFile;
import com.espendwise.ocean.common.webaccess.ResponseError;

import org.apache.log4j.Logger;


/**
 * @author deping
 *
 */
public class ProcessBatchOrders extends InboundFlatFile {
	public interface COLUMN {
        public static final int
                SITE_REF_NUM = 0,
                CUST_SKU = 1,
                DIST_SKU = 2,
                QUANTITY = 3,
                PO_NUM = 4,
                DIST_UOM = 5,
                VERSION = 6,
                SITE_ID = 7,
                STORE_SKU = 8;        
    }
	protected Logger log = Logger.getLogger(this.getClass());	
	private static final SimpleDateFormat custOrderDataFormat = new SimpleDateFormat("yyyyMMdd");
	private List<InboundBatchOrderData> parsedObjects = new ArrayList<InboundBatchOrderData>();
	private Integer storeId = null;
	private Integer accountId = null;
	private Map<InboundBatchOrderData, String> lineNumberMap = new HashMap<InboundBatchOrderData, String>();
	private boolean isVersion1 = true;
	private Map<String, Map<String, InboundBatchOrderData>> orders = new HashMap<String, Map<String, InboundBatchOrderData>>();
	private List<String> qtyIgnoreList = new ArrayList<String>();
	
	public List validateBatchOrder(Integer storeId, Integer accountId, String fileName, byte[] dataContents) throws Exception {
		log.info("validateBatchOrder: Start");
		this.storeId = storeId;
		this.accountId = accountId;
		InboundBatchOrderData data = new InboundBatchOrderData();
		javaBeanToPopulate = data.getClass().getName();
		InputStream inputStream = null;
        
		try{
			inputStream = new BufferedInputStream(new ByteArrayInputStream((byte[])dataContents));
			if (inputStream.available() == 0){
				throw new Exception("Stream size is 0 - " + fileName);
	        }
	        
	        String streamType = getFileExtension(fileName);
	        boolean fileFormatIssue = streamType == null || (!streamType.equals("txt") && !streamType.equals("csv"));
        	
	        if (fileFormatIssue){
	    		String message = I18nUtil.getMessage("validation.web.batchOrder.error.mustBeCsvOrTxtFileFormat");
	    		appendErrors(message);
	        }
    			
	        Account accountEjb = APIAccess.getAPIAccess().getAccountAPI();
	        try {
	            AccountData accountD = accountEjb.getAccount(accountId, storeId);
	            if (accountD.getBusEntity().getBusEntityStatusCd().equals(RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE)){
	                appendErrors("validation.web.batchOrder.error.noneActiveAccountFoundForAccountId", accountId+"");
	            }
	        } catch (Exception e) {
	            appendErrors("validation.web.batchOrder.error.accountIdNotExistInPrimaryEntity", accountId+"", storeId+"");                
	        }
	        if (getErrorMsgs().size()>0){
	            return getErrorMsgs();
	        }
	        
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			translate((Reader)reader);
	        validateBusEntitiesAndItems();
		}catch(Exception e){
			appendErrors(e.getMessage());
		}finally {
			if (inputStream != null)
				inputStream.close();
		} 
		
		log.info("validateBatchOrder: End");
		return getErrorMsgs();
	}
	
	private void validateBusEntitiesAndItems() throws Exception {
		/** Batch Order rows should be grouped into orders by site budget reference number and by PO#, if PO# exists. 
		 *  One order for each unique combination of site budget reference number and PO#. 
		 *  If no PO# is present rows should be grouped into orders by location. One order per unique Site Budget Reference number.
		 *  Consolidate the same items in a order by item key 
		 */ 
		if (getErrorMsgs().size()>0){
        	return;
        }
		Connection conn = null;
		String selAccountId = "SELECT BUS_ENTITY_ID FROM CLW_BUS_ENTITY WHERE BUS_ENTITY_ID = ? AND BUS_ENTITY_STATUS_CD = 'ACTIVE'";
		String sql = "SELECT be.bus_entity_id, ca.catalog_id, bea.bus_entity2_id accountId \n" +
				"FROM clw_bus_entity be, clw_bus_entity_assoc bea, clw_catalog_assoc ca, clw_catalog c, clw_bus_entity_assoc asa" + (isVersion1? ", clw_property pr" : "") + " \n" +
				"WHERE asa.bus_entity1_id = bea.bus_entity2_id \n" +
				"AND asa.bus_entity_assoc_cd = 'ACCOUNT OF STORE' \n" +
				"AND asa.bus_entity2_id = ? \n" +
				"AND be.bus_entity_id = bea.bus_entity1_id \n" +
				"AND bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' \n" +
				"AND be.bus_entity_status_cd = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' \n" +
				"AND be.bus_entity_id = ca.bus_entity_id \n" +
				"AND ca.catalog_id = c.catalog_id \n" +
				"AND c.catalog_type_cd = 'SHOPPING'\n" +
				(isVersion1? 
						"AND bea.bus_entity2_id = ? \n" +						
						"AND be.bus_entity_id = pr.bus_entity_id \n" +
						"AND pr.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "' \n" +
						"AND pr.clw_value = ? \n" : "AND be.bus_entity_id = ? \n"
				) +
				"ORDER BY bea.bus_entity1_id ";
			
        try {
            conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(selAccountId);
            PreparedStatement pstmt1 = conn.prepareStatement(sql);
            Map siteMap = new HashMap();
        	Map shoppingCatalogMap = new HashMap();
            
            Map<String, InboundBatchOrderData> orderItems = null;
            
            Iterator it = parsedObjects.iterator();            
    		while(it.hasNext()){
    			InboundBatchOrderData flat =(InboundBatchOrderData) it.next();
    			if (flat.qty <= 0){
    			    getQtyIgnoreList().add(lineNumberMap.get(flat));
    			    continue;
    			}
    			
    			String siteKey = flat.getSiteKey();
    			if (!siteMap.containsKey(siteKey)){
    				pstmt1.setInt(1,storeId);
    				if (isVersion1){
    					pstmt1.setInt(2,accountId);
    					pstmt1.setString(3,flat.getSiteRefNum());    					
    				} else {
    					pstmt1.setInt(2,flat.getSiteId());
    				}
	    			ResultSet rs = pstmt1.executeQuery();
	    			if (rs.next()){
	    				int siteId = rs.getInt(1);
	    				int shoppingCatalogId = rs.getInt(2);
	    				int accountId = rs.getInt(3);
	    				if (rs.next()){
	    					appendErrors(flat, "validation.web.batchOrder.error.multipleLocationsMatched");
	    				}else{
	    					siteMap.put(siteKey, siteId);
	    					shoppingCatalogMap.put(siteKey, shoppingCatalogId);
	    				}
	    			}else{
	    				appendErrors(flat, "validation.web.batchOrder.error.failedToFindActiveLocation");
	    			}   
    			}
    			if (siteMap.containsKey(siteKey)){
    				if (setItemInfo(conn, ((Integer)shoppingCatalogMap.get(siteKey)).intValue(), flat)){
    				    orderItems = orders.get(siteKey);
	    				if (orderItems == null){
							orderItems = new HashMap<String, InboundBatchOrderData>();
							orders.put(siteKey, orderItems);
	    				}
	    				InboundBatchOrderData existItem = orderItems.get(flat.getItemKey());
						if (existItem != null){ // consolidate the same items in a order
							existItem.qty += flat.qty;
						}else{
							if (isVersion1)
								flat.siteId = (Integer)siteMap.get(siteKey);
							orderItems.put(flat.getItemKey(), flat);
						}
    				}
    			}
    		}            		
    		pstmt.close();
    		pstmt1.close();
        } finally {
            closeConnection(conn);
        }
	}
	
	public void process(Integer storeId, String fileName, String applyToBudget, String sendConfirmation, Object dataContents, Integer accountId) throws Exception {
		log.info("process: Start");
		validateBatchOrder(storeId, accountId, fileName, (byte[])dataContents);
		if (getErrorMsgs().size()>0){
        	throw new RuntimeException(getFormatedErrorMsgs());
        }
	    doPostProcessing(applyToBudget, sendConfirmation);		
		log.info("process: End");		
	}
		
	/**
     *Returns the extension of a file:
     *foo.bar returns bar
     *foobar returns foobar
     */
    private String getFileExtension(String pFileName){
        java.util.StringTokenizer tok = new java.util.StringTokenizer(pFileName,".");
        String suffix = null;
        while(tok.hasMoreTokens()){
            suffix = tok.nextToken();
        }
        return suffix;
    }
    
    /**
	 *passed in the parsed line will preform the necessary logic of populating the object
	 */
	public void parseLine(List pParsedLine) throws Exception{
		if(isEmpty(pParsedLine)){
			log.info("empty line");
			return;
		}
		if(currentLineNumber == 0){
			if (pParsedLine.size() < 4){
				throw new Exception("Data has less than 4 required columns");
			}
			currentLineNumber++;
			return;
		}else{
			parseDetailLine(pParsedLine);
		}
		currentLineNumber++;
	}
	
	protected void parseDetailLine(List pParsedLine) throws Exception{
		InboundBatchOrderData parsedObj = new InboundBatchOrderData();
		lineNumberMap.put(parsedObj, getCurrentLineNumber()+1+"");
		int columnCount = pParsedLine.size();
		if (getCurrentLineNumber() == 1){
			if (columnCount > COLUMN.VERSION && pParsedLine.get(COLUMN.VERSION) != null){
				String version = (String)pParsedLine.get(COLUMN.VERSION);
				if (version.equals("1"))
					isVersion1 = true;
				else if (version.equals("2"))
					isVersion1 = false;
				else{
					throw new Exception("Version Num (" + version + ") should be 1 or 2");
				}
			}
		}
				
		if (columnCount > COLUMN.SITE_REF_NUM && pParsedLine.get(COLUMN.SITE_REF_NUM) != null)
			parsedObj.setSiteRefNum((String)pParsedLine.get(COLUMN.SITE_REF_NUM));
		if (columnCount > COLUMN.CUST_SKU && pParsedLine.get(COLUMN.CUST_SKU) != null){
            parsedObj.setCustSku((String)pParsedLine.get(COLUMN.CUST_SKU));
        }
        if (columnCount > COLUMN.DIST_SKU && pParsedLine.get(COLUMN.DIST_SKU) != null)
			parsedObj.setDistSku((String)pParsedLine.get(COLUMN.DIST_SKU));
		if (columnCount > COLUMN.QUANTITY){
			if (pParsedLine.get(COLUMN.QUANTITY) == null){
				appendErrors(parsedObj, "validation.web.batchOrder.error.positiveQtyExpectedOnColumn", (COLUMN.QUANTITY +1));
			}else{
				try{
					parsedObj.setQty(new Integer((String)pParsedLine.get(COLUMN.QUANTITY)).intValue());
					/*if (parsedObj.qty <= 0){
						appendErrors(parsedObj, "validation.web.batchOrder.error.positiveQtyExpectedOnColumn", (COLUMN.QUANTITY +1));
					}*/
				}catch(Exception e){
					appendErrors(parsedObj, "validation.web.batchOrder.error.errorParsingQty", pParsedLine.get(COLUMN.QUANTITY));
					parsedObj.setQty(-1);
				}
			}
		}else{
			appendErrors(parsedObj, "validation.web.batchOrder.error.positiveQtyExpectedOnColumn", (COLUMN.QUANTITY +1));
		}
		
		if (columnCount > COLUMN.PO_NUM &&  pParsedLine.get(COLUMN.PO_NUM) != null){
			String poNum = (String) pParsedLine.get(COLUMN.PO_NUM); 
			if (Utility.isSet(poNum) && poNum.length() > 22){
				appendErrors(parsedObj, "validation.web.batchOrder.error.poNumShouldNotExceed22Characters", pParsedLine.get(COLUMN.PO_NUM));
			}else{
				parsedObj.setPoNum(((String)pParsedLine.get(COLUMN.PO_NUM)));
			}
		}
		
		if (columnCount > COLUMN.DIST_UOM &&  pParsedLine.get(COLUMN.DIST_UOM) != null)
			parsedObj.setDistUom(((String)pParsedLine.get(COLUMN.DIST_UOM)));
				
		if (columnCount > COLUMN.SITE_ID &&  pParsedLine.get(COLUMN.SITE_ID) != null){
			try{
				parsedObj.setSiteId(new Integer((String)pParsedLine.get(COLUMN.SITE_ID)).intValue());
			}catch(Exception e){
				appendErrors(parsedObj, "validation.web.batchOrder.error.errorParsingSiteId", pParsedLine.get(COLUMN.SITE_ID));
				parsedObj.setSiteId(-1);
			}
		}
		if (columnCount > COLUMN.STORE_SKU &&  pParsedLine.get(COLUMN.STORE_SKU) != null){
			try{
				parsedObj.setStoreSku(new Integer((String)pParsedLine.get(COLUMN.STORE_SKU)).intValue());
			}catch(Exception e){
				appendErrors(parsedObj, "validation.web.batchOrder.error.errorParsingStoreSku", pParsedLine.get(COLUMN.STORE_SKU));
				parsedObj.setStoreSku(-1);
			}
		}
	     processParsedObject(parsedObj);
	}

	
	protected void processParsedObject(Object pParsedObject) throws Exception{
		InboundBatchOrderData parsedObj = (InboundBatchOrderData) pParsedObject;
		if (isVersion1){
			if (!Utility.isSet(parsedObj.siteRefNum)){
				appendErrors(parsedObj, "validation.web.batchOrder.error.locationRefNumExpectedOnColumn", (COLUMN.SITE_REF_NUM +1));
			}			
			if (!Utility.isSet(parsedObj.custSku) && !Utility.isSet(parsedObj.distSku)){
				appendErrors(parsedObj, "validation.web.batchOrder.error.custSkuOrdistSkuExpectedOnColumn", (COLUMN.CUST_SKU +1), (COLUMN.DIST_SKU +1));
			}
		} else {
			if (parsedObj.siteId==0){
				appendErrors(parsedObj, "validation.web.batchOrder.error.siteIdExpectedOnColumn", (COLUMN.SITE_ID +1));
			}			
			if (parsedObj.storeSku==0){
				appendErrors(parsedObj, "validation.web.batchOrder.error.storeSkuExpectedOnColumn", (COLUMN.STORE_SKU +1));
			}
		}
			
		parsedObjects.add(parsedObj);
	}
	
	protected void doPostProcessing(String applyToBudget, String sendConfirmation) throws Exception{
		String date = custOrderDataFormat.format(new Date());
		OrderRequestData order = null;
		OrderRequestDataVector orderReqs = new OrderRequestDataVector();
		for (Map.Entry<String, Map<String, InboundBatchOrderData>> entry : orders.entrySet()) {
		    Object[] items = entry.getValue().values().toArray();								
			for(int lineNum = 1; lineNum <= items.length; lineNum++){				
				InboundBatchOrderData flat = (InboundBatchOrderData) items[lineNum-1];
				
				if (lineNum == 1){
					order = OrderRequestData.createValue();
					order.setAccountId(accountId);
					order.setSiteId(flat.siteId);
					order.setCustomerPoNumber(flat.getPoNum());
					order.setCustomerOrderDate(date);
					order.setOrderSourceCd(RefCodeNames.ORDER_SOURCE_CD.EDI_850);
					order.setOrderType(RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER);
					order.setSkuTypeCd(RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR);
					order.setBypassCustomerWorkflow(true);
					if (!Utility.isTrue(applyToBudget)){
						order.setOrderBudgetTypeCd(RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE);
					}
					
					if (Utility.isTrue(sendConfirmation))
						order.addArbitraryOrderMeta(RefCodeNames.ORDER_PROPERTY_TYPE_CD.SEND_ORDER_CONFIRMATION, "TRUE", 0);
					
					orderReqs.add(order);
				}
				order.addItemEntry(lineNum, flat.getDistSku(), flat.getQty(), flat.price, flat.getDistUom(), null, "");			
			}
		}
		if (!orderReqs.isEmpty()){
			IntegrationServices intSvc = APIAccess.getAPIAccess().getIntegrationServicesAPI();
			intSvc.createOrder850Events(orderReqs);
		}
    }
	
	// set item_id and price to object InboundBatchOrderData if item is found in shopping catalog
	private boolean setItemInfo(Connection conn, int shoppingCatalogId, InboundBatchOrderData flat) throws Exception {
		boolean itemInCatalog = true;
		String temp = "";
		if (isVersion1){
		    if (flat.custSku != null){
		        temp = "AND cs.CUSTOMER_SKU_NUM  = ? \n";
		    } else {
		        temp = "AND im.ITEM_NUM = ? \n";
		        if (flat.getDistUom()!=null)
		            temp += "AND im.ITEM_UOM = ? \n";
		    }
		}else{
		    temp = "AND i.sku_num = ? \n";
		}
		String sql = "SELECT i.ITEM_ID, ci.AMOUNT, im.ITEM_NUM, im.ITEM_UOM \n" +
				"FROM CLW_ITEM i, CLW_CATALOG_STRUCTURE cs, CLW_CONTRACT c, CLW_CONTRACT_ITEM ci, CLW_ITEM_MAPPING im \n" +
				"WHERE i.ITEM_ID = cs.ITEM_ID \n" +
				"AND cs.CATALOG_ID = ? \n" +
				"AND CATALOG_STRUCTURE_CD='" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT + "' \n" +
				"AND c.CATALOG_ID = cs.CATALOG_ID \n" +
				"AND c.CONTRACT_ID = ci.CONTRACT_ID \n" +
				"AND ci.ITEM_ID = i.ITEM_ID \n" +
				"AND im.ITEM_ID = i.ITEM_ID \n" +
				"AND im.ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_DISTRIBUTOR + "' \n" +					
				"AND im.BUS_ENTITY_ID = cs.BUS_ENTITY_ID \n" + temp;
		
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int idx = 1;
        pstmt.setInt(idx++, shoppingCatalogId);
        if (isVersion1){
            if (flat.custSku != null){
                pstmt.setString(idx++, flat.getCustSku());
            }else{
    	        pstmt.setString(idx++, flat.getDistSku());
    	        if (flat.getDistUom()!=null)
    	        	pstmt.setString(idx++, flat.getDistUom());
            }
        }else{
        	pstmt.setInt(idx++, flat.getStoreSku());
        }
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()){
        	flat.itemId = rs.getInt(1);
        	flat.price = rs.getDouble(2);
        	flat.distSku = rs.getString(3);
        	flat.distUom = rs.getString(4);
        	if (rs.next())
        		appendErrors(flat, "validation.web.batchOrder.error.multipleItemsMatched");
        }else{
        	appendErrors(flat, "validation.web.batchOrder.error.itemNotInShoppingCatalog");
        	itemInCatalog = false;
        }
        pstmt.close();
        return itemInCatalog;
	}	
		
	// add error message
	private void appendErrors(InboundBatchOrderData flat, String messageKey, Object... args) {
		String lineNum = lineNumberMap.get(flat);
		if (args.length > 0){
			for (int i = 0; i < args.length; i++){
				args[i]=args[i].toString();
			}
		}
		ResponseError error = new ResponseError(null, null, null, messageKey, args);
		Object[] args1 = new Object[]{lineNum, error};		
		error = new ResponseError(null, null, null, "validation.web.batchOrder.error.errorOnLine", args1);
		getErrorMsgs().add(error);		
	}
	
	private void appendErrors(String messageKey, Object... args) {
	    ResponseError error = new ResponseError(null, null, null, messageKey, args);
		getErrorMsgs().add(error);	
	}
	
	private void appendErrors(String message) {
        ResponseError error = new ResponseError(message);
        getErrorMsgs().add(error);  
    }
	
	public String getFormatedErrorMsgs ()
	{
		if (errorMsgs.size() == 0)
			return "";

		StringBuilder msg = new StringBuilder(errorMsgs.size() * 100);
		String temp = "";
		for (int i = 0; i < errorMsgs.size(); i++){
			ResponseError error = (ResponseError) errorMsgs.get(i);
			if (error.getArgs()!=null && error.getArgs().length>=2 && error.getArgs()[1] instanceof ResponseError){
				ResponseError error1 = (ResponseError) error.getArgs()[1];
				temp = I18nUtil.getMessage(error1.getKey(), error1.getArgs());
				error.getArgs()[1] = temp;
			}
			if (Utility.isSet(error.getKey()))
				msg.append("**** " + I18nUtil.getMessage(error.getKey(), error.getArgs()) + "\r\n");
			else
				msg.append("**** " + error.getMessage() + "\r\n");
		}
			
		return msg.toString();
	}

	public Integer getOrderCount() {
		return orders.size();
	}
	
	public List<String> getQtyIgnoreList() {
        return qtyIgnoreList;
    }

    public class InboundBatchOrderData implements Serializable{
		private String siteRefNum;
		private String poNum;
		private String custSku;
		private String distSku;
		private int qty;
		private String distUom = null;
		private int siteId;
		private int storeSku;	
		int itemId=0;
		double price;
		
		public String toString(){
			return "InboundBatchOrderData: siteRefNum=" + siteRefNum + ", poNum=" + poNum + ", custSku=" + custSku + 
			    ", distSku=" + distSku + ", distUom=" + distUom + ", qty="+ qty + 
				", siteId=" + siteId + ", storeSku=" + storeSku+ ", itemId=" + itemId + 
				", price=" + price;
		}
		private String getSiteKey(){
			String key = accountId+":";
			
			if (isVersion1){
				key += siteRefNum;
			}else{
				key += siteId;
			}
			
			if (Utility.isSet(poNum))
				return key + ":" + poNum;
			else
				return key;			
		}		
		private String getItemKey(){
			if (isVersion1){
				if (Utility.isSet(distUom))
					return distSku+":"+ distUom;
				else 
					return distSku;
			}else{
				return storeSku+"";
			}
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
		public void setDistUom(String distUom) {
			this.distUom = distUom;
		}
		public String getDistUom() {
			return distUom;
		}
		public void setSiteId(int siteId) {
			this.siteId = siteId;
		}
		public int getSiteId() {
			return siteId;
		}
		public void setStoreSku(int storeSku) {
			this.storeSku = storeSku;
		}
		public int getStoreSku() {
			return storeSku;
		}
        public String getCustSku() {
            return custSku;
        }
        public void setCustSku(String custSku) {
            this.custSku = custSku;
        }
	}
}
