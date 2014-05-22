/*
 * TaxUtil.java
 *
 * Created on September 2, 2005, 10:49 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.util;

import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.CityPostalCodeDataAccess;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 *
 * @author bstevens
 */
public class TaxUtil {
    private static final Logger log = Logger.getLogger(TaxUtil.class);
    
    public static final BigDecimal ZERO = new BigDecimal(0);
    /**
     *Returns the shipping address.
     */
    private static AddressData getShipTo(Connection pConn,int siteId) throws TaxCalculationException, SQLException{

        if(siteId > 0){
            AddressDataVector addrs = BusEntityDAO.getSiteAddresses(pConn, siteId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
            AddressData sAddr;
            if(addrs != null && addrs.size() > 0){
                sAddr = (AddressData) addrs.get(0);
            }else{
                throw new TaxCalculationException("No address found for site id "+siteId);
            }
            return sAddr;
        }
        throw new TaxCalculationException("No shipto information specified");
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
            AddressDataVector addrs = BusEntityDAO.getSiteAddresses(pConn, siteId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
            AddressData sAddr;
            if(addrs != null && addrs.size() > 0){
                sAddr = (AddressData) addrs.get(0);
            }else{
                throw new TaxCalculationException("No address found for site id "+siteId);
            }
            return sAddr;
        }
        throw new TaxCalculationException("No shipto information specified");
    }

    /**
     *From the query data extracts wheather or not this site is taxable or not
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
     *From the query data extracts wheather or not this account is taxable or not
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
     *From the query data extracts wheather or not this store is taxable or not
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
     *Returns an initialzed TaxQueryResponse object that may be used for multiple
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

        if(   !pSiteTaxableIndicator
	   || !pAcctTaxableIndicator 
	   || !pStoreTaxableIndicator){
            return new TaxQueryResponse(new BigDecimal(0.00));
        }

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CityPostalCodeDataAccess.POSTAL_CODE,Utility.normalizePostalCode(pShippingAddr.getPostalCode()));
        //could add in cirt criteria state etc.  See the @see AddressValidatorBean for  examples as there
        //are certain gotchas with this data (like city length limits).
        CityPostalCodeDataVector postalCodes = CityPostalCodeDataAccess.select(pConn,crit,1); //just get one as there are known dup zipcodes
        if(postalCodes.isEmpty()){
            throw new TaxCalculationException("Could not find tax for postal code: "+pShippingAddr.getPostalCode());
        }
        CityPostalCodeData pc = (CityPostalCodeData) postalCodes.get(0);
        if(pc.getTaxRate() == null){
            throw new TaxCalculationException("Tax Rate is not set for postal code: "+pShippingAddr.getPostalCode());
        }
        BigDecimal taxRate=pc.getTaxRate();
        //and then simply perform the simple calculation
        return new TaxQueryResponse(taxRate);
    }

    public static BigDecimal getTaxRate(Connection pCon, AddressData pShippingAddr) throws TaxCalculationException, SQLException {

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(CityPostalCodeDataAccess.POSTAL_CODE, Utility.normalizePostalCode(pShippingAddr.getPostalCode()));
        //could add in cirt criteria state etc.  See the @see AddressValidatorBean for  examples as there
        //are certain gotchas with this data (like city length limits).
        CityPostalCodeDataVector postalCodes = CityPostalCodeDataAccess.select(pCon, crit, 1); //just get one as there are known dup zipcodes
        if (postalCodes.isEmpty()) {
            //throw new TaxCalculationException("Could not find tax for postal code: " + pShippingAddr.getPostalCode());
            log.info("TaxUtil. Warning. Could not find tax for postal code: " + pShippingAddr.getPostalCode());
            return new BigDecimal(0);
        }
        CityPostalCodeData pc = (CityPostalCodeData) postalCodes.get(0);
        if (pc.getTaxRate() == null) {
            //throw new TaxCalculationException("Tax Rate is not set for postal code: " + pShippingAddr.getPostalCode());
            log.info("TaxUtil. Warning. Tax Rate is not set for postal code: " + pShippingAddr.getPostalCode());
            return new BigDecimal(0);
        }

        return pc.getTaxRate();
    }

    /**
     *Updates the database with the new tax information
     */
    public static void updateTaxRateCityPostalCodeData(Connection pCon, CityPostalCodeData pPostalCodeData,BigDecimal pTaxRate)
    throws SQLException{
        if(pTaxRate == null && pPostalCodeData.getTaxRate() != null){
            //if this is set in the db but we could not retrieve anything from the tax server don't set it
            return;
        }
        pPostalCodeData.setModBy("taxLoader");
        pPostalCodeData.setTaxRate(pTaxRate);
        CityPostalCodeDataAccess.update(pCon,pPostalCodeData);
    }
    
    /**
     *Retrieves a list of zipcodes ordered by priority in which to process.  Empty first then last modified.
     */
    public static CityPostalCodeDataVector getPostalCodesToProcessSalesTaxRates(Connection pCon, int maxNumberToReturn)
    throws SQLException {
        if(maxNumberToReturn == 0){
            throw new RuntimeException("maxNumberToReturn cannot be 0");
        }
        CityPostalCodeDataVector toReturn;
        DBCriteria crit = new DBCriteria();
        crit.addIsNull(CityPostalCodeDataAccess.TAX_RATE);
        toReturn = CityPostalCodeDataAccess.select(pCon,crit,maxNumberToReturn);
        int newMaxToReturn = maxNumberToReturn - toReturn.size();
        
        if(newMaxToReturn > 0){
            crit = new DBCriteria();
            crit.addIsNotNull(CityPostalCodeDataAccess.TAX_RATE);
            crit.addOrderBy(CityPostalCodeDataAccess.MOD_DATE, true);

            toReturn.addAll(CityPostalCodeDataAccess.select(pCon,crit, newMaxToReturn));
        }
        return toReturn;
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
    public static BigDecimal calculateItemTax(Connection pCon,
                                              OrderItemDataVector oItms,
                                              int siteId, int acctId, int storeId)
                                              throws SQLException, TaxCalculationException {
    	
        BigDecimal totalTaxAmount = new BigDecimal(0);
        //PreOrderItemDataVector poiDV = pBaton.getPreOrderItemDataVector();
        if(oItms!=null && oItms.size()>0){
        	OrderItemData oid = (OrderItemData)oItms.get(0);
        	if(Utility.isSet(oid.getTaxExempt())){
        		if(oid.getTaxExempt().equalsIgnoreCase("T")){
        			return totalTaxAmount;
        		}else if(oid.getTaxExempt().equalsIgnoreCase("F")){
        			log.info("Item ID "+oid.getItemId()+" is Taxable.");
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
        BigDecimal taxRate = getTaxRate(pCon, siteId);

        if (oItms != null && oItms.size() > 0) {
            Iterator it = oItms.iterator();
            while (it.hasNext()) {
            	boolean foundF1= false;
                OrderItemData oid = (OrderItemData) it.next();
                String itemStatus = oid.getOrderItemStatusCd();
                if (!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.equals(itemStatus)) {
                	
                	if(Utility.isSet(oid.getTaxAmount())){
                		
                		BigDecimal itemTax = oid.getTaxAmount();
                		if (!oid.getCustContractPrice().equals(ZERO)) {
                		    taxRate  = itemTax.divide(oid.getCustContractPrice(),2,BigDecimal.ROUND_HALF_UP);
                        } else {
                            taxRate = ZERO;
                        }
                		oid.setTaxRate(taxRate);
                		totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);
                		
                	}else{
                		if (Utility.isTaxableOrderItem(oid)) {

                			oid.setTaxRate(taxRate);
                			BigDecimal qty = new BigDecimal(oid.getTotalQuantityOrdered());
                			BigDecimal itemTax = oid.getTaxRate().multiply(oid.getCustContractPrice().multiply(qty));
                			itemTax=itemTax.setScale(2,BigDecimal.ROUND_HALF_UP);
                			oid.setTaxAmount(itemTax);
                			totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);

                		} else {
                			oid.setTaxRate(taxRate);
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
     * @param oItms order item collection
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
    public static BigDecimal getTaxRate(Connection pConn, int siteId) throws TaxCalculationException, SQLException {
        AddressData address = getShipTo(pConn, siteId);
        return getTaxRate(pConn, address);
    }

    /**
     * Calculates the Tax for Item in Customer Invoice
     * @param items - all items from Self-Service Erp system
     * @return BigDecimal - all taxes
     */
    public static BigDecimal calculateCustomerItemTaxes(SelfServiceOrderItemDescViewVector items) {

        BigDecimal totalTaxAmount = new BigDecimal(0);
        if (items != null) {
            for (Object oItem : items) {

                SelfServiceOrderItemDescView item = (SelfServiceOrderItemDescView) oItem;
                if (Utility.isTaxableOrderItem(item.getOrderItem())) {

                    BigDecimal amt = item.getPrice();
                    BigDecimal taxRate = item.getOrderItem().getTaxRate();
                    if (taxRate == null) {
                        taxRate = new BigDecimal(0);
                    }

                    BigDecimal qty = new BigDecimal(item.getQuantity());
                    BigDecimal itemTax = taxRate.multiply(amt.multiply(qty));
                    totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);

                }
            }
        }

        return totalTaxAmount;
    }
    
    /**
     * Calculates the Tax for Item in Invoice
     * @param invoiceItems - all items from Invoice
     * @return BigDecimal - all taxes
     */
    public static BigDecimal calculateInvoiceItemTaxes(OrderItemDescDataVector invoiceItems) {

        BigDecimal totalTaxAmount = new BigDecimal(0);
        if (invoiceItems != null) {

            Iterator it = invoiceItems.iterator();
            while (it.hasNext()) {

                OrderItemDescData itm = (OrderItemDescData) it.next();
                if (Utility.isTaxableOrderItem(itm.getOrderItem())) {

                    BigDecimal amt = Utility.getBestCostFromInvoiceDistDetail(itm.getWorkingInvoiceDistDetailData());
                    BigDecimal taxRate = itm.getOrderItem().getTaxRate();
                    if (taxRate == null){
                        taxRate = new BigDecimal(0);
                    }

                    BigDecimal qty = new BigDecimal(itm.getWorkingInvoiceDistDetailData().getDistItemQuantity());
                    BigDecimal itemTax = taxRate.multiply(amt.multiply(qty));
                    itm.setCalculatedSalesTax(itemTax);
                    totalTaxAmount = Utility.addAmt(totalTaxAmount, itemTax);

                } else {
                    itm.setCalculatedSalesTax(new BigDecimal(0));
                }
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
}
