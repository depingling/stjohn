package com.cleanwise.service.apps.dataexchange;

import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.DistributorHome;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.IntegrationServicesHome;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.PropertyServiceHome;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.StoreHome;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.JNDINames;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.TradingPartnerInfo;
import com.cleanwise.service.apps.quartz.EventJobImpl;
import com.cleanwise.view.utils.fax.FaxConfig;
import com.cleanwise.view.utils.fax.FaxRequest;

/**
 * Title:        OutboundPurchaseOrderFaxJob
 * Copyright:    Copyright (c) 2009
 * Company:      Cleanwise, Inc.
 * @author       Sergei V. Cher
 *
 */
public class OutboundPurchaseOrderMyFaxJob extends EventJobImpl {

    private static final Logger log = Logger.getLogger(OutboundPurchaseOrderMyFaxJob.class);


    private static final String P_XMLFILENAME   = "xmlfilename";
    String envVarValue; // value of the environment variable

    /*****************************************************************/ 
    //maximum number of invoices that will be sent out per FAX request
    private static int POS_PER_FAX = 10;


    static IntegrationServicesHome mIntegrationServicesHome;
    IntegrationServices intSvc;
    static PropertyServiceHome mPropertyServiceHome;
    PropertyService prpSvc;
    static StoreHome mStoreHome;
    Store strRef;
    static DistributorHome mDistributorHome;
    Distributor distRef;

    HashMap storeHM = new HashMap();
    
    //FaxConfigMyFax1 config;
    
    /******************************************************************/
    
    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public OutboundPurchaseOrderMyFaxJob() {
    }

