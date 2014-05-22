package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Category;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;

public class InvoiceDistDateAnalysis implements InvoiceDistPipeline{
	private static final Category log = Category.getInstance(InvoiceDistPipeline.class);
	private static Date MAX_BEG_DATE ;
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	static{
		try{
			MAX_BEG_DATE = DATE_FORMAT.parse("01/01/1990");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
		Date invDate = pBaton.getInvoiceDistData().getInvoiceDate();
        if(invDate == null){
        	pBaton.addError("pipeline.message.invoiceDateEmpty");
        	return;
        }
        try{
        	pBaton.setSingularProperty(DATE_FORMAT.format(invDate), RefCodeNames.ORDER_PROPERTY_TYPE_CD.VENDOR_REQUESTED_INV_DATE, pCon, true);
        }catch(SQLException e){
        	e.printStackTrace();
        	throw new PipelineException(e.getMessage());
        }

        Date now = new Date();
        if(MAX_BEG_DATE.after(invDate)){
        	log.info("invoice date: "+invDate+" is before "+MAX_BEG_DATE);
        	pBaton.addError("pipeline.message.invoiceBefore1990");
        }else if(now.before(invDate)){
        	log.info("invoice date: "+invDate+" is after today ("+now+")");
        	pBaton.addError("pipeline.message.invoiceAfterToday");
        }


	}
}
