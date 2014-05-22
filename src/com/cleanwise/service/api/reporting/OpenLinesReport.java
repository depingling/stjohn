/*
 * OpenLinesReport.java
 *
 * Created on February 3, 2003, 9:47 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.ScheduleProc;
import com.cleanwise.service.api.util.DBCriteria;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.sql.*;
import java.math.BigDecimal;

/**
 * Picks up all contract items and adds year to date trade infrmation
 * @param the contract identifier
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class OpenLinesReport implements GenericReportMulti{
	private boolean showPrice = false;
	private boolean extendedVersion = false;
    private static final BigDecimal ZERO = new BigDecimal(0.00);
    private String[] prioritySkuA;
    com.cleanwise.service.api.util.ConnectionContainer mConsContainer;
    protected com.cleanwise.service.api.util.ConnectionContainer getConnectionContainer(){
        return mConsContainer;
    }
    Map mParamMap;
    protected Map getParamMap(){
        return mParamMap;
    }
    /**
     *If a subclass needs to add addtional where conditions to the main SQL select
     *statement then they should add it here.  Make sure that implementing methods
     * do not return null, instead return an empty string ("").
     */
    protected String getAdditionalWhereConds() throws SQLException{
        return "";
    }

    private boolean isSpecialAccountErpNum(String pAcctNum){
        if ("10131".equals(pAcctNum) || "10053".equals(pAcctNum)){
            return true;
        }
        return false;
    }

    /** Creates a new instance of OpenLinesReport */
    public OpenLinesReport() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
