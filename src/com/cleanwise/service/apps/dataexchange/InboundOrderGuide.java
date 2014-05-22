package com.cleanwise.service.apps.dataexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.value.OrderGuideData;
import com.cleanwise.service.api.value.OrderGuideLoadRequestData;

public class InboundOrderGuide extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	ArrayList parsedObjects = new ArrayList(); 
	
	/**
	 * Overidden as we will be doin a whole bunch of post processing work
	 * and recreating this list.  @see addIntegrationRequestSuper.
	 */
	protected void addIntegrationRequest(Object pRequest){
		log.info("addIntegrationRequest CALLED");
		parsedObjects.add(pRequest);
	}
	/**
	 * Calls the super call implementation of addIntegrationRequest
	 */
	private void addIntegrationRequestSuper(Object pRequest){
		super.addIntegrationRequest(pRequest);
	}
	
	protected void doPostProcessing() {
		//sort the list by entity key
		Collections.sort(parsedObjects,ENTITYKEY_SORT);
		log.info("Sorted");
		//loop through
		String lastKey=null;
		OrderGuideLoadRequestData oG = null;
		Iterator it = parsedObjects.iterator();
		while(it.hasNext()){
			FlatOrderGuideRequest flatOG =(FlatOrderGuideRequest) it.next();
			if(flatOG.getEntityKey() == null || flatOG.getSku() == null){
				//Can't do anything with it, skip it
				continue;
			}
			if(!flatOG.getEntityKey().equals(lastKey)){
				log.info(flatOG.getEntityKey()+"!="+lastKey);
				lastKey = flatOG.getEntityKey();
				oG = createEmptyOrderGuideLoadRequestData(flatOG);
				addIntegrationRequestSuper(oG); //add requst to really be processed
			}
			
			oG.addItem(0,flatOG.getSku());
		}		
	}
	
	
	private OrderGuideLoadRequestData createEmptyOrderGuideLoadRequestData(FlatOrderGuideRequest flatOG){
		OrderGuideLoadRequestData oG = new OrderGuideLoadRequestData();
		oG.setOrderGuide(OrderGuideData.createValue());
		oG.getOrderGuide().setOrderGuideTypeCd(flatOG.getOrderGuideTypeCd());
		oG.getOrderGuide().setShortDesc(flatOG.getEntityKey());//default name
		oG.setEntityKey(flatOG.getEntityKey());
		oG.setSkuTypeCd(flatOG.getSkuType());
		return oG;
	}
	
	
	public static class FlatOrderGuideRequest{
		public FlatOrderGuideRequest(){
			
		}
		String entityKey;
		public void setEntityKey(String v){entityKey = v;}
		public String getEntityKey(){return entityKey;}
		
		String sku;
		public void setSku(String v){sku = v;}
		public String getSku(){return sku;}
		
		String orderGuideTypeCd;
		public void setOrderGuideTypeCd(String v){orderGuideTypeCd = v;}
		public String getOrderGuideTypeCd(){return orderGuideTypeCd;}
		
		String skuType;
		public void setSkuType(String v){skuType = v;}
		public String getSkuType(){return skuType;}
		
		public String toString(){
			return "FlatOrderGuideRequest: "+entityKey;
		}
	}
	
	static final Comparator ENTITYKEY_SORT = new Comparator() {
	    public int compare(Object o1, Object o2)
	    {
		String s1 = ((FlatOrderGuideRequest)o1).getEntityKey();
		String s2 = ((FlatOrderGuideRequest)o2).getEntityKey();
		if(s1 == null && s2 == null){
			return 0;
		}else if(s1 == null){
			s1 = "";
		}else if(s2 == null){
			s2 = "";
		}
		
		return s1.compareTo(s2);
	    }
	};
}
