/*
 * MakeLedgerEntryInvoiceAdjustments.java
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.AccountBean;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.log4j.Category;

/**
 * MakeLedgerEntryInvoiceAdjustments creates ledger adjustment entries from received invoices.  Currently only
 * handles Distributor and manufacturer store types.
 * 
 * Item pricing is adjusted on a per invoice basis.  Freight and tax are not updated until the last invoice against
 * an order is received.
 */
public class MakeLedgerEntryInvoiceAdjustments  implements OrderPipeline {
	private static final Category log = Category.getInstance(MakeLedgerEntryInvoiceAdjustments.class);
    private static final BigDecimal ZERO = new BigDecimal("0.00");
    private transient OrderData mOrderD;
    private transient Connection mCon;
    private transient Date mOrderDate;
    private transient BusEntityDAO bdao = new BusEntityDAO();

    private transient int fiscalYear;
    private transient int fiscalCalId;
    private transient int budPeriod;

    private transient boolean setupFiscalCalDataRun = false;
    private transient CostCenterDataVector mCostCenters = null;
    private transient SiteLedgerDataVector mLedgerEntries;

    private transient Map<Integer,BigDecimal> costCenterMap =  new HashMap();

    /**
     *Returns the current fiscal year based off the account fiscal calendar.  If there is none returns the year that the
     *order was approved, or ordered whichever is greater.
     */
    private void setupFiscalCalData() throws SQLException{
        if(setupFiscalCalDataRun){
            return;
        }
        FiscalCalenderData fiscCal = bdao.getFiscalCalender(mCon, mOrderD.getAccountId(), mOrderDate);
        //int fiscalPeriod;
        if(fiscCal == null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(mOrderDate);
            fiscalYear = cal.get(Calendar.YEAR);
            fiscalCalId = 0;
        }else{
            fiscalYear = fiscCal.getFiscalYear();
            fiscalCalId = fiscCal.getFiscalCalenderId();
        }
    }

    /**
     *Returns the budget period for the order based off the order date, account and site id.
     */
    private int getBudgetPeriod() throws Exception{
        if(budPeriod == 0){
            budPeriod = bdao.getAccountBudgetPeriod(mCon, mOrderD.getAccountId(), mOrderD.getSiteId(), mOrderDate);
        }
        return budPeriod;
    }

