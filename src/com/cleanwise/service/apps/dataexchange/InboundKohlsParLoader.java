package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.InventoryLevelDAO;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.FiscalCalenderDetailData;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.InventoryLevelData;
import com.cleanwise.service.api.value.InventoryLevelDetailDataVector;
import com.cleanwise.service.api.value.InventoryLevelView;
import com.cleanwise.service.api.wrapper.InventoryLevelViewWrapper;

import org.apache.log4j.Logger;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * 
 * @author DLING
 * Here is the file contents format. It will have one period (based on current period which could be 1 to 12) data and all other periods are left empty.
 * etrading ID	Distributor Sku	Period1	Period2	Period3	Period4	Period5	Period6	Period7	Period8	Period9	Period10 Period11 Period12 Period13
	KOHLS0007	5313860									9									
	KOHLS0007	6073034									2									
	.....
 *
 * Here are steps to process the par value
 * 1. Loader will parse the file and decide which period to process. Let say current period is 4
 * 2. Create temporary table temp_par_value with columns: line_num, site_ref_num, dist_sku, period4, site_id,item_id and inventory_level_id
 * 3. Populate table temp_par_value with following columns by parsing the file: line_num, site_ref_num, dist_sku, period4
 * 4. Populate column site_id column in table temp_par_value
 * 5. Populate column item_id column in table temp_par_value
 * 6. Validate Location exists for each  "etrading ID" and account id combination
 * 7. Validate item exists for each dist sku number and distributor combination
 * 8. Clean up data by deleting duplicated rows in temp table
 * 9. Validate if duplicated rows exists in clw_inventory for site and item in temp table. Throw exception if duplicated rows exists and data need to be fixed manually
 * 10. Remove clw_inventory_level record if no clw_inventory_level_detail exists
 * 11. Add new records into clw_inventory_level and clw_inventory_level_detail tables if item exists in temp table but not in clw_inventory_level table.
 * 12. Populate column inventory_level_id column in table temp_par_value if par value temp table is different from current value in table clw_inventory_level.par_value4
 * 13. Update table column clw_inventory_level.par_value4 with temp_par_value.period4 where inventroy_level_id match
 * 14. Update table column clw_inventory_level_detail.clw_value with temp_par_value.period4 where period and inventroy_level_id match.
 * 
 * Beside par value updated, mod_by, mod_date, pars_mod_by and pars_mod_date also been updated for table clw_inventory_level
 * Beside par value updated, mod_by, mod_date also been updated for table clw_inventory_level
 * 
 * A distributor type trading partner is need to pass in distributor id (176650) and account id (255181)
 */
public class InboundKohlsParLoader extends InboundFlatFile {

	protected Logger log = Logger.getLogger(this.getClass());
	private static final String ADD_BY = "kohlsParLoader";

	private Connection conn = null;
	private PreparedStatement insertStmt;
	private PreparedStatement updateSiteIdStmt;
	private PreparedStatement updateItemIdStmt;
    private int parValuePeriod = 0;
    private int accountId =0; //176650	Kohl's
    private int distributorId =0; //255181	KOHLSEDI
    
