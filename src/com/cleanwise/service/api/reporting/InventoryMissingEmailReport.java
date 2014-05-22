package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Site;
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
 * Title:        InventoryMissingEmailReport
 * Description:  The report which shows the schedules information to notify about missing of inventory items.
 * Purpose:      send_inventory_missing_email batch optimization.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         20.05.2009
 */
public class InventoryMissingEmailReport implements GenericReport, java.io.Serializable {

    private static final Logger log = Logger.getLogger(InventoryMissingEmailReport.class);

    public static interface REPORT_HEADER {
        public static final String CUTOFF_DATE = "Cutoff Date";
        public static final String CUTOFF_TIME = "Cutoff Time";
        public static final String SITE_ID = "Site Id";
        public static final String ACCOUNT_ID = "Account Id";
        public static final String CATALOG_ID = "Catalog Id";
        public static final String DISTRIBUTOR_ID = "Distributor Id";
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
        public static final String CUTOFF_REM_DATE = "Cutoff Rem Date";
        public static final String ON_HAND_QUANTITY = "On Hand Qty";
        public static final String EMAIL_PERIOD = "Email Period";
    }

    public static interface PARAM_NAME {
        public static final String STORE_ID = "STORE_ID";
        public static final String ACCOUNT_ID = "ACCOUNT_ID";
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

        String strStoreId = (String) pParams.get(PARAM_NAME.STORE_ID);
        String strAccountId = (String) pParams.get(PARAM_NAME.ACCOUNT_ID);

        int storeId = 0;
        int accountId = 0;
        ArrayList<List> records = new ArrayList<List>();

        storeId = Utility.parseInt(strStoreId);
        accountId = Utility.parseInt(strAccountId);

        ArrayList<ScheduleRowData> scheduleRawRecords = 
            getScheduleRawRecords(pCons.getMainConnection(), storeId, accountId);

        handleScheduleRawRecords(scheduleRawRecords, records);

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader();
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Inv.Missing.Email.Report");
        result.setTable(records);

        log.info("process => END");

        return result;

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
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.CUTOFF_REM_DATE, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.ON_HAND_QUANTITY, 0, 32, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", REPORT_HEADER.EMAIL_PERIOD, 0, 32, "NUMBER"));
        return header;
    }

