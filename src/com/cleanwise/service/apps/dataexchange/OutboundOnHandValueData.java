package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.*;

import org.apache.log4j.Logger;


public class OutboundOnHandValueData extends InterchangeOutboundSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private int storeId = 0;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private static final String SEPERATOR_STR = "|"; //the separator used for the file
	private static final String QUOTE_STR = "\""; //the quote used for the file
    private static List<Character> specialChars = new ArrayList<Character>();
	static{
		specialChars.add('\n');
		specialChars.add('\r');
	}
    
    public OutboundOnHandValueData() {
        seperateFileForEachOutboundOrder = true;
    }
    
    public void buildTransactionContent() throws Exception {    	
    	storeId = (Integer)currOutboundReq.getGenericMap().get("STORE_ID");    	
    	
    	// reset the file name with store id in it
    	getTranslator().setOutputFileName(this.getFileName());
    	interchangeD.setEdiFileName(getTranslator().getOutputFileName());
    	
    	
    	Connection conn = getConnection();
		try {			
			
			List<OnHandValueLine> lines = getOnHandValues(conn, storeId);
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
				
				OnHandValueLine ordTotline = (OnHandValueLine) it.next();
				sb.append(ordTotline.getLine()+"\r\n");
				it.remove();
			}
			if (sb.length() > 0){
				translator.writeOutputStream(sb.toString());
			}
			
			log.info("Total order item records : " + (i));			
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
	
    
    public String getFileName()throws Exception{   //onhandvalue_392146_06272011030533.txt 	
    	SimpleDateFormat frmt = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String now = frmt.format(new java.util.Date());
        String fileName = "onhandvalue_" + storeId + "_" + now + getFileExtension();
        return fileName;
    }
    public String getFileExtension() throws Exception{
        return ".txt";
    }
    
    public String getTimeStampString(Date date, SimpleDateFormat frmt){
    	return frmt.format(date);
    }
    
	/**
	 * Get list of inventory item for a giving store and satisfy following condition
	 *  1. Is a active site
	 * 	2. Site has active CORPORATE schedule (has Schedule Dates greater than current run date)
	 *  3. Site property ALLOW_CORPORATE_SCHED_ORDER is set to true
	 *  4. Site has inventory items
	 * 
	 * @param conn
	 * @param runDate
	 * @return list of site information related to corporate schedule
	 * @throws Exception
	 */
    private ArrayList<OnHandValueLine> getOnHandValues(Connection conn, int storeId) throws Exception {
    	log.info("getOnHandValues(): storeId="+storeId);
    	SimpleDateFormat frmt = new java.text.SimpleDateFormat("MM/dd/yyyy");
        String reportDate = frmt.format(new java.util.Date());
    	PropertyService propEjb = APIAccess.getAPIAccess().getPropertyServiceAPI();
		String storeTypeCd = propEjb.getBusEntityProperty(storeId, RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
		String sql = "select account.short_desc || '|' || site.short_desc || '|' || c.short_desc, \r\n" +
			"store.short_desc store_name, \r\n" +
			"account.bus_entity_id account_id, \r\n" +
			"account.short_desc account_name, \r\n" +
			"site.bus_entity_id site_id, \r\n" +
			"site.short_desc site_name, \r\n" +
			"(select max(clw_value) from clw_property p where p.bus_entity_id = site.bus_entity_id and p.short_desc = 'SITE_REFERENCE_NUMBER') site_ref_num, \r\n" +
			"(select max(clw_value) from clw_property p where p.bus_entity_id = site.bus_entity_id and p.short_desc = 'DIST_SITE_REFERENCE_NUMBER') dist_site_ref_num, \r\n" +
			"ci.sku_num, \r\n" +
			"(select max(im.item_num) from clw_item_mapping im where ci.item_id = im.item_id and im.item_mapping_cd = 'ITEM_DISTRIBUTOR') dist_sku_num, \r\n" +
			"(select max(im.item_num) from clw_item_mapping im where ci.item_id = im.item_id and im.item_mapping_cd = 'ITEM_MANUFACTURER') manuf_sku_num,\r\n" +
			"ci.item_id, \r\n" +
			"(select max(category.short_desc) from clw_catalog c, clw_catalog_assoc ca, clw_item category, clw_item_assoc  itmassoc \r\n" +
			"  where c.catalog_id = ca.catalog_id \r\n" +
			"  and c.catalog_type_cd = 'STORE' \r\n" +
			"  and c.catalog_status_cd = 'ACTIVE' \r\n" +
			"  and ca.bus_entity_id = store.bus_entity_id \r\n" +
			"  and c.catalog_id = itmassoc.catalog_id \r\n" +
			"  and itmassoc.item1_id = ci.item_id \r\n" +
			"  and category.item_id =itmassoc.item2_id \r\n" +
			"  and  category.item_type_cd = 'CATEGORY') category_name, \r\n" +
			"ci.short_desc prod_name, \r\n" +
			"(select max(clw_value) from clw_item_meta uom where ci.item_id = uom.item_id and uom.name_value = 'UOM') uom, \r\n" +
			"(select max(clw_value) from clw_item_meta pack where ci.item_id = pack.item_id and pack.name_value = 'PACK') pack, \r\n" +
			"(select max(clw_value) from clw_item_meta isize where ci.item_id = isize.item_id and isize.name_value = 'SIZE') isize, \r\n" +
			"(select max(dist.short_desc) from clw_item_mapping im, clw_bus_entity dist where ci.item_id = im.item_id and im.item_mapping_cd = 'ITEM_MANUFACTURER' and im.bus_entity_id = dist.bus_entity_id) manuf_name, \r\n" +
			"(select max(dist.short_desc) from clw_item_mapping im, clw_bus_entity dist where ci.item_id = im.item_id and im.item_mapping_cd = 'ITEM_DISTRIBUTOR' and im.bus_entity_id = dist.bus_entity_id) dist_name, \r\n" +
			"(select max(qty_on_hand) from clw_inventory_level il where il.item_id = ci.item_id and il.bus_entity_id = site.bus_entity_id) qty_on_hand, \r\n" +
			"(select CASE WHEN max(im.item_mapping_id) IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END \r\n" +
			"  from clw_item_mapping im \r\n" +
			"  where im.item_id = ci.item_id \r\n" +
			"  and im.item_mapping_cd = 'ITEM_CERTIFIED_COMPANY') green_item, \r\n" +
			"(select max(clw_value) \r\n" +
			"  from clw_item_meta compl_item \r\n" +
			"  where compl_item.item_id = ci.item_id \r\n" +
			"  and compl_item.name_value = 'Compliance Item') compliance_item, \r\n" +
			"cs.cost_center_id, \r\n" +
			"(select max(ui_name) from clw_country where country_code = substr(contract.locale_cd, 4)) country, \r\n" +
			"(select max(global_code) from clw_currency c where c.locale = contract.locale_cd) currency, \r\n" +
			"citem.amount price \r\n" +
			"from  clw_bus_entity store, clw_bus_entity account, clw_bus_entity site, \r\n" +
			"  clw_bus_entity_assoc asAssoc, clw_bus_entity_assoc saAssoc, clw_catalog c, clw_catalog_assoc ca, clw_catalog_structure cs, \r\n" +
			"  clw_item ci, clw_contract contract, clw_contract_item citem, clw_inventory_items ii, \r\n " +
			"  (SELECT distinct TO_NUMBER(value) site_id \r\n" + // site id from corporate schedule
			"    FROM clw_schedule sch join clw_schedule_detail schd \r\n" +
			"    ON sch.schedule_id = schd.schedule_id \r\n" +
			"    WHERE sch.schedule_id in ( select distinct schedule_id from clw_schedule_detail schd \r\n" +
			"    	WHERE sch.schedule_id = schd.schedule_id \r\n" +
			"    	and sch.bus_entity_id = " + storeId + " \r\n" +
			"    	and schedule_detail_cd = 'ALSO_DATE' \r\n" +
			"    	and To_Date (value,'mm/dd/yyyy') > to_date('" + reportDate + "', 'mm/dd/yyyy')) \r\n" +
			"    and schd.SCHEDULE_DETAIL_CD = 'SITE_ID') schedSite \r\n" +
			"where store.bus_entity_id = asAssoc.bus_entity2_id \r\n" +
			"and asassoc.bus_entity_assoc_cd = 'ACCOUNT OF STORE' \r\n" +
			"and asassoc.bus_entity1_id = account.bus_entity_id  \r\n" +
			"and saAssoc.bus_entity1_id = site.bus_entity_id \r\n" +
			"and saAssoc.bus_entity_assoc_cd = 'SITE OF ACCOUNT' \r\n" +
			"and account.bus_entity_id = saassoc.bus_entity2_id \r\n" +
			"and site.bus_entity_status_cd = 'ACTIVE' \r\n" +
			"AND EXISTS (select p.clw_value FROM CLW_PROPERTY p \n" +
			"   WHERE p.bus_entity_id = site.bus_entity_id \n" +
			"   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "' \n" +
			"   and upper(p.clw_value) = 'TRUE' \n" +
			"   ) \n" +
			"and ca.bus_entity_id = site.bus_entity_id \r\n" +
			"and ca.catalog_id = c.catalog_id \r\n" +
			"and c.catalog_type_cd = 'SHOPPING' \r\n" +
			"and c.catalog_status_cd = 'ACTIVE' \r\n" +
			"and c.catalog_id = cs.catalog_id \r\n" +
			"and cs.item_id = ci.item_id \r\n" +
			"and cs.catalog_structure_cd = 'CATALOG_PRODUCT' \r\n" +
			"and contract.catalog_id = ca.catalog_id \r\n" +
			"and contract.contract_id = citem.contract_id \r\n" +
			"and citem.item_id = ci.item_id \r\n" +
			"and ii.item_id = ci.item_id \r\n" +
			"and ii.bus_entity_id = account.bus_entity_id \r\n" +
			"and schedSite.site_id = site.bus_entity_id";
    	log.info(sql);
    	Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int i = 1;
		ArrayList<OnHandValueLine> lines = new ArrayList<OnHandValueLine>();
		while (rs.next()){
			if (i++ % 5000 == 0){
				log.info("line count: " + i + " " + new Date());
			}	
			String key = rs.getString(1);
			List<String> line = new ArrayList<String>();
			line.add(storeId+"");
			line.add(escapeSpecialChars(rs.getString("store_name")));
			line.add(rs.getString("account_id"));
			line.add(escapeSpecialChars(rs.getString("account_name")));
			line.add(rs.getString("site_id"));
			line.add(escapeSpecialChars(rs.getString("site_name")));
			line.add(Utility.strNN(rs.getString("site_ref_num")));
			line.add(Utility.strNN(rs.getString("dist_site_ref_num")));
			line.add(reportDate);
			
			line.add(rs.getString("sku_num"));
			line.add(Utility.strNN(rs.getString("dist_sku_num")));
			line.add(rs.getString("item_id"));
			
			line.add(escapeSpecialChars(rs.getString("category_name")));
			line.add(escapeSpecialChars(rs.getString("prod_name")));
			line.add(rs.getString("uom"));
			line.add(Utility.strNN(rs.getString("pack")));
			line.add(Utility.strNN(rs.getString("isize")));
			line.add(Utility.strNN(rs.getString("manuf_sku_num")));
			line.add(escapeSpecialChars(rs.getString("manuf_name")));
			line.add(escapeSpecialChars(rs.getString("dist_name")));
			line.add(Utility.strNN(rs.getString("qty_on_hand")));
			
			String complianceItem = getComplianceItem(rs.getString("compliance_item"));	
			line.add(complianceItem);
			line.add(rs.getString("green_item"));
			int costCenterId=rs.getInt("cost_center_id");
			line.add(costCenterId==0? "-1" : rs.getString("cost_center_id"));
			line.add(rs.getString("country"));			
			line.add(rs.getString("currency"));
			line.add(rs.getString("price"));
			lines.add(new OnHandValueLine(key,line));
		}	
		stmt.close();
		// sort in java code instead of sql due to larger quantity records
		BeanComparator comparator = new BeanComparator(new String[]{"getSortField"});
		Collections.sort(lines,comparator);
		return lines;
	}
    
    private String getComplianceItem(String complianceItem) {
    	if (Utility.isSet(complianceItem)){
    		complianceItem = complianceItem.toUpperCase();
			if (Utility.isEqual(complianceItem, "YES"))
				return "Yes";
			else if (Utility.isEqual(complianceItem, "NO"))
				return "No";
			else if (Utility.isEqual(complianceItem, "POTENTIAL"))
				return "Potential";
			else return "";
		} else 
			return "";
	}
    
	public class OnHandValueLine {
    	private String sortField;
    	List<String> rowData;
		public OnHandValueLine(String sortField, List<String> rowData) {
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
				if (record==null)
					record="";
				sb.append(record).append(SEPERATOR_STR);
			}
			String temp = sb.toString();
			return temp.substring(0, temp.length()-1);
		}
    }	
}
