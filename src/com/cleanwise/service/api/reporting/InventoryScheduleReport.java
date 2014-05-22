package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ScheduleProc;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;


/**
 * Title:        InventoryScheduleReport
 * Description:  The report which shows the schedule of process for the inventory orders.
 * Purpose:      process_scheduled_orders batch optimization.
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         24.11.2008
 * Time:         14:51:36
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class InventoryScheduleReport implements GenericReport, java.io.Serializable {

    private static final Logger log = Logger.getLogger(InventoryScheduleReport.class);

    public static interface REPORT_HEADER {
        public static final String CUTOFF_DATE = "Cutoff Date";
        public static final String CUTOFF_TIME = "Cutoff Time";
        public static final String SITE_ID = "SiteId";
        public static final String ACCOUNT_ID = "AccountId";
        public static final String CATALOG_ID = "Catalog";
        public static final String DISTRIBUTOR_ID = "Distributor";
        public static final String ZIP = "Zip Code";
        public static final String SCHEDULE_ID = "Schedule";
        public static final String SCHEDULE_DESC = "Schedule Desc";
        public static final String SCHEDULE_RULE = "Schedule Rule Code";
        public static final String CYCLE = "Cycle";
        public static final String SCHEDULE_EFF_DATE = "Schedule Eff.Date";
        public static final String SCHEDULE_EXP_DATE = "Schedule Exp.Date";
        public static final String CUTOFF_DAY = "Cutoff Day";
        public static final String ACCOUNT_CUTOFF_DAY = "Account Cutoff Day";
        public static final String SITE_DELIVERY_SCHEDULE = "Site Dev.Schedule";
        public static final String DELIVERY_DATE = "Delivery Date";
    }

    public static final String TIME_FORMAT = "hh:mm a";
    public static final String DATE_FORMAT = "MM/dd/yyyy";

    SimpleDateFormat sdfTime = new SimpleDateFormat(TIME_FORMAT);
    SimpleDateFormat sdfDate = new SimpleDateFormat(DATE_FORMAT);

    public static int getColumnIndex(GenericReportColumnViewVector pHeader, String pColumnName) {
        int idx = -1;
        for (Object oCol : pHeader) {
            ++idx;
            GenericReportColumnView hCol = (GenericReportColumnView) oCol;
            if (hCol.getColumnName().equals(pColumnName)) {
                return idx;
            }
        }
        return -1;
    }

    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        log.info("process => BEGIN");

        String begDateStr = (String) pParams.get("BEG_DATE");
        String endDateStr = (String) pParams.get("END_DATE_OPT");
        String invModernShopping = (String) pParams.get("INV_MODERN_SHOPPING");

        if (!ReportingUtils.isValidDate(begDateStr)) {
            String mess = "^clw^\"" + begDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }

        if (Utility.isSet(endDateStr) && !ReportingUtils.isValidDate(endDateStr)) {
            String mess = "^clw^\"" + endDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }

        ArrayList<GregorianCalendar> testCalendars = new ArrayList<GregorianCalendar>();

        GregorianCalendar begCal = new GregorianCalendar();
        begCal.setTime(ReportingUtils.parseDate(begDateStr));
        begCal.set(Calendar.HOUR_OF_DAY, 0);
        begCal.set(Calendar.MINUTE, 0);
        begCal.set(Calendar.SECOND, 0);
        begCal.set(Calendar.MILLISECOND, 0);

        testCalendars.add(begCal);

        if (Utility.isSet(endDateStr)) {

            GregorianCalendar endCal = new GregorianCalendar();

            endCal.setTime(ReportingUtils.parseDate(endDateStr));
            endCal.set(Calendar.HOUR_OF_DAY, 0);
            endCal.set(Calendar.MINUTE, 0);
            endCal.set(Calendar.SECOND, 0);
            endCal.set(Calendar.MILLISECOND, 0);

            if (endCal.after(begCal)) {
                long diffVal = endCal.getTime().getTime() - begCal.getTime().getTime();
                GregorianCalendar tempCal = (GregorianCalendar) begCal.clone();
                int diffDays = (int) ((double)diffVal /(double)(24*60*60*1000));
                for (int i = 1; i <= diffDays; i++) {
                    tempCal.add(Calendar.DAY_OF_YEAR, 1);
                    GregorianCalendar nextCal = (GregorianCalendar) tempCal.clone();
                    testCalendars.add(nextCal);
                }
            } else {
                String mess = "^clw^End Date cannot be on or before Begin Date^clw^";
                throw new Exception(mess);
            }
        }

        ArrayList<List> records = new ArrayList<List>();
        Distributor distrEjb = APIAccess.getAPIAccess().getDistributorAPI();

        ArrayList<SiteCuttofDayRecord> inventorySiteRecords = getInventorySiteRecords(pCons.getMainConnection(), Utility.isTrue(invModernShopping));

        //builds the optimal structure for the schedule selection
        HashMap<String, HashMap<Integer, ArrayList<SiteCuttofDayRecord>>> groupSiteCutoffMap = groupByScheduleKeyAndCutoffDays(inventorySiteRecords);

        if (!inventorySiteRecords.isEmpty()) {

            for (GregorianCalendar runCal : testCalendars) {

                log.info("process => Creating data for date: "+sdfDate.format(runCal.getTime()));

                IdVector scheduleIds = distrEjb.getSchedules(runCal.getTime());

                for (Object oScheduleId : scheduleIds) {

                    ScheduleJoinView sch = distrEjb.getDeliveryScheduleById((Integer) oScheduleId);

                    ArrayList<String> zipCodes = extractZipCodes(sch);

                    for (String zipCode : zipCodes) {

                        String scheduleKey = getScheduleKey(sch.getSchedule().getBusEntityId(),zipCode);
                        HashMap<Integer, ArrayList<SiteCuttofDayRecord>> groupSiteCutoffDays = groupSiteCutoffMap.get(scheduleKey);

                        if (groupSiteCutoffDays != null) {

                            for (Integer siteCutoffDays : groupSiteCutoffDays.keySet()) {

                                ArrayList<SiteCuttofDayRecord> siteRecords = groupSiteCutoffDays.get(siteCutoffDays);

                                for (SiteCuttofDayRecord siteRecord : siteRecords) {

                                    SiteDeliveryScheduleView siteDelivery = siteRecord.getSiteDelivery();
                                    if (siteDelivery == null || checkScheduleDeliverySetup(siteDelivery)) {

                                        ScheduleProc scheduleProc = new ScheduleProc(sch, siteDelivery, siteCutoffDays);
                                        scheduleProc.initSchedule();
                                        ScheduleOrderDates orderDates = scheduleProc.getOrderDeliveryDates(runCal.getTime(), runCal.getTime());

                                        if (orderDates != null) {

                                            if (runCal.getTime().compareTo(orderDates.getNextOrderCutoffDate()) == 0) {

                                                String cutoffTime = extractCutoffTime(sch);
                                                if (Utility.isSet(cutoffTime)) {

                                                    aRecord record = new aRecord();

                                                    record.setAccountId(siteRecord.getAccountId());
                                                    record.setSiteId(siteRecord.getSiteId());
                                                    record.setCatalogId(siteRecord.getCatalogId());
                                                    record.setZipCode(zipCode);

                                                    record.setScheduleId(sch.getSchedule().getScheduleId());
                                                    record.setScheduleDesc(sch.getSchedule().getShortDesc());
                                                    record.setScheduleRuleCd(sch.getSchedule().getScheduleRuleCd());
                                                    record.setCycle(sch.getSchedule().getCycle());
                                                    record.setDistributorId(siteRecord.getDistributorId());

                                                    if (sch.getSchedule().getEffDate() != null) {
                                                        record.setScheduleEffDate(sdfDate.format(sch.getSchedule().getEffDate()));
                                                    }

                                                    if (sch.getSchedule().getExpDate() != null) {
                                                        record.setScheduleExpDate(sdfDate.format(sch.getSchedule().getExpDate()));
                                                    }

                                                    record.setCutoffDate(sdfDate.format(runCal.getTime()));
                                                    record.setCutoffTime(cutoffTime);
                                                    record.setCutoffDay(extractCutoffDay(sch));
                                                    record.setAccountCutoffDay(siteRecord.getCuttofDays());
                                                    record.setSiteDeliverySchedule(siteDelivery == null ? null : siteDelivery.getSiteScheduleType());
                                                    record.setOrderDeliveryDate(sdfDate.format(orderDates.getNextOrderDeliveryDate()));

                                                    records.add(record.toList());
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader();
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Inv.Sch.Report");
        result.setTable(records);

        log.info("process => END");

        return result;

    }

    private boolean checkScheduleDeliverySetup(SiteDeliveryScheduleView siteDelivery) {

        String siteScheduleType = Utility.strNN(siteDelivery.getSiteScheduleType());
        if (siteScheduleType.startsWith("Any")) {
            //All right. Go ahead
        } else if (siteDelivery.getSiteScheduleType().startsWith("Spe")) {
            if (siteDelivery.getIntervWeek() == null) {
                log.info("checkScheduleDeliverySetup => No schedule delivery setup for site=" + siteDelivery.getSiteId());
                return false;
            }
        } else {
            if (!siteDelivery.getWeek1ofMonth() &&
                    !siteDelivery.getWeek2ofMonth() &&
                    !siteDelivery.getWeek3ofMonth() &&
                    !siteDelivery.getWeek4ofMonth() &&
                    !siteDelivery.getLastWeekofMonth()
                    ) {
                log.info("checkScheduleDeliverySetup => No schedule delivery setup for site=" + siteDelivery.getSiteId());
                return false;
            }
        }

        return true;

    }

    private String getScheduleKey(int distributorId, String zipCode) {
        return distributorId+"_"+zipCode;
    }

    private GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.ACCOUNT_ID, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.SITE_ID, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.CATALOG_ID, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.DISTRIBUTOR_ID, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.ZIP, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.SCHEDULE_ID, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SCHEDULE_DESC, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SCHEDULE_RULE, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.CYCLE, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SCHEDULE_EFF_DATE, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SCHEDULE_EXP_DATE, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.CUTOFF_DAY, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.ACCOUNT_CUTOFF_DAY, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SITE_DELIVERY_SCHEDULE, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.CUTOFF_DATE, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.CUTOFF_TIME, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.DELIVERY_DATE, 0, 255, "VARCHAR2"));

        return header;
    }

    private String extractCutoffTime(ScheduleJoinView sjVw) {
        String cutOffTime = "";
        ScheduleDetailDataVector scheduleDetails = sjVw.getScheduleDetail();
        if (scheduleDetails != null) {
            for (Object scheduleDetail : scheduleDetails) {
                ScheduleDetailData detail = (ScheduleDetailData) scheduleDetail;
                String valueTypeCd = detail.getScheduleDetailCd();
                if (valueTypeCd.equals(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_TIME)) {
                    String value = detail.getValue();
                    if (cutOffTime.length() > 0) {
                        return null;
                    } else {
                        cutOffTime = value;
                    }
                }
            }
        } else {
            return null;
        }
        return cutOffTime;
    }

    private String extractCutoffDay(ScheduleJoinView sjVw) {
        String cutOffDay = "";
        ScheduleDetailDataVector scheduleDetails = sjVw.getScheduleDetail();
        if (scheduleDetails != null) {
            for (Object scheduleDetail : scheduleDetails) {
                ScheduleDetailData detail = (ScheduleDetailData) scheduleDetail;
                String valueTypeCd = detail.getScheduleDetailCd();
                if (valueTypeCd.equals(RefCodeNames.SCHEDULE_DETAIL_CD.CUTOFF_DAY)) {
                    String value = detail.getValue();
                    if (cutOffDay.length() > 0) {
                        return null;
                    } else {
                        cutOffDay = value;
                    }
                }
            }
        } else {
            return null;
        }
        return cutOffDay;
    }

    private ArrayList<String> extractZipCodes(ScheduleJoinView sjVw) {
        ArrayList<String> zipCodes = new ArrayList<String>();
        ScheduleDetailDataVector scheduleDetails = sjVw.getScheduleDetail();
        if (scheduleDetails != null) {
            for (Object scheduleDetail : scheduleDetails) {
                ScheduleDetailData detail = (ScheduleDetailData) scheduleDetail;
                String valueTypeCd = detail.getScheduleDetailCd();
                if (valueTypeCd.equals(RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE)) {
                    String value = detail.getValue();
                    if (Utility.isSet(value) && !zipCodes.contains(value)) {
                        zipCodes.add(value);
                    }
                }
            }
        }
        return zipCodes;
    }

    /**
     * builds the optimal structure for the schedule selection
     * @param records inventory site collection
     * @return  HashMap<postal_code+distributorId, HashMap<accountCutoffDays, ArrayList<site records>>>
     */
    private HashMap<String, HashMap<Integer, ArrayList<SiteCuttofDayRecord>>> groupByScheduleKeyAndCutoffDays(ArrayList<SiteCuttofDayRecord> records) {
        HashMap<String, HashMap<Integer, ArrayList<SiteCuttofDayRecord>>> res = new HashMap<String, HashMap<Integer, ArrayList<SiteCuttofDayRecord>>>();
        for (SiteCuttofDayRecord record : records) {
            String key = getScheduleKey(record.getDistributorId(),record.getPostalCode());
            if (!res.containsKey(key)) {
                HashMap<Integer, ArrayList<SiteCuttofDayRecord>> cutoffDaysMap = new HashMap<Integer, ArrayList<SiteCuttofDayRecord>>();
                ArrayList<SiteCuttofDayRecord> recList = new ArrayList<SiteCuttofDayRecord>();
                recList.add(record);
                cutoffDaysMap.put(record.getCuttofDays(), recList);
                res.put(key, cutoffDaysMap);
            } else {
                HashMap<Integer, ArrayList<SiteCuttofDayRecord>> cutoffDaysMap = res.get(key);
                if (!cutoffDaysMap.containsKey(record.getCuttofDays())) {
                    ArrayList<SiteCuttofDayRecord> recList = new ArrayList<SiteCuttofDayRecord>();
                    recList.add(record);
                    cutoffDaysMap.put(record.getCuttofDays(), recList);
                } else {
                    ArrayList<SiteCuttofDayRecord> recList = cutoffDaysMap.get(record.getCuttofDays());
                    recList.add(record);
                }
            }
        }
        return res;
    }

    /**
     * gets inventory site records
     * @param conn connection
     * @param invModernShopping  inventory shopping(modern/classic)
     * @return  inventory site records
     * @throws Exception if errors
     */
    private ArrayList<SiteCuttofDayRecord> getInventorySiteRecords(Connection conn, boolean invModernShopping) throws Exception {

        ArrayList<SiteCuttofDayRecord> res = new ArrayList<SiteCuttofDayRecord>();

        String invSiteIdsSQL;
        if (invModernShopping) {
            invSiteIdsSQL = "SELECT BE.BUS_ENTITY_ID  FROM CLW_PROPERTY INV_PROP, CLW_PROPERTY INV_PROP_TYPE ,CLW_BUS_ENTITY BE\n" +
                    "  WHERE INV_PROP.PROPERTY_TYPE_CD = '" + RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING + "' \n" +
                    "   AND INV_PROP.CLW_VALUE = 'on' \n" +
                    "   AND INV_PROP_TYPE.PROPERTY_TYPE_CD = '" + RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING_TYPE + "' \n" +
                    "   AND INV_PROP_TYPE.CLW_VALUE = 'on' \n" +
                    "   AND INV_PROP.BUS_ENTITY_ID = INV_PROP_TYPE.BUS_ENTITY_ID " +
                    "   AND BE.BUS_ENTITY_ID = INV_PROP.BUS_ENTITY_ID \n" +
                    "   AND BE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "'\n" +
                    "   AND BE.BUS_ENTITY_STATUS_CD='" + RefCodeNames.PROPERTY_STATUS_CD.ACTIVE + "'";
        } else {
            invSiteIdsSQL = "SELECT BE.BUS_ENTITY_ID FROM CLW_PROPERTY INV_PROP, CLW_PROPERTY INV_PROP_TYPE, CLW_BUS_ENTITY BE\n" +
                    "  WHERE INV_PROP.PROPERTY_TYPE_CD = '" + RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING + "'\n" +
                    "     AND INV_PROP.CLW_VALUE = 'on' \n" +
                    " AND INV_PROP_TYPE.PROPERTY_TYPE_CD(+) = '" + RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING_TYPE + "'\n" +
                    " AND NVL(INV_PROP_TYPE.CLW_VALUE,'off') != 'on' \n" +
                    " AND INV_PROP.BUS_ENTITY_ID = INV_PROP_TYPE.BUS_ENTITY_ID(+) " +
                    " AND BE.BUS_ENTITY_ID = INV_PROP.BUS_ENTITY_ID \n" +
                    " AND BE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "'\n" +
                    " AND BE.BUS_ENTITY_STATUS_CD='" + RefCodeNames.PROPERTY_STATUS_CD.ACTIVE + "'";
        }

        String sql = "SELECT ACC.BUS_ENTITY_ID,ACC.BUS_ENTITY1_ID,ACC.POSTAL_CODE,ACC.CITY,ACC.COUNTY_CD,ACC.STATE_PROVINCE_CD,ACC.CATALOG_ID,ACC.DISTRIBUTOR_ID,TO_NUMBER(NVL(P1.CLW_VALUE,0)) AS CUTOFF_DAYS,P2.CLW_VALUE AS SITE_DELIVERY FROM \n" +
                "(SELECT BE.BUS_ENTITY_ID,BEA.BUS_ENTITY1_ID,A.POSTAL_CODE,A.CITY,A.COUNTY_CD,A.STATE_PROVINCE_CD,C.CATALOG_ID,DBE.BUS_ENTITY_ID AS DISTRIBUTOR_ID FROM CLW_BUS_ENTITY BE,CLW_BUS_ENTITY_ASSOC BEA,CLW_ADDRESS A,CLW_CATALOG C,CLW_CATALOG_ASSOC CA,CLW_CATALOG_ASSOC DCA,CLW_BUS_ENTITY DBE  \n" +
                "WHERE BEA.BUS_ENTITY1_ID IN (" + invSiteIdsSQL + ")\n" +
                " AND BEA.BUS_ENTITY_ASSOC_CD='" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "'\n" +
                " AND BE.BUS_ENTITY_ID = BEA.BUS_ENTITY2_ID\n" +
                " AND BE.BUS_ENTITY_STATUS_CD = '" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "' " +
                " AND A.BUS_ENTITY_ID = BEA.BUS_ENTITY1_ID \n" +
                " AND A.ADDRESS_TYPE_CD='" + RefCodeNames.ADDRESS_TYPE_CD.SHIPPING + "' \n" +
                " AND A.ADDRESS_STATUS_CD='" + RefCodeNames.ADDRESS_STATUS_CD.ACTIVE + "'" +
                " AND CA.BUS_ENTITY_ID = A.BUS_ENTITY_ID\n" +
                " AND CA.CATALOG_ASSOC_CD = '" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE + "'\n" +
                " AND C.CATALOG_ID = CA.CATALOG_ID\n" +
                " AND C.CATALOG_TYPE_CD = '" + RefCodeNames.CATALOG_TYPE_CD.SHOPPING + "'\n" +
                " AND DCA.CATALOG_ID = C.CATALOG_ID\n" +
                " AND DCA.CATALOG_ASSOC_CD = '" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR + "'\n" +
                " AND DBE.BUS_ENTITY_ID = DCA.BUS_ENTITY_ID\n" +
                " AND DBE.BUS_ENTITY_TYPE_CD='" + RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR + "'\n" +
                " AND DBE.BUS_ENTITY_STATUS_CD='" + RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE + "') ACC \n" +
                " \n" +
                " LEFT JOIN \n" +
                " \n" +
                " CLW_PROPERTY P1 ON ACC.BUS_ENTITY_ID = P1.BUS_ENTITY_ID \n" +
                " AND P1.SHORT_DESC='" + RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS + "' \n" +
                " AND P1.PROPERTY_TYPE_CD='" + RefCodeNames.PROPERTY_TYPE_CD.EXTRA + "' \n" +
                " AND P1.PROPERTY_STATUS_CD='" + RefCodeNames.PROPERTY_STATUS_CD.ACTIVE + "'" +
                " \n" +
                " LEFT JOIN \n" +
                " \n" +
                " CLW_PROPERTY P2 ON ACC.BUS_ENTITY1_ID = P2.BUS_ENTITY_ID \n" +
                " AND P2.SHORT_DESC='" + RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE + "' \n" +
                " AND P2.PROPERTY_TYPE_CD='" + RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE + "' \n" +
                " AND P2.PROPERTY_STATUS_CD='" + RefCodeNames.PROPERTY_STATUS_CD.ACTIVE + "'";

        log.info("getInventorySiteRecords => SQL: " + sql);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {

            SiteCuttofDayRecord x = new SiteCuttofDayRecord();
            SiteDeliveryScheduleView siteDelivery = null;

            x.setAccountId(rs.getInt(1));
            x.setSiteId(rs.getInt(2));
            x.setPostalCode(rs.getString(3));

            x.setCatalogId(rs.getInt(7));
            x.setDistributorId(rs.getInt(8));
            x.setCuttofDays(rs.getInt(9));

            String deliveryDates = rs.getString(10);
            if (Utility.isSet(deliveryDates)) {

                siteDelivery = SiteDeliveryScheduleView.createValue();

                siteDelivery.setSiteId(rs.getInt(1));
                siteDelivery.setPostalCode(rs.getString(3));
                siteDelivery.setCity(rs.getString(4));
                siteDelivery.setState(rs.getString(5));
                siteDelivery.setCounty(rs.getString(6));

                StringTokenizer st = new StringTokenizer(deliveryDates, ":");
                String stype = "", wk1st = "", wk2nd = "", wkenterv = "";
                for (int i = 0; st.hasMoreTokens(); i++) {
                    if (i == 0) stype = st.nextToken();
                    if (i == 1) {
                        wk1st = st.nextToken();
                        wkenterv = wk1st;
                    }
                    if (i == 2) wk2nd = st.nextToken();
                }

                siteDelivery.setSiteScheduleType(deliveryDates);
                if (stype.startsWith("Spe")) {

                    siteDelivery.setIntervWeek(wkenterv);

                } else {

                    if (wk1st.startsWith("Last") || wk2nd.startsWith("Last")) {
                        siteDelivery.setLastWeekofMonth(true);
                    }

                    if (wk1st.equals("1") || wk2nd.equals("1")) {
                        siteDelivery.setWeek1ofMonth(true);
                    }

                    if (wk1st.equals("2") || wk2nd.equals("2")) {
                        siteDelivery.setWeek2ofMonth(true);
                    }

                    if (wk1st.equals("3") || wk2nd.equals("3")) {
                        siteDelivery.setWeek3ofMonth(true);
                    }

                    if (wk1st.equals("4") || wk2nd.equals("4")) {
                        siteDelivery.setWeek4ofMonth(true);
                    }
                }
            }

            x.setSiteDelivery(siteDelivery);
            res.add(x);
        }

        rs.close();
        stmt.close();

        return res;
    }

    public class SiteCuttofDayRecord implements java.io.Serializable {

        private int accountId; //account id
        private int siteId;  //site id
        private int cuttofDays; //(SCHEDULE_CUTOFF_DAYS property of account)
        private String postalCode; //postal code of site
        private int catalogId;   //catalog id
        private int distributorId; //main distributor id for catalog
        private SiteDeliveryScheduleView siteDelivery; // delivery 

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public int getCuttofDays() {
            return cuttofDays;
        }

        public void setCuttofDays(int cuttofDays) {
            this.cuttofDays = cuttofDays;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public void setCatalogId(int catalogId) {
            this.catalogId = catalogId;
        }

        public void setDistributorId(int distributorId) {
            this.distributorId = distributorId;
        }

        public int getCatalogId() {
            return catalogId;
        }

        public int getDistributorId() {
            return distributorId;
        }

        public void setSiteDelivery(SiteDeliveryScheduleView siteDelivery) {
            this.siteDelivery = siteDelivery;
        }

        public SiteDeliveryScheduleView getSiteDelivery() {
            return siteDelivery;
        }
    }

    public class aRecord implements Record, java.io.Serializable {

        private String cutoffTime;
        private String cutoffDate;
        private Integer scheduleId;
        private String scheduleDesc;
        private String scheduleRuleCd;
        private Integer cycle;
        private Integer distributorId;
        private String scheduleEffDate;
        private String scheduleExpDate;
        private String cutoffDay;
        private String orderDeliveryDate;
        private Integer accountCutoffDay;
        private Integer accountId;
        private Integer siteId;
        private String zipCode;
        private Integer catalogId;
        private String siteDeliverySchedule;

        public List toList() {
            ArrayList<Serializable> list = new ArrayList<Serializable>();

            list.add(accountId);
            list.add(siteId);
            list.add(catalogId);
            list.add(distributorId);
            list.add(zipCode);
            list.add(scheduleId);
            list.add(scheduleDesc);
            list.add(scheduleRuleCd);
            list.add(cycle);
            list.add(scheduleEffDate);
            list.add(scheduleExpDate);
            list.add(cutoffDay);
            list.add(accountCutoffDay);
            list.add(siteDeliverySchedule);
            list.add(cutoffDate);
            list.add(cutoffTime);
            list.add(orderDeliveryDate);

            return list;
        }

        public String getSiteDeliverySchedule() {
            return siteDeliverySchedule;
        }

        public void setSiteDeliverySchedule(String siteDeliverySchedule) {
            this.siteDeliverySchedule = siteDeliverySchedule;
        }

        public String getCutoffTime() {
            return cutoffTime;
        }

        public void setCutoffTime(String cutoffTime) {
            this.cutoffTime = cutoffTime;
        }

        public void setCutoffDate(String cutoffDate) {
            this.cutoffDate = cutoffDate;
        }

        public String getCutoffDate() {
            return cutoffDate;
        }

        public void setScheduleId(Integer scheduleId) {
            this.scheduleId = scheduleId;
        }

        public void setScheduleDesc(String scheduleDesc) {
            this.scheduleDesc = scheduleDesc;
        }

        public void setScheduleRuleCd(String scheduleRuleCd) {
            this.scheduleRuleCd = scheduleRuleCd;
        }

        public void setCycle(Integer cycle) {
            this.cycle = cycle;
        }

        public void setDistributorId(Integer distributorId) {
            this.distributorId = distributorId;
        }

        public void setScheduleEffDate(String scheduleEffDate) {
            this.scheduleEffDate = scheduleEffDate;
        }

        public void setScheduleExpDate(String scheduleExpDate) {
            this.scheduleExpDate = scheduleExpDate;
        }

        public void setCutoffDay(String cutoffDay) {
            this.cutoffDay = cutoffDay;
        }

        public Integer getScheduleId() {
            return scheduleId;
        }

        public String getScheduleDesc() {
            return scheduleDesc;
        }

        public Integer getCycle() {
            return cycle;
        }

        public String getScheduleRuleCd() {
            return scheduleRuleCd;
        }

        public Integer getDistributorId() {
            return distributorId;
        }

        public String getScheduleEffDate() {
            return scheduleEffDate;
        }

        public String getScheduleExpDate() {
            return scheduleExpDate;
        }

        public String getCutoffDay() {
            return cutoffDay;
        }

        public void setOrderDeliveryDate(String orderDeliveryDate) {
            this.orderDeliveryDate = orderDeliveryDate;
        }

        public String getOrderDeliveryDate() {
            return orderDeliveryDate;
        }

        public void setAccountCutoffDay(Integer accountCutoffDay) {
            this.accountCutoffDay = accountCutoffDay;
        }

        public Integer getAccountCutoffDay() {
            return accountCutoffDay;
        }

        public Integer getAccountId() {
            return accountId;
        }

        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        public void setSiteId(Integer sitetId) {
            this.siteId = sitetId;
        }


        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public Integer getSiteId() {
            return siteId;
        }

        public void setCatalogId(Integer catalogId) {
            this.catalogId = catalogId;
        }

        public Integer getCatalogId() {
            return catalogId;
        }

        public String getZipCode() {
            return zipCode;
        }
    }
}