    /**
     *Any preconditions requiered for this pipeline to execute should be entered here.
     */
    private boolean shouldPipelineRun(OrderPipelineBaton pBaton,Connection pCon,APIAccess pFactory) throws RemoteException, APIServiceAccessException {
        int orderId = pBaton.getOrderData().getOrderId();
        //order has not been inserted yet, therfore no invoices could exists just return
        if(orderId == 0){
            log.debug("shouldPipelineRun.no orderId, do not run");
            return false;
        }
        String storeType=pBaton.getBusEntityPropertyCached(pBaton.getOrderData().getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE, pCon);
        if(!(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType))){
        	log.debug("shouldPipelineRun."+storeType+" not right store type, do not run");
            return false;
        }
        String oStat = pBaton.getOrderData().getOrderStatusCd();
        if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.DUPLICATED.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.PRE_PROCESSED.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.REFERENCE_ONLY.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.REJECTED.equals(oStat) ||
                RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP.equals(oStat)){
        	log.debug("shouldPipelineRun."+oStat+" not right order status, do not run");
            return false;
        }

        Account accountEjb = pFactory.getAccountAPI();
        if (Utility.isInventoryOrder(pBaton.getOrderData().getOrderSourceCd())&&accountEjb.ledgerSwitchOff(pBaton.getOrderData().getAccountId())) {
            return false;
        }

        try{
	        DBCriteria crit = new DBCriteria();
	        crit.addEqualTo(InvoiceDistDataAccess.ORDER_ID, orderId);
	        IdVector invoiceIds = InvoiceDistDataAccess.selectIdOnly(pCon, crit);
	        if(invoiceIds == null || invoiceIds.size() == 0){
	        	log.debug("shouldPipelineRun.no invoices, do not run");
	        	//no invoices, nothing to do.
	        	//if this is removed then pay special attention to freight and tax
	        	//as they will be adjusted the first time this runs as there is no
	        	//invoice present, so there is no tax charge.  The product ones
	        	//work fine as they are delt with by grouping and handle nulls better.
	        	return false;
	        }

        }catch(SQLException e){
        	throw new RemoteException("SQL Error during invoice lookup: "+e.getMessage(),e);
        }

        return true;
    }



    /** Process this pipeline.
     *
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,OrderPipelineActor pActor,Connection pCon,APIAccess pFactory)throws PipelineException {
        try{
            if(!shouldPipelineRun(pBaton,pCon,pFactory)){
                return pBaton;
            }
            mOrderD = pBaton.getOrderData();


            mOrderDate = mOrderD.getRevisedOrderDate();
            if(mOrderDate == null){
                mOrderDate = mOrderD.getOriginalOrderDate();
            }

            mCon = pCon;


            setupFiscalCalData();


            doLedgerUpdate();


            //go through the list of mapped data to update
            Iterator<Integer> it = costCenterMap.keySet().iterator();
            IdVector costCenterIds = new IdVector();
            while(it.hasNext()){
                Integer costCenterId = (Integer) it.next();
                BigDecimal amount = (BigDecimal) costCenterMap.get(costCenterId);
                insertAdjustmentRecord(amount, costCenterId.intValue());
                costCenterIds.add(costCenterId);
            }
            //loop through the existing site ledgers and remove anything that is there but should not be
            Iterator<SiteLedgerData> itLe= getLedgerEntries().iterator();
            String entryTypeCd = getLedgerEntryTypeCd(mOrderD);
            while(itLe.hasNext()){
                SiteLedgerData sl = (SiteLedgerData) itLe.next();
                if(entryTypeCd.equals(sl.getEntryTypeCd())){
                    Integer costCenterId = Integer.valueOf(sl.getCostCenterId());
                    if(!costCenterMap.containsKey(costCenterId)){
                        SiteLedgerDataAccess.remove(mCon,sl.getSiteLedgerId());
                    }
                }
            }

            if (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(pBaton.getOrderData().getOrderTypeCd())) {
                OrderObjectViewVector orderObjectViews = getVirtualInvoiceOrders(mCon, pFactory, mOrderD, costCenterIds);
                log.info("process => vertual orders to process: " + orderObjectViews.size());
                doLedgerUpdate(mCon, mOrderD, orderObjectViews);
            }

        }catch(Exception e){
        	log.error(e.getMessage(),e);
            //throw new PipelineException(e.getMessage());
        }
        return pBaton;
    }

    private void doLedgerUpdate(Connection pCon,
                                OrderData pConsolidatedOrder,
                                OrderObjectViewVector pOrderObjectViewList) throws Exception {

        log.info("doLedgerUpdate => BEGIN ");

        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();

        FiscalCalenderData fiscalCalender = null;
        int prevAccountId = 0;

        for (Object oOrderObjectView : pOrderObjectViewList) {

            OrderObjectView orderObjectView = (OrderObjectView) oOrderObjectView;
            Date orderDate = getOrderDate(orderObjectView.getOrder());
            int accountId = orderObjectView.getOrder().getAccountId();

            log.info("doLedgerUpdate => Order : " + orderObjectView.getOrder().getOrderId());

            if (prevAccountId != orderObjectView.getOrder().getAccountId()) {
                fiscalCalender = getFiscalCalender(pCon, accountId, orderDate);
            }

            HashMap<Integer, BigDecimal> costCenterMap = new HashMap<Integer, BigDecimal>();

            InvoiceDistDataVector invoiceDist = orderEjb.getInvoiceDistCollection(pConsolidatedOrder.getOrderId());

            for (Object oOrderItem : orderObjectView.getOrderItems()) {

                for (Object oInvoice : invoiceDist) {

                    InvoiceDistData invoice = ((InvoiceDistData) oInvoice);
                    InvoiceDistDetailDataVector invoiceItems = orderEjb.getInvoiceDistDetailCollection(invoice.getInvoiceDistId());

                    for (Object oInvoiceItem : invoiceItems) {

                        InvoiceDistDetailData invoiceItem = (InvoiceDistDetailData) oInvoiceItem;
                        OrderItemData orderItem = (OrderItemData) oOrderItem;

                        if (invoiceItem.getOrderItemId() == orderItem.getOrderItemId()) {

                            if (Math.abs(Utility.subtractAmt(invoiceItem.getAdjustedCost(), orderItem.getCustContractPrice()).doubleValue()) > 0.005) {

                                BigDecimal amount = new BigDecimal(orderItem.getDistItemQuantity() * (invoiceItem.getAdjustedCost().doubleValue() - (orderItem.getCustContractPrice()).doubleValue()));
                                if (Math.abs(amount.doubleValue()) > 0.005) {
                                    BigDecimal existingAmount = costCenterMap.get(orderItem.getCostCenterId());
                                    if (existingAmount == null) {
                                        costCenterMap.put(orderItem.getCostCenterId(), amount);
                                    } else {
                                        costCenterMap.put(orderItem.getCostCenterId(), Utility.addAmt(amount, existingAmount));
                                    }
                                }

                            }

                            break;
                        }

                    }

                }

            }

            log.info("doLedgerUpdate => cost centers for update: " + costCenterMap.keySet());

            for (Integer ccId : costCenterMap.keySet()) {
                insertAdjustmentRecord(pCon, orderObjectView.getOrder(), costCenterMap.get(ccId), ccId, fiscalCalender);
            }

            removeAdjustmentRecord(pCon, orderObjectView.getOrder().getOrderId(), costCenterMap);

            prevAccountId = accountId;

        }
        log.info("doLedgerUpdate => END.");

    }

    private void removeAdjustmentRecord(Connection  pCon,
                                        int pOrderId,
                                        HashMap<Integer, BigDecimal> pCostCenterMap) throws Exception {
        SiteLedgerDataVector sldv = getLedgerEntries(pCon, pOrderId);
        for (Object oSiteLedger : sldv) {
            if (RefCodeNames.LEDGER_ENTRY_TYPE_CD.INVOICE_DIST_ACTUAL.equals(((SiteLedgerData) (oSiteLedger)).getEntryTypeCd())) {
                if (!pCostCenterMap.containsKey(((SiteLedgerData) (oSiteLedger)).getCostCenterId())) {
                    SiteLedgerDataAccess.remove(pCon, ((SiteLedgerData) (oSiteLedger)).getSiteLedgerId());
                }
            }
        }
    }

    private OrderObjectViewVector getVirtualInvoiceOrders(Connection pCon,
                                                          APIAccess pFactory,
                                                          OrderData pOrder,
                                                          IdVector pCostCenterIds) throws Exception {

        Order orderEjb = pFactory.getOrderAPI();

        OrderItemDataVector orderItems;
        if (pCostCenterIds.size() > 0) {
            orderItems = getOrderItems(pCon, pOrder.getOrderId(), pCostCenterIds);
        } else {
            orderItems = new OrderItemDataVector();
        }

        OrderDataVector replacedOrders = orderEjb.getReplacedOrdersFor(pOrder.getOrderId());
        OrderObjectViewVector oList = new OrderObjectViewVector();

        for (Object oReplacedOrder : replacedOrders) {

            OrderData replacedOrder = ((OrderData) oReplacedOrder);

            OrderItemDataVector replacedOrderItems = orderEjb.getOrderItemCollection(replacedOrder.getOrderId());

            OrderItemDataVector oiList = new OrderItemDataVector();

            for (Object oReplacedOrderItem : replacedOrderItems) {

                OrderItemData replacedOrderItem = (OrderItemData) oReplacedOrderItem;

                for (Object oOrdetItem : orderItems) {

                    if (replacedOrderItem.getItemId() == ((OrderItemData) oOrdetItem).getItemId()) {

                        Integer distItemQty = ((OrderItemData) oOrdetItem).getDistItemQuantity();
                        int orderItemQty = replacedOrderItem.getTotalQuantityOrdered();

                        OrderItemData oi;
                        if (orderItemQty >= distItemQty) {
                            ((OrderItemData) oOrdetItem).setDistItemQuantity(0);
                            oi = (OrderItemData) replacedOrderItem.clone();
                            oi.setDistItemQuantity(distItemQty);
                            oi.setOrderItemId(((OrderItemData) oOrdetItem).getOrderItemId());
                        } else {
                            ((OrderItemData) oOrdetItem).setDistItemQuantity(distItemQty - orderItemQty);
                            oi = (OrderItemData) replacedOrderItem.clone();
                            oi.setDistItemQuantity(orderItemQty);
                            oi.setOrderItemId(((OrderItemData) oOrdetItem).getOrderItemId());
                        }

                        oiList.add(oi);

                    }
                }

            }

            OrderObjectView orderObjectView = OrderObjectView.createValue();

            orderObjectView.setOrder(replacedOrder);
            orderObjectView.setOrderItems(oiList);

            oList.add(orderObjectView);

        }

        return oList;

    }


    /**
     *If the order has been completed go ahead and make adjustments for the freight and sales tax dollars
     *and anything that was not in the order (lines without order item ids)
     */
    private void doLedgerUpdate() throws Exception{
        //grabs any order with price differences
    	//XXX this needs to account for sales tax and currently does not!
        String query = " select idd.erp_account_code, " +
                " sum(nvl(idd.adjusted_cost * idd.dist_item_quantity,0) - (nvl(oi.cust_contract_price,0) * nvl(idd.dist_item_quantity,0))) as total  " +
                " from clw_order_item oi, clw_invoice_dist_detail idd, clw_invoice_dist id " +
                " where idd.erp_account_code is not null AND id.order_id = " + mOrderD.getOrderId() +
                " and oi.order_item_id = idd.order_item_id and idd.adjusted_cost != oi.cust_contract_price "+
                " and idd.invoice_dist_id = id.invoice_dist_id " +
                " group by idd.erp_account_code ";
        log.debug("query: "+query);

        Statement stmt = null;
        ResultSet rs= null;
        
        try{
	        stmt = mCon.createStatement();
	        rs = stmt.executeQuery(query);
	
	        while(rs.next()){
	            String erpAcctCd = rs.getString("erp_account_code");
	            BigDecimal amount = rs.getBigDecimal("total");
	            int costCenterId = translateErpAccountCode(erpAcctCd);
	            addAmountForUpdate(amount, costCenterId);
	        }
	        rs.close();
	        stmt.close();
	
	        //grab any items that are not on the order
	        //XXX this needs to account for sales tax and currently does not!
	        query = " select idd.erp_account_code, " +
	                " sum(nvl(idd.adjusted_cost * idd.dist_item_quantity,0)) as total  " +
	                " from clw_invoice_dist_detail idd, clw_invoice_dist id " +
	                " where idd.erp_account_code is not null AND id.order_id = " + mOrderD.getOrderId() +
	                " and idd.invoice_dist_id = id.invoice_dist_id and (idd.order_item_id is null or idd.order_item_id = 0)" +
	                " group by idd.erp_account_code ";
	        log.debug("query2: "+query);
	        stmt = mCon.createStatement();
	        rs = stmt.executeQuery(query);
	        while(rs.next()){
	            String erpAcctCd = rs.getString("erp_account_code");
	            BigDecimal amount = rs.getBigDecimal("total");
	            int costCenterId = translateErpAccountCode(erpAcctCd);
	            addAmountForUpdate(amount, costCenterId);
	        }
	        rs.close();
	        stmt.close();
	
	        //grab any items that are cancelled
	        //XXX this needs to account for sales tax and currently does not!
	        query = " select idd.erp_account_code, " +
	                " sum(nvl(idd.adjusted_cost * idd.dist_item_quantity,0)) as total  " +
	                " from clw_order_item oi, clw_invoice_dist_detail idd, clw_invoice_dist id " +
	                " where idd.erp_account_code is not null AND id.order_id = " + mOrderD.getOrderId() +
	                " and oi.order_item_id = idd.order_item_id and oi.order_item_status_cd = '"+RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED+"' "+
	                " and idd.invoice_dist_id = id.invoice_dist_id " +
	                " group by idd.erp_account_code ";
	        log.debug("query3: "+query);
	        stmt = mCon.createStatement();
	        rs = stmt.executeQuery(query);
	        while(rs.next()){
	            String erpAcctCd = rs.getString("erp_account_code");
	            BigDecimal amount = rs.getBigDecimal("total");
	            int costCenterId = translateErpAccountCode(erpAcctCd);
	            addAmountForUpdate(amount, costCenterId);
	        }
	        rs.close();
	        stmt.close();
	        
	        int frtCostCenter = getFreightCostCenterId();
	        int taxCostCenter = getTaxCostCenterId();
	        if(frtCostCenter == 0 && taxCostCenter == 0){
	            return; //no need to calc any more adjustments
	        }
	        //now grab sales tax and freight
	        query = " select sum(nvl(id.sales_tax,0)) as tax, sum(nvl(id.freight,0) + nvl(id.MISC_CHARGES,0)) as frt " +
	                "from clw_invoice_dist id where id.order_id = " + mOrderD.getOrderId();
	        log.debug("query4: "+query);
	        stmt = mCon.createStatement();
	        rs = stmt.executeQuery(query);
	        while(rs.next()){
	            //first do tax
	            BigDecimal tax = rs.getBigDecimal("tax");
	            tax = Utility.subtractAmt(tax, mOrderD.getTotalTaxCost());
	            //if the order is fully baked, or the tax is already over the estimated tax make an adjustment
	            //use of or is used as we may need to 0 out a freight charge
	            if(isCompletedOrder() || tax.compareTo(ZERO) != 0){
	                addAmountForUpdate(tax, taxCostCenter);
	            }
	
	            //next do freight
	            BigDecimal frt = rs.getBigDecimal("frt");
	            log.debug("frt before="+frt);
	            frt = Utility.subtractAmt(frt, mOrderD.getTotalMiscCost());
	            frt = Utility.subtractAmt(frt, mOrderD.getTotalRushCharge());
	            frt = Utility.subtractAmt(frt, mOrderD.getTotalFreightCost());
	            //if the order is fully baked, or the freight is already over the allowed freight
	            //use of or is used as we may need to 0 out a freight charge
	            log.debug("frt after="+frt);
	            if(isCompletedOrder() || frt.compareTo(ZERO) != 0){
	            	log.debug("adding new amount for ledger update");
	                addAmountForUpdate(frt, frtCostCenter);
	            }
	
	        }
	        //done in finally statement below
	        //rs.close();
	        //stmt.close();
        }finally{
        	if(rs != null){
        		try{
        			rs.close();
        		}catch(Exception e){
        			log.error("Error closing result set",e);
        		}
        	}
        	if(stmt != null){
        		try{
        			stmt.close();
        		}catch(Exception e){
        			log.error("Error closing db statment",e);
        		}
        	}
        }
    }


    /**
     *Returns the master cost center or 0 if none exists
     */
    private int getTaxCostCenterId() throws Exception{
        Iterator<CostCenterData> it = getCostCenters().iterator();
        while(it.hasNext()){
            CostCenterData cc = (CostCenterData) it.next();
            if(RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(cc.getCostCenterTaxType())){
                return cc.getCostCenterId();
            }
        }
        return 0;
    }

    /**
     *Returns the freight cost center or 0 if none exists
     */
    private int getFreightCostCenterId() throws Exception{
        Iterator<CostCenterData> it = getCostCenters().iterator();
        while(it.hasNext()){
            CostCenterData cc = (CostCenterData) it.next();
            if(Utility.isTrue(cc.getAllocateFreight())){
                return cc.getCostCenterId();
            }
        }
        return 0;
    }


    /**
     *Translates an ERP Account code into a cost center id.  This is not a perfect match and there may be items that are
     *categorized outside of our budgeting system.  The erp account code is something a human may enter at the time of invoice
     *matching to categorize an expense (i.e. paper). But this match may not be perfect and should be lienient enought such that
     *"6310" will match "6310 - Supplies".  Curently this means if the cost center starts with a number then match that, otherwise
     *match the whole word.
     */
    private int translateErpAccountCode(String erpAccountCode)throws Exception{
        CostCenterDataVector costCenters = getCostCenters();
        String erpAcctTok = Utility.getFirstToken(erpAccountCode);
        try{
            Integer.parseInt(erpAcctTok);
        }catch(NumberFormatException e){
            erpAcctTok = erpAccountCode; //not a number must match whole string
        }
        Iterator<CostCenterData> it = costCenters.iterator();
        while(it.hasNext()){
            CostCenterData cc = (CostCenterData) it.next();
            String fullName = cc.getShortDesc();
            String shortName = Utility.getFirstToken(cc.getShortDesc());
            if(shortName.equals(erpAcctTok) || fullName.equals(erpAcctTok)){
                return cc.getCostCenterId();
            }
        }
        return 0;
    }

    private SiteLedgerDataVector getLedgerEntries() throws SQLException{
        if(mLedgerEntries == null){
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(SiteLedgerDataAccess.ORDER_ID, mOrderD.getOrderId());
            mLedgerEntries = SiteLedgerDataAccess.select(mCon,crit);
        }
        return mLedgerEntries;
    }

    private SiteLedgerDataVector getLedgerEntries(Connection pCon, int pOrderId) throws SQLException {
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(SiteLedgerDataAccess.ORDER_ID, pOrderId);
        return SiteLedgerDataAccess.select(pCon, crit);
    }
    /**
     *Figures out if a new adjustment record needs to be created or if there is an existing one that just needs updating
     */
    private SiteLedgerData getSiteLedgerEntry(int costCenterId,String pEntryTypeCd) throws Exception{
        SiteLedgerDataVector ledgerEntries = getLedgerEntries();
        Iterator<SiteLedgerData> it = ledgerEntries.iterator();
        while(it.hasNext()){
            SiteLedgerData esl = (SiteLedgerData) it.next();
            if(esl.getCostCenterId() == costCenterId && esl.getEntryTypeCd().equals(pEntryTypeCd)){
                return esl;
            }
        }
        SiteLedgerData sl = SiteLedgerData.createValue();
        sl.setAddBy("system");
        sl.setCostCenterId(costCenterId);
        sl.setEntryTypeCd(pEntryTypeCd);
        sl.setOrderId(mOrderD.getOrderId());
        sl.setSiteId(mOrderD.getSiteId());
        sl.setBudgetPeriod(getBudgetPeriod());
        sl.setBudgetYear(this.fiscalYear);
        sl.setFiscalCalenderId(this.fiscalCalId);
        return sl;
    }

    private SiteLedgerData getSiteLedgerEntry(Connection pCon,
                                              int pCostCenterId,
                                              int pOrderId,
                                              int pSiteId,
                                              int pFiscalYear,
                                              int pFiscalCalId,
                                              String pTypeCd) throws Exception{

        SiteLedgerDataVector ledgerEntries = getLedgerEntries(pCon, pOrderId);
        for (Object ledgerEntry : ledgerEntries) {
            SiteLedgerData esl = (SiteLedgerData) ledgerEntry;
            if (esl.getOrderId() == pOrderId
                    && esl.getCostCenterId() == pCostCenterId
                    && esl.getEntryTypeCd().equals(pTypeCd)) {
                return esl;
            }
        }

        SiteLedgerData sl = SiteLedgerData.createValue();
        sl.setAddBy("system");
        sl.setCostCenterId(pCostCenterId);
        sl.setEntryTypeCd(pTypeCd);
        sl.setOrderId(pOrderId);
        sl.setSiteId(pSiteId);
        sl.setBudgetPeriod(getBudgetPeriod());
        sl.setBudgetYear(pFiscalYear);
        sl.setFiscalCalenderId(pFiscalCalId);

        return sl;
    }

    /**
     *Adds an amount for a cost center to add to the adjustment.  May be called multiple times for
     *the same cost center and will add all of the amounts
     */
    private void addAmountForUpdate(BigDecimal amount, int costCenterId){
        log.info("Adding amount for update: "+amount+" costCenterId: "+costCenterId);
        if(costCenterId == 0 || amount.compareTo(ZERO) ==0){
            log.debug("skipping as cost center was 0 or amount to adjust was 0");
            //nothing to do
            return;
        }
        Integer key = Integer.valueOf(costCenterId);
        BigDecimal existingAmount = (BigDecimal) costCenterMap.get(key);
        if(existingAmount == null){
            costCenterMap.put(key,amount);
        }else{
            costCenterMap.put(key,Utility.addAmt(amount, existingAmount));
        }
    }
    /**
     *Inserts an adjustment into the siteledger;
     */
    private void insertAdjustmentRecord(BigDecimal amount, int costCenterId) throws Exception{
        if(costCenterId == 0 || amount.compareTo(ZERO) ==0){
            //don't insert a record if the adjustment amount = 0 or the cost center could not be found
            return;
        }

        SiteLedgerData sl = getSiteLedgerEntry(costCenterId, getLedgerEntryTypeCd(mOrderD));
        if(sl.getSiteLedgerId() != 0 && sl.getAmount().compareTo(amount) == 0
                && sl.getBudgetPeriod() == getBudgetPeriod() && sl.getBudgetYear() == this.fiscalYear &&
                sl.getFiscalCalenderId() == fiscalCalId){
            //if there is an existing entry and everything is the same no change should be made
            return;
        }


        sl.setAmount(amount);
        sl.setModBy("system");
        if(sl.getSiteLedgerId() == 0){
            SiteLedgerDataAccess.insert(mCon,sl);
        }else{
            SiteLedgerDataAccess.update(mCon,sl);
        }
    }

    private String getLedgerEntryTypeCd(OrderData mOrderD) {
        return RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(mOrderD.getOrderTypeCd()) ?
                RefCodeNames.LEDGER_ENTRY_TYPE_CD.CONSOLIDATED_INV_DIST_ACTUAL :
                RefCodeNames.LEDGER_ENTRY_TYPE_CD.INVOICE_DIST_ACTUAL;
    }

    private void insertAdjustmentRecord(Connection pCon,
                                        OrderData pOrder,
                                        BigDecimal pAmount,
                                        int costCenterId,
                                        FiscalCalenderData pFiscalCalender) throws Exception {

        if (costCenterId == 0 || pAmount.compareTo(ZERO) == 0) {
            //don't insert a record if the adjustment amount = 0 or the cost center could not be found
            return;
        }

        int pFiscalCalId = getFiscalCalenderId(pFiscalCalender);
        int pFiscalYear = getFiscalYear(pFiscalCalender, getOrderDate(pOrder));

        SiteLedgerData sl = getSiteLedgerEntry(pCon,
                costCenterId,
                pOrder.getOrderId(),
                pOrder.getSiteId(),
                pFiscalYear,
                pFiscalCalId,
                RefCodeNames.LEDGER_ENTRY_TYPE_CD.INVOICE_DIST_ACTUAL);

        if (sl.getSiteLedgerId() != 0 && sl.getAmount().compareTo(pAmount) == 0
                && sl.getBudgetPeriod() == getBudgetPeriod() && sl.getBudgetYear() == pFiscalYear &&
                sl.getFiscalCalenderId() == pFiscalCalId) {
            //if there is an existing entry and everything is the same no change should be made
            return;
        }

        sl.setAmount(pAmount);
        sl.setModBy("system");
        if (sl.getSiteLedgerId() == 0) {
            SiteLedgerDataAccess.insert(pCon, sl);
        } else {
            SiteLedgerDataAccess.update(pCon, sl);
        }
    }

    /**
     *Indicates whether this order has been closed out or not.  I.e. everything has been shipped.
     */
    private boolean isCompletedOrder(){
        //for now rely on the order status code.  Not sure if this gets set in the ThruStore stuff.
        return RefCodeNames.ORDER_STATUS_CD.INVOICED.equals(mOrderD.getOrderStatusCd());
    }

    private CostCenterDataVector getCostCenters() throws Exception{
        if(mCostCenters == null){
            AccountBean acctEjb = new AccountBean();
            mCostCenters = acctEjb.getAllCostCenters(mOrderD.getAccountId(), Account.ORDER_BY_NAME,mCon);
        }
        return mCostCenters;
    }

    private Date getOrderDate(OrderData orderD) {
        Date orderDate = orderD.getRevisedOrderDate();
        if (orderDate == null) {
            orderDate = orderD.getOriginalOrderDate();
        }
        return orderDate;
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


    private int getFiscalCalenderId(FiscalCalenderData pFiscalCalendar) {
        if (pFiscalCalendar != null) {
            return pFiscalCalendar.getFiscalCalenderId();
        } else {
            return 0;
        }
    }


    private OrderItemDataVector getOrderItems(Connection pConn,
                                              int pOrderId,
                                              IdVector pCostCenterIds) throws Exception {

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);
        dbc.addOneOf(OrderItemDataAccess.COST_CENTER_ID, pCostCenterIds);

        return OrderItemDataAccess.select(pConn, dbc);
    }
}
