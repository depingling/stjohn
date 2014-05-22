package com.cleanwise.service.apps.dataexchange;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.Utility;

public class InboundRedcoatsInvoice extends InboundInvoiceFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
    /**
     *Overides to look for a set of text to ignore
     * @throws Exception 
     */
    public void parseLine(List pParsedLine) throws Exception{
        String unParsedLine = Utility.toCommaSting(pParsedLine); //unparse the line to perform some tests on the "raw" data
        if(unParsedLine == null){
        	return;
        }
        unParsedLine = unParsedLine.trim();
        if(!unParsedLine.startsWith("BEGIN") && !unParsedLine.startsWith("END")){
        	super.parseLine(pParsedLine);
        }
    }
    
    protected void doPostProcessing(){
    	if(translator.getInputFilename() != null){
    		try{
	    	SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
	    	int year = Calendar.getInstance().get(Calendar.YEAR);
	    	int month = Calendar.getInstance().get(Calendar.MONTH);
	    	int start = translator.getInputFilename().indexOf('-') + 1;
	    	int end = start + 4;
	    	String text = translator.getInputFilename().substring(start,end);
	    	if(text.startsWith("12") && month <= 3){
	    		text = text + Integer.toString(year-1);
	    	}else{
	    		text = text + Integer.toString(year);
	    	}
	    	Date invDate = sdf.parse(text);
	    	
	    	Iterator it = parsedObjects.iterator();
			while(it.hasNext()){
				InboundInvoiceFlatFileData flat =(InboundInvoiceFlatFileData) it.next();
				flat.setInvoiceDate(invDate);
			}
    		}catch(Exception e){
    			log.info("Could not figure out invoice date, defaulting to today",e);
    			throw new RuntimeException(e.getMessage());
    		}
    	}
		super.doPostProcessing();
    }

}
