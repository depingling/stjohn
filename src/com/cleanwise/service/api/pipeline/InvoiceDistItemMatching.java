package com.cleanwise.service.api.pipeline;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Iterator;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;

import org.apache.log4j.Category;

public class InvoiceDistItemMatching implements InvoiceDistPipeline {
	private static final Category log = Category.getInstance(InvoiceDistItemMatching.class);

	public void process(InvoiceDistPipelineBaton pBaton, Connection pCon, APIAccess pFactory) throws PipelineException {

		if (pBaton.isInvoiceApproved()) {
			log.info("process.invoice is approved, bypassing pipeline");
			return;
		}

		InvoiceDistData invoice = pBaton.getInvoiceDistData();
		InvoiceDistDetailDataVector invoiceItems = pBaton.getInvoiceDistDetailDataVector();
		Iterator it = invoiceItems.iterator();
		while (it.hasNext()) {
			InvoiceDistDetailData invDetailD = (InvoiceDistDetailData) it.next();
			if(invDetailD.getOrderItemId() == 0){
		          pBaton.addError("pipeline.message.noItemFoundInvoiceDist",
		        		  invDetailD.getDistItemSkuNum(),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
		        		  Integer.toString(invDetailD.getDistLineNumber()),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.INTEGER,
		        		  invoice.getErpPoNum(),RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
		        		  0, invDetailD.getInvoiceDistDetailId());
			}
		}
	}
}
