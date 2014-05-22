package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.dao.ReportUserActivityDataAccess;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.ReportUserActivityData;
import com.cleanwise.service.api.value.ReportUserActivityDataVector;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserActivityReportNew implements GenericReportMulti {
    private final static int MAX_PARAMS_LEN = 4000;
    private final static String selUserStoreInfo = "select distinct ua.bus_entity_id as store_id, be.short_desc as store_name " +
	"from clw_user u " +
	"left outer join clw_user_assoc ua on u.user_id = ua.user_id " +
	"left outer join clw_bus_entity be on ua.bus_entity_id = be.bus_entity_id " +
	"where u.user_type_cd in ('CUSTOMER','MULTI-SITE BUYER') " +
	"and ua.user_assoc_cd = 'STORE' " +
	"and user_name = ?";
    private final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSSS");
    
    public final static String LINE_SEPARATOR = System
            .getProperty("line.separator") == null ? "\n" : System
            .getProperty("line.separator");

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "MM/dd/yyyy");

    public GenericReportResultViewVector process(ConnectionContainer pCons,
            GenericReportData pReportData, Map pParams) throws Exception {
        String pStartDateS = (String) pParams.get("BEG_DATE");
        if (pStartDateS == null)
        	pStartDateS = (String) pParams.get("BEG_DATE_OPT");
        String pEndDateS = (String) pParams.get("END_DATE");
        if (pEndDateS == null)
        	pEndDateS = (String) pParams.get("END_DATE_OPT");
        Connection conn = pCons.getMainConnection();
        Connection reportConn = pCons.getReportConnection();
        PreparedStatement pstmt = conn.prepareStatement(selUserStoreInfo);
        final Map<String, String[]> userStoreMap = new TreeMap<String, String[]>();

        Date now = new Date();
        if (!Utility.isSet(pStartDateS)){
        	pStartDateS = DATE_FORMAT.format(now);
        }
        if (!Utility.isSet(pEndDateS)){
        	pEndDateS = DATE_FORMAT.format(now);
        }
        final Date startDate = getDate(pStartDateS, DATE_FORMAT);
        final Date endDate;
        Date bufferDate = getDate(pEndDateS, DATE_FORMAT);
        if (bufferDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(bufferDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            endDate = c.getTime();
        } else {
            endDate = null;
        }
        String ffnn = ".";
        
        
        int hoursInterval = 3;
        List<ReportUserActivityData> dataRows = getLogItems(startDate, endDate, reportConn);
        List dataLists = new ArrayList();
        for (int i = 0; i < dataRows.size(); i++) {
        	ReportUserActivityData row = (ReportUserActivityData) dataRows.get(i);
            dataLists.add(toList(row));
        }
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        GenericReportResultView pageGeneral = GenericReportResultView
                .createValue();
        pageGeneral.setTable(getGroupData(dataRows, hoursInterval, pstmt, userStoreMap));
        pageGeneral.setHeader(getGroupHeader(hoursInterval));
        pageGeneral.setColumnCount(pageGeneral.getHeader().size());
        pageGeneral.setName("Group");
        GenericReportResultView pageDetail = GenericReportResultView
                .createValue();
        pageDetail.setTable(new ArrayList(dataLists));
        pageDetail.setHeader(getDetailHeader());
        pageDetail.setColumnCount(pageDetail.getHeader().size());
        pageDetail.setName("Details");
        resultV.add(pageDetail);
        resultV.add(pageGeneral);
        pstmt.close();
        return resultV;
    }
    
    private GenericReportColumnViewVector getDetailHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Store Id", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Store Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Session Id", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "User Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action Class", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "HTTP Start Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action Start Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action End Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "HTTP End Date", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Action Result", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "HTTP Result", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.math.BigDecimal", "Action Dur", 5, 20, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.math.BigDecimal", "HTTP Dur", 5, 20, "NUMBER"));
        header
                .add(ReportingUtils.createGenericReportColumnView(
                        "java.lang.String", "Referer", 500, MAX_PARAMS_LEN,
                        "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Params", 0, MAX_PARAMS_LEN, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Finish File", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "Server Name", 0, 255, "VARCHAR2"));
        return header;
    }

    
    private static GenericReportColumnViewVector getGroupHeader(
            int hoursInterval) {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(
                "java.lang.String", "User Name", 0, 255, "VARCHAR2"));
        List<Integer> hours = getStartHours(hoursInterval);
        for (int i : hours) {
            header.add(ReportingUtils.createGenericReportColumnView(
                    "java.math.BigDecimal", "Action " + i, 5, 20, "NUMBER"));
            header.add(ReportingUtils.createGenericReportColumnView(
                    "java.math.BigDecimal", "HTTP " + i, 5, 20, "NUMBER"));
        }
        return header;
    }
    
    private static Date getDate(String source, SimpleDateFormat sdf) {
        try {
            return (sdf == null) ? null : sdf.parse(source);
        } catch (Exception e) {
            return null;
        }
    }

    private List<ReportUserActivityData> getLogItems(Date startDate,
			Date endDate, Connection reportConn) throws Exception {
    	DBCriteria dbc = new DBCriteria();
        dbc.addGreaterOrEqual(ReportUserActivityDataAccess.ACTION_START_TIME,startDate);
        dbc.addLessThan(ReportUserActivityDataAccess.ACTION_END_TIME, endDate);
        dbc.addOrderBy(ReportUserActivityDataAccess.REPORT_USER_ACTIVITY_ID);
        ReportUserActivityDataVector userActivityDV = ReportUserActivityDataAccess.select(reportConn,dbc);
		return userActivityDV;
	}
    
    public List toList(ReportUserActivityData logItem) {
        List list = new ArrayList();
        if (logItem.getStoreId() == 0)
        	list.add(null);
        else
        	list.add(logItem.getStoreId()+"");
        list.add(logItem.getStoreName());
        
        list.add(logItem.getSessionId());
        list.add(logItem.getUserName());
        list.add(logItem.getActionClass());
        list.add(logItem.getAction());
        if (logItem.getHttpStartTime()==null)
        	list.add("");
        else
        	list.add(DEFAULT_SDF.format(logItem.getHttpStartTime()));
        if (logItem.getActionStartTime()==null)
        	list.add("");
        else
        	list.add(DEFAULT_SDF.format(logItem.getActionStartTime()));
        if (logItem.getActionEndTime()==null)
        	list.add("");
        else
        	list.add(DEFAULT_SDF.format(logItem.getActionEndTime()));
        if (logItem.getHttpEndTime()==null)
        	list.add("");
        else
        	list.add(DEFAULT_SDF.format(logItem.getHttpEndTime()));
        list.add(logItem.getActionResult());
        list.add(logItem.getHttpResult());
        list.add(logItem.getActionDuration());
        list.add(logItem.getHttpDuration());
        list.add(logItem.getReferer());
        list.add(logItem.getParams());
        list.add(logItem.getFinishFile());
        list.add(logItem.getServerName());
        return list;
    }

    private static ArrayList getGroupData(List records, int hoursInterval, PreparedStatement pstmt, Map<String, String[]> userStoreMap) throws Exception {
        Map<Date, Map<Integer, List<ReportUserActivityData>>> data = new TreeMap<Date, Map<Integer, List<ReportUserActivityData>>>();
        for (int i = 0; i < records.size(); i++) {
        	ReportUserActivityData row = (ReportUserActivityData) records.get(i);
        	Date startTime = (row.getHttpStartTime()==null) ? row.getActionStartTime() : row.getHttpStartTime();
            Date key1 = getDay(startTime);
            Map<Integer, List<ReportUserActivityData>> value1 = data.get(key1);
            if (value1 == null) {
                value1 = new TreeMap<Integer, List<ReportUserActivityData>>();
                data.put(key1, value1);
            }
            int key2 = getStartHour(startTime, hoursInterval);
            List<ReportUserActivityData> value2 = value1.get(key2);
            if (value2 == null) {
                value2 = new ArrayList<ReportUserActivityData>();
                value1.put(key2, value2);
            }
            value2.add(row);
        }
        ArrayList list = new ArrayList();
        for (Map.Entry<Date, Map<Integer, List<ReportUserActivityData>>> i : data.entrySet()) {
            Date key1 = i.getKey();
            Map<Integer, List<ReportUserActivityData>> val1 = i.getValue();
            list.add(getDayHeader(key1, hoursInterval));
            list.addAll(getDayData(val1, getStartHours(hoursInterval), pstmt, userStoreMap));
        }
        return list;
    }
    
    private static int getStartHour(Date date, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int i = 0;
        for (; i <= hour; i += interval) {
        }
        hour = i - interval;
        return hour >= 0 && hour <= 23 ? hour : 0;
    }

    private static List getDayHeader(Date date, int interval) {
        List list = new ArrayList();
        list.add(DATE_FORMAT.format(date));
        return list;
    }

    private static List getDayData(Map<Integer, List<ReportUserActivityData>> data,
            List<Integer> intervals, PreparedStatement pstmt, Map<String, String[]> userStoreMap) throws Exception {
        Map<String, Map<Integer, BigDecimal[]>> map = new TreeMap<String, Map<Integer, BigDecimal[]>>();
        for (Map.Entry<Integer, List<ReportUserActivityData>> e : data.entrySet()) {
            int key1 = e.getKey();
            List<ReportUserActivityData> val1 = e.getValue();
            for (ReportUserActivityData r : val1) {
                if (r.getUserName() == null) {
                    continue;
                }
                Map<Integer, BigDecimal[]> val2 = map.get(r.getUserName());
                if (val2 == null) {
                    val2 = new TreeMap<Integer, BigDecimal[]>();
                    map.put(r.getUserName(), val2);
                }
                BigDecimal[] val3 = val2.get(key1);
                if (val3 == null) {
                    val3 = new BigDecimal[] { r.getActionDuration(), r.getHttpDuration() };
                    val2.put(key1, val3);
                } else {
                    val3[0] = average(val3[0], r.getActionDuration());
                    val3[1] = average(val3[1], r.getHttpDuration());
                }
            }
        }
        List result = new ArrayList();
        for (Map.Entry<String, Map<Integer, BigDecimal[]>> e : map.entrySet()) {
            List row = new ArrayList();
            String store = "";
            String[] vals = userStoreMap.get(e.getKey());
            if (vals == null) {
            	pstmt.setString(1, e.getKey());
            	ResultSet rs = pstmt.executeQuery();
            	if (rs.next()){
            		String storeId = rs.getString("store_id");
                    String storeName = rs.getString("store_name");
                    vals = new String[] { storeId, storeName };                    
            	}else{
            		vals = new String[] { null, null };
            	}
            	userStoreMap.put(e.getKey(), vals);
            }
            if (vals != null && vals[0] != null) {
                store = vals[0] + "/" + vals[1] + " ";
            }
            row.add(store + e.getKey());
            for (Integer i : intervals) {
                BigDecimal[] avgs = e.getValue().get(i);
                if (avgs != null) {
                    row.add(avgs[0]);
                    row.add(avgs[1]);
                } else {
                    row.add(null);
                    row.add(null);
                }
            }
            result.add(row);
        }
        return result;
    }

    private static BigDecimal average(BigDecimal p1, BigDecimal p2) {
        if (p1 != null && p2 != null) {
            return p1.add(p2).divide(new BigDecimal(2),
                    BigDecimal.ROUND_HALF_UP);
        } else if (p1 != null) {
            return p1;
        } else if (p2 != null) {
            return p2;
        }
        return null;
    }

    private static List<Integer> getStartHours(int hoursInterval) {
        final List<Integer> startHours = new ArrayList<Integer>();
        for (int i = 0; i <= 23; i += hoursInterval) {
            startHours.add(i);
        }
        return startHours;
    }

    private static Date getDay(Date date) {
        Calendar source = Calendar.getInstance();
        source.setTime(date);
        Calendar c = Calendar.getInstance();
        c.set(source.get(Calendar.YEAR), source.get(Calendar.MONTH), source
                .get(Calendar.DATE), 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

}
