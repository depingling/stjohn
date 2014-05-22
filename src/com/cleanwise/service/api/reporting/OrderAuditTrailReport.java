/*
 * ReSaleOrder.java
 *
 * Created on October 23, 2003, 2:23 PM
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author deping
 */
public class OrderAuditTrailReport implements GenericReportMulti {
	private static String className = "OrderAuditTrailReport";
    private static final String PATTERN = "MM/dd/yyyy HH:mm";
    private final static String sqlSelSiteRefNum = "SELECT CLW_VALUE FROM CLW_PROPERTY WHERE SHORT_DESC = 'SITE_REFERENCE_NUMBER' AND BUS_ENTITY_ID = ?";
    private final static String sqlSelApprovedInfo = "select TO_CHAR(a.ADD_DATE, 'MM/DD/YYYY') APPROVED_DATE, TO_CHAR(a.ADD_DATE, 'HH24:MI:SS') APPROVED_TIME, " +
    		"b.user_name APPROVED_BY " +
    		"from clw_order_property a, clw_user b " +
    		"where a.approve_user_id is not null " +
    		"and a.approve_user_id = b.user_id " +
    		"and order_id = ? " +
    		"order by order_property_id desc";
    private final static String sqlSelErpReleasedInfo = "select TO_CHAR(ADD_DATE, 'MM/DD/YYYY') ERP_RELEASED_DATE, TO_CHAR(ADD_DATE, 'HH24:MI:SS') ERP_RELEASED_TIME " +
			"from clw_order_property " +
			"where order_id = ? " +
			"and order_property_type_cd = '"+ RefCodeNames.ORDER_PROPERTY_TYPE_CD.ERP_RELEASED_TIME + "'" +
			"order by order_property_id desc";
    private final static String sqlSelSendInfo = "select clw_value event_id, TO_CHAR(ADD_DATE, 'MM/DD/YYYY') SEND_DATE, TO_CHAR(ADD_DATE, 'HH24:MI:SS') SEND_TIME " +
			"from clw_order_property " +
			"where order_id = ? " +
			"and order_property_type_cd = '"+ RefCodeNames.ORDER_PROPERTY_TYPE_CD.EVENT_ID_OF_SEND_PROCESS + "'" +
			"order by order_property_id desc";    

