package com.cleanwise.service.apps.dataexchange;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Node;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.LdapItemData;

public class InboundOrangeLakeCXmlPunchOutLogon extends InboundcXMLPunchOutLogon {

    protected LdapItemData parseLdapItem(Node nodeToOperateOn) throws Exception{
    	LdapItemData ldap = new LdapItemData();
    	try {
	    	Node credentialNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Header/Sender/Credential");
	        if (credentialNode == null) {
	        	throw new Exception("Could not parse cXML (Credential)");
	        }
	        Node passwordNode = credentialNode.selectSingleNode("SharedSecret");
	        if (passwordNode == null) {
	        	throw new Exception("Could not parse cXML (SharedSecret)");
	        }
	        String password = passwordNode.getText();
	        String storedPassword = getPassword();
	        if(!Utility.isSet(password) || !Utility.isSet(storedPassword) ||
	        		!storedPassword.equalsIgnoreCase(password)) {
	        	throw new Exception("Authorization failed.  Check username and trading profile authorization setup.");
	        }
	        Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/PunchOutSetupRequest");
	        if (requestNode == null) {
	        	appendErrorMsgs("Could not parse request node", true);
	            return null;
	        }
	        String userName = null;
	        List nodeList = requestNode.selectNodes("Extrinsic");
	        Iterator it = nodeList.iterator();
	        while (it.hasNext()) {
	        	Node n = (Node)it.next();
	        	String nodeName = n.valueOf("@name");
	        	//Orange Lake uses a node name of "User" instead of the default value of "UniqueName"
	        	if (nodeName != null && nodeName.equalsIgnoreCase("User")){
	        		userName = n.getText();
	        		break;
	        	}
	        }
	        if (!Utility.isSet(userName)) {
	        	appendErrorMsgs("Could not parse User node", true);
	            return null;
	        }
	        ldap.setUserName(userName);
    	}
    	catch(Exception exc) {
    		exc.printStackTrace();
    		throw new Exception(exc.getMessage());
    	}
    	return ldap;
    }
	
	//Orange Lake requires that users have the ability to change locations, so the default
	//behavior that disallows this must be overridden.
	protected void getChangeLocationEnabled(){
		changeLocation = true;
    }
	
}
