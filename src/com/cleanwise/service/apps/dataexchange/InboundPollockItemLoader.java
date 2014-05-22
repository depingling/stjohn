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


public class InboundPollockItemLoader extends InboundFlatFile {
private final static int VERSION_NUMBER = 0;
private final static int ACTION = 1;
private final static int ASSET = 2;
private final static int STORE_ID = 3;
private final static int STORE_NAME = 4;
private final static int DIST_SKU = 5;
private final static int MFG_SKU = 6;
private final static int MANUFACTURER = 7;
private final static int DISTRIBUTOR = 8;
private final static int PACK = 9;
private final static int UOM = 10;
private final static int CATEGORY_NAME = 11;
private final static int SUBCAT1 = 12;
private final static int SUBCAT2 = 13;
private final static int SUBCAT3 = 14;
private final static int MULTI_PRODUCT_NAME = 15;
private final static int ITEM_SIZE = 16;
private final static int LONG_DESCRIPTION = 17;
private final static int SHORT_DESCRIPTION = 18;
private final static int PRODUCT_UPC = 19;
private final static int PACK_UPC = 20;
private final static int UNSPSC_CODE = 21;
private final static int COLOR = 22;
private final static int SHIPPING_WEIGHT = 23;
private final static int WEIGHT_UNIT = 24;
private final static int NSN = 25;
private final static int SHIPPING_CUBIC_SIZE = 26;
private final static int HAZMAT = 27;
private final static int CERTIFIED_COMPANIES = 28;
private final static int IMAGE = 29;
private final static int MSDS = 30;
private final static int SPECIFICATION = 31;
private final static int ASSET_NAME = 32;
private final static int MODEL_NUMBER = 33;
private final static int ASSOCIATE_DOC1 = 34;
private final static int ASSOCIATE_DOC2 = 35;
private final static int ASSOCIATE_DOC3 = 36;
private final static int SORT_ORDER_MAIN = 37;
private final static int SORT_ORDER_1 = 38;
private final static int SORT_ORDER_2 = 39;
private final static int SORT_ORDER_3 = 40;


private final static String itemloader = "pollockItemLoader";
private final static String itemActionAdd = "A";
private final static String itemActionChange = "C";
private final static String itemActionDelete = "D";
private int storeNum = 0;
private Date runDate = new Date();
Connection conn = null;
private int tradingPartnerId = 0;

	private static final String insertSql = "insert into CLT_POLLACK_ITEM_LOADER(VERSION_NUMBER,ACTION,ASSET,STORE_ID,STORE_NAME,DIST_SKU,MFG_SKU,MANUFACTURER," +
	"DISTRIBUTOR,PACK,UOM,CATEGORY_NAME,SUBCAT1,SUBCAT2,SUBCAT3,MULTI_PRODUCT_NAME,ITEM_SIZE,LONG_DESCRIPTION,SHORT_DESCRIPTION,PRODUCT_UPC,PACK_UPC,UNSPSC_CODE," +
	"COLOR,SHIPPING_WEIGHT,WEIGHT_UNIT,NSN,SHIPPING_CUBIC_SIZE,HAZMAT,CERTIFIED_COMPANIES,IMAGE,MSDS,SPECIFICATION,ASSET_NAME,MODEL_NUMBER," +
	"ASSOCIATE_DOC1,ASSOCIATE_DOC2,ASSOCIATE_DOC3,ADD_BY,ADD_DATE,SORT_ORDER_MAIN,SUBCAT1_ORDER,SUBCAT2_ORDER,SUBCAT3_ORDER) " +
	"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?)";


