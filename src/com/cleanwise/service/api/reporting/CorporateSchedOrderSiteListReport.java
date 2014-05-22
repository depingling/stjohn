package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Schedule;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.Utility;

import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;

import org.apache.log4j.Logger;


/**
 * This report will contain a list of sites for a selected account 
 * or group of accounts and the Site’s Corporate Schedule.   
 * It will be used by the business-side to ensure that sites are correctly 
 * configured for Corporate Scheduled ordering
 * @param list of selected account
 * @author  dling
 */
public class CorporateSchedOrderSiteListReport implements GenericReport {

	private static final Logger log = Logger.getLogger(CorporateSchedOrderSiteListReport.class);
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");


	/** Creates a new instance of CorporateSchedOrderSiteListReport */
	public CorporateSchedOrderSiteListReport() {
	}

	/** Should return a populated GenericReportResultView object.  At a minimum the header should
	 * be set so an empty report may be generated to the user.
	 *
	 */
	public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception
	{
		Connection con = pCons.getDefaultConnection();
		DBCriteria dbc;
		GenericReportResultView result = GenericReportResultView.createValue(); 
		String acctIdListParam = (String) ReportingUtils.getParam(pParams, "ACCOUNT_MULTI");

		///////////////////////////
		String acctIdList = null;
		if(Utility.isSet(acctIdListParam)){
			StringTokenizer tok = new StringTokenizer(acctIdListParam.trim(),",");
			while(tok.hasMoreTokens()){
				String acctIdS=tok.nextToken().trim();
				dbc = new DBCriteria();
				int acctId = 0;
				try {
					acctId = Integer.parseInt(acctIdS);
				} catch (Exception exc){}
				if(acctId==0) {
					throw new NumberFormatException("^clw^Wrong account id format: "+acctIdS+"^clw^");
				}
				dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID,acctId);
				dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
						RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
				IdVector acctIdV = BusEntityDataAccess.selectIdOnly(con,dbc);
				if(acctIdV.size()==0) {
					throw new NumberFormatException("^clw^Wrong accout id: "+acctId+"^clw^");
				}
				Integer acctIdI = (Integer) acctIdV.get(0);
				if(acctIdList!=null) {
					acctIdList += ","+acctIdI;
				} else {
					acctIdList = ""+acctIdI;
				}
			}
		}

		String sql = "SELECT A.BUS_ENTITY_ID ACCOUNT_ID, A.SHORT_DESC ACCOUNT_NAME, S.BUS_ENTITY_ID SITE_ID, \n" +
		"S.SHORT_DESC SITE_NAME, S_REF.SITE_BUDGET_REFERENCE_NUMBER, \n" +
		"S.BUS_ENTITY_STATUS_CD SITE_STATUS, \n" +
		"SC.SCHEDULE_ID, SC.SCHEDULE_NAME, SC.SCHEDULE_STATUS, SC.CUTOFF_TIME \n" +
		"FROM CLW_BUS_ENTITY S, CLW_BUS_ENTITY_ASSOC SA, CLW_BUS_ENTITY A, \n" +
		"  (SELECT BUS_ENTITY_ID SITE_ID, MAX(CLW_VALUE) SITE_BUDGET_REFERENCE_NUMBER \n" +
		"    FROM CLW_PROPERTY WHERE SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER + "' GROUP BY BUS_ENTITY_ID) S_REF, \n" +
		"  (SELECT SCHED_SITES.VALUE SITE_ID, SCHED.SCHEDULE_ID, SCHED.SHORT_DESC SCHEDULE_NAME, SCHED.SCHEDULE_STATUS_CD SCHEDULE_STATUS, C.CUTOFF_TIME \n" +
		"    FROM CLW_SCHEDULE SCHED, CLW_SCHEDULE_DETAIL SCHED_SITES, \n" +
		"      (SELECT SCHEDULE_ID, MAX(VALUE) CUTOFF_TIME FROM CLW_SCHEDULE_DETAIL \n" +
		"        WHERE SCHEDULE_DETAIL_CD = '" + RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME + "'  GROUP BY SCHEDULE_ID) C \n" +
		"    WHERE SCHED.SCHEDULE_ID = SCHED_SITES.SCHEDULE_ID \n" +
		"    AND SCHED_SITES.SCHEDULE_DETAIL_CD = 'SITE_ID' \n" +
		"    AND SCHED.SCHEDULE_ID = C.SCHEDULE_ID) SC \n" +
		"WHERE S.BUS_ENTITY_ID = SA.BUS_ENTITY1_ID \n" +
		"AND SA.BUS_ENTITY2_ID = A.BUS_ENTITY_ID \n" +
		"AND A.BUS_ENTITY_ID IN (" + acctIdList + ") \n" +
		"AND S_REF.SITE_ID = S.BUS_ENTITY_ID \n" +
		"AND SC.SITE_ID(+) = TO_CHAR(S.BUS_ENTITY_ID) \n" +
		"ORDER BY A.SHORT_DESC, S.SHORT_DESC";
		
