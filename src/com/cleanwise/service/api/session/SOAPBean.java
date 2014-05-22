package com.cleanwise.service.api.session;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.dto.AccountDto;
import com.cleanwise.service.api.dto.AddressDto;
import com.cleanwise.service.api.dto.BusEntityDto;
import com.cleanwise.service.api.dto.EmailDto;
import com.cleanwise.service.api.dto.ManufacturerDto;
import com.cleanwise.service.api.dto.PhoneDto;
import com.cleanwise.service.api.dto.PropertyDto;
import com.cleanwise.service.api.dto.SiteDto;
import com.cleanwise.service.api.dto.UserDto;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCrit;
import com.cleanwise.service.api.util.SearchCritCondition;
import com.cleanwise.service.api.util.UserRightsTool;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.BusEntityAssocData;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.EmailData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.UserAssocData;
import com.cleanwise.service.api.value.UserData;

public class SOAPBean extends BusEntityServicesAPI {
	
	private Connection connection = null;// This field is only for JUnit testing!!!  Use method super.getConnection()
	public SOAPBean(Connection connection) { // This constructor only for JUnit testing. 
		this.connection = connection;
	}
	
	public SOAPBean(){}
	
	private static final Logger log = Logger.getLogger(SOAPBean.class);
	
	public void ejbCreate() throws CreateException, RemoteException {}

