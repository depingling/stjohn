package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.dataexchange.OutboundProvider;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Title:        OutboundOperations
 * Description:  Contains outbound operations for process-event-system
 * Purpose:      Execution outbound operations.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         20.08.2007
 * Time:         19:11:41
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class OutboundOperations {
    private static final Logger log = Logger.getLogger(OutboundOperations.class);
    private static String className="OutboundOperations";

    /**
     * this is operation builds map which contains ProcessOutboundRequestView
     * key is combine of TradingProfileConfigId and TradingProfileId and GroupSenderOverride if Override is present
     * @param provider provider class
     * @param orders orders to process
     * @param erpNum distributor erp num
     * @param setType type of transaction
     * @return  map
     * @throws Exception if an errors
     */
    public HashMap buildOutboundRequest(OutboundProvider provider,OutboundEDIRequestDataVector orders,String erpNum,String setType) throws Exception {
        return provider.buildOutboundRequest(orders,erpNum, setType);
    }

    /**
     * procces of outbound request
     * @param provider provider class
     * @param outboundRequests map which contains ProcessOutboundRequestView. Key is combine of TradingProfileConfigId
     * and TradingProfileId and GroupSenderOverride if Override is present
     * @return outbound result collection
     * @throws Exception if an errors
     */
    public ProcessOutboundResultViewVector outboundRequestProcess(OutboundProvider provider,HashMap outboundRequests) throws Exception {
        return provider.outboundRequestProcess(outboundRequests);
    }

    /**
     * outbound final operation
     * @param provider provider class
     * @param results proccesed data
     * @throws Exception if an error
     */
    public void processResults(OutboundProvider provider,ProcessOutboundResultViewVector results, Integer currentEventId) throws Exception {
        provider.outboundResultProcess(results, currentEventId);
    }


    /**
     * Error logging
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e){

        log.info("ERROR in " + className + " :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

        log.info("ERROR in " + className + " :: " + errorMessage);
    }

}
