package com.cleanwise.service.api.session;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;
import java.rmi.*;

import java.sql.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashSet;
import java.math.BigDecimal;
import org.apache.log4j.Category;


/**
 * Title:        AccountBean
 * Description:  Bean implementation for Account Stateless Session Bean
 * Purpose:      Provides access to the services for managing the account information, properties and relationships.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Tim Besser, CleanWise, Inc.
 */
import javax.ejb.*;

import org.apache.log4j.Logger;


/**
 * <code>Account</code> stateless session bean.
 *
 * @author <a href="mailto:tbesser@cleanwise.com"></a>
 */
public class AccountBean
        extends BusEntityServicesAPI {

           private static final Logger log = Logger.getLogger(AccountBean.class);
	   Category mCat = Category.getInstance(this.getClass().getName());
    /**
     * Creates a new <code>AccountBean</code> instance.
     *
     */
    public AccountBean() {
    }

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate()
    throws CreateException, RemoteException {
    }


    /**
     *Gets a list of Account type BusEntityData objects based off the supplied search criteria object
     *@param pCrit the search criteria
     *@return                      a set of AccountData objects
     *@exception  RemoteException  if an error occurs
     */
    public BusEntityDataVector getAccountBusEntByCriteria(BusEntitySearchCriteria pCrit)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDataVector busEntityVec =
                    BusEntityDAO.getBusEntityByCriteria(conn,pCrit,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            return busEntityVec;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     *Gets a list of AccountData objects based off the supplied search criteria object
     *@param pCrit the search criteria
     *@return                      a set of AccountData objects
     *@exception  RemoteException  if an error occurs
     */
    public AccountDataVector getAccountsByCriteria(BusEntitySearchCriteria pCrit)
    throws RemoteException {
        AccountDataVector acctVec = new AccountDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDataVector busEntityVec =
                    BusEntityDAO.getBusEntityByCriteria(conn,pCrit,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            Iterator iter = busEntityVec.iterator();
	    int thisone = 0, totalct = busEntityVec.size();
            while (iter.hasNext()) {
		logDebug( " acct1 loading " + thisone++ + " of " + totalct );
                acctVec.add(getAccountDetails((BusEntityData) iter.next()));
		logDebug( " acct2 loaded  " + thisone + " of " + totalct );
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return acctVec;
    }
    /**
      *Gets a list of AccountData objects based off the supplied search criteria object
      *@param pCrit the search criteria
      *@return                      a set of AccountData objects
      *@exception  RemoteException  if an error occurs
      */
     public AccountUIViewVector getAccountsUIByCriteria(BusEntitySearchCriteria pCrit)
     throws RemoteException {
         AccountUIViewVector acctVec = new AccountUIViewVector();
         Connection conn = null;
         try {
             conn = getConnection();
             BusEntityDataVector busEntityVec =
                     BusEntityDAO.getBusEntityByCriteria(conn,pCrit,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
              if (busEntityVec != null && busEntityVec.size() >0){

                IdVector ids = new IdVector();
                for (int i = 0; i < busEntityVec.size(); i++) {
                  BusEntityData be = (BusEntityData) busEntityVec.get(i);
                  Integer id = new Integer(be.getBusEntityId());
                  ids.add(id);
                }

               DBCriteria crit = new DBCriteria();
               crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                               RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE);
               crit.addOneOf(PropertyDataAccess.BUS_ENTITY_ID,
                             IdVector.toCommaString(ids));
               crit.addOrderBy(PropertyDataAccess.BUS_ENTITY_ID);
               PropertyDataVector pdV = PropertyDataAccess.select(conn, crit);

               crit = new DBCriteria();
               crit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                               RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
               crit.addOneOf(AddressDataAccess.BUS_ENTITY_ID,
                             IdVector.toCommaString(ids));
               crit.addOrderBy(AddressDataAccess.BUS_ENTITY_ID);
               AddressDataVector adV = AddressDataAccess.select(conn, crit);
               for (int i = 0; i < busEntityVec.size(); i++) {
                 AccountUIView aui = new AccountUIView();
                 BusEntityData be = (BusEntityData) busEntityVec.get(i);
                 PropertyData pd = (i < pdV.size()) ? (PropertyData) pdV.get(i) : null;
                 AddressData ad =  (i < adV.size()) ? (AddressData) adV.get(i) : null;

                 aui.setBusEntity(be);
                 if (pd != null && be.getBusEntityId() == pd.getBusEntityId()) {
                   aui.setAccountType(pd);
                 }
                 else {
                   aui.setAccountType(new PropertyData());
                 }
                 if (ad != null && be.getBusEntityId() == ad.getBusEntityId()) {
                   aui.setPrimaryAddress(ad);
                 }
                 else {
                   aui.setPrimaryAddress(new AddressData());
                 }
                 acctVec.add(aui);
               }
             }
         } catch (Exception e) {
             throw processException(e);
         } finally {
             closeConnection(conn);
         }
         return acctVec;
    }
    /**
     *Gets a list of AccountView objects based off the supplied search criteria object
     *@param pCrit the search criteria
     *@return                      a set of AccountView objects
     *@exception  RemoteException  if an error occurs
     */
    public AccountViewVector getAccountsViewList(BusEntitySearchCriteria pCrit)
    throws RemoteException {
        AccountViewVector acctVwV = new AccountViewVector();
        Connection conn = null;
        try {
            conn = getConnection();
            IdVector storeIdV = pCrit.getStoreBusEntityIds();
            if(storeIdV==null || storeIdV.size()==0) {
                String errorMess = "^clw^Account request doesn't have store id^clw^";
                throw new Exception(errorMess);
            }
            BusEntityDataVector busEntityVec =
                    BusEntityDAO.getBusEntityByCriteria(conn,pCrit,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            IdVector acctIdV =  new IdVector();
            for (Iterator iter = busEntityVec.iterator(); iter.hasNext();) {
                AccountView aVw = AccountView.createValue();
                BusEntityData beD = (BusEntityData) iter.next();
                int acctId = beD.getBusEntityId();
                acctIdV.add(new Integer(acctId));
                aVw.setAcctId(acctId);
                aVw.setAcctName(beD.getShortDesc());
                aVw.setAcctStatusCd(beD.getBusEntityStatusCd());
                acctVwV.add(aVw);

            }
            if(storeIdV.size()==1) {
                Integer storeIdI = (Integer) storeIdV.get(0);
                int storeId = storeIdI.intValue();
                for(Iterator iter = acctVwV.iterator(); iter.hasNext();) {
                    AccountView aVw = (AccountView) iter.next();
                    aVw.setStoreId(storeId);
                }
            } else {
                DBCriteria dbc = new DBCriteria();
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeIdV);
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, acctIdV);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                BusEntityAssocDataVector beaDV =
                        BusEntityAssocDataAccess.select(conn,dbc);
                for(Iterator iter = acctVwV.iterator(); iter.hasNext();) {
                    AccountView aVw = (AccountView) iter.next();
                    int acctId = aVw.getAcctId();
                    for(Iterator iter1 = beaDV.iterator(); iter1.hasNext();) {
                        BusEntityAssocData beaD = (BusEntityAssocData) iter1.next();
                        if(acctId==beaD.getBusEntity1Id()) {
                            aVw.setStoreId(beaD.getBusEntity2Id());
                            break;
                        }
                    }
                }

            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return acctVwV;
    }

    /**
     * Describe <code>getAccountDetails</code> method here.
     *
     * @param pBusEntity a <code>BusEntityData</code> value
     * @return a <code>AccountData</code> value
     * @exception RemoteException if an error occurs
     */
    public AccountData getAccountDetails(BusEntityData pBusEntity) throws RemoteException{
        Connection conn = null;
        try {
            conn = getConnection();
            return getAccountDetails(pBusEntity,conn);
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    AccountData getAccountDetails(BusEntityData pBusEntity, Connection conn)
	throws Exception{

        PropertyDataVector miscProperties = null;
        List runtimeDisplayOrderItemActionTypes = new ArrayList();
        List fieldProperties = null;
        PropertyDataVector fieldPropertiesRuntime = null;
        PropertyData accountType = null;
        PropertyData orderMinimum = null;
        PropertyData creditLimit = null;
        PropertyData creditRating = null;
        PropertyData crcShop = null;
        PropertyData comments = null, ognote = null, skutag = null;
        PropertyData authForResale = null;
        PropertyData resaleErpAccount = null;
        AddressData primaryAddress = null;
        PhoneData primaryPhone = null;
        PhoneData primaryFax = null;
        PhoneData orderPhone = null;
        PhoneData orderFax = null;
        PhoneData poConfirmFax = null;
        EmailData primaryEmail = null;
        EmailData customerServiceEmail = null;
        EmailData defaultEmail = null;
        AddressData billingAddress = null;
        BusEntityAssocData storeAssoc = null;
        ArrayList orderManagerEmail = new ArrayList();
        String ediShipToPrefix = null;
        BigDecimal targetMargin = null;
        String customerSystemApprovalCd = null;
        String trackPunchoutOrderMessages = null;
        boolean makeShipToBillTo = false;
        boolean customerRequestPoAllowed = true; //default to true
        boolean taxableIndicator = false;
        String freightChargeType = null;
        String purchaseOrderAccountName = null;
        String budgetTypeCd = null;
        String glTransformationType = null;
        String distrPOType = null;
        String distributorAccountRefNum = null;
        ProductViewDefDataVector productViewDefinitions = null;

        // get store association
        DBCriteria assocCrit = new DBCriteria();
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                pBusEntity.getBusEntityId());
        assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

        BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(
                conn, assocCrit);

        // there ought to be exactly one - if not, that's a problem
        int storeId=0;
        if (assocVec.size() > 0) {
            storeAssoc = (BusEntityAssocData)assocVec.get(0);
            storeId = storeAssoc.getBusEntity2Id();
        }

        // get account address
        DBCriteria addressCrit = new DBCriteria();
        addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        addressCrit.addEqualTo(AddressDataAccess.PRIMARY_IND, true);

        AddressDataVector addressVec = AddressDataAccess.select(conn,
                addressCrit);

        // if more than one primary address, for now we don't care
        if (addressVec.size() > 0) {
            primaryAddress = (AddressData)addressVec.get(0);
        }

        // get billing address
        addressCrit = new DBCriteria();
        addressCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        addressCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                RefCodeNames.ADDRESS_TYPE_CD.BILLING);
        addressVec = AddressDataAccess.select(conn, addressCrit);

        // if more than one billing address, for now we don't care
        if (addressVec.size() > 0) {
            billingAddress = (AddressData)addressVec.get(0);
        }

        // get related email addresses
        DBCriteria emailCrit = new DBCriteria();
        emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        //emailCrit.addEqualTo(EmailDataAccess.PRIMARY_IND, true);

        EmailDataVector emailVec = EmailDataAccess.select(conn, emailCrit);
        Iterator emailIter = emailVec.iterator();

        // if more than one primary - we don't care
        while (emailIter.hasNext()) {

            EmailData email = (EmailData)emailIter.next();
            if(RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT.equals(email.getEmailTypeCd())) {
                primaryEmail = email;
            }else if(RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE.equals(email.getEmailTypeCd())) {
                customerServiceEmail = email;
            }else if(RefCodeNames.EMAIL_TYPE_CD.DEFAULT.equals(email.getEmailTypeCd())) {
                defaultEmail = email;
            }
             
        }
       // STJ - 3843 
        if(primaryEmail==null) {
        	while(emailIter.hasNext()){
        		EmailData email = (EmailData)emailIter.next();
        		if(email.getPrimaryInd()) {
        			primaryEmail = email;
        		}
        	}
        }

        // get related phones
        DBCriteria phoneCrit = new DBCriteria();
        phoneCrit.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        phoneCrit.addEqualTo(PhoneDataAccess.PRIMARY_IND, true);

        PhoneDataVector phoneVec = PhoneDataAccess.select(conn, phoneCrit);
        Iterator phoneIter = phoneVec.iterator();

        while (phoneIter.hasNext()) {

            PhoneData phone = (PhoneData)phoneIter.next();
            String phoneType = phone.getPhoneTypeCd();

            if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.PHONE) == 0) {
                primaryPhone = phone;
            } else if (phoneType.compareTo(RefCodeNames.PHONE_TYPE_CD.FAX) == 0) {
                primaryFax = phone;
            } else if (phoneType.compareTo(
                    RefCodeNames.PHONE_TYPE_CD.ORDERPHONE) == 0) {
                orderPhone = phone;
            } else if (phoneType.compareTo(
                    RefCodeNames.PHONE_TYPE_CD.ORDERFAX) == 0) {
                orderFax = phone;
            } else if (phoneType.compareTo(
                    RefCodeNames.PHONE_TYPE_CD.POFAX) == 0) {
                poConfirmFax = phone;
            } else {
                log.info("Unknown fax type: "+phoneType+" phone id: "+phone.getPhoneId());
                Thread.currentThread().dumpStack();
                // ignore - unidentified phone
            }
        }

        DBCriteria prodViewCrit = new DBCriteria();
        prodViewCrit.addEqualTo(ProductViewDefDataAccess.ACCOUNT_ID, pBusEntity.getBusEntityId());
        prodViewCrit.addEqualTo(ProductViewDefDataAccess.STATUS_CD, RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
        productViewDefinitions = ProductViewDefDataAccess.select(conn, prodViewCrit);



        // get properties
        DBCriteria propCrit = new DBCriteria();
        propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        propCrit.addIsNull(PropertyDataAccess.USER_ID);

        PropertyDataVector props = PropertyDataAccess.select(conn,
                propCrit);
        miscProperties = new PropertyDataVector();
        fieldProperties = new PropertyDataVector();
        fieldPropertiesRuntime = new PropertyDataVector();


        DBCriteria propCritAcc = new DBCriteria();
        propCritAcc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
        PropertyDataVector propsStore = PropertyDataAccess.select(conn,propCritAcc);
        Iterator storePropIt = propsStore.iterator();
        PropertyDataVector fieldPropertiesUnordered = new PropertyDataVector();
        Iterator propIter = props.iterator();
        int howToCleanTopicId = 0;
	Note noteEjb = getAPIAccess().getNoteAPI();

	PropertyDataVector topicDV =
	    noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);
	for(Iterator iter=topicDV.iterator(); iter.hasNext();) {
	    PropertyData pD = (PropertyData) iter.next();
	    if("How to Clean".equalsIgnoreCase(pD.getValue())) {
		howToCleanTopicId = pD.getPropertyId();
		break;
	    }
	}

        while (propIter.hasNext()) {

            PropertyData prop = (PropertyData)propIter.next();
            String propType = prop.getPropertyTypeCd();

            if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.EXTRA)){
                miscProperties.add(prop);
            } else if (propType.equals(RefCodeNames.PROPERTY_TYPE_CD.ALTERNATE_UI)) {
                miscProperties.add(prop);
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.ALLOWED_RUNTIME_ORD_ITM_ACT) == 0) {
                runtimeDisplayOrderItemActionTypes.add(prop.getValue());
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE) == 0) {
                accountType = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.ORDER_MINIMUM) == 0) {
                orderMinimum = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.CREDIT_LIMIT) == 0) {
                creditLimit = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.CREDIT_RATING) == 0) {
                creditRating = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.CRC_SHOP) == 0) {
                crcShop = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.COMMENTS) == 0) {
                comments = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOTE) == 0) {
                ognote = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.SKU_TAG) == 0) {
                skutag = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.AUTHORIZED_FOR_RESALE) == 0) {
                authForResale = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CUSTOMER_PO_NUMBER) == 0) {
                customerRequestPoAllowed = Utility.isTrue(prop.getValue());
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR) == 0) {
                taxableIndicator = Utility.isTrue(prop.getValue());
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.RESALE_ACCOUNT_ERP_NUM ) == 0) {
                resaleErpAccount = prop;
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.EDI_SHIP_TO_PREFIX ) == 0) {
                ediShipToPrefix = prop.getValue();
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_APPROVAL_CD)){
                customerSystemApprovalCd = prop.getValue();
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.TRACK_PUNCHOUT_ORDER_MESSAGES)){
                trackPunchoutOrderMessages = prop.getValue();
            } else if (propType.compareTo(
                    RefCodeNames.PROPERTY_TYPE_CD.TARGET_MARGIN ) == 0) {
                try{
                    targetMargin = new BigDecimal(prop.getValue());
                }catch (Exception e){}
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD)) {
                fieldPropertiesUnordered.add(prop);
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO)) {
                makeShipToBillTo = Utility.isTrue(prop.getValue());
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.FREIGHT_CHARGE_TYPE)) {
                freightChargeType = prop.getValue();
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.PURCHASE_ORDER_ACCOUNT_NAME)) {
                purchaseOrderAccountName = prop.getValue();
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD)) {
                budgetTypeCd = prop.getValue();
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.GL_TRANSFORMATION_TYPE)) {
                glTransformationType = prop.getValue();
            } else if (propType.equals(
                    RefCodeNames.PROPERTY_TYPE_CD.DISTR_PO_TYPE)) {
                distrPOType = prop.getValue();
            } else if (propType.equals(
            		RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM)){
            	distributorAccountRefNum = prop.getValue();
            }



            // some property we know nothing about
        }



        try{
	        BusEntityFieldsData bfd =
	                getAPIAccess().getPropertyServiceAPI().fetchAccountFieldsData(
	                storeAssoc.getBusEntity2Id());

	        fieldProperties = BusEntityFieldDataElement.createBusEntityFieldDataElementCol(
	                bfd, fieldPropertiesUnordered, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD,
	                pBusEntity.getBusEntityId());
        }catch(DataNotFoundException e){
        	log.info("No Site Field Data");
        }

        // get order manager email addresses
        emailCrit = new DBCriteria();
        emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        emailCrit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,
                RefCodeNames.EMAIL_TYPE_CD.ORDER_MANAGER);

        EmailDataVector orderManagerEmailV = EmailDataAccess.select(conn, emailCrit);
        for (int ii=0; ii<orderManagerEmailV.size(); ii++) {
            EmailData eD = (EmailData)orderManagerEmailV.get(ii);
            orderManagerEmail.add(eD.getEmailAddress());
        }

        CatalogData catd = getAccountCatalog(conn, pBusEntity.getBusEntityId());

        // get workflow email address
        /*
        emailCrit = new DBCriteria();
        emailCrit.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        emailCrit.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD, RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE);

        EmailDataVector wEmailVec = EmailDataAccess.select(conn, emailCrit);
        Iterator wEmailIter = wEmailVec.iterator();

        // if more than one workflow email - we don't care
        if (wEmailIter.hasNext()) {
            EmailData email = (EmailData)wEmailIter.next();
            workflowEmail = email;
        }
        */
        AccountData ad = new AccountData(pBusEntity,
                storeAssoc,
                primaryAddress,
                primaryPhone, primaryFax, orderPhone, orderFax,
                primaryEmail, billingAddress, accountType,
                orderMinimum, creditLimit, creditRating,
                crcShop, comments, miscProperties,catd ,orderManagerEmail,
                authForResale,resaleErpAccount,
                ediShipToPrefix,targetMargin,fieldProperties,
                fieldPropertiesRuntime,customerSystemApprovalCd,trackPunchoutOrderMessages,makeShipToBillTo,
                freightChargeType,
                runtimeDisplayOrderItemActionTypes,
                purchaseOrderAccountName,budgetTypeCd,customerRequestPoAllowed, poConfirmFax,taxableIndicator,
                distrPOType, customerServiceEmail,defaultEmail,glTransformationType,distributorAccountRefNum, productViewDefinitions);

        // Account level order guide note.
        if ( null != ognote ) ad.setOrderGuideNote(ognote);
        if ( null != skutag ) ad.setSkuTag(skutag);
        ad.setInventoryItemsData(getInventoryItems(pBusEntity.getBusEntityId()));

        //Get parameters
        DBCriteria paramDbc = new DBCriteria();
        paramDbc.addEqualTo(BusEntityParameterDataAccess.BUS_ENTITY_ID,
                pBusEntity.getBusEntityId());
        BusEntityParameterDataVector bepDV =
                BusEntityParameterDataAccess.select(conn,paramDbc);
        ad.setAccountParameters(bepDV);
	ad.setHowToCleanTopicId(howToCleanTopicId);
	logDebug("howToCleanTopicId="+howToCleanTopicId);
	if ( howToCleanTopicId > 0 ) {
	    try{
		int accountId = pBusEntity.getBusEntityId();
		NoteViewVector noteVwV =
		    noteEjb.getNoteTitles(howToCleanTopicId, accountId, new ArrayList(), false);
		ad.setNoteList(noteVwV);

		for ( int i = 0; null != noteVwV && i < noteVwV.size() ; i++ ) {
		    NoteView nv = (NoteView)noteVwV.get(i);
		    NoteJoinView njVw = noteEjb.getNote(nv.getNoteId());
		    ad.addNote(njVw);
		}
	    }catch(Exception e){
		log.info("No Account Notes");
	    }
        }


        // Get checkout options
        DBCriteria sod_dbc = new DBCriteria();
        sod_dbc.addEqualTo(ShoppingOptionsDataAccess.ACCOUNT_ID,
                pBusEntity.getBusEntityId());
        ShoppingOptionsDataVector sodv =
                ShoppingOptionsDataAccess.select(conn, sod_dbc);
        ad.setCheckoutOptions(sodv);

        // Get checkout options config
        sod_dbc = new DBCriteria();
        sod_dbc.addEqualTo(ShoppingOptConfigDataAccess.ACCOUNT_ID,
                pBusEntity.getBusEntityId());
        ShoppingOptConfigDataVector socdv =
                ShoppingOptConfigDataAccess.select(conn, sod_dbc);
        if ( null != socdv && socdv.size() > 0 ) {
            ad.setCheckoutOptConfig((ShoppingOptConfigData)socdv.get(0));
        } else {
            ad.setCheckoutOptConfig(null);
        }

        // set up any account related shopping restrictions.

	if ( null != catd ) {
	    ProductDAO pdao = mCacheManager.getProductDAO();
	    IdVector acctItems = pdao.allCatalogItemIds
		(conn,catd.getCatalogId());
	    // Construct a shopping control object for each item in this
	    // account.
	    ShoppingControlDataVector scdv =
                mCacheManager.getBusEntityDAO().getAccountShoppingControls
                (conn, pBusEntity.getBusEntityId(), acctItems);

	    ad.setShoppingControls(scdv);
	    acctItems = null;
	    pdao = null;
        }

	ad.setFiscalCalenders
	    (mCacheManager.getBusEntityDAO().getFiscalCalenders
	     (conn,pBusEntity.getBusEntityId()));

	ad.setAccountBillTos
	    (getAccountBillTos(conn,pBusEntity.getBusEntityId()));

        return ad;
    }

    private CacheManager mCacheManager = new CacheManager("AccountBean ");

    /**
     *Gets the field properties for this account.
     */
    private List getBusEntityFieldDataElementsForAccount(int pAccountId, int pStoreId)
    throws RemoteException {
        Connection conn = null;
        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,pAccountId);

            PropertyDataVector pv = PropertyDataAccess.select(conn,crit);
            BusEntityFieldsData bfd =
                    getAPIAccess().getPropertyServiceAPI().fetchAccountFieldsData(pStoreId);

            List fieldProperties = BusEntityFieldDataElement.createBusEntityFieldDataElementCol(
                    bfd, pv, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD,pAccountId);
            return fieldProperties;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     * Gets the account information values to be used by the request.
     * @param pAccountId an <code>int</code> value
     * @param pStoreId the id of the account store.  If nonzero will only
     *        return an Account that belongs to that store.  i.e. if the
     *        Account belongs to a different store, it won't be returned
     * @return a <code>AccountData</code> value
     * @exception RemoteException if an error occurs
     * @exception DataNotFoundException if an error occurs
     */
    public AccountData getAccount(int pAccountId, int pStoreId)
    throws RemoteException, DataNotFoundException {

        AccountData account = null;
        Connection conn = null;

        try {
            conn = getConnection();

            BusEntityData busEntity = null;
            busEntity = BusEntityDataAccess.select(conn, pAccountId);

            if (busEntity.getBusEntityTypeCd().compareTo(
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT) != 0) {
                throw new DataNotFoundException("Bus Entity not account");
            }

            if (pStoreId != 0) {

                // need to check that this account is associated to store
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                        pAccountId);
                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,
                        pStoreId);
                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

                BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(
                        conn, crit);

                if (assocVec.size() == 0) {
                    throw new DataNotFoundException("BUS_ENTITY_ID :" +
                            pAccountId);
                }
            }

            account = getAccountDetails(busEntity);
        } catch (DataNotFoundException e)        {
            throw e;
        } catch (Exception e)        {
            throw new RemoteException("getAccount: " + e.getMessage());
        } finally        {
            closeConnection(conn);
        }

        return account;
    }


    /**
     * Gets the lightweight account information values to be used by the request.
     * @param pAccountId the account identifier.
     * @return AccountData
     * @throws RemoteException Required by EJB 1.0
     *         DataNotFoundException if account with pAccountId doesn't exist
     */
    public BusEntityData getAccountBusEntity(int pAccountId)
    throws RemoteException, DataNotFoundException{
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityData account = BusEntityDataAccess.select(conn,pAccountId);
            if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(account.getBusEntityTypeCd())){
                return account;
            }else{
                throw new DataNotFoundException("no account for bus entity id: "+pAccountId);
            }
        }catch (DataNotFoundException e){
            throw e;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     * Returns Account Id for company code (assume unique)
     */

    public int getAccountIdForCompany(String companyNo) throws RemoteException,DataNotFoundException {
    	Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
            		RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
            crit.addEqualTo(PropertyDataAccess.CLW_VALUE, companyNo);
            crit.addEqualToIgnoreCase(PropertyDataAccess.SHORT_DESC, "Company Number:");

            PropertyDataVector pDV = PropertyDataAccess.select(conn, crit);
            if(pDV!=null){
            	if(pDV.size()==0 || pDV.size()>1){
            		throw new Exception("Error finding company code :"+companyNo);
            	}else{
            		PropertyData pD = (PropertyData)pDV.get(0);
            		return pD.getBusEntityId();
            	}

            }

        }catch (DataNotFoundException e){
            throw e;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
        return 0;
    }

    /**
     *Returns the Account Id for the account associated to this site
     *@param pSiteId
     */
    public int getAccountIdForSite(int pSiteId)
    throws RemoteException,DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            return getAccountIdForSiteLocal(conn,pSiteId);
        }catch (DataNotFoundException e){
            throw e;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     *Returns the Account Data object for the account associated to this site
     *@param pSiteId
     */
    public AccountData getAccountForSite(int pSiteId)
    throws RemoteException,DataNotFoundException {
        Connection conn = null;
        try {
            conn = getConnection();
            int accountId = getAccountIdForSiteLocal(conn,pSiteId);
            return populateAccountFromId(conn,accountId,pSiteId);
        }catch (DataNotFoundException e){
            throw e;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    protected int getAccountIdForSiteLocal(Connection conn, int pSiteId)
    throws RemoteException,DataNotFoundException {
        try{

            // get account association
            DBCriteria assocCrit = new DBCriteria();
            assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                    pSiteId);
            assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

            BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(
                    conn, assocCrit);

            // there ought to be exactly one - if not, that's a problem
            BusEntityAssocData accountAssoc = null;

            if (assocVec.size() > 0) {
                accountAssoc = (BusEntityAssocData)assocVec.get(0);
                // Get the account information.
                return accountAssoc.getBusEntity2Id();
            }else
            	return 0;            
            
        }catch (Exception e){
            throw processException(e);
        }
    }

    private AccountData populateAccountFromId(Connection conn, int accountId, int pSiteId) throws Exception{
        BusEntityData busEntity = BusEntityDataAccess.select(conn,accountId);
        AccountData account = getAccountDetails(busEntity);

        // Overwrite account address if account uses ship to as bill to
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,accountId);
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO);
        dbc.addEqualTo(PropertyDataAccess.CLW_VALUE,"true");
        PropertyDataVector pDV = PropertyDataAccess.select(conn,dbc);
        if(pDV.size()>0) {
            dbc = new DBCriteria();
            dbc.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,pSiteId);
            dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                    RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
            dbc.addEqualTo(AddressDataAccess.ADDRESS_STATUS_CD,
                    RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
            AddressDataVector aDV = AddressDataAccess.select(conn,dbc);
            if(aDV.size()>0) {
                AddressData aD = (AddressData) aDV.get(0);
                account.setBillingAddress(aD);
            }
        }
        //Get parameters
        DBCriteria paramDbc = new DBCriteria();
        paramDbc.addEqualTo(BusEntityParameterDataAccess.BUS_ENTITY_ID,
                accountId);
        BusEntityParameterDataVector bepDV =
                BusEntityParameterDataAccess.select(conn,paramDbc);
        account.setAccountParameters(bepDV);

        return account;
    }

    /**
     * Get all accounts that match the given name.  The arguments specify
     * whether the name is interpreted as a pattern or exact match.
     *
     * @param pName a <code>String</code> value with account name or pattern
     * @param pStoreId the id of the account store.  If nonzero will only
     *        return a Accounts that belongs to that store.  Otherwise will
     *        return all matching Accounts.
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
     *        BEGINS_WITH_IGNORE_CASE
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>AccountDataVector</code> of matching accounts
     * @exception RemoteException if an error occurs
     */
    public AccountDataVector getAccountByName(String pName, int pStoreId,
            int pMatch, int pOrder)
            throws RemoteException {

        AccountDataVector accountVec = new AccountDataVector();
        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            switch (pMatch) {

                case Account.EXACT_MATCH:
                    crit.addEqualTo(BusEntityDataAccess.SHORT_DESC, pName);

                    break;

                case Account.EXACT_MATCH_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                            pName);

                    break;

                case Account.BEGINS_WITH:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC, pName +
                            "%");

                    break;

                case Account.BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                            pName + "%");

                    break;

                case Account.CONTAINS:
                    crit.addLike(BusEntityDataAccess.SHORT_DESC,
                            "%" + pName + "%");

                    break;

                case Account.CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC,
                            "%" + pName + "%");

                    break;

                default:
                    throw new RemoteException("getAccountByName: Bad match specification");
            }

            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

            if (pStoreId != 0) {

                StringBuffer buf = new StringBuffer();
                buf.append(BusEntityDataAccess.BUS_ENTITY_ID);
                buf.append(" IN (SELECT ");
                buf.append(BusEntityAssocDataAccess.BUS_ENTITY1_ID);
                buf.append(" FROM ");
                buf.append(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC);
                buf.append(" WHERE ");
                buf.append(BusEntityAssocDataAccess.BUS_ENTITY2_ID);
                buf.append(" = ");
                buf.append(pStoreId);
                buf.append(" AND ");
                buf.append(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD);
                buf.append(" = '");
                buf.append(RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                buf.append("')");
                crit.addCondition(buf.toString());
            }

            switch (pOrder) {

                case Account.ORDER_BY_ID:
                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);

                    break;

                case Account.ORDER_BY_NAME:
                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);

                    break;

                default:
                    throw new RemoteException("getAccountByName: Bad order specification");
            }

            BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn,
                    crit);

            Iterator iter = busEntityVec.iterator();

            while (iter.hasNext()) {
                accountVec.add(getAccountDetails((BusEntityData)iter.next()));
            }
        } catch (Exception e) {
            throw new RemoteException("getAccountByName: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return accountVec;
    }

    /**
     * Get all the accounts or all accounts for a given store.
     * @param pStoreId the Id of the accounts store.  If zero, all accounts
     *        without regard to the store, will be returned
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>AccountDataVector</code> with all accounts.
     * @exception RemoteException if an error occurs
     */
    public AccountDataVector getAllAccounts(int pStoreId, int pOrder)
    throws RemoteException {

        AccountDataVector accountVec = new AccountDataVector();
        Connection conn = null;

        try {
            conn = getConnection();

            DBCriteria crit = new DBCriteria();

            if (pStoreId == 0) {
                crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            } else {
                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,
                        pStoreId);
                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

                IdVector ids = BusEntityAssocDataAccess.selectIdOnly(conn,
                        BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                        crit);

                if (ids.size() == 0) {

                    // no accounts for this store
                    return accountVec;
                }
                crit = new DBCriteria();
                crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, ids);
            }

            switch (pOrder) {

                case Account.ORDER_BY_ID:
                    crit.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID, true);

                    break;

                case Account.ORDER_BY_NAME:
                    crit.addOrderBy(BusEntityDataAccess.SHORT_DESC, true);

                    break;

                default:
                    throw new RemoteException("getAllAccounts: Bad order specification");
            }

            BusEntityDataVector busEntityVec = BusEntityDataAccess.select(conn,
                    crit);
            Iterator iter = busEntityVec.iterator();

            while (iter.hasNext()) {
                accountVec.add(getAccountDetails((BusEntityData)iter.next()));
            }
        } catch (Exception e) {
          e.printStackTrace();
            throw new RemoteException("getAllAccounts: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return accountVec;
    }

    /**
     *Updates a set of properties and maintains uniquness based off the shortDescription field, the passed
     *in propertyTypeCd represents the namespace to be used for uniquness
     */
    private void updatePropsUniqueShortDesc(PropertyDataVector pDataVector,Connection conn,
            int pBusEntityId, String propertyTypeCd, boolean deleteExtra)
            throws SQLException{
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,propertyTypeCd);
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,pBusEntityId);
        PropertyDataVector pDV = PropertyDataAccess.select(conn,dbc);

        Iterator propIter = pDataVector.iterator();
        while (propIter.hasNext()) {
            PropertyData prop = (PropertyData)propIter.next();
            String shortDesc = prop.getShortDesc();
            if(!Utility.isSet(shortDesc)) continue;
            boolean newFl = true;
            for(int ii=0; ii<pDV.size(); ii++) {
                PropertyData pD = (PropertyData) pDV.get(ii);
                if(shortDesc.equals(pD.getShortDesc())){
                    if(prop.isDirty()){
                        pD.setValue(prop.getValue());
                        pD.setModBy(prop.getModBy());
                        PropertyDataAccess.update(conn, pD);
                    }
                    pDV.remove(ii);
                    newFl =false;
                    break;
                }
            }
            if(newFl) {
                prop.setPropertyTypeCd(propertyTypeCd);
                if (prop.getPropertyStatusCd().compareTo("") == 0) {
                    prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                }
                prop.setBusEntityId(pBusEntityId);
                PropertyDataAccess.insert(conn, prop);
            }
        }

        if(deleteExtra){
            IdVector toDelete = new IdVector();
            for(int ii=0; ii<pDV.size(); ii++) {
                PropertyData pD = (PropertyData) pDV.get(ii);
                toDelete.add(new Integer(pD.getPropertyId()));
            }
            if(toDelete.size()>0) {
                DBCriteria dbc1 = new DBCriteria();
                dbc1.addOneOf(PropertyDataAccess.PROPERTY_ID,toDelete);
                PropertyDataAccess.remove(conn,dbc1);
            }
        }
    }

    /**
     * Describe <code>addAccount</code> method here.
     *
     * @param pAccountData a <code>AccountData</code> value
     * @param pStoreId the id of the account store.
     * @return an <code>AccountData</code> value
     * @exception RemoteException if an error occurs
     */
    public AccountData addAccount(AccountData pAccountData, int pStoreId)
    throws RemoteException {

        BusEntityAssocData storeAssoc = pAccountData.getStoreAssoc();
        storeAssoc.setBusEntity2Id(pStoreId);

        return updateAccount(pAccountData);
    }

    /**
     * Updates the account information values to be used by the request.
     * @param pAccountData  the AccountData account data.
     * @return an <code>AccountData</code> value
     * @throws RemoteException Required by EJB 1.0
     */
    public AccountData updateAccount(AccountData pAccountData)
    throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            BusEntityData busEntity = pAccountData.getBusEntity();

            if (busEntity.isDirty()) {

                if (busEntity.getBusEntityId() == 0) {
                    busEntity.setBusEntityTypeCd(
                            RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                    BusEntityData be = BusEntityDataAccess.insert( conn, busEntity );
                    if(!Utility.getClwSwitch()) {
                        be.setErpNum(""+be.getBusEntityId());
                        BusEntityDataAccess.update( conn, busEntity );
                    }
                } else {
                    BusEntityDataAccess.update(conn, busEntity);
                }
            }

            int accountId = pAccountData.getBusEntity().getBusEntityId();
            BusEntityAssocData storeAssoc = pAccountData.getStoreAssoc();

            if (storeAssoc.isDirty()) {

                if (storeAssoc.getBusEntityAssocId() == 0) {
                    storeAssoc.setBusEntity1Id(accountId);
                    storeAssoc.setBusEntityAssocCd(
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                    BusEntityAssocDataAccess.insert(conn, storeAssoc);
                } else {
                    BusEntityAssocDataAccess.update(conn, storeAssoc);
                }
            }

            EmailData primaryEmail = pAccountData.getPrimaryEmail();

            if (primaryEmail.isDirty()) {

                if (primaryEmail.getEmailId() == 0) {
                    primaryEmail.setBusEntityId(accountId);
                    primaryEmail.setEmailTypeCd(
                            RefCodeNames.EMAIL_TYPE_CD.PRIMARY_CONTACT);
                    primaryEmail.setEmailStatusCd(
                            RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
                    primaryEmail.setPrimaryInd(true);
                    EmailDataAccess.insert(conn, primaryEmail);
                } else {
                    EmailDataAccess.update(conn, primaryEmail);
                }
            }

            EmailData customerServiceEmail = pAccountData.getCustomerServiceEmail();

            if (customerServiceEmail.isDirty()) {

                if (customerServiceEmail.getEmailId() == 0) {
                    customerServiceEmail.setBusEntityId(accountId);
                    customerServiceEmail.setEmailTypeCd(
                            RefCodeNames.EMAIL_TYPE_CD.CUSTOMER_SERVICE);
                    customerServiceEmail.setEmailStatusCd(
                            RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
                    customerServiceEmail.setPrimaryInd(false);
                    EmailDataAccess.insert(conn, customerServiceEmail);
                } else {
                    EmailDataAccess.update(conn, customerServiceEmail);
                }
            }

            EmailData defaultEmail = pAccountData.getDefaultEmail();
            if (defaultEmail.isDirty()) {
                if (defaultEmail.getEmailId() == 0) {
                    defaultEmail.setBusEntityId(accountId);
                    defaultEmail.setEmailTypeCd(
                            RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
                    defaultEmail.setEmailStatusCd(
                            RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
                    defaultEmail.setPrimaryInd(false);
                    EmailDataAccess.insert(conn, defaultEmail);
                } else {
                    EmailDataAccess.update(conn, defaultEmail);
                }
            }


            PhoneData primaryPhone = pAccountData.getPrimaryPhone();

            if (primaryPhone.isDirty()) {

                if (primaryPhone.getPhoneId() == 0) {
                    primaryPhone.setBusEntityId(accountId);
                    primaryPhone.setPhoneStatusCd(
                            RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    primaryPhone.setPhoneTypeCd(
                            RefCodeNames.PHONE_TYPE_CD.PHONE);
                    primaryPhone.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, primaryPhone);
                } else {
                    PhoneDataAccess.update(conn, primaryPhone);
                }
            }

            PhoneData orderPhone = pAccountData.getOrderPhone();

            if (orderPhone.isDirty()) {

                if (orderPhone.getPhoneId() == 0) {
                    orderPhone.setBusEntityId(accountId);
                    orderPhone.setPhoneStatusCd(
                            RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    orderPhone.setPhoneTypeCd(
                            RefCodeNames.PHONE_TYPE_CD.ORDERPHONE);
                    orderPhone.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, orderPhone);
                } else {
                    PhoneDataAccess.update(conn, orderPhone);
                }
            }

            PhoneData primaryFax = pAccountData.getPrimaryFax();

            if (primaryFax.isDirty()) {

                if (primaryFax.getPhoneId() == 0) {
                    primaryFax.setBusEntityId(accountId);
                    primaryFax.setPhoneStatusCd(
                            RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    primaryFax.setPhoneTypeCd(RefCodeNames.PHONE_TYPE_CD.FAX);
                    primaryFax.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, primaryFax);
                } else {
                    PhoneDataAccess.update(conn, primaryFax);
                }
            }

            PhoneData orderFax = pAccountData.getOrderFax();

            if (orderFax.isDirty()) {

                if (orderFax.getPhoneId() == 0) {
                    orderFax.setBusEntityId(accountId);
                    orderFax.setPhoneStatusCd(
                            RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    orderFax.setPhoneTypeCd(
                            RefCodeNames.PHONE_TYPE_CD.ORDERFAX);
                    orderFax.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, orderFax);
                } else {
                    PhoneDataAccess.update(conn, orderFax);
                }
            }

            PhoneData poConfirmFax = pAccountData.getFaxBackConfirm();

            if (poConfirmFax.isDirty()) {

                if (poConfirmFax.getPhoneId() == 0) {
                    poConfirmFax.setBusEntityId(accountId);
                    poConfirmFax.setPhoneStatusCd(
                            RefCodeNames.PHONE_STATUS_CD.ACTIVE);
                    poConfirmFax.setPhoneTypeCd(
                            RefCodeNames.PHONE_TYPE_CD.POFAX);
                    poConfirmFax.setPrimaryInd(true);
                    PhoneDataAccess.insert(conn, poConfirmFax);
                } else {
                    PhoneDataAccess.update(conn, poConfirmFax);
                }
            }

            AddressData primaryAddress = pAccountData.getPrimaryAddress();

            if (primaryAddress.isDirty()) {

                if (primaryAddress.getAddressId() == 0) {
                    primaryAddress.setBusEntityId(accountId);
                    primaryAddress.setAddressStatusCd(
                            RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                    primaryAddress.setAddressTypeCd(
                            RefCodeNames.ADDRESS_TYPE_CD.PRIMARY_CONTACT);
                    primaryAddress.setPrimaryInd(true);
                    AddressDataAccess.insert(conn, primaryAddress);
                } else {
                    AddressDataAccess.update(conn, primaryAddress);
                }
            }

            AddressData billingAddress = pAccountData.getBillingAddress();

            if (billingAddress.isDirty()) {

                if (billingAddress.getAddressId() == 0) {
                    billingAddress.setBusEntityId(accountId);
                    billingAddress.setAddressStatusCd(
                            RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
                    billingAddress.setAddressTypeCd(
                            RefCodeNames.ADDRESS_TYPE_CD.BILLING);
                    AddressDataAccess.insert(conn, billingAddress);
                } else {
                    AddressDataAccess.update(conn, billingAddress);
                }
            }

            PropertyData accountType = pAccountData.getAccountType();

            if (accountType.isDirty()) {
                accountType.setShortDesc("ACCOUNT TYPE");
                accountType.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                accountType.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE);
                accountType.setBusEntityId(accountId);

                if (accountType.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, accountType);
                } else {
                    PropertyDataAccess.update(conn, accountType);
                }
            }

            PropertyData orderMinimum = pAccountData.getOrderMinimum();

            if (orderMinimum.isDirty()) {
                orderMinimum.setShortDesc("ORDER MINIMUM");
                orderMinimum.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                orderMinimum.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.ORDER_MINIMUM);
                orderMinimum.setBusEntityId(accountId);

                if (orderMinimum.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, orderMinimum);
                } else {
                    PropertyDataAccess.update(conn, orderMinimum);
                }
            }

            PropertyData creditLimit = pAccountData.getCreditLimit();

            if (creditLimit.isDirty()) {
                creditLimit.setShortDesc("CREDIT LIMIT");
                creditLimit.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                creditLimit.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.CREDIT_LIMIT);
                creditLimit.setBusEntityId(accountId);

                if (creditLimit.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, creditLimit);
                } else {
                    PropertyDataAccess.update(conn, creditLimit);
                }
            }

            PropertyData creditRating = pAccountData.getCreditRating();

            if (creditRating.isDirty()) {
                creditRating.setShortDesc("CREDIT RATING");
                creditRating.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                creditRating.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.CREDIT_RATING);
                creditRating.setBusEntityId(accountId);

                if (creditRating.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, creditRating);
                } else {
                    PropertyDataAccess.update(conn, creditRating);
                }
            }

            PropertyData crcShop = pAccountData.getCrcShop();

            if (crcShop.isDirty()) {
                crcShop.setShortDesc("CRC SHOP");
                crcShop.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                crcShop.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.CRC_SHOP);
                crcShop.setBusEntityId(accountId);

                if (crcShop.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, crcShop);
                } else {
                    PropertyDataAccess.update(conn, crcShop);
                }
            }

            PropertyData comments = pAccountData.getComments();

            if (comments.isDirty()) {
                comments.setShortDesc("COMMENTS");
                comments.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                comments.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.COMMENTS);
                comments.setBusEntityId(accountId);

                if (comments.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, comments);
                } else {
                    PropertyDataAccess.update(conn, comments);
                }
            }

            PropertyData ognote = pAccountData.getOrderGuideNote();

            if (ognote.isDirty()) {
                ognote.setShortDesc
                        (RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOTE);
                ognote.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                ognote.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.ORDER_GUIDE_NOTE);
                ognote.setBusEntityId(accountId);

                if (ognote.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, ognote);
                } else {
                    PropertyDataAccess.update(conn, ognote);
                }
            }

            PropertyData skutag = pAccountData.getSkuTag();

            if (skutag.isDirty()) {
                skutag.setShortDesc
                        (RefCodeNames.PROPERTY_TYPE_CD.SKU_TAG);
                skutag.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                skutag.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.SKU_TAG);
                skutag.setBusEntityId(accountId);

                if (skutag.getPropertyId() == 0) {
                    PropertyDataAccess.insert(conn, skutag);
                } else {
                    PropertyDataAccess.update(conn, skutag);
                }
            }

            PropertyData resaleAccountNum = pAccountData.getResaleAccountErpNumber();
            if (resaleAccountNum.isDirty()){
                resaleAccountNum.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.RESALE_ACCOUNT_ERP_NUM);
                resaleAccountNum.setPropertyStatusCd(
                        RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                resaleAccountNum.setPropertyTypeCd(
                        RefCodeNames.PROPERTY_TYPE_CD.RESALE_ACCOUNT_ERP_NUM);
                resaleAccountNum.setBusEntityId(accountId);
                if (resaleAccountNum.getPropertyId() == 0){
                    PropertyDataAccess.insert(conn, resaleAccountNum);
                }else{
                    PropertyDataAccess.update(conn, resaleAccountNum);
                }
            }

            String erpShipToPrefix = pAccountData.getEdiShipToPrefix();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.EDI_SHIP_TO_PREFIX);
            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,accountId);
            boolean erpShipToPrefixFl = false;
            if(erpShipToPrefix!=null && erpShipToPrefix.trim().length()>0) {
                try {
                    int erpShipToPrefixInt = Integer.parseInt(erpShipToPrefix);
                    if(erpShipToPrefixInt!=accountId) {
                        erpShipToPrefixFl = true;
                    }
                }catch(Exception exc){
                    erpShipToPrefixFl=true; //Non numeric value is legal
                }
            }
            if(!erpShipToPrefixFl) {
                PropertyDataAccess.remove(conn,dbc);
            } else {
                PropertyDataVector erpShipToPrefixV = PropertyDataAccess.select(conn,dbc);
                int ii=0;
                for(; ii<erpShipToPrefixV.size(); ii++) {
                    PropertyData  prop = (PropertyData) erpShipToPrefixV.get(ii);
                    if(ii==0) {
                        prop.setValue(erpShipToPrefix);
                        prop.setModBy(pAccountData.getBusEntity().getModBy());
                        PropertyDataAccess.update(conn, prop);
                    } else {
                        PropertyDataAccess.remove(conn,prop.getPropertyId());
                    }
                }
                if(ii==0) {
                    PropertyData  prop = PropertyData.createValue();
                    prop.setBusEntityId(pAccountData.getBusEntity().getBusEntityId());
                    prop.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.EDI_SHIP_TO_PREFIX);
                    prop.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.EDI_SHIP_TO_PREFIX);
                    prop.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                    prop.setValue(erpShipToPrefix);
                    prop.setAddBy(pAccountData.getBusEntity().getModBy());
                    prop.setModBy(pAccountData.getBusEntity().getModBy());
                    PropertyDataAccess.insert(conn, prop);
                }
            }

            PropertyUtil pUtil = new PropertyUtil(conn);
            String customerSystemApprovalCd = pAccountData.getCustomerSystemApprovalCd();
            if(!Utility.isSet(customerSystemApprovalCd)){
                customerSystemApprovalCd = RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.NONE;
            }
            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_APPROVAL_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_APPROVAL_CD, customerSystemApprovalCd);


            Boolean customerRequestPoAllowed = new Boolean(pAccountData.isCustomerRequestPoAllowed());
            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CUSTOMER_PO_NUMBER,
                    RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CUSTOMER_PO_NUMBER, customerRequestPoAllowed.toString());

            Boolean taxableIndicator = new Boolean(pAccountData.isTaxableIndicator());
            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR,
                    RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR, taxableIndicator.toString());


            Boolean authForReSale = new Boolean(pAccountData.isAuthorizedForResale());
            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.AUTHORIZED_FOR_RESALE,
                    RefCodeNames.PROPERTY_TYPE_CD.AUTHORIZED_FOR_RESALE, authForReSale.toString());

            String targetMargStr = null;
            if(pAccountData.getTargetMargin() != null){
                targetMargStr = pAccountData.getTargetMargin().toString();
            }
            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.TARGET_MARGIN,
                    RefCodeNames.PROPERTY_TYPE_CD.TARGET_MARGIN, targetMargStr);

            boolean makeShipToBillTo = pAccountData.isMakeShipToBillTo();
            if(makeShipToBillTo){
                pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO,
                        RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO, "true");
            }else{
                pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO,
                        RefCodeNames.PROPERTY_TYPE_CD.MAKE_SHIP_TO_BILL_TO, "false");
            }

            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.FREIGHT_CHARGE_TYPE,
                    RefCodeNames.PROPERTY_TYPE_CD.FREIGHT_CHARGE_TYPE, pAccountData.getFreightChargeType());

            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.PURCHASE_ORDER_ACCOUNT_NAME,
                    RefCodeNames.PROPERTY_TYPE_CD.PURCHASE_ORDER_ACCOUNT_NAME, pAccountData.getPurchaseOrderAccountName());

            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD, pAccountData.getBudgetTypeCd());

            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.GL_TRANSFORMATION_TYPE,
                    RefCodeNames.PROPERTY_TYPE_CD.GL_TRANSFORMATION_TYPE, pAccountData.getGlTransformationType());

            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM,
                    RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM, pAccountData.getDistributorAccountRefNum());


            pUtil.saveValue(0, accountId, RefCodeNames.PROPERTY_TYPE_CD.DISTR_PO_TYPE,
                    RefCodeNames.PROPERTY_TYPE_CD.DISTR_PO_TYPE, pAccountData.getDistrPOType());

            PropertyDataVector miscProperties = pAccountData.getMiscProperties();
            //Iterator propIter = miscProperties.iterator();
            updatePropsUniqueShortDesc(miscProperties,conn,accountId,RefCodeNames.PROPERTY_TYPE_CD.EXTRA,true);
            if(pAccountData.getDataFieldProperties() != null){
                PropertyDataVector fieldProperties = BusEntityFieldDataElement.toPropertyDataVector(pAccountData.getDataFieldProperties());
                updatePropsUniqueShortDesc(fieldProperties,conn,accountId,RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD,false);
            }else{
                pAccountData.setDataFieldProperties(getBusEntityFieldDataElementsForAccount(accountId, storeAssoc.getBusEntity2Id()));
            }

            {
                //make it a linked list as we are going to be removing items from it
                LinkedList newAllowed = new LinkedList(pAccountData.getRuntimeDisplayOrderItemActionTypes());
                dbc = new DBCriteria();
                dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,RefCodeNames.PROPERTY_TYPE_CD.ALLOWED_RUNTIME_ORD_ITM_ACT);
                dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID,accountId);
                PropertyDataVector existingAllowed = PropertyDataAccess.select(conn,dbc);
                Iterator existingAllowedIt = existingAllowed.iterator();
                while(existingAllowedIt.hasNext()){
                    PropertyData aProp = (PropertyData) existingAllowedIt.next();
                    if(newAllowed.contains(aProp.getValue())){
                        newAllowed.remove(aProp.getValue());
                    }else{
                        PropertyDataAccess.remove(conn,aProp.getPropertyId());
                    }
                }
                Iterator newAllowedIt = newAllowed.iterator();
                while(newAllowedIt.hasNext()){
                    String newAllowedStr = (String) newAllowedIt.next();
                    PropertyData p = PropertyData.createValue();
                    p.setAddBy(pAccountData.getBusEntity().getModBy());
                    p.setModBy(pAccountData.getBusEntity().getModBy());
                    p.setBusEntityId(accountId);
                    p.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                    p.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ALLOWED_RUNTIME_ORD_ITM_ACT);
                    p.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.ALLOWED_RUNTIME_ORD_ITM_ACT);
                    p.setValue(newAllowedStr);
                    PropertyDataAccess.insert(conn,p);
                }
            }

            //Order manager eMail
            dbc = new DBCriteria();
            dbc.addEqualTo(EmailDataAccess.BUS_ENTITY_ID,accountId);
            dbc.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,
                    RefCodeNames.EMAIL_TYPE_CD.ORDER_MANAGER);
            EmailDataVector emailDV = EmailDataAccess.select(conn,dbc);
            IdVector emailIdsToDelete = new IdVector();
            ArrayList emails = pAccountData.getOrderManagerEmails();
            if(emails==null) emails = new ArrayList();

            for(int ii=0; ii<emailDV.size(); ii++) {
                EmailData eD = (EmailData) emailDV.get(ii);
                String oldEmail = eD.getEmailAddress();
                boolean existsFl = false;
                for(int jj=0;jj<emails.size(); jj++) {
                    String newEmail = (String) emails.get(jj);
                    if(oldEmail.equals(newEmail)) {
                        emails.remove(jj);
                        existsFl = true;
                        break;
                    }
                }
                if(!existsFl) {
                    emailIdsToDelete.add(new Integer(eD.getEmailId()));
                }
            }
            if(emailIdsToDelete.size()>0) {
                dbc = new DBCriteria();
                dbc.addOneOf(EmailDataAccess.EMAIL_ID,emailIdsToDelete);
                EmailDataAccess.remove(conn,dbc);
            }
            if(emails.size()>0) {
                String user = pAccountData.getBusEntity().getModBy();
                for(int ii=0; ii<emails.size(); ii++) {
                    EmailData em = EmailData.createValue();
                    em.setBusEntityId(accountId);
                    em.setEmailAddress((String) emails.get(ii));
                    em.setShortDesc(RefCodeNames.EMAIL_TYPE_CD.ORDER_MANAGER);
                    em.setPrimaryInd(false);
                    em.setEmailTypeCd(RefCodeNames.EMAIL_TYPE_CD.ORDER_MANAGER);
                    em.setEmailStatusCd(RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
                    em.setAddBy(user);
                    em.setModBy(user);
                    EmailDataAccess.insert(conn, em);
                }

            }

            //Account parameters
            //Old ones
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityParameterDataAccess.BUS_ENTITY_ID, accountId);
            BusEntityParameterDataVector parametersDV =
                    BusEntityParameterDataAccess.select(conn,dbc);
            //New Ones
            BusEntityParameterDataVector newParameters = pAccountData.getAccountParameters();
            if(newParameters==null) {
                newParameters = new BusEntityParameterDataVector();
            }

            BusEntityParameterDataVector insertedParameters =
                    new BusEntityParameterDataVector();
            //Insert
            for(Iterator iter = newParameters.iterator(); iter.hasNext();) {
                BusEntityParameterData newBepD = (BusEntityParameterData) iter.next();
                if(newBepD.getBusEntityParameterId()==0) {
                    newBepD = BusEntityParameterDataAccess.insert(conn,newBepD);
                    iter.remove();
                    insertedParameters.add(newBepD);
                }
            }
            // Update
            for(Iterator iter = parametersDV.iterator(); iter.hasNext(); ) {
                BusEntityParameterData bepD = (BusEntityParameterData) iter.next();
                boolean deleteFl = true;
                for(Iterator iter1 = newParameters.iterator(); iter1.hasNext();) {
                    BusEntityParameterData newBepD = (BusEntityParameterData) iter1.next();
                    if(newBepD.getBusEntityParameterId()==bepD.getBusEntityParameterId()) {
                        deleteFl = false;
                        if(newBepD.isDirty()) {
                            BusEntityParameterDataAccess.update(conn,newBepD);
                        }
                        break;
                    }
                }
                if(deleteFl) {
                    BusEntityParameterDataAccess.remove(conn,bepD.getBusEntityParameterId());
                }
            }

            //combine
            newParameters.addAll(insertedParameters);

        } catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }

        return pAccountData;
    }

    /**
     * <code>removeAccount</code> may be used to remove an 'unused' account.
     * An unused account is a account with no database references other than
     * the default primary address, phone numbers, email addresses, properties
     * and the account/store association.  Attempting to remove a account
     * that is used will result in a failure initially reported as a
     * SQLException and consequently caught and rethrown as a RemoteException.
     *
     * @param pAccountData a <code>AccountData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeAccount(AccountData pAccountData)
    throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();

            int accountId = pAccountData.getBusEntity().getBusEntityId();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, accountId);
            PropertyDataAccess.remove(conn, crit);
            PhoneDataAccess.remove(conn, crit);
            EmailDataAccess.remove(conn, crit);
            AddressDataAccess.remove(conn, crit);
            crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID, accountId);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            BusEntityAssocDataAccess.remove(conn, crit);
            crit = new DBCriteria();
            crit.addEqualTo(BusEntityParameterDataAccess.BUS_ENTITY_ID, accountId);
            BusEntityParameterDataAccess.remove(conn, crit);
            BusEntityDataAccess.remove(conn, accountId);
        } catch (Exception e) {
            throw new RemoteException("removeAccount: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Gets the CostCenter information values to be used by the request.
     * @param pCostCenterId the account identifier.
     * @param pAccountId the id of the cost center account.  If nonzero will
     *        only return a Cost Center that belongs to that account.  i.e.
     *        if the Cost Center belongs to a different account, it won't be
     *        returned
     * @param pAccountId the id of the CostCenter Account.
     * @return CostCenterData
     * @throws RemoteException Required by EJB 1.0
     * DataNotFoundException if CostCenter with pCostCenterId for the
     * given Account doesn't exist
     */
    public CostCenterData getCostCenter(int pCostCenterId, int pAccountId)
    throws RemoteException, DataNotFoundException {

        CostCenterData costCenter = null;
        Connection conn = null;

        try {
            conn = getConnection();
            costCenter = CostCenterDataAccess.select(conn, pCostCenterId);
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID,pCostCenterId);
            dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                    RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            IdVector catalogIdV = CostCenterAssocDataAccess.selectIdOnly(conn,
                    CostCenterAssocDataAccess.CATALOG_ID,dbc);


            if (pAccountId != 0) {
                CatalogData cD = getAccountCatalog(conn,pAccountId);

                if(cD==null || !catalogIdV.contains(new Integer(cD.getCatalogId()))) {
                    throw new DataNotFoundException("Cost Center " +
                            pCostCenterId +
                            "not associated with Account " +
                            pAccountId);
                }
            }
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RemoteException("getCostCenter: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }

        return costCenter;
    }

    public CatalogData getAccountCatalog(int pAccountId)
    throws RemoteException    {
        Connection conn = null;

        try        {
            conn = getConnection();
            return getAccountCatalog(conn, pAccountId) ;
        } catch (Exception e)  {
            throw new RemoteException("getAccountCatalog: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public IdVector getAllShoppingCatalogsForAcct(int pAccountId)
    throws RemoteException    {
        Connection conn = null;
        IdVector shopCatalogIds = new IdVector();
        try{
            conn = getConnection();

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pAccountId);
            crit.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
            IdVector catalogIds = CatalogAssocDataAccess.selectIdOnly(conn, CatalogAssocDataAccess.CATALOG_ID, crit);

            crit = new DBCriteria();
            crit.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIds);
            crit.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
            shopCatalogIds = CatalogDataAccess.selectIdOnly(conn, crit);

        } catch (Exception e)  {
            throw new RemoteException("getAccountCatalog: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return shopCatalogIds;
    }

    public CatalogData getAccountCatalog(Connection con, int pAccountId) throws Exception {
        return BusEntityDAO.getAccountCatalog(con, pAccountId);
    }

    public FiscalCalenderDataVector getAccountFiscalCalenders(int pAccountId) throws RemoteException {
        Connection conn = null;
        try        {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID, pAccountId);
            return FiscalCalenderDataAccess.select(conn, dbc);
        } catch (Exception e)  {
            throw new RemoteException("getFiscalCalenders: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Get all the CostCenters for a given account
     * @param pAccountId the Id of the CostCenter's Account.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>CostCenterDataVector</code> with all CostCenters.
     * @exception RemoteException if an error occurs
     */
    public CostCenterDataVector getAllCostCenters(int pAccountId, int pOrder)
    throws RemoteException{
        Connection conn = null;
        try{
            conn = getConnection();
            return getAllCostCenters(pAccountId,pOrder,conn);
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    /**
     * Get all the CostCenters for a given account
     * @param pAccountId the Id of the CostCenter's Account.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @param conn an open connection object
     * @return a <code>CostCenterDataVector</code> with all CostCenters.
     * @exception RemoteException if an error occurs
     */
    public CostCenterDataVector getAllCostCenters(int pAccountId, int pOrder, Connection conn)
    throws Exception{
        CostCenterDataVector costCenterVec;

        //if no fiscal calender return.
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID,pAccountId);
        IdVector fcDV = FiscalCalenderDataAccess.selectIdOnly(conn,dbc);
        if(fcDV.size()==0) {
            return new CostCenterDataVector();
        }

        CatalogData accountCatalogD = getAccountCatalog(conn, pAccountId);
        if(accountCatalogD==null) {
            log.debug("Could not find account catalog data");
            return new CostCenterDataVector();
        }
        int accountCatalogId = accountCatalogD.getCatalogId();

        log.debug("CExamining catalog if: "+accountCatalogId);

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID,accountCatalogId);
        crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                    RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
        IdVector costCenterIdV = CostCenterAssocDataAccess.selectIdOnly(conn,
                                      CostCenterAssocDataAccess.COST_CENTER_ID,crit);

        crit = new DBCriteria();
        crit.addOneOf(CostCenterDataAccess.COST_CENTER_ID, costCenterIdV);

        switch (pOrder) {

            case Account.ORDER_BY_ID:
                crit.addOrderBy(CostCenterDataAccess.COST_CENTER_ID, true);

                break;

            case Account.ORDER_BY_NAME:
                crit.addOrderBy(CostCenterDataAccess.SHORT_DESC, true);

                break;

            default:
                throw new RemoteException("getAllCostCenters: Bad order specification");
        }

        costCenterVec = CostCenterDataAccess.select(conn, crit);

        String s = "\n CostCenters for pAccountId=" + pAccountId +
                " accountCatalogId=" + accountCatalogId;
        for ( int i = 0; null != costCenterVec && i < costCenterVec.size(); i++ ) {
            CostCenterData ccd = (CostCenterData)costCenterVec.get(i);
            s += "\n\t\t" + ccd;
            if ( null == ccd.getAllocateFreight()) {
                ccd.setAllocateFreight("0");
            }
        }
        logDebug("Account.getAllCostCenters" + s);

        return costCenterVec;
    }

    /**
     * Get all CostCenters that match the given name.  The arguments specify
     * whether the name is interpreted as a pattern or exact match.
     *
     * @param pName a <code>String</code> value with CostCenter name or pattern
     * @param pAccountId the id of the CostCenter Account.
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, CONTAINS,
     * EXACT_MATCH_IGNORE_CASE, BEGINS_WITH_IGNORE_CASE and
     * CONTAINS_IGNORE_CASE.
     * @param pOrder one of ORDER_BY_ID, ORDER_BY_NAME
     * @return a <code>CostCenterDataVector</code> of matching CostCenters
     * @exception RemoteException if an error occurs
     */

    public CostCenterDataVector getCostCenterByName
            (String pName,
            int pAccountId, int pMatch,
            int pOrder)
            throws RemoteException    {

        CostCenterDataVector costCenterVec = new CostCenterDataVector();
        Connection conn = null;

        try {
            conn = getConnection();

            CatalogData accountCatalogD = getAccountCatalog(conn, pAccountId);
            if(accountCatalogD==null) {
                return costCenterVec;
            }
            int accountCatalogId = accountCatalogD.getCatalogId();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID,pAccountId);
            IdVector fcDV = FiscalCalenderDataAccess.selectIdOnly(conn,dbc);
            if(fcDV.size()==0) {
                return costCenterVec;
            }

            DBCriteria crit = new DBCriteria();

            switch (pMatch) {

                case Account.EXACT_MATCH:
                    crit.addEqualTo(CostCenterDataAccess.SHORT_DESC, pName);

                    break;

                case Account.EXACT_MATCH_IGNORE_CASE:
                    crit.addEqualToIgnoreCase(CostCenterDataAccess.SHORT_DESC,
                            pName);

                    break;

                case Account.BEGINS_WITH:
                    crit.addLike(CostCenterDataAccess.SHORT_DESC, pName +
                            "%");

                    break;

                case Account.BEGINS_WITH_IGNORE_CASE:
                    crit.addLikeIgnoreCase(CostCenterDataAccess.SHORT_DESC,
                            pName + "%");

                    break;

                case Account.CONTAINS:
                    crit.addLike(CostCenterDataAccess.SHORT_DESC,
                            "%" + pName + "%");

                    break;

                case Account.CONTAINS_IGNORE_CASE:
                    crit.addLikeIgnoreCase(CostCenterDataAccess.SHORT_DESC,
                            "%" + pName + "%");

                    break;

                default:
                    throw new RemoteException("getCostCenterByName: Bad match specification");
            }

            DBCriteria crit1 = new DBCriteria();
            crit1.addEqualTo(CostCenterAssocDataAccess.CATALOG_ID,accountCatalogId);
            crit1.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                    RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            IdVector costCenterIdV = CostCenterAssocDataAccess.selectIdOnly(conn,
                    CostCenterAssocDataAccess.COST_CENTER_ID,crit1);

            crit.addOneOf(CostCenterDataAccess.COST_CENTER_ID, costCenterIdV);

            switch (pOrder) {

                case Account.ORDER_BY_ID: //doesn't matter only one account requested
                    //crit.addOrderBy(CostCenterDataAccess.BUS_ENTITY_ID, true);

                    break;

                case Account.ORDER_BY_NAME:
                    crit.addOrderBy(CostCenterDataAccess.SHORT_DESC, true);

                    break;

                default:
                    throw new RemoteException("getCostCenterByName: Bad order specification");
            }

            costCenterVec = CostCenterDataAccess.select(conn, crit);
        } catch (Exception e)        {
            throw new RemoteException("getCostCenterByName: " +
                    e.getMessage());
        } finally        {
            closeConnection(conn);
        }

        return costCenterVec;
    }

    /**
     * Describe <code>addCostCenter</code> method here.
     *
     * @param pCostCenterData a <code>CostCenterData</code> value
     * @param pAccountId the id of the account store.
     * @return an <code>CostCenterData</code> value
     * @exception RemoteException if an error occurs
     * DuplicateNameException if another cost center with same name exists
     */
    /*
    public CostCenterData addCostCenter(CostCenterData pCostCenterData,
            int pAccountId)
            throws RemoteException,
            DuplicateNameException {
        Connection conn = null;
        int accountCatalogId = 0;
        try {
            conn = getConnection();
            CatalogData accountCatalogD = getAccountCatalog(conn,pAccountId);
            if(accountCatalogD==null) {
                throw new Exception("No account catalog for account "+pAccountId);
            }
            accountCatalogId = accountCatalogD.getCatalogId();
            if (pCostCenterData.getCatalogId() != accountCatalogId) {
                pCostCenterData.setCatalogId(accountCatalogId);
            }

            return updateCostCenter(conn, pCostCenterData);
        } catch (DuplicateNameException ne) {
            throw ne;
        } catch (Exception e){
            throw new RemoteException( e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }
    */

    /**
     * Updates the CostCenter information values to be used by the request.
     * @param pCostCenterData  the CostCenterData object.
     * @return an <code>CostCenterData</code> value
     * @throws RemoteException Required by EJB 1.0
     * DuplicateNameException if another cost center with same name exists
     */

    public CostCenterData updateCostCenter(CostCenterData pCostCenterData)
    throws RemoteException,
            DuplicateNameException {

        Connection conn = null;

        try {
            conn = getConnection();
            pCostCenterData = updateCostCenter(conn, pCostCenterData);
        } catch (DuplicateNameException ne){
            throw ne;
        } catch (Exception e) {
            throw new RemoteException("updateCostCenter: " + e.getMessage());
        } finally  {
            closeConnection(conn);
        }

        return pCostCenterData;
    }


    private CostCenterData updateCostCenter(Connection conn, CostCenterData pCostCenterData)
    throws DuplicateNameException, Exception {
        if (pCostCenterData.isDirty()) {
            int costCenterId = pCostCenterData.getCostCenterId();

            // check that name is unique
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID,costCenterId);
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                    RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            IdVector catalogIdV = CostCenterAssocDataAccess.selectIdOnly(conn,
                    CostCenterAssocDataAccess.CATALOG_ID,crit);

            crit = new DBCriteria();
            crit.addOneOf(CostCenterAssocDataAccess.CATALOG_ID, catalogIdV);
            crit.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                    RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
            crit.addNotEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID, costCenterId);
            IdVector costCenterIdV = CostCenterAssocDataAccess.selectIdOnly(conn,
                    CostCenterAssocDataAccess.COST_CENTER_ID,crit);



            crit = new DBCriteria();
            crit.addEqualTo(CostCenterDataAccess.SHORT_DESC,
                    pCostCenterData.getShortDesc());
            crit.addNotEqualTo(CostCenterDataAccess.COST_CENTER_ID,costCenterId);
            crit.addOneOf(CostCenterDataAccess.COST_CENTER_ID,costCenterIdV);

            IdVector dups = CostCenterDataAccess.selectIdOnly(conn, crit);
            if (dups.size() > 0) {
                throw new DuplicateNameException(CostCenterDataAccess.SHORT_DESC);
            }

            if (pCostCenterData.getCostCenterId() == 0) {
                pCostCenterData=CostCenterDataAccess.insert(conn, pCostCenterData);
            } else {
                CostCenterDataAccess.update(conn, pCostCenterData);
            }

            setBudgetTypeStatus(conn, pCostCenterData);
        }
        return pCostCenterData;
    }

    private void setBudgetTypeStatus(Connection conn, CostCenterData pCostCenterData) throws Exception {

        int costCenterId = pCostCenterData.getCostCenterId();
        if (costCenterId == 0) {
            return;
        }

        String costCenterType = pCostCenterData.getCostCenterTypeCd();
        if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(costCenterType) ||
                RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(costCenterType)) {

            setOrderBudgetTypeStatus(conn, pCostCenterData);

        } else if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET.equals(costCenterType) ||
                RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET.equals(costCenterType)) {

            setWorkOrderBudgetTypeStatus(conn, pCostCenterData);

        } else {
            throw new RemoteException("Invalid cost center type code: " + pCostCenterData.getCostCenterTypeCd());
        }
    }

    private void setOrderBudgetTypeStatus(Connection conn, CostCenterData pCostCenterData) throws Exception {

        String siteBudgetTypeCd = RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET;
        String accountBudgetTypeCd = RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET;
        setBudgetTypeStatus(conn, pCostCenterData, siteBudgetTypeCd, accountBudgetTypeCd);

    }


    /**
     * Updates budget status depending on cost center type (ACCOUNT_BUDGET or SITE_BUDGET
     * and SITE_WORK_ORDER_BUDGET or ACCOUNT_WORK_ORDER_BUDGET)
     * @param pCostCenterData  the CostCenterData object.
     * @throws RemoteException Required by EJB 1.0
     */
    public void setBudgetTypeStatus(CostCenterData pCostCenterData)  throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
            setBudgetTypeStatus(conn, pCostCenterData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("updateCostCenter: " + e.getMessage());
        } finally  {
            closeConnection(conn);
        }

    }

    private void setWorkOrderBudgetTypeStatus(Connection conn, CostCenterData pCostCenterData) throws Exception {

        String siteBudgetTypeCd = RefCodeNames.BUDGET_TYPE_CD.SITE_WORK_ORDER_BUDGET;
        String accountBudgetTypeCd = RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_WORK_ORDER_BUDGET;
        setBudgetTypeStatus(conn, pCostCenterData,siteBudgetTypeCd,accountBudgetTypeCd);
    }

    private void setBudgetTypeStatus(Connection conn, CostCenterData pCostCenterData, String siteBudgetTypeCd, String accountBudgetTypeCd) throws Exception {

        int costCenterId = pCostCenterData.getCostCenterId();
        //update the budgets associated with this cost center.  If the cost center is an account type
        //set the account type budgets to active and the site type to inactive and visa versa
        //If the cost center itself is inactive than all the budgets should also be inactive
        boolean siteBudgetsInactive = false;
        boolean acctBudgetsInactive = false;
        String costCenterType = pCostCenterData.getCostCenterTypeCd();
        String costCenterStatus = pCostCenterData.getCostCenterStatusCd();
        if (RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(costCenterStatus)) {
            if (accountBudgetTypeCd.equals(costCenterType)) {
                siteBudgetsInactive = true;
            } else if (siteBudgetTypeCd.equals(costCenterType)) {
                acctBudgetsInactive = true;
            }
        } else {
            siteBudgetsInactive = true;
            acctBudgetsInactive = true;
        }

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BudgetDataAccess.COST_CENTER_ID, costCenterId);
        BudgetDataVector bdv = BudgetDataAccess.select(conn, crit);
        Iterator it = bdv.iterator();
        while (it.hasNext()) {
            BudgetData bud = (BudgetData) it.next();
            if (siteBudgetsInactive) {
                if (siteBudgetTypeCd.equals(bud.getBudgetTypeCd())) {
                    if (RefCodeNames.BUDGET_STATUS_CD.ACTIVE.equals(bud.getBudgetStatusCd())) {
                        bud.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.INACTIVE);
                        BudgetDataAccess.update(conn, bud);
                    }
                }
            } else {
                if (RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(bud.getBudgetTypeCd())) {
                    if (RefCodeNames.BUDGET_STATUS_CD.INACTIVE.equals(bud.getBudgetStatusCd())) {
                        bud.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
                        BudgetDataAccess.update(conn, bud);
                    }
                }
            }
            if (acctBudgetsInactive) {
                if (accountBudgetTypeCd.equals(bud.getBudgetTypeCd())) {
                    if (RefCodeNames.BUDGET_STATUS_CD.ACTIVE.equals(bud.getBudgetStatusCd())) {
                        bud.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.INACTIVE);
                        BudgetDataAccess.update(conn, bud);
                    }
                }
            } else {
                if (accountBudgetTypeCd.equals(bud.getBudgetTypeCd())) {
                    if (RefCodeNames.BUDGET_STATUS_CD.INACTIVE.equals(bud.getBudgetStatusCd())) {
                        bud.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
                        BudgetDataAccess.update(conn, bud);
                    }
                }
            }
        }
    }

    /**
     * <code>removeCostCenter</code> may be used to remove an 'unused'
     * CostCenter. An unused CostCenter is a CostCenter with no database
     * references other than the default properties.
     * Attempting to remove a CostCenter that is used will
     * result in a failure initially reported as a SQLException and
     * consequently caught and rethrown as a RemoteException.
     *
     * @param pCostCenterData a <code>CostCenterData</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void removeCostCenter(CostCenterData pCostCenterData)
    throws RemoteException {

        Connection conn = null;

        try {
            conn = getConnection();
            //check assignement
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(CatalogStructureDataAccess.COST_CENTER_ID,
                    pCostCenterData.getCostCenterId());
            dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                    RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);

            IdVector catalogIdV =
                    CatalogStructureDataAccess.selectIdOnly(conn,
                    CatalogStructureDataAccess.CATALOG_ID,dbc);
            if(catalogIdV.size()>0) {
                dbc = new DBCriteria();
                dbc.addOneOf(CatalogDataAccess.CATALOG_ID,catalogIdV);
                dbc.addNotEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                        RefCodeNames.CATALOG_STATUS_CD.INACTIVE);
                CatalogDataVector catDV = CatalogDataAccess.select(conn,dbc,5);

                if(catalogIdV.size()>0) {
                    String errorMess = "";
                    for(int ii=0; ii<catDV.size(); ii++) {
                        if(ii>0) errorMess += ", ";
                        CatalogData cD = (CatalogData) catDV.get(ii);
                        String sd = cD.getShortDesc();
                        if(sd==null) sd = "";
                        errorMess += sd.trim()+"("+cD.getCatalogId()+")";
                    }
                    throw new Exception("^clw^ Some catalogs have item assigned " +
                            "to the cost center. Cataogs: "+errorMess+"^clw^");
                }
            }


            // remove any site budget data first
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BudgetDataAccess.COST_CENTER_ID,
                    pCostCenterData.getCostCenterId());
            BudgetDataAccess.remove(conn, crit);

            // now remove the cost center itself
            CostCenterDataAccess.remove(conn,
                    pCostCenterData.getCostCenterId());

        } catch (Exception e) {
            throw new RemoteException("removeCostCenter: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
    }

    public ArrayList getAllItemToCostCenters(int pAccountId)
    throws RemoteException    {
        String sql1 = sqlForAllCatalogs(pAccountId);
        String sql2 = " SELECT cs.catalog_id,  " +
                " ( select sku_num from clw_item i " +
                "    where cs.item_id = i.item_id) as sku_num,  " +
                " ( select trim(i.short_desc) from clw_item i " +
                "    where cs.item_id = i.item_id) as item_desc,  " +
                " cs.cost_center_id, 'none' AS cost_center_name " +
                " from    clw_catalog_structure cs, clw_catalog cat  " +
                " where cs.catalog_id in ( " + sql1 + " )  " +
                " and cat.catalog_id = cs.catalog_id and cat.catalog_type_cd in (" +
                " '" + RefCodeNames.CATALOG_TYPE_CD.SHOPPING + "'," +
                " '" + RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING + "' )" +
                " and cs.catalog_structure_cd = 'CATALOG_PRODUCT' " +
                " AND (cost_center_id = 0 or cost_center_id is null) "
                + " order by 1,2 ";

        ArrayList itoccv = new ArrayList();

        Connection conn = null;
        try        {
            conn = getConnection();
            logDebug(" SQL: " + sql2);
            itoccv = UniversalDAO.getData(conn, sql2);

        } catch (Exception e) {
            logError("getAllItemToCostCenters, error: " + e);
            e.printStackTrace();
            throw new RemoteException
                    ("getAllItemToCostCenters, error: " + e);
        } finally {
            closeConnection(conn);
        }

        return itoccv;
    }

    public CategoryToCostCenterViewVector
            getAllCategoryToCostCenters(int pAccountId)
            throws RemoteException {
        String sql1 = sqlForAllCatalogs(pAccountId);

        CategoryToCostCenterViewVector ccv =
                new CategoryToCostCenterViewVector();

        Connection conn = null;
        try {

            conn = getConnection();
            Statement stmt1 = conn.createStatement();
            logDebug("SQL 1: " + sql1);
            ResultSet rs1 = stmt1.executeQuery(sql1);
            StringBuffer catids = new StringBuffer("");
            boolean addcomma = false;
            while (rs1.next()) {
                if ( addcomma ) {
                    catids.append(", ");
                }
                catids.append(String.valueOf(rs1.getInt(1)));
                addcomma = true;
            }
            rs1.close();
            stmt1.close();

            if ( catids.length() > 0 ) {
                String sql2 = "select distinct trim(i.short_desc), " +
                        " cs.cost_center_id from  " +
                        " clw_catalog_structure cs, clw_catalog cat , clw_item i " +
                        " where cs.catalog_id in ( " +
                        catids + " ) " +
                        " and cs.catalog_structure_cd = 'CATALOG_CATEGORY' " +
                        " and cs.item_id (+) = i.item_id" +
                        " and cat.catalog_id = cs.catalog_id and cat.catalog_type_cd in (" +
                        " '" + RefCodeNames.CATALOG_TYPE_CD.SHOPPING + "'," +
                        " '" + RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING + "' )" +
                        " order by 1 ";

                Statement stmt2 = conn.createStatement();
                logDebug("SQL 2: " + sql2);
                ResultSet rs2 = stmt2.executeQuery(sql2);

                while (rs2.next()) {
                    CategoryToCostCenterView catToCostCenter =
                            new CategoryToCostCenterView
                            (rs2.getString(1), rs2.getInt(2));
                    ccv.add(catToCostCenter);
                }

                rs2.close();
                stmt2.close();
            }
        } catch (Exception e) {
            logError("getAllCategoryToCostCenters, error: " + e);
            e.printStackTrace();
            throw new RemoteException
                    ("getAllCategoryToCostCenters, error: " + e);
        } finally {
            closeConnection(conn);
        }

        return ccv;
    }

    public void setCategoryCostCenter(int pAccountId,
            String pCatalogCategoryName,
            int pNewCostCenterId)
            throws RemoteException {
        logDebug("setCategoryCostCenter (int pAccountId=" + pAccountId +
                ", String pCatalogCategoryName=" + pCatalogCategoryName +
                ", int pNewCostCenterId=" + pNewCostCenterId + ")");

        String sql = sqlForAllCatalogs(pAccountId);

        Connection con = null;
        try {
            con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            StringBuffer catids = new StringBuffer("");
            boolean addcomma = false;
            while (rs.next()) {
                if ( addcomma ) {
                    catids.append( ", ");
                }
                catids.append( String.valueOf(rs.getInt(1)) );
                addcomma = true;
            }

            logDebug( "Catalog ids found: " + catids );
            if ( catids.length() > 0 ) {

                // Update the cost center for each item found
                // under this category.
                String updSql = "update clw_catalog_structure cs2 " +
                        " set cost_center_id = " + pNewCostCenterId +
                        " where " +
                        " cs2.catalog_id in ( " + catids + " ) " +
                        " and  cs2.item_id in ( " +
                        " select distinct ia.item1_id " +
                        " from clw_item_assoc ia where " +
                        " ia.catalog_id in ( " + catids + " ) and " +
                        " ( item2_id in (select distinct cs.item_id " +
                        " from clw_catalog_structure cs , clw_item i " +
                        " where cs.catalog_id in ( " + catids + " ) " +
                        " and trim(i.short_desc) = ?  " +
                        " and i.item_id = cs.item_id ) ) )  "  ;


                logDebug("UPD SQL 1: " + updSql);
                PreparedStatement stmt2 = con.prepareStatement(updSql);
                stmt2.setString(1, pCatalogCategoryName);
                stmt2.executeUpdate();
                stmt2.close();

                // Update the cost center for the category.
                updSql = "update clw_catalog_structure cs2 " +
                        " set cost_center_id = " + pNewCostCenterId +
                        " where " +
                        " cs2.catalog_id in ( " + catids + " ) " +
                        " and  cs2.item_id in ( " +
                        "   select distinct cs.item_id " +
                        "   from clw_catalog_structure cs , clw_item i " +
                        "   where cs.catalog_id in ( " + catids + " ) " +
                        "   and trim(i.short_desc) = ? " +
                        "   and i.item_id = cs.item_id ) " ;

                logDebug("UPD SQL 2: " + updSql);
                stmt2 = con.prepareStatement(updSql);
                stmt2.setString(1, pCatalogCategoryName);
                stmt2.executeUpdate();
                stmt2.close();

            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            logError(" setCategoryCostCenter error: " + e);
        } finally {
            closeConnection(con);
        }

        return;
    }

    private String sqlForAllCatalogs(int pAccountId) {
        return "  select distinct catalog_id from clw_catalog_assoc " +
                "   where bus_entity_id = " + pAccountId +
                "  or bus_entity_id in " +
                "  ( select bus_entity1_id from clw_bus_entity_assoc " +
                "   where bus_entity2_id = " + pAccountId +
                "  ) " ;
    }

    private String sqlForAccountCatalog(int pAccountId) {
        return "select ca.catalog_id  from "
                + " clw_catalog_assoc ca , clw_catalog cat  "
                + " where ca.bus_entity_id = " + pAccountId
                + " and cat.catalog_id = ca.catalog_id "
                + " and cat.catalog_type_cd = '"
                + RefCodeNames.CATALOG_TYPE_CD.ACCOUNT + "'";
    }

    private BusEntityData getBusEntity(int pBusEntId, java.util.Map busEntMap, Connection con)
    throws DataNotFoundException, SQLException{

        if ( pBusEntId <= 0 ) return null;
        Integer key = new Integer(pBusEntId);
        BusEntityData data = (BusEntityData) busEntMap.get(key);
        if(data == null){
            try{
                data = BusEntityDataAccess.select(con,pBusEntId);
                busEntMap.put(key, data);
            }catch(DataNotFoundException e){
                return null;
            }
        }
        return data;
    }

    private ContractData getContract(int pContractId, java.util.Map contractMap, Connection con)
    throws DataNotFoundException, SQLException{

        if ( pContractId <= 0 ) return null;

        Integer key = new Integer(pContractId);
        ContractData data = (ContractData) contractMap.get(key);
        if(data == null){
            try{
                data = ContractDataAccess.select(con,pContractId);
                contractMap.put(key, data);
            }catch(DataNotFoundException e){
                return null;
            }
        }
        return data;
    }

    private CatalogData getCatalog(int pCatalogId, java.util.Map catalogMap, Connection con)
    throws DataNotFoundException, SQLException{

        if ( pCatalogId <= 0 ) return null;

        Integer key = new Integer(pCatalogId);
        CatalogData data = (CatalogData) catalogMap.get(key);
        if(data == null){
            try{
                data = CatalogDataAccess.select(con,pCatalogId);
                catalogMap.put(key, data);
            }catch(DataNotFoundException e){
                return null;
            }
        }
        return data;
    }

    private boolean isValidOrderRoute(Connection con,OrderRoutingData ord,java.util.Map cache)
    throws SQLException{
        java.util.Map contractCache = (java.util.Map) cache.get(ContractData.class.getName());
        java.util.Map busEntCache = (java.util.Map) cache.get(BusEntityData.class.getName());
        if (contractCache==null){
            contractCache = new java.util.HashMap();
            cache.put(ContractData.class.getName(),contractCache);
        }
        if(busEntCache==null){
            busEntCache = new java.util.HashMap();
            cache.put(BusEntityData.class.getName(),busEntCache);
        }
        try{
            if(getBusEntity(ord.getAccountId(), busEntCache, con) == null){return false;}

            //0 indicate value is not set and shuld not be validated
            boolean isTrueNegative = false;
            if(ord.getDistributorId() != 0){
                if(getBusEntity(ord.getDistributorId(), busEntCache, con) == null){return false;}
            }else{
                isTrueNegative = true;
            }

            if(ord.getContractId() != 0){
                if(isTrueNegative){return false;}
                if(getContract(ord.getContractId(), contractCache, con) == null){return false;}
            }else if(!isTrueNegative){
                //value must be set if distributor is set and value cannot be set if
                //distributor is not set
                return false;
            }

            if(ord.getFinalDistributorId() != 0){
                if(isTrueNegative){return false;}
                if(getBusEntity(ord.getFinalDistributorId(), busEntCache, con) == null){return false;}
            }else if(!isTrueNegative){
                //value must be set if distributor is set and value cannot be set if
                //distributor is not set
                return false;
            }

            if(ord.getFreightHandlerId() != 0){
                if(isTrueNegative){return false;}
                if(getBusEntity(ord.getFreightHandlerId(), busEntCache, con) == null){return false;}
            }else if(!isTrueNegative){
                //value must be set if distributor is set and value cannot be set if
                //distributor is not set
                return false;
            }

            if(ord.getFinalContractId() != 0){
                if(isTrueNegative){return false;}
                if(getContract(ord.getFinalContractId(), contractCache, con) == null){return false;}
            }else if(!isTrueNegative){
                //value must be set if distributor is set and value cannot be set if
                //distributor is not set
                return false;
            }

            if(!Utility.isSet(ord.getZip())){return false;}
        }catch(DataNotFoundException e){
            return false;
        }
        return true;
    }


    /**
     *Saves a collection of OrderRoutingData or OrderRoutingDescView (the OrderRoutingData portion of it).
     *If the supplied data is a OrderRoutingDescView data the delete flag may be specified and the flag
     *records will be removed.
     *
     *@param pOrderRoutingCollection a list containing OrderRoutingDescView data, and or OrderRoutingData
     *@throws RemoteException if an error occurs
     */
    public void updateAccountOrderRoutingCollection(java.util.List pOrderRoutingCollection)
    throws RemoteException{
        Connection con = null;
        try {
            con = getConnection();
            Iterator it = pOrderRoutingCollection.iterator();
            java.util.HashMap validationCache = new java.util.HashMap();
            while(it.hasNext()){
                boolean delete = false;
                OrderRoutingData ord = null;
                Object obj = it.next();
                if(obj instanceof OrderRoutingDescView){
                    delete = ((OrderRoutingDescView) obj).getDelete();
                    ord = ((OrderRoutingDescView) obj).getOrderRoutingData();
                }else{
                    ord = (OrderRoutingData) obj;
                }
                if(ord.isDirty()){
                    if(ord.getOrderRoutingId() == 0){
                        if(!delete){
                            if(isValidOrderRoute(con,ord,validationCache)){
                                //check to make sure it does not exist already
                                DBCriteria crit = new DBCriteria();
                                crit.addEqualTo(OrderRoutingDataAccess.ZIP,ord.getZip());
                                crit.addEqualTo(OrderRoutingDataAccess.ACCOUNT_ID,ord.getAccountId());
                                OrderRoutingDataVector ordv = OrderRoutingDataAccess.select(con,crit);
                                if(ordv.size() != 0){
                                    throw new RemoteException("Entry already exists for account id: "+ord.getAccountId()+" and zip: "+ord.getZip());
                                }
                                OrderRoutingDataAccess.insert(con, ord);
                            }else{
                                throw new RemoteException("Order Route Is Not Valid For Zip: " + ord.getZip());
                            }
                        }
                    }else{
                        if(delete){
                            OrderRoutingDataAccess.remove(con, ord.getOrderRoutingId());
                        }else{
                            if(isValidOrderRoute(con,ord,validationCache)){
                                DBCriteria crit = new DBCriteria();
                                crit.addEqualTo(OrderRoutingDataAccess.ZIP,ord.getZip());
                                crit.addNotEqualTo(OrderRoutingDataAccess.ORDER_ROUTING_ID,ord.getOrderRoutingId());
                                crit.addEqualTo(OrderRoutingDataAccess.ACCOUNT_ID,ord.getAccountId());
				OrderRoutingDataVector ordv = OrderRoutingDataAccess.select(con,crit);
                                if(ordv.size() != 0){
                                    throw new RemoteException("Entry already exists for account id: "+ord.getAccountId()+" and zip: "+ord.getZip());
                                }
                                OrderRoutingDataAccess.update(con, ord);
                            }else{
                                throw new RemoteException("Order Route Is Not Valid For Zip: " + ord.getZip());
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }finally {
            closeConnection(con);
        }
    }

    public OrderRoutingDescViewVector getAccountOrderRoutingCollection(int pAccountId)
    throws RemoteException {

        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderRoutingDataAccess.ACCOUNT_ID, pAccountId);
            crit.addOrderBy(OrderRoutingDataAccess.ZIP);
            OrderRoutingDataVector ordv = OrderRoutingDataAccess.select(con,crit);

            OrderRoutingDescViewVector descCol = new OrderRoutingDescViewVector();
            Iterator it = ordv.iterator();
            java.util.HashMap busEntMap = new java.util.HashMap();
            java.util.HashMap contractMap = new java.util.HashMap();
            java.util.HashMap catalogMap = new java.util.HashMap();
            while(it.hasNext()){
                OrderRoutingDescView ordVw = OrderRoutingDescView.createValue();
                OrderRoutingData ord = (OrderRoutingData) it.next();
                ordVw.setOrderRoutingData(ord);
                ordVw.setAccount(getBusEntity(ord.getAccountId(), busEntMap, con));
                ordVw.setDistributor(getBusEntity(ord.getDistributorId(), busEntMap, con));
                ordVw.setFreightHandler(getBusEntity(ord.getFreightHandlerId(), busEntMap, con));
                ordVw.setFinalDistributor(getBusEntity(ord.getFinalDistributorId(), busEntMap, con));
                ContractData contractD = getContract(ord.getContractId(), contractMap, con);
                ordVw.setContract(contractD);
                if(contractD!=null) {
                  int catalogId = contractD.getCatalogId();
                  ordVw.setCatalog(getCatalog(catalogId,catalogMap,con));
                }
                ContractData finalContractD = getContract(ord.getFinalContractId(), contractMap, con);
                ordVw.setFinalContract(finalContractD);
                if(finalContractD!=null) {
                  int finalCatalogId = finalContractD.getCatalogId();
                  ordVw.setFinalCatalog(getCatalog(finalCatalogId,catalogMap,con));
                }
                ordVw.setFinalFreightHandler(getBusEntity(ord.getFinalFreightHandlerId(), busEntMap, con));
                ordVw.setLtlFreightHandler(getBusEntity(ord.getLtlFreightHandlerId(), busEntMap, con));
                descCol.add(ordVw);
            }
            return descCol;	}catch (Exception e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }finally {
                closeConnection(con);
            }
    }

    public void resetCostCenters(int pAccountId)
    throws RemoteException {

        Connection con = null;
        try {
            con = getConnection();

            String sqlForAllCatalogs = sqlForAllCatalogs(pAccountId);
            Statement stmt0 = con.createStatement();
            ResultSet rs0 = stmt0.executeQuery(sqlForAllCatalogs);
            LinkedList catalogIdVV = new LinkedList();
            int ind = 0;
            IdVector catalogIdV = null;
            while (rs0.next()) {
                int catalogId = rs0.getInt("CATALOG_ID");
                if(catalogIdV==null || catalogIdV.size()>=999) {
                    catalogIdV = new IdVector();
                    catalogIdVV.add(catalogIdV);
                }
                catalogIdV.add(new Integer(catalogId));
            }
            rs0.close();
            stmt0.close();

            for(Iterator iter=catalogIdVV.iterator(); iter.hasNext(); ) {
                catalogIdV = (IdVector) iter.next();
                String q = " select distinct ia.item1_id product_item_id, "
                        + " cs.item_id category_id,  "
                        + " cs.catalog_id,  "
                        + " cs.cost_center_id category_cost_center_id "
                        + " , cs2.cost_center_id product_cost_center_id "
                        + " from clw_catalog_structure cs , clw_catalog cat , "
                        + " clw_item_assoc ia, clw_catalog_structure cs2   "
                        + " where cs.catalog_structure_cd = 'CATALOG_CATEGORY' " +
                        " and cat.catalog_id = cs.catalog_id and cat.catalog_type_cd in (" +
                        " '" + RefCodeNames.CATALOG_TYPE_CD.SHOPPING + "'," +
                        " '" + RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING + "' )" +
                        " and cs.cost_center_id > 0 "
                        + " and cs.catalog_id in (  "
                        +  (IdVector.toCommaString(catalogIdV))
                        + " ) and cs.item_id = ia.item2_id "
                        + " and ia.catalog_id = cs.catalog_id "
                        + " and ia.catalog_id = cs2.catalog_id "
                        + " and ia.item1_id = cs2.item_id "
                        + " and cs2.cost_center_id <> cs.cost_center_id ";

                Statement stmt1 = con.createStatement();
                Statement stmt2 = con.createStatement();

                ResultSet rs1 = stmt1.executeQuery(q);
                while (rs1.next()) {
                    int prodItemId = rs1.getInt("PRODUCT_ITEM_ID");
                    int newCostCenterId = rs1.getInt("CATEGORY_COST_CENTER_ID");
                    int catalogId = rs1.getInt("CATALOG_ID");
                    String updsql = " update clw_catalog_structure "
                            + " set cost_center_id = " + newCostCenterId
                            + " where catalog_id = " + catalogId
                            + " and item_id = " + prodItemId;
                    logDebug("resetCostCenters UPD SQL 1: " + updsql);
                    stmt2.executeUpdate(updsql);
                }

                stmt2.close();
                stmt1.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            logError(" resetCostCenters error: " + e);
        } finally {
            closeConnection(con);
        }

        return;

    }
    private int findCostCenterId(int itemId, CatalogStructureDataVector csdv) {
        for ( int i = 0; csdv != null && i < csdv.size(); i++ ) {
            CatalogStructureData csd =
                    (CatalogStructureData)csdv.get(i);
            if ( itemId == csd.getItemId() ) {
                logDebug("    Found category csd=" + csd);
                return csd.getCostCenterId();
            }
        }
        return 0;
    }

    public void updateOrderRoutingEntry(OrderRoutingData pOrd)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderRoutingDataAccess.ACCOUNT_ID, pOrd.getAccountId());
            crit.addEqualTo(OrderRoutingDataAccess.ZIP, pOrd.getZip());
            OrderRoutingDataVector ordv = OrderRoutingDataAccess.select(con,crit);
            for ( int i = 0; ordv != null && i < ordv.size(); i++ ) {
                OrderRoutingData ord = (OrderRoutingData)ordv.get(i);
                pOrd.setOrderRoutingId(ord.getOrderRoutingId());
                OrderRoutingDataAccess.update(con,pOrd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(con);
        }
    }

    public void deleteOrderRoutingEntry(int pAccountId, String pZip)
    throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderRoutingDataAccess.ACCOUNT_ID, pAccountId);
            crit.addEqualTo(OrderRoutingDataAccess.ZIP, pZip);
            OrderRoutingDataAccess.remove(con,crit);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(con);
        }
    }

    public SiteDeliveryScheduleViewVector
            getAccountDeliveryScheduleCollection(int pAccountId)
            throws RemoteException {
        logDebug("getAccountDeliveryScheduleCollection, pAccountId=" + pAccountId);
        Connection con = null;
        try {
            con = getConnection();
            // Get all schedules for this account.
            return BusEntityDAO.getDeliverySchs(con, pAccountId, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }
        return new SiteDeliveryScheduleViewVector();
    }

    public void updateSiteDeliverySchedule
            (int pSiteId, String pSchedType, String [] pWeeks, String intervWeek)
            throws RemoteException {

        logDebug("pSiteId=" + pSiteId + ", pSchedType=" + pSchedType +
                ",  pWeeks=" + pWeeks);

        String finalsch = pSchedType;
        if ( pSchedType.equals("Monthly")) {
            finalsch += ":" + pWeeks[0];
        } else if (pSchedType.startsWith("Spe") ) {
            finalsch += ":" + intervWeek;
        } else if ( pSchedType.startsWith("Bi") ) {
            finalsch += ":" + pWeeks[0] + ":" + pWeeks[1];
        }

        Connection con = null;
        try {
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pSiteId);
            crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE
                    );
            PropertyDataVector pdv = PropertyDataAccess.select(con,crit);
            if ( pdv == null || pdv.size() == 0 ) {
                PropertyData propd = PropertyData.createValue();
                propd.setBusEntityId(pSiteId);
                propd.setValue(finalsch);
                propd.setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE);
                propd.setPropertyTypeCd
                        (RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE);
                propd.setPropertyStatusCd
                        (RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                PropertyDataAccess.insert(con,propd);
                return;
            }

            logDebug("  update property: 1" );
            for ( int i = 0; pdv != null && i < pdv.size(); i++ ) {
                PropertyData propd = (PropertyData)pdv.get(i);
                logDebug("  update property: " + propd);
                propd.setValue(finalsch);
                PropertyDataAccess.update(con,propd);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(con);
        }

        return;
    }

    /**
     * Get account substitutions for the item
     * @param pAccountId the account identifier
     * @param pItemId the item identifier
     * @return a set of ItemSubstitutionDefData objects
     * @exception RemoteException
     */
    public ItemSubstitutionDefDataVector getAccountItemSubstiutions
            (int pAccountId, int pItemId, boolean pActiveOnlyFlag)
            throws RemoteException {
        Connection conn = null;
        ItemSubstitutionDefDataVector substitutions = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.BUS_ENTITY_ID,pAccountId);
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.ITEM_ID,pItemId);
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_TYPE_CD,
                    RefCodeNames.SUBST_TYPE_CD.ITEM_ACCOUNT);
            if(pActiveOnlyFlag) {
                dbc.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_STATUS_CD,
                        RefCodeNames.SUBST_STATUS_CD.ACTIVE);
            }
            substitutions = ItemSubstitutionDefDataAccess.select(conn,dbc);
        }catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("AccountBean.getAccountItemSubstiution. Exception. "+exc.getMessage());
        } finally {
            closeConnection(conn);
        }
        return substitutions;
    }

    /**
     * Saves account substitution. Updates the state if the substitution exists and
     *  differs or inserts new substitution record if does not exist
     * @param pSubstitution ItemSubstitutionDefData object
     * @exception RemoteException
     */
    public ItemSubstitutionDefData saveAccountItemSubstiutions
            (ItemSubstitutionDefData pSubstitution, String pUser)
            throws RemoteException {
        Connection conn = null;
        ItemSubstitutionDefData substitution = null;
        try {
            String newStatus = pSubstitution.getSubstStatusCd();
            BigDecimal newConvertFactor = pSubstitution.getUomConversionFactor();
            if(newStatus==null) {
                String errorMessage = "Item substitution status can't be null";
                throw new Exception(errorMessage);
            }
            if(newConvertFactor==null) {
                String errorMessage = "Item substitution convertion factor can't be null";
                throw new Exception(errorMessage);
            }

            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            int accountId = pSubstitution.getBusEntityId();
            int itemId = pSubstitution.getItemId();
            int substItemId = pSubstitution.getSubstItemId();
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.BUS_ENTITY_ID,accountId);
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.ITEM_ID,itemId);
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_ITEM_ID,substItemId);
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.SUBST_TYPE_CD,
                    RefCodeNames.SUBST_TYPE_CD.ITEM_ACCOUNT);
            ItemSubstitutionDefDataVector substitutions = ItemSubstitutionDefDataAccess.select(conn,dbc);
            int size = substitutions.size();
            if(size==0) {
                pSubstitution.setAddBy(pUser);
                pSubstitution.setModBy(pUser);
                substitution = ItemSubstitutionDefDataAccess.insert(conn,pSubstitution);
            } else if(size>=1) {
                substitution = (ItemSubstitutionDefData) substitutions.get(0);
                if(!newStatus.equals(substitution.getSubstStatusCd()) ||
                        !newConvertFactor.equals(substitution.getUomConversionFactor())) {
                }
                substitution.setSubstStatusCd(newStatus);
                substitution.setUomConversionFactor(newConvertFactor);
                substitution.setModBy(pUser);
                ItemSubstitutionDefDataAccess.update(conn,substitution);
                if(size>1) {
                    dbc.addNotEqualTo(ItemSubstitutionDefDataAccess.ITEM_SUBSTITUTION_DEF_ID,
                            substitution.getItemSubstitutionDefId());
                    ItemSubstitutionDefDataAccess.remove(conn,dbc);

                }
            }
        }catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("AccountBean.saveAccountItemSubstiutions. Exception. "+exc.getMessage());
        } finally {
            closeConnection(conn);
        }
        return substitution;
    }

    /**
     * Removes account item substitutions.
     * @param pSubstitutionIds ItemSubstitutionDef identifiers
     * @retorun number of removed records
     * @exception RemoteException
     */
    public int removeAccountItemSubstiutions(IdVector pSubstitutionIds)
    throws RemoteException {
        Connection conn = null;
        int removedNumber = 0;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(ItemSubstitutionDefDataAccess.ITEM_SUBSTITUTION_DEF_ID,pSubstitutionIds);
            removedNumber = ItemSubstitutionDefDataAccess.remove(conn,dbc);
        }catch (Exception exc) {
            logError(exc.getMessage());
            exc.printStackTrace();
            throw new RemoteException("AccountBean.removeAccountItemSubstiutions. Exception. "+exc.getMessage());
        } finally {
            closeConnection(conn);
        }
        return removedNumber;
    }

    /**
     * Gets substitutions for the account.
     * @param pItemIds  the vector of item identifiers.
     * @param pAccountId the account identifier.
     * @param pIncludeNull the flag, which indicates to include items with no substitutions
     * @return vector of AccountItemSubstView objects
     * @throws            RemoteException Required by EJB 1.0
     */
    public AccountItemSubstViewVector getAccountItemSubstitutions(IdVector pItemIds, int pAccountId, boolean pIncludeNull)
    throws RemoteException {
        Connection conn = null;
        AccountItemSubstViewVector substitutions = new AccountItemSubstViewVector();
        try {
            DBCriteria dbc;
            conn = getConnection();
            //Substitutions
            dbc = new DBCriteria();
            dbc.addOneOf(ItemSubstitutionDefDataAccess.ITEM_ID, pItemIds);
            dbc.addEqualTo(ItemSubstitutionDefDataAccess.BUS_ENTITY_ID,pAccountId);
            String itemSubstReq = ItemSubstitutionDefDataAccess.getSqlSelectIdOnly(ItemSubstitutionDefDataAccess.ITEM_ID,dbc);
            String substItemReq = ItemSubstitutionDefDataAccess.getSqlSelectIdOnly(ItemSubstitutionDefDataAccess.SUBST_ITEM_ID,dbc);
            dbc.addOrderBy(ItemSubstitutionDefDataAccess.ITEM_ID);
            ItemSubstitutionDefDataVector accountItemSubstDV = ItemSubstitutionDefDataAccess.select(conn,dbc);
            //Substitute items
            dbc = new DBCriteria();
            dbc.addOneOf(ItemDataAccess.ITEM_ID,substItemReq);
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);
            ItemDataVector substItemDV = ItemDataAccess.select(conn,dbc);
            //Substitute uom,size,pack
            dbc = new DBCriteria();
            dbc.addOneOf(ItemMetaDataAccess.ITEM_ID,substItemReq);
            LinkedList prop = new LinkedList();
            prop.add("UOM");
            prop.add("SIZE");
            prop.add("PACK");
            dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE,prop);
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);
            ItemMetaDataVector substItemUomDV = ItemMetaDataAccess.select(conn,dbc);
            //Substitution Item manufacturer
            dbc = new DBCriteria();
            dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, substItemReq);
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                    RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
            ItemMappingDataVector substItemMapDV = ItemMappingDataAccess.select(conn,dbc);
            IdVector mfgIdV = new IdVector();
            for(int ii=0; ii<substItemMapDV.size(); ii++) {
                ItemMappingData imD = (ItemMappingData) substItemMapDV.get(ii);
                int mfgId = imD.getBusEntityId();
                Integer mfgIdI = new Integer(mfgId);
                if(!mfgIdV.contains(mfgIdI)){
                    mfgIdV.add(mfgIdI);
                }
            }
            //Items to substitute
            dbc = new DBCriteria();
            if(pIncludeNull) {//select all requested items
                dbc.addOneOf(ItemDataAccess.ITEM_ID, pItemIds);
            } else { //select only items with substitutions
                dbc.addOneOf(ItemDataAccess.ITEM_ID, itemSubstReq);
            }
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);
            ItemDataVector itemDV = ItemDataAccess.select(conn,dbc);
            //Item uom, size, pack
            dbc = new DBCriteria();
            if(pIncludeNull) {//select all requested items
                dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, pItemIds);
            } else { //select only items with substitutions
                dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, itemSubstReq);
            }
            prop = new LinkedList();
            prop.add("UOM");
            prop.add("SIZE");
            prop.add("PACK");
            dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE,prop);
            dbc.addOrderBy(ItemDataAccess.ITEM_ID);
            ItemMetaDataVector itemUomDV = ItemMetaDataAccess.select(conn,dbc);
            //Item manufacturer
            dbc = new DBCriteria();
            if(pIncludeNull) {//select all requested items
                dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, pItemIds);
            } else { //select only items with substitutions
                dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, itemSubstReq);
            }
            dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                    RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
            dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
            ItemMappingDataVector itemMapDV = ItemMappingDataAccess.select(conn,dbc);
            for(int ii=0; ii<itemMapDV.size(); ii++) {
                ItemMappingData imD = (ItemMappingData) itemMapDV.get(ii);
                int mfgId = imD.getBusEntityId();
                Integer mfgIdI = new Integer(mfgId);
                if(!mfgIdV.contains(mfgIdI)){
                    mfgIdV.add(mfgIdI);
                }
            }
            //Manufacturers
            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,mfgIdV);
            BusEntityDataVector mfgBusEntDV = BusEntityDataAccess.select(conn,dbc);
            Object[] mfgBusEntA = mfgBusEntDV.toArray();

            //Combine altogether
            for(int ii=0,jj=0,uu=0,mm=0; ii<itemDV.size(); ii++) {
                AccountItemSubstView itemSubstView = null;
                AccountItemSubstView itemSubstViewWrk = AccountItemSubstView.createValue();
                itemSubstViewWrk.setAccountId(pAccountId);
                ItemData iD = (ItemData) itemDV.get(ii);
                int itemId = iD.getItemId();
                itemSubstViewWrk.setItemId(itemId);
                itemSubstViewWrk.setItemDesc(iD.getShortDesc());
                itemSubstViewWrk.setItemSku(iD.getSkuNum());
                while(uu<itemUomDV.size()) {
                    ItemMetaData imD = (ItemMetaData) itemUomDV.get(uu);
                    if(imD.getItemId()==itemId) {
                        uu++;
                        if("UOM".equals(imD.getNameValue())) {
                            itemSubstViewWrk.setItemUom(imD.getValue());
                        } else if("SIZE".equals(imD.getNameValue())) {
                            itemSubstViewWrk.setItemSize(imD.getValue());
                        } else if("PACK".equals(imD.getNameValue())) {
                            itemSubstViewWrk.setItemPack(imD.getValue());
                        }
                        continue;
                    }
                    if(imD.getItemId()>itemId) {
                        break;
                    }
                }
                while(mm<itemMapDV.size()) {
                    ItemMappingData imD = (ItemMappingData) itemMapDV.get(mm);
                    if(imD.getItemId()==itemId) {
                        int mfgId = imD.getBusEntityId();
                        itemSubstViewWrk.setItemMfgId(mfgId);
                        itemSubstViewWrk.setItemMfgSku(imD.getItemNum());
                        for(int mm1=0; mm1<mfgBusEntA.length; mm1++) {
                            BusEntityData beD = (BusEntityData) mfgBusEntA[mm1];
                            if(beD.getBusEntityId()==mfgId) {
                                itemSubstViewWrk.setItemMfgName(beD.getShortDesc());
                                break;
                            }
                        }
                        mm++;
                        break;
                    }
                    if(imD.getItemId()>itemId) {
                        break;
                    }
                }
                boolean flag = true;
                for(;jj<accountItemSubstDV.size();jj++){
                    ItemSubstitutionDefData cisD = (ItemSubstitutionDefData) accountItemSubstDV.get(jj);
                    int itemId1 = cisD.getItemId();
                    if(itemId1>itemId) {
                        break;
                    }
                    if(itemId1<itemId) {
                        throw new RemoteException("Error. AccountBean.getSubstitutions(). Programm logic error");
                    }
                    if(itemId1==itemId) {
                        flag = false;
                        itemSubstView = AccountItemSubstView.createValue();
                        itemSubstView.setItemSubstitutionDefId(cisD.getItemSubstitutionDefId());
                        itemSubstView.setAccountId(itemSubstViewWrk.getAccountId());
                        itemSubstView.setItemId(itemSubstViewWrk.getItemId());
                        itemSubstView.setItemDesc(itemSubstViewWrk.getItemDesc());
                        itemSubstView.setItemSku(itemSubstViewWrk.getItemSku());
                        itemSubstView.setItemUom(itemSubstViewWrk.getItemUom());
                        itemSubstView.setItemSize(itemSubstViewWrk.getItemSize());
                        itemSubstView.setItemPack(itemSubstViewWrk.getItemPack());
                        itemSubstView.setItemMfgId(itemSubstViewWrk.getItemMfgId());
                        itemSubstView.setItemMfgSku(itemSubstViewWrk.getItemMfgSku());
                        itemSubstView.setItemMfgName(itemSubstViewWrk.getItemMfgName());
                        itemSubstView.setSubstStatusCd(cisD.getSubstStatusCd());
                        itemSubstView.setUomConversionFactor(cisD.getUomConversionFactor());
                        int substItemId = cisD.getSubstItemId();
                        int substItemId1 = 0;
                        int kk = 0;
                        for(; kk<substItemDV.size(); kk++){
                            ItemData siD = (ItemData) substItemDV.get(kk);
                            substItemId1 = siD.getItemId();
                            if(substItemId1>substItemId) {
                                break;
                            }
                            if(substItemId1==substItemId) {
                                itemSubstView.setSubstItemId(substItemId);
                                itemSubstView.setSubstItemDesc(siD.getShortDesc());
                                itemSubstView.setSubstItemSku(siD.getSkuNum());
                                for(int xx=0; xx<substItemUomDV.size(); xx++) {
                                    ItemMetaData imD = (ItemMetaData) substItemUomDV.get(xx);
                                    if(imD.getItemId()==substItemId1) {
                                        if("UOM".equals(imD.getNameValue())) {
                                            itemSubstView.setSubstItemUom(imD.getValue());
                                        }else if("SIZE".equals(imD.getNameValue())) {
                                            itemSubstView.setSubstItemSize(imD.getValue());
                                        } else if("PACK".equals(imD.getNameValue())) {
                                            itemSubstView.setSubstItemPack(imD.getValue());
                                        }
                                        continue;
                                    }
                                    if(imD.getItemId()>substItemId1) {
                                        break;
                                    }
                                }
                                for(int ss=0; ss<substItemMapDV.size(); ss++) {
                                    ItemMappingData imD = (ItemMappingData) substItemMapDV.get(ss);
                                    if(imD.getItemId()==substItemId1) {
                                        int mfgId = imD.getBusEntityId();
                                        itemSubstView.setSubstItemMfgId(mfgId);
                                        itemSubstView.setSubstItemMfgSku(imD.getItemNum());
                                        for(int ss1=0; ss1<mfgBusEntA.length; ss1++) {
                                            BusEntityData beD = (BusEntityData) mfgBusEntA[ss1];
                                            if(beD.getBusEntityId()==mfgId) {
                                                itemSubstView.setSubstItemMfgName(beD.getShortDesc());
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if(kk==substItemDV.size() || substItemId1>substItemId) {
                            String errorMess = "Error. AcccountBean.getSubstitutions(). "+
                                    "AccountItemSubst object does not have a substitution item. ContactItemSubstId: "+
                                    cisD.getItemSubstitutionDefId();
                            throw new RemoteException(errorMess);
                        }
                        substitutions.add(itemSubstView);
                    }
                    if(itemId1>itemId && flag==true) {
                        substitutions.add(itemSubstViewWrk);
                        break;
                    }
                }
                if(flag==true) {
                    substitutions.add(itemSubstViewWrk);
                }
            }

        } catch (Exception exc) {
            String em = "Error. AccountBean.getAccountItemSubstitutions() Exception happened. "+exc.getMessage();
            exc.printStackTrace();
            logError(em);
            throw new RemoteException(em);
        } finally {
            closeConnection(conn);
        }
        return substitutions;
    }

    /**
     * Gets account all of the accounts for this trading partner that have a site that matches,
     * uses begings with name match first and exact match if multiple accounts found.  If sitename is null the site
     * name is not analyized.
     * @param pTrPartnerId the trading partner identifier
     * @param pSiteName the site name
     * @return AccountDataVector
     * @throws DataNotFoundException if the account could not be found
     * @throws RemoteException when error happened
     */
    public AccountDataVector getAccountsByTrParnterIdAndSiteName(int pTrPartnerId, String pSiteName)
    throws RemoteException, DataNotFoundException, MultipleDataException {
        Connection conn = null;
        AccountItemSubstViewVector substitutions = new AccountItemSubstViewVector();
        try {



            DBCriteria dbc;
            conn = getConnection();

            TradingPartnerData tpd = TradingPartnerDataAccess.select(conn, pTrPartnerId);

            dbc = new DBCriteria();
            dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ID,pTrPartnerId);
            if(tpd.getTradingPartnerTypeCd().equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE)){
            	dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, RefCodeNames.TRADING_PARTNER_ASSOC_CD.STORE);
            }else{
            	dbc.addEqualTo(TradingPartnerAssocDataAccess.TRADING_PARTNER_ASSOC_CD, RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
            }

            IdVector busEntityIds = TradingPartnerAssocDataAccess.selectIdOnly(conn, TradingPartnerAssocDataAccess.BUS_ENTITY_ID, dbc);



            if(tpd.getTradingPartnerTypeCd().equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE)){

            	if(pSiteName!= null){
            		pSiteName = pSiteName.trim();
            	}
            	AccountDataVector acdv = new AccountDataVector();

            	SiteBean sb= new SiteBean();
            	SiteViewVector svv = new SiteViewVector();
            	//loop through busEntityIds
            	for(int i=0; i<busEntityIds.size(); i++){
            		int thisStoreId = ((Integer)busEntityIds.get(i)).intValue();
            		svv = sb.getSiteByNameDeprecated(pSiteName, 0, thisStoreId, false, Site.BEGINS_WITH, Site.ORDER_BY_ID);
            	}

            	Iterator it = svv.iterator();
                while(it.hasNext()){

                	SiteView sv = (SiteView)it.next();
                    int accountId = sv.getAccountId();
                    AccountData acd = getAccount(accountId, 0);
                    acdv.add(acd);
                }
                return acdv;
            }

            if(pSiteName!=null) {
                dbc = new DBCriteria();
                dbc.addBeginsWithIgnoreCase(BusEntityDataAccess.SHORT_DESC, pSiteName);
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
                dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                        RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                String siteReq = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbc);


                dbc = new DBCriteria();
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, busEntityIds);
                dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteReq);
                dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                busEntityIds = BusEntityAssocDataAccess.selectIdOnly(conn,BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
                if(busEntityIds.size()>1) {
                    //Try Exact Matching
                    dbc = new DBCriteria();
                    dbc.addEqualToIgnoreCase(BusEntityDataAccess.SHORT_DESC, pSiteName);
                    dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                            RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
                    dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                            RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
                    siteReq = BusEntityDataAccess.getSqlSelectIdOnly(BusEntityDataAccess.BUS_ENTITY_ID, dbc);


                    dbc = new DBCriteria();
                    dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, busEntityIds);
                    dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteReq);
                    dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

                    IdVector busEntityIdsEM = BusEntityAssocDataAccess.selectIdOnly(conn,BusEntityAssocDataAccess.BUS_ENTITY2_ID, dbc);
                    if(busEntityIdsEM.size() >= 1){
                        busEntityIds = busEntityIdsEM;
                    }
                }
            }
            if(busEntityIds.size()==0) {
                String mess = "No account found for trading partner id = "+pTrPartnerId+" and site name = "+pSiteName;
                throw new DataNotFoundException(mess);
            }

            AccountDataVector acdv = new AccountDataVector();
            Iterator it = busEntityIds.iterator();
            while(it.hasNext()){
                Integer accountId = (Integer) it.next();
                AccountData acd = getAccount(accountId.intValue(), 0);
                acdv.add(acd);
            }
            return acdv;
        }catch (Exception exc) {
            if(exc instanceof DataNotFoundException) {
                throw (DataNotFoundException)exc;
            }else{
                throw processException(exc);
            }
        }finally {
            closeConnection(conn);
        }
    }


    public FiscalPeriodView getFiscalInfo(int pAccountId)
    throws RemoteException, DataNotFoundException {
        BusEntityDAO bdao = new BusEntityDAO();
        Connection con = null;
        try {
            con = getConnection();
            return bdao.getFiscalInfo(con, pAccountId);
        } catch (Exception e) {
            e.printStackTrace();
            logError("getFiscalCalender, failed for pAccountId=" +
                    pAccountId );
        } finally {
            closeConnection(con);
        }
        return null;
    }

    public FiscalCalenderView updateFiscalCal(FiscalCalenderView pCal) throws RemoteException {

        BusEntityDAO bdao = new BusEntityDAO();
        Connection con = null;
        try {
            con = getConnection();
            FiscalCalenderView fcv = bdao.updateFiscalCal(con, pCal);
            logDebug("updateFiscalCal fcv=" + fcv);
            return fcv;

        } catch (Exception e) {
            e.printStackTrace();
            logError("updateFiscalCal pCal=" + pCal + " failed");
        } finally {
            closeConnection(con);
        }
        return null;

    }

    public FiscalCalenderData updateFiscalCalender(FiscalCalenderData pCal)
            throws RemoteException {
        Connection con = null;
        try {
            con = getConnection();
            FiscalCalenderData fcd = pCal;
            if (pCal.getFiscalCalenderId() > 0) {
                FiscalCalenderDataAccess.update(con, pCal);
            } else {
                fcd = FiscalCalenderDataAccess.insert(con, pCal);
            }
            logDebug("updateFiscalCal fcd=" + fcd);
            return fcd;

        } catch (Exception e) {
            e.printStackTrace();
            logError("updateFiscalCal pCal=" + pCal + " failed");
        } finally {
            closeConnection(con);
        }
        return null;
    }


    public FiscalCalenderViewVector getFiscalCalCollection(int pBusEntityId) throws RemoteException {

        BusEntityDAO bdao = new BusEntityDAO();
        Connection con = null;
        try {
            con = getConnection();
            FiscalCalenderViewVector fcvv = bdao.getFiscalCalenders(con, pBusEntityId);
            logDebug("getFiscalCalCollection pBusEntityId=" + pBusEntityId);
            return fcvv;

        } catch (Exception e) {
            e.printStackTrace();
            logError("ERROR getFiscalCalCollection pBusEntityId=" + pBusEntityId);
        } finally {
            closeConnection(con);
        }
        return null;

    }


    public FiscalPeriodView createDefaultFiscalInfo(int pAccountId)
    throws RemoteException, DataNotFoundException {
        BusEntityDAO bdao = new BusEntityDAO();
        Connection con = null;
        try {
            con = getConnection();
            FiscalPeriodView fpv = bdao.getFiscalInfo(con, pAccountId);
            if ( null == fpv ) {
                fpv =  bdao.createFiscalInfo(con, pAccountId);
                logDebug( "createDefaultFiscalInfo fpv=" + fpv);
            }
            return fpv;

        } catch (Exception e) {
            e.printStackTrace();
            logError("createDefaultFiscalInfo, failed for pAccountId=" +
                    pAccountId );
        } finally {
            closeConnection(con);
        }
        return null;
    }


    public ArrayList getInventoryItems(int pAccountId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        try {
            DBCriteria dbc;
            con = getConnection();
            dbc = new DBCriteria();
            dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID,
                    pAccountId);
            dbc.addEqualTo(InventoryItemsDataAccess.STATUS_CD,
                    RefCodeNames.ITEM_STATUS_CD.ACTIVE);
            InventoryItemsDataVector iv =
                    InventoryItemsDataAccess.select(con,dbc);
            IdVector ids = new IdVector();
            ArrayList accountInventoryItems = new ArrayList();

            for (int i = 0; iv != null && i < iv.size(); i++ ) {
                InventoryItemsData iData = (InventoryItemsData)iv.get(i);
                ids.add(new Integer(iData.getItemId()));
                InventoryItemDataJoin iidj = new InventoryItemDataJoin(iData);
                accountInventoryItems.add(iidj);
            }
            ProductDAO pdao = new ProductDAO(con,ids);
            ProductDataVector pdv = pdao.getResultVector();
            for (int i = 0; pdv != null && i < pdv.size(); i++ ) {
                ProductData pData = (ProductData)pdv.get(i);
                for ( int j = 0; j < accountInventoryItems.size(); j++){
                    InventoryItemDataJoin iidj = (InventoryItemDataJoin)accountInventoryItems.get(j);
                    if (iidj.setProductData(pData)) {
                        break;
                    }
                }
            }
            return accountInventoryItems;
        } catch (Exception e) {
            e.printStackTrace();
            logError("getInventoryItems, failed for pAccountId=" +
                    pAccountId );
        } finally {
            closeConnection(con);
        }
        return null;
    }

    public ArrayList getInventoryItemsAvailable(int pAccountId)
    throws RemoteException, DataNotFoundException {
        Connection con = null;
        try {
            ArrayList accountInventoryItems = getInventoryItems(pAccountId);

            // Find all items in the account catalog
            DBCriteria dbc;
            con = getConnection();
            dbc = new DBCriteria();
            dbc.addCondition(CatalogDataAccess.CATALOG_ID +
                    " in ( " + sqlForAccountCatalog(pAccountId) + " ) " );

            IdVector accountCatalog = CatalogDataAccess.selectIdOnly(con,dbc);
            // Get all the items.
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,
                    accountCatalog);
            IdVector itemIds = CatalogStructureDataAccess.selectIdOnly
                    (con,CatalogStructureDataAccess.ITEM_ID,dbc);

            ProductDAO pdao = new ProductDAO(con,itemIds);
            ProductDataVector pdv = pdao.getResultVector();
            for (int i = 0; pdv != null && i < pdv.size(); i++ ) {
                ProductData pData = (ProductData)pdv.get(i);
                boolean foundItem = false;
                for ( int j = 0; j < accountInventoryItems.size(); j++){
                    InventoryItemDataJoin iidj = (InventoryItemDataJoin)accountInventoryItems.get(j);
                    if (iidj.setProductData(pData)) {
                        foundItem = true;
                        break;
                    }
                }
                if (foundItem == false && pData.getSkuNum() > 0 ) {
                    // This item is not currently an inventory item.
                    // Add it as unavailable.
                    InventoryItemsData iData = InventoryItemsData.createValue();
                    iData.setItemId(pData.getProductId());
                    iData.setStatusCd(RefCodeNames.ITEM_STATUS_CD.INACTIVE);
                    InventoryItemDataJoin iidj = new InventoryItemDataJoin(iData);
                    iidj.setProductData(pData);
                    accountInventoryItems.add(iidj);
                }
            }
            return accountInventoryItems;
        } catch (Exception e) {
            e.printStackTrace();
            logError("getInventoryItemsAvailable, failed for pAccountId=" +
                    pAccountId );
        } finally {
            closeConnection(con);
        }
        return null;
    }

    public void addInventoryItems(int pAccountId, String [] pItemIds, String pUser)
    throws RemoteException, DataNotFoundException {
        String [] itemsarr = pItemIds;
        Connection con = null;
        try {
            con = getConnection();
            for ( int i = 0; i < itemsarr.length; i++){
                InventoryItemsData iid = InventoryItemsData.createValue();
                iid.setBusEntityId(pAccountId);
                iid.setItemId(Integer.parseInt(itemsarr[i]));
                iid.setStatusCd(RefCodeNames.ITEM_STATUS_CD.ACTIVE);
                iid.setAddBy(pUser);
                iid.setModBy(pUser);
                // The auto order setting is defaulted to no.
                iid.setEnableAutoOrder("N");
                InventoryItemsDataAccess.insert(con, iid);
            }
        } catch (Exception e) {
            processException(e);
        } finally {
            closeConnection(con);
        }

    }

    public void updateInventoryItems(int pAccountId, String [] pItemIds,
            String pUser, String pReqAction)
            throws RemoteException, DataNotFoundException {
        String [] itemsarr = pItemIds;
        Connection con = null;
        try {
            con = getConnection();
            for ( int i = 0; i < itemsarr.length; i++){
                DBCriteria dbc;
                dbc = new DBCriteria();
                dbc.addEqualTo(InventoryItemsDataAccess.BUS_ENTITY_ID, pAccountId);
                dbc.addEqualTo(InventoryItemsDataAccess.ITEM_ID, itemsarr[i]);
                if ( pReqAction.equals("remove") ) {
                    InventoryItemsDataAccess.remove(con, dbc);
                } else if ( pReqAction.equals("enable-auto-order") ||
                        pReqAction.equals("disable-auto-order") ) {
                    InventoryItemsDataVector v =
                            InventoryItemsDataAccess.select(con, dbc);
                    for ( int i2 = 0; i2 < v.size(); i2++ ){
                        InventoryItemsData iid = (InventoryItemsData)v.get(i2);
                        if ( pReqAction.equals("enable-auto-order")) {
                            iid.setEnableAutoOrder("Y");
                        } else {
                            iid.setEnableAutoOrder("N");
                        }
                        iid.setModBy(pUser);
                        logDebug("updatde iid=" + iid);
                        InventoryItemsDataAccess.update(con,iid);
                    }
                }

            }
        } catch (Exception e) {
            processException(e);
        } finally {
            closeConnection(con);
        }
    }

    private String checkVal(String t) {
        if ( null == t ) { return ""; }
        return t.trim();
    }

    public BillToData lookupBillTo(AccountData pAcctData,
            OrderAddressData pCustBillToAddr)
            throws RemoteException {

        logDebug(" lookupBillTo, pAcctData=" + pAcctData
                + " pCustBillToAddr=" + pCustBillToAddr );

        // try to match this order billing address to a known
        // billing address for this account.
        String sql2 = "select max(ba.bus_entity1_id) from "
                + " clw_bus_entity_assoc ba, clw_address ad "
                + " where bus_entity2_id = ? "
                + " and bus_entity_assoc_cd = '"
                + RefCodeNames.BUS_ENTITY_ASSOC_CD.BILLTO_FOR_ACCOUNT + "' "
                + " and ad.bus_entity_id = bus_entity1_id ";

        String addr1 = checkVal(pCustBillToAddr.getAddress1());
        if ( addr1.length() > 0 ) {
            sql2 += " and upper(ad.address1) like upper(?) ";
        }

        String addr2 = checkVal(pCustBillToAddr.getAddress2());
        if ( addr2.length() > 0 ) {
            sql2 += " and upper(ad.address2) like upper(?) ";
        }

        String addr3 = checkVal(pCustBillToAddr.getAddress3());
        if ( addr3.length() > 0 ) {
            sql2 += " and upper(ad.address3) like upper(?) ";
        }

        String addr4 = checkVal(pCustBillToAddr.getAddress4());
        if ( addr4.length() > 0 ) {
            sql2 += " and upper(ad.address4) like upper(?) ";
        }

        String city = checkVal(pCustBillToAddr.getCity());
        if ( city.length() > 0 ) {
            sql2 += " and upper(ad.city) = upper(?) ";
        }

        String state = checkVal(pCustBillToAddr.getStateProvinceCd());
        if ( state.length() > 0 ) {
            sql2 += " and upper(ad.state_province_cd) = "
                    + " upper(?) ";
        }

        String country = checkVal(pCustBillToAddr.getCountryCd());
        if ( country.length() > 0 ) {
            sql2 += " and upper(ad.country_cd) = upper(?) ";
        }

        String postalCode = checkVal(pCustBillToAddr.getPostalCode());
        if ( postalCode.length() > 0 ) {
            sql2 += " and upper(ad.postal_code) like "
                    + " upper(?) ";
        }

        int billtoid = 0;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql2);
            int i=1;
            stmt.setInt(i++, pAcctData.getBusEntity().getBusEntityId());
            if ( addr1.length() > 0 ) {
                stmt.setString(i++, addr1 + "%");
            }
            if ( addr2.length() > 0 ) {
                stmt.setString(i++, addr2 + "%");
            }
            if ( addr3.length() > 0 ) {
                stmt.setString(i++, addr3 + "%");
            }
            if ( addr4.length() > 0 ) {
                stmt.setString(i++, addr4 + "%");
            }
            if ( city.length() > 0 ) {
                stmt.setString(i++, city);
            }
            if ( state.length() > 0 ) {
                stmt.setString(i++, state);
            }
            if ( country.length() > 0 ) {
                stmt.setString(i++, country);
            }
            if ( postalCode.length() > 0 ) {
                stmt.setString(i++, postalCode + "%");
            }

            logDebug("SQL 122: " + sql2);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                billtoid = rs.getInt(1);
            }

            rs.close();
            stmt.close();

            if ( billtoid <= 0 ) {
                logDebug("no bill to found for pCustBillToAddr="
                        + pCustBillToAddr);
                return null;
            }

            logDebug("DONE lookupBillTo, pAcctData=" + pAcctData
                    + " pCustBillToAddr=" + pCustBillToAddr );

            return lookupBillTo(conn, pAcctData.getBusEntity(), billtoid );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection(conn);
        }
    }

    private BillToData lookupBillTo(Connection conn,
            BusEntityData acctData,
            int pBillToId)
            throws Exception {

        int thisBillToid = pBillToId;
        BusEntityData bed = BusEntityDataAccess.select(conn, thisBillToid);
        DBCriteria addrCrit = new DBCriteria();
        addrCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID,thisBillToid);
        addrCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                RefCodeNames.ADDRESS_TYPE_CD.ACCOUNT_BILLTO);

        AddressDataVector adv = AddressDataAccess.select(conn, addrCrit);
        AddressData ad ;
        if ( null != adv && adv.size() > 0) {
            ad = (AddressData)adv.get(0);
        } else {
            ad = AddressData.createValue();
        }
        return new BillToData(bed, acctData, ad);
    }

    public ArrayList getAccountBillTos(Connection conn, int pAccountId)
    throws Exception {

        BillToVector billtos = new BillToVector();

	DBCriteria assocCrit = new DBCriteria();
	assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,pAccountId);
	assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
			     RefCodeNames.BUS_ENTITY_ASSOC_CD.BILLTO_FOR_ACCOUNT);
	IdVector billtoIds = BusEntityAssocDataAccess.selectIdOnly(
								   conn, BusEntityAssocDataAccess.BUS_ENTITY1_ID, assocCrit);

	BusEntityData acctData = BusEntityDataAccess.select(conn, pAccountId);
	for ( int i = 0; billtoIds != null && i < billtoIds.size(); i++ ) {
	    int thisBillToid = ((Integer)billtoIds.get(i)).intValue();
	    billtos.add(lookupBillTo(conn, acctData, thisBillToid));

	}

        billtos.sort("Name");
        return billtos;
    }

    public ArrayList getAccountBillTos(int pAccountId)
    throws RemoteException, DataNotFoundException {

        Connection conn = null;
        try{
            conn = getConnection();
	    return getAccountBillTos(conn,pAccountId);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public void addBillTo(BillToData pBillToData)
    throws RemoteException, DataNotFoundException {
        Connection conn = null;
        try{
            int accountId = pBillToData.getAccountBusEntity().getBusEntityId(),
                    thisBillToId = pBillToData.getBillToId();
            String moduser = pBillToData.getBusEntity().getModBy();
            conn = getConnection();

            if ( thisBillToId > 0 ) {
                BusEntityDataAccess.update(conn, pBillToData.getBusEntity());
            } else {
                pBillToData.getBusEntity().setAddBy(moduser);
                BusEntityData be =
                        BusEntityDataAccess.insert(conn, pBillToData.getBusEntity());
                thisBillToId = be.getBusEntityId();
            }

            pBillToData.getBillToAddress().setBusEntityId(thisBillToId);
            if ( pBillToData.getBillToAddress().getAddressId() > 0 ) {
                pBillToData.getBillToAddress().setModBy(moduser);
                AddressDataAccess.update(conn, pBillToData.getBillToAddress());
            } else {
                AddressData billtoAddr = pBillToData.getBillToAddress();
                billtoAddr.setAddBy(moduser);
                billtoAddr.setModBy(moduser);
                AddressDataAccess.insert(conn, billtoAddr);
            }

            DBCriteria assocCrit = new DBCriteria();
            assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,accountId);
            assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,thisBillToId);
            assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.BILLTO_FOR_ACCOUNT);
            IdVector billtoIds = BusEntityAssocDataAccess.selectIdOnly(
                    conn, BusEntityAssocDataAccess.BUS_ENTITY1_ID, assocCrit);

            if ( billtoIds == null || billtoIds.size() == 0 ) {
                BusEntityAssocData billtoassoc = BusEntityAssocData.createValue();
                billtoassoc.setBusEntity1Id(thisBillToId);
                billtoassoc.setBusEntity2Id(accountId);
                billtoassoc.setBusEntityAssocCd
                        (RefCodeNames.BUS_ENTITY_ASSOC_CD.BILLTO_FOR_ACCOUNT);
                billtoassoc.setAddBy(moduser);
                billtoassoc.setModBy(moduser);
                BusEntityAssocDataAccess.insert(conn, billtoassoc);
            }

            String billtoErpNumber = pBillToData.getBusEntity().getErpNum();
            if(billtoErpNumber==null || billtoErpNumber.trim().length()==0) {
                logDebug("no erp number defined for billto=" + pBillToData );
                return;
            }

            String acctErpNum = pBillToData.getAccountBusEntity().getErpNum();
            if(acctErpNum==null || acctErpNum.trim().length()==0) {
                logDebug("no account erp number defined for billto=" + pBillToData );
                return;
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }

    public BillToData getBillToDetail(int pBillToId)
    throws RemoteException, DataNotFoundException {
        Connection conn = null;
        BillToData billToData = null;
        try {
            conn = getConnection();
            DBCriteria assocCrit = new DBCriteria();
            assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                    pBillToId);
            assocCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.BILLTO_FOR_ACCOUNT);
            IdVector acctIds = BusEntityAssocDataAccess.selectIdOnly(
                    conn, BusEntityAssocDataAccess.BUS_ENTITY2_ID, assocCrit);
            int accountId = ((Integer)acctIds.get(0)).intValue();

            BusEntityData acctData = BusEntityDataAccess.select(conn, accountId);
            billToData = lookupBillTo(conn, acctData, pBillToId);

            // Get Lawson data for this ship to.
            String billtoErpNumber = billToData.getBusEntity().getErpNum();
            if(billtoErpNumber==null || billtoErpNumber.trim().length()==0) {
                logDebug("no erp number defined for billto=" + billToData );
                return billToData;
            }
        } catch (Exception e) {
            processException(e);
        }finally{
            closeConnection(conn);
        }

        return billToData;
    }



    /**
     *fetches the accounts identified by the supplied account ids
     *@param pAccountIds pAccount ids
     *@returns the populated AccountDataVector
     *@throws DataNotFoundException if ANY of the accounts in the id vector are not found
     *@exception  RemoteException  if an error occurs
     */
    public AccountDataVector getAccountCollection(IdVector pAccountIds) throws DataNotFoundException, RemoteException{
        AccountDataVector dv = new AccountDataVector();
        Connection conn = null;
        try {
            conn = getConnection();
            Iterator iter = pAccountIds.iterator();
            while (iter.hasNext()) {
                Integer id = (Integer) iter.next();
                BusEntityData bed = BusEntityDataAccess.select(conn, id.intValue());
                dv.add(getAccountDetails(bed));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return dv;
    }

    /**
     *fetches the accounts identified by the supplied account ids
     *@param pAccountIds pAccount ids
     *@returns the populated AccountDataVector
     *@throws DataNotFoundException if ANY of the accounts in the id vector are not found
     *@exception  RemoteException  if an error occurs
     */
    public AccountSearchResultViewVector getAccountsAsSrchResultVector(IdVector pAccountIds)
    										throws DataNotFoundException, RemoteException{
    	AccountSearchResultViewVector dv = new AccountSearchResultViewVector();
        Connection conn = null;
        try {
            conn = getConnection();
            Iterator iter = pAccountIds.iterator();
            while (iter.hasNext()) {
                Integer id = (Integer) iter.next();
                StringBuffer sql = new StringBuffer();
        	    sql.append("SELECT e.bus_entity_id," +
        	    		"e.erp_num," +
        	    		"e.short_desc," +
        	    		"a.city," +
        	    		"a.state_province_cd," +
        	    		"p.Clw_value," +
        	    		"e.bus_entity_status_cd," +
        	    		"a.address1," +
        	    		"a.postal_code " +
        	    		"FROM CLW_BUS_ENTITY e, CLW_PROPERTY p, CLW_ADDRESS a " +
        	    		"WHERE   e.BUS_ENTITY_ID IN = ?" +
        	    	  	"AND e.bus_entity_id =a.bus_entity_id " +
        	    		"AND e.BUS_ENTITY_ID = p.bus_entity_id " );
        	    sql.append("ORDER BY e.SHORT_DESC ASC");
                AccountSearchResultView accountResult = null;
                mCat.info("SQL: in getAccountsAsSrchResultVector" + sql.toString());
                PreparedStatement stmt = conn.prepareStatement(sql.toString());
                stmt.setInt(1,id);
                ResultSet rs=stmt.executeQuery();
                while (rs.next()) {
                    // build the object
                	accountResult=new AccountSearchResultView();

                	accountResult.setAccountId(rs.getInt(1));
    		    	accountResult.setErpNum(rs.getString(2));
    		    	accountResult.setShortDesc(rs.getString(3));
    		    	accountResult.setCity(rs.getString(4));
    		    	accountResult.setStateProvinceCd(rs.getString(5));
    		    	accountResult.setValue(rs.getString(6));
    		    	accountResult.setBusEntityStatusCd(rs.getString(7));
    		    	accountResult.setAddress1(rs.getString(8));
    		    	accountResult.setPostalCode(rs.getString(9));

    		    	 dv.add(accountResult);
                }
                rs.close();
                stmt.close();

            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return dv;
    }


    public ShoppingControlDataVector updateShoppingControls
            (ShoppingControlDataVector pShopCtrlv )
            throws RemoteException {

        if ( null == pShopCtrlv || pShopCtrlv.size() <=0 ) {
            logDebug( this.getClass().getName() + " , updateShoppingControls, pShopCtrlv="
                    + pShopCtrlv );
            return pShopCtrlv;
        }

        Connection conn = null;
        try {
            int thisacctid = 0;
            conn = getConnection();
            Iterator it = pShopCtrlv.iterator();
            while (it.hasNext()){
                ShoppingControlData scd = (ShoppingControlData)it.next();

                if ( scd.getAccountId() > 0 ) {
                    thisacctid = scd.getAccountId();
               // }else

                updateShoppingControlItem(conn, scd);
                }
            }

            if ( thisacctid <= 0 ) {

                return null;
            }
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, thisacctid);
            dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID, 0);
            return pShopCtrlv;
        } catch (Exception e) {
            e.printStackTrace();
            logDebug("updateShoppingControls, error " + e);
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public void updateShoppingControls
    (ShoppingControlDataVector pAccountShoppingCtrols, ShoppingControlDataVector pSiteShoppingCtrols)
    throws RemoteException {

    	Connection conn = null;
    	try {
    		int thisacctid = 0;
    		conn = getConnection();
    		Iterator it = pAccountShoppingCtrols.iterator();
    		while (it.hasNext()){
    			ShoppingControlData scd = (ShoppingControlData)it.next();
    			if (scd.getShoppingControlId() > 0){
    				updateShoppingControlItem(conn, scd);
    			}else{
    				ShoppingControlDataAccess.insert(conn, scd);
    			}
    		}
    		
    		it = pSiteShoppingCtrols.iterator();
    		while (it.hasNext()){
    			ShoppingControlData sdata = (ShoppingControlData)it.next();
    			// only update
    			if (sdata.getShoppingControlId() > 0){
	            		ShoppingControlDataAccess.update(conn, sdata);
            	}
    		}
    	} catch (Exception e) {
    		throw processException(e);
    	} finally {
    		closeConnection(conn);
    	}
    }


    private void updateShoppingControlItem
            (Connection pCon, ShoppingControlData pScd)
            throws Exception {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(ShoppingControlDataAccess.ACCOUNT_ID, pScd.getAccountId());
        if(pScd.getAccountId() > 0){
        	dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID, 0);
        }else{
        	dbc.addEqualTo(ShoppingControlDataAccess.SITE_ID, pScd.getSiteId());
        }
        dbc.addEqualTo(ShoppingControlDataAccess.ITEM_ID, pScd.getItemId());
        ShoppingControlDataVector scdv
                = ShoppingControlDataAccess.select(pCon, dbc);

        if ( null == scdv || scdv.size() == 0 ) {
            // Insert a new entry.
            pScd.setAddBy(pScd.getModBy());
            ShoppingControlDataAccess.insert(pCon, pScd);
            if(pScd.getAccountId() == 0){
            	return;//should be ok all the time, but in case data coruption (site account records without an account one) issue the update.
            }
        }


    	String updateSQL = "update "+ShoppingControlDataAccess.CLW_SHOPPING_CONTROL+
    		" set "+ShoppingControlDataAccess.MAX_ORDER_QTY+" = ?"+
    		", "+ShoppingControlDataAccess.RESTRICTION_DAYS+" = ?"+
    		", "+ShoppingControlDataAccess.CONTROL_STATUS_CD+" = ?"+
    		", "+ShoppingControlDataAccess.HISTORY_ORDER_QTY+" = -1 "+
    		", "+ShoppingControlDataAccess.ACTUAL_MAX_QTY+" = -1 "+
    		", "+ShoppingControlDataAccess.EXP_DATE+" = NULL "+
    		", "+ShoppingControlDataAccess.MOD_BY+" = ?"+
    		", "+ShoppingControlDataAccess.MOD_DATE+" = ? ";
    		if(pScd.getAccountId() == 0){
    			updateSQL+=", "+ShoppingControlDataAccess.ACCOUNT_ID+" = 0 ";
    		}
    		updateSQL+=" WHERE "+ShoppingControlDataAccess.ITEM_ID+"="+pScd.getItemId();
    		if(pScd.getAccountId() > 0){
    			updateSQL+=" AND "+ShoppingControlDataAccess.ACCOUNT_ID+"="+pScd.getAccountId();
    		}else{
    			updateSQL+=" AND "+ShoppingControlDataAccess.SITE_ID+"="+pScd.getSiteId();
    		}
    		PreparedStatement pstmt = pCon.prepareStatement(updateSQL);
    		pstmt.setInt(1, pScd.getMaxOrderQty());
    		pstmt.setInt(2, pScd.getRestrictionDays());
    		pstmt.setString(3,pScd.getControlStatusCd());
    		pstmt.setString(4,pScd.getModBy());
    		pstmt.setDate(5,new java.sql.Date(System.currentTimeMillis()));
    		int updated = pstmt.executeUpdate();
    		pstmt.close();

    }


    /**
     * Creates the cache tables for all accounts that have fiscal calendars.
     * @throws RemoteException if an error occurs
     */
    public void createFiscalCalCacheTablesForAll()
    throws RemoteException{
    	Connection con;
    	try{
    		con = getConnection();
    		long totalTimer = System.currentTimeMillis();
    		//delete *ALL* existing data from the flattened table
    		FiscalCalenderFlatDataAccess.remove(con,new DBCriteria());

    		//get all of the fiscal calendars
    		HashMap createdFlatRecords = new HashMap(); //used for performance
    		FiscalCalenderViewVector cals = getAllFiscalCalenders(con);


    		//DBCriteria crit = new DBCriteria();
    		//crit.addEqualTo(FiscalCalenderDataAccess.BUS_ENTITY_ID,101254);
    		//crit.addEqualTo(FiscalCalenderDataAccess.FISCAL_CALENDER_ID,101);
    		//FiscalCalenderDataVector cals = FiscalCalenderDataAccess.select(con,crit);

    		Iterator it = cals.iterator();
    		while(it.hasNext()){
    			FiscalCalendarInfo aCal =
    				new FiscalCalendarInfo((FiscalCalenderView) it.next());
    			int numP = aCal.getNumberOfBudgetPeriods();
    			StringBuffer key = new StringBuffer();
    			for(int i=0;i<numP;i++){
    				key.append(aCal.getBudgetPeriod(i+1));
    			}
    			FiscalCalenderFlatDataVector fcfdv;
    			if(createdFlatRecords.containsKey(key.toString())){
    				fcfdv = (FiscalCalenderFlatDataVector)createdFlatRecords.get(key.toString());
    			}else{
    				fcfdv = createFiscalCalendarFlattened(aCal);
    			}
    			if(fcfdv != null){
    				//reset all of the data to the correct fiscal calendar
    				//and account...may be using cached data from the hashmap
    				//then insert the record
    				Iterator it2 = fcfdv.iterator();
    				while(it2.hasNext()){
    					FiscalCalenderFlatData flat = (FiscalCalenderFlatData) it2.next();
    					flat.setBusEntityId(aCal.getFiscalCalenderView().getFiscalCalender().getBusEntityId());
    					flat.setFiscalCalenderId(aCal.getFiscalCalenderView().getFiscalCalender().getFiscalCalenderId());
    					flat.setFiscalYear(aCal.getFiscalCalenderView().getFiscalCalender().getFiscalYear());
    					if(flat.getFiscalYear() == 0){
    						flat.setFiscalYear(Calendar.getInstance().get(Calendar.YEAR));
    					}
    					FiscalCalenderFlatDataAccess.insert(con,flat);
    				}
    			}
    		}
    		closeConnection(con);
    	}catch(Exception e){
    		throw processException(e);
    	}
    }

    private FiscalCalenderViewVector getAllFiscalCalenders(Connection pCon) throws SQLException {

        FiscalCalenderViewVector fcvv = new FiscalCalenderViewVector();

        DBCriteria dbc = new DBCriteria();
        dbc.addOrderBy(FiscalCalenderDataAccess.BUS_ENTITY_ID);
        dbc.addOrderBy(FiscalCalenderDataAccess.EFF_DATE, false);
        dbc.addCondition("1=1");

        FiscalCalenderDataVector v = FiscalCalenderDataAccess.select(pCon, dbc);

        if (null != v && v.size() > 0) {

            for (int i = 0; i < v.size(); i++) {

                FiscalCalenderData fcd = (FiscalCalenderData) v.get(i);

                dbc = new DBCriteria();
                dbc.addEqualTo(FiscalCalenderDetailDataAccess.FISCAL_CALENDER_ID, fcd.getFiscalCalenderId());
                dbc.addOrderBy(FiscalCalenderDetailDataAccess.PERIOD);

                FiscalCalenderDetailDataVector details = FiscalCalenderDetailDataAccess.select(pCon, dbc);

                fcvv.add(new FiscalCalenderView(fcd, details));
            }
        }

        return fcvv;
    }

    /**
     * This might be better suited to be a method off of the FiscalCalendarInfo object,
     * but desire to keep the cache/flattened logic localized keeps it here for now.
     */
    private FiscalCalenderFlatDataVector createFiscalCalendarFlattened(FiscalCalendarInfo pFiscalCalendarInfo){
    	FiscalCalenderFlatDataVector dv = new FiscalCalenderFlatDataVector();
    	int numP = pFiscalCalendarInfo.getNumberOfBudgetPeriods();
    	for(int i=0;i<numP;i++){
			FiscalCalendarInfo.BudgetPeriodInfo bpi = pFiscalCalendarInfo.getBudgetPeriod(i+1);
			FiscalCalenderFlatData flat = FiscalCalenderFlatData.createValue();
			flat.setStartDate(bpi.getStartDate());
			flat.setEndDate(bpi.getEndDate());
			flat.setFiscalCalenderId(pFiscalCalendarInfo.getFiscalCalenderView().getFiscalCalender().getFiscalCalenderId());
			flat.setFiscalYear(pFiscalCalendarInfo.getFiscalCalenderView().getFiscalCalender().getFiscalYear());
			if(flat.getFiscalYear() == 0){
				flat.setFiscalYear(Calendar.getInstance().get(Calendar.YEAR));
			}
			flat.setPeriod(bpi.getBudgetPeriod());
			dv.add(flat);
		}
    	return dv;
    }

  public FiscalCalenderData getCurrentFiscalCalender(int accountId)
       throws RemoteException{
        Connection conn=null;
           try {
               conn = getConnection();
               BusEntityDAO bdao = new BusEntityDAO();
              return  bdao.getCurrentFiscalCalender(conn,accountId);
           }
           catch (Exception e) {
            e.printStackTrace();
            logDebug("getCurrentFiscalCalender, error " + e);
        } finally {
            closeConnection(conn);
        }
 return null;
}

    public FiscalCalenderView getCurrentFiscalCalenderV(int pAccountId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDAO bdao = new BusEntityDAO();
            return bdao.getCurrentFiscalCalenderV(conn, pAccountId);
        }
        catch (Exception e) {
            e.printStackTrace();
            logDebug("getCurrentFiscalCalenderV, error " + e);
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public FiscalCalenderData getFiscalCalender(int accountId, Date orderDate) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDAO bdao = new BusEntityDAO();
            return bdao.getFiscalCalender(conn, accountId, orderDate);
        }
        catch (Exception e) {
            e.printStackTrace();
            logDebug("getFiscalCalender, error " + e);
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public FiscalCalenderView getFiscalCalenderV(int pAccountId, Date pOrderDate) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            BusEntityDAO bdao = new BusEntityDAO();
            return bdao.getFiscalCalenderV(conn, pAccountId, pOrderDate);
        } catch (Exception e) {
            e.printStackTrace();
            logDebug("getFiscalCalenderV, error " + e);
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public int getAccountIdByOrderId(int orderId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbCriteria = new DBCriteria();
            dbCriteria.addEqualTo(OrderDataAccess.ORDER_ID, orderId);
            IdVector ids = OrderDataAccess.selectIdOnly(conn, OrderDataAccess.ACCOUNT_ID, dbCriteria);
            if (ids.size() > 1) {
                throw new Exception("Multiple account_id for order_id : " + orderId);
            } else if (ids.size() == 0) {
                return 0;
            } else {
                return ((Integer) ids.get(0)).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logDebug("getAccountIdByOrderId, error " + e);
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public boolean ledgerSwitchOff(int pAccountId) throws RemoteException {
        Connection conn = null;
        try {
            if (pAccountId > 0) {

                conn = getConnection();
                PropertyUtil pru = new PropertyUtil(conn);
                String val = pru.fetchValue(0, pAccountId, RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_LEDGER_SWITCH);
                return Utility.isOff(val, false);
            }
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            logDebug("ledgerSwitchOff, error " + e);
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
        return false;
    }

    public IdVector getAccountsForStore(int pStoreId)
    throws RemoteException{
        Connection conn = null;

        try {
          conn = getConnection();
          DBCriteria crit = new DBCriteria();

          if (pStoreId == 0) {
              crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                      RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
          } else {
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,
                            pStoreId);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                            RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);

            IdVector ids = BusEntityAssocDataAccess.selectIdOnly(conn,
                BusEntityAssocDataAccess.BUS_ENTITY1_ID,
                crit);

            if (ids.size() == 0)
              return null;

            return ids;
          }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getAccountsForStore: " + e.getMessage());
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public int cloneAccount(int cloneAccountId, int storeId, String accountName, String user)
    throws RemoteException {

    	Connection conn = null;
    	BusEntityData busEntity = null;

    	try {
    		conn = getConnection();

    		// get busEntity for cloneAccountId
    		busEntity = BusEntityDataAccess.select(conn, cloneAccountId);
    		busEntity.setShortDesc(accountName);
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
            while (propertyIter.hasNext()) {
            	PropertyData property = (PropertyData) propertyIter.next();
            	property.setBusEntityId(accountId);
            	property.setAddBy(user);
            	property.setModBy(user);
	    		PropertyDataAccess.insert(conn, property);
    		}

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

    	} catch (Exception e){
    		throw processException(e);
    	}finally{
    		closeConnection(conn);
    	}
    }

    public ProductViewDefDataVector getProductViewDefData(int accountId)
            throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria cr = new DBCriteria();
            cr.addEqualTo(ProductViewDefDataAccess.ACCOUNT_ID, accountId);
            ProductViewDefDataVector list = ProductViewDefDataAccess.select(
                    conn, cr);
            return list;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }
    public AccountSearchResultViewVector search(String stores,
    											String fieldValue,
    											String fieldSearchType,
    											String searchGroupId,
    											boolean showInactive ) throws RemoteException{
        return search(stores, fieldValue, fieldSearchType, null, null, searchGroupId, showInactive);
    }

    public AccountSearchResultViewVector search(String stores,
			String fieldValue,
			String fieldSearchType,
			String refNumValue,
			String refNumSearchType,
			String searchGroupId,
			boolean showInactive ) throws RemoteException{
    	return search(stores, fieldValue, fieldSearchType, refNumValue, refNumSearchType, searchGroupId, showInactive,0);
    }
    public AccountSearchResultViewVector search(String stores,
    											String fieldValue,
    											String fieldSearchType,
    											String refNumValue,
    											String refNumSearchType,
    											String searchGroupId,
    											boolean showInactive,
    											int userId) throws RemoteException{
    	AccountSearchResultViewVector v = new AccountSearchResultViewVector();

    	if(Utility.isSet(fieldValue)){
        	if(!fieldSearchType.equals("id")){
                if(fieldSearchType.equals("nameBegins")){
                    fieldValue = fieldValue +"%";
                } else {
                    fieldValue = "%"+fieldValue+"%";
                }
            }
    	}

    	String refNumFromStr = "";
        String refNumCondStr = "";
    	if (Utility.isSet(refNumValue)) {
            refNumFromStr = ", clw_property refNumProp ";

            if (ORACLE.equals(databaseName)) {
                refNumCondStr = " AND refNumProp.Short_Desc = 'DIST_ACCT_REF_NUM' " +
                            " AND refNumProp.Bus_Entity_Id = e.bus_entity_id " +
                            " AND NLS_UPPER(refNumProp.Clw_Value) LIKE ";
                if (refNumSearchType.equals("nameBegins")) {
                    refNumCondStr += "NLS_UPPER('" + refNumValue + "%') ";
                } else {
                    refNumCondStr += "NLS_UPPER('%" + refNumValue + "%') ";
                }
            } else if (EDB.equals(databaseName)) {
            	refNumCondStr = " AND refNumProp.Short_Desc = 'DIST_ACCT_REF_NUM' " +
                            " AND refNumProp.Bus_Entity_Id = e.bus_entity_id " +
                            " AND UPPER(refNumProp.Clw_Value) LIKE ";
                if (refNumSearchType.equals("nameBegins")) {
                    refNumCondStr += "UPPER('" + refNumValue + "%') ";
                } else {
                    refNumCondStr += "UPPER('%" + refNumValue + "%') ";
                }
            } else {
                throw new RemoteException("Unknown database");
            }
            
        }

    	StringBuffer sql = new StringBuffer();
	    sql.append("SELECT e.bus_entity_id," +
	    		"e.erp_num," +
	    		"e.short_desc," +
	    		"a.city," +
	    		"a.state_province_cd," +
	    		"p.Clw_value," +
	    		"e.bus_entity_status_cd, " +
	    		"a.address1," +
	    		"a.postal_code, " +
	    		"a.country_cd " +
	    		"FROM CLW_BUS_ENTITY e, CLW_PROPERTY p, CLW_ADDRESS a " +
	    		refNumFromStr +
	    		"WHERE   e.BUS_ENTITY_ID IN" +
	    		"   (  SELECT DISTINCT BUS_ENTITY1_ID " +
	    		"FROM CLW_BUS_ENTITY_ASSOC   WHERE BUS_ENTITY2_ID IN ( ? ) ) " +
	    		"AND e.BUS_ENTITY_TYPE_CD = 'ACCOUNT' " +
	    		"AND e.bus_entity_id =a.bus_entity_id(+) " +
	    		"AND e.BUS_ENTITY_ID = p.bus_entity_id(+) " +
	    		"AND p.USER_ID(+) IS NULL " +
	    		"AND p.property_type_cd(+) = 'ACCOUNT_TYPE_CD' " +
	    		"AND a.PRIMARY_IND(+) = 1 ");

	    StringBuffer condition = new StringBuffer();
	    if(Utility.isSet(searchGroupId) && (!searchGroupId.equals("0"))){
	    	condition.append(" AND e.BUS_ENTITY_ID IN " +
	    			"(SELECT DISTINCT BUS_ENTITY_ID " +
	    			"FROM CLW_GROUP_ASSOC WHERE GROUP_ID = '"+ searchGroupId +"' " +
	    			"AND GROUP_ASSOC_CD = 'BUS_ENTITY_OF_GROUP') ");
	    }
	    if(userId>0){
	    	String selUserAccountGroup = "SELECT DISTINCT A.GROUP_ID " +
			"FROM CLW_GROUP_ASSOC A, CLW_GROUP B " +
			"WHERE A.USER_ID = "+userId+ " " +
			"AND A.GROUP_ID = B.GROUP_ID " +
			"AND B.GROUP_TYPE_CD = 'ACCOUNT'";		    	
	    	
    		condition.append(" AND e.BUS_ENTITY_ID IN " +
	    			"(SELECT DISTINCT BUS_ENTITY_ID " +
	    			"FROM CLW_GROUP_ASSOC WHERE GROUP_ID IN (" + selUserAccountGroup + ") " +
	    			"AND GROUP_ASSOC_CD = 'BUS_ENTITY_OF_GROUP') ");
		}
		

        if (Utility.isSet(fieldValue)) {
            if(fieldSearchType.equals("id")){
                condition.append(" AND e.BUS_ENTITY_ID = ? ");
            } else {
            	if (ORACLE.equals(databaseName)) {
                    condition.append(" AND NLS_UPPER(e.SHORT_DESC) LIKE NLS_UPPER(?)");
            	} else if (EDB.equals(databaseName)) {
            		condition.append(" AND UPPER(e.SHORT_DESC) LIKE UPPER(?)");
            	} else {
                    throw new RemoteException("Unknown database");
                }
            }
        }

	    if(!showInactive){
	    	condition.append("  AND e.bus_entity_status_cd <> 'INACTIVE' " );
	    }
	    sql.append(condition);

	    sql.append(refNumCondStr);

	    sql.append("ORDER BY e.SHORT_DESC ASC");

    	Connection con = null;
    	mCat.info("Query in AccountBean.Search " + sql.toString());
    	mCat.info("Parameters to sql stores " + stores + "fieldValue " + fieldValue);
    	try{
	    	con = getConnection();
	    	PreparedStatement stmt = con.prepareStatement(sql.toString());
	    	stmt.setString(1, stores);
	    	if (Utility.isSet(fieldValue)) {
	    	    stmt.setString(2, fieldValue);
            }
	    	stmt.setMaxRows(Constants.MAX_ACCOUNTS_TO_RETURN);
		    ResultSet rs=stmt.executeQuery();


		    while(rs.next()){
		    	AccountSearchResultView accountResult = new AccountSearchResultView();
		    	accountResult.setAccountId(rs.getInt(1));
		    	accountResult.setErpNum(rs.getString(2));
		    	accountResult.setShortDesc(rs.getString(3));
		    	accountResult.setCity(rs.getString(4));
		    	accountResult.setStateProvinceCd(rs.getString(5));
		    	accountResult.setValue(rs.getString(6));
		    	accountResult.setBusEntityStatusCd(rs.getString(7));
		    	accountResult.setAddress1(rs.getString(8));
		    	accountResult.setPostalCode(rs.getString(9));
		    	accountResult.setCountryCd(rs.getString(10));

		    	v.add(accountResult);
		    }
		    mCat.info("SSSearch result size " + v.size());
		} catch (Exception e) {
	        e.printStackTrace();
	        logError(" AccountBean.search: " + e);
	    } finally {
	    	closeConnection(con);
	    }
	    	return v;
    }

    public AccountSearchResultViewVector searchAccounts(    int userId,
                                                            IdVector stores,
                                                            String fieldValue,
                                                            String fieldSearchType,
                                                            String searchGroupId,
                                                            boolean showInactive)
                                                        throws RemoteException {
        AccountSearchResultViewVector accountSearchResultVV = new AccountSearchResultViewVector();
        BusEntitySearchCriteria crit = new BusEntitySearchCriteria();
        Connection conn = null;
        try {
            conn = getConnection();

            if (userId > 0) {
                crit.addUserId(userId);
            }
            if (stores != null && stores.size() > 0) {
                crit.setStoreBusEntityIds(stores);
            }
            if (fieldValue != null && !"".equals(fieldValue)) {
                if (fieldSearchType != null && !"".equals(fieldSearchType)) {
                    if ("id".equals(fieldSearchType)) {
                        crit.setSearchId(fieldValue);
                    } else {
                        crit.setSearchName(fieldValue);
                        if ("nameBegins".equals(fieldSearchType)) {
                            crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                        } else {
                            crit.setSearchNameType(BusEntitySearchCriteria.NAME_CONTAINS);
                        }
                    }
                } else {
                    crit.setSearchName(fieldValue);
                    crit.setSearchNameType(BusEntitySearchCriteria.NAME_STARTS_WITH);
                }
            }
            if (searchGroupId != null && !"".equals(searchGroupId) && !"0".equals(searchGroupId)) {
                crit.setSearchGroupId(searchGroupId);
            }
            crit.setSearchForInactive(showInactive);
            crit.setOrder(BusEntitySearchCriteria.ORDER_BY_NAME);
            BusEntityDataVector busEntityDV =
                    BusEntityDAO.getBusEntityByCriteria(conn, crit, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);

            if (busEntityDV.size() > 0) {
                int size = busEntityDV.size();
                BusEntityData beD = null;
                AddressData billingAddress;
                AddressDataVector addrV = null;
                PropertyDataVector propsV = null;
                PropertyData accTypeProperty;
                for (int i = 0; i < size; i++) {
                    billingAddress = null;
                    accTypeProperty = null;
                    beD = (BusEntityData)busEntityDV.get(i);
                    AccountSearchResultView accountResult = new AccountSearchResultView();

                    accountResult.setAccountId(beD.getBusEntityId());
                    accountResult.setErpNum(beD.getErpNum());
                    accountResult.setShortDesc(beD.getShortDesc());
                    accountResult.setBusEntityStatusCd(beD.getBusEntityStatusCd());

                    // get billing address
                    DBCriteria addrCrit = new DBCriteria();
                    addrCrit.addEqualTo(AddressDataAccess.BUS_ENTITY_ID, beD.getBusEntityId());
                    addrCrit.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD, RefCodeNames.ADDRESS_TYPE_CD.BILLING);
                    addrV = AddressDataAccess.select(conn, addrCrit);
                    // if more than one billing address - get the first one
                    if (addrV.size() > 0) {
                        billingAddress = (AddressData)addrV.get(0);
                    }
                    if (billingAddress != null) {
                        accountResult.setCity(billingAddress.getCity());
                        accountResult.setStateProvinceCd(billingAddress.getStateProvinceCd());
                        accountResult.setAddress1(billingAddress.getAddress1());
                        accountResult.setPostalCode(billingAddress.getPostalCode());
                    }

                    // get account's type
                    DBCriteria propCrit = new DBCriteria();
                    propCrit.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, beD.getBusEntityId());
                    propCrit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_TYPE);
                    propsV = PropertyDataAccess.select(conn, propCrit);
                    // if more than one ACCOUNT_TYPE property - get the first one
                    if (propsV.size() > 0) {
                        accTypeProperty = (PropertyData)propsV.get(0);
                    }
                    if (accTypeProperty != null) {
                        accountResult.setValue(accTypeProperty.getValue());
                    }

                    accountSearchResultVV.add(accountResult);
                }
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }

        return accountSearchResultVV;
    }

    public void updateProductViewDefData(int accountId, ProductViewDefDataVector list, String userName)
    throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            for (int i = 0; list != null && i < list.size(); i++) {
                ProductViewDefData item = (ProductViewDefData) list.get(i);
                item.setAccountId(accountId);
                item.setModBy(userName);
                if (item.getProductViewDefId() > 0) {
                    ProductViewDefDataAccess.update(conn, item);
                } else {
                    item.setAddBy(userName);
                    ProductViewDefDataAccess.insert(conn, item);
                }
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public Map<Integer, String> getPropertyValues(IdVector accountIds,
            String propertyType) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            Map<Integer, String> result = new HashMap<Integer, String>();
            while (accountIds.size() > 0) {
                IdVector bufferIds = new IdVector();
                while (bufferIds.size() < 1000 && accountIds.size() > 0) {
                    bufferIds.add(accountIds.get(0));
                    accountIds.remove(0);
                }
                DBCriteria crit = new DBCriteria();
                crit.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, bufferIds);
                crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, propertyType);
                crit.addIsNull(PropertyDataAccess.USER_ID);
                PropertyDataVector props = PropertyDataAccess
                        .select(conn, crit);
                for (int i = 0; i < props.size(); i++) {
                    PropertyData pd = (PropertyData) props.get(i);
                    result.put(pd.getBusEntityId(), pd.getValue());
                }
            }
            return result;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public Map<Integer, List> getPropertiesForAccounts(IdVector accountIds, List shortDescriptions,
            List propertyTypes, boolean omitNullValues) throws RemoteException {
        Connection conn = null;
        Map<Integer, List> result = new HashMap<Integer, List>();
        try {
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            if (accountIds != null && accountIds.size() > 0) {
            	crit.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, accountIds);
            }
            if (shortDescriptions != null && shortDescriptions.size() > 0) {
            	crit.addOneOf(PropertyDataAccess.SHORT_DESC, shortDescriptions);
            }
            if (propertyTypes != null && propertyTypes.size() > 0) {
            	crit.addOneOf(PropertyDataAccess.PROPERTY_TYPE_CD, propertyTypes);
            }
            if (omitNullValues) {
            	crit.addIsNotNull(PropertyDataAccess.CLW_VALUE);
            }
            PropertyDataVector props = PropertyDataAccess.select(conn, crit);
            for (int i = 0; i < props.size(); i++) {
                PropertyData pd = (PropertyData) props.get(i);
                List properties = result.get(pd.getBusEntityId());
                if (properties == null) {
                	properties = new ArrayList();
                	result.put(pd.getBusEntityId(), properties);
                }
                properties.add(pd);
            }
        } 
        catch (Exception e) {
            throw processException(e);
        } 
        finally {
            closeConnection(conn);
        }
        return result;
    }

    public IdVector getAccountAssocCollection(IdVector pAccountIds,
                                              String pAssocType,
                                              String pStatusCd) throws RemoteException{
         Connection con = null;
        try {
            con = getConnection();
            return getAccountAssocCollection(con, pAccountIds, pAssocType,pStatusCd);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(con);
        }
    }


    private IdVector getAccountAssocCollection(Connection con, IdVector pAccountIds, String pAssocType, String pStatusCd) throws Exception {

        IdVector resIds = new IdVector();
        if (pAccountIds != null && !pAccountIds.isEmpty()) {

            DBCriteria dbc = new DBCriteria();

            dbc.addJoinTableOneOf(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID, pAccountIds);
            dbc.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, pAssocType);
            dbc.addJoinCondition(BusEntityDataAccess.BUS_ENTITY_ID, BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, pStatusCd);

            BusEntityDataVector beVector = BusEntityDataAccess.select(con, dbc);

            HashSet resultSet = new HashSet(Utility.toIdVector(beVector));
            resIds.addAll(resultSet);
        }

        return resIds;
    }

    

    public String getDefaultEmail(int pAccountId, int pStoreId)
			throws RemoteException {
		String result = null;
		Connection conn = null;
		try {
			conn = getConnection();
			DBCriteria cr = null;
			if (pAccountId > 0) {
				cr = new DBCriteria();
				cr.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, pAccountId);
				cr.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,
						RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
				cr.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,
						RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
				EmailDataVector edv = EmailDataAccess.select(conn, cr);
				if (edv.size() > 0) {
					EmailData ed = (EmailData) edv.get(0);
					result = ed.getEmailAddress();
				}
			}
			if (Utility.isSet(result) == false && pStoreId > 0) {
				cr = new DBCriteria();
				cr.addEqualTo(EmailDataAccess.BUS_ENTITY_ID, pStoreId);
				cr.addEqualTo(EmailDataAccess.EMAIL_STATUS_CD,
						RefCodeNames.EMAIL_STATUS_CD.ACTIVE);
				cr.addEqualTo(EmailDataAccess.EMAIL_TYPE_CD,
						RefCodeNames.EMAIL_TYPE_CD.DEFAULT);
				EmailDataVector edv = EmailDataAccess.select(conn, cr);
				if (edv.size() > 0) {
					EmailData ed = (EmailData) edv.get(0);
					result = ed.getEmailAddress();
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		} finally {
			closeConnection(conn);
		}
	}

    /**
     *fetches the account Ids identified by the supplied search criteria
     *@param pAccountIds pAccount ids
     *@returns the populated idVector
     *@ ??? throws DataNotFoundException if ANY of the accounts in the id vector are not found ???
     *@exception  RemoteException  if an error occurs
     */
    public IdVector getInactiveAccountIds(IdVector pAccountIds) throws RemoteException{
        IdVector idV = new IdVector();
        IdVector result = new IdVector();
        Connection conn = null;
      while (pAccountIds.size() > 0) {
            IdVector bufferIds = new IdVector();
            while (bufferIds.size() < 1000 && pAccountIds.size() > 0) {
                bufferIds.add(pAccountIds.get(0));
                pAccountIds.remove(0);
            }
		DBCriteria crit = new DBCriteria();
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                RefCodeNames.BUS_ENTITY_STATUS_CD.INACTIVE);
        crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,
              IdVector.toCommaString(bufferIds));
        try {
            conn = getConnection();
            idV = BusEntityDataAccess.selectIdOnly(conn, crit);
            for (int i = 0; i < idV.size(); i++) {
                result.add(idV.get(i));
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
      }//while
      return result;
    }

    public AccCategoryToCostCenterView getCategoryToCostCenterViewByCatalog(int pCatalogId)  throws RemoteException {
      Connection conn = null;
      AccCategoryToCostCenterView categToCCView = null;
      String subSql =
          " select distinct ca1.BUS_ENTITY_ID, c1.CATALOG_ID acc_catalog_id  " +
                " from " +
                "   clw_bus_entity_assoc ba, clw_bus_entity be,"+
                "   clw_catalog_assoc ca, clw_catalog c, " +
                "   clw_catalog_assoc ca1, clw_catalog c1 " +
                " where c.CATALOG_ID = " + pCatalogId +
                " and c.CATALOG_ID = ca.catalog_id " +
                " and c.CATALOG_STATUS_CD = '"+RefCodeNames.CATALOG_STATUS_CD.ACTIVE+"' " +
                " and c.CATALOG_TYPE_CD ='"+ RefCodeNames.CATALOG_TYPE_CD.SHOPPING+ "' " +
                " and ca.CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE+"' " +
                " and ca.BUS_ENTITY_ID = ba.BUS_ENTITY1_ID " +
                " and ba.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' " +
                " and ca1.BUS_ENTITY_ID = ba.BUS_ENTITY2_ID " +
                " and ca1.CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' " +
                " and c1.CATALOG_ID = ca1.catalog_id " +
                " and c1.CATALOG_TYPE_CD ='"+ RefCodeNames.CATALOG_TYPE_CD.ACCOUNT +"' " +
                " and c1.CATALOG_STATUS_CD ='"+RefCodeNames.CATALOG_STATUS_CD.ACTIVE+"' "+
                " and ba.BUS_ENTITY1_ID = be.BUS_ENTITY_ID "+
                " and be.BUS_ENTITY_STATUS_CD = '" +RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE+ "'";

      log.info("getCategoryToCostCenterViewByCatalog() ==> subSql=" + subSql);
      try {
        conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(subSql);
        ResultSet rs = stmt.executeQuery();
        int accountId = 0;
        int accCatalogId = 0;
        while (rs.next()) {
          accountId = (rs.getString(1)!= null) ? rs.getInt(1) : 0;
          accCatalogId = (rs.getString(2)!= null) ? rs.getInt(2) : 0;
          if (rs.next()){
            throw new Exception("getCategoryToCostCenterViewByCatalog() ==>Error. Multiple accounts/account Catalogs found. Site catalog Id =" + pCatalogId);
          }
        }
        if (accountId == 0 || accCatalogId == 0) {
          return null;
//          throw new Exception("getCategoryToCostCenterViewByCatalog() ==> Error. No account/account Catalog. Site catalog Id =" + pCatalogId);
        }
        rs.close();
        stmt.close();

        categToCCView = AccCategoryToCostCenterView.createValue();
        categToCCView.setAccountId(accountId);
        categToCCView.setAccCatalogId(accCatalogId);
        categToCCView.setLastReqSiteCatalogId(pCatalogId);
        categToCCView.setLastReqSiteId(0);

        HashMap categToCostCenterMap = new HashMap();
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, accCatalogId);
        dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD, RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY);
        dbc.addCondition(" NVL("+CatalogStructureDataAccess.STATUS_CD+",'"+RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE+"')<> '" + RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.INACTIVE+ "'");       
        //      dbc.addEqualTo(CatalogStructureDataAccess.STATUS_CD, RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE) ;
        CatalogStructureDataVector csDV = CatalogStructureDataAccess.select(conn, dbc);
        for (int i = 0; i < csDV.size(); i++) {
          CatalogStructureData csD = (CatalogStructureData)csDV.get(i);
          categToCostCenterMap.put(new Integer(csD.getItemId()), new Integer(csD.getCostCenterId()));
        }
        categToCCView.setCategoryToCostCenterMap(categToCostCenterMap);

      } catch (Exception e) {
         e.printStackTrace();
         throw processException(e);
      } finally {
            closeConnection(conn);
      }
      log.info("getCategoryToCostCenterViewByCatalog() ==> Uploaded NEW categToCCView for siteCatalogId=" +pCatalogId + "; "+((categToCCView!= null)?("accCatalogId="+categToCCView.getAccCatalogId()):" NULL"));
      return  categToCCView;
    }

    public AccCategoryToCostCenterView getCategoryToCostCenterViewByAccount(int pAccountId)  throws RemoteException {
       Connection conn = null;
       if ( pAccountId ==0){
         return null;
       }

       AccCategoryToCostCenterView categToCCView = null;
       String sql = "SELECT distinct " +
         " cs.COST_CENTER_ID, " +
         " cs.ITEM_ID categoryId, "+
         " cc.CATALOG_ID acc_catalog_id, "+
         " cc.BUS_ENTITY_ID account_id " +
         " FROM clw_catalog_structure cs,  " +
         " (select ca.CATALOG_ID, ca.BUS_ENTITY_ID  " +
                 "  from clw_catalog_assoc ca, clw_catalog c " +
                 "  where ca.BUS_ENTITY_ID =" +pAccountId +
                 "  and ca.CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' " +
                 "  and ca.CATALOG_ID = c.CATALOG_ID " +
                 "  and c.CATALOG_STATUS_CD = '"+RefCodeNames.CATALOG_STATUS_CD.ACTIVE+"' " +
                 "  and c.CATALOG_TYPE_CD = '"+ RefCodeNames.CATALOG_TYPE_CD.ACCOUNT +"' " +
         "  ) cc  " +
         " WHERE cs.CATALOG_ID = cc.CATALOG_ID " +
         "  and NVL(cs.STATUS_CD,'ACTIVE')<> '" +RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.INACTIVE +"' "+
         "  and cs.CATALOG_STRUCTURE_CD = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+ "' " ;
       log.info("getCategoryToCostCenterViewByAcc() ==> sql=" + sql);
       try {
         conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery();
         int accountId = 0;
         int accCatalogId = 0;
         HashMap categToCostCenterMap = new HashMap();
         while (rs.next()) {
           accountId = (rs.getString(4)!= null) ? rs.getInt(4) : 0;
           accCatalogId = (rs.getString(3)!= null) ? rs.getInt(3) : 0;
           if (accountId == 0 || accCatalogId == 0) {
             throw new Exception("getCategoryToCostCenterViewByAcc() ==> Error in Cost Centers search for account. Account Catalog not fount. AccountId =" + pAccountId);
           }
           if (categToCCView == null ) {
            categToCCView = AccCategoryToCostCenterView.createValue();
            categToCCView.setAccountId(accountId);
            categToCCView.setAccCatalogId(accCatalogId);
            categToCCView.setLastReqSiteId(0);
            categToCCView.setLastReqSiteCatalogId(0);
            int categoryId = (rs.getString(2) != null) ? rs.getInt(2) : 0;
            int costCenterId = (rs.getString(1) != null) ? rs.getInt(1) : 0;
            categToCostCenterMap.put(new Integer(categoryId), new Integer(costCenterId));
          } else if (categToCCView.getAccCatalogId()!= accCatalogId ||
                    categToCCView.getAccountId()!= accountId ) {
            throw new Exception("getCategoryToCostCenterViewByAcc() ==> Error in Cost Centers search for account. Multiple account Catalogs found. AccountId =" + pAccountId);
          } else {
           int categoryId = (rs.getString(2) != null) ? rs.getInt(2) : 0;
           int costCenterId = (rs.getString(1) != null) ? rs.getInt(1) : 0;
           categToCostCenterMap.put(new Integer(categoryId), new Integer(costCenterId));
         }
       }
       if (categToCCView != null) {
         categToCCView.setCategoryToCostCenterMap(categToCostCenterMap);
       }
       rs.close();
       stmt.close();

       } catch (Exception e) {
          e.printStackTrace();
          throw processException(e);
       } finally {
             closeConnection(conn);
       }
       log.info("getCategoryToCostCenterViewByAcc() ==> Uploaded NEW categToCCView for accountId=" +pAccountId + "; "+((categToCCView!= null)?("accCatalogId="+categToCCView.getAccCatalogId()):" NULL"));
       return  categToCCView;
     }

    public AccCategoryToCostCenterView getCategoryToCostCenterView(int pSiteId)  throws RemoteException {
      Connection conn = null;
      if ( pSiteId ==0){
        return null;
      }

      AccCategoryToCostCenterView categToCCView = null;
      String sql = "SELECT distinct " +
        " cs.COST_CENTER_ID, " +
        " cs.ITEM_ID categoryId, "+
        " cc.CATALOG_ID acc_catalog_id, "+
        " cc.BUS_ENTITY_ID account_id " +
        " FROM clw_catalog_structure cs,  " +
        " (select ca.CATALOG_ID, ca.BUS_ENTITY_ID  " +
                "  from clw_bus_entity_assoc ba, clw_catalog_assoc ca, clw_catalog c " +
                "  where ba.BUS_ENTITY1_ID = " + pSiteId +
                "  and ba.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' " +
                "  and ba.BUS_ENTITY2_ID = ca.BUS_ENTITY_ID " +
                "  and ca.CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' " +
                "  and ca.CATALOG_ID = c.CATALOG_ID " +
                "  and c.CATALOG_STATUS_CD = '"+RefCodeNames.CATALOG_STATUS_CD.ACTIVE+"' " +
                "  and c.CATALOG_TYPE_CD = '"+ RefCodeNames.CATALOG_TYPE_CD.ACCOUNT +"' " +
        "  ) cc  " +
        " WHERE cs.CATALOG_ID = cc.CATALOG_ID " +
//      "  and cs.STATUS_CD = '" +RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.ACTIVE +"' "+
        "  and NVL(cs.STATUS_CD,'ACTIVE')<> '" +RefCodeNames.CATALOG_STRUCTURE_STATUS_CD.INACTIVE +"' "+
        "  and cs.CATALOG_STRUCTURE_CD = '"+ RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY+ "' " ;
      log.info("getCategoryToCostCenterView() ==> sql=" + sql);
      try {
        conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        int accountId = 0;
        int accCatalogId = 0;
        HashMap categToCostCenterMap = new HashMap();
        while (rs.next()) {
          accountId = (rs.getString(4)!= null) ? rs.getInt(4) : 0;
          accCatalogId = (rs.getString(3)!= null) ? rs.getInt(3) : 0;
          if (accountId == 0 || accCatalogId == 0) {
            throw new Exception("getCategoryToCostCenterView() ==> Error in Cost Centers search for site. No account/account Catalog. SiteId =" + pSiteId);
          }
          if (categToCCView == null ) {
           categToCCView = AccCategoryToCostCenterView.createValue();
           categToCCView.setAccountId(accountId);
           categToCCView.setAccCatalogId(accCatalogId);
           categToCCView.setLastReqSiteId(pSiteId);
           categToCCView.setLastReqSiteCatalogId(0);
           int categoryId = (rs.getString(2) != null) ? rs.getInt(2) : 0;
           int costCenterId = (rs.getString(1) != null) ? rs.getInt(1) : 0;
           categToCostCenterMap.put(new Integer(categoryId), new Integer(costCenterId));

         } else if (categToCCView.getAccCatalogId()!= accCatalogId ||
                   categToCCView.getAccountId()!= accountId ) {
           throw new Exception("getCategoryToCostCenterView() ==> Error in Cost Centers search for site. Multiple accounts/account Catalogs found. SiteId =" + pSiteId);
         } else {
          int categoryId = (rs.getString(2) != null) ? rs.getInt(2) : 0;
          int costCenterId = (rs.getString(1) != null) ? rs.getInt(1) : 0;
          categToCostCenterMap.put(new Integer(categoryId), new Integer(costCenterId));
        }
      }
      if (categToCCView != null) {
        categToCCView.setCategoryToCostCenterMap(categToCostCenterMap);
      }
      rs.close();
      stmt.close();

      } catch (Exception e) {
         e.printStackTrace();
         throw processException(e);
      } finally {
            closeConnection(conn);
      }
      log.info("getCategoryToCostCenterView() ==> Uploaded NEW categToCCView for siteId=" +pSiteId + "; "+((categToCCView!= null)?("accCatalogId="+categToCCView.getAccCatalogId()):" NULL"));
      return  categToCCView;
    }

    public boolean isAccountForSite( int pSiteId, int pSiteCatalogId, int pAccCatalogId )  throws RemoteException{
      if ( pAccCatalogId == 0 || (pSiteId == 0 && pSiteCatalogId == 0)){
        return false;
      }
      boolean ok = false;
      String subSql =
          "select ca.BUS_ENTITY_ID from clw_catalog_assoc ca, clw_catalog c,  clw_bus_entity be "+
          " where C.CATALOG_ID = ca.catalog_id "+
          " and ca.catalog_id = "+ pSiteCatalogId +
          " and CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE+"' " +
          " and C.CATALOG_STATUS_CD = 'ACTIVE' " +
          " and CA.BUS_ENTITY_ID = BE.BUS_ENTITY_ID "+
          " and BE.BUS_ENTITY_STATUS_CD = 'ACTIVE' ";
      log.info("isAccountForSite()==> subSql=" + subSql);

      String subSql2 = " select bus_entity_id from clw_catalog_assoc ca "+
          " where CATALOG_ID =" + pAccCatalogId +
          " and CATALOG_ASSOC_CD = '"+RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT+"' " ;

      if (pSiteId > 0 ) {
        subSql= pSiteId +"";
      }

      Connection conn = null;
      try{
        conn = getConnection();
        DBCriteria crit = new DBCriteria();
        crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,subSql);
        crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, subSql2);
        crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
        BusEntityAssocDataVector assocVec = BusEntityAssocDataAccess.select(conn, crit);
        ok = assocVec != null && assocVec.size() > 0;

      }catch (Exception e){
        e.printStackTrace();
        throw processException(e);
      } finally {
            closeConnection(conn);
      }
      log.info("isAccountForSite()==> "+ok +">> FOR: siteId=" + pSiteId + ", pSiteCatalogId="+ pSiteCatalogId + ", pAccCatalogId="+ pAccCatalogId);
      return ok;
    }
    public boolean checkCostCentersForSite( int pSiteId, int pSiteCatalogId, AccCategoryToCostCenterView pCategToCCView )  throws RemoteException{

      boolean valid = false;
      if (pCategToCCView != null) {
        valid =  (pCategToCCView.getLastReqSiteId() > 0 && pSiteId > 0 && pCategToCCView.getLastReqSiteId() == pSiteId) ||
              (pCategToCCView.getLastReqSiteCatalogId() > 0 && pSiteCatalogId > 0 && pCategToCCView.getLastReqSiteCatalogId() == pSiteCatalogId);
        if (valid){
          log.info("checkCostCentersForSite() ==> Cost Centers requested for the same siteId/catalogId.");
        } else {
          valid = isAccountForSite(pSiteId, pSiteCatalogId,pCategToCCView.getAccCatalogId());
          if (valid){
            log.info("checkCostCentersForSite() ==> Used Cost Centers from parameters.");
          }
        }
       log.info("checkCostCentersForSite() ==> toKeepSessionAttr ="+valid+"; accCatalogId="+pCategToCCView.getAccCatalogId()+": siteId=("+pCategToCCView.getLastReqSiteId()+"=>"+pSiteId+"), catalogId=("+pCategToCCView.getLastReqSiteCatalogId()+"=>"+ pSiteCatalogId+")" );
      }
      return valid;
    }

    public AccCategoryToCostCenterView refreshCategoryToCostCenterView( AccCategoryToCostCenterView pCategToCCView, int pSiteId)  throws RemoteException{
      return refreshCategoryToCostCenterView(pCategToCCView, pSiteId, 0);
    }

    public AccCategoryToCostCenterView refreshCategoryToCostCenterView( AccCategoryToCostCenterView pCategToCCView, int pSiteId, int pSiteCatalogId)  throws RemoteException{
      log.info("refreshCategoryToCostCenterView() ==> BEGIN.");
      AccCategoryToCostCenterView  categToCCView = null;
//      try {
        boolean toUpdate =!checkCostCentersForSite(pSiteId, pSiteCatalogId, pCategToCCView);

        if (!toUpdate) {
          categToCCView = pCategToCCView;
          return categToCCView;
        }

        String siteIdStr = "" + pSiteId;
        if (pSiteId == 0) {
          if (pSiteCatalogId > 0) {
            categToCCView = getCategoryToCostCenterViewByCatalog(pSiteCatalogId);
          } else {
           log.info("refreshCategoryToCostCenterView() ==> Unable to refresh categoryToCostCenter Object. SiteId = 0; SiteCatalogId = 0 ") ;
           return null;
         }
       } else{
         categToCCView = getCategoryToCostCenterView(pSiteId);
       }
 //    } catch (Exception e) {
 //      e.printStackTrace();
 //    }
     log.info("refreshCategoryToCostCenterView() ==> END. categToCostCenterMap =" + ((categToCCView!= null) ? categToCCView.getCategoryToCostCenterMap().toString() : "NULL"));
     return categToCCView;

    }
    
    
    /**
     * Gets the fiscal calendar data for required fiscal year.
     * @param pAccountId - Account Id.
     * @param pFiscalYear - Fiscal year.
     * @return FiscalCalenderData - Fiscal Calendar Data.
     * @throws RemoteException
     */
     public FiscalCalenderData getFiscalCalenderForYear(int pAccountId,int pFiscalYear)throws RemoteException {
     	 Connection conn = null;
     	 FiscalCalenderData fiscalCalendarData = null;
          try {
              conn = getConnection();
              BusEntityDAO bdao = new BusEntityDAO();
              fiscalCalendarData = bdao.getFiscalCalenderForYear(conn, pAccountId, pFiscalYear);
          }
          catch (Exception e) {
              e.printStackTrace();
              logDebug("getFiscalCalender, error " + e);
          } finally {
              closeConnection(conn);
          }
          return fiscalCalendarData;
     }
     
     /**
      * Gets the fiscal calendar view for required fiscal year.
      * @param pAccountId - Account Id.
      * @param pFiscalYear - Fiscal year.
      * @return FiscalCalenderView - Fiscal Calendar View.
      * @throws RemoteException - if any error occurs.
      */
     public FiscalCalenderView getFiscalCalenderVForYear(int pAccountId,int pFiscalYear) throws RemoteException {
     	Connection conn = null;
     	FiscalCalenderView fiscalCalenderView = null;
         try {
             conn = getConnection();
             BusEntityDAO bdao = new BusEntityDAO();
             fiscalCalenderView = bdao.getFiscalCalenderVForYear(conn, pAccountId, pFiscalYear);
         } catch (Exception e) {
             e.printStackTrace();
             logDebug("getFiscalCalenderV, error " + e);
         } finally {
             closeConnection(conn);
         }
         return fiscalCalenderView;
     }
     
     /**
      * Gets all accounts that were configured in all groups for a user. 
      * @param stores - Store Id
      * @param fieldValue
      * @param fieldSearchType
      * @param searchGroupId
      * @param userId - User Id.
      * @param showInactive
      * @return
      * @throws RemoteException - if any error occurs.
      */
     public AccountSearchResultViewVector searchGroupsByUserId(String stores,
				String fieldValue,
				String fieldSearchType,
				String searchGroupId,
				int userId,
				boolean showInactive ) throws RemoteException{
    	 return search(stores, fieldValue, fieldSearchType, null, null, searchGroupId, showInactive,userId);
     }
     public ShoppingControlDataVector getShoppingControls(int accountId, IdVector itemIds) 
     throws RemoteException {
    	 Connection conn = null;
    	 try {
    		 conn = getConnection();
    		 ProductDAO pdao = mCacheManager.getProductDAO();
    		 ShoppingControlDataVector scdv =
    			 mCacheManager.getBusEntityDAO().getAccountShoppingControls
    			 (conn, accountId, itemIds);
    		 return scdv;
    	 }catch (Exception e){
    		 throw processException(e);
    	 }finally{
    		 closeConnection(conn);
    	 }

     }
     
     public BusEntityDataVector getAccountBusEntByCriteria(DBCriteria pCrit) throws RemoteException {
    	 Connection conn = null;
    	 try {
    		 conn = getConnection();
    		 return BusEntityDataAccess.select(conn, pCrit);
    	 }catch (Exception e){
    		 throw processException(e);
    	 }finally{
    		 closeConnection(conn);
    	 }    	 
     }
}
