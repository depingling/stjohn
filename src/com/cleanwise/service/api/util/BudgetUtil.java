/*
 * BudgetUtil.java
 *
 * Created on April 6, 2005, 5:29 PM
 */

package com.cleanwise.service.api.util;

import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.BudgetViewWrapper;
import org.apache.log4j.Category;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Access to functions involving the budgeting system.  Initial version was taken
 * from the site bean and adapted to be at the account level.
 *
 * @author bstevens
 */
public class BudgetUtil {

    private static Category log = Category.getInstance(BudgetUtil.class.getName());

    Connection mCon;
    private BusEntityDAO mBDAO;
    private HashMap mFiscalPeriodViewCache = new HashMap();

    private void evaluateAmounts(BudgetSpendView pBudgetSpendView,
                                 int pAccountId,
                                 int pSiteId,
                                 CostCenterData pCostCenter,
                                 String pAccountBudgetAccrualCd) throws SQLException {

        int costCenterId = 0;

        if (pCostCenter != null) {
            costCenterId = pCostCenter.getCostCenterId();
        }

        BudgetViewVector lBudgets = BudgetDAO.getBudgetsForSite(mCon, pAccountId, pSiteId, costCenterId, pBudgetSpendView.getBudgetYear());
        if (lBudgets != null) {

            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> found: " + lBudgets.size() + " budgets <<<<<<<<<<<<<<<<<<<<<");

            BudgetView bv;
            BigDecimal allocated = new BigDecimal(0);
            BigDecimal spent = new BigDecimal(0);
            Integer budgetThreshold = null;

            for (Object lBudget : lBudgets) {

                bv = (BudgetView) lBudget;

                log.debug("processing budget id: " + bv.getBudgetData().getBudgetId());

                // Now get the total allocated up to this period.
                BudgetViewWrapper bvw = new BudgetViewWrapper(bv);
                for (int period = 1; period <= pBudgetSpendView.getNumberOfBudgetPeriods(); period++) {
                    allocated = evaluateABudgetPeriod(pAccountBudgetAccrualCd, pBudgetSpendView.getBudgetPeriod(), period, allocated, bvw.getAmount(period));
                }
                BigDecimal currPeriodAlloc = bvw.getAmount(pBudgetSpendView.getBudgetPeriod());
                pBudgetSpendView.setUnlimitedBudget(currPeriodAlloc == null);
                if (!pBudgetSpendView.getUnlimitedBudget()){
	                spent = spent.add(_calculateAmountSpent(pAccountId,
	                        pSiteId,
	                        bv.getBudgetData().getCostCenterId(),
	                        pBudgetSpendView.getBudgetYear(),
	                        pBudgetSpendView.getBudgetPeriod(),
	                        pAccountBudgetAccrualCd,
	                        bv.getBudgetData().getBudgetTypeCd()));
                }

            }

            if (pCostCenter != null) {
                budgetThreshold = getBudgetThresholdPercent(mCon,
                        pAccountId,
                        pSiteId,
                        pCostCenter.getCostCenterId(),
                        pBudgetSpendView.getBudgetYear());
            }

            pBudgetSpendView.setAmountAllocated(Utility.bdNN(allocated));
            pBudgetSpendView.setAmountSpent(Utility.bdNN(spent));
            pBudgetSpendView.setBudgetThresholdPercent(budgetThreshold);

        }

    }

    private Integer getBudgetThresholdPercent(Connection pCon, int pAccountId, int pSiteId, int pCostCenterId, int pBudgetYear) throws SQLException {

        log.debug("getBudgetThresholdPercent()=> BEGIN");

        log.debug("getBudgetThresholdPercent()=> pAccountId: " + pAccountId +
                ", pSiteId: " + pSiteId +
                ", pCostCenterId: " + pCostCenterId +
                ", pBudgetYear: " + pBudgetYear);

        int storeId = BusEntityDAO.getStoreForAccount(pCon, pAccountId);

        boolean budgetThresholdFl = false;
        String budgetThresholdType = null;
        Integer retVal = null;

        // Get Budget Threshold Flag
        {
            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, storeId);
            dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_FL);
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

