package com.cleanwise.service.apps.edi;

import org.apache.log4j.Logger;


import com.americancoders.edi.*;


/** code to parse 810 transaction
 *<br>class 810 Advanced Ship Notice
 *<br>
 *@author Deping
 */
public class Inbound810 extends Inbound810Super
{
	protected Logger log = Logger.getLogger(this.getClass());
	/**
	 * Extract all segments included in ST - SE Segment
	 */
	public void extract()
	throws OBOEException
	{
		super.extract();
	}	
}