		log("SQL: "+sql);

		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		APIAccess factory = new APIAccess();         
		Schedule scheduleEjb = factory.getScheduleAPI();
		List<ReportRow> rowDataA = new ArrayList<ReportRow>();
		Map<Integer, ScheduleInfo> scheduleInfoMap = new HashMap<Integer, ScheduleInfo>();
		while (rs.next() ) {
			ReportRow rr = new ReportRow();
			rowDataA.add(rr);
			rr.accountId = rs.getInt("ACCOUNT_ID");
			rr.accountName = rs.getString("ACCOUNT_NAME");
			rr.siteId = rs.getInt("SITE_ID");
			rr.siteName = rs.getString("SITE_NAME");           
			rr.siteRefNum = rs.getString("SITE_BUDGET_REFERENCE_NUMBER");;
			rr.siteStatus = rs.getString("SITE_STATUS");
			rr.scheduleId = rs.getInt("SCHEDULE_ID");
			if (rr.scheduleId > 0){
				ScheduleInfo schedInfo = scheduleInfoMap.get(rr.scheduleId);
				if (schedInfo == null){
					schedInfo = new ScheduleInfo();
					schedInfo.scheduleName = rs.getString("SCHEDULE_NAME");
					schedInfo.scheduleStatus = rs.getString("SCHEDULE_STATUS");           
					schedInfo.cutoffTime = rs.getString("CUTOFF_TIME");
					Date nextDelDate = scheduleEjb.getNextDeliveryDate(rr.scheduleId);
					if (nextDelDate != null)
						schedInfo.nextScheduleDate = DATE_FORMAT.format(nextDelDate);
				}
				rr.schedInfo = schedInfo;
				
			}
		}

		rs.close();
		stmt.close();


		result.setTable(new ArrayList());
		for(ReportRow rr : rowDataA){
			ArrayList row = new ArrayList();
			row.add(new Integer(rr.accountId));
			row.add(rr.accountName);
			row.add(new Integer(rr.siteId));
			row.add(rr.siteName);
			row.add(rr.siteRefNum);
			row.add(rr.siteStatus);
			if (rr.scheduleId > 0){
				row.add(new Integer(rr.scheduleId));				
				row.add(rr.schedInfo.scheduleName);
				row.add(rr.schedInfo.scheduleStatus);
				row.add(rr.schedInfo.cutoffTime);
				row.add(rr.schedInfo.nextScheduleDate);				
			}else{
				row.add("");
				row.add("");
				row.add("");
				row.add("");
				row.add("");				
			}
			result.getTable().add(row);
		}
		result.setHeader(getReportHeader());
		result.setColumnCount(result.getHeader().size());
		return result;
	}


	private GenericReportColumnViewVector getReportHeader() {
		GenericReportColumnViewVector header = new GenericReportColumnViewVector();
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Account Id",0,32,"NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Site Id",0,32,"NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Budget Reference Number",0,255,"VARCHAR2"));    	
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Status",0,255,"VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Schedule Id",0,32,"NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Schedule Name",0,255,"VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Schedule Status",0,255,"VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Cutoff Time",0,255,"VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Next Schedule Date",0,255,"VARCHAR2"));
		return header;
	}

	public class ReportRow {
		int accountId;
		String accountName;
		int siteId;
		String siteName;
		String siteRefNum;
		String siteStatus;
		int scheduleId;
		ScheduleInfo schedInfo = null;		

		public String toString() {
			String  str =
				"accountId=<"+accountId+">"+
				"accountName=<"+accountName+">"+
				"siteId=<"+siteId+">"+
				"siteName=<"+siteName+">"+
				"siteRefNum=<"+siteRefNum+">"+
				"siteStatus=<"+siteStatus+">"+
				"scheduleId=<"+scheduleId+">"+ schedInfo;
			return str;
		}
	}
	
	class ScheduleInfo {
		String scheduleName;
		String scheduleStatus;
		String cutoffTime;
		String nextScheduleDate;
		public String toString() {
			String  str = "scheduleName=<"+scheduleName+">"+
			"scheduleStatus=<"+scheduleStatus+">"+
			"cutoffTime=<"+cutoffTime+">"+
			"nextScheduleDate=<"+nextScheduleDate+">";
			return str;
		}
	}

	/**
	 * Logging
	 * @param message - message which will be pasted to log
	 */
	private void log(String message){
		log.info(getClass().getName() + " :: " + message);
	}
}
