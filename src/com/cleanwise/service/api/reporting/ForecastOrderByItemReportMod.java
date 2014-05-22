/*
 * ForecastOrderByLocationReport.java
 *
 * Created on October 15, 2008, 4:43 PM
 */

package com.cleanwise.service.api.reporting;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.GenericReportCellView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.GenericReportStyleView;
import com.cleanwise.service.api.value.UserData;
import com.cleanwise.view.utils.Constants;


/**
 * Picks up orders and aggregates it on Surrplier
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 * @param pAccountId account Id optionally,
 */
public class ForecastOrderByItemReportMod  implements GenericReportMulti {

	private static final Logger log = Logger.getLogger(ForecastOrderByItemReportMod.class); // new statement
	
	String pattern = ".*[Gg]arment.*";
	Pattern p = Pattern.compile(pattern);
	
    /** Creates a new instance of PurchBySupplierJDReport */
    public ForecastOrderByItemReportMod() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */

    protected static final String SUPPLIER = "Supplier";

    protected static final String PRICE_COL = "inv.order_qty * inv.price";
    protected static final String QTY_COL   = "inv.qty_on_hand";
    protected static final String COUNT_COL = "inv.item_id";

    protected static final String CATEGORY = "Category";
    protected static final String ITEM_NUM = "Item #";
    protected static final String ITEM_DESC = "Item Description";
    protected static final String TOTAL_BLANK = "Locations w/Blank";
    protected static final String TOTAL_ZERO = "Locations w/Zero";
    protected static final String TOTAL_BLANK_OR_ZERO = "Total Locations (w/Zero and Blank)";
    protected static final String TOTAL_ENTRY = "Locations w/Entry";
///    protected static final String EXTENDED_PRICE = "Extended Price";
//    protected static final String EXTENDED_PRICE1 = "Extended Price Items w/Blank";
//    protected static final String EXTENDED_PRICE2 = "Extended Price Items w/Zero";
//    protected static final String EXTENDED_PRICE3 = "Extended Price Items (w/Zero and Blank)";
//    protected static final String EXTENDED_PRICE4 = "Extended Price Items w/Entry";

  ///  protected static final String TOTAL = "Total Locations";
    protected static final String PERCENT_OF_TOTAL = "% Location";
    
    protected static final String VALUE_SHIPPED = "Shipped Value";
    protected static final String QUANTITY_ORDERED = "Ordered Qty";
    protected static final String QUANTITY_ON_HAND = "On-hand Qty";
    protected static final String TOTAL_INVENTORY = "Total Inventory";
    protected static final String OVERALL_TOTAL_LOCATIONS = "Overall Total Locations";
    protected static final String AVERAGE_UNITS_PER_LOCATION = "Average Units Per Location";    
    
    
    protected static final String TBL_TEMP   = "CLT_FORECAST_REPORT";    

    protected static final String BEG_DATE_S = "BEG_DATE_OPT";
    protected static final String END_DATE_S = "END_DATE_OPT";
    protected static final String END_YEAR_S = "endYear_OPT";
    protected static final String END_MONTH_S = "endMonth_OPT";

    protected static final int EXTREM = 999999999;

    private String localCurrencySign = "$";
    ArrayList repCurrencyList = new ArrayList();

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
     Connection con = pCons.getDefaultConnection();

     ReportingUtils.verifyDates(pParams);

     GenericReportResultView result = GenericReportResultView.createValue();


     result.setTitle( getReportTitle(con, pReportData.getName(), pParams));
     result.setHeader(getReportHeader());
     result.setColumnCount(getReportHeader().size());
     result.setName(pReportData.getName()); // length - maximum 50 characters 
     result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
     result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
     result.setTable(new ArrayList());
     //====  Request for values ==== // 
     try {
       ArrayList repYList = getResultOfQuery(con,  pParams );
       
       Integer ovelallTotalLocations = getResultOfQueryOverallTotalLocations(con,  pParams );  //new
       
       GenericReportResultViewVector resultV = prepareReportData (result, repYList, ovelallTotalLocations);
       HashMap userStyles = createReportStyleDescriptor();
       result.setUserStyle(userStyles);
       return resultV;
     }
     catch (SQLException exc) {
       String errorMess = "Error. Report. Forecast Order By Item Report. SQL Exception happened. " +  exc.getMessage();
       logError(errorMess);
       exc.printStackTrace();
       throw new RemoteException(errorMess);
     }
     catch (Exception exc) {
       String errorMess = "Error. Report. Forecast Order By Item Report. Exception happened. " +  exc.getMessage();
       logError(errorMess);
       exc.printStackTrace();
       throw new RemoteException(errorMess);
     }

   }
   protected String getParamValue (Map pParams, String pControlName){
      String value = null;
      if (pParams.containsKey(pControlName) &&  Utility.isSet( (String) pParams.get(pControlName))) {
        value = (String) pParams.get(pControlName);
        log.info(" pParams.get(" + pControlName + ")::" + value);
      }
      return value;
    }

    protected GenericReportResultViewVector prepareReportData (GenericReportResultView result , ArrayList repList, Integer oTotLoc ){
          if (repList.size()!= 0){
            ArrayList tRepList = transform(repList, oTotLoc);
            for (Iterator iter1=tRepList.iterator(); iter1.hasNext();) {
              ForecastOrderSummaryDetail det = (ForecastOrderSummaryDetail) iter1.next();
              result.getTable().add(det.toList());
            }
          }
          GenericReportResultViewVector resultV = new GenericReportResultViewVector();
          resultV.add(result);
        return resultV;
    }


    protected HashMap createReportStyleDescriptor(){
     GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();

     /*
    (String parm1, String parm2, String parm3, String parm4, short parm5, String parm6, String parm7, String parm8, String parm9, int[] parm10)
    */
     int[] mergeRegion = new int[]{1,1,1,getReportHeader().size()};

     GenericReportStyleView pageTitle = new GenericReportStyleView(ReportingUtils.PAGE_TITLE,"TEXT",null,"Areal",10,"BOLD",null,null, "LEFT", false, null,null,null,0 );
     GenericReportStyleView colHeaderBlakOnWhite = new GenericReportStyleView("COLUMN_HEADER_BLACK_ON_WHITE","TEXT",null,"Times New Roman",-1,"BOLD","BLACK","WHITE","CENTER", false,null,null,null,0 );

     GenericReportStyleView colDataGreyInt = new GenericReportStyleView("COLUMN_DATA_GREY",ReportingUtils.DATA_CATEGORY.INTEGER,null,null,-1,null,null,"GREY_25_PERCENT",null, false,null,null,null,0 );
     GenericReportStyleView colDataGreyFloat = new GenericReportStyleView("COLUMN_DATA_GREY_FLOAT",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0.00",null,-1,null,null,"GREY_25_PERCENT",null, false,null ,null,null,0);
     GenericReportStyleView colDataGreyPerc = new GenericReportStyleView("COLUMN_DATA_GREY_PERCENT",ReportingUtils.DATA_CATEGORY.PERCENTAGE,null,null,-1,null,null,"GREY_25_PERCENT",null, false,null ,null,null,0);

     GenericReportStyleView colHeaderGrey = new GenericReportStyleView("COLUMN_HEADER_GREY","TEXT",null,null,-1,"BOLD",null,"GREY_25_PERCENT","LEFT",true, null ,null,null,0);
     GenericReportStyleView colHeader = new GenericReportStyleView("COLUMN_HEADER_WRAP","TEXT",null,null,-1,"BOLD",null,null,"LEFT",true, null,null,null,0 );

     GenericReportStyleView colDataCenter = new GenericReportStyleView("COLUMN_DATA_CENTER",null,null,null,-1,null,null,null,"CENTER",false, null,null,null,0 );
     GenericReportStyleView colDataPerc = new GenericReportStyleView("COLUMN_DATA_PERCENT",ReportingUtils.DATA_CATEGORY.PERCENTAGE,null,null,-1,null,null,null,null, false,null,null,null,0 );

     GenericReportStyleView colDataGreyIntSepStyle = new GenericReportStyleView("COLUMN_DATA_GREY_SEP_STYLE",ReportingUtils.DATA_CATEGORY.INTEGER,"#,##0",null,-1,null,null,"GREY_25_PERCENT",null, false,null,null,null,0 );
     GenericReportStyleView colDataIntSepStyle = new GenericReportStyleView("COLUMN_DATA_SEP_STYLE",ReportingUtils.DATA_CATEGORY.INTEGER,"#,##0",null,-1,null,null,null,null, false,null,null,null,0 );
     
     if (repCurrencyList.size() == 0){
       repCurrencyList.add(localCurrencySign);
     }
     GenericReportStyleView[] colDataGreyCurrency = new GenericReportStyleView[repCurrencyList.size()];
     GenericReportStyleView[] colDataCurrency = new GenericReportStyleView[repCurrencyList.size()];
     for (int i = 0; i < repCurrencyList.size(); i++) {
        String currency = (String)repCurrencyList.get(i);
        colDataGreyCurrency[i] = new GenericReportStyleView("COLUMN_DATA_GREY_"+currency, ReportingUtils.DATA_CATEGORY.FLOAT,currency+"#,##0.00",null,-1,null,null,"GREY_25_PERCENT",null, false,null,null,null,0 );
        colDataCurrency[i] = new GenericReportStyleView("COLUMN_DATA_"+currency, ReportingUtils.DATA_CATEGORY.FLOAT,currency+"#,##0.00",null,-1,null,null,null,null, false,null,null,null,0 );
     }

     reportDesc.setUserStyle(pageTitle.getStyleName(), pageTitle);
     reportDesc.setUserStyle(colHeaderBlakOnWhite.getStyleName(), colHeaderBlakOnWhite);
     reportDesc.setUserStyle(colDataGreyInt.getStyleName(), colDataGreyInt);
     reportDesc.setUserStyle(colDataGreyFloat.getStyleName(), colDataGreyFloat);
     reportDesc.setUserStyle(colDataGreyPerc.getStyleName(),colDataGreyPerc);
     reportDesc.setUserStyle(colHeader.getStyleName(), colHeader);
     reportDesc.setUserStyle(colHeaderGrey.getStyleName(), colHeaderGrey);
     reportDesc.setUserStyle(colDataCenter.getStyleName(), colDataCenter);
     reportDesc.setUserStyle(colDataPerc.getStyleName(),colDataPerc);
     
     reportDesc.setUserStyle(colDataGreyIntSepStyle.getStyleName(), colDataGreyIntSepStyle);
     reportDesc.setUserStyle(colDataIntSepStyle.getStyleName(), colDataIntSepStyle);

     for (int i = 0; i < repCurrencyList.size(); i++) {
       reportDesc.setUserStyle(colDataGreyCurrency[i].getStyleName(), colDataGreyCurrency[i]);
       reportDesc.setUserStyle(colDataCurrency[i].getStyleName(), colDataCurrency[i]);
     }

     HashMap styleDesc = reportDesc.getGenericReportUserStyleDesc();
     return styleDesc;
   }


    protected GenericReportColumnViewVector getReportHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView(this.CATEGORY, "COLUMN_HEADER_WRAP", null, "15"));
        header.add(ReportingUtils.createGenericReportColumnView(this.ITEM_NUM, "COLUMN_HEADER_WRAP", null, "10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.ITEM_DESC, "COLUMN_HEADER_WRAP", "COLUMN_DATA_CENTER", "20"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_ZERO, "COLUMN_HEADER_WRAP", ReportingUtils.INTEGER_STYLE, "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_TOTAL, "COLUMN_HEADER_WRAP", "COLUMN_DATA_PERCENT", "8"));
