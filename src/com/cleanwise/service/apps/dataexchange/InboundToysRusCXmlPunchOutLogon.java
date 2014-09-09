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
public class InboundToysRusCXmlPunchOutLogon extends InboundcXMLPunchOutLogon {
	
	public InboundToysRusCXmlPunchOutLogon(){
		siteRefNumType = RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER;
	}    

	/**
	 * Toys R Us will send user name at PunchOutSetupRequest/Contact/Email
	 */
	protected UserAcessTokenViewData getAccess(Node nodeToOperateOn) throws Exception{
		UserAcessTokenViewData tokenView;
		LdapItemData ldap = new LdapItemData();
		Connection pCon = null;
		try{
			Node credentialNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Header/Sender/Credential");
	        if(credentialNode == null){
	        	throw new Exception("Could not parse cXML (Credential)");
	        }
	        
	        Node passNameNode = credentialNode.selectSingleNode("SharedSecret");
	        if(passNameNode == null){
	        	throw new Exception("Could not parse cXML (SharedSecret)");
	        }
	        
	        String pass = passNameNode.getText();
		
	        String passval = mPassword;
	        if(!pass.equalsIgnoreCase(passval)){
	        	throw new Exception("Authorization failed.  Shared secret does not match trading profile authorization setup!");
	        }
	        
	        Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/PunchOutSetupRequest");
	        if(requestNode == null){
	        	appendErrorMsgs("Could not parse request node", true);
	            return null;
	        }
			
	        Node emailNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/PunchOutSetupRequest/Contact/Email");
	        
	        if(emailNode == null){
	            throw new Exception("Could not parse cXML (Email)");
	        }
	        
	        String username = emailNode.getText();
	        
			//check if user exists in our system else login as default user
			DBCriteria cr = new DBCriteria();
			pCon= this.getConnection();
			
			cr.addEqualTo(UserDataAccess.USER_NAME, username);
			cr.addEqualTo(UserDataAccess.USER_STATUS_CD, RefCodeNames.USER_STATUS_CD.ACTIVE);
			
			UserDataVector udv = UserDataAccess.select(pCon, cr);
			
			if(udv!=null && udv.size()>0){
				ldap.setUserName(username);
			}else{
				throw new Exception("User Name not valid: " + username);
			}		
			
			tokenView = Translator.intSvc.createAccessToken(ldap,false);
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
