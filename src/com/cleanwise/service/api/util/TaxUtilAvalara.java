/*
 * TaxUtilAvalara.java
 *
 * Created on February 17, 2010, 10:49 AM
 *
 */

package com.cleanwise.service.api.util;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import com.cleanwise.service.api.session.*;

// packages needed for using of Avatax software from Avalara: Begin

import com.avalara.avatax.services.tax.*;
import com.avalara.avatax.services.base.Profile;
import com.avalara.avatax.services.base.Security;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.util.Date;

import java.util.HashMap;
import java.util.ArrayList;

//packages needed for using of Avatax software from Avalara: End

/**
 *
 * @author scher
 */
public class TaxUtilAvalara {

	private static final Logger log = Logger.getLogger(TaxUtilAvalara.class);
    public static final BigDecimal ZERO = new BigDecimal(0);

    /**
     *Returns the shipping address.
     */
    public static AddressData getShipTo(Connection pConn,int siteId)
	throws DataNotFoundException, SQLException{

        if(siteId > 0){
            AddressDataVector addrs = BusEntityDAO.getSiteAddresses(pConn, siteId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
            AddressData sAddr;
            if(addrs != null && addrs.size() > 0){
                sAddr = (AddressData) addrs.get(0);
            }else{
                addrs = BusEntityDAO.getSiteAddresses(pConn, siteId, RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
                if(addrs != null && addrs.size() > 0){
                    sAddr = (AddressData) addrs.get(0);
                }else{
                    throw new DataNotFoundException("No address found for site id "+siteId);
				}	
            }
            return sAddr;
        }
        throw new DataNotFoundException("No shipto information specified");
    }

  /**
     *Returns the shipping address..may be constructed and incomplete
     */
    private static AddressData getShipTo(Connection pConn,TaxQuery query) throws TaxCalculationException, SQLException{
        if(query.getShipTo() != null){
            return query.getShipTo();
        }
        if(query.getSite() != null && query.getSite().getSiteAddress() != null){
            return query.getSite().getSiteAddress();
        }
        if(query.getSiteId() > 0){
            int siteId = query.getSiteId();
			try {
                AddressData sAddr = getShipTo(pConn, siteId);
                return sAddr;
			} catch (DataNotFoundException exc) {
                throw new TaxCalculationException("No address found for site id "+siteId);
			}
        }
        throw new TaxCalculationException("No shipto information specified");
    }

    /**
     *From the query data extracts whether or not this site is taxable or not
     */
    private static boolean isSiteTaxable(Connection pConn,TaxQuery query) throws SQLException{
        if(query.getSite() != null){
            if(query.getSite().getTaxableIndicator() != null){
                return Utility.isTrue(query.getSite().getTaxableIndicator().getValue());
            }
            return false;
        }
        if(query.getSiteId() > 0){
            return isBusEntityIdTaxable(pConn,query.getSiteId());
        }
        //default to true.  This should only happen when no site information was specified
        return true;
    }

    /**
     *From the query data extracts whether or not this account is taxable
     */
    private static boolean isAccountTaxable(Connection pConn,TaxQuery query) throws SQLException{
        if(query.getAccount() != null){
            return query.getAccount().isTaxableIndicator();
        }
        if(query.getAccountId() > 0){
            return isBusEntityIdTaxable(pConn,query.getAccountId());
        }
        //default to true.  This should only happen when no account information was specified
        return true;
    }

    /**
     *From the query data extracts whether or not this store is taxable or not
     */
    private static boolean isStoreTaxable(Connection pConn,TaxQuery query) throws SQLException{
        if(query.getStore() != null){
            return query.getStore().isTaxableIndicator();
        }
        if(query.getStoreId() > 0){
            return isBusEntityIdTaxable(pConn,query.getStoreId());
        }
        //default to true.  This should only happen when no store information was specified
        return true;
    }


    /**
     *Figures out if a given bus entity id is taxable
     */
    public static boolean isBusEntityIdTaxable(Connection pConn, int busEntityId) throws SQLException{
        String taxS;
        try{
            PropertyUtil pru = new PropertyUtil(pConn);
            taxS = pru.fetchValueIgnoreMissing(0, busEntityId, RefCodeNames.PROPERTY_TYPE_CD.TAXABLE_INDICATOR);
        }catch(Exception e){
            e.printStackTrace();
            throw new SQLException(e.getMessage()); //rethrow as a sql exception as that is what really happened
        }
        return Utility.isTrue(taxS,true);
    }


    /**
     *Performs tax calculation on on the supplied data.
     */
    public static BigDecimal calculateTax(Connection pConn, TaxQuery query)
    throws SQLException, TaxCalculationException{
    	TaxQueryResponse tr = getTaxQueryResponse(pConn, getShipTo(pConn,query),isSiteTaxable(pConn,query),isAccountTaxable(pConn,query),isStoreTaxable(pConn,query));
        return tr.calculateTax(query.getAmount());
    }

    /**
     *Returns an initialized TaxQueryResponse object that may be used for multiple
     *calculations
    */
    public static TaxQueryResponse getTaxQueryResponse(Connection pConn, TaxQuery query)
    throws SQLException, TaxCalculationException{
    	return getTaxQueryResponse(pConn, getShipTo(pConn,query),isSiteTaxable(pConn,query),isAccountTaxable(pConn,query),isStoreTaxable(pConn,query));
    }

    /**
     *Performs tax calculation on on the supplied data.
     */
    private static TaxQueryResponse getTaxQueryResponse(Connection pConn, AddressData pShippingAddr, boolean pSiteTaxableIndicator,boolean pAcctTaxableIndicator, boolean pStoreTaxableIndicator)
    throws SQLException, TaxCalculationException{

        String logStr = "Calculating sales tax";
        if(   !pSiteTaxableIndicator
	   || !pAcctTaxableIndicator
	   || !pStoreTaxableIndicator){
            logStr +=
		" BUS_ENTTITY_ID=" + pShippingAddr.getBusEntityId() +
		" something was tax exempt, flags " +
		", site="+pSiteTaxableIndicator +
		", acct="+pAcctTaxableIndicator +
		", store="+pStoreTaxableIndicator ;
            log.info("TaxQueryResponse getTaxQueryResponse " +
			       logStr);
            return new TaxQueryResponse(new BigDecimal(0.00));
        }

        //DBCriteria crit = new DBCriteria();
       // crit.addEqualTo(CityPostalCodeDataAccess.POSTAL_CODE,Utility.normalizePostalCode(pShippingAddr.getPostalCode()));
       // //could add in cirt criteria state etc.  See the @see AddressValidatorBean for  examples as there
       // //are certain gotchas with this data (like city length limits).
       // CityPostalCodeDataVector postalCodes = CityPostalCodeDataAccess.select(pConn,crit,1); //just get one as there are known dup zipcodes
       // if(postalCodes.isEmpty()){
       //     throw new TaxCalculationException("Could not find tax for postal code: "+pShippingAddr.getPostalCode());
       // }
       // CityPostalCodeData pc = (CityPostalCodeData) postalCodes.get(0);
       // if(pc.getTaxRate() == null){
       //     throw new TaxCalculationException("Tax Rate is not set for postal code: "+pShippingAddr.getPostalCode());
       // }
       // BigDecimal taxRate=pc.getTaxRate();
        //and then simply perform the simple calculation
        return new TaxQueryResponse(new BigDecimal(0.00));


    }

    public static BigDecimal getTaxRate(Connection pCon, AddressData pShippingAddr) throws TaxCalculationException, SQLException {

       // DBCriteria crit = new DBCriteria();
       // crit.addEqualTo(CityPostalCodeDataAccess.POSTAL_CODE, Utility.normalizePostalCode(pShippingAddr.getPostalCode()));
       // //could add in cirt criteria state etc.  See the @see AddressValidatorBean for  examples as there
       // //are certain gotchas with this data (like city length limits).
       // CityPostalCodeDataVector postalCodes = CityPostalCodeDataAccess.select(pCon, crit, 1); //just get one as there are known dup zipcodes
       // if (postalCodes.isEmpty()) {
       //     //throw new TaxCalculationException("Could not find tax for postal code: " + pShippingAddr.getPostalCode());
       //     log.info("TaxUtilAvalara. Warning. Could not find tax for postal code: " + pShippingAddr.getPostalCode());
       //    return new BigDecimal(0);
       // }
       // CityPostalCodeData pc = (CityPostalCodeData) postalCodes.get(0);
       // if (pc.getTaxRate() == null) {
       //     //throw new TaxCalculationException("Tax Rate is not set for postal code: " + pShippingAddr.getPostalCode());
       //     log.info("TaxUtilAvalara. Warning. Tax Rate is not set for postal code: " + pShippingAddr.getPostalCode());
       //     return new BigDecimal(0);
       // }

        return new BigDecimal(0);
    }

    /**
     *Updates the database with the new tax information
     */
   // public static void updateTaxRateCityPostalCodeData(Connection pCon, CityPostalCodeData pPostalCodeData,BigDecimal pTaxRate)
   // throws SQLException{
      //  if(pTaxRate == null && pPostalCodeData.getTaxRate() != null){
      //      //if this is set in the db but we could not retrieve anything from the tax server don't set it
      //      return;
      //  }
      //  pPostalCodeData.setModBy("taxLoader");
      //  pPostalCodeData.setTaxRate(pTaxRate);
      //  CityPostalCodeDataAccess.update(pCon,pPostalCodeData);
   // }

    /**
     *Retrieves a list of zipcodes ordered by priority in which to process.  Empty first then last modified.
     */
   // public static CityPostalCodeDataVector getPostalCodesToProcessSalesTaxRates(Connection pCon, int maxNumberToReturn)
   // throws SQLException {
       // if(maxNumberToReturn == 0){
       //     throw new RuntimeException("maxNumberToReturn cannot be 0");
       // }
       // CityPostalCodeDataVector toReturn=null;
       // DBCriteria crit = new DBCriteria();
       // crit.addIsNull(CityPostalCodeDataAccess.TAX_RATE);
       // toReturn = CityPostalCodeDataAccess.select(pCon,crit,maxNumberToReturn);
       // int newMaxToReturn = maxNumberToReturn - toReturn.size();

       // if(newMaxToReturn > 0){
       //     crit = new DBCriteria();
       //     crit.addIsNotNull(CityPostalCodeDataAccess.TAX_RATE);
       //     crit.addOrderBy(CityPostalCodeDataAccess.MOD_DATE, true);
//
       //     toReturn.addAll(CityPostalCodeDataAccess.select(pCon,crit, newMaxToReturn));
       // }
     //   return toReturn;
   // }

// old code (for new code see calculateOrderItemsTax method)
    /**
     * calculates tax amount for the each item
     * and  assigns  (tax_rate,tax_amount)   to clw_order_item
     *
     * @param pCon    Connection
     * @param oItms   order item collection
     * @param siteId  site indentifier
     * @param acctId  account identifier
     * @param storeId store identifier
     * @return total tax amount
     * @throws SQLException            if an db errors
     * @throws TaxCalculationException if an calculate errors
     */
    public static BigDecimal calculateItemTax(Connection pCon
                                              ,OrderItemDataVector oItms
                                              ,int siteId
                                              ,int acctId
                                              ,int storeId
                                              ,Distributor distributorEjb
                                              ,Site siteEjb)
                                              throws SQLException, TaxCalculationException {

        BigDecimal totalTaxAmount = new BigDecimal(0);
        //PreOrderItemDataVector poiDV = pBaton.getPreOrderItemDataVector();
        if(oItms!=null && oItms.size()>0){
        	OrderItemData oid = (OrderItemData)oItms.get(0);
        	if(Utility.isSet(oid.getTaxExempt())){
        		if(oid.getTaxExempt().trim().equalsIgnoreCase("true")){
        			log.info("Item ID "+oid.getItemId()+" IS NOT Taxable.");
        			return totalTaxAmount;
        		}else if(oid.getTaxExempt().trim().equalsIgnoreCase("false")){
        			log.info("Item ID "+oid.getItemId()+" IS Taxable.");
        		}
        	}else{
        		if (!isTaxable(pCon, storeId, acctId, siteId)){
            		return totalTaxAmount;
            	}
        	}
        }else{
        	if (!isTaxable(pCon, storeId, acctId, siteId)){
        		return totalTaxAmount;
        	}
        }
        //BigDecimal taxRate = getTaxRate(pCon, siteId); //old code
        BigDecimal taxRate = ZERO; // new code for AvaTax

        // find Shipping Address for the site with id = siteId
        AddressData sad = null;
        try {
            sad = siteEjb.getShipToAddress(siteId); // Shipping Address for the site with id = siteId
        } catch(Exception e) {
        	e.printStackTrace();
            throw new TaxCalculationException("calculateItemTax::Error while trying to find the AddressData using API");
        }
        SiteData sd = null;
        try {
            sd = siteEjb.getSite(siteId);
        } catch(Exception e) {
        	e.printStackTrace();
            throw new TaxCalculationException("calculateItemTax::Error while trying to get the SiteData using API");
        }
        BusEntityData bed = null;

        if (oItms != null && oItms.size() > 0) {
            Iterator it = oItms.iterator();
            while (it.hasNext()) {
            	boolean foundF1= false;
                OrderItemData oid = (OrderItemData) it.next();
                String itemStatus = oid.getOrderItemStatusCd();
                if (!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)) {

                	if(Utility.isSet(oid.getTaxAmount())){

                		log.info("Tax was already present: "+oid.getTaxAmount());
                		BigDecimal itemTax = oid.getTaxAmount();
                		if (itemTax != ZERO && oid.getCustContractPrice() != ZERO) {
                		    //taxRate  = itemTax.divide(oid.getCustContractPrice(),2,BigDecimal.ROUND_HALF_UP);
                			BigDecimal totalPrice = oid.getCustContractPrice().multiply(new BigDecimal(oid.getTotalQuantityOrdered()));
                			if (!totalPrice.equals(ZERO)) {
                			    taxRate  = itemTax.divide(totalPrice,2,BigDecimal.ROUND_HALF_UP);
                            } else {
                                taxRate = ZERO;
                            }
                		    log.info("Tax rate = "+taxRate+" item = "+oid.getDistItemSkuNum());
                		}
                		oid.setTaxRate(taxRate);
                		totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);

                	}else{
                		if (Utility.isTaxableOrderItem(oid)) { //order item is taxable !

                			//oid.setTaxRate(taxRate); // old code
                			BigDecimal qty = new BigDecimal(oid.getTotalQuantityOrdered());
                			BigDecimal contractPrice = oid.getCustContractPrice();

                			/*** new code => calculate Avatax: Begin ***/

                			BigDecimal itemAmount = contractPrice.multiply(qty);
                			int orderId = oid.getOrderId();
                			String erpNum = oid.getDistErpNum();
                			log.info("BBBB: erpNum = " + erpNum);
                			try {
                			    bed = distributorEjb.getDistributorForOrderItem(orderId, storeId, erpNum);
                	        } catch(Exception e) {
                	        	e.printStackTrace();
                	            throw new TaxCalculationException("Error while trying to find the BusEntityData for known orderId, storeId, and erpNum");
                	        }
                			BigDecimal distributorId = new BigDecimal(bed.getBusEntityId());
                			log.info("Item " + oid.getItemShortDesc() + " Distributor = " + distributorId);
                     	    AddressData oad = new AddressData(); // Origin Address

                     	    //find the Origin Address of the Item
                            if (null != distributorId) {
                               DistributorData dd = null;
                               try {
                         	       dd = distributorEjb.getDistributor(distributorId.intValue());
                	           } catch(Exception e) {
                	        	   e.printStackTrace();
                	               throw new TaxCalculationException("Error while trying to find the Distributor");
                	           }
                         	   oad = dd.getPrimaryAddress(); //or Billing Address ??? (both - from DB table CLW_ADDRESS)
                            } else { // item Distributor is NOT defined/found in the Cleanwise Database
                         	   // find the Site address, to which the catalog is attached
                         	   oad = sd.getSiteAddress(); //site Address from DB table CLW_ADDRESS
                            }
                            TaxUtilAvalara taxutilavalara = new TaxUtilAvalara();
                            GetTaxResult getTaxResult = taxutilavalara.calculateAvatax(oad, sad, itemAmount);
                            if (getTaxResult.getResultCode() == SeverityLevel.Success)
                            {
                                //log.info("DocCode: " + getTaxRequest.getDocCode());
                                // DocId is generated by AvaTax
                                log.info("DocId: " + getTaxResult.getDocId());
                                log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
                                log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
                                BigDecimal taxitem = getTaxResult.getTotalTax(); /* taxitem = TotalTax for an item */
                                log.info("alculateItemTax()method: taxitem1 = " + taxitem);
                                totalTaxAmount = Utility.addAmt(taxitem.setScale(2, BigDecimal.ROUND_HALF_UP), totalTaxAmount);
                                taxitem = taxitem.setScale(2, BigDecimal.ROUND_HALF_UP);
                                log.info("alculateItemTax()method: taxitem2 = " + taxitem);
                                oid.setTaxAmount(taxitem);
                                itemAmount = itemAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                                BigDecimal itemTaxRate = ZERO;
                                if (itemAmount.abs().doubleValue()>0.00999) {
                                    itemTaxRate = taxitem.divide(itemAmount, 6, BigDecimal.ROUND_HALF_UP);
                                }
                                log.info("calculateItemTax()method: itemTaxRate = " + itemTaxRate);
                                oid.setTaxRate(itemTaxRate);
                                log.info("AvaTax calculated tax successfully!");
                            }
                            else
                            {
                         	    printMessages(getTaxResult.getMessages());
                                log.info("AvaTax tax calculation failed!");

                            }

                            /*** new code => calculate Avatax: End ***/



                			//BigDecimal itemTax = oid.getTaxRate().multiply(oid.getCustContractPrice().multiply(qty)); //old code
                			//itemTax=itemTax.setScale(2,BigDecimal.ROUND_HALF_UP); //old code
                			//oid.setTaxAmount(itemTax); //old code
                			//totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax); //old code

                		} else { //order item is not taxable !
                			//oid.setTaxRate(taxRate); //old code
                			oid.setTaxRate(ZERO); //new code: tax rate is NOT known at this time !
                			oid.setTaxAmount(new BigDecimal(0));
                		}
                	}

                }
            }
        }
        return totalTaxAmount;
    }

