/*
 * InvoiceDistCancelBackorders.java
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDescDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.*;

/**
 * Based off the disributor configuration will cancel the backordered quantity and items that remain on this order
 * @author bstevens
 */
public class InvoiceDistCancelBackorders implements InvoiceDistPipeline{
    private static final Logger log = Logger.getLogger(InvoiceDistCancelBackorders.class);
    private InvoiceDistDetailDataVector mInvoiceItems;
    private InvoiceDistData mInvoice;
    private ArrayList items;
    private OrderItemDataVector toCancel;
    private OrderItemDataVector toChange;
    private OrderData mOrder;
    private Connection mCon;
    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        try{
            if(pBaton.isHasError()){
                return;
            }
            InvoiceDistDescDataVector iddv = pBaton.getPreviousInvoices(pCon);
            if(iddv != null && iddv.size() >= 1){
                log.info("InvoiceDistCancelBackorders setup to run against first invoice only, exiting");
                return;
            }
            
            mInvoice = pBaton.getInvoiceDistData();
            if(mInvoice.getOrderId() == 0 || !Utility.isSet(mInvoice.getErpPoNum())){
            	log.info("InvoiceDistCancelBackorders found invoice with no po or no order id, exiting invid: "+mInvoice.getInvoiceDistId());
                return;
            }
            if(mInvoice.getBusEntityId() == 0){
            	log.info("InvoiceDistCancelBackorders found invoice with no distributor, exiting invid: "+mInvoice.getInvoiceDistId());
                return;
            }
            
            mCon = pCon;
            mOrder = pBaton.getOrder();
            PropertyUtil p = new PropertyUtil(pCon);
            String val = p.fetchValueIgnoreMissing(0, pBaton.getInvoiceDistData().getBusEntityId(), RefCodeNames.PROPERTY_TYPE_CD.CANCEL_BACKORDERED_LINES);
            if(!Utility.isTrue(val)){
                return;
            }
            
            BusEntityData dist = BusEntityDataAccess.select(mCon,mInvoice.getBusEntityId());
            mInvoiceItems = pBaton.getInvoiceDistDetailDataVector();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderItemDataAccess.ORDER_ID,mInvoice.getOrderId());
            crit.addEqualTo(OrderItemDataAccess.ERP_PO_NUM,mInvoice.getErpPoNum());
            crit.addEqualTo(OrderItemDataAccess.DIST_ERP_NUM,dist.getErpNum());
            OrderItemDataVector oitems = OrderItemDataAccess.select(mCon,crit);
            
            
            
            //Match up the item lists
            items = new ArrayList();
            toCancel = new OrderItemDataVector();
            toChange = new OrderItemDataVector();
            Iterator it = oitems.iterator();
            while(it.hasNext()){
                OrderItemData det = (OrderItemData) it.next();
                AnItem item = new AnItem(det, sumQtyShipped(det.getOrderItemId()));
                item.adjustQtyAndAddToCancelList();
            }
            OrderData order = null;
            