    public GenericReportResultViewVector process(com.cleanwise.service.api.util.ConnectionContainer pCons, com.cleanwise.service.api.value.GenericReportData pReportData, java.util.Map pParams)
            throws Exception {
    	Connection con = pCons.getDefaultConnection();
        String storeIdS = (String) pParams.get("STORE");
        String accountIdS = (String) pParams.get("ACCOUNT_MULTI_OPT");
        String userIdS = (String) pParams.get("CUSTOMER_OPT");
        if (Utility.isSet(userIdS)){
	        int userId = new Integer(userIdS).intValue();
	        UserData userD = UserDataAccess.select(con, userId);
	        String userTypeCd = userD.getUserTypeCd();
        	if (userTypeCd.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
        			userTypeCd.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
        			userTypeCd.equals(RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR)){                            
        		userIdS = "";
        	}
        }
        String webOrderNumS = (String) pParams.get("Web Order Num_OPT");
        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE");
        String dateFmt = (String) pParams.get("DATE_FMT");
        if (dateFmt == null)
        	dateFmt = "MM/dd/yyyy";
        

        if (!ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        
        if (!ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }

        int acctCt = 0;

        StringTokenizer token = new StringTokenizer(accountIdS, ",");
        while (token.hasMoreTokens()) {
            String acctS = token.nextToken();
            acctCt++;
            acctS = acctS.trim();
            if (acctS.length() > 0)
                try {
                    int acctId = Integer.parseInt(acctS);
                } catch (Exception exc) {
                    String mess = "^clw^" + acctS + " is not a valid account identifier ^clw^";
                    throw new Exception(mess);
                }
        }

        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        GenericReportResultView report = GenericReportResultView.createValue();
        report.setHeader(getHeader());
        report.setTable(getTable(con, begDateS, endDateS, storeIdS, accountIdS, userIdS, webOrderNumS, dateFmt));
        
        report.setColumnCount(report.getHeader().size());
        report.setName("Order Audit Trail");
        report.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        report.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
        report.setFreezePositionRow(1);
        resultV.add(report);

        return resultV;
    }


	private ArrayList getTable(Connection conn, String begDateS, String endDateS, 
			String storeIdS, String accountIdS, String userIdS, String webOrderNumS, String dateFmt) throws Exception {
		String sql = "SELECT ORDER_ID, SITE_ID, ORDER_NUM CONFIRM_NUM, \r\n" +
				"  ORDER_SITE_NAME SITE_NAME, TO_CHAR(ORIGINAL_ORDER_DATE, 'MM/DD/YYYY') PLACE_DATE, \r\n" +
				"  TO_CHAR(ORIGINAL_ORDER_TIME, 'HH24:MI:SS') PLACE_TIME, \r\n" +
				"  (CASE order_status_cd \r\n" +
				"  		WHEN 'Pending Review' THEN 'TRUE' \r\n" +
				"  		WHEN 'Pending Order Review' THEN 'TRUE' \r\n" +
				"  		WHEN 'Pending Approval' THEN 'TRUE' \r\n" +
				"  		WHEN 'Pending Consolidation' THEN 'TRUE' \r\n" +
				"  		WHEN 'Pending Date' THEN 'TRUE' \r\n" +
				"  		ELSE 'FALSE' \r\n" +
				"  	END) PENDING_STATUS, \r\n" +
				"  (CASE REQUEST_PO_NUM \r\n" +
				"  		WHEN 'N/A' THEN '' \r\n" +
				"  		WHEN NULL THEN '' \r\n" +
				"  		ELSE REQUEST_PO_NUM \r\n" +
				"  	END) CUSTOMER_PO_NUMBER, \r\n" +
				"  	O.TOTAL_PRICE ORDER_SUB_TOTAL,  \r\n" +
				"O.TOTAL_FREIGHT_COST FREIGHT, O.TOTAL_MISC_COST HANDLING, O.CURRENCY_CD CURRENCY_CODE \r\n" +
				"FROM CLW_ORDER O \r\n" +
				"WHERE STORE_ID = ?  \r\n";
		if (Utility.isSet(accountIdS)){
			sql += "AND ACCOUNT_ID IN (" + accountIdS + ") \r\n";
		}
		if (Utility.isSet(userIdS)){
			sql += "AND USER_ID = " + userIdS + " \r\n";
		}
		
		if (Utility.isSet(webOrderNumS)){
			sql += "AND ORDER_NUM = " + webOrderNumS + " \r\n";
		}
		
		sql += getDateWhere(begDateS, endDateS, dateFmt);
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, storeIdS);
		PreparedStatement pstmtSiteRef = conn.prepareStatement(sqlSelSiteRefNum);
		PreparedStatement pstmtApproved = conn.prepareStatement(sqlSelApprovedInfo);
		PreparedStatement pstmtErpReleased = conn.prepareStatement(sqlSelErpReleasedInfo);
		PreparedStatement pstmtSend = conn.prepareStatement(sqlSelSendInfo);
		
		ResultSet rs = pstmt.executeQuery();
		ArrayList rows = new ArrayList();
    	while (rs.next()){
    		List row = new ArrayList();
    		int orderId = rs.getInt("ORDER_ID");
    		int siteId = rs.getInt("SITE_ID");
    		
    		row.add(rs.getString("CONFIRM_NUM"));
    		row.add(rs.getString("SITE_NAME"));
    		pstmtSiteRef.setInt(1, siteId);
    		ResultSet rs1 = pstmtSiteRef.executeQuery();
    		if (rs1.next()){
    			row.add(rs1.getString(1));
    		}else{
    			row.add(null);
    		}
    		row.add(rs.getString("PLACE_DATE"));
    		row.add(rs.getString("PLACE_TIME"));
    		String pendingStatus = rs.getString("PENDING_STATUS");
    		row.add(pendingStatus);
    		boolean approved = false;
    		if (pendingStatus.equals("FALSE")){
	    		pstmtApproved.setInt(1, orderId);
	    		rs1 = pstmtApproved.executeQuery();
	    		if (rs1.next()){
	    			approved = true;
	    			row.add(rs1.getString("APPROVED_DATE"));
	    			row.add(rs1.getString("APPROVED_TIME"));
	    			row.add(rs1.getString("APPROVED_BY"));
	    		}
    		}
    		if (!approved){
    			row.add(null);
    			row.add(null);
    			row.add(null);
    		}
    		
    		pstmtErpReleased.setInt(1, orderId);
    		rs1 = pstmtErpReleased.executeQuery();
    		if (rs1.next()){
    			row.add(rs1.getString("ERP_RELEASED_DATE"));
    			row.add(rs1.getString("ERP_RELEASED_TIME"));
    		}else{
    			row.add(null);
    			row.add(null);    			
    		}
			
    		pstmtSend.setInt(1, orderId);
    		rs1 = pstmtSend.executeQuery();
    		if (rs1.next()){
    			row.add(rs1.getString("SEND_DATE"));
    			row.add(rs1.getString("SEND_TIME"));
    		}else{
    			row.add(null);
    			row.add(null);
    		}
    		
    		row.add(rs.getString("CUSTOMER_PO_NUMBER"));
    		row.add(rs.getString("ORDER_SUB_TOTAL"));
    		row.add(rs.getString("FREIGHT"));
    		row.add(rs.getString("HANDLING"));
    		row.add(rs.getString("CURRENCY_CODE"));    		
    		rows.add(row);
    	}
        
		return rows;
	}
	
