/*
 * InboundEDIStubEmailer.java
 *
 * Created on June 17, 2004, 2:00 PM
 */

package com.cleanwise.service.apps.edi;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OutboundEventData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;
/**
 * Inbound edi "parser"  This class will take in a file and email the contents to a
 * given email address.  This would be for a file type that is not implemented yet and 
 * we would like to be notified of it existence, but not actually parse the file.  Oboe
 * should take care of issuesing a positiive 997 to indicate we parsed the file, but beyond
 * that no further automated action needs to be taken.
 * @author  BStevens
 */
public class InboundEDIStubFtpFile extends InboundEdiSuper{
	private static final Logger log = Logger.getLogger(InboundEDIStubFtpFile.class);
    private static List sentFiles = new LinkedList();
    
    /** Sends the email 
     * @throws Exception */
    public void extract() throws Exception {        
        String fileName = this.ediHandler.getTranslator().getInputFilename();
        if(!sentFiles.contains(fileName)){
            sentFiles.add(fileName);
            InboundTranslate translator = (InboundTranslate) getTranslator();
            OutboundEventData eventData = new OutboundEventData();
    		eventData.setFileName(translator.getInputFilename());
    		eventData.setPartnerKey(translator.getProfile().getGroupReceiver()+"");
    		eventData.setSetType(translator.getSetType());
    		eventData.setByteArray(translator.getDataContents());
    		
    		String tohost = null;
			String port = null;
			String username = null;
			String password = null;
			String ftpmode = null;
			String todir = null;
			String transfer_type = null;
			Map sendParameterMap = new HashMap();
			eventData.setSendParameterMap(sendParameterMap);
    		
    		TradingPropertyMapDataVector mapping = translator.getTradingPropertyMapDataVector();
            Iterator it = mapping.iterator();
            while(it.hasNext()){
                TradingPropertyMapData map = (TradingPropertyMapData) it.next();
                if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode()) &&
                		RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty()) &&
                		"tohost".equals(map.getPropertyTypeCd())){
                	tohost = map.getHardValue();
                } else if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode()) &&
                		RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty()) &&
                		"port".equals(map.getPropertyTypeCd())){
                	port = map.getHardValue();
                } else if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode()) &&
                		RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty()) &&
                		"username".equals(map.getPropertyTypeCd())){
                	username = map.getHardValue();
                } else if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode()) &&
                		RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty()) &&
                		"password".equals(map.getPropertyTypeCd())){
                	password = map.getHardValue();
                } else if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode()) &&
                		RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty()) &&
                		"ftpmode".equals(map.getPropertyTypeCd())){
                	ftpmode = map.getHardValue();
                } else if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode()) &&
                		RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty()) &&
                		"todir".equals(map.getPropertyTypeCd())){
                	todir = map.getHardValue();
                } else if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode()) &&
                		RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty()) &&
                		"transfer_type".equals(map.getPropertyTypeCd())){
                	transfer_type = map.getHardValue();
                } 
            }
			sendParameterMap.put("tohost", tohost);
		    sendParameterMap.put("username", username);
		    sendParameterMap.put("password", password);
		    sendParameterMap.put("exceptionOnFileExists", "false");
		    if (port != null)
		    	sendParameterMap.put("port", port);
		    if (ftpmode != null)
		    	sendParameterMap.put("ftpmode", ftpmode);
		    if (todir != null)
		    	sendParameterMap.put("todir", todir);
		    if (transfer_type != null)
		    	sendParameterMap.put("transfer_type", transfer_type);
		    
		    ediHandler.appendIntegrationRequest(eventData);    		
        }
    }
    
}