    private static String createTempTableSql = "create table temp_par_value(line_num number, site_ref_num varchar2(40),dist_sku varchar2(40),period#PERIOD_COL# varchar2(20),site_id number,item_id number, inventory_level_id number)";
    private static String insertTempTableSql = "INSERT into temp_par_value(line_num, site_ref_num,dist_sku,period#PERIOD_COL#,site_id,item_id)values(?, ?,?,?,0,0)";
    private static String updateSiteIdSql = "update temp_par_value t set t.site_id = (select cp.bus_entity_id from \r\n" +
    			"(select * from clw_property where bus_entity_id in \r\n" +
    			"(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = ?) and short_desc = 'SITE_REFERENCE_NUMBER') cp \r\n" +
    			"where cp.clw_value = t.site_ref_num)";
    private static String updateItemIdSql = "update temp_par_value t set t.item_id = (select cim.item_id from clw_item_mapping cim,clw_item ci \r\n" +
    			"where cim.item_id = ci.item_id \r\n" +
    			"and ci.item_status_cd = 'ACTIVE' and cim.bus_entity_id = ? and t.dist_sku = cim.item_num)";
    private static String selectDupItemsInTempSql = "select site_id,item_id,count(*) from temp_par_value group by site_id,item_id having count(*) > 1";	
    private static String deleteDupItemsInTempSql = "delete from temp_par_value a  WHERE rowid != (SELECT min(rowid) FROM temp_par_value WHERE site_id = a.site_id and item_id = a.item_id GROUP BY site_id,item_id HAVING count(*) >1)";
	private static String selectDupInventoryRow = "select cil.bus_entity_id,cil.item_id,count(*) \r\n" +
				"from clw_item ci,clw_bus_entity cbe,clw_inventory_level cil,temp_par_value t \r\n" +
				"where t.site_id = cbe.bus_entity_id and cbe.bus_entity_id = cil.bus_entity_id \r\n" +
				"and ci.item_id = t.item_id and cil.item_id = ci.item_id \r\n" +
				"group by cil.bus_entity_id,cil.item_id \r\n" +
				"having count(*) > 1 order by item_id";
    private static String selectMissingParSql = "select distinct site_id,item_id from temp_par_value where (site_id,item_id) not in \r\n" +
    			"(select site_id,item_id from \r\n" +
    			"(select t.site_id,t.item_id,t.period#PERIOD_COL#,cil.par_value#PERIOD_COL# \r\n" +
    			"from clw_item ci,clw_bus_entity cbe,clw_inventory_level cil,temp_par_value t \r\n" +
    			"where t.site_id = cbe.bus_entity_id and cbe.bus_entity_id = cil.bus_entity_id \r\n" +
    			"and ci.item_id = t.item_id and cil.item_id = ci.item_id))";
    private static String updateInventoryLevelIdSql = "update temp_par_value t set t.inventory_level_id = \r\n" +
				"  (select il.inventory_level_id from clw_inventory_level il \r\n" +
				"			where t.site_id = il.bus_entity_id \r\n" +
				"      and t.item_id = il.item_id) \r\n" +
				"where exists (select il.inventory_level_id from clw_inventory_level il \r\n" +
				"			where t.site_id = il.bus_entity_id \r\n" +
				"      and t.item_id = il.item_id \r\n" +
				"			and (il.par_value#PERIOD_COL# is null or t.period#PERIOD_COL# != il.par_value#PERIOD_COL#))";
    private static String updateInvLevelParValue = "update clw_inventory_level a set par_value#PERIOD_COL# = (select period#PERIOD_COL# from temp_par_value b where a.inventory_level_id = b.inventory_level_id), \r\n" +
				"	mod_by = '" + ADD_BY + "', mod_date = sysdate, pars_mod_by = '" + ADD_BY + "', pars_mod_Date=sysdate \r\n" +
				"where exists (select * from temp_par_value b where a.inventory_level_id = b.inventory_level_id)";
    private static String updateInvLevelDetailParVarlue = "update clw_inventory_level_detail a set clw_value = (select period#PERIOD_COL# from temp_par_value b where a.inventory_level_id = b.inventory_level_id), \r\n" +
				"	mod_by = '" + ADD_BY + "', mod_date = sysdate \r\n" +
				"where exists (select * from temp_par_value b where a.inventory_level_id = b.inventory_level_id and a.period = #PERIOD_COL#)";
    		
    public interface COLUMN {
        public static final int
                SITE_REF_NUM = 0,
                DIST_SKU = 1,
                PAR_VALUE = 2;        
    }
    

    public InboundKohlsParLoader() {
    	useHeaderLine = true;
    	hasHeaderLine = true;
        super.setSepertorChar(',');
    }

    public void translate(InputStream pIn,String pStreamType) throws Exception {
    	try{
    		conn = getConnection();
    		super.translate(pIn, pStreamType);    		
    	}finally {
    		closeConnection(conn);
    	}
    }
    
    protected void init() {
        log.info("init()=> BEGIN");
        log.info("init()=> END.");
    }

