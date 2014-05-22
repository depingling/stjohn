/*
 * DistributorInvoiceFreightTool.java
 *
 * Created on April 14, 2003, 1:58 PM
 */

package com.cleanwise.service.api.util;
import com.cleanwise.service.api.value.*;
import java.math.BigDecimal;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 * Runs through the various freight rules for a distributor invoice and provides access into whether the
 * freight on this invoice is correctly charged or not.  Takes into account whether there is any freight
 * on the supplied invoice at all.
 * @author  bstevens
 */
public class DistributorInvoiceFreightTool {
    private static final Logger log = Logger.getLogger(DistributorInvoiceFreightTool.class);
    static final BigDecimal ZERO = new BigDecimal(0);
    
    FreightHandlerView mFreightHandler;

    /** Holds value of property overMinimumOrderWithFrieght. */
    private boolean overMinimumOrderWithFrieght;
    private boolean ignoreMinimumOrder;
    //private boolean overChargedFreight;
    private boolean mHighFreightCharge;
    private boolean freightOverOrderFreightForDist;
    private boolean backOrderWithFreight;
    private boolean freeTerritoryWithFreight;
    private boolean freightHandlerFreightNotAllowedWithFreight;
    private BigDecimal invoiceFrt = null;
    
    /** Creates a new instance of DistributorInvoiceFreightTool */
    public DistributorInvoiceFreightTool
	(BigDecimal pPurchaseOrderSubTotal,
	 OrderItemDataVector pOrderItems,
	 InvoiceDistData pInvoice,
	 DistributorData pDistributor,
	 FreightHandlerView pFreightHandler,
	 InvoiceDistDataVector pExistingInvoices,
	 BusEntityTerrViewVector pRelevantOrAllBusEntityTerrDataVector,
	 OrderFreightDataVector pRelevantOrAllOrderFreightDataVector) {
        
	boolean orderRouted = false; 
	mFreightHandler = pFreightHandler;

        //if there is no freight just return
        if(pInvoice.getFreight() == null || 
	   pInvoice.getFreight().compareTo(ZERO) <= 0){
            return;
        }
        
	
	BigDecimal frt = pInvoice.getFreight();
	if ( null == frt ) {
	    frt = ZERO;
	}
	invoiceFrt= frt;

	int maxDistFrt = 0;
	String s = pDistributor.getMaxInvoiceFreightAllowed();
	try {
	    if ( null != s && s.length() > 0 ) {
		maxDistFrt = Integer.parseInt(s);
	    }
	} catch (Exception e) {
	    maxDistFrt = -1;
	    log.info("******Invalid Freight="+s
			       + " for distributor " + pDistributor );
	}
	if ( maxDistFrt > 0 && frt.intValue() > maxDistFrt ) {
	    mHighFreightCharge = true;
	}
	else {
	    mHighFreightCharge = false;
	}

        BigDecimal minimumOrderAmount = pDistributor.getMinimumOrderAmount();
        if(minimumOrderAmount == null){
            minimumOrderAmount=ZERO;
        }
        if(minimumOrderAmount.compareTo(pPurchaseOrderSubTotal) < 0){
            overMinimumOrderWithFrieght = true;
        }

	if ( Boolean.FALSE.equals
	     (pDistributor.getIgnoreOrderMinimumForFreight())) {
	    ignoreMinimumOrder = false;
	} else {
	    ignoreMinimumOrder = true;
	}

        //determine if this purchase order was delivered to a freight free territory
        freeTerritoryWithFreight = false;
        String lPostalCode = pInvoice.getShipToPostalCode();
        int i = lPostalCode.length();
        while (lPostalCode.length() > 1) {
            lPostalCode = lPostalCode.substring(0, i);
            i--;
            Iterator it = pRelevantOrAllBusEntityTerrDataVector.iterator();
            while(it.hasNext()){
                BusEntityTerrView terr = (BusEntityTerrView) it.next();
                if(lPostalCode.equals(terr.getPostalCode())){
                    if(isFreightFreeZip(terr.getBusEntityTerrFreightCd())) {
                        freeTerritoryWithFreight = true;
                        break;
                    }
                }
            }
        }
        
        //compare the freight on the order if this distributor is setup with freight tables
        //and the order has order freight tables.  If the distributor is setup with freight
        //tables and the order does not have any freight on it then it is assumed that this
        //is an over charge (i.e. the rules evaluated and were not present at time of order).
        if(pDistributor.getFreightTable() != null && pDistributor.getFreightTable().hasValidFreightCriteria()){
        	if(pRelevantOrAllOrderFreightDataVector != null && !pRelevantOrAllOrderFreightDataVector.isEmpty()){
        		Iterator it = pRelevantOrAllOrderFreightDataVector.iterator();
        		BigDecimal totalAmt = ZERO;
        		boolean hasEst = false;
        		while(it.hasNext()){
        			OrderFreightData ordFrt = (OrderFreightData) it.next();
        			if(ordFrt.getBusEntityId() == pDistributor.getBusEntity().getBusEntityId()){
        				totalAmt = Utility.addAmt(totalAmt, ordFrt.getAmount());
        				if(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(ordFrt.getFreightTypeCd())){
            				hasEst = true;
            			}
        			}
        		}
        		if(!hasEst && totalAmt.compareTo(frt) < 0){
					freightOverOrderFreightForDist = true;
				}
        	}else{
        		freightOverOrderFreightForDist = true;
        	}
        }
        
        //determine if this purchase order is a backorder
        if(Boolean.FALSE.equals(pDistributor.getAllowFreightOnBackorders())){
            Iterator it = pExistingInvoices.iterator();
            while(it.hasNext()){
                InvoiceDistData invoice = (InvoiceDistData) it.next();
                String statusCd = invoice.getInvoiceStatusCd();
                if(!(statusCd.equals(RefCodeNames.INVOICE_STATUS_CD.CANCELLED) ||
                statusCd.equals(RefCodeNames.INVOICE_STATUS_CD.REJECTED) ||
                statusCd.equals(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE))){
                    //we know the invoice is not either canceled or duplicate, make sure it is not the one we are
                    //dealing with now, and make sure that the invoice date is before the one we are processing
                    //in case we are reprocessing a resolved exception
                    if(!invoice.getInvoiceNum().equalsIgnoreCase(pInvoice.getInvoiceNum()) && invoice.getInvoiceDate().compareTo(pInvoice.getInvoiceDate()) < 0){
                        backOrderWithFreight = true;
                        break;
                    }
                }
            }
        }
        
        //set freightHandlerFreightNotAllowedWithFreight variable
        if(orderRouted && mFreightHandler != null && !isFreightHandlerFreightAllowed()){
            freightHandlerFreightNotAllowedWithFreight=true;
        }
    }
    
