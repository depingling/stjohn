/*
 * OutboundTransaction.java
 *
 * Created on October 23, 2002, 11:39 AM
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.ElectronicTransactionData;

/**
 * Inbound translation will instantiate an implementing class and call the 
 * appropriate methods.  Class should be defined in the trading partner configuration
 * screens.
 * @author  bstevens
 */
public interface InboundTransaction extends Transaction {
    /**
     *Preforms the translation.  
     */
    void translate(String s) throws Exception;
    public void translateInterchange() throws Exception;    
    void translateInterchangeHeader() throws Exception;    
    void translateInterchangeTrailer() throws Exception;    
    void translateInterchangeContent() throws Exception;
    ElectronicTransactionData getTransactionObject();
    Translator getTranslator();
    //void translateTransaction()throws Exception;
    //void translateTransactionHeader()throws Exception;
	//void translateTransactionContent() throws Exception;
	//void translateTransactionTrailer() throws Exception;    
}
