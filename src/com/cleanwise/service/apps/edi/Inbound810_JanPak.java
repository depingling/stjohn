package com.cleanwise.service.apps.edi;



import org.apache.log4j.Logger;


import com.americancoders.edi.*;

/** code to parse 810 transaction
 *<br>class 810 Invoice
 *<br>
 *@author Deping
 */

public class Inbound810_JanPak extends Inbound810Super
{	
	protected Logger log = Logger.getLogger(this.getClass());

	private static String[] freightSKUsL = {".202069",".201368",".202068"};

	/**
	 * Extract all segments included in ST - SE Segment
	 */
	public void extract()
	throws OBOEException
	{
		this.freightSKUs = freightSKUsL;
		super.extract();
	}
}


