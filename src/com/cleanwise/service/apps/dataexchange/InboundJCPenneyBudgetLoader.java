package com.cleanwise.service.apps.dataexchange;

import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.log4j.Logger;



import com.cleanwise.service.api.util.BudgetUtil;
import com.cleanwise.service.api.value.BudgetDefinitionRequest;
import com.cleanwise.service.api.value.IntegrationRequestData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;

public class InboundJCPenneyBudgetLoader extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	/*private static final BigDecimal chemPer = new BigDecimal(0.27);
	private static final BigDecimal janPer = new BigDecimal(0.12);
	private static final BigDecimal marblePer = new BigDecimal(0);
	private static final BigDecimal linerPer = new BigDecimal(0.11);
	private static final BigDecimal sanPapPer = new BigDecimal(0.50);*/
	
	private static final BigDecimal chemPer = new BigDecimal(0.31);
	private static final BigDecimal janPer = new BigDecimal(0.18);
	private static final BigDecimal marblePer = new BigDecimal(0);
	private static final BigDecimal linerPer = new BigDecimal(0.17);
	private static final BigDecimal sanPapPer = new BigDecimal(0.34);
	
	
	 /**
     * This method is called when the file is done processing.  Implementing methods should
     * check the failed flag if post processing should only be done for successfully processed
     * files.  The default implementation is to do nothing.
     */
    protected void doPostProcessing(){
    	IntegrationRequestsVector newList = new IntegrationRequestsVector();
    	
    	//loop through and do the split out over the different cost centers and set the keys.
    	Iterator it = getIntegrationRequests().iterator();
    	while(it.hasNext()){
    		Object o = it.next();
    		log.info("classname: "+o.getClass().getName());
    		IntegrationRequestData ir = (IntegrationRequestData) o;
    		BudgetDefinitionRequest def = (BudgetDefinitionRequest) ir.getIntegrationRequest();
    		//it.remove();//remove old value, we will add new ones.
    		
    		BudgetDefinitionRequest master = def;
    		
    		BudgetDefinitionRequest chemicals = (BudgetDefinitionRequest) master.clone();
    		BudgetDefinitionRequest janitorial = (BudgetDefinitionRequest) master.clone();
    		BudgetDefinitionRequest marble = (BudgetDefinitionRequest) master.clone();
    		BudgetDefinitionRequest liners = (BudgetDefinitionRequest) master.clone();
    		BudgetDefinitionRequest sanitaryPaper = (BudgetDefinitionRequest) master.clone();
    		
    		chemicals.setBudgetIdentifier("Chemicals");
    		janitorial.setBudgetIdentifier("Janitorial");
    		marble.setBudgetIdentifier("Marble");
    		liners.setBudgetIdentifier("Plastic");
    		sanitaryPaper.setBudgetIdentifier("Sanitary");

    		for(int i=1; i<= 13; i++){
    			BigDecimal amtBase = master.getAmount(i);
    		
    			if(amtBase != null){
    				log.info("multiplying requests "+amtBase+" * "+chemPer);
	    			chemicals.setAmount(amtBase.multiply(chemPer), i);
	    			janitorial.setAmount(amtBase.multiply(janPer), i);
	    			marble.setAmount(amtBase.multiply(marblePer), i);
	    			liners.setAmount(amtBase.multiply(linerPer), i);
	    			sanitaryPaper.setAmount(amtBase.multiply(sanPapPer), i);
    			}
    			
    		}
    		log.debug("Size before: "+newList.size());
    		newList.add(new IntegrationRequestData(chemicals,ir.getMessage(),ir.isError()));
    		newList.add(new IntegrationRequestData(janitorial,ir.getMessage(),ir.isError()));
    		newList.add(new IntegrationRequestData(marble,ir.getMessage(),ir.isError()));
    		newList.add(new IntegrationRequestData(liners,ir.getMessage(),ir.isError()));
    		newList.add(new IntegrationRequestData(sanitaryPaper,ir.getMessage(),ir.isError()));
    		log.debug("Size after: "+newList.size());
    		def.clone();
    	}
    	//refresh the list with our new data
    	getIntegrationRequests().clear();
    	getIntegrationRequests().addAll(newList);
    }
}