///        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE, "COLUMN_HEADER_WRAP", "COLUMN_DATA_$", "10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_BLANK, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY", "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_TOTAL, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_PERCENT", "8"));
///        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_$", "10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_BLANK_OR_ZERO, "COLUMN_HEADER_WRAP", ReportingUtils.INTEGER_STYLE, "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_TOTAL, "COLUMN_HEADER_WRAP", "COLUMN_DATA_PERCENT", "8"));
///        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE,"COLUMN_HEADER_WRAP", "COLUMN_DATA_$", "10"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_ENTRY, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY", "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.PERCENT_OF_TOTAL, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_PERCENT", "8"));
///        header.add(ReportingUtils.createGenericReportColumnView(this.EXTENDED_PRICE, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_$", "10"));
///        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL, "COLUMN_HEADER_WRAP", ReportingUtils.INTEGER_STYLE, "8"));
        
        header.add(ReportingUtils.createGenericReportColumnView(this.VALUE_SHIPPED, "COLUMN_HEADER_WRAP", "COLUMN_DATA_$", "10"));
        //header.add(ReportingUtils.createGenericReportColumnView(this.QUANTITY_ORDERED, "COLUMN_HEADER_GREY", ReportingUtils.INTEGER_STYLE, "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.QUANTITY_ORDERED, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_SEP_STYLE", "8"));
        //header.add(ReportingUtils.createGenericReportColumnView(this.QUANTITY_ON_HAND, "COLUMN_HEADER_GREY", ReportingUtils.INTEGER_STYLE, "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.QUANTITY_ON_HAND, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY_SEP_STYLE", "8"));
        //header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_INVENTORY, "COLUMN_HEADER_WRAP", ReportingUtils.INTEGER_SEPARATOR_STYLE, "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.TOTAL_INVENTORY, "COLUMN_HEADER_WRAP", "COLUMN_DATA_SEP_STYLE", "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.OVERALL_TOTAL_LOCATIONS, "COLUMN_HEADER_WRAP", ReportingUtils.INTEGER_STYLE, "8"));
        //header.add(ReportingUtils.createGenericReportColumnView(this.OVERALL_TOTAL_LOCATIONS, "COLUMN_HEADER_WRAP", "COLUMN_DATA_SEP_STYLE", "8"));
        //header.add(ReportingUtils.createGenericReportColumnView(this.AVERAGE_UNITS_PER_LOCATION, "COLUMN_HEADER_GREY", ReportingUtils.INTEGER_STYLE, "8"));
        header.add(ReportingUtils.createGenericReportColumnView(this.AVERAGE_UNITS_PER_LOCATION, "COLUMN_HEADER_GREY", "COLUMN_DATA_GREY", "8"));
        return header;
    }

    protected ArrayList getResultOfQuery(Connection conn, Map pParams) throws Exception{
      BigDecimal zeroAmt = new BigDecimal(0);
      Integer zeroNum = new Integer(0);

      String userIdStr = (String) pParams.get("CUSTOMER");
      String storeIdStr = (String) pParams.get("STORE_SELECT");
      int storeId = Integer.parseInt(storeIdStr);

      String siteCritSql = getUserSites(conn, userIdStr, storeIdStr);

      log.info("siteCritSql = " + siteCritSql);
      
      //String tmpTab = fillTmpTab (conn, storeId, siteCritSql);
      String orderSummarySql= getQuerySql(siteCritSql, pParams);
      
      log.info("orderSummarySql = " + orderSummarySql);

      String subSql = getQuerySql(orderSummarySql);
      
      log.info("subSql = " + subSql);

      String selectSql =
          "select \n" +
          "  (select short_desc from clw_item where item_id = sub.item_id) ITEM_DESC , \n" +
          "  CATEGORY, \n" +
          "   (select local_code from CLW_CURRENCY where locale = sub.locale_cd) CURRENCY, \n" +
          "   CATEGORY AS category1, \n" +
          "   ITEM_ID, \n" +
          "   SUM_TYPE, \n" +
          "   ITEM_NUM, \n" +
          "   LOCALE_CD, \n" +
          "   DIST_ITEM_NUM, \n" +
          
          "   forecast, \n" +
          "   extended_price, \n" +
          
          "   shipped_value, \n" +
          "   ordered_qty, \n" +
          "   qty_on_hand \n" +
          
          " from (\n" +
             "SELECT CATEGORY,   \n"+
            "       ITEM_ID,   \n"+
            "       SUM_TYPE , \n" +
            "       max(item_num) ITEM_NUM,   \n"+
            "       max(locale_cd) LOCALE_CD, \n"+
            
            "       max(dist_item_num)  DIST_ITEM_NUM, \n"+ 
            
            "       count(site_id ) forecast, \n" +
            "       sum (extended_price) extended_price, \n" +
            
            "       sum(shipped_value) shipped_value, \n" +
            "       sum(ordered_qty) ordered_qty, \n" +
            "       sum(qty_on_hand) qty_on_hand \n" +
            
            "  FROM   \n" +
         "  (" +
          subSql +
          "  ) sub1 " +
          "  GROUP BY CATEGORY, ITEM_ID, SUM_TYPE \n" +
          "  ) sub " +
          " order by category, item_num \n";

      PreparedStatement pstmt = conn.prepareStatement(selectSql);

      log.info(getClass().getName() + " -------> SQL: \n" + selectSql);
      log.info("getResultOfQuery() -------> Execute Query started: <"+logTime()+">");
      ResultSet rs = pstmt.executeQuery();
      log.info("getResultOfQuery() -------> Execute Query finished: <"+logTime()+">");
      //ArrayList psViewV= new ArrayList();
      ForecastOrderSummaryVector psViewV = new ForecastOrderSummaryVector();
      Map distItemNums = new HashMap();
      Map<String, String> itemToItemNumMap = new HashMap<String, String>();
      Map<String, String> itemToCurrencyMap = new  HashMap<String, String>();
      String defCurrency = "";
      while (rs.next()){
          ForecastOrderSummaryDetail record = new ForecastOrderSummaryDetail();
          String itemId = (rs.getString("ITEM_ID") != null) ? rs.getString("ITEM_ID") : "";
          String category  = (rs.getString("CATEGORY") != null) ? rs.getString("CATEGORY") : "";

          String itemDesc = (rs.getString("ITEM_DESC") != null) ? rs.getString("ITEM_DESC") : "";
          String itemNum = (rs.getString("ITEM_NUM") != null) ? rs.getString("ITEM_NUM") : "";
          String currency = (rs.getString("CURRENCY") != null) ? rs.getString("CURRENCY") : "";
          
          String distItemNum = (rs.getString("DIST_ITEM_NUM") != null) ? rs.getString("DIST_ITEM_NUM") : "";
         // if (Utility.isSet(distItemNum)) {
        //	  distItemNums.put(itemId, distItemNum);
         // }
          if (!Utility.isSet(itemNum)){
        	  itemToItemNumMap.put(itemId, distItemNum);
          }
          if (!Utility.isSet(currency)){
        	  itemToCurrencyMap.put(itemId, "");
          } else {
        	  defCurrency = currency;
          }
          
          Integer forecast = (Utility.isSet(rs.getString("FORECAST"))) ? new Integer(rs.getString("FORECAST")) : zeroNum ;
          BigDecimal extendedPrice = (rs.getBigDecimal("EXTENDED_PRICE") != null) ? rs.getBigDecimal("EXTENDED_PRICE") : zeroAmt;
          String sumType = rs.getString("SUM_TYPE");
          
          BigDecimal shippedValue = (rs.getBigDecimal("SHIPPED_VALUE") != null) ? rs.getBigDecimal("SHIPPED_VALUE") : zeroAmt;
          Integer orderedQty = (Utility.isSet(rs.getString("ORDERED_QTY"))) ? new Integer(rs.getString("ORDERED_QTY")) : zeroNum ;
          Integer qtyOnHand = (Utility.isSet(rs.getString("QTY_ON_HAND"))) ? new Integer(rs.getString("QTY_ON_HAND")) : zeroNum ;
          
          record.setItemId(itemId);
          record.setCategory(category);
          record.setItemNum(itemNum);
          record.setItemName(itemDesc);
          record.setCurrency(currency);
          record.setForecast(forecast);
          record.mExtendedPrice = extendedPrice;
          record.setForecastType(sumType);
          
          record.setShippedValue(shippedValue);
          record.setOrderedQty(orderedQty);
          record.setQtyOnHand(qtyOnHand);
          
          psViewV.add(record);
      }
      pstmt.close();
      rs.close();
      
      if (Utility.isSet(itemToCurrencyMap) && !Utility.isSet(defCurrency)){
    	  defCurrency = getCurrency(conn, itemToCurrencyMap,storeIdStr);
      }

      if (Utility.isSet(itemToItemNumMap)){
    	  populateItemNum(conn, itemToItemNumMap);
      }
      if (Utility.isSet(itemToItemNumMap) && Utility.isSet(itemToCurrencyMap)){
	      for (Object rec : psViewV) {
	    	  if (!Utility.isSet( ((ForecastOrderSummaryDetail)rec).getCurrency())){
	    		  ((ForecastOrderSummaryDetail)rec).setCurrency(defCurrency); 
	    	  }
	    	  if (!Utility.isSet( ((ForecastOrderSummaryDetail)rec).getItemNum())) {
	    		  String itemNum = (String)itemToItemNumMap.get(((ForecastOrderSummaryDetail)rec).getItemId());
	    		  ((ForecastOrderSummaryDetail)rec).setItemNum(itemNum); 
	    	  }
	      }
	      psViewV.sort("itemNum"); 
      }
      return psViewV;

    }
    
    private void populateItemNum(Connection conn, Map itemToItemNumMap ) throws Exception {

     	String itemList = Utility.toCommaSting(itemToItemNumMap.keySet());
        log.info("populateItemNum() --->  itemList="+ itemList);
 
        String sql =
            "SELECT max(customer_sku_num) ITEM_NUM , cs.item_id \n" +
            "FROM  \n" +
            " clw_catalog_structure cs , \n" +
            " clw_catalog_assoc aca, \n" +
            "  clw_catalog c  \n" +
              
            "WHERE  cs.catalog_id = aca.CATALOG_ID  \n" + 
            "and C.CATALOG_ID = cs.catalog_id  \n" +
            "and C.CATALOG_TYPE_CD ='"+ RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+ "' \n" +
            " AND cs.item_id in ( "+ itemList +")" + 
            " group by item_id";
        	
        log.info("populateItemNum() --->  sql =" + sql );

        PreparedStatement pstmt = conn.prepareStatement(sql);

        log.info("populateItemNum() ---> Execute Query started: <"+logTime()+">");
        ResultSet rs = pstmt.executeQuery();
        log.info("populateItemNum() ---> Execute Query started: <"+logTime()+">");
        
        while (rs.next()){
            String itemNum = (rs.getString("ITEM_NUM") != null) ? rs.getString("ITEM_NUM") : "";
            int itemId  = (rs.getInt("item_id") != 0) ? rs.getInt("item_id") : 0;
            itemToItemNumMap.put(""+itemId, itemNum);
        }
        pstmt.close();
        rs.close();
    }
    
    private String getCurrency(Connection conn, Map itemToCurrencyMap, String storeId) throws Exception {
  
          
        String subSql = " select ACC.BUS_ENTITY1_ID site_id \n" +
          "from \n" +
         "  clw_bus_entity_assoc store,\n" +
         "  clw_bus_entity_assoc acc\n" +
         "where \n" +
           "store.bus_entity2_id = "+ storeId + " \n" +
           "and STORE.BUS_ENTITY1_ID = ACC.BUS_ENTITY2_ID \n" +
           "and STORE.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+ "' \n" +
           "and ACC.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+ "' \n" +
           "and rownum = 1" ;
    	
        	
        log.info("populateCurrency() --->  subSql =" + subSql );
        PreparedStatement pstmt = conn.prepareStatement(subSql);
        ResultSet rs = pstmt.executeQuery();
        int siteId = 0;
        while (rs.next()){
             siteId  = (rs.getInt("site_id") != 0) ? rs.getInt("site_id") : 0;
        }
        pstmt.close();
        rs.close();
        
       	String sql = "select  local_code currency \n"+         
        " from  \n"+
	    "    clw_contract ct, \n"+ 
	    "    clw_catalog_assoc sca, \n"+
	    "    CLW_CURRENCY cur \n"+
        " where 1=1 \n"+
        " and ct.catalog_id = sca.CATALOG_ID  \n"+
        " and  CUR.LOCALE =  CT.LOCALE_CD  \n"+
        " and  SCA.CATALOG_ASSOC_CD = '"+ RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE+ "' \n"+
        " and sca.bus_entity_id = " + siteId ;
        log.info("populateCurrency() --->  sql =" + sql );
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        String currency = "";
        while (rs.next()){
        	currency  = (rs.getString("currency") != null) ? rs.getString("currency") : "";
        }
        pstmt.close();
        rs.close();
        return currency;
    }
    
    protected Integer getResultOfQueryOverallTotalLocations(Connection conn, Map pParams) throws Exception{ // new
        BigDecimal zeroAmt = new BigDecimal(0);
        Integer zeroNum = new Integer(0);

        String userIdStr = (String) pParams.get("CUSTOMER");
        String storeIdStr = (String) pParams.get("STORE_SELECT");
   
        String siteCritSql = getUserSites(conn, userIdStr, storeIdStr);

        log.info("siteCritSql = " + siteCritSql);
        
        String overallTotalLocationsSql = getSubQuerySql(siteCritSql, pParams);        

        String selectOverallTotalLocationsSql =
        	"select \n" +
        	" count(distinct bus_entity_id) overall_total_locations\n" +
        	" from clw_inventory_order_qty inv \n" +
        	" where inv.item_type = 'Inventory' \n" +
        	"   AND inv.item_id >0 \n" +
       	    "   AND inv.par is not null \n" +
       overallTotalLocationsSql;
        	


        PreparedStatement pstmt = conn.prepareStatement(selectOverallTotalLocationsSql);

        log.info(getClass().getName() + " -------> selectOverallTotalLocationsSql: " + selectOverallTotalLocationsSql);
        ResultSet rs = pstmt.executeQuery();
        Integer overallTotalLocations = new Integer(0);
        while (rs.next()){
            
            overallTotalLocations = (Utility.isSet(rs.getString("overall_total_locations"))) ? new Integer(rs.getString("overall_total_locations")) : zeroNum ;
            
        }
        pstmt.close();
        rs.close();

        return overallTotalLocations;

      }

    protected String getQuerySql(String subSelect) {
       String sql =
            "SELECT g.CATEGORY,   \n"+
            "       g.ITEM_ID,   \n"+
            "       g.SUM_TYPE, \n" +
            "       max(g.item_num) ITEM_NUM,   \n"+
            "       max(g.locale_cd) LOCALE_CD, \n"+
            
            "       max(g.dist_item_num) DIST_ITEM_NUM, \n"+ 

            "       g.site_id, \n" +
            "       sum (g.extended_price) extended_price, \n" +
            
            "       sum(g.shipped_value) shipped_value, \n" +
            "       sum(g.ordered_qty) ordered_qty, \n" +
            "       sum(g.qty_on_hand) qty_on_hand \n" +
            
            "  FROM (  \n" +
         subSelect +
            "     ) g \n" +
            "  GROUP BY SITE_ID, CATEGORY, ITEM_ID, SUM_TYPE \n" ;
       return sql;
    }

    protected String getQuerySql( String pSiteCrit, Map pParams) throws Exception {
      //=========== getting parameters =======================================

       String begDateStr = (String) pParams.get(BEG_DATE_S);
       String endDateStr = (String) pParams.get(END_DATE_S);
       log.info(" FFFFFFFFFFF begDateStr=" + begDateStr + ", endDateStr=" + endDateStr);
       String endYearStr = (String) pParams.get(END_YEAR_S);
       String endMonthStr = (String) pParams.get(END_MONTH_S);
       if(begDateStr.length()==0 && endDateStr.length()==0){
         ArrayList dateRange = convertToDateStr( endMonthStr, endYearStr );
         begDateStr = (String)dateRange.get(0);
         endDateStr = (String)dateRange.get(1);
         log.info(" FFFFFFFFFFF begDateStr=" + begDateStr + ", endDateStr=" + endDateStr);
       }
       //======================================================================//

      GregorianCalendar currBegCalendar = new GregorianCalendar();
      currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

      GregorianCalendar currEndCalendar = new GregorianCalendar();
      currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

      String startDateS = ReportingUtils.toSQLDate(currBegCalendar.getTime());
      String endDateS =   ReportingUtils.toSQLDate(currEndCalendar.getTime());


      String filterCrit =
          "  AND inv.add_date BETWEEN " + startDateS + " \n" + " AND " + endDateS + " + 1 \n" +
//        ((Utility.isSet(pSiteCrit))? "  AND inv.bus_entity_id IN (" + pSiteCrit + ") \n" : "") ;
          ((Utility.isSet(pSiteCrit))? "  AND exists (" + pSiteCrit + ") \n" : "") ;
      String storeIdStr = (String) pParams.get("STORE_SELECT");
      int storeId = Integer.parseInt(storeIdStr);
 
      
      String sql=
            "SELECT inv.COST_CENTER as CATEGORY, \n" +
            
            "        o.order_id, \n" +
            
            "        inv.item_id ITEM_ID, \n" +
            "        inv.BUS_ENTITY_ID SITE_ID , \n" +
            
            "        o.locale_cd LOCALE_CD,  \n" +
            "        o.cust_item_sku_num ITEM_NUM, \n" + 
            "        inv.dist_item_num, \n" +  
/*            
            "         NVL(o.locale_cd, (\n" +
//            "             select locale_cd from clw_contract ct  \n" +
//            "               where ct.catalog_id = ca.SITE_CATALOG_ID ) \n" +
            "             select locale_cd from clw_contract ct, clw_catalog_assoc sca \n" +
            "               WHERE ct.catalog_id = sca.CATALOG_ID and sca.bus_entity_Id = acc.bus_entity1_id " +
            "                 and SCA.CATALOG_ASSOC_CD = '" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE+ "') \n" +
            "            ) LOCALE_CD, \n" +
            "        NVL (o.cust_item_sku_num, \n" +
            "           NVL (( \n" +
//            "  				SELECT customer_sku_num \n" +
//            "                 FROM clw_catalog_structure cs  \n" +
//            "                 WHERE cs.catalog_id = o.ACCT_CATALOG_ID \n" +
//            "                   AND o.site_id = inv.bus_entity_id \n" +
//            "                   AND cs.item_id = inv.item_id " +
            "     		  SELECT customer_sku_num  FROM clw_catalog_structure cs , clw_catalog_assoc aca, clw_catalog c WHERE  cs.catalog_id = aca.CATALOG_ID  and aca.bus_entity_Id = acc.bus_entity2_id \n" +
            "              and C.CATALOG_ID = cs.catalog_id and C.CATALOG_TYPE_CD ='"+RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+ "' AND cs.item_id = inv.item_id  \n" +      
            "             ), \n" +
            "                inv.dist_item_num \n" +
            "               ) \n" +
            "            ) ITEM_NUM, \n" +
*/            
            "        inv.qty_on_hand AS qty_on_hand, \n" +
            "       (inv.order_qty * NVL (inv.price, inv.price)) AS extended_price, \n" +
            
            "       (inv.order_qty * NVL (inv.price, inv.price)) AS shipped_value, \n" +
            "       inv.order_qty AS ordered_qty, \n" +
            
            "       (CASE WHEN inv.qty_on_hand is null THEN 'Blank'   \n" +
            "              WHEN inv.qty_on_hand =0 THEN 'Zero' \n" +
            "              WHEN inv.qty_on_hand >0 THEN 'Entry' \n" +
            "         END ) SUM_TYPE \n" +
            "FROM   \n" +
            "    (SELECT    o.site_id, oi.item_id, o.locale_cd, \n" +
            "               TRUNC (o.original_order_date) AS ord_add_date,  \n" +
            "               oi.cust_item_sku_num,  \n" +
            "               oi.cust_contract_price AS price, \n" +
            "               o.order_id \n" +
            "       FROM clw_order_item oi, clw_order o \n" +
            "       WHERE o.order_status_cd NOT IN \n" +
            "            ('Cancelled', \n" +
            "            'ERP Rejected', \n" +
            "            'Rejected', \n" +
            "            'Duplicate', \n" +
            "            'REFERENCE_ONLY', \n" +
            "            'Received' \n" +
            "            ) \n" +
            "         AND o.order_id = oi.order_id \n" +
            "         AND o.order_source_cd = 'Inventory' \n" +
            "        AND TRUNC (o.original_order_date) BETWEEN \n" +
                     startDateS + " \n" + " AND " + endDateS + " + 1 \n" +
            " ) o, \n" +
            " clw_inventory_order_qty inv, \n" +
            " clw_bus_entity_assoc acc, \n" +
            " clw_bus_entity_assoc store, \n" +            
//            tmpTab + " ca, \n" + 
            " clw_item i \n" +
            "WHERE 1=1 \n" +
            " and store.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE +"' \n" +
            " and STORE.BUS_ENTITY2_ID = " + storeId +
            " and STORE.BUS_ENTITY1_ID = ACC.BUS_ENTITY2_ID \n" +
            " and ACC.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n" +
            " and ACC.BUS_ENTITY1_ID = inv.BUS_ENTITY_ID \n" +
            
//            "     inv.BUS_ENTITY_ID = ca.site_id    \n" +
            " AND inv.bus_entity_id = o.site_id(+)  \n" +
            " AND inv.item_id = o.item_id(+) \n" +
            " AND inv.order_id = o.order_id(+)  \n"  +
            " AND inv.item_id = i.item_id \n" +
            " AND lower(i.short_desc) like '%forecasted%' \n" +
            " AND inv.item_type = 'Inventory' \n" +
            " AND inv.item_id >0 \n" +
            filterCrit ;

        return sql;
    }
    