	public List<SiteDto> getSites(SearchCrit searchCrit, int storeId) throws RemoteException {
		
		Connection conn = null;
		List<SiteDto> resultList = new ArrayList<SiteDto>();
		try{
			conn = getConnection();

			Collection<BusEntityData> busEntityList =  
				getBusEntityCollection(searchCrit, storeId, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE, conn, this.getConditionsForNestedEntities(searchCrit.getSearchCritConditions()));
			
			//build DTO
			for(BusEntityData busEntityData : busEntityList){
				SiteDto siteDto = new SiteDto();
				fillBusEntityDto(siteDto, busEntityData);
				log.info("getSites -> siteDTO = " + siteDto);
				
				siteDto.setAddressShipping(getAddress(busEntityData.getBusEntityId(), null, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING, conn));
				
				siteDto.setSiteProperties(getProperties(busEntityData.getBusEntityId(), null, conn));
				resultList.add(siteDto);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("getSites: " + e.getMessage());
		}finally {
	        closeConnection(conn);
	    }
		return resultList ;
	}
	
	public List<ManufacturerDto> getManufacturers(SearchCrit searchCrit, int storeId) throws RemoteException {
		log.info("getManufacturers -> searchCrit = " + searchCrit + " storeId = " + storeId);
		Connection conn = null;
		List<ManufacturerDto> resultList = new ArrayList<ManufacturerDto>();
		try{
			conn = getConnection();
			Collection<BusEntityData> busEntityList = getBusEntityCollection(searchCrit, storeId, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER, conn, Collections.EMPTY_LIST);
			//build DTO
			for(BusEntityData busEntityData : busEntityList){
				ManufacturerDto manufacturerDto = new ManufacturerDto();
				fillBusEntityDto(manufacturerDto, busEntityData);
				resultList.add(manufacturerDto);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("getManufacturers(: " + e.getMessage());
		}finally {
	        closeConnection(conn);
	    }
		return resultList; 
	}
	
	
	public List<AccountDto> getAccounts(SearchCrit searchCrit, int storeId) throws RemoteException {
		Connection conn = null;
		List<AccountDto> resultList = new ArrayList<AccountDto>();
		try {
			conn = getConnection();
			Collection<BusEntityData> busEntityList = getBusEntityCollection(searchCrit, storeId, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, conn,  this.getConditionsForNestedEntities(searchCrit.getSearchCritConditions()));
			for(BusEntityData busEntityData : busEntityList){
				PhoneDto phoneOrder = getPhone(busEntityData.getBusEntityId(), null, "Order Phone", conn);
				PhoneDto phoneFax  = getPhone(busEntityData.getBusEntityId(), null, "Order Fax", conn);
				PhoneDto phonePrimaryContact = getPhone(busEntityData.getBusEntityId(), null, "Primary Phone", conn);
				PhoneDto phonePrimaryContactFax = getPhone(busEntityData.getBusEntityId(), null, "Primary Fax", conn);
				
				EmailDto emailPrimaryContact = getEmail(busEntityData.getBusEntityId(), null, RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT, conn);
			    EmailDto emailDefault = getEmail(busEntityData.getBusEntityId(), null, RefCodeNames.EMAIL_TYPE_CD.DEFAULT, conn);
			    EmailDto emailContactUsCC = getEmail(busEntityData.getBusEntityId(), null, RefCodeNames.EMAIL_TYPE_CD.CONTACT_US, conn);
			    EmailDto emailCustomerService = getEmail(busEntityData.getBusEntityId(), null, RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE, conn);
			    
			    AddressDto addressPrimary = getAddress(busEntityData.getBusEntityId(),null, RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT,  conn);
			    AddressDto addressBilling = getAddress(busEntityData.getBusEntityId(),null, RefCodeNames.ADDRESS_TYPE_CD.BILLING, conn);
			    List<PropertyDto> siteProperties = getProperties(busEntityData.getBusEntityId(), null, conn);
			    
			    AccountDto accountDto = new AccountDto();
			    fillBusEntityDto(accountDto, busEntityData);
			    accountDto.setPhoneOrder(phoneOrder);
			    accountDto.setPhoneFax(phoneFax);
			    accountDto.setPhonePrimaryContact(phonePrimaryContact);
			    accountDto.setPhonePrimaryContactFax(phonePrimaryContactFax);
			    accountDto.setEmailPrimaryContact(emailPrimaryContact);
			    accountDto.setEmailDefault(emailDefault);
			    accountDto.setEmailContactUsCC(emailContactUsCC);
			    accountDto.setEmailCustomerService(emailCustomerService);
			    accountDto.setAddressPrimary(addressPrimary);
			    accountDto.setAddressBilling(addressBilling);
			    accountDto.setSiteProperties(siteProperties);		    
			    
			    resultList.add(accountDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("getAccounts " + e.getMessage());
		} finally {
			closeConnection(conn);
		}
		return resultList;
	}
	
	@Deprecated
	private Collection<BusEntityData> getBusEntityCollection(SearchCrit searchCrit, int storeId, String busEntityTypeCd, Connection conn) throws RemoteException {
		return getBusEntityCollection(searchCrit, storeId, busEntityTypeCd, conn, null);
	}
	
	private Collection<BusEntityData> getBusEntityCollection(SearchCrit searchCrit, int storeId, String busEntityTypeCd, Connection conn, List<SearchCritCondition> conditionsForNestedEntities) throws RemoteException {
		try{
			DBCriteria dbCriteria = convertSearchCritToDbCriteria(searchCrit);
			DBCriteria isolCriteria = new DBCriteria();
			if (busEntityTypeCd.equals(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE)) {
				dbCriteria.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " site");
				isolCriteria.addCondition("CLW_BUS_ENTITY.BUS_ENTITY_ID = site.BUS_ENTITY1_ID");
				isolCriteria.addEqualTo("site.BUS_ENTITY_ASSOC_CD", RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

				dbCriteria.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC + " account");
				isolCriteria.addCondition("site.BUS_ENTITY2_ID = account.BUS_ENTITY1_ID");

				isolCriteria.addEqualTo("account.BUS_ENTITY_ASSOC_CD", RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
				isolCriteria.addEqualTo("account.BUS_ENTITY2_ID", storeId);
			} else {
			
				dbCriteria.addJoinTable(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC);
				isolCriteria.addCondition("CLW_BUS_ENTITY.BUS_ENTITY_ID = CLW_BUS_ENTITY_ASSOC.BUS_ENTITY1_ID");

				if (busEntityTypeCd.equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT))
					isolCriteria.addEqualTo("CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD", RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
				else if (busEntityTypeCd.equals(RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER))
					isolCriteria.addEqualTo("CLW_BUS_ENTITY_ASSOC.BUS_ENTITY_ASSOC_CD", RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE);
				
				isolCriteria.addEqualTo("CLW_BUS_ENTITY_ASSOC.BUS_ENTITY2_ID", storeId);
			}
			dbCriteria.addIsolatedCriterita(isolCriteria);
			
			if (conditionsForNestedEntities != null && !conditionsForNestedEntities.isEmpty()) {
				DBCriteria addressCriteria = null,
						   propertyCriteria = null,
						   phoneCriteria = null,
						   emailCriteria = null;
				
				for(SearchCritCondition condition : conditionsForNestedEntities){
					switch (condition.getNestedEntity()) {
					case SearchCritCondition.ADDRESS:
						if (addressCriteria == null) {
							addressCriteria = new DBCriteria();
							dbCriteria.addJoinTable(AddressDataAccess.CLW_ADDRESS);
							addressCriteria.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID, AddressDataAccess.CLW_ADDRESS, AddressDataAccess.BUS_ENTITY_ID);
						}
						addConditionToDBCriteria(addressCriteria, condition,AddressDataAccess.CLW_ADDRESS);
						
						break;
					case SearchCritCondition.PROPERTY:
						if(propertyCriteria == null){
							propertyCriteria = new DBCriteria();
							dbCriteria.addJoinTable(PropertyDataAccess.CLW_PROPERTY);
							propertyCriteria.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID, PropertyDataAccess.CLW_PROPERTY, PropertyDataAccess.BUS_ENTITY_ID);
						}
						addConditionToDBCriteria(propertyCriteria, condition,PropertyDataAccess.CLW_PROPERTY);
						break;
					case SearchCritCondition.PHONE:
						if(phoneCriteria == null){
							phoneCriteria = new DBCriteria();
							dbCriteria.addJoinTable(PhoneDataAccess.CLW_PHONE);
							phoneCriteria.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID, PhoneDataAccess.CLW_PHONE, PhoneDataAccess.BUS_ENTITY_ID);
						}
						addConditionToDBCriteria(phoneCriteria, condition,PhoneDataAccess.CLW_PHONE);
						break;
					case SearchCritCondition.EMAIL:
						if(emailCriteria == null){
							emailCriteria = new DBCriteria();
							dbCriteria.addJoinTable(EmailDataAccess.CLW_EMAIL);
							emailCriteria.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY + "." + BusEntityDataAccess.BUS_ENTITY_ID, EmailDataAccess.CLW_EMAIL, EmailDataAccess.BUS_ENTITY_ID);
						}
						addConditionToDBCriteria(emailCriteria, condition,EmailDataAccess.CLW_EMAIL);
						break;
					default:
						break;
					}
				}
				if(addressCriteria != null) dbCriteria.addIsolatedCriterita(addressCriteria);
				if(propertyCriteria != null) dbCriteria.addIsolatedCriterita(propertyCriteria);
				if(phoneCriteria != null) dbCriteria.addIsolatedCriterita(phoneCriteria);
				if(emailCriteria != null) dbCriteria.addIsolatedCriterita(emailCriteria);
			}
			
			List<BusEntityData> result = BusEntityDataAccess.select(conn, dbCriteria);			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("getBusEntityList: " + e.getMessage());
		}		
	}	
	
	private void fillBusEntityDto(BusEntityDto busEntityDto, BusEntityData busEntityData){
		busEntityDto.setShortDesc(busEntityData.getShortDesc());
		busEntityDto.setBusEntityId(busEntityData.getBusEntityId());
		busEntityDto.setStatusCd(busEntityData.getBusEntityStatusCd());
	}
	
	public int save(Object object, int assocId)throws RemoteException {
		return save(object, "soapUser", assocId);
	}
	