    /**
     * Recalculates tax amount for the each item from collection.
     *
     * No changes were applied to this method when we moved to the new way of tax
     * calculation using Avatax software.
     * Reason: when this method is invoked, tax rate should already be
     * calculated and stored in the OrderItemDataVector Object (Database table CLW_ORDER_ITEM)
     *
     * @param oItms order item collection
     * @return total tax amount
     */
    public static BigDecimal recalculateItemTaxAmount(OrderItemDataVector oItms) {

        BigDecimal totalTaxAmount = new BigDecimal(0);
        if (oItms != null && oItms.size() > 0) {
            Iterator it = oItms.iterator();
            while (it.hasNext()) {

                OrderItemData oid = (OrderItemData) it.next();
                String itemStatus = oid.getOrderItemStatusCd();
                if (!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)) {
                    if (Utility.isTaxableOrderItem(oid)) {

                        BigDecimal qty = new BigDecimal(oid.getTotalQuantityOrdered());
                        BigDecimal taxRate = oid.getTaxRate();
                        log.info("recalculateItemTaxAmount() method: taxRate = " + taxRate);
                        taxRate=taxRate==null?new BigDecimal(0):taxRate;
                        BigDecimal itemTax = taxRate.multiply(oid.getCustContractPrice().multiply(qty));
                        itemTax=itemTax.setScale(2,BigDecimal.ROUND_HALF_UP);
                        oid.setTaxAmount(itemTax);
                        totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);

                    } else {
                        oid.setTaxAmount(new BigDecimal(0));
                    }
                }
            }
        }
        return totalTaxAmount;
    }

    /**
     * gets total tax amount
     *
     * No changes were applied to this method when we moved to the new way of tax
     * calculation using Avatax software (written by vendor "Avalara")
     * Reason: when this method is invoked, tax should already be
     * calculated and stored in the OrderItemDataVector Object (corresponding Database table - CLW_ORDER_ITEM)
     * @param oItms order item collection
     *
     * @return total tax amount
     */
    public static BigDecimal getTotalTaxAmount(OrderItemDataVector oItms) {

    	BigDecimal totalTaxAmount = new BigDecimal(0);
    	if (oItms != null && oItms.size() > 0) {
    		Iterator it = oItms.iterator();
    		while (it.hasNext()) {
    			OrderItemData oid = (OrderItemData) it.next();
                if( ! ((RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED).equals(oid.getOrderItemStatusCd()))){
                	BigDecimal itemTax = oid.getTaxAmount();
                	itemTax=itemTax==null?new BigDecimal(0):itemTax;
                	totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);
                }
    		}
    	}
    	return totalTaxAmount;
    }

    /**
     * gets tax rate.
     *
     * @param pConn  connection
     * @param siteId site indentifier
     * @return tax rate
     * @throws SQLException            if an db errors
     * @throws TaxCalculationException if an calculate errors
     */
    public static BigDecimal getTaxRate(Connection pConn, int siteId)
	throws DataNotFoundException, TaxCalculationException, SQLException {
        AddressData address = getShipTo(pConn, siteId);
        return getTaxRate(pConn, address);
    }

    /**
     * gets Shipping Address.
     *
     * @param pConn  connection
     * @param siteId site indentifier
     * @return tax rate
     * @throws SQLException            if an db errors
     * @throws TaxCalculationException if an calculate errors
     */
    public static BigDecimal getShippingAddress(Connection pConn, int siteId)
	throws DataNotFoundException, TaxCalculationException, SQLException {
        AddressData address = getShipTo(pConn, siteId);
        return getTaxRate(pConn, address);
    }