/*    private String fillTmpTab (Connection connection, int storeId, String pSiteCrit) throws Exception {
        String tempSuffix = ""; 
        String tempUser = ""; //System.getProperty("appcmd.tempUser");

    	String tblName = Utility.getFullTableName(tempUser, TBL_TEMP, tempSuffix);
        String sql = null;
        ResultSet resSet = null;
        PreparedStatement stmt = null;
        boolean isExistTempTable = ReportingUtils.checkTableOnExistence(connection, null, tblName);
        
        /// Deleting of already existing  temporary table 
        if (isExistTempTable) {
     	   ReportingUtils.execClearTbl(connection, tblName);
        } else {
 	       /// Creation of the temporary table 
 	       try {
 	           sql ="create table " + tblName + " ( "+
 				     "site_id NUMBER NOT NULL, "+
 				     "acct_catalog_id NUMBER NOT NULL, "+
 				     "site_catalog_id NUMBER NOT NULL "+
 				     ") ";
 		        stmt = connection.prepareStatement(sql);
 		        stmt.executeUpdate();
 		        stmt.close();
 		        log.info("fillTmpTab ==> Table "+ tblName+ " created.");
 		        /// indexes on the temporary table
 		       ReportingUtils.createIndex(connection, tblName, "I1" , "acct_catalog_id" ) ;
 		       ReportingUtils.createIndex(connection, tblName, "I2" , "site_catalog_id" ) ;
 	
 		    } catch (SQLException ex) {
 		        String msg = "An error occurred at creation of table " +
 		        	tblName + ". " + ex.getMessage()+
 		             "*** Execute the following request to get more information : " +  sql;
 		        throw new SQLException("^clw^"+msg+ "^clw^");
 		    }
        } 
 	    /// Population of temporary table
        try {
            sql = "INSERT INTO "+ tblName + " (" +
            "select \n"+
            "CBEA.BUS_ENTITY1_ID site_id, \n"+
            "ac.catalog_id acct_catalog_id, sc.catalog_id site_catalog_id   \n"+
            "from \n"+
            "       clw_bus_entity_assoc cbea, \n"+
            "       clw_bus_entity_assoc cbea1, \n"+
            "       clw_catalog ac, \n"+
            "       clw_catalog_assoc aca, \n"+
            "       clw_catalog_assoc sca, \n"+
            "       clw_catalog sc  \n"+
           "where 1=1 \n"+
           "and CBEA1.BUS_ENTITY2_ID = "+ storeId +" \n"+
           "and CBEA1.BUS_ENTITY1_ID = CBEA.BUS_ENTITY2_ID \n"+
           "and CBEA.BUS_ENTITY_ASSOC_CD = '"+RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT+"' \n"+
           "and cbea1.bus_entity_assoc_cd ='"+RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE+"'  \n"+
           "and CBEA.BUS_ENTITY2_ID = aca.bus_entity_id and aca.catalog_id = ac.catalog_id \n"+
           "and CBEA.BUS_ENTITY1_ID = sca.bus_entity_id and sca.catalog_id = sc.catalog_id  \n"+
           "and ac.catalog_type_cd = '" +RefCodeNames.CATALOG_TYPE_CD.ACCOUNT+ "' and sc.catalog_type_cd = '"+ RefCodeNames.CATALOG_TYPE_CD.SHOPPING +"' \n" +        
            ((Utility.isSet(pSiteCrit))? "  AND inv.bus_entity_id IN (" + pSiteCrit + ") \n" : "") ;
             
 	       log.info("fillTmpTab() ==> SQL: "+ sql);
            stmt = connection.prepareStatement(sql);
            int inserted = stmt.executeUpdate();
            stmt.close();
 	       log.info("fillTmpTab() ==> Table "+ tblName+ ": Inserted " + inserted + " rows.");
           	
        } catch  (SQLException ex) {
            String msg = "An error occurred at insertion of records into table " +
            	tblName + ". " + ex.getMessage()+
            	"*** Execute the following request to get more information : " +  sql;
            throw new SQLException("^clw^"+msg+ "^clw^");       	
        }
    	return tblName;
    }
*/

    protected String getSubQuerySql(String pSiteCrit, Map pParams) throws Exception {
        //=========== getting parameters =======================================

         String begDateStr = (String) pParams.get(BEG_DATE_S);
         String endDateStr = (String) pParams.get(END_DATE_S);
         log.info(" getSubQuerySql begDateStr=" + begDateStr + ", endDateStr=" + endDateStr);
         String endYearStr = (String) pParams.get(END_YEAR_S);
         String endMonthStr = (String) pParams.get(END_MONTH_S);
         if(begDateStr.length()==0 && endDateStr.length()==0){
           ArrayList dateRange = convertToDateStr( endMonthStr, endYearStr );
           begDateStr = (String)dateRange.get(0);
           endDateStr = (String)dateRange.get(1);
           log.info(" getSubQuerySql begDateStr=" + begDateStr + ", endDateStr=" + endDateStr);
         }
         //======================================================================//

        GregorianCalendar currBegCalendar = new GregorianCalendar();
        currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

        GregorianCalendar currEndCalendar = new GregorianCalendar();
        currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

        String startDateS = ReportingUtils.toSQLDate(currBegCalendar.getTime());
        String endDateS =   ReportingUtils.toSQLDate(currEndCalendar.getTime());


        String filterCrit =
            "  AND inv.add_date BETWEEN " + startDateS + " \n" + " AND " + endDateS + " + 1 \n" +
//          ((Utility.isSet(pSiteCrit))? "  AND inv.bus_entity_id IN (" + pSiteCrit + ") \n" : "") ;
        ((Utility.isSet(pSiteCrit))? "  AND exists (" + pSiteCrit + ") \n" : "") ;
        
        return filterCrit;
        
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
//                 "SELECT DISTINCT be.BUS_ENTITY_ID \n" +
                 "SELECT 1 \n" +
//                 " FROM CLW_BUS_ENTITY_ASSOC be1, CLW_BUS_ENTITY_ASSOC be2, CLW_BUS_ENTITY be, CLW_PROPERTY p \n" +
                 " FROM CLW_PROPERTY p WHERE 1=1 \n" +
//                 " WHERE be1.BUS_ENTITY2_ID = " + storeId + " \n" +
//                 "   AND be1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
//                 "   AND be2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
//                 "   AND be1.BUS_ENTITY1_ID = be2.BUS_ENTITY2_ID \n" +
//                 "   AND be.BUS_ENTITY_ID   = be2.BUS_ENTITY1_ID \n" +
//                 "   AND BUS_ENTITY_STATUS_CD = 'ACTIVE' \n" +
//                 "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = be.BUS_ENTITY_ID \n" +
                 "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = inv.BUS_ENTITY_ID \n" +
                 "   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "'  \n" ;
          } else if ( RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd) ||
                      RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd) ||
                      RefCodeNames.USER_TYPE_CD.MSB.equals(userTypeCd) )  {
            siteIdsSql=
//                " SELECT DISTINCT ua.BUS_ENTITY_ID FROM CLW_USER_ASSOC ua, CLW_PROPERTY p \n" +
                " SELECT 1 FROM CLW_USER_ASSOC ua, CLW_PROPERTY p \n" +
                "  WHERE ua.USER_ID = " + userId + " AND ua.USER_ASSOC_CD = 'SITE' \n" +
                "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = ua.BUS_ENTITY_ID \n" +
                "   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "'  \n" +
                "   and ua.BUS_ENTITY_ID= inv.BUS_ENTITY_ID ";
          } else {
            siteIdsSql=
//                "SELECT DISTINCT be.BUS_ENTITY_ID \n" +
//                "SELECT 1 \n" +
//               " FROM CLW_BUS_ENTITY_ASSOC be1, CLW_BUS_ENTITY_ASSOC be2, \n" +
//               " CLW_BUS_ENTITY be, CLW_USER_ASSOC ua, CLW_PROPERTY p \n" +
//               " WHERE be1.BUS_ENTITY2_ID = " + storeId + " \n" +
//               "   AND be1.BUS_ENTITY_ASSOC_CD = 'ACCOUNT OF STORE' \n" +
//               "   AND be2.BUS_ENTITY_ASSOC_CD = 'SITE OF ACCOUNT' \n" +
//               "   AND be1.BUS_ENTITY1_ID = be2.BUS_ENTITY2_ID \n" +
//               "   AND be.BUS_ENTITY_ID   = be2.BUS_ENTITY1_ID \n" +
//               "   AND BUS_ENTITY_STATUS_CD = 'ACTIVE' \n" +
//               "   AND be.BUS_ENTITY_ID = ua.BUS_ENTITY_ID \n " +
//               "   AND ua.USER_ID = " + userId + " AND ua.USER_ASSOC_CD = 'SITE' \n" +
//               "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = be.BUS_ENTITY_ID \n" +
//               "   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "'  \n" +
//               "   AND be.BUS_ENTITY_ID = inv.BUS_ENTITY_ID "	;
                " SELECT 1 FROM CLW_USER_ASSOC ua, CLW_PROPERTY p \n" +
                "  WHERE ua.USER_ID = " + userId + " AND ua.USER_ASSOC_CD = 'SITE' \n" +
                "   AND p.CLW_VALUE = 'true' AND p.BUS_ENTITY_ID = ua.BUS_ENTITY_ID \n" +
                "   AND p.SHORT_DESC = '" + RefCodeNames.PROPERTY_TYPE_CD.ALLOW_CORPORATE_SCHED_ORDER + "'  \n" +
                "   and ua.BUS_ENTITY_ID= inv.BUS_ENTITY_ID ";
         }
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }

        return siteIdsSql;
      }

    protected String getTitle() {
      //String title = "Forecast Order Summary by Location Report" ;
      String title = "Forecasted Items Summary Zero and Blank Entries" ;
      return title;
    }

    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      title.add(ReportingUtils.createGenericReportColumnView(pTitle,ReportingUtils.PAGE_TITLE, null, "20"));
