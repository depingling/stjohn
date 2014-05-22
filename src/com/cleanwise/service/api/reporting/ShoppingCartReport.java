/*
 * ShoppingCartReport.java
 *
 * Created on April 25, 2005, 2:59 PM
 */

package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;

import org.apache.log4j.Category;

/**
 *
 * @author veronika
 */
public class ShoppingCartReport implements GenericReportMulti {
	private static final Category log = Category.getInstance(ShoppingCartReport.class);
    //private BigDecimal autoOrderFactor = new BigDecimal(0);
    private BigDecimal ZERO = new BigDecimal(0);

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws
            Exception {

        Connection con = pCons.getDefaultConnection();

        String accountStr = (String)ReportingUtils.getParam(pParams, "ACCOUNT");

        int accountId = 0;
        if (Utility.isSet(accountStr)) {
            accountId = Integer.parseInt(accountStr);
        }

        Statement stmt = null;
        ResultSet rs = null;
        try {

            APIAccess factory = APIAccess.getAPIAccess();
            Account accBean = factory.getAccountAPI();
            //Site siteBean = factory.getSiteAPI();

            FiscalCalenderView fcV = accBean.getCurrentFiscalCalenderV(accountId);
            int budgetYear = fcV.getFiscalCalender().getFiscalYear();
            FiscalCalendarInfo aCal = new FiscalCalendarInfo(fcV);
            java.util.Date curPeriodBeg = null;
            java.util.Date curPeriodEnd = null;
            java.util.Date currDate = new java.util.Date();
            int periodQty = aCal.getNumberOfBudgetPeriods();
            int curPeriodNum = 1;
            for(int idx=1; idx <= periodQty; idx++) {
                FiscalCalendarInfo.BudgetPeriodInfo bpi = aCal.getBudgetPeriod(idx);
                java.util.Date dd = bpi.getStartDate();
                if(dd.after(currDate)) {
                    break;
                }
                curPeriodBeg = dd;
                curPeriodEnd = bpi.getEndDate();
                curPeriodNum = idx;
            }
            log.info("curPeriodNum: "+curPeriodNum);

            //CostCenterDataVector costCenters = accBean.getAllCostCenters(accountId, Account.ORDER_BY_NAME);



            HashMap nextOrderDateHM = calculateNextOrderDates(con, null, accountId) ;

            String itemQtySql =
                    "SELECT * FROM (\n" +
                            "SELECT og.bus_entity_id site_id, ogs.item_id, ogs.quantity qty, ogs.quantity qty_with_auto FROM clw_order_guide og \n" +
                            "join clw_order_guide_structure ogs ON og.order_guide_id = ogs.order_guide_id \n" +
                            "join clw_bus_entity_assoc bea ON og.bus_entity_id = bea.bus_entity1_id \n" +
                            "AND bea.bus_entity2_id = " + accountId + " \n" +
                            "WHERE og.order_guide_type_cd = '"+RefCodeNames.ORDER_GUIDE_TYPE_CD.INVENTORY_CART+"' \n" +
                            "UNION ALL \n" +
                            "SELECT il.bus_entity_id site_id, il.item_id, \n" +
                            "To_Number(Nvl(order_qty,Nvl(cild.clw_value-qty_on_hand,0))) qty, \n" +
                            "To_Number(Nvl(order_qty,Nvl(cild.clw_value-qty_on_hand,Nvl2(enable_auto_order,round(Nvl(fild.clw_value,0)/2+0.49),0)))) qty_with_auto \n" +
                            "FROM (clw_inventory_level il \n" +
                            "join clw_bus_entity_assoc bea ON il.bus_entity_id = bea.bus_entity1_id AND bea.bus_entity2_id = " + accountId + ") \n" +
                            "left join clw_inventory_items ii ON ii.bus_entity_id = " + accountId + " AND il.item_id = ii.item_id AND enable_auto_order = 'Y' \n" +
                            "left join clw_inventory_level_detail cild ON il.inventory_level_id = cild.inventory_level_id AND cild.period=" + curPeriodNum + "   \n" +
                            "left join clw_inventory_level_detail fild ON il.inventory_level_id = fild.inventory_level_id AND fild.period=1  \n" +
                            "WHERE cild.clw_value > 0 or order_qty IS NOT NULL) ORDER BY site_id, item_id";
            log.info("itemQtySql: "+itemQtySql);



            stmt = con.createStatement();
            rs = stmt.executeQuery(itemQtySql);
            ArrayList siteItemAL  = new ArrayList();
            HashSet itemIdHS = new HashSet();

            while (rs.next()) {
                InvSiteItem isi = new InvSiteItem();
                isi.siteId = rs.getInt("site_id");
                isi.itemId = rs.getInt("item_id");
                isi.orderQty = rs.getInt("qty");
                if(isi.orderQty<0) isi.orderQty = 0;
                isi.autoOrderQty = rs.getInt("qty_with_auto");
                if(isi.autoOrderQty<0) isi.autoOrderQty = 0;
                siteItemAL.add(isi);
                Integer itemIdI = new Integer(isi.itemId);
                if(!itemIdHS.contains(itemIdI)) {
                    itemIdHS.add(itemIdI);
                }
            }

            rs.close();
            stmt.close();

////////////////////



            IdVector itemIdV = new IdVector();
            for(Iterator iter = itemIdHS.iterator(); iter.hasNext();) {
                itemIdV.add(iter.next());
            }
            String itemPriceSql =
                    "SELECT bea.bus_entity1_id site_id, ci.item_id, ci.amount "+
                    "FROM clw_contract_item ci join clw_contract c ON ci.contract_id = c.contract_id "+
                    "join clw_catalog_assoc ca ON c.catalog_id = ca.catalog_id "+
                    "join clw_bus_entity_assoc bea ON ca.bus_entity_id = bea.bus_entity1_id AND bea.bus_entity2_id = "+accountId+" "+
                    "WHERE ci.item_id in ("+IdVector.toCommaString(itemIdV)+") "+
                    "ORDER BY bea.bus_entity1_id, item_id ";
            log.info("itemPriceSql: "+itemPriceSql);
            stmt = con.createStatement();
            rs = stmt.executeQuery(itemPriceSql);
            ArrayList itemPriceAL  = new ArrayList();
            log.info("itemPriceSql done ");

            while (rs.next()) {
                int siteId = rs.getInt("site_id");
                int itemId = rs.getInt("item_id");
                BigDecimal price = rs.getBigDecimal("amount");
                if(price==null) {
                    continue;
                }
                InvSiteItem isi = new InvSiteItem();
                isi.siteId = siteId;
                isi.itemId = itemId;
                isi.price = price;
                itemPriceAL.add(isi);
            }

            rs.close();
            stmt.close();

            log.info("itemPriceSql done 1 ");
            //Calc cart amount
            InvSiteItem wrkItemPrice = null;
            for(Iterator iter=siteItemAL.iterator(), iter1 = itemPriceAL.iterator();
            iter.hasNext();) {
                InvSiteItem isi = (InvSiteItem) iter.next();
                while(wrkItemPrice!=null || iter1.hasNext()) {
                    if(wrkItemPrice==null) wrkItemPrice = (InvSiteItem) iter1.next();
                    int sId = wrkItemPrice.siteId;
                    int iId = wrkItemPrice.itemId;
                    if(sId==isi.siteId && iId==isi.itemId) {
                        isi.price = wrkItemPrice.price;
                        wrkItemPrice = null;
                        break;
                    } else if(sId>isi.siteId ||(sId==isi.siteId && iId>isi.itemId)) {
                        break;
                    } else {
                        wrkItemPrice = null;
                        continue;
                    }
                }
            }
            log.info("amount calc done ");

            // Move to site level
            IdVector siteIdV = new IdVector();
            ArrayList invSiteAL = new ArrayList();
            int prevSiteId = -1;
            InvSite wrkIs = null;
            for(Iterator iter=siteItemAL.iterator(); iter.hasNext();) {
                InvSiteItem isi = (InvSiteItem) iter.next();
                if(isi.price==null) {
                    isi.price = ZERO;
                    log.info("Warning. no price found Site id: "+isi.siteId+" item id: "+isi.itemId);
                }
                isi.orderAmt = isi.price.multiply(new BigDecimal(isi.orderQty));
                isi.autoOrderAmt = isi.price.multiply(new BigDecimal(isi.autoOrderQty));
                if(prevSiteId!=isi.siteId) {
                    prevSiteId = isi.siteId;
                    wrkIs = new InvSite();
                    invSiteAL.add(wrkIs);
                    wrkIs.siteId = isi.siteId;
                    wrkIs.orderAmt = isi.orderAmt;
                    wrkIs.autoOrderAmt = isi.autoOrderAmt;
                    siteIdV.add(new Integer(isi.siteId));
                } else {
                    wrkIs.orderAmt = wrkIs.orderAmt.add(isi.orderAmt);
                    wrkIs.autoOrderAmt = wrkIs.autoOrderAmt.add(isi.autoOrderAmt);
                }
            }
            log.info("Warning. about to get site data ");
            //get site data
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, siteIdV);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
            BusEntityDataVector siteBeDV = BusEntityDataAccess.select(con,dbc);


            //set site name
            BusEntityData wrkSiteBeD = null;
            for(Iterator iter = invSiteAL.iterator(),iter1=siteBeDV.iterator(); iter.hasNext();) {
                InvSite is = (InvSite) iter.next();
                while(wrkSiteBeD!=null  || iter1.hasNext()) {
                    if(wrkSiteBeD==null) wrkSiteBeD = (BusEntityData) iter1.next();
                    int wrkSiteId = wrkSiteBeD.getBusEntityId();
                    if(wrkSiteId==is.siteId) {
                        is.siteName = wrkSiteBeD.getShortDesc();
                        wrkSiteBeD = null;
                        break;
                    } else if (wrkSiteId > is.siteId) {
                        break;
                    } else {
                        wrkSiteBeD = null;
                        continue;
                    }
                }
            }

            //set cutoff date
            for(Iterator iter = invSiteAL.iterator(); iter.hasNext();) {
                InvSite is = (InvSite) iter.next();
                if(is.siteName==null) {
                    continue; // ignore not active sites
                }
                Integer siteIdI = Integer.valueOf(is.siteId);
                ScheduleOrderDates sod = (ScheduleOrderDates) nextOrderDateHM.get(siteIdI);
                if(sod!=null) {
                    is.cutoffDate = sod.getNextOrderCutoffDate();
                } else {
                    log.info("Warning. no cutoff date for the site Site id: "+siteIdI);
                }
            }

            //set last update
            String lastUpdatSql =
                    "SELECT add_date, add_by, site_id, clw_value, item_id FROM clw_shopping_info WHERE shopping_info_id IN ( "+
                    "SELECT Max(shopping_info_id) FROM clw_shopping_info si   "+
                    "WHERE (order_guide_id IN  "+
                    "(SELECT order_guide_id FROM clw_order_guide og WHERE order_guide_type_cd = 'INVENTORY_CART'  "+
                    "   AND og.bus_entity_id IN (SELECT bus_entity1_id FROM clw_bus_entity_assoc WHERE bus_entity2_id = "+accountId+"))  "+
                    " OR order_guide_id = 0) "+
                    "AND si.order_id IS NULL  "+
                    "AND (si.clw_value like '%quan%')  "+
                    "GROUP BY site_id "+
                    ") ORDER BY site_id ";
        /*
        "SELECT si.add_date, si.add_by, si.site_id FROM clw_shopping_info si "+
        "where si.order_id IS NULL "+
        "AND si.site_id IN (SELECT bus_entity1_id FROM clw_bus_entity_assoc WHERE bus_entity2_id = "+accountId+") "+
        "AND (si.clw_value like '%set%' OR si.clw_value like '%update%' OR si.clw_value like '%add%' ) "+
        "ORDER BY site_id, add_date desc";
         */
            log.info("lastUpdatSql: "+lastUpdatSql);
            stmt = con.createStatement();
            rs = stmt.executeQuery(lastUpdatSql);
            log.info("lastUpdatSql done ");

            Iterator siteIter = invSiteAL.iterator();
            prevSiteId = -1;
            InvSite wrkInvSite = null;
            while (rs.next()) {
                int siteId = rs.getInt("site_id");
                if(prevSiteId==siteId) {
                    continue;
                }
                java.util.Date updateDt = rs.getDate("add_date");
                String updatedBy = rs.getString("add_by");
                String lastAction = rs.getString("clw_value");
                int lastActionItemId = rs.getInt("item_id");
                while(wrkInvSite!=null || siteIter.hasNext()) {
                    if(wrkInvSite==null) wrkInvSite = (InvSite) siteIter.next();
                    if(wrkInvSite.siteId==siteId) {
                        wrkInvSite.lastModDate = updateDt;
                        wrkInvSite.lastModBy = updatedBy;
                        wrkInvSite = null;
                        break;
                    } else if(wrkInvSite.siteId > siteId) {
                        break;
                    } else {
                        wrkInvSite = null;
                        continue;
                    }
                }
            }

            rs.close();
            stmt.close();

            //Budget spent
            /* vvv
            String budgetSpentSql =
                    "SELECT site_id, Sum(amount) amount FROM ( "+
                    "SELECT site_id, amount FROM clw_site_ledger sl  "+
                    "WHERE budget_year = "+budgetYear+" "+
                    //"AND budget_period = "+curPeriodNum+" "+
                    "AND site_id IN (SELECT bus_entity1_id FROM clw_bus_entity_assoc WHERE bus_entity2_id = "+accountId+") "+
                    "AND order_id IS NULL  "+
                    "UNION all "+
                    "SELECT sl.site_id, sl.amount FROM clw_site_ledger sl join clw_order o  "+
                    "ON sl.ORDER_id = o.order_id AND o.order_status_cd not in ('Cancelled','Rejected') AND o.account_id = "+accountId+" "+
                    "WHERE budget_year = "+budgetYear+" "+
                    //"AND budget_period = "+curPeriodNum+" "+
                    ") GROUP BY site_id "+
                    "ORDER BY site_id ";

            log.info("budgetSpentdSql: "+budgetSpentSql);
            stmt = con.createStatement();
            rs = stmt.executeQuery(budgetSpentSql);
            log.info("budgetSpentSql done ");

            siteIter = invSiteAL.iterator();
            prevSiteId = -1;
            wrkInvSite = null;
            while (rs.next()) {
                int siteId = rs.getInt("site_id");
                if(prevSiteId==siteId) {
                    continue;
                }
                BigDecimal spentAmt = rs.getBigDecimal("amount");
                if(spentAmt==null) spentAmt = ZERO;
                while(wrkInvSite!=null || siteIter.hasNext()) {
                    if(wrkInvSite==null) wrkInvSite = (InvSite) siteIter.next();
                    if(wrkInvSite.siteId==siteId) {
                        wrkInvSite.spentAmt = spentAmt;
                        wrkInvSite = null;
                        break;
                    } else if(wrkInvSite.siteId > siteId) {
                        break;
                    } else {
                        wrkInvSite = null;
                        continue;
                    }
                }
            }

            rs.close();
            stmt.close();
            */

/*
SELECT bus_entity_id, Sum(amount1) amount FROM clw_budget
WHERE bus_entity_id IN (SELECT bus_entity1_id FROM clw_bus_entity_assoc WHERE bus_entity2_id = 94010)
GROUP BY bus_entity_id
ORDER BY bus_entity_id
;
 */
            //Budget allocated
            /*

            String budgetAllocatedSql = "SELECT BUS_ENTITY_ID SITE_ID, SUM(AMOUNT) AMOUNT FROM CLW_BUDGET B, CLW_BUDGET_DETAIL BD \n" +
                    "  WHERE B.BUS_ENTITY_ID IN (SELECT BUS_ENTITY1_ID FROM CLW_BUS_ENTITY_ASSOC WHERE BUS_ENTITY2_ID = "+accountId+")\n" +
                    "    AND B.BUDGET_STATUS_CD = '"+RefCodeNames.BUDGET_STATUS_CD.ACTIVE+"'  \n" +
                    "    AND B.BUDGET_TYPE_CD = '"+RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET+"' \n" +
                    "    AND B.BUDGET_ID = BD.BUDGET_ID\n" +
                    "    AND BD.PERIOD <= "+curPeriodNum +
                    "  GROUP BY BUS_ENTITY_ID \n" +
                    "  ORDER BY BUS_ENTITY_ID";

            log.info("budgetAllocatedSql: "+budgetAllocatedSql);
            stmt = con.createStatement();
            rs = stmt.executeQuery(budgetAllocatedSql);
            log.info("budgetAllocatedSql done ");

            siteIter = invSiteAL.iterator();
            prevSiteId = -1;
            wrkInvSite = null;
            while (rs.next()) {
                int siteId = rs.getInt("site_id");
                if(prevSiteId==siteId) {
                    continue;
                }
                BigDecimal budgetAmt = rs.getBigDecimal("amount");
                if(budgetAmt==null) budgetAmt = ZERO;
                while(wrkInvSite!=null || siteIter.hasNext()) {
                    if(wrkInvSite==null) wrkInvSite = (InvSite) siteIter.next();
                    if(wrkInvSite.siteId==siteId) {
                        wrkInvSite.budgetAmt = budgetAmt;
                        wrkInvSite = null;
                        break;
                    } else if(wrkInvSite.siteId > siteId) {
                        break;
                    } else {
                        wrkInvSite = null;
                        continue;
                    }
                }
            }

            rs.close();
            stmt.close();
            */
            //

            GenericReportResultViewVector retVal = new GenericReportResultViewVector();
            GenericReportResultView page = GenericReportResultView.createValue();
            retVal.add(page);

            ArrayList items = new ArrayList();

            BudgetUtil budgetUtil = new BudgetUtil(con);
            PropertyUtil p = new PropertyUtil(con);
            String accrualCd = p.fetchValueIgnoreMissing(0,accountId,RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD);


            for(Iterator iter = invSiteAL.iterator(); iter.hasNext();){
                InvSite is = (InvSite) iter.next();
                if(is.siteName==null) {
                    continue;
                }
                ArrayList row = new ArrayList();
                row.add(is.siteName);
                row.add(curPeriodBeg);
                //row.add(curPeriodEnd);
                row.add(is.cutoffDate);
                row.add(is.lastModDate);
                row.add(is.lastModBy);
                row.add(is.orderAmt);
                //row.add(is.autoOrderAmt);
                /* vvv
                if(is.spentAmt==null) is.spentAmt = ZERO;
                //row.add(is.spentAmt);
                if(is.budgetAmt==null) is.budgetAmt = ZERO;
                //row.add(is.budgetAmt);
                BigDecimal ytdDiff = is.budgetAmt.subtract(is.spentAmt);
                row.add(ytdDiff);
                */

                BigDecimal ytdDiff = getYTDDifference(is.siteId, accountId, budgetUtil, accrualCd);
                row.add(ytdDiff);

                //row.add(is.lastAction);
                items.add(row);
            }
            page.setTable(items);
            page.setName("Site Inventory");
            GenericReportColumnViewVector header = getReportHeader();
            page.setHeader(header);
            page.setColumnCount(header.size());
            return retVal;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("getSpendingData failed: " + e.getMessage());
        }
    }

    private BigDecimal getYTDDifference(int pSiteId, int pAccountId, BudgetUtil bu,  String pAccrualCd) {
          BigDecimal tdiffTotal = new BigDecimal(0.00);
          try {
              BudgetSpendViewVector sv = bu.getAllBudgetSpentForSite(pAccountId, pSiteId, pAccrualCd);
              BigDecimal allocated = new BigDecimal(0.00);
              for ( int i = 0; sv != null && i < sv.size(); i++ ) {
                  BudgetSpendView thisbudget = (BudgetSpendView)sv.get(i);
                  if ( thisbudget.getAmountAllocated() != null ) {
                    allocated = allocated.add(thisbudget.getAmountAllocated());
                  }
              }
              BigDecimal spent = new BigDecimal(0.00);
              for ( int i = 0; sv != null && i < sv.size(); i++ ) {
                  BudgetSpendView thisbudget = (BudgetSpendView)sv.get(i);
                  if ( thisbudget.getAmountSpent() != null ) {
                      spent = spent.add(thisbudget.getAmountSpent());
                  }
              }
              if(allocated==null){allocated=new BigDecimal(0.00);}
              if(spent==null){spent=new BigDecimal(0.00);}
              tdiffTotal = allocated.subtract(spent);
          } catch (Exception e) {
            e.printStackTrace();
          }
          return tdiffTotal;
    }

    //************************************* METHODS TO GENERATE THE REPORT DATA *********************************************



    protected boolean getRenderBSCTab() {
        return true;
    }
