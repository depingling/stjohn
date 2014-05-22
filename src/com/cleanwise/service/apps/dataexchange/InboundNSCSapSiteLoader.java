

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

import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.Utility;

public class InboundNSCSapSiteLoader extends InboundNSCSapLoader { //InboundFlatFile {
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
    private final static int CATALOG_KEY = 23;

    private final static String siteloader = "NSCSapSiteLoader";
    private final static String siteActionAdd = "A";
    private final static String siteActionChange = "C";
    private final static String siteActionDelete = "D";
    private final static String tempTable = "clt_nsc_site_loader";
    private int storeNum = 0;
    private Date runDate = new Date();
    Connection conn = null;
    private static final String insertSql = "insert into " + tempTable + "(version,action,store_id,store_name," +
    	"account_ref_num,account_name," +
    	"site_name,site_ref_num,taxable,inventory_shopping_para," +
    	"buyer_order_guide,first_name,last_name,address1,address2,address3," +
    	"address4,city,state,postal_code,country,ship_message,order_guide_comments,catalog_key,add_by,add_date)" +
    	" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";

    private static final String trimDataSql = "update " + tempTable + " set version = trim(version),action = trim(action)," +
    	    "store_id = trim(store_id),store_name = trim(store_name),account_ref_num =trim(account_ref_num)," +
    	    "account_name = trim(account_name),site_name = trim(site_name),site_ref_num = trim(site_ref_num)," +
    		"taxable = trim(taxable),inventory_shopping_para = trim(inventory_shopping_para)," +
    		"buyer_order_guide = trim(buyer_order_guide),first_name = trim(first_name)," +
    		"last_name=trim(last_name),address1=trim(address1),address2=trim(address2),address3=trim(address3)," +
    		"address4=trim(address4),city=trim(city),state=trim(state),postal_code=trim(postal_code)," +
    		"country=decode(upper(trim(country)),'US','UNITED STATES',trim(country)),ship_message=trim(ship_message),order_guide_comments=trim(order_guide_comments),catalog_key=trim(catalog_key)";



