package com.cleanwise.service.apps.dataexchange;
import org.apache.log4j.Logger;

/**
*Delegates most of the logic back to the InboundcXMLOrder class but it does anything that is specific to 
*inmar.
*/
public class InboundInmarcXMLOrder extends InboundcXMLOrder{
   private static final Logger  log = Logger.getLogger(InboundInmarcXMLOrder.class);
   /**
   * Sub classes can overide this method in order to provide post-processing of site reference logic that is
   * specific to their implementation.  For example finding a site directly, or any string manipulation.
   * @Returns the post processed site reference number
   */
   protected String processAddressId(String refSiteId){
	   log.info("Analyizing addressid: ["+refSiteId+"]");
	   if(refSiteId == null){
          return refSiteId;
	   }
	   //takes CLS:1800-406:900 and makes it into CLS:900
	   String[] siteParts = refSiteId.split(":");
	   if(siteParts.length == 3){
            refSiteId = siteParts[0]+":"+siteParts[2];
	   }else{
            log.warn("addressid expected to contain 3 parts seperated by a colon ':'...but did not find 3 parts in id: "+refSiteId+" continuing using unparsed addressid");
	   }
	   return refSiteId;
   }
}
