/*
 * BaseJDReport.java
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
import java.math.MathContext;
import org.apache.log4j.Logger;

/**
 * Picks up orders and agreates it on Sites
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 * @param pAccountId account Id optionally,
 */
public abstract class BaseJDReportDW  implements GenericReportMulti {
   private final static Logger log = Logger.getLogger(BaseJDReportDW.class);
    /** Creates a new instance of DistributorInvoiceProfitReport */
    public BaseJDReportDW() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    protected static final String REPORT_NAME    = "REPORT_NAME";

    protected static final String GROUPBY = "Group by";
    protected static final String CURRENT_NET_SALES = "Current Yr Net Sales $";
    protected static final String PRIOR_NET_SALES = "Prior Yr Net Sales $";
    protected static final String PROC_OF_TOTAL = "% of Total Sales %";
    protected static final String CURRENT_UNITS = "Current Yr Units";
    protected static final String PRIOR_UNITS = "Prior Yr Units";


    protected static final String NET_SALES_GROWTH = "Net Growth %";
    protected static final String UNIT_GROWTH = "Unit Growth %";
    protected static final String GRAND_TOTAL ="Grand Total";

    protected static final String CHEMICALS_TOTAL ="JohnsonDiversey Chemical Total";

    protected static final String JD_GROUP_NAME = "JD Brands";
    protected static final BigDecimal EXTREM = new BigDecimal(999999999);
    // define names of the Analitic Report Controls
    protected static final String STORE_S        = "DW_STORE_SELECT";
    protected static final String BEG_DATE_S     = "DW_BEGIN_DATE";
    protected static final String END_DATE_S     = "DW_END_DATE";
    protected static final String CATEGORY_OPT_S = "DW_CATEGORY_OPT";
    protected static final String DSR_OPT_S      = "DW_DSR_OPT";
    protected static final String VERTICAL_OPT_S = "DW_VERTICAL_OPT";
    protected static final String REGION_OPT_S   = "DW_REGION_OPT";
    protected static final String LOCATE_ACCOUNT_MULTI_S    = "DW_LOCATE_ACCOUNT_MULTI_OPT";
    protected static final String LOCATE_SITE_MULTI_OPT_S   = "DW_LOCATE_SITE_MULTI_OPT";
    protected static final String LOCATE_MANUFACTURER_OPT_S = "DW_LOCATE_MANUFACTURER_OPT";
    protected static final String LOCATE_DISTRIBUTOR_OPT_S  = "DW_LOCATE_DISTRIBUTOR_OPT";
    protected static final String LOCATE_ITEM_OPT_S         = "DW_LOCATE_ITEM_OPT";

    protected static final String FONT_NAME = "Arial";
    protected static final int FONT_SIZE = 10;
    protected static final String[] COL_WIDTH = new String[]{"34","35","27","29","14","14","9","10","12","12","14","20"};
//    protected static final String[] COL_WIDTH = new String[]{"34","40","27","29","19","16","9","15","21","19","14","21"};

    protected static final String BOLD_STYLE = "BOLD";


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getReportConnection();
        String errorMess = "No error";

        GenericReportResultView result = GenericReportResultView.createValue();
        String reportName = (String) pParams.get(REPORT_NAME);
        result.setTitle( getReportTitle(con, reportName, pParams));
        result.setHeader(getReportHeader());
        result.setColumnCount(getReportHeader().size());
        result.setTable(new ArrayList());

