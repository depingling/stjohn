package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.value.OrderRequestData;

/**
 * Class to make anisochronous request order.
 * 
 */
public class OrderRequest850Task {
    /**
     * Invoke to make order request.
     * 
     * @param pOrderReq
     *            The order request data.
     * @throws Exception
     *             Throws if was any problems.
     */
    public void processOrderRequest(OrderRequestData pOrderReq)
            throws Exception {
        IntegrationServices services = APIAccess.getAPIAccess()
                .getIntegrationServicesAPI();
        services.processOrderRequest(pOrderReq);
    }
}
