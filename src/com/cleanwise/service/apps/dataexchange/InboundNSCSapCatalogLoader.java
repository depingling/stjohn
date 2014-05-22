
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

import com.cleanwise.service.api.util.Utility;
import org.apache.log4j.Logger;

import com.cleanwise.service.api.session.TradingPartner;

public class InboundNSCSapCatalogLoader extends InboundNSCSapLoader {//InboundFlatFile {
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
        private final static int MANUF_SKU = 28;
        private final static int MANUFACTURER = 29;
        private final static int SHORT_DESC = 30;
//        private final static int ITEM_ID = 31;


private HashMap categoryToCostCenterMap = new  HashMap();

private final static String catalogloader= "NSCSapCatalogLoader";
private final static String tempTable ="CLT_NSC_CATALOG_LOADER";

private int storeNum = 0;
private Date runDate = new Date();
Connection conn = null;

	private static final String insertSql = "insert into " + tempTable +
            "(VERSION_NUMBER,STORE_ID,STORE_NAME,ACCOUNT_NAME,ACCOUNT_REF_NUM,CATALOG_KEY,CATALOG_NAME, " +
	    "ORDER_GUIDE_NAME,LOCALE,DIST_SKU,DISTRIBUTOR,PACK,UOM,COST,PRICE,CUSTOMER_SKU,SERVICE_CODE,CATEGORY_NAME,CATEGORY_ORDER," +
            "SUBCAT1,SUBCAT1_ORDER,SUBCAT2,SUBCAT2_ORDER,SUBCAT3,SUBCAT3_ORDER,ORDER_GUIDE,COST_CENTER_KEY,FREIGHT_TABLE_KEY,MANUF_SKU,MANUFACTURER,SHORT_DESC,"+
			"ADD_BY,ADD_DATE,LINE_NUM, FILE_NAME) " +
	"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
        "?,?,?," +
        " ?,sysdate,?,?)";


    private static final String trimDataSql = "update " + tempTable + " set " +
			"VERSION_NUMBER	= TRIM(VERSION_NUMBER),STORE_ID	= TRIM(STORE_ID),STORE_NAME = TRIM(STORE_NAME)," +
			"ACCOUNT_NAME = TRIM(ACCOUNT_NAME),ACCOUNT_REF_NUM = TRIM(ACCOUNT_REF_NUM),CATALOG_KEY = TRIM(CATALOG_KEY)," +
			"CATALOG_NAME = TRIM(CATALOG_NAME),ORDER_GUIDE_NAME = TRIM(ORDER_GUIDE_NAME),LOCALE = TRIM(LOCALE)," +
			"DIST_SKU = TRIM(DIST_SKU),DISTRIBUTOR = TRIM(DISTRIBUTOR),PACK = TRIM(PACK),UOM = TRIM(UOM)," +
			"COST = TRIM(COST),PRICE	= TRIM(PRICE),CUSTOMER_SKU = TRIM(CUSTOMER_SKU),SERVICE_CODE = TRIM(SERVICE_CODE)," +
			"CATEGORY_NAME = TRIM(CATEGORY_NAME),CATEGORY_ORDER = TRIM(CATEGORY_ORDER),SUBCAT1 = TRIM(SUBCAT1)," +
			"SUBCAT1_ORDER = TRIM(SUBCAT1_ORDER),SUBCAT2 = TRIM(SUBCAT2),SUBCAT2_ORDER = TRIM(SUBCAT2_ORDER)," +
			"SUBCAT3 = TRIM(SUBCAT3),SUBCAT3_ORDER = TRIM(SUBCAT3_ORDER),ORDER_GUIDE	= TRIM(ORDER_GUIDE)," +
			"COST_CENTER_KEY = TRIM(COST_CENTER_KEY),FREIGHT_TABLE_KEY = TRIM(FREIGHT_TABLE_KEY)"+
                        ",MANUF_SKU = TRIM(MANUF_SKU), MANUFACTURER = TRIM(MANUFACTURER), SHORT_DESC = TRIM(SHORT_DESC)" ;


