/*
 * InvoiceDistTaxAnalysis.java
 *
 * Created on Sept 13, 2005, 3:21 PM
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TaxUtilAvalara;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDescData;
import com.cleanwise.service.api.value.TaxQuery;
import com.cleanwise.view.forms.StoreVendorInvoiceDetailForm;

import java.util.Iterator;

import java.sql.Connection;
import java.sql.SQLException;
import java.math.BigDecimal;

import org.apache.log4j.*;

//packages needed for using AvaTax software from Avalara: Begin
import com.avalara.avatax.services.tax.TaxSvc;
import com.avalara.avatax.services.tax.TaxSvcLocator;
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
//packages needed for using AvaTax software from Avalara: End

/**
 *Analyzes the distributor invoice against the tax charged.
 * @author bstevens
 */
public class InvoiceDistTaxAnalysis implements InvoiceDistPipeline{
    private static final BigDecimal ZERO = new BigDecimal(0.00);
	private static final Logger log = Logger.getLogger(InvoiceDistTaxAnalysis.class);
	
    Connection mCon;

    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        try{
          mCon = pCon;
            if(pBaton.getInvoiceDistData().getSalesTax() != null){
                pBaton.setSingularProperty(pBaton.getInvoiceDistData().getSalesTax().toString(), RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_TAX, pCon, true);
            }

            DistributorData dist = pBaton.getDistributorForInvoice(pFactory);
            if(RefCodeNames.EXCEPTION_ON_TAX_VALUE.NO.equals(dist.getExceptionOnTaxDifference())){
              return;
            }
            //if this is an approved invoice then there are no errors possible
            if(pBaton.isInvoiceApproved()){
                return;
            }
            InvoiceDistData invoice = pBaton.getInvoiceDistData();
            OrderData order = pBaton.getOrder();
            if(order == null){
                //something else will log this as an error
                return;
            }

            //match the order items
            BigDecimal taxable = getTaxableSubTotal(pBaton.getInvoiceDistDetailDataVector());
            taxable = taxable.setScale(2, BigDecimal.ROUND_HALF_UP); // new for AvaTax
            log.info("taxable = " + taxable);
            if(taxable == null){
              taxable=invoice.getSubTotal();
            }

            //BigDecimal calcTax = TaxUtil.calculateTax(pCon,new TaxQuery(taxable,order.getSiteId(), order.getAccountId(), invoice.getStoreId())); //old code
            
            ///////////////////////////// AvaTax new code: Begin
            
            Order orderEjb=pFactory.getOrderAPI();
            boolean taxableOrder = orderEjb.isTaxableOrder(invoice.getStoreId(), order.getAccountId(), order.getSiteId());
            log.info("taxableOrder = " + taxableOrder);
            
            //BigDecimal calcTax = ZERO;
            BigDecimal calcTax = null;
            //calcTax = calcTax.setScale(2, BigDecimal.ROUND_HALF_UP);
            
            AddressData originAddress = AddressData.createValue();
            AddressData shippingAddress = AddressData.createValue();
            
     	    //find the Origin Address and the Shipping Address for the Items, located in the Distributor(Vendor) Invoice
            if (null != invoice && taxableOrder) {  // Invoice Object is NOT empty and processed Order is taxable
            	log.info("invoice = " + invoice);
                int distId = dist.getBusEntity().getBusEntityId(); // find Distributor
                log.info("distId = " + distId);

                AddressData distPrimaryAddress = dist.getPrimaryAddress();
                log.info("distPrimaryAddress = " + distPrimaryAddress);
                
            	shippingAddress.setAddress1(invoice.getShipToAddress1());     
            	shippingAddress.setAddress2(invoice.getShipToAddress2());   
            	shippingAddress.setAddress3(invoice.getShipToAddress3());   
            	shippingAddress.setAddress4(invoice.getShipToAddress4());   
            	shippingAddress.setCity(invoice.getShipToCity());
            	shippingAddress.setStateProvinceCd(invoice.getShipToState()); 
            	shippingAddress.setPostalCode(invoice.getShipToPostalCode()); 
            	shippingAddress.setCountryCd(invoice.getShipToCountry());
            	
                originAddress.setAddress1(distPrimaryAddress.getAddress1());
                originAddress.setAddress2(distPrimaryAddress.getAddress2());
                originAddress.setAddress3(distPrimaryAddress.getAddress3());
                originAddress.setAddress4(distPrimaryAddress.getAddress4());
                originAddress.setCity(distPrimaryAddress.getCity());
                originAddress.setStateProvinceCd(distPrimaryAddress.getStateProvinceCd());
                originAddress.setPostalCode(distPrimaryAddress.getPostalCode());
                originAddress.setCountryCd(distPrimaryAddress.getCountryCd());   
	
                log.info("Shipping Address final before calling Avalara = " + shippingAddress);
                log.info("Origin Address final before calling Avalara = " + originAddress);                        
            
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
                    //throw new PipelineException("InvoiceDistTaxAnalysis::AvaTax Invoice tax calculation failed!");                              
                }
            }
            ///////////////////////////// AvaTax new code: End
            
            BigDecimal recvTax = invoice.getSalesTax(); 
            if(calcTax == null){
                calcTax = ZERO;
            }
            if(recvTax == null){
                recvTax = ZERO;
            }
            calcTax = calcTax.abs();
            recvTax = recvTax.abs();

            if(calcTax.subtract(recvTax).abs().doubleValue() > .05){ //allow for up to a 5 cent discrepancy
                //String message = "Tax charged ("+recvTax.toString()+") does not match calculated tax ("+calcTax.toString()+").";
                pBaton.addError("pipeline.message.notMatchedTaxCharged",
                                recvTax.toString(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                calcTax.toString(),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                null, null);
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

    protected static void printMessages(ArrayOfMessage messages)
    {
        for (int ii = 0; ii < messages.size(); ii++)
        {
            Message message = messages.getMessage(ii);
            log.info(message.getSeverity().toString() + " " + ii + ": " + message.getSummary());
        }

    }
    
    /**
     *Returns the taxable subtotal for the invoice.  If an item matches to an order item it compares the taxable
     *status of the order item.  If not it assumes that the item is taxable.
     */
    private BigDecimal getTaxableSubTotal(InvoiceDistDetailDataVector pInvItems){
        BigDecimal subTotal = ZERO;
        Iterator it = pInvItems.iterator();
        while(it.hasNext()){
          InvoiceDistDetailData itm = (InvoiceDistDetailData) it.next();
          OrderItemData oid = null;
          if(itm.getOrderItemId() != 0){
            try{
              oid = OrderItemDataAccess.select(mCon,itm.getOrderItemId());
            }catch(Exception e){
              log.info("Error no order item found for invoice dist detail id: "+itm.getInvoiceDistDetailId());
            }
          }

          if(oid == null || Utility.isTaxableOrderItem(oid)) {
                BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(itm);
                amt = amt.multiply(new BigDecimal(itm.getDistItemQuantity()));
                subTotal = Utility.addAmt(subTotal, amt);
          }
        }
        return subTotal;
    }

}
