/*
 * InboundHHSBudgetLoader.java
 *
 * Created on December 10, 2005, 12:57 PM
 *
 * Copyright December 10, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BudgetDefinitionRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * This class extends the mapping framework to simply combine 2 lines together when necessary.  The chemical budget
 *and the tax free Chemical budget get summed together.  Other than that it is the standard loader.
 * @author bstevens
 */
public class InboundHHSBudget extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
    HashMap groupedData = new HashMap();
    private static String BUDGET_PREFIX_TRIM = "5030";
    
    /**
     *Overides to look for a set of text to ignore
     * @throws Exception 
     */
    public void parseLine(List pParsedLine) throws Exception{
        String unParsedLine = Utility.toCommaSting(pParsedLine); //unparse the line to perform some tests on the "raw" data
        if(0==getCurrentLineNumber()){
            if(pParsedLine.isEmpty()){
                return;
            }
            //String unParsedLine = Utility.toCommaSting(pParsedLine); //unparse the line to perform some tests on the "raw" data
            if(Utility.isSet(unParsedLine)){
                unParsedLine = unParsedLine.toUpperCase();
                if(unParsedLine.startsWith("Description:".toUpperCase())){
                    log.info("Ignoring line: "+unParsedLine);
                    return;
                }
                if(unParsedLine.startsWith("Version:".toUpperCase())){
                    log.info("Ignoring line: "+unParsedLine);
                    return;
                }
                if(unParsedLine.startsWith("Last Read:".toUpperCase())){
                    log.info("Ignoring line: "+unParsedLine);
                    return;
                }
                unParsedLine = unParsedLine.toUpperCase();
                if(unParsedLine.indexOf("Jan,Feb,Mar,Apr".toUpperCase()) > -1){
                    log.info("Ignoring line: "+unParsedLine);
                    return;
                }
                if(unParsedLine.indexOf("January,February,March".toUpperCase()) > -1){
                    log.info("Ignoring line: "+unParsedLine);
                    return;
                }
            }
        }
        log.info("Parsing line: "+unParsedLine);
        super.parseLine(pParsedLine);
    }
    
    
    /**
     *Intercept the requests as we will controll what gets added
     */
    protected void processParsedObject(Object pParsedObject){
        try{
            BudgetDefinitionRequest bdef = (BudgetDefinitionRequest) pParsedObject;
            if(!Utility.isSet(bdef.getEntityKey())){
            	//nothing to do with it.
            	return;
            }
            StringBuffer key = new StringBuffer();
            if(bdef.getEntityKey() != null){
                key.append(bdef.getEntityKey());
            }
            key.append("-::-");
            if(bdef.getBudgetIdentifier() != null){
                String bid = bdef.getBudgetIdentifier();
                int idx = bid.indexOf(' ');
                int idx2 = bid.indexOf('-');
                idx = Utility.min(idx, idx2);
                if(idx >=  0){
                    bid = bid.substring(0,idx);
                }
                bid = bid.trim();
                if(bid.startsWith(BUDGET_PREFIX_TRIM)){
                    bid = bid.substring(BUDGET_PREFIX_TRIM.length());
                }
                //combine the chemicals into one line...we seperate it back out on the backend.
                //The system has no way of changing the cost center based on wheather or not an 
                //item is taxable or not dynamically
                if("400".equals(bid)){
                    bid = "300";
                }
                if("150".equals(bid)){
                    bid = "100";
                }
                bdef.setBudgetIdentifier(bid);
                key.append(bid);
            }
            BudgetDefinitionRequest ebdef = (BudgetDefinitionRequest) groupedData.get(key.toString());
            ebdef = addBudgetDefinitionRequests(ebdef,bdef);
            groupedData.put(key.toString(), ebdef);
        }catch(ClassCastException e){
        	log.error("got: "+pParsedObject.getClass().getName()+" expected: "+pParsedObject.getClass().getName(),e);
            throw e;
        }
    }
    
    /**
     *Adds the amounts in the budgets from b2 into b1.  If either is null returns the other budget
     */
    private BudgetDefinitionRequest addBudgetDefinitionRequests(BudgetDefinitionRequest b1, BudgetDefinitionRequest b2){
        if(b1 == null){
            return b2;
        }
        if(b2 == null){
            return b1;
        }
        b1.setAmount1(Utility.addAmt(b1.getAmount1(), b2.getAmount1()));
        b1.setAmount2(Utility.addAmt(b1.getAmount2(), b2.getAmount2()));
        b1.setAmount3(Utility.addAmt(b1.getAmount3(), b2.getAmount3()));
        b1.setAmount4(Utility.addAmt(b1.getAmount4(), b2.getAmount4()));
        b1.setAmount5(Utility.addAmt(b1.getAmount5(), b2.getAmount5()));
        b1.setAmount6(Utility.addAmt(b1.getAmount6(), b2.getAmount6()));
        b1.setAmount7(Utility.addAmt(b1.getAmount7(), b2.getAmount7()));
        b1.setAmount8(Utility.addAmt(b1.getAmount8(), b2.getAmount8()));
        b1.setAmount9(Utility.addAmt(b1.getAmount9(), b2.getAmount9()));
        b1.setAmount10(Utility.addAmt(b1.getAmount10(), b2.getAmount10()));
        b1.setAmount11(Utility.addAmt(b1.getAmount11(), b2.getAmount11()));
        b1.setAmount12(Utility.addAmt(b1.getAmount12(), b2.getAmount12()));
        b1.setAmount13(Utility.addAmt(b1.getAmount13(), b2.getAmount13()));
        return b1;
        
    }
    
    protected void doPostProcessing(){
        Iterator it = groupedData.values().iterator();
        while(it.hasNext()){
            addIntegrationRequest(it.next());
        }
    }
    
}
