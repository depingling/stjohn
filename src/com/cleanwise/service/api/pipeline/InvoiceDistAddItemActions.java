/*
 * InvoiceDistAddItemActions.java
 *
 * Created on August 1, 2005, 2:36 PM
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import java.sql.SQLException;
import java.util.Iterator;
import java.sql.Connection;
/**
 *Adds the order item actions that are associated with a distributor invoice
 * @author bstevens
 */
public class InvoiceDistAddItemActions implements InvoiceDistPipeline{

    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        try{
            if(pBaton.isHasError()){
                return;
            }
            InvoiceDistData invoice = pBaton.getInvoiceDistData();
            InvoiceDistDetailDataVector invoiceItems = pBaton.getInvoiceDistDetailDataVector();
            Iterator it = invoiceItems.iterator();
            while(it.hasNext()){
                InvoiceDistDetailData invDet = (InvoiceDistDetailData) it.next();
                if(invDet.getDistItemQuantity() == 0 || invDet.getOrderItemId() == 0){
                    continue;
                }
                // add the shipped action
                /*java.util.Date currentDate = new java.util.Date();
                OrderItemActionData shippedAction = getOrderItemAction(pCon, invDet, RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED);
                shippedAction.setOrderId(invoice.getOrderId());
                shippedAction.setOrderItemId(invDet.getOrderItemId());
                shippedAction.setAffectedSku(String.valueOf(invDet.getItemSkuNum()));
                //shippedAction.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED);
                shippedAction.setQuantity(invDet.getDistItemQuantity());
                shippedAction.setActionDate(invoice.getInvoiceDate());
                shippedAction.setActionTime(invoice.getInvoiceDate());
                shippedAction.setModBy(invoice.getModBy());
                if(shippedAction.getOrderItemActionId() == 0){
                    OrderItemActionDataAccess.insert(pCon,shippedAction);
                }else{
                    OrderItemActionDataAccess.update(pCon,shippedAction);
                }*/

                // add the invoiced action
                OrderItemActionData invoicedAction = getOrderItemAction(pCon, invDet, RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED);
                invoicedAction.setOrderId(invoice.getOrderId());
                invoicedAction.setOrderItemId(invDet.getOrderItemId());
                invoicedAction.setAffectedSku(String.valueOf(invDet.getItemSkuNum()));
                //invoicedAction.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED);
                invoicedAction.setQuantity(invDet.getDistItemQuantity());
                invoicedAction.setActionDate(invoice.getInvoiceDate());
                invoicedAction.setActionTime(invoice.getInvoiceDate());
                invoicedAction.setModBy(invoice.getModBy());
                if(invoicedAction.getOrderItemActionId() == 0){
                    OrderItemActionDataAccess.insert(pCon,invoicedAction);
                }else{
                    OrderItemActionDataAccess.update(pCon,invoicedAction);
                }
            }
        }catch(java.sql.SQLException e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

    private OrderItemActionData getOrderItemAction(Connection pCon,InvoiceDistDetailData invDet, String pActionCd)throws SQLException{
        OrderItemActionData anAction;
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderItemActionDataAccess.ACTUAL_TRANSACTION_ID,invDet.getInvoiceDistDetailId());
        crit.addEqualTo(OrderItemActionDataAccess.AFFECTED_TABLE,InvoiceDistDetailDataAccess.CLW_INVOICE_DIST_DETAIL);
        crit.addEqualTo(OrderItemActionDataAccess.ACTION_CD,pActionCd);
        OrderItemActionDataVector oiadv = OrderItemActionDataAccess.select(pCon,crit);
        if(oiadv == null || oiadv.isEmpty()){
            anAction = OrderItemActionData.createValue();
            anAction.setAffectedTable(InvoiceDistDetailDataAccess.CLW_INVOICE_DIST_DETAIL);
            anAction.setActualTransactionId(invDet.getInvoiceDistDetailId());
            anAction.setActionCd(pActionCd);
            anAction.setAddBy("InvoiceDistAddItemActions");
        }else{
            anAction = (OrderItemActionData) oiadv.get(0);
        }
        return anAction;
    }
}
