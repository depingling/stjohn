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


public class InboundPollockCatalogAssocLoader extends InboundNSCSapLoader {//InboundFlatFile {
			private final static int VERSION = 0;
			private final static int STORE_ID = 1;
			private final static int STORE_NAME = 2;
			private final static int ACCOUNT_REF_NUM = 3;
			private final static int ACCOUNT_NAME = 4;
			private final static int SITE_NAME = 5;
			private final static int SITE_REF_NUM = 6;
			private final static int CATALOG_KEY = 7;
                        private final static int BUY_LIST= 8;
                        private final static int PRICE_LIST_1= 9;
                        private final static int PRICE_LIST_2= 10;
                        private final static int PROPRIETARY_LIST= 11;

                        protected int FILE_LINE_SIZE = 8; //12;  // Number of fields in the Line
                        protected int FILE_LINE_SIZE_2 = 12;  // Number of fields in the Line (Version 2)
			private final static String catalogassocloader = "pollockCatalogAssocLoader";
			private int storeNum = 0;
			private Date runDate = new Date();
			Connection conn = null;

    private static final String insertSql = "INSERT INTO CLT_POLLOCK_CATALOG_ASSOC(VERSION,STORE_ID,STORE_NAME,ACCOUNT_REF_NUM,ACCOUNT_NAME,SITE_NAME," +
											"SITE_REF_NUM,CATALOG_KEY,PRICE_LIST_1, PRICE_LIST_2, PROPRIETARY_LIST, BUY_LIST,ADD_BY,ADD_DATE) " +
											"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";


    private static final String trimDataSql = "update CLT_POLLOCK_CATALOG_ASSOC set " +
						"VERSION = trim(VERSION),STORE_ID = trim(STORE_ID),STORE_NAME = trim(STORE_NAME)," +
 					    "ACCOUNT_REF_NUM = trim(ACCOUNT_REF_NUM),ACCOUNT_NAME = trim(ACCOUNT_NAME),SITE_NAME = trim(SITE_NAME)," +
						"SITE_REF_NUM = trim(SITE_REF_NUM), CATALOG_KEY = trim(CATALOG_KEY), PRICE_LIST_1 = trim(PRICE_LIST_1)"+
                                                ", PRICE_LIST_2 = trim(PRICE_LIST_2), PROPRIETARY_LIST = trim(PROPRIETARY_LIST), BUY_LIST = trim(BUY_LIST)";

