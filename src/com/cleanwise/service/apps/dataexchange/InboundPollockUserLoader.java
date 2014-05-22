package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.apps.loaders.PipeFileParser;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.BusEntityParameterDataAccess;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;

import org.apache.log4j.Logger;


public class InboundPollockUserLoader extends InboundFlatFile {
			private final static int VERSION = 0;
			private final static int ACTION = 1;
			private final static int STORE_ID = 2;
			private final static int STORE_NAME = 3;
			private final static int ACCOUNT_REF_NUM = 4;
			private final static int ACCOUNT_NAME = 5;
			private final static int SITE_NAME = 6;
			private final static int SITE_REF_NUM = 7;
			private final static int USERNAME = 8;
			private final static int PASSWORD = 9;
			private final static int UPDATE_PASSWORD = 10;
			private final static int PREFERRED_LANGUAGE = 11;
			private final static int FIRST_NAME = 12;
			private final static int LAST_NAME = 13;
			private final static int ADDRESS1 = 14;
			private final static int ADDRESS2 = 15;
			private final static int CITY = 16;
			private final static int STATE = 17;
			private final static int POSTAL_CODE = 18;
			private final static int COUNTRY = 19;
			private final static int PHONE = 20;
			private final static int EMAIL = 21;
			private final static int FAX = 22;
			private final static int MOBILE = 23;
			private final static int APPROVER = 24;
			private final static int NEEDS_APPROVAL = 25;
			private final static int WAS_APPROVED = 26;
			private final static int WAS_REJECTED = 27;
			private final static int WAS_MODIFIED = 28;
			private final static int CORPORATE_USER = 29;
			private final static int RECEIVE_INVENTORY_MIS_EMAIL = 30;
			private final static int ON_ACCOUNT = 31;
			private final static int CREDIT_CARD = 32;
			private final static int OTHER_PAYMENT = 33;
			private final static int PO_NUM_REQUIRED = 34;
			private final static int SHOW_PRICE = 35;
			private final static int CONTRACT_ITEMS_ONLY = 36;
			private final static int BROWSE_ONLY = 37;
			private final static int NO_REPORTING = 38;
			private final static int ORDER_DETAIL_NOTIFICATION = 39;
			private final static int SHIPPING_NOTIFICATION = 40;
			private final static int WORK_ORDER_COMPLETED_NOTIFI = 41;
			private final static int WORK_ORDER_ACCEPTED_NOTIFI = 42;
			private final static int WORK_ORDER_REJECTED_NOTIFI = 43;
			private final static int GROUP_ID = 44;

			private final static String userloader = "pollockUserLoader";
			private final static String userActionAdd = "A";
			private final static String userActionChange = "C";
			private final static String userActionDelete = "D";
			//private final static int storeNum = 182241;
			private int storeNum = 0;
			private Date runDate = new Date();
			Connection conn = null;

    private static final String insertSql = "INSERT INTO CLT_POLLOCK_USER_LOADER(VERSION,ACTION,STORE_ID,STORE_NAME,ACCOUNT_REF_NUM," +
											"ACCOUNT_NAME,SITE_NAME,SITE_REF_NUM,USERNAME,PASSWORD,UPDATE_PASSWORD,PREFERRED_LANGUAGE," +
											"FIRST_NAME,LAST_NAME,ADDRESS1,ADDRESS2,CITY,STATE,POSTAL_CODE,COUNTRY,PHONE,EMAIL,FAX,MOBILE," +
											"APPROVER,NEEDS_APPROVAL,WAS_APPROVED,WAS_REJECTED,WAS_MODIFIED,CORPORATE_USER,RECEIVE_INVENTORY_MIS_EMAIL," +
											"ON_ACCOUNT,CREDIT_CARD,OTHER_PAYMENT,PO_NUM_REQUIRED,SHOW_PRICE,CONTRACT_ITEMS_ONLY,BROWSE_ONLY," +
											"NO_REPORTING,ORDER_DETAIL_NOTIFICATION,SHIPPING_NOTIFICATION,WORK_ORDER_COMPLETED_NOTIFI," +
											"WORK_ORDER_ACCEPTED_NOTIFI,WORK_ORDER_REJECTED_NOTIFI,GROUP_ID,add_by,add_date) " +
											"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";