	public int save(Object object, String userName, int assocId) throws RemoteException {
		try {
			if (object instanceof ManufacturerDto)
				return saveManufacturerDto((ManufacturerDto) object, userName, assocId);
			else if (object instanceof AccountDto)
				return saveAccountDto((AccountDto) object, userName, assocId);
			else if (object instanceof UserDto)
				return saveUserDto((UserDto) object, userName, assocId);
			else if (object instanceof SiteDto)
				return saveSiteDto((SiteDto) object, assocId, userName);
			else
				throw new Exception("Unsupported object for save :" + object.getClass());
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	private int saveManufacturerDto(ManufacturerDto manufacturerDto, String userName, int storeId) throws RemoteException {
		Connection conn = null;
		try {
			conn = getConnection();
			// busEntity
			int busEntityId = createOrUpdateBusEntity(manufacturerDto, RefCodeNames.BUS_ENTITY_TYPE_CD.MANUFACTURER, userName, conn);
			manufacturerDto.setBusEntityId(busEntityId);
			saveManufacturerAssoc(storeId, manufacturerDto, userName, conn);
			return busEntityId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("saveManufacturerDtoo: " + e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}
	
	private int saveAccountDto(AccountDto accountDto, String userName, int storeId) throws RemoteException {
		Connection conn = null;
		try {
			conn = getConnection();
			
			int userId = 0; 
			int busEntityId = createOrUpdateBusEntity(accountDto, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT, userName, conn);
			accountDto.setBusEntityId(busEntityId);
			saveAccountAssoc(storeId, accountDto, userName, conn);

			if (accountDto.getPhoneOrder() == null) throw new Exception("Phone Order is Null");
			createOrUpdatePhone(accountDto.getPhoneOrder(),busEntityId,userId, userName, RefCodeNames.PHONE_TYPE_CD.ORDERPHONE, RefCodeNames.PHONE_STATUS_CD.ACTIVE, conn);
				
			if (accountDto.getPhoneFax() == null) throw new Exception("PhoneFax Order is Null");
			createOrUpdatePhone(accountDto.getPhoneFax(),busEntityId,userId, userName, RefCodeNames.PHONE_TYPE_CD.FAX,RefCodeNames.PHONE_STATUS_CD.ACTIVE, conn);
			
			if (accountDto.getPhonePrimaryContact() == null) throw new Exception("Phone Primary Contact is Null");
			createOrUpdatePhone(accountDto.getPhonePrimaryContact(),busEntityId,userId, userName, RefCodeNames.PHONE_TYPE_CD.PHONE,RefCodeNames.PHONE_STATUS_CD.ACTIVE, conn);
			
			if (accountDto.getPhonePrimaryContactFax() == null) throw new Exception("Phone Primary Contact Fax is Null");
			createOrUpdatePhone(accountDto.getPhonePrimaryContactFax(),busEntityId,userId, userName, RefCodeNames.PHONE_TYPE_CD.FAX,RefCodeNames.PHONE_STATUS_CD.ACTIVE, conn);
			
			if (accountDto.getEmailPrimaryContact() == null) throw new Exception("Email Primary Contact is Null");
			createOrUpdateEmail(accountDto.getEmailPrimaryContact(),busEntityId,userId, userName, RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT, RefCodeNames.EMAIL_STATUS_CD.ACTIVE, conn);
			
			if (accountDto.getEmailDefault() != null)// throw new Exception("Email Default is Null");
			createOrUpdateEmail(accountDto.getEmailDefault(),busEntityId,userId, userName, RefCodeNames.EMAIL_TYPE_CD.DEFAULT, RefCodeNames.EMAIL_STATUS_CD.ACTIVE, conn);
			
			if (accountDto.getEmailContactUsCC() != null)
			createOrUpdateEmail(accountDto.getEmailContactUsCC(),busEntityId,userId, userName, RefCodeNames.EMAIL_TYPE_CD.CONTACT_US, RefCodeNames.EMAIL_STATUS_CD.ACTIVE, conn);
			
			if (accountDto.getEmailCustomerService() != null)//throw new Exception("Email Customer Service is null");
				createOrUpdateEmail(accountDto.getEmailCustomerService(),busEntityId,userId, userName, RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE, RefCodeNames.EMAIL_STATUS_CD.ACTIVE, conn);
			
			if(accountDto.getAddressPrimary() == null)throw new Exception("Address Primary is null");
			createOrUpdateAddress(accountDto.getAddressPrimary(),busEntityId,userId,RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT, userName, conn);
			
			if(accountDto.getAddressBilling() == null) throw new Exception("Address Billing is null");
			createOrUpdateAddress(accountDto.getAddressBilling(),busEntityId,userId,RefCodeNames.ADDRESS_TYPE_CD.BILLING, userName, conn);

			if (accountDto.getSiteProperties() != null) 				// throw new Exception("Properties is null");
			createOrUpdateProperty(accountDto.getSiteProperties(), busEntityId, userId, userName, conn);			

			return busEntityId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("saveAccountDto: " + e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}
	
	private int saveUserDto(UserDto userDto, String userName, int storeId) throws RemoteException {
		Connection conn = null;
		try {
			conn = getConnection();
			int userId = createOrUpdateUser(userDto, userName, conn);
			userDto.setUserId(userId);
			saveUserAssoc(storeId, userDto, userName, conn);
			
			if (userDto.getEmailPrimary() != null)
				createOrUpdateEmail(userDto.getEmailPrimary(), 0, userId, userName, RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT,RefCodeNames.EMAIL_STATUS_CD.ACTIVE, conn);
			
			if (userDto.getPhonePrimary() != null)
				createOrUpdatePhone(userDto.getPhonePrimary(), 0, userId, userName, RefCodeNames.PHONE_TYPE_CD.PHONE, RefCodeNames.PHONE_STATUS_CD.ACTIVE, conn);
			
			if (userDto.getPhoneFax() != null)
				createOrUpdatePhone(userDto.getPhoneFax(), 0, userId, userName, RefCodeNames.PHONE_TYPE_CD.FAX, RefCodeNames.PHONE_STATUS_CD.ACTIVE, conn);
				
			if (userDto.getPhoneMobile() != null)
				createOrUpdatePhone(userDto.getPhoneMobile(), 0, userId, userName, RefCodeNames.PHONE_TYPE_CD.MOBILE, RefCodeNames.PHONE_STATUS_CD.ACTIVE, conn);
				
			if (userDto.getAddressPrimaryContact() != null)
				createOrUpdateAddress(userDto.getAddressPrimaryContact(), 0, userId,RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT, userName, conn);
			
			if (userDto.getUserProperties() != null)
				createOrUpdateProperty(userDto.getUserProperties(), 0, userId, userName, conn);

			return userId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("saveUserDto: " + e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}	
	
	private int saveSiteDto(SiteDto siteDto, int accountId, String userName) throws RemoteException {
		Connection conn = null;
		try {
			conn = getConnection();
			int busEntityId = createOrUpdateBusEntity(siteDto, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE, userName, conn);
			siteDto.setBusEntityId(busEntityId);
			
			saveSiteAssoc(accountId, siteDto, userName, conn);
			
//			//address
			if(siteDto.getAddressShipping() == null) throw new Exception("Shipping address is null");
			createOrUpdateAddress(siteDto.getAddressShipping(),busEntityId, 0,RefCodeNames.ADDRESS_TYPE_CD.SHIPPING, userName, conn);			
			
			// Properties
			if(siteDto.getSiteProperties() != null)
			createOrUpdateProperty(siteDto.getSiteProperties(), busEntityId, 0, userName, conn);
			
			return busEntityId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("saveSiteDto: " + e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}
	
	private int createOrUpdateUser(UserDto userDto, String userName, Connection conn) throws SQLException, DataNotFoundException{
		UserData userData = userDto.getUserId() > 0 ? UserDataAccess.select(conn, userDto.getUserId()) : new UserData();
		
		userData.setUserId(userDto.getUserId());
		userData.setFirstName(userDto.getFirstName());
		userData.setLastName(userDto.getLastName());
		userData.setUserName(userDto.getUserName());
		userData.setPassword(userDto.getPassword());
		userData.setEffDate(new Date(userDto.getEffDate()));
		userData.setExpDate(new Date(userDto.getExpDate()));		
		userData.setUserStatusCd(userDto.getUserStatusCd());
		userData.setUserTypeCd(userDto.getUserStatusCd());
		userData.setPrefLocaleCd("en_US");
		userData.setWorkflowRoleCd("UNKNOWN");
		
		UserRightsTool userRightsTool = new UserRightsTool(userData);
		userRightsTool.setOnAccount(userDto.getOnAccount());
		userRightsTool.setPoNumRequired(userDto.getPoNumberRequiered());
		userRightsTool.setOtherPaymentFlag(userDto.getOtherPayment());
		
		String userRoleCd = userRightsTool.makePermissionsToken();
		userData.setUserRoleCd(userRoleCd);
	    userData.setModBy(userName);
	    userData.setModDate(new Date());

	    if(userDto.getUserId() == 0){
	    	userData.setAddBy(userName);
	    	userData.setAddDate(new Date());
	    	return UserDataAccess.insert(conn, userData).getUserId();
	    }else{
	    	UserDataAccess.update(conn, userData);
	    	return userData.getUserId();
	    }
	}
	
	private int createOrUpdateBusEntity(BusEntityDto dto, String busEntityType, String userName, Connection conn) throws SQLException, DataNotFoundException{
		BusEntityData busEntityData = dto.getBusEntityId() > 0 ? BusEntityDataAccess.select(conn, dto.getBusEntityId()) : new BusEntityData(); 
		
		busEntityData.setBusEntityId(dto.getBusEntityId());
	    busEntityData.setShortDesc(dto.getShortDesc());
	    busEntityData.setLongDesc(dto.getShortDesc());
	    busEntityData.setBusEntityTypeCd(busEntityType);
	    busEntityData.setBusEntityStatusCd(dto.getStatusCd());
	    busEntityData.setWorkflowRoleCd("UNKNOWN");
	    busEntityData.setLocaleCd("unk");
	    busEntityData.setModBy(userName);
	    busEntityData.setModDate(new Date());
	    
	    if(dto.getBusEntityId() == 0){
	    	busEntityData.setAddBy(userName);
	    	busEntityData.setAddDate(new Date());
	    	return BusEntityDataAccess.insert(conn, busEntityData).getBusEntityId();
	    }else{
	    	BusEntityDataAccess.update(conn, busEntityData);
	    	return busEntityData.getBusEntityId();
	    }
	}
	
	private void createOrUpdatePhone(PhoneDto phoneDto, int busEntityId, int userId, String userName, String typeCd, String statusCd, Connection conn) throws SQLException, DataNotFoundException{
		PhoneData phoneData = phoneDto.getPhoneId() == 0 ? new PhoneData() : PhoneDataAccess.select(conn, phoneDto.getPhoneId());		

		phoneData.setPhoneNum(phoneDto.getPhoneNum());		
		phoneData.setPhoneTypeCd(typeCd);
		phoneData.setPhoneStatusCd(statusCd);
		phoneData.setBusEntityId(busEntityId);
		phoneData.setUserId(userId);
		phoneData.setModBy(userName);
		phoneData.setModDate(new Date());
		
		if (phoneDto.getPhoneId() == 0) {
			phoneData.setAddBy(userName);
			phoneData.setAddDate(new Date());
			PhoneDataAccess.insert(conn, phoneData);
		}else{
			PhoneDataAccess.update(conn, phoneData);
		}
	}
	
	private void createOrUpdateEmail(EmailDto emailDto, int busEntityId, int userId, String userName, String typeCd, String statusCd, Connection conn) throws SQLException, DataNotFoundException{
		EmailData emailData = emailDto.getEmailId() == 0 ? new EmailData() : EmailDataAccess.select(conn, emailDto.getEmailId());
		
		emailData.setEmailAddress(emailDto.getEmailAddress());
		emailData.setEmailTypeCd(typeCd);
		emailData.setEmailStatusCd(statusCd);
		emailData.setBusEntityId(busEntityId);
		emailData.setUserId(userId);
		
		if (emailDto.getEmailId() == 0) {
			emailData.setAddBy(userName);
			emailData.setAddDate(new Date());
			EmailDataAccess.insert(conn, emailData);
		}else{
			EmailDataAccess.update(conn, emailData);
		}
		emailData.setModBy(userName);
		emailData.setModDate(new Date());
	}

	
	private void createOrUpdateAddress(AddressDto addressDto, int busEntityId, int userId, String typeCd, String userName, Connection conn) throws SQLException, DataNotFoundException{
		AddressData addressData = addressDto.getAddressId() == 0 ? new AddressData() : AddressDataAccess.select(conn, addressDto.getAddressId());
		
		addressData.setAddressId(addressDto.getAddressId());
		addressData.setCountryCd(addressDto.getCountryCd());
		addressData.setAddressStatusCd(addressDto.getAddressStatusCd());
		addressData.setAddressTypeCd(typeCd);
		addressData.setName1(addressDto.getName1());
		addressData.setName2(addressDto.getName2());
		addressData.setAddress1(addressDto.getAddress1());
		addressData.setAddress2(addressDto.getAddress2());
		addressData.setAddress3(addressDto.getAddress3());
		addressData.setAddress4(addressDto.getAddress4());
		addressData.setCity(addressDto.getCity());
		addressData.setPostalCode(addressDto.getPostalCode());
		addressData.setModBy(userName);
		addressData.setModDate(new Date());
		addressData.setBusEntityId(busEntityId);
		addressData.setUserId(userId);
		
		if (addressDto.getAddressId() == 0) {
			addressData.setAddBy(userName);
			addressData.setAddDate(new Date());
			AddressDataAccess.insert(conn, addressData);
		}else{
			AddressDataAccess.update(conn, addressData);
		}
	}
	
	private void createOrUpdateProperty(PropertyDto propertyDto, int busEntityId, int userId, String userName, Connection conn) throws Exception {
		try {
			PropertyData propertyData = propertyDto.getPropertyId() == 0 ? new PropertyData() : PropertyDataAccess.select(conn, propertyDto.getPropertyId());

			propertyData.setBusEntityId(busEntityId);
			propertyData.setUserId(userId);
			propertyData.setPropertyTypeCd(propertyDto.getPropertyTypeCd1());
			propertyData.setShortDesc(propertyDto.getPropertyTypeCd2());
			propertyData.setValue(propertyDto.getValue());
			propertyData.setPropertyStatusCd(propertyDto.getPropertyStatusCd());
			propertyData.setLocaleCd(propertyDto.getLocale());
			propertyData.setModBy(userName);
			propertyData.setModDate(new Date());

			if (propertyDto.getPropertyId() == 0) {
				propertyData.setAddBy(userName);
				propertyData.setAddDate(new Date());
				PropertyDataAccess.insert(conn, propertyData);
			} else {
				PropertyDataAccess.update(conn, propertyData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	private void createOrUpdateProperty(List<PropertyDto> properties, int busEntityId, int userId, String userName, Connection conn) throws Exception{
		for(PropertyDto propertyDto : properties){
			createOrUpdateProperty(propertyDto, busEntityId, userId, userName, conn);
		}
	}
	
	public PhoneDto getPhone(Integer busEntityId, Integer userId, String shortDesc, Connection conn) throws RemoteException {
		 List<PhoneDto> phoneList = getPhones(busEntityId, userId, shortDesc, conn);
		 return phoneList.isEmpty() ? null : phoneList.get(0);
	}
	
	public List<PhoneDto> getPhones(Integer busEntityId, Integer userId, String shortDesc, Connection conn) throws RemoteException {
		List<PhoneDto> resultList = new ArrayList<PhoneDto>();
		try {
			DBCriteria dbCriteria = new DBCriteria();
			if(busEntityId != null)
				dbCriteria.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID, busEntityId);
			else if(userId != null)
				dbCriteria.addEqualTo(PhoneDataAccess.USER_ID, userId);
			
			if(shortDesc != null)
				dbCriteria.addEqualToIgnoreCase(PhoneDataAccess.SHORT_DESC, shortDesc);

			List<PhoneData> phoneList = PhoneDataAccess.select(conn, dbCriteria);
			for(PhoneData phoneData: phoneList){
				PhoneDto phoneDto = new PhoneDto();
				phoneDto.setPhoneId(phoneData.getPhoneId());
				phoneDto.setPhoneNum(phoneData.getPhoneNum());
				resultList.add(phoneDto);
			}
		} catch (Exception e) {
			throw new RemoteException("getPhone: " + e.getMessage());
		}
		return resultList;
	}
	
	public EmailDto getEmail(Integer busEntityId, Integer userId, String emailTypeCd, Connection conn) throws RemoteException {
		 List<EmailDto> emailList = getEmails(busEntityId, userId, emailTypeCd, conn);
		 return emailList.isEmpty() ? null : emailList.get(0);
	}
	
	public List<EmailDto> getEmails(Integer busEntityId, Integer userId, String emailTypeCd, Connection conn) throws RemoteException {
		List<EmailDto> resultList = new ArrayList<EmailDto>();
		try {
			DBCriteria dbCriteria = new DBCriteria();
			if(busEntityId != null)
				dbCriteria.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, busEntityId);
			else if(userId != null)
				dbCriteria.addEqualTo(EmailDataAccess.USER_ID, userId);
			
			if(emailTypeCd != null)
				dbCriteria.addEqualToIgnoreCase(EmailDataAccess.EMAIL_TYPE_CD, emailTypeCd);
				
			List<EmailData> emailList = EmailDataAccess.select(conn, dbCriteria);
			for(EmailData emailData: emailList){
				EmailDto emailDto = new EmailDto();
				emailDto.setEmailId(emailData.getEmailId());
				emailDto.setEmailAddress(emailData.getEmailAddress());
				resultList.add(emailDto);
			}
		} catch (Exception e) {
			throw new RemoteException("getEmails: " + e.getMessage());
		}
		return resultList;
	}

	
	private List<SearchCritCondition> getConditionsForNestedEntities(List<SearchCritCondition> allconditions){
		List<SearchCritCondition> result = new ArrayList<SearchCritCondition>();
		for(SearchCritCondition condition : allconditions){
			if (condition.getNestedEntity() > 0) result.add(condition);
		}
		return result;
	}
	
	public AddressDto getAddress(Integer busEntityId, Integer userId, String addressTypeCd, Connection conn) throws RemoteException {
		List<AddressDto> addresses =  getAddresses(busEntityId, userId, addressTypeCd, conn);
		return addresses.isEmpty() ? null : addresses.get(0);
	}
	
	public List<AddressDto> getAddresses(Integer busEntityId, Integer userId, String addressTypeCd, Connection conn) throws RemoteException {
		List<AddressDto> resultList = new ArrayList<AddressDto>();
		try{
			AddressDataAccess dao = new AddressDataAccess();
			DBCriteria dbCriteria = new DBCriteria();
			
			if (busEntityId != null && busEntityId > 0)
				dbCriteria.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, busEntityId);
			else if (userId != null && userId > 0)
				dbCriteria.addEqualTo(AddressDataAccess.USER_ID, userId);
			
//			dbCriteria.addEqualTo(AddressDataAccess.PRIMARY_IND, true);
			
			dbCriteria.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD, addressTypeCd);
			
			List addressList = dao.select(conn, dbCriteria);
			for(Object obj : addressList){
				AddressData addressData = (AddressData) obj;
				AddressDto addressDto = new AddressDto();
				
				addressDto.setAddressId(addressData.getAddressId());
				addressDto.setName1(addressData.getName1());
				addressDto.setName2(addressData.getName2());
				addressDto.setAddress1(addressData.getAddress1());
				addressDto.setAddress2(addressData.getAddress2());
				addressDto.setAddress3(addressData.getAddress3());
				addressDto.setAddress4(addressData.getAddress4());
				addressDto.setCity(addressData.getCity());
				addressDto.setStateProvinceCd(addressData.getStateProvinceCd());
				addressDto.setCountryCd(addressData.getCountryCd());
				addressDto.setPostalCode(addressData.getPostalCode());
				addressDto.setAddressStatusCd(addressData.getAddressStatusCd());
				resultList.add(addressDto);
			}
		}catch (Exception e) {
			throw new RemoteException("getAddresss: " + e.getMessage());
		}
		
		if(resultList.size() > 1){
			// We should to do something... TBD
		}
		return resultList;
	}

	private List<PropertyDto> getProperties(Integer busEntityId, Integer userId, Connection conn) throws RemoteException {
		List<PropertyDto> resultList = new ArrayList<PropertyDto>();
		try{
			PropertyDataAccess dao = new PropertyDataAccess();
			DBCriteria criteria = new DBCriteria();
			if(busEntityId != null){
				criteria.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, busEntityId);
			}else if(userId != null){
				criteria.addEqualTo(PropertyDataAccess.USER_ID, userId);
			}
			List propertyList = dao.select(conn, criteria);
			for(Object obj : propertyList){
				PropertyData pd = (PropertyData) obj;
				PropertyDto pdto = new PropertyDto();
				
				pdto.setPropertyId(pd.getPropertyId());
				pdto.setPropertyTypeCd1(pd.getPropertyTypeCd());
				pdto.setPropertyTypeCd2(pd.getShortDesc());
				pdto.setValue(pd.getValue());
				pdto.setPropertyStatusCd(pd.getPropertyStatusCd());
				pdto.setLocale(pd.getLocaleCd());
				resultList.add(pdto);
			}
		}catch (Exception e) {
			throw new RemoteException("getProperties: " + e.getMessage());
		}
		return resultList ;		
	}
	
	private DBCriteria convertSearchCritToDbCriteria(SearchCrit searchCrit){
		DBCriteria dbCriteria  = new DBCriteria();
		for(SearchCritCondition condition : searchCrit.getSearchCritConditions()){
			log.info("convertSearchCritToDbCriteria -> condition = " + condition);
			if (condition.getNestedEntity() > 0) continue;
			this.addConditionToDBCriteria(dbCriteria, condition);
		}		
		return dbCriteria;
	}
	
	private void addConditionToDBCriteria(DBCriteria dbCriteria, SearchCritCondition condition){
		addConditionToDBCriteria(dbCriteria, condition, null);
	}
	
	private void addConditionToDBCriteria(DBCriteria dbCriteria, SearchCritCondition condition, String alias){
		String searchType = (alias != null && alias.length() > 0) ? (alias + "." + condition.getSearchType()) : condition.getSearchType();
			
		switch (condition.getSearchOperator()) {
		case SearchCritCondition.EQUAL:
			dbCriteria.addEqualTo(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.EQUAL_IGNORE_CASE:
			dbCriteria.addEqualToIgnoreCase(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.NOT_EQUAL:
			dbCriteria.addNotEqualTo(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.NOT_EQUAL_IGNORE_CASE:
			dbCriteria.addNotEqualToIgnoreCase(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.GREATER_THAN:
			dbCriteria.addGreaterThan(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.GREATER_OR_EQUAL:
			dbCriteria.addGreaterOrEqual(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.LESS_THAN:
			dbCriteria.addLessThan(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.LESS_OR_EQUAL:
			dbCriteria.addLessOrEqual(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.LIKE:
			dbCriteria.addLike(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.LIKE_IGNORE_CASE:
			dbCriteria.addLikeIgnoreCase(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.IS_NULL:
			dbCriteria.addIsNull(searchType);
			break;
		case SearchCritCondition.IS_NOT_NULL:
			dbCriteria.addIsNotNull(searchType);
			break;
		case SearchCritCondition.IS_NULL_OR_0:
			dbCriteria.addIsNullOr0(searchType);
			break;
		case SearchCritCondition.IS_NULL_OR_SPACE:
			dbCriteria.addIsNullOrSpace(searchType);
			break;				
		case SearchCritCondition.CONTAINS:
			dbCriteria.addContains(searchType, condition.getSearchValue());
			break;
		case SearchCritCondition.CONTAINS_IGNORE_CASE:
			dbCriteria.addContainsIgnoreCase(searchType, condition.getSearchValue());
			break;
			
		/// to be done.....

		default:
			break;
		}
	}
	
	public List<UserDto> getUsers(SearchCrit searchCrit, int storeId) throws RemoteException {
		Connection conn = null;
		List<UserDto> resultList = new ArrayList<UserDto>();
		try{
			conn = getConnection();
			List<UserData> users = getUserData(searchCrit, storeId, conn, getConditionsForNestedEntities(searchCrit.getSearchCritConditions()));
			for(UserData userData : users){
				UserDto userDto = new UserDto(); 
				userDto.setUserId(userData.getUserId());
				userDto.setFirstName(userData.getFirstName());
				userDto.setLastName(userData.getLastName());
				userDto.setUserName(userData.getUserName());
				userDto.setPassword(userData.getPassword());
				if(userData.getEffDate()!=null)
				userDto.setEffDate(userData.getEffDate().getTime());
				if(userData.getExpDate()!=null)
				userDto.setExpDate(userData.getExpDate().getTime());
				userDto.setUserStatusCd(userData.getUserStatusCd());
				UserRightsTool userRightsTool = new UserRightsTool(userData);
				userDto.setOnAccount(userRightsTool.getOnAccount());
				userDto.setOtherPayment(userRightsTool.getOtherPaymentFlag());
				userDto.setPoNumberRequiered(userRightsTool.getPoNumRequired());				
				
				Integer userId = userData.getUserId();
				userDto.setUserTypeCd(userData.getUserTypeCd());
				userDto.setEmailPrimary(this.getEmail(null, userId, RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT, conn));
				userDto.setPhonePrimary(this.getPhone(null, userId, RefCodeNames.PHONE_TYPE_CD.PHONE, conn));
				userDto.setPhoneFax(this.getPhone(null, userId, RefCodeNames.PHONE_TYPE_CD.FAX, conn));
				userDto.setPhoneMobile(this.getPhone(null, userId, RefCodeNames.PHONE_TYPE_CD.MOBILE, conn));
				userDto.setAddressPrimaryContact(this.getAddress(null, userId, RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT, conn));
				userDto.setUserProperties(this.getProperties(null, userId, conn));
				
				resultList.add(userDto);
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("getUsers: " + e.getMessage());
		}finally {
	        closeConnection(conn);
	    }
		return resultList ;
	}
	
	private List<UserData> getUserData(SearchCrit searchCrit, int storeId, Connection conn, List<SearchCritCondition> conditionsForNestedEntities) throws SQLException{
		DBCriteria dbCriteria = convertSearchCritToDbCriteria(searchCrit);
		dbCriteria.addJoinTable(UserAssocDataAccess.CLW_USER_ASSOC);

		DBCriteria storeCriteria = new DBCriteria();
		storeCriteria.addJoinCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, UserAssocDataAccess.CLW_USER_ASSOC, UserAssocDataAccess.USER_ID);
		storeCriteria.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.BUS_ENTITY_ID, storeId);
		storeCriteria.addEqualTo(UserAssocDataAccess.CLW_USER_ASSOC + "." + UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.STORE);
		dbCriteria.addIsolatedCriterita(storeCriteria);
		
		if(conditionsForNestedEntities != null && !conditionsForNestedEntities.isEmpty()){
			DBCriteria addressCriteria = null, 
					   phoneCriteria = null, 
					   emailCriteria = null, 
					   propertyCriteria = null;
			for(SearchCritCondition condition : conditionsForNestedEntities){
				switch (condition.getNestedEntity()) {
				case SearchCritCondition.ADDRESS:
					if (addressCriteria == null) {
						addressCriteria = new DBCriteria();
						dbCriteria.addJoinTable(AddressDataAccess.CLW_ADDRESS);
						addressCriteria.addJoinCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, AddressDataAccess.CLW_ADDRESS, AddressDataAccess.USER_ID);
					}
					addConditionToDBCriteria(addressCriteria, condition);
					break;
				case SearchCritCondition.PHONE:
					if (phoneCriteria == null) {
						phoneCriteria = new DBCriteria();
						dbCriteria.addJoinTable(PhoneDataAccess.CLW_PHONE);
						phoneCriteria.addJoinCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, PhoneDataAccess.CLW_PHONE, PhoneDataAccess.USER_ID);
					}
					addConditionToDBCriteria(phoneCriteria, condition);
					break;
				case SearchCritCondition.EMAIL:
					if (emailCriteria == null) {
						emailCriteria = new DBCriteria();
						dbCriteria.addJoinTable(EmailDataAccess.CLW_EMAIL);
						emailCriteria.addJoinCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, EmailDataAccess.CLW_EMAIL, EmailDataAccess.USER_ID);
					}
					addConditionToDBCriteria(emailCriteria, condition);
					break;
				case SearchCritCondition.PROPERTY:
					if (propertyCriteria == null) {
						propertyCriteria = new DBCriteria();
						dbCriteria.addJoinTable(PropertyDataAccess.CLW_PROPERTY);
						propertyCriteria.addJoinCondition(UserDataAccess.CLW_USER + "." + UserDataAccess.USER_ID, PropertyDataAccess.CLW_PROPERTY, AddressDataAccess.USER_ID);
					}
					addConditionToDBCriteria(propertyCriteria, condition);
					break;
				default:
					break;
				}
			}
			if(addressCriteria != null) dbCriteria.addIsolatedCriterita(addressCriteria);
			if(phoneCriteria != null) dbCriteria.addIsolatedCriterita(phoneCriteria);
			if(emailCriteria != null) dbCriteria.addIsolatedCriterita(emailCriteria);
			if(propertyCriteria != null) dbCriteria.addIsolatedCriterita(propertyCriteria);
		}
		
		return UserDataAccess.select(conn, dbCriteria);
	}
	
	private void saveAccountAssoc(int storeId, AccountDto dto, String userName, Connection conn) throws Exception{
			if(dto.getBusEntityId() == 0) throw new Exception("AccountId equals 0");
			BusEntityAssocDataAccess dao = new BusEntityAssocDataAccess();
			
			String busEntityAssocCd = RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE;
			//search already exist
			DBCriteria dbCriteria = new DBCriteria();
			dbCriteria.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dto.getBusEntityId());
			dbCriteria.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, busEntityAssocCd);
			
			List<BusEntityAssocData> assoces = BusEntityAssocDataAccess.select(conn, dbCriteria);
			BusEntityAssocData assocData = assoces.isEmpty() ? new BusEntityAssocData() : assoces.get(0);
			
		    assocData.setBusEntity1Id(dto.getBusEntityId());
		    assocData.setBusEntity2Id(storeId);
		    assocData.setBusEntityAssocCd(busEntityAssocCd);
		    assocData.setAddDate(new Date());
		    assocData.setAddBy(userName);
		    assocData.setModDate(new Date());
		    assocData.setModBy(userName);
		    
			if(assocData.getBusEntityAssocId() == 0)
				dao.insert(conn, assocData);
			else
				dao.update(conn, assocData);
	}
	private void saveManufacturerAssoc(int storeId, ManufacturerDto dto, String userName, Connection conn) throws Exception{
			if(dto.getBusEntityId() == 0)throw new Exception("ManufacturerId equals 0");
			BusEntityAssocDataAccess dao = new BusEntityAssocDataAccess();
			
			String busEntityAssocCd = RefCodeNames.BUS_ENTITY_ASSOC_CD.MANUFACTURER_STORE;
			//search already exist
			DBCriteria dbCriteria = new DBCriteria();
			dbCriteria.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dto.getBusEntityId());
			dbCriteria.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, busEntityAssocCd);
			
			List<BusEntityAssocData> assoces = BusEntityAssocDataAccess.select(conn, dbCriteria);
			BusEntityAssocData assocData = assoces.isEmpty() ? new BusEntityAssocData() : assoces.get(0);
			
			assocData.setBusEntity1Id(dto.getBusEntityId());
		    assocData.setBusEntity2Id(storeId);
		    assocData.setBusEntityAssocCd(busEntityAssocCd);
		    assocData.setAddDate(new Date());
		    assocData.setAddBy(userName);
		    assocData.setModDate(new Date());
		    assocData.setModBy(userName);

			if(assocData.getBusEntityAssocId() == 0)
				dao.insert(conn, assocData);
			else
				dao.update(conn, assocData);
	}

	private void saveSiteAssoc(int accountId, SiteDto dto, String userName, Connection conn) throws Exception {
		try {
			if (accountId == 0) throw new DataNotFoundException("Bad accountId");
			BusEntityAssocDataAccess dao = new BusEntityAssocDataAccess();

			String busEntityAssocCd = RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT;
			// search already exist
			DBCriteria dbCriteria = new DBCriteria();
			dbCriteria.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, dto.getBusEntityId());
			dbCriteria.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, busEntityAssocCd);
			
			List<BusEntityAssocData> assoces = BusEntityAssocDataAccess.select(conn, dbCriteria);
			BusEntityAssocData assocData = assoces.isEmpty() ? new BusEntityAssocData() : assoces.get(0);
			
			assocData.setBusEntity1Id(dto.getBusEntityId());
			assocData.setBusEntity2Id(accountId);
			assocData.setBusEntityAssocCd(busEntityAssocCd);
			assocData.setAddDate(new Date());
			assocData.setAddBy(userName);
			assocData.setModDate(new Date());
			assocData.setModBy(userName);
			if(assocData.getBusEntityAssocId() == 0)
				dao.insert(conn, assocData);
			else
				dao.update(conn, assocData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	private void saveUserAssoc(int storeId, UserDto dto, String userName, Connection conn) throws Exception{
		UserDto userDto = (UserDto) dto;
		if(userDto.getUserId() == 0) throw new Exception("UserId equals 0");
		UserAssocDataAccess dao = new UserAssocDataAccess();
		
		String busEntityAssocCd = RefCodeNames.USER_ASSOC_CD.STORE;
		//search already exist
		DBCriteria dbCriteria = new DBCriteria();
		dbCriteria.addEqualTo(UserAssocDataAccess.USER_ID, dto.getUserId());
		dbCriteria.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, busEntityAssocCd);
		List<UserAssocData> assoces = dao.select(conn, dbCriteria);
		UserAssocData assocData = assoces.isEmpty() ? new UserAssocData() : assoces.get(0);

		assocData.setUserId(dto.getUserId());
	    assocData.setBusEntityId(storeId);
	    assocData.setUserAssocCd(busEntityAssocCd);
	    assocData.setAddDate(new Date());
	    assocData.setAddBy(userName);
	    assocData.setModDate(new Date());
	    assocData.setModBy(userName);

	    if(assocData.getUserAssocId() == 0){
	    	dao.insert(conn, assocData);
	    }else{
	    	dao.update(conn, assocData);
	    }
	}
	
}
