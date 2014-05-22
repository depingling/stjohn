
package com.cleanwise.service.apps.dataexchange;
//com.cleanwise.service.apps.loaders.PipeFileParser
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


public class InboundPollockSiteLoader extends InboundFlatFile {
	private final static int VERSION = 0;
	private final static int ACTION = 1;
    private final static int STORE_ID = 2;
    private final static int STORE_NAME = 3;
    private final static int ACCOUNT_REF_NUM = 4;
    private final static int ACCOUNT_NAME = 5;
    private final static int SITE_NAME = 6;
    private final static int SITE_REF_NUM = 7;
    private final static int TAXABLE = 8;
    private final static int INV_SHOP = 9;
    private final static int BUYER_ORDER_GUIDE = 10;
    private final static int FIRST_NAME = 11;
    private final static int LAST_NAME = 12;
    private final static int ADDRESS1 = 13;
    private final static int ADDRESS2 = 14;
    private final static int ADDRESS3 = 15;
    private final static int ADDRESS4 = 16;
    private final static int CITY = 17;
    private final static int STATE = 18;
    private final static int POSTAL_CODE = 19;
    private final static int COUNTRY = 20;
    private final static int SHIP_MESSAGE = 21;
    private final static int ORD_COMMENTS = 22;
    private final static int ITEM_DISPLAY_RESTRICT = 23;
    private final static String siteloader = "pollockSiteLoader";
    private final static String siteActionAdd = "A";
    private final static String siteActionChange = "C";
    private final static String siteActionDelete = "D";

    //private final static int storeNum = 182241;
    private int storeNum = 0;
//    private boolean ifSiteExists;
    private Date runDate = new Date();
    private final static String tempTable ="clt_pollock_site_loader";

    Connection conn = null;
    private static final String insertSql = "insert into " + tempTable + "(version,action,store_id,store_name," +
    	"account_ref_num,account_name," +
    	"site_name,site_ref_num,taxable,inventory_shopping_para," +
    	"buyer_order_guide,first_name,last_name,address1,address2,address3," +
    	"address4,city,state,postal_code,country,ship_message,order_guide_comments,item_display_restrict,add_by,add_date)" +
    	" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";

    private static final String trimDataSql = "update "+tempTable+" set version = trim(version),action = trim(action)," +
    	    "store_id = trim(store_id),store_name = trim(store_name),account_ref_num =trim(account_ref_num)," +
    	    "account_name = trim(account_name),site_name = trim(site_name),site_ref_num = trim(site_ref_num)," +
    		"taxable = trim(taxable),inventory_shopping_para = trim(inventory_shopping_para)," +
    		"buyer_order_guide = trim(buyer_order_guide),first_name = trim(first_name)," +
    		"last_name=trim(last_name),address1=trim(address1),address2=trim(address2),address3=trim(address3)," +
    		"address4=trim(address4),city=trim(city),state=trim(state),postal_code=trim(postal_code)," +
    		"country=trim(country),ship_message=trim(ship_message),order_guide_comments=trim(order_guide_comments)," +
    		"item_display_restrict=trim(item_display_restrict)";

    private static String selectStoreId = "select distinct STORE_ID,STORE_NAME from " + tempTable;
//'PollardSiteLoader',sysdate
    private static String verifyStoreId = "select bus_entity_id from clw_bus_entity where " +
	"upper(short_desc) = upper(?) and bus_entity_type_cd = 'STORE' " +
	"and bus_entity_status_cd = 'ACTIVE'";

	//private static String selectSiteAccSql = "select site_ref_num, account_ref_num from clt_pollock_site_loader";
    
    private static String checkDupSite = "select site_ref_num from (select site_ref_num,count(*) from " +
    "(select s.bus_entity_id site_id,s.short_desc site_name,s.bus_entity_status_cd site_status," +
    "cp.clw_value site_ref_num " +
    " from clw_bus_entity s,clw_property cp,clw_bus_entity_assoc cbea,clw_bus_entity_assoc cbeaa," +
    "clw_bus_entity acc " +
    " where cbeaa.bus_entity2_id = ? and cbeaa.bus_entity1_id = acc.bus_entity_id " +
    "and acc.bus_entity_type_cd = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"' " +
    " and acc.bus_entity_id = cbea.bus_entity2_id and cbea.bus_entity1_id = s.bus_entity_id " +
    " and s.bus_entity_id = cp.bus_entity_id and cp.short_desc = '"+RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER+"' " +
    "and cp.clw_value in(select site_ref_num from "+tempTable+")) " +
    " group by site_ref_num having count(*) >1)";
    
