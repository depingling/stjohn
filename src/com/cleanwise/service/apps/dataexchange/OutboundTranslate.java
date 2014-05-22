package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.apps.quartz.EventJob;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Category;
import org.quartz.*;

/**
 * This class is entry point of inbound document translation. Depend on which type
 * of transaction, it create object for that type to parse the transaction
 *
 * @author Deping
 */
public class OutboundTranslate extends Translator implements EventJob {
	private static final Category log = Category.getInstance(OutboundTranslate.class);
	private static final String P_ERP_NUM_LIST = "erpNumList";
	private static final String P_BUS_ENTITY_ID_LIST = "busEntityIdList";
	private static final String P_TRADING_PARTNER_LIST = "tradingPartnerList";
	private static final String P_SET_TYPE = "setType";
	private static final String P_EVENT_SYS = "eventSys";
	private static final String P_OUT_FILENAME = "outFilename";
    protected TradingPropertyMapDataVector mTradingPropertyMapDataVector;
    private boolean managingFiles = false;
    //contains a list of the files that have been processed
    private boolean externallyManagedStreams = false;
    private Map sendParameterMap;
    private static int counter = 0;
    private int priorityOverride = 0;
	private int subProcessPriority = 0;

    public void setManagingFiles(boolean pManagingFiles){
    	managingFiles = pManagingFiles;
    }
    
    IntegrationRequestsVector requests = new IntegrationRequestsVector();
    private String reports = null;

    public OutboundTranslate() throws Exception {
        super();
    }
    
    public OutboundTranslate(int priorityOverride, int subProcessPriority) throws Exception {
        super();
        this.priorityOverride = priorityOverride;
        this.subProcessPriority = subProcessPriority;
    }

    @Override
	public void setEventPriority(JobDataMap jobDataMap) {
    	String priorityOverrideStr = (String) jobDataMap.get(Event.PRIORITY_OVERRIDE);
        if (Utility.isSet(priorityOverrideStr)){
        	priorityOverride = new Integer(priorityOverrideStr).intValue();
        }
        priorityOverrideStr = (String) jobDataMap.get(Event.SUBPROCESS_PRIORITY);
        if (Utility.isSet(priorityOverrideStr)){
      	  subProcessPriority = new Integer(priorityOverrideStr).intValue();
        }
	}
    public TradingPropertyMapDataVector getTradingPropertyMapDataVector() {
        return mTradingPropertyMapDataVector;
    }

  /**
   * Verifies the authorization
   */
  public boolean verifyAuthorization(String authorization,String security){
	  TradingProfileData tpd = getDefaultTradingProfileData();
	  if(tpd == null){
		  log.info("Could not get trading profile");
		  return false;
	  }
	  log.info("Checking...");
	  log.info("Does "+tpd.getAuthorization()+"="+authorization);
	  log.info("Does "+tpd.getSecurityInfo()+"="+security);
	  if(authorization.equals(tpd.getAuthorization()) && security.equals(tpd.getSecurityInfo())){
		  return true;
	  }
	  log.info("No Match");
	  return false;
  }

