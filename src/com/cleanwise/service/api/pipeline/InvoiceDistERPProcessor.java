/*
 * InvoiceDistERPProcessor.java
 *
 * Created on August 1, 2005, 2:37 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.session.SelfServiceErpBean;
import com.cleanwise.service.api.session.ThruStoreErpBean;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceDistData;

import java.sql.Connection;

/**
 *
 * @author bstevens
 */
public class InvoiceDistERPProcessor implements InvoiceDistPipeline{

    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        try{
            if(pBaton.isHasError()){
                return;
            }
            InvoiceDistData invoice = pBaton.getInvoiceDistData();
            //determin erp code if possible
            if(!Utility.isSet(invoice.getErpSystemCd())){
            	if(Utility.isSet(pBaton.getOrder().getErpSystemCd())){
            		invoice.setErpSystemCd(pBaton.getOrder().getErpSystemCd());
            	}else{
	            	PropertyUtil pru = new PropertyUtil(pCon);
	            	String erpCd = pru.fetchValue(0,invoice.getStoreId(),RefCodeNames.PROPERTY_TYPE_CD.ERP_SYSTEM);
	            	invoice.setErpSystemCd(erpCd);
            	}
            }
            if(!Utility.isSet(invoice.getErpSystemCd())){
                OrderDAO.enterOrderProperty(pCon,RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,"Invoice Error","No ERP System Code",
                        invoice.getOrderId(),0,invoice.getInvoiceDistId(),0,0,0,0,"System");
                invoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
            }else if(RefCodeNames.ERP_SYSTEM_CD.SELF_SERVICE.equals(invoice.getErpSystemCd())) {
                //erp processing gets stuff out of the db and doesn't know anything of the pipeline yet, so save everything to db.
                pBaton.saveBatonObjects(pCon);
                SelfServiceErpBean selfService = new SelfServiceErpBean();
                selfService.service(invoice.getInvoiceDistId());
                //now re-populat the baton
                pBaton.populateBaton(pCon,invoice.getInvoiceDistId());
            } else {
                //erp processing gets stuff out of the db and doesn't know anything of the pipeline yet, so save everything to db.
                pBaton.saveBatonObjects(pCon);
                ThruStoreErpBean  thuB = new ThruStoreErpBean();
                thuB.generateCustomerInvoice("system",invoice.getInvoiceDistId());
                //now re-populat the baton
                pBaton.populateBaton(pCon,invoice.getInvoiceDistId());
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

}