	protected Logger log = Logger.getLogger(InboundNSCSapCatalogLoader.class);

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("[InboundNSCSapCatalogLoader].translate() => BEGIN.");
		log.info("Input Stream: "+ pIn.getClass().getName());
        IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        try {
            PipeFileParser parser = new PipeFileParser();
            parser.parse(pIn);
            storeNum = getStoreIdFromTradingPartner();

           List<List<String>> list = (List<List<String>>) parser.getParsedStrings();
           process(list);

           isEjb.processNSCSapCatalog(conn,catalogloader,storeNum, tempTable);
		   //store in DB
        PreparedStatement pstmt = null;
		String copySQL = "insert into TCLW_NSC_CATALOG_LOADER select * from "+ tempTable;
        pstmt = conn.prepareStatement(copySQL);
		pstmt.executeUpdate();
		pstmt.close();
           log.info("[InboundNSCSapCatalogLoader].doPostProcessing() => END. Processed: " + list.size()+" catalogs. ");

        } catch (Exception e) {
		    e.printStackTrace();
            log.info("ERROR(s) :" + Utility.getInitErrorMsg(e));
            log.info("[InboundNSCSapCatalogLoader].translate() => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms");
            setFail(true);
     //       throw e;
        }finally{
    		if (conn != null)
    			//conn.close();
    			closeConnection(conn);
    	}
        log.info("[InboundNSCSapCatalogLoader].translate() => END.Process time at : "
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
      //NOTE we skip the first line if it is a header by setting i = 1
        for (int i = 0; parsedData != null && i < parsedData.size() && !isErrorLimit() ; i++) {
            List<String> line = (List<String>) parsedData.get(i);

            if (isValid (line, i)){

              processLine (line,  pstmt,i+1,"AA");
            }
        }
        checkUniqueCostCenterAcrossCategory();
        if (errorLines.size() > 0 || errorMsgs.size() > 0){
           processErrorMsgs();
           throw new Exception("^clw^"+getFormatedErrorMsgs()+"^clw^");
         }

        pstmt.executeBatch();
        pstmt = conn.prepareStatement(trimDataSql);
        pstmt.executeUpdate();

//        getStoreIdFromFile(pstmt);
	pstmt.close();

/*        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception(errorMessage);
        }
*/
    }

