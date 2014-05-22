/*
 * InvoiceDistHoldInvoice.java
 *
 * Created on October 19, 2005, 12:08 PM
 *
 * Copyright October 19, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderItemData;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Category;

/**
 * Performs all of the logic to set the adjusted cost and log errors if the pricing is different and
 * the distributor is configured for this to be an error.
 *
 * @author bstevens
 */
public class InvoiceDistPriceAnalysis implements InvoiceDistPipeline {
  private static final Category log = Category.getInstance(InvoiceDistPriceAnalysis.class);

  ArrayList invoiceErrorsToLog = new ArrayList();
  DistributorData distributor;
  Connection mCon;
  InvoiceDistData invoice;
  private static BigDecimal ZERO=new BigDecimal(0);

  public void process(InvoiceDistPipelineBaton pBaton, Connection pCon,
      APIAccess pFactory) throws PipelineException {
    try {
        if (pBaton.isInvoiceApproved()) {
	      log.info("process.invoice is approved, bypassing pipeline");
          return;
        }
        int distId = pBaton.getInvoiceDistData().getBusEntityId();
        if (distId == 0) {
	      log.info("process.busEntityId (Distributor id) is null.  Error will be dealt with later, bypassing pipeline");
          return;
        }
        mCon = pCon;
        distributor = pBaton.getDistributorForInvoice(pFactory);

        invoice = pBaton.getInvoiceDistData();
        InvoiceDistDetailDataVector invoiceItems = pBaton.getInvoiceDistDetailDataVector();
        
        Iterator it = invoiceItems.iterator();
        BigDecimal adjustedTotal = new BigDecimal(0.00);
        adjustedTotal=adjustedTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        boolean foundPricingError = false;
        while(it.hasNext()){
        	InvoiceDistDetailData invDetailD = (InvoiceDistDetailData) it.next();
        	log.info("Processing invoice dist detail id: "+invDetailD.getInvoiceDistDetailId());
        	OrderItemData itemMatch = null;
        	if(invDetailD.getOrderItemId() != 0){
        	  try{
        		  log.info("Fetching order item: "+invDetailD.getOrderItemId());
                  itemMatch = OrderItemDataAccess.select(pCon,invDetailD.getOrderItemId());
        	  }catch(Exception e){}
        	}else{
        		itemMatch = null;
        	}
        	if(itemMatch!=null){
              log.info("Have an order item for this invoice item");
        	  int errorCt = processLineItem(invDetailD,itemMatch, pBaton);
        	  if(errorCt > 0){
                foundPricingError = true;
        	  }
        	}
        	BigDecimal qty = new BigDecimal(invDetailD.getDistItemQuantity());
        	BigDecimal cost = Utility.getBestCostFromInvoiceDistDetail(invDetailD);
        	log.info("Setting adjusted cost to: " + cost);
        	invDetailD.setAdjustedCost(cost);
        	BigDecimal lineItemTotal;
        	if(cost == null || qty == null){
        	  lineItemTotal = ZERO;
        	}else{
        	  lineItemTotal = qty.multiply(invDetailD.getAdjustedCost());
        	}
        	adjustedTotal = adjustedTotal.add(lineItemTotal);
        }//end looping through items


        adjustedTotal=adjustedTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        invoice.setSubTotal(adjustedTotal);


        //If the invoice price model is set to HOLD_ALL then log a note.
        //This is necessary here as opposed to the item loop both for performance
        //and because an invoice may have no items, no matched items, etc
        if (RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.HOLD_ALL.equals(distributor.getInvoiceLoadingPriceModel())) {
              String messKey = "pipeline.message.invoiceLoadingModelHoldAll";
              if (invoiceErrorsToLog != null && !invoiceErrorsToLog.contains(messKey)) {
                invoiceErrorsToLog.add(messKey);
              }
              foundPricingError = true;
        }

        if(foundPricingError){
          pBaton.addError("pipeline.message.pricingErrorFound");
        }

    } catch (Exception e) {
      e.printStackTrace();
      throw new PipelineException(e.getMessage());
    }
  }


  /**
   * Runs through all the item logic
   */
  private int processLineItem(InvoiceDistDetailData invDetailD,OrderItemData itemMatch,
                    InvoiceDistPipelineBaton pBaton){
    int exceptionCount = 0;
    // Now check the cost in the incoming detail record against what's
    // in the item record and record the lower of the two in the
    // adjusted_cost column of invoice detail.
    BigDecimal cwReqCost = invDetailD.getItemReceivedCost();
    BigDecimal cwCostMatch = itemMatch.getDistItemCost();
    BigDecimal cost = cwReqCost; // may be changed based off pricing

    log.debug("In Process Line Item...");

    // model
    invDetailD.setItemCost(cwCostMatch);

    String priceModel = distributor.getInvoiceLoadingPriceModel();
    BigDecimal diffThresholdUpper = distributor.getInvoiceAmountPercentAllowanceUpper();
    BigDecimal diffThresholdLower = distributor.getInvoiceAmountPercentAllowanceLower();
    if(diffThresholdUpper == null){
      diffThresholdUpper = ZERO;
    }
    if(diffThresholdLower == null){
      diffThresholdLower = ZERO;
    }
    BigDecimal percentDiff;
    log.debug("cwCostMatch="+cwCostMatch);
    log.debug("cwReqCost="+cwReqCost);
	cwCostMatch = cwCostMatch.setScale(10);
	cwReqCost = cwReqCost.setScale(10);
	if(cwCostMatch.compareTo(ZERO) == 0 && cwReqCost.compareTo(ZERO) == 0){
		log.debug("Both zero");
	  percentDiff = ZERO;
	}else if (cwCostMatch.compareTo(ZERO) == 0){
		log.debug("Cost match zero");
	  percentDiff = new BigDecimal(100);
    }else if (cwReqCost != null && cwCostMatch != null) {
    	log.debug("Doing multiplication..."+cwReqCost.subtract(cwCostMatch).abs()+"/"+cwCostMatch);
      percentDiff = cwReqCost.subtract(cwCostMatch).abs().divide(cwCostMatch, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100));
    } else {
      percentDiff = new BigDecimal(100);
    }
    log.info("percentDiff for invoice dist detail id "+invDetailD.getInvoiceDistId()+ "="+percentDiff);

