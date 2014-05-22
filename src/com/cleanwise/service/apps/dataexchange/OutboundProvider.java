package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;

import java.io.Serializable;
import java.util.HashMap;


public interface OutboundProvider extends Serializable {
    public OutboundEDIRequestDataVector getOrdersToProcess(String erpNum, String setType) throws Exception;
    public HashMap buildOutboundRequest(OutboundEDIRequestDataVector orders, String erpNum, String setType) throws Exception;
    public ProcessOutboundResultViewVector outboundRequestProcess(HashMap outboundRequests) throws Exception ;
    public void processIntegrationRequests(IntegrationRequestsVector reqs, TradingPartnerData partner, String erpNum) throws Exception;
    public void outboundResultProcess(ProcessOutboundResultViewVector results, Integer currentEventId) throws Exception;
}
