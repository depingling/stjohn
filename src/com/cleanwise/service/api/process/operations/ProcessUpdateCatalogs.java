/**
 * 
 */
package com.cleanwise.service.api.process.operations;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.OrderGuideDataVector;
import com.cleanwise.service.api.value.OrderRequestData;
import com.cleanwise.service.api.value.OrderRequestDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.CatalogData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.apps.dataexchange.ExcelReader;
import com.cleanwise.service.apps.dataexchange.InboundFlatFile;
import com.cleanwise.service.apps.loaders.PipeFileParser;
import com.cleanwise.service.api.dao.CatalogAssocDataAccess;
import com.cleanwise.service.api.dao.CatalogDataAccess;
import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.espendwise.ocean.common.webaccess.ResponseError;

import org.apache.log4j.Logger;


/**
 * @author nguschina
 *
 */
public class ProcessUpdateCatalogs extends InboundFlatFile {
	
	private final static String tempTable = "CLT_UPDATE_CATALOG_LOADER";
	private final static String catalogloader= "updateCatalogLoader";
	private int columnCount = 16;
	private int reqColumnCount = 16;
	private boolean isActionAdd = false;
	private boolean isVersion1 = true;
	private String dateFormat = "MM/dd/yyyy";

	protected Logger log = Logger.getLogger(this.getClass());	
	private List<InboundCatalogData> parsedObjects = new ArrayList<InboundCatalogData>();
	private int storeId = 0;
	private int storeCatalogId = 0;
	private Map<InboundCatalogData, String> lineNumberMap = new HashMap<InboundCatalogData, String>();
	private Map<String, List<InboundCatalogData>> catalogs = new HashMap<String, List<InboundCatalogData>>();
	
	private List<String> activeCatalogIds = new ArrayList<String>();
	//private Map<String, String> allKeyMap = new HashMap<String, String>(); 
	private Map<String, List<String>> activeOGToCatalogMap = new HashMap<String, List<String>>();
	private Map<String, List<String>> activeCatalogToOGMap = new HashMap<String, List<String>>();
	
	Connection conn = null;
	private static final String insertSql = "insert into "+ tempTable +"(" +
	"VERSION_NUMBER,ACTION,LINE_NUMBER,STORE_ID,STORE_SKU,CATALOG_ID, " +
	"ORDER_GUIDE_NAMES,DIST_SKU,DISTRIBUTOR,DIST_PACK,DIST_UOM,COST,PRICE," +
	"CUSTOMER_SKU,CATEGORY_NAME,TAX_EXEMPT, SPL, STORE_CATALOG_ID,ADD_BY,ADD_DATE) " +
	"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";

	//private static final String checkDataSql = "select count(*) from CLT_UPDATE_CATALOG_LOADER";
	private static final String trimDataSql = "update "+ tempTable +" set " +
	"VERSION_NUMBER	= TRIM(VERSION_NUMBER),ACTION = TRIM(ACTION)," +
	"CATALOG_IDS = TRIM(CATALOG_IDS)," +
	"ORDER_GUIDE_NAMES = TRIM(ORDER_GUIDE_NAMES)," +
	"DIST_SKU = TRIM(DIST_SKU),DISTRIBUTOR = TRIM(DISTRIBUTOR)," +
	"PACK = TRIM(PACK),UOM = TRIM(UOM)," +
	"CUSTOMER_SKU = TRIM(CUSTOMER_SKU)," +
	"CATEGORY_NAME = TRIM(CATEGORY_NAME)" ;
	private static String selectStoreId = "select distinct STORE_ID,STORE_NAME from "+ tempTable ;
	
	private static String verifyStoreId = "select bus_entity_id from clw_bus_entity where " +
	"upper(short_desc) = upper(?) and bus_entity_type_cd = 'STORE' " +
	"and bus_entity_status_cd = 'ACTIVE'";
	
