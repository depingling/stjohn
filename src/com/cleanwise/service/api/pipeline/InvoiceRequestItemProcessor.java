package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Title:        InvoiceRequestItemProcessor
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         24.04.2007
 * Time:         0:53:52
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 * @noinspection UnusedAssignment
 */

public class InvoiceRequestItemProcessor implements InvoiceRequestPipeline {

    private static final Logger log = Logger.getLogger(InvoiceRequestItemProcessor.class);
    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal ONE = new BigDecimal(1);

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton, Connection pCon, APIAccess pFactory) throws PipelineException {

       if (!(pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0)) {
            log.info("process => could not find the order/po or any items");
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
            return pBaton;
        }

        InvoiceRequestData pInvoiceReq = pBaton.getInvoiceRequest();
        InvoiceDistData invoiceD = pInvoiceReq.getInvoiceD();
        HashMap ccMap = new HashMap();
        BigDecimal recvdTotal = new BigDecimal(0.0);
        InvoiceDistDataVector insertedInvoices = new InvoiceDistDataVector();

        int insertedInvoiceItems = pBaton.getCountInsertedInvoiceItems();

        InvoiceDistDetailDescDataVector toInsertItems = new InvoiceDistDetailDescDataVector();
        DistributorInvoiceNumTool distInvNumTool = new DistributorInvoiceNumTool();
        try {
            IntegrationServices intServ = pFactory.getIntegrationServicesAPI();
            Order orderEjb = pFactory.getOrderAPI();
            PurchaseOrder poEjb = pFactory.getPurchaseOrderAPI();
            for (int i = 0; i < pInvoiceReq.getInvoiceDetailDV().size(); i++) {

                pBaton.setCurrInsInvDetailFlag(Boolean.TRUE);
                pBaton.getItemNotesToLog().clear();
                pBaton.setCurrInoiceDetailData((InvoiceDistDetailData) pInvoiceReq.getInvoiceDetailDV().get(i));

                pBaton.getCurrInoiceDetailData().setInvoiceDistId(invoiceD.getInvoiceDistId());
                pBaton.getCurrInoiceDetailData().setDistItemQtyReceived(pBaton.getCurrInoiceDetailData().getDistItemQuantity());

                // Ignore the record altogether (i.e., don't even insert it)
                // if the quantity is zero.
                if (pBaton.getCurrInoiceDetailData().getDistItemQuantity() == 0) {
                    continue;
                }

                if (pBaton.getCurrInoiceDetailData().getItemReceivedCost() == null ||pBaton.getCurrInoiceDetailData().getDistItemQuantity() == -9999) {
                    String notes = "Missing cost and/or quantity: " +
                                   "cwCost=" + pBaton.getCurrInoiceDetailData().getItemReceivedCost() +
                                   ",quantity=" + pBaton.getCurrInoiceDetailData().getDistItemQuantity();
                    pBaton.getItemNotesToLog().add(notes);
                    pBaton.incExceptionCount();
                }

                // Get the order item record
                OrderItemData itemMatch = null;
                boolean usePoLineNumForInvoiceMatch = false;
            	if (pBaton.getTradingPartnerInfo() != null){
            		usePoLineNumForInvoiceMatch = pBaton.getTradingPartnerInfo().isUsePoLineNumForInvoiceMatch();    					
            	}            	

                try {
                	itemMatch = intServ.matchDistributorInvoiceItem(pBaton.getCurrInoiceDetailData(),
                            pBaton.getItemNotesToLog(),
                            pBaton.getNotesToLog(),
                            pBaton.getPOItems(),
                            pBaton.getOrder(),usePoLineNumForInvoiceMatch);
                } catch (Exception e) {
                    log.info(e.getMessage());
                    pBaton.incExceptionCount();
                }

                int cwSku = pBaton.getCurrInoiceDetailData().getItemSkuNum();
                // line match found
                if (itemMatch != null) {
                    pBaton.getCurrInoiceDetailData().setOrderItemId(itemMatch.getOrderItemId());
                    pBaton.getCurrInoiceDetailData().setErpPoLineNum(itemMatch.getErpPoLineNum());
                    pBaton.getCurrInoiceDetailData().setErpPoRefLineNum(itemMatch.getErpPoRefLineNum());
                    if (cwSku == 0) {
                        cwSku = itemMatch.getItemSkuNum();
                        pBaton.getCurrInoiceDetailData().setItemSkuNum(cwSku);
                    }

                    //find cost cener for this item

                    if (itemMatch.getCostCenterId() > 0) {
                        CostCenterData ccd;
                        String key = String.valueOf(itemMatch.getCostCenterId());
                        if (ccMap.containsKey(key)) {
                            ccd = (CostCenterData) ccMap.get(key);
                        } else {
                            ccd = orderEjb.getCostCenter(itemMatch.getCostCenterId());
                            ccMap.put(key, ccd);
                        }
                        pBaton.getCurrInoiceDetailData().setErpAccountCode(ccd.getShortDesc());
                    }

                    if (pBaton.getTradingPartnerInfo() != null && pBaton.getDistributor() != null) {

                       int excCount =  intServ.setItemAdjustedCost(pBaton.getTradingPartnerInfo().getTradingPartnerData(),
                                pBaton.getCurrInoiceDetailData(),
                                itemMatch, pBaton.getItemNotesToLog(),
                                pBaton.getNotesToLog(),
                                pBaton.getDistributor());
                       pBaton.addExceptionCount(excCount);

                    } else if (pBaton.getDistributor() != null) {

                        int excCount = intServ.setItemAdjustedCost(null, pBaton.getCurrInoiceDetailData(), itemMatch,
                                pBaton.getItemNotesToLog(),
                                pBaton.getNotesToLog(),
                                pBaton.getDistributor());
                        pBaton.addExceptionCount(excCount);
                    }

                    if (itemMatch.getDistUomConvMultiplier() != null && itemMatch.getDistUomConvMultiplier().compareTo(ONE) != 0)
                        {
                                int excCount = uomConversion(orderEjb,poEjb,intServ,
                                                            itemMatch,
                                                            pInvoiceReq.getInvoiceD().getErpPoNum(),
                                                            pBaton);
                                pBaton.addExceptionCount(excCount);

                        }

                        BigDecimal lineItemTotal = new BigDecimal(pBaton.getCurrInoiceDetailData().getDistItemQtyReceived());
                        if (pBaton.getCurrInoiceDetailData().getItemReceivedCost() != null) {
                            lineItemTotal = lineItemTotal.multiply(pBaton.getCurrInoiceDetailData().getItemReceivedCost());
                        } else {
                            lineItemTotal = new BigDecimal(0.00);
                        }
                        recvdTotal = recvdTotal.add(lineItemTotal);
                        pBaton.setRecivedTotal(recvdTotal);
                        // UOM Check
                        // Get the UOM from the distributor invoice.
                        String uom = pBaton.getCurrInoiceDetailData().getDistItemUom();
                        if (!Utility.isSet(uom)) {
                            uom = pBaton.getCurrInoiceDetailData().getItemUom();
                        }
                        // Get the UOM that was sent to them in
                        // the PO (order item entry).
                        String uomMatch = itemMatch.getDistItemUom();

                        if (Utility.isSet(uom) && !Utility.matchUOM(uom, uomMatch)) {
                            String notes;
                            if (pBaton.getTradingPartnerInfo() != null && pBaton.getTradingPartnerInfo().isCheckUOM()) {
                                pBaton.incExceptionCount();
                                notes = "";
                            } else {
                                notes = "Warning: ";
                            }
                            notes = notes + "UOM in invoice (" + uom +
                                    ") doesn't match value" +
                                    " in order item status record (" +
                                    uomMatch + ")";
                            pBaton.getItemNotesToLog().add(notes);
                        }
                        ///////////////////////////////////////////////////////////////
                        addActions(orderEjb,itemMatch,
                                pBaton.getCurrInoiceDetailData(),
                                String.valueOf(cwSku),
                                pBaton.getInvoiceRequest().getInvoiceD(),
                                pBaton.getProcessName());
                        // override the cleanwise item sku and vendor item sku number
                        // with original ordered item information
                        pBaton.getCurrInoiceDetailData().setItemSkuNum(itemMatch.getItemSkuNum());
                        pBaton.getCurrInoiceDetailData().setDistItemSkuNum(itemMatch.getDistItemSkuNum());


                }
                if (pBaton.getCurrInsInvDetailFlag().booleanValue()) {
                    insertedInvoiceItems++;
                    //this a value for the next step (InvoiceRequestEndProcess) of pipeline
                    pBaton.setCountInsertedInvoiceItems(insertedInvoiceItems);
                }
                if (pBaton.getCurrInoiceDetailData().getInvoiceDistDetailId() == 0) {
                    //if the invoice dist id is set in the detail record than use that,
                    //otherwise use the one from the current invoice dist record.
                   /* int invoiceDId = 0;
                    if (pBaton.getCurrInoiceDetailData().getInvoiceDistId() == 0) {
                        invoiceDId = invoiceD.getInvoiceDistId();
                    } else {
                        invoiceDId = pBaton.getCurrInoiceDetailData().getInvoiceDistId();
                    }*/

                    InvoiceDistDetailDescData toInsertItem = InvoiceDistDetailDescData.createValue();
                    toInsertItem.setOrderItem(itemMatch);
                    LinkedList itemNotesToLogCopy = new LinkedList();
                    itemNotesToLogCopy.addAll(pBaton.getItemNotesToLog());
                    toInsertItem.setInvoiceDistDetailNotes(itemNotesToLogCopy);
                    toInsertItem.setInvoiceDistDetail(pBaton.getCurrInoiceDetailData());
                    toInsertItems.add(toInsertItem);
                    pBaton.setToInsertItems(toInsertItems);

                }

            }//end looping through items
            //log("process => toInsertItems  : "+toInsertItems);
            //log("process => insertedInvoices : "+insertedInvoices);
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
            throw new PipelineException(e.getMessage());
        }
        return pBaton;
    }

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton) throws PipelineException, NamingException {
        return process(pBaton,null,APIAccess.getAPIAccess());
    }


    private void addActions(Order orderEjb,OrderItemData itemMatch, InvoiceDistDetailData invDistDetail, String cwSku, InvoiceDistData invoiceD,String processName) throws SQLException, RemoteException {

        Date currentDate = new Date();

        // add the invoiced action
        OrderItemActionData invoicedAction = OrderItemActionData.createValue();
        invoicedAction.setOrderId(itemMatch.getOrderId());
        invoicedAction.setOrderItemId(itemMatch.getOrderItemId());
        invoicedAction.setAffectedSku("" + cwSku);
        invoicedAction.setActionCd(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_INVOICED);
        invoicedAction.setQuantity(invDistDetail.getDistItemQuantity());
        invoicedAction.setActionDate(currentDate);
        invoicedAction.setActionTime(currentDate);
        invoicedAction.setAddBy(processName);
        invoicedAction.setModBy(processName);
        invoicedAction.setAffectedTable(InvoiceDistDataAccess.CLW_INVOICE_DIST);
        invoicedAction.setAffectedId(invoiceD.getInvoiceDistId());
        orderEjb.addOrderItemAction(invoicedAction);

    }

    private int uomConversion(Order orderEjb, PurchaseOrder poEjb,
                              IntegrationServices intServ,
                              OrderItemData item,
                              String erpPoNum,
                              InvoiceRequestPipelineBaton pBaton) throws RemoteException, SQLException {


        int exceptionCount=0;
        //now we know we have a converted UOM, check to see if there is one in the db already
        List results = poEjb.getInvoiceDistDetail(item.getOrderItemId(), erpPoNum);

        Iterator it = results.iterator();
        InvoiceDistData lastInvoiceDistData = null;
        InvoiceDistDataVector currInvoices = new InvoiceDistDataVector();
        InvoiceDistDetailDataVector currInvoiceItems = new InvoiceDistDetailDataVector();
        boolean foundErrInvoice = false;

        while (it.hasNext()) {

            List row = (List) it.next();
            InvoiceDistData curId = (InvoiceDistData) row.get(0);
            InvoiceDistDetailData curIdd = (InvoiceDistDetailData) row.get(1);
            currInvoiceItems.add(curIdd);

            if (lastInvoiceDistData != null && curId.getInvoiceDistId() != lastInvoiceDistData.getInvoiceDistId()) {
                currInvoices.add(curId);
                //multiples found
                if (!foundErrInvoice && !RefCodeNames.INVOICE_STATUS_CD.PENDING.equals(lastInvoiceDistData.getInvoiceStatusCd())) {
                    String notes = "Overshipped dist uom conversion item";
                    pBaton.getItemNotesToLog().add(notes);
                    exceptionCount++;
                    foundErrInvoice = true;
                } else {
                    String notes = "Found mulitple pending invoices for item";
                    pBaton.getItemNotesToLog().add(notes);
                    exceptionCount++;
                    foundErrInvoice = true;
                }
            }
            lastInvoiceDistData = curId;
        }

        if (currInvoiceItems.size() > 1) {
            String notes = "Found mulitple DIST UOM Conv item invoices";
            pBaton.getItemNotesToLog().add(notes);
            exceptionCount++;
            foundErrInvoice = true;
        }
        //update any pending invoices if we have found an overshipment.  This could occur on rare
        //occasions depending on the order recieved
        if (foundErrInvoice) {
            it = currInvoices.iterator();
            while (it.hasNext()) {
                InvoiceDistData id = (InvoiceDistData) it.next();
                if (RefCodeNames.INVOICE_STATUS_CD.PENDING.equals(id.getInvoiceStatusCd())) {
                    id.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
                    poEjb.updateInvoiceDistData(id);
                }
            }
        } else {
            //should have exactly one invoice
            if (lastInvoiceDistData != null && !RefCodeNames.INVOICE_STATUS_CD.PENDING.equals(lastInvoiceDistData.getInvoiceStatusCd())) {
                String notes = "Overshipped dist uom conversion item";
                pBaton.getItemNotesToLog().add(notes);
                exceptionCount++;
            } else {
                InvoiceDistData newDistInvoice;
                if (lastInvoiceDistData == null) {
                    //if we are going about creating a new one, and this item is shipped complete
                    //then just leave it on the present invoice.  Otherwise create a new one
                    if (pBaton.getCurrInoiceDetailData().getDistItemQuantity() < item.getTotalQuantityOrdered()) {
                        //This is the easy case, insert a new header and detail record
                        try {
                            int distId = 0;
                            if (pBaton.getDistributor()!= null) {
                                distId = pBaton.getDistributor().getBusEntity().getBusEntityId();
                            }
                            if (pBaton.getInvoiceRequest().getInvoiceDetailDV().size() == 1) {
                                //if this is the only item on the invoice then just reset the invoice number
                                pBaton.getNotesToLog().add("Partially shipped dist conversion line invoice, ref invoice: " + pBaton.getInvoiceRequest().getInvoiceD().getInvoiceNum());
                                pBaton.getInvoiceRequest().getInvoiceD().setInvoiceNum(poEjb.getNewInvoiceNumForDistInvoiceLike(pBaton.getInvoiceRequest().getInvoiceD().getInvoiceNum(), distId));
                                pBaton.getInvoiceRequest().getInvoiceD().setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING);
                            } else {
                                newDistInvoice = (InvoiceDistData) pBaton.getInvoiceRequest().getInvoiceD().clone();
                                newDistInvoice.setInvoiceNum(poEjb.getNewInvoiceNumForDistInvoiceLike(pBaton.getInvoiceRequest().getInvoiceD().getInvoiceNum(), distId));
                                //zero out frieght and sales tax, we will leave that on the original invoice
                                newDistInvoice.setFreight(ZERO);
                                newDistInvoice.setSalesTax(ZERO);
                                newDistInvoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING);
                                //reset subtotals and the current, and existing invoices
                                BigDecimal thisLineSTot = pBaton.getCurrInoiceDetailData().getItemReceivedCost().multiply(new BigDecimal(pBaton.getCurrInoiceDetailData().getDistItemQtyReceived()));
                                pBaton.getInvoiceRequest().getInvoiceD().setSubTotal(thisLineSTot);
                                List newNotes = new LinkedList();
                                newNotes.add("Partially shipped dist conversion line invoice, ref invoice: " + pBaton.getInvoiceRequest().getInvoiceD().getInvoiceNum());
                                
                                boolean checkDuplInvoiceNum = pBaton.getTradingPartnerInfo() != null ? !pBaton.getTradingPartnerInfo().isRelaxValidateInboundDuplInvoiceNum() : true;
                                newDistInvoice = intServ.insertInvoiceDistData(newDistInvoice, exceptionCount, thisLineSTot, newNotes, pBaton.getInsertedInvoices(), checkDuplInvoiceNum);
                                intServ.insertDistributorInvoiceNotes(newDistInvoice, pBaton.getOrder(), newNotes, new HashMap());
                                pBaton.getInsertedInvoices().add(newDistInvoice);
                                pBaton.getCurrInoiceDetailData().setInvoiceDistId(newDistInvoice.getInvoiceDistId());
                                pBaton.setCurrInsInvDetailFlag(Boolean.FALSE);

                            }
                        } catch (Exception e) {
                            pBaton.getNotesToLog().add(e.getMessage());
                            exceptionCount++;
                        }

                    }
                } else {
                    //we found an an invoice that has this item on it and is waiting to be completed
                    InvoiceDistDetailData currInvoiceItem = (InvoiceDistDetailData) currInvoiceItems.get(0);
                    newDistInvoice = lastInvoiceDistData;
                    InvoiceDistData invoiceD = pBaton.getInvoiceRequest().getInvoiceD();
                    String invoiceNum = invoiceD.getInvoiceNum();
                    int storeId = invoiceD.getStoreId();
                    InvoiceDistDataVector dupIds =
                            intServ.existingInvoiceFound(invoiceNum, erpPoNum, storeId,
                            pBaton.getInsertedInvoices());
                    if (dupIds.isEmpty()) {
                        //invoiceD = the new invoice we may or may not insert, it is the one we are moving this item OFF of
                        //newDistInvoice = the existing invoice in the database which we are moving the item ON TO
                        //currInvoiceItem = the existing invoice item in the database which we are moving the item ON TO
                        //
                        //what we need to do here is reset the subtotals.  Adding to the exisitng, and subtracting from the new.
                        //the adjustedTotal variable will take care of the invoiceD object.  This simply doesn't get incremented

                        List newNotes = new LinkedList();
                        newNotes.add("Partially shipped dist conversion line invoice, ref invoice: " + pBaton.getInvoiceRequest().getInvoiceD().getInvoiceNum());
                        //add qtys for the existing line
                        int recQty = currInvoiceItem.getDistItemQtyReceived() + pBaton.getCurrInoiceDetailData().getDistItemQtyReceived();
                        currInvoiceItem.setDistItemQtyReceived(recQty);
                        //do the UOM conversion
                        exceptionCount += intServ.convertCostAndQty(item.getDistUomConvMultiplier(), currInvoiceItem, pBaton.getItemNotesToLog(), false, false);

                        BigDecimal newSubTotal = currInvoiceItem.getItemReceivedCost().multiply(new BigDecimal(currInvoiceItem.getDistItemQuantity()));
                        newDistInvoice.setSubTotal(newSubTotal);

                        //if this invoice item is now complete then set its status
                        int totalConvertedQtyOrdered = item.getDistUomConvMultiplier().multiply(new BigDecimal(item.getTotalQuantityOrdered())).intValue();
                        if (currInvoiceItem.getDistItemQtyReceived() == totalConvertedQtyOrdered) {
                            newDistInvoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
                        } else if (currInvoiceItem.getDistItemQtyReceived() > totalConvertedQtyOrdered) {
                            newDistInvoice.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
                            pBaton.getItemNotesToLog().add("Overshipped Line");
                        }
                        poEjb.updateInvoiceDistData(newDistInvoice);
                        orderEjb.updateInvoiceDistDetail(currInvoiceItem);
                        pBaton.setCurrInsInvDetailFlag(Boolean.FALSE);
                        //insertedInvoices....
                        Iterator intlit = pBaton.getItemNotesToLog().iterator();
                        while (intlit.hasNext()) {
                            String myNotes = (String) intlit.next();
                            orderEjb.enterOrderProperty(
                                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                                    "Inbound EDI 810 Note", myNotes,
                                    pBaton.getOrder().getOrderId(), item.getOrderItemId(),
                                    newDistInvoice.getInvoiceDistId(),
                                    currInvoiceItem.getInvoiceDistDetailId(),
                                    0, 0, 0, "");
                        }
                        intlit = newNotes.iterator();
                        while (intlit.hasNext()) {
                            String myNotes = (String) intlit.next();
                            orderEjb.enterOrderProperty(
                                    RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE,
                                    "Inbound EDI 810 Note", myNotes,
                                    pBaton.getOrder().getOrderId(), 0,
                                    newDistInvoice.getInvoiceDistId(),
                                    0, 0, 0, 0, "");
                        }
                        pBaton.setCurrInoiceDetailData(currInvoiceItem);
                        pBaton.getItemNotesToLog().clear();
                        //do this if you don't want an extra blank invoice with frieght only
                        //insertedInvoices.add(newDistInvoice);
                    } else {
                        //we could do some fancy duplicate checking here, what is going
                        //to happen by just sort of ignoring it is that it will be set to
                        //pending review even if it is truly a duplicate.  For rev 1 we will
                        //just leave this as is.
                    }
                }
            }
        }   
     return exceptionCount;

    }



}