    protected Logger log = Logger.getLogger(InboundNSCSapSiteLoader.class);

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("translate => BEGIN.");
        IntegrationServices isEjb = APIAccess.getAPIAccess()
        	.getIntegrationServicesAPI();
        try {
            PipeFileParser parser = new PipeFileParser();
            parser.parse(pIn);
         //   parser.cleanUpResult();
            storeNum = getStoreIdFromTradingPartner();
            List<List<String>> list = (List<List<String>>) parser.getParsedStrings();
            process(list);
            log.info("[InboundNSCSapSiteLoader].translate() => STORE ID = " + storeNum);
            isEjb.processNSCSapSite(conn,siteloader,siteActionAdd,siteActionChange,siteActionDelete,storeNum, tempTable);
            log.info("[InboundNSCSapSiteLoader].doPostProcessing() => END. Processed: " + list.size()+" sites. ");

        } catch (Exception e) {
             log.info("ERROR(s) :" + Utility.getInitErrorMsg(e));
             log.info("[InboundNSCSapSiteLoader].translate() => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms");

            setFail(true);
            throw e;
        }
        log.info("[InboundNSCSapSiteLoader].translate() => END.Process time at : "
                + (System.currentTimeMillis() - startTime) + " ms");
    }

    private void process(List<List<String>> parsedData) throws Exception {
        if (parsedData.size() == 0) {
          throw new Exception("^clw^Input file could be empty.^clw^");
        }
        if (conn == null){
          conn = getConnection();
	}
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        int recProcessed = 0;
      //NOTE we skip the first line if it is a header by setting i = 1
        for (int i = 0; parsedData != null && i < parsedData.size() && !isErrorLimit() ; i++) {
          List<String> line = (List<String>) parsedData.get(i);

          if (isValid (line, i)){
            processLine (line,  pstmt);
            recProcessed++;
            if(recProcessed%1000==0) log.info(recProcessed+" rows inserted");
            if(recProcessed%10000==0) pstmt.executeBatch();
          }
        }

        if (errorLines.size() > 0){
          processErrorMsgs();
          throw new Exception("^clw^"+getFormatedErrorMsgs()+"^clw^");
        }
        int[] nn = pstmt.executeBatch();
        pstmt.close();

        pstmt = conn.prepareStatement(trimDataSql);
        int n = pstmt.executeUpdate();
        if (n != recProcessed){
          throw new Exception("^clw^"+"Filling of the Temporary table is not completed"+"^clw^");
        }

/*
        //--------------------------------------------------//
         pstmt = null;
         String copySQL = "insert into TCLT_NSC_SITE_LOADER select * from "+ tempTable;
         pstmt = conn.prepareStatement(copySQL);
         pstmt.executeUpdate();
         pstmt.close();
         //----------------------------------------------------//
*/

//        getStoreIdFromFile(pstmt);
	pstmt.close();

/*        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception(errorMessage);
        }
*/
   }

   private void processLine (List<String> line, PreparedStatement pstmt) throws Exception {
     String versionCd = line.get(VERSION);
     String actionCd = line.get(ACTION);
     String storeID = line.get(STORE_ID);
     String storeName = line.get(STORE_NAME);
     String accountRefNum = line.get(ACCOUNT_REF_NUM);
     String accountName = line.get(ACCOUNT_NAME);
     String siteName = line.get(SITE_NAME);
     String siteRefNum = line.get(SITE_REF_NUM);
     String taxableCd = line.get(TAXABLE);
     String invShop = line.get(INV_SHOP);
     String buyerOrderGuide = line.get(BUYER_ORDER_GUIDE);
     String firstName = line.get(FIRST_NAME);
     String lastName = line.get(LAST_NAME);
     String address1Cd = line.get(ADDRESS1);
     String address2Cd = line.get(ADDRESS2);
     String address3Cd = line.get(ADDRESS3);
     String address4Cd = line.get(ADDRESS4);
     String cityCd = line.get(CITY);
     String stateCd = line.get(STATE);
     String postalCode = line.get(POSTAL_CODE);
     String countryCd = line.get(COUNTRY);
     String shipMessage = line.get(SHIP_MESSAGE);
     String ordComments = line.get(ORD_COMMENTS);
     String catalogKey = line.get(CATALOG_KEY);
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
       pstmt.setString(24, catalogKey);
       pstmt.setString(25, siteloader);

       pstmt.addBatch();
     } catch(Exception e){
        conn.close();
        conn = null;
        throw e;

      }
   }

   private boolean isValid(List<String> lineValues, int line){
      boolean valid = true;
      boolean ok = true;
       valid &= checkRequired (lineValues.get(VERSION), "Version Number", line);

       String action = lineValues.get(ACTION);
       ok = checkRequired (action, "Action", line);
       if (ok && (action.trim().length()>1 || !"ACD".contains(action.trim().toUpperCase()))){
           addError("Incorrect Action =" + action+" . Should be A|C|D" ,line);
           ok = false;
       }
       valid &= ok;

       ok = checkType(lineValues.get(STORE_ID), "Store Id", TYPE.INTEGER, true, line);
       valid &=ok;
       if (ok && Integer.parseInt(lineValues.get(STORE_ID)) != storeNum) {
         addError("Incorrect STORE ID value = '" + Integer.parseInt(lineValues.get(STORE_ID)) + "'. Trading Partner associated with store Id =" + storeNum +". " , line);
         valid = false;
       }
       valid &= checkRequired(lineValues.get(STORE_NAME), "Store Name", line);
 //      valid &= checkRequired(lineValues.get(ACCOUNT_NAME), "Account Name", line);
       valid &= checkRequired(lineValues.get(ACCOUNT_REF_NUM), "Account Ref Num", line);
 //!!! valid &= checkRequired((String)lineValues.get(CATALOG_KEY), "Catalog Key", line);

       String siteName = lineValues.get(SITE_NAME);
       ok = checkRequired(siteName, "Site Name", line);
       if (ok && siteName.trim().length() > 30){
         addError("Site Name: '" + siteName+"' is too long. Max length is 30 char" , line);
         ok = false;
       }
       valid &= ok;

       valid &= checkRequired(lineValues.get(SITE_REF_NUM), "Sire Ref Num", line);
       valid &= checkType(lineValues.get(TAXABLE), "Taxable", TYPE.TEXT, true, line);
       valid &= checkType(lineValues.get(INV_SHOP), "Enable Inventory Shopping", TYPE.BOOLEAN,true, line);

       valid &= checkType(lineValues.get(BUYER_ORDER_GUIDE), "Share buyer order guides", TYPE.BOOLEAN, true, line);
       valid &= checkRequired(lineValues.get(ADDRESS1), "Address1", line);
       valid &= checkRequired(lineValues.get(CITY), "City", line);
       valid &= checkRequired(lineValues.get(STATE), "State", line);
       valid &= checkRequired(lineValues.get(POSTAL_CODE), "Postal Code", line);
       valid &= checkRequired(lineValues.get(COUNTRY), "Country", line);

      return valid;
    }

}
