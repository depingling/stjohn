/*
 * OrderAccountReport.java
 *
 * Created on September 24, 2003 by YKupershmidt
 *
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.Utility;
import java.util.Map;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.GregorianCalendar;
/**
 * Picks up distributor and customer invoices and agreates it on order level based on customer invoices
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted from the ReportOrderBean to the new GenericReport Framework.
 * @author  bstevens
 */
public class OrderAccountReport implements GenericReportMulti {
    public class DetailReportLine{
      public int month = 0;
      //public int itemId = 0;
      public int siteId = 0;
      public int catalogId = 0;
      public int qty = 0;
      public BigDecimal amount = new BigDecimal(0);
      public int categoryId = 0;
      public String category = "";
      public String site = "";
      public DetailReportLine copy() {
         DetailReportLine drl = new DetailReportLine();
         drl.month = this.month;
         drl.siteId = this.siteId;
         drl.qty = this.qty;
         drl.amount = this.amount;
         drl.category = this.category;
         drl.site = this.site;
         return drl;
      }
    }
    public class ItemInfo{
      public int id;
      public int sku;
      public String name;
    }
    /** Creates a new instance of OrderAccountRepor */
    public OrderAccountReport() {
    }

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */
    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
    throws Exception {
        Connection con = pCons.getMainConnection();
        String errorMess = "No error";
        BigDecimal zeroAmt = new BigDecimal(0);
        String yearS = (String) pParams.get("YEAR");
        ReportingUtils repUtil = new ReportingUtils();
        ReportingUtils.UserAccessDescription userDesc =
            repUtil.getUserAccessDescription(pParams,con);

        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String mess = "^clw^Wrong year format^clw^";
        try {
          GregorianCalendar curDate = new GregorianCalendar();
          int yearInt = Integer.parseInt(yearS);
          if (yearInt < 2001 || yearInt > curDate.get(Calendar.YEAR)){
            throw new Exception(mess);
          }
        }
          catch (Exception exc1) {
            throw new Exception(mess);
          }
        String accountIdS = (String) pParams.get("ACCOUNT");
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        try {

        //Select catalog for the account
        String accountCatalogIdSql =
          "select c.catalog_id from "+
          " clw_catalog c, clw_catalog_assoc ca "+
          " where c.catalog_id = ca.catalog_id "+
          "  and c.catalog_status_cd = 'ACTIVE' "+
          " and c.catalog_type_cd = 'ACCOUNT' "+
          " and ca.bus_entity_id = "+accountIdS+" ";

        Statement stmt = con.createStatement();
        ResultSet accountCatalogIdRS = stmt.executeQuery(accountCatalogIdSql);
        int accountCatalogId = 0;
        if (accountCatalogIdRS.next() ) {
           accountCatalogId = accountCatalogIdRS.getInt("catalog_id");
        }
        accountCatalogIdRS.close();
        stmt.close();


         //Get order data agregeted
          String orderSql =
            "select sku_agr.*, nvl(ctg.short_desc,'Other') category from "+
            "(select to_char(original_order_date, 'mm') order_month, "+
            "  ia.item2_id, o.site_id, o.account_id, "+
            "  sum (oi.total_quantity_ordered) qty, "+
            "  sum(oi.cust_contract_price*oi.total_quantity_ordered) total_amt "+
            "from clw_item_assoc ia, "+
            "	   (select  order_id,site_id, account_id, original_order_date from clw_order "+
            "    where order_status_cd not in ( "+
            "	 'Cancelled', "+
            "	 'Duplicate', "+
            "	 'Duplicate Order', "+
            "	 'Duplicated', "+
            "   'ERP Cancelled', "+
            "   'Pending Approval', "+
            "   'Pending Date', "+
            "	 'Pending Order Review',  "+
            "	 'Pending Review', "+
            "	 'Rejected') "+
            "   and account_id = "+accountIdS+" "+
            "    and original_order_date between "
              + " ('1 jan "+yearS+"') and ('31 dec "+yearS+"') "+
            "	) o, "+
            "		 clw_order_item oi "+
            "		 where oi.order_id = o.order_id "+
            "	  and oi.order_item_status_cd not in ( "+
            "	  'CANCELLED', "+
            "	  'Cancelled', "+
            "	  'PENDING_ERP_PO', "+
            "	  'PENDING_FULFILLMENT', "+
            "	  'PENDING_REVIEW'   ) "+
            "	  and oi.total_quantity_ordered>0 "+
            "	  and oi.item_id =  ia.item1_id(+) "+
            "      and ia.catalog_id(+) = "+accountCatalogId+" "+
            "	  and ia.item_assoc_cd(+) = 'PRODUCT_PARENT_CATEGORY'  "+
            "	  group by ia.item2_id, o.site_id, o.account_id, "
              + " to_char(original_order_date, 'mm') "+
            ") sku_agr, clw_item ctg "+
              "where ctg.item_id(+) = sku_agr.item2_id ";
        if ( userDesc.hasAccessToAll() == false ) {
          if (userDesc.hasStoreAccess()) {
        		  orderSql += " and store_id in (" + userDesc.getAuthorizedSql() + ") ";
          }else if (userDesc.hasAccountAccess()) {
                orderSql += "and account_id in ("+
                    userDesc.getAuthorizedSql()+") ";
          }else if (userDesc.hasSiteAccess()) {
                orderSql += "and site_id in ("+
                    userDesc.getAuthorizedSql()+") ";
          }
        }
          orderSql +=  "order by ctg.short_desc, site_id, order_month";

        stmt = con.createStatement();
        ResultSet orderRS = stmt.executeQuery(orderSql);

        ArrayList orderDetailList = new ArrayList();
        while (orderRS.next() ) {
          DetailReportLine drl = new DetailReportLine();
          drl.month = Integer.parseInt(orderRS.getString("order_month"));
          drl.siteId = orderRS.getInt("site_id");
          drl.category = orderRS.getString("category");
          drl.qty = orderRS.getInt("qty");
          drl.amount = orderRS.getBigDecimal("total_amt");
          orderDetailList.add(drl);
        }
        orderRS.close();
        stmt.close();

        //Pick up items with no category
        String noCatgeoryItemSql =
          "select distinct null_cat.item_id, i.sku_num, i.short_desc from "+
          "(select sku_agr.item_id, ctg.item_id categ_id"
            + ", sku_agr.site_id, sku_agr.account_id from "+
          "(select  oi.item_id, o.site_id, o.account_id "+
          "from clw_order o, clw_order_item oi  "+
          "   where account_id = "+accountIdS+" "+
          "and order_status_cd not in ( "+
          "'Cancelled', "+
          "'Duplicate', "+
          "'Duplicate Order', "+
          "'Duplicated', "+
          "'ERP Cancelled', "+
          "'Pending Approval', "+
          "'Pending Date',"+
          "'Pending Order Review',"+
          "'Pending Review', "+
          "'Rejected'   ) "+
          "and oi.order_id = o.order_id "+
          "and oi.order_item_status_cd not in ( "+
          "'CANCELLED', "+
          "'Cancelled', "+
          "'PENDING_ERP_PO', "+
          "'PENDING_FULFILLMENT', "+
          "'PENDING_REVIEW'   ) "+
          "and oi.total_quantity_ordered>0 "+
          "      and original_order_date between "
            + " ('1 jan "+yearS+"') and ('31 dec "+yearS+"') "+
          "group by oi.item_id,o.site_id, o.account_id "+
          ") sku_agr, clw_item_assoc ia, clw_item ctg "+
          "where sku_agr.item_id = ia.item1_id(+) "+
          "and ia.item_assoc_cd(+) = 'PRODUCT_PARENT_CATEGORY' "+
          "and ctg.item_id(+) = ia.item2_id "+
          "  and ia.catalog_id(+) = "+accountCatalogId+" "+
          ") null_cat, clw_item i "+
          "where i.item_id(+) = null_cat.item_id  "+
            "and categ_id is null ";

        if ( userDesc.hasAccessToAll() == false ) {
          if (userDesc.hasStoreAccess()) {
        		noCatgeoryItemSql += " and store_id in (" + userDesc.getAuthorizedSql() + ") ";
      	  }else if (userDesc.hasAccountAccess()) {
                noCatgeoryItemSql += " and account_id in ("+
                    userDesc.getAuthorizedSql()+") ";
          }else if (userDesc.hasSiteAccess()) {
                noCatgeoryItemSql += " and site_id in ("+
                    userDesc.getAuthorizedSql()+") ";
          }
        }

        stmt = con.createStatement();
        ResultSet noCategoryItemRS = stmt.executeQuery(noCatgeoryItemSql);
        ArrayList noCategoryItems = new ArrayList();
        while (noCategoryItemRS.next() ) {
          ItemInfo itm = new ItemInfo();
          itm.id = noCategoryItemRS.getInt("item_id");
          itm.sku = noCategoryItemRS.getInt("sku_num");
          itm.name = noCategoryItemRS.getString("short_desc");
          noCategoryItems.add(itm);
        }
        orderRS.close();
        stmt.close();

        //Get site names
        IdVector siteIds = new IdVector();
        for(int ii=0; ii<orderDetailList.size();ii++) {
          DetailReportLine drl = (DetailReportLine) orderDetailList.get(ii);
          Integer siteIdI = new Integer(drl.siteId);
          if(!siteIds.contains(siteIdI) ) {
            siteIds.add(siteIdI);
          }
        }

        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,siteIds);
        dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
        BusEntityDataVector siteDV = BusEntityDataAccess.select(con,dbc);

        //Assign Site Names
        String wrkCategory = "-1";
        for(int ii=0, jj=0; ii<orderDetailList.size();ii++) {
          DetailReportLine drl = (DetailReportLine) orderDetailList.get(ii);
          if(!wrkCategory.equals(drl.category)) {
             jj=0;
             wrkCategory = drl.category;
          }
          for(;jj<siteDV.size(); jj++) {
            BusEntityData beD = (BusEntityData) siteDV.get(jj);
            if(drl.siteId==beD.getBusEntityId()) {
              drl.site = beD.getShortDesc();
              break;
            }
            if(drl.siteId<beD.getBusEntityId()) break;
          }
        }

        //Group by category, site
        wrkCategory = "-1";
        int wrkSiteId = -1;
        DetailReportLine agrRepLine = null;
        ArrayList agrCatSiteRep = new ArrayList();
        for(int ii=0; ii<orderDetailList.size();ii++) {
          DetailReportLine drl = (DetailReportLine) orderDetailList.get(ii);
          if(!(wrkCategory.equals(drl.category) &&
             wrkSiteId==drl.siteId)) {
             wrkCategory = drl.category;
             wrkSiteId = drl.siteId;
             if(ii>0) {
               agrCatSiteRep.add(agrRepLine);
             }
             agrRepLine = drl.copy();
             agrRepLine.amount = new BigDecimal(0);
             agrRepLine.qty = 0;
          }
          agrRepLine.qty += drl.qty;
if(drl.amount!=null){
          agrRepLine.amount = agrRepLine.amount.add(drl.amount);
}
        }
        if(orderDetailList.size()>0) {
          agrCatSiteRep.add(agrRepLine);
        }

        //Group by category
        wrkCategory = "-1";
        agrRepLine = null;
        ArrayList agrCatRep = new ArrayList();
        for(int ii=0; ii<agrCatSiteRep.size();ii++) {
          DetailReportLine drl = (DetailReportLine) agrCatSiteRep.get(ii);
          if(!wrkCategory.equals(drl.category)) {
             if(ii>0) {
               agrCatRep.add(agrRepLine);
             }
             wrkCategory = (drl.category!=null)?drl.category:"Other";
             agrRepLine = drl.copy();
             agrRepLine.amount = new BigDecimal(0);
             agrRepLine.qty = 0;
          }
          agrRepLine.qty += drl.qty;
          agrRepLine.amount = agrRepLine.amount.add(drl.amount);
        }
        if(agrCatSiteRep.size()>0) {
          agrCatRep.add(agrRepLine);
        }
        //Create sku no category page
        ArrayList row = null;
        if(noCategoryItems.size()>0) {
          GenericReportResultView result3 = GenericReportResultView.createValue();
          result3.setTable(new ArrayList());
          row = null;
          for(int ii=0; ii<noCategoryItems.size();ii++) {
            ItemInfo itm = (ItemInfo) noCategoryItems.get(ii);
            row = new ArrayList();
            row.add(""+itm.sku);
            row.add(itm.name);
            result3.getTable().add(row);
          }
          GenericReportColumnViewVector noCategoryItemRepCol = getNoCategoryItemReportHeader();
          result3.setColumnCount(noCategoryItemRepCol.size());
          result3.setHeader(noCategoryItemRepCol);
          result3.setName("Other Category Items");
          resultV.add(result3);
        }
        //Create category site month page
        GenericReportResultView result2 = GenericReportResultView.createValue();
        result2.setTable(new ArrayList());
        wrkSiteId = -1;
        wrkCategory = "-1";
        int prevMon = 0;
        row = null;
        for(int ii=0, mon=1; ii<orderDetailList.size();ii++) {
           DetailReportLine drl = (DetailReportLine) orderDetailList.get(ii);
           if(!wrkCategory.equals(drl.category) || wrkSiteId!=drl.siteId) {
             wrkCategory = drl.category;
             wrkSiteId = drl.siteId;
             if(ii>0) result2.getTable().add(row);
             row = new ArrayList();
             row.add(drl.category);
             row.add(drl.site);
             prevMon = 0;
           }
           int month = drl.month;
           while(month>prevMon+1) {
             row.add(null);
             row.add(null);
             prevMon++;
           }
           row.add(drl.amount);
           row.add(new Integer(drl.qty));
           prevMon++;

        }
        GenericReportColumnViewVector categorySiteMonRepCol = getCategorySiteMonthReportHeader();
        result2.setColumnCount(categorySiteMonRepCol.size());
        result2.setHeader(categorySiteMonRepCol);
        result2.setWidth("2048");
        result2.setName("Site Month Orders");
        resultV.add(result2);


        //Create category site page
        GenericReportResultView result1 = GenericReportResultView.createValue();
        result1.setTable(new ArrayList());
        for(int ii=0; ii<agrCatSiteRep.size();ii++) {
           row = new ArrayList();
           DetailReportLine drl = (DetailReportLine) agrCatSiteRep.get(ii);
           row.add(drl.category);
           row.add(drl.site);
           row.add(drl.amount);
           row.add(new Integer(drl.qty));
           result1.getTable().add(row);
        }
        GenericReportColumnViewVector categorySiteRepCol = getCategorySiteReportHeader();
        result1.setColumnCount(categorySiteRepCol.size());
        result1.setHeader(categorySiteRepCol);
        result1.setName("Site Orders");
        result1.setWidth("400");
        resultV.add(result1);

        //Create category page
        GenericReportResultView result = GenericReportResultView.createValue();
        result.setTable(new ArrayList());
        for(int ii=0; ii<agrCatRep.size();ii++) {
           row = new ArrayList();
           DetailReportLine drl = (DetailReportLine) agrCatRep.get(ii);
           row.add(drl.category);
           row.add(drl.amount);
           row.add(new Integer(drl.qty));
           result.getTable().add(row);
        }
        GenericReportColumnViewVector categoryRepCol = getCategoryReportHeader();
        result.setColumnCount(categoryRepCol.size());
        result.setHeader(categoryRepCol);
        result.setWidth("400");
        result.setName("Account Orders");
        resultV.add(result);
      }
      catch (SQLException exc) {
          errorMess = "Error. SQL Exception happened. "+exc.getMessage();
          exc.printStackTrace();
          throw new RemoteException(errorMess);
      }finally {
           try {if (con != null) con.close();} catch (Exception ex) {}
      }
      return resultV;
    }


    private GenericReportColumnViewVector getNoCategoryItemReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","CLW SKU",0,255,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","NAME",0,255,"VARCHAR2"));
        return header;
    }

    private GenericReportColumnViewVector getCategoryReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2","*",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount$",2,20,"NUMBER","*",true));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Quantity",0,255,"NUMBER","*",true));
        return header;
    }

    private GenericReportColumnViewVector getCategorySiteReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount$",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Quantity",0,255,"NUMBER"));
        return header;
    }

    private GenericReportColumnViewVector getCategorySiteMonthReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Category",0,255,"VARCHAR2","100",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site",0,255,"VARCHAR2","100",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Jan Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Jan Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Feb Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Feb Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Mar Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Mar Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Apr Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Apr Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","May Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","May Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Jun Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Jun Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Jul Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Jul Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Aug Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Aug Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Sep Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Sep Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Oct Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Oct Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Nov Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Nov Qty",0,255,"NUMBER","50",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Dec Amt$",2,20,"NUMBER","70",false));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Dec Qty",0,255,"NUMBER","50",false));
        return header;
    }


}
