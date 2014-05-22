package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.PropertyDataAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.apps.loaders.FixedWidthFieldDef;
import com.cleanwise.service.apps.loaders.FixedWidthParser;
import com.cleanwise.service.apps.loaders.TabFileParser;
import com.cleanwise.service.apps.loaders.FixedWidthParser.Filter;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;


public class Inbound855_Kroger_Peyton  extends FixedWidthParser implements StreamedInboundTransaction{
	IntegrationRequestsVector mRequestsToProcess = new IntegrationRequestsVector();
	private HashMap<String, Integer>cachedAccountLookup = new HashMap<String, Integer>();
	private boolean mFailed = false; 
	
	private ArrayList<AcknowledgeRequestDataFlat> mAcknowledgeRequestDataFlatList = new ArrayList<AcknowledgeRequestDataFlat>();
	private HashMap<String, AcknowledgeRequestData> mAcknowledgeRequestDataMap = new HashMap<String, AcknowledgeRequestData>();
	
    protected Logger log = Logger.getLogger(Inbound855_Kroger_Peyton.class);

    private Translator mTranslator;
    
    @Override
    protected void processUnfilteredLine(String pLine){
    	throw new RuntimeException("Not implemented");
    }

 // nothing to translate
	public void translateInterchangeHeaderByHandler() throws Exception	{}
	
	public void translateInterchangeContent() throws Exception {

	}
	
	
    
  //group everything together and create distinct AcknowledgeRequestData requests
    protected void postProcessFile(){
    	Connection con=null;
    	
    	try{
	        log.info("Parsing finished. Lines processed: "+getCurrentLineNumber());
	        int storeId = mTranslator.getStoreId();
	        con = getConnection();
	        int counter = 0;
	        Iterator<AcknowledgeRequestDataFlat> it = mAcknowledgeRequestDataFlatList.iterator();
	        while(it.hasNext()){
	        	AcknowledgeRequestDataFlat flat = it.next();
	        	counter ++;
	        	String key = flat.getErpPoNum() + "::"+flat.getVendorOrderNum(); //the key to group the file on..basically anything in the header
	        	AcknowledgeRequestData ackReq;
	        	if(!mAcknowledgeRequestDataMap.containsKey(key)){
	        		String accountKey = flat.getSiteKey();
	        		Integer accountId = null;
	        		
	        		if(accountKey.length() != 7){
	        			log.error("The site account id ("+accountKey+") was too short, expected 7 chars.  Line: "+counter);
	        			setFail(true);
	        			continue;
	        		}
	        		accountKey = accountKey.trim().substring(0,3);
	        		String siteKey = flat.getSiteKey().substring(4);
	        		siteKey = Utility.trimLeft(siteKey, "0"); //they pad the site id with 0's
	        		if(this.cachedAccountLookup.containsKey(accountKey)){
	        			accountId = cachedAccountLookup.get(accountKey);
	        			if(accountId == null){
	        				log.error("Could not find account for Divsion: "+accountKey);
	        				setFail(true);
	        				continue;
	        			}
	        		}else{
		        		DBCriteria crit = new DBCriteria();
		        		crit.addEqualTo(PropertyDataAccess.SHORT_DESC, "Division");
		        		crit.addEqualToIgnoreCase(PropertyDataAccess.CLW_VALUE, accountKey);
		        		crit.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FIELD_CD);
		        		
		        		DBCriteria subCrit = new DBCriteria();
		        		subCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID, storeId);
		        		subCrit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
		        		crit.addOneOf(PropertyDataAccess.BUS_ENTITY_ID, BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY1_ID, subCrit));
		        		IdVector accountIds = PropertyDataAccess.selectIdOnly(con,PropertyDataAccess.BUS_ENTITY_ID, crit);
		        		if(accountIds.size() > 1){
		        			log.error(PropertyDataAccess.getSqlSelectIdOnly(PropertyDataAccess.BUS_ENTITY_ID,crit));
		        			log.error("Found multiple accounts for Divsion: "+accountKey+" ("+Utility.toCommaSting(accountIds)+")");
	        				setFail(true);
	        				continue;
		        		}else if(accountIds.size() == 0){
		        			cachedAccountLookup.put(accountKey, null);
		        			log.error("Could not find account for Divsion: "+accountKey);
	        				setFail(true);
	        				continue;
		        		}else{
		        			accountId = (Integer) accountIds.get(0);
		        		}
	        		}
	        		
	        		
	        		ackReq = AcknowledgeRequestData.createValue();
	        		ackReq.setAccountId(accountId.intValue());
	            	ackReq.setAckDate(flat.getAckDate());
	            	ackReq.setErpPoNum(flat.getErpPoNum());
	            	//ackReq.setMatchOrderByVendorOrderNum(?);
	            	ackReq.setRequestCreateOrderIfNotExists(true);
	            	ackReq.setSiteKey(siteKey); //from above split...compound key account and site
	            	ackReq.setVendorOrderNum(flat.getVendorOrderNum());
	            	ackReq.setAckItemDV(new AckItemDataVector());
	            	mAcknowledgeRequestDataMap.put(key, ackReq);
	        	}else{
	        		ackReq = mAcknowledgeRequestDataMap.get(key);
	        	}
	        	AckItemData ackItemReq = AckItemData.createValue();
	        	ackItemReq.setAction("IA");
	        	ackItemReq.setActionDate(flat.getAckDate());//note use of header date not getActionDate()
	        	ackItemReq.setDistSkuNum(flat.getDistSkuNum());
	        	ackItemReq.setItemName(flat.getItemName());
	        	// the price received is actually line total, so need to divided by quantity
	        	BigDecimal price = flat.getPrice().divide(new BigDecimal(flat.getQuantity()), 2, BigDecimal.ROUND_HALF_UP);
	        	ackItemReq.setPrice(price);
	        	ackItemReq.setQuantity(flat.getQuantity());
	        	