	public void process(Integer storeId, String fileName,  Object dataContents) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("process() ===> Start");
        IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        try {
        	if (conn == null){
    			conn = getConnection();
    		}
            storeCatalogId = isEjb.getStoreCatalog(conn, storeId.intValue());
			validateContents(storeId, fileName, (byte[])dataContents);
			if (getErrorMsgs().size()>0){
	        	throw new RuntimeException(getFormatedErrorMsgs());
	        }
		    doPostProcessing();	
			if (getErrorMsgs().size()>0){
	        	throw new RuntimeException(getFormatedErrorMsgs());
	        }
		    
		    isEjb.processUpdateCatalog(conn,catalogloader, storeId.intValue(),isVersion1, tempTable);
		    log.info("Update Catalog Loader finished successfully: PROCESSED " + parsedObjects.size() + " records");
        } catch (Exception e) {
        	log.info("ERROR(s) :" + Utility.getInitErrorMsg(e));
            log.error("process => FAILED.Process time at : " + (System.currentTimeMillis() - startTime) + " ms", e);
            setFail(true);
            throw e;
        }finally{
    		if (conn != null)
    			//conn.close();
    			closeConnection(conn);
    	}
        log.info("process() ===> End");		
	}
	
	public List validateContents(Integer storeId, String fileName, byte[] dataContents) throws Exception {
		log.info("validateContents() ===> Start. dataContents.length="+ dataContents.length);
		this.storeId = storeId.intValue();
		InboundCatalogData data = new InboundCatalogData();
		javaBeanToPopulate = data.getClass().getName();
		InputStream inputStream = null;
        
		try{
			inputStream = new BufferedInputStream(new ByteArrayInputStream((byte[])dataContents));
			if (inputStream.available() == 0){
				throw new Exception("Stream size is 0 - " + fileName);
	        }
	        String streamType = getFileExtension(fileName.trim().toLowerCase());
	        boolean fileFormatIssue = streamType == null || (!streamType.equals("xls") && !streamType.equals("xlsx"));

	        log.info("validateContents() ===> fileName = [" + fileName.trim().toLowerCase()+ "], streamType=" + streamType+ ", fileFormatIssue=" + fileFormatIssue); 
	        if (fileFormatIssue){
	    		String message = I18nUtil.getMessage("validation.catalog.loader.error.mustBeXlsFileFormat");
	    		//log.info("validateContents() ===> message = " + message);
	    		appendErrors(message);        		
	    		return getErrorMsgs();
	        }
			translate(inputStream, streamType);
			//validateParsedObjects();
		}catch(Exception e){
			e.printStackTrace();
			//appendErrors(e.getMessage());
		}finally {
			if (inputStream != null)
				inputStream.close();
		} 
		
		log.info("validateContents() ===> End");
		return getErrorMsgs();
	}
	
    public void translate(InputStream pIn, String pStreamType) throws Exception {
        
        log.info("translate() => BEGIN.");
		currentLineNumber = 1;

        ExcelReader reader = new ExcelReader(pIn);
        ExcelReader xlsRdr = (ExcelReader) reader;
		xlsRdr.processFile(this);
        log.info("translate() => END.");
    }
	


		
	/**
     *Returns the extension of a file:
     *foo.bar returns bar
     *foobar returns foobar
     */
    private String getFileExtension(String pFileName){
        java.util.StringTokenizer tok = new java.util.StringTokenizer(pFileName,".");
        String suffix = null;
        while(tok.hasMoreTokens()){
            suffix = tok.nextToken();
        }
        return suffix;
    }

    
    /**
	 *passed in the parsed line will preform the necessary logic of populating the object
	 */
	public void parseLine(List pParsedLine) throws Exception{
        //log.info("parseLine() => currentLineNumber = "+ currentLineNumber);

		if(isEmpty(pParsedLine)){
			log.info("parseLine() :" + currentLineNumber +" ===> empty line");
			return;
		}
		if(currentLineNumber == 1){
			if (pParsedLine.size() < 4){
				throw new Exception("Data has less than 4 required columns");
			}

		}
		parseDetailLine(pParsedLine);
		currentLineNumber++;
        
	}
	
	protected void parseDetailLine(List pParsedLine) throws Exception{
		//log.info("parseDetailLine() => BEGIN. getCurrentLineNumber()="+getCurrentLineNumber()+", pParsedLine="+ pParsedLine);
		
		InboundCatalogData parsedObj = new InboundCatalogData();
		lineNumberMap.put(parsedObj, getCurrentLineNumber()+1+"");
		try {
			String errMessKey = null;
			validateAndPopulateProperty(Columns.VERSION_NUMBER, pParsedLine, parsedObj, true, null, "(1|2)", -1, -1, errMessKey); 
			validateAndPopulateProperty(Columns.ACTION, pParsedLine, parsedObj, true, null, "(A|D|C)", -1, -1, errMessKey); 
			
			reqColumnCount = getRequiredColumnCnt(parsedObj.getVersionNumber(), parsedObj.getAction());
			if (getCurrentLineNumber() == 1){
				columnCount = pParsedLine.size();
			} else {
				//if (columnCount != pParsedLine.size()){
				//	appendErrors(""+getCurrentLineNumber(), "validation.catalog.loader.error.incorrectColumnCount", pParsedLine.size(), columnCount);
				//}
			}
			if (reqColumnCount > 0  && columnCount < reqColumnCount){
				appendErrors(""+getCurrentLineNumber(), "validation.catalog.loader.error.columnCountLessThanMinimum", columnCount, reqColumnCount);
	        }
	    	validateAndPopulateProperty(Columns.LINE_NUMBER, pParsedLine, parsedObj, true);
	    	validateAndPopulateProperty(Columns.CATALOG_ID,  pParsedLine, parsedObj, true);
	    	validateAndPopulateProperty(Columns.STORE_SKU,  pParsedLine, parsedObj, isVersion1);
	    	validateAndPopulateProperty(Columns.PRICE, pParsedLine, parsedObj, true);
	    	validateAndPopulateProperty(Columns.COST, pParsedLine, parsedObj, false);
	    	validateAndPopulateProperty(Columns.CATEGORY, pParsedLine, parsedObj, isActionAdd);
	    	validateAndPopulateProperty(Columns.DIST_NAME, pParsedLine, parsedObj, isActionAdd);
	    	validateAndPopulateProperty(Columns.DIST_SKU, pParsedLine, parsedObj, !isVersion1);
	    	validateAndPopulateProperty(Columns.DIST_UOM, pParsedLine, parsedObj, !isVersion1);
	       	validateAndPopulateProperty(Columns.DIST_PACK, pParsedLine, parsedObj, false);
	       	validateAndPopulateProperty(Columns.ORDER_GUIDE_NAME, pParsedLine, parsedObj, false);
	       	validateAndPopulateProperty(Columns.TAX_EXEMPT, pParsedLine, parsedObj, false);
	       	validateAndPopulateProperty(Columns.CUSTOMER_SKU, pParsedLine, parsedObj, false);
	       	validateAndPopulateProperty(Columns.STANDARD_PRODUCT_LIST, pParsedLine, parsedObj, false);
			if (getCurrentLineNumber()<2) {
				log.info("parseDetailLine() => line# ="+getCurrentLineNumber() + ", parsedObj="+ parsedObj.toString());
			}
		} catch (Exception e) {
			log.info("parseDetailLine() => Exception ["+e.getMessage()+"] on line# ="+getCurrentLineNumber()+", pParsedLine="+ pParsedLine);
			//e.printStackTrace();
			throw new Exception (e);
		}
		
	    validateParsedObject(parsedObj);
		//log.info("parseDetailLine() => END.");
	}

	
	protected void validateParsedObject(Object pParsedObject) throws Exception{
		InboundCatalogData parsedObj = (InboundCatalogData) pParsedObject;
		if (isVersion1 ){
        	Integer value = parsedObj.getStoreSku(); 
        	if (value == null || value.intValue() <= 0) {
            	appendErrors(parsedObj, "validation.catalog.loader.error.wrongPositiveValue", Columns.STORE_SKU.getHeader(), value);            	
        	}
        }
		BigDecimal price = parsedObj.getPrice();
		if (price != null && price.doubleValue() < 0) {
        	appendErrors(parsedObj, "validation.catalog.loader.error.wrongPositiveValue", Columns.PRICE.getHeader(), price);            	
    	}
		
		BigDecimal cost = parsedObj.getCost();	
		if (cost != null && cost.doubleValue() < 0) {
        	appendErrors(parsedObj, "validation.catalog.loader.error.wrongPositiveValue", Columns.COST.getHeader(), cost);            	
    	}
		
		if (Utility.isSet(parsedObj.getOrderGuideNames())&& parsedObj.getAction().equalsIgnoreCase("C")) {
        	appendErrors(parsedObj, "validation.catalog.loader.error.columnMustBeEmptyForAction", Columns.ORDER_GUIDE_NAME.getHeader(), parsedObj.getAction().toUpperCase());
		}
	   	//validate catalog ids format
		if (Utility.isSet(parsedObj.getCatalogIds())){
			//log.info("validateParsedObject()===> parsedObj.getCatalogIds().size()= "+ parsedObj.getCatalogIds().size());
    		StringBuffer errs = new StringBuffer();
			for (int i=1; i<parsedObj.getCatalogIds().size(); i++){
    	        if (Utility.isSet((String)parsedObj.getCatalogIds().get(i))) {
    	        	String value = ((String)parsedObj.getCatalogIds().get(i)).trim(); 
    	            try {
    	            	int v = Integer.parseInt(value);
    	            } catch (Exception ex) {
    	            	errs.append((errs.length() == 0)? "" : ",") ;
	    	            errs.append(value);
    	            }
 
     	        }
    		}
            if (errs.length() > 0) {
            	appendErrors(parsedObj, "validation.catalog.loader.error.columnMustBeListOfNumbers", Columns.CATALOG_ID.getHeader(), errs.toString());
            }
    	}	
		parsedObjects.add(parsedObj);
	}
	
	
	//private void doPostProcessing(List<InboundCatalogData> parsedObjs) throws Exception {
	protected void doPostProcessing() throws Exception {
		log.info("Start doPostProcessing()....");
		Set<Integer> catalogIds = new HashSet<Integer>();
		Set<String> ogNames =  new HashSet<String>();
//		Set<String> distNames = new HashSet<String>();
		for (InboundCatalogData obj : parsedObjects){
			for (int i=0; obj.getCatalogIds()!=null && i<obj.getCatalogIds().size(); i++){
				catalogIds.add (new Integer(((String)(obj.getCatalogIds().get(i))).trim()));
				//catalogIds.add ((Integer)obj.getCatalogIds().get(i));
			}
			for (int i=0; obj.getOrderGuideNames()!=null && i<obj.getOrderGuideNames().size(); i++){
				ogNames.add(((String)(obj.getOrderGuideNames().get(i))).trim());
			}
		}
		log.info("doPostProcessing()===> 1)Validate  catalogIds = "+ catalogIds);
		log.info("doPostProcessing()===> 2)Validate  orderGuideNames= "+ ogNames);

		activeCatalogIds = getActiveCatalogIds( catalogIds);
		activeOGToCatalogMap = getOrderGuides( ogNames);
		activeCatalogToOGMap = convertMap(activeOGToCatalogMap);
		
		
		if (!Utility.isSet(activeCatalogIds)) {
			Object[] args = new Object[]{catalogIds.toString(), ""+storeId};
			appendErrors("validation.catalog.loader.error.noActiveCatalogs", args);
			return;
		}
		
		doTmpTableLoad();
		doTmpTableUpdateAndCheck();

		log.info("End doPostProcessing()....");
	}

	private Map<String, List<String>> convertMap (Map<String, List<String>> origMap) {
		//log.info("convertMap() ===> origMap=" + origMap);
		Map<String, List<String>> retMap = new HashMap<String, List<String>>();
		List<String> retList = null;
		if (origMap == null || origMap.isEmpty()) {
			return origMap;
		}
		Iterator it = origMap.keySet().iterator();
		while (it.hasNext()) {
			String origKey = (String)it.next(); 
			List<String> origList  = (List<String>)origMap.get(origKey); 
			if (origList != null) {
				for (String origEl : origList ){
					retList = retMap.get(origEl);
					if (retList == null) {
						retList = new ArrayList<String>();
						retMap.put(origEl, retList);
					}
					retList.add(origKey);
				}
			}
		}
		//log.info("convertMap() ===> retMap=" + retMap);
		return retMap;
	}
	
	private void doTmpTableLoad() throws Exception {
		log.info("doTmpTableLoad() ===> Start doTmpTableLoad().upload into TEMP table...");
		
        if (parsedObjects.size() == 0) {
        	throw new Exception("Input file could be empty.");
        } 	
         
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(insertSql);
        //loadTempTable(parsedData);
        //log.info("doTmpTableLoad() ===> SQL insertSql in Temp Table = " +  insertSql);
        log.info("doTmpTableLoad() ===> SQL insertSql in Temp table " );
        log.info("doTmpTableLoad() ===> parsedObjects.size() = "+parsedObjects.size());
        for (InboundCatalogData obj : parsedObjects) {
        	 int i = parsedObjects.indexOf(obj);
        	 
        	 Set<String> validCatalogs = checkCatalogs(obj);
        	 Set<String> errOGNames = checkOgNames(obj);
        	 
        	 //log.info("doTmpTableLoad() ===> validCatalogs="+ validCatalogs);
        	 if (Utility.isSet(obj.getCatalogIds()) && !validCatalogs.isEmpty()){
	        	 for (Object catIdObj : obj.getCatalogIds()) {
	        		 String catalogId = (String)catIdObj;
	        		 int catId = (validCatalogs.contains(catalogId)) ? new Integer(catalogId).intValue() : 0;
	        		 String ogNamesForCatalog = "";
	        		 if (catId > 0 && !Utility.isSet(errOGNames) &&
	        				 activeCatalogToOGMap != null &&
	        				 activeCatalogToOGMap.get(catalogId) != null &&
	     					Utility.isSet((List<String>)activeCatalogToOGMap.get(catalogId))){
	     				ogNamesForCatalog =Utility.toCommaSting( (List<String>)activeCatalogToOGMap.get(catalogId));
	     			 }
	        		 log.info("doTmpTableLoad() ===> ogNamesForCatalog="+ ogNamesForCatalog);
	                 try {
	                	populateTmpRow(pstmt, obj);
	     				pstmt.setInt(6, catId);
	     				pstmt.setString(7,ogNamesForCatalog);

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
        	 }
        }
        log.info("doTmpTableLoad() ===> 1) DO executeBatch()");
        pstmt.executeBatch();
        
        //log.info("doTmpTableLoad() ===> 2) DO executeUpdate() for 'trimDataSql'");
        pstmt = conn.prepareStatement(trimDataSql);
        int n = pstmt.executeUpdate();
        
        log.info("doTmpTableLoad() ===> 3) DO close()");
        pstmt.close();
        // verify that all records are loaded into temporary table
        if (n < parsedObjects.size()){
          throw new Exception("Filling of the loader's temp table is not completed. Uploaded "+n+" from "+parsedObjects.size()+" records.");
        }

        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception(errorMessage);
        }

	}
	
	private void populateTmpRow(PreparedStatement pstmt, InboundCatalogData obj ) throws Exception {
		pstmt.setString(1,obj.getVersionNumber());
		pstmt.setString(2,obj.getAction().toUpperCase());
		pstmt.setInt(3,obj.getLineNumber().intValue());	
		pstmt.setInt(4,storeId);
		pstmt.setInt(5, ((obj.getStoreSku() != null) ? obj.getStoreSku().intValue() : 0));
		//pstmt.setString(6,((Utility.isSet(obj.getCatalogIds())) ? Utility.toCommaSting(obj.getCatalogIds()) : ""));
		//pstmt.setString(7,(Utility.isSet(obj.getOrderGuideNames())) ? Utility.toCommaSting(obj.getOrderGuideNames()): "");
		pstmt.setString(8,obj.getDistSku());
		pstmt.setString(9,obj.getDistName());
		pstmt.setInt(10,(obj.getDistPack()!= null) ? obj.getDistPack().intValue() : 0);
		pstmt.setString(11,obj.getDistUom());
		pstmt.setBigDecimal(12,obj.getCost());
		pstmt.setBigDecimal(13,obj.getPrice());
		pstmt.setString(14,obj.getCustomerSku());
		
		pstmt.setString(15,obj.getCategory());
		pstmt.setString(16, (obj.getTaxExempt()!=null) ? obj.getTaxExempt().toString() : null );
		pstmt.setString(17,(obj.getStandardProductList()!= null) ? obj.getStandardProductList().toString() : "");
		pstmt.setInt(18, storeCatalogId);
		pstmt.setString(19,catalogloader);

	}
	
	private void doTmpTableUpdateAndCheck () throws Exception {
		log.info("doTmpTableUpdateAndCheck() ===> Begin. StoreId = "+ storeId + ", storeCatalogId="+storeCatalogId );
		String sql = "";
		String subSql="";
		PreparedStatement stmt = null;
        //=========filling ITEM_ID ========================//
        try {
        	if (isVersion1){
            sql  =
                "UPDATE " + tempTable + " tmp \n" +
                "SET \n" +
                "( \n" +
                    "tmp.ITEM_ID \n" +
                ") = ( \n" +
                "SELECT \n" +
                	" item.ITEM_ID " +
                "FROM \n" +
                " clw_item item INNER JOIN \n" +
                "( \n" +
                	"clw_catalog_Structure cs \n" +
                ") \n" +
                	"ON cs.item_id = item.item_id AND \n" +
                	"CS.CATALOG_ID ="+ storeCatalogId+
                " WHERE " +
                	" tmp.STORE_SKU = item.SKU_NUM  \n" +
                ") " +
                " WHERE TMP.VERSION_NUMBER= 1 and TMP.STORE_SKU is not null ";
        	} else {
            sql =
                "UPDATE " + tempTable + " tmp \n" +
                "SET \n" +
                "( \n" +
                    "tmp.ITEM_ID \n" +
                ") = ( \n" +
                "SELECT \n" +
                	" im.ITEM_ID " +
                "FROM \n" +
                " clw_item_mapping im " +
                " INNER JOIN clw_item_assoc ia ON ia.ITEM_ID = im.ITEM_ID \n" +
                "       and ia.item_Assoc_Cd = 'PRODUCT_PARENT_CATEGORY' \n" +
                "       and ia.catalog_Id ="+storeCatalogId + "\n" +
                " INNER JOIN clw_catalog_structure cs ON cs.ITEM_ID = im.ITEM_ID \n" +
                "       and cs.catalog_Id ="+storeCatalogId+"\n" +
                " WHERE " +
                    " im.item_Mapping_Cd = 'ITEM_DISTRIBUTOR' AND \n" + 
                	" tmp.DIST_SKU = im.ITEM_NUM  AND \n" +
                	" tmp.UOM = im.ITEM_UOM  AND \n" +
                ") " +
                " WHERE TMP.VERSION_NUMBER= 2 and TMP.DIST_SKU is not null \n" +
                "  and TMP.UOM is not null";
        	}
 
	        //log.info("filling ITEM_ID: SQL=" +sql);
	        log.info("...filling ITEM_ID");

	        stmt = conn.prepareStatement(sql);
            //stmt.setInt(1, catalogId);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException ex) {
            String msg = "An error occurred at updating of field 'ITEM_ID'" +
                "in the table " + tempTable + ". " + ex.getMessage()+
                "*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");
        }
		//  =========== filling DIST_ID by Distributor Name==========// 
	    try {
		    sql =" update " + tempTable + " tmp \n" + 
			"set tmp.dist_id  = (SELECT bus_entity_id  \n" + 
			"   FROM  \n" + 
			"    clw_bus_entity be  \n" + 
			"     INNER JOIN clw_bus_entity_assoc bea ON  \n" + 
			"      BE.bus_entity_id = BEA.BUS_ENTITY1_ID AND  \n" + 
			"      BEA.BUS_ENTITY2_ID = " + storeId + " \n" + 
			"   WHERE  \n" + 
	        "     tmp.DISTRIBUTOR = be.SHORT_DESC AND  \n" + 
	        "     be.BUS_ENTITY_TYPE_CD ='DISTRIBUTOR' ) \n" + 
			" WHERE tmp.DISTRIBUTOR IS NOT NULL " ;

		    //log.info("filling DIST_ID: SQL=" +sql);
		    log.info("...filling DIST_ID" );
		    
	        stmt = conn.prepareStatement(sql);
	        //stmt.setInt(1, catalogId);
	        stmt.executeUpdate();
	        stmt.close();
	    }
	    catch (SQLException ex) {
	        String msg = "An error occurred at updating of field 'DIST_ID'" +
	            "in the table " + tempTable + ". " + ex.getMessage()+
	            "*** Execute the following request to get more information : " +  sql;
	        throw new SQLException("^clw^"+msg+ "^clw^");
	    }
        //========= filling DIST_ID by catalog structure for action 'C'=====// 
	    try {
		    sql =" update " + tempTable + " tmp \n" + 
			"set tmp.dist_id  = (SELECT bus_entity_id  \n" + 
			"   FROM  \n" + 
			"    clw_catalog_structure cs  \n" + 
			"   WHERE  action = 'C' AND \n" + 
	        "     tmp.ITEM_ID = cs.ITEM_ID AND  \n" + 
	        "     tmp.CATALOG_ID = cs.CATALOG_ID ) \n" + 
			" WHERE tmp.DISTRIBUTOR IS NULL AND ( \n" +
			"            tmp.DIST_SKU is not null OR \n" +
			"            tmp.DIST_PACK is not null OR \n" +
			"            tmp.DIST_UOM is not null OR \n" +
			"            tmp.SPL is not null )\n" +
			"";

		    //log.info("filling DIST_ID: SQL=" +sql);
		    log.info("...filling DIST_ID by catalog structure for action 'C'" );
		    
	        stmt = conn.prepareStatement(sql);
	        //stmt.setInt(1, catalogId);
	        stmt.executeUpdate();
	        stmt.close();
	    }
	    catch (SQLException ex) {
	        String msg = "An error occurred at updating of field 'DIST_ID' " +
	            "in the table " + tempTable + ". " + ex.getMessage()+
	            "*** Execute the following request to get more information : " +  sql;
	        throw new SQLException("^clw^"+msg+ "^clw^");
	    }

	    //======== filling  CATALOG_TYPE_CD ============//
        try {
	        sql =" update " + tempTable + " tmp \n" + 
				" set tmp.catalog_type_cd = (select c.catalog_type_cd \n" + 
				" FROM \n" + 
				"  clw_catalog c \n" + 
				"     where \n" + 
				"         c.catalog_id = tmp.catalog_id ) \n" + 
				"  WHERE   tmp.catalog_id > 0 " ; 
   	     
	        //log.info("filling CATALOG_TYPE_CD: SQL=" +sql );
	        log.info("...filling CATALOG_TYPE_CD"  );

	        stmt = conn.prepareStatement(sql);
	        stmt.executeUpdate();
	        stmt.close();
        }
	    catch (SQLException ex) {
	        String msg = "An error occurred at updating of field 'CATALOG_TYPE_CD'" +
	            "in the table " + tempTable + ". " + ex.getMessage()+
	            "*** Execute the following request to get more information : " +  sql;
	        throw new SQLException("^clw^"+msg+ "^clw^");
	    }
		
		//======== filling  ACC_CATALOG_ID if we Add items and there are no Items in account catalog ===========//
        try {
	        sql =" update " + tempTable + " tmp \n" + 
				" set tmp.acc_catalog_id = (select distinct c.catalog_id \n" + 
				" FROM \n" + 
				"  clw_catalog_assoc caa \n" + 
				"   INNER JOIN clw_catalog c ON  c.catalog_id = CAA.CATALOG_ID AND C.CATALOG_TYPE_CD= 'ACCOUNT'\n" + 
				"   INNER JOIN clw_catalog_assoc cas ON CAA.BUS_ENTITY_ID = CAS.BUS_ENTITY_ID and cas.CATALOG_ASSOC_CD = 'CATALOG_ACCOUNT'\n" + 
				"     where \n" + 
				"         caa.CATALOG_ASSOC_CD = 'CATALOG_ACCOUNT' AND \n" + 
				"   not exists (select 1 from clw_catalog_structure cs where cs.item_id =tmp.item_id and cs.catalog_id =c.catalog_id ) AND \n" +
				"         cas.catalog_id = tmp.catalog_id ) \n" + 
				"  WHERE   tmp.catalog_id > 0 and tmp.catalog_type_cd <> 'ACCOUNT'" ; 
   	     
	        //log.info("filling ACC_CATALOG_ID: SQL="+sql );
	        log.info("...filling ACC_CATALOG_ID" );

	        stmt = conn.prepareStatement(sql);
	        stmt.executeUpdate();
	        stmt.close();
        }
	    catch (SQLException ex) {
	        String msg = "An error occurred at updating of field 'ACC_CATALOG_ID'" +
	            "in the table " + tempTable + ". " + ex.getMessage()+
	            "*** Execute the following request to get more information : " +  sql;
	        throw new SQLException("^clw^"+msg+ "^clw^");
	    }
		// -----filling CONTRACT_ID  ====== item should be in the shopping catalog //
	      try {
	    	  subSql =
	    		"   clw_contract ct \n" +
				"    where  \n"+
				"      ct.catalog_id = tmp.catalog_id and \n"+
				"      CT.CONTRACT_STATUS_CD = 'ACTIVE'" ;
		      sql = "SELECT ct.catalog_id FROM \n" + 
	      			"  (select  distinct catalog_id from " + tempTable + ") tmp, \n"+
		      		subSql +
		      		" group by ct.catalog_id having  count(*) >1  "	;
	    	  
	      
	    	  stmt = conn.prepareStatement(sql);
	    	  ResultSet rs = stmt.executeQuery();
    		  StringBuffer err = new StringBuffer();
	    	  if (rs != null) {
		    	  while (rs.next()){
		    		  err.append((err.length()==0)? ""+rs.getInt(1): ", "+rs.getInt(1));
		    	  }
		    	  if (err.length()!=0) {
		    		  appendErrors("validation.catalog.loader.error.multipleContractsForCatalog", err.toString());
		    	  }
	    	  }
	    	  if (err.length()==0) {
		    	  sql =" update " + tempTable + " tmp \n" + 
					" set tmp.contract_id = ( " +
					"  select ct.contract_id FROM \n"+
					subSql +
//					"   clw_contract ct \n" +
//					"    where  \n"+
//					"      ct.catalog_id = tmp.catalog_id and \n"+
//					"      CT.CONTRACT_STATUS_CD = 'ACTIVE'" +
					
					") \n"+
					" WHERE   tmp.item_id > 0 and tmp.catalog_id >0 \n";
					//" tmp.action='A' and and 1=1"; 
					//"    AND (tmp.price >= 0 OR tmp.action = 'D')"; 
	
				 //log.info("filling CONTRACT_ID: SQL="+ sql);
			     log.info("...filling CONTRACT_ID");
	
			     stmt = conn.prepareStatement(sql);
		         stmt.executeUpdate();
		         stmt.close();
	    	 }
		   }
		   catch (SQLException ex) {
		        String msg = "An error occurred at updating of field 'CONTRACT_ID'" +
		            "in the table " + tempTable + ". " + ex.getMessage()+
		            "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		   }
 
			// -----filling CONTRACT_ITEM_ID  ====== item should be in the shopping catalog //
	      try {
	    	 subSql =  
					"    clw_contract_item cti \n" +
					"    INNER JOIN clw_contract ct  ON \n" +
					"      cti.contract_id = ct.contract_id \n"+
					"    where  \n"+
					"      cti.item_id = tmp.item_id and \n"+
					"      ct.catalog_id = tmp.catalog_id and \n"+
					"      CT.CONTRACT_STATUS_CD = 'ACTIVE'" ;

	    	  
		     sql =" update " + tempTable + " tmp \n" + 
				" set tmp.contract_item_id = (select cti.contract_item_id \n"+
				"  FROM \n"+
				subSql +
				") \n"+
				" WHERE tmp.action<>'A' and tmp.item_id > 0 and tmp.catalog_id >0 \n"+
				"    AND tmp.catalog_type_cd <> 'ACCOUNT' \n" +    //do not populate for Account catalog's contract
				"    AND (tmp.price >= 0 OR tmp.action = 'D')"; 

		     //log.info("filling CONTRACT_ITEM_ID: SQL=" +sql);
		     log.info("...filling CONTRACT_ITEM_ID" );

		     stmt = conn.prepareStatement(sql);
	         stmt.executeUpdate();
	         stmt.close();
		   }
		   catch (SQLException ex) {
		        String msg = "An error occurred at updating of field 'CONTRACT_ITEM_ID'" +
		            "in the table " + tempTable + ". " + ex.getMessage()+
		            "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		   }
		 
		// ===== filling CATALOG_STRUCTURE_ID =============//
	      try {
			  subSql = 
			         "   clw_catalog_structure cs \n" + 
			         " where \n" + 
			         "  cs.item_id = tmp.item_id AND \n" +  
			         "  cs.catalog_id = tmp.catalog_id AND \n" + 
			         "  CS.CATALOG_STRUCTURE_CD = 'CATALOG_PRODUCT'" ;

	    	  sql =" update " + tempTable + " tmp \n" + 
			     " set tmp.catalog_structure_id = (select cs.catalog_structure_id \n" + 
		         " FROM \n" + 
		         subSql+
		         ") \n" + 
		         " WHERE tmp.action<>'A' and tmp.item_id > 0 and  tmp.catalog_id >0 \n"+
		         "  AND (tmp.distributor is not null or \n" +
		         "       tmp.customer_sku is not null or \n" +
		         "       tmp.tax_exempt is not null or \n" +
		         "       tmp.action ='D')";

			     //log.info("filling CATALOG_STRUCTURE_ID: SQL=" +sql);
			     log.info("...filling CATALOG_STRUCTURE_ID");
			     
		         stmt = conn.prepareStatement(sql);
		         stmt.executeUpdate();
		         stmt.close();
		   }
		   catch (SQLException ex) {
		        String msg = "An error occurred at updating of field 'CATALOG_STRUCTURE_ID'" +
		            "in the table " + tempTable + ". " + ex.getMessage()+
		            "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		   }
		
		// ===== filling CATEGORY_ID ================//
	      try {
			  subSql = 
					 " clw_item i \n" +
					 " INNER JOIN clw_catalog_structure cs ON \n" + 
					 "        i.item_id = cs.item_id and \n" + 
					 "        CS.CATALOG_STRUCTURE_CD ='CATALOG_CATEGORY' \n" + 
					 "    where tmp.category_name = i.short_desc AND \n" + 
					 "        cs.catalog_id = tmp.catalog_id " ;

	    	  sql =" update " + tempTable + " tmp \n" + 
				 " set tmp.category_id = (select I.ITEM_ID  \n" + 
				 "   FROM  \n" +
				 subSql +
				 " ) \n" + 
				 " WHERE tmp.category_name is not null  and tmp.catalog_id >0 ";
		  //-->> generate err if  category_name does not found in the catalog
		  //-->> if 'A'  check that category exists in the account catalog too
				//log.info("filling CATEGORY_ID: SQL=" +sql);
				log.info("...filling CATEGORY_ID");

			     stmt = conn.prepareStatement(sql);
		         stmt.executeUpdate();
		         stmt.close();
		   }
		   catch (SQLException ex) {
		        String msg = "An error occurred at updating of field 'CATEGORY_ID'" +
		            "in the table " + tempTable + ". " + ex.getMessage()+
		            "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		   }
			// ===== filling ACC_CATEGORY_ID ================//
		      try {
				  subSql = 
						 " clw_item i \n" +
						 " INNER JOIN clw_catalog_structure cs ON \n" + 
						 "        i.item_id = cs.item_id and \n" + 
						 "        CS.CATALOG_STRUCTURE_CD ='CATALOG_CATEGORY' \n" + 
						 "    where tmp.category_name = i.short_desc AND \n" + 
						 "        cs.catalog_id = tmp.acc_catalog_id " ;

		    	  sql =" update " + tempTable + " tmp \n" + 
					 " set tmp.acc_category_id = (select I.ITEM_ID  \n" + 
					 "   FROM  \n" +
					 subSql +
//					 " clw_item i \n" +
//					 " INNER JOIN clw_catalog_structure cs ON \n" + 
//					 "        i.item_id = cs.item_id and \n" + 
//					 "        CS.CATALOG_STRUCTURE_CD ='CATALOG_CATEGORY' \n" + 
//					 "    where tmp.category_name = i.short_desc AND \n" + 
//					 "        cs.catalog_id = tmp.catalog_id " +
					 " ) \n" + 
					 " WHERE tmp.category_name is not null  and tmp.acc_catalog_id >0 ";
			  //-->> generate err if  category_name does not found in the catalog
			  //-->> if 'A'  check that category exists in the account catalog too
					//log.info("filling ACC_CATEGORY_ID: SQL=" +sql);
					log.info("...filling ACC_CATEGORY_ID" );

				     stmt = conn.prepareStatement(sql);
			         stmt.executeUpdate();
			         stmt.close();
			   }
			   catch (SQLException ex) {
			        String msg = "An error occurred at updating of field 'ACC_CATEGORY_ID'" +
			            "in the table " + tempTable + ". " + ex.getMessage()+
			            "*** Execute the following request to get more information : " +  sql;
			        throw new SQLException("^clw^"+msg+ "^clw^");
			   }
			  
		  
		 // ===== filling ITEM_ASSOC_ID ========//  
	      try {
			  subSql = 
			        "  clw_item_assoc ia \n" + 
			        " where \n" + 
			        "  ia.item1_id = tmp.item_id and \n" + 
			        "  ia.catalog_id = tmp.catalog_id and \n" + 
			        "  IA.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY'" ;
				  
	    	  sql =" update " + tempTable + " tmp \n" + 
				" set tmp.item_assoc_id = (select ia.item_assoc_id \n" + 
		        " FROM \n" + 
		        subSql +
//		        "  clw_item_assoc ia \n" + 
//		        " where \n" + 
//		        "  ia.item1_id = tmp.item_id and \n" + 
//		        "  ia.catalog_id = tmp.catalog_id and \n" + 
//		        "  IA.ITEM_ASSOC_CD = 'PRODUCT_PARENT_CATEGORY'" +
		        ") \n" + 
				" WHERE tmp.action<>'A' and tmp.item_id > 0 and tmp.catalog_id >0 \n" ;
   	     
		//log.info("filling ITEM_ASSOC_ID: SQL=" +sql);
		log.info("...filling ITEM_ASSOC_ID" );
       
		 stmt = conn.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();
	   }
	   catch (SQLException ex) {
	        String msg = "An error occurred at updating of field 'ITEM_ASSOC_ID'" +
	            "in the table " + tempTable + ". " + ex.getMessage()+
	            "*** Execute the following request to get more information : " +  sql;
	        throw new SQLException("^clw^"+msg+ "^clw^");
	   }
		// ======= filling ITEM_MAPPING_ID =================//
       try {
 /*   	  subSql =
	        "    clw_item_mapping im \n" + 
	        "  INNER JOIN (clw_catalog_structure cs) ON cs.Item_id = im.Item_id \n" + 
            "    and cs.bus_entity_id = IM.BUS_ENTITY_ID \n" + 
            "    and CS.CATALOG_STRUCTURE_CD = 'CATALOG_PRODUCT' \n" + 
	        "  where  \n" + 
	        "    cs.catalog_id = tmp.catalog_id and \n" + 
	        "    im.item_id = tmp.item_id and \n" + 
	        "    im.item_mapping_cd ='ITEM_DISTRIBUTOR' " ;
	        
	      sql = "Select ITEM_ID, BUS_ENTITY_ID from ( SELECT distinct im.item_id,im.bus_entity_id FROM " + 
	            tempTable + " tmp, " + 
	            subSql +
	  			") group by item_id,bus_entity_id having  count(*) >1  "	;
    	  stmt = conn.prepareStatement(sql);
    	  ResultSet rs = stmt.executeQuery();
    	  StringBuffer err = new StringBuffer();
    	  if (rs != null) {
    		  Map<Integer, List<Integer>> errItemToDistMap = new HashMap<Integer, List<Integer>>();
	    	  while (rs.next()){
	    		  Integer itemId = new Integer(rs.getInt("ITEM_ID"));
	    		  Integer distId = new Integer(rs.getInt("BUS_ENTITY_ID"));
	    		  List<Integer> distIds =  errItemToDistMap.get(itemId);
	    		  if (!Utility.isSet(distIds)){
	    			  distIds = new ArrayList<Integer>();
	    			  errItemToDistMap.put(itemId, distIds);
	    		  }
	    		  distIds.add(distId);
	    		  //err.append((err.length()==0)? ""+rs.getInt(1): ", "+rs.getInt(1));
	    	  }
	    	  if (errItemToDistMap.size() > 0) {
	    		  appendErrors("validation.catalog.loader.error.multipleDistributorForItem", errItemToDistMap);
	    	  }
    	  }
*/    	  
       	  subSql =
  	        "    clw_item_mapping im \n" + 
  	        "  where  \n" + 
  	        "    im.bus_entity_id = tmp.dist_id and \n "+
  	        "    im.item_id = tmp.item_id and \n" + 
  	        "    im.status_cd ='ACTIVE' and \n" +
  	        "    im.item_mapping_cd ='ITEM_DISTRIBUTOR' " ;
    	  
    	   sql =" update " + tempTable + " tmp \n" + 
	        " set tmp.item_mapping_id = (select distinct item_mapping_id  \n" + 
	        "  FROM  \n" + 
	        	subSql +
	        " ) \n" + 
	        " WHERE tmp.action<>'A' and tmp.item_id > 0  \n" +
	        "  AND (tmp.action = 'D' or \n" +
	        "       tmp.Distributor is not null OR tmp.Dist_sku is not null OR \n" +
	        "       tmp.dist_uom is not null OR tmp.dist_pack > 0 OR \n" +
	        "       tmp.spl is not null) ";

		     //log.info("filling ITEM_MAPPING_ID: SQL=" +sql);
		     log.info("...filling ITEM_MAPPING_ID" );
	        
	        stmt = conn.prepareStatement(sql);
	        stmt.executeUpdate();
	        stmt.close();
	    }
	    catch (SQLException ex) {
	        String msg = "An error occurred at updating of field 'ITEM_MAPPING_ID'" +
	            "in the table " + tempTable + ". " + ex.getMessage()+
	            "*** Execute the following request to get more information : " +  sql;
	        throw new SQLException("^clw^"+msg+ "^clw^");
	    }

	   //------------------- Update to Check all keys are filling correctly ---------//
      try {
	   sql = "update "+tempTable+" tmp \n" +
			 " set  tmp.to_upd = 'E' \n" +
			 "  where nvl(store_catalog_id , 0)  = 0 OR \n" +
		     "     nvl(catalog_id, 0) = 0 OR \n" +
		     "     nvl(item_id, 0) = 0 OR \n" +
		     "     nvl(contract_id, 0)=0 OR \n" +
		     "    ( nvl(dist_id, 0) = 0  and Distributor is not null ) OR \n" +
		     "    ( nvl(category_id, 0)=0 and category_name is not null  )  OR \n" +
		     "    ( nvl(acc_category_id, 0)=0 and acc_catalog_id > 0  )  OR \n" +
		         
		     "    ( nvl(contract_item_id, 0) = 0 and action <> 'A' and catalog_type_cd <> 'ACCOUNT'  ) OR \n" +
		     "    ( nvl(contract_item_id, 0) > 0 and action = 'A'  ) OR \n" +
		         
		     "    ( nvl(catalog_structure_id, 0) = 0 and action <> 'A' ) OR \n" +
		     "    ( nvl(catalog_structure_id, 0) > 0 and action = 'A' ) OR \n" +
		     "    ( nvl(catalog_structure_id, 0) = 0 and action = 'A' and nvl(acc_catalog_id, 0) = 0 and catalog_type_cd <> 'ACCOUNT') OR \n" +
		         
		     "    ( nvl(item_assoc_id, 0)=0 and action <> 'A'  and  category_id > 0  )  OR \n" +
		     "    ( nvl(item_assoc_id, 0)>0 and  action = 'A' )  OR \n" +
		 
		      //"   ( nvl(item_mapping_id, 0)=0 and  action <> 'A'  and dist_id > 0 )  OR \n" +
		      "   ( nvl(item_mapping_id, 0)=0  and dist_id = 0 and dist_sku is not null ) OR \n" +
		      "   ( nvl(item_mapping_id, 0)=0  and dist_id = 0 and dist_uom is not null ) OR \n" +
		      "   ( nvl(item_mapping_id, 0)=0  and dist_id = 0 and dist_pack is not null ) OR \n " +
		      "   ( nvl(item_mapping_id, 0)=0  and dist_id = 0 and spl is not null  )   \n" +
		      "";
		      //"   ( nvl(item_mapping_id, 0)>0  and  action = 'A' )  ";
 	     //log.info("Update to Check all keys are filling correctly: SQL=" +sql);
 	     log.info("...update to validate keys");

	   	 stmt = conn.prepareStatement(sql);
         stmt.executeUpdate();
         stmt.close();
	   }
	   catch (SQLException ex) {
	        String msg = "Find incorrect lines: an error occurred at updating of field 'TO_UPD'" +
	            "in the table " + tempTable + ". " + ex.getMessage()+
	            "*** Execute the following request to get more information : " +  sql;
	        throw new SQLException("^clw^"+msg+ "^clw^");
	   }
	   // -----------------  Update to find not unique lines ------------------
	      try {
	   	   sql = "update "+tempTable+" tmp \n" +
			   "set  tmp.to_upd = \n" +
			   "  (select flag  \n" +
			   "     from \n" +
			   "            ( select 'E' flag,  catalog_id , item_id \n" +
			   "              from  "+tempTable+" \n" +
			   "              group by catalog_id , item_id \n" +
			   "              having count(*) > 1) req \n" +
			   "     where tmp.item_id = req.item_id and tmp.catalog_id = req.catalog_id ) \n" +  
			   "WHERE  tmp.item_id > 0 and tmp.catalog_id > 0 and to_upd is null ";

	   	     //log.info("Update to find not unique lines: SQL=" +sql);
	   	     log.info("...update to find not unique lines" );
			 stmt = conn.prepareStatement(sql);
	         stmt.executeUpdate();
	         stmt.close();
		   }
		   catch (SQLException ex) {
		        String msg = "Find not unique lines: an error occurred at updating of field 'TO_UPD'" +
		            "in the table " + tempTable + ". " + ex.getMessage()+
		            "*** Execute the following request to get more information : " +  sql;
		        throw new SQLException("^clw^"+msg+ "^clw^");
		   }
	   //------------------- Check all keys are filling correctly ---------//
	   try {
		     sql = "select VERSION_NUMBER,ACTION,LINE_NUMBER," +
		            "CATALOG_TYPE_CD, "+
		     		"ACC_CATALOG_ID, CATALOG_ID, ITEM_ID, CONTRACT_ID," +
		     		"DIST_ID, ACC_CATEGORY_ID, CATEGORY_ID, CONTRACT_ITEM_ID, " +
		     		"CATALOG_STRUCTURE_ID, ITEM_ASSOC_ID, ITEM_MAPPING_ID, " +
		     		"STORE_SKU,DIST_SKU,DISTRIBUTOR," +
		     		"DIST_PACK,DIST_UOM," +
		     		"CUSTOMER_SKU,CATEGORY_NAME,TAX_EXEMPT, SPL" +
		     		" from  "+tempTable+" tmp where to_upd = 'E' ";
		     //log.info("Check all keys are filling correctly: SQL=" +sql);
		     log.info("...check that all keys are filling correctly" );
			 stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery();
			 
             while(rs.next()){
            	InboundCatalogData obj = new InboundCatalogData();
            	obj.setVersionNumber(rs.getString("VERSION_NUMBER"));
            	obj.setAction(rs.getString("ACTION"));
            	int lineNumber = rs.getInt("LINE_NUMBER");
            	String catalogTypeCd = rs.getString("CATALOG_TYPE_CD");
				int accCatalogId = rs.getInt("ACC_CATALOG_ID");
				int catalogId =rs.getInt("CATALOG_ID");
				int itemId = rs.getInt("ITEM_ID");
				int contractId = rs.getInt("CONTRACT_ID");
				int distId = rs.getInt("DIST_ID") ;
				int accCategoryId = rs.getInt("ACC_CATEGORY_ID") ;
				int categoryId = rs.getInt("CATEGORY_ID") ;
				int contractItemId = rs.getInt("CONTRACT_ITEM_ID");
				int catalogStructureId =rs.getInt("CATALOG_STRUCTURE_ID");
				int itemAssocId = rs.getInt("ITEM_ASSOC_ID");
				int itemMappingId = rs.getInt("ITEM_MAPPING_ID") ;
				obj.setStoreSku(new Integer(rs.getInt("STORE_SKU")));
				obj.setDistSku(rs.getString("DIST_SKU"));
				obj.setDistName(rs.getString("DISTRIBUTOR"));
				obj.setDistPack(new Integer(rs.getInt("DIST_PACK")));
				obj.setDistUom(rs.getString("DIST_UOM"));
				obj.setCustomerSku(rs.getString("CUSTOMER_SKU"));
				obj.setCategory(rs.getString("CATEGORY_NAME"));
				obj.setTaxExempt(Utility.isSet((rs.getString("TAX_EXEMPT"))) ? new Boolean(rs.getString("TAX_EXEMPT")): null);
				obj.setStandardProductList(Utility.isSet(rs.getString("SPL")) ? new Boolean(rs.getString("SPL")) : null);

				String itemKey = obj.getItemKey();
				String itemKeyName = obj.getItemKeyName();
				//log.info("Catalog Loader Start Time = " + catalogStartTime);
				if (itemId == 0) {
					appendErrors(""+lineNumber, "validation.catalog.loader.error.itemKeyNotExist", storeCatalogId,  itemKeyName, itemKey);
				}
				if (accCategoryId == 0 && Utility.isSet(obj.getCategory()) && accCatalogId > 0 ) {
					appendErrors(""+lineNumber, "validation.catalog.loader.error.categoryNotExistAcc", obj.getCategory(), accCatalogId);
				}
				if (categoryId == 0 && Utility.isSet(obj.getCategory())) {
					appendErrors(""+lineNumber, "validation.catalog.loader.error.categoryNotExist", obj.getCategory(), catalogId);
				}
				if (itemId> 0 && catalogId > 0 && contractId == 0 ) {
					appendErrors(""+lineNumber, "validation.catalog.loader.error.contractNotExist", catalogId);
				}
				if (distId == 0 && Utility.isSet(obj.getDistName())) {
					appendErrors(""+lineNumber, "validation.catalog.loader.error.distNameNotExist", obj.getDistName());
				}
				if (obj.getStandardProductList()!=null && distId == 0){
					appendErrors(""+lineNumber, "validation.catalog.loader.error.columnCannotBeEmptyIf", Columns.DIST_NAME.getHeader(), Columns.STANDARD_PRODUCT_LIST.getHeader());
				} 

				if (!obj.getAction().equalsIgnoreCase("A") && itemId > 0) {
					if (contractItemId == 0 ) {
						appendErrors(""+lineNumber, "validation.catalog.loader.error.contractItemNotExist", itemKeyName, itemKey, catalogId);
					}
					if (catalogStructureId == 0 && ( obj.getAction().equalsIgnoreCase("D") || obj.getTaxExempt()!= null || Utility.isSet(obj.getCustomerSku()) || Utility.isSet(obj.getDistName()) )) {
						appendErrors(""+lineNumber, "validation.catalog.loader.error.catalogStructureNotExist", itemKeyName, itemKey, catalogId);
					}
					if (itemMappingId == 0 && distId == 0) {
						appendErrors(""+lineNumber, "validation.catalog.loader.error.undefindDistributor", itemKeyName, itemKey, catalogId);
					}
					//if (itemMappingId == 0 && distId > 0) {
					//	appendErrors(""+lineNumber, "validation.catalog.loader.error.undefindDistributor", itemKeyName, itemKey, catalogId);
					//}
					
					if (itemAssocId == 0 && categoryId > 0) {
						appendErrors(""+lineNumber, "validation.catalog.loader.error.itemAssocNotExist", itemKeyName, itemKey, catalogId);
					}
				} else if (obj.getAction().equalsIgnoreCase("A") && itemId > 0) {
					
					// Skipped this validation because accCatalogId is populated if there is no item in it
					//if (accCatalogId == 0 && contractItemId == 0 && !"ACCOUNT".equals(catalogTypeCd) ) {
					//	appendErrors(""+lineNumber, "validation.catalog.loader.error.accCatalogNotExist", catalogId, storeId);
					//}
					if (contractItemId > 0 ) {
						appendErrors(""+lineNumber, "validation.catalog.loader.error.contractItemExist", itemKeyName, itemKey, catalogId);
					}
					if (catalogStructureId > 0 ) {
						appendErrors(""+lineNumber, "validation.catalog.loader.error.catalogStructureExist", itemKeyName, itemKey, catalogId);
					}
					//if (itemMappingId > 0) {
					//	appendErrors(""+lineNumber, "validation.catalog.loader.error.itemMappingExist", itemKeyName, itemKey, catalogId);
					//}
					
					if (itemAssocId > 0) {
						appendErrors(""+lineNumber, "validation.catalog.loader.error.itemAssocExist", itemKeyName, itemKey, catalogId);
					}
					
				}
			 }	         
	         
	         stmt.close();
	   }
	   catch (SQLException ex) {
	        throw new SQLException("^clw^"+ex.getMessage()+ "^clw^");
	   }
	   
	}
	
	private Set<String> checkOgNames( InboundCatalogData parsedObj) {
			
		List<String> lineOGNames = (List<String>)parsedObj.getOrderGuideNames();
		
		//-------- Validation that all provided order guides belong to provided catalogs
		if (lineOGNames == null) {
			return null;
		}
    	Set<String> errOGNames = new HashSet<String>();
    	log.info("...checkOgNames() ==> lineOGNames="+lineOGNames);
		for (String ogName : lineOGNames) {
			boolean isFoundCatalogForOG = false; 
			if (activeOGToCatalogMap!= null && !activeOGToCatalogMap.isEmpty()) {
				 List<String> catalogsForOGName = (List<String>)activeOGToCatalogMap.get(ogName);			
				 if (catalogsForOGName != null ){
					for (String catId : catalogsForOGName){
						if (parsedObj.getCatalogIds().contains(catId) &&
							activeCatalogIds!= null && 
							activeCatalogIds.contains(catId)) {
						  isFoundCatalogForOG=true;
						  break;
						} 
					}
				 }
			}
			if (!isFoundCatalogForOG){
				 errOGNames.add(ogName);
			}
		}
		log.info("checkOgNames() ==> errOGNames="+errOGNames);
		if (!errOGNames.isEmpty()){
			appendErrors(parsedObj, "validation.catalog.loader.error.ogListNotBelongToCatalogList", errOGNames, parsedObj.getCatalogIds());
		}
		return  errOGNames;
	}
	
    private Set<String> checkCatalogs (InboundCatalogData parsedObj){
		//---------- Validate that catalog Ids belong to the current store 
		StringBuffer errIds = new StringBuffer();
		Set<String> validIds = new HashSet<String>();
		
		for (Object catalogId : parsedObj.getCatalogIds()){
			if (!activeCatalogIds.contains((String)catalogId)){				
			   	log.info("...checkCatalogs() ==>Not in ACTIVE LIST : catalogId="+catalogId);
				errIds.append((errIds.length() != 0) ? "," : "");
				errIds.append(catalogId);
			} else {
				validIds.add((String)catalogId);
			}
		}
		if (errIds.length() != 0){
			appendErrors(parsedObj, "validation.catalog.loader.error.catalogNotExist", errIds, storeId);
		}
	   	//log.info("checkCatalogs() ==> activeCatalogIds="+activeCatalogIds+"; validIds="+validIds+ " / errIds="+errIds);
		return  validIds;
    }
	
    private List<String> getActiveCatalogIds( Set<Integer> catalogIds) throws Exception {
		List catalogIdList = new ArrayList();
		catalogIdList.addAll(catalogIds);
		
    	DBCriteria dbc = new DBCriteria();
		dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
		dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIdList);
		dbc.addJoinCondition(CatalogDataAccess.CLW_CATALOG, CatalogDataAccess.CATALOG_ID, CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ID);
		dbc.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);
		dbc.addJoinTableEqualTo(CatalogAssocDataAccess.CLW_CATALOG_ASSOC, CatalogAssocDataAccess.BUS_ENTITY_ID, storeId);
		
		CatalogDataVector catalogs = CatalogDataAccess.select(conn, dbc);
		List<String> activeCatalogs = new  ArrayList<String>();
		for (int i=0; i< catalogs.size(); i++){
			CatalogData cat = (CatalogData)catalogs.get(i);
			activeCatalogs.add(""+cat.getCatalogId());
		}
		return activeCatalogs;
    }

    private Map<String, List<String>> getOrderGuides( Set<String> ogNames) throws Exception {
        Map<String, List<String>> ogMap = new HashMap<String, List<String>>();
    	if (ogNames.isEmpty()){
    		return ogMap;
    	}
    	
    	List<String> ogTypes = new ArrayList<String>();
    	ogTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
    	ogTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE);
    	ogTypes.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
    	String s="'";
    	String orderGuideTypes = Utility.toCommaSting(ogTypes, new Character(s.charAt(0)));
    	String orderGuideNames =  Utility.toCommaSting(ogNames, new Character(s.charAt(0)));

    	String sql = "Select " +
		"  og.short_Desc, " +
		"  coalesce(og.catalog_Id, c.catalog_Id), "+
		"  og.bus_Entity_Id, "+
		"  og.user_Id, " +
		"  og.order_Guide_Type_Cd "+
		" from CLW_Order_Guide og," +
		"      CLW_Catalog c " +
		"where 1=1 " +
		"  and og.order_Guide_Type_Cd in ("+orderGuideTypes+") " +
		"  and og.short_Desc in ("+orderGuideNames+")" +
		"  and( (og.catalog_Id = c.catalog_Id) or "+
		"       (og.catalog_Id is null and og.bus_Entity_Id in (select ca.bus_Entity_Id from CLW_Catalog_Assoc ca where ca.catalog_Id = c.catalog_Id and ca.catalog_Assoc_Cd ='CATALOG_SITE'))"+ 
		"	  )  "+    
		"	and c.catalog_Type_Cd ='SHOPPING' " ;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<OrderGuideData> ogs = new ArrayList<OrderGuideData>();
        while(rs.next()) {    
        	OrderGuideData og = new OrderGuideData();
        	ogs.add(og);
        	og.setShortDesc(rs.getString(1));
        	og.setCatalogId(rs.getInt(2));
        	og.setBusEntityId(rs.getInt(3));
        	og.setUserId(rs.getInt(4));
        	og.setOrderGuideTypeCd(rs.getString(5));
        } 	
        pstmt.close();
        if (ogs != null) {
        	for (int i=0; i< ogs.size(); i++){        		
        		OrderGuideData og = (OrderGuideData)ogs.get(i);
        		List<String> ogDs = (List<String>)ogMap.get(og.getShortDesc());
        		if (ogDs ==null) {
        			ogDs = new ArrayList();
        			ogMap.put(og.getShortDesc(), ogDs);
        		}
    			ogDs.add(""+og.getCatalogId());
         	}
        }
    	return ogMap;
    }	

	private void appendErrors(String lineNum, String messageKey, Object... args) {
		//log.info("appendErrors ===>Begin1.1 lineNum=" + lineNum+ ", messageKey=" + messageKey);
		if (args!= null && args.length > 0){
			for (int i = 0; i < args.length; i++){
				if (args[i] != null){
					args[i]=args[i].toString();
				}
			}
		}
		ResponseError error = new ResponseError(null, null, null, messageKey, args);
		Object[] args1 = new Object[]{lineNum, error};		
		error = new ResponseError(null, null, null, "validation.catalog.loader.error.errorOnLine", args1);
		getErrorMsgs().add(error);		
		//log.info("appendErrors ===>End. getErrorMsgs()=" + getErrorMsgs());
	}
	
	private void appendErrors(String message) {
		//log.info("appendErrors ===>Begin2. message=" + message);
		ResponseError error = new ResponseError(message);
		getErrorMsgs().add(error);	
	}
	private void appendErrors(String messageKey,  Object... args) {
		//log.info("appendErrors ===>Begin3. messageKey=" + messageKey);
		
		ResponseError error = new ResponseError(null, null, null, messageKey, args);
		getErrorMsgs().add(error);	
	}
	private void appendErrors(InboundCatalogData obj, String messageKey, Object... args) {
		//log.info("appendErrors ===>Begin0. messageKey=" + messageKey);
		String lineNum = (obj!= null && obj.getLineNumber()!= null) ? obj.getLineNumber().toString(): ""+getCurrentLineNumber();
		appendErrors(lineNum, messageKey,  args);
	}	

	public String getFormatedErrorMsgs ()
	{
		//log.info("getFormatedErrorMsgs() ====> Begin.   getErrorMsgs()="+getErrorMsgs() );
		if (getErrorMsgs().size() == 0)
			return "";

		StringBuilder msg = new StringBuilder(getErrorMsgs().size() * 100);
		String temp = "";
		for (int i = 0; i < getErrorMsgs().size(); i++){
			ResponseError error = (ResponseError) getErrorMsgs().get(i);
			if (error != null && error.getArgs()!=null && error.getArgs().length>=2 && error.getArgs()[1] instanceof ResponseError){
				ResponseError error1 = (ResponseError) error.getArgs()[1];
				temp = I18nUtil.getMessage(error1.getKey(), error1.getArgs());
				error.getArgs()[1] = temp;
			}
			//log.info("getFormatedErrorMsgs() ====>  error.getKey()="+error.getKey() );
			if (Utility.isSet(error.getKey()))
				msg.append("**** " + I18nUtil.getMessage(error.getKey(), error.getArgs()) + "\r\n");
			else
				msg.append("**** " + error.getMessage() + "\r\n");
		}
			
		return msg.toString();
	}

	public Integer getCatalogCount() {
		return new Integer(catalogs.size());
	}
	//=====================================//
	protected void validateAndPopulateProperty(Columns prop,  List parsedLine, InboundCatalogData beanObj, boolean required) throws Exception{
		try {
			validateAndPopulateProperty(prop, parsedLine, beanObj, required, null, null, -1, -1, null);
		} catch (Exception ex ) {
			throw new Exception ("validateAndPopulateProperty() ===>  Name="+prop.getValue() +" ==>****" + ex.getMessage());
			
		}
	}
	protected void validateAndPopulateProperty(Columns prop,  List parsedLine, InboundCatalogData beanObj, boolean required, String defaultValue, String patternExp, int minLength, int maxLength,
			String errMessKey) throws Exception{
		String beanProp = prop.getValue();
		String beanPropHeader = prop.getHeader();
	    int  columnIndex = prop.getIndex();
	    
	    Object value = null;
		if (columnIndex < parsedLine.size()){			
			value = parsedLine.get(columnIndex);
		}	
		boolean valueSet = true;
		if (value != null && value instanceof String)
			value = ((String)value).trim();
		if (value == null || !Utility.isSet(value.toString()))
			valueSet = false;
		
		if (!valueSet && defaultValue!=null)
			value = defaultValue;
		if (!valueSet && required){
			appendErrors(beanObj, "validation.catalog.loader.error.emptyValue", beanPropHeader);
			return;
		}
					
		if (!valueSet)
			return;
		
		Method meth = Utility.getJavaBeanSetterMethod(beanObj, beanProp);
		if(meth == null){
			throw new RuntimeException("No setter found for property: "+beanProp);
		}
		try{
			Utility.populateJavaBeanSetterMethod(beanObj, meth,value,dateFormat);
		}catch(Exception e){
			if (e instanceof NumberFormatException){
				appendErrors(beanObj, "validation.catalog.loader.error.columnMustBeNumber", beanPropHeader);
			}else{
				Class lParams = meth.getParameterTypes()[0];
				if (lParams.getName().equals("java.util.Date")){
					appendErrors(beanObj, "validation.catalog.loader.error.columnMustBeDate", beanPropHeader);
				}else{
					//validation.loader.error.errorParsingData=Error parsing column '{0}': {1}
					appendErrors(beanObj, "validation.catalog.loader.error.errorParsingData", beanPropHeader, value);
				}
			}			
		}
		
		if (maxLength==-1)
			maxLength=255;
		if (minLength > 0  || maxLength > 0){
			//validation.loader.error.stringRangeLess=Column '{0}' is too short, minimum length is {1}
			Method getMethod = Utility.getJavaBeanGetterMethod(beanObj, beanProp);
			value = getMethod.invoke(beanObj, new Object [0]);
			if (!(value instanceof String))
				return;
			int length = value.toString().length();
			if (minLength > 0 && length < minLength){
				appendErrors(beanObj,"validation.catalog.loader.error.stringRangeLess", beanPropHeader, minLength);
			}
			if (maxLength > 0 && length > maxLength){
				appendErrors(beanObj, "validation.catalog.loader.error.stringRangeOut", beanPropHeader, maxLength);
			}
		}		
		if (Utility.isSet(patternExp) && (value instanceof String)) {
			Pattern valPatternExp = Pattern.compile(patternExp,Pattern.CASE_INSENSITIVE); 
			if (!valPatternExp.matcher((String)value).matches()) {
				appendErrors(beanObj, "validation.catalog.loader.error.wrongValue", beanPropHeader, value, patternExp);
			}
		}
	}
	
	
	private int getRequiredColumnCnt(String pVersion, String action){
		int reqColumnCnt = -1;
		if (Utility.isSet(pVersion)){
			if (pVersion.equals("1")){
				isVersion1 = true;
				reqColumnCnt = 6;
			}else if (pVersion.equals("2")) {
				isVersion1 = false;
		    	reqColumnCnt = 7;
			}
		}
		if ("A".equals(action)){
			isActionAdd = true;
			reqColumnCnt+=2;
		} else {
			isActionAdd = false;
		}
		return reqColumnCnt;
	}


	public enum Columns  {
		VERSION_NUMBER ("versionNumber", 0, "Version Number"),
		ACTION ("action", 1, "Action"),
		LINE_NUMBER ("lineNumber", 2, "Line Number"),
		CATALOG_ID ("catalogIds", 3,"Catalog Id"),
		STORE_SKU ("storeSku", 4,"Store SKU"),
		PRICE ("price", 5,"Price"),
		COST ("cost", 6,"Cost"),
		CATEGORY ("category", 7,"Category"),
		DIST_NAME ("distName", 8,"Distributor Name"),
		DIST_SKU ("distSku", 9, "Dist SKU"),
		DIST_UOM("distUom", 10, "Dist UOM"),
		DIST_PACK ("distPack", 11, "Dist Pack"),
		ORDER_GUIDE_NAME ("orderGuideNames", 12,"Order Guide Name"),
		TAX_EXEMPT ("taxExempt", 13, "Tax Exempt" ),
		CUSTOMER_SKU ("customerSku", 14,"Customer SKU"),
		STANDARD_PRODUCT_LIST ("standardProductList", 15,"Standard Product List");

        String value;
        int index;
        String header;
	    // Constructors
	    private Columns(String pName, int pIndex, String pHeader) {
	        value = pName;
	        index = pIndex;
	        header = pHeader;
	    }
	    public String getValue(){
	        return value;
	    }
	    public int getIndex(){
	        return index;
	    }
	    public String getHeader(){
	        return header;
	    }
	}	
	
	public class InboundCatalogData implements Serializable{
		
		private String versionNumber;
		private String action ;
		private Integer lineNumber;
		private List catalogIds;
		private Integer storeSku;
		private BigDecimal price;
		private BigDecimal cost;
		private String category;
		private String distName;
		private String distSku;
		private String distUom;
		private Integer distPack;
		private List orderGuideNames;
		private Boolean taxExempt;
		private String customerSku;
		private Boolean standardProductList;
		private Integer itemId;
		
		//private String distId;
		//private List catalogIds;
		//private List orderGuideNames;
		
		
		public String toString(){
			return "InboundCatalogData: versionNumber="+	versionNumber + ", action=" + action + 
				", lineNumber=" + lineNumber + ", catalogIds=" + catalogIds + ", storeSku=" + storeSku + ", price="+ price + 
				", cost=" + cost + ", category=" + category+ ", distName=" + distName + 
				", distSku=" + distSku + ", distUom=" +distUom + ", distPack=" +distPack +
				", orderGuideNames=" +orderGuideNames + ", taxExempt=" +taxExempt +
				", customerSku=" + customerSku + ", standardProductList=" + standardProductList ;
		}
		public String getItemKey(){
			if (isVersion2()){
				if (Utility.isSet(distUom))
					return distSku+","+ distUom;
				else 
					return distSku;
			}else{
				return (storeSku!= null) ? storeSku.toString(): "0";
			}
		}
		
		public String getItemKeyName(){
			if (isVersion2()){
					return "distSku,distUom";
			}else{
				return "storeSku";
			}
		}
		public boolean isVersion1() {
			return versionNumber.equals("1");
		}
		public boolean isVersion2() {
			return versionNumber.equals("2");
		}
		
		public Integer getItemId() {
			return itemId;
		}
		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}
		public String getVersionNumber() {
			return versionNumber;
		}
		public void setVersionNumber(String versionNumber) {
			this.versionNumber = versionNumber;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public Integer getLineNumber() {
			return lineNumber;
		}
		public void setLineNumber(Integer lineNumber) {
			this.lineNumber = lineNumber;
		}
		public List getCatalogIds() {
			return catalogIds;
		}
		public void setCatalogIds(List catalogIds) {
			this.catalogIds = catalogIds;
		}
		public Integer getStoreSku() {
			return storeSku;
		}
		public void setStoreSku(Integer storeSku) {
			this.storeSku = storeSku;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public BigDecimal getCost() {
			return cost;
		}
		public void setCost(BigDecimal cost) {
			this.cost = cost;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getDistName() {
			return distName;
		}
		public void setDistName(String distName) {
			this.distName = distName;
		}
		public String getDistSku() {
			return distSku;
		}
		public void setDistSku(String distSku) {
			this.distSku = distSku;
		}
		public String getDistUom() {
			return distUom;
		}
		public void setDistUom(String distUom) {
			this.distUom = distUom;
		}
		public Integer getDistPack() {
			return distPack;
		}
		public void setDistPack(Integer distPack) {
			this.distPack = distPack;
		}
		public List getOrderGuideNames() {
			return orderGuideNames;
		}
		public void setOrderGuideNames(List orderGuideNames) {
			this.orderGuideNames = orderGuideNames;
		}
		public Boolean getTaxExempt() {
			return taxExempt;
		}
		public void setTaxExempt(Boolean taxExempt) {
			this.taxExempt = taxExempt;
		}
		public String getCustomerSku() {
			return customerSku;
		}
		public void setCustomerSku(String customerSku) {
			this.customerSku = customerSku;
		}
		public Boolean getStandardProductList() {
			return standardProductList;
		}
		public void setStandardProductList(Boolean standardProductList) {
			this.standardProductList = standardProductList;
		}
		

		
	}
}