    private void processLine (List line, PreparedStatement pstmt, int lineNum, String fileName) throws Exception {
          String versionCd = (String)line.get(VERSION_NUMBER);
          String storeId = (String)line.get(STORE_ID);
          String storeName = (String)line.get(STORE_NAME);
          String accountName = (String)line.get(ACCOUNT_NAME);
          String accountRefNum = (String)line.get(ACCOUNT_REF_NUM);
          String catalogKey = (String)line.get(CATALOG_KEY);
          String catalogName = (String)line.get(CATALOG_NAME);
          String orderGuideName = (String)line.get(ORDER_GUIDE_NAME);
          String locale = (String)line.get(LOCALE);
          String distSku = (String)line.get(DIST_SKU);
          String distributor = (String)line.get(DISTRIBUTOR);
          String pack = (String)line.get(PACK);
          String uom = (String)line.get(UOM);
          String cost = (String)line.get(COST);
          String price = (String)line.get(PRICE);
          String customerSku = (String)line.get(CUSTOMER_SKU);
          String serviceCode = (String)line.get(SERVICE_CODE);
          String categoryName = (String)line.get(CATEGORY_NAME);
          String categoryOrder = (String)line.get(CATEGORY_ORDER);
          String subCat1= (String)line.get(SUBCAT1);
          String subCat1Order = (String)line.get(SUBCAT1_ORDER);
          String subCat2 = (String)line.get(SUBCAT2);
          String subCat2Order = (String)line.get(SUBCAT2_ORDER);
          String subCat3 = (String)line.get(SUBCAT3);
          String subCat3Order = (String)line.get(SUBCAT3_ORDER);
          String orderGuide = (String)line.get(ORDER_GUIDE);
          String costCenterKey = (String)line.get(COST_CENTER_KEY);
          String freightTableKey = (String)line.get(FREIGHT_TABLE_KEY);
          String manufSku = (String)line.get(MANUF_SKU);
          String manufacturer = (String)line.get(MANUFACTURER);
          String shortDesc = (String)line.get(SHORT_DESC);

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
                 pstmt.setString(29,manufSku);
                 pstmt.setString(30,manufacturer);
                 pstmt.setString(31,shortDesc);

                 pstmt.setString(32,catalogloader);
                 pstmt.setInt(33,lineNum);
                 pstmt.setString(34,fileName);

                 pstmt.addBatch();

               }catch(Exception e){
         //	conn.rollback();
                 conn.close();
                 conn = null;
                 throw e;
               }

    }

    private void checkUniqueCostCenterAcrossCategory () {
      if (this.categoryToCostCenterMap != null){
        Set keys = categoryToCostCenterMap.keySet();
        for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
          String category = (String) iter.next();
          HashSet costCenters = (HashSet)categoryToCostCenterMap.get(category);
          if (costCenters != null && costCenters.size()> 1){
            addError("There are different Cost Centers :"+costCenters.toString()+" found across Category: " + category );
          }
        }
      }
    }

    private boolean isValid(List<String> lineValues, int line){
      boolean valid = true;
      boolean ok = true;
       valid &= checkRequired (lineValues.get(VERSION_NUMBER), "Version Number", line);
/*       valid &= checkRequired(lineValues.get(STORE_ID), "Store Id", line);
       ok = checkRequired(lineValues.get(STORE_ID), "Store Id", line);
       if (ok){
         ok = checkType(lineValues.get(STORE_ID), "Store Id", TYPE.INTEGER, line);
         valid &=ok;
         if (ok && Integer.parseInt(lineValues.get(STORE_ID)) != storeNum) {
           addError("Incorrect STORE ID value = '" + Integer.parseInt(lineValues.get(STORE_ID)) + "'. Trading Partner associated with store Id =" + storeNum +". Line: " + line);
           valid = false;
         }
       } else{
         valid &= ok;
       }*/
      ok = checkType(lineValues.get(STORE_ID), "Store Id", TYPE.INTEGER, true, line);
      valid &=ok;
      if (ok && Integer.parseInt(lineValues.get(STORE_ID)) != storeNum) {
        addError("Incorrect STORE ID value = '" + Integer.parseInt(lineValues.get(STORE_ID)) + "'. Trading Partner associated with store Id =" + storeNum+". ", line);
        valid = false;
      }

       valid &= checkRequired(lineValues.get(STORE_NAME), "Store Name", line);
       valid &= checkRequired(lineValues.get(ACCOUNT_NAME), "Account Name", line);
       valid &= checkRequired(lineValues.get(ACCOUNT_REF_NUM), "Account Ref Num", line);
       valid &= checkRequired(lineValues.get(CATALOG_KEY), "Catalog Key", line);

       String catalogName = lineValues.get(CATALOG_NAME);
       ok = checkRequired(catalogName, "Catalog Name", line);
       if (ok && catalogName.trim().length() > 30) {
         addError("Catalog Name: '" + catalogName + "' is too long. Max length is 30 char.", line);
         ok = false;
       }
       valid &= ok;

       String ogName =lineValues.get(ORDER_GUIDE_NAME);
       ok = checkRequired(ogName, "Order Guide Name", line);
       if (ok && ogName.trim().length() > 30) {
         addError("Order Guide Name: '" + ogName + "' is too long. Max length is 30 char." , line);
         ok = false;
       }
       valid &= ok;

       valid &= checkRequired(lineValues.get(LOCALE), "Locate", line);
       valid &= checkRequired(lineValues.get(DIST_SKU), "Dist SKU", line);
       valid &= checkRequired(lineValues.get(DISTRIBUTOR), "Distributor", line);
       valid &= checkRequired(lineValues.get(PACK), "Pack", line);
       valid &= checkRequired(lineValues.get(UOM), "UOM", line);

       valid &= checkType(lineValues.get(COST), "Cost", TYPE.BIG_DECIMAL, true, line);


       valid &= checkType(lineValues.get(PRICE), "Price", TYPE.BIG_DECIMAL, true, line);

       String category = lineValues.get(CATEGORY_NAME);
       ok = checkRequired(category, "Category Name", line);
       valid &= ok;
       if (ok){
         HashSet costCenterSet = null;
         if (categoryToCostCenterMap.containsKey(category)){
            costCenterSet = (HashSet)categoryToCostCenterMap.get(category);
         } else {
           costCenterSet = new HashSet();
           categoryToCostCenterMap.put(category, costCenterSet);
         }
         if (Utility.isSet(lineValues.get(COST_CENTER_KEY))){
             costCenterSet.add(lineValues.get(COST_CENTER_KEY));
         }
       }
       //------------------verify that subCat does not have empty superCat
       String err = "";
       if (Utility.isSet(lineValues.get(SUBCAT3))) {
           err += (!Utility.isSet(lineValues.get(SUBCAT1))) ? "SubCat1 is empty; " :"" ;
           err += (!Utility.isSet(lineValues.get(SUBCAT2))) ? "SubCat2 is empty; " :"";
       } else if (Utility.isSet(lineValues.get(SUBCAT2))){
          err += (!Utility.isSet(lineValues.get(SUBCAT1))) ? "SubCat1 is empty; " :"" ;
       }
       ok = true;
       if (err.length() > 0 ) {
         addError(err , line);
         ok = false;
       }
       valid &=ok;
       //-------------------------------------------------//

         valid &= checkType(lineValues.get(CATEGORY_ORDER), "Category Order", TYPE.INTEGER,false, line);

         valid &= checkType(lineValues.get(SUBCAT1_ORDER), "SubCat1 Order", TYPE.INTEGER,false, line);

         valid &= checkType(lineValues.get(SUBCAT2_ORDER), "SubCat2 Order", TYPE.INTEGER,false, line);
         valid &= checkType(lineValues.get(SUBCAT3_ORDER), "SubCat3 Order", TYPE.INTEGER,false, line);

         valid &= checkType(lineValues.get(ORDER_GUIDE), "Order Guide", TYPE.BOOLEAN, true,line);

         valid &= checkType(lineValues.get(MANUF_SKU), "Manufacturer SKU", TYPE.TEXT, false, line);
         valid &= checkType(lineValues.get(MANUFACTURER), "Manufacturer", TYPE.TEXT, false, line);
         valid &= checkType(lineValues.get(SHORT_DESC), "Short Description", TYPE.TEXT, false, line);
      return valid;
    }

     }
