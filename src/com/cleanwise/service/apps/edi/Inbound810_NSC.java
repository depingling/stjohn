package com.cleanwise.service.apps.edi;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import com.americancoders.edi.*;

import java.rmi.RemoteException;
import java.util.*;
import java.math.BigDecimal;

import org.apache.log4j.Logger;



/**
 * @author deping
 */
public class Inbound810_NSC extends Inbound810_JDChina
{
	protected Logger log = Logger.getLogger(this.getClass());

	protected BigDecimal getItemReceivedCost(String costStr){
		BigDecimal cost = new BigDecimal(costStr);
		if (cost.doubleValue() <= 0.01){
			excludedTotal = excludedTotal.add(cost);
			return ZERO;
		} else
			return cost;
	}
		
}