            if(!toChange.isEmpty() || !toCancel.isEmpty()){                
                order = pBaton.getOrder();
                if(!toCancel.isEmpty()){
                    OrderDAO.cancelAndUpdateOrderItems(pCon, toCancel, order, "invoice processing");
                }
                
                String origStatusCd = order.getOrderStatusCd();
                PipelineUtil pip = new PipelineUtil();
                try{
                    pip.processPipeline(mCon, pFactory, order, RefCodeNames.PIPELINE_CD.CANCEL_BACKORDERED);
                }finally{
                    //we don't care what the status that the pipeline sets the order to, we will set it to
                    //the status that the order currently is in.  We do not want to change it no matter what
                    //the circumstances!
                    order.setOrderStatusCd(origStatusCd);
                    OrderDataAccess.update(pCon,order);
                }
                
            }

            
            /*if(!toChange.isEmpty()){
                 BigDecimal distQuantity;
                BigDecimal uomMult = orderItemD.getDistUomConvMultiplier();
                if(uomMult == null){
                  uomMult = new BigDecimal(1);
                }
                distQuantity = uomMult.multiply(new BigDecimal(qtyNew));
                orderItemD.setDistItemQuantity(distQuantity.intValue());
                
                OrderData order = OrderDataAccess.select(pCon,mInvoice.getOrderId());
                pFactory.getOrderAPI().cancelOrderItems(order, toCancel, "invoice processing");
            }
            
            OrderDAO.updateOrderSummaryInfo();
             */
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }
    
    /**
     *Sums up everything on this invoice that has shipped against this order item.  Accounts for multiple
     *shippments against the same item
     */
    private int sumQtyShipped(int orderItemId){
        if(orderItemId == 0){
            return 0;
        }
        int sumToReturn = 0;
        Iterator it = mInvoiceItems.iterator();
        while(it.hasNext()){
            InvoiceDistDetailData inv = (InvoiceDistDetailData) it.next();
            if(inv.getOrderItemId() == orderItemId){
                sumToReturn = sumToReturn + inv.getDistItemQuantity();
            }
        }
        return sumToReturn;
    }
    
    /**
     *Records the quantity change in the order item action table.  Curently this is setup such that there is only 
     *assumed to ever be one cancelation due to backorder against a given line.  If you run the same invoice
     *through multiple times it will just update this field.  If this ever changes to need to handle multiple invoices
     *correctly this logic may need to be revised.
     */
    private void recordQuantityCancelled(OrderItemData pOrdItm, int cancelQty)
    throws SQLException{
        OrderItemActionData act;
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderItemActionDataAccess.ORDER_ITEM_ID,pOrdItm.getOrderItemId());
        crit.addEqualTo(OrderItemActionDataAccess.ACTION_CD,RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED_BACKORDER);
        OrderItemActionDataVector existing = OrderItemActionDataAccess.select(mCon,crit);
        if(existing.size() > 0){
            act = (OrderItemActionData) existing.get(0);
        }else{
            act = OrderItemActionData.createValue();
            act.setAddBy(mInvoice.getModBy());
        }
        act.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED_BACKORDER);
        act.setActionDate(mInvoice.getInvoiceDate());
        act.setActualTransactionId(mInvoice.getInvoiceDistId());
        act.setAffectedId(0);
        act.setAffectedLineItem(pOrdItm.getOrderLineNum());
        act.setAffectedOrderNum(mOrder.getOrderNum());
        act.setAffectedSku(Integer.toString(pOrdItm.getItemSkuNum()));
        act.setAffectedTable(InvoiceDistDataAccess.CLW_INVOICE_DIST);
        //act.setAmount();
        act.setComments(null);
        act.setModBy(mInvoice.getModBy());
        act.setOrderId(mOrder.getOrderId());
        act.setOrderItemId(pOrdItm.getOrderItemId());
        act.setQuantity(cancelQty);
        if(act.getOrderItemActionId() == 0){
            OrderItemActionDataAccess.insert(mCon,act);
        }else{
            OrderItemActionDataAccess.update(mCon,act);
        }
    }
    
    private class AnItem{
        private int totalQtyShipped;
        private OrderItemData ord;
        
        private AnItem(OrderItemData pOrd, int pTotalQtyShipped){
            ord = pOrd;
            totalQtyShipped = pTotalQtyShipped;
        }
        
        private void adjustQtyAndAddToCancelList()throws SQLException{
            if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(ord.getOrderItemStatusCd())){
                //nothing else necessary
                return;
            }
            int allSums = ord.getTotalQuantityShipped() + totalQtyShipped;
            if(allSums == 0){
                //cancel the entire line
                toCancel.add(ord);
            }
            int toCancelQty = ord.getTotalQuantityOrdered() - allSums;
            if(toCancelQty > 0){
                recordQuantityCancelled(ord, toCancelQty);
                //cancel the quantity
                ord.setTotalQuantityOrdered(ord.getTotalQuantityOrdered() - toCancelQty);
                toChange.add(ord);
            }
            OrderItemDataAccess.update(mCon,ord);
        }
    }
    
}
