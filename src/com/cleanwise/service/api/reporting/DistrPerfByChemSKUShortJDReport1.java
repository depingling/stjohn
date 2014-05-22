package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.util.Utility;

import org.apache.log4j.Logger;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.Connection;
import com.cleanwise.service.api.value.IdVector;
import java.sql.SQLException;
import java.lang.Math;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.value.GenericReportStyleView;


public class DistrPerfByChemSKUShortJDReport1 implements  GenericReportMulti {

    private static final Logger log = Logger.getLogger(DistrPerfByChemSKUShortJDReport1.class);

//    private static final String BEG_DATE = "BEG_DATE";
//    private static final String END_DATE = "END_DATE";
//    private static final String STORE_ID = "STORE";
    protected static final String REPORT_NAME    = "REPORT_NAME";
    protected static final String STORE_S        = "DW_STORE_SELECT";
    protected static final String BEG_DATE_S     = "DW_BEGIN_DATE";
    protected static final String END_DATE_S     = "DW_END_DATE";
    protected static final String REPRES_OPT_S   = "DW_DSR_OPT";
    protected static final String MARKET_OPT_S   = "DW_VERTICAL_OPT";
    protected static final String REGION_OPT_S   = "DW_REGION_OPT";
    protected static final String ACCOUNT_MULTI_OPT_S    = "DW_LOCATE_ACCOUNT_MULTI_OPT";
    protected static final String CATEGORY_OPT_S    = "DW_CATEGORY_OPT";
    protected static final String LOCATE_ITEM_OPT_S    = "DW_LOCATE_ITEM_OPT";
    protected static final String CONNECT_CUST_OPT_S   = "DW_CONNECT_CUST_OPT";
    protected static final String REGION_AUTOSUGGEST_OPT_S   = "DW_REGION_AUTOSUGGEST_OPT";
    protected static final String REPRES_AUTOSUGGEST_OPT_S   = "DW_DSR_AUTOSUGGEST_OPT";
    protected static final String CATEGORY_AUTOSUGGEST_OPT_S = "DW_CATEGORY_AUTOSUGGEST_OPT";


    protected static final String PRICE_COL = "LINE_AMOUNT";
    protected static final String QTY_COL   = "QUANTITY";
    protected static final String COST_COL  = "LINE_COST";

    protected static final String GRAND_TOTAL ="Grand Total";
    protected static final String SUB_TOTAL ="Sub Total";
    protected static final String BOLD_STYLE = "BOLD";

    protected static final String FONT_NAME = "Arial";
    protected static final int FONT_SIZE = 10;

    protected static final String[] COL_WIDTH = new String[]{"12","31","5","5","11","21","12","12","9","9","10","11","11"};


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection con = pCons.getReportConnection();

        String reportName = (String) pParams.get(REPORT_NAME);
        String begDateStr = (String) pParams.get(BEG_DATE_S);
        String endDateStr = (String) pParams.get(END_DATE_S);
        String storeIdStr = (String) pParams.get(STORE_S);
        String accountIdStr = (String) pParams.get(ACCOUNT_MULTI_OPT_S);
        String regionFilter = getRegionFilter(pParams);
        String marketFilter = (String) pParams.get(MARKET_OPT_S);
        String represFilter = getRepresFilter(pParams);
        String categoryFilter = getCategoryFilter(pParams);
        String itemFilter = (String) pParams.get(LOCATE_ITEM_OPT_S);
        String connCustFilter = (String) pParams.get(CONNECT_CUST_OPT_S);

        String delay = (String) pParams.get("DD_OPT");
        boolean delayFl = true;
        if(Utility.isSet(delay)) delayFl = false;

        boolean accountFl = Utility.isSet(accountIdStr);
        boolean regionFl = Utility.isSet(regionFilter);
        boolean industryFl = Utility.isSet(marketFilter);
        boolean represFl = Utility.isSet(represFilter);
        boolean categoryFl = Utility.isSet(categoryFilter);
        boolean itemFl = Utility.isSet(itemFilter);
        boolean connCustFl = Utility.isSet(connCustFilter)  && connCustFilter.equals("Yes") ;


        /*
        #STORE##BEG_DATE#
        #END_DATE#
        #ACCOUNT_MULTI_OPT#
        #REPRES_OPT(STRING,N,Sales Representative:)#
        #REGION_OPT(STRING,N,Region:)#
        #MARKET_OPT(STRING,N,Market:)#
        */
        int storeId;

        for(Iterator iter = pParams.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
        }
        try {
            storeId = Integer.parseInt(storeIdStr);
        } catch (NumberFormatException e) {
            log.info(e.getMessage(), e);
            String mess = "^clw^Store identifier is not a valid value^clw^";
            throw new Exception(mess);
        }

        if (!ReportingUtils.isValidDate(begDateStr)) {
            String mess = "^clw^\"" + begDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }

        if (!ReportingUtils.isValidDate(endDateStr)) {
            String mess = "^clw^\"" + endDateStr + "\" is not a valid date of the form: mm/dd/yyyy^clw^";
            throw new Exception(mess);
        }


        GregorianCalendar currBegCalendar = new GregorianCalendar();
        currBegCalendar.setTime(ReportingUtils.parseDate(begDateStr));

        GregorianCalendar priorBegCalendar = (GregorianCalendar) currBegCalendar.clone();
        priorBegCalendar.add(GregorianCalendar.YEAR, -1);

        GregorianCalendar currEndCalendar = new GregorianCalendar();
        currEndCalendar.setTime(ReportingUtils.parseDate(endDateStr));

        GregorianCalendar priorEndCalendar = (GregorianCalendar) currEndCalendar.clone();
        priorEndCalendar.add(GregorianCalendar.YEAR, -1);

