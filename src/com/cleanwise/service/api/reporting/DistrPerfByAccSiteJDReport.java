/*
 * DistrPerfByAccRegDsrJDReport.java
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
 * Picks up distributor invoices and agreates it on Account, Region, Rep_Name
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pRegion (optionaly) if exists there is ERP NUM of the Region, if blank or null - for all Regions
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 *
 */
public class DistrPerfByAccSiteJDReport extends DistrPerfByAccountJDReport implements GenericReportMulti {
  protected static final String SITE = "Ship To";


protected DistrPerfJPReportViewVector getResultOfQuery(Connection conn, Map pParams) throws Exception{
  BigDecimal zeroAmt = new BigDecimal(0);
  String selectSqlAccount= getQuerySql( pParams /*startDate,  endDate , storeId, regionS*/);

  // for accounts if there were sales in "Current Year" and may be there were no sales in "Prior Year"
  String selectSqlCurrY = getQuerySqlForCurrentY(selectSqlAccount);
  // for accounts if there were sales in "Prior Year" and there were no sales in "Current Year"
  String   selectSqlPriorY = getQuerySqlForPriorY(selectSqlAccount);

  String selectSql =
      "select \n" +
      "   (select JD_ACCOUNT_NAME from DW_ACCOUNT_DIM where ACCOUNT_DIM_ID = ACCOUNT ) SHORT_DESC, \n" +
      "   (select SITE_NAME from DW_SITE_DIM where SITE_DIM_ID = SITE ) SITE_NANE, \n" +
      "   g.* \n"+
      " from \n" +
      "  (" +
        selectSqlCurrY +
      " union  \n" +
        selectSqlPriorY +
      ") g \n" +
      " order by nvl(SUM_PRICE,0) desc, SHORT_DESC, SITE \n";

  PreparedStatement pstmt = conn.prepareStatement(selectSql);

  ResultSet rs = pstmt.executeQuery();
  DistrPerfJPReportViewVector psViewV= new DistrPerfJPReportViewVector();
  while (rs.next()){
    DistrPerfJPReportView psView = new DistrPerfJPReportView ();
    String account = (rs.getString("ACCOUNT") != null) ? rs.getString("ACCOUNT") : "0";
    String accountName = (rs.getString("SHORT_DESC") != null) ? rs.getString("SHORT_DESC") : "";
    String siteName = (rs.getString("SITE_NANE") != null) ? rs.getString("SITE_NANE") : "";
    BigDecimal sumPrice = (rs.getBigDecimal("SUM_PRICE") != null) ? rs.getBigDecimal("SUM_PRICE") : zeroAmt;
    BigDecimal sumQty   = (rs.getBigDecimal("SUM_QTY") != null) ? rs.getBigDecimal("SUM_QTY") : zeroAmt;
    BigDecimal sumCost =  (rs.getBigDecimal("SUM_COST") != null) ? rs.getBigDecimal("SUM_COST") : zeroAmt;
    BigDecimal sumPricePre = (rs.getBigDecimal("PRIOR_SUM_PRICE") != null) ? rs.getBigDecimal("PRIOR_SUM_PRICE") : zeroAmt;
    BigDecimal sumQtyPre   = (rs.getBigDecimal("PRIOR_SUM_QTY") != null) ? rs.getBigDecimal("PRIOR_SUM_QTY") : zeroAmt;
    BigDecimal sumCostPre =  (rs.getBigDecimal("PRIOR_SUM_COST") != null) ? rs.getBigDecimal("PRIOR_SUM_COST") : zeroAmt;
    BigDecimal netSalesGrowth = sumPrice.subtract(sumPricePre);
    netSalesGrowth =  ( netSalesGrowth != null) ? netSalesGrowth : zeroAmt;

    psView.setAccount(account);
    psView.setAccountName(accountName);
    psView.setRegionName(siteName);
    psView.setSumPrice(sumPrice);
    psView.setSumQty(sumQty);
    psView.setSumCost(sumCost);
    psView.setSumPricePre(sumPricePre);
    psView.setSumQtyPre(sumQtyPre);
    psView.setSumCostPre(sumCostPre);
    psView.setGroupByName(accountName);
    psView.setNetSalesGrowth(netSalesGrowth);
    psViewV.add(psView);
  }

  DistrPerfJPReportViewVector psViewGroupedV = psViewV;//groupBy(psViewV);
  pstmt.close();
  rs.close();
  return psViewGroupedV;
}

protected int getFreezeColumns() {
  return 2;
}


protected GenericReportColumnViewVector getReportHeader() {


   GenericReportColumnViewVector header = new GenericReportColumnViewVector();
/*   header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",this.ACCOUNT,0,255,"VARCHAR2","100",false));
   header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",this.SITE,0,255,"VARCHAR2","100",false));
//   header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",this.DSR,0,255,"VARCHAR2","100",false));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",this.CURRENT_NET_SALES +" $",0,20,"NUMBER","100",false));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",this.PRIOR_NET_SALES +" $",0,20,"NUMBER","100",false));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",this.PROC_OF_TOTAL,0,20,"NUMBER","60",false,"0.00%"));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",this.NET_SALES_GROWTH_PR,0,20,"NUMBER","60",false,"0.00%"));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",this.CURRENT_UNIT_SALES+" $",0,20,"NUMBER","80",false));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",this.PRIOR_UNIT_SALES+" $",0,20,"NUMBER","80",false));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",this.UNIT_GROWTH,0,20, "NUMBER","60",false,"0.00%"));
   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",this.MARGIN,0,20,"NUMBER","60",false,"0.00%"));
*/
   header.add(ReportingUtils.createGenericReportColumnView(this.ACCOUNT, "COL_ACCOUNT", null,COL_WIDTH[0]));
   header.add(ReportingUtils.createGenericReportColumnView(this.SITE, "COL_SITE", null,COL_WIDTH[0]));
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
   boolean subTotalRowFlag = (((String)reportRowMap.get(this.GROUPBY)).equals(SUB_TOTAL )) ? true : false;

   String style = null;
   if (currYItem == null && totalRowFlag) {
      style = BOLD_STYLE;
      row.add(putCellStyle(style,reportRowMap.get(this.GROUPBY)));
      row.add("");
//      row.add("");
   } else {
     if (subTotalRowFlag){
       style = BOLD_STYLE;
       row.add(putCellStyle(style, reportRowMap.get(this.GROUPBY)));
     } else {
       row.add(putCellStyle(style, currYItem.getAccountName()));
     }
     row.add(putCellStyle(style,currYItem.getRegionName()));
//     row.add(putCellStyle(style,currYItem.getRepName()));
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
         " select  c.ACCOUNT , c.SITE,  c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, \n" +
         "         b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
         " from  \n" +
         subSelect +
         " where c.account  = b.account (+)  \n" +
         "  and c.SITE    = b.SITE (+) \n" ;
 //        "  and  c.DSR_ID   = b.DSR_ID (+) \n" ;
     return sql;
   }
   protected String getQuerySqlForPriorY(String subSelect) {
     String sql =
         " select  b.ACCOUNT , b.SITE,  c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, \n" +
         "         b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
         " from  \n" +
         subSelect +
         " where c.account(+)   = b.account  \n" +
         "   and c.SITE (+)     = b.SITE  \n" ;

     return sql;
   }


    protected String getQuerySql(Map pParams) throws Exception {

      String filterCond = createFilterCondition(pParams);
      String begDateStr = (String) pParams.get(BEG_DATE_S);
      String endDateStr = (String) pParams.get(END_DATE_S);

      String  subSqlForCurrPeriod  = createDateSqlCond( begDateStr, endDateStr, 0);
      String  subSqlForPriorPeriod = createDateSqlCond( begDateStr, endDateStr, -1);

     String whereByRegion =  "";

     String subSqlJdItems =
          " select ITEM_DIM_ID from DW_ITEM_DIM where UPPER(JD_ITEM_FL) = 'TRUE' \n" ;


     String sql=
           " (select  ACCOUNT, SITE,  \n" +
           "  sum("+PRICE_COL+") SUM_PRICE, sum("+QTY_COL+") SUM_QTY, sum("+COST_COL+") SUM_COST, \n"+
           "  0 PRIOR_SUM_PRICE  , 0 PRIOR_SUM_QTY, 0 PRIOR_SUM_COST \n" +
           " from ( \n" +
           "   select  \n"+
           "      ACCOUNT_DIM_ID      ACCOUNT, \n" +
           "      SITE_DIM_ID         SITE,  \n" +
//           "      SALES_REP_DIM_ID    DSR_ID,  \n" +
                  PRICE_COL + " , " + QTY_COL + " , " +  COST_COL + " \n" +
          "    from DW_INVOICE_FACT  \n" +
          "    where  DATE_DIM_ID in  ( " + subSqlForCurrPeriod + " ) \n" +
          "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
          filterCond +
          "    ) \n" +
          "  group by ACCOUNT, SITE ) c , \n" +
          "  (select  ACCOUNT, SITE, \n"+
          "   0 SUM_PRICE  , 0 SUM_QTY, 0 SUM_COST, \n"+
          "   sum("+PRICE_COL+") PRIOR_SUM_PRICE, sum("+QTY_COL+") PRIOR_SUM_QTY, sum("+COST_COL+") PRIOR_SUM_COST \n" +
          " from ( \n" +
          "   select  \n"+
          "        ACCOUNT_DIM_ID      ACCOUNT, \n" +
          "        SITE_DIM_ID         SITE,  \n" +
//          "        SALES_REP_DIM_ID    DSR_ID,  \n" +
                    PRICE_COL + " , " + QTY_COL + " , " +  COST_COL + " \n" +
          "    from DW_INVOICE_FACT   \n" +
          "    where  DATE_DIM_ID in ( " + subSqlForPriorPeriod + " ) \n" +
          "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
          filterCond +
          "    ) \n" +
          "  group by ACCOUNT, SITE ) b  \n" ;

      return sql;
    }

    protected String getTitle() {
     String title = "Performance by Account by Site " ;
     return title;
    }

    protected DistrPerfJPReportViewVector  groupBy (DistrPerfJPReportViewVector psViewV){
       DistrPerfJPReportViewVector groupV = new DistrPerfJPReportViewVector();

       BigDecimal sumPrice = new BigDecimal(0);
       BigDecimal sumQty  = new BigDecimal(0);
       BigDecimal sumCost = new BigDecimal(0);
       BigDecimal sumPricePre = new BigDecimal(0);
       BigDecimal sumQtyPre = new BigDecimal(0);
       BigDecimal sumCostPre = new BigDecimal(0);
       DistrPerfJPReportView psViewOrd = null;
       DistrPerfJPReportView psViewsGr = null;

       String groupVal = "";
       String priorGroupVal = "";
       for (int i = 0; i < psViewV.size(); i++) {
         psViewOrd = (DistrPerfJPReportView) psViewV.get(i);
         groupVal = psViewOrd.getGroupByName();

         psViewsGr = new DistrPerfJPReportView();
         if (groupVal != null && priorGroupVal.length() > 0 &&
             !groupVal.equals(priorGroupVal)) {
           psViewsGr.setGroupByName(this.SUB_TOTAL + " for " + priorGroupVal);
           psViewsGr.setRegionName(priorGroupVal);
           psViewsGr.setSumPrice(sumPrice);
           psViewsGr.setSumQty(sumQty);
           psViewsGr.setSumCost(sumCost);
           psViewsGr.setSumPricePre(sumPricePre);
           psViewsGr.setSumQtyPre(sumQtyPre);
           psViewsGr.setSumCostPre(sumCostPre);
           psViewsGr.setNetSalesGrowth(sumPrice.subtract(sumPricePre));
           groupV.add(psViewsGr);

           sumPrice = new BigDecimal(0);
           sumQty = new BigDecimal(0);
           sumCost = new BigDecimal(0);
           sumPricePre = new BigDecimal(0);
           sumQtyPre = new BigDecimal(0);
           sumCostPre = new BigDecimal(0);

         }
         sumPrice= sumPrice.add(psViewOrd.getSumPrice());
         sumQty= sumQty.add(psViewOrd.getSumQty());
         sumCost = sumCost.add(psViewOrd.getSumCost());
         sumPricePre= sumPricePre.add(psViewOrd.getSumPricePre());
         sumQtyPre= sumQtyPre.add(psViewOrd.getSumQtyPre());
         sumCostPre = sumCostPre.add(psViewOrd.getSumCostPre());

         priorGroupVal = groupVal;
         groupV.add(psViewOrd) ;
       }
         psViewsGr = new DistrPerfJPReportView();
         psViewsGr.setGroupByName(this.SUB_TOTAL + " for " + groupVal);
         psViewsGr.setRegionName(groupVal);
         psViewsGr.setSumPrice(sumPrice);
         psViewsGr.setSumQty(sumQty);
         psViewsGr.setSumCost(sumCost);
         psViewsGr.setSumPricePre(sumPricePre);
         psViewsGr.setSumQtyPre(sumQtyPre);
         psViewsGr.setSumCostPre(sumCostPre);
         psViewsGr.setNetSalesGrowth(sumPrice.subtract(sumPricePre));
         groupV.add(psViewsGr);

       return groupV;
    }

}
