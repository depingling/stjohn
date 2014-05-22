/*
 * ForecastOrderByLocationReport.java
 *
 * Created on October 15, 2008, 4:43 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.sql.*;
import java.sql.Connection;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import com.cleanwise.service.api.framework.ValueObject;
import java.util.*;
import com.cleanwise.service.api.util.Utility;
import java.text.SimpleDateFormat;
import com.cleanwise.service.api.dao.UserDataAccess;

import org.apache.log4j.Logger;

/**
 * Picks up orders and agreates it on Surrplier
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 * @param pAccountId account Id optionally,
 */
public class ForecastOrderByLocationReport  implements GenericReportMulti {
    private static final Logger log = Logger.getLogger(ForecastOrderByLocationReport.class) ;

    /** Creates a new instance of PurchBySupplierJDReport */
    public ForecastOrderByLocationReport() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */

    protected static final String SUPPLIER = "Supplier";

    protected static final String PRICE_COL = "inv.order_qty * inv.price";
    protected static final String QTY_COL   = "inv.qty_on_hand";
    protected static final String COUNT_COL = "inv.item_id";

    protected static final String LOCATION_NAME = "Location Name";
    protected static final String CITY = "City";
    protected static final String STATE = "State";
    protected static final String TOTAL_BLANK = "Forecasted Items w/Blank";
    protected static final String TOTAL_ZERO = "Forecasted Items w/Zero";
    protected static final String TOTAL_BLANK_OR_ZERO = "Total Items (w/Zero and Blank)";
    protected static final String TOTAL_ENTRY = "Forecasted Items w/Entry";
    protected static final String EXTENDED_PRICE = "Extended Price";
//    protected static final String EXTENDED_PRICE1 = "Extended Price Items w/Blank";
//    protected static final String EXTENDED_PRICE2 = "Extended Price Items w/Zero";
//    protected static final String EXTENDED_PRICE3 = "Extended Price Items (w/Zero and Blank)";
//    protected static final String EXTENDED_PRICE4 = "Extended Price Items w/Entry";

    protected static final String BEG_DATE_S = "BEG_DATE_OPT";
    protected static final String END_DATE_S = "END_DATE_OPT";
    protected static final String END_YEAR_S = "endYear_OPT";
    protected static final String END_MONTH_S = "endMonth_OPT";
    private String localCurrencySign = "$";
    ArrayList repCurrencyList = new ArrayList();

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
     Connection con = pCons.getDefaultConnection();

     ReportingUtils.verifyDates(pParams);


 //    localCurrencySign = getCurrency(con, (String)pParams.get("CUSTOMER"));


//     HashMap userStyles = createReportStyleDescriptor();
     GenericReportResultView result = GenericReportResultView.createValue();

