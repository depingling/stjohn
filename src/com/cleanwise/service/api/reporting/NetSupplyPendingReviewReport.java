package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Title:        NetSupplyPendingReviewReport
 * Description:  A list of all NetSupply orders in a “Pending Review” status
 * Purpose:      This an automated report that will be sent every morning via email to a designated recipient
 * Company:      CleanWise, Inc.
 * Date:         23.04.2009
 * Time:         11:38:11
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class NetSupplyPendingReviewReport implements GenericReport {

    private static final Logger log = Logger.getLogger(NetSupplyPendingReviewReport.class);

    protected static Date INITIAL_DATE = new GregorianCalendar(1999, 0, 1).getTime();
    protected static Date CURRENT_DATE = new Date();

    protected static final String BEG_DATE_OPT = "BEG_DATE_OPT";
    protected static final String END_DATE_OPT = "END_DATE_OPT";
    protected static final String STORE        = "STORE";
    protected static final String REPORT_NAME  = "REPORT_NAME";

    private static final char NOTE_DELIM = ';';
    private static final char OUTBOUND_PO_DELIM = ',';

    private static final Comparator<aRecord> ORDER_DATE_COMPARE = new Comparator<aRecord>() {
        public int compare(aRecord o1, aRecord o2) {
            Date date1 = o1.getOrderDate() != null ? o1.getOrderDate() : new Date(0);
            Date date2 = o2.getOrderDate() != null ? o2.getOrderDate() : new Date(0);
            return date1.compareTo(date2);
        }
    };

    public static interface REPORT_HEADER {
        public static final String ACCOUNT_NAME    = "Account Name";
        public static final String OUTBOUND_PO_NUM = "Outbound PO #";
        public static final String ORDER_DATE      = "Order Date";
        public static final String WEB_ORDER_NUM   = "Web Order #";
        public static final String SITE_NAME       = "Site Name";
        public static final String SITE_ADDRESS_1  = "Site Address 1";
        public static final String SITE_CITY       = "Site City";
        public static final String SITE_STATE      = "Site State";
        public static final String SITE_ZIP        = "Site Zip";
        public static final String PLACED_BY       = "Placed By";
        public static final String ORDER_STATUS    = "Order Status";
        public static final String ORDER_NOTE      = "Order Note ";
    }

    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        log.info("process => BEGIN");
        Connection con = pCons.getMainConnection();

        String begDateStr = (String) pParams.get(BEG_DATE_OPT);
        String endDateStr = (String) pParams.get(END_DATE_OPT);
        String storeIdStr = (String) pParams.get(STORE);
        String reportName = "NetSupply Pending Review Orders";

        try {
            Integer.parseInt(storeIdStr);
        } catch (NumberFormatException e) {
            log.info(e.getMessage(), e);
            String mess = "^clw^Store identifier is not a valid value^clw^";
            throw new Exception(mess);
        }

        if (Utility.isSet(begDateStr)) {
            if (!ReportingUtils.isValidDate(begDateStr)) {
                String mess = "^clw^\"" + begDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
                throw new Exception(mess);
            }
        } else {
            begDateStr = ReportingUtils.format(INITIAL_DATE);
        }

        if (Utility.isSet(endDateStr)) {
            if (!ReportingUtils.isValidDate(endDateStr)) {
                String mess = "^clw^\"" + endDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
                throw new Exception(mess);
            }
        } else {
            endDateStr = ReportingUtils.format(CURRENT_DATE);
        }

        HashMap<Integer, aRecord> ordersMap = new HashMap<Integer, aRecord>();
        ArrayList<Integer> orderIds;

        {
            String orderSql = "SELECT O.ORDER_ID,A.SHORT_DESC AS ACCOUNT_NAME,NVL(O.REVISED_ORDER_DATE,O.ORIGINAL_ORDER_DATE) AS ORDER_DATE,O.ORDER_NUM,O.USER_FIRST_NAME,O.USER_LAST_NAME \n" +
                    " FROM CLW_ORDER O \n" +
                    "   LEFT JOIN CLW_BUS_ENTITY A ON O.ACCOUNT_ID = A.BUS_ENTITY_ID \n" +
                    "  WHERE O.ORDER_STATUS_CD IN ('" + RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW + "','" + RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW + "')"
                    + getOrderDateCriteria(begDateStr, endDateStr)
                    + " AND O.STORE_ID =" + storeIdStr;


            log.info("process => orderSql:" + orderSql);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(orderSql);

            while (rs.next()) {

                aRecord r = new aRecord();

                int orderId          = rs.getInt("ORDER_ID");
                String accountName   = rs.getString("ACCOUNT_NAME");
                Date orderdate       = rs.getDate("ORDER_DATE");
                String orderNum      = rs.getString("ORDER_NUM");
                String userFirstName = rs.getString("USER_FIRST_NAME");
                String userLastName  = rs.getString("USER_LAST_NAME");

                r.setWebOrderNum(orderNum);
                r.setOrderId(orderId);
                r.setAccountName(accountName);
                r.setOrderDate(orderdate);
                r.setPlacedBy(getUserName(userFirstName, userLastName));
                r.setOrderStatus(RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW);
                ordersMap.put(orderId, r);

            }

            rs.close();
            stmt.close();
        }

        orderIds = new ArrayList<Integer>(ordersMap.keySet());
        if (!orderIds.isEmpty()) {

            {
                String orderAddressSql = "SELECT ORDER_ID,SHORT_DESC AS SITE_NAME,ADDRESS1,CITY,POSTAL_CODE,STATE_PROVINCE_CD \n" +
                        " FROM CLW_ORDER_ADDRESS  \n" +
                        " WHERE ORDER_ID IN (" + Utility.getAsString(orderIds) + ")";

                log.info("process => orderAddressSql:" + orderAddressSql);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(orderAddressSql);
                while (rs.next()) {

                    int orderId         = rs.getInt("ORDER_ID");
                    String siteName     = rs.getString("SITE_NAME");
                    String siteAddress1 = rs.getString("ADDRESS1");
                    String city         = rs.getString("CITY");
                    String zip          = rs.getString("POSTAL_CODE");
                    String state        = rs.getString("STATE_PROVINCE_CD");

                    aRecord r = ordersMap.get(orderId);
                    if (r != null) {
                        r.setSiteName(siteName);
                        r.setSiteAddress1(siteAddress1);
                        r.setSiteCity(city);
                        r.setSiteZip(zip);
                        r.setSiteState(state);
                    }

                }

                rs.close();
                stmt.close();
            }


            {
                /*String poSql = "SELECT ORDER_ID,OUTBOUND_PO_NUM AS ORDER_NOTE FROM CLW_PURCHASE_ORDER  \n" +
                        " WHERE ORDER_ID IN (" + Utility.getAsString(orderIds) + ")";*/

            	String poSql = "SELECT ORDER_ID,REQUEST_PO_NUM FROM CLW_ORDER "+
            	" WHERE ORDER_ID IN (" + Utility.getAsString(orderIds) + ")";
            	
                log.info("process => poSql:" + poSql);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(poSql);
                while (rs.next()) {

                    int orderId          = rs.getInt("ORDER_ID");
                    String outboundPoNum = rs.getString("REQUEST_PO_NUM");

                    aRecord r = ordersMap.get(orderId);
                    if (r != null) {
                        if (Utility.isSet(r.getOutboundPO())) {
                            if (Utility.isSet(outboundPoNum)) {
                                r.setOutboundPO(r.getOutboundPO() + OUTBOUND_PO_DELIM + outboundPoNum);
                            }
                        } else {
                            r.setOutboundPO(outboundPoNum);
                        }
                    }

                }

                rs.close();
                stmt.close();
            }


            /*{
                String orderNoteSql = "SELECT ORDER_ID,CLW_VALUE AS ORDER_NOTE FROM CLW_ORDER_PROPERTY  \n" +
                        " WHERE ORDER_ID IN (" + Utility.getAsString(orderIds) + ") AND ORDER_PROPERTY_TYPE_CD = '" + RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE + "'";

                log.info("process => orderNoteSql:" + orderNoteSql);

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(orderNoteSql);
                while (rs.next()) {

                    int orderId     = rs.getInt("ORDER_ID");
                    String ordeNote = rs.getString("ORDER_NOTE");

                    aRecord r = ordersMap.get(orderId);
                    if (r != null) {
                        if (Utility.isSet(r.getOrderNote())) {
                            if (Utility.isSet(ordeNote)) {
                                r.setOrderNote(r.getOrderNote() + NOTE_DELIM + ordeNote);
                            }
                        } else {
                            r.setOrderNote(ordeNote);
                        }
                    }

                }

                rs.close();
                stmt.close();
            }*/
        }

        List<aRecord> values = new ArrayList<aRecord>(ordersMap.values());
        Collections.sort(values,ORDER_DATE_COMPARE);

        ArrayList<List> table = new ArrayList<List>();
        for (aRecord r : values) {
            table.add(r.toList());
        }

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader();
        result.setTitle(getReportTitle(reportName, begDateStr, endDateStr));
        result.setHeader(header);
        result.setColumnCount(header.size());
        result.setName("Pending Review Orders");
        result.setTable(table);
        result.setFreezePositionRow(result.getTitle().size() + 2);
        log.info("process => END.");

        return result;
    }

    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.ACCOUNT_NAME, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.OUTBOUND_PO_NUM, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", REPORT_HEADER.ORDER_DATE, 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.WEB_ORDER_NUM, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SITE_NAME, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SITE_ADDRESS_1, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SITE_CITY, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SITE_STATE, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.SITE_ZIP, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.PLACED_BY, 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.ORDER_STATUS, 0, 255, "VARCHAR2"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", REPORT_HEADER.ORDER_NOTE, 0, 255, "VARCHAR2"));

        return header;

    }

    protected GenericReportColumnViewVector getReportTitle(String pReportName, String pBegDate, String pEndDate) {

        GenericReportColumnViewVector title = new GenericReportColumnViewVector();

        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Report Name: " + pReportName, 0, 255, "VARCHAR2"));
        if(pBegDate!=null && !pBegDate.equals(ReportingUtils.format(INITIAL_DATE)) && pEndDate!=null){
        	title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Date Range: " + pBegDate + " - " + pEndDate, 0, 255, "VARCHAR2"));
        }
        return title;
    }


    private String getUserName(String firstName, String lastName) {
        String userName = Utility.strNN(firstName);
        if (Utility.isSet(lastName)) {
            if (Utility.isSet(userName)) {
                userName += " " + lastName;
            } else {
                userName = lastName;
            }
        }
        return userName;
    }


    private String getOrderDateCriteria(String begDateStr, String endDateStr) {
        if (Utility.isSet(begDateStr) && Utility.isSet(endDateStr)) {
            return " AND NVL(O.REVISED_ORDER_DATE,O.ORIGINAL_ORDER_DATE) BETWEEN " + ReportingUtils.toSQLDate(begDateStr) + " AND " + ReportingUtils.toSQLDate(endDateStr);
        } else if (Utility.isSet(begDateStr)) {
            return " AND NVL(O.REVISED_ORDER_DATE,O.ORIGINAL_ORDER_DATE) >= " + ReportingUtils.toSQLDate(begDateStr);
        } else if (Utility.isSet(endDateStr)) {
            return " AND NVL(O.REVISED_ORDER_DATE,O.ORIGINAL_ORDER_DATE) <= " + ReportingUtils.toSQLDate(endDateStr);
        } else {
            return "";
        }
    }

    public class aRecord implements Record, java.io.Serializable {

        private String accountName;
        private String outboundPO;
        private Date orderDate;
        private String webOrderNum;
        private String siteName;
        private String siteAddress1;
        private String siteCity;
        private String siteState;
        private String siteZip;
        private String placedBy;
        private String orderStatus;
       // private String orderNote;
        private int orderId;

        public List toList() {

            ArrayList<Object> list = new ArrayList<Object>();

            list.add(this.accountName);
            list.add(this.outboundPO);
            list.add(this.orderDate);
            list.add(this.webOrderNum);
            list.add(this.siteName);
            list.add(this.siteAddress1);
            list.add(this.siteCity);
            list.add(this.siteState);
            list.add(this.siteZip);
            list.add(this.placedBy);
            list.add(this.orderStatus);
          //  list.add(this.orderNote);

            return list;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getOutboundPO() {
            return outboundPO;
        }

        public void setOutboundPO(String outboundPO) {
            this.outboundPO = outboundPO;
        }

        public Date getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
        }

        public String getWebOrderNum() {
            return webOrderNum;
        }

        public void setWebOrderNum(String webOrderNum) {
            this.webOrderNum = webOrderNum;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getSiteAddress1() {
            return siteAddress1;
        }

        public void setSiteAddress1(String siteAddress1) {
            this.siteAddress1 = siteAddress1;
        }

        public String getSiteCity() {
            return siteCity;
        }

        public void setSiteCity(String siteCity) {
            this.siteCity = siteCity;
        }

        public String getSiteState() {
            return siteState;
        }

        public void setSiteState(String siteState) {
            this.siteState = siteState;
        }

        public String getSiteZip() {
            return siteZip;
        }

        public void setSiteZip(String siteZip) {
            this.siteZip = siteZip;
        }

        public String getPlacedBy() {
            return placedBy;
        }

        public void setPlacedBy(String placedBy) {
            this.placedBy = placedBy;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        /*public String getOrderNote() {
            return orderNote;
        }

        public void setOrderNote(String orderNote) {
            this.orderNote = orderNote;
        }*/

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getOrderId() {
            return orderId;
        }
    }

}
