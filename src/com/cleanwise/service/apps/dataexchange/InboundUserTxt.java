package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;

import com.cleanwise.service.apps.loaders.TabFileParser;

import java.rmi.RemoteException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InboundUserTxt extends InboundFlatFile {
    //private final static String MEMBER_DELIMITER = "*";
    private final String USER_LOADER = "Nsc User Loader";
    protected final String USER_ROLE_CD = "SP^CI^OA^PR^";
    protected final String DATE_FORMAT = "MM/dd/yyyy";

    protected Logger log = Logger.getLogger(this.getClass());

    protected int storeId = 0;
    protected int distributorId = 0;
    protected Date effDate = null;

    protected Distributor distrEjb;
    protected CatalogInformation catalogInfoEjb;
    protected Catalog catalogEjb;
    protected Account accountEjb;
    protected Site siteEjb;
    protected User userEjb;
    protected HashMap catalogHM = new HashMap();
    protected NscUsViewVector nscUsers = new NscUsViewVector();


    public void doPreProcessing() throws Exception {

        accountEjb = APIAccess.getAPIAccess().getAccountAPI();
        catalogInfoEjb = APIAccess.getAPIAccess().getCatalogInformationAPI();
        catalogEjb = APIAccess.getAPIAccess().getCatalogAPI();
        distrEjb = APIAccess.getAPIAccess().getDistributorAPI();
        siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        userEjb = APIAccess.getAPIAccess().getUserAPI();
        //manufacturerEjb = APIAccess.getAPIAccess().getManufacturerAPI();


        TradingPartnerData partner = translator.getPartner();
        if (partner == null) {
            throw new IllegalArgumentException("Trading Partner ID cann't be determined");
        }
        //Find storeId
        int tradingPartnerId = partner.getTradingPartnerId();
        log.info("Found Trading Partner id = " + tradingPartnerId);
        if (partner.getTradingPartnerTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE)) {
            int[] storeIds;
            storeIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
            if (storeIds == null || storeIds.length == 0) {
                throw new IllegalArgumentException("Trading partner with type STORE is not associated with any store. " +
                        "Trading parther id: " + tradingPartnerId);
            } else if (storeIds.length > 1) {
                throw new IllegalArgumentException("Trading partner with type STORE associated with multiple stores. " +
                        "Trading parther id: " + tradingPartnerId);
            }

            storeId = storeIds[0];
            log.info("Inbount Item Txt STORE  id= " + storeId);

        } else if (partner.getTradingPartnerTypeCd().equals(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT)) {
            storeId = APIAccess.getAPIAccess().getStoreAPI().getStoreIdByAccount(tradingPartnerId);
        } else {
            throw new IllegalArgumentException(
                    "Trading partner must have type of ACCOUNT or STORE. Trading parther id: " + tradingPartnerId);
        }
        //Get store catalog id
        //storeCatalogId = catalogInfoEjb.getStoreCatalogId(storeId);

        //Get distributor
        BusEntityDataVector beDV = distrEjb.getNscStoreDistributor(storeId);
        if(beDV.size()==0) {
            throw new IllegalArgumentException(
                    "No disributor found for the store. Store id : " + storeId);
        }
        if(beDV.size()>1) {
            throw new IllegalArgumentException(
                    "Multiple disributors found for the store (should be one. Store id : " + storeId);
        }
        BusEntityData beD = (BusEntityData) beDV.get(0);
        distributorId = beD.getBusEntityId();

    }


    /**
   * Called when the object has successfully been parsed
   */
    protected void processParsedObject(Object pParsedObject)
    throws Exception
    {
        if (pParsedObject instanceof NscUsView){
            //Remove nulls and trim
             NscUsView nscUsVw = (NscUsView) pParsedObject;
             String contactName = Utility.strNN(nscUsVw.getContactName()).trim();
             nscUsVw.setContactName(contactName);
             String catalogName = Utility.strNN(nscUsVw.getCatalogName()).trim();
             nscUsVw.setCatalogName(catalogName);
             String customerNumber = Utility.strNN(nscUsVw.getCustomerNumber()).trim();
             nscUsVw.setCustomerNumber(customerNumber);
             String emailAddress = Utility.strNN(nscUsVw.getEmailAddress()).trim();
             nscUsVw.setEmailAddress(emailAddress);
             String locationNumber = Utility.strNN(nscUsVw.getLocationNumber()).trim();
             nscUsVw.setLocationNumber(locationNumber);
             String memberNumber = Utility.strNN(nscUsVw.getMemberNumber()).trim();
             nscUsVw.setMemberNumber(memberNumber);
             String userName = Utility.strNN(nscUsVw.getUserName()).trim();
             nscUsVw.setUserName(userName);
             String userPasswordCoded = PasswordUtil.getHash(userName, locationNumber);
             nscUsVw.setPassword(userPasswordCoded);

             String firstName;
             String lastName;
             int index;
             if ((index = contactName.lastIndexOf(' ')) > -1) {
                firstName = contactName.substring(0, index).trim();
                lastName = contactName.substring(index + 1).trim();
             } else {
                firstName = contactName;
                lastName = "N/A";
             }
             nscUsVw.setFirstName(firstName);
             nscUsVw.setLastName(lastName);
             nscUsers.add(nscUsVw);

        } else {
            throw new Exception("Found object of wrong type: "+pParsedObject.getClass().getName());
        }
    }
    protected String makeErrorStr(List pErrAL) {
        StringBuffer errors = new StringBuffer();
        errors.append("Errors: ");
        for(Iterator iter=pErrAL.iterator(); iter.hasNext();) {
            errors.append(iter.next());
            errors.append("\r\n");
        }
        return errors.toString();
    }

    protected void doPostProcessing()
    throws Exception
    {
        HashMap custMajHM = new HashMap();
        HashSet memberNumHS = new HashSet();
        HashMap userNameHM = new HashMap();
        ArrayList errorAL = new ArrayList();
        int lineNum = 0;
        log.info("InboundUserTxt IIIIIIIIIIIIII Start Vaildation Proceess");
        for(Iterator iter=nscUsers.iterator(); iter.hasNext();) {
            NscUsView nscUsVw = (NscUsView) iter.next();
            lineNum++;

            String contactName = nscUsVw.getContactName();
            if(contactName.length()==0) {
                String errorMess = "Empty Contact Name. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            String catalogName = nscUsVw.getCatalogName();
            if(catalogName.length()==0) {
                String errorMess = "Empty Catalog Name. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            String customerNumber = nscUsVw.getCustomerNumber();
            if(customerNumber.length()==0) {
                String errorMess = "Empty Customer Number. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            String emailAddress = nscUsVw.getEmailAddress();
            if(emailAddress.length()==0) {
                String errorMess = "Empty Email Address. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            if(emailAddress.length()>4000) {
                String errorMess = "Email Address is longer than 4000 characters: "+emailAddress.length()+"  Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            /*if (Utility.isSet(emailAddress) && !Utility.isValidEmailAddress(emailAddress)) {
                String errorMess = "Email Address has incorrect format: '"
                        + emailAddress + "'  Line num: " + lineNum;
                errorAL.add(errorMess);
            }*/
            String locationNumber = nscUsVw.getLocationNumber();
            if(locationNumber.length()==0) {
                String errorMess = "Empty Location Number. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            String memberNumber = nscUsVw.getMemberNumber();
            if(memberNumber.length()==0) {
                String errorMess = "Empty Servicing Agent Number. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            String userName = nscUsVw.getUserName();
            if(userName.length()==0) {
                String errorMess = "Empty User Name (id). Line num: "+lineNum;
                errorAL.add(errorMess);
            }

            HashSet siteNumHS = (HashSet) custMajHM.get(customerNumber);
            if(siteNumHS==null) {
                siteNumHS = new HashSet();
                siteNumHS.add(locationNumber);
                custMajHM.put(customerNumber,siteNumHS);
            } else {
                if(!siteNumHS.contains(locationNumber)) {
                    siteNumHS.add(locationNumber);
                }
            }
            if(!memberNumHS.contains(memberNumber)) {
                memberNumHS.add(memberNumber);
            }
            if(!userNameHM.containsKey(userName)) {
                userNameHM.put(userName,nscUsVw);
            } else {
                NscUsView nuVw = (NscUsView) userNameHM.get(userName);
                if(!nuVw.getPassword().equals(nscUsVw.getPassword())) {
                    String errorMess = "The same user name used with different passwords. User Name: "+userName;
                    errorAL.add(errorMess);
                }
                if(!nuVw.getContactName().equals(nscUsVw.getContactName())) {
                    String errorMess = "The same user name used with different contact names. User Name: "+userName;
                    errorAL.add(errorMess);
                }
                if(!nuVw.getEmailAddress().equals(nscUsVw.getEmailAddress())) {
                    String errorMess = "The same user name used with different email addresses. User Name: "+userName;
                    errorAL.add(errorMess);
                }
            }
        }

        if(errorAL.size()>0) {
            String errorMess = makeErrorStr(errorAL);
            throw new Exception(errorMess);
        }

        //Save users
        List errors = userEjb.saveNscUsers(nscUsers, storeId, distributorId);
        if(errors.size()>0) {
            String errorMess = makeErrorStr(errors);
            throw new Exception(errorMess);
        }

    }




    public void translate(InputStream pIn, String pStreamType) throws Exception {
        doPreProcessing();
        TabFileParser parser = new TabFileParser();
        parser.parse(pIn);
        parser.cleanUpResult();
        parser.processParsedStrings(this);
        doPostProcessing();
    }


    protected void updateUserInfo(NscUsView nscUsVw, int siteId, int accountId, int storeId)
                 throws Exception{

        String userLoginName = Utility.strNN(nscUsVw.getUserName()).trim();
         if (userLoginName.length() > 0) {
            String USER_ROLE_CD = "SP^CI^OA^";

            User userEjb = APIAccess.getAPIAccess().getUserAPI();

            UserDataVector usersDV = userEjb.getUsersByName(userLoginName,
                                                            0, //siteId,
                                                            User.NAME_EXACT,//_IGNORE_CASE,
                                                            User.ORDER_BY_NAME);
            // prepare the new user data
            UserData newUserD = UserData.createValue();
            Date now = new Date();
                String contactName = Utility.strNN(nscUsVw.getContactName()).trim();
                String firstName;
                String lastName;

                int index;
                if ((index = contactName.lastIndexOf(' ')) > -1) {
                    firstName = contactName.substring(0, index).trim();
                    lastName = contactName.substring(index + 1).trim();
                } else {
                    firstName = contactName;
                    lastName = "N/A";
                }

                newUserD.setUserId(0);
                newUserD.setAddBy(getModBy());
                newUserD.setAddDate(now);
                newUserD.setLastActivityDate(now);
                newUserD.setModDate(now);
                newUserD.setModBy(getModBy());
                newUserD.setFirstName(firstName);
                newUserD.setLastName(lastName);
                newUserD.setUserName(userLoginName);
                String userPassword = Utility.strNN(nscUsVw.getLocationNumber()).trim();
                String userPasswordCoded = PasswordUtil.getHash(userLoginName, userPassword);
                newUserD.setPassword(userPasswordCoded);
                newUserD.setPrefLocaleCd(RefCodeNames.LOCALE_CD.EN_US);
                newUserD.setUserRoleCd(USER_ROLE_CD);
                newUserD.setUserStatusCd(RefCodeNames.USER_STATUS_CD.ACTIVE);
                newUserD.setUserTypeCd(RefCodeNames.USER_TYPE_CD.MSB);
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

                try {
                    Date runDate = sdf.parse(sdf.format(new Date()));
                    newUserD.setEffDate(runDate);
                } catch(ParseException e) {
                    log.error("Error while parsing the effDate. " + e.toString());
                }

            UserInfoData userInfoD;
            if (usersDV.size() > 0) {   // Update User Information
                userInfoD = userEjb.getUserContact(((UserData)usersDV.get(0)).getUserId());
                UserData userD = userInfoD.getUserData();
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

                // modify address data if it exist
                AddressData addressD = userInfoD.getAddressData();

                if (addressD.getAddressId() > 0) {
                    addressD.setModBy(newUserD.getModBy());
                    addressD.setModDate(newUserD.getModDate());
                    addressD.setName1(newUserD.getFirstName());
                    addressD.setName2(newUserD.getLastName());
                    addressD.setAddressStatusCd(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                }
                userEjb.addUserAssoc(userD.getUserId(), storeId, RefCodeNames.USER_ASSOC_CD.STORE);
                userEjb.addUserAssoc(userD.getUserId(), accountId, RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                UserAssocData uaD = userEjb.addUserAssoc(userD.getUserId(), siteId, RefCodeNames.USER_ASSOC_CD.SITE);
log.info("InboundUserTxt IIIIIIIIIIIIIIIIIIIII add user assoc: "+uaD);
            } else {    // create a new User
                userInfoD = UserInfoData.createValue();
                newUserD.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
                userInfoD.setUserData(newUserD);

                // add User
                userInfoD = userEjb.addUserInfo(userInfoD);
                int userId = userInfoD.getUserData().getUserId();
                // associate User to the Site, Account, Store
                userEjb.addUserAssoc(userId, storeId, RefCodeNames.USER_ASSOC_CD.STORE);
                userEjb.addUserAssoc(userId, accountId, RefCodeNames.USER_ASSOC_CD.ACCOUNT);
                userEjb.addUserAssoc(userId, siteId, RefCodeNames.USER_ASSOC_CD.SITE);
            }
            // update EmailInformation
            EmailData emailD = userInfoD.getEmailData();

            emailD.setModBy(getModBy());
            emailD.setModDate(now);
            emailD.setUserId(userInfoD.getUserData().getUserId());
            String emailAddress = Utility.strNN(nscUsVw.getEmailAddress()).trim();
            if(emailAddress.length()>80) {
                throw new Exception ("Email address is longer than 80 characters. User name: "+userLoginName);
            }
            emailD.setEmailAddress(emailAddress);
            userEjb.updateUserInfo(userInfoD);

        } else {
            throw new Exception("Error user doesn't have no user login name. ");
        }
    }

    protected String getModBy() {
        return USER_LOADER;
    }


}