    private void handleScheduleRawRecords(ArrayList<ScheduleRowData> scheduleRawRecords, ArrayList<List> reportRecords) throws Exception {
        if (scheduleRawRecords == null || reportRecords == null) {
            return;
        }
        if (scheduleRawRecords.isEmpty()) {
            return;
        }

        HashMap<String, HashMap<Integer, ArrayList<ScheduleRowData> > > groupSiteCutoffMap = 
            groupByScheduleKeyAndCutoffDays(scheduleRawRecords);

        Date runDate = new Date();

        Distributor distrEjb = APIAccess.getAPIAccess().getDistributorAPI();
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
        IdVector scheduleIds = distrEjb.getSchedules(runDate);

        for (Object oScheduleId : scheduleIds) {
            ScheduleJoinView sch = distrEjb.getDeliveryScheduleById((Integer) oScheduleId);
            ArrayList<String> zipCodes = extractZipCodes(sch);

            for (String zipCode : zipCodes) {
                String scheduleKey = getScheduleKey(sch.getSchedule().getBusEntityId(), zipCode);
                HashMap<Integer, ArrayList<ScheduleRowData> > groupSiteCutoffDays = groupSiteCutoffMap.get(scheduleKey);

                if (groupSiteCutoffDays != null) {

                    for (Integer siteCutoffDays : groupSiteCutoffDays.keySet()) {
                        ArrayList<ScheduleRowData> siteRecords = groupSiteCutoffDays.get(siteCutoffDays);

                        for (ScheduleRowData siteRecord : siteRecords) {
                            SiteDeliveryScheduleView siteDelivery = ScheduleRowData2SiteDeliveryScheduleView(siteRecord);

                            if (siteDelivery == null || checkScheduleDeliverySetup(siteDelivery)) {
                                ScheduleProc scheduleProc = new ScheduleProc(sch, siteDelivery, siteCutoffDays);
                                scheduleProc.initSchedule();
                                ScheduleOrderDates orderDates = scheduleProc.getOrderDeliveryDates(runDate, runDate);

                                if (orderDates != null) {
                                    if (scheduleProc.getCutoffTime() != null) {

                                        orderDates.setNextOrderCutoffTime(scheduleProc.getCutoffTime());

                                        //log.info("### orderDates: " + orderDates.toString());

                                        if (orderDates.getNextOrderCutoffDate() != null &&
                                            orderDates.getNextOrderCutoffTime() != null) {

                                            GregorianCalendar remCutoffCal = siteEjb.getRemCutoffCalendar(siteRecord.getAccountId(), 
                                                orderDates.getNextOrderCutoffDate(), orderDates.getNextOrderCutoffTime());
                                            remCutoffCal.set(Calendar.HOUR_OF_DAY, 0);
                                            remCutoffCal.set(Calendar.MINUTE, 0);
                                            remCutoffCal.set(Calendar.SECOND, 0);
                                            remCutoffCal.set(Calendar.MILLISECOND, 0);

                                            GregorianCalendar cutoffCal = new GregorianCalendar();
                                            GregorianCalendar cutoffTimeCal = new GregorianCalendar();
                                            cutoffTimeCal.setTime(scheduleProc.getCutoffTime());
                                            cutoffCal.setTime(orderDates.getNextOrderCutoffDate());
                                            cutoffCal.set(Calendar.HOUR_OF_DAY, cutoffTimeCal.get(Calendar.HOUR_OF_DAY));
                                            cutoffCal.set(Calendar.MINUTE, cutoffTimeCal.get(Calendar.MINUTE));
                                            cutoffCal.set(Calendar.SECOND, cutoffTimeCal.get(Calendar.SECOND));
                                            cutoffCal.set(Calendar.MILLISECOND, cutoffTimeCal.get(Calendar.MILLISECOND));


                                            if (remCutoffCal.getTime().before(runDate) && cutoffCal.getTime().after(runDate)) {
    
                                                log.info("### remCutoff: " + remCutoffCal.getTime());
                                                log.info("###   runDate: " + runDate);
                                                log.info("###    cutoff: " + cutoffCal.getTime());

                                                ReportRecord record = new ReportRecord();
                                                
                                                record.setCutoffTime(sdfTime.format(scheduleProc.getCutoffTime()));
                                                record.setCutoffDate(extractCutoffDay(sch));
                                                record.setScheduleId(sch.getSchedule().getScheduleId());
                                                record.setScheduleDesc(sch.getSchedule().getShortDesc());
                                                record.setScheduleRuleCd(sch.getSchedule().getScheduleRuleCd());
                                                record.setCycle(sch.getSchedule().getCycle());
                                                record.setDistributorId(new Integer(siteRecord.getDistributorId()));
                                                if (sch.getSchedule().getEffDate() != null) {
                                                    record.setScheduleEffDate(sdfDate.format(sch.getSchedule().getEffDate()));
                                                }
                                                if (sch.getSchedule().getExpDate() != null) {
                                                    record.setScheduleExpDate(sdfDate.format(sch.getSchedule().getExpDate()));
                                                }
                                                //record.setCutoffDay(sdfDate.format(runDate));
                                                
                                                record.setCutoffDay(sdfDate.format(cutoffCal.getTime()));
                                                record.setOrderDeliveryDate(sdfDate.format(orderDates.getNextOrderDeliveryDate()));
                                                record.setAccountCutoffDay(new Integer(siteRecord.getCuttofDays()));
                                                record.setAccountId(new Integer(siteRecord.getAccountId()));
                                                record.setSiteId(new Integer(siteRecord.getSiteId()));
                                                record.setZipCode(siteRecord.getPostalCode());
                                                record.setCatalogId(new Integer(siteRecord.getCatalogId()));
                                                record.setSiteDeliverySchedule(siteDelivery == null ? null : siteDelivery.getSiteScheduleType());
                                                record.setCutoffRemDate(sdfDate.format(remCutoffCal.getTime()));
                                                record.setOnHandQty(new Integer(0));
                                                record.setEmailDays(siteRecord.getEmailDays());
                                                reportRecords.add(record.toList());
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

    private SiteDeliveryScheduleView ScheduleRowData2SiteDeliveryScheduleView(ScheduleRowData scheduleRowData) {
        SiteDeliveryScheduleView siteDelivery = null;       
        String deliveryDates = scheduleRowData.getDeliverySchedule();

        if (Utility.isSet(deliveryDates)) {
            siteDelivery = SiteDeliveryScheduleView.createValue();
            siteDelivery.setSiteId(scheduleRowData.getSiteId());
            siteDelivery.setPostalCode(scheduleRowData.getPostalCode());
            siteDelivery.setCity(scheduleRowData.getCity());
            siteDelivery.setState(scheduleRowData.getState());
            siteDelivery.setCounty(scheduleRowData.getCounty());

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
        return siteDelivery;
    }

    private ArrayList<ScheduleRowData> getScheduleRawRecords(Connection conn, int storeId, int accountId) throws Exception {
        ArrayList<ScheduleRowData> res = new ArrayList<ScheduleRowData>();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT \n");
            sql.append("account.BUS_ENTITY_ID AS ACCOUNT_ID, \n");
            sql.append("siteAccAssoc.BUS_ENTITY1_ID AS SITE_ID, \n");
            sql.append("catalog.CATALOG_ID, \n");
            sql.append("distr.BUS_ENTITY_ID AS DISTRIBUTOR_ID, \n");
            sql.append("addr.POSTAL_CODE, \n");
            sql.append("addr.COUNTRY_CD, \n");
            sql.append("addr.COUNTY_CD, \n");
            sql.append("addr.STATE_PROVINCE_CD, \n");
            sql.append("addr.CITY, \n");
            sql.append("TO_NUMBER(NVL(propCutoff.CLW_VALUE,0)) AS CUTOFF_DAYS, \n");
            sql.append("propDelivery.CLW_VALUE AS SITE_DELIVERY, \n");
            sql.append("TO_NUMBER(propMissingNotif.CLW_VALUE) AS EMAIL_DAYS \n");
        sql.append("FROM \n");
            sql.append("CLW_BUS_ENTITY account \n");
                sql.append("INNER JOIN CLW_BUS_ENTITY_ASSOC siteAccAssoc ON \n");
                    sql.append("account.BUS_ENTITY_ID = siteAccAssoc.BUS_ENTITY2_ID \n");
                    sql.append("AND account.BUS_ENTITY_STATUS_CD = '").append(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE).append("' \n");
                    sql.append("AND siteAccAssoc.BUS_ENTITY_ASSOC_CD = '").append(RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT).append("' \n");
                sql.append("INNER JOIN CLW_ADDRESS addr ON \n");
                    sql.append("addr.BUS_ENTITY_ID = siteAccAssoc.BUS_ENTITY1_ID \n");
                    sql.append("AND addr.ADDRESS_TYPE_CD = '").append(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING).append("' \n");
                    sql.append("AND addr.ADDRESS_STATUS_CD = '").append(RefCodeNames.ADDRESS_STATUS_CD.ACTIVE).append("' \n");
                sql.append("INNER JOIN CLW_CATALOG_ASSOC catSiteAssoc ON \n");
                    sql.append("catSiteAssoc.BUS_ENTITY_ID = addr.BUS_ENTITY_ID \n");
                    sql.append("AND catSiteAssoc.CATALOG_ASSOC_CD = '").append(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE).append("' \n");
                sql.append("INNER JOIN CLW_CATALOG catalog ON \n");
                    sql.append("catalog.CATALOG_ID = catSiteAssoc.CATALOG_ID \n");
                    sql.append("AND catalog.CATALOG_TYPE_CD = '").append(RefCodeNames.CATALOG_TYPE_CD.SHOPPING).append("' \n");
                sql.append("INNER JOIN CLW_CATALOG_ASSOC catDistAssoc ON \n");
                    sql.append("catDistAssoc.CATALOG_ID = catalog.CATALOG_ID \n");
                    sql.append("AND catDistAssoc.CATALOG_ASSOC_CD = '").append(RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR).append("' \n");
                sql.append("INNER JOIN CLW_BUS_ENTITY distr ON \n");
                    sql.append("distr.BUS_ENTITY_ID = catDistAssoc.BUS_ENTITY_ID \n");
                    sql.append("AND distr.BUS_ENTITY_TYPE_CD='").append(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR).append("' \n");
                    sql.append("AND distr.BUS_ENTITY_STATUS_CD='").append(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE).append("' \n");
                sql.append("INNER JOIN CLW_PROPERTY propMissingNotif ON \n");
                    sql.append("account.BUS_ENTITY_ID = propMissingNotif.BUS_ENTITY_ID \n");
                    sql.append("AND propMissingNotif.SHORT_DESC='").append(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_MISSING_NOTIFICATION).append("' \n");
                    sql.append("AND propMissingNotif.CLW_VALUE IS NOT NULL \n");
                    sql.append("AND propMissingNotif.PROPERTY_TYPE_CD='").append(RefCodeNames.PROPERTY_TYPE_CD.EXTRA).append("' \n");
                    sql.append("AND propMissingNotif.PROPERTY_STATUS_CD='").append(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE).append("' \n");
                sql.append("LEFT JOIN CLW_PROPERTY propCutoff ON \n");
                    sql.append("account.BUS_ENTITY_ID = propCutoff.BUS_ENTITY_ID \n");
                    sql.append("AND propCutoff.SHORT_DESC='").append(RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS).append("' \n");
                    sql.append("AND propCutoff.PROPERTY_TYPE_CD='").append(RefCodeNames.PROPERTY_TYPE_CD.EXTRA).append("' \n");
                    sql.append("AND propCutoff.PROPERTY_STATUS_CD='").append(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE).append("' \n");
                sql.append("LEFT JOIN CLW_PROPERTY propDelivery ON \n");
                    sql.append("siteAccAssoc.BUS_ENTITY1_ID = propDelivery.BUS_ENTITY_ID \n");
                    sql.append("AND propDelivery.SHORT_DESC='").append(RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE).append("' \n");
                    sql.append("AND propDelivery.PROPERTY_TYPE_CD='").append(RefCodeNames.PROPERTY_TYPE_CD.DELIVERY_SCHEDULE).append("' \n");
                    sql.append("AND propDelivery.PROPERTY_STATUS_CD='").append(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE).append("' \n");
        sql.append("WHERE \n");
            sql.append("siteAccAssoc.BUS_ENTITY1_ID IN \n");
            sql.append("( \n");
                sql.append("SELECT \n");
                    sql.append("site.BUS_ENTITY_ID \n");
                sql.append("FROM \n");
                    sql.append("CLW_PROPERTY prop_inv, \n");
                    sql.append("CLW_PROPERTY prop_inv_type, \n");
                    sql.append("CLW_BUS_ENTITY site \n");
                sql.append("WHERE \n");
                    sql.append("prop_inv.PROPERTY_TYPE_CD = '").append(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING).append("' \n");
                    sql.append("AND prop_inv.CLW_VALUE = 'on' \n");
                    sql.append("AND prop_inv_type.PROPERTY_TYPE_CD = '").append(RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_SHOPPING_TYPE).append("' \n");
                    sql.append("AND prop_inv_type.CLW_VALUE = 'on' \n");
                    sql.append("AND prop_inv.BUS_ENTITY_ID = prop_inv_type.BUS_ENTITY_ID \n");
                    sql.append("AND site.BUS_ENTITY_ID = prop_inv.BUS_ENTITY_ID \n");
                    sql.append("AND site.BUS_ENTITY_TYPE_CD='").append(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE).append("' \n");
                    sql.append("AND site.BUS_ENTITY_STATUS_CD='").append(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE).append("' \n");
            sql.append(") \n");
            sql.append("AND distr.BUS_ENTITY_ID IN \n");
            sql.append("( \n");
                sql.append("SELECT DISTINCT CLW_SCHEDULE.BUS_ENTITY_ID FROM CLW_SCHEDULE \n");
                sql.append("WHERE CLW_SCHEDULE.SCHEDULE_TYPE_CD='").append(RefCodeNames.SCHEDULE_TYPE_CD.DELIVERY).append("' \n");
            sql.append(") \n");

        log.info("### sql: " + sql.toString());

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql.toString());
        while (rs.next()) {
            ScheduleRowData ScheduleRowData = new ScheduleRowData();

            ScheduleRowData.setAccountId(rs.getInt(1));
            ScheduleRowData.setSiteId(rs.getInt(2));
            ScheduleRowData.setCatalogId(rs.getInt(3));
            ScheduleRowData.setDistributorId(rs.getInt(4));
            ScheduleRowData.setPostalCode(rs.getString(5));
            ScheduleRowData.setCountry(rs.getString(6));
            ScheduleRowData.setCounty(rs.getString(7));
            ScheduleRowData.setState(rs.getString(8));
            ScheduleRowData.setCity(rs.getString(9));
            ScheduleRowData.setCuttofDays(rs.getInt(10));
            ScheduleRowData.setDeliverySchedule(rs.getString(11));
            ScheduleRowData.setEmailDays(rs.getInt(12));

            res.add(ScheduleRowData);
        }
        return res;
    }

    private HashMap<String, HashMap<Integer, ArrayList<ScheduleRowData> > > groupByScheduleKeyAndCutoffDays(ArrayList<ScheduleRowData> records) {
        HashMap<String, HashMap<Integer, ArrayList<ScheduleRowData> > > res = new HashMap<String, HashMap<Integer, ArrayList<ScheduleRowData> > >();
        for (ScheduleRowData record : records) {
            String key = getScheduleKey(record.getDistributorId(), record.getPostalCode());
            if (!res.containsKey(key)) {
                HashMap<Integer, ArrayList<ScheduleRowData> > cutoffDaysMap = new HashMap<Integer, ArrayList<ScheduleRowData> >();
                ArrayList<ScheduleRowData> recList = new ArrayList<ScheduleRowData>();
                recList.add(record);
                cutoffDaysMap.put(record.getCuttofDays(), recList);
                res.put(key, cutoffDaysMap);
            } else {
                HashMap<Integer, ArrayList<ScheduleRowData> > cutoffDaysMap = res.get(key);
                if (!cutoffDaysMap.containsKey(record.getCuttofDays())) {
                    ArrayList<ScheduleRowData> recList = new ArrayList<ScheduleRowData>();
                    recList.add(record);
                    cutoffDaysMap.put(record.getCuttofDays(), recList);
                } else {
                    ArrayList<ScheduleRowData> recList = cutoffDaysMap.get(record.getCuttofDays());
                    recList.add(record);
                }
            }
        }
        return res;
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

    public class ScheduleRowData implements java.io.Serializable {
        private int _accountId;
        private int _siteId;
        private int _catalogId;
        private int _distributorId;
        private String _postalCode;
        private String _country;
        private String _county;
        private String _state;
        private String _city;
        private int _cuttofDays;
        private String _deliverySchedule;
        private int _emailDays;

        public int getAccountId() {
            return _accountId;
        }
        public void setAccountId(int accountId) {
            _accountId = accountId;
        }
        public int getSiteId() {
            return _siteId;
        }
        public void setSiteId(int siteId) {
            _siteId = siteId;
        }
        public int getCatalogId() {
            return _catalogId;
        }
        public void setCatalogId(int catalogId) {
            _catalogId = catalogId;
        }
        public int getDistributorId() {
            return _distributorId;
        }
        public void setDistributorId(int distributorId) {
            _distributorId = distributorId;
        }
        public String getPostalCode() {
            return _postalCode;
        }
        public void setPostalCode(String postalCode) {
            _postalCode = postalCode;
        }
        public String getCountry() {
            return _country;
        }
        public void setCountry(String country) {
            _country = country;
        }
        public String getCounty() {
            return _county;
        }
        public void setCounty(String county) {
            _county = county;
        }
        public String getState() {
            return _state;
        }
        public void setState(String state) {
            _state = state;
        }
        public String getCity() {
            return _city;
        }
        public void setCity(String city) {
            _city = city;
        }
        public int getCuttofDays() {
            return _cuttofDays;
        }
        public void setCuttofDays(int cuttofDays) {
            _cuttofDays = cuttofDays;
        }
        public String getDeliverySchedule() {
            return _deliverySchedule;
        }
        public void setDeliverySchedule(String deliverySchedule) {
            _deliverySchedule = deliverySchedule;
        }
        public int getEmailDays() {
            return _emailDays;
        }
        public void setEmailDays(int emailDays) {
            _emailDays = emailDays;
        }
    }

    public class ReportRecord implements Record, java.io.Serializable {
        private String _cutoffTime;
        private String _cutoffDate;
        private Integer _scheduleId;
        private String _scheduleDesc;
        private String _scheduleRuleCd;
        private Integer _cycle;
        private Integer _distributorId;
        private String _scheduleEffDate;
        private String _scheduleExpDate;
        private String _cutoffDay;
        private String _orderDeliveryDate;
        private Integer _accountCutoffDay;
        private Integer _accountId;
        private Integer _siteId;
        private String _zipCode;
        private Integer _catalogId;
        private String _siteDeliverySchedule;
        private String _cutoffRemDate;
        private Integer _onHandQty;
        private Integer _emailDays;

        public List toList() {
            ArrayList<Serializable> list = new ArrayList<Serializable>();

            list.add(_accountId);
            list.add(_siteId);
            list.add(_catalogId);
            list.add(_distributorId);
            list.add(_zipCode);
            list.add(_scheduleId);
            list.add(_scheduleDesc);
            list.add(_scheduleRuleCd);
            list.add(_cycle);
            list.add(_scheduleEffDate);
            list.add(_scheduleExpDate);
            list.add(_cutoffDay);
            list.add(_accountCutoffDay);
            list.add(_siteDeliverySchedule);
            list.add(_cutoffDate);
            list.add(_cutoffTime);
            list.add(_orderDeliveryDate);
            list.add(_cutoffRemDate);
            list.add(_onHandQty);
            list.add(_emailDays);

            return list;
        }

        public String getSiteDeliverySchedule() {
            return _siteDeliverySchedule;
        }
        public void setSiteDeliverySchedule(String siteDeliverySchedule) {
            _siteDeliverySchedule = siteDeliverySchedule;
        }
        public String getCutoffTime() {
            return _cutoffTime;
        }
        public void setCutoffTime(String cutoffTime) {
            _cutoffTime = cutoffTime;
        }
        public void setCutoffDate(String cutoffDate) {
            _cutoffDate = cutoffDate;
        }
        public String getCutoffDate() {
            return _cutoffDate;
        }
        public void setScheduleId(Integer scheduleId) {
            _scheduleId = scheduleId;
        }
        public void setScheduleDesc(String scheduleDesc) {
            _scheduleDesc = scheduleDesc;
        }
        public void setScheduleRuleCd(String scheduleRuleCd) {
            _scheduleRuleCd = scheduleRuleCd;
        }
        public void setCycle(Integer cycle) {
            _cycle = cycle;
        }
        public void setDistributorId(Integer distributorId) {
            _distributorId = distributorId;
        }
        public void setScheduleEffDate(String scheduleEffDate) {
            _scheduleEffDate = scheduleEffDate;
        }
        public void setScheduleExpDate(String scheduleExpDate) {
            _scheduleExpDate = scheduleExpDate;
        }
        public void setCutoffDay(String cutoffDay) {
            _cutoffDay = cutoffDay;
        }
        public Integer getScheduleId() {
            return _scheduleId;
        }
        public String getScheduleDesc() {
            return _scheduleDesc;
        }
        public Integer getCycle() {
            return _cycle;
        }
        public String getScheduleRuleCd() {
            return _scheduleRuleCd;
        }
        public Integer getDistributorId() {
            return _distributorId;
        }
        public String getScheduleEffDate() {
            return _scheduleEffDate;
        }
        public String getScheduleExpDate() {
            return _scheduleExpDate;
        }
        public String getCutoffDay() {
            return _cutoffDay;
        }
        public void setOrderDeliveryDate(String orderDeliveryDate) {
            _orderDeliveryDate = orderDeliveryDate;
        }
        public String getOrderDeliveryDate() {
            return _orderDeliveryDate;
        }
        public void setAccountCutoffDay(Integer accountCutoffDay) {
            _accountCutoffDay = accountCutoffDay;
        }
        public Integer getAccountCutoffDay() {
            return _accountCutoffDay;
        }
        public Integer getAccountId() {
            return _accountId;
        }
        public void setAccountId(Integer accountId) {
            _accountId = accountId;
        }
        public void setSiteId(Integer sitetId) {
            _siteId = sitetId;
        }
        public void setZipCode(String zipCode) {
            _zipCode = zipCode;
        }
        public Integer getSiteId() {
            return _siteId;
        }
        public void setCatalogId(Integer catalogId) {
            _catalogId = catalogId;
        }
        public Integer getCatalogId() {
            return _catalogId;
        }
        public String getZipCode() {
            return _zipCode;
        }
        public String getCutoffRemDate() {
            return _cutoffRemDate;
        }
        public void setCutoffRemDate(String cutoffRemDate) {
            _cutoffRemDate = cutoffRemDate;
        }
        public Integer getOnHandQty() {
            return _onHandQty;
        }
        public void setOnHandQty(Integer onHandQty) {
            _onHandQty = onHandQty;
        }
        public Integer getEmailDays() {
            return _emailDays;
        }
        public void setEmailDays(Integer emailDays) {
            _emailDays = emailDays;
        }
    }
}