    private static String checkDupAccount = "select account_ref_num from " +
    " (select clw_value account_ref_num,count(*) from clw_property where bus_entity_id in " +
    " (select bus_entity_id from clw_bus_entity where bus_entity_id in " +
    " (select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = ?) " +
    " and bus_entity_type_cd = '"+RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT+"') " +
    " and short_desc = '"+RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM+"' " +
    " and clw_value in(select account_ref_num from "+tempTable+") " +
    " group by clw_value having count(*) >1)";


	private static String populateSiteSql = "update "+tempTable+" t set t.site_id = (" +
                "select p.bus_entity_id from clw_property p where p.clw_value = t.site_ref_num and p.short_desc = '"+
                    RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER+"' and " +
                "   p.bus_entity_id in " +
                "   (select ba.bus_entity1_id from clw_bus_entity_assoc ba where ba.bus_entity_assoc_cd = '"+
                        RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' " +
                "      and ba.bus_entity2_id in (" +
                "           select baa.bus_entity1_id from clw_bus_entity_assoc baa where baa.bus_entity_assoc_cd = '"+
                                RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"'" +
                "            and baa.bus_entity2_id =  t.store_id )" +
                "))";

    private static String populateCurrentAccSql = "update "+tempTable+" t set t.current_account_id = (" +
            "select ba.bus_entity2_id from clw_bus_entity_assoc ba where ba.bus_entity1_id = t.site_id " +
            " and  ba.bus_entity_assoc_cd = '"+ RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"')";

    private static String populateNewAccSql = "update "+tempTable+" t set t.new_account_id = (" +
            "select p.bus_entity_id from clw_property p where p.clw_value = t.account_ref_num and p.short_desc = '"+
                    RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM+"'" +
                " and p.bus_entity_id in (" +
                    " select baa.bus_entity1_id from clw_bus_entity_assoc baa where baa.bus_entity_assoc_cd = '"+
                                        RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"'" +
                            " and baa.bus_entity2_id = t.store_id) )";