// Old code Avalara Tax
//    /**
//     * Calculates the Tax for Item in Customer Invoice
//     * @param items - all items from Self-Service Erp system
//     * @return BigDecimal - all taxes
//     */
//    public static BigDecimal calculateCustomerItemTaxes(SelfServiceOrderItemDescViewVector items
//    		                                            , int siteId
//    		                                            , int acctId
//    		                                            , int storeId
//    		                                            , Distributor distributorEjb
//    		                                            , Site siteEjb) throws SQLException, TaxCalculationException {
//
//        BigDecimal totalTaxAmount = new BigDecimal(0);
//
//        // find Shipping Address for the site with id = siteId
//        AddressData sad = null;
//        try {
//            sad = siteEjb.getShipToAddress(siteId); // Shipping Address for the site with id = siteId
//        } catch(Exception e) {
//        	e.printStackTrace();
//            throw new TaxCalculationException("calculateCustomerItemTaxes::Error while trying to find the AddressData using API");
//        }
//        SiteData sd = null;
//        try {
//            sd = siteEjb.getSite(siteId);
//        } catch(Exception e) {
//        	e.printStackTrace();
//            throw new TaxCalculationException("calculateCustomerItemTaxes::Error while trying to get the SiteData using API");
//        }
//        BusEntityData bed = null;
//
//        if (items != null) {
//            for (Object oItem : items) {
//
//                SelfServiceOrderItemDescView item = (SelfServiceOrderItemDescView) oItem;
//                if (Utility.isTaxableOrderItem(item.getOrderItem())) {
//
//                    BigDecimal amt = item.getPrice();
//                    BigDecimal qty = new BigDecimal(item.getQuantity());
//                    //BigDecimal itemTax = taxRate.multiply(amt.multiply(qty)); //old code
//
//        			/*** SVC new code => calculate Avatax: Begin ***/
//
//        			BigDecimal itemAmount = amt.multiply(qty);
//        			int orderId = item.getOrderItem().getOrderId();
//        			String erpNum = item.getOrderItem().getDistErpNum();
//        			try {
//        			    bed = distributorEjb.getDistributorForOrderItem(orderId, storeId, erpNum);
//        	        } catch(Exception e) {
//        	        	e.printStackTrace();
//        	            throw new TaxCalculationException("Error while trying to find the BusEntityData for known orderId, storeId, and erpNum");
//        	        }
//        			BigDecimal distributorId = new BigDecimal(bed.getBusEntityId());
//        			log.info("Item " + item.getOrderItem().getItemShortDesc() + " Distributor = " + distributorId);
//             	    AddressData oad = new AddressData();
//
//             	    //find the origin address for the item
//                    if (null != distributorId) {
//                       DistributorData dd = null;
//                       try {
//                 	       dd = distributorEjb.getDistributor(distributorId.intValue());
//        	           } catch(Exception e) {
//        	        	   e.printStackTrace();
//        	               throw new TaxCalculationException("Error while trying to find the Distributor");
//        	           }
//                 	   oad = dd.getPrimaryAddress(); //or Billing Address ??? (both - from DB table CLW_ADDRESS)
//                    } else { // item Distributor is NOT defined/found in the Cleanwise Database
//                 	   // find the Site address, to which the catalog is attached
//                 	   oad = sd.getSiteAddress(); //site Address from DB table CLW_ADDRESS
//                    }
//
//                    // calculate tax for the Customer Invoice Item
//                    TaxUtilAvalara taxutilavalara = new TaxUtilAvalara();
//                    GetTaxResult getTaxResult = taxutilavalara.calculateAvatax(oad, sad, itemAmount);
//                    if (getTaxResult.getResultCode() == SeverityLevel.Success)
//                    {
//                        //log.info("DocCode: " + getTaxRequest.getDocCode());
//                        // DocId is generated by AvaTax
//                        log.info("DocId: " + getTaxResult.getDocId());
//                        log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
//                        log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
//                        BigDecimal taxitem = getTaxResult.getTotalTax();
//                        totalTaxAmount = Utility.addAmt(taxitem.setScale(2, BigDecimal.ROUND_HALF_UP), totalTaxAmount);
//                        log.info("AvaTax calculated Customer Invoice Item tax successfully!");
//                    }
//                    else
//                    {
//                 	    printMessages(getTaxResult.getMessages());
//                        log.info("AvaTax Customer Invoice Item tax calculation failed!");
//                        throw new TaxCalculationException("AvaTax Customer Invoice Item tax calculation failed!");
//
//                    }
//
//                    /*** SVC new code => calculate Avatax: End ***/
//                }
//            }
//        }
//
//        return totalTaxAmount;
//    }