/*
  private GenericReportResultView generatePage(ArrayList entries) {

    GenericReportResultView repResults = GenericReportResultView.createValue();
    repResults.setFancyDisplay(true);
    repResults.setTable(entries);
    repResults.setHeader(getReportHeader());
    repResults.setColumnCount(repResults.getHeader().size());
    repResults.setName("Shopping Cart");
    return repResults;
  }

 */
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Period Begin Date", 0,0, "DATE"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Period End Date", 0,0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Next Cutoff Date", 0,0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Last Cart Update", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Last modified by", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Cart  Total$", 2,20,"NUMBER"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Cart  Total (with AutoOrder)$",2,20,"NUMBER"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Amount Spent$", 2,20,"NUMBER"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Budget$", 2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "YTD Budget Difference$", 2,20,"NUMBER"));
        //header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Last action", 0, 255, "VARCHAR2"));
        return header;
    }

    private GenericReportColumnViewVector getDetailHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Site_Name", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Sku Num",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Qty",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Amount$",2,20,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer","Defaut Qty",0,32,"NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal","Default Amount$$",2,20,"NUMBER"));
        return header;
    }

  /*
  private ArrayList getSiteCartItems(Connection pCon, ArrayList pSiteIds, int pAccountId) throws SQLException {
    StringBuffer sqlSelectItems = new StringBuffer();
    for (Iterator i = pSiteIds.iterator(); i.hasNext(); ) {
      int itemId = ((Integer) i.next()).intValue();
      sqlSelectItems.append(itemId).append(",");
    }
    sqlSelectItems.delete(sqlSelectItems.length() - 1, sqlSelectItems.length());

    ArrayList result = new ArrayList();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      String query = "select " +
                     " x.quantity*ci.amount, " +
                     " x.item_id, " +
                     " s.bus_entity_id site_id " +
                     " from clw_order_guide_structure x, " +
                     " clw_contract_item ci,  " +
                     " clw_contract c, " +
                     " clw_catalog_assoc cata, " +
                     " clw_property p, " +
                     " clw_bus_entity s, " +
                     " clw_order_guide og " +
                     " where x.order_guide_id = og.order_guide_id and " +
                     " c.catalog_id = cata.catalog_id and  " +
                     " cata.bus_entity_id = s.bus_entity_id and " +
                     " x.item_id = ci.item_id and  " +
                     " ci.contract_id = c.contract_id and " +
                     " c.contract_status_cd = 'ACTIVE' and " +
                     " og.bus_entity_id in ( " +
                     " select bus_entity1_id " +
                     " from clw_bus_entity_assoc " +
                     " where bus_entity2_id = " + pAccountId + " ) " +
                     " and og.bus_entity_id = s.bus_entity_id  " +
                     " and og.bus_entity_id = p.bus_entity_id " +
                     " and p.short_desc = 'NEXT_ORDER_CUTOFF_DATE' " +
                     " and og.order_guide_id = x.order_guide_id " +
                     " and order_guide_type_cd in ('SHOPPING_CART', 'INVENTORY_CART') " +
                     " and s.bus_entity_id in ( " + sqlSelectItems + " ) " +
                     " and og.mod_date = ( select max(ogg.mod_date) " +
                     " from clw_order_guide ogg " +
                     " where ogg.bus_entity_id = s.bus_entity_id )" +
                     " and x.quantity*ci.amount > 0";

      stmt = pCon.createStatement();
      rs = stmt.executeQuery(query);

      while (rs.next()) {
        ArrayList row = new ArrayList(3);
        row.add(new Integer(rs.getInt(3))); // site id
        row.add(new Integer(rs.getInt(2))); // item id
        row.add(rs.getBigDecimal(1));       // amount
        result.add(row);
      }
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw new SQLException("getSpendingData failed: " + e.getMessage());
    }
    return result;
  }
   */
