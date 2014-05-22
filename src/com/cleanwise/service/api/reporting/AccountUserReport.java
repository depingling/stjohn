/*
 * AccountBudgetReport.java
 *
 * Created on April 27, 2005, 10:21 AM
 */

package com.cleanwise.service.api.reporting;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;

/**
 * Shell class that configures the account budget report.  All logic is in the super class implementation
 * @author bstevens
 */
public class AccountUserReport extends BudgetReport{
	private static final Logger log = Logger.getLogger(CatalogsItemReport.class);
	
    private final static int VERSION = 1;
	private final static int ACTION = 2;
	private final static int STORE_ID = 3;
	private final static int STORE_NAME = 4;
	private final static int ACCOUNT_REF_NUM = 5;
	private final static int ACCOUNT_NAME = 6;
	private final static int SITE_NAME = 7;
	private final static int SITE_REF_NUM = 8;
	private final static int USERNAME = 9;
	private final static int PASSWORD = 10;
	private final static int UPDATE_PASSWORD = 11;
	private final static int PREFERRED_LANGUAGE = 12;
	private final static int FIRST_NAME = 13;
	private final static int LAST_NAME = 14;
	private final static int ADDRESS1 = 15;
	private final static int ADDRESS2 = 16;
	private final static int CITY = 17;
	private final static int STATE = 18;
	private final static int POSTAL_CODE = 19;
	private final static int COUNTRY = 20;
	private final static int PHONE = 21;
	private final static int EMAIL = 22;
	private final static int FAX = 23;
	private final static int MOBILE = 24;
	private final static int APPROVER = 25;
	private final static int NEEDS_APPROVAL = 26;
	private final static int WAS_APPROVED = 27;
	private final static int WAS_REJECTED = 28;
	private final static int WAS_MODIFIED = 29;
	private final static int ORDER_DETAIL_NOTIFICATION = 30;		
	private final static int SHIPPING_NOTIFICATION = 31;
	private final static int CUTOFF_TIME_REMINDER = 32;
	private final static int NUMBER_OF_TIMES = 33;
	private final static int PHY_INV_NON_COMP_LOCATION_LISTING = 34;
	private final static int PHY_INV_COUNTS_PAST_DUE = 35;
	private final static int CORPORATE_USER = 36;
	private final static int ON_ACCOUNT = 37;
	private final static int CREDIT_CARD = 38;
	private final static int OTHER_PAYMENT = 39;
	private final static int PO_NUM_REQUIRED = 40;
	private final static int SHOW_PRICE = 41;
	private final static int BROWSE_ONLY = 42;
	private final static int NO_REPORTING = 43;
	private final static int GROUP_ID = 44;
	private final static int SITE_ID = 45;
	private final static int STATUS = 46;
	private final static int ACCOUNT_ID = 47;
	