// New code Avalara Tax. YR
    /**
     * Calculates the Tax for Item in Customer Invoice
     * @param items - all items from Self-Service Erp system
     * @return BigDecimal - all taxes
     */
    public static BigDecimal calculateCustomerItemTaxes(SelfServiceOrderItemDescViewVector items
                                                            , int siteId
                                                            , int acctId
                                                            , int storeId
                                                            , Distributor distributorEjb
                                                            , Site siteEjb) throws SQLException, TaxCalculationException {

        BigDecimal totalTaxAmount = new BigDecimal(0);
        HashMap<String,AvalaraTaxEntry> addressItemsMap = new HashMap<String,AvalaraTaxEntry>();

        // find Shipping Address for the site with id = siteId
        AddressData sad = null;
        try {
            sad = siteEjb.getShipToAddress(siteId); // Shipping Address for the site with id = siteId
        } catch(Exception e) {
                e.printStackTrace();
            throw new TaxCalculationException("calculateCustomerItemTaxes::Error while trying to find the AddressData using API");
        }
        SiteData sd = null;
        try {
            sd = siteEjb.getSite(siteId);
        } catch(Exception e) {
                e.printStackTrace();
            throw new TaxCalculationException("calculateCustomerItemTaxes::Error while trying to get the SiteData using API");
        }
        BusEntityData bed = null;

        if (items != null) {
            for (Object oItem : items) {

                SelfServiceOrderItemDescView item = (SelfServiceOrderItemDescView) oItem;
                if (Utility.isTaxableOrderItem(item.getOrderItem())) {

                    BigDecimal amt = item.getPrice();
                    BigDecimal qty = new BigDecimal(item.getQuantity());
                    //BigDecimal itemTax = taxRate.multiply(amt.multiply(qty)); //old code

                    /*** SVC new code => calculate Avatax: Begin ***/
                    BigDecimal itemAmount = amt.multiply(qty);
                    int orderId = item.getOrderItem().getOrderId();
                    String erpNum = item.getOrderItem().getDistErpNum();
                    try {
                        bed = distributorEjb.getDistributorForOrderItem(orderId, storeId, erpNum);
                    } catch(Exception e) {
                        e.printStackTrace();
                        throw new TaxCalculationException("Error while trying to find the BusEntityData for known orderId, storeId, and erpNum");
                    }
                    BigDecimal distributorId = new BigDecimal(bed.getBusEntityId());
                    log.info("Item " + item.getOrderItem().getItemShortDesc() + " Distributor = " + distributorId);
                    AddressData oad = new AddressData();

                    //find the origin address for the item
                    if (null != distributorId) {
                       DistributorData dd = null;
                       try {
                           dd = distributorEjb.getDistributor(distributorId.intValue());
                       } catch(Exception e) {
                           e.printStackTrace();
                           throw new TaxCalculationException("Error while trying to find the Distributor");
                       }
                       oad = dd.getPrimaryAddress(); //or Billing Address ??? (both - from DB table CLW_ADDRESS)
                   } else { // item Distributor is NOT defined/found in the Cleanwise Database
                       // find the Site address, to which the catalog is attached
                       oad = sd.getSiteAddress(); //site Address from DB table CLW_ADDRESS
                   }

                   if (!Utility.isSetForDisplay(oad.getAddress1()) &&
                       !Utility.isSetForDisplay(oad.getAddress2()) &&
                       !Utility.isSetForDisplay(oad.getAddress3()) &&
                       !Utility.isSetForDisplay(oad.getAddress4()) &&
                       !Utility.isSetForDisplay(oad.getPostalCode()) ) {
                       oad = sad;
                   }

                   AvalaraTaxItem aItem = new AvalaraTaxItem(Integer.toString(item.getOrderItem().getOrderLineNum()),
                                                            Integer.toString(item.getOrderItem().getItemSkuNum()),
                                                            itemAmount,
                                                            item.getQuantity(),
                                                            item.getOrderItem().getItemShortDesc());
                   String addressStr = addressToString(oad);
                   if (addressItemsMap.get(addressStr) == null) {
                       AvalaraTaxEntry entry = new AvalaraTaxEntry();
                       ArrayList<AvalaraTaxItem> aItems = new ArrayList<AvalaraTaxItem>();
                       aItems.add(aItem);
                       entry.setItems(aItems);
                       entry.setOriginAddress(oad);
                       entry.setShippingAddress(sad);
                       addressItemsMap.put(addressStr, entry);
                   } else {
                       addressItemsMap.get(addressStr).getItems().add(aItem);
                   }
                }
            }
            if (!addressItemsMap.isEmpty()) {
                calculateAvalaraTax(addressItemsMap);
                totalTaxAmount = Utility.addAmt(totalTaxAmount,
                                                postCalculateAvalaraTax(addressItemsMap, null));
            }
        }

        return totalTaxAmount;
    }

// Old code Avalara Tax
//    /**
//     * Calculates the Sales Tax for the Vendor Invoice Item
//     * @param invoiceItems - all items from Vendor Invoice
//     * @return BigDecimal - all taxes
//     */
//    public static BigDecimal calculateInvoiceItemTaxes(OrderItemDescDataVector invoiceItems, AddressData originAddress, AddressData shippingAddress) throws TaxCalculationException  {
//        BigDecimal totalTaxAmount = new BigDecimal(0);
//        log.info("calculateInvoiceItemTaxes() method");
//        if (invoiceItems != null) {
//
//            Iterator it = invoiceItems.iterator();
//            while (it.hasNext()) {
//
//                OrderItemDescData itm = (OrderItemDescData) it.next();
//                if (Utility.isTaxableOrderItem(itm.getOrderItem())) {
//
//                    BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(itm.getWorkingInvoiceDistDetailData());
//                    log.info("calculateInvoiceItemTaxes amt = " + amt);
//                    ///////////////////////////// SVC_Avatax new code: Begin
//
//                    BigDecimal qty = new BigDecimal(itm.getWorkingInvoiceDistDetailData().getDistItemQuantity());
//                    log.info("calculateInvoiceItemTaxes qty = " + qty);
//                    BigDecimal itemAmount = amt.multiply(qty);
//                    log.info("calculateInvoiceItemTaxes itemAmount = " + itemAmount);
//
//                    GetTaxResult getTaxResult = calculateAvatax(originAddress, shippingAddress, itemAmount);
//                    BigDecimal itemTax = ZERO;
//                    if (getTaxResult.getResultCode() == SeverityLevel.Success)
//                    {
//                        //log.info("DocCode: " + getTaxRequest.getDocCode());
//                        // DocId is generated by AvaTax
//                        log.info("DocId: " + getTaxResult.getDocId());
//                        log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
//                        log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
//                        itemTax = getTaxResult.getTotalTax();
//                        itemTax = itemTax.setScale(2, BigDecimal.ROUND_HALF_UP);
//                        //itm.setCalculatedSalesTax(itemTax);
//                        //totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);
//                        log.info("AvaTax calculated Vendor Invoice Item tax successfully!");
//                    }
//                    else
//                    {
//                 	    printMessages(getTaxResult.getMessages());
//                        log.info("AvaTax Vendor Invoice Item tax calculation failed!");
//                        throw new TaxCalculationException("calculateInvoiceItemTaxes::AvaTax Vendor Invoice Item tax calculation failed!");
//                    }
//
//                    ///////////////////////////// SVC_Avatax new code: End
//
//                    itm.setCalculatedSalesTax(itemTax); //old code
//                    totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax); //old code
//
//                } else {
//                    itm.setCalculatedSalesTax(new BigDecimal(0));
//                }
//            }
//        }
//
//        return totalTaxAmount;
//    }

