package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.tree.ReportFactory;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.transformer.SimpleReportTransformer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.*;

import org.apache.log4j.Logger;


public class OutboundProcurementSiteDataXML extends InterchangeOutboundSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private int storeId = 0;    
    
    public OutboundProcurementSiteDataXML() {
        seperateFileForEachOutboundOrder = true;
    }
    
    public void buildTransactionContent() throws Exception {
        
    	storeId = (Integer)currOutboundReq.getGenericMap().get("STORE_ID");
    	// reset the file name with store id in it
    	getTranslator().setOutputFileName(this.getFileName());
    	interchangeD.setEdiFileName(getTranslator().getOutputFileName());
    	String sqlSelectSite = "select store.short_desc store_name, account.bus_entity_id account_id, account.short_desc account_name, account.bus_entity_status_cd account_status, \r\n" +
		"site.bus_entity_id site_id, site.short_desc site_name, site.bus_entity_status_cd site_status,\r\n" +
		"(select max(clw_value) from clw_property p where p.bus_entity_id = site.bus_entity_id and p.short_desc = 'SITE_REFERENCE_NUMBER') site_ref_num,\r\n" +
		"(select max(clw_value) from clw_property p where p.bus_entity_id = site.bus_entity_id and p.short_desc = 'DIST_SITE_REFERENCE_NUMBER') dist_site_ref_num, \r\n" +
		"siteAddr.address1, siteAddr.address2, siteAddr.address3, siteAddr.city, siteAddr.state_province_cd, siteAddr.postal_code, siteAddr.country_cd \r\n" +
		"from clw_bus_entity store, clw_bus_entity account, clw_bus_entity site, clw_bus_entity_assoc accountA, clw_bus_entity_assoc siteA, clw_address siteAddr \r\n" +
		"where store.bus_entity_id = accountA.bus_entity2_id \r\n" +
		"and account.bus_entity_id = accountA.bus_entity1_id \r\n" +
		"and accountA.bus_entity_assoc_cd = 'ACCOUNT OF STORE' \r\n" +
		"and siteA.bus_entity2_id = accountA.bus_entity1_id \r\n" +
		"and site.bus_entity_id = siteA.bus_entity1_id \r\n" +
		"and siteA.bus_entity_assoc_cd = 'SITE OF ACCOUNT' \r\n" +
		"and siteaddr.bus_entity_id = site.bus_entity_id \r\n" +
		"and siteaddr.address_type_cd = 'SHIPPING'\r\n" +
		"and store.bus_entity_id = " + storeId + " \r\n" +
		"order by store.short_desc, account.short_desc, site.short_desc";
    	    	
    	String sqlSelectSiteProp = "select site.bus_entity_id site_id, 'SiteData'||substr(acctProp.short_desc,2,1) site_data_label, siteprop.short_desc site_data_name, siteprop.clw_value site_data_value \r\n" +
		"from clw_bus_entity site, clw_property acctProp, clw_bus_entity_assoc siteA, clw_property siteProp \r\n" +
		"where acctprop.bus_entity_id = siteA.bus_entity2_id \r\n" +
		"and acctprop.property_type_cd = 'SITE_FIELD_CD' \r\n" +
		"and acctprop.short_desc like 'F%Tag' \r\n" +
		"and site.bus_entity_id = siteA.bus_entity1_id \r\n" +
		"and siteA.bus_entity_assoc_cd = 'SITE OF ACCOUNT' \r\n" +
		"and site.bus_entity_id = siteprop.bus_entity_id \r\n" +
		"and siteprop.property_type_cd = 'SITE_FIELD_CD' \r\n" +
		"and acctProp.clw_value = siteProp.short_desc \r\n" +
		"and site.bus_entity_id = ? \r\n" +
		"order by siteprop.property_id";
    	
    	String sqlSelBudget = "select a.budget_id, a.budget_type_cd, a. budget_status_cd, a.budget_year, a.cost_center_id, b.short_desc, b.cost_center_status_cd, c.period, c.amount \r\n" +
		"from clw_budget a, clw_cost_center b, clw_budget_detail c \r\n" +
		"where a.cost_center_id=b.cost_center_id \r\n" +
		"and a.budget_type_cd= 'SITE BUDGET' \r\n" +
		"and a.budget_id = c.budget_id \r\n" +
		"and a.budget_year >= to_number(to_char(sysdate, 'yyyy')) - 1 \r\n" +// only include current and last year
		"and a.bus_entity_id = ? \r\n" +
		"order by a.budget_year, a.budget_id, a.cost_center_id, c.period";    	
    	
    	Connection conn = null;		
		try {
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sqlSelectSiteProp); 
			PreparedStatement pstmtBudget = conn.prepareStatement(sqlSelBudget);
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery(sqlSelectSite);
			log.info(sqlSelectSiteProp);
			log.info(sqlSelBudget);
			
			log.info(sqlSelectSite);
			ReportItem tempRI = null;
			ReportItem siteRI = null;
			
			ReportItem siteRootRI = ReportItem.createValue("Sites");
			while (rs.next()){
				int siteId = rs.getInt("site_id");
	        	siteRI = ReportItem.createValue("Site");
	        	siteRootRI.addChild(siteRI);			        
	        	siteRI.addAttribute("id", siteId);
	        	tempRI = ReportItem.createValue("StoreId",storeId);
		        siteRI.addChild(tempRI);			        
		        tempRI = ReportItem.createValue("StoreName",rs.getString("store_name"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("AccountId",rs.getInt("account_id"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("AccountName",rs.getString("account_name"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("SiteName",rs.getString("site_name"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("SiteBudgetReferenceNumber",rs.getString("site_ref_num"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("SiteStatus",rs.getString("site_status"));
		        siteRI.addChild(tempRI);
		        
		        pstmt.setInt(1, siteId);
		        ResultSet sitepRs = pstmt.executeQuery();
		        while (sitepRs.next()){
			        String siteDataLabel = sitepRs.getString("site_data_label");
			        String siteDataName = sitepRs.getString("site_data_name");
			        String siteDataValue = sitepRs.getString("site_data_value");
			        if (siteDataValue == null)
			        	tempRI = ReportItem.createValue(siteDataLabel);
			        else
			        	tempRI = ReportItem.createValue(siteDataLabel,siteDataValue);
			        tempRI.addAttribute("name", siteDataName);
			        siteRI.addChild(tempRI);
		        }
		        
		        tempRI = ReportItem.createValue("Address1",rs.getString("address1"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("Address2",rs.getString("address2"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("Address3",rs.getString("address3"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("City",rs.getString("city"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("State",rs.getString("state_province_cd"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("PostalCode",rs.getString("postal_code"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("Country",rs.getString("country_cd"));
		        siteRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("DistSiteRefNum",rs.getString("dist_site_ref_num"));
		        siteRI.addChild(tempRI);
		        
		        // budgets
		        ReportItem budgetListRI = ReportItem.createValue("Budgets");
		        siteRI.addChild(budgetListRI);	  
		        pstmtBudget.setInt(1, siteId);
		        ResultSet rs1 = pstmtBudget.executeQuery();
		        ReportItem budgetRI = null;
		        ReportItem costCenterRI = null;
		        
		        
		        int preBudgetId = -1;
		        int preCostCenterId = -1;
		        while (rs1.next()){
		        	int budgetId = rs1.getInt("budget_id");
		        	if (preBudgetId != budgetId){
		        		budgetRI = ReportItem.createValue("Budget");
		        		budgetRI.addAttribute("id", budgetId);
		        		budgetListRI.addChild(budgetRI);
		        		
		        		tempRI = ReportItem.createValue("BudgetStatus",rs1.getString("budget_status_cd"));
		        		budgetRI.addChild(tempRI);
				        tempRI = ReportItem.createValue("BudgetType",rs1.getString("budget_type_cd"));
		        		budgetRI.addChild(tempRI);
		        		tempRI = ReportItem.createValue("FiscalYear",rs1.getString("budget_year"));
				        tempRI.addAttribute("format", "yyyy");
				        budgetRI.addChild(tempRI);
				        preBudgetId = budgetId;
				        preCostCenterId = -1;
		        	}
				        
			        int costCenterId = rs1.getInt("cost_center_id");
			        if (preCostCenterId != costCenterId){
				        costCenterRI = ReportItem.createValue("CostCenter");
				        costCenterRI.addAttribute("id", rs1.getInt("cost_center_id"));
				        budgetRI.addChild(costCenterRI);
				        tempRI = ReportItem.createValue("CostCenterStatus",rs1.getString("cost_center_status_cd"));	
				        costCenterRI.addChild(tempRI);		
				        tempRI = ReportItem.createValue("CostCenterName",rs1.getString("short_desc"));	
				        costCenterRI.addChild(tempRI);
				        preCostCenterId = costCenterId;
			        }
			        tempRI = ReportItem.createValue("BudgetAmt",rs1.getString("amount"));
			        tempRI.addAttribute("Period", rs1.getInt("period"));
			        costCenterRI.addChild(tempRI);	
		        }	
		        
		        // add dummy budget if no site budget
		        if (budgetRI == null){
		        	budgetRI = ReportItem.createValue("Budget");
	        		budgetRI.addAttribute("id", -1);
	        		budgetListRI.addChild(budgetRI);
	        		
	        		tempRI = ReportItem.createValue("BudgetStatus","INACTIVE");
	        		budgetRI.addChild(tempRI);
			        tempRI = ReportItem.createValue("BudgetType","NONE");
	        		budgetRI.addChild(tempRI);
	        		tempRI = ReportItem.createValue("FiscalYear","01");
			        tempRI.addAttribute("format", "yyyy");
			        budgetRI.addChild(tempRI);
			        costCenterRI = ReportItem.createValue("CostCenter");
			        costCenterRI.addAttribute("id", -1);
			        budgetRI.addChild(costCenterRI);
			        tempRI = ReportItem.createValue("CostCenterStatus","INACTIVE");	
			        costCenterRI.addChild(tempRI);		
			        tempRI = ReportItem.createValue("CostCenterName","Dummy");	
			        costCenterRI.addChild(tempRI);
			        for (int i = 1; i < 13; i++){
				        tempRI = ReportItem.createValue("BudgetAmt","");
				        tempRI.addAttribute("Period", i);
				        costCenterRI.addChild(tempRI);
			        }
				}
			}
			
			stmt.close();
			pstmt.close();
			pstmtBudget.close();
			ReportFactory reportFactory = (ReportFactory) Class.forName("com.cleanwise.service.api.tree.xml.ReportXmlFactory").newInstance();
		    String str = reportFactory.transform(siteRootRI, new SimpleReportTransformer());
		    translator.writeOutputStream(str);			
		} finally {
	    	closeConnection(conn);
	    }
    }
    
    public String getFileName()throws Exception{   //procuresitedata_392146_06272011030533.xml 	
    	SimpleDateFormat frmt = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String now = frmt.format(new java.util.Date());
        String fileName = "procuresitedata_" + storeId + "_" + now + getFileExtension();
        return fileName;
    }
    public String getFileExtension() throws Exception{
        return ".xml";
    }
    
    public String getTimeStampString(Date date, SimpleDateFormat frmt){
    	return frmt.format(date);
    }

}
