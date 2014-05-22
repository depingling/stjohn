package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.UserAssocDataAccess;

import com.cleanwise.service.apps.loaders.TabFileParser;

import java.rmi.RemoteException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InboundXpedxUser extends InboundFlatFile {
	protected Logger log = Logger.getLogger(this.getClass());
    private String USER_LOADER = "Xpedx User Loader";

    private boolean DEFAULT_SHOW_PRICE = true;
    private boolean DEFAULT_CONTRACT_ITEMS_ONLY = true;
    private boolean DEFAULT_ON_ACCOUNT = true;
    private IdVector storeIds = null;
    
    //private UserInfoDataVector users = new UserInfoDataVector();

  /**
   * Called when the object has successfully been parsed
   */
    protected void processParsedObject(Object pParsedObject)  throws Exception{
        log.info("Start process parsed object ...");
        if(pParsedObject == null)
            throw new IllegalArgumentException("No parsed site object present");

        if (pParsedObject instanceof XpedxUserView){
            XpedxUserView userView = (XpedxUserView)pParsedObject;

                userView = checkMandatory(userView);
                 
                if (storeIds == null){
	                //Getting trading partner id
	                TradingPartnerData partner = translator.getPartner();
	                if(partner == null)
	                    throw new IllegalArgumentException("Trading Partner ID cann't be determined");
	                int tradingPartnerId = partner.getTradingPartnerId();
	                log.info("Found Trading Partner id = " + tradingPartnerId);
	                
	                TradingPartner tradingEjb=APIAccess.getAPIAccess().getTradingPartnerAPI();
	                HashMap mTradPartnerAssoc  = tradingEjb.getMapTradingPartnerAssocIds(tradingPartnerId);                
	                storeIds       = (IdVector) mTradPartnerAssoc.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
	                if (storeIds.size() == 0)
	                	throw new IllegalArgumentException("Trading Partner " + tradingPartnerId + " need to be associated with a store");
	            }
                //verify user or create new if it doesn't exist
                updateUserInfo(userView, storeIds);
                
        }
    }    
        

    private void updateUserInfo(XpedxUserView beanToSave, IdVector storeIds)
                 throws Exception{

         Date now = new Date();
            User userEjb = APIAccess.getAPIAccess().getUserAPI();
            UserData userD = null;
            UserInfoData userInfoD = null;
            
                UserDataVector usersDV = userEjb.getUsersByName(beanToSave.getUsername(),
                                                                0,
                                                                User.NAME_EXACT,
                                                                User.ORDER_BY_NAME);
                if (usersDV.size() > 0) { // check store association  
                	userInfoD = userEjb.getUserContact(((UserData)usersDV.get(0)).getUserId());
                	userD = userInfoD.getUserData();
            		UserAssocDataVector uaDV = userEjb.getUserAssocCollecton(userD.getUserId(), RefCodeNames.USER_ASSOC_CD.STORE);
            		if (uaDV.size() > 0){
            			boolean found = false;
            			for (int i = 0; i < storeIds.size(); i++){
            				Integer pBusEntityId = (Integer)storeIds.get(i);
            				if (userBusEntityAssocFound(uaDV, pBusEntityId.intValue())){
            					found = true;
            					break;
            				}
            			}
            			if (!found)
            				throw new Exception("User (" + beanToSave.getUsername() + ") already exist in different store");
            		}
                }
                // prepare the new user data
                UserData newUserD = UserData.createValue();
                newUserD.setUserId(0);
                newUserD.setAddBy(USER_LOADER);
                newUserD.setAddDate(now);
                newUserD.setLastActivityDate(now);
                newUserD.setModDate(now);
                newUserD.setModBy(USER_LOADER);
                newUserD.setFirstName(beanToSave.getFirstName());
                newUserD.setLastName(beanToSave.getLastName());
                newUserD.setUserName(beanToSave.getUsername());
                newUserD.setPassword(PasswordUtil.getHash(beanToSave.getUsername(), beanToSave.getPassword()));
                newUserD.setPrefLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
                newUserD.setUserStatusCd(RefCodeNames.USER_STATUS_CD.ACTIVE);
                newUserD.setUserTypeCd(RefCodeNames.USER_TYPE_CD.MSB);
                newUserD.setEffDate(now);
                // user rights
                UserRightsTool tool = new UserRightsTool(newUserD);
                tool.setShowPrice(DEFAULT_SHOW_PRICE);
                tool.setUserOnContract(DEFAULT_CONTRACT_ITEMS_ONLY);
                tool.setOnAccount(DEFAULT_ON_ACCOUNT);
                tool.setCanApprovePurchases(beanToSave.getApprover().booleanValue());
                tool.setEmailOrderDetailNotification(beanToSave.getOtherDetailNotification().booleanValue());
                tool.setOrderNotificationShipped(beanToSave.getShippingNotification().booleanValue());
                tool.setEmailForApproval(beanToSave.getNeedsApproval().booleanValue());
                tool.setEmailOrderApproved(beanToSave.getWasApproved().booleanValue());
                tool.setEmailOrderRejection(beanToSave.getWasRejected().booleanValue());
                tool.setEmailOrderModifications(beanToSave.getWasModified().booleanValue());
                String userRights = tool.makePermissionsToken();
                newUserD.setUserRoleCd(userRights);
                if (beanToSave.getApprover().booleanValue()) {
                    newUserD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
                }

                
                if (userD != null) {   // Update User Information               	

                    // modify user information
                    userD.setAddBy(newUserD.getAddBy());
                    userD.setAddDate(newUserD.getAddDate());
                    userD.setLastActivityDate(newUserD.getLastActivityDate());
                    userD.setModDate(newUserD.getModDate());
                    userD.setModBy(newUserD.getModBy());
                    userD.setFirstName(newUserD.getFirstName());
                    userD.setLastName(newUserD.getLastName());
                    userD.setUserName(newUserD.getUserName());
                    userD.setPassword(newUserD.getPassword());
                    userD.setPrefLocaleCd(newUserD.getPrefLocaleCd());                    
                    userD.setUserStatusCd(newUserD.getUserStatusCd());
                    userD.setUserTypeCd(newUserD.getUserTypeCd());
                    userD.setEffDate(newUserD.getEffDate());
                    userD.setUserRoleCd(newUserD.getUserRoleCd());
                    if (beanToSave.getApprover().booleanValue()) {
                        userD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER);
                    }
                } else {    // create a new User
                    userInfoD = UserInfoData.createValue();
                    newUserD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
                    userInfoD.setUserData(newUserD);

                    // add User
                    userInfoD = userEjb.addUserInfo(userInfoD);

                }

                int userId = userInfoD.getUserData().getUserId();

                // update EmailInformation
                EmailData emailD = userInfoD.getEmailData();
                if (emailD == null ) {
                    emailD = EmailData.createValue();
                    emailD.setAddBy(USER_LOADER);
                    emailD.setAddDate(now);
                    userInfoD.setEmailData(emailD);
                }
                emailD.setModBy(USER_LOADER);
                emailD.setModDate(now);
                emailD.setUserId(userId);
                String emailAddress = beanToSave.getEmail();
                String emails[] = emailAddress.split(",");                
                emailD.setEmailAddress(emails[0]);

                // update phone information
                PhoneData phoneD = userInfoD.getPhone();
                if (phoneD == null) {
                    phoneD = PhoneData.createValue();
                    phoneD.setAddBy(USER_LOADER);
                    phoneD.setAddDate(now);
                    userInfoD.setPhone(phoneD);
                }
                phoneD.setModBy(USER_LOADER);
                phoneD.setModDate(now);
                phoneD.setUserId(userId);
                phoneD.setPhoneNum(beanToSave.getPhone());

                // update address data
                AddressData addressD = userInfoD.getAddressData();
                if (addressD.getAddressId()  == 0) {
                    addressD = AddressData.createValue();
                    addressD.setAddBy(USER_LOADER);
                    addressD.setAddDate(now);
                    userInfoD.setAddressData(addressD);
                }
                addressD.setUserId(userId);
                addressD.setModBy(newUserD.getModBy());
                addressD.setModDate(newUserD.getModDate());
                addressD.setName1(newUserD.getFirstName());
                addressD.setName2(newUserD.getLastName());
                addressD.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                addressD.setAddress1(beanToSave.getAddress1());
                addressD.setAddress2(beanToSave.getAddress2());
                addressD.setCity(beanToSave.getCity());
                addressD.setStateProvinceCd(beanToSave.getState());
                addressD.setCountryCd(beanToSave.getCountry());
                addressD.setPostalCode(beanToSave.getPostalCode());

                // update user
                userInfoD = userEjb.updateUserInfo(userInfoD);

                // add or update user associations
                // accounts
                addUserAssoc(userId, beanToSave.getAccountId(), RefCodeNames.USER_ASSOC_CD.ACCOUNT, userEjb);
                // sites
                if (beanToSave.getSiteId() > 0) {
                    addUserAssoc(userId, beanToSave.getSiteId(), RefCodeNames.USER_ASSOC_CD.SITE, userEjb);
                }
                // store
                addUserAssoc(userId, beanToSave.getStoreId(), RefCodeNames.USER_ASSOC_CD.STORE, userEjb);
    }


    private XpedxUserView checkMandatory(XpedxUserView userView) throws Exception {
        // store id
        String errMessage = "Error parsing file for store id "+ userView.getStoreId() + ": mandatory field ";
        if (userView.getStoreId() <= 0 ) {
            throw new IllegalArgumentException("Store ID not found in the parsig file.");
        }
        // account id
        if (userView.getAccountId() <= 0) {
            throw new IllegalArgumentException(errMessage + "Account ID");
        }
        // username
        if (!Utility.isSet(userView.getUsername())) {
            throw new IllegalArgumentException(errMessage + "User Name");
        }
        // password
        if (!Utility.isSet(userView.getPassword())) {
            throw new IllegalArgumentException(errMessage + "Password");
        }
        // pref language
        if (!Utility.isSet(userView.getPrefLanguage())) {
            throw new IllegalArgumentException(errMessage + "Preferred Language");
        }
        // first name
        if (!Utility.isSet(userView.getFirstName())) {
            throw new IllegalArgumentException(errMessage + "First Name");
        }
        // last name
        if (!Utility.isSet(userView.getLastName())) {
            throw new IllegalArgumentException(errMessage + "Last Name");
        }
        // address 1
        if (!Utility.isSet(userView.getAddress1())) {
            throw new IllegalArgumentException(errMessage + "Street Address 1");
        }
        // City
        if (!Utility.isSet(userView.getCity())) {
            throw new IllegalArgumentException(errMessage + "City");
        }
        // Postal Code
        if (!Utility.isSet(userView.getPostalCode())) {
            throw new IllegalArgumentException(errMessage + "Postal Code");
        }
        // Country
        if (!Utility.isSet(userView.getCountry())) {
            throw new IllegalArgumentException(errMessage + "Country");
        }
        if("USA".equals(userView.getCountry())) {
            userView.setCountry(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
        }
        // State (if country required state)
        Country countryEjb = APIAccess.getAPIAccess().getCountryAPI();
        CountryData countryD = countryEjb.getCountryByShortDesc(userView.getCountry());
        if (countryD != null) {
            CountryPropertyData countryProp =  countryEjb.getCountryPropertyData(countryD.getCountryId(), RefCodeNames.COUNTRY_PROPERTY.USES_STATE);
            boolean isStateProvinceRequired = countryProp != null && Utility.isTrue(countryProp.getValue());
            if (isStateProvinceRequired && !Utility.isSet(userView.getState())) {
                throw new IllegalArgumentException(errMessage + "State");
            }
        }
        // Phone
        if (!Utility.isSet(userView.getPhone())) {
            throw new IllegalArgumentException(errMessage + "Phone");
        }
        // Approver
        if (userView.getApprover() == null) {
            throw new IllegalArgumentException(errMessage + "Approver");
        }
        // Order Detail Notification
        if (userView.getOtherDetailNotification() == null) {
            throw new IllegalArgumentException(errMessage + "Order Detail Notification");
        }
        // Shipping Notification
        if (userView.getShippingNotification() == null) {
            throw new IllegalArgumentException(errMessage + "Shipping Notification");
        }
        // Needs Approval
        if (userView.getNeedsApproval() == null) {
            throw new IllegalArgumentException(errMessage + "Needs Approval");
        }
        // Was Approved
        if (userView.getWasApproved() == null) {
            throw new IllegalArgumentException(errMessage + "Was Approved");
        }
        // Was Rejected
        if (userView.getWasRejected() == null) {
            throw new IllegalArgumentException(errMessage + "Was Rejected");
        }
        // Was Modified
        if (userView.getWasModified() == null) {
            throw new IllegalArgumentException(errMessage + "Was Modified");
        }
        return userView;
    }

   private void addUserAssoc(int pUserId, int pBusEntityId, String pAssocType, User pUserEjb) throws RemoteException{
       UserAssocDataVector accAssocV = pUserEjb.getUserAssocCollecton(pUserId, pAssocType);
       boolean found = userBusEntityAssocFound(accAssocV, pBusEntityId);
       if (!found) {
          IdVector toAdd = Utility.toIdVector(pBusEntityId);
          pUserEjb.addBusEntityAssociations(pUserId, toAdd, pAssocType, USER_LOADER);
       }
   }
   
   private boolean userBusEntityAssocFound(UserAssocDataVector userAssocDV,int pBusEntityId ){
	   Iterator i = userAssocDV.iterator();
       while (i.hasNext()) {
           UserAssocData assocD = (UserAssocData)i.next();
           if (assocD.getBusEntityId() == pBusEntityId) {
               return true;
           }
       }
       return false;
   }


}