	public GenericReportResultViewVector process(ConnectionContainer pCons,
			GenericReportData pReportData,
			Map pParams) throws Exception {

		Connection con = pCons.getDefaultConnection();
		GenericReportResultViewVector resultV = new GenericReportResultViewVector();
		try {
			String storeIdStr = (String) pParams.get("STORE");
			String accountIdStr = (String) pParams.get("ACCOUNT");
			if (!Utility.isSet(accountIdStr))
				accountIdStr = (String) pParams.get("ACCOUNT_OPT");
			String userIdStr = (String) pParams.get("USER");
			if (!Utility.isSet(userIdStr))
				userIdStr = (String) pParams.get("USER_OPT");
			ArrayList table = new ArrayList();
			
			int storeId = Integer.parseInt(storeIdStr);
			int accountId = Utility.isSet(accountIdStr) ? Integer.parseInt(accountIdStr) : 0;
			int userId = Utility.isSet(userIdStr) ? Integer.parseInt(userIdStr) : 0;
			String sql = "select u.user_id, \n" +
			"'' Action, \n" +
			"s.bus_entity_id store_id, \n" +
			"s.short_desc store_name, \n" +
			"(select upper(clw_value) from clw_property where bus_entity_id = a.bus_entity_id and property_type_cd = 'DIST_ACCT_REF_NUM') account_ref_num, \n" +
			"a.short_desc account_name, \n" +
			"'Site Name', 'Distributor Site Reference Number', u.user_name, '' password, \n" +
			"'FALSE' Update_Password, \n" +
			"(select max(l.ui_name) from clw_language l where l.language_code = substr(u.pref_locale_cd, 1, 2)) Preferred_Language, \n" +
			"u.first_name, u.last_name, \n" +
			"address.address1, address.address2, address.city, address.state_province_cd, address.postal_code, address.country_cd, \n" +
			"(select phone_num from clw_phone where user_id = u.user_id and phone_type_cd = 'PHONE') phone, \n" +
			"(select email_address from clw_email where user_id = u.user_id and email_type_cd = 'PRIMARY') email, \n" +
			"(select phone_num from clw_phone where user_id = u.user_id and phone_type_cd = 'FAX') fax, \n" +
			"(select phone_num from clw_phone where user_id = u.user_id and phone_type_cd = 'MOBILE') mobile, \n" +
			"(case u.workflow_role_cd WHEN 'ORDER APPROVER' THEN 'TRUE' ELSE 'FALSE' end) Approver, \n" +
			"(case INSTR(u.user_role_cd,'ONAe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Needs_Approval, \n" +
			"(case INSTR(u.user_role_cd,'OAe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Was_Approved, \n" +
			"(case INSTR(u.user_role_cd,'ORe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Was_Rejected, \n" +
			"(case INSTR(u.user_role_cd,'OMe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Was_Modified, \n" +
			"(case INSTR(u.user_role_cd,'ODAe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Order_Detail_Notification, \n" +
			"(case INSTR(u.user_role_cd,'OSe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Shipping_Notification, \n" +
			"(case INSTR(u.user_role_cd,'CTRe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Cutoff_Time_Reminder, \n" +
			"(select upper(clw_value) from clw_property p where p.user_id = u.user_id and p.property_type_cd = 'CUTOFF_TIME_EMAIL_REMINDER_CNT') Number_of_time, \n" +
			"(case INSTR(u.user_role_cd,'PINCSLe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Inv_Non_Compliant_Locations, \n" +
			"(case INSTR(u.user_role_cd,'PICPDe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Inventory_Counts_Past_Due,\n" +				
			"(select upper(clw_value) from clw_property where user_id = u.user_id and property_type_cd = 'CORPORATE_USER') Corporate_User, \n" +
			"(case INSTR(u.user_role_cd,'OA^') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) On_Account, \n" +
			"(case INSTR(u.user_role_cd,'CC^') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Credit_Card, \n" +
			"(case INSTR(u.user_role_cd,'OSe') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Other_Payment, \n" +
			"(case INSTR(u.user_role_cd,'PR^') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) PO_NUM_Required, \n" +
			"(case INSTR(u.user_role_cd,'SP^') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Show_Price, \n" +
			"(case INSTR(u.user_role_cd,'BO^') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) Browse_Only, \n" +
			"(case INSTR(u.user_role_cd,'NR^') WHEN 0 THEN 'FALSE' ELSE 'TRUE' end) No_Reporting, \n" +
			"(select (listagg (group_id, ',') WITHIN GROUP (ORDER BY group_id)) from clw_group_assoc where user_id = u.user_id group by user_id) Group_ID, \n" +
			"'Site ID', u.user_status_cd Status, accountAssoc.bus_entity_id account_id \n" +
			"from clw_user u, clw_bus_entity s, clw_bus_entity a, clw_user_assoc storeAssoc, clw_user_assoc accountAssoc, clw_bus_entity_assoc accountStoreAssoc, \n" +
			"clw_address address \n" +
			"where u.user_id=storeAssoc.user_id \n" +
			"and u.user_type_cd = 'MULTI-SITE BUYER' \n" +
			"and u.user_id = accountAssoc.user_id \n" +
			"and s.bus_entity_id = storeAssoc.bus_entity_id \n" +
			"and a.bus_entity_id = accountAssoc.bus_entity_id \n" +
			"and storeAssoc.bus_entity_id = accountStoreAssoc.bus_entity2_id \n" +
			"and accountAssoc.bus_entity_id=accountStoreAssoc.bus_entity1_id \n" +
			"and accountStoreAssoc.bus_entity_assoc_cd = 'ACCOUNT OF STORE' \n" +
			"and u.user_id = address.user_id and address_type_cd = 'PRIMARY CONTACT' \n" +
			"and storeAssoc.bus_entity_id = ? \n" +
			(accountId>0 ? "and accountAssoc.bus_entity_id = ? \n" : "") +
			(userId>0 ? "and u.user_id = ? \n" : "") ;
	String selSiteInfo = "select site.bus_entity_id site_id, site.short_desc site_name, \n" +
			"(select upper(max(clw_value)) from clw_property where bus_entity_id = site.bus_entity_id and property_type_cd = 'DIST_SITE_REFERENCE_NUMBER') site_ref_num \n" +
			"from clw_bus_entity site, clw_user_assoc userSiteAssoc, clw_bus_entity_assoc siteAccountAssoc \n" +
			"where site.bus_entity_id = userSiteAssoc.bus_entity_id \n" +
			"and site.bus_entity_status_cd = 'ACTIVE' \n" +
			"and userSiteAssoc.bus_entity_id = siteAccountAssoc.bus_entity1_id \n" +
			"and siteAccountAssoc.bus_entity_assoc_cd = 'SITE OF ACCOUNT' \n" +
			"and userSiteAssoc.user_id = ? \n" +
			"and siteAccountAssoc.bus_entity2_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			int i = 1;
			pstmt.setInt(i++, storeId);
			if (accountId > 0)
				pstmt.setInt(i++, accountId);
	        if (userId > 0)
	        	pstmt.setInt(i++, userId);
	        ResultSet rs = pstmt.executeQuery();
	        PreparedStatement pstmt1 = con.prepareStatement(selSiteInfo);
	        
	        while (rs.next()){
	        	List row = new ArrayList();
	        	table.add(row);	        	
	        	userId = rs.getInt(1);
	        	accountId = rs.getInt(ACCOUNT_ID);
	        	pstmt1.setInt(1, userId);
	        	pstmt1.setInt(2, accountId);
	        	
	        	ResultSet rs1 = pstmt1.executeQuery();
	        	List<String> siteIds = new ArrayList<String>();
	        	List<String> siteNames = new ArrayList<String>();
	        	List<String> siteRefNums = new ArrayList<String>();
	        	while (rs1.next()){
	        		siteIds.add(rs1.getString(1));
	        		siteNames.add(rs1.getString(2));
	        		String siteRefNum = rs1.getString(3);
	        		if (Utility.isSet(siteRefNum))
	        			siteRefNums.add(siteRefNum);
	        	}
	        	rs1.close();
	        	row.add("2");// VERSION
	        	for (int idx = ACTION; idx <= STATUS; idx++){
	        		if (idx == STORE_ID){
	        			row.add(rs.getInt(STORE_ID));	    	        	
	        		} else if (idx == SITE_NAME){
	        			if (siteNames.isEmpty())
	    	        		row.add("");
	    	        	else
	    	        		row.add(siteNames.get(0)); // only first site name
	        		} else if ( idx == SITE_REF_NUM){
	        			if (siteRefNums.isEmpty())
	    	        		row.add("");
	    	        	else
	    	        		row.add(Utility.getAsString(siteRefNums)); 
	        		} else if ( idx == SITE_ID){
	        			if (siteIds.isEmpty())
	    	        		row.add("");
	    	        	else
	    	        		row.add(Utility.getAsString(siteIds)); 
	        		} else{
	        			row.add(rs.getString(idx));
	        		}
	        	}
	        }
            
            rs.close();
            pstmt.close();
            pstmt1.close();

            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(table);

            GenericReportColumnViewVector boHeader = getReportHeader();
            result.setColumnCount(boHeader.size());
            result.setHeader(boHeader);
            result.setName("Account User Report");
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            resultV.add(result);

			return resultV;
		} catch (NumberFormatException e) {
			throw new Exception(e.toString());
		}
	}