    protected void parseDetailLine(List pParsedLine) throws Exception{
    	if (pParsedLine.size() < 3){
    		throw new Exception("At least 3 columns required");
    	}
    	String siteRefNum = (String) pParsedLine.get(COLUMN.SITE_REF_NUM);
		String distSku = (String) pParsedLine.get(COLUMN.DIST_SKU);
		if (currentLineNumber==1){// get par period
			for (int i = 2; i < pParsedLine.size(); i++) {
				String parValue = (String) pParsedLine.get(i);
				if (Utility.isSet(parValue)){
					parValuePeriod = i - 1;
					break;
				}
				
			}
			
			if (parValuePeriod == 0){
				throw new Exception("Failed to find current period");
			}
			
			log.info("Current period="+parValuePeriod);
			
			Statement stmt = conn.createStatement();
			try{// drop temp table if exists
				String dropTempTable = "drop table temp_par_value";
				log.info("Drop temp table: temp_par_value");
				stmt.executeQuery(dropTempTable);
			}catch(Exception e){}
			
			// create temp table
			stmt.execute(getQuery(createTempTableSql));
			insertStmt = conn.prepareStatement(getQuery(insertTempTableSql));
			
			
		}
		String parValue = null;
		if (pParsedLine.size() > parValuePeriod)
			parValue = (String) pParsedLine.get(parValuePeriod+1);
		
		insertTempTable(siteRefNum, distSku, parValue);
    	
    }

    /**
     * Insert data from file into temp table
     * @param siteRefNum
     * @param distSku
     * @param parValue
     * @throws Exception
     */
    private void insertTempTable(String siteRefNum, String distSku, String parValue) throws Exception {
    	insertStmt.setInt(1, currentLineNumber);
    	insertStmt.setString(2, siteRefNum);
    	insertStmt.setString(3, distSku);
    	insertStmt.setString(4, parValue);
    	insertStmt.addBatch();
    	if (currentLineNumber%500==0)
    		insertStmt.executeBatch();
	}

