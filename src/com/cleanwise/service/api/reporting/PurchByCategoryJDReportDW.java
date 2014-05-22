/*
 * PurchBySiteJDReportDW.java
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
import java.math.MathContext;

/**
 * Picks up orders and agreates it on Categories
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pStoreId store Id ,
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 * @param pAccountIds account Ids optionally,
 * @param pSiteIds site Ids optionally
 */
public class PurchByCategoryJDReportDW extends BaseJDReportDW implements GenericReportMulti {

    /** Creates a new instance of DistributorInvoiceProfitReport */
    public PurchByCategoryJDReportDW() {
    }
    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    protected static final String CATEGORY = "Category";
  //  protected static final String CHEMICALS_TOTAL ="Chemicals Total";
    protected static final String CHEMICAL ="Chemical";

    protected static final String PRICE_COL = "QUANTITY * PRICE";
    protected static final String QTY_COL   = "QUANTITY";
    protected static final String COST_COL  = "PRICE";

     protected GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(this.CATEGORY, "COL_CATEGORY", null,"27"));
        header.add(ReportingUtils.createGenericReportColumnView(this.CURRENT_NET_SALES+" $", "COL_CUR_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[4]));
        header.add(ReportingUtils.createGenericReportColumnView(this.PRIOR_NET_SALES+" $", "COL_PRI_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[5]));
        header.add(ReportingUtils.createGenericReportColumnView(this.PROC_OF_TOTAL,"COL_PROC_TOT","TYPE_PERCENT_DATA",COL_WIDTH[6]));
        header.add(ReportingUtils.createGenericReportColumnView(this.NET_SALES_GROWTH,"COL_SALES_GR","TYPE_PERCENT_DATA",COL_WIDTH[7]));
        header.add(ReportingUtils.createGenericReportColumnView(this.CURRENT_UNITS,"COL_CUR_UNIT","TYPE_QTY_DATA",COL_WIDTH[8]));
        header.add(ReportingUtils.createGenericReportColumnView(this.PRIOR_UNITS, "COL_PRI_UNIT","TYPE_QTY_DATA",COL_WIDTH[9]));
        header.add(ReportingUtils.createGenericReportColumnView(this.UNIT_GROWTH,"COL_UNIT_GR","TYPE_PERCENT_DATA",COL_WIDTH[10]));
        return header;
    }


    protected void addRowToReport(ArrayList row , Map reportRowMap, JDReportView currYItem) {
      String groupByRow =  (String)reportRowMap.get(this.GROUPBY);

      boolean totalRowFlag = (groupByRow != null && groupByRow.equals(GRAND_TOTAL )) ? true : false;
      String style = null;
      if (currYItem == null && totalRowFlag ) {
         style = BOLD_STYLE;
         row.add(putCellStyle(style,reportRowMap.get(this.GROUPBY)));
      } else {
         if (currYItem.getGroupByName().contains(this.CHEMICALS_TOTAL)){
           style = BOLD_STYLE;
         }
         row.add(putCellStyle(style,currYItem.getGroupByName()));
       }
      row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_NET_SALES)));
      row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_NET_SALES)));
      row.add(putCellStyle(style,reportRowMap.get(this.PROC_OF_TOTAL)));
      row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.NET_SALES_GROWTH))));
      row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_UNITS)));
      row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_UNITS)));
      row.add(putCellStyle(style,ReportingUtils.validPercent(reportRowMap.get(this.UNIT_GROWTH))));
    }

    protected ArrayList getResultOfQuery(Connection conn, Map pParams) throws Exception{
      int storeId = Integer.parseInt((String) pParams.get(STORE_S));
      String categoryStr = (String) pParams.get(CATEGORY_OPT_S);

      BigDecimal zeroAmt = new BigDecimal(0);
      IdVector listOfJDItems = getListOfJDItems(conn, JD_GROUP_NAME);
      String selectSqlSites= getQuerySql( pParams);
//      HashMap itemToTopCatMap = getItemToTopCatMap (conn, storeId,categoryStr);

      // for accounts if there were sales in "Current Year" and may be there were no sales in "Prior Year"
      String selectSqlCurrY = getQuerySqlForCurrentY(selectSqlSites);
      // for accounts if there were sales in "Prior Year" and there were no sales in "Current Year"

      String selectSql =
          "select  g.*, \n"+
          " (select NVL(jd_category3, NVL(jd_category2,jd_category1)) from DW_CATEGORY_DIM where g.category_dim_id = category_dim_id) AS CATEGORY \n" +
          "from  \n" +
          "  (" +
          selectSqlCurrY +
         ") g \n" +
          " order by CATEGORY \n";

      PreparedStatement pstmt = conn.prepareStatement(selectSql);

      ResultSet rs = pstmt.executeQuery();

      JDReportViewVector psViewV= new JDReportViewVector();
       // for Chemicals total
      BigDecimal sumPriceC = new BigDecimal(0);
      BigDecimal sumQtyC  = new BigDecimal(0);
      BigDecimal sumPricePreC = new BigDecimal(0);
      BigDecimal sumQtyPreC = new BigDecimal(0);

      while (rs.next()){
        JDReportView psView = new JDReportView ();
        Integer itemId = (rs.getString("ITEM_ID") != null) ? new Integer(rs.getString("ITEM_ID")) : new Integer("0");
        String type = (listOfJDItems.contains(itemId)) ? CHEMICAL : "";
//        String category  = (String)itemToTopCatMap.get(itemId.toString());
        String category  = (rs.getString("CATEGORY") != null) ? new String(rs.getString("CATEGORY")) : "";

        BigDecimal itemPrice = (rs.getBigDecimal("SUM_PRICE") != null) ? rs.getBigDecimal("SUM_PRICE") : zeroAmt;
        BigDecimal itemQty   = (rs.getBigDecimal("SUM_QTY") != null) ? rs.getBigDecimal("SUM_QTY") : zeroAmt;
        BigDecimal itemPricePre = (rs.getBigDecimal("PRIOR_SUM_PRICE") != null) ? rs.getBigDecimal("PRIOR_SUM_PRICE") : zeroAmt;
        BigDecimal itemQtyPre   = (rs.getBigDecimal("PRIOR_SUM_QTY") != null) ? rs.getBigDecimal("PRIOR_SUM_QTY") : zeroAmt;
        if(type.equals(CHEMICAL)){
          sumPriceC = sumPriceC.add(itemPrice);
          sumQtyC = sumQtyC.add(itemQty);
          sumPricePreC = sumPricePreC.add(itemPricePre);
          sumQtyPreC = sumQtyPreC.add(itemQtyPre);
        }
        if (category != null) {
          psView.setGroupByType(type);
          psView.setGroupByName(category);
          psView.setSumPrice(itemPrice);
          psView.setSumQty(itemQty);
          psView.setSumPricePre(itemPricePre);
          psView.setSumQtyPre(itemQtyPre);
          psViewV.add(psView);
        }
      }
      pstmt.close();
      rs.close();

      JDReportViewVector psViewGroupedV = groupByCategory(psViewV);
      psViewGroupedV.sort("SumPrice", false);

      // Chemical Total
      JDReportView psViewC = new JDReportView ();
      psViewC.setGroupByName(this.CHEMICALS_TOTAL);
      psViewC.setSumPrice(sumPriceC);
      psViewC.setSumQty(sumQtyC);
      psViewC.setSumPricePre(sumPricePreC);
      psViewC.setSumQtyPre(sumQtyPreC);
      psViewGroupedV.add(psViewC);


      return psViewGroupedV;

    }

    private JDReportViewVector  groupByCategory (JDReportViewVector psViewV){
      psViewV.sort("GroupByName");
      JDReportViewVector groupV = new JDReportViewVector();

      BigDecimal sumPrice = new BigDecimal(0);
      BigDecimal sumQty  = new BigDecimal(0);
      BigDecimal sumPricePre = new BigDecimal(0);
      BigDecimal sumQtyPre = new BigDecimal(0);
      JDReportView psViewOrd = null;
      JDReportView psViewsGr = null;

      String category = "";
      String priorCategory = "";
      for (int i = 0; i < psViewV.size(); i++) {
        psViewOrd = (JDReportView) psViewV.get(i);
        category = psViewOrd.getGroupByName();

        psViewsGr = new JDReportView();
        if (category != null && priorCategory.length() > 0 &&
            !category.equals(priorCategory)) {
          psViewsGr.setGroupByName(priorCategory);
          psViewsGr.setSumPrice(sumPrice);
          psViewsGr.setSumQty(sumQty);
          psViewsGr.setSumPricePre(sumPricePre);
          psViewsGr.setSumQtyPre(sumQtyPre);
          groupV.add(psViewsGr);

          sumPrice = new BigDecimal(0);
          sumQty = new BigDecimal(0);
          sumPricePre = new BigDecimal(0);
          sumQtyPre = new BigDecimal(0);
        }
        sumPrice= sumPrice.add(psViewOrd.getSumPrice());
        sumQty= sumQty.add(psViewOrd.getSumQty());
        sumPricePre= sumPricePre.add(psViewOrd.getSumPricePre());
        sumQtyPre= sumQtyPre.add(psViewOrd.getSumQtyPre());
        priorCategory = category;
      }
        psViewsGr = new JDReportView();
        psViewsGr.setGroupByName(category);
        psViewsGr.setSumPrice(sumPrice);
        psViewsGr.setSumQty(sumQty);
        psViewsGr.setSumPricePre(sumPricePre);
        psViewsGr.setSumQtyPre(sumQtyPre);
        groupV.add(psViewsGr);

      return groupV;
    }



    protected String getQuerySql(  Map pParams) throws Exception {
      String filterCond = createFilterCondition(pParams);
      String begDateStr = (String) pParams.get(BEG_DATE_S);
      String endDateStr = (String) pParams.get(END_DATE_S);

      //======================================================================

      String  subSqlForCurrPeriod  = createDateSqlCond( begDateStr, endDateStr, 0);
      String  subSqlForPriorPeriod = createDateSqlCond( begDateStr, endDateStr, -1);


     String sql=
       " select ITEM_DIM_ID as ITEM_ID ,\n" +
       "        (select CATEGORY_DIM_ID from DW_ITEM_DIM where ITEM_DIM_ID = dw.ITEM_DIM_ID) CATEGORY_DIM_ID, \n" +
       "        sum("+ PRICE_COL +") SUM_PRICE, \n" +
       "        sum("+ QTY_COL +") SUM_QTY,  \n" +
       "         0 PRIOR_SUM_PRICE, \n" +
       "         0 PRIOR_SUM_QTY \n" +
       "  from DW_ORDER_FACT dw \n"+
       "    where  DATE_DIM_ID in ( " + subSqlForCurrPeriod + " ) \n" +
      filterCond +
//       "    group by  ITEM_DIM_ID, CATEGORY_DIM_ID \n" +
       "    group by  ITEM_DIM_ID \n" +
       " union all \n" +
       "select   ITEM_DIM_ID as ITEM_ID, \n" +
       "        (select CATEGORY_DIM_ID from DW_ITEM_DIM where ITEM_DIM_ID = dw.ITEM_DIM_ID) CATEGORY_DIM_ID, \n" +
       "        0 SUM_PRICE, \n" +
       "        0 SUM_QTY,  \n" +
       "        sum("+ PRICE_COL +") PRIOR_SUM_PRICE, \n" +
       "        sum("+ QTY_COL +")  PRIOR_SUM_QTY \n" +
       "  from DW_ORDER_FACT dw \n"+
      "    where  DATE_DIM_ID in ( " + subSqlForPriorPeriod + " ) \n" +
      filterCond +
//      "    group by  ITEM_DIM_ID, CATEGORY_DIM_ID \n" ;
      "    group by  ITEM_DIM_ID \n" ;

      return sql;
     }


  protected String getQuerySqlForCurrentY(String subSelect) {
      String sql =
          "select  ITEM_ID, CATEGORY_DIM_ID, \n" +
          "        sum(SUM_PRICE) SUM_PRICE, sum(SUM_QTY) SUM_QTY, \n " +
          "        sum(PRIOR_SUM_PRICE) PRIOR_SUM_PRICE, sum(PRIOR_SUM_QTY) PRIOR_SUM_QTY \n" +
          " from ( \n" +
          subSelect +
          " ) group by ITEM_ID, CATEGORY_DIM_ID  \n";

      return sql;
    }


    protected IdVector getListOfJDItems(Connection con, String pGroupName)  {
 //     String subSql =
 //          " select distinct BUS_ENTITY_ID from CLW_GROUP_ASSOC ga, CLW_GROUP g \n" +
 //          " where ga.GROUP_ID =g.GROUP_ID \n" +
 //          "   and g.SHORT_DESC = '" + pGroupName + "' \n" +
 //          "   and g.GROUP_TYPE_CD = '" + RefCodeNames.GROUP_TYPE_CD.MANUFACTURER + "' \n" +
 //          "   and g.GROUP_STATUS_CD= '" + RefCodeNames.GROUP_STATUS_CD.ACTIVE + "'";


    IdVector idV = null;
    try {
      String sql =
         " select ITEM_DIM_ID from DW_ITEM_DIM where UPPER(JD_ITEM_FL) = 'TRUE' \n" ;

//      String sql =
//          " SELECT item_Id FROM clw_item_mapping \n" +
//          " WHERE bus_entity_id IN (" +
//          subSql +
//          ") \n" +
//          "   AND  ITEM_MAPPING_CD = '" + RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER + "'";

       Statement stmt = con.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       idV = new IdVector();
       while (rs.next()) {
         Integer beId = new Integer(rs.getInt(1));
         idV.add(beId);
       }
       stmt.close();

    }
    catch (SQLException exc) {
       exc.printStackTrace();
    }
     return idV;
    }


}
