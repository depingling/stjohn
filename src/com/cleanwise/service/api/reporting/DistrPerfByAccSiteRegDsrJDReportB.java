/*
 * DistrPerfByAccountJDReport.java
 *
 * Created on September 12, 2008, 4:43 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import com.cleanwise.service.api.framework.ValueObject;
import java.util.*;
import com.cleanwise.service.api.util.Utility;
import java.math.MathContext;

/**
 * Picks up distributor invoices and agreates it on Account
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 *
 */
public class DistrPerfByAccSiteRegDsrJDReportB  implements GenericReportMulti {

    /** Creates a new instance of DistributorInvoiceProfitReport */
    public DistrPerfByAccSiteRegDsrJDReportB() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    // define names of the Analitic Report Controls
     protected static final String REPORT_NAME    = "REPORT_NAME";
     protected static final String STORE_S        = "DW_STORE_SELECT";
     protected static final String BEG_DATE_S     = "DW_BEGIN_DATE";
     protected static final String END_DATE_S     = "DW_END_DATE";
     protected static final String CATEGORY_OPT_S = "DW_CATEGORY_OPT";
     protected static final String DSR_OPT_S      = "DW_DSR_OPT";
     protected static final String VERTICAL_OPT_S = "DW_VERTICAL_OPT";
     protected static final String REGION_OPT_S   = "DW_REGION_OPT";
     protected static final String CONNECT_CUST_OPT_S   = "DW_CONNECT_CUST_OPT";
     protected static final String REGION_AUTOSUGGEST_OPT_S   = "DW_REGION_AUTOSUGGEST_OPT";
     protected static final String DSR_AUTOSUGGEST_OPT_S      = "DW_DSR_AUTOSUGGEST_OPT";
     protected static final String CATEGORY_AUTOSUGGEST_OPT_S = "DW_CATEGORY_AUTOSUGGEST_OPT";
     protected static final String LOCATE_ACCOUNT_MULTI_S    = "DW_LOCATE_ACCOUNT_MULTI_OPT";
     protected static final String LOCATE_SITE_MULTI_OPT_S   = "DW_LOCATE_SITE_MULTI_OPT";
     protected static final String LOCATE_MANUFACTURER_OPT_S = "DW_LOCATE_MANUFACTURER_OPT";
     protected static final String LOCATE_DISTRIBUTOR_OPT_S  = "DW_LOCATE_DISTRIBUTOR_OPT";
     protected static final String LOCATE_ITEM_OPT_S         = "DW_LOCATE_ITEM_OPT";



     protected static final String BOLD_STYLE = "BOLD";

    protected static final String ACCOUNT = "Customer";//"Account";
    protected static final String SHIP_TO = "Ship To";
    protected static final String GROUPBY = "Group by";
    protected static final String CURRENT_NET_SALES = "Current Year Sales $";
    protected static final String PRIOR_NET_SALES = "Prior Year Sales $";
    protected static final String CURRENT_UNIT_SALES = "Current Year Unit Sales";
    protected static final String PRIOR_UNIT_SALES = "Prior Year Unit Sales";
    protected static final String PROC_OF_TOTAL = "% of Total %";
    protected static final String NET_SALES_GROWTH = "Sales Growth $";
    protected static final String NET_SALES_GROWTH_PR = "Sales Growth %%";
    protected static final String UNIT_GROWTH = "Unit Growth %%";
    protected static final String MARGIN = "Current Year Margin %%";
 //   protected static final String NET_SALES_DECLINE = "Net Sales Decline %%";
 //   protected static final String UNIT_DECLINE = "Unit Decline %%";
    protected static final String GRAND_TOTAL ="Grand Total";
    protected static final String SUB_TOTAL ="Sub Total";

    protected static final String PRICE_COL = "LINE_AMOUNT";
    protected static final String QTY_COL   = "QUANTITY";
    protected static final String COST_COL  = "LINE_COST";

