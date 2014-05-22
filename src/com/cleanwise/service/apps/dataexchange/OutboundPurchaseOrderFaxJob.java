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
import org.quartz.JobExecutionException;

import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.session.DistributorHome;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.IntegrationServicesHome;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.session.OrderHome;
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

/**
 * Title:        OutboundPurchaseOrderFaxJob
 * Copyright:    Copyright (c) 2009
 * Company:      Cleanwise, Inc.
 * @author       Sergei V. Cher
 *
 */
public class OutboundPurchaseOrderFaxJob extends EventJobImpl {

    private static final Logger log = Logger.getLogger(OutboundPurchaseOrderFaxJob.class);


    private static final String P_XMLFILENAME   = "xmlfilename";
    String envVarValue; // value of the environment variable
    String pathtojbossHome; // path to the "jbossHome" directory; 

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
    
    static OrderHome mOrderHome; 
    Order ordRef; 

    HashMap storeHM = new HashMap();
        
    /******************************************************************/
    
    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public OutboundPurchaseOrderFaxJob() {
    }

    public void execute(JobDetail jobDetail) throws JobExecutionException {
        try {

            generateFaxes(jobDetail);

        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    public void generateFaxes(JobDetail jobDetail) throws Exception {

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
  	      log.fatal ("Usage: OutboundPurchaseOrderFaxJob [Path to XML File]");
	      log.fatal ("Path to XML file *must* be passed as a parameter in JobDataMap object.");
	      return;
        }    	
        pathtojbossHome = getjbossHomeDirectory();
        String fullPathToXmlFile1 = pathtojbossHome + "/xsuite/jobs/" + pXmlfilename;
        
        /***
    	String envVarName = "XSUITE_HOME";
    	envVarValue = System.getenv(envVarName);
    	//String propFileName = envVarValue + "/xsuite/app.properties"; // commented temporary 2/13/2009
        String fullPathToXmlFile1 = envVarValue + "/xsuite/jobs/" + pXmlfilename;
        ***/
        log.info("fullPathToXmlFile in production = " + fullPathToXmlFile1);
        
        //String fullPathToXmlFile2 = "C:\\EJBServer\\xsuite\\jobs\\SendSingleFaxPO.xml"; // for testing on my local Win box
        //log.info("fullPathToXmlFile on my local Win box = " + fullPathToXmlFile2); // for testing on my local Win box
        
        try {
            setUp();
        }catch (Exception e){
        	log.info("Setup failed, database remains unchanged, and no faxes have " +
                    "been sent.");
            e.printStackTrace();
            return;
        }
        log.info("Setup was successful.");
        generateOutboundFaxs(fullPathToXmlFile1); // Production system
        //generateOutboundFaxs(fullPathToXmlFile2); // for testing ONLY on my local Win box!!!
        log.info("Finished");
    }
    /**
     *Sets up the class as an EJB Client.
     */      
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
    	/***
    	String envVarName = "XSUITE_HOME";
    	envVarValue = System.getenv(envVarName);
    	String propFileName = envVarValue + "/xsuite/app.properties"; // commented temporary 2/13/2009
    	String propFileName = "C:\\Documents and Settings\\scher\\workspace\\stjohn\\app.properties"; //added temporary 2/13/2009
    	***/
    	
    	pathtojbossHome = getjbossHomeDirectory();
        String propFileName = pathtojbossHome + "/xsuite/app.properties"; // full path to the property file in production - uncomment after testing
        //String propFileName = "C:\\Documents and Settings\\scher\\workspace\\stjohn\\app.properties"; //added for testing only on my local Win box
    	if ( null == propFileName ) {
            propFileName = "installation.properties";
        }
    	log.info("propFileName = " + propFileName);
    	
    	/********************************************/
        
        Properties props = new Properties();
        java.io.File f = new java.io.File(propFileName);
        log.info("Absolute path = " + f.getAbsolutePath());
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
        
        //setup reference to the order bean
        ref = jndiContext.lookup(JNDINames.ORDER_EJBHOME);
        mOrderHome = (OrderHome)
        PortableRemoteObject.narrow(ref, OrderHome.class);
        ordRef = mOrderHome.create();
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
        FaxConfigMyFax config; // commented by SVC 2/13/3009 for testing purposes
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
                
                log.info("serverUrl = " + serverUrl);
                
                coverPage = prpSvc.getProperty("FAX.CoverPage");
                
                log.info("coverPage = " + coverPage);
                
            }catch(DataNotFoundException e){
            	e.printStackTrace();
            	throw new RuntimeException("Could not find property: FAX.ServerUrl.  This property *must* be "+
                        "configured to continue.  Exiting without faxing or making changes to the database.");
                

            }
            log.info("ServerURL and coverPage are set up.");
            
