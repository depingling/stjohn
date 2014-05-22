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


public class InboundPollockCatalogLoader extends InboundFlatFile {
	private final static int VERSION_NUMBER = 0;
	private final static int STORE_ID = 1;
	private final static int STORE_NAME = 2;
	private final static int ACCOUNT_NAME = 3;
	private final static int ACCOUNT_REF_NUM = 4;
	private final static int CATALOG_KEY = 5;
	private final static int CATALOG_NAME = 6;
	private final static int ORDER_GUIDE_NAME = 7;
	private final static int LOCALE = 8;
	private final static int DIST_SKU = 9;
	private final static int DISTRIBUTOR = 10;
	private final static int PACK = 11;
	private final static int UOM = 12;
	private final static int COST = 13;
	private final static int PRICE = 14;
	private final static int CUSTOMER_SKU = 15;
	private final static int SERVICE_CODE = 16;
	private final static int CATEGORY_NAME = 17;
	private final static int CATEGORY_ORDER = 18;
	private final static int SUBCAT1 = 19;
	private final static int SUBCAT1_ORDER = 20;
	private final static int SUBCAT2 = 21;
	private final static int SUBCAT2_ORDER = 22;
	private final static int SUBCAT3 = 23;
	private final static int SUBCAT3_ORDER = 24;
	private final static int ORDER_GUIDE = 25;
	private final static int COST_CENTER_KEY = 26;
	private final static int FREIGHT_TABLE_KEY = 27;

	private final static String tempTable = "CLT_POLLOCK_CATALOG_LOADER";
private final static String catalogloader= "pollockCatalogLoader";
private int storeNum = 0;
private Date runDate = new Date();
Connection conn = null;

	private static final String insertSql = "insert into CLT_POLLOCK_CATALOG_LOADER(VERSION_NUMBER,STORE_ID,STORE_NAME,ACCOUNT_NAME,ACCOUNT_REF_NUM,CATALOG_KEY,CATALOG_NAME, " +
											"ORDER_GUIDE_NAME,LOCALE,DIST_SKU,DISTRIBUTOR,PACK,UOM,COST,PRICE,CUSTOMER_SKU,SERVICE_CODE,CATEGORY_NAME,CATEGORY_ORDER," +
											"SUBCAT1,SUBCAT1_ORDER,SUBCAT2,SUBCAT2_ORDER,SUBCAT3,SUBCAT3_ORDER,ORDER_GUIDE,COST_CENTER_KEY,FREIGHT_TABLE_KEY,ADD_BY,ADD_DATE) " +
	"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";


    private static final String trimDataSql = "update CLT_POLLOCK_CATALOG_LOADER set " +
			"VERSION_NUMBER	= TRIM(VERSION_NUMBER),STORE_ID	= TRIM(STORE_ID),STORE_NAME = TRIM(STORE_NAME)," +
			"ACCOUNT_NAME = TRIM(ACCOUNT_NAME),ACCOUNT_REF_NUM = TRIM(ACCOUNT_REF_NUM),CATALOG_KEY = TRIM(CATALOG_KEY)," +
			"CATALOG_NAME = TRIM(CATALOG_NAME),ORDER_GUIDE_NAME = TRIM(ORDER_GUIDE_NAME),LOCALE = TRIM(LOCALE)," +
			"DIST_SKU = TRIM(DIST_SKU),DISTRIBUTOR = TRIM(DISTRIBUTOR),PACK = TRIM(PACK),UOM = TRIM(UOM)," +
			"COST = TRIM(COST),PRICE	= TRIM(PRICE),CUSTOMER_SKU = TRIM(CUSTOMER_SKU),SERVICE_CODE = TRIM(SERVICE_CODE)," +
			"CATEGORY_NAME = TRIM(CATEGORY_NAME),CATEGORY_ORDER = TRIM(CATEGORY_ORDER),SUBCAT1 = TRIM(SUBCAT1)," +
			"SUBCAT1_ORDER = TRIM(SUBCAT1_ORDER),SUBCAT2 = TRIM(SUBCAT2),SUBCAT2_ORDER = TRIM(SUBCAT2_ORDER)," +
			"SUBCAT3 = TRIM(SUBCAT3),SUBCAT3_ORDER = TRIM(SUBCAT3_ORDER),ORDER_GUIDE	= TRIM(ORDER_GUIDE)," +
			"COST_CENTER_KEY = TRIM(COST_CENTER_KEY),FREIGHT_TABLE_KEY = TRIM(FREIGHT_TABLE_KEY)";

    private static String selectStoreId = "select distinct STORE_ID,STORE_NAME from CLT_POLLOCK_CATALOG_LOADER";

