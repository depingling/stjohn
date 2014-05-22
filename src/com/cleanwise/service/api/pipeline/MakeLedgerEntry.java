/*
 * CancelReplaceItemDuplication.java
 *
 * Created on August 25, 2003
 */

package com.cleanwise.service.api.pipeline;
import java.sql.Connection;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.dao.ProductDAO;
import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Calendar;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import java.util.Locale;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;


/**
 * Makes Ledger Entry
 * @author  YKupershmidt (copied from IntegrationServicesBean)
 */
public class MakeLedgerEntry  implements OrderPipeline
{
    private static final Logger log = Logger.getLogger(MakeLedgerEntry.class);

    /** Process this pipeline.
     *
     * @param OrderRequestData the order request object to act upon
     * @param Connection a active database connection
     * @param APIAccess
     *
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                OrderPipelineActor pActor,
                Connection pCon,
                APIAccess pFactory)
    throws PipelineException
    {
    try{

        if (!shouldPipelineRun(pBaton, pFactory)) {
            return pBaton;
   }

    pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
    OrderData orderD = pBaton.getOrderData();

    if (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderD.getOrderTypeCd())) {
        Order orderEjb = pFactory.getOrderAPI();
        OrderDataVector ordersToProcess = orderEjb.getReplacedOrdersFor(orderD.getOrderId());
        ledgerUpdate(pCon, ordersToProcess, getOrderDate(orderD), pBaton.getUserName());
    } else {
        ledgerUpdate(pCon, orderD, pFactory, pBaton.getUserName());
    }

    //Return
     pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
     return pBaton;
    }
    catch(Exception exc) {
       exc.printStackTrace();
      throw new PipelineException(exc.getMessage());
    }
    finally{
    }
    }

    private SiteLedgerDataVector getSiteLedgerEntries(Connection pCon, int orderId) throws SQLException{
        if(orderId == 0){
            return null;
        }
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(SiteLedgerDataAccess.ENTRY_TYPE_CD,RefCodeNames.LEDGER_ENTRY_TYPE_CD.ORDER);
        crit.addEqualTo(SiteLedgerDataAccess.ORDER_ID, orderId);
        SiteLedgerDataVector dv = SiteLedgerDataAccess.select(pCon,crit);
        return dv;
    }



     public void ledgerUpdate(Connection pCon, OrderData orderD,
     APIAccess pFactory, String pUserName)
     throws Exception
     {

         java.util.Date ordDate = orderD.getRevisedOrderDate();
         if(ordDate == null){
             ordDate = orderD.getOriginalOrderDate();
         }
         int orderId = orderD.getOrderId();
         int siteId = orderD.getSiteId(),
         accountId = orderD.getAccountId(),
         storeId = orderD.getStoreId();
         if(siteId<=0 || orderId<=0 || accountId <= 0) {
             return;
         }
         
         BusEntityDAO bdao = new BusEntityDAO();
         int fiscalYear = 0;
         int budPeriod = 0;
         int fiscalCalId = 0;
         
         DBCriteria dbc = new DBCriteria();
         dbc.addEqualTo(OrderPropertyDataAccess.ORDER_ID, orderD.getOrderId());
         dbc.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD,
                        RefCodeNames.ORDER_PROPERTY_TYPE_CD.BUDGET_YEAR_PERIOD);
         OrderPropertyDataVector orderPropertyDV = OrderPropertyDataAccess.select(pCon, dbc);
         
         if (orderPropertyDV.size() >= 1) {
           	String budgetYearAndPeriod = ((OrderPropertyData)orderPropertyDV.get(0)).getValue();
           	fiscalYear = Integer.parseInt(budgetYearAndPeriod.substring(0, budgetYearAndPeriod.indexOf(':')));
           	budPeriod = Integer.parseInt(budgetYearAndPeriod.substring(budgetYearAndPeriod.indexOf(':')+1)); 
           	FiscalCalenderView fiscCal = bdao.getFiscalCalenderVForYear(pCon,accountId,fiscalYear);
           	fiscalCalId = fiscCal.getFiscalCalender().getFiscalCalenderId();
         }else{ 
	         FiscalCalenderData fiscCal = bdao.getFiscalCalender (pCon, accountId, ordDate);
	         
	         if(fiscCal == null){
	             Calendar cal = Calendar.getInstance();
	             cal.setTime(ordDate);
	             fiscalYear = cal.get(Calendar.YEAR);
	             fiscalCalId = 0;
	         }else{
	             fiscalYear = fiscCal.getFiscalYear();
	             fiscalCalId = fiscCal.getFiscalCalenderId();
	         }         
	         
	         budPeriod = bdao.getAccountBudgetPeriod(pCon, orderD.getAccountId(), orderD.getSiteId(), ordDate);
         }

         // Make a ledger entry for each cost center in the order.
         // Get the cost centers and amounts spent in this order.
         String query = " select oi.cost_center_id, " +
         " sum(oi.cust_contract_price * oi.total_quantity_ordered), " +
		" sale_type_cd,sum(tax_amount) " +
         " from clw_order_item oi " +
         " where oi.cost_center_id != 0 AND oi.order_id = " + orderId
	     + " and ( order_item_status_cd != 'CANCELLED' or "
		+ " order_item_status_cd is null ) " +
         " group by oi.cost_center_id, sale_type_cd ";

         Statement stmt = null;
         ResultSet rs = null;
         stmt = pCon.createStatement();
         rs = stmt.executeQuery(query);
         HashMap ccSum = new HashMap();
         HashMap ccTaxSum = new HashMap();
         BigDecimal taxTotal=new BigDecimal(0.00);
         while (rs.next()) {
             Integer thisCostCenterId = new Integer(rs.getInt(1));
             BigDecimal thisCostCenterSum = new BigDecimal(rs.getDouble(2));
             String saleType = rs.getString(3);
             BigDecimal taxAdd = rs.getBigDecimal(4);
             taxTotal=Utility.addAmt(taxTotal,taxAdd);

             if(ccSum.containsKey(thisCostCenterId)){
                 BigDecimal toAdd = (BigDecimal) ccSum.get(thisCostCenterId);
                 thisCostCenterSum = thisCostCenterSum.add(toAdd);
             }
             if(ccTaxSum.containsKey(thisCostCenterId)){
                 BigDecimal ccTax = (BigDecimal) ccTaxSum.get(thisCostCenterId);
                 ccTaxSum.put(thisCostCenterId,Utility.addAmt(ccTax,taxAdd));
             }
             else
             {
                 ccTaxSum.put(thisCostCenterId,taxAdd);
             }
             ccSum.put(thisCostCenterId,thisCostCenterSum);
         }

         //now add in freight and sales tax
         BigDecimal frt = Utility.addAmt(orderD.getTotalMiscCost(), orderD.getTotalFreightCost());
         AccountBean actEjb = new AccountBean();
         CostCenterDataVector ccdv = actEjb.getAllCostCenters(accountId,Account.ORDER_BY_ID, pCon);
         Iterator it=ccdv.iterator();
         boolean foundFreightCC = false;
         boolean foundDiscountCC = false;
         while(it.hasNext()){
             CostCenterData ccd = (CostCenterData) it.next();

             if(Utility.isTrue(ccd.getNoBudget(), true)){
             	continue;
             }

             //******************first check on freight******************
             Integer key = new Integer(ccd.getCostCenterId());
             if(Utility.isTrue(ccd.getAllocateFreight())){
                 if(foundFreightCC){
                     throw new PipelineException("accont has multiple cost centers setup to allocate freight to");
                 }
                 foundFreightCC = true;
                 BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
                 thisCostCenterSum = Utility.addAmt(thisCostCenterSum, frt);
                 ccSum.put(key,thisCostCenterSum);
             }//end Utility.isTrue(...
             //******************next check on discount******************
             if (Utility.isTrue(ccd.getAllocateDiscount())) {
                 log.info("ledgerUpdate =>  cost center has setup to allocate discount");
                 if (foundDiscountCC) {
                     throw new PipelineException("Account has multiple cost centers setup to allocate discount.");
                 }
                 BigDecimal discount = OrderDAO.getDiscountAmt(pCon, orderD.getOrderId());
                 log.info("ledgerUpdate => discount = " + discount);
                 BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
                 thisCostCenterSum = Utility.subtractAmt(thisCostCenterSum, discount);
                 log.info("ledgerUpdate => Putting into map: " + thisCostCenterSum);
                 ccSum.put(key, thisCostCenterSum);
                 foundDiscountCC = true;
             }//end Utility.isTrue(...
             //******************next check on sales tax******************
             if(RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(ccd.getCostCenterTaxType())){
                 BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
                 BigDecimal taxCostCenterSum = (BigDecimal) ccTaxSum.get(key);
                 thisCostCenterSum = Utility.addAmt(taxCostCenterSum, thisCostCenterSum);
                 ccSum.put(key,thisCostCenterSum);
             }else if(RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(ccd.getCostCenterTaxType())){
                 BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
                 thisCostCenterSum = Utility.addAmt(taxTotal, thisCostCenterSum);
                 ccSum.put(key,thisCostCenterSum);
             }//else don't do anything with the sales tax
         }


         SiteLedgerDataVector existingLedgers = getSiteLedgerEntries(pCon,orderId);

         it = ccSum.keySet().iterator();
         while(it.hasNext()){
             Integer thisCostCenterIdKey = (Integer) it.next();
             int thisCostCenterId = thisCostCenterIdKey.intValue();

             BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(thisCostCenterIdKey);
             SiteLedgerData le = null;

             if(existingLedgers != null){
	             Iterator sit = existingLedgers.iterator();
	             while(sit.hasNext()){
	            	 SiteLedgerData ale = (SiteLedgerData) sit.next();
	            	 if(ale.getCostCenterId() == thisCostCenterId){
	            		 sit.remove(); //we will set any leftovers to 0
	            		 le = ale;
	            	 }
	             }
             }

             if(le == null){
                le = SiteLedgerData.createValue();
                le.setAddBy(pUserName);
             }
             //reset the budget period if it is not set
             if(le.getBudgetPeriod() <= 0){
            	 le.setBudgetPeriod(budPeriod);
             }
             //reset fiscal calender if it is not set
             if(le.getFiscalCalenderId() <= 0){
            	 le.setFiscalCalenderId(fiscalCalId);
             }
             //reset budget year if it is not set
             if(le.getBudgetYear() <= 0){
            	 le.setBudgetYear(fiscalYear);
             }
             le.setOrderId(orderId);
             le.setSiteId(siteId);
             le.setCostCenterId(thisCostCenterId);

             le.setAmount(thisCostCenterSum);
             le.setEntryTypeCd(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ORDER);
             le.setModBy(pUserName);


             CostCenterData mycc = CostCenterDataAccess.select(pCon, thisCostCenterId);
             if(Utility.isTrue(mycc.getNoBudget(), true)){
            	 if(le.getSiteLedgerId()!=0){
            		 SiteLedgerDataAccess.remove(pCon, le.getSiteLedgerId());
            	 }
              	continue;
              }


             if(le.getSiteLedgerId() == 0){
                 if(thisCostCenterSum.compareTo(new BigDecimal(0.00))!=0){
                    SiteLedgerDataAccess.insert(pCon, le);
                 }
             }else{
                 SiteLedgerDataAccess.update(pCon, le);
             }
         }
         rs.close();
         stmt.close();

         //loop through any leftover ledger entries and set them to be empty
         it = existingLedgers.iterator();
         while(it.hasNext()){
        	 SiteLedgerData le = (SiteLedgerData) it.next();
        	 le.setAmount(new BigDecimal(0.00));
        	 SiteLedgerDataAccess.update(pCon, le);
         }

         if(!RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(orderD.getOrderStatusCd()) &&
               !RefCodeNames.ORDER_STATUS_CD.INVOICED.equals(orderD.getOrderStatusCd())){
             DBCriteria crit = new DBCriteria();
             crit.addEqualTo(SiteLedgerDataAccess.ORDER_ID,orderId);
             crit.addGreaterThan(SiteLedgerDataAccess.COST_CENTER_ID,0);

             SiteLedgerDataVector slv = SiteLedgerDataAccess.select(pCon, crit);
             for ( int ii = 0; slv != null && ii < slv.size(); ii++) {
                 SiteLedgerData sld = (SiteLedgerData)slv.get(ii);
                 int thisCostCenterId = sld.getCostCenterId();
                 // Update the budget period in the event the
                 // order is being approved in a new period.
                 sld.setBudgetPeriod(budPeriod);
                 sld.setBudgetYear(fiscalYear);
                 sld.setFiscalCalenderId(fiscalCalId);
                 SiteLedgerDataAccess.update(pCon, sld);
             }

         }

     }


    private boolean shouldPipelineRun(OrderPipelineBaton pBaton, APIAccess pFactory) throws RemoteException, APIServiceAccessException {

        Account accountEjb = pFactory.getAccountAPI();
        PropertyService propEjb = pFactory.getPropertyServiceAPI();

        OrderData orderData = pBaton.getOrderData();
        
        boolean isInventoryOrder = Utility.isInventoryOrder(orderData.getOrderSourceCd());
        boolean ledgerSwitchOff = accountEjb.ledgerSwitchOff(orderData.getAccountId());
        boolean isToBeConsolidated = Utility.isToBeConsolidated(orderData.getOrderTypeCd());
        boolean notApplyToBudgetForBatchOrder = RefCodeNames.ORDER_TYPE_CD.BATCH_ORDER.equals(orderData.getOrderTypeCd()) && RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equals(orderData.getOrderBudgetTypeCd());
        
        log.info("shouldPipelineRun => isInventoryOrder: " + isInventoryOrder + " ,ledgerSwitchOff: " + ledgerSwitchOff + ", isToBeConsolidated: " + isToBeConsolidated + ", notApplyToBudgetForBatchOrder: " + notApplyToBudgetForBatchOrder);

        if ((isInventoryOrder && ledgerSwitchOff) || isToBeConsolidated || notApplyToBudgetForBatchOrder) {
            log.info("shouldPipelineRun => return code: FALSE ");
            return false;
        } else {
            log.info("shouldPipelineRun => return code: TRUE ");
            return true;
        }
    }


    public void ledgerUpdate(Connection pCon,
                             OrderDataVector pOrders,
                             Date pOrderDate,
                             String pUserName) throws Exception {

        if (pOrders != null) {

            FiscalCalenderData fiscalCalender = null;
            int budgetPeriod = 0;
            CostCenterDataVector accountCostCenters = new CostCenterDataVector();

            int prevAccountId = 0;
            int prevSiteId = 0;

            for (Object oOrder : pOrders) {

                OrderData order = (OrderData) oOrder;

                int orderId = order.getOrderId();
                int siteId = order.getSiteId();
                int accountId = order.getAccountId();

                log.info("ledgerUpdate => Account: " + accountId + ", Site: " + siteId + ", Order: " + orderId);
                log.info("ledgerUpdate => Order Date: " + pOrderDate);

                if (siteId <= 0 || orderId <= 0 || accountId <= 0) {
                    continue;
                }

                if (prevAccountId != accountId) {

                    log.info("ledgerUpdate => get fiscal calender.Account: " + accountId+",Order Date: "+pOrderDate);
                    fiscalCalender = getFiscalCalender(pCon, accountId, pOrderDate);
                    log.info("ledgerUpdate => fiscal calender: " + fiscalCalender);

                    log.info("ledgerUpdate => get cost centers for account: " + accountId);
                    accountCostCenters = getAccountCostCenters(pCon, accountId);
                    log.info("ledgerUpdate => cost centers size: " + accountCostCenters.size());
                }

                if (prevAccountId != accountId || prevSiteId != siteId) {
                    log.info("ledgerUpdate => get budget period. Account: " + accountId + ", Site: " + siteId+", Order Date: "+pOrderDate);
                    budgetPeriod = getBudgetPeriod(pCon, accountId, siteId, pOrderDate);
                    log.info("ledgerUpdate => budget period: " + budgetPeriod);
                }

                ledgerUpdate(pCon, order, pOrderDate, budgetPeriod, fiscalCalender, accountCostCenters, pUserName);

                prevAccountId = accountId;
                prevSiteId = siteId;

            }

        }
    }

    // Note : now only for consolidated orders
    public void ledgerUpdate(Connection pCon,
                             OrderData pOrder,
                             Date pOrderDate,
                             int pBudgetPeriod,
                             FiscalCalenderData pFiscalCalender,
                             CostCenterDataVector ccdv,
                             String pUserName) throws Exception {


        HashMap<Integer, BigDecimal> ccSum = new HashMap<Integer, BigDecimal>();
        HashMap<Integer, BigDecimal> ccTaxSum = new HashMap<Integer, BigDecimal>();
        BigDecimal taxTotal = new BigDecimal(0.00);

        // Make a ledger entry for each cost center in the order.
        // Get the cost centers and amounts spent in this order.
        {
            String query = " select oi.cost_center_id, " +
                    " sum(oi.cust_contract_price * oi.total_quantity_ordered), " +
                    " sale_type_cd,sum(tax_amount) " +
                    " from clw_order_item oi " +
                    " where oi.cost_center_id != 0 AND oi.order_id = " + pOrder.getOrderId()
                    + " and ( order_item_status_cd != 'CANCELLED' or "
                    + " order_item_status_cd is null ) " +
                    " group by oi.cost_center_id, sale_type_cd ";

            Statement stmt = pCon.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {

                Integer thisCostCenterId = rs.getInt(1);
                BigDecimal thisCostCenterSum = new BigDecimal(rs.getDouble(2));

                BigDecimal taxAdd = rs.getBigDecimal(4);
                taxTotal = Utility.addAmt(taxTotal, taxAdd);

                if (ccSum.containsKey(thisCostCenterId)) {
                    BigDecimal toAdd = ccSum.get(thisCostCenterId);
                    thisCostCenterSum = thisCostCenterSum.add(toAdd);
                }

                if (ccTaxSum.containsKey(thisCostCenterId)) {
                    BigDecimal ccTax = ccTaxSum.get(thisCostCenterId);
                    ccTaxSum.put(thisCostCenterId, Utility.addAmt(ccTax, taxAdd));
                } else {
                    ccTaxSum.put(thisCostCenterId, taxAdd);
                }

                ccSum.put(thisCostCenterId, thisCostCenterSum);

            }

            rs.close();
            stmt.close();
        }

        for (Object oCostCenter : ccdv) {

            CostCenterData costCenter = (CostCenterData) oCostCenter;
            Integer key = costCenter.getCostCenterId();

            if (Utility.isTrue(costCenter.getNoBudget(), true)) {
                continue;
            }

            if (RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(costCenter.getCostCenterTaxType())) {
                BigDecimal thisCostCenterSum = ccSum.get(key);
                BigDecimal taxCostCenterSum = ccTaxSum.get(key);
                thisCostCenterSum = Utility.addAmt(taxCostCenterSum, thisCostCenterSum);
                ccSum.put(key, thisCostCenterSum);
            } else if (RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(costCenter.getCostCenterTaxType())) {
                BigDecimal thisCostCenterSum = ccSum.get(key);
                thisCostCenterSum = Utility.addAmt(taxTotal, thisCostCenterSum);
                ccSum.put(key, thisCostCenterSum);
            }

        }

        ledgerUpdate(pCon, pOrder, pFiscalCalender, pOrderDate, pBudgetPeriod, ccSum, pUserName);

    }

    private void ledgerUpdate(Connection pCon,
                              OrderData pOrder,
                              FiscalCalenderData pFiscalCalendar,
                              Date pOrderDate,
                              int pBudgetPeriod,
                              HashMap<Integer, BigDecimal> pCostCenterSum,
                              String pUserName) throws Exception {


        SiteLedgerDataVector existingLedgers = getSiteLedgerEntries(pCon, pOrder.getOrderId());

        for (Integer thisCostCenterId : pCostCenterSum.keySet()) {

            BigDecimal thisCostCenterSum = pCostCenterSum.get(thisCostCenterId);

            SiteLedgerData le = null;
            Iterator sit = existingLedgers.iterator();
            while (sit.hasNext()) {
                SiteLedgerData ale = (SiteLedgerData) sit.next();
                if (ale.getCostCenterId() == thisCostCenterId) {
                    sit.remove(); //we will set any leftovers to 0
                    le = ale;
                }
            }

            if (le == null) {
                le = SiteLedgerData.createValue();
                le.setAddBy(pUserName);
            }
            //reset the budget period if it is not set
            if (le.getBudgetPeriod() <= 0) {
                le.setBudgetPeriod(pBudgetPeriod);
            }

            //reset fiscal calender if it is not set
            if (le.getFiscalCalenderId() <= 0) {
                le.setFiscalCalenderId(getFiscalCalenderId(pFiscalCalendar));
            }

            //reset budget year if it is not set
            if (le.getBudgetYear() <= 0) {
                le.setBudgetYear(getFiscalYear(pFiscalCalendar, pOrderDate));
            }

            le.setOrderId(pOrder.getOrderId());
            le.setSiteId(pOrder.getSiteId());
            le.setCostCenterId(thisCostCenterId);

            le.setAmount(thisCostCenterSum);
            le.setEntryTypeCd(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ORDER);
            le.setModBy(pUserName);

            CostCenterData mycc = CostCenterDataAccess.select(pCon, thisCostCenterId);
            if (Utility.isTrue(mycc.getNoBudget(), true)) {
                if (le.getSiteLedgerId() != 0) {
                    SiteLedgerDataAccess.remove(pCon, le.getSiteLedgerId());
                    log.info("ledgerUpdate =>  remove: "+le);
                }
                continue;
            }


            if (le.getSiteLedgerId() == 0) {
                if (thisCostCenterSum.compareTo(new BigDecimal(0.00)) != 0) {
                    le = SiteLedgerDataAccess.insert(pCon, le);
                    log.info("ledgerUpdate => insert: " + le);
                }
            } else {
                SiteLedgerDataAccess.update(pCon, le);
                log.info("ledgerUpdate => update: " + le);
            }
        }

        //loop through any leftover ledger entries and set them to be empty
        for (Object oExistingLedger : existingLedgers) {
            SiteLedgerData le = (SiteLedgerData) oExistingLedger;
            le.setAmount(new BigDecimal(0.00));
            log.info("ledgerUpdate => update: " + le);
            SiteLedgerDataAccess.update(pCon, le);
        }

        if (!RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(pOrder.getOrderStatusCd()) &&
                !RefCodeNames.ORDER_STATUS_CD.INVOICED.equals(pOrder.getOrderStatusCd())) {

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(SiteLedgerDataAccess.ORDER_ID, pOrder.getOrderId());
            crit.addGreaterThan(SiteLedgerDataAccess.COST_CENTER_ID, 0);

            SiteLedgerDataVector slv = SiteLedgerDataAccess.select(pCon, crit);
            for (int ii = 0; slv != null && ii < slv.size(); ii++) {

                SiteLedgerData sld = (SiteLedgerData) slv.get(ii);

                // Update the budget period in the event the
                // order is being approved in a new period.
                sld.setBudgetPeriod(pBudgetPeriod);
                sld.setBudgetYear(getFiscalYear(pFiscalCalendar, pOrderDate));
                sld.setFiscalCalenderId(getFiscalCalenderId(pFiscalCalendar));

                log.info("ledgerUpdate => Update the budget period in the event the order is being approved in a new period.");
                SiteLedgerDataAccess.update(pCon, sld);
                log.info("ledgerUpdate => update: "+sld);

            }
        }
    }

    private int getFiscalCalenderId(FiscalCalenderData pFiscalCalendar) {
        if (pFiscalCalendar != null) {
            return pFiscalCalendar.getFiscalCalenderId();
        } else {
            return 0;
        }
    }

    private int getFiscalYear(FiscalCalenderData fiscCal, Date pOrderDate) {
        if (fiscCal == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(pOrderDate);
            return cal.get(Calendar.YEAR);
        } else {
            return fiscCal.getFiscalYear();
        }
    }

    private FiscalCalenderData getFiscalCalender(Connection pCon,
                                                 int pAccountId,
                                                 Date pOrderDate) throws SQLException {
        BusEntityDAO bdao = new BusEntityDAO();
        return bdao.getFiscalCalender(pCon, pAccountId, pOrderDate);
    }

    private int getBudgetPeriod(Connection pCon,
                                int pAccountId,
                                int pSiteId,
                                Date pOrderDate) throws Exception {
        BusEntityDAO bdao = new BusEntityDAO();
        return bdao.getAccountBudgetPeriod(pCon, pAccountId, pSiteId, pOrderDate);
    }

    private CostCenterDataVector getAccountCostCenters(Connection pCon, int accountId) throws Exception {
        AccountBean actEjb = new AccountBean(); //Not a good practice.Copied from original version.
        return actEjb.getAllCostCenters(accountId, Account.ORDER_BY_ID, pCon);
    }

    private Date getOrderDate(OrderData orderD) {
        Date orderDate = orderD.getRevisedOrderDate();
        if (orderDate == null) {
            orderDate = orderD.getOriginalOrderDate();
        }
        return orderDate;
    }


}
