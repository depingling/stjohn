package com.cleanwise.service.apps.dataexchange;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.InterchangeData;
import com.cleanwise.service.api.value.TradingProfileData;

/**
 * Does nothing, but does not generate an error.
 * @author  BStevens
 */
public class InboundIgnore implements InboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
  
    /**
     *Sets the object that instantiated this InboundTransaction.
     */
    public void setTranslator(Translator handler){}
    
    /**
     *Preforms the translator.  It should also determine the TradingPartnerData that is
     *appropriate if the translator does not make this (as in edi where it is read out of the
     *file, as opposed to being determined by the file name).
     */
    public void translate(String s) throws Exception{
        log.info("Ignoring file");
    }
    
    /**
     *returns true if the data could not be processed.
     */
    public boolean isFail(){
        return false;
    }
    
    /**
     *get the integration requests to be processed.
     */
    public IntegrationRequestsVector getRequestsToProcess(){
        return new IntegrationRequestsVector();
    }
    
    /**
     *Returns a report to be logged.  Should be human readable.
     */
    public String getTranslationReport(){return "";}
    
    /**
     *Returns the TradingProfileData this can be determined by reading in the file or be set
     *by the inbound translator.
     */
    //TradingProfileData getProfile();
    
    /**
     *Sets the trading partner when the trading partner is not determined by reading the file
     *as it is in EDI.
     */
    public void setProfile(TradingProfileData pTradingProfileData){
    }

	public void translateInterchange() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void translateInterchangeContent() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void translateInterchangeHeader() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void translateInterchangeTrailer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public InterchangeData createInterchangeObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public ElectronicTransactionData createTransactionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public ElectronicTransactionData getTransactionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public Translator getTranslator() {
		// TODO Auto-generated method stub
		return null;
	}
}
