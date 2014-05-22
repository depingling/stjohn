package com.cleanwise.service.apps.dataexchange;

import java.io.InputStream;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.InvoiceNetworkServiceDataVector;
import com.cleanwise.service.apps.loaders.TabFileParser;

public class InvoiceNetworkServices extends InboundFlatFile {
    final InvoiceNetworkServiceDataVector gotInvoices = new InvoiceNetworkServiceDataVector();

    protected void processParsedObject(Object pParsedObject) throws Exception {
        gotInvoices.add(pParsedObject);
    }

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        TabFileParser parser = new TabFileParser();
        parser.parse(pIn);
        parser.cleanUpResult();
        parser.processParsedStrings(this);
        processGotInoices();
    }

    protected void init() {
        super.init();
        gotInvoices.clear();
    }

    private void processGotInoices() throws Exception {
        int tradingPartnerId = translator.getTradingPartnerDescView()
                .getTradingPartnerData().getTradingPartnerId();
        int[] storeIds = translator.getTradingPartnerBusEntityIds(tradingPartnerId,
                RefCodeNames.TRADING_PARTNER_ASSOC_CD.STORE);
        if(storeIds==null || storeIds.length==0) {
            String errorMess = "Trading partner is not configured to any store. Trading partner id:  "+tradingPartnerId;
            throw new Exception(errorMess);
        }
        if(storeIds.length>1) {
            String errorMess = "Trading partner configured to multiple stores. Trading partner id:  "+tradingPartnerId;
            throw new Exception(errorMess);
        }
        IntegrationServices services = APIAccess.getAPIAccess()
                .getIntegrationServicesAPI();
        services.processInvoicesOfNetworkService(gotInvoices, storeIds[0]);
    }
}
