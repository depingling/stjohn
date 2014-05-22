package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.InvoiceDistData;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import javax.naming.NamingException;
import java.sql.Connection;

/**
 * Title:       InvoiceRequestAddShipFromAddress
 * Description: 
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         23.04.2007
 * Time:         16:13:19
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class InvoiceRequestAddShipFromAddress implements InvoiceRequestPipeline {
    private static String className = "InvoiceRequestAddShipFromAddress";

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton, Connection pCon, APIAccess pFactory) throws PipelineException {
        // Add ship from address to address table

        if (!(pBaton.getPurchaseOrder() != null && pBaton.getPOItems() != null && pBaton.getPOItems().size() > 0)) {
            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
            return pBaton;
        }
        try {

            InvoiceDistData invoiceD = pBaton.getInvoiceRequest().getInvoiceD();

            Distributor distBean = pFactory.getDistributorAPI();
            AddressData address = AddressData.createValue();
            address.setBusEntityId(invoiceD.getBusEntityId());
            address.setName1(invoiceD.getShipFromName());
            address.setAddress1("NA");
            address.setCity(invoiceD.getShipFromCity());
            address.setStateProvinceCd(invoiceD.getShipFromState());
            address.setPostalCode(invoiceD.getShipFromPostalCode());
            address.setCountryCd(RefCodeNames.ADDRESS_COUNTRY_CD.UNITED_STATES);
            address.setAddBy(pBaton.getProcessName());
            address.setModBy(pBaton.getProcessName());

            pBaton.setWhatNext(PipelineBaton.GO_NEXT);
      } catch (Exception e) {
            pBaton.incExceptionCount();
            throw new PipelineException(e.getMessage());
        } finally {

        }
        return pBaton;
    }

    public InvoiceRequestPipelineBaton process(InvoiceRequestPipelineBaton pBaton) throws PipelineException, NamingException {
      return process(pBaton,null,APIAccess.getAPIAccess());
    }

}
