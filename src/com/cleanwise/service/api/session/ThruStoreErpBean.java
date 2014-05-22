package com.cleanwise.service.api.session;

/**
 * Title:        ThruStoreErpBean
 * Description:  Bean does rudimenta erp functions 
 * Purpose:      To simulate some Lawson results
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 *
 */

import javax.ejb.*;
import java.rmi.*;
import java.text.SimpleDateFormat; 
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.util.*;

import java.sql.Connection;
import java.sql.SQLException;
import com.cleanwise.service.api.dao.*;

import java.sql.*;
import org.apache.log4j.Logger;
import java.util.GregorianCalendar;
import com.cleanwise.service.api.APIAccess;
import java.math.BigDecimal;


public class ThruStoreErpBean extends ApplicationServicesAPI
{
  private static final Logger log = Logger.getLogger(ThruStoreErpBean.class);
  protected static final String OP_ORDER_OUT =   "ORD_OUT";
  protected static final String OP_PO_IN =       "PO_IN  ";
  protected static final String OP_INVOICE_OUT = "INV_OUT";
  protected static final String OP_INVOICE_IN =  "INV_IN ";
  protected static final String OP_UNKNOWN =     "UNKNOWN";
  protected static final String OP_REMIT_OUT =   "REMIT_OUT";
  protected static final String OP_BATCH_CREATE = "CRT BATCH";