    protected Logger log = Logger.getLogger(InboundPollockSiteLoader.class);

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
            isEjb.processSite(conn,siteloader,siteActionAdd,siteActionChange,siteActionDelete,storeNum,tempTable);
        } catch (Exception e) {
            log.error("translate => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms", e);
            setFail(true);
            throw e;
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
            String versionCd  		= line.get(VERSION);
            String actionCd  		= line.get(ACTION);
            String storeID  		= line.get(STORE_ID);
            String storeName 		= line.get(STORE_NAME);
            String accountRefNum 	= line.get(ACCOUNT_REF_NUM);
            String accountName 		= line.get(ACCOUNT_NAME);
            String siteName 		= line.get(SITE_NAME);
            String siteRefNum 		= line.get(SITE_REF_NUM);
            String taxableCd 		= line.get(TAXABLE);
            String invShop 			= line.get(INV_SHOP);
            String buyerOrderGuide  = line.get(BUYER_ORDER_GUIDE);
            String firstName 		= line.get(FIRST_NAME);
            String lastName 		= line.get(LAST_NAME);
            String address1Cd 		= line.get(ADDRESS1);
            String address2Cd 		= line.get(ADDRESS2);
            String address3Cd 		= line.get(ADDRESS3);
            String address4Cd		= line.get(ADDRESS4);
            String cityCd 			= line.get(CITY);
            String stateCd 			= line.get(STATE);
            String postalCode 		= line.get(POSTAL_CODE);
            String countryCd 		= line.get(COUNTRY);
            String shipMessage 		= line.get(SHIP_MESSAGE);
            String ordComments 		= line.get(ORD_COMMENTS);
            String itemDisplayRestrict = "";
            if(line.size() == 23){
            	itemDisplayRestrict = "";
            
            }else{
            	itemDisplayRestrict = line.get(ITEM_DISPLAY_RESTRICT);
            }

            try {
            pstmt.setString(1, versionCd);
            pstmt.setString(2, actionCd);
            pstmt.setString(3, storeID);
            pstmt.setString(4, storeName);
            pstmt.setString(5, accountRefNum);
            pstmt.setString(6, accountName);
            pstmt.setString(7, siteName);
            pstmt.setString(8, siteRefNum);
            pstmt.setString(9, taxableCd);
            pstmt.setString(10, invShop);
            pstmt.setString(11, buyerOrderGuide);
            pstmt.setString(12, firstName);
            pstmt.setString(13, lastName);
            pstmt.setString(14, address1Cd);
            pstmt.setString(15, address2Cd);
            pstmt.setString(16, address3Cd);
            pstmt.setString(17, address4Cd);
            pstmt.setString(18, cityCd);
            pstmt.setString(19, stateCd);
            pstmt.setString(20, postalCode);
            pstmt.setString(21, countryCd);
            pstmt.setString(22, shipMessage);
            pstmt.setString(23, ordComments);
            pstmt.setString(24, itemDisplayRestrict);
            pstmt.setString(25, siteloader);


            pstmt.addBatch();
            if(i > 0 && i % 10000 == 0){
                 log.info("Calling execute batch at 10000 records");
                 pstmt.executeBatch();
            }

            }catch(Exception e){
			//	conn.rollback();
                e.printStackTrace();
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
        }
       
        //check if duplicate site exists
        pstmt = conn.prepareStatement(checkDupSite);
        pstmt.setInt(1, storeNum);
        rs = pstmt.executeQuery();
        
        boolean foundDuplicateSites=false;
    	StringBuffer duplicateSites = new StringBuffer();
    	while(rs.next()){
    		foundDuplicateSites = true;
    		duplicateSites.append(rs.getString(1));
    		duplicateSites.append(",");
    	}
    	
    	if (foundDuplicateSites ) {
    		throw new IllegalArgumentException("More than 1 Site found for same site_ref_num in store id: "+storeNum+" site_ref_num: "+duplicateSites);
    		//log.info("Could not find accounts using store id: "+storeNum+" and Dist reference number: "+missingAccounts);
    	} else{
    		log.info("CHECK 22222222222222222222222 Success Counttttt is zero continue = ");
    	}
    	pstmt.close();
    	
    	
    	//Check if duplicate Account exists
    	pstmt = conn.prepareStatement(checkDupAccount);
        pstmt.setInt(1, storeNum);
        rs = pstmt.executeQuery();
        
        boolean foundDuplicateAccounts=false;
    	StringBuffer duplicateAccounts = new StringBuffer();
    	while(rs.next()){
    		foundDuplicateAccounts = true;
    		duplicateAccounts.append(rs.getString(1));
    		duplicateAccounts.append(",");
    	}
    	
    	if (foundDuplicateAccounts) {
    		throw new IllegalArgumentException("More than 1 Account found for same account_ref_num in store id: "+storeNum+" acc_ref_num: "+duplicateAccounts);
    		//log.info("Could not find accounts using store id: "+storeNum+" and Dist reference number: "+missingAccounts);
    	} else{
    		log.info("CHECK 22222222222222222222222 Success Counttttt is zero continue = ");
    	}
    	pstmt.close();
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        
        // populate site id
        pstmt = conn.prepareStatement(populateSiteSql);
        n = pstmt.executeUpdate();
        pstmt.close();
        if (n != parsedData.size()){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedData.size()+" records.");
        }

        // populate current account id
        pstmt = conn.prepareStatement(populateCurrentAccSql);
        n = pstmt.executeUpdate();
        pstmt.close();
        if (n != parsedData.size()){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedData.size()+" records.");
        }


        // populate new account id
        pstmt = conn.prepareStatement(populateNewAccSql);
        n = pstmt.executeUpdate();
        pstmt.close();
        if (n != parsedData.size()){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedData.size()+" records.");
        }



        /*else{
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
