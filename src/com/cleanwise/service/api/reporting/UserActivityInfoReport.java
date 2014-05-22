package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.ReportUserActivityData;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class UserActivityInfoReport implements GenericReportMulti {
    private final static int MAX_PARAMS_LEN = 4000;
    private final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private final static String DB_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";

    public GenericReportResultViewVector process(ConnectionContainer pCons,
                                                 GenericReportData pReportData,
                                                 Map pParams) throws Exception {
        Date now = new Date();
        Date time = null;
        Date beginDate = null;
        Date endDate = null;
        int durationBoundary = 10;

        String pBeginDateS = (String)pParams.get("BEG_DATE");
        if (pBeginDateS == null) {
            pBeginDateS = (String)pParams.get("BEG_DATE_OPT");
        }
        String pBeginTimeS = null;
        if (Utility.isSet(pBeginDateS)) {
            pBeginTimeS = (String)pParams.get("BEG_TIME");
            if (pBeginTimeS == null) {
                pBeginTimeS = (String)pParams.get("BEG_TIME_OPT");
            }
        }
        if (Utility.isSet(pBeginDateS)) {
            beginDate = getDate(pBeginDateS, DATE_FORMAT);
            if (beginDate == null) {
                throw new RemoteException("^clw^" + "'Begin Date' is not in format: " + pBeginDateS + "^clw^");
            }
            if (Utility.isSet(pBeginTimeS)) {
                time = getDate(pBeginTimeS, TIME_FORMAT);
                if (time == null) {
                    throw new RemoteException("^clw^" + "'Begin Time' is not in format: " + pBeginTimeS + "^clw^");
                }
                beginDate = mergeHoursAndMins(beginDate, time);
            }
        } else {
            beginDate = shiftToHours(now, -1);
        }
        
        String pEndDateS = (String)pParams.get("END_DATE");
        if (pEndDateS == null) {
            pEndDateS = (String)pParams.get("END_DATE_OPT");
        }
        String pEndTimeS = null;
        if (Utility.isSet(pEndDateS)) {
            pEndTimeS = (String)pParams.get("END_TIME");
            if (pEndTimeS == null) {
                pEndTimeS = (String)pParams.get("END_TIME_OPT");
            }
        }
        if (Utility.isSet(pEndDateS)) {
            endDate = getDate(pEndDateS, DATE_FORMAT);
            if (endDate == null) {
                throw new RemoteException("^clw^" + "'End Date' is not in format: " + pEndDateS + "^clw^");
            }
            if (Utility.isSet(pEndTimeS)) {
                time = getDate(pEndTimeS, TIME_FORMAT);
                if (time == null) {
                    throw new RemoteException("^clw^" + "'End Time' is not in format: " + pEndTimeS + "^clw^");
                }
                endDate = mergeHoursAndMins(endDate, time);
            }
        } else {
            endDate = now;
        }
        
        String pDurationBoundaryS = (String)pParams.get("DURATION_BOUNDARY");
        if (pDurationBoundaryS == null) {
            pDurationBoundaryS = (String)pParams.get("DURATION_BOUNDARY_OPT");
        }
        if (Utility.isSet(pDurationBoundaryS)) {
            try {
                durationBoundary = Integer.parseInt(pDurationBoundaryS);
            } catch (NumberFormatException e) {
                throw new RemoteException("^clw^" + "'Duration Boundary' is not in format: " + pDurationBoundaryS + "^clw^");
            }
        }
        
        String pDBurlS = (String)pParams.get("DB_URL");
        if (!Utility.isSet(pDBurlS)) {
            throw new RemoteException("^clw^" + "'DB Url' parameter is empty!" + "^clw^");
        }
        
        String pDBuserS = (String)pParams.get("DB_USER");
        if (!Utility.isSet(pDBuserS)) {
            throw new RemoteException("^clw^" + "'DB User' parameter is empty!" + "^clw^");
        }
        
        String pDBpasswordS = (String)pParams.get("DB_PASSWORD");
        if (!Utility.isSet(pDBpasswordS)) {
            throw new RemoteException("^clw^" + "'DB Password' parameter is empty!" + "^clw^");
        }
        
        String checkBeginDate = DEFAULT_SDF.format(beginDate);
        String checkEndDate = DEFAULT_SDF.format(endDate);
        
        if (endDate.before(beginDate)) {
            throw new RemoteException("^clw^" + "'Begin Date' is AFTER 'End Date'" + "^clw^");
        }
        
        //Connection conn = pCons.getMainConnection();
        //Connection reportConn = pCons.getReportConnection();
        
        Connection conn = null;
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        try {
            Class.forName(DB_DRIVER_NAME);
            conn = DriverManager.getConnection(pDBurlS, pDBuserS, pDBpasswordS);

            List<ReportUserActivityData> dataRows = getLogItems(beginDate, endDate, durationBoundary, conn);
            List dataLists = new ArrayList();
            for (ReportUserActivityData row : dataRows) {
                dataLists.add(toList(row));
            }

            GenericReportResultView pageDetail = GenericReportResultView.createValue();
            pageDetail.setTable((ArrayList<ReportUserActivityData>)dataLists);
            pageDetail.setHeader(getDetailHeader());
            pageDetail.setColumnCount(pageDetail.getHeader().size());
            pageDetail.setName("Details");
            resultV.add(pageDetail);

        } catch (ClassNotFoundException e) {
            System.err.println("Could not find the database driver: " + DB_DRIVER_NAME);
            e.printStackTrace();
        } catch (SQLException e ) {
            if (conn != null) {
                System.err.println("SQL Error: " + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch(SQLException e) {}
            }
        }
        return resultV;
    }
    
    private GenericReportColumnViewVector getDetailHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Store Id", 0, 255, "VARCHAR2"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Store Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Session Id", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "User Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action Class", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "HTTP Start Time", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action Start Time", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action End Time", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "HTTP End Time", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Action Result", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "HTTP Result", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Action Duration", 5, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "HTTP Duration", 5, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Referer", 500, MAX_PARAMS_LEN, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Params", 0, MAX_PARAMS_LEN, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Finish File", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Server Name", 0, 255, "VARCHAR2"));

        return header;
    }

    private static Date getDate(String source, SimpleDateFormat sdf) {
        try {
            return (sdf == null) ? null : sdf.parse(source);
        } catch (Exception e) {
            return null;
        }
    }

    private List<ReportUserActivityData> getLogItems(Date beginDate,
                                                     Date endDate,
                                                     int durationBoundary,
                                                     Connection reportConn) throws Exception {
        List<ReportUserActivityData> result = new ArrayList<ReportUserActivityData>();
    	String query = "SELECT " +
                       "STORE_ID, " +
                       "STORE_NAME, " +
                       "SESSION_ID, " +
                       "USER_NAME, " +
                       "ACTION_CLASS, " +
                       "ACTION, " +
                       "HTTP_START_TIME, " +
                       "ACTION_START_TIME, " +
                       "HTTP_END_TIME, " +
                       "ACTION_END_TIME, " +
                       "ACTION_RESULT, " +
                       "HTTP_RESULT, " +
                       "ACTION_DURATION, " +
                       "HTTP_DURATION, " +
                       "REFERER, " +
                       "PARAMS, " +
                       "FINISH_FILE, " +
                       "REQUEST_ID, " +
                       "SERVER_NAME, " +
                       "ADD_DATE, " +
                       "ADD_BY, " +
                       "MOD_DATE, " +
                       "MOD_BY " +
                       "FROM RPT_USER_ACTIVITY " +
                       "WHERE ACTION_DURATION > ? AND " +
                       "((HTTP_START_TIME IS NOT NULL AND HTTP_START_TIME >= ? AND HTTP_START_TIME <= ?) OR" +
                       " (ACTION_START_TIME IS NOT NULL AND ACTION_START_TIME >= ? AND ACTION_START_TIME <= ?) OR" +
                       " (ACTION_END_TIME IS NOT NULL AND ACTION_END_TIME >= ? AND ACTION_END_TIME <= ?) OR" +
                       " (HTTP_END_TIME IS NOT NULL AND HTTP_END_TIME >= ? AND HTTP_END_TIME <= ?))";

        
        PreparedStatement pstmt = reportConn.prepareStatement(query);
        pstmt.setInt(1, durationBoundary);
        pstmt.setTimestamp(2, toSQLTimestamp(beginDate));
        pstmt.setTimestamp(3, toSQLTimestamp(endDate));
        pstmt.setTimestamp(4, toSQLTimestamp(beginDate));
        pstmt.setTimestamp(5, toSQLTimestamp(endDate));
        pstmt.setTimestamp(6, toSQLTimestamp(beginDate));
        pstmt.setTimestamp(7, toSQLTimestamp(endDate));
        pstmt.setTimestamp(8, toSQLTimestamp(beginDate));
        pstmt.setTimestamp(9, toSQLTimestamp(endDate));
        
        ResultSet rs = pstmt.executeQuery();
        ReportUserActivityData userActivityD;
        /*
          store_id                NUMBER(38,0)   NULL,
          store_name              VARCHAR2(100)  NULL,
          session_id              VARCHAR2(40)   NULL,
          user_name               VARCHAR2(40)   NULL,
          action_class            VARCHAR2(40)   NULL,
          action                  VARCHAR2(80)   NULL,
          http_start_time         DATE           NULL,
          action_start_time       DATE           NULL,
          http_end_time           DATE           NULL,
          action_end_time         DATE           NULL,
          action_result           VARCHAR2(10)   NULL,
          http_result             VARCHAR2(10)   NULL,
          action_duration         NUMBER(15,3)   NULL,
          http_duration           NUMBER(15,3)   NULL,
          referer                 VARCHAR2(500)  NULL,
          params                  VARCHAR2(4000) NULL,
          finish_file             VARCHAR2(120)  NULL,
          request_id              VARCHAR2(30)   NULL,
          server_name             VARCHAR2(50)   NULL,
          add_date                DATE           NOT NULL,
          add_by                  VARCHAR2(30)   NULL,
          mod_date                DATE           NOT NULL,
          mod_by                  VARCHAR2(30)   NULL
         */
        while (rs.next()){
            userActivityD = new ReportUserActivityData();
            userActivityD.setStoreId(rs.getInt(1));                     // STORE_ID
            userActivityD.setStoreName(rs.getString(2));                // STORE_NAME
            userActivityD.setSessionId(rs.getString(3));                // SESSION_ID
            userActivityD.setUserName(rs.getString(4));                 // USER_NAME
            userActivityD.setActionClass(rs.getString(5));              // ACTION_CLASS
            userActivityD.setAction(rs.getString(6));                   // ACTION
            userActivityD.setHttpStartTime(rs.getTimestamp(7));         // HTTP_START_TIME
            userActivityD.setActionStartTime(rs.getTimestamp(8));       // ACTION_START_TIME
            userActivityD.setHttpEndTime(rs.getTimestamp(9));           // HTTP_END_TIME
            userActivityD.setActionEndTime(rs.getTimestamp(10));        // ACTION_END_TIME
            userActivityD.setActionResult(rs.getString(11));            // ACTION_RESULT
            userActivityD.setHttpResult(rs.getString(12));              // HTTP_RESULT                                                                        
            userActivityD.setActionDuration(rs.getBigDecimal(13));      // ACTION_DURATION
            userActivityD.setHttpDuration(rs.getBigDecimal(14));        // HTTP_DURATION
            userActivityD.setReferer(rs.getString(15));                 // REFERER
            userActivityD.setParams(rs.getString(16));                  // PARAMS
            userActivityD.setFinishFile(rs.getString(17));              // FINISH_FILE
            userActivityD.setRequestId(rs.getString(18));               // REQUEST_ID
            userActivityD.setServerName(rs.getString(19));              // SERVER_NAME
            userActivityD.setAddDate(rs.getTimestamp(20));              // ADD_DATE
            userActivityD.setAddBy(rs.getString(21));                   // ADD_BY
            userActivityD.setModDate(rs.getTimestamp(22));              // MOD_DATE
            userActivityD.setAddBy(rs.getString(23));                   // MOD_BY
            result.add(userActivityD);
        }
        rs.close();
        pstmt.close();

        return result;
    }
    
    public List toList(ReportUserActivityData userActivityData) {
        List list = new ArrayList();

        //if (userActivityData.getStoreId() == 0) {
        //    list.add(null);
        //} else {
        //    list.add("" + userActivityData.getStoreId());
        //}
        //list.add(userActivityData.getStoreName());
        list.add(userActivityData.getSessionId());
        list.add(userActivityData.getUserName());
        list.add(userActivityData.getActionClass());
        list.add(userActivityData.getAction());
        if (userActivityData.getHttpStartTime() == null) {
            list.add("");
        } else {
            list.add(DEFAULT_SDF.format(userActivityData.getHttpStartTime()));
        }
        if (userActivityData.getActionStartTime() == null) {
            list.add("");
        } else {
            list.add(DEFAULT_SDF.format(userActivityData.getActionStartTime()));
        }
        if (userActivityData.getActionEndTime() == null) {
            list.add("");
        } else {
            list.add(DEFAULT_SDF.format(userActivityData.getActionEndTime()));
        }
        if (userActivityData.getHttpEndTime() == null) {
            list.add("");
        } else {
            list.add(DEFAULT_SDF.format(userActivityData.getHttpEndTime()));
        }
        list.add(userActivityData.getActionResult());
        list.add(userActivityData.getHttpResult());
        list.add(userActivityData.getActionDuration());
        list.add(userActivityData.getHttpDuration());
        list.add(userActivityData.getReferer());
        list.add(userActivityData.getParams());
        list.add(userActivityData.getFinishFile());
        list.add(userActivityData.getServerName());
        return list;
    }
    
    private static Date shiftToHours(Date pDate, int pNumberOfHours) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setLenient(true);

        cal.setTime(pDate);
        cal.add(Calendar.HOUR_OF_DAY, pNumberOfHours);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
    
    private static Date mergeHoursAndMins(Date pDate, Date pTime) {
        Calendar calDate = Calendar.getInstance();
        calDate.setLenient(true);
        calDate.setTime(pDate);

        Calendar calTime = Calendar.getInstance();
        calTime.setTime(pTime);
        
        calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
        calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));

        return calDate.getTime();
    }
    
    private static Timestamp toSQLTimestamp (Date value) {
	Timestamp result = null;
	if (null != value) {
	    result = new Timestamp(value.getTime());
	}
	return result;
    }

}
