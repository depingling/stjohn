package com.cleanwise.service.apps.edi;

import java.math.BigDecimal;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.OrderItemData;

import org.apache.log4j.Logger;



/**
 * @author Deping
 */
public class Outbound850DistributorV2_NSC extends Outbound850DistributorV2 {
	protected Logger log = Logger.getLogger(this.getClass());
    

    /** constructor for class Outbound850
     *@throws OBOEException - most likely transactionset not found
     */
    public Outbound850DistributorV2_NSC()
    throws OBOEException {
    	removeNoneNumCharFormPostalCode = true;
    }
    
    protected BigDecimal getPoCost(OrderItemData oi){
        if(oi.getDistUomConvCost() != null && oi.getDistUomConvCost().compareTo(ZERO) > 0){
            return oi.getDistUomConvCost();
        }else{
        	if (oi.getDistItemCost() != null && oi.getDistItemCost().compareTo(ZERO)==0)
        		return new BigDecimal(0.01);
            return oi.getDistItemCost();
        }
    }
}
