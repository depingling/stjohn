package com.cleanwise.service.apps.dataexchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Category;


import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.InterchangeData;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.OutboundEventData;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.apps.edi.Outbound850;
import com.cleanwise.service.apps.edi.Outbound850_Network;

/**
 * Network services requires that some transactions go out as EDI and others go out as 
 * their flat file format.  There is only one distributor involved due to the  integration
 * on the inbound side.  Following the standard model of setting up another distributor
 * was deemed to have business consequences around reporting and "conceptual strangeness"
 * so this special builder/handler was constructed to generate 2 files, one for the EDI
 * accounts and one for the CSV accounts.
 * 
 * @author Brook Stevens
 *
 */
public class OutboundNetwork850Handler2 extends InterchangeOutboundSuper implements OutboundTransaction{
	private static final Category log = Category.getInstance(OutboundNetwork850Handler2.class);
	private static final String TYPE_EDI = "EDI"; //EDI type of transaction key (used for account property as well)
	private static final String TYPE_CSV = "CSV"; //CSV type of transaction key
	private Map sendParamMap;
	
	//the unchanged (original) send parameter map that the handler had when this class was first instantiated
	//This class will change it  based off the actual builder that is called so files can be sent to different
	//places.
	private Map unchangedSendParamMap; 
	
	//the list of builders that are being controlled at any one time
	ArrayList<OutboundTransaction> transactions = new ArrayList<OutboundTransaction>();
	//the OutboundTranslate object that instantiated this class
	OutboundTranslate mHandler;
	
	
	
	//maps the request to the types
	HashMap<String,OutboundEDIRequestDataVector> requestMap = new HashMap<String,OutboundEDIRequestDataVector>();
	
	OutboundTransaction currentBuilder; //current builder that is being worked on
	String currentBuilderType; //current builder type that is being worked on (for example EDI)

	/**
	 * Does most of the actual work.  Will instantiate the builder objects, and 
	 * simulate the work of the OutboundTranslatore on them.  
	 * If you need to add additional export types this and the setTransactionsToProcess method
	 * are the two methods that need to be modified, everything
	 * else should just use the interface OutboundTransaction, or loop through all of the
	 * builders that exist and return an amalgomation of their results (i.e. all the
	 * IntegrationServiceRequests from every builder.) 
	 */
	public void buildInterchangeContent() throws Exception {
		
		
		
	}

	/**Empty method.  Everything is done in the @see buildInterchangeContent method.*/
	public void buildInterchangeHeader() throws Exception {
		//nothing to do
	}

	/**Empty method.  Everything is done in the @see buildInterchangeContent method.*/
	public void buildInterchangeTrailer() throws Exception {
		//nothing to do
	}

	/**
	 * Will return the value from the builder that is being worked on when this method
	 * is called.  This value will change as the class changes the builder to do different
	 * things.
	 */
	public String getFileExtension() throws Exception {
		if(currentBuilder instanceof Outbound850_Network){
			return ".edi";
		}else{
			return currentBuilder.getFileExtension();
		}
	}

	/**
	 * Will return the value from the builder that is being worked on when this method
	 * is called.  This value will change as the class changes the builder to do different
	 * things.
	 */
	public String getFileName() throws Exception {
		return currentBuilder.getFileName();
	}
	
	private boolean isEDIAccount(OutboundEDIRequestData currOutboundReq){
		if(currOutboundReq.getAccountProperties() != null){
	        Iterator<PropertyData> it =  currOutboundReq.getAccountProperties().iterator();
	        while(it.hasNext()){
	        	PropertyData apd = (PropertyData) it.next();
	        	if("EDI".equals(apd.getShortDesc()) && Utility.isSet(apd.getValue())){
	        		log.info("FOUND EDI PROPERTY: "+apd.getValue());
	        		return Utility.isTrue(apd.getValue());
	        	}
	        
	        }
		}
		return false;
	}

