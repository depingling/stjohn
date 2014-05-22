package com.cleanwise.service.apps.edi;

import org.apache.log4j.Logger;



/** code to parse 810 transaction
 *<br>class 810 Advanced Ship Notice
 *<br>
 *@author Deping
 */
public class Inbound810_FBG extends Inbound810Super
{
	protected Logger log = Logger.getLogger(this.getClass());
	public Inbound810_FBG(){
		distSkuType = "PN";		
	}
}