// New code Avalara Tax. YR
    /**
     * Calculates the Sales Tax for the Vendor Invoice Item
     * @param invoiceItems - all items from Vendor Invoice
     * @return BigDecimal - all taxes
     */
    public static BigDecimal calculateInvoiceItemTaxes(OrderItemDescDataVector invoiceItems, AddressData originAddress, AddressData shippingAddress) throws TaxCalculationException  {
        HashMap<String,AvalaraTaxEntry> addressItemsMap = new HashMap<String,AvalaraTaxEntry>();
        BigDecimal totalTaxAmount = new BigDecimal(0);
        log.info("calculateInvoiceItemTaxes() method");
        if (invoiceItems != null) {

            Iterator it = invoiceItems.iterator();
            while (it.hasNext()) {

                OrderItemDescData itm = (OrderItemDescData) it.next();
                if (Utility.isTaxableOrderItem(itm.getOrderItem())) {

                    BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(itm.getWorkingInvoiceDistDetailData());
                    log.info("calculateInvoiceItemTaxes amt = " + amt);
                    ///////////////////////////// SVC_Avatax new code: Begin

                    BigDecimal qty = new BigDecimal(itm.getWorkingInvoiceDistDetailData().getDistItemQuantity());
                    log.info("calculateInvoiceItemTaxes qty = " + qty);
                    BigDecimal itemAmount = amt.multiply(qty);
                    log.info("calculateInvoiceItemTaxes itemAmount = " + itemAmount);

                    if (!Utility.isSetForDisplay(originAddress.getAddress1()) &&
                        !Utility.isSetForDisplay(originAddress.getAddress2()) &&
                        !Utility.isSetForDisplay(originAddress.getAddress3()) &&
                        !Utility.isSetForDisplay(originAddress.getAddress4()) &&
                        !Utility.isSetForDisplay(originAddress.getPostalCode()) ) {
                        originAddress = shippingAddress;
                    }

                    AvalaraTaxItem aItem = new AvalaraTaxItem(Integer.toString(itm.getOrderItem().getOrderLineNum()),
                                                             Integer.toString(itm.getOrderItem().getItemSkuNum()),
                                                             itemAmount,
                                                             itm.getWorkingInvoiceDistDetailData().getDistItemQuantity(),
                                                             itm.getOrderItem().getItemShortDesc());
                    String addressStr = addressToString(originAddress);
                    if (addressItemsMap.get(addressStr) == null) {
                        AvalaraTaxEntry entry = new AvalaraTaxEntry();
                        ArrayList<AvalaraTaxItem> aItems = new ArrayList<AvalaraTaxItem>();
                        aItems.add(aItem);
                        entry.setItems(aItems);
                        entry.setOriginAddress(originAddress);
                        entry.setShippingAddress(shippingAddress);
                        addressItemsMap.put(addressStr, entry);
                    } else {
                        addressItemsMap.get(addressStr).getItems().add(aItem);
                    }
                } else {
                    itm.setCalculatedSalesTax(new BigDecimal(0));
                }
            }
            if (!addressItemsMap.isEmpty()) {
                calculateAvalaraTax(addressItemsMap);
                totalTaxAmount = Utility.addAmt(totalTaxAmount,
                                                postCalculateAvalaraTax(addressItemsMap, invoiceItems));
            }



        }

        return totalTaxAmount;
    }

    public static boolean isTaxable(Connection pCon, int storeId,int accountId,int siteId) throws SQLException {
          boolean acctTaxableIndicator  = isBusEntityIdTaxable(pCon, storeId);
          boolean siteTaxableIndicator  = isBusEntityIdTaxable(pCon, accountId);
          boolean storeTaxableIndicator = isBusEntityIdTaxable(pCon, siteId);
          return acctTaxableIndicator && siteTaxableIndicator && storeTaxableIndicator;
     }

     /***
      * Pass to this method the following parameters:
      * "Origin" address
      * "Destination"(Shipping) address
      * Amount to be taxed
      */
     public static GetTaxResult calculateAvatax(AddressData originAddress, AddressData shippingAddress, BigDecimal amount)
     {
    	    log.info("TaxUtilAvalara.calculateAvatax(): Received amount in local currency = " + amount.toString());
    	    boolean success = true;
            BigDecimal calculatedTax = new BigDecimal(0);
            GetTaxResult getTaxResult = new GetTaxResult();

            log.info("calculateAvatax::Received Origin Address1 = " + originAddress);
            log.info("calculateAvatax::Received Shipping Address = " + shippingAddress);

            if(!Utility.isSetForDisplay(originAddress.getAddress1()) &&
            		!Utility.isSetForDisplay(originAddress.getAddress2()) &&
            		!Utility.isSetForDisplay(originAddress.getAddress3()) &&
            		!Utility.isSetForDisplay(originAddress.getAddress4()) &&
            		!Utility.isSetForDisplay(originAddress.getPostalCode())
            ){
            	originAddress = shippingAddress;
            }

            log.info("calculateAvatax::Origin Address2 = " + originAddress);
            log.info("calculateAvatax::Shipping Address2 = " + shippingAddress);

            try
            {
              	TaxSvcSoap port = getTaxSvc();
              	GetTaxRequest getTaxRequest = new GetTaxRequest();

                // Set the tax document properties
                getTaxRequest.setCompanyCode(""); // SVC
                //getTaxRequest.setCompanyCode("test"); // added by SVC
                Date docDate = new Date();
                getTaxRequest.setDocCode("TaxTxSample-" + docDate.toString()); // Unique DocCode
                //getTaxRequest.setDocType(DocumentType.SalesInvoice); //uncommented ONLY TEMPORARY FOR TESTING by SVC
                getTaxRequest.setDocType(DocumentType.SalesOrder); //commented ONLY TEMPORARY FOR TESTING - we use SalesOrder Tax model in Production!!!
                getTaxRequest.setDocDate(docDate);
                //request.setExemptionNo("");
                getTaxRequest.setCustomerCode("TaxSvcTest");
                //request.setPurchaserType("");
                getTaxRequest.setSalespersonCode("");
                getTaxRequest.setOriginCode("Origin");
                getTaxRequest.setDestinationCode("Dest");
                getTaxRequest.setDetailLevel(DetailLevel.Line);

                getTaxRequest.setLocationCode("Test LocationCode");

                // Add the origin and destination addresses referred to by the
                // setOriginCode and setDestinationCode properties above.
                ArrayOfBaseAddress addresses = new ArrayOfBaseAddress(2);
                //BaseAddress origin = new BaseAddress(); //original
                //origin.setAddressCode("Origin"); //original
                BaseAddress baseaddress1 = new BaseAddress();
                String addressCode;
                addressCode = "Origin";
                BaseAddress origin = new BaseAddress();
                origin = populateAvataxAddressFields(baseaddress1, originAddress, addressCode);
                //log.info("Origin Address3 = " + origin);
                addresses.add(origin);

                com.avalara.avatax.services.tax.ArrayOfBaseAddress originAddressToUse = getTaxRequest.getAddresses();
                //log.info("originAddressToUse for AvaTax = " + originAddressToUse);

                BaseAddress baseaddress2 = new BaseAddress();
                addressCode = "Dest";
                BaseAddress destination = new BaseAddress();
                destination = populateAvataxAddressFields(baseaddress2, shippingAddress, addressCode);

                log.info("Destination Address = " + destination);

                addresses.add(destination);

                getTaxRequest.setAddresses(addresses);

                com.avalara.avatax.services.tax.ArrayOfBaseAddress addressesToUse = getTaxRequest.getAddresses();
                log.info("addressesToUse for AvaTax = " + addressesToUse);

                // Add invoice lines
                //ArrayOfLine lines = new ArrayOfLine(2); //original
                ArrayOfLine lines = new ArrayOfLine(1); //mine
                Line line;

                line = new Line();
                line.setNo("101");
                line.setItemCode("Item001");
                line.setQty(new BigDecimal(1));
                //line.setAmount(new BigDecimal(1000.00)); // old
                line.setAmount(amount);
                //added in 4.12 release
                //line.setCustomerUsageType("G"); //commented by SVC
                line.setCustomerUsageType(""); //SVC - must be this way
                line.setDescription("Sample Description");
                lines.add(line);

                getTaxRequest.setLines(lines);

                // Get the tax
                //GetTaxResult getTaxResult = port.getTax(getTaxRequest); //original
                getTaxResult = port.getTax(getTaxRequest); //mine

                //boolean success = true;
                if (getTaxResult.getResultCode() == SeverityLevel.Success)
                {
                    // DocId is generated by AvaTax
                    calculatedTax = getTaxResult.getTotalTax();
                    //return getTaxResult.getTotalTax();
                    //success = true;
                }
                else
                {
                    printMessages(getTaxResult.getMessages());
                    //success = false;
                    //return (new BigDecimal(0));
                }


                // Post the document
                /***
                PostTaxRequest postTaxRequest = new PostTaxRequest();
                // Identify which document
                postTaxRequest.setCompanyCode(getTaxRequest.getCompanyCode());
                postTaxRequest.setDocCode(getTaxRequest.getDocCode());
                postTaxRequest.setDocType(getTaxRequest.getDocType());
                // Can change the document date, but this is not usually desired
                postTaxRequest.setDocDate(getTaxRequest.getDocDate());
                // Pass the expected TotalAmount and TotalTax for verification
                postTaxRequest.setTotalAmount(getTaxResult.getTotalAmount());
                postTaxRequest.setTotalTax(getTaxResult.getTotalTax());

                PostTaxResult postTaxResult = port.postTax(postTaxRequest);

                if (postTaxResult.getResultCode() != SeverityLevel.Success)
                {
                    printMessages(postTaxResult.getMessages());
                }

                // Commit the document - AvaTax provides a two phase commit.
                // Within a financial application, you should now post to the finance application,
                // then commit to AvaTax or CancelTax if posting fails

                CommitTaxRequest commitTaxRequest = new CommitTaxRequest();
                // Identify the document.  We can use just the document id returned from PostTax
                commitTaxRequest.setDocId(postTaxResult.getDocId());

                CommitTaxResult commitTaxResult = port.commitTax(commitTaxRequest);

                if (commitTaxResult.getResultCode() != SeverityLevel.Success)
                {
                    printMessages(commitTaxResult.getMessages());

                    if (commitTaxResult.getResultCode() == SeverityLevel.Error || commitTaxResult.getResultCode() == SeverityLevel.Exception)
                    {
                        // Cancel it
                        CancelTaxRequest cancelTaxRequest = new CancelTaxRequest();
                        cancelTaxRequest.setDocId(postTaxResult.getDocId());
                        cancelTaxRequest.setCancelCode(CancelCode.PostFailed);

                        CancelTaxResult cancelTaxResult = port.cancelTax(cancelTaxRequest);

                    }
                }
                ***/

            }
            catch (Exception ex)
            {
                log.info("Exception: " + ex.getMessage());
            }

            return getTaxResult;

        } // public static BigDecimal calculateAvatax()

     protected static TaxSvcSoap getTaxSvc() throws ServiceException, SOAPException, MalformedURLException, IOException, Exception
     {

         //String url = properties.getProperty("avatax4j.url");
         
         String url = System.getProperty("avatax4j.url");
         log.info("*****avatax4j.url = " + url);
         
         //Assert.assertFalse("avatax4j.url property is not configured in avatax4j.properties", url == null || url.length() == 0);
 		 if (url.equals(null) || url.length() == 0)
 		 {
            log.info("avatax4j.url property is not configured in avatax4j.properties");
 	     }

         //String account = properties.getProperty("avatax4j.account");
 		 
         String account = System.getProperty("avatax4j.account");
         log.info("*****avatax4j.account = " + account);
         
         //Assert.assertFalse("avatax4j.account property is not configured in avatax4j.properties", account == null || account.length() == 0);
 	 	 if (account.equals(null) || account.length() == 0){
 	 		log.info("avatax4j.account property is not configured in avatax4j.properties");
 	 	 }
         
 	 	 //String license = properties.getProperty("avatax4j.license");
         String license = System.getProperty("avatax4j.license");
         log.info("*****avatax4j.license = " + license); 	 	 
 	 	 
         //Assert.assertFalse("avatax4j.license property is not configured in avatax4j.properties", license == null || license.length() == 0);
 	 	 if (license.equals(null) || license.length() == 0){
  	 		log.info("avatax4j.license property is not configured in avatax4j.properties");
  	 	 }

 	 	 TaxSvc taxSvc;

    	 TaxSvcSoap port;
         taxSvc = new TaxSvcLocator();

         port = taxSvc.getTaxSvcSoap(new URL(url));

         // Set the profile
         Profile profile = new Profile();
         profile.setClient("TaxSvcTest,4.0.0.0");
         port.setProfile(profile);

         // Set security
         Security security = new Security();
         security.setAccount(account);
         security.setLicense(license);
         port.setSecurity(security);

         return port;
     }

 	 protected static String getjbossHomeDirectory() throws Exception {
 		//String pJbossHomeDirectory = Utility.loadProperties("defst.default.properties").getProperty("jbossHome"); YKR25
 		String pJbossHomeDirectory = System.getProperty("jbossHome"); //YKR25
 		log.info("pJbossHomeDirectory1 = " + pJbossHomeDirectory);

 		return pJbossHomeDirectory;
 	 }

     protected static void printMessages(ArrayOfMessage messages)
     {
         for (int ii = 0; ii < messages.size(); ii++)
         {
             Message message = messages.getMessage(ii);
             log.info(message.getSeverity().toString() + " " + ii + ": " + message.getSummary());
         }

     }

     public static BaseAddress populateAvataxAddressFields(BaseAddress pBaseaddress, AddressData pAddress, String addressCode)
     {
    	 pBaseaddress.setAddressCode(addressCode);

    	 String line1 = "";
         String address1 = pAddress.getAddress1();
         if (null != address1) {
         	line1 = address1;
         }
         pBaseaddress.setLine1(line1);

         String line2 = "";
         String address2 = pAddress.getAddress2();
         if (null != address2) {
         	line2 = address2;
         }
         pBaseaddress.setLine2(line2);

         String line3 = "";
         String address3 = pAddress.getAddress3();
         if (null != address3) {
         	line3 = address3;
         }

         String line4 = "";
         String address4 = pAddress.getAddress4();
         if (null != address4) {
         	line4 = address4;
         }

         String line3_4 = line3 + line4;
         pBaseaddress.setLine3(line3_4);

         String city = "";
         String avataxCity = pAddress.getCity();
         if (null != avataxCity) {
         	city = avataxCity;
         }
         pBaseaddress.setCity(city);

         String stateProvince = "";
         String avataxRegion = pAddress.getStateProvinceCd();
         if (null != avataxRegion) {
         	stateProvince = avataxRegion;
         }
         pBaseaddress.setRegion(stateProvince);

         String postalCode = "";
         String avataxPostalCode = pAddress.getPostalCode();
         if (null != avataxPostalCode) {
        	 postalCode = avataxPostalCode;
         }
         pBaseaddress.setPostalCode(postalCode);

         String country = "";
         String avataxCountry = pAddress.getCountryCd();
         if (null != avataxCountry) {
        	 country = avataxCountry;
         }
         pBaseaddress.setCountry(country);

         return pBaseaddress;

     }

