/*
 * PurchBySiteJDReport.java
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
import java.text.SimpleDateFormat;
import java.sql.*;
import java.sql.Connection;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import com.cleanwise.service.api.framework.ValueObject;
import java.util.*;
import com.cleanwise.service.api.util.Utility;

/**
 * Picks up orders and agreates it on SKU & Subcategories
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 * @param pAccountId account Id optionally,
 */
public class PurchBySKUSubcatJDReportDW  extends BaseJDReportDW implements GenericReportMulti {
//PurchBySiteJDReportDW
    /** Creates a new instance of DistributorInvoiceProfitReport */
    public PurchBySKUSubcatJDReportDW() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    protected static final String SKU_NUM = "SKU Number";
    protected static final String SKU_NAME = "SKU Name";
    protected static final String SUB_CATEGORY = "Category";
    protected static final String PACK = "Pack";
    protected static final String UOM = "UOM";
    protected static final String SIZE = "Size";
    protected static final String PRICE_COL = "QUANTITY * PRICE";
    protected static final String QTY_COL   = "QUANTITY";
    protected static final String COST_COL  = "PRICE";


      protected GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView(this.SKU_NUM, "COL_SKU_NUM", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.SKU_NAME, "COL_SKU_NAME", null,"46"));

        header.add(ReportingUtils.createGenericReportColumnView(this.PACK, "COL_PACK", null,"6"));
        header.add(ReportingUtils.createGenericReportColumnView(this.UOM, "COL_UOM", null,"6"));
        header.add(ReportingUtils.createGenericReportColumnView(this.SIZE, "COL_SIZE", null,"12"));

        header.add(ReportingUtils.createGenericReportColumnView(this.SUB_CATEGORY, "COL_SITE", null,COL_WIDTH[3]));
        header.add(ReportingUtils.createGenericReportColumnView(this.CURRENT_NET_SALES+" $", "COL_CUR_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[4]));
        header.add(ReportingUtils.createGenericReportColumnView(this.PRIOR_NET_SALES+" $", "COL_PRI_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[5]));
        header.add(ReportingUtils.createGenericReportColumnView(this.PROC_OF_TOTAL,"COL_PROC_TOT","TYPE_PERCENT_DATA",COL_WIDTH[6]));
        header.add(ReportingUtils.createGenericReportColumnView(this.NET_SALES_GROWTH,"COL_SALES_GR","TYPE_PERCENT_DATA",COL_WIDTH[7]));
        header.add(ReportingUtils.createGenericReportColumnView(this.CURRENT_UNITS,"COL_CUR_UNIT","TYPE_QTY_DATA",COL_WIDTH[8]));
        header.add(ReportingUtils.createGenericReportColumnView(this.PRIOR_UNITS, "COL_PRI_UNIT","TYPE_QTY_DATA",COL_WIDTH[9]));
        header.add(ReportingUtils.createGenericReportColumnView(this.UNIT_GROWTH,"COL_UNIT_GR","TYPE_PERCENT_DATA",COL_WIDTH[10]));


        return header;
    }

    protected int getFreezeColumns() {
     return 2;
   }


    protected void addRowToReport(ArrayList row , Map reportRowMap, JDReportView currYItem) {
      boolean totalRowFlag = (((String)reportRowMap.get(this.GROUPBY)).equals(GRAND_TOTAL )) ? true : false;
      String style = null;
      if (currYItem == null && totalRowFlag ) {
         style = BOLD_STYLE;
         row.add(putCellStyle(style,reportRowMap.get(this.GROUPBY)));
         row.add(putCellStyle(style,""));
         row.add(putCellStyle(style,""));
         row.add(putCellStyle(style,""));
         row.add(putCellStyle(style,""));
         row.add(putCellStyle(style,""));
       } else {
         row.add(putCellStyle(style,currYItem.getSku()));
         row.add(putCellStyle(style,currYItem.getSkuName()));
         row.add(putCellStyle(style,currYItem.getPack()));
         row.add(putCellStyle(style,currYItem.getUom()));
         row.add(putCellStyle(style,currYItem.getSize()));
         row.add(putCellStyle(style,currYItem.getSubCat()));
       }
      row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_NET_SALES)));
      row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_NET_SALES)));
      row.add(putCellStyle(style,reportRowMap.get(this.PROC_OF_TOTAL)));
      row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.NET_SALES_GROWTH))));
      row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_UNITS)));
      row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_UNITS)));
      row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.UNIT_GROWTH))));

    }

    protected ArrayList getResultOfQuery(Connection conn,  Map pParams) throws Exception{
      int storeId = Integer.parseInt((String) pParams.get(STORE_S));

      BigDecimal zeroAmt = new BigDecimal(0);

      // for accounts if there were sales in "Current Year" and may be there were no sales in "Prior Year"
      String selectSqlCurrY = getQuerySql(  pParams);//getQuerySqlForCurrentY(selectSqlSites);
      // for accounts if there were sales in "Prior Year" and there were no sales in "Current Year"
//      String selectSqlPriorY = getQuerySqlForPriorY(selectSqlSites);

      String selectSql =
          "select \n" +
          "   JD_MANUF_SKU SKU_NUM, \n" +
          "   JD_ITEM_DESC SKU_NAME, \n" +
          "   JD_ITEM_PACK PACK, \n" +
          "   JD_ITEM_UOM UOM, \n" +
          "   JD_ITEM_SIZE ITEM_SIZE, \n" +
          "   (select NVL(jd_category3, NVL(jd_category2,jd_category1)) from DW_CATEGORY_DIM where dw.category_dim_id = category_dim_id) AS CATEGORY, \n" +
          "   g.* \n"+
          " from DW_ITEM_DIM dw, \n" +
          "  (" +
            selectSqlCurrY +
            ") g \n" +
          " where  g.item_dim_id = dw.item_dim_id  \n" +
          " order by nvl(SUM_PRICE,0) desc, SKU_NUM \n";

      PreparedStatement pstmt = conn.prepareStatement(selectSql);

      ResultSet rs = pstmt.executeQuery();
      JDReportViewVector psViewV= new JDReportViewVector();
      while (rs.next()){
        JDReportView psView = new JDReportView ();
//        String itemId = (rs.getString("ITEM_ID") != null) ? rs.getString("ITEM_ID") : "0";
        String sku = (rs.getString("SKU_NUM") != null) ? rs.getString("SKU_NUM") : "0";
        String skuName = (rs.getString("SKU_NAME") != null) ? rs.getString("SKU_NAME") : "";
        BigDecimal sumPrice = (rs.getBigDecimal("SUM_PRICE") != null) ? rs.getBigDecimal("SUM_PRICE") : zeroAmt;
        BigDecimal sumQty   = (rs.getBigDecimal("SUM_QTY") != null) ? rs.getBigDecimal("SUM_QTY") : zeroAmt;
        BigDecimal sumPricePre = (rs.getBigDecimal("PRIOR_SUM_PRICE") != null) ? rs.getBigDecimal("PRIOR_SUM_PRICE") : zeroAmt;
        BigDecimal sumQtyPre   = (rs.getBigDecimal("PRIOR_SUM_QTY") != null) ? rs.getBigDecimal("PRIOR_SUM_QTY") : zeroAmt;
        String pack = (rs.getString("PACK") != null) ? rs.getString("PACK") : "";
        String uom = (rs.getString("UOM") != null) ? rs.getString("UOM") : "";
        String size = (rs.getString("ITEM_SIZE") != null) ? rs.getString("ITEM_SIZE") : "";
        String category = (rs.getString("CATEGORY") != null) ? rs.getString("CATEGORY") : "";
        psView.setSku(sku);
        psView.setSkuName(skuName);
        psView.setSubCat(category);
        psView.setPack(pack);
        psView.setUom(uom);
        psView.setSize(size);

        psView.setSumPrice(sumPrice);
        psView.setSumQty(sumQty);
        psView.setSumPricePre(sumPricePre);
        psView.setSumQtyPre(sumQtyPre);
        psViewV.add(psView);
      }
      pstmt.close();
      rs.close();
      psViewV.sort("SumPrice", false);
      return psViewV;

    }

    protected String getQuerySql( Map pParams) throws Exception {
      String filterCond = createFilterCondition(pParams);
      String begDateStr = (String) pParams.get(BEG_DATE_S);
      String endDateStr = (String) pParams.get(END_DATE_S);

      //======================================================================

      String  subSqlForCurrPeriod  = createDateSqlCond( begDateStr, endDateStr, 0);
      String  subSqlForPriorPeriod = createDateSqlCond( begDateStr, endDateStr, -1);

      String subSqlJdItems =
          " select ITEM_DIM_ID from DW_ITEM_DIM where UPPER(JD_ITEM_FL) = 'TRUE' \n" ;

     String sql=
        " select ITEM_DIM_ID,\n" +
        "        sum("+ PRICE_COL +") SUM_PRICE, \n" +
        "        sum("+ QTY_COL +") SUM_QTY,  \n" +
        "         0 PRIOR_SUM_PRICE, \n" +
        "         0 PRIOR_SUM_QTY \n" +
        "  from DW_ORDER_FACT \n"+
        "    where  DATE_DIM_ID in ( " + subSqlForCurrPeriod + " ) \n" +
       filterCond +
        "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
        "    group by ITEM_DIM_ID \n" +
        " union all \n" +
        "select ITEM_DIM_ID, \n" +
        "        0 SUM_PRICE, \n" +
        "        0 SUM_QTY,  \n" +
        "        sum("+ PRICE_COL +") PRIOR_SUM_PRICE, \n" +
        "        sum("+ QTY_COL +")  PRIOR_SUM_QTY \n" +
        "  from DW_ORDER_FACT \n"+
       "    where  DATE_DIM_ID in ( " + subSqlForPriorPeriod + " ) \n" +
       filterCond +
        "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
       "    group by ITEM_DIM_ID \n" ;

         return sql;
     }
}
