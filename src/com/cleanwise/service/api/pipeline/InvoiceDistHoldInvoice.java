/*
 * InvoiceDistHoldInvoice.java
 *
 * Created on October 19, 2005, 12:08 PM
 *
 * Copyright October 19, 2005 Cleanwise, Inc.
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

/**
 * Determines if invoice is in a hold state and if so stops the processing of it.
 * @author bstevens
 */
public class InvoiceDistHoldInvoice implements InvoiceDistPipeline{
    
    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        try{
            if(pBaton.isInvoiceApproved()){
                return;
            }
            int distId = pBaton.getInvoiceDistData().getBusEntityId();
            if(distId == 0){
                return;
            }
            
            PropertyUtil pru = new PropertyUtil(pCon);
            String holdForStr = pru.fetchValueIgnoreMissing(0, distId, RefCodeNames.PROPERTY_TYPE_CD.HOLD_INVOICE);
            if(!Utility.isSet(holdForStr)){
                return;
            }
            int holdFor = 0;
            try{
                holdFor = Integer.parseInt(holdForStr);
            }catch(Exception e){
                throw new PipelineException("Hold invoice property was not parsable into an integer ("+holdForStr+")");
            }
            
            Calendar cal = Calendar.getInstance();
            Date today = Utility.truncateDateByDay(new Date());
            Date invDate = Utility.truncateDateByDay(pBaton.getInvoiceDistData().getAddDate());
            cal.setTime(invDate);
            cal.add(Calendar.DAY_OF_YEAR, holdFor);
            invDate = cal.getTime();
            if(holdFor > 0 && invDate.after(today)){
                pBaton.setWhatNext(pBaton.STOP);
                //add a note??
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }
    
}
