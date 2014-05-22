/*
 * OrderUOMConverter.java
 *
 * Created on March 19, 2004, 2:16 PM
 */

package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.HashMap;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.PipelineUtil;
import java.math.BigDecimal;

import org.apache.log4j.Category;

/**
 * This component should not be run until after the distributor and the items are finalized as it will
 *change the quantity that goes on the po based off these two variables
 * @author  bstevens
 */
public class OrderDistQtyUOMConverter implements OrderPipeline {
	private static Category log = Category.getInstance(OrderDistQtyUOMConverter.class);
  private static final BigDecimal ZERO = new BigDecimal(0);
  public OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                    OrderPipelineActor pActor,
                                    Connection pCon,
                                    APIAccess pFactory) throws PipelineException {
    pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
    OrderData orderD = pBaton.getOrderData();
    OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
    try {
      process(orderD, orderItemDV, orderD.getAccountId(), pCon, pFactory, pBaton, pActor);
      orderD = OrderDataAccess.select(pCon, orderD.getOrderId());
      if (OrderPipelineBaton.STOP.equals(pBaton.getWhatNext())) {
        PipelineUtil.updateOrder(pBaton, pActor, pCon, pFactory);
        return pBaton;
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderItemDataAccess.ORDER_ID, orderD.getOrderId());
      dbc.addOrderBy(OrderItemDataAccess.ORDER_ITEM_ID);
      orderItemDV = OrderItemDataAccess.select(pCon, dbc);

      pBaton.setOrderData(orderD);
      pBaton.setOrderItemDataVector(orderItemDV);
      //pBaton.setOrderStatus(orderD.getOrderStatusCd());
      //if(pBaton.hasErrors()){
      //  pBaton.setWhatNext(OrderPipelineBaton.STOP);
      //} else {
      //  pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
      //}
      return pBaton;
    } catch (Exception exc) {
      exc.printStackTrace();
      throw new PipelineException(exc.getMessage());
    }
  }

  /**
   *Process this pipeline.
   *
   *@param pOrderData
   *@param pOrderItemDataVector
   *@param pAccountId
   *@param Connection a active database connection
   *@param APIAccess
   */
  private void process(OrderData pOrderData, OrderItemDataVector pOrderItemDataVector,
                       int pAccountId, Connection pCon, APIAccess pFactory,
                       OrderPipelineBaton pBaton, OrderPipelineActor pActor) throws Exception {
	  log.info("process method started");
    HashMap distMap = new HashMap();
    //int decimalsForCurrency = pFactory.getCurrencyAPI().getDecimalPlacesForCurrency(pOrderData.getCurrencyCd());
    int decimalsForCurrency = I18nUtil.getCurrencyByCd(pOrderData.getCurrencyCd()).getDecimals();
    if(decimalsForCurrency == -1){
    	log.info("Could not find decimals for currency defaulting to 2");
    	//default to 2 as this is what most currencies use
    	decimalsForCurrency = 2;
    }
    log.info("Decimals for currency "+pOrderData.getCurrencyCd()+" is "+decimalsForCurrency);
    Iterator it = pOrderItemDataVector.iterator();
    while (it.hasNext()) {
      OrderItemData oi = (OrderItemData) it.next();
      if (RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
          equals(oi.getOrderItemStatusCd())) {
        continue;
      }
      oi.setDistItemQuantity(oi.getTotalQuantityOrdered());
      String distErpNum = oi.getDistErpNum();
      if (!Utility.isSet(distErpNum)) {
        String messKey = "pipeline.message.noDistErpNum";
        String mess = "No distributor erp number for the item: " + oi.getItemSkuNum();
        if(!pBaton.isHistoricalOrderOrderProps()){
	        pBaton.addError
	          (pCon, OrderPipelineBaton.NO_DISTRIBUTOR_FOR_SKU,
	           "Distr Qty UOM Converter",
	           RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, oi.getOrderLineNum(), 0,
	           messKey,
	           "" + oi.getItemSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
	        pBaton.setWhatNext(OrderPipelineBaton.STOP);
        }
        return;
      }
      BusEntityData dist = Utility.getDistBusEntityByErpNumber(pCon, oi.getDistErpNum(), distMap);
      if (dist == null) {
        return;
      }
      DBCriteria imCrit = new DBCriteria();
      imCrit.addEqualTo(ItemMappingDataAccess.ITEM_ID, oi.getItemId());
      imCrit.addEqualTo(ItemMappingDataAccess.BUS_ENTITY_ID, dist.getBusEntityId());
      ItemMappingDataVector imv = ItemMappingDataAccess.select(pCon, imCrit);
      ItemMappingData im = null;
      if (imv != null && !imv.isEmpty()) {
        im = (ItemMappingData) imv.get(0);
      }

      if (im != null && im.getUomConvMultiplier() != null && im.getUomConvMultiplier().compareTo(ZERO) > 0) {
        try {
          oi.setDistUomConvMultiplier(im.getUomConvMultiplier());
          oi = doConversion(oi,decimalsForCurrency);
        } catch (Exception e) {
        	if(!pBaton.isHistoricalOrderOrderProps()){
	          String messKey = "pipeline.message.exception";
	          String msg = e.getMessage();
	          pBaton.addError
	            (pCon, OrderPipelineBaton.UOM_CONVERSION_PROBLEM,
	             "Distr Qty UOM Converter",
	             RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW, oi.getOrderLineNum(), 0,
	             messKey,
	             msg, RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING);
	          pBaton.setWhatNext(OrderPipelineBaton.STOP);
        	}
          return;
        }
      }
      OrderItemDataAccess.update(pCon, oi);
    }
    if (pOrderData.isDirty()) {
      OrderDataAccess.update(pCon, pOrderData);
    }
    log.info("process method ended");
  }

  /**
   *performs the conversion based off the multiplier that is set in the order item.  Throws an exception if there was
   *any issue with the conversion
   */
  public static OrderItemData doConversion(OrderItemData oi, int currencyDecimalPlaces) throws Exception {
    //convert the uom
    BigDecimal currQty = new BigDecimal(oi.getTotalQuantityOrdered());
    BigDecimal currCost = oi.getDistItemCost();
    BigDecimal multiplier = oi.getDistUomConvMultiplier();
    BigDecimal distQty = multiplier.multiply(currQty);

    try {
      distQty = distQty.setScale(0, BigDecimal.ROUND_UNNECESSARY);
    } catch (ArithmeticException e) {
      throw new PipelineException("Problem converting distributor uom: " + multiplier + " * " + currQty +
                                  " must be a positive integer");
    }
    BigDecimal distCost;
    try {
      currCost = currCost.setScale(currencyDecimalPlaces, BigDecimal.ROUND_UNNECESSARY);
      log.info(" OrderItemData doConversion " +
                         " currCost=" + currCost +
                         " multiplier=" + multiplier
        );
      distCost = currCost.divide(multiplier, BigDecimal.ROUND_UNNECESSARY);
      distCost = distCost.setScale(currencyDecimalPlaces, BigDecimal.ROUND_UNNECESSARY);

    } catch (ArithmeticException e) {
      log.error(" OrderItemData doConversion " +
                         " oi=" + oi +
                         " distQty=" + distQty,e
        );

      String msg = "Problem converting distributor uom: " + multiplier + " * " + currCost +
                   " must be a positive number with 2 decimal places or less, OrderItemData=" + oi;
      throw new Exception(msg);
    }
    if (distCost.compareTo(ZERO) < 0 || distQty.compareTo(ZERO) < 0) {
      String msg = "Problem converting distributor uom: " + distQty + " AND " + distCost + " must be positive";
      throw new Exception(msg);
    }
    oi.setDistItemQuantity(distQty.intValue());
    oi.setDistUomConvCost(distCost);
    oi.setDistUomConvMultiplier(multiplier);

    return oi;
  }

  // private void insertOrderNote(OrderItemData oid, String note, Connection pConn)
  // throws SQLException{
  //     OrderDAO.addOrderItemNote(pConn, oid.getOrderId(), oid.getOrderItemId(), note,"Order Dist Qty UOM Converter");
  // }
}