    private static final String trimDataSql = "update CLT_POLLACK_ITEM_LOADER set " +
			"VERSION_NUMBER = TRIM(VERSION_NUMBER),ACTION = TRIM(ACTION),ASSET = TRIM(ASSET), " +
			"STORE_ID = TRIM(STORE_ID),STORE_NAME = TRIM(STORE_NAME),DIST_SKU = TRIM(DIST_SKU), " +
			"MFG_SKU = TRIM(MFG_SKU),MANUFACTURER = TRIM(MANUFACTURER),DISTRIBUTOR = TRIM(DISTRIBUTOR), " +
			"PACK = TRIM(PACK),UOM = TRIM(UOM),CATEGORY_NAME = TRIM(CATEGORY_NAME), " +
			"SUBCAT1 = TRIM(SUBCAT1),SUBCAT2 = TRIM(SUBCAT2),SUBCAT3 = TRIM(SUBCAT3), " +
			"MULTI_PRODUCT_NAME = TRIM(MULTI_PRODUCT_NAME),ITEM_SIZE = TRIM(ITEM_SIZE), " +
			"LONG_DESCRIPTION = TRIM(LONG_DESCRIPTION),SHORT_DESCRIPTION = TRIM(SHORT_DESCRIPTION), " +
			"PRODUCT_UPC = TRIM(PRODUCT_UPC),PACK_UPC = TRIM(PACK_UPC),UNSPSC_CODE = TRIM(UNSPSC_CODE), " +
			"COLOR = TRIM(COLOR),SHIPPING_WEIGHT = TRIM(SHIPPING_WEIGHT),WEIGHT_UNIT = TRIM(WEIGHT_UNIT), " +
			"NSN = TRIM(NSN),SHIPPING_CUBIC_SIZE = TRIM(SHIPPING_CUBIC_SIZE),HAZMAT = TRIM(HAZMAT), " +
			"CERTIFIED_COMPANIES = TRIM(CERTIFIED_COMPANIES),IMAGE = TRIM(IMAGE),MSDS = TRIM(MSDS), " +
			"SPECIFICATION = TRIM(SPECIFICATION),ASSET_NAME = TRIM(ASSET_NAME),MODEL_NUMBER = TRIM(MODEL_NUMBER)," +
			"ASSOCIATE_DOC1 = TRIM(ASSOCIATE_DOC1),ASSOCIATE_DOC2 = TRIM(ASSOCIATE_DOC2), ASSOCIATE_DOC3 = TRIM(ASSOCIATE_DOC3)," +
			"SORT_ORDER_MAIN =TRIM(SORT_ORDER_MAIN),SUBCAT1_ORDER = TRIM(SUBCAT1_ORDER)," +
			"SUBCAT2_ORDER = TRIM(SUBCAT2_ORDER),SUBCAT3_ORDER = TRIM(SUBCAT3_ORDER)";

    private static String selectStoreId = "select distinct STORE_ID,STORE_NAME from CLT_POLLACK_ITEM_LOADER";

    private static String verifyStoreId = "select bus_entity_id from clw_bus_entity where " +
    		"upper(short_desc) = upper(?) and bus_entity_type_cd = 'STORE' " +
    		"and bus_entity_status_cd = 'ACTIVE'";

	protected Logger log = Logger.getLogger(InboundPollockItemLoader.class);

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
    //        isEjb.processItem(conn,itemloader,itemActionAdd,itemActionChange,itemActionDelete,storeNum,tradingPartnerId);
            int tpid = getTranslator().getPartner().getTradingPartnerId();
            isEjb.processItem(conn,itemloader,itemActionAdd,itemActionChange,itemActionDelete,storeNum, tpid);
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
            String versiionNumber 						= line.get(VERSION_NUMBER);
			String actionCd 							= line.get(ACTION);
			String assetCd 								= line.get(ASSET);
			String storeID 								= line.get(STORE_ID);
			String storeName 							= line.get(STORE_NAME);
			String distSku 								= line.get(DIST_SKU);
			String mfgSku 								= line.get(MFG_SKU);
			String manufacturerName 					= line.get(MANUFACTURER);
			String distributorName 						= line.get(DISTRIBUTOR);
			String packCd 								= line.get(PACK);
			String uomCd 								= line.get(UOM);
			String categoryName 						= line.get(CATEGORY_NAME);
			String subcat1Cd 							= line.get(SUBCAT1);
			String subcat2Cd 							= line.get(SUBCAT2);
			String subcat3Cd 							= line.get(SUBCAT3);
			String multiProductName 					= line.get(MULTI_PRODUCT_NAME);
			String itemSize 							= line.get(ITEM_SIZE);
			String longDesc 							= line.get(LONG_DESCRIPTION);
			String shortDesc 							= line.get(SHORT_DESCRIPTION);
			String productUpc 							= line.get(PRODUCT_UPC);
			String packUpc 								= line.get(PACK_UPC);
			String unspscCode 							= line.get(UNSPSC_CODE);
			String colorCd 								= line.get(COLOR);
			String shippingWeight	 					= line.get(SHIPPING_WEIGHT);
			String weightUnit 							= line.get(WEIGHT_UNIT);
			String nsnCd 								= line.get(NSN);
			String shippingCubicSize 					= line.get(SHIPPING_CUBIC_SIZE);
			String hazmatCd 							= line.get(HAZMAT);
			String certifiedCompanies	 				= line.get(CERTIFIED_COMPANIES);
			String imageCd 								= line.get(IMAGE);
			String msdsCd 								= line.get(MSDS);
			String specificationCd 						= line.get(SPECIFICATION);
			String assetName 							= line.get(ASSET_NAME);
			String modelNumber 							= line.get(MODEL_NUMBER);
			String associateDoc1						= line.get(ASSOCIATE_DOC1);
			String associateDoc2						= line.get(ASSOCIATE_DOC2);
			String associateDoc3						= line.get(ASSOCIATE_DOC3);
			String sortOrderMain = "";
			String subCat1Order  = "";
			String subCat2Order  = "";
			String subCat3Order  = "";
			