//      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Store : " + getListOfEntityNames(con, (String) pParams.get(STORE_S)),0,255,"VARCHAR2"));
      if (pParams.containsKey(this.BEG_DATE_S) && Utility.isSet((String) pParams.get(this.BEG_DATE_S)) &&
              pParams.containsKey(this.END_DATE_S) && Utility.isSet((String) pParams.get(this.END_DATE_S))){
            title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(BEG_DATE_S) + " - " + (String) pParams.get(END_DATE_S),ReportingUtils.PAGE_TITLE, null, "20"));
          }else{
        	  String controlName = null;
              controlName = this.END_MONTH_S;
              if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
                title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(this.END_MONTH_S)+ "/" + (String) pParams.get(this.END_YEAR_S) ,ReportingUtils.PAGE_TITLE, null, "20"));
              }
          }
      /*if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(this.END_MONTH_S)+ "/" + (String) pParams.get(this.END_YEAR_S) ,ReportingUtils.PAGE_TITLE, null, "20"));
      }
//      controlName = SITE_MULTI_OPT_S;
//      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
//        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sites : " + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
//     }
      if (pParams.containsKey(this.BEG_DATE_S) && Utility.isSet((String) pParams.get(this.BEG_DATE_S)) &&
          pParams.containsKey(this.END_DATE_S) && Utility.isSet((String) pParams.get(this.END_DATE_S))){
        title.add(ReportingUtils.createGenericReportColumnView("Date Range: " + (String) pParams.get(BEG_DATE_S) + " - " + (String) pParams.get(END_DATE_S),ReportingUtils.PAGE_TITLE, null, "20"));
      }*/
  return title;
}

 protected ArrayList convertToDateStr ( String pEndMonthStr, String pEndYearStr){

   //Month year drop-down
   ArrayList dateRange = new ArrayList();
   int endYear = Integer.parseInt(pEndYearStr);
   int endMonth = Integer.parseInt(pEndMonthStr);
   if(endYear!=0 && endMonth !=0){
         GregorianCalendar begC = new GregorianCalendar(endYear, endMonth - 1, 1);
         Date beginDateC = begC.getTime();
         log.info(" FFFFFFFFFFF beginDateC=" + beginDateC.toString());

         GregorianCalendar endC = new GregorianCalendar(endYear,endMonth-1,1);
         endC.set(Calendar.DAY_OF_MONTH, endC.getActualMaximum(Calendar.DATE));
         Date endDateC = endC.getTime();
         log.info(" FFFFFFFFFFF endDateC=" + endDateC.toString());


         SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

         dateRange.add(df.format(beginDateC));
         dateRange.add(df.format(endDateC));
   }
   log.info(" FFFFFFFFFFF dateRange=" + dateRange.toString());
   return dateRange;
 }

 protected ArrayList transform (ArrayList repList, Integer oTotLoc) {
	 
	   // merge all "garment" product items into one: Begin	 
	 
	   log.info("transform(): repList.size() = " + repList.size());
	   log.info("transform(): oTotLoc = " + oTotLoc);
	   //log.info("transform(): repList1 = " + repList);
	   
	   ForecastOrderSummaryDetail mergedGarmentDet = new ForecastOrderSummaryDetail();
	   ForecastOrderSummaryVector garmentDetList = new ForecastOrderSummaryVector();
	   ForecastOrderSummaryVector repList0 = new ForecastOrderSummaryVector();
   if(repList.size() > 0) {  
	   for (Iterator iter2 = repList.iterator(); iter2.hasNext(); ) {
		   ForecastOrderSummaryDetail det2 = (ForecastOrderSummaryDetail) iter2.next();
		   log.info("transform(): det2.getItemName() = " + det2.getItemName());
	       if (det2.getItemName().matches(pattern)) { // see pattern definition in the in the beginning of the class 
	    	   log.info("pattern matched ! itemName = " + det2.getItemName());
	    	   garmentDetList.add(det2); 	      	   
	       } else {
	    	   repList0.add(det2);
	       }
	   } 
	   log.info("repList0_1.size() = " + repList0.size());
	   log.info("garmentDetList.size() = " + garmentDetList.size());
	   
	   int garmentDetListSize = garmentDetList.size();
	   if (garmentDetListSize > 0) {
		   	   
	      ForecastOrderSummaryDetail detSum = (ForecastOrderSummaryDetail) garmentDetList.get(0);
	      
	      ForecastOrderSummaryDetail garmentDetSumBlank = new ForecastOrderSummaryDetail();
	      ForecastOrderSummaryDetail garmentDetSumZero = new ForecastOrderSummaryDetail();
	      ForecastOrderSummaryDetail garmentDetSumEntry = new ForecastOrderSummaryDetail();

	      garmentDetSumBlank.setItemId(detSum.getItemId());   
	      garmentDetSumBlank.setCategory(detSum.getCategory());
	      garmentDetSumBlank.setItemNum(detSum.getItemNum());
	      garmentDetSumBlank.setItemName(detSum.getItemName());
	      garmentDetSumBlank.setCurrency(detSum.getCurrency());
	      garmentDetSumBlank.setForecastType("Blank");
	      
	      garmentDetSumZero.setItemId(detSum.getItemId());   
	      garmentDetSumZero.setCategory(detSum.getCategory());
	      garmentDetSumZero.setItemNum(detSum.getItemNum());
	      garmentDetSumZero.setItemName(detSum.getItemName());
	      garmentDetSumZero.setCurrency(detSum.getCurrency());
	      garmentDetSumZero.setForecastType("Zero");
	      
	      garmentDetSumEntry.setItemId(detSum.getItemId());   
	      garmentDetSumEntry.setCategory(detSum.getCategory());
	      garmentDetSumEntry.setItemNum(detSum.getItemNum());
	      garmentDetSumEntry.setItemName(detSum.getItemName());
	      garmentDetSumEntry.setCurrency(detSum.getCurrency());
	      garmentDetSumEntry.setForecastType("Entry");
	      
	      int tot_forecastZero = 0;
	      int tot_forecastBlank = 0;
	      int tot_forecastEntry = 0;	      
	      
	      BigDecimal tot_extendedPriceZero = new BigDecimal(0);
	      BigDecimal tot_extendedPriceBlank = new BigDecimal(0);
	      BigDecimal tot_extendedPriceEntry = new BigDecimal(0);
	      
	      BigDecimal tot_shippedValueZero = new BigDecimal(0);
	      BigDecimal tot_shippedValueBlank = new BigDecimal(0);
	      BigDecimal tot_shippedValueEntry = new BigDecimal(0);
	      
	      int tot_orderedQtyZero = 0;
	      int tot_orderedQtyBlank = 0;
	      int tot_orderedQtyEntry = 0;
	      
	      int tot_qtyOnHandZero = 0;
	      int tot_qtyOnHandBlank = 0;
	      int tot_qtyOnHandEntry = 0;
	      
	      int fl_Blank = 0;
	      int fl_Zero = 0;
	      int fl_Entry = 0;
	      
	      for (Iterator iter3 = garmentDetList.iterator(); iter3.hasNext(); ) {
	    	  mergedGarmentDet = (ForecastOrderSummaryDetail) iter3.next();
	          if (mergedGarmentDet.getForecastType().equals("Blank")) {
	        	  tot_forecastBlank = tot_forecastBlank + mergedGarmentDet.getForecast().intValue(); 
	        	  tot_extendedPriceBlank = tot_extendedPriceBlank.add(mergedGarmentDet.getExtendedPrice());
	        	  tot_shippedValueBlank = tot_shippedValueBlank.add(mergedGarmentDet.getShippedValue());
	        	  tot_orderedQtyBlank = tot_orderedQtyBlank + mergedGarmentDet.getOrderedQty().intValue();
	        	  tot_qtyOnHandBlank = tot_qtyOnHandBlank + mergedGarmentDet.getQtyOnHand().intValue();
	        	  
	        	  fl_Blank = 1;
	          }
	          else if (mergedGarmentDet.getForecastType().equals("Zero")) {
	        	  tot_forecastZero = tot_forecastZero + mergedGarmentDet.getForecast().intValue(); 
	        	  tot_extendedPriceZero = tot_extendedPriceZero.add(mergedGarmentDet.getExtendedPrice());
	        	  tot_shippedValueZero = tot_shippedValueZero.add(mergedGarmentDet.getShippedValue());
	        	  tot_orderedQtyZero = tot_orderedQtyZero + mergedGarmentDet.getOrderedQty().intValue();
	        	  tot_qtyOnHandZero = tot_qtyOnHandZero + mergedGarmentDet.getQtyOnHand().intValue();
	        	  
	        	  fl_Zero = 1;
	        	  
	          }
	          else if (mergedGarmentDet.getForecastType().equals("Entry")) {
	        	  tot_forecastEntry = tot_forecastEntry + mergedGarmentDet.getForecast().intValue(); 
	        	  tot_extendedPriceEntry = tot_extendedPriceEntry.add(mergedGarmentDet.getExtendedPrice());
	        	  tot_shippedValueEntry = tot_shippedValueEntry.add(mergedGarmentDet.getShippedValue());
	        	  tot_orderedQtyEntry = tot_orderedQtyEntry + mergedGarmentDet.getOrderedQty().intValue();
	        	  tot_qtyOnHandEntry = tot_qtyOnHandEntry + mergedGarmentDet.getQtyOnHand().intValue();
	        	  
	        	  fl_Entry = 1;
	        	  
	          }
	      }	// for  
	      
	      if (fl_Blank == 1) {
	    	  garmentDetSumBlank.setForecast(tot_forecastBlank);
	    	  garmentDetSumBlank.setExtendedPrice(tot_extendedPriceBlank);
	    	  garmentDetSumBlank.setShippedValue(tot_shippedValueBlank);
	    	  garmentDetSumBlank.setOrderedQty(tot_orderedQtyBlank);
	    	  garmentDetSumBlank.setQtyOnHand(tot_qtyOnHandBlank);
	    	  
	    	  log.info("garmentDetSumBlank = " + garmentDetSumBlank);
	    	  repList0.add(garmentDetSumBlank);
	      }
	      if (fl_Zero == 1) {
	    	  garmentDetSumZero.setForecast(tot_forecastZero);
	    	  garmentDetSumZero.setExtendedPrice(tot_extendedPriceZero);
	    	  garmentDetSumZero.setShippedValue(tot_shippedValueZero);
	    	  garmentDetSumZero.setOrderedQty(tot_orderedQtyZero);
	    	  garmentDetSumZero.setQtyOnHand(tot_qtyOnHandZero);
	    	  
	    	  log.info("garmentDetSumZero = " + garmentDetSumZero);
	    	  repList0.add(garmentDetSumZero);
	      }
	      if (fl_Entry == 1) {
	    	  garmentDetSumEntry.setForecast(tot_forecastEntry);
	    	  garmentDetSumEntry.setExtendedPrice(tot_extendedPriceEntry);
	    	  garmentDetSumEntry.setShippedValue(tot_shippedValueEntry);
	    	  garmentDetSumEntry.setOrderedQty(tot_orderedQtyEntry);
	    	  garmentDetSumEntry.setQtyOnHand(tot_qtyOnHandEntry);
	    	  
	    	  log.info("garmentDetSumEntry = " + garmentDetSumEntry);
	    	  repList0.add(garmentDetSumEntry);
	      }
	          	              	   
	   } // if (garmentDetListSize > 0) {

   } // if(repList.size() > 0)
   log.info("repList0_2.size() = " + repList0.size());
   
   // merge all "garment" product items into one: End	  
   
   ForecastOrderSummaryVector newRepList= new ForecastOrderSummaryVector();
   ForecastOrderSummaryDetail det = null;
   BigDecimal bd = null;
   if (repList0 != null){
     int total = 0;
     int totBlankOrZero = 0;
     double totPriceBlankOrZero = 0;
     
     BigDecimal total_shipped_value = new BigDecimal(0); //new
     int total_ordered_qty = 0; //new
     int total_qtyOnHand = 0; //new
     Integer overall_total_locations = oTotLoc; //new
     
     ForecastOrderSummaryDetail newDet = new ForecastOrderSummaryDetail();
     newDet.init();
     ForecastOrderSummaryDetail detPre = (ForecastOrderSummaryDetail) repList0.get(0);
     String currPre = detPre.getCurrency();
     for (Iterator iter1 = repList0.iterator(); iter1.hasNext(); ) {
       det = (ForecastOrderSummaryDetail) iter1.next();
           
       //----- create currency

       if (!detPre.getItemId().equals(det.getItemId())) {
         newDet.setItemNum(detPre.getItemNum());
         newDet.setCategory(detPre.getCategory());
         newDet.setItemName(detPre.getItemName());
         newDet.setCurrency(det.getCurrency());
         newDet.setTotalBlankOrZero(new Integer(totBlankOrZero));

         newDet.setTotal(new Integer(total));                 
         
         newDet.setPercentBlank(calcPercent((newDet.getForecastBlank()).doubleValue(), total));
         newDet.setPercentZero(calcPercent((newDet.getForecastZero()).doubleValue(), total));
         newDet.setPercentBlankOrZero(calcPercent((newDet.getTotalBlankOrZero()).doubleValue(), total));
         newDet.setPercentEntry(calcPercent((newDet.getForecastEntry()).doubleValue(), total));
         
         /*** new piece of code: Begin ***/ 
         newDet.setTotalShippedValue(total_shipped_value);
         newDet.setTotalOrderedQty(new Integer(total_ordered_qty));
         newDet.setTotalQtyOnHand(new Integer(total_qtyOnHand));
         newDet.setTotalInventory(new Integer(total_ordered_qty + total_qtyOnHand));
         newDet.setOverallTotalLocations(overall_total_locations); //new
         
         BigDecimal bd11 = new BigDecimal(String.valueOf(newDet.getTotalInventory().intValue())); //convert int to BigDecimal
         //log.info("bd11 = " + bd11);
         BigDecimal bd22 = new BigDecimal(String.valueOf(newDet.getOverallTotalLocations().intValue())); //convert int to BigDecimal
         //log.info("bd22 = " + bd22);         
         //BigDecimal bd11AverageUnitsPerLocation = bd11.divide(bd22, 2, BigDecimal.ROUND_HALF_UP); //scale=2; rounds to the digit(s) before the the decimal point
         BigDecimal bd11AverageUnitsPerLocation = bd11.divide(bd22, 0, BigDecimal.ROUND_HALF_UP); //scale=0; rounds to the digit(s) before the the decimal point
         //log.info("bd11AverageUnitsPerLocation1_after_division = " + bd11AverageUnitsPerLocation);
         log.info("bd11AverageUnitsPerLocation1_after_division = " + bd11AverageUnitsPerLocation);
         //bd11AverageUnitsPerLocation = bd11AverageUnitsPerLocation.setScale(0,BigDecimal.ROUND_HALF_UP);
         //log.info("bd11AverageUnitsPerLocation_after_rounding = " + bd11AverageUnitsPerLocation);
         int intAverageUnitsPerLocation11 = bd11AverageUnitsPerLocation.intValue();
         //log.info("intAverageUnitsPerLocation11 = " + intAverageUnitsPerLocation11);
         newDet.setAverageUnitsPerLocation(new Integer(intAverageUnitsPerLocation11));
         /*** new piece of code: End   ***/         
         
         bd = new BigDecimal(totPriceBlankOrZero);
         bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);
         newDet.setExtendedPriceBlankOrZero(bd);
         newRepList.add(newDet);
         detPre = det;

         newDet = new ForecastOrderSummaryDetail();
         newDet.init();
         totBlankOrZero = 0;
         totPriceBlankOrZero = 0;
         total = 0;
         
         /*** new piece of code: Begin ***/         
         total_shipped_value = new BigDecimal(0);
         total_ordered_qty = 0;
         total_qtyOnHand = 0;
         //total_inventory = 0; //new
         overall_total_locations = oTotLoc; //new
         /*** new piece of code: End   ***/         

       } // endif
       if (det.getForecastType().equals("Blank")) {
             newDet.setForecastBlank(det.getForecast());
             newDet.setExtendedPriceBlank(det.getExtendedPrice());
             totBlankOrZero = totBlankOrZero + det.getForecast().intValue();
             totPriceBlankOrZero = totPriceBlankOrZero + det.getExtendedPrice().doubleValue();
             total = total + det.getForecast().intValue();
             
             /*** new piece of code: Begin ***/
             total_shipped_value = total_shipped_value.add(det.getShippedValue());
             total_ordered_qty = total_ordered_qty + det.getOrderedQty();
             total_qtyOnHand  = total_qtyOnHand  + det.getQtyOnHand();                       
             /*** new piece of code: End   ***/
       }
       else if (det.getForecastType().equals("Zero")) {
             newDet.setForecastZero(det.getForecast());
             newDet.setExtendedPriceZero(det.getExtendedPrice());
             totBlankOrZero = totBlankOrZero + det.getForecast().intValue();
             totPriceBlankOrZero = totPriceBlankOrZero + det.getExtendedPrice().doubleValue();
             total = total + det.getForecast().intValue();
             
             /*** new piece of code: Begin ***/
             total_shipped_value = total_shipped_value.add(det.getShippedValue());
             total_ordered_qty = total_ordered_qty + det.getOrderedQty();
             total_qtyOnHand  = total_qtyOnHand  + det.getQtyOnHand();
             /*** new piece of code: End   ***/
       }
       else if (det.getForecastType().equals("Entry")) {
             newDet.setForecastEntry(det.getForecast());
             newDet.setExtendedPriceEntry(det.getExtendedPrice());
             
             total = total + det.getForecast().intValue();
             
             /*** new piece of code: Begin ***/
             total_shipped_value = total_shipped_value.add(det.getShippedValue());
             total_ordered_qty = total_ordered_qty + det.getOrderedQty();
             total_qtyOnHand  = total_qtyOnHand  + det.getQtyOnHand();
             /*** new piece of code: End   ***/
       } //endif

     } // for
     newDet.setItemNum(det.getItemNum());
     newDet.setCurrency(det.getCurrency());
     newDet.setCategory(det.getCategory());
     newDet.setItemName(det.getItemName());
     newDet.setTotalBlankOrZero(new Integer(totBlankOrZero));

     newDet.setTotal(new Integer(total));

     /*** new piece of code: Begin ***/
     newDet.setTotalShippedValue(total_shipped_value);
     newDet.setTotalOrderedQty(new Integer(total_ordered_qty));
     newDet.setTotalQtyOnHand(new Integer(total_qtyOnHand));
     newDet.setTotalInventory(new Integer(total_ordered_qty + total_qtyOnHand));
     newDet.setOverallTotalLocations(overall_total_locations); //new
     
   
     BigDecimal bd1 = new BigDecimal(String.valueOf(newDet.getTotalInventory().intValue())); //convert int to BigDecimal
     //log.info("bd1 = " + bd1);
     BigDecimal bd2 = new BigDecimal(String.valueOf(newDet.getOverallTotalLocations().intValue())); //convert int to BigDecimal
     //log.info("bd2 = " + bd2);
     //BigDecimal bd1AverageUnitsPerLocation = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP); //scale=2; rounds to the digit(s) before the the decimal point
     BigDecimal bd1AverageUnitsPerLocation = bd1.divide(bd2, 0, BigDecimal.ROUND_HALF_UP); //scale=0; rounds to the digit(s) before the the decimal point
     //log.info("bd1AverageUnitsPerLocation1_after_division = " + bd1AverageUnitsPerLocation);
     log.info("bd1AverageUnitsPerLocation1_after_division = " + bd1AverageUnitsPerLocation);
     //bd1AverageUnitsPerLocation = bd1AverageUnitsPerLocation.setScale(0,BigDecimal.ROUND_HALF_UP);
     //log.info("bd1AverageUnitsPerLocation_after_rounding = " + bd1AverageUnitsPerLocation);
     int intAverageUnitsPerLocation = bd1AverageUnitsPerLocation.intValue();
     //log.info("intAverageUnitsPerLocation = " + intAverageUnitsPerLocation);
     
     newDet.setAverageUnitsPerLocation(new Integer(intAverageUnitsPerLocation));   
     /*** new piece of code: End ***/
     
     newDet.setPercentBlank(calcPercent((newDet.getForecastBlank()).doubleValue(), total));
     newDet.setPercentZero(calcPercent((newDet.getForecastZero()).doubleValue(), total));
     newDet.setPercentBlankOrZero(calcPercent((newDet.getTotalBlankOrZero()).doubleValue(), total));
     newDet.setPercentEntry(calcPercent((newDet.getForecastEntry()).doubleValue(), total));

     bd = new BigDecimal(totPriceBlankOrZero);
     bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);
     newDet.setExtendedPriceBlankOrZero(bd);
     newRepList.add(newDet);
   }   
       
   return newRepList;
 }

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
/*
 private String getCurrency(Connection pConn, String customerIdStr){
   return "$";
 }
*/
 private void logError(Object o){
     log.info(o.toString());
 }
 private String logTime() {
	 SimpleDateFormat sdf = new SimpleDateFormat(Constants.SIMPLE_DATETIME_PATTERN);
	 return sdf.format(new Date());
 }
 
