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


public class DistrQuarterlyLWJDSales implements  GenericReportMulti {

    private static final Logger log = Logger.getLogger(DistrQuarterlyLWJDSales.class);

    protected static final String REPORT_NAME    = "REPORT_NAME";
    protected static final String STORE_S        = "DW_STORE_SELECT";
    protected static final String BEG_DATE_S     = "DW_BEGIN_DATE";
    protected static final String END_DATE_S     = "DW_END_DATE";
//    protected static final String REPRES_OPT_S   = "DW_DSR_OPT";

    protected static final String PRICE_COL = "LINE_AMOUNT";
//    protected static final String QTY_COL   = "QUANTITY";
//    protected static final String COST_COL  = "LINE_COST";

    protected static final String GRAND_TOTAL ="Grand Total";
    protected static final String BOLD_STYLE = "BOLD";

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection con = pCons.getReportConnection();

        String reportName = (String) pParams.get(REPORT_NAME);
        String begDateStr = (String) pParams.get(BEG_DATE_S);
        String endDateStr = (String) pParams.get(END_DATE_S);
        String storeIdStr = (String) pParams.get(STORE_S);

//        boolean represFl = Utility.isSet(represFilter);

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

/*        String subSqlFilters  =
            (represFl   ? " (select REP_NAME from DW_SALES_REP_DIM where dw.SALES_REP_DIM_ID = SALES_REP_DIM_ID ) REP_NAME, \n":"");

        String represCondStr = (!represFl) ? "" :
                               " SELECT SALES_REP_DIM_ID FROM DW_SALES_REP_DIM WHERE \n" +
                               "   UPPER(REP_NAME) like '%" + represFilter.toUpperCase().replaceAll("'", "''") + "%' \n";



        String filterCong =
            (represFl?"  and SALES_REP_DIM_ID  in ( \n" + represCondStr + ") \n" : "") ;
 */

         String subSqlJdItems =
              " select ITEM_DIM_ID from DW_ITEM_DIM where UPPER(JD_ITEM_FL) = 'TRUE' \n" ;

          String sql =
              "SELECT \n" +
              "         MAX (lw_id) lw_id,  \n" +
              "         MAX (rep_code) rep_num, \n" +
              "         MAX (rep_name) rep_name, \n" +
              "         SUM (line_amount) sales \n" +
              "    FROM dw_invoice_fact dw, dw_sales_rep_dim rep \n" +
              "    WHERE ITEM_DIM_ID in ( \n" + subSqlJdItems + " ) \n"+
              "     AND  DATE_DIM_ID in ( \n" + subSqlForCurrPeriod + " ) \n" +
              "     AND  dw.STORE_DIM_ID =   " + storeId + " \n" +
              "     AND rep.sales_rep_dim_id = dw.sales_rep_dim_id \n" +
              "GROUP BY dw.sales_rep_dim_id \n" +
              "ORDER BY upper(rep_num)" ;

        log.info("process sql:" + sql);

        Statement stmt = con.createStatement();

        int totalSum = 0;
        ResultSet rs = stmt.executeQuery(sql);
        LinkedList lines = new LinkedList();
        while (rs.next()) {
            aRecord record = new aRecord();

            String lwId = rs.getString("lw_id");
            record.setLwId(lwId);

            String repNum = rs.getString("rep_num");
            record.setRepNum(repNum);


            String repName = rs.getString("rep_name");
            record.setRepName(repName);


            Integer sales = new Integer(rs.getInt("sales"));
            totalSum += sales.intValue();
            record.setSales(sales);


            lines.add(record);


        }
        rs.close();
        stmt.close();

        ArrayList records = new ArrayList();
        // define Total record
        aRecord recordTotal = new aRecord();
        recordTotal.init();
        recordTotal.setRepNum(GRAND_TOTAL);

        for(Iterator iter=lines.iterator(); iter.hasNext();) {
          aRecord record = (aRecord) iter.next();
          recordTotal.sum(record);
          records.add(record.toList());
        }
//        recordTotal.setSales(new Integer(totalSum));
        records.add(recordTotal.toList());

        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader();
        result.setHeader(header);
        result.setTitle( getReportTitle(con, reportName, pParams));

        result.setColumnCount(header.size());
        result.setName("Quarterly LW JD Sales");
        result.setTable(records);

        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        resultV.add(result);

        return resultV;
    }

    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
 /* A */  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sales Rep Number", 0, 255, "VARCHAR2","80",false));
 /* B */  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Sales Rep Name", 0, 255, "VARCHAR2","100",false));
 /* C */  header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Total JD Sales $$", 2, 20, "NUMBER","100",false,"#,##0"));
 /* D */  header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Loyalty Works ID", 0, 255, "VARCHAR2","60",false));

        return header;
    }

    protected GenericReportColumnViewVector getReportTitle(Connection con, String pTitle, Map pParams) {
      GenericReportColumnViewVector title = new GenericReportColumnViewVector();
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",pTitle,0,255,"VARCHAR2"));
      String controlLabel = ReportingUtils.getControlLabel(STORE_S, pParams, "Store : ");
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfNames(con, this.STORE_S , pParams),0,255,"VARCHAR2"));

      String controlLabelB = ReportingUtils.getControlLabel(BEG_DATE_S, pParams, "Date Begin");
      String controlLabelE = ReportingUtils.getControlLabel(END_DATE_S, pParams, "End");
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabelB + (String) pParams.get(BEG_DATE_S) + "; " + controlLabelE + (String) pParams.get(END_DATE_S),0,255,"VARCHAR2"));

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
     String typeDim = "STORE";
     if (idsS.length() != 0) {
       try {

         String sql =
             " select " + typeDim + "_NAME from DW_" + typeDim + "_DIM where " + typeDim + "_DIM_ID \n" +
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
    String title = "JanPak Quarterly LW JD Sales" ;
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


   private class aRecord //implements Record, java.io.Serializable
    {

        private String mLwId;
        private String mRepNum;
        private String mRepName;
        private Integer mSales;

        public List toList() {
            ArrayList list = new ArrayList();

            String style = null;
            if (mRepNum.equals(GRAND_TOTAL)  ){
              style = BOLD_STYLE;
            }

            list.add(putCellStyle(style,mRepNum));
            list.add(putCellStyle(style,mRepName));
            list.add(putCellStyle(style,mSales));
            list.add(putCellStyle(style,mLwId));
            return list;
        }

        public void init(){

         mSales = new Integer(0);

        }

        public void sum(aRecord record) {
          if (record.getSales()!=null){
            mSales = new Integer(mSales.intValue() + record.getSales().intValue()) ;
          }

        }

        public void calculate(){

          BigDecimal salesDec = (mSales != null ) ? new BigDecimal(mSales.intValue()) : null;

       }

        public String getLwId() {
            return mLwId;
        }

        public void setLwId(String pLwId) {
            this.mLwId = pLwId;
        }

        public String getRepNum() {
            return mRepNum;
        }

        public void setRepNum(String pRepNum) {
            this.mRepNum = pRepNum;
        }

        public String getRepName() {
            return mRepName;
        }

        public void setRepName(String pRepName) {
            this.mRepName = pRepName;
        }


        public Integer getSales() {
            return mSales;
        }

        public void setSales(Integer pSales) {
            this.mSales = pSales;
        }

    }
}
