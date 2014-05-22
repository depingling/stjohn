package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.ProcessOutboundResultViewVector;
import com.cleanwise.service.api.value.ProcessOutboundResultView;
import com.cleanwise.service.api.value.OrderRequestDataVector;

import java.util.Iterator;
import java.io.File;

import org.apache.log4j.Logger;

public class OutboundClwProvider extends OutboundSimpleProvider {

	private static final Logger log = Logger.getLogger(OutboundClwProvider.class);

    public void outboundResultProcess(ProcessOutboundResultViewVector results, Integer currentEventId) throws Exception {
        Iterator it = results.iterator();
        while (it.hasNext()) {
            ProcessOutboundResultView resultView = (ProcessOutboundResultView) it.next();
            if (resultView.getProcessResult() instanceof File) {
                putTransferEvent(resultView, currentEventId);
                processIntegrationRequests(resultView);
            } else if (resultView.getProcessResult() instanceof OrderRequestDataVector) {
                resultView.getIntegrationRequests().addAll((OrderRequestDataVector) resultView.getProcessResult());
                processIntegrationRequests(resultView);
            }
        }
        log.info(getTranslationReport(results));
    }

}
