package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.americancoders.edi.x12.*;
import java.util.*;
import com.cleanwise.service.api.value.*;
import java.math.BigDecimal;
import com.cleanwise.service.api.util.*;


/** code to parse 810 transaction
 *<br>class 810 Advanced Ship Notice
 *<br>
 *@author Deping
 */
public class Inbound810 extends Inbound810Super
{
	/**
	 * Extract all segments included in ST - SE Segment
	 */
	public void extract()
	throws OBOEException
	{
		super.extract();
	}	
}