            if(line.size() == 37){
            	sortOrderMain = "";
            }else{
            	sortOrderMain = line.get(SORT_ORDER_MAIN);
            }
            
            if(line.size() == 37){
            	subCat1Order = "";
            }else{
            	subCat1Order = line.get(SORT_ORDER_1);
            }
            
            if(line.size() == 37){
            	subCat2Order = "";
            }else{
            	subCat2Order = line.get(SORT_ORDER_2);
            }
            
            if(line.size() == 37){
            	subCat3Order = "";
            }else{
            	subCat3Order = line.get(SORT_ORDER_3);
            }

            try {

				pstmt.setString(1,versiionNumber);
				pstmt.setString(2,actionCd);
				pstmt.setString(3,assetCd);
				pstmt.setString(4,storeID);
				pstmt.setString(5,storeName);
				pstmt.setString(6,distSku);
				pstmt.setString(7,mfgSku);
				pstmt.setString(8,manufacturerName);
				pstmt.setString(9,distributorName);
				pstmt.setString(10,packCd);
				pstmt.setString(11,uomCd);
				pstmt.setString(12,categoryName);
				pstmt.setString(13,subcat1Cd);
				pstmt.setString(14,subcat2Cd);
				pstmt.setString(15,subcat3Cd);
				pstmt.setString(16,multiProductName);
				pstmt.setString(17,itemSize);
				pstmt.setString(18,longDesc);
				pstmt.setString(19,shortDesc);
				pstmt.setString(20,productUpc);
				pstmt.setString(21,packUpc);
				pstmt.setString(22,unspscCode);
				pstmt.setString(23,colorCd);
				pstmt.setString(24,shippingWeight);
				pstmt.setString(25,weightUnit);
				pstmt.setString(26,nsnCd);
				pstmt.setString(27,shippingCubicSize);
				pstmt.setString(28,hazmatCd);
				pstmt.setString(29,certifiedCompanies);
				pstmt.setString(30,imageCd);
				pstmt.setString(31,msdsCd);
				pstmt.setString(32,specificationCd);
				pstmt.setString(33,assetName);
				pstmt.setString(34,modelNumber);
				pstmt.setString(35,associateDoc1);
				pstmt.setString(36,associateDoc2);
				pstmt.setString(37,associateDoc3);
				pstmt.setString(38,itemloader);
				pstmt.setString(39,sortOrderMain);
				pstmt.setString(40,subCat1Order);
				pstmt.setString(41,subCat2Order);
				pstmt.setString(42,subCat3Order);
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
        		throw new Exception("Error Found Two distinct Store ID's and store name in File");
		}
        pstmt.close();
        if(storeNum == 0){
        	throw new Exception("Store ID is missing in File");
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
        tradingPartnerId = partner.getTradingPartnerId();
        log.info("TRADINGGGGGGGGGGGGGGGGGGGGG PARTNER ID = " + tradingPartnerId);



    }
}
