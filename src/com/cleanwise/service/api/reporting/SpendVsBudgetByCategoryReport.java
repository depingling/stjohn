/*
 * ForecastOrderByLocationReport.java
 *
 * Created on October 15, 2008, 4:43 PM
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.FiscalCalendarUtility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Picks up orders and agreates it on Surrplier
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 * @param pAccountId account Id optionally,
 */
public class SpendVsBudgetByCategoryReport  implements GenericReportMulti {
    private static final BigDecimal ZERO = new BigDecimal("0.00");
    /** Creates a new instance of PurchBySupplierJDReport */
    public SpendVsBudgetByCategoryReport() {
    }

    protected static final String BEGIN_YTD_YEAR_FP = "BEGIN_YTD_YEAR_FP";
    protected static final String END_YTD_YEAR_FP = "END_YTD_YEAR_FP";
    protected static final String BEGIN_FP = "BEGIN_FP";
    protected static final String END_FP = "END_FP";

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */

    protected static final String GRAND_TOTAL = "TOTAL";
    protected static final String PRICE_COL = "inv.order_qty * inv.price";
    protected static final String QTY_COL   = "inv.qty_on_hand";
    protected static final String COUNT_COL = "inv.item_id";

    //------------Veiw By Category-----------------------
    protected static final String CATEGORY = "Category";
    protected static final String CATEGORY_SPEND = "Category Spend";
    protected static final String SUBCATEGORY_SPEND = "Subcategory Spend";
    protected static final String PERCENT_OF_TOTAL = "% Of Total Spend";
    protected static final String PERCENT_OF_CATEGORY = "% Of Category";
    protected static final String MONTH_BUDGET = "Budget";
    protected static final String MONTH_VARIANCE = "Variance";
    protected static final String PERCENT_OF_MONTH_VARIANCE = "% Variance";
    protected static final String PERCENT_OF_BUDGET_SPENT = "% Budget Spent";
    protected static final String YTD_CATEGORY_SPEND = "YTD Spend";
    protected static final String YTD_SUBCATEGORY_SPEND = "YTD Subcategory Spend";
    protected static final String YTD_PERCENT_OF_TOTAL = "YTD % Of Total Spend";
    protected static final String YTD_PERCENT_OF_CATEGORY = "YTD % Of Category";
    protected static final String YTD_BUDGET = "YTD Budget";
    protected static final String YTD_VARIANCE = "YTD Variance";
    protected static final String PERCENT_OF_YTD_VARIANCE = "% YTD Variance";
    protected static final String PERCENT_OF_YTD_BUDGET_SPENT = "% YTD Budget Spent";
    //--------------Detail View-----------------------------
    protected static final String LOCATION = "Location";
    protected static final String CITY = "City";
    protected static final String STATE = "State";
    protected static final String CUST_PO_NUM = "Cust PO Number";
    protected static final String ORDER_DATE = "Order Date";
    protected static final String SUBCATEGORY = "Subcategory 1&2";
    protected static final String ITEM_NUM = "Item Number";
    protected static final String DESCRIPTION = "Description";
    protected static final String QTY = "QTY";
    protected static final String UOM = "UOM";
    protected static final String EXTENDED_VAL = "Extended Value";
    //--------------------Parameters-------------------------
    protected static final String BEG_DATE_S = "BEG_DATE_OPT";
    protected static final String END_DATE_S = "END_DATE_OPT";
    protected static final String END_YEAR_S = "endYear_OPT";
    protected static final String END_MONTH_S = "endMonth_OPT";
    public static final String TABLE_WIDTH="748";

    protected static final int EXTREM = 999999999;
    protected static final String SHIFT_CHAR = " ";
    protected static final String UNKNOWN = "Unknown";

    private static final String localCurrencySign = "$";
    ArrayList repCurrencyList = new ArrayList();

    int catalogId = -1;
    String siteCrit = null;
  //  HashMap itemToCatMap = null;
  //  HashMap categoryToCategoryMap = null;
    HashMap<Integer,Category> categoryMap =null;
    HashMap catSummaryCollection = null;

    ArrayList dateRange = null;  // valid budjet date period
    //String startDateS = null;   // sql criteria for start date;
    //String endDateS = null ;    // sql criteria for end date;
    String ytdStartDateS = null;  // sql criteria for YTD start date;
    boolean debug =  false;

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
     Connection con = pCons.getDefaultConnection(); //pCons.getMainConnection();
     String errorMess = "No error";

     String userIdStr = (String) pParams.get("CUSTOMER");
     String storeIdStr = (String) pParams.get("STORE_SELECT");

     String categoryFilterStr = getParamValue(pParams, "CATEGORY");



     catalogId = getCatalogId(con, Integer.parseInt(storeIdStr));
     siteCrit = getUserSites(con, userIdStr, storeIdStr);
     //validateBudgetDateRange(con, pParams);
     calcBudgetDateRange(con,pParams);

     GenericReportResultView result = GenericReportResultView.createValue();
     //result.setReportFormat(".xlsx");
     result.setWidth(TABLE_WIDTH);
     result.setTitle( getReportTitle(con, pReportData.getName(), pParams));
     result.setHeader(getSheet1Header());
     result.setColumnCount(getSheet1Header().size());
     result.setName("Spend vs Budget"); // length - maximum 30 letters
     result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
     result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
     result.setTable(new ArrayList());



     GenericReportResultView details = GenericReportResultView.createValue();
     //details.setReportFormat(".xlsx");
     GenericReportColumnViewVector detailsTitle = getReportTitle(con, pReportData.getName()+ " Details", pParams);
     GenericReportColumnViewVector detailsHeader = getSheet2Header();

     details.setWidth(TABLE_WIDTH);
     details.setTitle(detailsTitle );
     details.setHeader(detailsHeader);
     details.setColumnCount(detailsHeader.size());
     details.setName("Cost Center Details"); // length - maximum 30 letters
     details.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
     details.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
     details.setTable(new ArrayList());
     //====  Request for values ==== //
     try {
       createCategoryMaps (con,  categoryFilterStr);
       //       GenericReportResultViewVector resultV = prepareReportData (result, repYList);
       GenericReportResultViewVector resultV = new GenericReportResultViewVector();
       ArrayList sheet1Data = getResultOfSheet1Query(con,  pParams );
       populateSheetData(result, sheet1Data);
       ArrayList sheet2Data = getResultOfSheet2Query(con,  pParams );
 //      populateSheetData(details, sheet2Data);
       Collection detailCollection = createDetailCollection(sheet2Data, detailsTitle, detailsHeader);
       resultV.addAll(detailCollection);
  //     resultV.add(details);
       resultV.add(result);
       HashMap userStyles = createReportStyleDescriptor();
       result.setUserStyle(userStyles);
  //     details.setUserStyle(userStyles);
       for (Iterator iter=detailCollection.iterator(); iter.hasNext();) {
           GenericReportResultView det = (GenericReportResultView)iter.next();
           det.setUserStyle(userStyles);
       }
       return resultV;
     }
     catch (SQLException exc) {
       errorMess = "Error. SQL Exception happened. " +  exc.getMessage();
       exc.printStackTrace();
       throw new RemoteException(errorMess);
     }
     catch (Exception exc) {
       errorMess = "Error. Exception happened. " +  exc.getMessage();
       exc.printStackTrace();
       throw new RemoteException(errorMess);
     }

   }

   private Collection createDetailCollection(ArrayList sheet2Data,  GenericReportColumnViewVector pTitle, GenericReportColumnViewVector pHeader){

     ArrayList<GenericReportResultView> dc = new ArrayList();
     //sheet2Data.sort("Category");
     String category = "";
     String priorCategory = "";
     ArrayList<BRecord> catSheet = new ArrayList();
     for (int i = 0; i < sheet2Data.size(); i++) {
       BRecord row = (BRecord)sheet2Data.get(i);
       category = (Utility.isSet(row.getCategory())) ? row.getCategory() : UNKNOWN ;

//log(" --> category = " +category + "/ priorCategory = " + priorCategory );
       if (category != null && priorCategory.length() > 0 &&
           !category.equals(priorCategory)) {
         GenericReportResultView d = GenericReportResultView.createValue();
         initSheet(d, pTitle, pHeader, priorCategory);
         populateSheetData(d, catSheet);
         dc.add(d);
         catSheet = new ArrayList();
       }
       priorCategory = category;
       catSheet.add(row);
     }
     GenericReportResultView d = GenericReportResultView.createValue();
     initSheet(d, pTitle, pHeader, category);
     populateSheetData(d, catSheet);
     dc.add(d);
     ArrayList<GenericReportResultView> dcDesc = new ArrayList();
     GenericReportResultView lastSheet = null;
     for(int ii=0; ii<dc.size(); ii++) {
       if (((GenericReportResultView)dc.get(ii)).getName().equals(UNKNOWN)){
         lastSheet = (GenericReportResultView)dc.get(ii);
         break;
       }
     }
     if (lastSheet != null ) {
       dcDesc.add(lastSheet);
     }
     for(int ii=dc.size()-1; ii>=0; ii--) {
       if (!((GenericReportResultView)dc.get(ii)).getName().equals(UNKNOWN)){
         dcDesc.add(dc.get(ii));
       }
     }

     return dcDesc;
   }

   private void initSheet(GenericReportResultView pResult, GenericReportColumnViewVector pTitle, GenericReportColumnViewVector pHeader, String pSheetName){
     pResult.setWidth(TABLE_WIDTH);
     pResult.setTitle( pTitle);
     pResult.setHeader(pHeader);
     pResult.setColumnCount(pHeader.size());
     if (Utility.isSet(pSheetName) ){
       pResult.setName(pSheetName.substring(0, Math.min(pSheetName.length(), 29) )); // length - maximum 30 letters
//log("-->SheetName = " + pResult.getName() );
     }
     pResult.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
     pResult.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
     pResult.setTable(new ArrayList());
   }



   protected String getParamValue (Map pParams, String pControlName){
      String value = null;
      if (pParams.containsKey(pControlName) &&  Utility.isSet( (String) pParams.get(pControlName))) {
        value = (String) pParams.get(pControlName);
      }
      return value;
    }

    protected void populateSheetData (GenericReportResultView result , ArrayList repList ){
          if (repList.size()!= 0){
            for (Iterator iter1=repList.iterator(); iter1.hasNext();) {
              Object det = iter1.next();
              if ( det instanceof ARecord) {
                result.getTable().add(((ARecord)det).toList());
              } else if ( det instanceof BRecord) {
                result.getTable().add(((BRecord)det).toList());
              }
            }
          }
    }

