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
public class PurchBySKUSubcatJDReport  extends PurchBySiteJDReport  implements GenericReportMulti {

    /** Creates a new instance of DistributorInvoiceProfitReport */
    public PurchBySKUSubcatJDReport() {
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


      protected GenericReportColumnViewVector getReportHeader() {
   //     String L_CURRENT_NET_SALES = "Current Yr Net Purchases $";
   //     String L_PRIOR_NET_SALES = "Prior Yr Net Purchases $";
   //     String L_PROC_OF_TOTAL = "% of Total Purchases %";

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",this.SKU_NUM,0,255,"VARCHAR2","80",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",this.SKU_NAME,0,255,"VARCHAR2","80",false));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.PACK, 0, 255, "VARCHAR2", "60",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.UOM,  0, 255, "VARCHAR2", "60",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", this.SIZE, 0, 255, "VARCHAR2", "60",false));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String",this.SUB_CATEGORY,0,255,"VARCHAR2","100",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",CURRENT_NET_SALES+" $",0,20,"NUMBER","80",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",PRIOR_NET_SALES+" $",0,20,"NUMBER","80",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",PROC_OF_TOTAL,0,20,"NUMBER","60",false,"0.0%"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",this.NET_SALES_GROWTH,0,20,"NUMBER","60",false,"0.0%"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",this.CURRENT_UNITS,0,20,"NUMBER","80",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal.Separator",this.PRIOR_UNITS,0,20,"NUMBER","80",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal",this.UNIT_GROWTH,0,20, "NUMBER","60",false,"0.0%"));
        return header;
    }
