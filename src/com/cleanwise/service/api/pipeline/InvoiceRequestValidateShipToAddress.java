package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;

import java.sql.Connection;

/**
 * Title:       InvoiceRequestValidateShipToAddress
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         23.04.2007
 * Time:         15:02:50
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class InvoiceRequestValidateShipToAddress implements InvoiceRequestPipeline {

    private static final String className = "InvoiceRequestValidateShipToAddress";

      public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton) throws Exception {
        return process(pBaton,null,APIAccess.getAPIAccess());
    }

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton, Connection pCon, APIAccess pFactory) throws PipelineException {

        if (!(pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0)) {
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
            return pBaton;
        }

        try {

            Order orderEjb = pFactory.getOrderAPI();
            PurchaseOrderData thePo = pBaton.getPurchaseOrder();
            InvoiceDistData invoiceD = pBaton.getInvoiceRequest().getInvoiceD();

            // validate ship-to address field.
            OrderAddressData shipTo = orderEjb.getOrderAddress(thePo.getOrderId(),RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);
            if (shipTo == null) {
                pBaton.getNotesToLog().add("Error: Unable to validate ship-to address - Order shipping address not found.");
                if (pBaton.getTradingPartnerInfo() != null && pBaton.getTradingPartnerInfo().isCheckAddress()) {
                    pBaton.incExceptionCount();
                }
            } else {
                if ((!Utility.stringMatch(invoiceD.getShipToAddress2(),shipTo.getAddress2(), 0)
                      && !Utility.stringMatch(invoiceD.getShipToAddress3(),shipTo.getAddress3(), 0)
                      && !Utility.stringMatch(invoiceD.getShipToAddress2(),shipTo.getAddress3(), 0)
                      && !Utility.stringMatch(invoiceD.getShipToAddress3(),shipTo.getAddress2(), 0))
                      || !Utility.stringMatch(invoiceD.getShipToCity(), shipTo.getCity(), 0)
                      || !Utility.stringMatch(invoiceD.getShipToState(),shipTo.getStateProvinceCd(), 0)
                      || !Utility.stringMatch(invoiceD.getShipToPostalCode(),shipTo.getPostalCode(), 5)) {

                      String addressNote = "Warning: Mismatched ship-to fields in inbound 810:" +
                                           "\nReceived = " + invoiceD.getShipToAddress2() + ", " +
                                            invoiceD.getShipToAddress3() + ", " +
                                            invoiceD.getShipToCity() + ", " +
                                            invoiceD.getShipToState() + ", " +
                                            invoiceD.getShipToPostalCode() +
                                            "\nDB value = " +
                                            shipTo.getAddress2() + ", " +
                                            shipTo.getAddress3() + ", " +
                                            shipTo.getCity() + ", " +
                                            shipTo.getStateProvinceCd() + ", " +
                                            shipTo.getPostalCode();

                    pBaton.getNotesToLog().add(addressNote);

                    if (pBaton.getTradingPartnerInfo() != null && pBaton.getTradingPartnerInfo().isCheckAddress()) {
                        pBaton.incExceptionCount();
                    }
                }
            }
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
            /* pBaton.addError(pCon,
                            InvoiceRequestPipelineBaton.UNKNOWN_ERROR,
                            RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
                            0, 0, e.getMessage());*/
            throw new PipelineException(e.getMessage());
        } finally {

        }
        return pBaton;
    }


}