    /**
     *Indicates that this order was shipped via a freigh handler and the frieght was charged yet it is configured to not allow freight
     */
    public boolean isFreightHandlerFreightNotAllowedWithFreight() {
        return freightHandlerFreightNotAllowedWithFreight;
    }
    
    /**
    *Indicates that the distributor is configured with a freight table
    *and the freight calculated at the time of the order is greater than
    *the freight that is being charged.
    */
    public boolean isFreightOverOrderFreightForDist() {
        return freightOverOrderFreightForDist;
    }

    /**
     *Indicates that freight should be allowed on this order regardless of the other rules as a frieght handler was used
     *and the frieght handler that was used shoul in fact allow freight
     */
    public boolean isFreightHandlerFreightAllowed() {
	if ( null != mFreightHandler &&
	     null != mFreightHandler.getAcceptFreightOnInvoice() &&
	     "true".equals(mFreightHandler.getAcceptFreightOnInvoice())) {
	    // This freight handler is set up to allow
	    // freight charges.
	    return true;
	}
	return false;
    }

    /**
     *returns true if any of the freight issues have been triggered.
     */
    public boolean isFreightOverMax() {
	return mHighFreightCharge;
    }

    public boolean isOverChargedFreight() {

	// Check the freight amount first.
	if ( null == invoiceFrt ) {
	    return false;
	}
	if ( invoiceFrt.compareTo(new BigDecimal(2.5)) <= 0 ) {
	    // The total freight is less than or equal
	    // to 2.5.  Allow it as this may be an invoice
	    // for an order headed to a freight free zone
	    // with a fuel surcharge.  per Holly.
	    // Durval, 11/14/2005.
	    // Durval, 1/9/2005. Allow 2.5 for Kellermeyer, per Holly.
	    return false;
	}

	if (isFreightOverMax()) {
	    return true;
	}

        if (isFreightOverOrderFreightForDist()) {
            return true;
        }

	if ( overMinimumOrderWithFrieght &&
	     ignoreMinimumOrder &&
	     backOrderWithFreight == false &&
	     freeTerritoryWithFreight == false ) {
	    // This order was charged freight even
	    // though the order is over the minimum.
	    // This is allowed for this distributor.
	    return false;  
	}

	if ( isFreightHandlerFreightAllowed() ) {
	    // Allow the freight charge as this invoice
	    // was shipped via a freight handler for which
	    // we must pay (LTL).
	    return false;
	}

        return overMinimumOrderWithFrieght || 
	    backOrderWithFreight || 
	    freeTerritoryWithFreight;
    }
    
    /** Getter for property overMinimumOrderWithFrieght.
     * @return Value of property overMinimumOrderWithFrieght.
     *
     */
    public boolean isOverMinimumOrderWithFrieght() {
        return this.overMinimumOrderWithFrieght;
    }
    
    /** Getter for property backOrderWithFreight.
     * @return Value of property backOrderWithFreight.
     *
     */
    public boolean isBackOrderWithFreight() {
        return this.backOrderWithFreight;
    }
    
    /** Getter for property freeTerritoryWithFreight.
     * @return Value of property freeTerritoryWithFreight.
     *
     */
    public boolean isFreeTerritoryWithFreight() {
        return this.freeTerritoryWithFreight;
    }

    private static boolean isFreightFreeZip(String pBusEntityTerrFreightCd) {
        if ( null == pBusEntityTerrFreightCd ) {
            return false;
        }
        if(pBusEntityTerrFreightCd.equals
           (RefCodeNames.BUS_ENTITY_TERR_FREIGHT_CD.NO_FREIGHT)){
            return true;
        }
        return false;

    }
       
    public static boolean isFreightFreeArea(BusEntityTerrDataVector pV) {
        for ( int i = 0; pV != null && i < pV.size(); i++ ) {
            BusEntityTerrData terr = (BusEntityTerrData)pV.get(i);
            if ( isFreightFreeZip(terr.getBusEntityTerrFreightCd()) ) {
                return true;
            }
        }
        return false;
    }
    
}
