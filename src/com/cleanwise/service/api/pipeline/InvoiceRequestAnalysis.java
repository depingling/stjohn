package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import javax.naming.NamingException;
import java.sql.Connection;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.log4j.Logger;

//packages needed for using Avatax software from Avalara: Begin
import com.avalara.avatax.services.tax.*;
import com.avalara.avatax.services.base.Profile;
import com.avalara.avatax.services.base.Security;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Date;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
//packages needed for using Avatax software from Avalara: End

/**
 * Title:        InvoiceRequestAnalysis
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         23.04.2007
 * Time:         16:53:24
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class InvoiceRequestAnalysis implements InvoiceRequestPipeline {

    private static final Logger log = Logger.getLogger(InvoiceRequestAnalysis.class);

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton, Connection pCon, APIAccess pFactory) throws PipelineException {

        if (!(pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0)) {
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
            return pBaton;
        }

        try {
            int exceptionCount;
            exceptionCount = fileDCCAnalisys(pBaton.getInvoiceRequest(), pBaton.getDistributor(), pBaton.getNotesToLog(), pBaton.getMiscInvoiceNotes());
            pBaton.addExceptionCount(exceptionCount);

            exceptionCount=controlTotalSumAnalisys(pBaton.getInvoiceRequest(), pBaton.getNotesToLog(), pBaton.getMiscInvoiceNotes());
            pBaton.addExceptionCount(exceptionCount);

            exceptionCount=invoiceDistTaxAnalysis(pCon, pFactory,
                    pBaton.getInvoiceRequest().getInvoiceD(),
                    pBaton.getInvoiceRequest().getInvoiceDetailDV(),
                    pBaton.getOrder(),
                    pBaton.getPOItems(),
                    pBaton.getDistributor(),
                    pBaton.getItemNotesToLog(),
                    pBaton.getNotesToLog());
            pBaton.addExceptionCount(exceptionCount);
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
            pBaton.addError(pCon,
                            InvoiceRequestPipelineBaton.UNKNOWN_ERROR,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                            0, 0, e.getMessage());
            throw new PipelineException(e.getMessage());
        } finally {

        }
        return pBaton;
    }

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton) throws PipelineException, NamingException {
        try{
            
            if (!(pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0)) {
                pBaton.setWhatNext(PipelineBaton.GO_NEXT);
                return pBaton;
            }

            int exceptionCount;
            exceptionCount = fileDCCAnalisys(pBaton.getInvoiceRequest(), pBaton.getDistributor(), pBaton.getNotesToLog(), pBaton.getMiscInvoiceNotes());
            pBaton.addExceptionCount(exceptionCount);

            exceptionCount=controlTotalSumAnalisys(pBaton.getInvoiceRequest(), pBaton.getNotesToLog(), pBaton.getMiscInvoiceNotes());
            pBaton.addExceptionCount(exceptionCount);

            exceptionCount=invoiceDistTaxAnalysis(APIAccess.getAPIAccess(),
                    pBaton.getInvoiceRequest().getInvoiceD(),
                    pBaton.getInvoiceRequest().getInvoiceDetailDV(),
                    pBaton.getOrder(),
                    pBaton.getPOItems(),
                    pBaton.getDistributor(),
                    pBaton.getItemNotesToLog(),
                    pBaton.getNotesToLog());
            pBaton.addExceptionCount(exceptionCount);
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
             pBaton.incExceptionCount();
            throw new PipelineException(e.getMessage());
        } finally {

        }
        return pBaton;
    }

    public int invoiceDistTaxAnalysis(APIAccess factory,
                                      InvoiceDistData invoice,
                                      InvoiceDistDetailDataVector invoiceDistDetailDV,
                                      OrderData orderData, OrderItemDataVector poItems,
                                      DistributorData distr, List itemNotesToLog, List notesToLog) throws Exception {
        int exceptionCount = 0;


        try {

            IntegrationServices intServEjb = factory.getIntegrationServicesAPI();
            Order orderEjb=factory.getOrderAPI();
            PurchaseOrder poEjb=factory.getPurchaseOrderAPI();


            if (invoice == null) throw new Exception("Invoice dist data is null");
            if (invoiceDistDetailDV == null) throw new Exception("Invoice data detail data collection  is null");
            if (orderData == null) throw new Exception("Order Data  is null");
            if (poItems == null) throw new Exception("Order item collection is null");

            String exceptionOnTaxDifference = RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES; //exceptionOnTaxDifference = "true"
            if (distr != null) {
                exceptionOnTaxDifference = distr.getExceptionOnTaxDifference();
            }
            if (RefCodeNames.EXCEPTION_ON_TAX_VALUE.NO.equals(exceptionOnTaxDifference)) {
                return 0;
            }
            
            log.info("QQQQQ exceptionOnTaxDifference = " + exceptionOnTaxDifference);
            
            ///////////////////////////////////////////////////////////////////
            // gets approved invoice flag
            // if this is an approved invoice then there are no errors possible
            ///////////////////////////////////////////////////////////////////
            if(orderEjb.getApprovedInvoiceFlag(invoice.getInvoiceDistId()))
            {
                return 0;
            }
            ////////////////////////////////////////////////////////////////////////////
            //Gets the taxable subtotal for the invoice.
            //If an item matches an order item it compares the taxable
            //status of the order item.  If not it assumes that the item is taxable.
            /////////////////////////////////////////////////////////////////////////////
            BigDecimal subTotal = new BigDecimal(0);
            BigDecimal subTotalResaleItems = new BigDecimal(0); 
            boolean homoGenResaleOrderFl = true; //
            int nonResaleCnt = 0;
            int resaleCnt = 0;
            Iterator it = invoiceDistDetailDV.iterator();
            while (it.hasNext()) {
                InvoiceDistDetailData invDistDetailData = (InvoiceDistDetailData) it.next();
                OrderItemData oid;
                try {
                    invDistDetailData.setInvoiceDistId(invoice.getInvoiceDistId());
                    invDistDetailData.setDistItemQtyReceived(invDistDetailData.getDistItemQuantity());
                    oid = intServEjb.matchDistributorInvoiceItem(invDistDetailData, itemNotesToLog,
                            notesToLog, poItems, orderData);
                } catch (Exception e) {
                    log.info("Error no order item found." + e.getMessage());
                    return 1;
                }
                if(oid==null) {
                    log.info("Error no order item found. DistSku " + invDistDetailData.getDistItemSkuNum());
                    return 1;
                }
                if (!RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd()) || oid.getSaleTypeCd().equals(null) || oid.getSaleTypeCd().trim().equals("")) {
                	// non-re-sale Order item (taxable)
                	nonResaleCnt += 1;
                } else {
                	// re-sale Order item (non-taxable)
                	resaleCnt += 1;
                }
                
                if (homoGenResaleOrderFl) { // do I still need this 5 lines of code ?
                    if (!RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd())) {
                        homoGenResaleOrderFl = false;
                    }
                }
                /*** old code for subTotal calculations: Begin
                if (!RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd())
                        || (RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd())
                        && RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES_FOR_RESALE.equals(exceptionOnTaxDifference))) {
                    {
                        BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(invDistDetailData);
                        amt = amt.multiply(new BigDecimal(invDistDetailData.getDistItemQuantity()));
                        subTotal = Utility.addAmt(subTotal, amt);
                    }
                }
                old code for subTotal calculations: End ***/
                
                if (!RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd())) {
                    BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(invDistDetailData);
                    amt = amt.multiply(new BigDecimal(invDistDetailData.getDistItemQuantity()));
                    subTotal = Utility.addAmt(subTotal, amt);
                }
            } // while (it.hasNext())

            ////////////////////////////////////////////////////////////////////////////
            
            log.info("PPPPPPP subTotal = " + subTotal);
            log.info("RRRRRRR subTotalResaleItems = " + subTotalResaleItems);
            log.info("NNNNNNN homoGenResaleOrderFl = " + homoGenResaleOrderFl);
            
            log.info("nonResaleCnt = " + nonResaleCnt);
            log.info("resaleCnt = " + resaleCnt);
            
            boolean allNonResaleItems = false; //init flag
            boolean mixResaleNonresaleItems = false; //init flag
            boolean allResaleItems = false; //init flag
            
            if (nonResaleCnt > 0 && resaleCnt == 0) {
            	//ALL items in the Invoice are non-re-sale (taxable)
            	allNonResaleItems = true;
            }            
            if (nonResaleCnt > 0 && resaleCnt > 0) {
            	//mixture of re-sale and non-re-sale items in the Invoice
            	mixResaleNonresaleItems = true;
            }
            if (nonResaleCnt == 0 && resaleCnt > 0) {
            	//ALL items in the Invoice are re-sale (non-taxable)
            	allResaleItems = true;
            }            
            
            log.info("allNonResaleItems = " + allNonResaleItems);
            log.info("mixResaleNonresaleItems = " + mixResaleNonresaleItems);
            log.info("allResaleItems = " + allResaleItems);
                                                
            BigDecimal taxable = subTotal;
            if (taxable == null) {
                taxable = invoice.getSubTotal();
            }


            //BigDecimal calcTax = orderEjb.calculateTax(new TaxQuery(taxable, orderData.getSiteId(), orderData.getAccountId(), invoice.getStoreId())); //old code
            
            
            
            ////////////////////// AvaTax new code: Begin            

            boolean taxableOrder = orderEjb.isTaxableOrder(invoice.getStoreId(), orderData.getAccountId(), orderData.getSiteId());
            log.info("invoiceDistTaxAnalysis method() (8 parameters) taxableOrder = " + taxableOrder);
            
            BigDecimal calcTax = null;   
            
            if (taxableOrder && (subTotal != new BigDecimal(0)) && (allNonResaleItems || mixResaleNonresaleItems)) { //(Order is taxable) AND (subTotal != 0) AND (ALL items are NOT re-sale items)
               log.info("invoice = " + invoice);
               int distId = distr.getBusEntity().getBusEntityId(); // find Distributor
               log.info("distId = " + distId);

               AddressData distPrimaryAddress = distr.getPrimaryAddress();
               log.info("distPrimaryAddress = " + distPrimaryAddress);

               AddressData originAddress = new AddressData();
               AddressData shippingAddress = new AddressData();
               
               log.info("originAddress after initiation = " + originAddress);  
               log.info("shippingAddress after initiation = " + shippingAddress); 
               
     	       //find the Origin Address and the Shipping Address for the Distributor(Vendor) Invoice Items 
               //invoice != null: check for invoice = null was done in the beginning of the invoiceDistTaxAnalysis() method
            
               shippingAddress.setAddress1(invoice.getShipToAddress1());     
               shippingAddress.setAddress2(invoice.getShipToAddress2());   
               shippingAddress.setAddress3(invoice.getShipToAddress3());   
               shippingAddress.setAddress4(invoice.getShipToAddress4());   
               shippingAddress.setCity(invoice.getShipToCity());
               shippingAddress.setStateProvinceCd(invoice.getShipToState());
               shippingAddress.setPostalCode(invoice.getShipToPostalCode());
               shippingAddress.setCountryCd(invoice.getShipToCountry());
            
               /*** USE THE "PRIMARY CONTACT" ORIGIN ADDRESS ***/
               originAddress.setAddress1(distPrimaryAddress.getAddress1());
               originAddress.setAddress2(distPrimaryAddress.getAddress2());
               originAddress.setAddress3(distPrimaryAddress.getAddress3());
               originAddress.setAddress4(distPrimaryAddress.getAddress4());
               originAddress.setCity(distPrimaryAddress.getCity());
               originAddress.setStateProvinceCd(distPrimaryAddress.getStateProvinceCd());
               originAddress.setPostalCode(distPrimaryAddress.getPostalCode());
               originAddress.setCountryCd(distPrimaryAddress.getCountryCd());  
               /************************************************/

               // piece below works correct, but the ORIGIN address is NOT full; 
               // tax calculation can potentially produce different result 
               /***
               originAddress.setAddress1(invoice.getShipFromAddress1());
               originAddress.setAddress2(invoice.getShipFromAddress2());
               originAddress.setAddress3(invoice.getShipFromAddress3());
               originAddress.setAddress4(invoice.getShipFromAddress4());
               originAddress.setCity(invoice.getShipFromCity());
               originAddress.setStateProvinceCd(invoice.getShipFromState());
               originAddress.setPostalCode(invoice.getShipFromPostalCode());
               originAddress.setCountryCd(invoice.getShipFromCountry()); 
               ***/  


               log.info("Shipping Address final before calculating sales tax = " + shippingAddress);
               log.info("Origin Address final before calculating sales tax = " + originAddress);
            
               GetTaxResult getTaxResult = TaxUtilAvalara.calculateAvatax(originAddress, shippingAddress, taxable);
               if (getTaxResult.getResultCode() == SeverityLevel.Success)
               {
                  //log.info("DocCode: " + getTaxRequest.getDocCode());
                  // DocId is generated by AvaTax
                  log.info("DocId: " + getTaxResult.getDocId());
                  log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
                  log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
                  calcTax = getTaxResult.getTotalTax();
                  calcTax = calcTax.setScale(2, BigDecimal.ROUND_HALF_UP);
                  log.info("AvaTax calculated Invoice tax successfully!");
               }
               else
               {
         	      printMessages(getTaxResult.getMessages());
                  log.info("AvaTax Invoice tax calculation failed!");
                  throw new PipelineException("InvoiceRequestAnalysis::InvoiceDistTaxAnalysis() => AvaTax Invoice tax calculation failed!");                              
               }
            } //(if taxableOrder) 
            
            ///////////////////////////// AvaTax new code: End
            
            
            BigDecimal recvTax = invoice.getSalesTax();

            if (calcTax == null) {
                calcTax = new BigDecimal(0);
            }
            if (recvTax == null) {
                recvTax = new BigDecimal(0);
            }

            calcTax = calcTax.abs();
            recvTax = recvTax.abs();

            BigDecimal orderAmount =poEjb.getTotalAmountWhithALLOperations(poItems, orderData);
            BigDecimal invoiceAmount = invoice.getSubTotal();

            calcTax = calcTax.abs();
            recvTax = recvTax.abs();
            double taxDiff = calcTax.subtract(recvTax).abs().doubleValue();
            
            /*** below we'll compare received(from Invoice) and calculated sales taxes ***/

            /*** old code: Begin
            if (taxDiff > 0.01) { //allow for up to a 5 cent discrepancy
                String mess = "Tax charged (" + recvTax.toString() + ") does not match calculated tax (" + calcTax.toString() + ").";
                notesToLog.add(mess);
            }


            if (homoGenResaleOrderFl) {
                if (!Utility.isZeroValue(calcTax)) {
                    exceptionCount++;
                    String mess = "Tax Order (" + calcTax.toString() + ") does not match 0";
                    notesToLog.add(mess);
                }
                if (!Utility.isZeroValue(recvTax)) {
                    exceptionCount++; 
                    String mess = "Tax Invoice (" + recvTax.toString() + ") does not match 0";
                    notesToLog.add(mess);
                }
            }
            old code: End ***/
            
            if (allNonResaleItems) {
            	if (RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES_FOR_RESALE.equals(exceptionOnTaxDifference)){
                	// Distributor property value = "yes for resale"
            		// do nothing - NO ERROR MESSAGE with ANY discrepancy between calculated and received taxes
                }
            	if (RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES.equals(exceptionOnTaxDifference)){
                   	// Distributor property value = "yes"
                    if (taxDiff > 0.01) { //allow for up to a 5 cents discrepancy
                        String mess = "Tax charged (" + recvTax.toString() + ") does not match calculated tax (" + calcTax.toString() + ").";
                        notesToLog.add(mess);
                    }
            	}
            }
            if(mixResaleNonresaleItems){
               if (taxDiff > 0.01) { //allow for up to a 5 cents discrepancy
            	    // Distributor property value = "yes" OR "yes for resale"
                    String mess = "Tax charged (" + recvTax.toString() + ") does not match calculated tax (" + calcTax.toString() + ").";
                    notesToLog.add(mess);
               }
            }
            if (allResaleItems){
        	    // Distributor property value = "yes" OR "yes for resale"
                if (!Utility.isZeroValue(recvTax)) {
                    exceptionCount++; 
                    String mess = "Tax Invoice (" + recvTax.toString() + ") does not match 0";
                    notesToLog.add(mess);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            notesToLog.add("Error during sales tax check");
        }
        return exceptionCount;
    }


    private int controlTotalSumAnalisys(InvoiceRequestData pInvoiceReq, ArrayList notesToLog, Map miscInvoiceNotes) {

        int exceptionCount=0;
        //control total sum
         InvoiceRequestPipelineBaton.addListValueToMap(miscInvoiceNotes, 
        		 RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_TOTAL, 
        		 pInvoiceReq.getControlTotalSum().toString());
         
         java.math.BigDecimal freight = pInvoiceReq.getInvoiceD().getFreight();
         if(Utility.isSet(freight)){
         InvoiceRequestPipelineBaton.addListValueToMap(miscInvoiceNotes, 
        		 RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_FREIGHT , 
        		 freight.toString());
         }
         
         java.math.BigDecimal salesTax = pInvoiceReq.getInvoiceD().getSalesTax();
         if(Utility.isSet(salesTax)){
         InvoiceRequestPipelineBaton.addListValueToMap(miscInvoiceNotes, 
        		 RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_TAX , 
        		 salesTax.toString());
         }
         java.math.BigDecimal miscCharges = pInvoiceReq.getInvoiceD().getMiscCharges();
         if(Utility.isSet(miscCharges)){
         InvoiceRequestPipelineBaton.addListValueToMap(miscInvoiceNotes, 
        		 RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_MISC_CHARGE , 
        		 miscCharges.toString());
         }
         java.math.BigDecimal discount = Utility.toNegative(pInvoiceReq.getInvoiceD().getDiscounts());
         if(Utility.isSet(discount)){
         InvoiceRequestPipelineBaton.addListValueToMap(miscInvoiceNotes, 
        		 RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_DISCOUNT , 
        		 discount.toString());
         }
        //Too many errors in invoices. We can't handle all of them. YK use just store 1
        if (pInvoiceReq.getInvoiceD().getStoreId() == 1 && pInvoiceReq.getInvoiceD().getSubTotal() != null && pInvoiceReq.getControlTotalSum() != null) {
            BigDecimal diff = pInvoiceReq.getInvoiceD().getSubTotal().subtract(pInvoiceReq.getControlTotalSum()).abs();
            if (diff.doubleValue() >= 0.01) {
                String str = "The calculated total of the invoice does not match " +
                        "to the received check total. The difference is " + diff +
                        "(received=" + pInvoiceReq.getInvoiceD().getSubTotal() + " calc=" + pInvoiceReq.getControlTotalSum() + ")";
                notesToLog.add(str);
                exceptionCount += 1;
            }
        }
        return exceptionCount;
    }

    private int fileDCCAnalisys(InvoiceRequestData pInvoiceReq, DistributorData distributor, ArrayList notesToLog, Map miscInvoiceNotes) throws Exception {

        int exceptionCount = 0;
        String fileDcc = pInvoiceReq.getDistributorCompanyCode();
        if (Utility.isSet(fileDcc) && distributor != null) {
            fileDcc = fileDcc.trim();
            if (Utility.isSet(distributor.getDistributorsCompanyCode())) {
                String dbDcc = distributor.getDistributorsCompanyCode().trim();
                if (!fileDcc.equals(dbDcc)) {
                    notesToLog.add("distributor company code in file (" + fileDcc + ") does not match " +
                                   "company code for this distributor (" + dbDcc + ")");
                    exceptionCount += 1;
                }
            }
        }

        if (Utility.isSet(fileDcc)) {
            InvoiceRequestPipelineBaton.addListValueToMap(miscInvoiceNotes, RefCodeNames.PROPERTY_TYPE_CD.DISTRIBUTORS_COMPANY_CODE, fileDcc);
        }

        return exceptionCount;
    }


    public int invoiceDistTaxAnalysis(Connection conn, APIAccess factory,
                                      InvoiceDistData invoice,
                                      InvoiceDistDetailDataVector invoiceDistDetailDV,
                                      OrderData orderData, OrderItemDataVector poItems,
                                      DistributorData distr, List itemNotesToLog, List notesToLog) throws Exception {
        int exceptionCount = 0;


        try {

            IntegrationServices intServEjb = factory.getIntegrationServicesAPI();

            Order orderEjb=factory.getOrderAPI();
            
            if (invoice == null) throw new Exception("Invoice dist data is null");
            if (invoiceDistDetailDV == null) throw new Exception("Invoice data detail data collection  is null");
            if (orderData == null) throw new Exception("Order Data  is null");
            if (poItems == null) throw new Exception("Order item collection is null");

            String exceptionOnTaxDifference = RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES;
            if (distr != null) {
                exceptionOnTaxDifference = distr.getExceptionOnTaxDifference();
            }
            
            log.info("exceptionOnTaxDifference = " + exceptionOnTaxDifference);
            
            if (RefCodeNames.EXCEPTION_ON_TAX_VALUE.NO.equals(exceptionOnTaxDifference)) {
                return 0;
            }
            ///////////////////////////////////////////////////////////////////
            // gets  approved invoice flag
            // if this is an approved invoice then there are no errors possible
            ///////////////////////////////////////////////////////////////////
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(OrderPropertyDataAccess.INVOICE_DIST_ID, invoice.getInvoiceDistId());
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_TYPE_CD, RefCodeNames.ORDER_PROPERTY_TYPE_CD.INVOICE_DIST_APPROVED);
            crit.addEqualTo(OrderPropertyDataAccess.ORDER_PROPERTY_STATUS_CD, RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            OrderPropertyDataVector values = OrderPropertyDataAccess.select(conn, crit);
            if (values != null) {
                if (values.size() > 0) {
                    OrderPropertyData prop = (OrderPropertyData) values.get(0);
                    if (prop != null && Utility.isTrue(prop.getValue())) {
                        return 0;
                    }
                }
            }
            ////////////////////////////////////////////////////////////////////////////
            //Gets the taxable sub total for the invoice.
            //If an item matches to an order item it compares the taxable
            //status of the order item.  If not it assumes that the item is taxable.
            /////////////////////////////////////////////////////////////////////////////
            BigDecimal subTotal = new BigDecimal(0);
            boolean homoGenResaleOrderFl = true; //
            Iterator it = invoiceDistDetailDV.iterator();
            while (it.hasNext()) {
                InvoiceDistDetailData invDistDetailData = (InvoiceDistDetailData) it.next();
                OrderItemData oid;
                try {
                    invDistDetailData.setInvoiceDistId(invoice.getInvoiceDistId());
                    invDistDetailData.setDistItemQtyReceived(invDistDetailData.getDistItemQuantity());
                    oid = intServEjb.matchDistributorInvoiceItem(invDistDetailData, itemNotesToLog,
                            notesToLog, poItems, orderData);
                } catch (Exception e) {
                    log.info("Error no order item found." + e.getMessage());
                    return 1;
                }

                if (homoGenResaleOrderFl) {
                    if (!RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd())) {
                        homoGenResaleOrderFl = false;
                    }
                }

                if (!RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd())
                        || (RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE.equals(oid.getSaleTypeCd())
                        && RefCodeNames.EXCEPTION_ON_TAX_VALUE.YES_FOR_RESALE.equals(exceptionOnTaxDifference))) {
                    {
                        BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(invDistDetailData);
                        amt = amt.multiply(new BigDecimal(invDistDetailData.getDistItemQuantity()));
                        subTotal = Utility.addAmt(subTotal, amt);
                    }
                }
            }

            ////////////////////////////////////////////////////////////////////////////

            log.info("subTotal = " + subTotal);
            
            BigDecimal taxable = subTotal;
            if (taxable == null) {
                taxable = invoice.getSubTotal();
            }


            //BigDecimal calcTax = TaxUtil.calculateTax(conn, new TaxQuery(taxable, orderData.getSiteId(), orderData.getAccountId(), invoice.getStoreId())); //old tax code
            
            ////////////////////// AvaTax new code: Begin 
            
            boolean taxableOrder = orderEjb.isTaxableOrder(invoice.getStoreId(), orderData.getAccountId(), orderData.getSiteId());
            log.info("invoiceDistTaxAnalysis method() (9 parameters) taxableOrder = " + taxableOrder);
            
            //BigDecimal calcTax = new BigDecimal(0);
            BigDecimal calcTax = null;
            
         if (taxableOrder) {
            int distId = distr.getBusEntity().getBusEntityId(); // find Distributor
            log.info("distId = " + distId);

            AddressData distPrimaryAddress = distr.getPrimaryAddress();
            log.info("distPrimaryAddress = " + distPrimaryAddress);

            AddressData originAddress = new AddressData();
            AddressData shippingAddress = new AddressData();
            
     	    //find the Origin Address and the Shipping Address for the Distributor Invoice Items
            //invoice != null: check for invoice = null was done in the beginning of the invoiceDistTaxAnalysis() method
                        	
            shippingAddress.setAddress1(invoice.getShipToAddress1());     
            shippingAddress.setAddress2(invoice.getShipToAddress2());   
            shippingAddress.setAddress3(invoice.getShipToAddress3());   
            shippingAddress.setAddress4(invoice.getShipToAddress4());   
            shippingAddress.setCity(invoice.getShipToCity());
            shippingAddress.setStateProvinceCd(invoice.getShipToState());
            shippingAddress.setPostalCode(invoice.getShipToPostalCode());
            shippingAddress.setCountryCd(invoice.getShipToCountry());
            
            /// changed piece of code 
            /*** USE THE "PRIMARY CONTACT" ORIGIN ADDRESS ***/
            originAddress.setAddress1(distPrimaryAddress.getAddress1());
            originAddress.setAddress2(distPrimaryAddress.getAddress2());
            originAddress.setAddress3(distPrimaryAddress.getAddress3());
            originAddress.setAddress4(distPrimaryAddress.getAddress4());
            originAddress.setCity(distPrimaryAddress.getCity());
            originAddress.setStateProvinceCd(distPrimaryAddress.getStateProvinceCd());
            originAddress.setPostalCode(distPrimaryAddress.getPostalCode());
            originAddress.setCountryCd(distPrimaryAddress.getCountryCd());  
            /************************************************/            

            log.info("Shipping Address = " + shippingAddress);
            log.info("Origin Address = " + originAddress);
            
            GetTaxResult getTaxResult = TaxUtilAvalara.calculateAvatax(originAddress, shippingAddress, taxable);
            if (getTaxResult.getResultCode() == SeverityLevel.Success)
            {
                //log.info("DocCode: " + getTaxRequest.getDocCode());
                // DocId is generated by AvaTax
                log.info("DocId: " + getTaxResult.getDocId());
                log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
                log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
                calcTax = getTaxResult.getTotalTax();
                calcTax = calcTax.setScale(2, BigDecimal.ROUND_HALF_UP);
                log.info("AvaTax calculated Invoice tax successfully!");
            }
            else
            {
         	    printMessages(getTaxResult.getMessages());
                log.info("AvaTax Invoice tax calculation failed!");
                throw new PipelineException("InvoiceRequestAnalysis::InvoiceDistTaxAnalysis() => AvaTax Invoice tax calculation failed!");                              
            }
         } //if(taxableOrder)
         
         ///////////////////////////// AvaTax new code: End
            
            BigDecimal recvTax = invoice.getSalesTax();

            if (calcTax == null) {
                calcTax = new BigDecimal(0);
            }
            if (recvTax == null) {
                recvTax = new BigDecimal(0);
            }

            calcTax = calcTax.abs();
            recvTax = recvTax.abs();

            BigDecimal orderAmount = PipelineCalculationOperations.getTotalAmountWhithALLOperations(conn, poItems, orderData);
            BigDecimal invoiceAmount = invoice.getSubTotal();

            calcTax = calcTax.abs();
            recvTax = recvTax.abs();
            double taxDiff = calcTax.subtract(recvTax).abs().doubleValue();
            
            /*** below we'll compare received and calculated sales tax ***/

            if (taxDiff > 0.01) { //allow for up to a 5 cent discrepancy
                String mess = "Tax charged (" + recvTax.toString() + ") does not match calculated tax (" + calcTax.toString() + ").";
                notesToLog.add(mess);
            }


            if (homoGenResaleOrderFl) {
                if (!Utility.isZeroValue(calcTax)) {
                    exceptionCount++;
                    String mess = "Tax Order (" + calcTax.toString() + ") does not match 0";
                    notesToLog.add(mess);
                }
                if (!Utility.isZeroValue(recvTax)) {
                    exceptionCount++;
                    String mess = "Tax Invoice (" + recvTax.toString() + ") does not match 0";
                    notesToLog.add(mess);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            notesToLog.add("Error during sales tax check");
        }
        return exceptionCount;
    }

    protected static void printMessages(ArrayOfMessage messages)
    {
        for (int ii = 0; ii < messages.size(); ii++)
        {
            Message message = messages.getMessage(ii);
            log.info(message.getSeverity().toString() + " " + ii + ": " + message.getSummary());
        }

    }

}