        String startDateS = ReportingUtils.toSQLDate(currBegCalendar.getTime());
        String endDateS = ReportingUtils.toSQLDate(currEndCalendar.getTime());
        String  subSqlForCurrPeriod  = " select DATE_DIM_ID from DW_DATE_DIM where \n CALENDAR_DATE between "+ startDateS+" and "+endDateS ;

        startDateS = ReportingUtils.toSQLDate(priorBegCalendar.getTime());
        endDateS = ReportingUtils.toSQLDate(priorEndCalendar.getTime());
        String  subSqlForPriorPeriod = " select DATE_DIM_ID from DW_DATE_DIM where \n CALENDAR_DATE between "+ startDateS+" and "+endDateS ;

        String subSqlJdItems =
                 " select ITEM_DIM_ID from DW_ITEM_DIM where UPPER(JD_ITEM_FL) = 'TRUE' \n" ;

        String subSqlFilters  =
            (accountFl  ? " (select ACCOUNT_NAME from DW_ACCOUNT_DIM where dw.ACCOUNT_DIM_ID = ACCOUNT_DIM_ID ) account_name, \n":"")+
            (regionFl   ? " (select REGION_NAME from DW_REGION_DIM where dw.REGION_DIM_ID = REGION_DIM_ID ) REGION, \n":"")+
            (industryFl ? " (select JD_MARKET from  DW_ACCOUNT_DIM  where dw.ACCOUNT_DIM_ID= ACCOUNT_DIM_ID  )   INDUSTRY, \n":"")+
            (represFl   ? " (select REP_NAME from DW_SALES_REP_DIM where dw.SALES_REP_DIM_ID = SALES_REP_DIM_ID ) REP_NAME, \n":"");

        String regionCondStr = (!regionFl) ? "" :
                               " SELECT REGION_DIM_ID FROM DW_REGION_DIM WHERE \n" +
                               "   UPPER(REGION_NAME) like '%" + regionFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
        String represCondStr = (!represFl) ? "" :
                               " SELECT SALES_REP_DIM_ID FROM DW_SALES_REP_DIM WHERE \n" +
                               "   UPPER(REP_NAME) like '%" + represFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
        String marketCondStr = (!industryFl) ? "" :
                               " SELECT ACCOUNT_DIM_ID FROM DW_ACCOUNT_DIM WHERE \n" +
                               "   UPPER(JD_MARKET) like '%" + marketFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
        String categoryCondStr = (!categoryFl) ? "" :
//                               " SELECT CATEGORY_DIM_ID FROM DW_CATEGORY_DIM WHERE \n" +
//                              "   UPPER(JD_CATEGORY1) like '%" + categoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
                              "SELECT ITEM_DIM_ID FROM DW_ITEM_DIM i, DW_CATEGORY_DIM c \n" +
                              "WHERE i.CATEGORY_DIM_ID = c.CATEGORY_DIM_ID  \n" +
                              "  AND i.STORE_DIM_ID =   " + storeIdStr + " \n" +
                              "  AND UPPER(JD_CATEGORY1) like '%" + categoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";


        String connCustCondStr = (!connCustFl) ? "" :
                             " SELECT ACCOUNT_DIM_ID FROM DW_ACCOUNT_DIM WHERE \n" +
                             "   UPPER(CONNECTION_CUSTOMER) = '" + String.valueOf(connCustFl).toUpperCase() + "' \n";


        String filterCond =
            (accountFl?" and ACCOUNT_DIM_ID in ( \n" + accountIdStr + ") \n":"")+
            (regionFl?"  and REGION_DIM_ID  in ( \n" + regionCondStr + ") \n" : "") +
            (industryFl?" and ACCOUNT_DIM_ID in ( \n" + marketCondStr + ") \n":"")+
            (represFl?"  and SALES_REP_DIM_ID  in ( \n" + represCondStr + ") \n" : "") +
//            (categoryFl?"  and CATEGORY_DIM_ID  in ( \n" + categoryCondStr + ") \n" : "") +
            (categoryFl?"  and ITEM_DIM_ID  in ( \n" + categoryCondStr + ") \n" : "") +
            (itemFl?"  and ITEM_DIM_ID  in ( \n" + itemFilter + ") \n" : "") +
            (connCustFl?" and ACCOUNT_DIM_ID in ( \n" + connCustCondStr + ") \n":"");