     result.setTitle( getReportTitle(con, pReportData.getName(), pParams));
     result.setHeader(getReportHeader());
     result.setColumnCount(getReportHeader().size());
     result.setName(pReportData.getName());
     result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
     result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
 //  result.setUserStyle(userStyles);
     result.setTable(new ArrayList());
     //====  Request for values ==== //
     try {
       ArrayList repYList = getResultOfQuery(con,  pParams );
       GenericReportResultViewVector resultV = prepareReportData (result, repYList);
       HashMap userStyles = createReportStyleDescriptor();
       result.setUserStyle(userStyles);
       return resultV;
     }
     catch (SQLException exc) {
       String errorMess = "Error. Report.BaseJDReport() SQL Exception happened. " +  exc.getMessage();
       exc.printStackTrace();
       throw new RemoteException(errorMess);
     }
     catch (Exception exc) {
       String errorMess = "Error. Report.BaseJDReport() Exception happened. " +  exc.getMessage();
       exc.printStackTrace();
       throw new RemoteException(errorMess);
     }

   }
   protected String getParamValue (Map pParams, String pControlName){
      String value = null;
      if (pParams.containsKey(pControlName) &&  Utility.isSet( (String) pParams.get(pControlName))) {
        value = (String) pParams.get(pControlName);
      }
      return value;
    }

    protected GenericReportResultViewVector prepareReportData (GenericReportResultView result , ArrayList repList ){
          if (repList.size()!= 0){
            ArrayList tRepList = transform(repList);
            for (Iterator iter1=tRepList.iterator(); iter1.hasNext();) {
              ForecastOrderSummaryDetail det = (ForecastOrderSummaryDetail) iter1.next();
              result.getTable().add(det.toList());
            }
          }
          GenericReportResultViewVector resultV = new GenericReportResultViewVector();
          resultV.add(result);
        return resultV;
    }


    protected HashMap createReportStyleDescriptor(){
     GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();

     /*
    (String parm1, String parm2, String parm3, String parm4, short parm5, String parm6, String parm7, String parm8, String parm9, int[] parm10)
    */
     int[] mergeRegion = new int[]{1,1,1,getReportHeader().size()};

     GenericReportStyleView pageTitle = new GenericReportStyleView(ReportingUtils.PAGE_TITLE,"TEXT",null,"Areal",10,"BOLD",null,null, "LEFT", false, null,null,null,0 );
     GenericReportStyleView colHeaderBlakOnWhite = new GenericReportStyleView("COLUMN_HEADER_BLACK_ON_WHITE","TEXT",null,"Times New Roman",-1,"BOLD","BLACK","WHITE","CENTER", false,null,null,null,0 );

     GenericReportStyleView colDataGreyInt = new GenericReportStyleView("COLUMN_DATA_GREY",ReportingUtils.DATA_CATEGORY.INTEGER,null,null,-1,null,null,"GREY_25_PERCENT",null, false,null,null,null,0 );
     GenericReportStyleView colDataGreyFloat = new GenericReportStyleView("COLUMN_DATA_GREY_FLOAT",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0.00",null,-1,null,null,"GREY_25_PERCENT",null, false,null,null,null,0 );

     GenericReportStyleView colHeaderGrey = new GenericReportStyleView("COLUMN_HEADER_GREY","TEXT",null,null,-1,"BOLD",null,"GREY_25_PERCENT","LEFT",true, null,null,null,0 );
     GenericReportStyleView colHeader = new GenericReportStyleView("COLUMN_HEADER_WRAP","TEXT",null,null,-1,"BOLD",null,null,"LEFT",true, null,null,null,0 );

     GenericReportStyleView colDataCenter = new GenericReportStyleView("COLUMN_DATA_CENTER",null,null,null,-1,null,null,null,"CENTER",false, null,null,null,0 );
//     GenericReportStyleView colDataFloat = new GenericReportStyleView("COLUMN_DATA_CURRENCY",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0.00",null,-1,null,null,null,null, false,null );

     if (repCurrencyList.size() == 0){
       repCurrencyList.add(localCurrencySign);
     }
     GenericReportStyleView[] colDataGreyCurrency = new GenericReportStyleView[repCurrencyList.size()];
     GenericReportStyleView[] colDataCurrency = new GenericReportStyleView[repCurrencyList.size()];
     for (int i = 0; i < repCurrencyList.size(); i++) {
        String currency = (String)repCurrencyList.get(i);
        colDataGreyCurrency[i] = new GenericReportStyleView("COLUMN_DATA_GREY_"+currency, ReportingUtils.DATA_CATEGORY.FLOAT,currency+"#,##0.00",null,-1,null,null,"GREY_25_PERCENT",null, false,null,null,null,0 );
        colDataCurrency[i] = new GenericReportStyleView("COLUMN_DATA_"+currency, ReportingUtils.DATA_CATEGORY.FLOAT,currency+"#,##0.00",null,-1,null,null,null,null, false,null,null,null,0 );
     }

     reportDesc.setUserStyle(pageTitle.getStyleName(), pageTitle);
     reportDesc.setUserStyle(colHeaderBlakOnWhite.getStyleName(), colHeaderBlakOnWhite);
     reportDesc.setUserStyle(colDataGreyInt.getStyleName(), colDataGreyInt);
     reportDesc.setUserStyle(colDataGreyFloat.getStyleName(), colDataGreyFloat);
//     reportDesc.setUserStyle(colDataGreyCurrency.getStyleName(), colDataGreyCurrency);
     reportDesc.setUserStyle(colHeader.getStyleName(), colHeader);
     reportDesc.setUserStyle(colHeaderGrey.getStyleName(), colHeaderGrey);
     reportDesc.setUserStyle(colDataCenter.getStyleName(), colDataCenter);
//     reportDesc.setUserStyle(colDataCurrency.getStyleName(), colDataCurrency);
     for (int i = 0; i < repCurrencyList.size(); i++) {
       reportDesc.setUserStyle(colDataGreyCurrency[i].getStyleName(), colDataGreyCurrency[i]);
       reportDesc.setUserStyle(colDataCurrency[i].getStyleName(), colDataCurrency[i]);
     }

     HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
     return styleDesc;
   }


    protected GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(this.LOCATION_NAME, "COLUMN_HEADER_WRAP", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.CITY, "COLUMN_HEADER_WRAP", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.STATE, "COLUMN_HEADER_WRAP", "COLUMN_DATA_CENTER","6"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_ZERO, "COLUMN_HEADER_WRAP", ReportingUtils.INTEGER_STYLE,"8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE, "COLUMN_HEADER_WRAP", "COLUMN_DATA_$", "10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_BLANK, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY","8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_$", "10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_BLANK_OR_ZERO, "COLUMN_HEADER_WRAP", ReportingUtils.INTEGER_STYLE,"8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE,"COLUMN_HEADER_WRAP", "COLUMN_DATA_$", "10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_ENTRY, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY","8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_$", "10"));
        return header;
    }

    protected ArrayList getResultOfQuery(Connection conn, Map pParams) throws Exception{
      BigDecimal zeroAmt = new BigDecimal(0);
      Integer zeroNum = new Integer(0);

      String userIdStr = (String) pParams.get("CUSTOMER");
      String storeIdStr = (String) pParams.get("STORE_SELECT");

      String siteCritSql = getUserSites(conn, userIdStr, storeIdStr);

      String orderSummarySql= getQuerySql(siteCritSql, pParams);

      String subSql = getQuerySql(orderSummarySql);

      String selectSql =
          "select \n" +
          "   (select SHORT_DESC from CLW_BUS_ENTITY where BUS_ENTITY_ID = sub.site_id ) LOCATION_NAME, \n" +
          "    city CITY, \n" +
          "    state_province_cd STATE, \n" +
          "   (select local_code from CLW_CURRENCY where locale = sub.locale_cd) CURRENCY, \n" +
          "    sub.* \n"+
          " from \n" +
          "  (" +
          subSql +
          "  ) sub, \n" +
          " clw_address \n" +
          "WHERE bus_entity_id = site_id \n" +
          ((Utility.isSet(siteCritSql))? "  AND site_id IN (" + siteCritSql + ") \n" : "") +
          " order by location_name \n";

      PreparedStatement pstmt = conn.prepareStatement(selectSql);

      log.info(" -------> SQL: " + selectSql);
      ResultSet rs = pstmt.executeQuery();
      ArrayList psViewV= new ArrayList();
      while (rs.next()){
          ForecastOrderSummaryDetail record = new ForecastOrderSummaryDetail();
          String locName = (rs.getString("LOCATION_NAME") != null) ? rs.getString("LOCATION_NAME") : "";
          String city = (rs.getString("CITY") != null) ? rs.getString("CITY") : "";
          String state = (rs.getString("STATE") != null) ? rs.getString("STATE") : "";
          String currency = (rs.getString("CURRENCY") != null) ? rs.getString("CURRENCY") : "";

          Integer forecast = (Utility.isSet(rs.getString("FORECAST"))) ? new Integer(rs.getString("FORECAST")) : zeroNum ;
          BigDecimal extendedPrice = (rs.getBigDecimal("EXTENDED_PRICE") != null) ? rs.getBigDecimal("EXTENDED_PRICE") : zeroAmt;
          String sumType = rs.getString("SUM_TYPE");

          record.setLocationName(locName);
          record.setCity(city);
          record.setState(state);
          record.setCurrency(currency);
          record.setForecast(forecast);
          record.mExtendedPrice = extendedPrice;
          record.setForecastType(sumType);
          psViewV.add(record);
      }
      pstmt.close();
      rs.close();

      return psViewV;

    }

    protected String getQuerySql(String subSelect) {
       String sql =
           "SELECT site_id,   \n" +
           "       locale_cd, \n" +
            "      sum_type, \n" +
            "      sum (forecast) forecast, \n" +
            "      sum(extended_price) extended_price \n" +
            "  FROM (  \n" +
           "SELECT g.site_id,   \n" +
           "       NVL(g.locale_cd, \n" +
           "            (select locale_cd from clw_contract c, clw_catalog_assoc ca \n" +
           "              where c.catalog_id = ca.CATALOG_ID \n" +
           "                and ca.BUS_ENTITY_ID = g.site_id)) locale_cd, \n" +
            "       g.sum_type , \n" +
            "       count( g.item_id) forecast, \n" +
            "       sum (g.extended_price) extended_price \n" +
            "  FROM (  \n" +
         subSelect +
            "     ) g \n" +
            "  GROUP BY site_id, locale_cd, sum_type) sss \n" +
			" GROUP BY site_id, locale_cd, sum_type \n";
       return sql;
    }

    protected String getQuerySql(String pSiteCrit, Map pParams) throws Exception {
      //=========== getting parameters =======================================

       String begDateStr = (String) pParams.get(BEG_DATE_S);
       String endDateStr = (String) pParams.get(END_DATE_S);
       String endYearStr = (String) pParams.get(END_YEAR_S);
       String endMonthStr = (String) pParams.get(END_MONTH_S);
       if(begDateStr.length()==0 && endDateStr.length()==0){
         ArrayList dateRange = convertToDateStr( endMonthStr, endYearStr );
         begDateStr = (String)dateRange.get(0);
         endDateStr = (String)dateRange.get(1);
       }
       //======================================================================//

      GregorianCalendar currBegCalendar = new GregorianCalendar();
      currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

      GregorianCalendar currEndCalendar = new GregorianCalendar();
      currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

      String startDateS = ReportingUtils.toSQLDate(currBegCalendar.getTime());
      String endDateS =   ReportingUtils.toSQLDate(currEndCalendar.getTime());

      String siteListS = pSiteCrit;

      String filterCrit =
          "  AND inv.add_date BETWEEN " + startDateS + " \n" + " AND " + endDateS + " + 1 \n" ;


        String sql=
            " SELECT  inv.bus_entity_id site_id, o.locale_cd, \n" +
            "         inv.item_id item_id, \n" +
            "         inv.qty_on_hand AS qty_on_hand, \n" +
            "        (inv.order_qty * NVL (o.price, inv.price)) AS extended_price, \n" +
            "        (CASE WHEN inv.qty_on_hand is null THEN 'Blank'   \n" +
            "              WHEN inv.qty_on_hand =0 THEN 'Zero' \n" +
            "              WHEN inv.qty_on_hand >0 THEN 'Entry' \n" +
            "         END ) sum_type \n" +
            " FROM (SELECT o.site_id, o.order_num, o.locale_cd, \n" +
            "              TRUNC (o.original_order_date) AS ord_add_date, oi.item_id, \n" +
            "              oi.cust_item_sku_num, oi.dist_erp_num, \n" +
            "              NVL (oi.cust_item_uom, oi.item_uom) AS uom, \n" +
            "              oi.cust_contract_price AS price \n" +
            "              FROM clw_order_item oi, clw_order o \n" +
            "              WHERE o.order_status_cd NOT IN \n" +
            "              ('Cancelled', \n" +
            "               'ERP Rejected', \n" +
            "               'Rejected', \n" +
            "               'Duplicate', \n" +
            "               'REFERENCE_ONLY' \n" +
            "              ) \n" +
            "              AND o.order_id = oi.order_id \n" +
            "              AND o.order_source_cd = 'Inventory') o, \n" +
            "  clw_inventory_order_qty inv, \n" +
            "  clw_bus_entity_assoc ba, \n" +
            "  clw_inventory_items ii \n" +
            "WHERE  \n" +
            " ba.bus_entity1_id = inv.bus_entity_id \n" +
            " AND ba.bus_entity_assoc_cd = 'SITE OF ACCOUNT' \n" +
            " AND inv.item_id = ii.item_id(+) \n" +
            " AND TRUNC (inv.add_date) = o.ord_add_date(+) \n" +
            " AND inv.item_type = 'Inventory' \n" +
            " AND inv.item_id = o.item_id(+) \n" +
            " AND o.site_id(+) = inv.bus_entity_id \n" +
            filterCrit ;

        return sql;
    }

    private String getUserSites(Connection pCon, String pUserIdStr, String pStoreIdStr){
      String siteIdsSql = "";
      int userId = (Utility.isSet(pUserIdStr)) ? Integer.parseInt(pUserIdStr) : -1;
      int storeId = (Utility.isSet(pStoreIdStr)) ? Integer.parseInt(pStoreIdStr) : -1;

      try {
        UserData userD = UserDataAccess.select(pCon, userId);
        String userTypeCd = userD.getUserTypeCd();
        if (RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)) {
           siteIdsSql=
               "SELECT DISTINCT be.BUS_ENTITY_ID \n" +
               " FROM CLW_BUS_ENTITY_ASSOC be1, CLW_BUS_ENTITY_ASSOC be2, CLW_BUS_ENTITY be, CLW_PROPERTY p \n" +
               " WHERE be1.BUS_ENTITY2_ID = " + storeId + " \n" +
               "   AND be1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
               "   AND be2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
               "   AND be1.BUS_ENTITY1_ID = be2.BUS_ENTITY2_ID \n" +
               "   AND be.BUS_ENTITY_ID   = be2.BUS_ENTITY1_ID \n" +
               "   AND BUS_ENTITY_STATUS_CD = 'ACTIVE' \n" +
               "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = be.BUS_ENTITY_ID \n" +
               "   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "'  \n" ;
        } else if ( RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) )  {
          siteIdsSql=
              " SELECT DISTINCT ua.BUS_ENTITY_ID FROM CLW_USER_ASSOC ua, CLW_PROPERTY p \n" +
              "  WHERE ua.USER_ID = " + userId + " AND ua.USER_ASSOC_CD = 'SITE' \n" +
              "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = ua.BUS_ENTITY_ID \n" +
              "   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "'  \n" ;
        } else {
          siteIdsSql=
              "SELECT DISTINCT be.BUS_ENTITY_ID \n" +
             " FROM CLW_BUS_ENTITY_ASSOC be1, CLW_BUS_ENTITY_ASSOC be2, \n" +
             " CLW_BUS_ENTITY be, CLW_USER_ASSOC ua, CLW_PROPERTY p \n" +
             " WHERE be1.BUS_ENTITY2_ID = " + storeId + " \n" +
             "   AND be1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
             "   AND be2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
             "   AND be1.BUS_ENTITY1_ID = be2.BUS_ENTITY2_ID \n" +
             "   AND be.BUS_ENTITY_ID   = be2.BUS_ENTITY1_ID \n" +
             "   AND BUS_ENTITY_STATUS_CD = 'ACTIVE' \n" +
             "   AND be.BUS_ENTITY_ID = ua.BUS_ENTITY_ID \n " +
             "   AND ua.USER_ID = " + userId + " AND ua.USER_ASSOC_CD = 'SITE' \n" +
             "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = be.BUS_ENTITY_ID \n" +
             "   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "'  \n" ;
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

      return siteIdsSql;
    }

    protected String getTitle() {
      String title = "Forecast Order Summary by Location Report" ;
      return title;
    }

    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      title.add(ReportingUtils.createGenericReportColumnView(pTitle,ReportingUtils.PAGE_TITLE, null, "20"));
//      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Store : " + getListOfEntityNames(con, (String) pParams.get(STORE_S)),0,255,"VARCHAR2"));
      if (pParams.containsKey(this.BEG_DATE_S) && Utility.isSet((String) pParams.get(this.BEG_DATE_S)) &&
              pParams.containsKey(this.END_DATE_S) && Utility.isSet((String) pParams.get(this.END_DATE_S))){
            title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(BEG_DATE_S) + " - " + (String) pParams.get(END_DATE_S),ReportingUtils.PAGE_TITLE, null, "20"));
          }else{
        	  String controlName = null;
              controlName = this.END_MONTH_S;
              if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
                title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(this.END_MONTH_S)+ "/" + (String) pParams.get(this.END_YEAR_S) ,ReportingUtils.PAGE_TITLE, null, "20"));
              }
          }

