/*
 * FreightDetailReport.java
 *
 * Created on March 11, 2005, 2:50 PM
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.ConnectionContainer;
import java.util.Map;

import java.util.ArrayList;




/**
 * Simple report that sleeps for the specified number of seconds.  Used for debugging network timeout connections.
 */
public class TimeTestReport implements GenericReportMulti {
    private static final int MAX_SECONDS_ALLOWED =  60 * 15; //15 minutes
    

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) 
    throws Exception {
        String waitTimeStr = (String) pParams.get("Time In Seconds");
        int waitTimeNum;
        try{
            waitTimeNum = Integer.parseInt(waitTimeStr.trim());
        }catch(Exception e){
            throw new RuntimeException("^clw^Time In Seconds must be a number^clw^");
        }
        //Sanity check to make sure we don't have a thread running too long
        if(waitTimeNum > MAX_SECONDS_ALLOWED){
            throw new RuntimeException("^clw^Time In Seconds must be less than "+MAX_SECONDS_ALLOWED+"^clw^");
        }
        
        try{
            Thread.sleep(waitTimeNum * 1000);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("^clw^Error happened during wait: "+e.getMessage()+"^clw^");
        }
        
        
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        GenericReportResultView result = GenericReportResultView.createValue();
        GenericReportColumnViewVector header = getReportHeader();
        result.setColumnCount(header.size());
        result.setHeader(header);
        result.setName("Timer");
        
        
        
        ArrayList row = new ArrayList();
        ArrayList data = new ArrayList();
        
        row.add("Done sleeping: "+waitTimeNum);
        data.add(row);
        
        result.setTable(data);
			
        resultV.add(result);
        
        return resultV;
						
    }
    
    private GenericReportColumnViewVector getReportHeader() {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Data",0,255,"VARCHAR2"));
        
        return header;
    }


    
}