        String sql =
                "SELECT \n"+
                " sku \"SKU\", \n"+
                " pack \"Pack\", \n"+
                " uom \"UOM\", \n"+
                " item_size \"Size\", \n"+
                " product \"Product\", \n"+
                " category \"Subcategory\", \n"+
                " price2007 \"2007 Sales\", \n"+
                " price2008 \"2008 Sales\", \n"+
                " Round(price2008/9354337,4) \"% of Total%\", \n"+
                " cost2008 \"2008 Cost\", \n"+
                " (CASE WHEN abs(price2008) >0 AND abs(price2007) >0 THEN " +
                "    round((price2008 - price2007)/price2007,4) ELSE NULL END) \"Sales Growth\", \n"+
                " qty2007 \"2007 Units\", \n"+
                " qty2008 \"2008 Units\", \n"+
                " (CASE WHEN abs(qty2008) >0 AND abs(qty2007)> 0 THEN " +
                "    round((qty2008 - qty2007)/qty2007,4) ELSE NULL END)  \"Units Growth %\", \n"+
                " price2008-cost2008 \"2008 Margin\", \n"+
                " (CASE WHEN abs(price2008) >0 THEN " +
                "    round((price2008 - cost2008)/price2008,4) ELSE NULL END)  \"2008 Margin %\", \n"+
                (accountFl?" account_name, \n":"")+
                (regionFl?" REGION, \n":"")+
                (industryFl?" INDUSTRY, \n":"")+
                (represFl?" REP_NAME, \n":"")+
                " NULL \"2008 Avg Inv\", \n"+
                " NULL \"Inv Turns\", \n"+
                " flag \n"+
                " FROM  (\n"+
                " SELECT\n"+
                " sku,\n"+
                " Max(pack) pack,\n"+
                " Max(uom) uom,\n"+
                " Max(item_size) item_size,\n"+
                " product,\n"+
                " Max(category) category,\n"+
                " Sum(price2008) price2008,\n"+
                " Sum(cost2008) cost2008,\n"+
                " Sum(qty2008) qty2008,\n"+
                " Sum(price2007) price2007,\n"+
                " Sum(cost2007) cost2007,\n"+
                " Sum(qty2007) qty2007,\n"+
               (accountFl?" account_name, \n":"")+
               (regionFl?" REGION, \n":"")+
               (industryFl?" upper(INDUSTRY) industry, \n":"")+
               (represFl?" REP_NAME, \n":"")+
                " Max(flag) flag\n"+
                " FROM\n"+
                " (\n"+
                " (\n"+
        //---
                " SELECT\n"+
//                " DIST_SKU sku, \n"+
                "(select jd_manuf_sku from DW_ITEM_DIM where dw.item_dim_id = item_dim_id) sku, \n" +
                " max(DIST_PACK) pack, \n"+
                " max(DIST_UOM) uom, \n"+
                " max((select jd_item_size from DW_ITEM_DIM where dw.item_dim_id = item_dim_id)) As item_size, \n"+
                " (select jd_item_desc from DW_ITEM_DIM where dw.item_dim_id = item_dim_id)  AS product, \n" +
//                " max((select jd_category1 from DW_CATEGORY_DIM where dw.category_dim_id = category_dim_id)) AS category, \n" +
                " max((select jd_category1 from DW_CATEGORY_DIM c, DW_ITEM_DIM i \n" +
                "      where c.CATEGORY_DIM_ID = i.CATEGORY_DIM_ID \n" +
                "        and dw.ITEM_DIM_ID = i.ITEM_DIM_ID )) AS category, \n" +
                " Sum("+PRICE_COL+") price2008,\n"+
                " Sum("+COST_COL+") cost2008,\n"+
                " Sum("+QTY_COL+") qty2008,\n"+
                " 0 price2007,\n"+
                " 0 cost2007,\n"+
                " 0 qty2007,\n"+
                subSqlFilters +
                " (select (CASE WHEN jd_item_id IS NOT NULL THEN '' ELSE 'JanPak Item Name' END) \n" +
                "    from DW_ITEM_DIM where dw.item_dim_id = item_dim_id)  AS flag \n" +

                " FROM dw_invoice_fact dw \n"+
                " WHERE ITEM_DIM_ID in ( \n" + subSqlJdItems + " ) \n"+
                "  AND  DATE_DIM_ID in ( \n" + subSqlForCurrPeriod + " ) \n" +
                "  AND  STORE_DIM_ID =   " + storeId + " \n" +
                filterCond +
//                " GROUP BY DIST_SKU, item_dim_id \n"+
                " GROUP BY  item_dim_id \n"+
                (accountFl?", ACCOUNT_DIM_ID":"")+
                (regionFl?", REGION_DIM_ID":"")+
                (industryFl?", ACCOUNT_DIM_ID":"")+
                (represFl?", SALES_REP_DIM_ID":"")+
                " )\n"+

                " UNION all\n"+

                " (\n"+
                " SELECT\n"+
//                " DIST_SKU sku, \n"+
                "(select jd_manuf_sku from DW_ITEM_DIM where dw.item_dim_id = item_dim_id) sku, \n" +
                " max(DIST_PACK) pack, \n"+
                " max(DIST_UOM) uom, \n"+
                " max((select jd_item_size from DW_ITEM_DIM where dw.item_dim_id = item_dim_id)) item_size, \n"+
                " (select jd_item_desc from DW_ITEM_DIM where dw.item_dim_id = item_dim_id)  AS product, \n" +
//                " max((select jd_category1 from DW_CATEGORY_DIM where dw.category_dim_id = category_dim_id)) AS category, \n" +
                " max((select jd_category1 from DW_CATEGORY_DIM c, DW_ITEM_DIM i \n" +
                "      where c.CATEGORY_DIM_ID = i.CATEGORY_DIM_ID \n" +
                "        and dw.ITEM_DIM_ID = i.ITEM_DIM_ID )) AS category, \n" +

                " 0 price2008,\n"+
                " 0 cost2008,\n"+
                " 0 qty2008,\n"+
                " Sum("+PRICE_COL+") price2008,\n"+
                " Sum("+COST_COL+") cost2008,\n"+
                " Sum("+QTY_COL+") qty2008,\n"+
                subSqlFilters +
                " (select (CASE WHEN jd_item_id IS NOT NULL THEN '' ELSE 'JanPak Item Name' END) \n" +
                "    from DW_ITEM_DIM where dw.item_dim_id = item_dim_id)  AS flag \n" +

