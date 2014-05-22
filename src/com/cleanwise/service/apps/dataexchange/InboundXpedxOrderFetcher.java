package com.cleanwise.service.apps.dataexchange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.InboundEventData;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.apps.SOAPTransferClient;

/**
 * Generates requests to get orders from xpedx.  We are doing
 * these one at a time so there is not one big file of orders to
 * get, but we have to call a function, get the list and then
 * request each of them individually.
 */
public class InboundXpedxOrderFetcher extends InboundXMLSuper {
	protected Logger log = Logger.getLogger(this.getClass());
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd-mmhhssSS");
	        
    private HashMap getParams(){
    	HashMap theMap = new HashMap();
    	Iterator it = getTranslator().getTradingPropertyMapDataVector().iterator();
    	while(it.hasNext()){
    		TradingPropertyMapData map = (TradingPropertyMapData) it.next();
    		if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode())){
    			String value = map.getHardValue();
    			if(value == null){
    				value = "";
    			}
    			theMap.put(map.getPropertyTypeCd(),value);
    		}
    	}
    	return theMap;
    }
    
    /**
     *Preforms the translation of this cXML file.  Will return a document that contains a url which the user can use to log in
     *with.  This url will not requier a user name a password, utilizing the access token setup, which this class will create.
     */
    public void translate(org.dom4j.Node nodeToOperateOn) throws Exception {
    	log.info("********************************** in order fetcher::"+nodeToOperateOn.getNodeType());
    	HashMap paramsMaster = getParams();
    	String doc = nodeToOperateOn.asXML();
    	String key = "<SOPNUMBE>";
    	ArrayList docs = new ArrayList();
    	int start = 0;
    	while(true){
	    	start = doc.indexOf(key,start+1);
	    	log.info("start="+start);
	    	if(start < 0){
	    		break;
	    	}
	    	int end = doc.indexOf("</SOPNUMBE>", start);
	    	String docNum = doc.substring(start + key.length(), end);
	    	docs.add(docNum);
    	}
    	
    	Iterator it = docs.iterator();
    	HashMap data = new HashMap();
    	TradingProfileData profile = getTranslator().getProfile();
    	while(it.hasNext()){    		
			String docum = (String) it.next();
			log.info("Trying to get doc: "+docum);
			log.info(profile.getInterchangeSender());
			log.info(profile.getAuthorization());
			log.info(profile.getSecurityInfo());
			log.info("");
			
			HashMap params = (HashMap) paramsMaster.clone();
			params.put("sOrder",docum);
            params.put("company","");
			String response = SOAPTransferClient.invoke(profile.getInterchangeSender(), 
					"https://webservices.xpedx.com/", "getOrder", profile.getAuthorization(), profile.getSecurityInfo(),params);
			log.info("success");
			String localFileName = docum + "getAnOrder" + formater.format(new Date())+".xml"; //getanorder text should eb able to be customized
			data.put(localFileName,response);
    	}
    	
    	it = data.keySet().iterator();    	
    	while(it.hasNext()){
    		String fileName = (String) it.next();
    		String fileContents = (String) data.get(fileName);
	        InboundEventData eventData = xmlHandler.generateInboundEvent(fileName, fileContents.getBytes());
	        eventData.setStatus(Event.STATUS_PROCESSED);//TODO: set PROCESSED for now. should be READY
    	}
    	xmlHandler.createInterchangeObject();
    	xmlHandler.createTransactionObject();  
    }
}