    private static String selectStoreId = "select distinct STORE_ID from CLT_POLLOCK_CATALOG_ASSOC";
    private HashMap siteToCatalogMap = new  HashMap();
    protected Logger log = Logger.getLogger(InboundPollockCatalogAssocLoader.class);
	private int versionNum = 1; //default

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("translate => BEGIN.");
        IntegrationServices isEjb = APIAccess.getAPIAccess()
        	.getIntegrationServicesAPI();
        try {
            PipeFileParser parser = new PipeFileParser();
            parser.parse(pIn);
            storeNum = getStoreIdFromTradingPartner();

         //   parser.cleanUpResult();
            List<List<String>> list = (List<List<String>>) parser.getParsedStrings();
            process(list);
            log.info("STORE ID =====================" + storeNum);
            isEjb.processCatalogAssoc(conn,catalogassocloader,storeNum);
        } catch (Exception e) {
          log.info("ERROR(s) :" + Utility.getInitErrorMsg(e));
          log.info("translate => FAILED.Process time at : "
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
      log.info("process() ====> Begin.");
        if (parsedData.size() == 0)
        	throw new Exception("Input file could be empty.");
        //checkLine(parsedData);

        if (conn == null){
			conn = getConnection();
		}
        //log.info("Getting Connectionnnnnnnnnn:= " + conn);
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        //loadTempTable(parsedData);
      //  log.info("SQL insertSql in Temp Table = " +  insertSql);
        //NOTE we skip the first line if it is a header by setting i = 1
        //Analyze version number
        List<String> firstLine = (List<String>) parsedData.get(0);
        String version = firstLine.get(VERSION);
		try {
			versionNum = Integer.parseInt(version);
		} catch (Exception exc) {
		}
	int recProcessed = 0;
        for (int i = 0; parsedData != null && i < parsedData.size() && !isErrorLimit() ; i++) {
            List<String> line = (List<String>) parsedData.get(i);
            if (line != null && isValidLineSize(line.size(), i) && isValid (line, i)){
              processLine (line,  pstmt);
              recProcessed++;
              if(recProcessed%10000==0) {
                log.info("Calling execute batch at 10000 records");
                pstmt.executeBatch();
              }
            }
        }
        checkUniqueCatalogForSite ();
        if (errorLines.size() > 0 || errorMsgs.size() > 0){
           processErrorMsgs();
           throw new Exception("^clw^"+getFormatedErrorMsgs()+"^clw^");
         }

       pstmt.executeBatch();
       pstmt.close();

       pstmt = conn.prepareStatement(trimDataSql);
        int n = pstmt.executeUpdate();
 //       log.info("process() ====>nn="+ nn);
        pstmt.close();

        if (n != parsedData.size()){
          throw new Exception("^clw^"+"Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedData.size()+" records."+"^clw^");
        }

        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception("^clw^"+errorMessage+"^clw^");
        }

		log.info("process() ====> End.");
    }

    private void processLine (List line, PreparedStatement pstmt) throws Exception {
      String versionCd				= (String)line.get(VERSION);
      String storeId				= (String)line.get(STORE_ID);
      String storeName				= (String)line.get(STORE_NAME);
      String accountRefNum			= (String)line.get(ACCOUNT_REF_NUM);
      String accountName			= (String)line.get(ACCOUNT_NAME);
      String siteName				= (String)line.get(SITE_NAME);
      String siteRefNum				= (String)line.get(SITE_REF_NUM);
      String catalogKey				= (String)line.get(CATALOG_KEY);
      String priceList1				= (versionNum==2)?((String)line.get(PRICE_LIST_1)):null;
      String priceList2				= (versionNum==2)?((String)line.get(PRICE_LIST_2)):null;
      String proprietaryList	    = (versionNum==2)?((String)line.get(PROPRIETARY_LIST)):null;
      String buyList				= (versionNum==2)?((String)line.get(BUY_LIST)):null;


      try {
        pstmt.setString(1, versionCd);
        pstmt.setString(2, storeId);
        pstmt.setString(3, storeName);
        pstmt.setString(4, accountRefNum);
        pstmt.setString(5, accountName);
        pstmt.setString(6, siteName);
        pstmt.setString(7, siteRefNum);
        pstmt.setString(8, catalogKey);
        pstmt.setString(9, priceList1);
        pstmt.setString(10, priceList2);
        pstmt.setString(11, proprietaryList);
        pstmt.setString(12, buyList);
        pstmt.setString(13, catalogassocloader);
        pstmt.addBatch();

      }
      catch (Exception e) {
        //	conn.rollback();
        conn.close();
        conn = null;
        throw e;
      }
    }

    protected int getFileLineSize() {
      if(versionNum==1) return FILE_LINE_SIZE;
      return FILE_LINE_SIZE_2;
    }


    private boolean isValid(List<String> lineValues, int line){
	   if(versionNum==1) return isValid1(lineValues, line);
	   return isValid2(lineValues, line);
	}

    private boolean isValid1(List<String> lineValues, int line){
      boolean valid = true;
      boolean ok = true;
      String version = lineValues.get(VERSION);
      ok = checkType (version, "Version Number", TYPE.TEXT,true,line);
      if (ok && !(version.trim().equals("1"))){
        addError("Version Number:"+ version +". Should be 1", line);
        ok = false;
      }
      valid &= ok;

      ok = checkType(lineValues.get(STORE_ID), "Store Id", TYPE.INTEGER, true, line);
      valid &=ok;
      if (ok && Integer.parseInt(lineValues.get(STORE_ID)) != storeNum) {
        addError("Incorrect STORE ID value = '" + Integer.parseInt(lineValues.get(STORE_ID)) +
		  "'. Trading Partner associated with store Id =" + storeNum+". ", line);
        valid = false;
      }

       valid &= checkRequired(lineValues.get(STORE_NAME), "Store Name", line);
 //      valid &= checkRequired(lineValues.get(ACCOUNT_NAME), "Account Name", line);
       valid &= checkRequired(lineValues.get(ACCOUNT_REF_NUM), "Account Ref Num", line);

 //      valid &= checkType(lineValues.get(SITE_NAME), "Site Name", TYPE.TEXT, true, line);
       valid &= checkRequired(lineValues.get(SITE_REF_NUM), "Site Ref Num", line);

       valid &= checkType(lineValues.get(CATALOG_KEY), "Catalog Key", TYPE.TEXT, false, line);
       // Check: All Catalog Id, Price List (level1), Price List (level 2), Proprietary List and Buy List are empty
       ok = Utility.isSet(lineValues.get(CATALOG_KEY));
       if (!ok){
         addError("Catalog Id is empty", line);
         valid = false;
       }

       if (valid){
         HashSet catalogSet= null;
         String site = lineValues.get(SITE_REF_NUM)+"/"+lineValues.get(ACCOUNT_REF_NUM);
         if (siteToCatalogMap.containsKey(site)) {
           catalogSet = (HashSet) siteToCatalogMap.get(site);
         }
         else {
           catalogSet = new HashSet();
           siteToCatalogMap.put(site, catalogSet);
         }
         if (Utility.isSet(lineValues.get(CATALOG_KEY))) {
           catalogSet.add(lineValues.get(CATALOG_KEY));
         }
       }
//       log.info("isValid() ====> valid ="+ valid + ", line=" + line);
       return valid;
     }

	 private boolean isValid2(List<String> lineValues, int line){
      boolean valid = true;
      boolean ok = true;
      String version = lineValues.get(VERSION);
      ok = checkType (version, "Version Number", TYPE.TEXT,true,line);
      if (ok && !(version.trim().equals("1")|| version.trim().equals("2"))){
        addError("Version Number:"+ version +". Should be 1 or 2.", line);
        ok = false;
      }
      valid &= ok;

      ok = checkType(lineValues.get(STORE_ID), "Store Id", TYPE.INTEGER, true, line);
      valid &=ok;
      if (ok && Integer.parseInt(lineValues.get(STORE_ID)) != storeNum) {
        addError("Incorrect STORE ID value = '" + Integer.parseInt(lineValues.get(STORE_ID)) + "'. Trading Partner associated with store Id =" + storeNum+". ", line);
        valid = false;
      }

       valid &= checkRequired(lineValues.get(STORE_NAME), "Store Name", line);
 //      valid &= checkRequired(lineValues.get(ACCOUNT_NAME), "Account Name", line);
       valid &= checkRequired(lineValues.get(ACCOUNT_REF_NUM), "Account Ref Num", line);

 //      valid &= checkType(lineValues.get(SITE_NAME), "Site Name", TYPE.TEXT, true, line);
       valid &= checkRequired(lineValues.get(SITE_REF_NUM), "Site Ref Num", line);

       valid &= checkType(lineValues.get(CATALOG_KEY), "Catalog Key", TYPE.TEXT, false, line);
       valid &= checkType(lineValues.get(PRICE_LIST_1), "Price List(Level 1)", TYPE.TEXT, false, line);
       valid &= checkType(lineValues.get(PRICE_LIST_2), "Price List(Level 2)", TYPE.TEXT, false, line);
       valid &= checkType(lineValues.get(PROPRIETARY_LIST), "Proprietary List", TYPE.TEXT, false, line);
       valid &= checkType(lineValues.get(BUY_LIST), "Buy List", TYPE.TEXT, false, line);
       // Check: All Catalog Id, Price List (level1), Price List (level 2), Proprietary List and Buy List are empty
       ok = Utility.isSet(lineValues.get(CATALOG_KEY) ) ||
                Utility.isSet(lineValues.get(PRICE_LIST_1) ) ||
                Utility.isSet(lineValues.get(PRICE_LIST_2) ) ||
                Utility.isSet(lineValues.get(PROPRIETARY_LIST) ) ||
                Utility.isSet(lineValues.get(BUY_LIST) );
       if (!ok){
         addError("All Catalog Id, Price List (level1), Price List (level 2), Proprietary List and Buy List are empty", line);
         valid = false;
       }
       //Check: Both Catalog ID and Buy List are not empty
       boolean b = Utility.isSet(lineValues.get(CATALOG_KEY) ) && Utility.isSet(lineValues.get(BUY_LIST) );
       if (b){
           addError("Both Catalog Key and Buy List are not empty. Catalog Key ="+lineValues.get(CATALOG_KEY) +", Buy List ="+lineValues.get(BUY_LIST), line);
           valid = false;
       }
       //Check: Both Catalog ID and Buy List are empty
      b = !Utility.isSet(lineValues.get(CATALOG_KEY) ) && !Utility.isSet(lineValues.get(BUY_LIST) );
      if (b){
          addError("Both Catalog Key and Buy List are not provided.", line);
          valid = false;
      }
      if (valid){
         HashSet catalogSet= null;
         String site = lineValues.get(SITE_REF_NUM)+"/"+lineValues.get(ACCOUNT_REF_NUM);
         if (siteToCatalogMap.containsKey(site)) {
           catalogSet = (HashSet) siteToCatalogMap.get(site);
         }
         else {
           catalogSet = new HashSet();
           siteToCatalogMap.put(site, catalogSet);
         }
         if (Utility.isSet(lineValues.get(CATALOG_KEY))) {
           catalogSet.add(lineValues.get(CATALOG_KEY));
         }
         if (Utility.isSet(lineValues.get(BUY_LIST))) {
           catalogSet.add(lineValues.get(BUY_LIST));
         }

       }
//       log.info("isValid() ====> valid ="+ valid + ", line=" + line);
       return valid;
     }
     private void checkUniqueCatalogForSite () {
       if (this.siteToCatalogMap != null){
         Set keys = siteToCatalogMap.keySet();
         for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
           String site = (String) iter.next();
           HashSet catalogs = (HashSet)siteToCatalogMap.get(site);
           if (catalogs != null && catalogs.size()> 1){
             addError("Different catalogs(buy lists) defined for site. Site & Account Ref Nums="+ site+ ", Catalogs = "+catalogs.toString() );
           }
         }
       }
     }

}