  /**
   * Tries to get a default trading profile for the configured setup.  This may not
   * work if the trading profile needs an incomming transaction to uniquely determine this.
   * @return TradingProfileData
   */
  private TradingProfileData getDefaultTradingProfileData(){
	  try{
		  log.info("beid="+getBusEntityId());
		  log.info("erp="+getErpNum());
		  log.info("set="+getSetType());
		  TradingProfileConfigData tpd = getOutboundTradingProfileConfig(getErpNum(), getBusEntityId(),0, getSetType());
		  return getTradingProfile(tpd.getTradingProfileId());
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return null;
  }

  public OutboundTranslate(String erpNum, int busEntityId, String pSetType,
                           IntegrationServices pIntegrationServices,
                           TradingPartner pTradingPartner, ByteArrayOutputStream pOut) throws Exception {
    initializeVariables(erpNum, busEntityId, pSetType, null);
    intSvc = pIntegrationServices;
    partnerSvc = pTradingPartner;
    externallyManagedStreams = true;
    setOutputStream(pOut);
  }

  // ====================================================================================
  /**
   * @exception RemoteException
   */
  public void setProfile(TradingProfileData pProfile) throws Exception {
    super.setProfile(pProfile);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
    formatter.setTimeZone(TimeZone.getTimeZone(pProfile.getTimeZone()));
    setDateTimeAsString(new String(formatter.format(getTimestamp())));
    pProfile.setInterchangeControlNum(partnerSvc.getNextInterchangeControlNum(
      pProfile.getTradingProfileId()));
    pProfile.setGroupControlNum(partnerSvc.getNextGroupControlNum(pProfile.
      getTradingProfileId()));
  }

  /**
   * increments the interchange control number in the supplied object and updates the db
   */
  public int incrementInterchangeControlNum(TradingProfileData pProfile) throws
    Exception {
    pProfile.setInterchangeControlNum(partnerSvc.getNextInterchangeControlNum(
      pProfile.getTradingProfileId()));
    return pProfile.getInterchangeControlNum();
  }
  
  /**
   * increments the group control number in the supplied object and updates the db
   */
  public int incrementGroupControlNum(TradingProfileData pProfile) throws
    Exception {
    pProfile.setGroupControlNum(partnerSvc.getNextGroupControlNum(pProfile.
      getTradingProfileId()));
    return pProfile.getGroupControlNum();
  }

  public OutboundEDIRequestDataVector getEDIOrdersByErpNumAndSetType(String
    erpNum, String setType, int busEntityId) throws RemoteException {
    OutboundEDIRequestDataVector lOrders =
        intSvc.getEDIOrdersByErpNumAndSetType(erpNum, setType, busEntityId);
    return lOrders;
  }

  /**
   * Get a trading profile config data from Trading_Profile_Config table
   * by matching trading profile id
   */
  public TradingProfileConfigData getOutboundTradingProfileConfig(
    String erpNum, int busEntityId, int incommingProfileId, String setType) throws
    RemoteException, DataNotFoundException {

    TradingPartnerDescView vw = intSvc.getOutboundTradingProfileConfig(erpNum,
      busEntityId, incommingProfileId, setType);
    if (vw != null) {
      mTradingPropertyMapDataVector = vw.getTradingPropertyMapDataVector();
      TradingProfileConfigData lProfileConfig = vw.getTradingProfileConfigData();
      return lProfileConfig;
    }
    return null;
  }

  /**
   * Get a trading profile record from Trading_Profile table by match group sender id
   * with incoming document group receiver id and group receiver id with incomming
   * group sender id.
   */
  public TradingProfileData getTradingProfile(int profileId) throws
    RemoteException, DataNotFoundException {
    TradingProfileData lProfile = partnerSvc.getTradingProfile(profileId);
    if (partner == null ||
        partner.getTradingPartnerId() != lProfile.getTradingPartnerId()) {
      //refresh the partner if necessary
      partner = partnerSvc.getTradingPartner(lProfile.getTradingPartnerId());
    }
    return (lProfile);
  }

  private OutboundEDIRequestDataVector mReqOrderDV;
  public OutboundEDIRequestDataVector getAllOutboundReqOrderDV() {
    return mReqOrderDV;
  }

  public void setOutboundReqOrderDV(OutboundEDIRequestDataVector p) {
    mReqOrderDV = p;
  }

  private void initializeVariables(String erpNum, int busEntityId,
                                   String pSetType, String pOutFilename) {
    setBusEntityId(busEntityId);
    setErpNum(erpNum);
    if (pSetType.equals("850_REBATE"))
    	pSetType = "850";
    setSetType(pSetType);
    if (pSetType.equals("850"))
      setGroupType(Translator.FG850);
    else if (pSetType.equals("855"))
      setGroupType(Translator.FG855);
    else if (pSetType.equals("856"))
      setGroupType(Translator.FG856);
    else if (pSetType.equals("810"))
      setGroupType(Translator.FG810);
    else if (pSetType.equals("997"))
      setGroupType(Translator.FG997);
    else if (pSetType.equals("824"))
      setGroupType(Translator.FG824);
    else if (pSetType.equals("867"))
      setGroupType(Translator.FG867);
    setOutputFileName(pOutFilename);
  }

  public void setUp(String erpNum, int busEntityId, String pSetType,
                    String pOutFilename) throws Exception {
    initializeVariables(erpNum, busEntityId, pSetType, pOutFilename);
  }

  public void setUp1(String erpNum, int busEntityId, String pSetType,
                    InboundTranslate pInboundTranslate) throws Exception {

    //we will go through all this procss to get the file name as it may be necessary to report back
    initializeVariables(erpNum, busEntityId, pSetType, pInboundTranslate.getOutputFileName());
    if (pInboundTranslate.getOutputStream() != null) {
      this.setOutputStream(pInboundTranslate.getOutputStream());
    }
    pInboundTranslate.setOutputFileName(getOutputFileName());
  }

  /**
   *Overidden from super class.  If a builder calls this method when the output stream is being managed by something other than
   *this class an IOException is thrown.
   */
  public void openOutputStream() throws IOException {
    if (externallyManagedStreams) {
      throw new IOException(
        "Builder trying to open output stream for an externally managed stream!");
    }
    super.openOutputStream();
  }

  /**
   *initilizes the output stream, and if managing the fies itself will create a new file using
   *its logic, or if the supplied builder implements getFileName it will use the supplied file name.
   *If the builderreturns null its own logic will be used for naming files.
   */
  public void initializeOutputStream(OutboundTransaction pBuilder) throws Exception {
    String oFileName = getOutputFileName();
    log.info("initializeOutputStream. OutoutFileName: "+oFileName);
    if (oFileName != null && managingFiles == false) {
      openOutputStream();
    } else {
      managingFiles = true;

      String fileName = null;
      if (pBuilder != null) {
        fileName = pBuilder.getFileName();
      }
      //determine file name
      if (fileName == null) {
        String fileExtension = null;
        if (pBuilder != null) {
          fileExtension = pBuilder.getFileExtension();
        }
        if (fileExtension == null) {
          fileExtension = ".txt";
        }
        java.text.SimpleDateFormat frmt = new java.text.SimpleDateFormat(
          "yyyyMMddHHmmss");
          String now = frmt.format(new java.util.Date())+getCounterStr(pBuilder);
          if (getErpNum() != null && "1127".equals(getErpNum())) {
            fileName = "clwlagorder_" + now + fileExtension;
          } else {
            String dtype = "i";
            if (getSetType().endsWith("850")) {
              dtype = "po";
            } else if (getSetType().endsWith("810")) {
              dtype = "inv";
            } else if (getSetType().endsWith("855")) {
              dtype = "ack";
            } else if (getSetType().endsWith("856")) {
              dtype = "sn";
            } else if (getSetType().endsWith("824")) {
              dtype = "exc";
            } else if (getSetType().endsWith("997")) {
              dtype = "fa";
            }
            fileName = dtype + getSetType() + "_cleanwise.dat." + now +
                       fileExtension;
          }          
      }

      setOutputFileName(fileName);
      if (!externallyManagedStreams) {
        openOutputStream();
      }
    }
  }

    public void translate(OutboundEDIRequestDataVector orders, String erpNum,
                          int busEntityId, String setType) throws Exception {
    	reports = "";
    	requests.clear();
        TradingProfileConfigData profileConfigD = null;
        TradingProfileData profileD = null;
        TradingPartnerAssocData profileOveride = null;
        int currIncommingProfileId = -1; // 0 is valid, must be negative value
        int currProfileId = -1;
        BuilderRequest currBuilderReq = null;

        log.info("translate.Order counts: " + orders.size());
        setOutboundReqOrderDV(orders);

        if (orders.size() == 0) {
            log.info("  *** NO ORDERS *** ");
            return;
        }

        String storeType = null;
        HashMap overides = new HashMap();
        String originalGroupSender = null;

        IdVector incommingProfileIds = new IdVector();
        for (int i = 0; i < orders.size(); i++) {
            OutboundEDIRequestData outEdi = (OutboundEDIRequestData) orders.get(i);
            if(outEdi != null && outEdi.getPurchaseOrderD() != null){
            	log.info("Outbound Translator PO Log: "+outEdi.getPurchaseOrderD().getErpPoNum());
            }
            Integer profileId = new Integer(outEdi.getIncomingTradingProfileId());
            if (!incommingProfileIds.contains(profileId)) {
                incommingProfileIds.add(profileId);
            }
            if (storeType == null) {
                storeType = outEdi.getStoreType();
            } else {
                if (!storeType.equals(outEdi.getStoreType())) {
                	log.fatal("translate.Error multiple store types for outbound translation "+
                			"(translator will continue, but results should be verified).  " +
                			"This means that we are generating an outbound transaction with 2 different stores in it and there are " +
                			"at least 2 different store types included in the output (DISTRIBUTOR, MLA, etc) "+generateErrorInformationStringMessage(profileId.intValue()));
                }
            }
        }

        // sort the data...only implented for 850s currently.
        // this would be different depending on the mappings and what we are
        // trying to do. Right now this is implemented with the idea of it
        // being a distributor store with multiple Accounts that need different
        // group senders. There are other options here, but they have not yet
        // been implemented.
        Collections.sort(orders, OUTBOUND_PROCESSING_COMPARE);

        // will hold the list that the builder should process. This will be the
        // list that matches the various
        // options that may requier multiple files included overidden
        // groupSenders and multiple trading profiles
        // for a single account.
        HashMap builderRequestsMap = new HashMap();
        for (int i = 0; i < orders.size(); i++) {
            log.debug("i=" + i);
            // Now looping through trasnsactions...build temp list from this
            // instead of just calling builder
            OutboundEDIRequestData outEdi = (OutboundEDIRequestData) orders.get(i);
            int id = outEdi.getIncomingTradingProfileId();

            // if the profile config data has not yet been fetched grab it.
            if (id != currIncommingProfileId) {
                currIncommingProfileId = id;
                //if the id passed in is 0 the searching of this data will search for all profiles that work
                //for this erpNum/bus entity id and set type combo
                profileConfigD = getOutboundTradingProfileConfig(erpNum, busEntityId,id, setType);
                if (profileD == null ||
                        profileD.getTradingPartnerId() !=
                                profileConfigD.getTradingProfileId()) {
                    if (profileConfigD == null) {
                    	log.fatal("translate.Transaction not setup "+generateErrorInformationStringMessage(id));
                    }
                    profileD = getTradingProfile(profileConfigD.getTradingProfileId()); //this also initiallizes the partner
                    originalGroupSender = profileD.getGroupSender();
                    buildOverideHashMap(overides, profileD.getTradingPartnerId());
                }
            }

            log.info("translate()=> Profile[" + id + "] profileConfigD: " + profileConfigD);

            //Check TRADING_TYPE for partner if it is not EDI than continue
            if(partner!=null){
                if(RefCodeNames.TRADING_TYPE_CD.FAX.equals(partner.getTradingTypeCd())){
                	log.fatal("translate.Trading partner is fax "+generateErrorInformationStringMessage(id));
                    continue;
                }
            }

            if (profileConfigD == null) {
            	log.fatal("translate.Trading profile config is not setup "+generateErrorInformationStringMessage(id));
                continue;
            }

            if (getPartner() == null) {
            	log.fatal("translate.Trading partner is not setup "+generateErrorInformationStringMessage(id));
            }

            // only make this check if this tp is configured for multiple tps,
            // if not
            // then we can assume that we want to use the only trading profile
            // that is
            // avaliable to this partner. If there are multiple tps then we need
            // to know
            // which one this transaction comes in on to know what to send it
            // out on.
            if (profileConfigD.getTradingProfileId() != currProfileId &&
                    incommingProfileIds.size() > 1) {
                currProfileId = profileConfigD.getTradingProfileId();

            }
            // Next check for overides for this bus entity. If there is an
            // overide for the header (aka group sender) then
            // we will need to recreate the envelope
            if (getPartner() != null
                    && getPartner().getTradingPartnerTypeCd().equals(RefCodeNames.TRADING_PARTNER_TYPE_CD.DISTRIBUTOR)
                    && RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)) {
                // using arbitrary hierarchy for overides, if multiple bus
                // entities have overides the behavior is
                // distributor then account then store
                profileOveride = (TradingPartnerAssocData) overides.get(new Integer(outEdi.getDistributorIdIfPresent()));

                if (profileOveride == null) {
                    profileOveride = (TradingPartnerAssocData) overides.get(new Integer(outEdi.getAccountIdIfPresent()));
                    if (profileOveride == null) {
                        profileOveride = (TradingPartnerAssocData) overides.get(new Integer(
                                outEdi.getStoreIdIfPresent()));
                    }
                }
            }

            currBuilderReq = new BuilderRequest(profileConfigD, profileD,  profileOveride, currIncommingProfileId);

            BuilderRequest existingBuilerReq = (BuilderRequest) builderRequestsMap.get(currBuilderReq.getKey());
            if (existingBuilerReq == null) {
                existingBuilerReq = currBuilderReq;
                builderRequestsMap.put(currBuilderReq.getKey(), existingBuilerReq);
            }

            if (existingBuilerReq.transactionsToProcess == null) {
                existingBuilerReq.transactionsToProcess = new
                        OutboundEDIRequestDataVector(); //init the vector if necessary
            }

            existingBuilerReq.transactionsToProcess.add(outEdi);
        }

        //now loop through the builder requests and process them
        Iterator it = builderRequestsMap.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            log.info("translate.1 DOING BUILDER!!!!!!!!!!!!!!!!!!!!!!!         " + key);
            BuilderRequest builderReq = (BuilderRequest) builderRequestsMap.get(key);
            profileD.setGroupSender(originalGroupSender); // restore original group sender in case GroupSenderOverride exists
            handleBuilderCleanup(builderReq);
        }

