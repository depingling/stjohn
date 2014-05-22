package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.SiteLedgerDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.AccountBean;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import org.apache.log4j.Logger;


import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Makes Ledger Entry
 * @author  YKupershmidt (copied from IntegrationServicesBean)
 */
public class StoreOrderUpdateLedgerEntry  implements OrderPipeline
{
    private static final Logger log = Logger.getLogger(StoreOrderUpdateLedgerEntry.class);

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
    StoreOrderPipelineBaton sBaton =(StoreOrderPipelineBaton)pBaton;
    OrderData orderD = sBaton.getStoreOrderChangeRequestData().getOrderData();

    if (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderD.getOrderTypeCd())) {
        Order orderEjb = pFactory.getOrderAPI();
        OrderDataVector ordersToProcess = orderEjb.getReplacedOrdersFor(orderD.getOrderId());
        MakeLedgerEntry ledgerPlugin = new MakeLedgerEntry();
        ledgerPlugin.ledgerUpdate(pCon, ordersToProcess, getOrderDate(orderD), pBaton.getUserName());
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

         SiteLedgerDataVector existingLedgers = getSiteLedgerEntries(pCon,orderId);
         if (existingLedgers.size() <= 0) {
           return;
         }

         // Make a ledger entry for each cost center in the order.
         // Get the cost centers and amounts spent in this order.
         String query = " select oi.cost_center_id, " +
         " sum(oi.cust_contract_price * oi.total_quantity_ordered), " +
                " sale_type_cd , sum(tax_amount) " +
         " from clw_order_item oi " +
         " where oi.cost_center_id != 0 AND oi.order_id = " + orderId
             + " and ( order_item_status_cd != 'CANCELLED' or "
                + " order_item_status_cd is null ) " +
         " group by oi.cost_center_id, sale_type_cd ";

         Statement stmt = null;
         ResultSet rs = null;
         stmt = pCon.createStatement();
         rs = stmt.executeQuery(query);
         BusEntityDAO bdao = new BusEntityDAO();
         FiscalCalenderData fiscCal = bdao.getFiscalCalender (pCon, accountId, ordDate);
         int fiscalYear;
         int fiscalCalId;
         //int fiscalPeriod;
         if(fiscCal == null){
             Calendar cal = Calendar.getInstance();
             cal.setTime(ordDate);
             fiscalYear = cal.get(Calendar.YEAR);
             fiscalCalId = 0;
         }else{
             fiscalYear = fiscCal.getFiscalYear();
             fiscalCalId = fiscCal.getFiscalCalenderId();
         }
         HashMap ccSum = new HashMap();
         HashMap ccTaxSum = new HashMap();
         BigDecimal taxTotal=new BigDecimal(0.00);
         while (rs.next()) {
             Integer thisCostCenterId = new Integer(rs.getInt(1));
             BigDecimal thisCostCenterSum = new BigDecimal(rs.getDouble(2));
             String saleType = rs.getString(3);
             BigDecimal taxAdd = rs.getBigDecimal(4);
             taxTotal = Utility.addAmt(taxTotal, taxAdd);
             if (ccSum.containsKey(thisCostCenterId)) {
                 BigDecimal toAdd = (BigDecimal) ccSum.get(thisCostCenterId);
                 thisCostCenterSum = thisCostCenterSum.add(toAdd);
             }
             if (ccTaxSum.containsKey(thisCostCenterId)) {
                 BigDecimal ccTax = (BigDecimal) ccTaxSum.get(thisCostCenterId);
                 ccTaxSum.put(thisCostCenterId, Utility.addAmt(ccTax, taxAdd));
             } else {
                 ccTaxSum.put(thisCostCenterId, taxAdd);
             }
             ccSum.put(thisCostCenterId, thisCostCenterSum);
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
                 thisCostCenterSum = Utility.addAmt(thisCostCenterSum, discount);
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


//         SiteLedgerDataVector existingLedgers = getSiteLedgerEntries(pCon,orderId);


         BusEntityDAO bd = new BusEntityDAO();
         it = ccSum.keySet().iterator();
         while(it.hasNext()){
             Integer thisCostCenterIdKey = (Integer) it.next();
             int thisCostCenterId = thisCostCenterIdKey.intValue();
             BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(thisCostCenterIdKey);
             int budPeriod = bd.getAccountBudgetPeriod(pCon, orderD.getAccountId(), orderD.getSiteId(), ordDate);
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
             le.setBudgetPeriod(budPeriod);
             le.setFiscalCalenderId(fiscalCalId);
             le.setBudgetYear(fiscalYear);
             le.setOrderId(orderId);
             le.setSiteId(siteId);
             le.setCostCenterId(thisCostCenterId);

             le.setAmount(thisCostCenterSum);
             le.setEntryTypeCd(RefCodeNames.LEDGER_ENTRY_TYPE_CD.ORDER);
             le.setModBy(pUserName);
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
                 int budPeriod = 0;
                 budPeriod = bd.getAccountBudgetPeriod(pCon, orderD.getAccountId(), orderD.getSiteId(), ordDate);
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
        OrderData orderData = ((StoreOrderPipelineBaton) pBaton).getStoreOrderChangeRequestData().getOrderData();

        boolean isInventoryOrder = Utility.isInventoryOrder(orderData.getOrderSourceCd());
        boolean ledgerSwitchOff = accountEjb.ledgerSwitchOff(orderData.getAccountId());
        boolean isToBeConsolidated = Utility.isToBeConsolidated(orderData.getOrderTypeCd());

        log.info("shouldPipelineRun => isInventoryOrder: " + isInventoryOrder + " ,ledgerSwitchOff: " + ledgerSwitchOff + ", isToBeConsolidated: " + isToBeConsolidated);

        if ((isInventoryOrder && ledgerSwitchOff) || isToBeConsolidated) {
            log.info("shouldPipelineRun => return code: FALSE ");
            return false;
        } else {
            log.info("shouldPipelineRun => return code: TRUE ");
            return true;
        }
    }

    private Date getOrderDate(OrderData orderD) {
        Date orderDate = orderD.getRevisedOrderDate();
        if (orderDate == null) {
            orderDate = orderD.getOriginalOrderDate();
        }
        return orderDate;
    }
}
