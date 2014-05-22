/**
 * 
 */
package com.cleanwise.service.apps.dataexchange;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

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
public class InboundSodexoCXmlPunchOutLogon extends InboundcXMLPunchOutLogon {
	private String siteRefNum = null;
	public InboundSodexoCXmlPunchOutLogon(){
		siteRefNumType = RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER;
	}
	/**
	 * This is used only by Unicco. Get the user from PunchOutSetupRequest/ShipTo
	 * There is a one-to-one correspondence between a user and a shipTo location
	 * If user/shipTo does not exist in our system, log in as default user/shipTo
	 * 
	 * @param args
	 * @throws InvalidLoginException 
	 * @throws RemoteException 
	 */
	protected UserAcessTokenViewData getAccess(Node nodeToOperateOn) throws Exception{
		UserAcessTokenViewData tokenView;
		LdapItemData ldap = parseLdapItem(nodeToOperateOn); 

		// get siteRefNum
		String customerAccountNumber = null;
		Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/PunchOutSetupRequest");
		List nodeList = requestNode.selectNodes("Extrinsic");
        Iterator it = nodeList.iterator();
        while(it.hasNext()){
        	Node n = (Node)it.next();
        	String nodeName = n.valueOf("@name");
        	if(nodeName!=null && nodeName.equalsIgnoreCase("CustomerAccountNumber")){
        		customerAccountNumber = n.getText();
        		break;
        	}
        }
        if(customerAccountNumber == null || !(customerAccountNumber.trim().length()>0)){
			throw new Exception("Could not parse cXML (CustomerAccountNumber)");
		}
			
        siteRefNum = customerAccountNumber;
		String usrname = "SM"+siteRefNum;
	
        Connection pCon = null;
		try{
			//check if user exists in our system else login as default user
			DBCriteria cr = new DBCriteria();
			pCon= this.getConnection();
			
			cr.addEqualTo(UserDataAccess.USER_NAME, usrname);
			cr.addEqualTo(UserDataAccess.USER_STATUS_CD, RefCodeNames.USER_STATUS_CD.ACTIVE);
			
			UserDataVector udv = UserDataAccess.select(pCon, cr);
			
			if(udv!=null && udv.size()>0){
				ldap.setUserName(usrname);
			}else{
				throw new Exception("User Name not valid: " + usrname);
			}		
			
			tokenView = Translator.intSvc.createAccessToken(ldap,false);
			return tokenView;
		}catch(Exception exc){
			exc.printStackTrace();
			throw exc;
		}finally{
			if(pCon!=null){
				pCon.close();
			}
		}
	}
	protected String getSiteRefNum(Node nodeToOperateOn) {
		return siteRefNum;
	}
		
	protected void getChangeLocationEnabled(){
		changeLocation = true;
    }
	
}
