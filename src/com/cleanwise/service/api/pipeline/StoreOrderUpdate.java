package com.cleanwise.service.api.pipeline;

import java.math.*;
import java.lang.Math;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.rmi.RemoteException;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import org.apache.log4j.Category;

/**
 *
 */
public class StoreOrderUpdate  extends StoreOrderPipeline {
	
	private static final Category log = Category.getInstance(IntegrationServicesBean.class);

    private final static String className = "IntegrationServicesBean";

    /**
     *Cancel order items
     */
    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException {
        pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
        StoreOrderPipelineBaton sBaton =(StoreOrderPipelineBaton)pBaton;
        try{
          OrderData order = sBaton.getStoreOrderChangeRequestData().getOrderData();
          if (sBaton.getStoreOrderChangeRequestData().getNewOrderDate() != null) {
            order.setRevisedOrderDate(sBaton.getStoreOrderChangeRequestData().getNewOrderDate());
            order.setRevisedOrderTime(null);
          }

          OrderMetaDataVector orderMeta = sBaton.getStoreOrderChangeRequestData().getOrderMeta();
          OrderItemDescDataVector orderItemDescList = sBaton.getStoreOrderChangeRequestData().getOrderItemsDesc();
          IdVector selOrderItems = sBaton.getStoreOrderChangeRequestData().getDelOrderItems();
          OrderItemDataVector orderItems = sBaton.getStoreOrderChangeRequestData().getOrderItems();
          String user = sBaton.getStoreOrderChangeRequestData().getUserName();
          BigDecimal freight = sBaton.getStoreOrderChangeRequestData().getTotalFreightCost();
          BigDecimal misc = sBaton.getStoreOrderChangeRequestData().getTotalMiscCost();
          BigDecimal smallOrderFee = sBaton.getStoreOrderChangeRequestData().getSmallOrderFeeAmt();
          BigDecimal fuelSurcharge = sBaton.getStoreOrderChangeRequestData().getFuelSurchargeAmt();
          BigDecimal discount = sBaton.getStoreOrderChangeRequestData().getDiscountAmt();
          // Step #1. update items
          HashMap poDM = updateItems(pCon, pFactory, order, orderItemDescList, user, pBaton.getCategToCCView());
          // Step #2. Cancel selected items
          cancelItems(pCon, order, selOrderItems, user);
          // Step #3. Calculate total price
          DBCriteria dbc = new DBCriteria();
          dbc.addEqualTo(OrderDataAccess.ORDER_ID,order.getOrderId());
          orderItems = OrderItemDataAccess.select(pCon,dbc);
          setOrderTotalPrice(order, orderItems);
          // Step #4. Set charges
          setHandlingCharges(order, freight, misc);
          // Step #5. Update order
          updateOrder(pCon, order, user);
          // Step #6. Update small order fee &  fuel surcharge
          setMetaCharges(order, orderMeta, smallOrderFee, fuelSurcharge, discount, user);
          // Step #7. Update order
          updatePoStatus(pCon, poDM, user);
          // Step #8. Update PO detail
          //BigDecimal orderTotal = calculateTotalAmount(freight, misc, smallOrderFee, fuelSurcharge, order );
          //updatePoDetails(pCon, order, poDM, orderTotal, user);
          
          //Step #9. Clear Workflow Queue, if the Order is in "End State"
          if (order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.REJECTED)
        	  || order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED)
              || order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.ERP_RELEASED)
              || order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.INVOICED)
              || order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.ERP_CANCELLED)
              || order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.CANCELLED))
          {
        	  log.info(".process() method => calling removeWorkflowQueue() method");
        	  removeWorkflowQueue(pFactory, order.getOrderId()); 
          }
          
          //Step #10. Clear Workflow Queue, if the Order Status was changed from "Pending Order Review" to "Ordered" status  
          if (order.getOrderStatusCd().equals(RefCodeNames.ORDER_STATUS_CD.ORDERED)) {
        	  log.info(".process() method => calling processWorkflowQueue() method");
        	  String shortDesc = RefCodeNames.WORKFLOW_RULE_ACTION.STOP_ORDER;
        	  processWorkflowQueue(pFactory, order.getOrderId(), shortDesc); 
          }

          boolean rebillOrder = sBaton.getStoreOrderChangeRequestData().getRebillOrder();
          updateRebillOrderProperty(pCon, pFactory, order, rebillOrder, user);
          pBaton.setUserName(user);
          pBaton.setOrderItemDataVector(orderItems);
        } catch(Exception e){
		     e.printStackTrace();
          if (e instanceof PipelineMessageException) {
            throw (PipelineException)e;
          }
          throw new PipelineException(e.getMessage());
        }
        return sBaton;
    }

  private void updateOrder(Connection pConn,
                           OrderData pOrder,
                           String pUser)
      throws PipelineException {
   // update the order info
   try {
     pOrder.setModBy(pUser);
     OrderDataAccess.update(pConn, pOrder);
   } catch (Exception e) {
     throw new PipelineException(e.getMessage());
   } finally {
   }
 }


 private void updatePoStatus(Connection pConn,
                          HashMap poMap,
                          String pUser)
     throws PipelineException {
  try {
    Iterator iter = poMap.values().iterator();
    while (iter.hasNext()) {
      PurchaseOrderData poD = (PurchaseOrderData)iter.next();
      PurchaseOrderData poM = PurchaseOrderDataAccess.select(pConn, poD.getPurchaseOrderId());
      if (poD.getPurchaseOrderStatusCd() != null &&
          !poD.getPurchaseOrderStatusCd().equals(poM.getPurchaseOrderStatusCd())) {
        poM.setModBy(pUser);
        poM.setPurchaseOrderStatusCd(poD.getPurchaseOrderStatusCd());
        PurchaseOrderDataAccess.update(pConn, poM);
      }
    }
  } catch (Exception e) {
    throw new PipelineException(e.getMessage());
  } finally {
  }
}

    private void updatePoDetails(Connection pConn,
                              OrderData pOrder,
                              HashMap poMap,
                              BigDecimal pOrderTotal,
                              String pUser)
         throws PipelineException {
      try {
        Iterator iter = poMap.values().iterator();
        while (iter.hasNext()) {
          PurchaseOrderData poD = (PurchaseOrderData)iter.next();
          PurchaseOrderData poM = PurchaseOrderDataAccess.select(pConn, poD.getPurchaseOrderId());
          if (poM != null) {
          // line item total
              poM.setLineItemTotal(pOrder.getTotalPrice());
              // purchase_order_total
              poM.setPurchaseOrderTotal(pOrderTotal);
              // erp system cd
              poM.setErpSystemCd(pOrder.getErpSystemCd());
              // tax total
              poM.setTaxTotal(pOrder.getTotalTaxCost());
              poM.setModBy(pUser);
              PurchaseOrderDataAccess.update(pConn, poM);
          }
        }
      } catch (Exception e) {
        throw new PipelineException(e.getMessage());
      } finally {
      }
    }

    private void updateRebillOrderProperty(Connection pConn,
                    APIAccess pFactory,
                    OrderData pOrder,
                    boolean pRebillOrderValue, String pUser)  throws PipelineException {
      try {
            Order orderEjb = pFactory.getOrderAPI();
            OrderPropertyDataVector rebillOrderProps =
                orderEjb.getOrderPropertyCollection(pOrder.getOrderId(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER);
            OrderPropertyData rebillOrderProp = null;
            if (rebillOrderProps.size() == 0 ) {
                if (!pRebillOrderValue) {
                    return;
                }
                rebillOrderProp = OrderPropertyData.createValue();
                rebillOrderProp.setAddBy(pUser);
                rebillOrderProp.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER);
                rebillOrderProp.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                rebillOrderProp.setOrderId(pOrder.getOrderId());
                rebillOrderProp.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER);
            } else {
                rebillOrderProp = (OrderPropertyData)rebillOrderProps.get(0);
            }
            rebillOrderProp.setOrderStatusCd(pOrder.getOrderStatusCd());
            rebillOrderProp.setValue((new Boolean(pRebillOrderValue).toString()));
            rebillOrderProp.setModBy(pUser);

            if(rebillOrderProp.getOrderPropertyId() > 0){
                OrderPropertyDataAccess.update(pConn, rebillOrderProp);
            }else{
                rebillOrderProp = OrderPropertyDataAccess.insert(pConn, rebillOrderProp);
            }
      } catch (Exception e) {
        throw new PipelineException(e.getMessage());
      } finally {
      }
    }

   private BigDecimal calculateTotalAmount(BigDecimal freight,  BigDecimal misc, BigDecimal smallOrderFee,
                                           BigDecimal fuelSurcharge, OrderData pOrder) {
        BigDecimal result = new BigDecimal(0);
        BigDecimal recalct = new BigDecimal(0);

        if ( freight != null ) {
            recalct = recalct.add(freight);
        }
        if ( misc != null ) {
            recalct = recalct.add(misc);
        }
        if ( fuelSurcharge != null ) {
            recalct = recalct.add(fuelSurcharge);
        }
        if ( pOrder.getTotalRushCharge() != null ) {
            recalct = recalct.add(pOrder.getTotalRushCharge());
        }
        if (smallOrderFee != null ) {
            recalct = recalct.add(smallOrderFee);
        }
        result = recalct.add(pOrder.getTotalTaxCost()).add(pOrder.getTotalPrice());
        //result=result.setScale(2,BigDecimal.ROUND_HALF_UP);

        return result;
    }

  private void cancelItems(Connection pConn,
                           OrderData pOrder,
                           IdVector pOrderItemIdV,
                           String pUser)
     throws PipelineException {
      if (null==pOrderItemIdV || pOrderItemIdV.size()==0) {
        return;
      }
      try {
          DBCriteria crit = new DBCriteria();
          crit.addOneOf(OrderItemDataAccess.ORDER_ITEM_ID, pOrderItemIdV);
          crit.addNotEqualTo(OrderItemDataAccess.ORDER_ITEM_STATUS_CD, RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED);
          DBCriteria critOr = new DBCriteria();
          critOr.addOneOf(OrderItemDataAccess.ORDER_ITEM_ID, pOrderItemIdV);
          critOr.addIsNull(OrderItemDataAccess.ORDER_ITEM_STATUS_CD);
          crit.addOrCriteria(critOr);
          OrderItemDataVector oiDV = OrderItemDataAccess.select(pConn,crit);
          OrderDAO.cancelAndUpdateOrderItems(pConn, oiDV, pOrder, pUser);
      } catch (Exception e) {
        throw new PipelineException(e.getMessage());

      } finally {
      }

      return;
  }

  /**
   *calculate the total price
   *@param pOrder order
   *@param pOrderItemsDV order items
   *@param pSelOrderItemsDV selected order items
   */
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
				  //price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
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

  private OrderItemData updateAddOrderItemWorker(Connection pCon, OrderItemData pOrderItemData)
      throws Exception{
    if (pOrderItemData.isDirty())
    {
      if (pOrderItemData.getOrderItemId() == 0)
      {
        pOrderItemData = OrderItemDataAccess.insert(pCon, pOrderItemData);
      }
      else
      {
        OrderDAO.updateOrderItem(pCon,pOrderItemData);
      }
    }
    return pOrderItemData;
  }


  private HashMap updateItems(Connection pConn,
                                   APIAccess pFactory,
                                   OrderData pOrder,
                                   OrderItemDescDataVector orderItemDescList,
                                   String pUser,
                                   AccCategoryToCostCenterView pCategToCostCenterView )
     throws Exception {
      HashMap poDM = new HashMap();
        Order orderEjb = pFactory.getOrderAPI();
        Distributor distEjb = pFactory.getDistributorAPI();
        Site siteEjb = pFactory.getSiteAPI();
        CatalogInformation catalogInfoEjb = pFactory.getCatalogInformationAPI();
        Contract contractEjb = pFactory.getContractAPI();
        ItemInformation itemInfEjb=pFactory.getItemInformationAPI();
        Asset assetEjb=pFactory.getAssetAPI();

        HashMap itemDist = new HashMap();
        int orderLineNumMax = 0;
        IdVector itemIds=new IdVector();
		boolean poExistsFl = false;
        if (null != orderItemDescList && 0 < orderItemDescList.size())	    {
          for (int i = 0; i < orderItemDescList.size(); i++) {
            OrderItemDescData oidD = (OrderItemDescData)orderItemDescList.get(i);
            if (oidD.getOrderItem().getItemId() > 0) {
              OrderItemData oid = (OrderItemData)itemDist.get(oidD.getOrderItem().getDistErpNum());
              itemIds.add(new Integer(oidD.getOrderItem().getItemId()));
              if (oid == null) {
                oid = OrderItemData.createValue();
                itemDist.put(oidD.getOrderItem().getDistErpNum(), oid);
              }
              oid.setErpPoLineNum(Math.max(oidD.getOrderItem().getErpPoLineNum(), oid.getErpPoLineNum()));
              oid.setErpOrderLineNum(Math.max(oidD.getOrderItem().getErpOrderLineNum(), oid.getErpOrderLineNum()));

              if (oidD.getOrderItem().getErpPoNum() != null && !"".equals(oidD.getOrderItem().getErpPoNum())) {
                oid.setErpPoNum(oidD.getOrderItem().getErpPoNum());
				poExistsFl = true;
              }
              if (oidD.getOrderItem().getErpPoDate() != null) {
                oid.setErpPoDate(oidD.getOrderItem().getErpPoDate());
              }
              oid.setErpPoRefLineNum(Math.max(oidD.getOrderItem().getErpPoRefLineNum(), oid.getErpPoRefLineNum()));
              if (oidD.getOrderItem().getErpPoRefNum() != null && !"".equals(oidD.getOrderItem().getErpPoRefNum())) {
                oid.setErpPoRefNum(oidD.getOrderItem().getErpPoRefNum());
              }
              if (oidD.getOrderItem().getErpPoRefDate() != null) {
                oid.setErpPoRefDate(oidD.getOrderItem().getErpPoRefDate());
              }
              if (oidD.getOrderItem().getPurchaseOrderId() != 0) {
                oid.setPurchaseOrderId(oidD.getOrderItem().getPurchaseOrderId());
                PurchaseOrderData poD = (PurchaseOrderData) poDM.get("" + oidD.getOrderItem().getPurchaseOrderId());
                if (poD == null) {
                  poD = PurchaseOrderData.createValue();
                  poDM.put("" + oidD.getOrderItem().getPurchaseOrderId(), poD);
                }
                String poStatus;
                if (oidD.getPoItemStatus() != null && !"".equals(oidD.getPoItemStatus())) {
                  poStatus = oidD.getPoItemStatus();
                } else {
                  poStatus = oidD.getPurchaseOrderData().getPurchaseOrderStatusCd();
                }
                if (poD.getPurchaseOrderStatusCd() == null || "".equals(poD.getPurchaseOrderStatusCd()) || poD.getPurchaseOrderStatusCd().equals(poStatus)) {
                  poD.setPurchaseOrderId(oidD.getOrderItem().getPurchaseOrderId());
                  poD.setPurchaseOrderStatusCd(poStatus);
                } else {
                  throw new PipelineMessageException("error.simpleGenericError",
                                                     "Incompatible purchase order status[" +
                                                     poStatus + " and " +
                                                     poD.getPurchaseOrderStatusCd() + "].");
                }
              }
            }
            orderLineNumMax = Math.max(orderLineNumMax, oidD.getOrderItem().getOrderLineNum());
          }
        }

        boolean consolidatedOrderFl =
            (RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(pOrder.getOrderTypeCd()))?true:false;

        ItemDataVector itemDV=itemInfEjb.getItemCollection(itemIds);
        boolean isServiceOrder = PipelineUtil.isSimpleServiceOrder(itemDV);
        int accountId = pOrder.getAccountId();
        int contractId = pOrder.getContractId();
        int catalogId = 0;

        // see if we can get the contract data according to the contract id
        ContractData contractD = null;
        try	    {
          contractD = contractEjb.getContractByAccount(contractId, accountId);
        } catch (Exception e) {
          throw new PipelineMessageException("error.simpleGenericError",
                                             "Can't find contract[contractId="
                                             + contractId +  "]. ");
        }
        if (null != contractD) {
          catalogId = contractD.getCatalogId();
        } else {
          throw new PipelineMessageException("error.simpleGenericError",
                                             "Can't get contract for contractId=" +
                                             contractId +  "] ");
        }

        OrderItemDataVector changedOrderItemList = new OrderItemDataVector();
        boolean updateItemsFlag = false;
        OrderItemDataVector orderItemDV = new OrderItemDataVector();
        ReplacedOrderItemViewVector modReplOrderItems = new ReplacedOrderItemViewVector();

        if (null != orderItemDescList && 0 < orderItemDescList.size())	    {
          // get the all contractItems information and the all contract distributors
          BusEntityDataVector contractDistV = new BusEntityDataVector();
          try {
            contractDistV = contractEjb.getContractDistCollection(pOrder.getContractId());
          } catch (Exception e) {
            contractDistV = new BusEntityDataVector();
          }

          // Begin to change each ordeItemD
          for (int i = 0; i < orderItemDescList.size(); i++)	{
            OrderItemDescData orderItemDescD = (OrderItemDescData)orderItemDescList.get(i);
            if (null == orderItemDescD) {
              continue;
            }
            OrderItemData orderItemD = orderItemDescD.getOrderItem();

            int origItemId = orderItemD.getItemId();
            boolean orderItemChangedFlag = false;

            int origDistId = orderItemDescD.getDistId();
            String itemIdS = orderItemDescD.getItemIdS();
            int itemId = orderItemDescD.getOrderItem().getItemId();
            String itemSkuNumS = orderItemDescD.getItemSkuNumS();
            boolean itemInputedFlag = false;
            boolean itemAddedFlag=false;
            if ( Utility.isSet(itemIdS) && itemIdS.trim().length() > 0 ){
              itemInputedFlag = true;
                if (orderItemDescD.getOrderItem().getItemId() == 0)
                {
                    itemAddedFlag = true;
                }
              // see if we can parse the inputed item id
              try {
                itemId = Integer.parseInt(itemIdS);
              }
              catch (Exception e) {
                throw new PipelineMessageException("error.simpleGenericError",
                                                  "Invalid Item Id[" + i +
                                                  "]="+itemIdS);
              }

              // set the distributor info according to the contract
              // because the new item maybe has different distributor
              if (isServiceOrder) {
                    origDistId = contractEjb.getServiceDistributorId(contractId, itemId);
              } else {
                    origDistId = contractEjb.getItemDistributorId(contractId, itemId);
              }
              if (0 != origDistId) {
                orderItemDescD.setDistId(origDistId);
                orderItemDescD.setDistName("");

                if (null != contractDistV && 0 < contractDistV.size()) {
                  boolean findContractDistFlag = true;
                  for (int k = 0; k < contractDistV.size(); k++) {
                    BusEntityData distD = (BusEntityData) contractDistV.get(k);
                    if (distD.getBusEntityId() == origDistId) {
                      findContractDistFlag = true;
                      orderItemD.setDistErpNum(distD.getErpNum());
                      orderItemDescD.setDistName(distD.getShortDesc());
                      break;
                    }
                    if (false == findContractDistFlag) {
                      orderItemD.setDistErpNum(null);
                    }
                  }
                }
                else {
                  orderItemD.setDistErpNum(null);
                }
                } else {
                  orderItemDescD.setDistId(0);
                  orderItemDescD.setDistName("");
                  orderItemD.setDistErpNum(null);
                }

                String productBundleValue = ShoppingDAO.getProductBundleValue(pConn, pOrder.getSiteId());
                if (Utility.isSet(productBundleValue)) {

                    orderItemD.setDistItemCost(null);
                    orderItemD.setDistUomConvCost(null);
                    orderItemD.setCustContractPrice(null);

                    try {
                        BigDecimal price = ShoppingDAO.getContractItemPrice(pConn,
                                pOrder.getStoreId(),
                                pOrder.getSiteId(),
                                contractId,
                                catalogId,
                                itemId);
                        orderItemD.setDistItemCost(price);
                        orderItemD.setDistUomConvCost(price);
                        orderItemD.setCustContractPrice(price);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    // fetch the distItemCost and custContractPrice
                    ContractItemDataVector contractItemV =
                            contractEjb.getContractItemCollectionByItem(contractId, itemId);
                    if (null != contractItemV && 0 < contractItemV.size()) {
                        ContractItemData contractItemD =
                                (ContractItemData) contractItemV.get(0);
                        if (null != contractItemD) {
                            orderItemD.setDistItemCost(contractItemD.getDistCost());
                            orderItemD.setDistUomConvCost(contractItemD.getDistCost());
                            orderItemD.setCustContractPrice(contractItemD.getAmount());
                        } else {
                            orderItemD.setDistItemCost(null);
                            orderItemD.setDistItemCost(null);
                            orderItemD.setCustContractPrice(null);
                        }
                    }
                }
            }
              String assetIdS = orderItemDescD.getAssetIdS();
              boolean assetInputedFlag = false;
              int assetId = 0;
              if (isServiceOrder) {
                  if (orderItemDescD.getAssetInfo() != null
                      && orderItemDescD.getAssetInfo().getAssetId() > 0) {

                  assetId = orderItemDescD.getAssetInfo().getAssetId();
                  }
                  if (Utility.isSet(assetIdS) && assetIdS.trim().length() > 0) {
                      assetInputedFlag = true;
                      // see if we can parse the inputed item id
                      try {
                          assetId = Integer.parseInt(assetIdS);
                      }
                      catch (Exception e) {
                          throw new PipelineMessageException("error.simpleGenericError",
                                  "Invalid Asset Id[" + i +
                                          "]=" + assetIdS);
                      }
                  }
              }

              // determine if we want to fetch the productD
              ProductData productD = null;
              boolean distInputedFlag = false;
              if (null != orderItemDescD.getDistIdS() && !"".equals(orderItemDescD.getDistIdS())) {
                distInputedFlag = true;
              }

              if (true == itemInputedFlag || true == distInputedFlag || assetInputedFlag) {
                // see if we can get the item data according to the item id
                  if (null == itemSkuNumS || "".equals(itemSkuNumS)){
                      itemSkuNumS = String.valueOf(orderItemD.getItemSkuNum());
                  }
                  if (isServiceOrder) {
                      if (assetId> 0) {
                          orderItemChangedFlag= setServiceData(catalogInfoEjb, assetEjb, itemInfEjb,
                                                               orderItemDescD, orderItemD, itemId, assetId,
                                                               catalogId, origDistId,itemInputedFlag, pUser);

                      }
                  }   else    {

                try {
                  productD = catalogInfoEjb.getCatalogClwProduct(catalogId, itemId,0,pOrder.getSiteId(), pCategToCostCenterView);
                } catch (Exception e) {
                  throw new PipelineMessageException("error.badItem",
                                                     "cwSku " + itemSkuNumS +
                                                     " [itemId=" +
                                                     itemId +
                                                     ", catalogId=" +
                                                     catalogId +"] 1");
                }
                if (null == productD){
                  throw new PipelineMessageException("error.badItem",
                                                      "cwSku " + itemSkuNumS +
                                                      " [itemId=" +
                                                      itemId +
                                                      ", catalogId=" +
                                                      catalogId +"] 2");
                }

                if (null == orderItemD) {
                  orderItemD = OrderItemData.createValue();
                }

                if (true == itemInputedFlag) {
                  orderItemChangedFlag = true;
                  orderItemD.setItemId(itemId);
                }

                if (true == itemInputedFlag) {
                  orderItemD.setItemId(productD.getProductId());
                  orderItemD.setItemSkuNum(productD.getSkuNum());
                  orderItemD.setItemShortDesc(productD.getShortDesc());
                  orderItemD.setItemUom(productD.getUom());
                  orderItemD.setItemPack(productD.getPack());
                  orderItemD.setItemSize(productD.getSize());
                  orderItemD.setItemCost(null);
                  orderItemD.setCostCenterId(productD.getCostCenterId());
                  orderItemD.setCustItemSkuNum(productD.getCustomerSkuNum());
                  orderItemD.setCustItemShortDesc(productD.getCatalogProductShortDesc());
                  orderItemD.setCustItemUom(productD.getUom());
                  orderItemD.setCustItemPack(productD.getPack());
                  orderItemD.setManuItemSkuNum(productD.getManufacturerSku());
                  orderItemD.setManuItemMsrp(new BigDecimal(productD.getListPrice()));
                  orderItemD.setManuItemUpcNum(productD.getUpc());
                  orderItemD.setManuPackUpcNum(productD.getPkgUpc());
                  orderItemD.setManuItemShortDesc(productD.getManufacturerName());
                  orderItemD.setModBy(pUser);

                  if (0 != origDistId) {
                    orderItemD.setDistItemShortDesc(productD.getCatalogDistributorName());
                    orderItemD.setDistItemSkuNum(productD.getDistributorSku(origDistId));
                    orderItemD.setDistItemUom(productD.getDistributorUom(origDistId));
                    orderItemD.setDistItemPack(productD.getDistributorPack(origDistId));
                  } else {
                    orderItemD.setDistItemSkuNum(null);
                    orderItemD.setDistItemUom(null);
                    orderItemD.setDistItemPack(null);
                  }
                }
              }
              }


              String distIdS = orderItemDescD.getDistIdS();
              String distName = orderItemDescD.getNewDistName();
              orderItemDescD.setDistIdS("");
              orderItemDescD.setNewDistName("");

              if (null != distIdS && !"".equals(distIdS)) {
                // see if we can parse the inputed dist id
                int distId = 0;
                try {
                  distId = Integer.parseInt(distIdS);
                }
                catch (Exception e) {
                  throw new PipelineMessageException("error.badDist",
                                                     "[distName=" + distName +
                                                     "]" + "Dist Id[" + i + "]");
                }

                // see if we can get the dist data according to the dist id
                DistributorData distD = null;

                try {
                  distD = distEjb.getDistributor(distId);
                }
                catch (Exception e) {
                  throw new PipelineMessageException("error.badDist",
                                                     "[distName=" + distName +
                                                     "]");
                }

                if (null != distD) {
                  if (null == orderItemD || distD.getBusEntity().getBusEntityId() != origDistId) {
                    orderItemChangedFlag = true;
                    if (null == orderItemD) {
                      orderItemD = OrderItemData.createValue();
                      orderItemD.setOrderId(pOrder.getOrderId());
                    }

                    orderItemD.setDistErpNum(distD.getBusEntity().getErpNum());
                    orderItemD.setDistItemShortDesc(distD.getBusEntity().getShortDesc());
                    orderItemDescD.setDistId(distD.getBusEntity().getBusEntityId());
                    orderItemDescD.setDistName(distD.getBusEntity().getShortDesc());

                    IdVector itemV = new IdVector();
                    itemV.add(new Integer(itemId));
                    DistItemViewVector diVwV =
                        distEjb.getDistItems(distD.getBusEntity().getBusEntityId(), itemV);
                    if (diVwV.size() > 0) {
                      DistItemView diVw = (DistItemView) diVwV.get(0);
                      orderItemD.setDistItemSkuNum(diVw.getDistItemSku());
                      orderItemD.setDistItemUom(diVw.getDistItemUom());
                      orderItemD.setDistItemPack(diVw.getDistItemPack());
                    }
                  }
                }
                else {
                  throw new PipelineMessageException("error.badDist",
                                                     "[distName=" + distName +
                                                     "]");
                }
              } // end if ! "".equals(distIdS)
              // po
              if (true == itemInputedFlag || true == orderItemChangedFlag) {
                  ////////////////////////////////////////////////////////////////////////////////
                  OrderItemData saveItem = (OrderItemData) itemDist.get(orderItemD.getDistErpNum());
                  if (saveItem != null) {
                      orderItemD.setPurchaseOrderId(saveItem.getPurchaseOrderId());
                      orderItemD.setErpPoNum(saveItem.getErpPoNum());
                      orderItemD.setErpPoDate(saveItem.getErpPoDate());
                      orderItemD.setErpPoRefNum(saveItem.getErpPoRefNum());
                      orderItemD.setErpPoRefDate(saveItem.getErpPoRefDate());

                      if (itemAddedFlag) {
                          if (orderItemD.getPurchaseOrderId() > 0) {

                              saveItem.setErpPoLineNum(saveItem.getErpPoLineNum() + 1);
                              orderItemD.setErpPoLineNum(saveItem.getErpPoLineNum());

                          }
                          orderItemD.setErpPoRefLineNum(0);
                          if (saveItem.getErpOrderLineNum() > 0)
                              orderItemD.setErpOrderLineNum(orderLineNumMax++);
                      }
                  } else{
					if(poExistsFl) {
                      throw new PipelineMessageException("error.badDist",
                              "[distName=" +
                                      orderItemDescD.getDistName() +
                                      "]" + "Dist Id[" + i + "]");
					}
                  }
                  ////////////////////////////////////////////////////////////////////////////////

              }

              if (orderItemDescD.getReSale() &&
                  RefCodeNames.ITEM_SALE_TYPE_CD.END_USE.equals(orderItemD.getSaleTypeCd())) {
                orderItemChangedFlag = true;
                orderItemD.setSaleTypeCd(RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE);
              }
              else if (!orderItemDescD.getReSale() &&
                       RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(orderItemD.
                  getSaleTypeCd())) {
                orderItemChangedFlag = true;
                orderItemD.setSaleTypeCd(RefCodeNames.ITEM_SALE_TYPE_CD.END_USE);
              }


              BigDecimal taxRate = orderItemD.getTaxRate();
              taxRate=taxRate==null?new BigDecimal(0):taxRate;

              if (orderItemDescD.getTaxExempt()!=Utility.isTrue(orderItemD.getTaxExempt()))
               {
                   orderItemD.setTaxExempt(orderItemDescD.getTaxExempt()?
                             RefCodeNames.ORDER_ITEM_TAX_EXEMPT_CD.TRUE :
                             RefCodeNames.ORDER_ITEM_TAX_EXEMPT_CD.FALSE);
                   orderItemChangedFlag = true;
               }

              // set the item status
              if (!"".equals(orderItemDescD.getItemStatus())) {
                if ("Ordered".equals(orderItemDescD.getItemStatus())) {
                  orderItemD.setOrderItemStatusCd(null);
                }
                else {
                  orderItemD.setOrderItemStatusCd(orderItemDescD.getItemStatus());
                }
                orderItemChangedFlag = true;
              }

        // set the po item status
//        if (!"".equals(orderItemDescD.getPoItemStatus())) {
//          orderItemD.setOrderItemStatusCd(orderItemDescD.getItemStatus());
//          orderItemChangedFlag = true;
//        }

      // set the item price
      String itemPriceS = orderItemDescD.getItemPriceS().trim();

      if (null != itemPriceS && !"".equals(itemPriceS)) {
        // check that it is a valid amount format
        BigDecimal itemPrice = null;

        try                        {
          itemPrice = CurrencyFormat.parse(itemPriceS);
          //itemPrice = itemPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
        }    catch (Exception pe)             {
          throw new PipelineMessageException("error.invalidNumberAmount",
                                                    "Customer Cost[" + i +
                                                    "]");
        }

        if (null != itemPrice)           {
          if (null != orderItemD.getCustContractPrice())            {
            if (0.001 < java.lang.Math.abs(orderItemD.getDistItemCost().subtract
                                           (itemPrice).doubleValue())) {
              orderItemD.setCustContractPrice(itemPrice);
              orderItemD.setDistItemCost(itemPrice);
              orderItemD.setDistUomConvCost(itemPrice);
              orderItemChangedFlag = true;
            }
          }
          else {
            orderItemD.setCustContractPrice(itemPrice);
            orderItemD.setDistItemCost(itemPrice);
            orderItemD.setDistUomConvCost(itemPrice);
            orderItemChangedFlag = true;
          }
        }
        else                    {
          throw new PipelineMessageException("error.invalidNumberAmount",
                                             "Customer Cost [" + i +
                                             "]");
        }
      } // end of dealing inputed cost

      // set the dist cost for this item
      String cwCostS = orderItemDescD.getCwCostS().trim();

      if (null != cwCostS && !"".equals(cwCostS)) {
        // check that it is a valid amount format
        BigDecimal distItemCost = null;

        try                        {
          distItemCost = CurrencyFormat.parse(cwCostS);
          //distItemCost = distItemCost.setScale(2,BigDecimal.ROUND_HALF_UP);
        }    catch (Exception pe)             {
          throw new PipelineMessageException("error.invalidNumberAmount",
                                                    "Dist Cost[" + i +
                                                    "]");
        }

        if (null != distItemCost)           {
          if (null != orderItemD.getDistItemCost())            {
            if (0.001 < java.lang.Math.abs(orderItemD.getDistItemCost().subtract
                                           (distItemCost).doubleValue())) {
              orderItemD.setDistItemCost(distItemCost);
              orderItemD.setDistUomConvCost(distItemCost);
              orderItemChangedFlag = true;
            }
          }
          else {
            orderItemD.setDistItemCost(distItemCost);
            orderItemD.setDistUomConvCost(distItemCost);
            orderItemChangedFlag = true;
          }
        }
        else                    {
          throw new PipelineMessageException("error.invalidNumberAmount",
                                             "Dist Cost [" + i +
                                             "]");
        }
      } // end of dealing inputed cost

      // set the itemQuantity for this item
      if(!consolidatedOrderFl) {
        String itemQuantityS = orderItemDescD.getItemQuantityS().trim();
        if (null != itemQuantityS && !"".equals(itemQuantityS))    {
          // check that it is a valid amount format
          int itemQuantity = 0;
          try                          {
            itemQuantity = Integer.parseInt(itemQuantityS);
          } catch (Exception pe) {
            throw new PipelineMessageException("error.invalidNumberAmount",
                                               "Item Quantity[" + i +
                                               "]");
          }

          if (orderItemD.getTotalQuantityOrdered() != itemQuantity){
            orderItemD.setTotalQuantityOrdered(itemQuantity);
            orderItemD.setDistItemQuantity(itemQuantity);
            orderItemChangedFlag = true;
          }
        } // end of dealing inputed item quantity
      } else {
        //Consolidated order
        int newQty = 0;
        try {
//          ReplacedOrderViewVector roVwV = detailForm.getOrderStatusDetail().getReplacedOrders();
//          newQty = checkReplacedItemQtyChange(modReplOrderItems, origItemId, orderItemD, roVwV);
        } catch(Exception exc) {
          String errorMess = exc.getMessage();
          throw new PipelineMessageException("error.simpleGenericError", errorMess);
        }
        if(newQty != orderItemD.getTotalQuantityOrdered()) {
          orderItemD.setTotalQuantityOrdered(newQty);
          orderItemD.setDistItemQuantity(newQty);
          orderItemChangedFlag = true;
        }
      }

      if (true == orderItemChangedFlag) {
        orderItemD.setModBy(pUser);
        orderItemD.setTaxAmount(null);
        orderItemD.setTaxRate(null);
        changedOrderItemList.add(orderItemD);
      }
      orderItemDV.add(orderItemD);

    } //end of i-loop for orderItemDescList

    if (null != changedOrderItemList && 0 < changedOrderItemList.size())	{
      updateItemsFlag =true;
    }
  } // end if null != orderItemDescList

  if(updateItemsFlag == true)  {
	  
	  //calculate tax if newly added item
	  TaxUtilAvalara.calculateOrderItemsTax(pConn, orderItemDV, pOrder.getSiteId(), pOrder.getAccountId(), 
			  pOrder.getStoreId(), distEjb, siteEjb);

      TaxUtilAvalara.recalculateItemTaxAmount(orderItemDV);
  }

  if(updateItemsFlag == true)  {
    OrderItemDataVector updatedItems;
    if(consolidatedOrderFl) {
      OrderDAO.updateReplacedOrderItems(pConn,changedOrderItemList, modReplOrderItems,pUser);
      updatedItems = new OrderItemDataVector();
      for(Iterator it = changedOrderItemList.iterator();it.hasNext(); )  {
        OrderItemData itemD = (OrderItemData) it.next();
        updatedItems.add(updateAddOrderItemWorker(pConn,itemD));
      }
    } else {
      updatedItems = orderEjb.updateOrderItemCollection(changedOrderItemList);
      Iterator it = changedOrderItemList.iterator();
      while(it.hasNext())
      {
        OrderItemData itemD = (OrderItemData) it.next();

        OrderItemActionData oiaD = OrderItemActionData.createValue();
        oiaD.setOrderId(itemD.getOrderId());
        oiaD.setOrderItemId(itemD.getOrderItemId());
        oiaD.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED);
        oiaD.setAddBy(pUser);
        //oiaD = OrderItemActionDataAccess.insert(pConn, oiaD);

        updatedItems.add(updateAddOrderItemWorker(pConn,itemD));
      }
    }
  }

      return poDM;
  }

  private void removeWorkflowQueue(APIAccess pFactory, int orderId) throws Exception {
      Workflow workflowEjb = pFactory.getWorkflowAPI();
      workflowEjb.deleteWorkflowQueueEntryByOrderId(orderId);	  
  }

  private void processWorkflowQueue(APIAccess pFactory, int orderId, String pShortDesc) throws Exception {
      Workflow workflowEjb = pFactory.getWorkflowAPI();
      workflowEjb.delWorkflowQueueEntryByOrderIdAndShortDesc(orderId, pShortDesc);	  
  }

  static class CurrencyFormat {

      /**
       * <code>parse</code> a string representing a currency value and
       * generate the corresponding BigDecimal value.  If default locale
       * is the 'US', examples of valid strings are: "$123.45", "123.45",
       * "$1,234.45".  This method works by firsting attempting to parse
       * the string as a currency, and failing that as a number.
       * @see java.math.BigDecimal
       *
       * @param value a <code>String</code> value
       * @return a <code>BigDecimal</code> value
       * @exception ParseException if an error occurs
       */
      public static BigDecimal parse(String value) throws ParseException {
          BigDecimal decValue;
          try {
              if (value == null || value.equals("")) {
                  decValue = new BigDecimal(0);
              } else {
                  // first try to parse as a plain number (no currency symbol,
                  // grouping symbols, etc.)
                  NumberFormat nf  = NumberFormat.getInstance();
                  Number n = nf.parse(value);
                  decValue = new BigDecimal(n.doubleValue());
              }
          } catch (ParseException pe) {
              // OK, wasn't just a plain number - try as a currency
              // Hardcode the US locale until we decide what to do
              NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
              Number n = nf.parse(value);
              // changing to a double - potential roundoff issues
              decValue = new BigDecimal(n.doubleValue());
          }
          return decValue;
      }

      /**
       * <code>format</code> returns a string representing the currency
       * value.  For example, something like: "$1,234,567.89"
       *
       * @param value a <code>BigDecimal</code> value
       * @return a <code>String</code> value
       */
      public static String format(BigDecimal value) {
          // Hardcode the US locale until we decide what to do
          NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
      if(value==null) value = new BigDecimal(0);
          return nf.format(value);
      }

      /**
       * <code>formatAsNumber</code> returns a string representing the
       * decimal number value.  For example, something like: "1234567.89"
       *
       * @param value a <code>BigDecimal</code> value
       * @return a <code>String</code> value
       */
      public static String formatAsNumber(BigDecimal value) {
          NumberFormat nf = NumberFormat.getInstance();
          return nf.format(value);
      }
  }

    public static boolean setServiceData(CatalogInformation catalogInfoEjb,
                                         Asset assetEjb,
                                         ItemInformation itemInfoEjb,
                                         OrderItemDescData orderItemDescD,
                                         OrderItemData orderItemD,
                                         int itemId, int assetId,
                                         int catalogId, int origDistId,
                                         boolean itemInputedFlag,
                                         String pUser) throws PipelineMessageException, RemoteException {

        ServiceData serviceD = null;
        boolean orderItemChangedFlag = false;

        try {
            serviceD = catalogInfoEjb.getServiceData(itemId, catalogId);
        } catch (Exception e) {
            throw new PipelineMessageException("error.badItem", " [serviceId=" +
                    itemId + ", catalogId=" + catalogId + "] 1");
        }
        if (null == serviceD) {
            throw new PipelineMessageException("error.badItem", " [serviceId=" +
                    itemId + ", catalogId=" + catalogId + "] 2");
        }
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setAssetId(assetId);
        AssetDetailViewVector assetVV=null;
        try {
            assetVV = assetEjb.getAssetDetailViewVector(criteria);
            if (assetVV.size() > 1)
                throw new Exception("Multiple Asset Data for assetId : " + criteria.getAssetId());
            orderItemDescD.setAssetInfo(((AssetDetailView) assetVV.get(0)).getAssetDetailData().getAssetData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PipelineMessageException("error.badAsset","[assetId : " + criteria.getAssetId()+"]");
        }

        IdVector serviceIds=new IdVector();
        serviceIds.add(new Integer(serviceD.getItemData().getItemId()));
        ItemDataVector services = getServicesByIds(((AssetDetailView) assetVV.get(0)).getAssetServiceAssoc(), serviceIds);

        if(services==null||services.size()==0)
        throw new PipelineMessageException("error.badServiceForAsset","[ assetId : " + criteria.getAssetId()+" serviceId : "+serviceD.getItemData().getItemId()+" ]");


        orderItemDescD.setItemInfo(itemInfoEjb.getItem(orderItemD.getItemId()));


        if (null == orderItemD) {
            orderItemD = OrderItemData.createValue();
        }


        orderItemChangedFlag = true;

        orderItemD.setItemId(serviceD.getItemData().getItemId());
        orderItemD.setAssetId(assetId);
        orderItemD.setItemSkuNum(serviceD.getItemData().getSkuNum());
        orderItemD.setItemShortDesc(serviceD.getItemData().getShortDesc());
        orderItemD.setItemCost(null);
        orderItemD.setCostCenterId(serviceD.getCostCenterId());

        String custSkuNum = serviceD.getCatalogStructureData().getCustomerSkuNum();
        if (custSkuNum == null || custSkuNum.trim().length() == 0) {
            if (serviceD.getItemData().getSkuNum() != 0) {
                custSkuNum = String.valueOf(serviceD.getItemData().getSkuNum());
            } else {
                custSkuNum = "";
            }
        }
        String custShortDesc = serviceD.getCatalogStructureData().getShortDesc();
        if (custShortDesc==null || custShortDesc.trim().length() == 0) {
            custShortDesc = serviceD.getItemData().getShortDesc();
            if (custShortDesc == null)
                custShortDesc = "";
        }

        orderItemD.setCustItemSkuNum(custSkuNum);
        orderItemD.setCustItemShortDesc(custShortDesc);
        orderItemD.setModBy(pUser);
        if (0 != origDistId) {
            orderItemD.setDistItemShortDesc(serviceD.getCatalogDistributor().getShortDesc());
            ItemMappingDataVector distrMappingDV = serviceD.getDistrMappingDV();
            orderItemD.setDistItemSkuNum("");
            for (int ii = 0; ii < distrMappingDV.size(); ii++) {
                ItemMappingData itemMappingD = (ItemMappingData)
                        distrMappingDV.get(ii);
                if (itemMappingD.getBusEntityId() == origDistId) {
                    orderItemD.setDistItemSkuNum(itemMappingD.getItemNum());
                }
            }
        } else {
            orderItemD.setDistItemSkuNum(null);
        }

        return orderItemChangedFlag;

    }

    public static ItemDataVector getServicesByIds(ItemDataVector assetServiceAssoc, IdVector serviceIdsInSiteCatalog) {

        ItemDataVector result = new ItemDataVector();
        if (assetServiceAssoc == null || serviceIdsInSiteCatalog == null) return result;
        if (assetServiceAssoc.size() == 0 || assetServiceAssoc.size() == 0) return result;

        Iterator it = assetServiceAssoc.iterator();
        while (it.hasNext()) {
            ItemData service = (ItemData) it.next();
            Iterator it2 = serviceIdsInSiteCatalog.iterator();
            while (it2.hasNext()) {
                int id = ((Integer) it2.next()).intValue();
                if (service.getItemId() == id) {
                    result.add(service);
                    break;
                }
            }
        }
        return result;
    }
}
