package com.cleanwise.service.apps.dataexchange;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Node;

import com.cleanwise.service.api.util.Utility;

/**
*Delegates most of the logic back to the InboundcXMLOrder class but it does anything that is specific to 
*Orange Lake.
*/
public class InboundOrangeLakecXMLOrder extends InboundcXMLOrder{
	private static final Logger log = Logger.getLogger(InboundOrangeLakecXMLOrder.class);
   
	@Override
	protected void checkSecurity(Node credentialNode) throws Exception {
		if (credentialNode == null) {
			throw new Exception("Could not parse cXML (Credential)");
		}
        Node passwordNode = credentialNode.selectSingleNode("SharedSecret");
        if (passwordNode == null) {
        	throw new Exception("Could not parse cXML (SharedSecret)");
        }
        ////Orange Lake will be sending an encrypted shared secret, so encrypt the password
        ////specified on the Trading Partner before comparing it to the value passed in.
        //String password = passwordNode.getText();
        //String encrytpedPassword = PasswordUtil.getMD5Hash(InboundOrangeLakeCXmlPunchOutLogon.USERNAME, getPassword());
        //if(!encrytpedPassword.equalsIgnoreCase(password)){
        //	throw new Exception("Authorization failed.  Check username and trading profile authorization setup.");
        //}
        ////Orange Lake will be sending an encrypted shared secret, so encrypt the password
        ////specified on the Trading Partner before comparing it to the value passed in.
        //String password = passwordNode.getText();
        //String encrytpedPassword = PasswordUtil.getMD5Hash(USERNAME, getPassword());
        //if(!encrytpedPassword.equalsIgnoreCase(password)){
        //	throw new Exception("Authorization failed.  Check username and trading profile authorization setup.");
        //}
        String password = passwordNode.getText();
        String storedPassword = getPassword();
        if(!Utility.isSet(password) || !Utility.isSet(storedPassword) ||
        		!storedPassword.equalsIgnoreCase(password)){
        	throw new Exception("Authorization failed.  Check username and trading profile authorization setup.");
        }
	}
    
	@Override
    protected String parseItemLineNumber(Node pItemNode) {
        String custLineS = null;
        Node itemDetailNode = pItemNode.selectSingleNode("ItemDetail");
        Iterator<Node> extrinsicIt = itemDetailNode.selectNodes("Extrinsic").iterator();
        while (extrinsicIt.hasNext()) {
        	Node exNode = extrinsicIt.next();
        	String nodeName = exNode.valueOf("@name");
        	if ("LINENUM".equalsIgnoreCase(nodeName)){
        		custLineS = exNode.getText();
        	}
        }
        return custLineS;
    }
}