    public void execute(JobDetail jobDetail) throws JobExecutionException {
        try {

            generateFaxes(jobDetail);
            //process(jobDetail);

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    public void generateFaxes(JobDetail jobDetail) throws Exception {

        //JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI();


        String show = "";
        String jobName = jobDetail.getFullName();
        String trName = jobDetail.getFullName();

        show += "\n****************************************************************************\n";
        show += "OutboundPurchaseOrderFaxJob: " + jobName + "(" + trName + ") exec at " + new java.util.Date() + "\n";

        JobDataMap dataMap = jobDetail.getJobDataMap();

        Map<String, String> propertyMap = getPropertyMap(dataMap);
        String pXmlfilename = dataMap.getString(P_XMLFILENAME);

        show += "properties:" + propertyMap + "\n";
        show += "****************************************************************************\n";

        log.info(show);
        
        if (pXmlfilename.equals(null) || pXmlfilename.equals("")) {
  	      log.fatal ("Usage: OutboundPurchaseOrderNew [Path to XML File]");
	      log.fatal ("Path to XML file *must* be passed as a parameter in JobDataMap object.");
	      return;
        }
        //OutboundPurchaseOrderNew out=new OutboundPurchaseOrderNew();
        
        try {
            //out.setUp();
            setUp();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        //out.generateOutboundFaxs(sourcedoc);
        generateOutboundFaxs(pXmlfilename);
    }


    //maximum number of invoices that will be sent out per fax request
    //private static int POS_PER_FAX = 10;


    //static IntegrationServicesHome mIntegrationServicesHome;
    //IntegrationServices intSvc;
    //static PropertyServiceHome mPropertyServiceHome;
    //PropertyService prpSvc;
    //static StoreHome mStoreHome;
    //Store strRef;
    //static DistributorHome mDistributorHome;
    //Distributor distRef;

    //HashMap storeHM = new HashMap();

    /**
     *Sets up the class as an EJB Client.
     */
    
    //JanPakInvoiceLoader loader = APIAccess.getAPIAccess().getJanPakInvoiceLoaderAPI(); // SVC: ???
    
    private void setUp() throws Exception { //SVC: this method was not changed in the new version of this class
        // Check for a properties file command option.
    	/*** SVC: new code
    	Properties props = System.getProperties(); 
    	props.put("conf", "XSUITE_HOME");
    	System.setProperties(props);
        ***/

    	/*** SVC: old code
        String propFileName = System.getProperty("conf");
        if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
        ***/
    	
    	/*** SVC: new code => get the "properties" file name ***/
    	String envVarName = "XSUITE_HOME";
    	envVarValue = System.getenv(envVarName);
    	String propFileName = envVarValue + "/xsuite/app.properties"; // commented temporary 2/13/2009
    	//String propFileName = "C:\\Documents and Settings\\scher\\workspace\\stjohn\\app.properties"; //added temporary 2/13/2009 for Win platform
    	if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
    	/********************************************/
        
        Properties props = new Properties();
        java.io.File f = new java.io.File(propFileName);
        props.load(new FileInputStream(propFileName) );
        InitialContext jndiContext = new InitialContext(props);
        Object ref;

        // Get a reference to the Integration Services Bean
        ref = jndiContext.lookup(JNDINames.INTEGRATION_SERVICES_EJBHOME);
        mIntegrationServicesHome = (IntegrationServicesHome)
        PortableRemoteObject.narrow(ref, IntegrationServicesHome.class);
        intSvc = mIntegrationServicesHome.create();

        // setup property Services Bean
        ref = jndiContext.lookup(JNDINames.PROPERTY_SERVICE_EJBHOME);
        mPropertyServiceHome = (PropertyServiceHome)
        PortableRemoteObject.narrow(ref, PropertyServiceHome.class);
        prpSvc = mPropertyServiceHome.create();

        //setup reference to the store bean
        ref = jndiContext.lookup(JNDINames.STORE_EJBHOME);
        mStoreHome = (StoreHome)
        PortableRemoteObject.narrow(ref, StoreHome.class);
        strRef = mStoreHome.create();

        //setup reference to the distributor bean
        ref = jndiContext.lookup(JNDINames.DISTRIBUTOR_EJBHOME);
        mDistributorHome = (DistributorHome)
        PortableRemoteObject.narrow(ref, DistributorHome.class);
        distRef = mDistributorHome.create();
    }

    /**
     *Takes the source List, and removes all items after the splitPoint and puts those entries into the moveToList
     */
    private void splitList(List srcList,List moveToList, int splitPoint){
        while (srcList.size() > splitPoint){
            moveToList.add(srcList.get(splitPoint));
            srcList.remove(splitPoint);
        }
    }



    /**
     *Does the main work of looping through all the distributors, finding all of the relevant POs,
     *and sending them
     */
    private void generateOutboundFaxs(String xmlDoc){
        FaxConfig config; // commented by SVC 2/13/3009 for testing purposes
        //StoreData store; // SVC: commented by Brook Stevens
        //First loop through and get everything into one list.  If we fail in here then we can bail
        //without having to determine what has been half FAXed.  Failures during retrieval will not be
        //reported back to the system, the states will remain unchanged, and the FAXes will not be sent.
        //failures during FAXing will be reported to the database.
        List masterList = new ArrayList();
        ArrayList erpNumServed = new ArrayList();
        try{
            //create and populate the FaxConfiguration object, this object contains the global
            //configuration information for this FAX session
            String serverUrl;
            String coverPage;
            String docRoot = System.getProperty("webdeploy") + "/";

            try{
                serverUrl = prpSvc.getProperty("FAX.ServerUrl");
                
                coverPage = prpSvc.getProperty("FAX.CoverPage");
                
            }catch(DataNotFoundException e){
                log.info("Could not find property: FAX.ServerUrl.  This property *must* be "+
                        "configured to continue.  Exiting without faxing or making changes to the database.");
                e.printStackTrace();
                return;
            }
            config = new FaxConfig(serverUrl);

            config.setCoverPage(coverPage);

            config.setBaseDirectoryPath(docRoot);

            config.setDebug(false); 
            
            List tpiList = intSvc.getOutboundBusEntityByTradingType(
                    RefCodeNames.TRADING_TYPE_CD.FAX,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            
            if (tpiList == null || tpiList.size() == 0){
                return;
            }
            
            //ArrayList erpNumServed = new ArrayList();
            for (int i=0,len=tpiList.size();i<len;i++){
                TradingPartnerInfo tpi = (TradingPartnerInfo) tpiList.get(i);
                DistributorData dist;
                BusEntityDataVector tpBeDV = tpi.getBusEntities();
                for(int jj=0; jj<tpBeDV.size(); jj++) {
                    BusEntityData tpBeD = (BusEntityData) tpBeDV.get(jj);
                    int busEntId = tpBeD.getBusEntityId();
                    try{
                        //try to locate the distributor for this trading partner
                        dist = distRef.getDistributor(busEntId);
                    }catch(DataNotFoundException e){
                        log.info("Could not find distributor associated with trading "+
                                "partner: " + tpi.getTradingPartnerData().getTradingPartnerId() +
                                "claims to be associated with bus entity id: " + busEntId);
                        e.printStackTrace();
                        return;
                    }
                    String distErpNumber;
                    if(dist.getBusEntity().getErpNum()!=null && dist.getBusEntity().getErpNum().trim().length() > 0){
                        distErpNumber = dist.getBusEntity().getErpNum().trim();
                    }else{
                        log.info("Error: found distributor with no erp number, distId="+dist.getBusEntity().getBusEntityId());
                        continue;
                    }
                    if(erpNumServed.contains(distErpNumber)){
                        continue; //To avoid double sendings if database consistency is broken
                    }
                    erpNumServed.add(distErpNumber);
                    OutboundEDIRequestDataVector pos = intSvc.getEDIOrdersByErpNumAndSetType(distErpNumber,"850",0);
                    if(pos == null){
                    	pos = new OutboundEDIRequestDataVector();
                    }
                    //add it to the master list to process if there are some pos for this distributor
                    if (pos.size() > 0){
                        //masterList.add(new OutboundPurchaseOrderNew.DistributorPosList(dist,pos,tpi)); //commented by SVC 2/11/2009
                        masterList.add(new DistributorPosList(dist,pos,tpi)); //added by SVC 2/11/2009
                    }
                }
            }
        }catch(RemoteException e){
            log.info("Unexpected RemoteException. "+
                    "Exiting without faxing or making changes to the database.");
            e.printStackTrace();
            return;
        }
        
        //System.exit (1); // added 2/13/2009 temporary for testing only
        		
        //break up the master list if any of the trading partners
        //have large number of pos (> posPerFax). These can both
        //break the RightFax API and more importantly would more
        //prone to being interrupted during the FAX transmission
        
        ///*** SVC: commented temporary 2/13/2009
 
        for (int i=0;i<masterList.size();i++){
            DistributorPosList disPo = (DistributorPosList) masterList.get(i);
            OutboundEDIRequestDataVector newPos = new OutboundEDIRequestDataVector();

            //first break out the list into the multiples
            //required for different sets of cover texts
            Collections.sort(disPo.getPos(),EDI_REQUEST_FAX_CONFIRM_COMP);
            Iterator disPoIt = disPo.getPos().iterator();
            String lastConfrimText = null;
            for (int j=0,len=disPo.getPos().size();j<len;j++){
                OutboundEDIRequestData req = (OutboundEDIRequestData) disPo.getPos().get(j);
                String confirmText = getFaxConfirmText(req);
                //don't split out the first one
                if(lastConfrimText == null){
                    lastConfrimText = confirmText;
                }
                if(!confirmText.equals(lastConfrimText)){
                    lastConfrimText = confirmText;
                    splitList(disPo.getPos(),newPos,j); //j not j++ as we want to split it before the one we are currently looping on
                    DistributorPosList newDisPo = new DistributorPosList(
                            disPo.getDistributorData(),
                            newPos,
                            disPo.getTradingPartnerInfo());
                    masterList.add(newDisPo);

                    newPos = new OutboundEDIRequestDataVector();
                    break;
                }
            }
           

            //cut the current version version down to under posPerFax (10) pos, and add
            //the remainder to the end of the master list, where we will process
            //it on a future iteration
            splitList(disPo.getPos(),newPos,POS_PER_FAX);
            if (newPos.size() > 0){
                DistributorPosList newDisPo = new DistributorPosList(
                        disPo.getDistributorData(),
                        newPos,
                        disPo.getTradingPartnerInfo());
                masterList.add(newDisPo);
            }
        }
        
        //SVC: end of temporary comment: 2/13/2009 ***/
        
        /*** SVC: temporary comment 2/13/2009 ***/
        
        //this is the List of items that will be sent back to be updated
        IntegrationRequestsVector irv = new IntegrationRequestsVector();

        //create the FaxAdapter object
        //FaxAdaptor adaptor = new FaxAdaptor(config); // commented by SVC - I don't need it
        //now loop through and FAX the retrieved documents
        StoreData  storeD = null;
        for (int i=0,len=masterList.size();i<len;i++){
            boolean success = false;
            DistributorPosList distPoList = (DistributorPosList) masterList.get(i);
            DistributorData dist = distPoList.getDistributorData();
            TradingPartnerInfo tpi = distPoList.getTradingPartnerInfo();
            
            //FaxTranslatorMyFax translatormyfax = new FaxTranslatorMyFax(config); // create FaxTranslatorMyFax object
            
            FaxRequest req = new FaxRequest(tpi.getPurchaseOrderFaxNumber().getPhoneNum(),tpi.getToContactName(),dist.getBusEntity().getShortDesc());
            
            //FaxCreatorMyFax faxcreatormyfax = new FaxCreatorMyFax(xmlDoc); //new class: SVC
            ArrayList reqs = new ArrayList();
            reqs.add(req);

            //loop through all the positions and extract some info. from them
            StringBuffer coverNotes = new StringBuffer();
            String lastFaxConfirmText = null;

            for (int j=0,len2=distPoList.getPos().size();j<len2;j++){
                OutboundEDIRequestData po = (OutboundEDIRequestData) distPoList.getPos().get(j);
                int storeId = po.getPurchaseOrderD().getStoreId();
                if(storeD==null || storeD.getBusEntity().getBusEntityId()!=storeId){
                    storeD = getStoreData(storeId);
                }
                String faxConfirmText = getFaxConfirmText(po);
                if(!faxConfirmText.equals(lastFaxConfirmText)){
                    lastFaxConfirmText = faxConfirmText;
                    coverNotes.append(faxConfirmText);
                    coverNotes.append("\n");
                    coverNotes.append("POs in this fax: ");
                }
                // we can assume that all the items are on one PO, this is checked
                // when the FAX constructs the PO.
                if (po.getOrderItemDV().size() > 0){
                    OrderItemData itm = (OrderItemData) po.getOrderItemDV().get(0);
                    coverNotes.append(itm.getOutboundPoNum());
                }else{
                    log.info("Error Panic: order has no items!");
                }
                //make sure we are not appending an extra comma.
                if (j + 1 < len2){
                    coverNotes.append(",");
                }
            }

            // end of temporary comment ***/ 
        
            /*** commented temporary for testing purposes only !!! ***/
            try {
                //FaxDocument doc = translator.constructOutboundPurchaseOrder(dist,distPoList.getPos(),storeD,tpi.getFromContactName());
                //FaxTranslatorMyFax translatormyfax = new FaxTranslatorMyFax();
                // SVC: Below => String encodedDocBody = body of the FAX
                //String encodedDocBody = translatormyfax.constructOutboundPurchaseOrder(dist,distPoList.getPos(),storeD,tpi.getFromContactName());

                // create FAX, which will be sent to the customer
                // new class FaxCreatorMyFax: SVC                
                //FaxCreatorMyFax faxcreatormyfax = new FaxCreatorMyFax(); 
                // Possible solution(I decided that it is not necessary): Using Java Bean "faxSettings", set the values of the : fax_recepient_number
                //                                                      : fax_recipient_name
                //                                                      : document => encoded document body (String encodedDocBody) in PDF format
                //faxcreatormyfax.createAndSendSingelFax(xmlDoc
                //		                        ,tpi.getPurchaseOrderFaxNumber().getPhoneNum()
                //		                        ,tpi.getToContactName()
                //		                        ,encodedDocBody); // xmlDoc is initially passed to 
                                                         // method generateOutboundFaxs()
                                                         // as a parameter
                //doc.setCoverPageNote(coverNotes.toString());
                
                //adaptor.sendFax(doc,reqs);
                
                //if (doc.isSuccess() == Boolean.TRUE){
                //    success = true;
                //}
                

            //}catch(FaxException e){
            //    e.printStackTrace();            
            //}catch(IOException e){
            //    e.printStackTrace();           
            }catch(Exception e){
                e.printStackTrace();
            }

                        

        /*** end of temporary comment 2/13/2009 ***/ 
            
      }  // end of the loop: "for (int i=0,len=masterList.size();i<len;i++) {" 
        
    } // end of method generateOutboundFaxs()

    /**
     *Returns the store that matches the store id passed in. Maintains a hashmap of the stores that it has already looked
     *for performance
     */
    private StoreData getStoreData(int pStoreId){
        StoreData storeD = null;
        Integer storeIdI = new Integer(pStoreId);
        if(storeHM.containsKey(storeIdI)) {
            storeD = (StoreData) storeHM.get(storeIdI);
        } else {
            try {
                storeD = strRef.getStore(pStoreId);
            } catch(Exception exc) {
                log.info("Could not get store data. Store id="+storeIdI);
                exc.printStackTrace();
                return null;
            }
            storeHM.put(storeIdI,storeD);
        }
        return storeD;
    }

    /**
     * Command line entry point for program.
     * @param args the command line arguments
     */
    /***
    public static void main(String args[]) {
    	
 	    if (args.length != 1)
	    {
	      System.err.println ("Usage: OutboundPurchaseOrderNew [Path to XML File]");
	      System.err.println ("Path to XML file *must* be passed as a parameter to this class.");
	      System.exit (1);
	    }
 	    String sourcedoc = args[0];
 	    
 	    
        OutboundPurchaseOrderNew out=new OutboundPurchaseOrderNew();
 
        try {
            out.setUp();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        out.generateOutboundFaxs(sourcedoc);
    }
    ***/
    
    //private inner class to hold the pos to distributor relationship
    private class DistributorPosList {
        DistributorPosList(DistributorData pDistributorData,
                OutboundEDIRequestDataVector pPos,
                TradingPartnerInfo pTradingPartnerInfo){
            this.distributorData = pDistributorData;
            this.pos = pPos;
            this.tradingPartnerInfo = pTradingPartnerInfo;
        }
        private DistributorData distributorData;

        private OutboundEDIRequestDataVector pos;

        private TradingPartnerInfo tradingPartnerInfo;

        private DistributorData getDistributorData() {
            return this.distributorData;
        }

        private OutboundEDIRequestDataVector getPos() {
            return this.pos;
        }

        private TradingPartnerInfo getTradingPartnerInfo(){
            return this.tradingPartnerInfo;
        }
    }

    //returns the text that should be used to confirm this purchase order rquest
    private String getFaxConfirmText(OutboundEDIRequestData req){
        StoreData storeD = getStoreData(req.getOrderD().getStoreId());
        StringBuffer txt = new StringBuffer("Please confirm receipt to: ");
        if(req.getFaxBackConfirmNumber() == null || !Utility.isSet(req.getFaxBackConfirmNumber().getPhoneNum())){
            if(storeD == null || storeD.getPrimaryFax() == null || !Utility.isSet(storeD.getPrimaryFax().getPhoneNum())){
                return "";
            }else{
                txt.append(storeD.getPrimaryFax().getPhoneNum());
            }
        }else{
            txt.append(req.getFaxBackConfirmNumber().getPhoneNum());
        }
        return txt.toString();
    }

    //private inner class to hold the updates we will send back
    private class IntegrationRequestStruct{
        private Object req;
        private int tradingPartnerId;
        private IntegrationRequestStruct(Object pReq, int pTradingPartnerId){
            req = pReq;
            tradingPartnerId = pTradingPartnerId;
        }
    }


    private Comparator EDI_REQUEST_FAX_CONFIRM_COMP = new Comparator() {
        public int compare(Object o1, Object o2) {

            String t1 = getFaxConfirmText((OutboundEDIRequestData)o1);
            String t2 = getFaxConfirmText((OutboundEDIRequestData)o2);
            if(t1 == null && t2 == null){
                //this means that they are using he store default
                int store1 = ((OutboundEDIRequestData)o1).getOrderD().getStoreId();
                int store2 = ((OutboundEDIRequestData)o2).getOrderD().getStoreId();
                return store1 - store2;
            }
            if(t1 == null){
                return 1;
            }
            if(t2 == null){
                return -1;
            }
            return t1.compareTo(t2);
        }
    };
   
    private Map<String, String> getPropertyMap(JobDataMap dataMap) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        if (dataMap != null && dataMap.size() != 0) {
            String[] keys = dataMap.getKeys();
            for (String key : keys) {
                propertyMap.put(key, dataMap.getString(key));
            }
        }
        return propertyMap;
    }

}