//      controlName = SITE_MULTI_OPT_S;
//      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
//        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sites : " + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
//     }
      /*if (pParams.containsKey(this.BEG_DATE_S) && Utility.isSet((String) pParams.get(this.BEG_DATE_S)) &&
          pParams.containsKey(this.END_DATE_S) && Utility.isSet((String) pParams.get(this.END_DATE_S))){
        title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(BEG_DATE_S) + " - " + (String) pParams.get(END_DATE_S),ReportingUtils.PAGE_TITLE, null, "20"));
      }*/
  return title;
}

 protected ArrayList convertToDateStr ( String pEndMonthStr, String pEndYearStr){

   //Month year dropdown
   ArrayList dateRange = new ArrayList();
   int endYear = Integer.parseInt(pEndYearStr);
   int endMonth = Integer.parseInt(pEndMonthStr);
   if(endYear!=0 && endMonth !=0){
         GregorianCalendar begC = new GregorianCalendar(endYear, endMonth - 1, 1);
         Date beginDateC = begC.getTime();

         GregorianCalendar endC = new GregorianCalendar(endYear,endMonth-1,1);
         endC.set(Calendar.DAY_OF_MONTH, endC.getActualMaximum(Calendar.DATE));
         Date endDateC = endC.getTime();

         SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

         dateRange.add(df.format(beginDateC));
         dateRange.add(df.format(endDateC));
   }
   return dateRange;
 }

 protected ArrayList transform (ArrayList repList) {
   ForecastOrderSummaryVector newRepList= new ForecastOrderSummaryVector();
   ForecastOrderSummaryDetail det = null;
   BigDecimal bd = null;
   if (repList != null){
     int totBlankOrZero = 0;
     double totPriceBlankOrZero = 0;
     ForecastOrderSummaryDetail newDet = new ForecastOrderSummaryDetail();
     newDet.init();
     ForecastOrderSummaryDetail detPre = (ForecastOrderSummaryDetail) repList.get(0);
     String currPre = detPre.getCurrency();
     for (Iterator iter1 = repList.iterator(); iter1.hasNext(); ) {
       det = (ForecastOrderSummaryDetail) iter1.next();
       //----- create currency

       if (!detPre.getLocationName().equals(det.getLocationName())) {
         newDet.setLocationName(detPre.getLocationName());
         newDet.setCity(detPre.getCity());
         newDet.setState(detPre.getState());
         newDet.setCurrency(det.getCurrency());
         newDet.setTotalBlankOrZero(new Integer(totBlankOrZero));
         bd = new BigDecimal(totPriceBlankOrZero);
         bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);
         newDet.setExtendedPriceBlankOrZero(bd);
         newRepList.add(newDet);
         detPre = det;

         newDet = new ForecastOrderSummaryDetail();
         newDet.init();
         totBlankOrZero = 0;
         totPriceBlankOrZero = 0;
       }
         if (det.getForecastType().equals("Blank")) {
             newDet.setForecastBlank(det.getForecast());
             newDet.setExtendedPriceBlank(det.getExtendedPrice());
             totBlankOrZero = totBlankOrZero + det.getForecast().intValue();
             totPriceBlankOrZero = totPriceBlankOrZero + det.getExtendedPrice().doubleValue();
         }
         else if (det.getForecastType().equals("Zero")) {
             newDet.setForecastZero(det.getForecast());
             newDet.setExtendedPriceZero(det.getExtendedPrice());
             totBlankOrZero = totBlankOrZero + det.getForecast().intValue();
             totPriceBlankOrZero = totPriceBlankOrZero + det.getExtendedPrice().doubleValue();
         }
         else if (det.getForecastType().equals("Entry")) {
             newDet.setForecastEntry(det.getForecast());
             newDet.setExtendedPriceEntry(det.getExtendedPrice());
         }

     }
     newDet.setLocationName(det.getLocationName());
     newDet.setCurrency(det.getCurrency());
     newDet.setCity(det.getCity());
     newDet.setState(det.getState());
     newDet.setTotalBlankOrZero(new Integer(totBlankOrZero));
     bd = new BigDecimal(totPriceBlankOrZero);
     bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);
     newDet.setExtendedPriceBlankOrZero(bd);
     newRepList.add(newDet);
     newRepList.sort("LocationName");
   }
   return newRepList;
 }
 private String getCurrency(Connection pConn, String customerIdStr){
   return "$";
/*   String sql =
       "select LOCAL_CODE from CLW_CURRENCY c, CLW_USER u \n" +
       " where c.LOCALE = u.PREF_LOCALE_CD \n" +
       "   and u.USER_ID = " + Integer.parseInt(customerIdStr);
   PreparedStatement stmt = pConn.prepareStatement(sql);
   ResultSet rs=stmt.executeQuery();
*/
/*
   CleanwiseUser appUser = ShopTool.getCurrentUser(request);

   Locale thisUserLocale = appUser.getPrefLocale();
   AccountData accountData = appUser.getUserAccount();
   SiteData siteData = appUser.getSite();

   ContractData contractData = siteData.getContractData();
   String purchasesLocale = null;
   String isEmpty = "false";
   if (siteData != null && siteData.getContractData() != null ) {
     purchasesLocale = contractData.getLocaleCd();
   }

   if ( null == purchasesLocale ) {
     purchasesLocale = "en_US";
   }

   String thisCurrencyCode = "USD";
   CurrencyData currData = ClwI18nUtil.getCurrency(purchasesLocale);
   if ( currData != null && currData.getGlobalCode() != null ) {
        thisCurrencyCode = currData.getGlobalCode();
   }
*/
 }
