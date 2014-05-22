package com.cleanwise.service.apps.dataexchange;

import java.io.InputStream;
import java.util.HashMap;

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
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.loaders.PipeFileParser;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class InboundPollockSiteDelivery extends InboundFlatFile {
    protected Logger log = Logger.getLogger(this.getClass());
    private static String ACCOUNT_REF_NUM = "Account Ref Num";
    private static String SITE_REF_NUM = "Site Ref Num";
    private static String YEAR = "Year";
    private static String WEEK = "Week";
    private static String DELIVERY_DAY = "Delivery Day";
    private static String CUTOFF_DAY = "Cutoff Day";
    private static String CUTOFF_TIME = "Cutoff Time";


    final SiteDeliveryUploadViewVector parsedData = new SiteDeliveryUploadViewVector();

    protected void processParsedObject(Object pParsedObject) throws Exception {
      if (!(pParsedObject instanceof SiteDeliveryUploadView)){
          throw new IllegalArgumentException("Parsed object has a wrong type "+
                  pParsedObject.getClass().getName()+
                  " (should be SiteDeliveryUploadView) ");
        }
        parsedData.add(pParsedObject);
    }

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        PipeFileParser parser = new PipeFileParser();

        parser.parse(pIn);
        parser.cleanUpResult();
        parser.processParsedStrings(this);

        doPostProcessing();
        doErrorProcessing();
    }

    protected void init() {
        super.init();
        parsedData.clear();
    }

    private void doErrorProcessing() throws Exception {
         Vector errors = getErrorMsgs();
//         log.info("doErrorProcessing errors: errors=" + errors.toString());
         if (errors.size() > 0) {
             log.info("doErrorProcessing errors:" + getFormatedErrorMsgs());
             String errorMessage = "Errors:" + getFormatedErrorMsgs();
             throw new Exception(errorMessage);
         }
   }

    protected void doPostProcessing() throws Exception {
      log.info("->doPostProcessing() : Begin.");

        IntegrationServices services = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        ErrorHolderViewVector errors = new ErrorHolderViewVector();
        try {
           IdVector storeIds = getStoreIds();

           SiteDeliveryUploadViewVector parsedDeliveryDataV = new SiteDeliveryUploadViewVector();
           Map parsedDeliveryDataM = new TreeMap();
           if(isParsedDataValid()){

             for (int i = 0; i < parsedData.size(); i++) {
               SiteDeliveryUploadView item = (SiteDeliveryUploadView)
                   parsedData.get(i);
               String key = item.getAccountRefNum() + "_"
                   + item.getSiteRefNum() + "_"
                   + item.getYear() + "_" + item.getWeek();
               SiteDeliveryUploadView deliveryItem = (SiteDeliveryUploadView)parsedDeliveryDataM.get(key);
               if (deliveryItem == null) {
                 deliveryItem = SiteDeliveryUploadView.createValue();
                 initDeliveryItem(deliveryItem);
                 deliveryItem.setAccountRefNum(item.
                     getAccountRefNum());
                 deliveryItem.setSiteRefNum(item.getSiteRefNum());
                 deliveryItem.setWeek(item.getWeek());
                 deliveryItem.setYear(item.getYear());
                 parsedDeliveryDataM.put(key, deliveryItem);
               }

               int deliveryDay = item.getDeliveryDay();
               populateDeliveryItem(deliveryDay, item.getCutoffDay1(), item.getCutoffSystemTime1(), item.getCutoffSiteTime1(), deliveryItem);

             }
             Set keySet = parsedDeliveryDataM.keySet();
             for (Iterator iter = keySet.iterator(); iter.hasNext(); ) {
               String key = (String) iter.next();
               SiteDeliveryUploadView deliveryItem = (SiteDeliveryUploadView)parsedDeliveryDataM.get(key);
               parsedDeliveryDataV.add(deliveryItem);
             }

//log.info("->doPostProcessing() : parsedDeliveryDataV=" + parsedDeliveryDataV.toString());
             services.processSiteDelivery(parsedDeliveryDataV, errors, storeIds, "Pollock");
             for (int i = 0; errors != null && i < errors.size(); i++) {
                 ErrorHolderView item = (ErrorHolderView) errors.get(i);
                 appendErrorMsgs( item.getMessage());
             }
             log.info("->doPostProcessing() : End. Successfully processed " + parsedData.size() + " rows");
           }
         } catch (Exception e) {
            e.printStackTrace();
            log.error("ERROR:" + e.getMessage());
            throw e;
        }

    }

    private boolean isParsedDataValid(){
      boolean isValid = true;
      for (int i = 0; i < parsedData.size(); i++) {
        SiteDeliveryUploadView item = (SiteDeliveryUploadView) parsedData.get(i);
        String line = "Line # " + (i + 1)  ;

        StringBuffer missingRequiredFlds = new StringBuffer("");
        String def = "";
        if (!Utility.isSet(item.getAccountRefNum())) {
          def = (missingRequiredFlds.length() == 0) ? " " : ", ";
          missingRequiredFlds.append(def + ACCOUNT_REF_NUM);
        }
        if (!Utility.isSet(item.getSiteRefNum())) {
          def = (missingRequiredFlds.length() == 0) ? " " : ", ";
          missingRequiredFlds.append(def + SITE_REF_NUM);
        }
        if (item.getYear() == 0) {
          def = (missingRequiredFlds.length() == 0) ? " " : ", ";
          missingRequiredFlds.append(def + YEAR);
        }
        if (missingRequiredFlds.length() != 0) {
          processRequiredError(i, missingRequiredFlds.toString());
          isValid = false;
        }

        int week = item.getWeek();
        if ((week >= 1 && week <= 53) == false) {
          String s = "Incorrect " +WEEK +" -> " + week + ", must be in range [1-53] for:" + line;
          appendErrorMsgs(s);
          isValid = false;
        }
        int deliveryDay = item.getDeliveryDay();
        if ( (deliveryDay >= 1 && deliveryDay <= 7) == false) {
           String s = "Incorrect " +DELIVERY_DAY +" -> " + deliveryDay + ", must be in range [1-7] for:" + line;
           appendErrorMsgs(s);
           isValid = false;
        }
        int cutoffDay = item.getCutoffDay1();
        if ( (cutoffDay >= 1 && cutoffDay <= 7) == false) {
           String s = "Incorrect " +CUTOFF_DAY +" -> " + cutoffDay + ", must be in range [1-7] for:" + line;
           appendErrorMsgs(s);
           isValid = false;
        }

      }
      return isValid;
    }

    private void initDeliveryItem (SiteDeliveryUploadView deliveryItem){
      deliveryItem.setCutoffDay1(0);
      deliveryItem.setCutoffDay2(0);
      deliveryItem.setCutoffDay3(0);
      deliveryItem.setCutoffDay4(0);
      deliveryItem.setCutoffDay5(0);
      deliveryItem.setCutoffDay6(0);
      deliveryItem.setCutoffDay7(0);

      String initialCutoffSysTime = "13:00";
      String initialCutoffSystemTime = getCutoffSiteTime(null, initialCutoffSysTime);

      deliveryItem.setCutoffSiteTime1(initialCutoffSysTime);
      deliveryItem.setCutoffSiteTime2(initialCutoffSysTime);
      deliveryItem.setCutoffSiteTime3(initialCutoffSysTime);
      deliveryItem.setCutoffSiteTime4(initialCutoffSysTime);
      deliveryItem.setCutoffSiteTime5(initialCutoffSysTime);
      deliveryItem.setCutoffSiteTime6(initialCutoffSysTime);
      deliveryItem.setCutoffSiteTime7(initialCutoffSysTime);

      deliveryItem.setCutoffSystemTime1(initialCutoffSystemTime);
      deliveryItem.setCutoffSystemTime2(initialCutoffSystemTime);
      deliveryItem.setCutoffSystemTime3(initialCutoffSystemTime);
      deliveryItem.setCutoffSystemTime4(initialCutoffSystemTime);
      deliveryItem.setCutoffSystemTime5(initialCutoffSystemTime);
      deliveryItem.setCutoffSystemTime6(initialCutoffSystemTime);
      deliveryItem.setCutoffSystemTime7(initialCutoffSystemTime);
    }
    private void populateDeliveryItem( int deliveryDay, int cutoffDay, String cutoffSysTime, String cutoffSiteTime,  SiteDeliveryUploadView deliveryItem){
      switch (deliveryDay) {
        case 1:
          deliveryItem.setCutoffDay1(cutoffDay);
          deliveryItem.setCutoffSiteTime1(getCutoffSiteTime(cutoffSiteTime, cutoffSysTime));
          deliveryItem.setCutoffSystemTime1(cutoffSysTime);
          deliveryItem.setDeliveryFlag1("Y");
          break;
        case 2:
          deliveryItem.setCutoffDay2(cutoffDay);
          deliveryItem.setCutoffSiteTime2(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime2(cutoffSysTime);
          deliveryItem.setDeliveryFlag2("Y");
          break;
        case 3:
          deliveryItem.setCutoffDay3(cutoffDay);
          deliveryItem.setCutoffSiteTime3(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime3(cutoffSysTime);
          deliveryItem.setDeliveryFlag3("Y");
          break;
        case 4:
          deliveryItem.setCutoffDay4(cutoffDay);
          deliveryItem.setCutoffSiteTime4(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime4(cutoffSysTime);
          deliveryItem.setDeliveryFlag4("Y");
          break;
        case 5:
          deliveryItem.setCutoffDay5(cutoffDay);
          deliveryItem.setCutoffSiteTime5(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime5(cutoffSysTime);
          deliveryItem.setDeliveryFlag5("Y");
          break;
        case 6:
          deliveryItem.setCutoffDay6(cutoffDay);
          deliveryItem.setCutoffSiteTime6(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime6(cutoffSysTime);
          deliveryItem.setDeliveryFlag6("Y");
          break;
        case 7:
          deliveryItem.setCutoffDay7(cutoffDay);
          deliveryItem.setCutoffSiteTime7(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime7(cutoffSysTime);
          deliveryItem.setDeliveryFlag7("Y");
          break;
      }

   }

    private String getCutoffSiteTime(String pCutoffSiteTime, String pCutoffSystemTime) {

        if (Utility.isSet(pCutoffSiteTime)) {
            return pCutoffSiteTime;
        } else { //cutoff site time is not set               
            // Note!!! If TimeZone will be defined for site
            // we can calculate siteTime here
            return pCutoffSystemTime;
        }

    }

   private void processError (int i, SiteDeliveryUploadView item, ErrorHolderViewVector errors){
      String line = "Line # " + (i + 1) + ") CustMaj:"
          + item.getAccountRefNum() + " SiteRefNum:"
          + item.getSiteRefNum() + " Year:"
          + item.getYear() + " Week:"
          + item.getWeek();
      errors.add(new ErrorHolderView("WARNING",
                                "Incorrect delivery day ->"
                                          + item.getDeliveryDay()
                                          + ", must be in range [1-7] for:"
                                          + line));

    }

    private void processRequiredError (int i, String fldNames){
       String line = "Line # " + (i + 1)  ;
       String s = "Missing required field(s) ->" + fldNames + " for: " + line;
       appendErrorMsgs(s);
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
		if (assocMap == null) {
                  throw new Exception ("Trading Partner is not associated with any store");
                }
                return (IdVector) assocMap.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
	}
}
