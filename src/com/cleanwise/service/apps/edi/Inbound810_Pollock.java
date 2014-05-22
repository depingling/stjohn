package com.cleanwise.service.apps.edi;

import org.apache.log4j.Logger;



/**
 * @author deping
 */
public class Inbound810_Pollock extends Inbound810_JDChina
{
	protected Logger log = Logger.getLogger(this.getClass());

	public Inbound810_Pollock(){
		reqInvoice.setCheckTotal(false);
	}

}