/*
    protected GenericReportColumnViewVector getReportTitle(String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",pTitle,0,255,"VARCHAR2"));
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Customer = " + (String) pParams.get("STORE"),0,255,"VARCHAR2"));
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",(String) pParams.get(""),0,255,"VARCHAR2"));
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","From : " + (String) pParams.get("BEG_DATE"),0,255,"VARCHAR2"));
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","  To :" + (String) pParams.get("END_DATE"),0,255,"VARCHAR2"));
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",(String) pParams.get(""),0,255,"VARCHAR2"));
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Regions = " + (String) pParams.get("ACCOUNTs_OPT"),0,255,"VARCHAR2"));
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sites = " + (String) pParams.get("SITEs_OPT"),0,255,"VARCHAR2"));

      return title;
    }
*/
    protected String getTitle() {
      String title = "Purchases by SKU and Subcategory" ;
      return title;
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
      IdVector listOfManuf = getListOfManuf(conn, JD_GROUP_NAME);
      String selectSqlSites= getQuerySql( listOfManuf, pParams);
      HashMap itemToTopCatMap = getItemToTopCatMap (conn, storeId);

      // for accounts if there were sales in "Current Year" and may be there were no sales in "Prior Year"
      String selectSqlCurrY = getQuerySqlForCurrentY(selectSqlSites);
      // for accounts if there were sales in "Prior Year" and there were no sales in "Current Year"
//      String selectSqlPriorY = getQuerySqlForPriorY(selectSqlSites);

      String selectSql =
          "select \n" +
          "   ITEM_NUM as SKU_NUM, \n" +
          "   i.SHORT_DESC as SKU_NAME, \n" +
          "(select CLW_VALUE from CLW_ITEM_META where NAME_VALUE = 'PACK' and ITEM_ID=i.ITEM_ID) as PACK, \n" +
          "(select CLW_VALUE from CLW_ITEM_META where NAME_VALUE = 'UOM' and ITEM_ID=i.ITEM_ID) as UOM, \n" +
          "(select CLW_VALUE from CLW_ITEM_META where NAME_VALUE = 'SIZE' and ITEM_ID=i.ITEM_ID) as item_SIZE, \n" +
          "   g.* \n"+
          " from clw_item_mapping im, clw_item i, \n" +
          "  (" +
            selectSqlCurrY +
//          " union  \n" +
//            selectSqlPriorY +
            ") g \n" +
          " where  ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'  \n" +
          "   and im.ITEM_ID = g.ITEM_ID \n" +
          "   and i.ITEM_ID = g.ITEM_ID \n" +
          " order by nvl(SUM_PRICE,0) desc, SKU_NUM \n";

      PreparedStatement pstmt = conn.prepareStatement(selectSql);

      ResultSet rs = pstmt.executeQuery();
      JDReportViewVector psViewV= new JDReportViewVector();
      while (rs.next()){
        JDReportView psView = new JDReportView ();
        String itemId = (rs.getString("ITEM_ID") != null) ? rs.getString("ITEM_ID") : "0";
        String sku = (rs.getString("SKU_NUM") != null) ? rs.getString("SKU_NUM") : "0";
        String skuName = (rs.getString("SKU_NAME") != null) ? rs.getString("SKU_NAME") : "";
        BigDecimal sumPrice = (rs.getBigDecimal("SUM_PRICE") != null) ? rs.getBigDecimal("SUM_PRICE") : zeroAmt;
        BigDecimal sumQty   = (rs.getBigDecimal("SUM_QTY") != null) ? rs.getBigDecimal("SUM_QTY") : zeroAmt;
        BigDecimal sumPricePre = (rs.getBigDecimal("PRIOR_SUM_PRICE") != null) ? rs.getBigDecimal("PRIOR_SUM_PRICE") : zeroAmt;
        BigDecimal sumQtyPre   = (rs.getBigDecimal("PRIOR_SUM_QTY") != null) ? rs.getBigDecimal("PRIOR_SUM_QTY") : zeroAmt;
        String pack = (rs.getString("PACK") != null) ? rs.getString("PACK") : "";
        String uom = (rs.getString("UOM") != null) ? rs.getString("UOM") : "";
        String size = (rs.getString("ITEM_SIZE") != null) ? rs.getString("ITEM_SIZE") : "";
        psView.setSku(sku);
        psView.setSkuName(skuName);
        psView.setSubCat((String)itemToTopCatMap.get(itemId));
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

    protected String getQuerySql(IdVector listOfManuf, Map pParams) throws Exception {
      //=========== getting parameters =======================================
      int storeId = Integer.parseInt((String) pParams.get(STORE_S));
      String begDateStr = (String) pParams.get(BEG_DATE_S);
      String endDateStr = (String) pParams.get(END_DATE_S);
      Object accountsV = getParamValue(pParams, LOCATE_ACCOUNT_MULTI_S);
      Object sitesV = getParamValue(pParams, SITE_MULTI_OPT_S);
      //======================================================================//
       GregorianCalendar currBegCalendar = new GregorianCalendar();
       currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

       GregorianCalendar priorBegCalendar = (GregorianCalendar) currBegCalendar.clone();
       priorBegCalendar.add(GregorianCalendar.YEAR, -1);

       GregorianCalendar currEndCalendar = new GregorianCalendar();
       currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

       GregorianCalendar priorEndCalendar = (GregorianCalendar) currEndCalendar.clone();
       priorEndCalendar.add(GregorianCalendar.YEAR, -1);

       String startDateS = ReportingUtils.toSQLDate(currBegCalendar.getTime());
       String endDateS =   ReportingUtils.toSQLDate(currEndCalendar.getTime());

       String startDateSPre = ReportingUtils.toSQLDate(priorBegCalendar.getTime());
       String endDateSPre = ReportingUtils.toSQLDate(priorEndCalendar.getTime());

       String whereForAccounts = (ReportingUtils.isParamSet(accountsV) && !ReportingUtils.isParamSet(sitesV) ) ?
          "    and account_id in (" +ReportingUtils.convertParamToString(accountsV) + ")  \n" : "";

       String whereForSites = (ReportingUtils.isParamSet(sitesV) ) ?
         "    and site_id in (" +ReportingUtils.convertParamToString(sitesV) + ")  \n" : "";

       String subSqlJdItems = "-1";
       String itemListSelected = getParamValue(pParams, LOCATE_ITEM_OPT_S);
       if (Utility.isSet(itemListSelected)) {
         subSqlJdItems = itemListSelected;
       } else if (listOfManuf != null && listOfManuf.size() > 0) {
         subSqlJdItems =
           " SELECT item_Id FROM clw_item_mapping \n" +
           " WHERE bus_entity_id IN (" +IdVector.toCommaString(listOfManuf) + ") \n" +
           "   AND  ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'";
       }


       String sql=
          " select item_id, \n" +
          "        sum("+ PRICE_COL +") SUM_PRICE, \n" +
          "        sum("+ QTY_COL +") SUM_QTY,  \n" +
          "         0 PRIOR_SUM_PRICE, \n" +
          "         0 PRIOR_SUM_QTY \n" +
          "   from clw_order o, clw_order_item oi \n" +
          "  where o.ORDER_ID = oi.ORDER_ID \n" +
          "    and store_id =  " + storeId + " \n" +
          whereForAccounts +
          whereForSites +
          "    and o.ORIGINAL_ORDER_DATE between "+ startDateS+" and "+endDateS + " \n" +
          "    and oi.ERP_PO_NUM is not NULL \n" +
          "    and oi.ORDER_ITEM_STATUS_CD not in ('" + RefCodeNames.ORDER_STATUS_CD.CANCELLED + "') \n" +
          "    and oi.ITEM_ID in (" + subSqlJdItems +") \n" +
          "    group by item_id \n" +
          " union all \n" +
          " select item_id, \n" +
          "        0 SUM_PRICE, \n" +
          "        0 SUM_QTY,  \n" +
          "        sum("+ PRICE_COL +") PRIOR_SUM_PRICE, \n" +
          "        sum("+ QTY_COL +")  PRIOR_SUM_QTY \n" +
          "   from clw_order o, clw_order_item oi \n" +
          "  where o.ORDER_ID = oi.ORDER_ID \n" +
          "    and store_id =  " + storeId + " \n" +
          whereForAccounts +
          whereForSites +
          "    and o.ORIGINAL_ORDER_DATE between "+ startDateSPre+" and "+endDateSPre + " \n" +
          "    and oi.ERP_PO_NUM is not NULL \n" +
          "    and oi.ORDER_ITEM_STATUS_CD not in ('" + RefCodeNames.ORDER_STATUS_CD.CANCELLED + "') \n" +
          "    and oi.ITEM_ID in (" + subSqlJdItems +") \n" +
          "    group by item_id \n";

         return sql;
     }


  protected String getQuerySqlForCurrentY(String subSelect) {
      String sql =
          "select  ITEM_ID, sum(SUM_PRICE) SUM_PRICE, sum(SUM_QTY) SUM_QTY, \n " +
          "                 sum(PRIOR_SUM_PRICE) PRIOR_SUM_PRICE, sum(PRIOR_SUM_QTY) PRIOR_SUM_QTY \n" +
          " from ( \n" +
          subSelect +
          " ) group by ITEM_ID  \n";

      return sql;
    }


}
