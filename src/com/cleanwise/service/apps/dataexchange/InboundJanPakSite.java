package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.BusEntityParameterDataAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.log4j.Logger;



public class InboundJanPakSite extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
	private static final String className = "InboundJanPakSite";
	
	private static final String selectAccount = "select b.bus_entity_id, b_addr.address_id, p_addr.address_id p_address_id, " +
		"p2.property_id accountTypeId, b_addr.name1, b_addr.address1, b_addr.address3, b_addr.address4, " +
		"b_addr.address2, b_addr.city, b_addr.STATE_PROVINCE_CD state, b_addr.POSTAL_CODE zip, p2.clw_value accountType " +
		"from CLW_BUS_ENTITY b, CLW_ADDRESS b_addr, CLW_ADDRESS p_addr, CLW_PROPERTY p2 " +
		"where b.bus_entity_id = b_addr.bus_entity_id " +
		"and b_addr.address_type_cd = 'BILLING' " +
		"and b.bus_entity_id = p_addr.bus_entity_id " +
		"and p_addr.address_type_cd = 'PRIMARY CONTACT'	" +
		"and b.bus_entity_id = p2.bus_entity_id " +
		"and p2.short_desc = 'ACCOUNT TYPE' " +
		"and b.bus_entity_id = ?";
	
	private static final String selectSite = "select b.bus_entity_id, addr.address_id, addr.name1, addr.address1, " +
			"addr.address2, addr.address3, addr.address4, addr.city, addr.STATE_PROVINCE_CD state, addr.POSTAL_CODE zip " +
		"from CLW_BUS_ENTITY b, CLW_ADDRESS addr " +
		"where b.bus_entity_id = addr.bus_entity_id " +
		"and addr.address_type_cd = 'SHIPPING' " +
		"and b.bus_entity_id = ?";
	
	private static final String selectAccountOrSiteId = "select b.bus_entity_id " +
			"from CLW_BUS_ENTITY b, CLW_BUS_ENTITY_ASSOC ba, CLW_PROPERTY p " +
			"where b.bus_entity_id = ba.bus_entity1_id " +
			"and bus_entity_assoc_cd = ? " +
			"and ba.bus_entity2_id = ? " +
			"and b.bus_entity_id = p.bus_entity_id " +
			"and p.short_desc = ? " +
			"and p.clw_value = ?";
	
	private static final String insertPropertySql = "Insert into clw_property (PROPERTY_ID,BUS_ENTITY_ID,USER_ID,SHORT_DESC,CLW_VALUE,PROPERTY_STATUS_CD,PROPERTY_TYPE_CD,ADD_DATE,ADD_BY,MOD_DATE,MOD_BY,LOCALE_CD) " +
			"values (clw_property_seq.NEXTVAL,?,null,?,?,'ACTIVE',?,sysdate,'InboundJanPakSite',sysdate,'InboundJanPakSite',null)";
	
	private static final String updatePropertySql = "update clw_property set clw_value = ? where bus_entity_id = ? and property_type_cd = ?";
	
	Connection conn = null;
	private int storeId = -1;
	private static int lineCount = 0;
	private static long startTime;
	private Date runDate = new Date();
	private Map accountMap = new HashMap(); // Map of billNum and accountId
	private List siteNumList;
	private boolean checkSiteNumList = false; // JanPak set to false. JD China set to true
	private boolean processNewRecordOnly = false;//JD China is true
	private int cloneAccountId = -1;

	/**
	 * Called when the object has successfully been parsed
	 */
	protected void processParsedObject(Object pParsedObject)  throws Exception{		
		if(pParsedObject == null) {
			throw new IllegalArgumentException("No parsed site object present");
		}

		if (lineCount == 0){
			startTime = System.currentTimeMillis();
		}else if (lineCount%50==0){	
			log.info((System.currentTimeMillis()-startTime)/1000 + " Second has passed.");
		}
		
		if (pParsedObject instanceof JanPakSiteView){
			JanPakSiteView txtData = (JanPakSiteView)pParsedObject;
			log.info("Processing AccountRefCode=" + txtData.getBillNum() + ", SiteRefCode=" + txtData.getShipNum() + " (" + ++lineCount + ")");
			
			// first time, load storeId
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
				processNewRecordOnly = Utility.isTrue(getTranslator().getConfigurationProperty(RefCodeNames.ENTITY_PROPERTY_TYPE.PROCESS_NEW_RECORD_ONLY));
			} 

			if (txtData.getBillNum() == null || txtData.getBillNum().equals("")){ // process account and site
				txtData.setBillNum(txtData.getShipNum());
				txtData.setBillName(txtData.getShipName());
				txtData.setBillAddr1(txtData.getShipAddr1());
				txtData.setBillAddr2(txtData.getShipAddr2());
				txtData.setBillAddr3(txtData.getShipAddr3());
				txtData.setBillAddr4(txtData.getShipAddr4());
				txtData.setBillCity(txtData.getShipCity());
				txtData.setBillState(txtData.getShipState());
				txtData.setBillZip(txtData.getShipZip());
			}
			
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				if (conn == null){
					conn = getConnection();
					conn.setAutoCommit(false);
					if (checkSiteNumList){
						siteNumList = new ArrayList();
						String selectItemSku = "select SITE_NUM from DL_TEMP_SITE_NUM";
						Statement stmt = conn.createStatement();
						rs = stmt.executeQuery(selectItemSku);
						while (rs.next()){
							siteNumList.add(rs.getString(1));
						}
						rs.close();
						stmt.close();
						log.info("Total Site in table DL_TEMP_SITE_NUM=" + siteNumList.size());
					}
				}
				
				if (checkSiteNumList && !siteNumList.contains(txtData.getBillNum()) && !siteNumList.contains(txtData.getShipNum())){
					return;
				}
				Integer accountId = (Integer)accountMap.get(txtData.getBillNum());
				if (accountId == null){
					
					pstmt = conn.prepareStatement(selectAccountOrSiteId);
					pstmt.setString(1, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
					pstmt.setInt(2, storeId);
					pstmt.setString(3, RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM);
					pstmt.setString(4, txtData.getBillNum());
					rs = pstmt.executeQuery();
					if (rs.next()){
						accountId = new Integer(rs.getInt("bus_entity_id"));						
						if (!processNewRecordOnly){
							rs.close();
							pstmt.close();
							
							pstmt = conn.prepareStatement(selectAccount);
							pstmt.setInt(1, accountId);
							rs = pstmt.executeQuery();
							rs.next();
							updateData(conn, rs, txtData, true);
						}
					}else{//
						accountId = processNewData(conn, txtData, storeId, true);
					}
					rs.close();
					pstmt.close();	
					accountMap.put(txtData.getBillNum(), accountId);
									
				}	
				pstmt = conn.prepareStatement(selectAccountOrSiteId);
				pstmt.setString(1, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
				pstmt.setInt(2, accountId.intValue());				
				pstmt.setString(3, RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
				pstmt.setString(4, txtData.getShipNum());
				rs = pstmt.executeQuery();				
				
				if (rs.next()){	
					if (!processNewRecordOnly){						
						int siteId = rs.getInt(1);
						rs.close();
						pstmt.close();
						pstmt = conn.prepareStatement(selectSite);
						pstmt.setInt(1, siteId);
						rs = pstmt.executeQuery();
						rs.next();
						updateData(conn, rs, txtData, false);
					}
				}else{//
					processNewData(conn, txtData, accountId.intValue(), false);
				}
				rs.close();
				pstmt.close();
				conn.commit();
			}catch(Exception e){
				conn.rollback();
				conn.close();
				conn = null;
				throw e;
			}
		}
	}	

	private Integer processNewData(Connection conn, JanPakSiteView txtData, int parentId, boolean isAccount) throws Exception {
		PreparedStatement pstmt = null;
		int busEntityId = 0;
		
		if (isAccount){
			log.info("New Account for AccountRefCode=" + txtData.getBillNum());
			log.info("createNewAccount => begin");
	        
	        if (cloneAccountId < 0){
	        	cloneAccountId = getCloneAccountId(conn);
	        }
	        
	        busEntityId = cloneAccount(conn, cloneAccountId, storeId, txtData.getBillName() + " - " + txtData.getBillNum(), className);
	        			
			BusEntityData busEntityData = BusEntityDataAccess.select(conn, busEntityId);
			busEntityData.setEffDate(runDate);
			BusEntityDataAccess.update(conn, busEntityData);
			
			// get account address
			DBCriteria addressCrit = new DBCriteria();
			addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, busEntityId);
			addressCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.BILLING);
			AddressDataVector addressVec = AddressDataAccess.select(conn, addressCrit);
			
			
			AddressData addressD = (AddressData)addressVec.get(0);
			setAddressData(txtData, addressD, true);
			AddressDataAccess.update(conn, addressD);
			
			addressCrit = new DBCriteria();
			addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, busEntityId);
			addressCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
			addressVec = AddressDataAccess.select(conn, addressCrit);
			
			addressD = (AddressData)addressVec.get(0);
			setAddressData(txtData, addressD, true);
			AddressDataAccess.update(conn, addressD);
			
			pstmt = conn.prepareStatement(updatePropertySql);			
			
			pstmt.setString(1, txtData.getBillNum());
			pstmt.setInt(2, busEntityId);
			pstmt.setString(3, RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM);
			pstmt.addBatch();
			
			pstmt.setString(1, txtData.getCustSelCode());
			pstmt.setInt(2, busEntityId);
			pstmt.setString(3, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE);
			pstmt.addBatch();
			
		}else{
			log.info("New Site for SiteRefCode=" + txtData.getShipNum());
			BusEntityData busEntityData = BusEntityData.createValue();
			busEntityData.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
			busEntityData.setWorkflowRoleCd("UNKNOWN");
			busEntityData.setLocaleCd("en_US");
			busEntityData.setAddBy(className);
			busEntityData.setEffDate(runDate);
			busEntityData.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
			busEntityData.setShortDesc(txtData.getShipName());
			busEntityData = BusEntityDataAccess.insert(conn, busEntityData);
			busEntityId = busEntityData.getBusEntityId();
			busEntityData.setErpNum("#" + busEntityId);
			BusEntityDataAccess.update(conn, busEntityData);
				
			AddressData addressD = AddressData.createValue();
			addressD.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);			
			addressD.setAddBy(className);			
			addressD.setPrimaryInd(true);
			addressD.setAddressTypeCd(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
			setAddressData(txtData, addressD, false);
			addressD.setBusEntityId(busEntityId);
			addressD = AddressDataAccess.insert(conn, addressD);
			
			// create busEntityAssoc
			BusEntityAssocData busEntityAssocD = BusEntityAssocData.createValue();
			busEntityAssocD.setBusEntity1Id(busEntityId);
			busEntityAssocD.setBusEntity2Id(parentId);
			busEntityAssocD.setAddBy(className);
			busEntityAssocD.setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
			busEntityAssocD = BusEntityAssocDataAccess.insert(conn, busEntityAssocD);
			
			pstmt = conn.prepareStatement(insertPropertySql);
			pstmt.setInt(1, busEntityId);
			
			// site reference number	
			pstmt.setString(2, RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
			pstmt.setString(3, txtData.getShipNum());
			pstmt.setString(4, RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
			pstmt.addBatch();
			
			pstmt.setString(2, RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK);
			pstmt.setString(3, null);
			pstmt.setString(4, RefCodeNames.PROPERTY_TYPE_CD.TARGET_FACILITY_RANK);
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		pstmt.close();
		return new Integer(busEntityId);
		
	}

	private int getCloneAccountId(Connection conn) throws SQLException,
			Exception {
		DBCriteria propCrit = new DBCriteria();
		propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
		propCrit.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_PROPERTY_ACCOUNT );
		PropertyDataVector propVec = PropertyDataAccess.select(conn, propCrit);
		if (propVec.size() > 0){
			return (new Integer(((PropertyData)propVec.get(0)).getValue())).intValue();
		}else{
			throw new Exception("RefCodeNames.PROPERTY_TYPE_CD.DEFAULT_PROPERTY_ACCOUNT is not set for store " + storeId);
		}
	}

	private void setAddressData(JanPakSiteView txtData, AddressData addressD, boolean isBilling) {
		if (isBilling){
			addressD.setName1(txtData.getBillName());
			if (!isEqual(txtData.getBillAddr1(), addressD.getAddress1()))
				addressD.setAddress1(txtData.getBillAddr1());
			if (!isEqual(txtData.getBillAddr2(), addressD.getAddress2()))
				addressD.setAddress2(txtData.getBillAddr2());
			if (!isEqual(txtData.getBillAddr3(), addressD.getAddress3()))
				addressD.setAddress3(txtData.getBillAddr3());
			if (!isEqual(txtData.getBillAddr4(), addressD.getAddress4()))
				addressD.setAddress4(txtData.getBillAddr4());
			if (!isEqual(txtData.getBillCity(), addressD.getCity()))
				addressD.setCity(txtData.getBillCity());
			if (!isEqual(txtData.getBillZip(), addressD.getPostalCode()))
				addressD.setPostalCode(txtData.getBillZip());
			if (!isEqual(txtData.getBillState(), addressD.getStateProvinceCd()))
				addressD.setStateProvinceCd(txtData.getBillState());			
		}else{
			addressD.setName1(txtData.getShipName());
			if (!isEqual(txtData.getShipAddr1(), addressD.getAddress1()))
				addressD.setAddress1(txtData.getShipAddr1());
			if (!isEqual(txtData.getShipAddr2(), addressD.getAddress2()))
				addressD.setAddress2(txtData.getShipAddr2());
			if (!isEqual(txtData.getShipAddr3(), addressD.getAddress3()))
				addressD.setAddress3(txtData.getShipAddr3());
			if (!isEqual(txtData.getShipAddr4(), addressD.getAddress4()))
				addressD.setAddress4(txtData.getShipAddr4());
			if (!isEqual(txtData.getShipCity(), addressD.getCity()))
				addressD.setCity(txtData.getShipCity());
			if (!isEqual(txtData.getShipZip(), addressD.getPostalCode()))
				addressD.setPostalCode(txtData.getShipZip());
			if (!isEqual(txtData.getShipState(), addressD.getStateProvinceCd()))
				addressD.setStateProvinceCd(txtData.getShipState());
		}
		if (Utility.isSet(txtData.getCountry()))
			addressD.setCountryCd(txtData.getCountry().toUpperCase());
		else
			addressD.setCountryCd(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
		addressD.setModBy(className);
	}

	// update account or site as needed
	private void updateData(Connection conn, ResultSet rs, JanPakSiteView txtData, boolean isAccount) throws Exception {
				
		String addrName = rs.getString("name1");
		String address1 = rs.getString("address1");
		String address2 = rs.getString("address2");
		String address3 = rs.getString("address3");
		String address4 = rs.getString("address4");
		String city = rs.getString("city");
		String zip = rs.getString("zip");
		String state = rs.getString("state");	
		int busEntityId = rs.getInt("bus_entity_id");
		
		if (isAccount){
			if (isEqual(txtData.getCustSelCode(), rs.getString("accountType"))
					&& isEqual(txtData.getBillName(), addrName)
					&& isEqual(txtData.getBillAddr1(), address1)
					&& isEqual(txtData.getBillAddr2(), address2)
					&& isEqual(txtData.getBillAddr3(), address3)
					&& isEqual(txtData.getBillAddr4(), address4)
					&& isEqual(txtData.getBillCity(), city)
					&& isEqual(txtData.getBillZip(), zip)
					&& isEqual(txtData.getBillState(), state)){
				log.info("Account not changed for AccountRefCode=" + txtData.getBillNum());
			}else{
				if (!isEqual(txtData.getCustSelCode(), rs.getString("accountType"))){
					PropertyData propertyD = PropertyDataAccess.select(conn, rs.getInt("accountTypeId"));
					propertyD.setValue(txtData.getCustSelCode());
					propertyD.setModBy(className);
					PropertyDataAccess.update(conn, propertyD);					
				}
				
				if (!isEqual(txtData.getBillName(), addrName)){
					BusEntityData accountD = BusEntityDataAccess.select(conn, busEntityId);
					accountD.setShortDesc(txtData.getBillName());
					accountD.setModBy(className);
					BusEntityDataAccess.update(conn, accountD);
				}
				
				if (isEqual(txtData.getBillAddr1(), address1)
						&& isEqual(txtData.getBillAddr2(), address2)
						&& isEqual(txtData.getBillAddr3(), address3)
						&& isEqual(txtData.getBillAddr4(), address4)
						&& isEqual(txtData.getBillCity(), city)
						&& isEqual(txtData.getBillZip(), zip)
						&& isEqual(txtData.getBillState(), state)){
				}else{					
					AddressData addressD = AddressDataAccess.select(conn, rs.getInt("address_id"));
					setAddressData(txtData, addressD, true);
					AddressDataAccess.update(conn, addressD);
					
					addressD = AddressDataAccess.select(conn, rs.getInt("p_address_id"));
					setAddressData(txtData, addressD, true);
					AddressDataAccess.update(conn, addressD);
				}
				log.info("Account changed for AccountRefCode=" + txtData.getBillNum());
			}
		}else{
			if (isEqual(txtData.getShipName(), addrName)
					&& isEqual(txtData.getShipAddr1(), address1)
					&& isEqual(txtData.getShipAddr2(), address2)
					&& isEqual(txtData.getShipAddr3(), address3)
					&& isEqual(txtData.getShipAddr4(), address4)
					&& isEqual(txtData.getShipCity(), city)
					&& isEqual(txtData.getShipZip(), zip)
					&& isEqual(txtData.getShipState(), state)){
				log.info("Site not changed for SiteRefCode=" + txtData.getShipNum());
			}else{
				if (!isEqual(txtData.getShipName(), addrName)){
					BusEntityData siteD = BusEntityDataAccess.select(conn, busEntityId);
					siteD.setShortDesc(txtData.getShipName());
					siteD.setModBy(className);
					BusEntityDataAccess.update(conn, siteD);
				}
				AddressData addressD = AddressDataAccess.select(conn, rs.getInt("address_id"));
				setAddressData(txtData, addressD, false);
				AddressDataAccess.update(conn, addressD);
				log.info("Site changed for SiteRefCode=" + txtData.getShipNum());
			}
		}
	}
			
	public String getTranslationReport() {
		return "Successfully translated "+accountMap.size() + " Account and " + lineCount + " Site.";
	}
	
	
    private int cloneAccount(Connection conn, int cloneAccountId, int storeId, String accountName, String user)
    throws Exception {
    	BusEntityData busEntity = null;  

		// get busEntity for cloneAccountId
		busEntity = BusEntityDataAccess.select(conn, cloneAccountId);   
		busEntity.setShortDesc(accountName);
		busEntity.setLongDesc(accountName);
        busEntity.setAddBy(user);
        busEntity.setModBy(user);
        busEntity = BusEntityDataAccess.insert( conn, busEntity );
		
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
        crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);
        PropertyDataVector pdv = PropertyDataAccess.select(conn, crit);
            		
		String erpSystemCode = "";
		if (pdv.size() > 1)
			erpSystemCode = ((PropertyData)pdv.get(0)).getValue();
		
		busEntity.setErpNum("#"+busEntity.getBusEntityId());
		BusEntityDataAccess.update( conn, busEntity );

		int accountId = busEntity.getBusEntityId();
		
		// create store association
		BusEntityAssocData storeAssoc = BusEntityAssocData.createValue();
		storeAssoc.setBusEntity1Id(accountId);
		storeAssoc.setBusEntity2Id(storeId);
		storeAssoc.setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);		
		storeAssoc.setAddBy(user);
		storeAssoc.setModBy(user);
		BusEntityAssocDataAccess.insert(conn, storeAssoc);
		
		// get related email addresses
        DBCriteria emailCrit = new DBCriteria();
        emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, cloneAccountId);

        EmailDataVector emailVec = EmailDataAccess.select(conn, emailCrit);
        Iterator emailIter = emailVec.iterator();

        while (emailIter.hasNext()) {
            EmailData email = (EmailData)emailIter.next();    		
            email.setBusEntityId(accountId);
            email.setAddBy(user);
            email.setModBy(user);
			EmailDataAccess.insert(conn, email);
		}
        
        // get related phones
        DBCriteria phoneCrit = new DBCriteria();
        phoneCrit.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID, cloneAccountId);

        PhoneDataVector phoneVec = PhoneDataAccess.select(conn, phoneCrit);
        Iterator phoneIter = phoneVec.iterator();

        while (phoneIter.hasNext()) {
            PhoneData phone = (PhoneData)phoneIter.next();  
            phone.setBusEntityId(accountId);
            phone.setAddBy(user);
            phone.setModBy(user);
			PhoneDataAccess.insert(conn, phone);
		}

     	// get account address
        DBCriteria addressCrit = new DBCriteria();
        addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, cloneAccountId);
        AddressDataVector addressVec = AddressDataAccess.select(conn, addressCrit);
        Iterator addressIter = addressVec.iterator();
        while (addressIter.hasNext()) {
        	AddressData address = (AddressData)addressIter.next();
        	address.setBusEntityId(accountId);
        	address.setAddBy(user);
        	address.setModBy(user);
			AddressDataAccess.insert(conn, address);
		}
        
        // get properties
        DBCriteria propCrit = new DBCriteria();
        propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, cloneAccountId);        
        PropertyDataVector pv = PropertyDataAccess.select(conn,propCrit);
        Iterator propertyIter = pv.iterator();
        
        PreparedStatement pstmt = conn.prepareStatement(insertPropertySql);
        while (propertyIter.hasNext()) {
        	PropertyData property = (PropertyData) propertyIter.next();
        	
    		pstmt.setInt(1, accountId);    		
    		pstmt.setString(2, property.getShortDesc());
    		pstmt.setString(3, property.getValue());
    		pstmt.setString(4, property.getPropertyTypeCd());
    		pstmt.addBatch();
		}
		pstmt.executeBatch();
		pstmt.close();
	
        // get busEntity parameter
        DBCriteria busParmCrit = new DBCriteria();
        busParmCrit.addEqualTo(BusEntityParameterDataAccess.BUS_ENTITY_ID, cloneAccountId);
        
        BusEntityParameterDataVector bpb = BusEntityParameterDataAccess.select(conn,busParmCrit);
        Iterator busParamIter = bpb.iterator();
        while (busParamIter.hasNext()) {
        	BusEntityParameterData busParam = (BusEntityParameterData) busParamIter.next();
        	busParam.setBusEntityId(accountId);	
        	busParam.setAddBy(user);
        	busParam.setModBy(user);
        	BusEntityParameterDataAccess.insert(conn, busParam);
		}

        return accountId;
    }
    
    private static boolean isEqual(String str1, String str2){
    	if (str2 != null && (str2.equals("NA") || str2.equals("N/A")))
    		str2 = null;
    	return Utility.isEqual(str1, str2);
	}

}