//    public GenericReportResultView process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
    public com.cleanwise.service.api.value.GenericReportResultViewVector
    process(com.cleanwise.service.api.util.ConnectionContainer pCons,
           com.cleanwise.service.api.value.GenericReportData pReportData,
           java.util.Map pParams)
    throws Exception
    {
    mConsContainer = pCons;
    mParamMap = pParams;
    GenericReportResultViewVector resultPages = new GenericReportResultViewVector();
    GenericReportResultView rankedTable = GenericReportResultView.createValue();
    resultPages.add(rankedTable);
    GenericReportResultView result = GenericReportResultView.createValue();
    resultPages.add(result);
    Connection con = pCons.getMainConnection();
    DBCriteria dbc = new DBCriteria();
    dbc.addEqualTo(CatalogDataAccess.SHORT_DESC,"Report Priority Items");
    dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                                   RefCodeNames.CATALOG_TYPE_CD.ESTIMATOR);
    String priorItemCatReq =
       CatalogDataAccess.getSqlSelectIdOnly(CatalogDataAccess.CATALOG_ID,dbc);
    dbc = new DBCriteria();
    dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID,priorItemCatReq);
    dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                            RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
    String priorityItemReq = CatalogStructureDataAccess.getSqlSelectIdOnly(
                                       CatalogStructureDataAccess.ITEM_ID, dbc);
    dbc = new DBCriteria();
    dbc.addOneOf(ItemDataAccess.ITEM_ID,priorityItemReq);
    IdVector itemSkuNums = ItemDataAccess.selectIdOnly(con,ItemDataAccess.SKU_NUM,dbc);
    prioritySkuA = new String[itemSkuNums.size()];
    int prioritySkuAInd = -1;
    for(Iterator iter=itemSkuNums.iterator(); iter.hasNext();) {
      prioritySkuAInd++;
      Integer skuI = (Integer) iter.next();
      prioritySkuA[prioritySkuAInd] = skuI.toString();
    }


    Connection repCon = pCons.getReportConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try {
        String amountS = (String) pParams.get("AMOUNT");
        int amount = 0;
        if(amountS != null && amountS.trim().length()>0) {
           try {
             amount = Integer.parseInt(amountS);
           }catch(Exception exc) {
              throw new Exception("Wrong Amount parameter format. Amount = " +amountS);
           }
        }

        showPrice = Utility.isTrue((String) pParams.get("Show Pricing"));
        extendedVersion = Utility.isTrue((String) pParams.get("Extended Version"));

        String openLinesSql =
        "SELECT "+
        "   trim(ord.customer) as Account_Erp_Num, "+
        "   ord.NI_GDS_BASE as Goods,   "+
        "   LN.item  as Item,  "+
        "   LN.ent_unit_cst as Unit_Cost ,  "+
        "   LN.po_number as Po_Number ,  "+
        "   LN.line_nbr as Line_Number ,  "+
        "   LN.order_nbr as Order_Number ,  "+
        "   replace(LN.description,',','') as Descritption, "+
        "   trim(ord.ship_to) as Ship_To,  "+
        "   LN.quantity as Quantity ,  "+
        "   (LN.quantity- LN.approve_qty) as Open_Quantity,  "+
        "   ((LN.quantity - LN.approve_qty) * LN.ent_unit_cst) as Open_Cost,  "+
        "   trim(bill_name) as bill_name "+
        "  FROM law_poline LN, law_custorder ord "+
        "  WHERE (LN.quantity-LN.approve_qty- LN.unrel_app_qty) > 0  "+
        "    AND ord.NI_GDS_BASE >= "+amount+" "+
        "    AND ord.order_nbr = LN.order_nbr  "+
        "    AND LN.cancelled_fl = 'N'  "+
        "    AND LN.closed_fl = 'N'  "+
        "    AND trim(LN.order_nbr) IS NOT NULL  "+
        "    AND ord.company = LN.company "+
	    " and not exists (select * from clw_order_item oi " +
	    " where oi.erp_po_ref_num = ln.po_number  " +
	    " and oi.item_sku_num = ln.item  " +
	    " and oi.order_item_status_cd = 'CANCELLED')" +
        getAdditionalWhereConds()+
        " ORDER BY LN.po_number ";


        String accountInfoSql =
                "SELECT poan.clw_value AS po_account_name, a.short_desc AS account_name  FROM clw_bus_entity a, (SELECT * FROM clw_property WHERE property_type_cd = 'PURCHASE_ORDER_ACCOUNT_NAME') poan WHERE"+
                " a.bus_entity_id = poan.bus_entity_id (+) AND a.bus_entity_type_cd = 'ACCOUNT' and a.erp_num = ?";

        PreparedStatement accountInfoStmt = con.prepareStatement(accountInfoSql);
        stmt = con.createStatement();
        rs = stmt.executeQuery(openLinesSql);
        ArrayList poNumVector = new ArrayList();
        String prevPoNumber = "";
        OpenLinesResultViewVector openLinesResVwV = new OpenLinesResultViewVector();
        HashMap accountInfoMap = new HashMap();
        while (rs.next() ) {
          OpenLinesResultView olrVw = OpenLinesResultView.createValue();
          String accountErpNum = rs.getString("Account_Erp_Num");
          olrVw.setAccountErpNum(accountErpNum);
          olrVw.setGoods(rs.getBigDecimal("Goods"));
          olrVw.setItem(rs.getString("Item"));
          olrVw.setUnitCost(rs.getBigDecimal("Unit_Cost"));
          String poNumber = rs.getString("Po_Number");
          if(!poNumber.equals(prevPoNumber)) {
            poNumVector.add(poNumber);
            prevPoNumber = poNumber;
          }
          olrVw.setPoNumber(poNumber);
          olrVw.setLineNumber(rs.getInt("Line_Number"));
          olrVw.setOrderNumber(rs.getInt("Order_Number"));
          olrVw.setDescritption(rs.getString("Descritption"));
          olrVw.setShipTo(rs.getString("Ship_To"));
          olrVw.setQuantity(rs.getInt("Quantity"));
          olrVw.setOpenQuantity(rs.getInt("Open_Quantity"));
          olrVw.setOpenCost(rs.getBigDecimal("Open_Cost"));
          String bill_name;
          AccountInfo ai = (AccountInfo) accountInfoMap.get(accountErpNum);
          if(ai != null){
              bill_name = ai.accountName;
          }else{
              if(accountErpNum != null){
                  accountInfoStmt.setString(1, accountErpNum);
                  accountInfoStmt.execute();
                  ResultSet accountInfoRs = accountInfoStmt.getResultSet();
                  if(accountInfoRs.next()){
                    bill_name = accountInfoRs.getString("po_account_name");
                  }else{
                      bill_name = null;
                  }
                  if(!Utility.isSet(bill_name)){
                      bill_name = accountInfoRs.getString("account_name");
                      if(!Utility.isSet(bill_name)){
                          bill_name = rs.getString("bill_name");
                      }
                  }
              }else{
                  bill_name = rs.getString("bill_name");
              }
              accountInfoMap.put(accountErpNum, new AccountInfo(bill_name));
          }
          /*
          if(bill_name!=null &&
            bill_name.trim().startsWith("JCI") &&
            !"10051".equals(accountErpNum) //do not care about JCI - JCPenney
            ) {
            olrVw.setSiteRank(9);
          }
          */
          olrVw.setAccountName(bill_name);
          openLinesResVwV.add(olrVw);
        }
        rs.close();
        rs = null;
        stmt.close();
        stmt = null;

        if(openLinesResVwV.size()==0) {//empty report
          rankedTable.setHeader(getReportHeader());
          result.setHeader(getReportHeader());
          return resultPages;
        }

        OpenLinesResultView[] openLinesResVwA = new OpenLinesResultView[openLinesResVwV.size()];
        for(int ii=0; ii<openLinesResVwA.length; ii++) {
          OpenLinesResultView olrVw = (OpenLinesResultView)   openLinesResVwV.get(ii);
          openLinesResVwA[ii] = olrVw;
        }
        openLinesResVwV = null;

        int size = poNumVector.size();
        String vendorSql =
          "Select "+
          " trim(vendor) as vendor, "+
          " po_date as po_date, "+
          " sh_city_addr5 as Ship_Name, "+
          " sh_state_prov as State, "+
          " sh_post_code as Zip_Code, "+
          " po_number "+
          " FROM  law_purchorder "+
          " WHERE  trim(vendor) IS NOT NULL "+
          " AND cancelled_fl = 'N' "+
          " AND po_number IN (";

        String poCritS = "";
        for(int ii=0; ii<size; ii++) {
          if(poCritS.length()>0) poCritS+=",";
          poCritS += "'"+poNumVector.get(ii)+"'";
          if((ii>0 && (ii%900)==0) || ii==size-1){
            String vendorSqlWrk = vendorSql + poCritS+")";
            vendorSqlWrk += " ORDER BY po_number ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(vendorSqlWrk);
            int ind = 0;
            while (rs.next() ) {
              String vendor = rs.getString("vendor");
              Date poDate = rs.getDate("po_date");
              String shipName = rs.getString("Ship_Name");
              String state = rs.getString("State");
              String zipCode = rs.getString("Zip_Code");
              String poNumber = rs.getString("po_number");
              for(; ind<openLinesResVwA.length; ind++) {
                OpenLinesResultView olrVw = (OpenLinesResultView) openLinesResVwA[ind];
                int cr = poNumber.compareTo(olrVw.getPoNumber());
                if(cr==0) {
                  olrVw.setVendor(vendor);
                  olrVw.setPoDate(poDate);
                  olrVw.setShipName(shipName);
                  olrVw.setState(state);
                  olrVw.setZipCode(zipCode);
                  continue;
                }
                if(cr<0) break;
              }
            }
          rs.close();
          rs = null;
          stmt.close();
          stmt = null;
          poCritS = "";
          }
        }
        //Remove no vendor records
        int count = 0;
        for(int ii=0; ii<openLinesResVwA.length; ii++) {
          String vendor = openLinesResVwA[ii].getVendor();
          if(vendor!=null && vendor.length()>0) count++;
        }
        OpenLinesResultView[] olrVwA = new OpenLinesResultView[count];
        count = 0;
        for(int ii=0; ii<openLinesResVwA.length; ii++) {
          String vendor = openLinesResVwA[ii].getVendor();
          if(vendor!=null && vendor.length()>0) {
            olrVwA[count] = openLinesResVwA[ii];
            count++;
          }
        }
        if(count==0) {
          rankedTable.setHeader(getReportHeader());
          result.setHeader(getReportHeader());
          return resultPages;
        }
        openLinesResVwA = olrVwA;
        olrVwA = null;

        //Order by account and site
        if(openLinesResVwA.length>1) {
          for(int ii=0; ii<openLinesResVwA.length-1; ii++) {
            boolean breakFl = true;
            for(int jj=0; jj<openLinesResVwA.length-ii-1;jj++) {
              OpenLinesResultView olrVw1 = openLinesResVwA[jj];
              OpenLinesResultView olrVw2 = openLinesResVwA[jj+1];
              boolean moveFl = false;
              int rcAcct = olrVw1.getAccountErpNum().compareTo(olrVw2.getAccountErpNum());
              if(rcAcct>0) moveFl = true;
              if(rcAcct==0) {
                int rcSite = olrVw1.getShipTo().compareTo(olrVw2.getShipTo());
                if(rcSite>0) moveFl = true;
              }
              if(moveFl) {
                openLinesResVwA[jj] = olrVw2;
                openLinesResVwA[jj+1] = olrVw1;
                breakFl = false;
              }
            }
            if(breakFl) break;
          }
        }

        OpenLinesResultView olrVw = null;
        //Get site ids
        ArrayList accountErpV = new ArrayList();
        ArrayList siteErpCritV = new ArrayList();
        count = 0;
        String siteErpCrit = "";
        olrVw = openLinesResVwA[0];
        String prevAcctErpNum = olrVw.getAccountErpNum();
        String prevSiteErpNum = olrVw.getShipTo();
        for(int ii=0; ii<openLinesResVwA.length; ii++) {
          olrVw = openLinesResVwA[ii];
          if(!prevAcctErpNum.equals(olrVw.getAccountErpNum()) || count==900) {
            accountErpV.add(prevAcctErpNum);
            if(count>0) siteErpCrit += ",";
            siteErpCrit +="'"+prevSiteErpNum+"'";
            siteErpCritV.add(siteErpCrit);
            prevAcctErpNum = olrVw.getAccountErpNum();
            count=0;
            siteErpCrit = "";
            prevSiteErpNum = olrVw.getShipTo();
          }
          if(!prevSiteErpNum.equals(olrVw.getShipTo())){
            if(count>0) siteErpCrit += ",";
            siteErpCrit +="'"+prevSiteErpNum+"'";
            prevSiteErpNum = olrVw.getShipTo();
            count++;
          }
        }
        accountErpV.add(prevAcctErpNum);



        if(count>0) siteErpCrit += ",";
        siteErpCrit +="'"+prevSiteErpNum+"'";
        siteErpCritV.add(siteErpCrit);
        /*for(int ii=0; ii<accountErpV.size(); ii++) {
          String acctErpNum = (String) accountErpV.get(ii);
          siteErpCrit = (String) siteErpCritV.get(ii);
          String siteIdSql =
           "SELECT "+
            " be.bus_entity_id as site_id, "+
            " trim(be.erp_num) as site_erp_num "+
            " FROM clw_bus_entity be, clw_bus_entity_assoc bea, clw_bus_entity be1 "+
            " WHERE be.bus_entity_type_cd='SITE' "+
            " AND be.erp_num IN ("+siteErpCrit+")"+
            " AND bea.bus_entity1_id = be.bus_entity_id "+
            " AND bea.bus_entity2_id = be1.bus_entity_id "+
            " AND bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' "+
            " AND be1.bus_entity_type_cd='ACCOUNT' "+
            " AND be1.erp_num = '"+acctErpNum+"'"+
            " ORDER BY be1.erp_num, be.erp_num ";
           stmt = con.createStatement();
           rs = stmt.executeQuery(siteIdSql);
           int ind = 0;
           while (rs.next() ) {
             int siteId = rs.getInt("site_id");
             String siteErpNum = rs.getString("site_erp_num");
             for(; ind<openLinesResVwA.length; ind++) {
               olrVw = (OpenLinesResultView) openLinesResVwA[ind];
               int cr = acctErpNum.compareTo(olrVw.getAccountErpNum());
               int cr1 = siteErpNum.compareTo(olrVw.getShipTo());
               if(cr==0 && cr1==0) {
                 olrVw.setSiteId(siteId);
                 continue;
               }
               if(cr<0 || cr==0 && cr1<0) break;
             }
           }
           rs.close();
           rs = null;
           stmt.close();
           stmt = null;
         }*/

        //Target facility
        count = 0;
        siteErpCrit = "";
        olrVw = openLinesResVwA[0];
        prevAcctErpNum = olrVw.getAccountErpNum();
        prevSiteErpNum = olrVw.getShipTo();
        for(int ii=0; ii<openLinesResVwA.length; ii++) {
          olrVw = openLinesResVwA[ii];
          if(!prevAcctErpNum.equals(olrVw.getAccountErpNum()) || count==900) {
            accountErpV.add(prevAcctErpNum);
            if(count>0) siteErpCrit += ",";
            siteErpCrit +="'"+prevSiteErpNum+"'";
            siteErpCritV.add(siteErpCrit);
            prevAcctErpNum = olrVw.getAccountErpNum();
            count=0;
            siteErpCrit = "";
            prevSiteErpNum = olrVw.getShipTo();
          }
          if(!prevSiteErpNum.equals(olrVw.getShipTo())){
            if(count>0) siteErpCrit += ",";
            siteErpCrit +="'"+prevSiteErpNum+"'";
            prevSiteErpNum = olrVw.getShipTo();
            count++;
          }
        }
        accountErpV.add(prevAcctErpNum);
        if(count>0) siteErpCrit += ",";
        siteErpCrit +="'"+prevSiteErpNum+"'";
        siteErpCritV.add(siteErpCrit);
        for(int ii=0; ii<accountErpV.size(); ii++) {
          String acctErpNum = (String) accountErpV.get(ii);
          siteErpCrit = (String) siteErpCritV.get(ii);
          String targetFacilSql =
          "SELECT "+
            "\"Target Facility\" as Target_Facility, "+
            " site.acct_erp_num, "+
            " site.site_erp_num "+
            " FROM crt_site_data sd, crt_site site "+
            "  WHERE site.site_id = sd.site_id "+
            " AND site.acct_erp_num = '"+acctErpNum+"'"+
            " AND site.site_erp_num IN ("+siteErpCritV.get(ii)+")" +
            " ORDER BY site.acct_erp_num, site.site_erp_num ";
           stmt = repCon.createStatement();
           rs = stmt.executeQuery(targetFacilSql);
           int ind = 0;
           while (rs.next() ) {
             String targetFacility = rs.getString("Target_Facility");
             String siteErpNum = rs.getString("site_erp_num");
             for(; ind<openLinesResVwA.length; ind++) {
               olrVw = (OpenLinesResultView) openLinesResVwA[ind];
               int cr = acctErpNum.compareTo(olrVw.getAccountErpNum());
               int cr1 = siteErpNum.compareTo(olrVw.getShipTo());
               if(cr==0 && cr1==0) {
                 olrVw.setTargetFacility(targetFacility);
                 continue;
               }
               if(cr<0 || cr==0 && cr1<0) break;
             }
           }
           rs.close();
           rs = null;
           stmt.close();
           stmt = null;
         }

         //Get vendor name
        boolean moveFl = false;
        boolean breakFl = true;
        if(openLinesResVwA.length>1) { //Order by vendor and item
           for(int ii=0; ii<openLinesResVwA.length-1; ii++) {
             breakFl = true;
             for(int jj=0; jj<openLinesResVwA.length-ii-1;jj++) {
               OpenLinesResultView olrVw1 = openLinesResVwA[jj];
               OpenLinesResultView olrVw2 = openLinesResVwA[jj+1];
               int rc = olrVw1.getVendor().compareTo(olrVw2.getVendor());
               moveFl = false;
               if(rc>0) moveFl = true;
               if(rc==0) {
                 int rc1 = olrVw1.getItem().compareTo(olrVw2.getItem());
                 if(rc1>0) moveFl = true;
               }
               if(moveFl){
                 openLinesResVwA[jj] = olrVw2;
                 openLinesResVwA[jj+1] = olrVw1;
                 breakFl = false;
               }
             }
             if(breakFl) break;
           }
         }
         count = 0;
         String vendorCrit = "";
         olrVw = openLinesResVwA[0];
         String prevVendor = olrVw.getVendor();
         for(int ii=0; ii<openLinesResVwA.length; ii++) {
           olrVw = openLinesResVwA[ii];
           if(!prevVendor.equals(olrVw.getVendor())){
             if(vendorCrit.length()>0) vendorCrit += ",";
             vendorCrit += "'"+prevVendor.trim()+"'";
             prevVendor = olrVw.getVendor();
           }
         }
         if(vendorCrit.length()>0) vendorCrit += ",";
         vendorCrit += "'"+prevVendor.trim()+"'";
         String vendorNameSql =
           "SELECT "+
            " bus_entity_id as vendor_id, "+
            " short_desc as Vendor_Name, "+
            " trim(erp_num) as vendor "+
            " FROM clw_bus_entity "+
            " WHERE bus_entity_type_cd='DISTRIBUTOR' "+
            " AND erp_num IN ("+vendorCrit+")"+
            " ORDER BY erp_num ";
         stmt = con.createStatement();
         rs = stmt.executeQuery(vendorNameSql);
         int ind = 0;
         String vendorIdCrit = "";
         while (rs.next() ) {
           String vendorName = rs.getString("Vendor_Name");
           int vendorId = rs.getInt("vendor_id");
           if(vendorIdCrit.length()>0) vendorIdCrit += ",";
           vendorIdCrit += vendorId;
           String vendor = rs.getString("vendor");
           for(; ind<openLinesResVwA.length; ind++) {
             olrVw = (OpenLinesResultView) openLinesResVwA[ind];
             int cr = vendor.compareTo(olrVw.getVendor());
             if(cr==0) {
               olrVw.setVendorId(vendorId);
               olrVw.setVendorName(vendorName);
               continue;
             }
             if(cr<0) break;
           }
         }
         rs.close();
         rs = null;
         stmt.close();
         stmt = null;

         //Vendor items
         String vendorItemsSql =
           "SELECT "+
           " ITEM , "+
           " trim(VENDOR) as vendor, "+
           " replace(ven_item,',','')  as VEN_ITEM "+
           //" VEN_ITEM_DESC, "+
           //" VBUY_UOM, "+
           //" VPRI_UOM "+
           " FROM law_poitemven "+
           " WHERE trim(vendor) in ("+vendorCrit+")"+
           " ORDER BY vendor, item ";
         stmt = con.createStatement();
         rs = stmt.executeQuery(vendorItemsSql);
         ind = 0;
         while (rs.next() ) {
           String vendor = rs.getString("VENDOR");
           String item = rs.getString("ITEM");
           String venItem = rs.getString("VEN_ITEM");
           //String venitemDesc = rs.getString("VEN_ITEM_DESC");
           //String vbuyUom = rs.getString("VBUY_UOM");
           //String vpriUom = rs.getString("VPRI_UOM");
           for(; ind<openLinesResVwA.length; ind++) {
             olrVw = openLinesResVwA[ind];
             int cr = vendor.compareTo(olrVw.getVendor());
             int cr1 = item.compareTo(olrVw.getItem());
             if(cr==0 && cr1==0) {
               olrVw.setVendorItem(venItem);
               continue;
             }
             if(cr<0 || cr==0&&cr1<0) break;
           }
         }
         rs.close();
         rs = null;
         stmt.close();
         stmt = null;
         //Delivery schedules
         if(openLinesResVwA.length>1) { //Order by vendor and zip code
           for(int ii=0; ii<openLinesResVwA.length-1; ii++) {
             breakFl = true;
             for(int jj=0; jj<openLinesResVwA.length-ii-1;jj++) {
               OpenLinesResultView olrVw1 = openLinesResVwA[jj];
               OpenLinesResultView olrVw2 = openLinesResVwA[jj+1];
               int vendorId1 = olrVw1.getVendorId();
               int vendorId2 = olrVw2.getVendorId();
               moveFl = false;
               if(vendorId1>vendorId2) moveFl = true;
               if(vendorId1==vendorId2) {
                 int rc1 = olrVw1.getZipCode().compareTo(olrVw2.getZipCode());
                 if(rc1>0) moveFl = true;
               }
               if(moveFl){
                 openLinesResVwA[jj] = olrVw2;
                 openLinesResVwA[jj+1] = olrVw1;
                 breakFl = false;
               }
             }
             if(breakFl) break;
           }
         }

         String vendorSchedSql =
         "SELECT "+
          " s.bus_entity_id as vendor_id, "+
          " s.schedule_id, "+
          " sd.value as zip_code "+
          " FROM clw_schedule s, clw_schedule_detail sd "+
          " WHERE  s.schedule_status_cd = 'ACTIVE' "+
	  " AND s.schedule_id = sd.schedule_id "+
	  " AND sd.schedule_detail_cd = 'ZIP_CODE' "+
          " AND s.bus_entity_id IN ("+vendorIdCrit+")" +
          " ORDER BY s.bus_entity_id, sd.value ";
         stmt = con.createStatement();
         rs = stmt.executeQuery(vendorSchedSql);
         ind = 0;
         while (rs.next() ) {
           int vendorId = rs.getInt("vendor_id");
           String zipCode = rs.getString("zip_code");
           int scheduleId = rs.getInt("schedule_id");
           for(; ind<openLinesResVwA.length; ind++) {
             olrVw = openLinesResVwA[ind];
             int vId = olrVw.getVendorId();
             int cr = vendorId - vId;
             String zp = olrVw.getZipCode();
             if(zp==null) zp = "";
             zp = zp.substring(0,5);
             int cr1 = zipCode.compareTo(zp);
             if(cr==0 && cr1==0) {
               olrVw.setScheduleId(scheduleId);
               continue;
             }
             if(cr<0 || cr==0&&cr1<0) break;
           }
         }
         rs.close();
         rs = null;
         stmt.close();
         stmt = null;

         //Order by site id
         if(openLinesResVwA.length>1) { //Order by vendor and zip code
           for(int ii=0; ii<openLinesResVwA.length-1; ii++) {
             breakFl = true;
             for(int jj=0; jj<openLinesResVwA.length-ii-1;jj++) {
               OpenLinesResultView olrVw1 = openLinesResVwA[jj];
               OpenLinesResultView olrVw2 = openLinesResVwA[jj+1];
               int siteId1 = olrVw1.getSiteId();
               int siteId2 = olrVw2.getSiteId();
               moveFl = false;
               if(siteId1>siteId2) {
                 openLinesResVwA[jj] = olrVw2;
                 openLinesResVwA[jj+1] = olrVw1;
                 breakFl = false;
               }
             }
             if(breakFl) break;
           }
         }
         //Set site rank
         String sql =
         "select be.bus_entity_id, to_number(trim(clw_value)) rank "+
         " from clw_bus_entity be, clw_property prop "+
         " where be.bus_entity_id = prop.bus_entity_id  "+
         " and prop.short_desc = 'TARGET_FACILITY_RANK' "+
         " and trim(prop.clw_value) in ('9','10') "+
         " and be.erp_num is not null "+
         " and be.bus_entity_type_cd = 'SITE' "+
         " order by be.bus_entity_id ";
         stmt = con.createStatement();
         rs = stmt.executeQuery(sql);
         ind = 0;
         while (rs.next() ) {
           int siteId = rs.getInt("bus_entity_id");
           int rank  = rs.getInt("rank");
           for(; ind<openLinesResVwA.length; ind++) {
             olrVw = openLinesResVwA[ind];
             int sId = olrVw.getSiteId();
             int cr = siteId - sId;
             if(cr==0) {
               olrVw.setSiteRank(rank);
               continue;
             }
             if(cr<0) break;
           }
         }
         rs.close();
         rs = null;
         stmt.close();
         stmt = null;

         //Site schedule
         int prevSiteId = openLinesResVwA[0].getSiteId();
         String siteIdCrit = "";
         ArrayList siteIdCritV = new ArrayList();
         count = 0;
         for(int ii=0; ii<openLinesResVwA.length; ii++) {
           olrVw = openLinesResVwA[ii];
           int siteId = olrVw.getSiteId();
           if(siteId!=prevSiteId) {
             if(count>0) siteIdCrit += ",";
             siteIdCrit += prevSiteId;
             prevSiteId = siteId;
             count++;
           }
           if(count==900) {
              siteIdCritV.add(siteIdCrit);
              siteIdCrit = "";
              count = 0;
           }
         }
         if(count>0) siteIdCrit += ",";
         siteIdCrit += prevSiteId;
         siteIdCritV.add(siteIdCrit);
         ind = 0;
         for(int ii=0; ii<siteIdCritV.size(); ii++) {
           siteIdCrit = (String) siteIdCritV.get(ii);
           String siteScheduleSql =
             "SELECT "+
             " clw_value as schedule, "+
             " bus_entity_id as site_id "+
             " FROM clw_property "+
             " WHERE property_type_cd = 'DELIVERY SCHEDULE' "+
             "  AND bus_entity_id   IN ("+siteIdCrit+") "+
             " ORDER BY bus_entity_id ";
           stmt = con.createStatement();
           rs = stmt.executeQuery(siteScheduleSql);
           while (rs.next() ) {
             int siteId = rs.getInt("site_id");
             String siteSchedule = rs.getString("schedule");
             for(; ind<openLinesResVwA.length; ind++) {
               olrVw = openLinesResVwA[ind];
               int sId = olrVw.getSiteId();
               int cr = siteId - sId;
               if(cr==0) {
                 olrVw.setSiteSchedule(siteSchedule);
                 continue;
               }
               if(cr<0) break;
             }
           }
           rs.close();
           rs = null;
           stmt.close();
           stmt = null;

         }
         //Delivery dates. Sort by schedule id and site schedule
        if(openLinesResVwA.length>1) {
           for(int ii=0; ii<openLinesResVwA.length-1; ii++) {
             breakFl = true;
             for(int jj=0; jj<openLinesResVwA.length-ii-1;jj++) {
               OpenLinesResultView olrVw1 = openLinesResVwA[jj];
               OpenLinesResultView olrVw2 = openLinesResVwA[jj+1];
               int schedId1 = olrVw1.getScheduleId();
               int schedId2 = olrVw2.getScheduleId();
               moveFl = false;
               if(schedId1>schedId2) moveFl = true;
               if(schedId1==schedId2){
                 String siteSched1 = olrVw1.getSiteSchedule();
                 String siteSched2 = olrVw2.getSiteSchedule();
                 if(siteSched1!=null && siteSched2!=null &&
                    siteSched1.compareTo(siteSched2)>0){
                 moveFl = true;
                 }
               }
               if(moveFl) {
                 openLinesResVwA[jj] = olrVw2;
                 openLinesResVwA[jj+1] = olrVw1;
                 breakFl = false;
               }
             }
             if(breakFl) break;
           }
         }

         Distributor distBean =
                    APIAccess.getAPIAccess().getDistributorAPI();
         int prevScheduleId = 0;
         String prevSiteSchedule = "";
         ScheduleProc scheduleProc = null;
         for(int ii=0; ii<openLinesResVwA.length; ii++) {
           olrVw = openLinesResVwA[ii];
           int sId = olrVw.getScheduleId();
           String sSched = olrVw.getSiteSchedule();
           ScheduleJoinView sjVw = null;
           SiteDeliveryScheduleView sds = null;
           if(sId !=0 &&
              sSched!=null &&
              sSched.trim().length()>0) {
             boolean newScheduleFl = false;
             if(prevScheduleId != sId) {
               newScheduleFl = true;
               sjVw = distBean.getDeliveryScheduleById(sId);
             }
             if(!sSched.equals(prevSiteSchedule)){
               newScheduleFl = true;
               StringTokenizer st = new StringTokenizer(sSched, ":");
               sds = SiteDeliveryScheduleView.createValue();
               for (int tt = 0; st.hasMoreTokens(); tt++ ) {
                 String str =   st.nextToken();
                 if ( tt == 0 ) {
                   sds.setSiteScheduleType(str);
                 } else if ( str.startsWith("Last")) {
                   sds.setLastWeekofMonth(true);
                 } else if(str.equals("1")) {
                   sds.setWeek1ofMonth(true);
                 } else if(str.equals("2")) {
                   sds.setWeek2ofMonth(true);
                 } else if(str.equals("3")) {
                   sds.setWeek3ofMonth(true);
                 } else if(str.equals("4")) {
                   sds.setWeek4ofMonth(true);
                 }
               }
             }
             if(newScheduleFl) {
               scheduleProc = new ScheduleProc(sjVw,sds);
               scheduleProc.initSchedule();
             }
             GregorianCalendar deliveryCal = scheduleProc.getOrderDeliveryDate(olrVw.getPoDate(),olrVw.getPoDate());
             if(deliveryCal!=null) {
               olrVw.setDeliveryDate(deliveryCal.getTime());
             }
           }
         }
         //Order by po date
        if(openLinesResVwA.length>1) {
           for(int ii=0; ii<openLinesResVwA.length-1; ii++) {
             breakFl = true;
             for(int jj=0; jj<openLinesResVwA.length-ii-1;jj++) {
               OpenLinesResultView olrVw1 = openLinesResVwA[jj];
               OpenLinesResultView olrVw2 = openLinesResVwA[jj+1];
               Date poDate1 = olrVw1.getPoDate();
               Date poDate2 = olrVw2.getPoDate();
               moveFl = false;
               int rc = -1;
               if(poDate1==null && poDate2==null) {
                 rc = 0;
               } else if(poDate1!=null && poDate2==null) {
                 moveFl = true;
               } else if(poDate1!=null && poDate2!=null) {
                 rc =poDate1.compareTo(poDate2);
                 if(rc>0) moveFl = true;
               }
               if(rc==0) {
                 String poNumber1 = olrVw1.getPoNumber();
                 String poNumber2 = olrVw2.getPoNumber();
                 if(poNumber1==null) poNumber1="";
                 if(poNumber2==null) poNumber1="";
                 rc = poNumber1.compareTo(poNumber2);
                 if(rc>0) moveFl = true;
               }
               if(rc==0) {
                 int lineNumber1 = olrVw1.getLineNumber();
                 int lineNumber2 = olrVw1.getLineNumber();
                 rc = lineNumber1 - lineNumber2;
                 if(rc>0) moveFl = true;
               }
               if(moveFl) {
                 openLinesResVwA[jj] = olrVw2;
                 openLinesResVwA[jj+1] = olrVw1;
                 breakFl = false;
               }
             }
             if(breakFl) break;
           }
         }

        {
            //get existing vendor invoices
            java.util.HashMap lInvoiceMap = new java.util.HashMap();
            String venInvSql = "select invoice from law_apinvoice where po_num=?";
            PreparedStatement venInvStmt = con.prepareStatement(venInvSql);

            for(int ii=0; ii<openLinesResVwA.length; ii++) {
                OpenLinesResultView line = openLinesResVwA[ii];
                if(lInvoiceMap.containsKey(line.getPoNumber())){
                    line.setExsistingVendorInvoicesAgainstPo((List) lInvoiceMap.get(line.getPoNumber()));
                }else{
                    //venInvStmt.setString(1, Utility.padLeft(line.getPoNumber(),' ',7));
                    venInvStmt.setString(1, line.getPoNumber());
                    rs = venInvStmt.executeQuery();
                    ArrayList invoiceNums = new ArrayList();
                    while (rs.next() ) {
                        String invRes = rs.getString("invoice");
                        if (invRes!= null){
                            invRes = invRes.trim();
                        }
                        invoiceNums.add(invRes);
                    }
                    line.setExsistingVendorInvoicesAgainstPo(invoiceNums);
                    lInvoiceMap.put(line.getPoNumber(), invoiceNums);
                }
            }
            venInvStmt.close();
            venInvStmt = null;
        }
        {
            //get clw_order information
            java.util.HashMap lOrderMap = new java.util.HashMap();
            String orderSql = "select ORDER_ID,ORDER_STATUS_CD,order_site_name, " +
                    " nvl(revised_order_date,original_order_date) as approved_date, site_id " +
                    " from clw_order where erp_order_num=?";
            PreparedStatement orderStmt = con.prepareStatement(orderSql);

            for(int ii=0; ii<openLinesResVwA.length; ii++) {
                OpenLinesResultView line = openLinesResVwA[ii];
                Integer lOrderNum = new Integer(line.getOrderNumber());
                if(lOrderMap.containsKey(lOrderNum)){
                    OpenLinesResultView oldLine = (OpenLinesResultView)lOrderMap.get(lOrderNum);
                    line.setOrderStatusCd(oldLine.getOrderStatusCd());
                    line.setApprovedDate(oldLine.getApprovedDate());
                    line.setOrderSiteName(oldLine.getOrderSiteName());
                    line.setOrderId(oldLine.getOrderId());
                    line.setSiteId(oldLine.getSiteId());
                }else{
                    //venInvStmt.setString(1, Utility.padLeft(line.getPoNumber(),' ',7));
                    orderStmt.setString(1, lOrderNum.toString());
                    rs = orderStmt.executeQuery();
                    String orderStatus = "";
                    String orderSiteName = "";
                    int orderId = 0;
                    int siteId = 0;
                    Date approvedDate = null;
                    if (rs.next() ) {
                        orderId = rs.getInt("ORDER_ID");
                        orderStatus = rs.getString("ORDER_STATUS_CD");
                        approvedDate = rs.getDate("approved_date");
                        orderSiteName = rs.getString("order_site_name");
                        siteId = rs.getInt("SITE_ID");
                    }
                    rs.close();
                    line.setOrderId(orderId);
                    line.setOrderStatusCd(orderStatus);
                    line.setApprovedDate(approvedDate);
                    line.setOrderSiteName(orderSiteName);
                    if(line.getSiteId() == 0){
                        line.setSiteId(siteId);
                    }
                    lOrderMap.put(lOrderNum, line);
                }
            }
            orderStmt.close();
            orderStmt = null;
        }
        long time = System.currentTimeMillis();
        {
            //get the order item information
            String orderItemSql =
                    "select order_item_id, order_item_status_cd,freight_handler_id, dist_uom_conv_cost, " +
                    "dist_item_quantity, DIST_UOM_CONV_MULTIPLIER, erp_po_num, erp_po_line_num, erp_po_date,  " +
                    "outbound_po_num, cust_contract_price "+
                    "from clw_order_item "+
                "where order_id = ? and item_sku_num = ?";
            HashMap freightHandlerMap = new HashMap();
            PreparedStatement orderItmStmt = con.prepareStatement(orderItemSql);
            for(int ii=0; ii<openLinesResVwA.length; ii++) {
                OpenLinesResultView line = openLinesResVwA[ii];
                orderItmStmt.setInt(1, line.getOrderId());
                String item = line.getItem();
                if(item != null){
                    item = item.trim();
                }
                orderItmStmt.setString(2, item);
                rs = orderItmStmt.executeQuery();
                if(rs.next()){
                    int fhid = rs.getInt("freight_handler_id");
                    String status = rs.getString("order_item_status_cd");
                    int orderItemId = rs.getInt("order_item_id");
                    BigDecimal distUomConvCost = rs.getBigDecimal("dist_uom_conv_cost");
                    int distUomConvQty = rs.getInt("dist_item_quantity");
                    String webErpPoNum = rs.getString("erp_po_num");
                    int webErpPoLineNum = rs.getInt("erp_po_line_num");
                    Date webErpPoDate = rs.getDate("erp_po_date");
                    String outboundPoNum = rs.getString("outbound_po_num");
                    BigDecimal distUomConvMultiplier = rs.getBigDecimal("DIST_UOM_CONV_MULTIPLIER");
                    BigDecimal custContractPrice = rs.getBigDecimal("cust_contract_price");
                    if(distUomConvMultiplier == null || distUomConvMultiplier.compareTo(ZERO)==0){
                        distUomConvQty = line.getQuantity();
                        distUomConvCost = line.getUnitCost();
                        line.setDistUomConvOpenQty(line.getOpenQuantity());
                    }else{
                        BigDecimal currOpenQty = new BigDecimal(line.getOpenQuantity());
                        currOpenQty = currOpenQty.multiply(distUomConvMultiplier);
                        currOpenQty = currOpenQty.setScale(0, BigDecimal.ROUND_HALF_UP); //if this does not round perfectly
                                                                    //it is technically an error condition,
                                                                    //but the error has already happened and
                                                                    //we can't do anything about it now
                        line.setDistUomConvOpenQty(currOpenQty.intValue());
                    }
                    line.setFreightHandler("");
                    if(rs.next()){
                        line.setOrderItemStatusCd("MULTIPLE");
                    }else{
                        if(fhid > 0){
                            Integer fhKey = new Integer(fhid);
                            if(freightHandlerMap.containsKey(fhKey)){
                                BusEntityData fh = (BusEntityData) freightHandlerMap.get(fhKey);
                                line.setFreightHandler(fh.getShortDesc());
                            }else{
                                BusEntityData fh = BusEntityDataAccess.select(con,fhid);
                                if(fh != null){
                                    line.setFreightHandler(fh.getShortDesc());
                                    freightHandlerMap.put(fhKey,fh);
                                }
                            }
                        }
                        if(Utility.isSet(status)){
                            line.setOrderItemStatusCd(status);
                        }else{
                            line.setOrderItemStatusCd("PENDING");
                        }
                        line.setOrderItemId(orderItemId);
                        line.setDistUomConvQty(distUomConvQty);
                        line.setDistUomConvCost(distUomConvCost);
                        line.setWebPoNumber(webErpPoNum);
                        line.setWebLineNumber(webErpPoLineNum);
                        line.setWebPoDate(webErpPoDate);
                        line.setOutboundPoNum(outboundPoNum);
                        line.setUnitPrice(custContractPrice);
                        if(custContractPrice != null){
                        	line.setOpenPrice(custContractPrice.multiply(new BigDecimal(line.getOpenQuantity())));
                        }
                    }
                }else{
                    line.setOrderItemStatusCd("ITEM NOT FOUND");
                }
                rs.close();
            }
            orderItmStmt.close();
            orderItmStmt = null;
        }
        {
            //get order property info
            java.util.HashMap lOrderPropMap = new java.util.HashMap();
            java.util.HashMap lOrderItemPropMap = new java.util.HashMap();
            String orderPropSql = "select ORDER_ITEM_ID, ORDER_PROPERTY_TYPE_CD,CLW_VALUE, SHORT_DESC from clw_order_property "+
                "where ORDER_PROPERTY_STATUS_CD='ACTIVE' and order_id=?";
            PreparedStatement orderPropStmt = con.prepareStatement(orderPropSql);

            for(int ii=0; ii<openLinesResVwA.length; ii++) {
                OpenLinesResultView line = openLinesResVwA[ii];
                Properties props;
                Integer lOrderId = new Integer(line.getOrderId());

                if(lOrderPropMap.containsKey(lOrderId)){
                    props = (Properties) lOrderPropMap.get(lOrderId);
                }else{
                    orderPropStmt.setInt(1, lOrderId.intValue());
                    rs = orderPropStmt.executeQuery();
                    String billingOrder = "";
                    props = new Properties();

                    while (rs.next() ) {
                        String type = rs.getString("ORDER_PROPERTY_TYPE_CD");
                        String type2 = rs.getString("SHORT_DESC");
                        String value = rs.getString("CLW_VALUE");
                        if(type != null && value!=null){
                        	props.setProperty(type,value);
                        }
                        int orderItemId = rs.getInt("ORDER_ITEM_ID");
                        if(orderItemId > 0){
                        	Integer oikey = new Integer(orderItemId);
                        	Properties singleOrderItemProps;
                        	if(lOrderItemPropMap.containsKey(oikey)){
                        		singleOrderItemProps = (Properties) lOrderItemPropMap.get(oikey);
                        	}else{
                        		singleOrderItemProps = new Properties();
                        		lOrderItemPropMap.put(oikey,singleOrderItemProps);
                        	}
                        	if(type != null && value != null){
                        		if(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE.equals(type) && type2 != null){
                        			singleOrderItemProps.setProperty(type2,value);
                        		}else{
                        			singleOrderItemProps.setProperty(type,value);
                        		}
                        	}
                        }
                    }
                    rs.close();
                    lOrderPropMap.put(lOrderId, props);
                }
                line.setBillingOnlyOrder(props.getProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER));


                Properties iProps = (Properties) lOrderItemPropMap.get(new Integer(line.getOrderItemId()));
                if(iProps != null){
                	line.setOpenLineStatus(iProps.getProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.OPEN_LINE_STATUS_CD));
                }
                if(!Utility.isSet(line.getBillingOnlyOrder())){
                    line.setBillingOnlyOrder("FALSE");
                }
            }
            orderPropStmt.close();
            orderPropStmt = null;
        }

         //Make report
         GregorianCalendar gc = new GregorianCalendar();
         gc.set(GregorianCalendar.HOUR_OF_DAY,0);
         gc.set(GregorianCalendar.MINUTE,0);
         gc.set(GregorianCalendar.SECOND,0);
         gc.set(GregorianCalendar.MILLISECOND,0);
         gc.add(GregorianCalendar.DATE,-7);
         Date thresholdDate = gc.getTime();
         BigDecimal bd = null;
         ArrayList lines = new ArrayList();
         ArrayList rankedLines = new ArrayList();
         for(int ii=0; ii<openLinesResVwA.length; ii++) {
            olrVw = openLinesResVwA[ii];
            processResultLine(olrVw,lines,rankedLines);
         }
        result.setTable(lines);
        result.setHeader(getReportHeader());
        result.setColumnCount(result.getHeader().size());
        result.setName("Open POs");

        if(!rankedLines.isEmpty()){
            rankedTable.setTable(rankedLines);
            rankedTable.setHeader(getReportHeader());
            rankedTable.setColumnCount(result.getHeader().size());
            rankedTable.setName("Ranked Open POs");
        }
    } catch (Exception exc) {
       exc.printStackTrace();
       throw exc;
     }finally{
       try{
         con.close();
         //repCon.close();
         if(rs!=null) rs.close();
         if(stmt!=null) stmt.close();
       }catch(Exception exc) {
         exc.printStackTrace();
       }
     }
      return resultPages;
    }

    protected void processResultLine(OpenLinesResultView olrVw,ArrayList lines, ArrayList rankedLines){
    	BigDecimal bd;
        ArrayList row = new ArrayList();
        row.add(olrVw.getAccountErpNum());
        String rankS = Utility.strNN(olrVw.getTargetFacility());
        if(olrVw.getSiteRank()!=0) {
            rankS += olrVw.getSiteRank();
        }
        if(extendedVersion){
	        row.add(rankS);
	        row.add(olrVw.getOrderStatusCd());
        }
        row.add(olrVw.getOrderItemStatusCd());
        if(extendedVersion){
        	row.add(olrVw.getOpenLineStatus());
        }
        row.add(olrVw.getBillingOnlyOrder());
        if(extendedVersion){
        	bd = olrVw.getGoods();
        	bd.setScale(2,BigDecimal.ROUND_HALF_UP);
            row.add(bd);
        }
        row.add(olrVw.getVendor());
        row.add(olrVw.getVendorName());
        row.add(olrVw.getWebPoDate());
        if(extendedVersion){
	        row.add(olrVw.getPoDate());
	        row.add(olrVw.getApprovedDate());
        }
        String item = olrVw.getItem();
        row.add(item);
        bd = olrVw.getUnitCost();
        bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        row.add(bd);
        if(olrVw.getDistUomConvCost() != null){
            bd = olrVw.getDistUomConvCost();
            bd.setScale(2,BigDecimal.ROUND_HALF_UP);
            row.add(bd);
        }else{
            row.add(ZERO);
        }
        row.add(olrVw.getOutboundPoNum());
        row.add(olrVw.getWebPoNumber());
        row.add(new Integer(olrVw.getWebLineNumber()));
        row.add(olrVw.getPoNumber());
        if(extendedVersion){
        	row.add(new Integer(olrVw.getLineNumber()));
        }
        row.add(new Integer(olrVw.getOrderNumber()));
        row.add(olrVw.getVendorItem());
        row.add(olrVw.getDescritption());
        row.add(olrVw.getShipTo());
        row.add(olrVw.getShipName());
        row.add(olrVw.getState());
        row.add(olrVw.getZipCode());
        row.add(new Integer(olrVw.getQuantity()));
        row.add(new Integer(olrVw.getOpenQuantity()));
        row.add(new Integer(olrVw.getDistUomConvQty()));
        row.add(new Integer(olrVw.getDistUomConvOpenQty()));
        if(showPrice){
        	bd = olrVw.getOpenPrice();
            bd.setScale(2,BigDecimal.ROUND_HALF_UP);
            row.add(bd);
        }
        bd = olrVw.getOpenCost();
        bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        row.add(bd);
        if(extendedVersion){
	        row.add(olrVw.getDeliveryDate());
	        row.add(new Integer(olrVw.getVendorId()));
	        row.add(new Integer(olrVw.getScheduleId()));
        }
        row.add(new Integer(olrVw.getSiteId()));
        if(extendedVersion){
	        row.add(olrVw.getFreightHandler());
	        row.add(olrVw.getSiteSchedule());
        }
        row.add(olrVw.getOrderSiteName());
        row.add(olrVw.getAccountName());
        if(olrVw.getExsistingVendorInvoicesAgainstPo() != null){
            Iterator it = olrVw.getExsistingVendorInvoicesAgainstPo().iterator();
            StringBuffer buf = new StringBuffer();
            while(it.hasNext()){
                buf.append((String) it.next());
                if(it.hasNext()){
                    buf.append(",");
                }
            }
            row.add(buf.toString());
        }
        lines.add(row);

        //Get all JC Penney Type 1 sites as ranked ones
        String acctErpNum = olrVw.getAccountErpNum();
        if(acctErpNum!=null) acctErpNum = acctErpNum.trim();
        if(isSpecialAccountErpNum(acctErpNum)) {
          Date poDate = olrVw.getPoDate();
          if(prioritySkuA.length>0) {
            for(int mm=0; mm<prioritySkuA.length; mm++) {
              if(prioritySkuA[mm].equals(item)) {
                ArrayList rankedRow = new ArrayList();
                rankedRow.addAll(row);
                rankedLines.add(rankedRow);
                break;
              }
            }
          } else {
            ArrayList rankedRow = new ArrayList();
            rankedRow.addAll(row);
            rankedLines.add(rankedRow);
          }
        }
    }

    protected GenericReportColumnViewVector getReportHeader() {
      GenericReportColumnViewVector header = new GenericReportColumnViewVector();
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Erp Num",0,255,"VARCHAR2"));
       if(extendedVersion){
	       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Target Facility",0,255,"VARCHAR2"));
	       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Status",0,255,"VARCHAR2"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Item Status",0,255,"VARCHAR2"));
       if(extendedVersion){
    	   header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Open Line Status",0,255,"VARCHAR2"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Billing Only",0,255,"VARCHAR2"));
       if(extendedVersion){
    	   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Goods$",2,20,"NUMBER"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Vendor",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Vendor Name",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Po Date",0,0,"DATE"));
       if(extendedVersion){
	       header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Law Po Date",0,0,"DATE"));
	       header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Order Approved Date",0,0,"DATE"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Unit Cost$",2,20,"NUMBER"));
	   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Dist Unit Cost$",2,20,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Outbound PO Number",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Clw PO Number",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Line Number",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Law PO Number",0,255,"VARCHAR2"));
       if(extendedVersion){
    	   header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Law Line Number",0,32,"NUMBER"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Order Number",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Vendor Item",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Descritption",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship To",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Ship Name",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","State",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Zip Code",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Quantity",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Open Quantity",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Dist Quantity",0,32,"NUMBER"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Dist Open Quantity",0,32,"NUMBER"));
       if(showPrice){
    	   header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Open Customer Price$",2,20,"NUMBER"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Open Cost$",2,20,"NUMBER"));
       if(extendedVersion){
	       header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp","Delivery Date",0,0,"DATE"));
	       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Vendor Id",0,32,"NUMBER"));
	       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Schedule Id",0,32,"NUMBER"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Site Id",0,32,"NUMBER"));
       if(extendedVersion){
	       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Freight Handler",0,255,"VARCHAR2"));
	       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Schedule",0,255,"VARCHAR2"));
       }
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Site Name",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Account Name",0,255,"VARCHAR2"));
       header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Existing Vendor Invoices",0,255,"VARCHAR2"));
       return header;
    }

    private class AccountInfo{
        public String accountName;
        AccountInfo(String pAccountName){
            accountName = pAccountName;
        }
    }
}