	private final static String getDateWhere(String begDateS, String endDateS,
            String dateFmt)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        java.util.Date startDate = sdf.parse(begDateS);
        java.util.Date endDateOrig = sdf.parse(endDateS);
        java.util.Date endDate = addDays(endDateOrig,1);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(PATTERN);
        String startDateTargetS = sdfTarget.format(startDate);
        String endDateTargetS = sdfTarget.format(endDate);
        return "AND TO_DATE(TO_CHAR(ORIGINAL_ORDER_DATE, 'MM/dd/yyyy') || TO_CHAR(ORIGINAL_ORDER_TIME, 'HH24:MI'), 'MM/dd/yyyyHH24:MI') "
                + " BETWEEN TO_DATE('"
                + startDateTargetS
                + "','MM/dd/yyyy HH24:MI') AND TO_DATE('"
                + endDateTargetS
                + "','MM/dd/yyyy HH24:MI') ";
    }


	private static Date getDate(String source, SimpleDateFormat sdf) {
        try {
            return (sdf == null) ? null : sdf.parse(source);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Add nDays to input date.
     *
     * @param nDays  Number of days to add. May be negative.
     * @return  Date as requested.
     */
    public static java.util.Date addDays(java.util.Date date, int nDays)
    {
    	if (date == null)
        throw new IllegalArgumentException("Date is null in ReSaleOrderReport.addDays()");
      // Create a calendar based on given date
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      // Add/subtract the specified number of days
      calendar.add(Calendar.DAY_OF_MONTH, nDays);
      return calendar.getTime();
    }


    private GenericReportColumnViewVector getHeader() {    	
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
    	header.add(ReportingUtils.createGenericReportColumnView(
                		"java.lang.String", "Confirm Num", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Site Name", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Site Budget Reference Number", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Placed Date", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Placed Time", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Pending Status", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Approved Date", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Approved Time", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Approved By", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Released Date", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Released Time", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Sent Date", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Sent Time", 0, 255, "VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Customer PO Number", 5, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Order Sub Total", 5, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Freight", 5, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Handling", 5, 20, "NUMBER"));
		header.add(ReportingUtils.createGenericReportColumnView(
		                "java.lang.String", "Currency Code", 0, 255, "VARCHAR2"));

        return header;
    }

}