	private GenericReportColumnViewVector getReportHeader() {
		GenericReportColumnViewVector header = new GenericReportColumnViewVector();

		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Version Number", 0,38, "NUMBER","1",false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action", 0, 1, "VARCHAR2", "1", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Store ID", 0,38, "NUMBER","10",false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Store/Primary Entity Name", 0, 0,"DATE", "10", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Reference Number", 0,38, "NUMBER","10",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account Name", 0, 255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Location Name", 0, 255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Distributor Location Reference Number", 0, 4000, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Username", 0, 255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Password", 0, 255, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Update Password", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Preferred Language", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "First Name", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Last Name", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 1", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Address 2", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Postal Code", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Country", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Phone", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Email", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Fax", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Mobile", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Approver", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Needs Approval", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Was Approved", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Was Rejected", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Was Modified", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Detail Notification", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Shipping Notification", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Cutoff Time Reminder", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Number of Times", 0,38, "NUMBER","10",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Physical Inventory Non-Compliant Location Listing", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Physical Inventory Counts Past Due", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Corporate User", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "On Account", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Credit Card", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Other Payment", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "PO# Required", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Show Price", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Browse Only", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "No Reporting", 0, 255, "VARCHAR2","6",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Group ID", 0, 4000, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Location ID", 0, 4000, "VARCHAR2","30",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Status", 0, 255, "VARCHAR2","30",false));

        return header;
	}
}