    protected static final BigDecimal EXTREM = new BigDecimal(999999999);
    protected static final String FONT_NAME = "Arial";
    protected static final int FONT_SIZE = 10;
//    protected static final String[] COL_WIDTH = new String[]{"34","40","27","29","19","17","9","15","22","19","14","21"};
    protected static final String[] COL_WIDTH = new String[]{"34","40","27","29", "12","12","9","9","10","11","11","11"};


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getReportConnection();
        String errorMess = "No error";
        //=========== getting parameters =======================================
        String reportName = (String) pParams.get(REPORT_NAME);
        GenericReportResultView result = GenericReportResultView.createValue();
        result.setTitle( getReportTitle(con, reportName, pParams));
        result.setHeader(getReportHeader());
        result.setColumnCount(getReportHeader().size());
        result.setTable(new ArrayList());

        //====  Request for values ==== //
        try {
          ArrayList repYList = getResultOfQuery(con, pParams);
          GenericReportResultViewVector resultV = calculateReportData (result, repYList);
          HashMap userStyles = createReportStyleDescriptor();
          result.setUserStyle(userStyles);
          result.setFreezePositionColumn(2);
          result.setFreezePositionRow(result.getTitle().size()+2);
         return resultV;
        }
        catch (SQLException exc) {
          errorMess = " SQL Exception happened. " +  exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }
        catch (Exception exc) {
          errorMess = " Exception happened. " +  exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
        }

    }

    protected HashMap createReportStyleDescriptor(){

        /*
       (String parm1, String parm2, String parm3, String parm4, short parm5, String parm6, String parm7, String parm8, String parm9, int[] parm10)
       */
        //int[] mergeRegion = new int[]{1,1,1,getReportHeader().size()};
        //<style name><value type><value format><font name><font size><font type><font color><background color><aliment><wrap flag><merge (not working)><data class NULL><width><scale>

        //GenericReportStyleView colNew = new GenericReportStyleView("COLUMN_NEW","TEXT",null,null,-1,null,null,null,"LEFT",false, null,null,"100",0 );
        //GenericReportStyleView colHeaderGrey = new GenericReportStyleView("COLUMN_STYLE_1",ReportingUtils.DATA_CATEGORY.FLOAT,"$#,##0.00",null,-1,null,null,null,null,true, null,null,"100",0 );
        //GenericReportStyleView colHeaderPercent = new GenericReportStyleView("COLUMN_STYLE_2",ReportingUtils.DATA_CATEGORY.FLOAT,"0.00%",null,-1,null,null,null,null,true, null,null,"80",0 );
        GenericReportStyleView colAccount = new GenericReportStyleView("COL_ACCOUNT","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[0],0 );
        GenericReportStyleView colSite = new GenericReportStyleView("COL_SITE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[1],0 );
        GenericReportStyleView colBranch = new GenericReportStyleView("COL_BRANCH","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[2],0 );
        GenericReportStyleView colDSR = new GenericReportStyleView("COL_DSR","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[3],0 );

        GenericReportStyleView colCurSales = new GenericReportStyleView("COL_CUR_SALES",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[4],0 );
        GenericReportStyleView colPriSales = new GenericReportStyleView("COL_PRI_SALES",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[5],0 );
        GenericReportStyleView colProcOfTot = new GenericReportStyleView("COL_PROC_TOT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[6],0 );
        GenericReportStyleView colSalesGr = new GenericReportStyleView("COL_SALES_GR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );
        GenericReportStyleView colCurUnit = new GenericReportStyleView("COL_CUR_UNIT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[8],0 );
        GenericReportStyleView colPriUnit = new GenericReportStyleView("COL_PRI_UNIT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[9],0 );

        GenericReportStyleView colUnitGr = new GenericReportStyleView("COL_UNIT_GR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[10],0 );
        GenericReportStyleView colMargin = new GenericReportStyleView("COL_MARGIN",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[11],0 );

//        GenericReportStyleView typeAmount = new GenericReportStyleView("TYPE_AMOUNT",null,null,null,-1,"BOLD",null,null,"CENTER",true, null,null,"8",0 );
//        GenericReportStyleView typePercent = new GenericReportStyleView("TYPE_PERCENT",null,null,null,-1,"BOLD",null,null,"CENTER",true, null,null,"8",0 );
        GenericReportStyleView typeAmountD = new GenericReportStyleView("TYPE_AMOUNT_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );
        GenericReportStyleView typePercentD = new GenericReportStyleView("TYPE_PERCENT_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"0.00%",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );
        GenericReportStyleView typeQtyD = new GenericReportStyleView("TYPE_QTY_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );

        GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
        reportDesc.setUserStyle(colAccount.getStyleName(), colAccount);
        reportDesc.setUserStyle(colSite.getStyleName(), colSite);
        reportDesc.setUserStyle(colBranch.getStyleName(), colBranch);
        reportDesc.setUserStyle(colDSR.getStyleName(), colDSR);

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
      if (pParams.containsKey(pControlName) &&
          Utility.isSet( (String) pParams.get(pControlName))) {
        value = (String) pParams.get(pControlName);
      }
      return value;
    }

    protected GenericReportResultViewVector calculateReportData (GenericReportResultView result , ArrayList repYList ){
          //====  Define Total Values==== //
          MathContext mc = new MathContext(0);
          BigDecimal currentYNetSalesTot = new BigDecimal(0,mc);
          BigDecimal currentYUnitTot = new BigDecimal(0,mc);
          BigDecimal currentYCostTot = new BigDecimal(0,mc);

          BigDecimal priorYNetSalesTot = new BigDecimal(0,mc);
          BigDecimal priorYUnitTot = new BigDecimal(0,mc);
          BigDecimal netSalesGrowthTot = new BigDecimal(0,mc);

          BigDecimal procNetSalesGrowth = EXTREM;
          BigDecimal procUnitGrowth = EXTREM;
          BigDecimal procCurrentYMargin =EXTREM;

          //   calculate Grand total values
          Iterator rsI = repYList.iterator();
           while (rsI.hasNext()) {
            DistrPerfJPReportView currYItem = (DistrPerfJPReportView) rsI.next();

            BigDecimal sumPrice = currYItem.getSumPrice().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQty   = currYItem.getSumQty().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumCost = currYItem.getSumCost().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumPricePre = currYItem.getSumPricePre().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQtyPre   = currYItem.getSumQtyPre().setScale(0, BigDecimal.ROUND_HALF_UP);

             if (!currYItem.getGroupByName().startsWith(this.SUB_TOTAL)){
               currentYNetSalesTot = currentYNetSalesTot.add(sumPrice);
               currentYUnitTot = currentYUnitTot.add(sumQty);
               currentYCostTot = currentYCostTot.add(sumCost);
               priorYNetSalesTot = priorYNetSalesTot.add(sumPricePre);
               priorYUnitTot = priorYUnitTot.add(sumQtyPre);
             }
          }
          netSalesGrowthTot = currentYNetSalesTot.subtract(priorYNetSalesTot);

          // calculate rows

          for (int i = 0; i < repYList.size(); i++) {
            ArrayList row = new ArrayList();
            DistrPerfJPReportView currYItem = (DistrPerfJPReportView) repYList.get(i);
            BigDecimal sumPrice = currYItem.getSumPrice().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQty   = currYItem.getSumQty().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumCost = currYItem.getSumCost().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumPricePre = currYItem.getSumPricePre().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal sumQtyPre   = currYItem.getSumQtyPre().setScale(0, BigDecimal.ROUND_HALF_UP);
            BigDecimal netSalesGrowth   = currYItem.getNetSalesGrowth().setScale(0, BigDecimal.ROUND_HALF_UP);

            BigDecimal totalPr = new BigDecimal(0);
            BigDecimal netSalesGrowthPr = EXTREM;
            BigDecimal unitGrowthPr = EXTREM;
            BigDecimal marginPr = new BigDecimal(0);

            // calculate Percentages
            if ( currentYNetSalesTot.abs().compareTo(new BigDecimal(0)) > 0){
              totalPr = sumPrice.divide(currentYNetSalesTot,4,BigDecimal.ROUND_HALF_UP);
              totalPr = totalPr.setScale(4, BigDecimal.ROUND_HALF_UP);

            }
            if ( sumPricePre.abs().compareTo(new BigDecimal(0)) > 0){
              netSalesGrowthPr = sumPrice.subtract(sumPricePre).divide( sumPricePre,4,BigDecimal.ROUND_HALF_UP);
              netSalesGrowthPr = netSalesGrowthPr.setScale(4, BigDecimal.ROUND_HALF_UP);
            }
            if ( sumQtyPre.abs().compareTo(new BigDecimal(0)) > 0){
              unitGrowthPr = (sumQty.subtract(sumQtyPre)).divide(sumQtyPre, 4, BigDecimal.ROUND_HALF_UP);
              unitGrowthPr = unitGrowthPr.setScale(4, BigDecimal.ROUND_HALF_UP);
            }
            if ( sumPrice.abs().compareTo(new BigDecimal(0)) > 0){
              marginPr = (sumPrice.subtract(sumCost)).divide( sumPrice,4,BigDecimal.ROUND_HALF_UP);
              marginPr = marginPr.setScale(4, BigDecimal.ROUND_HALF_UP);
            }

            Map reportRowMap = new HashMap();
            if (Utility.isSet(currYItem.getGroupByName()) && currYItem.getGroupByName().startsWith(SUB_TOTAL)  ){
              reportRowMap.put(this.GROUPBY, SUB_TOTAL);
            } else {
              reportRowMap.put(this.GROUPBY, currYItem.getAccountName());
            }
            reportRowMap.put(this.CURRENT_NET_SALES, sumPrice);
            reportRowMap.put(this.PRIOR_NET_SALES, sumPricePre);
            reportRowMap.put(this.PROC_OF_TOTAL, totalPr);
            reportRowMap.put(this.NET_SALES_GROWTH, netSalesGrowth);
            reportRowMap.put(this.NET_SALES_GROWTH_PR, netSalesGrowthPr);
            reportRowMap.put(this.CURRENT_UNIT_SALES, sumQty);
            reportRowMap.put(this.PRIOR_UNIT_SALES, sumQtyPre);
            reportRowMap.put(this.UNIT_GROWTH, unitGrowthPr);
            reportRowMap.put(this.MARGIN, marginPr);

           addRowToReport(row, reportRowMap, currYItem );
           result.getTable().add(row);
          }
          //==============   calculating Total Percentages ===============================

          if ( priorYNetSalesTot.abs().compareTo(new BigDecimal(0)) > 0){
            procNetSalesGrowth = (currentYNetSalesTot.subtract(priorYNetSalesTot)).divide(priorYNetSalesTot, 4, BigDecimal.ROUND_HALF_UP);
            procNetSalesGrowth = procNetSalesGrowth.setScale(4, BigDecimal.ROUND_HALF_UP);

          }
          if ( priorYUnitTot.abs().compareTo(new BigDecimal(0)) > 0){
            procUnitGrowth = (currentYUnitTot.subtract(priorYUnitTot)).divide(priorYUnitTot, 4, BigDecimal.ROUND_HALF_UP);
            procUnitGrowth = procUnitGrowth.setScale(4, BigDecimal.ROUND_HALF_UP);
         }
          if ( currentYNetSalesTot.abs().compareTo(new BigDecimal(0)) > 0){
            procCurrentYMargin = (currentYNetSalesTot.subtract(currentYCostTot)).divide(currentYNetSalesTot, 4, BigDecimal.ROUND_HALF_UP);
            procCurrentYMargin = procCurrentYMargin.setScale(4, BigDecimal.ROUND_HALF_UP);
          }
          //=============add Total Row as summary results========//
           ArrayList rowTot = new ArrayList();
           Map reportRowMapTot = new HashMap();
           reportRowMapTot.put(this.GROUPBY, GRAND_TOTAL );
           reportRowMapTot.put(this.CURRENT_NET_SALES, currentYNetSalesTot);
           reportRowMapTot.put(this.PRIOR_NET_SALES, priorYNetSalesTot);
           reportRowMapTot.put(this.PROC_OF_TOTAL, "");
           reportRowMapTot.put(this.NET_SALES_GROWTH, netSalesGrowthTot);
           reportRowMapTot.put(this.NET_SALES_GROWTH_PR, procNetSalesGrowth);
           reportRowMapTot.put(this.CURRENT_UNIT_SALES, currentYUnitTot);
           reportRowMapTot.put(this.PRIOR_UNIT_SALES, priorYUnitTot);
           reportRowMapTot.put(this.UNIT_GROWTH, procUnitGrowth);
           reportRowMapTot.put(this.MARGIN, procCurrentYMargin);

           addRowToReport(rowTot, reportRowMapTot, null);
           result.getTable().add(rowTot);

          GenericReportResultViewVector resultV = new GenericReportResultViewVector();
          resultV.add(result);
        return resultV;
    }


      protected GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        return header;
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
      controlName = this.LOCATE_DISTRIBUTOR_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Distributors");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }
      controlName = this.DSR_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Distributor Sales Rep");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = DSR_AUTOSUGGEST_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor Sales Rep : " + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = REGION_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Region");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel  + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = REGION_AUTOSUGGEST_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Brunch : " + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = VERTICAL_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Vertical");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = CONNECT_CUST_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Connection Customer");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }

      return title;
    }

/*    public String getControlLabel(String controlName, HashMap controlInfo, String defaultLabel ){
      String label  = defaultLabel;
      if (controlInfo != null) {
        GenericReportControlView grc = (GenericReportControlView) controlInfo.get(controlName) ;
        if (grc != null) {
          label = (Utility.isSet(grc.getLabel())) ? grc.getLabel().replace(":", "" ) : defaultLabel;
        }
      }
      return label + " : ";
    }
*/
    public Date calcPreDate (Date currDate){
      Calendar cal = Calendar.getInstance();
      cal.setTime(currDate);
      int year = cal.get(Calendar.YEAR);
      cal.set(Calendar.YEAR, year - 1);
      Date datePre = cal.getTime();
      return datePre;
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
        typeDim = "STORE";
        tableName = typeDim;
      } else if (controlName.equals(this.LOCATE_ACCOUNT_MULTI_S)) {
        typeDim = "ACCOUNT";
        tableName = typeDim;
      } else if (controlName.equals(this.LOCATE_SITE_MULTI_OPT_S)) {
        typeDim = "SITE";
        tableName = typeDim;
      } else if (controlName.equals(this.LOCATE_DISTRIBUTOR_OPT_S)) {
        typeDim = "JD_DIST";
        tableName = "DISTRIBUTOR";
      }

      if (idsS.length() != 0) {
        try {

          String sql =
              " select " + typeDim + "_NAME from DW_" + tableName + "_DIM where " + tableName + "_DIM_ID \n" +
              " in (" + idsS + ")";

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

    private String getDsrFilter(Map pParams) {

        String dsrStr = getParamValue(pParams, DSR_OPT_S);

        if (Utility.isSet(dsrStr)) {
            return dsrStr;
        } else {
            String autoSuggDsrStr = getParamValue(pParams, DSR_AUTOSUGGEST_OPT_S);
            if (Utility.isSet(autoSuggDsrStr)) {
                return autoSuggDsrStr;
            } else {
                return dsrStr;
            }
        }
    }

    private String getRegionFilter(Map pParams) {

        String regionStr = getParamValue(pParams, REGION_OPT_S);

        if (Utility.isSet(regionStr)) {
            return regionStr;
        } else {
            String autoSuggRegStr = getParamValue(pParams, REGION_AUTOSUGGEST_OPT_S);
            if (Utility.isSet(autoSuggRegStr)) {
                return autoSuggRegStr;
            } else {
                return regionStr;
            }
        }
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
      row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.NET_SALES_GROWTH_PR))));
      row.add(putCellStyle(style,reportRowMap.get(this.CURRENT_UNIT_SALES)));
      row.add(putCellStyle(style,reportRowMap.get(this.PRIOR_UNIT_SALES)));
      row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.UNIT_GROWTH))));
      row.add(putCellStyle(style,ReportingUtils.validPercent (reportRowMap.get(this.MARGIN))));
    }

    protected DistrPerfJPReportViewVector getResultOfQuery(Connection conn, Map pParams) throws Exception{
      BigDecimal zeroAmt = new BigDecimal(0);
      String selectSqlAccount= getQuerySql( pParams );

      // for accounts if there were sales in "Current Year" and may be there were no sales in "Prior Year"
      String selectSqlCurrY = getQuerySqlForCurrentY(selectSqlAccount);
      // for accounts if there were sales in "Prior Year" and there were no sales in "Current Year"
       String   selectSqlPriorY = getQuerySqlForPriorY(selectSqlAccount);

      String selectSql =
          "select \n" +
          "   (select JD_ACCOUNT_NAME from DW_ACCOUNT_DIM where ACCOUNT_DIM_ID = ACCOUNT ) SHORT_DESC, \n" +
          "   g.* \n"+
          " from \n" +
          "  (" +
            selectSqlCurrY +
          " union  \n" +
            selectSqlPriorY +
          ") g \n" +
          " order by nvl(SUM_PRICE,0) desc, SHORT_DESC \n";

      PreparedStatement pstmt = conn.prepareStatement(selectSql);

      ResultSet rs = pstmt.executeQuery();
      DistrPerfJPReportViewVector psViewV= new DistrPerfJPReportViewVector();
      while (rs.next()){
        DistrPerfJPReportView psView = new DistrPerfJPReportView ();
        String account = (rs.getString("ACCOUNT") != null) ? rs.getString("ACCOUNT") : "0";
        String accountName = (rs.getString("SHORT_DESC") != null) ? rs.getString("SHORT_DESC") : "";
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

    protected String getQuerySqlForCurrentY(String subSelect) {
      String sql =
          " select  c.ACCOUNT , c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
          " from  \n" +
          subSelect +
          " where c.ACCOUNT  = b.ACCOUNT (+) \n";
      return sql;
    }
    protected String getQuerySqlForPriorY(String subSelect) {
      String sql =
          " select  b.ACCOUNT , c.SUM_PRICE, c.SUM_QTY, c.SUM_COST, b.PRIOR_SUM_PRICE, b.PRIOR_SUM_QTY, b.PRIOR_SUM_COST  \n" +
          " from  \n" +
             subSelect +
           " where c.ACCOUNT(+)  = b.ACCOUNT \n" ;
      return sql;
    }

    protected String getQuerySql(Map pParams) throws Exception {

      String filterCond = createFilterCondition(pParams);
      String begDateStr = (String) pParams.get(BEG_DATE_S);
      String endDateStr = (String) pParams.get(END_DATE_S);

      String  subSqlForCurrPeriod  = createDateSqlCond( begDateStr, endDateStr, 0);
      String  subSqlForPriorPeriod = createDateSqlCond( begDateStr, endDateStr, -1);

      String subSqlJdItems =
          " select ITEM_DIM_ID from DW_ITEM_DIM where UPPER(JD_ITEM_FL) = 'TRUE' \n" ;

      String sql=
          "  (select  ACCOUNT_DIM_ID  ACCOUNT, SITE_DIM_ID SHIP_TO \n" +
          "    sum("+PRICE_COL+") SUM_PRICE, sum("+QTY_COL+") SUM_QTY, \n" +
          "    sum("+COST_COL+") SUM_COST, 0 PRIOR_SUM_PRICE  , 0 PRIOR_SUM_QTY, 0 PRIOR_SUM_COST \n" +
          "    from DW_INVOICE_FACT \n" +
          "    where  DATE_DIM_ID in ( " + subSqlForCurrPeriod + " ) \n" +
          filterCond +
          "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
          "   group by  ACCOUNT_DIM_ID ) c, \n" +
          "  (select  ACCOUNT_DIM_ID  ACCOUNT, 0 SUM_PRICE, 0 SUM_QTY, 0 SUM_COST, sum("+PRICE_COL+") PRIOR_SUM_PRICE  , sum("+QTY_COL+") PRIOR_SUM_QTY, sum("+COST_COL+") PRIOR_SUM_COST \n" +
          "    from DW_INVOICE_FACT \n" +
          "    where  DATE_DIM_ID in ( " + subSqlForPriorPeriod + " ) \n" +
          filterCond +
          "      and  ITEM_DIM_ID in ( " + subSqlJdItems + " ) \n"+
          "   group by  ACCOUNT_DIM_ID, SITE_DIM_ID ) b \n" ;

        return sql;
    }
//------------------
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

    protected String getTitle() {
       String title = "Performance by Account" ;
       return title;
    }

    protected Object putCellStyle(String style, Object obj) {
      if (style == null) {
        return obj;
      }
      HashMap map = new HashMap();
      map.put(style, obj);
      return map;
    }

  protected String createFilterCondition(Map pParams) {

    String storeFilter = getParamValue(pParams, this.STORE_S);
    String accountFilter = getParamValue(pParams, LOCATE_ACCOUNT_MULTI_S);
    String siteFilter  = getParamValue(pParams, LOCATE_SITE_MULTI_OPT_S);
    String distributorFilter  = getParamValue(pParams, LOCATE_DISTRIBUTOR_OPT_S);
    String dsrFilter  = getDsrFilter(pParams);
    String regionFilter = getRegionFilter(pParams);
    String verticalFilter = getParamValue(pParams, VERTICAL_OPT_S);
    String connCustFilter = getParamValue(pParams, CONNECT_CUST_OPT_S);

    boolean accountFl = Utility.isSet(accountFilter);
    boolean siteFl = Utility.isSet(siteFilter);
    boolean distFl = Utility.isSet(distributorFilter);
    boolean regionFl = Utility.isSet(regionFilter);
    boolean verticalFl = Utility.isSet(verticalFilter);
    boolean dsrFl = Utility.isSet(dsrFilter);
    boolean connCustFl = Utility.isSet(connCustFilter) && connCustFilter.equals("Yes") ;

    String storeCondStr =  " AND STORE_DIM_ID =   " + storeFilter + " \n" ;
    String regionCondStr = (!regionFl) ? "" :
                           " SELECT REGION_DIM_ID FROM DW_REGION_DIM WHERE \n" +
                           "   UPPER(REGION_NAME) like '%" + regionFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
    String verticalCondStr = (!verticalFl) ? "" :
                           " SELECT ACCOUNT_DIM_ID FROM DW_ACCOUNT_DIM WHERE \n" +
                           "   UPPER(JD_MARKET) like '%" + verticalFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
    String dsrCondStr = (!dsrFl) ? "" :
                          " SELECT SALES_REP_DIM_ID FROM DW_SALES_REP_DIM WHERE \n" +
                          "   UPPER(REP_NAME) like '%" + dsrFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
    String connCustCondStr = (!connCustFl) ? "" :
                           " SELECT ACCOUNT_DIM_ID FROM DW_ACCOUNT_DIM WHERE \n" +
                           "   UPPER(CONNECTION_CUSTOMER) = '" + String.valueOf(connCustFl).toUpperCase() + "' \n";

    String filterCond = storeCondStr +
        (accountFl?" and ACCOUNT_DIM_ID in ( \n" + accountFilter + ") \n":"")+
        (siteFl?" and SITE_DIM_ID in ( \n" + siteFilter + ") \n":"")+
        (distFl?" and DISTRIBUTOR_DIM_ID in ( \n" + distributorFilter + ") \n":"")+
        (regionFl?"  and REGION_DIM_ID  in ( \n" + regionCondStr + ") \n" : "") +
        (verticalFl?" and ACCOUNT_DIM_ID in ( \n" + verticalCondStr + ") \n":"")+
        (dsrFl?"  and SALES_REP_DIM_ID  in ( \n" + dsrCondStr + ") \n" : "") +
        (connCustFl?" and ACCOUNT_DIM_ID in ( \n" + connCustCondStr + ") \n":"");

    return filterCond;
  }

        public class DistrPerfJPReportViewVector extends java.util.ArrayList implements Comparator {
           /**
            * Constructor.
            */
           public DistrPerfJPReportViewVector () {}

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
             DistrPerfJPReportView obj1 = (DistrPerfJPReportView)o1;
             DistrPerfJPReportView obj2 = (DistrPerfJPReportView)o2;

             if("SumPrice".equalsIgnoreCase(_sortField)) {
               BigDecimal i1 = obj1.getSumPrice();
               BigDecimal i2 = obj2.getSumPrice();
               if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
               else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
             }
             if("NetSalesGrowth".equalsIgnoreCase(_sortField)) {
               BigDecimal i1 = obj1.getNetSalesGrowth();
               BigDecimal i2 = obj2.getNetSalesGrowth();
               if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
               else if(i2==null) retcode = 1;
               else {
                 i1 = i1.abs();
                 i2 = i2.abs();
                 retcode = i1.compareTo(i2);
               }
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

    protected class DistrPerfJPReportView extends ValueObject {
    private String mAccount;
    private String mShipToId;
    private String mAccountName;
    private String mShipToName;
    private BigDecimal mSumPrice;
    private BigDecimal mSumCost;
    private BigDecimal mSumQty;
    private BigDecimal mSumPricePre;
    private BigDecimal mSumCostPre;
    private BigDecimal mSumQtyPre;
    private BigDecimal mNetSalesGrowth;
    private String mRegionName;
    private String mRepName;
    private String mVerticalName;
    private String mGroupByName;


    /**
     * Constructor.
     */
    public DistrPerfJPReportView ()
    {
        mAccount = "";
        mAccountName="";
        mShipToId = "";
        mShipToName = "";
        mRegionName="";
        mRepName = "";
        mVerticalName ="";
        mGroupByName="";
    }


    public void setAccount(String pAccount){
        this.mAccount = pAccount;
    }
    public String getAccount(){
        return mAccount;
    }
    public void setShipToId(String pShipToId){
        this.mShipToId = pShipToId;
    }
    public String getShipToId(){
        return mShipToId;
    }
    public void setShipToName(String pShipToName){
        this.mShipToName = pShipToName;
    }
    public String getShipToName(){
        return mShipToName;
    }
    public void setAccountName(String pAccountName){
        this.mAccountName = pAccountName;
    }
    public String getAccountName(){
        return mAccountName;
    }
    public void setRegionName(String pRegionName){
        this.mRegionName = pRegionName;
    }
    public String getRegionName(){
        return mRegionName;
    }
    public void setRepName(String pRepName){
        this.mRepName = pRepName;
    }
    public String getRepName(){
        return mRepName;
    }
    public void setVerticalName(String pVerticalName){
      this.mVerticalName = pVerticalName;
    }
    public String getVerticalName() {
      return mVerticalName;
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
    public void setGroupByName(String pGroupByName){
     this.mGroupByName = pGroupByName;
   }
   public String getGroupByName() {
     return mGroupByName;
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
    //------------------------------------
    public void setNetSalesGrowth(BigDecimal pNetSalesGrowth) {
      this.mNetSalesGrowth = pNetSalesGrowth;
    }

    public BigDecimal getNetSalesGrowth() {
      return mNetSalesGrowth;
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
