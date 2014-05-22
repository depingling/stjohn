/**
 * 
 */
package com.cleanwise.service.apps.dataexchange;

/**
 * @author ssharma
 *
 */
public class OutboundPunchOutOrderCXMLPollock extends OutboundPunchOutOrderCXML{
	
	public String getLanguage(){
    	return "en";
    }
	
	public String getCurrencyCode(){
    	return "USD";
    }

	public boolean getRequiresSharedSecret(){
    	return true;
    }
}
