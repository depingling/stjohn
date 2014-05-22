package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.session.Distributor;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;


public class InboundJanPakInvoice extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
	private static final String className = "InboundJanPakInvoice";
	private String user = className;
	private static long startTime;
	private String currInvoiceNum = null;
	private Map invoiceMap = new HashMap(); // invoice num and invoice id map;
	private JanPakDistInvoiceViewVector invoiceItems = null;
	
	private Map accountMap = new HashMap(); // account num and account accountId map
	private Map siteMap = new HashMap(); // site num and account accountId map
	private int storeId = -1;
	private int distributorId = -1;
	private int lineCount = 0;
	private Connection conn = null;
	private static final String selectAccountOrSiteIdSql = "SELECT BUS_ENTITY_ID FROM CLW_BUS_ENTITY " +
			"WHERE BUS_ENTITY_STATUS_CD <> 'INACTIVE' " +
			"AND BUS_ENTITY_ID IN (SELECT DISTINCT BUS_ENTITY1_ID FROM CLW_BUS_ENTITY_ASSOC WHERE BUS_ENTITY2_ID IN (?)) " +
			"AND BUS_ENTITY_ID IN (SELECT DISTINCT BUS_ENTITY_ID FROM CLW_PROPERTY WHERE CLW_VALUE= ? AND SHORT_DESC = ?) " +
			"AND BUS_ENTITY_TYPE_CD = ?";
	private static final String selectInvoiceId = "SELECT INVOICE_DIST_ID FROM CLW_INVOICE_DIST WHERE SITE_ID = ? AND INVOICE_NUM = ?";
	private static final String selectInvoiceItemId = "SELECT INVOICE_DIST_DETAIL_ID FROM CLW_INVOICE_DIST_DETAIL id WHERE INVOICE_DIST_ID = ? AND DIST_ITEM_SKU_NUM = ?";
	
	private static final String insertOrderPropertySql = "Insert into CLW_ORDER_PROPERTY (ORDER_PROPERTY_ID,INVOICE_DIST_ID,SHORT_DESC,CLW_VALUE,ORDER_PROPERTY_STATUS_CD,ORDER_PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE) " +
			"values (CLW_ORDER_PROPERTY_SEQ.NEXTVAL,?,?,?,'ACTIVE','JANPAK_INVOICE',sysdate,'InboundJanPakInvoice',sysdate)";
	/**
	 * Called when the object has successfully been parsed
	 */
	protected void processParsedObject(Object pParsedObject)  throws Exception{
		if(pParsedObject == null) {
			throw new IllegalArgumentException("No parsed site object present");
		}

		if (lineCount == 0){
			conn = getConnection();
			conn.setAutoCommit(false);
			startTime = System.currentTimeMillis();
		}else if (lineCount%50==0){	
			log.info("*********** " + (System.currentTimeMillis()-startTime)/1000 + " Second has passed. " + "*********** ");
		}
		if (pParsedObject instanceof JanPakDistInvoiceView){
			JanPakDistInvoiceView invoiceView = (JanPakDistInvoiceView)pParsedObject;					

			// first time, load storeId, distributorId
			if (storeId < 0){
				//Getting trading partner id
				TradingPartnerData partner = translator.getPartner();
				if(partner == null) {
					throw new IllegalArgumentException("Trading Partner ID cann't be determined");
				}
				int tradingPartnerId = partner.getTradingPartnerId();
				int storeIds[] = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
						RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
				if(storeIds == null || storeIds.length < 1) {
					throw new IllegalArgumentException("No stores present for current trading partner id = " +
							tradingPartnerId);
				} else if(storeIds.length > 1) {
					throw new Exception("Multiple stores present for current trading partner id = " +
							tradingPartnerId);
				}
				storeId = storeIds[0];
				Distributor distributorEjb = APIAccess.getAPIAccess().getDistributorAPI();
				IdVector distBusEntityIdV = distributorEjb.getDistributorIdsForStore(storeId);
				if(distBusEntityIdV.size()==0) {
	                String errorMess = "Store doesn't have any distributors. Store Id: "+storeId;
	                throw new Exception(errorMess);
	            }
	            if(distBusEntityIdV.size()>1) {
	                String errorMess = "Store has multiple distributors when suppose to have only one. Store Id: "+storeId;
	                throw new Exception(errorMess);
	            }
	            distributorId = ((Integer)distributorEjb.getDistributorIdsForStore(storeId).get(0)).intValue();
			} 
			log.info("Process Line " + ++lineCount + ":invoiceNum=" + invoiceView.getInvoiceNum());	
							
			if (!invoiceView.getInvoiceNum().equals(currInvoiceNum)){
				if (currInvoiceNum != null)
					processInvoice(invoiceItems);
				currInvoiceNum = invoiceView.getInvoiceNum();
				invoiceItems = new JanPakDistInvoiceViewVector();
			}
			invoiceItems.add(invoiceView);
				
		}
	}


	protected void doPostProcessing() throws Exception {
		processInvoice(invoiceItems);
		getTransactionObject().setException(this.getFormatedErrorMsgs());
	
		if (conn != null)
			conn.close();
	}

	public void processInvoice(JanPakDistInvoiceViewVector items)
	throws Exception {
		JanPakDistInvoiceView item = (JanPakDistInvoiceView) items.get(0);
		String invoiceNum = item.getInvoiceNum();
		String accountNum = item.getBillNum();
		String siteNum = item.getShipNum();
		Integer accountId = (Integer) accountMap.get(accountNum);
		Integer siteId = (Integer) siteMap.get(siteNum);
		
		Integer invoiceId = (Integer) invoiceMap.get(invoiceNum);
		if (invoiceId != null){
			throw new Exception("Invoice data is not sorted by invoice number - " + invoiceNum);
		}

				
		if (accountId == null){
			accountId = getAccountOrSiteByProperty(conn, storeId, accountNum, true);
			if (accountId == null){
				appendErrorMsgs("Account not found for DIST_ACCT_REF_NUM=" + accountNum + " and storeId=" + storeId);
				return;
			}else{
				accountMap.put(accountNum, accountId);
			}
		}

		if (siteId == null){
			siteId = getAccountOrSiteByProperty(conn, accountId.intValue(), siteNum, false);
			if (siteId == null){
				appendErrorMsgs("Site not found for SITE_REF_NUM=" + siteNum + " and accountId=" + accountId);
				return;
			}else{
				siteMap.put(siteNum, siteId);
			}
		}

		invoiceId = getInvoiceIdIfExist(conn, invoiceNum, siteId.intValue());
		if (invoiceId != null){
			appendErrorMsgs("Duplicated invoice with Invoice Num=" + invoiceNum + " and site id=" + siteId + "  already exists");
			return;
		}        
		
		
		InvoiceDistData idData = InvoiceDistData.createValue();
		idData.setStoreId(storeId);
		idData.setBusEntityId(distributorId);
		idData.setAccountId(accountId.intValue());
		idData.setSiteId(siteId.intValue());
		idData.setInvoiceNum(invoiceNum);
		idData.setInvoiceDate(item.getInvoiceDate());
		idData.setFreight(item.getFreight());
		idData.setSalesTax(item.getTax());
		idData.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY);
		idData.setSubTotal(new BigDecimal(0));
		if (item.getTax() != null)
			idData.setSubTotal(idData.getSubTotal().add(item.getTax()));
		if (item.getFreight() != null)
			idData.setSubTotal(idData.getSubTotal().add(item.getFreight()));
		idData.setAddBy(user);
		idData.setModBy(user);
					
		DBCriteria addressCrit = new DBCriteria();
        addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, siteId);
        addressCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        AddressDataVector addresses = AddressDataAccess.select(conn,addressCrit);
        
		AddressData addrD = (AddressData) addresses.get(0);
		idData.setShipToName(addrD.getName1());
		idData.setShipToCity(addrD.getCity());
		idData.setShipToState(addrD.getStateProvinceCd());
		idData.setShipToPostalCode(addrD.getPostalCode());
		idData = InvoiceDistDataAccess.insert(conn, idData);
		
		PreparedStatement pstmt = conn.prepareStatement(insertOrderPropertySql);
		insertInvoiceProperty(pstmt, idData.getInvoiceDistId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.BRANCH, item.getBranch());
		insertInvoiceProperty(pstmt, idData.getInvoiceDistId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.REP_NUM, item.getRepNum());
		insertInvoiceProperty(pstmt, idData.getInvoiceDistId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.REP_NAME, item.getRepName());
		if (Utility.isSet(item.getCustPoNum()))
			insertInvoiceProperty(pstmt, idData.getInvoiceDistId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_PO_NUM, item.getCustPoNum());
		pstmt.executeBatch();	
		pstmt.close();
		
		invoiceId = new Integer(idData.getInvoiceDistId());
		invoiceMap.put(invoiceNum, invoiceId);
		
		for (int i = 0; i < items.size(); i++) {
			item = (JanPakDistInvoiceView) items.get(i);
			InvoiceDistDetailData iddData = InvoiceDistDetailData.createValue();
			iddData.setInvoiceDistId(invoiceId.intValue());
			
			DBCriteria crit = new DBCriteria();
			crit.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, distributorId);
			crit.addEqualTo(ItemMappingDataAccess.ITEM_NUM, item.getDistSku());
			ItemMappingDataVector itemMappingV = ItemMappingDataAccess.select(conn,crit);
			if (itemMappingV.size() > 0){
				ItemMappingData itemMappingD = (ItemMappingData) itemMappingV.get(0);
				iddData.setDistItemShortDesc(itemMappingD.getShortDesc());
				iddData.setDistItemUom(itemMappingD.getItemUom());
			}

			iddData.setDistLineNumber(i+1);		                                
			iddData.setDistItemQuantity(item.getQuantity());
			iddData.setDistItemQtyReceived(item.getQuantity());
			iddData.setInvoiceDistSkuNum(item.getDistSku());
			iddData.setDistItemSkuNum(item.getDistSku());
			iddData.setDistItemUom(item.getDistUom());
			iddData.setItemReceivedCost(item.getPrice().abs());
			iddData.setAdjustedCost(item.getPrice().abs());
			iddData.setDistIntoStockCost(item.getCost().abs());
			iddData.setAddBy(user);
			iddData.setModBy(user);
			iddData = InvoiceDistDetailDataAccess.insert(conn, iddData);
			idData.setSubTotal(idData.getSubTotal().add(item.getPrice().abs().multiply(new BigDecimal(item.getQuantity()))));
		}

		InvoiceDistDataAccess.update(conn, idData);
		conn.commit();

	}

	private Integer getAccountOrSiteByProperty(Connection conn, int parentId, String refNum, boolean isAccount) throws SQLException {
		Integer busEntityId = null;
		PreparedStatement pstmt = conn.prepareStatement(selectAccountOrSiteIdSql);
		pstmt.setInt(1, parentId);
		pstmt.setString(2, refNum);
		if (isAccount){
			pstmt.setString(3, RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM);
			pstmt.setString(4, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
		}else{
			pstmt.setString(3, RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
			pstmt.setString(4, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
		}
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			busEntityId = new Integer(rs.getInt(1));
		}
		pstmt.close();
		rs.close();
		return busEntityId;
	}
	
	private void insertInvoiceProperty(PreparedStatement pstmt, int invoiceDistId, String key,	String val) throws SQLException {
		pstmt.setInt(1, invoiceDistId);
		pstmt.setString(2, key);
		pstmt.setString(3, val);
		pstmt.addBatch();
	}
	
	private Integer getInvoiceIdIfExist(Connection con, String pInvoiceNum, int siteId) throws SQLException{	
		Integer invoiceId = null;
		PreparedStatement pstmt = con.prepareStatement(selectInvoiceId);
		pstmt.setInt(1, siteId);
		pstmt.setString(2, pInvoiceNum);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()){
			invoiceId = new Integer(rs.getInt(1));			
		}
		pstmt.close();
		rs.close();
		return invoiceId;
	}

	/** Returns a report to be logged.  Should be human readable.
	 *
	 */
	public String getTranslationReport() {
		String temp = "Total invoice in file= " + invoiceMap.size() + ", Total invoice processed=" + invoiceMap.size();
		return temp + "\r\n" + getTransactionObject().getException();
	}
}

