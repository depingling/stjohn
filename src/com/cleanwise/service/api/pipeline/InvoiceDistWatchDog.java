/*
 * InvoiceDistUpdateObjects.java
 *
 * Created on August 1, 2005, 9:44 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.dao.OrderPropertyDataAccess;
import com.cleanwise.service.api.session.Pipeline;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderPropertyData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;

/**
 *Updates all of the objects that are run through the pipeline.  The erp processing currently has its own update
 *statements, so care needs to be taken that whatever modifications made by that are preserved.
 * @author bstevens
 */
public class InvoiceDistWatchDog implements InvoiceDistPipeline{
	private static final Category log = Category.getInstance(InvoiceDistWatchDog.class);
    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
    	
    	try{
    		//Last ditch check for a duplicate
	        DBCriteria crit = new DBCriteria();
	        List<String> statusCodes = new ArrayList<String>();
	        statusCodes.add(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
	        statusCodes.add(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE);
	        statusCodes.add(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
	        statusCodes.add(RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY);
	        statusCodes.add(RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE);
	        crit.addNotOneOf(InvoiceDistDataAccess.INVOICE_STATUS_CD,statusCodes);
	        crit.addNotEqualTo(InvoiceDistDataAccess.INVOICE_DIST_ID, pBaton.getInvoiceDistData().getInvoiceDistId());
	        crit.addEqualTo(InvoiceDistDataAccess.STORE_ID, pBaton.getInvoiceDistData().getStoreId());
	        crit.addEqualTo(InvoiceDistDataAccess.INVOICE_NUM, pBaton.getInvoiceDistData().getInvoiceNum());
	        crit.addEqualTo(InvoiceDistDataAccess.BUS_ENTITY_ID, pBaton.getInvoiceDistData().getBusEntityId());
	        InvoiceDistDataVector existingInvoices = InvoiceDistDataAccess.select(pCon, crit);
	        if(existingInvoices != null && existingInvoices.size() > 0){
	        	pBaton.addError("pipeline.message.invoiceDuplicateFound");
	        }
	        
	        //enter an order property to tell what system this was processed on.
	        //useful for debugging 2 systems processing the same invoice
	        try{
		        OrderPropertyData opd = OrderPropertyData.createValue();
		        opd.setAddBy("system");
		        opd.setModBy("system");
		        opd.setInvoiceDistId(pBaton.getInvoiceDistData().getInvoiceDistId());
		        opd.setOrderId(pBaton.getInvoiceDistData().getOrderId());
		        opd.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
		        opd.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
		        opd.setShortDesc(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
		        String ipAddress = java.net.InetAddress.getLocalHost().getHostAddress();
		        opd.setValue("Invoice Pipeline IP: "+ipAddress);
		        OrderPropertyDataAccess.insert(pCon, opd);
	        }catch(Exception e){
	        	//continue
	        	e.printStackTrace();
	        }
    	}catch(SQLException e){
    		log.error("Caught unexpected error:", e);
    		throw new PipelineException(e.getMessage());
    	}
    }
    
}
