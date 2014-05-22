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


public class OutboundProcurementAccountDataXML extends InterchangeOutboundSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private int storeId = 0;    
    
    public OutboundProcurementAccountDataXML() {
        seperateFileForEachOutboundOrder = true;
    }
    
    public void buildTransactionContent() throws Exception {
        
    	storeId = (Integer)currOutboundReq.getGenericMap().get("STORE_ID");
    	// reset the file name with store id in it
    	getTranslator().setOutputFileName(this.getFileName());
    	interchangeD.setEdiFileName(getTranslator().getOutputFileName());
    	String sqlAccountProp = "select store.short_desc store_name, account.bus_entity_id account_id, account.short_desc account_name, account.bus_entity_status_cd account_status, \r\n" +
    	"    acctAddr.address1, acctAddr.address2, acctAddr.address3, acctAddr.city, acctAddr.state_province_cd, acctAddr.postal_code, acctAddr.country_cd \r\n" +
		"from clw_bus_entity store, clw_bus_entity account, clw_bus_entity_assoc accountA, clw_address acctAddr \r\n" +
		"where store.bus_entity_id = accountA.bus_entity2_id \r\n" +
		"and account.bus_entity_id = accountA.bus_entity1_id \r\n" +
		"and accountA.bus_entity_assoc_cd = 'ACCOUNT OF STORE' \r\n" +
		"and acctAddr.bus_entity_id = account.bus_entity_id \r\n" +
		"and address_type_cd='BILLING' \r\n" +
		"and store.bus_entity_id = " + storeId + " \r\n" +
		"order by store.short_desc, account.short_desc \r\n";
    	
    	String sqlSiteProp = "select account.bus_entity_id account_id, \r\n" +
		"'SiteData'||substr(siteprop.short_desc,2,1) site_data_name, siteprop.clw_value site_data_value \r\n" +
		"from clw_bus_entity account, clw_property siteProp \r\n" +
		"where account.bus_entity_id = siteprop.bus_entity_id \r\n" +
		"and siteprop.property_type_cd = 'SITE_FIELD_CD' \r\n" +
		"and siteprop.clw_value is not null \r\n" +
		"and siteprop.short_desc like 'F%Tag' \r\n" +
		"and account.bus_entity_id = ? \r\n" +
		"order by account.bus_entity_id, substr(siteprop.short_desc,2,1)";
    	
    	String sqlSelFisCalendar = "select b.fiscal_calender_id, a.fiscal_year, to_char(a.eff_date, 'mm/dd/yyyy') eff_date, b.period, b.mmdd \r\n" +
		"from clw_fiscal_calender a, clw_fiscal_calender_detail b \r\n" +
		"where a.bus_entity_id = ? \r\n" +
		"and a.fiscal_calender_id = b.fiscal_calender_id \r\n" +
		"and (a.fiscal_year=0 or a.fiscal_year >= to_number(to_char(sysdate, 'yyyy')) - 1)\r\n" +// only include current and last year
		"order by a.fiscal_year, b.period";
    	
    	String sqlSelBudget = "select a.budget_id, a.budget_type_cd, a. budget_status_cd, a.budget_year, a.cost_center_id, b.short_desc, b.cost_center_status_cd, c.period, c.amount \r\n" +
		"from clw_budget a, clw_cost_center b, clw_budget_detail c \r\n" +
		"where a.cost_center_id=b.cost_center_id \r\n" +
		"and a.budget_type_cd= 'ACCOUNT BUDGET' \r\n" +
		"and a.budget_id = c.budget_id \r\n" +
		"and a.budget_year >= to_number(to_char(sysdate, 'yyyy')) - 1 \r\n " +// only include current and last year
		"and a.bus_entity_id = ? \r\n" +
		"order by a.budget_year, a.budget_id, a.cost_center_id, c.period";    	
    	
    	
    	Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement pstmtSiteP = conn.prepareStatement(sqlSiteProp); 
			PreparedStatement pstmtFisCal = conn.prepareStatement(sqlSelFisCalendar);
			PreparedStatement pstmtBudget = conn.prepareStatement(sqlSelBudget);
			
			Statement stmt = conn.createStatement(); 
			log.info(sqlAccountProp);
			log.info(sqlSiteProp);
			log.info(sqlSelFisCalendar);
			log.info(sqlSelBudget);
			ResultSet rs = stmt.executeQuery(sqlAccountProp);
			
			ReportItem tempRI = null;
			ReportItem acctRI = null;
			ReportItem siteDataRI = null;
			ReportItem fiscalCalendarListRI = null;
			ReportItem rootRI = ReportItem.createValue("Accounts");
			while (rs.next()){
				int accountId = rs.getInt("account_id");
	        	acctRI = ReportItem.createValue("Account");
		        rootRI.addChild(acctRI);			        
	        	acctRI.addAttribute("id", accountId);
	        	tempRI = ReportItem.createValue("StoreId",storeId);
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("StoreName",rs.getString("store_name"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("AccountName",rs.getString("account_name"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("Status",rs.getString("account_status"));
		        acctRI.addChild(tempRI);
		        
		        // create group of "SiteData" properties
		        siteDataRI = ReportItem.createValue("SiteData");
		        acctRI.addChild(siteDataRI);	  
		        pstmtSiteP.setInt(1, accountId);
		        ResultSet rs1 = pstmtSiteP.executeQuery();
		        while (rs1.next()){
		        	String siteDataName = rs1.getString("site_data_name");
			        String siteDataValue = rs1.getString("site_data_value");
			        tempRI = ReportItem.createValue(siteDataName,siteDataValue);
			        siteDataRI.addChild(tempRI);	
		        }
		        tempRI = ReportItem.createValue("Address1",rs.getString("address1"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("Address2",rs.getString("address2"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("Address3",rs.getString("address3"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("City",rs.getString("city"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("State",rs.getString("state_province_cd"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("PostalCode",rs.getString("postal_code"));
		        acctRI.addChild(tempRI);
		        tempRI = ReportItem.createValue("Country",rs.getString("country_cd"));
		        acctRI.addChild(tempRI);
		        
		        // fiscal calendars
		        fiscalCalendarListRI = ReportItem.createValue("FiscalCalendarList");
		        acctRI.addChild(fiscalCalendarListRI);	  
		        pstmtFisCal.setInt(1, accountId);
		        rs1 = pstmtFisCal.executeQuery();
		        ReportItem fiscalCalendarRI = null;
		        ReportItem periodListRI = null;
		        
		        int preFiscalCalendarId = -1;
		        while (rs1.next()){
		        	int fiscalCalendarId = rs1.getInt("fiscal_calender_id");
		        	if (preFiscalCalendarId != fiscalCalendarId){
		        		fiscalCalendarRI = ReportItem.createValue("FiscalCalendar");
		        		fiscalCalendarRI.addAttribute("id", fiscalCalendarId);
		        		fiscalCalendarListRI.addChild(fiscalCalendarRI);
		        		tempRI = ReportItem.createValue("FiscalYear",rs1.getString("fiscal_year"));
				        tempRI.addAttribute("format", "yyyy");
				        fiscalCalendarRI.addChild(tempRI);
				        tempRI = ReportItem.createValue("EffectiveDate",rs1.getString("eff_date"));
				        tempRI.addAttribute("format", "mm/dd/yyyy");
				        fiscalCalendarRI.addChild(tempRI);
				        periodListRI = ReportItem.createValue("PeriodList");
				        periodListRI.addAttribute("format", "mm/dd");
				        fiscalCalendarRI.addChild(periodListRI);
		        		preFiscalCalendarId = fiscalCalendarId;
		        	}
		        	
		        	tempRI = ReportItem.createValue("Period",rs1.getString("mmdd"));
		        	tempRI.addAttribute("num", rs1.getInt("period"));
		        	periodListRI.addChild(tempRI);
		        }
		        // add dummy fiscal calendar if no fiscal calendar exists
		        if (fiscalCalendarRI == null){
		        	fiscalCalendarRI = ReportItem.createValue("FiscalCalendar");
	        		fiscalCalendarRI.addAttribute("id", -1);
	        		fiscalCalendarListRI.addChild(fiscalCalendarRI);
	        		tempRI = ReportItem.createValue("FiscalYear","01");
			        tempRI.addAttribute("format", "yyyy");
			        fiscalCalendarRI.addChild(tempRI);
			        tempRI = ReportItem.createValue("EffectiveDate","01/01/01");
			        tempRI.addAttribute("format", "mm/dd/yyyy");
			        fiscalCalendarRI.addChild(tempRI);
			        periodListRI = ReportItem.createValue("PeriodList");
			        periodListRI.addAttribute("format", "mm/dd");
			        fiscalCalendarRI.addChild(periodListRI);
	        		for (int i = 1; i < 13; i++){
	        			tempRI = ReportItem.createValue("Period",i+"/1");		        		
			        	tempRI.addAttribute("num", i);
			        	periodListRI.addChild(tempRI);
	        		}
		        }
		        
		        
		        // budgets
		        ReportItem budgetListRI = ReportItem.createValue("Budgets");
		        acctRI.addChild(budgetListRI);	  
		        pstmtBudget.setInt(1, accountId);
		        rs1 = pstmtBudget.executeQuery();
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
			}
			
			stmt.close();
			pstmtSiteP.close();
			pstmtFisCal.close();
			pstmtBudget.close();
			
			ReportFactory reportFactory = (ReportFactory) Class.forName("com.cleanwise.service.api.tree.xml.ReportXmlFactory").newInstance();
		    String str = reportFactory.transform(rootRI, new SimpleReportTransformer());
		    translator.writeOutputStream(str);			
		} finally {
	    	closeConnection(conn);
	    }
    }
    
    public String getFileName()throws Exception{   //procureacctdata_392146_06272011030533.xml 	
    	SimpleDateFormat frmt = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String now = frmt.format(new java.util.Date());
        String fileName = "procureacctdata_" + storeId + "_" + now + getFileExtension();
        return fileName;
    }
    public String getFileExtension() throws Exception{
        return ".xml";
    }
    
    public String getTimeStampString(Date date, SimpleDateFormat frmt){
    	return frmt.format(date);
    }

}
