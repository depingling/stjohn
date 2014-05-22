package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.util.Utility;

import org.apache.log4j.Logger;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.Connection;
import com.cleanwise.service.api.value.IdVector;
import java.sql.SQLException;
import java.lang.Math;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.value.GenericReportStyleView;
import java.rmi.RemoteException;


public class StopAndShopOrderTotalsReportDW implements GenericReportMulti {

    private static final Logger log = Logger.getLogger(StopAndShopOrderTotalsReportDW.class);

//    private static final String BEG_DATE = "BEG_DATE";
//    private static final String END_DATE = "END_DATE";
//    private static final String STORE_ID = "STORE";
    protected static final String REPORT_NAME    = "REPORT_NAME";
    protected static final String STORE_S        = "DW_STORE_SELECT";
    protected static final String BEG_DATE_S     = "DW_BEGIN_DATE";
    protected static final String END_DATE_S     = "DW_END_DATE";
//    protected static final String REPRES_OPT_S   = "DW_DSR_OPT";
//    protected static final String MARKET_OPT_S   = "DW_VERTICAL_OPT";
//    protected static final String REGION_OPT_S   = "DW_REGION_OPT";
    protected static final String LOCATE_ACCOUNT_MULTI_OPT_S = "DW_LOCATE_ACCOUNT_MULTI_OPT";
    protected static final String LOCATE_SITE_MULTI_OPT_S    = "DW_LOCATE_SITE_MULTI_OPT";
    protected static final String LOCATE_MANUFACTURER_OPT_S = "DW_LOCATE_MANUFACTURER_OPT";
    protected static final String LOCATE_DISTRIBUTOR_OPT_S  = "DW_LOCATE_DISTRIBUTOR_OPT";
    protected static final String CATEGORY_OPT_S      = "DW_CATEGORY_OPT";
    protected static final String LOCATE_ITEM_OPT_S   = "DW_LOCATE_ITEM_OPT";
//    protected static final String CONNECT_CUST_OPT_S   = "DW_CONNECT_CUST_OPT";
//    protected static final String REGION_AUTOSUGGEST_OPT_S   = "DW_REGION_AUTOSUGGEST_OPT";
//    protected static final String REPRES_AUTOSUGGEST_OPT_S   = "DW_DSR_AUTOSUGGEST_OPT";
    protected static final String CATEGORY_AUTOSUGGEST_OPT_S = "DW_CATEGORY_AUTOSUGGEST_OPT";

    public static final String TABLE_WIDTH="748";
    protected static final String PRICE_COL = "PRICE";
    protected static final String QTY_COL   = "QUANTITY";
//    protected static final String COST_COL  = "LINE_COST";

    protected static final String GRAND_TOTAL ="Grand Total:";
//    protected static final String SUB_TOTAL ="Sub Total";
    protected static final String BOLD_STYLE = "BOLD";

    protected static final String FONT_NAME = "Arial";
    protected static final int FONT_SIZE = 10;

    /*** original array ***/
    /***
    protected static final String[] COL_WIDTH = new String[]
        {"24","20","22","11","11","12","8","10","9","11","7","8","9","10",
         "13","22","20","11","35","5","5","12","14","15","10" };
    ***/

    protected static final String[] COL_WIDTH = new String[]
        {"13","21","8","5","23","16","15","11","12","14","36","10"};

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getReportConnection();
        String errorMess = "No error";

        String reportName = (String) pParams.get(REPORT_NAME);
        String begDateStr = (String) pParams.get(BEG_DATE_S);
        String endDateStr = (String) pParams.get(END_DATE_S);

        String filterCond = createFilterCondition(pParams);
        String dateCond = createDateSqlCond( begDateStr, endDateStr, 0);
        HashMap userStyles = createReportStyleDescriptor();

        GenericReportResultView tab1 = GenericReportResultView.createValue();
        GenericReportColumnViewVector tab1Title = getReportTitle(con,pReportData.getName(), pParams);
        GenericReportColumnViewVector tab1Header = getReportHeader1();

        tab1.setWidth(TABLE_WIDTH);
        tab1.setTitle(tab1Title);
        tab1.setHeader(tab1Header);
        tab1.setColumnCount(tab1Header.size());
        tab1.setName("Order Totals"); // length - maximum 30 letters
        tab1.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        tab1.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
        tab1.setUserStyle(userStyles);
        tab1.setFreezePositionColumn(2);
        tab1.setFreezePositionRow(tab1.getTitle().size()+2);
 //       tab1.setTable(new ArrayList());


        GenericReportResultView tab2 = GenericReportResultView.createValue();
        GenericReportColumnViewVector tab2Title = getReportTitle(con,pReportData.getName() + " Details", pParams);
        GenericReportColumnViewVector tab2Header = getReportHeader2();

        tab2.setWidth(TABLE_WIDTH);
        tab2.setTitle(tab2Title);
        tab2.setHeader(tab2Header);
        tab2.setColumnCount(tab2Header.size());
        tab2.setName("Details"); // length - maximum 30 letters
        tab2.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
        tab2.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
        tab2.setUserStyle(userStyles);
        tab2.setFreezePositionColumn(2);
        tab2.setFreezePositionRow(tab2.getTitle().size()+2);

 //       tab2.setTable(new ArrayList());