            PropertyDataVector storeProperties = PropertyDataAccess.select(pCon, dbc);
            if (!storeProperties.isEmpty()) {
                budgetThresholdFl = Utility.isTrue(((PropertyData) storeProperties.get(0)).getValue());
            }

        }

        log.debug("getBudgetThresholdPercent()=> storeId: " + storeId + ", budgetThresholdFl: " + budgetThresholdFl);

        if (budgetThresholdFl) {

            // Get Budget Threshold Type
            {
                DBCriteria dbc = new DBCriteria();

                dbc.addEqualTo(PropertyDataAccess.BUS_ENTITY_ID, pAccountId);
                dbc.addEqualTo(PropertyDataAccess.SHORT_DESC, RefCodeNames.PROPERTY_TYPE_CD.BUDGET_THRESHOLD_TYPE);
                dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.EXTRA);
                dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD, RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);

                PropertyDataVector storeProperties = PropertyDataAccess.select(pCon, dbc);
                if (!storeProperties.isEmpty()) {
                    budgetThresholdType = ((PropertyData) storeProperties.get(0)).getValue();
                }
            }

            log.debug("getBudgetThresholdPercent()=> budgetThresholdType: " + budgetThresholdType);

            if (isUsedSiteBudgetThreshold(budgetThresholdFl, budgetThresholdType)) {
                BudgetDataVector budgets = BudgetDAO.getBudgetDataForSite(pCon, pAccountId, pSiteId, pCostCenterId, pBudgetYear);
                if (!budgets.isEmpty()) {
                    try {
                        String budgetThreshold = ((BudgetData) budgets.get(0)).getBudgetThreshold();
                        if (Utility.isSet(budgetThreshold)) {
                            retVal = Utility.parsePercentInt(budgetThreshold);
                        }
                    } catch (NumberFormatException e) {
                        log.error(e);
                    }
                }
            } else if (isUsedAccountBudgetThreshold(budgetThresholdFl, budgetThresholdType)) {

                DBCriteria dbc = new DBCriteria();
                dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pAccountId);
                dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);

                String catalogReq = CatalogAssocDataAccess.getSqlSelectIdOnly(CatalogAssocDataAccess.CATALOG_ID, dbc);

                dbc = new DBCriteria();
                dbc.addOneOf(CostCenterAssocDataAccess.CATALOG_ID, catalogReq);
                dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ID, pCostCenterId);
                dbc.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD, RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
                dbc.addOrderBy(CostCenterAssocDataAccess.COST_CENTER_ASSOC_ID);

                CostCenterAssocDataVector costCenters = CostCenterAssocDataAccess.select(pCon, dbc);

                if (!costCenters.isEmpty()) {
                    try {
                        String budgetThreshold = ((CostCenterAssocData) costCenters.get(0)).getBudgetThreshold();
                        if (Utility.isSet(budgetThreshold)) {
                            retVal = Utility.parsePercentInt(budgetThreshold);
                        }
                    } catch (NumberFormatException e) {
                        log.error(e);
                    }
                }
            }
        }

        log.debug("getBudgetThresholdPercent()=> END.retVal: " + retVal);

        return retVal;

    }


    /**
     * Calculates the amount spent against the budget using the site ledger table
     */
    private BigDecimal _calculateAmountSpent(int pAccountId,
                                             int pSiteId,
                                             int pCostCenterId,
                                             int pBudgetYear,
                                             int pBudgetPeriod,
                                             String pAccountBudgetAccrualCd,
                                             String pBudgetTypeCd) throws SQLException {

        Statement stmt = mCon.createStatement();
        DBCriteria actualCrit = new DBCriteria();
        String sl = SiteLedgerDataAccess.CLW_SITE_LEDGER;
        actualCrit.addJoinTableEqualTo(sl, SiteLedgerDataAccess.ENTRY_TYPE_CD, RefCodeNames.LEDGER_ENTRY_TYPE_CD.PRIOR_PERIOD_BUDGET_ACTUAL);

        String col_sloid = "sl." + SiteLedgerDataAccess.ORDER_ID;
        String col_ooid = OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ORDER_ID;
        String col_cc = "sl." + SiteLedgerDataAccess.COST_CENTER_ID;
        String col_fiscal_year = "sl." + SiteLedgerDataAccess.BUDGET_YEAR;

        // Sum up all the financial activity for the budget
        // period in question.
        String query = " SELECT SUM( " + SiteLedgerDataAccess.AMOUNT +
                " ) from " +
                SiteLedgerDataAccess.CLW_SITE_LEDGER + " sl, " +
                OrderDataAccess.CLW_ORDER + " where " +
                col_sloid + " = " + col_ooid + " and " +
                OrderDataAccess.ORDER_STATUS_CD + " in " +
                OrderDAO.kGoodOrderStatusSqlList + " and " +
                "(" + OrderDataAccess.ORDER_BUDGET_TYPE_CD + " not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') or " + OrderDataAccess.ORDER_BUDGET_TYPE_CD + " is null)";

      String subQuery = "";
      if (RefCodeNames.BUDGET_TYPE_CD.ACCOUNT_BUDGET.equals(pBudgetTypeCd)) {
            subQuery += " and " + OrderDataAccess.CLW_ORDER + "." + OrderDataAccess.ACCOUNT_ID + " = " + pAccountId;
            actualCrit.addJoinCondition(sl, SiteLedgerDataAccess.SITE_ID, BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID);
            actualCrit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountId);
        } else if (RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET.equals(pBudgetTypeCd)) {
            subQuery += " and sl." + SiteLedgerDataAccess.SITE_ID + " = " + pSiteId;
            actualCrit.addJoinTableEqualTo(sl, SiteLedgerDataAccess.SITE_ID, pSiteId);
        } else {
            throw new RuntimeException("Unknown budget type cd: " + pBudgetTypeCd + " for site id: " + pSiteId);
        }

        if (pCostCenterId > 0) {
            subQuery += " and " + col_cc + " = " + pCostCenterId;
            actualCrit.addJoinTableEqualTo(sl, SiteLedgerDataAccess.COST_CENTER_ID, pCostCenterId);
        } else {
            subQuery += " and " + col_cc + " > 0 ";
            actualCrit.addJoinTableGreaterOrEqual(sl, SiteLedgerDataAccess.COST_CENTER_ID, 0);
        }

        if (RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_PERIOD.equals(pAccountBudgetAccrualCd)) {
            String col_budget_period = "sl." + SiteLedgerDataAccess.BUDGET_PERIOD;
            subQuery += " and " + col_budget_period + " = " + pBudgetPeriod;
            actualCrit.addJoinTableEqualTo(sl, SiteLedgerDataAccess.BUDGET_PERIOD, pBudgetPeriod);
        }

        subQuery += " and " + col_fiscal_year + " = " + pBudgetYear;
        actualCrit.addJoinTableEqualTo(sl, SiteLedgerDataAccess.BUDGET_YEAR, pBudgetYear);

        query += subQuery;
        log.debug("sum query: " + query);

        ResultSet rs = stmt.executeQuery(query);

        BigDecimal netAmount = null;
        if (rs.next()) {
            netAmount = rs.getBigDecimal(1);

        }
        if (null == netAmount) {
            netAmount = new BigDecimal(0);
        }

        rs.close();
        stmt.close();

        SiteLedgerDataVector actualsRes = new SiteLedgerDataVector();

        JoinDataAccess.selectTableInto(new SiteLedgerDataAccess(), actualsRes, mCon, actualCrit, 0);
        Iterator it = actualsRes.iterator();
        while (it.hasNext()) {
            SiteLedgerData s = (SiteLedgerData) it.next();
            if (s.getAmount() != null) {
                netAmount = netAmount.add(s.getAmount());
            }
        }
        BigDecimal consolodatedAmount = getConsolidatedSpendAmount(subQuery);
        if (consolodatedAmount != null ) {
          netAmount = netAmount.add(consolodatedAmount);
        }
          return netAmount;
    }
    /*
    * Calculates the amount spent against the budget using the site ledger table
    */
     public BigDecimal getConsolidatedSpendAmount(String subQuery) throws SQLException {


       String query =
         " SELECT  SUM (AMOUNT) " +
         "   FROM CLW_SITE_LEDGER sl, CLW_ORDER  ,  " +
         "        CLW_ORDER_ASSOC oa, CLW_ORDER o2  " +
         "  WHERE sl.ORDER_ID = CLW_ORDER.ORDER_ID  " +

         "        AND CLW_ORDER.ORDER_STATUS_CD = '" +RefCodeNames.ORDER_STATUS_CD.CANCELLED + "' " +
         "        AND CLW_ORDER.ORDER_TYPE_CD = '" + RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED + "' " +
         "        AND  CLW_ORDER.ORDER_ID = oa.ORDER1_ID " +
         "        AND  o2.ORDER_ID = oa.ORDER2_ID  " +
         "        AND  oa.ORDER_ASSOC_CD='" + RefCodeNames.ORDER_ASSOC_CD.CONSOLIDATED + "' " +
         "        AND  o2.ORDER_STATUS_CD in " +OrderDAO.kGoodOrderStatusSqlList +

         "        AND (CLW_ORDER.ORDER_BUDGET_TYPE_CD not in ('" + RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE + "', '" + RefCodeNames.ORDER_BUDGET_TYPE_CD.REBILL + "') " +
         "             OR CLW_ORDER.ORDER_BUDGET_TYPE_CD IS NULL) " ;

         query += subQuery;
         log.debug("[BudgetUtil]. getConsolidatedSpendAmount() :" + query);

         Statement stmt = mCon.createStatement();
         ResultSet rs = stmt.executeQuery(query);

         BigDecimal amount = new BigDecimal(0);
         if (rs.next()) {
             amount = rs.getBigDecimal(1);
         }
         rs.close();
         stmt.close();
         return amount;

     }

    /*
     * Calculates the amount spent against the budget using the site ledger table
     */
    public BigDecimal _calculateWorkOrderSiteAmountSpent(int pSiteId,
                                                          int pCostCenterId,
                                                          int pBudgetYear,
                                                          int pBudgetPeriod) throws Exception {


//        StringBuffer workOrderQuery = new StringBuffer();
//
//        StringBuffer ccCondition = new StringBuffer();
//        StringBuffer byCondition = new StringBuffer();
//        StringBuffer bpCondition = new StringBuffer();
//
//        workOrderQuery.append("SELECT " + WorkOrderDataAccess.WORK_ORDER_ID + " FROM " + WorkOrderDataAccess.CLW_WORK_ORDER);
//
//        workOrderQuery.append(" WHERE ");
//        workOrderQuery.append(WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.BUS_ENTITY_ID);
//        workOrderQuery.append("=");
//        workOrderQuery.append(pSiteId);
//
////        workOrderQuery.append(" AND ");
////        workOrderQuery.append(WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.STATUS_CD);
////        workOrderQuery.append(" IN ");
////        workOrderQuery.append(WorkOrderUtil.kGoodWorkOrderStatusSqlList);
//
//        if (pCostCenterId > 0) {
//            ccCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.COST_CENTER_ID);
//            ccCondition.append("=");
//            ccCondition.append(pCostCenterId);
//        } else {
//            ccCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.COST_CENTER_ID);
//            ccCondition.append(">");
//            ccCondition.append(0);
//        }
//
//        if (pBudgetYear > 0) {
//            byCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_YEAR);
//            byCondition.append("=");
//            byCondition.append(pBudgetYear);
//        } else {
//            byCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_YEAR);
//            byCondition.append(">");
//            byCondition.append(0);
//        }
//
//        if (pBudgetPeriod > 0) {
//            bpCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_PERIOD);
//            bpCondition.append("=");
//            bpCondition.append(pBudgetPeriod);
//        } else {
//            bpCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_PERIOD);
//            bpCondition.append(">");
//            bpCondition.append(0);
//        }
//
//        StringBuffer siteLedgerQuery = new StringBuffer();
//
//        siteLedgerQuery.append("SELECT SUM(" + SiteLedgerDataAccess.AMOUNT + ") FROM " + SiteLedgerDataAccess.CLW_SITE_LEDGER);
////        siteLedgerQuery.append(",(");
////        siteLedgerQuery.append(workOrderQuery);
////        siteLedgerQuery.append(") workOrder");
//
//        siteLedgerQuery.append(" WHERE ");
//        siteLedgerQuery.append("workOrder." + WorkOrderDataAccess.WORK_ORDER_ID);
//        siteLedgerQuery.append("=");
//        siteLedgerQuery.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.WORK_ORDER_ID);
//
//        siteLedgerQuery.append(" AND ");
//        siteLedgerQuery.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.SITE_ID);
//        siteLedgerQuery.append("=");
//        siteLedgerQuery.append(pSiteId);
//
//        siteLedgerQuery.append(" AND ");
//        siteLedgerQuery.append(ccCondition);
//
//        siteLedgerQuery.append(" AND ");
//        siteLedgerQuery.append(byCondition);
//
//        siteLedgerQuery.append(" AND ");
//        siteLedgerQuery.append(bpCondition);
//


        String query = String.format(
	        "SELECT  SUM (amount) " +
	        "FROM    clw_site_ledger " +
	        "WHERE   clw_site_ledger.cost_center_id = %1d " +
	        	"AND clw_site_ledger.budget_year = %2d " +
	        	"AND clw_site_ledger.budget_period = %3d",
	        pCostCenterId,
	        pBudgetYear,
	        pBudgetPeriod);

        Statement stmt = mCon.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        BigDecimal netAmount = new BigDecimal(0);
        if (rs.next()) {
            netAmount = rs.getBigDecimal(1);
        }

        return netAmount;

    }

      /*
     * Calculates the amount spent against the budget using the site ledger table
     */
      public BigDecimal _calculateWorkOrderAccountAmountSpent(int pAccountId,
                                                               int pCostCenterId,
                                                               int pBudgetYear,
                                                               int pBudgetPeriod) throws Exception {

/*
          StringBuffer workOrderQuery = new StringBuffer();

          StringBuffer ccCondition = new StringBuffer();
          StringBuffer byCondition = new StringBuffer();
          StringBuffer bpCondition = new StringBuffer();

          workOrderQuery.append("SELECT " + WorkOrderDataAccess.WORK_ORDER_ID + " FROM " + WorkOrderDataAccess.CLW_WORK_ORDER);

          DBCriteria siteIdsCrit = new DBCriteria();
          siteIdsCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
          siteIdsCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pAccountId);

          workOrderQuery.append(",(");
          workOrderQuery.append(BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteIdsCrit));
          workOrderQuery.append(")");
          workOrderQuery.append(" site ");

          workOrderQuery.append(" WHERE ");
          workOrderQuery.append(WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.BUS_ENTITY_ID);
          workOrderQuery.append("=");
          workOrderQuery.append("site."+BusEntityAssocDataAccess.BUS_ENTITY1_ID);

//          workOrderQuery.append(" AND ");
//          workOrderQuery.append(WorkOrderDataAccess.CLW_WORK_ORDER + "." + WorkOrderDataAccess.STATUS_CD);
//          workOrderQuery.append(" IN ");
//          workOrderQuery.append(WorkOrderUtil.kGoodWorkOrderStatusSqlList);

          if (pCostCenterId > 0) {
              ccCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.COST_CENTER_ID);
              ccCondition.append("=");
              ccCondition.append(pCostCenterId);
          } else {
              ccCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.COST_CENTER_ID);
              ccCondition.append(">");
              ccCondition.append(0);
          }

          if (pBudgetYear > 0) {
              byCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_YEAR);
              byCondition.append("=");
              byCondition.append(pBudgetYear);
          } else {
              byCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_YEAR);
              byCondition.append(">");
              byCondition.append(0);
          }

          if (pBudgetPeriod > 0) {
              bpCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_PERIOD);
              bpCondition.append("=");
              bpCondition.append(pBudgetPeriod);
          } else {
              bpCondition.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.BUDGET_PERIOD);
              bpCondition.append(">");
              bpCondition.append(0);
          }

          StringBuffer siteLedgerQuery = new StringBuffer();

          siteLedgerQuery.append("SELECT SUM(" + SiteLedgerDataAccess.AMOUNT + ") FROM " + SiteLedgerDataAccess.CLW_SITE_LEDGER);
          siteLedgerQuery.append(",(");
          siteLedgerQuery.append(workOrderQuery);
          siteLedgerQuery.append(") workOrder");

          workOrderQuery.append(",(");
          workOrderQuery.append(BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, siteIdsCrit));
          workOrderQuery.append(")");
          workOrderQuery.append(" site ");

          siteLedgerQuery.append(" WHERE ");
          siteLedgerQuery.append("workOrder." + WorkOrderDataAccess.WORK_ORDER_ID);
          siteLedgerQuery.append("=");
          siteLedgerQuery.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.WORK_ORDER_ID);

          siteLedgerQuery.append(" AND ");
          siteLedgerQuery.append(SiteLedgerDataAccess.CLW_SITE_LEDGER + "." + SiteLedgerDataAccess.SITE_ID);
          siteLedgerQuery.append("=");
          siteLedgerQuery.append("site."+BusEntityAssocDataAccess.BUS_ENTITY1_ID);

          siteLedgerQuery.append(" AND ");
          siteLedgerQuery.append(ccCondition);

          siteLedgerQuery.append(" AND ");
          siteLedgerQuery.append(byCondition);

          siteLedgerQuery.append(" AND ");
          siteLedgerQuery.append(bpCondition);

*/

        String query = String.format(
	        "SELECT  SUM (amount) " +
	        "FROM    clw_site_ledger " +
	        "WHERE   clw_site_ledger.cost_center_id = %1d " +
	        	"AND clw_site_ledger.budget_year = %2d " +
	        	"AND clw_site_ledger.budget_period = %3d",
	        pCostCenterId,
	        pBudgetYear,
	        pBudgetPeriod);

          Statement stmt = mCon.createStatement();
          ResultSet rs = stmt.executeQuery(query);

          BigDecimal netAmount = new BigDecimal(0);
          if (rs.next()) {
              netAmount = rs.getBigDecimal(1);
          }

          return netAmount;

      }
    /**
     *Utility method to start accruing against a given budget.  If by period is specified the
     *amounts are added only if the 2 periods are equal, otherwise they are added if the start
     *period is less than or equal to the passed in period.
     */
    private BigDecimal evaluateABudgetPeriod(String pAccountBudgetAccrualCd,int pCurrentBudgetPeriod, int evaluatingBudgetPeriod,
            BigDecimal startAmt, BigDecimal periodAmt){

        if(RefCodeNames.BUDGET_ACCRUAL_TYPE_CD.BY_PERIOD.equals(pAccountBudgetAccrualCd)){
            if(pCurrentBudgetPeriod == evaluatingBudgetPeriod){
                return Utility.addAmt(startAmt, periodAmt);
            }
        }else{
            if(pCurrentBudgetPeriod >= evaluatingBudgetPeriod){
                return Utility.addAmt(startAmt, periodAmt);
            }
        }
        return startAmt;
    }

    /**
     *  Initializes an empty BudgetSpendView object
     * @param pSiteId the site id to use for initialization
     * @param pCostCenterId optional cost center id
     * @param pBudget the @see BudgetSpendView object to operate on.  This is the same object
     * 	that is returned
     * @return BudgetSpendView the modified passed in pBudget object
     */
    private BudgetSpendView initBudgetSpendView(int pSiteId, int pCostCenterId,BudgetSpendView pBudget, int pOptFiscPeriod)
    throws Exception {

        pBudget.setCurrentBudgetPeriod(1);

        pBudget.setCostCenterId(pCostCenterId);
        if (pCostCenterId == 0) {
            pBudget.setCostCenterName("NONE");
        } else {
            CostCenterData ccd = CostCenterDataAccess.select
                    (mCon, pCostCenterId);
            pBudget.setCostCenterName(ccd.getShortDesc());
        }

        try {
            Integer siteInt = new Integer(pSiteId);
            FiscalPeriodView fiscalInfo = (FiscalPeriodView) mFiscalPeriodViewCache.get(siteInt);
            if(fiscalInfo == null){
                fiscalInfo = mBDAO.getFiscalInfo(mCon, pSiteId);
                mFiscalPeriodViewCache.put(siteInt,fiscalInfo);
            }

            if (null == fiscalInfo) {
                log.info("No fiscal info for pSiteId="
                        + pSiteId);
                return pBudget;
            }

            FiscalCalenderView fc = fiscalInfo.getFiscalCalenderView();
            if (null == fc) {
                log.info("No fiscal calendar for pSiteId="
                        + pSiteId);
                return pBudget;
            }
            int thisPeriod;
            if(pOptFiscPeriod == 0){
            	thisPeriod = fiscalInfo.getCurrentFiscalPeriod();
            }else{
            	thisPeriod = pOptFiscPeriod;
            }
            String p = fc.getFiscalCalender().getPeriodCd();

            pBudget.setBudgetYear(fc.getFiscalCalender().getFiscalYear());
            pBudget.setBudgetPeriod(thisPeriod);
            pBudget.setCurrentBudgetPeriod(thisPeriod);
            pBudget.setCurrentBudgetYear(fc.getFiscalCalender().getFiscalYear());
            pBudget.setNumberOfBudgetPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(fc));

            log.debug("budget period cd: " + p + " fc=" + fc + " pBudget=" + pBudget);

            return pBudget;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("_initBudgetDates: error: " + e);
        }

        return pBudget;
    }


   /** Creates a new instance of BudgetUtil */
    public BudgetUtil(Connection pCon) {
        mCon = pCon;
        mBDAO = new BusEntityDAO();
    }


    /**
     *Calculates the budget spent for the supplied site.  This depends greatly
     *upon the account configuration and the budget configuration.  If the budget is
     *setup at the account level then this is taken into account for example.  The
     *design is that the caller does not need to know this, but in the context of the
     *current site this will return the amount spent against the budget.
     *@param  pSiteId              the site id to operate off of
     *@param  pCostCenterId        optional cost center id
     *@return                      The budgetSpent2 value
     *@exception  RemoteException  Description of the Exception
     */
    public BudgetSpendView getBudgetSpentForSite(int pSiteId, int pCostCenterId)
    throws Exception {
	    CostCenterData csd = null;
	    if(pCostCenterId > 0){
	        csd = CostCenterDataAccess.select(mCon,pCostCenterId);
	    }

	    int accountId = BusEntityDAO.getAccountForSite(mCon, pSiteId);
	    PropertyUtil p = new PropertyUtil(mCon);
	    String accrualCd = p.fetchValueIgnoreMissing(0,accountId,RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD);
	    return getBudgetSpentForSite(accountId, pSiteId, csd, accrualCd);
    }

    /**
     *Calculates the budget spent for the supplied site.  This depends greatly
     *upon the account configuration and the budget configuration.  If the budget is
     *setup at the account level then this is taken into account for example.  The
     *design is that the caller does not need to know this, but in the context of the
     *current site this will return the amount spent against the budget.
     *
     *@param  pSiteId        The Site Id
     *@param pCostCenter     optional used to query on a specific cost center
     */
    public BudgetSpendView getBudgetSpentForSite(int pSiteId,CostCenterData pCostCenter)
    throws Exception {
    	return getBudgetSpentForSite(0,pSiteId,pCostCenter,null);
    }


    private String getBudgetAccrualTypeCd(int pAccountId) throws RemoteException{
    	PropertyUtil pru = new PropertyUtil(mCon);
    	return pru.fetchValueIgnoreMissing(0,pAccountId,RefCodeNames.PROPERTY_TYPE_CD.BUDGET_ACCRUAL_TYPE_CD);
    }

    /**
     * Calculates the budget spent for the supplied site.  This depends greatly
     * upon the account configuration and the budget configuration.  If the budget is
     * setup at the account level then this is taken into account for example.  The
     * design is that the caller does not need to know this, but in the context of the
     * current site this will return the amount spent against the budget.
     *
     * @param pAccountId              if avaliable this may be provided, method will query the db if it is not provided
     * @param pSiteId                 The Site Id
     * @param pCostCenter             optional cost center.  If left null will calculate amount spent for ALL cost centers
     * @param pAccountBudgetAccrualCd if avaliable this may be provided, method will query the db if it is not provided
     * @return Description of the Return Value
     * @throws Exception Description of the Exception
     */
    public BudgetSpendView getBudgetSpentForSite(int pAccountId,
                                                 int pSiteId,
                                                 CostCenterData pCostCenter,
                                                 String pAccountBudgetAccrualCd) throws Exception {

        BudgetSpendView budget = BudgetSpendView.createValue();
        if (pCostCenter == null) {
            budget.setAllocateFreight(false);
        } else {
            budget.setAllocateFreight(Utility.isTrue(pCostCenter.getAllocateFreight()));
            budget.setCostCenterTaxType(pCostCenter.getCostCenterTaxType());
        }

        if (budget.getCostCenterTaxType() == null) {
            budget.setCostCenterTaxType(RefCodeNames.COST_CENTER_TAX_TYPE.DONT_ALLOCATE_SALES_TAX);
        }

        //get account budgets
        if (pAccountId == 0) {
            pAccountId = BusEntityDAO.getAccountForSite(mCon, pSiteId);
        }

        if (pAccountBudgetAccrualCd == null) {
            pAccountBudgetAccrualCd = getBudgetAccrualTypeCd(pAccountId);
        }

        // Search through the site ledger for the period
        // identified by the cost center and sum the financial
        // activity.

        // Get the start of this budget period.
        budget.setBusEntityId(pSiteId);
        int costCenterId = 0;
        if (pCostCenter != null) {
            costCenterId = pCostCenter.getCostCenterId();
        }

        budget = initBudgetSpendView(pSiteId, costCenterId, budget, 0);

        log.debug("-- budget0=" + budget);

        evaluateAmounts(budget, pAccountId, pSiteId, pCostCenter, pAccountBudgetAccrualCd);

        return budget;
    }

    /**
     * Returns all of the cost centers for the supplied site id
     * @param pSiteId the site id
     * @return a List of CostCenterData objects
     */
    public CostCenterDataVector getCostCentersForSite(int pSiteId) throws SQLException{
        return getCostCentersForBusEntity(pSiteId,RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
    }

    /**
     *  Returns the amount spent accross cost centers @see getBudgetSpentForSite
     *  @param pSiteId the site if of the budget
     */
    public BudgetSpendViewVector getAllBudgetSpentForSite(int pSiteId)
    throws Exception {
    	return getAllBudgetSpentForSite(0,pSiteId,null);
    }


    /**
     *  Returns the amount spent accross cost centers @see getBudgetSpentForSite
     *  @param pAccountId optional will get from db if not provided
     *  @param pSiteId the site if of the budget
     *  @param pBudgetAccrualTypeCd optional will get from db if not provided
     */
    public BudgetSpendViewVector getAllBudgetSpentForSite( int pAccountId, int pSiteId,String pBudgetAccrualTypeCd)
             throws Exception {

    	if(pAccountId == 0){
    		pAccountId = BusEntityDAO.getAccountForSite(this.mCon,pSiteId);
    	}
    	if(pBudgetAccrualTypeCd == null){
    		pBudgetAccrualTypeCd = getBudgetAccrualTypeCd(pAccountId);
    	}

        BudgetSpendViewVector v = new BudgetSpendViewVector();
        CostCenterDataVector ccdv = this.getCostCentersForSite(pSiteId);

        log.debug("Getting cost centers for site");
        log.debug("FOUND: "+ccdv.size());

        for (int idx = 0; idx < ccdv.size(); idx++) {
            CostCenterData ccd = (CostCenterData) ccdv.get(idx);
            BudgetSpendView thisbudget =
                    this.getBudgetSpentForSite(pAccountId, pSiteId, ccd, pBudgetAccrualTypeCd);
            v.add(thisbudget);
        }
        return v;
    }

    /**
     * Returns all of the cost centers for the supplied bus entity id
     * @param busEntityId the site id
     * @return a List of CostCenterData objects
     */
    private CostCenterDataVector getCostCentersForBusEntity(int busEntityId, String busEntityType) throws SQLException{
        if(busEntityType == null){
            try{
                busEntityType = BusEntityDataAccess.select(mCon,busEntityId).getBusEntityTypeCd();
            }catch(DataNotFoundException e){
                throw new SQLException(e.getMessage());
            }
        }

        int accountId;
        if(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(busEntityType)){

            accountId = BusEntityDAO.getAccountForSite(mCon, busEntityId);
        }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(busEntityType)){
            accountId = busEntityId;
        }else{
            throw new RuntimeException("Wrong bus entity type: "+busEntityType);
        }

        //now get the account catalog
        DBCriteria crit = new DBCriteria();
        String casTab = CatalogAssocDataAccess.CLW_CATALOG_ASSOC;
        String catTab = CatalogDataAccess.CLW_CATALOG;
        crit.addJoinCondition(catTab,CatalogDataAccess.CATALOG_ID,casTab, CatalogAssocDataAccess.CATALOG_ID);
        crit.addJoinTableEqualTo(catTab,CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
        crit.addJoinTableEqualTo(catTab,CatalogDataAccess.CATALOG_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.ACCOUNT);
        crit.addJoinTableEqualTo(casTab,CatalogAssocDataAccess.BUS_ENTITY_ID,accountId);
        IdVector catids = JoinDataAccess.selectIdOnly(mCon, catTab, CatalogDataAccess.CATALOG_ID, crit, 2);
        if(catids.size() > 1){
            throw new SQLException("Found more than one account catalog for account id: "+accountId+ "("+IdVector.toCommaString(catids)+")");
        }else if(catids.isEmpty()){
            return new CostCenterDataVector();
        }

        DBCriteria crit1 = new DBCriteria();
        crit1.addOneOf(CostCenterAssocDataAccess.CATALOG_ID,catids);
        crit1.addEqualTo(CostCenterAssocDataAccess.COST_CENTER_ASSOC_CD,
                    RefCodeNames.COST_CENTER_ASSOC_CD.COST_CENTER_ACCOUNT_CATALOG);
        IdVector costCenterIdV = CostCenterAssocDataAccess.selectIdOnly(mCon,
                    CostCenterAssocDataAccess.COST_CENTER_ID,crit1);


        crit = new DBCriteria();
        String cstCntrTab = CostCenterDataAccess.CLW_COST_CENTER;
        crit.addEqualTo(CostCenterDataAccess.COST_CENTER_STATUS_CD,RefCodeNames.SIMPLE_STATUS_CD.ACTIVE);
        crit.addOneOf(CostCenterDataAccess.COST_CENTER_ID,costCenterIdV);
        IdVector acctAndSite = new IdVector();
        acctAndSite.add(new Integer(busEntityId));
        if(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(busEntityType)){
            acctAndSite.add(new Integer(accountId));
        }
        crit.addJoinTableOrderBy(cstCntrTab,CostCenterDataAccess.COST_CENTER_ID);
        CostCenterDataVector ccdv = CostCenterDataAccess.select(mCon,crit);
        //look for duplicates
        Iterator it = ccdv.iterator();
        int last = 0;
        while(it.hasNext()){
            CostCenterData ccd = (CostCenterData) it.next();
            if(last == ccd.getCostCenterId()){
                it.remove();
            }else{
                last = ccd.getCostCenterId();
            }
        }
        return ccdv;
    }


    /**
     * Returns all of the cost centers for the supplied account id
     * @param pAccountId the account id
     * @return a List of CostCenterData objects
     */
    public CostCenterDataVector getCostCentersForAccount(int pAccountId) throws SQLException{
       return getCostCentersForBusEntity(pAccountId,RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
    }

    public static HashMap<Integer, BigDecimal> getAmounts(BudgetDetailDataVector details) {
        HashMap<Integer, BigDecimal> amounts = new HashMap<Integer, BigDecimal>();
        if (details != null) {
            for (Object oBudgetDetail : details) {
                BudgetDetailData budgetDetail = ((BudgetDetailData) oBudgetDetail);
                amounts.put(budgetDetail.getPeriod(), budgetDetail.getAmount());
            }
        }
        return amounts;
    }

    public static BigDecimal getAmount(BudgetDetailDataVector pDetails, int pPeriod) {
        for (Object oBudgetDetail : pDetails) {
            BudgetDetailData detailData = (BudgetDetailData) oBudgetDetail;
            if (pPeriod == detailData.getPeriod()){
                return  detailData.getAmount();
            }
        }
        return null;
    }

    public static BudgetDetailData getBudgetDetail(BudgetDetailDataVector pDetails, int pPeriod) {
        for (Object oBudgetDetail : pDetails) {
            BudgetDetailData detailData = (BudgetDetailData) oBudgetDetail;
            if (pPeriod == detailData.getPeriod()) {
                return detailData;
            }
        }
        return null;
    }

    public static BudgetView createBudgetView() {
        return new BudgetView(BudgetData.createValue(), new BudgetDetailDataVector());
    }

    public static boolean isWrongBudgetThreshold(String threshold) {
        if (Utility.isSet(threshold)) {
            try {
                int value = Utility.parsePercentInt(threshold);
                return !(value >= -100 && value <= 100);
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUsedSiteBudgetThreshold(String pAllowBudgetThreshold, String pThresholdType) {
        return isUsedSiteBudgetThreshold(Utility.isTrue(pAllowBudgetThreshold), pThresholdType);
    }

    public static boolean isUsedSiteBudgetThreshold(boolean pAllowBudgetThreshold, String pThresholdType) {
        return pAllowBudgetThreshold && RefCodeNames.BUDGET_THRESHOLD_TYPE.SITE_BUDGET_THRESHOLD.equals(pThresholdType);
    }

    public static boolean isUsedAccountBudgetThreshold(boolean pAllowBudgetThreshold, String pThresholdType) {
        return pAllowBudgetThreshold && RefCodeNames.BUDGET_THRESHOLD_TYPE.ACCOUNT_BUDGET_THRESHOLD.equals(pThresholdType);
    }

    public static boolean isUsedAccountBudgetThreshold(String pAllowBudgetThreshold, String pThresholdType) {
        return isUsedSiteBudgetThreshold(Utility.isTrue(pAllowBudgetThreshold), pThresholdType);
    }

    public static BigDecimal calculateBudgetThreshold(BigDecimal pAllocatedBudget, Integer pThresholdPct) {

        log.debug("calculateBudgetThreshold()=> pAllocatedBudget: " + pAllocatedBudget + ", pThresholdPct: " + pThresholdPct);

        if (pAllocatedBudget == null) {
            return pAllocatedBudget;
        }

        if (pThresholdPct == null) {
            return pAllocatedBudget;
        }

        BigDecimal percentage = new BigDecimal(0);

        percentage = percentage.add(pAllocatedBudget);
        percentage = percentage.multiply(new BigDecimal(pThresholdPct));
        percentage = percentage.divide(new BigDecimal(100));

        BigDecimal budgetThreshold = new BigDecimal(0);
        budgetThreshold = budgetThreshold.add(pAllocatedBudget);
        budgetThreshold = budgetThreshold.add(percentage);

        log.debug("calculateBudgetThreshold()=> END.Budget Threshold: " + budgetThreshold);

        return budgetThreshold;

    }


    public static BigDecimal calculateOrderTotal(OrderData pOrder, OrderMetaDataVector pOrderMeta) {

        BigDecimal orderDiscount = null;
        BigDecimal orderSmallOrderFee = null;
        BigDecimal orderFuelsurcharge = null;
        BigDecimal freightAmt = pOrder.getTotalFreightCost();
        BigDecimal handlingAmt = pOrder.getTotalMiscCost();
        BigDecimal rushOrderCharge = pOrder.getTotalRushCharge();
        BigDecimal salesTax = pOrder.getTotalTaxCost();
        BigDecimal subTotal = pOrder.getTotalPrice();

        OrderMetaData mFuelsurcharge = Utility.getMetaObject(pOrder, pOrderMeta, RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
        if (mFuelsurcharge != null && Utility.isSet(mFuelsurcharge.getValue())) {
            orderFuelsurcharge = Utility.parseBigDecimal(mFuelsurcharge.getValue());
        }

        OrderMetaData mDiscount = Utility.getMetaObject(pOrder, pOrderMeta, RefCodeNames.CHARGE_CD.DISCOUNT);
        if (mDiscount != null && Utility.isSet(mDiscount.getValue())) {
            orderDiscount = Utility.parseBigDecimal(mDiscount.getValue());
        }

        OrderMetaData mSmallOrderFee = Utility.getMetaObject(pOrder, pOrderMeta, RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
        if (mSmallOrderFee != null && Utility.isSet(mSmallOrderFee.getValue())) {
            orderSmallOrderFee = Utility.parseBigDecimal(mSmallOrderFee.getValue());
        }

        BigDecimal orderTotal = Utility.bdNN(subTotal);

        orderTotal = orderTotal.add(Utility.bdNN(orderDiscount));
        orderTotal = orderTotal.add(Utility.bdNN(orderFuelsurcharge));
        orderTotal = orderTotal.add(Utility.bdNN(orderSmallOrderFee));
        orderTotal = orderTotal.add(Utility.bdNN(freightAmt));
        orderTotal = orderTotal.add(Utility.bdNN(handlingAmt));
        orderTotal = orderTotal.add(Utility.bdNN(rushOrderCharge));
        orderTotal = orderTotal.add(Utility.bdNN(salesTax));

        return orderTotal;

    }
    // Location Budget
    /**
     * Returns the Budget's current period amount 
     * @param pAccountId the account id
     * @param pAccountId the site id
     * @param pAccountId the cost center id
     * @param pAccountId the currentPeriod
     * @return a BigDecimal value
     */
    public BigDecimal getBudgetPeriodAmount(int pAccountId,int pSiteId,int costCenterId,int currentPeriod)
    throws Exception{
    	BigDecimal amount = new BigDecimal(-1);    	
    	BudgetSpendView budget = BudgetSpendView.createValue();
    	
    	budget = initBudgetSpendView(pSiteId, costCenterId, budget, 0); 
    	BudgetViewVector lBudgets = BudgetDAO.getBudgetsForSite(mCon, pAccountId, pSiteId, costCenterId, budget.getBudgetYear());
    	BudgetView bv;
    	  for (Object lBudget : lBudgets) {

              bv = (BudgetView) lBudget;

              BudgetViewWrapper bvw = new BudgetViewWrapper(bv);

              for (int period = 1; period <= budget.getNumberOfBudgetPeriods(); period++) {
            	  if(currentPeriod == period)
            		  amount = bvw.getCurrentPeriodAmount(currentPeriod);
              }

    	}
    	
    	return amount;
    }


    public BudgetSpendView getBudgetSpendView(int pSiteId, int pAccountId,int pCostCenterId, 
    		BudgetSpendView pBudget,int budgetPeriod, int budgetYear, String pAccountBudgetAccrualCd)
    throws Exception {

        try{
        	
        	FiscalPeriodView fiscalInfo =  mBDAO.getFiscalInfo(mCon, pSiteId,budgetPeriod, budgetYear);

            if (null == fiscalInfo) {
                log.info("No fiscal info for pSiteId=" + pSiteId);
                return pBudget;
            }

            FiscalCalenderView fc = fiscalInfo.getFiscalCalenderView();
            if (null == fc) {
                log.info("No fiscal calendar for pSiteId="
                        + pSiteId);
                return pBudget;
            }

            pBudget.setBudgetYear(budgetYear);
            pBudget.setBudgetPeriod(budgetPeriod);
            pBudget.setCurrentBudgetPeriod(budgetPeriod);
            pBudget.setCurrentBudgetYear(budgetYear);
            pBudget.setNumberOfBudgetPeriods(FiscalCalendarUtility.getNumberOfBudgetPeriods(fc));
            
        	BudgetViewVector lBudgets = BudgetDAO.getBudgetsForSite(mCon, pAccountId, pSiteId, pCostCenterId, budgetYear);
        	
        	if (lBudgets != null) {
        		
        		BudgetView bv;
        		BigDecimal allocated = new BigDecimal(0);
        		BigDecimal spent = new BigDecimal(0);
        		
        		for(int i=0; i<lBudgets.size(); i++){
        			bv = (BudgetView)lBudgets.get(i);
        			
            		BudgetViewWrapper bvw = new BudgetViewWrapper(bv);
            		
            		if(budgetPeriod > 0){ //get amount for this period
        				
        				allocated = bvw.getAmount(budgetPeriod);
            		}else{
            			//get amount for all periods of the year
            			for (int period = 1; period <= pBudget.getNumberOfBudgetPeriods(); period++) {
            				if(bvw.getAmount(period)!=null){
            					allocated = allocated.add(bvw.getAmount(period));
            				}
            			}
            		}
            		
            		//Unlimited budget
            		if(allocated == null){
            			pBudget.setUnlimitedBudget(true);
            		}
            		
            		if (!pBudget.getUnlimitedBudget()){
            			
            			if(budgetPeriod>0){
	                		spent = spent.add(_calculateAmountSpent(pAccountId,
	                		       pSiteId,
	                		       pCostCenterId,
	                		       budgetYear,
	                		       budgetPeriod,
	                		       pAccountBudgetAccrualCd,
	                		       bv.getBudgetData().getBudgetTypeCd()));
	                		
            			}else{
            			
            				spent = spent.add(_calculateAmountSpent(pAccountId,
                     		       pSiteId,
                     		       pCostCenterId,
                     		       budgetYear,
                     		       0,
                     		       pAccountBudgetAccrualCd,
                     		       bv.getBudgetData().getBudgetTypeCd()));
            			}
            		}
            		
            		
        		}
        		
        		
        		pBudget.setAmountAllocated(Utility.bdNN(allocated));
        		pBudget.setAmountSpent(Utility.bdNN(spent));
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
            log.error("_initBudgetDates: error: " + e);
        }

        return pBudget;
    }

}