                " FROM dw_invoice_fact dw \n"+
                " WHERE ITEM_DIM_ID in ( \n" + subSqlJdItems + " ) \n"+
                "  AND  DATE_DIM_ID in ( \n" + subSqlForPriorPeriod + " ) \n" +
                "  AND  STORE_DIM_ID =   " + storeId + " \n" +
                filterCond +
//                " GROUP BY DIST_SKU, item_dim_id \n"+
                " GROUP BY  item_dim_id \n"+
                  (accountFl?", ACCOUNT_DIM_ID":"")+
                  (regionFl?", REGION_DIM_ID":"")+
                  (industryFl?", ACCOUNT_DIM_ID":"")+
                  (represFl?", SALES_REP_DIM_ID":"")+
                " )\n"+
                " )\n"+
                " GROUP BY sku, product\n"+
                (accountFl?", ACCOUNT_NAME":"")+
                (regionFl?", REGION":"")+
                (industryFl?", upper(INDUSTRY)":"")+
                (represFl?", REP_NAME":"")+
                " )\n"+

                " ORDER BY " +
                "UPPER(\"Subcategory\"),     \n " +
                "\"2008 Sales\" desc \n";

        log.info("process sql:" + sql);

        Statement stmt = con.createStatement();

        int totalSum = 0;
        ResultSet rs = stmt.executeQuery(sql);
        LinkedList lines = new LinkedList();
        while (rs.next()) {
            aRecord record = new aRecord();

            String productName = rs.getString("Product");
            record.setProductName(productName);
            record.setEnterpriseProductName(productName);

            String manufSku = rs.getString("SKU");
            record.setManufacturerSku(manufSku);

            String category = rs.getString("Subcategory");
            record.setCategory(category);

            Integer currUnits = new Integer(rs.getInt("2008 Units"));
            record.setCurrProductUnits(currUnits);

            Integer currSales = new Integer(rs.getInt("2008 Sales"));
            totalSum += currSales.intValue();
            record.setCurrProductSales(currSales);

            Integer currCost = new Integer(rs.getInt("2008 Cost"));
            record.setCurrProductCost(currCost);

            BigDecimal perOfTotal = rs.getBigDecimal("% of Total%");
            record.setPerOfTotal(perOfTotal);

            BigDecimal salesGrowth = rs.getBigDecimal("Sales Growth");
            record.setSalesGrowth(salesGrowth);

            BigDecimal unitsGrowth = rs.getBigDecimal("Units Growth %");
            record.setUnitsGrowth(unitsGrowth);

            Integer dollarMargin = new Integer(rs.getInt("2008 Margin"));
            record.setDecMargin(dollarMargin);

            BigDecimal perMargin = rs.getBigDecimal("2008 Margin %");
            record.setPerMargin(perMargin);

            Integer priorUnits = new Integer(rs.getInt("2007 Units"));
            record.setPriorProductUnits(priorUnits);

            Integer priorSales = new Integer(rs.getInt("2007 Sales"));
            record.setPriorProductSales(priorSales);

            record.setPriorProductCost(new Integer(0));

            if(Utility.isSet(accountIdStr)) {
                String accountName = rs.getString("account_name");
                record.setAccountName(accountName);
            }
            if(Utility.isSet(regionFilter)) {
                String region = rs.getString("REGION");
                record.setRegion(region);
            }
            if(Utility.isSet(marketFilter)) {
                String industry = rs.getString("INDUSTRY");
                record.setIndustry(industry);
            }
            if(Utility.isSet(represFilter)) {
                String represName = rs.getString("REP_NAME");
                record.setRepresName(represName);
            }

            String pack = rs.getString("Pack");
            record.setPack(pack);
            String uom = rs.getString("UOM");
            record.setUom(uom);
            String size = rs.getString("Size");
            record.setSize(size);
            record.setGroupByFld(category);  // category

            lines.add(record);


        }
        rs.close();
        stmt.close();

        ArrayList records = new ArrayList();
        // define Total record
        aRecord recordTotal = new aRecord();
        recordTotal.init();
        recordTotal.setManufacturerSku(GRAND_TOTAL);
        // define subTotal record
        aRecord recordSubTotal = new aRecord();
        recordSubTotal.init();
        String groupVal = "";
        String priorGroupVal = "";
        int groupNom = 0;
        for(Iterator iter=lines.iterator(); iter.hasNext();) {
            aRecord record = (aRecord) iter.next();
            Integer curVal = record.getCurrProductSales();
            double curValDb = curVal.doubleValue();
            double percent ;
            BigDecimal bd = null;
            if (totalSum != 0){
              percent = curValDb / totalSum;
              bd = new BigDecimal(percent);
              bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);
            }
            record.setPerOfTotal(bd);

            groupVal = record.getGroupByFld();
            if (groupVal != null && priorGroupVal.length() > 0 &&
                 !groupVal.equals(priorGroupVal)) {
              recordSubTotal.calculate();
              recordSubTotal.setManufacturerSku(SUB_TOTAL);
              recordSubTotal.setCategory(priorGroupVal);
              records.add(recordSubTotal.toList(false,false,false, false));
              recordSubTotal.init();
              groupNom ++;
            }
            recordSubTotal.sum(record);
            priorGroupVal = groupVal;
            recordTotal.sum(record);
            records.add(record.toList(accountFl,regionFl, industryFl, represFl));
        }
        if (groupNom != 0){
          recordSubTotal.calculate();
          recordSubTotal.setManufacturerSku(SUB_TOTAL);
          recordSubTotal.setCategory(groupVal);
          records.add(recordSubTotal.toList(false, false, false, false));
        }
        recordTotal.calculate();
        records.add(recordTotal.toList(false,false,false, false));



        GenericReportResultView result = GenericReportResultView.createValue();
        HashMap userStyles = createReportStyleDescriptor();

        GenericReportColumnViewVector header =
                getReportHeader(currBegCalendar.get(GregorianCalendar.YEAR),accountFl, regionFl, industryFl, represFl);
        result.setHeader(header);
        result.setTitle( getReportTitle(con, reportName, pParams));
        result.setUserStyle(userStyles);
        result.setFreezePositionColumn(6);
        result.setFreezePositionRow(result.getTitle().size()+2);

        result.setColumnCount(header.size());
        result.setName("Chem SKU Short");
        result.setTable(records);