    boolean overCostDiff = false;
    if (cwReqCost != null && cwCostMatch != null) {
    	log.debug("non null="+cwReqCost.compareTo(cwCostMatch));
      if (cwReqCost.compareTo(cwCostMatch) >= 0) {
        if (percentDiff.compareTo(diffThresholdUpper) > 0) {
        	log.debug("overDiff=true");
          overCostDiff = true;
        }
      } else {
        if (percentDiff.compareTo(diffThresholdLower) > 0) {
          log.debug("overDiff=true  B");
          overCostDiff = true;
        }
      }
    }

    log.debug("overCostDiff="+overCostDiff);

    String errorMessageKey = "";
    String errorMessage = "";

    // set cost based off the price model for this distributor
    if (RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.LOWEST.equals(priceModel)) {
      if (cwReqCost != null && cwCostMatch != null) {
        if (overCostDiff) {
          errorMessage = "cost difference percent ("
              + percentDiff
              + ") exceeds tolarance for item: "
              + invDetailD.getDistItemSkuNum()
              + " ("+invDetailD.getItemSkuNum()+")";

              errorMessageKey = "pipeline.message.priceErrFoundInvoiceDist";
              exceptionCount++;
        }
        if (cwReqCost.compareTo(cwCostMatch) > 0) {
          cost = cwCostMatch;
        }
      }
    } else if (RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.PREDETERMINED.equals(priceModel)) {
      if (cwCostMatch != null) {
        cost = cwCostMatch;
      }
      if (cwReqCost != null && cwCostMatch != null) {
        if (overCostDiff) {
          errorMessage = "cost difference percent ("
              + percentDiff
              + ") exceeds tolarance for item: "
              + invDetailD.getDistItemSkuNum()
              + " ("+invDetailD.getItemSkuNum()+")";

              errorMessageKey = "pipeline.message.priceErrFoundInvoiceDist";
              exceptionCount++;
        }
      }
    } else if (RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.DISTRIBUTOR_INVOICE.equals(priceModel)) {
      if (cwReqCost != null) {
        cost = cwReqCost;
      }
      if (cwReqCost != null && cwCostMatch != null) {
        if (overCostDiff) {
          errorMessage = "cost difference percent ("
              + percentDiff
              + ") exceeds tolarance for item: "
              + invDetailD.getDistItemSkuNum()
              + " ("+invDetailD.getItemSkuNum()+")";

            errorMessageKey = "pipeline.message.priceErrFoundInvoiceDist";
            exceptionCount++;
        }
      }
    } else if (RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.HOLD_ALL.equals(priceModel)) {
    	//delt with at header level
    } else { // use exception type as default
          // if(RefCodeNames.INVOICE_LOADING_PRICE_MODEL_CD.EXCEPTION.equals(priceModel)){
      if (cwReqCost != null && cwCostMatch != null) {
        if (cwReqCost.compareTo(cwCostMatch) != 0) {
            String message = "Distributor price model is set to: "
                  + distributor
                      .getInvoiceLoadingPriceModel()
                  + " and the cost on the invoice does not match the pre-determined cost for item: "
                  + invDetailD.getDistItemSkuNum()
                  + " ("+invDetailD.getItemSkuNum()+")"
                  + " (requested,pre-determined) ("
                  + cwReqCost + "," + cwCostMatch + ")";
            pBaton.addError(message);
            OrderDAO.enterOrderProperty(mCon,RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,"Invoice Error",
                message.toString(),invoice.getOrderId(),invDetailD.getOrderItemId(),
                invoice.getInvoiceDistId(),invDetailD.getInvoiceDistDetailId(),0,0,0,"System");

          exceptionCount++;
        }
      }
    }
    log.info("Setting adjusted cost to: " + cost);
    invDetailD.setAdjustedCost(cost);

    if (exceptionCount > 0 && Utility.isSet(errorMessageKey)) {
    // add a button error and add error to order property


		      pBaton.addError(errorMessageKey,
           		  percentDiff.toString(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.PRICE,
	       		  invDetailD.getDistItemSkuNum(),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
	       		  Integer.toString(invDetailD.getItemSkuNum()),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER);

           OrderDAO.enterOrderProperty(mCon,RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,"Invoice Error",
                errorMessage,invoice.getOrderId(),
                invDetailD.getOrderItemId(),invoice.getInvoiceDistId(),
                invDetailD.getInvoiceDistDetailId(),0,0,0,"System",
                errorMessageKey,
                percentDiff.toString(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.PRICE,
	       		invDetailD.getDistItemSkuNum(),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
	       		Integer.toString(invDetailD.getItemSkuNum()),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER);


    }

    return exceptionCount;
  }
}