    private static final String trimDataSql = "update CLT_POLLOCK_USER_LOADER set " +
						"VERSION = trim(VERSION),ACTION = trim(ACTION),STORE_ID	= trim(STORE_ID),STORE_NAME = trim(STORE_NAME)," +
 					    "ACCOUNT_REF_NUM	= trim(ACCOUNT_REF_NUM),ACCOUNT_NAME = trim(ACCOUNT_NAME),SITE_NAME = trim(SITE_NAME)," +
						"SITE_REF_NUM = trim(SITE_REF_NUM),USERNAME = trim(USERNAME),PASSWORD = trim(PASSWORD)," +
						"UPDATE_PASSWORD	= trim(UPDATE_PASSWORD),PREFERRED_LANGUAGE = trim(PREFERRED_LANGUAGE)," +
						"FIRST_NAME = trim(FIRST_NAME),LAST_NAME	= trim(LAST_NAME),ADDRESS1 = trim(ADDRESS1)," +
						"ADDRESS2	= trim(ADDRESS2),CITY = trim(CITY),STATE	= trim(STATE),POSTAL_CODE = trim(POSTAL_CODE)," +
						"COUNTRY	= trim(COUNTRY),PHONE = trim(PHONE),EMAIL = trim(EMAIL),FAX = trim(FAX)," +
						"MOBILE = trim(MOBILE),APPROVER	= trim(APPROVER),NEEDS_APPROVAL	= trim(NEEDS_APPROVAL)," +
						"WAS_APPROVED = trim(WAS_APPROVED),WAS_REJECTED = trim(WAS_REJECTED),WAS_MODIFIED = trim(WAS_MODIFIED)," +
						"CORPORATE_USER = trim(CORPORATE_USER),RECEIVE_INVENTORY_MIS_EMAIL = trim(RECEIVE_INVENTORY_MIS_EMAIL)," +
						"ON_ACCOUNT = trim(ON_ACCOUNT),CREDIT_CARD = trim(CREDIT_CARD),OTHER_PAYMENT = trim(OTHER_PAYMENT)," +
						"PO_NUM_REQUIRED	= trim(PO_NUM_REQUIRED),SHOW_PRICE = trim(SHOW_PRICE)," +
						"CONTRACT_ITEMS_ONLY = trim(CONTRACT_ITEMS_ONLY),BROWSE_ONLY = trim(BROWSE_ONLY)," +
						"NO_REPORTING = trim(NO_REPORTING),ORDER_DETAIL_NOTIFICATION = trim(ORDER_DETAIL_NOTIFICATION)," +
						"SHIPPING_NOTIFICATION = trim(SHIPPING_NOTIFICATION),WORK_ORDER_COMPLETED_NOTIFI	= trim(WORK_ORDER_COMPLETED_NOTIFI)," +
						"WORK_ORDER_ACCEPTED_NOTIFI = trim(WORK_ORDER_ACCEPTED_NOTIFI),WORK_ORDER_REJECTED_NOTIFI = trim(WORK_ORDER_REJECTED_NOTIFI)," +
						"GROUP_ID = trim(GROUP_ID)";

    private static String selectStoreId = "select distinct STORE_ID,STORE_NAME from CLT_POLLOCK_USER_LOADER";

    private static String verifyStoreId = "select bus_entity_id from clw_bus_entity where " +
	"upper(short_desc) = upper(?) and bus_entity_type_cd = 'STORE' " +
	"and bus_entity_status_cd = 'ACTIVE'";