//================================================================================
 public class ForecastOrderSummaryVector extends java.util.ArrayList implements Comparator {
    /**
     * Constructor.
     */
    public ForecastOrderSummaryVector () {}

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
      ForecastOrderSummaryDetail obj1 = (ForecastOrderSummaryDetail)o1;
      ForecastOrderSummaryDetail obj2 = (ForecastOrderSummaryDetail)o2;

      if("ItemNum".equalsIgnoreCase(_sortField)) {
       String i1 = obj1.getItemNum();
       String i2 = obj2.getItemNum();
       if(i1==null) if(i2==null) retcode = 0; else  retcode = -1;
       else if(i2==null) retcode = 1; else retcode = i1.compareTo(i2);
     }

      if(!_ascFl) retcode = -retcode;
      return retcode;
    }
   }

 private class ForecastOrderSummaryDetail //implements Record, java.io.Serializable
   {
     private String mItemId;
     private String mItemNum;
     private String mItemName;
     private String mCategory;
     private String mCurrency;

     private Integer mForecastBlank;
     private Integer mForecastZero;
     private Integer mTotalBlankOrZero;
     private Integer mForecastEntry;
     private Integer mTotal;


     private BigDecimal mExtendedPriceBlank;
     private BigDecimal mExtendedPriceZero;
     private BigDecimal mExtendedPriceBlankOrZero;
     private BigDecimal mExtendedPriceEntry;

     private BigDecimal mPercentEntry;
     private BigDecimal mPercentZero;
     private BigDecimal mPercentBlank;
     private BigDecimal mPercentBlankOrZero;

     private String mForecastType;
     private Integer mForecast;
     private BigDecimal mExtendedPrice;
     
     /*** new piece of code: Begin ***/
     private BigDecimal mValueShipped;
     private Integer mQuantityOrdered;
     private Integer mQuantityOnHand;
     private BigDecimal mTotalValueShipped;
     private Integer mTotalQuantityOrdered;
     private Integer mTotalQuantityOnHand;
     private Integer mTotalInventory;
     private Integer mOverallTotalLocations;
     private Integer mAverageUnitsPerLocation;
     /*** new piece of code: End ***/
     
     

     public void init(){
       mForecastBlank = new Integer(0);
       mForecastZero = new Integer(0);
       mTotalBlankOrZero = new Integer(0);
       mForecastEntry = new Integer(0);
       mExtendedPriceBlank = new BigDecimal(0);
       mExtendedPriceZero = new BigDecimal(0);
       mExtendedPriceBlankOrZero = new BigDecimal(0);
       mExtendedPriceEntry = new BigDecimal(0);
       
       /*** new piece of code: Begin ***/
       mValueShipped = new BigDecimal(0);
       mQuantityOrdered = new Integer(0);
       mQuantityOnHand = new Integer(0);
       mTotalValueShipped = new BigDecimal(0);
       mTotalQuantityOrdered = new Integer(0);
       mTotalQuantityOnHand = new Integer(0);
       mTotalInventory = new Integer(0);
       mOverallTotalLocations = new Integer(0);
       mAverageUnitsPerLocation = new Integer(0);
       /*** new piece of code: End ***/

     }
     private ArrayList toList() {
         ArrayList list = new ArrayList();

         list.add(mCategory);
         list.add(mItemNum);
         list.add(mItemName);
         list.add(mForecastZero);
         list.add(mPercentZero);
      ///   list.add(mExtendedPriceZero);
         list.add(mForecastBlank);
         list.add(mPercentBlank);
      ///   list.add(mExtendedPriceBlank);
         list.add(mTotalBlankOrZero);
         list.add(mPercentBlankOrZero);
     ///    list.add(mExtendedPriceBlankOrZero);
         list.add(mForecastEntry);
         list.add(mPercentEntry);
     ///    list.add(mExtendedPriceEntry);
        /// list.add(mTotal);
         
         /*** new piece of code: Begin ***/
         list.add(mTotalValueShipped);
         list.add(mTotalQuantityOrdered);
         list.add(mTotalQuantityOnHand);
         list.add(mTotalInventory);
         list.add(mOverallTotalLocations);
         list.add(mAverageUnitsPerLocation);
         /*** new piece of code: End ***/
         
         return list;
     }

     private GenericReportCellView getCellView(Object dataVal, String styleName){
       GenericReportCellView cellView = GenericReportCellView.createValue();
       cellView.setDataValue(dataVal);
       cellView.setStyleName(styleName);
       return cellView;
     }

     public String getItemId() {
       return mItemId;
     }

     public void setItemId(String pItemId) {
       this.mItemId = pItemId;
     }
     public String getCategory() {
       return mCategory;
     }

     public void setCategory(String pCategory) {
       this.mCategory = pCategory;
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

     public Integer getForecastBlank() {
          return mForecastBlank;
      }

      public void setForecastBlank(Integer pForecastBlank) {
          this.mForecastBlank = pForecastBlank;
      }
      public Integer getForecastZero() {
           return mForecastZero;
      }
      public void setForecastZero(Integer pForecastZero) {
           this.mForecastZero = pForecastZero;
      }
      public Integer getTotalBlankOrZero() {
            return mTotalBlankOrZero;
      }
      public void setTotalBlankOrZero(Integer pTotalBlankOrZero) {
            this.mTotalBlankOrZero = pTotalBlankOrZero;
      }
      public Integer getForecastEntry() {
            return mForecastEntry;
        }
      public void setForecastEntry(Integer pForecastEntry) {
          this.mForecastEntry = pForecastEntry;
      }
      public BigDecimal getExtendedPriceBlank() {
          return mExtendedPriceBlank;
      }
      public void setExtendedPriceBlank(BigDecimal pExtendedPriceBlank) {
          this.mExtendedPriceBlank = pExtendedPriceBlank;
      }
      public BigDecimal getExtendedPriceZero() {
          return mExtendedPriceZero;
      }
      public void setExtendedPriceZero(BigDecimal pExtendedPriceZero) {
          this.mExtendedPriceZero = pExtendedPriceZero;
      }
      public BigDecimal getExtendedPriceBlankOrZero() {
          return mExtendedPriceBlankOrZero;
      }
      public void setExtendedPriceBlankOrZero(BigDecimal pExtendedPriceBlankOrZero) {
          this.mExtendedPriceBlankOrZero = pExtendedPriceBlankOrZero;
      }
      public BigDecimal getExtendedPriceEntry() {
          return mExtendedPriceEntry;
      }
      public void setExtendedPriceEntry(BigDecimal pExtendedPriceEntry) {
          this.mExtendedPriceEntry = pExtendedPriceEntry;
      }

      public void setPercentZero(BigDecimal pPercentZero) {
          this.mPercentZero = pPercentZero;
      }

      public void setPercentBlank(BigDecimal pPercentBlank) {
          this.mPercentBlank = pPercentBlank;
      }
      public void setPercentBlankOrZero(BigDecimal pPercentBlankOrZero) {
          this.mPercentBlankOrZero = pPercentBlankOrZero;
      }
      public void setPercentEntry(BigDecimal pPercentEntry) {
          this.mPercentEntry = pPercentEntry;
      }
      public void setTotal(Integer pTotal) {
        this.mTotal = pTotal;
      }
      
      //==========================================//   
      
      public void setOverallTotalLocations(Integer pOverallTotalLocations) {
    	  this.mOverallTotalLocations = pOverallTotalLocations;
      }
      
      public Integer getOverallTotalLocations() {
          return mOverallTotalLocations;
      }

      public BigDecimal getShippedValue() {
          return mValueShipped;
      }
      
      public void setShippedValue(BigDecimal pShippedValue) {
    	  this.mValueShipped = pShippedValue;
      }
      
      public Integer getOrderedQty() {
          return mQuantityOrdered;
      }
      
      public void setOrderedQty(Integer pOrderedQty) {
    	  this.mQuantityOrdered = pOrderedQty;
      }
      
      public Integer getQtyOnHand() {
          return mQuantityOnHand;
      }
       
      public void setQtyOnHand(Integer pQtyOnHand) {
    	  this.mQuantityOnHand = pQtyOnHand;
      }
      
      public void setTotalShippedValue(BigDecimal pTotalShippedValue) {
    	  this.mTotalValueShipped = pTotalShippedValue;
      }
      
      public BigDecimal getTotalShippedValue() {
    	  return mTotalValueShipped;
      }
          
      public Integer getTotalOrderedQty() {
          return mTotalQuantityOrdered;
      }
      
      public void setTotalOrderedQty(Integer pTotalOrderedQty) {
    	  this.mTotalQuantityOrdered = pTotalOrderedQty;
      }
      
      public Integer getTotalQtyOnHand() {
          return mTotalQuantityOnHand;
      }
       
      public void setTotalQtyOnHand(Integer pTotalQtyOnHand) {
    	  this.mTotalQuantityOnHand = pTotalQtyOnHand;
      }
      
      public Integer getTotalInventory() {
          return mTotalInventory;
      }
      
      public void setTotalInventory(Integer pTotalInventory) {
    	  this.mTotalInventory = pTotalInventory;
      }
      
      public void setAverageUnitsPerLocation(Integer pAverageUnitsPerLocation) {
    	  this.mAverageUnitsPerLocation = pAverageUnitsPerLocation;
      }
      
      //==========================================//
      public String getForecastType() {
         return mForecastType;
       }

       public void setForecastType(String pForecastType) {
         this.mForecastType = pForecastType;
       }

      public Integer getForecast() {
          return mForecast;
      }
      public void setForecast(Integer pForecast) {
          this.mForecast = pForecast;
      }
      public BigDecimal getExtendedPrice() {
          return mExtendedPrice;
      }
      public void setExtendedPrice(BigDecimal pExtendedPrice) {
          this.mExtendedPrice = pExtendedPrice;
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
