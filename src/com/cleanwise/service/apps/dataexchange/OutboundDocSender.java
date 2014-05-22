package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.process.operations.FileGenerator;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;


import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

public class OutboundDocSender implements java.io.Serializable {
	protected Logger log = Logger.getLogger(this.getClass());
    public static final String PARAM_ERP_NUM="erpnum";
    public static final String PARAM_OUTPUT_FILE="ofile";
    public static final String PARAM_OUTPUT_DIR="ofile";
    public static final String PARAM_DOC_TYPE="type";
    public static final String[] requiredInputParamNames=new String[]{PARAM_OUTPUT_FILE};


    private ArrayList errorNotes=new ArrayList();
    private static String className="OutboundDocSender";
    private TradingPartner partnerSvc;
    private IntegrationServices intSvc;
    private Order orderEjb;


    public OutboundDocSender() throws Exception {
       log("constructor => begin");
             String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            // Assume that there is a local installation.properties file.
            propFileName = "installation.properties";
        }
        log("constructor => installation.properties : "+propFileName);
        Properties props = new Properties();
        props.load(new FileInputStream(propFileName) );
        log("constructor => props.load : "+props);

        log("constructor =>  InitialContext ");
        InitialContext jndiContext = new InitialContext(props);
        log("constructor =>  jndiContext : "+jndiContext);
        Object ref;
        log("constructor =>  Get a reference to the Trading Partner Bean");
        // Get a reference to the Trading Partner Bean
        ref = jndiContext.lookup(JNDINames.TRADING_PARTNER_EJBHOME);
        // Get a reference from this to the Bean's Home interface
        TradingPartnerHome mTradingPartnerHome = (TradingPartnerHome)
                PortableRemoteObject.narrow(ref, TradingPartnerHome.class);
        // Get trading partner profile
         partnerSvc = mTradingPartnerHome.create();

        log("constructor =>  Get a reference to the Integration Services Bean");
        // Get a reference to the Integration Services Bean
        ref = jndiContext.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
        // Get a reference from this to the Bean's Home interface
        IntegrationServicesHome mIntegrationServicesHome = (IntegrationServicesHome)
                PortableRemoteObject.narrow(ref, IntegrationServicesHome.class);
        intSvc = mIntegrationServicesHome.create();
        log("constructor =>  Get a reference to the Order Bean");
        // Get a reference to the Order Bean
        ref = jndiContext.lookup(JNDINames.ORDER_EJBHOME);
        OrderHome mOrderHome = (OrderHome) PortableRemoteObject.narrow(ref, OrderHome.class);
        orderEjb = mOrderHome.create();

