/*
 * DistrPerfByRegionJDReport.java
 *
 * Created on September 28, 2008, 10:12 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
/**
 * Picks up distributor invoices and agreates it on Vertical ( account type)
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 *
 */
public class DistrPerfByVerticalJDReport extends DistrPerfByAccountJDReport implements GenericReportMulti {
  protected static final String VERTICAL = "Vertical Market";


protected DistrPerfJPReportViewVector getResultOfQuery(Connection conn, Map pParams ) throws Exception{
  BigDecimal zeroAmt = new BigDecimal(0);
  String selectSqlAccount= getQuerySql( pParams );

  // for accounts if there were sales in "Current Year" and may be there were no sales in "Prior Year"
  String selectSqlCurrY = getQuerySqlForCurrentY(selectSqlAccount);
  // for accounts if there were sales in "Prior Year" and there were no sales in "Current Year"
  String   selectSqlPriorY = getQuerySqlForPriorY(selectSqlAccount);

  String selectSql =
      "select \n" +
 //     "   VERTICAL  VERTICAL_NAME, \n" +
      "   g.* \n"+
      " from \n" +
      "  (" +
        selectSqlCurrY +
      " union  \n" +
        selectSqlPriorY +
      ") g \n" +
      " order by nvl(SUM_PRICE,0) desc, VERTICAL_NAME \n";

  PreparedStatement pstmt = conn.prepareStatement(selectSql);

  ResultSet rs = pstmt.executeQuery();
  DistrPerfJPReportViewVector psViewV= new DistrPerfJPReportViewVector();
  while (rs.next()){
    DistrPerfJPReportView psView = new DistrPerfJPReportView ();
    String verticalName = (rs.getString("VERTICAL_NAME") != null) ? rs.getString("VERTICAL_NAME") : "";
    BigDecimal sumPrice = (rs.getBigDecimal("SUM_PRICE") != null) ? rs.getBigDecimal("SUM_PRICE") : zeroAmt;
    BigDecimal sumQty   = (rs.getBigDecimal("SUM_QTY") != null) ? rs.getBigDecimal("SUM_QTY") : zeroAmt;
    BigDecimal sumCost =  (rs.getBigDecimal("SUM_COST") != null) ? rs.getBigDecimal("SUM_COST") : zeroAmt;
    BigDecimal sumPricePre = (rs.getBigDecimal("PRIOR_SUM_PRICE") != null) ? rs.getBigDecimal("PRIOR_SUM_PRICE") : zeroAmt;
    BigDecimal sumQtyPre   = (rs.getBigDecimal("PRIOR_SUM_QTY") != null) ? rs.getBigDecimal("PRIOR_SUM_QTY") : zeroAmt;
    BigDecimal sumCostPre =  (rs.getBigDecimal("PRIOR_SUM_COST") != null) ? rs.getBigDecimal("PRIOR_SUM_COST") : zeroAmt;
    BigDecimal netSalesGrowth = sumPrice.subtract(sumPricePre);
    netSalesGrowth =  ( netSalesGrowth != null) ? netSalesGrowth : zeroAmt;

    psView.setVerticalName(verticalName);
    psView.setSumPrice(sumPrice);
    psView.setSumQty(sumQty);
    psView.setSumCost(sumCost);
    psView.setSumPricePre(sumPricePre);
    psView.setSumQtyPre(sumQtyPre);
    psView.setSumCostPre(sumCostPre);
    psView.setNetSalesGrowth(netSalesGrowth);
    psViewV.add(psView);
  }
  pstmt.close();
  rs.close();
  return psViewV;
}
protected GenericReportColumnViewVector getReportHeader() {

   GenericReportColumnViewVector header = new GenericReportColumnViewVector();

   header.add(ReportingUtils.createGenericReportColumnView(this.VERTICAL, "COL_BRANCH", null,COL_WIDTH[2]));
   header.add(ReportingUtils.createGenericReportColumnView(this.CURRENT_NET_SALES+" $", "COL_CUR_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[3]));
   header.add(ReportingUtils.createGenericReportColumnView(this.PRIOR_NET_SALES+" $", "COL_PRI_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[4]));
   header.add(ReportingUtils.createGenericReportColumnView(this.PROC_OF_TOTAL,"COL_PROC_TOT","TYPE_PERCENT_DATA",COL_WIDTH[5]));
   header.add(ReportingUtils.createGenericReportColumnView(this.NET_SALES_GROWTH_PR,"COL_SALES_GR_PR","TYPE_PERCENT_DATA",COL_WIDTH[6]));
   header.add(ReportingUtils.createGenericReportColumnView(this.CURRENT_UNIT_SALES,"COL_CUR_UNIT","TYPE_QTY_DATA",COL_WIDTH[8]));
   header.add(ReportingUtils.createGenericReportColumnView(this.PRIOR_UNIT_SALES, "COL_PRI_UNIT","TYPE_QTY_DATA",COL_WIDTH[9]));
   header.add(ReportingUtils.createGenericReportColumnView(this.UNIT_GROWTH,"COL_UNIT_GR","TYPE_PERCENT_DATA",COL_WIDTH[10]));
   header.add(ReportingUtils.createGenericReportColumnView(this.MARGIN,"COL_MARGIN","TYPE_PERCENT_DATA",COL_WIDTH[11]));

   return header;
}

  protected void addRowToReport(ArrayList row , Map reportRowMap, DistrPerfJPReportView currYItem) {
     boolean totalRowFlag = (((String)reportRowMap.get(this.GROUPBY)).equals(GRAND_TOTAL )) ? true : false;
     String style = null;
     if (currYItem == null && totalRowFlag ) {
        style = BOLD_STYLE;
        row.add(putCellStyle(style,reportRowMap.get(this.GROUPBY)));
     } else {
       row.add(currYItem.getVerticalName());
     }

     row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_NET_SALES)));
     row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_NET_SALES)));
     row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.PROC_OF_TOTAL))));
     row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.NET_SALES_GROWTH_PR))));
     row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_UNIT_SALES)));
     row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_UNIT_SALES)));
     row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.UNIT_GROWTH))));
     row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.MARGIN))));
  }

  protected String getQuerySqlForCurrentY(String subSelect) {
     String sql =
         " select   c.VERTICAL_NAME,  c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, \n"+
                    " b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
         " from  \n" +
         subSelect +
         " where c.VERTICAL_NAME   = b.VERTICAL_NAME (+) \n" ;
     return sql;
   }
   protected String getQuerySqlForPriorY(String subSelect) {
     String sql =
         " select  b.VERTICAL_NAME,  c.SUM_PRICE, c.SUM_QTY, c.SUM_COST,  \n" +
         "         b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
         " from  \n" +
         subSelect +
         " where c.VERTICAL_NAME (+)   = b.VERTICAL_NAME  \n" ;

     return sql;
   }


    protected String getQuerySql( Map pParams ) throws Exception {

       String filterCond = createFilterCondition(pParams);
       String begDateStr = (String) pParams.get(BEG_DATE_S);
       String endDateStr = (String) pParams.get(END_DATE_S);

       String  subSqlForCurrPeriod  = createDateSqlCond( begDateStr, endDateStr, 0);
       String  subSqlForPriorPeriod = createDateSqlCond( begDateStr, endDateStr, -1);

       String subSqlJdItems =
               " select ITEM_DIM_ID from DW_ITEM_DIM where UPPER(JD_ITEM_FL) = 'TRUE' \n" ;

       String sql=
           " (select   VERTICAL_NAME,  \n" +
           "          sum("+PRICE_COL+") SUM_PRICE, sum("+QTY_COL+") SUM_QTY, sum("+COST_COL+") SUM_COST, \n"+
           "           0 PRIOR_SUM_PRICE  , 0 PRIOR_SUM_QTY, 0 PRIOR_SUM_COST \n" +
           " from ( \n" +
           "   select  \n"+
           "      (select JD_MARKET from  DW_ACCOUNT_DIM  where ACCOUNT_DIM_ID=dif.ACCOUNT_DIM_ID ) VERTICAL_NAME, \n" +
                  PRICE_COL + " , " + QTY_COL + " , " +  COST_COL + " \n" +
           "    from DW_INVOICE_FACT dif \n" +
           "    where  DATE_DIM_ID in  ( " + subSqlForCurrPeriod + " ) \n" +
           filterCond +
           "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
           "    ) \n" +
           "  group by  VERTICAL_NAME  ) c , \n" +
           "  (select   VERTICAL_NAME,  \n" +
           "           0 SUM_PRICE  , 0 SUM_QTY, 0 SUM_COST, " +
           "           sum("+PRICE_COL+") PRIOR_SUM_PRICE, sum("+QTY_COL+") PRIOR_SUM_QTY, sum("+COST_COL+") PRIOR_SUM_COST \n" +
           " from ( \n" +
           "   select  \n"+
           "    (select JD_MARKET from  DW_ACCOUNT_DIM  where ACCOUNT_DIM_ID= dif.ACCOUNT_DIM_ID )  VERTICAL_NAME, \n" +
                    PRICE_COL + " , " + QTY_COL + " , " +  COST_COL + " \n" +
            "    from DW_INVOICE_FACT dif \n" +
            "    where  DATE_DIM_ID in  ( " + subSqlForPriorPeriod + " ) \n" +
            filterCond +
            "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
            "    ) \n" +
            "  group by  VERTICAL_NAME ) b  \n" ;

      return sql;
    }

    protected String getTitle() {
      String title = "Performance by Vertical" ;
      return title;
    }
}
