package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.*;

import org.apache.log4j.Logger;


public class OutboundOrderTotalsDiversey extends InterchangeOutboundSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private int storeId = 0;
    private static final String SEPERATOR_STR = "|"; //the separator used for the file
	private static final String QUOTE_STR = "\""; //the quote used for the file
    private static List<Character> specialChars = new ArrayList<Character>();
	static{
		specialChars.add('\n');
		specialChars.add('\r');
	}
    
    public OutboundOrderTotalsDiversey() {
        seperateFileForEachOutboundOrder = true;
    }
    
    public void buildTransactionContent() throws Exception {
    	SimpleDateFormat frmt = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	String dateFmt = "MM/DD/YYYY HH24:MI:SS";
        Date processDate = new Date();
        String processTimeStampStr = getTimeStampString(processDate, frmt);
        String temp = getTranslator().getConfigPropertyByPropertyTypeCd("JD Brands Group ID");
        int jdBrandsManufGroupId = 0;
        if (Utility.isSet(temp))
        	jdBrandsManufGroupId = Integer.parseInt(temp);
        boolean filterOutQuantity = false;
        temp = getTranslator().getConfigPropertyByPropertyTypeCd("Filter Out Quantity");
        if (Utility.isSet(temp))
        	filterOutQuantity = Utility.isTrue(temp);        
        
    	storeId = (Integer)currOutboundReq.getGenericMap().get("STORE_ID");    	
    	
    	// reset the file name with store id in it
    	getTranslator().setOutputFileName(this.getFileName());
    	interchangeD.setEdiFileName(getTranslator().getOutputFileName());
    	
    	
    	Connection conn = getConnection();
        String lastProcessTimeStamp = null;
        PropertyData propertyD = null;
		
		try {			
			DBCriteria dbc = new DBCriteria();
	        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
	        dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.ORDER_TOTALS_LAST_TIMESTAMP);
	        PropertyDataVector pDV = PropertyDataAccess.select(conn,dbc);
	        if (pDV.size() > 0){
	        	propertyD = (PropertyData) pDV.get(0);
	        }
	                       
			if (propertyD != null && Utility.isSet(propertyD.getValue())){
				lastProcessTimeStamp = propertyD.getValue();
			}
			
			List<OrderTotalsLine> lines = getOrderItemDetails(conn, storeId, dateFmt,
					lastProcessTimeStamp, null, jdBrandsManufGroupId, filterOutQuantity);
			int i=0;
			Iterator it = lines.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()){
				if (i++ % 5000 == 0){
					translator.writeOutputStream(sb.toString());
					sb = new StringBuffer();
					log.info("line count2: " + i + " " + new Date());
					System.gc();
				}	
				
				OrderTotalsLine ordTotline = (OrderTotalsLine) it.next();
				sb.append(ordTotline.getLine()+"\r\n");
				it.remove();
			}
			if (sb.length() > 0){
				translator.writeOutputStream(sb.toString());
			}
			
			log.info("Total order item records : " + (i));
			if (lastProcessTimeStamp == null){
				propertyD = PropertyData.createValue();
				propertyD.setBusEntityId(storeId);
				propertyD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
				propertyD.setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.ORDER_TOTALS_LAST_TIMESTAMP);
				propertyD.setShortDesc(propertyD.getPropertyTypeCd());
				propertyD.setValue(processTimeStampStr);
				propertyD.setAddBy("OutboundOrderTotalsDiversey");
				propertyD.setModBy("OutboundOrderTotalsDiversey");
				PropertyDataAccess.insert(conn, propertyD);
			}else{
				propertyD.setValue(processTimeStampStr);
				PropertyDataAccess.update(conn, propertyD);
			}
			
		} finally {
	    	closeConnection(conn);
	    }

    }
    private String escapeSpecialChars(String aRecord){
    	return escapeSpecialChars(aRecord, QUOTE_STR, SEPERATOR_STR);
    }
    
    /**
	 * escape character '\n' and '\r' with space and seperator character with quoted("") String
	 * E.g. 
	 * 1. "string1\nString2\rString3" will become  "string1 String2 String3" -- Escape '\n' and '\r' 
	 * "string1|String2|String3" will become ""string1|String2|String3""	 -- Escape '|'
	 * "string1"|String2|String3" will become ""string1""|String2|String3""	 -- Escape '|' and '"' if quote and seperator character both exist
	 */
	private String escapeSpecialChars(String aRecord, String quote, String seperator){
		StringBuffer theLine = new StringBuffer();
		
		if(aRecord == null){
			aRecord = "";
		}
		Iterator<Character> itSpecalChars = specialChars.iterator();
		while (itSpecalChars.hasNext()) {
			Character specialCharStr = (Character) itSpecalChars.next();
			aRecord = aRecord.replace(specialCharStr, ' ');
		}
		if(aRecord.contains(seperator)){
			if(aRecord.contains(quote)){
				//escape the quote char
				aRecord = aRecord.replace(quote, quote+quote);
			}
			theLine.append(quote);
			theLine.append(aRecord);
			theLine.append(quote);
		}else{
			theLine.append(aRecord);
		}
		return theLine.toString();
	}
	
    
    public String getFileName()throws Exception{   //orderdata_392146_06272011030533.txt 	
    	SimpleDateFormat frmt = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String now = frmt.format(new java.util.Date());
        String fileName = "orderdata_" + storeId + "_" + now + getFileExtension();
        return fileName;
    }
    public String getFileExtension() throws Exception{
        return ".txt";
    }
    
    public String getTimeStampString(Date date, SimpleDateFormat frmt){
    	return frmt.format(date);
    }
    
    public ArrayList getOrderItemDetailTable(Connection con, int storeId, String dateFmt,
			String begDateS, String endDateS, int jdBrandsManufGroupId, boolean filterOutQuantity) throws Exception {
    	ArrayList<OrderTotalsLine> lines = getOrderItemDetails(con, storeId, dateFmt,
    			begDateS, endDateS, jdBrandsManufGroupId, filterOutQuantity);
    	ArrayList<List<String>> returnTable = new ArrayList<List<String>>();
    	for (OrderTotalsLine line : lines){
    		returnTable.add(line.rowData);
    	}
    	return returnTable;
    	
    }
    private ArrayList<OrderTotalsLine> getOrderItemDetails(Connection conn, int storeId, String dateFmt,
			String begDateS, String endDateS, int jdBrandsManufGroupId, boolean filterOutQuantity) throws Exception {
    	log.info("getOrderItemDetails(): storeId="+storeId + ", filterOutQuantity="+filterOutQuantity+", jdBrandsManufGroupId="+jdBrandsManufGroupId);
    	log.info("getOrderItemDetails(): dateFmt="+dateFmt + ", begDateS="+begDateS+", endDateS="+endDateS);
    	
    	String sql = "select * from ( \r\n" + 
    	"SELECT account.short_desc || '|' || site.short_desc || '|' || ord.order_num, \r\n" + 
    	"store.short_desc storeName, \r\n" +
    	"account.short_desc accountName, \r\n" +
    	"site.short_desc siteName, \r\n" +
    	"ord.order_num confirmationNum, \r\n" +
    	"ord.request_po_num poNum, \r\n" +
    	"TO_CHAR(NVL(revised_order_date, original_order_date), 'MM/DD/YYYY') orderDate, \r\n" +
    	"(select max(oim.clw_value) from clw_order_item_meta oim \r\n" +
      	"  where oi.order_item_id = oim.order_item_id(+) \r\n" +
      	"  and oim.name(+) = 'CATEGORY_NAME') categoryName, \r\n" +
    	"(select max(category.short_desc) from clw_catalog c, clw_catalog_assoc ca, clw_item category, clw_item_assoc  itmassoc \r\n" +
      	"  where c.catalog_id = ca.catalog_id \r\n" +
      	"  and c.catalog_type_cd = 'STORE' \r\n" +
      	"  and c.catalog_status_cd = 'ACTIVE' \r\n" +
      	"  and ca.bus_entity_id =ord.store_id \r\n" +
      	"  and c.catalog_id = itmassoc.catalog_id \r\n" +
      	"  and itmassoc.item1_id = oi.item_id \r\n" +
      	"  and category.item_id =itmassoc.item2_id \r\n" +
      	"  and  category.item_type_cd = 'CATEGORY') categoryName2, \r\n" +
    	"NVL(oi.cust_item_sku_num,item_sku_num) cust_sku_num, \r\n" +
    	"oi.dist_item_sku_num, \r\n" +
    	"(select max(clw_value) from clw_item_meta uom where oi.item_id = uom.item_id and uom.name_value = 'UOM') uom, \r\n" +
    	"(select max(clw_value) from clw_item_meta pack where oi.item_id = pack.item_id and pack.name_value = 'PACK') pack, \r\n" +
    	"(select max(clw_value) from clw_item_meta isize where oi.item_id = isize.item_id and isize.name_value = 'SIZE') isize, \r\n" +
    	"oi.manu_item_sku_num, \r\n" +
    	"(select max(mf.short_desc) from clw_bus_entity mf, clw_item_mapping mfim \r\n" +
    	"  where mf.bus_entity_id = mfim.bus_entity_id \r\n" +
    	"  and mf.bus_entity_type_cd = 'MANUFACTURER' \r\n" +
    	"  and mfim.item_id = oi.item_id) manufName, \r\n" +
    	"(select max(dist.short_desc) from clw_bus_entity dist \r\n" + 
        " where dist.erp_num = oi.dist_erp_num \r\n" + 
        " and dist.bus_entity_type_cd = 'DISTRIBUTOR' \r\n" +
        ") distName, \r\n" +
    	"ci.short_desc itemName, \r\n" +
    	"(select CASE WHEN max(im.item_mapping_id) IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END \r\n" +
    	"  from clw_item_mapping im \r\n" +
    	"  where im.item_id = oi.item_id \r\n" +
    	"  and im.item_mapping_cd = 'ITEM_CERTIFIED_COMPANY') greenItem, \r\n" +
    	"(select max(clw_value) \r\n" +
    	"  from clw_item_meta compl_item \r\n" +
    	"  where compl_item.item_id = oi.item_id \r\n" +
    	"  and compl_item.name_value = 'Compliance Item') complianceItem, \r\n" +
    	"oi.sale_type_cd, \r\n" +
    	"oi.total_quantity_ordered  quantity, \r\n" +
    	"oi.cust_contract_price*total_quantity_ordered lineTotal, \r\n" +
    	"ord.currency_cd, \r\n" +
    	"oi.order_item_status_cd, \r\n" +
    	"ord.order_status_cd,  \r\n";
    	if (jdBrandsManufGroupId > 0){
    		sql += "(SELECT CASE WHEN max(m.bus_entity_id) IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END \r\n" +
			   "  FROM CLW_BUS_ENTITY m, CLW_BUS_ENTITY_ASSOC ba, clw_bus_entity s, clw_item_mapping mfim \r\n" +
			   "  WHERE m.BUS_ENTITY_ID IN ( \r\n" +
			   "    SELECT DISTINCT BUS_ENTITY_ID FROM CLW_GROUP_ASSOC WHERE GROUP_ID = 412 AND GROUP_ASSOC_CD = 'BUS_ENTITY_OF_GROUP') \r\n" +
			   "  AND m.bus_entity_id = ba.bus_entity1_id \r\n" +
			   "  and s.bus_entity_id = ba.bus_entity2_id \r\n" +
			   "  and ba.bus_entity_assoc_cd = 'MANUFACTURER OF STORE' \r\n" +
			   "  and s.bus_entity_id = ord.store_id \r\n" +
			   "  and mfim.bus_entity_id = m.bus_entity_id \r\n" +
			   "  and mfim.item_id=oi.item_id) jdBrand, \r\n";
    	}
    	sql += "ord.store_id,  \r\n" +
    	"ord.account_id, \r\n" +
    	"ord.site_id, \r\n" +
    	"ord.order_id, \r\n" +
    	"oi.order_item_id, \r\n" +
    	"sl.budget_year, sl.budget_period, sl.cost_center_id \r\n" +
    	"from  CLW_ORDER ord, CLW_ORDER_ITEM oi,CLW_ITEM ci, CLW_BUS_ENTITY store, CLW_BUS_ENTITY account, CLW_BUS_ENTITY site, CLW_SITE_LEDGER sl \r\n" +
    	"where ord.ORDER_STATUS_CD IN ('ERP Released','Invoiced') \r\n" +
    	"and (oi.order_item_status_cd NOT IN ('CANCELLED') OR oi.order_item_status_cd IS NULL) \r\n" +
    	"and  ord.store_id = store.bus_entity_id \r\n" +
    	"and  ord.account_id = account.bus_entity_id \r\n" +
    	"and  ord.site_id = site.bus_entity_id \r\n" +
    	"and ord.order_id = oi.order_id \r\n" +
    	"and ci.item_id = oi.item_id \r\n" +
    	"and sl.order_id(+) =  oi.order_id \r\n" +
    	"and sl.cost_center_id(+) = oi.cost_center_id \r\n" +
    	"and ord.store_id = " + storeId + " \r\n";
    	if (Utility.isSet(begDateS))
    		sql += "and ord.mod_date > to_date('" + begDateS + "', '" + dateFmt + "') \r\n";  
    	if (Utility.isSet(endDateS))
    		sql += "and ord.mod_date <= to_date('" + endDateS + "', '" + dateFmt + "')+1 \r\n";
    	sql += ") \r\n";
    	if (jdBrandsManufGroupId > 0 && !filterOutQuantity){
			sql += "WHERE jdBrand = 'TRUE'";
    	}
    	log.info(sql);
    	Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int i = 1;
		ArrayList<OrderTotalsLine> lines = new ArrayList<OrderTotalsLine>();
		while (rs.next()){
			if (i++ % 5000 == 0){
				log.info("line count: " + i + " " + new Date());
			}	
			String key = rs.getString(1);
			List<String> line = new ArrayList<String>();
			line.add(escapeSpecialChars(rs.getString("storeName")));
			line.add(escapeSpecialChars(rs.getString("accountName")));
			line.add(escapeSpecialChars(rs.getString("siteName")));
			line.add(escapeSpecialChars(rs.getString("confirmationNum")));
			line.add(escapeSpecialChars(rs.getString("poNum")));
			String orderDate = rs.getString("orderDate");
			line.add(orderDate);
			String categoryName = escapeSpecialChars(rs.getString("categoryName"));
			if (!Utility.isSet(categoryName)){
				categoryName = escapeSpecialChars(rs.getString("categoryName2"));
				if (!Utility.isSet(categoryName)){
					throw new Exception ("Failed to get category name for order_item_id="+rs.getString("order_item_id"));
				}
			}
			line.add(categoryName);
			line.add(escapeSpecialChars(rs.getString("cust_sku_num")));
			line.add(escapeSpecialChars(rs.getString("dist_item_sku_num")));
			line.add(rs.getString("uom"));
			line.add(rs.getString("pack"));
			line.add(rs.getString("isize"));
			line.add(escapeSpecialChars(rs.getString("manu_item_sku_num")));
			line.add(escapeSpecialChars(rs.getString("manufName")));
			line.add(escapeSpecialChars(rs.getString("distName")));
			line.add(escapeSpecialChars(rs.getString("itemName")));
			line.add(rs.getString("greenItem"));
			String complianceItem = escapeSpecialChars(rs.getString("complianceItem"));
			if (Utility.isSet(complianceItem)){
				complianceItem = complianceItem.toUpperCase();
				if (Utility.isEqual(complianceItem, "YES"))
					complianceItem = "Yes";
				else if (Utility.isEqual(complianceItem, "NO"))
					complianceItem = "No";
				else if (Utility.isEqual(complianceItem, "POTENTIAL"))
					complianceItem = "Potential";
				else complianceItem = "";
			} else 
				complianceItem = "";
						
			line.add(complianceItem);			
			line.add(rs.getString("sale_type_cd"));
			boolean isJdBrand = Utility.isEqual("TRUE", rs.getString("jdBrand"));
			// filter out quantity if not a JD brand and compliance item not equal to "No"
			if (filterOutQuantity && !isJdBrand && !Utility.isEqual(rs.getString("complianceItem"), "No")){
				line.add("");
			}				
			else
				line.add(rs.getString("quantity"));
			
			line.add(rs.getString("lineTotal"));
			line.add(rs.getString("currency_cd"));
			line.add(rs.getString("order_item_status_cd"));
			line.add(rs.getString("order_status_cd"));
			line.add(rs.getString("store_id"));
			line.add(rs.getString("account_id"));
			line.add(rs.getString("site_id"));
			line.add(rs.getString("order_id"));
			line.add(rs.getString("order_item_id"));
			if (rs.getString("cost_center_id")==null){
				line.add("01");
				int endIdx = orderDate.indexOf('/');
				String month = orderDate.substring(0, endIdx);
				if (month.charAt(0) == '0')
					month = month.substring(1);
				line.add(month);
				line.add("-1");
				
			}else{
				line.add(rs.getString("budget_year"));
				line.add(rs.getString("budget_period"));
				line.add(rs.getString("cost_center_id"));
			}
			lines.add(new OrderTotalsLine(key,line));
		}	
		stmt.close();
		// sort in java code instead of sql due to larger quantity records
		BeanComparator comparator = new BeanComparator(new String[]{"getSortField"});
		Collections.sort(lines,comparator);
		return lines;
	}
    
    public class OrderTotalsLine {
    	private String sortField;
    	List<String> rowData;
		public OrderTotalsLine(String sortField, List<String> rowData) {
			this.sortField = sortField;
			this.rowData = rowData;
		}
		
		public void setSortField(String sortField) {
			this.sortField = sortField;
		}

		public String getSortField() {
			return sortField;
		}
		
		public String getLine(){
			StringBuffer sb = new StringBuffer();
			for (String record : rowData){
				sb.append(record).append(SEPERATOR_STR);
			}
			String temp = sb.toString();
			return temp.substring(0, temp.length()-1);
		}
    }	
}