/*
    protected GenericReportResultViewVector prepareReportData (GenericReportResultView result , ArrayList repList ){
          if (repList.size()!= 0){
            ArrayList tRepList = transform(repList);
            for (Iterator iter1=tRepList.iterator(); iter1.hasNext();) {
              ForecastOrderSummaryDetail det = (ForecastOrderSummaryDetail) iter1.next();
              result.getTable().add(det.toList());
            }
          }
          GenericReportResultViewVector resultV = new GenericReportResultViewVector();
          resultV.add(result);
        return resultV;
    }

*/
    protected HashMap createReportStyleDescriptor(){
     GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();

     /*
    (String parm1, String parm2, String parm3, String parm4, short parm5, String parm6, String parm7, String parm8, String parm9, int[] parm10)
    */
//     int[] mergeRegion = new int[]{1,1,1,getReportHeader().size()};

     GenericReportStyleView pageTitle = new GenericReportStyleView(ReportingUtils.PAGE_TITLE,"TEXT",null,"Areal",10,"BOLD",null,null, "LEFT", false, null, null,null,0 );
     GenericReportStyleView colHeaderBold = new GenericReportStyleView("COLUMN_HEADER_WRAP_BOLD","TEXT",null,null,-1,"BOLD",null,null,"LEFT",true, null, null,null,0 );
     GenericReportStyleView colHeaderNormal = new GenericReportStyleView("COLUMN_HEADER_WRAP","TEXT",null,null,-1, "NORMAL",null,null,"LEFT",true, null, null,null,0 );

     GenericReportStyleView colDataCenter = new GenericReportStyleView("COLUMN_DATA_CENTER",null,null,null,-1,null,null,null,"CENTER",false, null , null,null,0);
     GenericReportStyleView colDataPercBold = new GenericReportStyleView("COLUMN_DATA_PERCENT_BOLD",ReportingUtils.DATA_CATEGORY.PERCENTAGE,"0.0%",null,-1,"BOLD",null,null,null, false,null, null,null,0 );
     GenericReportStyleView colDataPerc = new GenericReportStyleView("COLUMN_DATA_PERCENT",ReportingUtils.DATA_CATEGORY.PERCENTAGE,"0.0%",null,-1,null,null,null,null, false,null , null,null,0);
     GenericReportStyleView colDataBold = new GenericReportStyleView("COLUMN_DATA_BOLD","TEXT",null,null,-1,"BOLD",null,null,"LEFT", false,null, null,null,0 );
     GenericReportStyleView colDataNormal = new GenericReportStyleView("COLUMN_DATA_NORMAL","TEXT",null,null,-1,"NORMAL",null,null,"LEFT", false,null, null,null,0 );

     if (repCurrencyList.size() == 0){
       repCurrencyList.add(localCurrencySign);
     }
     GenericReportStyleView[] colDataCurrInParenth = new GenericReportStyleView[repCurrencyList.size()];
     GenericReportStyleView[] colDataCurrency = new GenericReportStyleView[repCurrencyList.size()];
     GenericReportStyleView[] colDataCurrencyBold = new GenericReportStyleView[repCurrencyList.size()];
     for (int i = 0; i < repCurrencyList.size(); i++) {
        String currency = (String)repCurrencyList.get(i);
        colDataCurrInParenth[i] = new GenericReportStyleView("CURRENCY_IN_PARENTH_"+currency, ReportingUtils.DATA_CATEGORY.FLOAT,"("+currency+"#,##0.00)",null,-1,"BOLD",null,null,null, false,null, null,null,0 );
        colDataCurrency[i] = new GenericReportStyleView("CURRENCY_"+currency, ReportingUtils.DATA_CATEGORY.FLOAT,currency+"#,##0.00",null,-1,null,null,null,null, false,null , null,null,0);
        colDataCurrencyBold[i] = new GenericReportStyleView("CURRENCY_BOLD_"+currency, ReportingUtils.DATA_CATEGORY.FLOAT,currency+"#,##0.00",null,-1,"BOLD",null,null,null, false,null , null,null,0);
     }

     reportDesc.setUserStyle(pageTitle.getStyleName(), pageTitle);
     reportDesc.setUserStyle(colHeaderBold.getStyleName(), colHeaderBold);
     reportDesc.setUserStyle(colHeaderNormal.getStyleName(), colHeaderNormal);
     reportDesc.setUserStyle(colDataCenter.getStyleName(), colDataCenter);
     reportDesc.setUserStyle(colDataPercBold.getStyleName(),colDataPercBold);
     reportDesc.setUserStyle(colDataPerc.getStyleName(),colDataPerc);
     reportDesc.setUserStyle(colDataBold.getStyleName(),colDataBold);
     reportDesc.setUserStyle(colDataNormal.getStyleName(),colDataNormal);

     for (int i = 0; i < repCurrencyList.size(); i++) {
       reportDesc.setUserStyle(colDataCurrInParenth[i].getStyleName(), colDataCurrInParenth[i]);
       reportDesc.setUserStyle(colDataCurrency[i].getStyleName(), colDataCurrency[i]);
       reportDesc.setUserStyle(colDataCurrencyBold[i].getStyleName(), colDataCurrencyBold[i]);
     }

     HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
     return styleDesc;
   }


    protected GenericReportColumnViewVector getSheet1Header() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        if (debug) {
          header.add(ReportingUtils.createGenericReportColumnView("COST_CENTER", "COLUMN_HEADER_WRAP_BOLD", "COLUMN_DATA_BOLD","10"));
          header.add(ReportingUtils.createGenericReportColumnView("LEVEL", "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.INTEGER_STYLE,"10"));
          header.add(ReportingUtils.createGenericReportColumnView("CATEGORY ID", "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.INTEGER_STYLE,"10"));
          header.add(ReportingUtils.createGenericReportColumnView("CATEGORY NAME", "COLUMN_HEADER_WRAP_BOLD", null,"10"));
        }
        header.add(ReportingUtils.createGenericReportColumnView(this.CATEGORY, "COLUMN_HEADER_WRAP_BOLD", "COLUMN_DATA_BOLD","10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.CATEGORY_SPEND, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.FLOAT_SEPARATOR_STYLE,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.SUBCATEGORY_SPEND, "COLUMN_HEADER_WRAP", "COLUMN_DATA_NORMAL","10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_TOTAL, "COLUMN_HEADER_WRAP_BOLD", "COLUMN_DATA_PERCENT_BOLD","10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_CATEGORY, "COLUMN_HEADER_WRAP", "COLUMN_DATA_PERCENT","10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.MONTH_BUDGET, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.FLOAT_SEPARATOR_STYLE,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.MONTH_VARIANCE, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.FLOAT_SEPARATOR_STYLE,"12"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_BUDGET_SPENT, "COLUMN_HEADER_WRAP_BOLD", "COLUMN_DATA_PERCENT_BOLD","10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.YTD_CATEGORY_SPEND, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.FLOAT_SEPARATOR_STYLE,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.YTD_SUBCATEGORY_SPEND,"COLUMN_HEADER_WRAP", ReportingUtils.FLOAT_SEPARATOR_STYLE,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.YTD_PERCENT_OF_TOTAL, "COLUMN_HEADER_WRAP_BOLD", "COLUMN_DATA_PERCENT_BOLD","10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.YTD_PERCENT_OF_CATEGORY, "COLUMN_HEADER_WRAP", "COLUMN_DATA_PERCENT","10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.YTD_BUDGET, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.FLOAT_SEPARATOR_STYLE,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.YTD_VARIANCE, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.FLOAT_SEPARATOR_STYLE,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_YTD_BUDGET_SPENT, "COLUMN_HEADER_WRAP_BOLD", "COLUMN_DATA_PERCENT_BOLD","10"));
        return header;
    }

    protected GenericReportColumnViewVector getSheet2Header() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(this.LOCATION, "COLUMN_HEADER_WRAP_BOLD", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.CITY, "COLUMN_HEADER_WRAP_BOLD", null,"8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.STATE, "COLUMN_HEADER_WRAP_BOLD", "COLUMN_DATA_CENTER","5"));
        header.add(ReportingUtils.createGenericReportColumnView(this.CUST_PO_NUM, "COLUMN_HEADER_WRAP_BOLD", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.ORDER_DATE, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.DATE_STYLE,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.CATEGORY, "COLUMN_HEADER_WRAP_BOLD", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.SUBCATEGORY, "COLUMN_HEADER_WRAP_BOLD", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.ITEM_NUM, "COLUMN_HEADER_WRAP_BOLD", null,"10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.DESCRIPTION, "COLUMN_HEADER_WRAP_BOLD", null,"30"));
        header.add(ReportingUtils.createGenericReportColumnView(this.QTY, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.INTEGER_STYLE,"8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.UOM, "COLUMN_HEADER_WRAP_BOLD", ReportingUtils.TABLE_DATA,"6"));
        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_VAL,"COLUMN_HEADER_WRAP_BOLD", "CURRENCY_$","10"));
        return header;
    }

    protected ArrayList getResultOfSheet1Query(Connection conn, Map pParams) throws Exception{
      BigDecimal zeroAmt = new BigDecimal(0);
      Integer zeroNum = new Integer(0);
      //------------------------------------
/*      ArrayList resultV = new ArrayList();
      aRecord record = new aRecord();
      record.init();
      resultV.add(record);
      return resultV;  ///TEMPORRARY !!!!!!
*/      //--------------------------------------
      //=========== getting parameters =======================================
      /*
         String begDateStr = null;
         String endDateStr = null;
         if (dateRange != null && dateRange.size() == 2) {
           begDateStr = (String) dateRange.get(0);
           endDateStr = (String) dateRange.get(1);
         } else {
           throw new RemoteException("^clwKey^report.text.budgetDateRangeInvalid^clwKey^");
         }
       */
      //======================================================================//

        /*
        GregorianCalendar currBegCalendar = new GregorianCalendar();
        currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

        GregorianCalendar currEndCalendar = new GregorianCalendar();
        currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

        int year = currEndCalendar.get(Calendar.YEAR);
        GregorianCalendar ytdBegCalendar = new GregorianCalendar();
        ytdBegCalendar.setTime(ReportingUtils.parseDate("01/01/"+ year));

        startDateS = ReportingUtils.toSQLDate(currBegCalendar.getTime());
        endDateS =   ReportingUtils.toSQLDate(currEndCalendar.getTime());
        ytdStartDateS = ReportingUtils.toSQLDate(ytdBegCalendar.getTime());
        */

        calcBudgetDateRange(conn, pParams);
        String orderSql= getOrderQuerySql(pParams);
        PreparedStatement pstmt = conn.prepareStatement(orderSql);
        ResultSet rs = pstmt.executeQuery();
        HashSet<Integer> categoryIds = new HashSet<Integer>();
        double totalSum = 0;
        double ytdTotalSum = 0;
        while (rs.next()){
             int costCenterId = rs.getInt("cost_center_id");
             int catId = rs.getInt("category_id");
             BigDecimal spend = rs.getBigDecimal("period_spend") ;
if(catId==0) {
    continue;
}
             totalSum += spend.doubleValue();
             BigDecimal ytdSpend = rs.getBigDecimal("ytd_spend") ;
             ytdTotalSum += ytdSpend.doubleValue();

             Category cat1 = categoryMap.get(catId);
if(cat1==null) {
    continue;
}
             categoryIds.add(catId);
             cat1.costCenterId = costCenterId;
             cat1.ytdSpend = cat1.ytdSpend.add(ytdSpend);
             cat1.spend = cat1.spend.add(spend);
             cat1.ytdNoSubSpend = cat1.ytdNoSubSpend.add(ytdSpend);
             cat1.noSubSpend = cat1.noSubSpend.add(spend);

             if(cat1.subSubCategId!=cat1.subCategId) {
                 Category cat2 = categoryMap.get(cat1.subCategId);
                 categoryIds.add(cat2.subSubCategId);
                 cat2.costCenterId = costCenterId;
                 cat2.ytdSpend = cat2.ytdSpend.add(ytdSpend);
                 cat2.spend = cat2.spend.add(spend);
                 if(cat1.subCategId != cat1.topCategId) {
                     Category cat3 = categoryMap.get(cat1.topCategId);
                     categoryIds.add(cat3.subSubCategId);
                     cat3.costCenterId = costCenterId;
                     cat3.ytdSpend = cat3.ytdSpend.add(ytdSpend);
                     cat3.spend = cat3.spend.add(spend);
                 }
             }
             /*
             spendRecord.setCostCenterId(costSenterId);
             //spendRecord.setCategory(catName);
             spendRecord.setCategorySpend(spend) ;
             spendRecord.setYTDCategorySpend(ytdSpend) ;
             spendRecord.setCategoryId(catId);
             spendRecord.setCurrency(this.localCurrencySign);

             spendLines.add(spendRecord);
             */
      }
       pstmt.close();
       rs.close();

       String budgetSql= getBudgetQuerySql(pParams);

       pstmt = conn.prepareStatement(budgetSql);

       HashMap<Integer,BigDecimal> ytdBudgetMap = new HashMap<Integer,BigDecimal>();
       HashMap<Integer,BigDecimal> budgetMap = new HashMap<Integer,BigDecimal>();

       rs = pstmt.executeQuery();

       double budgetSum =0;
       double ytdBudgetSum = 0;
       ArrayList costCenters = new ArrayList();

       while (rs.next()){
              int costCenterId = rs.getInt("COST_CENTER_ID");
              BigDecimal budget = rs.getBigDecimal("BUDGET") ;
              BigDecimal ytdBudget = rs.getBigDecimal("YTD_BUDGET") ;
              budgetMap.put(costCenterId, budget );
              ytdBudgetMap.put(costCenterId, ytdBudget );
              budgetSum    += budget.doubleValue();
              ytdBudgetSum += ytdBudget.doubleValue();
              costCenters.add(costCenterId);
        }
        pstmt.close();
        rs.close();

        Category[] categoryA = new Category[categoryIds.size()];

        //----------------- Set Budgets To Top Level Categories ----------
        int index = 0;
        for(Iterator iter=categoryIds.iterator(); iter.hasNext();) {
            int categId = ((Integer)iter.next()).intValue();
            Category cat = categoryMap.get(categId);
            categoryA[index++] = cat;
            if(cat.subSubCategId==cat.subCategId && cat.subCategId==cat.topCategId) {
                cat.budget = budgetMap.get(cat.costCenterId);
                cat.ytdBudget = ytdBudgetMap.get(cat.costCenterId);
            }
        }

        //Sort categories
        if(categoryA.length>1) {

            for(int ii=0; ii<categoryA.length; ii++) {
                boolean exitFl = true;
                for(int jj=0; jj<categoryA.length-ii-1; jj++) {
                    Category cat1 = categoryA[jj];
                    Category cat2 = categoryA[jj+1];
                    String name1 = null;
                    if(cat1.subSubCategId==cat1.subCategId && cat1.subCategId==cat1.topCategId) {
                       name1 = cat1.subSubCategName;
                    } else if(cat1.subSubCategId!=cat1.subCategId && cat1.subCategId==cat1.topCategId) {
                       name1 = cat1.subCategName+"@"+cat1.subSubCategName;
                    } else  {
                       name1 =  cat1.topCategName+"@"+cat1.subCategName+"@"+cat1.subSubCategName;
                    }
                    String name2 = null;
                    if(cat2.subSubCategId==cat2.subCategId && cat2.subCategId==cat2.topCategId) {
                       name2 = cat2.subSubCategName;
                    } else if(cat2.subSubCategId!=cat2.subCategId && cat2.subCategId==cat2.topCategId) {
                       name2 = cat2.subCategName+"@"+cat2.subSubCategName;
                    } else  {
                       name2 =  cat2.topCategName+"@"+cat2.subCategName+"@"+cat2.subSubCategName;
                    }
                    if(name1.compareToIgnoreCase(name2)>0) {
                        categoryA[jj] = cat2;
                        categoryA[jj+1] = cat1;
                        exitFl = false;
                    }
                }
                if(exitFl) break;
            }
        }


       //----------------- calculate Totals and Sum by Category ----------------/
       /*
       HashMap catToCatForCostCentersMap = new HashMap();
       for (int i = 0; i < costCenters.size(); i++) {
         Set keySet = categoryToCategoryMap.keySet();
         for(Iterator iter = keySet.iterator(); iter.hasNext();){
           String key= (String)iter.next();
           ArrayList catSummV = (ArrayList)categoryToCategoryMap.get(key);
           ArrayList catSummNewV = new ArrayList();
           if (catSummV != null){
             for (int ii = 0; ii < catSummV.size(); ii++) {
               CategorySummary catSumm = (CategorySummary) catSummV.get(ii);
               CategorySummary catSummNew =
                       new CategorySummary(catSumm.getCategoryId(), catSumm.getLevel(), catSumm.getCategoryName());
               catSummNewV.add(catSummNew) ;
             }
           }
           catToCatForCostCentersMap.put((String)costCenters.get(i)+key,catSummNewV );
         }
       }

       double totalSum = 0;
       double ytdTotalSum = 0;

       for (int i = 0; i < spendLines.size(); i++) {
         ARecord rec = (ARecord)spendLines.get(i);
         BigDecimal mBudget = (budgetMap != null ) ? (BigDecimal)budgetMap.get(rec.getCostCenterId()) : zeroAmt;
         BigDecimal mYTDBudget = (ytdBudgetMap != null ) ? (BigDecimal)ytdBudgetMap.get(rec.getCostCenterId()) : zeroAmt;

         rec.setMonthBudget((mBudget!=null) ? mBudget : zeroAmt );
         rec.setYTDBudget((mYTDBudget !=null) ? mYTDBudget : zeroAmt);

         totalSum     += rec.getCategorySpend().doubleValue();
         ytdTotalSum  += rec.getYTDCategorySpend().doubleValue();

         String key = rec.getCostCenterId()+rec.getCategoryId();
         ArrayList catSummV = (ArrayList)catToCatForCostCentersMap.get(key);
         if (catSummV != null){
           for (int ii = 0; ii < catSummV.size(); ii++) {
             CategorySummary catSumm = (CategorySummary) catSummV.get(ii);
             catSumm.sum(rec.getCategorySpend(), rec.getYTDCategorySpend());
           }

         }
       }
       */
       //------------------------------------------------------------------------
       /*
       SummaryVector resultLines = new SummaryVector();

       if (spendLines != null) {
         double categorySum = 0;
         double ytdCategorySum = 0;

        for (Iterator iter = spendLines.iterator(); iter.hasNext(); ) {
         ARecord rec = (ARecord) iter.next();
         ARecord row = null;
         ArrayList catSummV = (ArrayList)catToCatForCostCentersMap.get(rec.getCostCenterId()+rec.getCategoryId());

         String fullPath = "";
         if (catSummV == null){
           catSummV = new ArrayList();
         }
         for (int ii = 0; ii < catSummV.size(); ii++) {
            CategorySummary catSumm = (CategorySummary)catSummV.get(ii);
            if(catSumm != null){
             row = new ARecord();
             row.setCostCenterId(rec.getCostCenterId());
             row.setLevel(catSumm.getLevel());
             row.setCurrency(rec.getCurrency());
             if (catSumm.getLevel() == 1){
               fullPath = catSumm.getCategoryName();
               row.setCategory(fullPath);
               row.setCategorySpend(catSumm.getSumByCategory());
               row.setMonthBudget(rec.getMonthBudget());
               row.setYTDCategorySpend(catSumm.getYTDSumByCategory());
               row.setYTDBudget(rec.getYTDBudget());
               categorySum = (row.getCategorySpend() != null) ? row.getCategorySpend().doubleValue() : 0;
               ytdCategorySum = (row.getYTDCategorySpend() != null) ? row.getYTDCategorySpend().doubleValue() : 0;
             } else{
               fullPath += "/" + catSumm.getCategoryName();
               row.setCategory(fullPath);
               row.setSubcategory(catSumm.getCategoryName());
               row.setSubcategorySpend(catSumm.getSumByCategory());
               row.setYTDSubcategorySpend(catSumm.getYTDSumByCategory());
            }
            row.calculate(totalSum,ytdTotalSum,categorySum,ytdCategorySum);
            resultLines.add(row);
           }
          }
      //   }
         row = new ARecord();
         row.setCurrency(rec.getCurrency());
         row.setCategoryId(rec.getCategoryId());
         row.setCostCenterId(rec.getCostCenterId());
         row.setLevel(catSummV.size() + 1);
         if( catSummV.size() == 0){
           row.setCategory(rec.getCategory());
           row.setCategorySpend(rec.getCategorySpend());
           row.setMonthBudget(rec.getMonthBudget());
           row.setYTDCategorySpend(rec.getCategorySpend());
           row.setYTDBudget(rec.getYTDBudget());

           categorySum = (row.getCategorySpend() != null) ? row.getCategorySpend().doubleValue() : 0;
           ytdCategorySum = (row.getYTDCategorySpend() != null) ? row.getYTDCategorySpend().doubleValue() : 0;
         } else {
           row.setCategory(fullPath);
           row.setSubcategory(padString(rec.getCategory(), row.getLevel())) ;
           row.setSubcategorySpend(rec.getCategorySpend());
           row.setYTDSubcategorySpend(rec.getYTDCategorySpend());
         }
         row.calculate(totalSum,ytdTotalSum,categorySum,ytdCategorySum);
         resultLines.add(row);
        }
        resultLines.sort("Category");
     }

     */
  //   }
       SummaryVector resultLines = new SummaryVector();
       BigDecimal topCategSpend = null;
       BigDecimal ytdTopCategSpend = null;
       BigDecimal totalSumBD = new BigDecimal(totalSum);
       BigDecimal ytdTotalSumBD = new BigDecimal(ytdTotalSum);
       for(int ii=0; ii<categoryA.length; ii++) {
           Category cat = categoryA[ii];
           ARecord row = new ARecord();
           row.init();
           if(cat.subSubCategId==cat.subCategId && cat.subCategId == cat.topCategId) {
               topCategSpend = cat.spend;
               ytdTopCategSpend = cat.ytdSpend;
               row.setCostCenterId(cat.costCenterId);
               row.setLevel(1);
               row.setCurrency(this.localCurrencySign);
               row.setCategory(cat.subSubCategName);
               row.setCategorySpend(cat.spend);
               if(cat.budget!=null) {
                   row.setMonthBudget(cat.budget);
               }
               row.setYTDCategorySpend(cat.ytdSpend);
               if(cat.ytdBudget!=null) {
                   row.setYTDBudget(cat.ytdBudget);
               }
               row.setSubcategorySpend(cat.noSubSpend);
               row.setYTDSubcategorySpend(cat.ytdNoSubSpend);
               //if(Math.abs(totalSum)>0.001) {
               //    row.setPercentOfTotal(cat.spend.divide(totalSumBD,1,BigDecimal.ROUND_HALF_UP));
               //}
               //if(Math.abs(ytdTotalSum)>0.001) {
               //    row.setPercentOfYTDTotal(cat.spend.divide(ytdTotalSumBD,1,BigDecimal.ROUND_HALF_UP));
               //}
               //if(Math.abs(topCategSpend.doubleValue())>0.001) {
               //    row.setPercentOfCategory(cat.noSubSpend.divide(topCategSpend,1,BigDecimal.ROUND_HALF_UP));
               //}
               //if(Math.abs(ytdTopCategSpend.doubleValue())>0.001) {
               //    row.setPercentOfCategory(cat.ytdNoSubSpend.divide(ytdTopCategSpend,1,BigDecimal.ROUND_HALF_UP));
               //}
           }
           if(cat.subSubCategId!=cat.subCategId && cat.subCategId == cat.topCategId) {
               row.setLevel(2);
               row.setCurrency(this.localCurrencySign);
               row.setCategory(cat.subCategName+"/"+cat.subSubCategName);
               row.setSubcategory("    "+cat.subSubCategName);
               row.setSubcategorySpend(cat.spend);
               row.setYTDSubcategorySpend(cat.ytdSpend);
               //row.setSubcategorySpend(cat.noSubSpend);
               //row.setYTDSubcategorySpend(cat.ytdNoSubSpend);
               if(Math.abs(topCategSpend.doubleValue())>0.001) {
                   row.setPercentOfCategory(cat.spend.divide(topCategSpend,1,BigDecimal.ROUND_HALF_UP));
               }
               if(Math.abs(ytdTopCategSpend.doubleValue())>0.001) {
                   row.setPercentOfCategory(cat.ytdSpend.divide(ytdTopCategSpend,1,BigDecimal.ROUND_HALF_UP));
               }
           }
           if(cat.subSubCategId!=cat.subCategId && cat.subCategId != cat.topCategId) {
               row.setLevel(3);
               row.setCurrency(this.localCurrencySign);
               row.setCategory(cat.topCategName+"/"+cat.subCategName+"/"+cat.subSubCategName);
               row.setSubcategory("        "+cat.subSubCategName);
               row.setSubcategorySpend(cat.spend);
               row.setYTDSubcategorySpend(cat.ytdSpend);
               //row.setSubcategorySpend(cat.noSubSpend);
               //row.setYTDSubcategorySpend(cat.ytdNoSubSpend);
               if(topCategSpend == null){
                   topCategSpend = ZERO;
               }
               if(ytdTopCategSpend == null){
                   ytdTopCategSpend = ZERO;
               }
               if(Math.abs(topCategSpend.doubleValue())>0.001) {
                   row.setPercentOfCategory(cat.spend.divide(topCategSpend,1,BigDecimal.ROUND_HALF_UP));
               }
               if(Math.abs(ytdTopCategSpend.doubleValue())>0.001) {
                   row.setPercentOfCategory(cat.ytdSpend.divide(ytdTopCategSpend,1,BigDecimal.ROUND_HALF_UP));
               }
           }
           row.calculate (totalSum, ytdTotalSum, topCategSpend.doubleValue(), ytdTopCategSpend.doubleValue());
           resultLines.add(row);
      }

     // Total Row
      ARecord totalRow = new ARecord();
      totalRow.init();
      totalRow.setCategory(GRAND_TOTAL);
      totalRow.setLevel(1);
      totalRow.setCurrency(this.localCurrencySign);
      totalRow.setCategorySpend(new BigDecimal(totalSum));
      totalRow.setMonthBudget(new BigDecimal(budgetSum));
      totalRow.setYTDCategorySpend(new BigDecimal(ytdTotalSum));
      totalRow.setYTDBudget(new BigDecimal(ytdBudgetSum));
      totalRow.setPercentOfTotal(null);
      totalRow.setPercentOfYTDTotal(null);
      totalRow.calculateTotal();

      resultLines.add(totalRow);
      return resultLines;
    }

    protected ArrayList getResultOfSheet2Query(Connection conn, Map pParams) throws Exception{
      BigDecimal zeroAmt = new BigDecimal(0);
      Integer zeroNum = new Integer(0);

 //     String userIdStr = (String) pParams.get("CUSTOMER");
 //     String storeIdStr = (String) pParams.get("STORE_SELECT");

 //     String siteCritSql = getUserSites(conn, userIdStr, storeIdStr);

      //String subSql= getSheet2QuerySql( pParams);

        FiscalPeriod begFp  = (FiscalPeriod) pParams.get(BEGIN_FP);
        FiscalPeriod endFp  = (FiscalPeriod) pParams.get(END_FP);

        Date begDate = begFp.begDate;
        Date endDate = endFp.endDate;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(endDate);
        gc.add(GregorianCalendar.DATE, 1);
        endDate = gc.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String begDateS = sdf.format(begDate);
        String endDateS = sdf.format(endDate);
      //======================================================================//


        String filterCrit =
          "  AND nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) >= to_date('" + begDateS + "','mm/dd/yyyy') \n" +
          "  AND nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) < to_date('" + endDateS + "','mm/dd/yyyy') \n" +
        ((Utility.isSet(siteCrit))? "  AND o.SITE_ID IN (" + siteCrit + ") \n" : "") ;

        String selectSql =
          "select \n" +
          " be.short_desc AS location_name, adr.city, adr.state_province_cd AS state, \n" +
          " (select local_code from CLW_CURRENCY where locale = iinfo.locale_cd) CURRENCY, \n" +
          " i.short_desc as item_desc, \n" +
          " iinfo.*, (CASE WHEN iinfo.value_num > 0 THEN iinfo.value_num ELSE ia.item2_id END) as category_id\n" +
          " FROM  \n" +
          " ( \n" +
          "   SELECT    \n" +
          "             o.site_id, \n" +
          "             oi.item_id, \n" +
          "             o.locale_cd, \n" +
          "             c.catalog_id AS acct_catalog_id, \n" +
          "             o.ORDER_NUM CUST_PO_NUM, \n" +
          "             nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) order_date, \n" +
          "             NVL(oi.cust_item_sku_num, oi.dist_item_sku_num)  ITEM_NUM, \n" +
          "             oi.TOTAL_QUANTITY_ORDERED QTY, \n" +
          "             NVL (oi.cust_item_uom, oi.item_uom) AS UOM, \n" +
          "             oim.value_num, \n" +
          "             TOTAL_QUANTITY_ORDERED * CUST_CONTRACT_PRICE AS EXTENDED_PRICE \n" +
          "   FROM clw_order_item oi, clw_order o, clw_catalog_assoc ca, clw_catalog c, \n" +
          "       (SELECT order_item_id, value_num FROM clw_order_item_meta WHERE name = '" +
              RefCodeNames.ORDER_ITEM_META_NAME.CATEGORY_NAME + "') oim \n" +
          "   WHERE o.order_status_cd NOT IN \n" +
          "         ('Cancelled', \n" +
          "          'ERP Rejected', \n" +
          "          'Rejected', \n" +
          "          'Duplicate', \n" +
          "          'REFERENCE_ONLY', \n" +
          "			 'Received',	\n "+
          "			 'Pending Review',	\n "+
          "			 'Pending Order Review'	\n "+
          
          "         ) \n" +
          "   AND o.order_id = oi.order_id \n" +
          "   AND Nvl(oi.ORDER_ITEM_STATUS_CD,'aa')!='CANCELLED' \n" +
          "   AND o.account_id = ca.bus_entity_id \n" +
          "   AND ca.catalog_assoc_cd = 'CATALOG_ACCOUNT' \n" +
          "   AND ca.catalog_id = c.catalog_id \n" +
          "   AND c.catalog_type_cd = 'ACCOUNT' \n" +
          "   AND oi.order_item_id = oim.order_item_id(+)\n" + 
          "   AND EXISTS (SELECT 1 FROM clw_inventory_level il WHERE il.bus_entity_id = o.site_id) \n" +
          filterCrit +
          " ) iinfo, clw_item_assoc ia, clw_item i, clw_address adr, clw_bus_entity be \n" +
          " WHERE iinfo.item_id = ia.item1_id(+) \n" +
          " AND iinfo.acct_catalog_id = ia.catalog_id(+) \n" +
          " AND ITEM_ASSOC_CD(+) = 'PRODUCT_PARENT_CATEGORY' \n" +
          " AND iinfo.item_id = i.item_id \n" +
          " AND iinfo.site_id = adr.bus_entity_id(+) \n" +
          " AND adr.primary_ind(+) = 1 \n" +
          " AND be.bus_entity_id = iinfo.site_id \n" +
          "  order by UPPER(location_name), UPPER(CUST_PO_NUM), UPPER(item_num) \n";
/*
 String selectSql1 =
          "select \n" +
          "   (SELECT short_desc FROM clw_bus_entity \n WHERE bus_entity_id = sub.site_id) AS location_name, \n" +
          "   city, \n" +
          "   state_province_cd state, \n" +
          "   (select local_code from CLW_CURRENCY where locale = sub.locale_cd) CURRENCY, \n" +
          "   sub.* \n"+
          " from \n" +
          "  (" +
          //subSql +
          "  ) sub, " +
          " clw_address \n" +
          "WHERE bus_entity_id = site_id \n" +
          " order by UPPER(location_name), UPPER(CUST_PO_NUM), UPPER(item_num) \n";
*/
      PreparedStatement pstmt = conn.prepareStatement(selectSql);

      ResultSet rs = pstmt.executeQuery();
 //     ArrayList psViewV= new ArrayList();
      DetailVector psViewV= new DetailVector();
      while (rs.next()){
          BRecord record = new BRecord();
          String locName = (rs.getString("LOCATION_NAME") != null) ? rs.getString("LOCATION_NAME") : "";
          String city = (rs.getString("CITY") != null) ? rs.getString("CITY") : "";
          String state = (rs.getString("STATE") != null) ? rs.getString("STATE") : "";
          String currency = (rs.getString("CURRENCY") != null) ? rs.getString("CURRENCY") : "";

//          String siteId = (rs.getString("site_id") != null) ? rs.getString("site_id") : "";
//          String itemId = (rs.getString("ITEM_ID") != null) ? rs.getString("ITEM_ID") : "";
          String custPoNum = (rs.getString("CUST_PO_NUM") != null) ? rs.getString("CUST_PO_NUM") : "";
          Date orderDate = rs.getDate("ORDER_DATE")  ;
          String itemNum = (rs.getString("ITEM_NUM") != null) ? rs.getString("ITEM_NUM") : "";
          String itemDesc = (rs.getString("ITEM_DESC") != null) ? rs.getString("ITEM_DESC") : "";
          Integer qty = (Utility.isSet(rs.getString("QTY"))) ? new Integer(rs.getString("QTY")) : zeroNum ;
          String uom  = (rs.getString("UOM") != null) ? rs.getString("UOM") : "";
          BigDecimal extendedPrice = (rs.getBigDecimal("EXTENDED_PRICE") != null) ? rs.getBigDecimal("EXTENDED_PRICE") : zeroAmt;
          int categId = rs.getInt("category_id");
          Category categ = categoryMap.get(categId);
          String category =""; //!!!!!!!!!!!!!!!!!!!!!!!  getCategoryByItem(1, itemId );
          String subCategory = ""; //!!!!!!!!!!!!!!!!!!!!!!! getCategoryByItem(2, itemId )+
          if(categ!=null) {
              category = categ.topCategName;
              if(categ.topCategId==categ.subCategId && categ.subCategId!=categ.subSubCategId) {
                  subCategory = categ.subSubCategName;
              } else if (categ.topCategId!=categ.subCategId && categ.subCategId!=categ.subSubCategId) {
                  subCategory = categ.subCategName+"/"+categ.subSubCategName;
              }
          }
          record.setLocationName(locName);
          record.setCity(city);
          record.setState(state);
          record.setCurrency(currency);
          record.setCustPONum(custPoNum);
          record.setOrderDate(orderDate);
          record.setCategory(category);
          record.setSubcategory(subCategory);
          record.setItemNum(itemNum);
          record.setDesc(itemDesc);
          record.setQty(qty);
          record.setUom(uom);
          record.setExtendedVal(extendedPrice);

          psViewV.add(record);
      }
      pstmt.close();
      rs.close();
      psViewV.sort("Category");
      return psViewV;

    }

     private String getOrderQuerySql(Map pParams) {
        FiscalPeriod ytdBegFp  = (FiscalPeriod) pParams.get(BEGIN_YTD_YEAR_FP);
        FiscalPeriod ytdEndFp  = (FiscalPeriod) pParams.get(END_YTD_YEAR_FP);
        FiscalPeriod begFp  = (FiscalPeriod) pParams.get(BEGIN_FP);
        FiscalPeriod endFp  = (FiscalPeriod) pParams.get(END_FP);

        Date ytdBegDate = ytdBegFp.begDate;
        Date begDate = begFp.begDate;
        Date minDate = ytdBegDate;
        if(ytdBegDate.compareTo(begDate)>0) minDate = begDate;

        Date endDate = endFp.endDate;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(endDate);
        gc.add(GregorianCalendar.DATE, 1);
        endDate = gc.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String ytdBegDateS = sdf.format(ytdBegDate);
        String begDateS = sdf.format(begDate);
        String minDateS = sdf.format(minDate);
        String endDateS = sdf.format(endDate);

        String filterCrit =
           " AND nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) >= to_date('" + minDateS + "','mm/dd/yyyy') \n" +
           " AND nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) < to_date('" + endDateS + "','mm/dd/yyyy') \n" +
          ((Utility.isSet(siteCrit))? "  AND o.SITE_ID IN (" + siteCrit + ") \n" : "") ;


        String orderSql=
           "SELECT category_id, cost_center_id, \n" +
           " Sum(period_spend) period_spend, \n" +
           " Sum(ytd_spend) ytd_spend \n" +
           " FROM \n" +
           " ( \n" +
           " SELECT oinfo.*, \n" +
           " (CASE WHEN value_num > 0 THEN value_num ELSE\n" + 
           "  (SELECT ia.item2_id \n" +
           "   FROM clw_catalog cat, clw_catalog_assoc cata, clw_item_assoc ia \n" +
           "   WHERE 1=1 \n" +
           "   AND cata.bus_entity_id = oinfo.account_id \n" +
           "   AND cata.catalog_id = cat.catalog_id \n" +
           "   AND cat.catalog_type_cd = 'ACCOUNT' \n" +
           "   AND ia.item1_id = oinfo.item_id \n" +
           "   AND ia.catalog_id = cat.catalog_id \n" +
           "   AND ia.item_assoc_cd = 'PRODUCT_PARENT_CATEGORY') \n" +
           "  END) category_id \n" +
           "  FROM  \n" +
           " (SELECT o.account_id, oi.item_id, oi.COST_CENTER_ID, oim.value_num, \n" +
           "   sum((CASE WHEN nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) >=TO_DATE('"+begDateS+"','MM/dd/yyyy')  \n" +
           "   THEN  (TOTAL_QUANTITY_ORDERED * CUST_CONTRACT_PRICE) ELSE 0 END)) period_spend, \n" +
           "   sum((CASE WHEN nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) >=TO_DATE('"+ytdBegDateS+"','MM/dd/yyyy')  \n" +
           "   THEN  (TOTAL_QUANTITY_ORDERED * CUST_CONTRACT_PRICE) ELSE 0 END)) ytd_spend \n" +
           "     FROM clw_order_item oi, clw_order o, \n" +
           "         (SELECT order_item_id, value_num  FROM clw_order_item_meta WHERE name = '" + 
                       RefCodeNames.ORDER_ITEM_META_NAME.CATEGORY_NAME + "') oim" +
           "     WHERE o.ORDER_ID = oi.ORDER_ID \n" +
           "       AND  o.ORDER_STATUS_CD NOT IN \n" +
           "                      ('Cancelled', \n" +
           "                       'ERP Rejected', \n" +
           "                       'Rejected', \n" +
           "                       'Duplicate', \n" +
           "                       'REFERENCE_ONLY', \n" +
           "					   'Received',	\n "+
           "					   'Pending Review',	\n "+
           "					   'Pending Order Review'	\n "+
           "                      ) \n" +
           "      AND oi.order_item_id = oim.order_item_id(+)\n" + 
           "      AND Nvl(oi.order_item_status_cd,'RRR')!= 'CANCELLED' \n" +
           filterCrit  +
           "  AND EXISTS (SELECT 1 FROM clw_inventory_level il WHERE il.bus_entity_id = o.site_id) \n" +
           "  GROUP BY o.account_id, oi.item_id, oi.COST_CENTER_ID, oim.value_num \n" +
           "  ) oinfo \n" +
           "  ) \n" +
           "  GROUP BY category_id, cost_center_id \n";

        /*
        String orderSql=
           " SELECT COST_CENTER_ID, ITEM2_ID CATEGORY_ID, \n"+
           "        sum(SPEND) SPEND, \n" +
           "        sum(YTD_SPEND) YTD_SPEND, \n" +
           "        (select SHORT_DESC from CLW_ITEM i where  \n"+
           "           i.ITEM_ID = ITEM2_ID) CATEGORY_NAME \n"+
           " FROM \n" +
           " (SELECT COST_CENTER_ID, ITEM_ID, item2_id, SPEND, YTD_SPEND \n" +
           "  FROM clw_item_assoc ia, \n" +
           "  (SELECT y.ITEM_ID, y.COST_CENTER_ID,\n" +
           "          nvl(m.SPEND, 0) SPEND, \n" +
           "          nvl(y.SPEND, 0) YTD_SPEND \n" +
           "   FROM \n" +
           "   (SELECT item_id, \n" +
           "           oi.COST_CENTER_ID, \n" +
           "           sum(TOTAL_QUANTITY_ORDERED * CUST_CONTRACT_PRICE) SPEND   \n" +
           "    FROM clw_order_item oi, clw_order o \n" +
           "    WHERE o.ORDER_ID = oi.ORDER_ID \n" +
           "      AND  o.ORDER_STATUS_CD NOT IN \n" +
           "                     ('Cancelled', \n" +
           "                      'ERP Rejected', \n" +
           "                      'Rejected', \n" +
           "                      'Duplicate', \n" +
           "                      'REFERENCE_ONLY' \n" +
           "                     ) \n" +
           filterCrit  + " \n" +
           "      GROUP BY oi.COST_CENTER_ID, ITEM_ID \n" +
           "    ) m, \n" +
           "   (SELECT item_id, \n" +
           "           oi.COST_CENTER_ID, \n" +
           "           sum(TOTAL_QUANTITY_ORDERED * CUST_CONTRACT_PRICE) SPEND   \n" +
           "    FROM clw_order_item oi, clw_order o \n" +
           "    WHERE o.ORDER_ID = oi.ORDER_ID \n" +
           "      AND  o.ORDER_STATUS_CD NOT IN \n" +
           "                     ('Cancelled', \n" +
           "                      'ERP Rejected', \n" +
           "                      'Rejected', \n" +
           "                      'Duplicate', \n" +
           "                      'REFERENCE_ONLY' \n" +
           "                     ) \n" +
           ytdFilterCrit + " \n" +
           "      GROUP BY oi.COST_CENTER_ID, ITEM_ID \n" +
           "    ) y \n" +
           "   WHERE m.COST_CENTER_ID (+) = y.COST_CENTER_ID \n" +
           "     AND m.ITEM_ID (+) = y.ITEM_ID \n" +
           "  ) ord \n " +
           " WHERE ia.ITEM1_ID = ord.ITEM_ID \n" +
           "    AND CATALOG_ID in ( \n" + catalogId + " ) " +
           " ) group by COST_CENTER_ID, ITEM2_ID \n" +
           " order by COST_CENTER_ID, CATEGORY_ID" ;
        */
        return orderSql;
    }



    private String getBudgetQuerySql (Map pParams){

      /*
      String pStartDate = (String)dateRange.get(0);
      String pEndDate = (String)dateRange.get(1);

      String startYear = pStartDate.substring(6,10);
      String startPeriod = pStartDate.substring(0, 5);
      String endYear = pEndDate.substring(6,10);
      String endPeriod = pEndDate.substring(0, 5);
      String startYTDDate = "01/01/" +endYear;
      */

      FiscalPeriod ytdBegFp  = (FiscalPeriod) pParams.get(BEGIN_YTD_YEAR_FP);
      FiscalPeriod ytdEndFp  = (FiscalPeriod) pParams.get(END_YTD_YEAR_FP);
      FiscalPeriod begFp  = (FiscalPeriod) pParams.get(BEGIN_FP);
      FiscalPeriod endFp  = (FiscalPeriod) pParams.get(END_FP);

      int ytdBegFiscalPeriod = ytdBegFp.fiscalYear*100 + ytdBegFp.periodNumber;
      int begFiscalPeriod = begFp.fiscalYear*100 + begFp.periodNumber;
      int endFiscalPeriod = endFp.fiscalYear*100 + endFp.periodNumber;
      int minFiscalPeriod = begFiscalPeriod;
      if (minFiscalPeriod > ytdBegFiscalPeriod) minFiscalPeriod =  ytdBegFiscalPeriod;

      String budgetSql =
         "SELECT  \n" +
         " COST_CENTER_ID, \n" +
         " Sum ((CASE WHEN budget_year*100+budget_period >= "+ytdBegFiscalPeriod + " \n" +
         "      THEN AMOUNT_ALLOCATED ELSE 0 END)) ytd_budget, \n" +
         " Sum ((CASE WHEN budget_year*100+budget_period >= "+begFiscalPeriod + " \n" +
         "      THEN AMOUNT_ALLOCATED ELSE 0 END)) budget \n" +
         " FROM TCLW_ACCTBUDGET_REPORT \n" +
         " WHERE 1=1 \n" +
         " AND budget_year*100+budget_period >= " + minFiscalPeriod + " \n" +
         " AND budget_year*100+budget_period <= " + endFiscalPeriod + " \n" +
         ((Utility.isSet(siteCrit))? "   AND SITE_ID IN (" + siteCrit + ") \n" : "") +
         " GROUP BY COST_CENTER_ID \n";
/*
        String budgetSql =
         "SELECT m.COST_CENTER_ID, \n" +
         "          BUDGET, \n" +
         "          YTD_BUDGET \n" +
         " FROM \n" +
          "(SELECT COST_CENTER_ID, \n" +
          "       sum(AMOUNT_ALLOCATED)  BUDGET \n" +
          "FROM  \n" +
          " (SELECT COST_CENTER_ID,  \n" +
          "     AMOUNT_ALLOCATED, \n" +
          "     to_date(PERIOD_START_DATE||'/'||BUDGET_YEAR, 'mm/dd/yyyy') START_DATE, \n" +
          "     to_date(PERIOD_END_DATE||'/'||BUDGET_YEAR, 'mm/dd/yyyy') END_DATE \n" +
          "  FROM TCLW_ACCTBUDGET_REPORT   \n" +
          "  WHERE BUDGET_YEAR is not null AND BUDGET_YEAR >0 \n" +
          "     AND BUDGET_YEAR in ( " +startYear+", " + endYear +" ) \n" +
          ((Utility.isSet(siteCrit))? "   AND SITE_ID IN (" + siteCrit + ") \n" : "") +
          " )    \n" +
          "WHERE START_DATE >=to_date('" + pStartDate + "','mm/dd/yyyy'  ) \n" +
          "  AND END_DATE <= to_date('"  + pEndDate   + "','mm/dd/yyyy'  )   \n" +
          "GROUP BY COST_CENTER_ID ) m,  \n" +

          "(SELECT COST_CENTER_ID, \n" +
          "       sum(AMOUNT_ALLOCATED)  YTD_BUDGET \n" +
          "FROM  \n" +
          " (SELECT COST_CENTER_ID,  \n" +
          "     AMOUNT_ALLOCATED, \n" +
          "     to_date(PERIOD_START_DATE||'/'||BUDGET_YEAR, 'mm/dd/yyyy') START_DATE, \n" +
          "     to_date(PERIOD_END_DATE||'/'||BUDGET_YEAR, 'mm/dd/yyyy') END_DATE \n" +
          "  FROM TCLW_ACCTBUDGET_REPORT   \n" +
          "  WHERE BUDGET_YEAR is not null AND BUDGET_YEAR >0 \n" +
          "     AND BUDGET_YEAR in ( " + endYear +" ) \n" +
          ((Utility.isSet(siteCrit))? "   AND SITE_ID IN (" + siteCrit + ") \n" : "") +
          " )    \n" +
          "WHERE START_DATE >=to_date('" + startYTDDate + "','mm/dd/yyyy'  ) \n" +
          "  AND END_DATE <= to_date('"  + pEndDate     +  "','mm/dd/yyyy'  )   \n" +
          "GROUP BY COST_CENTER_ID ) y  \n" +
          "WHERE m.COST_CENTER_ID (+) = y.COST_CENTER_ID \n " ;
*/
      return  budgetSql;

    }

    protected String getSheet2QuerySql( Map pParams) throws Exception {
      //=========== getting parameters =======================================
        FiscalPeriod begFp  = (FiscalPeriod) pParams.get(BEGIN_FP);
        FiscalPeriod endFp  = (FiscalPeriod) pParams.get(END_FP);

        Date begDate = begFp.begDate;
        Date endDate = endFp.endDate;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(endDate);
        gc.add(GregorianCalendar.DATE, 1);
        endDate = gc.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String begDateS = sdf.format(begDate);
        String endDateS = sdf.format(endDate);
      //======================================================================//


      String filterCrit =
          "  AND nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) >= to_date('" + begDateS + "','mm/dd/yyyy') \n" +
          "  AND nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) < to_date('" + endDateS + "','mm/dd/yyyy') \n" +
        ((Utility.isSet(siteCrit))? "  AND o.SITE_ID IN (" + siteCrit + ") \n" : "") ;

       String sql =

          " SELECT     o.site_id, \n" +
          "            oi.item_id, \n" +
          "            o.locale_cd, \n" +
          "            o.ORDER_NUM CUST_PO_NUM, \n" +
          "            nvl(o.revised_order_date,o.ORIGINAL_ORDER_DATE) order_date, \n" +

          "            (select SHORT_DESC from CLW_ITEM where ITEM_ID = ia.ITEM2_ID) Subcatagory, \n" +
          "            NVL(oi.cust_item_sku_num, oi.dist_item_sku_num)  ITEM_NUM, \n" +
          "            (select SHORT_DESC from CLW_ITEM where ITEM_ID = oi.ITEM_ID) ITEM_DESC, \n" +
          "            oi.TOTAL_QUANTITY_ORDERED QTY, \n" +
          "            NVL (oi.cust_item_uom, oi.item_uom) AS UOM, \n" +
          "            TOTAL_QUANTITY_ORDERED * CUST_CONTRACT_PRICE AS EXTENDED_PRICE \n" +
//          "            oi.cust_contract_price AS EXTENDED_PRICE \n" +
          "  FROM clw_order_item oi, clw_order o , clw_item_assoc ia \n" +
          "  WHERE o.order_status_cd NOT IN \n" +
          "        ('Cancelled', \n" +
          "         'ERP Rejected', \n" +
          "         'Rejected', \n" +
          "         'Duplicate', \n" +
          "         'REFERENCE_ONLY' \n" +
          "        ) \n" +
          "    AND o.order_id = oi.order_id \n" +
          "    AND ia.Item1_id = oi.item_id \n" +
          "    AND ia.Catalog_id =  " + catalogId +" \n" +
          "    AND ia.ITEM_ASSOC_CD = '" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY +"' \n" +
          filterCrit  ;


        return sql;
    }

    private String getUserSites(Connection pCon, String pUserIdStr, String pStoreIdStr){
      String siteIdsSql = "";
      int userId = (Utility.isSet(pUserIdStr)) ? Integer.parseInt(pUserIdStr) : -1;
      int storeId = (Utility.isSet(pStoreIdStr)) ? Integer.parseInt(pStoreIdStr) : -1;

      try {
        UserData userD = UserDataAccess.select(pCon, userId);
        String userTypeCd = userD.getUserTypeCd();
        if (RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.REPORTING_USER.equals(userTypeCd) ||
            RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR.equals(userTypeCd)) {
           siteIdsSql=
               "SELECT DISTINCT BUS_ENTITY_ID \n" +
               " FROM CLW_BUS_ENTITY_ASSOC be1, CLW_BUS_ENTITY_ASSOC be2, CLW_BUS_ENTITY be \n" +
               " WHERE be1.BUS_ENTITY2_ID = " + storeId + " \n" +
               "   AND be1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
               "   AND be2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
               "   AND be1.BUS_ENTITY1_ID = be2.BUS_ENTITY2_ID \n" +
               "   AND be.BUS_ENTITY_ID   = be2.BUS_ENTITY1_ID \n" +
               "   AND BUS_ENTITY_STATUS_CD = 'ACTIVE' \n" ;
        } else if ( RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
                    RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) )  {
          siteIdsSql=
              " SELECT DISTINCT BUS_ENTITY_ID FROM CLW_USER_ASSOC \n" +
              "  WHERE USER_ID = " + userId + " AND USER_ASSOC_CD = 'SITE' \n" ;
        } else {
          siteIdsSql=
              "SELECT DISTINCT BUS_ENTITY_ID \n" +
             " FROM CLW_BUS_ENTITY_ASSOC be1, CLW_BUS_ENTITY_ASSOC be2, \n" +
             " CLW_BUS_ENTITY be, CLW_USER_ASSOC ua \n" +
             " WHERE be1.BUS_ENTITY2_ID = " + storeId + " \n" +
             "   AND be1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
             "   AND be2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
             "   AND be1.BUS_ENTITY1_ID = be2.BUS_ENTITY2_ID \n" +
             "   AND be.BUS_ENTITY_ID   = be2.BUS_ENTITY1_ID \n" +
             "   AND BUS_ENTITY_STATUS_CD = 'ACTIVE' \n" +
             "   AND be.BUS_ENTITY_ID = ua.BUS_ENTITY_ID \n " +
             "   AND USER_ID = " + userId + " AND USER_ASSOC_CD = 'SITE' \n" ;
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

      return siteIdsSql;
    }

    protected void createCategoryMaps (Connection con,  String pCategoryFilter) throws SQLException {


      String categoryCondStr ="";
      if (Utility.isSet(pCategoryFilter) ){
        categoryCondStr = " and UPPER(SHORT_DESC) like '%" + pCategoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
      }
/*
      String sql =
          "select item1_id,  \n" +
          "      nvl(cat.Category1_id, ia.item2_id)  top_id, \n"+
          "      nvl(cat.Category2_id, ia.item2_id)  sub1_id, \n"+
          "      nvl(cat.Category3_id, ia.item2_id)  sub2_id, \n"+
          "      (select short_desc from CLW_ITEM where item_id = \n"+
          "          nvl(cat.Category1_id, ia.item2_id)) topCategory, \n"+
          "      (select short_desc from CLW_ITEM where item_id = \n"+
          "         nvl(cat.Category2_id, ia.item2_id)) subCategory1, \n"+
          "      (select short_desc from CLW_ITEM where item_id = \n"+
          "          nvl(cat.Category3_id, ia.item2_id)) subCategory2 \n"+

         "from  \n"+
         " clw_item_assoc ia,  \n"+
         " ( select distinct    \n"+
         "                  a.Item2_id category1_id, \n"+
         "                  a.Item1_id category2_id, \n"+
         "                  nvl(b.Item1_id,a.Item1_id)  category3_id \n"+
         "   from   \n"+
         "   ( select distinct  ITEM2_ID , ITEM1_ID  \n"+
         "    from CLW_ITEM_ASSOC  \n"+
         "    where CATALOG_ID = "+ catalogId + " \n"+
         "      and ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY'  \n"+
         "      connect by PRIOR ITEM1_ID = ITEM2_ID  \n"+
         "      start with ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY') a , \n"+

         "   ( select distinct ITEM2_ID, ITEM1_ID   \n"+
         "    from CLW_ITEM_ASSOC  \n"+
         "    where CATALOG_ID = "+ catalogId + "\n"+
         "      and ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY'  \n"+
         "      connect by PRIOR ITEM1_ID = ITEM2_ID  \n"+
         "      start with ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY') b  \n"+
         "   where a.item1_id = b.item2_id(+) \n"+
         "  ) cat \n"+
         "where ia.Item2_id = category3_id (+) \n"+
         "   and ia.catalog_id = "+ catalogId + "   \n" +
         "order by topCategory, subCategory1,subCategory2  \n";
*/
      String sql =
          "SELECT   \n"+
          "  cs.item_id sub2_id,   \n"+
          "  Nvl(ia2.item2_id,cs.item_id) sub1_id,   \n"+
          "  Nvl(ia3.item2_id,Nvl(ia2.item2_id,cs.item_id)) top_id,  \n"+
          "  (select short_desc from CLW_ITEM where item_id =   \n"+
          "  Nvl(ia3.item2_id,Nvl(ia2.item2_id,cs.item_id))) topCategory,  \n"+
          "  (select short_desc from CLW_ITEM where item_id =  \n"+
          "  Nvl(ia2.item2_id,cs.item_id)) subCategory1,  \n"+
          "  (select short_desc from CLW_ITEM where item_id = cs.item_id) subCategory2  \n"+

          "  FROM clw_catalog_structure cs  \n"+
          "  left join clw_item_assoc ia2  \n"+
          "     ON cs.item_id = ia2.item1_id  \n"+
          "     AND ia2.catalog_id = cs.catalog_id  \n"+
          "     AND ia2.ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY'  \n"+
          "  left join clw_item_assoc ia3  \n"+
          "     ON ia2.item2_id = ia3.item1_id  \n"+
          "     AND ia2.catalog_id = ia3.catalog_id  \n"+
          "     AND ia3.ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY'  \n"+
          "  WHERE cs.catalog_id = "+ catalogId + " \n"+
          "  AND cs.CATALOG_STRUCTURE_CD ='CATALOG_CATEGORY'  ";

      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      //itemToCatMap = new HashMap();
      //categoryToCategoryMap = new HashMap();
      categoryMap = new HashMap<Integer,Category>();
      while (rs.next()) {
        /*
        HashMap catMap = new HashMap();
        HashMap catSummaryMap = new HashMap();
        CategorySummary catSummary = null;
        catSummaryCollection = new HashMap();

        String item = (rs.getString(1) != null) ? rs.getString(1) : "0";
        int topCatId = rs.getInt(2) ;
        int subCat1Id = rs.getInt(3);
        int subCat2Id = rs.getInt(4);
        String topCat = (rs.getString(5) != null) ? rs.getString(5) : "0";
        String subCat1 = (rs.getString(6) != null) ? rs.getString(6) : "0";
        String subCat2 = (rs.getString(7) != null) ? rs.getString(7) : "0";
        Integer level = new Integer(1);
        if (topCatId == subCat2Id ) {
          subCat1 = null;
          subCat2 = null;
        } else if ( topCatId != subCat2Id  && subCat1Id == subCat2Id) {
          subCat2 = null;
        }
        catMap.put(new Integer(1), topCat);
        catMap.put(new Integer(2), subCat1);
        catMap.put(new Integer(3), subCat2);
        ArrayList catSummaryV = new ArrayList();
        if (subCat1 != null || subCat2 != null ){
          if (subCat1 != null ) {
            catSummary = (CategorySummary)catSummaryCollection.get(String.valueOf(topCatId));
            if (catSummary == null) {
              catSummary = new CategorySummary(String.valueOf(topCatId), 1, topCat);
              catSummaryCollection.put(String.valueOf(topCatId), catSummary);
            }
            catSummaryV.add(catSummary);
          }
          if (subCat2 != null) {
            catSummary = (CategorySummary)catSummaryCollection.get(String.valueOf(subCat1Id));
            if (catSummary == null) {
              catSummary = new CategorySummary(String.valueOf(subCat1Id), 2, subCat1);
              catSummaryCollection.put(String.valueOf(subCat1Id), catSummary);
            }
            catSummaryV.add(catSummary);
          }
        }
        categoryToCategoryMap.put(String.valueOf(subCat2Id), catSummaryV);
        itemToCatMap.put(item, catMap );
         */
        Category categ = new Category();
        categ.subSubCategId = rs.getInt("sub2_id") ;
        categ.subCategId = rs.getInt("sub1_id") ;
        categ.topCategId = rs.getInt("top_id") ;
        categ.subSubCategName = rs.getString("subCategory2");
        categ.subCategName = rs.getString("subCategory1");
        categ.topCategName = rs.getString("topCategory");
        categ.ytdBudget = new BigDecimal(0);
        categ.ytdSpend = new BigDecimal(0);
        categ.budget = new BigDecimal(0);
        categ.spend = new BigDecimal(0);
        categ.ytdNoSubSpend = new BigDecimal(0);
        categ.noSubSpend = new BigDecimal(0);
        categoryMap.put(categ.subSubCategId, categ);

      }
      stmt.close();
   }

   public String padString(String pString, int pNum ){
     String shift = "";
     for (int i = 0; i < 3*(pNum-1); i++) {
       shift += SHIFT_CHAR;
     }
     return shift + pString;
   }

/*
   private String getCategoryByCategory(int pLevel, String pCategoryId) {
      Integer level = new Integer(pLevel);
      String categoryName = "";
      if (categoryToCategoryMap != null) {
        HashMap catMap = (HashMap) categoryToCategoryMap.get(pCategoryId);
        if (catMap != null) {
          categoryName = (String)catMap.get(level);
        }
      }
      categoryName = (categoryName != null) ? padString(categoryName, pLevel ) : "";
      return categoryName;
    }
*/
/*
    private String getCategoryByItem(int pLevel, String itemId) {
      Integer level = new Integer(pLevel);
      String categoryName = "";
      if (itemToCatMap != null) {
        HashMap catMap = (HashMap) itemToCatMap.get(itemId);
        if (catMap != null) {
          categoryName = (String)catMap.get(level);
        }
      }
      categoryName = (categoryName != null) ? categoryName : "";
      return categoryName;
    }
    */

    protected int getCatalogId(Connection con, int pStoreId) throws Exception
    {
      //Get catalog id
      int catalogId = 0;
      String sql = "select c.catalog_id " +
          " from clw_catalog_assoc ca, clw_catalog c" +
          " where c.catalog_type_cd = 'STORE'" +
          " and c.catalog_status_cd in 'ACTIVE'" +
          " and c.catalog_id = ca.catalog_id" +
          " and ca.catalog_assoc_cd = 'CATALOG_STORE'" +
          " and ca.bus_entity_id = " + pStoreId;

      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      int count = 0;
      while (rs.next()) {
        count++;
        catalogId = rs.getInt(1);
      }
      rs.close();
      stmt.close();
      if (count == 0) {
        String errorMess = "No catalog for store. Store id: " + pStoreId;
        throw new SQLException(errorMess);
      }
      if (count > 1) {
        String errorMess = "Multiple active catalogs for store. Store id: " +
            pStoreId;
        throw new SQLException(errorMess);
      }
      return catalogId;
    }


/*
    protected String getTitle() {
      String title = "" ;
      return title;
    }
*/
    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      title.add(ReportingUtils.createGenericReportColumnView(pTitle,ReportingUtils.PAGE_TITLE, null,"10"));
//      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Store : " + getListOfEntityNames(con, (String) pParams.get(STORE_S)),0,255,"VARCHAR2"));
      String controlName = null;
      if (pParams.containsKey(this.BEG_DATE_S) && Utility.isSet((String) pParams.get(this.BEG_DATE_S)) &&
          pParams.containsKey(this.END_DATE_S) && Utility.isSet((String) pParams.get(this.END_DATE_S))){
        title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(BEG_DATE_S) + " - " + (String) pParams.get(END_DATE_S),ReportingUtils.PAGE_TITLE, null,"10"));
      } else {
        controlName = this.END_MONTH_S;
        if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
          title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(this.END_MONTH_S)+ "/" + (String) pParams.get(this.END_YEAR_S) ,ReportingUtils.PAGE_TITLE, null,"10"));
        }
      }
  return title;
}

 private ArrayList convertToDateStr ( String pEndMonthStr, String pEndYearStr){

   //Month year dropdown
   ArrayList dateRange = new ArrayList();
   int endYear = Integer.parseInt(pEndYearStr);
   int endMonth = Integer.parseInt(pEndMonthStr);
   if(endYear!=0 && endMonth !=0){
         GregorianCalendar begC = new GregorianCalendar(endYear, endMonth - 1, 1);
         Date beginDateC = begC.getTime();

         GregorianCalendar endC = new GregorianCalendar(endYear,endMonth-1,1);
         endC.set(Calendar.DAY_OF_MONTH, endC.getActualMaximum(Calendar.DATE));
         Date endDateC = endC.getTime();


         SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

         dateRange.add(df.format(beginDateC));
         dateRange.add(df.format(endDateC));
   }
   return dateRange;
 }
/*
 private BigDecimal calcPercent(double pValue, int pTotal){
   double percent = 0;
   BigDecimal bd = null;
   if (pTotal != 0){
     percent = pValue / pTotal;
     bd = new BigDecimal(percent);
     bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
   }
   return bd;
 }
*/
/*
 private String getCurrency(Connection pConn, String customerIdStr){
   return "$";
 }
*/

 // Customise Cell with Style
 public GenericReportCellView getCellView(Object dataVal, String styleName){
  GenericReportCellView cellView = GenericReportCellView.createValue();
  cellView.setDataValue(dataVal);
  cellView.setStyleName(styleName);
  return cellView;
}

private BigDecimal calcPercent(BigDecimal val1, BigDecimal val2){
  BigDecimal result = null;

  if (val1 != null && val2 != null){
    double val2D = val2.doubleValue();
    double val1D = val1.doubleValue();
    if (val2D != 0) {
      result = new BigDecimal(val1D/val2D);
      result.setScale(3, BigDecimal.ROUND_HALF_UP);
    }
  }
  return result;
}


FiscalPeriod createFp (FiscalPeriod prevFp, int fiscalYear, int period, String mmdd,  int currYear)
throws Exception
{
   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
   FiscalPeriod fp = null;
   if(mmdd!=null) {
       Date periodBeg = sdf.parse(mmdd+'/'+currYear);
       if(prevFp!=null && prevFp.begDate!=null && prevFp.begDate.compareTo(periodBeg)>0) {
         currYear++;
         periodBeg = sdf.parse(mmdd+'/'+currYear);
       }
       fp = new FiscalPeriod();
       fp.periodYear = currYear;
       fp.periodNumber = period;
       fp.fiscalYear = fiscalYear;
       fp.begDate = periodBeg;
       if(prevFp!=null) {
           GregorianCalendar gc = new GregorianCalendar();
           gc.setTime(periodBeg);
           gc.add(GregorianCalendar.DATE, -1);
           prevFp.endDate = gc.getTime();
       }
   }
   return fp;
}


private void calcBudgetDateRange(Connection conn, Map pParams) throws Exception {

  ReportingUtils.verifyDates(pParams);

  String begDateStr = (String) pParams.get(BEG_DATE_S);
  String endDateStr = (String) pParams.get(END_DATE_S);
  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
  Date begDate = sdf.parse(begDateStr);
  Date endDate = sdf.parse(endDateStr);
  begDateStr = sdf.format(begDate);
  endDateStr = sdf.format(endDate);
  GregorianCalendar gregCal = new GregorianCalendar();
  gregCal.setTime(endDate);
  gregCal.add(GregorianCalendar.DATE, 1);
  Date nextPeriodDate = gregCal.getTime();
  String nextPeriodDateStr = sdf.format(nextPeriodDate);


    IdVector accountIds = new IdVector();
    FiscalCalenderViewVector fiscalCalenders = new FiscalCalenderViewVector();

    String sql = " SELECT DISTINCT bus_entity2_id FROM clw_bus_entity_assoc  \n" +
            " WHERE  BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
            " AND bus_entity1_id IN (" + siteCrit + ")";

    PreparedStatement pstmt = conn.prepareStatement(sql);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
        accountIds.add(rs.getInt(1));
    }

    pstmt.close();
    rs.close();

    BusEntityDAO beDao = new BusEntityDAO();
    FiscalCalenderViewVector fiscalCalenderVV = beDao.getFiscalCalenderCollections(conn, accountIds, nextPeriodDate);

   SimpleDateFormat ySdf = new SimpleDateFormat("yyyy");
   Iterator it = fiscalCalenderVV.iterator();
   while (it.hasNext()){
       FiscalCalenderView fcV = (FiscalCalenderView) it.next();
       fiscalCalenders.add(0,fcV);
       if(fcV.getFiscalCalender().getEffDate().compareTo(begDate)<=0) {
           break;
       }
   }

   List<FiscalPeriod> periods = new LinkedList<FiscalPeriod>();
   int endFiscalYear = 0;
   Date endFiscalYearEffDate = null;
   FiscalPeriod prevPeriod = null;
   for(Iterator iter=fiscalCalenders.iterator(); iter.hasNext();) {
       FiscalCalenderView fcV = (FiscalCalenderView) iter.next();
       FiscalPeriod fp1 = null;
       FiscalPeriod fp2 = null;
       FiscalPeriod fp3 = null;
       FiscalPeriod fp4 = null;
       FiscalPeriod fp5 = null;
       FiscalPeriod fp6 = null;
       FiscalPeriod fp7 = null;
       FiscalPeriod fp8 = null;
       FiscalPeriod fp9 = null;
       FiscalPeriod fp10 = null;
       FiscalPeriod fp11 = null;
       FiscalPeriod fp12 = null;
       FiscalPeriod fp13 = null;

       int fiscalYear = fcV.getFiscalCalender().getFiscalYear();
       endFiscalYear = fiscalYear;
       Date effDate = fcV.getFiscalCalender().getEffDate();
       endFiscalYearEffDate = effDate;
       String periodYearS = ySdf.format(effDate);
       int periodYear = Integer.parseInt(periodYearS);

      fp1 = createFp (prevPeriod, fiscalYear,1, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 1), periodYear);
       if(fp1!=null) {
           periodYear = fp1.periodYear;
           prevPeriod = fp1;
           periods.add(fp1);
       } else {continue;}
       fp2 = createFp (fp1, fiscalYear,2, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 2), periodYear);
       if(fp2!=null) {
           periodYear = fp2.periodYear;
           prevPeriod = fp2;
           periods.add(fp2);
       } else {continue;}
       fp3 = createFp (fp2, fiscalYear,3, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 3), periodYear);
       if(fp3!=null) {
           periodYear = fp3.periodYear;
           prevPeriod = fp3;
           periods.add(fp3);
       } else {continue;}
       fp4 = createFp (fp3, fiscalYear,4, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 4), periodYear);
       if(fp4!=null) {
           periodYear = fp4.periodYear;
           prevPeriod = fp4;
           periods.add(fp4);
       } else {continue;}
       fp5 = createFp (fp4, fiscalYear,5, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 5), periodYear);
       if(fp5!=null) {
           periodYear = fp5.periodYear;
           prevPeriod = fp5;
           periods.add(fp5);
       } else {continue;}
       fp6 = createFp (fp5, fiscalYear,6, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 6), periodYear);
       if(fp6!=null) {
           periodYear = fp6.periodYear;
           prevPeriod = fp6;
           periods.add(fp6);
       } else {continue;}
       fp7 = createFp (fp6, fiscalYear,7, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 7), periodYear);
       if(fp7!=null) {
           periodYear = fp7.periodYear;
           prevPeriod = fp7;
           periods.add(fp7);
       } else {continue;}
       fp8 = createFp (fp7, fiscalYear,8, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 8), periodYear);
       if(fp8!=null) {
           periodYear = fp8.periodYear;
           prevPeriod = fp8;
           periods.add(fp8);
       } else {continue;}
       fp9 = createFp (fp8, fiscalYear,9, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 9), periodYear);
       if(fp9!=null) {
           periodYear = fp9.periodYear;
           prevPeriod = fp9;
           periods.add(fp9);
       } else {continue;}
       fp10 = createFp (fp9, fiscalYear,10, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 10), periodYear);
       if(fp10!=null) {
           periodYear = fp10.periodYear;
           prevPeriod = fp10;
           periods.add(fp10);
       } else {continue;}
       fp11 = createFp (fp10, fiscalYear,11, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 11), periodYear);
       if(fp11!=null) {
           periodYear = fp11.periodYear;
           prevPeriod = fp11;
           periods.add(fp11);
       } else {continue;}
       fp12 = createFp (fp11, fiscalYear,12, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 12), periodYear);
       if(fp12!=null) {
           periodYear = fp12.periodYear;
           prevPeriod = fp12;
           periods.add(fp12);
       } else {continue;}
       fp13 = createFp (fp12, fiscalYear,13, FiscalCalendarUtility.getMmdd(fcV.getFiscalCalenderDetails(), 13), periodYear);
       if(fp13!=null) {
           periodYear = fp13.periodYear;
           prevPeriod = fp13;
           periods.add(fp13);
       }
   }
   if (endFiscalYear==0) {
     throw new Exception("Attention! endFiscalYear = 0");
   }

   List<FiscalPeriod> requestPeriods = new LinkedList<FiscalPeriod>();
   List<FiscalPeriod> ytdPeriods = new LinkedList<FiscalPeriod>();
   
   if(endDate.before(endFiscalYearEffDate) ){
	   endFiscalYear=endFiscalYear-1;
   }
   
   boolean begFl = false;
   boolean endFl = false;
   for(Iterator iter=periods.iterator(); iter.hasNext();) {
       FiscalPeriod fp = (FiscalPeriod) iter.next();

	   if(fp.fiscalYear==endFiscalYear) {
           ytdPeriods.add(fp);
       }
       if(fp.begDate.equals(begDate)) {
           begFl = true;
       }
       if(begFl) {
           requestPeriods.add(fp);
       }
       if(fp.endDate!=null && fp.endDate.equals(endDate)) {
           endFl = true;
           break;
       }
   }

    if (!begFl && !endFl){
     throw new RemoteException("^clwKey^report.text.budgetDateRangeInvalid^clwKey^");
    }
    if (!begFl) {
      throw new RemoteException("^clwKey^report.text.beginBudgetDateInvalid^clwKey^");
    }
    if (!endFl) {
      throw new RemoteException("^clwKey^report.text.endBudgetDateInvalid^clwKey^");
    }
    pParams.put(BEGIN_YTD_YEAR_FP,ytdPeriods.get(0));
    pParams.put(END_YTD_YEAR_FP,ytdPeriods.get(ytdPeriods.size()-1));
    pParams.put(BEGIN_FP,requestPeriods.get(0));
    pParams.put(END_FP,requestPeriods.get(requestPeriods.size()-1));

}