	/**
	 * Sets the list of all the transations that we are going to process this will be broken up into sub lists
	 * and sent to the individual OutboundTransaction objects.  A side effect of this method is that the 
	 */
	public void setTransactionsToProcess(OutboundEDIRequestDataVector transactionstoproc) {
		Iterator<OutboundEDIRequestData> it = (Iterator<OutboundEDIRequestData>) transactionstoproc.iterator();
		sendParamMap = mHandler.getSendParameterMap();
		while(it.hasNext()){
			OutboundEDIRequestData aReq = it.next();
			
			//String isEdi = Utility.getPropertyValue(aReq.getAccountProperties(),"EDI");
			//this should go out as EDI
			//if(Utility.isTrue(isEdi)){
			
			/*
			 * Removing EDI file creation option as per Bug 4852
			if(isEDIAccount(aReq)){
				OutboundEDIRequestDataVector ediReqs = requestMap.get(TYPE_EDI);
				if(ediReqs == null){
					ediReqs = new OutboundEDIRequestDataVector();
					requestMap.put(TYPE_EDI, ediReqs);
				}
				log.info("Adding EDI request: "+aReq.getOrderD().getAccountId());
				ediReqs.add(aReq);
			}else{
			* End of Removing EDI file creation option as per Bug 4852
			*/
			OutboundEDIRequestDataVector csvReqs = requestMap.get(TYPE_CSV);
			if(csvReqs == null){
				csvReqs = new OutboundEDIRequestDataVector();
				requestMap.put(TYPE_CSV, csvReqs);
			}
			log.info("Adding CSV request: "+aReq.getOrderD().getAccountId());
			csvReqs.add(aReq);
			//}
		}
		
		unchangedSendParamMap = mHandler.getSendParameterMap();
		
		Iterator<String> keysIt = requestMap.keySet().iterator();
		while(keysIt.hasNext()){
			String key = keysIt.next();
			if(TYPE_CSV.equals(key)){
				currentBuilder = new OutboundNetwork850CsvTabPurchaseOrder2();
			}else if(TYPE_EDI.equals(key)){
				currentBuilder = new Outbound850_Network();
			}else{
				throw new RuntimeException("Unknown outbound type called: "+key);
			}
			currentBuilderType = key;
			mHandler.setSendParameterMap(setupSendParamMap());
			
			transactions.add(currentBuilder);
			
			
			currentBuilder.setTranslator(mHandler);
			currentBuilder.setTransactionsToProcess(requestMap.get(key));

			try{
				mHandler.initializeOutputStream(this);
				currentBuilder.buildInterchangeHeader();
				currentBuilder.buildInterchangeContent();
				currentBuilder.buildInterchangeTrailer();
			}catch(Exception e){
				throw new RuntimeException(e.getMessage(),e);
			}
		}
	}

	public InterchangeData createInterchangeObject() {
		throw new RuntimeException("createInterchangeObject called");
	}

	public ElectronicTransactionData createTransactionObject() {
		throw new RuntimeException("createTransactionObject called");
	}

	/**
	 * For all of the outbound translators this object is controlling returns their associated requests to process
	 */
	public IntegrationRequestsVector getRequestsToProcess() {
		IntegrationRequestsVector requests = new IntegrationRequestsVector();
		Iterator<OutboundTransaction> it = transactions.iterator();
		while(it.hasNext()){
			requests.addAll(it.next().getRequestsToProcess());
		}
		return requests;
	}

	/**
	 * Gets a report from the builders (OutboundTranslate objects) of what they have done.
	 */
	public String getTranslationReport() {
		StringBuffer report = new StringBuffer();
		Iterator<OutboundTransaction> it = transactions.iterator();
		while(it.hasNext()){
			OutboundTransaction trans = it.next();
			report.append("Report from: ");
			report.append(trans.getClass().getName());
			report.append('\n');
			report.append(trans.getTranslationReport());
			report.append('\n');
			report.append('\n');
			report.append('\n');
		}
		return report.toString();
	}

	/**
	 * If ANY of the builders that this handler controls fail then this method will return
	 * true.  Only if they all processed successfuly will the indicator be false.
	 */
	public boolean isFail() {
		Iterator<OutboundTransaction> it = transactions.iterator();
		while(it.hasNext()){
			if(it.next().isFail()){
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the translator, or handler object (caller class).
	 */
	public void setTranslator(Translator translator) {
		mHandler = (OutboundTranslate) translator;
	}
	
	/**
	 * Overridden method to change the settings of the send parameter map before it is
	 * actually used so we can send it to different places depending on the type of the
	 * transaction.  In other words the EDI files go to a different place than the csv files.
	 * 
	 * To do this it takes the original sendParamterMap and searches for keys of the form:
	 * EDIsomekey or CSVsomekey 
	 * So the configuration needs to have all possible values of the output present and only 
	 * the ones that apply should be used.
	 *
	 */
	private Map setupSendParamMap(){
		log.info("In overidden generateOutboundEvent method");
		Iterator it = sendParamMap.keySet().iterator();
		Map myCorrectedSendParamMap = new HashMap();
		while(it.hasNext()){
			String key = (String) it.next();
			if(key!= null && key.startsWith(currentBuilderType)){
				Object value = sendParamMap.get(key);
				key = key.substring(currentBuilderType.length());
				myCorrectedSendParamMap.put(key, value);
			}
		}
		return myCorrectedSendParamMap;
	}
	
	
	/**
	 * Does nothing, the outbound events are generated by the builders themselves and added to the 
	 * integration requests themselves.
	 */
	protected void updateOutboundEvent(){
		return;
	}

}
