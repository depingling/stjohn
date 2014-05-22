/*
 * InboundEDIStubEmailer.java
 *
 * Created on June 17, 2004, 2:00 PM
 */

package com.cleanwise.service.apps.edi;
import java.io.File;
import java.util.List;
import java.util.LinkedList;

import org.apache.log4j.Logger;


import com.americancoders.edi.x12.X12Envelope;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.utils.Constants;
/**
 * Inbound edi "parser"  This class will take in a file and email the contents to a
 * given email address.  This would be for a file type that is not implemented yet and 
 * we would like to be notified of it existence, but not actually parse the file.  Oboe
 * should take care of issuesing a positiive 997 to indicate we parsed the file, but beyond
 * that no further automated action needs to be taken.
 * @author  BStevens
 */
public class InboundEDIStubEmailer extends InboundEdiSuper{
	private static final Logger log = Logger.getLogger(InboundEDIStubEmailer.class);
    private static List sentFiles = new LinkedList();
    
    /** Sends the email 
     * @throws Exception */
    public void extract() throws Exception {        
        String fileName = this.ediHandler.getTranslator().getInputFilename();
        if(!sentFiles.contains(fileName)){
            sentFiles.add(fileName);
            String to = ediHandler.getTranslator().getIntegrationEmailAddress();
            String sub = "recieved file for group sender/reciever: "+
            ediHandler.getTranslator().getProfile().getGroupSender()+"/"+
            ediHandler.getTranslator().getProfile().getGroupReceiver();
            
            EventEmailDataView eventEmailData = new EventEmailDataView();
            eventEmailData.setEventEmailId(0);
            eventEmailData.setFromAddress(Constants.EMAIL_ADDR_NO_REPLY);
            eventEmailData.setToAddress(to);
            eventEmailData.setSubject(sub);
            eventEmailData.setEmailStatusCd(Event.STATUS_READY);
            eventEmailData.setText("filename="+fileName+"\n\n"+ts.getFormattedText(X12Envelope.X12_FORMAT));
            EventData eventData = Utility.createEventDataForEmail();
            EventEmailView eev = new EventEmailView(eventData, eventEmailData);

            //this.ediHandler.appendIntegrationRequest(eev);
            Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
            eventEjb.addEventEmail(eev, "InboundTranslate");
        }
    }
    
}