private void validateBudgetDateRange(Connection conn, Map pParams) throws Exception {

  ReportingUtils.verifyDates(pParams);

  String begDateParam = (String) pParams.get(BEG_DATE_S);
  String endDateParam = (String) pParams.get(END_DATE_S);
  String endYearStr = (String) pParams.get(END_YEAR_S);
  String endMonthStr = (String) pParams.get(END_MONTH_S);

  String begDateStr = "";
  String endDateStr = "";
  String endYear ;
  String endMonth;
  if(begDateParam.length()==0 && endDateParam.length()==0){
    ArrayList dateRange = convertToDateStr( endMonthStr, endYearStr );
    begDateStr = (String)dateRange.get(0);
    endDateStr = (String)dateRange.get(1);
    endYear = endDateStr.substring(6,10);
    endMonth = endDateStr.substring(0,2);
  } else {
    begDateStr = begDateParam;
    endDateStr = endDateParam;
    try{
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	Date begDateDate = sdf.parse(begDateParam);
    	Date endDateDate = sdf.parse(endDateParam);
    	//convert them back to dates, but in a predictable
    	//format (i.e. 02/14/2009 not 2/14/2009).  This is
    	//used later on when comparing the dates to the fiscal
    	//dates as string comparison, not date comparison is used
    	begDateParam = sdf.format(begDateDate);
    	endDateParam = sdf.format(endDateDate);
    	Calendar cal = Calendar.getInstance();
    	endYear = Integer.toString(cal.get(Calendar.YEAR));
    	endMonth = Integer.toString(cal.get(Calendar.MONTH));
    }catch(ParseException e){
    	throw new RemoteException("^clwKey^report.text.budgetDateRangeInvalid^clwKey^");
    }
  }


//    String startYear = begDateStr.substring(6,10);
//    String startPeriod = begDateStr.substring(0, 5);
    //String endYear = endDateStr.substring(6,10);
    //String endMonth = endDateStr.substring(0,2);
//    String endPeriod = endDateStr.substring(0, 5);

    String dateCrit = null;
    if(begDateParam.length()==0 && endDateParam.length()==0){
      dateCrit =
         " to_char(END_DATE, 'mm') = '" + endMonth + "'  \n"  ;
    } else {
       dateCrit =
           "     START_DATE >= to_date('" + begDateStr +"','mm/dd/yyyy'  ) \n" +
           " AND END_DATE   <= to_date('" + endDateStr + "','mm/dd/yyyy'  ) \n";
    }
    String sql =
       "SELECT  max(budget_period), \n"+
       "        min(to_char(start_date,'mm/dd/yyyy')) START_PERIOD, \n" +
       "        max(to_char(end_date,'mm/dd/yyyy')) END_PERIOD  \n"  +
       "FROM ( \n"  +
       "   SELECT distinct budget_period,  \n"  +
       "   to_date(PERIOD_START_DATE||'/'|| \n"  +
       "      (case when (budget_period =1 and PERIOD_START_DATE > PERIOD_END_DATE) \n"  +
       "       then (BUDGET_YEAR-1) else BUDGET_YEAR end ), 'mm/dd/yyyy') START_DATE, \n"  +
       "   to_date(PERIOD_END_DATE||'/'|| \n"  +
       "      (case when (budget_period >= 12 and PERIOD_START_DATE > PERIOD_END_DATE) \n"  +
       "       then (BUDGET_YEAR+1) else BUDGET_YEAR end ), 'mm/dd/yyyy') END_DATE  \n"  +
       "   FROM TCLW_ACCTBUDGET_REPORT   \n"  +
       "   WHERE BUDGET_YEAR is not null AND BUDGET_YEAR >0  \n"  +
       "   AND BUDGET_YEAR in (" + endYear +  ") \n"  +
       ((Utility.isSet(siteCrit))? "  AND SITE_ID IN (" + siteCrit + ") \n" : "") +
       ") WHERE " + dateCrit;

   PreparedStatement pstmt = conn.prepareStatement(sql);

   dateRange = new ArrayList();
   ResultSet rs = pstmt.executeQuery();
   while (rs.next()){
      String begPeriodDate = (rs.getString("START_PERIOD") != null) ? rs.getString("START_PERIOD") : "";
      String endPeriodDate = (rs.getString("END_PERIOD") != null) ? rs.getString("END_PERIOD") : "";

      if (!Utility.isSet(begPeriodDate) || !Utility.isSet(endPeriodDate)){
        throw new RemoteException("^clwKey^report.text.budgetDateRangeInvalid^clwKey^");
      } else {
        if (Utility.isSet(begDateParam) &&
            Utility.isSet(begPeriodDate) && begPeriodDate.equals(begDateParam)){
          dateRange.add(begPeriodDate);
        } else if ( !Utility.isSet(begDateParam) && Utility.isSet(begPeriodDate) ) {
          dateRange.add(begPeriodDate);
        } else {
          throw new RemoteException("^clwKey^report.text.beginBudgetDateInvalid^clwKey^");
        }

        if (Utility.isSet(endDateParam) &&
            Utility.isSet(endPeriodDate) && endPeriodDate.equals(endDateParam)){
          dateRange.add(endPeriodDate);
        } else if (!Utility.isSet(endDateParam) && Utility.isSet(endPeriodDate)) {
          dateRange.add(endPeriodDate);
        } else {
          throw new RemoteException("^clwKey^report.text.endBudgetDateInvalid^clwKey^");
        }
      }
   }
   pstmt.close();
   rs.close();
}
//=======================================================================
 public class DetailVector extends java.util.ArrayList implements Comparator {
   /**
    * Constructor.
    */
   public DetailVector () {}

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
     BRecord obj1 = (BRecord)o1;
     BRecord obj2 = (BRecord)o2;

     if("Category".equalsIgnoreCase(_sortField)) {
      String i1 = obj1.getCategory()+"@"+obj1.getLocationName()+"@"+obj1.getCustPONum()+"@"+obj1.getItemNum();
      String i2 = obj2.getCategory()+"@"+obj2.getLocationName()+"@"+obj2.getCustPONum()+"@"+obj2.getItemNum();
      if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
      else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
    }

     if(!_ascFl) retcode = -retcode;
     return retcode;
   }
  }

