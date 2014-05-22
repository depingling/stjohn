/**
 * 
 */
package com.cleanwise.service.apps.dataexchange;

import java.rmi.RemoteException;
import java.sql.Connection;

import org.dom4j.Node;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 * @author ssharma
 *
 */
public class InboundGCACXmlPunchOutLogon extends InboundcXMLPunchOutLogon {
	
	protected UserAcessTokenViewData getAccess(Node nodeToOperateOn) throws Exception{
		
		UserAcessTokenViewData tokenView;
		LdapItemData ldap = new LdapItemData();
		Connection pCon = null;
		try{
			
			Node credentialNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Header/Sender/Credential");
	        if(credentialNode == null){
	        	throw new Exception("Could not parse cXML (Credential)");
	        }
	        Node userNameNode = credentialNode.selectSingleNode("Identity");
	        Node passNameNode = credentialNode.selectSingleNode("SharedSecret");
			
			if(userNameNode == null){
				throw new Exception("Could not parse cXML (Sender/Identity)");
			}
			
	        if(passNameNode == null){
	        	throw new Exception("Could not parse cXML (SharedSecret)");
	        }
	        
	        String usrname = userNameNode.getText();
	        String pass = passNameNode.getText();
		
	        String passval = mPassword;
	        if(!pass.equalsIgnoreCase(passval)){
	        	throw new Exception("Could not validate password!");
	        }
	        
			ldap.setUserName(usrname);
		
	        ldap.setPassword(pass);
					
			tokenView = Translator.intSvc.createAccessToken(ldap,true);
			return tokenView;
			
		
		}catch(Exception exc){
			exc.printStackTrace();
			throw new Exception(exc.getMessage());
		}finally{
			if(pCon!=null){
				pCon.close();
			}
		}
	}
	
	protected void getChangeLocationEnabled(){
		changeLocation = true;
    }

}
