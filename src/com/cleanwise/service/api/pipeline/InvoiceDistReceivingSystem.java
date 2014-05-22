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
import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.session.Pipeline;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.value.InvoiceDistDetailData;
import com.cleanwise.service.api.value.InvoiceDistDetailDataVector;
import com.cleanwise.service.api.value.OrderItemData;
import java.sql.Connection;
import java.util.Iterator;

/**
 *Updates all of the objects that are run through the pipeline.  The erp processing currently has its own update
 *statements, so care needs to be taken that whatever modifications made by that are preserved.
 * @author bstevens
 */
public class InvoiceDistReceivingSystem implements InvoiceDistPipeline{

    public void process(
            InvoiceDistPipelineBaton pBaton,
            Connection pCon,
            APIAccess pFactory)
            throws PipelineException{
        try{
            if(pBaton.isInvoiceApproved() || pBaton.getInvoiceDistDetailDataVector()== null){
                return;
            }

            //In this version only the first invoice is ever looked at for the recieving system
            if(pBaton.getPreviousInvoices(pCon) != null && pBaton.getPreviousInvoices(pCon).size() > 0){
                return;
            }

            PropertyUtil pru = new PropertyUtil(pCon);
            String recICd = pru.fetchValueIgnoreMissing(0, pBaton.getInvoiceDistData().getBusEntityId(), RefCodeNames.PROPERTY_TYPE_CD.RECEIVING_SYSTEM_INVOICE_CD);
            if(!Utility.isSet(recICd) || RefCodeNames.RECEIVING_SYSTEM_INVOICE_CD.DISABLED.equals(recICd)){
                return;
            }

            boolean ordRec = Utility.isTrue(pBaton.getOrderProperty(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_RECEIVED, pCon));
            if(!ordRec && RefCodeNames.RECEIVING_SYSTEM_INVOICE_CD.ENTER_ERRORS_ONLY_FIRST_ONLY.equals(recICd)){
                return; //everything is okay, the user is only entering data if something went wrong
            }

            if(!ordRec && RefCodeNames.RECEIVING_SYSTEM_INVOICE_CD.REQUIERE_ENTRY_FIRST_ONLY.equals(recICd)){
                //should we wait, or error out??
                //pBaton.addError("Order not received against when invoice received");
                pBaton.addError("pipeline.message.requireEntryFirstOnly");
                return;
            }


            Iterator it = pBaton.getInvoiceDistDetailDataVector().iterator();
            while(it.hasNext()){
                InvoiceDistDetailData idd = (InvoiceDistDetailData) it.next();
                if(idd.getOrderItemId() > 0){
                    OrderItemData oid = OrderItemDataAccess.select(pCon,idd.getOrderItemId());
                    if(oid.getTotalQuantityReceived() < idd.getDistItemQuantity()){ //XXX what to do with prev qty recieved/invoiced
                        //pBaton.addError("Receiving System, overshipped qty for vendor sku: "+idd.getDistItemSkuNum()+" when invoice received");
                        pBaton.addError("pipeline.message.overshipQtyWhenInvoiceReceived",
                                        idd.getDistItemSkuNum(), RefCodeNames.PIPELINE_MESSAGE_ARG_CD.STRING,
                                        null, null, null, null);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

}