    private static String verifyStoreId = "select bus_entity_id from clw_bus_entity where " +
	"upper(short_desc) = upper(?) and bus_entity_type_cd = 'STORE' " +
	"and bus_entity_status_cd = 'ACTIVE'";

	protected Logger log = Logger.getLogger(InboundPollockCatalogLoader.class);

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
            isEjb.processCatalog(conn,catalogloader,storeNum,tempTable);
        } catch (Exception e) {
        	log.info("ERROR(s) :" + Utility.getInitErrorMsg(e));
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
        //log.info("Getting Connectionnnnnnnnnn:= " + conn);
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        //loadTempTable(parsedData);
        log.info("SQL insertSql in Temp Table = " +  insertSql);
        log.info("parsedData.size() = "+parsedData.size());
      //NOTE we skip the first line if it is a header by setting i = 1
        for (int i = 0; parsedData != null && i < parsedData.size(); i++) {
            List<String> line = (List<String>) parsedData.get(i);
             String versionCd = line.get(VERSION_NUMBER);
			 String storeId = line.get(STORE_ID);
			 String storeName = line.get(STORE_NAME);
			 String accountName = line.get(ACCOUNT_NAME);
			 String accountRefNum = line.get(ACCOUNT_REF_NUM);
			 String catalogKey = line.get(CATALOG_KEY);
			 String catalogName = line.get(CATALOG_NAME);
			 String orderGuideName = line.get(ORDER_GUIDE_NAME);
			 String locale = line.get(LOCALE);
			 String distSku = line.get(DIST_SKU);
			 String distributor = line.get(DISTRIBUTOR);
			 String pack = line.get(PACK);
			 String uom = line.get(UOM);
			 String cost = line.get(COST);
			 String price = line.get(PRICE);
			 String customerSku = line.get(CUSTOMER_SKU);
			 String serviceCode = line.get(SERVICE_CODE);
			 String categoryName = line.get(CATEGORY_NAME);
			 String categoryOrder = line.get(CATEGORY_ORDER);
			 String subCat1= line.get(SUBCAT1);
			 String subCat1Order = line.get(SUBCAT1_ORDER);
			 String subCat2 = line.get(SUBCAT2);
			 String subCat2Order = line.get(SUBCAT2_ORDER);
			 String subCat3 = line.get(SUBCAT3);
			 String subCat3Order = line.get(SUBCAT3_ORDER);
			 String orderGuide = line.get(ORDER_GUIDE);
			 String costCenterKey = line.get(COST_CENTER_KEY);
			 String freightTableKey = line.get(FREIGHT_TABLE_KEY);
			//log.info("SSSSSSSSSSSSSSSSSSSSSSSSSstoreID" + storeId);

            try {


				pstmt.setString(1,versionCd);
				pstmt.setString(2,storeId);
				pstmt.setString(3,storeName);
				pstmt.setString(4,accountName);
				pstmt.setString(5,accountRefNum);
				pstmt.setString(6,catalogKey);
				pstmt.setString(7,catalogName);
				pstmt.setString(8,orderGuideName);
				pstmt.setString(9,locale);
				pstmt.setString(10,distSku);
				pstmt.setString(11,distributor);
				pstmt.setString(12,pack);
				pstmt.setString(13,uom);
				pstmt.setString(14,cost);
				pstmt.setString(15,price);
				pstmt.setString(16,customerSku);
				pstmt.setString(17,serviceCode);
				pstmt.setString(18,categoryName);
				pstmt.setString(19,categoryOrder);
				pstmt.setString(20,subCat1);
				pstmt.setString(21,subCat1Order);
				pstmt.setString(22,subCat2);
				pstmt.setString(23,subCat2Order);
				pstmt.setString(24,subCat3);
				pstmt.setString(25,subCat3Order);
				pstmt.setString(26,orderGuide);
				pstmt.setString(27,costCenterKey);
				pstmt.setString(28,freightTableKey);
				pstmt.setString(29,catalogloader);

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
        log.info("11111111111111111111111111111111111111111111111111111111111111111");
        pstmt.executeBatch();
        log.info("222222222222222222222222222222222222222222222222222222222222222222");
        pstmt = conn.prepareStatement(trimDataSql);
        int n = pstmt.executeUpdate();
        log.info("3333333333333333333333333333333333333333333333333333333333333333333");
        pstmt.close();
        // verify that all records are loaded into temporary table
        if (n != parsedData.size()){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedData.size()+" records.");
        }
        pstmt = conn.prepareStatement(selectStoreId);
        ResultSet rs = pstmt.executeQuery();
        String storeNam = "";
        while(rs.next()){
			storeNum = Integer.parseInt(rs.getString(1));
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

        log.info("4444444444444444444444444444444444444444444444444444444444444444444444444444");


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