        //====  Request for values ==== //
        try {
          ArrayList repYList = getResultOfQuery(con, pParams );
          GenericReportResultViewVector resultV = calculateReportData (result, repYList);
          HashMap userStyles = createReportStyleDescriptor();
          result.setUserStyle(userStyles);
          result.setFreezePositionColumn(getFreezeColumns());
          result.setFreezePositionRow(result.getTitle().size()+2);

          return resultV;
        }
        catch (SQLException exc) {
          errorMess = "Error. Report.BaseJDReport() SQL Exception happened. " +  exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
          errorMess = "Error. Report.BaseJDReport() Exception happened. " +  exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }

    }

    protected HashMap createReportStyleDescriptor(){

    /*
   (String parm1, String parm2, String parm3, String parm4, short parm5, String parm6, String parm7, String parm8, String parm9, int[] parm10)
   */
      GenericReportStyleView colSite = new GenericReportStyleView("COL_SITE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[1],0 );
      GenericReportStyleView colCat = new GenericReportStyleView("COL_CATEGORY","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,"27",0 );
      GenericReportStyleView colSupp = new GenericReportStyleView("COL_SUPPLIER","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,"26",0 );

      GenericReportStyleView colSkuNum = new GenericReportStyleView("COL_SKU_NUM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,"11",0 );
      GenericReportStyleView colSkuName = new GenericReportStyleView("COL_SKU_NAME","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,"47",0 );
      GenericReportStyleView colPack = new GenericReportStyleView("COL_PACK","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,"5",0 );
      GenericReportStyleView colUom = new GenericReportStyleView("COL_UOM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,"5",0 );
      GenericReportStyleView colSize = new GenericReportStyleView("COL_SIZE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,"13",0 );

      GenericReportStyleView colCurSales = new GenericReportStyleView("COL_CUR_SALES",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[4],0 );
      GenericReportStyleView colPriSales = new GenericReportStyleView("COL_PRI_SALES",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[5],0 );
      GenericReportStyleView colProcOfTot = new GenericReportStyleView("COL_PROC_TOT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[6],0 );
      GenericReportStyleView colSalesGr = new GenericReportStyleView("COL_SALES_GR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );
      GenericReportStyleView colCurUnit = new GenericReportStyleView("COL_CUR_UNIT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[8],0 );
      GenericReportStyleView colPriUnit = new GenericReportStyleView("COL_PRI_UNIT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[9],0 );

      GenericReportStyleView colUnitGr = new GenericReportStyleView("COL_UNIT_GR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[10],0 );
      GenericReportStyleView colMargin = new GenericReportStyleView("COL_MARGIN",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[11],0 );

      GenericReportStyleView typeAmountD = new GenericReportStyleView("TYPE_AMOUNT_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );
      GenericReportStyleView typePercentD = new GenericReportStyleView("TYPE_PERCENT_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"0.00%",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );
      GenericReportStyleView typeQtyD = new GenericReportStyleView("TYPE_QTY_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );

      GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
      reportDesc.setUserStyle(colSite.getStyleName(), colSite);
      reportDesc.setUserStyle(colCat.getStyleName(), colCat);
      reportDesc.setUserStyle(colSupp.getStyleName(), colSupp);
      reportDesc.setUserStyle(colSkuNum.getStyleName(), colSkuNum);
      reportDesc.setUserStyle(colSkuName.getStyleName(), colSkuName);
      reportDesc.setUserStyle(colPack.getStyleName(), colPack);
      reportDesc.setUserStyle(colUom.getStyleName(), colUom);
      reportDesc.setUserStyle(colSize.getStyleName(), colSize);

      reportDesc.setUserStyle(colCurSales.getStyleName(), colCurSales);
      reportDesc.setUserStyle(colPriSales.getStyleName(), colPriSales);
      reportDesc.setUserStyle(colProcOfTot.getStyleName(), colProcOfTot);
      reportDesc.setUserStyle(colSalesGr.getStyleName(), colSalesGr);
      reportDesc.setUserStyle(colCurUnit.getStyleName(), colCurUnit);
      reportDesc.setUserStyle(colPriUnit.getStyleName(), colPriUnit);
      reportDesc.setUserStyle(colUnitGr.getStyleName(), colUnitGr);
      reportDesc.setUserStyle(colMargin.getStyleName(), colMargin);

      reportDesc.setUserStyle(typeAmountD.getStyleName(), typeAmountD);
      reportDesc.setUserStyle(typePercentD.getStyleName(), typePercentD);
      reportDesc.setUserStyle(typeQtyD.getStyleName(), typeQtyD);

      HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
    return styleDesc;
  }


    private String getParamValue (Map pParams, String pControlName){
      String value = null;
      if (pParams.containsKey(pControlName) &&  Utility.isSet( (String) pParams.get(pControlName))) {
        value = (String) pParams.get(pControlName);
      }
      return value;
    }

    protected GenericReportResultViewVector calculateReportData (GenericReportResultView result , ArrayList repYList ){
          //====  Define Total Values==== //
          MathContext mc = new MathContext(0);
          BigDecimal currentYNetSalesTot = new BigDecimal(0, mc);
          BigDecimal currentYUnitTot = new BigDecimal(0, mc);

          BigDecimal priorYNetSalesTot = new BigDecimal(0, mc);
          BigDecimal priorYUnitTot = new BigDecimal(0, mc);

          BigDecimal procNetSalesGrowth = EXTREM;
          BigDecimal procUnitGrowth = EXTREM;

          //   calculate Grand total values
          Iterator rsI = repYList.iterator();
           while (rsI.hasNext()) {
            JDReportView currYItem = (JDReportView) rsI.next();
            BigDecimal sumPrice = currYItem.getSumPrice().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQty = currYItem.getSumQty().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumPricePre = currYItem.getSumPricePre().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQtyPre   = currYItem.getSumQtyPre().setScale(0, BigDecimal.ROUND_HALF_UP);

            if (!currYItem.getGroupByName().equals(this.CHEMICALS_TOTAL)){
              currentYNetSalesTot = currentYNetSalesTot.add(sumPrice);
              currentYUnitTot = currentYUnitTot.add(sumQty);
              priorYNetSalesTot = priorYNetSalesTot.add(sumPricePre);
              priorYUnitTot = priorYUnitTot.add(sumQtyPre);
            }
          }

          // calculate rows

          for (int i = 0; i < repYList.size(); i++) {
            ArrayList row = new ArrayList();
            JDReportView currYItem = (JDReportView) repYList.get(i);
            BigDecimal sumPrice = currYItem.getSumPrice().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQty   = currYItem.getSumQty().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumPricePre = currYItem.getSumPricePre().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQtyPre   = currYItem.getSumQtyPre().setScale(0, BigDecimal.ROUND_HALF_UP);


            BigDecimal totalPr = new BigDecimal(0);
            BigDecimal netSalesGrowthPr = EXTREM;
            BigDecimal unitGrowthPr = EXTREM;

            // calculate Percentages
            //BigDecimal precision = new BigDecimal(0.0001);
            if ( currentYNetSalesTot.abs().compareTo(new BigDecimal(0)) > 0){
              totalPr = sumPrice.divide(currentYNetSalesTot,4,BigDecimal.ROUND_HALF_UP);
            }
            if ( sumPricePre.abs().compareTo(new BigDecimal(0)) > 0){
              netSalesGrowthPr = sumPrice.subtract(sumPricePre).divide( sumPricePre,4,BigDecimal.ROUND_HALF_UP);
              netSalesGrowthPr = netSalesGrowthPr.setScale(3, BigDecimal.ROUND_HALF_UP);
             }
            if ( sumQtyPre.abs().compareTo(new BigDecimal(0)) > 0){
              unitGrowthPr = (sumQty.subtract(sumQtyPre)).divide(sumQtyPre, 4, BigDecimal.ROUND_HALF_UP);
              unitGrowthPr = unitGrowthPr.setScale(3, BigDecimal.ROUND_HALF_UP);
            }

            Map reportRowMap = new HashMap();
            reportRowMap.put(this.GROUPBY, currYItem.getGroupByName());
            reportRowMap.put(this.CURRENT_NET_SALES, sumPrice);
            reportRowMap.put(this.PRIOR_NET_SALES, sumPricePre);
            reportRowMap.put(this.PROC_OF_TOTAL, totalPr);
            reportRowMap.put(this.NET_SALES_GROWTH, netSalesGrowthPr);
            reportRowMap.put(this.CURRENT_UNITS, sumQty);
            reportRowMap.put(this.PRIOR_UNITS, sumQtyPre);
            reportRowMap.put(this.UNIT_GROWTH, unitGrowthPr);

           addRowToReport(row, reportRowMap, currYItem );
           result.getTable().add(row);
          }
          //==============   calculating Total Percentages ===============================

          if ( priorYNetSalesTot.abs().compareTo(new BigDecimal(0)) > 0){
            procNetSalesGrowth = (currentYNetSalesTot.subtract(priorYNetSalesTot)).divide(priorYNetSalesTot, 4, BigDecimal.ROUND_HALF_UP);
            procNetSalesGrowth = procNetSalesGrowth.setScale(3, BigDecimal.ROUND_HALF_UP);
          }
          if ( priorYUnitTot.abs().compareTo(new BigDecimal(0)) > 0){
            procUnitGrowth = (currentYUnitTot.subtract(priorYUnitTot)).divide(priorYUnitTot, 4, BigDecimal.ROUND_HALF_UP);
            procUnitGrowth = procUnitGrowth.setScale(3, BigDecimal.ROUND_HALF_UP);
          }
          //=============add Total Row as summary results========//
           ArrayList rowTot = new ArrayList();
           Map reportRowMapTot = new HashMap();
           reportRowMapTot.put(this.GROUPBY, GRAND_TOTAL );
           reportRowMapTot.put(this.CURRENT_NET_SALES, currentYNetSalesTot);
           reportRowMapTot.put(this.PRIOR_NET_SALES, priorYNetSalesTot);
           reportRowMapTot.put(this.PROC_OF_TOTAL, "");
           reportRowMapTot.put(this.NET_SALES_GROWTH, procNetSalesGrowth);
           reportRowMapTot.put(this.CURRENT_UNITS, currentYUnitTot);
           reportRowMapTot.put(this.PRIOR_UNITS, priorYUnitTot);
           reportRowMapTot.put(this.UNIT_GROWTH, procUnitGrowth);

           addRowToReport(rowTot, reportRowMapTot, null);
           result.getTable().add(rowTot);

          GenericReportResultViewVector resultV = new GenericReportResultViewVector();
          resultV.add(result);
        return resultV;
    }

    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
       GenericReportColumnViewVector title = new GenericReportColumnViewVector();

       title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",pTitle,0,255,"VARCHAR2"));

       String controlLabel = ReportingUtils.getControlLabel(STORE_S, pParams, "Store");
       title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", controlLabel +  getListOfNames(con, STORE_S , pParams),0,255,"VARCHAR2"));
       title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Date Begin : " + (String) pParams.get(BEG_DATE_S) + ";  End :" + (String) pParams.get(END_DATE_S),0,255,"VARCHAR2"));

       String controlName = LOCATE_ACCOUNT_MULTI_S;
       controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Accounts");
       if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
         title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
       }
       controlName = LOCATE_SITE_MULTI_OPT_S;
       controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Sites");
       if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
         title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
       }
       controlName = this.LOCATE_MANUFACTURER_OPT_S;
       controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Manufacturers");
       if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
         title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
       }
       controlName = this.CATEGORY_OPT_S;
       controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Category");
       if (pParams.containsKey(controlName) && Utility.isSet( (String) pParams.get(controlName))) {
         title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", controlLabel + (String) pParams.get(controlName), 0, 255, "VARCHAR2"));
       }

       controlName = this.LOCATE_ITEM_OPT_S;
       controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Items");
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
      StringBuffer nms = new StringBuffer();
      String typeDim = "";
      String tableName = "";
      if (controlName.equals(STORE_S)){
        typeDim = "STORE_NAME";
        tableName = "STORE";
      } else if (controlName.equals(this.LOCATE_ACCOUNT_MULTI_S)) {
        typeDim = "ACCOUNT_NAME";
        tableName = "ACCOUNT";
      } else if (controlName.equals(this.LOCATE_SITE_MULTI_OPT_S)) {
        typeDim = "SITE_NAME";
        tableName = "SITE";
      } else if (controlName.equals(this.LOCATE_DISTRIBUTOR_OPT_S)) {
        typeDim = "JD_DIST_NAME";
        tableName = "DISTRIBUTOR";
      } else if (controlName.equals(this.LOCATE_MANUFACTURER_OPT_S)) {
        typeDim = "JD_MANUF_NAME";
        tableName = "MANUFACTURER";
      } else if (controlName.equals(this.LOCATE_ITEM_OPT_S)) {
        typeDim = "JD_ITEM_DESC";
        tableName = "ITEM";
      }

      if (idsS.length() != 0) {
        try {

          String sql =
              " select " + typeDim + " from DW_" + tableName + "_DIM where " + tableName + "_DIM_ID \n" +
              " in (" + idsS + ")";

          log.info(".getListOfNames()--->SQL: " + sql);
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


    public Date calcPreDate (Date currDate){
      Calendar cal = Calendar.getInstance();
      cal.setTime(currDate);
      int year = cal.get(Calendar.YEAR);
      cal.set(Calendar.YEAR, year - 1);
      Date datePre = cal.getTime();
      return datePre;
    }

    protected abstract GenericReportColumnViewVector getReportHeader() ;
    protected abstract ArrayList getResultOfQuery(Connection conn, Map pParams) throws Exception ;

    protected void addRowToReport(ArrayList row , Map reportRowMap, JDReportView currYItem) {
      String groupByRow =  (String)reportRowMap.get(this.GROUPBY);

      boolean totalRowFlag = (groupByRow != null && groupByRow.equals(GRAND_TOTAL )) ? true : false;
      if (currYItem == null && totalRowFlag ) {
        row.add(reportRowMap.get(this.GROUPBY));
      } else {
        row.add(currYItem.getGroupByName());
      }
      row.add(reportRowMap.get(this.CURRENT_NET_SALES));
      row.add(reportRowMap.get(this.PRIOR_NET_SALES));
      row.add(reportRowMap.get(this.PROC_OF_TOTAL));
      row.add( ReportingUtils.validPercent (reportRowMap.get(this.NET_SALES_GROWTH)));

      row.add( ReportingUtils.validPercent(reportRowMap.get(this.UNIT_GROWTH)));
    }

    protected String createFilterCondition(Map pParams) {

      String storeFilter = getParamValue(pParams, this.STORE_S);
      String accountFilter = getParamValue(pParams, LOCATE_ACCOUNT_MULTI_S);
      String siteFilter  = getParamValue(pParams, LOCATE_SITE_MULTI_OPT_S);

      String manufFilter  = getParamValue(pParams, LOCATE_MANUFACTURER_OPT_S);
      String categoryFilter  = getParamValue(pParams, CATEGORY_OPT_S);
      String itemFilter  = getParamValue(pParams, LOCATE_ITEM_OPT_S);

      boolean accountFl = Utility.isSet(accountFilter);
      boolean siteFl = Utility.isSet(siteFilter);
      boolean manufFl = Utility.isSet(manufFilter);
      boolean categoryFl = Utility.isSet(categoryFilter);
      boolean itemFl = Utility.isSet(itemFilter);

      String storeCondStr =  " AND STORE_DIM_ID =   " + storeFilter + " \n" ;
      String categoryCondStr = (!categoryFl) ? "" :
 //                      "SELECT ITEM_DIM_ID FROM DW_ITEM_DIM WHERE CATEGORY_DIM_ID IN \n" +
 //                      " (SELECT CATEGORY_DIM_ID FROM DW_CATEGORY_DIM WHERE \n" +
 //                      "  UPPER(NVL(jd_category3, NVL(jd_category2,jd_category1))) like '%" +
 //                       categoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n"+
 //                      ") \n";
                   "SELECT ITEM_DIM_ID FROM DW_ITEM_DIM i, DW_CATEGORY_DIM c \n" +
                   "WHERE i.CATEGORY_DIM_ID = c.CATEGORY_DIM_ID  \n" +
                   "  AND i.STORE_DIM_ID =   " + storeFilter + " \n" +
                   "  AND UPPER(NVL(jd_category3, NVL(jd_category2,jd_category1))) like '%" +
                    categoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";


      String filterCond = storeCondStr +
          "       AND QUANTITY is not NULL AND PRICE is not NULL \n" +
          (accountFl?" and ACCOUNT_DIM_ID in ( \n" + accountFilter + ") \n":"")+
          (siteFl?" and SITE_DIM_ID in ( \n" + siteFilter + ") \n":"")+
          (itemFl?" and ITEM_DIM_ID in ( \n" + itemFilter + ") \n":"")+
          (manufFl?" and MANUFACTURER_DIM_ID in ( \n" + manufFilter + ") \n":"")+
    //      (categoryFl?" and CATEGORY_DIM_ID in ( \n" + categoryCondStr + ") \n":"");
          (categoryFl?" and ITEM_DIM_ID in ( \n" + categoryCondStr + ") \n":"");

//          (distFl?" and DISTRIBUTOR_DIM_ID in ( \n" + distributorFilter + ") \n":"")+

      return filterCond;
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


    public class JDReportViewVector extends java.util.ArrayList implements Comparator {
       /**
        * Constructor.
        */
       public JDReportViewVector () {}

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
         JDReportView obj1 = (JDReportView)o1;
         JDReportView obj2 = (JDReportView)o2;

         if("SumPrice".equalsIgnoreCase(_sortField)) {
           BigDecimal i1 = obj1.getSumPrice();
           BigDecimal i2 = obj2.getSumPrice();
           if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
           else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
         }
         if("GroupByName".equalsIgnoreCase(_sortField)) {
          String i1 = obj1.getGroupByName();
          String i2 = obj2.getGroupByName();
          if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
          else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
        }

         if(!_ascFl) retcode = -retcode;
         return retcode;
       }
   }
   protected Object putCellStyle(String style, Object obj) {
     if (style == null){
       return obj;
     }
     HashMap map = new HashMap();
     map.put(style, obj);
     return map;
   }

    public class JDReportView extends ValueObject {

    private String mGroupByName;
    private String mGroupByType;
    private String mSiteId;
    private String mItemId;
    private String mSiteName;
    private String mSku;
    private String mSkuName;
    private String mSubCat;
    private String mPack;
    private String mUom;
    private String mSize;
    private BigDecimal mSumPrice;
    private BigDecimal mSumCost;
    private BigDecimal mSumQty;
    private BigDecimal mSumPricePre;
    private BigDecimal mSumCostPre;
    private BigDecimal mSumQtyPre;

    /**
     * Constructor.
     */
    public JDReportView ()
    {
        mSiteId = "";
        mItemId = "";
        mSiteName="";
        mSku = "";
        mSkuName = "";
        mSubCat = "";
        mGroupByName="";
        mGroupByType="";
        mPack="";
        mUom= "";
        mSize="";

    }


    public void setSiteId(String pSiteId){
        this.mSiteId = pSiteId;
    }
    public String getSiteId(){
        return mSiteId;
    }
    public void setSiteName(String pSiteName){
        this.mSiteName = pSiteName;
    }
    public String getSiteName(){
        return mSiteName;
    }
    public void setGroupByName(String pGroupByName){
        this.mGroupByName = pGroupByName;
    }
    public String getGroupByName(){
        return mGroupByName;
    }
    public void setGroupByType(String pGroupByType){
        this.mGroupByType = pGroupByType;
    }
    public String getGroupByType(){
        return mGroupByType;
    }

    public void setItemId(String pItemId){
        this.mItemId = pItemId;
    }
    public String getItemId(){
        return mItemId;
    }
    public void setSku(String pSku){
      this.mSku = pSku;
    }
    public String getSku() {
      return mSku;
    }
    public void setSkuName(String pSkuName){
      this.mSkuName = pSkuName;
    }
    public String getSkuName() {
      return mSkuName;
    }

    public void setSubCat(String pSubCat) {
      this.mSubCat = pSubCat;
    }

    public String getSubCat() {
      return mSubCat;
    }

    public void setSumPrice(BigDecimal pSumPrice){
        this.mSumPrice = pSumPrice;
    }
    public BigDecimal getSumPrice(){
        return mSumPrice;
    }
    public void setSumCost(BigDecimal pSumCost){
      this.mSumCost = pSumCost;
    }
    public BigDecimal getSumCost() {
      return mSumCost;
    }

    public void setSumQty(BigDecimal pSumQty) {
      this.mSumQty = pSumQty;
    }

    public BigDecimal getSumQty() {
      return mSumQty;
    }
    //--------------------------------
    public void setSumPricePre(BigDecimal pSumPricePre){
      this.mSumPricePre = pSumPricePre;
    }
    public BigDecimal getSumPricePre() {
      return mSumPricePre;
    }
    public void setSumCostPre(BigDecimal pSumCostPre) {
      this.mSumCostPre = pSumCostPre;
    }
    public BigDecimal getSumCostPre() {
      return mSumCostPre;
    }

    public void setSumQtyPre(BigDecimal pSumQtyPre) {
      this.mSumQtyPre = pSumQtyPre;
    }

    public BigDecimal getSumQtyPre() {
      return mSumQtyPre;
    }
    //------------------------------------------
    public void setPack(String pPack) {
      this.mPack = pPack;
    }

    public String getPack() {
      return mPack;
    }

    public void setUom(String pUom) {
      this.mUom = pUom;
    }

    public String getUom() {
      return mUom;
    }

    public void setSize(String pSize) {
      this.mSize = pSize;
    }

    public String getSize() {
      return mSize;
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
