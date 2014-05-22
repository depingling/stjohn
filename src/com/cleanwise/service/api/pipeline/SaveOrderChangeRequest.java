/**
 *
 */
package com.cleanwise.service.api.pipeline;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
import com.cleanwise.service.api.pipeline.OrderRequestProductParsing.DistributorCharges;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.ItemSkuMapping;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PipelineUtil;
import com.cleanwise.service.api.util.RefCodeNames;
//import com.cleanwise.service.api.util.TaxUtil;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.CatalogCategoryData;
import com.cleanwise.service.api.value.CatalogCategoryDataVector;
import com.cleanwise.service.api.value.ContractItemData;
import com.cleanwise.service.api.value.CustomerOrderChangeRequestData;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderAddressDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderItemActionData;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderItemChangeRequestData;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemJoinData;
import com.cleanwise.service.api.value.OrderItemMetaData;
import com.cleanwise.service.api.value.OrderItemMetaDataVector;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.PurchaseOrderDataVector;

/**
 * @author ssharma
 *
 */
public class SaveOrderChangeRequest implements OrderPipeline{

	public OrderPipelineBaton process(OrderPipelineBaton pBaton,
			OrderPipelineActor pActor, Connection pCon, APIAccess pFactory)
			throws PipelineException {

		try{

			CustomerOrderChangeRequestData mChangeOrdReq = pBaton.getCustomerOrderChangeRequestData();
			if(mChangeOrdReq==null){
				throw new Exception("Order Change Request data was null");
			}
			String pUser = mChangeOrdReq.getUserName();
			int orderId = mChangeOrdReq.getOrderData().getOrderId();

			OrderData orderD = OrderDataAccess.select(pCon, orderId);

			//OrderItems
			DBCriteria crit = new DBCriteria();
			crit.addEqualTo(OrderItemDataAccess.ORDER_ID, orderId);
			OrderItemDataVector oiDV = OrderItemDataAccess.select(pCon, crit);
			Collections.sort(oiDV,ORDER_ITEM_CUST_LINE_NUM_COMPARE);

			OrderItemDataVector changeItems = new OrderItemDataVector();
			OrderItemActionDataVector changeItemActions = new OrderItemActionDataVector();
			OrderItemDataVector addItems = new OrderItemDataVector();
			HashMap poMap = new HashMap<String,PurchaseOrderData>();    //(orderId+dist, po)

			List changeItemReqs = new ArrayList(mChangeOrdReq.getOrderItemChangeRequests());
			Collections.sort(changeItemReqs,CHANGE_ITEM_CUST_LINE_NUM_COMPARE);

                        Iterator it = changeItemReqs.iterator();
			while(it.hasNext()){

				OrderItemChangeRequestData itemReq = (OrderItemChangeRequestData) it.next();
				OrderItemData coid = itemReq.getOrderItemData();
 				String distSku = coid.getDistItemSkuNum();
				int lineNum = coid.getCustLineNum();
				boolean foundF1 = false;

				String actionCd = "";
				OrderItemActionData oiaD = OrderItemActionData.createValue();
				for(int i=0; i<oiDV.size(); i++){
					OrderItemData oid = (OrderItemData)oiDV.get(i);

					String distErpNum = oid.getDistErpNum();
					String key = distErpNum+orderId;
					if(distErpNum != null && !poMap.containsKey(key)){

						crit = new DBCriteria();
						crit.addEqualTo(PurchaseOrderDataAccess.ORDER_ID, orderId);
						crit.addEqualTo(PurchaseOrderDataAccess.DIST_ERP_NUM, distErpNum);
						PurchaseOrderDataVector poDV = PurchaseOrderDataAccess.select(pCon, crit);
						if(poDV!=null){
							poMap.put(key, (PurchaseOrderData)poDV.get(0));
						}else{
							PurchaseOrderData poD = PurchaseOrderData.createValue();
							poMap.put(key, poD);
						}
					}

					if(oid.getDistItemSkuNum() != null &&
                                           oid.getDistItemSkuNum().equalsIgnoreCase(distSku) &&
                                           oid.getCustLineNum()==lineNum){
						foundF1=true;

						//check qty change
						if(coid.getTotalQuantityOrdered()==0){
							//cancel item
							oid.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
							actionCd = RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED;
						}else if(oid.getTotalQuantityOrdered()!=coid.getTotalQuantityOrdered()){
							oid.setTotalQuantityOrdered(coid.getTotalQuantityOrdered());
							oid.setDistItemQuantity(coid.getTotalQuantityOrdered());
							actionCd = RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.QUANTITY_CHANGE;
							oiaD.setQuantity(coid.getTotalQuantityOrdered());
						}
						//check price change
						if(oid.getDistItemCost().compareTo(coid.getDistItemCost())!=0 ||
								oid.getCustContractPrice().compareTo(coid.getCustContractPrice())!=0){
							//oid.setDistItemCost(coid.getDistItemCost());
							//actionCd = RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_ACCEPTED_CHANGES_MADE;
							//oiaD.setAmount(coid.getDistItemCost());
							throw new Exception("Price :"+coid.getCustContractPrice().toString()+" is not the same as the order.  Price changes are not allowed. "+
									"Item: "+coid.getDistItemSkuNum()+",Line: "+coid.getCustLineNum());
						}
						//check tax change
						if(oid.getTaxAmount()!=null && coid.getTaxAmount()!=null &&
								oid.getTaxAmount().compareTo(coid.getTaxAmount())!=0){
							oid.setTaxExempt(coid.getTaxExempt());
							oid.setTaxAmount(coid.getTaxAmount());
							oid.setTaxRate(coid.getTaxRate());
							//set the status if it has not been set already.  A quantity change wins
							//over a tax change for example
							if(actionCd.length()>0){
								oiaD.setQuantity(coid.getTotalQuantityOrdered());
								actionCd = RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.TAX_CHANGE;
							}
						}

						if(actionCd.length()>0){
							changeItems.add(oid);

							oiaD.setActionCd(actionCd);
							oiaD.setOrderId(orderId);
							oiaD.setOrderItemId(oid.getOrderItemId());
							oiaD.setActionDate(new java.util.Date());
							oiaD.setAddBy(pUser);
							oiaD.setAffectedLineItem(lineNum);

							changeItemActions.add(oiaD);
						}

						oiDV.remove(oid);
					}
				}
				if(!foundF1){
					addItems.add(itemReq);
				}

			}
			java.util.Date currDate = new java.util.Date();
			if(changeItems.size()>0){
				//update items
				for(int i=0; i<changeItems.size(); i++){
					OrderItemData cItem = (OrderItemData)changeItems.get(i);
					cItem.setModBy(pUser);
					cItem.setModDate(currDate);
					OrderItemDataAccess.update(pCon, cItem);
				}
			}

			//OrderItemActions
			if(changeItemActions.size()>0){
				//order item actions
				for(int j=0; j<changeItemActions.size(); j++){
					OrderItemActionData cItem = (OrderItemActionData)changeItemActions.get(j);
					cItem.setModBy(pUser);
					cItem.setModDate(currDate);
					OrderItemActionDataAccess.insert(pCon, cItem);
				}
			}

			if(oiDV.size()>0){
				//cancel items
				for(int k=0; k<oiDV.size(); k++){
					OrderItemData cItem = (OrderItemData)oiDV.get(k);
					cItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
					cItem.setModBy(pUser);
					cItem.setModDate(currDate);
					OrderItemDataAccess.update(pCon, cItem);

					//add order item action for cancelled
					OrderItemActionData oiaD = OrderItemActionData.createValue();
					oiaD.setOrderId(orderId);
					oiaD.setOrderItemId(cItem.getOrderItemId());
					oiaD.setActionCd(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
					oiaD.setAddBy(pUser);
					oiaD.setAddDate(currDate);
					oiaD.setActionDate(currDate);
					oiaD.setComments("");

					OrderItemActionDataAccess.insert(pCon, oiaD);
				}
			}


			int siteId = orderD.getSiteId();
			int catalogId = 0;
			try{
				Site siteEjb = pFactory.getSiteAPI();
				catalogId = siteEjb.getShoppigCatalogIdForSite(siteId);

			}catch(Exception e){
				pBaton.addError(pCon, OrderPipelineBaton.NO_ACTIVE_CATALOG_FOUND,
                        RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0,
                        null);
			}
			if(addItems.size()>0){
				//add new items
				for(int l=0; l<addItems.size(); l++){
					OrderItemChangeRequestData ocItem = (OrderItemChangeRequestData)addItems.get(l);
					OrderItemData oItem = ocItem.getOrderItemData();
					String distSku = oItem.getDistItemSkuNum();
					String distErp = oItem.getDistErpNum();

					int itemId=0;

					try {
						itemId = ItemSkuMapping.mapToItemId(pCon, distSku,
								RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR, catalogId);
					} catch (Exception e) {
						pBaton.addError(pCon, OrderPipelineBaton.NO_ITEM_FOUND, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oItem.getCustLineNum(), 0, "pipeline.message.noItemFound", distSku, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, RefCodeNames.SKU_TYPE_CD.DISTRIBUTOR, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
					}

					if(catalogId>0 && itemId>0){

						oItem.setItemId(itemId);
						ProductData pd = null;
						CatalogInformation catInfoEjb;
						try {
                            catInfoEjb = pFactory.getCatalogInformationAPI();
                            pd = catInfoEjb.getCatalogClwProduct(catalogId, itemId, 0,siteId, pBaton.getCategToCCView());

						}catch(Exception e){
							e.printStackTrace();
						}
						if(pd==null){
							try {
								catInfoEjb = pFactory.getCatalogInformationAPI();
								pd = catInfoEjb.getProduct(itemId, pBaton.getCategToCCView());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (pd == null) {
							pBaton.addError(pCon, OrderPipelineBaton.ERROR_GETTING_PRODUCT_DATA, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oItem.getCustLineNum(), 0, "pipeline.message.errorGettingProductData", "" + itemId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING, "" + catalogId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
						}

						if (pd != null) {
							String custSku = pd.getActualCustomerSkuNum();
							if (Utility.isSet(custSku)) {
								oItem.setCustItemSkuNum(custSku);
							} else {
								oItem.setCustItemSkuNum(null);
							}

							String prodName = pd.getCustomerProductShortDesc();
							if (!Utility.isSet(prodName)) {
								prodName = pd.getCatalogProductShortDesc();
							}
							oItem.setCustItemShortDesc(prodName);
							oItem.setCustItemUom(pd.getUom());

							String manufSku = pd.getManufacturerSku();
							if (!Utility.isSet(manufSku)) {
								pBaton.addError(pCon, OrderPipelineBaton.NO_MANUFACTURER_SKU, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, oItem.getCustLineNum(), 0, "pipeline.message.noManufSku", "" + itemId, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
							}
							oItem.setManuItemSkuNum(manufSku);
							oItem.setManuItemShortDesc(pd.getManufacturerName());
							oItem.setManuItemMsrp(new BigDecimal(pd.getListPrice()));
							oItem.setManuItemUpcNum(pd.getUpc());
							oItem.setManuPackUpcNum(pd.getPkgUpc());

							oItem.setItemSkuNum(pd.getSkuNum());
							//oItem.setItemShortDesc(pd.getShortDesc());
							//oItem.setItemUom(pd.getUom());
							oItem.setItemPack(pd.getPack());
							oItem.setItemSize(pd.getSize());
							oItem.setCostCenterId(pd.getCostCenterId());
							oItem.setDistErpNum(pd.getCatalogDistributor().getErpNum());
							oItem.setOrderId(orderId);
							oItem.setOrderLineNum(oItem.getCustLineNum());
							oItem.setDistItemShortDesc(pd.getCatalogDistributorName());

							int pDistrId = pd.getCatalogDistributor().getBusEntityId();
							oItem.setDistItemPack(pd.getDistributorPack(pDistrId));

							oItem.setAddBy(pUser);

						}
					}else{
						throw new Exception("Item: "+oItem.getDistItemSkuNum()+",Line: "+oItem.getCustLineNum()+" not found in catalog. (Internal id:"+catalogId+")");
					}
					oItem = OrderItemDataAccess.insert(pCon, oItem);

					String key = oItem.getDistErpNum()+orderId;
					if(!poMap.containsKey(key)){
						crit = new DBCriteria();
						crit.addEqualTo(PurchaseOrderDataAccess.ORDER_ID, orderId);
						crit.addEqualTo(PurchaseOrderDataAccess.DIST_ERP_NUM, oItem.getDistErpNum());
						PurchaseOrderDataVector poDV = PurchaseOrderDataAccess.select(pCon, crit);
						if(poDV!=null){
							poMap.put(key, (PurchaseOrderData)poDV.get(0));
						}else{
							/*PurchaseOrderData poD = PurchaseOrderData.createValue();
							poMap.put(key, poD);*/
						}
					}
				}
			}
			//Update Order
			crit= new DBCriteria();
			crit.addEqualTo(OrderItemDataAccess.ORDER_ID,orderId);
			//remove canceled order items programatically as Oracle will not pickup a null during a "!=" statement
			oiDV = OrderItemDataAccess.select(pCon,crit);
			for(Iterator iter = oiDV.iterator(); iter.hasNext();) {
		        OrderItemData oiD = (OrderItemData) iter.next();
		        if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(oiD.getOrderItemStatusCd())){
		        	iter.remove();
		        }
			}


			if(oiDV==null || oiDV.size()==0){
				//cancel order
				orderD.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.CANCELLED);
				updateOrder(pCon, orderD, pUser);

			}else{
				setOrderTotalPrice(orderD, oiDV);

				updateOrder(pCon, orderD, pUser);

				updatePO(pCon,poMap, orderD,oiDV, pUser);

			}

			pBaton.setUserName(pUser);
			pBaton.setOrderData(orderD);
			pBaton.setOrderItemDataVector(oiDV);

			crit = new DBCriteria();
			crit.addEqualTo(OrderAddressDataAccess.ORDER_ID, orderId);
			OrderAddressDataVector orderAddV = OrderAddressDataAccess.select(pCon, crit);
			if(orderAddV!=null){
				for(int i=0; i<orderAddV.size(); i++){
					OrderAddressData oa = (OrderAddressData)orderAddV.get(i);
					if(oa.getAddressTypeCd().equals(RefCodeNames.ADDRESS_TYPE_CD.BILLING)){
						pBaton.setBillToData(oa);
					}else if(oa.getAddressTypeCd().equals(RefCodeNames.ADDRESS_TYPE_CD.SHIPPING)){
						pBaton.setShipToData(oa);
					}
				}
			}

			crit = new DBCriteria();
			crit.addEqualTo(OrderMetaDataAccess.ORDER_ID, orderId);
			OrderMetaDataVector omDV = OrderMetaDataAccess.select(pCon, crit);
			if(omDV!=null){
				pBaton.setOrderMetaDataVector(omDV);
			}

			pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);

		}catch(Exception e){
			e.printStackTrace();
			throw new PipelineException(e.getMessage());
		}

		return pBaton;
	}

	public void setOrderTotalPrice(OrderData pOrder,
			  OrderItemDataVector pOrderItemsDV) {

		  BigDecimal totalPrice = new BigDecimal(0);
		  for(Iterator iter = pOrderItemsDV.iterator(); iter.hasNext();) {
			  OrderItemData orderItemD = (OrderItemData) iter.next();
			  String itemStatus = orderItemD.getOrderItemStatusCd();
			  if (! ((RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED).equals(itemStatus))) {
				  BigDecimal amount = orderItemD.getCustContractPrice();
				  if (amount != null) {
					  int qty = orderItemD.getTotalQuantityOrdered();
					  BigDecimal price = amount.multiply(new BigDecimal(qty));
					  price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
					  totalPrice = totalPrice.add(price);
				  }
			  }
		  }
		  //totalPrice = totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
		  BigDecimal origTotalPrice = pOrder.getOriginalAmount();
		  if(origTotalPrice==null || !origTotalPrice.equals(totalPrice)) {
			  pOrder.setOriginalAmount(totalPrice);
			  pOrder.setTotalPrice(totalPrice);
		  }
		  BigDecimal totalTaxCost = TaxUtilAvalara.getTotalTaxAmount(pOrderItemsDV);
		  pOrder.setTotalTaxCost(totalTaxCost);

	  }

	private void updateOrder(Connection pConn,OrderData pOrder,String pUser)
	throws PipelineException {
		// update the order info
		try {
			pOrder.setModBy(pUser);
			//java.util.Date currDate = new java.util.Date();
			//Calendar cal = Calendar.getInstance();
			//cal.setTime(currDate);
			//pOrder.setRevisedOrderDate(cal.getTime());
			OrderDataAccess.update(pConn, pOrder);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PipelineException(e.getMessage());
		} finally {
		}
	}

	private void updatePO(Connection pConn,HashMap<String,PurchaseOrderData> pPoMap,OrderData pOrder,
			OrderItemDataVector pOrderItems,String pUser)throws PipelineException{

		try{

			Map itemsByDistMap = new HashMap<String,OrderItemDataVector>();

			for(int i=0; i<pOrderItems.size(); i++){
				OrderItemData oid = (OrderItemData)pOrderItems.get(i);
				String dist = oid.getDistErpNum();
				if(!itemsByDistMap.containsKey(dist)){
					OrderItemDataVector oitems = new OrderItemDataVector();
					oitems.add(oid);
					itemsByDistMap.put(dist,oitems);
				}else{
					OrderItemDataVector oitems = (OrderItemDataVector)itemsByDistMap.get(dist);
					oitems.add(oid);
					itemsByDistMap.put(dist, oitems);
				}
			}

			Iterator it = itemsByDistMap.values().iterator();
			while(it.hasNext()){

				BigDecimal lineItemTotal = new BigDecimal(0);
				BigDecimal lineTaxTotal = new BigDecimal(0);
				String dist="";
				OrderItemDataVector oiDV = (OrderItemDataVector)it.next();
				for(int ii=0; ii<oiDV.size(); ii++){
					OrderItemData od = (OrderItemData)oiDV.get(ii);

					BigDecimal amount = od.getCustContractPrice();
					if (amount != null) {
						int qty = od.getTotalQuantityOrdered();
						BigDecimal price = amount.multiply(new BigDecimal(qty));
						price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
						lineItemTotal = lineItemTotal.add(price);
					}
					
					BigDecimal itemTax = od.getTaxAmount();
		            if (itemTax == null) {
		            	itemTax = new BigDecimal(0);
		            }
					lineTaxTotal = lineTaxTotal.add(itemTax);
					if(dist==null || !(dist.trim().length()>0)){
						dist = od.getDistErpNum();
					}

				}

				String key = dist+pOrder.getOrderId();
				if(pPoMap.containsKey(key)){

					PurchaseOrderData po = (PurchaseOrderData)pPoMap.get(key);
					if(po.getLineItemTotal().compareTo(lineItemTotal)!=0){
						po.setLineItemTotal(lineItemTotal);
						po.setTaxTotal(lineTaxTotal);
						po.setPurchaseOrderTotal(lineTaxTotal.add(lineItemTotal));
						po.setModBy(pUser);
						po.setModDate(new java.util.Date());
						if(po.getPurchaseOrderId()>0){
							PurchaseOrderDataAccess.update(pConn, po);

							//update order items with PO id
							int poid = po.getPurchaseOrderId();
							String outboundPO = po.getOutboundPoNum();
							String erpPo = po.getErpPoNum();
							for(int jj=0; jj<oiDV.size(); jj++){
								OrderItemData item = (OrderItemData)oiDV.get(jj);
								item.setPurchaseOrderId(poid);
								item.setOutboundPoNum(outboundPO);
								item.setErpPoNum(erpPo);
								OrderItemDataAccess.update(pConn, item);
							}

						}else{
							/*po.setOrderId(pOrder.getOrderId());
							po.setDistErpNum(dist);
							po.setAddBy(pUser);
							po.setAddDate(new java.util.Date());
							//po.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT);
							po =PurchaseOrderDataAccess.insert(pConn, po);

							pPoMap.put(key, po);*/
						}
					}
				}
			}


		}catch (Exception e) {
			e.printStackTrace();
			throw new PipelineException(e.getMessage());
		} finally {
		}
	}

	private Comparator ORDER_ITEM_CUST_LINE_NUM_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {

            OrderItemData t1 = (OrderItemData)o1;
            OrderItemData t2 = (OrderItemData)o2;

            int t_line1 = t1.getCustLineNum();
            int t_line2 = t2.getCustLineNum();

            if(t_line1 > t_line2){
            	return 1;
            }else if(t_line1 < t_line2){
            	return -1;
            }else{
            	return 0;
            }
        }
    };

    private Comparator CHANGE_ITEM_CUST_LINE_NUM_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {

        	OrderItemChangeRequestData t1 = (OrderItemChangeRequestData)o1;
        	OrderItemChangeRequestData t2 = (OrderItemChangeRequestData)o2;

            int t_line1 = t1.getOrderItemData().getCustLineNum();
            int t_line2 = t2.getOrderItemData().getCustLineNum();

            if(t_line1 > t_line2){
            	return 1;
            }else if(t_line1 < t_line2){
            	return -1;
            }else{
            	return 0;
            }
        }
    };

}
