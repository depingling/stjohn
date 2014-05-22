package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.util.ConnectionContainer;

import java.util.Map;
import java.rmi.RemoteException;

/**
 * Picks up all catalog items and adds year to date trade information Adapted
 * from the ReportOrderBean to the new GenericReport Framework
 */
public class TradingPartnerReport implements DomUniversalReport {
    public final static String TRADING_PARTNER_PARAM = "TRADING_PARTNER_ID";

    public ReportItem process(ConnectionContainer pCons,
            GenericReportData pReportData, Map pParams) throws Exception {
        int pTradingPartnerId;
        ReportItem tradingPartnerItem = null;
        try {
            pTradingPartnerId = Integer.parseInt((String) pParams
                    .get(TRADING_PARTNER_PARAM));
        } catch (RuntimeException e) {
            throw new RemoteException(
                    "Could not parse Trading Partner control: "
                            + pParams.get(TRADING_PARTNER_PARAM));
        }
        try {
            APIAccess factory = new APIAccess();
            TradingPartner tradingPartnerAPI = factory.getTradingPartnerAPI();
            tradingPartnerItem = tradingPartnerAPI.getReportItem(pTradingPartnerId);
            return tradingPartnerItem;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
