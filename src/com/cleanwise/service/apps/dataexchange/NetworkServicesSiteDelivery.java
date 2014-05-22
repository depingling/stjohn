package com.cleanwise.service.apps.dataexchange;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.ErrorHolderView;
import com.cleanwise.service.api.value.ErrorHolderViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.SiteDeliveryUploadView;
import com.cleanwise.service.api.value.SiteDeliveryUploadViewVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.apps.loaders.TabFileParser;

public class NetworkServicesSiteDelivery extends InboundFlatFile {
    protected Logger log = Logger.getLogger(this.getClass());

    final SiteDeliveryUploadViewVector parsedData = new SiteDeliveryUploadViewVector();

    protected void processParsedObject(Object pParsedObject) throws Exception {
        parsedData.add(pParsedObject);
    }

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        TabFileParser parser = new TabFileParser();
        parser.parse(pIn);
        parser.cleanUpResult();
        parser.processParsedStrings(this);
        
        parser = null;
        System.gc();
        processGotInoices();
    }

    protected void init() {
        super.init();
        parsedData.clear();
    }

    private void processGotInoices() throws Exception {
        IntegrationServices services = APIAccess.getAPIAccess()
                .getIntegrationServicesAPI();
        ErrorHolderViewVector errors = new ErrorHolderViewVector();
        try {
        	IdVector storeIds = getStoreIds();
        	
        	// group data by Customer Majar Num
        	Map<String, SiteDeliveryUploadViewVector> accMajUploadViewMap = new HashMap<String, SiteDeliveryUploadViewVector>();
        	for (int i = 0; i < parsedData.size(); i++){
        		SiteDeliveryUploadView sduView = (SiteDeliveryUploadView) parsedData.get(i);
        		String accMaj = sduView.getCustomerMajorAccount();
        		SiteDeliveryUploadViewVector uploadViewList = accMajUploadViewMap.get(accMaj);
        		if (uploadViewList == null){
        			uploadViewList = new SiteDeliveryUploadViewVector();
        			accMajUploadViewMap.put(accMaj, uploadViewList);
        		}
        		uploadViewList.add(sduView);
        	}
        	
        	// process site delivery grouped by account majar num to reduce the memory usage
        	Iterator iter = accMajUploadViewMap.keySet().iterator();
        	while (iter.hasNext()){
        		String accMaj = (String) iter.next();
        		SiteDeliveryUploadViewVector sduvVector = (SiteDeliveryUploadViewVector) accMajUploadViewMap.get(accMaj);
        		services.processSiteDelivery(sduvVector, errors, storeIds, "Network");
        	}
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ERROR:" + e.getMessage());
            throw e;
        }
        for (int i = 0; errors != null && i < errors.size(); i++) {
            ErrorHolderView item = (ErrorHolderView) errors.get(i);
            if ("ERROR".equals(item.getType())) {
                log.error(item.getType() + ":" + item.getMessage());
            } else {
                log.warn(item.getType() + ":" + item.getMessage());
            }
        }
    }
    
    private IdVector getStoreIds() throws Exception {
		TradingPartner partnerEjb = APIAccess.getAPIAccess()
				.getTradingPartnerAPI();
		TradingPartnerData partner = translator.getPartner();
		if (partner == null) {
			throw new IllegalArgumentException(
					"Trading Partner ID cann't be determined");
		}
		HashMap assocMap = partnerEjb.getMapTradingPartnerAssocIds(partner
				.getTradingPartnerId());
		if (assocMap != null) {
			return (IdVector) assocMap
					.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
		}
		return null;
	}
}