        //====  Request for values ==== //
        int storeId;
        try {
          storeId = Integer.parseInt((String) pParams.get(STORE_S));

          GenericReportResultViewVector resultV = new GenericReportResultViewVector();

          ArrayList tab1Data = getTab1QueryResult(con, filterCond, dateCond);
          tab1.setTable(tab1Data);

          ArrayList tab2Data = getTab2QueryResult(con, filterCond, dateCond);
          tab2.setTable(tab2Data);

          resultV.add(tab2);
          resultV.add(tab1);

          return resultV;
        }
        catch (NumberFormatException e) {
          log.info(e.getMessage(), e);
          String mess = "^clw^Store identifier is not a valid value^clw^";
          throw new Exception(mess);
        }
        catch (SQLException exc) {
          errorMess = "Error. SQL Exception happened. " + exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
          errorMess = "Error. Exception happened. " + exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }

  }

      public ArrayList getTab1QueryResult(Connection pCon, String filterCond, String dateCond) throws Exception {

        String sql =
            "SELECT  \n"+
            "  (SELECT ACCOUNT_NAME FROM dw_account_dim WHERE ACCOUNT_DIM_ID = g.ACCOUNT_DIM_ID) account_name, \n"+
            "  site.SITE_NAME site_name, \n"+
            "  site.SITE_REGION site_region, \n"+                                            //SVC
            "  site.SITE_MARKET site_market, \n"+                                            //SVC
            "  site.SITE_DISTRICT site_district, \n"+                                        //SVC
            "  site.SITE_TYPE site_type, \n"+                                                //SVC
            "  site.SITE_SQUARE_FOOTAGE site_square_footage, \n"+                            //SVC
            "  site.SITE_AVERAGE_CUSTOMER_COUNT site_average_customer_count, \n"+            //SVC
            "  site.SITE_BSC site_bsc, \n"+                                                  //SVC
            "  site.SITE_STREET_ADDRESS address, \n"+
            "  site.SITE_CITY site_city, \n"+                                                //SVC
            "  site.SITE_STATE site_state, \n"+                                              //SVC
            "  site.SITE_NUM site_budget_ref, \n"+
 //           "  site.SITE_DIST_REF_CD site_dist_ref, \n"+
 //          --       (select Site_name from dw_site_dim where site_dim_id = g.SITE_DIM_ID) Site_name,
 //          --       (select SITE_STREET_ADDRESS  from dw_site_dim  where site_dim_id = g.site_DIM_ID) address,
 //          --       (select site_num  from dw_site_dim  where site_dim_id = g.site_DIM_ID) site_budget_ref,
 //          --       (select site_dist_ref_cd  from dw_site_dim  where site_dim_id = g.site_DIM_ID) site_dist_ref,
            "  ORDER_NUM confirm_num, \n"+
            "  PO_NUM, \n"+
            "  (SELECT CALENDAR_DATE FROM dw_date_dim WHERE DATE_DIM_ID = g.DATE_DIM_ID) order_date, \n"+
            "  SALE_TYPE, \n"+
            "  ORDER_SUBTOTAL \n"+
 //           "  TAX, FREIGHT, HANDLING, \n"+ //SVC
 //           "   CURRENCY_CD \n"+            //SVC
            "FROM dw_site_dim site, \n"+
            "  (SELECT   ACCOUNT_DIM_ID, \n"+
            "            SITE_DIM_ID, \n"+
            "            ORDER_NUM, \n"+
            "            PO_NUM,    \n"+
            "            SALE_TYPE, \n"+
            "            DATE_DIM_ID, \n"+
            "            SUM (ORDER_SUBTOTAL) order_subtotal \n"+
 //           "            SUM (TAX) tax, SUM (FREIGHT) freight, SUM (HANDLING) handling, \n"+
 //           "            CURRENCY_CD \n"+
            "    FROM (SELECT order_id, \n"+
            "                 max(account_dim_id) account_dim_id, \n"+
            "                 max(SITE_DIM_ID) site_dim_id, \n"+
            "                 max(ORDER_NUM) order_num, \n"+
            "                 max(REQUEST_PO_NUM) po_num, \n"+
            "                 max(SALE_TYPE) sale_type, \n"+
            "                 max(DATE_DIM_ID) date_dim_id, \n"+
            "                 sum(QUANTITY * PRICE) order_subtotal \n"+
 //           "                 sum(TAX_AMOUNT) tax, \n"+
 //           "                 max(TOTAL_FREIGHT_COST) freight, \n"+
 //           "                 max(TOTAL_MISC_COST) handling, \n"+
 //           "                 max(CURRENCY_CD) currency_cd \n"+
            "            FROM dw_order_fact f \n"+
            "            WHERE QUANTITY is not NULL AND PRICE is not NULL AND \n"+
            "               DATE_DIM_ID in ( \n"+ dateCond + ") \n"+
            filterCond + " \n"+
            "GROUP BY order_id) o  \n"+
            "       GROUP BY ACCOUNT_DIM_ID, SITE_DIM_ID, DATE_DIM_ID,     \n"+
 //           "                ORDER_NUM, PO_NUM, SALE_TYPE, CURRENCY_CD ) g \n"+
            "                ORDER_NUM, PO_NUM, SALE_TYPE ) g \n"+
            "WHERE site.SITE_DIM_ID = g.SITE_DIM_ID  \n" +
            "ORDER by account_name, site_name";

        log.info(".getTab1QueryResult() SQL:" + sql);

        Statement stmt = pCon.createStatement();

        double totalSum = 0;
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList lines = new ArrayList();
        while (rs.next()) {
            ARecord record = new ARecord();

            String accName = rs.getString("account_name");
            String siteName = rs.getString("site_name");

            String siteRegion = rs.getString("site_region");
            String siteMarket = rs.getString("site_market");
            String siteDistrict = rs.getString("site_district");
            String siteType = rs.getString("site_type");
            String siteSquareFootage = rs.getString("site_square_footage");
            String siteAverageCustomerCount = rs.getString("site_average_customer_count");
            String siteBsc = rs.getString("site_bsc");

            String address = rs.getString("address");

            String siteCity = rs.getString("site_city");
            String siteState = rs.getString("site_state");

            String siteBudgetRef = rs.getString("site_budget_ref");
//            String siteDistRef = rs.getString("site_dist_ref");
            String confirmNum = rs.getString("confirm_num");
            String poNum = rs.getString("PO_NUM");
            Date orderDate = rs.getDate("order_date");
            String saleType = rs.getString("SALE_TYPE");

            BigDecimal orderSubtotal = rs.getBigDecimal("order_subtotal");
            totalSum += orderSubtotal.doubleValue();

            //BigDecimal tax = rs.getBigDecimal("tax");
            //BigDecimal freight = rs.getBigDecimal("freight");
            //BigDecimal handling = rs.getBigDecimal("handling");
            //String currencyCd = rs.getString("currency_cd");

            record.setAccountName(accName);
            record.setSiteName(siteName);

            record.setSiteRegion(siteRegion);
            record.setSiteMarket(siteMarket);
            record.setSiteDistrict(siteDistrict);
            record.setSiteType(siteType);
            record.setSiteSquareFootage(siteSquareFootage);
            record.setSiteAverageCustomerCount(siteAverageCustomerCount);
            record.setSiteBsc(siteBsc);

            record.setAddress(address);

            record.setSiteCity(siteCity);
            record.setSiteState(siteState);

            record.setSiteBudgetRef(siteBudgetRef);
//            record.setSiteDistRef(siteDistRef);
            record.setConfirmNum(confirmNum);
            record.setPoNum(poNum);
            record.setOrderDate(orderDate);
            record.setSaleType(saleType);
            record.setOrderSubtotal(orderSubtotal);
//            record.setTax(tax);
//            record.setFreight(freight);
//            record.setHandling(handling);
//            record.setCurrencyCd(currencyCd);

            lines.add(record.toList1());


        }
        rs.close();
        stmt.close();

        // define Total record
        ARecord recordTotal = new ARecord();
        recordTotal.init();
        recordTotal.setAccountName(GRAND_TOTAL);
        recordTotal.setOrderSubtotal(new BigDecimal(totalSum));
        lines.add(recordTotal.toList1());
        return lines;
    }

    public ArrayList getTab2QueryResult(Connection pCon, String filterCond, String dateCond) throws Exception {

      String sql =
          "SELECT  \n"+
          "       a.ACCOUNT_NAME account_name, \n"+
          "       s.SITE_NAME site_name, \n"+
          "       s.SITE_REGION site_region, \n"+                                            //SVC
          "       s.SITE_MARKET site_market, \n"+                                            //SVC
          "       s.SITE_DISTRICT site_district, \n"+                                        //SVC
          "       s.SITE_TYPE site_type, \n"+                                                //SVC
          "       s.SITE_SQUARE_FOOTAGE site_square_footage, \n"+                            //SVC
          "       s.SITE_AVERAGE_CUSTOMER_COUNT site_average_customer_count, \n"+            //SVC
          "       s.SITE_BSC site_bsc, \n"+                                                  //SVC
          "       s.SITE_STREET_ADDRESS address, \n"+
          "       s.SITE_CITY site_city, \n"+                                                //SVC
          "       s.SITE_STATE site_state, \n"+                                              //SVC
          "       s.SITE_NUM site_budget_ref, \n"+
//          "       s.SITE_DIST_REF_CD site_dist_ref, \n"+
          "       ORDER_NUM confirm_num, \n"+
          "       REQUEST_PO_NUM po_num, \n"+
          "       d.CALENDAR_DATE order_date, \n"+
//          "       (select CATEGORY1 from dw_category_dim  \n where CATEGORY_DIM_ID = i.CATEGORY_DIM_ID) category, \n"+
//          "       f.DIST_SKU, \n"+
//          "       f.DIST_UOM, \n"+
//          "       f.DIST_PACK, \n"+
//          "       i.ITEM_SIZE, \n"+
//          "       i.MANUF_SKU, \n"+
//          "       m.MANUF_NAME, \n"+
//          "       d.DIST_NAME,  \n"+
//          "       i.ITEM_DESC, \n"+
          "       (select JD_CATEGORY1 from dw_category_dim  \n where CATEGORY_DIM_ID = i.CATEGORY_DIM_ID) category, \n"+
          "       id.JD_DIST_SKU as DIST_SKU, \n"+
          "       id.JD_DIST_UOM as DIST_UOM, \n"+
          "       id.JD_DIST_PACK as DIST_PACK, \n"+
          "       i.JD_ITEM_SIZE ITEM_SIZE, \n"+
          "       i.JD_MANUF_SKU as MANUF_SKU, \n"+
          "       m.JD_MANUF_NAME as MANUF_NAME, \n"+
          "       d.JD_DIST_NAME as DIST_NAME,  \n"+
          "       i.JD_ITEM_DESC as ITEM_DESC, \n"+

          "       SALE_TYPE, \n"+
          "       QUANTITY , \n"+
          "       QUANTITY * PRICE line_total, \n"+
  //        "       CURRENCY_CD, \n"+
          "       i.CLW_SKU sys_sku \n"+
          "  FROM dw_order_fact f, \n"+
          "       dw_manufacturer_dim m, \n"+
          "       dw_distributor_dim d, \n"+
          "       dw_item_distributor id, \n"+
          "       dw_account_dim a, \n"+
          "       dw_SITE_DIM s,  \n"+
          "       dw_ITEM_DIM i, \n"+
          "       dw_date_dim d \n"+
          "  WHERE    QUANTITY is not NULL AND PRICE is not NULL AND \n"+
          "           f.ACCOUNT_DIM_ID = a.ACCOUNT_DIM_ID AND \n"+
          "           f.SITE_DIM_ID = s.SITE_DIM_ID AND \n"+
          "           f.MANUFACTURER_DIM_ID = m.MANUFACTURER_DIM_ID AND \n"+
          "           f.DISTRIBUTOR_DIM_ID = d.DISTRIBUTOR_DIM_ID(+) AND \n"+
          "           f.DATE_DIM_ID = d. DATE_DIM_ID AND \n"+
          "           f.ITEM_DIM_ID = i.ITEM_DIM_ID AND \n"+
          "           f.ITEM_DIM_ID = id.ITEM_DIM_ID AND \n"+
          "           NVL(f.DISTRIBUTOR_DIM_ID,id.DISTRIBUTOR_DIM_ID) = id.DISTRIBUTOR_DIM_ID \n"+
          "      AND  f.DATE_DIM_ID in ( \n"+ dateCond + ") \n"+
          filterCond + " \n"+
          "ORDER BY  account_name, site_name, confirm_num DESC";

      log.info(".getTab2QueryResult() SQL:" + sql);

      Statement stmt = pCon.createStatement();

      double totalSum = 0;
      ResultSet rs = stmt.executeQuery(sql);
      ArrayList lines = new ArrayList();
      while (rs.next()) {
          ARecord record = new ARecord();

          String accName = rs.getString("account_name");
          String siteName = rs.getString("site_name");

          String siteRegion = rs.getString("site_region");
          String siteMarket = rs.getString("site_market");
          String siteDistrict = rs.getString("site_district");
          String siteType = rs.getString("site_type");
          String siteSquareFootage = rs.getString("site_square_footage");
          String siteAverageCustomerCount = rs.getString("site_average_customer_count");
          String siteBsc = rs.getString("site_bsc");

          String address = rs.getString("address");

          String siteCity = rs.getString("site_city");
          String siteState = rs.getString("site_state");

          String siteBudgetRef = rs.getString("site_budget_ref");
//          String siteDistRef = rs.getString("site_dist_ref");
          String confirmNum = rs.getString("confirm_num");
          String poNum = rs.getString("PO_NUM");
          Date orderDate = rs.getDate("order_date");
          String saleType = rs.getString("SALE_TYPE");
          String manufSku = rs.getString("MANUF_SKU");
          String manufName = rs.getString("MANUF_NAME");
          String category = rs.getString("category");
          String distSku = rs.getString("DIST_SKU");
          String itemDesc = rs.getString("ITEM_DESC");
          String distUom = rs.getString("DIST_UOM");
          String distPack = rs.getString("DIST_PACK");
          String itemSize = rs.getString("ITEM_SIZE");
          String distName = rs.getString("DIST_NAME");
          String sysSku = rs.getString("sys_sku");


          Integer quantity = new Integer(rs.getInt("quantity"));
          BigDecimal lineTotal = rs.getBigDecimal("line_total");
          totalSum += lineTotal.doubleValue();

          //String currencyCd = rs.getString("currency_cd");

          record.setAccountName(accName);
          record.setSiteName(siteName);

          record.setSiteRegion(siteRegion);
          record.setSiteMarket(siteMarket);
          record.setSiteDistrict(siteDistrict);
          record.setSiteType(siteType);
          record.setSiteSquareFootage(siteSquareFootage);
          record.setSiteAverageCustomerCount(siteAverageCustomerCount);
          record.setSiteBsc(siteBsc);

          record.setAddress(address);

          record.setSiteCity(siteCity);
          record.setSiteState(siteState);

          record.setSiteBudgetRef(siteBudgetRef);
//          record.setSiteDistRef(siteDistRef);
          record.setConfirmNum(confirmNum);
          record.setPoNum(poNum);
          record.setOrderDate(orderDate);
          record.setSaleType(saleType);
//          record.setCurrencyCd(currencyCd);
          record.setManufSku(manufSku);
          record.setManufacturer(manufName);
          record.setCategory(category);
          record.setDistSku(distSku);
          record.setProductName(itemDesc);
          record.setUom(distUom);
          record.setPack(distPack);
          record.setSize(itemSize);
          record.setDistributor(distName);
          record.setQuantity(quantity);
          record.setLineTotal(lineTotal);
          record.setSystemSku(sysSku);

          lines.add(record.toList2());


      }
      rs.close();
      stmt.close();

      // define Total record
      ARecord recordTotal = new ARecord();
      recordTotal.init();
      recordTotal.setAccountName(GRAND_TOTAL);
      recordTotal.setLineTotal(new BigDecimal(totalSum));
      lines.add(recordTotal.toList2());
      return lines;
    }
    protected HashMap createReportStyleDescriptor(){

         /*
        (String parm1, String parm2, String parm3, String parm4, short parm5, String parm6, String parm7, String parm8, String parm9, int[] parm10)
        */
         //int[] mergeRegion = new int[]{1,1,1,getReportHeader().size()};
         //<style name><value type><value format><font name><font size><font type><font color><background color><aliment><wrap flag><merge (not working)><data class NULL><width><scale>

         GenericReportStyleView colAcc = new GenericReportStyleView("COL_ACC","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[0],0 );
         GenericReportStyleView colSite = new GenericReportStyleView("COL_SITE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[1],0 );

         GenericReportStyleView colRegion = new GenericReportStyleView("COL_REGION","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[2],0 );
         GenericReportStyleView colMarket = new GenericReportStyleView("COL_MARKET","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[2],0 );
         GenericReportStyleView colDistrict = new GenericReportStyleView("COL_DISTRICT","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[2],0 );
         GenericReportStyleView colType = new GenericReportStyleView("COL_TYPE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[3],0 );
         GenericReportStyleView colSquareFootage = new GenericReportStyleView("COL_SQUARE_FOOTAGE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[2],0 );
         GenericReportStyleView colAverageCustomerCount = new GenericReportStyleView("COL_AVERAGE_CUSTOMER_COUNT","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[0],0 );
         GenericReportStyleView colBsc = new GenericReportStyleView("COL_BSC","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[1],0 );

         GenericReportStyleView colAddress = new GenericReportStyleView("COL_ADDRESS","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[4],0 );

         GenericReportStyleView colCity = new GenericReportStyleView("COL_CITY","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[5],0 );
         GenericReportStyleView colState = new GenericReportStyleView("COL_STATE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[3],0 );

         GenericReportStyleView colBudgetRef = new GenericReportStyleView("COL_BUDGET_REF","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );
//         GenericReportStyleView colDistrRef = new GenericReportStyleView("COL_DISTR_REF","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[4],0 );
         GenericReportStyleView colConfirm = new GenericReportStyleView("COL_CONFIRM_NUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[0],0 );
         GenericReportStyleView colPo = new GenericReportStyleView("COL_PO_NUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[6],0 );
         GenericReportStyleView colDate = new GenericReportStyleView("COL_DATE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );
         GenericReportStyleView colSaleType = new GenericReportStyleView("COL_SALE_TYPE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );

         GenericReportStyleView colTotal = new GenericReportStyleView("COL_TOTAL",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[11],0 );
         //GenericReportStyleView colTax = new GenericReportStyleView("COL_TAX",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[10],0 );
         //GenericReportStyleView colFreight = new GenericReportStyleView("COL_FREIGHT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[11],0 );
         //GenericReportStyleView colHand = new GenericReportStyleView("COL_HAND",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[12],0 );

         //GenericReportStyleView colCurr = new GenericReportStyleView("COL_CURR","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[13],0 );


         GenericReportStyleView colManufSku = new GenericReportStyleView("COL_MANUF_SKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[8],0 );
         GenericReportStyleView colManufName = new GenericReportStyleView("COL_MANUF_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[9],0 );
         GenericReportStyleView colCateg = new GenericReportStyleView("COL_CATEG","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[4],0 );
         GenericReportStyleView colDistSku = new GenericReportStyleView("COL_DIST_SKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[0],0 );
         GenericReportStyleView colProdName = new GenericReportStyleView("COL_PROD_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[10],0 );
         GenericReportStyleView colPack = new GenericReportStyleView("COL_PACK","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[3],0 );
         GenericReportStyleView colUom = new GenericReportStyleView("COL_UOM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[3],0 );
         GenericReportStyleView colSize = new GenericReportStyleView("COL_SIZE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );
         GenericReportStyleView colDistName = new GenericReportStyleView("COL_DIST_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );
         GenericReportStyleView colQuantity = new GenericReportStyleView("COL_QUANTITY",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[2],0 );
 //        GenericReportStyleView colPrice = new GenericReportStyleView("COL_PRICE",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[24],0 );
         GenericReportStyleView colSysSku = new GenericReportStyleView("COL_SYS_SKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );

         GenericReportStyleView typeAmountD = new GenericReportStyleView("TYPE_AMOUNT_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0.00",FONT_NAME,FONT_SIZE,null,null,null,null,false, null,null,null,0 );
         GenericReportStyleView typeQtyD = new GenericReportStyleView("TYPE_QTY_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,null,null,null,null,false, null,null,null,0 );
 //        GenericReportStyleView typeDateD = new GenericReportStyleView("TYPE_DATE",ReportingUtils.DATA_CATEGORY.DATE,null,FONT_NAME,FONT_SIZE,null,null,null,null,false, null,null,null,0 );

         GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
         reportDesc.setUserStyle(colAcc.getStyleName(), colAcc);
         reportDesc.setUserStyle(colSite.getStyleName(), colSite);

         reportDesc.setUserStyle(colRegion.getStyleName(), colRegion);
         reportDesc.setUserStyle(colMarket.getStyleName(), colMarket);
         reportDesc.setUserStyle(colDistrict.getStyleName(), colDistrict);
         reportDesc.setUserStyle(colType.getStyleName(), colType);
         reportDesc.setUserStyle(colSquareFootage.getStyleName(), colSquareFootage);
         reportDesc.setUserStyle(colAverageCustomerCount.getStyleName(), colAverageCustomerCount);
         reportDesc.setUserStyle(colBsc.getStyleName(), colBsc);

         reportDesc.setUserStyle(colAddress.getStyleName(), colAddress);

         reportDesc.setUserStyle(colCity.getStyleName(), colCity);
         reportDesc.setUserStyle(colState.getStyleName(), colState);

         reportDesc.setUserStyle(colBudgetRef.getStyleName(), colBudgetRef);
//         reportDesc.setUserStyle(colDistrRef.getStyleName(), colDistrRef);
         reportDesc.setUserStyle(colConfirm.getStyleName(), colConfirm);
         reportDesc.setUserStyle(colPo.getStyleName(), colPo);
         reportDesc.setUserStyle(colDate.getStyleName(), colDate);
         reportDesc.setUserStyle(colSaleType.getStyleName(), colSaleType);
         reportDesc.setUserStyle(colTotal.getStyleName(), colTotal);
//         reportDesc.setUserStyle(colTax.getStyleName(), colTax);
//         reportDesc.setUserStyle(colFreight.getStyleName(), colFreight);
//         reportDesc.setUserStyle(colHand.getStyleName(), colHand);
//         reportDesc.setUserStyle(colCurr.getStyleName(), colCurr);
         reportDesc.setUserStyle(colManufSku.getStyleName(), colManufSku);
         reportDesc.setUserStyle(colManufName.getStyleName(), colManufName);
         reportDesc.setUserStyle(colDistSku.getStyleName(), colDistSku);
         reportDesc.setUserStyle(colDistName.getStyleName(), colDistName);
         reportDesc.setUserStyle(colProdName.getStyleName(), colProdName);
         reportDesc.setUserStyle(colSysSku.getStyleName(), colSysSku);
         reportDesc.setUserStyle(colQuantity.getStyleName(), colQuantity);
//         reportDesc.setUserStyle(colPrice.getStyleName(), colPrice);
         reportDesc.setUserStyle(colPack.getStyleName(), colPack);
         reportDesc.setUserStyle(colUom.getStyleName(), colUom);
         reportDesc.setUserStyle(colSize.getStyleName(), colSize);
         reportDesc.setUserStyle(typeAmountD.getStyleName(), typeAmountD);
         reportDesc.setUserStyle(typeQtyD.getStyleName(), typeQtyD);
//         reportDesc.setUserStyle(typeDateD.getStyleName(), typeDateD);

         HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
         return styleDesc;
       }

    private GenericReportColumnViewVector getReportHeader1() {
          GenericReportColumnViewVector header = new GenericReportColumnViewVector();
          header.add(ReportingUtils.createGenericReportColumnView("Account Name", "COL_ACC", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("Site Name",  "COL_SITE", null,COL_WIDTH[1]));

          header.add(ReportingUtils.createGenericReportColumnView("Region",  "COL_REGION", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("Market",  "COL_MARKET", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("District",  "COL_DISTRICT", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("Type",  "COL_TYPE", null,COL_WIDTH[3]));
          header.add(ReportingUtils.createGenericReportColumnView("Square Footage",  "COL_SQUARE_FOOTAGE", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("Average Customer Count",  "COL_AVERAGE_CFUSTOMER_COUNT", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("BSC",  "COL_BSC", null,COL_WIDTH[1]));

          header.add(ReportingUtils.createGenericReportColumnView("Address1", "COL_ADDRESS", null,COL_WIDTH[4]));

          header.add(ReportingUtils.createGenericReportColumnView("City",  "COL_CITY", null,COL_WIDTH[5]));
          header.add(ReportingUtils.createGenericReportColumnView("State",  "COL_STATE", null,COL_WIDTH[3]));

          header.add(ReportingUtils.createGenericReportColumnView("Site Budget Ref Number", "COL_BUDGET_REF", null,COL_WIDTH[7]));
//          header.add(ReportingUtils.createGenericReportColumnView("Distributor Reference Code",  "COL_DISTR_REF", null,COL_WIDTH[4]));
          header.add(ReportingUtils.createGenericReportColumnView("Confirmation Number", "COL_CONFIRM_NUM", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("PO Number", "COL_PO_NUM", null,COL_WIDTH[6]));
          header.add(ReportingUtils.createGenericReportColumnView("Order Date", "COL_DATE", ReportingUtils.DATE_STYLE,COL_WIDTH[7]));
          header.add(ReportingUtils.createGenericReportColumnView("Sale Type", "COL_SALE_TYPE", null,COL_WIDTH[11]));

          header.add(ReportingUtils.createGenericReportColumnView("Order Total", "COL_TOTAL","TYPE_AMOUNT_DATA",COL_WIDTH[11]));
//          header.add(ReportingUtils.createGenericReportColumnView("Tax", "COL_TAX","TYPE_AMOUNT_DATA",COL_WIDTH[10]));
//          header.add(ReportingUtils.createGenericReportColumnView("Freight","COL_FREIGHT","TYPE_AMOUNT_DATA",COL_WIDTH[11]));
//          header.add(ReportingUtils.createGenericReportColumnView("Handling","COL_HAND","TYPE_AMOUNT_DATA",COL_WIDTH[12]));
//          header.add(ReportingUtils.createGenericReportColumnView("Currency Code","COL_CURR",null,COL_WIDTH[13]));

        return header;
    }

    private GenericReportColumnViewVector getReportHeader2() {
          GenericReportColumnViewVector header = new GenericReportColumnViewVector();
          header.add(ReportingUtils.createGenericReportColumnView("Account Name", "COL_ACC", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("Site Name",  "COL_SITE", null,COL_WIDTH[1]));

          header.add(ReportingUtils.createGenericReportColumnView("Region",  "COL_REGION", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("Market",  "COL_MARKET", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("District",  "COL_DISTRICT", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("Type",  "COL_TYPE", null,COL_WIDTH[3]));
          header.add(ReportingUtils.createGenericReportColumnView("Square Footage",  "COL_SQUARE_FOOTAGE", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("Average Customer Count",  "COL_AVERAGE_CFUSTOMER_COUNT", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("BSC",  "COL_BSC", null,COL_WIDTH[1]));

          header.add(ReportingUtils.createGenericReportColumnView("Address1", "COL_ADDRESS", null,COL_WIDTH[4]));

          header.add(ReportingUtils.createGenericReportColumnView("City",  "COL_CITY", null,COL_WIDTH[5]));
          header.add(ReportingUtils.createGenericReportColumnView("State",  "COL_STATE", null,COL_WIDTH[3]));

          header.add(ReportingUtils.createGenericReportColumnView("Site Budget Ref Number", "COL_BUDGET_REF", null,COL_WIDTH[7]));
//          header.add(ReportingUtils.createGenericReportColumnView("Distributor Reference Code",  "COL_DISTR_REF", null,COL_WIDTH[4]));
          header.add(ReportingUtils.createGenericReportColumnView("Confirmation Number", "COL_CONFIRM_NUM", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("PO Number", "COL_PO_NUM", null,COL_WIDTH[6]));
          header.add(ReportingUtils.createGenericReportColumnView("Order Date", "COL_DATE",  ReportingUtils.DATE_STYLE,COL_WIDTH[7]));
          header.add(ReportingUtils.createGenericReportColumnView("Category", "COL_CATEG", null,COL_WIDTH[4]));
          header.add(ReportingUtils.createGenericReportColumnView("Distributor SKU", "COL_DIST_SKU", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("UOM", "COL_UOM", null,COL_WIDTH[3]));
          header.add(ReportingUtils.createGenericReportColumnView("Pack", "COL_PACK", null,COL_WIDTH[3]));
          header.add(ReportingUtils.createGenericReportColumnView("Item Size", "COL_SIZE", null,COL_WIDTH[7]));
          header.add(ReportingUtils.createGenericReportColumnView("Manufacturer SKU", "COL_MANUF_SKU", null,COL_WIDTH[8]));
          header.add(ReportingUtils.createGenericReportColumnView("Manufacturer", "COL_MANUF_NAME", null,COL_WIDTH[9]));
          header.add(ReportingUtils.createGenericReportColumnView("Distributor", "COL_DIST_NAME", null,COL_WIDTH[7]));
          header.add(ReportingUtils.createGenericReportColumnView("Product Name", "COL_PROD_NAME", null,COL_WIDTH[10]));
          header.add(ReportingUtils.createGenericReportColumnView("Sale Type", "COL_SALE_TYPE", null,COL_WIDTH[7]));

          header.add(ReportingUtils.createGenericReportColumnView("Quantity", "COL_QUANTITY","TYPE_QTY_DATA",COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("Line Total", "COL_TOTAL","TYPE_AMOUNT_DATA",COL_WIDTH[7]));
//          header.add(ReportingUtils.createGenericReportColumnView("Currency Code","COL_CURR",null,COL_WIDTH[13]));
          header.add(ReportingUtils.createGenericReportColumnView("System SKU","COL_SYS_SKU","TYPE_PERCENT_DATA",COL_WIDTH[7]));

        return header;
    }


    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",pTitle,0,255,"VARCHAR2"));
      String controlLabel = null;
      String controlName = null;

      controlLabel = ReportingUtils.getControlLabel(STORE_S, pParams, "Store");
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", controlLabel + getListOfNames(con, this.STORE_S , pParams),0,255,"VARCHAR2"));

      String controlLabelB = ReportingUtils.getControlLabel(BEG_DATE_S, pParams, "Date Begin");
      String controlLabelE = ReportingUtils.getControlLabel(END_DATE_S, pParams, "End");
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabelB + (String) pParams.get(BEG_DATE_S) + "; " + controlLabelE + (String) pParams.get(END_DATE_S),0,255,"VARCHAR2"));

      controlName = this.LOCATE_ACCOUNT_MULTI_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Accounts");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }

      controlName = this.LOCATE_SITE_MULTI_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Sites");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }

      controlName = this.CATEGORY_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Category");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = CATEGORY_AUTOSUGGEST_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category : " + pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = this.LOCATE_ITEM_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Items");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }
      controlName = this.LOCATE_MANUFACTURER_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Manufacturers");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }
      controlName = this.LOCATE_DISTRIBUTOR_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Distributors");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }

      return title;
    }

    protected String  getListOfNames (Connection con, String controlName, Map pParams){
     Object listOfIds = pParams.get(controlName);
     IdVector ids = new IdVector();
     String idsS = null;
     if (listOfIds instanceof List) {
       ids = (IdVector) ( (ArrayList) listOfIds).clone();
       idsS = IdVector.toCommaString(ids);
     }
     if (listOfIds instanceof String) {
       idsS = (String) listOfIds;
     }
     boolean orderby = false;
     StringBuffer nms = new StringBuffer();
     String typeDim = "";
     String colName ="";
     if (controlName.equals(STORE_S)){
       typeDim = "STORE";
       colName = typeDim + "_NAME";
     } else if (controlName.equals(this.LOCATE_ACCOUNT_MULTI_OPT_S)) {
       typeDim = "ACCOUNT";
       colName = typeDim + "_NAME";
       orderby = true;
     } else if (controlName.equals(this.LOCATE_SITE_MULTI_OPT_S)) {
         typeDim = "SITE";
         colName = typeDim + "_NAME";
         orderby = true;
     } else if (controlName.equals(this.LOCATE_ITEM_OPT_S)) {
         typeDim = "ITEM";
         colName = typeDim + "_DESC";
     } else if (controlName.equals(this.LOCATE_MANUFACTURER_OPT_S)) {
           typeDim = "MANUFACTURER";
           colName = "MANUF" + "_NAME";
     } else if (controlName.equals(this.LOCATE_DISTRIBUTOR_OPT_S)) {
             typeDim = "DISTRIBUTOR";
             colName = "DIST" + "_NAME";
     }
     if (idsS.length() != 0) {
       try {

         String sql =
             " select " + colName + " from DW_" + typeDim + "_DIM where " + typeDim + "_DIM_ID \n" +
             " in (" + idsS + ")";
         if(orderby){
        	 sql = sql + " order by " + colName;
         }

         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
           String be = new String(rs.getString(1));
           if (nms.length() == 0) {
             nms.append(be);
           }
           else {
             nms.append(", " + be);
           }
         }
         stmt.close();
       }
       catch (SQLException exc) {
         exc.printStackTrace();
       }
     }
     return nms.toString();
   }



  protected Object putCellStyle(String style, Object obj) {
    if (style == null || obj == null) {
      return obj;
    }
    HashMap map = new HashMap();
    map.put(style, obj);
    return map;
  }


 private String getCategoryFilter(Map pParams) {

        String categoryStr = (String) pParams.get(CATEGORY_OPT_S);

        if (Utility.isSet(categoryStr)) {
            return categoryStr;
        } else {
            String autoSuggCategStr = (String) pParams.get(CATEGORY_AUTOSUGGEST_OPT_S);
            if (Utility.isSet(autoSuggCategStr)) {
                return autoSuggCategStr;
            } else {
                return categoryStr;
            }
        }
    }

    protected String createFilterCondition(Map pParams) {

      String storeFilter = getParamValue(pParams, this.STORE_S);
      String accountFilter = getParamValue(pParams, LOCATE_ACCOUNT_MULTI_OPT_S);
      String siteFilter  = getParamValue(pParams, LOCATE_SITE_MULTI_OPT_S);

      String manufFilter  = getParamValue(pParams, LOCATE_MANUFACTURER_OPT_S);
      String distributorFilter  = getParamValue(pParams, LOCATE_DISTRIBUTOR_OPT_S);
      String categoryFilter  = getParamValue(pParams, CATEGORY_OPT_S);
      String itemFilter  = getParamValue(pParams, LOCATE_ITEM_OPT_S);

      boolean accountFl = Utility.isSet(accountFilter);
      boolean siteFl = Utility.isSet(siteFilter);
      boolean manufFl = Utility.isSet(manufFilter);
      boolean distFl = Utility.isSet(distributorFilter);
      boolean categoryFl = Utility.isSet(categoryFilter);
      boolean itemFl = Utility.isSet(itemFilter);

      String storeCondStr =  " AND f.STORE_DIM_ID =   " + storeFilter + " \n" ;
      String categoryCondStr = (!categoryFl) ? "" :
 //                      " SELECT CATEGORY_DIM_ID FROM DW_CATEGORY_DIM WHERE \n" +
 //                      " UPPER(NVL(jd_category3, NVL(jd_category2,jd_category1))) like '%" +
 //                       categoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
                    "SELECT ITEM_DIM_ID FROM DW_ITEM_DIM i, DW_CATEGORY_DIM c \n" +
                    "WHERE i.CATEGORY_DIM_ID = c.CATEGORY_DIM_ID  \n" +
                    "  AND i.STORE_DIM_ID =   " + storeFilter + " \n" +
//                    "  AND UPPER(NVL(jd_category3, NVL(jd_category2,jd_category1))) like '%" + // commented temporary until jd_category1 is populated (not NULL)
                    "  AND UPPER(category1) like '%" +
                    categoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";


      String filterCond = storeCondStr +
          (accountFl?" and f.ACCOUNT_DIM_ID in ( \n" + accountFilter + ") \n":"")+
          (siteFl?" and f.SITE_DIM_ID in ( \n" + siteFilter + ") \n":"")+
          (itemFl?" and f.ITEM_DIM_ID in ( \n" + itemFilter + ") \n":"")+
          (manufFl?" and f.MANUFACTURER_DIM_ID in ( \n" + manufFilter + ") \n":"")+
//          (categoryFl?" and f.CATEGORY_DIM_ID in ( \n" + categoryCondStr + ") \n":"")+
          (categoryFl?" and f.ITEM_DIM_ID in ( \n" + categoryCondStr + ") \n":"")+
          (distFl?" and f.DISTRIBUTOR_DIM_ID in ( \n" + distributorFilter + ") \n":"");

      return filterCond;
    }

    private String getParamValue (Map pParams, String pControlName){
      String value = null;
      if (pParams.containsKey(pControlName) &&  Utility.isSet( (String) pParams.get(pControlName))) {
        value = (String) pParams.get(pControlName);
      }
      return value;
    }


    protected String createDateSqlCond(String begDateStr, String endDateStr, int yearOffset) throws Exception {
      GregorianCalendar currBegCalendar = new GregorianCalendar();
      currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

      GregorianCalendar currEndCalendar = new GregorianCalendar();
      currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

      String startDateS = ReportingUtils.toSQLDate(currBegCalendar.getTime());
      String endDateS = ReportingUtils.toSQLDate(currEndCalendar.getTime());

      if (yearOffset != 0) {
        GregorianCalendar priorBegCalendar = (GregorianCalendar) currBegCalendar.clone();
        priorBegCalendar.add(GregorianCalendar.YEAR, yearOffset);

        GregorianCalendar priorEndCalendar = (GregorianCalendar) currEndCalendar.clone();
        priorEndCalendar.add(GregorianCalendar.YEAR, yearOffset);

        startDateS = ReportingUtils.toSQLDate(priorBegCalendar.getTime());
        endDateS = ReportingUtils.toSQLDate(priorEndCalendar.getTime());
      }
       String  subSqlForDatePeriod  = " select DATE_DIM_ID from DW_DATE_DIM where CALENDAR_DATE between "+ startDateS+" and "+endDateS ;

       return subSqlForDatePeriod;
    }

    protected int getFreezeColumns() {
      return 1;
    }

   private class ARecord //implements Record, java.io.Serializable
    {

        private String accountName;
        private String siteName;

        private String siteMarket;
        private String siteRegion;
        private String siteDistrict;
        private String siteType;
        private String siteSquareFootage;
        private String siteAverageCustomerCount;
        private String siteBsc;

        private String address;

        private String siteCity;
        private String siteState;

        private String siteBudgetRef;
//        private String siteDistRef;
        private String confirmNum;
        private String poNum;
        private String saleType;
        private BigDecimal orderSubtotal;
//        private BigDecimal tax;
//        private BigDecimal freight;
//        private BigDecimal handling;
//        private String currencyCd;
        private Date orderDate;

         private String manufSku;
         private String manufacturer;
         private String distSku;
         private String productName;
         private String category;
         private String pack;
         private String uom;
         private String size;
         private String distributor;
         private Integer quantity;
         private BigDecimal lineTotal ;
         private String systemSku;
         private String groupByFld;

        public List toList1() {
            ArrayList list = new ArrayList();

            String style = null;
            if (accountName != null && (accountName.equals(GRAND_TOTAL) ) ){
              style = BOLD_STYLE;
            }

            list.add(putCellStyle(style,accountName));
            list.add(siteName);

            list.add(siteRegion);
            list.add(siteMarket);
            list.add(siteDistrict);
            list.add(siteType);
            list.add(siteSquareFootage);
            list.add(siteAverageCustomerCount);
            list.add(siteBsc);

            list.add(address);

            list.add(siteCity);
            list.add(siteState);

            list.add(siteBudgetRef);
//            list.add(siteDistRef);
            list.add(confirmNum);
            list.add(poNum);
            list.add(orderDate);
            list.add(saleType);
            list.add(putCellStyle(style,orderSubtotal));
//            list.add(tax);
//            list.add(freight);
//            list.add(handling);
//            list.add(currencyCd);
             return list;
        }
        public List toList2() {
            ArrayList list = new ArrayList();

            String style = null;
            if (accountName != null && (accountName.equals(GRAND_TOTAL) ) ){
              style = BOLD_STYLE;
            }

            list.add(putCellStyle(style,accountName));
            list.add(siteName);

            list.add(siteRegion);
            list.add(siteMarket);
            list.add(siteDistrict);
            list.add(siteType);
            list.add(siteSquareFootage);
            list.add(siteAverageCustomerCount);
            list.add(siteBsc);

            list.add(address);

            list.add(siteCity);
            list.add(siteState);

            list.add(siteBudgetRef);
//            list.add(siteDistRef);
            list.add(confirmNum);
            list.add(poNum);
            list.add(orderDate);
            list.add(category);
            list.add(distSku);
            list.add(uom);
            list.add(pack);
            list.add(size);
            list.add(manufSku);
            list.add(manufacturer);
            list.add(distributor);
            list.add(productName);
            list.add(saleType);
            list.add(quantity);
            list.add(putCellStyle(style,lineTotal));
//            list.add(currencyCd);
            list.add(systemSku);
             return list;
        }

        public void init(){
         orderSubtotal = new BigDecimal(0);
         lineTotal = new BigDecimal(0);
        }

        public void sum(ARecord record) {
          if (record.getOrderSubtotal()!=null){
            orderSubtotal = new BigDecimal(orderSubtotal.intValue() +record.getOrderSubtotal().intValue());
          }
        }

        public void calculate(){

       }


        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getSiteName() {
           return siteName;
       }

       public void setSiteName(String siteName) {
           this.siteName = siteName;
       }

       public String getSiteMarket() {
           return siteMarket;
       }

       public void setSiteMarket(String siteMarket) {
           this.siteMarket = siteMarket;
       }

       public String getSiteRegion() {
           return siteRegion;
       }

       public void setSiteRegion(String siteRegion) {
           this.siteRegion = siteRegion;
       }

       public String getSiteDistrict() {
           return siteDistrict;
       }

       public void setSiteDistrict(String siteDistrict) {
           this.siteDistrict = siteDistrict;
       }

       public String getSiteType() {
           return siteType;
       }

       public void setSiteType(String siteType) {
           this.siteType = siteType;
       }

       public String getSiteSquareFootage() {
           return siteSquareFootage;
       }

       public void setSiteSquareFootage(String siteSquareFootage) {
           this.siteSquareFootage = siteSquareFootage;
       }

       public String getSiteAverageCustomerCount() {
           return siteAverageCustomerCount;
       }

       public void setSiteAverageCustomerCount(String siteAverageCustomerCount) {
           this.siteAverageCustomerCount = siteAverageCustomerCount;
       }

       public String getSiteBsc() {
           return siteBsc;
       }

       public void setSiteBsc(String siteBsc) {
           this.siteBsc = siteBsc;
       }

       public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
        public String getCategory() {
              return category;
        }

        public void setCategory(String category) {
              this.category = category;
       }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }
        public String getDistributor() {
            return distributor;
        }

        public void setDistributor(String distributor) {
            this.distributor = distributor;
        }

        public String getDistSku() {
            return distSku;
        }

        public void setDistSku(String distSku) {
            this.distSku = distSku;
        }
        public String getManufrSku() {
            return manufSku;
        }

        public void setManufSku(String manufSku) {
            this.manufSku = manufSku;
        }
        public String getSystemSku() {
             return systemSku;
         }

         public void setSystemSku(String systemSku) {
             this.systemSku = systemSku;
         }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getSiteBudgetRef() {
            return siteBudgetRef;
        }

        public void setSiteBudgetRef(String siteBudgetRef) {
            this.siteBudgetRef = siteBudgetRef;
        }

        /***
        public String getSiteDistRef() {
            return siteDistRef;
        }

        public void setSiteDistRef(String siteDistRef) {
            this.siteDistRef = siteDistRef;
        }
        ***/

        public String getConfirmNum() {
            return confirmNum;
        }

        public void setConfirmNum(String confirmNum) {
            this.confirmNum = confirmNum;
        }

        public String getPoNum() {
            return poNum;
        }

        public void setPoNum(String poNum) {
            this.poNum = poNum;
        }

        public String getSaleType() {
            return saleType;
        }

        public void setSaleType(String saleType) {
            this.saleType = saleType;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }


        public BigDecimal getOrderSubtotal() {
            return orderSubtotal;
        }

        public void setOrderSubtotal(BigDecimal orderSubtotal) {
            this.orderSubtotal = orderSubtotal;
        }
        public BigDecimal getLineTotal() {
            return lineTotal;
        }

        public void setLineTotal(BigDecimal lineTotal ) {
            this.lineTotal = lineTotal ;
        }

        /***
        public BigDecimal getTax() {
            return tax;
        }

        public void setTax(BigDecimal tax) {
            this.tax = tax;
        }

        public BigDecimal getFreight() {
            return freight;
        }

        public void setFreight(BigDecimal freight) {
            this.freight = freight;
        }
        public BigDecimal getHandling() {
            return handling;
        }

        public void setHandling(BigDecimal handling) {
            this.handling = handling;
        }
        ***/

         public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }
        public String getUom() {
            return uom;
        }

        public void setUom(String uom) {
            this.uom = uom;
        }
        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
        /***
        public String getCurrencyCd() {
            return currencyCd;
        }

        public void setCurrencyCd(String currencyCd) {
            this.currencyCd = currencyCd;
        }
        ***/
        public Date getOrderDate() {
          return orderDate;
        }

        public void setOrderDate(Date orderDate) {
          this.orderDate = orderDate;
        }

        public String getGroupByFld() {
            return groupByFld;
        }

        public void setGroupByFld(String groupByFld) {
            this.groupByFld = groupByFld;
        }

    }
    public class GenericReportUserStyleDesc extends ValueObject {
      HashMap mReportUserStyle;

      public GenericReportUserStyleDesc() {
        mReportUserStyle = new HashMap();
      }

      public void setUserStyle(String pUserStyleType,
                               GenericReportStyleView pPageTitleStyle) {
        if (mReportUserStyle == null) {
          mReportUserStyle = new HashMap();
        }
        mReportUserStyle.put(pUserStyleType, pPageTitleStyle);
      }

      public GenericReportStyleView getUserStyle(String pUserStyleType) {
        return ( (mReportUserStyle != null) ?
                (GenericReportStyleView) mReportUserStyle.get(pUserStyleType) : null);
      }

      public HashMap getGenericReportUserStyleDesc() {
        return mReportUserStyle;
      }

      public void setGenericReportUserStyleDesc(HashMap pReportUserStyle) {
        mReportUserStyle = pReportUserStyle;
      }
    }

}

