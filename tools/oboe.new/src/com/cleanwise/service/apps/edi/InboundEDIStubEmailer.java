/*
 * InboundEDIStubEmailer.java
 *
 * Created on June 17, 2004, 2:00 PM
 */

package com.cleanwise.service.apps.edi;
import java.io.File;
import java.util.List;
import java.util.LinkedList;
import com.cleanwise.service.apps.ApplicationsEmailTool;
/**
 * Inbound edi "parser"  This class will take in a file and email the contents to a
 * given email address.  This would be for a file type that is not implemented yet and 
 * we would like to be notified of it existence, but not actually parse the file.  Oboe
 * should take care of issuesing a positiive 997 to indicate we parsed the file, but beyond
 * that no further automated action needs to be taken.
 * @author  BStevens
 */
public class InboundEDIStubEmailer extends InboundSuper{
    private static List sentFiles = new LinkedList();
    
    /** Sends the email */
    public void extract() {
        
        try{
            String fileName = this.mHandler.translator.getInputFilename();
            if(!sentFiles.contains(fileName)){
                sentFiles.add(fileName);
                String to = mHandler.getInboundTranslator().getIntegrationEmailAddress();
                String sub = "recieved file for group sender/reciever: "+
                    mHandler.getProfile().getGroupSender()+"/"+
                    mHandler.getProfile().getGroupReceiver();
                File f = new File(fileName);
                ApplicationsEmailTool.sendEmail(to, sub,"",f);
            }
        }catch(Exception e){
            //print the stack trace and set us up as having failed.
            e.printStackTrace();
            this.mHandler.setFail(true);
        }
    }
    
}
