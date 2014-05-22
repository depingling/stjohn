package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.InterchangeData;

public interface Transaction {
	/**
     *Sets the object that instantiated this Transaction (Inbound or Outbound).
     */
    void setTranslator(Translator translator);
	/** failed to process current inbound or outbound trasaction */
	boolean isFail();
	
	/** list of object need to be updated */
	IntegrationRequestsVector getRequestsToProcess() ;
	
	/** text version of report about current transaction */
	String getTranslationReport() ;
	
	/** create InterchangeData object */
	InterchangeData createInterchangeObject();
	
	/** create ElectronicTransactionData object */
	ElectronicTransactionData createTransactionObject();
	
}
