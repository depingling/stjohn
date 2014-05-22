package com.cleanwise.service.api.pipeline;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
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
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.CustomerOrderChangeRequestData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemChangeRequestData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SaveWebOrderChangeRequest implements OrderPipeline {
	/**
	 * Process this pipeline.
	 * 
	 * @param OrderRequestData
	 *            the order request object to act upon
	 * @param Connection
	 *            a active database connection
	 * @param APIAccess
	 * 
	 */
	public OrderPipelineBaton process(OrderPipelineBaton pBaton,
			OrderPipelineActor pActor, Connection pCon, APIAccess pFactory)
			throws PipelineException {
		try{
			doRecievedItemProcessing(pBaton,pCon);
			doQtyChangeProcessing(pBaton,pCon);
		}catch(Exception e){
			e.printStackTrace();
			throw new PipelineException(e.getMessage());
		}

		return pBaton;
	}
	
	
	private void doQtyChangeProcessing(OrderPipelineBaton pBaton,Connection pCon) throws SQLException{
		CustomerOrderChangeRequestData req = pBaton.getCustomerOrderChangeRequestData();
		OrderData ord = pBaton.getOrderData();
		
		OrderItemDataVector toCancel = new OrderItemDataVector();
		Iterator it = req.getOrderItemChangeRequests().iterator();
		while (it.hasNext()) {
			OrderItemChangeRequestData itmReq = (OrderItemChangeRequestData) it.next();
			OrderItemData oItm = getOrderItemData(pBaton.getOrderItemDataVector(), itmReq.getOrderItemId());
			if(itmReq.getNewTotalQuantityOrdered() == null){
				continue;
			}
			int diff = oItm.getTotalQuantityOrdered() - itmReq.getNewTotalQuantityOrdered().intValue();
			if(diff!=0){
				if(0 == itmReq.getNewTotalQuantityOrdered().intValue()){
					toCancel.add(oItm);
				}
				oItm.setTotalQuantityOrdered(itmReq.getNewTotalQuantityOrdered().intValue());
				String actionCd;
				if(diff > 0){
					if(req.isMarkReceived()){
						//if the qty diff is negative and the order is marked for
						//recieving assume that the item is being cacnelled for backorder
						//reasons
						actionCd = RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED_BACKORDER;
					}else{
						actionCd = RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED;
					}
				}else{
					actionCd = RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.QUANTITY_CHANGE;
				}
				
				recordQuantityChange(actionCd,pCon,req.getUserName(),ord,oItm,diff);
				OrderItemDataAccess.update(pCon,oItm);
			}
		}
		
		//if there is something to cancel cancel it
		if(!toCancel.isEmpty()){
			OrderDAO.cancelAndUpdateOrderItems(pCon,toCancel , req.getOrderData(), req.getUserName());
		}
	}
	

	/**
	 *Processes the reciegving system logic if that data is present 
	 *
	 * @param pBaton
	 * @param pCon
	 */
	private void doRecievedItemProcessing(OrderPipelineBaton pBaton,Connection pCon) throws SQLException,RemoteException{
		
		boolean orderHadAlreadyBeenRecieved = false;
		HashMap distCache = new HashMap();
		CustomerOrderChangeRequestData req = pBaton.getCustomerOrderChangeRequestData();
		int orderId = req.getOrderData().getOrderId();
		if (req.isMarkReceived()) {
			OrderPropertyData opd = OrderDAO.getOrderProperty(pCon, orderId,RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
			if (opd == null) {
				opd = OrderPropertyData.createValue();
				opd.setAddBy(req.getUserName());
				opd.setOrderId(orderId);
				opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
				opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
				opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED);
			}else{
				orderHadAlreadyBeenRecieved = true;
			}
			opd.setValue(Boolean.TRUE.toString());
			opd.setModBy(req.getUserName());
			pBaton.addOrderPropertyData(opd);
		}

		
		
		Date currentDate = new Date();
		
		// now do the line item updates
		Iterator it = req.getOrderItemChangeRequests().iterator();
		while (it.hasNext()) {
			OrderItemChangeRequestData itmReq = (OrderItemChangeRequestData) it.next();
			OrderItemData oItm = getOrderItemData(pBaton.getOrderItemDataVector(), itmReq.getOrderItemId());
			if (req.isMarkReceived() && itmReq.getNewTotalQuantityReceived() != null) {
				oItm.setTotalQuantityReceived(itmReq.getNewTotalQuantityReceived().intValue());

				//log the order item action
				int recQtyDiff = itmReq.getNewTotalQuantityReceived().intValue() - oItm.getTotalQuantityReceived();
	            //if both are 0 then we want to know that the user is recieving 0 as that is significant (means they didn't get anything)
	            if(recQtyDiff != 0 || !orderHadAlreadyBeenRecieved){
	                //allow negatives as they may decrement a qty, we just want the history
	                OrderItemActionData anAct = OrderItemActionData.createValue();
	                anAct.setOrderId(oItm.getOrderId());
	                anAct.setOrderItemId(oItm.getOrderItemId());
	                anAct.setAffectedSku(String.valueOf(oItm.getItemSkuNum()));
	                anAct.setAffectedLineItem(oItm.getOrderLineNum());
	                anAct.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.RECEIVED_AGAINST);
	                anAct.setQuantity(recQtyDiff);
	                anAct.setActionDate(currentDate);
	                anAct.setActionTime(currentDate);
	                OrderItemActionDataAccess.insert(pCon,anAct);
	            }
				
				
				
				//check to see if we need to change any order quantites
				if(isCancelBackorderedLinesForDistributor(pBaton.getOrderData().getStoreId(),oItm.getDistErpNum(),pCon,distCache)){
					DBCriteria crit = new DBCriteria();
					crit.addEqualTo(InvoiceDistDetailDataAccess.ORDER_ITEM_ID,oItm.getOrderItemId());
					InvoiceDistDetailDataVector iddv = InvoiceDistDetailDataAccess.select(pCon,crit);
					//currently there is a pieplein process that canceles backorders within the invoice processing
					//for the distributor invoice.  This procedure is assumed to be more accurate than the recieving
					//system and if tehre are any invoices recieved so far then leave the order unchanged
					if(iddv == null || iddv.isEmpty()){
						//if the total qty ordered is less then the qty recieved set it for action by the qty change
						//request processing logic
						if(itmReq.getNewTotalQuantityReceived().intValue() < oItm.getTotalQuantityOrdered()){
							itmReq.setNewTotalQuantityOrdered(itmReq.getNewTotalQuantityReceived());
						}
					}
				}
			}
		}
	}
	
	
	
	/**
     *Records the quantity change in the order item action table.  Curently this is setup such that there is only 
     *assumed to ever be one cancelation due to backorder against a given line.  If you run the same invoice
     *through multiple times it will just update this field.  If this ever changes to need to handle multiple invoices
     *correctly this logic may need to be revised.
     */
    private void recordQuantityChange(String actionCode, Connection pCon,String user,OrderData pOrder,OrderItemData pOrdItm, int cancelQty)
    throws SQLException{
        OrderItemActionData act;
        act = OrderItemActionData.createValue();
        act.setAddBy(user);
        act.setActionCd(actionCode);
        act.setActionDate(new Date());
        act.setActualTransactionId(pOrdItm.getOrderItemId());
        act.setAffectedId(0);
        act.setAffectedLineItem(pOrdItm.getOrderLineNum());
        act.setAffectedOrderNum(pOrder.getOrderNum());
        act.setAffectedSku(Integer.toString(pOrdItm.getItemSkuNum()));
        act.setAffectedTable(OrderItemDataAccess.CLW_ORDER_ITEM);
        //act.setAmount();
        act.setComments(null);
        act.setModBy(user);
        act.setOrderId(pOrder.getOrderId());
        act.setOrderItemId(pOrdItm.getOrderItemId());
        act.setQuantity(cancelQty);
        if(act.getOrderItemActionId() == 0){
            OrderItemActionDataAccess.insert(pCon,act);
        }else{
            OrderItemActionDataAccess.update(pCon,act);
        }
    }
	
	/*
	 * //Match up the item lists
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
                    pip.processPipeline(mCon, pFactory, order, RefCodeNames.PIPELINE_CD.REPROCESS_ORDER);
                }finally{
                    //we don't care what the status that the pipeline sets the order to, we will set it to
                    //the status that the order currently is in.  We do not want to change it no matter what
                    //the circumstances!
                    order.setOrderStatusCd(origStatusCd);
                    OrderDataAccess.update(pCon,order);
                }
                
            }
	 */
	
	private boolean isCancelBackorderedLinesForDistributor(int storeId,String erpnum, Connection pCon, HashMap cache)throws SQLException,RemoteException{
		if(!cache.containsKey(erpnum)){
			DBCriteria crit = new DBCriteria();
			crit.addEqualTo(BusEntityDataAccess.ERP_NUM,erpnum);
			crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
			BusEntityDataVector dists = BusEntityDataAccess.select(pCon,crit);
			int distId = 0;
			if(dists!= null && !dists.isEmpty()){
				distId = ((BusEntityData) dists.get(0)).getBusEntityId();
			}
			if(distId == 0){
				cache.put(erpnum,"false");
			}
			
			
			PropertyUtil p = new PropertyUtil(pCon);
				
	        String val = p.fetchValueIgnoreMissing(0, distId, RefCodeNames.PROPERTY_TYPE_CD.CANCEL_BACKORDERED_LINES);
	        cache.put(erpnum,val);
		}
		return Utility.isTrue((String) cache.get(erpnum));
	}

	/**
	 * Helper method to get the order item based off the id out of the
	 * orderitemdatavector
	 */
	private OrderItemData getOrderItemData(OrderItemDataVector itms,
			int orderItemId) {
		if (itms == null || orderItemId == 0) {
			return null;
		}
		Iterator it = itms.iterator();
		while (it.hasNext()) {
			OrderItemData itm = (OrderItemData) it.next();
			if (itm.getOrderItemId() == orderItemId) {
				return itm;
			}
		}
		return null;
	}
}
