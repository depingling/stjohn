package com.espendwise.webservice.restful;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.InvoiceCustDetailDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.OrderItemActionDataVector;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.ItemSubstitutionDataVector;
import com.cleanwise.service.api.value.OrderItemActionDescDataVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.operations.ProcessBatchOrders;
import com.cleanwise.service.api.session.StoreOrder;
import com.cleanwise.service.api.util.ICleanwiseUser;
import com.cleanwise.service.api.util.PipelineMessageException;
import com.cleanwise.service.api.value.StoreOrderChangeRequestData;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.OrderData;
import com.espendwise.ocean.common.webaccess.BasicRequestValue;
import com.espendwise.ocean.common.webaccess.BasicResponseValue;
import com.espendwise.ocean.common.webaccess.ResponseError;

import com.espendwise.webservice.restful.value.BatchOrderValidationRequestData;
import com.espendwise.webservice.restful.value.OrderChangeRequestData;
import com.espendwise.webservice.restful.value.OrderCancelRequestData;

import javax.ws.rs.*;
import java.util.*;

import javax.ejb.EJBException;

@Path("/service")
public class OrderRunRestService extends RestServiceSuper {

    private static final Logger log = Logger.getLogger(OrderRunRestService.class);    
        
    @PUT
    @Path("/updateOrder")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public BasicResponseValue updateOrder(OrderChangeRequestData crOrderData) {
        BasicResponseValue<Integer> response = null;

        try {
            ICleanwiseUser user = authorize(crOrderData.getAccessToken(), crOrderData.getLoginData());

            if (user != null) {
                StoreOrder storeOrderEJB = APIAccess.getAPIAccess().getStoreOrderAPI();
        
                StoreOrderChangeRequestData changeRequest = convertChangeRequest(crOrderData);
                        
                OrderData orderData = crOrderData.getOrderData();
                if (orderData != null && RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(orderData.getOrderStatusCd())) {
                    storeOrderEJB.processPipelineSyncAsync(orderData);
                } else {
                    storeOrderEJB.updateOrder(changeRequest);
                }

                response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.OK),
                                                           BasicResponseValue.STATUS.OK,
                                                           null);
            } else {
                response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.ACCESS_DENIED),
                                                           BasicResponseValue.STATUS.ACCESS_DENIED,
                                                           null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            EJBException ee = (EJBException) e.getCause();
            if (ee != null && (ee.getCause() instanceof PipelineMessageException)) {
                PipelineMessageException pex = (PipelineMessageException) ee.getCause();
                
                response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.ERROR),
                                                           BasicResponseValue.STATUS.ERROR,
                                                           Utility.toList(new ResponseError(pex.getMessage())));
            } else {
                 response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.ERROR),
                                                            BasicResponseValue.STATUS.ERROR,
                                                            Utility.toList(new ResponseError(e.getMessage())));
            }
        }

        return response;
    }
    
    @PUT
    @Path("/cancelOrder")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public BasicResponseValue cancelOrder(BasicRequestValue<OrderCancelRequestData> request) {
        BasicResponseValue<Integer> response = null;
        
        try {
            ICleanwiseUser user = authorize(request.getObject().getAccessToken(), request.getObject().getLoginData());

            if (user != null) {
                StoreOrder storeOrderEJB = APIAccess.getAPIAccess().getStoreOrderAPI();

                storeOrderEJB.cancelOrder(request.getObject().getOrderId(),
                                          request.getObject().getLoginData().getUserName());

                response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.OK),
                                                           BasicResponseValue.STATUS.OK,
                                                           null);
            } else {
                response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.ACCESS_DENIED),
                                                           BasicResponseValue.STATUS.ACCESS_DENIED,
                                                           null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            EJBException ee = (EJBException) e.getCause();
            if (ee != null && (ee.getCause() instanceof PipelineMessageException)) {
                PipelineMessageException pex = (PipelineMessageException) ee.getCause();
                
                response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.ERROR),
                                                           BasicResponseValue.STATUS.ERROR,
                                                           Utility.toList(new ResponseError(pex.getMessage())));
            } else {
                 response = new BasicResponseValue<Integer>(Integer.valueOf(BasicResponseValue.STATUS.ERROR),
                                                           BasicResponseValue.STATUS.ERROR,
                                                           Utility.toList(new ResponseError(e.getMessage())));
            }
        }

        return response;
    }

    private StoreOrderChangeRequestData convertChangeRequest (OrderChangeRequestData crOrderData) {
        StoreOrderChangeRequestData changeRequest = StoreOrderChangeRequestData.createValue();
        
        changeRequest.setOrderData(crOrderData.getOrderData());
        changeRequest.setOldOrderData((OrderData)crOrderData.getOrderData().clone());
        changeRequest.setUserName(crOrderData.getLoginData().getUserName());

        if (Utility.isSet(crOrderData.getDeleteOrderItemIds())) {
            IdVector toDeleteIds = new IdVector();
            toDeleteIds.addAll(crOrderData.getDeleteOrderItemIds());
            changeRequest.setDelOrderItems(toDeleteIds);
        }

        changeRequest.setTotalFreightCost(crOrderData.getTotalFreightCostValue());
        changeRequest.setTotalMiscCost(crOrderData.getTotalMiscCostValue());
        changeRequest.setSmallOrderFeeAmt(crOrderData.getSmallOrderFeeValue());
        changeRequest.setFuelSurchargeAmt(crOrderData.getFuelSurChargeValue());
        changeRequest.setDiscountAmt(crOrderData.getDiscountValue());

        changeRequest.setNewOrderDate(crOrderData.getNewOrderDate());
        if (crOrderData.getNewSiteId() != null) {
            changeRequest.setNewSiteId(crOrderData.getNewSiteId().intValue());
        }

        if (Utility.isSet(crOrderData.getOrderItems())) {
            OrderItemDataVector orderItemsVector = new OrderItemDataVector();    
            orderItemsVector.addAll(crOrderData.getOrderItems());
            changeRequest.setOldOrderItems(orderItemsVector);
        }

        if (Utility.isSet(crOrderData.getOrderItemsDesc())) {
            OrderItemDescDataVector orderItemDescVector = new OrderItemDescDataVector();
            com.cleanwise.service.api.value.OrderItemDescData orderItemDescST;
            for (com.espendwise.webservice.restful.value.OrderItemDescData orderItemDesc : crOrderData.getOrderItemsDesc()) {
                orderItemDescST = com.cleanwise.service.api.value.OrderItemDescData.createValue();
                orderItemDescST.setActualCost(orderItemDesc.getActualCost());
                orderItemDescST.setActualQty(orderItemDesc.getActualQty());
                if (Utility.isSet(orderItemDesc.getAssetIdS())) {
                    orderItemDescST.setAssetIdS(orderItemDesc.getAssetIdS());
                }
                orderItemDescST.setAssetInfo(orderItemDesc.getAssetInfo());
                orderItemDescST.setCalculatedSalesTax(orderItemDesc.getCalculatedSalesTax());
                if (Utility.isSet(orderItemDesc.getCatalogItemSkuNum())) {
                    orderItemDescST.setCatalogItemSkuNum(orderItemDesc.getCatalogItemSkuNum());
                }
                if (Utility.isSet(orderItemDesc.getCwCostS())) {
                    orderItemDescST.setCwCostS(orderItemDesc.getCwCostS());
                }
                orderItemDescST.setDeliveryDate(orderItemDesc.getDeliveryDate());
                if (orderItemDesc.getDistId() != null) {
                    orderItemDescST.setDistId(orderItemDesc.getDistId().intValue());
                }
                if (Utility.isSet(orderItemDesc.getDistIdS())) {
                    orderItemDescST.setDistIdS(orderItemDesc.getDistIdS());
                }
                if (Utility.isSet(orderItemDesc.getDistLineNumS())) {
                    orderItemDescST.setDistLineNumS(orderItemDesc.getDistLineNumS());
                }
                if (Utility.isSet(orderItemDesc.getDistName())) {
                    orderItemDescST.setDistName(orderItemDesc.getDistName());
                }
                orderItemDescST.setDistPoNote(orderItemDesc.getDistPoNote());
                if (Utility.isSet(orderItemDesc.getDistRuntimeDisplayName())) {
                    orderItemDescST.setDistRuntimeDisplayName(orderItemDesc.getDistRuntimeDisplayName());
                }
                orderItemDescST.setHasNote(orderItemDesc.getHasNote());
                if (Utility.isSet(orderItemDesc.getInvoiceCustDetailList())) {
                    InvoiceCustDetailDataVector icddV = new InvoiceCustDetailDataVector();
                    icddV.addAll(orderItemDesc.getInvoiceCustDetailList());
                    orderItemDescST.setInvoiceCustDetailList(icddV);
                }
                if (Utility.isSet(orderItemDesc.getInvoiceDistDataList())) {
                    InvoiceDistDataVector iddV = new InvoiceDistDataVector();
                    iddV.addAll(orderItemDesc.getInvoiceDistDataList());
                    orderItemDescST.setInvoiceDistDataVector(iddV);
                }
                if (Utility.isSet(orderItemDesc.getInvoiceDistDetailList())) {
                    InvoiceDistDetailDataVector idddV = new InvoiceDistDetailDataVector();
                    idddV.addAll(orderItemDesc.getInvoiceDistDetailList());
                    orderItemDescST.setInvoiceDistDetailList(idddV);
                }
                if (Utility.isSet(orderItemDesc.getItemIdS())) {
                    orderItemDescST.setItemIdS(orderItemDesc.getItemIdS());
                }
                orderItemDescST.setItemInfo(orderItemDesc.getItemInfo());
                if (Utility.isSet(orderItemDesc.getItemPriceS())) {
                    orderItemDescST.setItemPriceS(orderItemDesc.getItemPriceS());
                }
                if (Utility.isSet(orderItemDesc.getItemQuantityRecvdS())) {
                    orderItemDescST.setItemQuantityRecvdS(orderItemDesc.getItemQuantityRecvdS());
                }
                if (Utility.isSet(orderItemDesc.getItemQuantityS())) {
                    orderItemDescST.setItemQuantityS(orderItemDesc.getItemQuantityS());
                }
                if (Utility.isSet(orderItemDesc.getItemSkuNumS())) {
                    orderItemDescST.setItemSkuNumS(orderItemDesc.getItemSkuNumS());
                }
                if (Utility.isSet(orderItemDesc.getItemStatus())) {
                    orderItemDescST.setItemStatus(orderItemDesc.getItemStatus());
                }
                if (Utility.isSet(orderItemDesc.getLineTotalS())) {
                    orderItemDescST.setLineTotalS(orderItemDesc.getLineTotalS());
                }
                if (Utility.isSet(orderItemDesc.getNewAssetName())) {
                    orderItemDescST.setNewAssetName(orderItemDesc.getNewAssetName());
                }
                if (Utility.isSet(orderItemDesc.getNewDistName())) {
                    orderItemDescST.setNewDistName(orderItemDesc.getNewDistName());
                }
                orderItemDescST.setNewInvoiceDistDetail(orderItemDesc.getNewInvoiceDistDetail());
                orderItemDescST.setNewOrderItemActionQty(orderItemDesc.getNewOrderItemActionQty());
                if (Utility.isSet(orderItemDesc.getNewOrderItemActionQtyS())) {
                    orderItemDescST.setNewOrderItemActionQtyS(orderItemDesc.getNewOrderItemActionQtyS());
                }
                if (Utility.isSet(orderItemDesc.getNewServiceName())) {
                    orderItemDescST.setNewServiceName(orderItemDesc.getNewServiceName());
                }
                if (Utility.isSet(orderItemDesc.getOpenLineStatusCd())) {
                    orderItemDescST.setOpenLineStatusCd(orderItemDesc.getOpenLineStatusCd());
                }
                orderItemDescST.setOrderDiscount(orderItemDesc.getOrderDiscount());
                orderItemDescST.setOrderFreight(orderItemDesc.getOrderFreight());
                orderItemDescST.setOrderFuelSurcharge(orderItemDesc.getOrderFuelSurcharge());
                orderItemDescST.setOrderHandling(orderItemDesc.getOrderHandling());
                orderItemDescST.setOrderItem(orderItemDesc.getOrderItem());
                if (Utility.isSet(orderItemDesc.getOrderItemActionDescList())) {
                    OrderItemActionDescDataVector oiaddV = new OrderItemActionDescDataVector();
                    com.cleanwise.service.api.value.OrderItemActionDescData orderItemActionDescST;
                    for (com.espendwise.webservice.restful.value.OrderItemActionDescData oiad : orderItemDesc.getOrderItemActionDescList()) {
                        orderItemActionDescST = com.cleanwise.service.api.value.OrderItemActionDescData.createValue();
                        
                        orderItemActionDescST.setDistItemSkuNum(oiad.getDistItemSkuNum());
                        orderItemActionDescST.setItemDistCost(oiad.getItemDistCost());
                        orderItemActionDescST.setItemPack(oiad.getItemPack());
                        orderItemActionDescST.setItemShortDesc(oiad.getItemShortDesc());
                        if (oiad.getItemSkuNum() != null) {
                            orderItemActionDescST.setItemSkuNum(oiad.getItemSkuNum());
                        }
                        orderItemActionDescST.setItemUom(oiad.getItemUom());
                        orderItemActionDescST.setOrderItemAction(oiad.getOrderItemAction());
                        
                        oiaddV.add(orderItemActionDescST);
                    }
                    orderItemDescST.setOrderItemActionDescList(oiaddV);
                } 
                if (Utility.isSet(orderItemDesc.getOrderItemActionList())) {
                    OrderItemActionDataVector oiadV = new OrderItemActionDataVector();
                    oiadV.addAll(orderItemDesc.getOrderItemActionList());
                    orderItemDescST.setOrderItemActionList(oiadV);
                }
                orderItemDescST.setOrderItemIdS(orderItemDesc.getOrderItemIdS());
                if (Utility.isSet(orderItemDesc.getOrderItemNotes())) {
                    OrderPropertyDataVector opdV = new OrderPropertyDataVector();
                    opdV.addAll(orderItemDesc.getOrderItemNotes());
                    orderItemDescST.setOrderItemNotes(opdV);
                }
                if (Utility.isSet(orderItemDesc.getOrderItemSubstitutionList())) {
                    ItemSubstitutionDataVector isdV = new ItemSubstitutionDataVector();
                    isdV.addAll(orderItemDesc.getOrderItemSubstitutionList());
                    orderItemDescST.setOrderItemSubstitutionList(isdV);
                }
                orderItemDescST.setOrderSmallOrderFee(orderItemDesc.getOrderSmallOrderFee());
                if (Utility.isSet(orderItemDesc.getPoItemStatus())) {
                    orderItemDescST.setPoItemStatus(orderItemDesc.getPoItemStatus());
                }
                orderItemDescST.setPurchaseOrderData(orderItemDesc.getPurchaseOrderData());
                if (Utility.isSet(orderItemDesc.getQtyReturnedString())) {
                    orderItemDescST.setQtyReturnedString(orderItemDesc.getQtyReturnedString());
                }
                orderItemDescST.setReSale(orderItemDesc.getReSale());
                orderItemDescST.setReturnRequestDetailData(orderItemDesc.getReturnRequestDetailData());
                if (Utility.isSet(orderItemDesc.getShipStatus())) {
                    orderItemDescST.setShipStatus(orderItemDesc.getShipStatus());
                }
                orderItemDescST.setShoppingHistory(orderItemDesc.getShoppingHistory());
                orderItemDescST.setStandardProductList(orderItemDesc.getStandardProductList());
                orderItemDescST.setTargetShipDate(orderItemDesc.getTargetShipDate());
                if (Utility.isSet(orderItemDesc.getTargetShipDateString())) {
                    orderItemDescST.setTargetShipDateString(orderItemDesc.getTargetShipDateString());
                }
                orderItemDescST.setTaxExempt(orderItemDesc.getTaxExempt());
                orderItemDescST.setWorkingInvoiceDistDetailData(orderItemDesc.getWorkingInvoiceDistDetailData());
                        
                orderItemDescVector.add(orderItemDescST);
            }
            changeRequest.setOrderItemsDesc(orderItemDescVector);
        }
        
        OrderMetaDataVector orderMetaVector = new OrderMetaDataVector();
        if (Utility.isSet(crOrderData.getOrderMeta())) {
            orderMetaVector.addAll(crOrderData.getOrderMeta());
        }
        changeRequest.setOrderMeta(orderMetaVector);
        changeRequest.setRebillOrder(crOrderData.getRebillOrder());
        
        return changeRequest;
    }
    
    @PUT
    @Path("/validateBatchOrder")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public BasicResponseValue validateBatchOrder(BatchOrderValidationRequestData batchOrderData) {
        BasicResponseValue<Map> response = null;
        Map responseMap = new HashMap();
        
        try {
            ICleanwiseUser user = authorize(batchOrderData.getAccessToken(), batchOrderData.getLoginData());

            if (user != null) {
                ProcessBatchOrders processBatchOrders = new ProcessBatchOrders();
                List errors = processBatchOrders.validateBatchOrder(batchOrderData.getStoreId(), batchOrderData.getFileName(), batchOrderData.getDataContents());

                if (errors.size() > 0){
                	responseMap.put("responseStatus", BasicResponseValue.STATUS.ERROR);
                	response = new BasicResponseValue<Map>(responseMap, BasicResponseValue.STATUS.ERROR, errors);
                }else{
                	responseMap.put("responseStatus", ""+BasicResponseValue.STATUS.OK);
                	responseMap.put("orderCount", ""+processBatchOrders.getOrderCount());
                	response = new BasicResponseValue<Map>(responseMap, BasicResponseValue.STATUS.OK, null);
                }
            } else {
            	responseMap.put("responseStatus", BasicResponseValue.STATUS.ACCESS_DENIED);
                response = new BasicResponseValue<Map>(responseMap, BasicResponseValue.STATUS.ACCESS_DENIED, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("responseStatus", BasicResponseValue.STATUS.ERROR);
            response = new BasicResponseValue<Map>(responseMap, BasicResponseValue.STATUS.ERROR, Utility.toList(new ResponseError(e.getMessage())));
        }

        return response;
    }    
}