        // do any data updates from this processing
        if (requests != null && !requests.isEmpty()) {
            if (!processIntegrationRequests(requests)){
            	setReport("Failed outbound translation");
            }
        }
        
        log.info(reports);
    }


    /**
     * Genarates a debug string suitable for human consumption.  This should be placed on
     * the end of any fatal error message and optionally on others if useful.
     * @return the debug string
     */
    private String generateErrorInformationStringMessage(){
    	return generateErrorInformationStringMessage(0);
    }

    /**
     * Genarates a debug string suitable for human consumption.  This should be placed on
     * the end of any fatal error message and optionally on others if useful.
     * @param incommingTradingId optional
     * @return the debug string
     */
    private String generateErrorInformationStringMessage(int incommingTradingId){
    	StringBuffer ret = new StringBuffer(" for outbound transaction = " + this.getSetType());
    	if(this.getBusEntityId() != 0){
    		ret.append(" and busEntId: " + this.getBusEntityId());
    	}
    	if(Utility.isSet(this.getErpNum())){
    		ret.append("erpNum "+this.getErpNum());
    	}
    	if(incommingTradingId != 0){
    		ret.append(" incomming trading id: " + incommingTradingId);
    	}
    	return ret.toString();
    }

  /**
   * Builds the overides hashmap which contrains group sender and potentially other overidden trading profile data.
   * @param overides the hashmap to add data to
   * @param pTradingPartner the trading partner id that is currently being translated
   */
  private void buildOverideHashMap(HashMap overides, int pTradingPartner) throws
    RemoteException {
    TradingPartnerAssocDataVector assocList = partnerSvc.
      getTradingPartnerAssocDataVectorForPartnerAll(pTradingPartner); // get em for this tpid
    Iterator it = assocList.iterator();
    while (it.hasNext()) {
      TradingPartnerAssocData tpa = (TradingPartnerAssocData) it.next();
      if (Utility.isSet(tpa.getGroupSenderOverride())) {
        overides.put(new Integer(tpa.getBusEntityId()), tpa);
      }
    }
  }


  // handles cleanup of the builder. currIncommingProfileId should no longer be necessary to pass in, but it is left in
  // untill the JCPenney transactions we know are working. There is a log message to look for, if this is not appearing
  // then this parameter can be removed from all of the builders
  private void handleBuilderCleanup(BuilderRequest buildReq) throws Exception {
    OutboundEDIRequestDataVector transactionsToProcess = buildReq.
      transactionsToProcess;
    TradingPartnerAssocData profileOveride = buildReq.profileOveride;
    TradingProfileData profileD = buildReq.profileD;
    TradingProfileConfigData profileConfigD = buildReq.profileConfigD;

    if (profileOveride != null) {
      // deal with overide information
      profileD.setGroupSender(profileOveride.getGroupSenderOverride());
    }
    setProfile(profileD);
	Class c = Class.forName(profileConfigD.getClassname());

    log.info("Outbound Translator Classname: " + profileConfigD.getClassname());

    OutboundTransaction builder = (OutboundTransaction) c.newInstance();
    builder.setTranslator(this);
    builder.setTransactionsToProcess(transactionsToProcess);

    initializeOutputStream(builder);
    builder.buildInterchangeHeader();
    builder.buildInterchangeContent();
    builder.buildInterchangeTrailer();
    IntegrationRequestsVector reqs = builder.getRequestsToProcess();
    if (reqs != null) {
      requests.addAll(reqs);
    } else {
      log.fatal("handleBuilderCleanup.No requests to process (Builder ran with input but did not return any updates)"+generateErrorInformationStringMessage());
    }
    if (getOutputStream().size() == 0)
    	return;
    File tmpFile = null;
    try{
    	String fileName = "OutboundTranslate";
    	String fileExt;
    	if(builder.getFileName() != null){
    		fileName+=builder.getFileName();
    	}
    	if(builder.getFileExtension() != null){
    		fileExt=builder.getFileExtension();
    	}else{
    		fileExt="txt";
    	}
    	tmpFile = File.createTempFile(fileName, fileExt,getIntegrationFileLogDirectory(false));
    	log.info("Writing output stream to tempfile: "+tmpFile.getAbsolutePath());
    	FileOutputStream fout = new FileOutputStream(tmpFile);
    	getOutputStream().writeTo(fout);
    	log.info("Writing output stream to tempfile: "+tmpFile.getAbsolutePath()+" success");
    }catch(Exception e){
    	log.fatal("handleBuilderCleanup.Writing output stream to tempfile failed.  " +
    			"This means that there is somthing wrong with the file system.  " +
    			"Processing continued normally as long as database processing is ok, " +
    			"but the secondary logging of files to the hard disk failed. "+
    			generateErrorInformationStringMessage(),e);
    }
    appendReport(builder.getTranslationReport());
  }



  public boolean processIntegrationRequests(IntegrationRequestsVector reqs) throws Exception {
    if (reqs.size() > 0){
      return processIntegrationRequests(reqs, getErpNum());
    }
    return true;
  }

  public static void main(String arg[]) throws Exception {
    String erpNumList = System.getProperty("erpnum");
    String outFilename = System.getProperty("ofile");
    String setType = System.getProperty("type");
    String eventSys = System.getProperty("eventSys");

    String busEntityIdList = System.getProperty("busEntityIdList");
    String tradingPartnerList = System.getProperty("tradingPartnerIdList");
    if ((erpNumList == null && busEntityIdList == null && tradingPartnerList == null) ||
        setType == null) {
      throw new Exception("usage: OutboundTranslator " +
                          "-Derpnum=<comma seperated ERP number list, ex: 10052,10053> -Dofile=<output file name (optional)> " +
                          "-tradingPartnerIdList=<comma seperated list, ex: 1,4>" +
                          "-Dodir=<output directory (optional)> " +
                          "-Dtype=<type, 850, 510, etc> " +
                          "-DbusEntityIdList=<comma seperated Business Entity Id List, ex: 100> " +
                          "One of the tradingPartnerIdList, busEntityIdList, or erpnum properties must be specified");
    }

    OutboundTranslate obTranslate = new OutboundTranslate();
    Map sendParameterMap = new HashMap();
    sendParameterMap.put("xxx", "IGNORE this transaction");
    
	String ftpmode = System.getProperty("ftpmode");
	if ( "email-attachment".equals(ftpmode)){
		sendParameterMap.put("ftpmode", System.getProperty("ftpmode"));
		sendParameterMap.put("emailto", System.getProperty("emailto"));
		sendParameterMap.put("emailsubject", System.getProperty("emailsubject"));
		sendParameterMap.put("emailfrom", System.getProperty("emailfrom"));
	}
    obTranslate.setSendParameterMap(sendParameterMap);
    obTranslate.process(erpNumList, busEntityIdList, tradingPartnerList, setType, eventSys, outFilename);
  }

    private void putEvent(int busEntityId, String erpNum, String setType) throws Exception {
        try {
            OutboundEDIRequestDataVector orders = getEDIOrdersByErpNumAndSetType(erpNum, setType, busEntityId);
            if(orders.size()>0){

                int processId = getProcessEjb().getActiveTemplateProcessId(RefCodeNames.PROCESS_NAMES.OUTBOUND_SERVICE);
                if (erpNum == null)
                	erpNum = intSvc.getErpNumByBusEntityId(busEntityId);
                
                EventProcessView epv = Utility.createEventProcessView(processId, priorityOverride, subProcessPriority);
                epv.getProperties().add(Utility.createEventPropertyData("erpNum",Event.PROCESS_VARIABLE,erpNum,2));
                epv.getProperties().add(Utility.createEventPropertyData("setType",Event.PROCESS_VARIABLE,setType,3));
                epv.getProperties().add(Utility.createEventPropertyData("processName",Event.PROCESS_VARIABLE,RefCodeNames.PROCESS_NAMES.OUTBOUND_SERVICE,4));
                epv.getProperties().add(Utility.createEventPropertyData("provider",Event.PROCESS_VARIABLE, new OutboundClwProvider(),5));
                epv.getProperties().add(Utility.createEventPropertyData("orders",Event.PROCESS_VARIABLE,orders,6));
                getEventEjb().addEventProcess(epv, "OutboundTranslate");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public void doProcessing(int busEntityId, String erpNum, String setType,
                            String outFilename) throws Exception {
    if (busEntityId == 0) {
      log.info("**************Processing ERP Number: xxx " + erpNum +
                         "**************");
    } else {
      log.info("**************Processing Bus Entity ID: " +
                         busEntityId + "**************");
    }
    setUp(erpNum, busEntityId, setType, outFilename);
    OutboundEDIRequestDataVector orders = getEDIOrdersByErpNumAndSetType(erpNum,
      setType, busEntityId);
    try {
      translate(orders, erpNum, busEntityId, setType);
    } catch (Exception e) {
    	log.fatal("doProcessing.Outbound translation failed."+generateErrorInformationStringMessage(),e);
    	throw e;
    }
    //check for null if there was nothing generated
    if (getOutputStream() != null) {
      closeOutputStream();
    }
    log.info("************** End of processing. ****************");
  }

  // sorts first by incoming trading profile id then by account id
  static final Comparator OUTBOUND_PROCESSING_COMPARE = new Comparator() {
    public int compare(Object o1, Object o2) {
      OutboundEDIRequestData req1 = (OutboundEDIRequestData) o1;
      OutboundEDIRequestData req2 = (OutboundEDIRequestData) o2;
      int id1 = req1.getIncomingTradingProfileId();
      int id2 = req2.getIncomingTradingProfileId();
      if (id1 == id2) {
        id1 = req1.getStoreIdIfPresent();
        id2 = req2.getStoreIdIfPresent();
        if (id1 == id2) {
          id1 = req1.getAccountIdIfPresent();
          id2 = req2.getAccountIdIfPresent();
          if (id1 == id2) {
            id1 = req1.getDistributorIdIfPresent();
            id2 = req2.getDistributorIdIfPresent();
          }
        }
      }
      return id1 - id2;
    }
  };

  private static class BuilderRequest {
    private TradingProfileConfigData profileConfigD;
    private TradingProfileData profileD;
    private TradingPartnerAssocData profileOveride;
    private OutboundEDIRequestDataVector transactionsToProcess;
    private Object key;

    private BuilderRequest(TradingProfileConfigData pProfileConfigD,
                           TradingProfileData pProfileD,
                           TradingPartnerAssocData pProfileOveride,
                           int pIncommingProfileId) {
      profileConfigD = pProfileConfigD;
      profileD = pProfileD;
      profileOveride = pProfileOveride;
      String overides = "";
      if (profileOveride != null) {
        overides = profileOveride.getGroupSenderOverride();
      }
      //+"::"+Integer.toString(incommingProfileId) intentionally left off as this may be 0 and 1 in ceratin cases.
      //the pProfileConfigD.getTradingProfileConfigId() should account for this case already as they are tied to
      //a given trading profile, and the EJB logic should handle getting this when there is not incomming trading
      //profile.  If included USPS will generate 2 files instead of one, one with the web orders (incomming trading profile id 0)
      //and 1 for the EDI (incomming trading profile id 1)
      key = Integer.toString(pProfileConfigD.getTradingProfileConfigId()) +
            "::" + Integer.toString(profileD.getTradingProfileId()) + "::" +
            overides;
    }

    private Object getKey() {
      return key;
    }
  }


  private OutputStream mOutputResponseStream;
  OutputStream getOutputExitingOutputResponseStream() {
    return mOutputResponseStream;
  }

  public void setOutputResponseStream(OutputStream pOutputResponseStream) {
    mOutputResponseStream = pOutputResponseStream;
  }

  private String mOutputResponseFileName;
  //public for unit tests
  public void setOutputResponseFileName(String pOutputResponseFileName) {
    mOutputResponseFileName = pOutputResponseFileName;
  }

  public String getOutputResponseFileName() {
    return mOutputResponseFileName;
  }


  public void translateToOutputStream(int busEntityId, String setType) throws Exception {
    OutboundEDIRequestDataVector orders = getEDIOrdersByErpNumAndSetType(null, setType, busEntityId);
    try {
      translate(orders, null, busEntityId, setType);
    } catch (Exception e) {
      log.fatal("translateToOutputStream.Outbound translation failed."+generateErrorInformationStringMessage(),e);
      throw e;
    }
    if (getOutputStream() != null) {
      closeOutputStream();
    }
    log.info("************** End of processing. ****************");
  }

    private void appendReport(String newReport){
    	reports += newReport;
    }

    private void setReport(String report){
    	reports = report;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	JobDetail jobDetail = context.getJobDetail();
    	setEventPriority(jobDetail.getJobDataMap());
    	execute(jobDetail);
    }

    public void execute(JobDetail jobDetail) throws JobExecutionException {
    	String erpNumList = null;
    	String busEntityIdList = null;
    	String tradingPartnerList = null;
    	String setType = null;
    	String eventSys = null;
    	String outFilename = null;
    	sendParameterMap = new HashMap();// parameters needed for send outbound file (e.g. ftp, email...)

    	String show = "";
        String jobName = jobDetail.getFullName();
        String trName  = jobDetail.getFullName();
        show += "\n****************************************************************************\n";
        show += "GetFtpJob: " + jobName + "(" + trName + ") exec at " + new Date() + "\n";
        JobDataMap dataMap = jobDetail.getJobDataMap();

        if (dataMap != null && dataMap.size() != 0) {
          show += "parameters:\n";
          String[] keys = dataMap.getKeys();

          for ( int i = 0; i < keys.length; i++) {
            if (P_ERP_NUM_LIST.equalsIgnoreCase(keys[i])){
            	erpNumList = dataMap.getString(keys[i]);
            } else if (P_TRADING_PARTNER_LIST.equalsIgnoreCase(keys[i])){
            	tradingPartnerList = dataMap.getString(keys[i]);
            } else if (P_BUS_ENTITY_ID_LIST.equalsIgnoreCase(keys[i])){
            	busEntityIdList = dataMap.getString(keys[i]);
            } else if (P_SET_TYPE.equalsIgnoreCase(keys[i])){
            	setType = dataMap.getString(keys[i]);
            } else if (P_EVENT_SYS.equalsIgnoreCase(keys[i])){
            	eventSys = dataMap.getString(keys[i]);
            } else if (P_OUT_FILENAME.equalsIgnoreCase(keys[i])){
            	outFilename = dataMap.getString(keys[i]);
            }else{
            	sendParameterMap.put(keys[i], dataMap.getString(keys[i]));
            }
            show += keys[i] + " is " + dataMap.getString(keys[i]) + "\n";
          }
        }

        show += "****************************************************************************\n";

        log.info(show);

    	if ((erpNumList == null && busEntityIdList == null && tradingPartnerList == null)) {
    		throw new JobExecutionException("usage: OutboundTranslator - At least one of the tradingPartnerIdList, busEntityIdList, or erpnumList properties must be defined.\r\n" +
    				"erpnumList=<comma seperated ERP number list, ex: 10052,10053>  " +
    				"tradingPartnerList=<comma seperated list, ex: 1,4>" +
    				"busEntityIdList=<comma seperated Business Entity Id List, ex: 100>");
    	}
    	if (setType == null) {
    		throw new JobExecutionException("usage: OutboundTranslator - type parameter must be defined" +
    				"setType=<type, 850, 510, etc> ");
    	}

    	if (outFilename != null){
    		int start = outFilename.indexOf("{");
    		int end = outFilename.indexOf("}");
    		if (start >= 0 && end > 0){
    			SimpleDateFormat formatter = new SimpleDateFormat(outFilename.substring(start+1, end));
    			String dateTimeString = new String(formatter.format(getTimestamp()));
    			outFilename = outFilename.substring(0, start) + dateTimeString + outFilename.substring(end+1);
    		}
    	}

    	try{
    		process(erpNumList, busEntityIdList, tradingPartnerList, setType, eventSys, outFilename);
    	} catch (Exception e) {
    		log.error(e.getMessage(), e);
			throw new JobExecutionException(e);
		}
	}

    public void process(String erpNumList, String busEntityIdList, String tradingPartnerList,
    		String setType, String eventSys, String outFilename) throws Exception {

    	try{
    		if (erpNumList != null) {
    			java.util.StringTokenizer tok = new java.util.StringTokenizer(erpNumList, ",");
    			while (tok.hasMoreTokens()) {
    				String erpNum = tok.nextToken();
    				OutboundTranslate translator = new OutboundTranslate(priorityOverride, subProcessPriority);
    				if(Utility.isTrue(eventSys)){
    					translator.putEvent(0, erpNum, setType);
    				}   else{
    					translator.setSendParameterMap(sendParameterMap);
    					translator.doProcessing(0, erpNum, setType, outFilename);
    				}
    			}
    		}

    		if (busEntityIdList != null) {
    			java.util.StringTokenizer tok = new java.util.StringTokenizer(
    					busEntityIdList, ",");
    			while (tok.hasMoreTokens()) {
    				String busEntityIdS = tok.nextToken();
					int busEntityId = Integer.parseInt(busEntityIdS);
					OutboundTranslate translator = new OutboundTranslate(priorityOverride, subProcessPriority);
					if(Utility.isTrue(eventSys)){
						translator.putEvent(busEntityId, null, setType);
					}   else{
						translator.setSendParameterMap(sendParameterMap);
						translator.doProcessing(busEntityId, null, setType, outFilename);
					}

    			}
    		}
    		if (tradingPartnerList != null) {
    			java.util.StringTokenizer tok = new java.util.StringTokenizer(
    					tradingPartnerList, ",");
    			while (tok.hasMoreTokens()) {
    				String tradingPartnerS = tok.nextToken();

					int tradingPartner = Integer.parseInt(tradingPartnerS);
					OutboundTranslate translator = new OutboundTranslate(priorityOverride, subProcessPriority);
					TradingPartnerAssocDataVector associations = Translator.partnerSvc.
					getTradingPartnerAssocDataVectorForPartnerForTrading(tradingPartner);
					HashSet processed = new HashSet();
					Iterator it = associations.iterator();
					while (it.hasNext()) {
						TradingPartnerAssocData assoc = (TradingPartnerAssocData) it.next();
						//sanity check that we haven't already processed this be.  Would only happen if
						//the same business entity was associated with this trading partner multipl times.
						//performace only, on second go round there would be no transaction to process.
						if (!processed.contains(new Integer(assoc.getBusEntityId()))) {
							if(Utility.isTrue(eventSys)){
								translator.putEvent(assoc.getBusEntityId(), null, setType);
							}   else{
								translator.setSendParameterMap(sendParameterMap);
								translator.doProcessing(assoc.getBusEntityId(), null, setType, outFilename);
							}

						}
					}
    			}
    		}
    	}catch(Exception e ) {
    		log.error(e.getMessage(), e);
    		throw new Exception("Failed in OutboundTranslate.process", e);
    	}

    }

	public void setSendParameterMap(Map sendParameterMap) {
		this.sendParameterMap = sendParameterMap;
	}

	public Map getSendParameterMap() {
		return sendParameterMap;
	}
	 
	private static synchronized String getCounterStr(OutboundTransaction pBuilder) {
		String counterStr = null;
		try{
			if(pBuilder != null){
				if(pBuilder.getCounterStr()!=null){
					counterStr = pBuilder.getCounterStr();
				}
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
		if (counterStr != null){
			return counterStr;
		}else{
			counter++;
			if (counter == 1000)
				counter = 1;
			
			return Utility.padLeft(counter, '0', 3);
		}
	}	
}
