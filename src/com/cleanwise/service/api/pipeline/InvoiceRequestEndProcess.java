package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDataAccess;
import com.cleanwise.service.api.dao.InvoiceDistDetailDataAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 * Title:        InvoiceRequestEndProcess
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         25.04.2007
 * Time:         13:42:21
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class InvoiceRequestEndProcess implements InvoiceRequestPipeline {

    private String className="InvoiceRequestEndProcess";

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton, Connection pCo, APIAccess pFactory) throws PipelineException {

        int insertedInvoiceItems=pBaton.getCountInsertedInvoiceItems();
        InvoiceDistDataVector insertedInvoices = pBaton.getInsertedInvoices();
        InvoiceDistData invoiceD = pBaton.getInvoiceRequest().getInvoiceD();
        boolean insertInvoice = true;
        BigDecimal recivedTotal =pBaton.getRecivedTotal();
        InvoiceDistDetailDescDataVector toInsertItems = pBaton.getToInsertItems();
        try {

            IntegrationServices intServ=pFactory.getIntegrationServicesAPI();
            PurchaseOrder poEjb = pFactory.getPurchaseOrderAPI();
            Distributor distEjb = pFactory.getDistributorAPI();
            Order orderEjb = pFactory.getOrderAPI();
            if (pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0) {


                if(insertedInvoiceItems == 0){
                    //if we didn't insert any items then we have to go back and add the freight onto
                    //the first invoice that we already loaded.  If there isn't one then we just set the
                    //freight and assume it is a freight only invoice.
                    if(!insertedInvoices.isEmpty()){
                        InvoiceDistData firstInvoice = (InvoiceDistData) insertedInvoices.get(0);
                        firstInvoice.setFreight(invoiceD.getFreight());
                        firstInvoice.setSalesTax(invoiceD.getSalesTax());
                        //invoiceD = firstInvoice;
                        poEjb.updateInvoiceDistData(firstInvoice);
                        intServ.insertDistributorInvoiceNotes(firstInvoice, pBaton.getOrder(), pBaton.getNotesToLog(),pBaton.getMiscInvoiceNotes());
                        insertInvoice = false;
                    }
                    //if no items were inserted and there is no freight do not insert the invoice.  This would happen if an invoice
                    //was recieved completeing the shipment of a UOM converted invoice, the item would be moved to the other invoice,
                    //the intent is that the freight would remain on this invoice.  If there is no freight or tax don't insert the
                    //invoice
                    if(Utility.isZeroValue(invoiceD.getSalesTax()) && Utility.isZeroValue(invoiceD.getFreight()) && Utility.isZeroValue(recivedTotal)){

                        //if(!invoiceD.getSalesTax().equals(ZERO) && !invoiceD.getFreight().equals(ZERO) && !invoiceD.getSubTotal().equals(ZERO)){
                        insertInvoice = false;
                        //}
                    }
                }

                if(insertInvoice){
                    invoiceD.setAddBy(pBaton.getProcessName());
                    invoiceD.setModBy(pBaton.getProcessName());
                    try{
	                    String ipAddress = java.net.InetAddress.getLocalHost().getHostAddress();
	                    pBaton.getNotesToLog().add("Invoice Request Pipeline Server IP: " + ipAddress);
                    }catch(Exception e){
                    	//continue
                    	e.printStackTrace();
                    }
                    
                    checkSubTotalMatch(pBaton);
                    boolean checkDuplInvoiceNum = pBaton.getTradingPartnerInfo() != null ? !pBaton.getTradingPartnerInfo().isRelaxValidateInboundDuplInvoiceNum() : true;
                    invoiceD = intServ.insertInvoiceDistData(invoiceD, pBaton.getExceptionCount(), recivedTotal, pBaton.getNotesToLog(),insertedInvoices, checkDuplInvoiceNum);
                    insertedInvoices.add(invoiceD);
                    
                    intServ.insertDistributorInvoiceNotes(invoiceD, pBaton.getOrder(), pBaton.getNotesToLog(), pBaton.getMiscInvoiceNotes());
                }

                intServ.insertInvoiceDistDetailAndProperties(toInsertItems, invoiceD.getInvoiceDistId(),pBaton.getOrder());

            } else { //if we could not find the order/po or any items
                if(!invoiceD.getInvoiceStatusCd().equals(RefCodeNames.INVOICE_STATUS_CD.DUPLICATE)) {
                    invoiceD.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
                }
                //one distributor hooked up to the trading profile,
                //we can assume the distributor then

                IdVector pDistributorIds= (IdVector) pBaton.getTradPartnerAssoc().get(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

                if(pDistributorIds != null && pDistributorIds.size() == 1){
                    Object o = pDistributorIds.get(0);
                    //sanity check
                    if(o != null && o instanceof Integer){
                        invoiceD.setBusEntityId(((Integer) o).intValue());
                    }

                }

                StringBuffer note = new StringBuffer("");
                if (Utility.isSet(invoiceD.getErpPoNum())) {
                    note.append("No matching order found for po number: ");
                    note.append(invoiceD.getErpPoNum());
                    note.append("\n");
                    note.append("and distributors: ");
                    //find valid dists and log note for user
                    if(pDistributorIds != null && pDistributorIds.size() > 0) {
                        DBCriteria crit2 = new DBCriteria();
                        BusEntitySearchCriteria crit=new BusEntitySearchCriteria();
                        crit.setDistributorBusEntityIds(pDistributorIds);
                        BusEntityDataVector beds = distEjb.getDistributrBusEntitiesByCriteria(crit);
                        Iterator it = beds.iterator();
                        while(it.hasNext()) {
                            BusEntityData bed = (BusEntityData) it.next();
                            note.append(bed.getShortDesc());
                            if(it.hasNext()){
                                note.append(",");
                            }
                        }
                    }
                } else {
                    note.append("Invoice received with no po number supplied");
                }
                pBaton.getNotesToLog().add(note.toString());
                pBaton.incExceptionCount();
                checkSubTotalMatch(pBaton);
                invoiceD = poEjb.updateInvoiceDistData(invoiceD);
                insertedInvoices.add(invoiceD);
                

                for (int i = 0;i < pBaton.getInvoiceRequest().getInvoiceDetailDV().size();i++) {

                    InvoiceDistDetailData invDetailD = (InvoiceDistDetailData)pBaton.getInvoiceRequest().getInvoiceDetailDV().get(i);
                    invDetailD.setInvoiceDistId(invoiceD.getInvoiceDistId());
                    invDetailD.setAddBy(pBaton.getProcessName());
                    invDetailD.setModBy(pBaton.getProcessName());
                    orderEjb.updateInvoiceDistDetail(invDetailD);
                }
                intServ.insertDistributorInvoiceNotes(invoiceD, null, pBaton.getNotesToLog(),pBaton.getMiscInvoiceNotes());
            }
              pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
    	  e.printStackTrace();
            throw new PipelineException(e.getMessage());
        } finally {

        }
        return pBaton;
    }

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton) throws PipelineException, NamingException {
        return process(pBaton, null, APIAccess.getAPIAccess());
    }
    
    private void checkSubTotalMatch(InvoiceRequestPipelineBaton pBaton){
    	if (!pBaton.getInvoiceRequest().isCheckTotal())
    		return;
    	BigDecimal controlTotal = pBaton.getInvoiceRequest().getControlTotalSum();
    	InvoiceDistData invoiceD = pBaton.getInvoiceRequest().getInvoiceD();
    	if(controlTotal == null || invoiceD.getSubTotal() == null){
    		return;
    	}
    	if (controlTotal.subtract(invoiceD.getSubTotal()).abs().doubleValue() > 0.05){
    		invoiceD.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
    		StringBuffer note = new StringBuffer("Error: Distributor invoice received total(" + invoiceD.getSubTotal() + ") " +
    				"do not match calculated total (" + controlTotal + ")");
    		pBaton.getNotesToLog().add(note.toString());
    		pBaton.incExceptionCount();
    	}
    }

}
