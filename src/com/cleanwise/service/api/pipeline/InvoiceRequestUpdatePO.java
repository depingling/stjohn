package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.PurchaseOrder;
import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PurchaseOrderData;
import com.cleanwise.service.api.value.PurchaseOrderDataVector;
import com.cleanwise.service.api.value.PurchaseOrderStatusCriteriaData;

import javax.naming.NamingException;
import java.sql.Connection;

import org.apache.log4j.*;

/**
 * Title:        InvoiceRequestUpdatePO
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         23.04.2007
 * Time:         15:50:44
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class InvoiceRequestUpdatePO implements InvoiceRequestPipeline {
    private static final Logger log = Logger.getLogger(InvoiceRequestUpdatePO.class);

    private static final String className = "InvoiceRequestUpdatePO";

    public InvoiceRequestPipelineBaton  process(InvoiceRequestPipelineBaton pBaton, Connection pCon, APIAccess pFactory) throws PipelineException {

        //update the po to be in an acknowledge state if it is not already
        if (!(pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0)) {

            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
            return pBaton;
        }

        try {

            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(PurchaseOrderDataAccess.ERP_PO_NUM, pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum());
            crit.addEqualTo(PurchaseOrderDataAccess.ORDER_ID, pBaton.getInvoiceRequest().getInvoiceD().getOrderId());
            PurchaseOrderDataVector pos = PurchaseOrderDataAccess.select(pCon, crit);
            if (pos.size() == 1) {
                PurchaseOrderData po = (PurchaseOrderData) pos.get(0);
                if (po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT)
                        || po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR)
                        || po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED)) {

                    po.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER);
                    PurchaseOrderDataAccess.update(pCon, po);

                } else if (po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED)) {

                    pBaton.getNotesToLog().add("Error: PO " + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + " was cancelled");
                    log.info("PO " + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + " was cancelled");
                    pBaton.incExceptionCount();
                }
            } else if (pos.size() > 1) {
                pBaton.getNotesToLog().add("Error: Found multiple po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
                log.info("Found multiple po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
            } else {
                pBaton.getNotesToLog().add("Error: Could not find any po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
                log.info("Could not find any po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
            }
             pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
            pBaton.addError(pCon,
                            InvoiceRequestPipelineBaton.UNKNOWN_ERROR,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                            0, 0, e.getMessage());
            throw new PipelineException(e.getMessage());
        } finally {

        }
        return pBaton;
    }

    public InvoiceRequestPipelineBaton  process(InvoiceRequestPipelineBaton pBaton) throws PipelineException, NamingException {
        return process(pBaton, APIAccess.getAPIAccess());
    }

    private InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton, APIAccess apiAccess) throws PipelineException {
          //update the po to be in an acknowledge state if it is not already
        if (!(pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0)) {

            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
            return pBaton;
        }

        try {
            PurchaseOrder poEjb = apiAccess.getPurchaseOrderAPI();
            PurchaseOrderStatusCriteriaData poCrit=new PurchaseOrderStatusCriteriaData();
            poCrit.setErpPONum(pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum());
            IdVector storeIdVector = new IdVector();
            storeIdVector.add(new Integer(pBaton.getInvoiceRequest().getInvoiceD().getStoreId()));
            poCrit.setStoreIdVector(storeIdVector);

            PurchaseOrderDataVector pos = poEjb.getPurchaseOrderCollection(poCrit);
            if (pos.size() == 1) {
                PurchaseOrderData po = (PurchaseOrderData) pos.get(0);
                if (po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.PENDING_FULFILLMENT)
                        || po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR)
                        || po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED)) {

                    po.setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.DIST_ACKD_PURCH_ORDER);
                    poEjb.updatePurchaseOrder(po);

                } else if (po.getPurchaseOrderStatusCd().equals(RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED)) {

                    pBaton.getNotesToLog().add("Error: PO " + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + " was cancelled");
                    log.info("PO " + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + " was cancelled");
                    pBaton.incExceptionCount();
                }
            } else if (pos.size() > 1) {
                pBaton.getNotesToLog().add("Error: Found multiple po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
                log.info("Found multiple po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
            } else {
                pBaton.getNotesToLog().add("Error: Could not find any po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
                log.info("Could not find any po records for this invoice's po (" + pBaton.getInvoiceRequest().getInvoiceD().getErpPoNum() + ")");
            }
             pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
            pBaton.incExceptionCount();
            throw new PipelineException(e.getMessage());
        } finally {

        }
        return pBaton;
    }

}
