package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.ReportShowProperty;

import java.util.*;
import java.sql.*;
import java.text.*;
import java.util.Date;

public class InvoiceTotalCountReport  implements GenericReportMulti, ReportShowProperty {

    private static final String PATTERN = "MM/dd/yyyy HH:mm";
    
    public com.cleanwise.service.api.value.GenericReportResultViewVector
        process(com.cleanwise.service.api.util.ConnectionContainer pCons,
                com.cleanwise.service.api.value.GenericReportData pReportData,
                java.util.Map pParams) throws Exception {
        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();

        String storeIdS =  (String) ReportingUtils.getParam(pParams, "STORE");
        int storeId = 0;
        try {
            storeId = Integer.parseInt(storeIdS, 10);
        } catch (Exception e) {
            String mess = "^clw^Invalid store " + storeIdS + "^clw^";
            throw new Exception(mess);
        }

        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE");
        String dateFmt = (String) ReportingUtils.getParam(pParams, "DATE_FMT");

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }

        if (Utility.isSet(begDateS) && !ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if (Utility.isSet(endDateS) && !ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }

        // get store name
        String storeName = "";
        String sql = "select short_desc from clw_bus_entity where bus_entity_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, storeId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            storeName = rs.getString(1);
        }
        rs.close();
        stmt.close();

        if (!Utility.isSet(storeName)) {
            String mess = "^clw^No Store Found^clw^";
            throw new Exception(mess);
        }

        String dateWhere = getDateWhere(begDateS, endDateS, dateFmt);

        String status = (String) ReportingUtils.getParam(pParams, "INVOICE_STATUS_SELECT");
        String statusCond = "";
        if (Utility.isSet(status)) {
            statusCond = "upper(invoice_status_cd) = '" + status + "' and \n";
        }

     /*   ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserAccessDescription userDesc =
            repUtil.getUserAccessDescription(pParams,con);
            */
        /*String accessControlCond = null;
        if (userDesc.hasStoreAccess()) {
          accessControlCond += " and o.store_id in (" + userDesc.getAuthorizedSql() + ") ";
  	    }else if(userDesc.hasAccountAccess()) {
          accessControlCond =" and o.account_id in ("+
                    userDesc.getAuthorizedSql()+") ";
        } else if(userDesc.hasSiteAccess()) {
          accessControlCond = " and o.site_id in ("+
                    userDesc.getAuthorizedSql()+") ";
        } */

        sql = "Select \n" +
                "invoice_status_cd,\n" +
                "count(invoice_status_cd) as count \n" +
                "FROM CLW_INVOICE_DIST \n" +
               "WHERE " +
                "INVOICE_DATE >= TO_DATE(?, '" + dateFmt + "') AND \n" +
                "INVOICE_DATE <= TO_DATE(?, '" + dateFmt + "') AND \n" +
                statusCond +
                "STORE_ID = ? \n" +
               "group by invoice_status_cd \n"+
               "order by invoice_status_cd";

        stmt = con.prepareStatement(sql);
        stmt.setString(1, begDateS);
        stmt.setString(2, endDateS);
        stmt.setInt(3, storeId);
        rs = stmt.executeQuery();
        LinkedList invoiceTotCountLL = new LinkedList();
        while(rs.next()){
          InvoiceTotalCount invd = new InvoiceTotalCount();
          invoiceTotCountLL.add(invd);
          invd.statusCd = rs.getString("invoice_status_cd");
          invd.totalCount = rs.getInt("count");
          invd.beginDateS = begDateS;
          invd.endDateS = endDateS;
          invd.storeName = storeName;
        }
        rs.close();
        stmt.close();


        GenericReportResultView result = GenericReportResultView.createValue();
        result.setTable(new ArrayList());
        Iterator it = invoiceTotCountLL.iterator();
        while(it.hasNext()) {
            result.getTable().add(((InvoiceTotalCount) it.next()).toList());
        }
        GenericReportColumnViewVector header = getInvoiceTotalCountReportHeader();
        result.setColumnCount(header.size());
        result.setHeader(header);
        result.setName("Invoice Total Count Report");
        resultV.add(result);

        return resultV;
    }


    private GenericReportColumnViewVector getInvoiceTotalCountReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Store Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Begin Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","End Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Status",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Count",0,20,"NUMBER"));
        return header;
    }

    private class InvoiceTotalCount {
        String storeName;
        String statusCd;
        String beginDateS;
        String endDateS;
        int totalCount;

        public ArrayList toList(){
            ArrayList list = new ArrayList();
            list.add(storeName);
            list.add(beginDateS);
            list.add(endDateS);
            list.add(statusCd);
            list.add(totalCount);
            return list;
        }

    }


    private final static int ONE_SECOND = 1000;
    private final static int ONE_MINUTE = ONE_SECOND * 60;
    private final static int ONE_HOUR = ONE_MINUTE * 60;

    public static Date addDays(Date date, int nDays)
    {
    	if (date == null)
        throw new IllegalArgumentException("Date is null in ManufacturerItemSummaryReport.addDays()");
      // Create a calendar based on given date
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      // Add/subtract the specified number of days
      calendar.add(Calendar.DAY_OF_MONTH, nDays);
      return calendar.getTime();
    }
    public final static Date convertDate(Date source,
            TimeZone tzSource, TimeZone tzTarget) {
        long timeInMillis = source.getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(source);
        int millis = c.get(Calendar.HOUR_OF_DAY) * ONE_HOUR
                + c.get(Calendar.MINUTE) * ONE_MINUTE;
        long delta1 = tzSource.getOffset(c.get(Calendar.ERA), c
                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
                millis);
        int delta2 = tzTarget.getOffset(c.get(Calendar.ERA), c
                .get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH), c.get(Calendar.DAY_OF_WEEK),
                millis);
        return new Date(timeInMillis + (delta2 - delta1));
    }

    public final static String getDateWhere(String begDateS, String endDateS,
            String dateFmt)
            throws Exception {
        StringBuffer result = new StringBuffer();
        if (!Utility.isSet(begDateS) && !Utility.isSet(endDateS)) return result.toString();

        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(PATTERN);

        String startDateTargetS = null;
        if (Utility.isSet(begDateS)) {
            Date startDate = sdf.parse(begDateS);
            startDateTargetS = sdfTarget.format(startDate);
        }
        String endDateTargetS = null;
        if (Utility.isSet(endDateS)) {
            Date endDate = sdf.parse(endDateS);
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);
            endDateTargetS = sdfTarget.format(endDate);
        }

         if (Utility.isSet(startDateTargetS) && Utility.isSet(endDateTargetS)) {
            result.append(" BETWEEN TO_DATE('").append(startDateTargetS).
                   append("','MM/dd/yyyy HH24:MI') AND TO_DATE('").append(endDateTargetS).
                   append("','MM/dd/yyyy HH24:MI') \n");
        } else {
            if (Utility.isSet(startDateTargetS)) {
                result.append(">= TO_DATE('").append(startDateTargetS).
                       append("','MM/dd/yyyy HH24:MI')\n");
            } else {
                result.append("<= TO_DATE('").append(endDateTargetS).
                       append("','MM/dd/yyyy HH24:MI')\n");
            }
        }

        return result.toString();
    }

    public boolean showOnlyDownloadReportButton() {
       return true;
    }
   }