if(delayFl) {
        long begT = currBegCalendar.getTime().getTime();
        long endT = currEndCalendar.getTime().getTime();
        long diff = endT-begT;
        long days = diff/(1000*3600*24);
log.info("Days: "+days);
        long del = days*5*1000;
        //Thread.sleep(del);
}
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        resultV.add(result);
        return resultV;
    }

    protected HashMap createReportStyleDescriptor(){

         /*
        (String parm1, String parm2, String parm3, String parm4, short parm5, String parm6, String parm7, String parm8, String parm9, int[] parm10)
        */
         //int[] mergeRegion = new int[]{1,1,1,getReportHeader().size()};
         //<style name><value type><value format><font name><font size><font type><font color><background color><aliment><wrap flag><merge (not working)><data class NULL><width><scale>

         GenericReportStyleView colSku = new GenericReportStyleView("COL_SKU","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[0],0 );
         GenericReportStyleView colProduct = new GenericReportStyleView("COL_PRODUCT","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[1],0 );
         GenericReportStyleView colPack = new GenericReportStyleView("COL_PACK","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[2],0 );
         GenericReportStyleView colUom = new GenericReportStyleView("COL_UOM","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[3],0 );
         GenericReportStyleView colSize = new GenericReportStyleView("COL_SIZE","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[4],0 );
         GenericReportStyleView colCateg = new GenericReportStyleView("COL_CATEG","TEXT",null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",false, null,null,COL_WIDTH[5],0 );

         GenericReportStyleView colCurSales = new GenericReportStyleView("COL_CUR_SALES",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[6],0 );
         GenericReportStyleView colPriSales = new GenericReportStyleView("COL_PRI_SALES",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[7],0 );
    //     GenericReportStyleView colProcOfTot = new GenericReportStyleView("COL_PROC_TOT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[8],0 );
         GenericReportStyleView colSalesGrPr = new GenericReportStyleView("COL_SALES_GR_PR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[8],0 );
    //     GenericReportStyleView colSalesGr = new GenericReportStyleView("COL_SALES_GR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[10],0 );
         GenericReportStyleView colCurUnit = new GenericReportStyleView("COL_CUR_UNIT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[9],0 );
         GenericReportStyleView colPriUnit = new GenericReportStyleView("COL_PRI_UNIT",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[10],0 );

         GenericReportStyleView colUnitGr = new GenericReportStyleView("COL_UNIT_GR",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[11],0 );
         GenericReportStyleView colMargin = new GenericReportStyleView("COL_MARGIN",null,null,FONT_NAME,FONT_SIZE,"BOLD",null,null,"CENTER",true, null,null,COL_WIDTH[12],0 );

         GenericReportStyleView typeAmountD = new GenericReportStyleView("TYPE_AMOUNT_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );
         GenericReportStyleView typePercentD = new GenericReportStyleView("TYPE_PERCENT_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"0.00%",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );
         GenericReportStyleView typeQtyD = new GenericReportStyleView("TYPE_QTY_DATA",ReportingUtils.DATA_CATEGORY.FLOAT,"#,##0",FONT_NAME,FONT_SIZE,null,null,null,null,true, null,null,null,0 );

         GenericReportUserStyleDesc reportDesc = new GenericReportUserStyleDesc();
         reportDesc.setUserStyle(colSku.getStyleName(), colSku);
         reportDesc.setUserStyle(colProduct.getStyleName(), colProduct);
         reportDesc.setUserStyle(colPack.getStyleName(), colPack);
         reportDesc.setUserStyle(colUom.getStyleName(), colUom);
         reportDesc.setUserStyle(colSize.getStyleName(), colSize);
         reportDesc.setUserStyle(colCateg.getStyleName(), colCateg);

         reportDesc.setUserStyle(colCurSales.getStyleName(), colCurSales);
         reportDesc.setUserStyle(colPriSales.getStyleName(), colPriSales);
 //        reportDesc.setUserStyle(colProcOfTot.getStyleName(), colProcOfTot);
 //        reportDesc.setUserStyle(colSalesGr.getStyleName(), colSalesGr);
         reportDesc.setUserStyle(colSalesGrPr.getStyleName(), colSalesGrPr);
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

    private GenericReportColumnViewVector getReportHeader(int year,boolean accountFl,boolean regionFl, boolean industryFl, boolean represFl) {
        String yearC = "Current Year" ; // (year)
        String yearP = "Prior Year" ;   // (year -1)
/*
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
 //   if(accountFl)header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Account", 0, 255, "VARCHAR2"));
 //   if(regionFl)header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Region", 0, 255, "VARCHAR2"));
 //   if(industryFl)header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Vertical", 0, 255, "VARCHAR2"));
 //   if(represFl)header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sales Representative", 0, 255, "VARCHAR2"));

 // A /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SKU Number", 0, 255, "VARCHAR2","80",false,null));
 // B /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Product Name", 0, 255, "VARCHAR2","150",false,null));
 // C /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Pack", 0, 255, "VARCHAR2","40",false,null));
 // D /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "UOM", 0, 255, "VARCHAR2","40",false,null));
 // E /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Size", 0, 255, "VARCHAR2","40",false,null));
 // F /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Category", 0, 255, "VARCHAR2","100",false,null));
 //       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Supplier Name", 0, 255, "VARCHAR2"));
 // G /  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", yearC + " Sales $$", 2, 20, "NUMBER","100",false,"#,##0"));
 // H /  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", yearP + " Sales $$", 0, 20, "NUMBER","100",false,"#,##0"));
 //////  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "% of Total %", 2, 20, "NUMBER","60",false,"0.00%"));
 //////  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", yearC + " Cost $$", 0, 20, "NUMBER","100",false,"#,##0"));
 // K /  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Sales Growth %%", 2, 20, "NUMBER","60",false,"0.00%"));
 // L /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", yearC + " Units", 0, 32, "NUMBER","80",false,"#,##0"));
 // M /  header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", yearP + " Units", 0, 32, "NUMBER","80",false,"#,##0"));
 // N /  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Units Growth %%", 2, 20, "NUMBER","60", false,"0.00%"));
 ///////  header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", yearC + " Margin $$", 0, 20, "NUMBER","80",false,"#,##0"));
 // P /  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", yearC + " Margin %%", 2, 20, "NUMBER","60",false,"0.00%"));
 ///////  header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", yearC + " Average Inventory $$", 0, 255, "VARCHAR2","80",false));
 //////  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Inventory Turns", 0, 255, "VARCHAR2","80",false));
 /////       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Flag", 0, 255, "VARCHAR2"));
*/
          GenericReportColumnViewVector header = new GenericReportColumnViewVector();
          header.add(ReportingUtils.createGenericReportColumnView("SKU Number", "COL_SKU", null,COL_WIDTH[0]));
          header.add(ReportingUtils.createGenericReportColumnView("Product Name",  "COL_PRODUCT", null,COL_WIDTH[1]));
          header.add(ReportingUtils.createGenericReportColumnView("Pack", "COL_PACK", null,COL_WIDTH[2]));
          header.add(ReportingUtils.createGenericReportColumnView("UOM", "COL_UOM", null,COL_WIDTH[3]));
          header.add(ReportingUtils.createGenericReportColumnView("Size",  "COL_SIZE", null,COL_WIDTH[4]));
          header.add(ReportingUtils.createGenericReportColumnView("Category", "COL_CATEG", null,COL_WIDTH[5]));

          header.add(ReportingUtils.createGenericReportColumnView( yearC + " Sales $$", "COL_CUR_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[6]));
          header.add(ReportingUtils.createGenericReportColumnView(yearP + " Sales $$", "COL_PRI_SALES","TYPE_AMOUNT_DATA",COL_WIDTH[7]));
          header.add(ReportingUtils.createGenericReportColumnView("Sales Growth %%","COL_SALES_GR_PR","TYPE_PERCENT_DATA",COL_WIDTH[8]));
          header.add(ReportingUtils.createGenericReportColumnView(yearC + " Units","COL_CUR_UNIT","TYPE_QTY_DATA",COL_WIDTH[9]));
          header.add(ReportingUtils.createGenericReportColumnView(yearP + " Units","COL_PRI_UNIT","TYPE_QTY_DATA",COL_WIDTH[10]));
          header.add(ReportingUtils.createGenericReportColumnView("Units Growth %%", "COL_UNIT_GR","TYPE_PERCENT_DATA",COL_WIDTH[11]));
         header.add(ReportingUtils.createGenericReportColumnView(yearC + " Margin %%","COL_MARGIN","TYPE_PERCENT_DATA",COL_WIDTH[12]));

        return header;
    }

    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",pTitle,0,255,"VARCHAR2"));
      String controlLabel = null;
      String controlName = null;

      controlLabel = ReportingUtils.getControlLabel(STORE_S, pParams, "Store");
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String", controlLabel + getListOfNames(con, this.STORE_S , pParams),0,255,"VARCHAR2"));

      String controlLabelB = ReportingUtils.getControlLabel(BEG_DATE_S, pParams, "Date Begin");
      String controlLabelE = ReportingUtils.getControlLabel(END_DATE_S, pParams, "End");
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabelB + (String) pParams.get(BEG_DATE_S) + "; " + controlLabelE + (String) pParams.get(END_DATE_S),0,255,"VARCHAR2"));

      controlName = this.ACCOUNT_MULTI_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Accounts");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }
      controlName = this.REPRES_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Distributor Sales Rep");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = REPRES_AUTOSUGGEST_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor Sales Rep : " + pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = this.REGION_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Region");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = REGION_AUTOSUGGEST_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Branch : " + pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = this.MARKET_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Verical Market");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = this.LOCATE_ITEM_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Items");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfNames(con, controlName, pParams),0,255,"VARCHAR2"));
      }
      controlName = this.CATEGORY_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Category");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = CATEGORY_AUTOSUGGEST_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category : " + pParams.get(controlName),0,255,"VARCHAR2"));
      }
      controlName = CONNECT_CUST_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Connection Customer");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + (String) pParams.get(controlName),0,255,"VARCHAR2"));
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
     String colName ="";
     if (controlName.equals(STORE_S)){
       typeDim = "STORE";
       colName = typeDim + "_NAME";
     } else if (controlName.equals(this.ACCOUNT_MULTI_OPT_S)) {
       typeDim = "ACCOUNT";
       colName = typeDim + "_NAME";
     } else if (controlName.equals(this.LOCATE_ITEM_OPT_S)) {
         typeDim = "ITEM";
         colName = "JD_" + typeDim + "_DESC";
     }
     if (idsS.length() != 0) {
       try {

         String sql =
             " select " + colName + " from DW_" + typeDim + "_DIM where " + typeDim + "_DIM_ID \n" +
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

  protected String getTitle() {
    String title = "Performance by SKU" ;
    return title;
  }

  protected Object putCellStyle(String style, Object obj) {
    if (style == null || obj == null) {
      return obj;
    }
    HashMap map = new HashMap();
    map.put(style, obj);
    return map;
  }

    private String getRegionFilter(Map pParams) {

        String regionStr = (String) pParams.get(REGION_OPT_S);

        if (Utility.isSet(regionStr)) {
            return regionStr;
        } else {
            String autoSuggRegStr = (String) pParams.get(REGION_AUTOSUGGEST_OPT_S);
            if (Utility.isSet(autoSuggRegStr)) {
                return autoSuggRegStr;
            } else {
                return regionStr;
            }
        }
    }

    private String getRepresFilter(Map pParams) {

        String regionStr = (String) pParams.get(REPRES_OPT_S);

        if (Utility.isSet(regionStr)) {
            return regionStr;
        } else {
            String autoSuggRepresStr = (String) pParams.get(REPRES_AUTOSUGGEST_OPT_S);
            if (Utility.isSet(autoSuggRepresStr)) {
                return autoSuggRepresStr;
            } else {
                return regionStr;
            }
        }
    }

    private String getCategoryFilter(Map pParams) {

        String categoryStr = (String) pParams.get(CATEGORY_OPT_S);

        if (Utility.isSet(categoryStr)) {
            return categoryStr;
        } else {
            String autoSuggCategStr = (String) pParams.get(CATEGORY_AUTOSUGGEST_OPT_S);
            if (Utility.isSet(autoSuggCategStr)) {
                return autoSuggCategStr;
            } else {
                return categoryStr;
            }
        }
    }

   private class aRecord //implements Record, java.io.Serializable
    {

        private String productName;
        private String enterpriseProductName;
        private String manufacturerSku;
        private String manufacturer;
        private String category;
        private Integer currProductUnits;
        private Integer currProductSales;
        private Integer currProductCost;
        private BigDecimal perOfTotal;
        private BigDecimal salesGrowth;
        private BigDecimal unitsGrowth;
        private Integer decMargin;
        private BigDecimal perMargin;
        private Integer priorProductUnits;
        private Integer priorProductSales;
        private Integer priorProductCost;
        private String accountName;
        private String region;
        private String industry;
        private String represName;
        private String flag;
        private String pack;
        private String uom;
        private String size;
        private String groupByFld;

        public List toList(boolean accountFl, boolean regionFl, boolean industryFl, boolean represFl) {
            ArrayList list = new ArrayList();

            String style = null;
            if (manufacturerSku != null && (manufacturerSku.equals(GRAND_TOTAL) || manufacturerSku.equals(SUB_TOTAL)) ){
              style = BOLD_STYLE;
            }

            if(accountFl) list.add(accountName);
            if(regionFl) list.add(region);
            if(industryFl) list.add(industry);
            if(represFl) list.add(represName);
            list.add(putCellStyle(style,manufacturerSku));
            String productNameS = enterpriseProductName == null ? productName : enterpriseProductName;
            list.add(putCellStyle(style,productNameS));
            list.add(putCellStyle(style,pack));
            list.add(putCellStyle(style,uom));
            list.add(putCellStyle(style,size));
            list.add(putCellStyle(style,category));
//            list.add(manufacturer);
            list.add(putCellStyle(style,currProductSales));
            list.add(putCellStyle(style,priorProductSales));
//            list.add(putCellStyle(style,perOfTotal));
//            list.add(putCellStyle(style,currProductCost));
            list.add(putCellStyle(style,salesGrowth));
            list.add(putCellStyle(style,currProductUnits));
            list.add(putCellStyle(style,priorProductUnits));
            list.add(putCellStyle(style,unitsGrowth));
//            list.add(putCellStyle(style,decMargin));
            list.add(putCellStyle(style,perMargin));
//            list.add(null);
//            list.add(null);
//            list.add(flag);
            return list;
        }

        public void init(){

         currProductUnits = new Integer(0);
         currProductSales = new Integer(0);
         currProductCost = new Integer(0);
         priorProductUnits = new Integer(0);
         priorProductSales = new Integer(0);
         priorProductCost = new Integer(0);

        }

        public void sum(aRecord record) {
          if (record.getCurrProductUnits()!=null){
            currProductUnits = new Integer(currProductUnits.intValue() +record.getCurrProductUnits().intValue());
          }
          if (record.getCurrProductSales()!=null){
            currProductSales = new Integer(currProductSales.intValue() + record.getCurrProductSales().intValue()) ;
          }
          if (record.getCurrProductCost()!=null){
            currProductCost =  new Integer(currProductCost.intValue() + record.getCurrProductCost().intValue());
          }
          if (record.getPriorProductUnits()!=null){
            priorProductUnits = new Integer(priorProductUnits.intValue() + record.getPriorProductUnits().intValue()) ;
          }
          if (record.getPriorProductSales()!=null){
            priorProductSales = new Integer(priorProductSales.intValue() + record.getPriorProductSales().intValue());
          }
          if (record.getPriorProductCost()!=null){
            priorProductCost = new Integer(priorProductCost.intValue() + record.getPriorProductCost().intValue());
          }

/*
          currProductUnits = currProductUnits + record.getCurrProductUnits() ;
          currProductSales = currProductSales + record.getCurrProductSales() ;
          currProductCost = currProductCost + record.getCurrProductCost();
          priorProductUnits = priorProductUnits + record.getPriorProductUnits() ;
          priorProductSales = priorProductSales + record.getPriorProductSales();
          priorProductCost = priorProductCost + record.getPriorProductCost();
*/
        }

        public void calculate(){

//          decMargin =  currProductSales-currProductCost;
          if (currProductSales != null && currProductCost != null){
            decMargin =  new Integer( currProductSales.intValue()-currProductCost.intValue());
          }
          BigDecimal currProductSalesDec = (currProductSales != null ) ? new BigDecimal(currProductSales.intValue()) : null;
          BigDecimal currProductUnitsDec = (currProductUnits != null ) ? new BigDecimal(currProductUnits.intValue()) : null;
          BigDecimal currProductCostDec =  (currProductCost  != null ) ? new BigDecimal(currProductCost.intValue()) : null;
          BigDecimal priorProductSalesDec =(priorProductSales != null) ? new BigDecimal(priorProductSales.intValue()) : null;
          BigDecimal priorProductUnitsDec =(priorProductUnits != null) ? new BigDecimal(priorProductUnits.intValue()) : null;

          if ((currProductSalesDec != null) && (priorProductSalesDec != null) && priorProductSalesDec.abs().compareTo(new BigDecimal(0)) > 0){
            salesGrowth = (currProductSalesDec.subtract(priorProductSalesDec)).divide(priorProductSalesDec, 4, BigDecimal.ROUND_HALF_UP);
            salesGrowth = salesGrowth.setScale(3, BigDecimal.ROUND_HALF_UP);
          }
          if ((currProductUnitsDec != null) &&  (priorProductUnitsDec != null) && priorProductUnitsDec.abs().compareTo(new BigDecimal(0)) > 0){
            unitsGrowth = (currProductUnitsDec.subtract(priorProductUnitsDec)).divide(priorProductUnitsDec, 4, BigDecimal.ROUND_HALF_UP);
            unitsGrowth = unitsGrowth.setScale(3, BigDecimal.ROUND_HALF_UP);
          }

          if ( (currProductSalesDec != null) && currProductSalesDec.abs().compareTo(new BigDecimal(0)) > 0){
            perMargin = (currProductSalesDec.subtract(currProductCostDec)).divide(currProductSalesDec, 4, BigDecimal.ROUND_HALF_UP);
            perMargin = perMargin.setScale(3, BigDecimal.ROUND_HALF_UP);
          }

       }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Integer getCurrProductUnits() {
            return currProductUnits;
        }

        public void setCurrProductUnits(Integer currProductUnits) {
            this.currProductUnits = currProductUnits;
        }

        public Integer getCurrProductSales() {
            return currProductSales;
        }

        public void setCurrProductSales(Integer currProductSales) {
            this.currProductSales = currProductSales;
        }

        public BigDecimal getPerOfTotal() {
            return perOfTotal;
        }

        public void setPerOfTotal(BigDecimal perOfTotal) {
            this.perOfTotal = perOfTotal;
        }

        public BigDecimal getSalesGrowth() {
            return salesGrowth;
        }

        public void setSalesGrowth(BigDecimal salesGrowth) {
            this.salesGrowth = salesGrowth;
        }

        public BigDecimal getUnitsGrowth() {
            return unitsGrowth;
        }

        public void setUnitsGrowth(BigDecimal unitsGrowth) {
            this.unitsGrowth = unitsGrowth;
        }

        public Integer getDecMargin() {
            return decMargin;
        }

        public void setDecMargin(Integer decMargin) {
            this.decMargin = decMargin;
        }

        public BigDecimal getPerMargin() {
            return perMargin;
        }

        public void setPerMargin(BigDecimal perMargin) {
            this.perMargin = perMargin;
        }

        public Integer getPriorProductUnits() {
            return priorProductUnits;
        }

        public void setPriorProductUnits(Integer priorProductUnits) {
            this.priorProductUnits = priorProductUnits;
        }

        public Integer getPriorProductSales() {
            return priorProductSales;
        }

        public void setPriorProductSales(Integer priorProductSales) {
            this.priorProductSales = priorProductSales;
        }

        public String getEnterpriseProductName() {
            return enterpriseProductName;
        }

        public void setEnterpriseProductName(String enterpriseProductName) {
            this.enterpriseProductName = enterpriseProductName;
        }


        public String getManufacturerSku() {
            return manufacturerSku;
        }

        public void setManufacturerSku(String manufacturerSku) {
            this.manufacturerSku = manufacturerSku;
        }

        public Integer getCurrProductCost() {
            return currProductCost;
        }

        public void setCurrProductCost(Integer currProductCost) {
            this.currProductCost = currProductCost;
        }


        public Integer getPriorProductCost() {
            return priorProductCost;
        }

        public void setPriorProductCost(Integer priorProducCost) {
            this.priorProductCost = priorProducCost;
        }
        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }
        public String getRepresName() {
            return represName;
        }

        public void setRepresName(String represName) {
            this.represName = represName;
        }
        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
        public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }
        public String getUom() {
            return uom;
        }

        public void setUom(String uom) {
            this.uom = uom;
        }
        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
        public String getGroupByFld() {
            return groupByFld;
        }

        public void setGroupByFld(String groupByFld) {
            this.groupByFld = groupByFld;
        }

    }
    public class GenericReportUserStyleDesc extends ValueObject {
      HashMap mReportUserStyle;

      public GenericReportUserStyleDesc() {
        mReportUserStyle = new HashMap();
      }

      public void setUserStyle(String pUserStyleType,
                               GenericReportStyleView pPageTitleStyle) {
        if (mReportUserStyle == null) {
          mReportUserStyle = new HashMap();
        }
        mReportUserStyle.put(pUserStyleType, pPageTitleStyle);
      }

      public GenericReportStyleView getUserStyle(String pUserStyleType) {
        return ( (mReportUserStyle != null) ?
                (GenericReportStyleView) mReportUserStyle.get(pUserStyleType) : null);
      }

      public HashMap getGenericReportUserStyleDesc() {
        return mReportUserStyle;
      }

      public void setGenericReportUserStyleDesc(HashMap pReportUserStyle) {
        mReportUserStyle = pReportUserStyle;
      }
    }

}