// ------------------ New Avalara Tax Lines. YR -----------------
     public static String addressToString(AddressData pAddress)
     {
         String addressString = "";

         String line1 = ":";
         String address1 = pAddress.getAddress1();
         if (null != address1) {
                line1 += address1;
         }
         addressString += line1;

         String line2 = ":";
         String address2 = pAddress.getAddress2();
         if (null != address2) {
                line2 += address2;
         }
         addressString += line2;

         String line3 = ":";
         String address3 = pAddress.getAddress3();
         if (null != address3) {
                line3 += address3;
         }
         addressString += line3;

         String line4 = "+";
         String address4 = pAddress.getAddress4();
         if (null != address4) {
                line4 += address4;
         }
         addressString += line4;

         String city = ":";
         String avataxCity = pAddress.getCity();
         if (null != avataxCity) {
                city += avataxCity;
         }
         addressString += city;

         String stateProvince = ":";
         String avataxRegion = pAddress.getStateProvinceCd();
         if (null != avataxRegion) {
                stateProvince += avataxRegion;
         }
         addressString += stateProvince;

         String postalCode = ":";
         String avataxPostalCode = pAddress.getPostalCode();
         if (null != avataxPostalCode) {
                 postalCode += avataxPostalCode;
         }
         addressString += postalCode;

         String country = ":";
         String avataxCountry = pAddress.getCountryCd();
         if (null != avataxCountry) {
                 country += avataxCountry;
         }
         addressString += country;

         return addressString;

     }


     /**
      * calculates tax amount for the each item
      * and  assigns  (tax_rate,tax_amount)   to clw_order_item
      *
      * @param pCon    Connection
      * @param oItms   order item collection
      * @param siteId  site indentifier
      * @param acctId  account identifier
      * @param storeId store identifier
      * @return total tax amount
      * @throws SQLException            if an db errors
      * @throws TaxCalculationException if an calculate errors
      */
     public static BigDecimal calculateOrderItemsTax(Connection pCon
                                                    ,OrderItemDataVector oItms
                                                    ,int siteId
                                                    ,int acctId
                                                    ,int storeId
                                                    ,Distributor distributorEjb
                                                    ,Site siteEjb)
             throws SQLException, TaxCalculationException {

         BigDecimal totalTaxAmount = new BigDecimal(0);
         HashMap<String,AvalaraTaxEntry> addressItemsMap = new HashMap<String,AvalaraTaxEntry>();
         totalTaxAmount = Utility.addAmt(totalTaxAmount,
                                         preCalculateAvalaraTax(pCon, oItms, siteId, acctId, storeId, distributorEjb, siteEjb, addressItemsMap));
         if (!addressItemsMap.isEmpty()) {
             calculateAvalaraTax(addressItemsMap);
             totalTaxAmount = Utility.addAmt(totalTaxAmount,
                                             postCalculateAvalaraTax(addressItemsMap, oItms));
         }
         return totalTaxAmount;
     }


     /**
      * mapping order items for calculation avalara tax amount
      *
      * @param pCon    Connection
      * @param oItms   order item collection
      * @param siteId  site indentifier
      * @param acctId  account identifier
      * @param storeId store identifier
      * @param addressItemsMap order items to origin address map
      * @return total tax amount
      * @throws SQLException            if an db errors
      * @throws TaxCalculationException if an calculate errors
      */
     public static BigDecimal preCalculateAvalaraTax(Connection pCon
                                                    ,OrderItemDataVector oItms
                                                    ,int siteId
                                                    ,int acctId
                                                    ,int storeId
                                                    ,Distributor distributorEjb
                                                    ,Site siteEjb
                                                    ,HashMap<String,AvalaraTaxEntry> addressItemsMap)
             throws SQLException, TaxCalculationException {

         BigDecimal totalTaxAmount = new BigDecimal(0);
         if(oItms!=null && oItms.size()>0){
                 OrderItemData oid = (OrderItemData)oItms.get(0);
                 if(Utility.isSet(oid.getTaxExempt())){
                         if(oid.getTaxExempt().trim().equalsIgnoreCase("true")){
                                 log.info("Item ID "+oid.getItemId()+" IS NOT Taxable.");
                                 return totalTaxAmount;
                         }else if(oid.getTaxExempt().trim().equalsIgnoreCase("false")){
                                 log.info("Item ID "+oid.getItemId()+" IS Taxable.");
                         }
                 }else{
                         if (!isTaxable(pCon, storeId, acctId, siteId)){
                         return totalTaxAmount;
                 }
                 }
         }else{
                 if (!isTaxable(pCon, storeId, acctId, siteId)){
                         return totalTaxAmount;
                 }
         }
         BigDecimal taxRate = ZERO;

         // find Shipping Address for the site with id = siteId
         AddressData shippingAddress = null;
         try {
             shippingAddress = siteEjb.getShipToAddress(siteId); // Shipping Address for the site with id = siteId
         } catch(Exception e) {
                 e.printStackTrace();
             throw new TaxCalculationException("Error while trying to find the AddressData using API");
         }
         SiteData sd = null;
         try {
             sd = siteEjb.getSite(siteId);
         } catch(Exception e) {
                 e.printStackTrace();
             throw new TaxCalculationException("Error while trying to get the SiteData using API");
         }

         if (oItms != null && oItms.size() > 0) {
             Iterator it = oItms.iterator();
// ========================================
// Items to Original Address Mapping
// ========================================
             while (it.hasNext()) {
                 OrderItemData oid = (OrderItemData) it.next();
                 String itemStatus = oid.getOrderItemStatusCd();
                 if (!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)) {
                     if(Utility.isSet(oid.getTaxAmount())){
                         log.info("Tax was already present: "+oid.getTaxAmount());
                         BigDecimal itemTax = oid.getTaxAmount();
                         if (itemTax != ZERO && oid.getCustContractPrice() != ZERO) {
                             //taxRate  = itemTax.divide(oid.getCustContractPrice(),2,BigDecimal.ROUND_HALF_UP);
                        	 BigDecimal totalPrice = oid.getCustContractPrice().multiply(new BigDecimal(oid.getTotalQuantityOrdered()));
                        	 if (!totalPrice.equals(ZERO)) {
                        	    taxRate  = itemTax.divide(totalPrice,2,BigDecimal.ROUND_HALF_UP);
                             } else {
                                 taxRate = ZERO;
                             }
                             log.info("Tax rate = "+taxRate+" item = "+oid.getDistItemSkuNum());
                         }
                         oid.setTaxRate(taxRate);
                         totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);
                     } else {
                         if (Utility.isTaxableOrderItem(oid)) { //order item is taxable !
                             int orderId = oid.getOrderId();
                             String erpNum = oid.getDistErpNum();
                             log.info("BBBB: erpNum = " + erpNum);
                             BusEntityData bed = null;
                             try {
                                 bed = distributorEjb.getDistributorForOrderItem(orderId, storeId, erpNum);
                             } catch (Exception e) {
                                 e.printStackTrace();
                                 throw new TaxCalculationException("Error while trying to find the BusEntityData for known orderId, storeId, and erpNum");
                             }
                             BigDecimal distributorId = new BigDecimal(bed.getBusEntityId());
                             log.info("Item " + oid.getItemShortDesc() + " Distributor = " + distributorId);
                             AddressData oad = new AddressData(); // Origin Address
                             //find the Origin Address of the Item
                             if (null != distributorId) {
                                 DistributorData dd = null;
                                 try {
                                     dd = distributorEjb.getDistributor(distributorId.intValue());
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                     throw new TaxCalculationException("Error while trying to find the Distributor");
                                 }
                                 oad = dd.getPrimaryAddress(); //or Billing Address ??? (both - from DB table CLW_ADDRESS)
                             } else { // item Distributor is NOT defined/found in the Cleanwise Database
                                 // find the Site address, to which the catalog is attached
                                 oad = sd.getSiteAddress(); //site Address from DB table CLW_ADDRESS
                             }

                             if (!Utility.isSetForDisplay(oad.getAddress1()) &&
                                 !Utility.isSetForDisplay(oad.getAddress2()) &&
                                 !Utility.isSetForDisplay(oad.getAddress3()) &&
                                 !Utility.isSetForDisplay(oad.getAddress4()) &&
                                 !Utility.isSetForDisplay(oad.getPostalCode()) ) {
                                 oad = shippingAddress;
                             }

                             AvalaraTaxItem item = new AvalaraTaxItem(Integer.toString(oid.getOrderLineNum()),
                                                                      Integer.toString(oid.getItemSkuNum()),
                                                                      oid.getCustContractPrice().multiply(new BigDecimal(oid.getTotalQuantityOrdered())),
                                                                      oid.getTotalQuantityOrdered(),
                                                                      oid.getItemShortDesc());
                             String addressStr = addressToString(oad);
                             if (addressItemsMap.get(addressStr) == null) {
                                 AvalaraTaxEntry entry = new AvalaraTaxEntry();
                                 ArrayList<AvalaraTaxItem> items = new ArrayList<AvalaraTaxItem>();
                                 items.add(item);
                                 entry.setItems(items);
                                 entry.setOriginAddress(oad);
                                 entry.setShippingAddress(shippingAddress);
                                 addressItemsMap.put(addressStr, entry);
                             } else {
                                 addressItemsMap.get(addressStr).getItems().add(item);
                             }
                         } else { //order item is not taxable !
                             oid.setTaxRate(ZERO);
                             oid.setTaxAmount(ZERO);
                         }
                     }
                 }
             }
