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
public abstract class BaseJDReport  implements GenericReportMulti {
   private static final Logger log = Logger.getLogger(BaseJDReport.class);
    /** Creates a new instance of DistributorInvoiceProfitReport */
    public BaseJDReport() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    protected static final String GROUPBY = "Group by";
    protected static final String CURRENT_NET_SALES = "Current Year Sales $";
    protected static final String PRIOR_NET_SALES = "Prior Year Sales $";
    protected static final String PROC_OF_TOTAL = "% of Total Sales %";
    protected static final String CURRENT_UNITS = "Current Yr Units";
    protected static final String PRIOR_UNITS = "Prior Yr Units";


    protected static final String NET_SALES_GROWTH = "Growth %%";
    protected static final String UNIT_GROWTH = "Unit Growth %%";
    protected static final String GRAND_TOTAL ="Grand Total";

    protected static final String CHEMICALS_TOTAL ="JohnsonDiversey Chemical Total";

    protected static final String JD_GROUP_NAME = "JD Brands";
    protected static final BigDecimal EXTREM = new BigDecimal(999999999);
    // define names of the Analitic Report Controls
    protected static final String STORE_S = "STORE_SELECT";
    protected static final String BEG_DATE_S = "DW_BEGIN_DATE";
    protected static final String END_DATE_S = "DW_END_DATE";
    protected static final String ACCOUNT_OPT_S = "ACCOUNT_OPT";
    protected static final String ACCOUNTs_OPT_S = "ACCOUNTs_OPT";
    protected static final String SITEs_OPT_S = "SITEs_OPT";
    protected static final String LOCATE_ACCOUNT_MULTI_S = "LOCATE_ACCOUNT_MULTI_OPT";
    protected static final String SITE_MULTI_OPT_S = "LOCATE_SITE_MULTI_OPT";
    protected static final String LOCATE_ITEM_OPT_S = "LOCATE_ITEM_OPT";
    protected static final String LOCATE_MANUFACTURER_OPT_S = "LOCATE_MANUFACTURER_OPT";
    protected static final String CATEGORY_OPT_S = "CATEGORY_OPT";
    protected static final String BOLD_STYLE = "BOLD";


    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {
        Connection con = pCons.getMainConnection();
        String errorMess = "No error";
        HashMap userStyles = createReportStyleDescriptor();
        GenericReportResultView result = GenericReportResultView.createValue();

        result.setTitle( getReportTitle(con, pReportData.getName(), pParams));
        result.setHeader(getReportHeader());
        result.setColumnCount(getReportHeader().size());
        result.setTable(new ArrayList());
        result.setUserStyle(userStyles);
        //====  Request for values ==== //
        try {
          ArrayList repYList = getResultOfQuery(con,  pParams );
          GenericReportResultViewVector resultV = calculateReportData (result, repYList);

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

    protected String getParamValue (Map pParams, String pControlName){
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
              totalPr = totalPr.setScale(3, BigDecimal.ROUND_HALF_UP);
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
      String controlName = null;
      String controlLabel = null;
      controlName = STORE_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Store : ");
      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
/*      String controlName = ACCOUNTs_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Accounts : " + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
      }
      controlName = SITEs_OPT_S;
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Sites : " + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
      }
*/
      controlName = LOCATE_ACCOUNT_MULTI_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Accounts");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
      }
      controlName = SITE_MULTI_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Sites");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
      }
      controlName = LOCATE_ITEM_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Items");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfItemNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
      }
      controlName = LOCATE_MANUFACTURER_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Manufacturers");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel + getListOfEntityNames(con, (String) pParams.get(controlName)),0,255,"VARCHAR2"));
      }

      controlName = CATEGORY_OPT_S;
      controlLabel = ReportingUtils.getControlLabel(controlName, pParams, "Category");
      if (pParams.containsKey(controlName) && Utility.isSet((String) pParams.get(controlName))){
        title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabel +  (String) pParams.get(controlName),0,255,"VARCHAR2"));
      }
      String controlLabelB = ReportingUtils.getControlLabel(BEG_DATE_S, pParams, "Date Begin");
      String controlLabelE = ReportingUtils.getControlLabel(END_DATE_S, pParams, "End");

      title.add(ReportingUtils.createGenericReportColumnView("java.lang.String",controlLabelB + (String) pParams.get(BEG_DATE_S) + "; " + controlLabelE + (String) pParams.get(END_DATE_S),0,255,"VARCHAR2"));

      return title;
    }

    protected String  getListOfItemNames (Connection con, Object listOfIds){
      String sql = " select SHORT_DESC from CLW_ITEM where ITEM_ID \n";
      return  getListOfNames(con, listOfIds, sql);
    }

    protected String  getListOfEntityNames (Connection con, Object listOfIds){
      String sql = " select SHORT_DESC from CLW_BUS_ENTITY where BUS_ENTITY_ID \n";
      return  getListOfNames(con, listOfIds, sql);
    }

    protected String  getListOfNames (Connection con, Object listOfIds, String sqlStr){
      IdVector ids = new IdVector();
      String idsS = null;
      if (listOfIds instanceof List) {
        ids = (IdVector)((ArrayList)listOfIds).clone();
        idsS = IdVector.toCommaString(ids);
      }
      if (listOfIds instanceof String) {
        idsS = (String) listOfIds;
      }
      StringBuffer nms = new StringBuffer();
      if (idsS.length() != 0 ){
        try {
          String sql = sqlStr + " in (" + idsS + ")";

         log.info("getListOfNames------------>SQL: " + sql);
          Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql);
          while (rs.next()) {
            String be = new String(rs.getString(1));
            if (nms.length() == 0){
              nms.append(be);
            } else {
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
    protected HashMap createReportStyleDescriptor(){
      return null;
    }


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


    protected IdVector getListOfManuf(Connection con, String pGroupName) throws Exception {

      IdVector idV = null;
      try {
        String sql =
             " select distinct BUS_ENTITY_ID from CLW_GROUP_ASSOC ga, CLW_GROUP g \n" +
             " where ga.GROUP_ID =g.GROUP_ID \n" +
             "   and g.SHORT_DESC = ? \n" +
             "   and g.GROUP_TYPE_CD = '" + RefCodeNames.GROUP_TYPE_CD.MANUFACTURER + "' \n" +
             "   and g.GROUP_STATUS_CD= '" + RefCodeNames.GROUP_STATUS_CD.ACTIVE + "'";

         log.info("getListOfManuf------------>SQL: " + sql);
         PreparedStatement stmt = con.prepareStatement(sql);
         stmt.setString(1, pGroupName);

         ResultSet rs = stmt.executeQuery();
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
      if (idV.size()== 0){
        String errorMess = "No Manufactories for Store in the Group: "+ pGroupName +".";
        throw new Exception(errorMess);
      }
       return idV;
    }

    protected HashMap getItemToTopCatMap (Connection con, int storeId) throws SQLException {
      return getItemToTopCatMap (con, storeId, null);
    }
 /*   protected HashMap getItemToTopCatMap (Connection con, int storeId, String pCategoryFilter) throws SQLException {

      int storeCatalogId = getCatalogId(con, storeId);
      IdVector topListAssoc = getTopCategoriesAssocForStore(con, storeCatalogId);

      String assocListSql = "";
      if  (topListAssoc != null && topListAssoc.size() > 0){
      assocListSql =
          "   select ITEM2_ID as TOP_CAT_ID, ITEM1_ID ACT_CAT_ID \n" +
          "   from CLW_ITEM_ASSOC \n" +
          "   where CATALOG_ID = " + storeCatalogId + " \n" +
          "     and ITEM_ASSOC_CD ='" + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "' \n" +
          "     and  ITEM2_ID in (" + IdVector.toCommaString(topListAssoc) + ") \n" +
          "     connect by PRIOR ITEM1_ID = ITEM2_ID \n" +
          "     start with ITEM_ASSOC_CD ='" + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "'\n" +
          " union \n" ;
      }

      String categoryCondStr ="";
      if (Utility.isSet(pCategoryFilter) ){
         categoryCondStr = " and UPPER(SHORT_DESC) like '%" + pCategoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
      }

      String sql =
          "select g.* , short_desc TOP_CAT_NAME \n" +
          "from CLW_ITEM i,  \n" +
          " (select distinct ITEM1_ID, TOP_CAT_ID \n" +
          "  from CLW_ITEM_ASSOC , \n" +
          "   ( " + assocListSql +
          "  select distinct  i.ITEM_ID TOP_CAT_ID, i.ITEM_ID ACT_CAT_ID \n" +
          "  from CLW_ITEM i, CLW_CATALOG_STRUCTURE cs  \n" +
          "  where cs.CATALOG_ID = " +storeCatalogId + " \n" +
          "   and i.ITEM_ID = cs.ITEM_ID  \n" +
          "   and cs.CATALOG_STRUCTURE_CD = '" + RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "' \n" +
          "   and i.ITEM_ID not in ( \n" +
          "        select ITEM1_ID \n" +
          "        from CLW_ITEM_ASSOC where CATALOG_ID = " +storeCatalogId + " \n" +
          "         and ITEM_ASSOC_CD ='" + RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "') \n" +
          ") \n" +
          " where ITEM2_ID = ACT_CAT_ID " +
          "   and ITEM_ASSOC_CD ='" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+ "' \n" +
          "   and CATALOG_ID = " + storeCatalogId + " \n" +
          " ) g \n" +
          "where i.ITEM_ID = TOP_CAT_ID  \n"  +
          categoryCondStr +
          "order by ITEM1_ID ";


      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      HashMap map = new HashMap();
      while (rs.next()) {
        String item = (rs.getString(1) != null) ? rs.getString(1) : "0";
        String topCat = (rs.getString(3) != null) ? rs.getString(3) : "0";
       map.put(item, topCat );
      }
      stmt.close();
      return map;
    }
 */
    protected HashMap getItemToTopCatMap (Connection con, int storeId, String pCategoryFilter) throws SQLException {

       int storeCatalogId = getCatalogId(con, storeId);
       IdVector topListAssoc = getTopCategoriesAssocForStore(con, storeCatalogId);


       String categoryCondStr ="";
       if (Utility.isSet(pCategoryFilter) ){
          //categoryCondStr = " and UPPER(TOP_CAT_NAME) like '%" + pCategoryFilter.toUpperCase().replaceAll("'", "''") + "%' \n";
          categoryCondStr = " and UPPER(TOP_CAT_NAME) like upper(?) \n";
       }

      String sql =
          "SELECT distinct ITEM1_ID, TOP_CAT_ID, TOP_CAT_NAME \n" +
          "  from CLW_ITEM_ASSOC , \n" +
          "  (SELECT   \n"+
          "    cs.item_id sub2_id,   \n"+
          "    Nvl(ia3.item2_id,Nvl(ia2.item2_id,cs.item_id)) TOP_CAT_ID,  \n"+
          "    (select short_desc from CLW_ITEM where item_id =   \n"+
          "    Nvl(ia3.item2_id,Nvl(ia2.item2_id,cs.item_id))) TOP_CAT_NAME  \n"+
          "    FROM clw_catalog_structure cs  \n"+
          "    left join clw_item_assoc ia2  \n"+
          "      ON cs.item_id = ia2.item1_id  \n"+
          "      AND ia2.catalog_id = cs.catalog_id  \n"+
          "      AND ia2.ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY'  \n"+
          "    left join clw_item_assoc ia3  \n"+
          "      ON ia2.item2_id = ia3.item1_id  \n"+
          "      AND ia2.catalog_id = ia3.catalog_id  \n"+
          "      AND ia3.ITEM_ASSOC_CD ='CATEGORY_PARENT_CATEGORY'  \n"+
          "    WHERE cs.catalog_id = ? \n"+
          "    AND cs.CATALOG_STRUCTURE_CD ='CATALOG_CATEGORY'  \n" +
          "  ) \n" +
         " where ITEM2_ID = sub2_id " +
         "   and ITEM_ASSOC_CD ='" + RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY+ "' \n" +
         categoryCondStr +
         "   and CATALOG_ID = " + storeCatalogId + " \n" ;

      log.info("BaseJDReport ----getItemToTopCatMap :: -------- sql: " + sql);
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setInt(1, storeCatalogId);
      if (Utility.isSet(pCategoryFilter) ){
          stmt.setString(2, "%" + pCategoryFilter + "%");
      }
      ResultSet rs = stmt.executeQuery();
      HashMap map = new HashMap();
      while (rs.next()) {
        String item = (rs.getString(1) != null) ? rs.getString(1) : "0";
        String topCat = (rs.getString(3) != null) ? rs.getString(3) : "0";
        map.put(item, topCat );
      }
      stmt.close();
      return map;
    }


    private IdVector getTopCategoriesAssocForStore(Connection con, int pStoreCatalogId) throws SQLException {
     // Get the category names alphabetically.
     String sql = "select i.item_id \n" +
         " from clw_item i, clw_catalog_structure cs  \n" +
         " where cs.catalog_id = " + pStoreCatalogId +
         " and i.item_id = cs.item_id  \n" +
         " and cs.catalog_structure_cd = '" +
         RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_CATEGORY + "' \n" +
         " and i.item_id in \n" +
         "   ( select item2_id from clw_item_assoc \n" +
         "      where catalog_id = " + pStoreCatalogId + "  \n" +
         "        and item_assoc_cd = '" +
         RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "' ) \n" +
         " and i.item_id not in \n" +
         "   ( select item1_id from clw_item_assoc \n" +
         "      where catalog_id = " + pStoreCatalogId + "  \n" +
         "        and item_assoc_cd = '" +
         RefCodeNames.ITEM_ASSOC_CD.CATEGORY_PARENT_CATEGORY + "' ) \n" +
         " order by i.item_id";
//     logDebug("CatlogInformationBean IIIIIIIIIIIIIIIIIIII sql: " + sql);

     log.info("BaseJDReport IIIIIIIIIIIIIIIIIIII sql: " + sql);
     Statement stmt = con.createStatement();
     ResultSet rs = stmt.executeQuery(sql);
     IdVector topCategoryIds = new IdVector();
     while (rs.next()) {
       Integer ti = new Integer(rs.getInt(1));
       topCategoryIds.add(ti);
     }
     stmt.close();
     return topCategoryIds;
}


    protected int getCatalogId(Connection con, int pStoreId) throws SQLException
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
        String errorMess = "Multiple active catalogs for store. Store id: " +  pStoreId;
        throw new SQLException(errorMess);
      }
      return catalogId;
    }


    protected String getTitle() {
      String title = "" ;
      return title;
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
}
