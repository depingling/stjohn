/*
 * SystemVerification.java
 *
 * Created on January 7, 2004, 2:50 PM
 */

package com.cleanwise.service.apps;
import com.meterware.httpunit.*;
import java.util.List;
import org.xml.sax.SAXException;
import java.io.*;
import com.cleanwise.service.api.util.Utility;
/**
 *
 * @author  bstevens
 */
public class SystemVerification {
    
    /** Creates a new instance of SystemVerification */
    public SystemVerification() {
        HttpUtil lHttpUtil = new HttpUtil();
        lHttpUtil.setUpOptions(true,false,true,false);
        String portStr = System.getProperty("port");
        String host = System.getProperty("host");
        String user = System.getProperty("userName");
        String pass = System.getProperty("passWord");
        String searchText = System.getProperty("searchText");
        String reason = "unknown";
        boolean success = false;
        int port = 80;
        if(!Utility.isSet(searchText)){
            reason = "no searchText specified";
        }else{
            if(Utility.isSet(portStr)){
                port = Integer.parseInt(portStr);
            }
            if(!Utility.isSet(host)){
                host = "localhost";
            }
            lHttpUtil.setPort(port);
            lHttpUtil.setHost(host);
            lHttpUtil.setUserName(user);
            lHttpUtil.setPassword(pass);
            
            try{
                WebConversation wc = lHttpUtil.login();
                WebResponse wr = wc.getCurrentPage();
                try{
                	String linkId = "sys_message_continue";
                	WebLink link = wr.getLinkWithID(linkId);
                	if(link != null){
                		wr = link.click();
                	}
                }catch(Exception e){e.printStackTrace();}
                String text = wr.getText();
                if(text.indexOf(searchText)<=0){
                    reason = "Wrong Text ("+searchText+" not found after login)";
                }else{
                    success = true;
                }
                lHttpUtil.logOut(wc);
            }catch(java.net.ConnectException e){
                reason = e.getMessage();
            }catch(Exception e){
                reason = e.getMessage();
            }
        }
        
        if(!success){
            String toAddress = System.getProperty("email");
            String messg = "Error verifying System: \n"+reason;
            if(Utility.isSet(toAddress)){
                try{
                    String subj = host + ":" + port + ", " 
			+ this.getClass().getName() + " Failed";
                    ApplicationsEmailTool.sendEmail(toAddress,subj, messg);
                }catch(Exception e){
                    System.err.println("Failed to send message");
                    System.err.println(messg);
                    e.printStackTrace();
                }
            }else{
                System.err.println(messg);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SystemVerification();
    }
    
}
