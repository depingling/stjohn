/**
 * 
 */
package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import org.apache.log4j.Logger;


/**
 * @author ssharma
 *
 */
public class ProcessInboundChunks{

	protected Logger log = Logger.getLogger(this.getClass());
	public void processChunks(IntegrationRequestsVector pReqs,
			Integer pTradingPartnerId, Integer pParentEventId) throws Exception {
		
		log.info("ProcessInboundChunks: Start");
		try{
			IntegrationServices intSvc = APIAccess.getAPIAccess().getIntegrationServicesAPI();
			
			log.info("Number of requests: "+pReqs.size());
			intSvc.processIntegrationRequests(pReqs, null, pTradingPartnerId.intValue());
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		
		log.info("ProcessInboundChunks: End");
		
	}

}
