/*
 * ReSaleOrder.java
 *
 * Created on October 23, 2003, 2:23 PM
 */

package com.cleanwise.service.api.reporting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;

/**
 * @author bstevens
 */
public class OrderApprovalReport implements GenericReportMulti {
    private static final String PATTERN = "MM/dd/yyyy";


    protected boolean mDisplaySalesTaxInfo;
    protected boolean mDisplayDistInfo;
    protected HashMap storeMap = new HashMap();
    protected HashMap masterItemCategoryMap = new HashMap();
    Connection con;
    protected boolean multiAccounts = false;
    protected boolean includeDist = false;
    protected HashMap accountMap = new HashMap();
    protected boolean showPrice;
    protected boolean displayPONum = false;
    protected boolean includeState = false;

    private String reportName = "Order Approval Report";

    public GenericReportResultViewVector process(ConnectionContainer pCons,
                                                 GenericReportData pReportData,
                                                 Map pParams)
            throws Exception {
        con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV =
                new GenericReportResultViewVector();
        ArrayList orderTotals = new ArrayList();
        String begDateS = (String) pParams.get("BEG_DATE");
        String endDateS = (String) pParams.get("END_DATE");
        String dateFmt = (String) pParams.get("DATE_FMT");

        if (null == dateFmt) {
            dateFmt = "MM/dd/yyyy";
        }

        if (!ReportingUtils.isValidDate(begDateS, dateFmt)) {
            String mess = "^clw^\"" + begDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        if (!ReportingUtils.isValidDate(endDateS, dateFmt)) {
            String mess = "^clw^\"" + endDateS + "\" is not a valid date of the form: " + dateFmt + "^clw^";
            throw new Exception(mess);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        Date begDateD = sdf.parse(begDateS);
        Date endDateD = sdf.parse(endDateS);
        if (endDateD != null && begDateD != null && endDateD.compareTo(begDateD) < 0) {
            String mess = "^clw^End date: \"" + endDateS
                    + "\" cannot be before start date: \"" + begDateS
                    + "\"^clw^";
            throw new Exception(mess);
        }

        String dateWhere = getDateWhere(begDateS, endDateS, dateFmt);
        String dateRangeStr = begDateS + " - " + endDateS;
        String siteIdS = (String)ReportingUtils.getParam(pParams, "LOCATE_SITE_MULTI");

        if (siteIdS != null) {
            StringTokenizer token = new StringTokenizer(siteIdS, ",");
			int ct = 0;
            while (token.hasMoreTokens()) {
				ct ++;
                String siteS = token.nextToken();
                if (siteS.trim().length() > 0)
                    try {
                        Integer.parseInt(siteS.trim());
                    } catch (Exception exc) {
                        String mess = "^clw^" + siteS + " is not a valid site identifier ^clw^";
                        throw new Exception(mess);
                    }

            }
			if(ct >= 1000){
                    String mess = "^clw^ Too many sites selected.  To select all sites leave Sites parameter blank ^clw^";
                    throw new Exception(mess);
			}
        }

        ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserAccessDescription userDesc = repUtil.getUserAccessDescription(pParams, con);

        String orderTotalSql =
                    "select o.order_id, \n" +
                            "site_id,order_site_name, \n" +
                            "oa.city as city,\n" +
                            "oa.state_province_cd as state,\n" +
                            "oa.postal_code as postal_code, \n" +
                            "order_num as web_order_number,\n" +
                            "o.original_order_date as order_date,\n" +
                            "prop.add_by as approver,\n" +
                            "o.order_status_cd,\n" +
                            "prop.add_date as final_order_status_date,\n" +
                            "o.comments\n" +
                     "from clw_order o, clw_order_address oa,\n" +
                            "(select order_id, count(order_property_id) cop from clw_order_property \n" +
                            "   where message_key = 'pipeline.message.fwdForApproval'\n" +
                            "   group by order_id) pappr,\n" +
                            "clw_order_property prop \n" +
                     "where " +
                            "oa.order_id = o.order_id and oa.address_type_cd = 'SHIPPING'\n" +
                            " and pappr.order_id = o.order_id\n" +
                            " and prop.order_id(+) = o.order_id and prop.short_desc(+) = 'Order Status Update' \n" +
                            dateWhere;


            if (Utility.isSet(siteIdS)) {
                orderTotalSql += " AND o.site_id in (" + siteIdS + ") ";
            } else {
                if (!userDesc.hasAccessToAll()) {
                    if (userDesc.hasStoreAccess()) {
                        orderTotalSql += " and o.store_id in (" + userDesc.getAuthorizedSql() + ") ";
                    } else if (userDesc.hasAccountAccess()) {
                        orderTotalSql += " and o.account_id in (" + userDesc.getAuthorizedSql() + ") ";
                    } else if (userDesc.hasSiteAccess()) {
                        orderTotalSql += " and o.site_id in (" + userDesc.getAuthorizedSql() + ") ";
                    }
                }
            }
            orderTotalSql += " order by order_site_name";
        

            PreparedStatement stmt = con.prepareStatement(orderTotalSql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                aRecord record = new aRecord();
                record.orderId = rs.getInt("order_id");
                record.siteName = rs.getString("order_site_name");
                record.city = rs.getString("city");
                record.state = rs.getString("state");
                record.postalCode = rs.getString("postal_code");
                record.confirmNum = rs.getString("web_order_number");
                Date orderDate = rs.getDate("order_date");
                String orderDateS = sdf.format(orderDate);
                record.orderDate = orderDateS;
                record.approver = rs.getString("approver");
                record.orderStatus = rs.getString("order_status_cd");
                Date finalOrderDate = rs.getDate("final_order_status_date");
                if (finalOrderDate != null) {
                    String finalOrderDateS = sdf.format(finalOrderDate);
                    record.finalOrderStatusDate = finalOrderDateS;
                }
                record.comments = rs.getString("comments");

                orderTotals.add(record);
            }
            rs.close();
            stmt.close();
            GenericReportResultView result =
                    GenericReportResultView.createValue();
            result.setTable(new ArrayList());

            // get comments from order property
            String sql = "select clw_value, add_by \n" +
                    "from clw_order_property where " +
                    "short_desc = 'CUSTOMER_CART_COMMENTS' and order_id = ? " ;
            Iterator it = orderTotals.iterator();
            stmt = con.prepareStatement(sql);
            while (it.hasNext()) {
                aRecord rec = (aRecord) it.next();
                int order_id = rec.orderId;
                stmt.setInt(1, order_id);
                rs = stmt.executeQuery();
                StringBuffer comments = new StringBuffer();
                while (rs.next()) {
                    String comment = rs.getString(1);
                    String author = rs.getString(2);
                    if (Utility.isSet(comment)) {
                        comments.append(comment);
                        if (Utility.isSet(author)) {
                            comments.append(" (").append(author).append("); ");    
                        }
                    }
                    rec.comments = comments.toString();
                }
                rs.close();
                result.getTable().add(rec.toList());
            }
            stmt.close();

            GenericReportColumnViewVector header = getReportHeader();
            result.setColumnCount(header.size());
            result.setHeader(header);
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            result.setName(reportName);
            result.setTitle(getReportTitle(reportName, dateRangeStr));
            result.setFreezePositionRow(4);
        
            resultV.add(result);

        return resultV;
    }






    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site Name", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "City", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "State", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Postal Code", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Confirmation #", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Date", 0, 255, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Approver", 0, 255, "VARCHAR2", "12", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Order Status", 0, 255, "VARCHAR2", "20", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Final Order Status Date", 0, 0, "VARCHAR2", "10", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Comments", 0, 255, "VARCHAR2", "20", false));
        return header;
    }

    protected GenericReportColumnViewVector getReportTitle(String pReportName, String pDateRange) {

        GenericReportColumnViewVector title = new GenericReportColumnViewVector();

        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Report Name: " + pReportName, 0, 255, "VARCHAR2"));
      	title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Date Range: " + pDateRange, 0, 255, "VARCHAR2"));
        return title;
    }


    protected class aRecord {
        int orderId;
        String siteName;
        String city;
        String state;
        String postalCode;
        String confirmNum;
        String orderDate;
        String approver;
        String orderStatus;
        String finalOrderStatusDate;
        String comments;

        public ArrayList toList() {
              ArrayList list = new ArrayList();
              list.add(siteName);
              list.add(city);
              list.add(state);
              list.add(postalCode);
              list.add(confirmNum);
              list.add(orderDate);
              list.add(approver);
              list.add(orderStatus);
              list.add(finalOrderStatusDate);
              list.add(comments);

              return list;
          }
      }



    public final static String getDateWhere(String begDateS, String endDateS,
            String dateFmt)
            throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
        java.util.Date startDate = sdf.parse(begDateS);
        java.util.Date endDate = sdf.parse(endDateS);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(PATTERN);
        String startDateTargetS = sdfTarget.format(startDate);
        String endDateTargetS = sdfTarget.format(endDate);
        return " and o.original_order_date "
                + " BETWEEN TO_DATE('"
                + startDateTargetS
                + "','MM/dd/yyyy') AND TO_DATE('"
                + endDateTargetS
                + "','MM/dd/yyyy') ";
    }



}