	        	ackReq.getAckItemDV().add(ackItemReq);
	        }
	        
	        //Iterator ackReqIt = mAcknowledgeRequestDataMap.values().iterator();
	        //add integration requests
	        mRequestsToProcess.addAll(mAcknowledgeRequestDataMap.values());
    	}catch(Exception e){
    		log.error(e.getMessage(), e);
    	}finally{
    		closeConnection(con);
    	}
    }
    
    
    
    protected void processLine(Object pObj) {
        
    	AcknowledgeRequestDataFlat ackReq = (AcknowledgeRequestDataFlat) pObj;
    	mAcknowledgeRequestDataFlatList.add(ackReq);
      }
    
    /**
     * Defines the fixed width format which is as follows:
001-011   invoice_number 
013-019   customer_number
021-045   address 
047-066   city 
068-069   state 
073-081   zip 
083-099   qty 
101-117   cost 
119-124   dist sku 
126-155   item description 
157-166   order_select_date 
168-190   Running total of qty 
     */
    private void setupFileMapping(){
        RemittanceData del = RemittanceData.createValue();
        del.getPaymentReferenceNumber();
        
        //Header records, these are duplicated throught the file, so we will have
        //to ge through and get rid of the duplicates at the end.
        FixedWidthFieldDef[] charRanges = new FixedWidthFieldDef[7];
        charRanges[0] = new FixedWidthFieldDef(0,11,"erpPoNum",null,null,null);
        charRanges[1] = new FixedWidthFieldDef(12,19,"siteKey",null,null,null);
        //charRanges[2] = new FixedWidthFieldDef(20,46,"address",null,null,null);
        //charRanges[3] = new FixedWidthFieldDef(47,67,"city",null,null,null);
        //charRanges[4] = new FixedWidthFieldDef(68,69,"state",null,null,null);
        //charRanges[5] = new FixedWidthFieldDef(72,81,"zip",null,null,null); 
        charRanges[2] = new FixedWidthFieldDef(82,99,"quantity",null,null,null); 
        charRanges[3] = new FixedWidthFieldDef(100,117,"price",null,null,null);
        charRanges[4] = new FixedWidthFieldDef(118,124,"distSkuNum",null,null,null); 
        charRanges[5] = new FixedWidthFieldDef(125,155,"itemName",null,null,null); 
        charRanges[6] = new FixedWidthFieldDef(156,168,"ackDate","yyyy-MM-dd",null,null); 
        Filter fil = this.addFilter(charRanges,"com.cleanwise.service.api.value.AcknowledgeRequestDataFlat",0,0,"");
    }

	public void parseLine(List parsedLine) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Does the main work of translating the file
	 */
	public void translate(InputStream in, String streamType) throws Exception {
		setupFileMapping();
		this.parse(in);
	}

	/**
	 *calls other translate method
	 */
	public void translate(String s) throws Exception {
		
		// call other translate method
		ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes());
		translate(in, "txt");
	}

	public ElectronicTransactionData getTransactionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public Translator getTranslator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void translateInterchange() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void translateInterchangeHeader() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void translateInterchangeTrailer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public InterchangeData createInterchangeObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public ElectronicTransactionData createTransactionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the requests that were translated by this translator
	 */
	public IntegrationRequestsVector getRequestsToProcess() {
		return mRequestsToProcess;
	}

	
	public String getTranslationReport() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Used when a problem happens.  Checked problems should log them and then set this flag.
	 * Unchecked problems should throw an error message.
	 */
	public boolean isFail() {
		return mFailed;
	}
	/**
	 * Used when a problem happens.  Checked problems should log them and then set this flag.
	 * Unchecked problems should throw an error message.
	 */
	private void setFail(boolean isFailed){
		mFailed = isFailed;
		
	}

	/**
	 * Initialize the translator.  This should be called when this class is instantiated.
	 */
	public void setTranslator(Translator translator) {
		mTranslator = translator;
	}
    
}
