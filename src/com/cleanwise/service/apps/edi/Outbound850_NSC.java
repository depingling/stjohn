package com.cleanwise.service.apps.edi;


import org.apache.log4j.Logger;


/**
 * Formats the order extraction into a EDI document, the format specified by NSC.
 *
 * @author Deping
 */
public class Outbound850_NSC extends Outbound850 {
	protected Logger log = Logger.getLogger(this.getClass());
	
	public String getFileName()throws Exception{
    	java.text.SimpleDateFormat frmt = new java.text.SimpleDateFormat(
        "yyyyMMddHHmmss");
        String now = frmt.format(new java.util.Date());
        Thread.currentThread().sleep(1000); // added here to prevent same filename 
        return "po850_cleanwisedat" + now +".txt";
    }
}

