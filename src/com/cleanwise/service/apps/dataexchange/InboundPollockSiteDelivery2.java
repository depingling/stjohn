package com.cleanwise.service.apps.dataexchange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.value.ErrorHolderView;
import com.cleanwise.service.api.value.ErrorHolderViewVector;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.SiteDeliveryUploadView;
import com.cleanwise.service.api.value.SiteDeliveryUploadViewVector;
import com.cleanwise.service.api.util.Utility;

import java.util.*;

public class InboundPollockSiteDelivery2 extends InboundFlatFile {
    protected Logger log = Logger.getLogger(this.getClass());
    private static String ACCOUNT_REF_NUM = "Account Ref Num";
    private static String SITE_REF_NUM = "Site Ref Num";
    private static String DELIVERY_DATE = "Delivery Date";
    private static String CUTOFF_DATE = "Cutoff Date";
    private static String CUTOFF_TIME = "Cutoff Time";
    private static String CUTOFF_SYSTEM_TIME = "Cutoff Server Time";
    private static int storeId = 0;
    public interface COLUMN {
        public static final int
                VERSION = 0,
                STORE_ID = 1,
                ACCT_REF_NUM = 3,
                SITE_REF_NUM = 5,
                DELEVERY_DATE = 7,
                CUTOFF_DATE = 8,
                CUTOFF_TIME = 9,
                CUTOFF_SYSTEM_TIME =10;
        
    }
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); 
    final SiteDeliveryUploadViewVector parsedData = new SiteDeliveryUploadViewVector();

    public InboundPollockSiteDelivery2(){
    	super.setSepertorChar('|');
    }
    
    protected void parseDetailLine(List pParsedLine) throws Exception{
    	SiteDeliveryUploadView parsedObj = SiteDeliveryUploadView.createValue();
    	String temp = (String) pParsedLine.get(COLUMN.VERSION);
		if (!Utility.isSet(temp)){
			getErrorMsgs().add("Version # expected on column " + COLUMN.VERSION+1);
		}
		if (Utility.isSet(temp) && !Utility.isEqual("2", temp)){
			getErrorMsgs().add("Wrong version# '" + temp + "'. Expected Version#=2");
		}

		if (storeId == 0){
			temp = (String) pParsedLine.get(COLUMN.STORE_ID);
			if (Utility.isSet(temp)){
				try {
					storeId = -1;
					storeId = Integer.parseInt(temp);
					int storeIdFromTradingPartner = translator.getStoreId();
					if (storeId != storeIdFromTradingPartner){
						getErrorMsgs().add("Store id " + storeId + " is not valid");
					}
				}catch(NumberFormatException e){
					getErrorMsgs().add("NumberFormatException occurred for set store id from '" + temp);
				}
			}else{
				getErrorMsgs().add("NumberFormatException occurred for set store id from '" + temp + "'");
			}
		}		
		
		parsedObj.setAccountRefNum((String) pParsedLine.get(COLUMN.ACCT_REF_NUM));
		parsedObj.setSiteRefNum((String) pParsedLine.get(COLUMN.SITE_REF_NUM));
		temp = (String) pParsedLine.get(COLUMN.DELEVERY_DATE);
		if (Utility.isSet(temp)){
			try{
	            Date deliveryDate = dateFormat.parse(temp);
	            parsedObj.setDeliveryDate1(deliveryDate);
	            GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(deliveryDate);
                parsedObj.setDeliveryDay(cal.get(Calendar.DAY_OF_WEEK));
                int week = cal.get(Calendar.WEEK_OF_YEAR);
                
                // prevent 12/30/2012 saved as week=1 and year=2012 
                if (week ==1 && cal.get(Calendar.MONTH)== 11)
                	week = 53;
                parsedObj.setWeek(week);
                parsedObj.setYear(cal.get(Calendar.YEAR));     
	        }catch(ParseException e){
	            e.printStackTrace();
	            getErrorMsgs().add("could not parse delivery date: " + temp);
	        }
		}
        temp = (String) pParsedLine.get(COLUMN.CUTOFF_DATE);
        if (Utility.isSet(temp)){
			try{
	            Date cutoffDate = dateFormat.parse(temp);
	            parsedObj.setCutoffDate1(cutoffDate);
	            GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(cutoffDate);
                parsedObj.setCutoffDay1(cal.get(Calendar.DAY_OF_WEEK));                
	        }catch(ParseException e){
	            e.printStackTrace();
	            getErrorMsgs().add("could not parse delivery date: " + temp );
	        }
        }
		parsedObj.setCutoffSiteTime1((String) pParsedLine.get(COLUMN.CUTOFF_TIME));
		parsedObj.setCutoffSystemTime1((String) pParsedLine.get(COLUMN.CUTOFF_SYSTEM_TIME));

		if(getErrorMsgs().size() == 0){
	         try{
	            processParsedObject(parsedObj);
	         }catch(Exception ex){
	            ex.printStackTrace();
	            throw new RuntimeException(ex.getMessage());
	         }
	     }
	}

    protected void processParsedObject(Object pParsedObject) throws Exception {      
        parsedData.add(pParsedObject);
    }

    private void doErrorProcessing() throws Exception {
         Vector errors = getErrorMsgs();
         if (errors.size() > 0) {
             log.info("doErrorProcessing errors:" + getFormatedErrorMsgs());
             String errorMessage = "Errors:" + getFormatedErrorMsgs();
             throw new Exception(errorMessage);
         }
   }

    protected void doPostProcessing() throws Exception {
      log.info("->doPostProcessing() : Begin.");

      doErrorProcessing();
        IntegrationServices services = APIAccess.getAPIAccess().getIntegrationServicesAPI();
        ErrorHolderViewVector errors = new ErrorHolderViewVector();
        try {
           IdVector storeIds = new IdVector();
           storeIds.add(storeId);

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
                 deliveryItem.setAccountRefNum(item.getAccountRefNum());
                 deliveryItem.setSiteRefNum(item.getSiteRefNum());
                 deliveryItem.setWeek(item.getWeek());
                 deliveryItem.setYear(item.getYear());
                 parsedDeliveryDataM.put(key, deliveryItem);
               }

               populateDeliveryItem(item.getDeliveryDate1(), item.getCutoffDate1(), item.getDeliveryDay(), item.getCutoffDay1(), item.getCutoffSystemTime1(), item.getCutoffSiteTime1(), deliveryItem);

             }
             Set keySet = parsedDeliveryDataM.keySet();
             for (Iterator iter = keySet.iterator(); iter.hasNext(); ) {
               String key = (String) iter.next();
               SiteDeliveryUploadView deliveryItem = (SiteDeliveryUploadView)parsedDeliveryDataM.get(key);
               parsedDeliveryDataV.add(deliveryItem);
             }

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
        
        if (item.getDeliveryDate1() == null) {
          def = (missingRequiredFlds.length() == 0) ? " " : ", ";
          	missingRequiredFlds.append(def + DELIVERY_DATE);
        }
        
        if (item.getCutoffDate1() == null) {
            def = (missingRequiredFlds.length() == 0) ? " " : ", ";
            	missingRequiredFlds.append(def + CUTOFF_DATE);
        }
        
        if (item.getCutoffSiteTime1() == null) {
            def = (missingRequiredFlds.length() == 0) ? " " : ", ";
            	missingRequiredFlds.append(def + CUTOFF_TIME);
        }
        
        if (item.getCutoffSystemTime1() == null) {
            def = (missingRequiredFlds.length() == 0) ? " " : ", ";
            	missingRequiredFlds.append(def + CUTOFF_SYSTEM_TIME);
          }
        if (missingRequiredFlds.length() != 0) {
          processRequiredError(i, missingRequiredFlds.toString());
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

      String initialCutoffSysTime = "13:00:01";
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
    private void populateDeliveryItem(Date deliveryDate, Date cutoffDate, int deliveryDay, int cutoffDay, String cutoffSysTime, String cutoffSiteTime,  SiteDeliveryUploadView deliveryItem){
      switch (deliveryDay) {
        case 1:
          deliveryItem.setCutoffDay1(cutoffDay);
          deliveryItem.setCutoffSiteTime1(getCutoffSiteTime(cutoffSiteTime, cutoffSysTime));
          deliveryItem.setCutoffSystemTime1(cutoffSysTime);
          deliveryItem.setDeliveryFlag1("Y");
          deliveryItem.setDeliveryDate1(deliveryDate);
          deliveryItem.setCutoffDate1(cutoffDate);
          break;
        case 2:
          deliveryItem.setCutoffDay2(cutoffDay);
          deliveryItem.setCutoffSiteTime2(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime2(cutoffSysTime);
          deliveryItem.setDeliveryFlag2("Y");
          deliveryItem.setDeliveryDate2(deliveryDate);
          deliveryItem.setCutoffDate2(cutoffDate);          
          break;
        case 3:
          deliveryItem.setCutoffDay3(cutoffDay);
          deliveryItem.setCutoffSiteTime3(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime3(cutoffSysTime);
          deliveryItem.setDeliveryFlag3("Y");
          deliveryItem.setDeliveryDate3(deliveryDate);
          deliveryItem.setCutoffDate3(cutoffDate);
          
          break;
        case 4:
          deliveryItem.setCutoffDay4(cutoffDay);
          deliveryItem.setCutoffSiteTime4(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime4(cutoffSysTime);
          deliveryItem.setDeliveryFlag4("Y");
          deliveryItem.setDeliveryDate4(deliveryDate);
          deliveryItem.setCutoffDate4(cutoffDate);          
          break;
        case 5:
          deliveryItem.setCutoffDay5(cutoffDay);
          deliveryItem.setCutoffSiteTime5(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime5(cutoffSysTime);
          deliveryItem.setDeliveryFlag5("Y");
          deliveryItem.setDeliveryDate5(deliveryDate);
          deliveryItem.setCutoffDate5(cutoffDate);
          break;
        case 6:
          deliveryItem.setCutoffDay6(cutoffDay);
          deliveryItem.setCutoffSiteTime6(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime6(cutoffSysTime);
          deliveryItem.setDeliveryFlag6("Y");
          deliveryItem.setDeliveryDate6(deliveryDate);
          deliveryItem.setCutoffDate6(cutoffDate);
          break;
        case 7:
          deliveryItem.setCutoffDay7(cutoffDay);
          deliveryItem.setCutoffSiteTime7(getCutoffSiteTime(cutoffSiteTime,cutoffSysTime));
          deliveryItem.setCutoffSystemTime7(cutoffSysTime);
          deliveryItem.setDeliveryFlag7("Y");
          deliveryItem.setDeliveryDate7(deliveryDate);
          deliveryItem.setCutoffDate7(cutoffDate);
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

    private void processRequiredError (int i, String fldNames){
       String line = "Line # " + (i + 1)  ;
       String s = "Missing required field(s) ->" + fldNames + " for: " + line;
       appendErrorMsgs(s);
    }
}