            config = new FaxConfigMyFax(serverUrl);
            log.info("config object is instantiated.");
            
            config.setCoverPage(coverPage);
            log.info("setCoverPage() method is invoked.");
            
            config.setBaseDirectoryPath(docRoot);
            log.info("setBaseDirectoryPath() method is invoked.");
            
            config.setDebug(false); 
            
            log.info("Using fax config: " + config.toString());

            List tpiList = intSvc.getOutboundBusEntityByTradingType(
                    RefCodeNames.TRADING_TYPE_CD.FAX,RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
            
            log.info("------------Size of tpiList = " + tpiList.size());
            
            if (tpiList == null || tpiList.size() == 0){
                log.info("Error: No fax distributors configured!");
                return;
            }            
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
                                " claims to be associated with bus entity id: " + busEntId);
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
                    log.info("found " + pos.size() + " pos to process for distributor: " + distErpNumber);
                    //add it to the master list to process if there are some pos for this distributor
                    if (pos.size() > 0){
                        //masterList.add(new OutboundPurchaseOrderNew.DistributorPosList(dist,pos,tpi)); //commented by SVC 2/11/2009
                        masterList.add(new DistributorPosList(dist,pos,tpi)); //added by SVC 2/11/2009
                    }
                }
            }
        }catch(RemoteException e){
        	throw new RuntimeException("Unexpected RemoteException. "+
                    "Exiting without faxing or making changes to the database.");
            
        }
        
        log.info("size of erpNumServed =  " + erpNumServed.size()); //SVC
        log.info("ArrayList erpNumServed =  " + erpNumServed); //SVC
        log.info("size of masterList1 =  " + masterList.size()); //SVC
        log.info("masterList1 =  " + masterList); //SVC
        		
        //break up the master list if any of the trading partners
        //have large number of POs (> posPerFax). These can both
        //break the RightFax API and more importantly would more
        //prone to being interrupted during the FAX transmission
        
 
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
           

            //cut the current version down to under posPerFax (10) pos, and add
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
        
        log.info("size of masterList2 =  " + masterList.size()); //SVC
        log.info("masterList2 =  " + masterList); //SVC
        
        //this is the List of items that will be sent back to be updated
        //IntegrationRequestsVector irv = new IntegrationRequestsVector();

        //now loop through and FAX the retrieved documents
        StoreData  storeD = null;
        for (int i=0,len=masterList.size();i<len;i++){
            boolean success = false;
            DistributorPosList distPoList = (DistributorPosList) masterList.get(i);
            DistributorData dist = distPoList.getDistributorData();
            TradingPartnerInfo tpi = distPoList.getTradingPartnerInfo();
                        
            FaxRequestMyFax req = new FaxRequestMyFax(tpi.getPurchaseOrderFaxNumber().getPhoneNum(),tpi.getToContactName(),dist.getBusEntity().getShortDesc());
            
            log.info("req = " + req); //SVC
            
            ArrayList reqs = new ArrayList();
            reqs.add(req);

            //loop through all the positions and extract some info. from them
            StringBuffer coverNotes = new StringBuffer();
            StringBuffer coverSheetPoList = new StringBuffer(); // new: SVC
            ArrayList orderIdList = new ArrayList(); // new: SVC => list of order Ids for a single FAX
            String lastFaxConfirmText = null;

            for (int j=0,len2=distPoList.getPos().size();j<len2;j++){
            	log.info("processing PO (Purchase Order): " + j);
                OutboundEDIRequestData po = (OutboundEDIRequestData) distPoList.getPos().get(j);
                int storeId = po.getPurchaseOrderD().getStoreId();
                
                //int orderId = po.getOrderD().getOrderId(); // SVC (new): get orderId for the Purchase Order
                
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
                //coverSheetPoList.append("POs in this fax: "); // new: SVC
                if (po.getOrderItemDV().size() > 0){
                    OrderItemData itm = (OrderItemData) po.getOrderItemDV().get(0);
                    coverNotes.append(itm.getOutboundPoNum());
                    coverSheetPoList.append(itm.getOutboundPoNum()); // new: SVC
                    orderIdList.add(itm.getOrderId()); // new: SVC
                }else{
                	log.info("Error Panic: order has no items!");
                }
                //make sure we are not appending an extra comma.
                if (j + 1 < len2){
                    coverNotes.append(",");
                    coverSheetPoList.append(","); // SVC
                }
            }
            try {
            	log.info("constructing PO list");
                FaxTranslatorMyFax translatormyfax = new FaxTranslatorMyFax();
                // SVC: Below => String encodedDocBody = body of the FAX
                String encodedDocBody = translatormyfax.constructOutboundPurchaseOrder(dist,distPoList.getPos(),storeD,tpi.getFromContactName());
                log.info("Done with construction of PO list");
                
                String faxNum = req.getReceiverFaxNum(); // FAX#(s) to send FAX to 
                log.info("******SVC: faxNum = " + faxNum);
                
                // create FAX, which will be sent to the customer
                FaxCreatorMyFax faxcreatormyfax = new FaxCreatorMyFax(); 
                // Possible solution(I decided that it is not necessary): Using Java Bean "faxSettings", set the values of the : fax_recepient_number
                //                                                      : fax_recipient_name
                //                                                      : document => encoded document body (String encodedDocBody) in PDF format
                
                // xmlDoc (encodedDocBody) is initially passed to method generateOutboundFaxs() as a parameter
                int fax_not_sent = faxcreatormyfax.createAndSendSingelFax(xmlDoc
                		                        //,tpi.getPurchaseOrderFaxNumber().getPhoneNum()
                		                        , faxNum
                		                        , tpi.getToContactName()
                		                        , encodedDocBody
                		                        , coverSheetPoList.toString()
                		                        , orderIdList
                		                        , ordRef
                		                        , coverNotes.toString());  
                
                log.info("Attempt to send a FAX was made.");
                //success = true; // commented by SVC 9/3/2009
                log.info("fax_not_sent = " + fax_not_sent);
                if (fax_not_sent == 0) { //all FAX(s) for one PO were sent successfully
                    success = true; 
                } else {
                	success = false;
                }
            }catch(FaxExceptionMyFax e){
                e.printStackTrace();
                
            }catch(Exception e){
                e.printStackTrace();
                
            }
            
            //update state on the header level
            log.info("Updating state on the header level");
            
            //this is the List of items that will be sent back to be updated
            IntegrationRequestsVector irv = new IntegrationRequestsVector();
            
            for (int k=0,polen=distPoList.getPos().size();k<polen;k++){
                //get the current PO
                OutboundEDIRequestData po = (OutboundEDIRequestData) distPoList.getPos().get(k);
                if (!success){
                    po.getOrderD().setExceptionInd("Y");
                    po.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
                }else{
                    po.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
                }
                //update state on the item level
                //String outboundPoNumber = "Unset";
                for (int h=0,itmlen=po.getOrderItemDV().size();h<itmlen;h++){
                    OrderItemData itm = (OrderItemData) po.getOrderItemDV().get(h);
                    //outboundPoNumber = itm.getOutboundPoNum();
                    if (!success){
                        itm.setExceptionInd("Y");
                        itm.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED);
                    }else{
                        itm.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
                    }
                    //add item to the list to be updated
                    irv.add(new IntegrationRequestStruct(itm,distPoList.getTradingPartnerInfo().getTradingPartnerData().getTradingPartnerId()));
                }
                //add order to the list to be updated
                irv.add(new IntegrationRequestStruct(po.getOrderD(),distPoList.getTradingPartnerInfo().getTradingPartnerData().getTradingPartnerId()));

                //add po to the list to be updated
                irv.add(new IntegrationRequestStruct(po.getPurchaseOrderD(),distPoList.getTradingPartnerInfo().getTradingPartnerData().getTradingPartnerId()));

                /*OrderPropertyData od = OrderPropertyData.createValue();
                od.setOrderId(po.getOrderD().getOrderId());
                od.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
                od.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
                od.setShortDesc("Fax Id");
                od.setAddBy("System");
                od.setModBy("System");
                if (req.getRequestId() == null){
                    od.setValue("Po Number: " + outboundPoNumber + "ERRR");
                }else{
                    od.setValue("Po Number: " + outboundPoNumber + " Fax Id: " + req.getRequestId());
                }
                irv.add(new IntegrationRequestStruct(od,distPoList.getTradingPartnerInfo().getTradingPartnerData().getTradingPartnerId()));*/
           
            } // end of the loop: "for (int k=0,polen=distPoList.getPos().size()..."
            
            try{
                int tpid = -1;
                Iterator it = irv.iterator();
                IntegrationRequestsVector reqs1 = new IntegrationRequestsVector();
                while(it.hasNext()){
                    IntegrationRequestStruct aReq = (IntegrationRequestStruct) it.next();
                    if(tpid == -1){
                        tpid = aReq.tradingPartnerId;
                    }else if(tpid != aReq.tradingPartnerId){
                        if(!reqs1.isEmpty()){
                    		intSvc.processIntegrationRequests(reqs1, "",tpid);
                        }
                        tpid = aReq.tradingPartnerId;
                    }
                    reqs1.add(aReq.req);

                }
                if(!reqs1.isEmpty()){
                    intSvc.processIntegrationRequests(reqs1, "",tpid);
                }
            }catch (Exception e){
            	e.printStackTrace();
                throw new RuntimeException("Undetermined state!!");
            }
            
      }  // end of the loop: "for (int i=0,len=masterList.size();i<len;i++) {" 
        
      /*** 
        try{
            int tpid = -1;
            Iterator it = irv.iterator();
            IntegrationRequestsVector reqs = new IntegrationRequestsVector();
            while(it.hasNext()){
                IntegrationRequestStruct aReq = (IntegrationRequestStruct) it.next();
                if(tpid == -1){
                    tpid = aReq.tradingPartnerId;
                }else if(tpid != aReq.tradingPartnerId){
                    if(!reqs.isEmpty()){
                		intSvc.processIntegrationRequests(reqs, "",tpid);
                    }
                    tpid = aReq.tradingPartnerId;
                }
                reqs.add(aReq.req);

            }
            if(!reqs.isEmpty()){
                intSvc.processIntegrationRequests(reqs, "",tpid);
            }
        }catch (Exception e){
        	e.printStackTrace();
            throw new RuntimeException("Undetermined state!!");
        }
     ***/
        
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

    //returns the text that should be used to confirm this purchase order request
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

    private String jbossHomeDirectory = null;
	private String getjbossHomeDirectory() throws Exception{
		if(jbossHomeDirectory == null){
			//jbossHomeDirectory = Utility.loadProperties("defst.default.properties").getProperty("jbossHome"); YKR25
			jbossHomeDirectory = System.getProperty("jbossHome"); //YKR25
			//log.info("jbossHomeDirectory1 = " + jbossHomeDirectory);
		}		
		//log.info("jbossHomeDirectory2 = " + jbossHomeDirectory);
		return jbossHomeDirectory;
	}
	
}