	@Override
    protected void doPostProcessing() throws Exception {
		log.info("doPostProcessing()=> BEGIN");   
		insertStmt.executeBatch();
		insertStmt.close();
        
		// find account id that related to this loader
		int[] accountIds = getTranslator().getTradingPartnerBusEntityIds(getTranslator().getProfile().getTradingPartnerId(),
				RefCodeNames.TRADING_PARTNER_ASSOC_CD.ACCOUNT);
		if (accountIds.length==0)
			throw new Exception("Need add an Account Association to Trading Partner setup");
		accountId = accountIds[0];
		log.info("Account ID for par lader: " + accountId);   
		
		// find distributor related to this loader
		int[] distributorIds = getTranslator().getTradingPartnerBusEntityIds(getTranslator().getProfile().getTradingPartnerId(),
				RefCodeNames.TRADING_PARTNER_ASSOC_CD.DISTRIBUTOR);
		if (distributorIds.length==0)
			throw new Exception("Need add an Distributor Association to Trading Partner setup");		
		distributorId = distributorIds[0];	
		log.info("Distritutor ID for par lader: " + distributorId);   
        
        // update site id column in temp table
		log.info("Update site id column in temp table: " + updateSiteIdSql);
        updateSiteIdStmt = conn.prepareStatement(updateSiteIdSql);
        updateSiteIdStmt.setInt(1, accountId);
        updateSiteIdStmt.execute();
        
        // update item id in temp table
        log.info("Update item id in temp table: " + updateItemIdSql);
		updateItemIdStmt = conn.prepareStatement(updateItemIdSql);	
		updateItemIdStmt.setInt(1, distributorId);
		updateItemIdStmt.execute();

		// validate site exists
		Statement stmt = conn.createStatement();
		String selSiteIdIsNull = "select line_num from temp_par_value where site_id is null";
		log.info("Validate site exists: " + selSiteIdIsNull);		
		stmt.execute(selSiteIdIsNull);
		ResultSet rs = stmt.getResultSet();
		List<Integer> lineNums = new ArrayList<Integer>();
		while (rs.next()){
			lineNums.add(rs.getInt(1));			
		}
		if (lineNums.size()>0){
			appendErrorMsgs("Failed to find location on line #: " + Utility.getAsString(lineNums));
		}
		
		// validate item exists
		String selItemIdIsNull = "select line_num from temp_par_value where item_id is null";
		log.info("Validate item exists: " + selItemIdIsNull);
		stmt.execute(selItemIdIsNull);
		rs = stmt.getResultSet();
		lineNums = new ArrayList<Integer>();
		while (rs.next()){
			lineNums.add(rs.getInt(1));			
		}
		if (lineNums.size()>0){
			appendErrorMsgs("Failed to find item on line #: " + Utility.getAsString(lineNums));
		}
		
		if (getErrorMsgs().size()>0)
			 throw new Exception(super.getFormatedErrorMsgs());		
		
		
		// delete duplicated rows in temp table
		rs = stmt.executeQuery(getQuery(selectDupItemsInTempSql));
		if (rs.next()){
			stmt.execute(getQuery(deleteDupItemsInTempSql ));
		}		
		
		// Check if duplicated inventory item found in table CLW_INVENTORY_LEVEL. 
		// Data will need to be fixed manually before re-process the par loader 
		
		
		rs = stmt.executeQuery(getQuery(selectDupInventoryRow));
		while (rs.next()){
			appendErrorMsgs("Duplicated inventory item found in table clw_inventory_level for siteId: " + rs.getInt(1) + ", itemId: " + rs.getInt(2));
		}
		if (getErrorMsgs().size()>0)
			 throw new Exception(super.getFormatedErrorMsgs());
		
		BusEntityDAO bDao = new BusEntityDAO();
		FiscalCalenderView fiscalCalender = bDao.getCurrentFiscalCalenderV(conn, accountId);
        if (fiscalCalender == null) {
            String mess = "Could not find 'Fiscal Calendar' for account:" + accountId;
            throw new Exception(mess);
        }
		
        // Remove clw_inventory_level record if no clw_inventory_level_detail exists        
        String deleteInvLevel = "delete from clw_inventory_level cil \r\n" +
    			"where exists (select * from temp_par_value t where cil.item_id = t.item_id and bus_entity_id = t.site_id) \r\n" +
    			"and not exists (select * from clw_inventory_level_detail d where cil.inventory_level_id=d.inventory_level_id)";
        log.info("Remove clw_inventory_level record if no clw_inventory_level_detail exists: " + deleteInvLevel);
        stmt.execute(deleteInvLevel);
        
		// insert inventory leval and its detail if item exists in temp_par_value and not clw_inventory_level
		rs = stmt.executeQuery(getQuery(selectMissingParSql));
		while (rs.next()){
			int siteId = rs.getInt(1);
			int itemId = rs.getInt(2);
			
			InventoryLevelData il = InventoryLevelData.createValue();
            il.setBusEntityId(siteId);
            il.setItemId(itemId);
            InventoryLevelView ilView = new InventoryLevelView(il, new InventoryLevelDetailDataVector());
            InventoryLevelViewWrapper ilViewWrapper = new InventoryLevelViewWrapper(ilView);            
            ilViewWrapper.setParsModBy("loader");
            ilViewWrapper.setParsModDate(new java.util.Date(System.currentTimeMillis()));
            
            for (Object oFiscalPeriod : fiscalCalender.getFiscalCalenderDetails()) {
                FiscalCalenderDetailData fp = (FiscalCalenderDetailData) oFiscalPeriod;
                ilViewWrapper.setParValue(fp.getPeriod(), 0);
            }

            InventoryLevelDAO.updateInventoryLevelView(conn, ilViewWrapper.getInventoryLevelView(), ADD_BY);
		}
		
		// update inventory_level_id in temp_par_value table if par value changed
		stmt.execute(getQuery(updateInventoryLevelIdSql));
		
		// update clw_inventory_level
		stmt.execute(getQuery(updateInvLevelParValue));
		
		// update clw_inventory_level_detail.clw_value
		stmt.execute(getQuery(updateInvLevelDetailParVarlue));
		
		// double check data
		String checkSql = "select cild.inventory_level_detail_id,t.period#PERIOD_COL#,cild.clw_value \r\n" +
    			"from clw_item ci,clw_bus_entity cbe,clw_inventory_level cil,temp_par_value t,clw_inventory_level_detail cild \r\n" +
    			"where t.site_id = cbe.bus_entity_id and cbe.bus_entity_id = cil.bus_entity_id \r\n" +
    			"and cil.inventory_level_id = cild.inventory_level_id \r\n" +
    			"and cild.period =#PERIOD_COL# \r\n" +
    			"and ci.item_id = t.item_id and cil.item_id = ci.item_id \r\n" +
    			"and (t.period#PERIOD_COL# != cild.clw_value or cil.par_value#PERIOD_COL#!=cild.clw_value)";
		rs = stmt.executeQuery(getQuery(checkSql));
		if (rs.next()){
			throw new Exception("Par value has not updated correctly");
		}
		
        log.info("doPostProcessing()=> END.");

    }
        
	private String getQuery(String sql){
		sql = sql.replaceAll("#PERIOD_COL#", ""+parValuePeriod);
		log.info("SQL: " + sql);
		return sql;
	}
}