// ========================================
// End of Items to Original Address Mapping
// ========================================
         }
         return totalTaxAmount;
     }

     /**
      * calculates tax amount for all items
      *
      * @param addressItemsMap order items to origin address map
      * @throws TaxCalculationException if an calculate errors
      */
     public static void calculateAvalaraTax(HashMap<String,AvalaraTaxEntry> addressItemsMap) {
         try {
             TaxSvcSoap port = getTaxSvc();
             GetTaxRequest getTaxRequest = new GetTaxRequest();
             GetTaxResult getTaxResult = new GetTaxResult();

             if (!addressItemsMap.isEmpty()) {
                 Iterator oaIter = addressItemsMap.keySet().iterator();
                 while (oaIter.hasNext()) {
                     String originAddressStr = (String)oaIter.next();
                     AddressData shippingAddress = addressItemsMap.get(originAddressStr).getShippingAddress();
                     AddressData originAddress = addressItemsMap.get(originAddressStr).getOriginAddress();
// ================================
// Request Header to Avalara
// ================================
                     // Set the tax document properties
                     getTaxRequest.setCompanyCode(""); // SVC
                     //getTaxRequest.setCompanyCode("test"); // added by SVC
                     Date docDate = new Date();
                     getTaxRequest.setDocCode("TaxTxSample-" + docDate.toString()); // Unique DocCode
                     //getTaxRequest.setDocType(DocumentType.SalesInvoice); //uncommented ONLY TEMPORARY FOR TESTING by SVC
                     getTaxRequest.setDocType(DocumentType.SalesOrder); //commented ONLY TEMPORARY FOR TESTING - we use SalesOrder Tax model in Production!!!
                     getTaxRequest.setDocDate(docDate);
                     //request.setExemptionNo("");
                     getTaxRequest.setCustomerCode("TaxSvcTest");
                     //request.setPurchaserType("");
                     getTaxRequest.setSalespersonCode("");
                     getTaxRequest.setOriginCode("Origin");
                     getTaxRequest.setDestinationCode("Dest");
                     getTaxRequest.setDetailLevel(DetailLevel.Line);
                     getTaxRequest.setLocationCode("Test LocationCode");

                     // Add the origin and destination addresses referred to by the
                     // setOriginCode and setDestinationCode properties above.
                     ArrayOfBaseAddress addresses = new ArrayOfBaseAddress(2);
                     //BaseAddress origin = new BaseAddress(); //original
                     //origin.setAddressCode("Origin"); //original
                     BaseAddress baseaddress1 = new BaseAddress();
                     String addressCode;
                     addressCode = "Origin";
                     BaseAddress origin = new BaseAddress();
                     origin = populateAvataxAddressFields(baseaddress1, originAddress, addressCode);
                     //log.info("Origin Address3 = " + origin);
                     addresses.add(origin);

                     BaseAddress baseaddress2 = new BaseAddress();
                     addressCode = "Dest";
                     BaseAddress destination = new BaseAddress();
                     destination = populateAvataxAddressFields(baseaddress2, shippingAddress, addressCode);
                     log.info("Destination Address = " + destination);

                     addresses.add(destination);

                     getTaxRequest.setAddresses(addresses);

                     com.avalara.avatax.services.tax.ArrayOfBaseAddress addressesToUse = getTaxRequest.getAddresses();
                     log.info("addressesToUse for AvaTax = " + addressesToUse);
// ================================
// End of Request Header to Avalara
// ================================
                     ArrayOfLine lines = new ArrayOfLine();
                     for (AvalaraTaxItem item : addressItemsMap.get(originAddressStr).getItems()) {
// ================================
// Item lines to Avalara
// ================================
                         Line line;
                         line = new Line();
                         line.setNo(item.getItemNo());
                         line.setItemCode(item.getItemCode());
                         line.setQty(new BigDecimal(item.getItemQuantity()));
//                         BigDecimal itemAmount = item.getItemAmount().multiply(new BigDecimal(item.getItemQuantity()));
                         BigDecimal itemAmount = item.getItemAmount();
                         line.setAmount(itemAmount);
                         //added in 4.12 release
                         //line.setCustomerUsageType("G"); //commented by SVC
                         line.setCustomerUsageType(""); //SVC - must be this way
                         line.setDescription(item.getItemDesc());
                         lines.add(line);
// ================================
// End of Item lines to Avalara
// ================================
                     }
                     getTaxRequest.setLines(lines);
                     getTaxResult = port.getTax(getTaxRequest);
// ================================
// Result from Avalara
// ================================
                     if (getTaxResult.getResultCode() == SeverityLevel.Success) {
                         // DocId is generated by AvaTax
                         log.info("DocId: " + getTaxResult.getDocId());
                         log.info("TotalAmount: " + getTaxResult.getTotalAmount().toString());
                         log.info("TotalTax: " + getTaxResult.getTotalTax().toString());
                         for(int i = 0; i < getTaxResult.getTaxLines().getTaxLine().length; i++) {
                             TaxLine taxLine = getTaxResult.getTaxLines().getTaxLine(i);
                             Line cln = getTaxRequest.getLines().getLine(i);
                             log.info("     Line: "+ taxLine.getNo() + " Amount: " + cln.getAmount() + " Tax: "+taxLine.getTax());
                             log.info("     ItemCode: " + cln.getItemCode() + " TaxCode: " + taxLine.getTaxCode());

                             BigDecimal taxitem = taxLine.getTax();
                             log.info("calculateItemTax()method: taxitem1 = " + taxitem);
                             taxitem = taxitem.setScale(2, BigDecimal.ROUND_HALF_UP);
                             log.info("calculateItemTax()method: taxitem2 = " + taxitem);

                             for(int j = 0; j < taxLine.getTaxDetails().getTaxDetail().length; j++) {
                                 TaxDetail taxDetail = taxLine.getTaxDetails().getTaxDetail(j);
                                 log.info("          Juris Type: "+taxDetail.getJurisType()+"; Juris Name: "+taxDetail.getJurisName()+"; Rate: "+taxDetail.getRate()+"; Amt: "+taxDetail.getTax());
                             }

                             for (AvalaraTaxItem item : addressItemsMap.get(originAddressStr).getItems()) {
                                 if (taxLine.getNo().equals(item.getItemNo())) {
                                     item.setTaxAmount(taxitem);    // TaxAmount
                                     BigDecimal itemAmount = item.getItemAmount();
                                     itemAmount = itemAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                                     BigDecimal itemTaxRate = ZERO;
                                     if (itemAmount.abs().doubleValue()>0.00999) {
                                        itemTaxRate = taxitem.divide(itemAmount, 6, BigDecimal.ROUND_HALF_UP);
                                     }
                                     log.info("calculateItemTax()method: itemTaxRate = " + itemTaxRate);
                                     item.setTaxRate(itemTaxRate);  // TaxRate
                                     break;
                                 }
                             }
                         }
                     } else {
                         printMessages(getTaxResult.getMessages());
                         log.info("AvaTax tax calculation failed!");
                     }
// ================================
// End Result from Avalara
// ================================
                 }
             }
             log.info("AvaTax calculated tax successfully!");
         } catch (Exception ex) {
             log.info("Exception: " + ex.getMessage());
         }
 }


 /**
  * calculates total tax amount for all items
  * and  assigns  (tax_rate,tax_amount)   to clw_order_item
  *
  * @param addressItemsMap order items to origin address map
  * @param oItms   order item collection
  * @return total tax amount
  * @throws SQLException            if an db errors
  * @throws TaxCalculationException if an calculate errors
  */
 public static BigDecimal postCalculateAvalaraTax(HashMap<String,AvalaraTaxEntry> addressItemsMap, ArrayList oItms) {

     BigDecimal totalTaxAmount = new BigDecimal(0);
     if (!addressItemsMap.isEmpty()) {
         Iterator oaIter = addressItemsMap.keySet().iterator();
         while (oaIter.hasNext()) {
             String originAddressStr = (String) oaIter.next();
             for (AvalaraTaxItem item : addressItemsMap.get(originAddressStr).getItems()) {
                 if (oItms != null && oItms.size() > 0) {
                     Iterator it = oItms.iterator();
                     while (it.hasNext()) {
                         Object od = it.next();
                         if (od instanceof OrderItemData) {
                             OrderItemData oid = (OrderItemData) od;
                             if(item.getItemNo().equals(Integer.toString(oid.getOrderLineNum()))) {
                                 oid.setTaxAmount(item.getTaxAmount());
                                 oid.setTaxRate(item.getTaxRate());
                                 break;
                             }
                         } else if (od instanceof OrderItemDescData) {
                             OrderItemDescData oidd = (OrderItemDescData) od;
                             if(item.getItemNo().equals(Integer.toString(oidd.getOrderItem().getOrderLineNum()))) {
                                 oidd.setCalculatedSalesTax(item.getTaxAmount());
                                 break;
                             }
                         }
                     }
                 }
                 BigDecimal taxitem = item.getTaxAmount();
                 totalTaxAmount = Utility.addAmt(taxitem.setScale(2, BigDecimal.ROUND_HALF_UP), totalTaxAmount);
             }
         }
     }
     return totalTaxAmount;
 }

 public static class AvalaraTaxEntry {
     private AddressData _originAddress;
     private AddressData _shippingAddress;
     private ArrayList<AvalaraTaxItem> _items = new ArrayList<AvalaraTaxItem>();

     public AvalaraTaxEntry() {
     }

     public void setOriginAddress(AddressData originAddress) {
         _originAddress = originAddress;
     }
     public AddressData getOriginAddress() {
         return _originAddress;
     }

     public void setShippingAddress(AddressData shippingAddress) {
         _shippingAddress = shippingAddress;
     }
     public AddressData getShippingAddress() {
         return _shippingAddress;
     }

     public void setItems(ArrayList<AvalaraTaxItem> items) {
         _items = items;
     }
     public ArrayList<AvalaraTaxItem> getItems() {
         return _items;
     }
 }

 public static class AvalaraTaxItem {
     private String _itemNo;
     private String _itemCode;
     private BigDecimal _itemAmount;
     private int _itemQuantity;
     private BigDecimal _taxAmount = new BigDecimal(0);
     private BigDecimal _taxRate;
     private String _itemDesc;
     public AvalaraTaxItem() {
     }
     public AvalaraTaxItem(String itemNo,
                    String itemCode,
                    BigDecimal itemAmount,
                    int itemQuantity,
                    String itemDesc
                    ) {
      _itemNo = itemNo;
      _itemCode = itemCode;
      _itemAmount = itemAmount;
      _itemQuantity = itemQuantity;
      _itemDesc = itemDesc;
     }

     public void setItemNo(String itemNo) {
         _itemNo = itemNo;
     }
     public String getItemNo() {
         return _itemNo;
     }

     public void setItemCode(String itemCode) {
         _itemCode = itemCode;
     }
     public String getItemCode() {
         return _itemCode;
     }

     public void setItemAmount(BigDecimal itemAmount) {
         _itemAmount = itemAmount;
     }
     public BigDecimal getItemAmount() {
         return _itemAmount;
     }

     public void setItemQuantity(int itemQuantity) {
         _itemQuantity = itemQuantity;
     }
     public int getItemQuantity() {
         return _itemQuantity;
     }

     public void setTaxAmount(BigDecimal taxAmount) {
         _taxAmount = taxAmount;
     }
     public BigDecimal getTaxAmount() {
         return _taxAmount;
     }

     public void setTaxRate(BigDecimal taxRate) {
         _taxRate = taxRate;
     }
     public BigDecimal getTaxRate() {
         return _taxRate;
     }

     public void setItemDesc(String itemDesc) {
         _itemDesc = itemDesc;
     }
     public String getItemDesc() {
         return _itemDesc;
     }

 }



}