    protected Logger log = Logger.getLogger(InboundPollockAccountLoader.class);

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("translate => BEGIN.");
        IntegrationServices isEjb = APIAccess.getAPIAccess()
        	.getIntegrationServicesAPI();
        try {
            PipeFileParser parser = new PipeFileParser();
            parser.parse(pIn);
         //   parser.cleanUpResult();
            List<List<String>> list = (List<List<String>>) parser
                    .getParsedStrings();
            process(list);
            log.info("STORE ID =====================" + storeNum);
            isEjb.processUser(conn,userloader,userActionAdd,userActionChange,userActionDelete,storeNum);
        } catch (Exception e) {
            log.error("translate => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms", e);
            setFail(true);
            throw e;
        }finally{
    		if (conn != null)
    			//conn.close();
    			closeConnection(conn);
    	}
        log.info("translate => END.Process time at : "
                + (System.currentTimeMillis() - startTime) + " ms");
    }


    private void process(List<List<String>> parsedData) throws Exception {
        TradingPartnerData partner = translator.getPartner();
        if (partner == null) {
            appendErrorMsgs("Trading Partner ID cannot be determined");
        }
        if (parsedData.size() == 0)
        	throw new Exception("Input file could be emptyyyyy.");
        //checkLine(parsedData);

        if (conn == null){
			conn = getConnection();
		}
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        //loadTempTable(parsedData);
        log.info("SQL insertSql in Temp Table = " +  insertSql);
        //NOTE we skip the first line if it is a header by setting i = 1
        for (int i = 0; parsedData != null && i < parsedData.size(); i++) {
            List<String> line = (List<String>) parsedData.get(i);
			String versionCd				= line.get(VERSION);
			String actionCd					= line.get(ACTION);
			String storeId					= line.get(STORE_ID);
			String storeName				= line.get(STORE_NAME);
			String accountRefNum			= line.get(ACCOUNT_REF_NUM);
			String accountName				= line.get(ACCOUNT_NAME);
			String siteName					= line.get(SITE_NAME);
			String siteRefNum				= line.get(SITE_REF_NUM);
			String username					= line.get(USERNAME);
			String password					= line.get(PASSWORD);
			String updatePassword			= line.get(UPDATE_PASSWORD);
			String preferredLanguage		= line.get(PREFERRED_LANGUAGE);
			String firstName				= line.get(FIRST_NAME);
			String lastName					= line.get(LAST_NAME);
			String address1					= line.get(ADDRESS1);
			String address2					= line.get(ADDRESS2);
			String city						= line.get(CITY);
			String state					= line.get(STATE);
			String postalCode				= line.get(POSTAL_CODE);
			String country					= line.get(COUNTRY);
			String phone					= line.get(PHONE);
			String email					= line.get(EMAIL);
			String fax						= line.get(FAX);
			String mobile					= line.get(MOBILE);
			String approver					= line.get(APPROVER);
			String needsApproval			= line.get(NEEDS_APPROVAL);
			String wasApproved				= line.get(WAS_APPROVED);
			String wasRejected				= line.get(WAS_REJECTED);
			String wasModified				= line.get(WAS_MODIFIED);
			String corporateUser			= line.get(CORPORATE_USER);
			String receiveInventoryMisEmail	= line.get(RECEIVE_INVENTORY_MIS_EMAIL);
			String onAccount				= line.get(ON_ACCOUNT);
			String creditCard				= line.get(CREDIT_CARD);
			String otherPayment				= line.get(OTHER_PAYMENT);
			String poNumRequired			= line.get(PO_NUM_REQUIRED);
			String showPrice				= line.get(SHOW_PRICE);
			String contractItemsOnly		= line.get(CONTRACT_ITEMS_ONLY);
			String browseOnly				= line.get(BROWSE_ONLY);
			String noReporting				= line.get(NO_REPORTING);
			String orderDetailNotification	= line.get(ORDER_DETAIL_NOTIFICATION);
			String shippingNotification		= line.get(SHIPPING_NOTIFICATION);
			String workOrderCompletedNotifi	= line.get(WORK_ORDER_COMPLETED_NOTIFI);
			String workOrderAcceptedNotifi	= line.get(WORK_ORDER_ACCEPTED_NOTIFI);
			String workOrderRejectedNotifi	= line.get(WORK_ORDER_REJECTED_NOTIFI);
			String groupId					= line.get(GROUP_ID);



            try {
            	pstmt.setString(1,versionCd);
				pstmt.setString(2,actionCd);
				pstmt.setString(3,storeId);
				pstmt.setString(4,storeName);
				pstmt.setString(5,accountRefNum);
				pstmt.setString(6,accountName);
				pstmt.setString(7,siteName);
				pstmt.setString(8,siteRefNum);
				pstmt.setString(9,username);
				pstmt.setString(10,password);
				pstmt.setString(11,updatePassword);
				pstmt.setString(12,preferredLanguage);
				pstmt.setString(13,firstName);
				pstmt.setString(14,lastName);
				pstmt.setString(15,address1);
				pstmt.setString(16,address2);
				pstmt.setString(17,city);
				pstmt.setString(18,state);
				pstmt.setString(19,postalCode);
				pstmt.setString(20,country);
				pstmt.setString(21,phone);
				pstmt.setString(22,email);
				pstmt.setString(23,fax);
				pstmt.setString(24,mobile);
				pstmt.setString(25,approver);
				pstmt.setString(26,needsApproval);
				pstmt.setString(27,wasApproved);
				pstmt.setString(28,wasRejected);
				pstmt.setString(29,wasModified);
				pstmt.setString(30,corporateUser);
				pstmt.setString(31,receiveInventoryMisEmail);
				pstmt.setString(32,onAccount);
				pstmt.setString(33,creditCard);
				pstmt.setString(34,otherPayment);
				pstmt.setString(35,poNumRequired);
				pstmt.setString(36,showPrice);
				pstmt.setString(37,contractItemsOnly);
				pstmt.setString(38,browseOnly);
				pstmt.setString(39,noReporting);
				pstmt.setString(40,orderDetailNotification);
				pstmt.setString(41,shippingNotification);
				pstmt.setString(42,workOrderCompletedNotifi);
				pstmt.setString(43,workOrderAcceptedNotifi);
				pstmt.setString(44,workOrderRejectedNotifi);
				pstmt.setString(45,groupId);
				pstmt.setString(46,userloader);
                pstmt.addBatch();
                if(i > 0 && i % 10000 == 0){
                     log.info("Calling execute batch at 10000 records");
                     pstmt.executeBatch();
                }

            }catch(Exception e){
			//	conn.rollback();
				conn.close();
				conn = null;
				throw e;
			}

        }

        pstmt.executeBatch();
        pstmt = conn.prepareStatement(trimDataSql);
        int n = pstmt.executeUpdate();
        pstmt.close();
        // verify that all records are loaded into temporary table
        if (n != parsedData.size()){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedData.size()+" records.");
        }

        pstmt = conn.prepareStatement(selectStoreId);
        ResultSet rs = pstmt.executeQuery();
        String storeNam = "";
        while(rs.next()){
			storeNum = rs.getInt(1);
			storeNam = rs.getString(2);
			if (rs.next())
        		throw new Exception("Error Found Two distinct Store ID's in File");
		}
        pstmt.close();
        if(storeNum == 0){
        	throw new Exception("Store ID is missing in File");
        } /*else{
      	   log.info("storeID in File = " + storeNum + "storeName is file = " + storeNam);
      	    pstmt = conn.prepareStatement(verifyStoreId);
          	pstmt.setString(1, storeNam);
          	rs = pstmt.executeQuery();
          	int busEntityI = 0;
          	while(rs.next()){
          		busEntityI = rs.getInt(1);
          	}
          	log.info("storeID from system = " + busEntityI);
          	if(busEntityI != storeNum){
          		throw new Exception("Error Found Store ID and store name in File does not match store_id and store name in System =" + storeNum + " store Name in File =" + storeNam + "store_id in system = " + busEntityI + "sql=" + verifyStoreId);
          	}


   		pstmt.close();
         }*/


        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception(errorMessage);
        }
        IntegrationServices isEjb = APIAccess.getAPIAccess()
                .getIntegrationServicesAPI();
        IntegrationRequestsVector irv = new IntegrationRequestsVector();
        int tradingPartnerId = partner.getTradingPartnerId();



    }
}
