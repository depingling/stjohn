/*
 * OutboundTransaction.java
 *
 * Created on October 23, 2002, 11:39 AM
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;

/**
 * Outbound translation will instantiate an implementing class and call the 
 * appropriate methods.  Class should be defined in the trading partner configuration
 * screens.
 * @author  bstevens
 */
public interface OutboundTransaction extends Transaction {	
    void buildInterchangeHeader()throws Exception;
    void buildInterchangeContent()throws Exception;
    void buildInterchangeTrailer() throws Exception;
    //public void buildTransaction()throws Exception;
    //public void buildTransactionHeader()throws Exception;
	//public void buildTransactionContent() throws Exception;
	//public void buildTransactionTrailer() throws Exception;


    /**
     * get outbound filename.
     */
    String getFileName()throws Exception;//may be left unimplemented (return null) and the default will be used
    /**
     * get outbound file extension e.g. .xml .txt....
     */
    String getFileExtension()throws Exception;//may be left unimplemented (return null) and the default will be used
    /**
     * set outbound data list to be process.
     */
    void setTransactionsToProcess(OutboundEDIRequestDataVector transactions);
    
    String getCounterStr() ;
    
}