        log("constructor =>  end");

    }

    public static void main(String arg[]) throws Exception {

        OutboundDocSender sender = new OutboundDocSender();
        try {
        	Properties inputParams = System.getProperties();
            File file = sender.createEmailfor(inputParams);
           // sender.sendEmail(file);
        }
        catch (Exception e) {
            sender.processException(e);
        } finally {
            if (sender.getErrorNotes().size() > 0) {
                sender.printErrors(sender.getErrorNotes());

            }
        }
    }

    private void processException(Exception e) {
        error(e.getMessage(),e);
    }

    private void printErrors(ArrayList errorNotes) {
        log(errorNotes.toString());
    }

    private ArrayList getErrorNotes() {
        return this.errorNotes;
    }

    private File createEmailfor(Properties props) throws Exception {
        log("createEmailfor => begin");
        log("cretateEamilfor => convert proprties to map");
        HashMap paramsMap = convertToMap(props, requiredInputParamNames);
        log("createEmailfor => map : "+paramsMap);
        File file = null;
        if (!isFailed(checkInputParams(paramsMap,requiredInputParamNames,requiredInputParamNames))) {
            String docType = (String) paramsMap.get(PARAM_DOC_TYPE);
            HashMap requestMap = buildRequestMap(paramsMap);
            log("createEmailfor =>request map : "+requestMap);
            Iterator it=requestMap.keySet().iterator();
            {
             String key= (String) it.next();
             BuilderRequest request= (BuilderRequest) requestMap.get(key);
             log("createEmailfor => key : "+key+ "request : "+request);
             Class c = Class.forName("com.cleanwise.service.apps.email.DocBuilderKBaseDocument50029");
             com.cleanwise.service.api.process.operations.FileGenerator generator = (FileGenerator) c.newInstance();
             log("createEmailfor => builder : "+generator);
             file=new File(generator.generate(request,(String)props.getProperty(PARAM_OUTPUT_FILE)));
             request.setFile(file);
            }

      }
        return file;
    }

    private HashMap buildRequestMap(HashMap paramsMap) throws Exception {

      HashMap builderRequestsMap = new HashMap();
      BuilderRequest testBuilderReq=new BuilderRequest(TradingProfileConfigData.createValue() ,
              TradingProfileData.createValue(),TradingPartnerAssocData.createValue(),TradingPartnerData.createValue(),0);
      builderRequestsMap.put("test",testBuilderReq);
      return builderRequestsMap;
    }


  /**
   * Builds the overides hashmap which contrains group sender and potentially other overidden trading profile data.
   * @param overides the hashmap to add data to
   * @param pTradingPartner the trading partner id that is currently being translated
   */
  private void buildOverideHashMap(HashMap overides,TradingPartnerAssocDataVector assocList,int pTradingPartner) throws
    RemoteException {
     // get em for this tpid
    Iterator it = assocList.iterator();
    while (it.hasNext()) {
      TradingPartnerAssocData tpa = (TradingPartnerAssocData) it.next();
      if (Utility.isSet(tpa.getGroupSenderOverride())) {
        overides.put(new Integer(tpa.getBusEntityId()), tpa);
      }
    }
  }

    private boolean isFailed(boolean b) {
        return !b;
    }

    private boolean checkInputParams(HashMap params, String[] paramNameList, String[] requriedParamNameList) {
        boolean success = true;
        boolean[] controlFlags =
                new boolean[requriedParamNameList != null ? requriedParamNameList.length : 0];

        if (paramNameList != null) {
            for (int i = 0; i < paramNameList.length; i++) {
                String value = (String) params.get(paramNameList[i]);
                if (!Utility.isSet(value)) {
                    String mess = "Param " + paramNameList[i] + " is not set.";
                    errorNotes.add(mess);
                    if (success) success = false;
                } else {
                    if (requriedParamNameList != null) {
                        for (int j = 0; j < requriedParamNameList.length; j++) {
                        if (requriedParamNameList[j].equals(paramNameList[i])) {
                                controlFlags[i] = true;
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            Set keys = params.keySet();
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = (String) params.get(key);
                if (!Utility.isSet(value)) {
                    String mess = "Param " + key + " is not set.";
                    errorNotes.add(mess);
                    if (success) success = false;
                } else {
                    if (requriedParamNameList != null) {
                        for (int i = 0; i < requriedParamNameList.length; i++) {
                            if (requriedParamNameList.equals(key)) {
                                controlFlags[i] = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < controlFlags.length; i++) {
            if (!controlFlags[i]) {
                String mess = paramNameList[i] + " is required  param.";
                errorNotes.add(mess);
                if (success) success = false;
            }
        }
        return success;
    }

    private HashMap convertToMap(Properties props) {
       return convertToMap(props,null);
    }

    private HashMap convertToMap(Properties props, String[] paramNameList) {
        HashMap resultMap = new HashMap();
        if (paramNameList != null) {
            for (int i = 0; i < paramNameList.length; i++) {
                String property = props.getProperty(paramNameList[i]);
                if (property != null) {
                    resultMap.put(paramNameList[i], property);
                }
            }
        } else {
            Set keys = props.keySet();
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                resultMap.put(key, props.getProperty(key));
            }
        }
        return resultMap;
    }


    public static class BuilderRequest {
        private TradingProfileConfigData profileConfigD;
        private TradingProfileData profileD;
        private TradingPartnerData partnerD;
        private TradingPartnerAssocData profileOveride;
        private int incommingProfileId;
        private OutboundEmailRequestDataVector transactionsToProcess;
        private Object key;
        private File file;

        private BuilderRequest(TradingProfileConfigData pProfileConfigD,
                               TradingProfileData pProfileD,
                               TradingPartnerAssocData pProfileOveride,TradingPartnerData pPartner,
                               int pIncommingProfileId) {
            profileConfigD = pProfileConfigD;
            profileD = pProfileD;
            partnerD=pPartner;
            profileOveride = pProfileOveride;
            incommingProfileId = pIncommingProfileId;
            //+"@"+Integer.toString(incommingProfileId) intentionally left off as this may be 0 and 1 in ceratin cases.
            //the pProfileConfigD.getTradingProfileConfigId() should account for this case already as they are tied to
            //a given trading profile, and the EJB logic should handle getting this when there is not incomming trading
            //profile.  If included USPS will generate 2 files instead of one, one with the web orders (incomming trading profile id 0)
            //and 1 for the EDI (incomming trading profile id 1)
            key = Integer.toString(pProfileConfigD.getTradingProfileConfigId()) +
                   "@" + Integer.toString(profileD.getTradingProfileId()) + "@"
                    +  profileOveride != null?profileOveride.getGroupSenderOverride():"";
        }


        private Object getKey() {
            return key;
        }

        public TradingPartnerAssocData getProfileOveride() {
            return profileOveride;
        }

        public void setProfileOveride(TradingPartnerAssocData profileOveride) {
            this.profileOveride = profileOveride;
        }

        public TradingProfileConfigData getProfileConfigD() {
            return profileConfigD;
        }

        public void setProfileConfigD(TradingProfileConfigData profileConfigD) {
            this.profileConfigD = profileConfigD;
        }

        public TradingProfileData getProfileD() {
            return profileD;
        }

        public void setProfileD(TradingProfileData profileD) {
            this.profileD = profileD;
        }

        public int getIncommingProfileId() {
            return incommingProfileId;
        }

        public void setIncommingProfileId(int incommingProfileId) {
            this.incommingProfileId = incommingProfileId;
        }

        public OutboundEmailRequestDataVector getTransactionsToProcess() {
            return transactionsToProcess;
        }

        public void setTransactionsToProcess(OutboundEmailRequestDataVector transactionsToProcess) {
            this.transactionsToProcess = transactionsToProcess;
        }

        public TradingPartnerData getPartnerD() {
            return partnerD;
        }

        public void setPartnerD(TradingPartnerData partnerD) {
            this.partnerD = partnerD;
        }

        public File getFile() {
              return file;
        }


        public void setFile(File file) {
            this.file = file;
        }
    }

      /**
     * Logging
     * @param message - message which will be pasted to log
     */
    private void log(String message){
        log.info(message);
    }

    /**
     * Error logging
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private  void error(String message, Exception e){

    	log.error(message,e);
        
    }
}



