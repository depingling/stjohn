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


public class InboundPollockOrderGuideLoader extends InboundFlatFile {
	private final static int VERSION_NUMBER = 0;
	private final static int STORE_ID = 1;
    private final static int STORE_NAME = 2;
    private final static int ACCOUNT_REF_NUM = 3;
    private final static int ACCOUNT_NAME = 4;
    private final static int SITE_NAME = 5;
    private final static int SITE_REF_NUM = 6;
	private final static int CATALOG_ID = 7;
    private final static int ORDER_GUIDE_NAME = 8;
    private final static int ORDER_GUIDE_TYPE = 9;
    private final static int DIST_SKU = 10;

private final static String orderguideloader = "pollockOrderGuideLoader";
private int storeNum = 0;
private Date runDate = new Date();
Connection conn = null;

	private static final String insertSql = "insert into CLT_POLLARD_ORDER_GUIDE_LOADER(VERSION_NUMBER,STORE_ID,STORE_NAME,ACCOUNT_REF_NUM,ACCOUNT_NAME,SITE_NAME,SITE_REF_NUM," +
											"CATALOG_ID,ORDER_GUIDE_NAME,ORDER_GUIDE_TYPE,DIST_SKU,ADD_BY,ADD_DATE) " +
	"values(?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";


    private static final String trimDataSql = "update CLT_POLLARD_ORDER_GUIDE_LOADER set " +
			"VERSION_NUMBER = TRIM(VERSION_NUMBER),STORE_ID = TRIM(STORE_ID),STORE_NAME = TRIM(STORE_NAME),ACCOUNT_REF_NUM = TRIM(ACCOUNT_REF_NUM)," +
			"ACCOUNT_NAME = TRIM(ACCOUNT_NAME),SITE_NAME = TRIM(SITE_NAME),SITE_REF_NUM = TRIM(SITE_REF_NUM),CATALOG_ID = TRIM(CATALOG_ID)," +
			"ORDER_GUIDE_NAME = TRIM(ORDER_GUIDE_NAME),ORDER_GUIDE_TYPE = TRIM(ORDER_GUIDE_TYPE),DIST_SKU = TRIM(DIST_SKU)";

    private static String selectStoreId = "select distinct STORE_ID from CLT_POLLARD_ORDER_GUIDE_LOADER";

	protected Logger log = Logger.getLogger(InboundPollockOrderGuideLoader.class);

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("translate => BEGIN.");
        IntegrationServices isEjb = APIAccess.getAPIAccess()
        	.getIntegrationServicesAPI();
        try {
            PipeFileParser parser = new PipeFileParser();
            parser.setSource(pIn);
            int parsedDataSize = 0;
            while(1==1) {
                List<List<String>> list = (List<List<String>>)parser.parse(10000);
                if (!list.isEmpty()) {
                    parsedDataSize = parsedDataSize + list.size();
                    process(list);
                } else {
                    break;
                }
            }
            parser.cleanUp();
            verify(parsedDataSize);
            log.info("parsedDataSize: " + parsedDataSize);

            isEjb.processOrderGuide(conn,orderguideloader,storeNum);
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
        //log.info("Getting Connectionnnnnnnnnn:= " + conn);
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        //loadTempTable(parsedData);
        log.info("SQL insertSql in Temp Table = " +  insertSql);
      //NOTE we skip the first line if it is a header by setting i = 1
        for (int i = 0; parsedData != null && i < parsedData.size(); i++) {
            List<String> line = (List<String>) parsedData.get(i);
            String versionCd = line.get(VERSION_NUMBER);
			String storeId = line.get(STORE_ID);
			String storeName = line.get(STORE_NAME);
			String accountRefNum = line.get(ACCOUNT_REF_NUM);
			String accountName = line.get(ACCOUNT_NAME);
			String siteName = line.get(SITE_NAME);
			String siteRefNum = line.get(SITE_REF_NUM);
			String catalogId = line.get(CATALOG_ID);
			String orderGuideName = line.get(ORDER_GUIDE_NAME);
			String orderGuideType = line.get(ORDER_GUIDE_TYPE);
			String distSku = line.get(DIST_SKU);
			//log.info("SSSSSSSSSSSSSSSSSSSSSSSSSstoreID" + storeId);

            try {


				pstmt.setString(1,versionCd);
				pstmt.setString(2,storeId);
				pstmt.setString(3,storeName);
				pstmt.setString(4,accountRefNum);
				pstmt.setString(5,accountName);
				pstmt.setString(6,siteName);
				pstmt.setString(7,siteRefNum);
				pstmt.setString(8,catalogId);
				pstmt.setString(9,orderGuideName);
				pstmt.setString(10,orderGuideType);
				pstmt.setString(11,distSku);
				pstmt.setString(12,orderguideloader);

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
        pstmt.close();
    }

    private void verify(int parsedDataSize) throws Exception {
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(trimDataSql);
        int n = pstmt.executeUpdate();
        log.info("3333333333333333333333333333333333333333333333333333333333333333333");
        // verify that all records are loaded into temporary table
        if (n != parsedDataSize){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedDataSize+" records.");
        }
        pstmt = conn.prepareStatement(selectStoreId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
                        storeNum = Integer.parseInt(rs.getString(1));

                        if (rs.next())
                        throw new Exception("Error Found Two distinct Store ID's in File");
                }
        if(storeNum == 0){
                throw new Exception("Store ID is missing in File");
        }
        log.info("4444444444444444444444444444444444444444444444444444444444444444444444444444");
                pstmt.close();

        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception(errorMessage);
        }
//        IntegrationServices isEjb = APIAccess.getAPIAccess()
//                .getIntegrationServicesAPI();
//        IntegrationRequestsVector irv = new IntegrationRequestsVector();
//        int tradingPartnerId = partner.getTradingPartnerId();


    }

}
