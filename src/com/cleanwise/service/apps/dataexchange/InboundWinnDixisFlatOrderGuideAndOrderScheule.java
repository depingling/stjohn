package com.cleanwise.service.apps.dataexchange;

import java.util.List;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.FlatOrderGuideRequestView;
import com.cleanwise.service.api.value.IntegrationRequestsVector;

public class InboundWinnDixisFlatOrderGuideAndOrderScheule  extends InboundFlatFile{
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
		//if line starts with quantity, or just has an "E" with no data ignore it
        if(!unParsedLine.startsWith("Commodity") && !unParsedLine.startsWith("E,,,,") && !unParsedLine.startsWith("E,null,null,null")){
            log.info("Ignoring line starting with commodity: "+unParsedLine);
        	super.parseLine(pParsedLine);
        }
    }

    /**
     * Called when the object has successfully been parsed
     //set the order guide name to the file name
     * @throws Exception 
     */

    protected void processParsedObject(Object pParsedObject) throws Exception{
    	if(translator.getInputFilename() != null){
    		FlatOrderGuideRequestView flat =(FlatOrderGuideRequestView) pParsedObject;
			flat.setOrderGuideName(translator.getInputFilename());
    	}
    	super.processParsedObject(pParsedObject);
    }
    
    /**
     * This method is called when the file is done processing.  Implementing methods should
     * check the failed flag if post processing should only be done for successfully processed
     * files.  The default implementation is to do nothing.
     * Re-organize the list of object into one object and insert it as one object
     */
    protected void doPostProcessing(){
    	IntegrationRequestsVector newList = new IntegrationRequestsVector();
    	newList.addAll(getIntegrationRequests());
    	
    	//refresh the list with our new data
    	getIntegrationRequests().clear();
    	getIntegrationRequests().add(newList);
    }
}