//================================================================================
 public class SummaryVector extends java.util.ArrayList implements Comparator {
    /**
     * Constructor.
     */
    public SummaryVector () {}

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
      ARecord obj1 = (ARecord)o1;
      ARecord obj2 = (ARecord)o2;

      if("Category".equalsIgnoreCase(_sortField)) {
       String i1 = obj1.getCostCenterId()+ obj1.getCategory();// + obj1.getSubcategory();
       String i2 = obj2.getCostCenterId()+ obj2.getCategory();// + obj2.getSubcategory();
       if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
       else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
     }

      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
   }


// ---------------- Record for Sheet 1------------------------//
 public class ARecord //implements Record, java.io.Serializable
   {
     private int mCategoryId;
     private int mLevel;
     private int mCostCenterId;

     private String mItemNum;
     private String mItemName;
     private String mCategory;
     private String mSubcategory;
     private String mCategoryType;

     private String mCurrency;

     private BigDecimal mCategorySpend;
     private BigDecimal mSubcategorySpend;
     private BigDecimal mMonthBudget;
     private BigDecimal mMonthVar;

     private BigDecimal mYTDCategorySpend;
     private BigDecimal mYTDSubcategorySpend;
     private BigDecimal mYTDBudget;
     private BigDecimal mYTDVar;

     private BigDecimal mPercentOfTotal;
     private BigDecimal mPercentOfCategory;
     private BigDecimal mPercentOfMonthVar;
     private BigDecimal mPercentOfYTDTotal;
     private BigDecimal mPercentOfYTDCategory;
     private BigDecimal mPercentOfYTDVar;


     public void init(){
       mPercentOfTotal = new BigDecimal(0);
       mPercentOfCategory = new BigDecimal(0);
       mPercentOfMonthVar = new BigDecimal(0);
       mPercentOfYTDTotal = new BigDecimal(0);
       mPercentOfYTDCategory = new BigDecimal(0);
       mPercentOfYTDVar = new BigDecimal(0);

       mYTDCategorySpend = new BigDecimal(0);
       mYTDSubcategorySpend = new BigDecimal(0);
       mYTDBudget = new BigDecimal(0);
       mYTDVar = new BigDecimal(0);

       mCategorySpend = new BigDecimal(0);
       mSubcategorySpend = new BigDecimal(0);
       mMonthBudget = new BigDecimal(0);
       mMonthVar = new BigDecimal(0);

     }
     public ArrayList toList() {
         ArrayList list = new ArrayList();
         String budgetStyle = null;
         if (debug){
           list.add(mCostCenterId);
           list.add(new Integer(mLevel));
           list.add(mCategoryId);
           list.add(mCategory);
         }
         if(mLevel == 1) {
           list.add(getCellView(mCategory, "COLUMN_DATA_BOLD"));
           list.add(getCellView(mCategorySpend, "CURRENCY_BOLD_"+mCurrency));
           //if(Math.abs(mSubcategorySpend.doubleValue())>0.001) {
           //    list.add(getCellView(mSubcategorySpend, "CURRENCY_"+mCurrency)); // mSubcategorySpend
           //} else {
               list.add(""); // mSubcategorySpend
           //}
           list.add(mPercentOfTotal);
           //if(Math.abs(mSubcategorySpend.doubleValue())>0.001) {
           //    list.add(mPercentOfCategory); //mPercentOfCategory
           //}  else {
               list.add(""); //mPercentOfCategory
           //}

           list.add(getCellView(mMonthBudget, "CURRENCY_BOLD_"+mCurrency));
           budgetStyle = (mMonthVar != null && mMonthVar.signum() > 0 )? "CURRENCY_BOLD_" : "CURRENCY_IN_PARENTH_";
           list.add(getCellView(mMonthVar.abs(), budgetStyle + mCurrency));
           list.add(mPercentOfMonthVar);
           list.add(getCellView(mYTDCategorySpend, "CURRENCY_BOLD_"+mCurrency));
           //if(Math.abs(mYTDSubcategorySpend.doubleValue())>0.001) {
           //    list.add(getCellView(mYTDSubcategorySpend, "CURRENCY_"+mCurrency)); //mYTDSubcategorySpend
           //} else {
               list.add(""); //mYTDSubcategorySpend
           //}
           list.add(mPercentOfYTDTotal);
           //if(Math.abs(mYTDSubcategorySpend.doubleValue())>0.001) {
           //    list.add(mPercentOfYTDCategory); //mPercentOfYTDCategory
           //} else {
               list.add(""); //mPercentOfYTDCategory
           //}
           list.add(getCellView(mYTDBudget, "CURRENCY_BOLD_"+mCurrency));
           budgetStyle = (mYTDVar!= null && mYTDVar.signum() > 0 ) ? "CURRENCY_BOLD_" : "CURRENCY_IN_PARENTH_";
           list.add(getCellView(mYTDVar.abs(), budgetStyle + mCurrency));
           list.add(mPercentOfYTDVar);

         } else {
           list.add(getCellView( mSubcategory, "COLUMN_DATA_NORMAL"));
           list.add(""); //mCategorySpend
           list.add(getCellView(mSubcategorySpend, "CURRENCY_"+mCurrency));
           list.add(""); //mPercentOfTotal
           list.add(mPercentOfCategory);
           list.add(""); // mMonthBudget
           list.add(""); // mMonthVar
           list.add(""); // mPercentOfMonthVar
           list.add(""); // mYTDCategorySpend
           list.add(getCellView(mYTDSubcategorySpend, "CURRENCY_"+mCurrency));
           list.add(""); // mPercentOfYTDTotal
           list.add(mPercentOfYTDCategory);
           list.add(""); // mYTDBudget
           list.add(""); // mYTDVar
           list.add(""); // mPercentOfYTDVar

         }

         return list;
     }
     public void calculate ( double pTotalSum, double pYTDTotalSum, double pCategorySum, double pYTDCategorySum) {
        if (mLevel == 1) {
          mPercentOfTotal = calcPercent(mCategorySpend, new BigDecimal(pTotalSum));
          if (mCategorySpend != null && mMonthBudget != null) {
            mMonthVar = mCategorySpend.subtract(mMonthBudget);
          }
          mPercentOfMonthVar = calcPercent(mMonthVar, mMonthBudget);
          mPercentOfYTDTotal = calcPercent(mYTDCategorySpend, new BigDecimal(pYTDTotalSum));

          if (mYTDCategorySpend != null && mYTDBudget != null) {
            mYTDVar = mYTDCategorySpend.subtract(mYTDBudget);
          }
          mPercentOfYTDVar = calcPercent(mYTDVar, mYTDBudget);
        }
        else {
          mPercentOfCategory = calcPercent(mSubcategorySpend, new BigDecimal(pCategorySum));
          mPercentOfYTDCategory = calcPercent(mYTDSubcategorySpend, new BigDecimal(pYTDCategorySum));
        }

      }
      public void calculateTotal () {
         if (mCategorySpend != null && mMonthBudget != null) {
          mMonthVar = mCategorySpend.subtract(mMonthBudget);
        }
        mPercentOfMonthVar = calcPercent(mMonthVar, mMonthBudget);

         if (mYTDCategorySpend != null && mYTDBudget != null) {
          mYTDVar = mYTDCategorySpend.subtract(mYTDBudget);
        }
        mPercentOfYTDVar = calcPercent(mYTDVar, mYTDBudget);
      }

      public int getLevel() {
        return mLevel;
      }

      public void setLevel(int pLevel) {
        this.mLevel = pLevel;
      }

     public int getCostCenterId() {
       return mCostCenterId;
     }

     public void setCostCenterId(int pCostCenterId) {
       this.mCostCenterId = pCostCenterId;
     }

     public int getCategoryId() {
       return mCategoryId;
     }

     public void setCategoryId(int pCategoryId) {
       this.mCategoryId = pCategoryId;
     }
     public int getTopCategoryId() {
       return mCategoryId;
     }

     public void setTopCategoryId(int pCategoryId) {
       this.mCategoryId = pCategoryId;
     }

     public String getCategory() {
       return mCategory;
     }

     public void setCategory(String pCategory) {
       this.mCategory = pCategory;
     }
     public String getSubcategory() {
       return mSubcategory;
     }

     public void setSubcategory(String pSubcategory) {
       this.mSubcategory = pSubcategory;
     }

     public String getItemNum() {
       return mItemNum;
     }

     public void setItemNum(String pItemNum) {
       this.mItemNum = pItemNum;
     }
     public String getItemName() {
       return mItemName;
     }

     public void setItemName(String pItemName) {
       this.mItemName = pItemName;
     }
     public String getCurrency() {
       return mCurrency;
     }

     public void setCurrency(String pCurrency) {
       this.mCurrency = pCurrency;
     }



      public BigDecimal getCategorySpend() {
          return mCategorySpend;
      }
      public void setCategorySpend(BigDecimal pCategorySpend) {
          this.mCategorySpend = pCategorySpend;
      }
      public BigDecimal getSubcategorySpend() {
          return mSubcategorySpend;
      }
      public void setSubcategorySpend(BigDecimal pSubcategorySpend) {
          this.mSubcategorySpend = pSubcategorySpend;
      }
      public BigDecimal getMonthBudget() {
          return mMonthBudget;
      }
      public void setMonthBudget(BigDecimal pMonthBudget) {
          this.mMonthBudget = pMonthBudget;
      }

      public BigDecimal getMonthVar() {
          return mMonthVar;
      }
      public void setMonthVar(BigDecimal pMonthVar) {
          this.mMonthVar = pMonthVar;
      }
     public BigDecimal getYTDCategorySpend() {
       return mYTDCategorySpend;
     }
     public void setYTDCategorySpend(BigDecimal pYTDCategorySpend) {
       this.mYTDCategorySpend = pYTDCategorySpend;
     }
     public BigDecimal getYTDSubcategorySpend() {
       return mYTDSubcategorySpend;
     }
     public void setYTDSubcategorySpend(BigDecimal pYTDSubcategorySpend) {
       this.mYTDSubcategorySpend = pYTDSubcategorySpend;
     }
     public BigDecimal getYTDBudget() {
       return mYTDBudget;
     }
     public void setYTDBudget(BigDecimal pYTDBudget) {
       this.mYTDBudget = pYTDBudget;
     }
     public BigDecimal getYTDVar() {
       return mYTDVar;
     }
     public void setYTDVar(BigDecimal pYTDVar) {
       this.mYTDVar = pYTDVar;
     }

      public void setPercentOfTotal(BigDecimal pPercentOfTotal) {
          this.mPercentOfTotal = pPercentOfTotal;
      }

      public void setPercentOfCategory(BigDecimal pPercentOfCategory) {
          this.mPercentOfCategory = pPercentOfCategory;
      }
      public void setPercentOfMonthVar(BigDecimal pPercentOfMonthVar) {
          this.mPercentOfMonthVar = pPercentOfMonthVar;
      }
      public void setPercentOfYTDTotal(BigDecimal pPercentOfYTDTotal) {
          this.mPercentOfYTDTotal = pPercentOfYTDTotal;
      }
      public void setPercentOfYTDCategory(BigDecimal pPercentOfYTDCategory) {
          this.mPercentOfYTDCategory = pPercentOfYTDCategory;
      }
      public void setPercentOfYTDVar(BigDecimal pPercentOfYTDVar) {
          this.mPercentOfYTDVar = pPercentOfYTDVar;
      }

      //==========================================//
      public String getCategoryType() {
         return mCategoryType;
       }

       public void setCategoryType(String pCategoryType) {
         this.mCategoryType = pCategoryType;
       }



   }
   // Record for Sheet 2
   public class BRecord //implements Record, java.io.Serializable
   {
     private String mItemNum;
     private String mCategory;
     private String mSubcategory;
     private String mLocationName;
     private String mCity;
     private String mState;
     private Date mOrderDate;
     private String mCustPONum;
     private String mDesc;
     private String mUom;

     private String mCurrency;

     private Integer mQty;
     private BigDecimal mExtendedVal;

     public void init(){
       mQty = new Integer(0);
       mExtendedVal = new BigDecimal(0);
     }

     public ArrayList toList() {
       ArrayList list = new ArrayList();
       SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
       list.add(mLocationName);
       list.add(mCity);
       list.add(mState);
       list.add(mCustPONum);
       list.add(df.format(mOrderDate));
       list.add(mCategory);
       list.add(mSubcategory);
       list.add(mItemNum);
       list.add(mDesc);
       list.add(mQty);
       list.add(mUom);
       list.add(mExtendedVal);
       return list;
     }

     public String getCategory() {
        return mCategory;
      }

      public void setCategory(String pCategory) {
        this.mCategory = pCategory;
      }
      public String getSubcategory() {
         return mSubcategory;
       }

     public void setSubcategory(String pSubcategory) {
       this.mSubcategory = pSubcategory;
     }

    public String getLocationName() {
        return mLocationName;
    }
     public void setLocationName(String pLocationName) {
         this.mLocationName = pLocationName;
     }
     public String getCity() {
         return mCity;
     }
     public void setCity(String pCity) {
         this.mCity = pCity;
     }
     public String getState() {
         return mState;
     }
     public void setState(String pState) {
         this.mState = pState;
     }
     public Date getOrderDate() {
         return mOrderDate;
     }
     public void setOrderDate(Date pOrderDate) {
         this.mOrderDate = pOrderDate;
     }
     public String getCustPONum() {
         return mCustPONum;
     }
     public void setCustPONum(String pCustPONum) {
         this.mCustPONum = pCustPONum;
     }
     public String getDesc() {
         return mDesc;
     }
     public void setDesc(String pDesc) {
         this.mDesc = pDesc;
     }
     public String getUom() {
         return mUom;
     }
     public void setUom(String pUom) {
         this.mUom = pUom;
     }
     public Integer getQty() {
          return mQty;
      }

      public void setQty(Integer pQty) {
          this.mQty = pQty;
      }
      public BigDecimal getExtendedVal() {
          return mExtendedVal;
      }
      public void setExtendedVal(BigDecimal pExtendedVal) {
          this.mExtendedVal = pExtendedVal;
      }

      public String getItemNum() {
         return mItemNum;
       }

       public void setItemNum(String pItemNum) {
         this.mItemNum = pItemNum;
       }

       public String getCurrency() {
         return mCurrency;
       }

       public void setCurrency(String pCurrency) {
         this.mCurrency = pCurrency;
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

  private class CategorySummary {
    private String mKeyCat; //product category Id
    private int mLevel; //level of category
    private String mCategoryId;
    private BigDecimal mSumByCategory = null;
    private BigDecimal mYTDSumByCategory = null;
    private String mCategoryName = "";

    protected CategorySummary(String pCategoryId, int pLevel, String pCategoryName ){
      mCategoryId = pCategoryId;
      mLevel = pLevel;
      mCategoryName = pCategoryName;
    }


    public void sum(BigDecimal catValue, BigDecimal catYTDValue ) {
      if (mSumByCategory == null){
        mSumByCategory = new BigDecimal(0);
      }
      mSumByCategory = mSumByCategory.add(catValue);
      if (mYTDSumByCategory == null){
        mYTDSumByCategory = new BigDecimal(0);
      }
      mYTDSumByCategory = mYTDSumByCategory.add(catYTDValue);
    }

    public int getLevel() {
      return mLevel;
    }

    public void setLevel(int pLevel) {
      mLevel = pLevel;
    }

    public String getKeyCat() {
      return mKeyCat;
    }

    public void setKeyCat(String pKeyCat) {
      mKeyCat = pKeyCat;
    }
    public String getCategoryId() {
      return mCategoryId;
    }

    public void setCategoryId(String pCategoryId) {
      mCategoryId = pCategoryId;
    }
    public String getCategoryName() {
      return padString(mCategoryName, mLevel);
    }

    public void setCategoryName(String pCategoryName) {
      mCategoryName = pCategoryName;
    }
    public BigDecimal getSumByCategory() {
      return mSumByCategory;
    }

    public void setSumByCategory(BigDecimal pSumByCategory) {
      mSumByCategory = pSumByCategory;
    }
    public BigDecimal getYTDSumByCategory() {
      return mYTDSumByCategory;
    }

    public void setYTDSumByCategory(BigDecimal pYTDSumByCategory) {
      mYTDSumByCategory = pYTDSumByCategory;
    }

  }
  public class Category {
      int costCenterId;
      int subSubCategId;
      int subCategId;
      int topCategId;
      String subSubCategName;
      String subCategName;
      String topCategName;
      BigDecimal ytdBudget;
      BigDecimal budget;
      BigDecimal ytdSpend;
      BigDecimal spend;
      BigDecimal noSubSpend;
      BigDecimal ytdNoSubSpend;


  }
  public class FiscalPeriod {
      int fiscalYear;
      int periodYear;
      int periodNumber;
      Date begDate;
      Date endDate;
  }
}