/*
  private ArrayList completeValues(Connection pCon, ArrayList pEntries, ArrayList pSites,
                                   ArrayList pSiteCartItems, Site pSiteBean, CostCenterDataVector costCenters, int pAccountId)
                                   throws RemoteException, DataNotFoundException, SQLException{
    Iterator i = pEntries.iterator();
    Iterator iSiteIds = pSites.iterator();
    while (i.hasNext()) {
      ArrayList row = (ArrayList)i.next();
      int site_id = ((Integer)iSiteIds.next()).intValue();

      ArrayList cartTotalValues = calcCartTotal(pCon, site_id, pSiteCartItems, pSiteBean);
      BigDecimal cartTotal = (BigDecimal)cartTotalValues.get(0);
      BigDecimal cartTotalWithDefault = (BigDecimal)cartTotalValues.get(1);

      if (cartTotal.compareTo(ZERO) != 0 || cartTotalWithDefault.compareTo(ZERO) != 0) {
        BigDecimal budjetWithCart = getBudgetWithCart(pAccountId, site_id, cartTotal, costCenters);

        row.add(cartTotal);
        row.add(cartTotalWithDefault);
        row.add(budjetWithCart);
      } else {
        i.remove();
      }
    }
    return pEntries;
  }
 */
/*
  private ArrayList calcCartTotal(Connection pCon, int pSiteId, ArrayList pSiteCartItems, Site pSiteBean)
    throws RemoteException, DataNotFoundException, SQLException {
    ArrayList results = new ArrayList(2);
    BigDecimal resultTotal = new BigDecimal(0.00);
    BigDecimal resultTotalDefault = new BigDecimal(0.00);
    SiteInventoryConfigViewVector sicVwV = pSiteBean.lookupSiteInventory(pSiteId);
    SiteData siteD = pSiteBean.getSite(pSiteId);

    Iterator i = pSiteCartItems.iterator();

    while (i.hasNext()) {
      ArrayList row = (ArrayList) i.next();
      int site_id = ((Integer) row.get(0)).intValue();
      if (site_id != pSiteId) {
        continue;
      }

      int itemId = ((Integer) row.get(1)).intValue();
      BigDecimal totalAmount = (BigDecimal) row.get(2);

      resultTotal = resultTotal.add(totalAmount);
      resultTotalDefault = resultTotalDefault.add(totalAmount);
      for (Iterator iter1 = sicVwV.iterator(); iter1.hasNext(); ) {
        SiteInventoryInfoView siiVw = (SiteInventoryInfoView) iter1.next();
        if (siiVw.getProductData().getItemData().getItemId() == itemId) {
          iter1.remove();
          break;
        }
      }

    }

    if (sicVwV.size() > 0) {
      ArrayList itemIds = new ArrayList(sicVwV.size());
      for (Iterator iter1 = sicVwV.iterator(); iter1.hasNext(); ) {
        SiteInventoryInfoView siiVw = (SiteInventoryInfoView) iter1.next();
        itemIds.add(new Integer(siiVw.getProductData().getItemData().getItemId()));
      }
      ArrayList itemPrices = getItemPrices(pCon, pSiteId, itemIds);

      for (Iterator iter1 = sicVwV.iterator(); iter1.hasNext(); ) {
        SiteInventoryInfoView siiVw = (SiteInventoryInfoView) iter1.next();

        int parValue = siiVw.getParValue();
        int qtyOnHand = 0;
        try {
          if (siiVw.getQtyOnHand() != null) {
            qtyOnHand = Integer.parseInt(siiVw.getQtyOnHand().trim());
          } else {
            qtyOnHand = -1;
          }
        } catch (Exception exc) {}

        BigDecimal itemPrice = new BigDecimal(0);
        for (int j=0; j<itemPrices.size(); j++) {
          ContractItemData cItem = (ContractItemData) itemPrices.get(j);
          if (cItem.getItemId() == siiVw.getProductData().getItemData().getItemId()) {
            itemPrice = cItem.getAmount();
            break;
          }
        }

        if (siteD.isModernInventoryCartAvailable()) {
          int quantity = 0;
          try {
            quantity = Integer.parseInt(siiVw.getOrderQty().trim());
          } catch (Exception e) {}
          if (quantity > 0) {
            BigDecimal amount = itemPrice.multiply(new BigDecimal(quantity));
            resultTotal = resultTotal.add(amount);
            resultTotalDefault = resultTotalDefault.add(amount);
          }

        } else {
          if (qtyOnHand >= 0) {
            int quantity = parValue - qtyOnHand;
            if (quantity > 0) {
              BigDecimal amount = itemPrice.multiply(new BigDecimal(quantity));
              resultTotal = resultTotal.add(amount);
              resultTotalDefault = resultTotalDefault.add(amount);
            }
          } else {
            // with using default value
            if (Utility.isTrue(siiVw.getAutoOrderItem())) {
              BigDecimal q = autoOrderFactor.multiply(new BigDecimal(parValue));
              q.setScale(0, BigDecimal.ROUND_CEILING);
              BigDecimal amount = itemPrice.multiply(q);
              resultTotalDefault = resultTotalDefault.add(amount);
            }
          }
        }
      }
    }

    results.add(resultTotal);
    results.add(resultTotalDefault);
    return results;
  }

 */
  /*
  private BigDecimal getBudgetWithCart(int accountId, int siteId, BigDecimal cartAmt, CostCenterDataVector costCenters) {
    BigDecimal totWCartTotal = new BigDecimal(0.00);
    BigDecimal allocatedTotal = new BigDecimal(0.00);
    BigDecimal spentTotal = new BigDecimal(0.00);
    BigDecimal tdiffTotal = new BigDecimal(0.00);

    try {
      APIAccess factory = APIAccess.getAPIAccess();
      SiteData site = factory.getSiteAPI().getSite(siteId);
      costCenters = factory.getAccountAPI().getAllCostCenters(accountId, Account.ORDER_BY_NAME);

      if (costCenters != null && costCenters.size() > 0) {
        Iterator i = costCenters.iterator();
        while (i.hasNext()) {
          CostCenterData cd = (CostCenterData) i.next();
          if (!Utility.isTrue(cd.getNoBudget(), false)) {
            BigDecimal allocated = site.getBudgetAllocated(cd.getCostCenterId());
            BigDecimal spent = site.getBudgetSpent(cd.getCostCenterId());
            BigDecimal tdiff = allocated.subtract(spent);
            allocatedTotal = allocatedTotal.add(allocated);
            spentTotal = spentTotal.add(spent);
            tdiffTotal = tdiffTotal.add(tdiff);
          }
        }
        totWCartTotal = tdiffTotal.subtract(cartAmt);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return totWCartTotal;
  }

   */
  /*
  private BigDecimal getAutoOrderFactorByAccount(Connection pCon, int accountId) throws SQLException {
    BigDecimal result = new BigDecimal(0);
    String query = "select clw_value from clw_property " +
                   " where bus_entity_id = " + accountId +
                   "       and short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.AUTO_ORDER_FACTOR + "'";
    Statement stmt = pCon.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    if (rs.next()) {
      result = rs.getBigDecimal(1);
    }
    rs.close();
    stmt.close();
    return result;
  }
   */
  /*
  private int getCatalogIdByAccount(Connection pCon, int pAccountId) throws SQLException {
    int result = 0;
    String query = "select catalog_id from clw_catalog " +
                   "where catalog_id in " +
                   "    (select distinct catalog_id from clw_catalog_assoc " +
                   "     where bus_entity_id = " + pAccountId +
                   "           and catalog_assoc_cd = '" + RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT + "')" +
                   "     and catalog_type_cd = '" + RefCodeNames.CATALOG_TYPE_CD.ACCOUNT + "'" +
                   "     and catalog_status_cd <> '" + RefCodeNames.CATALOG_STATUS_CD.INACTIVE + "'";

    Statement stmt = pCon.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    if (rs.next()) {
      result = rs.getInt(1);
    }
    rs.close();
    stmt.close();
    return result;
  }
   */
  /*
  private ContractItemDataVector getItemPrices(Connection pCon, int pSiteId, ArrayList pItemIds) throws SQLException {
    StringBuffer sqlSelectItems = new StringBuffer();
    for (Iterator i = pItemIds.iterator(); i.hasNext(); ) {
      int itemId = ((Integer)i.next()).intValue();
      sqlSelectItems.append(itemId).append(",");
    }
    sqlSelectItems.delete(sqlSelectItems.length()-1,sqlSelectItems.length());

    String query = "select aaa1.item_id, nvl(aaa2.amount, 0) from " +
               " (select item_id " +
               " from clw_item i " +
               " where i.item_id in( " + sqlSelectItems.toString() + ") ) aaa1 " +
               " left join " +
               " (select ci.amount, ci.item_id " +
               " from clw_contract_item ci ,  clw_contract c,  clw_catalog_assoc cata " +
               " where c.catalog_id = cata.catalog_id " +
               " and cata.bus_entity_id = " + pSiteId +
               " and ci.contract_id = c.contract_id " +
               " and c.contract_status_cd = 'ACTIVE') aaa2 " +
               " on aaa1.item_id = aaa2.item_id " +
               " order by aaa1.item_id";

    Statement stmt = pCon.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    ContractItemDataVector v = new ContractItemDataVector();
    while (rs.next()) {
      ContractItemData x = ContractItemData.createValue();
      x.setItemId(rs.getInt(1));
      x.setAmount(rs.getBigDecimal(2));
      v.add(x);
    }
    rs.close();
    stmt.close();
    return v;
  }
   */
    public class InvSiteItem {
        int siteId;
        int itemId;
        int orderQty;
        int autoOrderQty;
        int catalogId;
        BigDecimal orderAmt;
        BigDecimal autoOrderAmt;
        BigDecimal price;
    }

    public class InvSite {
        String siteName;
        java.util.Date invPeriodBeg;
        java.util.Date invPeriodEnd;
        java.util.Date cutoffDate;
        java.util.Date deliveryDate;
        int siteId;
        BigDecimal orderAmt;
        BigDecimal autoOrderAmt;
        java.util.Date lastModDate;
        String lastModBy;
        BigDecimal budgetAmt;
        BigDecimal spentAmt;
        String lastAction;
        int lateActionItemId;
    }
    private HashMap calculateNextOrderDates(Connection pCon, IdVector pSiteIds, int pAccountId)
    throws Exception {
        Statement stmt = null;
        DBCriteria dbc;
        int siteQty = 0;
        int calcQty = 0;
        Distributor distBean = APIAccess.getAPIAccess().getDistributorAPI();
        java.util.Date currdate = new java.util.Date();
        int accountId = pAccountId;
        //Get account cutoff
        if(pSiteIds == null) {
            dbc = new DBCriteria();
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, accountId);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
            pSiteIds = BusEntityAssocDataAccess.selectIdOnly(pCon,BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);
        }

        dbc = new DBCriteria();
        dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, accountId);
        dbc.addEqualTo(PropertyDataAccess.SHORT_DESC,
                RefCodeNames.PROPERTY_TYPE_CD.SCHEDULE_CUTOFF_DAYS);
        PropertyDataVector propertyDV = PropertyDataAccess.select(pCon,dbc);
        int accountCutoffDays = 0;
        if(propertyDV.size()==1) {
            PropertyData pD = (PropertyData) propertyDV.get(0);
            String ss = pD.getValue();
            try {
                accountCutoffDays = Integer.parseInt(ss);
            } catch(Exception exc) {
                log.info("Illegal account schedule cutoff days value: "+ss+" Account id: "+accountId);
            }
        } else if(propertyDV.size()>1) {
            log.info("Multiple account schedule cutoff days for the account. Account id: "+accountId);
        }


        // Get catalogs
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, pSiteIds);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_SITE);
        dbc.addOrderBy(CatalogAssocDataAccess.CATALOG_ID);
        CatalogAssocDataVector siteCataAssocDV =
                CatalogAssocDataAccess.select(pCon, dbc);
        HashMap siteCatalogHM = new HashMap();
        IdVector catalogIdV = new IdVector();
        int prevCatalogId =0;
        for(Iterator iter=siteCataAssocDV.iterator(); iter.hasNext();) {
            CatalogAssocData caD = (CatalogAssocData) iter.next();
            siteCatalogHM.put(new Integer(caD.getBusEntityId()), caD);
            int catalogId = caD.getCatalogId();
            if(prevCatalogId!=catalogId)  {
                prevCatalogId = catalogId;
                catalogIdV.add(new Integer(catalogId));
            }
        }

        //Get site zip codes
        dbc = new DBCriteria();
        dbc.addOneOf(AddressDataAccess.BUS_ENTITY_ID, pSiteIds);
        dbc.addEqualTo(AddressDataAccess.ADDRESS_TYPE_CD,
                RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
        dbc.addEqualTo(AddressDataAccess.PRIMARY_IND,1);
        dbc.addOrderBy(AddressDataAccess.POSTAL_CODE);
        AddressDataVector addressDV = AddressDataAccess.select(pCon,dbc);
        IdVector zipCodes = new IdVector();
        String prevZipCode = "-1";
        HashMap siteAddrHM = new HashMap();
        for(Iterator iter=addressDV.iterator(); iter.hasNext(); ) {
            AddressData aD = (AddressData) iter.next();
            String zipCode = aD.getPostalCode();
            if (zipCode == null) {
                zipCode = "";
            } else {
                zipCode = zipCode.trim();
                if(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES.equals(aD.getCountryCd())){
                    if (zipCode.length() > 5) {
                        zipCode = zipCode.substring(0, 5);
                    }
                }
            }
            aD.setPostalCode(zipCode);
            siteAddrHM.put(new Integer(aD.getBusEntityId()), aD);
            if(!prevZipCode.equals(zipCode)) {
                prevZipCode = zipCode;
                zipCodes.add(zipCode);
            }
        }

        //Get main distributors for catalogs
        dbc = new DBCriteria();
        dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID,catalogIdV);
        dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                RefCodeNames.CATALOG_ASSOC_CD.CATALOG_MAIN_DISTRIBUTOR);
        dbc.addOrderBy(CatalogAssocDataAccess.BUS_ENTITY_ID);
        CatalogAssocDataVector catAssocDV =
                CatalogAssocDataAccess.select(pCon, dbc);
             /*
              String q = "select cs.bus_entity_id, count(cs.item_id) qty " +
                         " from clw_catalog_structure cs " +
                         " where catalog_id = " + catid +
                         " group by cs.bus_entity_id " +
                         " order by qty desc ";
              stmt = pCon.createStatement();
              ResultSet rs = stmt.executeQuery(q);
              if(rs.next()) {
                distId = rs.getInt(1);
              }
              rs.close();
              stmt.close();
              */
        HashMap catalogDistHM = new HashMap();
        IdVector distIdV = new IdVector();
        int prevDistId = 0;
        for(Iterator iter=catAssocDV.iterator(); iter.hasNext();) {
            CatalogAssocData caD = (CatalogAssocData) iter.next();
            catalogDistHM.put(new Integer(caD.getCatalogId()),caD);
            int distId = caD.getBusEntityId();
            if(prevDistId!=distId) {
                prevDistId = distId;
                distIdV.add(new Integer(distId));
            }
        }