  // Types of exceptions logged
  protected static final String PO_OUT =
               RefCodeNames.ORDER_EXCEPTION_TYPE_CD.ORDER_TO_ERP;
  protected static final String PO_IN =
               RefCodeNames.ORDER_EXCEPTION_TYPE_CD.PO_FROM_ERP;
  protected static final String INVOICE_OUT =
               RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_TO_ERP;
  protected static final String INVOICE_IN =
               RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_FROM_ERP;

  
  protected static final String CLW_ERP_CUST_INVOICE = "CLW_ERP_CUST_INVOICE";
  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}
 
 //***************************************************************************
 /**
  * Gets new orders and generates pos
  */
  /*public void generatePos(String pUser)
  throws RemoteException, Exception
  {
    String globalOp = OP_PO_IN;
    logInfo("##########################################");
    logInfo((new Date(System.currentTimeMillis()))+" - Batch. Thru Store PO processing");

    int nbOrders =0;
    int nbErrors =0;

    Connection con = null;
    try {
      con = getConnection();

      IdVector newOrderIds = getNewThruStoreOrderIds();
      for(int ii=0; ii<newOrderIds.size(); ii++) {
        Integer orderIdI = (Integer) newOrderIds.get(ii);
        try {
          processNewThruStoreOrder(orderIdI.intValue(), pUser);
        } catch (Exception exc) {
          nbErrors++;
        }
        nbOrders++;
      }
    }catch (Exception exc) {
      logError("Error in poProcessing: "+exc.getMessage());
      exc.printStackTrace();
      throw exc;
    }finally{
	closeConnection(con);
      logInfo("------------------------------------------------");
      logInfo((new Date(System.currentTimeMillis()))+" - Batch finished. Orders processed: "+nbOrders+" Errors: "+nbErrors);
      logInfo("################################################");
    }
  }*/
 //-------------------------------------------------------
 /**
  * Gets list of erp po numbers for the order
  * @param pOrderId record id
  * @return  List of erp po numbers
  * @throws  RemoteException
  */
  public ArrayList getOrderErpPos(int pOrderId)
  throws RemoteException
  {
    String errorMessage;
    Connection con = null;
    ArrayList erpPos = new ArrayList();
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,pOrderId);
      dbc.addOrderBy(OrderItemDataAccess.ERP_PO_NUM);
      OrderItemDataVector orderItemDV = OrderItemDataAccess.select(con,dbc);
      String erpPo = "";
      for(int ii=0; ii<orderItemDV.size();ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        String nextErpPo = oiD.getErpPoNum();
        if(Utility.isSet(nextErpPo) && !nextErpPo.equals(erpPo)) {
          erpPo = nextErpPo;
          erpPos.add(erpPo);
        }
      }
    }catch(Exception exc) {
      errorMessage = "Error. ThruStoreErpBean.getOrderErpPos() "+exc.getMessage();
      logError(errorMessage);
      throw new RemoteException(errorMessage);
    }finally{
      closeConnection(con);
    }
    return erpPos;
  }


 //*****************************************************
 /**
  * Gets new valid orders which have CLW_JCI erp system
  * @return the vector of order ids
  * @throws   RemoteException, LawsonException
  */

  public IdVector getNewThruStoreOrderIds()
  throws RemoteException
  {
    logInfo("************************************************");
    logInfo((new Date(System.currentTimeMillis()))+" - Get released order ids");
    String errorMess;
    Connection con = null; // Oracle connection
    IdVector orderIds = new IdVector();

    try {
      DBCriteria dbc;
      con = getConnection();
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderDataAccess.ORDER_STATUS_CD, 
                                      RefCodeNames.ORDER_STATUS_CD.ORDERED);
      dbc.addEqualTo(OrderDataAccess.ERP_SYSTEM_CD,
                                      RefCodeNames.ERP_SYSTEM_CD.CLW_JCI);
      OrderDataVector orderDV = OrderDataAccess.select(con,dbc);
      if (orderDV.size() == 0) {
          logInfo("No new thru store orders found");
          return orderIds;
      }

      for(int ii=0; ii<orderDV.size(); ii++) {
        OrderData oD = (OrderData) orderDV.get(ii);
        orderIds.add(new Integer(oD.getOrderId()));
      }
    }
    catch (Exception exc) {
      String em = "Error. ThruStoreErpBean.getNewThruStoreOrderIds() Exception happened. "+exc.getMessage();
      exc.printStackTrace();
      throw new RemoteException(em);
   }
   finally {
      closeConnection(con);
   }
    return orderIds;
  }


    private boolean isResaleOrderItem(OrderItemData currOiD) {
        return   Utility.isTaxExemptItemSaleTypeCd(currOiD);
    }
  
 //*****************************************************
  /**
   *Updates an existing po with any total information.  The totals are not calculated until after the po is first already created,
   *this method handles the cleanup of after the items are created and the totals are known.
   */
  private void updateInsertedPurchaseOrderWithTotals(OrderData orderD, PurchaseOrderData poD, Connection con, BigDecimal itemLineTotal, BigDecimal itemTaxableTotal)
  throws SQLException, TaxCalculationException, RemoteException, DataNotFoundException{

      if(itemTaxableTotal == null){
          itemTaxableTotal=new BigDecimal(0.00);
      }
      poD.setTaxTotal(itemTaxableTotal);
      poD.setLineItemTotal(itemLineTotal);
      BigDecimal total = Utility.addAmt(itemLineTotal, itemTaxableTotal);
      poD.setPurchaseOrderTotal(total);
      PurchaseOrderDataAccess.update(con, poD);
  }
  
  
 /**
 * Verifies po against the order, cancell wrong pos, generates new pos
 * @param the order id in CLW_ORDER_TABLE
 * @param the user login name
 * @throws   RemoteException
  */
  public void processNewThruStoreOrder(int pOrderId, String pUser)
  throws RemoteException
  {
    String globalOp = OP_PO_IN;
    logInfo("************************************************");
    logInfo((new Date(System.currentTimeMillis()))+" - Released order processing");
    logInfo("Order Id: "+pOrderId+", User: "+pUser);
    String errorMess;
    Connection con = null;
    Exception exception = null;
    try {
      //Get OrderData object
      con = getConnection();
      PurchaseOrder poEjb = APIAccess.getAPIAccess().getPurchaseOrderAPI();

      String lstat = getAppLock(con, globalOp);
      logDebug("lock status is: " + lstat);
      if (! lstat.equals("UNLOCKED") ) {
	  logInfo("This operation, " + globalOp +" is " + lstat );
	  return;
      }
      OrderData orderD = getOrder(con,pOrderId);
      String erpSystemCd = orderD.getErpSystemCd();
      logDebug("Erp system: "+erpSystemCd);
      //if(erpSystemCd==null || !erpSystemCd.trim().equals(RefCodeNames.ERP_SYSTEM_CD.CLW_JCI)){
     //   return;
      //}
      String statusCd = orderD.getOrderStatusCd();
      if(!RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP.equals(statusCd)) {
        return;
      }
      String  orderNum = orderD.getOrderNum();
      logInfo("Order number: " + orderNum);
      
      //get auxiliary info about the order
      boolean billingOrder = false;
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, 
          RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, 
          RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER);
      crit.addEqualTo(OrderPropertyDataAccess.ORDER_ID,pOrderId);
      crit.addEqualToIgnoreCase(OrderPropertyDataAccess.CLW_VALUE,"TRUE");
      OrderPropertyDataVector opd = OrderPropertyDataAccess.select(con,crit);
      if(opd.size() >= 1){
        billingOrder = true;
        }else if(opd.size() == 0){
        billingOrder = false;
      }   
      
      //get order items
      crit = new DBCriteria();
      crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);
      crit.addOrderBy(OrderItemDataAccess.DIST_ERP_NUM);
      crit.addOrderByAlpha(OrderItemDataAccess.ITEM_SKU_NUM);
      
      OrderItemDataVector allOrderItemDV = OrderItemDataAccess.select(con,crit);

      
      OrderItemDataVector orderItemDV = new OrderItemDataVector();
      for(int ii=0; ii<allOrderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) allOrderItemDV.get(ii);
        if(!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(
           oiD.getOrderItemStatusCd())) {
           orderItemDV.add(oiD);
        }
      }

      
      //get existing pos
      crit = new DBCriteria();
      crit.addEqualTo(PurchaseOrderDataAccess.ORDER_ID,pOrderId);
      crit.addNotEqualTo(PurchaseOrderDataAccess.PURCHASE_ORDER_STATUS_CD,
                               RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED);
      PurchaseOrderDataVector purchaseOrderDV = PurchaseOrderDataAccess.select(con,crit);
      ArrayList notes = new ArrayList();
      
      //recalculate existing pos
      for(int ii=0; ii<purchaseOrderDV.size(); ii++) {
        PurchaseOrderData poD = (PurchaseOrderData) purchaseOrderDV.get(ii);
        int poId = poD.getPurchaseOrderId();
        String erpPoNum = poD.getErpPoNum();
        String distErpNum = poD.getDistErpNum();
        String poStatus = poD.getPurchaseOrderStatusCd();
        BigDecimal lineTotal = poD.getLineItemTotal();
        BigDecimal itemLineTotal = new BigDecimal(0);
        String itemDistErpNum = null;
        boolean allowPoCancelFl = false;
        if(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT.equals(poStatus)) {
          allowPoCancelFl = true;
        }
        boolean toCancelFl = false;
        boolean itemsAllowNewPoFl = true;
        for(int jj=0; jj<orderItemDV.size(); jj++) {
          OrderItemData oiD = (OrderItemData) orderItemDV.get(jj);
          if(poId!=oiD.getPurchaseOrderId()) continue;
          String itemStatusCd = oiD.getOrderItemStatusCd();
          if(!(
            RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO.equals(itemStatusCd)||
            RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT.equals(itemStatusCd)||
            RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW.equals(itemStatusCd)||
            RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED.equals(itemStatusCd)
          )){
            itemsAllowNewPoFl = false;
          }
          
          if(!erpPoNum.equals(oiD.getErpPoNum())) {
            String note = "No order item - po consitancy. Erp po number does not match. "+
            "Erp po num = "+erpPoNum+" Item erp po num: "+oiD.getErpPoNum()+" Sku num: "+oiD.getItemSkuNum();
            notes.add(note);
          }
          String den = oiD.getDistErpNum();
          if(den==null || !den.trim().equals(distErpNum)) {
            String note = "No order item - po consitancy. Distributor does not match. "+
            "Erp po num = "+erpPoNum+" Sku num: "+oiD.getItemSkuNum()+" Dist erp num "+
            distErpNum+" != "+den;
            notes.add(note);
            toCancelFl = true;
          }
          int qty = oiD.getTotalQuantityOrdered();
          BigDecimal cost = oiD.getDistItemCost();
          itemLineTotal = itemLineTotal.add(cost.multiply(new BigDecimal(qty)));
        }
        lineTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        itemLineTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        if(lineTotal.subtract(itemLineTotal).abs().doubleValue()>=0.01){
          String note = "No order item - po consitancy. Cost does not match. "+
           "Erp po num = "+erpPoNum+ " : "+lineTotal+" != "+itemLineTotal;
           notes.add(note);
           toCancelFl = true;
        }
        //Cancel po if needed and possible
        if(toCancelFl && allowPoCancelFl && itemsAllowNewPoFl) {
          for(int jj=0; jj<orderItemDV.size(); jj++) {
            OrderItemData oiD = (OrderItemData) orderItemDV.get(jj);
            if(poId!=oiD.getPurchaseOrderId()) continue;
            oiD.setPurchaseOrderId(0);
            oiD.setErpPoDate(null);
            oiD.setErpPoTime(null);
            oiD.setErpPoLineNum(0);
            oiD.setErpPoNum(null);
            oiD.setAckStatusCd(null);
            OrderItemDataAccess.update(con, oiD);
          }
          poD.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED);
          String note = "PO cancelled due to order items mismatch. Erp po num: "+erpPoNum;
          notes.add(note);
          PurchaseOrderDataAccess.update(con,poD);
        }
      }

      PropertyUtil pru = new PropertyUtil(con);
      String orderProSplitTaxS = pru.fetchValueIgnoreMissing(0, orderD.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.ORDER_PROCESSING_SPLIT_TAX);
      boolean orderProSplitTax = Utility.isTrue(orderProSplitTaxS);
      String storeType = pru.fetchValueIgnoreMissing(0, orderD.getStoreId(), RefCodeNames.PROPERTY_TYPE_CD.STORE_TYPE);
      boolean isMLAStore = RefCodeNames.STORE_TYPE_CD.MLA.equals(storeType);
      //Create new purchase orders
      String prevDistErpNum = "";
      String distErpNum = null;
      PurchaseOrderData poD = null;
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      Date time = new Date();
      Date curDate = new Date();
      curDate = sdf.parse(sdf.format(curDate));
      String erpPoNum = null;
      String ountboundErpPoNum=null;
      BigDecimal itemLineTotal = new BigDecimal(0);
      BigDecimal itemTaxableTotal = new BigDecimal(0);
      int poLineNum = 0;
      int pendingFulfilmentCount = 0;
      int poCount = 0;
      
      for(int ii=0; ii<orderItemDV.size(); ii++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(ii);
        if(oiD.getPurchaseOrderId()!=0) {
          String stat = oiD.getOrderItemStatusCd();
          continue;
        }
        distErpNum = oiD.getDistErpNum();
        if(distErpNum==null || distErpNum.trim().length()==0) {
          String note = "Order item does not have a distributor. Sku num = "+
            oiD.getItemSkuNum()+" Order item id = "+oiD.getOrderItemId();
          notes.add(note);
          continue;
        }
        if(!prevDistErpNum.equals(distErpNum)) {
          prevDistErpNum = distErpNum;  
          if(poD!=null) {
              updateInsertedPurchaseOrderWithTotals(orderD, poD, con, itemLineTotal,itemTaxableTotal);
          }
          poD = PurchaseOrderData.createValue();
          poD.setOrderId(pOrderId);// SQL type:NUMBER
          poD.setErpPoNum("NA");// SQL type:VARCHAR2, not null
          poD.setPoDate(curDate);// SQL type:DATE
          poD.setDistErpNum(distErpNum);// SQL type:VARCHAR2
          if (billingOrder){
            poD.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER);
          }else{
            poD.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT);
          }
          poD.setLineItemTotal(new BigDecimal(0));// SQL type:NUMBER
          poD.setPurchaseOrderTotal(new BigDecimal(0));// SQL type:NUMBER, not null
          poD.setAddBy(pUser);// SQL type:VARCHAR2
          poD.setModBy(pUser);// SQL type:VARCHAR2
          poD.setStoreId(orderD.getStoreId());// SQL type:NUMBER
          poD.setErpSystemCd(orderD.getErpSystemCd());// SQL type:VARCHAR2
          poD = PurchaseOrderDataAccess.insert(con, poD);
          erpPoNum = "#"+orderD.getOrderNum() + "-" + Utility.padLeft(poCount,'0',2);
          if(orderProSplitTax && isResaleOrderItem(oiD)){
              erpPoNum = erpPoNum+"R";
          }
          //erpPoNum = "#"+poD.getPurchaseOrderId();
          String poSuffix = null;

          if (Utility.isInventoryOrder(orderD.getOrderSourceCd())) {
                try {
                    poSuffix = pru.fetchValue(0, orderD.getAccountId(), RefCodeNames.PROPERTY_TYPE_CD.INVENTORY_PO_SUFFIX);
                } catch (DataNotFoundException e) {
                    log.info("WARNING : " + e.getMessage());
                }
            }

          erpPoNum+=Utility.strNN(poSuffix);
          String erpPoSuffix= Utility.padLeft(poCount,'0',2) + Utility.strNN(poSuffix);

          ountboundErpPoNum = poEjb.generateOutboundPoNum(pOrderId, distErpNum,erpPoNum,erpPoSuffix);

          poD.setErpPoNum(erpPoNum);
          poD.setOutboundPoNum(ountboundErpPoNum);

          //update clw_order_add_on_charge with purchase order id
          DBCriteria dbCrit = new DBCriteria();
          dbCrit.addEqualTo(BusEntityDataAccess.ERP_NUM, distErpNum);
          dbCrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD, RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
          dbCrit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
          int distId = 0;
          IdVector distIds = BusEntityDataAccess.selectIdOnly(con,dbCrit);
          if(distIds!=null && distIds.size()>0){
        	  distId = ((Integer)distIds.get(0)).intValue();
          }
          if(distId>0){
        	  dbCrit = new DBCriteria();
        	  dbCrit.addEqualTo(OrderAddOnChargeDataAccess.BUS_ENTITY_ID, distId);
        	  dbCrit.addEqualTo(OrderAddOnChargeDataAccess.ORDER_ID, orderD.getOrderId());
        	  
        	  OrderAddOnChargeDataVector addOnChargeV = OrderAddOnChargeDataAccess.select(con,dbCrit);
        	  
        	  if(addOnChargeV!=null && addOnChargeV.size()>0){
        		  for(int i=0; i<addOnChargeV.size(); i++){
        			  OrderAddOnChargeData charge = (OrderAddOnChargeData)addOnChargeV.get(i);
        			  charge.setPurchaseOrderId(poD.getPurchaseOrderId());
        			  charge.setModBy(pUser);
        			  OrderAddOnChargeDataAccess.update(con, charge);       			 
        		  }
        	  }
        	 
          }
          
          itemLineTotal = new BigDecimal(0);
          itemTaxableTotal = new BigDecimal(0);
          poLineNum = 0;
          poCount++;
        }
        poLineNum++;
        int qty = oiD.getTotalQuantityOrdered();
        BigDecimal cost = oiD.getDistItemCost();
        itemLineTotal = itemLineTotal.add(cost.multiply(new BigDecimal(qty)));
        itemTaxableTotal = Utility.addAmt(itemTaxableTotal,oiD.getTaxAmount());

        oiD.setPurchaseOrderId(poD.getPurchaseOrderId());
        oiD.setErpPoNum(erpPoNum);
        oiD.setOutboundPoNum(ountboundErpPoNum);
        oiD.setErpOrderLineNum(oiD.getOrderLineNum());
        if (orderD.getOrderSourceCd().equals("EDI") && !isMLAStore && oiD.getCustLineNum() > 0){
        	oiD.setErpPoLineNum(oiD.getCustLineNum());
        }else{
        	oiD.setErpPoLineNum(poLineNum);
        }
        oiD.setErpOrderNum(null);
        oiD.setErpPoDate(curDate);
        oiD.setErpPoTime(time);
        if (billingOrder){
            oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
          }else{
            oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT);
          }
        
        pendingFulfilmentCount++;
        OrderItemDataAccess.update(con, oiD);

