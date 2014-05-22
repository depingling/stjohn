/*
 * DistrPerfByAccountJDReport.java
 *
 * Created on September 12, 2008, 4:43 PM
 */

package com.cleanwise.service.api.reporting;

import java.util.Map;

import java.util.ArrayList;

import java.math.BigDecimal;

import java.util.*;
import com.cleanwise.service.api.value.IdVector;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;

/**
 * Picks up distributor invoices and agreates it on Account
 * for Decline
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 *
 */
public class DistrPerfByAccountJDDeclineReport  extends DistrPerfByAccountJDReport implements GenericReportMulti {

  protected String getQuerySqlForCurrentY(String subSelect) {
    String sql =
        " select  c.ACCOUNT , c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
        " from  \n" +
        subSelect +
        " where c.ACCOUNT  = b.ACCOUNT (+) \n" +
        " and nvl(c.SUM_PRICE,0) < nvl(b.PRIOR_SUM_PRICE,0) \n";
    return sql;
  }
  protected String getQuerySqlForPriorY(String subSelect) {
    String sql =
        " select  b.ACCOUNT , c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
        " from  \n" +
           subSelect +
         " where c.ACCOUNT(+)  = b.ACCOUNT \n" +
         " and nvl(c.SUM_PRICE,0) < nvl(b.PRIOR_SUM_PRICE,0) \n";
    return sql;
    }

 protected DistrPerfJPReportViewVector getResultOfQuery(Connection conn, Map pParams) throws Exception{
   DistrPerfJPReportViewVector psViewV = (DistrPerfJPReportViewVector)super.getResultOfQuery(conn, pParams) ;
   psViewV.sort("NetSalesGrowth", false);
   return psViewV;
 }

 protected GenericReportColumnViewVector getReportHeader() {

   GenericReportColumnViewVector header = new GenericReportColumnViewVector();

   header.add(ReportingUtils.createGenericReportColumnView(this.ACCOUNT, "COL_ACCOUNT", null,COL_WIDTH[0]));
   header.add(ReportingUtils.createGenericReportColumnView(this.CURRENT_NET_SALES+" $", "COL_CUR_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[3]));
   header.add(ReportingUtils.createGenericReportColumnView(this.PRIOR_NET_SALES+" $", "COL_PRI_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[4]));
   header.add(ReportingUtils.createGenericReportColumnView(this.PROC_OF_TOTAL,"COL_PROC_TOT","TYPE_PERCENT_DATA",COL_WIDTH[5]));
   header.add(ReportingUtils.createGenericReportColumnView(this.NET_SALES_GROWTH+" $","COL_SALES_GR","TYPE_AMOUNT_DATA",COL_WIDTH[7]));
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
      row.add(putCellStyle(style,currYItem.getAccountName()));
    }

    row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_NET_SALES)));
    row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_NET_SALES)));
    row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.PROC_OF_TOTAL))));
    row.add(putCellStyle(style,reportRowMap.get(this.NET_SALES_GROWTH)));
    row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.NET_SALES_GROWTH_PR))));
    row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_UNIT_SALES)));
    row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_UNIT_SALES)));
    row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.UNIT_GROWTH))));
    row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.MARGIN))));
 }
    protected String getTitle() {
      String title = "Performance by Account with decline" ;
      return title;
    }
}
