/*
 * InboundcXMLPunchOutLogon.java
 *
 * Created on July 14, 2004, 5:07 PM
 */

package com.cleanwise.service.apps.dataexchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.cleanwise.service.api.util.InvalidLoginException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.LdapItemData;
import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideLoadRequestData;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.UserAcessTokenViewData;
import com.cleanwise.view.utils.Constants;

/**
 *
 * @author  bstevens
 */
public class InboundcXMLPunchOutLogon extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());
    private static final int OPERATION_EDIT = 1;
    private static final int OPERATION_CREATE = 2;
    private UserAcessTokenViewData tokenView;
    protected boolean changeLocation;
    protected String uniqueName = null;
    protected String siteRefNumType = RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER; // set default to SITE_REFERENCE_NUMBER
    
    public void translate(String s) throws Exception {
        try{
            translate(DocumentHelper.parseText(s));
        }catch(DocumentException e){
        	e.printStackTrace();
        	throw new IOException(e.getMessage());
        }
    }
    
    /**
     *Parses out the items from the cXML file sent when an edit request is made
     */
    private OrderGuideLoadRequestData parseItems(Node requestNode){
        List items = requestNode.selectNodes("ItemOut");
        if(items == null){
            throw new NullPointerException("No 'ItemOut' Nodes found");
        }
        Iterator it = items.iterator();
        if(!it.hasNext()){
            throw new NullPointerException("No 'ItemOut' Nodes found");
        }
        OrderGuideLoadRequestData cart = new OrderGuideLoadRequestData();
        int ct = 0;
        while(it.hasNext()){
            Node aItemNode = (Node) it.next();
            String qtyStr = aItemNode.valueOf("@quantity");
            Node partNode = aItemNode.selectSingleNode("ItemID/SupplierPartID");
            if(partNode == null){
                throw new NullPointerException("No 'ItemID/SupplierPartID' Node found for in item loop: "+ct);
            }
            String sku = partNode.getText();
            if(!Utility.isSet(sku)){
                throw new NullPointerException("No 'ItemID/SupplierPartID' found for in item loop: "+ct);
            }
            if(!Utility.isSet(qtyStr)){
                throw new NullPointerException("No quantity found for item in loop: "+ct+" ("+sku+")");
            }
            int qty;
            try{
                qty = Integer.parseInt(qtyStr);
            }catch(NumberFormatException e){
                throw new NumberFormatException("quantity for item in loop: "+ct+" ("+sku+") was not numeric: "+qtyStr);
            }
            cart.addItem(qty,sku);
            ct++;
        }
        return cart;
    }
    
    protected int getValidSite(String siteRefNum) throws Exception{
    	try{
    		TradingPartnerData partner = getTranslator().getPartner();
    		int tradingPartnerId = getTranslator().getPartner().getTradingPartnerId();
    		String tradingPartnerTypeCd = partner.getTradingPartnerTypeCd();
    		int[] parentIds = null;
    		boolean parentIsAccount = tradingPartnerTypeCd.equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER);
            // checking account
            if(tradingPartnerTypeCd.equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.CUSTOMER)) {
                parentIds = getTranslator().getTradingPartnerBusEntityIds(tradingPartnerId,
                                                        RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
                if(parentIds == null || parentIds.length < 1) {
                  throw new IllegalArgumentException("No accounts present for current trading partner id = " +
                    tradingPartnerId);
                }    
            } else if(tradingPartnerTypeCd.equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.STORE)) {
                parentIds = getTranslator().getTradingPartnerBusEntityIds(tradingPartnerId,
                                                                RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
                if(parentIds == null || parentIds.length < 1) {
                  throw new IllegalArgumentException("No stores present for current trading partner id = " +
                    tradingPartnerId);
                }
                
            } else {
              throw new IllegalArgumentException("Trading partner should has type of ACCOUNT or STORE");
            }       
            List parentIdList = new ArrayList();
            for (int parentId : parentIds){
            	parentIdList.add(parentId);
            }
            
            return Translator.intSvc.getSiteIdByPropertyName(
            		siteRefNumType, siteRefNum, parentIdList, parentIsAccount);
    	}catch(Exception exc){
    		exc.printStackTrace();
    		throw new Exception(exc.getMessage());
    	}
    }
    
    protected UserAcessTokenViewData getAccess(Node nodeToOperateOn) throws Exception{
    	//LdapItemData lUserInfo = xmlHandler.parseLdapItem(nodeToOperateOn); 
    	LdapItemData lUserInfo = parseLdapItem(nodeToOperateOn); 
		if(lUserInfo == null){
			appendErrorMsgs(super.getErrorMsgs().toString(),true);
		}
    	try{
    		
    		return Translator.getIntSvc().createAccessToken(lUserInfo,false);
    	}catch(InvalidLoginException exc) {
        	appendErrorMsgs("Wrong user name or password "+lUserInfo.getUserName(), true);
        	throw new Exception(exc.getMessage());
    	}
    }
    
    protected LdapItemData parseLdapItem(Node nodeToOperateOn) throws Exception{
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
	        	throw new Exception("Authorization failed.  Check username and trading profile authorization setup.");
	        }
	        
	        Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/PunchOutSetupRequest");
	        if(requestNode == null){
	        	appendErrorMsgs("Could not parse request node", true);
	            return null;
	        }
	        
	        String userName = null;
	        List nodeList = requestNode.selectNodes("Extrinsic");
	        Iterator it = nodeList.iterator();
	        while(it.hasNext()){
	        	Node n = (Node)it.next();
	        	String nodeName = n.valueOf("@name");
	        	if(nodeName!=null && nodeName.equalsIgnoreCase("UniqueName")){
	        		userName = n.getText();
	        		break;
	        	}
	        }
	        if(userName == null){
	        	appendErrorMsgs("Could not parse UniqueName node", true);
	            return null;
	        }
	        ldap.setUserName(userName);
	       
    	}catch(Exception exc){
    		exc.printStackTrace();
    		throw new Exception(exc.getMessage());
    	}finally{
    		if(pCon!=null){
    			pCon.close();
    		}
    	}
    	return ldap;
    }
    
    /**
     *Preforms the translation of this cXML file.  Will return a document that contains a url which the user can use to log in
     *with.  This url will not requier a user name a password, utilizing the access token setup, which this class will create.
     */
    public void translate(Node nodeToOperateOn) throws Exception {
        
        //LdapItemData lUserInfo = xmlHandler.parseLdapItem(nodeToOperateOn);       

       
        try {
            //log.info("Requesting user "+lUserInfo.getUserName()+ " and pass: *********");
            
            //tokenView = Translator.intSvc.createAccessToken(lUserInfo);
           
            //get user and access token           
            tokenView = getAccess(nodeToOperateOn);
            
        }catch (InvalidLoginException exc) {
        	appendErrorMsgs("Wrong user name or password", true);
            return;
        }catch (Exception exc) {
            exc.printStackTrace();
            log.error(exc.getMessage());
            appendErrorMsgs("Unknown user logon error", true);
            return;
        }
        
        
        Node requestNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/PunchOutSetupRequest");
        if(requestNode == null){
        	appendErrorMsgs("Could not parse request node", true);
            return;
        }
        //there are 2 valid operations:
        //  create - Nothing needs to happen here, log user in and you are done.
        //  edit - need to find an appropriate order to edit and direct the user to this 
        //         order.
        String operation = requestNode.valueOf("@operation");
        int oper;
        if(operation.equalsIgnoreCase("edit")){
            oper = OPERATION_EDIT;
        }else if(operation.equalsIgnoreCase("create")){
            oper = OPERATION_CREATE;
        }else{
        	appendErrorMsgs("operation "+operation+" is not valid, expected either edit or create", true);
            return;
        }


        String siteRefNum = getSiteRefNum(nodeToOperateOn);
        if(siteRefNum!=null && siteRefNum.trim().length()>0){
        	try{
        		/*int siteId = Translator.intSvc.getBusEntityIdByProperty(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER, 
        				siteRefNum);*/
        		
        		int siteId = getValidSite(siteRefNum);
        		if(siteId > 0){
        			siteRefNum = Integer.toString(siteId);
        		}else{
        			throw new Exception("Cannot find site with dist ref num:"+siteRefNum);
        		}
        		
        	}catch(Exception e){
                e.printStackTrace();
                appendErrorMsgs("Unknown site:"+siteRefNum, true);
                return;
            }
        }
 
        Node cookieNode = requestNode.selectSingleNode("BuyerCookie");
        if(cookieNode == null){
        	appendErrorMsgs("Could not parse BuyerCookie node", true);
            return;
        }
        String buyerCookie = cookieNode.getText();
        
        Node commURLNode = requestNode.selectSingleNode("BrowserFormPost/URL");
        if(requestNode == null){
        	appendErrorMsgs("Could not parse BrowserFormPost/URL node", true);
            return;
        }
        URL commURL;
        try{
            commURL = new URL(commURLNode.getText());
        }catch(MalformedURLException e){
        	appendErrorMsgs(commURLNode.getText()+" not a valid URL", true);
            return;
        }
        
        if (oper == OPERATION_EDIT){
            SiteData site;
            if(tokenView.getUserSiteIds() != null && !tokenView.getUserSiteIds().isEmpty()){
                if(tokenView.getUserSiteIds().size() == 1){
                    Integer siteId = (Integer) tokenView.getUserSiteIds().get(0);
                    try{
                        site = Translator.intSvc.getSite(siteId.intValue());
                    }catch(Exception e){
                        e.printStackTrace();
                        appendErrorMsgs("Unknown user logon error 1", true);
                        return;
                    }
                }else{
                	appendErrorMsgs("User has more than one site ("+tokenView.getUserSiteIds().size()+"), edit order function is not supported", true);
                	return;
                }
            }else{
            	appendErrorMsgs("User has no sites, cannot edit order", true);
                return;
            }
            try{
                OrderGuideLoadRequestData scd = parseItems(requestNode);
                String skuType = getTranslator().getPartner().getSkuTypeCd();
                scd.setSkuTypeCd(skuType);
                OrderGuideData og = OrderGuideData.createValue();
                if (uniqueName != null)
                	og.setAddBy(uniqueName);
                else
                	og.setAddBy("InboundcXMLPunchOutLogin");
                og.setBusEntityId(site.getSiteId());
                og.setCatalogId(0);
                og.setOrderGuideTypeCd(RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
                og.setShortDesc(site.getBusEntity().getShortDesc());
                og.setUserId(tokenView.getUserData().getUserId());
                scd.setOrderGuide(og);
                scd.setSite(site);
                xmlHandler.appendIntegrationRequest(scd);
            }catch(RuntimeException e){
                e.printStackTrace();
                appendErrorMsgs(e.getMessage(), true);
                return;
            }
        }
        
        
        //construct the logon url based off the request
        StringBuffer logonUrl = new StringBuffer();
        
        
        URI source = getTranslator().getSourceURI();
        //if (source == null)
        //	source = new URI("null");

        logonUrl.append(source.getScheme());
        logonUrl.append(":");
        if(!source.isOpaque()){
            logonUrl.append("//");
        }
        logonUrl.append(source.getHost());

        if(source.getPort() > 0){
            if("http".equalsIgnoreCase(source.getScheme())){
                if(source.getPort() != 80){
                    logonUrl.append(":");
                    logonUrl.append(source.getPort());
                }
            }else if("https".equalsIgnoreCase(source.getScheme())){
                if(source.getPort() != 443){
                    logonUrl.append(":");
                    logonUrl.append(source.getPort());
                }
            }
        }
        //logonUrl.append("localhost:8080");
        //if this was an http request get the context
        if("http".equalsIgnoreCase(source.getScheme()) || "https".equalsIgnoreCase(source.getScheme())){
            //append context
            String path = source.getPath();
            
            path = com.cleanwise.service.api.util.Utility.trimLeft(path,":");
            path = com.cleanwise.service.api.util.Utility.trimLeft(path,"/");
            
            
            int end = path.indexOf('/');
            logonUrl.append("/");
            if(end > 0){
                logonUrl.append(path.substring(0,end));
            }else{
                logonUrl.append(path);
            }
        }
        //logonUrl.append("/userportal/autoLogon.do?");
        logonUrl.append("/userportal/logon.do?");
        logonUrl.append(Constants.ACCESS_TOKEN);
        logonUrl.append("=");
        logonUrl.append(tokenView.getAccessToken());
        logonUrl.append("&");
        logonUrl.append(Constants.CUSTOMER_SYSTEM_ID);
        logonUrl.append("=");
        logonUrl.append(buyerCookie);
        logonUrl.append("&");
        logonUrl.append(Constants.CUSTOMER_SYSTEM_URL);
        logonUrl.append("=");
        logonUrl.append(URLEncoder.encode(commURL.toString(),"UTF-8"));
        logonUrl.append("&");
        logonUrl.append(Constants.CW_LOGOUT_ENABLED);
        logonUrl.append("=false");
        
        getChangeLocationEnabled();
        if(!changeLocation){
            logonUrl.append("&");
            logonUrl.append(Constants.CHANGE_LOCATION);
            logonUrl.append("=false");
        }
        
        if(siteRefNum!=null && siteRefNum.trim().length()>0){
        	logonUrl.append("&");
            logonUrl.append(Constants.DEFAULT_SITE);
            logonUrl.append("="+siteRefNum);
        }
        
        if(uniqueName!=null && uniqueName.trim().length()>0){
        	logonUrl.append("&");
            logonUrl.append(Constants.UNIQUE_NAME);
            logonUrl.append("="+uniqueName);
        }
        
        //create the response code
        Element ele = InboundXMLHandler.createEmptyCXMLDocument();
        
        Element responseE = ele.addElement("Response");
        Element statusE =responseE.addElement("Status");
        statusE.addAttribute("code", InboundXMLHandler.POSITIVE_REASPONSE_CODE);
        statusE.addAttribute("text", InboundXMLHandler.POSITIVE_REASPONSE_TEXT);
        statusE.addText("");
	        
        responseE.addElement("PunchOutSetupResponse")
        .addElement("StartPage")
        .addElement("URL").addText(logonUrl.toString());
	        
        Document cxmlResp = ele.getDocument();
        //log our response
        OutputStream out = getTranslator().getOutputResponseStream(null);
        InboundXMLHandler.writeCXMLToResponse(out,cxmlResp);
        log.info("Responding with: "+cxmlResp.asXML());
        
    }

	protected String getSiteRefNum(Node nodeToOperateOn) {
		//use addressID as default site if passed in the file
        String siteRefNum =null;
        try{
        	Node shipToNode = nodeToOperateOn.getDocument().selectSingleNode("//cXML/Request/PunchOutSetupRequest/ShipTo/Address");
        	if(shipToNode != null){
        		siteRefNum = shipToNode.valueOf("@addressID");
        	}
			
        }catch(Exception exc){
        	exc.printStackTrace();
        	//throw new Exception(exc.getMessage());
        	log.info("AddressID not provided. Using default shipTo.");
        }
		return siteRefNum;
	}
    
    protected void getChangeLocationEnabled(){
    	changeLocation = false;
    }
    
    private void processFailedResponse(Vector errors) throws IOException{
    	
    	Document negResp = xmlHandler.createNegativeResponse(errors.toString());
    	InboundXMLHandler.writeCXMLToResponse(getTranslator().getOutputResponseStream(null),negResp);
    	log.info("Responding with: "+negResp.asXML());
        return;
    }
    
    // override this in extended class if support generic user id
    protected String getGenericUserId(){
    	return null;
    }

}