/////////////////////////////////////////////////

        // dist schedules
        dbc = new DBCriteria();
        dbc.addOneOf(ScheduleDataAccess.BUS_ENTITY_ID,distIdV);
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_STATUS_CD,
                RefCodeNames.SCHEDULE_STATUS_CD.ACTIVE);
        dbc.addEqualTo(ScheduleDataAccess.SCHEDULE_TYPE_CD,
                RefCodeNames.SCHEDULE_TYPE_CD.DELIVERY);
        ScheduleDataVector scheduleDV =
                ScheduleDataAccess.select(pCon,dbc);
        IdVector scheduleIdV = new IdVector();
        HashMap schedDistHM = new HashMap();
        for(Iterator iter=scheduleDV.iterator(); iter.hasNext();) {
            ScheduleData sD = (ScheduleData) iter.next();
            scheduleIdV.add(new Integer(sD.getScheduleId()));
            schedDistHM.put(new Integer(sD.getScheduleId()), sD);
        }


        dbc = new DBCriteria();
        dbc.addOneOf(ScheduleDetailDataAccess.SCHEDULE_ID, scheduleIdV);
        dbc.addEqualTo(ScheduleDetailDataAccess.SCHEDULE_DETAIL_CD,
                RefCodeNames.SCHEDULE_DETAIL_CD.ZIP_CODE);
        dbc.addOneOf(ScheduleDetailDataAccess.VALUE,zipCodes);

        ScheduleDetailDataVector scheduleDetailDV =
                ScheduleDetailDataAccess.select(pCon,dbc);
        HashMap distZipSchedHM = new HashMap();

        for(Iterator iter=scheduleDetailDV.iterator(); iter.hasNext();) {
            ScheduleDetailData sdD = (ScheduleDetailData) iter.next();
            String country = sdD.getCountryCd();
            if(country==null) country = RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES;
            Integer schedIdI = new Integer(sdD.getScheduleId());
            ScheduleData sD = (ScheduleData) schedDistHM.get(schedIdI);
            int distId = sD.getBusEntityId();
            String key = distId + country + sdD.getValue();
            if(!distZipSchedHM.containsKey(key)) {
                distZipSchedHM.put(key, schedIdI);
                log.info("Dist Scedule: key: "+key+" Schedule id: "+schedIdI);
            }
        }
        //YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY

        //Get site shedules
        SiteDeliveryScheduleViewVector siteDeliverySchedDV =
                BusEntityDAO.getDeliverySchs(pCon, pAccountId,0);

        HashMap scheduleJoinHM = new HashMap();
        HashMap integratedSchedHM = new HashMap();
        HashMap siteScheduleHM = new HashMap();
        for(Iterator iter=siteDeliverySchedDV.iterator(); iter.hasNext();) {
            SiteDeliveryScheduleView siteDelivSchVw = (SiteDeliveryScheduleView) iter.next();
            int siteId = siteDelivSchVw.getSiteId();
            log.info("AAAAAAAAAAAAAAAAA analyzing site id: " + siteId);
            Integer siteIdI = new Integer(siteId);
            AddressData aD  = (AddressData) siteAddrHM.get(siteIdI);
            CatalogAssocData siteCatalogCaD = (CatalogAssocData) siteCatalogHM.get(siteIdI);
            if(siteCatalogCaD==null) {
                log.info("No catalog. Site id: "+siteId);
                continue;
            }
            int catalogId = siteCatalogCaD.getCatalogId();
            Integer catalogIdI = new Integer(catalogId);
            CatalogAssocData catalogDistCaD = (CatalogAssocData) catalogDistHM.get(catalogIdI);
            if(catalogDistCaD==null) {
                log.info("No dist for catalog. Catalog  id: "+catalogIdI);
                continue;
            }
            int distId = catalogDistCaD.getBusEntityId();
            String distSchedKey = distId + aD.getCountryCd() + aD.getPostalCode();
            Integer scheduleIdI = (Integer) distZipSchedHM.get(distSchedKey);
            if(scheduleIdI==null) {
                log.info("No scedule for the site. Site id: "+siteId+" shced key: "+distSchedKey);
                continue;
            }
            int scheduleId = scheduleIdI.intValue();
            ScheduleJoinView sjVw = (ScheduleJoinView) scheduleJoinHM.get(scheduleIdI);
            if(sjVw==null) {
                sjVw = distBean.getDeliveryScheduleById(scheduleId);
                scheduleJoinHM.put(scheduleIdI,sjVw);
            }
            String siteScheduleKey = siteDelivSchVw.getSiteScheduleType()+
                    siteDelivSchVw.getIntervWeek()+
                    (siteDelivSchVw.getWeek1ofMonth()?"1":"")+
                    (siteDelivSchVw.getWeek2ofMonth()?"2":"")+
                    (siteDelivSchVw.getWeek3ofMonth()?"3":"")+
                    (siteDelivSchVw.getWeek4ofMonth()?"4":"")+
                    (siteDelivSchVw.getLastWeekofMonth()?"5":"");

            String integratedKey = siteScheduleKey + scheduleId;
            ScheduleOrderDates sods = (ScheduleOrderDates) integratedSchedHM.get(integratedKey);
            siteQty++;
            if(sods==null) {
                calcQty++;
                ScheduleProc scheduleProc = new ScheduleProc(sjVw, siteDelivSchVw, accountCutoffDays);
                scheduleProc.initSchedule();
                sods = scheduleProc.getOrderDeliveryDates(currdate,currdate);

            }
			if(sods != null){
				log.info("site id: "+siteIdI+" -> "+sods.getNextOrderCutoffDate()+" - "+sods.getNextOrderDeliveryDate());
			}else{
				log.info("site id: "+siteIdI+" -> Null order cutoff and next delivery");
			}
            siteScheduleHM.put(siteIdI,sods);
        }
        log.info("siteQty: "+siteQty+" Sched calc qty: "+calcQty);
        return siteScheduleHM;
    }}