//================================================================================
 public class ForecastOrderSummaryVector extends java.util.ArrayList implements Comparator {
    /**
     * Constructor.
     */
    public ForecastOrderSummaryVector () {}

    String _sortField = "";
    boolean _ascFl = true;
    /**
     * Sort
     */
    public void sort(String pFieldName) {
       sort(pFieldName,true);
    }

    public void sort(String pFieldName, boolean pAscFl) {
       _sortField = pFieldName;
       _ascFl = pAscFl;
       Collections.sort(this,this);
    }

   public int compare(Object o1, Object o2)
    {
      int retcode = -1;
      ForecastOrderSummaryDetail obj1 = (ForecastOrderSummaryDetail)o1;
      ForecastOrderSummaryDetail obj2 = (ForecastOrderSummaryDetail)o2;

      if("LocationName".equalsIgnoreCase(_sortField)) {
       String i1 = obj1.getLocationName();
       String i2 = obj2.getLocationName();
       if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
       else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
     }

      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
   }

 private class ForecastOrderSummaryDetail //implements Record, java.io.Serializable
   {
     private String mLocationName;
     private String mCity;
     private String mState;
     private String mCurrency;

     private Integer mForecastBlank;
     private Integer mForecastZero;
     private Integer mTotalBlankOrZero;
     private Integer mForecastEntry;
     private BigDecimal mExtendedPriceBlank;
     private BigDecimal mExtendedPriceZero;
     private BigDecimal mExtendedPriceBlankOrZero;
     private BigDecimal mExtendedPriceEntry;

     private String mForecastType;
     private Integer mForecast;
     private BigDecimal mExtendedPrice;

     public void init(){
       mForecastBlank = new Integer(0);
       mForecastZero = new Integer(0);
       mTotalBlankOrZero = new Integer(0);
       mForecastEntry = new Integer(0);
       mExtendedPriceBlank = new BigDecimal(0);
       mExtendedPriceZero = new BigDecimal(0);
       mExtendedPriceBlankOrZero = new BigDecimal(0);
       mExtendedPriceEntry = new BigDecimal(0);

     }
     private ArrayList toList() {
         ArrayList list = new ArrayList();

         list.add(mLocationName);
         list.add(mCity);
         list.add(mState);
         list.add(mForecastZero);
         list.add(mExtendedPriceZero);
         list.add(mForecastBlank);
         list.add(mExtendedPriceBlank);
         list.add(mTotalBlankOrZero);
         list.add(mExtendedPriceBlankOrZero);
         list.add(mForecastEntry);
         list.add(mExtendedPriceEntry);
         return list;
     }

     private GenericReportCellView getCellView(Object dataVal, String styleName){
       GenericReportCellView cellView = GenericReportCellView.createValue();
       cellView.setDataValue(dataVal);
       cellView.setStyleName(styleName);
       return cellView;
     }

     public String getLocationName() {
       return mLocationName;
     }

     public void setLocationName(String pLocationName) {
       this.mLocationName = pLocationName;
     }
     public String getCity() {
       return mCity;
     }

     public void setCity(String pCity) {
       this.mCity = pCity;
     }
     public String getState() {
       return mState;
     }

     public void setState(String pState) {
       this.mState = pState;
     }
     public String getCurrency() {
       return mCurrency;
     }

     public void setCurrency(String pCurrency) {
       this.mCurrency = pCurrency;
     }

     public Integer getForecastBlank() {
          return mForecastBlank;
      }

      public void setForecastBlank(Integer pForecastBlank) {
          this.mForecastBlank = pForecastBlank;
      }
      public Integer getForecastZero() {
           return mForecastZero;
       }
       public void setForecastZero(Integer pForecastZero) {
           this.mForecastZero = pForecastZero;
       }
       public Integer getTotalBlankOrZero() {
            return mTotalBlankOrZero;
        }
       public void setTotalBlankOrZero(Integer pTotalBlankOrZero) {
            this.mTotalBlankOrZero = pTotalBlankOrZero;
        }
      public Integer getForecastEntry() {
            return mForecastEntry;
        }
      public void setForecastEntry(Integer pForecastEntry) {
          this.mForecastEntry = pForecastEntry;
      }
      public BigDecimal getExtendedPriceBlank() {
          return mExtendedPriceBlank;
      }
      public void setExtendedPriceBlank(BigDecimal pExtendedPriceBlank) {
          this.mExtendedPriceBlank = pExtendedPriceBlank;
      }
      public BigDecimal getExtendedPriceZero() {
          return mExtendedPriceZero;
      }
      public void setExtendedPriceZero(BigDecimal pExtendedPriceZero) {
          this.mExtendedPriceZero = pExtendedPriceZero;
      }
      public BigDecimal getExtendedPriceBlankOrZero() {
          return mExtendedPriceBlankOrZero;
      }
      public void setExtendedPriceBlankOrZero(BigDecimal pExtendedPriceBlankOrZero) {
          this.mExtendedPriceBlankOrZero = pExtendedPriceBlankOrZero;
      }
      public BigDecimal getExtendedPriceEntry() {
          return mExtendedPriceEntry;
      }
      public void setExtendedPriceEntry(BigDecimal pExtendedPriceEntry) {
          this.mExtendedPriceEntry = pExtendedPriceEntry;
      }
      //==========================================//
      public String getForecastType() {
         return mForecastType;
       }

       public void setForecastType(String pForecastType) {
         this.mForecastType = pForecastType;
       }

      public Integer getForecast() {
          return mForecast;
      }
      public void setForecast(Integer pForecast) {
          this.mForecast = pForecast;
      }
      public BigDecimal getExtendedPrice() {
          return mExtendedPrice;
      }
      public void setExtendedPrice(BigDecimal pExtendedPrice) {
          this.mExtendedPrice = pExtendedPrice;
      }

   }

//=======================================================================================//
 public class GenericReportUserStyleDesc extends ValueObject {
   HashMap mReportUserStyle;

   public GenericReportUserStyleDesc ()
   {
     mReportUserStyle = new HashMap();
   }

   public void setUserStyle ( String pUserStyleType, GenericReportStyleView pPageTitleStyle){
     if (mReportUserStyle == null){
       mReportUserStyle = new HashMap();
     }
     mReportUserStyle.put(pUserStyleType, pPageTitleStyle);
   }
   public GenericReportStyleView getUserStyle(String pUserStyleType){
      return ((mReportUserStyle != null) ? (GenericReportStyleView)mReportUserStyle.get(pUserStyleType) : null);
   }
   public HashMap getGenericReportUserStyleDesc(){
     return mReportUserStyle;
   }

   public void setGenericReportUserStyleDesc (HashMap pReportUserStyle){
     mReportUserStyle = pReportUserStyle;
   }
  }
}
