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
 * Picks up distributor invoices and agreates it on Account, Region, Rep_Name
 * for Decline
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pRegion (optionaly) if exists there is ERP NUM of the Region, if blank or null - for all Regions
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 *
 */

public class DistrPerfByAccRegDsrJDDeclineReport  extends DistrPerfByAccRegDsrJDReport implements GenericReportMulti {


    protected String getQuerySqlForCurrentY(String subSelect) {
     String sql =
         " select  c.ACCOUNT , c.REGION, c.DSR_ID, c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
         " from  \n" +
         subSelect +
         " where c.account  = b.account (+)  \n" +
         "  and c.REGION   = b.REGION (+) \n" +
         "  and  c.DSR_ID   = b.DSR_ID (+) \n" +
         " and nvl(c.SUM_PRICE,0)  < nvl(b.PRIOR_SUM_PRICE,0) \n" ;
     return sql;
   }
   protected String getQuerySqlForPriorY(String subSelect) {
     String sql =
         " select  b.ACCOUNT , b.REGION, b.DSR_ID, c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
         " from  \n" +
         subSelect +
         " where c.account(+)  = b.account  \n" +
         "   and c.REGION (+)   = b.REGION  \n" +
         "   and c.DSR_ID (+)   = b.DSR_ID  \n" +
         " and nvl(c.SUM_PRICE,0) < nvl(b.PRIOR_SUM_PRICE,0) \n";

     return sql;
   }

   protected String getTitle() {
     String title = "Performance by Account by Region by DSR with decline" ;
     return title;
    }

  protected DistrPerfJPReportViewVector getResultOfQuery(Connection conn, Map pParams ) throws Exception{
   BigDecimal zeroAmt = new BigDecimal(0);
   String selectSqlAccount= getQuerySql( pParams );

   // for accounts if there were sales in "Current Year" and may be there were no sales in "Prior Year"
   String selectSqlCurrY = getQuerySqlForCurrentY(selectSqlAccount);
   // for accounts if there were sales in "Prior Year" and there were no sales in "Current Year"
   String   selectSqlPriorY = getQuerySqlForPriorY(selectSqlAccount);

   String selectSql =
       "select \n" +
       "   (select JD_ACCOUNT_NAME from DW_ACCOUNT_DIM where ACCOUNT_DIM_ID = ACCOUNT ) SHORT_DESC, \n" +
       "   (select REGION_NAME from DW_REGION_DIM where REGION_DIM_ID = REGION ) BRANCH, \n" +
       "   (select REP_NAME from DW_SALES_REP_DIM where SALES_REP_DIM_ID = DSR_ID ) DSR, \n" +
       "   (nvl(SUM_PRICE,0) - nvl(PRIOR_SUM_PRICE,0)) NET_SALES_GROWTH, \n" +
       "   g.* \n"+
       " from \n" +
       "  (" +
         selectSqlCurrY +
       " union  \n" +
         selectSqlPriorY +
       ") g \n" +
       " order by BRANCH,  nvl(NET_SALES_GROWTH,0)  \n";

   PreparedStatement pstmt = conn.prepareStatement(selectSql);

   ResultSet rs = pstmt.executeQuery();
   DistrPerfJPReportViewVector psViewV= new DistrPerfJPReportViewVector();
   while (rs.next()){
     DistrPerfJPReportView psView = new DistrPerfJPReportView ();
     String account = (rs.getString("ACCOUNT") != null) ? rs.getString("ACCOUNT") : "0";
     String accountName = (rs.getString("SHORT_DESC") != null) ? rs.getString("SHORT_DESC") : "";
     String regionName = (rs.getString("BRANCH") != null) ? rs.getString("BRANCH") : "";
     String repName = (rs.getString("DSR") != null) ? rs.getString("DSR") : "";
     BigDecimal sumPrice = (rs.getBigDecimal("SUM_PRICE") != null) ? rs.getBigDecimal("SUM_PRICE") : zeroAmt;
     BigDecimal sumQty   = (rs.getBigDecimal("SUM_QTY") != null) ? rs.getBigDecimal("SUM_QTY") : zeroAmt;
     BigDecimal sumCost =  (rs.getBigDecimal("SUM_COST") != null) ? rs.getBigDecimal("SUM_COST") : zeroAmt;
     BigDecimal sumPricePre = (rs.getBigDecimal("PRIOR_SUM_PRICE") != null) ? rs.getBigDecimal("PRIOR_SUM_PRICE") : zeroAmt;
     BigDecimal sumQtyPre   = (rs.getBigDecimal("PRIOR_SUM_QTY") != null) ? rs.getBigDecimal("PRIOR_SUM_QTY") : zeroAmt;
     BigDecimal sumCostPre =  (rs.getBigDecimal("PRIOR_SUM_COST") != null) ? rs.getBigDecimal("PRIOR_SUM_COST") : zeroAmt;
     BigDecimal netSalesGrowth = (rs.getBigDecimal("NET_SALES_GROWTH") != null) ? rs.getBigDecimal("NET_SALES_GROWTH") : zeroAmt;

     psView.setAccount(account);
     psView.setAccountName(accountName);
     psView.setRegionName(regionName);
     psView.setRepName(repName);
     psView.setSumPrice(sumPrice);
     psView.setSumQty(sumQty);
     psView.setSumCost(sumCost);
     psView.setSumPricePre(sumPricePre);
     psView.setSumQtyPre(sumQtyPre);
     psView.setSumCostPre(sumCostPre);
     psView.setGroupByName(regionName);
     psView.setNetSalesGrowth(netSalesGrowth);
     psViewV.add(psView);
   }

   DistrPerfJPReportViewVector psViewGroupedV = groupBy(psViewV);
   pstmt.close();
   rs.close();
   return psViewGroupedV;
 }



   protected GenericReportColumnViewVector getReportHeader() {

       GenericReportColumnViewVector header = new GenericReportColumnViewVector();

       header.add(ReportingUtils.createGenericReportColumnView(this.ACCOUNT, "COL_ACCOUNT", null,COL_WIDTH[0]));
       header.add(ReportingUtils.createGenericReportColumnView(this.BRANCH,  "COL_BRANCH", null,COL_WIDTH[2]));
       header.add(ReportingUtils.createGenericReportColumnView(this.DSR, "COL_DSR", null,COL_WIDTH[3]));
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
       boolean subTotalRowFlag = (((String)reportRowMap.get(this.GROUPBY)).equals(SUB_TOTAL )) ? true : false;

       String style = null;
       if (currYItem == null && totalRowFlag) {
          style = BOLD_STYLE;
          row.add(putCellStyle(style,reportRowMap.get(this.GROUPBY)));
          row.add("");
          row.add("");
       } else {
         if (subTotalRowFlag){
           style = BOLD_STYLE;
           row.add(putCellStyle(style, reportRowMap.get(this.GROUPBY)));
         } else {
           row.add(putCellStyle(style, currYItem.getAccountName()));
         }
         row.add(putCellStyle(style,currYItem.getRegionName()));
         row.add(putCellStyle(style,currYItem.getRepName()));
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

}