/*	ShoppingDAO.inventoryItemUpdate
	    (con, orderD.getSiteId(), 
	     oiD.getItemId(),oiD.getTotalQuantityOrdered(), 
	     orderD.getOrderSourceCd()
	     );
             */
      }
      if(poD!=null) {//the last po if there were any
        updateInsertedPurchaseOrderWithTotals(orderD, poD, con, itemLineTotal,itemTaxableTotal);
      }     
      if(pendingFulfilmentCount==orderItemDV.size()) {
        orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED);
        OrderDataAccess.update(con, orderD);
        OrderPropertyData prop = OrderPropertyData.createValue();
        prop.setOrderId(orderD.getOrderId());
        prop.setAddBy("ThruStoreErpBean");
        prop.setModBy("ThruStoreErpBean");
        prop.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        prop.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ERP_RELEASED_TIME);
        prop.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ERP_RELEASED_TIME);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        dateTimeFormat.format(new Date());
        prop.setValue(dateTimeFormat.format(new Date())); 
        OrderPropertyDataAccess.insert(con, prop);
      }
      for(int ii=0; ii<notes.size(); ii++) {     
        String note = (String) notes.get(ii);
        putOrderException(orderD, null, note, pUser, RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
      }

    }catch (Exception exc) {
       exc.printStackTrace();
       exception = exc;
     }finally {
	try {
	    releaseAppLock(con, globalOp);
	}catch ( java.sql.SQLException e ) {
         e.printStackTrace();
	 logError(e.getMessage());
	}finally{
        closeConnection(con);
        if (exception != null){// throw original exception
        	throw new RemoteException("Exception in ThruStoreErpBean.processNewThruStoreOrder: ", exception);
        }
	}
    }
  }

  //****************************************************************************
  private OrderData getOrder(Connection pCon, int pOrderId)
  throws RemoteException
  {
    String errorMess;
    OrderData orderD = null;
    try {
      orderD = OrderDataAccess.select(pCon,pOrderId);
    }
    catch (Exception exc) {
      exc.printStackTrace();
      errorMess  = exc.getMessage();
      logError(errorMess);
      throw new RemoteException(errorMess);
    }
    return orderD;
  }




 //*****************************************************************************
 /**
 * Gets vendor invoices and generates customer invoces 
 * @param pUser the user name
 * @throws   RemoteException
  */
  public void generateCustomerInvoice(String pUser,int pInvoiceDistId) 
  throws RemoteException
  {
   logDebug("TSEB EEEEEEEEEEEEEEEEEEEE generateCustomeInvoices started");
    //Check customer invoice existance (put into pending status if exists)
    //Generate customer report
    //Change status
    String errorMess;
    Connection con = null;
    try {
      con = getConnection();
      
     //Get new distributor invoices DIST_SHIPPED status
     DBCriteria dbc = new DBCriteria();
     dbc.addEqualTo(InvoiceDistDataAccess.ERP_SYSTEM_CD,RefCodeNames.ERP_SYSTEM_CD.CLW_JCI);
     dbc.addEqualTo(InvoiceDistDataAccess.INVOICE_DIST_ID,pInvoiceDistId);
     String invoiceReq = InvoiceDistDataAccess.getSqlSelectIdOnly(InvoiceDistDataAccess.INVOICE_DIST_ID,dbc);
     dbc.addOrderBy(InvoiceDistDataAccess.INVOICE_DIST_ID);
     InvoiceDistDataVector invoiceDistDV = InvoiceDistDataAccess.select(con,dbc);
     if(invoiceDistDV.size() != 1){
         throw new Exception("Unexpected error, invoiceDistDV size not 1 ("+invoiceDistDV.size()+")");
     }
     dbc = new DBCriteria();
     dbc.addOneOf(InvoiceDistDetailDataAccess.INVOICE_DIST_ID, invoiceReq);
     dbc.addOrderBy(InvoiceDistDetailDataAccess.INVOICE_DIST_ID);
   logDebug("TSEB EEEEEEEEEEEEEEEEEEEE generateCustomeInvoices sql: "+InvoiceDistDetailDataAccess.getSqlSelectIdOnly("*",dbc));
     InvoiceDistDetailDataVector invoiceDistDetailDV = InvoiceDistDetailDataAccess.select(con,dbc);
     int idPrev = 0;
     InvoiceDistDetailDataVector details = new InvoiceDistDetailDataVector();
     InvoiceDistData idD = null;
     Iterator it = invoiceDistDV.iterator();
     logDebug(">>>>>>>>>>>>>>>>>>>> SIZE::"+invoiceDistDV.size());
     while(it.hasNext()){
         logDebug("  IN ITERATION   ");
         idD = (InvoiceDistData) it.next();
         int id = idD.getInvoiceDistId();
         if(idPrev == 0){
             idPrev = id;
         }
             
         if(true) {
         //if(id!=idPrev) {
             idPrev = id;
             
             Iterator detIt = invoiceDistDetailDV.iterator();
             while(detIt.hasNext()){
                 InvoiceDistDetailData idDetD = (InvoiceDistDetailData) detIt.next();
                 int id1 = idDetD.getInvoiceDistId();
                 if(id1>idPrev) {
                     break;
                 } else if(id1==idPrev) {
                     details.add(idDetD);
                     detIt.remove();
                 }
             }
             logDebug("  CREATING CUSTOMER INVOICE FROM DIST INVOICE: "+idD.getInvoiceNum());
             createCustomerInvoice(con,  idD, details,pUser);
             details.clear();
             idD = null;
         }
         
     }
     if(idD != null){
         logDebug("  CREATING LAST CUSTOMER INVOICE FROM DIST INVOICE: "+idD.getInvoiceNum());
         createCustomerInvoice(con,  idD, invoiceDistDetailDV,pUser);
     }

   }catch (Exception exc) {
       exc.printStackTrace();
       throw new RemoteException(exc.getMessage());
     }finally {
	try {
	    releaseAppLock(con, CLW_ERP_CUST_INVOICE);
	}catch ( java.sql.SQLException e ) {
         e.printStackTrace();
	 logError(e.getMessage());
	}
        closeConnection(con);
    }
   logDebug("TSEB EEEEEEEEEEEEEEEEEEEE generateCustomeInvoices finished");
  }

  //----------------------------------------------------------------------------
  private int createCustomerInvoice(Connection pCon, InvoiceDistData pInvoiceDist, 
                          InvoiceDistDetailDataVector pInvoiceDistDet, String pUser) 
  throws Exception
  {
    int custInvoiceId = 0;
    ArrayList errors = new ArrayList();
    ArrayList warnings = new ArrayList();
    DBCriteria dbc;

    // Create the customer invoice object
    InvoiceCustData invoiceCustD = InvoiceCustData.createValue();
    invoiceCustD.setInvoiceNum("");
    invoiceCustD.setOriginalInvoiceNum(null);
    if(pInvoiceDist.getInvoiceDate() == null){
        invoiceCustD.setInvoiceDate(new Date());
    }else{
        invoiceCustD.setInvoiceDate(pInvoiceDist.getInvoiceDate());
    }

    invoiceCustD.setShippingName(pInvoiceDist.getShipToName());
    invoiceCustD.setShippingAddress1(pInvoiceDist.getShipToAddress1());
    invoiceCustD.setShippingAddress2(pInvoiceDist.getShipToAddress2());
    invoiceCustD.setShippingAddress3(pInvoiceDist.getShipToAddress3());
    invoiceCustD.setShippingAddress4(pInvoiceDist.getShipFromAddress4());
    invoiceCustD.setShippingCity(pInvoiceDist.getShipToCity());
    invoiceCustD.setShippingState(pInvoiceDist.getShipToState());
    invoiceCustD.setShippingPostalCode(pInvoiceDist.getShipToPostalCode());
    invoiceCustD.setShippingCountry(pInvoiceDist.getShipToCountry());

    BigDecimal subtotal = pInvoiceDist.getSubTotal();  
    invoiceCustD.setSubTotal(subtotal);
    BigDecimal freight = pInvoiceDist.getFreight();
    invoiceCustD.setFreight(freight);
    BigDecimal salesTax = pInvoiceDist.getSalesTax();
    invoiceCustD.setSalesTax(salesTax);
    BigDecimal discounts = pInvoiceDist.getDiscounts();
    invoiceCustD.setDiscounts(pInvoiceDist.getDiscounts());
    BigDecimal miscCharges = pInvoiceDist.getDiscounts();
    invoiceCustD.setMiscCharges(pInvoiceDist.getDiscounts());
    BigDecimal netDue = new BigDecimal(0);
    if(subtotal!=null) netDue = netDue.add(subtotal);
    if(freight!=null) netDue = netDue.add(freight);
    if(salesTax!=null) netDue = netDue.add(salesTax);
    if(miscCharges!=null) netDue = netDue.add(miscCharges);
    if(discounts!=null) netDue = netDue.subtract(discounts.abs());
    invoiceCustD.setNetDue(netDue);
    //invoiceCustD.setBatchNumber(0);
    //invoiceCustD.setPaymentTermsCd(mull);
    //invoiceCustD.setCitStatusCd(RefCodeNames.CIT_STATUS_CD.PENDING);
    invoiceCustD.setInvoiceStatusCd(
                                  RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
    invoiceCustD.setErpSystemCd(pInvoiceDist.getErpSystemCd());
    invoiceCustD.setStoreId(pInvoiceDist.getStoreId());
    invoiceCustD.setAddBy(pUser);
    invoiceCustD.setModBy(pUser);
    String invoiceType = (pInvoiceDist.getSubTotal()!=null && pInvoiceDist.getSubTotal().doubleValue() < 0) ? RefCodeNames.INVOICE_TYPE_CD.CR : RefCodeNames.INVOICE_TYPE_CD.IN;
    invoiceCustD.setInvoiceType(invoiceType);
    //invoiceCustD.setCredits
    //invoiceCustD.setBatchDate
    //invoiceCustD.setBatchTime



     //find order
     int orderId = pInvoiceDist.getOrderId();
     OrderData orderD = null;
     if(orderId!=0) {
       invoiceCustD.setOrderId(orderId);
       try {
         orderD = OrderDataAccess.select(pCon,orderId);
       }catch (DataNotFoundException exc){}
       if(orderD==null) {
         String errorMess = "No order found. Order id = "+orderId;
         logError(errorMess);
         errors.add(errorMess);
       } else {
         invoiceCustD.setAccountId(orderD.getAccountId());
         invoiceCustD.setSiteId(orderD.getSiteId());
       }
     }
      
      //get order item informtion
     OrderItemDataVector orderItemDV = null;
     if(orderId!=0) {
       dbc = new DBCriteria();
       dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,orderId);
       orderItemDV = OrderItemDataAccess.select(pCon,dbc);
     } else {
       orderItemDV = new OrderItemDataVector();
     }

     invoiceCustD = InvoiceCustDataAccess.insert(pCon,invoiceCustD);
     String invoiceNum = "#"+pInvoiceDist.getInvoiceNum();
     invoiceCustD.setInvoiceNum(invoiceNum);
     InvoiceCustDataAccess.update(pCon, invoiceCustD);

     int invoiceCustId = invoiceCustD.getInvoiceCustId();
     
     //Items
     for(int ii=0; ii<pInvoiceDistDet.size(); ii++) {
         InvoiceDistDetailData invDistDetD = (InvoiceDistDetailData) pInvoiceDistDet.get(ii);
         InvoiceCustDetailData icdD = InvoiceCustDetailData.createValue();
         icdD.setInvoiceCustId(invoiceCustId);
         icdD.setShipStatusCd(RefCodeNames.SHIP_STATUS_CD.PENDING);
         icdD.setAddBy(pUser);
         icdD.setModBy(pUser);
         
         icdD.setLineNumber(ii+1);
         icdD.setItemUom(invDistDetD.getItemUom());
         icdD.setItemSkuNum(invDistDetD.getItemSkuNum());
         icdD.setItemShortDesc(invDistDetD.getItemShortDesc());
         icdD.setItemQuantity(invDistDetD.getDistItemQuantity()); //fix for Bug ID 4614 
         icdD.setItemPack(invDistDetD.getItemPack());
         icdD.setInvoiceDetailStatusCd(null);
         icdD.setLineTotal(invDistDetD.getLineTotal());
         icdD.setCustContractPrice(invDistDetD.getItemCost());
         icdD.setOrderItemId(invDistDetD.getOrderItemId());
         OrderItemData oiDMatch = null;
         if(icdD.getOrderItemId() != 0){
             try{
                 oiDMatch=OrderItemDataAccess.select(pCon,icdD.getOrderItemId());
             }catch(Exception e){}
         }
         if(oiDMatch==null && orderItemDV!=null){    
             for(int nn=0; nn<orderItemDV.size(); nn++) {
                 OrderItemData oiD = (OrderItemData) orderItemDV.get(nn);
                 if(oiD.getItemSkuNum()==icdD.getItemSkuNum() || invDistDetD.getOrderItemId() == oiD.getOrderItemId()) {
                     int orderItemId = oiD.getOrderItemId();
                     icdD.setOrderItemId(orderItemId);
                     oiDMatch = oiD;
                     break;
                 }
             }
         }
         logDebug("TSEB EEEEEEEEEEEEEEEEEEEE generateCustomeInvoices icdD: "+icdD);
         InvoiceCustDetailDataAccess.insert(pCon,icdD);
         if (oiDMatch == null) {
             String errorMess = "No order item found " + "for customer invoice item " + icdD.getItemSkuNum();
             logError(errorMess);
             errors.add(errorMess);
         } else {
             checkCustInvoicedQuantity(errors, warnings, icdD, oiDMatch, pUser, pCon);
         }
     }
     //check if the order should be closed out
     DBCriteria crit = new DBCriteria ();
     crit.addEqualTo(OrderItemDataAccess.ORDER_ID, invoiceCustD.getOrderId());
     Iterator it = OrderItemDataAccess.select(pCon,crit).iterator();
     //Iterator it = orderItemDV.iterator();
     boolean allInvoiced = true;
     while(it.hasNext()){
         OrderItemData oid = (OrderItemData) it.next();
         if(Utility.isGoodOrderItemStatus(oid.getOrderItemStatusCd()) && !RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED.equals(oid.getOrderItemStatusCd())){
             allInvoiced = false;
         }
     }
     if(allInvoiced && orderD!=null){
         if(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED.equals(orderD.getOrderStatusCd())){
            orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.INVOICED);
            orderD.setModBy("thrustoreErp");
            OrderDataAccess.update(pCon,orderD);
         }
     }
     
    if(errors.size() > 0) {
      invoiceCustD.setInvoiceStatusCd(
                         RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
      InvoiceCustDataAccess.update(pCon,invoiceCustD);
    }
    for(int ii=0; ii<errors.size(); ii++) {
      String error = (String) errors.get(ii);
      logError(error);
      putInvoiceException(invoiceCustD, null, error, pUser, "CLW_ERP customer invoice error");
    }
    for(int ii=0; ii<warnings.size(); ii++) {
      String warning = (String) warnings.get(ii);
      logError(warning);
      putInvoiceException(invoiceCustD, null, warning, pUser, "CLW_ERP customer invoice warning");
    }
    pInvoiceDist.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_PROCESSED);
    InvoiceDistDataAccess.update(pCon, pInvoiceDist);
     return custInvoiceId;
  }

  /**
   * Verify that the quantity invoiced (including the quantities from
   * any pre-existing released invoices) doesn't exceed the quantity ordered.
   * If so, log an exception.  If on the other hand, the two quantities
   * are now equal, update the order item status to INVOICED.
   */
   private void checkCustInvoicedQuantity(ArrayList pErrors, 
                                         ArrayList pWarnings, 
                                         InvoiceCustDetailData icdD,
                                         OrderItemData oiD,
                                         String pUser,
                                         Connection pCon)
   throws Exception                                         
  {
    
    int quantityInvoiced = 0;
    int quantityOrdered = 0;


    final String GENERATED =
                 "'" + RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED + "'";
    final String GENERATED_ERROR =
                 "'" + RefCodeNames.INVOICE_STATUS_CD.ERP_GENERATED_ERROR + "'";
    final String CANCELLED =
                 "'" + RefCodeNames.INVOICE_STATUS_CD.CANCELLED + "'";

    // Sum up all the quantities previously invoiced for the order item 
    // XXX: Should we be eliminating credit invoice items from this
    // check?
    Statement stmt = pCon.createStatement();
    String query = " SELECT SUM(" + InvoiceCustDetailDataAccess.ITEM_QUANTITY
            + ") from " +	InvoiceCustDetailDataAccess.CLW_INVOICE_CUST_DETAIL
            +	" where " +	InvoiceCustDetailDataAccess.ORDER_ITEM_ID
            + " = " + oiD.getOrderItemId()
            + " and " + InvoiceCustDetailDataAccess.INVOICE_CUST_ID
            + " not in "
            + " (SELECT " + InvoiceCustDataAccess.INVOICE_CUST_ID
            + " from " + InvoiceCustDataAccess.CLW_INVOICE_CUST
            + " where "
            + InvoiceCustDataAccess.INVOICE_STATUS_CD + " = " + GENERATED
            + " or "
            + InvoiceCustDataAccess.INVOICE_STATUS_CD + " = " + GENERATED_ERROR
            + " or "
            + InvoiceCustDataAccess.INVOICE_STATUS_CD + " = " + CANCELLED
            + ")";
    ResultSet rs = stmt.executeQuery( query );
    if (rs.next()) {
      quantityInvoiced = rs.getInt(1);
    }
    // Get the quantity ordered and compare.  If the invoiced
    // quantity is greater, add an exception.
    quantityOrdered = oiD.getTotalQuantityOrdered();
    if (quantityInvoiced > quantityOrdered) {
      String warning = "Customer invoice warning. Order id = "+oiD.getOrderItemId()+
                    " Quantity invoiced (" + quantityInvoiced
                    + ") exceeds quantity ordered (" + quantityOrdered + ")";
      logError(warning);
      pWarnings.add(warning);
    } else {
        // Otherwise all is well.  Update the order item record with the
        // updated quantity shipped and also set the status to INVOICED
        // if the total quantity is now accounted for.
        oiD.setTotalQuantityShipped(quantityInvoiced);
        if (quantityInvoiced == quantityOrdered) {
          oiD.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED);
        }
        oiD.setModBy(pUser);
        OrderItemDataAccess.update(pCon,oiD);
      }
  }


  ///////////////////////////////////////////////////////////////////////////////////////////////////////
 // Order version
 private void putOrderException(OrderData pOrder, OrderItemData pOrderItem, String pErrorMess, String pUser, String pShortDesc)
 {
    OrderPropertyData orderPropertyD = 
            buildException(pErrorMess, pUser, pShortDesc);
    orderPropertyD.setOrderId(pOrder.getOrderId());
    if (pOrderItem != null) {
        orderPropertyD.setOrderItemId(pOrderItem.getOrderItemId());
    }
    addException(orderPropertyD);
 }

 // Distributor invoice version
 private void putInvoiceException(InvoiceDistData pInvoice, InvoiceDistDetailData pInvoiceDetail, String pErrorMess, String pUser, String pShortDesc)
  {
    OrderPropertyData orderPropertyD = 
            buildException(pErrorMess, pUser, pShortDesc);
    orderPropertyD.setInvoiceDistId(pInvoice.getInvoiceDistId());
    orderPropertyD.setOrderId(pInvoice.getOrderId());
    if (pInvoiceDetail != null) {
        orderPropertyD.setInvoiceDistDetailId(pInvoiceDetail.getInvoiceDistDetailId());
        orderPropertyD.setOrderItemId(pInvoiceDetail.getOrderItemId());
    }
    addException(orderPropertyD);
  }

 // Customer invoice version
 private void putInvoiceException(InvoiceCustData pInvoice, InvoiceCustDetailData pInvoiceDetail, String pErrorMess, String pUser, String pShortDesc)
  {
    OrderPropertyData orderPropertyD = 
            buildException(pErrorMess, pUser, pShortDesc);
    orderPropertyD.setInvoiceCustId(pInvoice.getInvoiceCustId());
    orderPropertyD.setOrderId(pInvoice.getOrderId());
    if (pInvoiceDetail != null) {
        orderPropertyD.setInvoiceCustDetailId(pInvoiceDetail.getInvoiceCustDetailId());
        orderPropertyD.setOrderItemId(pInvoiceDetail.getOrderItemId());
    }
    addException(orderPropertyD);
  }

 private OrderPropertyData buildException(String pErrorMess, String pUser, String pShortDesc)
  {
    OrderPropertyData orderPropertyD = OrderPropertyData.createValue();
    orderPropertyD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
    orderPropertyD.setOrderPropertyStatusCd("ACTIVE");
    orderPropertyD.setShortDesc((pShortDesc == null ?
                                 "Lawson UNKNOWN" : pShortDesc));
    // Truncate if greater than length of database field.
    if (pErrorMess != null && pErrorMess.length() > 2000) {
        orderPropertyD.setValue(pErrorMess.substring(0,2000));
    } else {
        orderPropertyD.setValue(pErrorMess);
    }

    orderPropertyD.setAddBy(pUser);
    orderPropertyD.setModBy(pUser);
    return orderPropertyD;
  }

 private void addException(OrderPropertyData pOrderPropertyD)
  {
    Connection con = null;
    try {
      con = getConnection();
      OrderPropertyDataAccess.insert(con,pOrderPropertyD);
    }
    catch (Exception exc) {
      String errorMess = "Can't add record to CLW_ORDER_PROPERTY table. "+exc.getMessage();
      logError(errorMess);
    }finally {
      closeConnection(con);
    }
  }
  //-------------------------------------------------------------------------------
  private GregorianCalendar createCalendar(String pDateS){
    if(pDateS==null) return null;
    char[] dateA = pDateS.toCharArray();
    int ii=0;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa=='/') break;
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==0 || ii==dateA.length) return null;
    String monthS = new String(dateA,0,ii);
    ii++;
    int ii1=ii;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa=='/') break;
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==ii1 || ii==dateA.length) return null;
    String dayS = new String(dateA,ii1,ii-ii1);
    ii++;
    ii1 = ii;
    for(; ii<dateA.length; ii++) {
      char aa = dateA[ii];
      if(aa<'0'||aa>'9') return null;
    }
    if(ii==ii1) return null;
    String yearS = new String(dateA,ii1,ii-ii1);
    int month = 0;
    int day = 0;
    int year = 0;
    try{
      month = Integer.parseInt(monthS);
      month--;
      day = Integer.parseInt(dayS);
      year = Integer.parseInt(yearS);
      if(year<100) year += 2000;
    } catch(NumberFormatException exc) {
      return null;
    }
    GregorianCalendar calendar = null;
    try{
      calendar = new GregorianCalendar(year,month,day);
    } catch(Exception exc) {
      return null;
    }
    return calendar;
  }

  //==============================================================================
  private boolean allItemsHavePO(OrderData pOrder, Connection pCon)
  throws Exception
  {
      DBCriteria dbc;
      int orderId = pOrder.getOrderId();
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderItemDataAccess.ORDER_ID,orderId);
      dbc.addNotEqualTo(OrderItemDataAccess.ORDER_ITEM_STATUS_CD,RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
      dbc.addOrderBy(OrderItemDataAccess.ORDER_ITEM_ID);
      OrderItemDataVector orderItemDV = OrderItemDataAccess.select(pCon,dbc);
      if (orderItemDV == null || orderItemDV.size() == 0) {
          String errorMess = "Error.  No order items for order id " + orderId;
          throw new Exception(errorMess);
      }
      boolean result = true;
      for(int i=0; i<orderItemDV.size(); i++) {
        OrderItemData oiD = (OrderItemData) orderItemDV.get(i);
        String poNum = oiD.getErpPoNum();
        if (!Utility.isSet(poNum)) {
            result = false;
        }
      }
      return result;
  }

  private Integer toInteger(String str)
  throws Exception
  {
      Integer val = null;
      try {
          val = new Integer(str);
      } catch (NumberFormatException e) {
          String errorMess = "Can't convert " + str + " to integer";
          throw new Exception(errorMess);
      }
      return val;
  }

  // XXX: Should be moved to Utility
  private boolean equals(String one, String two)
  {
      boolean result = false;
      if (!Utility.isSet(one)) {
          if (!Utility.isSet(two)) {
              result = true;
          }
      } else if (Utility.isSet(two) && one.trim().equals(two.trim())) {
          result = true;
      }
      return result;
  }

  // XXX: Should be moved to Utility
  private boolean equalsIgnoreCase(String one, String two)
  {
      boolean result = false;
      if (!Utility.isSet(one)) {
          if (!Utility.isSet(two)) {
              result = true;
          }
      } else if (Utility.isSet(two) &&
                 one.trim().toUpperCase().equals(two.trim().toUpperCase())) {
          result = true;
      }
      return result;
  }

  
}

