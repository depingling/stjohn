package com.cleanwise.service.apps.dataexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.apache.log4j.Logger;


//import com.cleanwise.service.api.value.OrderGuideData;
//import com.cleanwise.service.api.value.OrderGuideLoadRequestData;
//import com.cleanwise.service.apps.loaders.TabFileParser;
import com.cleanwise.service.apps.loaders.PipeFileParser;
import java.io.InputStream;
import java.util.HashSet;
import java.util.HashMap;
import java.math.BigDecimal;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.ContractItemPriceView;
import com.cleanwise.service.api.value.ContractItemPriceViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.InboundPriceLoaderView;
import com.cleanwise.service.api.value.InboundPriceLoaderViewVector;
import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.framework.ValueObject;

public class InboundNSCSapPricingLoader extends InboundNSCSapLoader {//InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
	ArrayList parsedObjects = new ArrayList();
        int storeNum = 0;

        public void translate(InputStream pIn, String pStreamType) throws Exception {

          PipeFileParser parser = new PipeFileParser();
          parser.parse(pIn);
          parser.cleanUpResult();
          parser.processParsedStrings(this);
          storeNum = getStoreIdFromTradingPartner();
          doPostProcessing();
        }

        protected void processParsedObject(Object pParsedObject) throws Exception {
           if (!(pParsedObject instanceof InboundPriceLoaderView)){
               throw new IllegalArgumentException("Parsed object has a wrong type "+
                       pParsedObject.getClass().getName()+
                       " (should be InboundPriceLoaderView) ");
             }
             parsedObjects.add(pParsedObject);
       }

	protected void doPostProcessing() {
               log.info("[InboundNSCSapPricingLoader].doPostProcessing() => BEGIN. ");
               long startTime = System.currentTimeMillis();
		//loop through
		String lastKey=null;

		ContractItemPriceViewVector pvV = new ContractItemPriceViewVector();
                log.info("[InboundNSCSapPricingLoader].doPostProcessing() =>  :" + parsedObjects.size());
		Iterator it = parsedObjects.iterator();

                int line =0;
                HashSet catalogKeys = new HashSet();
		while(it.hasNext() && !isErrorLimit()){
			InboundPriceLoaderView pricing =(InboundPriceLoaderView) it.next();
                        line++;
                        if (isValid(pricing, line)){
                          ContractItemPriceView pv = createPricingView(pricing);
                          catalogKeys.add(pricing.getCatalogKey());
                          pvV.add(pv);
                        }
                }


 //               if (this.getErrorMsgs() != null && this.getErrorMsgs().size()>0 ){
                if (errorLines.size() > 0){
                  processErrorMsgs();
                  setFail(true);
                  log.error("ERROR(s) :" + this.getFormatedErrorMsgs());

                } else if ( pvV.size() > 0) {
                  try {
                    IntegrationServices isEjb = APIAccess.getAPIAccess().getIntegrationServicesAPI();
                    int total = isEjb.processPricing(pvV, catalogKeys, storeNum);
                    log.info("[InboundNSCSapPricingLoader].doPostProcessing() => END. Processed: " + pvV.size()+" records. ");
                  } catch (Exception ex ){
                    setFail(true);
                    log.info("ERROR :" + Utility.getInitErrorMsg(ex));
                    log.info("[InboundNSCSapPricingLoader].doPostProcessing() => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms" ) ;

                  }
                } else {
                  log.info("[InboundNSCSapPricingLoader].doPostProcessing() => End. NO RECORDS to process!" ) ;
                }

	}


	private ContractItemPriceView createPricingView(InboundPriceLoaderView pricing){
		ContractItemPriceView pv = ContractItemPriceView.createValue();

  //              pv.setStoreId(new Integer(pricing.getStoreId()).intValue());
                pv.setContractName(pricing.getCatalogKey());
                pv.setDistName(pricing.getDistributor());
                pv.setItemUom(pricing.getUom());
                pv.setItemCustSku(pricing.getDistSku());
                pv.setDistCost(new BigDecimal(pricing.getCost()));
                pv.setPrice(new BigDecimal(pricing.getPrice()));
                return pv;
	}

	private boolean isValid(InboundPriceLoaderView pricing, int line){
          boolean valid = true;
            boolean ok = true;

         ok = checkType(pricing.getStoreId(), "Store Id", TYPE.INTEGER, true, line);
         valid &=ok;
         if (ok && Integer.parseInt(pricing.getStoreId()) != storeNum) {
           addError("Incorrect STORE ID value = '" + Integer.parseInt(pricing.getStoreId()) + "'. Trading Partner associated with store Id =" + storeNum +". " , line);
           valid = false;
         }

         valid &= checkRequired(pricing.getCatalogKey(), "Catalog Key", line);

         valid &= checkRequired(pricing.getDistSku(), "Dist SKU", line);
         valid &= checkRequired(pricing.getDistributor(), "Distributor", line);

          valid &= checkRequired(pricing.getUom(), "UOM", line);
          valid &= checkType(pricing.getCost(), "Cost", TYPE.BIG_DECIMAL, true, line);

          valid &= checkType(pricing.getPrice(), "Price", TYPE.BIG_DECIMAL, true, line);

          return valid;
        }

}
